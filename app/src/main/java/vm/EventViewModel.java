package vm; 
import android.databinding.Bindable;
import kale.dbinding.BaseViewModel;
import com.android.databinding.library.baseAdapters.BR;
public class EventViewModel extends BaseViewModel {

    private android.view.View.OnClickListener onClick;
    public final void setOnClick(android.view.View.OnClickListener onClick) {
        this.onClick = onClick;
        notifyPropertyChanged(BR.onClick);
    }
    @Bindable public final android.view.View.OnClickListener getOnClick() {return this.onClick;}}