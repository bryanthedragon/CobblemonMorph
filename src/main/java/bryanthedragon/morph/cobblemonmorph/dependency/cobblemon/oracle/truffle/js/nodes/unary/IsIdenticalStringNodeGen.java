package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IsIdenticalStringNode.class)
public final class IsIdenticalStringNodeGen extends IsIdenticalStringNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private TruffleString.EqualNode string_equalsNode_;

   private IsIdenticalStringNodeGen(TruffleString string, JavaScriptNode operand, boolean leftConstant) {
      super(string, operand, leftConstant);
   }

   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof TruffleString) {
         TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
         return this.doString(operandNodeValue_, this.string_equalsNode_);
      } else if ((state_0 & 2) != 0 && !JSGuards.isTruffleString(operandNodeValue)) {
         return this.doOther(operandNodeValue);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof TruffleString) {
         TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
         return this.doString(operandNodeValue__, this.string_equalsNode_);
      } else if ((state_0 & 2) != 0 && !JSGuards.isTruffleString(operandNodeValue_)) {
         return this.doOther(operandNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof TruffleString) {
         TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
         return this.doString(operandNodeValue__, this.string_equalsNode_);
      } else if ((state_0 & 2) != 0 && !JSGuards.isTruffleString(operandNodeValue_)) {
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
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      boolean var6;
      try {
         int state_0 = this.state_0_;
         if (!(operandNodeValue instanceof TruffleString)) {
            if (JSGuards.isTruffleString(operandNodeValue)) {
               throw new UnsupportedSpecializationException(this, new Node[]{super.operandNode}, operandNodeValue);
            }

            int var11;
            this.state_0_ = var11 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doOther(operandNodeValue);
         }

         TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
         this.string_equalsNode_ = super.insert(TruffleString.EqualNode.create());
         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var6 = this.doString(operandNodeValue_, this.string_equalsNode_);
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
      Object[] s = new Object[]{"doString", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.string_equalsNode_));
         s[2] = cached;
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

   public static IsIdenticalStringNode create(TruffleString string, JavaScriptNode operand, boolean leftConstant) {
      return new IsIdenticalStringNodeGen(string, operand, leftConstant);
   }
}
