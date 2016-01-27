package kale.db;

import org.kale.vd.OtherViewData;
import org.kale.vd.UserViewData;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kale
 * @date 2015/12/24
 */
public class MainViewModel {

    private final OtherViewData mOtherViewData;

    private UserViewData mUserViewData;

    public MainViewModel(UserViewData userViewData,OtherViewData otherViewData) {
        mUserViewData = userViewData;
        mOtherViewData = otherViewData;
    }

    /**
     * 这个当然可以放在构造方法中进行，我这里为了说明view层调用vm的方法，强制加入了一个回调。
     */
    public boolean init(Context context) {
        mUserViewData.setPic(BitmapFactory.decodeResource(context.getResources(),R.drawable.mingren));
        mUserViewData.setName("漩涡鸣人");
        //mOtherViewData.setViz(View.VISIBLE);
        return true;
    }

    public String getPackageName(Context context) {
        return context.getPackageName();
    }

    public List<NewsInfo> loadData() {
        ArrayList<NewsInfo> list = new ArrayList<>();
        list.add(new NewsInfo("Saber", "点个赞吧", new int[]{R.drawable.saber}));
        list.add(new NewsInfo("地狱少女", "点个赞吧", new int[]{R.drawable.dysn}));
        list.add(new NewsInfo("初音未来", "点个赞吧", new int[]{R.drawable.chuyin}));
        list.add(new NewsInfo("凉宫", "点个赞吧", new int[]{R.drawable.lianggong}));
        list.add(new NewsInfo("古河渚", "点个赞吧", new int[]{R.drawable.ghz}));
        return list;
    }
    

    public static void main(String[] args) {
        UserViewData userViewData = new UserViewData();
        OtherViewData otherViewData = new OtherViewData();
        MainViewModel model = new MainViewModel(userViewData, otherViewData);
    }

}
