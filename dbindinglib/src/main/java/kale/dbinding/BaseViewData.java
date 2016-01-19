package kale.dbinding;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kale
 * @date 2016/1/9
 */
public abstract class BaseViewData<T> extends BaseObservable implements Serializable{

    private static final String TAG = "BaseViewData";

    @NonNull
    private final List<Object> referenceList;

    /**
     * [reference class, onValueChangedCb list]
     */
    @NonNull
    private final Map<Class, List<OnValueChangedCallback>> mValueChangedCbs;

    public BaseViewData() {
        mValueChangedCbs = new android.support.v4.util.ArrayMap<>();
        referenceList = new ArrayList<>();
    }

    public void addReference(@Nullable Object reference) {
        if (reference != null && !referenceList.contains(reference)) {
            referenceList.add(reference);
        }
    }

    public void removeReference(@Nullable Object reference) {
        if (reference != null) {
            referenceList.remove(reference);

            List<OnValueChangedCallback> callbacks = mValueChangedCbs.remove(reference.getClass());
            if (callbacks != null) {
                for (OnValueChangedCallback callback : callbacks) {
                    removeOnPropertyChangedCallback(callback);
                }
            }
        }
    }

    @NonNull
    public List<Object> getReferenceList() {
        return referenceList;
    }

    public boolean hasReference() {
        return !referenceList.isEmpty();
    }
    
    /**
     * 同一个类也可能有多次的监听
     * @param callback
     */
    public void addOnValueChangedCallback(Class<?> clz ,@Nullable OnValueChangedCallback<T> callback) {
        if (callback == null) {
            Log.w(TAG, "Current callback is null");
            return;
        }
        
        if (mValueChangedCbs.containsKey(clz)) {
            mValueChangedCbs.get(clz).add(callback);
        } else {
            ArrayList<OnValueChangedCallback> list = new ArrayList<>();
            list.add(callback);
            mValueChangedCbs.put(clz, list);
        }
        addOnPropertyChangedCallback(callback);
    }

    @Deprecated
    @Override
    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        super.addOnPropertyChangedCallback(callback);
    }

    @NonNull
    public Map<Class, List<OnValueChangedCallback>> getValueChangedCbs() {
        return mValueChangedCbs;
    }
}
