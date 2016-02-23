package kale.db;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import kale.adapter.item.AdapterItem;
import kale.adapter.item.BaseAdapterItem;
import kale.db.databinding.GameItemBinding;
import kale.db.model.NewsInfo;
import kale.dbinding.DBinding;
import vm.GameViewModel;

/**
 * @author Kale
 * @date 2016/1/6
 */
public class GameItem implements AdapterItem<NewsInfo> {

    public static final String LIKED = "liked";

    private GameItemBinding b;

    private final GameViewModel mGameVm = new GameViewModel();
    private Activity mActivity;

    public GameItem(Activity activity) {
        mActivity = activity;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.game_item;
    }

    @Override
    public void bindViews(View view) {
        b = DBinding.bindViewModel(view, mGameVm);
    }

    @Override
    public void setViews() {
        notifyData(b);
        b.getRoot().setOnClickListener(v -> mActivity.startActivity(GameDetailActivity.withIntent(mActivity, mGameVm)));
    }

    /**
     * 数据改变后ui会做一些改变。
     * 应该利用对vd的字段监听的方式做处理，不应该在数据改变时，通过开发者做ui层面的更新。
     *
     * @param b 为什么不是单一监听器，而是观察者模式？
     *             因为多个页面会有对同一个数据的监听操作，如果是单一的那么就没办法实现这个功能。
     */
    public void notifyData(final GameItemBinding b) {
        mGameVm.addOnValueChangedCallback(id -> {
            switch (id) {
                case BR.title:
                    b.titleTv.setVisibility(!TextUtils.isEmpty(mGameVm.getTitle()) ? View.VISIBLE : View.GONE);
                    break;
                case BR.isLikeText:
                    final int color = mGameVm.getIsLikeText().equals(LIKED) ? R.color.yellow : R.color.gray;
                    b.isLikeText.setTextColor(mActivity.getResources().getColor(color));
                    break;
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // 这里就仅仅做数据和ui的绑定工作了，不用想ui层面的任何逻辑，这个算是数据层面的绑定工作。
    // 因为item中通常的数据逻辑不会太复杂，一般都很简单。所以这里就不用做一个逻辑层。
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 将viewData和model的数据进行同步
     * model模型可能很复杂，但viewData的模型很简单，这里就是做二者的转换。
     * 转换的步骤不可少，不能把model直接写成viewData
     *
     * 在adapter的item中更新数据一定要记得if后面要跟上else，否则会因为item的复用而造成显示的问题。
     * 但如果通过这样的写法，塞数据的过程和ui更新的过程是完全分开的，可以有效的降低因人为遗忘造成的数据状态未更新的问题
     */
    @Override
    public void handleData(NewsInfo data, int pos) {
        mGameVm.setPic(BitmapFactory.decodeResource(mActivity.getResources(), data.picResIdArr[0]));
        mGameVm.setTitle(data.title);
        mGameVm.setIcon(BitmapFactory.decodeResource(mActivity.getResources(), data.picResIdArr[1]));
        mGameVm.setIsLikeText(data.isLikeText);
    }

}
