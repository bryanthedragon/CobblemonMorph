package com.oracle.truffle.api;

import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.impl.ThreadLocalHandshake;
import com.oracle.truffle.api.nodes.Node;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class TruffleSafepoint {
   private static final ThreadLocalHandshake HANDSHAKE = LanguageAccessor.ACCESSOR.runtimeSupport().getThreadLocalHandshake();

   protected TruffleSafepoint(Accessor.EngineSupport support) {
      if (support == null) {
         throw new AssertionError("Only runtime is allowed create truffle safepoint instances.");
      }
   }

   public static void poll(Node location) {
      if (location == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new NullPointerException();
      } else {
         HANDSHAKE.poll(location);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static void pollHere(Node location) {
      Objects.requireNonNull(location);
      HANDSHAKE.poll(location);
   }

   @Deprecated(since = "22.1")
   public final <T> void setBlocked(
      Node location,
      TruffleSafepoint.Interrupter interrupter,
      TruffleSafepoint.Interruptible<T> interruptible,
      T object,
      Runnable beforeInterrupt,
      Runnable afterInterrupt
   ) {
      this.setBlockedWithException(location, interrupter, interruptible, object, beforeInterrupt, afterInterrupt == null ? null : t -> afterInterrupt.run());
   }

   public abstract <T> void setBlockedWithException(
      Node location,
      TruffleSafepoint.Interrupter interrupter,
      TruffleSafepoint.Interruptible<T> interruptible,
      T object,
      Runnable beforeInterrupt,
      Consumer<Throwable> afterInterrupt
   );

   public static <T> void setBlockedThreadInterruptible(Node location, TruffleSafepoint.Interruptible<T> interruptible, T object) {
      TruffleSafepoint safepoint = getCurrent();
      safepoint.setBlockedWithException(location, TruffleSafepoint.Interrupter.THREAD_INTERRUPT, interruptible, object, null, (Consumer<Throwable>)null);
   }

   public abstract boolean setAllowActions(boolean enabled) throws IllegalStateException;

   public abstract boolean setAllowSideEffects(boolean enabled);

   public abstract boolean hasPendingSideEffectingActions();

   public static TruffleSafepoint getCurrent() {
      return HANDSHAKE.getCurrent();
   }

   @FunctionalInterface
   public interface CompiledInterruptible<T> extends TruffleSafepoint.Interruptible<T> {
      @Override
      void apply(T arg) throws InterruptedException;
   }

   public interface Interrupter {
      TruffleSafepoint.Interrupter THREAD_INTERRUPT = new TruffleSafepoint.Interrupter() {
         @Override
         public void resetInterrupted() {
            Thread.interrupted();
         }

         @Override
         public void interrupt(Thread t) {
            t.interrupt();
         }
      };

      void interrupt(Thread thread);

      void resetInterrupted();
   }

   @FunctionalInterface
   public interface Interruptible<T> {
      void apply(T arg) throws InterruptedException;
   }
}
