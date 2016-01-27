package kale.db;

import org.kale.vd.OtherViewData;
import org.kale.vd.UserViewData;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kale.adapter.CommonAdapter;
import kale.adapter.IAdapter;
import kale.adapter.item.AdapterItem;
import kale.db.databinding.ActivityMainBinding;
import kale.dbinding.DBinding;

/**
 * 可能要根据注解中的某个属性来判断是否要维持数据的全局唯一
 */
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
        // context?
    }

    ActivityMainBinding b;

    private void bindViews() {
        b = DBinding.bind(this, R.layout.activity_main); // 设置布局
    }

    private void setViews() {
        DBinding.setVariables(b, mUserViewData, mOtherViewData);// 不要求放入的顺序
        b.userInfoLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserDetailActivity.withIntent(MainActivity.this, mUserViewData));
            }
        });
        List<NewsInfo> data = new ArrayList<>();
        b.mainLv.setAdapter(new CommonAdapter<NewsInfo>(data) {
            @NonNull
            @Override
            public AdapterItem<NewsInfo> createItem(Object o) {
                return new NewsItem(MainActivity.this);
            }
        });
    }

    private void doTransaction() {
        final MainViewModel viewModel = new MainViewModel(mUserViewData, mOtherViewData); // 定义vm
        boolean show = viewModel.init(getBaseContext());
        if (show) {
            Toast.makeText(MainActivity.this, "Init Complete", Toast.LENGTH_SHORT).show();
        }
        List<NewsInfo> data = viewModel.loadData();
        
        ((IAdapter) b.mainLv.getAdapter()).setData(data);
        ((BaseAdapter) b.mainLv.getAdapter()).notifyDataSetChanged();
    }
}
