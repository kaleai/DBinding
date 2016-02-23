package kale.db;

import android.app.Activity;
import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import kale.db.databinding.GameDetailBinding;
import kale.dbinding.DBinding;
import vm.GameViewModel;

/**
 * @author Kale
 * @date 2016/1/27
 */
public class GameDetailActivity extends AppCompatActivity {

    private static final String KEY = "view_data";

    private GameViewModel mGameVm;

    private Observable.OnPropertyChangedCallback mCallback;

    public static Intent withIntent(Activity activity, GameViewModel viewModel) {
        return new Intent(activity, GameDetailActivity.class)
                .putExtra(KEY, viewModel.toSerializable());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameVm = GameViewModel.toViewModel(getIntent().getSerializableExtra(KEY));

        final GameDetailBinding b = DBinding.bindViewModel(this, R.layout.game_detail, mGameVm);
        addCallback(b);

        mGameVm.setOnClick(v -> {
            if (v == b.likeBtn) {
                mGameVm.setIsLikeText(GameItem.LIKED);
            }
        });
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
    private void addCallback(final GameDetailBinding b) {
        mCallback = mGameVm.addOnValueChangedCallback(propertyId -> {
            if (propertyId == BR._all || propertyId == BR.isLikeText) {
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
