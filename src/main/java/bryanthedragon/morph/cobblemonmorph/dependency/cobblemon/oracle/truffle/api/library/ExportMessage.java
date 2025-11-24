
package com.oracle.truffle.api.library;

import com.oracle.truffle.api.library.Library;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.TYPE})
@Repeatable(value=Repeat.class)
public @interface ExportMessage {
    public String name() default "";

    public Class<? extends Library> library() default Library.class;

    public String limit() default "";

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.METHOD, ElementType.TYPE})
    public static @interface Ignore {
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.METHOD, ElementType.TYPE})
    public static @interface Repeat {
        public ExportMessage[] value();
    }
}

