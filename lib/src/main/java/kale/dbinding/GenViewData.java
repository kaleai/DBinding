package kale.dbinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import kale.dbinding.model.SimpleClass;
import kale.dbinding.parser.TypeFinder;
import kale.dbinding.parser.ViewDataGenerator;
import kale.dbinding.util.FileHelper;

public class GenViewData {

    public static void generateViewData(String moduleDir) {
        FileHelper.deleteAllFile(moduleDir);
        // load config
        File config = FileHelper.loadConfigFile(moduleDir);
        TypeFinder.setCustomAttrConfig(getAttrConfig(config));
        
        // load all layout files
        List<File> files = FileHelper.loadLayoutFiles(moduleDir);
        System.out.println("layout file size = " + files.size());
        
        // get classes which will be create
        List<SimpleClass> classes = new ViewDataGenerator().generateAllViewData(files);

        final StringBuilder dir = new StringBuilder();
        for (SimpleClass cls : classes) {
            // First:kale.view.data -> kale/view/data (file dir)
            final String[] packageNames = cls.packageName.split("\\.");
            for (String name : packageNames) {
                dir.append(name).append(File.separator);
            }
            
            // Second:create one class
            try {
                FileHelper.createViewDataFile(moduleDir, dir.toString(), cls.simpleName, cls.content);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            
            // Final clear StringBuilder
            dir.delete(0, dir.length()); // clear StringBuilder
        }
    }

    /**
     * Parse xml file to Map<String, String>
     *     xml: <string name="text">java.lang.CharSequence</string>
     *     map: [text : java.lang.CharSequence]
     */
    public static Map<String, String> getAttrConfig(File xmlFile) {
        Map<String, String> attrValueMap = new HashMap<>();
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try {
            FileReader reader = new FileReader(xmlFile);
            XMLStreamReader xmlReader = inputFactory.createXMLStreamReader(reader);
            while (xmlReader.hasNext()) {
                Integer eventType = xmlReader.next();
                switch (eventType) {
                    case XMLEvent.START_ELEMENT:
                        if (xmlReader.getName().toString().equals("string")) {
                            String attr = xmlReader.getAttributeValue(null, "name");
                            String value = xmlReader.getElementText();
                            attrValueMap.put(attr, value);
                        }
                        break;
                }
            }
            xmlReader.close();
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return attrValueMap;
    }
    
    

    ///////////////////////////////////////////////////////////////////////////
    // Just For Test
    ///////////////////////////////////////////////////////////////////////////

    private static final String ROOT = System.getProperty("user.dir");

    public static void main(String[] args) {
        System.out.println("=========== start ===========");
        long start = System.currentTimeMillis();
        String moduleDir = ROOT + File.separator + "app" + File.separator;

        generateViewData(moduleDir);

        System.out.println("========== end =========== " + (System.currentTimeMillis() - start));
    }
}
