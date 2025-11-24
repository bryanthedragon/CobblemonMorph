
package com.oracle.truffle.polyglot;

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
import com.oracle.truffle.polyglot.DefaultLanguageView;

@GeneratedBy(value=DefaultLanguageView.class)
final class DefaultLanguageViewGen {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private DefaultLanguageViewGen() {
    }

    static {
        LibraryExport.register(DefaultLanguageView.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=DefaultLanguageView.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        static final FinalBitSet ENABLED_MESSAGES = InteropLibraryExports.createMessageBitSet(INTEROP_LIBRARY_, "hasLanguage", "toDisplayString", "getLanguage");
        private static final Uncached UNCACHED = new Uncached();

        private InteropLibraryExports() {
            super(InteropLibrary.class, DefaultLanguageView.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof DefaultLanguageView);
            InteropLibrary uncached = InteropLibraryExports.createDelegate(INTEROP_LIBRARY_, UNCACHED);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof DefaultLanguageView);
            return InteropLibraryExports.createDelegate(INTEROP_LIBRARY_, new Cached(receiver));
        }

        @GeneratedBy(value=DefaultLanguageView.class)
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
                return ((DefaultLanguageView)receiver_).delegate;
            }

            @Override
            public Library getDelegateExportLibrary(Object delegate_) {
                return INTEROP_LIBRARY_.getUncached(delegate_);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof DefaultLanguageView) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof DefaultLanguageView;
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
                return ((DefaultLanguageView)receiver).hasLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object arg0Value_, boolean arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                DefaultLanguageView arg0Value = (DefaultLanguageView)arg0Value_;
                return arg0Value.toDisplayString(arg1Value, INTEROP_LIBRARY_.getUncached(arg0Value.delegate));
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((DefaultLanguageView)receiver).getLanguage();
            }
        }

        @GeneratedBy(value=DefaultLanguageView.class)
        private static final class Cached
        extends InteropLibrary
        implements LibraryExport.DelegateExport {
            @Node.Child
            private InteropLibrary receiverDelegateInteropLibrary_;

            protected Cached(Object receiver) {
                DefaultLanguageView castReceiver = (DefaultLanguageView)receiver;
                this.receiverDelegateInteropLibrary_ = super.insert(INTEROP_LIBRARY_.create(castReceiver.delegate));
            }

            @Override
            public FinalBitSet getDelegateExportMessages() {
                return ENABLED_MESSAGES;
            }

            @Override
            public Object readDelegateExport(Object receiver_) {
                return ((DefaultLanguageView)receiver_).delegate;
            }

            @Override
            public Library getDelegateExportLibrary(Object delegate) {
                return this.receiverDelegateInteropLibrary_;
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof DefaultLanguageView) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                if (!(receiver instanceof DefaultLanguageView)) {
                    return false;
                }
                return this.receiverDelegateInteropLibrary_.accepts(((DefaultLanguageView)receiver).delegate);
            }

            @Override
            public boolean hasLanguage(Object receiver) {
                assert (receiver instanceof DefaultLanguageView) : "Invalid library usage. Library does not accept given receiver.";
                return ((DefaultLanguageView)receiver).hasLanguage();
            }

            @Override
            public Object toDisplayString(Object arg0Value_, boolean arg1Value) {
                assert (arg0Value_ instanceof DefaultLanguageView) : "Invalid library usage. Library does not accept given receiver.";
                DefaultLanguageView arg0Value = (DefaultLanguageView)arg0Value_;
                InteropLibrary toDisplayStringNode__toDisplayString_delegateLibrary__ = this.receiverDelegateInteropLibrary_;
                return arg0Value.toDisplayString(arg1Value, toDisplayStringNode__toDisplayString_delegateLibrary__);
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MONOMORPHIC;
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (receiver instanceof DefaultLanguageView) : "Invalid library usage. Library does not accept given receiver.";
                return ((DefaultLanguageView)receiver).getLanguage();
            }
        }
    }
}

