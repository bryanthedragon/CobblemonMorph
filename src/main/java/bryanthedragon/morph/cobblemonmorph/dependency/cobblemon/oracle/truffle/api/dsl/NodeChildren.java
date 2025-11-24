
package com.oracle.truffle.api.dsl;

import com.oracle.truffle.api.dsl.NodeChild;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.TYPE})
public @interface NodeChildren {
    public NodeChild[] value() default {};
}

