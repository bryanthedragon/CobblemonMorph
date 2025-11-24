
package com.oracle.truffle.polyglot;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

final class PolyglotEngineException
extends RuntimeException {
    final RuntimeException e;
    final boolean closedException;

    PolyglotEngineException(RuntimeException e) {
        this(e, false);
    }

    PolyglotEngineException(RuntimeException e, boolean closedException) {
        super(null, e);
        this.e = e;
        this.closedException = closedException;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    static void rethrow(Throwable e) {
        if (e instanceof PolyglotEngineException) {
            throw ((PolyglotEngineException)e).e;
        }
    }

    static PolyglotEngineException illegalArgument(IllegalArgumentException e) {
        return new PolyglotEngineException(e);
    }

    static PolyglotEngineException illegalArgument(String message) {
        return new PolyglotEngineException(new IllegalArgumentException(message));
    }

    static PolyglotEngineException illegalState(String message) {
        return new PolyglotEngineException(new IllegalStateException(message));
    }

    static PolyglotEngineException closedException(String message) {
        return new PolyglotEngineException(new IllegalStateException(message), true);
    }

    static PolyglotEngineException nullPointer(String message) {
        return new PolyglotEngineException(new NullPointerException(message));
    }

    static PolyglotEngineException unsupported(String message) {
        return new PolyglotEngineException(new UnsupportedOperationException(message));
    }

    static PolyglotEngineException unsupported(String message, Throwable cause) {
        return new PolyglotEngineException(new UnsupportedOperationException(message, cause));
    }

    static PolyglotEngineException classCast(String message) {
        return new PolyglotEngineException(new ClassCastException(message));
    }

    static PolyglotEngineException arrayIndexOutOfBounds(String message) {
        return new PolyglotEngineException(new ArrayIndexOutOfBoundsException(message));
    }

    static PolyglotEngineException bufferIndexOutOfBounds(String message) {
        return new PolyglotEngineException(new IndexOutOfBoundsException(message));
    }

    static PolyglotEngineException noSuchElement(String message) {
        return new PolyglotEngineException(new NoSuchElementException(message));
    }

    static PolyglotEngineException concurrentModificationException(String message) {
        return new PolyglotEngineException(new ConcurrentModificationException(message));
    }
}

