package kale.dbinding.model;

/**
 * @author Kale
 * @date 2015/12/23
 */
public class SimpleField {
    
    public Class<?> type;

    public String name;

    public SimpleField(Class<?> type, String name) {

        if (type == null) {
            type = Object.class;
        }
        this.type = type;
        this.name = name;
    }
}
