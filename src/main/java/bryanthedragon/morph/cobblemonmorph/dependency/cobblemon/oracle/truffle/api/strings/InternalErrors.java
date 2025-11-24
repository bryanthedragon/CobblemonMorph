
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;

final class InternalErrors {
    InternalErrors() {
    }

    static RuntimeException substringOutOfBounds() {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return new IndexOutOfBoundsException("substring range (offset + length) is out of bounds!");
    }

    static RuntimeException indexOutOfBounds() {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return new IndexOutOfBoundsException("index is out of bounds!");
    }

    static RuntimeException invalidCodePoint(int c) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return new IllegalArgumentException(String.format("invalid code point: 0x%x", c));
    }

    static RuntimeException unsupportedOperation() {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return new UnsupportedOperationException();
    }

    static RuntimeException unsupportedOperation(String msg) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return new UnsupportedOperationException(msg);
    }

    static RuntimeException illegalArgument(String msg) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return new IllegalArgumentException(msg);
    }

    static RuntimeException illegalByteArrayLength(String msg) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return new TruffleString.IllegalByteArrayLengthException(msg);
    }

    static RuntimeException illegalState(String msg) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return new IllegalStateException(msg);
    }

    static RuntimeException wrongEncoding(TruffleString.Encoding expectedEncoding) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return new IllegalArgumentException("the given string is not compatible to the expected encoding \"" + expectedEncoding + "\", did you forget to convert it?");
    }

    static RuntimeException unknownEncoding(String name) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return InternalErrors.illegalArgument("unknown encoding: \"" + name + "\"");
    }

    public static RuntimeException nativeAccessRequired() {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return new UnsupportedOperationException("this operation requires native access!");
    }

    @CompilerDirectives.TruffleBoundary
    static OutOfMemoryError outOfMemory() {
        return new OutOfMemoryError();
    }
}

