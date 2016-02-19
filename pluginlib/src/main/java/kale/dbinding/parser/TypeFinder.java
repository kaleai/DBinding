package kale.dbinding.parser;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kale
 * @date 2016/1/20
 */
public class TypeFinder {

    /**
     * Example:
     * First String:    text
     * Second String:   CharSequence
     */
    private static Map<String, String> attrTypeMap = new HashMap<>();

    public static String findTypeByAttrName(String name) {
        // 1.find value in custom config
        String type = attrTypeMap.get(name);
        if (type != null) {
            return type;
        }

        // 2.find value in default config
        switch (name) {
            case "text":
                return CharSequence.class.getCanonicalName();
            case "visibility":
                return int.class.getCanonicalName();
            case "src":
            case "drawableLeft":
                return Bitmap.class.getCanonicalName();
            case "listAdapter":
                return ListAdapter.class.getCanonicalName();
            case "pagerAdapter":
                return PagerAdapter.class.getCanonicalName();
            case "rcvAdapter":
                return RecyclerView.Adapter.class.getCanonicalName();

            // events
            case "onClick":
                return View.OnClickListener.class.getCanonicalName();
            case "onLongClick":
                return View.OnLongClickListener.class.getCanonicalName();
            default:
                // not support
                return Object.class.getCanonicalName();
        }
    }

    public static void setCustomAttrConfig(Map<String, String> config) {
        attrTypeMap = config;
    }
}
