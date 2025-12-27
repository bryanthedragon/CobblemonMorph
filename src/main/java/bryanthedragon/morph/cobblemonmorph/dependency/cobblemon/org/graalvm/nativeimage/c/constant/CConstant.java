package org.graalvm.nativeimage.c.constant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;
import org.graalvm.nativeimage.impl.CConstantValueSupport;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CConstant {
   String value() default "";

   public static final class ValueAccess {
      private ValueAccess() {
      }

      @Platforms(Platform.HOSTED_ONLY.class)
      public static <T> T get(Class<?> declaringClass, String methodName, Class<T> returnType) {
         return ImageSingletons.lookup(CConstantValueSupport.class).getCConstantValue(declaringClass, methodName, returnType);
      }
   }
}
