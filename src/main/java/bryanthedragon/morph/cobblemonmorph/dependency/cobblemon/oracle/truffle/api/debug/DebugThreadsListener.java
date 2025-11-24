
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.debug.DebugContext;

public interface DebugThreadsListener {
    public void threadInitialized(DebugContext var1, Thread var2);

    public void threadDisposed(DebugContext var1, Thread var2);
}

