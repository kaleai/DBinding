package kale.db;

import android.app.Activity;
import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import kale.db.databinding.GameDetailBinding;
import kale.dbinding.DBinding;
import viewdata.GameViewData;

/**
 * @author Kale
 * @date 2016/1/27
 */
public class GameDetailActivity extends AppCompatActivity {

    private static final String TAG = "GameDetailActivity";

    private static final String KEY = "view_data";

    public static final String LIKED = "liked";

    private GameViewData mViewData;

    private Observable.OnPropertyChangedCallback mCallback;

    public static Intent withIntent(Activity activity, GameViewData viewData) {
        return new Intent(activity, GameDetailActivity.class)
                .putExtra(KEY, viewData.toSerializable());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final GameDetailBinding b = DBinding.bind(this, R.layout.game_detail);

        mViewData = GameViewData.toViewData(getIntent().getSerializableExtra(KEY));
        if (mViewData == null) {
            return;
        }
        DBinding.setVariables(b, mViewData);

        /** 
         * 两个页面对like做了不同的监听处理，item中如果liked，那么文字变黄，当前界面中就是变灰色。
         * 这里用到了all做强制刷新,这里的注册应该在界面结束时销毁，如果不销毁，那么每次进入这个界面都会增加一个新的监听
         * 如果这个viewData仅仅是在一个页面中用，那么可以不用做remove回调的事情。但如果这个viewData通过intent进行传递了。
         * 这就需要注意下要在页面中remove掉这个回调！
         */
        mCallback = getCallback(b, mViewData);
        mViewData.addOnPropertyChangedCallback(mCallback);
        mViewData.notifyChange();

        b.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GameDetailActivity.this, "Click", Toast.LENGTH_SHORT).show();
                mViewData.setIsLikeText(LIKED);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewData.removeOnPropertyChangedCallback(mCallback);
    }

    @NonNull
    private Observable.OnPropertyChangedCallback getCallback(final GameDetailBinding b, final GameViewData viewData) {
        return new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.d(TAG, "onPropertyChanged: " + propertyId);
                if (propertyId == BR._all || propertyId == BR.isLikeText) {
                    if (viewData.getIsLikeText().equals(LIKED)) {
                        b.likeBtn.setTextColor(getResources().getColor(R.color.dark_gray));
                        b.likeBtn.setEnabled(false);
                    } else {
                        b.likeBtn.setTextColor(getResources().getColor(R.color.white));
                        b.likeBtn.setEnabled(true);
                    }
                }
            }
        };
    }
}
