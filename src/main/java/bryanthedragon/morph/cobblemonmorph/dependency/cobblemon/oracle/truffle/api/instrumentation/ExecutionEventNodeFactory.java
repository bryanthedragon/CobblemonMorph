package com.oracle.truffle.api.instrumentation;

public interface ExecutionEventNodeFactory {
   ExecutionEventNode create(EventContext context);
}
