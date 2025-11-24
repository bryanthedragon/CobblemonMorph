
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.debug.BreakpointExceptionFilter;
import com.oracle.truffle.api.debug.BreakpointLocation;
import com.oracle.truffle.api.debug.ChangedReturnInfo;
import com.oracle.truffle.api.debug.DebugException;
import com.oracle.truffle.api.debug.DebugValue;
import com.oracle.truffle.api.debug.Debugger;
import com.oracle.truffle.api.debug.DebuggerNode;
import com.oracle.truffle.api.debug.DebuggerSession;
import com.oracle.truffle.api.debug.SetThreadSuspensionEnabledNode;
import com.oracle.truffle.api.debug.SetThreadSuspensionEnabledNodeGen;
import com.oracle.truffle.api.debug.SourceElement;
import com.oracle.truffle.api.debug.SuppressFBWarnings;
import com.oracle.truffle.api.debug.SuspendAnchor;
import com.oracle.truffle.api.debug.SuspensionFilter;
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
    private final ResolveListener resolveListener;
    private volatile Debugger debugger;
    private final List<DebuggerSession> sessions = new LinkedList<DebuggerSession>();
    private volatile Assumption sessionsUnchanged;
    private volatile boolean enabled;
    private volatile int ignoreCount;
    private volatile boolean disposed;
    private volatile String condition;
    private volatile boolean global;
    private volatile GlobalBreakpoint roWrapper;
    private final AtomicLong hitCount = new AtomicLong();
    private volatile Assumption conditionUnchanged;
    private volatile Assumption conditionExistsUnchanged;
    private volatile EventBinding<? extends ExecutionEventNodeFactory> breakpointBinding;
    private final AtomicBoolean breakpointBindingAttaching = new AtomicBoolean(false);
    private volatile boolean breakpointBindingReady;
    private volatile Predicate<Source> sourcePredicate;
    private final AtomicReference<Object> sourceBinding = new AtomicReference();
    private static final Object SOURCE_BINDING_RESOLVED = new Object();

    Breakpoint(BreakpointLocation key, SuspendAnchor suspendAnchor) {
        this(key, suspendAnchor, false, null, null, null);
    }

    private Breakpoint(BreakpointLocation key, SuspendAnchor suspendAnchor, boolean oneShot, BreakpointExceptionFilter exceptionFilter, Object rootInstance, ResolveListener resolveListener) {
        this.locationKey = key;
        this.suspendAnchor = suspendAnchor;
        this.oneShot = oneShot;
        this.exceptionFilter = exceptionFilter;
        this.rootInstanceRef = rootInstance != null ? new WeakReference<Object>(rootInstance) : null;
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

    public Kind getKind() {
        if (this.locationKey == null) {
            return Kind.HALT_INSTRUCTION;
        }
        if (this.exceptionFilter == null) {
            return Kind.SOURCE_LOCATION;
        }
        return Kind.EXCEPTION;
    }

    public boolean isDisposed() {
        return this.disposed;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setEnabled(boolean enabled) {
        boolean doInstall = false;
        Breakpoint breakpoint = this;
        synchronized (breakpoint) {
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
        if (existsChanged && (assumption = this.conditionExistsUnchanged) != null) {
            this.conditionExistsUnchanged = null;
            assumption.invalidate();
        }
    }

    @SuppressFBWarnings(value={"UG"})
    public String getCondition() {
        return this.condition;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dispose() {
        DebuggerSession[] breakpointSessions = null;
        Debugger breakpointDebugger = null;
        Breakpoint breakpoint = this;
        synchronized (breakpoint) {
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
            for (Breakpoint breakpoint2 : breakpointSessions) {
                ((DebuggerSession)((Object)breakpoint2)).disposeBreakpoint(this);
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
        }
        if (this.debugger != null) {
            throw new IllegalStateException("Breakpoint is already installed in a Debugger instance.");
        }
        this.install(d);
        this.global = true;
    }

    private void install(Debugger d) {
        assert (Thread.holdsLock(this));
        if (this.debugger != null && this.debugger != d) {
            throw new IllegalStateException("Breakpoint is already installed in a different Debugger instance.");
        }
        this.debugger = d;
        if (this.exceptionFilter != null) {
            this.exceptionFilter.setDebugger(d);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean install(DebuggerSession d, boolean failOnError) {
        Breakpoint breakpoint = this;
        synchronized (breakpoint) {
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
        SourceFilter filter;
        EventBinding<1> binding;
        Object obj = this.sourceBinding.get();
        EventBinding<1> eventBinding = binding = SOURCE_BINDING_RESOLVED == obj ? null : (EventBinding<1>)obj;
        if (obj == null && (filter = this.locationKey.createSourceFilter()) != null) {
            this.sourcePredicate = this.locationKey.createSourcePredicate();
            binding = this.debugger.getInstrumenter().createExecuteSourceBinding(filter, new ExecuteSourceListener(){

                @Override
                public void onExecute(ExecuteSourceEvent event) {
                    Source source = event.getSource();
                    Breakpoint.this.resolveBreakpointAssignBinding(source);
                }
            }, true);
            if (this.sourceBinding.compareAndSet(null, binding)) {
                try {
                    binding.attach();
                }
                catch (IllegalStateException ex) {
                    assert (binding.isDisposed());
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void assignBinding(SourceSectionFilter locationFilter) {
        boolean attaching = this.breakpointBindingAttaching.getAndSet(true);
        if (!attaching) {
            Breakpoint breakpoint;
            EventBinding<BreakpointNodeFactory> newBinding = null;
            Debugger dbg = this.debugger;
            if (dbg == null) {
                return;
            }
            try {
                newBinding = dbg.getInstrumenter().attachExecutionEventFactory(locationFilter, new BreakpointNodeFactory());
                this.breakpointBinding = newBinding;
                this.breakpointBindingAttaching.set(false);
                breakpoint = this;
            }
            catch (Throwable throwable) {
                this.breakpointBindingAttaching.set(false);
                Breakpoint breakpoint2 = this;
                synchronized (breakpoint2) {
                    if (newBinding != null) {
                        this.getAndSetSourceBinding(SOURCE_BINDING_RESOLVED);
                        for (DebuggerSession s : this.sessions) {
                            s.allBindings.add(newBinding);
                        }
                    }
                    this.breakpointBindingReady = true;
                    this.notifyAll();
                }
                throw throwable;
            }
            synchronized (breakpoint) {
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

    boolean isGlobal() {
        return this.global;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void sessionClosed(DebuggerSession d) {
        boolean doUninstall;
        Breakpoint breakpoint = this;
        synchronized (breakpoint) {
            this.sessions.remove(d);
            this.sessionsAssumptionInvalidate();
            doUninstall = this.sessions.isEmpty();
        }
        if (doUninstall) {
            this.uninstall();
        }
    }

    Assumption getSessionsUnchanged() {
        assert (Thread.holdsLock(this));
        Assumption sessionsLocal = this.sessionsUnchanged;
        if (sessionsLocal == null) {
            this.sessionsUnchanged = sessionsLocal = Truffle.getRuntime().createAssumption();
        }
        return sessionsLocal;
    }

    private void sessionsAssumptionInvalidate() {
        assert (Thread.holdsLock(this));
        Assumption assumption = this.sessionsUnchanged;
        if (assumption != null) {
            this.sessionsUnchanged = null;
            assumption.invalidate();
        }
    }

    private void resolveBreakpoint(SourceSection resolvedLocation) {
        this.resolveBreakpoint(resolvedLocation, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void resolveBreakpoint(SourceSection resolvedLocation, boolean notifyResolved) {
        boolean doNotifyResolved = notifyResolved;
        Breakpoint breakpoint = this;
        synchronized (breakpoint) {
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void uninstall() {
        EventBinding<? extends ExecutionEventNodeFactory> binding;
        Breakpoint breakpoint = this;
        synchronized (breakpoint) {
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

    boolean notifyIndirectHit(EventContext context, DebuggerNode source, DebuggerNode node, MaterializedFrame frame, DebugException exception) throws BreakpointConditionFailure {
        if (!this.isEnabled()) {
            return false;
        }
        assert (node.getBreakpoint() == this);
        if (source != node) {
            AbstractBreakpointNode breakpointNode;
            Node contextNode;
            NodeLibrary contextNodeLibrary;
            Object rootInstance;
            if (this.rootInstanceRef != null && (rootInstance = this.rootInstanceRef.get()) != null && (contextNodeLibrary = NodeLibrary.getUncached(contextNode = context.getInstrumentedNode())).hasRootInstance(contextNode, frame)) {
                try {
                    if (rootInstance != contextNodeLibrary.getRootInstance(contextNode, frame)) {
                        return false;
                    }
                }
                catch (UnsupportedMessageException e) {
                    throw CompilerDirectives.shouldNotReachHere(e);
                }
            }
            if (!(breakpointNode = (AbstractBreakpointNode)node).testCondition(frame)) {
                return false;
            }
            if (this.exceptionFilter != null && exception != null) {
                Throwable throwable = exception.getRawException();
                assert (throwable != null);
                BreakpointExceptionFilter.Match matched = this.exceptionFilter.matchException(node, throwable);
                if (!matched.isMatched) {
                    return false;
                }
            }
            if (this.hitCount.incrementAndGet() <= (long)this.ignoreCount) {
                return false;
            }
        }
        if (this.isOneShot()) {
            this.setEnabled(false);
        }
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    private Object doBreak(EventContext context, DebuggerNode source, SessionList breakInSessions, boolean activeOnNoninternalCalls, MaterializedFrame frame, boolean onEnter, Object result, Throwable exception, BreakpointConditionFailure failure) {
        return this.doBreak(context, source, breakInSessions, activeOnNoninternalCalls, frame, onEnter, result, exception, source, false, null, failure);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private Object doBreak(EventContext context, DebuggerNode source, SessionList breakInSessions, boolean activeOnNoninternalCalls, MaterializedFrame frame, boolean onEnter, Object result, Throwable exception, Node throwLocation, boolean isCatchNodeComputed, DebugException.CatchLocation catchLocation, BreakpointConditionFailure failure) {
        if (!this.isEnabled()) {
            return result;
        }
        if (this.hitCount.incrementAndGet() <= (long)this.ignoreCount) {
            return result;
        }
        SuspendAnchor anchor = onEnter ? SuspendAnchor.BEFORE : SuspendAnchor.AFTER;
        Object newResult = result;
        SessionList current = breakInSessions;
        while (current != null) {
            DebuggerSession session = current.session;
            if (session.isBreakpointsActive(this.getKind())) {
                boolean internalCompliant = true;
                DebuggerSession.Caller caller = null;
                if (activeOnNoninternalCalls && !session.isIncludeInternal()) {
                    caller = DebuggerSession.findCurrentCaller(session, true);
                    boolean bl = internalCompliant = caller != null && !caller.node.getRootNode().isInternal();
                }
                if (internalCompliant) {
                    Breakpoint breakpoint = this;
                    synchronized (breakpoint) {
                        while (this.breakpointBinding != null && !this.breakpointBindingReady) {
                            try {
                                this.wait();
                            }
                            catch (InterruptedException interruptedException) {}
                        }
                    }
                    DebugException de = exception != null ? DebugException.create(session, exception, null, throwLocation, isCatchNodeComputed, catchLocation) : null;
                    newResult = caller != null ? session.notifyAtCaller(context, caller, null, source, anchor, newResult, de, failure) : session.notifyCallback(context, source, frame, anchor, null, newResult, de, failure);
                    session.restoreSteppingOnCurrentThread();
                }
            }
            current = current.next;
        }
        return newResult;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Breakpoint getROWrapper() {
        assert (this.global);
        GlobalBreakpoint wrapper = this.roWrapper;
        if (wrapper == null) {
            Breakpoint breakpoint = this;
            synchronized (breakpoint) {
                wrapper = this.roWrapper;
                if (wrapper == null) {
                    this.roWrapper = wrapper = new GlobalBreakpoint(this);
                }
            }
        }
        return wrapper;
    }

    Object getRootInstance() {
        return this.rootInstanceRef != null ? this.rootInstanceRef.get() : null;
    }

    public static Builder newBuilder(URI sourceUri) {
        Breakpoint breakpoint = BUILDER_INSTANCE;
        Objects.requireNonNull(breakpoint);
        return breakpoint.new Builder(sourceUri);
    }

    public static Builder newBuilder(Source source) {
        Breakpoint breakpoint = BUILDER_INSTANCE;
        Objects.requireNonNull(breakpoint);
        return breakpoint.new Builder(source);
    }

    public static Builder newBuilder(SourceSection sourceSection) {
        Breakpoint breakpoint = BUILDER_INSTANCE;
        Objects.requireNonNull(breakpoint);
        return breakpoint.new Builder(sourceSection);
    }

    public static ExceptionBuilder newExceptionBuilder(boolean caught, boolean uncaught) {
        if (!caught && !uncaught) {
            throw new IllegalArgumentException("At least one of 'caught' or 'uncaught' needs to be true.");
        }
        Breakpoint breakpoint = BUILDER_INSTANCE;
        Objects.requireNonNull(breakpoint);
        return breakpoint.new ExceptionBuilder(caught, uncaught);
    }

    @CompilerDirectives.TruffleBoundary
    private static SessionList removeDuplicateSession(SessionList sessions, DebuggerSession session) {
        SessionList current = sessions;
        boolean foundSession = false;
        while (current != null) {
            if (session == current.session) {
                foundSession = true;
                break;
            }
            current = current.next;
        }
        if (foundSession) {
            SessionList newSessions = null;
            current = sessions;
            while (current != null) {
                if (session != current.session) {
                    newSessions = new SessionList(current, newSessions);
                }
                current = current.next;
            }
            return newSessions;
        }
        return sessions;
    }

    static final class GlobalBreakpoint
    extends Breakpoint {
        private final Breakpoint delegate;

        GlobalBreakpoint(Breakpoint delegate) {
            this.delegate = delegate;
        }

        @Override
        public void dispose() {
            GlobalBreakpoint.fail();
        }

        @Override
        public void setCondition(String expression) {
            GlobalBreakpoint.fail();
        }

        @Override
        public void setEnabled(boolean enabled) {
            GlobalBreakpoint.fail();
        }

        @Override
        public void setIgnoreCount(int ignoreCount) {
            GlobalBreakpoint.fail();
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
        public Kind getKind() {
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

    static final class SessionList {
        final DebuggerSession session;
        final SessionList next;
        final Assumption suspensionFilterUnchanged;

        SessionList(DebuggerSession session, SessionList next) {
            this.session = session;
            this.suspensionFilterUnchanged = session.getSuspensionFilterUnchangedAssumption();
            this.next = next;
        }

        SessionList(SessionList current, SessionList next) {
            this.session = current.session;
            this.suspensionFilterUnchanged = current.suspensionFilterUnchanged;
            this.next = next;
        }

        boolean isValid() {
            if (!this.suspensionFilterUnchanged.isValid()) {
                return false;
            }
            if (this.next != null) {
                return this.next.isValid();
            }
            return true;
        }
    }

    private static class ConditionalBreakNode
    extends Node {
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

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean executeBreakCondition(VirtualFrame frame, SessionList sessions) {
            Object result;
            if (this.conditionSnippet == null && this.conditionCallNode == null || !this.conditionUnchanged.isValid()) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.initializeConditional(frame.materialize());
            }
            try {
                this.suspensionEnabledNode.execute(false, sessions);
                result = this.conditionSnippet != null ? this.conditionSnippet.execute(frame) : this.conditionCallNode.call(EMPTY_ARRAY);
            }
            finally {
                this.suspensionEnabledNode.execute(true, sessions);
            }
            if (this.interopLibrary.isBoolean(result)) {
                try {
                    return this.interopLibrary.asBoolean(result);
                }
                catch (UnsupportedMessageException e) {
                    throw CompilerDirectives.shouldNotReachHere(e);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Unsupported return type " + result + " in condition.");
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void initializeConditional(MaterializedFrame frame) {
            Source conditionSource;
            Node instrumentedNode = this.context.getInstrumentedNode();
            RootNode rootNode = instrumentedNode.getRootNode();
            if (rootNode == null) {
                throw new IllegalStateException("Probe was disconnected from the AST.");
            }
            Source instrumentedSource = this.context.getInstrumentedSourceSection().getSource();
            Breakpoint breakpoint = this.breakpoint;
            synchronized (breakpoint) {
                conditionSource = Source.newBuilder(instrumentedSource.getLanguage(), this.breakpoint.condition, "breakpoint condition").mimeType(instrumentedSource.getMimeType()).build();
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
                CallTarget callTarget = Debugger.ACCESSOR.parse(conditionSource, instrumentedNode, new String[0]);
                this.conditionCallNode = this.insert(Truffle.getRuntime().createDirectCallNode(callTarget));
            }
        }
    }

    static final class BreakpointConditionFailure
    extends SlowPathException {
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

    private static abstract class AbstractBreakpointNode
    extends DebuggerNode {
        private final Breakpoint breakpoint;
        protected final BranchProfile breakBranch = BranchProfile.create();
        @Node.Child
        private NodeLibrary contextNodeLibrary;
        @Node.Child
        private ConditionalBreakNode breakCondition;
        @CompilerDirectives.CompilationFinal
        private Assumption conditionExistsUnchanged;
        @CompilerDirectives.CompilationFinal
        protected boolean activeOnNoninternalCalls;
        @CompilerDirectives.CompilationFinal
        private SessionList sessionList;
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
                this.breakCondition = new ConditionalBreakNode(context, breakpoint);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private SessionList initializeSessions() {
            CompilerAsserts.neverPartOfCompilation();
            Breakpoint breakpoint = this.breakpoint;
            synchronized (breakpoint) {
                SourceSection sourceSection;
                boolean inInternalCode = this.context.getInstrumentedNode().getRootNode().isInternal();
                if (inInternalCode && (this.breakpoint.locationKey == null || this.breakpoint.locationKey.containsRoot()) && this.context.hasTag(SourceElement.ROOT.getTag())) {
                    this.activeOnNoninternalCalls = true;
                }
                Source inSource = (sourceSection = this.context.getInstrumentedSourceSection()) != null ? sourceSection.getSource() : null;
                SessionList listEntry = null;
                List<DebuggerSession> allSesssions = this.breakpoint.sessions;
                boolean inactiveInInternal = inInternalCode && !this.activeOnNoninternalCalls;
                for (int i = allSesssions.size() - 1; i >= 0; --i) {
                    DebuggerSession session = allSesssions.get(i);
                    if (inactiveInInternal && !session.isIncludeInternal() || inSource != null && session.isSourceFilteredOut(inSource)) continue;
                    listEntry = new SessionList(session, listEntry);
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
            Object rootInstance;
            SessionList sessions = this.computeUniqueActiveSessions();
            if (sessions == null) {
                return result;
            }
            if (this.breakpoint.rootInstanceRef != null && (rootInstance = this.breakpoint.rootInstanceRef.get()) != null && !this.testRootInstance(rootInstance, frame)) {
                return result;
            }
            BreakpointConditionFailure conditionError = null;
            try {
                if (!this.testCondition(frame)) {
                    return result;
                }
            }
            catch (BreakpointConditionFailure e) {
                conditionError = e;
            }
            this.breakBranch.enter();
            return this.breakpoint.doBreak(this.context, this, sessions, this.activeOnNoninternalCalls, frame.materialize(), onEnter, result, exception, conditionError);
        }

        private boolean testRootInstance(Object rootInstance, VirtualFrame frame) {
            if (this.contextNodeLibrary.hasRootInstance(this.context.getInstrumentedNode(), frame)) {
                try {
                    if (rootInstance != this.contextNodeLibrary.getRootInstance(this.context.getInstrumentedNode(), frame)) {
                        return false;
                    }
                }
                catch (UnsupportedMessageException e) {
                    throw CompilerDirectives.shouldNotReachHere(e);
                }
            }
            return true;
        }

        @ExplodeLoop
        protected final SessionList computeUniqueActiveSessions() {
            SessionList sessions = this.getSessions();
            boolean active = false;
            SessionList current = sessions;
            boolean duplicate = false;
            while (current != null) {
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
                current = current.next;
            }
            if (!active) {
                return null;
            }
            return sessions;
        }

        final SessionList getSessions() {
            SessionList sessions = this.sessionList;
            Assumption localSessionsUnchanged = this.sessionsUnchanged;
            if (localSessionsUnchanged == null || !localSessionsUnchanged.isValid() || sessions != null && !sessions.isValid()) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                sessions = this.initializeSessions();
            }
            return sessions;
        }

        boolean testCondition(VirtualFrame frame) throws BreakpointConditionFailure {
            SessionList localSessions;
            ConditionalBreakNode conditionNode = this.breakCondition;
            if (!this.conditionExistsUnchanged.isValid()) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                if (this.breakpoint.condition != null) {
                    this.breakCondition = conditionNode = this.insert(new ConditionalBreakNode(this.context, this.breakpoint));
                    this.notifyInserted(conditionNode);
                } else {
                    conditionNode = null;
                    this.breakCondition = null;
                }
                this.conditionExistsUnchanged = this.breakpoint.getConditionExistsUnchanged();
            }
            if ((localSessions = this.getSessions()) == null) {
                return false;
            }
            if (conditionNode != null) {
                try {
                    return conditionNode.executeBreakCondition(frame, localSessions);
                }
                catch (Throwable e) {
                    CompilerDirectives.transferToInterpreter();
                    throw new BreakpointConditionFailure(this.breakpoint, e);
                }
            }
            return true;
        }
    }

    private static class BreakpointAfterNodeException
    extends AbstractBreakpointNode {
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
                SessionList sessions = this.computeUniqueActiveSessions();
                if (sessions == null) {
                    return;
                }
                BreakpointExceptionFilter.Match matched = this.getBreakpoint().exceptionFilter.matchException(this.getContext().getInstrumentedNode(), exception);
                if (matched.isMatched) {
                    BreakpointConditionFailure conditionError = null;
                    try {
                        if (!this.testCondition(frame)) {
                            return;
                        }
                    }
                    catch (BreakpointConditionFailure e) {
                        conditionError = e;
                    }
                    this.breakBranch.enter();
                    this.doBreak(frame.materialize(), sessions, conditionError, exception, matched);
                }
            }
        }

        @CompilerDirectives.TruffleBoundary
        void doBreak(MaterializedFrame frame, SessionList debuggerSessions, BreakpointConditionFailure conditionError, Throwable exception, BreakpointExceptionFilter.Match matched) {
            Node throwLocation = this.getContext().getInstrumentedNode();
            this.getBreakpoint().doBreak(this.getContext(), this, debuggerSessions, this.activeOnNoninternalCalls, frame, false, null, exception, throwLocation, matched.isCatchNodeComputed, matched.catchLocation, conditionError);
        }
    }

    private static class BreakpointAfterNode
    extends AbstractBreakpointNode {
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

    private static class BreakpointBeforeNode
    extends AbstractBreakpointNode {
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

    private class BreakpointNodeFactory
    implements ExecutionEventNodeFactory {
        private BreakpointNodeFactory() {
        }

        @Override
        public ExecutionEventNode create(EventContext context) {
            if (!Breakpoint.this.isResolved()) {
                Breakpoint.this.resolveBreakpoint(context.getInstrumentedSourceSection());
            }
            if (Breakpoint.this.exceptionFilter != null) {
                return new BreakpointAfterNodeException(Breakpoint.this, context);
            }
            switch (Breakpoint.this.suspendAnchor) {
                case BEFORE: {
                    return new BreakpointBeforeNode(Breakpoint.this, context);
                }
                case AFTER: {
                    return new BreakpointAfterNode(Breakpoint.this, context);
                }
            }
            throw new IllegalStateException("Unknown suspend anchor: " + Breakpoint.this.suspendAnchor);
        }
    }

    public static interface ResolveListener {
        public void breakpointResolved(Breakpoint var1, SourceSection var2);
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

        public ExceptionBuilder suspensionFilter(SuspensionFilter filter) {
            this.suspensionFilter = filter;
            return this;
        }

        public ExceptionBuilder sourceElements(SourceElement ... sourceElements) {
            if (this.sourceElements != null) {
                throw new IllegalStateException("Step source elements can only be set once per the builder.");
            }
            if (sourceElements.length == 0) {
                throw new IllegalArgumentException("At least one source element needs to be provided.");
            }
            this.sourceElements = (SourceElement[])sourceElements.clone();
            return this;
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

    public final class Builder {
        private final Object key;
        private int line = -1;
        private SuspendAnchor anchor = SuspendAnchor.BEFORE;
        private int column = -1;
        private ResolveListener resolveListener;
        private int ignoreCount;
        private boolean oneShot;
        private DebugValue rootInstance;
        private SourceSection sourceSection;
        private SourceElement[] sourceElements;

        private Builder(Object key) {
            this.key = key == null ? BreakpointLocation.ANY_SOURCE : key;
        }

        private Builder(SourceSection key) {
            this(key.getSource());
            Objects.requireNonNull(key);
            this.sourceSection = key;
        }

        public Builder lineIs(int line) {
            if (line <= 0) {
                throw new IllegalArgumentException("Line argument must be > 0.");
            }
            if (this.line != -1) {
                throw new IllegalStateException("LineIs can only be called once per breakpoint builder.");
            }
            if (this.sourceSection != null) {
                throw new IllegalArgumentException("LineIs cannot be used with source section based breakpoint. ");
            }
            this.line = line;
            return this;
        }

        public Builder suspendAnchor(SuspendAnchor anchor) {
            this.anchor = anchor;
            return this;
        }

        public Builder columnIs(int column) {
            if (column <= 0) {
                throw new IllegalArgumentException("Column argument must be > 0.");
            }
            if (this.line == -1) {
                throw new IllegalStateException("ColumnIs can only be called after a line is set.");
            }
            this.column = column;
            return this;
        }

        public Builder resolveListener(ResolveListener resolveListener) {
            Objects.requireNonNull(resolveListener);
            if (this.resolveListener != null) {
                throw new IllegalStateException("ResolveListener can only be set once per breakpoint builder.");
            }
            this.resolveListener = resolveListener;
            return this;
        }

        public Builder ignoreCount(int ignoreCount) {
            if (ignoreCount < 0) {
                throw new IllegalArgumentException("IgnoreCount argument must be >= 0.");
            }
            this.ignoreCount = ignoreCount;
            return this;
        }

        public Builder oneShot() {
            this.oneShot = true;
            return this;
        }

        public Builder sourceElements(SourceElement ... sourceElements) {
            if (this.sourceElements != null) {
                throw new IllegalStateException("Step source elements can only be set once per the builder.");
            }
            if (sourceElements.length == 0) {
                throw new IllegalArgumentException("At least one source element needs to be provided.");
            }
            this.sourceElements = sourceElements;
            return this;
        }

        public Builder rootInstance(DebugValue rootInstance) {
            this.rootInstance = rootInstance;
            return this;
        }

        public Breakpoint build() {
            if (this.sourceElements == null) {
                this.sourceElements = new SourceElement[]{SourceElement.STATEMENT};
            }
            BreakpointLocation location = this.sourceSection != null ? BreakpointLocation.create(this.key, this.sourceElements, this.sourceSection) : BreakpointLocation.create(this.key, this.sourceElements, this.line, this.column);
            Breakpoint breakpoint = new Breakpoint(location, this.anchor, this.oneShot, null, this.rootInstance != null ? this.rootInstance.get() : null, this.resolveListener);
            breakpoint.setIgnoreCount(this.ignoreCount);
            return breakpoint;
        }
    }

    public static enum Kind {
        HALT_INSTRUCTION,
        SOURCE_LOCATION,
        EXCEPTION;

        static final Kind[] VALUES;

        static {
            VALUES = Kind.values();
        }
    }
}

