package kale.db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import in.workarounds.bundler.Bundler;
import kale.db.databinding.ActivityMainBinding;
import kale.dbinding.DBinding;
import vm.EventViewModel;
import vm.OtherViewModel;
import vm.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private EventViewModel mEvent = new EventViewModel();
    
    private final UserViewModel mUserVm;

    private final OtherViewModel mOtherVm;
    
    private ActivityMainBinding b;

    public MainActivity() {
        mUserVm = new UserViewModel() {
            @Override
            public void setName(CharSequence name) {
                // 对于复杂的ui需求，可以重载对应的set方法，不应该重载get方法
                super.setName("$" + name);
            }
        };
        mOtherVm = new OtherViewModel(); // 无意义，仅仅做说明
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViews();
        setViews();
        doTransaction();
    }

    private void bindViews() {
        b = DBinding.bindViewModel(this, R.layout.activity_main, mEvent, mUserVm, mOtherVm); // 对vm的放入顺序无要求
    }

    private void setViews() {
        mEvent.setOnClick(v -> {
            if (v == b.userInfoInclude.headPicIv) {
                Bundler.userDetailActivity(mUserVm.toSerializable()).start(this);
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
