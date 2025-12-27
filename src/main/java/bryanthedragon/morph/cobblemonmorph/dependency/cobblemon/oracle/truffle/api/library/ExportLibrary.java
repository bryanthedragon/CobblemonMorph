package com.oracle.truffle.api.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(ExportLibrary.Repeat.class)
public @interface ExportLibrary {
   Class<? extends Library> value();

   Class<?> receiverType() default Void.class;

   String delegateTo() default "";

   int priority() default 0;

   String transitionLimit() default "";

   boolean useForAOT() default false;

   int useForAOTPriority() default 0;

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE)
   public @interface Repeat {
      ExportLibrary[] value();
   }
}
