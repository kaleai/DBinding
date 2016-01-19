package kale.db;

import org.kale.vd.OtherViewData;

import kale.dbinding.ViewDataManager;

/**
 * @author Kale
 * @date 2016/1/13
 */
public class MainActivity$ViewDataInject {

    public void initViewData(MainActivity activity) {
        ViewDataManager manager = ViewDataManager.getInstance();
        
        /*activity.mUserViewData = new UserViewData();

        if (!manager.containsViewData(activity.mOtherViewData.getClass())) {
            activity.mOtherViewData = new OtherViewData();
        }*/
    }
    
    public static <T> T gen(String name) {
        if (name.equals("OtherViewData")) {
            return (T) new OtherViewData();
        } else {
            return null;
        } 
    }
}
