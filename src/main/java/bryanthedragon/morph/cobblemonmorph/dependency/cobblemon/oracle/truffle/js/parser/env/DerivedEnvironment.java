
package com.oracle.truffle.js.parser.env;

import com.oracle.truffle.js.nodes.JSFrameDescriptor;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.parser.env.BlockEnvironment;
import com.oracle.truffle.js.parser.env.Environment;
import com.oracle.truffle.js.parser.env.FunctionEnvironment;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class DerivedEnvironment
extends Environment {
    private final Environment blockEnvironment;

    protected DerivedEnvironment(Environment parent, NodeFactory factory, JSContext context) {
        super(parent, factory, context);
        if (parent instanceof FunctionEnvironment || parent instanceof BlockEnvironment) {
            this.blockEnvironment = parent;
        } else if (parent instanceof DerivedEnvironment) {
            this.blockEnvironment = ((DerivedEnvironment)parent).blockEnvironment;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public final JSFrameDescriptor getBlockFrameDescriptor() {
        return this.block().getBlockFrameDescriptor();
    }

    @Override
    public final int getScopeLevel() {
        return this.block().getScopeLevel();
    }

    @Override
    public final JSFrameSlot getCurrentBlockScopeSlot() {
        return this.block().getCurrentBlockScopeSlot();
    }

    @Override
    public final JSFrameSlot declareInternalSlot(Object name) {
        return this.block().declareInternalSlot(name);
    }

    private Environment block() {
        return this.blockEnvironment;
    }
}

