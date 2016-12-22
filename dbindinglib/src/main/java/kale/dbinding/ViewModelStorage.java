package kale.dbinding;

import java.util.Map;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

/**
 * @author Kale
 * @date 2016/1/22
 */
public class ViewModelStorage {

    private static ViewModelStorage instance;

    private final Map<Integer, BaseObservable> mMap = new ArrayMap<>();

    public static ViewModelStorage getInstance() {
        if (instance == null) {
            instance = new ViewModelStorage();
        }
        return instance;
    }

    private ViewModelStorage() {

    }

    Map<Integer, BaseObservable> getMap() {
        return mMap;
    }

    public int putViewModel(@NonNull BaseObservable viewModel) {
        if (mMap.containsValue(viewModel)) {
            for (Map.Entry<Integer, BaseObservable> entry : mMap.entrySet()) {
                if (entry.getValue() == viewModel) {
                    return entry.getKey();
                }
            }
            return 0;
        } else {
            int index = mMap.size();
            mMap.put(index, viewModel);
            return index;
        }
    }

    public
    @Nullable
    BaseObservable getViewModel(int key) {
        if (mMap.size() == 0) {
            return null;
        } else {
            return mMap.remove(key);
        }
    }

    int getRealSize() {
        int size = 0;
        for (Map.Entry<Integer, BaseObservable> entry : mMap.entrySet()) {
            if (entry.getValue() != null) {
                size++;
            }
        }
        return size;
    }
}
