/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.interop.JSMetaType;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSMetaType.class)
final class JSMetaTypeGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    private JSMetaTypeGen() {
    }

    static {
        LibraryExport.register(JSMetaType.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSMetaType.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSMetaType.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSMetaType);
            Uncached uncached = new Uncached();
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSMetaType);
            return new Cached();
        }

        @GeneratedBy(value=JSMetaType.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof JSMetaType) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof JSMetaType;
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
            public boolean isMetaInstance(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSMetaType arg0Value = (JSMetaType)arg0Value_;
                return JSMetaType.IsMetaInstance.doGeneric(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSMetaType)receiver).hasLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSMetaType)receiver).getLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSMetaType)receiver).isMetaObject();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaQualifiedName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSMetaType)receiver).getTypeName();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaSimpleName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSMetaType)receiver).getTypeName();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSMetaType)receiver).toDisplayString(allowSideEffects);
            }
        }

        @GeneratedBy(value=JSMetaType.class)
        private static final class Cached
        extends InteropLibrary {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private CachedData cached_cache;

            protected Cached() {
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof JSMetaType) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof JSMetaType;
            }

            @Override
            @ExplodeLoop
            public boolean isMetaInstance(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSMetaType arg0Value = (JSMetaType)arg0Value_;
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0) {
                        CachedData s0_ = this.cached_cache;
                        while (s0_ != null) {
                            if (s0_.valueLib_.accepts(arg1Value) && arg0Value == s0_.cachedType_) {
                                return JSMetaType.IsMetaInstance.doCached(arg0Value, arg1Value, s0_.cachedType_, s0_.valueLib_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return JSMetaType.IsMetaInstance.doGeneric(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean executeAndSpecialize(JSMetaType arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        int count0_ = 0;
                        CachedData s0_ = this.cached_cache;
                        if ((state_0 & 1) != 0) {
                            while (!(s0_ == null || s0_.valueLib_.accepts(arg1Value) && arg0Value == s0_.cachedType_)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 3) {
                            s0_ = super.insert(new CachedData(this.cached_cache));
                            s0_.cachedType_ = arg0Value;
                            s0_.valueLib_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                            VarHandle.storeStoreFence();
                            this.cached_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean bl = JSMetaType.IsMetaInstance.doCached(arg0Value, arg1Value, s0_.cachedType_, s0_.valueLib_);
                            return bl;
                        }
                    }
                    this.exclude_ = exclude |= 1;
                    this.cached_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = JSMetaType.IsMetaInstance.doGeneric(arg0Value, arg1Value);
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

            @Override
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSMetaType)receiver).hasLanguage();
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSMetaType)receiver).getLanguage();
            }

            @Override
            public boolean isMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSMetaType)receiver).isMetaObject();
            }

            @Override
            public Object getMetaQualifiedName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSMetaType)receiver).getTypeName();
            }

            @Override
            public Object getMetaSimpleName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSMetaType)receiver).getTypeName();
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSMetaType)receiver).toDisplayString(allowSideEffects);
            }

            @GeneratedBy(value=JSMetaType.class)
            private static final class CachedData
            extends Node {
                @Node.Child
                CachedData next_;
                @CompilerDirectives.CompilationFinal
                JSMetaType cachedType_;
                @Node.Child
                InteropLibrary valueLib_;

                CachedData(CachedData next_) {
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

