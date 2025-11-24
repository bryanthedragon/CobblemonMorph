
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.FinalBitSet;
import com.oracle.truffle.js.runtime.SafeInteger;

@GeneratedBy(value=SafeInteger.class)
final class SafeIntegerGen {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private SafeIntegerGen() {
    }

    static {
        LibraryExport.register(SafeInteger.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=SafeInteger.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        static final FinalBitSet ENABLED_MESSAGES = InteropLibraryExports.createMessageBitSet(INTEROP_LIBRARY_, "hasLanguage", "getLanguage", "toDisplayString", "hasMetaObject", "getMetaObject");
        private static final Uncached UNCACHED = new Uncached();

        private InteropLibraryExports() {
            super(InteropLibrary.class, SafeInteger.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof SafeInteger);
            InteropLibrary uncached = InteropLibraryExports.createDelegate(INTEROP_LIBRARY_, UNCACHED);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof SafeInteger);
            return InteropLibraryExports.createDelegate(INTEROP_LIBRARY_, new Cached(receiver));
        }

        @GeneratedBy(value=SafeInteger.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary
        implements LibraryExport.DelegateExport {
            protected Uncached() {
            }

            @Override
            public FinalBitSet getDelegateExportMessages() {
                return ENABLED_MESSAGES;
            }

            @Override
            public Object readDelegateExport(Object receiver_) {
                return ((SafeInteger)receiver_).value;
            }

            @Override
            public Library getDelegateExportLibrary(Object delegate_) {
                return INTEROP_LIBRARY_.getUncached(delegate_);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof SafeInteger) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof SafeInteger;
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
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((SafeInteger)receiver).hasLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((SafeInteger)receiver).getLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((SafeInteger)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((SafeInteger)receiver).hasMetaObject();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((SafeInteger)receiver).getMetaObject();
            }
        }

        @GeneratedBy(value=SafeInteger.class)
        private static final class Cached
        extends InteropLibrary
        implements LibraryExport.DelegateExport {
            @Node.Child
            private InteropLibrary receiverValueInteropLibrary_;

            protected Cached(Object receiver) {
                SafeInteger castReceiver = (SafeInteger)receiver;
                this.receiverValueInteropLibrary_ = super.insert(INTEROP_LIBRARY_.create(castReceiver.value));
            }

            @Override
            public FinalBitSet getDelegateExportMessages() {
                return ENABLED_MESSAGES;
            }

            @Override
            public Object readDelegateExport(Object receiver_) {
                return ((SafeInteger)receiver_).value;
            }

            @Override
            public Library getDelegateExportLibrary(Object delegate) {
                return this.receiverValueInteropLibrary_;
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof SafeInteger) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                if (!(receiver instanceof SafeInteger)) {
                    return false;
                }
                return this.receiverValueInteropLibrary_.accepts(((SafeInteger)receiver).value);
            }

            @Override
            public boolean hasLanguage(Object receiver) {
                assert (receiver instanceof SafeInteger) : "Invalid library usage. Library does not accept given receiver.";
                return ((SafeInteger)receiver).hasLanguage();
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (receiver instanceof SafeInteger) : "Invalid library usage. Library does not accept given receiver.";
                return ((SafeInteger)receiver).getLanguage();
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (receiver instanceof SafeInteger) : "Invalid library usage. Library does not accept given receiver.";
                return ((SafeInteger)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            public boolean hasMetaObject(Object receiver) {
                assert (receiver instanceof SafeInteger) : "Invalid library usage. Library does not accept given receiver.";
                return ((SafeInteger)receiver).hasMetaObject();
            }

            @Override
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (receiver instanceof SafeInteger) : "Invalid library usage. Library does not accept given receiver.";
                return ((SafeInteger)receiver).getMetaObject();
            }
        }
    }
}

