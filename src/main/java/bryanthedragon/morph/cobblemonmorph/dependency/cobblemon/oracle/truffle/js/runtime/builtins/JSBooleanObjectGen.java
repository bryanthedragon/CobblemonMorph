
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.js.runtime.builtins.JSBooleanObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;

@GeneratedBy(value=JSBooleanObject.class)
final class JSBooleanObjectGen {
    private JSBooleanObjectGen() {
    }

    static {
        LibraryExport.register(JSBooleanObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSBooleanObject.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSBooleanObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSBooleanObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSBooleanObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSBooleanObject.class)
        @DenyReplace
        private static final class Uncached
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
            public boolean isBoolean(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSBooleanObject)receiver).isBoolean();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean asBoolean(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSBooleanObject)receiver).asBoolean();
            }
        }

        @GeneratedBy(value=JSBooleanObject.class)
        private static final class Cached
        extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean isBoolean(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSBooleanObject)receiver).isBoolean();
            }

            @Override
            public boolean asBoolean(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSBooleanObject)receiver).asBoolean();
            }
        }
    }
}

