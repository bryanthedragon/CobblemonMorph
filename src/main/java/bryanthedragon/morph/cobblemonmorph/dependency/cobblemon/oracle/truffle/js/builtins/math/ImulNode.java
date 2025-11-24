
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.math.ImulNodeGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class ImulNode
extends JSBuiltinNode {
    ImulNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    public static ImulNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        return ImulNodeGen.create(context, builtin, ImulNode.createCast(arguments));
    }

    protected static JavaScriptNode[] createCast(JavaScriptNode[] argumentNodes) {
        for (int i = 0; i < argumentNodes.length; ++i) {
            argumentNodes[i] = JSToInt32Node.create(argumentNodes[i]);
        }
        return argumentNodes;
    }

    @Specialization
    protected static int imul(int a, int b) {
        return a * b;
    }
}

