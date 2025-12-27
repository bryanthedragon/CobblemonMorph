package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(ExpectByteArrayHostObjectNode.class)
public final class ExpectByteArrayHostObjectNodeGen extends ExpectByteArrayHostObjectNode {
   private static final ExpectByteArrayHostObjectNodeGen.Uncached UNCACHED = new ExpectByteArrayHostObjectNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private ExpectByteArrayHostObjectNodeGen() {
   }

   @Override
   public byte[] execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof byte[]) {
         byte[] arg0Value_ = (byte[])arg0Value;
         return ExpectByteArrayHostObjectNode.doByteArray(arg0Value_);
      } else if ((state_0 & 2) != 0) {
         return this.doBoxed(arg0Value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private byte[] executeAndSpecialize(Object arg0Value) {
      int state_0 = this.state_0_;
      if (arg0Value instanceof byte[]) {
         byte[] arg0Value_ = (byte[])arg0Value;
         int var5;
         this.state_0_ = var5 = state_0 | 1;
         return ExpectByteArrayHostObjectNode.doByteArray(arg0Value_);
      } else {
         int var4;
         this.state_0_ = var4 = state_0 | 2;
         return this.doBoxed(arg0Value);
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

   public static ExpectByteArrayHostObjectNode create() {
      return new ExpectByteArrayHostObjectNodeGen();
   }

   public static ExpectByteArrayHostObjectNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(ExpectByteArrayHostObjectNode.class)
   @DenyReplace
   private static final class Uncached extends ExpectByteArrayHostObjectNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public byte[] execute(Object arg0Value) {
         if (arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            return ExpectByteArrayHostObjectNode.doByteArray(arg0Value_);
         } else {
            return this.doBoxed(arg0Value);
         }
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @Override
      public boolean isAdoptable() {
         return false;
      }
   }
}
