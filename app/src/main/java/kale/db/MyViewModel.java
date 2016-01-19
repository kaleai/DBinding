package kale.db;

import org.kale.vd.OtherViewData;
import org.kale.vd.UserViewData;

import android.content.Context;
import android.graphics.BitmapFactory;

import rx.Observable;

/**
 * @author Kale
 * @date 2015/12/24
 */
public class MyViewModel {

    private final OtherViewData mOtherViewData;

    private UserViewData mUserViewData;

    public MyViewModel(UserViewData userViewData,OtherViewData otherViewData) {
        mUserViewData = userViewData;
        mOtherViewData = otherViewData;
    }

    /**
     * 这个当然可以放在构造方法中进行，但因为这里有一个回调，所以独立出来了一个方法。
     */
    public Observable<Boolean> init(Context context) {
        mUserViewData.setPic(BitmapFactory.decodeResource(context.getResources(),R.drawable.kale));
        mUserViewData.setName("kale");
        mOtherViewData.setSomeThing("someThing");
        //mOtherViewData.setViz(View.VISIBLE);
        return Observable.just(true);
    }

    public String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static void main(String[] args) {
        UserViewData userViewData = new UserViewData();
        OtherViewData otherViewData = new OtherViewData();
        MyViewModel model = new MyViewModel(userViewData, otherViewData);
        //System.out.println(otherViewData.getViz() == View.VISIBLE ? "可见" : "不可见");
    }

}
