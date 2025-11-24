
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.promise.UnwrapPromiseNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(value=UnwrapPromiseNode.class)
public final class UnwrapPromiseNodeGen
extends UnwrapPromiseNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private UnwrapPromiseNodeGen(JSContext context) {
        super(context);
    }

    @Override
    protected Object execute(JSDynamicObject arg0Value, int arg1Value, Object arg2Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg1Value == 1) {
                return UnwrapPromiseNode.fulfilled(arg0Value, arg1Value, arg2Value);
            }
            if ((state_0 & 2) != 0 && arg1Value == 2) {
                return UnwrapPromiseNode.rejected(arg0Value, arg1Value, arg2Value);
            }
            if ((state_0 & 4) != 0 && arg1Value == 0) {
                return UnwrapPromiseNode.pending(arg0Value, arg1Value, arg2Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
    }

    private Object executeAndSpecialize(JSDynamicObject arg0Value, int arg1Value, Object arg2Value) {
        int state_0 = this.state_0_;
        if (arg1Value == 1) {
            this.state_0_ = state_0 |= 1;
            return UnwrapPromiseNode.fulfilled(arg0Value, arg1Value, arg2Value);
        }
        if (arg1Value == 2) {
            this.state_0_ = state_0 |= 2;
            return UnwrapPromiseNode.rejected(arg0Value, arg1Value, arg2Value);
        }
        if (arg1Value == 0) {
            this.state_0_ = state_0 |= 4;
            return UnwrapPromiseNode.pending(arg0Value, arg1Value, arg2Value);
        }
        throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "fulfilled";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "rejected";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "pending";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static UnwrapPromiseNode create(JSContext context) {
        return new UnwrapPromiseNodeGen(context);
    }
}

