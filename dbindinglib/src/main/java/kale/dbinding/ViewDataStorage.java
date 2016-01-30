package kale.dbinding;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kale
 * @date 2016/1/22
 */
class ViewDataStorage {

    /**
     * 调用的类，类中的vd
     * [current class, viewData]
     */
    private final List<BaseViewData> mDataList = new ArrayList<>();

    private static ViewDataStorage instance;

    public static ViewDataStorage getInstance() {
        if (instance == null) {
            instance = new ViewDataStorage();
        }
        return instance;
    }

    private ViewDataStorage() {
        
    }

    public
    @NonNull
    @CheckResult
    List<BaseViewData> getDataList() {
        return mDataList;
    }

    public int putViewData(@NonNull BaseViewData viewData) {
        int index = mDataList.size();
        mDataList.add(viewData);
        return index;
    }

    public
    @Nullable
    BaseViewData removeViewData(int key) {
        return mDataList.remove(key);
    }
}
