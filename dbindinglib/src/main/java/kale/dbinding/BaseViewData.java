package kale.dbinding;

import android.databinding.BaseObservable;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * @author Kale
 * @date 2016/1/9
 */
public abstract class BaseViewData extends BaseObservable {
    
    private static ViewDataManager mManager = ViewDataManager.getInstance();

    /**
     * convert index to String
     */
    public Serializable toSerializable() {
        int index = mManager.putViewData(this);
        return String.valueOf(index);
    }

    /**
     * Find ViewData By String
     * @return ViewData from {@link ViewDataManager}
     */
    public static 
    @Nullable
    <T extends BaseViewData> T toViewData(Serializable value) {
        BaseViewData data = mManager.removeViewData(Integer.valueOf(((String) value)));
        return (T) data;
    }
    
}
