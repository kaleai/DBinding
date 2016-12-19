package kale.db;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

import android.graphics.BitmapFactory;
import android.widget.Toast;

import kale.db.base.BaseActivity;
import kale.db.databinding.UserDetailActivityBinding;
import kale.dbinding.DBinding;
import kale.dbinding.ViewModelLayout;
import kale.dbinding.util.SerializableViewModel;
import kale.viewmodel.UserDetailActivityVm;
import vm.CommonViewModel;

/**
 * @author Kale
 * @date 2015/12/26
 */
@Dispatcher
@ViewModelLayout(R.layout.user_detail_activity)
public class UserDetailActivity extends BaseActivity<UserDetailActivityBinding> {

    @Arg
    SerializableViewModel serializableCommonVm;

    private UserDetailActivityVm vm;

    @Override
    protected int getLayoutResId() {
        return R.layout.user_detail_activity;
    }

    @Override
    protected void beforeSetViews() {
        commonVm = (CommonViewModel) serializableCommonVm.toViewModel();
        vm = new UserDetailActivityVm(commonVm);
    }

    @Override
    protected void setViews() {
        DBinding.setVariables(b, viewEvents, commonVm);

        viewEvents.setOnClick(v -> {
            if (v == b.changeBtn) {
                vm.setPic(BitmapFactory.decodeResource(getResources(), R.drawable.kale));
                vm.setName("Kale");
            } else if (v == b.headPicIv) {
                Toast.makeText(UserDetailActivity.this, "点击了头像", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void doTransaction() {

    }

}
