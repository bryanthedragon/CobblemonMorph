
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(value=InteropLibrary.class)
final class ArrayIterator
implements TruffleObject {
    final Object array;
    private long currentItemIndex;

    ArrayIterator(Object array) {
        this.array = array;
        assert (InteropLibrary.getUncached().hasArrayElements(array)) : "Array must have array elements.";
    }

    @ExportMessage
    boolean isIterator() {
        return true;
    }

    @ExportMessage
    boolean hasIteratorNextElement(@CachedLibrary(value="this.array") InteropLibrary arrays) {
        try {
            return this.currentItemIndex < arrays.getArraySize(this.array);
        }
        catch (UnsupportedMessageException ume) {
            throw CompilerDirectives.shouldNotReachHere(ume);
        }
    }

    @ExportMessage
    Object getIteratorNextElement(@CachedLibrary(value="this.array") InteropLibrary arrays) throws UnsupportedMessageException, StopIterationException {
        try {
            long size = arrays.getArraySize(this.array);
            if (this.currentItemIndex >= size) {
                throw StopIterationException.create();
            }
            Object res = arrays.readArrayElement(this.array, this.currentItemIndex);
            ++this.currentItemIndex;
            return res;
        }
        catch (UnsupportedMessageException ume) {
            throw CompilerDirectives.shouldNotReachHere(ume);
        }
        catch (InvalidArrayIndexException iaie) {
            throw UnsupportedMessageException.create();
        }
    }
}

