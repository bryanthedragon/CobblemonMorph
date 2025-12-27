package org.graalvm.nativeimage.c.function;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CFunction {
   String value() default "";

   CFunction.Transition transition() default CFunction.Transition.TO_NATIVE;

   public static enum Transition {
      TO_NATIVE,
      NO_TRANSITION;
   }
}
