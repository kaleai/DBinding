package kale.dbinding;

import kale.dbinding.util.ReflectUtil;

/**
 * @author Kale
 * @date 2016/1/13
 */
public class BaseImp extends BaseViewData<BaseImp>{

    public static void main(String[] args) {
        int max = 20;
        Class clz = BaseImp.class;
        long start = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            ReflectUtil.newInstance(clz);
        }
        /*for (int j = 0; j < max; j++) {
            new BaseImp();
        }*/
        long p = System.currentTimeMillis();
        
        System.out.println(p - start);
        
        for (int j = 0; j < max; j++) {
            new BaseImp();
        }
        System.out.println(System.currentTimeMillis() - p);
    }
}
