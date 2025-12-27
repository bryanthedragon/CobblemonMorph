package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSGlobal;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class CallSitePrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<CallSitePrototypeBuiltins.CallSitePrototype> {
   public static final JSBuiltinsContainer BUILTINS = new CallSitePrototypeBuiltins();

   protected CallSitePrototypeBuiltins() {
      super(JSError.CALL_SITE_PROTOTYPE_NAME, CallSitePrototypeBuiltins.CallSitePrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, CallSitePrototypeBuiltins.CallSitePrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case getColumnNumber:
         case getLineNumber:
         case getPosition:
            return CallSitePrototypeBuiltinsFactory.CallSiteGetNumberNodeGen.create(
               context, builtin, builtinEnum, args().withThis().createArgumentNodes(context)
            );
         case getFunction:
         case getThis:
         case getFileName:
         case getFunctionName:
         case getMethodName:
         case getTypeName:
         case toString:
         case getEvalOrigin:
         case getScriptNameOrSourceURL:
         case getPromiseIndex:
            return CallSitePrototypeBuiltinsFactory.CallSiteGetNodeGen.create(context, builtin, builtinEnum, args().withThis().createArgumentNodes(context));
         case isToplevel:
         case isEval:
         case isNative:
         case isConstructor:
         case isAsync:
         case isPromiseAll:
            return CallSitePrototypeBuiltinsFactory.CallSiteGetBooleanNodeGen.create(
               context, builtin, builtinEnum, args().withThis().createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   abstract static class CallSiteGetBooleanNode extends CallSitePrototypeBuiltins.CallSiteOperation {
      private final CallSitePrototypeBuiltins.CallSitePrototype method;

      CallSiteGetBooleanNode(JSContext context, JSBuiltin builtin, CallSitePrototypeBuiltins.CallSitePrototype method) {
         super(context, builtin);
         this.method = method;
      }

      @Specialization
      final boolean getBoolean(JSDynamicObject thisObj) {
         GraalJSException.JSStackTraceElement stackTraceElement = this.getStackTraceElement(thisObj);
         switch (this.method) {
            case isToplevel:
               return JSRuntime.isNullOrUndefined(stackTraceElement.getThis())
                  || JSGlobal.isJSGlobalObject(stackTraceElement.getThis())
                  || stackTraceElement.isEval();
            case isEval:
               return stackTraceElement.isEval();
            case isNative:
               return JSFunction.isJSFunction(stackTraceElement.getFunction()) && JSFunction.isBuiltin((JSFunctionObject)stackTraceElement.getFunction());
            case isConstructor:
               return stackTraceElement.isConstructor();
            case isAsync:
               return stackTraceElement.isAsync();
            case isPromiseAll:
               return stackTraceElement.isPromiseAll();
            default:
               throw Errors.shouldNotReachHere();
         }
      }
   }

   abstract static class CallSiteGetNode extends CallSitePrototypeBuiltins.CallSiteOperation {
      private final CallSitePrototypeBuiltins.CallSitePrototype method;

      CallSiteGetNode(JSContext context, JSBuiltin builtin, CallSitePrototypeBuiltins.CallSitePrototype method) {
         super(context, builtin);
         this.method = method;
      }

      @Specialization
      final Object getFunctionName(JSDynamicObject thisObj) {
         GraalJSException.JSStackTraceElement stackTraceElement = this.getStackTraceElement(thisObj);
         switch (this.method) {
            case getFunction:
               if (stackTraceElement.isStrict()) {
                  return Undefined.instance;
               }

               return JSRuntime.nullToUndefined(stackTraceElement.getFunction());
            case getThis:
               if (stackTraceElement.isStrict()) {
                  return Undefined.instance;
               }

               return JSRuntime.nullToUndefined(stackTraceElement.getThisOrGlobal());
            case getFileName:
            case getScriptNameOrSourceURL:
               TruffleString fileName = stackTraceElement.getFileName();
               if (fileName != null && !Strings.startsWith(fileName, Strings.ANGLE_BRACKET_OPEN)) {
                  return fileName;
               }

               return Null.instance;
            case getFunctionName:
               TruffleString functionName = stackTraceElement.getFunctionName();
               return Strings.isEmpty(functionName) ? Null.instance : functionName;
            case getMethodName:
               JSContext context = this.getContext();
               TruffleString methodName = stackTraceElement.getMethodName(context);
               if (methodName != null && !Strings.isEmpty(methodName) && !methodName.equals(JSError.getAnonymousFunctionNameStackTrace(context))) {
                  return methodName;
               }

               return Null.instance;
            case getTypeName:
               return JSRuntime.toJSNull(stackTraceElement.getTypeName());
            case toString:
               return stackTraceElement.toString(this.getContext());
            case getEvalOrigin:
               TruffleString evalOrigin = stackTraceElement.getEvalOrigin();
               if (evalOrigin != null) {
                  return evalOrigin;
               }

               return Undefined.instance;
            case getPromiseIndex:
               if (stackTraceElement.isPromiseAll()) {
                  return stackTraceElement.getPromiseIndex();
               }

               return Null.instance;
            default:
               throw Errors.shouldNotReachHere();
         }
      }
   }

   abstract static class CallSiteGetNumberNode extends CallSitePrototypeBuiltins.CallSiteOperation {
      private final CallSitePrototypeBuiltins.CallSitePrototype method;

      CallSiteGetNumberNode(JSContext context, JSBuiltin builtin, CallSitePrototypeBuiltins.CallSitePrototype method) {
         super(context, builtin);
         this.method = method;
      }

      @Specialization
      final int getNumber(JSDynamicObject thisObj) {
         GraalJSException.JSStackTraceElement stackTraceElement = this.getStackTraceElement(thisObj);
         switch (this.method) {
            case getColumnNumber:
               return stackTraceElement.getColumnNumber();
            case getLineNumber:
               return stackTraceElement.getLineNumber();
            case getPosition:
               return stackTraceElement.getPosition();
            default:
               throw Errors.shouldNotReachHere();
         }
      }
   }

   public abstract static class CallSiteOperation extends JSBuiltinNode {
      @Node.Child
      private PropertyGetNode getStackTraceElementNode;
      private final BranchProfile errorBranch = BranchProfile.create();

      public CallSiteOperation(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.getStackTraceElementNode = PropertyGetNode.createGetHidden(JSError.STACK_TRACE_ELEMENT_PROPERTY_NAME, context);
      }

      protected final GraalJSException.JSStackTraceElement getStackTraceElement(JSDynamicObject thisObj) {
         Object element = this.getStackTraceElementNode.getValue(thisObj);
         if (!(element instanceof GraalJSException.JSStackTraceElement)) {
            this.errorBranch.enter();
            throw Errors.createTypeError("Expected CallSite as receiver");
         } else {
            return (GraalJSException.JSStackTraceElement)element;
         }
      }
   }

   public static enum CallSitePrototype implements BuiltinEnum<CallSitePrototypeBuiltins.CallSitePrototype> {
      getThis(0),
      getTypeName(0),
      getFunction(0),
      getFunctionName(0),
      getMethodName(0),
      getFileName(0),
      getLineNumber(0),
      getColumnNumber(0),
      getPosition(0),
      getEvalOrigin(0),
      getScriptNameOrSourceURL(0),
      getPromiseIndex(0),
      isToplevel(0),
      isEval(0),
      isNative(0),
      isConstructor(0),
      isAsync(0),
      isPromiseAll(0),
      toString(0);

      private final int length;

      private CallSitePrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
