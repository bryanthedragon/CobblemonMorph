
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.instrumentation.ExecuteSourceEvent;

public interface ExecuteSourceListener {
    public void onExecute(ExecuteSourceEvent var1);
}

