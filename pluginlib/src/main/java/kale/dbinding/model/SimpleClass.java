package kale.dbinding.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kale
 * @date 2015/12/24
 */
public class SimpleClass {

    public String packageName;

    public String fullName;

    public String simpleName;

    public List<SimpleField> fields;

    public String content;

    public String clsVarName;

    /**
     * @param fullName com.kale.vm.ViewModel
     */
    public SimpleClass(String fullName) {
        fields = new ArrayList<>();
        String[] fullNames = fullName.split("\\.");

        this.simpleName = fullNames[fullNames.length - 1]; // ViewModel
        this.packageName = fullName.substring(0, fullName.indexOf(simpleName) - 1); // com.kale.vm
        this.fullName = fullName; // com.kale.vm.ViewModel
    }
    
}
