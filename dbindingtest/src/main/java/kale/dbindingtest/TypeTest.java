package kale.dbindingtest;

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

/**
 * @author Kale
 * @date 2016/1/20
 */
public class TypeTest {

    /**
     * 这里注释掉的说明这些类不被自动生成ViewData的插件支持了.
     * 有些是因为本身就不是合法的adapter类，有些是因为有了更好的替代类。
     */
    public static void main(String[] args) {
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

                ________下面是自定义的适配器________.class,
                // custom adapter
                EditTextAttrAdapter.class,
                ImageViewAttrAdapter.class,
                TextViewAttrAdapter.class);
    }

    private static void checkType(Class... classes) {
        System.out.println("总共的数目：" + classes.length);
        for (Class clz : classes) {
            System.out.println("\n==== In " + clz.getSimpleName() + " ====\n");
            for (Method method : clz.getDeclaredMethods()) {
                boolean isStatic = Modifier.isStatic(method.getModifiers()); // is static method
                boolean isPublic = Modifier.isPublic(method.getModifiers()); // is public method
                int paramNum = method.getParameterTypes().length;
                Class firstType = method.getParameterTypes()[0];
                boolean isTargetMethod = method.getName().startsWith("set");

                if (isTargetMethod
                        && isPublic
                        && isStatic
                        && paramNum == 2
                        && View.class.isAssignableFrom(firstType)) {

                    String attrName = kale.dbinding.util.LetterUtil.getLowerLetter(method.getName().substring(3));
                    String got = TypeFinder.findTypeByAttrName(attrName);
                    String expected = method.getParameterTypes()[1].getCanonicalName();

                    if (!got.equals(expected)) {
                        /**
                         *如果{@link TypeFinder}已经处理过,这里就不做判定，否属于没有做处理的情况。
                         */
                        if (got.equals(Object.class.getCanonicalName())) {
                            System.err.println("匹配失败的方法：" + method.getName()
                                    + "，期望的是：" + expected + "，实际却是：" + got);
                        } else {
                            System.out.println("忽略的重载方法" + method.getName());
                        }
                    } else {
                        System.out.println("匹配成功：" + got);
                    }
                } else {
                    System.out.println("忽略的方法：" + method.getName());
                }
            }
        }
    }

}
