
package com.oracle.truffle.api.library;

import com.oracle.truffle.api.library.Library;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface GenerateLibrary {
    public Class<? extends Library> assertions() default Library.class;

    public Class<?> receiverType() default Object.class;

    public boolean defaultExportLookupEnabled() default false;

    public boolean dynamicDispatchEnabled() default true;

    public boolean pushEncapsulatingNode() default true;

    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.METHOD})
    public static @interface Abstract {
        public String[] ifExported() default {};
    }

    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.TYPE})
    @Repeatable(value=Repeat.class)
    public static @interface DefaultExport {
        public Class<?> value();

        @Retention(value=RetentionPolicy.CLASS)
        @Target(value={ElementType.TYPE})
        public static @interface Repeat {
            public DefaultExport[] value();
        }
    }
}

