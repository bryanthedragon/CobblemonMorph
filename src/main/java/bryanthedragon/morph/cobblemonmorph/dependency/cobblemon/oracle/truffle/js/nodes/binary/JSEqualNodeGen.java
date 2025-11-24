
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsPrimitiveNode;
import com.oracle.truffle.js.nodes.binary.JSEqualNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSEqualNode.class)
public final class JSEqualNodeGen
extends JSEqualNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int state_1_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private InteropLibrary bInterop;
    @Node.Child
    private InteropLibrary aInterop;
    @Node.Child
    private JSToPrimitiveNode toPrimitive;
    @Node.Child
    private IsPrimitiveNode isPrimitive;
    @Node.Child
    private JSEqualNode equal;
    @Node.Child
    private TruffleString.EqualNode string_equalsNode_;
    @Node.Child
    private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private JSToBooleanNode overloaded_toBooleanNode_;

    private JSEqualNodeGen(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    private boolean fallbackGuard_(int state_0, int state_1, Object leftNodeValue, Object rightNodeValue) {
        JSObject rightNodeValue_;
        Object rightNodeValue_2;
        Object leftNodeValue_;
        if (JSTypesGen.isImplicitDouble(leftNodeValue) && JSTypesGen.isImplicitDouble(rightNodeValue)) {
            return false;
        }
        if ((state_0 & 8) == 0 && leftNodeValue instanceof BigInt && rightNodeValue instanceof BigInt) {
            return false;
        }
        if (JSTypesGen.isImplicitDouble(leftNodeValue)) {
            if ((state_0 & 0x10) == 0 && rightNodeValue instanceof TruffleString) {
                return false;
            }
            if ((state_0 & 0x20) == 0 && rightNodeValue instanceof Boolean) {
                return false;
            }
        }
        if (leftNodeValue instanceof Boolean) {
            if ((state_0 & 0x40) == 0 && rightNodeValue instanceof Boolean) {
                return false;
            }
            if (JSTypesGen.isImplicitDouble(rightNodeValue)) {
                return false;
            }
            if ((state_0 & 0x200) == 0 && rightNodeValue instanceof TruffleString) {
                return false;
            }
        }
        if (leftNodeValue instanceof TruffleString) {
            if ((state_0 & 0x800) == 0 && rightNodeValue instanceof TruffleString) {
                return false;
            }
            if (JSTypesGen.isImplicitDouble(rightNodeValue)) {
                return false;
            }
            if ((state_0 & 0x2000) == 0 && rightNodeValue instanceof Boolean) {
                return false;
            }
            if ((state_0 & 0x4000) == 0 && rightNodeValue instanceof BigInt) {
                return false;
            }
        }
        if ((state_0 & 0x8000) == 0 && leftNodeValue instanceof BigInt && rightNodeValue instanceof TruffleString) {
            return false;
        }
        if ((state_0 & 0x10000) == 0 && leftNodeValue instanceof Boolean && rightNodeValue instanceof BigInt) {
            return false;
        }
        if ((state_0 & 0x20000) == 0 && leftNodeValue instanceof BigInt && rightNodeValue instanceof Boolean) {
            return false;
        }
        if ((state_0 & 0x40000) == 0 && JSRuntime.isNullOrUndefined(leftNodeValue) && JSRuntime.isNullOrUndefined(rightNodeValue)) {
            return false;
        }
        if ((state_0 & 0x80000) == 0 && JSRuntime.isNullOrUndefined(leftNodeValue)) {
            return false;
        }
        if ((state_0 & 0x100000) == 0 && JSRuntime.isNullOrUndefined(rightNodeValue)) {
            return false;
        }
        if ((state_0 & 0x200000) == 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
            return false;
        }
        if (leftNodeValue instanceof JSObject && rightNodeValue instanceof JSDynamicObject && !this.hasOverloadedOperators(leftNodeValue_ = (JSObject)leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue_2 = (JSDynamicObject)rightNodeValue)) {
            return false;
        }
        if (leftNodeValue instanceof JSDynamicObject && rightNodeValue instanceof JSObject && !this.hasOverloadedOperators(leftNodeValue_ = (JSDynamicObject)leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue_2 = (JSObject)rightNodeValue)) {
            return false;
        }
        if (leftNodeValue instanceof JSObject && !this.hasOverloadedOperators(leftNodeValue_ = (JSObject)leftNodeValue) && ((state_0 & 0x2000000) == 0 || this.isPrimitive.executeBoolean(rightNodeValue))) {
            return false;
        }
        if (rightNodeValue instanceof JSObject && !this.hasOverloadedOperators(rightNodeValue_ = (JSObject)rightNodeValue) && ((state_0 & 0x8000000) == 0 || this.isPrimitive.executeBoolean(leftNodeValue))) {
            return false;
        }
        if (leftNodeValue instanceof BigInt && JSTypesGen.isImplicitDouble(rightNodeValue)) {
            return false;
        }
        if (JSTypesGen.isImplicitDouble(leftNodeValue) && rightNodeValue instanceof BigInt) {
            return false;
        }
        if (leftNodeValue instanceof Symbol) {
            if ((state_1 & 1) == 0 && rightNodeValue instanceof Symbol) {
                return false;
            }
            if ((state_1 & 2) == 0 && !JSGuards.isSymbol(rightNodeValue) && !JSRuntime.isObject(rightNodeValue)) {
                return false;
            }
        }
        if ((state_1 & 4) == 0 && rightNodeValue instanceof Symbol && !JSGuards.isSymbol(leftNodeValue) && !JSRuntime.isObject(leftNodeValue)) {
            return false;
        }
        if ((state_1 & 8) == 0 && (JSRuntime.isForeignObject(leftNodeValue) || JSRuntime.isForeignObject(rightNodeValue))) {
            return false;
        }
        if (leftNodeValue instanceof Number && rightNodeValue instanceof Number && JSRuntime.isJavaNumber(leftNodeValue_ = (Number)leftNodeValue) && JSRuntime.isJavaNumber(rightNodeValue_2 = (Number)rightNodeValue)) {
            return false;
        }
        if ((state_1 & 0x20) == 0 && rightNodeValue instanceof TruffleString && JSRuntime.isJavaNumber(leftNodeValue)) {
            return false;
        }
        return (state_1 & 0x40) != 0 || !(leftNodeValue instanceof TruffleString) || !JSRuntime.isJavaNumber(rightNodeValue);
    }

    @Override
    public boolean executeBoolean(Object leftNodeValue, Object rightNodeValue) {
        JSObject jSObject;
        JSObject jSObject2;
        Object rightNodeValue_;
        int state_0 = this.state_0_;
        int state_1 = this.state_1_;
        if ((state_0 & 3) != 0 && leftNodeValue instanceof Integer) {
            int n = (Integer)leftNodeValue;
            if ((state_0 & 1) != 0 && rightNodeValue instanceof Integer) {
                int rightNodeValue_3 = (Integer)rightNodeValue;
                return JSEqualNode.doInt(n, rightNodeValue_3);
            }
            if ((state_0 & 2) != 0 && rightNodeValue instanceof Boolean) {
                boolean rightNodeValue_4 = (Boolean)rightNodeValue;
                return JSEqualNode.doIntBoolean(n, rightNodeValue_4);
            }
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue)) {
            double d = JSTypesGen.asImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue);
            if (JSTypesGen.isImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue)) {
                double rightNodeValue_5 = JSTypesGen.asImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue);
                return JSEqualNode.doDouble(d, rightNodeValue_5);
            }
        }
        if ((state_0 & 8) != 0 && leftNodeValue instanceof BigInt) {
            BigInt bigInt = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_6 = (BigInt)rightNodeValue;
                return JSEqualNode.doBigInt(bigInt, rightNodeValue_6);
            }
        }
        if ((state_0 & 0x30) != 0 && JSTypesGen.isImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue)) {
            double d = JSTypesGen.asImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue);
            if ((state_0 & 0x10) != 0 && rightNodeValue instanceof TruffleString) {
                TruffleString rightNodeValue_7 = (TruffleString)rightNodeValue;
                return this.doDoubleString(d, rightNodeValue_7);
            }
            if ((state_0 & 0x20) != 0 && rightNodeValue instanceof Boolean) {
                boolean rightNodeValue_8 = (Boolean)rightNodeValue;
                return JSEqualNode.doDoubleBoolean(d, rightNodeValue_8);
            }
        }
        if ((state_0 & 0x3C0) != 0 && leftNodeValue instanceof Boolean) {
            boolean bl = (Boolean)leftNodeValue;
            if ((state_0 & 0x40) != 0 && rightNodeValue instanceof Boolean) {
                boolean rightNodeValue_9 = (Boolean)rightNodeValue;
                return JSEqualNode.doBoolean(bl, rightNodeValue_9);
            }
            if ((state_0 & 0x80) != 0 && rightNodeValue instanceof Integer) {
                int rightNodeValue_10 = (Integer)rightNodeValue;
                return JSEqualNode.doBooleanInt(bl, rightNodeValue_10);
            }
            if ((state_0 & 0x100) != 0 && JSTypesGen.isImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue)) {
                double rightNodeValue_11 = JSTypesGen.asImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue);
                return JSEqualNode.doBooleanDouble(bl, rightNodeValue_11);
            }
            if ((state_0 & 0x200) != 0 && rightNodeValue instanceof TruffleString) {
                TruffleString rightNodeValue_12 = (TruffleString)rightNodeValue;
                return this.doBooleanString(bl, rightNodeValue_12);
            }
        }
        if ((state_0 & 0x7C00) != 0 && leftNodeValue instanceof TruffleString) {
            TruffleString truffleString = (TruffleString)leftNodeValue;
            if ((state_0 & 0xC00) != 0 && rightNodeValue instanceof TruffleString) {
                TruffleString rightNodeValue_2 = (TruffleString)rightNodeValue;
                if ((state_0 & 0x400) != 0 && JSGuards.isReferenceEquals(truffleString, rightNodeValue_2)) {
                    return JSEqualNode.doStringIdentity(truffleString, rightNodeValue_2);
                }
                if ((state_0 & 0x800) != 0) {
                    return JSEqualNode.doString(truffleString, rightNodeValue_2, this.string_equalsNode_);
                }
            }
            if ((state_0 & 0x1000) != 0 && JSTypesGen.isImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue)) {
                double rightNodeValue_13 = JSTypesGen.asImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue);
                return this.doStringDouble(truffleString, rightNodeValue_13);
            }
            if ((state_0 & 0x2000) != 0 && rightNodeValue instanceof Boolean) {
                boolean rightNodeValue_14 = (Boolean)rightNodeValue;
                return this.doStringBoolean(truffleString, rightNodeValue_14);
            }
            if ((state_0 & 0x4000) != 0 && rightNodeValue instanceof BigInt) {
                rightNodeValue_ = (BigInt)rightNodeValue;
                return this.doStringBigInt(truffleString, (BigInt)rightNodeValue_);
            }
        }
        if ((state_0 & 0x8000) != 0 && leftNodeValue instanceof BigInt) {
            BigInt bigInt = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof TruffleString) {
                rightNodeValue_ = (TruffleString)rightNodeValue;
                return this.doBigIntString(bigInt, (TruffleString)rightNodeValue_);
            }
        }
        if ((state_0 & 0x10000) != 0 && leftNodeValue instanceof Boolean) {
            boolean bl = (Boolean)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
                rightNodeValue_ = (BigInt)rightNodeValue;
                return this.doBooleanBigInt(bl, (BigInt)rightNodeValue_);
            }
        }
        if ((state_0 & 0x20000) != 0 && leftNodeValue instanceof BigInt) {
            BigInt bigInt = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof Boolean) {
                boolean rightNodeValue_15 = (Boolean)rightNodeValue;
                return this.doBigIntBoolean(bigInt, rightNodeValue_15);
            }
        }
        if ((state_0 & 0x3C0000) != 0) {
            if ((state_0 & 0x40000) != 0 && JSRuntime.isNullOrUndefined(leftNodeValue) && JSRuntime.isNullOrUndefined(rightNodeValue)) {
                return JSEqualNode.doBothNullOrUndefined(leftNodeValue, rightNodeValue);
            }
            if ((state_0 & 0x80000) != 0 && JSRuntime.isNullOrUndefined(leftNodeValue)) {
                return JSEqualNode.doLeftNullOrUndefined(leftNodeValue, rightNodeValue, this.bInterop);
            }
            if ((state_0 & 0x100000) != 0 && JSRuntime.isNullOrUndefined(rightNodeValue)) {
                return JSEqualNode.doRightNullOrUndefined(leftNodeValue, rightNodeValue, this.aInterop);
            }
            if ((state_0 & 0x200000) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
                return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_);
            }
        }
        if ((state_0 & 0x400000) != 0 && leftNodeValue instanceof JSObject) {
            JSObject jSObject3 = (JSObject)leftNodeValue;
            if (rightNodeValue instanceof JSDynamicObject) {
                rightNodeValue_ = (JSDynamicObject)rightNodeValue;
                if (!this.hasOverloadedOperators(jSObject3) && !this.hasOverloadedOperators(rightNodeValue_)) {
                    return JSEqualNode.doJSObject(jSObject3, (JSDynamicObject)rightNodeValue_);
                }
            }
        }
        if ((state_0 & 0x800000) != 0 && leftNodeValue instanceof JSDynamicObject) {
            JSDynamicObject jSDynamicObject = (JSDynamicObject)leftNodeValue;
            if (rightNodeValue instanceof JSObject) {
                rightNodeValue_ = (JSObject)rightNodeValue;
                if (!this.hasOverloadedOperators(jSDynamicObject) && !this.hasOverloadedOperators(rightNodeValue_)) {
                    return JSEqualNode.doJSObject(jSDynamicObject, (JSObject)rightNodeValue_);
                }
            }
        }
        if ((state_0 & 0x1000000) != 0 && leftNodeValue instanceof JSObject && !this.hasOverloadedOperators(jSObject2 = (JSObject)leftNodeValue) && this.isPrimitive.executeBoolean(rightNodeValue)) {
            return this.doJSObjectVsPrimitive(jSObject2, rightNodeValue, this.bInterop, this.toPrimitive, this.isPrimitive, this.equal);
        }
        if ((state_0 & 0x4000000) != 0 && rightNodeValue instanceof JSObject && !this.hasOverloadedOperators(jSObject = (JSObject)rightNodeValue) && this.isPrimitive.executeBoolean(leftNodeValue)) {
            return this.doJSObjectVsPrimitive(leftNodeValue, jSObject, this.aInterop, this.toPrimitive, this.isPrimitive, this.equal);
        }
        if ((state_0 & 0x30000000) != 0 && leftNodeValue instanceof BigInt) {
            BigInt bigInt = (BigInt)leftNodeValue;
            if ((state_0 & 0x10000000) != 0 && rightNodeValue instanceof Integer) {
                int rightNodeValue_16 = (Integer)rightNodeValue;
                return this.doBigIntAndInt(bigInt, rightNodeValue_16);
            }
            if ((state_0 & 0x20000000) != 0 && JSTypesGen.isImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue)) {
                double rightNodeValue_17 = JSTypesGen.asImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue);
                return this.doBigIntAndNumber(bigInt, rightNodeValue_17);
            }
        }
        if ((state_0 & 0xC0000000) != 0 && rightNodeValue instanceof BigInt) {
            BigInt bigInt = (BigInt)rightNodeValue;
            if ((state_0 & 0x40000000) != 0 && leftNodeValue instanceof Integer) {
                int leftNodeValue_13 = (Integer)leftNodeValue;
                return this.doIntAndBigInt(leftNodeValue_13, bigInt);
            }
            if ((state_0 & Integer.MIN_VALUE) != 0 && JSTypesGen.isImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue)) {
                double leftNodeValue_14 = JSTypesGen.asImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue);
                return this.doNumberAndBigInt(leftNodeValue_14, bigInt);
            }
        }
        if ((state_1 & 3) != 0 && leftNodeValue instanceof Symbol) {
            Symbol symbol = (Symbol)leftNodeValue;
            if ((state_1 & 1) != 0 && rightNodeValue instanceof Symbol) {
                rightNodeValue_ = (Symbol)rightNodeValue;
                return JSEqualNode.doSymbol(symbol, (Symbol)rightNodeValue_);
            }
            if ((state_1 & 2) != 0 && !JSGuards.isSymbol(rightNodeValue) && !JSRuntime.isObject(rightNodeValue)) {
                return JSEqualNode.doSymbolNotSymbol(symbol, rightNodeValue);
            }
        }
        if ((state_1 & 0xC) != 0) {
            if ((state_1 & 4) != 0 && rightNodeValue instanceof Symbol) {
                Symbol symbol = (Symbol)rightNodeValue;
                if (!JSGuards.isSymbol(leftNodeValue) && !JSRuntime.isObject(leftNodeValue)) {
                    return JSEqualNode.doSymbolNotSymbol(leftNodeValue, symbol);
                }
            }
            if ((state_1 & 8) != 0) {
                boolean bl = JSRuntime.isForeignObject(leftNodeValue);
                boolean foreign_isBForeign__ = JSRuntime.isForeignObject(rightNodeValue);
                if (bl || foreign_isBForeign__) {
                    return this.doForeign(leftNodeValue, rightNodeValue, bl, foreign_isBForeign__, this.aInterop, this.bInterop, this.toPrimitive, this.isPrimitive, this.equal);
                }
            }
        }
        if ((state_1 & 0x10) != 0 && leftNodeValue instanceof Number) {
            Number number = (Number)leftNodeValue;
            if (rightNodeValue instanceof Number) {
                Number rightNodeValue_20 = (Number)rightNodeValue;
                if (JSRuntime.isJavaNumber(number) && JSRuntime.isJavaNumber(rightNodeValue_20)) {
                    return JSEqualNode.doNumber(number, rightNodeValue_20);
                }
            }
        }
        if ((state_1 & 0x20) != 0 && rightNodeValue instanceof TruffleString) {
            TruffleString truffleString = (TruffleString)rightNodeValue;
            if (JSRuntime.isJavaNumber(leftNodeValue)) {
                return this.doNumberString(leftNodeValue, truffleString);
            }
        }
        if ((state_1 & 0xC0) != 0) {
            if ((state_1 & 0x40) != 0 && leftNodeValue instanceof TruffleString) {
                TruffleString truffleString = (TruffleString)leftNodeValue;
                if (JSRuntime.isJavaNumber(rightNodeValue)) {
                    return this.doStringNumber(truffleString, rightNodeValue);
                }
            }
            if ((state_1 & 0x80) != 0 && this.fallbackGuard_(state_0, state_1, leftNodeValue, rightNodeValue)) {
                return JSEqualNode.doFallback(leftNodeValue, rightNodeValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        int state_1 = this.state_1_;
        if ((state_0 & 0xF5FFFFFE) == 0 && (state_1 & 0xFF) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
            return this.executeBoolean_int_int0(state_0, state_1, frameValue);
        }
        if ((state_0 & 0xF5FFFFFD) == 0 && (state_1 & 0xFF) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
            return this.executeBoolean_int_boolean1(state_0, state_1, frameValue);
        }
        if ((state_0 & 0xF5FFFFFB) == 0 && (state_1 & 0xFF) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
            return this.executeBoolean_double_double2(state_0, state_1, frameValue);
        }
        if ((state_0 & 0xF5FFFFDF) == 0 && (state_1 & 0xFF) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
            return this.executeBoolean_double_boolean3(state_0, state_1, frameValue);
        }
        if ((state_0 & 0xF5FFFFBF) == 0 && (state_1 & 0xFF) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
            return this.executeBoolean_boolean_boolean4(state_0, state_1, frameValue);
        }
        if ((state_0 & 0xF5FFFF7F) == 0 && (state_1 & 0xFF) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
            return this.executeBoolean_boolean_int5(state_0, state_1, frameValue);
        }
        if ((state_0 & 0xF5FFFEFF) == 0 && (state_1 & 0xFF) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
            return this.executeBoolean_boolean_double6(state_0, state_1, frameValue);
        }
        if ((state_0 & 0x75FFFFCF) == 0 && (state_1 & 0xFF) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
            return this.executeBoolean_double7(state_0, state_1, frameValue);
        }
        if ((state_0 & 0xF5FEFDFF) == 0 && (state_1 & 0xFF) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
            return this.executeBoolean_boolean8(state_0, state_1, frameValue);
        }
        if ((state_0 & 0xD5FFEFFF) == 0 && (state_1 & 0xFF) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
            return this.executeBoolean_double9(state_0, state_1, frameValue);
        }
        if ((state_0 & 0xF5FDDFFF) == 0 && (state_1 & 0xFF) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
            return this.executeBoolean_boolean10(state_0, state_1, frameValue);
        }
        if ((state_0 & 0xE5FFFFFF) == 0 && (state_1 & 0xFF) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
            return this.executeBoolean_int11(state_0, state_1, frameValue);
        }
        if ((state_0 & 0xB5FFFFFF) == 0 && (state_1 & 0xFF) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
            return this.executeBoolean_int12(state_0, state_1, frameValue);
        }
        return this.executeBoolean_generic13(state_0, state_1, frameValue);
    }

    private boolean executeBoolean_int_int0(int state_0, int state_1, VirtualFrame frameValue) {
        int rightNodeValue_;
        int leftNodeValue_;
        try {
            leftNodeValue_ = this.leftNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        try {
            rightNodeValue_ = this.rightNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return JSEqualNode.doInt(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_int_boolean1(int state_0, int state_1, VirtualFrame frameValue) {
        boolean rightNodeValue_;
        int leftNodeValue_;
        try {
            leftNodeValue_ = this.leftNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        try {
            rightNodeValue_ = this.rightNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 2) != 0);
        return JSEqualNode.doIntBoolean(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_double_double2(int state_0, int state_1, VirtualFrame frameValue) {
        double rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_1 & 0xE00) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_1 & 0xD00) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_1 & 0x700) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        long rightNodeValue_long = 0L;
        int rightNodeValue_int = 0;
        try {
            if ((state_1 & 0xE000) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_1 & 0xD000) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_1 & 0x7000) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_1 & 0xD00) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0) ? (Number)leftNodeValue_int : (Number)((state_1 & 0x700) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0) ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult());
        }
        assert ((state_0 & 4) != 0);
        return JSEqualNode.doDouble(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_double_boolean3(int state_0, int state_1, VirtualFrame frameValue) {
        boolean rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_1 & 0xE00) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_1 & 0xD00) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_1 & 0x700) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        try {
            rightNodeValue_ = this.rightNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_1 & 0xD00) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0) ? (Number)leftNodeValue_int : (Number)((state_1 & 0x700) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0) ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult());
        }
        assert ((state_0 & 0x20) != 0);
        return JSEqualNode.doDoubleBoolean(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_boolean_boolean4(int state_0, int state_1, VirtualFrame frameValue) {
        boolean rightNodeValue_;
        boolean leftNodeValue_;
        try {
            leftNodeValue_ = this.leftNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        try {
            rightNodeValue_ = this.rightNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 0x40) != 0);
        return JSEqualNode.doBoolean(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_boolean_int5(int state_0, int state_1, VirtualFrame frameValue) {
        int rightNodeValue_;
        boolean leftNodeValue_;
        try {
            leftNodeValue_ = this.leftNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        try {
            rightNodeValue_ = this.rightNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 0x80) != 0);
        return JSEqualNode.doBooleanInt(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_boolean_double6(int state_0, int state_1, VirtualFrame frameValue) {
        double rightNodeValue_;
        boolean leftNodeValue_;
        try {
            leftNodeValue_ = this.leftNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        long rightNodeValue_long = 0L;
        int rightNodeValue_int = 0;
        try {
            if ((state_1 & 0xE000) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_1 & 0xD000) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_1 & 0x7000) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 0x100) != 0);
        return JSEqualNode.doBooleanDouble(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_double7(int state_0, int state_1, VirtualFrame frameValue) {
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_1 & 0xE00) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_1 & 0xD00) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_1 & 0x700) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 0x10) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
            return this.doDoubleString(leftNodeValue_, rightNodeValue__);
        }
        if ((state_0 & 0x20) != 0 && rightNodeValue_ instanceof Boolean) {
            boolean rightNodeValue__ = (Boolean)rightNodeValue_;
            return JSEqualNode.doDoubleBoolean(leftNodeValue_, rightNodeValue__);
        }
        if ((state_0 & Integer.MIN_VALUE) != 0 && rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return this.doNumberAndBigInt(leftNodeValue_, rightNodeValue__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize((state_1 & 0xD00) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0) ? (Number)leftNodeValue_int : (Number)((state_1 & 0x700) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0) ? (Number)leftNodeValue_long : (Number)leftNodeValue_), rightNodeValue_);
    }

    private boolean executeBoolean_boolean8(int state_0, int state_1, VirtualFrame frameValue) {
        boolean leftNodeValue_;
        try {
            leftNodeValue_ = this.leftNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 0x200) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
            return this.doBooleanString(leftNodeValue_, rightNodeValue__);
        }
        if ((state_0 & 0x10000) != 0 && rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return this.doBooleanBigInt(leftNodeValue_, rightNodeValue__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_double9(int state_0, int state_1, VirtualFrame frameValue) {
        Object leftNodeValue__;
        double rightNodeValue_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        long rightNodeValue_long = 0L;
        int rightNodeValue_int = 0;
        try {
            if ((state_1 & 0xE000) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_1 & 0xD000) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_1 & 0x7000) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0)) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        if ((state_0 & 0x1000) != 0 && leftNodeValue_ instanceof TruffleString) {
            leftNodeValue__ = (TruffleString)leftNodeValue_;
            return this.doStringDouble((TruffleString)leftNodeValue__, rightNodeValue_);
        }
        if ((state_0 & 0x20000000) != 0 && leftNodeValue_ instanceof BigInt) {
            leftNodeValue__ = (BigInt)leftNodeValue_;
            return this.doBigIntAndNumber((BigInt)leftNodeValue__, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, (state_1 & 0xD000) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0) ? (Number)rightNodeValue_int : (Number)((state_1 & 0x7000) == 0 && ((state_0 & 0xF5FFFFFF) != 0 || (state_1 & 0xFF) != 0) ? (Number)rightNodeValue_long : (Number)rightNodeValue_));
    }

    private boolean executeBoolean_boolean10(int state_0, int state_1, VirtualFrame frameValue) {
        boolean rightNodeValue_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        try {
            rightNodeValue_ = this.rightNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        if ((state_0 & 0x2000) != 0 && leftNodeValue_ instanceof TruffleString) {
            TruffleString leftNodeValue__ = (TruffleString)leftNodeValue_;
            return this.doStringBoolean(leftNodeValue__, rightNodeValue_);
        }
        if ((state_0 & 0x20000) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            return this.doBigIntBoolean(leftNodeValue__, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_int11(int state_0, int state_1, VirtualFrame frameValue) {
        int rightNodeValue_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        try {
            rightNodeValue_ = this.rightNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 0x10000000) != 0);
        if (leftNodeValue_ instanceof BigInt) {
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            return this.doBigIntAndInt(leftNodeValue__, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_int12(int state_0, int state_1, VirtualFrame frameValue) {
        int leftNodeValue_;
        try {
            leftNodeValue_ = this.leftNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        assert ((state_0 & 0x40000000) != 0);
        if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return this.doIntAndBigInt(leftNodeValue_, rightNodeValue__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_generic13(int state_0, int state_1, VirtualFrame frameValue) {
        JSObject jSObject;
        JSObject jSObject2;
        Object rightNodeValue__;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 3) != 0 && leftNodeValue_ instanceof Integer) {
            int n = (Integer)leftNodeValue_;
            if ((state_0 & 1) != 0 && rightNodeValue_ instanceof Integer) {
                int rightNodeValue__3 = (Integer)rightNodeValue_;
                return JSEqualNode.doInt(n, rightNodeValue__3);
            }
            if ((state_0 & 2) != 0 && rightNodeValue_ instanceof Boolean) {
                boolean rightNodeValue__4 = (Boolean)rightNodeValue_;
                return JSEqualNode.doIntBoolean(n, rightNodeValue__4);
            }
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue_)) {
            double d = JSTypesGen.asImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue_);
            if (JSTypesGen.isImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue_)) {
                double rightNodeValue__5 = JSTypesGen.asImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue_);
                return JSEqualNode.doDouble(d, rightNodeValue__5);
            }
        }
        if ((state_0 & 8) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt bigInt = (BigInt)leftNodeValue_;
            if (rightNodeValue_ instanceof BigInt) {
                BigInt rightNodeValue__6 = (BigInt)rightNodeValue_;
                return JSEqualNode.doBigInt(bigInt, rightNodeValue__6);
            }
        }
        if ((state_0 & 0x30) != 0 && JSTypesGen.isImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue_)) {
            double d = JSTypesGen.asImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue_);
            if ((state_0 & 0x10) != 0 && rightNodeValue_ instanceof TruffleString) {
                TruffleString rightNodeValue__7 = (TruffleString)rightNodeValue_;
                return this.doDoubleString(d, rightNodeValue__7);
            }
            if ((state_0 & 0x20) != 0 && rightNodeValue_ instanceof Boolean) {
                boolean rightNodeValue__8 = (Boolean)rightNodeValue_;
                return JSEqualNode.doDoubleBoolean(d, rightNodeValue__8);
            }
        }
        if ((state_0 & 0x3C0) != 0 && leftNodeValue_ instanceof Boolean) {
            boolean bl = (Boolean)leftNodeValue_;
            if ((state_0 & 0x40) != 0 && rightNodeValue_ instanceof Boolean) {
                boolean rightNodeValue__9 = (Boolean)rightNodeValue_;
                return JSEqualNode.doBoolean(bl, rightNodeValue__9);
            }
            if ((state_0 & 0x80) != 0 && rightNodeValue_ instanceof Integer) {
                int rightNodeValue__10 = (Integer)rightNodeValue_;
                return JSEqualNode.doBooleanInt(bl, rightNodeValue__10);
            }
            if ((state_0 & 0x100) != 0 && JSTypesGen.isImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue_)) {
                double rightNodeValue__11 = JSTypesGen.asImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue_);
                return JSEqualNode.doBooleanDouble(bl, rightNodeValue__11);
            }
            if ((state_0 & 0x200) != 0 && rightNodeValue_ instanceof TruffleString) {
                TruffleString rightNodeValue__12 = (TruffleString)rightNodeValue_;
                return this.doBooleanString(bl, rightNodeValue__12);
            }
        }
        if ((state_0 & 0x7C00) != 0 && leftNodeValue_ instanceof TruffleString) {
            TruffleString truffleString = (TruffleString)leftNodeValue_;
            if ((state_0 & 0xC00) != 0 && rightNodeValue_ instanceof TruffleString) {
                TruffleString rightNodeValue__2 = (TruffleString)rightNodeValue_;
                if ((state_0 & 0x400) != 0 && JSGuards.isReferenceEquals(truffleString, rightNodeValue__2)) {
                    return JSEqualNode.doStringIdentity(truffleString, rightNodeValue__2);
                }
                if ((state_0 & 0x800) != 0) {
                    return JSEqualNode.doString(truffleString, rightNodeValue__2, this.string_equalsNode_);
                }
            }
            if ((state_0 & 0x1000) != 0 && JSTypesGen.isImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue_)) {
                double rightNodeValue__13 = JSTypesGen.asImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue_);
                return this.doStringDouble(truffleString, rightNodeValue__13);
            }
            if ((state_0 & 0x2000) != 0 && rightNodeValue_ instanceof Boolean) {
                boolean rightNodeValue__14 = (Boolean)rightNodeValue_;
                return this.doStringBoolean(truffleString, rightNodeValue__14);
            }
            if ((state_0 & 0x4000) != 0 && rightNodeValue_ instanceof BigInt) {
                rightNodeValue__ = (BigInt)rightNodeValue_;
                return this.doStringBigInt(truffleString, (BigInt)rightNodeValue__);
            }
        }
        if ((state_0 & 0x8000) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt bigInt = (BigInt)leftNodeValue_;
            if (rightNodeValue_ instanceof TruffleString) {
                rightNodeValue__ = (TruffleString)rightNodeValue_;
                return this.doBigIntString(bigInt, (TruffleString)rightNodeValue__);
            }
        }
        if ((state_0 & 0x10000) != 0 && leftNodeValue_ instanceof Boolean) {
            boolean bl = (Boolean)leftNodeValue_;
            if (rightNodeValue_ instanceof BigInt) {
                rightNodeValue__ = (BigInt)rightNodeValue_;
                return this.doBooleanBigInt(bl, (BigInt)rightNodeValue__);
            }
        }
        if ((state_0 & 0x20000) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt bigInt = (BigInt)leftNodeValue_;
            if (rightNodeValue_ instanceof Boolean) {
                boolean rightNodeValue__15 = (Boolean)rightNodeValue_;
                return this.doBigIntBoolean(bigInt, rightNodeValue__15);
            }
        }
        if ((state_0 & 0x3C0000) != 0) {
            if ((state_0 & 0x40000) != 0 && JSRuntime.isNullOrUndefined(leftNodeValue_) && JSRuntime.isNullOrUndefined(rightNodeValue_)) {
                return JSEqualNode.doBothNullOrUndefined(leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 0x80000) != 0 && JSRuntime.isNullOrUndefined(leftNodeValue_)) {
                return JSEqualNode.doLeftNullOrUndefined(leftNodeValue_, rightNodeValue_, this.bInterop);
            }
            if ((state_0 & 0x100000) != 0 && JSRuntime.isNullOrUndefined(rightNodeValue_)) {
                return JSEqualNode.doRightNullOrUndefined(leftNodeValue_, rightNodeValue_, this.aInterop);
            }
            if ((state_0 & 0x200000) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
                return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_);
            }
        }
        if ((state_0 & 0x400000) != 0 && leftNodeValue_ instanceof JSObject) {
            JSObject jSObject3 = (JSObject)leftNodeValue_;
            if (rightNodeValue_ instanceof JSDynamicObject) {
                rightNodeValue__ = (JSDynamicObject)rightNodeValue_;
                if (!this.hasOverloadedOperators(jSObject3) && !this.hasOverloadedOperators(rightNodeValue__)) {
                    return JSEqualNode.doJSObject(jSObject3, (JSDynamicObject)rightNodeValue__);
                }
            }
        }
        if ((state_0 & 0x800000) != 0 && leftNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject jSDynamicObject = (JSDynamicObject)leftNodeValue_;
            if (rightNodeValue_ instanceof JSObject) {
                rightNodeValue__ = (JSObject)rightNodeValue_;
                if (!this.hasOverloadedOperators(jSDynamicObject) && !this.hasOverloadedOperators(rightNodeValue__)) {
                    return JSEqualNode.doJSObject(jSDynamicObject, (JSObject)rightNodeValue__);
                }
            }
        }
        if ((state_0 & 0x1000000) != 0 && leftNodeValue_ instanceof JSObject && !this.hasOverloadedOperators(jSObject2 = (JSObject)leftNodeValue_) && this.isPrimitive.executeBoolean(rightNodeValue_)) {
            return this.doJSObjectVsPrimitive(jSObject2, rightNodeValue_, this.bInterop, this.toPrimitive, this.isPrimitive, this.equal);
        }
        if ((state_0 & 0x4000000) != 0 && rightNodeValue_ instanceof JSObject && !this.hasOverloadedOperators(jSObject = (JSObject)rightNodeValue_) && this.isPrimitive.executeBoolean(leftNodeValue_)) {
            return this.doJSObjectVsPrimitive(leftNodeValue_, jSObject, this.aInterop, this.toPrimitive, this.isPrimitive, this.equal);
        }
        if ((state_0 & 0x30000000) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt bigInt = (BigInt)leftNodeValue_;
            if ((state_0 & 0x10000000) != 0 && rightNodeValue_ instanceof Integer) {
                int rightNodeValue__16 = (Integer)rightNodeValue_;
                return this.doBigIntAndInt(bigInt, rightNodeValue__16);
            }
            if ((state_0 & 0x20000000) != 0 && JSTypesGen.isImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue_)) {
                double rightNodeValue__17 = JSTypesGen.asImplicitDouble((state_1 & 0xF000) >>> 12, rightNodeValue_);
                return this.doBigIntAndNumber(bigInt, rightNodeValue__17);
            }
        }
        if ((state_0 & 0xC0000000) != 0 && rightNodeValue_ instanceof BigInt) {
            BigInt bigInt = (BigInt)rightNodeValue_;
            if ((state_0 & 0x40000000) != 0 && leftNodeValue_ instanceof Integer) {
                int leftNodeValue__13 = (Integer)leftNodeValue_;
                return this.doIntAndBigInt(leftNodeValue__13, bigInt);
            }
            if ((state_0 & Integer.MIN_VALUE) != 0 && JSTypesGen.isImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue_)) {
                double leftNodeValue__14 = JSTypesGen.asImplicitDouble((state_1 & 0xF00) >>> 8, leftNodeValue_);
                return this.doNumberAndBigInt(leftNodeValue__14, bigInt);
            }
        }
        if ((state_1 & 3) != 0 && leftNodeValue_ instanceof Symbol) {
            Symbol symbol = (Symbol)leftNodeValue_;
            if ((state_1 & 1) != 0 && rightNodeValue_ instanceof Symbol) {
                rightNodeValue__ = (Symbol)rightNodeValue_;
                return JSEqualNode.doSymbol(symbol, (Symbol)rightNodeValue__);
            }
            if ((state_1 & 2) != 0 && !JSGuards.isSymbol(rightNodeValue_) && !JSRuntime.isObject(rightNodeValue_)) {
                return JSEqualNode.doSymbolNotSymbol(symbol, rightNodeValue_);
            }
        }
        if ((state_1 & 0xC) != 0) {
            if ((state_1 & 4) != 0 && rightNodeValue_ instanceof Symbol) {
                Symbol symbol = (Symbol)rightNodeValue_;
                if (!JSGuards.isSymbol(leftNodeValue_) && !JSRuntime.isObject(leftNodeValue_)) {
                    return JSEqualNode.doSymbolNotSymbol(leftNodeValue_, symbol);
                }
            }
            if ((state_1 & 8) != 0) {
                boolean bl = JSRuntime.isForeignObject(leftNodeValue_);
                boolean foreign_isBForeign__ = JSRuntime.isForeignObject(rightNodeValue_);
                if (bl || foreign_isBForeign__) {
                    return this.doForeign(leftNodeValue_, rightNodeValue_, bl, foreign_isBForeign__, this.aInterop, this.bInterop, this.toPrimitive, this.isPrimitive, this.equal);
                }
            }
        }
        if ((state_1 & 0x10) != 0 && leftNodeValue_ instanceof Number) {
            Number number = (Number)leftNodeValue_;
            if (rightNodeValue_ instanceof Number) {
                Number rightNodeValue__20 = (Number)rightNodeValue_;
                if (JSRuntime.isJavaNumber(number) && JSRuntime.isJavaNumber(rightNodeValue__20)) {
                    return JSEqualNode.doNumber(number, rightNodeValue__20);
                }
            }
        }
        if ((state_1 & 0x20) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString truffleString = (TruffleString)rightNodeValue_;
            if (JSRuntime.isJavaNumber(leftNodeValue_)) {
                return this.doNumberString(leftNodeValue_, truffleString);
            }
        }
        if ((state_1 & 0xC0) != 0) {
            if ((state_1 & 0x40) != 0 && leftNodeValue_ instanceof TruffleString) {
                TruffleString truffleString = (TruffleString)leftNodeValue_;
                if (JSRuntime.isJavaNumber(rightNodeValue_)) {
                    return this.doStringNumber(truffleString, rightNodeValue_);
                }
            }
            if ((state_1 & 0x80) != 0 && this.fallbackGuard_(state_0, state_1, leftNodeValue_, rightNodeValue_)) {
                return JSEqualNode.doFallback(leftNodeValue_, rightNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeBoolean(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            double leftNodeValue_;
            int doubleCast0;
            int state_0 = this.state_0_;
            int state_1 = this.state_1_;
            int exclude = this.exclude_;
            if (leftNodeValue instanceof Integer) {
                int leftNodeValue_2 = (Integer)leftNodeValue;
                if (rightNodeValue instanceof Integer) {
                    int rightNodeValue_ = (Integer)rightNodeValue;
                    this.state_0_ = state_0 |= 1;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSEqualNode.doInt(leftNodeValue_2, rightNodeValue_);
                    return bl;
                }
                if (rightNodeValue instanceof Boolean) {
                    boolean rightNodeValue_ = (Boolean)rightNodeValue;
                    this.state_0_ = state_0 |= 2;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSEqualNode.doIntBoolean(leftNodeValue_2, rightNodeValue_);
                    return bl;
                }
            }
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
                leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
                int doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue);
                if (doubleCast1 != 0) {
                    double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                    state_1 |= doubleCast0 << 8;
                    this.state_0_ = state_0 |= 4;
                    this.state_1_ = state_1 |= doubleCast1 << 12;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSEqualNode.doDouble(leftNodeValue_, rightNodeValue_);
                    return bl;
                }
            }
            if (leftNodeValue instanceof BigInt) {
                BigInt leftNodeValue_3 = (BigInt)leftNodeValue;
                if (rightNodeValue instanceof BigInt) {
                    BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                    this.state_0_ = state_0 |= 8;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSEqualNode.doBigInt(leftNodeValue_3, rightNodeValue_);
                    return bl;
                }
            }
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
                leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
                if (rightNodeValue instanceof TruffleString) {
                    TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
                    this.state_0_ = state_0 |= 0x10;
                    this.state_1_ = state_1 |= doubleCast0 << 8;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doDoubleString(leftNodeValue_, rightNodeValue_);
                    return bl;
                }
                if (rightNodeValue instanceof Boolean) {
                    boolean rightNodeValue_ = (Boolean)rightNodeValue;
                    this.state_0_ = state_0 |= 0x20;
                    this.state_1_ = state_1 |= doubleCast0 << 8;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSEqualNode.doDoubleBoolean(leftNodeValue_, rightNodeValue_);
                    return bl;
                }
            }
            if (leftNodeValue instanceof Boolean) {
                boolean leftNodeValue_4 = (Boolean)leftNodeValue;
                if (rightNodeValue instanceof Boolean) {
                    boolean rightNodeValue_ = (Boolean)rightNodeValue;
                    this.state_0_ = state_0 |= 0x40;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSEqualNode.doBoolean(leftNodeValue_4, rightNodeValue_);
                    return bl;
                }
                if (rightNodeValue instanceof Integer) {
                    int rightNodeValue_ = (Integer)rightNodeValue;
                    this.state_0_ = state_0 |= 0x80;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSEqualNode.doBooleanInt(leftNodeValue_4, rightNodeValue_);
                    return bl;
                }
                int doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue);
                if (doubleCast1 != 0) {
                    double rightNodeValue_2 = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                    this.state_0_ = state_0 |= 0x100;
                    this.state_1_ = state_1 |= doubleCast1 << 12;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSEqualNode.doBooleanDouble(leftNodeValue_4, rightNodeValue_2);
                    return bl;
                }
                if (rightNodeValue instanceof TruffleString) {
                    TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
                    this.state_0_ = state_0 |= 0x200;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_2 = this.doBooleanString(leftNodeValue_4, rightNodeValue_);
                    return rightNodeValue_2;
                }
            }
            if (leftNodeValue instanceof TruffleString) {
                TruffleString leftNodeValue_5 = (TruffleString)leftNodeValue;
                if (rightNodeValue instanceof TruffleString) {
                    TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
                    if (exclude == 0 && JSGuards.isReferenceEquals(leftNodeValue_5, rightNodeValue_)) {
                        this.state_0_ = state_0 |= 0x400;
                        this.state_1_ = state_1;
                        lock.unlock();
                        hasLock = false;
                        boolean rightNodeValue_2 = JSEqualNode.doStringIdentity(leftNodeValue_5, rightNodeValue_);
                        return rightNodeValue_2;
                    }
                    this.string_equalsNode_ = super.insert(TruffleString.EqualNode.create());
                    this.exclude_ = exclude |= 1;
                    state_0 &= 0xFFFFFBFF;
                    this.state_0_ = state_0 |= 0x800;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_2 = JSEqualNode.doString(leftNodeValue_5, rightNodeValue_, this.string_equalsNode_);
                    return rightNodeValue_2;
                }
                int doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue);
                if (doubleCast1 != 0) {
                    double rightNodeValue_3 = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                    this.state_0_ = state_0 |= 0x1000;
                    this.state_1_ = state_1 |= doubleCast1 << 12;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doStringDouble(leftNodeValue_5, rightNodeValue_3);
                    return bl;
                }
                if (rightNodeValue instanceof Boolean) {
                    boolean rightNodeValue_ = (Boolean)rightNodeValue;
                    this.state_0_ = state_0 |= 0x2000;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_3 = this.doStringBoolean(leftNodeValue_5, rightNodeValue_);
                    return rightNodeValue_3;
                }
                if (rightNodeValue instanceof BigInt) {
                    BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                    this.state_0_ = state_0 |= 0x4000;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_3 = this.doStringBigInt(leftNodeValue_5, rightNodeValue_);
                    return rightNodeValue_3;
                }
            }
            if (leftNodeValue instanceof BigInt) {
                BigInt leftNodeValue_6 = (BigInt)leftNodeValue;
                if (rightNodeValue instanceof TruffleString) {
                    TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
                    this.state_0_ = state_0 |= 0x8000;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_3 = this.doBigIntString(leftNodeValue_6, rightNodeValue_);
                    return rightNodeValue_3;
                }
            }
            if (leftNodeValue instanceof Boolean) {
                boolean leftNodeValue_7 = (Boolean)leftNodeValue;
                if (rightNodeValue instanceof BigInt) {
                    BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                    this.state_0_ = state_0 |= 0x10000;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_3 = this.doBooleanBigInt(leftNodeValue_7, rightNodeValue_);
                    return rightNodeValue_3;
                }
            }
            if (leftNodeValue instanceof BigInt) {
                BigInt leftNodeValue_8 = (BigInt)leftNodeValue;
                if (rightNodeValue instanceof Boolean) {
                    boolean rightNodeValue_ = (Boolean)rightNodeValue;
                    this.state_0_ = state_0 |= 0x20000;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_3 = this.doBigIntBoolean(leftNodeValue_8, rightNodeValue_);
                    return rightNodeValue_3;
                }
            }
            if (JSRuntime.isNullOrUndefined(leftNodeValue) && JSRuntime.isNullOrUndefined(rightNodeValue)) {
                this.state_0_ = state_0 |= 0x40000;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean leftNodeValue_8 = JSEqualNode.doBothNullOrUndefined(leftNodeValue, rightNodeValue);
                return leftNodeValue_8;
            }
            if (JSRuntime.isNullOrUndefined(leftNodeValue)) {
                this.bInterop = super.insert(this.bInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.bInterop);
                this.state_0_ = state_0 |= 0x80000;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean leftNodeValue_8 = JSEqualNode.doLeftNullOrUndefined(leftNodeValue, rightNodeValue, this.bInterop);
                return leftNodeValue_8;
            }
            if (JSRuntime.isNullOrUndefined(rightNodeValue)) {
                this.aInterop = super.insert(this.aInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.aInterop);
                this.state_0_ = state_0 |= 0x100000;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean leftNodeValue_8 = JSEqualNode.doRightNullOrUndefined(leftNodeValue, rightNodeValue, this.aInterop);
                return leftNodeValue_8;
            }
            if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
                this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createHintDefault(JSEqualNode.getOverloadedOperatorName()));
                this.overloaded_toBooleanNode_ = super.insert(JSToBooleanNode.create());
                this.state_0_ = state_0 |= 0x200000;
                this.state_1_ = state_1;
                lock.unlock();
                hasLock = false;
                boolean leftNodeValue_8 = this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_);
                return leftNodeValue_8;
            }
            if (leftNodeValue instanceof JSObject) {
                JSObject leftNodeValue_9 = (JSObject)leftNodeValue;
                if (rightNodeValue instanceof JSDynamicObject) {
                    JSDynamicObject rightNodeValue_ = (JSDynamicObject)rightNodeValue;
                    if (!this.hasOverloadedOperators(leftNodeValue_9) && !this.hasOverloadedOperators(rightNodeValue_)) {
                        this.state_0_ = state_0 |= 0x400000;
                        this.state_1_ = state_1;
                        lock.unlock();
                        hasLock = false;
                        boolean rightNodeValue_3 = JSEqualNode.doJSObject(leftNodeValue_9, rightNodeValue_);
                        return rightNodeValue_3;
                    }
                }
            }
            if (leftNodeValue instanceof JSDynamicObject) {
                JSDynamicObject leftNodeValue_10 = (JSDynamicObject)leftNodeValue;
                if (rightNodeValue instanceof JSObject) {
                    JSObject rightNodeValue_ = (JSObject)rightNodeValue;
                    if (!this.hasOverloadedOperators(leftNodeValue_10) && !this.hasOverloadedOperators(rightNodeValue_)) {
                        this.state_0_ = state_0 |= 0x800000;
                        this.state_1_ = state_1;
                        lock.unlock();
                        hasLock = false;
                        boolean rightNodeValue_3 = JSEqualNode.doJSObject(leftNodeValue_10, rightNodeValue_);
                        return rightNodeValue_3;
                    }
                }
            }
            if (leftNodeValue instanceof JSObject) {
                JSObject leftNodeValue_11 = (JSObject)leftNodeValue;
                boolean JSObjectVsPrimitive0_duplicateFound_ = false;
                if ((state_0 & 0x1000000) != 0 && !this.hasOverloadedOperators(leftNodeValue_11) && this.isPrimitive.executeBoolean(rightNodeValue)) {
                    JSObjectVsPrimitive0_duplicateFound_ = true;
                }
                if (!JSObjectVsPrimitive0_duplicateFound_ && !this.hasOverloadedOperators(leftNodeValue_11)) {
                    IsPrimitiveNode jSObjectVsPrimitive0_isPrimitiveNode___check;
                    if ((state_0 & 0x2000000) == 0) {
                        if (this.isPrimitive == null) {
                            jSObjectVsPrimitive0_isPrimitiveNode___check = super.insert(this.isPrimitive == null ? IsPrimitiveNode.create() : this.isPrimitive);
                            if (jSObjectVsPrimitive0_isPrimitiveNode___check == null) {
                                throw new AssertionError((Object)"Specialization 'doJSObjectVsPrimitive(JSObject, Object, InteropLibrary, JSToPrimitiveNode, IsPrimitiveNode, JSEqualNode)' contains a shared cache with name 'isPrimitiveNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isPrimitive = jSObjectVsPrimitive0_isPrimitiveNode___check;
                        }
                        this.state_0_ = state_0 |= 0x2000000;
                        this.state_1_ = state_1;
                    }
                    if (this.isPrimitive.executeBoolean(rightNodeValue) && (state_0 & 0x1000000) == 0) {
                        this.bInterop = super.insert(this.bInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.bInterop);
                        this.toPrimitive = super.insert(this.toPrimitive == null ? JSToPrimitiveNode.createHintDefault() : this.toPrimitive);
                        if (this.isPrimitive == null) {
                            jSObjectVsPrimitive0_isPrimitiveNode___check = super.insert(this.isPrimitive == null ? IsPrimitiveNode.create() : this.isPrimitive);
                            if (jSObjectVsPrimitive0_isPrimitiveNode___check == null) {
                                throw new AssertionError((Object)"Specialization 'doJSObjectVsPrimitive(JSObject, Object, InteropLibrary, JSToPrimitiveNode, IsPrimitiveNode, JSEqualNode)' contains a shared cache with name 'isPrimitiveNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isPrimitive = jSObjectVsPrimitive0_isPrimitiveNode___check;
                        }
                        this.equal = super.insert(this.equal == null ? JSEqualNode.create() : this.equal);
                        this.state_0_ = state_0 |= 0x1000000;
                        this.state_1_ = state_1;
                        JSObjectVsPrimitive0_duplicateFound_ = true;
                    }
                }
                if (JSObjectVsPrimitive0_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    boolean jSObjectVsPrimitive0_isPrimitiveNode___check = this.doJSObjectVsPrimitive(leftNodeValue_11, rightNodeValue, this.bInterop, this.toPrimitive, this.isPrimitive, this.equal);
                    return jSObjectVsPrimitive0_isPrimitiveNode___check;
                }
            }
            if (rightNodeValue instanceof JSObject) {
                JSObject rightNodeValue_ = (JSObject)rightNodeValue;
                boolean JSObjectVsPrimitive1_duplicateFound_ = false;
                if ((state_0 & 0x4000000) != 0 && !this.hasOverloadedOperators(rightNodeValue_) && this.isPrimitive.executeBoolean(leftNodeValue)) {
                    JSObjectVsPrimitive1_duplicateFound_ = true;
                }
                if (!JSObjectVsPrimitive1_duplicateFound_ && !this.hasOverloadedOperators(rightNodeValue_)) {
                    IsPrimitiveNode jSObjectVsPrimitive1_isPrimitiveNode___check;
                    if ((state_0 & 0x8000000) == 0) {
                        if (this.isPrimitive == null) {
                            jSObjectVsPrimitive1_isPrimitiveNode___check = super.insert(this.isPrimitive == null ? IsPrimitiveNode.create() : this.isPrimitive);
                            if (jSObjectVsPrimitive1_isPrimitiveNode___check == null) {
                                throw new AssertionError((Object)"Specialization 'doJSObjectVsPrimitive(Object, JSObject, InteropLibrary, JSToPrimitiveNode, IsPrimitiveNode, JSEqualNode)' contains a shared cache with name 'isPrimitiveNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isPrimitive = jSObjectVsPrimitive1_isPrimitiveNode___check;
                        }
                        this.state_0_ = state_0 |= 0x8000000;
                        this.state_1_ = state_1;
                    }
                    if (this.isPrimitive.executeBoolean(leftNodeValue) && (state_0 & 0x4000000) == 0) {
                        this.aInterop = super.insert(this.aInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.aInterop);
                        this.toPrimitive = super.insert(this.toPrimitive == null ? JSToPrimitiveNode.createHintDefault() : this.toPrimitive);
                        if (this.isPrimitive == null) {
                            jSObjectVsPrimitive1_isPrimitiveNode___check = super.insert(this.isPrimitive == null ? IsPrimitiveNode.create() : this.isPrimitive);
                            if (jSObjectVsPrimitive1_isPrimitiveNode___check == null) {
                                throw new AssertionError((Object)"Specialization 'doJSObjectVsPrimitive(Object, JSObject, InteropLibrary, JSToPrimitiveNode, IsPrimitiveNode, JSEqualNode)' contains a shared cache with name 'isPrimitiveNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isPrimitive = jSObjectVsPrimitive1_isPrimitiveNode___check;
                        }
                        this.equal = super.insert(this.equal == null ? JSEqualNode.create() : this.equal);
                        this.state_0_ = state_0 |= 0x4000000;
                        this.state_1_ = state_1;
                        JSObjectVsPrimitive1_duplicateFound_ = true;
                    }
                }
                if (JSObjectVsPrimitive1_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    boolean jSObjectVsPrimitive1_isPrimitiveNode___check = this.doJSObjectVsPrimitive(leftNodeValue, rightNodeValue_, this.aInterop, this.toPrimitive, this.isPrimitive, this.equal);
                    return jSObjectVsPrimitive1_isPrimitiveNode___check;
                }
            }
            if (leftNodeValue instanceof BigInt) {
                BigInt leftNodeValue_12 = (BigInt)leftNodeValue;
                if (rightNodeValue instanceof Integer) {
                    int rightNodeValue_ = (Integer)rightNodeValue;
                    this.state_0_ = state_0 |= 0x10000000;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean jSObjectVsPrimitive1_isPrimitiveNode___check = this.doBigIntAndInt(leftNodeValue_12, rightNodeValue_);
                    return jSObjectVsPrimitive1_isPrimitiveNode___check;
                }
                int doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue);
                if (doubleCast1 != 0) {
                    double rightNodeValue_4 = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                    this.state_0_ = state_0 |= 0x20000000;
                    this.state_1_ = state_1 |= doubleCast1 << 12;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doBigIntAndNumber(leftNodeValue_12, rightNodeValue_4);
                    return bl;
                }
            }
            if (rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                if (leftNodeValue instanceof Integer) {
                    int leftNodeValue_13 = (Integer)leftNodeValue;
                    this.state_0_ = state_0 |= 0x40000000;
                    this.state_1_ = state_1;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_4 = this.doIntAndBigInt(leftNodeValue_13, rightNodeValue_);
                    return rightNodeValue_4;
                }
                int doubleCast02 = JSTypesGen.specializeImplicitDouble(leftNodeValue);
                if (doubleCast02 != 0) {
                    double leftNodeValue_14 = JSTypesGen.asImplicitDouble(doubleCast02, leftNodeValue);
                    this.state_0_ = state_0 |= Integer.MIN_VALUE;
                    this.state_1_ = state_1 |= doubleCast02 << 8;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doNumberAndBigInt(leftNodeValue_14, rightNodeValue_);
                    return bl;
                }
            }
            if (leftNodeValue instanceof Symbol) {
                Symbol leftNodeValue_15 = (Symbol)leftNodeValue;
                if (rightNodeValue instanceof Symbol) {
                    Symbol rightNodeValue_5 = (Symbol)rightNodeValue;
                    this.state_0_ = state_0;
                    this.state_1_ = state_1 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSEqualNode.doSymbol(leftNodeValue_15, rightNodeValue_5);
                    return bl;
                }
                if (!JSGuards.isSymbol(rightNodeValue) && !JSRuntime.isObject(rightNodeValue)) {
                    this.state_0_ = state_0;
                    this.state_1_ = state_1 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_5 = JSEqualNode.doSymbolNotSymbol(leftNodeValue_15, rightNodeValue);
                    return rightNodeValue_5;
                }
            }
            if (rightNodeValue instanceof Symbol) {
                Symbol rightNodeValue_ = (Symbol)rightNodeValue;
                if (!JSGuards.isSymbol(leftNodeValue) && !JSRuntime.isObject(leftNodeValue)) {
                    this.state_0_ = state_0;
                    this.state_1_ = state_1 |= 4;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_5 = JSEqualNode.doSymbolNotSymbol(leftNodeValue, rightNodeValue_);
                    return rightNodeValue_5;
                }
            }
            boolean foreign_isBForeign__ = false;
            boolean foreign_isAForeign__ = false;
            foreign_isAForeign__ = JSRuntime.isForeignObject(leftNodeValue);
            foreign_isBForeign__ = JSRuntime.isForeignObject(rightNodeValue);
            if (foreign_isAForeign__ || foreign_isBForeign__) {
                this.aInterop = super.insert(this.aInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.aInterop);
                this.bInterop = super.insert(this.bInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.bInterop);
                this.toPrimitive = super.insert(this.toPrimitive == null ? JSToPrimitiveNode.createHintDefault() : this.toPrimitive);
                this.isPrimitive = super.insert(this.isPrimitive == null ? IsPrimitiveNode.create() : this.isPrimitive);
                this.equal = super.insert(this.equal == null ? JSEqualNode.create() : this.equal);
                this.state_0_ = state_0;
                this.state_1_ = state_1 |= 8;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doForeign(leftNodeValue, rightNodeValue, foreign_isAForeign__, foreign_isBForeign__, this.aInterop, this.bInterop, this.toPrimitive, this.isPrimitive, this.equal);
                return bl;
            }
            if (leftNodeValue instanceof Number) {
                Number leftNodeValue_16 = (Number)leftNodeValue;
                if (rightNodeValue instanceof Number) {
                    Number rightNodeValue_ = (Number)rightNodeValue;
                    if (JSRuntime.isJavaNumber(leftNodeValue_16) && JSRuntime.isJavaNumber(rightNodeValue_)) {
                        this.state_0_ = state_0;
                        this.state_1_ = state_1 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = JSEqualNode.doNumber(leftNodeValue_16, rightNodeValue_);
                        return bl;
                    }
                }
            }
            if (rightNodeValue instanceof TruffleString) {
                TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
                if (JSRuntime.isJavaNumber(leftNodeValue)) {
                    this.state_0_ = state_0;
                    this.state_1_ = state_1 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doNumberString(leftNodeValue, rightNodeValue_);
                    return bl;
                }
            }
            if (leftNodeValue instanceof TruffleString) {
                TruffleString leftNodeValue_17 = (TruffleString)leftNodeValue;
                if (JSRuntime.isJavaNumber(rightNodeValue)) {
                    this.state_0_ = state_0;
                    this.state_1_ = state_1 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doStringNumber(leftNodeValue_17, rightNodeValue);
                    return bl;
                }
            }
            this.state_0_ = state_0;
            this.state_1_ = state_1 |= 0x80;
            lock.unlock();
            hasLock = false;
            boolean bl = JSEqualNode.doFallback(leftNodeValue, rightNodeValue);
            return bl;
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
        int state_1 = this.state_1_;
        if ((state_0 & 0xF5FFFFFF) == 0 && (state_1 & 0xFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        int counter = 0;
        counter += Integer.bitCount(state_0 & 0xF5FFFFFF);
        if ((counter += Integer.bitCount(state_1 & 0xFF)) == 1) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Node>> cached;
        Object[] data = new Object[39];
        data[0] = 0;
        int state_0 = this.state_0_;
        int state_1 = this.state_1_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doIntBoolean";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doDoubleString";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doDoubleBoolean";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doBooleanInt";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doBooleanDouble";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doBooleanString";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        s = new Object[3];
        s[0] = "doStringIdentity";
        s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[11] = s;
        s = new Object[3];
        s[0] = "doString";
        if ((state_0 & 0x800) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Node>>();
            cached.add(Arrays.asList(this.string_equalsNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[12] = s;
        s = new Object[3];
        s[0] = "doStringDouble";
        s[1] = (state_0 & 0x1000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[13] = s;
        s = new Object[3];
        s[0] = "doStringBoolean";
        s[1] = (state_0 & 0x2000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[14] = s;
        s = new Object[3];
        s[0] = "doStringBigInt";
        s[1] = (state_0 & 0x4000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[15] = s;
        s = new Object[3];
        s[0] = "doBigIntString";
        s[1] = (state_0 & 0x8000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[16] = s;
        s = new Object[3];
        s[0] = "doBooleanBigInt";
        s[1] = (state_0 & 0x10000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[17] = s;
        s = new Object[3];
        s[0] = "doBigIntBoolean";
        s[1] = (state_0 & 0x20000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[18] = s;
        s = new Object[3];
        s[0] = "doBothNullOrUndefined";
        s[1] = (state_0 & 0x40000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[19] = s;
        s = new Object[3];
        s[0] = "doLeftNullOrUndefined";
        if ((state_0 & 0x80000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.bInterop));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[20] = s;
        s = new Object[3];
        s[0] = "doRightNullOrUndefined";
        if ((state_0 & 0x100000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.aInterop));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[21] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 0x200000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[22] = s;
        s = new Object[3];
        s[0] = "doJSObject";
        s[1] = (state_0 & 0x400000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[23] = s;
        s = new Object[3];
        s[0] = "doJSObject";
        s[1] = (state_0 & 0x800000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[24] = s;
        s = new Object[3];
        s[0] = "doJSObjectVsPrimitive";
        if ((state_0 & 0x1000000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.bInterop, this.toPrimitive, this.isPrimitive, this.equal));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[25] = s;
        s = new Object[3];
        s[0] = "doJSObjectVsPrimitive";
        if ((state_0 & 0x4000000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.aInterop, this.toPrimitive, this.isPrimitive, this.equal));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[26] = s;
        s = new Object[3];
        s[0] = "doBigIntAndInt";
        s[1] = (state_0 & 0x10000000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[27] = s;
        s = new Object[3];
        s[0] = "doBigIntAndNumber";
        s[1] = (state_0 & 0x20000000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[28] = s;
        s = new Object[3];
        s[0] = "doIntAndBigInt";
        s[1] = (state_0 & 0x40000000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[29] = s;
        s = new Object[3];
        s[0] = "doNumberAndBigInt";
        s[1] = (state_0 & Integer.MIN_VALUE) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[30] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_1 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[31] = s;
        s = new Object[3];
        s[0] = "doSymbolNotSymbol";
        s[1] = (state_1 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[32] = s;
        s = new Object[3];
        s[0] = "doSymbolNotSymbol";
        s[1] = (state_1 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[33] = s;
        s = new Object[3];
        s[0] = "doForeign";
        if ((state_1 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.aInterop, this.bInterop, this.toPrimitive, this.isPrimitive, this.equal));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[34] = s;
        s = new Object[3];
        s[0] = "doNumber";
        s[1] = (state_1 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[35] = s;
        s = new Object[3];
        s[0] = "doNumberString";
        s[1] = (state_1 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[36] = s;
        s = new Object[3];
        s[0] = "doStringNumber";
        s[1] = (state_1 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[37] = s;
        s = new Object[3];
        s[0] = "doFallback";
        s[1] = (state_1 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[38] = s;
        return Introspection.Provider.create(data);
    }

    public static JSEqualNode create(JavaScriptNode left, JavaScriptNode right) {
        return new JSEqualNodeGen(left, right);
    }
}

