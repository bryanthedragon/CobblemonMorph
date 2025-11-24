
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.function.InternalCallNodeGen;

public abstract class InternalCallNode
extends JavaScriptBaseNode {
    static final int LIMIT = 3;

    protected InternalCallNode() {
    }

    public static InternalCallNode create() {
        return InternalCallNodeGen.create();
    }

    public abstract Object execute(CallTarget var1, Object[] var2);

    @Specialization(guards={"callTarget == cachedCallTarget"}, limit="LIMIT")
    protected static Object directCall(CallTarget callTarget, Object[] arguments, @Cached(value="callTarget") CallTarget cachedCallTarget, @Cached(value="create(cachedCallTarget)") DirectCallNode directCallNode) {
        return directCallNode.call(arguments);
    }

    @Specialization
    protected static Object indirectCall(CallTarget callTarget, Object[] arguments, @Cached(value="create()") IndirectCallNode indirectCallNode) {
        return indirectCallNode.call(callTarget, arguments);
    }
}

