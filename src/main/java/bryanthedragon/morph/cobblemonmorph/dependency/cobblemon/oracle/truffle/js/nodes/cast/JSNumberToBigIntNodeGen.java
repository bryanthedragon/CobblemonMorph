
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.cast.JSNumberToBigIntNode;
import com.oracle.truffle.js.runtime.BigInt;

@GeneratedBy(value=JSNumberToBigIntNode.class)
public final class JSNumberToBigIntNodeGen
extends JSNumberToBigIntNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private JSNumberToBigIntNodeGen() {
    }

    @Override
    public Object execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return this.doInteger(arg0Value_);
        }
        if ((state_0 & 6) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0) >>> 4, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0xF0) >>> 4, arg0Value);
            if ((state_0 & 2) != 0 && this.doubleRepresentsSameValueAsLong(arg0Value_)) {
                return this.doDoubleAsLong(arg0Value_);
            }
            if ((state_0 & 4) != 0 && !this.doubleRepresentsSameValueAsLong(arg0Value_)) {
                return this.doDoubleOther(arg0Value_);
            }
        }
        if ((state_0 & 8) != 0 && JSGuards.isJSNull(arg0Value)) {
            return JSNumberToBigIntNode.doNull(arg0Value);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private BigInt executeAndSpecialize(Object arg0Value) {
        int state_0 = this.state_0_;
        if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            this.state_0_ = state_0 |= 1;
            return this.doInteger(arg0Value_);
        }
        int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
        if (doubleCast0 != 0) {
            double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
            if (this.doubleRepresentsSameValueAsLong(arg0Value_)) {
                state_0 |= doubleCast0 << 4;
                this.state_0_ = state_0 |= 2;
                return this.doDoubleAsLong(arg0Value_);
            }
            if (!this.doubleRepresentsSameValueAsLong(arg0Value_)) {
                state_0 |= doubleCast0 << 4;
                this.state_0_ = state_0 |= 4;
                return this.doDoubleOther(arg0Value_);
            }
        }
        if (JSGuards.isJSNull(arg0Value)) {
            this.state_0_ = state_0 |= 8;
            return JSNumberToBigIntNode.doNull(arg0Value);
        }
        throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0xF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0xF & (state_0 & 0xF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[5];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doDoubleAsLong";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doDoubleOther";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doNull";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        return Introspection.Provider.create(data);
    }

    public static JSNumberToBigIntNode create() {
        return new JSNumberToBigIntNodeGen();
    }
}

