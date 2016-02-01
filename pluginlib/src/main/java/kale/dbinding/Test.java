package kale.dbinding;

import java.io.File;

/**
 * @author Kale
 * @date 2016/1/31
 */
public class Test {

    ///////////////////////////////////////////////////////////////////////////
    // Just For Test
    ///////////////////////////////////////////////////////////////////////////

    private static final String ROOT = System.getProperty("user.dir");
    
    public static void main(String[] args) {
        System.out.println("=========== start ===========");
        long start = System.currentTimeMillis();
        String moduleDir = ROOT + File.separator + "app" + File.separator;

        GenViewData.generateViewData(moduleDir);

        System.out.println("========== end =========== " + (System.currentTimeMillis() - start));
    }
    
}
