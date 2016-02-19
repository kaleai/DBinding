package kale.dbinding;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import kale.dbinding.util.ReflectUtil;

/**
 * @author Kale
 * @date 2015/12/26
 */
public class DBinding {

    public static void setVariables(@NonNull ViewDataBinding binding, @NonNull BaseObservable... vms) {
        Map<Class<?>, Method> setMethods = new HashMap<>(); // methods cache
        Method[] methods = binding.getClass().getDeclaredMethods(); // public methods
        for (Method method : methods) {
            if (method.getModifiers() == Modifier.PUBLIC
                    && method.getReturnType() == void.class
                    && method.getName().startsWith("set")
                    && method.getParameterTypes().length == 1) {

                setMethods.put(method.getParameterTypes()[0], method);
            }
        }

        for (BaseObservable vd : vms) {
            if (vd == null) {
                throw new NullPointerException("One or more ViewModel is null");
            }
            
            Method method = getMatchedMethod(setMethods, vd.getClass());
            ReflectUtil.invokeMethod(method, binding, vd);
        }
    }

    private @NonNull static Method getMatchedMethod(Map<Class<?>, Method> map, Class<?> clz) {
        for (Map.Entry<Class<?>, Method> entry : map.entrySet()) {
            if (entry.getKey().isAssignableFrom(clz)) {
                return entry.getValue();
            }
        }
        throw new RuntimeException(clz.getSimpleName() + " was not found in binding");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Bind <=> View
    ///////////////////////////////////////////////////////////////////////////

    public static <T extends ViewDataBinding> T bind(Activity activity, @LayoutRes int layoutId) {
        return DataBindingUtil.setContentView(activity, layoutId);
    }

    public static <T extends ViewDataBinding> T bind(View root) {
        return DataBindingUtil.bind(root);
    }


    ///////////////////////////////////////////////////////////////////////////
    // Bind view and viewModel
    ///////////////////////////////////////////////////////////////////////////

    public static <T extends ViewDataBinding> T bindViewModel(Activity activity, @LayoutRes int layoutId, @NonNull BaseObservable... vms) {
        T bind = bind(activity, layoutId);
        setVariables(bind, vms);
        return bind;
    }

    public static <T extends ViewDataBinding> T bindViewModel(View root, @NonNull BaseObservable... vms) {
        T bind = bind(root);
        setVariables(bind, vms);
        return bind;
    }
}
