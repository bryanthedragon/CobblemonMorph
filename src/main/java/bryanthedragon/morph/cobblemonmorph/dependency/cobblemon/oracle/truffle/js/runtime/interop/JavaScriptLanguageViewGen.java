
package com.oracle.truffle.js.runtime.interop;

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
import com.oracle.truffle.js.runtime.interop.JavaScriptLanguageView;

@GeneratedBy(value=JavaScriptLanguageView.class)
final class JavaScriptLanguageViewGen {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private JavaScriptLanguageViewGen() {
    }

    static {
        LibraryExport.register(JavaScriptLanguageView.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JavaScriptLanguageView.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        static final FinalBitSet ENABLED_MESSAGES = InteropLibraryExports.createMessageBitSet(INTEROP_LIBRARY_, "hasLanguage", "getLanguage", "toDisplayString", "hasMetaObject", "getMetaObject");
        private static final Uncached UNCACHED = new Uncached();

        private InteropLibraryExports() {
            super(InteropLibrary.class, JavaScriptLanguageView.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JavaScriptLanguageView);
            InteropLibrary uncached = InteropLibraryExports.createDelegate(INTEROP_LIBRARY_, UNCACHED);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JavaScriptLanguageView);
            return InteropLibraryExports.createDelegate(INTEROP_LIBRARY_, new Cached(receiver));
        }

        @GeneratedBy(value=JavaScriptLanguageView.class)
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
                return ((JavaScriptLanguageView)receiver_).delegate;
            }

            @Override
            public Library getDelegateExportLibrary(Object delegate_) {
                return INTEROP_LIBRARY_.getUncached(delegate_);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof JavaScriptLanguageView) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof JavaScriptLanguageView;
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
                return ((JavaScriptLanguageView)receiver).hasLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JavaScriptLanguageView)receiver).getLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JavaScriptLanguageView)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaObject(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JavaScriptLanguageView arg0Value = (JavaScriptLanguageView)arg0Value_;
                return arg0Value.hasMetaObject(INTEROP_LIBRARY_.getUncached(arg0Value.delegate));
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaObject(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JavaScriptLanguageView arg0Value = (JavaScriptLanguageView)arg0Value_;
                return arg0Value.getMetaObject(INTEROP_LIBRARY_.getUncached(arg0Value.delegate));
            }
        }

        @GeneratedBy(value=JavaScriptLanguageView.class)
        private static final class Cached
        extends InteropLibrary
        implements LibraryExport.DelegateExport {
            @Node.Child
            private InteropLibrary receiverDelegateInteropLibrary_;

            protected Cached(Object receiver) {
                JavaScriptLanguageView castReceiver = (JavaScriptLanguageView)receiver;
                this.receiverDelegateInteropLibrary_ = super.insert(INTEROP_LIBRARY_.create(castReceiver.delegate));
            }

            @Override
            public FinalBitSet getDelegateExportMessages() {
                return ENABLED_MESSAGES;
            }

            @Override
            public Object readDelegateExport(Object receiver_) {
                return ((JavaScriptLanguageView)receiver_).delegate;
            }

            @Override
            public Library getDelegateExportLibrary(Object delegate) {
                return this.receiverDelegateInteropLibrary_;
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof JavaScriptLanguageView) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                if (!(receiver instanceof JavaScriptLanguageView)) {
                    return false;
                }
                return this.receiverDelegateInteropLibrary_.accepts(((JavaScriptLanguageView)receiver).delegate);
            }

            @Override
            public boolean hasLanguage(Object receiver) {
                assert (receiver instanceof JavaScriptLanguageView) : "Invalid library usage. Library does not accept given receiver.";
                return ((JavaScriptLanguageView)receiver).hasLanguage();
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (receiver instanceof JavaScriptLanguageView) : "Invalid library usage. Library does not accept given receiver.";
                return ((JavaScriptLanguageView)receiver).getLanguage();
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (receiver instanceof JavaScriptLanguageView) : "Invalid library usage. Library does not accept given receiver.";
                return ((JavaScriptLanguageView)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            public boolean hasMetaObject(Object arg0Value_) {
                assert (arg0Value_ instanceof JavaScriptLanguageView) : "Invalid library usage. Library does not accept given receiver.";
                JavaScriptLanguageView arg0Value = (JavaScriptLanguageView)arg0Value_;
                InteropLibrary hasMetaObjectNode__hasMetaObject_delegateLibrary__ = this.receiverDelegateInteropLibrary_;
                return arg0Value.hasMetaObject(hasMetaObjectNode__hasMetaObject_delegateLibrary__);
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MONOMORPHIC;
            }

            @Override
            public Object getMetaObject(Object arg0Value_) throws UnsupportedMessageException {
                assert (arg0Value_ instanceof JavaScriptLanguageView) : "Invalid library usage. Library does not accept given receiver.";
                JavaScriptLanguageView arg0Value = (JavaScriptLanguageView)arg0Value_;
                InteropLibrary getMetaObjectNode__getMetaObject_delegateLibrary__ = this.receiverDelegateInteropLibrary_;
                return arg0Value.getMetaObject(getMetaObjectNode__getMetaObject_delegateLibrary__);
            }
        }
    }
}

