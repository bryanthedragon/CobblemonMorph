
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropException;

public final class UnknownIdentifierException
extends InteropException {
    private static final long serialVersionUID = 1857745390734085182L;
    private final String unknownIdentifier;

    private UnknownIdentifierException(String unknownIdentifier) {
        super((String)null);
        this.unknownIdentifier = unknownIdentifier;
    }

    private UnknownIdentifierException(String unknownIdentifier, Throwable cause) {
        super(null, cause);
        this.unknownIdentifier = unknownIdentifier;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public String getMessage() {
        return "Unknown identifier: " + this.unknownIdentifier;
    }

    public String getUnknownIdentifier() {
        return this.unknownIdentifier;
    }

    public static UnknownIdentifierException create(String unknownIdentifier) {
        return new UnknownIdentifierException(unknownIdentifier);
    }

    public static UnknownIdentifierException create(String unknownIdentifier, Throwable cause) {
        return new UnknownIdentifierException(unknownIdentifier, cause);
    }
}

