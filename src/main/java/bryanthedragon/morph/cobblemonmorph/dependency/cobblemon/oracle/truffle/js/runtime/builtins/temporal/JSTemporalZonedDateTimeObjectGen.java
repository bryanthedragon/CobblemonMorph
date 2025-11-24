
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@GeneratedBy(value=JSTemporalZonedDateTimeObject.class)
public final class JSTemporalZonedDateTimeObjectGen {
    private JSTemporalZonedDateTimeObjectGen() {
    }

    static {
        LibraryExport.register(JSTemporalZonedDateTimeObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSTemporalZonedDateTimeObject.class)
    public static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSTemporalZonedDateTimeObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSTemporalZonedDateTimeObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSTemporalZonedDateTimeObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSTemporalZonedDateTimeObject.class)
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
            public boolean isTimeZone(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSTemporalZonedDateTimeObject)receiver).isTimeZone();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSTemporalZonedDateTimeObject)receiver).asTimeZone();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isDate(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSTemporalZonedDateTimeObject)receiver).isDate();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSTemporalZonedDateTimeObject)receiver).asDate();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isTime(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSTemporalZonedDateTimeObject)receiver).isTime();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSTemporalZonedDateTimeObject)receiver).asTime();
            }
        }

        @GeneratedBy(value=JSTemporalZonedDateTimeObject.class)
        public static class Cached
        extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean isTimeZone(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSTemporalZonedDateTimeObject)receiver).isTimeZone();
            }

            @Override
            public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSTemporalZonedDateTimeObject)receiver).asTimeZone();
            }

            @Override
            public boolean isDate(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSTemporalZonedDateTimeObject)receiver).isDate();
            }

            @Override
            public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSTemporalZonedDateTimeObject)receiver).asDate();
            }

            @Override
            public boolean isTime(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSTemporalZonedDateTimeObject)receiver).isTime();
            }

            @Override
            public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSTemporalZonedDateTimeObject)receiver).asTime();
            }
        }
    }
}

