
package com.oracle.truffle.api.dsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.TYPE})
@Inherited
public @interface ReportPolymorphism {

    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.METHOD})
    public static @interface Megamorphic {
    }

    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.METHOD, ElementType.TYPE})
    @Inherited
    public static @interface Exclude {
    }
}

