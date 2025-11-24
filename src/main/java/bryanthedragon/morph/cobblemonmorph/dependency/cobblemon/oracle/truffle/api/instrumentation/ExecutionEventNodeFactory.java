
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ExecutionEventNode;

public interface ExecutionEventNodeFactory {
    public ExecutionEventNode create(EventContext var1);
}

