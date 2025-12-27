package com.oracle.truffle.api.dsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Inherited
public @interface ReportPolymorphism {
   @Retention(RetentionPolicy.CLASS)
   @Target({ElementType.METHOD, ElementType.TYPE})
   @Inherited
   public @interface Exclude {
   }

   @Retention(RetentionPolicy.CLASS)
   @Target(ElementType.METHOD)
   public @interface Megamorphic {
   }
}
