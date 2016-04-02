package kale.dbinding.adapters;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

/**
 * @author Kale
 * @date 2016/1/30
 */
public class RecyclerViewAttrAdapter {

    @BindingAdapter("app:rcvAdapter")
    public static void setAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
    }
}