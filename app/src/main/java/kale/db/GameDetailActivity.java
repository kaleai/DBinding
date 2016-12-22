package kale.db;

import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

import kale.db.base.BaseActivity;
import kale.db.databinding.GameDetailActivityBinding;
import kale.dbinding.data.ObservableBitmap;
import kale.dbinding.data.ObservableCharSequence;

/**
 * @author Kale
 * @date 2016/1/27
 */
@Dispatcher
public class GameDetailActivity extends BaseActivity<GameDetailActivityBinding> {

    @Arg
    int picResId;

    @Arg
    String title;

    @Arg
    ObservableBitmap icon;

    @Arg
    ObservableCharSequence isLikedText;

    @Override
    protected int getLayoutResId() {
        return R.layout.game_detail_activity;
    }

    @Override
    protected void beforeSetViews() {
        b.setTitle(title);
        b.setIcon(icon);
        b.setIsLikedText(isLikedText);
        b.setEvent(viewEvents);
    }

    @Override
    protected void setViews() {
        changeText();
        b.picIv.setImageBitmap(BitmapFactory.decodeResource(getResources(), picResId));
        
        viewEvents.setOnClick(v -> {
            if (v == b.likeBtn) {
                Toast.makeText(GameDetailActivity.this, "Thank you~", Toast.LENGTH_SHORT).show();
                isLikedText.set(GameItem.LIKED);
                changeText();
            }
        });
    }

    private void changeText() {
        boolean isLiked = isLikedText.get().equals(GameItem.LIKED);
        b.likeBtn.setTextColor(getResources().getColor(isLiked ? R.color.dark_gray : R.color.white));
        b.likeBtn.setEnabled(!isLiked);
    }

    @Override
    protected void doTransaction() {
    }

}
