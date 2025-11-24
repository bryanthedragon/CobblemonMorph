
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.debug.Breakpoint;
import com.oracle.truffle.api.debug.BreakpointLocation;
import com.oracle.truffle.api.debug.DebuggerSession;
import com.oracle.truffle.api.debug.SourceElement;
import com.oracle.truffle.api.debug.SuspendAnchor;
import com.oracle.truffle.api.debug.SuspendedCallback;
import com.oracle.truffle.api.debug.impl.DebuggerInstrument;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.instrumentation.Instrumenter;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import org.graalvm.polyglot.Engine;

public final class Debugger {
    static final boolean TRACE = Boolean.getBoolean("truffle.debug.trace");
    private final TruffleInstrument.Env env;
    final List<Object> propSupport = new CopyOnWriteArrayList<Object>();
    private final List<Consumer<Breakpoint>> breakpointAddedListeners = new CopyOnWriteArrayList<Consumer<Breakpoint>>();
    private final List<Consumer<Breakpoint>> breakpointRemovedListeners = new CopyOnWriteArrayList<Consumer<Breakpoint>>();
    private final Set<DebuggerSession> sessions = new HashSet<DebuggerSession>();
    private final List<Breakpoint> breakpoints = new ArrayList<Breakpoint>();
    final Breakpoint alwaysHaltBreakpoint;
    private final ThreadLocal<Integer> disabledSteppingCount = new ThreadLocal<Integer>(){

        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
    static final AccessorDebug ACCESSOR = new AccessorDebug();

    Debugger(TruffleInstrument.Env env) {
        this.env = env;
        this.alwaysHaltBreakpoint = new Breakpoint(BreakpointLocation.ANY, SuspendAnchor.BEFORE);
        this.alwaysHaltBreakpoint.setEnabled(true);
    }

    public DebuggerSession startSession(SuspendedCallback callback) {
        return this.startSession(callback, SourceElement.STATEMENT);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DebuggerSession startSession(SuspendedCallback callback, SourceElement ... defaultSourceElements) {
        DebuggerSession session = new DebuggerSession((Debugger)this, callback, defaultSourceElements);
        Breakpoint[] breakpointArray = this;
        synchronized (this) {
            this.sessions.add(session);
            Breakpoint[] bpts = this.breakpoints.toArray(new Breakpoint[0]);
            // ** MonitorExit[var5_4] (shouldn't be in output)
            for (Breakpoint b : bpts) {
                session.install(b, true);
            }
            session.install(this.alwaysHaltBreakpoint, true);
            return session;
        }
    }

    public synchronized int getSessionCount() {
        return this.sessions.size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void disposedSession(DebuggerSession session) {
        Debugger debugger = this;
        synchronized (debugger) {
            this.sessions.remove(session);
            for (Breakpoint b : this.breakpoints) {
                b.sessionClosed(session);
            }
            this.alwaysHaltBreakpoint.sessionClosed(session);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Breakpoint install(Breakpoint breakpoint) {
        if (breakpoint.isDisposed()) {
            throw new IllegalArgumentException("Cannot install breakpoint, it is already disposed.");
        }
        breakpoint.installGlobal((Debugger)this);
        DebuggerSession[] debuggerSessionArray = this;
        synchronized (this) {
            this.breakpoints.add(breakpoint);
            DebuggerSession[] ds = this.sessions.toArray(new DebuggerSession[0]);
            // ** MonitorExit[debuggerSessionArray] (shouldn't be in output)
            for (DebuggerSession s : ds) {
                s.install(breakpoint, true);
            }
            for (Consumer consumer : this.breakpointAddedListeners) {
                consumer.accept(breakpoint.getROWrapper());
            }
            if (TRACE) {
                Debugger.trace("installed debugger breakpoint %s", breakpoint);
            }
            return breakpoint;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Breakpoint> getBreakpoints() {
        ArrayList<Breakpoint> bpts;
        Debugger debugger = this;
        synchronized (debugger) {
            bpts = new ArrayList<Breakpoint>(this.breakpoints.size());
            for (Breakpoint b : this.breakpoints) {
                bpts.add(b.getROWrapper());
            }
        }
        return Collections.unmodifiableList(bpts);
    }

    List<Breakpoint> getRawBreakpoints() {
        return this.breakpoints;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void disposeBreakpoint(Breakpoint breakpoint) {
        boolean removed;
        Debugger debugger = this;
        synchronized (debugger) {
            removed = this.breakpoints.remove(breakpoint);
        }
        if (removed) {
            for (Consumer consumer : this.breakpointRemovedListeners) {
                consumer.accept(breakpoint.getROWrapper());
            }
        }
        if (TRACE) {
            Debugger.trace("disposed debugger breakpoint %s", breakpoint);
        }
    }

    public void addBreakpointAddedListener(Consumer<Breakpoint> listener) {
        this.breakpointAddedListeners.add(listener);
    }

    public void removeBreakpointAddedListener(Consumer<Breakpoint> listener) {
        this.breakpointAddedListeners.remove(listener);
    }

    public void addBreakpointRemovedListener(Consumer<Breakpoint> listener) {
        this.breakpointRemovedListeners.add(listener);
    }

    public void removeBreakpointRemovedListener(Consumer<Breakpoint> listener) {
        this.breakpointRemovedListeners.remove(listener);
    }

    @CompilerDirectives.TruffleBoundary
    public void disableStepping() {
        if (this.env.getEnteredContext() == null) {
            throw new IllegalStateException("Need to be called on a context thread");
        }
        int count = this.disabledSteppingCount.get();
        this.disabledSteppingCount.set(count + 1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    public void restoreStepping() {
        if (this.env.getEnteredContext() == null) {
            throw new IllegalStateException("Need to be called on a context thread");
        }
        int count = this.disabledSteppingCount.get();
        if (count == 0) {
            throw new IllegalStateException("restoreStepping() called without a corresponding disabledStepping()");
        }
        Debugger debugger = this;
        synchronized (debugger) {
            for (DebuggerSession session : this.sessions) {
                session.clearDisabledSteppingOnCurrentThread(count);
            }
        }
        this.disabledSteppingCount.set(--count);
    }

    int getSteppingDisabledCount() {
        return this.disabledSteppingCount.get();
    }

    TruffleInstrument.Env getEnv() {
        return this.env;
    }

    Instrumenter getInstrumenter() {
        return this.env.getInstrumenter();
    }

    static void trace(String message, Object ... parameters) {
        if (TRACE) {
            PrintStream out = System.out;
            out.println("Debugger: " + String.format(message, parameters));
        }
    }

    public static Debugger find(TruffleInstrument.Env env) {
        return env.lookup(env.getInstruments().get("debugger"), Debugger.class);
    }

    public static Debugger find(Engine engine) {
        return engine.getInstruments().get("debugger").lookup(Debugger.class);
    }

    public static Debugger find(TruffleLanguage.Env env) {
        return env.lookup(env.getInstruments().get("debugger"), Debugger.class);
    }

    static DebuggerInstrument.DebuggerFactory createFactory() {
        return new DebuggerInstrument.DebuggerFactory(){

            @Override
            public Debugger create(TruffleInstrument.Env env) {
                return new Debugger(env);
            }
        };
    }

    static final class AccessorDebug
    extends Accessor {
        AccessorDebug() {
        }

        protected CallTarget parse(Source code, Node context, String ... argumentNames) {
            RootNode rootNode = context.getRootNode();
            return this.languageSupport().parse(this.engineSupport().getEnvForInstrument(rootNode.getLanguageInfo()), code, context, argumentNames);
        }

        protected Object evalInContext(Source source, Node node, MaterializedFrame frame) {
            return this.languageSupport().evalInContext(source, node, frame);
        }
    }
}

