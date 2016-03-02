package kale.dbinding.util;

import android.support.annotation.Nullable;

import java.io.Serializable;

import kale.dbinding.BaseViewModel;

/**
 * @author Kale
 * @date 2016/3/2
 */
public class SerializableViewModel<T extends BaseViewModel> implements Serializable {

    private int index;

    public SerializableViewModel(int index) {
        this.index = index;
    }

    public
    @Nullable T toViewModel() {
        return (T) ViewModelStorage.getInstance().removeViewModel(index);
    }
}
