package kale.db;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import kale.db.databinding.UserDetailBinding;
import kale.dbinding.BaseViewModel;
import kale.dbinding.DBinding;
import vm.UserViewModel;

/**
 * @author Kale
 * @date 2015/12/26
 */
public class UserDetailActivity extends AppCompatActivity {

    private UserViewModel mUserVm;

    private static final String KEY = "view_model";

    public static Intent withIntent(Activity activity, BaseViewModel viewModel) {
        return new Intent(activity, UserDetailActivity.class)
                .putExtra(KEY, viewModel.toSerializable());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 通过toViewModel得到真正的vm对象
        mUserVm = UserViewModel.toViewModel(getIntent().getSerializableExtra(KEY));
        
        UserDetailBinding b = DBinding.bindViewModel(this, R.layout.user_detail, mUserVm);

        mUserVm.setOnClick(v -> {
            if (v == b.changeBtn) {
                mUserVm.setPic(BitmapFactory.decodeResource(getResources(), R.drawable.kale));
                mUserVm.setName("Kale");
            } else if (v == b.headPicIv) {
                Toast.makeText(UserDetailActivity.this, "点击了头像", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
