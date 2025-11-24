/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.CachedGetPropertyNode;
import com.oracle.truffle.js.nodes.access.CachedSetPropertyNode;
import com.oracle.truffle.js.nodes.access.FrequencyBasedPolymorphicAccessNode;
import com.oracle.truffle.js.nodes.access.JSProxyPropertySetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=CachedSetPropertyNode.class)
final class CachedSetPropertyNodeGen
extends CachedSetPropertyNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private CachedKeyData cachedKey_cache;
    @CompilerDirectives.CompilationFinal
    private JSClassProfile intIndex_jsclassProfile_;
    @Node.Child
    private ArrayIndexData arrayIndex_cache;
    @Node.Child
    private JSProxyPropertySetNode proxy_proxySet_;
    @Node.Child
    private GenericData generic_cache;

    private CachedSetPropertyNodeGen(JSContext context, boolean strict, boolean setOwn, boolean superProperty) {
        super(context, strict, setOwn, superProperty);
    }

    @Override
    @ExplodeLoop
    public void execute(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value, Object arg3Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            int arg1Value_;
            if ((state_0 & 1) != 0) {
                CachedKeyData s0_ = this.cachedKey_cache;
                while (s0_ != null) {
                    assert (s0_.cachedKey_ != null);
                    assert (!JSRuntime.isArrayIndex(s0_.cachedKey_));
                    if (JSRuntime.propertyKeyEquals(s0_.equalsNode_, s0_.cachedKey_, arg1Value)) {
                        this.doCachedKey(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedKey_, s0_.propertyNode_, s0_.equalsNode_);
                        return;
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0 && arg1Value instanceof Integer && JSRuntime.isArrayIndex(arg1Value_ = ((Integer)arg1Value).intValue()) && !JSGuards.isJSProxy(arg0Value)) {
                this.doIntIndex(arg0Value, arg1Value_, arg2Value, arg3Value, this.intIndex_jsclassProfile_);
                return;
            }
            if ((state_0 & 0x1C) != 0) {
                GenericData s4_;
                if ((state_0 & 4) != 0 && !JSGuards.isJSProxy(arg0Value)) {
                    ArrayIndexData s2_ = this.arrayIndex_cache;
                    while (s2_ != null) {
                        Object maybeIndex__ = s2_.toArrayIndexNode_.execute(arg1Value);
                        if (s2_.toArrayIndexNode_.isResultArrayIndex(maybeIndex__)) {
                            this.doArrayIndex(arg0Value, arg1Value, arg2Value, arg3Value, s2_.toArrayIndexNode_, maybeIndex__, s2_.jsclassProfile_);
                            return;
                        }
                        s2_ = s2_.next_;
                    }
                }
                if ((state_0 & 8) != 0 && JSGuards.isJSProxy(arg0Value)) {
                    this.doProxy(arg0Value, arg1Value, arg2Value, arg3Value, this.proxy_proxySet_);
                    return;
                }
                if ((state_0 & 0x10) != 0 && (s4_ = this.generic_cache) != null) {
                    this.doGeneric(arg0Value, arg1Value, arg2Value, arg3Value, s4_.toArrayIndexNode_, s4_.getType_, s4_.jsclassProfile_, s4_.highFrequency_, s4_.hotKey_, s4_.equalsNode_);
                    return;
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value, Object arg3Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int oldState_0;
            block32: {
                int exclude;
                block31: {
                    int state_0;
                    block30: {
                        int arg1Value_;
                        block29: {
                            TruffleString.EqualNode equalsNode__;
                            Object cachedKey__;
                            state_0 = this.state_0_;
                            exclude = this.exclude_;
                            oldState_0 = state_0;
                            if ((exclude & 1) != 0) break block29;
                            int count0_ = 0;
                            CachedKeyData s0_ = this.cachedKey_cache;
                            if ((state_0 & 1) != 0) {
                                while (s0_ != null) {
                                    assert (s0_.cachedKey_ != null);
                                    assert (!JSRuntime.isArrayIndex(s0_.cachedKey_));
                                    if (JSRuntime.propertyKeyEquals(s0_.equalsNode_, s0_.cachedKey_, arg1Value)) break;
                                    s0_ = s0_.next_;
                                    ++count0_;
                                }
                            }
                            if (s0_ == null && (cachedKey__ = CachedGetPropertyNode.cachedPropertyKey(arg1Value)) != null && !JSRuntime.isArrayIndex(cachedKey__) && JSRuntime.propertyKeyEquals(equalsNode__ = super.insert(TruffleString.EqualNode.create()), cachedKey__, arg1Value) && count0_ < 1) {
                                s0_ = super.insert(new CachedKeyData(this.cachedKey_cache));
                                s0_.cachedKey_ = cachedKey__;
                                s0_.propertyNode_ = s0_.insertAccessor(this.createSet(cachedKey__));
                                s0_.equalsNode_ = s0_.insertAccessor(equalsNode__);
                                VarHandle.storeStoreFence();
                                this.cachedKey_cache = s0_;
                                this.state_0_ = state_0 |= 1;
                            }
                            if (s0_ == null) break block29;
                            lock.unlock();
                            hasLock = false;
                            this.doCachedKey(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedKey_, s0_.propertyNode_, s0_.equalsNode_);
                            if (oldState_0 != 0) {
                                this.checkForPolymorphicSpecialize(oldState_0);
                            }
                            return;
                        }
                        if ((exclude & 2) != 0 || !(arg1Value instanceof Integer) || !JSRuntime.isArrayIndex(arg1Value_ = ((Integer)arg1Value).intValue()) || JSGuards.isJSProxy(arg0Value)) break block30;
                        this.intIndex_jsclassProfile_ = JSClassProfile.create();
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        this.doIntIndex(arg0Value, arg1Value_, arg2Value, arg3Value, this.intIndex_jsclassProfile_);
                        if (oldState_0 != 0) {
                            this.checkForPolymorphicSpecialize(oldState_0);
                        }
                        return;
                    }
                    try {
                        ToArrayIndexNode toArrayIndexNode__;
                        Object maybeIndex__ = null;
                        if ((exclude & 4) != 0 || JSGuards.isJSProxy(arg0Value)) break block31;
                        int count2_ = 0;
                        ArrayIndexData s2_ = this.arrayIndex_cache;
                        if ((state_0 & 4) != 0) {
                            while (s2_ != null && !s2_.toArrayIndexNode_.isResultArrayIndex(maybeIndex__ = s2_.toArrayIndexNode_.execute(arg1Value))) {
                                s2_ = s2_.next_;
                                ++count2_;
                            }
                        }
                        if (s2_ == null && (toArrayIndexNode__ = super.insert(ToArrayIndexNode.createNoToPropertyKey())).isResultArrayIndex(maybeIndex__ = toArrayIndexNode__.execute(arg1Value)) && count2_ < 3) {
                            s2_ = super.insert(new ArrayIndexData(this.arrayIndex_cache));
                            s2_.toArrayIndexNode_ = s2_.insertAccessor(toArrayIndexNode__);
                            s2_.jsclassProfile_ = JSClassProfile.create();
                            VarHandle.storeStoreFence();
                            this.arrayIndex_cache = s2_;
                            this.exclude_ = exclude |= 2;
                            state_0 &= 0xFFFFFFFD;
                            this.state_0_ = state_0 |= 4;
                        }
                        if (s2_ == null) break block31;
                        lock.unlock();
                        hasLock = false;
                        this.doArrayIndex(arg0Value, arg1Value, arg2Value, arg3Value, s2_.toArrayIndexNode_, maybeIndex__, s2_.jsclassProfile_);
                        if (oldState_0 != 0) {
                            this.checkForPolymorphicSpecialize(oldState_0);
                        }
                        return;
                    }
                    catch (Throwable throwable) {
                        if (oldState_0 != 0) {
                            this.checkForPolymorphicSpecialize(oldState_0);
                        }
                        throw throwable;
                    }
                }
                if ((exclude & 8) != 0 || !JSGuards.isJSProxy(arg0Value)) break block32;
                this.proxy_proxySet_ = super.insert(JSProxyPropertySetNode.create(this.context, this.strict));
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                this.doProxy(arg0Value, arg1Value, arg2Value, arg3Value, this.proxy_proxySet_);
                if (oldState_0 != 0) {
                    this.checkForPolymorphicSpecialize(oldState_0);
                }
                return;
            }
            GenericData s4_ = super.insert(new GenericData());
            s4_.toArrayIndexNode_ = s4_.insertAccessor(ToArrayIndexNode.create());
            s4_.getType_ = ConditionProfile.createBinaryProfile();
            s4_.jsclassProfile_ = JSClassProfile.create();
            s4_.highFrequency_ = ConditionProfile.createBinaryProfile();
            s4_.hotKey_ = s4_.insertAccessor(FrequencyBasedPolymorphicAccessNode.createFrequencyBasedPropertySet(this.context, this.setOwn, this.strict, this.superProperty));
            s4_.equalsNode_ = s4_.insertAccessor(TruffleString.EqualNode.create());
            VarHandle.storeStoreFence();
            this.generic_cache = s4_;
            this.exclude_ = exclude |= 0xF;
            this.cachedKey_cache = null;
            this.arrayIndex_cache = null;
            state_0 &= 0xFFFFFFF0;
            this.state_0_ = state_0 |= 0x10;
            lock.unlock();
            hasLock = false;
            this.doGeneric(arg0Value, arg1Value, arg2Value, arg3Value, s4_.toArrayIndexNode_, s4_.getType_, s4_.jsclassProfile_, s4_.highFrequency_, s4_.hotKey_, s4_.equalsNode_);
            if (oldState_0 != 0) {
                this.checkForPolymorphicSpecialize(oldState_0);
            }
            return;
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    private void checkForPolymorphicSpecialize(int oldState_0) {
        if ((oldState_0 & 0x10) == 0 && (this.state_0_ & 0x10) != 0) {
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
            CachedKeyData s0_ = this.cachedKey_cache;
            ArrayIndexData s2_ = this.arrayIndex_cache;
            if (!(s0_ != null && s0_.next_ != null || s2_ != null && s2_.next_ != null)) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[6];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doCachedKey";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            CachedKeyData s0_ = this.cachedKey_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.cachedKey_, s0_.propertyNode_, s0_.equalsNode_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doIntIndex";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.intIndex_jsclassProfile_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doArrayIndex";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ArrayIndexData s2_ = this.arrayIndex_cache;
            while (s2_ != null) {
                cached.add(Arrays.asList(s2_.toArrayIndexNode_, s2_.jsclassProfile_));
                s2_ = s2_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "doProxy";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.proxy_proxySet_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            GenericData s4_ = this.generic_cache;
            if (s4_ != null) {
                cached.add(Arrays.asList(s4_.toArrayIndexNode_, s4_.getType_, s4_.jsclassProfile_, s4_.highFrequency_, s4_.hotKey_, s4_.equalsNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        return Introspection.Provider.create(data);
    }

    public static CachedSetPropertyNode create(JSContext context, boolean strict, boolean setOwn, boolean superProperty) {
        return new CachedSetPropertyNodeGen(context, strict, setOwn, superProperty);
    }

    @GeneratedBy(value=CachedSetPropertyNode.class)
    private static final class GenericData
    extends Node {
        @Node.Child
        ToArrayIndexNode toArrayIndexNode_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile getType_;
        @CompilerDirectives.CompilationFinal
        JSClassProfile jsclassProfile_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile highFrequency_;
        @Node.Child
        FrequencyBasedPolymorphicAccessNode.FrequencyBasedPropertySetNode hotKey_;
        @Node.Child
        TruffleString.EqualNode equalsNode_;

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

    @GeneratedBy(value=CachedSetPropertyNode.class)
    private static final class ArrayIndexData
    extends Node {
        @Node.Child
        ArrayIndexData next_;
        @Node.Child
        ToArrayIndexNode toArrayIndexNode_;
        @CompilerDirectives.CompilationFinal
        JSClassProfile jsclassProfile_;

        ArrayIndexData(ArrayIndexData next_) {
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

    @GeneratedBy(value=CachedSetPropertyNode.class)
    private static final class CachedKeyData
    extends Node {
        @Node.Child
        CachedKeyData next_;
        @CompilerDirectives.CompilationFinal
        Object cachedKey_;
        @Node.Child
        PropertySetNode propertyNode_;
        @Node.Child
        TruffleString.EqualNode equalsNode_;

        CachedKeyData(CachedKeyData next_) {
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

