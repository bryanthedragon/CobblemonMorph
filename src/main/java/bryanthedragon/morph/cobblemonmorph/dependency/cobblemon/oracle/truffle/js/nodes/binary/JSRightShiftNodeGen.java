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
import com.oracle.truffle.js.nodes.binary.JSBinaryNode;
import com.oracle.truffle.js.nodes.binary.JSLeftShiftNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.binary.JSRightShiftNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.runtime.BigInt;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSRightShiftNode.class)
public final class JSRightShiftNodeGen
extends JSRightShiftNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSLeftShiftNode bigInt_leftShift_;
    @Node.Child
    private DoubleData double_cache;
    @Node.Child
    private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private GenericData generic_cache;

    private JSRightShiftNodeGen(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    @Override
    public Object execute(Object leftNodeValue, Object rightNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            if (rightNodeValue instanceof Integer) {
                int rightNodeValue_ = (Integer)rightNodeValue;
                return this.doInteger(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 2) != 0 && leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                return this.doBigInt(leftNodeValue_, rightNodeValue_, this.bigInt_leftShift_);
            }
        }
        if ((state_0 & 0xC) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0) >>> 6, rightNodeValue)) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0) >>> 6, rightNodeValue);
            if ((state_0 & 4) != 0 && leftNodeValue instanceof Integer) {
                int leftNodeValue_ = (Integer)leftNodeValue;
                if (!JSBinaryNode.largerThan2e32(rightNodeValue_)) {
                    return this.doIntDouble(leftNodeValue_, rightNodeValue_);
                }
            }
            if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue)) {
                double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue);
                DoubleData s3_ = this.double_cache;
                if (s3_ != null) {
                    return this.doDouble(leftNodeValue_, rightNodeValue_, s3_.rightShift_, s3_.leftInt32_, s3_.rightUInt32_);
                }
            }
        }
        if ((state_0 & 0x30) != 0) {
            GenericData s5_;
            if ((state_0 & 0x10) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
                return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
            }
            if ((state_0 & 0x20) != 0 && (s5_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                return this.doGeneric(leftNodeValue, rightNodeValue, s5_.rightShift_, s5_.leftToNumeric_, s5_.rightToNumeric_, s5_.mixedNumericTypes_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x3E) == 0 && (state_0 & 0x3F) != 0) {
            return this.execute_int_int0(state_0, frameValue);
        }
        if ((state_0 & 0x3B) == 0 && (state_0 & 0x3F) != 0) {
            return this.execute_int_double1(state_0, frameValue);
        }
        if ((state_0 & 0x37) == 0 && (state_0 & 0x3F) != 0) {
            return this.execute_double_double2(state_0, frameValue);
        }
        return this.execute_generic3(state_0, frameValue);
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

    private Object execute_int_double1(int state_0, VirtualFrame frameValue) {
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
            if ((state_0 & 0x380) == 0 && (state_0 & 0x3F) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x340) == 0 && (state_0 & 0x3F) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x1C0) == 0 && (state_0 & 0x3F) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C0) >>> 6, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(leftNodeValue_, ex.getResult());
        }
        assert ((state_0 & 4) != 0);
        if (!JSBinaryNode.largerThan2e32(rightNodeValue_)) {
            return this.doIntDouble(leftNodeValue_, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, (state_0 & 0x340) == 0 && (state_0 & 0x3F) != 0 ? (Number)rightNodeValue_int : (Number)((state_0 & 0x1C0) == 0 && (state_0 & 0x3F) != 0 ? (Number)rightNodeValue_long : (Number)rightNodeValue_));
    }

    private Object execute_double_double2(int state_0, VirtualFrame frameValue) {
        double rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x3800) == 0 && (state_0 & 0x3F) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x3400) == 0 && (state_0 & 0x3F) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x1C00) == 0 && (state_0 & 0x3F) != 0) {
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
            if ((state_0 & 0x380) == 0 && (state_0 & 0x3F) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x340) == 0 && (state_0 & 0x3F) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x1C0) == 0 && (state_0 & 0x3F) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C0) >>> 6, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0x3400) == 0 && (state_0 & 0x3F) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x1C00) == 0 && (state_0 & 0x3F) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult());
        }
        assert ((state_0 & 8) != 0);
        DoubleData s3_ = this.double_cache;
        if (s3_ != null) {
            return this.doDouble(leftNodeValue_, rightNodeValue_, s3_.rightShift_, s3_.leftInt32_, s3_.rightUInt32_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize((state_0 & 0x3400) == 0 && (state_0 & 0x3F) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x1C00) == 0 && (state_0 & 0x3F) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), (state_0 & 0x340) == 0 && (state_0 & 0x3F) != 0 ? (Number)rightNodeValue_int : (Number)((state_0 & 0x1C0) == 0 && (state_0 & 0x3F) != 0 ? (Number)rightNodeValue_long : (Number)rightNodeValue_));
    }

    private Object execute_generic3(int state_0, VirtualFrame frameValue) {
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 1) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__ = (Integer)leftNodeValue_;
            if (rightNodeValue_ instanceof Integer) {
                int rightNodeValue__ = (Integer)rightNodeValue_;
                return this.doInteger(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 2) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            if (rightNodeValue_ instanceof BigInt) {
                BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
                return this.doBigInt(leftNodeValue__, rightNodeValue__, this.bigInt_leftShift_);
            }
        }
        if ((state_0 & 0xC) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0) >>> 6, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0) >>> 6, rightNodeValue_);
            if ((state_0 & 4) != 0 && leftNodeValue_ instanceof Integer) {
                int leftNodeValue__ = (Integer)leftNodeValue_;
                if (!JSBinaryNode.largerThan2e32(rightNodeValue__)) {
                    return this.doIntDouble(leftNodeValue__, rightNodeValue__);
                }
            }
            if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue_)) {
                double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, leftNodeValue_);
                DoubleData s3_ = this.double_cache;
                if (s3_ != null) {
                    return this.doDouble(leftNodeValue__, rightNodeValue__, s3_.rightShift_, s3_.leftInt32_, s3_.rightUInt32_);
                }
            }
        }
        if ((state_0 & 0x30) != 0) {
            GenericData s5_;
            if ((state_0 & 0x10) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
                return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
            }
            if ((state_0 & 0x20) != 0 && (s5_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
                return this.doGeneric(leftNodeValue_, rightNodeValue_, s5_.rightShift_, s5_.leftToNumeric_, s5_.rightToNumeric_, s5_.mixedNumericTypes_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int leftNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x38) != 0) {
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
        if ((state_0 & 4) == 0 && (state_0 & 5) != 0) {
            return this.executeInt_int4(state_0, frameValue, leftNodeValue_);
        }
        if ((state_0 & 1) == 0 && (state_0 & 5) != 0) {
            return this.executeInt_double5(state_0, frameValue, leftNodeValue_);
        }
        return this.executeInt_generic6(state_0, frameValue, leftNodeValue_);
    }

    private int executeInt_int4(int state_0, VirtualFrame frameValue, int leftNodeValue_) throws UnexpectedResultException {
        int rightNodeValue_;
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

    private int executeInt_double5(int state_0, VirtualFrame frameValue, int leftNodeValue_) throws UnexpectedResultException {
        double rightNodeValue_;
        long rightNodeValue_long = 0L;
        int rightNodeValue_int = 0;
        try {
            if ((state_0 & 0x380) == 0 && (state_0 & 0x3F) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x340) == 0 && (state_0 & 0x3F) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x1C0) == 0 && (state_0 & 0x3F) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C0) >>> 6, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, ex.getResult()));
        }
        assert ((state_0 & 4) != 0);
        if (!JSBinaryNode.largerThan2e32(rightNodeValue_)) {
            return this.doIntDouble(leftNodeValue_, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, (state_0 & 0x340) == 0 && (state_0 & 0x3F) != 0 ? (Number)rightNodeValue_int : (Number)((state_0 & 0x1C0) == 0 && (state_0 & 0x3F) != 0 ? (Number)rightNodeValue_long : (Number)rightNodeValue_)));
    }

    private int executeInt_generic6(int state_0, VirtualFrame frameValue, int leftNodeValue_) throws UnexpectedResultException {
        double rightNodeValue__;
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 1) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__2 = (Integer)rightNodeValue_;
            return this.doInteger(leftNodeValue_, rightNodeValue__2);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0) >>> 6, rightNodeValue_) && !JSBinaryNode.largerThan2e32(rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C0) >>> 6, rightNodeValue_))) {
            return this.doIntDouble(leftNodeValue_, rightNodeValue__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x3A) == 0 && (state_0 & 0x3F) != 0) {
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
            int doubleCast12;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if ((exclude & 1) == 0 && leftNodeValue instanceof Integer) {
                int leftNodeValue_ = (Integer)leftNodeValue;
                if (rightNodeValue instanceof Integer) {
                    int rightNodeValue_ = (Integer)rightNodeValue;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doInteger(leftNodeValue_, rightNodeValue_);
                    return n;
                }
            }
            if ((exclude & 2) == 0 && leftNodeValue instanceof BigInt) {
                BigInt leftNodeValue_ = (BigInt)leftNodeValue;
                if (rightNodeValue instanceof BigInt) {
                    BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                    this.bigInt_leftShift_ = super.insert(JSLeftShiftNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    BigInt bigInt = this.doBigInt(leftNodeValue_, rightNodeValue_, this.bigInt_leftShift_);
                    return bigInt;
                }
            }
            if ((doubleCast12 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
                int doubleCast0;
                double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast12, rightNodeValue);
                if ((exclude & 4) == 0 && leftNodeValue instanceof Integer) {
                    int leftNodeValue_ = (Integer)leftNodeValue;
                    if (!JSBinaryNode.largerThan2e32(rightNodeValue_)) {
                        state_0 |= doubleCast12 << 6;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        Integer n = this.doIntDouble(leftNodeValue_, rightNodeValue_);
                        return n;
                    }
                }
                if ((exclude & 8) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
                    double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
                    DoubleData s3_ = super.insert(new DoubleData());
                    s3_.rightShift_ = s3_.insertAccessor(JSRightShiftNode.create());
                    s3_.leftInt32_ = s3_.insertAccessor(JSToInt32Node.create());
                    s3_.rightUInt32_ = s3_.insertAccessor(JSToUInt32Node.create());
                    VarHandle.storeStoreFence();
                    this.double_cache = s3_;
                    state_0 |= doubleCast0 << 10;
                    state_0 |= doubleCast12 << 6;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doDouble(leftNodeValue_, rightNodeValue_, s3_.rightShift_, s3_.leftInt32_, s3_.rightUInt32_);
                    return object;
                }
            }
            if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
                this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Object doubleCast12 = this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
                return doubleCast12;
            }
            if (!this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                GenericData s5_ = super.insert(new GenericData());
                s5_.rightShift_ = s5_.insertAccessor(JSRightShiftNode.create());
                s5_.leftToNumeric_ = s5_.insertAccessor(JSToNumericNode.create());
                s5_.rightToNumeric_ = s5_.insertAccessor(JSToNumericNode.create());
                s5_.mixedNumericTypes_ = BranchProfile.create();
                VarHandle.storeStoreFence();
                this.generic_cache = s5_;
                this.exclude_ = exclude |= 0xF;
                this.double_cache = null;
                state_0 &= 0xFFFFFFF0;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                Object object = this.doGeneric(leftNodeValue, rightNodeValue, s5_.rightShift_, s5_.leftToNumeric_, s5_.rightToNumeric_, s5_.mixedNumericTypes_);
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
        if ((state_0 & 0x3F) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x3F & (state_0 & 0x3F) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[7];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.bigInt_leftShift_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doIntDouble";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : ((exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[3] = s;
        s = new Object[3];
        s[0] = "doDouble";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            DoubleData s3_ = this.double_cache;
            if (s3_ != null) {
                cached.add(Arrays.asList(s3_.rightShift_, s3_.leftInt32_, s3_.rightUInt32_));
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            GenericData s5_ = this.generic_cache;
            if (s5_ != null) {
                cached.add(Arrays.asList(s5_.rightShift_, s5_.leftToNumeric_, s5_.rightToNumeric_, s5_.mixedNumericTypes_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[6] = s;
        return Introspection.Provider.create(data);
    }

    public static JSRightShiftNode create(JavaScriptNode left, JavaScriptNode right) {
        return new JSRightShiftNodeGen(left, right);
    }

    @GeneratedBy(value=JSRightShiftNode.class)
    private static final class GenericData
    extends Node {
        @Node.Child
        JSRightShiftNode rightShift_;
        @Node.Child
        JSToNumericNode leftToNumeric_;
        @Node.Child
        JSToNumericNode rightToNumeric_;
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

    @GeneratedBy(value=JSRightShiftNode.class)
    private static final class DoubleData
    extends Node {
        @Node.Child
        JSRightShiftNode rightShift_;
        @Node.Child
        JSToInt32Node leftInt32_;
        @Node.Child
        JSToUInt32Node rightUInt32_;

        DoubleData() {
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

