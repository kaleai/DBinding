package kale.db;

import org.kale.vd.OtherViewData;
import org.kale.vd.UserViewData;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import kale.adapter.IAdapter;
import kale.adapter.item.AdapterItem;
import kale.adapter.recyclerview.CommonRcvAdapter;
import kale.db.model.NewsInfo;

/**
 * @author Kale
 * @date 2015/12/24
 */
public class MainViewModel {

    private final OtherViewData mOtherViewData;

    private UserViewData mUserViewData;
    
    private IAdapter<NewsInfo> mAdapter;
    
    public MainViewModel(UserViewData userViewData,OtherViewData otherViewData) {
        mUserViewData = userViewData;
        mOtherViewData = otherViewData;
    }

    /**
     * 这个当然可以放在构造方法中进行，我这里为了说明view层调用vm的方法，强制加入了一个回调。
     */
    public boolean init(final Activity activity) {
        mUserViewData.setPic(BitmapFactory.decodeResource(activity.getResources(),R.drawable.mingren));
        mUserViewData.setName("漩涡鸣人");
        
        return true;
    }

    public CommonRcvAdapter getAdapter(final Activity activity) {
        // adapter放在vm层
        CommonRcvAdapter<NewsInfo> adapter = new CommonRcvAdapter<NewsInfo>(new ArrayList<NewsInfo>()) {
            @NonNull
            @Override
            public AdapterItem<NewsInfo> createItem(Object o) {
                return new NewsItem(activity);
            }
        };
        mAdapter = adapter;
        return adapter;
    }

    public String getPackageName(Context context) {
        return context.getPackageName();
    }

    public void loadData() {
        ArrayList<NewsInfo> list = new ArrayList<>();
        list.add(new NewsInfo("Saber", "点个赞吧", new int[]{R.drawable.saber}));
        list.add(new NewsInfo("地狱少女", "点个赞吧", new int[]{R.drawable.dysn}));
        list.add(new NewsInfo("初音未来", "点个赞吧", new int[]{R.drawable.chuyin}));
        list.add(new NewsInfo("凉宫", "点个赞吧", new int[]{R.drawable.lianggong}));
        list.add(new NewsInfo("古河渚", "点个赞吧", new int[]{R.drawable.ghz}));
        mAdapter.setData(list);
        ((CommonRcvAdapter) mAdapter).notifyDataSetChanged();
    }

    public static void main(String[] args) {
        UserViewData userViewData = new UserViewData();
        OtherViewData otherViewData = new OtherViewData();
        MainViewModel model = new MainViewModel(userViewData, otherViewData);
    }

}
