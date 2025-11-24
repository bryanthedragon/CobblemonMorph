
package com.oracle.svm.core.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Platforms(value={Platform.HOSTED_ONLY.class})
public @interface TargetClass {
    public Class<?> value() default TargetClass.class;

    public String className() default "";

    public Class<? extends Function<TargetClass, String>> classNameProvider() default NoClassNameProvider.class;

    public String[] innerClass() default {};

    public Class<?>[] onlyWith() default {AlwaysIncluded.class};

    public static interface NoClassNameProvider
    extends Function<TargetClass, String> {
    }

    public static class AlwaysIncluded
    implements BooleanSupplier {
        AlwaysIncluded() {
        }

        @Override
        public boolean getAsBoolean() {
            return true;
        }
    }
}

