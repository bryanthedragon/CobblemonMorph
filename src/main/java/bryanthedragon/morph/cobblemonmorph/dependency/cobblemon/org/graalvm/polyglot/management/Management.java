
package org.graalvm.polyglot.management;

import java.lang.reflect.Method;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.management.ExecutionEvent;
import org.graalvm.polyglot.management.ExecutionListener;

final class Management {
    static final AbstractPolyglotImpl IMPL = Management.initImpl();

    private Management() {
    }

    private static AbstractPolyglotImpl initImpl() {
        try {
            Method method = Engine.class.getDeclaredMethod("getImpl", new Class[0]);
            method.setAccessible(true);
            AbstractPolyglotImpl impl = (AbstractPolyglotImpl)method.invoke(null, new Object[0]);
            impl.setMonitoring(new ManagementAccessImpl());
            return impl;
        }
        catch (Exception e) {
            throw new IllegalStateException("Failed to initialize execution listener class.", e);
        }
    }

    private static final class ManagementAccessImpl
    extends AbstractPolyglotImpl.ManagementAccess {
        private ManagementAccessImpl() {
        }

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

