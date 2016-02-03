package kale.db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import kale.db.databinding.ActivityMainBinding;
import kale.dbinding.DBinding;
import viewdata.OtherViewData;
import viewdata.UserViewData;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private UserViewData mUserViewData = new UserViewData();

    private OtherViewData mOtherViewData = new OtherViewData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViews();
        setViews();
        doTransaction();
        
    }

    ActivityMainBinding b;

    private void bindViews() {
        b = DBinding.bind(this, R.layout.activity_main); // 设置布局
    }

    private void setViews() {
        DBinding.setVariables(b, mUserViewData, mOtherViewData);// 不要求放入的顺序
        b.userInfoInclude.headPicIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserDetailActivity.withIntent(MainActivity.this, mUserViewData));
            }
        });
        // 设置view的属性
        b.mainRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void doTransaction() {
        final MainViewModel viewModel = new MainViewModel(mUserViewData, mOtherViewData); // 定义vm
        boolean show = viewModel.init(this);
        if (show) {
            Toast.makeText(MainActivity.this, "Init Complete", Toast.LENGTH_SHORT).show();
        }
        b.mainRv.setAdapter(viewModel.getAdapter(this));
        
        viewModel.loadData();
    }
}
