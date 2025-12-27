package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSUnaryPlusNode.class)
public final class JSUnaryPlusNodeGen extends JSUnaryPlusNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSOverloadedUnaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSToNumberNode default_toNumberNode_;

   private JSUnaryPlusNodeGen(JavaScriptNode operand) {
      super(operand);
   }

   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
         return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
      } else if ((state_0 & 2) != 0 && !this.hasOverloadedOperators(operandNodeValue)) {
         return this.doDefault(operandNodeValue, this.default_toNumberNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue__ = (JSOverloadedOperatorsObject)operandNodeValue_;
         return this.doOverloaded(operandNodeValue__, this.overloaded_overloadedOperatorNode_);
      } else if ((state_0 & 2) != 0 && !this.hasOverloadedOperators(operandNodeValue_)) {
         return this.doDefault(operandNodeValue_, this.default_toNumberNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.execute(frameValue);
   }

   private Object executeAndSpecialize(Object operandNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var6;
      try {
         int state_0 = this.state_0_;
         if (!(operandNodeValue instanceof JSOverloadedOperatorsObject)) {
            if (this.hasOverloadedOperators(operandNodeValue)) {
               throw new UnsupportedSpecializationException(this, new Node[]{super.operandNode}, operandNodeValue);
            }

            this.default_toNumberNode_ = super.insert(JSToNumberNode.create());
            int var11;
            this.state_0_ = var11 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doDefault(operandNodeValue, this.default_toNumberNode_);
         }

         JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
         this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedUnaryNode.create(this.getOverloadedOperatorName()));
         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var6 = this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var6;
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
      Object[] s = new Object[]{"doOverloaded", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doDefault", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.default_toNumberNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static JSUnaryPlusNode create(JavaScriptNode operand) {
      return new JSUnaryPlusNodeGen(operand);
   }
}
