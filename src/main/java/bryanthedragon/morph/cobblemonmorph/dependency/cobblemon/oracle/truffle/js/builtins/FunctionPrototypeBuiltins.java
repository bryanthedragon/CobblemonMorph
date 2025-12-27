package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.HasPropertyCacheNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.binary.InstanceofNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectArrayNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.SuppressFBWarnings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

public final class FunctionPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<FunctionPrototypeBuiltins.FunctionPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new FunctionPrototypeBuiltins();
   public static final JSBuiltinsContainer BUILTINS_NASHORN_COMPAT = new FunctionPrototypeBuiltins.FunctionPrototypeNashornCompatBuiltins();

   protected FunctionPrototypeBuiltins() {
      super(JSFunction.PROTOTYPE_NAME, FunctionPrototypeBuiltins.FunctionPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, FunctionPrototypeBuiltins.FunctionPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case bind:
            return FunctionPrototypeBuiltinsFactory.JSBindNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).varArgs().createArgumentNodes(context)
            );
         case toString:
            return FunctionPrototypeBuiltinsFactory.JSFunctionToStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case apply:
            return FunctionPrototypeBuiltinsFactory.JSApplyNodeGen.create(context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context));
         case call:
            return FunctionPrototypeBuiltinsFactory.JSCallNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).varArgs().createArgumentNodes(context)
            );
         case _hasInstance:
            return FunctionPrototypeBuiltinsFactory.HasInstanceNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum FunctionPrototype implements BuiltinEnum<FunctionPrototypeBuiltins.FunctionPrototype> {
      bind(1),
      toString(0),
      apply(2),
      call(1),
      _hasInstance(1) {
         @Override
         public Object getKey() {
            return Symbol.SYMBOL_HAS_INSTANCE;
         }

         @Override
         public boolean isWritable() {
            return false;
         }

         @Override
         public boolean isConfigurable() {
            return false;
         }
      };

      private final int length;

      private FunctionPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public int getECMAScriptVersion() {
         return this == _hasInstance ? 6 : BuiltinEnum.super.getECMAScriptVersion();
      }
   }

   public static final class FunctionPrototypeNashornCompatBuiltins
      extends JSBuiltinsContainer.SwitchEnum<FunctionPrototypeBuiltins.FunctionPrototypeNashornCompatBuiltins.FunctionNashornCompat> {
      protected FunctionPrototypeNashornCompatBuiltins() {
         super(FunctionPrototypeBuiltins.FunctionPrototypeNashornCompatBuiltins.FunctionNashornCompat.class);
      }

      protected Object createNode(
         JSContext context,
         JSBuiltin builtin,
         boolean construct,
         boolean newTarget,
         FunctionPrototypeBuiltins.FunctionPrototypeNashornCompatBuiltins.FunctionNashornCompat builtinEnum
      ) {
         switch (builtinEnum) {
            case toSource:
               return FunctionPrototypeBuiltinsFactory.JSFunctionToStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
            default:
               return null;
         }
      }

      public static enum FunctionNashornCompat implements BuiltinEnum<FunctionPrototypeBuiltins.FunctionPrototypeNashornCompatBuiltins.FunctionNashornCompat> {
         toSource(0);

         private final int length;

         private FunctionNashornCompat(int length) {
            this.length = length;
         }

         @Override
         public int getLength() {
            return this.length;
         }
      }
   }

   public abstract static class HasInstanceNode extends JSBuiltinNode {
      @Node.Child
      InstanceofNode.OrdinaryHasInstanceNode ordinaryHasInstanceNode;

      public HasInstanceNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.ordinaryHasInstanceNode = InstanceofNode.OrdinaryHasInstanceNode.create(context);
      }

      @Specialization
      protected boolean hasInstance(Object thisObj, Object value) {
         return this.ordinaryHasInstanceNode.executeBoolean(value, thisObj);
      }
   }

   public abstract static class JSApplyNode extends JSBuiltinNode {
      @Node.Child
      private JSFunctionCallNode call = JSFunctionCallNode.createCall();
      @Node.Child
      private JSToObjectArrayNode toObjectArray;

      public JSApplyNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.toObjectArray = JSToObjectArrayNode.create(context, true);
      }

      @Specialization(guards = "isJSFunction(function)")
      protected Object applyFunction(JSDynamicObject function, Object target, Object args) {
         return this.apply(function, target, args);
      }

      @Specialization(guards = "isCallable.executeBoolean(function)", replaces = "applyFunction", limit = "1")
      protected Object applyCallable(Object function, Object target, Object args, @Cached @Cached.Shared("isCallable") IsCallableNode isCallable) {
         return this.apply(function, target, args);
      }

      private Object apply(Object function, Object target, Object args) {
         Object[] applyUserArgs = this.toObjectArray.executeObjectArray(args);

         assert applyUserArgs.length <= this.getContext().getContextOptions().getMaxApplyArgumentLength();

         Object[] passedOnArguments = JSArguments.create(target, function, applyUserArgs);
         return this.call.executeCall(passedOnArguments);
      }

      @Specialization(guards = "!isCallable.executeBoolean(function)", limit = "1")
      protected Object error(Object function, Object target, Object args, @Cached @Cached.Shared("isCallable") IsCallableNode isCallable) {
         throw Errors.createTypeErrorNotAFunction(function);
      }

      @Override
      public boolean countsTowardsStackTraceLimit() {
         return false;
      }
   }

   public abstract static class JSBindNode extends JSBuiltinNode {
      @Node.Child
      private GetPrototypeNode getPrototypeNode;
      @Node.Child
      private HasPropertyCacheNode hasFunctionLengthNode;
      @Node.Child
      private PropertyGetNode getFunctionLengthNode;
      @Node.Child
      private PropertyGetNode getFunctionNameNode;
      private final ConditionProfile mustSetLengthProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile setNameProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile hasFunctionLengthProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile hasIntegerFunctionLengthProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile isConstructorProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile isAsyncProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile setProtoProfile = ConditionProfile.createBinaryProfile();

      public JSBindNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.getPrototypeNode = GetPrototypeNode.create();
         this.hasFunctionLengthNode = HasPropertyCacheNode.create(JSFunction.LENGTH, context, true);
         this.getFunctionLengthNode = PropertyGetNode.create(JSFunction.LENGTH, false, context);
         this.getFunctionNameNode = PropertyGetNode.create(JSFunction.NAME, false, context);
      }

      @Specialization
      @SuppressFBWarnings(value = "ES_COMPARING_STRINGS_WITH_EQ", justification = "fast path")
      protected JSDynamicObject bindFunction(JSFunctionObject thisFnObj, Object thisArg, Object[] args) {
         JSDynamicObject proto = this.getPrototypeNode.execute((JSDynamicObject)thisFnObj);
         JSDynamicObject boundFunction = JSFunction.boundFunctionCreate(
            this.getContext(), thisFnObj, thisArg, args, proto, this.isConstructorProfile, this.isAsyncProfile, this.setProtoProfile, this
         );
         Number length = 0;
         boolean mustSetLength = true;
         if (this.hasFunctionLengthProfile.profile(this.hasFunctionLengthNode.hasProperty(thisFnObj))) {
            Object targetLen = this.getFunctionLengthNode.getValue(thisFnObj);
            if (this.hasIntegerFunctionLengthProfile.profile(targetLen instanceof Integer)) {
               int targetLenAsInt = (Integer)targetLen;
               if (targetLenAsInt == JSFunction.getLength(thisFnObj)) {
                  mustSetLength = false;
               } else {
                  length = Math.max(0, Math.max(0, targetLenAsInt) - args.length);
               }
            } else if (JSRuntime.isNumber(targetLen)) {
               double targetLenAsInt = toIntegerOrInfinity((Number)targetLen);
               if (targetLenAsInt != Double.NEGATIVE_INFINITY) {
                  length = JSRuntime.doubleToNarrowestNumber(Math.max(0.0, targetLenAsInt - args.length));
               }
            }
         }

         if (this.mustSetLengthProfile.profile(mustSetLength)) {
            JSFunction.setFunctionLength(boundFunction, length);
         }

         Object targetName = this.getFunctionNameNode.getValue(thisFnObj);
         if (!JSGuards.isString(targetName)) {
            targetName = Strings.EMPTY_STRING;
         }

         if (this.setNameProfile.profile(targetName != JSFunction.getName(thisFnObj))) {
            ((JSFunctionObject.Bound)boundFunction).setTargetName((TruffleString)targetName);
         }

         return boundFunction;
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization(guards = "isJSProxy(thisObj)")
      protected JSDynamicObject bindProxy(JSDynamicObject thisObj, Object thisArg, Object[] args) {
         JSDynamicObject proto = JSObject.getPrototype(thisObj);
         Object target = JSProxy.getTarget(thisObj);

         Object innerFunction;
         for (innerFunction = target; !JSFunction.isJSFunction(innerFunction); innerFunction = JSProxy.getTarget((JSDynamicObject)innerFunction)) {
            if (!JSProxy.isJSProxy(innerFunction)) {
               throw Errors.createTypeErrorNotAFunction(thisObj);
            }
         }

         assert JSFunction.isJSFunction(innerFunction);

         JSDynamicObject boundFunction = JSFunction.boundFunctionCreate(
            this.getContext(),
            (JSFunctionObject)innerFunction,
            thisArg,
            args,
            proto,
            this.isConstructorProfile,
            this.isAsyncProfile,
            this.setProtoProfile,
            this
         );
         Number length = 0;
         boolean targetHasLength = JSObject.hasOwnProperty(thisObj, JSFunction.LENGTH);
         if (targetHasLength) {
            Object targetLen = JSObject.get(thisObj, JSFunction.LENGTH);
            if (JSRuntime.isNumber(targetLen)) {
               double targetLenAsInt = toIntegerOrInfinity((Number)targetLen);
               if (targetLenAsInt != Double.NEGATIVE_INFINITY) {
                  length = JSRuntime.doubleToNarrowestNumber(Math.max(0.0, targetLenAsInt - args.length));
               }
            }
         }

         JSFunction.setFunctionLength(boundFunction, length);
         Object targetName = JSObject.get(thisObj, JSFunction.NAME);
         if (!Strings.isTString(targetName)) {
            targetName = Strings.EMPTY_STRING;
         }

         JSFunction.setBoundFunctionName(boundFunction, (TruffleString)targetName);
         return boundFunction;
      }

      @Specialization(guards = {"!isJSFunction(thisObj)", "!isJSProxy(thisObj)"})
      protected JSDynamicObject bindError(Object thisObj, Object thisArg, Object[] arg) {
         throw Errors.createTypeErrorNotAFunction(thisObj);
      }

      private static double toIntegerOrInfinity(Number number) {
         if (number instanceof Double) {
            double doubleValue = (Double)number;
            return Double.isNaN(doubleValue) ? 0.0 : JSRuntime.truncateDouble(doubleValue);
         } else {
            return JSRuntime.doubleValue(number);
         }
      }
   }

   public abstract static class JSCallNode extends JSBuiltinNode {
      @Node.Child
      private JSFunctionCallNode callNode = JSFunctionCallNode.createCall();

      public JSCallNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object call(Object function, Object target, Object[] args) {
         return this.callNode.executeCall(JSArguments.create(target, function, args));
      }

      @Override
      public boolean countsTowardsStackTraceLimit() {
         return false;
      }
   }

   public abstract static class JSFunctionToStringNode extends JSBuiltinNode {
      public JSFunctionToStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected boolean isBoundTarget(JSDynamicObject fnObj) {
         return JSFunction.isBoundFunction(fnObj);
      }

      @Specialization(guards = {"isJSFunction(fnObj)", "!isBoundTarget(fnObj)"})
      protected TruffleString toStringDefault(JSDynamicObject fnObj) {
         return toStringDefaultTarget(fnObj);
      }

      @Specialization(guards = {"isJSFunction(fnObj)", "isBoundTarget(fnObj)"})
      protected TruffleString toStringBound(JSDynamicObject fnObj) {
         if (this.getContext().isOptionV8CompatibilityMode()) {
            return Strings.FUNCTION_NATIVE_CODE;
         } else {
            TruffleString name = JSFunction.getName(fnObj);
            return getNameIntl(name);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private static TruffleString getNameIntl(TruffleString name) {
         int spacePos = Strings.lastIndexOf(name, 32);
         return Strings.concatAll(Strings.FUNCTION_SPC, spacePos < 0 ? name : Strings.lazySubstring(name, spacePos + 1), Strings.FUNCTION_NATIVE_CODE_BODY);
      }

      @Specialization(guards = {"isES2019OrLater()", "!isJSFunction(fnObj)", "isCallable.executeBoolean(fnObj)"}, limit = "1")
      protected TruffleString toStringCallable(
         Object fnObj, @Cached @Cached.Shared("isCallable") IsCallableNode isCallable, @CachedLibrary("fnObj") InteropLibrary interop
      ) {
         if (interop.hasExecutableName(fnObj)) {
            try {
               Object name = interop.getExecutableName(fnObj);
               return getNameIntl(InteropLibrary.getUncached().asTruffleString(name));
            } catch (UnsupportedMessageException var6) {
            }
         } else if (interop.isMetaObject(fnObj)) {
            try {
               Object name = interop.getMetaSimpleName(fnObj);
               return getNameIntl(InteropLibrary.getUncached().asTruffleString(name));
            } catch (UnsupportedMessageException var5) {
            }
         }

         return Strings.FUNCTION_NATIVE_CODE;
      }

      @Specialization(guards = {"isES2019OrLater()", "!isCallable.executeBoolean(fnObj)"}, limit = "1")
      protected TruffleString toStringNotCallable(Object fnObj, @Cached @Cached.Shared("isCallable") IsCallableNode isCallable) {
         throw Errors.createTypeErrorNotAFunction(fnObj);
      }

      @Specialization(guards = {"!isES2019OrLater()", "!isJSFunction(fnObj)"})
      protected TruffleString toStringNotFunction(Object fnObj) {
         throw Errors.createTypeErrorNotAFunction(fnObj);
      }

      final boolean isES2019OrLater() {
         return this.getContext().getEcmaScriptVersion() >= 10;
      }

      @CompilerDirectives.TruffleBoundary
      private static TruffleString toStringDefaultTarget(JSDynamicObject fnObj) {
         CallTarget ct = JSFunction.getCallTarget(fnObj);
         if (!(ct instanceof RootCallTarget)) {
            return Strings.fromJavaString(ct.toString());
         } else {
            RootCallTarget dct = (RootCallTarget)ct;
            RootNode rn = dct.getRootNode();
            SourceSection ssect = rn.getSourceSection();
            TruffleString result;
            if (ssect != null && ssect.isAvailable() && !ssect.getSource().isInternal()) {
               result = Strings.fromCharSequence(ssect.getCharacters());
            } else {
               result = Strings.concatAll(Strings.FUNCTION_SPC, JSFunction.getName(fnObj), Strings.FUNCTION_NATIVE_CODE_BODY);
            }

            return result;
         }
      }
   }
}
