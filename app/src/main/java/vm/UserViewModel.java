package vm;

import android.databinding.Bindable;

import kale.db.BR;
import kale.dbinding.BaseViewModel;
/** Generated code from DBinding. Do not modify! */
public class UserViewModel extends BaseViewModel<UserViewModel> {

    private android.support.v7.widget.RecyclerView.Adapter adapter;
    public void setAdapter(android.support.v7.widget.RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }
    @Bindable public android.support.v7.widget.RecyclerView.Adapter getAdapter() {return this.adapter;}

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