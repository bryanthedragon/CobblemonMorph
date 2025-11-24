
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class RandomNode
extends JSBuiltinNode {
    public RandomNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected double random() {
        return this.getRealm().getRandom().nextDouble();
    }
}

