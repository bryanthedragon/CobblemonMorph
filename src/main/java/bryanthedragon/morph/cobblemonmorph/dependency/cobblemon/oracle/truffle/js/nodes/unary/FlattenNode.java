package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;

public abstract class FlattenNode extends JavaScriptBaseNode {
   FlattenNode() {
   }

   public abstract Object execute(Object value);

   @Specialization
   protected static TruffleString doLazyString(TruffleString value, @Cached TruffleString.MaterializeNode materializeNode) {
      return Strings.flatten(materializeNode, value);
   }

   @Specialization
   protected static double doSafeInteger(SafeInteger value) {
      return value.doubleValue();
   }

   @Fallback
   protected static Object doOther(Object value) {
      return value;
   }

   public static FlattenNode create() {
      return FlattenNodeGen.create();
   }
}
