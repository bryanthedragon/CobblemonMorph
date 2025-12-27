package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@ImportStatic(JSConfig.class)
public abstract class IsNullNode extends IsIdenticalBaseNode {
   protected IsNullNode(JavaScriptNode operand, boolean leftConstant) {
      super(operand, leftConstant);
   }

   @Specialization(guards = "isJSNull(operand)")
   protected static boolean doNull(Object operand) {
      return true;
   }

   @Specialization(guards = "isUndefined(operand)")
   protected static boolean doUndefined(Object operand) {
      return false;
   }

   @Specialization(guards = "isJSObject(operand)")
   protected static boolean doObject(Object operand) {
      return false;
   }

   @Specialization(guards = "!isJSDynamicObject(operand)", limit = "InteropLibraryLimit")
   protected static boolean doCached(Object operand, @CachedLibrary("operand") InteropLibrary interop) {
      assert operand != Undefined.instance;

      return interop.isNull(operand);
   }

   public static IsNullNode create(JavaScriptNode operand, boolean leftConstant) {
      return IsNullNodeGen.create(operand, leftConstant);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.getOperand(), materializedTags), this.leftConstant);
   }

   @Override
   protected Object getConstantValue() {
      return Null.instance;
   }
}
