package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.nodes.control.TryCatchNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.Pair;

public class CreateResolvingFunctionNode extends JavaScriptBaseNode {
   static final HiddenKey ALREADY_RESOLVED_KEY = new HiddenKey("AlreadyResolved");
   static final HiddenKey PROMISE_KEY = new HiddenKey("Promise");
   static final HiddenKey THENABLE_KEY = new HiddenKey("thenable");
   static final HiddenKey THEN_KEY = new HiddenKey("then");
   private final JSContext context;
   @Node.Child
   private PropertySetNode setAlreadyResolvedNode;
   @Node.Child
   private PropertySetNode setPromiseNode;

   protected CreateResolvingFunctionNode(JSContext context) {
      this.context = context;
      this.setAlreadyResolvedNode = PropertySetNode.createSetHidden(ALREADY_RESOLVED_KEY, context);
      this.setPromiseNode = PropertySetNode.createSetHidden(PROMISE_KEY, context);
   }

   public static CreateResolvingFunctionNode create(JSContext context) {
      return new CreateResolvingFunctionNode(context);
   }

   public Pair<JSDynamicObject, JSDynamicObject> execute(JSDynamicObject promise) {
      CreateResolvingFunctionNode.AlreadyResolved alreadyResolved = new CreateResolvingFunctionNode.AlreadyResolved();
      JSFunctionObject resolve = this.createPromiseResolveFunction(promise, alreadyResolved);
      JSFunctionObject reject = this.createPromiseRejectFunction(promise, alreadyResolved);
      return new Pair<>(resolve, reject);
   }

