package kale.dbinding.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * @author Kale
 * @date 2016/1/5
 */
public class BitmapUtil {

    public static Drawable bitmap2Drawable(Resources res, Bitmap bitmap) {
        return new BitmapDrawable(res, bitmap);
    }
    
}
