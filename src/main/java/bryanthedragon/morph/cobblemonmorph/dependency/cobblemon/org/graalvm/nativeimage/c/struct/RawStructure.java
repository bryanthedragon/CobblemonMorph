
package org.graalvm.nativeimage.c.struct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.IntUnaryOperator;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface RawStructure {
    public Class<? extends IntUnaryOperator> sizeProvider() default IntUnaryOperator.class;
}

