
package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.impl.DefaultRuntimeAccessor;
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
    private static final Map<Thread, TruffleSafepointImpl> SAFEPOINTS = Collections.synchronizedMap(new WeakHashMap());

    static void resetNativeImageState() {
        for (TruffleSafepointImpl impl : SAFEPOINTS.values()) {
            impl.verifyUnused();
        }
        SAFEPOINTS.clear();
    }

    protected ThreadLocalHandshake() {
    }

    public abstract void poll(Node var1);

    public abstract TruffleSafepointImpl getCurrent();

    protected boolean isSupported() {
        return true;
    }

    public void testSupport() {
        if (!this.isSupported()) {
            throw new UnsupportedOperationException("Thread local handshakes are not supported on this platform. A possible reason may be that the underlying JVMCI version is too old.");
        }
    }

    public void setChangeAllowActions(TruffleSafepoint safepoint, boolean enabled) {
        ((TruffleSafepointImpl)safepoint).setChangeAllowActions(enabled);
    }

    public boolean isAllowActions(TruffleSafepoint safepoint) {
        return ((TruffleSafepointImpl)safepoint).isAllowActions();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    public final <T extends Consumer<Node>> Future<Void> runThreadLocal(Thread[] threads, T onThread, Consumer<T> onDone, boolean sideEffecting, boolean syncStartOfEvent, boolean syncEndOfEvent) {
        this.testSupport();
        assert (threads.length > 0);
        Handshake<T> handshake = new Handshake<T>(threads, onThread, onDone, sideEffecting, threads.length, syncStartOfEvent, syncEndOfEvent);
        if (syncStartOfEvent || syncEndOfEvent) {
            Class<ThreadLocalHandshake> clazz = ThreadLocalHandshake.class;
            synchronized (ThreadLocalHandshake.class) {
                this.addHandshakes(threads, handshake);
                // ** MonitorExit[var8_8] (shouldn't be in output)
            }
        } else {
            this.addHandshakes(threads, handshake);
        }
        return handshake;
    }

    private <T extends Consumer<Node>> void addHandshakes(Thread[] threads, Handshake<T> handshake) {
        for (int i = 0; i < threads.length; ++i) {
            Thread t = threads[i];
            if (!t.isAlive()) {
                throw new IllegalStateException("Thread no longer alive with pending handshake.");
            }
            this.getThreadState(t).addHandshake(t, handshake);
        }
    }

    public final boolean activateThread(TruffleSafepoint s, Future<?> f) {
        return ((TruffleSafepointImpl)s).activateThread((Handshake)f);
    }

    public final boolean deactivateThread(TruffleSafepoint s, Future<?> f) {
        return ((TruffleSafepointImpl)s).deactivateThread((Handshake)f);
    }

    public void ensureThreadInitialized() {
    }

    protected abstract void setFastPending(Thread var1);

    @CompilerDirectives.TruffleBoundary
    protected final void processHandshake(Node location) {
        TruffleSafepointImpl s = this.getCurrent();
        if (s.fastPendingSet) {
            s.processHandshakes(location, s.takeHandshakes());
        }
    }

    protected abstract void clearFastPending();

    private static Throwable combineThrowable(Throwable current, Throwable t) {
        if (current == null) {
            return t;
        }
        if (t instanceof ThreadDeath) {
            t.addSuppressed(current);
            return t;
        }
        current.addSuppressed(t);
        return current;
    }

    private static <T extends Throwable> RuntimeException sneakyThrow(Throwable ex) throws T {
        throw ex;
    }

    protected final TruffleSafepointImpl getThreadState(Thread thread) {
        return SAFEPOINTS.computeIfAbsent(thread, t -> new TruffleSafepointImpl(this));
    }

    protected static final class TruffleSafepointImpl
    extends TruffleSafepoint {
        private final ReentrantLock lock = new ReentrantLock();
        private final ThreadLocalHandshake impl;
        private volatile boolean fastPendingSet;
        private boolean sideEffectsEnabled = true;
        private boolean enabled = true;
        private volatile boolean changeAllowActionsAllowed;
        private TruffleSafepoint.Interrupter blockedAction;
        private boolean interrupted;
        private final LinkedList<HandshakeEntry> handshakes = new LinkedList();

        TruffleSafepointImpl(ThreadLocalHandshake handshake) {
            super(DefaultRuntimeAccessor.ENGINE);
            this.impl = handshake;
        }

        void verifyUnused() throws AssertionError {
            if (this.lock.isHeldByCurrentThread() || this.lock.isLocked()) {
                throw new AssertionError((Object)"Invalid locked state for safepoint.");
            }
            this.lock.lock();
            try {
                if (this.blockedAction != null) {
                    throw new AssertionError((Object)"Invalid pending blocked action.");
                }
                if (this.interrupted) {
                    throw new AssertionError((Object)"Invalid pending interrupted state.");
                }
                if (this.isPending()) {
                    throw new AssertionError((Object)"Invalid pending handshakes.");
                }
                if (!this.sideEffectsEnabled) {
                    throw new AssertionError((Object)"Invalid side-effects disabled state");
                }
                if (!this.enabled) {
                    throw new AssertionError((Object)"Invalid allow actions disabled state");
                }
            }
            finally {
                this.lock.unlock();
            }
        }

        void processHandshakes(Node location, List<HandshakeEntry> toProcess) {
            if (toProcess == null) {
                return;
            }
            Throwable ex = null;
            for (HandshakeEntry current : toProcess) {
                if (!this.claimEntry(current)) continue;
                try {
                    current.handshake.perform(location);
                }
                catch (Throwable e) {
                    ex = ThreadLocalHandshake.combineThrowable(ex, e);
                }
            }
            if (this.fastPendingSet) {
                this.resetPending();
            }
            if (ex != null) {
                throw ThreadLocalHandshake.sneakyThrow(ex);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public boolean deactivateThread(Handshake<?> handshake) {
            this.lock.lock();
            try {
                HandshakeEntry current = this.lookupEntry(handshake);
                if (current != null) {
                    assert (!current.reactivated || current.handshake.sideEffecting) : "Reactivated handshake was not processed!";
                    handshake.deactivateThread();
                    this.claimEntry(current);
                    handshake.threads.put(Thread.currentThread(), Boolean.TRUE);
                    this.resetPending();
                    boolean bl = true;
                    return bl;
                }
            }
            finally {
                this.lock.unlock();
            }
            return false;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public boolean activateThread(Handshake<?> handshake) {
            if (handshake.isDone()) {
                return false;
            }
            this.lock.lock();
            try {
                HandshakeEntry current = this.lookupEntry(handshake);
                if (current != null) {
                    boolean bl = false;
                    return bl;
                }
                boolean reactivated = false;
                if (handshake.threads.containsKey(Thread.currentThread())) {
                    if (!handshake.threads.get(Thread.currentThread()).booleanValue()) {
                        boolean bl = false;
                        return bl;
                    }
                    reactivated = true;
                }
                handshake.threads.put(Thread.currentThread(), Boolean.FALSE);
                if (handshake.activateThread()) {
                    this.addHandshakeImpl(Thread.currentThread(), handshake, reactivated);
                    boolean bl = true;
                    return bl;
                }
            }
            finally {
                this.lock.unlock();
            }
            return false;
        }

        private HandshakeEntry lookupEntry(Handshake<?> handshake) {
            assert (this.lock.isHeldByCurrentThread());
            for (HandshakeEntry entry : this.handshakes) {
                if (entry.handshake != handshake) continue;
                return entry;
            }
            return null;
        }

        void addHandshake(Thread t, Handshake<?> handshake) {
            this.lock.lock();
            try {
                this.addHandshakeImpl(t, handshake, false);
            }
            finally {
                this.lock.unlock();
            }
        }

        private void addHandshakeImpl(Thread t, Handshake<?> handshake, boolean reactivated) {
            this.handshakes.add(new HandshakeEntry(handshake, reactivated));
            if (this.isPending()) {
                this.setFastPendingAndInterrupt(t);
            }
        }

        private void setFastPendingAndInterrupt(Thread t) {
            TruffleSafepoint.Interrupter action2;
            assert (this.lock.isHeldByCurrentThread());
            if (!this.fastPendingSet) {
                this.fastPendingSet = true;
                this.impl.setFastPending(t);
            }
            if ((action2 = this.blockedAction) != null) {
                this.interrupted = true;
                action2.interrupt(t);
            }
        }

        List<HandshakeEntry> takeHandshakes() {
            this.lock.lock();
            try {
                if (this.interrupted) {
                    this.blockedAction.resetInterrupted();
                    this.interrupted = false;
                }
                if (this.isPending()) {
                    List<HandshakeEntry> taken = this.takeHandshakeImpl();
                    assert (!taken.isEmpty());
                    List<HandshakeEntry> list = taken;
                    return list;
                }
                List<HandshakeEntry> list = null;
                return list;
            }
            finally {
                this.lock.unlock();
            }
        }

        private void resetPending() {
            this.lock.lock();
            try {
                if (this.fastPendingSet && !this.isPending()) {
                    this.fastPendingSet = false;
                    this.impl.clearFastPending();
                }
            }
            finally {
                this.lock.unlock();
            }
        }

        private boolean claimEntry(HandshakeEntry entry) {
            this.lock.lock();
            try {
                boolean bl = this.handshakes.removeFirstOccurrence(entry);
                return bl;
            }
            finally {
                this.lock.unlock();
            }
        }

        private List<HandshakeEntry> takeHandshakeImpl() {
            if (!this.enabled) {
                return Collections.emptyList();
            }
            ArrayList<HandshakeEntry> toProcess = new ArrayList<HandshakeEntry>(this.handshakes.size());
            for (HandshakeEntry entry : this.handshakes) {
                if (!this.isPending(entry)) continue;
                toProcess.add(entry);
            }
            return toProcess;
        }

        private boolean isPending(HandshakeEntry entry) {
            return this.sideEffectsEnabled || !entry.handshake.sideEffecting;
        }

        @Override
        public <T> void setBlockedWithException(Node location, TruffleSafepoint.Interrupter interrupter, TruffleSafepoint.Interruptible<T> interruptible, T object, Runnable beforeInterrupt, Consumer<Throwable> afterInterrupt) {
            assert (this.impl.getCurrent() == this) : "Cannot be used from a different thread.";
            if (CompilerDirectives.inCompiledCode() && CompilerDirectives.isPartialEvaluationConstant(interruptible) && interruptible instanceof TruffleSafepoint.CompiledInterruptible) {
                this.setBlockedCompiled(location, interrupter, (TruffleSafepoint.CompiledInterruptible)interruptible, object, beforeInterrupt, afterInterrupt);
            } else {
                this.setBlockedBoundary(location, interrupter, interruptible, object, beforeInterrupt, afterInterrupt);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private <T> void setBlockedCompiled(Node location, TruffleSafepoint.Interrupter interrupter, TruffleSafepoint.CompiledInterruptible<T> interruptible, T object, Runnable beforeInterrupt, Consumer<Throwable> afterInterrupt) {
            TruffleSafepoint.Interrupter prev = this.blockedAction;
            try {
                while (true) {
                    try {
                        this.setBlockedImpl(location, interrupter, false);
                        interruptible.apply(object);
                    }
                    catch (InterruptedException e) {
                        this.setBlockedAfterInterrupt(location, prev, beforeInterrupt, afterInterrupt);
                        continue;
                    }
                    break;
                }
            }
            finally {
                this.setBlockedImpl(location, prev, false);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private <T> void setBlockedBoundary(Node location, TruffleSafepoint.Interrupter interrupter, TruffleSafepoint.Interruptible<T> interruptible, T object, Runnable beforeInterrupt, Consumer<Throwable> afterInterrupt) {
            TruffleSafepoint.Interrupter prev = this.blockedAction;
            try {
                while (true) {
                    try {
                        this.setBlockedImpl(location, interrupter, false);
                        interruptible.apply(object);
                    }
                    catch (InterruptedException e) {
                        this.setBlockedAfterInterrupt(location, prev, beforeInterrupt, afterInterrupt);
                        continue;
                    }
                    break;
                }
            }
            finally {
                this.setBlockedImpl(location, prev, false);
            }
        }

        @CompilerDirectives.TruffleBoundary
        private void setBlockedAfterInterrupt(Node location, TruffleSafepoint.Interrupter interrupter, Runnable beforeInterrupt, Consumer<Throwable> afterInterrupt) {
            if (beforeInterrupt != null) {
                beforeInterrupt.run();
            }
            Throwable t = null;
            try {
                this.setBlockedImpl(location, interrupter, true);
            }
            catch (Throwable e) {
                t = e;
                throw e;
            }
            finally {
                if (afterInterrupt != null) {
                    afterInterrupt.accept(t);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private void setBlockedImpl(Node location, TruffleSafepoint.Interrupter interrupter, boolean processSafepoints) {
            List<HandshakeEntry> toProcess = null;
            this.lock.lock();
            try {
                if (processSafepoints && this.isPending()) {
                    toProcess = this.takeHandshakeImpl();
                }
                if (this.interrupted) {
                    assert (this.blockedAction != null);
                    this.blockedAction.resetInterrupted();
                    this.interrupted = false;
                }
                this.blockedAction = interrupter;
            }
            finally {
                this.lock.unlock();
            }
            this.processHandshakes(location, toProcess);
            if (interrupter != null) {
                this.interruptIfPending(interrupter);
            }
        }

        private void interruptIfPending(TruffleSafepoint.Interrupter interrupter) {
            this.lock.lock();
            try {
                if (interrupter != null && this.isPending()) {
                    this.interrupted = true;
                    interrupter.interrupt(Thread.currentThread());
                }
            }
            finally {
                this.lock.unlock();
            }
        }

        private boolean isPending() {
            assert (this.lock.isHeldByCurrentThread());
            if (!this.enabled) {
                return false;
            }
            for (HandshakeEntry entry : this.handshakes) {
                if (!this.isPending(entry)) continue;
                return true;
            }
            return false;
        }

        void setChangeAllowActions(boolean changeAllowActionsAllowed) {
            this.changeAllowActionsAllowed = changeAllowActionsAllowed;
        }

        boolean isAllowActions() {
            return this.enabled;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean setAllowActions(boolean enabled) {
            assert (this.impl.getCurrent() == this) : "Cannot be used from a different thread.";
            this.lock.lock();
            try {
                if (!this.changeAllowActionsAllowed) {
                    throw new IllegalStateException("Using setAllowActions is only permitted during finalization of a language. See TruffleLanguage.finalizeContext(Object) for further details.");
                }
                boolean prev = this.enabled;
                this.enabled = enabled;
                this.updateFastPending();
                boolean bl = prev;
                return bl;
            }
            finally {
                this.lock.unlock();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean setAllowSideEffects(boolean enabled) {
            assert (this.impl.getCurrent() == this) : "Cannot be used from a different thread.";
            this.lock.lock();
            try {
                boolean prev = this.sideEffectsEnabled;
                this.sideEffectsEnabled = enabled;
                this.updateFastPending();
                boolean bl = prev;
                return bl;
            }
            finally {
                this.lock.unlock();
            }
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasPendingSideEffectingActions() {
            assert (this.impl.getCurrent() == this) : "Cannot be used from a different thread.";
            this.lock.lock();
            try {
                boolean bl = !this.sideEffectsEnabled && this.hasSideEffecting();
                return bl;
            }
            finally {
                this.lock.unlock();
            }
        }

        private boolean hasSideEffecting() {
            assert (this.lock.isHeldByCurrentThread());
            for (HandshakeEntry entry : this.handshakes) {
                if (!entry.handshake.sideEffecting) continue;
                return true;
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

    static final class HandshakeEntry {
        final Handshake<?> handshake;
        final boolean reactivated;

        HandshakeEntry(Handshake<?> handshake, boolean reactivated) {
            this.handshake = handshake;
            this.reactivated = reactivated;
        }

        public String toString() {
            return "HandshakeEntry[" + this.handshake + " reactivated=" + this.reactivated + "]";
        }
    }

    public static final class Handshake<T extends Consumer<Node>>
    implements Future<Void> {
        private final boolean sideEffecting;
        private final Phaser phaser;
        private volatile boolean cancelled;
        private final T action;
        private final boolean syncStartOfEvent;
        private final boolean syncEndOfEvent;
        private final Map<Thread, Boolean> threads;
        private final Consumer<T> onDone;

        Handshake(Thread[] initialThreads, T action2, Consumer<T> onDone, boolean sideEffecting, int numberOfThreads, boolean syncStartOfEvent, boolean syncEndOfEvent) {
            this.action = action2;
            this.onDone = onDone;
            this.sideEffecting = sideEffecting;
            this.syncStartOfEvent = syncStartOfEvent;
            this.syncEndOfEvent = syncEndOfEvent;
            this.phaser = new Phaser(numberOfThreads);
            this.threads = new ConcurrentHashMap<Thread, Boolean>(Arrays.stream(initialThreads).collect(Collectors.toMap(t -> t, t -> Boolean.FALSE)));
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
                    this.action.accept((Node)node);
                }
                this.phaser.arriveAndDeregister();
            }
            catch (Throwable throwable) {
                this.phaser.arriveAndDeregister();
                if (this.syncEndOfEvent) {
                    this.phaser.awaitAdvance(this.syncStartOfEvent ? 1 : 0);
                    assert (this.phaser.isTerminated());
                }
                if (this.phaser.isTerminated()) {
                    this.onDone.accept(this.action);
                }
                throw throwable;
            }
            if (this.syncEndOfEvent) {
                this.phaser.awaitAdvance(this.syncStartOfEvent ? 1 : 0);
                assert (this.phaser.isTerminated());
            }
            if (this.phaser.isTerminated()) {
                this.onDone.accept(this.action);
            }
        }

        boolean activateThread() {
            int result = this.phaser.register();
            if (result != 0) {
                this.phaser.arriveAndDeregister();
                return false;
            }
            return true;
        }

        void deactivateThread() {
            this.phaser.arriveAndDeregister();
            if (this.phaser.isTerminated()) {
                this.onDone.accept(this.action);
            }
        }

        @Override
        public Void get() throws InterruptedException {
            if (this.syncStartOfEvent) {
                this.phaser.awaitAdvanceInterruptibly(0);
                this.phaser.awaitAdvanceInterruptibly(1);
            } else {
                this.phaser.awaitAdvanceInterruptibly(0);
            }
            return null;
        }

        @Override
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
            }
            return false;
        }

        public String toString() {
            return "Handshake[action=" + this.action + ", phaser=" + this.phaser + ", cancelled=" + this.cancelled + ", sideEffecting=" + this.sideEffecting + ", syncStartOfEvent=" + this.syncStartOfEvent + ", syncEndOfEvent=" + this.syncEndOfEvent + "]";
        }
    }
}

