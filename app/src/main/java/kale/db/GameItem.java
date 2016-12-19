package kale.db;

import android.graphics.BitmapFactory;

import kale.db.base.BaseAdapterItem;
import kale.db.databinding.GameItemBinding;
import kale.db.model.NewsInfo;
import kale.viewmodel.GameItemVm;

/**
 * @author Kale
 * @date 2016/12/19
 */

public class GameItem extends BaseAdapterItem<GameItemBinding, NewsInfo> {

    static final String LIKED = "★ Liked";

    private GameItemVm vm;

    @Override
    public int getLayoutResId() {
        return R.layout.game_item;
    }

    @Override
    protected void beforeSetViews() {
        super.beforeSetViews();
        b.setData(commonVm);
        vm = new GameItemVm(commonVm);
    }

    @Override
    public void setViews() {
        notifyData(b);
        b.getRoot().setOnClickListener(v ->
                new GameDetailActivityDispatcher()
                        .setSerializableCommonVm(commonVm.toSerializable())
                        .start(getActivity())
        );
    }

    private void notifyData(final GameItemBinding b) {
        commonVm.addOnValueChangedCallback(id -> {
            switch (id) {
                case BR.isLikeText:
                    final int color = commonVm.getIsLikeText().equals(LIKED) ? R.color.yellow : R.color.gray;
                    b.isLikeText.setTextColor(b.getRoot().getResources().getColor(color));
                    break;
            }
        });
    }

    @Override
    public void handleData(NewsInfo data, int pos) {
        super.handleData(data, pos);
        vm.setPic(BitmapFactory.decodeResource(b.getRoot().getResources(), data.picResIdArr[0]));
        vm.setTitle(data.title);
        vm.setIcon(BitmapFactory.decodeResource(b.getRoot().getResources(), data.picResIdArr[1]));
        vm.setIsLikeText(data.isLikeText);
    }
}
