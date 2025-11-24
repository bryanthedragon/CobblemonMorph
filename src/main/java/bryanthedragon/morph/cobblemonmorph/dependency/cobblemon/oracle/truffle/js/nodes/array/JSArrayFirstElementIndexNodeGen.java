/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.array.JSArrayElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayFirstElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayNextElementIndexNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSArrayFirstElementIndexNode.class)
public final class JSArrayFirstElementIndexNodeGen
extends JSArrayFirstElementIndexNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private WithoutHolesCachedData withoutHolesCached_cache;
    @Node.Child
    private WithHolesCachedData withHolesCached_cache;
    @Node.Child
    private WithHolesUncachedData withHolesUncached_cache;
    @Node.Child
    private JSHasPropertyNode firstObjectViaEnumeration_hasPropertyNode_;
    @Node.Child
    private JSHasPropertyNode firstObjectViaFullEnumeration_hasPropertyNode_;
    @Node.Child
    private JSHasPropertyNode object_hasPropertyNode_;

    private JSArrayFirstElementIndexNodeGen(JSContext context) {
        super(context);
    }

    @Override
    @ExplodeLoop
    public long executeLong(Object arg0Value, long arg1Value, boolean arg2Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 0x3F) != 0 && arg0Value instanceof JSDynamicObject) {
                WithHolesUncachedData s3_;
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                if ((state_0 & 1) != 0 && arg2Value && !this.hasPrototypeElements(arg0Value_)) {
                    WithoutHolesCachedData s0_ = this.withoutHolesCached_cache;
                    while (s0_ != null) {
                        if (JSArrayElementIndexNode.getArrayType(arg0Value_) == s0_.cachedArrayType_ && !s0_.cachedArrayType_.hasHoles(arg0Value_)) {
                            return this.doWithoutHolesCached(arg0Value_, arg1Value, arg2Value, s0_.cachedArrayType_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0 && arg2Value && !this.hasPrototypeElements(arg0Value_) && !JSArrayElementIndexNode.hasHoles(arg0Value_)) {
                    return this.doWithoutHolesUncached(arg0Value_, arg1Value, arg2Value);
                }
                if ((state_0 & 4) != 0 && arg2Value && !this.hasPrototypeElements(arg0Value_)) {
                    WithHolesCachedData s2_ = this.withHolesCached_cache;
                    while (s2_ != null) {
                        if (JSArrayElementIndexNode.getArrayType(arg0Value_) == s2_.cachedArrayType_ && s2_.cachedArrayType_.hasHoles(arg0Value_)) {
                            return this.doWithHolesCached(arg0Value_, arg1Value, arg2Value, s2_.cachedArrayType_, s2_.nextElementIndexNode_, s2_.isZero_);
                        }
                        s2_ = s2_.next_;
                    }
                }
                if ((state_0 & 8) != 0 && (s3_ = this.withHolesUncached_cache) != null && arg2Value && (this.hasPrototypeElements(arg0Value_) || JSArrayElementIndexNode.hasHoles(arg0Value_))) {
                    return this.doWithHolesUncached(arg0Value_, arg1Value, arg2Value, s3_.nextElementIndexNode_, s3_.isZero_, s3_.arrayTypeProfile_);
                }
                if ((state_0 & 0x10) != 0 && !arg2Value && this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg1Value)) {
                    return this.firstObjectViaEnumeration(arg0Value_, arg1Value, arg2Value, this.firstObjectViaEnumeration_hasPropertyNode_);
                }
                if ((state_0 & 0x20) != 0 && !arg2Value && !this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg1Value) && JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value_, arg1Value)) {
                    return this.firstObjectViaFullEnumeration(arg0Value_, arg1Value, arg2Value, this.firstObjectViaFullEnumeration_hasPropertyNode_);
                }
            }
            if ((state_0 & 0x40) != 0 && !arg2Value && !JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value, arg1Value)) {
                return this.doObject(arg0Value, arg1Value, arg2Value, this.object_hasPropertyNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
    }

    private long executeAndSpecialize(Object arg0Value, long arg1Value, boolean arg2Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                if ((exclude & 1) == 0 && arg2Value && !this.hasPrototypeElements(arg0Value_)) {
                    int count0_ = 0;
                    WithoutHolesCachedData s0_ = this.withoutHolesCached_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && (JSArrayElementIndexNode.getArrayType(arg0Value_) != s0_.cachedArrayType_ || s0_.cachedArrayType_.hasHoles(arg0Value_))) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null) {
                        ScriptArray cachedArrayType__ = JSArrayElementIndexNode.getArrayTypeIfArray(arg0Value_, arg2Value);
                        if (JSArrayElementIndexNode.getArrayType(arg0Value_) == cachedArrayType__ && !cachedArrayType__.hasHoles(arg0Value_) && count0_ < 4) {
                            s0_ = new WithoutHolesCachedData(this.withoutHolesCached_cache);
                            s0_.cachedArrayType_ = cachedArrayType__;
                            VarHandle.storeStoreFence();
                            this.withoutHolesCached_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        long cachedArrayType__ = this.doWithoutHolesCached(arg0Value_, arg1Value, arg2Value, s0_.cachedArrayType_);
                        return cachedArrayType__;
                    }
                }
                if (arg2Value && !this.hasPrototypeElements(arg0Value_) && !JSArrayElementIndexNode.hasHoles(arg0Value_)) {
                    this.exclude_ = exclude |= 1;
                    this.withoutHolesCached_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    long count0_ = this.doWithoutHolesUncached(arg0Value_, arg1Value, arg2Value);
                    return count0_;
                }
                if ((exclude & 2) == 0 && arg2Value && !this.hasPrototypeElements(arg0Value_)) {
                    int count2_ = 0;
                    WithHolesCachedData s2_ = this.withHolesCached_cache;
                    if ((state_0 & 4) != 0) {
                        while (!(s2_ == null || JSArrayElementIndexNode.getArrayType(arg0Value_) == s2_.cachedArrayType_ && s2_.cachedArrayType_.hasHoles(arg0Value_))) {
                            s2_ = s2_.next_;
                            ++count2_;
                        }
                    }
                    if (s2_ == null) {
                        ScriptArray cachedArrayType__1 = JSArrayElementIndexNode.getArrayTypeIfArray(arg0Value_, arg2Value);
                        if (JSArrayElementIndexNode.getArrayType(arg0Value_) == cachedArrayType__1 && cachedArrayType__1.hasHoles(arg0Value_) && count2_ < 4) {
                            s2_ = super.insert(new WithHolesCachedData(this.withHolesCached_cache));
                            s2_.cachedArrayType_ = cachedArrayType__1;
                            s2_.nextElementIndexNode_ = s2_.insertAccessor(JSArrayNextElementIndexNode.create(this.context));
                            s2_.isZero_ = ConditionProfile.createBinaryProfile();
                            VarHandle.storeStoreFence();
                            this.withHolesCached_cache = s2_;
                            this.state_0_ = state_0 |= 4;
                        }
                    }
                    if (s2_ != null) {
                        lock.unlock();
                        hasLock = false;
                        long l = this.doWithHolesCached(arg0Value_, arg1Value, arg2Value, s2_.cachedArrayType_, s2_.nextElementIndexNode_, s2_.isZero_);
                        return l;
                    }
                }
                if (arg2Value && (this.hasPrototypeElements(arg0Value_) || JSArrayElementIndexNode.hasHoles(arg0Value_))) {
                    WithHolesUncachedData s3_ = super.insert(new WithHolesUncachedData());
                    s3_.nextElementIndexNode_ = s3_.insertAccessor(JSArrayNextElementIndexNode.create(this.context));
                    s3_.isZero_ = ConditionProfile.createBinaryProfile();
                    s3_.arrayTypeProfile_ = ValueProfile.createClassProfile();
                    VarHandle.storeStoreFence();
                    this.withHolesUncached_cache = s3_;
                    this.exclude_ = exclude |= 2;
                    this.withHolesCached_cache = null;
                    state_0 &= 0xFFFFFFFB;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    long l = this.doWithHolesUncached(arg0Value_, arg1Value, arg2Value, s3_.nextElementIndexNode_, s3_.isZero_, s3_.arrayTypeProfile_);
                    return l;
                }
                if (!arg2Value && this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg1Value)) {
                    this.firstObjectViaEnumeration_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    long l = this.firstObjectViaEnumeration(arg0Value_, arg1Value, arg2Value, this.firstObjectViaEnumeration_hasPropertyNode_);
                    return l;
                }
                if (!arg2Value && !this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg1Value) && JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value_, arg1Value)) {
                    this.firstObjectViaFullEnumeration_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    long l = this.firstObjectViaFullEnumeration(arg0Value_, arg1Value, arg2Value, this.firstObjectViaFullEnumeration_hasPropertyNode_);
                    return l;
                }
            }
            if (!arg2Value && !JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value, arg1Value)) {
                this.object_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                long l = this.doObject(arg0Value, arg1Value, arg2Value, this.object_hasPropertyNode_);
                return l;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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
            WithoutHolesCachedData s0_ = this.withoutHolesCached_cache;
            WithHolesCachedData s2_ = this.withHolesCached_cache;
            if (!(s0_ != null && s0_.next_ != null || s2_ != null && s2_.next_ != null)) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[8];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doWithoutHolesCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            WithoutHolesCachedData s0_ = this.withoutHolesCached_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.cachedArrayType_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doWithoutHolesUncached";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doWithHolesCached";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            WithHolesCachedData s2_ = this.withHolesCached_cache;
            while (s2_ != null) {
                cached.add(Arrays.asList(s2_.cachedArrayType_, s2_.nextElementIndexNode_, s2_.isZero_));
                s2_ = s2_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "doWithHolesUncached";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            WithHolesUncachedData s3_ = this.withHolesUncached_cache;
            if (s3_ != null) {
                cached.add(Arrays.asList(s3_.nextElementIndexNode_, s3_.isZero_, s3_.arrayTypeProfile_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "firstObjectViaEnumeration";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.firstObjectViaEnumeration_hasPropertyNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        s = new Object[3];
        s[0] = "firstObjectViaFullEnumeration";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.firstObjectViaFullEnumeration_hasPropertyNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[6] = s;
        s = new Object[3];
        s[0] = "doObject";
        if ((state_0 & 0x40) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.object_hasPropertyNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[7] = s;
        return Introspection.Provider.create(data);
    }

    public static JSArrayFirstElementIndexNode create(JSContext context) {
        return new JSArrayFirstElementIndexNodeGen(context);
    }

    @GeneratedBy(value=JSArrayFirstElementIndexNode.class)
    private static final class WithHolesUncachedData
    extends Node {
        @Node.Child
        JSArrayNextElementIndexNode nextElementIndexNode_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile isZero_;
        @CompilerDirectives.CompilationFinal
        ValueProfile arrayTypeProfile_;

        WithHolesUncachedData() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }

    @GeneratedBy(value=JSArrayFirstElementIndexNode.class)
    private static final class WithHolesCachedData
    extends Node {
        @Node.Child
        WithHolesCachedData next_;
        @CompilerDirectives.CompilationFinal
        ScriptArray cachedArrayType_;
        @Node.Child
        JSArrayNextElementIndexNode nextElementIndexNode_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile isZero_;

        WithHolesCachedData(WithHolesCachedData next_) {
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

    @GeneratedBy(value=JSArrayFirstElementIndexNode.class)
    private static final class WithoutHolesCachedData {
        @CompilerDirectives.CompilationFinal
        WithoutHolesCachedData next_;
        @CompilerDirectives.CompilationFinal
        ScriptArray cachedArrayType_;

        WithoutHolesCachedData(WithoutHolesCachedData next_) {
            this.next_ = next_;
        }
    }
}

