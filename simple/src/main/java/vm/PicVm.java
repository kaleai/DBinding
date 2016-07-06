package vm; 
import android.databinding.Bindable;
import kale.dbinding.BaseViewModel;
import com.android.databinding.library.baseAdapters.BR;
/** Generated code from DBinding. Do not modify! */
public class PicVm extends BaseViewModel<PicVm> {

    private java.lang.CharSequence isLikeText;
    public void setIsLikeText(java.lang.CharSequence isLikeText) {
        this.isLikeText = isLikeText;
        notifyPropertyChanged(BR.isLikeText);
    }
    @Bindable public java.lang.CharSequence getIsLikeText() {return this.isLikeText;}

    private java.lang.CharSequence time;
    public void setTime(java.lang.CharSequence time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }
    @Bindable public java.lang.CharSequence getTime() {return this.time;}

    private java.lang.Object picUrl;
    public void setPicUrl(java.lang.Object picUrl) {
        this.picUrl = picUrl;
        notifyPropertyChanged(BR.picUrl);
    }
    @Bindable public java.lang.Object getPicUrl() {return this.picUrl;}}