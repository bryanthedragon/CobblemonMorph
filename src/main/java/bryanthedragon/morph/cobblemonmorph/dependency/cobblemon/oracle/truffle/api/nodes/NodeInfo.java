
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.nodes.NodeCost;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Inherited
public @interface NodeInfo {
    public String shortName() default "";

    public NodeCost cost() default NodeCost.MONOMORPHIC;

    public String description() default "";

    public String language() default "";
}

