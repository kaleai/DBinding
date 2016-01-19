package kale.dbinding;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kale.dbinding.util.ReflectUtil;

/**
 * @author Kale
 * @date 2015/12/24
 */
public class ViewDataManager {

    /**
     * 调用的类，类中的vd
     * [current class, viewData]
     */
    private final Map<Class<?>, BaseViewData> mViewDataMap = new HashMap<>();

    private static ViewDataManager instance;

    public static ViewDataManager getInstance() {
        if (instance == null) {
            instance = new ViewDataManager();
        }
        return instance;
    }

    private ViewDataManager() {

    }

    @CheckResult
    public boolean containsViewData(Class clz) {
        return mViewDataMap.containsKey(clz);
    }
    
    
    Map<Class<?>,List<BaseViewData>> ddd;
    
    // 不能根据clz来判断销毁，因为可能存在多个页面复用的情况。应该根据当前页面的对象做销毁对应viewdata的操作。
    public void removeVD(Class clz,Object reference) {
        for (BaseViewData viewData : ddd.get(clz)) {
            viewData.removeReference(reference);
            if (!viewData.hasReference()) {
                ddd.get(clz).remove(viewData);
                if (ddd.get(clz).size() == 0) {
                    ddd.remove(clz);
                }
            }
        }
    } 

    public ViewDataManager putViewData(Class clz, BaseViewData viewData,Object reference) {
        mViewDataMap.put(clz, viewData); // 会存在页面复用的情况和更新vd的情况，所以每次put都执行。
        // 一个类中可能会跳转多个页面，多个页面可能都要接收同一个vd，而同一个vd只需要持有一个引用即可
        if (!viewData.getReferenceList().contains(reference)) {
            viewData.addReference(reference);
        }
        return instance;
    }

    public
    @Nullable
    <VD extends BaseViewData> VD getViewData(Class clz) {
        return (VD) mViewDataMap.get(clz);
    }
    
    /**
     * 如果是通过这个方法得到viewData，先从map中找。
     * 无论是new的还是找到了，都增加引用。
     * 会不会有同一个类引用两个相同对象的情况？会的。
     * 需要担心么？因为是map，所以同一个对象，仅仅存放一次。但会add两次引用，这里做了个处理
     * 在remove的时候，如果datalist多了，remove会返回null，但不会出问题。
     */
    @CheckResult
    public
    @NonNull
    <VD extends BaseViewData> VD getViewData(Class<VD> cls, Object reference) {
        BaseViewData vd = mViewDataMap.get(cls.getCanonicalName());
        if (vd == null) {
            vd = ReflectUtil.newInstance(cls);
            vd.addReference(reference);
            mViewDataMap.put(vd.getClass().getCanonicalName(), vd);
        } else {
            if (vd.getReferenceList().contains(reference)) {
                throw new RuntimeException(reference.getClass().getSimpleName()
                        + " contains multiple " + vd.getClass().getSimpleName()
                        + ",you should define viewData at once");
            }
        }
        return (VD) vd;
    }

    public void removeViewData(@NonNull List<BaseViewData> viewDataList, @NonNull Object reference) {
        for (BaseViewData vd : viewDataList) {
            vd.removeReference(reference);
            if (!vd.hasReference()) {
                mViewDataMap.remove(vd.getClass().getCanonicalName());
            }
        }
    }

    public void removeViewData(@NonNull Object reference, @NonNull BaseViewData... viewDataArr) {
        ArrayList<BaseViewData> data = new ArrayList<>(Arrays.asList(viewDataArr));
        removeViewData(data, reference);
    }

    public
    @NonNull
    Map<String, BaseViewData> getViewDataMap() {
        return mViewDataMap;
    }
}
