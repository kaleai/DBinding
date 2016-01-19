package kale.dbinding;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import kale.dbinding.parser.CodeTemple;
import kale.dbinding.parser.FileHelper;
import kale.dbinding.parser.ViewDataGenerator;

public class GenViewData {

    private static final String ROOT = System.getProperty("user.dir");
    
    
    public static void main(String[] args) {
        System.out.println("=========== start ===========");
        long start = System.currentTimeMillis();

        generateViewData(ROOT + File.separator + "app" + File.separator, CodeTemple.ATTR_FIELD_MAP);

        System.out.println("========== end =========== " + (System.currentTimeMillis() - start));
    }

    public static void generateViewData(String moduleDir, Map<String, String> attrFieldMap) {
        //FileHelper.deleteAllFile(moduleDir);
        ViewDataGenerator generator = new ViewDataGenerator(attrFieldMap);
        List<File> files = FileHelper.loadXmlFiles(moduleDir);
        System.out.println("size = " + files.size());

        List<kale.dbinding.model.SimpleClass> classes = generator.generateClasses(files);

        final StringBuilder sb = new StringBuilder();
        for (kale.dbinding.model.SimpleClass cls : classes) {
            final String[] packageNames = cls.packageName.split("\\.");
            for (String name : packageNames) {
                sb.append(name).append(File.separator);
            }

            try {
                FileHelper.createFile(moduleDir, sb.toString(), cls.simpleName, cls.content);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            sb.delete(0, sb.length());
        }
    }
}
