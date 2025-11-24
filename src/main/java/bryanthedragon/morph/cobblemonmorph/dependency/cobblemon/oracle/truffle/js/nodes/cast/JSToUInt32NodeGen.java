
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToUInt32Node.class)
public final class JSToUInt32NodeGen
extends JSToUInt32Node
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSStringToNumberNode string_stringToNumberNode_;
    @Node.Child
    private JSOverloadedBinaryNode overloadedOperator_overloadedOperatorNode_;
    @Node.Child
    private JSToNumberNode jSObject_toNumberNode_;
    @Node.Child
    private JSToPrimitiveNode foreignObject_toPrimitiveNode_;
    @Node.Child
    private JSToUInt32Node foreignObject_toUInt32Node_;

    private JSToUInt32NodeGen(boolean unsignedRightShift, int shiftValue) {
        super(unsignedRightShift, shiftValue);
    }

    @Override
    public Object execute(Object arg0Value) {
        int arg0Value_;
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0 && arg0Value instanceof Integer) {
            arg0Value_ = (Integer)arg0Value;
            if ((state_0 & 1) != 0 && arg0Value_ >= 0) {
                return this.doInteger(arg0Value_);
            }
            if ((state_0 & 2) != 0 && arg0Value_ < 0) {
                return this.doIntegerNegative(arg0Value_);
            }
        }
        if ((state_0 & 4) != 0 && arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_2 = (SafeInteger)arg0Value;
            return this.doSafeInteger(arg0Value_2);
        }
        if ((state_0 & 8) != 0 && arg0Value instanceof Boolean) {
            arg0Value_ = ((Boolean)arg0Value).booleanValue() ? 1 : 0;
            return this.doBoolean(arg0Value_ != 0);
        }
        if ((state_0 & 0x10) != 0 && arg0Value instanceof Long) {
            long arg0Value_3 = (Long)arg0Value;
            return this.doLong(arg0Value_3);
        }
        if ((state_0 & 0xE0) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0000) >>> 16, arg0Value)) {
            double arg0Value_4 = JSTypesGen.asImplicitDouble((state_0 & 0xF0000) >>> 16, arg0Value);
            if ((state_0 & 0x20) != 0 && !JSGuards.isDoubleLargerThan2e32(arg0Value_4)) {
                return this.doDoubleFitsInt32Negative(arg0Value_4);
            }
            if ((state_0 & 0x40) != 0 && JSGuards.isDoubleLargerThan2e32(arg0Value_4) && JSGuards.isDoubleRepresentableAsLong(arg0Value_4)) {
                return this.doDoubleRepresentableAsLong(arg0Value_4);
            }
            if ((state_0 & 0x80) != 0 && JSGuards.isDoubleLargerThan2e32(arg0Value_4) && !JSGuards.isDoubleRepresentableAsLong(arg0Value_4)) {
                return this.doDouble(arg0Value_4);
            }
        }
        if ((state_0 & 0x300) != 0) {
            if ((state_0 & 0x100) != 0 && JSGuards.isJSNull(arg0Value)) {
                return this.doNull(arg0Value);
            }
            if ((state_0 & 0x200) != 0 && JSGuards.isUndefined(arg0Value)) {
                return this.doUndefined(arg0Value);
            }
        }
        if ((state_0 & 0x400) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_5 = (TruffleString)arg0Value;
            return this.doString(arg0Value_5, this.string_stringToNumberNode_);
        }
        if ((state_0 & 0x800) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_6 = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_6);
        }
        if ((state_0 & 0x1000) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_7 = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_7);
        }
        if ((state_0 & 0x2000) != 0 && arg0Value instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject arg0Value_8 = (JSOverloadedOperatorsObject)arg0Value;
            assert (this.isUnsignedRightShift());
            return this.doOverloadedOperator(arg0Value_8, this.overloadedOperator_overloadedOperatorNode_);
        }
        if ((state_0 & 0x4000) != 0 && arg0Value instanceof JSObject) {
            JSObject arg0Value_9 = (JSObject)arg0Value;
            if (!this.isUnsignedRightShift() || !this.hasOverloadedOperators(arg0Value_9)) {
                return this.doJSObject(arg0Value_9, this.jSObject_toNumberNode_);
            }
        }
        if ((state_0 & 0x8000) != 0 && JSGuards.isForeignObject(arg0Value)) {
            return JSToUInt32Node.doForeignObject(arg0Value, this.foreignObject_toPrimitiveNode_, this.foreignObject_toUInt32Node_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private Object executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int arg0Value_;
            int state_0 = this.state_0_;
            if (arg0Value instanceof Integer) {
                arg0Value_ = (Integer)arg0Value;
                if (arg0Value_ >= 0) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doInteger(arg0Value_);
                    return n;
                }
                if (arg0Value_ < 0) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    SafeInteger safeInteger = this.doIntegerNegative(arg0Value_);
                    return safeInteger;
                }
            }
            if (arg0Value instanceof SafeInteger) {
                SafeInteger arg0Value_2 = (SafeInteger)arg0Value;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.doSafeInteger(arg0Value_2);
                return object;
            }
            if (arg0Value instanceof Boolean) {
                arg0Value_ = ((Boolean)arg0Value).booleanValue() ? 1 : 0;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                Integer n = this.doBoolean(arg0Value_ != 0);
                return n;
            }
            if (arg0Value instanceof Long) {
                long arg0Value_3 = (Long)arg0Value;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Double d = this.doLong(arg0Value_3);
                return d;
            }
            int doubleCast02 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast02 != 0) {
                double arg0Value_4 = JSTypesGen.asImplicitDouble(doubleCast02, arg0Value);
                if (!JSGuards.isDoubleLargerThan2e32(arg0Value_4)) {
                    state_0 |= doubleCast02 << 16;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.doDoubleFitsInt32Negative(arg0Value_4);
                    return d;
                }
                if (JSGuards.isDoubleLargerThan2e32(arg0Value_4) && JSGuards.isDoubleRepresentableAsLong(arg0Value_4)) {
                    state_0 |= doubleCast02 << 16;
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.doDoubleRepresentableAsLong(arg0Value_4);
                    return d;
                }
                if (JSGuards.isDoubleLargerThan2e32(arg0Value_4) && !JSGuards.isDoubleRepresentableAsLong(arg0Value_4)) {
                    state_0 |= doubleCast02 << 16;
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.doDouble(arg0Value_4);
                    return d;
                }
            }
            if (JSGuards.isJSNull(arg0Value)) {
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                Integer doubleCast02 = this.doNull(arg0Value);
                return doubleCast02;
            }
            if (JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                Integer doubleCast02 = this.doUndefined(arg0Value);
                return doubleCast02;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_5 = (TruffleString)arg0Value;
                this.string_stringToNumberNode_ = super.insert(JSStringToNumberNode.create());
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                Double d = this.doString(arg0Value_5, this.string_stringToNumberNode_);
                return d;
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_6 = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x800;
                lock.unlock();
                hasLock = false;
                Number number = this.doSymbol(arg0Value_6);
                return number;
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_7 = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x1000;
                lock.unlock();
                hasLock = false;
                Integer n = this.doBigInt(arg0Value_7);
                return n;
            }
            if (arg0Value instanceof JSOverloadedOperatorsObject) {
                JSOverloadedOperatorsObject arg0Value_8 = (JSOverloadedOperatorsObject)arg0Value;
                if (this.isUnsignedRightShift()) {
                    this.overloadedOperator_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
                    this.state_0_ = state_0 |= 0x2000;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doOverloadedOperator(arg0Value_8, this.overloadedOperator_overloadedOperatorNode_);
                    return object;
                }
            }
            if (arg0Value instanceof JSObject) {
                JSObject arg0Value_9 = (JSObject)arg0Value;
                if (!this.isUnsignedRightShift() || !this.hasOverloadedOperators(arg0Value_9)) {
                    this.jSObject_toNumberNode_ = super.insert(JSToNumberNode.create());
                    this.state_0_ = state_0 |= 0x4000;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.doJSObject(arg0Value_9, this.jSObject_toNumberNode_);
                    return d;
                }
            }
            if (JSGuards.isForeignObject(arg0Value)) {
                this.foreignObject_toPrimitiveNode_ = super.insert(JSToPrimitiveNode.createHintNumber());
                this.foreignObject_toUInt32Node_ = super.insert(JSToUInt32Node.create());
                this.state_0_ = state_0 |= 0x8000;
                lock.unlock();
                hasLock = false;
                Double d = JSToUInt32Node.doForeignObject(arg0Value, this.foreignObject_toPrimitiveNode_, this.foreignObject_toUInt32Node_);
                return d;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if ((state_0 & 0xFFFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0xFFFF & (state_0 & 0xFFFF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<JavaScriptBaseNode>> cached;
        Object[] data = new Object[17];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doIntegerNegative";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doLong";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doDoubleFitsInt32Negative";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doDoubleRepresentableAsLong";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doNull";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        s = new Object[3];
        s[0] = "doString";
        if ((state_0 & 0x400) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<JavaScriptBaseNode>>();
            cached.add(Arrays.asList(this.string_stringToNumberNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[11] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x800) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[12] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x1000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[13] = s;
        s = new Object[3];
        s[0] = "doOverloadedOperator";
        if ((state_0 & 0x2000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.overloadedOperator_overloadedOperatorNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[14] = s;
        s = new Object[3];
        s[0] = "doJSObject";
        if ((state_0 & 0x4000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.jSObject_toNumberNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[15] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x8000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.foreignObject_toPrimitiveNode_, this.foreignObject_toUInt32Node_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[16] = s;
        return Introspection.Provider.create(data);
    }

    public static JSToUInt32Node create(boolean unsignedRightShift, int shiftValue) {
        return new JSToUInt32NodeGen(unsignedRightShift, shiftValue);
    }

    @GeneratedBy(value=JSToUInt32Node.JSToUInt32WrapperNode.class)
    public static final class JSToUInt32WrapperNodeGen
    extends JSToUInt32Node.JSToUInt32WrapperNode
    implements Introspection.Provider {
        private JSToUInt32WrapperNodeGen(JavaScriptNode operand, boolean unsignedRightShift, int shiftValue) {
            super(operand, unsignedRightShift, shiftValue);
        }

        @Override
        public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
            return this.doDefault(operandNodeValue);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object operandNodeValue_ = this.operandNode.execute(frameValue);
            return this.doDefault(operandNodeValue_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doDefault";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static JSToUInt32Node.JSToUInt32WrapperNode create(JavaScriptNode operand, boolean unsignedRightShift, int shiftValue) {
            return new JSToUInt32WrapperNodeGen(operand, unsignedRightShift, shiftValue);
        }
    }
}

