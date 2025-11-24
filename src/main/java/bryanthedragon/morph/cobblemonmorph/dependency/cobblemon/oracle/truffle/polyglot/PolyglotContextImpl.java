
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.ThreadLocalAction;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.impl.DefaultTruffleRuntime;
import com.oracle.truffle.api.impl.JDKAccessor;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.polyglot.ContextPauseHandle;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.FileSystems;
import com.oracle.truffle.polyglot.FinalIntMap;
import com.oracle.truffle.polyglot.HostToGuestRootNode;
import com.oracle.truffle.polyglot.LanguageCache;
import com.oracle.truffle.polyglot.ObjectSizeCalculator;
import com.oracle.truffle.polyglot.OtherContextGuestObject;
import com.oracle.truffle.polyglot.PauseThreadLocalAction;
import com.oracle.truffle.polyglot.PolyglotBindings;
import com.oracle.truffle.polyglot.PolyglotBindingsValue;
import com.oracle.truffle.polyglot.PolyglotContextConfig;
import com.oracle.truffle.polyglot.PolyglotEngineException;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotFastThreadLocals;
import com.oracle.truffle.polyglot.PolyglotHostAccess;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotInstrument;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotLimits;
import com.oracle.truffle.polyglot.PolyglotLocals;
import com.oracle.truffle.polyglot.PolyglotLoggers;
import com.oracle.truffle.polyglot.PolyglotParsedEval;
import com.oracle.truffle.polyglot.PolyglotSharingLayer;
import com.oracle.truffle.polyglot.PolyglotStackFramesRetriever;
import com.oracle.truffle.polyglot.PolyglotThread;
import com.oracle.truffle.polyglot.PolyglotThreadInfo;
import com.oracle.truffle.polyglot.PolyglotThreadLocalActions;
import com.oracle.truffle.polyglot.PolyglotValueDispatch;
import com.oracle.truffle.polyglot.PolyglotWrapper;
import com.oracle.truffle.polyglot.ProcessHandlers;
import com.oracle.truffle.polyglot.SuppressFBWarnings;
import com.oracle.truffle.polyglot.SystemThread;
import com.oracle.truffle.polyglot.WeakAssumedValue;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.logging.Level;
import org.graalvm.options.OptionValues;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class PolyglotContextImpl
implements PolyglotImpl.VMObject {
    private static final TruffleLogger LOG = TruffleLogger.getLogger("engine", PolyglotContextImpl.class);
    private static final InteropLibrary UNCACHED = InteropLibrary.getFactory().getUncached();
    private static final Object[] DISPOSED_CONTEXT_THREAD_LOCALS = new Object[0];
    private static final Map<State, State[]> VALID_TRANSITIONS = new EnumMap<State, State[]>(State.class);
    volatile State state = State.DEFAULT;
    final WeakAssumedValue<PolyglotThreadInfo> singleThreadValue = new WeakAssumedValue("Single thread");
    volatile boolean singleThreaded = true;
    private final Map<Thread, PolyglotThreadInfo> threads = new WeakHashMap<Thread, PolyglotThreadInfo>();
    private volatile PolyglotThreadInfo cachedThreadInfo = PolyglotThreadInfo.NULL;
    volatile Context api;
    private ExecutorService cleanupExecutorService;
    private Future<?> cleanupFuture;
    boolean skipPendingExit;
    volatile int exitCode;
    private volatile String exitMessage;
    volatile Thread closeExitedTriggerThread;
    private volatile String invalidMessage;
    volatile boolean invalidResourceLimit;
    volatile Thread closingThread;
    private final ReentrantLock closingLock = new ReentrantLock();
    private final ReentrantLock interruptingLock = new ReentrantLock();
    private final ReentrantLock initiateCancelOrExitLock = new ReentrantLock();
    private List<Future<Void>> cancellationOrExitingFutures;
    volatile boolean disposing;
    final PolyglotEngineImpl engine;
    final PolyglotSharingLayer layer;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    final PolyglotLanguageContext[] contexts;
    final TruffleContext creatorTruffleContext;
    final TruffleContext currentTruffleContext;
    final PolyglotContextImpl parent;
    volatile Map<String, Value> polyglotBindings;
    volatile Value polyglotHostBindings;
    private final PolyglotBindings polyglotBindingsObject = new PolyglotBindings(this);
    final PolyglotLanguage creator;
    final ContextWeakReference weakReference;
    final Set<ProcessHandlers.ProcessDecorator> subProcesses;
    @CompilerDirectives.CompilationFinal
    PolyglotContextConfig config;
    @CompilerDirectives.CompilationFinal
    private volatile FinalIntMap languageIndexMap;
    private final List<PolyglotContextImpl> childContexts = new ArrayList<PolyglotContextImpl>();
    boolean inContextPreInitialization;
    List<com.oracle.truffle.api.source.Source> sourcesToInvalidate;
    final AtomicLong volatileStatementCounter = new AtomicLong();
    long statementCounter;
    final long statementLimit;
    private volatile Object contextBoundLoggers;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    Object[] contextLocals;
    volatile boolean localsCleared;
    private ObjectSizeCalculator objectSizeCalculator;
    final PolyglotThreadLocalActions threadLocalActions;
    private Collection<Closeable> closeables;
    private final Set<PauseThreadLocalAction> pauseThreadLocalActions = new LinkedHashSet<PauseThreadLocalAction>();
    @CompilerDirectives.CompilationFinal
    private Object hostContextImpl;
    final Node uncachedLocation;
    private final Set<SystemThread.LanguageSystemThread> activeSystemThreads = Collections.newSetFromMap(new HashMap());

    private PolyglotContextImpl() {
        this.engine = null;
        this.contexts = null;
        this.creatorTruffleContext = null;
        this.currentTruffleContext = null;
        this.layer = null;
        this.parent = null;
        this.polyglotHostBindings = null;
        this.polyglotBindings = null;
        this.creator = null;
        this.weakReference = null;
        this.statementLimit = 0L;
        this.threadLocalActions = null;
        this.subProcesses = new HashSet<ProcessHandlers.ProcessDecorator>();
        this.uncachedLocation = null;
    }

    PolyglotContextImpl(PolyglotEngineImpl engine, PolyglotContextConfig config) {
        this.parent = null;
        this.engine = engine;
        this.layer = new PolyglotSharingLayer(engine);
        this.config = config;
        this.creator = null;
        this.uncachedLocation = new UncachedLocationNode(this.layer);
        this.creatorTruffleContext = EngineAccessor.LANGUAGE.createTruffleContext(this, true);
        this.currentTruffleContext = EngineAccessor.LANGUAGE.createTruffleContext(this, false);
        this.weakReference = new ContextWeakReference(this);
        this.contexts = this.createContextArray();
        this.subProcesses = new HashSet<ProcessHandlers.ProcessDecorator>();
        this.statementCounter = this.statementLimit = config.limits != null && config.limits.statementLimit != 0L ? config.limits.statementLimit : 0x7FFFFFFFFFFFFFFEL;
        this.volatileStatementCounter.set(this.statementLimit);
        this.threadLocalActions = new PolyglotThreadLocalActions(this);
        PolyglotEngineImpl.ensureInstrumentsCreated(config.getConfiguredInstruments());
        if (!config.logLevels.isEmpty()) {
            EngineAccessor.LANGUAGE.configureLoggers(this, config.logLevels, this.getAllLoggers());
        }
    }

    PolyglotContextImpl(PolyglotLanguageContext creator, PolyglotContextConfig config) {
        PolyglotContextImpl parent;
        this.parent = parent = creator.context;
        this.layer = new PolyglotSharingLayer(parent.engine);
        this.config = config;
        this.engine = parent.engine;
        this.creator = creator.language;
        this.uncachedLocation = new UncachedLocationNode(this.layer);
        this.statementLimit = 0L;
        this.weakReference = new ContextWeakReference(this);
        this.creatorTruffleContext = EngineAccessor.LANGUAGE.createTruffleContext(this, true);
        this.currentTruffleContext = EngineAccessor.LANGUAGE.createTruffleContext(this, false);
        if (parent.state.isInterrupting()) {
            this.state = State.INTERRUPTING;
        } else if (parent.state.isCancelling()) {
            this.state = State.CANCELLING;
        } else if (parent.state.isExiting()) {
            this.state = State.EXITING;
        }
        this.invalidMessage = this.parent.invalidMessage;
        this.exitCode = this.parent.exitCode;
        this.exitMessage = this.parent.exitMessage;
        this.contextBoundLoggers = this.parent.contextBoundLoggers;
        this.threadLocalActions = new PolyglotThreadLocalActions(this);
        if (!parent.config.logLevels.isEmpty()) {
            EngineAccessor.LANGUAGE.configureLoggers(this, parent.config.logLevels, this.getAllLoggers());
        }
        this.contexts = this.createContextArray();
        this.subProcesses = new HashSet<ProcessHandlers.ProcessDecorator>();
        this.engine.noInnerContexts.invalidate();
    }

    private boolean isTransitionAllowed(State fromState, State toState) {
        State[] successors;
        assert (Thread.holdsLock(this));
        for (State successor : successors = VALID_TRANSITIONS.get((Object)fromState)) {
            if (successor != toState) continue;
            return this.isAdditionalTransitionConditionSatisfied(fromState, toState);
        }
        return false;
    }

    private boolean isAdditionalTransitionConditionSatisfied(State fromState, State toState) {
        assert (Thread.holdsLock(this));
        if (fromState.isClosing() != toState.isClosing() && this.closingThread != Thread.currentThread()) {
            return false;
        }
        return fromState.isExiting() || !toState.isExiting() || fromState == State.PENDING_EXIT || fromState == State.CLOSING_PENDING_EXIT || this.parent != null || this.skipPendingExit;
    }

    private boolean shouldCacheThreadInfo() {
        assert (Thread.holdsLock(this));
        return this.state.shouldCacheThreadInfo() && !this.disposing;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void claimSharingLayer(PolyglotLanguage language) {
        PolyglotSharingLayer s = this.layer;
        if (!s.isClaimed()) {
            Object object = this.engine.lock;
            synchronized (object) {
                if (!s.isClaimed()) {
                    assert (!language.isHost()) : "cannot claim context for a host language";
                    this.engine.claimSharingLayer(s, this, language);
                    assert (s.isClaimed());
                    this.weakReference.layer = s;
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean claimSharingLayer(PolyglotSharingLayer sharableLayer, Set<PolyglotLanguage> languages) {
        PolyglotSharingLayer s = this.layer;
        Object object = this.engine.lock;
        synchronized (object) {
            assert (!s.isClaimed()) : "sharing layer already claimed";
            if (!s.isClaimed()) {
                if (!s.claimLayerForContext(sharableLayer, this, languages)) {
                    return false;
                }
                assert (s.isClaimed());
                assert (this.layer.equals(sharableLayer));
                this.weakReference.layer = s;
            }
        }
        return true;
    }

    OptionValues getInstrumentContextOptions(PolyglotInstrument instrument) {
        return this.config.getInstrumentOptionValues(instrument);
    }

    public void resetLimits() {
        PolyglotLanguageContext languageContext = this.getHostContext();
        Object prev = PolyglotValueDispatch.hostEnter(languageContext);
        try {
            PolyglotLimits.reset(this);
            EngineAccessor.INSTRUMENT.notifyContextResetLimit(this.engine, this.creatorTruffleContext);
        }
        catch (Throwable e) {
            throw PolyglotImpl.guestToHostException(languageContext, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(languageContext, prev);
        }
    }

    public void safepoint() {
        PolyglotLanguageContext languageContext = this.getHostContext();
        Object prev = PolyglotValueDispatch.hostEnter(languageContext);
        try {
            TruffleSafepoint.poll(this.uncachedLocation);
        }
        catch (Throwable e) {
            throw PolyglotImpl.guestToHostException(languageContext, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(languageContext, prev);
        }
    }

    private PolyglotLanguageContext[] createContextArray() {
        PolyglotLanguageContext hostContext;
        Collection<PolyglotLanguage> languages = this.engine.idToLanguage.values();
        PolyglotLanguageContext[] newContexts = new PolyglotLanguageContext[this.engine.languageCount];
        Iterator<PolyglotLanguage> languageIterator = languages.iterator();
        for (int i = 1; i < this.engine.languageCount; ++i) {
            PolyglotLanguage language = languageIterator.next();
            newContexts[i] = new PolyglotLanguageContext(this, language);
        }
        PolyglotLanguage hostLanguage = this.engine.hostLanguage;
        newContexts[0] = hostContext = new PolyglotLanguageContext(this, hostLanguage);
        hostContext.ensureCreated(hostLanguage);
        hostContext.ensureInitialized(null);
        return newContexts;
    }

    PolyglotLanguageContext getContext(PolyglotLanguage language) {
        return this.contexts[language.engineIndex];
    }

    Object getContextImpl(PolyglotLanguage language) {
        return this.contexts[language.engineIndex].getContextImpl();
    }

    PolyglotLanguageContext getContextInitialized(PolyglotLanguage language, PolyglotLanguage accessingLanguage) {
        PolyglotLanguageContext context = this.getContext(language);
        context.ensureInitialized(accessingLanguage);
        return context;
    }

    void notifyContextCreated() {
        EngineAccessor.INSTRUMENT.notifyContextCreated(this.engine, this.creatorTruffleContext);
    }

    void addChildContext(PolyglotContextImpl child) {
        assert (Thread.holdsLock(this));
        assert (!this.state.isClosed());
        if (this.state.isClosing() && !this.state.shouldCacheThreadInfo()) {
            throw PolyglotEngineException.illegalState("Adding child context into a closing context.");
        }
        this.childContexts.add(child);
    }

    static PolyglotContextImpl requireContext() {
        PolyglotContextImpl context = PolyglotFastThreadLocals.getContext(null);
        if (context == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw PolyglotEngineException.illegalState("There is no current context available.");
        }
        return context;
    }

    public synchronized void explicitEnter() {
        try {
            Object[] prev = this.engine.enter(this);
            PolyglotThreadInfo current = this.getCurrentThreadInfo();
            assert (current.getThread() == Thread.currentThread());
            current.explicitContextStack.addLast(prev);
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(this.engine, t);
        }
    }

    public synchronized void explicitLeave() {
        if (this.state.isClosed()) {
            return;
        }
        try {
            PolyglotThreadInfo current = this.getCurrentThreadInfo();
            LinkedList<Object[]> stack = current.explicitContextStack;
            if (stack.isEmpty() || current.getThread() == null) {
                throw PolyglotEngineException.illegalState("The context is not entered explicity. A context can only be left if it was previously entered.");
            }
            this.engine.leave(stack.removeLast(), this);
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(this.engine, t);
        }
    }

    synchronized Future<Void> pause() {
        PauseThreadLocalAction pauseAction = new PauseThreadLocalAction(this);
        Future<Void> future2 = this.threadLocalActions.submit(null, "engine", (ThreadLocalAction)pauseAction, new PolyglotThreadLocalActions.HandshakeConfig(true, true, false, false));
        this.pauseThreadLocalActions.add(pauseAction);
        return new ContextPauseHandle(pauseAction, future2);
    }

    void resume(Future<Void> pauseFuture) {
        if (!(pauseFuture instanceof ContextPauseHandle) || ((ContextPauseHandle)pauseFuture).pauseThreadLocalAction.context != this) {
            throw new IllegalArgumentException("Resume method was not passed a valid pause future!");
        }
        ContextPauseHandle pauseHandle = (ContextPauseHandle)pauseFuture;
        pauseHandle.resume();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    Object[] enterThreadChanged(boolean notifyEnter, boolean enterReverted, boolean pollSafepoint, boolean deactivateSafepoints, boolean polyglotThreadFirstEnter) {
        Object[] objectArray;
        PolyglotThreadInfo enteredThread = null;
        Object[] prev = null;
        Thread current = Thread.currentThread();
        if (JDKAccessor.isVirtualThread(current) && !(Truffle.getRuntime() instanceof DefaultTruffleRuntime)) {
            throw PolyglotEngineException.illegalState("Using polyglot contexts on Java virtual threads is currently not supported with an optimizing Truffle runtime. As a workaround you may add the -Dtruffle.TruffleRuntime=com.oracle.truffle.api.impl.DefaultTruffleRuntime JVM argument to switch to a non-optimizing runtime when using virtual threads. Please note that performance is severly reduced in this mode. Loom support for optimizing runtimes will be added in a future release.");
        }
        try {
            if (current instanceof SystemThread) {
                throw PolyglotEngineException.illegalState("Context cannot be entered on system threads.");
            }
            boolean needsInitialization = false;
            objectArray = this;
            synchronized (this) {
                boolean transitionToMultiThreading;
                PolyglotThreadInfo threadInfo = this.getCurrentThreadInfo();
                if (enterReverted && threadInfo.getEnteredCount() == 0) {
                    this.threadLocalActions.notifyThreadActivation(threadInfo, false);
                    if ((this.state.isCancelling() || this.state.isExiting() || this.state == State.CLOSED_CANCELLED || this.state == State.CLOSED_EXITED) && !threadInfo.isActive()) {
                        this.notifyThreadClosed(threadInfo);
                    }
                    if ((this.state.isInterrupting() || this.state == State.CLOSED_INTERRUPTED) && !threadInfo.isActive()) {
                        Thread.interrupted();
                        this.notifyAll();
                    }
                }
                if (deactivateSafepoints && threadInfo != PolyglotThreadInfo.NULL) {
                    this.threadLocalActions.notifyThreadActivation(threadInfo, false);
                }
                this.checkClosedOrDisposing();
                assert (threadInfo != null);
                threadInfo = this.threads.get(current);
                if (threadInfo == null) {
                    threadInfo = this.createThreadInfo(current, polyglotThreadFirstEnter);
                    needsInitialization = true;
                }
                if (this.singleThreaded) {
                    this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
                }
                boolean bl = transitionToMultiThreading = this.isSingleThreaded() && this.hasActiveOtherThread(true);
                if (transitionToMultiThreading) {
                    this.checkAllThreadAccesses(Thread.currentThread(), false);
                }
                if (transitionToMultiThreading) {
                    this.engine.singleThreadPerContext.invalidate();
                    this.singleThreaded = false;
                }
                if (needsInitialization) {
                    this.threads.put(current, threadInfo);
                }
                if (needsInitialization) {
                    this.initializeThreadLocals(threadInfo);
                }
                prev = threadInfo.enterInternal();
                if (needsInitialization) {
                    this.threadLocalActions.notifyEnterCreatedThread();
                }
                if (notifyEnter) {
                    try {
                        threadInfo.notifyEnter(this.engine, (PolyglotContextImpl)this);
                    }
                    catch (Throwable t) {
                        threadInfo.leaveInternal(prev);
                        throw t;
                    }
                }
                enteredThread = threadInfo;
                Set<ThreadLocalAction> activatedActions = null;
                if (enteredThread.getEnteredCount() == 1 && !deactivateSafepoints) {
                    activatedActions = this.threadLocalActions.notifyThreadActivation(threadInfo, true);
                }
                if (transitionToMultiThreading) {
                    this.transitionToMultiThreaded();
                }
                if (needsInitialization) {
                    this.initializeNewThread(current);
                }
                if (enteredThread.getEnteredCount() == 1 && !this.pauseThreadLocalActions.isEmpty()) {
                    Iterator<PauseThreadLocalAction> threadLocalActionIterator = this.pauseThreadLocalActions.iterator();
                    while (threadLocalActionIterator.hasNext()) {
                        PauseThreadLocalAction threadLocalAction = threadLocalActionIterator.next();
                        if (!threadLocalAction.isPause()) {
                            threadLocalActionIterator.remove();
                            continue;
                        }
                        if (activatedActions != null && activatedActions.contains(threadLocalAction)) continue;
                        this.threadLocalActions.submit(new Thread[]{Thread.currentThread()}, "engine", (ThreadLocalAction)threadLocalAction, new PolyglotThreadLocalActions.HandshakeConfig(true, true, false, false));
                    }
                }
                this.setCachedThreadInfo(threadInfo);
                // ** MonitorExit[var10_10] (shouldn't be in output)
                if (needsInitialization) {
                    EngineAccessor.INSTRUMENT.notifyThreadStarted(this.engine, this.creatorTruffleContext, current);
                }
                objectArray = prev;
                if (!pollSafepoint) break block36;
            }
        }
        catch (Throwable throwable) {
            if (pollSafepoint) {
                try {
                    TruffleSafepoint.pollHere(this.uncachedLocation);
                }
                catch (Throwable t) {
                    if (enteredThread != null) {
                        this.leaveThreadChanged(prev, notifyEnter, true, polyglotThreadFirstEnter);
                    }
                    throw t;
                }
            }
            throw throwable;
        }
        {
            block36: {
                try {
                    TruffleSafepoint.pollHere(this.uncachedLocation);
                }
                catch (Throwable t) {
                    if (enteredThread != null) {
                        this.leaveThreadChanged(prev, notifyEnter, true, polyglotThreadFirstEnter);
                    }
                    throw t;
                }
            }
            return objectArray;
        }
    }

    PolyglotThreadInfo getCachedThread() {
        PolyglotThreadInfo info;
        if (CompilerDirectives.inCompiledCode() && CompilerDirectives.isPartialEvaluationConstant(this)) {
            info = this.singleThreadValue.getConstant();
            if (info == null) {
                info = this.cachedThreadInfo;
            }
        } else {
            info = this.cachedThreadInfo;
        }
        return info;
    }

    PolyglotThreadInfo getCurrentThreadInfo() {
        CompilerAsserts.neverPartOfCompilation();
        assert (Thread.holdsLock(this));
        PolyglotThreadInfo info = this.getCachedThread();
        if (info.getThread() != Thread.currentThread() && (info = this.threads.get(Thread.currentThread())) == null) {
            info = PolyglotThreadInfo.NULL;
        }
        assert (info.getThread() == null || info.getThread() == Thread.currentThread());
        return info;
    }

    void setCachedThreadInfo(PolyglotThreadInfo info) {
        this.cachedThreadInfo = !this.shouldCacheThreadInfo() || this.threadLocalActions.hasActiveEvents() ? PolyglotThreadInfo.NULL : info;
    }

    synchronized void checkMultiThreadedAccess(PolyglotThread newThread) {
        boolean singleThread = this.singleThreaded ? !this.isActiveNotCancelled() : false;
        this.checkAllThreadAccesses(newThread, singleThread);
    }

    private void checkAllThreadAccesses(Thread enteringThread, boolean singleThread) {
        assert (Thread.holdsLock(this));
        ArrayList<PolyglotLanguage> deniedLanguages = null;
        for (PolyglotLanguageContext context : this.contexts) {
            if (!context.isInitialized()) continue;
            boolean accessAllowed = true;
            if (!EngineAccessor.LANGUAGE.isThreadAccessAllowed(context.env, enteringThread, singleThread)) {
                accessAllowed = false;
            }
            if (accessAllowed) {
                for (PolyglotThreadInfo seenThread : this.threads.values()) {
                    if (EngineAccessor.LANGUAGE.isThreadAccessAllowed(context.env, seenThread.getThread(), singleThread)) continue;
                    accessAllowed = false;
                    break;
                }
            }
            if (accessAllowed) continue;
            if (deniedLanguages == null) {
                deniedLanguages = new ArrayList<PolyglotLanguage>();
            }
            deniedLanguages.add(context.language);
        }
        if (deniedLanguages != null) {
            throw PolyglotContextImpl.throwDeniedThreadAccess(enteringThread, singleThread, deniedLanguages);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    void leaveThreadChanged(Object[] prev, boolean notifyLeft, boolean entered, boolean dispose) {
        Throwable ex = null;
        PolyglotContextImpl polyglotContextImpl = this;
        synchronized (polyglotContextImpl) {
            PolyglotThreadInfo info;
            Thread current = Thread.currentThread();
            if (dispose) {
                info = this.threads.get(current);
                if (info == null) {
                    return;
                }
                ex = this.notifyThreadDisposing(current);
            }
            this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
            PolyglotThreadInfo threadInfo = this.threads.get(current);
            assert (threadInfo != null);
            info = threadInfo;
            if (entered) {
                try {
                    if (notifyLeft) {
                        info.notifyLeave(this.engine, this);
                    }
                }
                finally {
                    info.leaveInternal(prev);
                }
            }
            if (threadInfo.getEnteredCount() == 0) {
                this.threadLocalActions.notifyThreadActivation(threadInfo, false);
            }
            if ((this.state.isCancelling() || this.state.isExiting() || this.state == State.CLOSED_CANCELLED || this.state == State.CLOSED_EXITED) && !info.isActive()) {
                this.notifyThreadClosed(info);
            }
            boolean somePauseThreadLocalActionIsActive = false;
            if (threadInfo.getEnteredCount() == 0 && !this.pauseThreadLocalActions.isEmpty()) {
                Iterator<PauseThreadLocalAction> threadLocalActionIterator = this.pauseThreadLocalActions.iterator();
                while (threadLocalActionIterator.hasNext()) {
                    PauseThreadLocalAction threadLocalAction = threadLocalActionIterator.next();
                    if (!threadLocalAction.isPause()) {
                        threadLocalActionIterator.remove();
                        continue;
                    }
                    somePauseThreadLocalActionIsActive = true;
                }
            }
            if (entered && !somePauseThreadLocalActionIsActive) {
                this.setCachedThreadInfo(threadInfo);
            }
            if ((this.state.isInterrupting() || this.state == State.CLOSED_INTERRUPTED) && !info.isActive()) {
                Thread.interrupted();
                this.notifyAll();
            }
            if (dispose) {
                this.finishThreadDispose(current, info, ex);
            }
        }
    }

    private void finishThreadDispose(Thread current, PolyglotThreadInfo info, Throwable ex) {
        assert (!info.isActive());
        if (this.cachedThreadInfo.getThread() == current) {
            this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
        }
        info.setContextThreadLocals(DISPOSED_CONTEXT_THREAD_LOCALS);
        this.threads.remove(current);
        if (ex != null) {
            throw PolyglotContextImpl.sneakyThrow(ex);
        }
    }

    private Throwable notifyThreadDisposing(Thread current) {
        Throwable ex = null;
        for (PolyglotLanguageContext languageContext : this.contexts) {
            if (!languageContext.isInitialized()) continue;
            try {
                EngineAccessor.LANGUAGE.disposeThread(languageContext.env, current);
            }
            catch (Throwable t) {
                if (ex == null) {
                    ex = t;
                    continue;
                }
                ex.addSuppressed(t);
            }
        }
        try {
            EngineAccessor.INSTRUMENT.notifyThreadFinished(this.engine, this.creatorTruffleContext, current);
        }
        catch (Throwable t) {
            if (ex == null) {
                ex = t;
            }
            ex.addSuppressed(t);
        }
        return ex;
    }

    private void initializeNewThread(Thread thread) {
        for (PolyglotLanguageContext context : this.contexts) {
            if (!context.isInitialized()) continue;
            EngineAccessor.LANGUAGE.initializeThread(context.env, thread);
        }
    }

    long getStatementsExecuted() {
        long count = this.engine.singleThreadPerContext.isValid() ? this.statementCounter : this.volatileStatementCounter.get();
        return this.statementLimit - count;
    }

    private void transitionToMultiThreaded() {
        assert (Thread.holdsLock(this));
        for (PolyglotLanguageContext context : this.contexts) {
            if (!context.isInitialized()) continue;
            context.ensureMultiThreadingInitialized();
        }
        this.singleThreaded = false;
        this.singleThreadValue.invalidate();
        long statementsExecuted = this.statementLimit - this.statementCounter;
        this.volatileStatementCounter.getAndAdd(-statementsExecuted);
    }

    private PolyglotThreadInfo createThreadInfo(Thread current, boolean polyglotThreadFirstEnter) {
        assert (Thread.holdsLock(this));
        PolyglotThreadInfo threadInfo = new PolyglotThreadInfo(this, current, polyglotThreadFirstEnter);
        boolean singleThread = this.isSingleThreaded();
        ArrayList<PolyglotLanguage> deniedLanguages = null;
        for (PolyglotLanguageContext context : this.contexts) {
            if (!context.isInitialized() || EngineAccessor.LANGUAGE.isThreadAccessAllowed(context.env, current, singleThread)) continue;
            if (deniedLanguages == null) {
                deniedLanguages = new ArrayList<PolyglotLanguage>();
            }
            deniedLanguages.add(context.language);
        }
        if (deniedLanguages != null) {
            throw PolyglotContextImpl.throwDeniedThreadAccess(current, singleThread, deniedLanguages);
        }
        this.singleThreadValue.update(threadInfo);
        return threadInfo;
    }

    static RuntimeException throwDeniedThreadAccess(Thread current, boolean accessSingleThreaded, List<PolyglotLanguage> deniedLanguages) {
        StringBuilder languagesString = new StringBuilder("");
        for (PolyglotLanguage language : deniedLanguages) {
            if (languagesString.length() != 0) {
                languagesString.append(", ");
            }
            languagesString.append(language.getId());
        }
        String message = accessSingleThreaded ? String.format("Single threaded access requested by thread %s but is not allowed for language(s) %s.", current, languagesString) : String.format("Multi threaded access requested by thread %s but is not allowed for language(s) %s.", current, languagesString);
        throw PolyglotEngineException.illegalState(message);
    }

    public Value getBindings(String languageId) {
        PolyglotLanguageContext languageContext = this.lookupLanguageContext(languageId);
        assert (languageContext != null);
        Object prev = PolyglotValueDispatch.hostEnter(languageContext);
        try {
            if (!languageContext.isInitialized()) {
                languageContext.ensureInitialized(null);
            }
            Value value2 = languageContext.getHostBindings();
            return value2;
        }
        catch (Throwable e) {
            throw PolyglotImpl.guestToHostException(languageContext, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(languageContext, prev);
        }
    }

    public Value getPolyglotBindings() {
        try {
            this.checkClosed();
            Value bindings = this.polyglotHostBindings;
            if (bindings == null) {
                this.initPolyglotBindings();
                bindings = this.polyglotHostBindings;
            }
            return bindings;
        }
        catch (Throwable e) {
            throw PolyglotImpl.guestToHostException(this.engine, e);
        }
    }

    public Map<String, Value> getPolyglotGuestBindings() {
        Map<String, Value> bindings = this.polyglotBindings;
        if (bindings == null) {
            this.initPolyglotBindings();
            bindings = this.polyglotBindings;
        }
        return bindings;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initPolyglotBindings() {
        PolyglotContextImpl polyglotContextImpl = this;
        synchronized (polyglotContextImpl) {
            if (this.polyglotBindings == null) {
                this.polyglotBindings = new ConcurrentHashMap<String, Value>();
                PolyglotLanguageContext hostContext = this.getHostContext();
                PolyglotBindings bindings = new PolyglotBindings(hostContext);
                this.polyglotHostBindings = this.getAPIAccess().newValue(new PolyglotBindingsValue(hostContext, bindings), hostContext, bindings);
            }
        }
    }

    public Object getPolyglotBindingsObject() {
        return this.polyglotBindingsObject;
    }

    void checkClosedOrDisposing() {
        this.checkCancelled();
        if (this.state.isClosed() || this.disposing) {
            throw PolyglotEngineException.closedException("The Context is already closed.");
        }
    }

    void checkClosed() {
        this.checkCancelled();
        if (this.state.isClosed()) {
            throw PolyglotEngineException.closedException("The Context is already closed.");
        }
    }

    private void checkCancelled() {
        if (this.state.isInvalidOrClosed() && this.closingThread != Thread.currentThread() && this.invalidMessage != null) {
            if (this.exitMessage == null) {
                throw this.createCancelException(null);
            }
            throw this.createExitException(null);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private RuntimeException failValueSharing() {
        throw new PolyglotLanguageContext.ValueMigrationException("A value was tried to be migrated from one context to a different context. Value migration for the current context was disabled and is therefore disallowed.", this.uncachedLocation);
    }

    Object migrateValue(Object value2, PolyglotContextImpl valueContext) {
        if (!this.config.allowValueSharing) {
            throw this.failValueSharing();
        }
        Object result = this.engine.host.migrateValue(this, value2, valueContext);
        if (result != null) {
            return result;
        }
        assert (value2 instanceof TruffleObject);
        if (value2 instanceof OtherContextGuestObject) {
            OtherContextGuestObject otherValue = (OtherContextGuestObject)value2;
            if (otherValue.receiverContext == this && otherValue.delegateContext == valueContext) {
                return otherValue;
            }
            if (otherValue.receiverContext == valueContext && otherValue.delegateContext == this) {
                return otherValue.delegate;
            }
            return new OtherContextGuestObject(this, otherValue.delegate, valueContext);
        }
        assert (value2 instanceof TruffleObject);
        return new OtherContextGuestObject(this, value2, valueContext);
    }

    Object migrateHostWrapper(PolyglotWrapper wrapper) {
        Object wrapped = wrapper.getGuestObject();
        PolyglotContextImpl valueContext = wrapper.getContext();
        if (valueContext != this) {
            wrapped = this.migrateValue(wrapped, valueContext);
        }
        return wrapped;
    }

    PolyglotLanguageContext getHostContext() {
        return this.contexts[0];
    }

    Object getHostContextImpl() {
        return this.hostContextImpl;
    }

    @Override
    public PolyglotEngineImpl getEngine() {
        return this.engine;
    }

    PolyglotLanguageContext getLanguageContext(Class<? extends TruffleLanguage<?>> languageClass) {
        if (CompilerDirectives.isPartialEvaluationConstant(this)) {
            return this.getLanguageContextImpl(languageClass);
        }
        return this.getLanguageContextBoundary(languageClass);
    }

    @CompilerDirectives.TruffleBoundary
    private PolyglotLanguageContext getLanguageContextBoundary(Class<? extends TruffleLanguage<?>> languageClass) {
        return this.getLanguageContextImpl(languageClass);
    }

    PolyglotLanguageContext findLanguageContext(Class<? extends TruffleLanguage> languageClazz) {
        PolyglotLanguage directLanguage = this.engine.getLanguage(languageClazz, false);
        if (directLanguage != null) {
            return this.getContext(directLanguage);
        }
        for (PolyglotLanguageContext lang : this.contexts) {
            if (!lang.isInitialized()) continue;
            TruffleLanguage<?> language = EngineAccessor.LANGUAGE.getLanguage(lang.env);
            if (languageClazz == TruffleLanguage.class || !languageClazz.isInstance(language)) continue;
            return lang;
        }
        HashSet<String> languageNames = new HashSet<String>();
        for (PolyglotLanguageContext lang : this.contexts) {
            if (!lang.isInitialized()) continue;
            languageNames.add(lang.language.cache.getClassName());
        }
        throw PolyglotEngineException.illegalState("Cannot find language " + languageClazz + " among " + languageNames);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PolyglotLanguageContext getLanguageContextImpl(Class<? extends TruffleLanguage<?>> languageClass) {
        int indexValue;
        FinalIntMap map = this.languageIndexMap;
        int n = indexValue = map != null ? map.get(languageClass) : -1;
        if (indexValue == -1) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            PolyglotContextImpl polyglotContextImpl = this;
            synchronized (polyglotContextImpl) {
                if (this.languageIndexMap == null) {
                    this.languageIndexMap = new FinalIntMap();
                }
                if ((indexValue = this.languageIndexMap.get(languageClass)) == -1) {
                    PolyglotLanguageContext context = this.findLanguageContext(languageClass);
                    indexValue = context.language.engineIndex;
                    this.languageIndexMap.put(languageClass, indexValue);
                }
            }
        }
        PolyglotLanguageContext context = this.contexts[indexValue];
        return context;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void initializeInnerContextLanguage(String languageId) {
        PolyglotLanguage language = this.engine.idToLanguage.get(languageId);
        assert (language != null) : "language creating the inner context not be found";
        Object prev = this.engine.enterIfNeeded(this, true);
        try {
            this.initializeLanguage(language);
        }
        finally {
            this.engine.leaveIfNeeded(prev, this);
        }
    }

    private boolean initializeLanguage(PolyglotLanguage language) {
        PolyglotLanguageContext languageContext = this.getContext(language);
        assert (languageContext != null);
        languageContext.checkAccess(null);
        if (!languageContext.isInitialized()) {
            return languageContext.ensureInitialized(null);
        }
        return false;
    }

    public boolean initializeLanguage(String languageId) {
        PolyglotLanguageContext languageContext = this.lookupLanguageContext(languageId);
        Object prev = PolyglotValueDispatch.hostEnter(languageContext);
        try {
            boolean bl = this.initializeLanguage(languageContext.language);
            return bl;
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(languageContext, t, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(languageContext, prev);
        }
    }

    public Value parse(String languageId, Source source) {
        PolyglotLanguageContext languageContext = this.lookupLanguageContext(languageId);
        assert (languageContext != null);
        Object prev = PolyglotValueDispatch.hostEnter(languageContext);
        try {
            com.oracle.truffle.api.source.Source truffleSource = (com.oracle.truffle.api.source.Source)this.getAPIAccess().getReceiver(source);
            languageContext.checkAccess(null);
            languageContext.ensureInitialized(null);
            CallTarget target = languageContext.parseCached(null, truffleSource, null);
            Value value2 = languageContext.asValue(new PolyglotParsedEval(languageContext, truffleSource, target));
            return value2;
        }
        catch (Throwable e) {
            throw PolyglotImpl.guestToHostException(languageContext, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(languageContext, prev);
        }
    }

    private PolyglotLanguageContext lookupLanguageContext(String languageId) {
        PolyglotLanguageContext languageContext;
        try {
            PolyglotLanguage language = this.requirePublicLanguage(languageId);
            languageContext = this.getContext(language);
        }
        catch (Throwable e) {
            throw PolyglotImpl.guestToHostException(this.engine, e);
        }
        return languageContext;
    }

    public Value eval(String languageId, Source source) {
        PolyglotLanguageContext languageContext = this.lookupLanguageContext(languageId);
        assert (languageContext != null);
        Object prev = PolyglotValueDispatch.hostEnter(languageContext);
        try {
            Value hostValue;
            com.oracle.truffle.api.source.Source truffleSource = (com.oracle.truffle.api.source.Source)this.getAPIAccess().getReceiver(source);
            languageContext.checkAccess(null);
            languageContext.ensureInitialized(null);
            CallTarget target = languageContext.parseCached(null, truffleSource, null);
            Object result = target.call(PolyglotImpl.EMPTY_ARGS);
            try {
                hostValue = languageContext.asValue(result);
            }
            catch (ClassCastException | NullPointerException e) {
                throw new AssertionError(String.format("Language %s returned an invalid return value %s. Must be an interop value.", languageId, result), e);
            }
            if (truffleSource.isInteractive()) {
                PolyglotContextImpl.printResult(languageContext, result);
            }
            Value value2 = hostValue;
            return value2;
        }
        catch (Throwable e) {
            throw PolyglotImpl.guestToHostException(languageContext, e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(languageContext, prev);
        }
    }

    public PolyglotLanguage requirePublicLanguage(String languageId) {
        PolyglotLanguage language = this.engine.idToLanguage.get(languageId);
        if (language == null || language.cache.isInternal()) {
            this.engine.requirePublicLanguage(languageId);
            assert (false);
            return null;
        }
        return language;
    }

    @CompilerDirectives.TruffleBoundary
    static void printResult(PolyglotLanguageContext languageContext, Object result) {
        String stringResult;
        if (!EngineAccessor.LANGUAGE.isVisible(languageContext.env, result)) {
            return;
        }
        try {
            stringResult = UNCACHED.asString(UNCACHED.toDisplayString(languageContext.getLanguageView(result), true));
        }
        catch (UnsupportedMessageException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
        try {
            OutputStream out = languageContext.context.config.out;
            out.write(stringResult.getBytes(StandardCharsets.UTF_8));
            out.write(System.getProperty("line.separator").getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException ioex) {
            throw new IllegalStateException(ioex);
        }
    }

    private static boolean isCurrentEngineHostCallback(PolyglotEngineImpl engine) {
        RootNode topMostGuestToHostRootNode = Truffle.getRuntime().iterateFrames(f -> {
            RootNode root = ((RootCallTarget)f.getCallTarget()).getRootNode();
            if (EngineAccessor.HOST.isGuestToHostRootNode(root)) {
                return root;
            }
            return null;
        });
        if (topMostGuestToHostRootNode == null) {
            return false;
        }
        PolyglotSharingLayer sharing = (PolyglotSharingLayer)EngineAccessor.NODES.getSharingLayer(topMostGuestToHostRootNode);
        PolyglotEngineImpl rootEngine = sharing.engine;
        return rootEngine == engine;
    }

    public void close(boolean cancelIfExecuting) {
        try {
            this.clearExplicitContextStack();
            if (cancelIfExecuting) {
                this.cancel(false, null);
            } else {
                this.closeAndMaybeWait(false, null);
                this.checkCancelled();
            }
        }
        catch (Throwable t) {
            PolyglotException polyglotException = PolyglotImpl.guestToHostException(this.getHostContext(), t, false);
            if (!cancelIfExecuting && this.state.isInvalidOrClosed() && (polyglotException.isCancelled() || polyglotException.isExit())) {
                try {
                    this.closeAndMaybeWait(false, null);
                }
                catch (Throwable closeFinishError) {
                    PolyglotException closeFinishPolyglotException = PolyglotImpl.guestToHostException(this.getHostContext(), t, false);
                    polyglotException.addSuppressed(closeFinishPolyglotException);
                }
            }
            throw polyglotException;
        }
    }

    void cancel(boolean resourceLimit, String message) {
        String cancelMessage;
        String string = cancelMessage = message == null ? "Context execution was cancelled." : message;
        if (this.parent == null) {
            this.engine.polyglotHostService.notifyContextCancellingOrExiting(this, false, 0, resourceLimit, cancelMessage);
        }
        List<Future<Void>> futures = this.setCancelling(resourceLimit, cancelMessage);
        this.closeHereOrCancelInCleanupThread(futures);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void initiateCancelOrExit(boolean exit, int code, boolean resourceLimit, String message) {
        assert (this.parent == null);
        this.initiateCancelOrExitLock.lock();
        try {
            List<Future<Void>> futures = exit ? this.setExiting(null, code, message, true) : this.setCancelling(resourceLimit, message);
            if (!futures.isEmpty()) {
                this.cancellationOrExitingFutures = futures;
            }
        }
        finally {
            this.initiateCancelOrExitLock.unlock();
        }
    }

    void closeAndMaybeWait(boolean force, List<Future<Void>> futures) {
        if (force) {
            PolyglotEngineImpl.cancelOrExit(this, futures);
        } else {
            boolean closeCompleted = this.closeImpl(true);
            if (!closeCompleted) {
                throw PolyglotEngineException.illegalState(String.format("The context is currently executing on another thread. Set cancelIfExecuting to true to stop the execution on this thread.", new Object[0]));
            }
        }
        this.finishCleanup();
        this.checkSubProcessFinished();
        this.checkSystemThreadsFinished();
        if (this.parent == null) {
            this.engine.polyglotHostService.notifyContextClosed(this, force, this.invalidResourceLimit, this.invalidMessage);
        }
        if (this.engine.boundEngine && this.parent == null) {
            this.engine.ensureClosed(force, false, true);
        }
    }

    private void setState(State targetState) {
        assert (Thread.holdsLock(this));
        assert (this.isTransitionAllowed(this.state, targetState)) : "Transition from " + this.state.name() + " to " + targetState.name() + " not allowed!";
        this.state = targetState;
        this.notifyAll();
    }

    private List<Future<Void>> setInterrupting() {
        assert (Thread.holdsLock(this));
        ArrayList<Future<Void>> futures = new ArrayList<Future<Void>>();
        if (!this.state.isInterrupting() && !this.state.isInvalidOrClosed() && this.state != State.PENDING_EXIT && this.state != State.CLOSING_PENDING_EXIT) {
            State targetState;
            switch (this.state) {
                case CLOSING: {
                    targetState = State.CLOSING_INTERRUPTING;
                    break;
                }
                case CLOSING_FINALIZING: {
                    targetState = State.CLOSING_INTERRUPTING_FINALIZING;
                    break;
                }
                default: {
                    targetState = State.INTERRUPTING;
                }
            }
            this.setState(targetState);
            this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
            futures.add(this.threadLocalActions.submit(null, "engine", (ThreadLocalAction)new InterruptThreadLocalAction(), true));
            this.maybeSendInterrupt();
        }
        return futures;
    }

    private void unsetInterrupting() {
        assert (Thread.holdsLock(this));
        if (this.state.isInterrupting()) {
            State targetState;
            switch (this.state) {
                case CLOSING_INTERRUPTING: {
                    targetState = State.CLOSING;
                    break;
                }
                case CLOSING_INTERRUPTING_FINALIZING: {
                    targetState = State.CLOSING_FINALIZING;
                    break;
                }
                default: {
                    targetState = State.DEFAULT;
                }
            }
            this.setState(targetState);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void finishInterruptForChildContexts() {
        PolyglotContextImpl[] polyglotContextImplArray = this;
        synchronized (this) {
            this.unsetInterrupting();
            PolyglotContextImpl[] childContextsToInterrupt = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
            // ** MonitorExit[var2_1] (shouldn't be in output)
            for (PolyglotContextImpl childCtx : childContextsToInterrupt) {
                childCtx.finishInterruptForChildContexts();
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<Future<Void>> interruptChildContexts() {
        ArrayList<Future<Void>> futures;
        PolyglotContextImpl[] childContextsToInterrupt = null;
        PolyglotContextImpl polyglotContextImpl = this;
        synchronized (polyglotContextImpl) {
            futures = new ArrayList<Future<Void>>(this.setInterrupting());
            if (!futures.isEmpty()) {
                childContextsToInterrupt = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
            }
        }
        if (childContextsToInterrupt != null) {
            for (PolyglotContextImpl childCtx : childContextsToInterrupt) {
                futures.addAll(childCtx.interruptChildContexts());
            }
        }
        return futures;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void validateInterruptPrecondition(PolyglotContextImpl operationSource) {
        PolyglotContextImpl[] polyglotContextImplArray = this;
        synchronized (this) {
            PolyglotThreadInfo info = this.getCurrentThreadInfo();
            if (info != PolyglotThreadInfo.NULL && info.isActive()) {
                throw PolyglotEngineException.illegalState(String.format("Cannot interrupt context from a thread where %s context is active.", this == operationSource ? "the" : "its child"));
            }
            PolyglotContextImpl[] childContextsToInterrupt = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
            // ** MonitorExit[var3_2] (shouldn't be in output)
            for (PolyglotContextImpl childCtx : childContextsToInterrupt) {
                childCtx.validateInterruptPrecondition(operationSource);
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean interrupt(Duration timeout) {
        try {
            if (this.parent != null) {
                throw PolyglotEngineException.illegalState("Cannot interrupt inner context separately.");
            }
            long startMillis = System.currentTimeMillis();
            PolyglotContextImpl[] childContextsToInterrupt = null;
            this.interruptingLock.lock();
            this.validateInterruptPrecondition((PolyglotContextImpl)this);
            PolyglotContextImpl[] polyglotContextImplArray = this;
            synchronized (this) {
                if (this.state.isClosed()) {
                    boolean bl = true;
                    // ** MonitorExit[var6_5] (shouldn't be in output)
                    try {
                        if (childContextsToInterrupt == null) return bl;
                        PolyglotContextImpl[] polyglotContextImplArray2 = this;
                        synchronized (this) {
                            this.unsetInterrupting();
                            PolyglotContextImpl[] childContextsToFinishInterrupt = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
                            // ** MonitorExit[var9_10] (shouldn't be in output)
                            polyglotContextImplArray2 = childContextsToFinishInterrupt;
                            int n = polyglotContextImplArray2.length;
                            int n2 = 0;
                            while (n2 < n) {
                                PolyglotContextImpl childCtx = polyglotContextImplArray2[n2];
                                childCtx.finishInterruptForChildContexts();
                                ++n2;
                            }
                            return bl;
                        }
                    }
                    finally {
                        this.interruptingLock.unlock();
                    }
                }
                ArrayList<Future<Void>> futures = new ArrayList<Future<Void>>(this.setInterrupting());
                if (!futures.isEmpty()) {
                    childContextsToInterrupt = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
                }
                // ** MonitorExit[var6_5] (shouldn't be in output)
                if (childContextsToInterrupt == null) return PolyglotEngineImpl.cancelOrExitOrInterrupt((PolyglotContextImpl)this, futures, startMillis, timeout);
                polyglotContextImplArray = childContextsToInterrupt;
                int n = polyglotContextImplArray.length;
                int n3 = 0;
                while (n3 < n) {
                    PolyglotContextImpl childCtx = polyglotContextImplArray[n3];
                    futures.addAll(childCtx.interruptChildContexts());
                    ++n3;
                }
                return PolyglotEngineImpl.cancelOrExitOrInterrupt((PolyglotContextImpl)this, futures, startMillis, timeout);
            }
        }
        catch (Throwable thr) {
            throw PolyglotImpl.guestToHostException(this.engine, thr);
        }
    }

    public Value asValue(Object hostValue) {
        PolyglotLanguageContext languageContext = this.getHostContext();
        Object prev = PolyglotValueDispatch.hostEnter(languageContext);
        try {
            PolyglotLanguageContext targetLanguageContext;
            this.checkClosed();
            if (hostValue instanceof Value) {
                PolyglotLanguageContext valueContext = (PolyglotLanguageContext)this.getAPIAccess().getContext((Value)hostValue);
                if (valueContext != null && valueContext.context == this) {
                    Value value2 = (Value)hostValue;
                    return value2;
                }
                targetLanguageContext = languageContext;
            } else if (PolyglotWrapper.isInstance(hostValue)) {
                targetLanguageContext = PolyglotWrapper.asInstance(hostValue).getLanguageContext();
                if (this != targetLanguageContext.context) {
                    targetLanguageContext = languageContext;
                }
            } else {
                targetLanguageContext = languageContext;
            }
            Value value3 = targetLanguageContext.asValue(this.toGuestValue(null, hostValue, true));
            return value3;
        }
        catch (Throwable e) {
            throw PolyglotImpl.guestToHostException(this.getHostContext(), e, true);
        }
        finally {
            PolyglotValueDispatch.hostLeave(languageContext, prev);
        }
    }

    static PolyglotEngineImpl getConstantEngine(Node node) {
        if (!CompilerDirectives.inCompiledCode() || !CompilerDirectives.isPartialEvaluationConstant(node)) {
            return null;
        }
        if (node == null) {
            return null;
        }
        RootNode root = node.getRootNode();
        if (root == null) {
            return null;
        }
        PolyglotSharingLayer layer = (PolyglotSharingLayer)EngineAccessor.NODES.getSharingLayer(root);
        return layer != null ? layer.engine : null;
    }

    Object toGuestValue(Node node, Object hostValue, boolean asValue) {
        PolyglotContextImpl localContext;
        PolyglotEngineImpl localEngine = PolyglotContextImpl.getConstantEngine(node);
        if (localEngine == null) {
            localEngine = this.engine;
            localContext = this;
        } else {
            localContext = localEngine.singleContextValue.getConstant();
            if (localContext == null) {
                localContext = this;
            }
        }
        Object value2 = PolyglotHostAccess.toGuestValue(localContext, hostValue);
        return localEngine.host.toGuestValue(localContext.getHostContextImpl(), value2, asValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean waitForThreads(long startMillis, long timeoutMillis) {
        PolyglotContextImpl polyglotContextImpl = this;
        synchronized (polyglotContextImpl) {
            boolean otherThreadActive;
            long timeElapsed = System.currentTimeMillis() - startMillis;
            while ((otherThreadActive = this.hasActiveOtherThread(true)) && (timeoutMillis == 0L || timeElapsed < timeoutMillis)) {
                try {
                    if (timeoutMillis == 0L) {
                        this.wait();
                    } else {
                        this.wait(timeoutMillis - timeElapsed);
                    }
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                timeElapsed = System.currentTimeMillis() - startMillis;
            }
            return !otherThreadActive;
        }
    }

    boolean isSingleThreaded() {
        return this.singleThreaded;
    }

    Map<Thread, PolyglotThreadInfo> getSeenThreads() {
        assert (Thread.holdsLock(this));
        return this.threads;
    }

    private boolean isActiveNotCancelled() {
        return this.isActiveNotCancelled(true);
    }

    synchronized boolean isActiveNotCancelled(boolean includePolyglotThreads) {
        for (PolyglotThreadInfo seenTinfo : this.threads.values()) {
            if (!includePolyglotThreads && seenTinfo.isPolyglotThread(this) || !seenTinfo.isActiveNotCancelled()) continue;
            return true;
        }
        return false;
    }

    synchronized boolean isActive() {
        for (PolyglotThreadInfo seenTinfo : this.threads.values()) {
            if (!seenTinfo.isActive()) continue;
            return true;
        }
        return false;
    }

    synchronized boolean isActive(Thread thread) {
        PolyglotThreadInfo info = this.threads.get(thread);
        if (info == null || info == PolyglotThreadInfo.NULL) {
            return false;
        }
        return info.isActive();
    }

    private PolyglotThreadInfo getFirstActiveOtherThread(boolean includePolyglotThreads) {
        assert (Thread.holdsLock(this));
        for (PolyglotThreadInfo otherInfo : this.threads.values()) {
            if (!includePolyglotThreads && otherInfo.isPolyglotThread(this) || otherInfo.isCurrent() || !otherInfo.isActive()) continue;
            return otherInfo;
        }
        return null;
    }

    boolean hasActiveOtherThread(boolean includePolyglotThreads) {
        return this.getFirstActiveOtherThread(includePolyglotThreads) != null;
    }

    private void notifyThreadClosed(PolyglotThreadInfo info) {
        assert (Thread.holdsLock(this));
        if (!info.cancelled) {
            info.cancelled = true;
            Thread.interrupted();
        }
        this.notifyAll();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    long calculateHeapSize(long stopAtBytes, AtomicBoolean calculationCancelled) {
        ObjectSizeCalculator localObjectSizeCalculator;
        PolyglotContextImpl polyglotContextImpl = this;
        synchronized (polyglotContextImpl) {
            localObjectSizeCalculator = this.objectSizeCalculator;
            if (localObjectSizeCalculator == null) {
                this.objectSizeCalculator = localObjectSizeCalculator = new ObjectSizeCalculator();
            }
        }
        return localObjectSizeCalculator.calculateObjectSize(this.getContextHeapRoots(), stopAtBytes, calculationCancelled);
    }

    private Object[] getContextHeapRoots() {
        ArrayList<Object> heapRoots = new ArrayList<Object>();
        this.addRootPointersForContext(heapRoots);
        this.addRootPointersForStackFrames(heapRoots);
        return heapRoots.toArray();
    }

    private void addRootPointerForGuestToHostStackFrameArgument(Object obj, List<Object> heapRoots) {
        if (InteropLibrary.isValidValue(obj)) {
            heapRoots.add(obj);
        } else if (obj instanceof PolyglotWrapper) {
            heapRoots.add(((PolyglotWrapper)obj).getGuestObject());
        } else if (obj instanceof Value) {
            heapRoots.add(this.getAPIAccess().getReceiver((Value)obj));
        }
    }

    private void addRootPointersForStackFrames(List<Object> heapRoots) {
        FrameInstance[][] frameInstancesPerThread;
        FrameInstance[][] frameInstanceArray = frameInstancesPerThread = PolyglotStackFramesRetriever.getStackFrames(this);
        int n = frameInstanceArray.length;
        for (int i = 0; i < n; ++i) {
            FrameInstance[] threadInstances;
            for (FrameInstance frameInstance : threadInstances = frameInstanceArray[i]) {
                Frame frame = frameInstance.getFrame(FrameInstance.FrameAccess.READ_ONLY);
                RootNode rootNode = ((RootCallTarget)frameInstance.getCallTarget()).getRootNode();
                if (rootNode instanceof HostToGuestRootNode) continue;
                if (EngineAccessor.HOST.isGuestToHostRootNode(rootNode)) {
                    for (Object obj : frame.getArguments()) {
                        if (obj instanceof Object[]) {
                            for (Object elem : (Object[])obj) {
                                this.addRootPointerForGuestToHostStackFrameArgument(elem, heapRoots);
                            }
                            continue;
                        }
                        this.addRootPointerForGuestToHostStackFrameArgument(obj, heapRoots);
                    }
                    continue;
                }
                heapRoots.add(frame);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void addRootPointersForContext(List<Object> heapRoots) {
        PolyglotContextImpl[] childContextStartPoints;
        PolyglotContextImpl polyglotContextImpl = this;
        synchronized (polyglotContextImpl) {
            for (PolyglotLanguageContext context : this.contexts) {
                if (!context.isCreated()) continue;
                heapRoots.add(context.getContextImpl());
            }
            if (this.polyglotBindings != null) {
                for (Map.Entry entry : this.polyglotBindings.entrySet()) {
                    heapRoots.add(entry.getKey());
                    if (entry.getValue() == null) continue;
                    heapRoots.add(this.getAPIAccess().getReceiver((Value)entry.getValue()));
                }
            }
        }
        heapRoots.add(this.contextLocals);
        PolyglotContextImpl polyglotContextImpl2 = this;
        synchronized (polyglotContextImpl2) {
            for (PolyglotThreadInfo info : this.threads.values()) {
                heapRoots.add(info.getContextThreadLocals());
            }
            childContextStartPoints = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
        }
        for (PolyglotContextImpl childCtx : childContextStartPoints) {
            childCtx.addRootPointersForContext(heapRoots);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<Future<Void>> setCancelling(boolean resourceLimit, String message) {
        assert (message != null);
        PolyglotContextImpl[] childContextsToCancel = null;
        ArrayList<Future<Void>> futures = new ArrayList<Future<Void>>();
        PolyglotContextImpl polyglotContextImpl = this;
        synchronized (polyglotContextImpl) {
            if (!this.state.isInvalidOrClosed()) {
                State targetState = this.state.isClosing() ? State.CLOSING_CANCELLING : State.CANCELLING;
                this.invalidResourceLimit = resourceLimit;
                this.invalidMessage = message;
                this.exitMessage = null;
                this.setState(targetState);
                this.submitCancellationThreadLocalAction(futures);
                this.maybeSendInterrupt();
                childContextsToCancel = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
            }
        }
        if (childContextsToCancel != null) {
            assert (!futures.isEmpty());
            for (PolyglotContextImpl childCtx : childContextsToCancel) {
                futures.addAll(childCtx.setCancelling(resourceLimit, message));
            }
        }
        return this.getCancellingOrExitingFutures(futures);
    }

    private void submitCancellationThreadLocalAction(List<Future<Void>> futures) {
        PolyglotThreadInfo info = this.getCurrentThreadInfo();
        futures.add(this.threadLocalActions.submit(null, "engine", (ThreadLocalAction)new CancellationThreadLocalAction(), true));
        if (info != PolyglotThreadInfo.NULL) {
            info.cancelled = true;
            Thread.interrupted();
        }
        this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<Future<Void>> setExiting(PolyglotContextImpl triggeringParent, int code, String message, boolean skipPendingExit) {
        PolyglotContextImpl[] childContextsToCancel = null;
        ArrayList<Future<Void>> futures = new ArrayList<Future<Void>>();
        PolyglotContextImpl polyglotContextImpl = this;
        synchronized (polyglotContextImpl) {
            if (!this.state.isInvalidOrClosed()) {
                assert (message != null);
                State targetState = this.state.isClosing() ? State.CLOSING_EXITING : State.EXITING;
                this.skipPendingExit = skipPendingExit;
                this.invalidMessage = message;
                if (skipPendingExit) {
                    this.exitMessage = message;
                    this.exitCode = code;
                }
                if (triggeringParent != null) {
                    this.exitMessage = triggeringParent.exitMessage;
                    this.exitCode = triggeringParent.exitCode;
                }
                this.setState(targetState);
                if (!this.config.useSystemExit) {
                    this.submitCancellationThreadLocalAction(futures);
                    this.maybeSendInterrupt();
                }
                childContextsToCancel = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
            }
        }
        if (childContextsToCancel != null) {
            for (PolyglotContextImpl childCtx : childContextsToCancel) {
                futures.addAll(childCtx.setExiting(this, code, message, skipPendingExit));
            }
        }
        return this.getCancellingOrExitingFutures(futures);
    }

    private List<Future<Void>> getCancellingOrExitingFutures(List<Future<Void>> futures) {
        List<Future<Void>> toRet = futures;
        if (this.parent == null && toRet.isEmpty()) {
            this.initiateCancelOrExitLock.lock();
            try {
                if (this.cancellationOrExitingFutures != null) {
                    toRet = this.cancellationOrExitingFutures;
                    this.cancellationOrExitingFutures = null;
                }
            }
            finally {
                this.initiateCancelOrExitLock.unlock();
            }
        }
        return toRet;
    }

    private void setClosingState() {
        State targetState;
        assert (Thread.holdsLock(this));
        this.closingThread = Thread.currentThread();
        this.closingLock.lock();
        switch (this.state) {
            case CANCELLING: {
                targetState = State.CLOSING_CANCELLING;
                break;
            }
            case INTERRUPTING: {
                targetState = State.CLOSING_INTERRUPTING;
                break;
            }
            case EXITING: {
                targetState = State.CLOSING_EXITING;
                break;
            }
            default: {
                targetState = State.CLOSING;
            }
        }
        this.setState(targetState);
    }

    private void setFinalizingState() {
        State targetState;
        assert (Thread.holdsLock(this));
        assert (this.closingThread == Thread.currentThread());
        assert (this.closingLock.isHeldByCurrentThread());
        switch (this.state) {
            case CLOSING: {
                targetState = State.CLOSING_FINALIZING;
                break;
            }
            case CLOSING_INTERRUPTING: {
                targetState = State.CLOSING_INTERRUPTING_FINALIZING;
                break;
            }
            default: {
                return;
            }
        }
        this.setState(targetState);
    }

    private void setClosedState() {
        State targetState;
        assert (Thread.holdsLock(this));
        assert (this.state.isClosing()) : this.state.name();
        switch (this.state) {
            case CLOSING_CANCELLING: {
                targetState = State.CLOSED_CANCELLED;
                break;
            }
            case CLOSING_EXITING: {
                targetState = State.CLOSED_EXITED;
                break;
            }
            case CLOSING_INTERRUPTING_FINALIZING: {
                targetState = State.CLOSED_INTERRUPTED;
                break;
            }
            case CLOSING_FINALIZING: {
                targetState = State.CLOSED;
                break;
            }
            default: {
                throw new IllegalStateException("Cannot close polyglot context in the current state!");
            }
        }
        this.setState(targetState);
        assert (this.state.isClosed()) : this.state.name();
    }

    private void restoreFromClosingState(boolean cancelOperation) {
        State targetState;
        assert (Thread.holdsLock(this));
        assert (this.state.isClosing()) : this.state.name();
        assert (!cancelOperation) : "Close initiated for an invalid context must not fail!";
        switch (this.state) {
            case CLOSING_INTERRUPTING: 
            case CLOSING_INTERRUPTING_FINALIZING: {
                targetState = State.INTERRUPTING;
                break;
            }
            case CLOSING_CANCELLING: {
                targetState = State.CANCELLING;
                break;
            }
            case CLOSING_PENDING_EXIT: {
                targetState = State.PENDING_EXIT;
                break;
            }
            case CLOSING_EXITING: {
                targetState = State.EXITING;
                break;
            }
            default: {
                targetState = State.DEFAULT;
            }
        }
        this.setState(targetState);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SuppressFBWarnings(value={"UL_UNRELEASED_LOCK_EXCEPTION_PATH"})
    boolean closeImpl(boolean notifyInstruments) {
        boolean cancelOrExitOperation;
        boolean waitForClose = false;
        boolean finishCancelOrExit = false;
        block10: while (true) {
            if (waitForClose) {
                this.closingLock.lock();
                this.closingLock.unlock();
                waitForClose = false;
            }
            PolyglotContextImpl polyglotContextImpl = this;
            synchronized (polyglotContextImpl) {
                switch (this.state) {
                    case CLOSED: 
                    case CLOSED_INTERRUPTED: 
                    case CLOSED_CANCELLED: 
                    case CLOSED_EXITED: {
                        return true;
                    }
                    case CLOSING_CANCELLING: 
                    case CLOSING_EXITING: 
                    case CLOSING_INTERRUPTING: 
                    case CLOSING_INTERRUPTING_FINALIZING: 
                    case CLOSING: 
                    case CLOSING_FINALIZING: 
                    case CLOSING_PENDING_EXIT: {
                        assert (this.closingThread != null);
                        if (this.closingThread == Thread.currentThread()) {
                            return true;
                        }
                        waitForClose = true;
                        continue block10;
                    }
                    case PENDING_EXIT: {
                        this.waitUntilInvalid();
                        continue block10;
                    }
                    case CANCELLING: 
                    case EXITING: {
                        assert (this.cachedThreadInfo == PolyglotThreadInfo.NULL);
                        if (!finishCancelOrExit) {
                            this.waitForThreads(0L, 0L);
                            waitForClose = true;
                            finishCancelOrExit = true;
                            continue block10;
                        }
                        this.setClosingState();
                        cancelOrExitOperation = true;
                        break block10;
                    }
                    case INTERRUPTING: 
                    case DEFAULT: {
                        if (this.hasActiveOtherThread(false)) {
                            return false;
                        }
                        this.setClosingState();
                        cancelOrExitOperation = false;
                        break block10;
                    }
                    default: {
                        assert (false) : this.state.name();
                        break;
                    }
                }
            }
        }
        return this.finishClose(cancelOrExitOperation, notifyInstruments);
    }

    private void waitUntilInvalid() {
        while (!this.state.isInvalidOrClosed()) {
            try {
                this.wait();
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    synchronized void clearExplicitContextStack() {
        if (this.parent == null) {
            this.engine.polyglotHostService.notifyClearExplicitContextStack(this);
        }
        if (this.isActive(Thread.currentThread()) && !PolyglotContextImpl.isCurrentEngineHostCallback(this.engine)) {
            PolyglotThreadInfo threadInfo = this.getCurrentThreadInfo();
            if (!threadInfo.explicitContextStack.isEmpty()) {
                PolyglotContextImpl c = this;
                while (!threadInfo.explicitContextStack.isEmpty()) {
                    if (PolyglotFastThreadLocals.getContext(null) == this) {
                        Object[] prev = threadInfo.explicitContextStack.removeLast();
                        this.engine.leave(prev, c);
                        c = prev != null ? (PolyglotContextImpl)prev[1] : null;
                        continue;
                    }
                    throw PolyglotEngineException.illegalState("Unable to automatically leave an explicitly entered context, some other context was entered in the meantime.");
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean finishClose(boolean cancelOrExitOperation, boolean notifyInstruments) {
        Thread[] remainingThreads = null;
        List<PolyglotLanguageContext> disposedContexts = null;
        boolean success = false;
        try {
            Object unclosedChildContexts;
            PolyglotContextImpl t22;
            Object[] object;
            assert (this.closingThread == Thread.currentThread());
            assert (this.closingLock.isHeldByCurrentThread()) : "lock is acquired";
            assert (!this.state.isClosed());
            try {
                object = this.enterThreadChanged(false, false, !cancelOrExitOperation, cancelOrExitOperation, false);
            }
            catch (Throwable t22) {
                PolyglotContextImpl polyglotContextImpl = this;
                synchronized (polyglotContextImpl) {
                    this.restoreFromClosingState(cancelOrExitOperation);
                }
                throw t22;
            }
            if (cancelOrExitOperation) {
                t22 = this;
                synchronized (t22) {
                    this.threadLocalActions.submit(new Thread[]{Thread.currentThread()}, "engine", (ThreadLocalAction)new CancellationThreadLocalAction(), true);
                }
            }
            try {
                if (cancelOrExitOperation) {
                    this.closeChildContexts(notifyInstruments);
                } else {
                    this.exitContextNotification(TruffleLanguage.ExitMode.NATURAL, 0);
                }
                t22 = this;
                synchronized (t22) {
                    assert (this.state != State.CLOSING_FINALIZING && this.state != State.CLOSING_INTERRUPTING_FINALIZING);
                    this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
                    this.setFinalizingState();
                    if (this.state == State.CLOSING_PENDING_EXIT) {
                        this.waitUntilInvalid();
                    }
                }
                this.finalizeContext(notifyInstruments, cancelOrExitOperation);
                Object object2 = this;
                synchronized (object2) {
                    unclosedChildContexts = this.getUnclosedChildContexts();
                }
                object2 = unclosedChildContexts.iterator();
                while (object2.hasNext()) {
                    PolyglotContextImpl childCtx = (PolyglotContextImpl)object2.next();
                    if (!childCtx.isActive()) continue;
                    throw new IllegalStateException("There is an active child contexts after finalizeContext!");
                }
                if (!unclosedChildContexts.isEmpty()) {
                    this.closeChildContexts(notifyInstruments);
                }
                disposedContexts = this.disposeContext();
                success = true;
            }
            finally {
                unclosedChildContexts = this;
                synchronized (unclosedChildContexts) {
                    assert (!success || this.getUnclosedChildContexts().isEmpty()) : "Polyglot context close marked as successful, but there are unclosed child contexts.";
                    this.leaveThreadChanged(object, false, true, false);
                    if (success) {
                        remainingThreads = this.threads.keySet().toArray(new Thread[0]);
                    }
                    if (success) {
                        this.setClosedState();
                    } else {
                        this.restoreFromClosingState(cancelOrExitOperation);
                    }
                    this.disposing = false;
                    this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
                }
            }
        }
        finally {
            PolyglotContextImpl polyglotContextImpl = this;
            synchronized (polyglotContextImpl) {
                assert (!this.state.isClosing());
                this.closingThread = null;
                this.closingLock.unlock();
            }
        }
        for (PolyglotLanguageContext context : disposedContexts) {
            context.notifyDisposed(notifyInstruments);
        }
        if (success) {
            try {
                if (notifyInstruments) {
                    for (Thread thread : remainingThreads) {
                        EngineAccessor.INSTRUMENT.notifyThreadFinished(this.engine, this.creatorTruffleContext, thread);
                    }
                    EngineAccessor.INSTRUMENT.notifyContextClosed(this.engine, this.creatorTruffleContext);
                }
            }
            finally {
                if (this.parent != null) {
                    PolyglotContextImpl polyglotContextImpl = this.parent;
                    synchronized (polyglotContextImpl) {
                        this.parent.childContexts.remove(this);
                    }
                } else if (notifyInstruments) {
                    this.engine.disposeContext(this);
                }
            }
            PolyglotContextImpl polyglotContextImpl = this;
            synchronized (polyglotContextImpl) {
                this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
                if (!this.isActive()) {
                    this.threadLocalActions.notifyContextClosed();
                    if (this.contexts != null) {
                        for (PolyglotLanguageContext langContext : this.contexts) {
                            langContext.close();
                        }
                    }
                    if (this.contextLocals != null) {
                        Arrays.fill(this.contextLocals, null);
                    }
                    for (PolyglotThreadInfo thread : this.threads.values()) {
                        Object[] threadLocals = thread.getContextThreadLocals();
                        if (threadLocals != null) {
                            Arrays.fill(threadLocals, null);
                        }
                        PolyglotFastThreadLocals.cleanup(thread.fastThreadLocals);
                    }
                    this.localsCleared = true;
                }
            }
            if (this.parent == null) {
                if (!this.config.logLevels.isEmpty()) {
                    EngineAccessor.LANGUAGE.configureLoggers(this, null, this.getAllLoggers());
                }
                if (this.config.logHandler != null && !PolyglotLoggers.isSameLogSink(this.config.logHandler, this.engine.logHandler)) {
                    this.config.logHandler.close();
                }
            }
        }
        return true;
    }

    private List<PolyglotContextImpl> getUnclosedChildContexts() {
        assert (Thread.holdsLock(this));
        ArrayList<PolyglotContextImpl> unclosedChildContexts = new ArrayList<PolyglotContextImpl>();
        for (PolyglotContextImpl childCtx : this.childContexts) {
            if (childCtx.state.isClosed()) continue;
            unclosedChildContexts.add(childCtx);
        }
        return unclosedChildContexts;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void closeChildContexts(boolean notifyInstruments) {
        PolyglotContextImpl[] polyglotContextImplArray = this;
        synchronized (this) {
            PolyglotContextImpl[] childrenToClose = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
            // ** MonitorExit[var3_2] (shouldn't be in output)
            for (PolyglotContextImpl childContext : childrenToClose) {
                childContext.closeImpl(notifyInstruments);
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean setPendingExit(int code) {
        PolyglotContextImpl polyglotContextImpl = this;
        synchronized (polyglotContextImpl) {
            State targetState;
            switch (this.state) {
                case INTERRUPTING: 
                case DEFAULT: {
                    targetState = State.PENDING_EXIT;
                    break;
                }
                case CLOSING_INTERRUPTING: 
                case CLOSING: {
                    targetState = State.CLOSING_PENDING_EXIT;
                    break;
                }
                default: {
                    return false;
                }
            }
            this.exitCode = code;
            this.exitMessage = "Exit was called with exit code " + code + ".";
            this.closeExitedTriggerThread = Thread.currentThread();
            this.setState(targetState);
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void closeExited(Node exitLocation, int code) {
        Node location;
        Object futures;
        if (this.setPendingExit(code)) {
            this.exitContextNotification(TruffleLanguage.ExitMode.HARD, code);
            if (this.parent == null) {
                this.engine.polyglotHostService.notifyContextCancellingOrExiting(this, true, code, false, this.exitMessage);
            }
            if (!(futures = this.setExiting(null, code, this.exitMessage, false)).isEmpty()) {
                this.closeHereOrCancelInCleanupThread((List<Future<Void>>)futures);
            }
        } else {
            futures = this;
            synchronized (futures) {
                PolyglotThreadInfo info;
                if (!this.state.isInvalidOrClosed() && (this.closeExitedTriggerThread == (info = this.getCurrentThreadInfo()).getThread() || info.isPolyglotThread(this) && ((PolyglotThread)info.getThread()).hardExitNotificationThread)) {
                    throw this.createExitException(exitLocation);
                }
            }
        }
        State localState = this.state;
        Node node = location = exitLocation != null ? exitLocation : this.uncachedLocation;
        if (localState == State.PENDING_EXIT || localState == State.CLOSING_PENDING_EXIT || localState.isInvalidOrClosed()) {
            TruffleSafepoint.setBlockedThreadInterruptible(location, new TruffleSafepoint.Interruptible<PolyglotContextImpl>(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public void apply(PolyglotContextImpl ctx) throws InterruptedException {
                    PolyglotContextImpl polyglotContextImpl = ctx;
                    synchronized (polyglotContextImpl) {
                        while (!ctx.state.isInvalidOrClosed()) {
                            ctx.wait();
                        }
                    }
                }
            }, this);
            localState = this.state;
            if (this.config.useSystemExit && (localState.isExiting() || localState == State.CLOSED_EXITED)) {
                this.engine.host.hostExit(this.exitCode);
            }
            TruffleSafepoint.pollHere(location);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void closeHereOrCancelInCleanupThread(final List<Future<Void>> futures) {
        boolean cancelInSeparateThread = false;
        PolyglotContextImpl polyglotContextImpl = this;
        synchronized (polyglotContextImpl) {
            PolyglotThreadInfo info = this.getCurrentThreadInfo();
            Thread currentThread = Thread.currentThread();
            if (info.isPolyglotThread(this) || !this.singleThreaded && this.isActive(currentThread) || this.closingThread == currentThread || currentThread instanceof SystemThread) {
                cancelInSeparateThread = true;
            }
        }
        if (cancelInSeparateThread) {
            if (!futures.isEmpty()) {
                this.registerCleanupTask(new Runnable(){

                    @Override
                    public void run() {
                        PolyglotEngineImpl.cancelOrExit(PolyglotContextImpl.this, futures);
                    }
                });
            }
        } else {
            this.closeAndMaybeWait(true, futures);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void registerCleanupTask(Runnable cleanupTask) {
        PolyglotContextImpl polyglotContextImpl = this;
        synchronized (polyglotContextImpl) {
            if (!this.state.isClosed()) {
                if (this.cleanupExecutorService == null) {
                    this.cleanupExecutorService = Executors.newFixedThreadPool(1, new ThreadFactory(){

                        @Override
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(r);
                            t.setDaemon(true);
                            return t;
                        }
                    });
                }
                assert (this.cleanupFuture == null) : "Multiple cleanup tasks are currently not supported!";
                this.cleanupFuture = this.cleanupExecutorService.submit(cleanupTask);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void finishCleanup() {
        ExecutorService localCleanupService;
        PolyglotContextImpl polyglotContextImpl = this;
        synchronized (polyglotContextImpl) {
            if (this.isActive(Thread.currentThread())) {
                return;
            }
            localCleanupService = this.cleanupExecutorService;
        }
        if (localCleanupService != null) {
            try {
                try {
                    this.cleanupFuture.get();
                }
                catch (InterruptedException ie) {
                    this.engine.getEngineLogger().log(Level.INFO, "Waiting for polyglot context cleanup was interrupted!", ie);
                }
                catch (ExecutionException ee) {
                    assert (!(ee.getCause() instanceof AbstractTruffleException));
                    throw PolyglotContextImpl.sneakyThrow(ee.getCause());
                }
            }
            finally {
                localCleanupService.shutdownNow();
                while (!localCleanupService.isTerminated()) {
                    try {
                        if (localCleanupService.awaitTermination(1L, TimeUnit.MINUTES)) continue;
                        throw new IllegalStateException("Context cleanup service timeout!");
                    }
                    catch (InterruptedException ie) {
                        this.engine.getEngineLogger().log(Level.INFO, "Waiting for polyglot context cleanup was interrupted!", ie);
                    }
                }
            }
        }
    }

    private static <T extends Throwable> RuntimeException sneakyThrow(Throwable ex) throws T {
        throw ex;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<PolyglotLanguageContext> disposeContext() {
        assert (!this.disposing);
        this.disposing = true;
        ArrayList<PolyglotLanguageContext> disposedContexts = new ArrayList<PolyglotLanguageContext>(this.contexts.length);
        for (int i = this.contexts.length - 1; i >= 0; --i) {
            PolyglotLanguageContext context = this.contexts[i];
            boolean disposed = context.dispose();
            if (!disposed) continue;
            disposedContexts.add(context);
        }
        Closeable[] closeableArray = this;
        synchronized (this) {
            Closeable[] toClose = this.closeables == null ? null : this.closeables.toArray(new Closeable[0]);
            // ** MonitorExit[var3_4] (shouldn't be in output)
            if (toClose != null) {
                for (Closeable closeable : toClose) {
                    try {
                        closeable.close();
                    }
                    catch (IOException ioe) {
                        this.engine.getEngineLogger().log(Level.WARNING, "Failed to close " + closeable, ioe);
                    }
                }
            }
            return disposedContexts;
        }
    }

    private void exitContextNotification(TruffleLanguage.ExitMode exitMode, int code) {
        try {
            boolean exitNotificationPerformed;
            do {
                exitNotificationPerformed = false;
                for (int i = this.contexts.length - 1; i >= 0; --i) {
                    PolyglotLanguageContext context = this.contexts[i];
                    if (!context.isInitialized()) continue;
                    exitNotificationPerformed |= context.exitContext(exitMode, code);
                }
            } while (exitNotificationPerformed);
        }
        catch (Throwable t) {
            if (exitMode == TruffleLanguage.ExitMode.NATURAL || !(t instanceof PolyglotEngineImpl.CancelExecution)) {
                throw t;
            }
            this.engine.getEngineLogger().log(Level.FINE, "Execution was cancelled during exit notifications!", t);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void finalizeContext(boolean notifyInstruments, boolean cancelOrExitOperation) {
        TruffleSafepoint safepoint = TruffleSafepoint.getCurrent();
        PolyglotThreadLocalActions.TL_HANDSHAKE.setChangeAllowActions(safepoint, true);
        try {
            boolean finalizationPerformed;
            do {
                finalizationPerformed = false;
                for (int i = this.contexts.length - 1; i >= 0; --i) {
                    PolyglotLanguageContext context = this.contexts[i];
                    if (!context.isInitialized()) continue;
                    try {
                        finalizationPerformed |= context.finalizeContext(cancelOrExitOperation, notifyInstruments);
                        continue;
                    }
                    finally {
                        if (!PolyglotThreadLocalActions.TL_HANDSHAKE.isAllowActions(safepoint)) {
                            safepoint.setAllowActions(true);
                            throw new IllegalStateException("TruffleSafepoint.setAllowActions is still disabled even though finalization completed. Make sure allow actions are reset in a finally block.");
                        }
                    }
                }
            } while (finalizationPerformed);
        }
        finally {
            PolyglotThreadLocalActions.TL_HANDSHAKE.setChangeAllowActions(safepoint, false);
        }
    }

    synchronized void maybeSendInterrupt() {
        if (!(this.state.isInterrupting() || this.state.isCancelling() || this.state.isExiting())) {
            return;
        }
        for (PolyglotThreadInfo threadInfo : this.threads.values()) {
            if (threadInfo.isCurrent() || !threadInfo.isActiveNotCancelled()) continue;
            threadInfo.getThread().interrupt();
        }
    }

    Object getLocal(PolyglotLocals.LocalLocation l) {
        assert (l.engine == this.engine) : PolyglotContextImpl.invalidSharingError(this.engine, l.engine);
        return l.readLocal(this, this.contextLocals, false);
    }

    private Object[] getThreadLocals(Thread thread) {
        assert (Thread.holdsLock(this));
        PolyglotThreadInfo threadInfo = this.threads.get(thread);
        if (threadInfo == null) {
            return null;
        }
        return threadInfo.getContextThreadLocals();
    }

    @CompilerDirectives.TruffleBoundary
    synchronized Object getThreadLocal(PolyglotLocals.LocalLocation l, Thread t) {
        assert (l.engine == this.engine) : PolyglotContextImpl.invalidSharingError(this.engine, l.engine);
        Object[] threadLocals = this.getThreadLocals(t);
        if (threadLocals == null) {
            return null;
        }
        return l.readLocal(this, threadLocals, true);
    }

    void initializeThreadLocals(PolyglotThreadInfo threadInfo) {
        assert (Thread.holdsLock(this));
        assert (Thread.currentThread() == threadInfo.getThread()) : "thread locals must only be initialized on the current thread";
        PolyglotEngineImpl.StableLocalLocations locations = this.engine.contextThreadLocalLocations;
        Object[] locals = new Object[locations.locations.length];
        Thread thread = threadInfo.getThread();
        for (PolyglotInstrument instrument : this.engine.idToInstrument.values()) {
            if (!instrument.isCreated()) continue;
            this.invokeContextLocalsFactory(this.contextLocals, instrument.contextLocalLocations);
            this.invokeContextThreadFactory(locals, instrument.contextThreadLocalLocations, thread);
        }
        for (PolyglotLanguageContext language : this.contexts) {
            if (!language.isCreated()) continue;
            this.invokeContextLocalsFactory(this.contextLocals, language.getLanguageInstance().contextLocalLocations);
            this.invokeContextThreadFactory(locals, language.getLanguageInstance().contextThreadLocalLocations, thread);
        }
        threadInfo.setContextThreadLocals(locals);
    }

    void initializeContextLocals() {
        assert (Thread.holdsLock(this));
        if (this.contextLocals != null) {
            return;
        }
        PolyglotEngineImpl.StableLocalLocations locations = this.engine.contextLocalLocations;
        Object[] locals = new Object[locations.locations.length];
        this.initializeInstrumentContextLocals(locals);
        assert (this.contextLocals == null);
        this.contextLocals = locals;
    }

    void initializeInstrumentContextLocals(Object[] locals) {
        for (PolyglotInstrument instrument : this.engine.idToInstrument.values()) {
            if (!instrument.isCreated()) continue;
            this.invokeContextLocalsFactory(locals, instrument.contextLocalLocations);
        }
    }

    void initializeInstrumentContextThreadLocals() {
        for (PolyglotInstrument instrument : this.engine.idToInstrument.values()) {
            if (!instrument.isCreated()) continue;
            this.invokeContextThreadLocalFactory(instrument.contextThreadLocalLocations);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void invokeLocalsFactories(PolyglotLocals.LocalLocation[] contextLocalLocations, PolyglotLocals.LocalLocation[] contextThreadLocalLocations) {
        PolyglotContextImpl[] polyglotContextImplArray = this;
        synchronized (this) {
            if (this.localsCleared) {
                // ** MonitorExit[var4_3] (shouldn't be in output)
                return;
            }
            if (this.contextLocals != null) {
                this.invokeContextLocalsFactory(this.contextLocals, contextLocalLocations);
                this.invokeContextThreadLocalFactory(contextThreadLocalLocations);
            }
            PolyglotContextImpl[] localChildContexts = this.childContexts.toArray(new PolyglotContextImpl[0]);
            // ** MonitorExit[var4_3] (shouldn't be in output)
            for (PolyglotContextImpl childCtx : localChildContexts) {
                childCtx.invokeLocalsFactories(contextLocalLocations, contextThreadLocalLocations);
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void resizeThreadLocals(PolyglotEngineImpl.StableLocalLocations locations) {
        PolyglotContextImpl[] polyglotContextImplArray = this;
        synchronized (this) {
            if (this.localsCleared) {
                // ** MonitorExit[var3_2] (shouldn't be in output)
                return;
            }
            this.resizeContextThreadLocals(locations);
            PolyglotContextImpl[] localChildContexts = this.childContexts.toArray(new PolyglotContextImpl[0]);
            // ** MonitorExit[var3_2] (shouldn't be in output)
            for (PolyglotContextImpl childCtx : localChildContexts) {
                childCtx.resizeThreadLocals(locations);
            }
            return;
        }
    }

    void resizeContextThreadLocals(PolyglotEngineImpl.StableLocalLocations locations) {
        assert (Thread.holdsLock(this));
        for (PolyglotThreadInfo threadInfo : this.threads.values()) {
            Object[] threadLocals = threadInfo.getContextThreadLocals();
            if (threadLocals.length >= locations.locations.length) continue;
            threadInfo.setContextThreadLocals(Arrays.copyOf(threadLocals, locations.locations.length));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void resizeLocals(PolyglotEngineImpl.StableLocalLocations locations) {
        PolyglotContextImpl[] polyglotContextImplArray = this;
        synchronized (this) {
            if (this.localsCleared) {
                // ** MonitorExit[var3_2] (shouldn't be in output)
                return;
            }
            this.resizeContextLocals(locations);
            PolyglotContextImpl[] localChildContexts = this.childContexts.toArray(new PolyglotContextImpl[0]);
            // ** MonitorExit[var3_2] (shouldn't be in output)
            for (PolyglotContextImpl childCtx : localChildContexts) {
                childCtx.resizeLocals(locations);
            }
            return;
        }
    }

    void resizeContextLocals(PolyglotEngineImpl.StableLocalLocations locations) {
        assert (Thread.holdsLock(this));
        Object[] oldLocals = this.contextLocals;
        if (oldLocals != null) {
            if (oldLocals.length > locations.locations.length) {
                throw new AssertionError((Object)"Context locals array must never shrink.");
            }
            if (locations.locations.length > oldLocals.length) {
                this.contextLocals = Arrays.copyOf(oldLocals, locations.locations.length);
            }
        } else {
            this.contextLocals = new Object[locations.locations.length];
        }
    }

    void invokeContextLocalsFactory(Object[] locals, PolyglotLocals.LocalLocation[] locations) {
        assert (Thread.holdsLock(this));
        if (locations == null) {
            return;
        }
        try {
            for (int i = 0; i < locations.length; ++i) {
                PolyglotLocals.LocalLocation location = locations[i];
                if (locals[location.index] != null) continue;
                locals[location.index] = location.invokeFactory(this, null);
            }
        }
        catch (Throwable t) {
            for (int i = 0; i < locations.length; ++i) {
                locals[locations[i].index] = null;
            }
            throw t;
        }
    }

    void invokeContextThreadLocalFactory(PolyglotLocals.LocalLocation[] locations) {
        assert (Thread.holdsLock(this));
        if (locations == null) {
            return;
        }
        for (PolyglotThreadInfo threadInfo : this.threads.values()) {
            this.invokeContextThreadFactory(threadInfo.getContextThreadLocals(), locations, threadInfo.getThread());
        }
    }

    private void invokeContextThreadFactory(Object[] threadLocals, PolyglotLocals.LocalLocation[] locations, Thread thread) {
        assert (Thread.holdsLock(this));
        if (locations == null) {
            return;
        }
        try {
            for (int i = 0; i < locations.length; ++i) {
                PolyglotLocals.LocalLocation location = locations[i];
                if (threadLocals[location.index] != null) continue;
                threadLocals[location.index] = location.invokeFactory(this, thread);
            }
        }
        catch (Throwable t) {
            for (int i = 0; i < locations.length; ++i) {
                threadLocals[locations[i].index] = null;
            }
            throw t;
        }
    }

    static String invalidSharingError(PolyglotEngineImpl expectedEngine, PolyglotEngineImpl actualEngine) {
        return String.format("Detected invaliding sharing of context locals between polyglot engines. Expected engine %s but was %s.", expectedEngine, actualEngine);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean patch(PolyglotContextConfig newConfig) {
        CompilerAsserts.neverPartOfCompilation();
        this.config = newConfig;
        this.threadLocalActions.onContextPatch();
        if (!newConfig.logLevels.isEmpty()) {
            EngineAccessor.LANGUAGE.configureLoggers(this, newConfig.logLevels, this.getAllLoggers());
        }
        Object[] prev = this.engine.enter(this);
        try {
            for (int i = 0; i < this.contexts.length; ++i) {
                PolyglotLanguageContext context = this.contexts[i];
                if (context.language.isHost()) {
                    this.initializeHostContext(context, newConfig);
                }
                if (context.patch(newConfig)) continue;
                boolean bl = false;
                return bl;
            }
        }
        finally {
            this.engine.leave(prev, this);
        }
        return true;
    }

    void initializeHostContext(PolyglotLanguageContext context, PolyglotContextConfig newConfig) {
        Object contextImpl = context.getContextImpl();
        if (contextImpl == null) {
            throw new AssertionError((Object)"Host context not initialized.");
        }
        this.hostContextImpl = contextImpl;
        AbstractPolyglotImpl.AbstractHostLanguageService currentHost = this.engine.host;
        AbstractPolyglotImpl.AbstractHostLanguageService newHost = context.lookupService(AbstractPolyglotImpl.AbstractHostLanguageService.class);
        if (newHost == null) {
            throw new AssertionError((Object)("The engine host language must register a service of type:" + AbstractPolyglotImpl.AbstractHostLanguageService.class));
        }
        if (currentHost == null) {
            this.engine.host = newHost;
        } else if (currentHost != newHost) {
            throw new AssertionError((Object)"Host service must not change per engine.");
        }
        newHost.initializeHostContext(this, contextImpl, newConfig.hostAccess, newConfig.hostClassLoader, newConfig.classFilter, newConfig.hostClassLoadingAllowed, newConfig.hostLookupAllowed);
    }

    void replayInstrumentationEvents() {
        this.notifyContextCreated();
        EngineAccessor.INSTRUMENT.notifyThreadStarted(this.engine, this.creatorTruffleContext, Thread.currentThread());
        for (PolyglotLanguageContext lc : this.contexts) {
            LanguageInfo language = lc.language.info;
            if (!lc.eventsEnabled || lc.env == null) continue;
            EngineAccessor.INSTRUMENT.notifyLanguageContextCreate(this, this.creatorTruffleContext, language);
            EngineAccessor.INSTRUMENT.notifyLanguageContextCreated(this, this.creatorTruffleContext, language);
            if (!lc.isInitialized()) continue;
            EngineAccessor.INSTRUMENT.notifyLanguageContextInitialize(this, this.creatorTruffleContext, language);
            EngineAccessor.INSTRUMENT.notifyLanguageContextInitialized(this, this.creatorTruffleContext, language);
            if (!lc.finalized) continue;
            EngineAccessor.INSTRUMENT.notifyLanguageContextFinalized(this, this.creatorTruffleContext, language);
        }
    }

    private synchronized void checkSubProcessFinished() {
        ProcessHandlers.ProcessDecorator[] processes;
        for (ProcessHandlers.ProcessDecorator process : processes = this.subProcesses.toArray(new ProcessHandlers.ProcessDecorator[this.subProcesses.size()])) {
            if (!process.isAlive()) continue;
            throw new IllegalStateException(String.format("The context has an alive sub-process %s created by %s.", process.getCommand(), process.getOwner().language.getId()));
        }
    }

    private synchronized void checkSystemThreadsFinished() {
        if (!this.activeSystemThreads.isEmpty()) {
            SystemThread.LanguageSystemThread thread = this.activeSystemThreads.iterator().next();
            throw new IllegalStateException(String.format("The context has an alive system thread %s created by language %s.", thread.getName(), thread.languageId));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static PolyglotContextImpl preinitialize(PolyglotEngineImpl engine, PolyglotContextConfig.PreinitConfig preinitConfig, PolyglotSharingLayer sharableLayer, Set<PolyglotLanguage> languagesToPreinitialize, boolean emitWarning) {
        FileSystems.PreInitializeContextFileSystem fs = new FileSystems.PreInitializeContextFileSystem();
        FileSystems.PreInitializeContextFileSystem internalFs = new FileSystems.PreInitializeContextFileSystem();
        PolyglotContextConfig config = new PolyglotContextConfig(engine, fs, internalFs, preinitConfig);
        PolyglotContextImpl context = new PolyglotContextImpl(engine, config);
        Object object = engine.lock;
        synchronized (object) {
            engine.addContext(context);
        }
        context.inContextPreInitialization = true;
        context.sourcesToInvalidate = new ArrayList<com.oracle.truffle.api.source.Source>();
        try {
            if (sharableLayer != null && !context.claimSharingLayer(sharableLayer, languagesToPreinitialize)) {
                object = null;
                return object;
            }
            object = context;
            synchronized (object) {
                context.initializeContextLocals();
            }
            if (!languagesToPreinitialize.isEmpty()) {
                Object[] prev = context.engine.enter(context);
                try {
                    for (PolyglotLanguage language : languagesToPreinitialize) {
                        assert (language.engine == engine) : "invalid language";
                        if (PolyglotContextImpl.overridesPatchContext(language.getId())) {
                            context.getContextInitialized(language, null);
                            LOG.log(Level.FINE, "Pre-initialized context for language: {0}", language.getId());
                            continue;
                        }
                        if (!emitWarning) continue;
                        LOG.log(Level.WARNING, "Language {0} cannot be pre-initialized as it does not override TruffleLanguage.patchContext method.", language.getId());
                    }
                }
                finally {
                    context.leaveThreadChanged(prev, true, true, true);
                }
            }
            object = context;
            return object;
        }
        finally {
            context.inContextPreInitialization = false;
            for (PolyglotLanguage language : engine.languages) {
                if (language == null) continue;
                language.clearOptionValues();
            }
            Object object2 = engine.lock;
            synchronized (object2) {
                engine.removeContext(context);
            }
            for (com.oracle.truffle.api.source.Source sourceToInvalidate : context.sourcesToInvalidate) {
                EngineAccessor.SOURCE.invalidateAfterPreinitialiation(sourceToInvalidate);
            }
            context.singleThreadValue.reset();
            context.sourcesToInvalidate = null;
            context.threadLocalActions.prepareContextStore();
            fs.onPreInitializeContextEnd();
            internalFs.onPreInitializeContextEnd();
            FileSystems.resetDefaultFileSystemProvider();
            if (!config.logLevels.isEmpty()) {
                EngineAccessor.LANGUAGE.configureLoggers(context, null, context.getAllLoggers());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Object getOrCreateContextLoggers() {
        Object res = this.contextBoundLoggers;
        if (res == null) {
            PolyglotContextImpl polyglotContextImpl = this;
            synchronized (polyglotContextImpl) {
                res = this.contextBoundLoggers;
                if (res == null) {
                    res = EngineAccessor.LANGUAGE.createEngineLoggers(PolyglotLoggers.LoggerCache.newContextLoggerCache(this));
                    if (!this.config.logLevels.isEmpty()) {
                        EngineAccessor.LANGUAGE.configureLoggers(this, this.config.logLevels, res);
                    }
                    this.contextBoundLoggers = res;
                }
            }
        }
        return res;
    }

    private Object[] getAllLoggers() {
        Object defaultLoggers = EngineAccessor.LANGUAGE.getDefaultLoggers();
        Object engineLoggers = this.engine.getEngineLoggers();
        Object contextLoggers = this.contextBoundLoggers;
        ArrayList<Object> allLoggers = new ArrayList<Object>(3);
        allLoggers.add(defaultLoggers);
        if (engineLoggers != null) {
            allLoggers.add(engineLoggers);
        }
        if (contextLoggers != null) {
            allLoggers.add(contextLoggers);
        }
        return allLoggers.toArray(new Object[allLoggers.size()]);
    }

    private PolyglotEngineImpl.CancelExecution createCancelException(Node location) {
        return new PolyglotEngineImpl.CancelExecution(location, this.invalidMessage, this.invalidResourceLimit);
    }

    private ExitException createExitException(Node location) {
        return new ExitException(location, this.exitCode, this.exitMessage);
    }

    private static boolean overridesPatchContext(String languageId) {
        if (TruffleOptions.AOT) {
            return LanguageCache.overridesPathContext(languageId);
        }
        LanguageCache cache = LanguageCache.languages().get(languageId);
        for (Method m : cache.loadLanguage().getClass().getDeclaredMethods()) {
            if (!m.getName().equals("patchContext")) continue;
            return true;
        }
        return false;
    }

    synchronized void registerOnDispose(Closeable closeable) {
        if (this.disposing) {
            throw new IllegalStateException("Cannot register closeable when context is being disposed.");
        }
        if (this.closeables == null) {
            this.closeables = Collections.newSetFromMap(new WeakHashMap());
        }
        this.closeables.add(Objects.requireNonNull(closeable));
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("PolyglotContextImpl[");
        b.append("state=");
        State localState = this.state;
        b.append(localState.name());
        b.append(",disposing=");
        b.append(this.disposing);
        if (!localState.isClosed()) {
            if (this.isActive()) {
                b.append(", active");
            } else {
                b.append(", inactive");
            }
        }
        b.append(" languages=[");
        String sep = "";
        for (PolyglotLanguageContext languageContext : this.contexts) {
            if (!languageContext.isInitialized() && !languageContext.isCreated()) continue;
            b.append(sep);
            b.append(languageContext.language.getId());
            sep = ", ";
        }
        b.append("]");
        b.append("]");
        return b.toString();
    }

    @CompilerDirectives.TruffleBoundary
    void runOnCancelled() {
        Runnable onCancelledRunnable = this.config.onCancelled;
        if (onCancelledRunnable != null) {
            onCancelledRunnable.run();
        }
    }

    @CompilerDirectives.TruffleBoundary
    void runOnExited(int code) {
        Consumer<Integer> onExitedRunnable = this.config.onExited;
        if (onExitedRunnable != null) {
            onExitedRunnable.accept(code);
        }
    }

    @CompilerDirectives.TruffleBoundary
    void runOnClosed() {
        Runnable onClosedRunnable = this.config.onClosed;
        if (onClosedRunnable != null) {
            onClosedRunnable.run();
        }
    }

    synchronized void addSystemThread(SystemThread.LanguageSystemThread thread) {
        if (!this.state.isClosed()) {
            this.activeSystemThreads.add(thread);
        }
    }

    synchronized void removeSystemThread(SystemThread.LanguageSystemThread thread) {
        this.activeSystemThreads.remove(thread);
    }

    static {
        VALID_TRANSITIONS.put(State.DEFAULT, new State[]{State.CLOSING, State.INTERRUPTING, State.PENDING_EXIT, State.CANCELLING, State.EXITING});
        VALID_TRANSITIONS.put(State.CLOSING, new State[]{State.CLOSING_FINALIZING, State.CLOSING_INTERRUPTING, State.CLOSING_CANCELLING, State.CLOSING_PENDING_EXIT, State.CLOSING_EXITING, State.DEFAULT});
        VALID_TRANSITIONS.put(State.CLOSING_FINALIZING, new State[]{State.CLOSED, State.CLOSING_INTERRUPTING_FINALIZING, State.CLOSING_CANCELLING, State.CLOSING_EXITING, State.DEFAULT});
        VALID_TRANSITIONS.put(State.INTERRUPTING, new State[]{State.DEFAULT, State.CLOSING_INTERRUPTING, State.CANCELLING, State.PENDING_EXIT, State.EXITING});
        VALID_TRANSITIONS.put(State.PENDING_EXIT, new State[]{State.EXITING, State.CANCELLING});
        VALID_TRANSITIONS.put(State.CANCELLING, new State[]{State.CLOSING_CANCELLING});
        VALID_TRANSITIONS.put(State.CLOSING_INTERRUPTING, new State[]{State.CLOSING_INTERRUPTING_FINALIZING, State.CLOSING, State.CLOSING_PENDING_EXIT, State.CLOSING_CANCELLING, State.CLOSING_EXITING, State.INTERRUPTING});
        VALID_TRANSITIONS.put(State.CLOSING_INTERRUPTING_FINALIZING, new State[]{State.CLOSED_INTERRUPTED, State.CLOSING_FINALIZING, State.CLOSING_CANCELLING, State.CLOSING_EXITING, State.INTERRUPTING});
        VALID_TRANSITIONS.put(State.CLOSING_CANCELLING, new State[]{State.CLOSED_CANCELLED, State.CANCELLING});
        VALID_TRANSITIONS.put(State.CLOSING_PENDING_EXIT, new State[]{State.CLOSING_EXITING, State.CLOSING_CANCELLING, State.PENDING_EXIT});
        VALID_TRANSITIONS.put(State.CLOSING_EXITING, new State[]{State.CLOSED_EXITED, State.EXITING});
        VALID_TRANSITIONS.put(State.EXITING, new State[]{State.CLOSING_EXITING});
        VALID_TRANSITIONS.put(State.CLOSED, new State[0]);
        VALID_TRANSITIONS.put(State.CLOSED_CANCELLED, new State[0]);
        VALID_TRANSITIONS.put(State.CLOSED_EXITED, new State[0]);
    }

    private final class InterruptThreadLocalAction
    extends ThreadLocalAction {
        InterruptThreadLocalAction() {
            super(true, false);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        protected void perform(ThreadLocalAction.Access access) {
            PolyglotContextImpl.this.threadLocalActions.submit(new Thread[]{access.getThread()}, "engine", (ThreadLocalAction)this, true);
            State localState = PolyglotContextImpl.this.state;
            if (access.getThread() != PolyglotContextImpl.this.closingThread && (localState.isInterrupting() || localState == State.CLOSED_INTERRUPTED)) {
                PolyglotContextImpl[] polyglotContextImplArray = PolyglotContextImpl.this;
                synchronized (PolyglotContextImpl.this) {
                    PolyglotContextImpl[] interruptingChildContexts = PolyglotContextImpl.this.childContexts.toArray(new PolyglotContextImpl[0]);
                    // ** MonitorExit[var4_3] (shouldn't be in output)
                    for (PolyglotContextImpl childCtx : interruptingChildContexts) {
                        if (access.getThread() != childCtx.closingThread) continue;
                        return;
                    }
                    throw new PolyglotEngineImpl.InterruptExecution(access.getLocation());
                }
            }
        }
    }

    private final class CancellationThreadLocalAction
    extends ThreadLocalAction {
        CancellationThreadLocalAction() {
            super(false, false);
        }

        @Override
        protected void perform(ThreadLocalAction.Access access) {
            PolyglotContextImpl.this.threadLocalActions.submit(new Thread[]{access.getThread()}, "engine", (ThreadLocalAction)this, new PolyglotThreadLocalActions.HandshakeConfig(true, false, false, true));
            State localState = PolyglotContextImpl.this.state;
            if (localState.isCancelling() || localState.isExiting() || localState == State.CLOSED_CANCELLED || localState == State.CLOSED_EXITED) {
                if (localState.isExiting() || localState == State.CLOSED_EXITED) {
                    throw PolyglotContextImpl.this.createExitException(access.getLocation());
                }
                throw PolyglotContextImpl.this.createCancelException(access.getLocation());
            }
        }
    }

    private static final class UncachedLocationNode
    extends HostToGuestRootNode {
        UncachedLocationNode(PolyglotSharingLayer layer) {
            super(layer);
        }

        @Override
        protected Class<?> getReceiverType() {
            throw CompilerDirectives.shouldNotReachHere();
        }

        @Override
        protected Object executeImpl(PolyglotLanguageContext languageContext, Object receiver, Object[] args) {
            throw CompilerDirectives.shouldNotReachHere();
        }

        @Override
        public boolean isInternal() {
            return true;
        }
    }

    static class ContextWeakReference
    extends WeakReference<PolyglotContextImpl> {
        volatile boolean removed = false;
        volatile PolyglotSharingLayer layer;

        ContextWeakReference(PolyglotContextImpl referent) {
            super(referent, referent.engine.contextsReferenceQueue);
        }

        void freeSharing(PolyglotContextImpl context) {
            if (context != null) assert (this.layer == null || this.layer.equals(context.layer));
            if (this.layer != null && this.layer.isClaimed()) {
                this.layer.engine.freeSharingLayer(this.layer, context);
            }
        }
    }

    static final class ExitException
    extends ThreadDeath {
        private static final long serialVersionUID = -4838571769179260137L;
        private final Node location;
        private final SourceSection sourceSection;
        private final String exitMessage;
        private final int exitCode;

        ExitException(Node location, int exitCode, String exitMessage) {
            this(location, null, exitCode, exitMessage);
        }

        ExitException(SourceSection sourceSection, int exitCode, String exitMessage) {
            this(null, sourceSection, exitCode, exitMessage);
        }

        private ExitException(Node location, SourceSection sourceSection, int exitCode, String exitMessage) {
            this.location = location;
            this.sourceSection = sourceSection;
            this.exitCode = exitCode;
            this.exitMessage = exitMessage;
        }

        Node getLocation() {
            return this.location;
        }

        SourceSection getSourceLocation() {
            if (this.sourceSection != null) {
                return this.sourceSection;
            }
            return this.location == null ? null : this.location.getEncapsulatingSourceSection();
        }

        @Override
        public String getMessage() {
            return this.exitMessage;
        }

        int getExitCode() {
            return this.exitCode;
        }
    }

    static enum State {
        DEFAULT,
        INTERRUPTING,
        PENDING_EXIT,
        EXITING,
        CANCELLING,
        CLOSING,
        CLOSING_PENDING_EXIT,
        CLOSING_FINALIZING,
        CLOSING_INTERRUPTING,
        CLOSING_INTERRUPTING_FINALIZING,
        CLOSING_CANCELLING,
        CLOSING_EXITING,
        CLOSED,
        CLOSED_INTERRUPTED,
        CLOSED_CANCELLED,
        CLOSED_EXITED;


        boolean isInvalidOrClosed() {
            switch (this) {
                case CANCELLING: 
                case EXITING: 
                case CLOSING_CANCELLING: 
                case CLOSING_EXITING: 
                case CLOSED: 
                case CLOSED_INTERRUPTED: 
                case CLOSED_CANCELLED: 
                case CLOSED_EXITED: {
                    return true;
                }
            }
            return false;
        }

        boolean isInterrupting() {
            switch (this) {
                case INTERRUPTING: 
                case CLOSING_INTERRUPTING: 
                case CLOSING_INTERRUPTING_FINALIZING: {
                    return true;
                }
            }
            return false;
        }

        boolean isCancelling() {
            switch (this) {
                case CANCELLING: 
                case CLOSING_CANCELLING: {
                    return true;
                }
            }
            return false;
        }

        boolean isExiting() {
            switch (this) {
                case EXITING: 
                case CLOSING_EXITING: {
                    return true;
                }
            }
            return false;
        }

        boolean isClosing() {
            switch (this) {
                case CLOSING_CANCELLING: 
                case CLOSING_EXITING: 
                case CLOSING_INTERRUPTING: 
                case CLOSING_INTERRUPTING_FINALIZING: 
                case CLOSING: 
                case CLOSING_FINALIZING: 
                case CLOSING_PENDING_EXIT: {
                    return true;
                }
            }
            return false;
        }

        boolean isClosed() {
            switch (this) {
                case CLOSED: 
                case CLOSED_INTERRUPTED: 
                case CLOSED_CANCELLED: 
                case CLOSED_EXITED: {
                    return true;
                }
            }
            return false;
        }

        private boolean shouldCacheThreadInfo() {
            switch (this) {
                case CLOSING: 
                case CLOSING_PENDING_EXIT: 
                case DEFAULT: 
                case PENDING_EXIT: {
                    return true;
                }
            }
            return false;
        }
    }
}

