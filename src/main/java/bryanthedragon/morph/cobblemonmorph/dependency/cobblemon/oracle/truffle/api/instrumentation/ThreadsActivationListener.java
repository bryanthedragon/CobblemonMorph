package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.TruffleContext;

public interface ThreadsActivationListener {
   void onEnterThread(TruffleContext context);

   void onLeaveThread(TruffleContext context);
}
