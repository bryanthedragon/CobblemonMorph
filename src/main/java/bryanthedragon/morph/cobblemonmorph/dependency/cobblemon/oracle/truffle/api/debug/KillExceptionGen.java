
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.debug.KillException;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(value=KillException.class)
final class KillExceptionGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private KillExceptionGen() {
    }

    static {
        LibraryExport.register(KillException.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=KillException.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private static final Uncached UNCACHED = new Uncached();
        private static final Cached CACHE = new Cached();

        private InteropLibraryExports() {
            super(InteropLibrary.class, KillException.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof KillException);
            Uncached uncached = UNCACHED;
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof KillException);
            return CACHE;
        }

        @GeneratedBy(value=KillException.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof KillException) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof KillException;
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
            public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((KillException)receiver).getExceptionType();
            }
        }

        @GeneratedBy(value=KillException.class)
        private static final class Cached
        extends InteropLibrary {
            protected Cached() {
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof KillException) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof KillException;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((KillException)receiver).getExceptionType();
            }
        }
    }
}

