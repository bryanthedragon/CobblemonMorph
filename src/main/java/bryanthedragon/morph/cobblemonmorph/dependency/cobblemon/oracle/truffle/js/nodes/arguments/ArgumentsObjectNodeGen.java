
package com.oracle.truffle.js.nodes.arguments;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.arguments.ArgumentsObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsObject;

@GeneratedBy(value=ArgumentsObjectNode.class)
public final class ArgumentsObjectNodeGen
extends ArgumentsObjectNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private ArgumentsObjectNodeGen(JSContext context, boolean strict, int leadingArgCount) {
        super(context, strict, leadingArgCount);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0) {
            assert (this.isStrict());
            return this.doUnmapped(frameValue);
        }
        if ((state_0 & 2) != 0) {
            assert (!this.isStrict());
            return this.doMapped(frameValue);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(frameValue);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    private JSArgumentsObject executeAndSpecialize(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if (this.isStrict()) {
            this.state_0_ = state_0 |= 1;
            return this.doUnmapped(frameValue);
        }
        if (!this.isStrict()) {
            this.state_0_ = state_0 |= 2;
            return this.doMapped(frameValue);
        }
        throw new UnsupportedSpecializationException(this, new Node[0], new Object[0]);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doUnmapped";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doMapped";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static ArgumentsObjectNode create(JSContext context, boolean strict, int leadingArgCount) {
        return new ArgumentsObjectNodeGen(context, strict, leadingArgCount);
    }
}

