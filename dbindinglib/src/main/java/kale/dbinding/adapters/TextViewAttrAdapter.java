package kale.dbinding.adapters;

import android.databinding.BindingAdapter;
import android.databinding.adapters.TextViewBindingAdapter;
import android.graphics.Bitmap;
import android.widget.TextView;

import kale.dbinding.data.ObservableBitmap;
import kale.dbinding.data.ObservableCharSequence;
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

    @BindingAdapter("android:drawableLeft")
    public static void setDrawableLeft(TextView view, ObservableBitmap bitmap) {
        setDrawableLeft(view, bitmap.get());
    }
    
    @BindingAdapter("android:drawableRight")
    public static void setDrawableRight(TextView view, Bitmap bitmap) {
        view.setCompoundDrawablesWithIntrinsicBounds(null, null,
                BitmapUtil.bitmap2Drawable(view.getResources(), bitmap), null);
    }

    @BindingAdapter("android:text")
    public static void setText(TextView view, ObservableCharSequence text) {
        TextViewBindingAdapter.setText(view, text.get());
    }

   /* *//**
     * 虽然这个属性，在view中没有，但是通过这样的方式是可以直接被绑定的。只不过会有个警告
     *//*
    @BindingAdapter("app:smartText")
    public static void setSmartText(TextView view, CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            view.setText(text);
            view.setBackgroundColor(0xffff0000);
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        } else {
            view.setVisibility(View.GONE);
        }
    }*/
}
