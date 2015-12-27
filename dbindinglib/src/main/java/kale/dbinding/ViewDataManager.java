package kale.dbinding;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kale
 * @date 2015/12/24
 */
public class ViewDataManager {

    private final Map<String, IViewData> viewDataMap = new HashMap<>();

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
    public
    @NonNull
    <VD extends IViewData> VD getViewData(Class<VD> cls, Object reference) {
        String key = cls.getCanonicalName();
        IViewData vd = viewDataMap.get(key);
        if (vd == null) {
            try {
                vd = cls.newInstance();
                viewDataMap.put(key, vd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        addViewDataReference(vd, reference);
        return (VD) vd;
    }

    public <VD extends IViewData> VD putViewData(@NonNull VD viewData, @NonNull Object reference) {
        String key = viewData.getClass().getCanonicalName();
        IViewData vd = viewDataMap.get(key);
        if (vd == null) {
            viewDataMap.put(viewData.getClass().getCanonicalName(), viewData);
            vd = viewData;
        }
        addViewDataReference(vd, reference);
        return (VD) vd;
    }

    private void addViewDataReference(IViewData viewData, Object reference) {
        if (!viewData.getReferenceList().contains(reference)) {
            viewData.getReferenceList().add(reference);
        }
    }

    public void removeViewData(@NonNull Object reference, @NonNull List<IViewData> viewDataList) {
        for (IViewData vd : viewDataList) {
            vd.getReferenceList().remove(reference);
            if (vd.getReferenceList().size() == 0) {
                viewDataMap.remove(vd.getClass().getCanonicalName());
            }
        }
    }
    
    public void removeViewData(@NonNull Object reference, @NonNull IViewData... viewDataArr) {
        for (IViewData vd : viewDataArr) {
            vd.getReferenceList().remove(reference);
            if (vd.getReferenceList().size() == 0) {
                viewDataMap.remove(vd.getClass().getCanonicalName());
            }
        }
    }

    public
    @NonNull
    Map<String, IViewData> getViewDataMap() {
        return viewDataMap;
    }
}
