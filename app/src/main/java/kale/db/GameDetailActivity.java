package kale.db;

import com.lzh.courier.annoapi.Field;
import com.lzh.courier.annoapi.Params;

import android.databinding.Observable;
import android.widget.Toast;

import kale.db.base.BaseActivity;
import kale.db.databinding.GameDetailActivityBinding;
import kale.dbinding.DBinding;
import kale.dbinding.util.SerializableViewModel;
import vm.GameViewModel;

/**
 * @author Kale
 * @date 2016/1/27
 */
@Params(fields = {@Field(name = "gameVm", type = SerializableViewModel.class)})
public class GameDetailActivity extends BaseActivity<GameDetailActivityBinding> {

    private GameViewModel mGameVm;

    private Observable.OnPropertyChangedCallback mCallback;

    @Override
    protected int getLayoutResId() {
        return R.layout.game_detail_activity;
    }

    @Override
    protected void beforeSetViews() {
        mGameVm = (GameViewModel) GameDetailActivityArgsData.getArguments(getIntent()).getGameVm().toViewModel();
        DBinding.setVariables(b, viewEvents, mGameVm);
    }

    @Override
    protected void setViews() {
        addCallback(b);
        viewEvents.setOnClick(v -> {
            if (v == b.likeBtn) {
                Toast.makeText(GameDetailActivity.this, "Thank you~", Toast.LENGTH_SHORT).show();
                mGameVm.setIsLikeText(GameItem.LIKED);
            }
        });
    }

    @Override
    protected void doTransaction() {

    }

    /**
     * 这个callback的方法中只有view层面的改变，所以不涉及任何逻辑代码
     *
     * 两个页面对like做了不同的监听处理，item中如果liked，那么文字变黄，当前界面中就是变灰色。
     * 这里用到了all做强制刷新,这里的注册应该在界面结束时销毁，如果不销毁，那么每次进入这个界面都会增加一个新的监听
     *
     * 如果这个viewModel仅仅是在一个页面中用，那么可以不用做remove回调的事情。但如果这个viewModel通过intent进行传递了。
     * 这就需要注意下要在页面中remove掉这个回调！
     */
    private void addCallback(final GameDetailActivityBinding b) {
        mCallback = mGameVm.addOnValueChangedCallback(propertyId -> {
            if (propertyId == BR._all || propertyId == kale.db.BR.isLikeText) {
                // 因为这个属性要被notifyChange()所影响，所以监听了BR._all这个id
                boolean isLiked = mGameVm.getIsLikeText().equals(GameItem.LIKED);
                b.likeBtn.setTextColor(getResources().getColor(isLiked ? R.color.dark_gray : R.color.white));
                b.likeBtn.setEnabled(!isLiked);
            }
        });

        // 因为监听器是在数据改变之后设置的，所以这里强制进行一次所有监听器的通知。
        // 因为只有监听BR._all这个id的监听才会被调用，所以不会进行整个界面的全刷。
        mGameVm.notifyChange();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGameVm.removeOnPropertyChangedCallback(mCallback);
    }

}
