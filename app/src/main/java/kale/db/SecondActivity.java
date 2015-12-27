package kale.db;

import org.kale.TestViewData02;

import android.os.Bundle;
import android.support.annotation.Nullable;

import kale.dbinding.annotation.InjectViewData;

/**
 * @author Kale
 * @date 2015/12/26
 */
public class SecondActivity extends BaseActivity{

    @InjectViewData
    TestViewData02 mViewData;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main02);

        injectAllViewData();

        mViewData.setBtnText("(*^__^*)");
    }
}
