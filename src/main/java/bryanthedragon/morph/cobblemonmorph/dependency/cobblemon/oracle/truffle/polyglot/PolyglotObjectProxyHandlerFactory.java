/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotObjectProxyHandler;
import com.oracle.truffle.polyglot.PolyglotToHostNode;
import com.oracle.truffle.polyglot.PolyglotToHostNodeGen;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=PolyglotObjectProxyHandler.class)
final class PolyglotObjectProxyHandlerFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    PolyglotObjectProxyHandlerFactory() {
    }

    @GeneratedBy(value=PolyglotObjectProxyHandler.ProxyInvokeNode.class)
    static final class ProxyInvokeNodeGen
    extends PolyglotObjectProxyHandler.ProxyInvokeNode {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private CachedMethod0Data cachedMethod0_cache;
        @Node.Child
        private CachedMethod1Data cachedMethod1_cache;

        private ProxyInvokeNodeGen() {
        }

        @Override
        @ExplodeLoop
        public Object execute(PolyglotLanguageContext arg0Value, Object arg1Value, Method arg2Value, Object[] arg3Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedMethod0Data s0_ = this.cachedMethod0_cache;
                    while (s0_ != null) {
                        if (s0_.receivers_.accepts(arg1Value) && s0_.cachedMethod_ == arg2Value) {
                            return this.doCachedMethod(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedMethod_, s0_.name_, s0_.returnClass_, s0_.returnType_, s0_.receivers_, s0_.members_, s0_.branchProfile_, s0_.toHost_, s0_.error_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    CachedMethod1Data s1_ = this.cachedMethod1_cache;
                    while (s1_ != null) {
                        if (s1_.cachedMethod_ == arg2Value) {
                            return this.cachedMethod1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value, arg3Value);
                        }
                        s1_ = s1_.next_;
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private Object cachedMethod1Boundary(int state_0, CachedMethod1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Method arg2Value, Object[] arg3Value) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary receivers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                Object object = this.doCachedMethod(arg0Value, arg1Value, arg2Value, arg3Value, s1_.cachedMethod_, s1_.name_, s1_.returnClass_, s1_.returnType_, receivers__, s1_.members_, s1_.branchProfile_, s1_.toHost_, s1_.error_);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Method arg2Value, Object[] arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    int count0_ = 0;
                    CachedMethod0Data s0_ = this.cachedMethod0_cache;
                    if ((state_0 & 1) != 0) {
                        while (!(s0_ == null || s0_.receivers_.accepts(arg1Value) && s0_.cachedMethod_ == arg2Value)) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && count0_ < Integer.MAX_VALUE) {
                        s0_ = super.insert(new CachedMethod0Data(this.cachedMethod0_cache));
                        s0_.cachedMethod_ = arg2Value;
                        s0_.name_ = arg2Value.getName();
                        s0_.returnClass_ = PolyglotObjectProxyHandler.ProxyInvokeNode.getMethodReturnType(arg2Value);
                        s0_.returnType_ = PolyglotObjectProxyHandler.ProxyInvokeNode.getMethodGenericReturnType(arg2Value);
                        s0_.receivers_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                        s0_.members_ = s0_.insertAccessor(INTEROP_LIBRARY_.createDispatched(Integer.MAX_VALUE));
                        s0_.branchProfile_ = ConditionProfile.create();
                        s0_.toHost_ = s0_.insertAccessor(PolyglotToHostNodeGen.create());
                        s0_.error_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cachedMethod0_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.doCachedMethod(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedMethod_, s0_.name_, s0_.returnClass_, s0_.returnType_, s0_.receivers_, s0_.members_, s0_.branchProfile_, s0_.toHost_, s0_.error_);
                        return object;
                    }
                }
                InteropLibrary receivers__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    int count1_ = 0;
                    CachedMethod1Data s1_ = this.cachedMethod1_cache;
                    if ((state_0 & 2) != 0) {
                        while (s1_ != null) {
                            if (s1_.cachedMethod_ == arg2Value) {
                                receivers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                                break;
                            }
                            s1_ = s1_.next_;
                            ++count1_;
                        }
                    }
                    if (s1_ == null && count1_ < Integer.MAX_VALUE) {
                        s1_ = super.insert(new CachedMethod1Data(this.cachedMethod1_cache));
                        s1_.cachedMethod_ = arg2Value;
                        s1_.name_ = arg2Value.getName();
                        s1_.returnClass_ = PolyglotObjectProxyHandler.ProxyInvokeNode.getMethodReturnType(arg2Value);
                        s1_.returnType_ = PolyglotObjectProxyHandler.ProxyInvokeNode.getMethodGenericReturnType(arg2Value);
                        receivers__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        s1_.members_ = s1_.insertAccessor(INTEROP_LIBRARY_.createDispatched(Integer.MAX_VALUE));
                        s1_.branchProfile_ = ConditionProfile.create();
                        s1_.toHost_ = s1_.insertAccessor(PolyglotToHostNodeGen.create());
                        s1_.error_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cachedMethod1_cache = s1_;
                        this.exclude_ = exclude |= 1;
                        this.cachedMethod0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                    }
                    if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.doCachedMethod(arg0Value, arg1Value, arg2Value, arg3Value, s1_.cachedMethod_, s1_.name_, s1_.returnClass_, s1_.returnType_, receivers__, s1_.members_, s1_.branchProfile_, s1_.toHost_, s1_.error_);
                        return object;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
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
                CachedMethod0Data s0_ = this.cachedMethod0_cache;
                CachedMethod1Data s1_ = this.cachedMethod1_cache;
                if (!(s0_ != null && s0_.next_ != null || s1_ != null && s1_.next_ != null)) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        public static PolyglotObjectProxyHandler.ProxyInvokeNode create() {
            return new ProxyInvokeNodeGen();
        }

        @GeneratedBy(value=PolyglotObjectProxyHandler.ProxyInvokeNode.class)
        private static final class CachedMethod1Data
        extends Node {
            @Node.Child
            CachedMethod1Data next_;
            @CompilerDirectives.CompilationFinal
            Method cachedMethod_;
            @CompilerDirectives.CompilationFinal
            String name_;
            @CompilerDirectives.CompilationFinal
            Class<?> returnClass_;
            @CompilerDirectives.CompilationFinal
            Type returnType_;
            @Node.Child
            InteropLibrary members_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile branchProfile_;
            @Node.Child
            PolyglotToHostNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            CachedMethod1Data(CachedMethod1Data next_) {
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

        @GeneratedBy(value=PolyglotObjectProxyHandler.ProxyInvokeNode.class)
        private static final class CachedMethod0Data
        extends Node {
            @Node.Child
            CachedMethod0Data next_;
            @CompilerDirectives.CompilationFinal
            Method cachedMethod_;
            @CompilerDirectives.CompilationFinal
            String name_;
            @CompilerDirectives.CompilationFinal
            Class<?> returnClass_;
            @CompilerDirectives.CompilationFinal
            Type returnType_;
            @Node.Child
            InteropLibrary receivers_;
            @Node.Child
            InteropLibrary members_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile branchProfile_;
            @Node.Child
            PolyglotToHostNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            CachedMethod0Data(CachedMethod0Data next_) {
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
}

