package com.oracle.truffle.api.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GenerateLibrary {
   Class<? extends Library> assertions() default Library.class;

   Class<?> receiverType() default Object.class;

   boolean defaultExportLookupEnabled() default false;

   boolean dynamicDispatchEnabled() default true;

   boolean pushEncapsulatingNode() default true;

   @Retention(RetentionPolicy.CLASS)
   @Target(ElementType.METHOD)
   public @interface Abstract {
      String[] ifExported() default {};
   }

   @Retention(RetentionPolicy.CLASS)
   @Target(ElementType.TYPE)
   @Repeatable(GenerateLibrary.DefaultExport.Repeat.class)
   public @interface DefaultExport {
      Class<?> value();

      @Retention(RetentionPolicy.CLASS)
      @Target(ElementType.TYPE)
      public @interface Repeat {
         GenerateLibrary.DefaultExport[] value();
      }
   }
}
