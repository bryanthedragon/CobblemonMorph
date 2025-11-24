/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.builtins.helper.IsPristineObjectNode;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IsPristineObjectNode.class)
public final class IsPristineObjectNodeGen
extends IsPristineObjectNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private CachedData cached_cache;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private Assumption[] dynamic_assumption0_;

    private IsPristineObjectNodeGen(JSClass jsClass, Shape initialPrototypeShape, Object ... propertyKeys) {
        super(jsClass, initialPrototypeShape, propertyKeys);
    }

    @Override
    @ExplodeLoop
    public boolean execute(JSDynamicObject arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    if (!Assumption.isValidAssumption(s0_.assumption0_)) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.removeCached_(s0_);
                        return this.executeAndSpecialize(arg0Value);
                    }
                    if (s0_.cachedShape_.check(arg0Value)) {
                        return this.doCached(arg0Value, s0_.cachedShape_, s0_.isInstanceAndDoesNotOverwriteProps_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                if (!Assumption.isValidAssumption(this.dynamic_assumption0_)) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.removeDynamic_();
                    return this.executeAndSpecialize(arg0Value);
                }
                return this.doDynamic(arg0Value);
            }
            if ((state_0 & 4) != 0) {
                return this.doAssumptionsInvalid(arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(JSDynamicObject arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            Assumption[] dynamic_assumption0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
                Assumption[] assumption0;
                Shape cachedShape__;
                int count0_ = 0;
                CachedData s0_ = this.cached_cache;
                if ((state_0 & 1) != 0) {
                    while (!(s0_ == null || s0_.cachedShape_.check(arg0Value) && Assumption.isValidAssumption(s0_.assumption0_))) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && (cachedShape__ = arg0Value.getShape()).check(arg0Value) && Assumption.isValidAssumption(assumption0 = this.getPropertyFinalAssumptions()) && count0_ < 3) {
                    s0_ = new CachedData(this.cached_cache);
                    s0_.cachedShape_ = cachedShape__;
                    s0_.isInstanceAndDoesNotOverwriteProps_ = this.isInstanceAndDoesNotOverwriteProps(cachedShape__);
                    s0_.assumption0_ = assumption0;
                    VarHandle.storeStoreFence();
                    this.cached_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doCached(arg0Value, s0_.cachedShape_, s0_.isInstanceAndDoesNotOverwriteProps_);
                    return bl;
                }
            }
            if (Assumption.isValidAssumption(dynamic_assumption0 = this.getPropertyFinalAssumptions())) {
                this.dynamic_assumption0_ = dynamic_assumption0;
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doDynamic(arg0Value);
                return bl;
            }
            this.state_0_ = state_0 |= 4;
            lock.unlock();
            hasLock = false;
            boolean bl = this.doAssumptionsInvalid(arg0Value);
            return bl;
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

    void removeDynamic_() {
        Lock lock = this.getLock();
        lock.lock();
        try {
            this.state_0_ &= 0xFFFFFFFD;
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
                cached.add(Arrays.asList(s0_.cachedShape_, s0_.isInstanceAndDoesNotOverwriteProps_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doDynamic";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doAssumptionsInvalid";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static IsPristineObjectNode create(JSClass jsClass, Shape initialPrototypeShape, Object ... propertyKeys) {
        return new IsPristineObjectNodeGen(jsClass, initialPrototypeShape, propertyKeys);
    }

    @GeneratedBy(value=IsPristineObjectNode.class)
    private static final class CachedData {
        @CompilerDirectives.CompilationFinal
        CachedData next_;
        @CompilerDirectives.CompilationFinal
        Shape cachedShape_;
        @CompilerDirectives.CompilationFinal
        boolean isInstanceAndDoesNotOverwriteProps_;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        Assumption[] assumption0_;

        CachedData(CachedData next_) {
            this.next_ = next_;
        }
    }
}

