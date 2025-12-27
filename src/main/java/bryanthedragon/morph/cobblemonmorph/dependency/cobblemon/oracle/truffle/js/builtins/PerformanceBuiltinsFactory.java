package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(PerformanceBuiltins.class)
public final class PerformanceBuiltinsFactory {
   @GeneratedBy(PerformanceBuiltins.JSPerformanceNowNode.class)
   public static final class JSPerformanceNowNodeGen extends PerformanceBuiltins.JSPerformanceNowNode implements Introspection.Provider {
      private JSPerformanceNowNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.now();
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         return this.now();
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"now", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static PerformanceBuiltins.JSPerformanceNowNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PerformanceBuiltinsFactory.JSPerformanceNowNodeGen(context, builtin, arguments);
      }
   }
}
