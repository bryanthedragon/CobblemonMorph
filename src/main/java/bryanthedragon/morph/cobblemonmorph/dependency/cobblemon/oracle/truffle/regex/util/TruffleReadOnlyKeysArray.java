
package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.regex.AbstractRegexObject;
import java.util.Arrays;

@ExportLibrary(value=InteropLibrary.class)
public class TruffleReadOnlyKeysArray
extends AbstractRegexObject {
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final String[] keys;

    @CompilerDirectives.TruffleBoundary
    public TruffleReadOnlyKeysArray(String ... keys) {
        this.keys = keys;
        Arrays.sort(this.keys);
    }

    @CompilerDirectives.TruffleBoundary
    public boolean contains(String key) {
        return Arrays.binarySearch(this.keys, key) >= 0;
    }

    @ExportMessage
    boolean hasArrayElements() {
        return true;
    }

    @ExportMessage
    boolean isArrayElementReadable(long index) {
        return index >= 0L && index < (long)this.keys.length;
    }

    @ExportMessage
    long getArraySize() {
        return this.keys.length;
    }

    @ExportMessage
    String readArrayElement(long index) throws InvalidArrayIndexException {
        if (!this.isArrayElementReadable(index)) {
            throw InvalidArrayIndexException.create(index);
        }
        return this.keys[(int)index];
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return "TRegexReadOnlyArray{keys=" + Arrays.toString(this.keys) + "}";
    }
}

