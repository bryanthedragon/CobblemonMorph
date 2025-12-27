package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.ErrorStackTraceLimitNode;
import com.oracle.truffle.js.nodes.access.InitErrorObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;

public class PerformPromiseAnyNode extends PerformPromiseCombinatorNode {
   protected static final HiddenKey REJECT_ELEMENT_ARGS_KEY = new HiddenKey("RejectElementArgs");
   @Node.Child
   protected JSFunctionCallNode callResolve;
   @Node.Child
   protected PropertyGetNode getThen;
   @Node.Child
   protected JSFunctionCallNode callThen;
   @Node.Child
   protected PropertySetNode setArgs;
   private final BranchProfile growProfile = BranchProfile.create();

   protected PerformPromiseAnyNode(JSContext context) {
      super(context);
      this.callResolve = JSFunctionCallNode.createCall();
      this.getThen = PropertyGetNode.create(JSPromise.THEN, false, context);
      this.callThen = JSFunctionCallNode.createCall();
      this.setArgs = PropertySetNode.createSetHidden(REJECT_ELEMENT_ARGS_KEY, context);
   }

   public static PerformPromiseAnyNode create(JSContext context) {
      return new PerformPromiseAnyNode(context);
   }

   @Override
   public JSDynamicObject execute(IteratorRecord iteratorRecord, JSDynamicObject constructor, PromiseCapabilityRecord resultCapability, Object promiseResolve) {
      assert JSRuntime.isConstructor(constructor);

      assert JSRuntime.isCallable(promiseResolve);

      SimpleArrayList<Object> errors = new SimpleArrayList<>(10);
      PerformPromiseCombinatorNode.BoxedInt remainingElementsCount = new PerformPromiseCombinatorNode.BoxedInt(1);
      int index = 0;

      while (true) {
         Object next = this.iteratorStepOrSetDone(iteratorRecord);
         if (next == Boolean.FALSE) {
            iteratorRecord.setDone(true);
            remainingElementsCount.value--;
            if (remainingElementsCount.value == 0) {
               JSDynamicObject errorsArray = JSArray.createConstantObjectArray(this.context, this.getRealm(), errors.toArray());
               throw Errors.createAggregateError(errorsArray, this);
            } else {
               return resultCapability.getPromise();
            }
         }

         Object nextValue = this.iteratorValueOrSetDone(iteratorRecord, next);
         errors.add(Undefined.instance, this.growProfile);
         Object nextPromise = this.callResolve.executeCall(JSArguments.createOneArg(constructor, promiseResolve, nextValue));
         Object resolveElement = this.createResolveElementFunction(index, errors, resultCapability, remainingElementsCount);
         JSFunctionObject rejectElement = this.createRejectElementFunction(index, errors, resultCapability, remainingElementsCount);
         remainingElementsCount.value++;
         this.callThen.executeCall(JSArguments.create(nextPromise, this.getThen.getValue(nextPromise), resolveElement, rejectElement));
         index++;
      }
   }

   protected JSFunctionObject createRejectElementFunction(
      int index, SimpleArrayList<Object> errors, PromiseCapabilityRecord resultCapability, PerformPromiseCombinatorNode.BoxedInt remainingElementsCount
   ) {
      JSFunctionData functionData = this.context
         .getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.PromiseAnyRejectElement, c -> createRejectElementFunctionImpl(c));
      JSFunctionObject function = JSFunction.create(this.getRealm(), functionData);
      this.setArgs.setValue(function, new PerformPromiseAnyNode.RejectElementArgs(index, errors, resultCapability, remainingElementsCount));
      return function;
   }

   protected Object createResolveElementFunction(
      int index, SimpleArrayList<Object> errors, PromiseCapabilityRecord resultCapability, PerformPromiseCombinatorNode.BoxedInt remainingElementsCount
   ) {
      return resultCapability.getResolve();
   }

   private static JSFunctionData createRejectElementFunctionImpl(JSContext context) {
      class PromiseAnyRejectElementRootNode extends JavaScriptRootNode {
         @Node.Child
         private JavaScriptNode errorNode = AccessIndexedArgumentNode.create(0);
         @Node.Child
         private PropertyGetNode getArgs = PropertyGetNode.createGetHidden(PerformPromiseAnyNode.REJECT_ELEMENT_ARGS_KEY, context);
         @Node.Child
         private JSFunctionCallNode callReject = JSFunctionCallNode.createCall();
         @Node.Child
         private ErrorStackTraceLimitNode stackTraceLimitNode = ErrorStackTraceLimitNode.create();
         @Node.Child
         private InitErrorObjectNode initErrorObjectNode = InitErrorObjectNode.create(context);

         @Override
         public Object execute(VirtualFrame frame) {
            JSDynamicObject functionObject = JSFrameUtil.getFunctionObject(frame);
            PerformPromiseAnyNode.RejectElementArgs args = (PerformPromiseAnyNode.RejectElementArgs)this.getArgs.getValue(functionObject);
            if (args.alreadyCalled) {
               return Undefined.instance;
            } else {
               args.alreadyCalled = true;
               Object error = this.errorNode.execute(frame);
               args.errors.set(args.index, error);
               args.remainingElements.value--;
               if (args.remainingElements.value == 0) {
                  JSDynamicObject aggregateErrorObject = this.createAggregateError(args.errors.toArray());
                  return this.callReject.executeCall(JSArguments.createOneArg(Undefined.instance, args.capability.getReject(), aggregateErrorObject));
               } else {
                  return Undefined.instance;
               }
            }
         }

         private JSDynamicObject createAggregateError(Object[] errors) {
            int stackTraceLimit = this.stackTraceLimitNode.executeInt();
            JSRealm realm = this.getRealm();
            JSDynamicObject errorsArray = JSArray.createConstantObjectArray(context, this.getRealm(), errors);
            JSObject aggregateErrorObject = JSError.createErrorObject(context, realm, JSErrorType.AggregateError);
            JSDynamicObject errorFunction = realm.getErrorConstructor(JSErrorType.AggregateError);
            GraalJSException exception = JSException.createCapture(
               JSErrorType.AggregateError, null, aggregateErrorObject, realm, stackTraceLimit, errorFunction, false
            );
            this.initErrorObjectNode.execute(aggregateErrorObject, exception, null, errorsArray);
            return aggregateErrorObject;
         }
      }

      return JSFunctionData.createCallOnly(context, new PromiseAnyRejectElementRootNode().getCallTarget(), 1, Strings.EMPTY_STRING);
   }

   protected static final class RejectElementArgs {
      boolean alreadyCalled = false;
      final int index;
      final SimpleArrayList<Object> errors;
      final PromiseCapabilityRecord capability;
      final PerformPromiseCombinatorNode.BoxedInt remainingElements;

      RejectElementArgs(int index, SimpleArrayList<Object> errors, PromiseCapabilityRecord capability, PerformPromiseCombinatorNode.BoxedInt remainingElements) {
         this.index = index;
         this.errors = errors;
         this.capability = capability;
         this.remainingElements = remainingElements;
      }
   }
}
