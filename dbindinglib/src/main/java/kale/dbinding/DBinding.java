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

/**
 * @author Kale
 * @date 2015/12/26
 */
public class DBinding {

    private static final String TAG = "DBinding";

    public static void setVariables(@NonNull ViewDataBinding binding, @NonNull BaseObservable... vds) {
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

        for (int i = 0, size = vds.length; i < size; i++) {
            if (vds[i] == null) {
                throw new NullPointerException("No." + i + 1 + "viewData is not init");
            }
            try {
                Class vdClz = vds[i].getClass();
                if (setMethods.get(vdClz) != null) {
                    setMethods.get(vdClz).invoke(binding, vds[i]);
                } else {
                    throw new RuntimeException("binding和viewdata不对应，应检查是否使用了正确的bind和vd组合" + vds[i].getClass().getSimpleName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
