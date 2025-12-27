package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;

@GeneratedBy(IsIdenticalUndefinedNode.class)
public final class IsIdenticalUndefinedNodeGen extends IsIdenticalUndefinedNode implements Introspection.Provider {
   private IsIdenticalUndefinedNodeGen(JavaScriptNode operand, boolean leftConstant) {
      super(operand, leftConstant);
   }

   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      return this.doObject(operandNodeValue);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      return this.doObject(operandNodeValue_);
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      return this.doObject(operandNodeValue_);
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.executeBoolean(frameValue);
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      Object[] s = new Object[]{"doObject", (byte)1, null};
      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static IsIdenticalUndefinedNode create(JavaScriptNode operand, boolean leftConstant) {
      return new IsIdenticalUndefinedNodeGen(operand, leftConstant);
   }
}
