package kale.db;

import android.support.annotation.Nullable;

import java.util.Observable;

/**
 * @author Kale
 * @date 2016/1/8
 */
public class ObservableValue<T> extends Observable{

    private T mValue;

    public ObservableValue(T value) {
        mValue = value;
    }


    /**
     * @return the stored value.
     */
    public @Nullable T get() {
        return mValue;
    }

    /**
     * Set the stored value.
     */
    public void set(T value) {
        if (value != mValue) {
            mValue = value;
            notifyObservers(mValue);
        }
    }

}
