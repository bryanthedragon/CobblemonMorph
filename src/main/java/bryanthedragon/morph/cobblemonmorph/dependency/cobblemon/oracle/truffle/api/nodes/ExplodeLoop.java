
package com.oracle.truffle.api.nodes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface ExplodeLoop {
    public LoopExplosionKind kind() default LoopExplosionKind.FULL_UNROLL_UNTIL_RETURN;

    public static enum LoopExplosionKind {
        FULL_UNROLL,
        FULL_UNROLL_UNTIL_RETURN,
        FULL_EXPLODE,
        FULL_EXPLODE_UNTIL_RETURN,
        MERGE_EXPLODE;

    }
}

