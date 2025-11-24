
package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.runtime.interop.InteropFunction;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=InteropFunction.class)
public final class InteropFunctionGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    private InteropFunctionGen() {
    }

    static {
        LibraryExport.register(InteropFunction.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=InteropFunction.class)
    public static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, InteropFunction.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof InteropFunction);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof InteropFunction);
            return new Cached(receiver);
        }

        @GeneratedBy(value=InteropFunction.class)
        public static class Uncached
        extends InteropLibrary {
            private final Class<? extends InteropFunction> receiverClass_;

            protected Uncached(Object receiver) {
                this.receiverClass_ = ((InteropFunction)receiver).getClass();
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
                InteropFunction arg0Value = (InteropFunction)arg0Value_;
                return arg0Value.isIdenticalOrUndefined(arg1Value, INTEROP_LIBRARY_.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((InteropFunction)receiver).identityHashCode();
            }
        }

        @GeneratedBy(value=InteropFunction.class)
        public static class Cached
        extends InteropLibrary {
            private final Class<? extends InteropFunction> receiverClass_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @Node.Child
            private InteropLibrary thisLib_;
            @Node.Child
            private InteropLibrary otherLib_;

            protected Cached(Object receiver) {
                InteropFunction castReceiver = (InteropFunction)receiver;
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
                InteropFunction arg0Value = CompilerDirectives.castExact(arg0Value_, this.receiverClass_);
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    return arg0Value.isIdenticalOrUndefined(arg1Value, this.thisLib_, this.otherLib_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private TriState executeAndSpecialize(InteropFunction arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.thisLib_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.otherLib_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TriState triState = arg0Value.isIdenticalOrUndefined(arg1Value, this.thisLib_, this.otherLib_);
                    return triState;
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
                return NodeCost.MONOMORPHIC;
            }

            @Override
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return CompilerDirectives.castExact(receiver, this.receiverClass_).identityHashCode();
            }
        }
    }
}

