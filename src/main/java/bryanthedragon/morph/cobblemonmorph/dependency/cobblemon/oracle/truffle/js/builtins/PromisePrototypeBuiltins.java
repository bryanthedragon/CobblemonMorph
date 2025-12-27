package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertyNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.arguments.AccessFunctionNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.nodes.control.ThrowNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.promise.NewPromiseCapabilityNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseThenNode;
import com.oracle.truffle.js.nodes.promise.PromiseResolveNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class PromisePrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<PromisePrototypeBuiltins.PromisePrototype> {
   public static final JSBuiltinsContainer BUILTINS = new PromisePrototypeBuiltins();

   protected PromisePrototypeBuiltins() {
      super(JSPromise.PROTOTYPE_NAME, PromisePrototypeBuiltins.PromisePrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, PromisePrototypeBuiltins.PromisePrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case then:
            return PromisePrototypeBuiltinsFactory.ThenNodeGen.create(context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context));
         case catch_:
            return PromisePrototypeBuiltinsFactory.CatchNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case finally_:
            return PromisePrototypeBuiltinsFactory.FinallyNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         default:
            return null;
      }
   }

   public abstract static class CatchNode extends JSBuiltinNode {
      @Node.Child
      private PropertyGetNode getThen;
      @Node.Child
      private JSFunctionCallNode callThen;

      protected CatchNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.getThen = PropertyGetNode.create(JSPromise.THEN, false, context);
         this.callThen = JSFunctionCallNode.createCall();
      }

      @Specialization
      protected Object doObject(Object promise, Object onRejected) {
         return this.callThen.executeCall(JSArguments.create(promise, this.getThen.getValue(promise), Undefined.instance, onRejected));
      }
   }

   public abstract static class FinallyNode extends PromisePrototypeBuiltins.PromiseMethodNode {
      @Node.Child
      private IsCallableNode isCallable = IsCallableNode.create();
      @Node.Child
      private PropertyGetNode getThen;
      @Node.Child
      private JSFunctionCallNode callThen;
      @Node.Child
      private PropertySetNode setConstructor;
      @Node.Child
      private PropertySetNode setOnFinally;
      static final HiddenKey VALUE_KEY = new HiddenKey("Value");

      protected FinallyNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.getThen = PropertyGetNode.create(JSPromise.THEN, false, context);
         this.callThen = JSFunctionCallNode.createCall();
      }

      @Specialization(guards = "isJSObject(promise)")
      protected Object doObject(JSDynamicObject promise, Object onFinally) {
         JSDynamicObject constructor = this.speciesConstructor(promise);

         assert JSRuntime.isConstructor(constructor);

         Object thenFinally;
         Object catchFinally;
         if (!this.isCallable.executeBoolean(onFinally)) {
            thenFinally = onFinally;
            catchFinally = onFinally;
         } else {
            thenFinally = this.createFinallyFunction(constructor, onFinally, true);
            catchFinally = this.createFinallyFunction(constructor, onFinally, false);
         }

         return this.callThen.executeCall(JSArguments.create(promise, this.getThen.getValue(promise), thenFinally, catchFinally));
      }

      @Specialization(guards = "!isJSObject(thisObj)")
      protected JSDynamicObject doNotObject(Object thisObj, Object onFinally) {
         throw Errors.createTypeErrorIncompatibleReceiver(thisObj);
      }

      private JSDynamicObject createFinallyFunction(JSDynamicObject constructor, Object onFinally, boolean thenFinally) {
         if (this.setConstructor == null || this.setOnFinally == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.setConstructor = this.insert(PropertySetNode.createSetHidden(JSPromise.PROMISE_FINALLY_CONSTRUCTOR, this.getContext()));
            this.setOnFinally = this.insert(PropertySetNode.createSetHidden(JSPromise.PROMISE_ON_FINALLY, this.getContext()));
         }

         JSFunctionData functionData;
         if (thenFinally) {
            functionData = this.getContext()
               .getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.PromiseThenFinally, c -> createPromiseFinallyFunction(c, true));
         } else {
            functionData = this.getContext()
               .getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.PromiseCatchFinally, c -> createPromiseFinallyFunction(c, false));
         }

         JSDynamicObject function = JSFunction.create(this.getRealm(), functionData);
         this.setConstructor.setValue(function, constructor);
         this.setOnFinally.setValue(function, onFinally);
         return function;
      }

      private static JSFunctionData createPromiseFinallyFunction(JSContext context, boolean thenFinally) {
         class PromiseFinallyRootNode extends JavaScriptRootNode {
            @Node.Child
            private JavaScriptNode valueNode = AccessIndexedArgumentNode.create(0);
            @Node.Child
            private PropertyGetNode getConstructor = PropertyGetNode.createGetHidden(JSPromise.PROMISE_FINALLY_CONSTRUCTOR, context);
            @Node.Child
            private PropertyGetNode getOnFinally = PropertyGetNode.createGetHidden(JSPromise.PROMISE_ON_FINALLY, context);
            @Node.Child
            private PromiseResolveNode promiseResolve = PromiseResolveNode.create(context);
            @Node.Child
            private JSFunctionCallNode callFinally = JSFunctionCallNode.createCall();
            @Node.Child
            private PropertyGetNode getThen = PropertyGetNode.create(JSPromise.THEN, false, context);
            @Node.Child
            private JSFunctionCallNode callThen = JSFunctionCallNode.createCall();
            @Node.Child
            private PropertySetNode setValue = PropertySetNode.createSetHidden(PromisePrototypeBuiltins.FinallyNode.VALUE_KEY, context);

            @Override
            public Object execute(VirtualFrame frame) {
               JSDynamicObject functionObject = JSFrameUtil.getFunctionObject(frame);
               JSDynamicObject onFinally = (JSDynamicObject)this.getOnFinally.getValue(functionObject);

               assert JSRuntime.isCallable(onFinally);

               Object result = this.callFinally.executeCall(JSArguments.createZeroArg(Undefined.instance, onFinally));
               JSDynamicObject constructor = (JSDynamicObject)this.getConstructor.getValue(functionObject);

               assert JSRuntime.isConstructor(constructor);

               JSDynamicObject promise = this.promiseResolve.execute(constructor, result);
               Object value = this.valueNode.execute(frame);
               Object thunk = this.createHandlerFunction(value);
               return this.callThen.executeCall(JSArguments.create(promise, this.getThen.getValue(promise), thunk));
            }

            private Object createHandlerFunction(Object value) {
               JSFunctionData functionData;
               if (thenFinally) {
                  functionData = context.getOrCreateBuiltinFunctionData(
                     JSContext.BuiltinFunctionKey.PromiseValueThunk, c -> PromisePrototypeBuiltins.FinallyNode.createValueThunk(c)
                  );
               } else {
                  functionData = context.getOrCreateBuiltinFunctionData(
                     JSContext.BuiltinFunctionKey.PromiseThrower, c -> PromisePrototypeBuiltins.FinallyNode.createThrower(c)
                  );
               }

               JSDynamicObject function = JSFunction.create(this.getRealm(), functionData);
               this.setValue.setValue(function, value);
               return function;
            }
         }

         return JSFunctionData.createCallOnly(context, new PromiseFinallyRootNode().getCallTarget(), 1, Strings.EMPTY_STRING);
      }

      static JSFunctionData createThrower(JSContext context) {
         return createThunkImpl(context, ThrowNode.create(PropertyNode.createGetHidden(context, AccessFunctionNode.create(), VALUE_KEY), context));
      }

      static JSFunctionData createValueThunk(JSContext context) {
         return createThunkImpl(context, PropertyNode.createGetHidden(context, AccessFunctionNode.create(), VALUE_KEY));
      }

      private static JSFunctionData createThunkImpl(JSContext context, JavaScriptNode expression) {
         CallTarget callTarget = (new JavaScriptRootNode() {
            @Node.Child
            private JavaScriptNode body = expression;

            @Override
            public Object execute(VirtualFrame frame) {
               return this.body.execute(frame);
            }
         }).getCallTarget();
         return JSFunctionData.createCallOnly(context, callTarget, 0, Strings.EMPTY_STRING);
      }
   }

   @ImportStatic(JSPromise.class)
   public abstract static class PromiseMethodNode extends JSBuiltinNode {
      @Node.Child
      private ArrayPrototypeBuiltins.ArraySpeciesConstructorNode speciesConstructorNode;

      protected PromiseMethodNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.speciesConstructorNode = ArrayPrototypeBuiltins.ArraySpeciesConstructorNode.create(context, false);
      }

      protected final JSDynamicObject speciesConstructor(JSDynamicObject promise) {
         return this.speciesConstructorNode.speciesConstructor(promise, this.getRealm().getPromiseConstructor());
      }
   }

   public static enum PromisePrototype implements BuiltinEnum<PromisePrototypeBuiltins.PromisePrototype> {
      then(2),
      catch_(1),
      finally_(1);

      private final int length;

      private PromisePrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   public abstract static class ThenNode extends PromisePrototypeBuiltins.PromiseMethodNode {
      @Node.Child
      private NewPromiseCapabilityNode newPromiseCapability;
      @Node.Child
      private PerformPromiseThenNode performPromiseThen;

      protected ThenNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.newPromiseCapability = NewPromiseCapabilityNode.create(context);
         this.performPromiseThen = PerformPromiseThenNode.create(context);
      }

      @Specialization(guards = "isJSPromise(promise)")
      protected JSDynamicObject doPromise(JSDynamicObject promise, Object onFulfilled, Object onRejected) {
         JSDynamicObject constructor = this.speciesConstructor(promise);
         this.getContext().notifyPromiseHook(-1, promise);
         PromiseCapabilityRecord resultCapability = this.newPromiseCapability.execute(constructor);
         return this.performPromiseThen.execute(promise, onFulfilled, onRejected, resultCapability);
      }

      @Specialization(guards = "!isJSPromise(thisObj)")
      protected JSDynamicObject doNotPromise(Object thisObj, Object onFulfilled, Object onRejected) {
         throw Errors.createTypeErrorIncompatibleReceiver(thisObj);
      }
   }
}
