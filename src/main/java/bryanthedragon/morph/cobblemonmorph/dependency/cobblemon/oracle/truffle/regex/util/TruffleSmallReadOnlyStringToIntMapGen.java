
package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.regex.AbstractRegexObjectGen;
import com.oracle.truffle.regex.util.TruffleSmallReadOnlyStringToIntMap;

@GeneratedBy(value=TruffleSmallReadOnlyStringToIntMap.class)
final class TruffleSmallReadOnlyStringToIntMapGen {
    private TruffleSmallReadOnlyStringToIntMapGen() {
    }

    static {
        LibraryExport.register(TruffleSmallReadOnlyStringToIntMap.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=TruffleSmallReadOnlyStringToIntMap.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, TruffleSmallReadOnlyStringToIntMap.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof TruffleSmallReadOnlyStringToIntMap);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof TruffleSmallReadOnlyStringToIntMap);
            return new Cached(receiver);
        }

        @GeneratedBy(value=TruffleSmallReadOnlyStringToIntMap.class)
        @DenyReplace
        private static final class Uncached
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
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleSmallReadOnlyStringToIntMap)receiver).hasMembers();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleSmallReadOnlyStringToIntMap)receiver).getMembers(includeInternal);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberReadable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleSmallReadOnlyStringToIntMap)receiver).isMemberReadable(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleSmallReadOnlyStringToIntMap)receiver).readMember(member);
            }
        }

        @GeneratedBy(value=TruffleSmallReadOnlyStringToIntMap.class)
        private static final class Cached
        extends AbstractRegexObjectGen.InteropLibraryExports.Cached {
            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleSmallReadOnlyStringToIntMap)receiver).hasMembers();
            }

            @Override
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleSmallReadOnlyStringToIntMap)receiver).getMembers(includeInternal);
            }

            @Override
            public boolean isMemberReadable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleSmallReadOnlyStringToIntMap)receiver).isMemberReadable(member);
            }

            @Override
            public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((TruffleSmallReadOnlyStringToIntMap)receiver).readMember(member);
            }
        }
    }
}

