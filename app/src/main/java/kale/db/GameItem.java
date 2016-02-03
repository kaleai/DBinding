package kale.db;

import android.app.Activity;
import android.databinding.Observable;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import kale.adapter.item.BaseAdapterItem;
import kale.db.databinding.GameItemBinding;
import kale.db.model.NewsInfo;
import kale.dbinding.DBinding;
import viewdata.GameViewData;

/**
 * @author Kale
 * @date 2016/1/6
 *
 * 这里需要viewData，它可以方便的让你实现点击item进入后更新了阅读数，这个item也需要被更新
 */
public class GameItem extends BaseAdapterItem<NewsInfo> {

    @Override
    public int getLayoutResId() {
        return R.layout.game_item;
    }

    GameItemBinding mBind;
    GameViewData mViewData = new GameViewData();
    Activity mActivity;

    public GameItem(Activity activity) {
        mActivity = activity;
    }

    @Override
    protected void bindViews() {
        mBind = DBinding.bind(root);
        DBinding.setVariables(mBind, mViewData);
    }

    @Override
    public void setViews() {
        notifyData(mBind);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(GameDetailActivity.withIntent(mActivity, mViewData));           
            }
        });
    }

    /**
     * 数据改变后ui会做一些改变。
     * 应该利用对vd的字段监听的方式做处理，不应该在数据改变时，通过开发者做ui层面的更新。
     *
     * @param bind 为什么不是单一监听器，而是观察者模式？
     *             因为多个页面会有对同一个数据的监听操作，如果是单一的那么就没办法实现这个功能。
     */
    public void notifyData(final GameItemBinding bind) {
        mViewData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                GameViewData viewData = (GameViewData) sender;
                if (propertyId == kale.db.BR.title) {
                    setSmartText(bind.titleTv, viewData.getTitle());
                } else if (propertyId == BR.isLikeText) {
                    final int color;
                    if (mViewData.getIsLikeText().equals(GameDetailActivity.LIKED)) {
                        color = R.color.yellow;
                    } else {
                        color = R.color.gray;
                    }
                    bind.isLikeText.setTextColor(bind.getRoot().getResources().getColor(color));
                }
            }
        });
    }

    /**
     * 如果有数据，那么就显示textView；
     * 如果没数据，那么就让textView消失
     */
    public void setSmartText(TextView textView, CharSequence text) {
        textView.setVisibility(!TextUtils.isEmpty(text) ? View.VISIBLE : View.GONE);
    }
    
    
    
    ///////////////////////////////////////////////////////////////////////////
    // 这里就仅仅做数据和ui的绑定工作了，不用想ui层面的任何逻辑，这个算是数据层面的绑定工作。
    // 因为item中通常的数据逻辑不会太复杂，一般都很简单。所以这里就不用做一个逻辑层。
    ///////////////////////////////////////////////////////////////////////////
    
    /**
     * 将viewData和model的数据进行同步
     * model模型可能很复杂，但viewData的模型很简单，这里就是做二者的转换。
     * 转换的步骤不可少，不能把model直接写成viewData
     */
    @Override
    public void handleData(NewsInfo data, int pos) {
        mViewData.setPic(BitmapFactory.decodeResource(root.getResources(), data.picResIdArr[0]));
        mViewData.setTitle(data.title);
        mViewData.setIcon(BitmapFactory.decodeResource(root.getResources(), data.picResIdArr[1]));
        mViewData.setIsLikeText(data.isLikeText);
    }

}
