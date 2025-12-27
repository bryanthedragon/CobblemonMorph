package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.SafeInteger;

public abstract class IsNumberNode extends JavaScriptBaseNode {
   public static IsNumberNode create() {
      return IsNumberNodeGen.create();
   }

   public abstract boolean execute(Object value);

   @Specialization
   protected static boolean doInt(int value) {
      return true;
   }

   @Specialization
   protected static boolean doLong(long value) {
      return true;
   }

   @Specialization
   protected static boolean doSafeInteger(SafeInteger value) {
      return true;
   }

   @Specialization
   protected static boolean doDouble(double value) {
      return true;
   }

   @Fallback
   protected static boolean doOther(Object value) {
      return false;
   }
}
