
package com.oracle.truffle.api.dsl;

public interface ExecuteTracingSupport {
    public boolean isTracingEnabled();

    default public void traceOnEnter(Object[] arguments) {
    }

    default public void traceOnReturn(Object returnValue) {
    }

    default public void traceOnException(Throwable t) {
    }
}

