
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.cast.JSNumericToNumberNode;
import com.oracle.truffle.js.runtime.BigInt;

@GeneratedBy(value=JSNumericToNumberNode.class)
public final class JSNumericToNumberNodeGen
extends JSNumericToNumberNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private JSNumericToNumberNodeGen() {
    }

    @Override
    public Number executeNumeric(Object arg0Value) {
        Number arg0Value_;
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_2 = (BigInt)arg0Value;
            return JSNumericToNumberNode.doBigInt(arg0Value_2);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof Number && !JSGuards.isBigInt(arg0Value_ = (Number)arg0Value)) {
            return JSNumericToNumberNode.doOther(arg0Value_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private Number executeAndSpecialize(Object arg0Value) {
        Number arg0Value_;
        int state_0 = this.state_0_;
        if (arg0Value instanceof BigInt) {
            BigInt arg0Value_2 = (BigInt)arg0Value;
            this.state_0_ = state_0 |= 1;
            return JSNumericToNumberNode.doBigInt(arg0Value_2);
        }
        if (arg0Value instanceof Number && !JSGuards.isBigInt(arg0Value_ = (Number)arg0Value)) {
            this.state_0_ = state_0 |= 2;
            return JSNumericToNumberNode.doOther(arg0Value_);
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
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doOther";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static JSNumericToNumberNode create() {
        return new JSNumericToNumberNodeGen();
    }
}

