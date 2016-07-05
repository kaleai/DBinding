package kale.dbinding.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Kale
 * @date 2016/4/12
 */
public class LetterUtilTest {

    @Test
    public void testGetUpperLetter() throws Exception {
        String str;
        str = LetterUtil.getUpperLetter("abc");
        assertEquals("Abc", str);

        str= LetterUtil.getUpperLetter("aBc");
        assertEquals("ABc", str);

        str = LetterUtil.getUpperLetter(null);
        assertNull(str);

        str = LetterUtil.getUpperLetter("");
        assertEquals("", str);
    }

    @Test
    public void testGetLowerLetter() throws Exception {
        String str;
        str = LetterUtil.getLowerLetter("abc");
        assertEquals("abc", str);

        str= LetterUtil.getLowerLetter("aBc");
        assertEquals("aBc", str);

        str= LetterUtil.getLowerLetter("ABc");
        assertEquals("aBc", str);

        str = LetterUtil.getLowerLetter(null);
        assertNull(str);

        str = LetterUtil.getLowerLetter("");
        assertEquals("", str);
    }

    @Test
    public void testGetSubString() throws Exception {
    }

}