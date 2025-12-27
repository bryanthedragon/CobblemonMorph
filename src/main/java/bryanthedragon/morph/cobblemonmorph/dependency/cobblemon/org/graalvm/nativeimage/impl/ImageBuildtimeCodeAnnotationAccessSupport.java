package org.graalvm.nativeimage.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public interface ImageBuildtimeCodeAnnotationAccessSupport {
   boolean isAnnotationPresent(AnnotatedElement element, Class<? extends Annotation> annotationClass);

   Annotation getAnnotation(AnnotatedElement element, Class<? extends Annotation> annotationType);

   Class<? extends Annotation>[] getAnnotationTypes(AnnotatedElement element);
}
