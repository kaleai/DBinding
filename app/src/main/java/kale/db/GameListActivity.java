package kale.db;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import kale.db.base.BaseActivity;
import kale.db.databinding.GameListActivityBinding;
import kale.db.databinding.HeaderLayoutBinding;
import kale.dbinding.DBinding;
import kale.dbinding.data.ObservableBitmap;
import kale.dbinding.data.ObservableCharSequence;

public class GameListActivity extends BaseActivity<GameListActivityBinding> {

    private GameListPresenter mPresenter;

    private HeaderLayoutBinding mBind;

    @Override
    protected int getLayoutResId() {
        return R.layout.game_list_activity;
    }

    @Override
    protected void beforeSetViews() {
        mBind = DBinding.bind(findViewById(R.id.header_layout));
        mBind.setEvent(viewEvents);
        mBind.setName(ObservableCharSequence.create("default value"));
        mBind.setPic(ObservableBitmap.create());

        mPresenter = new GameListPresenter();
    }

    protected void setViews() {
        viewEvents.setOnClick(v -> {
            if (v == b.headerLayout.headPicIv) {
                new UserDetailActivityDispatcher()
                        .setName(mBind.getName())
                        .setPic(mBind.getPic())
                        .start(getActivity());
            }
        });

        b.mainRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        b.mainRv.setAdapter(mPresenter.getAdapter());
    }

    protected void doTransaction() {
        if (mPresenter.init(this, mBind.getName(), mBind.getPic())) {
            Toast.makeText(GameListActivity.this, "Init Completed", Toast.LENGTH_SHORT).show();
        }
        mPresenter.loadData();
    }
}
