
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;

public final class UnknownKeyException
extends InteropException {
    private static final long serialVersionUID = 1857745390734085182L;
    private final Object unknownKey;

    private UnknownKeyException(Object unknownKey) {
        super((String)null);
        this.unknownKey = unknownKey;
    }

    private UnknownKeyException(Object unknownKey, Throwable cause) {
        super(null, cause);
        this.unknownKey = unknownKey;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public String getMessage() {
        InteropLibrary interop = InteropLibrary.getUncached();
        try {
            return "Unknown identifier: " + interop.asString(interop.toDisplayString(this.unknownKey, false));
        }
        catch (UnsupportedMessageException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
    }

    public Object getUnknownKey() {
        return this.unknownKey;
    }

    public static UnknownKeyException create(Object unknownKey) {
        return new UnknownKeyException(unknownKey);
    }

    public static UnknownKeyException create(Object unknownKey, Throwable cause) {
        return new UnknownKeyException(unknownKey, cause);
    }
}

