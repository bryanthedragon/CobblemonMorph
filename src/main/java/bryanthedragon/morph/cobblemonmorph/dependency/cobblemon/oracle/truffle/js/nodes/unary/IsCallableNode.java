package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@ImportStatic(JSConfig.class)
@GenerateUncached
public abstract class IsCallableNode extends JavaScriptBaseNode {
   protected IsCallableNode() {
   }

   public abstract boolean executeBoolean(Object operand);

   @Specialization(guards = {"shape.check(function)", "isJSFunctionShape(shape)"}, limit = "1")
   protected static boolean doJSFunctionShape(JSDynamicObject function, @Cached("function.getShape()") Shape shape) {
      return true;
   }

   @Specialization(guards = "isJSFunction(function)", replaces = "doJSFunctionShape")
   protected static boolean doJSFunction(JSDynamicObject function) {
      return true;
   }

   @Specialization(guards = "isJSProxy(proxy)")
   protected static boolean doJSProxy(JSDynamicObject proxy) {
      return JSRuntime.isCallableProxy(proxy);
   }

   @Specialization(guards = {"isJSDynamicObject(object)", "!isJSFunction(object)", "!isJSProxy(object)"})
   protected static boolean doJSTypeOther(JSDynamicObject object) {
      return false;
   }

   @Specialization(guards = "isForeignObject(obj)", limit = "InteropLibraryLimit")
   protected static boolean doTruffleObject(Object obj, @CachedLibrary("obj") InteropLibrary interop) {
      return interop.isExecutable(obj) || interop.isInstantiable(obj);
   }

   @Specialization
   protected static boolean doString(TruffleString string) {
      return false;
   }

   @Specialization
   protected static boolean doNumber(Number number) {
      return false;
   }

   @Specialization
   protected static boolean doBoolean(boolean value) {
      return false;
   }

   @Specialization
   protected static boolean doSymbol(Symbol symbol) {
      return false;
   }

   @Specialization
   protected static boolean doBigInt(BigInt bigInt) {
      return false;
   }

   public static IsCallableNode create() {
      return IsCallableNodeGen.create();
   }
}
