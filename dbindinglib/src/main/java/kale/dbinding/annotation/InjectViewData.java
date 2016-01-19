package kale.dbinding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kale
 *
 *         用这个注解后，会保证标记此注解的同一类型的变量在内存中唯一
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectViewData {

    boolean isSingle() default false;

}
