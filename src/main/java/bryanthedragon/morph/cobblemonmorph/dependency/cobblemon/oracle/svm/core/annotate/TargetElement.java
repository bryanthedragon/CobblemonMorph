
package com.oracle.svm.core.annotate;

import com.oracle.svm.core.annotate.TargetClass;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
@Platforms(value={Platform.HOSTED_ONLY.class})
public @interface TargetElement {
    public static final String CONSTRUCTOR_NAME = "<init>";

    public String name() default "";

    public Class<?>[] onlyWith() default {TargetClass.AlwaysIncluded.class};
}

