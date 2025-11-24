
package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.regex.AbstractRegexObjectGen;
import com.oracle.truffle.regex.util.TruffleReadOnlyKeysArray;

@GeneratedBy(value=TruffleReadOnlyKeysArray.class)
public final class TruffleReadOnlyKeysArrayGen {
    private TruffleReadOnlyKeysArrayGen() {
    }

    static {
        LibraryExport.register(TruffleReadOnlyKeysArray.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=TruffleReadOnlyKeysArray.class)
    public static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, TruffleReadOnlyKeysArray.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof TruffleReadOnlyKeysArray);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof TruffleReadOnlyKeysArray);
            return new Cached(receiver);
        }

        @GeneratedBy(value=TruffleReadOnlyKeysArray.class)
        public static class Uncached
        extends AbstractRegexObjectGen.InteropLibraryExports.Uncached {
            protected Uncached(Object receiver) {
                super(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                return super.accepts(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasArrayElements(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleReadOnlyKeysArray)receiver).hasArrayElements();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isArrayElementReadable(Object receiver, long index) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleReadOnlyKeysArray)receiver).isArrayElementReadable(index);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getArraySize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleReadOnlyKeysArray)receiver).getArraySize();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleReadOnlyKeysArray)receiver).readArrayElement(index);
            }
        }

        @GeneratedBy(value=TruffleReadOnlyKeysArray.class)
        public static class Cached
        extends AbstractRegexObjectGen.InteropLibraryExports.Cached {
            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean hasArrayElements(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleReadOnlyKeysArray)receiver).hasArrayElements();
            }

            @Override
            public boolean isArrayElementReadable(Object receiver, long index) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleReadOnlyKeysArray)receiver).isArrayElementReadable(index);
            }

            @Override
            public long getArraySize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleReadOnlyKeysArray)receiver).getArraySize();
            }

            @Override
            public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleReadOnlyKeysArray)receiver).readArrayElement(index);
            }
        }
    }
}

