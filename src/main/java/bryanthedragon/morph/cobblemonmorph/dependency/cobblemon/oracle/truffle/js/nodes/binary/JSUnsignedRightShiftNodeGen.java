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
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.binary.JSUnsignedRightShiftNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.runtime.BigInt;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSUnsignedRightShiftNode.class)
public final class JSUnsignedRightShiftNodeGen
extends JSUnsignedRightShiftNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile double_returnType_;
    @Node.Child
    private JSToUInt32Node intDouble_rvalToUint32Node_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile intDouble_returnType_;
    @Node.Child
    private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private GenericData generic_cache;

    private JSUnsignedRightShiftNodeGen(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    @Override
    public Object execute(Object leftNodeValue, Object rightNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x1F) != 0 && rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            if ((state_0 & 7) != 0 && leftNodeValue instanceof Integer) {
                int leftNodeValue_ = (Integer)leftNodeValue;
                if ((state_0 & 1) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue_) && leftNodeValue_ >= 0) {
                    return this.doIntegerFast(leftNodeValue_, rightNodeValue_);
                }
                if ((state_0 & 2) != 0 && leftNodeValue_ >= 0) {
                    return this.doInteger(leftNodeValue_, rightNodeValue_);
                }
                if ((state_0 & 4) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
                    return this.doIntegerNegative(leftNodeValue_, rightNodeValue_);
                }
            }
            if ((state_0 & 0x18) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue)) {
                double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue);
                if ((state_0 & 8) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
                    return this.doDoubleZero(leftNodeValue_, rightNodeValue_);
                }
                if ((state_0 & 0x10) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
                    return this.doDouble(leftNodeValue_, rightNodeValue_, this.double_returnType_);
                }
            }
        }
        if ((state_0 & 0x60) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C000) >>> 14, rightNodeValue)) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C000) >>> 14, rightNodeValue);
            if ((state_0 & 0x20) != 0 && leftNodeValue instanceof Integer) {
                int leftNodeValue_ = (Integer)leftNodeValue;
                return this.doIntDouble(leftNodeValue_, rightNodeValue_, this.intDouble_rvalToUint32Node_, this.intDouble_returnType_);
            }
            if ((state_0 & 0x40) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue)) {
                double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue);
                return this.doDoubleDouble(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x80) != 0 && leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                return this.doBigInt(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x300) != 0) {
            GenericData s9_;
            if ((state_0 & 0x100) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
                return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
            }
            if (!((state_0 & 0x200) == 0 || (s9_ = this.generic_cache) == null || this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue) || JSUnsignedRightShiftNode.isHandled(leftNodeValue, rightNodeValue))) {
                return this.doGeneric(leftNodeValue, rightNodeValue, s9_.lvalToNumericNode_, s9_.rvalToNumericNode_, s9_.innerShiftNode_, s9_.mixedNumericTypes_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x3F8) == 0 && (state_0 & 0x3FF) != 0) {
            return this.execute_int_int0(state_0, frameValue);
        }
        if ((state_0 & 0x3E7) == 0 && (state_0 & 0x3FF) != 0) {
            return this.execute_double_int1(state_0, frameValue);
        }
        if ((state_0 & 0x3DF) == 0 && (state_0 & 0x3FF) != 0) {
            return this.execute_int_double2(state_0, frameValue);
        }
        if ((state_0 & 0x3BF) == 0 && (state_0 & 0x3FF) != 0) {
            return this.execute_double_double3(state_0, frameValue);
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
        if ((state_0 & 1) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue_) && leftNodeValue_ >= 0) {
            return this.doIntegerFast(leftNodeValue_, rightNodeValue_);
        }
        if ((state_0 & 2) != 0 && leftNodeValue_ >= 0) {
            return this.doInteger(leftNodeValue_, rightNodeValue_);
        }
        if ((state_0 & 4) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
            return this.doIntegerNegative(leftNodeValue_, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    private Object execute_double_int1(int state_0, VirtualFrame frameValue) {
        int rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x3800) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue__);
            }
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
            return this.executeAndSpecialize((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult());
        }
        if ((state_0 & 8) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
            return this.doDoubleZero(leftNodeValue_, rightNodeValue_);
        }
        if ((state_0 & 0x10) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
            return this.doDouble(leftNodeValue_, rightNodeValue_, this.double_returnType_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), rightNodeValue_);
    }

    private Object execute_int_double2(int state_0, VirtualFrame frameValue) {
        double rightNodeValue_;
        int leftNodeValue_;
        try {
            leftNodeValue_ = this.leftNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return this.executeAndSpecialize(ex.getResult(), rightNodeValue);
        }
        long rightNodeValue_long = 0L;
        int rightNodeValue_int = 0;
        try {
            if ((state_0 & 0x38000) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x34000) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x1C000) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C000) >>> 14, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 0x20) != 0);
        return this.doIntDouble(leftNodeValue_, rightNodeValue_, this.intDouble_rvalToUint32Node_, this.intDouble_returnType_);
    }

    private Object execute_double_double3(int state_0, VirtualFrame frameValue) {
        double rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x3800) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue__);
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
            if ((state_0 & 0x38000) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x34000) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x1C000) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C000) >>> 14, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult());
        }
        assert ((state_0 & 0x40) != 0);
        return this.doDoubleDouble(leftNodeValue_, rightNodeValue_);
    }

    private Object execute_generic4(int state_0, VirtualFrame frameValue) {
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 0x1F) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;
            if ((state_0 & 7) != 0 && leftNodeValue_ instanceof Integer) {
                int leftNodeValue__ = (Integer)leftNodeValue_;
                if ((state_0 & 1) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue__) && leftNodeValue__ >= 0) {
                    return this.doIntegerFast(leftNodeValue__, rightNodeValue__);
                }
                if ((state_0 & 2) != 0 && leftNodeValue__ >= 0) {
                    return this.doInteger(leftNodeValue__, rightNodeValue__);
                }
                if ((state_0 & 4) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue__)) {
                    return this.doIntegerNegative(leftNodeValue__, rightNodeValue__);
                }
            }
            if ((state_0 & 0x18) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue_)) {
                double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue_);
                if ((state_0 & 8) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue__)) {
                    return this.doDoubleZero(leftNodeValue__, rightNodeValue__);
                }
                if ((state_0 & 0x10) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue__)) {
                    return this.doDouble(leftNodeValue__, rightNodeValue__, this.double_returnType_);
                }
            }
        }
        if ((state_0 & 0x60) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C000) >>> 14, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C000) >>> 14, rightNodeValue_);
            if ((state_0 & 0x20) != 0 && leftNodeValue_ instanceof Integer) {
                int leftNodeValue__ = (Integer)leftNodeValue_;
                return this.doIntDouble(leftNodeValue__, rightNodeValue__, this.intDouble_rvalToUint32Node_, this.intDouble_returnType_);
            }
            if ((state_0 & 0x40) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue_)) {
                double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue_);
                return this.doDoubleDouble(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x80) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            if (rightNodeValue_ instanceof BigInt) {
                BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
                return this.doBigInt(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x300) != 0) {
            GenericData s9_;
            if ((state_0 & 0x100) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
                return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
            }
            if (!((state_0 & 0x200) == 0 || (s9_ = this.generic_cache) == null || this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_) || JSUnsignedRightShiftNode.isHandled(leftNodeValue_, rightNodeValue_))) {
                return this.doGeneric(leftNodeValue_, rightNodeValue_, s9_.lvalToNumericNode_, s9_.rvalToNumericNode_, s9_.innerShiftNode_, s9_.mixedNumericTypes_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0x3B0) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        if ((state_0 & 0x40) == 0 && (state_0 & 0x48) != 0) {
            return this.executeDouble_double_int5(state_0, frameValue);
        }
        if ((state_0 & 8) == 0 && (state_0 & 0x48) != 0) {
            return this.executeDouble_double_double6(state_0, frameValue);
        }
        return this.executeDouble_generic7(state_0, frameValue);
    }

    private double executeDouble_double_int5(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        int rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x3800) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return JSTypesGen.expectDouble(this.executeAndSpecialize(ex.getResult(), rightNodeValue));
        }
        try {
            rightNodeValue_ = this.rightNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult()));
        }
        assert ((state_0 & 8) != 0);
        if (JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
            return this.doDoubleZero(leftNodeValue_, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), rightNodeValue_));
    }

    private double executeDouble_double_double6(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        double rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x3800) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return JSTypesGen.expectDouble(this.executeAndSpecialize(ex.getResult(), rightNodeValue));
        }
        long rightNodeValue_long = 0L;
        int rightNodeValue_int = 0;
        try {
            if ((state_0 & 0x38000) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x34000) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x1C000) == 0 && (state_0 & 0x3FF) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C000) >>> 14, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult()));
        }
        assert ((state_0 & 0x40) != 0);
        return this.doDoubleDouble(leftNodeValue_, rightNodeValue_);
    }

    private double executeDouble_generic7(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        int rightNodeValue__;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x3800) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = this.rightNode.execute(frameValue);
            return JSTypesGen.expectDouble(this.executeAndSpecialize(ex.getResult(), rightNodeValue));
        }
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 8) != 0 && rightNodeValue_ instanceof Integer && JSUnsignedRightShiftNode.rvalZero(rightNodeValue__ = ((Integer)rightNodeValue_).intValue())) {
            return this.doDoubleZero(leftNodeValue_, rightNodeValue__);
        }
        if ((state_0 & 0x40) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C000) >>> 14, rightNodeValue_)) {
            double rightNodeValue__2 = JSTypesGen.asImplicitDouble((state_0 & 0x3C000) >>> 14, rightNodeValue_);
            return this.doDoubleDouble(leftNodeValue_, rightNodeValue__2);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), rightNodeValue_));
    }

    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int rightNodeValue_;
        int leftNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x3B0) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
        }
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
        if ((state_0 & 1) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue_) && leftNodeValue_ >= 0) {
            return this.doIntegerFast(leftNodeValue_, rightNodeValue_);
        }
        if ((state_0 & 2) != 0 && leftNodeValue_ >= 0) {
            return this.doInteger(leftNodeValue_, rightNodeValue_);
        }
        if ((state_0 & 4) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
            return this.doIntegerNegative(leftNodeValue_, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x3F8) == 0 && (state_0 & 0x3FF) != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0x3B7) == 0 && (state_0 & 0x3FF) != 0) {
                this.executeDouble(frameValue);
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
            int doubleCast1;
            int state_0 = this.state_0_;
            if (rightNodeValue instanceof Integer) {
                int doubleCast0;
                int rightNodeValue_ = (Integer)rightNodeValue;
                if (leftNodeValue instanceof Integer) {
                    int leftNodeValue_ = (Integer)leftNodeValue;
                    if (JSUnsignedRightShiftNode.rvalZero(rightNodeValue_) && leftNodeValue_ >= 0) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        Integer n = this.doIntegerFast(leftNodeValue_, rightNodeValue_);
                        return n;
                    }
                    if (leftNodeValue_ >= 0) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Integer n = this.doInteger(leftNodeValue_, rightNodeValue_);
                        return n;
                    }
                    if (!JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        Integer n = this.doIntegerNegative(leftNodeValue_, rightNodeValue_);
                        return n;
                    }
                }
                if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
                    double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
                    if (JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
                        state_0 |= doubleCast0 << 10;
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        Double d = this.doDoubleZero(leftNodeValue_, rightNodeValue_);
                        return d;
                    }
                    if (!JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
                        this.double_returnType_ = ConditionProfile.createBinaryProfile();
                        state_0 |= doubleCast0 << 10;
                        this.state_0_ = state_0 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        Number number = this.doDouble(leftNodeValue_, rightNodeValue_, this.double_returnType_);
                        return number;
                    }
                }
            }
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
                double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                if (leftNodeValue instanceof Integer) {
                    int leftNodeValue_ = (Integer)leftNodeValue;
                    this.intDouble_rvalToUint32Node_ = super.insert(JSToUInt32Node.create());
                    this.intDouble_returnType_ = ConditionProfile.createBinaryProfile();
                    state_0 |= doubleCast1 << 14;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    Number number = this.doIntDouble(leftNodeValue_, rightNodeValue_, this.intDouble_rvalToUint32Node_, this.intDouble_returnType_);
                    return number;
                }
                int doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue);
                if (doubleCast0 != 0) {
                    double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
                    state_0 |= doubleCast0 << 10;
                    state_0 |= doubleCast1 << 14;
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.doDoubleDouble(leftNodeValue_, rightNodeValue_);
                    return d;
                }
            }
            if (leftNodeValue instanceof BigInt) {
                BigInt leftNodeValue_ = (BigInt)leftNodeValue;
                if (rightNodeValue instanceof BigInt) {
                    BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    Number number = this.doBigInt(leftNodeValue_, rightNodeValue_);
                    return number;
                }
            }
            if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
                this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                Object leftNodeValue_ = this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
                return leftNodeValue_;
            }
            if (!(this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue) || JSUnsignedRightShiftNode.isHandled(leftNodeValue, rightNodeValue))) {
                GenericData s9_ = super.insert(new GenericData());
                s9_.lvalToNumericNode_ = s9_.insertAccessor(JSToNumericNode.create());
                s9_.rvalToNumericNode_ = s9_.insertAccessor(JSToNumericNode.create());
                s9_.innerShiftNode_ = s9_.insertAccessor(JSUnsignedRightShiftNode.create());
                s9_.mixedNumericTypes_ = BranchProfile.create();
                VarHandle.storeStoreFence();
                this.generic_cache = s9_;
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                Number number = this.doGeneric(leftNodeValue, rightNodeValue, s9_.lvalToNumericNode_, s9_.rvalToNumericNode_, s9_.innerShiftNode_, s9_.mixedNumericTypes_);
                return number;
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
        if ((state_0 & 0x3FF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x3FF & (state_0 & 0x3FF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[11];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doIntegerFast";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doIntegerNegative";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doDoubleZero";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doDouble";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.double_returnType_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        s = new Object[3];
        s[0] = "doIntDouble";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.intDouble_rvalToUint32Node_, this.intDouble_returnType_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[6] = s;
        s = new Object[3];
        s[0] = "doDoubleDouble";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            GenericData s9_ = this.generic_cache;
            if (s9_ != null) {
                cached.add(Arrays.asList(s9_.lvalToNumericNode_, s9_.rvalToNumericNode_, s9_.innerShiftNode_, s9_.mixedNumericTypes_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[10] = s;
        return Introspection.Provider.create(data);
    }

    public static JSUnsignedRightShiftNode create(JavaScriptNode left, JavaScriptNode right) {
        return new JSUnsignedRightShiftNodeGen(left, right);
    }

    @GeneratedBy(value=JSUnsignedRightShiftNode.class)
    private static final class GenericData
    extends Node {
        @Node.Child
        JSToNumericNode lvalToNumericNode_;
        @Node.Child
        JSToNumericNode rvalToNumericNode_;
        @Node.Child
        JSUnsignedRightShiftNode innerShiftNode_;
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

