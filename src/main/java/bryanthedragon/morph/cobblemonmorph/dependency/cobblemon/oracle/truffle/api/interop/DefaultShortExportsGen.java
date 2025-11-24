
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.DefaultShortExports;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;

@GeneratedBy(value=DefaultShortExports.class)
final class DefaultShortExportsGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private DefaultShortExportsGen() {
    }

    static {
        LibraryExport.register(DefaultShortExports.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=DefaultShortExports.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private static final Uncached UNCACHED = new Uncached();
        private static final Cached CACHE = new Cached();

        private InteropLibraryExports() {
            super(InteropLibrary.class, Short.class, true, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof Short);
            Uncached uncached = UNCACHED;
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof Short);
            return CACHE;
        }

        @GeneratedBy(value=DefaultShortExports.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof Short) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof Short;
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
            public boolean isNumber(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.isNumber((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInByte(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.fitsInByte((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public byte asByte(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.asByte((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInInt(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.fitsInInt((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInShort(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.fitsInShort((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInLong(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.fitsInLong((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInFloat(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.fitsInFloat((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInDouble(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.fitsInDouble((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public short asShort(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.asShort((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int asInt(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.asInt((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long asLong(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.asLong((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public float asFloat(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.asFloat((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double asDouble(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.asDouble((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.hasLanguage((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.getLanguage((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.hasSourceLocation((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.getSourceLocation((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.hasMetaObject((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.getMetaObject((Short)receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.toDisplayString((Short)receiver, allowSideEffects);
            }
        }

        @GeneratedBy(value=DefaultShortExports.class)
        private static final class Cached
        extends InteropLibrary {
            protected Cached() {
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof Short) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof Short;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public boolean isNumber(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.isNumber((Short)receiver);
            }

            @Override
            public boolean fitsInByte(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.fitsInByte((Short)receiver);
            }

            @Override
            public byte asByte(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.asByte((Short)receiver);
            }

            @Override
            public boolean fitsInInt(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.fitsInInt((Short)receiver);
            }

            @Override
            public boolean fitsInShort(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.fitsInShort((Short)receiver);
            }

            @Override
            public boolean fitsInLong(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.fitsInLong((Short)receiver);
            }

            @Override
            public boolean fitsInFloat(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.fitsInFloat((Short)receiver);
            }

            @Override
            public boolean fitsInDouble(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.fitsInDouble((Short)receiver);
            }

            @Override
            public short asShort(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.asShort((Short)receiver);
            }

            @Override
            public int asInt(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.asInt((Short)receiver);
            }

            @Override
            public long asLong(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.asLong((Short)receiver);
            }

            @Override
            public float asFloat(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.asFloat((Short)receiver);
            }

            @Override
            public double asDouble(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.asDouble((Short)receiver);
            }

            @Override
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.hasLanguage((Short)receiver);
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.getLanguage((Short)receiver);
            }

            @Override
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.hasSourceLocation((Short)receiver);
            }

            @Override
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.getSourceLocation((Short)receiver);
            }

            @Override
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.hasMetaObject((Short)receiver);
            }

            @Override
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.getMetaObject((Short)receiver);
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return DefaultShortExports.toDisplayString((Short)receiver, allowSideEffects);
            }
        }
    }
}

