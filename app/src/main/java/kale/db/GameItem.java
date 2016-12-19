package kale.db;

import android.databinding.Observable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import kale.db.base.BaseAdapterItem;
import kale.db.databinding.GameItemBinding;
import kale.db.model.NewsInfo;
import kale.dbinding.ObservableBitmap;
import kale.dbinding.ObservableString;
import vm.GameItemVm;

/**
 * @author Kale
 * @date 2016/12/19
 */

public class GameItem extends BaseAdapterItem<GameItemBinding, NewsInfo> {

    static final String LIKED = "★ Liked";

    private GameItemVm mGameItemVm = new GameItemVm();

    private int mPicResId;

    @Override
    public int getLayoutResId() {
        return R.layout.game_item;
    }

    @Override
    protected void beforeSetViews() {
        super.beforeSetViews();
        b.setVm(mGameItemVm);
    }

    @Override
    public void setViews() {
        b.getRoot().setOnClickListener(v -> {
                    new GameDetailActivityDispatcher()
                            .setPicResId(mPicResId)
                            .setSerializableCommonVm(mGameItemVm.toSerializable())
                            .setTitle(b.getTitle())
                            .start(getActivity());

            b.getVm().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                        @Override
                        public void onPropertyChanged(Observable observable, int i) {
                            final int color = mGameItemVm.getIsLikedText().equals(LIKED) ? R.color.yellow : R.color.gray;
                            b.isLikeText.setTextColor(b.getRoot().getResources().getColor(color));
                        }
                    });
                }
        );
    }

    @Override
    public void handleData(NewsInfo data, int pos) {
        super.handleData(data, pos);
        mPicResId = data.picResIdArr[0];
        Bitmap bitmap = BitmapFactory.decodeResource(b.getRoot().getResources(), data.picResIdArr[1]);
        b.setIcon(ObservableBitmap.create(bitmap));
        b.setTitle(ObservableString.create(data.title));
        mGameItemVm.setIsLikedText(data.isLikeText);
    }
}
