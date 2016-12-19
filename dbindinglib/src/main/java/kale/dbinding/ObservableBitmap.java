package kale.dbinding;

import android.databinding.ObservableField;
import android.graphics.Bitmap;

/**
 * @author Kale
 * @date 2016/12/19
 */
public class ObservableBitmap extends ObservableField<Bitmap> {

    public ObservableBitmap(Bitmap value) {
        super(value);
    }

    public ObservableBitmap() {
    }

    public static ObservableBitmap create(Bitmap bitmap) {
        return new ObservableBitmap(bitmap);
    }
}
