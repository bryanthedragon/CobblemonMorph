
package com.oracle.truffle.js.parser.env;

import com.oracle.js.parser.ir.Scope;
import com.oracle.js.parser.ir.Symbol;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.js.nodes.JSFrameDescriptor;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.parser.env.DerivedEnvironment;
import com.oracle.truffle.js.parser.env.Environment;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;

public final class PrivateEnvironment
extends DerivedEnvironment {
    private final Scope scope;

    public PrivateEnvironment(Environment parent, NodeFactory factory, JSContext context, Scope scope) {
        super(parent, factory, context);
        this.scope = scope;
    }

    @Override
    public JSFrameSlot findBlockFrameSlot(Object name) {
        JSFrameSlot slot = this.getBlockFrameDescriptor().findFrameSlot(JSFrameDescriptor.scopedIdentifier(name, this.scope));
        if (slot != null && JSFrameUtil.isPrivateName(slot)) {
            return slot;
        }
        return null;
    }

    @Override
    public void addFrameSlotFromSymbol(Symbol symbol) {
        JSFrameDescriptor.ScopedIdentifier id = JSFrameDescriptor.scopedIdentifier(symbol.getNameTS(), this.scope);
        assert (!this.getBlockFrameDescriptor().contains(id)) : symbol;
        this.getBlockFrameDescriptor().findOrAddFrameSlot(id, symbol.getFlags(), FrameSlotKind.Illegal);
    }

    @Override
    public Scope getScope() {
        return this.scope;
    }
}

