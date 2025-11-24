
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeAccessor;
import com.oracle.truffle.api.nodes.RepeatingNode;

public abstract class LoopNode
extends Node {
    protected LoopNode() {
    }

    public Object execute(VirtualFrame frame) {
        throw new AbstractMethodError("This method must be overridden in concrete subclasses.");
    }

    public abstract RepeatingNode getRepeatingNode();

    public static void reportLoopCount(Node source, int iterations) {
        assert (iterations >= 0);
        if (CompilerDirectives.hasNextTier()) {
            if (CompilerDirectives.isPartialEvaluationConstant(source)) {
                NodeAccessor.RUNTIME.onLoopCount(source, iterations);
            } else {
                LoopNode.onLoopCountBoundary(source, iterations);
            }
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static void onLoopCountBoundary(Node source, int iterations) {
        NodeAccessor.RUNTIME.onLoopCount(source, iterations);
    }
}

