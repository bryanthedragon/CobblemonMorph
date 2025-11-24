
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=GraalJSException.class)
public final class GraalJSExceptionGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    private GraalJSExceptionGen() {
    }

    static {
        LibraryExport.register(GraalJSException.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=GraalJSException.class)
    public static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, GraalJSException.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof GraalJSException);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof GraalJSException);
            return new Cached(receiver);
        }

        @GeneratedBy(value=GraalJSException.class)
        public static class Uncached
        extends InteropLibrary {
            private final Class<? extends GraalJSException> receiverClass_;

            protected Uncached(Object receiver) {
                this.receiverClass_ = ((GraalJSException)receiver).getClass();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (receiver.getClass() != this.receiverClass_ || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return CompilerDirectives.isExact(receiver, this.receiverClass_);
            }

            @Override
            public final boolean isAdoptable() {
                return false;
            }

            @Override
            public final NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                GraalJSException arg0Value = (GraalJSException)arg0Value_;
                if (arg1Value instanceof GraalJSException) {
                    GraalJSException arg1Value_ = (GraalJSException)arg1Value;
                    return GraalJSException.IsIdenticalOrUndefined.doException(arg0Value, arg1Value_, INTEROP_LIBRARY_.getUncached(), INTEROP_LIBRARY_.getUncached());
                }
                if (!GraalJSException.IsIdenticalOrUndefined.isGraalJSException(arg1Value)) {
                    return GraalJSException.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value, INTEROP_LIBRARY_.getUncached(), INTEROP_LIBRARY_.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((GraalJSException)receiver).hasSourceLocation();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((GraalJSException)receiver).getSourceLocationInterop();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((GraalJSException)receiver).hasLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((GraalJSException)receiver).getLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((GraalJSException)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int identityHashCode(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                GraalJSException arg0Value = (GraalJSException)arg0Value_;
                return arg0Value.identityHashCode(INTEROP_LIBRARY_.getUncached());
            }
        }

        @GeneratedBy(value=GraalJSException.class)
        public static class Cached
        extends InteropLibrary {
            private final Class<? extends GraalJSException> receiverClass_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private InteropLibrary thisLib;
            @Node.Child
            private InteropLibrary otherLib;
            @Node.Child
            private InteropLibrary identityHashCodeNode__identityHashCode_delegateLib_;

            protected Cached(Object receiver) {
                GraalJSException castReceiver = (GraalJSException)receiver;
                this.receiverClass_ = castReceiver.getClass();
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (receiver.getClass() != this.receiverClass_ || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return CompilerDirectives.isExact(receiver, this.receiverClass_);
            }

            @Override
            protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                GraalJSException arg0Value = CompilerDirectives.castExact(arg0Value_, this.receiverClass_);
                int state_0 = this.state_0_;
                if ((state_0 & 7) != 0) {
                    if ((state_0 & 1) != 0 && arg1Value instanceof GraalJSException) {
                        GraalJSException arg1Value_ = (GraalJSException)arg1Value;
                        return GraalJSException.IsIdenticalOrUndefined.doException(arg0Value, arg1Value_, this.thisLib, this.otherLib);
                    }
                    if ((state_0 & 2) != 0 && arg1Value instanceof JSDynamicObject) {
                        JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                        return GraalJSException.IsIdenticalOrUndefined.doJSObject(arg0Value, arg1Value_);
                    }
                    if ((state_0 & 4) != 0 && !GraalJSException.IsIdenticalOrUndefined.isGraalJSException(arg1Value)) {
                        return GraalJSException.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value, this.thisLib, this.otherLib);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isIdenticalOrUndefinedAndSpecialize(arg0Value, arg1Value);
            }

            private TriState isIdenticalOrUndefinedAndSpecialize(GraalJSException arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (arg1Value instanceof GraalJSException) {
                        GraalJSException arg1Value_ = (GraalJSException)arg1Value;
                        this.thisLib = super.insert(this.thisLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.thisLib);
                        this.otherLib = super.insert(this.otherLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.otherLib);
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        TriState triState = GraalJSException.IsIdenticalOrUndefined.doException(arg0Value, arg1Value_, this.thisLib, this.otherLib);
                        return triState;
                    }
                    if (exclude == 0 && arg1Value instanceof JSDynamicObject) {
                        JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        TriState triState = GraalJSException.IsIdenticalOrUndefined.doJSObject(arg0Value, arg1Value_);
                        return triState;
                    }
                    if (!GraalJSException.IsIdenticalOrUndefined.isGraalJSException(arg1Value)) {
                        this.thisLib = super.insert(this.thisLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.thisLib);
                        this.otherLib = super.insert(this.otherLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.otherLib);
                        this.exclude_ = exclude |= 1;
                        state_0 &= 0xFFFFFFFD;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        TriState triState = GraalJSException.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value, this.thisLib, this.otherLib);
                        return triState;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
                if ((state_0 & 7) == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & 7 & (state_0 & 7) - 1) == 0) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            @Override
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return CompilerDirectives.castExact(receiver, this.receiverClass_).hasSourceLocation();
            }

            @Override
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return CompilerDirectives.castExact(receiver, this.receiverClass_).getSourceLocationInterop();
            }

            @Override
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return CompilerDirectives.castExact(receiver, this.receiverClass_).hasLanguage();
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return CompilerDirectives.castExact(receiver, this.receiverClass_).getLanguage();
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return CompilerDirectives.castExact(receiver, this.receiverClass_).toDisplayString(allowSideEffects);
            }

            @Override
            public int identityHashCode(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                GraalJSException arg0Value = CompilerDirectives.castExact(arg0Value_, this.receiverClass_);
                int state_0 = this.state_0_;
                if ((state_0 & 8) != 0) {
                    return arg0Value.identityHashCode(this.identityHashCodeNode__identityHashCode_delegateLib_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.identityHashCodeNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private int identityHashCodeNode_AndSpecialize(GraalJSException arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.identityHashCodeNode__identityHashCode_delegateLib_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    int n = arg0Value.identityHashCode(this.identityHashCodeNode__identityHashCode_delegateLib_);
                    return n;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }
        }
    }
}

