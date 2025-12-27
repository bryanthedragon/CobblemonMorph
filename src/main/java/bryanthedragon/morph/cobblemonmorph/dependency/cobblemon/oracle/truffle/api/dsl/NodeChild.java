package com.oracle.truffle.api.dsl;

import com.oracle.truffle.api.nodes.Node;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Repeatable(NodeChildren.class)
public @interface NodeChild {
   String value() default "";

   Class<?> type() default Node.class;

   String[] executeWith() default {};

   boolean implicit() default false;

   String implicitCreate() default "create()";

   boolean allowUncached() default false;

   String uncached() default "getUncached()";
}
