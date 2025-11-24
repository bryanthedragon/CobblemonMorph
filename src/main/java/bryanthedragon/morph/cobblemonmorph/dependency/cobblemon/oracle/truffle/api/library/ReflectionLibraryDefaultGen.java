/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.api.library;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibrary;
import com.oracle.truffle.api.library.ReflectionLibraryDefault;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ReflectionLibraryDefault.class)
final class ReflectionLibraryDefaultGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private ReflectionLibraryDefaultGen() {
    }

    static {
        LibraryExport.register(ReflectionLibraryDefault.class, new ReflectionLibraryExports());
    }

    @GeneratedBy(value=ReflectionLibraryDefault.class)
    private static final class ReflectionLibraryExports
    extends LibraryExport<ReflectionLibrary> {
        private ReflectionLibraryExports() {
            super(ReflectionLibrary.class, Object.class, true, false, 0);
        }

        @Override
        protected ReflectionLibrary createUncached(Object receiver) {
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected ReflectionLibrary createCached(Object receiver) {
            return new Cached(receiver);
        }

        @GeneratedBy(value=ReflectionLibraryDefault.class)
        @DenyReplace
        private static final class Uncached
        extends ReflectionLibrary {
            @Node.Child
            private DynamicDispatchLibrary dynamicDispatch_;
            private final Class<?> dynamicDispatchTarget_;

            protected Uncached(Object receiver) {
                this.dynamicDispatch_ = DYNAMIC_DISPATCH_LIBRARY_.getUncached(receiver);
                this.dynamicDispatchTarget_ = this.dynamicDispatch_.dispatch(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                return this.dynamicDispatch_.accepts(receiver) && this.dynamicDispatch_.dispatch(receiver) == this.dynamicDispatchTarget_;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object send(Object arg0Value, Message arg1Value, Object ... arg2Value) throws Exception {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return ReflectionLibraryDefault.Send.doSendGeneric(arg0Value, arg1Value, arg2Value);
            }
        }

        @GeneratedBy(value=ReflectionLibraryDefault.class)
        private static final class Cached
        extends ReflectionLibrary {
            @Node.Child
            private DynamicDispatchLibrary dynamicDispatch_;
            private final Class<?> dynamicDispatchTarget_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private SendCachedData sendCached_cache;

            protected Cached(Object receiver) {
                this.dynamicDispatch_ = this.insert(DYNAMIC_DISPATCH_LIBRARY_.create(receiver));
                this.dynamicDispatchTarget_ = DYNAMIC_DISPATCH_LIBRARY_.getUncached(receiver).dispatch(receiver);
            }

            @Override
            public boolean accepts(Object receiver) {
                return this.dynamicDispatch_.accepts(receiver) && this.dynamicDispatch_.dispatch(receiver) == this.dynamicDispatchTarget_;
            }

            @Override
            @ExplodeLoop
            public Object send(Object arg0Value, Message arg1Value, Object ... arg2Value) throws Exception {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        SendCachedData s0_ = this.sendCached_cache;
                        while (s0_ != null) {
                            if (arg1Value == s0_.cachedMessage_ && s0_.cachedLibrary_.accepts(arg0Value)) {
                                return ReflectionLibraryDefault.Send.doSendCached(arg0Value, arg1Value, arg2Value, s0_.cachedMessage_, s0_.cachedLibrary_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return ReflectionLibraryDefault.Send.doSendGeneric(arg0Value, arg1Value, arg2Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(Object arg0Value, Message arg1Value, Object[] arg2Value) throws Exception {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        Library cachedLibrary__;
                        int count0_ = 0;
                        SendCachedData s0_ = this.sendCached_cache;
                        if ((state_0 & 1) != 0) {
                            while (!(s0_ == null || arg1Value == s0_.cachedMessage_ && s0_.cachedLibrary_.accepts(arg0Value))) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && (cachedLibrary__ = super.insert(ReflectionLibraryDefault.Send.createLibrary(arg1Value, arg0Value))).accepts(arg0Value) && count0_ < 8) {
                            s0_ = super.insert(new SendCachedData(this.sendCached_cache));
                            s0_.cachedMessage_ = arg1Value;
                            s0_.cachedLibrary_ = s0_.insertAccessor(cachedLibrary__);
                            VarHandle.storeStoreFence();
                            this.sendCached_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object = ReflectionLibraryDefault.Send.doSendCached(arg0Value, arg1Value, arg2Value, s0_.cachedMessage_, s0_.cachedLibrary_);
                            return object;
                        }
                    }
                    this.exclude_ = exclude |= 1;
                    this.sendCached_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = ReflectionLibraryDefault.Send.doSendGeneric(arg0Value, arg1Value, arg2Value);
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
                SendCachedData s0_;
                int state_0 = this.state_0_;
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.sendCached_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            @GeneratedBy(value=ReflectionLibraryDefault.class)
            private static final class SendCachedData
            extends Node {
                @Node.Child
                SendCachedData next_;
                @CompilerDirectives.CompilationFinal
                Message cachedMessage_;
                @Node.Child
                Library cachedLibrary_;

                SendCachedData(SendCachedData next_) {
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

