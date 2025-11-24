
package com.oracle.truffle.api.dsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.PARAMETER})
public @interface Cached {
    public String value() default "create($parameters)";

    public String uncached() default "getUncached($parameters)";

    public int dimensions() default -1;

    public boolean allowUncached() default false;

    public String[] parameters() default {};

    public boolean weak() default false;

    public boolean adopt() default true;

    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
    public static @interface Exclusive {
    }

    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.PARAMETER})
    public static @interface Shared {
        public String value();
    }
}

