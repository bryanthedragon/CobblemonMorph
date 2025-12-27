package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

public abstract class TestArrayNode extends JavaScriptBaseNode {
   protected static final int MAX_TYPE_COUNT = 4;
   protected final TestArrayNode.Test test;

   protected TestArrayNode(TestArrayNode.Test test) {
      this.test = test;
   }

   protected static ScriptArray getArrayType(JSDynamicObject target) {
      return JSObject.getArray(target);
   }

   protected static TestArrayNode create(TestArrayNode.Test test) {
      return TestArrayNodeGen.create(test);
   }

   public static TestArrayNode createHasHoles() {
      return create(TestArrayNode.Test.HasHoles);
   }

   public static TestArrayNode createIsSealed() {
      return create(TestArrayNode.Test.IsSealed);
   }

   public abstract boolean executeBoolean(JSDynamicObject target);

   @Specialization(guards = "arrayType.isInstance(getArrayType(target))", limit = "MAX_TYPE_COUNT")
   protected final boolean doCached(JSDynamicObject target, @Cached("getArrayType(target)") ScriptArray arrayType) {
      if (this.test == TestArrayNode.Test.HasHoles) {
         return arrayType.hasHoles(target);
      } else if (this.test == TestArrayNode.Test.IsSealed) {
         return arrayType.isSealed();
      } else {
         throw Errors.shouldNotReachHere();
      }
   }

   @Specialization(replaces = "doCached")
   protected final boolean doUncached(JSDynamicObject target) {
      ScriptArray arrayType = getArrayType(target);
      return this.doCached(target, arrayType);
   }

   protected static enum Test {
      HasHoles,
      IsSealed;
   }
}
