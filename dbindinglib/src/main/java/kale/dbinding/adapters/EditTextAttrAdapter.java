package kale.dbinding.adapters;

import android.databinding.BindingAdapter;
import android.widget.EditText;

import kale.dbinding.data.ObservableCharSequence;

/**
 * @author Kale
 * @date 2016/1/15
 *
 * 如果是editText，那么设置text会改变text的字段，并且可以通过监听text字段来得到用户输入时的text。来达到双向绑定的功能
 */
public class EditTextAttrAdapter {

    @BindingAdapter("android:text")
    public static void setText(EditText view, ObservableCharSequence text) {
        view.setText(text.get());
//        TextViewBindingAdapter.setText(view, text.get());
    }

}
