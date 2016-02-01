package kale.dbinding;

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

            + "public class %s extends BaseViewData {%s}";

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


    /**
     * Write this to config file when file is first created
     */
    public static final String CONFIG_TEMPLE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<resources>\n"
            + "    <!-- \n"
            + "        For original view.\n"
            + "        Example: android:text=\"name\"\n"
            + "    -->\n"
            + "\n"
            + "    <string name=\"text\">java.lang.CharSequence</string>\n"
            + "\n"
            + "    <!-- \n"
            + "        For Custom view. \n"
            + "        Example: app:customAttr=\"name\"\n"
            + "    -->\n"
            + "\n"
            + "    <string name=\"customAttr\">java.lang.CharSequence</string>\n"
            + "</resources>";

}
