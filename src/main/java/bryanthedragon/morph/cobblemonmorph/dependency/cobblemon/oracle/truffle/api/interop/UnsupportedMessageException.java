
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.interop.InteropException;

public final class UnsupportedMessageException
extends InteropException {
    private static final long serialVersionUID = 1857745390734085182L;

    private UnsupportedMessageException(Throwable cause) {
        super(null, cause);
    }

    private UnsupportedMessageException() {
        super((String)null);
    }

    @Override
    public String getMessage() {
        return "Message not supported.";
    }

    public static UnsupportedMessageException create() {
        return new UnsupportedMessageException();
    }

    public static UnsupportedMessageException create(Throwable cause) {
        return new UnsupportedMessageException(cause);
    }
}

