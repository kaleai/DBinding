package vm; 
import android.databinding.Bindable;
import kale.dbinding.BaseViewModel;
import com.android.databinding.library.baseAdapters.BR;
/** Generated code from DBinding. Do not modify! */
public class UserViewModel extends BaseViewModel<UserViewModel> {

    private android.graphics.Bitmap pic;
    public void setPic(android.graphics.Bitmap pic) {
        this.pic = pic;
        notifyPropertyChanged(BR.pic);
    }
    @Bindable public android.graphics.Bitmap getPic() {return this.pic;}

    private java.lang.CharSequence name;
    public void setName(java.lang.CharSequence name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable public java.lang.CharSequence getName() {return this.name;}}