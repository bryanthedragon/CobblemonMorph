package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.unary.JSOverloadedUnaryNode;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(LocalVarPostfixIncNode.class)
final class LocalVarPostfixIncNodeGen extends LocalVarPostfixIncNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private LocalVarPostfixIncNodeGen.ObjectData object_cache;

   private LocalVarPostfixIncNodeGen(LocalVarIncNode.LocalVarOp op, int slot, Object identifier, boolean hasTemporalDeadZone, ScopeFrameNode scopeFrameNode) {
      super(op, slot, identifier, hasTemporalDeadZone, scopeFrameNode);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Frame scopeFrameNodeValue_ = super.scopeFrameNode.executeFrame(frameValue);
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && scopeFrameNodeValue_.isBoolean(this.slot) && this.isIntegerKind(scopeFrameNodeValue_)) {
            return this.doBoolean(scopeFrameNodeValue_);
         }

         if ((state_0 & 2) != 0 && scopeFrameNodeValue_.isBoolean(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
            return this.doBooleanDouble(scopeFrameNodeValue_);
         }

         if ((state_0 & 4) != 0 && scopeFrameNodeValue_.isBoolean(this.slot)) {
            return this.doBooleanObject(scopeFrameNodeValue_);
         }

         if ((state_0 & 8) != 0 && scopeFrameNodeValue_.isInt(this.slot) && this.isIntegerKind(scopeFrameNodeValue_)) {
            try {
               return this.doInt(scopeFrameNodeValue_);
            } catch (ArithmeticException var18) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 4;
                  this.state_0_ &= -9;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(scopeFrameNodeValue_);
            }
         }

         if ((state_0 & 16) != 0 && scopeFrameNodeValue_.isInt(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
            return this.doIntDouble(scopeFrameNodeValue_);
         }

         if ((state_0 & 32) != 0 && scopeFrameNodeValue_.isInt(this.slot)) {
            return this.doIntObject(scopeFrameNodeValue_);
         }

         if ((state_0 & 64) != 0 && scopeFrameNodeValue_.isDouble(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
            return this.doDouble(scopeFrameNodeValue_);
         }

         if ((state_0 & 128) != 0 && scopeFrameNodeValue_.isDouble(this.slot)) {
            return this.doDoubleObject(scopeFrameNodeValue_);
         }

         if ((state_0 & 256) != 0) {
            LocalVarPostfixIncNodeGen.ObjectData s8_ = this.object_cache;
            if (s8_ != null && scopeFrameNodeValue_.isObject(this.slot)) {
               return this.doObject(
                  scopeFrameNodeValue_,
                  s8_.isNumberProfile_,
                  s8_.isIntegerProfile_,
                  s8_.isBigIntProfile_,
                  s8_.isBoundaryProfile_,
                  s8_.overloadedOperatorNode_,
                  s8_.toNumericOperand_
               );
            }
         }

         if ((state_0 & 512) != 0 && scopeFrameNodeValue_.isLong(this.slot) && this.isLongKind(scopeFrameNodeValue_)) {
            try {
               return this.doSafeInteger(scopeFrameNodeValue_);
            } catch (ArithmeticException var19) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 32;
                  this.state_0_ &= -513;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(scopeFrameNodeValue_);
            }
         }

         if ((state_0 & 1024) != 0 && scopeFrameNodeValue_.isLong(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
            return this.doSafeIntegerToDouble(scopeFrameNodeValue_);
         }

         if ((state_0 & 2048) != 0 && scopeFrameNodeValue_.isLong(this.slot)) {
            return this.doSafeIntegerObject(scopeFrameNodeValue_);
         }

         if ((state_0 & 4096) != 0 && this.isIllegal(scopeFrameNodeValue_)) {
            return this.doDead(scopeFrameNodeValue_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(scopeFrameNodeValue_);
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 4352) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else {
         Frame scopeFrameNodeValue_ = super.scopeFrameNode.executeFrame(frameValue);
         if ((state_0 & 3264) != 0) {
            if ((state_0 & 64) != 0 && scopeFrameNodeValue_.isDouble(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
               return this.doDouble(scopeFrameNodeValue_);
            }

            if ((state_0 & 128) != 0 && scopeFrameNodeValue_.isDouble(this.slot)) {
               return this.doDoubleObject(scopeFrameNodeValue_);
            }

            if ((state_0 & 1024) != 0 && scopeFrameNodeValue_.isLong(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
               return this.doSafeIntegerToDouble(scopeFrameNodeValue_);
            }

            if ((state_0 & 2048) != 0 && scopeFrameNodeValue_.isLong(this.slot)) {
               return this.doSafeIntegerObject(scopeFrameNodeValue_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectDouble(this.executeAndSpecialize(scopeFrameNodeValue_));
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 4352) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else {
         Frame scopeFrameNodeValue_ = super.scopeFrameNode.executeFrame(frameValue);
         if ((state_0 & 63) != 0) {
            if ((state_0 & 1) != 0 && scopeFrameNodeValue_.isBoolean(this.slot) && this.isIntegerKind(scopeFrameNodeValue_)) {
               return this.doBoolean(scopeFrameNodeValue_);
            }

            if ((state_0 & 2) != 0 && scopeFrameNodeValue_.isBoolean(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
               return this.doBooleanDouble(scopeFrameNodeValue_);
            }

            if ((state_0 & 4) != 0 && scopeFrameNodeValue_.isBoolean(this.slot)) {
               return this.doBooleanObject(scopeFrameNodeValue_);
            }

            if ((state_0 & 8) != 0 && scopeFrameNodeValue_.isInt(this.slot) && this.isIntegerKind(scopeFrameNodeValue_)) {
               try {
                  return this.doInt(scopeFrameNodeValue_);
               } catch (ArithmeticException var10) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  Lock lock = this.getLock();
                  lock.lock();

                  try {
                     this.exclude_ |= 4;
                     this.state_0_ &= -9;
                  } finally {
                     lock.unlock();
                  }

                  return JSTypesGen.expectInteger(this.executeAndSpecialize(scopeFrameNodeValue_));
               }
            }

            if ((state_0 & 16) != 0 && scopeFrameNodeValue_.isInt(this.slot) && this.isDoubleKind(scopeFrameNodeValue_)) {
               return this.doIntDouble(scopeFrameNodeValue_);
            }

            if ((state_0 & 32) != 0 && scopeFrameNodeValue_.isInt(this.slot)) {
               return this.doIntObject(scopeFrameNodeValue_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(scopeFrameNodeValue_));
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 8128) == 0 && state_0 != 0) {
            this.executeInt(frameValue);
         } else if ((state_0 & 4927) == 0 && state_0 != 0) {
            this.executeDouble(frameValue);
         } else {
            this.execute(frameValue);
         }
      } catch (UnexpectedResultException var4) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }
   }

   private Object executeAndSpecialize(Frame scopeFrameNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var7;
      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if ((exclude & 1) == 0 && scopeFrameNodeValue.isBoolean(this.slot) && this.isIntegerKind(scopeFrameNodeValue)) {
            int var49;
            this.state_0_ = var49 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doBoolean(scopeFrameNodeValue);
         }

         if ((exclude & 2) == 0 && scopeFrameNodeValue.isBoolean(this.slot) && this.isDoubleKind(scopeFrameNodeValue)) {
            int var56;
            this.exclude_ = var56 = exclude | 1;
            state_0 &= -2;
            int var48;
            this.state_0_ = var48 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doBooleanDouble(scopeFrameNodeValue);
         }

         if (scopeFrameNodeValue.isBoolean(this.slot)) {
            int var55;
            this.exclude_ = var55 = exclude | 3;
            state_0 &= -4;
            int var46;
            this.state_0_ = var46 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doBooleanObject(scopeFrameNodeValue);
         }

         if ((exclude & 4) == 0 && scopeFrameNodeValue.isInt(this.slot) && this.isIntegerKind(scopeFrameNodeValue)) {
            int var44;
            this.state_0_ = var44 = state_0 | 8;

            try {
               lock.unlock();
               hasLock = false;
               return this.doInt(scopeFrameNodeValue);
            } catch (ArithmeticException var27) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               lock.lock();

               try {
                  this.exclude_ |= 4;
                  this.state_0_ &= -9;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(scopeFrameNodeValue);
            }
         }

         if ((exclude & 8) == 0 && scopeFrameNodeValue.isInt(this.slot) && this.isDoubleKind(scopeFrameNodeValue)) {
            int var54;
            this.exclude_ = var54 = exclude | 4;
            state_0 &= -9;
            int var43;
            this.state_0_ = var43 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.doIntDouble(scopeFrameNodeValue);
         }

         if (scopeFrameNodeValue.isInt(this.slot)) {
            int var53;
            this.exclude_ = var53 = exclude | 12;
            state_0 &= -25;
            int var41;
            this.state_0_ = var41 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doIntObject(scopeFrameNodeValue);
         }

         if ((exclude & 16) == 0 && scopeFrameNodeValue.isDouble(this.slot) && this.isDoubleKind(scopeFrameNodeValue)) {
            int var39;
            this.state_0_ = var39 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.doDouble(scopeFrameNodeValue);
         }

         if (scopeFrameNodeValue.isDouble(this.slot)) {
            int var52;
            this.exclude_ = var52 = exclude | 16;
            state_0 &= -65;
            int var38;
            this.state_0_ = var38 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return this.doDoubleObject(scopeFrameNodeValue);
         }

         if (scopeFrameNodeValue.isObject(this.slot)) {
            LocalVarPostfixIncNodeGen.ObjectData s8_ = super.insert(new LocalVarPostfixIncNodeGen.ObjectData());
            s8_.isNumberProfile_ = ConditionProfile.createBinaryProfile();
            s8_.isIntegerProfile_ = ConditionProfile.createBinaryProfile();
            s8_.isBigIntProfile_ = ConditionProfile.createBinaryProfile();
            s8_.isBoundaryProfile_ = ConditionProfile.createBinaryProfile();
            s8_.overloadedOperatorNode_ = s8_.insertAccessor(JSOverloadedUnaryNode.create(this.getOverloadedOperatorName()));
            s8_.toNumericOperand_ = s8_.insertAccessor(JSToNumericNode.createToNumericOperand());
            VarHandle.storeStoreFence();
            this.object_cache = s8_;
            int var36;
            this.state_0_ = var36 = state_0 | 256;
            lock.unlock();
            hasLock = false;
            return this.doObject(
               scopeFrameNodeValue,
               s8_.isNumberProfile_,
               s8_.isIntegerProfile_,
               s8_.isBigIntProfile_,
               s8_.isBoundaryProfile_,
               s8_.overloadedOperatorNode_,
               s8_.toNumericOperand_
            );
         }

         if ((exclude & 32) != 0 || !scopeFrameNodeValue.isLong(this.slot) || !this.isLongKind(scopeFrameNodeValue)) {
            if ((exclude & 64) != 0 || !scopeFrameNodeValue.isLong(this.slot) || !this.isDoubleKind(scopeFrameNodeValue)) {
               if (!scopeFrameNodeValue.isLong(this.slot)) {
                  if (!this.isIllegal(scopeFrameNodeValue)) {
                     throw new UnsupportedSpecializationException(this, new Node[]{super.scopeFrameNode}, scopeFrameNodeValue);
                  }

                  int var35;
                  this.state_0_ = var35 = state_0 | 4096;
                  lock.unlock();
                  hasLock = false;
                  return this.doDead(scopeFrameNodeValue);
               }

               int var51;
               this.exclude_ = var51 = exclude | 96;
               state_0 &= -1537;
               int var34;
               this.state_0_ = var34 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               return this.doSafeIntegerObject(scopeFrameNodeValue);
            }

            int var50;
            this.exclude_ = var50 = exclude | 32;
            state_0 &= -513;
            int var32;
            this.state_0_ = var32 = state_0 | 1024;
            lock.unlock();
            hasLock = false;
            return this.doSafeIntegerToDouble(scopeFrameNodeValue);
         }

         int var30;
         this.state_0_ = var30 = state_0 | 512;

         try {
            lock.unlock();
            hasLock = false;
            return this.doSafeInteger(scopeFrameNodeValue);
         } catch (ArithmeticException var28) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            lock.lock();

            try {
               this.exclude_ |= 32;
               this.state_0_ &= -513;
            } finally {
               lock.unlock();
            }

            var7 = this.executeAndSpecialize(scopeFrameNodeValue);
         }
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var7;
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
      Object[] data = new Object[14];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doBooleanDouble", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doBooleanObject", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doInt", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doIntDouble", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doIntObject", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 16) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doDoubleObject", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doObject", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         LocalVarPostfixIncNodeGen.ObjectData s8_ = this.object_cache;
         if (s8_ != null) {
            cached.add(
               Arrays.asList(
                  s8_.isNumberProfile_, s8_.isIntegerProfile_, s8_.isBigIntProfile_, s8_.isBoundaryProfile_, s8_.overloadedOperatorNode_, s8_.toNumericOperand_
               )
            );
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 32) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doSafeIntegerToDouble", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 64) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doSafeIntegerObject", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      s = new Object[]{"doDead", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      return Introspection.Provider.create(data);
   }

   public static LocalVarPostfixIncNode create(
      LocalVarIncNode.LocalVarOp op, int slot, Object identifier, boolean hasTemporalDeadZone, ScopeFrameNode scopeFrameNode
   ) {
      return new LocalVarPostfixIncNodeGen(op, slot, identifier, hasTemporalDeadZone, scopeFrameNode);
   }

   @GeneratedBy(LocalVarPostfixIncNode.class)
   private static final class ObjectData extends Node {
      @CompilerDirectives.CompilationFinal
      ConditionProfile isNumberProfile_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isIntegerProfile_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isBigIntProfile_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isBoundaryProfile_;
      @Node.Child
      JSOverloadedUnaryNode overloadedOperatorNode_;
      @Node.Child
      JSToNumericNode toNumericOperand_;

      ObjectData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }
}
