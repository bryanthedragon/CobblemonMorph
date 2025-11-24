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
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSBitwiseAndNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSBitwiseAndNode.class)
public final class JSBitwiseAndNodeGen
extends JSBitwiseAndNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSToInt32Node double_leftInt32_;
    @Node.Child
    private JSToInt32Node double_rightInt32_;
    @Node.Child
    private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private GenericData generic_cache;

    private JSBitwiseAndNodeGen(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    @Override
    public Object executeObject(Object leftNodeValue, Object rightNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0 && rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            if ((state_0 & 1) != 0 && leftNodeValue instanceof Integer) {
                int leftNodeValue_ = (Integer)leftNodeValue;
                return this.doInteger(leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 2) != 0 && leftNodeValue instanceof SafeInteger) {
                SafeInteger leftNodeValue_ = (SafeInteger)leftNodeValue;
                return this.doSafeIntegerInt(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0xC) != 0 && rightNodeValue instanceof SafeInteger) {
            SafeInteger rightNodeValue_ = (SafeInteger)rightNodeValue;
            if ((state_0 & 4) != 0 && leftNodeValue instanceof Integer) {
                int leftNodeValue_ = (Integer)leftNodeValue;
                return this.doIntSafeInteger(leftNodeValue_, rightNodeValue_);
            }
            if ((state_0 & 8) != 0 && leftNodeValue instanceof SafeInteger) {
                SafeInteger leftNodeValue_ = (SafeInteger)leftNodeValue;
                return this.doSafeInteger(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue)) {
            double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue);
            if (JSTypesGen.isImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue)) {
                double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue);
                return this.doDouble(leftNodeValue_, rightNodeValue_, this.double_leftInt32_, this.double_rightInt32_);
            }
        }
        if ((state_0 & 0x20) != 0 && leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                return this.doBigInt(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0xC0) != 0) {
            GenericData s7_;
            if ((state_0 & 0x40) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
                return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
            }
            if ((state_0 & 0x80) != 0 && (s7_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                return this.doGeneric(leftNodeValue, rightNodeValue, s7_.leftNumeric_, s7_.rightNumeric_, s7_.and_, s7_.mixedNumericTypes_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0xFE) == 0 && (state_0 & 0xFF) != 0) {
            return this.execute_int_int0(state_0, frameValue);
        }
        if ((state_0 & 0xEF) == 0 && (state_0 & 0xFF) != 0) {
            return this.execute_double_double1(state_0, frameValue);
        }
        if ((state_0 & 0xFD) == 0 && (state_0 & 0xFF) != 0) {
            return this.execute_int2(state_0, frameValue);
        }
        if ((state_0 & 0xFB) == 0 && (state_0 & 0xFF) != 0) {
            return this.execute_int3(state_0, frameValue);
        }
        return this.execute_generic4(state_0, frameValue);
    }

    private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
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
        return this.doInteger(leftNodeValue_, rightNodeValue_);
    }

    private Object execute_double_double1(int state_0, VirtualFrame frameValue) {
        double rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0xE00) == 0 && (state_0 & 0xFF) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0xD00) == 0 && (state_0 & 0xFF) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x700) == 0 && (state_0 & 0xFF) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue__);
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
            if ((state_0 & 0xE000) == 0 && (state_0 & 0xFF) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0xD000) == 0 && (state_0 & 0xFF) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x7000) == 0 && (state_0 & 0xFF) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0xD00) == 0 && (state_0 & 0xFF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x700) == 0 && (state_0 & 0xFF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult());
        }
        assert ((state_0 & 0x10) != 0);
        return this.doDouble(leftNodeValue_, rightNodeValue_, this.double_leftInt32_, this.double_rightInt32_);
    }

    private Object execute_int2(int state_0, VirtualFrame frameValue) {
        int rightNodeValue_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        try {
            rightNodeValue_ = this.rightNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 2) != 0);
        if (leftNodeValue_ instanceof SafeInteger) {
            SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
            return this.doSafeIntegerInt(leftNodeValue__, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    private Object execute_int3(int state_0, VirtualFrame frameValue) {
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
        assert ((state_0 & 4) != 0);
        if (rightNodeValue_ instanceof SafeInteger) {
            SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;
            return this.doIntSafeInteger(leftNodeValue_, rightNodeValue__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    private Object execute_generic4(int state_0, VirtualFrame frameValue) {
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 3) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;
            if ((state_0 & 1) != 0 && leftNodeValue_ instanceof Integer) {
                int leftNodeValue__ = (Integer)leftNodeValue_;
                return this.doInteger(leftNodeValue__, rightNodeValue__);
            }
            if ((state_0 & 2) != 0 && leftNodeValue_ instanceof SafeInteger) {
                SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
                return this.doSafeIntegerInt(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0xC) != 0 && rightNodeValue_ instanceof SafeInteger) {
            SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;
            if ((state_0 & 4) != 0 && leftNodeValue_ instanceof Integer) {
                int leftNodeValue__ = (Integer)leftNodeValue_;
                return this.doIntSafeInteger(leftNodeValue__, rightNodeValue__);
            }
            if ((state_0 & 8) != 0 && leftNodeValue_ instanceof SafeInteger) {
                SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
                return this.doSafeInteger(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue_)) {
            double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue_);
            if (JSTypesGen.isImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue_)) {
                double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue_);
                return this.doDouble(leftNodeValue__, rightNodeValue__, this.double_leftInt32_, this.double_rightInt32_);
            }
        }
        if ((state_0 & 0x20) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            if (rightNodeValue_ instanceof BigInt) {
                BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
                return this.doBigInt(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0xC0) != 0) {
            GenericData s7_;
            if ((state_0 & 0x40) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
                return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
            }
            if ((state_0 & 0x80) != 0 && (s7_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
                return this.doGeneric(leftNodeValue_, rightNodeValue_, s7_.leftNumeric_, s7_.rightNumeric_, s7_.and_, s7_.mixedNumericTypes_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0xC0) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
        }
        if ((state_0 & 0x1E) == 0 && (state_0 & 0x1F) != 0) {
            return this.executeInt_int_int5(state_0, frameValue);
        }
        if ((state_0 & 0xF) == 0 && (state_0 & 0x1F) != 0) {
            return this.executeInt_double_double6(state_0, frameValue);
        }
        if ((state_0 & 0x1D) == 0 && (state_0 & 0x1F) != 0) {
            return this.executeInt_int7(state_0, frameValue);
        }
        if ((state_0 & 0x1B) == 0 && (state_0 & 0x1F) != 0) {
            return this.executeInt_int8(state_0, frameValue);
        }
        return this.executeInt_generic9(state_0, frameValue);
    }

    private int executeInt_int_int5(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        int rightNodeValue_;
        int leftNodeValue_;
        try {
            leftNodeValue_ = this.leftNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult(), rightNodeValue));
        }
        try {
            rightNodeValue_ = this.rightNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, ex.getResult()));
        }
        assert ((state_0 & 1) != 0);
        return this.doInteger(leftNodeValue_, rightNodeValue_);
    }

    private int executeInt_double_double6(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        double rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0xE00) == 0 && (state_0 & 0xFF) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0xD00) == 0 && (state_0 & 0xFF) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x700) == 0 && (state_0 & 0xFF) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult(), rightNodeValue));
        }
        long rightNodeValue_long = 0L;
        int rightNodeValue_int = 0;
        try {
            if ((state_0 & 0xE000) == 0 && (state_0 & 0xFF) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0xD000) == 0 && (state_0 & 0xFF) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x7000) == 0 && (state_0 & 0xFF) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize((state_0 & 0xD00) == 0 && (state_0 & 0xFF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x700) == 0 && (state_0 & 0xFF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult()));
        }
        assert ((state_0 & 0x10) != 0);
        return this.doDouble(leftNodeValue_, rightNodeValue_, this.double_leftInt32_, this.double_rightInt32_);
    }

    private int executeInt_int7(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        int rightNodeValue_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        try {
            rightNodeValue_ = this.rightNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, ex.getResult()));
        }
        assert ((state_0 & 2) != 0);
        if (leftNodeValue_ instanceof SafeInteger) {
            SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
            return this.doSafeIntegerInt(leftNodeValue__, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
    }

    private int executeInt_int8(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        int leftNodeValue_;
        try {
            leftNodeValue_ = this.leftNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult(), rightNodeValue));
        }
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        assert ((state_0 & 4) != 0);
        if (rightNodeValue_ instanceof SafeInteger) {
            SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;
            return this.doIntSafeInteger(leftNodeValue_, rightNodeValue__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
    }

    private int executeInt_generic9(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 3) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;
            if ((state_0 & 1) != 0 && leftNodeValue_ instanceof Integer) {
                int leftNodeValue__ = (Integer)leftNodeValue_;
                return this.doInteger(leftNodeValue__, rightNodeValue__);
            }
            if ((state_0 & 2) != 0 && leftNodeValue_ instanceof SafeInteger) {
                SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
                return this.doSafeIntegerInt(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0xC) != 0 && rightNodeValue_ instanceof SafeInteger) {
            SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;
            if ((state_0 & 4) != 0 && leftNodeValue_ instanceof Integer) {
                int leftNodeValue__ = (Integer)leftNodeValue_;
                return this.doIntSafeInteger(leftNodeValue__, rightNodeValue__);
            }
            if ((state_0 & 8) != 0 && leftNodeValue_ instanceof SafeInteger) {
                SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
                return this.doSafeInteger(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue_)) {
            double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, leftNodeValue_);
            if (JSTypesGen.isImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue_)) {
                double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0xF000) >>> 12, rightNodeValue_);
                return this.doDouble(leftNodeValue__, rightNodeValue__, this.double_leftInt32_, this.double_rightInt32_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0xE0) == 0 && (state_0 & 0xFF) != 0) {
                this.executeInt(frameValue);
                return;
            }
            this.execute(frameValue);
            return;
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return;
        }
    }

    private Object executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int doubleCast0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (rightNodeValue instanceof Integer) {
                int rightNodeValue_ = (Integer)rightNodeValue;
                if ((exclude & 1) == 0 && leftNodeValue instanceof Integer) {
                    int leftNodeValue_ = (Integer)leftNodeValue;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doInteger(leftNodeValue_, rightNodeValue_);
                    return n;
                }
                if ((exclude & 2) == 0 && leftNodeValue instanceof SafeInteger) {
                    SafeInteger leftNodeValue_ = (SafeInteger)leftNodeValue;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doSafeIntegerInt(leftNodeValue_, rightNodeValue_);
                    return n;
                }
            }
            if (rightNodeValue instanceof SafeInteger) {
                SafeInteger rightNodeValue_ = (SafeInteger)rightNodeValue;
                if ((exclude & 4) == 0 && leftNodeValue instanceof Integer) {
                    int leftNodeValue_ = (Integer)leftNodeValue;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doIntSafeInteger(leftNodeValue_, rightNodeValue_);
                    return n;
                }
                if ((exclude & 8) == 0 && leftNodeValue instanceof SafeInteger) {
                    SafeInteger leftNodeValue_ = (SafeInteger)leftNodeValue;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doSafeInteger(leftNodeValue_, rightNodeValue_);
                    return n;
                }
            }
            if ((exclude & 0x10) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
                double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
                int doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue);
                if (doubleCast1 != 0) {
                    double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                    this.double_leftInt32_ = super.insert(JSToInt32Node.create());
                    this.double_rightInt32_ = super.insert(JSToInt32Node.create());
                    state_0 |= doubleCast0 << 8;
                    state_0 |= doubleCast1 << 12;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doDouble(leftNodeValue_, rightNodeValue_, this.double_leftInt32_, this.double_rightInt32_);
                    return n;
                }
            }
            if ((exclude & 0x20) == 0 && leftNodeValue instanceof BigInt) {
                BigInt leftNodeValue_ = (BigInt)leftNodeValue;
                if (rightNodeValue instanceof BigInt) {
                    BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    BigInt bigInt = this.doBigInt(leftNodeValue_, rightNodeValue_);
                    return bigInt;
                }
            }
            if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
                this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                Object leftNodeValue_ = this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
                return leftNodeValue_;
            }
            if (!this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                GenericData s7_ = super.insert(new GenericData());
                s7_.leftNumeric_ = s7_.insertAccessor(JSToNumericNode.create());
                s7_.rightNumeric_ = s7_.insertAccessor(JSToNumericNode.create());
                s7_.and_ = s7_.insertAccessor(JSBitwiseAndNode.createInner());
                s7_.mixedNumericTypes_ = BranchProfile.create();
                VarHandle.storeStoreFence();
                this.generic_cache = s7_;
                this.exclude_ = exclude |= 0x3F;
                state_0 &= 0xFFFFFFC0;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                Object object = this.doGeneric(leftNodeValue, rightNodeValue, s7_.leftNumeric_, s7_.rightNumeric_, s7_.and_, s7_.mixedNumericTypes_);
                return object;
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
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[9];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "doSafeIntegerInt";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[2] = s;
        s = new Object[3];
        s[0] = "doIntSafeInteger";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : ((exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[3] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : ((exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[4] = s;
        s = new Object[3];
        s[0] = "doDouble";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.double_leftInt32_, this.double_rightInt32_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 0x10) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[5] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x20) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[6] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 0x40) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[7] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 0x80) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            GenericData s7_ = this.generic_cache;
            if (s7_ != null) {
                cached.add(Arrays.asList(s7_.leftNumeric_, s7_.rightNumeric_, s7_.and_, s7_.mixedNumericTypes_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[8] = s;
        return Introspection.Provider.create(data);
    }

    public static JSBitwiseAndNode create(JavaScriptNode left, JavaScriptNode right) {
        return new JSBitwiseAndNodeGen(left, right);
    }

    @GeneratedBy(value=JSBitwiseAndNode.class)
    private static final class GenericData
    extends Node {
        @Node.Child
        JSToNumericNode leftNumeric_;
        @Node.Child
        JSToNumericNode rightNumeric_;
        @Node.Child
        JSBitwiseAndNode and_;
        @CompilerDirectives.CompilationFinal
        BranchProfile mixedNumericTypes_;

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

