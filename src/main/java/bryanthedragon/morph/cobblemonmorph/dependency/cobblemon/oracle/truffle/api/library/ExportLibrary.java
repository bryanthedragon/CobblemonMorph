
package com.oracle.truffle.api.library;

import com.oracle.truffle.api.library.Library;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Repeatable(value=Repeat.class)
public @interface ExportLibrary {
    public Class<? extends Library> value();

    public Class<?> receiverType() default Void.class;

    public String delegateTo() default "";

    public int priority() default 0;

    public String transitionLimit() default "";

    public boolean useForAOT() default false;

    public int useForAOTPriority() default 0;

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface Repeat {
        public ExportLibrary[] value();
    }
}

