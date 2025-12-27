package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSAttributes;

public final class PerformanceBuiltins extends JSBuiltinsContainer.Lambda {
   public static final JSBuiltinsContainer BUILTINS = new PerformanceBuiltins();

   protected PerformanceBuiltins() {
      super(JSRealm.PERFORMANCE_CLASS_NAME);
      this.defineFunction(
         Strings.NOW,
         0,
         JSAttributes.getDefault(),
         (context, builtin) -> PerformanceBuiltinsFactory.JSPerformanceNowNodeGen.create(context, builtin, args().fixedArgs(0).createArgumentNodes(context))
      );
   }

   public abstract static class JSPerformanceNowNode extends JSBuiltinNode {
      public JSPerformanceNowNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected double now() {
         long ns = this.getRealm().nanoTime();
         return ns / 1000000.0;
      }
   }
}
