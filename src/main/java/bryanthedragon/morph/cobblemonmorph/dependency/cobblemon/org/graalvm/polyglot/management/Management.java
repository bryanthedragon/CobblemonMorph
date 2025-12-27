package org.graalvm.polyglot.management;

import java.lang.reflect.Method;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class Management {
   static final AbstractPolyglotImpl IMPL = initImpl();

   private Management() {
   }

   private static AbstractPolyglotImpl initImpl() {
      try {
         Method method = Engine.class.getDeclaredMethod("getImpl");
         method.setAccessible(true);
         AbstractPolyglotImpl impl = (AbstractPolyglotImpl)method.invoke(null);
         impl.setMonitoring(new Management.ManagementAccessImpl());
         return impl;
      } catch (Exception var2) {
         throw new IllegalStateException("Failed to initialize execution listener class.", var2);
      }
   }

   private static final class ManagementAccessImpl extends AbstractPolyglotImpl.ManagementAccess {
      @Override
      public ExecutionListener newExecutionListener(AbstractPolyglotImpl.AbstractExecutionListenerDispatch dispatch, Object receiver) {
         return new ExecutionListener(dispatch, receiver);
      }

      @Override
      public ExecutionEvent newExecutionEvent(AbstractPolyglotImpl.AbstractExecutionEventDispatch dispatch, Object event) {
         return new ExecutionEvent(dispatch, event);
      }

      @Override
      public Object getReceiver(ExecutionListener executionListener) {
         return executionListener.receiver;
      }

      @Override
      public AbstractPolyglotImpl.AbstractExecutionListenerDispatch getDispatch(ExecutionListener executionListener) {
         return executionListener.dispatch;
      }

      @Override
      public Object getReceiver(ExecutionEvent executionEvent) {
         return executionEvent.receiver;
      }

      @Override
      public AbstractPolyglotImpl.AbstractExecutionEventDispatch getDispatch(ExecutionEvent executionEvent) {
         return executionEvent.dispatch;
      }
   }
}
