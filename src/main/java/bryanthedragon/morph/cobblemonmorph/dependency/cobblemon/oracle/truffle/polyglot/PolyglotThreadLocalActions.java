
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ThreadLocalAction;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.impl.ThreadLocalHandshake;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.OptionValuesImpl;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineOptions;
import com.oracle.truffle.polyglot.PolyglotExceptionImpl;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotThreadInfo;
import com.sun.management.ThreadMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.logging.Level;

final class PolyglotThreadLocalActions {
    private static final Future<Void> COMPLETED_FUTURE = CompletableFuture.completedFuture(null);
    static final ThreadLocalHandshake TL_HANDSHAKE = EngineAccessor.ACCESSOR.runtimeSupport().getThreadLocalHandshake();
    private final PolyglotContextImpl context;
    private final Map<AbstractTLHandshake, Void> activeEvents = new LinkedHashMap<AbstractTLHandshake, Void>();
    @CompilerDirectives.CompilationFinal
    private TruffleLogger logger;
    private long idCounter;
    @CompilerDirectives.CompilationFinal
    private boolean traceActions;
    private List<PolyglotStatisticsAction> statistics;
    private Timer intervalTimer;

    PolyglotThreadLocalActions(PolyglotContextImpl context) {
        this.context = context;
        this.initialize();
    }

    void prepareContextStore() {
        if (this.intervalTimer != null) {
            this.intervalTimer.cancel();
            this.intervalTimer = null;
        }
    }

    void onContextPatch() {
        this.initialize();
    }

    boolean hasActiveEvents() {
        assert (Thread.holdsLock(this.context));
        return !this.activeEvents.isEmpty();
    }

    private void initialize() {
        OptionValuesImpl options = this.context.engine.getEngineOptionValues();
        this.statistics = options.get(PolyglotEngineOptions.SafepointALot) != false ? new ArrayList<PolyglotStatisticsAction>() : null;
        this.traceActions = options.get(PolyglotEngineOptions.TraceThreadLocalActions);
        long interval = options.get(PolyglotEngineOptions.TraceStackTraceInterval);
        if (interval > 0L) {
            this.intervalTimer = new Timer(true);
            this.setupIntervalTimer(interval);
        } else {
            this.intervalTimer = null;
        }
        this.logger = this.statistics != null || this.traceActions || interval > 0L ? this.context.engine.getEngineLogger() : null;
    }

    private void setupIntervalTimer(long interval) {
        this.intervalTimer.schedule(new TimerTask(){

            @Override
            public void run() {
                PolyglotThreadLocalActions.this.submit(null, "engine", (ThreadLocalAction)new PrintStackTraceAction(false, false), true);
            }
        }, interval, interval);
    }

    void notifyEnterCreatedThread() {
        assert (Thread.holdsLock(this.context));
        TL_HANDSHAKE.ensureThreadInitialized();
        if (this.statistics != null) {
            PolyglotStatisticsAction collector = new PolyglotStatisticsAction(Thread.currentThread());
            this.statistics.add(collector);
            this.submit(new Thread[]{Thread.currentThread()}, "engine", (ThreadLocalAction)collector, false);
        }
    }

    void notifyContextClosed() {
        assert (Thread.holdsLock(this.context));
        assert (!this.context.isActive() || this.context.state == PolyglotContextImpl.State.CLOSED_CANCELLED || this.context.state == PolyglotContextImpl.State.CLOSED_EXITED) : "context is still active, cannot flush safepoints";
        if (this.intervalTimer != null) {
            this.intervalTimer.cancel();
        }
        if (!this.activeEvents.isEmpty()) {
            ArrayList<AbstractTLHandshake> activeEventsList = new ArrayList<AbstractTLHandshake>(this.activeEvents.keySet());
            boolean pendingThreadLocalAction = false;
            for (AbstractTLHandshake handshake : activeEventsList) {
                Future<Void> future2 = handshake.future;
                if (future2.isDone()) continue;
                if (this.context.state == PolyglotContextImpl.State.CLOSED_CANCELLED || this.context.state == PolyglotContextImpl.State.CLOSED_EXITED) {
                    future2.cancel(true);
                    pendingThreadLocalAction = true;
                    continue;
                }
                throw new AssertionError((Object)("Pending thread local actions found. Did the actions not process on last leave? Pending action: " + handshake.action));
            }
            if (!pendingThreadLocalAction) {
                this.activeEvents.clear();
            }
        }
        if (this.statistics != null) {
            this.logStatistics();
        }
    }

