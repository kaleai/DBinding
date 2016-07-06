package kale.db.simple.databinding;

import com.facebook.drawee.view.SimpleDraweeView;

import android.databinding.BindingAdapter;

/**
 * @author Kale
 * @date 2016/5/26
 */
public class SimpleDraweeViewAdapter {

    @BindingAdapter({"url"})
    public static void setUrl(SimpleDraweeView view, String url) {
        view.setImageURI(url);
    }

}