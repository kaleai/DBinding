package kale.dbinding;

import android.databinding.Observable;
/**
 * @author Kale
 * @date 2016/1/14
 */
/**
 * 当viewData中的某个变量改变后会调用此回调类
 * @param <T> Type of ViewData
 */
public abstract class OnValueChangedCallback<T> extends Observable.OnPropertyChangedCallback {

    @Override
    public void onPropertyChanged(Observable sender, int propertyId) {
        afterChanged(propertyId, (T) sender);
    }

    /**
     * Called upon a change of the current value.
     *
     * @param viewData Current viewData contained new values
     * @param valueId  The value ID from BR
     */
    protected abstract void afterChanged(int valueId, T viewData);
}