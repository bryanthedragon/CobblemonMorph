
package com.oracle.truffle.host;

import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class HostEngineException {
    HostEngineException() {
    }

    static RuntimeException illegalArgument(AbstractPolyglotImpl.AbstractHostAccess polyglot, IllegalArgumentException e) {
        return HostEngineException.toEngineException(polyglot, e);
    }

    static RuntimeException illegalArgument(AbstractPolyglotImpl.AbstractHostAccess polyglot, String message) {
        return HostEngineException.toEngineException(polyglot, new IllegalArgumentException(message));
    }

    static RuntimeException nullPointer(AbstractPolyglotImpl.AbstractHostAccess polyglot, String message) {
        return HostEngineException.toEngineException(polyglot, new NullPointerException(message));
    }

    static RuntimeException unsupported(AbstractPolyglotImpl.AbstractHostAccess polyglot, String message) {
        return HostEngineException.toEngineException(polyglot, new UnsupportedOperationException(message));
    }

    static RuntimeException classCast(AbstractPolyglotImpl.AbstractHostAccess polyglot, String message) {
        return HostEngineException.toEngineException(polyglot, new ClassCastException(message));
    }

    static RuntimeException arrayIndexOutOfBounds(AbstractPolyglotImpl.AbstractHostAccess polyglot, String message) {
        return HostEngineException.toEngineException(polyglot, new ArrayIndexOutOfBoundsException(message));
    }

    static RuntimeException toEngineException(AbstractPolyglotImpl.AbstractHostAccess access, RuntimeException e) {
        return access.toEngineException(e);
    }
}

