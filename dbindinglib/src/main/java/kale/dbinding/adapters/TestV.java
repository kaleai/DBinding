package kale.dbinding.adapters;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import kale.dbinding.BaseViewData;

/**
 * @author Kale
 * @date 2016/1/15
 */
public class TestV extends BaseViewData {

    private ObservableField<CharSequence> text;

    public final void setText(CharSequence text) {
        setTextAndAddListener(this.text, text);
        //notifyPropertyChanged(BR.text);
    }
    @Bindable public final CharSequence getText() {return this.text.get();}

    
    private void setTextAndAddListener(ObservableField<CharSequence> c, CharSequence text) {
        if (c == null) {
            c = new ObservableField<>(text);
            c.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                   // notifyPropertyChanged(BR.text);
                }
            });
        } else {
            c.set(text);
        }
    }

}
