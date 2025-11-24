
package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(value=InteropLibrary.class)
public final class InteropMemberIterator
implements TruffleObject {
    private final boolean values;
    private final long keysSize;
    private long cursor;
    final Object iteratedObject;
    final Object keysObject;

    private InteropMemberIterator(boolean values, Object iteratedObject, Object keysObject, long keysSize) {
        this.values = values;
        this.iteratedObject = iteratedObject;
        this.keysObject = keysObject;
        this.keysSize = keysSize;
    }

    public static InteropMemberIterator create(boolean values, Object iteratedObject, Object keysObject, long keysSize) {
        return new InteropMemberIterator(values, iteratedObject, keysObject, keysSize);
    }

    @ExportMessage
    boolean isIterator() {
        return true;
    }

    @ExportMessage
    boolean hasIteratorNextElement() {
        return this.cursor < this.keysSize;
    }

    @ExportMessage
    Object getIteratorNextElement(@CachedLibrary(value="this.iteratedObject") InteropLibrary objInterop, @CachedLibrary(value="this.keysObject") InteropLibrary keysInterop) throws StopIterationException {
        if (this.hasIteratorNextElement()) {
            long index = this.cursor++;
            try {
                Object key = keysInterop.readArrayElement(this.keysObject, index);
                if (this.values) {
                    assert (InteropLibrary.getUncached().isString(key));
                    String stringKey = key instanceof String ? (String)key : InteropLibrary.getUncached().asString(key);
                    return objInterop.readMember(this.iteratedObject, stringKey);
                }
                return key;
            }
            catch (InvalidArrayIndexException | UnknownIdentifierException | UnsupportedMessageException e) {
                throw StopIterationException.create(e);
            }
        }
        throw StopIterationException.create();
    }
}

