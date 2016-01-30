package kale.dbinding.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import kale.dbinding.CodeTemple;

/**
 * @author Kale
 * @date 2015/12/25
 */
public class FileHelper {

    private static final String CONFIG_FILE_PATH = "src" + File.separator
            + "main" + File.separator
            + "res" + File.separator
            + "values";

    private static final String LAYOUT_FILE_PATH = "src" + File.separator
            + "main" + File.separator
            + "res" + File.separator
            + "layout";

    private static final String CLASS_SAVED_PATH = "src" + File.separator
            + "main" + File.separator
            + "java" + File.separator;
    
    public static File loadConfigFile(String moduleDir) {
        File file = new File(moduleDir + CONFIG_FILE_PATH + File.separator, "dbinding_config.xml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                OutputStream output = new FileOutputStream(file);
                output.write(CodeTemple.CONFIG_TEMPLE.getBytes());
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static List<File> loadLayoutFiles(String moduleDir) {
        File dir = new File(moduleDir + LAYOUT_FILE_PATH);
        ArrayList<File> files = new ArrayList<>();
        try {
            getListFiles(dir, files, ".xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public static void createViewDataFile(String moduleDir, String dirName, String fileName, String content) throws IOException {
        dirName = moduleDir + CLASS_SAVED_PATH + dirName;
        fileName = LetterUtil.getUpperLetter(fileName);
        File dir = new File(dirName);
        dir.mkdirs();
        File file = new File(dirName, fileName + ".java");
        OutputStream output = new FileOutputStream(file);
        output.write(content.getBytes());
        output.flush();
        output.close();
    }

    ///////////////////////////////////////////////////////////////////////////
    // utils
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Traverse the specified file directory and subdirectories
     */
    public static void getListFiles(File file, List<File> collector, String extension) throws IOException {
        if (file.isFile() && !extension.isEmpty() && file.getName().endsWith(extension)) {
            collector.add(file);
        }
        if ((!file.isHidden() && file.isDirectory())) {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                for (File subFile : subFiles) {
                    getListFiles(subFile, collector, extension);
                }
            }
        }
    }

    /**
     * delete all viewData in your module
     */
    public static void deleteAllFile(String moduleDir) {
        File dir = new File(moduleDir + CLASS_SAVED_PATH);
        deleteDir(dir);
    }

    /**
     * Delete all files in the directory
     *
     * @param dir file dir
     */
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

}
