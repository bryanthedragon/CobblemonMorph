package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.frame.FrameInstanceVisitor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.EventBinding;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ExecutionEventNode;
import com.oracle.truffle.api.instrumentation.ExecutionEventNodeFactory;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.SourceSectionFilter;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.nodes.ExecutableNode;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import java.io.Closeable;
import java.net.URI;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class DebuggerSession implements Closeable {
   private static final AtomicInteger SESSIONS = new AtomicInteger(0);
   private static final ThreadLocal<Boolean> inEvalInContext = new ThreadLocal<>();
   static final Set<SuspendAnchor> ANCHOR_SET_BEFORE = Collections.singleton(SuspendAnchor.BEFORE);
   static final Set<SuspendAnchor> ANCHOR_SET_AFTER = Collections.singleton(SuspendAnchor.AFTER);
   static final Set<SuspendAnchor> ANCHOR_SET_ALL = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(SuspendAnchor.BEFORE, SuspendAnchor.AFTER)));
   private final Debugger debugger;
   private final SuspendedCallback callback;
   private final Set<SourceElement> sourceElements;
   private final boolean hasExpressionElement;
   private final boolean hasRootElement;
   private final List<Breakpoint> breakpoints = Collections.synchronizedList(new ArrayList<>());
   private final Collection<Breakpoint> breakpointsUnresolved = ConcurrentHashMap.newKeySet();
   private volatile boolean breakpointsUnresolvedEmpty = true;
   private EventBinding<? extends ExecutionEventNodeFactory> syntaxElementsBinding;
   final Set<EventBinding<? extends ExecutionEventNodeFactory>> allBindings = Collections.synchronizedSet(new HashSet<>());
   private final ConcurrentHashMap<Thread, SuspendedEvent> currentSuspendedEventMap = new ConcurrentHashMap<>();
   private final ConcurrentHashMap<Thread, SteppingStrategy> strategyMap = new ConcurrentHashMap<>();
   private volatile boolean suspendNext;
   private volatile boolean suspendAll;
   private final DebuggerSession.StableBoolean stepping = new DebuggerSession.StableBoolean(false);
   private final DebuggerSession.StableBoolean ignoreLanguageContextInitialization = new DebuggerSession.StableBoolean(false);
   private volatile boolean includeInternal = false;
   private volatile boolean showHostStackFrames = false;
   private Predicate<Source> sourceFilter;
   @CompilerDirectives.CompilationFinal
   private volatile Assumption suspensionFilterUnchanged = Truffle.getRuntime().createAssumption("Unchanged suspension filter");
   private final DebuggerSession.StableBoolean alwaysHaltBreakpointsActive = new DebuggerSession.StableBoolean(true);
   private final DebuggerSession.StableBoolean locationBreakpointsActive = new DebuggerSession.StableBoolean(true);
   private final DebuggerSession.StableBoolean exceptionBreakpointsActive = new DebuggerSession.StableBoolean(true);
   private final DebuggerExecutionLifecycle executionLifecycle;
   final ThreadLocal<DebuggerSession.ThreadSuspension> threadSuspensions = new ThreadLocal<>();
   private final DebugSourcesResolver sources;
   private final ThreadLocal<Set<Integer>> steppingEnabledSlots = new ThreadLocal<>();
   private final int sessionId = SESSIONS.incrementAndGet();
   private volatile boolean closed;

   DebuggerSession(Debugger debugger, SuspendedCallback callback, SourceElement... sourceElements) {
      this.debugger = debugger;
      this.callback = callback;
      switch (sourceElements.length) {
         case 0:
            this.sourceElements = Collections.emptySet();
            break;
         case 1:
            this.sourceElements = Collections.singleton(sourceElements[0]);
            break;
         default:
            this.sourceElements = Collections.unmodifiableSet(new LinkedHashSet<>(Arrays.asList(sourceElements)));
      }

      this.hasExpressionElement = this.sourceElements.contains(SourceElement.EXPRESSION);
      this.hasRootElement = this.sourceElements.contains(SourceElement.ROOT);
      if (Debugger.TRACE) {
         this.trace("open with callback %s", callback);
      }

      this.sources = new DebugSourcesResolver(debugger.getEnv());
      this.addBindings(this.includeInternal, this.sourceFilter);
      this.executionLifecycle = new DebuggerExecutionLifecycle(this);
   }

   private void trace(String msg, Object... parameters) {
      Debugger.trace(this + ": " + msg, parameters);
   }

   @Override
   public String toString() {
      return String.format("Session[id=%s]", this.sessionId);
   }

   public Debugger getDebugger() {
      return this.debugger;
   }

   public DebugScope getTopScope(String languageId) throws DebugException {
      LanguageInfo info = this.debugger.getEnv().getLanguages().get(languageId);
      if (info == null) {
         return null;
      } else {
         try {
            Object scope = this.debugger.getEnv().getScope(info);
            return scope == null ? null : new DebugScope(scope, this, info);
         } catch (ThreadDeath var4) {
            throw var4;
         } catch (Throwable var5) {
            throw DebugException.create(this, var5, info);
         }
      }
   }

   public Map<String, ? extends DebugValue> getExportedSymbols() {
      return new AbstractMap<String, DebugValue>() {
         private final DebugValue polyglotBindings = new DebugValue.HeapValue(
            DebuggerSession.this, "polyglot", DebuggerSession.this.debugger.getEnv().getPolyglotBindings()
         );

         @Override
         public Set<Entry<String, DebugValue>> entrySet() {
            Set<Entry<String, DebugValue>> entries = new LinkedHashSet<>();

            for (DebugValue property : this.polyglotBindings.getProperties()) {
               entries.add(new SimpleImmutableEntry<>(property.getName(), property));
            }

            return Collections.unmodifiableSet(entries);
         }

         public DebugValue get(Object key) {
            if (!(key instanceof String)) {
               return null;
            } else {
               String name = (String)key;
               return this.polyglotBindings.getProperty(name);
            }
         }
      };
   }

   public void setShowHostStackFrames(boolean showHostStackFrames) {
      this.showHostStackFrames = showHostStackFrames;
   }

   boolean isShowHostStackFrames() {
      return this.showHostStackFrames;
   }

   public void setSteppingFilter(SuspensionFilter steppingFilter) {
      this.ignoreLanguageContextInitialization.set(steppingFilter.isIgnoreLanguageContextInitialization());
      synchronized (this) {
         boolean oldIncludeInternal = this.includeInternal;
         this.includeInternal = steppingFilter.isInternalIncluded();
         Predicate<Source> oldSourceFilter = this.sourceFilter;
         this.sourceFilter = steppingFilter.getSourcePredicate();
         this.suspensionFilterUnchanged.invalidate();
         this.suspensionFilterUnchanged = Truffle.getRuntime().createAssumption("Unchanged suspension filter");
         if (oldIncludeInternal != this.includeInternal || oldSourceFilter != this.sourceFilter) {
            this.removeBindings();
            this.addBindings(this.includeInternal, this.sourceFilter);
         }
      }
   }

   boolean isIncludeInternal() {
      return this.includeInternal;
   }

   boolean isSourceFilteredOut(Source source) {
      Predicate<Source> filter = this.sourceFilter;
      return filter != null ? !filter.test(source) : false;
   }

   Assumption getSuspensionFilterUnchangedAssumption() {
      return this.suspensionFilterUnchanged;
   }

   public synchronized void suspendNextExecution() {
      if (Debugger.TRACE) {
         this.trace("suspend next execution");
      }

      if (this.closed) {
         throw new IllegalStateException("session closed");
      } else {
         this.suspendNext = true;
         this.updateStepping();
      }
   }

   public boolean suspendHere(Node node) {
      SuspendedEvent event = this.currentSuspendedEventMap.get(Thread.currentThread());
      if (event != null) {
         throw new IllegalStateException("Suspended already");
      } else {
         RootNode nodeRoot;
         if (node != null) {
            nodeRoot = node.getRootNode();
            if (nodeRoot == null) {
               throw new IllegalArgumentException(String.format("The node %s does not have a root.", node));
            }
         } else {
            nodeRoot = null;
         }

         DebuggerSession.SuspendContextAndFrame result = Truffle.getRuntime()
            .iterateFrames(
               frameInstance -> {
                  RootNode root = ((RootCallTarget)frameInstance.getCallTarget()).getRootNode();
                  if (!this.includeInternal && root.isInternal()) {
                     return null;
                  } else if (nodeRoot != null && nodeRoot != root) {
                     throw new IllegalArgumentException(
                        String.format("The node %s belongs to a root %s, which is different from the current root %s.", node, nodeRoot, root)
                     );
                  } else {
                     Node callNode = frameInstance.getCallNode();
                     if (callNode == null) {
                        callNode = node;
                        if (node == null) {
                           callNode = root;
                        }
                     }

                     if (node != null && node != callNode) {
                        throw new IllegalArgumentException(String.format("The node %s does not match the current known call node %s.", node, callNode));
                     } else {
                        Node icallNode = InstrumentableNode.findInstrumentableParent(callNode);
                        if (icallNode != null) {
                           callNode = icallNode;
                        }

                        MaterializedFrame frame = frameInstance.getFrame(FrameInstance.FrameAccess.MATERIALIZE).materialize();
                        SuspendedContext context = SuspendedContext.create(callNode, null);
                        return new DebuggerSession.SuspendContextAndFrame(context, frame);
                     }
                  }
               }
            );
         if (result == null) {
            return false;
         } else {
            this.doSuspend(result.context, SuspendAnchor.BEFORE, result.frame, null);
            return true;
         }
      }
   }

   void restoreSteppingOnCurrentThread() {
      CompilerAsserts.neverPartOfCompilation();

      assert this.debugger.getEnv().getEnteredContext() != null : "Need to be called on a context thread";

      int count = this.debugger.getSteppingDisabledCount();
      if (count != 0) {
         Set<Integer> enabledSlots = this.steppingEnabledSlots.get();
         if (enabledSlots == null) {
            enabledSlots = new HashSet<>();
            this.steppingEnabledSlots.set(enabledSlots);
         }

         enabledSlots.add(count);
      }
   }

   boolean isSteppingEnabledOnCurrentThread() {
      CompilerAsserts.neverPartOfCompilation();

      assert this.debugger.getEnv().getEnteredContext() != null : "Need to be called on a context thread";

      int count = this.debugger.getSteppingDisabledCount();
      if (count == 0) {
         return true;
      } else {
         Set<Integer> enabledSlots = this.steppingEnabledSlots.get();
         return enabledSlots != null && enabledSlots.contains(count);
      }
   }

   void clearDisabledSteppingOnCurrentThread(int count) {
      CompilerAsserts.neverPartOfCompilation();

      assert this.debugger.getEnv().getEnteredContext() != null : "Need to be called on a context thread";

      assert count > 0 : "Wrong count = " + count;

      Set<Integer> enabledSlots = this.steppingEnabledSlots.get();
      if (enabledSlots != null) {
         enabledSlots.remove(count);
         if (enabledSlots.isEmpty()) {
            this.steppingEnabledSlots.remove();
         }
      }
   }

   public void suspend(Thread t) {
      if (Debugger.TRACE) {
         this.trace("suspend thread %s ", t);
      }

      if (this.closed) {
         throw new IllegalStateException("session closed");
      } else {
         this.setSteppingStrategy(t, SteppingStrategy.createAlwaysHalt(), true);
      }
   }

   public synchronized void suspendAll() {
      if (Debugger.TRACE) {
         this.trace("suspend all threads");
      }

      if (this.closed) {
         throw new IllegalStateException("session closed");
      } else {
         this.suspendAll = true;

         for (Thread t : this.strategyMap.keySet()) {
            SteppingStrategy s = this.strategyMap.get(t);

            assert s != null;

            if (s.isDone() || s.isConsumed()) {
               this.setSteppingStrategy(t, SteppingStrategy.createAlwaysHalt(), false);
            }
         }

         this.updateStepping();
      }
   }

   public synchronized void resumeAll() {
      if (Debugger.TRACE) {
         this.trace("resume all threads");
      }

      if (this.closed) {
         throw new IllegalStateException("session closed");
      } else {
         this.clearStrategies();
      }
   }

   public synchronized void resume(Thread t) {
      if (Debugger.TRACE) {
         this.trace("resume threads", t);
      }

      if (this.closed) {
         throw new IllegalStateException("session closed");
      } else {
         this.setSteppingStrategy(t, SteppingStrategy.createContinue(), true);
      }
   }

   private synchronized void setSteppingStrategy(Thread thread, SteppingStrategy strategy, boolean updateStepping) {
      if (!this.closed) {
         assert strategy != null;

         SteppingStrategy oldStrategy = this.strategyMap.put(thread, strategy);
         if (oldStrategy != strategy) {
            if (Debugger.TRACE) {
               this.trace("set stepping for thread: %s with strategy: %s", thread, strategy);
            }

            if (updateStepping) {
               this.updateStepping();
            }
         }
      }
   }

   private synchronized void clearStrategies() {
      this.suspendAll = false;
      this.suspendNext = false;
      this.strategyMap.clear();
      this.updateStepping();
   }

   private SteppingStrategy getSteppingStrategy(Thread value) {
      return this.strategyMap.get(value);
   }

   private void updateStepping() {
      assert Thread.holdsLock(this);

      boolean needsStepping = this.suspendNext || this.suspendAll;
      if (!needsStepping) {
         for (Thread t : this.strategyMap.keySet()) {
            SteppingStrategy s = this.strategyMap.get(t);

            assert s != null;

            if (!s.isDone()) {
               needsStepping = true;
               break;
            }
         }
      }

      this.stepping.set(needsStepping);
   }

   @CompilerDirectives.TruffleBoundary
   void setThreadSuspendEnabled(boolean enabled) {
      if (!enabled) {
         this.threadSuspensions.set(DebuggerSession.ThreadSuspension.DISABLED);
      } else {
         this.threadSuspensions.remove();
      }
   }

   private void addBindings(boolean includeInternalCode, Predicate<Source> sFilter) {
      if (this.syntaxElementsBinding == null) {
         if (!this.sourceElements.isEmpty()) {
            Class<?>[] syntaxTags = new Class[this.sourceElements.size() + (this.hasRootElement ? 0 : 1)];
            int i = 0;

            for (SourceElement element : this.sourceElements) {
               syntaxTags[i++] = element.getTag();
            }

            assert i == this.sourceElements.size();

            if (!this.hasRootElement) {
               syntaxTags[i] = StandardTags.RootTag.class;
            }

            this.syntaxElementsBinding = this.createBinding(includeInternalCode, sFilter, new ExecutionEventNodeFactory() {
               @Override
               public ExecutionEventNode create(EventContext context) {
                  if (context.hasTag(StandardTags.RootTag.class)) {
                     DebuggerSession.this.resolveUnresolvedBreakpoints(context.getInstrumentedNode());
                     return DebuggerSession.this.new RootSteppingDepthNode(context);
                  } else {
                     return DebuggerSession.this.new SteppingNode(context);
                  }
               }
            }, this.hasExpressionElement, syntaxTags);
            this.allBindings.add(this.syntaxElementsBinding);
         } else {
            this.syntaxElementsBinding = this.createBinding(includeInternalCode, sFilter, new ExecutionEventNodeFactory() {
               @Override
               public ExecutionEventNode create(EventContext context) {
                  if (context.hasTag(StandardTags.RootTag.class)) {
                     DebuggerSession.this.resolveUnresolvedBreakpoints(context.getInstrumentedNode());
                  }

                  return null;
               }
            }, false, StandardTags.RootTag.class);
         }
      }
   }

   private EventBinding<? extends ExecutionEventNodeFactory> createBinding(
      boolean includeInternalCode, Predicate<Source> sFilter, ExecutionEventNodeFactory factory, boolean onInput, Class<?>... tags
   ) {
      SourceSectionFilter.Builder builder = SourceSectionFilter.newBuilder().tagIs(tags);
      builder.includeInternal(includeInternalCode);
      if (sFilter != null) {
         builder.sourceIs(new SourceSectionFilter.SourcePredicate() {
            @Override
            public boolean test(Source source) {
               return sFilter.test(source);
            }
         });
      }

      SourceSectionFilter ssf = builder.build();
      return onInput
         ? this.debugger.getInstrumenter().attachExecutionEventFactory(ssf, ssf, factory)
         : this.debugger.getInstrumenter().attachExecutionEventFactory(ssf, factory);
   }

   private void removeBindings() {
      assert Thread.holdsLock(this);

      if (this.syntaxElementsBinding != null) {
         this.allBindings.remove(this.syntaxElementsBinding);
         this.syntaxElementsBinding.dispose();
         this.syntaxElementsBinding = null;
         if (Debugger.TRACE) {
            this.trace("disabled stepping");
         }
      }
   }

   Set<SourceElement> getSourceElements() {
      return this.sourceElements;
   }

   public DebugValue createPrimitiveValue(Object primitiveValue, LanguageInfo language) throws IllegalArgumentException {
      DebugValue.checkPrimitive(primitiveValue);
      return new DebugValue.HeapValue(this, language, null, primitiveValue);
   }

   @Override
   public synchronized void close() {
      if (Debugger.TRACE) {
         this.trace("close session");
      }

      if (this.closed) {
         throw new IllegalStateException("session already closed");
      } else {
         this.clearStrategies();
         this.removeBindings();

         for (Breakpoint breakpoint : this.breakpoints) {
            breakpoint.sessionClosed(this);
         }

         this.currentSuspendedEventMap.clear();
         this.allBindings.clear();
         this.debugger.disposedSession(this);
         this.closed = true;
      }
   }

   public List<Breakpoint> getBreakpoints() {
      if (this.closed) {
         throw new IllegalStateException("session already closed");
      } else {
         List<Breakpoint> b;
         synchronized (this.breakpoints) {
            b = new ArrayList<>(this.breakpoints);
         }

         return Collections.unmodifiableList(b);
      }
   }

   void visitBreakpoints(Consumer<Breakpoint> consumer) {
      synchronized (this.breakpoints) {
         for (Breakpoint b : this.breakpoints) {
            consumer.accept(b);
         }
      }
   }

   @Deprecated(since = "19.0")
   public void setBreakpointsActive(boolean active) {
      for (Breakpoint.Kind kind : Breakpoint.Kind.VALUES) {
         this.setBreakpointsActive(kind, active);
      }
   }

   public void setBreakpointsActive(Breakpoint.Kind breakpointKind, boolean active) {
      switch (breakpointKind) {
         case SOURCE_LOCATION:
            this.locationBreakpointsActive.set(active);
            break;
         case EXCEPTION:
            this.exceptionBreakpointsActive.set(active);
            break;
         case HALT_INSTRUCTION:
            this.alwaysHaltBreakpointsActive.set(active);
            break;
         default:
            CompilerDirectives.transferToInterpreter();
            throw new IllegalStateException("Unhandled breakpoint kind: " + breakpointKind);
      }
   }

   @Deprecated(since = "19.0")
   public boolean isBreakpointsActive() {
      for (Breakpoint.Kind kind : Breakpoint.Kind.VALUES) {
         if (this.isBreakpointsActive(kind)) {
            return true;
         }
      }

      return false;
   }

   public boolean isBreakpointsActive(Breakpoint.Kind breakpointKind) {
      switch (breakpointKind) {
         case SOURCE_LOCATION:
            return this.locationBreakpointsActive.get();
         case EXCEPTION:
            return this.exceptionBreakpointsActive.get();
         case HALT_INSTRUCTION:
            return this.alwaysHaltBreakpointsActive.get();
         default:
            CompilerDirectives.transferToInterpreter();
            throw new IllegalStateException("Unhandled breakpoint kind: " + breakpointKind);
      }
   }

   public synchronized Breakpoint install(Breakpoint breakpoint) {
      this.install(breakpoint, false);
      return breakpoint;
   }

   synchronized void install(Breakpoint breakpoint, boolean global) {
      if (this.closed) {
         if (!global) {
            throw new IllegalStateException("Debugger session is already closed. Cannot install new breakpoints.");
         }
      } else {
         if (!global && breakpoint.isResolvable() && !breakpoint.isResolved()) {
            this.breakpointsUnresolved.add(breakpoint);
            this.breakpointsUnresolvedEmpty = this.breakpointsUnresolved.isEmpty();
         }

         if (!breakpoint.install(this, !global)) {
            this.breakpointsUnresolved.remove(breakpoint);
            this.breakpointsUnresolvedEmpty = this.breakpointsUnresolved.isEmpty();
         } else {
            if (!global) {
               this.breakpoints.add(breakpoint);
            }

            if (Debugger.TRACE) {
               this.trace("installed session breakpoint %s", breakpoint);
            }
         }
      }
   }

   private void resolveUnresolvedBreakpoints(Node node) {
      if (!this.breakpointsUnresolvedEmpty) {
         SourceSection sourceSection = node.getSourceSection();
         if (sourceSection != null) {
            Source source = sourceSection.getSource();

            for (Breakpoint breakpoint : this.breakpointsUnresolved) {
               if (breakpoint.isEnabled()) {
                  breakpoint.doResolve(source);
               }
            }
         }
      }
   }

   void breakpointResolved(Breakpoint breakpoint) {
      assert breakpoint.isResolved();

      this.breakpointsUnresolved.remove(breakpoint);
      this.breakpointsUnresolvedEmpty = this.breakpointsUnresolved.isEmpty();
   }

   synchronized void disposeBreakpoint(Breakpoint breakpoint) {
      this.breakpoints.remove(breakpoint);
      if (Debugger.TRACE) {
         this.trace("disposed session breakpoint %s", breakpoint);
      }
   }

   public void setAsynchronousStackDepth(int depth) {
      this.debugger.getEnv().setAsynchronousStackDepth(depth);
   }

   public void setContextsListener(DebugContextsListener listener, boolean includeActiveContexts) {
      this.executionLifecycle.setContextsListener(listener, includeActiveContexts);
   }

   public void setThreadsListener(DebugThreadsListener listener, boolean includeInitializedThreads) {
      this.executionLifecycle.setThreadsListener(listener, includeInitializedThreads);
   }

   public void setSourcePath(Iterable<URI> uris) {
      this.sources.setSourcePath(uris);
   }

   public Source resolveSource(Source source) {
      return this.sources.resolve(source);
   }

   SourceSection resolveSection(SourceSection section) {
      return this.sources.resolve(section);
   }

   SourceSection resolveSection(Node node) {
      return this.sources.resolve(DebugSourcesResolver.findEncapsulatedSourceSection(node));
   }

   @CompilerDirectives.TruffleBoundary
   Object notifyCallback(
      EventContext context,
      DebuggerNode source,
      MaterializedFrame frame,
      SuspendAnchor suspendAnchor,
      DebuggerNode.InputValuesProvider inputValuesProvider,
      Object returnValue,
      DebugException exception,
      Breakpoint.BreakpointConditionFailure conditionFailure
   ) {
      DebuggerSession.ThreadSuspension suspensionDisabled = this.threadSuspensions.get();
      if (suspensionDisabled != null && !suspensionDisabled.enabled) {
         return returnValue;
      } else if (source.isStepNode() && this.ignoreLanguageContextInitialization.get() && !source.getContext().isLanguageContextInitialized()) {
         return returnValue;
      } else {
         Thread currentThread = Thread.currentThread();
         SuspendedEvent event = this.currentSuspendedEventMap.get(currentThread);
         if (event != null) {
            if (Debugger.TRACE) {
               this.trace("ignored suspended reason: recursive from source:%s context:%s location:%s", source, source.getContext(), source.getSuspendAnchors());
            }

            return returnValue;
         } else if (source.consumeIsDuplicate(this)) {
            if (Debugger.TRACE) {
               this.trace("ignored suspended reason: duplicate from source:%s context:%s location:%s", source, source.getContext(), source.getSuspendAnchors());
            }

            return returnValue;
         } else {
            List<DebuggerNode> nodes = this.collectDebuggerNodes(source, suspendAnchor);

            for (DebuggerNode node : nodes) {
               if (node != source) {
                  node.markAsDuplicate(this);
               }
            }

            SteppingStrategy s = this.getSteppingStrategy(currentThread);
            if (this.suspendNext) {
               synchronized (this) {
                  if (this.suspendNext) {
                     s = SteppingStrategy.createAlwaysHalt();
                     this.setSteppingStrategy(currentThread, s, true);
                     this.suspendNext = false;
                  }
               }
            }

            if (s == null) {
               s = this.notifyNewThread(currentThread);
            }

            Map<Breakpoint, Throwable> breakpointFailures = null;
            if (conditionFailure != null) {
               breakpointFailures = new HashMap<>();
               Breakpoint fb = conditionFailure.getBreakpoint();
               if (fb.isGlobal()) {
                  fb = fb.getROWrapper();
               }

               breakpointFailures.put(fb, conditionFailure.getConditionFailure());
            }

            return this.processBreakpointsAndStep(
               context,
               nodes,
               s,
               source,
               frame,
               suspendAnchor,
               inputValuesProvider,
               returnValue,
               exception,
               breakpointFailures,
               new Supplier<SuspendedContext>() {
                  public SuspendedContext get() {
                     return SuspendedContext.create(source.getContext(), DebuggerSession.this.debugger.getEnv());
                  }
               }
            );
         }
      }
   }

   private static void clearFrame(RootNode root, MaterializedFrame frame) {
      FrameDescriptor descriptor = frame.getFrameDescriptor();
      if (root.getFrameDescriptor() == descriptor) {
         Object value = descriptor.getDefaultValue();

         for (int slot = 0; slot < descriptor.getNumberOfSlots(); slot++) {
            if (frame.isStatic(slot)) {
               frame.setObjectStatic(slot, value);
            } else {
               frame.setObject(slot, value);
            }
         }

         for (int slotx = 0; slotx < descriptor.getNumberOfAuxiliarySlots(); slotx++) {
            frame.setAuxiliarySlot(slotx, null);
         }
      }
   }

   private void notifyUnwindCallback(MaterializedFrame frame, InsertableNode insertableNode) {
      Thread currentThread = Thread.currentThread();
      SteppingStrategy s = this.getSteppingStrategy(currentThread);

      assert s != null;

      assert s.isUnwind();

      assert s.step(this, null, null);

      s.consume();
      clearFrame(((Node)insertableNode).getRootNode(), frame);
      DebuggerSession.Caller caller = findCurrentCaller(this, this.includeInternal);
      SuspendedContext context = SuspendedContext.create(caller.node, ((SteppingStrategy.Unwind)s).unwind);
      this.doSuspend(context, SuspendAnchor.AFTER, caller.frame, insertableNode);
   }

   static DebuggerSession.Caller findCurrentCaller(DebuggerSession session, boolean includeInternal) {
      return Truffle.getRuntime().iterateFrames(new FrameInstanceVisitor<DebuggerSession.Caller>() {
         private int depth = 0;

         public DebuggerSession.Caller visitFrame(FrameInstance frameInstance) {
            if (!SuspendedEvent.isEvalRootStackFrame(session, frameInstance) && this.depth++ == 0) {
               return null;
            } else {
               Node callNode = frameInstance.getCallNode();

               while (callNode != null && !SourceSectionFilter.ANY.includes(callNode)) {
                  callNode = callNode.getParent();
               }

               if (callNode == null) {
                  return null;
               } else {
                  RootNode root = callNode.getRootNode();
                  return root != null && (includeInternal || !root.isInternal()) ? new DebuggerSession.Caller(frameInstance, callNode) : null;
               }
            }
         }
      });
   }

   private Object notifyCallerReturn(EventContext context, SteppingStrategy s, DebuggerNode source, SuspendAnchor suspendAnchor, Object returnValue) {
      if (source.isStepNode() && this.ignoreLanguageContextInitialization.get() && !source.getContext().isLanguageContextInitialized()) {
         return returnValue;
      } else {
         DebuggerSession.Caller caller = findCurrentCaller(this, this.includeInternal);
         return caller == null ? returnValue : this.notifyAtCaller(context, caller, s, source, suspendAnchor, returnValue, null, null);
      }
   }

   Object notifyAtCaller(
      EventContext context,
      DebuggerSession.Caller caller,
      SteppingStrategy s,
      DebuggerNode source,
      SuspendAnchor suspendAnchor,
      Object returnValue,
      DebugException exception,
      Breakpoint.BreakpointConditionFailure conditionFailure
   ) {
      DebuggerSession.ThreadSuspension suspensionDisabled = this.threadSuspensions.get();
      if (suspensionDisabled != null && !suspensionDisabled.enabled) {
         return returnValue;
      } else {
         Thread currentThread = Thread.currentThread();
         SuspendedEvent event = this.currentSuspendedEventMap.get(currentThread);
         if (event != null) {
            if (Debugger.TRACE) {
               this.trace("ignored suspended reason: recursive from source:%s context:%s location:%s", source, source.getContext(), source.getSuspendAnchors());
            }

            return returnValue;
         } else {
            List<DebuggerNode> nodes = this.collectDebuggerNodes(caller.node, suspendAnchor);

            for (DebuggerNode node : nodes) {
               Breakpoint breakpoint = node.getBreakpoint();
               if (breakpoint == null || this.isBreakpointsActive(breakpoint.getKind()) && breakpoint.getCondition() == null) {
                  return returnValue;
               }
            }

            for (DebuggerNode nodex : nodes) {
               nodex.markAsDuplicate(this);
            }

            nodes.add(source);
            SteppingStrategy strategy = s;
            if (s == null) {
               strategy = this.getSteppingStrategy(currentThread);
               if (strategy == null) {
                  strategy = this.notifyNewThread(currentThread);
               }
            }

            Map<Breakpoint, Throwable> breakpointFailures = null;
            if (conditionFailure != null) {
               breakpointFailures = new HashMap<>();
               Breakpoint fb = conditionFailure.getBreakpoint();
               if (fb.isGlobal()) {
                  fb = fb.getROWrapper();
               }

               breakpointFailures.put(fb, conditionFailure.getConditionFailure());
            }

            return this.processBreakpointsAndStep(
               context,
               nodes,
               strategy,
               source,
               caller.frame,
               suspendAnchor,
               null,
               returnValue,
               exception,
               breakpointFailures,
               new Supplier<SuspendedContext>() {
                  public SuspendedContext get() {
                     return SuspendedContext.create(caller.node, null);
                  }
               }
            );
         }
      }
   }

   private Object processBreakpointsAndStep(
      EventContext context,
      List<DebuggerNode> nodes,
      SteppingStrategy s,
      DebuggerNode source,
      MaterializedFrame frame,
      SuspendAnchor suspendAnchor,
      DebuggerNode.InputValuesProvider inputValuesProvider,
      Object returnValue,
      DebugException exception,
      Map<Breakpoint, Throwable> breakpointFailures,
      Supplier<SuspendedContext> contextSupplier
   ) {
      List<Breakpoint> breaks = null;

      for (DebuggerNode node : nodes) {
         Breakpoint breakpoint = node.getBreakpoint();
         if (breakpoint != null && this.isBreakpointsActive(breakpoint.getKind())) {
            boolean hit = true;
            Breakpoint.BreakpointConditionFailure failure = null;

            try {
               hit = breakpoint.notifyIndirectHit(context, source, node, frame, exception);
            } catch (Breakpoint.BreakpointConditionFailure var19) {
               failure = var19;
            }

            if (hit) {
               if (breaks == null) {
                  breaks = new ArrayList<>();
               }

               breaks.add(breakpoint.isGlobal() ? breakpoint.getROWrapper() : breakpoint);
            }

            if (failure != null) {
               if (breakpointFailures == null) {
                  breakpointFailures = new HashMap<>();
               }

               Breakpoint fb = failure.getBreakpoint();
               if (fb.isGlobal()) {
                  fb = fb.getROWrapper();
               }

               breakpointFailures.put(fb, failure.getConditionFailure());
            }
         }
      }

      if (breaks == null) {
         breaks = Collections.emptyList();
      }

      if (breakpointFailures == null) {
         breakpointFailures = Collections.emptyMap();
      }

      boolean hitStepping = s.step(this, source.getContext(), suspendAnchor);
      boolean hitBreakpoint = !breaks.isEmpty();
      Object newReturnValue = returnValue;
      if (hitStepping || hitBreakpoint) {
         s.consume();
         newReturnValue = this.doSuspend(
            contextSupplier.get(), suspendAnchor, frame, source, inputValuesProvider, returnValue, exception, breaks, breakpointFailures
         );
      } else if (Debugger.TRACE) {
         this.trace("ignored suspended reason: strategy(%s) from source:%s context:%s location:%s", s, source, source.getContext(), source.getSuspendAnchors());
      }

      if (s.isKill()) {
         this.performKill(source.getContext().getInstrumentedNode());
      }

      return newReturnValue;
   }

   private Object doSuspend(SuspendedContext context, SuspendAnchor suspendAnchor, MaterializedFrame frame, InsertableNode insertableNode) {
      return this.doSuspend(context, suspendAnchor, frame, insertableNode, null, null, null, Collections.emptyList(), Collections.emptyMap());
   }

   private Object doSuspend(
      SuspendedContext context,
      SuspendAnchor suspendAnchor,
      MaterializedFrame frame,
      InsertableNode insertableNode,
      DebuggerNode.InputValuesProvider inputValuesProvider,
      Object returnValue,
      DebugException exception,
      List<Breakpoint> breaks,
      Map<Breakpoint, Throwable> conditionFailures
   ) {
      CompilerAsserts.neverPartOfCompilation();
      Thread currentThread = Thread.currentThread();

      SuspendedEvent suspendedEvent;
      try {
         suspendedEvent = new SuspendedEvent(
            this, currentThread, context, frame, suspendAnchor, insertableNode, inputValuesProvider, returnValue, exception, breaks, conditionFailures
         );
         if (exception != null) {
            exception.setSuspendedEvent(suspendedEvent);
         }

         this.currentSuspendedEventMap.put(currentThread, suspendedEvent);

         try {
            this.callback.onSuspend(suspendedEvent);
         } finally {
            this.currentSuspendedEventMap.remove(currentThread);
            Object newReturnValue = suspendedEvent.getReturnObject();
            suspendedEvent.clearLeakingReferences();
         }
      } catch (Throwable var18) {
         throw var18;
      }

      if (this.closed) {
         Object var20;
         return var20;
      } else {
         SteppingStrategy strategy = suspendedEvent.getNextStrategy();
         if (!strategy.isKill()) {
            SteppingStrategy currentStrategy = this.getSteppingStrategy(currentThread);
            if (currentStrategy != null && !currentStrategy.isConsumed()) {
               strategy = currentStrategy;
            }
         }

         strategy.initialize(context, suspendAnchor);
         if (Debugger.TRACE) {
            this.trace("end suspend with strategy %s at %s location %s", strategy, context, suspendAnchor);
         }

         this.setSteppingStrategy(currentThread, strategy, true);
         if (strategy.isKill()) {
            this.performKill(context.getInstrumentedNode());
         } else if (strategy.isUnwind()) {
            ThreadDeath unwind = context.createUnwind(null, this.syntaxElementsBinding);
            ((SteppingStrategy.Unwind)strategy).unwind = unwind;
            throw unwind;
         }

         Object var19;
         return var19;
      }
   }

   private void performKill(Node location) {
      if (Boolean.TRUE.equals(inEvalInContext.get())) {
         throw new KillException(location);
      } else {
         TruffleContext truffleContext = this.debugger.getEnv().getEnteredContext();
         truffleContext.closeCancelled(location, "Execution cancelled by a debugging session.");
      }
   }

   private List<DebuggerNode> collectDebuggerNodes(DebuggerNode source, SuspendAnchor suspendAnchor) {
      EventContext context = source.getContext();
      List<DebuggerNode> nodes = new ArrayList<>();
      nodes.add(source);
      Iterator<ExecutionEventNode> nodesIterator = context.lookupExecutionEventNodes(this.allBindings);
      if (SuspendAnchor.BEFORE.equals(suspendAnchor)) {
         boolean after = false;

         while (nodesIterator.hasNext()) {
            DebuggerNode node = (DebuggerNode)nodesIterator.next();
            if (after) {
               if (node.isActiveAt(suspendAnchor)) {
                  nodes.add(node);
               }
            } else {
               after = node == source;
            }
         }
      } else {
         while (nodesIterator.hasNext()) {
            DebuggerNode node = (DebuggerNode)nodesIterator.next();
            if (node == source) {
               break;
            }

            if (node.isActiveAt(suspendAnchor)) {
               nodes.add(node);
            }
         }
      }

      return nodes;
   }

   private List<DebuggerNode> collectDebuggerNodes(Node iNode, SuspendAnchor suspendAnchor) {
      List<DebuggerNode> nodes = new ArrayList<>();

      for (EventBinding<?> binding : this.allBindings) {
         DebuggerNode node = (DebuggerNode)this.debugger.getInstrumenter().lookupExecutionEventNode(iNode, binding);
         if (node != null && node.isActiveAt(suspendAnchor)) {
            nodes.add(node);
         }
      }

      return nodes;
   }

   private synchronized SteppingStrategy notifyNewThread(Thread currentThread) {
      SteppingStrategy s = this.getSteppingStrategy(currentThread);
      if (s == null) {
         if (this.suspendAll) {
            s = SteppingStrategy.createAlwaysHalt();
         } else {
            s = SteppingStrategy.createContinue();
         }

         this.setSteppingStrategy(currentThread, s, true);
      }

      assert s != null;

      return s;
   }

   static Object evalInContext(SuspendedEvent ev, String code, FrameInstance frameInstance) throws DebugException {
      Node node;
      MaterializedFrame frame;
      if (frameInstance == null) {
         node = ev.getContext().getInstrumentedNode();
         frame = ev.getMaterializedFrame();
      } else {
         node = frameInstance.getCallNode();
         frame = frameInstance.getFrame(FrameInstance.FrameAccess.MATERIALIZE).materialize();
      }

      Object ex;
      try {
         inEvalInContext.set(Boolean.TRUE);
         ex = evalInContext(ev, node, frame, code);
      } catch (KillException var13) {
         throw DebugException.create(ev.getSession(), "Evaluation was killed.");
      } catch (IllegalStateException var14) {
         throw var14;
      } catch (Throwable var15) {
         LanguageInfo language = null;
         RootNode root = node.getRootNode();
         if (root != null) {
            language = root.getLanguageInfo();
         }

         throw DebugException.create(ev.getSession(), var15, language);
      } finally {
         inEvalInContext.remove();
      }

      return ex;
   }

   private static Object evalInContext(SuspendedEvent ev, Node node, MaterializedFrame frame, String code) {
      RootNode rootNode = node.getRootNode();
      if (rootNode == null) {
         throw new IllegalArgumentException("Cannot evaluate in context using a node that is not yet adopted using a RootNode.");
      } else {
         LanguageInfo info = rootNode.getLanguageInfo();
         if (info == null) {
            throw new IllegalArgumentException("Cannot evaluate in context using a without an associated TruffleLanguage.");
         } else {
            Source source = Source.newBuilder(info.getId(), code, "eval in context").internal(false).build();
            ExecutableNode fragment = ev.getSession().getDebugger().getEnv().parseInline(source, node, frame);
            if (fragment != null) {
               ev.getInsertableNode().setParentOf(fragment);
               return fragment.execute(frame);
            } else if (!info.isInteractive()) {
               throw new IllegalStateException("Can not evaluate in a non-interactive language.");
            } else {
               return Debugger.ACCESSOR.evalInContext(source, node, frame);
            }
         }
      }
   }

   static final class Caller {
      final Node node;
      final MaterializedFrame frame;

      Caller(FrameInstance frameInstance) {
         this.node = frameInstance.getCallNode();
         this.frame = frameInstance.getFrame(FrameInstance.FrameAccess.MATERIALIZE).materialize();
      }

      Caller(FrameInstance frameInstance, Node callNode) {
         this.node = callNode;
         this.frame = frameInstance.getFrame(FrameInstance.FrameAccess.MATERIALIZE).materialize();
      }
   }

   private final class RootSteppingDepthNode extends DebuggerSession.SteppingNode {
      RootSteppingDepthNode(EventContext context) {
         super(context);
      }

      @Override
      boolean isStepNode() {
         return DebuggerSession.this.hasRootElement;
      }

      @Override
      protected void onEnter(VirtualFrame frame) {
         if (DebuggerSession.this.stepping.get()) {
            this.doEnter();
            if (DebuggerSession.this.hasRootElement) {
               super.onEnter(frame);
            }
         }
      }

      @Override
      public void onReturnValue(VirtualFrame frame, Object result) {
         if (DebuggerSession.this.stepping.get()) {
            this.doReturn(frame.materialize(), result);
         }
      }

      @Override
      public void onReturnExceptional(VirtualFrame frame, Throwable exception) {
         if (DebuggerSession.this.stepping.get()) {
            this.doReturn();
         }
      }

      @Override
      protected Object onUnwind(VirtualFrame frame, Object info) {
         Object ret = super.onUnwind(frame, info);
         if (ret != null) {
            return ret;
         } else {
            return DebuggerSession.this.stepping.get() ? this.doUnwind(frame.materialize()) : null;
         }
      }

      @Override
      public void setParentOf(Node child) {
         this.insert(child);
      }

      @CompilerDirectives.TruffleBoundary
      private void doEnter() {
         SteppingStrategy steppingStrategy = DebuggerSession.this.strategyMap.get(Thread.currentThread());
         if (steppingStrategy != null) {
            steppingStrategy.notifyCallEntry();
         }
      }

      @CompilerDirectives.TruffleBoundary
      private void doReturn(MaterializedFrame frame, Object result) {
         Object newResult = null;

         try {
            if (DebuggerSession.this.hasRootElement) {
               newResult = this.doStepAfter(frame, result);
            }
         } finally {
            SteppingStrategy steppingStrategy = DebuggerSession.this.strategyMap.get(Thread.currentThread());
            if (steppingStrategy != null) {
               steppingStrategy.notifyCallExit();
            }
         }

         Object var8;
         if (var8 != null && ((SteppingStrategy)var8).isStopAfterCall()) {
            newResult = DebuggerSession.this.notifyCallerReturn(
               this.context, (SteppingStrategy)var8, this, SuspendAnchor.AFTER, newResult != null ? newResult : result
            );
            if (newResult != result) {
               throw this.getContext().createUnwind(new ChangedReturnInfo(newResult));
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      private void doReturn() {
         SteppingStrategy steppingStrategy = DebuggerSession.this.strategyMap.get(Thread.currentThread());
         if (steppingStrategy != null) {
            steppingStrategy.notifyCallExit();
         }
      }

      @CompilerDirectives.TruffleBoundary
      private Object doUnwind(MaterializedFrame frame) {
         SteppingStrategy steppingStrategy = DebuggerSession.this.strategyMap.get(Thread.currentThread());
         if (steppingStrategy != null) {
            Object info = steppingStrategy.notifyOnUnwind();
            if (info == ProbeNode.UNWIND_ACTION_REENTER) {
               DebuggerSession.this.notifyUnwindCallback(frame, this);
            }

            return info;
         } else {
            return null;
         }
      }

      @Override
      Set<SuspendAnchor> getSuspendAnchors() {
         return DebuggerSession.ANCHOR_SET_ALL;
      }

      @Override
      boolean isActiveAt(SuspendAnchor anchor) {
         return DebuggerSession.this.hasRootElement;
      }
   }

   static final class StableBoolean {
      @CompilerDirectives.CompilationFinal
      private volatile Assumption unchanged;
      @CompilerDirectives.CompilationFinal
      private volatile boolean value;

      StableBoolean(boolean initialValue) {
         this.value = initialValue;
         this.unchanged = Truffle.getRuntime().createAssumption("Unchanged boolean");
      }

      boolean get() {
         if (this.unchanged.isValid()) {
            return this.value;
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.value;
         }
      }

      void set(boolean value) {
         if (this.value != value) {
            this.value = value;
            Assumption old = this.unchanged;
            this.unchanged = Truffle.getRuntime().createAssumption("Unchanged boolean");
            old.invalidate();
         }
      }
   }

   private class SteppingNode extends DebuggerNode implements DebuggerNode.InputValuesProvider {
      SteppingNode(EventContext context) {
         super(context);
      }

      @Override
      boolean isStepNode() {
         return true;
      }

      @Override
      protected void onEnter(VirtualFrame frame) {
         if (DebuggerSession.this.stepping.get()) {
            this.doStepBefore(frame.materialize());
         }
      }

      @Override
      protected void onReturnValue(VirtualFrame frame, Object result) {
         if (DebuggerSession.this.stepping.get()) {
            Object newResult = this.doStepAfter(frame.materialize(), result);
            if (newResult != result) {
               CompilerDirectives.transferToInterpreter();
               throw this.getContext().createUnwind(new ChangedReturnInfo(newResult));
            }
         }
      }

      @Override
      protected void onReturnExceptional(VirtualFrame frame, Throwable exception) {
         if (DebuggerSession.this.stepping.get()) {
            this.doStepAfter(frame.materialize(), exception);
         }
      }

      @Override
      protected void onInputValue(VirtualFrame frame, EventContext inputContext, int inputIndex, Object inputValue) {
         if (DebuggerSession.this.stepping.get() && DebuggerSession.this.hasExpressionElement) {
            this.saveInputValue(frame, inputIndex, inputValue);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private void doStepBefore(MaterializedFrame frame) {
         SuspendAnchor anchor = SuspendAnchor.BEFORE;
         boolean doCallback;
         if (!DebuggerSession.this.suspendNext && !DebuggerSession.this.suspendAll) {
            SteppingStrategy steppingStrategy = DebuggerSession.this.getSteppingStrategy(Thread.currentThread());
            doCallback = steppingStrategy != null
               && DebuggerSession.this.isSteppingEnabledOnCurrentThread()
               && steppingStrategy.isActiveOnStepTo(this.context, anchor);
         } else {
            doCallback = DebuggerSession.this.isSteppingEnabledOnCurrentThread();
         }

         if (doCallback) {
            DebuggerSession.this.notifyCallback(this.context, this, frame, anchor, null, null, null, null);
         }
      }

      @CompilerDirectives.TruffleBoundary
      protected final Object doStepAfter(MaterializedFrame frame, Object result) {
         SuspendAnchor anchor = SuspendAnchor.AFTER;
         SteppingStrategy steppingStrategy = DebuggerSession.this.getSteppingStrategy(Thread.currentThread());
         return steppingStrategy != null && DebuggerSession.this.isSteppingEnabledOnCurrentThread() && steppingStrategy.isActiveOnStepTo(this.context, anchor)
            ? DebuggerSession.this.notifyCallback(this.context, this, frame, anchor, this, result, null, null)
            : result;
      }

      @Override
      public Object[] getDebugInputValues(MaterializedFrame frame) {
         return this.getSavedInputValues(frame);
      }

      @Override
      Set<SuspendAnchor> getSuspendAnchors() {
         return DebuggerSession.ANCHOR_SET_ALL;
      }

      @Override
      boolean isActiveAt(SuspendAnchor anchor) {
         SteppingStrategy steppingStrategy = DebuggerSession.this.getSteppingStrategy(Thread.currentThread());
         return steppingStrategy != null ? steppingStrategy.isActive(this.context, anchor) : false;
      }
   }

   static final class SuspendContextAndFrame {
      final SuspendedContext context;
      final MaterializedFrame frame;

      SuspendContextAndFrame(SuspendedContext context, MaterializedFrame frame) {
         this.context = context;
         this.frame = frame;
      }
   }

   static final class ThreadSuspension {
      static final DebuggerSession.ThreadSuspension ENABLED = new DebuggerSession.ThreadSuspension(true);
      static final DebuggerSession.ThreadSuspension DISABLED = new DebuggerSession.ThreadSuspension(false);
      boolean enabled;

      ThreadSuspension(boolean enabled) {
         this.enabled = enabled;
      }
   }
}
