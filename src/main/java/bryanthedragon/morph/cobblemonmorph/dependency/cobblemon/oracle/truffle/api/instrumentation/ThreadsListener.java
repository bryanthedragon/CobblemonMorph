package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.TruffleContext;

public interface ThreadsListener {
   void onThreadInitialized(TruffleContext context, Thread thread);

   void onThreadDisposed(TruffleContext context, Thread thread);
}
