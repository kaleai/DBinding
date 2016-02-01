package kale.dbinding.adapters;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import kale.dbinding.util.BitmapUtil;

/**
 * @author Kale
 * @date 2016/1/5
 * {@link android.databinding.adapters.TextViewBindingAdapter}
 */
public class TextViewAttrAdapter{

    @BindingAdapter("android:drawableTop")
    public static void setDrawableTop(TextView view, Bitmap bitmap) {
        view.setCompoundDrawablesWithIntrinsicBounds(null,
                BitmapUtil.bitmap2Drawable(view.getResources(), bitmap), null, null);
    }

    @BindingAdapter("android:drawableBottom")
    public static void setDrawableBottom(TextView view, Bitmap bitmap) {
        view.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
                BitmapUtil.bitmap2Drawable(view.getResources(), bitmap));
    }
    
    @BindingAdapter("android:drawableLeft")
    public static void setDrawableLeft(TextView view, Bitmap bitmap) {
        view.setCompoundDrawablesWithIntrinsicBounds(
                BitmapUtil.bitmap2Drawable(view.getResources(), bitmap), null, null, null);
    }
    
    @BindingAdapter("android:drawableRight")
    public static void setdrawableRight(TextView view, Bitmap bitmap) {
        view.setCompoundDrawablesWithIntrinsicBounds(null, null,
                BitmapUtil.bitmap2Drawable(view.getResources(), bitmap), null);
    }

    @BindingAdapter("app:smartText")
    public static void setSmartText(TextView view, CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            view.setText(text);
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
