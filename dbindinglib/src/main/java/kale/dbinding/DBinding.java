package kale.dbinding;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.CheckResult;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kale.dbinding.annotation.InjectViewData;
import kale.dbinding.util.ActivityLifecycleCallbacksAdapter;
import kale.dbinding.util.ReflectUtil;

/**
 * @author Kale
 * @date 2015/12/26
 */
public class DBinding {

    private static final String TAG = "DBinding";

//    public static void injectViewData(@NonNull Object target) {
//        injectViewData(target, false);
//    }
    
//    public static void injectViewData(@NonNull final Activity target) {
//        injectViewData(target, target);
//    }
        
    /**
     * If you used {@link InjectViewData} in current class.You can use this method to 
     * create ViewData from {@link ViewDataManager}.
     * @param currentActivity if current class is activity,you should use "this".
     *                 if in fragment you can use {@link Fragment#getActivity()}
     */
    private static void injectViewData(@NonNull Object target, @NonNull final Activity currentActivity) {
        final List<BaseViewData> viewDataList = injectViewData(target, true);
        if (viewDataList.isEmpty()) {
            return;
        }
        
        currentActivity.getApplication().registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksAdapter() {
            @Override
            public void onActivityDestroyed(Activity act) {
                if (act.equals(currentActivity)) {
                    // 界面结束后销毁当前界面的数据对象
                    ViewDataManager.getInstance().removeViewData(viewDataList, currentActivity);
                    currentActivity.getApplication().unregisterActivityLifecycleCallbacks(this);
                }
            }
        });
    }

    /**
     * 可能会有一个对象中有两个相同的vd的存在，这里不做判断，在vdmanager中判断
     * 
     * If you used {@link InjectViewData} in current class.You can use this method to 
     * create ViewData from {@link ViewDataManager}.  
     * 
     * @param target current class.Usually use {@code this}
     * @return The list of ViewData which with {@link InjectViewData#isSingle()} {@code single = true}  
     */
    private static List<BaseViewData> injectViewData(@NonNull Object target, boolean hasActivity) {
        List<BaseViewData> SingleVdList = new ArrayList<>();
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            // find fields which with annotation
            if (field.isAnnotationPresent(InjectViewData.class)
                    && BaseViewData.class.isAssignableFrom(field.getType())) {

                InjectViewData inject = field.getAnnotation(InjectViewData.class);
                boolean isSingle = inject.isSingle();
                if (isSingle && !hasActivity) {
                    throw new IllegalArgumentException("如果是全局唯一的，必须让viewdata和activity相关，来解绑");
                }
                BaseViewData viewData = initViewData(target, field, isSingle);
                ReflectUtil.setFieldValue(target, field, viewData);

                if (isSingle) {
                    SingleVdList.add(viewData);
                }
            }
        }
        
        if (!(target instanceof Activity) && SingleVdList.size() ==0 && hasActivity) {
            throw new IllegalArgumentException("不要传activity");
        }
        return SingleVdList;
    }

    @CheckResult
    @NonNull
    private static BaseViewData initViewData(@NonNull Object obj, Field field, boolean isSingle) {
        BaseViewData viewData;
        Class<BaseViewData> typeClz = (Class<BaseViewData>) field.getType();
        if (isSingle) {
            viewData = ViewDataManager.getInstance().getViewData(typeClz, obj);
        } else {
            viewData = ReflectUtil.newInstance(typeClz);
            viewData.addReference(obj);
        }
        return viewData;
    }

    public static <T extends ViewDataBinding> T setVariables(@NonNull ViewDataBinding binding, @NonNull BaseObservable... vds) {
        /*if (!binding.getClass().getSuperclass().getSimpleName().equals("ViewDataBinding")) {
            throw new IllegalArgumentException("The type of first arg must be ViewDataBinding");
        }*/
        Map<Class, Method> setMethods = new HashMap<>(); // methods cache
        Method[] methods = binding.getClass().getDeclaredMethods(); // public methods
        for (int i = 0, size = methods.length; i < size; i++) {
            Method method = methods[i];
            if (method.getModifiers() == Modifier.PUBLIC
                    && method.getReturnType() == void.class
                    && method.getName().startsWith("set")
                    && method.getParameterTypes().length == 1) {

                setMethods.put(method.getParameterTypes()[0], method);
            }
        }
        
        for (int i = 0,size = vds.length; i < size; i++) {
            if (vds[i] == null) {
                throw new NullPointerException("No." + i + 1 + "viewData is not init");
            }
            try {
                Class vdClz = vds[i].getClass();
                if (setMethods.get(vdClz) != null) {
                    setMethods.get(vdClz).invoke(binding, vds[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) binding;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Bind viewData to View
    ///////////////////////////////////////////////////////////////////////////
    
    public static <T extends ViewDataBinding> T bind(Activity activity, @LayoutRes int layoutId) {
        return DataBindingUtil.setContentView(activity, layoutId);
    }

    public static <T extends ViewDataBinding> T bind(View root) {
        return DataBindingUtil.bind(root);
    }
}
