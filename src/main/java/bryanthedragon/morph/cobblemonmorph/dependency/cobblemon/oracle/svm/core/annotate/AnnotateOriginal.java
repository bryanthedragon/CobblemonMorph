package com.oracle.svm.core.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Platforms(Platform.HOSTED_ONLY.class)
public @interface AnnotateOriginal {
}
