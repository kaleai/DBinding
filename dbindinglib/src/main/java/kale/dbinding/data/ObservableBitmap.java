package kale.dbinding.data;

import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import kale.dbinding.ViewModelStorage;

/**
 * @author Kale
 * @date 2016/12/19
 */
public class ObservableBitmap extends ObservableField<Bitmap> implements Parcelable {

    public ObservableBitmap() {
        super();
    }

    public ObservableBitmap(Bitmap value) {
        super(value);
    }
    
    public static ObservableBitmap create(Bitmap bitmap) {
        return new ObservableBitmap(bitmap);
    }

    public static ObservableBitmap create() {
        return new ObservableBitmap();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        int index = ViewModelStorage.getInstance().putViewModel(this);
        dest.writeInt(index);
    }

    public static final Creator<ObservableBitmap> CREATOR = new Creator<ObservableBitmap>() {
        @Override
        public ObservableBitmap createFromParcel(Parcel source) {
            int index = source.readInt();
            return (ObservableBitmap) ViewModelStorage.getInstance().getViewModel(index);
        }

        @Override
        public ObservableBitmap[] newArray(int size) {
            return new ObservableBitmap[size];
        }
    };
}
