
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropAccessor;

public abstract class InteropException
extends Exception {
    private static final long serialVersionUID = -5173354806966156285L;

    InteropException(String message, Throwable cause) {
        super(message, cause);
        assert (InteropException.validateCause(cause));
    }

    InteropException(String message) {
        super(message);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final synchronized Throwable getCause() {
        return super.getCause();
    }

    @Override
    @Deprecated(since="20.2")
    @CompilerDirectives.TruffleBoundary
    public final synchronized Throwable initCause(Throwable cause) {
        return super.initCause(cause);
    }

    @Override
    public final Throwable fillInStackTrace() {
        return this;
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean validateCause(Throwable t) {
        if (t == null) {
            return true;
        }
        if (!InteropAccessor.EXCEPTION.isException(t)) {
            throw new IllegalArgumentException("Cause exception must extend AbstractTruffleException but was " + t.getClass() + ".");
        }
        return true;
    }
}

