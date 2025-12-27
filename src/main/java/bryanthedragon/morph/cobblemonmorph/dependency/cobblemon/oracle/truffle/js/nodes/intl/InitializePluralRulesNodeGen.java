package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(InitializePluralRulesNode.class)
public final class InitializePluralRulesNodeGen extends InitializePluralRulesNode implements Introspection.Provider {
   private InitializePluralRulesNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public JSDynamicObject executeInit(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value) {
      return this.initializePluralRules(arg0Value, arg1Value, arg2Value);
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      Object[] s = new Object[]{"initializePluralRules", (byte)1, null};
      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static InitializePluralRulesNode create(JSContext context) {
      return new InitializePluralRulesNodeGen(context);
   }
}
