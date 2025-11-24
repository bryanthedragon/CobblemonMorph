
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.cast.ToArrayLengthNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;

@GeneratedBy(value=ToArrayLengthNode.class)
public final class ToArrayLengthNodeGen
extends ToArrayLengthNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private ToArrayLengthNodeGen() {
    }

    @Override
    public long executeLong(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return ToArrayLengthNode.doInt(arg0Value_);
        }
        if ((state_0 & 6) != 0 && arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            if ((state_0 & 2) != 0 && JSRuntime.isValidArrayLength(arg0Value_.longValue())) {
                return ToArrayLengthNode.doSafeInteger(arg0Value_);
            }
            if ((state_0 & 4) != 0 && !JSRuntime.isValidArrayLength(arg0Value_.longValue())) {
                return ToArrayLengthNode.rangeError(arg0Value_);
            }
        }
        if ((state_0 & 0x18) != 0 && arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            if ((state_0 & 8) != 0 && JSRuntime.isValidArrayLength(arg0Value_)) {
                return ToArrayLengthNode.doLong(arg0Value_);
            }
            if ((state_0 & 0x10) != 0 && !JSRuntime.isValidArrayLength(arg0Value_)) {
                return ToArrayLengthNode.rangeError(arg0Value_);
            }
        }
        if ((state_0 & 0x60) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, arg0Value);
            if ((state_0 & 0x20) != 0 && JSRuntime.isValidArrayLength(arg0Value_)) {
                return ToArrayLengthNode.doDouble(arg0Value_);
            }
            if ((state_0 & 0x40) != 0 && !JSRuntime.isValidArrayLength(arg0Value_)) {
                return ToArrayLengthNode.rangeError(arg0Value_);
            }
        }
        if ((state_0 & 0x80) != 0 && !JSRuntime.isNumber(arg0Value)) {
            return ToArrayLengthNode.typeNotNumber(arg0Value);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private long executeAndSpecialize(Object arg0Value) {
        int doubleCast0;
        int state_0 = this.state_0_;
        if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            this.state_0_ = state_0 |= 1;
            return ToArrayLengthNode.doInt(arg0Value_);
        }
        if (arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            if (JSRuntime.isValidArrayLength(arg0Value_.longValue())) {
                this.state_0_ = state_0 |= 2;
                return ToArrayLengthNode.doSafeInteger(arg0Value_);
            }
            if (!JSRuntime.isValidArrayLength(arg0Value_.longValue())) {
                this.state_0_ = state_0 |= 4;
                return ToArrayLengthNode.rangeError(arg0Value_);
            }
        }
        if (arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            if (JSRuntime.isValidArrayLength(arg0Value_)) {
                this.state_0_ = state_0 |= 8;
                return ToArrayLengthNode.doLong(arg0Value_);
            }
            if (!JSRuntime.isValidArrayLength(arg0Value_)) {
                this.state_0_ = state_0 |= 0x10;
                return ToArrayLengthNode.rangeError(arg0Value_);
            }
        }
        if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
            double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
            if (JSRuntime.isValidArrayLength(arg0Value_)) {
                state_0 |= doubleCast0 << 8;
                this.state_0_ = state_0 |= 0x20;
                return ToArrayLengthNode.doDouble(arg0Value_);
            }
            if (!JSRuntime.isValidArrayLength(arg0Value_)) {
                state_0 |= doubleCast0 << 8;
                this.state_0_ = state_0 |= 0x40;
                return ToArrayLengthNode.rangeError(arg0Value_);
            }
        }
        if (!JSRuntime.isNumber(arg0Value)) {
            this.state_0_ = state_0 |= 0x80;
            return ToArrayLengthNode.typeNotNumber(arg0Value);
        }
        throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0xFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0xFF & (state_0 & 0xFF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[9];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "rangeError";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doLong";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "rangeError";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "rangeError";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "typeNotNumber";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        return Introspection.Provider.create(data);
    }

    public static ToArrayLengthNode create() {
        return new ToArrayLengthNodeGen();
    }
}

