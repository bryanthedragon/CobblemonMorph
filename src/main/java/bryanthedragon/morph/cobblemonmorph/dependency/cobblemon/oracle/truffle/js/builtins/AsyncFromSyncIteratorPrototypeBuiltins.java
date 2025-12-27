package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.IteratorCompleteNode;
import com.oracle.truffle.js.nodes.access.IteratorNextNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.nodes.control.TryCatchNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.promise.NewPromiseCapabilityNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseThenNode;
import com.oracle.truffle.js.nodes.promise.PromiseResolveNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class AsyncFromSyncIteratorPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<AsyncFromSyncIteratorPrototypeBuiltins.GeneratorPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new AsyncFromSyncIteratorPrototypeBuiltins();

   protected AsyncFromSyncIteratorPrototypeBuiltins() {
      super(JSFunction.ASYNC_FROM_SYNC_ITERATOR_PROTOTYPE_NAME, AsyncFromSyncIteratorPrototypeBuiltins.GeneratorPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, AsyncFromSyncIteratorPrototypeBuiltins.GeneratorPrototype builtinEnum
   ) {
      assert context.getEcmaScriptVersion() >= 8;

      switch (builtinEnum) {
         case next:
            return AsyncFromSyncIteratorPrototypeBuiltinsFactory.AsyncFromSyncNextNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case return_:
            return AsyncFromSyncIteratorPrototypeBuiltinsFactory.AsyncFromSyncReturnNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case throw_:
            return AsyncFromSyncIteratorPrototypeBuiltinsFactory.AsyncFromSyncThrowNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   @ImportStatic(JSRuntime.class)
   private abstract static class AsyncFromSyncBaseNode extends JSBuiltinNode {
      static final HiddenKey DONE = new HiddenKey("Done");
      @Node.Child
      private JSFunctionCallNode executePromiseMethodNode;
      @Node.Child
      private NewPromiseCapabilityNode newPromiseCapabilityNode;
      @Node.Child
      protected PerformPromiseThenNode performPromiseThenNode;
      @Node.Child
      private PromiseResolveNode promiseResolveNode;
      @Node.Child
      protected IteratorNextNode iteratorNextNode;
      @Node.Child
      protected IteratorValueNode iteratorValueNode;
      @Node.Child
      protected IteratorCompleteNode iteratorCompleteNode;
      @Node.Child
      protected PropertyGetNode getSyncIteratorRecordNode;
      @Node.Child
      private PropertySetNode setDoneNode;
      @Node.Child
      private TryCatchNode.GetErrorObjectNode getErrorObjectNode;
      protected ConditionProfile valuePresenceProfile = ConditionProfile.createBinaryProfile();

      AsyncFromSyncBaseNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.newPromiseCapabilityNode = NewPromiseCapabilityNode.create(context);
         this.executePromiseMethodNode = JSFunctionCallNode.createCall();
         this.iteratorNextNode = IteratorNextNode.create();
         this.iteratorCompleteNode = IteratorCompleteNode.create(context);
         this.iteratorValueNode = IteratorValueNode.create();
         this.getSyncIteratorRecordNode = PropertyGetNode.createGetHidden(JSFunction.ASYNC_FROM_SYNC_ITERATOR_KEY, context);
         this.setDoneNode = PropertySetNode.createSetHidden(DONE, context);
         this.performPromiseThenNode = PerformPromiseThenNode.create(context);
         this.promiseResolveNode = PromiseResolveNode.create(context);
      }

      protected PromiseCapabilityRecord createPromiseCapability() {
         return this.newPromiseCapabilityNode.executeDefault();
      }

      protected boolean isAsyncFromSyncIterator(JSDynamicObject thiz) {
         return thiz != Undefined.instance && this.getSyncIteratorRecordNode.getValue(thiz) != Undefined.instance;
      }

      protected void promiseCapabilityReject(PromiseCapabilityRecord promiseCapability, AbstractTruffleException exception) {
         if (this.getErrorObjectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(this.getContext()));
         }

         Object result = this.getErrorObjectNode.execute(exception);
         this.promiseCapabilityRejectImpl(promiseCapability, result);
      }

      protected void promiseCapabilityRejectImpl(PromiseCapabilityRecord promiseCapability, Object result) {
         this.executePromiseMethodNode.executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getReject(), result));
      }

      protected void promiseCapabilityResolve(PromiseCapabilityRecord valueWrapperCapability, Object result) {
         this.executePromiseMethodNode.executeCall(JSArguments.createOneArg(Undefined.instance, valueWrapperCapability.getResolve(), result));
      }

      protected final Object asyncFromSyncIteratorContinuation(Object result, PromiseCapabilityRecord promiseCapability) {
         boolean done;
         try {
            done = this.iteratorCompleteNode.execute(result);
         } catch (AbstractTruffleException var9) {
            this.promiseCapabilityReject(promiseCapability, var9);
            return promiseCapability.getPromise();
         }

         Object returnValue;
         try {
            returnValue = this.iteratorValueNode.execute(result);
         } catch (AbstractTruffleException var8) {
            this.promiseCapabilityReject(promiseCapability, var8);
            return promiseCapability.getPromise();
         }

         JSRealm realm = this.getRealm();
         JSDynamicObject valueWrapper;
         if (this.getContext().usePromiseResolve()) {
            valueWrapper = this.promiseResolveNode.execute(realm.getPromiseConstructor(), returnValue);
         } else {
            PromiseCapabilityRecord valueWrapperCapability = this.createPromiseCapability();
            this.promiseCapabilityResolve(valueWrapperCapability, returnValue);
            valueWrapper = valueWrapperCapability.getPromise();
         }

         JSFunctionObject onFulfilled = this.createIteratorValueUnwrapFunction(realm, done);
         this.performPromiseThenNode.execute(valueWrapper, onFulfilled, Undefined.instance, promiseCapability);
         return promiseCapability.getPromise();
      }

      protected final JSFunctionObject createIteratorValueUnwrapFunction(JSRealm realm, boolean done) {
         JSContext context = realm.getContext();
         JSFunctionData functionData = context.getOrCreateBuiltinFunctionData(
            JSContext.BuiltinFunctionKey.AsyncFromSyncIteratorValueUnwrap, c -> createIteratorValueUnwrapImpl(c)
         );
         JSFunctionObject function = JSFunction.create(realm, functionData);
         this.setDoneNode.setValueBoolean(function, done);
         return function;
      }

      private static JSFunctionData createIteratorValueUnwrapImpl(JSContext context) {
         class AsyncFromSyncIteratorValueUnwrapRootNode extends JavaScriptRootNode {
            @Node.Child
            private JavaScriptNode valueNode = AccessIndexedArgumentNode.create(0);
            @Node.Child
            private PropertyGetNode isDoneNode = PropertyGetNode.createGetHidden(AsyncFromSyncIteratorPrototypeBuiltins.AsyncFromSyncBaseNode.DONE, context);
            @Node.Child
            private CreateIterResultObjectNode createIterResult = CreateIterResultObjectNode.create(context);

            @Override
            public Object execute(VirtualFrame frame) {
               JSDynamicObject functionObject = JSFrameUtil.getFunctionObject(frame);
               Object value = this.valueNode.execute(frame);

               boolean done;
               try {
                  done = this.isDoneNode.getValueBoolean(functionObject);
               } catch (UnexpectedResultException var6) {
                  throw Errors.shouldNotReachHere();
               }

               return this.createIterResult.execute(frame, value, done);
            }
         }

         return JSFunctionData.createCallOnly(context, new AsyncFromSyncIteratorValueUnwrapRootNode().getCallTarget(), 1, Strings.EMPTY_STRING);
      }
   }

   public abstract static class AsyncFromSyncMethod extends AsyncFromSyncIteratorPrototypeBuiltins.AsyncFromSyncBaseNode {
      @Node.Child
      private JSFunctionCallNode executeReturnMethod = JSFunctionCallNode.createCall();

      public AsyncFromSyncMethod(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected abstract GetMethodNode getMethod();

      protected abstract Object processUndefinedMethod(VirtualFrame frame, PromiseCapabilityRecord promiseCapability, Object value);

      protected Object doMethod(VirtualFrame frame, JSDynamicObject thisObj, Object value) {
         PromiseCapabilityRecord promiseCapability = this.createPromiseCapability();
         if (!this.isAsyncFromSyncIterator(thisObj)) {
            JSException typeError = Errors.createTypeErrorIncompatibleReceiver(thisObj);
            this.promiseCapabilityReject(promiseCapability, typeError);
            return promiseCapability.getPromise();
         } else {
            IteratorRecord syncIteratorRecord = (IteratorRecord)this.getSyncIteratorRecordNode.getValue(thisObj);
            JSDynamicObject syncIterator = syncIteratorRecord.getIterator();
            Object method = this.getMethod().executeWithTarget(syncIterator);
            if (method == Undefined.instance) {
               return this.processUndefinedMethod(frame, promiseCapability, value);
            } else {
               Object returnResult;
               try {
                  if (this.valuePresenceProfile.profile(JSArguments.getUserArgumentCount(frame.getArguments()) == 0)) {
                     returnResult = this.executeReturnMethod.executeCall(JSArguments.create(syncIterator, method));
                  } else {
                     returnResult = this.executeReturnMethod.executeCall(JSArguments.create(syncIterator, method, value));
                  }
               } catch (AbstractTruffleException var10) {
                  this.promiseCapabilityReject(promiseCapability, var10);
                  return promiseCapability.getPromise();
               }

               if (!JSDynamicObject.isJSDynamicObject(returnResult)) {
                  this.promiseCapabilityReject(promiseCapability, Errors.createTypeErrorNotAnObject(returnResult));
                  return promiseCapability.getPromise();
               } else {
                  return this.asyncFromSyncIteratorContinuation(returnResult, promiseCapability);
               }
            }
         }
      }
   }

   public abstract static class AsyncFromSyncNext extends AsyncFromSyncIteratorPrototypeBuiltins.AsyncFromSyncBaseNode {
      public AsyncFromSyncNext(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isObject(thisObj)")
      protected Object next(VirtualFrame frame, JSDynamicObject thisObj, Object value) {
         PromiseCapabilityRecord promiseCapability = this.createPromiseCapability();
         if (!this.isAsyncFromSyncIterator(thisObj)) {
            JSException typeError = Errors.createTypeErrorIncompatibleReceiver(thisObj);
            this.promiseCapabilityReject(promiseCapability, typeError);
            return promiseCapability.getPromise();
         } else {
            IteratorRecord syncIteratorRecord = (IteratorRecord)this.getSyncIteratorRecordNode.getValue(thisObj);

            Object nextResult;
            try {
               if (this.valuePresenceProfile.profile(JSArguments.getUserArgumentCount(frame.getArguments()) == 0)) {
                  nextResult = this.iteratorNextNode.execute(syncIteratorRecord);
               } else {
                  nextResult = this.iteratorNextNode.execute(syncIteratorRecord, value);
               }
            } catch (AbstractTruffleException var8) {
               this.promiseCapabilityReject(promiseCapability, var8);
               return promiseCapability.getPromise();
            }

            return this.asyncFromSyncIteratorContinuation(nextResult, promiseCapability);
         }
      }
   }

   public abstract static class AsyncFromSyncReturn extends AsyncFromSyncIteratorPrototypeBuiltins.AsyncFromSyncMethod {
      @Node.Child
      private GetMethodNode getReturn;
      @Node.Child
      private CreateIterResultObjectNode createIterResult;

      public AsyncFromSyncReturn(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.getReturn = GetMethodNode.create(context, Strings.RETURN);
         this.createIterResult = CreateIterResultObjectNode.create(this.getContext());
      }

      @Override
      protected GetMethodNode getMethod() {
         return this.getReturn;
      }

      @Override
      protected Object processUndefinedMethod(VirtualFrame frame, PromiseCapabilityRecord promiseCapability, Object value) {
         JSDynamicObject iterResult = this.createIterResult.execute(frame, value, true);
         this.promiseCapabilityResolve(promiseCapability, iterResult);
         return promiseCapability.getPromise();
      }

      @Specialization(guards = "isObject(thisObj)")
      protected Object resume(VirtualFrame frame, JSDynamicObject thisObj, Object value) {
         return this.doMethod(frame, thisObj, value);
      }
   }

   public abstract static class AsyncFromSyncThrow extends AsyncFromSyncIteratorPrototypeBuiltins.AsyncFromSyncMethod {
      @Node.Child
      private GetMethodNode getThrow;

      public AsyncFromSyncThrow(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.getThrow = GetMethodNode.create(context, Strings.THROW);
      }

      @Override
      protected GetMethodNode getMethod() {
         return this.getThrow;
      }

      @Override
      protected Object processUndefinedMethod(VirtualFrame frame, PromiseCapabilityRecord promiseCapability, Object value) {
         this.promiseCapabilityRejectImpl(promiseCapability, value);
         return promiseCapability.getPromise();
      }

      @Specialization(guards = "isObject(thisObj)")
      protected Object doThrow(VirtualFrame frame, JSDynamicObject thisObj, Object value) {
         return this.doMethod(frame, thisObj, value);
      }
   }

   public static enum GeneratorPrototype implements BuiltinEnum<AsyncFromSyncIteratorPrototypeBuiltins.GeneratorPrototype> {
      next(1),
      return_(1),
      throw_(1);

      private final int length;

      private GeneratorPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
