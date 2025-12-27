package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.EventBinding;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ExecutionEventNode;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.AbstractList;
import java.util.List;
import java.util.function.Consumer;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.management.ExecutionEvent;

final class PolyglotExecutionListenerDispatch extends AbstractPolyglotImpl.AbstractExecutionListenerDispatch {
   static final Object[] EMPTY_ARRAY = new Object[0];

   PolyglotExecutionListenerDispatch(PolyglotImpl engineImpl) {
      super(engineImpl);
   }

   @Override
   public void closeExecutionListener(Object impl) {
      try {
         ((PolyglotExecutionListenerDispatch.ListenerImpl)impl).closing = true;
         ((PolyglotExecutionListenerDispatch.ListenerImpl)impl).binding.dispose();
      } catch (Throwable var3) {
         throw wrapException(((PolyglotExecutionListenerDispatch.ListenerImpl)impl).engine, var3);
      }
   }

   private static RuntimeException wrapException(PolyglotEngineImpl engine, Throwable t) {
      return PolyglotImpl.guestToHostException(engine, t);
   }

   abstract static class AbstractNode extends ExecutionEventNode implements PolyglotExecutionListenerDispatch.Event {
      final PolyglotExecutionListenerDispatch.ListenerImpl config;
      final EventContext context;
      final ExecutionEvent cachedEvent;

      AbstractNode(PolyglotExecutionListenerDispatch.ListenerImpl config, EventContext context) {
         this.config = config;
         this.context = context;
         this.cachedEvent = config.management.newExecutionEvent(config.executionEventDispatch, this);
      }

      @Override
      public String getRootName() {
         RootNode rootNode = this.context.getInstrumentedNode().getRootNode();
         if (rootNode == null) {
            return null;
         } else {
            try {
               return rootNode.getName();
            } catch (Throwable var3) {
               throw this.wrapHostError(var3);
            }
         }
      }

      @Override
      protected final void onEnter(VirtualFrame frame) {
         if (this.config.onEnter != null) {
            try {
               this.invokeOnEnter();
            } catch (Throwable var3) {
               throw this.wrapHostError(var3);
            }
         }
      }

      protected RuntimeException wrapHostError(Throwable t) {
         assert !this.config.engine.host.isHostException(t);

         throw this.config.engine.host.toHostException(null, t);
      }

      @CompilerDirectives.TruffleBoundary(allowInlining = true)
      protected final void invokeOnEnter() {
         this.config.onEnter.accept(this.cachedEvent);
      }

      @CompilerDirectives.TruffleBoundary(allowInlining = true)
      protected final void invokeReturn() {
         this.config.onReturn.accept(this.cachedEvent);
      }

      @CompilerDirectives.TruffleBoundary(allowInlining = true)
      protected final void invokeException() {
         this.config.onReturn.accept(this.cachedEvent);
      }

      @CompilerDirectives.TruffleBoundary(allowInlining = true)
      protected final void invokeReturnAllocate(List<Value> inputValues, Value returnValue) {
         this.config
            .onReturn
            .accept(
               this.config
                  .management
                  .newExecutionEvent(
                     this.config.executionEventDispatch, new PolyglotExecutionListenerDispatch.DynamicEvent(this, inputValues, returnValue, null)
                  )
            );
      }

      @Override
      public final SourceSection getLocation() {
         return PolyglotImpl.getPolyglotSourceSection(this.config.engine.impl, this.context.getInstrumentedSourceSection());
      }

