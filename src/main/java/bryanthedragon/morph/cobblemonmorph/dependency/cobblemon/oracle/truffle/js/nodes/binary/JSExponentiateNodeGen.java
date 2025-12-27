package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSExponentiateNode.class)
public final class JSExponentiateNodeGen extends JSExponentiateNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSExponentiateNodeGen.GenericData generic_cache;

   private JSExponentiateNodeGen(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   @Override
   public Object execute(Object leftNodeValue, Object rightNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 1920) >>> 7, leftNodeValue)) {
         double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 1920) >>> 7, leftNodeValue);
         if (JSTypesGen.isImplicitDouble((state_0 & 30720) >>> 11, rightNodeValue)) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 30720) >>> 11, rightNodeValue);
            return this.doDouble(leftNodeValue_, rightNodeValue_);
         }
      }

      if ((state_0 & 28) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_ = (BigInt)leftNodeValue;
         if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_ = (BigInt)rightNodeValue;
            if ((state_0 & 4) != 0
               && JSGuards.isBigIntZero(leftNodeValue_)
               && !JSGuards.isBigIntZero(rightNodeValue_)
               && !JSGuards.isBigIntNegativeVal(rightNodeValue_)) {
               return this.doBigIntZero(leftNodeValue_, rightNodeValue_);
            }

            if ((state_0 & 8) != 0 && JSGuards.isBigIntZero(rightNodeValue_)) {
               return this.doBigIntZeroPowZero(leftNodeValue_, rightNodeValue_);
            }

            if ((state_0 & 16) != 0
               && !JSGuards.isBigIntZero(leftNodeValue_)
               && !JSGuards.isBigIntZero(rightNodeValue_)
               && !JSGuards.isBigIntNegativeVal(rightNodeValue_)) {
               return this.doBigInt(leftNodeValue_, rightNodeValue_);
            }
         }
      }

      if ((state_0 & 96) != 0) {
         if ((state_0 & 32) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 64) != 0) {
            JSExponentiateNodeGen.GenericData s6_ = this.generic_cache;
            if (s6_ != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
               return this.doGeneric(
                  leftNodeValue, rightNodeValue, s6_.nestedExponentiateNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_
               );
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      return (state_0 & 124) == 0 && (state_0 & 125) != 0 ? this.execute_double_double0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
   }

   private Object execute_double_double0(int state_0, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 1792) == 0 && (state_0 & 127) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 1664) == 0 && (state_0 & 127) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 896) == 0 && (state_0 & 127) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 1920) >>> 7, leftNodeValue__);
         }
      } catch (UnexpectedResultException var15) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var15.getResult(), rightNodeValue);
      }

      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((state_0 & 28672) == 0 && (state_0 & 127) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 26624) == 0 && (state_0 & 127) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 14336) == 0 && (state_0 & 127) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 30720) >>> 11, rightNodeValue__);
         }
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 1664) == 0 && (state_0 & 127) != 0
               ? leftNodeValue_int
               : ((state_0 & 896) == 0 && (state_0 & 127) != 0 ? leftNodeValue_long : leftNodeValue_),
            var14.getResult()
         );
      }

      assert (state_0 & 1) != 0;

      return this.doDouble(leftNodeValue_, rightNodeValue_);
   }

   private Object execute_generic1(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 1920) >>> 7, leftNodeValue_)) {
         double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 1920) >>> 7, leftNodeValue_);
         if (JSTypesGen.isImplicitDouble((state_0 & 30720) >>> 11, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 30720) >>> 11, rightNodeValue_);
            return this.doDouble(leftNodeValue__, rightNodeValue__);
         }
      }

      if ((state_0 & 28) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
         if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            if ((state_0 & 4) != 0
               && JSGuards.isBigIntZero(leftNodeValue__)
               && !JSGuards.isBigIntZero(rightNodeValue__)
               && !JSGuards.isBigIntNegativeVal(rightNodeValue__)) {
               return this.doBigIntZero(leftNodeValue__, rightNodeValue__);
            }

            if ((state_0 & 8) != 0 && JSGuards.isBigIntZero(rightNodeValue__)) {
               return this.doBigIntZeroPowZero(leftNodeValue__, rightNodeValue__);
            }

            if ((state_0 & 16) != 0
               && !JSGuards.isBigIntZero(leftNodeValue__)
               && !JSGuards.isBigIntZero(rightNodeValue__)
               && !JSGuards.isBigIntNegativeVal(rightNodeValue__)) {
               return this.doBigInt(leftNodeValue__, rightNodeValue__);
            }
         }
      }

      if ((state_0 & 96) != 0) {
         if ((state_0 & 32) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
            return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 64) != 0) {
            JSExponentiateNodeGen.GenericData s6_ = this.generic_cache;
            if (s6_ != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
               return this.doGeneric(
                  leftNodeValue_, rightNodeValue_, s6_.nestedExponentiateNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_
               );
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 96) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else {
         long leftNodeValue_long = 0L;
         int leftNodeValue_int = 0;

         double leftNodeValue_;
         try {
            if ((state_0 & 1792) == 0 && (state_0 & 127) != 0) {
               leftNodeValue_ = super.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 1664) == 0 && (state_0 & 127) != 0) {
               leftNodeValue_int = super.leftNode.executeInt(frameValue);
               leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 896) == 0 && (state_0 & 127) != 0) {
               leftNodeValue_long = super.leftNode.executeLong(frameValue);
               leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
               Object leftNodeValue__ = super.leftNode.execute(frameValue);
               leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 1920) >>> 7, leftNodeValue__);
            }
         } catch (UnexpectedResultException var15) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = super.rightNode.execute(frameValue);
            return JSTypesGen.expectDouble(this.executeAndSpecialize(var15.getResult(), rightNodeValue));
         }

         long rightNodeValue_long = 0L;
         int rightNodeValue_int = 0;

         double rightNodeValue_;
         try {
            if ((state_0 & 28672) == 0 && (state_0 & 127) != 0) {
               rightNodeValue_ = super.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 26624) == 0 && (state_0 & 127) != 0) {
               rightNodeValue_int = super.rightNode.executeInt(frameValue);
               rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 14336) == 0 && (state_0 & 127) != 0) {
               rightNodeValue_long = super.rightNode.executeLong(frameValue);
               rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
               Object rightNodeValue__ = super.rightNode.execute(frameValue);
               rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 30720) >>> 11, rightNodeValue__);
            }
         } catch (UnexpectedResultException var14) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(
               this.executeAndSpecialize(
                  (state_0 & 1664) == 0 && (state_0 & 127) != 0
                     ? leftNodeValue_int
                     : ((state_0 & 896) == 0 && (state_0 & 127) != 0 ? leftNodeValue_long : leftNodeValue_),
                  var14.getResult()
               )
            );
         }

         if ((state_0 & 1) != 0) {
            return this.doDouble(leftNodeValue_, rightNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(
               this.executeAndSpecialize(
                  (state_0 & 1664) == 0 && (state_0 & 127) != 0
                     ? leftNodeValue_int
                     : ((state_0 & 896) == 0 && (state_0 & 127) != 0 ? leftNodeValue_long : leftNodeValue_),
                  (state_0 & 26624) == 0 && (state_0 & 127) != 0
                     ? rightNodeValue_int
                     : ((state_0 & 14336) == 0 && (state_0 & 127) != 0 ? rightNodeValue_long : rightNodeValue_)
               )
            );
         }
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 126) == 0 && (state_0 & 127) != 0) {
            this.executeDouble(frameValue);
            return;
         }

         if ((state_0 & 125) != 0) {
            this.execute(frameValue);
            return;
         }
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return;
      }

      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 2) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
         if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            if (JSGuards.isBigIntNegativeVal(rightNodeValue__)) {
               this.doBigIntNegativeExponent(leftNodeValue__, rightNodeValue__);
               return;
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
   }

   private Object executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         int doubleCast0;
         if (exclude == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
            double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
            int doubleCast1;
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               state_0 |= doubleCast0 << 7;
               state_0 |= doubleCast1 << 11;
               int var26;
               this.state_0_ = var26 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doDouble(leftNodeValue_, rightNodeValue_);
            }
         }

         if (leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_ = (BigInt)rightNodeValue;
               if (JSGuards.isBigIntNegativeVal(rightNodeValue_)) {
                  int var23;
                  this.state_0_ = var23 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  this.doBigIntNegativeExponent(leftNodeValue_, rightNodeValue_);
                  return null;
               }

               if (JSGuards.isBigIntZero(leftNodeValue_) && !JSGuards.isBigIntZero(rightNodeValue_) && !JSGuards.isBigIntNegativeVal(rightNodeValue_)) {
                  int var22;
                  this.state_0_ = var22 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doBigIntZero(leftNodeValue_, rightNodeValue_);
               }

               if (JSGuards.isBigIntZero(rightNodeValue_)) {
                  int var21;
                  this.state_0_ = var21 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.doBigIntZeroPowZero(leftNodeValue_, rightNodeValue_);
               }

               if (!JSGuards.isBigIntZero(leftNodeValue_) && !JSGuards.isBigIntZero(rightNodeValue_) && !JSGuards.isBigIntNegativeVal(rightNodeValue_)) {
                  int var20;
                  this.state_0_ = var20 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.doBigInt(leftNodeValue_, rightNodeValue_);
               }
            }
         }

         if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
            int var17;
            this.state_0_ = var17 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         } else if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            throw new UnsupportedSpecializationException(this, new Node[]{super.leftNode, super.rightNode}, leftNodeValue, rightNodeValue);
         } else {
            JSExponentiateNodeGen.GenericData s6_ = super.insert(new JSExponentiateNodeGen.GenericData());
            s6_.nestedExponentiateNode_ = s6_.insertAccessor(JSExponentiateNode.create());
            s6_.toNumeric1Node_ = s6_.insertAccessor(JSToNumericNode.create());
            s6_.toNumeric2Node_ = s6_.insertAccessor(JSToNumericNode.create());
            s6_.mixedNumericTypes_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.generic_cache = s6_;
            int var27;
            this.exclude_ = var27 = exclude | 1;
            state_0 &= -2;
            int var19;
            this.state_0_ = var19 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(leftNodeValue, rightNodeValue, s6_.nestedExponentiateNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_);
         }
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if ((state_0 & 127) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 127 & (state_0 & 127) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[8];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doDouble", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doBigIntNegativeExponent", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doBigIntZero", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doBigIntZeroPowZero", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doOverloaded", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doGeneric", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSExponentiateNodeGen.GenericData s6_ = this.generic_cache;
         if (s6_ != null) {
            cached.add(Arrays.asList(s6_.nestedExponentiateNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      return Introspection.Provider.create(data);
   }

   public static JSExponentiateNode create(JavaScriptNode left, JavaScriptNode right) {
      return new JSExponentiateNodeGen(left, right);
   }

   @GeneratedBy(JSExponentiateNode.class)
   private static final class GenericData extends Node {
      @Node.Child
      JSExponentiateNode nestedExponentiateNode_;
      @Node.Child
      JSToNumericNode toNumeric1Node_;
      @Node.Child
      JSToNumericNode toNumeric2Node_;
      @CompilerDirectives.CompilationFinal
      BranchProfile mixedNumericTypes_;

      GenericData() {
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
