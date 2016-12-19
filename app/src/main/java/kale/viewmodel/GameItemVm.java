package kale.viewmodel;

import android.graphics.Bitmap;

import vm.CommonViewModel;

/**
 * @author Kale
 * @date 2016/12/18
 */

public class GameItemVm extends ViewModelWrapper {

    public GameItemVm(CommonViewModel viewModel) {
        super(viewModel);
    }

    public void setPic(Bitmap pic) {
        viewModel.setPic(pic);
    }

    public void setIcon(Bitmap icon) {
        viewModel.setIcon(icon);
    }

    public void setTitle(CharSequence title) {
        viewModel.setTitle(title);
    }

    public void setIsLikeText(CharSequence text) {
        viewModel.setIsLikeText(text);
    }

}
