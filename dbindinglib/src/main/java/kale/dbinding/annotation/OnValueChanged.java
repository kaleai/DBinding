package kale.dbinding.annotation;

import kale.dbinding.BaseViewData;

/**
 * @author Kale
 * @date 2016/1/11
 */
public @interface OnValueChanged {

    Class<? extends BaseViewData> viewData();
}
