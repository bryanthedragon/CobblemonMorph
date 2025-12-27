package org.graalvm.nativeimage.c.function;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CLibrary {
   String value();

   boolean requireStatic() default false;

   String[] dependsOn() default {};
}
