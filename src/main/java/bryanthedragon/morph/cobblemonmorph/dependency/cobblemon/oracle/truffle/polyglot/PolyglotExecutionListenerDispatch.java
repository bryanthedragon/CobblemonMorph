
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.EventBinding;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ExecutionEventNode;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import java.util.AbstractList;
import java.util.List;
import java.util.function.Consumer;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.management.ExecutionEvent;

final class PolyglotExecutionListenerDispatch
extends AbstractPolyglotImpl.AbstractExecutionListenerDispatch {
    static final Object[] EMPTY_ARRAY = new Object[0];

    PolyglotExecutionListenerDispatch(PolyglotImpl engineImpl) {
        super(engineImpl);
    }

    @Override
    public void closeExecutionListener(Object impl) {
        try {
            ((ListenerImpl)impl).closing = true;
            ((ListenerImpl)impl).binding.dispose();
        }
        catch (Throwable t) {
            throw PolyglotExecutionListenerDispatch.wrapException(((ListenerImpl)impl).engine, t);
        }
    }

    private static RuntimeException wrapException(PolyglotEngineImpl engine, Throwable t) {
        return PolyglotImpl.guestToHostException(engine, t);
    }

    static class ReadOnlyValueList
    extends AbstractList<Value> {
        static final ReadOnlyValueList EMPTY = new ReadOnlyValueList(new Value[0]);
        private final Value[] valueArray;

        ReadOnlyValueList(Value[] valueArray) {
            this.valueArray = valueArray;
        }

        @Override
        public Value get(int index) {
            return this.valueArray[index];
        }

        @Override
        public int size() {
            return this.valueArray.length;
        }
    }

    static abstract class AbstractNode
    extends ExecutionEventNode
    implements Event {
        final ListenerImpl config;
        final EventContext context;
        final ExecutionEvent cachedEvent;

        AbstractNode(ListenerImpl config, EventContext context) {
            this.config = config;
            this.context = context;
            this.cachedEvent = config.management.newExecutionEvent(config.executionEventDispatch, this);
        }

        @Override
        public String getRootName() {
            RootNode rootNode = this.context.getInstrumentedNode().getRootNode();
            if (rootNode == null) {
                return null;
            }
            try {
                return rootNode.getName();
            }
            catch (Throwable t) {
                throw this.wrapHostError(t);
            }
        }

        @Override
        protected final void onEnter(VirtualFrame frame) {
            if (this.config.onEnter != null) {
                try {
                    this.invokeOnEnter();
                }
                catch (Throwable t) {
                    throw this.wrapHostError(t);
                }
            }
        }

        protected RuntimeException wrapHostError(Throwable t) {
            assert (!this.config.engine.host.isHostException(t));
            throw this.config.engine.host.toHostException(null, t);
        }

        @CompilerDirectives.TruffleBoundary(allowInlining=true)
        protected final void invokeOnEnter() {
            this.config.onEnter.accept(this.cachedEvent);
        }

        @CompilerDirectives.TruffleBoundary(allowInlining=true)
        protected final void invokeReturn() {
            this.config.onReturn.accept(this.cachedEvent);
        }

        @CompilerDirectives.TruffleBoundary(allowInlining=true)
        protected final void invokeException() {
            this.config.onReturn.accept(this.cachedEvent);
        }

        @CompilerDirectives.TruffleBoundary(allowInlining=true)
        protected final void invokeReturnAllocate(List<Value> inputValues, Value returnValue) {
            this.config.onReturn.accept(this.config.management.newExecutionEvent(this.config.executionEventDispatch, new DynamicEvent(this, inputValues, returnValue, null)));
        }

        @Override
        public final SourceSection getLocation() {
            return PolyglotImpl.getPolyglotSourceSection(this.config.engine.impl, this.context.getInstrumentedSourceSection());
        }

        @Override
        public final List<Value> getInputValues() {
            if (this.config.collectInputValues) {
                return ReadOnlyValueList.EMPTY;
            }
            return null;
        }

        @Override
        public final PolyglotException getException() {
            return null;
        }

        @Override
        public final Value getReturnValue() {
            return null;
        }

        @Override
        public final EventContext getContext() {
            return this.context;
        }

        @Override
        public final PolyglotEngineImpl getEngine() {
            return this.config.engine;
        }
    }

    static class DefaultNode
    extends AbstractNode
    implements Event {
        DefaultNode(ListenerImpl config, EventContext context) {
            super(config, context);
        }

        @Override
        protected void onReturnValue(VirtualFrame frame, Object result) {
            if (this.config.onReturn != null) {
                try {
                    this.invokeReturn();
                }
                catch (Throwable t) {
                    throw this.wrapHostError(t);
                }
            }
        }

        @Override
        protected void onReturnExceptional(VirtualFrame frame, Throwable exception) {
            if (this.config.onReturn != null) {
                try {
                    this.invokeException();
                }
                catch (Throwable t) {
                    throw this.wrapHostError(t);
                }
            }
        }
    }

    static class ProfilingNode
    extends AbstractNode
    implements Event {
        @CompilerDirectives.CompilationFinal
        boolean seenInputValues;
        @CompilerDirectives.CompilationFinal
        boolean seenReturnValue;
        final PolyglotLanguage language;

        ProfilingNode(ListenerImpl config, EventContext context) {
            super(config, context);
            PolyglotLanguage languageToUse = null;
            com.oracle.truffle.api.source.SourceSection location = context.getInstrumentedSourceSection();
            if (location != null) {
                languageToUse = config.engine.idToLanguage.get(location.getSource().getLanguage());
            }
            if (languageToUse == null) {
                assert (false);
                languageToUse = config.engine.hostLanguage;
            }
            this.language = languageToUse;
        }

        @Override
        protected void onInputValue(VirtualFrame frame, EventContext inputContext, int inputIndex, Object inputValue) {
            assert (this.config.onReturn != null && this.config.collectInputValues);
            if (!this.seenInputValues) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.seenInputValues = true;
            }
            this.saveInputValue(frame, inputIndex, inputValue);
        }

        @Override
        protected void onReturnValue(VirtualFrame frame, Object result) {
            if (this.config.onReturn != null) {
                try {
                    if (this.config.collectReturnValues && !this.seenReturnValue && result != null) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.seenReturnValue = true;
                    }
                    if (this.seenReturnValue || this.seenInputValues) {
                        Object[] inputValues = this.seenInputValues ? this.getSavedInputValues(frame) : EMPTY_ARRAY;
                        this.invokeReturnAllocate(inputValues, result);
                    } else {
                        this.invokeReturn();
                    }
                }
                catch (Throwable t) {
                    throw this.wrapHostError(t);
                }
            }
        }

        @Override
        protected void onReturnExceptional(VirtualFrame frame, Throwable exception) {
            if (this.config.onReturn != null) {
                try {
                    if (this.seenReturnValue || this.seenInputValues) {
                        Object[] inputValues = this.seenInputValues ? this.getSavedInputValues(frame) : EMPTY_ARRAY;
                        this.invokeExceptionAllocate(inputValues, exception);
                    } else if (this.config.collectExceptions) {
                        this.invokeExceptionAllocate(this.config.collectInputValues ? ReadOnlyValueList.EMPTY : (List)null, exception);
                    } else {
                        this.invokeException();
                    }
                }
                catch (Throwable t) {
                    throw this.wrapHostError(t);
                }
            }
        }

        @CompilerDirectives.TruffleBoundary
        private void invokeExceptionAllocate(Object[] inputValues, Throwable result) {
            ReadOnlyValueList convertedInputValues;
            boolean reportInputValues;
            boolean reportException = this.config.collectExceptions;
            boolean bl = reportInputValues = this.config.collectInputValues && inputValues.length > 0;
            if (!reportException && !reportInputValues) {
                this.invokeException();
                return;
            }
            PolyglotLanguageContext languageContext = this.language.getCurrentLanguageContext();
            if (reportInputValues) {
                Value[] hostValues = new Value[inputValues.length];
                for (int i = 0; i < inputValues.length; ++i) {
                    Object guestValue = inputValues[i];
                    hostValues[i] = guestValue != null ? languageContext.asValue(inputValues[i]) : null;
                }
                convertedInputValues = new ReadOnlyValueList(hostValues);
            } else {
                convertedInputValues = ReadOnlyValueList.EMPTY;
            }
            this.invokeExceptionAllocate(convertedInputValues, result);
        }

        @CompilerDirectives.TruffleBoundary
        private void invokeReturnAllocate(Object[] inputValues, Object result) {
            boolean reportInputValues;
            boolean reportResult = this.config.collectReturnValues && result != null;
            boolean bl = reportInputValues = this.config.collectInputValues && inputValues.length > 0;
            if (!reportResult && !reportInputValues) {
                this.invokeReturn();
                return;
            }
            PolyglotLanguageContext languageContext = this.language.getCurrentLanguageContext();
            Value returnValue = reportResult ? languageContext.asValue(result) : null;
            ReadOnlyValueList convertedInputValues = reportInputValues ? new ReadOnlyValueList(languageContext.toHostValues(inputValues)) : ReadOnlyValueList.EMPTY;
            this.invokeReturnAllocate(convertedInputValues, returnValue);
        }

        @CompilerDirectives.TruffleBoundary(allowInlining=true)
        protected final void invokeExceptionAllocate(List<Value> inputValues, Throwable e) {
            PolyglotException ex = e != null ? PolyglotImpl.guestToHostException(this.language.getCurrentLanguageContext(), e, true) : null;
            this.config.onReturn.accept(this.config.management.newExecutionEvent(this.config.executionEventDispatch, new DynamicEvent(this, inputValues, null, ex)));
        }
    }

    static final class DynamicEvent
    implements Event {
        final AbstractNode node;
        final List<Value> inputValues;
        final Value returnValue;
        final PolyglotException exception;

        DynamicEvent(AbstractNode node, List<Value> inputValues, Value returnValue, PolyglotException ex) {
            this.node = node;
            this.inputValues = inputValues;
            this.returnValue = returnValue;
            this.exception = ex;
        }

        @Override
        public String getRootName() {
            return this.node.getRootName();
        }

        @Override
        public PolyglotException getException() {
            return this.exception;
        }

        @Override
        public SourceSection getLocation() {
            return this.node.getLocation();
        }

        @Override
        public List<Value> getInputValues() {
            return this.inputValues;
        }

        @Override
        public Value getReturnValue() {
            return this.returnValue;
        }

        @Override
        public EventContext getContext() {
            return this.node.context;
        }

        @Override
        public PolyglotEngineImpl getEngine() {
            return this.node.getEngine();
        }
    }

    static interface Event {
        public String getRootName();

        public SourceSection getLocation();

        public List<Value> getInputValues();

        public Value getReturnValue();

        public EventContext getContext();

        public PolyglotException getException();

        public PolyglotEngineImpl getEngine();
    }

    static class ListenerImpl {
        final AbstractPolyglotImpl.AbstractExecutionEventDispatch executionEventDispatch;
        final PolyglotEngineImpl engine;
        final Consumer<ExecutionEvent> onEnter;
        final Consumer<ExecutionEvent> onReturn;
        final AbstractPolyglotImpl.ManagementAccess management;
        final boolean collectInputValues;
        final boolean collectReturnValues;
        final boolean collectExceptions;
        volatile EventBinding<?> binding;
        volatile boolean closing;

        ListenerImpl(AbstractPolyglotImpl.AbstractExecutionEventDispatch executionEventDispatch, PolyglotEngineImpl engine, Consumer<ExecutionEvent> onEnter, Consumer<ExecutionEvent> onReturn, boolean collectInputValues, boolean collectReturnValues, boolean collectExceptions) {
            this.executionEventDispatch = executionEventDispatch;
            this.engine = engine;
            this.onEnter = onEnter;
            this.onReturn = onReturn;
            this.management = engine.impl.getManagement();
            this.collectInputValues = collectInputValues;
            this.collectReturnValues = collectReturnValues;
            this.collectExceptions = collectExceptions;
        }
    }
}

