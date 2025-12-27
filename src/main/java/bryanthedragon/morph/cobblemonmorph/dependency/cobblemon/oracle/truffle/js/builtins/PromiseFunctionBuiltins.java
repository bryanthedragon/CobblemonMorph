package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.access.GetIteratorBaseNode;
import com.oracle.truffle.js.nodes.access.IteratorCloseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.control.TryCatchNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.promise.NewPromiseCapabilityNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseAllNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseAllSettledNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseAnyNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseCombinatorNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseRaceNode;
import com.oracle.truffle.js.nodes.promise.PromiseResolveNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class PromiseFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<PromiseFunctionBuiltins.PromiseFunction> {
   public static final JSBuiltinsContainer BUILTINS = new PromiseFunctionBuiltins();

   protected PromiseFunctionBuiltins() {
      super(JSPromise.CLASS_NAME, PromiseFunctionBuiltins.PromiseFunction.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, PromiseFunctionBuiltins.PromiseFunction builtinEnum) {
      switch (builtinEnum) {
         case all:
            return PromiseFunctionBuiltinsFactory.PromiseCombinatorNodeGen.create(
               context, builtin, PerformPromiseAllNode.create(context), args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case allSettled:
            return PromiseFunctionBuiltinsFactory.PromiseCombinatorNodeGen.create(
               context, builtin, PerformPromiseAllSettledNode.create(context), args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case any:
            return PromiseFunctionBuiltinsFactory.PromiseCombinatorNodeGen.create(
               context, builtin, PerformPromiseAnyNode.create(context), args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case race:
            return PromiseFunctionBuiltinsFactory.PromiseCombinatorNodeGen.create(
               context, builtin, PerformPromiseRaceNode.create(context), args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case reject:
            return PromiseFunctionBuiltinsFactory.RejectNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case resolve:
            return PromiseFunctionBuiltinsFactory.ResolveNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         default:
            return null;
      }
   }

   public abstract static class PromiseCombinatorNode extends JSBuiltinNode {
      @Node.Child
      private NewPromiseCapabilityNode newPromiseCapabilityNode;
      @Node.Child
      private PropertyGetNode getResolve;
      @Node.Child
      private IsCallableNode isCallable;
      @Node.Child
      private GetIteratorBaseNode getIteratorNode;
      @Node.Child
      private PerformPromiseCombinatorNode performPromiseOpNode;
      @Node.Child
      private JSFunctionCallNode callRejectNode;
      @Node.Child
      private IteratorCloseNode iteratorCloseNode;
      @Node.Child
      private TryCatchNode.GetErrorObjectNode getErrorObjectNode;

      protected PromiseCombinatorNode(JSContext context, JSBuiltin builtin, PerformPromiseCombinatorNode performPromiseOp) {
         super(context, builtin);
         this.newPromiseCapabilityNode = NewPromiseCapabilityNode.create(context);
         this.getResolve = PropertyGetNode.create(JSPromise.RESOLVE, context);
         this.isCallable = IsCallableNode.create();
         this.getIteratorNode = GetIteratorBaseNode.create();
         this.performPromiseOpNode = performPromiseOp;
      }

      @Specialization
      protected Object doObject(JSObject constructor, Object iterable) {
         PromiseCapabilityRecord promiseCapability = this.newPromiseCapabilityNode.execute(constructor);

         Object promiseResolve;
         IteratorRecord iteratorRecord;
         try {
            promiseResolve = this.getPromiseResolve(constructor);
            iteratorRecord = this.getIteratorNode.execute(iterable);
         } catch (AbstractTruffleException var7) {
            return this.rejectPromise(var7, promiseCapability);
         }

         try {
            return this.performPromiseOpNode.execute(iteratorRecord, constructor, promiseCapability, promiseResolve);
         } catch (AbstractTruffleException var8) {
            if (!iteratorRecord.isDone()) {
               this.iteratorClose(iteratorRecord);
            }

            return this.rejectPromise(var8, promiseCapability);
         }
      }

      private Object getPromiseResolve(JSDynamicObject constructor) {
         assert JSRuntime.isConstructor(constructor);

         Object promiseResolve = this.getResolve.getValue(constructor);
         if (!this.isCallable.executeBoolean(promiseResolve)) {
            throw Errors.createTypeErrorNotAFunction(promiseResolve);
         } else {
            return promiseResolve;
         }
      }

      protected JSDynamicObject rejectPromise(AbstractTruffleException ex, PromiseCapabilityRecord promiseCapability) {
         if (this.callRejectNode == null || this.getErrorObjectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callRejectNode = this.insert(JSFunctionCallNode.createCall());
            this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(this.getContext()));
         }

         Object error = this.getErrorObjectNode.execute(ex);
         this.callRejectNode.executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getReject(), error));
         return promiseCapability.getPromise();
      }

      private void iteratorClose(IteratorRecord iteratorRecord) {
         if (this.iteratorCloseNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.iteratorCloseNode = this.insert(IteratorCloseNode.create(this.getContext()));
         }

         this.iteratorCloseNode.executeAbrupt(iteratorRecord.getIterator());
      }

      @Specialization(guards = "!isJSObject(thisObj)")
      protected JSDynamicObject doNotObject(Object thisObj, Object iterable) {
         throw Errors.createTypeError("Cannot create promise from this type");
      }
   }

   public static enum PromiseFunction implements BuiltinEnum<PromiseFunctionBuiltins.PromiseFunction> {
      all(1),
      race(1),
      reject(1),
      resolve(1),
      allSettled(1),
      any(1);

      private final int length;

      private PromiseFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public int getECMAScriptVersion() {
         if (this == any) {
            return 12;
         } else {
            return this == allSettled ? 11 : 6;
         }
      }
   }

   public abstract static class RejectNode extends JSBuiltinNode {
      @Node.Child
      private NewPromiseCapabilityNode newPromiseCapability;
      @Node.Child
      private JSFunctionCallNode callReject;

      protected RejectNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.newPromiseCapability = NewPromiseCapabilityNode.create(context);
         this.callReject = JSFunctionCallNode.createCall();
      }

      @Specialization(guards = "isJSObject(constructor)")
      protected JSDynamicObject doObject(JSDynamicObject constructor, Object reason) {
         PromiseCapabilityRecord promiseCapability = this.newPromiseCapability.execute(constructor);
         this.callReject.executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getReject(), reason));
         return promiseCapability.getPromise();
      }

      @Specialization(guards = "!isJSObject(thisObj)")
      protected JSDynamicObject doNotObject(Object thisObj, Object iterable) {
         throw Errors.createTypeError("Cannot reject promise from this type");
      }
   }

   public abstract static class ResolveNode extends JSBuiltinNode {
      @Node.Child
      private PromiseResolveNode promiseResolve;

      protected ResolveNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.promiseResolve = PromiseResolveNode.create(context);
      }

      @Specialization(guards = "isJSObject(constructor)")
      protected JSDynamicObject doObject(JSDynamicObject constructor, Object value) {
         return this.promiseResolve.execute(constructor, value);
      }

      @Specialization(guards = "!isJSObject(thisObj)")
      protected JSDynamicObject doNotObject(Object thisObj, Object iterable) {
         throw Errors.createTypeError("Cannot resolve promise from this type");
      }
   }
}
