package kale.dbinding.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kale
 * @date 2015/12/25
 */
public class FileHelper {
    
    final static String XML_FILE_PATH = "src" + File.separator
            + "main" + File.separator
            + "res" + File.separator
            + "layout";

    public static final String CLASS_SAVE_PATH = "src" + File.separator
            + "debug" + File.separator
            + "java" + File.separator;

    public static List<File> loadXmlFiles(String moduleDir) {
        File dir = new File(moduleDir + XML_FILE_PATH);
        ArrayList<File> files = new ArrayList<>();
        try {
            listFiles(dir, files, ".xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public static void createFile(String moduleDir, String dirName, String fileName, String content) throws IOException {
        dirName = moduleDir + CLASS_SAVE_PATH + dirName;
        fileName = getUpFieldName(fileName);
        File dir = new File(dirName);
        boolean b = dir.mkdirs();
        File file = new File(dirName, fileName + ".java");
        OutputStream output = new FileOutputStream(file);
        output.write(content.getBytes());
        output.flush();
        output.close();
    }

    public static void deleteAllFile(String moduleDir) {
        File dir = new File(moduleDir + CLASS_SAVE_PATH);
        deleteDir(dir);
    }

    ///////////////////////////////////////////////////////////////////////////
    // utils
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 遍历指定目录及子目录下的文件
     */
    public static void listFiles(File file, List<File> collector, String extension) throws IOException {
        if (file.isFile() && !extension.isEmpty() && file.getName().endsWith(extension)) {
            collector.add(file);
        }
        if ((!file.isHidden() && file.isDirectory())) {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                for (File subFile : subFiles) {
                    listFiles(subFile, collector, extension);
                }
            }
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    
    private static String getUpFieldName(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }


}
