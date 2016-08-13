package kale.dbinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kale
 * @date 2016/8/7
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface VmGenerator {

    String value();
}
