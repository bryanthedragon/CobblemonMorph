
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.DefaultBooleanExports;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;

@GeneratedBy(value=DefaultBooleanExports.class)
final class DefaultBooleanExportsGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private DefaultBooleanExportsGen() {
    }

    static {
        LibraryExport.register(DefaultBooleanExports.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=DefaultBooleanExports.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private static final Uncached UNCACHED = new Uncached();
        private static final Cached CACHE = new Cached();

        private InteropLibraryExports() {
            super(InteropLibrary.class, Boolean.class, true, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof Boolean);
            Uncached uncached = UNCACHED;
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof Boolean);
            return CACHE;
        }

        @GeneratedBy(value=DefaultBooleanExports.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof Boolean) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof Boolean;
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
            public boolean isBoolean(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.isBoolean((Boolean)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean asBoolean(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.asBoolean((Boolean)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.hasLanguage((Boolean)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.getLanguage((Boolean)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.hasSourceLocation((Boolean)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.getSourceLocation((Boolean)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.hasMetaObject((Boolean)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.getMetaObject((Boolean)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.toDisplayString((Boolean)receiver, allowSideEffects);
            }
        }

        @GeneratedBy(value=DefaultBooleanExports.class)
        private static final class Cached
        extends InteropLibrary {
            protected Cached() {
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof Boolean) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof Boolean;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public boolean isBoolean(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.isBoolean((Boolean)receiver);
            }

            @Override
            public boolean asBoolean(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.asBoolean((Boolean)receiver);
            }

            @Override
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.hasLanguage((Boolean)receiver);
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.getLanguage((Boolean)receiver);
            }

            @Override
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.hasSourceLocation((Boolean)receiver);
            }

            @Override
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.getSourceLocation((Boolean)receiver);
            }

            @Override
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.hasMetaObject((Boolean)receiver);
            }

            @Override
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.getMetaObject((Boolean)receiver);
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultBooleanExports.toDisplayString((Boolean)receiver, allowSideEffects);
            }
        }
    }
}

