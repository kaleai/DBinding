package kale.db;

import com.lzh.courier.annoapi.Field;
import com.lzh.courier.annoapi.Params;

import android.graphics.BitmapFactory;
import android.widget.Toast;

import kale.db.base.BaseActivity;
import kale.db.databinding.UserDetailActivityBinding;
import kale.dbinding.DBinding;
import kale.dbinding.util.SerializableViewModel;
import vm.UserViewModel;

/**
 * @author Kale
 * @date 2015/12/26
 */
@Params(fields = {@Field(name = "vm", type = SerializableViewModel.class)})
public class UserDetailActivity extends BaseActivity<UserDetailActivityBinding> {

    private UserViewModel mUserVm;

    @Override
    protected int getLayoutResId() {
        return R.layout.user_detail_activity;
    }

    @Override
    protected void beforeSetViews() {
        UserDetailActivityArgsData data = UserDetailActivityArgsData.getArguments(getIntent());
        mUserVm = (UserViewModel) data.getVm().toViewModel();
    }

    @Override
    protected void setViews() {
        DBinding.setVariables(b, viewEvents, mUserVm);

        viewEvents.setOnClick(v -> {
            if (v == b.changeBtn) {
                mUserVm.setPic(BitmapFactory.decodeResource(getResources(), R.drawable.kale));
                mUserVm.setName("Kale");
            } else if (v == b.headPicIv) {
                Toast.makeText(UserDetailActivity.this, "点击了头像", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void doTransaction() {

    }

}
