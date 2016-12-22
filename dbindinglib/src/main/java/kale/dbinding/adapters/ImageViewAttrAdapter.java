package kale.dbinding.adapters;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

import kale.dbinding.data.ObservableBitmap;

/**
 * @author Kale
 * @date 2016/1/5
 * {@link android.databinding.adapters.ImageViewBindingAdapter}
 */
public class ImageViewAttrAdapter {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, ObservableBitmap bitmap) {
        view.setImageBitmap(bitmap.get());
    }
}
