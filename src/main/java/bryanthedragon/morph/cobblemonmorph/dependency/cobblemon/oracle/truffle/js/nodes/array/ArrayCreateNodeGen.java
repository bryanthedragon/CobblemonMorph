
package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.array.ArrayCreateNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;

@GeneratedBy(value=ArrayCreateNode.class)
public final class ArrayCreateNodeGen
extends ArrayCreateNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private ArrayCreateNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public JSArrayObject execute(long arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && JSRuntime.isValidArrayLength(arg0Value) && arg0Value <= Integer.MAX_VALUE) {
                return this.doDefault(arg0Value);
            }
            if ((state_0 & 2) != 0 && JSRuntime.isValidArrayLength(arg0Value) && arg0Value > Integer.MAX_VALUE) {
                return this.doLargeLength(arg0Value);
            }
            if ((state_0 & 4) != 0 && !JSRuntime.isValidArrayLength(arg0Value)) {
                return this.doInvalidLength(arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private JSArrayObject executeAndSpecialize(long arg0Value) {
        int state_0 = this.state_0_;
        if (JSRuntime.isValidArrayLength(arg0Value) && arg0Value <= Integer.MAX_VALUE) {
            this.state_0_ = state_0 |= 1;
            return this.doDefault(arg0Value);
        }
        if (JSRuntime.isValidArrayLength(arg0Value) && arg0Value > Integer.MAX_VALUE) {
            this.state_0_ = state_0 |= 2;
            return this.doLargeLength(arg0Value);
        }
        if (!JSRuntime.isValidArrayLength(arg0Value)) {
            this.state_0_ = state_0 |= 4;
            return this.doInvalidLength(arg0Value);
        }
        throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
        s[0] = "doDefault";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doLargeLength";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doInvalidLength";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static ArrayCreateNode create(JSContext context) {
        return new ArrayCreateNodeGen(context);
    }
}

