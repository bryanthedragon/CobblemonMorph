package org.graalvm.nativeimage;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import org.graalvm.nativeimage.impl.ImageBuildtimeCodeAnnotationAccessSupport;

public final class AnnotationAccess {
   public static boolean isAnnotationPresent(AnnotatedElement element, Class<? extends Annotation> annotationClass) {
      return ImageInfo.inImageBuildtimeCode()
         ? ImageSingletons.lookup(ImageBuildtimeCodeAnnotationAccessSupport.class).isAnnotationPresent(element, annotationClass)
         : element.isAnnotationPresent(annotationClass);
   }

   public static <T extends Annotation> T getAnnotation(AnnotatedElement element, Class<T> annotationType) {
      return (T)(ImageInfo.inImageBuildtimeCode()
         ? ImageSingletons.lookup(ImageBuildtimeCodeAnnotationAccessSupport.class).getAnnotation(element, annotationType)
         : element.getAnnotation(annotationType));
   }

   public static Class<? extends Annotation>[] getAnnotationTypes(AnnotatedElement element) {
      return ImageInfo.inImageBuildtimeCode()
         ? ImageSingletons.lookup(ImageBuildtimeCodeAnnotationAccessSupport.class).getAnnotationTypes(element)
         : Arrays.stream(element.getAnnotations()).map(Annotation::annotationType).toArray(Class[]::new);
   }

   private AnnotationAccess() {
   }
}
