package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.nodes.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class ThreadLocalHandshake {
   private static final Map<Thread, ThreadLocalHandshake.TruffleSafepointImpl> SAFEPOINTS = Collections.synchronizedMap(new WeakHashMap<>());

   static void resetNativeImageState() {
      for (ThreadLocalHandshake.TruffleSafepointImpl impl : SAFEPOINTS.values()) {
         impl.verifyUnused();
      }

      SAFEPOINTS.clear();
   }

   protected ThreadLocalHandshake() {
   }

   public abstract void poll(Node enclosingNode);

   public abstract ThreadLocalHandshake.TruffleSafepointImpl getCurrent();

   protected boolean isSupported() {
      return true;
   }

   public void testSupport() {
      if (!this.isSupported()) {
         throw new UnsupportedOperationException(
            "Thread local handshakes are not supported on this platform. A possible reason may be that the underlying JVMCI version is too old."
         );
      }
   }

   public void setChangeAllowActions(TruffleSafepoint safepoint, boolean enabled) {
      ((ThreadLocalHandshake.TruffleSafepointImpl)safepoint).setChangeAllowActions(enabled);
   }

   public boolean isAllowActions(TruffleSafepoint safepoint) {
      return ((ThreadLocalHandshake.TruffleSafepointImpl)safepoint).isAllowActions();
   }

   @CompilerDirectives.TruffleBoundary
   public final <T extends Consumer<Node>> Future<Void> runThreadLocal(
      Thread[] threads, T onThread, Consumer<T> onDone, boolean sideEffecting, boolean syncStartOfEvent, boolean syncEndOfEvent
   ) {
      this.testSupport();

      assert threads.length > 0;

      ThreadLocalHandshake.Handshake<T> handshake = new ThreadLocalHandshake.Handshake<>(
         threads, onThread, onDone, sideEffecting, threads.length, syncStartOfEvent, syncEndOfEvent
      );
      if (!syncStartOfEvent && !syncEndOfEvent) {
         this.addHandshakes(threads, handshake);
      } else {
         synchronized (ThreadLocalHandshake.class) {
            this.addHandshakes(threads, handshake);
         }
      }

      return handshake;
   }

   private <T extends Consumer<Node>> void addHandshakes(Thread[] threads, ThreadLocalHandshake.Handshake<T> handshake) {
      for (int i = 0; i < threads.length; i++) {
         Thread t = threads[i];
         if (!t.isAlive()) {
            throw new IllegalStateException("Thread no longer alive with pending handshake.");
         }

         this.getThreadState(t).addHandshake(t, handshake);
      }
   }

   public final boolean activateThread(TruffleSafepoint s, Future<?> f) {
      return ((ThreadLocalHandshake.TruffleSafepointImpl)s).activateThread((ThreadLocalHandshake.Handshake<?>)f);
   }

   public final boolean deactivateThread(TruffleSafepoint s, Future<?> f) {
      return ((ThreadLocalHandshake.TruffleSafepointImpl)s).deactivateThread((ThreadLocalHandshake.Handshake<?>)f);
   }

   public void ensureThreadInitialized() {
   }

   protected abstract void setFastPending(Thread t);

   @CompilerDirectives.TruffleBoundary
   protected final void processHandshake(Node location) {
      ThreadLocalHandshake.TruffleSafepointImpl s = this.getCurrent();
      if (s.fastPendingSet) {
         s.processHandshakes(location, s.takeHandshakes());
      }
   }

   protected abstract void clearFastPending();

   private static Throwable combineThrowable(Throwable current, Throwable t) {
      if (current == null) {
         return t;
      } else if (t instanceof ThreadDeath) {
         t.addSuppressed(current);
         return t;
      } else {
         current.addSuppressed(t);
         return current;
      }
   }

   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable ex) throws T {
      throw ex;
   }

   protected final ThreadLocalHandshake.TruffleSafepointImpl getThreadState(Thread thread) {
      return SAFEPOINTS.computeIfAbsent(thread, t -> new ThreadLocalHandshake.TruffleSafepointImpl(this));
   }

   public static final class Handshake<T extends Consumer<Node>> implements Future<Void> {
      private final boolean sideEffecting;
      private final Phaser phaser;
      private volatile boolean cancelled;
      private final T action;
      private final boolean syncStartOfEvent;
      private final boolean syncEndOfEvent;
      private final Map<Thread, Boolean> threads;
      private final Consumer<T> onDone;

      Handshake(
         Thread[] initialThreads, T action, Consumer<T> onDone, boolean sideEffecting, int numberOfThreads, boolean syncStartOfEvent, boolean syncEndOfEvent
      ) {
         this.action = action;
         this.onDone = onDone;
         this.sideEffecting = sideEffecting;
         this.syncStartOfEvent = syncStartOfEvent;
         this.syncEndOfEvent = syncEndOfEvent;
         this.phaser = new Phaser(numberOfThreads);
         this.threads = new ConcurrentHashMap<>(Arrays.stream(initialThreads).collect(Collectors.toMap(t -> (Thread)t, t -> Boolean.FALSE)));
      }

      @Override
      public boolean isCancelled() {
         return this.cancelled;
      }

      void perform(Node node) {
         try {
            if (this.syncStartOfEvent) {
               this.phaser.arriveAndAwaitAdvance();
            }

            if (!this.cancelled) {
               this.action.accept(node);
            }
         } finally {
            this.phaser.arriveAndDeregister();
            if (this.syncEndOfEvent) {
               this.phaser.awaitAdvance(this.syncStartOfEvent ? 1 : 0);

               assert this.phaser.isTerminated();
            }

            if (this.phaser.isTerminated()) {
               this.onDone.accept(this.action);
            }
         }
      }

      boolean activateThread() {
         int result = this.phaser.register();
         if (result != 0) {
            this.phaser.arriveAndDeregister();
            return false;
         } else {
            return true;
         }
      }

      void deactivateThread() {
         this.phaser.arriveAndDeregister();
         if (this.phaser.isTerminated()) {
            this.onDone.accept(this.action);
         }
      }

      public Void get() throws InterruptedException {
         if (this.syncStartOfEvent) {
            this.phaser.awaitAdvanceInterruptibly(0);
            this.phaser.awaitAdvanceInterruptibly(1);
         } else {
            this.phaser.awaitAdvanceInterruptibly(0);
         }

         return null;
      }

      public Void get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
         if (this.syncStartOfEvent) {
            this.phaser.awaitAdvanceInterruptibly(0, timeout, unit);
            this.phaser.awaitAdvanceInterruptibly(1, timeout, unit);
         } else {
            this.phaser.awaitAdvanceInterruptibly(0, timeout, unit);
         }

         return null;
      }

      @Override
      public boolean isDone() {
         return this.cancelled || this.phaser.isTerminated();
      }

      @Override
      public boolean cancel(boolean mayInterruptIfRunning) {
         if (!this.phaser.isTerminated()) {
            this.cancelled = true;
            return true;
         } else {
            return false;
         }
      }

      @Override
      public String toString() {
         return "Handshake[action="
            + this.action
            + ", phaser="
            + this.phaser
            + ", cancelled="
            + this.cancelled
            + ", sideEffecting="
            + this.sideEffecting
            + ", syncStartOfEvent="
            + this.syncStartOfEvent
            + ", syncEndOfEvent="
            + this.syncEndOfEvent
            + "]";
      }
   }

   static final class HandshakeEntry {
      final ThreadLocalHandshake.Handshake<?> handshake;
      final boolean reactivated;

      HandshakeEntry(ThreadLocalHandshake.Handshake<?> handshake, boolean reactivated) {
         this.handshake = handshake;
         this.reactivated = reactivated;
      }

      @Override
      public String toString() {
         return "HandshakeEntry[" + this.handshake + " reactivated=" + this.reactivated + "]";
      }
   }

   protected static final class TruffleSafepointImpl extends TruffleSafepoint {
      private final ReentrantLock lock = new ReentrantLock();
      private final ThreadLocalHandshake impl;
      private volatile boolean fastPendingSet;
      private boolean sideEffectsEnabled = true;
      private boolean enabled = true;
      private volatile boolean changeAllowActionsAllowed;
      private TruffleSafepoint.Interrupter blockedAction;
      private boolean interrupted;
      private final LinkedList<ThreadLocalHandshake.HandshakeEntry> handshakes = new LinkedList<>();

      TruffleSafepointImpl(ThreadLocalHandshake handshake) {
         super(DefaultRuntimeAccessor.ENGINE);
         this.impl = handshake;
      }

      void verifyUnused() throws AssertionError {
         if (!this.lock.isHeldByCurrentThread() && !this.lock.isLocked()) {
            this.lock.lock();

            try {
               if (this.blockedAction != null) {
                  throw new AssertionError("Invalid pending blocked action.");
               }

               if (this.interrupted) {
                  throw new AssertionError("Invalid pending interrupted state.");
               }

               if (this.isPending()) {
                  throw new AssertionError("Invalid pending handshakes.");
               }

               if (!this.sideEffectsEnabled) {
                  throw new AssertionError("Invalid side-effects disabled state");
               }

               if (!this.enabled) {
                  throw new AssertionError("Invalid allow actions disabled state");
               }
            } finally {
               this.lock.unlock();
            }
         } else {
            throw new AssertionError("Invalid locked state for safepoint.");
         }
      }

      void processHandshakes(Node location, List<ThreadLocalHandshake.HandshakeEntry> toProcess) {
         if (toProcess != null) {
            Throwable ex = null;

            for (ThreadLocalHandshake.HandshakeEntry current : toProcess) {
               if (this.claimEntry(current)) {
                  try {
                     current.handshake.perform(location);
                  } catch (Throwable var7) {
                     ex = ThreadLocalHandshake.combineThrowable(ex, var7);
                  }
               }
            }

            if (this.fastPendingSet) {
               this.resetPending();
            }

            if (ex != null) {
               throw ThreadLocalHandshake.sneakyThrow(ex);
            }
         }
      }

      public boolean deactivateThread(ThreadLocalHandshake.Handshake<?> handshake) {
         this.lock.lock();

         try {
            ThreadLocalHandshake.HandshakeEntry current = this.lookupEntry(handshake);
            if (current != null) {
               assert !current.reactivated || current.handshake.sideEffecting : "Reactivated handshake was not processed!";

               handshake.deactivateThread();
               this.claimEntry(current);
               handshake.threads.put(Thread.currentThread(), Boolean.TRUE);
               this.resetPending();
               return true;
            }
         } finally {
            this.lock.unlock();
         }

         return false;
      }

      public boolean activateThread(ThreadLocalHandshake.Handshake<?> handshake) {
         if (handshake.isDone()) {
            return false;
         } else {
            this.lock.lock();

            try {
               ThreadLocalHandshake.HandshakeEntry current = this.lookupEntry(handshake);
               if (current != null) {
                  return false;
               } else {
                  boolean reactivated = false;
                  if (handshake.threads.containsKey(Thread.currentThread())) {
                     if (!handshake.threads.get(Thread.currentThread())) {
                        return false;
                     }

                     reactivated = true;
                  }

                  handshake.threads.put(Thread.currentThread(), Boolean.FALSE);
                  if (!handshake.activateThread()) {
                     return false;
                  } else {
                     this.addHandshakeImpl(Thread.currentThread(), handshake, reactivated);
                     return true;
                  }
               }
            } finally {
               this.lock.unlock();
            }
         }
      }

      private ThreadLocalHandshake.HandshakeEntry lookupEntry(ThreadLocalHandshake.Handshake<?> handshake) {
         assert this.lock.isHeldByCurrentThread();

         for (ThreadLocalHandshake.HandshakeEntry entry : this.handshakes) {
            if (entry.handshake == handshake) {
               return entry;
            }
         }

         return null;
      }

      void addHandshake(Thread t, ThreadLocalHandshake.Handshake<?> handshake) {
         this.lock.lock();

         try {
            this.addHandshakeImpl(t, handshake, false);
         } finally {
            this.lock.unlock();
         }
      }

      private void addHandshakeImpl(Thread t, ThreadLocalHandshake.Handshake<?> handshake, boolean reactivated) {
         this.handshakes.add(new ThreadLocalHandshake.HandshakeEntry(handshake, reactivated));
         if (this.isPending()) {
            this.setFastPendingAndInterrupt(t);
         }
      }

      private void setFastPendingAndInterrupt(Thread t) {
         assert this.lock.isHeldByCurrentThread();

         if (!this.fastPendingSet) {
            this.fastPendingSet = true;
            this.impl.setFastPending(t);
         }

         TruffleSafepoint.Interrupter action = this.blockedAction;
         if (action != null) {
            this.interrupted = true;
            action.interrupt(t);
         }
      }

      List<ThreadLocalHandshake.HandshakeEntry> takeHandshakes() {
         this.lock.lock();

         List var2;
         try {
            if (this.interrupted) {
               this.blockedAction.resetInterrupted();
               this.interrupted = false;
            }

            if (!this.isPending()) {
               return null;
            }

            List<ThreadLocalHandshake.HandshakeEntry> taken = this.takeHandshakeImpl();

            assert !taken.isEmpty();

            var2 = taken;
         } finally {
            this.lock.unlock();
         }

         return var2;
      }

      private void resetPending() {
         this.lock.lock();

         try {
            if (this.fastPendingSet && !this.isPending()) {
               this.fastPendingSet = false;
               this.impl.clearFastPending();
            }
         } finally {
            this.lock.unlock();
         }
      }

      private boolean claimEntry(ThreadLocalHandshake.HandshakeEntry entry) {
         this.lock.lock();

         boolean var2;
         try {
            var2 = this.handshakes.removeFirstOccurrence(entry);
         } finally {
            this.lock.unlock();
         }

         return var2;
      }

      private List<ThreadLocalHandshake.HandshakeEntry> takeHandshakeImpl() {
         if (!this.enabled) {
            return Collections.emptyList();
         } else {
            List<ThreadLocalHandshake.HandshakeEntry> toProcess = new ArrayList<>(this.handshakes.size());

            for (ThreadLocalHandshake.HandshakeEntry entry : this.handshakes) {
               if (this.isPending(entry)) {
                  toProcess.add(entry);
               }
            }

            return toProcess;
         }
      }

      private boolean isPending(ThreadLocalHandshake.HandshakeEntry entry) {
         return this.sideEffectsEnabled || !entry.handshake.sideEffecting;
      }

      @Override
      public <T> void setBlockedWithException(
         Node location,
         TruffleSafepoint.Interrupter interrupter,
         TruffleSafepoint.Interruptible<T> interruptible,
         T object,
         Runnable beforeInterrupt,
         Consumer<Throwable> afterInterrupt
      ) {
         assert this.impl.getCurrent() == this : "Cannot be used from a different thread.";

         if (CompilerDirectives.inCompiledCode()
            && CompilerDirectives.isPartialEvaluationConstant(interruptible)
            && interruptible instanceof TruffleSafepoint.CompiledInterruptible) {
            this.setBlockedCompiled(location, interrupter, (TruffleSafepoint.CompiledInterruptible<T>)interruptible, object, beforeInterrupt, afterInterrupt);
         } else {
            this.setBlockedBoundary(location, interrupter, interruptible, object, beforeInterrupt, afterInterrupt);
         }
      }

      private <T> void setBlockedCompiled(
         Node location,
         TruffleSafepoint.Interrupter interrupter,
         TruffleSafepoint.CompiledInterruptible<T> interruptible,
         T object,
         Runnable beforeInterrupt,
         Consumer<Throwable> afterInterrupt
      ) {
         TruffleSafepoint.Interrupter prev = this.blockedAction;

         try {
            while (true) {
               try {
                  this.setBlockedImpl(location, interrupter, false);
                  interruptible.apply(object);
                  return;
               } catch (InterruptedException var12) {
                  this.setBlockedAfterInterrupt(location, prev, beforeInterrupt, afterInterrupt);
               }
            }
         } finally {
            this.setBlockedImpl(location, prev, false);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private <T> void setBlockedBoundary(
         Node location,
         TruffleSafepoint.Interrupter interrupter,
         TruffleSafepoint.Interruptible<T> interruptible,
         T object,
         Runnable beforeInterrupt,
         Consumer<Throwable> afterInterrupt
      ) {
         TruffleSafepoint.Interrupter prev = this.blockedAction;

         try {
            while (true) {
               try {
                  this.setBlockedImpl(location, interrupter, false);
                  interruptible.apply(object);
                  return;
               } catch (InterruptedException var12) {
                  this.setBlockedAfterInterrupt(location, prev, beforeInterrupt, afterInterrupt);
               }
            }
         } finally {
            this.setBlockedImpl(location, prev, false);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private void setBlockedAfterInterrupt(
         final Node location, final TruffleSafepoint.Interrupter interrupter, Runnable beforeInterrupt, Consumer<Throwable> afterInterrupt
      ) {
         if (beforeInterrupt != null) {
            beforeInterrupt.run();
         }

         Throwable t = null;

         try {
            this.setBlockedImpl(location, interrupter, true);
         } catch (Throwable var10) {
            t = var10;
            throw var10;
         } finally {
            if (afterInterrupt != null) {
               afterInterrupt.accept(t);
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      private void setBlockedImpl(final Node location, final TruffleSafepoint.Interrupter interrupter, boolean processSafepoints) {
         List<ThreadLocalHandshake.HandshakeEntry> toProcess = null;
         this.lock.lock();

         try {
            if (processSafepoints && this.isPending()) {
               toProcess = this.takeHandshakeImpl();
            }

            if (this.interrupted) {
               assert this.blockedAction != null;

               this.blockedAction.resetInterrupted();
               this.interrupted = false;
            }

            this.blockedAction = interrupter;
         } finally {
            this.lock.unlock();
         }

         this.processHandshakes(location, toProcess);
         if (interrupter != null) {
            this.interruptIfPending(interrupter);
         }
      }

      private void interruptIfPending(final TruffleSafepoint.Interrupter interrupter) {
         this.lock.lock();

         try {
            if (interrupter != null && this.isPending()) {
               this.interrupted = true;
               interrupter.interrupt(Thread.currentThread());
            }
         } finally {
            this.lock.unlock();
         }
      }

      private boolean isPending() {
         assert this.lock.isHeldByCurrentThread();

         if (!this.enabled) {
            return false;
         } else {
            for (ThreadLocalHandshake.HandshakeEntry entry : this.handshakes) {
               if (this.isPending(entry)) {
                  return true;
               }
            }

            return false;
         }
      }

      void setChangeAllowActions(boolean changeAllowActionsAllowed) {
         this.changeAllowActionsAllowed = changeAllowActionsAllowed;
      }

      boolean isAllowActions() {
         return this.enabled;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean setAllowActions(boolean enabled) {
         assert this.impl.getCurrent() == this : "Cannot be used from a different thread.";

         this.lock.lock();

         boolean var3;
         try {
            if (!this.changeAllowActionsAllowed) {
               throw new IllegalStateException(
                  "Using setAllowActions is only permitted during finalization of a language. See TruffleLanguage.finalizeContext(Object) for further details."
               );
            }

            boolean prev = this.enabled;
            this.enabled = enabled;
            this.updateFastPending();
            var3 = prev;
         } finally {
            this.lock.unlock();
         }

         return var3;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean setAllowSideEffects(boolean enabled) {
         assert this.impl.getCurrent() == this : "Cannot be used from a different thread.";

         this.lock.lock();

         boolean var3;
         try {
            boolean prev = this.sideEffectsEnabled;
            this.sideEffectsEnabled = enabled;
            this.updateFastPending();
            var3 = prev;
         } finally {
            this.lock.unlock();
         }

         return var3;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasPendingSideEffectingActions() {
         assert this.impl.getCurrent() == this : "Cannot be used from a different thread.";

         this.lock.lock();

         boolean var1;
         try {
            var1 = !this.sideEffectsEnabled && this.hasSideEffecting();
         } finally {
            this.lock.unlock();
         }

         return var1;
      }

      private boolean hasSideEffecting() {
         assert this.lock.isHeldByCurrentThread();

         for (ThreadLocalHandshake.HandshakeEntry entry : this.handshakes) {
            if (entry.handshake.sideEffecting) {
               return true;
            }
         }

         return false;
      }

      private void updateFastPending() {
         if (this.isPending()) {
            this.setFastPendingAndInterrupt(Thread.currentThread());
         } else if (this.fastPendingSet) {
            this.fastPendingSet = false;
            this.impl.clearFastPending();
         }
      }
   }
}
