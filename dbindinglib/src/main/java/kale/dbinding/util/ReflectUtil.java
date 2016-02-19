package kale.dbinding.util;

import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Kale
 * @date 2016/1/12
 */
public class ReflectUtil {

    /**
     * like {@code return new T();} 
     */
    public static @NonNull <T> T newInstance(Class<T> clz) {
        try {
            return clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // will not be null
    }

    /**
     * like {@code i = value;}
     */
    public static void setFieldValue(@NonNull Object target, Field field ,Object value) {
        field.setAccessible(true);
        try {
            field.set(target, value); // init fields
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void invokeMethod(Method method,Object obj,Object... params) {
        try {
            method.invoke(obj,params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
