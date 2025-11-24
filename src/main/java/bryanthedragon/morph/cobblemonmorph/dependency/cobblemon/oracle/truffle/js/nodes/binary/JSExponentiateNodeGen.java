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
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSExponentiateNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSExponentiateNode.class)
public final class JSExponentiateNodeGen
extends JSExponentiateNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private GenericData generic_cache;

    private JSExponentiateNodeGen(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    @Override
    public Object execute(Object leftNodeValue, Object rightNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x780) >>> 7, leftNodeValue)) {
            double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x780) >>> 7, leftNodeValue);
            if (JSTypesGen.isImplicitDouble((state_0 & 0x7800) >>> 11, rightNodeValue)) {
                double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x7800) >>> 11, rightNodeValue);
                return this.doDouble(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x1C) != 0 && leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                if ((state_0 & 4) != 0 && JSGuards.isBigIntZero(leftNodeValue_) && !JSGuards.isBigIntZero(rightNodeValue_) && !JSGuards.isBigIntNegativeVal(rightNodeValue_)) {
                    return this.doBigIntZero(leftNodeValue_, rightNodeValue_);
                }
                if ((state_0 & 8) != 0 && JSGuards.isBigIntZero(rightNodeValue_)) {
                    return this.doBigIntZeroPowZero(leftNodeValue_, rightNodeValue_);
                }
                if (!((state_0 & 0x10) == 0 || JSGuards.isBigIntZero(leftNodeValue_) || JSGuards.isBigIntZero(rightNodeValue_) || JSGuards.isBigIntNegativeVal(rightNodeValue_))) {
                    return this.doBigInt(leftNodeValue_, rightNodeValue_);
                }
            }
        }
        if ((state_0 & 0x60) != 0) {
            GenericData s6_;
            if ((state_0 & 0x20) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
                return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
            }
            if ((state_0 & 0x40) != 0 && (s6_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                return this.doGeneric(leftNodeValue, rightNodeValue, s6_.nestedExponentiateNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x7C) == 0 && (state_0 & 0x7D) != 0) {
            return this.execute_double_double0(state_0, frameValue);
        }
        return this.execute_generic1(state_0, frameValue);
    }

    private Object execute_double_double0(int state_0, VirtualFrame frameValue) {
        double rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x700) == 0 && (state_0 & 0x7F) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x780) >>> 7, leftNodeValue__);
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
            if ((state_0 & 0x7000) == 0 && (state_0 & 0x7F) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x6800) == 0 && (state_0 & 0x7F) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x3800) == 0 && (state_0 & 0x7F) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x7800) >>> 11, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return this.doDouble(leftNodeValue_, rightNodeValue_);
    }

    private Object execute_generic1(int state_0, VirtualFrame frameValue) {
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x780) >>> 7, leftNodeValue_)) {
            double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x780) >>> 7, leftNodeValue_);
            if (JSTypesGen.isImplicitDouble((state_0 & 0x7800) >>> 11, rightNodeValue_)) {
                double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x7800) >>> 11, rightNodeValue_);
                return this.doDouble(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x1C) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            if (rightNodeValue_ instanceof BigInt) {
                BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
                if ((state_0 & 4) != 0 && JSGuards.isBigIntZero(leftNodeValue__) && !JSGuards.isBigIntZero(rightNodeValue__) && !JSGuards.isBigIntNegativeVal(rightNodeValue__)) {
                    return this.doBigIntZero(leftNodeValue__, rightNodeValue__);
                }
                if ((state_0 & 8) != 0 && JSGuards.isBigIntZero(rightNodeValue__)) {
                    return this.doBigIntZeroPowZero(leftNodeValue__, rightNodeValue__);
                }
                if (!((state_0 & 0x10) == 0 || JSGuards.isBigIntZero(leftNodeValue__) || JSGuards.isBigIntZero(rightNodeValue__) || JSGuards.isBigIntNegativeVal(rightNodeValue__))) {
                    return this.doBigInt(leftNodeValue__, rightNodeValue__);
                }
            }
        }
        if ((state_0 & 0x60) != 0) {
            GenericData s6_;
            if ((state_0 & 0x20) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
                return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
            }
            if ((state_0 & 0x40) != 0 && (s6_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
                return this.doGeneric(leftNodeValue_, rightNodeValue_, s6_.nestedExponentiateNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        double rightNodeValue_;
        double leftNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x60) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x700) == 0 && (state_0 & 0x7F) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x780) >>> 7, leftNodeValue__);
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
            if ((state_0 & 0x7000) == 0 && (state_0 & 0x7F) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x6800) == 0 && (state_0 & 0x7F) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0x3800) == 0 && (state_0 & 0x7F) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x7800) >>> 11, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult()));
        }
        if ((state_0 & 1) != 0) {
            return this.doDouble(leftNodeValue_, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x680) == 0 && (state_0 & 0x7F) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0x380) == 0 && (state_0 & 0x7F) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), (state_0 & 0x6800) == 0 && (state_0 & 0x7F) != 0 ? (Number)rightNodeValue_int : (Number)((state_0 & 0x3800) == 0 && (state_0 & 0x7F) != 0 ? (Number)rightNodeValue_long : (Number)rightNodeValue_)));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x7E) == 0 && (state_0 & 0x7F) != 0) {
                this.executeDouble(frameValue);
                return;
            }
            if ((state_0 & 0x7D) != 0) {
                this.execute(frameValue);
                return;
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return;
        }
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 2) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__;
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            if (rightNodeValue_ instanceof BigInt && JSGuards.isBigIntNegativeVal(rightNodeValue__ = (BigInt)rightNodeValue_)) {
                this.doBigIntNegativeExponent(leftNodeValue__, rightNodeValue__);
                return;
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    private Object executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int doubleCast0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
                double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
                int doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue);
                if (doubleCast1 != 0) {
                    double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                    state_0 |= doubleCast0 << 7;
                    state_0 |= doubleCast1 << 11;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.doDouble(leftNodeValue_, rightNodeValue_);
                    return d;
                }
            }
            if (leftNodeValue instanceof BigInt) {
                BigInt leftNodeValue_ = (BigInt)leftNodeValue;
                if (rightNodeValue instanceof BigInt) {
                    BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                    if (JSGuards.isBigIntNegativeVal(rightNodeValue_)) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        this.doBigIntNegativeExponent(leftNodeValue_, rightNodeValue_);
                        Object var9_17 = null;
                        return var9_17;
                    }
                    if (JSGuards.isBigIntZero(leftNodeValue_) && !JSGuards.isBigIntZero(rightNodeValue_) && !JSGuards.isBigIntNegativeVal(rightNodeValue_)) {
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        BigInt bigInt = this.doBigIntZero(leftNodeValue_, rightNodeValue_);
                        return bigInt;
                    }
                    if (JSGuards.isBigIntZero(rightNodeValue_)) {
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        BigInt bigInt = this.doBigIntZeroPowZero(leftNodeValue_, rightNodeValue_);
                        return bigInt;
                    }
                    if (!(JSGuards.isBigIntZero(leftNodeValue_) || JSGuards.isBigIntZero(rightNodeValue_) || JSGuards.isBigIntNegativeVal(rightNodeValue_))) {
                        this.state_0_ = state_0 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        BigInt bigInt = this.doBigInt(leftNodeValue_, rightNodeValue_);
                        return bigInt;
                    }
                }
            }
            if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
                this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                Object leftNodeValue_ = this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
                return leftNodeValue_;
            }
            if (!this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                GenericData s6_ = super.insert(new GenericData());
                s6_.nestedExponentiateNode_ = s6_.insertAccessor(JSExponentiateNode.create());
                s6_.toNumeric1Node_ = s6_.insertAccessor(JSToNumericNode.create());
                s6_.toNumeric2Node_ = s6_.insertAccessor(JSToNumericNode.create());
                s6_.mixedNumericTypes_ = BranchProfile.create();
                VarHandle.storeStoreFence();
                this.generic_cache = s6_;
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                Object object = this.doGeneric(leftNodeValue, rightNodeValue, s6_.nestedExponentiateNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_);
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
        if ((state_0 & 0x7F) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x7F & (state_0 & 0x7F) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[8];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "doBigIntNegativeExponent";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doBigIntZero";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doBigIntZeroPowZero";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[6] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 0x40) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            GenericData s6_ = this.generic_cache;
            if (s6_ != null) {
                cached.add(Arrays.asList(s6_.nestedExponentiateNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[7] = s;
        return Introspection.Provider.create(data);
    }

    public static JSExponentiateNode create(JavaScriptNode left, JavaScriptNode right) {
        return new JSExponentiateNodeGen(left, right);
    }

    @GeneratedBy(value=JSExponentiateNode.class)
    private static final class GenericData
    extends Node {
        @Node.Child
        JSExponentiateNode nestedExponentiateNode_;
        @Node.Child
        JSToNumericNode toNumeric1Node_;
        @Node.Child
        JSToNumericNode toNumeric2Node_;
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

