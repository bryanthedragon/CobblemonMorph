package org.graalvm.nativeimage.c.struct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.IntUnaryOperator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RawStructure {
   Class<? extends IntUnaryOperator> sizeProvider() default IntUnaryOperator.class;
}
