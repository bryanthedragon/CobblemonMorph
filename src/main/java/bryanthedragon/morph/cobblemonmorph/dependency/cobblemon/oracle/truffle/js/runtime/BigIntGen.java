
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.BigInt;

@GeneratedBy(value=BigInt.class)
final class BigIntGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private BigIntGen() {
    }

    static {
        LibraryExport.register(BigInt.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=BigInt.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private static final Uncached UNCACHED = new Uncached();
        private static final Cached CACHE = new Cached();

        private InteropLibraryExports() {
            super(InteropLibrary.class, BigInt.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof BigInt);
            Uncached uncached = UNCACHED;
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof BigInt);
            return CACHE;
        }

        @GeneratedBy(value=BigInt.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof BigInt) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof BigInt;
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
                return ((BigInt)receiver).isNumber();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInByte(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).fitsInByte();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInShort(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).fitsInShort();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInInt(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).fitsInInt();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInLong(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).fitsInLong();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInDouble(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).fitsInDouble();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInFloat(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).fitsInFloat();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public byte asByte(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).asByte();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public short asShort(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).asShort();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int asInt(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).asInt();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long asLong(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).asLong();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public float asFloat(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).asFloat();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double asDouble(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).asDouble();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).hasLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).getLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).hasMetaObject();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).getMetaObject();
            }
        }

        @GeneratedBy(value=BigInt.class)
        private static final class Cached
        extends InteropLibrary {
            protected Cached() {
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof BigInt) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof BigInt;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public boolean isNumber(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).isNumber();
            }

            @Override
            public boolean fitsInByte(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).fitsInByte();
            }

            @Override
            public boolean fitsInShort(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).fitsInShort();
            }

            @Override
            public boolean fitsInInt(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).fitsInInt();
            }

            @Override
            public boolean fitsInLong(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).fitsInLong();
            }

            @Override
            public boolean fitsInDouble(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).fitsInDouble();
            }

            @Override
            public boolean fitsInFloat(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).fitsInFloat();
            }

            @Override
            public byte asByte(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).asByte();
            }

            @Override
            public short asShort(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).asShort();
            }

            @Override
            public int asInt(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).asInt();
            }

            @Override
            public long asLong(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).asLong();
            }

            @Override
            public float asFloat(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).asFloat();
            }

            @Override
            public double asDouble(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).asDouble();
            }

            @Override
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).hasLanguage();
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).getLanguage();
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).hasMetaObject();
            }

            @Override
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((BigInt)receiver).getMetaObject();
            }
        }
    }
}

