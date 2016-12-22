package kale.dbinding;

import java.io.Serializable;

import android.support.annotation.Nullable;

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
        return (T) ViewModelStorage.getInstance().getViewModel(index);
    }
}
