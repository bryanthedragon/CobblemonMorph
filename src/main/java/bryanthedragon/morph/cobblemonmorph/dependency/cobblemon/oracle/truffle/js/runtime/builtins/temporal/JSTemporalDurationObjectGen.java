
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.time.Duration;

@GeneratedBy(value=JSTemporalDurationObject.class)
public final class JSTemporalDurationObjectGen {
    private JSTemporalDurationObjectGen() {
    }

    static {
        LibraryExport.register(JSTemporalDurationObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSTemporalDurationObject.class)
    public static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSTemporalDurationObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSTemporalDurationObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSTemporalDurationObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSTemporalDurationObject.class)
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
            public boolean isDuration(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSTemporalDurationObject)receiver).isDuration();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Duration asDuration(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSTemporalDurationObject)receiver).asDuration();
            }
        }

        @GeneratedBy(value=JSTemporalDurationObject.class)
        public static class Cached
        extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean isDuration(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSTemporalDurationObject)receiver).isDuration();
            }

            @Override
            public Duration asDuration(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSTemporalDurationObject)receiver).asDuration();
            }
        }
    }
}

