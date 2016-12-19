package vm; 
import android.databinding.Bindable;
import kale.dbinding.BaseViewModel;
import com.android.databinding.library.baseAdapters.BR;
/** Generated code from DBinding. Do not modify! */
public class GameItemVm extends BaseViewModel<GameItemVm> {

    private java.lang.CharSequence isLikedText;
    public void setIsLikedText(java.lang.CharSequence isLikedText) {
        this.isLikedText = isLikedText;
        notifyPropertyChanged(BR.isLikedText);
    }
    @Bindable public java.lang.CharSequence getIsLikedText() {return this.isLikedText;}}