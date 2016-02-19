package kale.dbinding;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * @author Kale
 * @date 2016/1/9
 */
public abstract class BaseViewModel extends BaseObservable {

    private static ViewModelStorage mManager = ViewModelStorage.getInstance();

    /**
     * convert index to String
     */
    public Serializable toSerializable() {
        int index = mManager.putViewModel(this);
        return String.valueOf(index);
    }

    /**
     * Find ViewModel By String
     */
    public static
    @Nullable
    <T extends BaseViewModel> T toViewModel(Serializable value) {
        BaseViewModel data = mManager.removeViewModel(Integer.valueOf(((String) value)));
        return (T) data;
    }


    public synchronized Observable.OnPropertyChangedCallback addOnValueChangedCallback(final OnValueChangedCallback callback) {
        Observable.OnPropertyChangedCallback cb = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                callback.onChanged(propertyId);
            }
        };
        addOnPropertyChangedCallback(cb);
        return cb;
    }

    public interface OnValueChangedCallback {

        void onChanged(int propertyId);

    }

}
