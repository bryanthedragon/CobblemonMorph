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
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSDoubleToStringNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSAddNode.class)
public final class JSAddNodeGen extends JSAddNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSConcatStringsNode concatStringsNode;
   @Node.Child
   private TruffleString.FromLongNode stringFromLongNode;
   @Node.Child
   private JSDoubleToStringNode doubleToStringNode;
   @Node.Child
   private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSAddNodeGen.PrimitiveConversionData primitiveConversion_cache;

   private JSAddNodeGen(boolean truncate, JavaScriptNode left, JavaScriptNode right) {
      super(truncate, left, right);
   }

   @Override
   public Object execute(Object leftNodeValue, Object rightNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 15) != 0 && leftNodeValue instanceof Integer) {
         int leftNodeValue_ = (Integer)leftNodeValue;
         if ((state_0 & 7) != 0 && rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            if ((state_0 & 1) != 0) {
               assert this.truncate;

               return JSAddNode.doIntTruncate(leftNodeValue_, rightNodeValue_);
            }

            if ((state_0 & 2) != 0) {
               assert !this.truncate;

               try {
                  return JSAddNode.doInt(leftNodeValue_, rightNodeValue_);
               } catch (ArithmeticException var68) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  Lock lock = this.getLock();
                  lock.lock();

                  try {
                     this.exclude_ |= 2;
                     this.state_0_ &= -3;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
               }
            }

            if ((state_0 & 4) != 0) {
               assert !this.truncate;

               try {
                  return JSAddNode.doIntOverflow(leftNodeValue_, rightNodeValue_);
               } catch (ArithmeticException var69) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  Lock lock = this.getLock();
                  lock.lock();

                  try {
                     this.exclude_ |= 4;
                     this.state_0_ &= -5;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
               }
            }
         }

         if ((state_0 & 8) != 0 && rightNodeValue instanceof SafeInteger) {
            SafeInteger rightNodeValue_x = (SafeInteger)rightNodeValue;

            try {
               return JSAddNode.doIntSafeInteger(leftNodeValue_, rightNodeValue_x);
            } catch (ArithmeticException var70) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 8;
                  this.state_0_ &= -9;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_x);
            }
         }
      }

      if ((state_0 & 48) != 0 && leftNodeValue instanceof SafeInteger) {
         SafeInteger leftNodeValue_x = (SafeInteger)leftNodeValue;
         if ((state_0 & 16) != 0 && rightNodeValue instanceof Integer) {
            int rightNodeValue_x = (Integer)rightNodeValue;

            try {
               return JSAddNode.doSafeIntegerInt(leftNodeValue_x, rightNodeValue_x);
            } catch (ArithmeticException var71) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 16;
                  this.state_0_ &= -17;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(leftNodeValue_x, rightNodeValue_x);
            }
         }

         if ((state_0 & 32) != 0 && rightNodeValue instanceof SafeInteger) {
            SafeInteger rightNodeValue_x = (SafeInteger)rightNodeValue;

            try {
               return JSAddNode.doSafeInteger(leftNodeValue_x, rightNodeValue_x);
            } catch (ArithmeticException var72) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 32;
                  this.state_0_ &= -33;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(leftNodeValue_x, rightNodeValue_x);
            }
         }
      }

      if ((state_0 & 64) != 0 && JSTypesGen.isImplicitDouble((state_0 & 491520) >>> 15, leftNodeValue)) {
         double leftNodeValue_xx = JSTypesGen.asImplicitDouble((state_0 & 491520) >>> 15, leftNodeValue);
         if (JSTypesGen.isImplicitDouble((state_0 & 7864320) >>> 19, rightNodeValue)) {
            double rightNodeValue_x = JSTypesGen.asImplicitDouble((state_0 & 7864320) >>> 19, rightNodeValue);
            return JSAddNode.doDouble(leftNodeValue_xx, rightNodeValue_x);
         }
      }

      if ((state_0 & 128) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_xx = (BigInt)leftNodeValue;
         if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_x = (BigInt)rightNodeValue;
            return this.doBigInt(leftNodeValue_xx, rightNodeValue_x);
         }
      }

      if ((state_0 & 768) != 0 && leftNodeValue instanceof TruffleString) {
         TruffleString leftNodeValue_xx = (TruffleString)leftNodeValue;
         if ((state_0 & 256) != 0 && rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_x = (TruffleString)rightNodeValue;
            return this.doString(leftNodeValue_xx, rightNodeValue_x, this.concatStringsNode);
         }

         if ((state_0 & 512) != 0 && rightNodeValue instanceof Integer) {
            int rightNodeValue_x = (Integer)rightNodeValue;
            return this.doStringInt(leftNodeValue_xx, rightNodeValue_x, this.concatStringsNode, this.stringFromLongNode);
         }
      }

      if ((state_0 & 1024) != 0 && leftNodeValue instanceof Integer) {
         int leftNodeValue_xxx = (Integer)leftNodeValue;
         if (rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_x = (TruffleString)rightNodeValue;
            return this.doIntString(leftNodeValue_xxx, rightNodeValue_x, this.concatStringsNode, this.stringFromLongNode);
         }
      }

      if ((state_0 & 2048) != 0 && leftNodeValue instanceof TruffleString) {
         TruffleString leftNodeValue_xxx = (TruffleString)leftNodeValue;
         if (JSGuards.isNumber(rightNodeValue)) {
            return this.doStringNumber(leftNodeValue_xxx, rightNodeValue, this.concatStringsNode, this.doubleToStringNode);
         }
      }

      if ((state_0 & 28672) != 0) {
         if ((state_0 & 4096) != 0 && rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_x = (TruffleString)rightNodeValue;
            if (JSGuards.isNumber(leftNodeValue)) {
               return this.doNumberString(leftNodeValue, rightNodeValue_x, this.concatStringsNode, this.doubleToStringNode);
            }
         }

         if ((state_0 & 24576) != 0) {
            if ((state_0 & 8192) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
               return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
            }

            if ((state_0 & 16384) != 0) {
               JSAddNodeGen.PrimitiveConversionData s14_ = this.primitiveConversion_cache;
               if (s14_ != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
                  return this.doPrimitiveConversion(
                     leftNodeValue,
                     rightNodeValue,
                     s14_.toPrimitiveA_,
                     s14_.toPrimitiveB_,
                     s14_.toNumericA_,
                     s14_.toNumericB_,
                     s14_.toStringA_,
                     s14_.toStringB_,
                     s14_.profileA_,
                     s14_.profileB_,
                     s14_.add_,
                     s14_.mixedNumericTypes_
                  );
               }
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 32760) == 0 && (state_0 & 32767) != 0) {
         return this.execute_int_int0(state_0, frameValue);
      } else if ((state_0 & 32703) == 0 && (state_0 & 32767) != 0) {
         return this.execute_double_double1(state_0, frameValue);
      } else if ((state_0 & 31735) == 0 && (state_0 & 32767) != 0) {
         return this.execute_int2(state_0, frameValue);
      } else {
         return (state_0 & 32239) == 0 && (state_0 & 32767) != 0 ? this.execute_int3(state_0, frameValue) : this.execute_generic4(state_0, frameValue);
      }
   }

   private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
      int leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeInt(frameValue);
      } catch (UnexpectedResultException var26) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var26.getResult(), rightNodeValue);
      }

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var25) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var25.getResult());
      }

      if ((state_0 & 1) != 0) {
         assert this.truncate;

         return JSAddNode.doIntTruncate(leftNodeValue_, rightNodeValue_);
      } else if ((state_0 & 2) != 0) {
         assert !this.truncate;

         try {
            return JSAddNode.doInt(leftNodeValue_, rightNodeValue_);
         } catch (ArithmeticException var23) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 2;
               this.state_0_ &= -3;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
         }
      } else if ((state_0 & 4) != 0) {
         assert !this.truncate;

         try {
            return JSAddNode.doIntOverflow(leftNodeValue_, rightNodeValue_);
         } catch (ArithmeticException var24) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 4;
               this.state_0_ &= -5;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
         }
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   private Object execute_double_double1(int state_0, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 458752) == 0 && (state_0 & 32767) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 425984) == 0 && (state_0 & 32767) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 229376) == 0 && (state_0 & 32767) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 491520) >>> 15, leftNodeValue__);
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
         if ((state_0 & 7340032) == 0 && (state_0 & 32767) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 6815744) == 0 && (state_0 & 32767) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 3670016) == 0 && (state_0 & 32767) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 7864320) >>> 19, rightNodeValue__);
         }
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 425984) == 0 && (state_0 & 32767) != 0
               ? leftNodeValue_int
               : ((state_0 & 229376) == 0 && (state_0 & 32767) != 0 ? leftNodeValue_long : leftNodeValue_),
            var14.getResult()
         );
      }

      assert (state_0 & 64) != 0;

      return JSAddNode.doDouble(leftNodeValue_, rightNodeValue_);
   }

   private Object execute_int2(int state_0, VirtualFrame frameValue) {
      int leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeInt(frameValue);
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var14.getResult(), rightNodeValue);
      }

      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 8) != 0 && rightNodeValue_ instanceof SafeInteger) {
         SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;

         try {
            return JSAddNode.doIntSafeInteger(leftNodeValue_, rightNodeValue__);
         } catch (ArithmeticException var13) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 8;
               this.state_0_ &= -9;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(leftNodeValue_, rightNodeValue__);
         }
      } else if ((state_0 & 1024) != 0 && rightNodeValue_ instanceof TruffleString) {
         TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
         return this.doIntString(leftNodeValue_, rightNodeValue__, this.concatStringsNode, this.stringFromLongNode);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   private Object execute_int3(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var14.getResult());
      }

      if ((state_0 & 16) != 0 && leftNodeValue_ instanceof SafeInteger) {
         SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;

         try {
            return JSAddNode.doSafeIntegerInt(leftNodeValue__, rightNodeValue_);
         } catch (ArithmeticException var13) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 16;
               this.state_0_ &= -17;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(leftNodeValue__, rightNodeValue_);
         }
      } else if ((state_0 & 512) != 0 && leftNodeValue_ instanceof TruffleString) {
         TruffleString leftNodeValue__ = (TruffleString)leftNodeValue_;
         return this.doStringInt(leftNodeValue__, rightNodeValue_, this.concatStringsNode, this.stringFromLongNode);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   private Object execute_generic4(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 15) != 0 && leftNodeValue_ instanceof Integer) {
         int leftNodeValue__ = (Integer)leftNodeValue_;
         if ((state_0 & 7) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;
            if ((state_0 & 1) != 0) {
               assert this.truncate;

               return JSAddNode.doIntTruncate(leftNodeValue__, rightNodeValue__);
            }

            if ((state_0 & 2) != 0) {
               assert !this.truncate;

               try {
                  return JSAddNode.doInt(leftNodeValue__, rightNodeValue__);
               } catch (ArithmeticException var69) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  Lock lock = this.getLock();
                  lock.lock();

                  try {
                     this.exclude_ |= 2;
                     this.state_0_ &= -3;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(leftNodeValue__, rightNodeValue__);
               }
            }

            if ((state_0 & 4) != 0) {
               assert !this.truncate;

               try {
                  return JSAddNode.doIntOverflow(leftNodeValue__, rightNodeValue__);
               } catch (ArithmeticException var70) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  Lock lock = this.getLock();
                  lock.lock();

                  try {
                     this.exclude_ |= 4;
                     this.state_0_ &= -5;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(leftNodeValue__, rightNodeValue__);
               }
            }
         }

         if ((state_0 & 8) != 0 && rightNodeValue_ instanceof SafeInteger) {
            SafeInteger rightNodeValue__x = (SafeInteger)rightNodeValue_;

            try {
               return JSAddNode.doIntSafeInteger(leftNodeValue__, rightNodeValue__x);
            } catch (ArithmeticException var71) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 8;
                  this.state_0_ &= -9;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(leftNodeValue__, rightNodeValue__x);
            }
         }
      }

      if ((state_0 & 48) != 0 && leftNodeValue_ instanceof SafeInteger) {
         SafeInteger leftNodeValue__x = (SafeInteger)leftNodeValue_;
         if ((state_0 & 16) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__x = (Integer)rightNodeValue_;

            try {
               return JSAddNode.doSafeIntegerInt(leftNodeValue__x, rightNodeValue__x);
            } catch (ArithmeticException var72) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 16;
                  this.state_0_ &= -17;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(leftNodeValue__x, rightNodeValue__x);
            }
         }

         if ((state_0 & 32) != 0 && rightNodeValue_ instanceof SafeInteger) {
            SafeInteger rightNodeValue__x = (SafeInteger)rightNodeValue_;

            try {
               return JSAddNode.doSafeInteger(leftNodeValue__x, rightNodeValue__x);
            } catch (ArithmeticException var73) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 32;
                  this.state_0_ &= -33;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(leftNodeValue__x, rightNodeValue__x);
            }
         }
      }

      if ((state_0 & 64) != 0 && JSTypesGen.isImplicitDouble((state_0 & 491520) >>> 15, leftNodeValue_)) {
         double leftNodeValue__xx = JSTypesGen.asImplicitDouble((state_0 & 491520) >>> 15, leftNodeValue_);
         if (JSTypesGen.isImplicitDouble((state_0 & 7864320) >>> 19, rightNodeValue_)) {
            double rightNodeValue__x = JSTypesGen.asImplicitDouble((state_0 & 7864320) >>> 19, rightNodeValue_);
            return JSAddNode.doDouble(leftNodeValue__xx, rightNodeValue__x);
         }
      }

      if ((state_0 & 128) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__xx = (BigInt)leftNodeValue_;
         if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__x = (BigInt)rightNodeValue_;
            return this.doBigInt(leftNodeValue__xx, rightNodeValue__x);
         }
      }

      if ((state_0 & 768) != 0 && leftNodeValue_ instanceof TruffleString) {
         TruffleString leftNodeValue__xx = (TruffleString)leftNodeValue_;
         if ((state_0 & 256) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__x = (TruffleString)rightNodeValue_;
            return this.doString(leftNodeValue__xx, rightNodeValue__x, this.concatStringsNode);
         }

         if ((state_0 & 512) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__x = (Integer)rightNodeValue_;
            return this.doStringInt(leftNodeValue__xx, rightNodeValue__x, this.concatStringsNode, this.stringFromLongNode);
         }
      }

      if ((state_0 & 1024) != 0 && leftNodeValue_ instanceof Integer) {
         int leftNodeValue__xxx = (Integer)leftNodeValue_;
         if (rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__x = (TruffleString)rightNodeValue_;
            return this.doIntString(leftNodeValue__xxx, rightNodeValue__x, this.concatStringsNode, this.stringFromLongNode);
         }
      }

      if ((state_0 & 2048) != 0 && leftNodeValue_ instanceof TruffleString) {
         TruffleString leftNodeValue__xxx = (TruffleString)leftNodeValue_;
         if (JSGuards.isNumber(rightNodeValue_)) {
            return this.doStringNumber(leftNodeValue__xxx, rightNodeValue_, this.concatStringsNode, this.doubleToStringNode);
         }
      }

      if ((state_0 & 28672) != 0) {
         if ((state_0 & 4096) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__x = (TruffleString)rightNodeValue_;
            if (JSGuards.isNumber(leftNodeValue_)) {
               return this.doNumberString(leftNodeValue_, rightNodeValue__x, this.concatStringsNode, this.doubleToStringNode);
            }
         }

         if ((state_0 & 24576) != 0) {
            if ((state_0 & 8192) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
               return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
            }

            if ((state_0 & 16384) != 0) {
               JSAddNodeGen.PrimitiveConversionData s14_ = this.primitiveConversion_cache;
               if (s14_ != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
                  return this.doPrimitiveConversion(
                     leftNodeValue_,
                     rightNodeValue_,
                     s14_.toPrimitiveA_,
                     s14_.toPrimitiveB_,
                     s14_.toNumericA_,
                     s14_.toNumericB_,
                     s14_.toStringA_,
                     s14_.toStringB_,
                     s14_.profileA_,
                     s14_.profileB_,
                     s14_.add_,
                     s14_.mixedNumericTypes_
                  );
               }
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 30724) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else {
         long leftNodeValue_long = 0L;
         int leftNodeValue_int = 0;

         double leftNodeValue_;
         try {
            if ((state_0 & 458752) == 0 && (state_0 & 32767) != 0) {
               leftNodeValue_ = super.leftNode.executeDouble(frameValue);
            } else if ((state_0 & 425984) == 0 && (state_0 & 32767) != 0) {
               leftNodeValue_int = super.leftNode.executeInt(frameValue);
               leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            } else if ((state_0 & 229376) == 0 && (state_0 & 32767) != 0) {
               leftNodeValue_long = super.leftNode.executeLong(frameValue);
               leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
            } else {
               Object leftNodeValue__ = super.leftNode.execute(frameValue);
               leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 491520) >>> 15, leftNodeValue__);
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
            if ((state_0 & 7340032) == 0 && (state_0 & 32767) != 0) {
               rightNodeValue_ = super.rightNode.executeDouble(frameValue);
            } else if ((state_0 & 6815744) == 0 && (state_0 & 32767) != 0) {
               rightNodeValue_int = super.rightNode.executeInt(frameValue);
               rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            } else if ((state_0 & 3670016) == 0 && (state_0 & 32767) != 0) {
               rightNodeValue_long = super.rightNode.executeLong(frameValue);
               rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
            } else {
               Object rightNodeValue__ = super.rightNode.execute(frameValue);
               rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 7864320) >>> 19, rightNodeValue__);
            }
         } catch (UnexpectedResultException var14) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(
               this.executeAndSpecialize(
                  (state_0 & 425984) == 0 && (state_0 & 32767) != 0
                     ? leftNodeValue_int
                     : ((state_0 & 229376) == 0 && (state_0 & 32767) != 0 ? leftNodeValue_long : leftNodeValue_),
                  var14.getResult()
               )
            );
         }

         if ((state_0 & 64) != 0) {
            return JSAddNode.doDouble(leftNodeValue_, rightNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(
               this.executeAndSpecialize(
                  (state_0 & 425984) == 0 && (state_0 & 32767) != 0
                     ? leftNodeValue_int
                     : ((state_0 & 229376) == 0 && (state_0 & 32767) != 0 ? leftNodeValue_long : leftNodeValue_),
                  (state_0 & 6815744) == 0 && (state_0 & 32767) != 0
                     ? rightNodeValue_int
                     : ((state_0 & 3670016) == 0 && (state_0 & 32767) != 0 ? rightNodeValue_long : rightNodeValue_)
               )
            );
         }
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 30724) != 0) {
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
            assert this.truncate;

            return JSAddNode.doIntTruncate(leftNodeValue_, rightNodeValue_);
         } else if ((state_0 & 2) != 0) {
            assert !this.truncate;

            try {
               return JSAddNode.doInt(leftNodeValue_, rightNodeValue_);
            } catch (ArithmeticException var13) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 2;
                  this.state_0_ &= -3;
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
         if ((state_0 & 32764) == 0 && (state_0 & 32767) != 0) {
            this.executeInt(frameValue);
         } else if ((state_0 & 32703) == 0 && (state_0 & 32767) != 0) {
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
         if (leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            if (rightNodeValue instanceof Integer) {
               int rightNodeValue_ = (Integer)rightNodeValue;
               if ((exclude & 1) == 0 && this.truncate) {
                  int var110;
                  this.state_0_ = var110 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return JSAddNode.doIntTruncate(leftNodeValue_, rightNodeValue_);
               }

               if ((exclude & 2) == 0 && !this.truncate) {
                  int var109;
                  this.state_0_ = var109 = state_0 | 2;

                  try {
                     lock.unlock();
                     hasLock = false;
                     return JSAddNode.doInt(leftNodeValue_, rightNodeValue_);
                  } catch (ArithmeticException var90) {
                     CompilerDirectives.transferToInterpreterAndInvalidate();
                     lock.lock();

                     try {
                        this.exclude_ |= 2;
                        this.state_0_ &= -3;
                     } finally {
                        lock.unlock();
                     }

                     return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
                  }
               }

               if ((exclude & 4) == 0 && !this.truncate) {
                  int var108;
                  this.state_0_ = var108 = state_0 | 4;

                  try {
                     lock.unlock();
                     hasLock = false;
                     return JSAddNode.doIntOverflow(leftNodeValue_, rightNodeValue_);
                  } catch (ArithmeticException var91) {
                     CompilerDirectives.transferToInterpreterAndInvalidate();
                     lock.lock();

                     try {
                        this.exclude_ |= 4;
                        this.state_0_ &= -5;
                     } finally {
                        lock.unlock();
                     }

                     return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
                  }
               }
            }

            if ((exclude & 8) == 0 && rightNodeValue instanceof SafeInteger) {
               SafeInteger rightNodeValue_x = (SafeInteger)rightNodeValue;
               int var107;
               this.state_0_ = var107 = state_0 | 8;

               try {
                  lock.unlock();
                  hasLock = false;
                  return JSAddNode.doIntSafeInteger(leftNodeValue_, rightNodeValue_x);
               } catch (ArithmeticException var87) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  lock.lock();

                  try {
                     this.exclude_ |= 8;
                     this.state_0_ &= -9;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_x);
               }
            }
         }

         if (leftNodeValue instanceof SafeInteger) {
            SafeInteger leftNodeValue_x = (SafeInteger)leftNodeValue;
            if ((exclude & 16) == 0 && rightNodeValue instanceof Integer) {
               int rightNodeValue_x = (Integer)rightNodeValue;
               int var106;
               this.state_0_ = var106 = state_0 | 16;

               try {
                  lock.unlock();
                  hasLock = false;
                  return JSAddNode.doSafeIntegerInt(leftNodeValue_x, rightNodeValue_x);
               } catch (ArithmeticException var88) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  lock.lock();

                  try {
                     this.exclude_ |= 16;
                     this.state_0_ &= -17;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(leftNodeValue_x, rightNodeValue_x);
               }
            }

            if ((exclude & 32) == 0 && rightNodeValue instanceof SafeInteger) {
               SafeInteger rightNodeValue_x = (SafeInteger)rightNodeValue;
               int var105;
               this.state_0_ = var105 = state_0 | 32;

               try {
                  lock.unlock();
                  hasLock = false;
                  return JSAddNode.doSafeInteger(leftNodeValue_x, rightNodeValue_x);
               } catch (ArithmeticException var89) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  lock.lock();

                  try {
                     this.exclude_ |= 32;
                     this.state_0_ &= -33;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(leftNodeValue_x, rightNodeValue_x);
               }
            }
         }

         int doubleCast0;
         if ((exclude & 64) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
            double leftNodeValue_xx = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
            int doubleCast1;
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               state_0 |= doubleCast0 << 15;
               state_0 |= doubleCast1 << 19;
               int var104;
               this.state_0_ = var104 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return JSAddNode.doDouble(leftNodeValue_xx, rightNodeValue_x);
            }
         }

         if ((exclude & 128) == 0 && leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_xx = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_x = (BigInt)rightNodeValue;
               int var101;
               this.state_0_ = var101 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(leftNodeValue_xx, rightNodeValue_x);
            }
         }

         if (leftNodeValue instanceof TruffleString) {
            TruffleString leftNodeValue_xx = (TruffleString)leftNodeValue;
            if ((exclude & 256) == 0 && rightNodeValue instanceof TruffleString) {
               TruffleString rightNodeValue_x = (TruffleString)rightNodeValue;
               this.concatStringsNode = super.insert(this.concatStringsNode == null ? JSConcatStringsNode.create() : this.concatStringsNode);
               int var100;
               this.state_0_ = var100 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return this.doString(leftNodeValue_xx, rightNodeValue_x, this.concatStringsNode);
            }

            if ((exclude & 512) == 0 && rightNodeValue instanceof Integer) {
               int rightNodeValue_x = (Integer)rightNodeValue;
               this.concatStringsNode = super.insert(this.concatStringsNode == null ? JSConcatStringsNode.create() : this.concatStringsNode);
               this.stringFromLongNode = super.insert(this.stringFromLongNode == null ? TruffleString.FromLongNode.create() : this.stringFromLongNode);
               int var99;
               this.state_0_ = var99 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return this.doStringInt(leftNodeValue_xx, rightNodeValue_x, this.concatStringsNode, this.stringFromLongNode);
            }
         }

         if ((exclude & 1024) == 0 && leftNodeValue instanceof Integer) {
            doubleCast0 = (Integer)leftNodeValue;
            if (rightNodeValue instanceof TruffleString) {
               TruffleString rightNodeValue_x = (TruffleString)rightNodeValue;
               this.concatStringsNode = super.insert(this.concatStringsNode == null ? JSConcatStringsNode.create() : this.concatStringsNode);
               this.stringFromLongNode = super.insert(this.stringFromLongNode == null ? TruffleString.FromLongNode.create() : this.stringFromLongNode);
               int var98;
               this.state_0_ = var98 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               return this.doIntString(doubleCast0, rightNodeValue_x, this.concatStringsNode, this.stringFromLongNode);
            }
         }

         if ((exclude & 2048) == 0 && leftNodeValue instanceof TruffleString) {
            TruffleString leftNodeValue_xxx = (TruffleString)leftNodeValue;
            if (JSGuards.isNumber(rightNodeValue)) {
               this.concatStringsNode = super.insert(this.concatStringsNode == null ? JSConcatStringsNode.create() : this.concatStringsNode);
               this.doubleToStringNode = super.insert(this.doubleToStringNode == null ? JSDoubleToStringNode.create() : this.doubleToStringNode);
               int var97;
               this.state_0_ = var97 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               return this.doStringNumber(leftNodeValue_xxx, rightNodeValue, this.concatStringsNode, this.doubleToStringNode);
            }
         }

         if ((exclude & 4096) == 0 && rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_x = (TruffleString)rightNodeValue;
            if (JSGuards.isNumber(leftNodeValue)) {
               this.concatStringsNode = super.insert(this.concatStringsNode == null ? JSConcatStringsNode.create() : this.concatStringsNode);
               this.doubleToStringNode = super.insert(this.doubleToStringNode == null ? JSDoubleToStringNode.create() : this.doubleToStringNode);
               int var96;
               this.state_0_ = var96 = state_0 | 4096;
               lock.unlock();
               hasLock = false;
               return this.doNumberString(leftNodeValue, rightNodeValue_x, this.concatStringsNode, this.doubleToStringNode);
            }
         }

         if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createHintDefault(this.getOverloadedOperatorName()));
            int var93;
            this.state_0_ = var93 = state_0 | 8192;
            lock.unlock();
            hasLock = false;
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         } else if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            throw new UnsupportedSpecializationException(this, new Node[]{super.leftNode, super.rightNode}, leftNodeValue, rightNodeValue);
         } else {
            JSAddNodeGen.PrimitiveConversionData s14_ = super.insert(new JSAddNodeGen.PrimitiveConversionData());
            s14_.toPrimitiveA_ = s14_.insertAccessor(JSToPrimitiveNode.createHintDefault());
            s14_.toPrimitiveB_ = s14_.insertAccessor(JSToPrimitiveNode.createHintDefault());
            s14_.toNumericA_ = s14_.insertAccessor(JSToNumericNode.create());
            s14_.toNumericB_ = s14_.insertAccessor(JSToNumericNode.create());
            s14_.toStringA_ = s14_.insertAccessor(JSToStringNode.create());
            s14_.toStringB_ = s14_.insertAccessor(JSToStringNode.create());
            s14_.profileA_ = ConditionProfile.createBinaryProfile();
            s14_.profileB_ = ConditionProfile.createBinaryProfile();
            s14_.add_ = s14_.insertAccessor(this.copyRecursive());
            s14_.mixedNumericTypes_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.primitiveConversion_cache = s14_;
            int var111;
            this.exclude_ = var111 = exclude | 8191;
            state_0 &= -8192;
            int var95;
            this.state_0_ = var95 = state_0 | 16384;
            lock.unlock();
            hasLock = false;
            return this.doPrimitiveConversion(
               leftNodeValue,
               rightNodeValue,
               s14_.toPrimitiveA_,
               s14_.toPrimitiveB_,
               s14_.toNumericA_,
               s14_.toNumericB_,
               s14_.toStringA_,
               s14_.toStringB_,
               s14_.profileA_,
               s14_.profileB_,
               s14_.add_,
               s14_.mixedNumericTypes_
            );
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
      if ((state_0 & 32767) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 32767 & (state_0 & 32767) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[16];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doIntTruncate", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doInt", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doIntOverflow", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doIntSafeInteger", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doSafeIntegerInt", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 16) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 32) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 64) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 128) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.concatStringsNode));
         s[2] = cached;
      } else if ((exclude & 256) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doStringInt", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.concatStringsNode, this.stringFromLongNode));
         s[2] = cached;
      } else if ((exclude & 512) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doIntString", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.concatStringsNode, this.stringFromLongNode));
         s[2] = cached;
      } else if ((exclude & 1024) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doStringNumber", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.concatStringsNode, this.doubleToStringNode));
         s[2] = cached;
      } else if ((exclude & 2048) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      s = new Object[]{"doNumberString", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.concatStringsNode, this.doubleToStringNode));
         s[2] = cached;
      } else if ((exclude & 4096) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      s = new Object[]{"doOverloaded", null, null};
      if ((state_0 & 8192) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[14] = s;
      s = new Object[]{"doPrimitiveConversion", null, null};
      if ((state_0 & 16384) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSAddNodeGen.PrimitiveConversionData s14_ = this.primitiveConversion_cache;
         if (s14_ != null) {
            cached.add(
               Arrays.asList(
                  s14_.toPrimitiveA_,
                  s14_.toPrimitiveB_,
                  s14_.toNumericA_,
                  s14_.toNumericB_,
                  s14_.toStringA_,
                  s14_.toStringB_,
                  s14_.profileA_,
                  s14_.profileB_,
                  s14_.add_,
                  s14_.mixedNumericTypes_
               )
            );
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[15] = s;
      return Introspection.Provider.create(data);
   }

   public static JSAddNode create(boolean truncate, JavaScriptNode left, JavaScriptNode right) {
      return new JSAddNodeGen(truncate, left, right);
   }

   @GeneratedBy(JSAddNode.class)
   private static final class PrimitiveConversionData extends Node {
      @Node.Child
      JSToPrimitiveNode toPrimitiveA_;
      @Node.Child
      JSToPrimitiveNode toPrimitiveB_;
      @Node.Child
      JSToNumericNode toNumericA_;
      @Node.Child
      JSToNumericNode toNumericB_;
      @Node.Child
      JSToStringNode toStringA_;
      @Node.Child
      JSToStringNode toStringB_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile profileA_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile profileB_;
      @Node.Child
      JSAddNode add_;
      @CompilerDirectives.CompilationFinal
      BranchProfile mixedNumericTypes_;

      PrimitiveConversionData() {
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
