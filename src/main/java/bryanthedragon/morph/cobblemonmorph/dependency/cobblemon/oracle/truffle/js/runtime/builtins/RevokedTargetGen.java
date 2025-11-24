
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;

@GeneratedBy(value=JSProxyObject.RevokedTarget.class)
final class RevokedTargetGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private RevokedTargetGen() {
    }

    static {
        LibraryExport.register(JSProxyObject.RevokedTarget.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSProxyObject.RevokedTarget.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private static final Uncached UNCACHED = new Uncached();
        private static final Cached CACHE = new Cached();

        private InteropLibraryExports() {
            super(InteropLibrary.class, JSProxyObject.RevokedTarget.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSProxyObject.RevokedTarget);
            Uncached uncached = UNCACHED;
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSProxyObject.RevokedTarget);
            return CACHE;
        }

        @GeneratedBy(value=JSProxyObject.RevokedTarget.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof JSProxyObject.RevokedTarget) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof JSProxyObject.RevokedTarget;
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
            public boolean isExecutable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).isExecutable();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object execute(Object receiver, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).execute(arguments);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isInstantiable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).isInstantiable();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object instantiate(Object receiver, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).instantiate(arguments);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).hasLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).getLanguage();
            }
        }

        @GeneratedBy(value=JSProxyObject.RevokedTarget.class)
        private static final class Cached
        extends InteropLibrary {
            protected Cached() {
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof JSProxyObject.RevokedTarget) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof JSProxyObject.RevokedTarget;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public boolean isExecutable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).isExecutable();
            }

            @Override
            public Object execute(Object receiver, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).execute(arguments);
            }

            @Override
            public boolean isInstantiable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).isInstantiable();
            }

            @Override
            public Object instantiate(Object receiver, Object ... arguments) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).instantiate(arguments);
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).hasLanguage();
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSProxyObject.RevokedTarget)receiver).getLanguage();
            }
        }
    }
}

