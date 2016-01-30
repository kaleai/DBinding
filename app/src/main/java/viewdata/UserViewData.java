package viewdata; 
import android.databinding.Bindable;
import kale.dbinding.BaseViewData;
import com.android.databinding.library.baseAdapters.BR;
public class UserViewData extends BaseViewData {

    private android.graphics.Bitmap pic;
    public final void setPic(android.graphics.Bitmap pic) {
        this.pic = pic;
        notifyPropertyChanged(BR.pic);
    }
    @Bindable public final android.graphics.Bitmap getPic() {return this.pic;}

    private android.graphics.Bitmap right;
    public final void setRight(android.graphics.Bitmap right) {
        this.right = right;
        notifyPropertyChanged(BR.right);
    }
    @Bindable public final android.graphics.Bitmap getRight() {return this.right;}

    private java.lang.CharSequence name;
    public final void setName(java.lang.CharSequence name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable public final java.lang.CharSequence getName() {return this.name;}}