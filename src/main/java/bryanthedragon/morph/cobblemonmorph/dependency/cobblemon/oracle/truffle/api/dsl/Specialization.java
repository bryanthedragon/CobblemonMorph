
package com.oracle.truffle.api.dsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface Specialization {
    public String insertBefore() default "";

    public Class<? extends Throwable>[] rewriteOn() default {};

    public String[] replaces() default {};

    public String[] guards() default {};

    public String[] assumptions() default {};

    public String limit() default "";
}

