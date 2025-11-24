
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.TruffleContext;

public interface ThreadsActivationListener {
    public void onEnterThread(TruffleContext var1);

    public void onLeaveThread(TruffleContext var1);
}

