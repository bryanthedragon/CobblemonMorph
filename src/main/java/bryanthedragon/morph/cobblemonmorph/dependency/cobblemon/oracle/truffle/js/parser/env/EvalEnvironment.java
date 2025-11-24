
package com.oracle.truffle.js.parser.env;

import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.parser.env.Environment;
import com.oracle.truffle.js.runtime.JSContext;

public class EvalEnvironment
extends Environment {
    private final boolean isDirectEval;

    public EvalEnvironment(Environment parent, NodeFactory factory, JSContext context, boolean isDirectEval) {
        super(parent, factory, context);
        assert (parent == null || parent.function() == null || parent.function().isDeepFrozen());
        this.isDirectEval = isDirectEval;
    }

    public boolean isDirectEval() {
        return this.isDirectEval;
    }

    @Override
    public JSFrameSlot findBlockFrameSlot(Object name) {
        throw new UnsupportedOperationException();
    }
}

