package kale.dbinding.adapters;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.annotation.IntDef;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Kale
 * @date 2016/1/15
 *
 * 如果是editText，那么设置text会改变text的字段，并且可以通过监听text字段来得到用户输入时的text。来达到双向绑定的功能
 */
public class EditTextAttrAdapter {

    private final static int BEFORE_CHANGED = 0;

    private final static int ON_CHANGED = 1;

    private final static int AFTER_CHANGED = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BEFORE_CHANGED, ON_CHANGED, AFTER_CHANGED})
    private @interface WatchType {

    }

    @BindingAdapter("app:onBeforeChanged")
    public static void beforeChanged(EditText view, final ObservableField<CharSequence> text) {
        changed(view, text, BEFORE_CHANGED);
    }

    @BindingAdapter("app:text01")
    public static void setText(EditText view, final ObservableField<CharSequence> text) {
        changed(view, text, AFTER_CHANGED);
    }

    private static void changed(EditText view, final ObservableField<CharSequence> text, final @WatchType int type) {
        if (text == null) {
            return;
        }
        // 因为这个方法会被多次调用，所以设置一个tag，避免多次设置watch
        if (view.getTag(31) != null) {
            view.setTag(31, true);
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (type == BEFORE_CHANGED) {
                        setText(text, s);
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (type == ON_CHANGED) {
                        setText(text, s);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (type == AFTER_CHANGED) {
                        setText(text, s);
                    }
                }
            });
        }
        // 检查 value 是否真的改变;这样可以避免 text view 中由于光标位置改变而引起的问题。
        CharSequence newText = text.get();
        if (!view.getText().toString().equals(newText)) {
            view.setText(newText);
        }
    }

    private static void setText(ObservableField<CharSequence> text, CharSequence s) {
        if (!TextUtils.equals(text.get(), s)) {
            text.set(s.toString());
        }
    }

}
