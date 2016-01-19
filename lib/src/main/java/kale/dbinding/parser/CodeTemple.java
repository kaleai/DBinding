package kale.dbinding.parser;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

import kale.dbinding.annotation.Visibility;

/**
 * @author Kale
 * @date 2015/12/23
 */
public class CodeTemple {

    public static final String TYPE = "@type@";

    public static final String FIELD = "@field@";

    public static final String UP_FIELD = "@up_field@";

    public static final String CLASS_TEMPLE = "package %s; \n"
            + "import android.databinding.Bindable;\n"
            + "import kale.dbinding.BaseViewData;\n"
            + "import com.android.databinding.library.baseAdapters.BR;\n"

            + "public class %s extends BaseViewData<%s> {%s}";


    /**
     * for normal fields
     */
    public static final String FIELD_TEMPLE = "\n\n"
            + "    private " + TYPE + " " + FIELD + ";\n"
            + "    public final void set" + UP_FIELD + "(" + TYPE + " " + FIELD + ") {\n"
            + "        this." + FIELD + " = " + FIELD + ";\n"
            + "        notifyPropertyChanged(BR." + FIELD + ");\n"
            + "    }\n"
            + "    @Bindable public final " + TYPE + " get" + UP_FIELD + "() {return this." + FIELD + ";}";

    /**
     * for editText's text
     */
    public static final String EDIT_TEXT_FIELD_TEMPLE = "\n\n"
            + "    private ObservableField<CharSequence> " + FIELD + ";\n"
            + "    public final void set" + UP_FIELD + "(CharSequence " + FIELD + ") {\n"
            + "        setTextAndAddListener(this." + FIELD + ", " + FIELD + ");\n"
            + "        notifyPropertyChanged(BR." + FIELD + ");\n"
            + "    }\n"
            + "    @Bindable public final CharSequence get" + UP_FIELD + "() {return this." + FIELD + ".get();}";

    public static final Map<String, String> ATTR_FIELD_MAP = new HashMap<>();

    static {
        ATTR_FIELD_MAP.put("text", CharSequence.class.getCanonicalName());
        ATTR_FIELD_MAP.put("drawableLeft", Bitmap.class.getCanonicalName());
        ATTR_FIELD_MAP.put("visibility", "@" + Visibility.class.getCanonicalName() + " " + int.class.getCanonicalName());
        ATTR_FIELD_MAP.put("src", Bitmap.class.getCanonicalName());
    }
}
