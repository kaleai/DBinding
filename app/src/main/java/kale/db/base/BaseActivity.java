package kale.db.base;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lzh.compiler.parceler.Parceler;

import kale.dbinding.DBinding;
import vm.EventViewModel;

/**
 * @author Kale
 * @date 2016/7/5
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected EventViewModel viewEvents = new EventViewModel();

    protected T b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parceler.injectToEntity(this, getIntent());
        bindViews();
        beforeSetViews();
        setViews();
        doTransaction();
    }

    protected Activity getActivity() {
        return this;
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    protected void bindViews() {
        b = DBinding.bind(this, getLayoutResId());
    }

    protected abstract void beforeSetViews();

    protected abstract void setViews();

    protected abstract void doTransaction();
}
