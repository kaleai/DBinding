package kale.dbinding.parser;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ListAdapter;

import java.util.HashMap;
import java.util.Map;

import kale.dbinding.annotation.Visibility;

/**
 * @author Kale
 * @date 2016/1/20
 *
 */
public class TypeFinder {

    /**
     * Example:
     * First String:    text
     * Second String:   CharSequence
     */
    private static Map<String,String> attrTypeMap = new HashMap<>();

    public static String findTypeByAttrName(String name) {
        // find value in custom config
        String type = attrTypeMap.get(name);
        if (type != null) {
            return type;
        }
        
        // find value in default config
        switch (name) {
            case "text":
                type = CharSequence.class.getCanonicalName();
                break;
            case "visibility":
                type = "@" + Visibility.class.getCanonicalName() + " " + int.class.getCanonicalName();
                break;
            case "src":
            case "drawableLeft":
                type = Bitmap.class.getCanonicalName();
                break;
            case "listAdapter":
                type = ListAdapter.class.getCanonicalName();
                break;
            case "pagerAdapter":
                type = PagerAdapter.class.getCanonicalName();
                break;
            case "rcvAdapter":
                type = RecyclerView.Adapter.class.getCanonicalName();
                break;
            default:
                // not support
                type = Object.class.getCanonicalName();
        }
        return type;
    }

    public static void setCustomAttrConfig(Map<String,String> config) {
        attrTypeMap = config;
    }
}