   private JSFunctionObject createPromiseResolveFunction(JSDynamicObject promise, CreateResolvingFunctionNode.AlreadyResolved alreadyResolved) {
      JSFunctionData functionData = this.context
         .getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.PromiseResolveFunction, c -> createPromiseResolveFunctionImpl(c));
      JSFunctionObject function = JSFunction.create(this.getRealm(), functionData);
      this.setPromiseNode.setValue(function, promise);
      this.setAlreadyResolvedNode.setValue(function, alreadyResolved);
      return function;
   }

   private static JSFunctionData createPromiseResolveFunctionImpl(JSContext context) {
      class PromiseResolveRootNode extends JavaScriptRootNode implements AsyncHandlerRootNode {
         @Node.Child
         private JavaScriptNode resolutionNode = AccessIndexedArgumentNode.create(0);
         @Node.Child
         private PropertyGetNode getPromiseNode;
         @Node.Child
         private PropertyGetNode getAlreadyResolvedNode = PropertyGetNode.createGetHidden(CreateResolvingFunctionNode.ALREADY_RESOLVED_KEY, context);
         @Node.Child
         private PropertyGetNode getThenNode;
         @Node.Child
         private IsCallableNode isCallableNode = IsCallableNode.create();
         @Node.Child
         private IsObjectNode isObjectNode = IsObjectNode.create();
         @Node.Child
         private FulfillPromiseNode fulfillPromiseNode;
         @Node.Child
         private RejectPromiseNode rejectPromiseNode;
         @Node.Child
         private TryCatchNode.GetErrorObjectNode getErrorObjectNode;
         private final ConditionProfile alreadyResolvedProfile = ConditionProfile.createBinaryProfile();
         @Node.Child
         private PropertySetNode setPromiseNode;
         @Node.Child
         private PropertySetNode setThenableNode;
         @Node.Child
         private PropertySetNode setThenNode;

         @Override
         public Object execute(VirtualFrame frame) {
            JSDynamicObject functionObject = JSFrameUtil.getFunctionObject(frame);
            JSDynamicObject promise = (JSDynamicObject)this.getPromise(functionObject);
            Object resolution = this.resolutionNode.execute(frame);
            CreateResolvingFunctionNode.AlreadyResolved alreadyResolved = (CreateResolvingFunctionNode.AlreadyResolved)this.getAlreadyResolvedNode
               .getValue(functionObject);
            if (this.alreadyResolvedProfile.profile(alreadyResolved.value)) {
               context.notifyPromiseRejectionTracker(promise, 3, resolution);
               return Undefined.instance;
            } else {
               alreadyResolved.value = true;
               context.notifyPromiseHook(1, promise);
               if (resolution == promise) {
                  this.enterErrorBranch();
                  return this.rejectPromise(promise, Errors.createTypeError("self resolution!"));
               } else if (!this.isObjectNode.executeBoolean(resolution)) {
                  return this.fulfillPromise(promise, resolution);
               } else {
                  Object then;
                  try {
                     then = this.getThen(resolution);
                  } catch (AbstractTruffleException var8) {
                     this.enterErrorBranch();
                     return this.rejectPromise(promise, var8);
                  }

                  if (!this.isCallableNode.executeBoolean(then)) {
                     return this.fulfillPromise(promise, resolution);
                  } else {
                     JSFunctionObject job = this.promiseResolveThenableJob(promise, resolution, then);
                     context.promiseEnqueueJob(this.getRealm(), job);
                     return Undefined.instance;
                  }
               }
            }
         }

         private Object fulfillPromise(JSDynamicObject promise, Object resolution) {
            if (this.fulfillPromiseNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.fulfillPromiseNode = this.insert(FulfillPromiseNode.create(context));
            }

            return this.fulfillPromiseNode.execute(promise, resolution);
         }

         private Object getThen(Object resolution) {
            if (this.getThenNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.getThenNode = this.insert(PropertyGetNode.create(JSPromise.THEN, false, context));
            }

            return this.getThenNode.getValue(resolution);
         }

         private Object getPromise(JSDynamicObject functionObject) {
            if (this.getPromiseNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.getPromiseNode = this.insert(PropertyGetNode.createGetHidden(CreateResolvingFunctionNode.PROMISE_KEY, context));
            }

            return this.getPromiseNode.getValue(functionObject);
         }

         private Object rejectPromise(JSDynamicObject promise, AbstractTruffleException exception) {
            Object error = this.getErrorObjectNode.execute(exception);
            return this.rejectPromiseNode.execute(promise, error);
         }

         private void enterErrorBranch() {
            if (this.rejectPromiseNode == null || this.getErrorObjectNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.rejectPromiseNode = this.insert(RejectPromiseNode.create(context));
               this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(context));
            }
         }

         private JSFunctionObject promiseResolveThenableJob(JSDynamicObject promise, Object thenable, Object then) {
            if (this.setPromiseNode == null || this.setThenableNode == null || this.setThenNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.setPromiseNode = this.insert(PropertySetNode.createSetHidden(CreateResolvingFunctionNode.PROMISE_KEY, context));
               this.setThenableNode = this.insert(PropertySetNode.createSetHidden(CreateResolvingFunctionNode.THENABLE_KEY, context));
               this.setThenNode = this.insert(PropertySetNode.createSetHidden(CreateResolvingFunctionNode.THEN_KEY, context));
            }

            JSFunctionData functionData = context.getOrCreateBuiltinFunctionData(
               JSContext.BuiltinFunctionKey.PromiseResolveThenableJob, c -> CreateResolvingFunctionNode.createPromiseResolveThenableJobImpl(c)
            );
            JSFunctionObject function = JSFunction.create(this.getRealm(), functionData);
            this.setPromiseNode.setValue(function, promise);
            this.setThenableNode.setValue(function, thenable);
            this.setThenNode.setValue(function, then);
            return function;
         }

         @Override
         public AsyncHandlerRootNode.AsyncStackTraceInfo getAsyncStackTraceInfo(JSFunctionObject handlerFunction) {
            assert JSFunction.isJSFunction(handlerFunction)
               && ((RootCallTarget)JSFunction.getFunctionData(handlerFunction).getCallTarget()).getRootNode() == this;

            JSDynamicObject promise = (JSDynamicObject)JSObjectUtil.getHiddenProperty(handlerFunction, CreateResolvingFunctionNode.PROMISE_KEY);
            return new AsyncHandlerRootNode.AsyncStackTraceInfo(promise, null);
         }
      }

      return JSFunctionData.createCallOnly(context, new PromiseResolveRootNode().getCallTarget(), 1, Strings.EMPTY_STRING);
   }

   private static JSFunctionData createPromiseResolveThenableJobImpl(JSContext context) {
      class PromiseResolveThenableJob extends JavaScriptRootNode {
         @Node.Child
         private PropertyGetNode getPromiseToResolveNode = PropertyGetNode.createGetHidden(CreateResolvingFunctionNode.PROMISE_KEY, context);
         @Node.Child
         private PropertyGetNode getThenableNode = PropertyGetNode.createGetHidden(CreateResolvingFunctionNode.THENABLE_KEY, context);
         @Node.Child
         private PropertyGetNode getThenNode = PropertyGetNode.createGetHidden(CreateResolvingFunctionNode.THEN_KEY, context);
         @Node.Child
         private PromiseResolveThenableNode promiseResolveThenable = PromiseResolveThenableNode.create(context);

         @Override
         public Object execute(VirtualFrame frame) {
            JSDynamicObject functionObject = JSFrameUtil.getFunctionObject(frame);
            JSDynamicObject promiseToResolve = (JSDynamicObject)this.getPromiseToResolveNode.getValue(functionObject);
            Object thenable = this.getThenableNode.getValue(functionObject);
            Object then = this.getThenNode.getValue(functionObject);
            return this.promiseResolveThenable.execute(promiseToResolve, thenable, then);
         }
      }

      return JSFunctionData.createCallOnly(context, new PromiseResolveThenableJob().getCallTarget(), 0, Strings.EMPTY_STRING);
   }

   private JSFunctionObject createPromiseRejectFunction(JSDynamicObject promise, CreateResolvingFunctionNode.AlreadyResolved alreadyResolved) {
      JSFunctionData functionData = this.context
         .getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.PromiseRejectFunction, c -> createPromiseRejectFunctionImpl(c));
      JSFunctionObject function = JSFunction.create(this.getRealm(), functionData);
      this.setPromiseNode.setValue(function, promise);
      this.setAlreadyResolvedNode.setValue(function, alreadyResolved);
      return function;
   }

   private static JSFunctionData createPromiseRejectFunctionImpl(JSContext context) {
      class PromiseRejectRootNode extends JavaScriptRootNode implements AsyncHandlerRootNode {
         @Node.Child
         private JavaScriptNode reasonNode;
         @Node.Child
         private PropertyGetNode getPromiseNode;
         @Node.Child
         private PropertyGetNode getAlreadyResolvedNode = PropertyGetNode.createGetHidden(CreateResolvingFunctionNode.ALREADY_RESOLVED_KEY, context);
         @Node.Child
         private RejectPromiseNode rejectPromiseNode;
         private final ConditionProfile alreadyResolvedProfile = ConditionProfile.createBinaryProfile();

         @Override
         public Object execute(VirtualFrame frame) {
            this.init();
            JSDynamicObject functionObject = JSFrameUtil.getFunctionObject(frame);
            JSDynamicObject promise = (JSDynamicObject)this.getPromiseNode.getValue(functionObject);
            Object reason = this.reasonNode.execute(frame);
            CreateResolvingFunctionNode.AlreadyResolved alreadyResolved = (CreateResolvingFunctionNode.AlreadyResolved)this.getAlreadyResolvedNode
               .getValue(functionObject);
            if (this.alreadyResolvedProfile.profile(alreadyResolved.value)) {
               context.notifyPromiseRejectionTracker(promise, 2, reason);
               return Undefined.instance;
            } else {
               alreadyResolved.value = true;
               return this.rejectPromiseNode.execute(promise, reason);
            }
         }

         public void init() {
            if (this.reasonNode == null || this.getPromiseNode == null || this.rejectPromiseNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.reasonNode = this.insert(AccessIndexedArgumentNode.create(0));
               this.getPromiseNode = this.insert(PropertyGetNode.createGetHidden(CreateResolvingFunctionNode.PROMISE_KEY, context));
               this.rejectPromiseNode = this.insert(RejectPromiseNode.create(context));
            }
         }

         @Override
         public AsyncHandlerRootNode.AsyncStackTraceInfo getAsyncStackTraceInfo(JSFunctionObject handlerFunction) {
            assert JSFunction.isJSFunction(handlerFunction)
               && ((RootCallTarget)JSFunction.getFunctionData(handlerFunction).getCallTarget()).getRootNode() == this;

            JSDynamicObject promise = (JSDynamicObject)JSObjectUtil.getHiddenProperty(handlerFunction, CreateResolvingFunctionNode.PROMISE_KEY);
            return new AsyncHandlerRootNode.AsyncStackTraceInfo(promise, null);
         }
      }

      return JSFunctionData.createCallOnly(context, new PromiseRejectRootNode().getCallTarget(), 1, Strings.EMPTY_STRING);
   }

   static final class AlreadyResolved {
      boolean value;
   }
}
