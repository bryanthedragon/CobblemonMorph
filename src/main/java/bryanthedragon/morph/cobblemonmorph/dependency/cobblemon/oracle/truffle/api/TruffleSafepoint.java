
package com.oracle.truffle.api;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.LanguageAccessor;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.impl.ThreadLocalHandshake;
import com.oracle.truffle.api.nodes.Node;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class TruffleSafepoint {
    private static final ThreadLocalHandshake HANDSHAKE = LanguageAccessor.ACCESSOR.runtimeSupport().getThreadLocalHandshake();

    protected TruffleSafepoint(Accessor.EngineSupport support) {
        if (support == null) {
            throw new AssertionError((Object)"Only runtime is allowed create truffle safepoint instances.");
        }
    }

    public static void poll(Node location) {
        if (location == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new NullPointerException();
        }
        HANDSHAKE.poll(location);
    }

    @CompilerDirectives.TruffleBoundary
    public static void pollHere(Node location) {
        Objects.requireNonNull(location);
        HANDSHAKE.poll(location);
    }

    @Deprecated(since="22.1")
    public final <T> void setBlocked(Node location, Interrupter interrupter, Interruptible<T> interruptible, T object, Runnable beforeInterrupt, Runnable afterInterrupt) {
        this.setBlockedWithException(location, interrupter, interruptible, object, beforeInterrupt, afterInterrupt == null ? null : t -> afterInterrupt.run());
    }

    public abstract <T> void setBlockedWithException(Node var1, Interrupter var2, Interruptible<T> var3, T var4, Runnable var5, Consumer<Throwable> var6);

    public static <T> void setBlockedThreadInterruptible(Node location, Interruptible<T> interruptible, T object) {
        TruffleSafepoint safepoint = TruffleSafepoint.getCurrent();
        safepoint.setBlockedWithException(location, Interrupter.THREAD_INTERRUPT, interruptible, object, null, null);
    }

    public abstract boolean setAllowActions(boolean var1) throws IllegalStateException;

    public abstract boolean setAllowSideEffects(boolean var1);

    public abstract boolean hasPendingSideEffectingActions();

    public static TruffleSafepoint getCurrent() {
        return HANDSHAKE.getCurrent();
    }

    public static interface Interrupter {
        public static final Interrupter THREAD_INTERRUPT = new Interrupter(){

            @Override
            public void resetInterrupted() {
                Thread.interrupted();
            }

            @Override
            public void interrupt(Thread t) {
                t.interrupt();
            }
        };

        public void interrupt(Thread var1);

        public void resetInterrupted();
    }

    @FunctionalInterface
    public static interface CompiledInterruptible<T>
    extends Interruptible<T> {
        @Override
        public void apply(T var1) throws InterruptedException;
    }

    @FunctionalInterface
    public static interface Interruptible<T> {
        public void apply(T var1) throws InterruptedException;
    }
}

