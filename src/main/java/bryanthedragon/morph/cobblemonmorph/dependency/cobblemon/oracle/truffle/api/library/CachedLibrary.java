
package com.oracle.truffle.api.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.PARAMETER})
public @interface CachedLibrary {
    public String value() default "";

    public String limit() default "";
}

