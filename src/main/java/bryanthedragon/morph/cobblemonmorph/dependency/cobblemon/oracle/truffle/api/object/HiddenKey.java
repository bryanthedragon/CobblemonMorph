
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(value=InteropLibrary.class)
public final class HiddenKey
implements TruffleObject {
    private final String name;

    public HiddenKey(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

    public int hashCode() {
        return this.name != null ? this.name.hashCode() : 0;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    String toDisplayString(boolean allowSideEffects) {
        return this.name + " (hidden)";
    }
}

