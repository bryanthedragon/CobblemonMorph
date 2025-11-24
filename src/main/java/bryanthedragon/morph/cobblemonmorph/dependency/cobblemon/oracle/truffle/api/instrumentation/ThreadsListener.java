
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.TruffleContext;

public interface ThreadsListener {
    public void onThreadInitialized(TruffleContext var1, Thread var2);

    public void onThreadDisposed(TruffleContext var1, Thread var2);
}

