
package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(value=InteropLibrary.class)
public final class EmptyIterator
implements TruffleObject {
    private static final EmptyIterator INSTANCE = new EmptyIterator();

    private EmptyIterator() {
    }

    public static Object create() {
        return INSTANCE;
    }

    @ExportMessage
    boolean isIterator() {
        return true;
    }

    @ExportMessage
    boolean hasIteratorNextElement() {
        return false;
    }

    @ExportMessage
    Object getIteratorNextElement() throws StopIterationException {
        throw StopIterationException.create();
    }
}

