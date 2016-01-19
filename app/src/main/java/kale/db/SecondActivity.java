package kale.db;

import org.kale.vd.UserViewData;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kale.db.databinding.UserDetailBinding;
import kale.dbinding.DBinding;
import kale.dbinding.annotation.InjectViewData;

/**
 * @author Kale
 * @date 2015/12/26
 */
public class SecondActivity extends AppCompatActivity {

    @InjectViewData(isSingle = true)
    UserViewData mViewData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserDetailBinding b = DBinding.bind(this, R.layout.user_detail); // 设置布局
        DBinding.injectViewData(this); // 初始化所有viewData
        DBinding.setVariables(b, mViewData); // 设置viewData到binding

        b.changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewData.setPic(BitmapFactory.decodeResource(getResources(), R.drawable.chuyin));
                mViewData.setName("初音未来");
            }
        });
    }
}
