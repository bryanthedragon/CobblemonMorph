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
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSBitwiseXorConstantNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSBitwiseXorConstantNode.class)
public final class JSBitwiseXorConstantNodeGen
extends JSBitwiseXorConstantNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSToInt32Node double_leftInt32_;
    @Node.Child
    private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
    @Node.Child
    private GenericData generic_cache;
    @Node.Child
    private JSToNumericNode genericBigIntCase_toNumeric_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile genericBigIntCase_profileIsBigInt_;

    private JSBitwiseXorConstantNodeGen(JavaScriptNode left, Object rightValue) {
        super(left, rightValue);
    }

    @Override
    public Object executeObject(Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            assert (this.isInt);
            return this.doInteger(operandNodeValue_);
        }
        if ((state_0 & 2) != 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
            assert (this.isInt);
            return this.doSafeInteger(operandNodeValue_);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, operandNodeValue);
            assert (this.isInt);
            return this.doDouble(operandNodeValue_, this.double_leftInt32_);
        }
        if ((state_0 & 0x40) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            assert (!this.isInt);
            return this.doBigInt(operandNodeValue_);
        }
        if ((state_0 & 0x80) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
            return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
        }
        if ((state_0 & 0x300) != 0) {
            GenericData s8_;
            if ((state_0 & 0x100) != 0 && (s8_ = this.generic_cache) != null && !this.hasOverloadedOperators(operandNodeValue)) {
                assert (this.isInt);
                return this.doGeneric(operandNodeValue, s8_.toNumeric_, s8_.profileIsBigInt_, s8_.innerXorNode_);
            }
            if ((state_0 & 0x200) != 0 && !this.hasOverloadedOperators(operandNodeValue)) {
                assert (!this.isInt());
                return this.doGenericBigIntCase(operandNodeValue, this.genericBigIntCase_toNumeric_, this.genericBigIntCase_profileIsBigInt_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            assert (this.isInt);
            return this.doInteger(operandNodeValue_);
        }
        if ((state_0 & 2) != 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
            assert (this.isInt);
            return this.doSafeInteger(operandNodeValue_);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, operandNodeValue);
            assert (this.isInt);
            return this.doDouble(operandNodeValue_, this.double_leftInt32_);
        }
        if ((state_0 & 0x40) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            assert (!this.isInt);
            return this.doBigInt(operandNodeValue_);
        }
        if ((state_0 & 0x80) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
            return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
        }
        if ((state_0 & 0x300) != 0) {
            GenericData s8_;
            if ((state_0 & 0x100) != 0 && (s8_ = this.generic_cache) != null && !this.hasOverloadedOperators(operandNodeValue)) {
                assert (this.isInt);
                return this.doGeneric(operandNodeValue, s8_.toNumeric_, s8_.profileIsBigInt_, s8_.innerXorNode_);
            }
            if ((state_0 & 0x200) != 0 && !this.hasOverloadedOperators(operandNodeValue)) {
                assert (!this.isInt());
                return this.doGenericBigIntCase(operandNodeValue, this.genericBigIntCase_toNumeric_, this.genericBigIntCase_profileIsBigInt_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x3C6) == 0 && (state_0 & 0x3C7) != 0) {
            return this.execute_int0(state_0, frameValue);
        }
        if ((state_0 & 0x3C3) == 0 && (state_0 & 0x3C7) != 0) {
            return this.execute_double1(state_0, frameValue);
        }
        return this.execute_generic2(state_0, frameValue);
    }

    private Object execute_int0(int state_0, VirtualFrame frameValue) {
        int operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        assert (this.isInt);
        return this.doInteger(operandNodeValue_);
    }

    private Object execute_double1(int state_0, VirtualFrame frameValue) {
        double operandNodeValue_;
        long operandNodeValue_long = 0L;
        int operandNodeValue_int = 0;
        try {
            if ((state_0 & 0x3800) == 0 && (state_0 & 0x3FF) != 0) {
                operandNodeValue_ = this.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0) {
                operandNodeValue_int = this.operandNode.executeInt(frameValue);
                operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0) {
                operandNodeValue_long = this.operandNode.executeLong(frameValue);
                operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
                Object operandNodeValue__ = this.operandNode.execute(frameValue);
                operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C00) >>> 10, operandNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 4) != 0);
        assert (this.isInt);
        return this.doDouble(operandNodeValue_, this.double_leftInt32_);
    }

    private Object execute_generic2(int state_0, VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__ = (Integer)operandNodeValue_;
            assert (this.isInt);
            return this.doInteger(operandNodeValue__);
        }
        if ((state_0 & 2) != 0 && operandNodeValue_ instanceof SafeInteger) {
            SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
            assert (this.isInt);
            return this.doSafeInteger(operandNodeValue__);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, operandNodeValue_);
            assert (this.isInt);
            return this.doDouble(operandNodeValue__, this.double_leftInt32_);
        }
        if ((state_0 & 0x40) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
            assert (!this.isInt);
            return this.doBigInt(operandNodeValue__);
        }
        if ((state_0 & 0x80) != 0 && operandNodeValue_ instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue__ = (JSOverloadedOperatorsObject)operandNodeValue_;
            return this.doOverloaded(operandNodeValue__, this.overloaded_overloadedOperatorNode_);
        }
        if ((state_0 & 0x300) != 0) {
            GenericData s8_;
            if ((state_0 & 0x100) != 0 && (s8_ = this.generic_cache) != null && !this.hasOverloadedOperators(operandNodeValue_)) {
                assert (this.isInt);
                return this.doGeneric(operandNodeValue_, s8_.toNumeric_, s8_.profileIsBigInt_, s8_.innerXorNode_);
            }
            if ((state_0 & 0x200) != 0 && !this.hasOverloadedOperators(operandNodeValue_)) {
                assert (!this.isInt());
                return this.doGenericBigIntCase(operandNodeValue_, this.genericBigIntCase_toNumeric_, this.genericBigIntCase_profileIsBigInt_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0x180) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
        }
        if ((state_0 & 6) == 0 && (state_0 & 7) != 0) {
            return this.executeInt_int3(state_0, frameValue);
        }
        if ((state_0 & 3) == 0 && (state_0 & 7) != 0) {
            return this.executeInt_double4(state_0, frameValue);
        }
        return this.executeInt_generic5(state_0, frameValue);
    }

    private int executeInt_int3(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        int operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult()));
        }
        assert ((state_0 & 1) != 0);
        assert (this.isInt);
        return this.doInteger(operandNodeValue_);
    }

    private int executeInt_double4(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        double operandNodeValue_;
        long operandNodeValue_long = 0L;
        int operandNodeValue_int = 0;
        try {
            if ((state_0 & 0x3800) == 0 && (state_0 & 0x3FF) != 0) {
                operandNodeValue_ = this.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0) {
                operandNodeValue_int = this.operandNode.executeInt(frameValue);
                operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0) {
                operandNodeValue_long = this.operandNode.executeLong(frameValue);
                operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
                Object operandNodeValue__ = this.operandNode.execute(frameValue);
                operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C00) >>> 10, operandNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(ex.getResult()));
        }
        assert ((state_0 & 4) != 0);
        assert (this.isInt);
        return this.doDouble(operandNodeValue_, this.double_leftInt32_);
    }

    private int executeInt_generic5(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__ = (Integer)operandNodeValue_;
            assert (this.isInt);
            return this.doInteger(operandNodeValue__);
        }
        if ((state_0 & 2) != 0 && operandNodeValue_ instanceof SafeInteger) {
            SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
            assert (this.isInt);
            return this.doSafeInteger(operandNodeValue__);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, operandNodeValue_);
            assert (this.isInt);
            return this.doDouble(operandNodeValue__, this.double_leftInt32_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(operandNodeValue_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x3F8) == 0 && (state_0 & 0x3FF) != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0x3C7) != 0) {
                this.execute(frameValue);
                return;
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return;
        }
        if ((state_0 & 0x30) == 0 && (state_0 & 0x38) != 0) {
            this.executeVoid_int6(state_0, frameValue);
            return;
        }
        if ((state_0 & 0x28) == 0 && (state_0 & 0x38) != 0) {
            this.executeVoid_double7(state_0, frameValue);
            return;
        }
        this.executeVoid_generic8(state_0, frameValue);
    }

    private void executeVoid_int6(int state_0, VirtualFrame frameValue) {
        int operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(ex.getResult());
            return;
        }
        assert ((state_0 & 8) != 0);
        assert (!this.isInt);
        this.doIntegerThrows(operandNodeValue_);
    }

    private void executeVoid_double7(int state_0, VirtualFrame frameValue) {
        double operandNodeValue_;
        long operandNodeValue_long = 0L;
        int operandNodeValue_int = 0;
        try {
            if ((state_0 & 0x3800) == 0 && (state_0 & 0x3FF) != 0) {
                operandNodeValue_ = this.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 0x3400) == 0 && (state_0 & 0x3FF) != 0) {
                operandNodeValue_int = this.operandNode.executeInt(frameValue);
                operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 0x1C00) == 0 && (state_0 & 0x3FF) != 0) {
                operandNodeValue_long = this.operandNode.executeLong(frameValue);
                operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
                Object operandNodeValue__ = this.operandNode.execute(frameValue);
                operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C00) >>> 10, operandNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(ex.getResult());
            return;
        }
        assert ((state_0 & 0x10) != 0);
        assert (!this.isInt);
        this.doDoubleThrows(operandNodeValue_);
    }

    private void executeVoid_generic8(int state_0, VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 8) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__ = (Integer)operandNodeValue_;
            assert (!this.isInt);
            this.doIntegerThrows(operandNodeValue__);
            return;
        }
        if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, operandNodeValue_);
            assert (!this.isInt);
            this.doDoubleThrows(operandNodeValue__);
            return;
        }
        if ((state_0 & 0x20) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
            assert (this.isInt);
            this.doBigIntThrows(operandNodeValue__);
            return;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.executeAndSpecialize(operandNodeValue_);
    }

    private Object executeAndSpecialize(Object operandNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            double operandNodeValue_5;
            int doubleCast0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if ((exclude & 1) == 0 && operandNodeValue instanceof Integer) {
                int operandNodeValue_2 = (Integer)operandNodeValue;
                if (this.isInt) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doInteger(operandNodeValue_2);
                    return n;
                }
            }
            if ((exclude & 2) == 0 && operandNodeValue instanceof SafeInteger) {
                SafeInteger operandNodeValue_3 = (SafeInteger)operandNodeValue;
                if (this.isInt) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doSafeInteger(operandNodeValue_3);
                    return n;
                }
            }
            if ((exclude & 4) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
                operandNodeValue_5 = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
                if (this.isInt) {
                    this.double_leftInt32_ = super.insert(JSToInt32Node.create());
                    state_0 |= doubleCast0 << 10;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.doDouble(operandNodeValue_5, this.double_leftInt32_);
                    return n;
                }
            }
            if ((exclude & 8) == 0 && operandNodeValue instanceof Integer) {
                int operandNodeValue_4 = (Integer)operandNodeValue;
                if (!this.isInt) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    this.doIntegerThrows(operandNodeValue_4);
                    Object operandNodeValue_5 = null;
                    return operandNodeValue_5;
                }
            }
            if ((exclude & 0x10) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
                operandNodeValue_5 = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
                if (!this.isInt) {
                    state_0 |= doubleCast0 << 10;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    this.doDoubleThrows(operandNodeValue_5);
                    Object var9_24 = null;
                    return var9_24;
                }
            }
            if (operandNodeValue instanceof BigInt) {
                BigInt operandNodeValue_6 = (BigInt)operandNodeValue;
                if ((exclude & 0x20) == 0 && this.isInt) {
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    this.doBigIntThrows(operandNodeValue_6);
                    Object var7_19 = null;
                    return var7_19;
                }
                if ((exclude & 0x40) == 0 && !this.isInt) {
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    BigInt bigInt = this.doBigInt(operandNodeValue_6);
                    return bigInt;
                }
            }
            if (operandNodeValue instanceof JSOverloadedOperatorsObject) {
                JSOverloadedOperatorsObject operandNodeValue_7 = (JSOverloadedOperatorsObject)operandNodeValue;
                this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                Object object = this.doOverloaded(operandNodeValue_7, this.overloaded_overloadedOperatorNode_);
                return object;
            }
            if (!this.hasOverloadedOperators(operandNodeValue) && this.isInt) {
                GenericData s8_ = super.insert(new GenericData());
                s8_.toNumeric_ = s8_.insertAccessor(JSToNumericNode.create());
                s8_.profileIsBigInt_ = ConditionProfile.createBinaryProfile();
                s8_.innerXorNode_ = s8_.insertAccessor(this.makeCopy());
                VarHandle.storeStoreFence();
                this.generic_cache = s8_;
                this.exclude_ = exclude |= 0x27;
                state_0 &= 0xFFFFFFD8;
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                Object object = this.doGeneric(operandNodeValue, s8_.toNumeric_, s8_.profileIsBigInt_, s8_.innerXorNode_);
                return object;
            }
            if (!this.hasOverloadedOperators(operandNodeValue) && !this.isInt()) {
                this.genericBigIntCase_toNumeric_ = super.insert(JSToNumericNode.create());
                this.genericBigIntCase_profileIsBigInt_ = ConditionProfile.createBinaryProfile();
                this.exclude_ = exclude |= 0x58;
                state_0 &= 0xFFFFFFA7;
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                BigInt bigInt = this.doGenericBigIntCase(operandNodeValue, this.genericBigIntCase_toNumeric_, this.genericBigIntCase_profileIsBigInt_);
                return bigInt;
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
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[2] = s;
        s = new Object[3];
        s[0] = "doDouble";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.double_leftInt32_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "doIntegerThrows";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : ((exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[4] = s;
        s = new Object[3];
        s[0] = "doDoubleThrows";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x10) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[5] = s;
        s = new Object[3];
        s[0] = "doBigIntThrows";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x20) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[6] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x40) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[7] = s;
        s = new Object[3];
        s[0] = "doOverloaded";
        if ((state_0 & 0x80) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[8] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            GenericData s8_ = this.generic_cache;
            if (s8_ != null) {
                cached.add(Arrays.asList(s8_.toNumeric_, s8_.profileIsBigInt_, s8_.innerXorNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "doGenericBigIntCase";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.genericBigIntCase_toNumeric_, this.genericBigIntCase_profileIsBigInt_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[10] = s;
        return Introspection.Provider.create(data);
    }

    public static JSBitwiseXorConstantNode create(JavaScriptNode left, Object rightValue) {
        return new JSBitwiseXorConstantNodeGen(left, rightValue);
    }

    @GeneratedBy(value=JSBitwiseXorConstantNode.class)
    private static final class GenericData
    extends Node {
        @Node.Child
        JSToNumericNode toNumeric_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile profileIsBigInt_;
        @Node.Child
        JavaScriptNode innerXorNode_;

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

