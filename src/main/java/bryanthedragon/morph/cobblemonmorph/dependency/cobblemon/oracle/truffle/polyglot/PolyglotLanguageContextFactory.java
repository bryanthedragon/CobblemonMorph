/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=PolyglotLanguageContext.class)
final class PolyglotLanguageContextFactory {
    PolyglotLanguageContextFactory() {
    }

    @GeneratedBy(value=PolyglotLanguageContext.ToGuestValueNode.class)
    static final class ToGuestValueNodeGen
    extends PolyglotLanguageContext.ToGuestValueNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private ToGuestValueNodeGen() {
        }

        @Override
        @ExplodeLoop
        Object execute(PolyglotLanguageContext arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arg1Value == null) {
                    return this.doNull(arg0Value, arg1Value);
                }
                if ((state_0 & 2) != 0 && arg1Value != null) {
                    CachedData s1_ = this.cached_cache;
                    while (s1_ != null) {
                        if (arg1Value.getClass() == s1_.cachedReceiver_) {
                            return this.doCached(arg0Value, arg1Value, s1_.cachedReceiver_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 4) != 0) {
                    return this.doUncached(arg0Value, arg1Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arg1Value == null) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doNull(arg0Value, arg1Value);
                    return object;
                }
                if (exclude == 0 && arg1Value != null) {
                    int count1_ = 0;
                    CachedData s1_ = this.cached_cache;
                    if ((state_0 & 2) != 0) {
                        while (s1_ != null && arg1Value.getClass() != s1_.cachedReceiver_) {
                            s1_ = s1_.next_;
                            ++count1_;
                        }
                    }
                    if (s1_ == null) {
                        Class<?> cachedReceiver__ = arg1Value.getClass();
                        if (arg1Value.getClass() == cachedReceiver__ && count1_ < 3) {
                            s1_ = new CachedData(this.cached_cache);
                            s1_.cachedReceiver_ = cachedReceiver__;
                            VarHandle.storeStoreFence();
                            this.cached_cache = s1_;
                            this.state_0_ = state_0 |= 2;
                        }
                    }
                    if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.doCached(arg0Value, arg1Value, s1_.cachedReceiver_);
                        return object;
                    }
                }
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFD;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.doUncached(arg0Value, arg1Value);
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

        public static PolyglotLanguageContext.ToGuestValueNode create() {
            return new ToGuestValueNodeGen();
        }

        public static PolyglotLanguageContext.ToGuestValueNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=PolyglotLanguageContext.ToGuestValueNode.class)
        @DenyReplace
        private static final class Uncached
        extends PolyglotLanguageContext.ToGuestValueNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            Object execute(PolyglotLanguageContext arg0Value, Object arg1Value) {
                if (arg1Value == null) {
                    return this.doNull(arg0Value, arg1Value);
                }
                return this.doUncached(arg0Value, arg1Value);
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

        @GeneratedBy(value=PolyglotLanguageContext.ToGuestValueNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedReceiver_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }
}

