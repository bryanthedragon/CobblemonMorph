package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.nodes.function.InternalCallNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.promise.NewPromiseCapabilityNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseThenNode;
import com.oracle.truffle.js.nodes.promise.PromiseResolveNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.AsyncGeneratorRequest;
import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayDeque;

public class AsyncGeneratorResumeNextNode extends JavaScriptBaseNode {
   @Node.Child
   private PropertyGetNode getGeneratorStateNode;
   @Node.Child
   private PropertySetNode setGeneratorStateNode;
   @Node.Child
   private PropertyGetNode getAsyncGeneratorQueueNode;
   @Node.Child
   private JSFunctionCallNode callPromiseResolveNode;
   @Node.Child
   private PerformPromiseThenNode performPromiseThenNode;
   @Node.Child
   private NewPromiseCapabilityNode newPromiseCapabilityNode;
   @Node.Child
   private AsyncGeneratorResolveNode asyncGeneratorResolveNode;
   @Node.Child
   private AsyncGeneratorRejectNode asyncGeneratorRejectNode;
   @Node.Child
   private PropertySetNode setGeneratorNode;
   @Node.Child
   private PropertySetNode setPromiseIsHandledNode;
   @Node.Child
   private PromiseResolveNode promiseResolveNode;
   @Node.Child
   private TryCatchNode.GetErrorObjectNode getErrorObjectNode;
   private final ConditionProfile abruptProf = ConditionProfile.createBinaryProfile();
   protected final JSContext context;
   static final HiddenKey RETURN_PROCESSOR_GENERATOR = new HiddenKey("Generator");

   protected AsyncGeneratorResumeNextNode(JSContext context) {
      this.context = context;
      this.getGeneratorStateNode = PropertyGetNode.createGetHidden(JSFunction.ASYNC_GENERATOR_STATE_ID, context);
      this.setGeneratorStateNode = PropertySetNode.createSetHidden(JSFunction.ASYNC_GENERATOR_STATE_ID, context);
      this.getAsyncGeneratorQueueNode = PropertyGetNode.createGetHidden(JSFunction.ASYNC_GENERATOR_QUEUE_ID, context);
      this.asyncGeneratorResolveNode = AsyncGeneratorResolveNode.create(context);
   }

   public static AsyncGeneratorResumeNextNode create(JSContext context) {
      return new AsyncGeneratorResumeNextNode.WithCall(context);
   }

   public static AsyncGeneratorResumeNextNode createTailCall(JSContext context) {
      return new AsyncGeneratorResumeNextNode(context);
   }

   public final Object execute(VirtualFrame frame, JSDynamicObject generator) {
      while (true) {
         JSFunction.AsyncGeneratorState state = (JSFunction.AsyncGeneratorState)this.getGeneratorStateNode.getValue(generator);

         assert state != JSFunction.AsyncGeneratorState.Executing;

         if (state == JSFunction.AsyncGeneratorState.AwaitingReturn) {
            return Undefined.instance;
         }

         ArrayDeque<AsyncGeneratorRequest> queue = (ArrayDeque<AsyncGeneratorRequest>)this.getAsyncGeneratorQueueNode.getValue(generator);
         if (queue.isEmpty()) {
            return Undefined.instance;
         }

         AsyncGeneratorRequest next = queue.peekFirst();
         if (this.abruptProf.profile(next.isAbruptCompletion())) {
            if (state == JSFunction.AsyncGeneratorState.SuspendedStart) {
               state = JSFunction.AsyncGeneratorState.Completed;
               this.setGeneratorStateNode.setValue(generator, state);
            }

            if (state == JSFunction.AsyncGeneratorState.Completed) {
               if (next.isReturn()) {
                  this.enterReturnBranch();
                  this.setGeneratorStateNode.setValue(generator, JSFunction.AsyncGeneratorState.AwaitingReturn);

                  JSDynamicObject promise;
                  try {
                     promise = this.promiseResolve(next.getCompletionValue());
                  } catch (AbstractTruffleException var10) {
                     this.asyncGeneratorRejectBrokenPromise(frame, generator, var10);
                     continue;
                  }

                  JSFunctionObject onFulfilled = this.createAsyncGeneratorReturnProcessorFulfilledFunction(generator);
                  JSFunctionObject onRejected = this.createAsyncGeneratorReturnProcessorRejectedFunction(generator);
                  PromiseCapabilityRecord throwawayCapability = this.newThrowawayCapability();
                  this.performPromiseThenNode.execute(promise, onFulfilled, onRejected, throwawayCapability);
                  return Undefined.instance;
               }

               assert next.isThrow();

               this.enterThrowBranch();
               this.asyncGeneratorRejectNode.performReject(frame, generator, next.getCompletionValue());
               continue;
            }
         } else if (state == JSFunction.AsyncGeneratorState.Completed) {
            this.asyncGeneratorResolveNode.performResolve(frame, generator, Undefined.instance, true);
            continue;
         }

         assert state == JSFunction.AsyncGeneratorState.SuspendedStart || state == JSFunction.AsyncGeneratorState.SuspendedYield;

         this.setGeneratorStateNode.setValue(generator, JSFunction.AsyncGeneratorState.Executing);
         return this.performResumeNext(generator, next.getCompletion());
      }
   }

