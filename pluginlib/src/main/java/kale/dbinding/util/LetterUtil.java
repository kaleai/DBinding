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
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * make first letter to lowercase
     * Abc -> abc
     */
    public static String getLowerLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * {kale} - > kale
     */
    public static String getSubString(String originStr, String begin) {
        int beginIndex = originStr.indexOf(begin) + begin.length();
        return originStr.substring(beginIndex);
    }

    /**
     * -kale -> kale
     */
    public static String getSubString(String originStr, String begin, String end) {
        int beginIndex = originStr.indexOf(begin) + begin.length();
        int endIndex = originStr.indexOf(end);
        return originStr.substring(beginIndex, endIndex);
    }
}
