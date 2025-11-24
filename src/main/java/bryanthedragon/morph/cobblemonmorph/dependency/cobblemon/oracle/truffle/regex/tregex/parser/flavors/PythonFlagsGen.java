
package com.oracle.truffle.regex.tregex.parser.flavors;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.regex.AbstractConstantKeysObjectGen;
import com.oracle.truffle.regex.tregex.parser.flavors.PythonFlags;

@GeneratedBy(value=PythonFlags.class)
final class PythonFlagsGen {
    private PythonFlagsGen() {
    }

    static {
        LibraryExport.register(PythonFlags.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=PythonFlags.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, PythonFlags.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof PythonFlags);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof PythonFlags);
            return new Cached(receiver);
        }

        @GeneratedBy(value=PythonFlags.class)
        @DenyReplace
        private static final class Uncached
        extends AbstractConstantKeysObjectGen.InteropLibraryExports.Uncached {
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
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PythonFlags)receiver).toDisplayString(allowSideEffects);
            }
        }

        @GeneratedBy(value=PythonFlags.class)
        private static final class Cached
        extends AbstractConstantKeysObjectGen.InteropLibraryExports.Cached {
            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((PythonFlags)receiver).toDisplayString(allowSideEffects);
            }
        }
    }
}

