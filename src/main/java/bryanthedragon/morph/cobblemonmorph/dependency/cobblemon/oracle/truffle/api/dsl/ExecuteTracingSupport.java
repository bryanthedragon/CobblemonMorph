package com.oracle.truffle.api.dsl;

public interface ExecuteTracingSupport {
   boolean isTracingEnabled();

   default void traceOnEnter(Object[] arguments) {
   }

   default void traceOnReturn(Object returnValue) {
   }

   default void traceOnException(Throwable t) {
   }
}
