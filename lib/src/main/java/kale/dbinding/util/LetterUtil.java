package kale.dbinding.util;

/**
 * @author Kale
 * @date 2016/1/20
 */
public class LetterUtil {

    /**
     * make first letter to uppercase
     * abc -> Abc
     */
    public static String getUpperLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * make first letter to lowercase
     * Abc -> abc
     */
    public static String getLowerLetter(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
