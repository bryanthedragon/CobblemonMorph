package com.oracle.truffle.js.nodes.wasm;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyValueTypes;

public abstract class ToWebAssemblyValueNode extends JavaScriptBaseNode {
   @Node.Child
   JSToInt32Node toInt32Node;
   @Node.Child
   JSToNumberNode toNumberNode = JSToNumberNode.create();
   @Node.Child
   JSToBigIntNode toBigIntNode;

   protected ToWebAssemblyValueNode() {
      this.toInt32Node = JSToInt32Node.create();
      this.toBigIntNode = JSToBigIntNode.create();
   }

   public static ToWebAssemblyValueNode create() {
      return ToWebAssemblyValueNodeGen.create();
   }

   public static ToWebAssemblyValueNode getUncached() {
      return ToWebAssemblyValueNode.Uncached.INSTANCE;
   }

   public abstract Object execute(Object value, TruffleString type);

   @Specialization
   protected Object convert(Object value, TruffleString type) {
      assert this.getLanguage().getJSContext().getContextOptions().isWasmBigInt() || !JSWebAssemblyValueTypes.isI64(type);

      if (JSWebAssemblyValueTypes.isI64(type)) {
         return this.toBigIntNode.executeBigInteger(value).longValue();
      } else if (JSWebAssemblyValueTypes.isI32(type)) {
         return this.toInt32Node.executeInt(value);
      } else {
         Number numberValue = this.toNumberNode.executeNumber(value);
         double doubleValue = JSRuntime.toDouble(numberValue);
         if (JSWebAssemblyValueTypes.isF32(type)) {
            return (float)doubleValue;
         } else {
            assert JSWebAssemblyValueTypes.isF64(type);

            return doubleValue;
         }
      }
   }

   static class Uncached extends ToWebAssemblyValueNode {
      static final ToWebAssemblyValueNode.Uncached INSTANCE = new ToWebAssemblyValueNode.Uncached();

      @Override
      public Object execute(Object value, TruffleString type) {
         assert this.getLanguage().getJSContext().getContextOptions().isWasmBigInt() || !JSWebAssemblyValueTypes.isI64(type);

         if (JSWebAssemblyValueTypes.isI64(type)) {
            return JSRuntime.toBigInt(value).longValue();
         } else if (JSWebAssemblyValueTypes.isI32(type)) {
            return JSRuntime.toInt32(value);
         } else {
            double doubleValue = JSRuntime.toDouble(value);
            if (JSWebAssemblyValueTypes.isF32(type)) {
               return (float)doubleValue;
            } else {
               assert JSWebAssemblyValueTypes.isF64(type);

               return doubleValue;
            }
         }
      }
   }
}