    private void logStatistics() {
        LongSummaryStatistics all = new LongSummaryStatistics();
        StringBuilder s = new StringBuilder();
        s.append(String.format("Safepoint Statistics %n", new Object[0]));
        s.append(String.format("  -------------------------------------------------------------------------------------- %n", new Object[0]));
        s.append(String.format("   Thread Name         Safepoints | Interval     Avg              Min              Max %n", new Object[0]));
        s.append(String.format("  -------------------------------------------------------------------------------------- %n", new Object[0]));
        for (PolyglotStatisticsAction statistic : this.statistics) {
            all.combine(statistic.intervalStatistics);
            PolyglotThreadLocalActions.formatStatisticLine(s, "  " + statistic.threadName, statistic.intervalStatistics);
        }
        s.append(String.format("  ------------------------------------------------------------------------------------- %n", new Object[0]));
        PolyglotThreadLocalActions.formatStatisticLine(s, "  All threads", all);
        this.logger.log(Level.INFO, s.toString());
        this.statistics.clear();
    }

    private static void formatStatisticLine(StringBuilder s, String label, LongSummaryStatistics statistics) {
        s.append(String.format(" %-20s  %10d | %16.3f us  %12.1f us  %12.1f us%n", label, statistics.getCount() + 1L, statistics.getAverage() / 1000.0, (double)statistics.getMin() / 1000.0, (double)statistics.getMax() / 1000.0));
    }

    Future<Void> submit(Thread[] threads, String originId, ThreadLocalAction action2, boolean needsEnter) {
        boolean sync = EngineAccessor.LANGUAGE.isSynchronousTLAction(action2);
        return this.submit(threads, originId, action2, new HandshakeConfig(needsEnter, sync, sync, false));
    }

