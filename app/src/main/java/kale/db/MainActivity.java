package kale.db;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import kale.db.base.BaseActivity;
import kale.db.databinding.ActivityMainBinding;
import kale.dbinding.DBinding;
import vm.UserViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private UserViewModel mUserVm;

    private GamePresenter presenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeSetViews() {
        mUserVm = new UserViewModel();

        DBinding.setVariables(b, mUserVm, viewEvents); // 不要求放入的顺序
        presenter = new GamePresenter(mUserVm);
    }

    protected void setViews() {
        viewEvents.setOnClick(v -> {
            if (v == b.userInfoInclude.headPicIv) {
                UserDetailActivityDispatcher.create().setVm(mUserVm.toSerializable()).start(this);
            }
        });

        // 设置view的属性
        b.mainRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        b.mainRv.setAdapter(presenter.getAdapter(this));
    }

    protected void doTransaction() {
        // 传入vm
        if (presenter.init(this)) {
            Toast.makeText(MainActivity.this, "Init Complete", Toast.LENGTH_SHORT).show();
        }
        presenter.loadData();
    }
}
