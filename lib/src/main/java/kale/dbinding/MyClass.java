package kale.dbinding;

import java.io.File;
import java.io.IOException;
import java.util.List;

import kale.dbinding.parser.FileHelper;
import kale.dbinding.parser.ViewDataGenerator;

public class MyClass {

    public static void main(String[] args) {
        FileHelper.deleteAllFile();
        ViewDataGenerator generator = new ViewDataGenerator();
        List<File> files = FileHelper.loadXmlFiles();
        List<kale.dbinding.model.SimpleClass> classes = generator.generateClasses(files);

        final String clsPath = FileHelper.CLASS_SAVE_PATH;
        final StringBuilder sb = new StringBuilder();
        for (kale.dbinding.model.SimpleClass cls : classes) {
            final String[] packageNames = cls.packageName.split("\\.");
            for (String name : packageNames) {
                sb.append(name).append(File.separator);
            }

            try {
                FileHelper.createFile(clsPath + sb.toString(), cls.simpleName, cls.content);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            sb.delete(0, sb.length());
        }
    }
}
