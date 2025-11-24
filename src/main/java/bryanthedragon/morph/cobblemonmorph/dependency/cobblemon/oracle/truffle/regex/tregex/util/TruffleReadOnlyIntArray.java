
package com.oracle.truffle.regex.tregex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.regex.AbstractRegexObject;
import java.util.Arrays;

@ExportLibrary(value=InteropLibrary.class)
public class TruffleReadOnlyIntArray
extends AbstractRegexObject {
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final int[] array;

    public TruffleReadOnlyIntArray(int[] array) {
        this.array = array;
    }

    @ExportMessage
    boolean hasArrayElements() {
        return true;
    }

    @ExportMessage
    boolean isArrayElementReadable(long index) {
        return index >= 0L && index < (long)this.array.length;
    }

    @ExportMessage
    long getArraySize() {
        return this.array.length;
    }

    @ExportMessage
    int readArrayElement(long index) throws InvalidArrayIndexException {
        if (!this.isArrayElementReadable(index)) {
            throw InvalidArrayIndexException.create(index);
        }
        return this.array[(int)index];
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return "TRegexReadOnlyIntArray{array=" + Arrays.toString(this.array) + "}";
    }
}

