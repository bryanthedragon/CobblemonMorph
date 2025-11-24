
package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.interop.InteropMemberIterator;

@GeneratedBy(value=InteropMemberIterator.class)
final class InteropMemberIteratorGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    private InteropMemberIteratorGen() {
    }

    static {
        LibraryExport.register(InteropMemberIterator.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=InteropMemberIterator.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private static final Uncached UNCACHED = new Uncached();

        private InteropLibraryExports() {
            super(InteropLibrary.class, InteropMemberIterator.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof InteropMemberIterator);
            Uncached uncached = UNCACHED;
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof InteropMemberIterator);
            return new Cached(receiver);
        }

        @GeneratedBy(value=InteropMemberIterator.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof InteropMemberIterator) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof InteropMemberIterator;
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
                return ((InteropMemberIterator)receiver).isIterator();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasIteratorNextElement(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((InteropMemberIterator)receiver).hasIteratorNextElement();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getIteratorNextElement(Object arg0Value_) throws StopIterationException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                InteropMemberIterator arg0Value = (InteropMemberIterator)arg0Value_;
                return arg0Value.getIteratorNextElement(INTEROP_LIBRARY_.getUncached(arg0Value.iteratedObject), INTEROP_LIBRARY_.getUncached(arg0Value.keysObject));
            }
        }

        @GeneratedBy(value=InteropMemberIterator.class)
        private static final class Cached
        extends InteropLibrary {
            @Node.Child
            private InteropLibrary receiverIteratedObjectInteropLibrary_;
            @Node.Child
            private InteropLibrary receiverKeysObjectInteropLibrary_;

            protected Cached(Object receiver) {
                InteropMemberIterator castReceiver = (InteropMemberIterator)receiver;
                this.receiverIteratedObjectInteropLibrary_ = super.insert(INTEROP_LIBRARY_.create(castReceiver.iteratedObject));
                this.receiverKeysObjectInteropLibrary_ = super.insert(INTEROP_LIBRARY_.create(castReceiver.keysObject));
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof InteropMemberIterator) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                if (!(receiver instanceof InteropMemberIterator)) {
                    return false;
                }
                if (!this.receiverIteratedObjectInteropLibrary_.accepts(((InteropMemberIterator)receiver).iteratedObject)) {
                    return false;
                }
                return this.receiverKeysObjectInteropLibrary_.accepts(((InteropMemberIterator)receiver).keysObject);
            }

            @Override
            public boolean isIterator(Object receiver) {
                assert (receiver instanceof InteropMemberIterator) : "Invalid library usage. Library does not accept given receiver.";
                return ((InteropMemberIterator)receiver).isIterator();
            }

            @Override
            public boolean hasIteratorNextElement(Object receiver) throws UnsupportedMessageException {
                assert (receiver instanceof InteropMemberIterator) : "Invalid library usage. Library does not accept given receiver.";
                return ((InteropMemberIterator)receiver).hasIteratorNextElement();
            }

            @Override
            public Object getIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException, StopIterationException {
                assert (arg0Value_ instanceof InteropMemberIterator) : "Invalid library usage. Library does not accept given receiver.";
                InteropMemberIterator arg0Value = (InteropMemberIterator)arg0Value_;
                InteropLibrary objInterop__ = this.receiverIteratedObjectInteropLibrary_;
                InteropLibrary keysInterop__ = this.receiverKeysObjectInteropLibrary_;
                return arg0Value.getIteratorNextElement(objInterop__, keysInterop__);
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MONOMORPHIC;
            }
        }
    }
}

