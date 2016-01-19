package kale.db;

import org.kale.vd.OtherViewData;
import org.kale.vd.UserViewData;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import kale.db.databinding.ActivityMainBinding;
import kale.dbinding.DBinding;
import rx.functions.Action1;

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
        
        ActivityMainBinding b = DBinding.bind(this, R.layout.activity_main); // 设置布局
        
        //DBinding.injectViewData(this); // 注入viewData
        
        final MyViewModel viewModel = new MyViewModel(mUserViewData, mOtherViewData); // 定义vm
        
        DBinding.setVariables(b, mOtherViewData, mUserViewData);// 不要求放入的顺序
        
        viewModel.init(getBaseContext()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean showToast) {
                if (showToast) {
                    Toast.makeText(MainActivity.this, "Init Complete", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        b.userInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

        // 显示toast、对话框。数据返回后如何监听结果进行别的显示
        // context?

    }

    
    

}
