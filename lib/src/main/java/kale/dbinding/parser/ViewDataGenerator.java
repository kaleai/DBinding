package kale.dbinding.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ViewDataGenerator {

    public static final String[] SYSTEM_NAME = {"layout", "data"};

    /**
     * [class full name, class]
     */
    private final Map<String, SimpleClass> mViewDataMap = new HashMap<>();

    public List<SimpleClass> generateAllViewData(List<File> xmlFiles) {
        for (File file : xmlFiles) {
            genViewDataFromLayout(file);
        }

        List<SimpleClass> list = new ArrayList<>();
        for (Map.Entry<String, SimpleClass> entry : mViewDataMap.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    private void genViewDataFromLayout(File xmlFile) {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try {
            FileReader reader = new FileReader(xmlFile);
            XMLStreamReader xmlReader = inputFactory.createXMLStreamReader(reader);
            List<SimpleClass> currentClasses = new ArrayList<>();

            while (xmlReader.hasNext()) {
                Integer eventType = xmlReader.next();
                switch (eventType) {
                    case XMLEvent.START_ELEMENT:
                        final QName qName = xmlReader.getName();
                        if (isSystemName(qName.toString())) {
                            continue;
                        }

                        if (qName.toString().equals("variable")) {
                            String ignore = xmlReader.getAttributeValue(null, "ignore");
                            if (ignore != null && ignore.equals("true")) {
                                continue;
                            }
                            initClassName(xmlReader, currentClasses);
                        } else {
                            initClassFields(xmlReader, currentClasses);
                        }
                        break;
                    case XMLEvent.END_DOCUMENT:
                        initClassContent(currentClasses);
                        break;
                }
            }
            xmlReader.close();
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initClassName(XMLStreamReader xmlReader, List<SimpleClass> classes) {
        final String fullClsName = xmlReader.getAttributeValue(null, "type").trim();
        SimpleClass cls = mViewDataMap.get(fullClsName);
        if (cls == null) {
            cls = new SimpleClass(fullClsName);
            mViewDataMap.put(fullClsName, cls);
        }
        cls.clsVarName = xmlReader.getAttributeValue(null, "name").trim();

        if (!classes.contains(cls)) {
            classes.add(cls);
        }
    }

    private void initClassFields(XMLStreamReader xmlReader, List<SimpleClass> currentClasses) {
        // Views
        //System.out.println("======name = " + xmlReader.getName());
        QName attrName;
        String attrValue;
        for (int i = 0; i < xmlReader.getAttributeCount(); i++) {
            attrName = xmlReader.getAttributeName(i);
            if (!attrName.getPrefix().equals("android") && !attrName.getPrefix().equals("app")) {
                continue;
            }

            // Example: @{viewData.text , default "31"}
            attrValue = xmlReader.getAttributeValue(i).trim();
            if (attrValue.startsWith("@{") && attrValue.endsWith("}")) {
                for (SimpleClass cls : currentClasses) {
                    String clsName = cls.clsVarName + "."; // Result: viewData.
                    String content = attrValue.substring(2).split("}")[0].trim(); // Result: viewData.text , default "31"
                    int index = content.indexOf(clsName);
                    if (index != -1) {
                        String fieldName = content.substring(index + clsName.length()).split(" ")[0];

                        if (!hasThisField(cls, fieldName)) {
                            String value = TypeFinder.findTypeByAttrName(attrName.getLocalPart());
                            cls.fields.add(new SimpleField(value, fieldName));
                        }
                    }
                }
            }
        }
    }

    private void initClassContent(List<SimpleClass> currentClasses) {
        final StringBuilder sb = new StringBuilder();
        for (SimpleClass cls : currentClasses) {
            for (SimpleField field : cls.fields) {
                sb.append(CodeTemple.FIELD_TEMPLE
                        .replaceAll(CodeTemple.TYPE, field.type)
                        .replaceAll(CodeTemple.UP_FIELD, LetterUtil.getUpperLetter(field.name))
                        .replaceAll(CodeTemple.FIELD, field.name));
            }
            cls.content = String.format(CodeTemple.CLASS_TEMPLE,
                    cls.packageName,
                    LetterUtil.getUpperLetter(cls.simpleName), sb.toString());
            sb.delete(0, sb.length());
        }
    }

    private boolean hasThisField(SimpleClass cls, String fieldName) {
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
