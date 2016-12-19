package kale.viewmodel;

import vm.CommonViewModel;

/**
 * @author Kale
 * @date 2016/12/19
 */

public class ViewModelWrapper {

    protected CommonViewModel viewModel;

    public ViewModelWrapper(CommonViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public CommonViewModel getCommonVm() {
        return viewModel;
    }

}
