package kale.db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import kale.db.databinding.ActivityMainBinding;
import kale.dbinding.DBinding;
import vm.OtherViewModel;
import vm.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private final UserViewModel mUserVm = new UserViewModel();

    private final OtherViewModel mOtherVm = new OtherViewModel(); // 无意义，仅仅做说明

    private ActivityMainBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViews();
        setViews();
        doTransaction();
    }

    private void bindViews() {
        b = DBinding.bindViewModel(this, R.layout.activity_main, mUserVm, mOtherVm); // 对vm的放入顺序无要求
    }

    private void setViews() {
        mUserVm.setOnClick(v -> {
            if (v == b.userInfoInclude.headPicIv) {
                startActivity(UserDetailActivity.withIntent(MainActivity.this, mUserVm));
            }
        });

        // 设置view的属性
        b.mainRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void doTransaction() {
        MainPresenter presenter = new MainPresenter(mUserVm, this);
        if (presenter.init(this)) {
            Toast.makeText(MainActivity.this, "Init Complete", Toast.LENGTH_SHORT).show();
        }
        presenter.loadData();
    }
}
