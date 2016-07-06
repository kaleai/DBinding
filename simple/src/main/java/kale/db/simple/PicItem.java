package kale.db.simple;

import android.app.Activity;
import android.content.Intent;
import android.databinding.Observable;
import android.text.TextUtils;
import android.view.View;

import kale.adapter.item.AdapterItem;
import kale.db.simple.databinding.PicItemBinding;
import kale.db.simple.model.Result;
import kale.dbinding.DBinding;
import vm.PicVm;

/**
 * @author Kale
 * @date 2016/7/5
 */

class PicItem implements AdapterItem<Result> {

    public static final String KEY_VIEW_MODEL = "KEY_VIEW_MODEL";
    
    private PicVm vm = new PicVm() {
        @Override
        public CharSequence getTime() {
            CharSequence time = super.getTime();
            if (!TextUtils.isEmpty(time)) {
                time = time.subSequence(0, 10);
            }
            return time;
        }
    };

    private PicItemBinding b;

    private Activity activity;

    public PicItem(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.pic_item;
    }

    @Override
    public void bindViews(View view) {
        b = DBinding.bind(view);
    }

    @Override
    public void setViews() {
        b.setVm(vm);
        b.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, PicDetailActivity.class)
                        .putExtra(KEY_VIEW_MODEL, vm.toSerializable()));
            }
        });
        vm.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (propertyId == BR.isLikeText) {
                    if (vm.getIsLikeText().equals("like")) {
                        b.likeText.setTextColor(activity.getResources().getColor(R.color.colorAccent));
                    } else {
                        b.likeText.setTextColor(activity.getResources().getColor(R.color.gray));
                    }
                }
            }
        });
    }

    @Override
    public void handleData(Result result, int i) {
        vm.setTime(result.createdAt);
        vm.setPicUrl(result.url);
        vm.setIsLikeText("unlike");
        b.executePendingBindings();
    }
}
