package kale.dbinding;

import android.databinding.ObservableField;

/**
 * @author Kale
 * @date 2016/12/19
 */
public class ObservableString extends ObservableField<String> {

    public ObservableString(String value) {
        super(value);
    }

    public ObservableString() {
    }

    public static ObservableString create(String str) {
        return new ObservableString(str);
    }
}
