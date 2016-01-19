package kale.dbinding.model;

/**
 * @author Kale
 * @date 2015/12/23
 */
public class SimpleField {
    
    public String type;

    public String name;

    public SimpleField(String type, String name) {
        if (type == null) {
            type = Object.class.getCanonicalName();
        }
        this.type = type;
        this.name = name;
    }
}
