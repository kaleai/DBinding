package kale.db;

import android.databinding.Observable;
import android.graphics.BitmapFactory;

import kale.db.base.BaseAdapterItem;
import kale.db.databinding.GameItemBinding;
import kale.db.model.NewsInfo;
import kale.dbinding.data.ObservableBitmap;
import kale.dbinding.data.ObservableCharSequence;

/**
 * @author Kale
 * @date 2016/12/19
 */

class GameItem extends BaseAdapterItem<GameItemBinding, NewsInfo> {

    static final String LIKED = "★ Liked";

    private ObservableCharSequence mIsLikedText = ObservableCharSequence.create();

    private ObservableCharSequence mTitle = ObservableCharSequence.create();

    private ObservableBitmap mIcon = ObservableBitmap.create();

    @Override
    public int getLayoutResId() {
        return R.layout.game_item;
    }

    @Override
    protected void beforeSetViews() {
        super.beforeSetViews();
        b.setTitle(mTitle);
        b.setIcon(mIcon);
        b.setIsLikedText(mIsLikedText);
    }

    @Override
    public void setViews() {
        b.getRoot().setOnClickListener(v ->
                new GameDetailActivityDispatcher()
                        .setTitle((String) mTitle.get()) // 如果另一个页面不需要做回调，那么就直接传基础变量
                        .setIsLikedText(mIsLikedText) // 这个变量需要做数据同步，所以就传递了ObservableXxxx
                        .setIcon(mIcon)
                        .setPicResId(getData().picResIdArr[0]) // 能传基础变量的就传基础变量
                        .start(getActivity())
        );
        
        mIsLikedText.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                final int color = mIsLikedText.get().equals(LIKED) ? R.color.yellow : R.color.gray;
                b.isLikeText.setTextColor(b.getRoot().getResources().getColor(color));
            }
        });
    }

    @Override
    public void handleData(NewsInfo data, int pos) {
        super.handleData(data, pos);
        mIcon.set(BitmapFactory.decodeResource(b.getRoot().getResources(), data.picResIdArr[1]));
        mTitle.set(data.title);
        mIsLikedText.set(data.isLikeText);
    }
}
