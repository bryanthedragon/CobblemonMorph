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
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
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
import java.util.Map.Entry;
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
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class PolyglotContextImpl implements PolyglotImpl.VMObject {
   private static final TruffleLogger LOG = TruffleLogger.getLogger("engine", PolyglotContextImpl.class);
   private static final InteropLibrary UNCACHED = InteropLibrary.getFactory().getUncached();
   private static final Object[] DISPOSED_CONTEXT_THREAD_LOCALS = new Object[0];
   private static final Map<PolyglotContextImpl.State, PolyglotContextImpl.State[]> VALID_TRANSITIONS = new EnumMap<>(PolyglotContextImpl.State.class);
   volatile PolyglotContextImpl.State state = PolyglotContextImpl.State.DEFAULT;
   final WeakAssumedValue<PolyglotThreadInfo> singleThreadValue = new WeakAssumedValue<>("Single thread");
   volatile boolean singleThreaded = true;
   private final Map<Thread, PolyglotThreadInfo> threads = new WeakHashMap<>();
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
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   final PolyglotLanguageContext[] contexts;
   final TruffleContext creatorTruffleContext;
   final TruffleContext currentTruffleContext;
   final PolyglotContextImpl parent;
   volatile Map<String, Value> polyglotBindings;
   volatile Value polyglotHostBindings;
   private final PolyglotBindings polyglotBindingsObject = new PolyglotBindings(this);
   final PolyglotLanguage creator;
   final PolyglotContextImpl.ContextWeakReference weakReference;
   final Set<ProcessHandlers.ProcessDecorator> subProcesses;
   @CompilerDirectives.CompilationFinal
   PolyglotContextConfig config;
   @CompilerDirectives.CompilationFinal
   private volatile FinalIntMap languageIndexMap;
   private final List<PolyglotContextImpl> childContexts = new ArrayList<>();
   boolean inContextPreInitialization;
   List<Source> sourcesToInvalidate;
   final AtomicLong volatileStatementCounter = new AtomicLong();
   long statementCounter;
   final long statementLimit;
   private volatile Object contextBoundLoggers;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   Object[] contextLocals;
   volatile boolean localsCleared;
   private ObjectSizeCalculator objectSizeCalculator;
   final PolyglotThreadLocalActions threadLocalActions;
   private Collection<Closeable> closeables;
   private final Set<PauseThreadLocalAction> pauseThreadLocalActions = new LinkedHashSet<>();
   @CompilerDirectives.CompilationFinal
   private Object hostContextImpl;
   final Node uncachedLocation;
   private final Set<SystemThread.LanguageSystemThread> activeSystemThreads = Collections.newSetFromMap(new HashMap<>());

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
      this.subProcesses = new HashSet<>();
      this.uncachedLocation = null;
   }

   PolyglotContextImpl(PolyglotEngineImpl engine, PolyglotContextConfig config) {
      this.parent = null;
      this.engine = engine;
      this.layer = new PolyglotSharingLayer(engine);
      this.config = config;
      this.creator = null;
      this.uncachedLocation = new PolyglotContextImpl.UncachedLocationNode(this.layer);
      this.creatorTruffleContext = EngineAccessor.LANGUAGE.createTruffleContext(this, true);
      this.currentTruffleContext = EngineAccessor.LANGUAGE.createTruffleContext(this, false);
      this.weakReference = new PolyglotContextImpl.ContextWeakReference(this);
      this.contexts = this.createContextArray();
      this.subProcesses = new HashSet<>();
      this.statementLimit = config.limits != null && config.limits.statementLimit != 0L ? config.limits.statementLimit : 9223372036854775806L;
      this.statementCounter = this.statementLimit;
      this.volatileStatementCounter.set(this.statementLimit);
      this.threadLocalActions = new PolyglotThreadLocalActions(this);
      PolyglotEngineImpl.ensureInstrumentsCreated(config.getConfiguredInstruments());
      if (!config.logLevels.isEmpty()) {
         EngineAccessor.LANGUAGE.configureLoggers(this, config.logLevels, this.getAllLoggers());
      }
   }

   PolyglotContextImpl(PolyglotLanguageContext creator, PolyglotContextConfig config) {
      PolyglotContextImpl parent = creator.context;
      this.parent = parent;
      this.layer = new PolyglotSharingLayer(parent.engine);
      this.config = config;
      this.engine = parent.engine;
      this.creator = creator.language;
      this.uncachedLocation = new PolyglotContextImpl.UncachedLocationNode(this.layer);
      this.statementLimit = 0L;
      this.weakReference = new PolyglotContextImpl.ContextWeakReference(this);
      this.creatorTruffleContext = EngineAccessor.LANGUAGE.createTruffleContext(this, true);
      this.currentTruffleContext = EngineAccessor.LANGUAGE.createTruffleContext(this, false);
      if (parent.state.isInterrupting()) {
         this.state = PolyglotContextImpl.State.INTERRUPTING;
      } else if (parent.state.isCancelling()) {
         this.state = PolyglotContextImpl.State.CANCELLING;
      } else if (parent.state.isExiting()) {
         this.state = PolyglotContextImpl.State.EXITING;
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
      this.subProcesses = new HashSet<>();
      this.engine.noInnerContexts.invalidate();
   }

   private boolean isTransitionAllowed(PolyglotContextImpl.State fromState, PolyglotContextImpl.State toState) {
      assert Thread.holdsLock(this);

      PolyglotContextImpl.State[] successors = VALID_TRANSITIONS.get(fromState);

      for (PolyglotContextImpl.State successor : successors) {
         if (successor == toState) {
            return this.isAdditionalTransitionConditionSatisfied(fromState, toState);
         }
      }

      return false;
   }

   private boolean isAdditionalTransitionConditionSatisfied(PolyglotContextImpl.State fromState, PolyglotContextImpl.State toState) {
      assert Thread.holdsLock(this);

      return fromState.isClosing() != toState.isClosing() && this.closingThread != Thread.currentThread()
         ? false
         : fromState.isExiting()
            || !toState.isExiting()
            || fromState == PolyglotContextImpl.State.PENDING_EXIT
            || fromState == PolyglotContextImpl.State.CLOSING_PENDING_EXIT
            || this.parent != null
            || this.skipPendingExit;
   }

   private boolean shouldCacheThreadInfo() {
      assert Thread.holdsLock(this);

      return this.state.shouldCacheThreadInfo() && !this.disposing;
   }

   void claimSharingLayer(PolyglotLanguage language) {
      PolyglotSharingLayer s = this.layer;
      if (!s.isClaimed()) {
         synchronized (this.engine.lock) {
            if (!s.isClaimed()) {
               assert !language.isHost() : "cannot claim context for a host language";

               this.engine.claimSharingLayer(s, this, language);

               assert s.isClaimed();

               this.weakReference.layer = s;
            }
         }
      }
   }

   boolean claimSharingLayer(PolyglotSharingLayer sharableLayer, Set<PolyglotLanguage> languages) {
      PolyglotSharingLayer s = this.layer;
      synchronized (this.engine.lock) {
         assert !s.isClaimed() : "sharing layer already claimed";

         if (!s.isClaimed()) {
            if (!s.claimLayerForContext(sharableLayer, this, languages)) {
               return false;
            }

            assert s.isClaimed();

            assert this.layer.equals(sharableLayer);

            this.weakReference.layer = s;
         }

         return true;
      }
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
      } catch (Throwable var7) {
         throw PolyglotImpl.guestToHostException(languageContext, var7, true);
      } finally {
         PolyglotValueDispatch.hostLeave(languageContext, prev);
      }
   }

   public void safepoint() {
      PolyglotLanguageContext languageContext = this.getHostContext();
      Object prev = PolyglotValueDispatch.hostEnter(languageContext);

      try {
         TruffleSafepoint.poll(this.uncachedLocation);
      } catch (Throwable var7) {
         throw PolyglotImpl.guestToHostException(languageContext, var7, true);
      } finally {
         PolyglotValueDispatch.hostLeave(languageContext, prev);
      }
   }

   private PolyglotLanguageContext[] createContextArray() {
      Collection<PolyglotLanguage> languages = this.engine.idToLanguage.values();
      PolyglotLanguageContext[] newContexts = new PolyglotLanguageContext[this.engine.languageCount];
      Iterator<PolyglotLanguage> languageIterator = languages.iterator();

      for (int i = 1; i < this.engine.languageCount; i++) {
         PolyglotLanguage language = languageIterator.next();
         newContexts[i] = new PolyglotLanguageContext(this, language);
      }

      PolyglotLanguage hostLanguage = this.engine.hostLanguage;
      PolyglotLanguageContext hostContext = new PolyglotLanguageContext(this, hostLanguage);
      newContexts[0] = hostContext;
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
      assert Thread.holdsLock(this);

      assert !this.state.isClosed();

      if (this.state.isClosing() && !this.state.shouldCacheThreadInfo()) {
         throw PolyglotEngineException.illegalState("Adding child context into a closing context.");
      } else {
         this.childContexts.add(child);
      }
   }

   static PolyglotContextImpl requireContext() {
      PolyglotContextImpl context = PolyglotFastThreadLocals.getContext(null);
      if (context == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw PolyglotEngineException.illegalState("There is no current context available.");
      } else {
         return context;
      }
   }

   public synchronized void explicitEnter() {
      try {
         Object[] prev = this.engine.enter(this);
         PolyglotThreadInfo current = this.getCurrentThreadInfo();

         assert current.getThread() == Thread.currentThread();

         current.explicitContextStack.addLast(prev);
      } catch (Throwable var3) {
         throw PolyglotImpl.guestToHostException(this.engine, var3);
      }
   }

   public synchronized void explicitLeave() {
      if (!this.state.isClosed()) {
         try {
            PolyglotThreadInfo current = this.getCurrentThreadInfo();
            LinkedList<Object[]> stack = current.explicitContextStack;
            if (!stack.isEmpty() && current.getThread() != null) {
               this.engine.leave(stack.removeLast(), this);
            } else {
               throw PolyglotEngineException.illegalState("The context is not entered explicity. A context can only be left if it was previously entered.");
            }
         } catch (Throwable var3) {
            throw PolyglotImpl.guestToHostException(this.engine, var3);
         }
      }
   }

   synchronized Future<Void> pause() {
      PauseThreadLocalAction pauseAction = new PauseThreadLocalAction(this);
      Future<Void> future = this.threadLocalActions
         .submit(null, "engine", pauseAction, new PolyglotThreadLocalActions.HandshakeConfig(true, true, false, false));
      this.pauseThreadLocalActions.add(pauseAction);
      return new ContextPauseHandle(pauseAction, future);
   }

   void resume(Future<Void> pauseFuture) {
      if (pauseFuture instanceof ContextPauseHandle && ((ContextPauseHandle)pauseFuture).pauseThreadLocalAction.context == this) {
         ContextPauseHandle pauseHandle = (ContextPauseHandle)pauseFuture;
         pauseHandle.resume();
      } else {
         throw new IllegalArgumentException("Resume method was not passed a valid pause future!");
      }
   }

   @CompilerDirectives.TruffleBoundary
   Object[] enterThreadChanged(
      boolean notifyEnter, boolean enterReverted, boolean pollSafepoint, boolean deactivateSafepoints, boolean polyglotThreadFirstEnter
   ) {
      PolyglotThreadInfo enteredThread = null;
      Object[] prev = null;
      Thread current = Thread.currentThread();
      if (JDKAccessor.isVirtualThread(current) && !(Truffle.getRuntime() instanceof DefaultTruffleRuntime)) {
         throw PolyglotEngineException.illegalState(
            "Using polyglot contexts on Java virtual threads is currently not supported with an optimizing Truffle runtime. As a workaround you may add the -Dtruffle.TruffleRuntime=com.oracle.truffle.api.impl.DefaultTruffleRuntime JVM argument to switch to a non-optimizing runtime when using virtual threads. Please note that performance is severly reduced in this mode. Loom support for optimizing runtimes will be added in a future release."
         );
      } else {
         Object[] var10;
         try {
            if (current instanceof SystemThread) {
               throw PolyglotEngineException.illegalState("Context cannot be entered on system threads.");
            }

            boolean needsInitialization = false;
            synchronized (this) {
               PolyglotThreadInfo threadInfo = this.getCurrentThreadInfo();
               if (enterReverted && threadInfo.getEnteredCount() == 0) {
                  this.threadLocalActions.notifyThreadActivation(threadInfo, false);
                  if ((
                        this.state.isCancelling()
                           || this.state.isExiting()
                           || this.state == PolyglotContextImpl.State.CLOSED_CANCELLED
                           || this.state == PolyglotContextImpl.State.CLOSED_EXITED
                     )
                     && !threadInfo.isActive()) {
                     this.notifyThreadClosed(threadInfo);
                  }

                  if ((this.state.isInterrupting() || this.state == PolyglotContextImpl.State.CLOSED_INTERRUPTED) && !threadInfo.isActive()) {
                     Thread.interrupted();
                     this.notifyAll();
                  }
               }

               if (deactivateSafepoints && threadInfo != PolyglotThreadInfo.NULL) {
                  this.threadLocalActions.notifyThreadActivation(threadInfo, false);
               }

               this.checkClosedOrDisposing();

               assert threadInfo != null;

               threadInfo = this.threads.get(current);
               if (threadInfo == null) {
                  threadInfo = this.createThreadInfo(current, polyglotThreadFirstEnter);
                  needsInitialization = true;
               }

               if (this.singleThreaded) {
                  this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
               }

               boolean transitionToMultiThreading = this.isSingleThreaded() && this.hasActiveOtherThread(true);
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
                     threadInfo.notifyEnter(this.engine, this);
                  } catch (Throwable var24) {
                     threadInfo.leaveInternal(prev);
                     throw var24;
                  }
               }

               enteredThread = threadInfo;
               Set<ThreadLocalAction> activatedActions = null;
               if (threadInfo.getEnteredCount() == 1 && !deactivateSafepoints) {
                  activatedActions = this.threadLocalActions.notifyThreadActivation(threadInfo, true);
               }

               if (transitionToMultiThreading) {
                  this.transitionToMultiThreaded();
               }

               if (needsInitialization) {
                  this.initializeNewThread(current);
               }

               if (threadInfo.getEnteredCount() == 1 && !this.pauseThreadLocalActions.isEmpty()) {
                  Iterator<PauseThreadLocalAction> threadLocalActionIterator = this.pauseThreadLocalActions.iterator();

                  while (threadLocalActionIterator.hasNext()) {
                     PauseThreadLocalAction threadLocalAction = threadLocalActionIterator.next();
                     if (!threadLocalAction.isPause()) {
                        threadLocalActionIterator.remove();
                     } else if (activatedActions == null || !activatedActions.contains(threadLocalAction)) {
                        this.threadLocalActions
                           .submit(
                              new Thread[]{Thread.currentThread()},
                              "engine",
                              threadLocalAction,
                              new PolyglotThreadLocalActions.HandshakeConfig(true, true, false, false)
                           );
                     }
                  }
               }

               this.setCachedThreadInfo(threadInfo);
            }

            if (needsInitialization) {
               EngineAccessor.INSTRUMENT.notifyThreadStarted(this.engine, this.creatorTruffleContext, current);
            }

            var10 = prev;
         } finally {
            if (pollSafepoint) {
               try {
                  TruffleSafepoint.pollHere(this.uncachedLocation);
               } catch (Throwable var25) {
                  if (enteredThread != null) {
                     this.leaveThreadChanged(prev, notifyEnter, true, polyglotThreadFirstEnter);
                  }

                  throw var25;
               }
            }
         }

         return var10;
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

      assert Thread.holdsLock(this);

      PolyglotThreadInfo info = this.getCachedThread();
      if (info.getThread() != Thread.currentThread()) {
         info = this.threads.get(Thread.currentThread());
         if (info == null) {
            info = PolyglotThreadInfo.NULL;
         }
      }

      assert info.getThread() == null || info.getThread() == Thread.currentThread();

      return info;
   }

   void setCachedThreadInfo(PolyglotThreadInfo info) {
      if (this.shouldCacheThreadInfo() && !this.threadLocalActions.hasActiveEvents()) {
         this.cachedThreadInfo = info;
      } else {
         this.cachedThreadInfo = PolyglotThreadInfo.NULL;
      }
   }

   synchronized void checkMultiThreadedAccess(PolyglotThread newThread) {
      boolean singleThread = this.singleThreaded ? !this.isActiveNotCancelled() : false;
      this.checkAllThreadAccesses(newThread, singleThread);
   }

   private void checkAllThreadAccesses(Thread enteringThread, boolean singleThread) {
      assert Thread.holdsLock(this);

      List<PolyglotLanguage> deniedLanguages = null;

      for (PolyglotLanguageContext context : this.contexts) {
         if (context.isInitialized()) {
            boolean accessAllowed = true;
            if (!EngineAccessor.LANGUAGE.isThreadAccessAllowed(context.env, enteringThread, singleThread)) {
               accessAllowed = false;
            }

            if (accessAllowed) {
               for (PolyglotThreadInfo seenThread : this.threads.values()) {
                  if (!EngineAccessor.LANGUAGE.isThreadAccessAllowed(context.env, seenThread.getThread(), singleThread)) {
                     accessAllowed = false;
                     break;
                  }
               }
            }

            if (!accessAllowed) {
               if (deniedLanguages == null) {
                  deniedLanguages = new ArrayList<>();
               }

               deniedLanguages.add(context.language);
            }
         }
      }

      if (deniedLanguages != null) {
         throw throwDeniedThreadAccess(enteringThread, singleThread, deniedLanguages);
      }
   }

   @CompilerDirectives.TruffleBoundary
   void leaveThreadChanged(Object[] prev, boolean notifyLeft, boolean entered, boolean dispose) {
      Throwable ex = null;
      synchronized (this) {
         Thread current = Thread.currentThread();
         if (dispose) {
            PolyglotThreadInfo info = this.threads.get(current);
            if (info == null) {
               return;
            }

            ex = this.notifyThreadDisposing(current);
         }

         this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
         PolyglotThreadInfo threadInfo = this.threads.get(current);

         assert threadInfo != null;

         PolyglotThreadInfo info = threadInfo;
         if (entered) {
            try {
               if (notifyLeft) {
                  info.notifyLeave(this.engine, this);
               }
            } finally {
               threadInfo.leaveInternal(prev);
            }
         }

         if (threadInfo.getEnteredCount() == 0) {
            this.threadLocalActions.notifyThreadActivation(threadInfo, false);
         }

         if ((
               this.state.isCancelling()
                  || this.state.isExiting()
                  || this.state == PolyglotContextImpl.State.CLOSED_CANCELLED
                  || this.state == PolyglotContextImpl.State.CLOSED_EXITED
            )
            && !threadInfo.isActive()) {
            this.notifyThreadClosed(threadInfo);
         }

         boolean somePauseThreadLocalActionIsActive = false;
         if (threadInfo.getEnteredCount() == 0 && !this.pauseThreadLocalActions.isEmpty()) {
            Iterator<PauseThreadLocalAction> threadLocalActionIterator = this.pauseThreadLocalActions.iterator();

            while (threadLocalActionIterator.hasNext()) {
               PauseThreadLocalAction threadLocalAction = threadLocalActionIterator.next();
               if (!threadLocalAction.isPause()) {
                  threadLocalActionIterator.remove();
               } else {
                  somePauseThreadLocalActionIsActive = true;
               }
            }
         }

         if (entered && !somePauseThreadLocalActionIsActive) {
            this.setCachedThreadInfo(threadInfo);
         }

         if ((this.state.isInterrupting() || this.state == PolyglotContextImpl.State.CLOSED_INTERRUPTED) && !threadInfo.isActive()) {
            Thread.interrupted();
            this.notifyAll();
         }

         if (dispose) {
            this.finishThreadDispose(current, threadInfo, ex);
         }
      }
   }

   private void finishThreadDispose(Thread current, PolyglotThreadInfo info, Throwable ex) {
      assert !info.isActive();

      if (this.cachedThreadInfo.getThread() == current) {
         this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
      }

      info.setContextThreadLocals(DISPOSED_CONTEXT_THREAD_LOCALS);
      this.threads.remove(current);
      if (ex != null) {
         throw sneakyThrow(ex);
      }
   }

   private Throwable notifyThreadDisposing(Thread current) {
      Throwable ex = null;

      for (PolyglotLanguageContext languageContext : this.contexts) {
         if (languageContext.isInitialized()) {
            try {
               EngineAccessor.LANGUAGE.disposeThread(languageContext.env, current);
            } catch (Throwable var9) {
               if (ex == null) {
                  ex = var9;
               } else {
                  ex.addSuppressed(var9);
               }
            }
         }
      }

      try {
         EngineAccessor.INSTRUMENT.notifyThreadFinished(this.engine, this.creatorTruffleContext, current);
      } catch (Throwable var8) {
         if (ex == null) {
            ex = var8;
         } else {
            ex.addSuppressed(var8);
         }
      }

      return ex;
   }

   private void initializeNewThread(Thread thread) {
      for (PolyglotLanguageContext context : this.contexts) {
         if (context.isInitialized()) {
            EngineAccessor.LANGUAGE.initializeThread(context.env, thread);
         }
      }
   }

   long getStatementsExecuted() {
      long count;
      if (this.engine.singleThreadPerContext.isValid()) {
         count = this.statementCounter;
      } else {
         count = this.volatileStatementCounter.get();
      }

      return this.statementLimit - count;
   }

   private void transitionToMultiThreaded() {
      assert Thread.holdsLock(this);

      for (PolyglotLanguageContext context : this.contexts) {
         if (context.isInitialized()) {
            context.ensureMultiThreadingInitialized();
         }
      }

      this.singleThreaded = false;
      this.singleThreadValue.invalidate();
      long statementsExecuted = this.statementLimit - this.statementCounter;
      this.volatileStatementCounter.getAndAdd(-statementsExecuted);
   }

   private PolyglotThreadInfo createThreadInfo(Thread current, boolean polyglotThreadFirstEnter) {
      assert Thread.holdsLock(this);

      PolyglotThreadInfo threadInfo = new PolyglotThreadInfo(this, current, polyglotThreadFirstEnter);
      boolean singleThread = this.isSingleThreaded();
      List<PolyglotLanguage> deniedLanguages = null;

      for (PolyglotLanguageContext context : this.contexts) {
         if (context.isInitialized() && !EngineAccessor.LANGUAGE.isThreadAccessAllowed(context.env, current, singleThread)) {
            if (deniedLanguages == null) {
               deniedLanguages = new ArrayList<>();
            }

            deniedLanguages.add(context.language);
         }
      }

      if (deniedLanguages != null) {
         throw throwDeniedThreadAccess(current, singleThread, deniedLanguages);
      } else {
         this.singleThreadValue.update(threadInfo);
         return threadInfo;
      }
   }

   static RuntimeException throwDeniedThreadAccess(Thread current, boolean accessSingleThreaded, List<PolyglotLanguage> deniedLanguages) {
      StringBuilder languagesString = new StringBuilder("");

      for (PolyglotLanguage language : deniedLanguages) {
         if (languagesString.length() != 0) {
            languagesString.append(", ");
         }

         languagesString.append(language.getId());
      }

      String message;
      if (accessSingleThreaded) {
         message = String.format("Single threaded access requested by thread %s but is not allowed for language(s) %s.", current, languagesString);
      } else {
         message = String.format("Multi threaded access requested by thread %s but is not allowed for language(s) %s.", current, languagesString);
      }

      throw PolyglotEngineException.illegalState(message);
   }

   public Value getBindings(String languageId) {
      PolyglotLanguageContext languageContext = this.lookupLanguageContext(languageId);

      assert languageContext != null;

      Object prev = PolyglotValueDispatch.hostEnter(languageContext);

      Value e;
      try {
         if (!languageContext.isInitialized()) {
            languageContext.ensureInitialized(null);
         }

         e = languageContext.getHostBindings();
      } catch (Throwable var8) {
         throw PolyglotImpl.guestToHostException(languageContext, var8, true);
      } finally {
         PolyglotValueDispatch.hostLeave(languageContext, prev);
      }

      return e;
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
      } catch (Throwable var2) {
         throw PolyglotImpl.guestToHostException(this.engine, var2);
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

   private void initPolyglotBindings() {
      synchronized (this) {
         if (this.polyglotBindings == null) {
            this.polyglotBindings = new ConcurrentHashMap<>();
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
         } else {
            throw this.createExitException(null);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private RuntimeException failValueSharing() {
      throw new PolyglotLanguageContext.ValueMigrationException(
         "A value was tried to be migrated from one context to a different context. Value migration for the current context was disabled and is therefore disallowed.",
         this.uncachedLocation
      );
   }

   Object migrateValue(Object value, PolyglotContextImpl valueContext) {
      if (!this.config.allowValueSharing) {
         throw this.failValueSharing();
      } else {
         Object result = this.engine.host.migrateValue(this, value, valueContext);
         if (result != null) {
            return result;
         } else {
            assert value instanceof TruffleObject;

            if (value instanceof OtherContextGuestObject) {
               OtherContextGuestObject otherValue = (OtherContextGuestObject)value;
               if (otherValue.receiverContext == this && otherValue.delegateContext == valueContext) {
                  return otherValue;
               } else {
                  return otherValue.receiverContext == valueContext && otherValue.delegateContext == this
                     ? otherValue.delegate
                     : new OtherContextGuestObject(this, otherValue.delegate, valueContext);
               }
            } else {
               assert value instanceof TruffleObject;

               return new OtherContextGuestObject(this, value, valueContext);
            }
         }
      }
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
      return CompilerDirectives.isPartialEvaluationConstant(this) ? this.getLanguageContextImpl(languageClass) : this.getLanguageContextBoundary(languageClass);
   }

   @CompilerDirectives.TruffleBoundary
   private PolyglotLanguageContext getLanguageContextBoundary(Class<? extends TruffleLanguage<?>> languageClass) {
      return this.getLanguageContextImpl(languageClass);
   }

   PolyglotLanguageContext findLanguageContext(Class<? extends TruffleLanguage> languageClazz) {
      PolyglotLanguage directLanguage = this.engine.getLanguage(languageClazz, false);
      if (directLanguage != null) {
         return this.getContext(directLanguage);
      } else {
         for (PolyglotLanguageContext lang : this.contexts) {
            if (lang.isInitialized()) {
               TruffleLanguage<?> language = EngineAccessor.LANGUAGE.getLanguage(lang.env);
               if (languageClazz != TruffleLanguage.class && languageClazz.isInstance(language)) {
                  return lang;
               }
            }
         }

         Set<String> languageNames = new HashSet<>();

         for (PolyglotLanguageContext langx : this.contexts) {
            if (langx.isInitialized()) {
               languageNames.add(langx.language.cache.getClassName());
            }
         }

         throw PolyglotEngineException.illegalState("Cannot find language " + languageClazz + " among " + languageNames);
      }
   }

   private PolyglotLanguageContext getLanguageContextImpl(Class<? extends TruffleLanguage<?>> languageClass) {
      FinalIntMap map = this.languageIndexMap;
      int indexValue = map != null ? map.get(languageClass) : -1;
      if (indexValue == -1) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         synchronized (this) {
            if (this.languageIndexMap == null) {
               this.languageIndexMap = new FinalIntMap();
            }

            indexValue = this.languageIndexMap.get(languageClass);
            if (indexValue == -1) {
               PolyglotLanguageContext context = this.findLanguageContext(languageClass);
               indexValue = context.language.engineIndex;
               this.languageIndexMap.put(languageClass, indexValue);
            }
         }
      }

      return this.contexts[indexValue];
   }

   void initializeInnerContextLanguage(String languageId) {
      PolyglotLanguage language = this.engine.idToLanguage.get(languageId);

      assert language != null : "language creating the inner context not be found";

      Object prev = this.engine.enterIfNeeded(this, true);

      try {
         this.initializeLanguage(language);
      } finally {
         this.engine.leaveIfNeeded(prev, this);
      }
   }

   private boolean initializeLanguage(PolyglotLanguage language) {
      PolyglotLanguageContext languageContext = this.getContext(language);

      assert languageContext != null;

      languageContext.checkAccess(null);
      return !languageContext.isInitialized() ? languageContext.ensureInitialized(null) : false;
   }

   public boolean initializeLanguage(String languageId) {
      PolyglotLanguageContext languageContext = this.lookupLanguageContext(languageId);
      Object prev = PolyglotValueDispatch.hostEnter(languageContext);

      boolean t;
      try {
         t = this.initializeLanguage(languageContext.language);
      } catch (Throwable var8) {
         throw PolyglotImpl.guestToHostException(languageContext, var8, true);
      } finally {
         PolyglotValueDispatch.hostLeave(languageContext, prev);
      }

      return t;
   }

   public Value parse(String languageId, org.graalvm.polyglot.Source source) {
      PolyglotLanguageContext languageContext = this.lookupLanguageContext(languageId);

      assert languageContext != null;

      Object prev = PolyglotValueDispatch.hostEnter(languageContext);

      Value var7;
      try {
         Source truffleSource = (Source)this.getAPIAccess().getReceiver(source);
         languageContext.checkAccess(null);
         languageContext.ensureInitialized(null);
         CallTarget target = languageContext.parseCached(null, truffleSource, null);
         var7 = languageContext.asValue(new PolyglotParsedEval(languageContext, truffleSource, target));
      } catch (Throwable var11) {
         throw PolyglotImpl.guestToHostException(languageContext, var11, true);
      } finally {
         PolyglotValueDispatch.hostLeave(languageContext, prev);
      }

      return var7;
   }

   private PolyglotLanguageContext lookupLanguageContext(String languageId) {
      try {
         PolyglotLanguage language = this.requirePublicLanguage(languageId);
         return this.getContext(language);
      } catch (Throwable var4) {
         throw PolyglotImpl.guestToHostException(this.engine, var4);
      }
   }

   public Value eval(String languageId, org.graalvm.polyglot.Source source) {
      PolyglotLanguageContext languageContext = this.lookupLanguageContext(languageId);

      assert languageContext != null;

      Object prev = PolyglotValueDispatch.hostEnter(languageContext);

      Value e;
      try {
         Source truffleSource = (Source)this.getAPIAccess().getReceiver(source);
         languageContext.checkAccess(null);
         languageContext.ensureInitialized(null);
         CallTarget target = languageContext.parseCached(null, truffleSource, null);
         Object result = target.call(PolyglotImpl.EMPTY_ARGS);

         Value hostValue;
         try {
            hostValue = languageContext.asValue(result);
         } catch (ClassCastException | NullPointerException var14) {
            throw new AssertionError(String.format("Language %s returned an invalid return value %s. Must be an interop value.", languageId, result), var14);
         }

         if (truffleSource.isInteractive()) {
            printResult(languageContext, result);
         }

         e = hostValue;
      } catch (Throwable var15) {
         throw PolyglotImpl.guestToHostException(languageContext, var15, true);
      } finally {
         PolyglotValueDispatch.hostLeave(languageContext, prev);
      }

      return e;
   }

   public PolyglotLanguage requirePublicLanguage(String languageId) {
      PolyglotLanguage language = this.engine.idToLanguage.get(languageId);
      if (language != null && !language.cache.isInternal()) {
         return language;
      } else {
         this.engine.requirePublicLanguage(languageId);

         assert false;

         return null;
      }
   }

   @CompilerDirectives.TruffleBoundary
   static void printResult(PolyglotLanguageContext languageContext, Object result) {
      if (EngineAccessor.LANGUAGE.isVisible(languageContext.env, result)) {
         String stringResult;
         try {
            stringResult = UNCACHED.asString(UNCACHED.toDisplayString(languageContext.getLanguageView(result), true));
         } catch (UnsupportedMessageException var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }

         try {
            OutputStream out = languageContext.context.config.out;
            out.write(stringResult.getBytes(StandardCharsets.UTF_8));
            out.write(System.getProperty("line.separator").getBytes(StandardCharsets.UTF_8));
         } catch (IOException var4) {
            throw new IllegalStateException(var4);
         }
      }
   }

   private static boolean isCurrentEngineHostCallback(PolyglotEngineImpl engine) {
      RootNode topMostGuestToHostRootNode = Truffle.getRuntime().iterateFrames(f -> {
         RootNode root = ((RootCallTarget)f.getCallTarget()).getRootNode();
         return EngineAccessor.HOST.isGuestToHostRootNode(root) ? root : null;
      });
      if (topMostGuestToHostRootNode == null) {
         return false;
      } else {
         PolyglotSharingLayer sharing = (PolyglotSharingLayer)EngineAccessor.NODES.getSharingLayer(topMostGuestToHostRootNode);
         PolyglotEngineImpl rootEngine = sharing.engine;
         return rootEngine == engine;
      }
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
      } catch (Throwable var7) {
         PolyglotException polyglotException = PolyglotImpl.guestToHostException(this.getHostContext(), var7, false);
         if (!cancelIfExecuting && this.state.isInvalidOrClosed() && (polyglotException.isCancelled() || polyglotException.isExit())) {
            try {
               this.closeAndMaybeWait(false, null);
            } catch (Throwable var6) {
               PolyglotException closeFinishPolyglotException = PolyglotImpl.guestToHostException(this.getHostContext(), var7, false);
               polyglotException.addSuppressed(closeFinishPolyglotException);
            }
         }

         throw polyglotException;
      }
   }

   void cancel(boolean resourceLimit, String message) {
      String cancelMessage = message == null ? "Context execution was cancelled." : message;
      if (this.parent == null) {
         this.engine.polyglotHostService.notifyContextCancellingOrExiting(this, false, 0, resourceLimit, cancelMessage);
      }

      List<Future<Void>> futures = this.setCancelling(resourceLimit, cancelMessage);
      this.closeHereOrCancelInCleanupThread(futures);
   }

   void initiateCancelOrExit(boolean exit, int code, boolean resourceLimit, String message) {
      assert this.parent == null;

      this.initiateCancelOrExitLock.lock();

      try {
         List<Future<Void>> futures;
         if (exit) {
            futures = this.setExiting(null, code, message, true);
         } else {
            futures = this.setCancelling(resourceLimit, message);
         }

         if (!futures.isEmpty()) {
            this.cancellationOrExitingFutures = futures;
         }
      } finally {
         this.initiateCancelOrExitLock.unlock();
      }
   }

   void closeAndMaybeWait(boolean force, List<Future<Void>> futures) {
      if (force) {
         PolyglotEngineImpl.cancelOrExit(this, futures);
      } else {
         boolean closeCompleted = this.closeImpl(true);
         if (!closeCompleted) {
            throw PolyglotEngineException.illegalState(
               String.format("The context is currently executing on another thread. Set cancelIfExecuting to true to stop the execution on this thread.")
            );
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

   private void setState(PolyglotContextImpl.State targetState) {
      assert Thread.holdsLock(this);

      assert this.isTransitionAllowed(this.state, targetState) : "Transition from " + this.state.name() + " to " + targetState.name() + " not allowed!";

      this.state = targetState;
      this.notifyAll();
   }

   private List<Future<Void>> setInterrupting() {
      assert Thread.holdsLock(this);

      List<Future<Void>> futures = new ArrayList<>();
      if (!this.state.isInterrupting()
         && !this.state.isInvalidOrClosed()
         && this.state != PolyglotContextImpl.State.PENDING_EXIT
         && this.state != PolyglotContextImpl.State.CLOSING_PENDING_EXIT) {
         PolyglotContextImpl.State targetState;
         switch (this.state) {
            case CLOSING:
               targetState = PolyglotContextImpl.State.CLOSING_INTERRUPTING;
               break;
            case CLOSING_FINALIZING:
               targetState = PolyglotContextImpl.State.CLOSING_INTERRUPTING_FINALIZING;
               break;
            default:
               targetState = PolyglotContextImpl.State.INTERRUPTING;
         }

         this.setState(targetState);
         this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
         futures.add(this.threadLocalActions.submit(null, "engine", new PolyglotContextImpl.InterruptThreadLocalAction(), true));
         this.maybeSendInterrupt();
      }

      return futures;
   }

   private void unsetInterrupting() {
      assert Thread.holdsLock(this);

      if (this.state.isInterrupting()) {
         PolyglotContextImpl.State targetState;
         switch (this.state) {
            case CLOSING_INTERRUPTING:
               targetState = PolyglotContextImpl.State.CLOSING;
               break;
            case CLOSING_INTERRUPTING_FINALIZING:
               targetState = PolyglotContextImpl.State.CLOSING_FINALIZING;
               break;
            default:
               targetState = PolyglotContextImpl.State.DEFAULT;
         }

         this.setState(targetState);
      }
   }

   private void finishInterruptForChildContexts() {
      PolyglotContextImpl[] childContextsToInterrupt;
      synchronized (this) {
         this.unsetInterrupting();
         childContextsToInterrupt = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
      }

      for (PolyglotContextImpl childCtx : childContextsToInterrupt) {
         childCtx.finishInterruptForChildContexts();
      }
   }

   private List<Future<Void>> interruptChildContexts() {
      PolyglotContextImpl[] childContextsToInterrupt = null;
      List<Future<Void>> futures;
      synchronized (this) {
         futures = new ArrayList<>(this.setInterrupting());
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

   private void validateInterruptPrecondition(PolyglotContextImpl operationSource) {
      PolyglotContextImpl[] childContextsToInterrupt;
      synchronized (this) {
         PolyglotThreadInfo info = this.getCurrentThreadInfo();
         if (info != PolyglotThreadInfo.NULL && info.isActive()) {
            throw PolyglotEngineException.illegalState(
               String.format("Cannot interrupt context from a thread where %s context is active.", this == operationSource ? "the" : "its child")
            );
         }

         childContextsToInterrupt = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
      }

      for (PolyglotContextImpl childCtx : childContextsToInterrupt) {
         childCtx.validateInterruptPrecondition(operationSource);
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public boolean interrupt(Duration timeout) {
      try {
         if (this.parent != null) {
            throw PolyglotEngineException.illegalState("Cannot interrupt inner context separately.");
         } else {
            long startMillis = System.currentTimeMillis();
            PolyglotContextImpl[] childContextsToInterrupt = null;
            this.interruptingLock.lock();
            boolean var60 = false /* VF: Semaphore variable */;

            boolean var72;
            label481: {
               boolean var70;
               try {
                  var60 = true;
                  this.validateInterruptPrecondition(this);
                  List<Future<Void>> futures;
                  synchronized (this) {
                     if (this.state.isClosed()) {
                        var72 = true;
                        var60 = false;
                        break label481;
                     }

                     futures = new ArrayList<>(this.setInterrupting());
                     if (!futures.isEmpty()) {
                        childContextsToInterrupt = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
                     }
                  }

                  if (childContextsToInterrupt != null) {
                     for (PolyglotContextImpl childCtx : childContextsToInterrupt) {
                        futures.addAll(childCtx.interruptChildContexts());
                     }
                  }

                  var70 = PolyglotEngineImpl.cancelOrExitOrInterrupt(this, futures, startMillis, timeout);
                  var60 = false;
               } finally {
                  if (var60) {
                     try {
                        if (childContextsToInterrupt != null) {
                           PolyglotContextImpl[] childContextsToFinishInterrupt;
                           synchronized (this) {
                              this.unsetInterrupting();
                              childContextsToFinishInterrupt = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
                           }

                           for (PolyglotContextImpl childCtx : childContextsToFinishInterrupt) {
                              childCtx.finishInterruptForChildContexts();
                           }
                        }
                     } finally {
                        this.interruptingLock.unlock();
                     }
                  }
               }

               try {
                  if (childContextsToInterrupt != null) {
                     PolyglotContextImpl[] childContextsToFinishInterrupt;
                     synchronized (this) {
                        this.unsetInterrupting();
                        childContextsToFinishInterrupt = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
                     }

                     for (PolyglotContextImpl childCtx : childContextsToFinishInterrupt) {
                        childCtx.finishInterruptForChildContexts();
                     }
                  }
               } finally {
                  this.interruptingLock.unlock();
               }

               return var70;
            }

            try {
               if (childContextsToInterrupt != null) {
                  PolyglotContextImpl[] childContextsToFinishInterrupt;
                  synchronized (this) {
                     this.unsetInterrupting();
                     childContextsToFinishInterrupt = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
                  }

                  for (PolyglotContextImpl childCtx : childContextsToFinishInterrupt) {
                     childCtx.finishInterruptForChildContexts();
                  }
               }
            } finally {
               this.interruptingLock.unlock();
            }

            return var72;
         }
      } catch (Throwable var69) {
         throw PolyglotImpl.guestToHostException(this.engine, var69);
      }
   }

   public Value asValue(Object hostValue) {
      PolyglotLanguageContext languageContext = this.getHostContext();
      Object prev = PolyglotValueDispatch.hostEnter(languageContext);

      try {
         this.checkClosed();
         PolyglotLanguageContext targetLanguageContext;
         if (hostValue instanceof Value) {
            PolyglotLanguageContext valueContext = (PolyglotLanguageContext)this.getAPIAccess().getContext((Value)hostValue);
            if (valueContext != null && valueContext.context == this) {
               return (Value)hostValue;
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

         return targetLanguageContext.asValue(this.toGuestValue(null, hostValue, true));
      } catch (Throwable var10) {
         throw PolyglotImpl.guestToHostException(this.getHostContext(), var10, true);
      } finally {
         PolyglotValueDispatch.hostLeave(languageContext, prev);
      }
   }

   static PolyglotEngineImpl getConstantEngine(Node node) {
      if (!CompilerDirectives.inCompiledCode() || !CompilerDirectives.isPartialEvaluationConstant(node)) {
         return null;
      } else if (node == null) {
         return null;
      } else {
         RootNode root = node.getRootNode();
         if (root == null) {
            return null;
         } else {
            PolyglotSharingLayer layer = (PolyglotSharingLayer)EngineAccessor.NODES.getSharingLayer(root);
            return layer != null ? layer.engine : null;
         }
      }
   }

   Object toGuestValue(Node node, Object hostValue, boolean asValue) {
      PolyglotEngineImpl localEngine = getConstantEngine(node);
      PolyglotContextImpl localContext;
      if (localEngine == null) {
         localEngine = this.engine;
         localContext = this;
      } else {
         localContext = localEngine.singleContextValue.getConstant();
         if (localContext == null) {
            localContext = this;
         }
      }

      Object value = PolyglotHostAccess.toGuestValue(localContext, hostValue);
      return localEngine.host.toGuestValue(localContext.getHostContextImpl(), value, asValue);
   }

   boolean waitForThreads(long startMillis, long timeoutMillis) {
      synchronized (this) {
         boolean otherThreadActive;
         for (long timeElapsed = System.currentTimeMillis() - startMillis;
            (otherThreadActive = this.hasActiveOtherThread(true)) && (timeoutMillis == 0L || timeElapsed < timeoutMillis);
            timeElapsed = System.currentTimeMillis() - startMillis
         ) {
            try {
               if (timeoutMillis == 0L) {
                  this.wait();
               } else {
                  this.wait(timeoutMillis - timeElapsed);
               }
            } catch (InterruptedException var11) {
            }
         }

         return !otherThreadActive;
      }
   }

   boolean isSingleThreaded() {
      return this.singleThreaded;
   }

   Map<Thread, PolyglotThreadInfo> getSeenThreads() {
      assert Thread.holdsLock(this);

      return this.threads;
   }

   private boolean isActiveNotCancelled() {
      return this.isActiveNotCancelled(true);
   }

   synchronized boolean isActiveNotCancelled(boolean includePolyglotThreads) {
      for (PolyglotThreadInfo seenTinfo : this.threads.values()) {
         if ((includePolyglotThreads || !seenTinfo.isPolyglotThread(this)) && seenTinfo.isActiveNotCancelled()) {
            return true;
         }
      }

      return false;
   }

   synchronized boolean isActive() {
      for (PolyglotThreadInfo seenTinfo : this.threads.values()) {
         if (seenTinfo.isActive()) {
            return true;
         }
      }

      return false;
   }

   synchronized boolean isActive(Thread thread) {
      PolyglotThreadInfo info = this.threads.get(thread);
      return info != null && info != PolyglotThreadInfo.NULL ? info.isActive() : false;
   }

   private PolyglotThreadInfo getFirstActiveOtherThread(boolean includePolyglotThreads) {
      assert Thread.holdsLock(this);

      for (PolyglotThreadInfo otherInfo : this.threads.values()) {
         if ((includePolyglotThreads || !otherInfo.isPolyglotThread(this)) && !otherInfo.isCurrent() && otherInfo.isActive()) {
            return otherInfo;
         }
      }

      return null;
   }

   boolean hasActiveOtherThread(boolean includePolyglotThreads) {
      return this.getFirstActiveOtherThread(includePolyglotThreads) != null;
   }

   private void notifyThreadClosed(PolyglotThreadInfo info) {
      assert Thread.holdsLock(this);

      if (!info.cancelled) {
         info.cancelled = true;
         Thread.interrupted();
      }

      this.notifyAll();
   }

   long calculateHeapSize(long stopAtBytes, AtomicBoolean calculationCancelled) {
      ObjectSizeCalculator localObjectSizeCalculator;
      synchronized (this) {
         localObjectSizeCalculator = this.objectSizeCalculator;
         if (localObjectSizeCalculator == null) {
            localObjectSizeCalculator = new ObjectSizeCalculator();
            this.objectSizeCalculator = localObjectSizeCalculator;
         }
      }

      return localObjectSizeCalculator.calculateObjectSize(this.getContextHeapRoots(), stopAtBytes, calculationCancelled);
   }

   private Object[] getContextHeapRoots() {
      List<Object> heapRoots = new ArrayList<>();
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
      FrameInstance[][] frameInstancesPerThread = PolyglotStackFramesRetriever.getStackFrames(this);

      for (FrameInstance[] threadInstances : frameInstancesPerThread) {
         for (FrameInstance frameInstance : threadInstances) {
            Frame frame = frameInstance.getFrame(FrameInstance.FrameAccess.READ_ONLY);
            RootNode rootNode = ((RootCallTarget)frameInstance.getCallTarget()).getRootNode();
            if (!(rootNode instanceof HostToGuestRootNode)) {
               if (!EngineAccessor.HOST.isGuestToHostRootNode(rootNode)) {
                  heapRoots.add(frame);
               } else {
                  for (Object obj : frame.getArguments()) {
                     if (obj instanceof Object[]) {
                        for (Object elem : (Object[])obj) {
                           this.addRootPointerForGuestToHostStackFrameArgument(elem, heapRoots);
                        }
                     } else {
                        this.addRootPointerForGuestToHostStackFrameArgument(obj, heapRoots);
                     }
                  }
               }
            }
         }
      }
   }

   private void addRootPointersForContext(List<Object> heapRoots) {
      synchronized (this) {
         for (PolyglotLanguageContext context : this.contexts) {
            if (context.isCreated()) {
               heapRoots.add(context.getContextImpl());
            }
         }

         if (this.polyglotBindings != null) {
            for (Entry<String, Value> binding : this.polyglotBindings.entrySet()) {
               heapRoots.add(binding.getKey());
               if (binding.getValue() != null) {
                  heapRoots.add(this.getAPIAccess().getReceiver(binding.getValue()));
               }
            }
         }
      }

      heapRoots.add(this.contextLocals);
      PolyglotContextImpl[] childContextStartPoints;
      synchronized (this) {
         for (PolyglotThreadInfo info : this.threads.values()) {
            heapRoots.add(info.getContextThreadLocals());
         }

         childContextStartPoints = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
      }

      for (PolyglotContextImpl childCtx : childContextStartPoints) {
         childCtx.addRootPointersForContext(heapRoots);
      }
   }

   private List<Future<Void>> setCancelling(boolean resourceLimit, String message) {
      assert message != null;

      PolyglotContextImpl[] childContextsToCancel = null;
      List<Future<Void>> futures = new ArrayList<>();
      synchronized (this) {
         if (!this.state.isInvalidOrClosed()) {
            PolyglotContextImpl.State targetState;
            if (this.state.isClosing()) {
               targetState = PolyglotContextImpl.State.CLOSING_CANCELLING;
            } else {
               targetState = PolyglotContextImpl.State.CANCELLING;
            }

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
         assert !futures.isEmpty();

         for (PolyglotContextImpl childCtx : childContextsToCancel) {
            futures.addAll(childCtx.setCancelling(resourceLimit, message));
         }
      }

      return this.getCancellingOrExitingFutures(futures);
   }

   private void submitCancellationThreadLocalAction(List<Future<Void>> futures) {
      PolyglotThreadInfo info = this.getCurrentThreadInfo();
      futures.add(this.threadLocalActions.submit(null, "engine", new PolyglotContextImpl.CancellationThreadLocalAction(), true));
      if (info != PolyglotThreadInfo.NULL) {
         info.cancelled = true;
         Thread.interrupted();
      }

      this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
   }

   private List<Future<Void>> setExiting(PolyglotContextImpl triggeringParent, int code, String message, boolean skipPendingExit) {
      PolyglotContextImpl[] childContextsToCancel = null;
      List<Future<Void>> futures = new ArrayList<>();
      synchronized (this) {
         if (!this.state.isInvalidOrClosed()) {
            assert message != null;

            PolyglotContextImpl.State targetState;
            if (this.state.isClosing()) {
               targetState = PolyglotContextImpl.State.CLOSING_EXITING;
            } else {
               targetState = PolyglotContextImpl.State.EXITING;
            }

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
      if (this.parent == null && futures.isEmpty()) {
         this.initiateCancelOrExitLock.lock();

         try {
            if (this.cancellationOrExitingFutures != null) {
               toRet = this.cancellationOrExitingFutures;
               this.cancellationOrExitingFutures = null;
            }
         } finally {
            this.initiateCancelOrExitLock.unlock();
         }
      }

      return toRet;
   }

   private void setClosingState() {
      assert Thread.holdsLock(this);

      this.closingThread = Thread.currentThread();
      this.closingLock.lock();
      PolyglotContextImpl.State targetState;
      switch (this.state) {
         case CANCELLING:
            targetState = PolyglotContextImpl.State.CLOSING_CANCELLING;
            break;
         case EXITING:
            targetState = PolyglotContextImpl.State.CLOSING_EXITING;
            break;
         case INTERRUPTING:
            targetState = PolyglotContextImpl.State.CLOSING_INTERRUPTING;
            break;
         default:
            targetState = PolyglotContextImpl.State.CLOSING;
      }

      this.setState(targetState);
   }

   private void setFinalizingState() {
      assert Thread.holdsLock(this);

      assert this.closingThread == Thread.currentThread();

      assert this.closingLock.isHeldByCurrentThread();

      PolyglotContextImpl.State targetState;
      switch (this.state) {
         case CLOSING_INTERRUPTING:
            targetState = PolyglotContextImpl.State.CLOSING_INTERRUPTING_FINALIZING;
            break;
         case CLOSING:
            targetState = PolyglotContextImpl.State.CLOSING_FINALIZING;
            break;
         default:
            return;
      }

      this.setState(targetState);
   }

   private void setClosedState() {
      assert Thread.holdsLock(this);

      assert this.state.isClosing() : this.state.name();

      PolyglotContextImpl.State targetState;
      switch (this.state) {
         case CLOSING_CANCELLING:
            targetState = PolyglotContextImpl.State.CLOSED_CANCELLED;
            break;
         case CLOSING_EXITING:
            targetState = PolyglotContextImpl.State.CLOSED_EXITED;
            break;
         case CLOSING_INTERRUPTING_FINALIZING:
            targetState = PolyglotContextImpl.State.CLOSED_INTERRUPTED;
            break;
         case CLOSING_FINALIZING:
            targetState = PolyglotContextImpl.State.CLOSED;
            break;
         default:
            throw new IllegalStateException("Cannot close polyglot context in the current state!");
      }

      this.setState(targetState);

      assert this.state.isClosed() : this.state.name();
   }

   private void restoreFromClosingState(boolean cancelOperation) {
      assert Thread.holdsLock(this);

      assert this.state.isClosing() : this.state.name();

      assert !cancelOperation : "Close initiated for an invalid context must not fail!";

      PolyglotContextImpl.State targetState;
      switch (this.state) {
         case CLOSING_CANCELLING:
            targetState = PolyglotContextImpl.State.CANCELLING;
            break;
         case CLOSING_EXITING:
            targetState = PolyglotContextImpl.State.EXITING;
            break;
         case CLOSED:
         case CLOSED_INTERRUPTED:
         case CLOSED_CANCELLED:
         case CLOSED_EXITED:
         case INTERRUPTING:
         case CLOSING:
         case CLOSING_FINALIZING:
         default:
            targetState = PolyglotContextImpl.State.DEFAULT;
            break;
         case CLOSING_INTERRUPTING:
         case CLOSING_INTERRUPTING_FINALIZING:
            targetState = PolyglotContextImpl.State.INTERRUPTING;
            break;
         case CLOSING_PENDING_EXIT:
            targetState = PolyglotContextImpl.State.PENDING_EXIT;
      }

      this.setState(targetState);
   }

   @SuppressFBWarnings("UL_UNRELEASED_LOCK_EXCEPTION_PATH")
   boolean closeImpl(boolean notifyInstruments) {
      boolean waitForClose = false;
      boolean finishCancelOrExit = false;

      while (true) {
         if (waitForClose) {
            this.closingLock.lock();
            this.closingLock.unlock();
            waitForClose = false;
         }

         boolean cancelOrExitOperation;
         synchronized (this) {
            switch (this.state) {
               case CANCELLING:
               case EXITING:
                  assert this.cachedThreadInfo == PolyglotThreadInfo.NULL;

                  if (!finishCancelOrExit) {
                     this.waitForThreads(0L, 0L);
                     waitForClose = true;
                     finishCancelOrExit = true;
                     continue;
                  }

                  this.setClosingState();
                  cancelOrExitOperation = true;
                  break;
               case CLOSING_CANCELLING:
               case CLOSING_EXITING:
               case CLOSING_INTERRUPTING:
               case CLOSING_INTERRUPTING_FINALIZING:
               case CLOSING:
               case CLOSING_FINALIZING:
               case CLOSING_PENDING_EXIT:
                  assert this.closingThread != null;

                  if (this.closingThread == Thread.currentThread()) {
                     return true;
                  }

                  waitForClose = true;
                  continue;
               case CLOSED:
               case CLOSED_INTERRUPTED:
               case CLOSED_CANCELLED:
               case CLOSED_EXITED:
                  return true;
               case INTERRUPTING:
               case DEFAULT:
                  if (this.hasActiveOtherThread(false)) {
                     return false;
                  }

                  this.setClosingState();
                  cancelOrExitOperation = false;
                  break;
               case PENDING_EXIT:
                  this.waitUntilInvalid();
                  continue;
               default:
                  assert false : this.state.name();
                  continue;
            }
         }

         return this.finishClose(cancelOrExitOperation, notifyInstruments);
      }
   }

   private void waitUntilInvalid() {
      while (!this.state.isInvalidOrClosed()) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }
   }

   synchronized void clearExplicitContextStack() {
      if (this.parent == null) {
         this.engine.polyglotHostService.notifyClearExplicitContextStack(this);
      }

      if (this.isActive(Thread.currentThread()) && !isCurrentEngineHostCallback(this.engine)) {
         PolyglotThreadInfo threadInfo = this.getCurrentThreadInfo();
         if (!threadInfo.explicitContextStack.isEmpty()) {
            PolyglotContextImpl c = this;

            while (!threadInfo.explicitContextStack.isEmpty()) {
               if (PolyglotFastThreadLocals.getContext(null) != this) {
                  throw PolyglotEngineException.illegalState(
                     "Unable to automatically leave an explicitly entered context, some other context was entered in the meantime."
                  );
               }

               Object[] prev = threadInfo.explicitContextStack.removeLast();
               this.engine.leave(prev, c);
               c = prev != null ? (PolyglotContextImpl)prev[1] : null;
            }
         }
      }
   }

   private boolean finishClose(boolean cancelOrExitOperation, boolean notifyInstruments) {
      Thread[] remainingThreads = null;
      List<PolyglotLanguageContext> disposedContexts = null;
      boolean success = false;

      try {
         assert this.closingThread == Thread.currentThread();

         assert this.closingLock.isHeldByCurrentThread() : "lock is acquired";

         assert !this.state.isClosed();

         Object[] prev;
         try {
            prev = this.enterThreadChanged(false, false, !cancelOrExitOperation, cancelOrExitOperation, false);
         } catch (Throwable var74) {
            synchronized (this) {
               this.restoreFromClosingState(cancelOrExitOperation);
            }

            throw var74;
         }

         if (cancelOrExitOperation) {
            synchronized (this) {
               this.threadLocalActions.submit(new Thread[]{Thread.currentThread()}, "engine", new PolyglotContextImpl.CancellationThreadLocalAction(), true);
            }
         }

         try {
            if (cancelOrExitOperation) {
               this.closeChildContexts(notifyInstruments);
            } else {
               this.exitContextNotification(TruffleLanguage.ExitMode.NATURAL, 0);
            }

            synchronized (this) {
               assert this.state != PolyglotContextImpl.State.CLOSING_FINALIZING && this.state != PolyglotContextImpl.State.CLOSING_INTERRUPTING_FINALIZING;

               this.setCachedThreadInfo(PolyglotThreadInfo.NULL);
               this.setFinalizingState();
               if (this.state == PolyglotContextImpl.State.CLOSING_PENDING_EXIT) {
                  this.waitUntilInvalid();
               }
            }

            this.finalizeContext(notifyInstruments, cancelOrExitOperation);
            List<PolyglotContextImpl> unclosedChildContexts;
            synchronized (this) {
               unclosedChildContexts = this.getUnclosedChildContexts();
            }

            for (PolyglotContextImpl childCtx : unclosedChildContexts) {
               if (childCtx.isActive()) {
                  throw new IllegalStateException("There is an active child contexts after finalizeContext!");
               }
            }

            if (!unclosedChildContexts.isEmpty()) {
               this.closeChildContexts(notifyInstruments);
            }

            disposedContexts = this.disposeContext();
            success = true;
         } finally {
            synchronized (this) {
               assert !success || this.getUnclosedChildContexts().isEmpty() : "Polyglot context close marked as successful, but there are unclosed child contexts.";

               this.leaveThreadChanged(prev, false, true, false);
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
      } finally {
         synchronized (this) {
            assert !this.state.isClosing();

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
         } finally {
            if (this.parent != null) {
               synchronized (this.parent) {
                  this.parent.childContexts.remove(this);
               }
            } else if (notifyInstruments) {
               this.engine.disposeContext(this);
            }
         }

         synchronized (this) {
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
      assert Thread.holdsLock(this);

      List<PolyglotContextImpl> unclosedChildContexts = new ArrayList<>();

      for (PolyglotContextImpl childCtx : this.childContexts) {
         if (!childCtx.state.isClosed()) {
            unclosedChildContexts.add(childCtx);
         }
      }

      return unclosedChildContexts;
   }

   private void closeChildContexts(boolean notifyInstruments) {
      PolyglotContextImpl[] childrenToClose;
      synchronized (this) {
         childrenToClose = this.childContexts.toArray(new PolyglotContextImpl[this.childContexts.size()]);
      }

      for (PolyglotContextImpl childContext : childrenToClose) {
         childContext.closeImpl(notifyInstruments);
      }
   }

   private boolean setPendingExit(int code) {
      synchronized (this) {
         PolyglotContextImpl.State targetState;
         switch (this.state) {
            case INTERRUPTING:
            case DEFAULT:
               targetState = PolyglotContextImpl.State.PENDING_EXIT;
               break;
            case CLOSING_INTERRUPTING:
            case CLOSING:
               targetState = PolyglotContextImpl.State.CLOSING_PENDING_EXIT;
               break;
            case CLOSING_INTERRUPTING_FINALIZING:
            case CLOSING_FINALIZING:
            case CLOSING_PENDING_EXIT:
            default:
               return false;
         }

         this.exitCode = code;
         this.exitMessage = "Exit was called with exit code " + code + ".";
         this.closeExitedTriggerThread = Thread.currentThread();
         this.setState(targetState);
         return true;
      }
   }

   void closeExited(Node exitLocation, int code) {
      if (this.setPendingExit(code)) {
         this.exitContextNotification(TruffleLanguage.ExitMode.HARD, code);
         if (this.parent == null) {
            this.engine.polyglotHostService.notifyContextCancellingOrExiting(this, true, code, false, this.exitMessage);
         }

         List<Future<Void>> futures = this.setExiting(null, code, this.exitMessage, false);
         if (!futures.isEmpty()) {
            this.closeHereOrCancelInCleanupThread(futures);
         }
      } else {
         synchronized (this) {
            if (!this.state.isInvalidOrClosed()) {
               PolyglotThreadInfo info = this.getCurrentThreadInfo();
               if (this.closeExitedTriggerThread == info.getThread()
                  || info.isPolyglotThread(this) && ((PolyglotThread)info.getThread()).hardExitNotificationThread) {
                  throw this.createExitException(exitLocation);
               }
            }
         }
      }

      PolyglotContextImpl.State localState = this.state;
      Node location = exitLocation != null ? exitLocation : this.uncachedLocation;
      if (localState == PolyglotContextImpl.State.PENDING_EXIT
         || localState == PolyglotContextImpl.State.CLOSING_PENDING_EXIT
         || localState.isInvalidOrClosed()) {
         TruffleSafepoint.setBlockedThreadInterruptible(location, new TruffleSafepoint.Interruptible<PolyglotContextImpl>() {
            public void apply(PolyglotContextImpl ctx) throws InterruptedException {
               synchronized (ctx) {
                  while (!ctx.state.isInvalidOrClosed()) {
                     ctx.wait();
                  }
               }
            }
         }, this);
         localState = this.state;
         if (this.config.useSystemExit && (localState.isExiting() || localState == PolyglotContextImpl.State.CLOSED_EXITED)) {
            this.engine.host.hostExit(this.exitCode);
         }

         TruffleSafepoint.pollHere(location);
      }
   }

   private void closeHereOrCancelInCleanupThread(List<Future<Void>> futures) {
      boolean cancelInSeparateThread = false;
      synchronized (this) {
         PolyglotThreadInfo info = this.getCurrentThreadInfo();
         Thread currentThread = Thread.currentThread();
         if (info.isPolyglotThread(this)
            || !this.singleThreaded && this.isActive(currentThread)
            || this.closingThread == currentThread
            || currentThread instanceof SystemThread) {
            cancelInSeparateThread = true;
         }
      }

      if (cancelInSeparateThread) {
         if (!futures.isEmpty()) {
            this.registerCleanupTask(new Runnable() {
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

   private void registerCleanupTask(Runnable cleanupTask) {
      synchronized (this) {
         if (!this.state.isClosed()) {
            if (this.cleanupExecutorService == null) {
               this.cleanupExecutorService = Executors.newFixedThreadPool(1, new ThreadFactory() {
                  @Override
                  public Thread newThread(Runnable r) {
                     Thread t = new Thread(r);
                     t.setDaemon(true);
                     return t;
                  }
               });
            }

            assert this.cleanupFuture == null : "Multiple cleanup tasks are currently not supported!";

            this.cleanupFuture = this.cleanupExecutorService.submit(cleanupTask);
         }
      }
   }

   void finishCleanup() {
      ExecutorService localCleanupService;
      synchronized (this) {
         if (this.isActive(Thread.currentThread())) {
            return;
         }

         localCleanupService = this.cleanupExecutorService;
      }

      if (localCleanupService != null) {
         try {
            this.cleanupFuture.get();
         } catch (InterruptedException var13) {
            this.engine.getEngineLogger().log(Level.INFO, "Waiting for polyglot context cleanup was interrupted!", (Throwable)var13);
         } catch (ExecutionException var14) {
            assert !(var14.getCause() instanceof AbstractTruffleException);

            throw sneakyThrow(var14.getCause());
         } finally {
            localCleanupService.shutdownNow();

            while (true) {
               if (localCleanupService.isTerminated()) {
                  ;
               } else {
                  try {
                     if (!localCleanupService.awaitTermination(1L, TimeUnit.MINUTES)) {
                        throw new IllegalStateException("Context cleanup service timeout!");
                     }
                  } catch (InterruptedException var12) {
                     this.engine.getEngineLogger().log(Level.INFO, "Waiting for polyglot context cleanup was interrupted!", (Throwable)var12);
                  }
               }
            }
         }
      }
   }

   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable ex) throws T {
      throw ex;
   }

   private List<PolyglotLanguageContext> disposeContext() {
      assert !this.disposing;

      this.disposing = true;
      List<PolyglotLanguageContext> disposedContexts = new ArrayList<>(this.contexts.length);

      for (int i = this.contexts.length - 1; i >= 0; i--) {
         PolyglotLanguageContext context = this.contexts[i];
         boolean disposed = context.dispose();
         if (disposed) {
            disposedContexts.add(context);
         }
      }

      Closeable[] toClose;
      synchronized (this) {
         toClose = this.closeables == null ? null : this.closeables.toArray(new Closeable[0]);
      }

      if (toClose != null) {
         for (Closeable closeable : toClose) {
            try {
               closeable.close();
            } catch (IOException var8) {
               this.engine.getEngineLogger().log(Level.WARNING, "Failed to close " + closeable, (Throwable)var8);
            }
         }
      }

      return disposedContexts;
   }

   private void exitContextNotification(TruffleLanguage.ExitMode exitMode, int code) {
      boolean exitNotificationPerformed;
      try {
         do {
            exitNotificationPerformed = false;

            for (int i = this.contexts.length - 1; i >= 0; i--) {
               PolyglotLanguageContext context = this.contexts[i];
               if (context.isInitialized()) {
                  exitNotificationPerformed |= context.exitContext(exitMode, code);
               }
            }
         } while (exitNotificationPerformed);
      } catch (Throwable var6) {
         if (exitMode == TruffleLanguage.ExitMode.NATURAL || !(var6 instanceof PolyglotEngineImpl.CancelExecution)) {
            throw var6;
         }

         this.engine.getEngineLogger().log(Level.FINE, "Execution was cancelled during exit notifications!", var6);
      }
   }

   private void finalizeContext(boolean notifyInstruments, boolean cancelOrExitOperation) {
      TruffleSafepoint safepoint = TruffleSafepoint.getCurrent();
      PolyglotThreadLocalActions.TL_HANDSHAKE.setChangeAllowActions(safepoint, true);

      boolean finalizationPerformed;
      try {
         do {
            finalizationPerformed = false;

            for (int i = this.contexts.length - 1; i >= 0; i--) {
               PolyglotLanguageContext context = this.contexts[i];
               if (context.isInitialized()) {
                  try {
                     finalizationPerformed |= context.finalizeContext(cancelOrExitOperation, notifyInstruments);
                  } finally {
                     if (!PolyglotThreadLocalActions.TL_HANDSHAKE.isAllowActions(safepoint)) {
                        safepoint.setAllowActions(true);
                        throw new IllegalStateException(
                           "TruffleSafepoint.setAllowActions is still disabled even though finalization completed. Make sure allow actions are reset in a finally block."
                        );
                     }
                  }
               }
            }
         } while (finalizationPerformed);
      } finally {
         PolyglotThreadLocalActions.TL_HANDSHAKE.setChangeAllowActions(safepoint, false);
      }
   }

   synchronized void maybeSendInterrupt() {
      if (this.state.isInterrupting() || this.state.isCancelling() || this.state.isExiting()) {
         for (PolyglotThreadInfo threadInfo : this.threads.values()) {
            if (!threadInfo.isCurrent() && threadInfo.isActiveNotCancelled()) {
               threadInfo.getThread().interrupt();
            }
         }
      }
   }

   Object getLocal(PolyglotLocals.LocalLocation l) {
      assert l.engine == this.engine : invalidSharingError(this.engine, l.engine);

      return l.readLocal(this, this.contextLocals, false);
   }

   private Object[] getThreadLocals(Thread thread) {
      assert Thread.holdsLock(this);

      PolyglotThreadInfo threadInfo = this.threads.get(thread);
      return threadInfo == null ? null : threadInfo.getContextThreadLocals();
   }

   @CompilerDirectives.TruffleBoundary
   synchronized Object getThreadLocal(PolyglotLocals.LocalLocation l, Thread t) {
      assert l.engine == this.engine : invalidSharingError(this.engine, l.engine);

      Object[] threadLocals = this.getThreadLocals(t);
      return threadLocals == null ? null : l.readLocal(this, threadLocals, true);
   }

   void initializeThreadLocals(PolyglotThreadInfo threadInfo) {
      assert Thread.holdsLock(this);

      assert Thread.currentThread() == threadInfo.getThread() : "thread locals must only be initialized on the current thread";

      PolyglotEngineImpl.StableLocalLocations locations = this.engine.contextThreadLocalLocations;
      Object[] locals = new Object[locations.locations.length];
      Thread thread = threadInfo.getThread();

      for (PolyglotInstrument instrument : this.engine.idToInstrument.values()) {
         if (instrument.isCreated()) {
            this.invokeContextLocalsFactory(this.contextLocals, instrument.contextLocalLocations);
            this.invokeContextThreadFactory(locals, instrument.contextThreadLocalLocations, thread);
         }
      }

      for (PolyglotLanguageContext language : this.contexts) {
         if (language.isCreated()) {
            this.invokeContextLocalsFactory(this.contextLocals, language.getLanguageInstance().contextLocalLocations);
            this.invokeContextThreadFactory(locals, language.getLanguageInstance().contextThreadLocalLocations, thread);
         }
      }

      threadInfo.setContextThreadLocals(locals);
   }

   void initializeContextLocals() {
      assert Thread.holdsLock(this);

      if (this.contextLocals == null) {
         PolyglotEngineImpl.StableLocalLocations locations = this.engine.contextLocalLocations;
         Object[] locals = new Object[locations.locations.length];
         this.initializeInstrumentContextLocals(locals);

         assert this.contextLocals == null;

         this.contextLocals = locals;
      }
   }

   void initializeInstrumentContextLocals(Object[] locals) {
      for (PolyglotInstrument instrument : this.engine.idToInstrument.values()) {
         if (instrument.isCreated()) {
            this.invokeContextLocalsFactory(locals, instrument.contextLocalLocations);
         }
      }
   }

   void initializeInstrumentContextThreadLocals() {
      for (PolyglotInstrument instrument : this.engine.idToInstrument.values()) {
         if (instrument.isCreated()) {
            this.invokeContextThreadLocalFactory(instrument.contextThreadLocalLocations);
         }
      }
   }

   void invokeLocalsFactories(PolyglotLocals.LocalLocation[] contextLocalLocations, PolyglotLocals.LocalLocation[] contextThreadLocalLocations) {
      PolyglotContextImpl[] localChildContexts;
      synchronized (this) {
         if (this.localsCleared) {
            return;
         }

         if (this.contextLocals != null) {
            this.invokeContextLocalsFactory(this.contextLocals, contextLocalLocations);
            this.invokeContextThreadLocalFactory(contextThreadLocalLocations);
         }

         localChildContexts = this.childContexts.toArray(new PolyglotContextImpl[0]);
      }

      for (PolyglotContextImpl childCtx : localChildContexts) {
         childCtx.invokeLocalsFactories(contextLocalLocations, contextThreadLocalLocations);
      }
   }

   void resizeThreadLocals(PolyglotEngineImpl.StableLocalLocations locations) {
      PolyglotContextImpl[] localChildContexts;
      synchronized (this) {
         if (this.localsCleared) {
            return;
         }

         this.resizeContextThreadLocals(locations);
         localChildContexts = this.childContexts.toArray(new PolyglotContextImpl[0]);
      }

      for (PolyglotContextImpl childCtx : localChildContexts) {
         childCtx.resizeThreadLocals(locations);
      }
   }

   void resizeContextThreadLocals(PolyglotEngineImpl.StableLocalLocations locations) {
      assert Thread.holdsLock(this);

      for (PolyglotThreadInfo threadInfo : this.threads.values()) {
         Object[] threadLocals = threadInfo.getContextThreadLocals();
         if (threadLocals.length < locations.locations.length) {
            threadInfo.setContextThreadLocals(Arrays.copyOf(threadLocals, locations.locations.length));
         }
      }
   }

   void resizeLocals(PolyglotEngineImpl.StableLocalLocations locations) {
      PolyglotContextImpl[] localChildContexts;
      synchronized (this) {
         if (this.localsCleared) {
            return;
         }

         this.resizeContextLocals(locations);
         localChildContexts = this.childContexts.toArray(new PolyglotContextImpl[0]);
      }

      for (PolyglotContextImpl childCtx : localChildContexts) {
         childCtx.resizeLocals(locations);
      }
   }

   void resizeContextLocals(PolyglotEngineImpl.StableLocalLocations locations) {
      assert Thread.holdsLock(this);

      Object[] oldLocals = this.contextLocals;
      if (oldLocals != null) {
         if (oldLocals.length > locations.locations.length) {
            throw new AssertionError("Context locals array must never shrink.");
         }

         if (locations.locations.length > oldLocals.length) {
            this.contextLocals = Arrays.copyOf(oldLocals, locations.locations.length);
         }
      } else {
         this.contextLocals = new Object[locations.locations.length];
      }
   }

   void invokeContextLocalsFactory(Object[] locals, PolyglotLocals.LocalLocation[] locations) {
      assert Thread.holdsLock(this);

      if (locations != null) {
         try {
            for (int i = 0; i < locations.length; i++) {
               PolyglotLocals.LocalLocation location = locations[i];
               if (locals[location.index] == null) {
                  locals[location.index] = location.invokeFactory(this, null);
               }
            }
         } catch (Throwable var5) {
            for (int ix = 0; ix < locations.length; ix++) {
               locals[locations[ix].index] = null;
            }

            throw var5;
         }
      }
   }

   void invokeContextThreadLocalFactory(PolyglotLocals.LocalLocation[] locations) {
      assert Thread.holdsLock(this);

      if (locations != null) {
         for (PolyglotThreadInfo threadInfo : this.threads.values()) {
            this.invokeContextThreadFactory(threadInfo.getContextThreadLocals(), locations, threadInfo.getThread());
         }
      }
   }

   private void invokeContextThreadFactory(Object[] threadLocals, PolyglotLocals.LocalLocation[] locations, Thread thread) {
      assert Thread.holdsLock(this);

      if (locations != null) {
         try {
            for (int i = 0; i < locations.length; i++) {
               PolyglotLocals.LocalLocation location = locations[i];
               if (threadLocals[location.index] == null) {
                  threadLocals[location.index] = location.invokeFactory(this, thread);
               }
            }
         } catch (Throwable var6) {
            for (int ix = 0; ix < locations.length; ix++) {
               threadLocals[locations[ix].index] = null;
            }

            throw var6;
         }
      }
   }

   static String invalidSharingError(PolyglotEngineImpl expectedEngine, PolyglotEngineImpl actualEngine) {
      return String.format(
         "Detected invaliding sharing of context locals between polyglot engines. Expected engine %s but was %s.", expectedEngine, actualEngine
      );
   }

   boolean patch(PolyglotContextConfig newConfig) {
      CompilerAsserts.neverPartOfCompilation();
      this.config = newConfig;
      this.threadLocalActions.onContextPatch();
      if (!newConfig.logLevels.isEmpty()) {
         EngineAccessor.LANGUAGE.configureLoggers(this, newConfig.logLevels, this.getAllLoggers());
      }

      Object[] prev = this.engine.enter(this);

      try {
         for (int i = 0; i < this.contexts.length; i++) {
            PolyglotLanguageContext context = this.contexts[i];
            if (context.language.isHost()) {
               this.initializeHostContext(context, newConfig);
            }

            if (!context.patch(newConfig)) {
               return false;
            }
         }

         return true;
      } finally {
         this.engine.leave(prev, this);
      }
   }

   void initializeHostContext(PolyglotLanguageContext context, PolyglotContextConfig newConfig) {
      Object contextImpl = context.getContextImpl();
      if (contextImpl == null) {
         throw new AssertionError("Host context not initialized.");
      } else {
         this.hostContextImpl = contextImpl;
         AbstractPolyglotImpl.AbstractHostLanguageService currentHost = this.engine.host;
         AbstractPolyglotImpl.AbstractHostLanguageService newHost = context.lookupService(AbstractPolyglotImpl.AbstractHostLanguageService.class);
         if (newHost == null) {
            throw new AssertionError("The engine host language must register a service of type:" + AbstractPolyglotImpl.AbstractHostLanguageService.class);
         } else {
            if (currentHost == null) {
               this.engine.host = newHost;
            } else if (currentHost != newHost) {
               throw new AssertionError("Host service must not change per engine.");
            }

            newHost.initializeHostContext(
               this,
               contextImpl,
               newConfig.hostAccess,
               newConfig.hostClassLoader,
               newConfig.classFilter,
               newConfig.hostClassLoadingAllowed,
               newConfig.hostLookupAllowed
            );
         }
      }
   }

   void replayInstrumentationEvents() {
      this.notifyContextCreated();
      EngineAccessor.INSTRUMENT.notifyThreadStarted(this.engine, this.creatorTruffleContext, Thread.currentThread());

      for (PolyglotLanguageContext lc : this.contexts) {
         LanguageInfo language = lc.language.info;
         if (lc.eventsEnabled && lc.env != null) {
            EngineAccessor.INSTRUMENT.notifyLanguageContextCreate(this, this.creatorTruffleContext, language);
            EngineAccessor.INSTRUMENT.notifyLanguageContextCreated(this, this.creatorTruffleContext, language);
            if (lc.isInitialized()) {
               EngineAccessor.INSTRUMENT.notifyLanguageContextInitialize(this, this.creatorTruffleContext, language);
               EngineAccessor.INSTRUMENT.notifyLanguageContextInitialized(this, this.creatorTruffleContext, language);
               if (lc.finalized) {
                  EngineAccessor.INSTRUMENT.notifyLanguageContextFinalized(this, this.creatorTruffleContext, language);
               }
            }
         }
      }
   }

   private synchronized void checkSubProcessFinished() {
      ProcessHandlers.ProcessDecorator[] processes = this.subProcesses.toArray(new ProcessHandlers.ProcessDecorator[this.subProcesses.size()]);

      for (ProcessHandlers.ProcessDecorator process : processes) {
         if (process.isAlive()) {
            throw new IllegalStateException(
               String.format("The context has an alive sub-process %s created by %s.", process.getCommand(), process.getOwner().language.getId())
            );
         }
      }
   }

   private synchronized void checkSystemThreadsFinished() {
      if (!this.activeSystemThreads.isEmpty()) {
         SystemThread.LanguageSystemThread thread = this.activeSystemThreads.iterator().next();
         throw new IllegalStateException(
            String.format("The context has an alive system thread %s created by language %s.", thread.getName(), thread.languageId)
         );
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static PolyglotContextImpl preinitialize(
      final PolyglotEngineImpl engine,
      final PolyglotContextConfig.PreinitConfig preinitConfig,
      PolyglotSharingLayer sharableLayer,
      Set<PolyglotLanguage> languagesToPreinitialize,
      boolean emitWarning
   ) {
      FileSystems.PreInitializeContextFileSystem fs = new FileSystems.PreInitializeContextFileSystem();
      FileSystems.PreInitializeContextFileSystem internalFs = new FileSystems.PreInitializeContextFileSystem();
      PolyglotContextConfig config = new PolyglotContextConfig(engine, fs, internalFs, preinitConfig);
      PolyglotContextImpl context = new PolyglotContextImpl(engine, config);
      synchronized (engine.lock) {
         engine.addContext(context);
      }

      context.inContextPreInitialization = true;
      context.sourcesToInvalidate = new ArrayList<>();
      boolean var31 = false /* VF: Semaphore variable */;

      PolyglotContextImpl var48;
      label398: {
         try {
            var31 = true;
            if (sharableLayer != null && !context.claimSharingLayer(sharableLayer, languagesToPreinitialize)) {
               var48 = null;
               var31 = false;
               break label398;
            }

            synchronized (context) {
               context.initializeContextLocals();
            }

            if (!languagesToPreinitialize.isEmpty()) {
               Object[] prev = context.engine.enter(context);

               try {
                  for (PolyglotLanguage language : languagesToPreinitialize) {
                     assert language.engine == engine : "invalid language";

                     if (overridesPatchContext(language.getId())) {
                        context.getContextInitialized(language, null);
                        LOG.log(Level.FINE, "Pre-initialized context for language: {0}", language.getId());
                     } else if (emitWarning) {
                        LOG.log(
                           Level.WARNING,
                           "Language {0} cannot be pre-initialized as it does not override TruffleLanguage.patchContext method.",
                           language.getId()
                        );
                     }
                  }
               } finally {
                  context.leaveThreadChanged(prev, true, true, true);
               }
            }

            var48 = context;
            var31 = false;
         } finally {
            if (var31) {
               context.inContextPreInitialization = false;

               for (PolyglotLanguage language : engine.languages) {
                  if (language != null) {
                     language.clearOptionValues();
                  }
               }

               synchronized (engine.lock) {
                  engine.removeContext(context);
               }

               for (Source sourceToInvalidate : context.sourcesToInvalidate) {
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

         context.inContextPreInitialization = false;

         for (PolyglotLanguage languagex : engine.languages) {
            if (languagex != null) {
               languagex.clearOptionValues();
            }
         }

         synchronized (engine.lock) {
            engine.removeContext(context);
         }

         for (Source sourceToInvalidate : context.sourcesToInvalidate) {
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

         return var48;
      }

      context.inContextPreInitialization = false;

      for (PolyglotLanguage languagexx : engine.languages) {
         if (languagexx != null) {
            languagexx.clearOptionValues();
         }
      }

      synchronized (engine.lock) {
         engine.removeContext(context);
      }

      for (Source sourceToInvalidate : context.sourcesToInvalidate) {
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

      return var48;
   }

   Object getOrCreateContextLoggers() {
      Object res = this.contextBoundLoggers;
      if (res == null) {
         synchronized (this) {
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
      List<Object> allLoggers = new ArrayList<>(3);
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

   private PolyglotContextImpl.ExitException createExitException(Node location) {
      return new PolyglotContextImpl.ExitException(location, this.exitCode, this.exitMessage);
   }

   private static boolean overridesPatchContext(String languageId) {
      if (TruffleOptions.AOT) {
         return LanguageCache.overridesPathContext(languageId);
      } else {
         LanguageCache cache = LanguageCache.languages().get(languageId);

         for (Method m : cache.loadLanguage().getClass().getDeclaredMethods()) {
            if (m.getName().equals("patchContext")) {
               return true;
            }
         }

         return false;
      }
   }

   synchronized void registerOnDispose(Closeable closeable) {
      if (this.disposing) {
         throw new IllegalStateException("Cannot register closeable when context is being disposed.");
      } else {
         if (this.closeables == null) {
            this.closeables = Collections.newSetFromMap(new WeakHashMap<>());
         }

         this.closeables.add(Objects.requireNonNull(closeable));
      }
   }

   @Override
   public String toString() {
      StringBuilder b = new StringBuilder();
      b.append("PolyglotContextImpl[");
      b.append("state=");
      PolyglotContextImpl.State localState = this.state;
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
         if (languageContext.isInitialized() || languageContext.isCreated()) {
            b.append(sep);
            b.append(languageContext.language.getId());
            sep = ", ";
         }
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
      VALID_TRANSITIONS.put(
         PolyglotContextImpl.State.DEFAULT,
         new PolyglotContextImpl.State[]{
            PolyglotContextImpl.State.CLOSING,
            PolyglotContextImpl.State.INTERRUPTING,
            PolyglotContextImpl.State.PENDING_EXIT,
            PolyglotContextImpl.State.CANCELLING,
            PolyglotContextImpl.State.EXITING
         }
      );
      VALID_TRANSITIONS.put(
         PolyglotContextImpl.State.CLOSING,
         new PolyglotContextImpl.State[]{
            PolyglotContextImpl.State.CLOSING_FINALIZING,
            PolyglotContextImpl.State.CLOSING_INTERRUPTING,
            PolyglotContextImpl.State.CLOSING_CANCELLING,
            PolyglotContextImpl.State.CLOSING_PENDING_EXIT,
            PolyglotContextImpl.State.CLOSING_EXITING,
            PolyglotContextImpl.State.DEFAULT
         }
      );
      VALID_TRANSITIONS.put(
         PolyglotContextImpl.State.CLOSING_FINALIZING,
         new PolyglotContextImpl.State[]{
            PolyglotContextImpl.State.CLOSED,
            PolyglotContextImpl.State.CLOSING_INTERRUPTING_FINALIZING,
            PolyglotContextImpl.State.CLOSING_CANCELLING,
            PolyglotContextImpl.State.CLOSING_EXITING,
            PolyglotContextImpl.State.DEFAULT
         }
      );
      VALID_TRANSITIONS.put(
         PolyglotContextImpl.State.INTERRUPTING,
         new PolyglotContextImpl.State[]{
            PolyglotContextImpl.State.DEFAULT,
            PolyglotContextImpl.State.CLOSING_INTERRUPTING,
            PolyglotContextImpl.State.CANCELLING,
            PolyglotContextImpl.State.PENDING_EXIT,
            PolyglotContextImpl.State.EXITING
         }
      );
      VALID_TRANSITIONS.put(
         PolyglotContextImpl.State.PENDING_EXIT, new PolyglotContextImpl.State[]{PolyglotContextImpl.State.EXITING, PolyglotContextImpl.State.CANCELLING}
      );
      VALID_TRANSITIONS.put(PolyglotContextImpl.State.CANCELLING, new PolyglotContextImpl.State[]{PolyglotContextImpl.State.CLOSING_CANCELLING});
      VALID_TRANSITIONS.put(
         PolyglotContextImpl.State.CLOSING_INTERRUPTING,
         new PolyglotContextImpl.State[]{
            PolyglotContextImpl.State.CLOSING_INTERRUPTING_FINALIZING,
            PolyglotContextImpl.State.CLOSING,
            PolyglotContextImpl.State.CLOSING_PENDING_EXIT,
            PolyglotContextImpl.State.CLOSING_CANCELLING,
            PolyglotContextImpl.State.CLOSING_EXITING,
            PolyglotContextImpl.State.INTERRUPTING
         }
      );
      VALID_TRANSITIONS.put(
         PolyglotContextImpl.State.CLOSING_INTERRUPTING_FINALIZING,
         new PolyglotContextImpl.State[]{
            PolyglotContextImpl.State.CLOSED_INTERRUPTED,
            PolyglotContextImpl.State.CLOSING_FINALIZING,
            PolyglotContextImpl.State.CLOSING_CANCELLING,
            PolyglotContextImpl.State.CLOSING_EXITING,
            PolyglotContextImpl.State.INTERRUPTING
         }
      );
      VALID_TRANSITIONS.put(
         PolyglotContextImpl.State.CLOSING_CANCELLING,
         new PolyglotContextImpl.State[]{PolyglotContextImpl.State.CLOSED_CANCELLED, PolyglotContextImpl.State.CANCELLING}
      );
      VALID_TRANSITIONS.put(
         PolyglotContextImpl.State.CLOSING_PENDING_EXIT,
         new PolyglotContextImpl.State[]{
            PolyglotContextImpl.State.CLOSING_EXITING, PolyglotContextImpl.State.CLOSING_CANCELLING, PolyglotContextImpl.State.PENDING_EXIT
         }
      );
      VALID_TRANSITIONS.put(
         PolyglotContextImpl.State.CLOSING_EXITING, new PolyglotContextImpl.State[]{PolyglotContextImpl.State.CLOSED_EXITED, PolyglotContextImpl.State.EXITING}
      );
      VALID_TRANSITIONS.put(PolyglotContextImpl.State.EXITING, new PolyglotContextImpl.State[]{PolyglotContextImpl.State.CLOSING_EXITING});
      VALID_TRANSITIONS.put(PolyglotContextImpl.State.CLOSED, new PolyglotContextImpl.State[0]);
      VALID_TRANSITIONS.put(PolyglotContextImpl.State.CLOSED_CANCELLED, new PolyglotContextImpl.State[0]);
      VALID_TRANSITIONS.put(PolyglotContextImpl.State.CLOSED_EXITED, new PolyglotContextImpl.State[0]);
   }

   private final class CancellationThreadLocalAction extends ThreadLocalAction {
      CancellationThreadLocalAction() {
         super(false, false);
      }

      @Override
      protected void perform(ThreadLocalAction.Access access) {
         PolyglotContextImpl.this.threadLocalActions
            .submit(new Thread[]{access.getThread()}, "engine", this, new PolyglotThreadLocalActions.HandshakeConfig(true, false, false, true));
         PolyglotContextImpl.State localState = PolyglotContextImpl.this.state;
         if (localState.isCancelling()
            || localState.isExiting()
            || localState == PolyglotContextImpl.State.CLOSED_CANCELLED
            || localState == PolyglotContextImpl.State.CLOSED_EXITED) {
            if (!localState.isExiting() && localState != PolyglotContextImpl.State.CLOSED_EXITED) {
               throw PolyglotContextImpl.this.createCancelException(access.getLocation());
            } else {
               throw PolyglotContextImpl.this.createExitException(access.getLocation());
            }
         }
      }
   }

   static class ContextWeakReference extends WeakReference<PolyglotContextImpl> {
      volatile boolean removed = false;
      volatile PolyglotSharingLayer layer;

      ContextWeakReference(PolyglotContextImpl referent) {
         super(referent, referent.engine.contextsReferenceQueue);
      }

      void freeSharing(PolyglotContextImpl context) {
         assert context == null || this.layer == null || this.layer.equals(context.layer);

         if (this.layer != null && this.layer.isClaimed()) {
            this.layer.engine.freeSharingLayer(this.layer, context);
         }
      }
   }

   static final class ExitException extends ThreadDeath {
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
         } else {
            return this.location == null ? null : this.location.getEncapsulatingSourceSection();
         }
      }

      @Override
      public String getMessage() {
         return this.exitMessage;
      }

      int getExitCode() {
         return this.exitCode;
      }
   }

   private final class InterruptThreadLocalAction extends ThreadLocalAction {
      InterruptThreadLocalAction() {
         super(true, false);
      }

      @Override
      protected void perform(ThreadLocalAction.Access access) {
         PolyglotContextImpl.this.threadLocalActions.submit(new Thread[]{access.getThread()}, "engine", this, true);
         PolyglotContextImpl.State localState = PolyglotContextImpl.this.state;
         if (access.getThread() != PolyglotContextImpl.this.closingThread
            && (localState.isInterrupting() || localState == PolyglotContextImpl.State.CLOSED_INTERRUPTED)) {
            PolyglotContextImpl[] interruptingChildContexts;
            synchronized (PolyglotContextImpl.this) {
               interruptingChildContexts = PolyglotContextImpl.this.childContexts.toArray(new PolyglotContextImpl[0]);
            }

            for (PolyglotContextImpl childCtx : interruptingChildContexts) {
               if (access.getThread() == childCtx.closingThread) {
                  return;
               }
            }

            throw new PolyglotEngineImpl.InterruptExecution(access.getLocation());
         }
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
            case CLOSED_EXITED:
               return true;
            default:
               return false;
         }
      }

      boolean isInterrupting() {
         switch (this) {
            case INTERRUPTING:
            case CLOSING_INTERRUPTING:
            case CLOSING_INTERRUPTING_FINALIZING:
               return true;
            default:
               return false;
         }
      }

      boolean isCancelling() {
         switch (this) {
            case CANCELLING:
            case CLOSING_CANCELLING:
               return true;
            default:
               return false;
         }
      }

      boolean isExiting() {
         switch (this) {
            case EXITING:
            case CLOSING_EXITING:
               return true;
            default:
               return false;
         }
      }

      boolean isClosing() {
         switch (this) {
            case CLOSING_CANCELLING:
            case CLOSING_EXITING:
            case CLOSING_INTERRUPTING:
            case CLOSING_INTERRUPTING_FINALIZING:
            case CLOSING:
            case CLOSING_FINALIZING:
            case CLOSING_PENDING_EXIT:
               return true;
            case CLOSED:
            case CLOSED_INTERRUPTED:
            case CLOSED_CANCELLED:
            case CLOSED_EXITED:
            case INTERRUPTING:
            default:
               return false;
         }
      }

      boolean isClosed() {
         switch (this) {
            case CLOSED:
            case CLOSED_INTERRUPTED:
            case CLOSED_CANCELLED:
            case CLOSED_EXITED:
               return true;
            default:
               return false;
         }
      }

      private boolean shouldCacheThreadInfo() {
         switch (this) {
            case CLOSING:
            case CLOSING_PENDING_EXIT:
            case DEFAULT:
            case PENDING_EXIT:
               return true;
            case CLOSING_FINALIZING:
            default:
               return false;
         }
      }
   }

   private static final class UncachedLocationNode extends HostToGuestRootNode {
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
}
