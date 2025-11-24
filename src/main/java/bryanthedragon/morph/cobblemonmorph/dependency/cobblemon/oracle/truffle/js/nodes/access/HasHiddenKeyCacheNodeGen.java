/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.HasHiddenKeyCacheNode;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=HasHiddenKeyCacheNode.class)
public final class HasHiddenKeyCacheNodeGen
extends HasHiddenKeyCacheNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private CachedData cached_cache;

    private HasHiddenKeyCacheNodeGen(HiddenKey key) {
        super(key);
    }

    @Override
    @ExplodeLoop
    public boolean executeHasHiddenKey(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((state_0 & 1) != 0) {
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    if (!Assumption.isValidAssumption(s0_.assumption0_)) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.removeCached_(s0_);
                        return this.executeAndSpecialize(arg0Value_);
                    }
                    if (s0_.cachedShape_.check(arg0Value_)) {
                        return HasHiddenKeyCacheNode.doCached(arg0Value_, s0_.cachedShape_, s0_.hasOwnProperty_, s0_.cacheLimit_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0 && JSGuards.isJSObject(arg0Value_)) {
                return this.doUncached(arg0Value_);
            }
        }
        if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arg0Value)) {
            return HasHiddenKeyCacheNode.doNonObject(arg0Value);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private boolean executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                if (exclude == 0) {
                    int cacheLimit__;
                    Assumption assumption0;
                    Shape cachedShape__;
                    int count0_ = 0;
                    CachedData s0_ = this.cached_cache;
                    if ((state_0 & 1) != 0) {
                        while (!(s0_ == null || s0_.cachedShape_.check(arg0Value_) && Assumption.isValidAssumption(s0_.assumption0_))) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && (cachedShape__ = arg0Value_.getShape()).check(arg0Value_) && Assumption.isValidAssumption(assumption0 = cachedShape__.getValidAssumption()) && count0_ < (cacheLimit__ = this.getPropertyCacheLimit())) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedShape_ = cachedShape__;
                        s0_.hasOwnProperty_ = this.doUncached(arg0Value_);
                        s0_.cacheLimit_ = cacheLimit__;
                        s0_.assumption0_ = assumption0;
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl = HasHiddenKeyCacheNode.doCached(arg0Value_, s0_.cachedShape_, s0_.hasOwnProperty_, s0_.cacheLimit_);
                        return bl;
                    }
                }
                if (JSGuards.isJSObject(arg0Value_)) {
                    this.exclude_ = exclude |= 1;
                    this.cached_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doUncached(arg0Value_);
                    return bl;
                }
            }
            if (!JSGuards.isJSObject(arg0Value)) {
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                boolean bl = HasHiddenKeyCacheNode.doNonObject(arg0Value);
                return bl;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        CachedData s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached_cache) == null || s0_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void removeCached_(Object s0_) {
        Lock lock = this.getLock();
        lock.lock();
        try {
            CachedData prev = null;
            CachedData cur = this.cached_cache;
            while (cur != null) {
                if (cur == s0_) {
                    if (prev == null) {
                        this.cached_cache = cur.next_;
                        break;
                    }
                    prev.next_ = cur.next_;
                    break;
                }
                prev = cur;
                cur = cur.next_;
            }
            if (this.cached_cache == null) {
                this.state_0_ &= 0xFFFFFFFE;
            }
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<List<Object>> cached = new ArrayList<List<Object>>();
            CachedData s0_ = this.cached_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.cachedShape_, s0_.hasOwnProperty_, s0_.cacheLimit_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doUncached";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doNonObject";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static HasHiddenKeyCacheNode create(HiddenKey key) {
        return new HasHiddenKeyCacheNodeGen(key);
    }

    @GeneratedBy(value=HasHiddenKeyCacheNode.class)
    private static final class CachedData {
        @CompilerDirectives.CompilationFinal
        CachedData next_;
        @CompilerDirectives.CompilationFinal
        Shape cachedShape_;
        @CompilerDirectives.CompilationFinal
        boolean hasOwnProperty_;
        @CompilerDirectives.CompilationFinal
        int cacheLimit_;
        @CompilerDirectives.CompilationFinal
        Assumption assumption0_;

        CachedData(CachedData next_) {
            this.next_ = next_;
        }
    }
}

