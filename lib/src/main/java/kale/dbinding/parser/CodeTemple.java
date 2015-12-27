package kale.dbinding.parser;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kale
 * @date 2015/12/23
 */
class CodeTemple {

    public static final String TYPE = "@type@";

    public static final String FIELD = "@field@";

    public static final String UP_FIELD = "@up_field@";

    public static final String CLASS_TEMPLE = "package %s; \n"
            + "import android.databinding.BaseObservable;\n"
            + "import android.databinding.Bindable;\n"
            + "import com.android.databinding.library.baseAdapters.BR;\n"
            + "import kale.dbinding.IViewData;\n"
            + "import java.util.ArrayList;\n"
            + "import java.util.List;\n"
            
            + "public class %s extends BaseObservable implements IViewData {\n"
            + "    private List<Object> referenceList = new ArrayList<>();\n"
            + "    public List<Object> getReferenceList() {return referenceList;}"
            + "%s\n}";
    

    public static final String FIELD_TEMPLE = "\n\n"
            + "    private " + TYPE + " " + FIELD + ";\n"
            + "    public final void set" + UP_FIELD + "(" + TYPE + " " + FIELD + ") {\n"
            + "        this." + FIELD + " = " + FIELD + ";\n"
            + "        notifyPropertyChanged(BR." + FIELD + ");\n"
            + "    }\n"
            + "    @Bindable public final " + TYPE + " get" + UP_FIELD + "() {return this." + FIELD + ";}";


    public static final Map<String, Class<?>> FIELD_TYPE = new HashMap<>();
    static {
        FIELD_TYPE.put("text", String.class);
        FIELD_TYPE.put("onClick", View.OnClickListener.class);
    }
}
