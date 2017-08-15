package org.lokkie.annotation;
       
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that indicates this member should be stored to DataStore with
 * the provided name value as its field name.
 *
 * @author Lokkie
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface StorableName {
    
    /**
     * @return the desired name of the field when it is stored
     */
    String value();
    /**
     * @return the alternative names of the field when it is stored
     */
    String[] alternate() default {};
}
