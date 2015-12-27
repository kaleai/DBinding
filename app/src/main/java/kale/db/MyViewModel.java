package kale.db;


import org.kale.TestViewData;
import org.kale.TestViewData02;

import android.content.Context;

/**
 * @author Kale
 * @date 2015/12/24
 * 
 *  * vm是不会被复用的，所以vm中可以做很多和业务相关的代码逻辑，通过viewData进行回调，一个vm会有多个vd，vd对vm不知情

 */
public class MyViewModel {

    private TestViewData mViewData01;
    private TestViewData02 mViewData02;

    public MyViewModel(TestViewData viewData01, TestViewData02 viewData02) {
        mViewData01 = viewData01;
        mViewData02 = viewData02;
        mViewData02.setBtnText("btnText"); // default text
    }

    public void refreshUI(Context context) {
        mViewData01.setData("data from vm");
        mViewData01.setPkgName(context.getPackageName());
        mViewData02.setBtnText("start new activity");
    }

}
