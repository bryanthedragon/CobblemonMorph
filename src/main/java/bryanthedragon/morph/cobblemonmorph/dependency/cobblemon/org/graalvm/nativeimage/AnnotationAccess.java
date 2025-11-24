
package org.graalvm.nativeimage;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import org.graalvm.nativeimage.ImageInfo;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.impl.ImageBuildtimeCodeAnnotationAccessSupport;

public final class AnnotationAccess {
    public static boolean isAnnotationPresent(AnnotatedElement element, Class<? extends Annotation> annotationClass) {
        if (ImageInfo.inImageBuildtimeCode()) {
            return ImageSingletons.lookup(ImageBuildtimeCodeAnnotationAccessSupport.class).isAnnotationPresent(element, annotationClass);
        }
        return element.isAnnotationPresent(annotationClass);
    }

    public static <T extends Annotation> T getAnnotation(AnnotatedElement element, Class<T> annotationType) {
        if (ImageInfo.inImageBuildtimeCode()) {
            return (T)ImageSingletons.lookup(ImageBuildtimeCodeAnnotationAccessSupport.class).getAnnotation(element, annotationType);
        }
        return element.getAnnotation(annotationType);
    }

    public static Class<? extends Annotation>[] getAnnotationTypes(AnnotatedElement element) {
        if (ImageInfo.inImageBuildtimeCode()) {
            return ImageSingletons.lookup(ImageBuildtimeCodeAnnotationAccessSupport.class).getAnnotationTypes(element);
        }
        return (Class[])Arrays.stream(element.getAnnotations()).map(Annotation::annotationType).toArray(Class[]::new);
    }

    private AnnotationAccess() {
    }
}

