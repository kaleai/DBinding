package kale.dbinding.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import kale.dbinding.CodeTemple;
import kale.dbinding.model.SimpleClass;
import kale.dbinding.model.SimpleField;
import kale.dbinding.util.LetterUtil;

/**
 * @author Kale
 * @date 2015/12/25
 */
public class ViewModelGenerator {

    public static final String[] SYSTEM_NAME = {"layout", "data"};

    /**
     * [class full name, class]
     */
    private final Map<String, SimpleClass> mViewModelMap = new HashMap<>();

    public List<SimpleClass> generateAllViewModels(List<File> layoutFiles) {
        layoutFiles.forEach(this::genViewModelFromLayoutFile);
        return mViewModelMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    private void genViewModelFromLayoutFile(File layoutFile) {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try {
            XMLStreamReader reader = inputFactory.createXMLStreamReader(new FileReader(layoutFile));
            // classes witch in one layout file
            List<SimpleClass> classes = new ArrayList<>();

            while (reader.hasNext()) {
                switch (reader.next()) {
                    case XMLEvent.START_ELEMENT:
                        final QName qName = reader.getName(); // <layout>,<data>,<variable><TextView>...
                        if (isSystemName(qName.toString())) {
                            continue;
                        }

                        if (qName.toString().equals("variable")) {
                            String ignore = reader.getAttributeValue(null, "ignore");
                            if (ignore != null && ignore.equals("true")) {
                                continue;
                            }
                            genClassObj(reader, classes);
                        } else {
                            genClassFields(reader, classes);
                        }
                        break;
                    case XMLEvent.END_DOCUMENT:
                        genClassBody(classes);
                        break;
                }
            }
            reader.close();
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * <variable name="user" type="org.kale.vm.UserViewModel"/>
     */
    private void genClassObj(XMLStreamReader xmlReader, List<SimpleClass> classes) {
        final String fullClsName = xmlReader.getAttributeValue(null, "type").trim(); // org.kale.vm.UserViewModel
        SimpleClass cls = mViewModelMap.get(fullClsName);
        if (cls == null) {
            cls = new SimpleClass(fullClsName);
            mViewModelMap.put(fullClsName, cls);
        }
        cls.clsVarName = xmlReader.getAttributeValue(null, "name").trim();

        if (!classes.contains(cls)) {
            classes.add(cls);
        }
    }

    /**
     * <TextView
     * android:layout_width="match_parent"
     * android:layout_height="match_parent"
     * android:text="@{user.name , default kale}"/>
     */
    private void genClassFields(XMLStreamReader xmlReader, List<SimpleClass> classes) {
        for (int i = 0; i < xmlReader.getAttributeCount(); i++) {
            QName attrName = xmlReader.getAttributeName(i);
            if (!attrName.getPrefix().equals("android") && !attrName.getPrefix().equals("app")) {
                // ignore "tools" and other prefix
                continue;
            }

            String attrValue = xmlReader.getAttributeValue(i).trim(); // @{user.name , default kale}
            if (attrValue.startsWith("@{") && attrValue.endsWith("}")) {
                // @{user.name , default kale} -> user.name , default kale
                String content = LetterUtil.getSubString(attrValue, "@{", "}").trim();
                genOneField(classes, attrName.getLocalPart(), content);
            }
        }
    }

    /**
     * text="@{user.name , default kale}"
     *
     * @param attrStr   text
     * @param content user.name , default kale
     */
    private void genOneField(List<SimpleClass> classes, String attrStr, String content) {
        classes.forEach(clz -> {
            String clzName = clz.clsVarName + "."; // user.
            if (content.contains(clzName)) {
                String fieldName = LetterUtil.getSubString(content, clzName).split(" ")[0]; // name

                if (!containsField(clz, fieldName)) {
                    String value = TypeFinder.findTypeByAttrName(attrStr);
                    clz.fields.add(new SimpleField(value, fieldName));
                }
            }
        });
    }

    private void genClassBody(List<SimpleClass> classes) {
        final StringBuilder sb = new StringBuilder();

        classes.forEach(clz -> {
            clz.fields.forEach(field ->
                    sb.append(CodeTemple.FIELD_TEMPLE
                            .replaceAll(CodeTemple.TYPE, field.type)
                            .replaceAll(CodeTemple.UP_FIELD, LetterUtil.getUpperLetter(field.name))
                            .replaceAll(CodeTemple.FIELD, field.name)));

            clz.content = String.format(CodeTemple.CLASS_TEMPLE, clz.packageName,
                    LetterUtil.getUpperLetter(clz.simpleName), sb.toString());
            sb.delete(0, sb.length());
        });
    }

    private boolean containsField(SimpleClass cls, String fieldName) {
        for (SimpleField field : cls.fields) {
            if (field.name.equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSystemName(String src) {
        for (String s : SYSTEM_NAME) {
            if (src.equals(s)) {
                return true;
            }
        }
        return false;
    }

}
