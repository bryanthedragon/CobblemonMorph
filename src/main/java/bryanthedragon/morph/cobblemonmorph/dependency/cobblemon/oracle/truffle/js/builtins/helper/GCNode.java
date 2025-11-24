
package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class GCNode
extends JSBuiltinNode {
    public GCNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    protected Object gc() {
        System.gc();
        return Undefined.instance;
    }
}

