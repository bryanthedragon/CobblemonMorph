/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.regex.runtime.nodes.StringEqualsNode;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=StringEqualsNode.class)
public final class StringEqualsNodeGen
extends StringEqualsNode {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private CacheIdentityData cacheIdentity_cache;

    private StringEqualsNodeGen() {
    }

    @Override
    @ExplodeLoop
    public boolean execute(String arg0Value, String arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
                CacheIdentityData s0_ = this.cacheIdentity_cache;
                while (s0_ != null) {
                    if (arg0Value == s0_.cachedA_ && s0_.cachedA_.equals(arg1Value)) {
                        return StringEqualsNode.cacheIdentity(arg0Value, arg1Value, s0_.cachedA_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                return StringEqualsNode.doEquals(arg0Value, arg1Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(String arg0Value, String arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
                String cachedA__;
                int count0_ = 0;
                CacheIdentityData s0_ = this.cacheIdentity_cache;
                if ((state_0 & 1) != 0) {
                    while (!(s0_ == null || arg0Value == s0_.cachedA_ && s0_.cachedA_.equals(arg1Value))) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && (cachedA__ = arg0Value).equals(arg1Value) && count0_ < 4) {
                    s0_ = new CacheIdentityData(this.cacheIdentity_cache);
                    s0_.cachedA_ = cachedA__;
                    VarHandle.storeStoreFence();
                    this.cacheIdentity_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = StringEqualsNode.cacheIdentity(arg0Value, arg1Value, s0_.cachedA_);
                    return bl;
                }
            }
            this.exclude_ = exclude |= 1;
            this.cacheIdentity_cache = null;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            boolean bl = StringEqualsNode.doEquals(arg0Value, arg1Value);
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
        CacheIdentityData s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cacheIdentity_cache) == null || s0_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static StringEqualsNode create() {
        return new StringEqualsNodeGen();
    }

    public static StringEqualsNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=StringEqualsNode.class)
    @DenyReplace
    private static final class Uncached
    extends StringEqualsNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean execute(String arg0Value, String arg1Value) {
            return StringEqualsNode.doEquals(arg0Value, arg1Value);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }
    }

    @GeneratedBy(value=StringEqualsNode.class)
    private static final class CacheIdentityData {
        @CompilerDirectives.CompilationFinal
        CacheIdentityData next_;
        @CompilerDirectives.CompilationFinal
        String cachedA_;

        CacheIdentityData(CacheIdentityData next_) {
            this.next_ = next_;
        }
    }
}

