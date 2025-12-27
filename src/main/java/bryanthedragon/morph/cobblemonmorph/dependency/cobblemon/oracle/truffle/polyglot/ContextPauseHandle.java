package com.oracle.truffle.polyglot;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class ContextPauseHandle implements Future<Void> {
   final PauseThreadLocalAction pauseThreadLocalAction;
   private final Future<Void> pauseActionFuture;

   ContextPauseHandle(PauseThreadLocalAction pauseThreadLocalAction, Future<Void> pauseActionFuture) {
      this.pauseThreadLocalAction = pauseThreadLocalAction;
      this.pauseActionFuture = pauseActionFuture;
   }

   @Override
   public boolean cancel(boolean mayInterruptIfRunning) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean isCancelled() {
      return false;
   }

   @Override
   public boolean isDone() {
      return this.pauseThreadLocalAction.wasPaused(this.pauseActionFuture);
   }

   public Void get() throws InterruptedException {
      this.pauseThreadLocalAction.waitUntilPaused(this.pauseActionFuture);
      return null;
   }

   public Void get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
      this.pauseThreadLocalAction.waitUntilPaused(this.pauseActionFuture, timeout, unit);
      return null;
   }

   void resume() {
      this.pauseThreadLocalAction.resume();
   }
}
