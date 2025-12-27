package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.nodes.Node;
import java.util.concurrent.atomic.AtomicInteger;

final class DefaultThreadLocalHandshake extends ThreadLocalHandshake {
   static final DefaultThreadLocalHandshake SINGLETON = new DefaultThreadLocalHandshake();
   private static final ThreadLocal<ThreadLocalHandshake.TruffleSafepointImpl> STATE = ThreadLocal.withInitial(
      () -> SINGLETON.getThreadState(Thread.currentThread())
   );
   private static final AtomicInteger PENDING_COUNT = new AtomicInteger();

   private DefaultThreadLocalHandshake() {
   }

   @Override
   public void poll(Node enclosingNode) {
      int count = PENDING_COUNT.get();

      assert count >= 0 : "inconsistent pending state";

      if (count > 0) {
         SINGLETON.processHandshake(enclosingNode);
      }
   }

   @Override
   public ThreadLocalHandshake.TruffleSafepointImpl getCurrent() {
      return STATE.get();
   }

   @Override
   protected void setFastPending(Thread t) {
      PENDING_COUNT.incrementAndGet();
   }

   @Override
   protected void clearFastPending() {
      PENDING_COUNT.decrementAndGet();
   }
}
