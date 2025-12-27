package com.oracle.truffle.js.nodes.wasm;

import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;

@GenerateUncached
public abstract class ToJSValueNode extends JavaScriptBaseNode {
   protected ToJSValueNode() {
   }

   public static ToJSValueNode create() {
      return ToJSValueNodeGen.create();
   }

   public abstract Object execute(Object value);

   @Specialization
   public Object convert(Object value) {
      if (value instanceof Float) {
         return (double)((Float)value).floatValue();
      } else {
         return value instanceof Long ? BigInt.valueOf((Long)value) : value;
      }
   }
}
