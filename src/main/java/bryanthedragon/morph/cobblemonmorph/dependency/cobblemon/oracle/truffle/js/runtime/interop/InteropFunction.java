
package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@ExportLibrary(value=InteropLibrary.class)
@ImportStatic(value={JSConfig.class})
public abstract class InteropFunction
implements TruffleObject {
    private final JSFunctionObject function;

    InteropFunction(JSFunctionObject function) {
        this.function = function;
    }

    public final JSFunctionObject getFunction() {
        return this.function;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    public final TriState isIdenticalOrUndefined(Object other, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary thisLib, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary otherLib) {
        if (this == other) {
            return TriState.TRUE;
        }
        if (other instanceof JSDynamicObject) {
            return TriState.valueOf(this.function == other);
        }
        if (otherLib.hasIdentity(other)) {
            return TriState.valueOf(thisLib.isIdentical(this.function, other, otherLib));
        }
        return TriState.UNDEFINED;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    public final int identityHashCode() {
        return this.function.hashCode();
    }
}

