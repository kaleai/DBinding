package vm; 
import com.android.databinding.library.baseAdapters.BR;

import android.databinding.Bindable;

import kale.dbinding.BaseViewModel;
/** Generated code from DBinding. Do not modify! */
public class CommonViewModel extends BaseViewModel<CommonViewModel> {

    private android.graphics.Bitmap pic;
    public void setPic(android.graphics.Bitmap pic) {
        this.pic = pic;
        notifyPropertyChanged(BR.pic);
    }

    @Bindable
    public android.graphics.Bitmap getPic() {
        return this.pic;
    }

    private java.lang.CharSequence name;
    public void setName(java.lang.CharSequence name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public java.lang.CharSequence getName() {
        return this.name;
    }
}