package kale.db;


import org.kale.TestViewData;
import org.kale.TestViewData02;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import kale.db.databinding.ActivityMainBinding;
import kale.dbinding.DBinding;
import kale.dbinding.annotation.InjectViewData;

/**
 * 仍旧需要fragment，因为ui层还是有代码的，比如动画和单向绑定
 */
public class MainActivity extends BaseActivity {

    @InjectViewData
    TestViewData mTestViewData;

    @InjectViewData
    TestViewData02 mTestViewData02;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding b = DataBindingUtil.setContentView(this, R.layout.activity_main);

        injectAllViewData(); // 父类方法

        final MyViewModel viewModel = new MyViewModel(mTestViewData, mTestViewData02);
        
        DBinding.setVariables(b, mTestViewData02, mTestViewData); //不要求放入的顺序
        
        b.packageNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
        
        b.dataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.refreshUI(getBaseContext());
            }
        });
        
        // context?
    }
    
}
