package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GenerateUncached
@ImportStatic(JSConfig.class)
public abstract class IsPrimitiveNode extends JavaScriptBaseNode {
   public abstract boolean executeBoolean(Object operand);

   @Specialization(guards = "isJSNull(operand)")
   protected static boolean doNull(Object operand) {
      return true;
   }

   @Specialization(guards = "isUndefined(operand)")
   protected static boolean doUndefined(Object operand) {
      return true;
   }

   @Specialization
   protected static boolean doBoolean(boolean operand) {
      return true;
   }

   @Specialization
   protected static boolean doInt(int operand) {
      return true;
   }

   @Specialization
   protected static boolean doLong(long operand) {
      return true;
   }

   @Specialization
   protected static boolean doLargeInt(SafeInteger operand) {
      return true;
   }

   @Specialization
   protected static boolean doDouble(double operand) {
      return true;
   }

   @Specialization
   protected static boolean doSymbol(Symbol operand) {
      return true;
   }

   @Specialization
   protected static boolean doBigInt(BigInt operand) {
      return true;
   }

   @Specialization
   protected static boolean doString(TruffleString operand) {
      return true;
   }

   @Specialization(guards = "isJSObject(operand)")
   protected static boolean doIsObject(JSDynamicObject operand) {
      return false;
   }

   @Specialization(guards = "isForeignObject(operand)", limit = "InteropLibraryLimit")
   protected static boolean doForeignObject(Object operand, @CachedLibrary("operand") InteropLibrary interop) {
      if (interop.isNull(operand)) {
         return true;
      } else if (interop.isBoolean(operand)) {
         return true;
      } else {
         return interop.isString(operand) ? true : interop.isNumber(operand);
      }
   }

   public static IsPrimitiveNode create() {
      return IsPrimitiveNodeGen.create();
   }

   public static IsPrimitiveNode getUncached() {
      return IsPrimitiveNodeGen.getUncached();
   }
}
