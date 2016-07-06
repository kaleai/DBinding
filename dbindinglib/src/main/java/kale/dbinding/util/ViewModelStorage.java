package kale.dbinding.util;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import kale.dbinding.BaseViewModel;

/**
 * @author Kale
 * @date 2016/1/22
 */
public class ViewModelStorage {

    /**
     * 调用的类，类中的vd
     * [current class, viewModel]
     */
    private final List<BaseViewModel> mDataList = new ArrayList<>();

    private static ViewModelStorage instance;

    public static ViewModelStorage getInstance() {
        if (instance == null) {
            instance = new ViewModelStorage();
        }
        return instance;
    }

    private ViewModelStorage() {
        
    }

    public
    @NonNull
    @CheckResult
    List<BaseViewModel> getDataList() {
        return mDataList;
    }

    public int putViewModel(@NonNull BaseViewModel viewModel) {
        int index = mDataList.size();
        mDataList.add(viewModel);
        return index;
    }

    public
    @Nullable
    BaseViewModel removeViewModel(int key) {
        if (mDataList.size() == 0) {
            return null;
        } else {
            return mDataList.remove(key);
        } 
    }
}
