
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.debug.SuspendedEvent;

public interface SuspendedCallback {
    public void onSuspend(SuspendedEvent var1);
}

