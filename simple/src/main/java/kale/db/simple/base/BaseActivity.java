package kale.db.simple.base;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import kale.dbinding.DBinding;

/**
 * @author Kale
 * @date 2016/7/5
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected T b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
