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
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSSubtractNode.class)
public final class JSSubtractNodeGen extends JSSubtractNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSSubtractNodeGen.GenericData generic_cache;

   private JSSubtractNodeGen(boolean truncate, JavaScriptNode left, JavaScriptNode right) {
      super(truncate, left, right);
   }

   @Override
   public Object execute(Object leftNodeValue, Object rightNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && leftNodeValue instanceof Integer) {
         int leftNodeValue_ = (Integer)leftNodeValue;
         if (rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;

            try {
               return this.doInt(leftNodeValue_, rightNodeValue_);
            } catch (ArithmeticException var12) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 1;
                  this.state_0_ &= -2;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
            }
         }
      }

      if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, leftNodeValue)) {
         double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, leftNodeValue);
         if (JSTypesGen.isImplicitDouble((state_0 & 7680) >>> 9, rightNodeValue)) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 7680) >>> 9, rightNodeValue);
            return this.doDouble(leftNodeValue_, rightNodeValue_);
         }
      }

      if ((state_0 & 4) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_ = (BigInt)leftNodeValue;
         if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_ = (BigInt)rightNodeValue;
            return this.doBigInt(leftNodeValue_, rightNodeValue_);
         }
      }

      if ((state_0 & 24) != 0) {
         if ((state_0 & 8) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 16) != 0) {
            JSSubtractNodeGen.GenericData s4_ = this.generic_cache;
            if (s4_ != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
               return this.doGeneric(leftNodeValue, rightNodeValue, s4_.toNumericA_, s4_.toNumericB_, s4_.subtract_, s4_.mixedNumericTypes_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 30) == 0 && (state_0 & 31) != 0) {
         return this.execute_int_int0(state_0, frameValue);
      } else {
         return (state_0 & 29) == 0 && (state_0 & 31) != 0 ? this.execute_double_double1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
      }
   }

   private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
      int leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeInt(frameValue);
      } catch (UnexpectedResultException var15) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var15.getResult(), rightNodeValue);
      }

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var14.getResult());
      }

      assert (state_0 & 1) != 0;

      try {
         return this.doInt(leftNodeValue_, rightNodeValue_);
      } catch (ArithmeticException var13) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Lock lock = this.getLock();
         lock.lock();

         try {
            this.exclude_ |= 1;
            this.state_0_ &= -2;
         } finally {
            lock.unlock();
         }

         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   private Object execute_double_double1(int state_0, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 448) == 0 && (state_0 & 31) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 416) == 0 && (state_0 & 31) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 224) == 0 && (state_0 & 31) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 480) >>> 5, leftNodeValue__);
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
         if ((state_0 & 7168) == 0 && (state_0 & 31) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 6656) == 0 && (state_0 & 31) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 3584) == 0 && (state_0 & 31) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 7680) >>> 9, rightNodeValue__);
         }
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 416) == 0 && (state_0 & 31) != 0
               ? leftNodeValue_int
               : ((state_0 & 224) == 0 && (state_0 & 31) != 0 ? leftNodeValue_long : leftNodeValue_),
            var14.getResult()
         );
      }

      assert (state_0 & 2) != 0;

      return this.doDouble(leftNodeValue_, rightNodeValue_);
   }

   private Object execute_generic2(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 1) != 0 && leftNodeValue_ instanceof Integer) {
         int leftNodeValue__ = (Integer)leftNodeValue_;
         if (rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;

            try {
               return this.doInt(leftNodeValue__, rightNodeValue__);
            } catch (ArithmeticException var13) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 1;
                  this.state_0_ &= -2;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(leftNodeValue__, rightNodeValue__);
            }
         }
      }

      if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, leftNodeValue_)) {
         double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, leftNodeValue_);
         if (JSTypesGen.isImplicitDouble((state_0 & 7680) >>> 9, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 7680) >>> 9, rightNodeValue_);
            return this.doDouble(leftNodeValue__, rightNodeValue__);
         }
      }

      if ((state_0 & 4) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
         if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return this.doBigInt(leftNodeValue__, rightNodeValue__);
         }
      }

      if ((state_0 & 24) != 0) {
         if ((state_0 & 8) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
            return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 16) != 0) {
            JSSubtractNodeGen.GenericData s4_ = this.generic_cache;
            if (s4_ != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
               return this.doGeneric(leftNodeValue_, rightNodeValue_, s4_.toNumericA_, s4_.toNumericB_, s4_.subtract_, s4_.mixedNumericTypes_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 24) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else {
         long leftNodeValue_long = 0L;
         int leftNodeValue_int = 0;

         double leftNodeValue_;
         try {
            if ((state_0 & 448) == 0 && (state_0 & 31) != 0) {
               leftNodeValue_ = super.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 416) == 0 && (state_0 & 31) != 0) {
               leftNodeValue_int = super.leftNode.executeInt(frameValue);
               leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 224) == 0 && (state_0 & 31) != 0) {
               leftNodeValue_long = super.leftNode.executeLong(frameValue);
               leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
               Object leftNodeValue__ = super.leftNode.execute(frameValue);
               leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 480) >>> 5, leftNodeValue__);
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
            if ((state_0 & 7168) == 0 && (state_0 & 31) != 0) {
               rightNodeValue_ = super.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 6656) == 0 && (state_0 & 31) != 0) {
               rightNodeValue_int = super.rightNode.executeInt(frameValue);
               rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 3584) == 0 && (state_0 & 31) != 0) {
               rightNodeValue_long = super.rightNode.executeLong(frameValue);
               rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
               Object rightNodeValue__ = super.rightNode.execute(frameValue);
               rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 7680) >>> 9, rightNodeValue__);
            }
         } catch (UnexpectedResultException var14) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(
               this.executeAndSpecialize(
                  (state_0 & 416) == 0 && (state_0 & 31) != 0
                     ? leftNodeValue_int
                     : ((state_0 & 224) == 0 && (state_0 & 31) != 0 ? leftNodeValue_long : leftNodeValue_),
                  var14.getResult()
               )
            );
         }

         if ((state_0 & 2) != 0) {
            return this.doDouble(leftNodeValue_, rightNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(
               this.executeAndSpecialize(
                  (state_0 & 416) == 0 && (state_0 & 31) != 0
                     ? leftNodeValue_int
                     : ((state_0 & 224) == 0 && (state_0 & 31) != 0 ? leftNodeValue_long : leftNodeValue_),
                  (state_0 & 6656) == 0 && (state_0 & 31) != 0
                     ? rightNodeValue_int
                     : ((state_0 & 3584) == 0 && (state_0 & 31) != 0 ? rightNodeValue_long : rightNodeValue_)
               )
            );
         }
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 24) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else {
         int leftNodeValue_;
         try {
            leftNodeValue_ = super.leftNode.executeInt(frameValue);
         } catch (UnexpectedResultException var15) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = super.rightNode.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(var15.getResult(), rightNodeValue));
         }

         int rightNodeValue_;
         try {
            rightNodeValue_ = super.rightNode.executeInt(frameValue);
         } catch (UnexpectedResultException var14) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, var14.getResult()));
         }

         if ((state_0 & 1) != 0) {
            try {
               return this.doInt(leftNodeValue_, rightNodeValue_);
            } catch (ArithmeticException var13) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 1;
                  this.state_0_ &= -2;
               } finally {
                  lock.unlock();
               }

               return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
            }
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
         }
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 30) == 0 && (state_0 & 31) != 0) {
            this.executeInt(frameValue);
         } else if ((state_0 & 29) == 0 && (state_0 & 31) != 0) {
            this.executeDouble(frameValue);
         } else {
            this.execute(frameValue);
         }
      } catch (UnexpectedResultException var4) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }
   }

   private Object executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if ((exclude & 1) == 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            if (rightNodeValue instanceof Integer) {
               int rightNodeValue_ = (Integer)rightNodeValue;
               int var32;
               this.state_0_ = var32 = state_0 | 1;

               try {
                  lock.unlock();
                  hasLock = false;
                  return this.doInt(leftNodeValue_, rightNodeValue_);
               } catch (ArithmeticException var22) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  lock.lock();

                  try {
                     this.exclude_ |= 1;
                     this.state_0_ &= -2;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
               }
            }
         }

         int doubleCast0;
         if ((exclude & 2) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
            double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
            int doubleCast1;
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               int var34;
               this.exclude_ = var34 = exclude | 1;
               state_0 &= -2;
               state_0 |= doubleCast0 << 5;
               state_0 |= doubleCast1 << 9;
               int var31;
               this.state_0_ = var31 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doDouble(leftNodeValue_, rightNodeValue_);
            }
         }

         if ((exclude & 4) == 0 && leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_ = (BigInt)rightNodeValue;
               int var27;
               this.state_0_ = var27 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(leftNodeValue_, rightNodeValue_);
            }
         }

         if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
            int var24;
            this.state_0_ = var24 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         } else if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            throw new UnsupportedSpecializationException(this, new Node[]{super.leftNode, super.rightNode}, leftNodeValue, rightNodeValue);
         } else {
            JSSubtractNodeGen.GenericData s4_ = super.insert(new JSSubtractNodeGen.GenericData());
            s4_.toNumericA_ = s4_.insertAccessor(JSToNumericNode.create());
            s4_.toNumericB_ = s4_.insertAccessor(JSToNumericNode.create());
            s4_.subtract_ = s4_.insertAccessor(this.copyRecursive());
            s4_.mixedNumericTypes_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.generic_cache = s4_;
            int var33;
            this.exclude_ = var33 = exclude | 7;
            state_0 &= -8;
            int var26;
            this.state_0_ = var26 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(leftNodeValue, rightNodeValue, s4_.toNumericA_, s4_.toNumericB_, s4_.subtract_, s4_.mixedNumericTypes_);
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
      if ((state_0 & 31) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 31 & (state_0 & 31) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[6];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doInt", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doOverloaded", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doGeneric", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSSubtractNodeGen.GenericData s4_ = this.generic_cache;
         if (s4_ != null) {
            cached.add(Arrays.asList(s4_.toNumericA_, s4_.toNumericB_, s4_.subtract_, s4_.mixedNumericTypes_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      return Introspection.Provider.create(data);
   }

   public static JSSubtractNode create(boolean truncate, JavaScriptNode left, JavaScriptNode right) {
      return new JSSubtractNodeGen(truncate, left, right);
   }

   @GeneratedBy(JSSubtractNode.class)
   private static final class GenericData extends Node {
      @Node.Child
      JSToNumericNode toNumericA_;
      @Node.Child
      JSToNumericNode toNumericB_;
      @Node.Child
      JavaScriptNode subtract_;
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
