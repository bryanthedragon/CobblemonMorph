package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.ThreadLocalAction;
import com.oracle.truffle.api.TruffleSafepoint;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class PauseThreadLocalAction extends ThreadLocalAction {
   private final Object pauseSync = new Object();
   private volatile boolean pause = true;
   private volatile boolean pauseComplete;
   final PolyglotContextImpl context;

   PauseThreadLocalAction(PolyglotContextImpl context) {
      super(false, true);
      this.context = context;
   }

   @Override
   protected void perform(ThreadLocalAction.Access access) {
      if (access.getThread() != this.context.closingThread) {
         synchronized (this.pauseSync) {
            this.pauseComplete = true;
            this.pauseSync.notifyAll();
         }

         TruffleSafepoint.setBlockedThreadInterruptible(
            access.getLocation(),
            new TruffleSafepoint.Interruptible<Object>() {
               @Override
               public void apply(Object waitObject) throws InterruptedException {
                  synchronized (waitObject) {
                     PolyglotContextImpl.State localContextState = PauseThreadLocalAction.this.context.state;

                     while (
                        PauseThreadLocalAction.this.pause
                           && !localContextState.isClosed()
                           && !localContextState.isCancelling()
                           && !localContextState.isExiting()
                     ) {
                        waitObject.wait();
                     }
                  }
               }
            },
            this.pauseSync
         );
      }
   }

   void resume() {
      synchronized (this.pauseSync) {
         this.pause = false;
         this.pauseSync.notifyAll();
      }
   }

   void waitUntilPaused(Future<?> actionFuture) throws InterruptedException {
      synchronized (this.pauseSync) {
         while (!this.pauseComplete && !actionFuture.isDone()) {
            this.pauseSync.wait(10L);
         }
      }
   }

   void waitUntilPaused(Future<?> actionFuture, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
      long timeoutTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(timeout, unit);
      synchronized (this.pauseSync) {
         while (!this.pauseComplete && !actionFuture.isDone() && System.currentTimeMillis() < timeoutTime) {
            long remainingTime = timeoutTime - System.currentTimeMillis();
            this.pauseSync.wait(Math.max(1L, Math.min(10L, remainingTime)));
         }

         if (!this.pauseComplete && !actionFuture.isDone()) {
            throw new TimeoutException("Waiting for pause timed out!");
         }
      }
   }

   boolean wasPaused(Future<?> actionFuture) {
      return this.pauseComplete || actionFuture.isDone();
   }

   boolean isPause() {
      return this.pause;
   }
}
