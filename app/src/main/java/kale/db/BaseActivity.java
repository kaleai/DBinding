package kale.db;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

import kale.dbinding.DBinding;
import kale.dbinding.IViewData;
import kale.dbinding.ViewDataManager;

/**
 * @author Kale
 * @date 2015/12/27
 * 
 * 如果是不希望外部调用的public方法，可以在取名上做处理。比如做onXXXX，不用用getXXX
 */
public class BaseActivity extends AppCompatActivity {

    List<IViewData> viewDataList;

    protected void injectAllViewData() {
        viewDataList = DBinding.injectViewData(this);
    }
    
    /**
     * 界面结束后销毁当前界面的数据对象 
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewDataManager.getInstance().removeViewData(this, viewDataList);
    }
}
