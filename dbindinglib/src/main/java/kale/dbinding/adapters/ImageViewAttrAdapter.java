package kale.dbinding.adapters;

import android.databinding.BindingAdapter;
import android.databinding.adapters.ImageViewBindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * @author Kale
 * @date 2016/1/5
 * {@link ImageView}
 */
public class ImageViewAttrAdapter extends ImageViewBindingAdapter {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }
}
