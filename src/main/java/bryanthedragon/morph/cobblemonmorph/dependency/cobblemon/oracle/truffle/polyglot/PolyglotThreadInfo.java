
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.SpecializationStatistics;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.utilities.TruffleWeakReference;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotFastThreadLocals;
import com.oracle.truffle.polyglot.PolyglotLocals;
import com.oracle.truffle.polyglot.PolyglotThread;
import com.oracle.truffle.polyglot.SuppressFBWarnings;
import java.util.LinkedList;

final class PolyglotThreadInfo {
    static final PolyglotThreadInfo NULL = new PolyglotThreadInfo(null, null, false);
    private static final Object NULL_CLASS_LOADER = new Object();
    final PolyglotContextImpl context;
    @CompilerDirectives.CompilationFinal
    private final TruffleWeakReference<Thread> thread;
    final PolyglotContextImpl polyglotThreadOwnerContext;
    private volatile int enteredCount;
    final LinkedList<Object[]> explicitContextStack = new LinkedList();
    volatile boolean cancelled;
    private Object originalContextClassLoader = NULL_CLASS_LOADER;
    private ClassLoaderEntry prevContextClassLoader;
    private SpecializationStatisticsEntry executionStatisticsEntry;
    private boolean safepointActive;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    Object[] contextThreadLocals;
    final Object[] fastThreadLocals;
    final EncapsulatingNodeReference encapsulatingNodeReference;

    PolyglotThreadInfo(PolyglotContextImpl context, Thread thread, boolean polyglotThreadFirstEnter) {
        this.context = context;
        this.thread = new TruffleWeakReference<Thread>(thread);
        if (thread instanceof PolyglotThread) {
            assert (!polyglotThreadFirstEnter || ((PolyglotThread)thread).getOwnerContext() == context);
            this.polyglotThreadOwnerContext = ((PolyglotThread)thread).getOwnerContext();
        } else {
            this.polyglotThreadOwnerContext = polyglotThreadFirstEnter ? context : null;
        }
        if (context == null) {
            this.encapsulatingNodeReference = null;
            this.fastThreadLocals = null;
        } else {
            this.encapsulatingNodeReference = EngineAccessor.NODES.createEncapsulatingNodeReference(thread);
            this.fastThreadLocals = PolyglotFastThreadLocals.createFastThreadLocals(this);
        }
    }

    Thread getThread() {
        return (Thread)this.thread.get();
    }

    boolean isSafepointActive() {
        assert (this.isCurrent());
        return this.safepointActive;
    }

    public void setSafepointActive(boolean safepointActive) {
        assert (this.isCurrent());
        this.safepointActive = safepointActive;
    }

    public Object[] getContextThreadLocals() {
        assert (Thread.holdsLock(this.context));
        return this.contextThreadLocals;
    }

    public void setContextThreadLocals(Object[] contextThreadLocals) {
        assert (Thread.holdsLock(this.context));
        this.contextThreadLocals = contextThreadLocals;
    }

    boolean isCurrent() {
        return this.getThread() == Thread.currentThread();
    }

    @SuppressFBWarnings(value={"VO_VOLATILE_INCREMENT"})
    Object[] enterInternal() {
        Object[] prev = PolyglotFastThreadLocals.enter(this);
        ++this.enteredCount;
        return prev;
    }

    int getEnteredCount() {
        assert (Thread.currentThread() == this.thread.get());
        return this.enteredCount;
    }

    @SuppressFBWarnings(value={"VO_VOLATILE_INCREMENT"})
    void leaveInternal(Object[] prev) {
        --this.enteredCount;
        PolyglotFastThreadLocals.leave(prev);
    }

    void notifyEnter(PolyglotEngineImpl engine, PolyglotContextImpl profiledContext) {
        if (!engine.customHostClassLoader.isValid()) {
            this.setContextClassLoader();
        }
        EngineAccessor.INSTRUMENT.notifyEnter(engine.instrumentationHandler, profiledContext.creatorTruffleContext);
        if (engine.specializationStatistics != null) {
            this.enterStatistics(engine.specializationStatistics);
        }
    }

    boolean isPolyglotThread(PolyglotContextImpl c) {
        return this.polyglotThreadOwnerContext == c;
    }

