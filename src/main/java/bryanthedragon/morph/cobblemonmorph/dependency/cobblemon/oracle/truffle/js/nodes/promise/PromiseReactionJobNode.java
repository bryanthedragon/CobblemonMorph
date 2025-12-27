package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.control.AwaitNode;
import com.oracle.truffle.js.nodes.control.TryCatchNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.PromiseReactionRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;

public class PromiseReactionJobNode extends JavaScriptBaseNode {
   static final HiddenKey REACTION_KEY = new HiddenKey("Reaction");
   static final HiddenKey ARGUMENT_KEY = new HiddenKey("Argument");
   private final JSContext context;
   @Node.Child
   private PropertySetNode setReaction;
   @Node.Child
   private PropertySetNode setArgument;

   protected PromiseReactionJobNode(JSContext context) {
      this.context = context;
      this.setReaction = PropertySetNode.createSetHidden(REACTION_KEY, context);
      this.setArgument = PropertySetNode.createSetHidden(ARGUMENT_KEY, context);
   }

   public static PromiseReactionJobNode create(JSContext context) {
      return new PromiseReactionJobNode(context);
   }

   public JSFunctionObject execute(Object reaction, Object argument) {
      JSFunctionData functionData = this.context
         .getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.PromiseReactionJob, c -> createPromiseReactionJobImpl(c));
      JSFunctionObject function = JSFunction.create(this.getRealm(), functionData);
      this.setReaction.setValue(function, reaction);
      this.setArgument.setValue(function, argument);
      return function;
   }

   private static JSFunctionData createPromiseReactionJobImpl(JSContext context) {
      return JSFunctionData.createCallOnly(context, new PromiseReactionJobNode.PromiseReactionJobRootNode(context).getCallTarget(), 0, Strings.EMPTY_STRING);
   }

   public static class PromiseReactionJobRootNode extends JavaScriptRootNode implements InstrumentableNode {
      private final JSContext context;
      @Node.Child
      private PropertyGetNode getReaction;
      @Node.Child
      private PropertyGetNode getArgument;
      @Node.Child
      private JSFunctionCallNode callResolveNode;
      @Node.Child
      private JSFunctionCallNode callRejectNode;
      @Node.Child
      private JSFunctionCallNode callHandlerNode;
      @Node.Child
      private TryCatchNode.GetErrorObjectNode getErrorObjectNode;
      private final ConditionProfile handlerProf = ConditionProfile.createBinaryProfile();

      PromiseReactionJobRootNode(JSContext context) {
         super(context.getLanguage(), null, null);
         this.context = context;
         this.getReaction = PropertyGetNode.createGetHidden(PromiseReactionJobNode.REACTION_KEY, context);
         this.getArgument = PropertyGetNode.createGetHidden(PromiseReactionJobNode.ARGUMENT_KEY, context);
      }

      @Override
      public Object execute(VirtualFrame frame) {
         JSDynamicObject functionObject = JSFrameUtil.getFunctionObject(frame);
         PromiseReactionRecord reaction = (PromiseReactionRecord)this.getReaction.getValue(functionObject);
         Object argument = this.getArgument.getValue(functionObject);
         PromiseCapabilityRecord promiseCapability = reaction.getCapability();
         Object handler = reaction.getHandler();

         assert promiseCapability != null || handler != Undefined.instance;

         if (promiseCapability != null) {
            this.context.notifyPromiseHook(2, promiseCapability.getPromise());
         }

         Object handlerResult;
         boolean fulfill;
         if (this.handlerProf.profile(handler == Undefined.instance)) {
            handlerResult = argument;
            fulfill = reaction.isFulfill();
         } else {
            try {
               handlerResult = this.callHandler().executeCall(JSArguments.createOneArg(Undefined.instance, handler, argument));
               if (promiseCapability == null) {
                  return Undefined.instance;
               }

               fulfill = true;
            } catch (AbstractTruffleException var10) {
               if (promiseCapability == null) {
                  assert this.context.isOptionTopLevelAwait();

                  throw var10;
               }

               handlerResult = this.getErrorObject().execute(var10);
               fulfill = false;
            }
         }

         Object status;
         if (fulfill) {
            status = this.callResolve().executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getResolve(), handlerResult));
         } else {
            status = this.callReject().executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getReject(), handlerResult));
         }

         this.context.notifyPromiseHook(3, promiseCapability.getPromise());
         return status;
      }

      private JSFunctionCallNode callResolve() {
         if (this.callResolveNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callResolveNode = this.insert(JSFunctionCallNode.createCall());
         }

         return this.callResolveNode;
      }

      private JSFunctionCallNode callReject() {
         if (this.callRejectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callRejectNode = this.insert(JSFunctionCallNode.createCall());
         }

         return this.callRejectNode;
      }

      private JSFunctionCallNode callHandler() {
         if (this.callHandlerNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callHandlerNode = this.insert(JSFunctionCallNode.createCall());
         }

         return this.callHandlerNode;
      }

      private TryCatchNode.GetErrorObjectNode getErrorObject() {
         if (this.getErrorObjectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(this.context));
         }

         return this.getErrorObjectNode;
      }

      @Override
      public boolean isCaptureFramesForTrace() {
         return this.context.isOptionAsyncStackTraces();
      }

      @Override
      protected List<TruffleStackTraceElement> findAsynchronousFrames(Frame frame) {
         if (!this.context.isOptionAsyncStackTraces()) {
            return null;
         } else {
            JSDynamicObject functionObject = JSFrameUtil.getFunctionObject(frame);
            PromiseReactionRecord reaction = (PromiseReactionRecord)this.getReaction.getValue(functionObject);
            PromiseCapabilityRecord promiseCapability = reaction.getCapability();
            if (promiseCapability != null) {
               return AwaitNode.findAsyncStackFramesFromPromise(promiseCapability.getPromise());
            } else {
               return JSFunction.isJSFunction(reaction.getHandler())
                  ? AwaitNode.findAsyncStackFramesFromHandler((JSFunctionObject)reaction.getHandler())
                  : null;
            }
         }
      }

      @Override
      public boolean hasTag(Class<? extends Tag> tag) {
         return tag == StandardTags.RootTag.class;
      }

      @Override
      public boolean isInstrumentable() {
         return false;
      }

      @Override
      public InstrumentableNode.WrapperNode createWrapper(ProbeNode probe) {
         throw Errors.shouldNotReachHere();
      }
   }
}
