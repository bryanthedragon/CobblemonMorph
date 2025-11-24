/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotMapEntry;
import com.oracle.truffle.polyglot.PolyglotToHostNode;
import com.oracle.truffle.polyglot.PolyglotToHostNodeGen;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Type;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=PolyglotMapEntry.class)
final class PolyglotMapEntryFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    PolyglotMapEntryFactory() {
    }

    @GeneratedBy(value=PolyglotMapEntry.Cache.class)
    static final class CacheFactory {
        CacheFactory() {
        }

        @GeneratedBy(value=PolyglotMapEntry.Cache.GetNode.class)
        static final class GetNodeGen
        extends PolyglotMapEntry.Cache.GetNode {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private Cached0Data cached0_cache;
            @Node.Child
            private PolyglotToHostNode cached1_toHost_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile cached1_error_;

            private GetNodeGen(PolyglotMapEntry.Cache cache, String name, long index, Class<?> elementClass, Type elementType) {
                super(cache, name, index, elementClass, elementType);
            }

            @Override
            @ExplodeLoop
            protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        Cached0Data s0_ = this.cached0_cache;
                        while (s0_ != null) {
                            if (s0_.interop_.accepts(arg1Value)) {
                                return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toHost_, s0_.error_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @CompilerDirectives.TruffleBoundary
            private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                    Object object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toHost_, this.cached1_error_);
                    return object;
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        Cached0Data s0_ = this.cached0_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 5) {
                            s0_ = super.insert(new Cached0Data(this.cached0_cache));
                            s0_.interop_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            s0_.toHost_ = s0_.insertAccessor(PolyglotToHostNodeGen.create());
                            s0_.error_ = BranchProfile.create();
                            VarHandle.storeStoreFence();
                            this.cached0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object2 = this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toHost_, s0_.error_);
                            return object2;
                        }
                    }
                    InteropLibrary cached1_interop__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        cached1_interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                        this.cached1_toHost_ = super.insert(PolyglotToHostNodeGen.create());
                        this.cached1_error_ = BranchProfile.create();
                        this.exclude_ = exclude |= 1;
                        this.cached0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        object = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toHost_, this.cached1_error_);
                        encapsulating_.set(prev_);
                    }
                    catch (Throwable throwable) {
                        encapsulating_.set(prev_);
                        throw throwable;
                    }
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
                Cached0Data s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached0_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static PolyglotMapEntry.Cache.GetNode create(PolyglotMapEntry.Cache cache, String name, long index, Class<?> elementClass, Type elementType) {
                return new GetNodeGen(cache, name, index, elementClass, elementType);
            }

            @GeneratedBy(value=PolyglotMapEntry.Cache.GetNode.class)
            private static final class Cached0Data
            extends Node {
                @Node.Child
                Cached0Data next_;
                @Node.Child
                InteropLibrary interop_;
                @Node.Child
                PolyglotToHostNode toHost_;
                @CompilerDirectives.CompilationFinal
                BranchProfile error_;

                Cached0Data(Cached0Data next_) {
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
}

