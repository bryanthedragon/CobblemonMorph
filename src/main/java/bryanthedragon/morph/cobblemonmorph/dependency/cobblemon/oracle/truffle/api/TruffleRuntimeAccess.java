package com.oracle.truffle.api;

public interface TruffleRuntimeAccess {
   TruffleRuntime getRuntime();

   default int getPriority() {
      return 0;
   }
}
