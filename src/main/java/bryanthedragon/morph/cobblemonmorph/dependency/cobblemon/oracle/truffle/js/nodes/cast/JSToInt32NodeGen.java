
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToDoubleNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToInt32Node.class)
public final class JSToInt32NodeGen
extends JSToInt32Node
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSStringToNumberNode string_stringToNumberNode_;
    @Node.Child
    private JSOverloadedBinaryNode overloadedOperator_overloadedOperatorNode_;
    @Node.Child
    private JSToDoubleNode jSObject_toDoubleNode_;
    @Node.Child
    private JSToPrimitiveNode foreignObject_toPrimitiveNode_;
    @Node.Child
    private JSToInt32Node foreignObject_toInt32Node_;

    private JSToInt32NodeGen(JavaScriptNode operand, boolean bitwiseOr) {
        super(operand, bitwiseOr);
    }

    @Override
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        long operandNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_2 = (Integer)operandNodeValue;
            return this.doInteger(operandNodeValue_2);
        }
        if ((state_0 & 2) != 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_3 = (SafeInteger)operandNodeValue;
            return this.doSafeInteger(operandNodeValue_3);
        }
        if ((state_0 & 4) != 0 && operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_4 = (Boolean)operandNodeValue;
            return this.doBoolean(operandNodeValue_4);
        }
        if ((state_0 & 8) != 0 && operandNodeValue instanceof Long && JSGuards.isLongRepresentableAsInt32(operandNodeValue_ = ((Long)operandNodeValue).longValue())) {
            return this.doLong(operandNodeValue_);
        }
        if ((state_0 & 0xF0) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0000) >>> 16, operandNodeValue)) {
            double operandNodeValue_5 = JSTypesGen.asImplicitDouble((state_0 & 0xF0000) >>> 16, operandNodeValue);
            if ((state_0 & 0x10) != 0 && !JSGuards.isDoubleLargerThan2e32(operandNodeValue_5)) {
                return this.doDoubleFitsInt(operandNodeValue_5);
            }
            if ((state_0 & 0x20) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_5) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_5) && JSGuards.isDoubleSafeInteger(operandNodeValue_5)) {
                return this.doDoubleRepresentableAsSafeInteger(operandNodeValue_5);
            }
            if ((state_0 & 0x40) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_5) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_5)) {
                return this.doDoubleRepresentableAsLong(operandNodeValue_5);
            }
            if ((state_0 & 0x80) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_5) && !JSGuards.isDoubleRepresentableAsLong(operandNodeValue_5)) {
                return this.doDouble(operandNodeValue_5);
            }
        }
        if ((state_0 & 0x300) != 0) {
            if ((state_0 & 0x100) != 0 && JSGuards.isUndefined(operandNodeValue)) {
                return this.doUndefined(operandNodeValue);
            }
            if ((state_0 & 0x200) != 0 && JSGuards.isJSNull(operandNodeValue)) {
                return this.doNull(operandNodeValue);
            }
        }
        if ((state_0 & 0x400) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_6 = (TruffleString)operandNodeValue;
            return this.doString(operandNodeValue_6, this.string_stringToNumberNode_);
        }
        if ((state_0 & 0x800) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_7 = (Symbol)operandNodeValue;
            return this.doSymbol(operandNodeValue_7);
        }
        if ((state_0 & 0x1000) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_8 = (BigInt)operandNodeValue;
            return this.doBigInt(operandNodeValue_8);
        }
        if ((state_0 & 0x2000) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue_9 = (JSOverloadedOperatorsObject)operandNodeValue;
            assert (this.isBitwiseOr());
            return this.doOverloadedOperator(operandNodeValue_9, this.overloadedOperator_overloadedOperatorNode_);
        }
        if ((state_0 & 0x4000) != 0 && operandNodeValue instanceof JSObject) {
            JSObject operandNodeValue_10 = (JSObject)operandNodeValue;
            if (!this.isBitwiseOr() || !this.hasOverloadedOperators(operandNodeValue_10)) {
                return this.doJSObject(operandNodeValue_10, this.jSObject_toDoubleNode_);
            }
        }
        if ((state_0 & 0x8000) != 0 && JSGuards.isForeignObject(operandNodeValue)) {
            return JSToInt32Node.doForeignObject(operandNodeValue, this.foreignObject_toPrimitiveNode_, this.foreignObject_toInt32Node_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    public int executeInt(Object operandNodeValue) {
        long operandNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x2000) != 0) {
            return (Integer)this.execute(null, operandNodeValue);
        }
        if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_2 = (Integer)operandNodeValue;
            return this.doInteger(operandNodeValue_2);
        }
        if ((state_0 & 2) != 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_3 = (SafeInteger)operandNodeValue;
            return this.doSafeInteger(operandNodeValue_3);
        }
        if ((state_0 & 4) != 0 && operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_4 = (Boolean)operandNodeValue;
            return this.doBoolean(operandNodeValue_4);
        }
        if ((state_0 & 8) != 0 && operandNodeValue instanceof Long && JSGuards.isLongRepresentableAsInt32(operandNodeValue_ = ((Long)operandNodeValue).longValue())) {
            return this.doLong(operandNodeValue_);
        }
        if ((state_0 & 0xF0) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0000) >>> 16, operandNodeValue)) {
            double operandNodeValue_5 = JSTypesGen.asImplicitDouble((state_0 & 0xF0000) >>> 16, operandNodeValue);
            if ((state_0 & 0x10) != 0 && !JSGuards.isDoubleLargerThan2e32(operandNodeValue_5)) {
                return this.doDoubleFitsInt(operandNodeValue_5);
            }
            if ((state_0 & 0x20) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_5) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_5) && JSGuards.isDoubleSafeInteger(operandNodeValue_5)) {
                return this.doDoubleRepresentableAsSafeInteger(operandNodeValue_5);
            }
            if ((state_0 & 0x40) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_5) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_5)) {
                return this.doDoubleRepresentableAsLong(operandNodeValue_5);
            }
            if ((state_0 & 0x80) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_5) && !JSGuards.isDoubleRepresentableAsLong(operandNodeValue_5)) {
                return this.doDouble(operandNodeValue_5);
            }
        }
        if ((state_0 & 0x300) != 0) {
            if ((state_0 & 0x100) != 0 && JSGuards.isUndefined(operandNodeValue)) {
                return this.doUndefined(operandNodeValue);
            }
            if ((state_0 & 0x200) != 0 && JSGuards.isJSNull(operandNodeValue)) {
                return this.doNull(operandNodeValue);
            }
        }
        if ((state_0 & 0x400) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_6 = (TruffleString)operandNodeValue;
            return this.doString(operandNodeValue_6, this.string_stringToNumberNode_);
        }
        if ((state_0 & 0x800) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_7 = (Symbol)operandNodeValue;
            return this.doSymbol(operandNodeValue_7);
        }
        if ((state_0 & 0x1000) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_8 = (BigInt)operandNodeValue;
            return this.doBigInt(operandNodeValue_8);
        }
        if ((state_0 & 0x4000) != 0 && operandNodeValue instanceof JSObject) {
            JSObject operandNodeValue_9 = (JSObject)operandNodeValue;
            if (!this.isBitwiseOr() || !this.hasOverloadedOperators(operandNodeValue_9)) {
                return this.doJSObject(operandNodeValue_9, this.jSObject_toDoubleNode_);
            }
        }
        if ((state_0 & 0x8000) != 0 && JSGuards.isForeignObject(operandNodeValue)) {
            return JSToInt32Node.doForeignObject(operandNodeValue, this.foreignObject_toPrimitiveNode_, this.foreignObject_toInt32Node_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return (Integer)this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    public int executeInt(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0xDFFE) == 0 && (state_0 & 0xDFFF) != 0) {
            return this.executeInt_int0(state_0, frameValue);
        }
        if ((state_0 & 0xDFFB) == 0 && (state_0 & 0xDFFF) != 0) {
            return this.executeInt_boolean1(state_0, frameValue);
        }
        if ((state_0 & 0xDFF7) == 0 && (state_0 & 0xDFFF) != 0) {
            return this.executeInt_long2(state_0, frameValue);
        }
        if ((state_0 & 0xDF0F) == 0 && (state_0 & 0xDFFF) != 0) {
            return this.executeInt_double3(state_0, frameValue);
        }
        return this.executeInt_generic4(state_0, frameValue);
    }

    private int executeInt_int0(int state_0, VirtualFrame frameValue) {
        int operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return (Integer)this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return this.doInteger(operandNodeValue_);
    }

    private int executeInt_boolean1(int state_0, VirtualFrame frameValue) {
        boolean operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return (Integer)this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 4) != 0);
        return this.doBoolean(operandNodeValue_);
    }

    private int executeInt_long2(int state_0, VirtualFrame frameValue) {
        long operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeLong(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return (Integer)this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 8) != 0);
        if (JSGuards.isLongRepresentableAsInt32(operandNodeValue_)) {
            return this.doLong(operandNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return (Integer)this.executeAndSpecialize(operandNodeValue_);
    }

    private int executeInt_double3(int state_0, VirtualFrame frameValue) {
        double operandNodeValue_;
        long operandNodeValue_long = 0L;
        int operandNodeValue_int = 0;
        try {
            if ((state_0 & 0xE0000) == 0 && (state_0 & 0xFFFF) != 0) {
                operandNodeValue_ = this.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 0xD0000) == 0 && (state_0 & 0xFFFF) != 0) {
                operandNodeValue_int = this.operandNode.executeInt(frameValue);
                operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 0x70000) == 0 && (state_0 & 0xFFFF) != 0) {
                operandNodeValue_long = this.operandNode.executeLong(frameValue);
                operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
                Object operandNodeValue__ = this.operandNode.execute(frameValue);
                operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF0000) >>> 16, operandNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return (Integer)this.executeAndSpecialize(ex.getResult());
        }
        if ((state_0 & 0x10) != 0 && !JSGuards.isDoubleLargerThan2e32(operandNodeValue_)) {
            return this.doDoubleFitsInt(operandNodeValue_);
        }
        if ((state_0 & 0x20) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_) && JSGuards.isDoubleSafeInteger(operandNodeValue_)) {
            return this.doDoubleRepresentableAsSafeInteger(operandNodeValue_);
        }
        if ((state_0 & 0x40) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)) {
            return this.doDoubleRepresentableAsLong(operandNodeValue_);
        }
        if ((state_0 & 0x80) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_) && !JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)) {
            return this.doDouble(operandNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return (Integer)this.executeAndSpecialize((state_0 & 0xD0000) == 0 && (state_0 & 0xFFFF) != 0 ? (Number)operandNodeValue_int : (Number)((state_0 & 0x70000) == 0 && (state_0 & 0xFFFF) != 0 ? (Number)operandNodeValue_long : (Number)operandNodeValue_));
    }

    private int executeInt_generic4(int state_0, VirtualFrame frameValue) {
        long operandNodeValue__;
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__2 = (Integer)operandNodeValue_;
            return this.doInteger(operandNodeValue__2);
        }
        if ((state_0 & 2) != 0 && operandNodeValue_ instanceof SafeInteger) {
            SafeInteger operandNodeValue__3 = (SafeInteger)operandNodeValue_;
            return this.doSafeInteger(operandNodeValue__3);
        }
        if ((state_0 & 4) != 0 && operandNodeValue_ instanceof Boolean) {
            boolean operandNodeValue__4 = (Boolean)operandNodeValue_;
            return this.doBoolean(operandNodeValue__4);
        }
        if ((state_0 & 8) != 0 && operandNodeValue_ instanceof Long && JSGuards.isLongRepresentableAsInt32(operandNodeValue__ = ((Long)operandNodeValue_).longValue())) {
            return this.doLong(operandNodeValue__);
        }
        if ((state_0 & 0xF0) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0000) >>> 16, operandNodeValue_)) {
            double operandNodeValue__5 = JSTypesGen.asImplicitDouble((state_0 & 0xF0000) >>> 16, operandNodeValue_);
            if ((state_0 & 0x10) != 0 && !JSGuards.isDoubleLargerThan2e32(operandNodeValue__5)) {
                return this.doDoubleFitsInt(operandNodeValue__5);
            }
            if ((state_0 & 0x20) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue__5) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue__5) && JSGuards.isDoubleSafeInteger(operandNodeValue__5)) {
                return this.doDoubleRepresentableAsSafeInteger(operandNodeValue__5);
            }
            if ((state_0 & 0x40) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue__5) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue__5)) {
                return this.doDoubleRepresentableAsLong(operandNodeValue__5);
            }
            if ((state_0 & 0x80) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue__5) && !JSGuards.isDoubleRepresentableAsLong(operandNodeValue__5)) {
                return this.doDouble(operandNodeValue__5);
            }
        }
        if ((state_0 & 0x300) != 0) {
            if ((state_0 & 0x100) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
                return this.doUndefined(operandNodeValue_);
            }
            if ((state_0 & 0x200) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
                return this.doNull(operandNodeValue_);
            }
        }
        if ((state_0 & 0x400) != 0 && operandNodeValue_ instanceof TruffleString) {
            TruffleString operandNodeValue__6 = (TruffleString)operandNodeValue_;
            return this.doString(operandNodeValue__6, this.string_stringToNumberNode_);
        }
        if ((state_0 & 0x800) != 0 && operandNodeValue_ instanceof Symbol) {
            Symbol operandNodeValue__7 = (Symbol)operandNodeValue_;
            return this.doSymbol(operandNodeValue__7);
        }
        if ((state_0 & 0x1000) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__8 = (BigInt)operandNodeValue_;
            return this.doBigInt(operandNodeValue__8);
        }
        if ((state_0 & 0x4000) != 0 && operandNodeValue_ instanceof JSObject) {
            JSObject operandNodeValue__9 = (JSObject)operandNodeValue_;
            if (!this.isBitwiseOr() || !this.hasOverloadedOperators(operandNodeValue__9)) {
                return this.doJSObject(operandNodeValue__9, this.jSObject_toDoubleNode_);
            }
        }
        if ((state_0 & 0x8000) != 0 && JSGuards.isForeignObject(operandNodeValue_)) {
            return JSToInt32Node.doForeignObject(operandNodeValue_, this.foreignObject_toPrimitiveNode_, this.foreignObject_toInt32Node_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return (Integer)this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x2000) == 0 && (state_0 & 0xFFFF) != 0) {
            this.executeInt(frameValue);
            return;
        }
        this.executeInt(frameValue);
    }

    private Object executeAndSpecialize(Object operandNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            long operandNodeValue_;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (operandNodeValue instanceof Integer) {
                int operandNodeValue_2 = (Integer)operandNodeValue;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Integer n = this.doInteger(operandNodeValue_2);
                return n;
            }
            if (operandNodeValue instanceof SafeInteger) {
                SafeInteger operandNodeValue_3 = (SafeInteger)operandNodeValue;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Integer n = this.doSafeInteger(operandNodeValue_3);
                return n;
            }
            if (operandNodeValue instanceof Boolean) {
                boolean operandNodeValue_4 = (Boolean)operandNodeValue;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Integer n = this.doBoolean(operandNodeValue_4);
                return n;
            }
            if (operandNodeValue instanceof Long && JSGuards.isLongRepresentableAsInt32(operandNodeValue_ = ((Long)operandNodeValue).longValue())) {
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                Integer n = this.doLong(operandNodeValue_);
                return n;
            }
            int doubleCast02 = JSTypesGen.specializeImplicitDouble(operandNodeValue);
            if (doubleCast02 != 0) {
                double operandNodeValue_5 = JSTypesGen.asImplicitDouble(doubleCast02, operandNodeValue);
                if (!JSGuards.isDoubleLargerThan2e32(operandNodeValue_5)) {
                    state_0 |= doubleCast02 << 16;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doDoubleFitsInt(operandNodeValue_5);
                    return n;
                }
                if (exclude == 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_5) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_5) && JSGuards.isDoubleSafeInteger(operandNodeValue_5)) {
                    state_0 |= doubleCast02 << 16;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doDoubleRepresentableAsSafeInteger(operandNodeValue_5);
                    return n;
                }
                if (JSGuards.isDoubleLargerThan2e32(operandNodeValue_5) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_5)) {
                    this.exclude_ = exclude |= 1;
                    state_0 &= 0xFFFFFFDF;
                    state_0 |= doubleCast02 << 16;
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doDoubleRepresentableAsLong(operandNodeValue_5);
                    return n;
                }
                if (JSGuards.isDoubleLargerThan2e32(operandNodeValue_5) && !JSGuards.isDoubleRepresentableAsLong(operandNodeValue_5)) {
                    state_0 |= doubleCast02 << 16;
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doDouble(operandNodeValue_5);
                    return n;
                }
            }
            if (JSGuards.isUndefined(operandNodeValue)) {
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                Integer doubleCast02 = this.doUndefined(operandNodeValue);
                return doubleCast02;
            }
            if (JSGuards.isJSNull(operandNodeValue)) {
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                Integer doubleCast02 = this.doNull(operandNodeValue);
                return doubleCast02;
            }
            if (operandNodeValue instanceof TruffleString) {
                TruffleString operandNodeValue_6 = (TruffleString)operandNodeValue;
                this.string_stringToNumberNode_ = super.insert(JSStringToNumberNode.create());
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                Integer n = this.doString(operandNodeValue_6, this.string_stringToNumberNode_);
                return n;
            }
            if (operandNodeValue instanceof Symbol) {
                Symbol operandNodeValue_7 = (Symbol)operandNodeValue;
                this.state_0_ = state_0 |= 0x800;
                lock.unlock();
                hasLock = false;
                Integer n = this.doSymbol(operandNodeValue_7);
                return n;
            }
            if (operandNodeValue instanceof BigInt) {
                BigInt operandNodeValue_8 = (BigInt)operandNodeValue;
                this.state_0_ = state_0 |= 0x1000;
                lock.unlock();
                hasLock = false;
                Integer n = this.doBigInt(operandNodeValue_8);
                return n;
            }
            if (operandNodeValue instanceof JSOverloadedOperatorsObject) {
                JSOverloadedOperatorsObject operandNodeValue_9 = (JSOverloadedOperatorsObject)operandNodeValue;
                if (this.isBitwiseOr()) {
                    this.overloadedOperator_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
                    this.state_0_ = state_0 |= 0x2000;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doOverloadedOperator(operandNodeValue_9, this.overloadedOperator_overloadedOperatorNode_);
                    return object;
                }
            }
            if (operandNodeValue instanceof JSObject) {
                JSObject operandNodeValue_10 = (JSObject)operandNodeValue;
                if (!this.isBitwiseOr() || !this.hasOverloadedOperators(operandNodeValue_10)) {
                    this.jSObject_toDoubleNode_ = super.insert(JSToDoubleNode.create());
                    this.state_0_ = state_0 |= 0x4000;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doJSObject(operandNodeValue_10, this.jSObject_toDoubleNode_);
                    return n;
                }
            }
            if (JSGuards.isForeignObject(operandNodeValue)) {
                this.foreignObject_toPrimitiveNode_ = super.insert(JSToPrimitiveNode.createHintNumber());
                this.foreignObject_toInt32Node_ = super.insert(JSToInt32Node.create());
                this.state_0_ = state_0 |= 0x8000;
                lock.unlock();
                hasLock = false;
                Integer n = JSToInt32Node.doForeignObject(operandNodeValue, this.foreignObject_toPrimitiveNode_, this.foreignObject_toInt32Node_);
                return n;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.operandNode}, operandNodeValue);
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
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doLong";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doDoubleFitsInt";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doDoubleRepresentableAsSafeInteger";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
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
        s[0] = "doUndefined";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doNull";
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
            cached.add(Arrays.asList(this.jSObject_toDoubleNode_));
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
            cached.add(Arrays.asList(this.foreignObject_toPrimitiveNode_, this.foreignObject_toInt32Node_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[16] = s;
        return Introspection.Provider.create(data);
    }

    public static JSToInt32Node create(JavaScriptNode operand, boolean bitwiseOr) {
        return new JSToInt32NodeGen(operand, bitwiseOr);
    }
}

