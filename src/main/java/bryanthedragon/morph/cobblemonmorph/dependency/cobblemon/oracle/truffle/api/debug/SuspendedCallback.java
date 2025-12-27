package com.oracle.truffle.api.debug;

public interface SuspendedCallback {
   void onSuspend(SuspendedEvent event);
}
