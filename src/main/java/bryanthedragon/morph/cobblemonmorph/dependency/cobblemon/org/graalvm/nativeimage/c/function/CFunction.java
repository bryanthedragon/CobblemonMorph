
package org.graalvm.nativeimage.c.function;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface CFunction {
    public String value() default "";

    public Transition transition() default Transition.TO_NATIVE;

    public static enum Transition {
        TO_NATIVE,
        NO_TRANSITION;

    }
}

