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
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.GlobalScopeTDZCheckNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=GlobalScopeTDZCheckNode.class)
final class GlobalScopeTDZCheckNodeGen
extends GlobalScopeTDZCheckNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private CachedData cached_cache;
    @Node.Child
    private PropertyGetNode uncached_getNode_;
    @CompilerDirectives.CompilationFinal
    private BranchProfile uncached_deadBranch_;

    private GlobalScopeTDZCheckNodeGen(JSContext context, TruffleString varName) {
        super(context, varName);
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object scopeNodeValue_ = this.scopeNode.execute(frameValue);
        if ((state_0 & 1) != 0 && scopeNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject scopeNodeValue__ = (JSDynamicObject)scopeNodeValue_;
            CachedData s0_ = this.cached_cache;
            while (s0_ != null) {
                if (!Assumption.isValidAssumption(s0_.assumption0_)) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.removeCached_(s0_);
                    return this.executeAndSpecialize(scopeNodeValue__);
                }
                if (scopeNodeValue__.getShape() == s0_.cachedShape_) {
                    return this.doCached(scopeNodeValue__, s0_.cachedShape_, s0_.dead_);
                }
                s0_ = s0_.next_;
            }
        }
        if ((state_0 & 2) != 0) {
            return this.doUncached(scopeNodeValue_, this.uncached_getNode_, this.uncached_deadBranch_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(scopeNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Object scopeNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0 && scopeNodeValue instanceof JSDynamicObject) {
                JSDynamicObject scopeNodeValue_ = (JSDynamicObject)scopeNodeValue;
                int count0_ = 0;
                CachedData s0_ = this.cached_cache;
                if ((state_0 & 1) != 0) {
                    while (!(s0_ == null || scopeNodeValue_.getShape() == s0_.cachedShape_ && Assumption.isValidAssumption(s0_.assumption0_))) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null) {
                    Assumption assumption0;
                    Shape cachedShape__ = scopeNodeValue_.getShape();
                    if (scopeNodeValue_.getShape() == cachedShape__ && Assumption.isValidAssumption(assumption0 = cachedShape__.getValidAssumption()) && count0_ < this.context.getPropertyCacheLimit()) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedShape_ = cachedShape__;
                        s0_.dead_ = this.isDead(cachedShape__);
                        s0_.assumption0_ = assumption0;
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doCached(scopeNodeValue_, s0_.cachedShape_, s0_.dead_);
                    return object;
                }
            }
            this.uncached_getNode_ = super.insert(PropertyGetNode.create(this.varName, this.context));
            this.uncached_deadBranch_ = BranchProfile.create();
            this.exclude_ = exclude |= 1;
            this.cached_cache = null;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            Object object = this.doUncached(scopeNodeValue, this.uncached_getNode_, this.uncached_deadBranch_);
            return object;
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
        ArrayList<List<Object>> cached;
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            CachedData s0_ = this.cached_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.cachedShape_, s0_.dead_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doUncached";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.uncached_getNode_, this.uncached_deadBranch_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static GlobalScopeTDZCheckNode create(JSContext context, TruffleString varName) {
        return new GlobalScopeTDZCheckNodeGen(context, varName);
    }

    @GeneratedBy(value=GlobalScopeTDZCheckNode.class)
    private static final class CachedData {
        @CompilerDirectives.CompilationFinal
        CachedData next_;
        @CompilerDirectives.CompilationFinal
        Shape cachedShape_;
        @CompilerDirectives.CompilationFinal
        boolean dead_;
        @CompilerDirectives.CompilationFinal
        Assumption assumption0_;

        CachedData(CachedData next_) {
            this.next_ = next_;
        }
    }
}

