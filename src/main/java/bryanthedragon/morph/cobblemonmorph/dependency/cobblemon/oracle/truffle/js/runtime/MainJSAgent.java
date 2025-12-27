package com.oracle.truffle.js.runtime;

public final class MainJSAgent extends JSAgent {
   public MainJSAgent(PromiseRejectionTracker promiseRejectionTracker) {
      super(promiseRejectionTracker, false);
   }

   @Override
   public void terminate() {
   }

   @Override
   public void wake() {
   }
}
