package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
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
   final List<Object> propSupport = new CopyOnWriteArrayList<>();
   private final List<Consumer<Breakpoint>> breakpointAddedListeners = new CopyOnWriteArrayList<>();
   private final List<Consumer<Breakpoint>> breakpointRemovedListeners = new CopyOnWriteArrayList<>();
   private final Set<DebuggerSession> sessions = new HashSet<>();
   private final List<Breakpoint> breakpoints = new ArrayList<>();
   final Breakpoint alwaysHaltBreakpoint;
   private final ThreadLocal<Integer> disabledSteppingCount = new ThreadLocal<Integer>() {
      protected Integer initialValue() {
         return 0;
      }
   };
   static final Debugger.AccessorDebug ACCESSOR = new Debugger.AccessorDebug();

   Debugger(TruffleInstrument.Env env) {
      this.env = env;
      this.alwaysHaltBreakpoint = new Breakpoint(BreakpointLocation.ANY, SuspendAnchor.BEFORE);
      this.alwaysHaltBreakpoint.setEnabled(true);
   }

   public DebuggerSession startSession(SuspendedCallback callback) {
      return this.startSession(callback, SourceElement.STATEMENT);
   }

   public DebuggerSession startSession(SuspendedCallback callback, SourceElement... defaultSourceElements) {
      DebuggerSession session = new DebuggerSession(this, callback, defaultSourceElements);
      Breakpoint[] bpts;
      synchronized (this) {
         this.sessions.add(session);
         bpts = this.breakpoints.toArray(new Breakpoint[0]);
      }

      for (Breakpoint b : bpts) {
         session.install(b, true);
      }

      session.install(this.alwaysHaltBreakpoint, true);
      return session;
   }

   public synchronized int getSessionCount() {
      return this.sessions.size();
   }

   void disposedSession(DebuggerSession session) {
      synchronized (this) {
         this.sessions.remove(session);

         for (Breakpoint b : this.breakpoints) {
            b.sessionClosed(session);
         }

         this.alwaysHaltBreakpoint.sessionClosed(session);
      }
   }

   public Breakpoint install(Breakpoint breakpoint) {
      if (breakpoint.isDisposed()) {
         throw new IllegalArgumentException("Cannot install breakpoint, it is already disposed.");
      } else {
         breakpoint.installGlobal(this);
         DebuggerSession[] ds;
         synchronized (this) {
            this.breakpoints.add(breakpoint);
            ds = this.sessions.toArray(new DebuggerSession[0]);
         }

         for (DebuggerSession s : ds) {
            s.install(breakpoint, true);
         }

         for (Consumer<Breakpoint> listener : this.breakpointAddedListeners) {
            listener.accept(breakpoint.getROWrapper());
         }

         if (TRACE) {
            trace("installed debugger breakpoint %s", breakpoint);
         }

         return breakpoint;
      }
   }

   public List<Breakpoint> getBreakpoints() {
      List<Breakpoint> bpts;
      synchronized (this) {
         bpts = new ArrayList<>(this.breakpoints.size());

         for (Breakpoint b : this.breakpoints) {
            bpts.add(b.getROWrapper());
         }
      }

      return Collections.unmodifiableList(bpts);
   }

   List<Breakpoint> getRawBreakpoints() {
      return this.breakpoints;
   }

   void disposeBreakpoint(Breakpoint breakpoint) {
      boolean removed;
      synchronized (this) {
         removed = this.breakpoints.remove(breakpoint);
      }

      if (removed) {
         for (Consumer<Breakpoint> listener : this.breakpointRemovedListeners) {
            listener.accept(breakpoint.getROWrapper());
         }
      }

      if (TRACE) {
         trace("disposed debugger breakpoint %s", breakpoint);
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
      } else {
         int count = this.disabledSteppingCount.get();
         this.disabledSteppingCount.set(count + 1);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void restoreStepping() {
      if (this.env.getEnteredContext() == null) {
         throw new IllegalStateException("Need to be called on a context thread");
      } else {
         int count = this.disabledSteppingCount.get();
         if (count == 0) {
            throw new IllegalStateException("restoreStepping() called without a corresponding disabledStepping()");
         } else {
            synchronized (this) {
               for (DebuggerSession session : this.sessions) {
                  session.clearDisabledSteppingOnCurrentThread(count);
               }
            }

            this.disabledSteppingCount.set(--count);
         }
      }
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

   static void trace(String message, Object... parameters) {
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
      return new DebuggerInstrument.DebuggerFactory() {
         @Override
         public Debugger create(TruffleInstrument.Env env) {
            return new Debugger(env);
         }
      };
   }

   static final class AccessorDebug extends Accessor {
      protected CallTarget parse(Source code, Node context, String... argumentNames) {
         RootNode rootNode = context.getRootNode();
         return this.languageSupport().parse(this.engineSupport().getEnvForInstrument(rootNode.getLanguageInfo()), code, context, argumentNames);
      }

      protected Object evalInContext(Source source, Node node, MaterializedFrame frame) {
         return this.languageSupport().evalInContext(source, node, frame);
      }
   }
}
