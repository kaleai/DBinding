package kale.dbinding.data;

import java.io.Serializable;

import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;

import kale.dbinding.ViewModelStorage;

/**
 * @author Kale
 * @date 2016/12/19
 */
public class ObservableCharSequence extends ObservableField<CharSequence> implements Parcelable, Serializable {

    public ObservableCharSequence() {
        super();
    }

    public ObservableCharSequence(CharSequence value) {
        super(value);
    }

    public static ObservableCharSequence create() {
        return new ObservableCharSequence();
    }

    public static ObservableCharSequence create(CharSequence value) {
        return new ObservableCharSequence(value);
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

    public static final Parcelable.Creator<ObservableCharSequence> CREATOR
            = new Parcelable.Creator<ObservableCharSequence>() {

        @Override
        public ObservableCharSequence createFromParcel(Parcel source) {
            int index = source.readInt();
            return (ObservableCharSequence) ViewModelStorage.getInstance().getViewModel(index);
        }

        @Override
        public ObservableCharSequence[] newArray(int size) {
            return new ObservableCharSequence[size];
        }
    };
}
