package kale.dbinding;

import android.databinding.BaseObservable;
import android.databinding.Observable;

/**
 * @author Kale
 * @date 2016/1/9
 */
public abstract class BaseViewModel<T extends BaseViewModel> extends BaseObservable {

    /**
     * convert index to {@link SerializableViewModel}
     */
    public SerializableViewModel<T> toSerializable() {
        int index = ViewModelStorage.getInstance().putViewModel(this);
        return new SerializableViewModel<>(index);
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
