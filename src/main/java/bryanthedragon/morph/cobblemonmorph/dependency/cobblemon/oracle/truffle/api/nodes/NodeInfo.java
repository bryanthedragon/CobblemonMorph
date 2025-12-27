package com.oracle.truffle.api.nodes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface NodeInfo {
   String shortName() default "";

   NodeCost cost() default NodeCost.MONOMORPHIC;

   String description() default "";

   String language() default "";
}
