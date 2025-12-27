package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(InitializeCollatorNode.class)
public final class InitializeCollatorNodeGen extends InitializeCollatorNode implements Introspection.Provider {
   private InitializeCollatorNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public JSDynamicObject executeInit(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value) {
      return this.initializeCollator(arg0Value, arg1Value, arg2Value);
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      Object[] s = new Object[]{"initializeCollator", (byte)1, null};
      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static InitializeCollatorNode create(JSContext context) {
      return new InitializeCollatorNodeGen(context);
   }
}
