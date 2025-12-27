package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.EventBinding;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ExecuteSourceEvent;
import com.oracle.truffle.api.instrumentation.ExecuteSourceListener;
import com.oracle.truffle.api.instrumentation.ExecutionEventNode;
import com.oracle.truffle.api.instrumentation.ExecutionEventNodeFactory;
import com.oracle.truffle.api.instrumentation.SourceFilter;
import com.oracle.truffle.api.instrumentation.SourceSectionFilter;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.NodeLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.ControlFlowException;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.ExecutableNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.nodes.SlowPathException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class Breakpoint {
   private static final Breakpoint BUILDER_INSTANCE = new Breakpoint();
   private final SuspendAnchor suspendAnchor;
   private final BreakpointLocation locationKey;
   private final boolean oneShot;
   private final BreakpointExceptionFilter exceptionFilter;
   private final Reference<Object> rootInstanceRef;
   private final Breakpoint.ResolveListener resolveListener;
   private volatile Debugger debugger;
   private final List<DebuggerSession> sessions = new LinkedList<>();
   private volatile Assumption sessionsUnchanged;
   private volatile boolean enabled;
   private volatile int ignoreCount;
   private volatile boolean disposed;
   private volatile String condition;
   private volatile boolean global;
   private volatile Breakpoint.GlobalBreakpoint roWrapper;
   private final AtomicLong hitCount = new AtomicLong();
   private volatile Assumption conditionUnchanged;
   private volatile Assumption conditionExistsUnchanged;
   private volatile EventBinding<? extends ExecutionEventNodeFactory> breakpointBinding;
   private final AtomicBoolean breakpointBindingAttaching = new AtomicBoolean(false);
   private volatile boolean breakpointBindingReady;
   private volatile Predicate<Source> sourcePredicate;
   private final AtomicReference<Object> sourceBinding = new AtomicReference<>();
   private static final Object SOURCE_BINDING_RESOLVED = new Object();

   Breakpoint(BreakpointLocation key, SuspendAnchor suspendAnchor) {
      this(key, suspendAnchor, false, null, null, null);
   }

   private Breakpoint(
      BreakpointLocation key,
      SuspendAnchor suspendAnchor,
      boolean oneShot,
      BreakpointExceptionFilter exceptionFilter,
      Object rootInstance,
      Breakpoint.ResolveListener resolveListener
   ) {
      this.locationKey = key;
      this.suspendAnchor = suspendAnchor;
      this.oneShot = oneShot;
      this.exceptionFilter = exceptionFilter;
      this.rootInstanceRef = rootInstance != null ? new WeakReference<>(rootInstance) : null;
      this.resolveListener = resolveListener;
      this.enabled = true;
   }

   private Breakpoint() {
      this.locationKey = null;
      this.suspendAnchor = SuspendAnchor.BEFORE;
      this.oneShot = false;
      this.exceptionFilter = null;
      this.rootInstanceRef = null;
      this.resolveListener = null;
   }

   public Breakpoint.Kind getKind() {
      if (this.locationKey == null) {
         return Breakpoint.Kind.HALT_INSTRUCTION;
      } else {
         return this.exceptionFilter == null ? Breakpoint.Kind.SOURCE_LOCATION : Breakpoint.Kind.EXCEPTION;
      }
   }

   public boolean isDisposed() {
      return this.disposed;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enabled) {
      boolean doInstall = false;
      synchronized (this) {
         if (this.disposed) {
            return;
         }

         if (this.enabled != enabled) {
            if (!this.sessions.isEmpty()) {
               doInstall = true;
            }

            this.enabled = enabled;
         }
      }

      if (doInstall) {
         if (enabled) {
            this.install();
         } else {
            this.uninstall();
         }
      }
   }

   public boolean isResolved() {
      return this.sourceBinding.get() == SOURCE_BINDING_RESOLVED;
   }

   public synchronized void setCondition(String expression) {
      boolean existsChanged = this.condition == null != (expression == null);
      this.condition = expression;
      Assumption assumption = this.conditionUnchanged;
      if (assumption != null) {
         this.conditionUnchanged = null;
         assumption.invalidate();
      }

      if (existsChanged) {
         assumption = this.conditionExistsUnchanged;
         if (assumption != null) {
            this.conditionExistsUnchanged = null;
            assumption.invalidate();
         }
      }
   }

   @SuppressFBWarnings("UG")
   public String getCondition() {
      return this.condition;
   }

   public void dispose() {
      DebuggerSession[] breakpointSessions = null;
      Debugger breakpointDebugger = null;
      synchronized (this) {
         if (!this.disposed) {
            this.setEnabled(false);
            this.getAndSetSourceBinding(null);
            breakpointSessions = this.sessions.toArray(new DebuggerSession[this.sessions.size()]);
            breakpointDebugger = this.debugger;
            this.debugger = null;
            this.disposed = true;
         }
      }

      if (breakpointSessions != null) {
         for (DebuggerSession session : breakpointSessions) {
            session.disposeBreakpoint(this);
         }
      }

      if (breakpointDebugger != null) {
         breakpointDebugger.disposeBreakpoint(this);
      }
   }

   private Object getAndSetSourceBinding(Object newValue) {
      Object oldBinding = this.sourceBinding.getAndSet(newValue);
      if (oldBinding instanceof EventBinding) {
         ((EventBinding)oldBinding).dispose();
      }

      return oldBinding;
   }

   public boolean isOneShot() {
      return this.oneShot;
   }

   public int getIgnoreCount() {
      return this.ignoreCount;
   }

   public void setIgnoreCount(int ignoreCount) {
      this.ignoreCount = ignoreCount;
   }

   public int getHitCount() {
      return (int)this.hitCount.get();
   }

   public String getLocationDescription() {
      return this.locationKey.toString();
   }

   public SuspendAnchor getSuspendAnchor() {
      return this.suspendAnchor;
   }

   public boolean isModifiable() {
      return true;
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode());
   }

   private synchronized Assumption getConditionUnchanged() {
      if (this.conditionUnchanged == null) {
         this.conditionUnchanged = Truffle.getRuntime().createAssumption("Breakpoint condition unchanged.");
      }

      return this.conditionUnchanged;
   }

   private synchronized Assumption getConditionExistsUnchanged() {
      if (this.conditionExistsUnchanged == null) {
         this.conditionExistsUnchanged = Truffle.getRuntime().createAssumption("Breakpoint condition existence unchanged.");
      }

      return this.conditionExistsUnchanged;
   }

   synchronized void installGlobal(Debugger d) {
      if (this.disposed) {
         throw new IllegalArgumentException("Cannot install breakpoint, it is disposed already.");
      } else if (this.debugger != null) {
         throw new IllegalStateException("Breakpoint is already installed in a Debugger instance.");
      } else {
         this.install(d);
         this.global = true;
      }
   }

   private void install(Debugger d) {
      assert Thread.holdsLock(this);

      if (this.debugger != null && this.debugger != d) {
         throw new IllegalStateException("Breakpoint is already installed in a different Debugger instance.");
      } else {
         this.debugger = d;
         if (this.exceptionFilter != null) {
            this.exceptionFilter.setDebugger(d);
         }
      }
   }

   boolean install(DebuggerSession d, boolean failOnError) {
      synchronized (this) {
         if (this.disposed) {
            if (failOnError) {
               throw new IllegalArgumentException("Cannot install breakpoint, it is disposed already.");
            }

            return false;
         }

         if (this.sessions.contains(d)) {
            if (failOnError) {
               throw new IllegalStateException("Breakpoint is already installed in the session.");
            }

            return true;
         }

         this.sessions.add(d);
         this.sessionsAssumptionInvalidate();
         this.install(d.getDebugger());
      }

      if (this.enabled) {
         this.install();
      }

      return true;
   }

   private void install() {
      Object obj = this.sourceBinding.get();
      EventBinding<?> binding = SOURCE_BINDING_RESOLVED == obj ? null : (EventBinding)obj;
      SourceFilter filter;
      if (obj == null && (filter = this.locationKey.createSourceFilter()) != null) {
         this.sourcePredicate = this.locationKey.createSourcePredicate();
         binding = this.debugger.getInstrumenter().createExecuteSourceBinding(filter, new ExecuteSourceListener() {
            @Override
            public void onExecute(ExecuteSourceEvent event) {
               Source source = event.getSource();
               Breakpoint.this.resolveBreakpointAssignBinding(source);
            }
         }, true);
         if (this.sourceBinding.compareAndSet(null, binding)) {
            try {
               binding.attach();
            } catch (IllegalStateException var5) {
               assert binding.isDisposed();
            }
         }
      } else if (this.breakpointBinding == null && (binding == null || binding.isDisposed())) {
         this.assignBinding(this.locationKey.createLocationFilter(null, this.suspendAnchor));
      }
   }

   boolean isResolvable() {
      return this.locationKey.canAdjustLocation();
   }

   void doResolve(Source source) {
      if (!this.isResolved() && this.sourcePredicate != null && this.sourcePredicate.test(source)) {
         this.resolveBreakpointAssignBinding(source);
      }
   }

   private void resolveBreakpointAssignBinding(Source source) {
      SourceSection location = this.locationKey.adjustLocation(source, this.debugger.getEnv(), this.suspendAnchor);
      if (location != null || !source.hasCharacters()) {
         Object eb = this.getAndSetSourceBinding(SOURCE_BINDING_RESOLVED);
         if (location != null && eb != SOURCE_BINDING_RESOLVED) {
            this.resolveBreakpoint(location, true);
         }

         this.assignBinding(this.locationKey.createLocationFilter(source, this.suspendAnchor));
      }
   }

   private void assignBinding(SourceSectionFilter locationFilter) {
      boolean attaching = this.breakpointBindingAttaching.getAndSet(true);
      if (!attaching) {
         EventBinding<? extends ExecutionEventNodeFactory> newBinding = null;
         Debugger dbg = this.debugger;
         if (dbg == null) {
            return;
         }

         try {
            this.breakpointBinding = newBinding = dbg.getInstrumenter().attachExecutionEventFactory(locationFilter, new Breakpoint.BreakpointNodeFactory());
         } finally {
            this.breakpointBindingAttaching.set(false);
            synchronized (this) {
               if (newBinding != null) {
                  this.getAndSetSourceBinding(SOURCE_BINDING_RESOLVED);

                  for (DebuggerSession s : this.sessions) {
                     s.allBindings.add(newBinding);
                  }
               }

               this.breakpointBindingReady = true;
               this.notifyAll();
            }
         }
      }
   }

   boolean isGlobal() {
      return this.global;
   }

   void sessionClosed(DebuggerSession d) {
      boolean doUninstall;
      synchronized (this) {
         this.sessions.remove(d);
         this.sessionsAssumptionInvalidate();
         doUninstall = this.sessions.isEmpty();
      }

      if (doUninstall) {
         this.uninstall();
      }
   }

   Assumption getSessionsUnchanged() {
      assert Thread.holdsLock(this);

      Assumption sessionsLocal = this.sessionsUnchanged;
      if (sessionsLocal == null) {
         this.sessionsUnchanged = sessionsLocal = Truffle.getRuntime().createAssumption();
      }

      return sessionsLocal;
   }

   private void sessionsAssumptionInvalidate() {
      assert Thread.holdsLock(this);

      Assumption assumption = this.sessionsUnchanged;
      if (assumption != null) {
         this.sessionsUnchanged = null;
         assumption.invalidate();
      }
   }

   private void resolveBreakpoint(SourceSection resolvedLocation) {
      this.resolveBreakpoint(resolvedLocation, false);
   }

   private void resolveBreakpoint(SourceSection resolvedLocation, boolean notifyResolved) {
      boolean doNotifyResolved = notifyResolved;
      synchronized (this) {
         if (this.disposed) {
            return;
         }

         if (!this.isResolved()) {
            doNotifyResolved = true;
            this.getAndSetSourceBinding(SOURCE_BINDING_RESOLVED);
         }
      }

      if (doNotifyResolved) {
         for (DebuggerSession s : this.sessions) {
            s.breakpointResolved(this);
         }

         if (this.resolveListener != null) {
            this.resolveListener.breakpointResolved(this, resolvedLocation);
         }
      }
   }

   private void uninstall() {
      EventBinding<?> binding;
      synchronized (this) {
         binding = this.breakpointBinding;
         this.breakpointBinding = null;

         for (DebuggerSession s : this.sessions) {
            s.allBindings.remove(binding);
         }

         this.breakpointBindingReady = false;
         this.getAndSetSourceBinding(null);
      }

      if (binding != null) {
         binding.dispose();
      }
   }

   boolean notifyIndirectHit(EventContext context, DebuggerNode source, DebuggerNode node, MaterializedFrame frame, DebugException exception) throws Breakpoint.BreakpointConditionFailure {
      if (!this.isEnabled()) {
         return false;
      } else {
         assert node.getBreakpoint() == this;

         if (source != node) {
            if (this.rootInstanceRef != null) {
               Object rootInstance = this.rootInstanceRef.get();
               if (rootInstance != null) {
                  Node contextNode = context.getInstrumentedNode();
                  NodeLibrary contextNodeLibrary = NodeLibrary.getUncached(contextNode);
                  if (contextNodeLibrary.hasRootInstance(contextNode, frame)) {
                     try {
                        if (rootInstance != contextNodeLibrary.getRootInstance(contextNode, frame)) {
                           return false;
                        }
                     } catch (UnsupportedMessageException var10) {
                        throw CompilerDirectives.shouldNotReachHere(var10);
                     }
                  }
               }
            }

            Breakpoint.AbstractBreakpointNode breakpointNode = (Breakpoint.AbstractBreakpointNode)node;
            if (!breakpointNode.testCondition(frame)) {
               return false;
            }

            if (this.exceptionFilter != null && exception != null) {
               Throwable throwable = exception.getRawException();

               assert throwable != null;

               BreakpointExceptionFilter.Match matched = this.exceptionFilter.matchException(node, throwable);
               if (!matched.isMatched) {
                  return false;
               }
            }

            if (this.hitCount.incrementAndGet() <= this.ignoreCount) {
               return false;
            }
         }

         if (this.isOneShot()) {
            this.setEnabled(false);
         }

         return true;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object doBreak(
      EventContext context,
      DebuggerNode source,
      Breakpoint.SessionList breakInSessions,
      boolean activeOnNoninternalCalls,
      MaterializedFrame frame,
      boolean onEnter,
      Object result,
      Throwable exception,
      Breakpoint.BreakpointConditionFailure failure
   ) {
      return this.doBreak(context, source, breakInSessions, activeOnNoninternalCalls, frame, onEnter, result, exception, source, false, null, failure);
   }

   @CompilerDirectives.TruffleBoundary
   private Object doBreak(
      EventContext context,
      DebuggerNode source,
      Breakpoint.SessionList breakInSessions,
      boolean activeOnNoninternalCalls,
      MaterializedFrame frame,
      boolean onEnter,
      Object result,
      Throwable exception,
      Node throwLocation,
      boolean isCatchNodeComputed,
      DebugException.CatchLocation catchLocation,
      Breakpoint.BreakpointConditionFailure failure
   ) {
      if (!this.isEnabled()) {
         return result;
      } else if (this.hitCount.incrementAndGet() <= this.ignoreCount) {
         return result;
      } else {
         SuspendAnchor anchor = onEnter ? SuspendAnchor.BEFORE : SuspendAnchor.AFTER;
         Object newResult = result;

         for (Breakpoint.SessionList current = breakInSessions; current != null; current = current.next) {
            DebuggerSession session = current.session;
            if (session.isBreakpointsActive(this.getKind())) {
               boolean internalCompliant = true;
               DebuggerSession.Caller caller = null;
               if (activeOnNoninternalCalls && !session.isIncludeInternal()) {
                  caller = DebuggerSession.findCurrentCaller(session, true);
                  internalCompliant = caller != null && !caller.node.getRootNode().isInternal();
               }

               if (internalCompliant) {
                  synchronized (this) {
                     while (this.breakpointBinding != null && !this.breakpointBindingReady) {
                        try {
                           this.wait();
                        } catch (InterruptedException var22) {
                        }
                     }
                  }

                  DebugException de;
                  if (exception != null) {
                     de = DebugException.create(session, exception, null, throwLocation, isCatchNodeComputed, catchLocation);
                  } else {
                     de = null;
                  }

                  if (caller != null) {
                     newResult = session.notifyAtCaller(context, caller, null, source, anchor, newResult, de, failure);
                  } else {
                     newResult = session.notifyCallback(context, source, frame, anchor, null, newResult, de, failure);
                  }

                  session.restoreSteppingOnCurrentThread();
               }
            }
         }

         return newResult;
      }
   }

   Breakpoint getROWrapper() {
      assert this.global;

      Breakpoint.GlobalBreakpoint wrapper = this.roWrapper;
      if (wrapper == null) {
         synchronized (this) {
            wrapper = this.roWrapper;
            if (wrapper == null) {
               this.roWrapper = wrapper = new Breakpoint.GlobalBreakpoint(this);
            }
         }
      }

      return wrapper;
   }

   Object getRootInstance() {
      return this.rootInstanceRef != null ? this.rootInstanceRef.get() : null;
   }

   public static Breakpoint.Builder newBuilder(URI sourceUri) {
      return BUILDER_INSTANCE.new Builder(sourceUri);
   }

   public static Breakpoint.Builder newBuilder(Source source) {
      return BUILDER_INSTANCE.new Builder(source);
   }

   public static Breakpoint.Builder newBuilder(SourceSection sourceSection) {
      return BUILDER_INSTANCE.new Builder(sourceSection);
   }

   public static Breakpoint.ExceptionBuilder newExceptionBuilder(boolean caught, boolean uncaught) {
      if (!caught && !uncaught) {
         throw new IllegalArgumentException("At least one of 'caught' or 'uncaught' needs to be true.");
      } else {
         return BUILDER_INSTANCE.new ExceptionBuilder(caught, uncaught);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static Breakpoint.SessionList removeDuplicateSession(Breakpoint.SessionList sessions, DebuggerSession session) {
      Breakpoint.SessionList current = sessions;

      boolean foundSession;
      for (foundSession = false; current != null; current = current.next) {
         if (session == current.session) {
            foundSession = true;
            break;
         }
      }

      if (foundSession) {
         Breakpoint.SessionList newSessions = null;

         for (Breakpoint.SessionList var5 = sessions; var5 != null; var5 = var5.next) {
            if (session != var5.session) {
               newSessions = new Breakpoint.SessionList(var5, newSessions);
            }
         }

         return newSessions;
      } else {
         return sessions;
      }
   }

   private abstract static class AbstractBreakpointNode extends DebuggerNode {
      private final Breakpoint breakpoint;
      protected final BranchProfile breakBranch = BranchProfile.create();
      @Node.Child
      private NodeLibrary contextNodeLibrary;
      @Node.Child
      private Breakpoint.ConditionalBreakNode breakCondition;
      @CompilerDirectives.CompilationFinal
      private Assumption conditionExistsUnchanged;
      @CompilerDirectives.CompilationFinal
      protected boolean activeOnNoninternalCalls;
      @CompilerDirectives.CompilationFinal
      private Breakpoint.SessionList sessionList;
      @CompilerDirectives.CompilationFinal
      private Assumption sessionsUnchanged;

      AbstractBreakpointNode(Breakpoint breakpoint, EventContext context) {
         super(context);
         this.breakpoint = breakpoint;
         if (breakpoint.rootInstanceRef != null) {
            this.contextNodeLibrary = NodeLibrary.getFactory().create(context.getInstrumentedNode());
         }

         this.conditionExistsUnchanged = breakpoint.getConditionExistsUnchanged();
         if (breakpoint.condition != null) {
            this.breakCondition = new Breakpoint.ConditionalBreakNode(context, breakpoint);
         }
      }

      private Breakpoint.SessionList initializeSessions() {
         CompilerAsserts.neverPartOfCompilation();
         synchronized (this.breakpoint) {
            boolean inInternalCode = this.context.getInstrumentedNode().getRootNode().isInternal();
            if (inInternalCode
               && (this.breakpoint.locationKey == null || this.breakpoint.locationKey.containsRoot())
               && this.context.hasTag(SourceElement.ROOT.getTag())) {
               this.activeOnNoninternalCalls = true;
            }

            SourceSection sourceSection = this.context.getInstrumentedSourceSection();
            Source inSource;
            if (sourceSection != null) {
               inSource = sourceSection.getSource();
            } else {
               inSource = null;
            }

            Breakpoint.SessionList listEntry = null;
            List<DebuggerSession> allSesssions = this.breakpoint.sessions;
            boolean inactiveInInternal = inInternalCode && !this.activeOnNoninternalCalls;

            for (int i = allSesssions.size() - 1; i >= 0; i--) {
               DebuggerSession session = allSesssions.get(i);
               if ((!inactiveInInternal || session.isIncludeInternal()) && (inSource == null || !session.isSourceFilteredOut(inSource))) {
                  listEntry = new Breakpoint.SessionList(session, listEntry);
               }
            }

            this.sessionList = listEntry;
            this.sessionsUnchanged = this.breakpoint.getSessionsUnchanged();
            return listEntry;
         }
      }

      @Override
      boolean isStepNode() {
         return false;
      }

      @Override
      Breakpoint getBreakpoint() {
         return this.breakpoint;
      }

      protected final Object onNode(VirtualFrame frame, boolean onEnter, Object result, Throwable exception) {
         Breakpoint.SessionList sessions = this.computeUniqueActiveSessions();
         if (sessions == null) {
            return result;
         } else {
            if (this.breakpoint.rootInstanceRef != null) {
               Object rootInstance = this.breakpoint.rootInstanceRef.get();
               if (rootInstance != null && !this.testRootInstance(rootInstance, frame)) {
                  return result;
               }
            }

            Breakpoint.BreakpointConditionFailure conditionError = null;

            try {
               if (!this.testCondition(frame)) {
                  return result;
               }
            } catch (Breakpoint.BreakpointConditionFailure var8) {
               conditionError = var8;
            }

            this.breakBranch.enter();
            return this.breakpoint
               .doBreak(this.context, this, sessions, this.activeOnNoninternalCalls, frame.materialize(), onEnter, result, exception, conditionError);
         }
      }

      private boolean testRootInstance(Object rootInstance, VirtualFrame frame) {
         if (this.contextNodeLibrary.hasRootInstance(this.context.getInstrumentedNode(), frame)) {
            try {
               if (rootInstance != this.contextNodeLibrary.getRootInstance(this.context.getInstrumentedNode(), frame)) {
                  return false;
               }
            } catch (UnsupportedMessageException var4) {
               throw CompilerDirectives.shouldNotReachHere(var4);
            }
         }

         return true;
      }

      @ExplodeLoop
      protected final Breakpoint.SessionList computeUniqueActiveSessions() {
         Breakpoint.SessionList sessions = this.getSessions();
         boolean active = false;
         Breakpoint.SessionList current = sessions;

         for (boolean duplicate = false; current != null; current = current.next) {
            DebuggerSession session = current.session;
            if (this.consumeIsDuplicate(session)) {
               if (!duplicate && sessions.next == null) {
                  return null;
               }

               duplicate = true;
               sessions = Breakpoint.removeDuplicateSession(sessions, session);
            } else if (session.isBreakpointsActive(this.breakpoint.getKind())) {
               active = true;
            }
         }

         return !active ? null : sessions;
      }

      final Breakpoint.SessionList getSessions() {
         Breakpoint.SessionList sessions = this.sessionList;
         Assumption localSessionsUnchanged = this.sessionsUnchanged;
         if (localSessionsUnchanged == null || !localSessionsUnchanged.isValid() || sessions != null && !sessions.isValid()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            sessions = this.initializeSessions();
         }

         return sessions;
      }

      boolean testCondition(VirtualFrame frame) throws Breakpoint.BreakpointConditionFailure {
         Breakpoint.ConditionalBreakNode conditionNode = this.breakCondition;
         if (!this.conditionExistsUnchanged.isValid()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            if (this.breakpoint.condition != null) {
               this.breakCondition = conditionNode = this.insert(new Breakpoint.ConditionalBreakNode(this.context, this.breakpoint));
               this.notifyInserted(conditionNode);
            } else {
               conditionNode = null;
               this.breakCondition = null;
            }

            this.conditionExistsUnchanged = this.breakpoint.getConditionExistsUnchanged();
         }

         Breakpoint.SessionList localSessions = this.getSessions();
         if (localSessions == null) {
            return false;
         } else if (conditionNode != null) {
            try {
               return conditionNode.executeBreakCondition(frame, localSessions);
            } catch (Throwable var5) {
               CompilerDirectives.transferToInterpreter();
               throw new Breakpoint.BreakpointConditionFailure(this.breakpoint, var5);
            }
         } else {
            return true;
         }
      }
   }

   private static class BreakpointAfterNode extends Breakpoint.AbstractBreakpointNode {
      BreakpointAfterNode(Breakpoint breakpoint, EventContext context) {
         super(breakpoint, context);
      }

      @Override
      Set<SuspendAnchor> getSuspendAnchors() {
         return DebuggerSession.ANCHOR_SET_AFTER;
      }

      @Override
      boolean isActiveAt(SuspendAnchor anchor) {
         return SuspendAnchor.AFTER == anchor;
      }

      @Override
      protected void onReturnValue(VirtualFrame frame, Object result) {
         Object newResult = this.onNode(frame, false, result, null);
         if (newResult != result) {
            CompilerDirectives.transferToInterpreter();
            throw this.getContext().createUnwind(new ChangedReturnInfo(newResult));
         }
      }

      @Override
      protected void onReturnExceptional(VirtualFrame frame, Throwable exception) {
         if (!(exception instanceof ControlFlowException) && !(exception instanceof ThreadDeath)) {
            this.onNode(frame, false, null, exception);
         }
      }
   }

   private static class BreakpointAfterNodeException extends Breakpoint.AbstractBreakpointNode {
      BreakpointAfterNodeException(Breakpoint breakpoint, EventContext context) {
         super(breakpoint, context);
      }

      @Override
      Set<SuspendAnchor> getSuspendAnchors() {
         return DebuggerSession.ANCHOR_SET_AFTER;
      }

      @Override
      boolean isActiveAt(SuspendAnchor anchor) {
         return SuspendAnchor.AFTER == anchor;
      }

      @Override
      public void onEnter(VirtualFrame frame) {
         this.getBreakpoint().exceptionFilter.resetReportedException();
      }

      @Override
      public void onReturnValue(VirtualFrame frame, Object result) {
         this.getBreakpoint().exceptionFilter.resetReportedException();
      }

      @Override
      protected void onReturnExceptional(VirtualFrame frame, Throwable exception) {
         if (!(exception instanceof ControlFlowException) && !(exception instanceof ThreadDeath)) {
            Breakpoint.SessionList sessions = this.computeUniqueActiveSessions();
            if (sessions == null) {
               return;
            }

            BreakpointExceptionFilter.Match matched = this.getBreakpoint().exceptionFilter.matchException(this.getContext().getInstrumentedNode(), exception);
            if (matched.isMatched) {
               Breakpoint.BreakpointConditionFailure conditionError = null;

               try {
                  if (!this.testCondition(frame)) {
                     return;
                  }
               } catch (Breakpoint.BreakpointConditionFailure var7) {
                  conditionError = var7;
               }

               this.breakBranch.enter();
               this.doBreak(frame.materialize(), sessions, conditionError, exception, matched);
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      void doBreak(
         MaterializedFrame frame,
         Breakpoint.SessionList debuggerSessions,
         Breakpoint.BreakpointConditionFailure conditionError,
         Throwable exception,
         BreakpointExceptionFilter.Match matched
      ) {
         Node throwLocation = this.getContext().getInstrumentedNode();
         this.getBreakpoint()
            .doBreak(
               this.getContext(),
               this,
               debuggerSessions,
               this.activeOnNoninternalCalls,
               frame,
               false,
               null,
               exception,
               throwLocation,
               matched.isCatchNodeComputed,
               matched.catchLocation,
               conditionError
            );
      }
   }

   private static class BreakpointBeforeNode extends Breakpoint.AbstractBreakpointNode {
      BreakpointBeforeNode(Breakpoint breakpoint, EventContext context) {
         super(breakpoint, context);
      }

      @Override
      Set<SuspendAnchor> getSuspendAnchors() {
         return DebuggerSession.ANCHOR_SET_BEFORE;
      }

      @Override
      boolean isActiveAt(SuspendAnchor anchor) {
         return SuspendAnchor.BEFORE == anchor;
      }

      @Override
      protected void onEnter(VirtualFrame frame) {
         this.onNode(frame, true, null, null);
      }
   }

   static final class BreakpointConditionFailure extends SlowPathException {
      private static final long serialVersionUID = 1L;
      private final Breakpoint breakpoint;

      BreakpointConditionFailure(Breakpoint breakpoint, Throwable cause) {
         super(cause);
         this.breakpoint = breakpoint;
      }

      public Breakpoint getBreakpoint() {
         return this.breakpoint;
      }

      public Throwable getConditionFailure() {
         return this.getCause();
      }
   }

   private class BreakpointNodeFactory implements ExecutionEventNodeFactory {
      @Override
      public ExecutionEventNode create(EventContext context) {
         if (!Breakpoint.this.isResolved()) {
            Breakpoint.this.resolveBreakpoint(context.getInstrumentedSourceSection());
         }

         if (Breakpoint.this.exceptionFilter != null) {
            return new Breakpoint.BreakpointAfterNodeException(Breakpoint.this, context);
         } else {
            switch (Breakpoint.this.suspendAnchor) {
               case BEFORE:
                  return new Breakpoint.BreakpointBeforeNode(Breakpoint.this, context);
               case AFTER:
                  return new Breakpoint.BreakpointAfterNode(Breakpoint.this, context);
               default:
                  throw new IllegalStateException("Unknown suspend anchor: " + Breakpoint.this.suspendAnchor);
            }
         }
      }
   }

   public final class Builder {
      private final Object key;
      private int line = -1;
      private SuspendAnchor anchor = SuspendAnchor.BEFORE;
      private int column = -1;
      private Breakpoint.ResolveListener resolveListener;
      private int ignoreCount;
      private boolean oneShot;
      private DebugValue rootInstance;
      private SourceSection sourceSection;
      private SourceElement[] sourceElements;

      private Builder(Object key) {
         if (key == null) {
            this.key = BreakpointLocation.ANY_SOURCE;
         } else {
            this.key = key;
         }
      }

      private Builder(SourceSection key) {
         this(key.getSource());
         Objects.requireNonNull(key);
         this.sourceSection = key;
      }

      public Breakpoint.Builder lineIs(int line) {
         if (line <= 0) {
            throw new IllegalArgumentException("Line argument must be > 0.");
         } else if (this.line != -1) {
            throw new IllegalStateException("LineIs can only be called once per breakpoint builder.");
         } else if (this.sourceSection != null) {
            throw new IllegalArgumentException("LineIs cannot be used with source section based breakpoint. ");
         } else {
            this.line = line;
            return this;
         }
      }

      public Breakpoint.Builder suspendAnchor(SuspendAnchor anchor) {
         this.anchor = anchor;
         return this;
      }

      public Breakpoint.Builder columnIs(int column) {
         if (column <= 0) {
            throw new IllegalArgumentException("Column argument must be > 0.");
         } else if (this.line == -1) {
            throw new IllegalStateException("ColumnIs can only be called after a line is set.");
         } else {
            this.column = column;
            return this;
         }
      }

      public Breakpoint.Builder resolveListener(Breakpoint.ResolveListener resolveListener) {
         Objects.requireNonNull(resolveListener);
         if (this.resolveListener != null) {
            throw new IllegalStateException("ResolveListener can only be set once per breakpoint builder.");
         } else {
            this.resolveListener = resolveListener;
            return this;
         }
      }

      public Breakpoint.Builder ignoreCount(int ignoreCount) {
         if (ignoreCount < 0) {
            throw new IllegalArgumentException("IgnoreCount argument must be >= 0.");
         } else {
            this.ignoreCount = ignoreCount;
            return this;
         }
      }

      public Breakpoint.Builder oneShot() {
         this.oneShot = true;
         return this;
      }

      public Breakpoint.Builder sourceElements(SourceElement... sourceElements) {
         if (this.sourceElements != null) {
            throw new IllegalStateException("Step source elements can only be set once per the builder.");
         } else if (sourceElements.length == 0) {
            throw new IllegalArgumentException("At least one source element needs to be provided.");
         } else {
            this.sourceElements = sourceElements;
            return this;
         }
      }

      public Breakpoint.Builder rootInstance(DebugValue rootInstance) {
         this.rootInstance = rootInstance;
         return this;
      }

      public Breakpoint build() {
         if (this.sourceElements == null) {
            this.sourceElements = new SourceElement[]{SourceElement.STATEMENT};
         }

         BreakpointLocation location;
         if (this.sourceSection != null) {
            location = BreakpointLocation.create(this.key, this.sourceElements, this.sourceSection);
         } else {
            location = BreakpointLocation.create(this.key, this.sourceElements, this.line, this.column);
         }

         Breakpoint breakpoint = new Breakpoint(
            location, this.anchor, this.oneShot, null, this.rootInstance != null ? this.rootInstance.get() : null, this.resolveListener
         );
         breakpoint.setIgnoreCount(this.ignoreCount);
         return breakpoint;
      }
   }

   private static class ConditionalBreakNode extends Node {
      private static final Object[] EMPTY_ARRAY = new Object[0];
      private final EventContext context;
      private final Breakpoint breakpoint;
      @Node.Child
      private SetThreadSuspensionEnabledNode suspensionEnabledNode = SetThreadSuspensionEnabledNodeGen.create();
      @Node.Child
      private DirectCallNode conditionCallNode;
      @Node.Child
      private ExecutableNode conditionSnippet;
      @CompilerDirectives.CompilationFinal
      private Assumption conditionUnchanged;
      @Node.Child
      private InteropLibrary interopLibrary;

      ConditionalBreakNode(EventContext context, Breakpoint breakpoint) {
         this.context = context;
         this.breakpoint = breakpoint;
         this.conditionUnchanged = breakpoint.getConditionUnchanged();
         this.interopLibrary = InteropLibrary.getFactory().createDispatched(5);
      }

      boolean executeBreakCondition(VirtualFrame frame, Breakpoint.SessionList sessions) {
         if (this.conditionSnippet == null && this.conditionCallNode == null || !this.conditionUnchanged.isValid()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.initializeConditional(frame.materialize());
         }

         Object result;
         try {
            this.suspensionEnabledNode.execute(false, sessions);
            if (this.conditionSnippet != null) {
               result = this.conditionSnippet.execute(frame);
            } else {
               result = this.conditionCallNode.call(EMPTY_ARRAY);
            }
         } finally {
            this.suspensionEnabledNode.execute(true, sessions);
         }

         if (this.interopLibrary.isBoolean(result)) {
            try {
               return this.interopLibrary.asBoolean(result);
            } catch (UnsupportedMessageException var7) {
               throw CompilerDirectives.shouldNotReachHere(var7);
            }
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Unsupported return type " + result + " in condition.");
         }
      }

      private void initializeConditional(MaterializedFrame frame) {
         Node instrumentedNode = this.context.getInstrumentedNode();
         RootNode rootNode = instrumentedNode.getRootNode();
         if (rootNode == null) {
            throw new IllegalStateException("Probe was disconnected from the AST.");
         } else {
            Source instrumentedSource = this.context.getInstrumentedSourceSection().getSource();
            Source conditionSource;
            synchronized (this.breakpoint) {
               conditionSource = Source.newBuilder(instrumentedSource.getLanguage(), this.breakpoint.condition, "breakpoint condition")
                  .mimeType(instrumentedSource.getMimeType())
                  .build();
               if (conditionSource == null) {
                  throw new IllegalStateException("Condition is not resolved " + rootNode);
               }

               this.conditionUnchanged = this.breakpoint.getConditionUnchanged();
            }

            ExecutableNode snippet = this.breakpoint.debugger.getEnv().parseInline(conditionSource, instrumentedNode, frame);
            if (snippet != null) {
               this.conditionSnippet = this.insert(snippet);
               this.notifyInserted(snippet);
            } else {
               CallTarget callTarget = Debugger.ACCESSOR.parse(conditionSource, instrumentedNode);
               this.conditionCallNode = this.insert(Truffle.getRuntime().createDirectCallNode(callTarget));
            }
         }
      }
   }

   public final class ExceptionBuilder {
      private final boolean caught;
      private final boolean uncaught;
      private SuspensionFilter suspensionFilter;
      private SourceElement[] sourceElements;

      ExceptionBuilder(boolean caught, boolean uncaught) {
         this.caught = caught;
         this.uncaught = uncaught;
      }

      public Breakpoint.ExceptionBuilder suspensionFilter(SuspensionFilter filter) {
         this.suspensionFilter = filter;
         return this;
      }

      public Breakpoint.ExceptionBuilder sourceElements(SourceElement... sourceElements) {
         if (this.sourceElements != null) {
            throw new IllegalStateException("Step source elements can only be set once per the builder.");
         } else if (sourceElements.length == 0) {
            throw new IllegalArgumentException("At least one source element needs to be provided.");
         } else {
            this.sourceElements = (SourceElement[])sourceElements.clone();
            return this;
         }
      }

      public Breakpoint build() {
         if (this.sourceElements == null) {
            this.sourceElements = new SourceElement[]{SourceElement.STATEMENT};
         }

         BreakpointLocation location = BreakpointLocation.create(this.sourceElements, this.suspensionFilter);
         BreakpointExceptionFilter efilter = new BreakpointExceptionFilter(this.caught, this.uncaught);
         return new Breakpoint(location, SuspendAnchor.AFTER, false, efilter, null, null);
      }
   }

   static final class GlobalBreakpoint extends Breakpoint {
      private final Breakpoint delegate;

      GlobalBreakpoint(Breakpoint delegate) {
         this.delegate = delegate;
      }

      @Override
      public void dispose() {
         fail();
      }

      @Override
      public void setCondition(String expression) {
         fail();
      }

      @Override
      public void setEnabled(boolean enabled) {
         fail();
      }

      @Override
      public void setIgnoreCount(int ignoreCount) {
         fail();
      }

      private static void fail() {
         throw new IllegalStateException("Unmodifiable breakpoint.");
      }

      @Override
      public boolean isModifiable() {
         return false;
      }

      @Override
      public String getCondition() {
         return this.delegate.getCondition();
      }

      @Override
      public int getHitCount() {
         return this.delegate.getHitCount();
      }

      @Override
      public int getIgnoreCount() {
         return this.delegate.getIgnoreCount();
      }

      @Override
      public Breakpoint.Kind getKind() {
         return this.delegate.getKind();
      }

      @Override
      public String getLocationDescription() {
         return this.delegate.getLocationDescription();
      }

      @Override
      public SuspendAnchor getSuspendAnchor() {
         return this.delegate.getSuspendAnchor();
      }

      @Override
      public boolean isDisposed() {
         return this.delegate.isDisposed();
      }

      @Override
      public boolean isEnabled() {
         return this.delegate.isEnabled();
      }

      @Override
      public boolean isOneShot() {
         return this.delegate.isOneShot();
      }

      @Override
      public boolean isResolved() {
         return this.delegate.isResolved();
      }
   }

   public static enum Kind {
      HALT_INSTRUCTION,
      SOURCE_LOCATION,
      EXCEPTION;

      static final Breakpoint.Kind[] VALUES = values();
   }

   public interface ResolveListener {
      void breakpointResolved(Breakpoint breakpoint, SourceSection section);
   }

   static final class SessionList {
      final DebuggerSession session;
      final Breakpoint.SessionList next;
      final Assumption suspensionFilterUnchanged;

      SessionList(DebuggerSession session, Breakpoint.SessionList next) {
         this.session = session;
         this.suspensionFilterUnchanged = session.getSuspensionFilterUnchangedAssumption();
         this.next = next;
      }

      SessionList(Breakpoint.SessionList current, Breakpoint.SessionList next) {
         this.session = current.session;
         this.suspensionFilterUnchanged = current.suspensionFilterUnchanged;
         this.next = next;
      }

      boolean isValid() {
         if (!this.suspensionFilterUnchanged.isValid()) {
            return false;
         } else {
            return this.next != null ? this.next.isValid() : true;
         }
      }
   }
}
