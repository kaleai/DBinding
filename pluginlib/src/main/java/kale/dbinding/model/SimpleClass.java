package kale.dbinding.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kale
 * @date 2015/12/24
 */
public class SimpleClass {

    public String packageName;

    public String name;

    public String simpleName;

    public List<SimpleField> fields;

    public String content;

    public String clsVarName;

    public SimpleClass(String name) {
        fields = new ArrayList<>();
        String[] fullNames = name.split("\\.");
        
        this.simpleName = fullNames[fullNames.length - 1];
        this.packageName = name.substring(0, name.indexOf(simpleName) - 1);
        this.name = name;
    }
}
