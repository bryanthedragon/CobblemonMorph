
package com.oracle.svm.core.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.FIELD})
@Platforms(value={Platform.HOSTED_ONLY.class})
public @interface Substitute {
    public boolean polymorphicSignature() default false;
}

