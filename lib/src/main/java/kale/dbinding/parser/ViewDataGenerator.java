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

/**
 * @author Kale
 * @date 2015/12/25
 */
public class ViewDataGenerator {

    public static final String[] SYSTEM_NAME = {"layout", "data"};

    /**
     * [class full name, class]
     */
    private final Map<String, kale.dbinding.model.SimpleClass> mClassesMap = new HashMap<>();

    public ViewDataGenerator() {
    }

    public List<kale.dbinding.model.SimpleClass> generateClasses(List<File> xmlFiles) {
        for (File file : xmlFiles) {
            generateOneClass(file);
        }
        List<kale.dbinding.model.SimpleClass> list = new ArrayList<>();
        for (Map.Entry<String, kale.dbinding.model.SimpleClass> entry : mClassesMap.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    private void generateOneClass(File xmlFile) {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try {
            FileReader reader = new FileReader(xmlFile);
            XMLStreamReader xmlReader = inputFactory.createXMLStreamReader(reader);
            List<kale.dbinding.model.SimpleClass> currentClasses = new ArrayList<>();

            while (xmlReader.hasNext()) {
                Integer eventType = xmlReader.next();
                switch (eventType) {
                    case XMLEvent.START_ELEMENT:
                        final QName qName = xmlReader.getName();
                        if (isSystemName(qName.toString())) {
                            continue;
                        }

                        if (qName.toString().equals("variable")) {
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

    private void initClassName(XMLStreamReader xmlReader, List<kale.dbinding.model.SimpleClass> classes) {
        kale.dbinding.model.SimpleClass cls;
        if (xmlReader.getAttributeLocalName(0).equals("name")
                || xmlReader.getAttributeLocalName(0).equals("type")) {

            final String fullClsName = xmlReader.getAttributeValue(null, "type").trim();
            if ((cls = mClassesMap.get(fullClsName)) == null) {
                cls = new kale.dbinding.model.SimpleClass(fullClsName);
                mClassesMap.put(fullClsName, cls);
            }
            cls.clsVarName = xmlReader.getAttributeValue(null, "name").trim();
            classes.add(cls);
        }
    }

    private void initClassFields(XMLStreamReader xmlReader, List<kale.dbinding.model.SimpleClass> classes) {
        // Views
        //System.out.println("======name = " + xmlReader.getName());
        QName attrName;
        String attrValue;
        for (int i = 0; i < xmlReader.getAttributeCount(); i++) {
            attrName = xmlReader.getAttributeName(i);
            if (attrName.getPrefix().equals("tools")) {
                continue;
            }

            // @{viewData.text default "31"}
            attrValue = xmlReader.getAttributeValue(i).trim();
            if (attrValue.startsWith("@{") && attrValue.endsWith("}")) {
                for (kale.dbinding.model.SimpleClass cls : classes) {
                    String clsName = cls.clsVarName + "."; // viewData.
                    String content = attrValue.substring(2).split("}")[0].trim(); // viewData.text default "31"
                    int index = content.indexOf(clsName);
                    if (index != -1) {
                        String fieldName = content.substring(index + clsName.length()).split(" ")[0];

                        if (!hasThisField(cls, fieldName)) {
                            cls.fields.add(new kale.dbinding.model.SimpleField(CodeTemple.FIELD_TYPE.get(attrName.getLocalPart()), fieldName));
                        }
                    }
                }
            }
            //System.out.println("attr = " + attrName.getLocalPart() + " attrValue = " + attrValue);
        }
    }


    private List<kale.dbinding.model.SimpleClass> initClassContent(List<kale.dbinding.model.SimpleClass> classes) {
        final StringBuilder sb = new StringBuilder();
        for (kale.dbinding.model.SimpleClass cls : classes) {
            for (kale.dbinding.model.SimpleField field : cls.fields) {
                sb.append(CodeTemple.FIELD_TEMPLE
                        .replaceAll(CodeTemple.TYPE, field.type.getCanonicalName())
                        .replaceAll(CodeTemple.UP_FIELD, getUpLetterName(field.name))
                        .replaceAll(CodeTemple.FIELD, field.name));
            }
            cls.content = String.format(CodeTemple.CLASS_TEMPLE,
                    cls.packageName, getUpLetterName(cls.simpleName), sb.toString());
            sb.delete(0, sb.length());
        }
        return classes;
    }

    private static String getUpLetterName(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private boolean hasThisField(kale.dbinding.model.SimpleClass cls, String fieldName) {
        for (kale.dbinding.model.SimpleField field : cls.fields) {
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
