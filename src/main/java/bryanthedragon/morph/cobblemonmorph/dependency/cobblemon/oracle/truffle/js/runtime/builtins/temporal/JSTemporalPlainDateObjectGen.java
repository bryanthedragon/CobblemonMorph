
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.time.LocalDate;

@GeneratedBy(value=JSTemporalPlainDateObject.class)
public final class JSTemporalPlainDateObjectGen {
    private JSTemporalPlainDateObjectGen() {
    }

    static {
        LibraryExport.register(JSTemporalPlainDateObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSTemporalPlainDateObject.class)
    public static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSTemporalPlainDateObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSTemporalPlainDateObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSTemporalPlainDateObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSTemporalPlainDateObject.class)
        public static class Uncached
        extends JSNonProxyObjectGen.InteropLibraryExports.Uncached {
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
            public boolean isDate(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSTemporalPlainDateObject)receiver).isDate();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSTemporalPlainDateObject)receiver).asDate();
            }
        }

        @GeneratedBy(value=JSTemporalPlainDateObject.class)
        public static class Cached
        extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean isDate(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSTemporalPlainDateObject)receiver).isDate();
            }

            @Override
            public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSTemporalPlainDateObject)receiver).asDate();
            }
        }
    }
}

