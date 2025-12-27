package com.oracle.truffle.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.graalvm.options.OptionCategory;
import org.graalvm.options.OptionStability;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Option {
   String name() default "";

   String help();

   boolean deprecated() default false;

   String deprecationMessage() default "";

   OptionCategory category();

   OptionStability stability() default OptionStability.EXPERIMENTAL;

   String usageSyntax() default "";

   @Retention(RetentionPolicy.CLASS)
   @Target(ElementType.TYPE)
   public @interface Group {
      String[] value();
   }
}