      @Override
      public final List<Value> getInputValues() {
         return this.config.collectInputValues ? PolyglotExecutionListenerDispatch.ReadOnlyValueList.EMPTY : null;
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

   static class DefaultNode extends PolyglotExecutionListenerDispatch.AbstractNode implements PolyglotExecutionListenerDispatch.Event {
      DefaultNode(PolyglotExecutionListenerDispatch.ListenerImpl config, EventContext context) {
         super(config, context);
      }

      @Override
      protected void onReturnValue(VirtualFrame frame, Object result) {
         if (this.config.onReturn != null) {
            try {
               this.invokeReturn();
            } catch (Throwable var4) {
               throw this.wrapHostError(var4);
            }
         }
      }

      @Override
      protected void onReturnExceptional(VirtualFrame frame, Throwable exception) {
         if (this.config.onReturn != null) {
            try {
               this.invokeException();
            } catch (Throwable var4) {
               throw this.wrapHostError(var4);
            }
         }
      }
   }

   static final class DynamicEvent implements PolyglotExecutionListenerDispatch.Event {
      final PolyglotExecutionListenerDispatch.AbstractNode node;
      final List<Value> inputValues;
      final Value returnValue;
      final PolyglotException exception;

      DynamicEvent(PolyglotExecutionListenerDispatch.AbstractNode node, List<Value> inputValues, Value returnValue, PolyglotException ex) {
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

   interface Event {
      String getRootName();

      SourceSection getLocation();

      List<Value> getInputValues();

      Value getReturnValue();

      EventContext getContext();

      PolyglotException getException();

      PolyglotEngineImpl getEngine();
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

      ListenerImpl(
         AbstractPolyglotImpl.AbstractExecutionEventDispatch executionEventDispatch,
         PolyglotEngineImpl engine,
         Consumer<ExecutionEvent> onEnter,
         Consumer<ExecutionEvent> onReturn,
         boolean collectInputValues,
         boolean collectReturnValues,
         boolean collectExceptions
      ) {
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

   static class ProfilingNode extends PolyglotExecutionListenerDispatch.AbstractNode implements PolyglotExecutionListenerDispatch.Event {
      @CompilerDirectives.CompilationFinal
      boolean seenInputValues;
      @CompilerDirectives.CompilationFinal
      boolean seenReturnValue;
      final PolyglotLanguage language;

      ProfilingNode(PolyglotExecutionListenerDispatch.ListenerImpl config, EventContext context) {
         super(config, context);
         PolyglotLanguage languageToUse = null;
         com.oracle.truffle.api.source.SourceSection location = context.getInstrumentedSourceSection();
         if (location != null) {
            languageToUse = config.engine.idToLanguage.get(location.getSource().getLanguage());
         }

         if (languageToUse == null) {
            assert false;

            languageToUse = config.engine.hostLanguage;
         }

         this.language = languageToUse;
      }

      @Override
      protected void onInputValue(VirtualFrame frame, EventContext inputContext, int inputIndex, Object inputValue) {
         assert this.config.onReturn != null && this.config.collectInputValues;

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

               if (!this.seenReturnValue && !this.seenInputValues) {
                  this.invokeReturn();
               } else {
                  Object[] inputValues;
                  if (this.seenInputValues) {
                     inputValues = this.getSavedInputValues(frame);
                  } else {
                     inputValues = PolyglotExecutionListenerDispatch.EMPTY_ARRAY;
                  }

                  this.invokeReturnAllocate(inputValues, result);
               }
            } catch (Throwable var4) {
               throw this.wrapHostError(var4);
            }
         }
      }

      @Override
      protected void onReturnExceptional(VirtualFrame frame, Throwable exception) {
         if (this.config.onReturn != null) {
            try {
               if (!this.seenReturnValue && !this.seenInputValues) {
                  if (this.config.collectExceptions) {
                     this.invokeExceptionAllocate(
                        (List<Value>)(this.config.collectInputValues ? PolyglotExecutionListenerDispatch.ReadOnlyValueList.EMPTY : (List)null), exception
                     );
                  } else {
                     this.invokeException();
                  }
               } else {
                  Object[] inputValues;
                  if (this.seenInputValues) {
                     inputValues = this.getSavedInputValues(frame);
                  } else {
                     inputValues = PolyglotExecutionListenerDispatch.EMPTY_ARRAY;
                  }

                  this.invokeExceptionAllocate(inputValues, exception);
               }
            } catch (Throwable var4) {
               throw this.wrapHostError(var4);
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      private void invokeExceptionAllocate(Object[] inputValues, Throwable result) {
         boolean reportException = this.config.collectExceptions;
         boolean reportInputValues = this.config.collectInputValues && inputValues.length > 0;
         if (!reportException && !reportInputValues) {
            this.invokeException();
         } else {
            PolyglotLanguageContext languageContext = this.language.getCurrentLanguageContext();
            PolyglotExecutionListenerDispatch.ReadOnlyValueList convertedInputValues;
            if (reportInputValues) {
               Value[] hostValues = new Value[inputValues.length];

               for (int i = 0; i < inputValues.length; i++) {
                  Object guestValue = inputValues[i];
                  if (guestValue != null) {
                     hostValues[i] = languageContext.asValue(inputValues[i]);
                  } else {
                     hostValues[i] = null;
                  }
               }

               convertedInputValues = new PolyglotExecutionListenerDispatch.ReadOnlyValueList(hostValues);
            } else {
               convertedInputValues = PolyglotExecutionListenerDispatch.ReadOnlyValueList.EMPTY;
            }

            this.invokeExceptionAllocate(convertedInputValues, result);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private void invokeReturnAllocate(Object[] inputValues, Object result) {
         boolean reportResult = this.config.collectReturnValues && result != null;
         boolean reportInputValues = this.config.collectInputValues && inputValues.length > 0;
         if (!reportResult && !reportInputValues) {
            this.invokeReturn();
         } else {
            PolyglotLanguageContext languageContext = this.language.getCurrentLanguageContext();
            Value returnValue;
            if (reportResult) {
               returnValue = languageContext.asValue(result);
            } else {
               returnValue = null;
            }

            PolyglotExecutionListenerDispatch.ReadOnlyValueList convertedInputValues;
            if (reportInputValues) {
               convertedInputValues = new PolyglotExecutionListenerDispatch.ReadOnlyValueList(languageContext.toHostValues(inputValues));
            } else {
               convertedInputValues = PolyglotExecutionListenerDispatch.ReadOnlyValueList.EMPTY;
            }

            this.invokeReturnAllocate(convertedInputValues, returnValue);
         }
      }

      @CompilerDirectives.TruffleBoundary(allowInlining = true)
      protected final void invokeExceptionAllocate(List<Value> inputValues, Throwable e) {
         PolyglotException ex = e != null ? PolyglotImpl.guestToHostException(this.language.getCurrentLanguageContext(), e, true) : null;
         this.config
            .onReturn
            .accept(
               this.config
                  .management
                  .newExecutionEvent(this.config.executionEventDispatch, new PolyglotExecutionListenerDispatch.DynamicEvent(this, inputValues, null, ex))
            );
      }
   }

   static class ReadOnlyValueList extends AbstractList<Value> {
      static final PolyglotExecutionListenerDispatch.ReadOnlyValueList EMPTY = new PolyglotExecutionListenerDispatch.ReadOnlyValueList(new Value[0]);
      private final Value[] valueArray;

      ReadOnlyValueList(Value[] valueArray) {
         this.valueArray = valueArray;
      }

      public Value get(int index) {
         return this.valueArray[index];
      }

      @Override
      public int size() {
         return this.valueArray.length;
      }
   }
}
