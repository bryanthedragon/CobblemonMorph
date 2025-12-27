package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.instrumentation.ContextsListener;
import com.oracle.truffle.api.instrumentation.EventBinding;
import com.oracle.truffle.api.instrumentation.Instrumenter;
import com.oracle.truffle.api.instrumentation.ThreadsListener;
import com.oracle.truffle.api.nodes.LanguageInfo;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

final class DebuggerExecutionLifecycle implements ContextsListener, ThreadsListener {
   private final DebuggerSession session;
   private final Instrumenter lifecycleInstrumenter;
   private EventBinding<ContextsListener> contextsBinding;
   private volatile DebugContextsListener contextsListener;
   private EventBinding<ThreadsListener> threadsBinding;
   private volatile DebugThreadsListener threadsListener;
   private final Map<TruffleContext, DebugContext> contextMap = new ConcurrentHashMap<>();

   DebuggerExecutionLifecycle(DebuggerSession session) {
      this.session = session;
      this.lifecycleInstrumenter = session.getDebugger().getEnv().getInstrumenter();
   }

   synchronized void setContextsListener(DebugContextsListener listener, boolean includeExistingContexts) {
      if (this.contextsBinding != null) {
         this.contextsBinding.dispose();
      }

      this.contextsListener = listener;
      if (listener != null) {
         this.contextsBinding = this.lifecycleInstrumenter.attachContextsListener(this, includeExistingContexts);
      } else {
         this.contextsBinding = null;
         if (this.threadsBinding == null) {
            this.contextMap.clear();
         }
      }
   }

   synchronized void setThreadsListener(DebugThreadsListener listener, boolean includeExistingThreads) {
      if (this.threadsBinding != null) {
         this.threadsBinding.dispose();
      }

      this.threadsListener = listener;
      if (listener != null) {
         this.threadsBinding = this.lifecycleInstrumenter.attachThreadsListener(this, includeExistingThreads);
      } else {
         this.threadsBinding = null;
         if (this.contextsBinding == null) {
            this.contextMap.clear();
         }
      }
   }

   DebugContext getCachedDebugContext(TruffleContext context) {
      return this.contextMap.computeIfAbsent(context, new Function<TruffleContext, DebugContext>() {
         public DebugContext apply(TruffleContext c) {
            return new DebugContext(DebuggerExecutionLifecycle.this, c);
         }
      });
   }

   Debugger getDebugger() {
      return this.session.getDebugger();
   }

   DebuggerSession getSession() {
      return this.session;
   }

   @Override
   public void onContextCreated(TruffleContext context) {
      DebugContextsListener l = this.contextsListener;
      if (l != null) {
         DebugContext dc = this.getCachedDebugContext(context);
         l.contextCreated(dc);
      }
   }

   @Override
   public void onLanguageContextCreated(TruffleContext context, LanguageInfo language) {
      DebugContextsListener l = this.contextsListener;
      if (l != null) {
         DebugContext dc = this.getCachedDebugContext(context);
         l.languageContextCreated(dc, language);
      }
   }

   @Override
   public void onLanguageContextInitialized(TruffleContext context, LanguageInfo language) {
      DebugContextsListener l = this.contextsListener;
      if (l != null) {
         DebugContext dc = this.getCachedDebugContext(context);
         l.languageContextInitialized(dc, language);
      }
   }

   @Override
   public void onLanguageContextFinalized(TruffleContext context, LanguageInfo language) {
      DebugContextsListener l = this.contextsListener;
      if (l != null) {
         DebugContext dc = this.getCachedDebugContext(context);
         l.languageContextFinalized(dc, language);
      }
   }

   @Override
   public void onLanguageContextDisposed(TruffleContext context, LanguageInfo language) {
      DebugContextsListener l = this.contextsListener;
      if (l != null) {
         DebugContext dc = this.getCachedDebugContext(context);
         l.languageContextDisposed(dc, language);
      }
   }

   @Override
   public void onContextClosed(TruffleContext context) {
      DebugContextsListener l = this.contextsListener;
      if (l != null) {
         DebugContext dc = this.getCachedDebugContext(context);
         l.contextClosed(dc);
      }
   }

   @Override
   public void onThreadInitialized(TruffleContext context, Thread thread) {
      DebugThreadsListener l = this.threadsListener;
      if (l != null) {
         DebugContext dc = this.getCachedDebugContext(context);
         l.threadInitialized(dc, thread);
      }
   }

   @Override
   public void onThreadDisposed(TruffleContext context, Thread thread) {
      DebugThreadsListener l = this.threadsListener;
      if (l != null) {
         DebugContext dc = this.getCachedDebugContext(context);
         l.threadDisposed(dc, thread);
      }
   }
}
