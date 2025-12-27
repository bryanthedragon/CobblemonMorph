package com.oracle.truffle.api.dsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.PARAMETER)
public @interface Cached {
   String value() default "create($parameters)";

   String uncached() default "getUncached($parameters)";

   int dimensions() default -1;

   boolean allowUncached() default false;

   String[] parameters() default {};

   boolean weak() default false;

   boolean adopt() default true;

   @Retention(RetentionPolicy.CLASS)
   @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
   public @interface Exclusive {
   }

   @Retention(RetentionPolicy.CLASS)
   @Target(ElementType.PARAMETER)
   public @interface Shared {
      String value();
   }
}
