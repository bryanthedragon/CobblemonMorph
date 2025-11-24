
package com.oracle.truffle.api.exception;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleStackTrace;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;

public abstract class AbstractTruffleException
extends RuntimeException
implements TruffleObject {
    public static final int UNLIMITED_STACK_TRACE = -1;
    private final int stackTraceElementLimit;
    private final Throwable cause;
    private final Node location;
    private Throwable lazyStackTrace;

    protected AbstractTruffleException() {
        this(null, null, -1, null);
    }

    protected AbstractTruffleException(Node location) {
        this(null, null, -1, location);
    }

    protected AbstractTruffleException(String message) {
        this(message, null, -1, null);
    }

    protected AbstractTruffleException(String message, Node location) {
        this(message, null, -1, location);
    }

    @CompilerDirectives.TruffleBoundary
    protected AbstractTruffleException(AbstractTruffleException prototype) {
        this(prototype.getMessage(), prototype.getCause(), prototype.getStackTraceElementLimit(), prototype.getLocation());
        for (Throwable t : prototype.getSuppressed()) {
            this.addSuppressed(t);
        }
        TruffleStackTrace.fillIn(prototype);
        assert (prototype.lazyStackTrace != null) : "Prototype must have a stack trace after fillIn.";
        this.lazyStackTrace = prototype.lazyStackTrace;
    }

    protected AbstractTruffleException(String message, Throwable cause, int stackTraceElementLimit, Node location) {
        super(message, cause);
        this.stackTraceElementLimit = stackTraceElementLimit;
        this.cause = cause;
        this.location = location;
    }

    @Override
    public final Throwable fillInStackTrace() {
        return this;
    }

    public final Node getLocation() {
        return this.location;
    }

    public final int getStackTraceElementLimit() {
        return this.stackTraceElementLimit;
    }

    @Override
    public final Throwable getCause() {
        return this.cause;
    }

    Throwable getLazyStackTrace() {
        return this.lazyStackTrace;
    }

    void setLazyStackTrace(Throwable stackTrace) {
        this.lazyStackTrace = stackTrace;
    }
}

