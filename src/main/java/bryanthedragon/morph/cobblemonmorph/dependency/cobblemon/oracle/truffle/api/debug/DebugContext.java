package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.source.Source;
import java.io.IOException;
import java.util.function.Supplier;

public final class DebugContext {
   private final DebuggerExecutionLifecycle executionLifecycle;
   private final TruffleContext context;

   DebugContext(DebuggerExecutionLifecycle executionLifecycle, TruffleContext context) {
      this.executionLifecycle = executionLifecycle;
      this.context = context;
   }

   public DebugValue evaluate(String code, String languageId) {
      assert code != null;

      Object prevContext = this.context.enter(null);

      DebugValue.HeapValue var8;
      try {
         Debugger debugger = this.executionLifecycle.getDebugger();
         CallTarget target = debugger.getEnv().parse(Source.newBuilder(languageId, code, "eval").build());
         Object result = target.call();
         LanguageInfo languageInfo = debugger.getEnv().getLanguages().get(languageId);
         var8 = new DebugValue.HeapValue(this.executionLifecycle.getSession(), languageInfo, null, result);
      } catch (IOException var12) {
         throw new RuntimeException(var12);
      } finally {
         this.context.leave(null, prevContext);
      }

      return var8;
   }

   public <T> T runInContext(Supplier<T> run) {
      assert run != null;

      Object prevContext = this.context.enter(null);

      Object var4;
      try {
         T ret = run.get();
         var4 = ret;
      } finally {
         this.context.leave(null, prevContext);
      }

      return (T)var4;
   }

   public DebugContext getParent() {
      TruffleContext parent = this.context.getParent();
      return parent == null ? null : this.executionLifecycle.getCachedDebugContext(parent);
   }
}
