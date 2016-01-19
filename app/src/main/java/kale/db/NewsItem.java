package kale.db;

import org.kale.vd.NewsViewData;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import kale.db.databinding.NewsItemBinding;
import kale.dbinding.DBinding;
import kale.dbinding.OnValueChangedCallback;
import kale.dbinding.annotation.InjectViewData;

/**
 * @author Kale
 * @date 2016/1/6
 *
 * 这里还是需要viewData的，比如点击这个item进入后更新了阅读数，这个item也需要被更新
 */
public class NewsItem {

    // 这里是list中的item，每个viewdata必须new出来，不能用注解。因为注解是保证全局唯一的viewdata，这里却是
    //会有很多个同一类型对象
    
    @InjectViewData(isSingle = true)
    NewsViewData mData;

    NewsItemBinding b;
    
    protected void onBindView(View root) {
        DBinding.injectViewData(this);
        b = DBinding.setVariables(DBinding.bind(root), mData);
    }

    private void startAct() {
        
        //ViewDataManager.getInstance().putViewData(getClass(), mData, this);
    }
    
    
    /**
     * 将viewData和model的数据进行同步
     * model模型可能很复杂，但viewData的模型很简单，这里就是做二者的转换。
     * 转换的步骤不可少，所以不能把model写成viewData
     */
    private void updateData(Resources res, final NewsModel model, NewsViewData vd) {
        vd.setTitle(model.title);
        vd.setContent(model.newsDetail.content);
        vd.setPic(BitmapFactory.decodeResource(res, R.drawable.kale));
    }

    /**
     * UI层面的操作。
     * 数据改变后ui会做一些改变，这里是一个数据改变后引起多个view的属性改变的情况，是一对多的情形。
     *
     * @param bind 这种情况在ui层面进行处理，而且希望是统一的做处理，独立出来。
     *             为什么不是单一监听器，而是观察者模式。
     *             因为多个页面会有对同一个数据的监听操作，如果是单一的那么就没办法实现这个功能。
     */
    public void notifyData(final NewsItemBinding bind) {
        bind.getNews().addOnValueChangedCallback(getClass(), new OnValueChangedCallback<NewsViewData>() {
            @Override
            public void afterChanged(int propertyId, NewsViewData viewData) {
                if (propertyId == BR.title) {
                    setSmartText(bind.title, viewData.getTitle());
                } else if (propertyId == BR.content) {
                    setSmartText(bind.content, viewData.getContent());
                }
            }
        });
    }

    public void setSmartText(TextView textView, CharSequence text) {
        textView.setVisibility(!TextUtils.isEmpty(text) ? View.VISIBLE : View.GONE);
    }
}
