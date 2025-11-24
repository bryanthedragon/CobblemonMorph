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
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.GlobalScopeLookupNode;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=GlobalScopeLookupNode.class)
public final class GlobalScopeLookupNodeGen
extends GlobalScopeLookupNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private Assumption absent_assumption_;
    @CompilerDirectives.CompilationFinal
    private CachedData cached_cache;
    @CompilerDirectives.CompilationFinal
    private BranchProfile uncached_errorBranch_;

    private GlobalScopeLookupNodeGen(TruffleString varName, boolean write) {
        super(varName, write);
    }

    @Override
    @ExplodeLoop
    public boolean execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((state_0 & 1) != 0) {
                if (!Assumption.isValidAssumption(this.absent_assumption_)) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.removeAbsent_();
                    return this.executeAndSpecialize(arg0Value_);
                }
                return GlobalScopeLookupNode.doAbsent(arg0Value_, this.absent_assumption_);
            }
            if ((state_0 & 2) != 0) {
                CachedData s1_ = this.cached_cache;
                while (s1_ != null) {
                    if (!Assumption.isValidAssumption(s1_.assumption0_)) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.removeCached_(s1_);
                        return this.executeAndSpecialize(arg0Value_);
                    }
                    if (arg0Value_.getShape() == s1_.cachedShape_) {
                        return this.doCached(arg0Value_, s1_.cachedShape_, s1_.exists_, s1_.dead_, s1_.constAssignment_, s1_.cacheLimit_);
                    }
                    s1_ = s1_.next_;
                }
            }
            if ((state_0 & 4) != 0) {
                return this.doUncached(arg0Value_, this.uncached_errorBranch_);
            }
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
                Assumption absent_assumption__;
                Assumption absent_assumption0;
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                if ((exclude & 1) == 0 && Assumption.isValidAssumption(absent_assumption0 = (absent_assumption__ = this.getAbsentPropertyAssumption(arg0Value_.getShape())))) {
                    this.absent_assumption_ = absent_assumption__;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = GlobalScopeLookupNode.doAbsent(arg0Value_, absent_assumption__);
                    return bl;
                }
                if ((exclude & 2) == 0) {
                    int count1_ = 0;
                    CachedData s1_ = this.cached_cache;
                    if ((state_0 & 2) != 0) {
                        while (!(s1_ == null || arg0Value_.getShape() == s1_.cachedShape_ && Assumption.isValidAssumption(s1_.assumption0_))) {
                            s1_ = s1_.next_;
                            ++count1_;
                        }
                    }
                    if (s1_ == null) {
                        int cacheLimit__;
                        Assumption assumption0;
                        Shape cachedShape__ = arg0Value_.getShape();
                        if (arg0Value_.getShape() == cachedShape__ && Assumption.isValidAssumption(assumption0 = cachedShape__.getValidAssumption()) && count1_ < (cacheLimit__ = this.getPropertyCacheLimit())) {
                            s1_ = new CachedData(this.cached_cache);
                            s1_.cachedShape_ = cachedShape__;
                            s1_.exists_ = cachedShape__.hasProperty(this.varName);
                            s1_.dead_ = this.isDead(cachedShape__);
                            s1_.constAssignment_ = this.isConstAssignment(cachedShape__);
                            s1_.cacheLimit_ = cacheLimit__;
                            s1_.assumption0_ = assumption0;
                            VarHandle.storeStoreFence();
                            this.cached_cache = s1_;
                            this.exclude_ = exclude |= 1;
                            state_0 &= 0xFFFFFFFE;
                            this.state_0_ = state_0 |= 2;
                        }
                    }
                    if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doCached(arg0Value_, s1_.cachedShape_, s1_.exists_, s1_.dead_, s1_.constAssignment_, s1_.cacheLimit_);
                        return bl;
                    }
                }
                this.uncached_errorBranch_ = BranchProfile.create();
                this.exclude_ = exclude |= 3;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFC;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doUncached(arg0Value_, this.uncached_errorBranch_);
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
        CachedData s1_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s1_ = this.cached_cache) == null || s1_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    void removeAbsent_() {
        Lock lock = this.getLock();
        lock.lock();
        try {
            this.state_0_ &= 0xFFFFFFFE;
        }
        finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void removeCached_(Object s1_) {
        Lock lock = this.getLock();
        lock.lock();
        try {
            CachedData prev = null;
            CachedData cur = this.cached_cache;
            while (cur != null) {
                if (cur == s1_) {
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
                this.state_0_ &= 0xFFFFFFFD;
            }
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doAbsent";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.absent_assumption_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doCached";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            CachedData s1_ = this.cached_cache;
            while (s1_ != null) {
                cached.add(Arrays.asList(s1_.cachedShape_, s1_.exists_, s1_.dead_, s1_.constAssignment_, s1_.cacheLimit_));
                s1_ = s1_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doUncached";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.uncached_errorBranch_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static GlobalScopeLookupNode create(TruffleString varName, boolean write) {
        return new GlobalScopeLookupNodeGen(varName, write);
    }

    @GeneratedBy(value=GlobalScopeLookupNode.class)
    private static final class CachedData {
        @CompilerDirectives.CompilationFinal
        CachedData next_;
        @CompilerDirectives.CompilationFinal
        Shape cachedShape_;
        @CompilerDirectives.CompilationFinal
        boolean exists_;
        @CompilerDirectives.CompilationFinal
        boolean dead_;
        @CompilerDirectives.CompilationFinal
        boolean constAssignment_;
        @CompilerDirectives.CompilationFinal
        int cacheLimit_;
        @CompilerDirectives.CompilationFinal
        Assumption assumption0_;

        CachedData(CachedData next_) {
            this.next_ = next_;
        }
    }
}

