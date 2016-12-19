package kale.db;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

import android.graphics.BitmapFactory;
import android.widget.Toast;

import kale.db.base.BaseActivity;
import kale.db.databinding.GameDetailActivityBinding;
import kale.dbinding.ObservableBitmap;
import kale.dbinding.ObservableString;
import kale.dbinding.util.SerializableViewModel;
import vm.GameItemVm;

/**
 * @author Kale
 * @date 2016/1/27
 */
@Dispatcher
public class GameDetailActivity extends BaseActivity<GameDetailActivityBinding> {

    @Arg
    SerializableViewModel serializableCommonVm;

    @Arg
    ObservableString title;

    @Arg
    int picResId;

    GameItemVm mGameItemVm;

    //    @Arg
    ObservableBitmap icon;

    @Override
    protected int getLayoutResId() {
        return R.layout.game_detail_activity;
    }

    @Override
    protected void beforeSetViews() {
        mGameItemVm = (GameItemVm) serializableCommonVm.toViewModel();
        
        b.setTitle(title);
        b.setVm(mGameItemVm);
//        b.setIcon(icon);
        b.setEvent(viewEvents);
        b.setVm(mGameItemVm);
    }

    @Override
    protected void setViews() {
        changeText();
        b.picIv.setImageBitmap(BitmapFactory.decodeResource(getResources(), picResId));
        
        viewEvents.setOnClick(v -> {
            if (v == b.likeBtn) {
                Toast.makeText(GameDetailActivity.this, "Thank you~", Toast.LENGTH_SHORT).show();
                mGameItemVm.setIsLikedText(GameItem.LIKED);
                changeText();
            }
        });
    }

    private void changeText() {
        boolean isLiked = mGameItemVm.getIsLikedText().equals(GameItem.LIKED);
        b.likeBtn.setTextColor(getResources().getColor(isLiked ? R.color.dark_gray : R.color.white));
        b.likeBtn.setEnabled(!isLiked);
    }

    @Override
    protected void doTransaction() {
    }

}
