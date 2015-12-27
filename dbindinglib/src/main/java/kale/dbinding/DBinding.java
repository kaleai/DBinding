package kale.dbinding;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kale.dbinding.annotation.InjectViewData;

/**
 * @author Kale
 * @date 2015/12/26
 */
public class DBinding {

    public static void main(String[] args) {
    }


    public static List<IViewData> injectViewData(Object obj) {
        List<IViewData> viewDataList = new ArrayList<>();
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //判断字段是否标注InjectView
            if (field.isAnnotationPresent(InjectViewData.class)) {
                Class<IViewData> type = (Class<IViewData>) field.getType();
                IViewData viewData = ViewDataManager.getInstance().getViewData(type, obj);
                //反射访问私有成员，必须加上这句
                field.setAccessible(true);
                try {
                    field.set(obj, viewData);
                    viewDataList.add(viewData);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                InjectViewData injectViewData = field.getAnnotation(InjectViewData.class);
            }
        }
        return viewDataList;
    }

    public static void setVariables(Object b, Object... vds) {
        Map<Class, Method> setMethods = new HashMap<>();
        Method[] methods = b.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getModifiers() == Modifier.PUBLIC
                    && method.getReturnType() == void.class
                    && method.getName().startsWith("set")
                    && method.getParameterTypes().length == 1) {

                setMethods.put(method.getParameterTypes()[0], method);
            }
        }
        
        for (Object vd : vds) {
            try {
                if (setMethods.get(vd.getClass()) != null) {
                    setMethods.get(vd.getClass()).invoke(b, vd);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

}
