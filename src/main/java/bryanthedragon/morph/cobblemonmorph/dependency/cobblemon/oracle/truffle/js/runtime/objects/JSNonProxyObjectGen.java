
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.objects.JSObjectGen;

@GeneratedBy(value=JSNonProxyObject.class)
public final class JSNonProxyObjectGen {
    private JSNonProxyObjectGen() {
    }

    static {
        LibraryExport.register(JSNonProxyObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSNonProxyObject.class)
    public static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSNonProxyObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSNonProxyObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSNonProxyObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSNonProxyObject.class)
        public static class Uncached
        extends JSObjectGen.InteropLibraryExports.Uncached {
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
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSNonProxyObject)receiver).hasMetaObject();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSNonProxyObject)receiver).getMetaObject();
            }
        }

        @GeneratedBy(value=JSNonProxyObject.class)
        public static class Cached
        extends JSObjectGen.InteropLibraryExports.Cached {
            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSNonProxyObject)receiver).hasMetaObject();
            }

            @Override
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSNonProxyObject)receiver).getMetaObject();
            }
        }
    }
}

