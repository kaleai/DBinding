package vm; 
import android.databinding.Bindable;
import kale.dbinding.BaseViewModel;
import com.android.databinding.library.baseAdapters.BR;
public class GameViewModel extends BaseViewModel {

    private android.graphics.Bitmap pic;
    public final void setPic(android.graphics.Bitmap pic) {
        this.pic = pic;
        notifyPropertyChanged(BR.pic);
    }
    @Bindable public final android.graphics.Bitmap getPic() {return this.pic;}

    private android.graphics.Bitmap icon;
    public final void setIcon(android.graphics.Bitmap icon) {
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
    }
    @Bindable public final android.graphics.Bitmap getIcon() {return this.icon;}

    private java.lang.CharSequence title;
    public final void setTitle(java.lang.CharSequence title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
    @Bindable public final java.lang.CharSequence getTitle() {return this.title;}

    private java.lang.CharSequence isLikeText;
    public final void setIsLikeText(java.lang.CharSequence isLikeText) {
        this.isLikeText = isLikeText;
        notifyPropertyChanged(BR.isLikeText);
    }
    @Bindable public final java.lang.CharSequence getIsLikeText() {return this.isLikeText;}

    private android.view.View.OnClickListener onClick;
    public final void setOnClick(android.view.View.OnClickListener onClick) {
        this.onClick = onClick;
        notifyPropertyChanged(BR.onClick);
    }
    @Bindable public final android.view.View.OnClickListener getOnClick() {return this.onClick;}}