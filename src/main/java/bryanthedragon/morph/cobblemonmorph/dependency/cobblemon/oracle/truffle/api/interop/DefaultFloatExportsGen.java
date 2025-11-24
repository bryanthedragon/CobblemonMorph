
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.DefaultFloatExports;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;

@GeneratedBy(value=DefaultFloatExports.class)
final class DefaultFloatExportsGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private DefaultFloatExportsGen() {
    }

    static {
        LibraryExport.register(DefaultFloatExports.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=DefaultFloatExports.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private static final Uncached UNCACHED = new Uncached();
        private static final Cached CACHE = new Cached();

        private InteropLibraryExports() {
            super(InteropLibrary.class, Float.class, true, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof Float);
            Uncached uncached = UNCACHED;
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof Float);
            return CACHE;
        }

        @GeneratedBy(value=DefaultFloatExports.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof Float) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof Float;
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
                return DefaultFloatExports.fitsInByte((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInInt(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.fitsInInt((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInShort(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.fitsInShort((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInLong(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.fitsInLong((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public byte asByte(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.asByte((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public short asShort(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.asShort((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int asInt(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.asInt((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long asLong(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.asLong((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isNumber(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.isNumber((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInFloat(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.fitsInFloat((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public float asFloat(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.asFloat((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInDouble(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.fitsInDouble((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double asDouble(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.asDouble((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.hasLanguage((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.getLanguage((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.hasSourceLocation((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.getSourceLocation((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.hasMetaObject((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.getMetaObject((Float)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.toDisplayString((Float)receiver, allowSideEffects);
            }
        }

        @GeneratedBy(value=DefaultFloatExports.class)
        private static final class Cached
        extends InteropLibrary {
            protected Cached() {
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof Float) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof Float;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public boolean fitsInByte(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.fitsInByte((Float)receiver);
            }

            @Override
            public boolean fitsInInt(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.fitsInInt((Float)receiver);
            }

            @Override
            public boolean fitsInShort(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.fitsInShort((Float)receiver);
            }

            @Override
            public boolean fitsInLong(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.fitsInLong((Float)receiver);
            }

            @Override
            public byte asByte(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.asByte((Float)receiver);
            }

            @Override
            public short asShort(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.asShort((Float)receiver);
            }

            @Override
            public int asInt(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.asInt((Float)receiver);
            }

            @Override
            public long asLong(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.asLong((Float)receiver);
            }

            @Override
            public boolean isNumber(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.isNumber((Float)receiver);
            }

            @Override
            public boolean fitsInFloat(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.fitsInFloat((Float)receiver);
            }

            @Override
            public float asFloat(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.asFloat((Float)receiver);
            }

            @Override
            public boolean fitsInDouble(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.fitsInDouble((Float)receiver);
            }

            @Override
            public double asDouble(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.asDouble((Float)receiver);
            }

            @Override
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.hasLanguage((Float)receiver);
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.getLanguage((Float)receiver);
            }

            @Override
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.hasSourceLocation((Float)receiver);
            }

            @Override
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.getSourceLocation((Float)receiver);
            }

            @Override
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.hasMetaObject((Float)receiver);
            }

            @Override
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.getMetaObject((Float)receiver);
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultFloatExports.toDisplayString((Float)receiver, allowSideEffects);
            }
        }
    }
}

