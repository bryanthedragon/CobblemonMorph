
package org.graalvm.nativeimage.c.constant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface CEnum {
    public String value() default "";

    public boolean addEnumKeyword() default false;
}

