package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;

@ImportStatic(JSRuntime.class)
public abstract class FroundNode extends MathOperation {
   public FroundNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization(guards = "intIsRepresentableAsFloat(x)")
   protected static int fround(int x) {
      return x;
   }

   @Specialization
   protected static double fround(double x) {
      return (float)x;
   }

   @Specialization
   protected double fround(Object a) {
      return fround(this.toDouble(a));
   }
}
