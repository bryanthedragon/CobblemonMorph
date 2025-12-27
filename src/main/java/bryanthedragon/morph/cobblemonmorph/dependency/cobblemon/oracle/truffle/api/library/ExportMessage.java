package com.oracle.truffle.api.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Repeatable(ExportMessage.Repeat.class)
public @interface ExportMessage {
   String name() default "";

   Class<? extends Library> library() default Library.class;

   String limit() default "";

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD, ElementType.TYPE})
   public @interface Ignore {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD, ElementType.TYPE})
   public @interface Repeat {
      ExportMessage[] value();
   }
}
