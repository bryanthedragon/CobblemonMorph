package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(ErrorStackTraceLimitNode.class)
public final class ErrorStackTraceLimitNodeGen extends ErrorStackTraceLimitNode implements Introspection.Provider {
   private ErrorStackTraceLimitNodeGen() {
   }

   @Override
   public int executeInt() {
      return this.doInt();
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      Object[] s = new Object[]{"doInt", (byte)1, null};
      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static ErrorStackTraceLimitNode create() {
      return new ErrorStackTraceLimitNodeGen();
   }
}
