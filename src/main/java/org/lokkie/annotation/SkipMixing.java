package org.lokkie.annotation;

import java.lang.annotation.*;

/**
 * An annotation that indicates this member should not be included in MixIn operation.
 * Used together with Mixable class
 *
 * @author lokkie
 * @version 0.1
 * @see org.lokkie.types.Mixable
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface SkipMixing {
    /**
     * @return should skip in all cases (false) or only null-valued fields (true)
     */
    boolean nullOnly() default false;
}