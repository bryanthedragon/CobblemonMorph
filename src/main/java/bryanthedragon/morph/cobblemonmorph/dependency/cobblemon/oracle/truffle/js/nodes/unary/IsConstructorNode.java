package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@ImportStatic(JSConfig.class)
@GenerateUncached
public abstract class IsConstructorNode extends JavaScriptBaseNode {
   protected IsConstructorNode() {
   }

   public abstract boolean executeBoolean(Object operand);

   @Specialization
   protected static boolean doJSFunction(JSFunctionObject function) {
      return JSFunction.isConstructor((JSDynamicObject)function);
   }

   @Specialization
   protected static boolean doJSProxy(JSProxyObject proxy) {
      return JSRuntime.isConstructorProxy(proxy);
   }

   @Specialization(guards = {"isJSDynamicObject(other)", "!isJSFunction(other)", "!isJSProxy(other)"})
   protected static boolean doOther(Object other) {
      return false;
   }

   @Specialization
   protected static boolean doString(TruffleString string) {
      return false;
   }

   @Specialization
   protected static boolean doBoolean(boolean value) {
      return false;
   }

   @Specialization
   protected static boolean doNumber(Number number) {
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

   @Specialization(guards = "isForeignObject(obj)", limit = "InteropLibraryLimit")
   protected static boolean doTruffleObject(Object obj, @CachedLibrary("obj") InteropLibrary interop) {
      return interop.isInstantiable(obj);
   }

   public static IsConstructorNode create() {
      return IsConstructorNodeGen.create();
   }
}
