package kale.db.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.view.View;

import kale.adapter.item.AdapterItem;
import kale.dbinding.DBinding;

/**
 * @author Kale
 * @date 2016/6/16
 */
public abstract class BaseAdapterItem<Bind extends ViewDataBinding, Data> implements AdapterItem<Data> {

    private View root;

    private int pos;

    protected Bind b;

    private Activity activity;

    private Data mData;

    public BaseAdapterItem(Activity activity) {
        this.activity = activity;
    }

    public BaseAdapterItem() {
    }

    @CallSuper
    @Override
    public void bindViews(View view) {
        root = view;
        b = DBinding.bind(view);
        beforeSetViews();
    }

    protected void beforeSetViews() {

    }

    @CallSuper
    @Override
    public void handleData(Data data, int position) {
        pos = position;
        this.mData = data;
    }

    public View getRoot() {
        return root;
    }

    public int getCurrentPosition() {
        return pos;
    }

    protected static void setVizOrInViz(View view, CharSequence str) {
        if (TextUtils.isEmpty(str)) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    protected static void setVizOrGone(View view, CharSequence str) {
        if (TextUtils.isEmpty(str)) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    protected int getColor(@ColorRes int colorResId) {
        return root.getResources().getColor(colorResId);
    }

    protected Context getContext() {
        return root.getContext();
    }

    public Activity getActivity() {
        if (activity == null) {
            Context context = root.getContext();
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else {
                activity = null;
            }
        }
        return activity;
    }

    public Data getData() {
        return mData;
    }
}
