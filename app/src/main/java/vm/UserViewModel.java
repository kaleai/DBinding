package vm; 
import android.databinding.Bindable;
import kale.dbinding.BaseViewModel;
import com.android.databinding.library.baseAdapters.BR;
public class UserViewModel extends BaseViewModel {

    private android.support.v7.widget.RecyclerView.Adapter adapter;
    public final void setAdapter(android.support.v7.widget.RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }
    @Bindable public final android.support.v7.widget.RecyclerView.Adapter getAdapter() {return this.adapter;}

    private android.graphics.Bitmap pic;
    public final void setPic(android.graphics.Bitmap pic) {
        this.pic = pic;
        notifyPropertyChanged(BR.pic);
    }
    @Bindable public final android.graphics.Bitmap getPic() {return this.pic;}

    private android.view.View.OnClickListener onClick;
    public final void setOnClick(android.view.View.OnClickListener onClick) {
        this.onClick = onClick;
        notifyPropertyChanged(BR.onClick);
    }
    @Bindable public final android.view.View.OnClickListener getOnClick() {return this.onClick;}

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