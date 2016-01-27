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
class ViewDataManager {

    /**
     * 调用的类，类中的vd
     * [current class, viewData]
     */
    private final List<BaseViewData> mDataList = new ArrayList<>();

    private static ViewDataManager instance;

    public static ViewDataManager getInstance() {
        if (instance == null) {
            instance = new ViewDataManager();
        }
        return instance;
    }

    private ViewDataManager() {
        
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
