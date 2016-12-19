package kale.db;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import kale.db.base.BaseActivity;
import kale.db.databinding.GameListActivityBinding;
import kale.dbinding.DBinding;
import kale.dbinding.ViewModelLayout;
import kale.viewmodel.HeaderLayoutVm;

@ViewModelLayout({R.layout.game_list_activity, R.layout.header_layout})
public class GameListActivity extends BaseActivity<GameListActivityBinding> {

    private GameListPresenter presenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.game_list_activity;
    }

    @Override
    protected void beforeSetViews() {
        DBinding.setVariables(b, commonVm, viewEvents); // 不要求放入的顺序
        presenter = new GameListPresenter(new HeaderLayoutVm(commonVm));
    }

    protected void setViews() {
        // 设置监听器
        viewEvents.setOnClick(v -> {
            if (v == b.userInfoInclude.headPicIv) {
                new UserDetailActivityDispatcher()
                        .setSerializableCommonVm(commonVm.toSerializable())
                        .start(getActivity());
            }
        });

        // 设置view的属性
        b.mainRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        b.mainRv.setAdapter(presenter.getAdapter());
    }

    protected void doTransaction() {
        if (presenter.init(this)) {
            Toast.makeText(GameListActivity.this, "Init Complete", Toast.LENGTH_SHORT).show();
        }
        presenter.loadData();
    }
}