   private JSDynamicObject promiseResolve(Object value) {
      if (this.context.usePromiseResolve()) {
         if (this.promiseResolveNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.promiseResolveNode = this.insert(PromiseResolveNode.create(this.context));
         }

         return this.promiseResolveNode.execute(this.getRealm().getPromiseConstructor(), value);
      } else {
         if (this.callPromiseResolveNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callPromiseResolveNode = this.insert(JSFunctionCallNode.createCall());
         }

         PromiseCapabilityRecord promiseCapability = this.newPromiseCapability();
         this.callPromiseResolveNode.executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getResolve(), value));
         return promiseCapability.getPromise();
      }
   }

   protected Object performResumeNext(JSDynamicObject generator, Completion completion) {
      return completion;
   }

   private void enterReturnBranch() {
      if (this.performPromiseThenNode == null || this.setGeneratorNode == null || this.setPromiseIsHandledNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.performPromiseThenNode = this.insert(PerformPromiseThenNode.create(this.context));
         this.setGeneratorNode = this.insert(PropertySetNode.createSetHidden(RETURN_PROCESSOR_GENERATOR, this.context));
         this.setPromiseIsHandledNode = this.insert(PropertySetNode.createSetHidden(JSPromise.PROMISE_IS_HANDLED, this.context));
      }
   }

   private PromiseCapabilityRecord newPromiseCapability() {
      if (this.newPromiseCapabilityNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.newPromiseCapabilityNode = this.insert(NewPromiseCapabilityNode.create(this.context));
      }

      return this.newPromiseCapabilityNode.executeDefault();
   }

   private PromiseCapabilityRecord newThrowawayCapability() {
      if (this.context.getEcmaScriptVersion() >= 10) {
         return null;
      } else {
         if (this.setPromiseIsHandledNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.setPromiseIsHandledNode = this.insert(PropertySetNode.createSetHidden(JSPromise.PROMISE_IS_HANDLED, this.context));
         }

         PromiseCapabilityRecord throwawayCapability = this.newPromiseCapability();
         this.setPromiseIsHandledNode.setValueBoolean(throwawayCapability.getPromise(), true);
         return throwawayCapability;
      }
   }

   private void enterThrowBranch() {
      if (this.asyncGeneratorRejectNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.asyncGeneratorRejectNode = this.insert(AsyncGeneratorRejectNode.create(this.context));
      }
   }

   private void asyncGeneratorRejectBrokenPromise(VirtualFrame frame, JSDynamicObject generator, AbstractTruffleException exception) {
      if (this.getErrorObjectNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(this.context));
      }

      this.enterThrowBranch();
      this.setGeneratorStateNode.setValue(generator, JSFunction.AsyncGeneratorState.Completed);
      Object error = this.getErrorObjectNode.execute(exception);
      this.asyncGeneratorRejectNode.performReject(frame, generator, error);
   }

   private JSFunctionObject createAsyncGeneratorReturnProcessorFulfilledFunction(JSDynamicObject generator) {
      JSFunctionData functionData = this.context
         .getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.AsyncGeneratorReturnFulfilled, c -> createAsyncGeneratorReturnProcessorFulfilledImpl(c));
      JSFunctionObject function = JSFunction.create(this.getRealm(), functionData);
      this.setGeneratorNode.setValue(function, generator);
      return function;
   }

   private static JSFunctionData createAsyncGeneratorReturnProcessorFulfilledImpl(JSContext context) {
      class AsyncGeneratorReturnFulfilledRootNode extends JavaScriptRootNode {
         @Node.Child
         private JavaScriptNode valueNode = AccessIndexedArgumentNode.create(0);
         @Node.Child
         private AsyncGeneratorResolveNode asyncGeneratorResolveNode = AsyncGeneratorResolveNode.create(context);
         @Node.Child
         private PropertyGetNode getGenerator = PropertyGetNode.createGetHidden(AsyncGeneratorResumeNextNode.RETURN_PROCESSOR_GENERATOR, context);
         @Node.Child
         private PropertySetNode setGeneratorState = PropertySetNode.createSetHidden(JSFunction.ASYNC_GENERATOR_STATE_ID, context);

         @Override
         public Object execute(VirtualFrame frame) {
            JSDynamicObject functionObject = JSFrameUtil.getFunctionObject(frame);
            JSDynamicObject generatorObject = (JSDynamicObject)this.getGenerator.getValue(functionObject);
            this.setGeneratorState.setValue(generatorObject, JSFunction.AsyncGeneratorState.Completed);
            Object value = this.valueNode.execute(frame);
            return this.asyncGeneratorResolveNode.execute(frame, generatorObject, value, true);
         }
      }

      return JSFunctionData.createCallOnly(context, new AsyncGeneratorReturnFulfilledRootNode().getCallTarget(), 1, Strings.EMPTY_STRING);
   }

   private JSFunctionObject createAsyncGeneratorReturnProcessorRejectedFunction(JSDynamicObject generator) {
      JSFunctionData functionData = this.context
         .getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.AsyncGeneratorReturnRejected, c -> createAsyncGeneratorReturnProcessorRejectedImpl(c));
      JSFunctionObject function = JSFunction.create(this.getRealm(), functionData);
      this.setGeneratorNode.setValue(function, generator);
      return function;
   }

   private static JSFunctionData createAsyncGeneratorReturnProcessorRejectedImpl(JSContext context) {
      class AsyncGeneratorReturnRejectedRootNode extends JavaScriptRootNode {
         @Node.Child
         private JavaScriptNode reasonNode = AccessIndexedArgumentNode.create(0);
         @Node.Child
         private AsyncGeneratorRejectNode asyncGeneratorRejectNode = AsyncGeneratorRejectNode.create(context);
         @Node.Child
         private PropertyGetNode getGenerator = PropertyGetNode.createGetHidden(AsyncGeneratorResumeNextNode.RETURN_PROCESSOR_GENERATOR, context);
         @Node.Child
         private PropertySetNode setGeneratorState = PropertySetNode.createSetHidden(JSFunction.ASYNC_GENERATOR_STATE_ID, context);

         @Override
         public Object execute(VirtualFrame frame) {
            JSDynamicObject functionObject = JSFrameUtil.getFunctionObject(frame);
            JSDynamicObject generatorObject = (JSDynamicObject)this.getGenerator.getValue(functionObject);
            this.setGeneratorState.setValue(generatorObject, JSFunction.AsyncGeneratorState.Completed);
            Object reason = this.reasonNode.execute(frame);
            return this.asyncGeneratorRejectNode.execute(frame, generatorObject, reason);
         }
      }

      return JSFunctionData.createCallOnly(context, new AsyncGeneratorReturnRejectedRootNode().getCallTarget(), 1, Strings.EMPTY_STRING);
   }

   private static class WithCall extends AsyncGeneratorResumeNextNode {
      @Node.Child
      private PropertyGetNode getGeneratorTarget;
      @Node.Child
      private PropertyGetNode getGeneratorContext;
      @Node.Child
      private InternalCallNode callNode;

      protected WithCall(JSContext context) {
         super(context);
         this.getGeneratorTarget = PropertyGetNode.createGetHidden(JSFunction.ASYNC_GENERATOR_TARGET_ID, context);
         this.getGeneratorContext = PropertyGetNode.createGetHidden(JSFunction.ASYNC_GENERATOR_CONTEXT_ID, context);
         this.callNode = InternalCallNode.create();
      }

      @Override
      protected Object performResumeNext(JSDynamicObject generator, Completion completion) {
         CallTarget generatorTarget = (CallTarget)this.getGeneratorTarget.getValue(generator);
         Object generatorContext = this.getGeneratorContext.getValue(generator);
         this.callNode.execute(generatorTarget, JSArguments.createResumeArguments(generatorContext, generator, completion));
         return Undefined.instance;
      }
   }
}
