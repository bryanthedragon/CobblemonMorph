
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.cast.JSToDoubleNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class MathOperation
extends JSBuiltinNode {
    @Node.Child
    private JSToDoubleNode toDoubleNode;

    public MathOperation(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    protected final double toDouble(Object target) {
        if (this.toDoubleNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toDoubleNode = this.insert(JSToDoubleNode.create());
        }
        return this.toDoubleNode.executeDouble(target);
    }
}

