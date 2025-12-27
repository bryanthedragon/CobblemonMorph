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

@GeneratedBy(JSMultiplyNode.class)
public final class JSMultiplyNodeGen extends JSMultiplyNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private BranchProfile int_resultZeroBranch_;
   @Node.Child
   private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSMultiplyNodeGen.GenericData generic_cache;

   private JSMultiplyNodeGen(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   @Override
   public Object execute(Object leftNodeValue, Object rightNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 7) != 0 && leftNodeValue instanceof Integer) {
         int leftNodeValue_ = (Integer)leftNodeValue;
         if (rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            if ((state_0 & 1) != 0 && rightNodeValue_ > 0) {
               try {
                  return this.doIntBLargerZero(leftNodeValue_, rightNodeValue_);
               } catch (ArithmeticException var32) {
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

            if ((state_0 & 2) != 0 && leftNodeValue_ > 0) {
               try {
                  return this.doIntALargerZero(leftNodeValue_, rightNodeValue_);
               } catch (ArithmeticException var33) {
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
               try {
                  return this.doInt(leftNodeValue_, rightNodeValue_, this.int_resultZeroBranch_);
               } catch (ArithmeticException var34) {
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
      }

      if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 1920) >>> 7, leftNodeValue)) {
         double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 1920) >>> 7, leftNodeValue);
         if (JSTypesGen.isImplicitDouble((state_0 & 30720) >>> 11, rightNodeValue)) {
            double rightNodeValue_x = JSTypesGen.asImplicitDouble((state_0 & 30720) >>> 11, rightNodeValue);
            return this.doDouble(leftNodeValue_, rightNodeValue_x);
         }
      }

      if ((state_0 & 16) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_ = (BigInt)leftNodeValue;
         if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_x = (BigInt)rightNodeValue;
            return this.doBigInts(leftNodeValue_, rightNodeValue_x);
         }
      }

      if ((state_0 & 96) != 0) {
         if ((state_0 & 32) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 64) != 0) {
            JSMultiplyNodeGen.GenericData s6_ = this.generic_cache;
            if (s6_ != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
               return this.doGeneric(leftNodeValue, rightNodeValue, s6_.nestedMultiplyNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 120) == 0 && (state_0 & 127) != 0) {
         return this.execute_int_int0(state_0, frameValue);
      } else {
         return (state_0 & 119) == 0 && (state_0 & 127) != 0 ? this.execute_double_double1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
      }
   }

   private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
      int leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeInt(frameValue);
      } catch (UnexpectedResultException var41) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var41.getResult(), rightNodeValue);
      }

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var40) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var40.getResult());
      }

      if ((state_0 & 1) != 0 && rightNodeValue_ > 0) {
         try {
            return this.doIntBLargerZero(leftNodeValue_, rightNodeValue_);
         } catch (ArithmeticException var37) {
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
      } else if ((state_0 & 2) != 0 && leftNodeValue_ > 0) {
         try {
            return this.doIntALargerZero(leftNodeValue_, rightNodeValue_);
         } catch (ArithmeticException var38) {
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
         try {
            return this.doInt(leftNodeValue_, rightNodeValue_, this.int_resultZeroBranch_);
         } catch (ArithmeticException var39) {
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

      assert (state_0 & 8) != 0;

      return this.doDouble(leftNodeValue_, rightNodeValue_);
   }

   private Object execute_generic2(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 7) != 0 && leftNodeValue_ instanceof Integer) {
         int leftNodeValue__ = (Integer)leftNodeValue_;
         if (rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;
            if ((state_0 & 1) != 0 && rightNodeValue__ > 0) {
               try {
                  return this.doIntBLargerZero(leftNodeValue__, rightNodeValue__);
               } catch (ArithmeticException var33) {
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

            if ((state_0 & 2) != 0 && leftNodeValue__ > 0) {
               try {
                  return this.doIntALargerZero(leftNodeValue__, rightNodeValue__);
               } catch (ArithmeticException var34) {
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
               try {
                  return this.doInt(leftNodeValue__, rightNodeValue__, this.int_resultZeroBranch_);
               } catch (ArithmeticException var35) {
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
      }

      if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 1920) >>> 7, leftNodeValue_)) {
         double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 1920) >>> 7, leftNodeValue_);
         if (JSTypesGen.isImplicitDouble((state_0 & 30720) >>> 11, rightNodeValue_)) {
            double rightNodeValue__x = JSTypesGen.asImplicitDouble((state_0 & 30720) >>> 11, rightNodeValue_);
            return this.doDouble(leftNodeValue__, rightNodeValue__x);
         }
      }

      if ((state_0 & 16) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
         if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__x = (BigInt)rightNodeValue_;
            return this.doBigInts(leftNodeValue__, rightNodeValue__x);
         }
      }

      if ((state_0 & 96) != 0) {
         if ((state_0 & 32) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
            return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 64) != 0) {
            JSMultiplyNodeGen.GenericData s6_ = this.generic_cache;
            if (s6_ != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
               return this.doGeneric(leftNodeValue_, rightNodeValue_, s6_.nestedMultiplyNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_);
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

         if ((state_0 & 8) != 0) {
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
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 96) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else {
         int leftNodeValue_;
         try {
            leftNodeValue_ = super.leftNode.executeInt(frameValue);
         } catch (UnexpectedResultException var41) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = super.rightNode.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(var41.getResult(), rightNodeValue));
         }

         int rightNodeValue_;
         try {
            rightNodeValue_ = super.rightNode.executeInt(frameValue);
         } catch (UnexpectedResultException var40) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, var40.getResult()));
         }

         if ((state_0 & 1) != 0 && rightNodeValue_ > 0) {
            try {
               return this.doIntBLargerZero(leftNodeValue_, rightNodeValue_);
            } catch (ArithmeticException var37) {
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
         } else if ((state_0 & 2) != 0 && leftNodeValue_ > 0) {
            try {
               return this.doIntALargerZero(leftNodeValue_, rightNodeValue_);
            } catch (ArithmeticException var38) {
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
         } else if ((state_0 & 4) != 0) {
            try {
               return this.doInt(leftNodeValue_, rightNodeValue_, this.int_resultZeroBranch_);
            } catch (ArithmeticException var39) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 4;
                  this.state_0_ &= -5;
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
         if ((state_0 & 120) == 0 && (state_0 & 127) != 0) {
            this.executeInt(frameValue);
         } else if ((state_0 & 119) == 0 && (state_0 & 127) != 0) {
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
               if ((exclude & 1) == 0 && rightNodeValue_ > 0) {
                  int var59;
                  this.state_0_ = var59 = state_0 | 1;

                  try {
                     lock.unlock();
                     hasLock = false;
                     return this.doIntBLargerZero(leftNodeValue_, rightNodeValue_);
                  } catch (ArithmeticException var47) {
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

               if ((exclude & 2) == 0 && leftNodeValue_ > 0) {
                  int var58;
                  this.state_0_ = var58 = state_0 | 2;

                  try {
                     lock.unlock();
                     hasLock = false;
                     return this.doIntALargerZero(leftNodeValue_, rightNodeValue_);
                  } catch (ArithmeticException var48) {
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

               if ((exclude & 4) == 0) {
                  this.int_resultZeroBranch_ = BranchProfile.create();
                  int var57;
                  this.state_0_ = var57 = state_0 | 4;

                  try {
                     lock.unlock();
                     hasLock = false;
                     return this.doInt(leftNodeValue_, rightNodeValue_, this.int_resultZeroBranch_);
                  } catch (ArithmeticException var46) {
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
         }

         int doubleCast0;
         if ((exclude & 8) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
            double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
            int doubleCast1;
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               state_0 |= doubleCast0 << 7;
               state_0 |= doubleCast1 << 11;
               int var56;
               this.state_0_ = var56 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doDouble(leftNodeValue_, rightNodeValue_x);
            }
         }

         if (leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_x = (BigInt)rightNodeValue;
               int var53;
               this.state_0_ = var53 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doBigInts(leftNodeValue_, rightNodeValue_x);
            }
         }

         if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
            int var50;
            this.state_0_ = var50 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         } else if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            throw new UnsupportedSpecializationException(this, new Node[]{super.leftNode, super.rightNode}, leftNodeValue, rightNodeValue);
         } else {
            JSMultiplyNodeGen.GenericData s6_ = super.insert(new JSMultiplyNodeGen.GenericData());
            s6_.nestedMultiplyNode_ = s6_.insertAccessor(JSMultiplyNode.create());
            s6_.toNumeric1Node_ = s6_.insertAccessor(JSToNumericNode.create());
            s6_.toNumeric2Node_ = s6_.insertAccessor(JSToNumericNode.create());
            s6_.mixedNumericTypes_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.generic_cache = s6_;
            int var60;
            this.exclude_ = var60 = exclude | 8;
            state_0 &= -9;
            int var52;
            this.state_0_ = var52 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(leftNodeValue, rightNodeValue, s6_.nestedMultiplyNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_);
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
      Object[] s = new Object[]{"doIntBLargerZero", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doIntALargerZero", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doInt", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.int_resultZeroBranch_));
         s[2] = cached;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doBigInts", null, null};
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
         JSMultiplyNodeGen.GenericData s6_ = this.generic_cache;
         if (s6_ != null) {
            cached.add(Arrays.asList(s6_.nestedMultiplyNode_, s6_.toNumeric1Node_, s6_.toNumeric2Node_, s6_.mixedNumericTypes_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      return Introspection.Provider.create(data);
   }

   public static JSMultiplyNode create(JavaScriptNode left, JavaScriptNode right) {
      return new JSMultiplyNodeGen(left, right);
   }

   @GeneratedBy(JSMultiplyNode.class)
   private static final class GenericData extends Node {
      @Node.Child
      JSMultiplyNode nestedMultiplyNode_;
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
