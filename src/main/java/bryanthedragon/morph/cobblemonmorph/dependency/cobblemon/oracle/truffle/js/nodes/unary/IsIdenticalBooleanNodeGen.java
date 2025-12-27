package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;

@GeneratedBy(IsIdenticalBooleanNode.class)
public final class IsIdenticalBooleanNodeGen extends IsIdenticalBooleanNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private IsIdenticalBooleanNodeGen(JavaScriptNode operand, boolean bool, boolean leftConstant) {
      super(operand, bool, leftConstant);
   }

   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof Boolean) {
         boolean operandNodeValue_ = (Boolean)operandNodeValue;
         return this.doBoolean(operandNodeValue_);
      } else if ((state_0 & 2) != 0 && fallbackGuard_(state_0, operandNodeValue)) {
         return this.doOther(operandNodeValue);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      return (state_0 & 2) == 0 && state_0 != 0 ? this.execute_boolean0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
   }

   private Object execute_boolean0(int state_0, VirtualFrame frameValue) {
      boolean operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 1) != 0;

      return this.doBoolean(operandNodeValue_);
   }

   private Object execute_generic1(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Boolean) {
         boolean operandNodeValue__ = (Boolean)operandNodeValue_;
         return this.doBoolean(operandNodeValue__);
      } else if ((state_0 & 2) != 0 && fallbackGuard_(state_0, operandNodeValue_)) {
         return this.doOther(operandNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      return (state_0 & 2) == 0 && state_0 != 0 ? this.executeBoolean_boolean2(state_0, frameValue) : this.executeBoolean_generic3(state_0, frameValue);
   }

   private boolean executeBoolean_boolean2(int state_0, VirtualFrame frameValue) {
      boolean operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 1) != 0;

      return this.doBoolean(operandNodeValue_);
   }

   private boolean executeBoolean_generic3(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Boolean) {
         boolean operandNodeValue__ = (Boolean)operandNodeValue_;
         return this.doBoolean(operandNodeValue__);
      } else if ((state_0 & 2) != 0 && fallbackGuard_(state_0, operandNodeValue_)) {
         return this.doOther(operandNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.executeBoolean(frameValue);
   }

   private boolean executeAndSpecialize(Object operandNodeValue) {
      int state_0 = this.state_0_;
      if (operandNodeValue instanceof Boolean) {
         boolean operandNodeValue_ = (Boolean)operandNodeValue;
         int var5;
         this.state_0_ = var5 = state_0 | 1;
         return this.doBoolean(operandNodeValue_);
      } else {
         int var4;
         this.state_0_ = var4 = state_0 | 2;
         return this.doOther(operandNodeValue);
      }
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null, null};
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doOther", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(int state_0, Object operandNodeValue) {
      return (state_0 & 1) != 0 || !(operandNodeValue instanceof Boolean);
   }

   public static IsIdenticalBooleanNode create(JavaScriptNode operand, boolean bool, boolean leftConstant) {
      return new IsIdenticalBooleanNodeGen(operand, bool, leftConstant);
   }
}
