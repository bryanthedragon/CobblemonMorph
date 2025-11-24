/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.binary;

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
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSGreaterThanNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringOrNumberNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSGreaterThanNode.class)
public final class JSGreaterThanNodeGen
extends JSGreaterThanNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private TruffleString.CompareCharsUTF16Node string_compareNode_;
    @Node.Child
    private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private JSToBooleanNode overloaded_toBooleanNode_;
    @Node.Child
    private GenericData generic_cache;

    private JSGreaterThanNodeGen(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    @Override
    public boolean executeBoolean(Object leftNodeValue, Object rightNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            if ((state_0 & 1) != 0 && rightNodeValue instanceof Integer) {
                int rightNodeValue_ = (Integer)rightNodeValue;
                return this.doInt(leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 2) != 0 && rightNodeValue instanceof SafeInteger) {
                SafeInteger rightNodeValue_ = (SafeInteger)rightNodeValue;
                return this.doSafeInteger(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0xC) != 0 && leftNodeValue instanceof SafeInteger) {
            SafeInteger leftNodeValue_ = (SafeInteger)leftNodeValue;
            if ((state_0 & 4) != 0 && rightNodeValue instanceof Integer) {
                int rightNodeValue_ = (Integer)rightNodeValue;
                return this.doSafeInteger(leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 8) != 0 && rightNodeValue instanceof SafeInteger) {
                SafeInteger rightNodeValue_ = (SafeInteger)rightNodeValue;
                return this.doSafeInteger(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue)) {
            double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue);
            if (JSTypesGen.isImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue)) {
                double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue);
                return this.doDouble(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x60) != 0 && leftNodeValue instanceof TruffleString) {
            TruffleString leftNodeValue_ = (TruffleString)leftNodeValue;
            if ((state_0 & 0x20) != 0 && rightNodeValue instanceof TruffleString) {
                TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
                return this.doString(leftNodeValue_, rightNodeValue_, this.string_compareNode_);
            }
            if ((state_0 & 0x40) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue)) {
                double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue);
                return this.doStringDouble(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x80) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue)) {
            double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue);
            if (rightNodeValue instanceof TruffleString) {
                TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
                return this.doDoubleString(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x100) != 0 && leftNodeValue instanceof TruffleString) {
            TruffleString leftNodeValue_ = (TruffleString)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                return this.doStringBigInt(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x1E00) != 0 && leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if ((state_0 & 0x200) != 0 && rightNodeValue instanceof TruffleString) {
                TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
                return this.doBigIntString(leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 0x400) != 0 && rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                return this.doBigInt(leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 0x800) != 0 && rightNodeValue instanceof Integer) {
                int rightNodeValue_ = (Integer)rightNodeValue;
                return this.doBigIntAndInt(leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 0x1000) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue)) {
                double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue);
                return this.doBigIntAndNumber(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x6000) != 0 && rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_ = (BigInt)rightNodeValue;
            if ((state_0 & 0x2000) != 0 && leftNodeValue instanceof Integer) {
                int leftNodeValue_ = (Integer)leftNodeValue;
                return this.doIntAndBigInt(leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 0x4000) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue)) {
                double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue);
                return this.doNumberAndBigInt(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x38000) != 0) {
            GenericData s17_;
            if ((state_0 & 0x8000) != 0 && JSGuards.isJavaNumber(leftNodeValue) && JSGuards.isJavaNumber(rightNodeValue)) {
                return this.doJavaNumber(leftNodeValue, rightNodeValue);
            }
            if ((state_0 & 0x10000) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
                return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_);
            }
            if ((state_0 & 0x20000) != 0 && (s17_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                return this.doGeneric(leftNodeValue, rightNodeValue, s17_.toStringOrNumber1_, s17_.toPrimitive1_, s17_.toStringOrNumber2_, s17_.toPrimitive2_, s17_.greaterThanNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x3FFFE) == 0 && (state_0 & 0x3FFFF) != 0) {
            return this.executeBoolean_int_int0(state_0, frameValue);
        }
        if ((state_0 & 0x3FFEF) == 0 && (state_0 & 0x3FFFF) != 0) {
            return this.executeBoolean_double_double1(state_0, frameValue);
        }
        if ((state_0 & 0x3DFFD) == 0 && (state_0 & 0x3FFFF) != 0) {
            return this.executeBoolean_int2(state_0, frameValue);
        }
        if ((state_0 & 0x3F7FB) == 0 && (state_0 & 0x3FFFF) != 0) {
            return this.executeBoolean_int3(state_0, frameValue);
        }
        if ((state_0 & 0x3EFBF) == 0 && (state_0 & 0x3FFFF) != 0) {
            return this.executeBoolean_double4(state_0, frameValue);
        }
        if ((state_0 & 0x3BF7F) == 0 && (state_0 & 0x3FFFF) != 0) {
            return this.executeBoolean_double5(state_0, frameValue);
        }
        return this.executeBoolean_generic6(state_0, frameValue);
    }

    private boolean executeBoolean_int_int0(int state_0, VirtualFrame frameValue) {
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
        return this.doInt(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_double_double1(int state_0, VirtualFrame frameValue) {
        double rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x380000) == 0 && (state_0 & 0x3FFFF) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x340000) == 0 && (state_0 & 0x3FFFF) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x1C0000) == 0 && (state_0 & 0x3FFFF) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue__);
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
            if ((state_0 & 0x3800000) == 0 && (state_0 & 0x3FFFF) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x3400000) == 0 && (state_0 & 0x3FFFF) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x1C00000) == 0 && (state_0 & 0x3FFFF) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0x340000) == 0 && (state_0 & 0x3FFFF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x1C0000) == 0 && (state_0 & 0x3FFFF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult());
        }
        assert ((state_0 & 0x10) != 0);
        return this.doDouble(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_int2(int state_0, VirtualFrame frameValue) {
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
        if ((state_0 & 2) != 0 && rightNodeValue_ instanceof SafeInteger) {
            SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;
            return this.doSafeInteger(leftNodeValue_, rightNodeValue__);
        }
        if ((state_0 & 0x2000) != 0 && rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return this.doIntAndBigInt(leftNodeValue_, rightNodeValue__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_int3(int state_0, VirtualFrame frameValue) {
        int rightNodeValue_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        try {
            rightNodeValue_ = this.rightNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        if ((state_0 & 4) != 0 && leftNodeValue_ instanceof SafeInteger) {
            SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
            return this.doSafeInteger(leftNodeValue__, rightNodeValue_);
        }
        if ((state_0 & 0x800) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            return this.doBigIntAndInt(leftNodeValue__, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    private boolean executeBoolean_double4(int state_0, VirtualFrame frameValue) {
        Object leftNodeValue__;
        double rightNodeValue_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        long rightNodeValue_long = 0L;
        int rightNodeValue_int = 0;
        try {
            if ((state_0 & 0x3800000) == 0 && (state_0 & 0x3FFFF) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x3400000) == 0 && (state_0 & 0x3FFFF) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x1C00000) == 0 && (state_0 & 0x3FFFF) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        if ((state_0 & 0x40) != 0 && leftNodeValue_ instanceof TruffleString) {
            leftNodeValue__ = (TruffleString)leftNodeValue_;
            return this.doStringDouble((TruffleString)leftNodeValue__, rightNodeValue_);
        }
        if ((state_0 & 0x1000) != 0 && leftNodeValue_ instanceof BigInt) {
            leftNodeValue__ = (BigInt)leftNodeValue_;
            return this.doBigIntAndNumber((BigInt)leftNodeValue__, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, (state_0 & 0x3400000) == 0 && (state_0 & 0x3FFFF) != 0 ? (Number)rightNodeValue_int : (Number)((state_0 & 0x1C00000) == 0 && (state_0 & 0x3FFFF) != 0 ? (Number)rightNodeValue_long : (Number)rightNodeValue_));
    }

    private boolean executeBoolean_double5(int state_0, VirtualFrame frameValue) {
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x380000) == 0 && (state_0 & 0x3FFFF) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x340000) == 0 && (state_0 & 0x3FFFF) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x1C0000) == 0 && (state_0 & 0x3FFFF) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 0x80) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
            return this.doDoubleString(leftNodeValue_, rightNodeValue__);
        }
        if ((state_0 & 0x4000) != 0 && rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return this.doNumberAndBigInt(leftNodeValue_, rightNodeValue__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize((state_0 & 0x340000) == 0 && (state_0 & 0x3FFFF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x1C0000) == 0 && (state_0 & 0x3FFFF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), rightNodeValue_);
    }

    private boolean executeBoolean_generic6(int state_0, VirtualFrame frameValue) {
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 3) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__ = (Integer)leftNodeValue_;
            if ((state_0 & 1) != 0 && rightNodeValue_ instanceof Integer) {
                int rightNodeValue__ = (Integer)rightNodeValue_;
                return this.doInt(leftNodeValue__, rightNodeValue__);
            }
            if ((state_0 & 2) != 0 && rightNodeValue_ instanceof SafeInteger) {
                SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;
                return this.doSafeInteger(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0xC) != 0 && leftNodeValue_ instanceof SafeInteger) {
            SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
            if ((state_0 & 4) != 0 && rightNodeValue_ instanceof Integer) {
                int rightNodeValue__ = (Integer)rightNodeValue_;
                return this.doSafeInteger(leftNodeValue__, rightNodeValue__);
            }
            if ((state_0 & 8) != 0 && rightNodeValue_ instanceof SafeInteger) {
                SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;
                return this.doSafeInteger(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue_)) {
            double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue_);
            if (JSTypesGen.isImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue_)) {
                double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue_);
                return this.doDouble(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x60) != 0 && leftNodeValue_ instanceof TruffleString) {
            TruffleString leftNodeValue__ = (TruffleString)leftNodeValue_;
            if ((state_0 & 0x20) != 0 && rightNodeValue_ instanceof TruffleString) {
                TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
                return this.doString(leftNodeValue__, rightNodeValue__, this.string_compareNode_);
            }
            if ((state_0 & 0x40) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue_)) {
                double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue_);
                return this.doStringDouble(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x80) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue_)) {
            double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue_);
            if (rightNodeValue_ instanceof TruffleString) {
                TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
                return this.doDoubleString(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x100) != 0 && leftNodeValue_ instanceof TruffleString) {
            TruffleString leftNodeValue__ = (TruffleString)leftNodeValue_;
            if (rightNodeValue_ instanceof BigInt) {
                BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
                return this.doStringBigInt(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x1E00) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            if ((state_0 & 0x200) != 0 && rightNodeValue_ instanceof TruffleString) {
                TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
                return this.doBigIntString(leftNodeValue__, rightNodeValue__);
            }
            if ((state_0 & 0x400) != 0 && rightNodeValue_ instanceof BigInt) {
                BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
                return this.doBigInt(leftNodeValue__, rightNodeValue__);
            }
            if ((state_0 & 0x800) != 0 && rightNodeValue_ instanceof Integer) {
                int rightNodeValue__ = (Integer)rightNodeValue_;
                return this.doBigIntAndInt(leftNodeValue__, rightNodeValue__);
            }
            if ((state_0 & 0x1000) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue_)) {
                double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00000) >>> 22, rightNodeValue_);
                return this.doBigIntAndNumber(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x6000) != 0 && rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            if ((state_0 & 0x2000) != 0 && leftNodeValue_ instanceof Integer) {
                int leftNodeValue__ = (Integer)leftNodeValue_;
                return this.doIntAndBigInt(leftNodeValue__, rightNodeValue__);
            }
            if ((state_0 & 0x4000) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue_)) {
                double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0000) >>> 18, leftNodeValue_);
                return this.doNumberAndBigInt(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x38000) != 0) {
            GenericData s17_;
            if ((state_0 & 0x8000) != 0 && JSGuards.isJavaNumber(leftNodeValue_) && JSGuards.isJavaNumber(rightNodeValue_)) {
                return this.doJavaNumber(leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 0x10000) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
                return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_);
            }
            if ((state_0 & 0x20000) != 0 && (s17_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
                return this.doGeneric(leftNodeValue_, rightNodeValue_, s17_.toStringOrNumber1_, s17_.toPrimitive1_, s17_.toStringOrNumber2_, s17_.toPrimitive2_, s17_.greaterThanNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeBoolean(frameValue);
    }

    private boolean executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int doubleCast0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (leftNodeValue instanceof Integer) {
                int leftNodeValue_ = (Integer)leftNodeValue;
                if ((exclude & 1) == 0 && rightNodeValue instanceof Integer) {
                    int rightNodeValue_ = (Integer)rightNodeValue;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doInt(leftNodeValue_, rightNodeValue_);
                    return bl;
                }
                if (rightNodeValue instanceof SafeInteger) {
                    SafeInteger rightNodeValue_ = (SafeInteger)rightNodeValue;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doSafeInteger(leftNodeValue_, rightNodeValue_);
                    return bl;
                }
            }
            if (leftNodeValue instanceof SafeInteger) {
                SafeInteger leftNodeValue_ = (SafeInteger)leftNodeValue;
                if (rightNodeValue instanceof Integer) {
                    int rightNodeValue_ = (Integer)rightNodeValue;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doSafeInteger(leftNodeValue_, rightNodeValue_);
                    return bl;
                }
                if (rightNodeValue instanceof SafeInteger) {
                    SafeInteger rightNodeValue_ = (SafeInteger)rightNodeValue;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doSafeInteger(leftNodeValue_, rightNodeValue_);
                    return bl;
                }
            }
            if ((exclude & 2) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
                double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
                int doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue);
                if (doubleCast1 != 0) {
                    double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                    state_0 |= doubleCast0 << 18;
                    state_0 |= doubleCast1 << 22;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doDouble(leftNodeValue_, rightNodeValue_);
                    return bl;
                }
            }
            if (leftNodeValue instanceof TruffleString) {
                int doubleCast1;
                TruffleString leftNodeValue_ = (TruffleString)leftNodeValue;
                if ((exclude & 4) == 0 && rightNodeValue instanceof TruffleString) {
                    TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
                    this.string_compareNode_ = super.insert(TruffleString.CompareCharsUTF16Node.create());
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doString(leftNodeValue_, rightNodeValue_, this.string_compareNode_);
                    return bl;
                }
                if ((exclude & 8) == 0 && (doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
                    double rightNodeValue_2 = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                    state_0 |= doubleCast1 << 22;
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doStringDouble(leftNodeValue_, rightNodeValue_2);
                    return bl;
                }
            }
            if ((exclude & 0x10) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
                double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
                if (rightNodeValue instanceof TruffleString) {
                    TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
                    state_0 |= doubleCast0 << 18;
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doDoubleString(leftNodeValue_, rightNodeValue_);
                    return bl;
                }
            }
            if (leftNodeValue instanceof TruffleString) {
                TruffleString leftNodeValue_ = (TruffleString)leftNodeValue;
                if (rightNodeValue instanceof BigInt) {
                    BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_2 = this.doStringBigInt(leftNodeValue_, rightNodeValue_);
                    return rightNodeValue_2;
                }
            }
            if (leftNodeValue instanceof BigInt) {
                int doubleCast1;
                BigInt leftNodeValue_ = (BigInt)leftNodeValue;
                if (rightNodeValue instanceof TruffleString) {
                    TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_2 = this.doBigIntString(leftNodeValue_, rightNodeValue_);
                    return rightNodeValue_2;
                }
                if ((exclude & 0x20) == 0 && rightNodeValue instanceof BigInt) {
                    BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                    this.state_0_ = state_0 |= 0x400;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_2 = this.doBigInt(leftNodeValue_, rightNodeValue_);
                    return rightNodeValue_2;
                }
                if (rightNodeValue instanceof Integer) {
                    int rightNodeValue_ = (Integer)rightNodeValue;
                    this.state_0_ = state_0 |= 0x800;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_2 = this.doBigIntAndInt(leftNodeValue_, rightNodeValue_);
                    return rightNodeValue_2;
                }
                if ((exclude & 0x40) == 0 && (doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
                    double rightNodeValue_3 = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                    state_0 |= doubleCast1 << 22;
                    this.state_0_ = state_0 |= 0x1000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doBigIntAndNumber(leftNodeValue_, rightNodeValue_3);
                    return bl;
                }
            }
            if (rightNodeValue instanceof BigInt) {
                int doubleCast02;
                BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                if (leftNodeValue instanceof Integer) {
                    int leftNodeValue_ = (Integer)leftNodeValue;
                    this.state_0_ = state_0 |= 0x2000;
                    lock.unlock();
                    hasLock = false;
                    boolean rightNodeValue_3 = this.doIntAndBigInt(leftNodeValue_, rightNodeValue_);
                    return rightNodeValue_3;
                }
                if ((exclude & 0x80) == 0 && (doubleCast02 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
                    double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast02, leftNodeValue);
                    state_0 |= doubleCast02 << 18;
                    this.state_0_ = state_0 |= 0x4000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doNumberAndBigInt(leftNodeValue_, rightNodeValue_);
                    return bl;
                }
            }
            if ((exclude & 0x100) == 0 && JSGuards.isJavaNumber(leftNodeValue) && JSGuards.isJavaNumber(rightNodeValue)) {
                this.state_0_ = state_0 |= 0x8000;
                lock.unlock();
                hasLock = false;
                boolean rightNodeValue_ = this.doJavaNumber(leftNodeValue, rightNodeValue);
                return rightNodeValue_;
            }
            if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
                this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createHintNumberRightToLeft(this.getOverloadedOperatorName()));
                this.overloaded_toBooleanNode_ = super.insert(JSToBooleanNode.create());
                this.state_0_ = state_0 |= 0x10000;
                lock.unlock();
                hasLock = false;
                boolean rightNodeValue_ = this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_);
                return rightNodeValue_;
            }
            if (!this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                GenericData s17_ = super.insert(new GenericData());
                s17_.toStringOrNumber1_ = s17_.insertAccessor(JSToStringOrNumberNode.create());
                s17_.toPrimitive1_ = s17_.insertAccessor(JSToPrimitiveNode.createHintNumber());
                s17_.toStringOrNumber2_ = s17_.insertAccessor(JSToStringOrNumberNode.create());
                s17_.toPrimitive2_ = s17_.insertAccessor(JSToPrimitiveNode.createHintNumber());
                s17_.greaterThanNode_ = s17_.insertAccessor(JSGreaterThanNode.create());
                VarHandle.storeStoreFence();
                this.generic_cache = s17_;
                this.exclude_ = exclude |= 0x1FF;
                state_0 &= 0xFFFF2B0E;
                this.state_0_ = state_0 |= 0x20000;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doGeneric(leftNodeValue, rightNodeValue, s17_.toStringOrNumber1_, s17_.toPrimitive1_, s17_.toStringOrNumber2_, s17_.toPrimitive2_, s17_.greaterThanNode_);
                return bl;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.leftNode, this.rightNode}, leftNodeValue, rightNodeValue);
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
        if ((state_0 & 0x3FFFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x3FFFF & (state_0 & 0x3FFFF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Node>> cached;
        Object[] data = new Object[19];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[5] = s;
        s = new Object[3];
        s[0] = "doString";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Node>>();
            cached.add(Arrays.asList(this.string_compareNode_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[6] = s;
        s = new Object[3];
        s[0] = "doStringDouble";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : ((exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[7] = s;
        s = new Object[3];
        s[0] = "doDoubleString";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x10) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[8] = s;
        s = new Object[3];
        s[0] = "doStringBigInt";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doBigIntString";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x20) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[11] = s;
        s = new Object[3];
        s[0] = "doBigIntAndInt";
        s[1] = (state_0 & 0x800) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[12] = s;
        s = new Object[3];
        s[0] = "doBigIntAndNumber";
        s[1] = (state_0 & 0x1000) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x40) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[13] = s;
        s = new Object[3];
        s[0] = "doIntAndBigInt";
        s[1] = (state_0 & 0x2000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[14] = s;
        s = new Object[3];
        s[0] = "doNumberAndBigInt";
        s[1] = (state_0 & 0x4000) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x80) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[15] = s;
        s = new Object[3];
        s[0] = "doJavaNumber";
        s[1] = (state_0 & 0x8000) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x100) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[16] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 0x10000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[17] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 0x20000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            GenericData s17_ = this.generic_cache;
            if (s17_ != null) {
                cached.add(Arrays.asList(s17_.toStringOrNumber1_, s17_.toPrimitive1_, s17_.toStringOrNumber2_, s17_.toPrimitive2_, s17_.greaterThanNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[18] = s;
        return Introspection.Provider.create(data);
    }

    public static JSGreaterThanNode create(JavaScriptNode left, JavaScriptNode right) {
        return new JSGreaterThanNodeGen(left, right);
    }

    @GeneratedBy(value=JSGreaterThanNode.class)
    private static final class GenericData
    extends Node {
        @Node.Child
        JSToStringOrNumberNode toStringOrNumber1_;
        @Node.Child
        JSToPrimitiveNode toPrimitive1_;
        @Node.Child
        JSToStringOrNumberNode toStringOrNumber2_;
        @Node.Child
        JSToPrimitiveNode toPrimitive2_;
        @Node.Child
        JSGreaterThanNode greaterThanNode_;

        GenericData() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }
}

