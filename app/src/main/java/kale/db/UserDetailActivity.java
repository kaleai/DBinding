package kale.db;

import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

import kale.db.base.BaseActivity;
import kale.db.databinding.UserDetailActivityBinding;
import kale.dbinding.data.ObservableBitmap;
import kale.dbinding.data.ObservableCharSequence;

/**
 * @author Kale
 * @date 2015/12/26
 */
@Dispatcher
public class UserDetailActivity extends BaseActivity<UserDetailActivityBinding> {

    @Arg
    ObservableCharSequence name;

    @Arg
    ObservableBitmap pic;

    @Override
    protected int getLayoutResId() {
        return R.layout.user_detail_activity;
    }

    @Override
    protected void beforeSetViews() {
    }

    @Override
    protected void setViews() {
        b.setEvent(viewEvents);
        b.setPic(pic);
        b.setName(name);

        b.nameTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                name.set(editable.toString());
            }
        });
        
        viewEvents.setOnClick(v -> {
            if (v == b.changeBtn) {
                pic.set(BitmapFactory.decodeResource(getResources(), R.drawable.kale));
                name.set("Kale");
            } else if (v == b.headPicIv) {
                Toast.makeText(UserDetailActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void doTransaction() {

    }

}
