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
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.binary.JSAddNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToOperandNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.OperatorSet;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSOverloadedBinaryNode.class)
public final class JSOverloadedBinaryNodeGen
extends JSOverloadedBinaryNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private ToOperandGenericData toOperandGeneric_cache;
    @Node.Child
    private ToOperandAdditionData toOperandAddition_cache;
    @Node.Child
    private ToNumericOperandData toNumericOperand_cache;

    private JSOverloadedBinaryNodeGen(TruffleString overloadedOperatorName, boolean numeric, JSToPrimitiveNode.Hint hint, boolean leftToRight) {
        super(overloadedOperatorName, numeric, hint, leftToRight);
    }

    @Override
    public Object execute(Object arg0Value, Object arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            ToNumericOperandData s2_;
            ToOperandAdditionData s1_;
            ToOperandGenericData s0_;
            if ((state_0 & 1) != 0 && (s0_ = this.toOperandGeneric_cache) != null) {
                assert (!this.isNumeric());
                assert (!this.isAddition());
                return this.doToOperandGeneric(arg0Value, arg1Value, s0_.toOperandLeftNode_, s0_.toOperandRightNode_, s0_.dispatchBinaryOperatorNode_);
            }
            if ((state_0 & 2) != 0 && (s1_ = this.toOperandAddition_cache) != null) {
                assert (!this.isNumeric());
                assert (this.isAddition());
                return this.doToOperandAddition(arg0Value, arg1Value, s1_.toOperandLeftNode_, s1_.toOperandRightNode_, s1_.dispatchBinaryOperatorNode_, s1_.toStringLeftNode_, s1_.toStringRightNode_, s1_.leftStringProfile_, s1_.rightStringProfile_, s1_.addNode_);
            }
            if ((state_0 & 4) != 0 && (s2_ = this.toNumericOperand_cache) != null) {
                assert (this.isNumeric());
                return this.doToNumericOperand(arg0Value, arg1Value, s2_.toNumericOperandLeftNode_, s2_.toNumericOperandRightNode_, s2_.dispatchBinaryOperatorNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    private Object executeAndSpecialize(Object arg0Value, Object arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (!this.isNumeric() && !this.isAddition()) {
                ToOperandGenericData s0_ = super.insert(new ToOperandGenericData());
                s0_.toOperandLeftNode_ = s0_.insertAccessor(JSToOperandNode.create(this.getHint(), !this.isEquality()));
                s0_.toOperandRightNode_ = s0_.insertAccessor(JSToOperandNode.create(this.getHint(), !this.isEquality()));
                s0_.dispatchBinaryOperatorNode_ = s0_.insertAccessor(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.create(this.getOverloadedOperatorName()));
                VarHandle.storeStoreFence();
                this.toOperandGeneric_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.doToOperandGeneric(arg0Value, arg1Value, s0_.toOperandLeftNode_, s0_.toOperandRightNode_, s0_.dispatchBinaryOperatorNode_);
                return object;
            }
            if (!this.isNumeric() && this.isAddition()) {
                ToOperandAdditionData s1_ = super.insert(new ToOperandAdditionData());
                s1_.toOperandLeftNode_ = s1_.insertAccessor(JSToOperandNode.create(this.getHint()));
                s1_.toOperandRightNode_ = s1_.insertAccessor(JSToOperandNode.create(this.getHint()));
                s1_.dispatchBinaryOperatorNode_ = s1_.insertAccessor(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.create(this.getOverloadedOperatorName()));
                s1_.toStringLeftNode_ = s1_.insertAccessor(JSToStringNode.create());
                s1_.toStringRightNode_ = s1_.insertAccessor(JSToStringNode.create());
                s1_.leftStringProfile_ = ConditionProfile.createBinaryProfile();
                s1_.rightStringProfile_ = ConditionProfile.createBinaryProfile();
                s1_.addNode_ = s1_.insertAccessor(JSAddNode.createUnoptimized());
                VarHandle.storeStoreFence();
                this.toOperandAddition_cache = s1_;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = this.doToOperandAddition(arg0Value, arg1Value, s1_.toOperandLeftNode_, s1_.toOperandRightNode_, s1_.dispatchBinaryOperatorNode_, s1_.toStringLeftNode_, s1_.toStringRightNode_, s1_.leftStringProfile_, s1_.rightStringProfile_, s1_.addNode_);
                return object;
            }
            if (this.isNumeric()) {
                ToNumericOperandData s2_ = super.insert(new ToNumericOperandData());
                s2_.toNumericOperandLeftNode_ = s2_.insertAccessor(JSToNumericNode.create(true));
                s2_.toNumericOperandRightNode_ = s2_.insertAccessor(JSToNumericNode.create(true));
                s2_.dispatchBinaryOperatorNode_ = s2_.insertAccessor(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.create(this.getOverloadedOperatorName()));
                VarHandle.storeStoreFence();
                this.toNumericOperand_cache = s2_;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.doToNumericOperand(arg0Value, arg1Value, s2_.toNumericOperandLeftNode_, s2_.toNumericOperandRightNode_, s2_.dispatchBinaryOperatorNode_);
                return object;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doToOperandGeneric";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            ToOperandGenericData s0_ = this.toOperandGeneric_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.toOperandLeftNode_, s0_.toOperandRightNode_, s0_.dispatchBinaryOperatorNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doToOperandAddition";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ToOperandAdditionData s1_ = this.toOperandAddition_cache;
            if (s1_ != null) {
                cached.add(Arrays.asList(s1_.toOperandLeftNode_, s1_.toOperandRightNode_, s1_.dispatchBinaryOperatorNode_, s1_.toStringLeftNode_, s1_.toStringRightNode_, s1_.leftStringProfile_, s1_.rightStringProfile_, s1_.addNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doToNumericOperand";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ToNumericOperandData s2_ = this.toNumericOperand_cache;
            if (s2_ != null) {
                cached.add(Arrays.asList(s2_.toNumericOperandLeftNode_, s2_.toNumericOperandRightNode_, s2_.dispatchBinaryOperatorNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static JSOverloadedBinaryNode create(TruffleString overloadedOperatorName, boolean numeric, JSToPrimitiveNode.Hint hint, boolean leftToRight) {
        return new JSOverloadedBinaryNodeGen(overloadedOperatorName, numeric, hint, leftToRight);
    }

    @GeneratedBy(value=JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
    public static final class DispatchBinaryOperatorNodeGen
    extends JSOverloadedBinaryNode.DispatchBinaryOperatorNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private OverloadedOverloadedData overloadedOverloaded_cache;
        @Node.Child
        private OverloadedNumberData overloadedNumber_cache;
        @Node.Child
        private OverloadedBigIntData overloadedBigInt_cache;
        @Node.Child
        private OverloadedStringData overloadedString_cache;
        @Node.Child
        private NumberOverloadedData numberOverloaded_cache;
        @Node.Child
        private BigIntOverloadedData bigIntOverloaded_cache;
        @Node.Child
        private StringOverloadedData stringOverloaded_cache;
        @Node.Child
        private JSFunctionCallNode generic_callNode_;

        private DispatchBinaryOperatorNodeGen(TruffleString overloadedOperatorName) {
            super(overloadedOperatorName);
        }

        @Override
        @ExplodeLoop
        protected Object execute(Object arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 0x1F) != 0 && arg0Value instanceof JSOverloadedOperatorsObject) {
                TruffleObject arg1Value_;
                JSOverloadedOperatorsObject arg0Value_ = (JSOverloadedOperatorsObject)arg0Value;
                if ((state_0 & 1) != 0 && arg1Value instanceof JSOverloadedOperatorsObject) {
                    arg1Value_ = (JSOverloadedOperatorsObject)arg1Value;
                    OverloadedOverloadedData s0_ = this.overloadedOverloaded_cache;
                    while (s0_ != null) {
                        if (arg0Value_.matchesOperatorCounter(s0_.leftOperatorCounter_) && ((JSOverloadedOperatorsObject)arg1Value_).matchesOperatorCounter(s0_.rightOperatorCounter_)) {
                            return this.doOverloadedOverloaded(arg0Value_, (JSOverloadedOperatorsObject)arg1Value_, s0_.leftOperatorCounter_, s0_.rightOperatorCounter_, s0_.operatorImplementation_, s0_.callNode_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    OverloadedNumberData s1_ = this.overloadedNumber_cache;
                    while (s1_ != null) {
                        if (arg0Value_.matchesOperatorCounter(s1_.leftOperatorCounter_) && JSGuards.isNumber(arg1Value)) {
                            return this.doOverloadedNumber(arg0Value_, arg1Value, s1_.leftOperatorCounter_, s1_.operatorImplementation_, s1_.callNode_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 4) != 0 && arg1Value instanceof BigInt) {
                    arg1Value_ = (BigInt)arg1Value;
                    OverloadedBigIntData s2_ = this.overloadedBigInt_cache;
                    while (s2_ != null) {
                        if (arg0Value_.matchesOperatorCounter(s2_.leftOperatorCounter_)) {
                            return this.doOverloadedBigInt(arg0Value_, (BigInt)arg1Value_, s2_.leftOperatorCounter_, s2_.operatorImplementation_, s2_.callNode_);
                        }
                        s2_ = s2_.next_;
                    }
                }
                if ((state_0 & 0x18) != 0) {
                    if ((state_0 & 8) != 0) {
                        OverloadedStringData s3_ = this.overloadedString_cache;
                        while (s3_ != null) {
                            if (arg0Value_.matchesOperatorCounter(s3_.leftOperatorCounter_) && JSGuards.isString(arg1Value)) {
                                assert (!this.isAddition());
                                return this.doOverloadedString(arg0Value_, arg1Value, s3_.leftOperatorCounter_, s3_.operatorImplementation_, s3_.callNode_);
                            }
                            s3_ = s3_.next_;
                        }
                    }
                    if ((state_0 & 0x10) != 0 && JSGuards.isNullOrUndefined(arg1Value)) {
                        return this.doOverloadedNullish(arg0Value_, arg1Value);
                    }
                }
            }
            if ((state_0 & 0x1E0) != 0 && arg1Value instanceof JSOverloadedOperatorsObject) {
                JSOverloadedOperatorsObject arg1Value_ = (JSOverloadedOperatorsObject)arg1Value;
                if ((state_0 & 0x20) != 0) {
                    NumberOverloadedData s5_ = this.numberOverloaded_cache;
                    while (s5_ != null) {
                        if (arg1Value_.matchesOperatorCounter(s5_.rightOperatorCounter_) && JSGuards.isNumber(arg0Value)) {
                            return this.doNumberOverloaded(arg0Value, arg1Value_, s5_.rightOperatorCounter_, s5_.operatorImplementation_, s5_.callNode_);
                        }
                        s5_ = s5_.next_;
                    }
                }
                if ((state_0 & 0x40) != 0 && arg0Value instanceof BigInt) {
                    BigInt arg0Value_ = (BigInt)arg0Value;
                    BigIntOverloadedData s6_ = this.bigIntOverloaded_cache;
                    while (s6_ != null) {
                        if (arg1Value_.matchesOperatorCounter(s6_.rightOperatorCounter_)) {
                            return this.doBigIntOverloaded(arg0Value_, arg1Value_, s6_.rightOperatorCounter_, s6_.operatorImplementation_, s6_.callNode_);
                        }
                        s6_ = s6_.next_;
                    }
                }
                if ((state_0 & 0x180) != 0) {
                    if ((state_0 & 0x80) != 0) {
                        StringOverloadedData s7_ = this.stringOverloaded_cache;
                        while (s7_ != null) {
                            if (arg1Value_.matchesOperatorCounter(s7_.rightOperatorCounter_) && JSGuards.isString(arg0Value)) {
                                assert (!this.isAddition());
                                return this.doStringOverloaded(arg0Value, arg1Value_, s7_.rightOperatorCounter_, s7_.operatorImplementation_, s7_.callNode_);
                            }
                            s7_ = s7_.next_;
                        }
                    }
                    if ((state_0 & 0x100) != 0 && JSGuards.isNullOrUndefined(arg0Value)) {
                        return this.doNullishOverloaded(arg0Value, arg1Value_);
                    }
                }
            }
            if ((state_0 & 0x200) != 0) {
                return this.doGeneric(arg0Value, arg1Value, this.generic_callNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int oldState_0;
                block66: {
                    JSOverloadedOperatorsObject arg1Value_;
                    block69: {
                        int exclude;
                        int state_0;
                        block68: {
                            int rightOperatorCounter__2;
                            block67: {
                                int rightOperatorCounter__42;
                                block61: {
                                    JSOverloadedOperatorsObject arg0Value_;
                                    block65: {
                                        int leftOperatorCounter__72;
                                        block64: {
                                            int leftOperatorCounter__62;
                                            block63: {
                                                int leftOperatorCounter__52;
                                                block62: {
                                                    int rightOperatorCounter__;
                                                    int leftOperatorCounter__42;
                                                    state_0 = this.state_0_;
                                                    exclude = this.exclude_;
                                                    oldState_0 = state_0;
                                                    if (!(arg0Value instanceof JSOverloadedOperatorsObject)) break block61;
                                                    arg0Value_ = (JSOverloadedOperatorsObject)arg0Value;
                                                    if ((exclude & 1) != 0 || !(arg1Value instanceof JSOverloadedOperatorsObject)) break block62;
                                                    JSOverloadedOperatorsObject arg1Value_2 = (JSOverloadedOperatorsObject)arg1Value;
                                                    int count0_ = 0;
                                                    OverloadedOverloadedData s0_ = this.overloadedOverloaded_cache;
                                                    if ((state_0 & 1) != 0) {
                                                        while (!(s0_ == null || arg0Value_.matchesOperatorCounter(s0_.leftOperatorCounter_) && arg1Value_2.matchesOperatorCounter(s0_.rightOperatorCounter_))) {
                                                            s0_ = s0_.next_;
                                                            ++count0_;
                                                        }
                                                    }
                                                    if (s0_ == null && arg0Value_.matchesOperatorCounter(leftOperatorCounter__42 = arg0Value_.getOperatorCounter()) && arg1Value_2.matchesOperatorCounter(rightOperatorCounter__ = arg1Value_2.getOperatorCounter()) && count0_ < 3) {
                                                        s0_ = super.insert(new OverloadedOverloadedData(this.overloadedOverloaded_cache));
                                                        s0_.leftOperatorCounter_ = leftOperatorCounter__42;
                                                        s0_.rightOperatorCounter_ = rightOperatorCounter__;
                                                        s0_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value_, arg1Value_2, this.getOverloadedOperatorName());
                                                        s0_.callNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
                                                        VarHandle.storeStoreFence();
                                                        this.overloadedOverloaded_cache = s0_;
                                                        this.state_0_ = state_0 |= 1;
                                                    }
                                                    if (s0_ == null) break block62;
                                                    lock.unlock();
                                                    hasLock = false;
                                                    Object leftOperatorCounter__42 = this.doOverloadedOverloaded(arg0Value_, arg1Value_2, s0_.leftOperatorCounter_, s0_.rightOperatorCounter_, s0_.operatorImplementation_, s0_.callNode_);
                                                    if (oldState_0 != 0) {
                                                        this.checkForPolymorphicSpecialize(oldState_0);
                                                    }
                                                    return leftOperatorCounter__42;
                                                }
                                                if ((exclude & 2) != 0) break block63;
                                                int count1_ = 0;
                                                OverloadedNumberData s1_ = this.overloadedNumber_cache;
                                                if ((state_0 & 2) != 0) {
                                                    while (!(s1_ == null || arg0Value_.matchesOperatorCounter(s1_.leftOperatorCounter_) && JSGuards.isNumber(arg1Value))) {
                                                        s1_ = s1_.next_;
                                                        ++count1_;
                                                    }
                                                }
                                                if (s1_ == null && arg0Value_.matchesOperatorCounter(leftOperatorCounter__52 = arg0Value_.getOperatorCounter()) && JSGuards.isNumber(arg1Value) && count1_ < 3) {
                                                    s1_ = super.insert(new OverloadedNumberData(this.overloadedNumber_cache));
                                                    s1_.leftOperatorCounter_ = leftOperatorCounter__52;
                                                    s1_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value_, arg1Value, this.getOverloadedOperatorName());
                                                    s1_.callNode_ = s1_.insertAccessor(JSFunctionCallNode.createCall());
                                                    VarHandle.storeStoreFence();
                                                    this.overloadedNumber_cache = s1_;
                                                    this.state_0_ = state_0 |= 2;
                                                }
                                                if (s1_ == null) break block63;
                                                lock.unlock();
                                                hasLock = false;
                                                Object leftOperatorCounter__52 = this.doOverloadedNumber(arg0Value_, arg1Value, s1_.leftOperatorCounter_, s1_.operatorImplementation_, s1_.callNode_);
                                                if (oldState_0 != 0) {
                                                    this.checkForPolymorphicSpecialize(oldState_0);
                                                }
                                                return leftOperatorCounter__52;
                                            }
                                            if ((exclude & 4) != 0 || !(arg1Value instanceof BigInt)) break block64;
                                            BigInt arg1Value_3 = (BigInt)arg1Value;
                                            int count2_ = 0;
                                            OverloadedBigIntData s2_ = this.overloadedBigInt_cache;
                                            if ((state_0 & 4) != 0) {
                                                while (s2_ != null && !arg0Value_.matchesOperatorCounter(s2_.leftOperatorCounter_)) {
                                                    s2_ = s2_.next_;
                                                    ++count2_;
                                                }
                                            }
                                            if (s2_ == null && arg0Value_.matchesOperatorCounter(leftOperatorCounter__62 = arg0Value_.getOperatorCounter()) && count2_ < 3) {
                                                s2_ = super.insert(new OverloadedBigIntData(this.overloadedBigInt_cache));
                                                s2_.leftOperatorCounter_ = leftOperatorCounter__62;
                                                s2_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value_, arg1Value_3, this.getOverloadedOperatorName());
                                                s2_.callNode_ = s2_.insertAccessor(JSFunctionCallNode.createCall());
                                                VarHandle.storeStoreFence();
                                                this.overloadedBigInt_cache = s2_;
                                                this.state_0_ = state_0 |= 4;
                                            }
                                            if (s2_ == null) break block64;
                                            lock.unlock();
                                            hasLock = false;
                                            Object leftOperatorCounter__62 = this.doOverloadedBigInt(arg0Value_, arg1Value_3, s2_.leftOperatorCounter_, s2_.operatorImplementation_, s2_.callNode_);
                                            if (oldState_0 != 0) {
                                                this.checkForPolymorphicSpecialize(oldState_0);
                                            }
                                            return leftOperatorCounter__62;
                                        }
                                        if ((exclude & 8) != 0) break block65;
                                        int count3_ = 0;
                                        OverloadedStringData s3_ = this.overloadedString_cache;
                                        if ((state_0 & 8) != 0) {
                                            while (s3_ != null) {
                                                if (arg0Value_.matchesOperatorCounter(s3_.leftOperatorCounter_) && JSGuards.isString(arg1Value)) {
                                                    assert (!this.isAddition());
                                                    break;
                                                }
                                                s3_ = s3_.next_;
                                                ++count3_;
                                            }
                                        }
                                        if (s3_ == null && arg0Value_.matchesOperatorCounter(leftOperatorCounter__72 = arg0Value_.getOperatorCounter()) && JSGuards.isString(arg1Value) && !this.isAddition() && count3_ < 3) {
                                            s3_ = super.insert(new OverloadedStringData(this.overloadedString_cache));
                                            s3_.leftOperatorCounter_ = leftOperatorCounter__72;
                                            s3_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value_, arg1Value, this.getOverloadedOperatorName());
                                            s3_.callNode_ = s3_.insertAccessor(JSFunctionCallNode.createCall());
                                            VarHandle.storeStoreFence();
                                            this.overloadedString_cache = s3_;
                                            this.state_0_ = state_0 |= 8;
                                        }
                                        if (s3_ == null) break block65;
                                        lock.unlock();
                                        hasLock = false;
                                        Object leftOperatorCounter__72 = this.doOverloadedString(arg0Value_, arg1Value, s3_.leftOperatorCounter_, s3_.operatorImplementation_, s3_.callNode_);
                                        if (oldState_0 != 0) {
                                            this.checkForPolymorphicSpecialize(oldState_0);
                                        }
                                        return leftOperatorCounter__72;
                                    }
                                    if (!JSGuards.isNullOrUndefined(arg1Value)) break block61;
                                    this.state_0_ = state_0 |= 0x10;
                                    lock.unlock();
                                    hasLock = false;
                                    Object count3_ = this.doOverloadedNullish(arg0Value_, arg1Value);
                                    if (oldState_0 != 0) {
                                        this.checkForPolymorphicSpecialize(oldState_0);
                                    }
                                    return count3_;
                                }
                                if (!(arg1Value instanceof JSOverloadedOperatorsObject)) break block66;
                                arg1Value_ = (JSOverloadedOperatorsObject)arg1Value;
                                if ((exclude & 0x10) != 0) break block67;
                                int count5_ = 0;
                                NumberOverloadedData s5_ = this.numberOverloaded_cache;
                                if ((state_0 & 0x20) != 0) {
                                    while (!(s5_ == null || arg1Value_.matchesOperatorCounter(s5_.rightOperatorCounter_) && JSGuards.isNumber(arg0Value))) {
                                        s5_ = s5_.next_;
                                        ++count5_;
                                    }
                                }
                                if (s5_ == null && arg1Value_.matchesOperatorCounter(rightOperatorCounter__42 = arg1Value_.getOperatorCounter()) && JSGuards.isNumber(arg0Value) && count5_ < 3) {
                                    s5_ = super.insert(new NumberOverloadedData(this.numberOverloaded_cache));
                                    s5_.rightOperatorCounter_ = rightOperatorCounter__42;
                                    s5_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value, arg1Value_, this.getOverloadedOperatorName());
                                    s5_.callNode_ = s5_.insertAccessor(JSFunctionCallNode.createCall());
                                    VarHandle.storeStoreFence();
                                    this.numberOverloaded_cache = s5_;
                                    this.state_0_ = state_0 |= 0x20;
                                }
                                if (s5_ == null) break block67;
                                lock.unlock();
                                hasLock = false;
                                Object rightOperatorCounter__42 = this.doNumberOverloaded(arg0Value, arg1Value_, s5_.rightOperatorCounter_, s5_.operatorImplementation_, s5_.callNode_);
                                if (oldState_0 != 0) {
                                    this.checkForPolymorphicSpecialize(oldState_0);
                                }
                                return rightOperatorCounter__42;
                            }
                            if ((exclude & 0x20) != 0 || !(arg0Value instanceof BigInt)) break block68;
                            BigInt arg0Value_ = (BigInt)arg0Value;
                            int count6_ = 0;
                            BigIntOverloadedData s6_ = this.bigIntOverloaded_cache;
                            if ((state_0 & 0x40) != 0) {
                                while (s6_ != null && !arg1Value_.matchesOperatorCounter(s6_.rightOperatorCounter_)) {
                                    s6_ = s6_.next_;
                                    ++count6_;
                                }
                            }
                            if (s6_ == null && arg1Value_.matchesOperatorCounter(rightOperatorCounter__2 = arg1Value_.getOperatorCounter()) && count6_ < 3) {
                                s6_ = super.insert(new BigIntOverloadedData(this.bigIntOverloaded_cache));
                                s6_.rightOperatorCounter_ = rightOperatorCounter__2;
                                s6_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value_, arg1Value_, this.getOverloadedOperatorName());
                                s6_.callNode_ = s6_.insertAccessor(JSFunctionCallNode.createCall());
                                VarHandle.storeStoreFence();
                                this.bigIntOverloaded_cache = s6_;
                                this.state_0_ = state_0 |= 0x40;
                            }
                            if (s6_ == null) break block68;
                            lock.unlock();
                            hasLock = false;
                            Object object = this.doBigIntOverloaded(arg0Value_, arg1Value_, s6_.rightOperatorCounter_, s6_.operatorImplementation_, s6_.callNode_);
                            if (oldState_0 != 0) {
                                this.checkForPolymorphicSpecialize(oldState_0);
                            }
                            return object;
                        }
                        try {
                            int rightOperatorCounter__3;
                            if ((exclude & 0x40) != 0) break block69;
                            int count7_ = 0;
                            StringOverloadedData s7_ = this.stringOverloaded_cache;
                            if ((state_0 & 0x80) != 0) {
                                while (s7_ != null) {
                                    if (arg1Value_.matchesOperatorCounter(s7_.rightOperatorCounter_) && JSGuards.isString(arg0Value)) {
                                        assert (!this.isAddition());
                                        break;
                                    }
                                    s7_ = s7_.next_;
                                    ++count7_;
                                }
                            }
                            if (s7_ == null && arg1Value_.matchesOperatorCounter(rightOperatorCounter__3 = arg1Value_.getOperatorCounter()) && JSGuards.isString(arg0Value) && !this.isAddition() && count7_ < 3) {
                                s7_ = super.insert(new StringOverloadedData(this.stringOverloaded_cache));
                                s7_.rightOperatorCounter_ = rightOperatorCounter__3;
                                s7_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value, arg1Value_, this.getOverloadedOperatorName());
                                s7_.callNode_ = s7_.insertAccessor(JSFunctionCallNode.createCall());
                                VarHandle.storeStoreFence();
                                this.stringOverloaded_cache = s7_;
                                this.state_0_ = state_0 |= 0x80;
                            }
                            if (s7_ == null) break block69;
                            lock.unlock();
                            hasLock = false;
                            Object object = this.doStringOverloaded(arg0Value, arg1Value_, s7_.rightOperatorCounter_, s7_.operatorImplementation_, s7_.callNode_);
                            if (oldState_0 != 0) {
                                this.checkForPolymorphicSpecialize(oldState_0);
                            }
                            return object;
                        }
                        catch (Throwable throwable) {
                            if (oldState_0 != 0) {
                                this.checkForPolymorphicSpecialize(oldState_0);
                            }
                            throw throwable;
                        }
                    }
                    if (!JSGuards.isNullOrUndefined(arg0Value)) break block66;
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doNullishOverloaded(arg0Value, arg1Value_);
                    if (oldState_0 != 0) {
                        this.checkForPolymorphicSpecialize(oldState_0);
                    }
                    return object;
                }
                this.generic_callNode_ = super.insert(JSFunctionCallNode.createCall());
                this.exclude_ = exclude |= 0x7F;
                this.overloadedOverloaded_cache = null;
                this.overloadedNumber_cache = null;
                this.overloadedBigInt_cache = null;
                this.overloadedString_cache = null;
                this.numberOverloaded_cache = null;
                this.bigIntOverloaded_cache = null;
                this.stringOverloaded_cache = null;
                state_0 &= 0xFFFFFF10;
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                Object object = this.doGeneric(arg0Value, arg1Value, this.generic_callNode_);
                if (oldState_0 != 0) {
                    this.checkForPolymorphicSpecialize(oldState_0);
                }
                return object;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        private void checkForPolymorphicSpecialize(int oldState_0) {
            if ((oldState_0 & 0x200) == 0 && (this.state_0_ & 0x200) != 0) {
                this.reportPolymorphicSpecialize();
            }
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0) {
                OverloadedOverloadedData s0_ = this.overloadedOverloaded_cache;
                OverloadedNumberData s1_ = this.overloadedNumber_cache;
                OverloadedBigIntData s2_ = this.overloadedBigInt_cache;
                OverloadedStringData s3_ = this.overloadedString_cache;
                NumberOverloadedData s5_ = this.numberOverloaded_cache;
                BigIntOverloadedData s6_ = this.bigIntOverloaded_cache;
                StringOverloadedData s7_ = this.stringOverloaded_cache;
                if (!(s0_ != null && s0_.next_ != null || s1_ != null && s1_.next_ != null || s2_ != null && s2_.next_ != null || s3_ != null && s3_.next_ != null || s5_ != null && s5_.next_ != null || s6_ != null && s6_.next_ != null || s7_ != null && s7_.next_ != null)) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Object>> cached;
            Object[] data = new Object[11];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doOverloadedOverloaded";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                OverloadedOverloadedData s0_ = this.overloadedOverloaded_cache;
                while (s0_ != null) {
                    cached.add(Arrays.asList(s0_.leftOperatorCounter_, s0_.rightOperatorCounter_, s0_.operatorImplementation_, s0_.callNode_));
                    s0_ = s0_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doOverloadedNumber";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                OverloadedNumberData s1_ = this.overloadedNumber_cache;
                while (s1_ != null) {
                    cached.add(Arrays.asList(s1_.leftOperatorCounter_, s1_.operatorImplementation_, s1_.callNode_));
                    s1_ = s1_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doOverloadedBigInt";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                OverloadedBigIntData s2_ = this.overloadedBigInt_cache;
                while (s2_ != null) {
                    cached.add(Arrays.asList(s2_.leftOperatorCounter_, s2_.operatorImplementation_, s2_.callNode_));
                    s2_ = s2_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "doOverloadedString";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                OverloadedStringData s3_ = this.overloadedString_cache;
                while (s3_ != null) {
                    cached.add(Arrays.asList(s3_.leftOperatorCounter_, s3_.operatorImplementation_, s3_.callNode_));
                    s3_ = s3_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[4] = s;
            s = new Object[3];
            s[0] = "doOverloadedNullish";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            s = new Object[3];
            s[0] = "doNumberOverloaded";
            if ((state_0 & 0x20) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                NumberOverloadedData s5_ = this.numberOverloaded_cache;
                while (s5_ != null) {
                    cached.add(Arrays.asList(s5_.rightOperatorCounter_, s5_.operatorImplementation_, s5_.callNode_));
                    s5_ = s5_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 0x10) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[6] = s;
            s = new Object[3];
            s[0] = "doBigIntOverloaded";
            if ((state_0 & 0x40) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                BigIntOverloadedData s6_ = this.bigIntOverloaded_cache;
                while (s6_ != null) {
                    cached.add(Arrays.asList(s6_.rightOperatorCounter_, s6_.operatorImplementation_, s6_.callNode_));
                    s6_ = s6_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 0x20) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[7] = s;
            s = new Object[3];
            s[0] = "doStringOverloaded";
            if ((state_0 & 0x80) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                StringOverloadedData s7_ = this.stringOverloaded_cache;
                while (s7_ != null) {
                    cached.add(Arrays.asList(s7_.rightOperatorCounter_, s7_.operatorImplementation_, s7_.callNode_));
                    s7_ = s7_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = (exclude & 0x40) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[8] = s;
            s = new Object[3];
            s[0] = "doNullishOverloaded";
            s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[9] = s;
            s = new Object[3];
            s[0] = "doGeneric";
            if ((state_0 & 0x200) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.generic_callNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[10] = s;
            return Introspection.Provider.create(data);
        }

        public static JSOverloadedBinaryNode.DispatchBinaryOperatorNode create(TruffleString overloadedOperatorName) {
            return new DispatchBinaryOperatorNodeGen(overloadedOperatorName);
        }

        @GeneratedBy(value=JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
        private static final class StringOverloadedData
        extends Node {
            @Node.Child
            StringOverloadedData next_;
            @CompilerDirectives.CompilationFinal
            int rightOperatorCounter_;
            @CompilerDirectives.CompilationFinal
            Object operatorImplementation_;
            @Node.Child
            JSFunctionCallNode callNode_;

            StringOverloadedData(StringOverloadedData next_) {
                this.next_ = next_;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }

        @GeneratedBy(value=JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
        private static final class BigIntOverloadedData
        extends Node {
            @Node.Child
            BigIntOverloadedData next_;
            @CompilerDirectives.CompilationFinal
            int rightOperatorCounter_;
            @CompilerDirectives.CompilationFinal
            Object operatorImplementation_;
            @Node.Child
            JSFunctionCallNode callNode_;

            BigIntOverloadedData(BigIntOverloadedData next_) {
                this.next_ = next_;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }

        @GeneratedBy(value=JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
        private static final class NumberOverloadedData
        extends Node {
            @Node.Child
            NumberOverloadedData next_;
            @CompilerDirectives.CompilationFinal
            int rightOperatorCounter_;
            @CompilerDirectives.CompilationFinal
            Object operatorImplementation_;
            @Node.Child
            JSFunctionCallNode callNode_;

            NumberOverloadedData(NumberOverloadedData next_) {
                this.next_ = next_;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }

        @GeneratedBy(value=JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
        private static final class OverloadedStringData
        extends Node {
            @Node.Child
            OverloadedStringData next_;
            @CompilerDirectives.CompilationFinal
            int leftOperatorCounter_;
            @CompilerDirectives.CompilationFinal
            Object operatorImplementation_;
            @Node.Child
            JSFunctionCallNode callNode_;

            OverloadedStringData(OverloadedStringData next_) {
                this.next_ = next_;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }

        @GeneratedBy(value=JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
        private static final class OverloadedBigIntData
        extends Node {
            @Node.Child
            OverloadedBigIntData next_;
            @CompilerDirectives.CompilationFinal
            int leftOperatorCounter_;
            @CompilerDirectives.CompilationFinal
            Object operatorImplementation_;
            @Node.Child
            JSFunctionCallNode callNode_;

            OverloadedBigIntData(OverloadedBigIntData next_) {
                this.next_ = next_;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }

        @GeneratedBy(value=JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
        private static final class OverloadedNumberData
        extends Node {
            @Node.Child
            OverloadedNumberData next_;
            @CompilerDirectives.CompilationFinal
            int leftOperatorCounter_;
            @CompilerDirectives.CompilationFinal
            Object operatorImplementation_;
            @Node.Child
            JSFunctionCallNode callNode_;

            OverloadedNumberData(OverloadedNumberData next_) {
                this.next_ = next_;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }

        @GeneratedBy(value=JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
        private static final class OverloadedOverloadedData
        extends Node {
            @Node.Child
            OverloadedOverloadedData next_;
            @CompilerDirectives.CompilationFinal
            int leftOperatorCounter_;
            @CompilerDirectives.CompilationFinal
            int rightOperatorCounter_;
            @CompilerDirectives.CompilationFinal
            Object operatorImplementation_;
            @Node.Child
            JSFunctionCallNode callNode_;

            OverloadedOverloadedData(OverloadedOverloadedData next_) {
                this.next_ = next_;
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

    @GeneratedBy(value=JSOverloadedBinaryNode.class)
    private static final class ToNumericOperandData
    extends Node {
        @Node.Child
        JSToNumericNode toNumericOperandLeftNode_;
        @Node.Child
        JSToNumericNode toNumericOperandRightNode_;
        @Node.Child
        JSOverloadedBinaryNode.DispatchBinaryOperatorNode dispatchBinaryOperatorNode_;

        ToNumericOperandData() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }

    @GeneratedBy(value=JSOverloadedBinaryNode.class)
    private static final class ToOperandAdditionData
    extends Node {
        @Node.Child
        JSToOperandNode toOperandLeftNode_;
        @Node.Child
        JSToOperandNode toOperandRightNode_;
        @Node.Child
        JSOverloadedBinaryNode.DispatchBinaryOperatorNode dispatchBinaryOperatorNode_;
        @Node.Child
        JSToStringNode toStringLeftNode_;
        @Node.Child
        JSToStringNode toStringRightNode_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile leftStringProfile_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile rightStringProfile_;
        @Node.Child
        JSAddNode addNode_;

        ToOperandAdditionData() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }

    @GeneratedBy(value=JSOverloadedBinaryNode.class)
    private static final class ToOperandGenericData
    extends Node {
        @Node.Child
        JSToOperandNode toOperandLeftNode_;
        @Node.Child
        JSToOperandNode toOperandRightNode_;
        @Node.Child
        JSOverloadedBinaryNode.DispatchBinaryOperatorNode dispatchBinaryOperatorNode_;

        ToOperandGenericData() {
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

