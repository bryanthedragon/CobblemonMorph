
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.HashIterator;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=HashIterator.class)
final class HashIteratorGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    private HashIteratorGen() {
    }

    static {
        LibraryExport.register(HashIterator.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=HashIterator.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, HashIterator.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof HashIterator);
            Uncached uncached = new Uncached();
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof HashIterator);
            return new Cached(receiver);
        }

        @GeneratedBy(value=HashIterator.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof HashIterator) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof HashIterator;
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
            public boolean isIterator(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HashIterator)receiver).isIterator();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasIteratorNextElement(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HashIterator arg0Value = (HashIterator)arg0Value_;
                return arg0Value.hasIteratorNextElement(INTEROP_LIBRARY_.getUncached(arg0Value.entriesIterator));
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException, StopIterationException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HashIterator arg0Value = (HashIterator)arg0Value_;
                return arg0Value.getIteratorNextElement(INTEROP_LIBRARY_.getUncached(arg0Value.entriesIterator), INTEROP_LIBRARY_.getUncached());
            }
        }

        @GeneratedBy(value=HashIterator.class)
        private static final class Cached
        extends InteropLibrary {
            @Node.Child
            private InteropLibrary receiverEntriesIteratorInteropLibrary_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @Node.Child
            private InteropLibrary getIteratorNextElementNode__getIteratorNextElement_arrays_;

            protected Cached(Object receiver) {
                HashIterator castReceiver = (HashIterator)receiver;
                this.receiverEntriesIteratorInteropLibrary_ = super.insert(INTEROP_LIBRARY_.create(castReceiver.entriesIterator));
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof HashIterator) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                if (!(receiver instanceof HashIterator)) {
                    return false;
                }
                return this.receiverEntriesIteratorInteropLibrary_.accepts(((HashIterator)receiver).entriesIterator);
            }

            @Override
            public boolean isIterator(Object receiver) {
                assert (receiver instanceof HashIterator) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HashIterator)receiver).isIterator();
            }

            @Override
            public boolean hasIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException {
                assert (arg0Value_ instanceof HashIterator) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HashIterator arg0Value = (HashIterator)arg0Value_;
                InteropLibrary hasIteratorNextElementNode__hasIteratorNextElement_iterators__ = this.receiverEntriesIteratorInteropLibrary_;
                return arg0Value.hasIteratorNextElement(hasIteratorNextElementNode__hasIteratorNextElement_iterators__);
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MONOMORPHIC;
            }

            @Override
            public Object getIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException, StopIterationException {
                assert (arg0Value_ instanceof HashIterator) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HashIterator arg0Value = (HashIterator)arg0Value_;
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    InteropLibrary getIteratorNextElementNode__getIteratorNextElement_iterators__ = this.receiverEntriesIteratorInteropLibrary_;
                    return arg0Value.getIteratorNextElement(getIteratorNextElementNode__getIteratorNextElement_iterators__, this.getIteratorNextElementNode__getIteratorNextElement_arrays_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getIteratorNextElementNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object getIteratorNextElementNode_AndSpecialize(HashIterator arg0Value) throws UnsupportedMessageException, StopIterationException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    InteropLibrary getIteratorNextElementNode__getIteratorNextElement_iterators__ = null;
                    getIteratorNextElementNode__getIteratorNextElement_iterators__ = this.receiverEntriesIteratorInteropLibrary_;
                    this.getIteratorNextElementNode__getIteratorNextElement_arrays_ = super.insert(INTEROP_LIBRARY_.createDispatched(1));
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.getIteratorNextElement(getIteratorNextElementNode__getIteratorNextElement_iterators__, this.getIteratorNextElementNode__getIteratorNextElement_arrays_);
                    return object;
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

