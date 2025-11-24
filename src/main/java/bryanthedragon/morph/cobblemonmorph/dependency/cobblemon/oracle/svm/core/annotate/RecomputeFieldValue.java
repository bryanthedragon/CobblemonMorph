
package com.oracle.svm.core.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
@Platforms(value={Platform.HOSTED_ONLY.class})
public @interface RecomputeFieldValue {
    public Kind kind();

    public Class<?> declClass() default RecomputeFieldValue.class;

    public String declClassName() default "";

    public String name() default "";

    public boolean isFinal() default false;

    public boolean disableCaching() default false;

    public static enum Kind {
        None,
        Reset,
        NewInstance,
        NewInstanceWhenNotNull,
        FromAlias,
        FieldOffset,
        ArrayBaseOffset,
        ArrayIndexScale,
        ArrayIndexShift,
        AtomicFieldUpdaterOffset,
        TranslateFieldOffset,
        Manual,
        Custom;

    }
}

