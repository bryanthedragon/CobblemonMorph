package com.oracle.truffle.api.debug;

public interface DebugThreadsListener {
   void threadInitialized(DebugContext context, Thread thread);

   void threadDisposed(DebugContext context, Thread thread);
}
