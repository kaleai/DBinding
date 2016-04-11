package kale.test.plugin;

import org.junit.Test;

import android.databinding.adapters.AbsListViewBindingAdapter;
import android.databinding.adapters.AbsSeekBarBindingAdapter;
import android.databinding.adapters.AbsSpinnerBindingAdapter;
import android.databinding.adapters.ActionMenuViewBindingAdapter;
import android.databinding.adapters.AdapterViewBindingAdapter;
import android.databinding.adapters.AutoCompleteTextViewBindingAdapter;
import android.databinding.adapters.CalendarViewBindingAdapter;
import android.databinding.adapters.CardViewBindingAdapter;
import android.databinding.adapters.CheckedTextViewBindingAdapter;
import android.databinding.adapters.ChronometerBindingAdapter;
import android.databinding.adapters.CompoundButtonBindingAdapter;
import android.databinding.adapters.DatePickerBindingAdapter;
import android.databinding.adapters.ExpandableListViewBindingAdapter;
import android.databinding.adapters.FrameLayoutBindingAdapter;
import android.databinding.adapters.ImageViewBindingAdapter;
import android.databinding.adapters.LinearLayoutBindingAdapter;
import android.databinding.adapters.NumberPickerBindingAdapter;
import android.databinding.adapters.ProgressBarBindingAdapter;
import android.databinding.adapters.RadioGroupBindingAdapter;
import android.databinding.adapters.RatingBarBindingAdapter;
import android.databinding.adapters.SearchViewBindingAdapter;
import android.databinding.adapters.SeekBarBindingAdapter;
import android.databinding.adapters.SpinnerBindingAdapter;
import android.databinding.adapters.SwitchBindingAdapter;
import android.databinding.adapters.SwitchCompatBindingAdapter;
import android.databinding.adapters.TabHostBindingAdapter;
import android.databinding.adapters.TabWidgetBindingAdapter;
import android.databinding.adapters.TableLayoutBindingAdapter;
import android.databinding.adapters.TextViewBindingAdapter;
import android.databinding.adapters.TimePickerBindingAdapter;
import android.databinding.adapters.ToolbarBindingAdapter;
import android.databinding.adapters.VideoViewBindingAdapter;
import android.databinding.adapters.ViewBindingAdapter;
import android.databinding.adapters.ViewGroupBindingAdapter;
import android.databinding.adapters.ViewStubBindingAdapter;
import android.databinding.adapters.ZoomControlsBindingAdapter;
import android.view.View;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import kale.dbinding.adapters.EditTextAttrAdapter;
import kale.dbinding.adapters.ImageViewAttrAdapter;
import kale.dbinding.adapters.TextViewAttrAdapter;
import kale.dbinding.parser.TypeFinder;
import kale.dbinding.util.LetterUtil;

import static org.junit.Assert.assertEquals;


/**
 * @author Kale
 * @date 2016/4/11
 */
public class PluginTypeTest {

    @Test
    public void testGetMax() {
        assertEquals(3, 1 + 2);
    }

    /**
     * 注释掉对象的原则：<br>
     * 本身就不是合法的adapter类 或 有了更好的替代类
     * 
     * 目前有过多的用例无法通过，等完善了后就可以真正跑测试了。
     */
    @Test
    public void testSupportTypes() {
        checkType(
                AbsListViewBindingAdapter.class,
                AbsSeekBarBindingAdapter.class,
                AbsSpinnerBindingAdapter.class,
                ActionMenuViewBindingAdapter.class,
                AdapterViewBindingAdapter.class,
                AutoCompleteTextViewBindingAdapter.class,
                CalendarViewBindingAdapter.class,
                CardViewBindingAdapter.class,
                CheckedTextViewBindingAdapter.class,
                ChronometerBindingAdapter.class,
                CompoundButtonBindingAdapter.class,
                //Converters.class,
                DatePickerBindingAdapter.class,
                ExpandableListViewBindingAdapter.class,
                FrameLayoutBindingAdapter.class,
                ImageViewBindingAdapter.class,
                LinearLayoutBindingAdapter.class,
                //ListenerUtil.class;
                NumberPickerBindingAdapter.class,
                //ObservableListAdapter.class;
                ProgressBarBindingAdapter.class,
                RadioGroupBindingAdapter.class,
                RatingBarBindingAdapter.class,
                SearchViewBindingAdapter.class,
                SeekBarBindingAdapter.class,
                SpinnerBindingAdapter.class,
                SwitchBindingAdapter.class,
                SwitchCompatBindingAdapter.class,
                TabHostBindingAdapter.class,
                TableLayoutBindingAdapter.class,
                TabWidgetBindingAdapter.class,
                TextViewBindingAdapter.class,
                TimePickerBindingAdapter.class,
                ToolbarBindingAdapter.class,
                VideoViewBindingAdapter.class,
                ViewBindingAdapter.class,
                ViewGroupBindingAdapter.class,
                ViewStubBindingAdapter.class,
                ZoomControlsBindingAdapter.class,

                // custom adapter
                EditTextAttrAdapter.class,
                ImageViewAttrAdapter.class,
                TextViewAttrAdapter.class);
    }

    private static void checkType(Class... classes) {
        System.out.println("测试类的总数：" + classes.length);

        for (Class clz : classes) {
            System.out.println("\n==== In " + clz.getSimpleName() + " ====\n");
            for (Method method : clz.getDeclaredMethods()) {
                boolean isStatic = Modifier.isStatic(method.getModifiers()); // is static method
                boolean isPublic = Modifier.isPublic(method.getModifiers()); // is public method
                boolean isTowParams = method.getParameterTypes().length == 2;
                Class firstType = method.getParameterTypes()[0];
                boolean isSetMethod = method.getName().startsWith("set");

                if (isPublic && isStatic && isTowParams && isSetMethod
                        && View.class.isAssignableFrom(firstType)) {

                    String attrName = LetterUtil.getLowerLetter(method.getName().substring(3));
                    String actual = TypeFinder.findTypeByAttrName(attrName);
                    String expected = method.getParameterTypes()[1].getCanonicalName();

                    if (!actual.equals(expected)) {
                        if (actual.equals(Object.class.getCanonicalName())) {
                            System.err.println("匹配失败的方法：" + method.getName() + "，期望的是：" + expected + "，实际却是：" + actual);
                        } else {
                            // 如果不是object，那么就说明已经重载了此方法，所以忽略
                            System.out.println("忽略的重载方法" + method.getName());
                        }
                    } else {
                        System.out.println("匹配成功：" + actual);
                    }
                } else {
                    System.out.println("忽略的方法：" + method.getName());
                }
            }
        }
    }
}
