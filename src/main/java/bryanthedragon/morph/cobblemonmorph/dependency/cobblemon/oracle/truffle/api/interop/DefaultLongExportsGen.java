
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.DefaultLongExports;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;

@GeneratedBy(value=DefaultLongExports.class)
final class DefaultLongExportsGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private DefaultLongExportsGen() {
    }

    static {
        LibraryExport.register(DefaultLongExports.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=DefaultLongExports.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private static final Uncached UNCACHED = new Uncached();
        private static final Cached CACHE = new Cached();

        private InteropLibraryExports() {
            super(InteropLibrary.class, Long.class, true, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof Long);
            Uncached uncached = UNCACHED;
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof Long);
            return CACHE;
        }

        @GeneratedBy(value=DefaultLongExports.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof Long) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof Long;
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
            public boolean fitsInByte(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.fitsInByte((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInInt(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.fitsInInt((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInShort(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.fitsInShort((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInFloat(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.fitsInFloat((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInDouble(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.fitsInDouble((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public byte asByte(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.asByte((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public short asShort(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.asShort((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int asInt(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.asInt((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public float asFloat(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.asFloat((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double asDouble(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.asDouble((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isNumber(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.isNumber((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInLong(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.fitsInLong((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long asLong(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.asLong((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.hasLanguage((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.getLanguage((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.hasSourceLocation((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.getSourceLocation((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.hasMetaObject((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.getMetaObject((Long)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.toDisplayString((Long)receiver, allowSideEffects);
            }
        }

        @GeneratedBy(value=DefaultLongExports.class)
        private static final class Cached
        extends InteropLibrary {
            protected Cached() {
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof Long) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof Long;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public boolean fitsInByte(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.fitsInByte((Long)receiver);
            }

            @Override
            public boolean fitsInInt(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.fitsInInt((Long)receiver);
            }

            @Override
            public boolean fitsInShort(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.fitsInShort((Long)receiver);
            }

            @Override
            public boolean fitsInFloat(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.fitsInFloat((Long)receiver);
            }

            @Override
            public boolean fitsInDouble(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.fitsInDouble((Long)receiver);
            }

            @Override
            public byte asByte(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.asByte((Long)receiver);
            }

            @Override
            public short asShort(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.asShort((Long)receiver);
            }

            @Override
            public int asInt(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.asInt((Long)receiver);
            }

            @Override
            public float asFloat(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.asFloat((Long)receiver);
            }

            @Override
            public double asDouble(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.asDouble((Long)receiver);
            }

            @Override
            public boolean isNumber(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.isNumber((Long)receiver);
            }

            @Override
            public boolean fitsInLong(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.fitsInLong((Long)receiver);
            }

            @Override
            public long asLong(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.asLong((Long)receiver);
            }

            @Override
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.hasLanguage((Long)receiver);
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.getLanguage((Long)receiver);
            }

            @Override
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.hasSourceLocation((Long)receiver);
            }

            @Override
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.getSourceLocation((Long)receiver);
            }

            @Override
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.hasMetaObject((Long)receiver);
            }

            @Override
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.getMetaObject((Long)receiver);
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultLongExports.toDisplayString((Long)receiver, allowSideEffects);
            }
        }
    }
}