    @SuppressFBWarnings(value={"VO_VOLATILE_INCREMENT"})
    void notifyLeave(PolyglotEngineImpl engine, PolyglotContextImpl profiledContext) {
        assert (Thread.currentThread() == this.getThread());
        try {
            EngineAccessor.INSTRUMENT.notifyLeave(engine.instrumentationHandler, profiledContext.creatorTruffleContext);
        }
        finally {
            if (!engine.customHostClassLoader.isValid()) {
                this.restoreContextClassLoader();
            }
            if (engine.specializationStatistics != null) {
                this.leaveStatistics(engine.specializationStatistics);
            }
        }
    }

    Object getThreadLocal(PolyglotLocals.LocalLocation l) {
        return l.readLocal(this.context, this.getThreadLocals(l.engine), true);
    }

    private Object[] getThreadLocals(PolyglotEngineImpl e) {
        CompilerAsserts.partialEvaluationConstant(e);
        Object[] locals = this.contextThreadLocals;
        assert (locals != null) : "thread local not initialized.";
        if (CompilerDirectives.inCompiledCode()) {
            locals = EngineAccessor.RUNTIME.unsafeCast(locals, Object[].class, true, true, true);
        }
        return locals;
    }

    @CompilerDirectives.TruffleBoundary
    private void enterStatistics(SpecializationStatistics statistics) {
        SpecializationStatistics prev = statistics.enter();
        if (prev != null || this.executionStatisticsEntry != null) {
            this.executionStatisticsEntry = new SpecializationStatisticsEntry(prev, this.executionStatisticsEntry);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private void leaveStatistics(SpecializationStatistics statistics) {
        SpecializationStatisticsEntry entry = this.executionStatisticsEntry;
        if (entry == null) {
            statistics.leave(null);
        } else {
            statistics.leave(entry.statistics);
            this.executionStatisticsEntry = entry.next;
        }
    }

    boolean isActiveNotCancelled() {
        return this.getThread() != null && this.enteredCount > 0 && !this.cancelled;
    }

    boolean isActive() {
        return this.getThread() != null && this.enteredCount > 0;
    }

    public String toString() {
        return super.toString() + "[thread=" + this.getThread() + ", enteredCount=" + this.enteredCount + ", cancelled=" + this.cancelled + "]";
    }

    @CompilerDirectives.TruffleBoundary
    private void setContextClassLoader() {
        ClassLoader hostClassLoader = this.context.config.hostClassLoader;
        if (hostClassLoader != null) {
            Thread t = this.getThread();
            ClassLoader original = t.getContextClassLoader();
            assert (this.originalContextClassLoader != NULL_CLASS_LOADER || this.prevContextClassLoader == null);
            if (this.originalContextClassLoader != NULL_CLASS_LOADER) {
                this.prevContextClassLoader = new ClassLoaderEntry((ClassLoader)this.originalContextClassLoader, this.prevContextClassLoader);
            }
            this.originalContextClassLoader = original;
            t.setContextClassLoader(hostClassLoader);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private void restoreContextClassLoader() {
        if (this.originalContextClassLoader != NULL_CLASS_LOADER) {
            assert (this.context.config.hostClassLoader != null);
            Thread t = this.getThread();
            t.setContextClassLoader((ClassLoader)this.originalContextClassLoader);
            if (this.prevContextClassLoader != null) {
                this.originalContextClassLoader = this.prevContextClassLoader.classLoader;
                this.prevContextClassLoader = this.prevContextClassLoader.next;
            } else {
                this.originalContextClassLoader = NULL_CLASS_LOADER;
            }
        }
    }

    private static final class SpecializationStatisticsEntry {
        final SpecializationStatistics statistics;
        final SpecializationStatisticsEntry next;

        SpecializationStatisticsEntry(SpecializationStatistics statistics, SpecializationStatisticsEntry next) {
            this.statistics = statistics;
            this.next = next;
        }
    }

    private static final class ClassLoaderEntry {
        final ClassLoader classLoader;
        final ClassLoaderEntry next;

        ClassLoaderEntry(ClassLoader classLoader, ClassLoaderEntry next) {
            this.classLoader = classLoader;
            this.next = next;
        }
    }
}

