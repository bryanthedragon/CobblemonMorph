
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.nodes.Node;

public abstract class IndirectCallNode
extends Node {
    private static final IndirectCallNode UNCACHED = new IndirectCallNode(){

        @Override
        public boolean isAdoptable() {
            return false;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object call(CallTarget target, Object ... arguments) {
            return target.call(arguments);
        }
    };

    protected IndirectCallNode() {
    }

    public abstract Object call(CallTarget var1, Object ... var2);

    public static IndirectCallNode create() {
        return Truffle.getRuntime().createIndirectCallNode();
    }

    public static IndirectCallNode getUncached() {
        return UNCACHED;
    }
}

