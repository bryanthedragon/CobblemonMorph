package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(InitializeDisplayNamesNode.class)
public final class InitializeDisplayNamesNodeGen extends InitializeDisplayNamesNode implements Introspection.Provider {
   private InitializeDisplayNamesNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public JSDynamicObject executeInit(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value) {
      return this.initializeDisplayNames(arg0Value, arg1Value, arg2Value);
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      Object[] s = new Object[]{"initializeDisplayNames", (byte)1, null};
      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static InitializeDisplayNamesNode create(JSContext context) {
      return new InitializeDisplayNamesNodeGen(context);
   }
}
