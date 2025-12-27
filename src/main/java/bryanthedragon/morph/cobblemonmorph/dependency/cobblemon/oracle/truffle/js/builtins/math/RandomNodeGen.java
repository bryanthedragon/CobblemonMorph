package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(RandomNode.class)
public final class RandomNodeGen extends RandomNode implements Introspection.Provider {
   private RandomNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      super(context, builtin);
   }

   @Override
   public JavaScriptNode[] getArguments() {
      return new JavaScriptNode[0];
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      return this.random();
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) {
      return this.random();
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
      Object[] s = new Object[]{"random", (byte)1, null};
      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static RandomNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      return new RandomNodeGen(context, builtin, arguments);
   }
}
