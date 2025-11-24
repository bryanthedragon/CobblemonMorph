
package com.oracle.truffle.api.memory;

final class ByteArrayOutOfBoundsException
extends IndexOutOfBoundsException {
    ByteArrayOutOfBoundsException() {
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}

