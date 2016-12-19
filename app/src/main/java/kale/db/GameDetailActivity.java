package kale.db;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

import android.widget.Toast;

import kale.db.base.BaseActivity;
import kale.db.databinding.GameDetailActivityBinding;
import kale.dbinding.DBinding;
import kale.dbinding.util.SerializableViewModel;
import kale.viewmodel.GameDetailActivityVm;
import vm.CommonViewModel;

/**
 * @author Kale
 * @date 2016/1/27
 */
@Dispatcher
public class GameDetailActivity extends BaseActivity<GameDetailActivityBinding> {

    @Arg
    SerializableViewModel serializableCommonVm;

    private GameDetailActivityVm vm;

    @Override
    protected int getLayoutResId() {
        return R.layout.game_detail_activity;
    }

    @Override
    protected void beforeSetViews() {
        commonVm = (CommonViewModel) serializableCommonVm.toViewModel();
        DBinding.setVariables(b, viewEvents, commonVm);
        vm = new GameDetailActivityVm(commonVm);
    }

    @Override
    protected void setViews() {
        changeText();
        
        viewEvents.setOnClick(v -> {
            if (v == b.likeBtn) {
                Toast.makeText(GameDetailActivity.this, "Thank you~", Toast.LENGTH_SHORT).show();
                vm.setIsLikeText(GameItem.LIKED);
                changeText();
            }
        });
    }

    private void changeText() {
        boolean isLiked = vm.getIsLikeText().equals(GameItem.LIKED);
        b.likeBtn.setTextColor(getResources().getColor(isLiked ? R.color.dark_gray : R.color.white));
        b.likeBtn.setEnabled(!isLiked);
    }

    @Override
    protected void doTransaction() {
    }

}
