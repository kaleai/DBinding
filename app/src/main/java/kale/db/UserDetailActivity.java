package kale.db;

import org.kale.vd.UserViewData;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kale.db.databinding.UserDetailBinding;
import kale.dbinding.BaseViewData;
import kale.dbinding.DBinding;

/**
 * @author Kale
 * @date 2015/12/26
 */
public class UserDetailActivity extends AppCompatActivity {

    private UserViewData mViewData;
    
    private static final String KEY = "view_data";

    public static Intent withIntent(Activity activity, BaseViewData viewData) {
        return new Intent(activity, UserDetailActivity.class)
                .putExtra(KEY, viewData.toSerializable());
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserDetailBinding b = DBinding.bind(this, R.layout.user_detail); // 设置布局
        mViewData = UserViewData.toViewData(getIntent().getSerializableExtra(KEY));
        
        DBinding.setVariables(b, mViewData); // 设置viewData到binding

        b.changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewData.setPic(BitmapFactory.decodeResource(getResources(), R.drawable.kale));
                mViewData.setName("Kale");
            }
        });
    }
}
