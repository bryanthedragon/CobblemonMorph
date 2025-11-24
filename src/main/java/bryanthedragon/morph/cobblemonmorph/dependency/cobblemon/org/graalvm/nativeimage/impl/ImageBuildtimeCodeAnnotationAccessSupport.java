
package org.graalvm.nativeimage.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public interface ImageBuildtimeCodeAnnotationAccessSupport {
    public boolean isAnnotationPresent(AnnotatedElement var1, Class<? extends Annotation> var2);

    public Annotation getAnnotation(AnnotatedElement var1, Class<? extends Annotation> var2);

    public Class<? extends Annotation>[] getAnnotationTypes(AnnotatedElement var1);
}

