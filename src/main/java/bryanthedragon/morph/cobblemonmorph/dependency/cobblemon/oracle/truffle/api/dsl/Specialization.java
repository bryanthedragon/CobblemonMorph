package com.oracle.truffle.api.dsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Specialization {
   String insertBefore() default "";

   Class<? extends Throwable>[] rewriteOn() default {};

   String[] replaces() default {};

   String[] guards() default {};

   String[] assumptions() default {};

   String limit() default "";
}
