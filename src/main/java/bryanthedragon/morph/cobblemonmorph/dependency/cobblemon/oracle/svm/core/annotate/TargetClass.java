package com.oracle.svm.core.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Platforms(Platform.HOSTED_ONLY.class)
public @interface TargetClass {
   Class<?> value() default TargetClass.class;

   String className() default "";

   Class<? extends Function<TargetClass, String>> classNameProvider() default TargetClass.NoClassNameProvider.class;

   String[] innerClass() default {};

   Class<?>[] onlyWith() default {TargetClass.AlwaysIncluded.class};

   public static class AlwaysIncluded implements BooleanSupplier {
      AlwaysIncluded() {
      }

      @Override
      public boolean getAsBoolean() {
         return true;
      }
   }

   public interface NoClassNameProvider extends Function<TargetClass, String> {
   }
}
