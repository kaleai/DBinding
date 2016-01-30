package viewdata; 
import android.databinding.Bindable;
import kale.dbinding.BaseViewData;
import com.android.databinding.library.baseAdapters.BR;
public class NewsViewData extends BaseViewData {

    private java.lang.CharSequence title;
    public final void setTitle(java.lang.CharSequence title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
    @Bindable public final java.lang.CharSequence getTitle() {return this.title;}

    private android.graphics.Bitmap pic;
    public final void setPic(android.graphics.Bitmap pic) {
        this.pic = pic;
        notifyPropertyChanged(BR.pic);
    }
    @Bindable public final android.graphics.Bitmap getPic() {return this.pic;}

    private java.lang.CharSequence isLikeText;
    public final void setIsLikeText(java.lang.CharSequence isLikeText) {
        this.isLikeText = isLikeText;
        notifyPropertyChanged(BR.isLikeText);
    }
    @Bindable public final java.lang.CharSequence getIsLikeText() {return this.isLikeText;}}