
package com.oracle.truffle.js.parser.env;

import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.parser.env.DerivedEnvironment;
import com.oracle.truffle.js.parser.env.Environment;
import com.oracle.truffle.js.runtime.JSContext;

public final class WithEnvironment
extends DerivedEnvironment {
    private final Object withVarIdentifier;

    public WithEnvironment(Environment parent, NodeFactory factory, JSContext context, Object withVarIdentifier) {
        super(parent, factory, context);
        this.withVarIdentifier = withVarIdentifier;
        assert (parent.findInternalSlot(withVarIdentifier) != null);
    }

    public Object getWithVarIdentifier() {
        return this.withVarIdentifier;
    }

    @Override
    public boolean isDynamicScopeContext() {
        return true;
    }
}