    Future<Void> submit(Thread[] threads, String originId, ThreadLocalAction action2, HandshakeConfig config) {
        return this.submit(threads, originId, action2, config, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Future<Void> submit(Thread[] threads, String originId, ThreadLocalAction action2, HandshakeConfig config, RecurringFuture existingFuture) {
        TL_HANDSHAKE.testSupport();
        Objects.requireNonNull(action2);
        if (threads != null) {
            for (int i = 0; i < threads.length; ++i) {
                Objects.requireNonNull(threads[i]);
            }
        }
        PolyglotContextImpl polyglotContextImpl = this.context;
        synchronized (polyglotContextImpl) {
            RecurringFuture future2;
            AbstractTLHandshake handshake;
            this.context.setCachedThreadInfo(PolyglotThreadInfo.NULL);
            if (this.context.state.isClosed() && !config.ignoreContextClosed) {
                return COMPLETED_FUTURE;
            }
            HashSet<Thread> filterThreads = null;
            if (threads != null) {
                filterThreads = new HashSet<Thread>(Arrays.asList(threads));
            }
            boolean recurring = EngineAccessor.LANGUAGE.isRecurringTLAction(action2);
            assert (existingFuture == null || recurring) : "recurring invariant";
            boolean sync = EngineAccessor.LANGUAGE.isSynchronousTLAction(action2);
            boolean sideEffect = EngineAccessor.LANGUAGE.isSideEffectingTLAction(action2);
            ArrayList<Thread> activePolyglotThreads = new ArrayList<Thread>();
            for (PolyglotThreadInfo info : this.context.getSeenThreads().values()) {
                Thread t = info.getThread();
                if (!info.isActive() || filterThreads != null && !filterThreads.contains(t)) continue;
                if (info.isCurrent() && sync && info.isSafepointActive()) {
                    throw new IllegalStateException("Recursive synchronous thread local action detected. They are disallowed as they may cause deadlocks. Schedule an asynchronous thread local action instead.");
                }
                activePolyglotThreads.add(t);
            }
            Thread[] activeThreads = activePolyglotThreads.toArray(new Thread[0]);
            if (sync) {
                assert (config.syncStartOfEvent || config.syncEndOfEvent) : "No synchronization requested for sync event!";
                handshake = new SyncEvent(this.context, threads, originId, action2, config);
            } else {
                assert (!config.syncStartOfEvent) : "Start of event sync requested for async event!";
                assert (!config.syncEndOfEvent) : "End of event sync requested for async event!";
                handshake = new AsyncEvent(this.context, threads, originId, action2, config);
            }
            if (this.traceActions) {
                Object threadLabel = threads == null ? "all-threads" : (threads.length == 1 ? "single-thread" : "multiple-threads-" + threads.length);
                threadLabel = (String)threadLabel + "[alive=" + activePolyglotThreads.size() + "]";
                String sideEffectLabel = sideEffect ? "side-effecting  " : "side-effect-free";
                String syncLabel = sync ? "synchronous " : "asynchronous";
                String recurringLabel = recurring ? "recurring" : "one-shot";
                handshake.debugId = this.idCounter++;
                this.log("submit", handshake, String.format("%-25s  %s  %s %s", threadLabel, sideEffectLabel, syncLabel, recurringLabel));
            }
            if (activeThreads.length > 0) {
                future2 = TL_HANDSHAKE.runThreadLocal(activeThreads, handshake, AbstractTLHandshake::notifyDone, EngineAccessor.LANGUAGE.isSideEffectingTLAction(action2), config.syncStartOfEvent, config.syncEndOfEvent);
                this.activeEvents.put(handshake, null);
            } else {
                future2 = COMPLETED_FUTURE;
                if (recurring) {
                    this.activeEvents.put(handshake, null);
                }
            }
            if (recurring) {
                if (existingFuture != null) {
                    existingFuture.setCurrentFuture(future2);
                    future2 = existingFuture;
                } else {
                    future2 = new RecurringFuture(future2);
                }
            }
            handshake.future = future2;
            return future2;
        }
    }

    private void log(String action2, AbstractTLHandshake handshake, String details) {
        if (this.traceActions) {
            this.logger.log(Level.INFO, String.format("[tl] %-18s %8d  %-30s %-10s %-30s %s", action2, handshake.debugId, "thread[" + Thread.currentThread().getName() + "]", handshake.originId, "action[" + handshake.action.toString() + "]", details));
        }
    }

    Set<ThreadLocalAction> notifyThreadActivation(PolyglotThreadInfo info, boolean active) {
        assert (!active || info.getEnteredCount() == 1) : "must be currently entered successfully";
        assert (Thread.holdsLock(this.context));
        if (this.activeEvents.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet<ThreadLocalAction> updatedActions = new HashSet<ThreadLocalAction>();
        TruffleSafepoint s = TruffleSafepoint.getCurrent();
        ArrayList<AbstractTLHandshake> activeEventsList = new ArrayList<AbstractTLHandshake>(this.activeEvents.keySet());
        for (AbstractTLHandshake handshake : activeEventsList) {
            if (!handshake.isEnabledForThread(Thread.currentThread())) continue;
            Future<Void> f = handshake.future;
            if (f instanceof RecurringFuture) {
                f = ((RecurringFuture)f).getCurrentFuture();
                assert (f != null) : "current future must never be null";
            }
            if (active) {
                if (this.traceActions) {
                    this.log("activate", handshake, "");
                }
                if (f == COMPLETED_FUTURE) {
                    assert (handshake.future instanceof RecurringFuture);
                    handshake.resubmitRecurring();
                    continue;
                }
                if (!TL_HANDSHAKE.activateThread(s, f)) continue;
                updatedActions.add(handshake.action);
                continue;
            }
            if (this.traceActions) {
                this.log("deactivate", handshake, "");
            }
            if (f == COMPLETED_FUTURE) {
                assert (handshake.future instanceof RecurringFuture);
                continue;
            }
            if (!TL_HANDSHAKE.deactivateThread(s, f)) continue;
            updatedActions.add(handshake.action);
        }
        return updatedActions;
    }

    void notifyLastDone(AbstractTLHandshake handshake) {
        assert (Thread.holdsLock(this.context));
        if (this.activeEvents.remove(handshake, null)) {
            if (this.traceActions) {
                if (handshake.future.isCancelled()) {
                    this.log("cancelled", handshake, "");
                } else {
                    this.log("done", handshake, "");
                }
            }
            handshake.resubmitRecurring();
        }
    }

    private static final class PolyglotStatisticsAction
    extends ThreadLocalAction {
        private static volatile ThreadMXBean threadBean;
        private long prevTime = 0L;
        private final LongSummaryStatistics intervalStatistics = new LongSummaryStatistics();
        private final String threadName;

        PolyglotStatisticsAction(Thread thread) {
            super(false, false, true);
            this.threadName = thread.getName();
        }

        @Override
        protected void perform(ThreadLocalAction.Access access) {
            long prev = this.prevTime;
            if (prev != 0L) {
                long now = System.nanoTime();
                this.intervalStatistics.accept(now - prev);
            }
            this.prevTime = System.nanoTime();
        }

        @CompilerDirectives.TruffleBoundary
        static long getCurrentCPUTime() {
            ThreadMXBean bean = threadBean;
            if (bean == null) {
                threadBean = bean = (ThreadMXBean)ManagementFactory.getThreadMXBean();
            }
            return bean.getCurrentThreadCpuTime();
        }

        public String toString() {
            return "PolyglotStatisticsAction@" + Integer.toHexString(this.hashCode());
        }
    }

    private static final class SyncEvent
    extends AbstractTLHandshake {
        SyncEvent(PolyglotContextImpl context, Thread[] filterThreads, String originId, ThreadLocalAction action2, HandshakeConfig config) {
            super(context, filterThreads, originId, action2, config);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        protected void acceptImpl(PolyglotTLAccess access) {
            PolyglotThreadInfo thread;
            PolyglotContextImpl polyglotContextImpl = this.context;
            synchronized (polyglotContextImpl) {
                thread = this.context.getCurrentThreadInfo();
            }
            thread.setSafepointActive(true);
            try {
                EngineAccessor.LANGUAGE.performTLAction(this.action, access);
            }
            finally {
                thread.setSafepointActive(false);
            }
        }
    }

    private static final class AsyncEvent
    extends AbstractTLHandshake {
        AsyncEvent(PolyglotContextImpl context, Thread[] filerThreads, String originId, ThreadLocalAction action2, HandshakeConfig config) {
            super(context, filerThreads, originId, action2, config);
        }

        @Override
        protected void acceptImpl(PolyglotTLAccess access) {
            EngineAccessor.LANGUAGE.performTLAction(this.action, access);
        }
    }

    static abstract class AbstractTLHandshake
    implements Consumer<Node> {
        private final String originId;
        final ThreadLocalAction action;
        long debugId;
        protected final PolyglotContextImpl context;
        final HandshakeConfig config;
        final Thread[] filterThreads;
        Future<Void> future;

        AbstractTLHandshake(PolyglotContextImpl context, Thread[] filterThreads, String originId, ThreadLocalAction action2, HandshakeConfig config) {
            this.action = action2;
            this.originId = originId;
            this.context = context;
            this.config = config;
            this.filterThreads = filterThreads;
        }

        protected final void resubmitRecurring() {
            if (this.future instanceof RecurringFuture) {
                RecurringFuture f = (RecurringFuture)this.future;
                if (!f.cancelled) {
                    this.context.threadLocalActions.submit(this.filterThreads, this.originId, this.action, this.config, f);
                }
            }
        }

        final boolean isEnabledForThread(Thread currentThread) {
            if (this.filterThreads == null) {
                return true;
            }
            for (Thread filterThread : this.filterThreads) {
                if (filterThread != currentThread) continue;
                return true;
            }
            return false;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        final void notifyDone() {
            PolyglotContextImpl polyglotContextImpl = this.context;
            synchronized (polyglotContextImpl) {
                this.context.threadLocalActions.notifyLastDone(this);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public final void accept(Node location) {
            Object prev = null;
            if (this.config.needsEnter) {
                prev = this.context.engine.enterIfNeeded(this.context, false);
            }
            try {
                this.notifyStart();
                PolyglotTLAccess access = new PolyglotTLAccess(Thread.currentThread(), location);
                try {
                    this.acceptImpl(access);
                }
                finally {
                    access.invalid = true;
                }
                this.notifySuccess();
            }
            catch (Throwable t) {
                if (!EngineAccessor.LANGUAGE.isSideEffectingTLAction(this.action) && InteropLibrary.getUncached().isException(t)) {
                    AssertionError e = new AssertionError("Throwing Truffle exception is disallowed in non-side-effecting thread local actions.", t);
                    this.notifyFailed((Throwable)((Object)e));
                    throw e;
                }
                this.notifyFailed(t);
                throw t;
            }
            finally {
                if (this.config.needsEnter) {
                    this.context.engine.leaveIfNeeded(prev, this.context);
                }
            }
        }

        private void notifyStart() {
            this.context.threadLocalActions.log("  perform-start", this, "");
        }

        private void notifySuccess() {
            this.context.threadLocalActions.log("  perform-done", this, "");
        }

        private void notifyFailed(Throwable t) {
            if (this.context.threadLocalActions.traceActions) {
                this.context.threadLocalActions.log("  perform-failed", this, " exception: " + t.toString());
            }
        }

        protected abstract void acceptImpl(PolyglotTLAccess var1);
    }

    static final class HandshakeConfig {
        final boolean needsEnter;
        final boolean syncStartOfEvent;
        final boolean syncEndOfEvent;
        final boolean ignoreContextClosed;

        HandshakeConfig(boolean needsEnter, boolean syncStartOfEvent, boolean syncEndOfEvent, boolean ignoreContextClosed) {
            this.needsEnter = needsEnter;
            this.syncStartOfEvent = syncStartOfEvent;
            this.syncEndOfEvent = syncEndOfEvent;
            this.ignoreContextClosed = ignoreContextClosed;
        }
    }

    static final class PolyglotTLAccess
    extends ThreadLocalAction.Access {
        final Thread thread;
        final Node location;
        volatile boolean invalid;

        PolyglotTLAccess(Thread thread, Node location) {
            super(PolyglotImpl.getInstance());
            this.thread = thread;
            this.location = location;
        }

        @Override
        public Node getLocation() {
            this.checkInvalid();
            return this.location;
        }

        @Override
        public Thread getThread() {
            this.checkInvalid();
            return Thread.currentThread();
        }

        private void checkInvalid() {
            if (this.thread != Thread.currentThread()) {
                throw new IllegalStateException("ThreadLocalAccess used on the wrong thread.");
            }
            if (this.invalid) {
                throw new IllegalStateException("ThreadLocalAccess is no longer valid.");
            }
        }
    }

    private final class PrintStackTraceAction
    extends ThreadLocalAction {
        PrintStackTraceAction(boolean hasSideEffects, boolean synchronous) {
            super(hasSideEffects, synchronous);
        }

        @Override
        protected void perform(ThreadLocalAction.Access access) {
            PolyglotThreadLocalActions.this.logger.log(Level.INFO, String.format("Stack Trace Thread %s: %s", Thread.currentThread().getName(), PolyglotExceptionImpl.printStackToString(PolyglotThreadLocalActions.this.context.getHostContext(), access.getLocation())));
        }
    }

    private static final class RecurringFuture
    implements Future<Void> {
        private volatile Future<Void> firstFuture;
        private volatile Future<Void> currentFuture;
        volatile boolean cancelled;

        RecurringFuture(Future<Void> f) {
            Objects.requireNonNull(f);
            this.firstFuture = f;
            this.currentFuture = f;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            this.cancelled = true;
            return this.currentFuture.cancel(mayInterruptIfRunning);
        }

        @Override
        public Void get() throws InterruptedException, ExecutionException {
            if (this.cancelled) {
                return null;
            }
            Future<Void> first = this.firstFuture;
            if (first == null) {
                return null;
            }
            return first.get();
        }

        @Override
        public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            if (this.cancelled) {
                return null;
            }
            Future<Void> first = this.firstFuture;
            if (first == null) {
                return null;
            }
            return first.get(timeout, unit);
        }

        Future<Void> getCurrentFuture() {
            return this.currentFuture;
        }

        void setCurrentFuture(Future<Void> currentFuture) {
            assert (!(currentFuture instanceof RecurringFuture)) : "no recursive recurring futures";
            assert (currentFuture != null);
            this.firstFuture = null;
            this.currentFuture = currentFuture;
        }

        @Override
        public boolean isCancelled() {
            return this.cancelled;
        }

        @Override
        public boolean isDone() {
            if (this.cancelled) {
                return true;
            }
            Future<Void> first = this.firstFuture;
            if (first == null) {
                return true;
            }
            return first.isDone();
        }
    }
}

