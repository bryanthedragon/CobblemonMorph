package org.graalvm.polyglot.management;

import java.util.function.Consumer;
import java.util.function.Predicate;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

public final class ExecutionListener implements AutoCloseable {
   private static final ExecutionListener EMPTY = new ExecutionListener(null, null);
   final AbstractPolyglotImpl.AbstractExecutionListenerDispatch dispatch;
   final Object receiver;

   ExecutionListener(AbstractPolyglotImpl.AbstractExecutionListenerDispatch dispatch, Object receiver) {
      this.dispatch = dispatch;
      this.receiver = receiver;
   }

   @Override
   public void close() {
      this.dispatch.closeExecutionListener(this.receiver);
   }

   public static ExecutionListener.Builder newBuilder() {
      return EMPTY.new Builder();
   }

   public final class Builder {
      private Consumer<ExecutionEvent> onReturn;
      private Consumer<ExecutionEvent> onEnter;
      private boolean expressions;
      private boolean statements;
      private boolean roots;
      private Predicate<Source> sourceFilter;
      private Predicate<String> rootNameFilter;
      private boolean collectInputValues;
      private boolean collectReturnValues;
      private boolean collectExceptions;

      Builder() {
      }

      public ExecutionListener.Builder onEnter(Consumer<ExecutionEvent> listener) {
         this.onEnter = listener;
         return this;
      }

      public ExecutionListener.Builder onReturn(Consumer<ExecutionEvent> listener) {
         this.onReturn = listener;
         return this;
      }

      public ExecutionListener.Builder sourceFilter(Predicate<Source> predicate) {
         this.sourceFilter = predicate;
         return this;
      }

      public ExecutionListener.Builder rootNameFilter(Predicate<String> predicate) {
         this.rootNameFilter = predicate;
         return this;
      }

      public ExecutionListener.Builder roots(boolean enabled) {
         this.roots = enabled;
         return this;
      }

      public ExecutionListener.Builder statements(boolean enabled) {
         this.statements = enabled;
         return this;
      }

      public ExecutionListener.Builder expressions(boolean enabled) {
         this.expressions = enabled;
         return this;
      }

      public ExecutionListener.Builder collectInputValues(boolean enabled) {
         this.collectInputValues = enabled;
         return this;
      }

      public ExecutionListener.Builder collectReturnValue(boolean enabled) {
         this.collectReturnValues = enabled;
         return this;
      }

      public ExecutionListener.Builder collectExceptions(boolean enabled) {
         this.collectExceptions = enabled;
         return this;
      }

      public ExecutionListener attach(Engine engine) {
         AbstractPolyglotImpl.APIAccess apiAccess = Management.IMPL.getAPIAccess();
         return apiAccess.getDispatch(engine)
            .attachExecutionListener(
               apiAccess.getReceiver(engine),
               this.onEnter,
               this.onReturn,
               this.expressions,
               this.statements,
               this.roots,
               this.sourceFilter,
               this.rootNameFilter,
               this.collectInputValues,
               this.collectReturnValues,
               this.collectExceptions
            );
      }
   }
}
