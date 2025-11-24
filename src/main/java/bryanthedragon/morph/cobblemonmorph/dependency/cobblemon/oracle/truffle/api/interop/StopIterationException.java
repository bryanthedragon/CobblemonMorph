
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.interop.InteropException;

public final class StopIterationException
extends InteropException {
    private static final long serialVersionUID = 1857745390734085182L;
    private static final StopIterationException INSTANCE = new StopIterationException();

    private StopIterationException() {
        super((String)null);
    }

    private StopIterationException(Throwable cause) {
        super(null, cause);
    }

    @Override
    public String getMessage() {
        return "Iteration was stopped.";
    }

    public static StopIterationException create() {
        return INSTANCE;
    }

    public static StopIterationException create(Throwable cause) {
        return new StopIterationException(cause);
    }
}

