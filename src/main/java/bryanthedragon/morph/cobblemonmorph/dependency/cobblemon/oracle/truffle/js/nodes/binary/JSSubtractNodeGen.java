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
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.binary.JSSubtractNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSSubtractNode.class)
public final class JSSubtractNodeGen
extends JSSubtractNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private GenericData generic_cache;

    private JSSubtractNodeGen(boolean truncate, JavaScriptNode left, JavaScriptNode right) {
        super(truncate, left, right);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object execute(Object leftNodeValue, Object rightNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            if (rightNodeValue instanceof Integer) {
                int rightNodeValue_ = (Integer)rightNodeValue;
                try {
                    return this.doInt(leftNodeValue_, rightNodeValue_);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 1;
                        this.state_0_ &= 0xFFFFFFFE;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
                }
            }
        }
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x1E0) >>> 5, leftNodeValue)) {
            double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x1E0) >>> 5, leftNodeValue);
            if (JSTypesGen.isImplicitDouble((state_0 & 0x1E00) >>> 9, rightNodeValue)) {
                double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x1E00) >>> 9, rightNodeValue);
                return this.doDouble(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 4) != 0 && leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
                BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                return this.doBigInt(leftNodeValue_, rightNodeValue_);
            }
        }
        if ((state_0 & 0x18) != 0) {
            GenericData s4_;
            if ((state_0 & 8) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
                return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
            }
            if ((state_0 & 0x10) != 0 && (s4_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                return this.doGeneric(leftNodeValue, rightNodeValue, s4_.toNumericA_, s4_.toNumericB_, s4_.subtract_, s4_.mixedNumericTypes_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x1E) == 0 && (state_0 & 0x1F) != 0) {
            return this.execute_int_int0(state_0, frameValue);
        }
        if ((state_0 & 0x1D) == 0 && (state_0 & 0x1F) != 0) {
            return this.execute_double_double1(state_0, frameValue);
        }
        return this.execute_generic2(state_0, frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
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
        try {
            return this.doInt(leftNodeValue_, rightNodeValue_);
        }
        catch (ArithmeticException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();
            try {
                this.exclude_ |= 1;
                this.state_0_ &= 0xFFFFFFFE;
            }
            finally {
                lock.unlock();
            }
            return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
        }
    }

    private Object execute_double_double1(int state_0, VirtualFrame frameValue) {
        double rightNodeValue_;
        double leftNodeValue_;
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x1C0) == 0 && (state_0 & 0x1F) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x1A0) == 0 && (state_0 & 0x1F) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0xE0) == 0 && (state_0 & 0x1F) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x1E0) >>> 5, leftNodeValue__);
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
            if ((state_0 & 0x1C00) == 0 && (state_0 & 0x1F) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x1A00) == 0 && (state_0 & 0x1F) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0xE00) == 0 && (state_0 & 0x1F) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x1E00) >>> 9, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize((state_0 & 0x1A0) == 0 && (state_0 & 0x1F) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0xE0) == 0 && (state_0 & 0x1F) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult());
        }
        assert ((state_0 & 2) != 0);
        return this.doDouble(leftNodeValue_, rightNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object execute_generic2(int state_0, VirtualFrame frameValue) {
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 1) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__ = (Integer)leftNodeValue_;
            if (rightNodeValue_ instanceof Integer) {
                int rightNodeValue__ = (Integer)rightNodeValue_;
                try {
                    return this.doInt(leftNodeValue__, rightNodeValue__);
                }
                catch (ArithmeticException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 1;
                        this.state_0_ &= 0xFFFFFFFE;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(leftNodeValue__, rightNodeValue__);
                }
            }
        }
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x1E0) >>> 5, leftNodeValue_)) {
            double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x1E0) >>> 5, leftNodeValue_);
            if (JSTypesGen.isImplicitDouble((state_0 & 0x1E00) >>> 9, rightNodeValue_)) {
                double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x1E00) >>> 9, rightNodeValue_);
                return this.doDouble(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 4) != 0 && leftNodeValue_ instanceof BigInt) {
            BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
            if (rightNodeValue_ instanceof BigInt) {
                BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
                return this.doBigInt(leftNodeValue__, rightNodeValue__);
            }
        }
        if ((state_0 & 0x18) != 0) {
            GenericData s4_;
            if ((state_0 & 8) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
                return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
            }
            if ((state_0 & 0x10) != 0 && (s4_ = this.generic_cache) != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
                return this.doGeneric(leftNodeValue_, rightNodeValue_, s4_.toNumericA_, s4_.toNumericB_, s4_.subtract_, s4_.mixedNumericTypes_);
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
        if ((state_0 & 0x18) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        long leftNodeValue_long = 0L;
        int leftNodeValue_int = 0;
        try {
            if ((state_0 & 0x1C0) == 0 && (state_0 & 0x1F) != 0) {
                leftNodeValue_ = this.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 0x1A0) == 0 && (state_0 & 0x1F) != 0) {
                leftNodeValue_int = this.leftNode.executeInt(frameValue);
                leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 0xE0) == 0 && (state_0 & 0x1F) != 0) {
                leftNodeValue_long = this.leftNode.executeLong(frameValue);
                leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
                Object leftNodeValue__ = this.leftNode.execute(frameValue);
                leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x1E0) >>> 5, leftNodeValue__);
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
            if ((state_0 & 0x1C00) == 0 && (state_0 & 0x1F) != 0) {
                rightNodeValue_ = this.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 0x1A00) == 0 && (state_0 & 0x1F) != 0) {
                rightNodeValue_int = this.rightNode.executeInt(frameValue);
                rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 0xE00) == 0 && (state_0 & 0x1F) != 0) {
                rightNodeValue_long = this.rightNode.executeLong(frameValue);
                rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
                Object rightNodeValue__ = this.rightNode.execute(frameValue);
                rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x1E00) >>> 9, rightNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x1A0) == 0 && (state_0 & 0x1F) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0xE0) == 0 && (state_0 & 0x1F) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), ex.getResult()));
        }
        if ((state_0 & 2) != 0) {
            return this.doDouble(leftNodeValue_, rightNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize((state_0 & 0x1A0) == 0 && (state_0 & 0x1F) != 0 ? (Number)leftNodeValue_int : (Number)((state_0 & 0xE0) == 0 && (state_0 & 0x1F) != 0 ? (Number)leftNodeValue_long : (Number)leftNodeValue_), (state_0 & 0x1A00) == 0 && (state_0 & 0x1F) != 0 ? (Number)rightNodeValue_int : (Number)((state_0 & 0xE00) == 0 && (state_0 & 0x1F) != 0 ? (Number)rightNodeValue_long : (Number)rightNodeValue_)));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int rightNodeValue_;
        int leftNodeValue_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x18) != 0) {
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
        if ((state_0 & 1) != 0) {
            try {
                return this.doInt(leftNodeValue_, rightNodeValue_);
            }
            catch (ArithmeticException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Lock lock = this.getLock();
                lock.lock();
                try {
                    this.exclude_ |= 1;
                    this.state_0_ &= 0xFFFFFFFE;
                }
                finally {
                    lock.unlock();
                }
                return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x1E) == 0 && (state_0 & 0x1F) != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0x1D) == 0 && (state_0 & 0x1F) != 0) {
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private Object executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int doubleCast0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if ((exclude & 1) == 0 && leftNodeValue instanceof Integer) {
                int leftNodeValue_ = (Integer)leftNodeValue;
                if (rightNodeValue instanceof Integer) {
                    int rightNodeValue_ = (Integer)rightNodeValue;
                    this.state_0_ = state_0 |= 1;
                    try {
                        lock.unlock();
                        hasLock = false;
                        Integer n = this.doInt(leftNodeValue_, rightNodeValue_);
                        return n;
                    }
                    catch (ArithmeticException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        lock.lock();
                        try {
                            this.exclude_ |= 1;
                            this.state_0_ &= 0xFFFFFFFE;
                        }
                        finally {
                            lock.unlock();
                        }
                        Object object = this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
                        if (hasLock) {
                            lock.unlock();
                        }
                        return object;
                    }
                }
            }
            if ((exclude & 2) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
                double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
                int doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue);
                if (doubleCast1 != 0) {
                    double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
                    this.exclude_ = exclude |= 1;
                    state_0 &= 0xFFFFFFFE;
                    state_0 |= doubleCast0 << 5;
                    state_0 |= doubleCast1 << 9;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.doDouble(leftNodeValue_, rightNodeValue_);
                    return d;
                }
            }
            if ((exclude & 4) == 0 && leftNodeValue instanceof BigInt) {
                BigInt leftNodeValue_ = (BigInt)leftNodeValue;
                if (rightNodeValue instanceof BigInt) {
                    BigInt rightNodeValue_ = (BigInt)rightNodeValue;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    BigInt bigInt = this.doBigInt(leftNodeValue_, rightNodeValue_);
                    return bigInt;
                }
            }
            if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
                this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                Object leftNodeValue_ = this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
                return leftNodeValue_;
            }
            if (!this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                GenericData s4_ = super.insert(new GenericData());
                s4_.toNumericA_ = s4_.insertAccessor(JSToNumericNode.create());
                s4_.toNumericB_ = s4_.insertAccessor(JSToNumericNode.create());
                s4_.subtract_ = s4_.insertAccessor(this.copyRecursive());
                s4_.mixedNumericTypes_ = BranchProfile.create();
                VarHandle.storeStoreFence();
                this.generic_cache = s4_;
                this.exclude_ = exclude |= 7;
                state_0 &= 0xFFFFFFF8;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Object object = this.doGeneric(leftNodeValue, rightNodeValue, s4_.toNumericA_, s4_.toNumericB_, s4_.subtract_, s4_.mixedNumericTypes_);
                return object;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.leftNode, this.rightNode}, leftNodeValue, rightNodeValue);
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
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
        if ((state_0 & 0x1F) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x1F & (state_0 & 0x1F) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[6];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[2] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : ((exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[3] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            GenericData s4_ = this.generic_cache;
            if (s4_ != null) {
                cached.add(Arrays.asList(s4_.toNumericA_, s4_.toNumericB_, s4_.subtract_, s4_.mixedNumericTypes_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        return Introspection.Provider.create(data);
    }

    public static JSSubtractNode create(boolean truncate, JavaScriptNode left, JavaScriptNode right) {
        return new JSSubtractNodeGen(truncate, left, right);
    }

    @GeneratedBy(value=JSSubtractNode.class)
    private static final class GenericData
    extends Node {
        @Node.Child
        JSToNumericNode toNumericA_;
        @Node.Child
        JSToNumericNode toNumericB_;
        @Node.Child
        JavaScriptNode subtract_;
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

