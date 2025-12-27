package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.interop.InteropAsyncFunction;
import com.oracle.truffle.js.runtime.interop.InteropBoundFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ImportStatic({JSGuards.class, JSFunction.class})
@GenerateUncached
public abstract class ExportValueNode extends JavaScriptBaseNode {
   ExportValueNode() {
   }

   public final Object execute(Object value) {
      return this.execute(value, Undefined.instance, false);
   }

   public abstract Object execute(Object value, Object thiz, boolean bindMemberFunctions);

   protected final boolean isInteropCompletePromises() {
      return this.getLanguage().getJSContext().getContextOptions().isMLEMode();
   }

   @Specialization(guards = {"!bindFunctions", "!isInteropCompletePromises() || !isAsyncFunction(function)"})
   protected static JSDynamicObject doFunctionNoBind(JSFunctionObject function, Object thiz, boolean bindFunctions) {
      return function;
   }

   @Specialization(guards = {"bindFunctions", "isUndefined(thiz)", "!isInteropCompletePromises() || !isAsyncFunction(function)"})
   protected static JSDynamicObject doFunctionUndefinedThis(JSFunctionObject function, Object thiz, boolean bindFunctions) {
      return function;
   }

   @Specialization(
      guards = {"bindFunctions", "!isUndefined(thiz)", "!isBoundJSFunction(function)", "!isInteropCompletePromises() || !isAsyncFunction(function)"}
   )
   protected static TruffleObject doBindUnboundFunction(JSFunctionObject function, Object thiz, boolean bindFunctions) {
      return new InteropBoundFunction(function, thiz);
   }

   @Specialization(guards = {"bindFunctions", "isBoundJSFunction(function)", "!isInteropCompletePromises() || !isAsyncFunction(function)"})
   protected static JSDynamicObject doBoundFunction(JSFunctionObject function, Object thiz, boolean bindFunctions) {
      return function;
   }

   @Specialization(guards = {"isInteropCompletePromises()", "isAsyncFunction(function)"})
   protected static TruffleObject doAsyncFunction(JSFunctionObject function, Object thiz, boolean bindFunctions) {
      return new InteropAsyncFunction(function);
   }

   @Specialization
   protected static double doSafeInteger(SafeInteger value, Object thiz, boolean bindFunctions) {
      return value.doubleValue();
   }

   @Specialization(guards = "!isJSFunction(value)")
   protected static JSDynamicObject doObject(JSDynamicObject value, Object thiz, boolean bindFunctions) {
      return value;
   }

   @Specialization
   protected static int doInt(int value, Object thiz, boolean bindFunctions) {
      return value;
   }

   @Specialization
   protected static long doLong(long value, Object thiz, boolean bindFunctions) {
      return value;
   }

   @Specialization
   protected static float doFloat(float value, Object thiz, boolean bindFunctions) {
      return value;
   }

   @Specialization
   protected static double doDouble(double value, Object thiz, boolean bindFunctions) {
      return value;
   }

   @Specialization
   protected static boolean doBoolean(boolean value, Object thiz, boolean bindFunctions) {
      return value;
   }

   @Specialization
   protected static BigInt doBigInt(BigInt value, Object thiz, boolean bindFunctions) {
      return value;
   }

   @Specialization
   protected static TruffleString doString(TruffleString value, Object thiz, boolean bindFunctions) {
      return value;
   }

   @Specialization(guards = "!isJSFunction(value)", replaces = "doObject")
   protected static TruffleObject doTruffleObject(TruffleObject value, Object thiz, boolean bindFunctions) {
      return value;
   }

   @CompilerDirectives.TruffleBoundary
   @Specialization(
      guards = {"!isTruffleObject(thiz)", "!isString(thiz)", "!isBoolean(thiz)", "!isNumberDouble(thiz)", "!isNumberLong(thiz)", "!isNumberInteger(thiz)"}
   )
   protected static Object doOther(Object value, Object thiz, boolean bindFunctions) {
      throw Errors.createTypeErrorFormat("Cannot convert to TruffleObject: %s", value == null ? null : value.getClass().getSimpleName());
   }

   public static ExportValueNode create() {
      return ExportValueNodeGen.create();
   }

   public static ExportValueNode getUncached() {
      return ExportValueNodeGen.getUncached();
   }
}
