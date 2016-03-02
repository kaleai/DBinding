package kale.db;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import kale.db.databinding.UserDetailBinding;
import kale.dbinding.DBinding;
import kale.dbinding.util.SerializableViewModel;
import vm.EventViewModel;
import vm.UserViewModel;

/**
 * @author Kale
 * @date 2015/12/26
 */
@RequireBundler
public class UserDetailActivity extends AppCompatActivity {

    private EventViewModel mEvent = new EventViewModel();

    @Arg
    public SerializableViewModel<UserViewModel> mSerializableUserVm;
    
    private UserViewModel mUserVm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundler.inject(this);
        mUserVm = mSerializableUserVm.toViewModel();        
        
        UserDetailBinding b = DBinding.bindViewModel(this, R.layout.user_detail, mEvent, mUserVm);

        // 不能同时设置两个click
        mEvent.setOnClick(v -> {
            if (v == b.changeBtn) {
                mUserVm.setPic(BitmapFactory.decodeResource(getResources(), R.drawable.kale));
                mUserVm.setName("Kale");
            } else if (v == b.headPicIv) {
                Toast.makeText(UserDetailActivity.this, "点击了头像", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
