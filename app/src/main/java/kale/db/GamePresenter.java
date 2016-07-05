package kale.db;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import kale.adapter.CommonRcvAdapter;
import kale.adapter.item.AdapterItem;
import kale.db.model.NewsInfo;
import kale.db.network.NetworkService;
import kale.dbinding.util.ObservableArrayList;
import vm.UserViewModel;

/**
 * @author Kale
 * @date 2015/12/24
 */
class GamePresenter {

    private final UserViewModel mUserVm;

    private final ObservableArrayList<NewsInfo> mList; // 界面的数据对象

    GamePresenter(UserViewModel userVm) {
        mUserVm = userVm;
        mList = new ObservableArrayList<>();
    }

    /**
     * 这个当然可以放在构造方法中进行，我这里为了说明view层调用p的方法，强制加入了一个回调。
     */
    boolean init(final Activity activity) {
        mUserVm.setPic(BitmapFactory.decodeResource(activity.getResources(), R.drawable.speed_icon));
        mUserVm.setName("Need for Speed");
        return true;
    }

    void loadData() {
        List<NewsInfo> data = NetworkService.loadDataFromNetwork();
        // load data from network
        mList.addAll(0, data); // don't need to call notifyDataSetChanged()
    }

    RecyclerView.Adapter getAdapter(Activity activity) {
        return new CommonRcvAdapter<NewsInfo>(mList) {
            @NonNull
            @Override
            public AdapterItem<NewsInfo> createItem(Object o) {
                return new GameItem(activity);
            }

        };
    }

}
    