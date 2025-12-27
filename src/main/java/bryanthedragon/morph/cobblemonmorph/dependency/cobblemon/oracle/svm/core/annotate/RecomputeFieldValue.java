package com.oracle.svm.core.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Platforms(Platform.HOSTED_ONLY.class)
public @interface RecomputeFieldValue {
   RecomputeFieldValue.Kind kind();

   Class<?> declClass() default RecomputeFieldValue.class;

   String declClassName() default "";

   String name() default "";

   boolean isFinal() default false;

   boolean disableCaching() default false;

   public static enum Kind {
      None,
      Reset,
      NewInstance,
      NewInstanceWhenNotNull,
      FromAlias,
      FieldOffset,
      ArrayBaseOffset,
      ArrayIndexScale,
      ArrayIndexShift,
      AtomicFieldUpdaterOffset,
      TranslateFieldOffset,
      Manual,
      Custom;
   }
}
