
package com.oracle.truffle.api.nodes;

public class ControlFlowException
extends RuntimeException {
    private static final long serialVersionUID = 3676602078425211386L;

    public ControlFlowException() {
        super(null, null);
    }

    @Override
    public final Throwable fillInStackTrace() {
        return this;
    }
}

