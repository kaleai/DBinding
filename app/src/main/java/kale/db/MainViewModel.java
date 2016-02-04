package kale.db;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import kale.adapter.IAdapter;
import kale.adapter.item.AdapterItem;
import kale.adapter.recyclerview.CommonRcvAdapter;
import kale.db.model.NewsInfo;
import viewdata.OtherViewData;
import viewdata.UserViewData;

/**
 * @author Kale
 * @date 2015/12/24
 */
public class MainViewModel {

    /**
     * 这里是其他的vd，仅仅是说明，没啥用处
     */
    private final OtherViewData mOtherViewData;

    private UserViewData mUserViewData;

    private IAdapter<NewsInfo> mAdapter;

    public MainViewModel(UserViewData userViewData, OtherViewData otherViewData) {
        mUserViewData = userViewData;
        mOtherViewData = otherViewData;
    }

    /**
     * 这个当然可以放在构造方法中进行，我这里为了说明view层调用vm的方法，强制加入了一个回调。
     */
    public boolean init(final Activity activity) {
        mUserViewData.setPic(BitmapFactory.decodeResource(activity.getResources(), R.drawable.speed_icon));
        mUserViewData.setName("Need for Speed");

        return true;
    }

    public CommonRcvAdapter getAdapter(final Activity activity) {
        // adapter放在vm层
        CommonRcvAdapter<NewsInfo> adapter = new CommonRcvAdapter<NewsInfo>(new ArrayList<NewsInfo>()) {
            @NonNull
            @Override
            public AdapterItem<NewsInfo> createItem(Object o) {
                return new GameItem(activity);
            }
        };
        mAdapter = adapter;
        return adapter;
    }

    public String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 为了说明的简单，这里模拟网络加载数据的步骤，还没做网络层。
     */
    public void loadData() {
        ArrayList<NewsInfo> list = new ArrayList<>();
        list.add(new NewsInfo("Hitman 47", "Like", new int[]{R.drawable.hit_man, R.drawable.hit_man_icon}));
        list.add(new NewsInfo("Assassins Creed", "Like", new int[]{R.drawable.assassin_s_creed,
                R.drawable.assassin_s_creed_icon}));
        list.add(new NewsInfo("StarCraft Ⅱ", "Like", new int[]{R.drawable.star_craft,R.drawable.star_craft_icon}));
        list.add(new NewsInfo("Watch Dogs", "Like", new int[]{R.drawable.watch_dogs, R.drawable.watch_dogs_icon}));
        mAdapter.setData(list);
        ((CommonRcvAdapter) mAdapter).notifyDataSetChanged();
    }

    public static void main(String[] args) {
        UserViewData userViewData = new UserViewData();
        OtherViewData otherViewData = new OtherViewData();
        MainViewModel model = new MainViewModel(userViewData, otherViewData);
    }

}
