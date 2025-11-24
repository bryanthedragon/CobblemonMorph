
package com.oracle.truffle.api.dsl;

import com.oracle.truffle.api.dsl.NodeChildren;
import com.oracle.truffle.api.nodes.Node;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.TYPE})
@Repeatable(value=NodeChildren.class)
public @interface NodeChild {
    public String value() default "";

    public Class<?> type() default Node.class;

    public String[] executeWith() default {};

    public boolean implicit() default false;

    public String implicitCreate() default "create()";

    public boolean allowUncached() default false;

    public String uncached() default "getUncached()";
}

