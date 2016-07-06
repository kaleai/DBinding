package kale.db.simple;

import android.view.View;

import kale.db.simple.base.BaseActivity;
import kale.db.simple.databinding.PicDetailActivityBinding;
import kale.dbinding.DBinding;
import kale.dbinding.util.SerializableViewModel;
import vm.PicVm;
import vm.ViewEvent;

/**
 * @author Kale
 * @date 2016/7/6
 */

public class PicDetailActivity extends BaseActivity<PicDetailActivityBinding> {

    private PicVm vm;

    private ViewEvent event = new ViewEvent();

    @Override
    protected int getLayoutResId() {
        return R.layout.pic_detail_activity;
    }

    @Override
    protected void beforeSetViews() {
        vm = (PicVm) ((SerializableViewModel) getIntent().getSerializableExtra(PicItem.KEY_VIEW_MODEL)).toViewModel();
    }

    @Override
    protected void setViews() {
        event.setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == b.likeBtn) {
                    vm.setIsLikeText(vm.getIsLikeText().equals("like") ? "unlike" : "like");
                }
            }
        });
        DBinding.setVariables(b, vm, event);
    }

    @Override
    protected void doTransaction() {

    }
}
