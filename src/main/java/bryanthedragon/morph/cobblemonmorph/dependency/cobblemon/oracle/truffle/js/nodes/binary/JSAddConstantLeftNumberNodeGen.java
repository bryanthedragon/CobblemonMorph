package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSAddConstantLeftNumberNode.class)
public final class JSAddConstantLeftNumberNodeGen extends JSAddConstantLeftNumberNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private TruffleString numberString_leftString_;
   @Node.Child
   private JSConcatStringsNode numberString_createLazyString_;
   @Node.Child
   private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSAddConstantLeftNumberNodeGen.PrimitiveConversionData primitiveConversion_cache;

   private JSAddConstantLeftNumberNodeGen(Number leftValue, JavaScriptNode right, boolean truncate) {
      super(leftValue, right, truncate);
   }

   @Override
   public Object execute(Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 7) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;
         if ((state_0 & 1) != 0) {
            assert this.truncate;

            assert this.isInt;

            return this.doIntTruncate(operandNodeValue_);
         }

         if ((state_0 & 2) != 0) {
            assert !this.truncate;

            assert this.isInt;

            try {
               return this.doInt(operandNodeValue_);
            } catch (ArithmeticException var30) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 1;
                  this.state_0_ &= -3;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(operandNodeValue_);
            }
         }

         if ((state_0 & 4) != 0) {
            assert !this.truncate;

            assert this.isSafeLong;

            try {
               return this.doIntOverflow(operandNodeValue_);
            } catch (ArithmeticException var31) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 2;
                  this.state_0_ &= -5;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(operandNodeValue_);
            }
         }
      }

      if ((state_0 & 8) != 0 && operandNodeValue instanceof SafeInteger) {
         SafeInteger operandNodeValue_x = (SafeInteger)operandNodeValue;

         assert this.isInt;

         try {
            return this.doSafeInteger(operandNodeValue_x);
         } catch (ArithmeticException var32) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 4;
               this.state_0_ &= -9;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(operandNodeValue_x);
         }
      } else if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3840) >>> 8, operandNodeValue)) {
         double operandNodeValue_x = JSTypesGen.asImplicitDouble((state_0 & 3840) >>> 8, operandNodeValue);
         return this.doDouble(operandNodeValue_x);
      } else if ((state_0 & 32) != 0 && operandNodeValue instanceof TruffleString) {
         TruffleString operandNodeValue_x = (TruffleString)operandNodeValue;
         return this.doNumberString(operandNodeValue_x, this.numberString_leftString_, this.numberString_createLazyString_);
      } else if ((state_0 & 64) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue_x = (JSOverloadedOperatorsObject)operandNodeValue;
         return this.doOverloaded(operandNodeValue_x, this.overloaded_overloadedOperatorNode_);
      } else {
         if ((state_0 & 128) != 0) {
            JSAddConstantLeftNumberNodeGen.PrimitiveConversionData s7_ = this.primitiveConversion_cache;
            if (s7_ != null && !this.hasOverloadedOperators(operandNodeValue)) {
               return this.doPrimitiveConversion(operandNodeValue, s7_.toPrimitiveB_, s7_.toNumberB_, s7_.leftString_, s7_.createLazyString_, s7_.profileB_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 7) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;
         if ((state_0 & 1) != 0) {
            assert this.truncate;

            assert this.isInt;

            return this.doIntTruncate(operandNodeValue_);
         }

         if ((state_0 & 2) != 0) {
            assert !this.truncate;

            assert this.isInt;

            try {
               return this.doInt(operandNodeValue_);
            } catch (ArithmeticException var31) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 1;
                  this.state_0_ &= -3;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(operandNodeValue_);
            }
         }

         if ((state_0 & 4) != 0) {
            assert !this.truncate;

            assert this.isSafeLong;

            try {
               return this.doIntOverflow(operandNodeValue_);
            } catch (ArithmeticException var32) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 2;
                  this.state_0_ &= -5;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(operandNodeValue_);
            }
         }
      }

      if ((state_0 & 8) != 0 && operandNodeValue instanceof SafeInteger) {
         SafeInteger operandNodeValue_x = (SafeInteger)operandNodeValue;

         assert this.isInt;

         try {
            return this.doSafeInteger(operandNodeValue_x);
         } catch (ArithmeticException var33) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 4;
               this.state_0_ &= -9;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(operandNodeValue_x);
         }
      } else if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3840) >>> 8, operandNodeValue)) {
         double operandNodeValue_x = JSTypesGen.asImplicitDouble((state_0 & 3840) >>> 8, operandNodeValue);
         return this.doDouble(operandNodeValue_x);
      } else if ((state_0 & 32) != 0 && operandNodeValue instanceof TruffleString) {
         TruffleString operandNodeValue_x = (TruffleString)operandNodeValue;
         return this.doNumberString(operandNodeValue_x, this.numberString_leftString_, this.numberString_createLazyString_);
      } else if ((state_0 & 64) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue_x = (JSOverloadedOperatorsObject)operandNodeValue;
         return this.doOverloaded(operandNodeValue_x, this.overloaded_overloadedOperatorNode_);
      } else {
         if ((state_0 & 128) != 0) {
            JSAddConstantLeftNumberNodeGen.PrimitiveConversionData s7_ = this.primitiveConversion_cache;
            if (s7_ != null && !this.hasOverloadedOperators(operandNodeValue)) {
               return this.doPrimitiveConversion(operandNodeValue, s7_.toPrimitiveB_, s7_.toNumberB_, s7_.leftString_, s7_.createLazyString_, s7_.profileB_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 248) == 0 && (state_0 & 0xFF) != 0) {
         return this.execute_int0(state_0, frameValue);
      } else {
         return (state_0 & 239) == 0 && (state_0 & 0xFF) != 0 ? this.execute_double1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
      }
   }

   private Object execute_int0(int state_0, VirtualFrame frameValue) {
      int operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeInt(frameValue);
      } catch (UnexpectedResultException var22) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var22.getResult());
      }

      if ((state_0 & 1) != 0) {
         assert this.truncate;

         assert this.isInt;

         return this.doIntTruncate(operandNodeValue_);
      } else if ((state_0 & 2) != 0) {
         assert !this.truncate;

         assert this.isInt;

         try {
            return this.doInt(operandNodeValue_);
         } catch (ArithmeticException var20) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 1;
               this.state_0_ &= -3;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(operandNodeValue_);
         }
      } else if ((state_0 & 4) != 0) {
         assert !this.truncate;

         assert this.isSafeLong;

         try {
            return this.doIntOverflow(operandNodeValue_);
         } catch (ArithmeticException var21) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 2;
               this.state_0_ &= -5;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(operandNodeValue_);
         }
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   private Object execute_double1(int state_0, VirtualFrame frameValue) {
      long operandNodeValue_long = 0L;
      int operandNodeValue_int = 0;

      double operandNodeValue_;
      try {
         if ((state_0 & 3584) == 0 && (state_0 & 0xFF) != 0) {
            operandNodeValue_ = super.operandNode.executeDouble(frameValue);
         } else if ((state_0 & 3328) == 0 && (state_0 & 0xFF) != 0) {
            operandNodeValue_int = super.operandNode.executeInt(frameValue);
            operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
         } else if ((state_0 & 1792) == 0 && (state_0 & 0xFF) != 0) {
            operandNodeValue_long = super.operandNode.executeLong(frameValue);
            operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
         } else {
            Object operandNodeValue__ = super.operandNode.execute(frameValue);
            operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 3840) >>> 8, operandNodeValue__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var9.getResult());
      }

      assert (state_0 & 16) != 0;

      return this.doDouble(operandNodeValue_);
   }

   private Object execute_generic2(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 7) != 0 && operandNodeValue_ instanceof Integer) {
         int operandNodeValue__ = (Integer)operandNodeValue_;
         if ((state_0 & 1) != 0) {
            assert this.truncate;

            assert this.isInt;

            return this.doIntTruncate(operandNodeValue__);
         }

         if ((state_0 & 2) != 0) {
            assert !this.truncate;

            assert this.isInt;

            try {
               return this.doInt(operandNodeValue__);
            } catch (ArithmeticException var31) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 1;
                  this.state_0_ &= -3;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(operandNodeValue__);
            }
         }

         if ((state_0 & 4) != 0) {
            assert !this.truncate;

            assert this.isSafeLong;

            try {
               return this.doIntOverflow(operandNodeValue__);
            } catch (ArithmeticException var32) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 2;
                  this.state_0_ &= -5;
               } finally {
                  lock.unlock();
               }

               return this.executeAndSpecialize(operandNodeValue__);
            }
         }
      }

      if ((state_0 & 8) != 0 && operandNodeValue_ instanceof SafeInteger) {
         SafeInteger operandNodeValue__x = (SafeInteger)operandNodeValue_;

         assert this.isInt;

         try {
            return this.doSafeInteger(operandNodeValue__x);
         } catch (ArithmeticException var33) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 4;
               this.state_0_ &= -9;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(operandNodeValue__x);
         }
      } else if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3840) >>> 8, operandNodeValue_)) {
         double operandNodeValue__x = JSTypesGen.asImplicitDouble((state_0 & 3840) >>> 8, operandNodeValue_);
         return this.doDouble(operandNodeValue__x);
      } else if ((state_0 & 32) != 0 && operandNodeValue_ instanceof TruffleString) {
         TruffleString operandNodeValue__x = (TruffleString)operandNodeValue_;
         return this.doNumberString(operandNodeValue__x, this.numberString_leftString_, this.numberString_createLazyString_);
      } else if ((state_0 & 64) != 0 && operandNodeValue_ instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue__x = (JSOverloadedOperatorsObject)operandNodeValue_;
         return this.doOverloaded(operandNodeValue__x, this.overloaded_overloadedOperatorNode_);
      } else {
         if ((state_0 & 128) != 0) {
            JSAddConstantLeftNumberNodeGen.PrimitiveConversionData s7_ = this.primitiveConversion_cache;
            if (s7_ != null && !this.hasOverloadedOperators(operandNodeValue_)) {
               return this.doPrimitiveConversion(operandNodeValue_, s7_.toPrimitiveB_, s7_.toNumberB_, s7_.leftString_, s7_.createLazyString_, s7_.profileB_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 228) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else {
         long operandNodeValue_long = 0L;
         int operandNodeValue_int = 0;

         double operandNodeValue_;
         try {
            if ((state_0 & 3584) == 0 && (state_0 & 0xFF) != 0) {
               operandNodeValue_ = super.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 3328) == 0 && (state_0 & 0xFF) != 0) {
               operandNodeValue_int = super.operandNode.executeInt(frameValue);
               operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 1792) == 0 && (state_0 & 0xFF) != 0) {
               operandNodeValue_long = super.operandNode.executeLong(frameValue);
               operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
               Object operandNodeValue__ = super.operandNode.execute(frameValue);
               operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 3840) >>> 8, operandNodeValue__);
            }
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(var9.getResult()));
         }

         if ((state_0 & 16) != 0) {
            return this.doDouble(operandNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(
               this.executeAndSpecialize(
                  (state_0 & 3328) == 0 && (state_0 & 0xFF) != 0
                     ? operandNodeValue_int
                     : ((state_0 & 1792) == 0 && (state_0 & 0xFF) != 0 ? operandNodeValue_long : operandNodeValue_)
               )
            );
         }
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 228) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else {
         int operandNodeValue_;
         try {
            operandNodeValue_ = super.operandNode.executeInt(frameValue);
         } catch (UnexpectedResultException var12) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(var12.getResult()));
         }

         if ((state_0 & 1) != 0) {
            assert this.truncate;

            assert this.isInt;

            return this.doIntTruncate(operandNodeValue_);
         } else if ((state_0 & 2) != 0) {
            assert !this.truncate;

            assert this.isInt;

            try {
               return this.doInt(operandNodeValue_);
            } catch (ArithmeticException var11) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 1;
                  this.state_0_ &= -3;
               } finally {
                  lock.unlock();
               }

               return JSTypesGen.expectInteger(this.executeAndSpecialize(operandNodeValue_));
            }
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(operandNodeValue_));
         }
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 252) == 0 && (state_0 & 0xFF) != 0) {
            this.executeInt(frameValue);
         } else if ((state_0 & 239) == 0 && (state_0 & 0xFF) != 0) {
            this.executeDouble(frameValue);
         } else {
            this.execute(frameValue);
         }
      } catch (UnexpectedResultException var4) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }
   }

   private Object executeAndSpecialize(Object operandNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            if (this.truncate && this.isInt) {
               int var56;
               this.state_0_ = var56 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doIntTruncate(operandNodeValue_);
            }

            if ((exclude & 1) == 0 && !this.truncate && this.isInt) {
               int var55;
               this.state_0_ = var55 = state_0 | 2;

               try {
                  lock.unlock();
                  hasLock = false;
                  return this.doInt(operandNodeValue_);
               } catch (ArithmeticException var44) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  lock.lock();

                  try {
                     this.exclude_ |= 1;
                     this.state_0_ &= -3;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(operandNodeValue_);
               }
            }

            if ((exclude & 2) == 0 && !this.truncate && this.isSafeLong) {
               int var54;
               this.state_0_ = var54 = state_0 | 4;

               try {
                  lock.unlock();
                  hasLock = false;
                  return this.doIntOverflow(operandNodeValue_);
               } catch (ArithmeticException var45) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  lock.lock();

                  try {
                     this.exclude_ |= 2;
                     this.state_0_ &= -5;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(operandNodeValue_);
               }
            }
         }

         if ((exclude & 4) == 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_x = (SafeInteger)operandNodeValue;
            if (this.isInt) {
               int var53;
               this.state_0_ = var53 = state_0 | 8;

               try {
                  lock.unlock();
                  hasLock = false;
                  return this.doSafeInteger(operandNodeValue_x);
               } catch (ArithmeticException var43) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  lock.lock();

                  try {
                     this.exclude_ |= 4;
                     this.state_0_ &= -9;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(operandNodeValue_x);
               }
            }
         }

         int doubleCast0;
         if ((exclude & 8) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
            double operandNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
            state_0 |= doubleCast0 << 8;
            int var52;
            this.state_0_ = var52 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.doDouble(operandNodeValue_x);
         } else if ((exclude & 16) == 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_x = (TruffleString)operandNodeValue;
            this.numberString_leftString_ = this.leftValueToString();
            this.numberString_createLazyString_ = super.insert(JSConcatStringsNode.create());
            int var47;
            this.state_0_ = var47 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doNumberString(operandNodeValue_x, this.numberString_leftString_, this.numberString_createLazyString_);
         } else if (operandNodeValue instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue_x = (JSOverloadedOperatorsObject)operandNodeValue;
            this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createHintDefault(this.getOverloadedOperatorName()));
            int var48;
            this.state_0_ = var48 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.doOverloaded(operandNodeValue_x, this.overloaded_overloadedOperatorNode_);
         } else if (this.hasOverloadedOperators(operandNodeValue)) {
            throw new UnsupportedSpecializationException(this, new Node[]{super.operandNode}, operandNodeValue);
         } else {
            JSAddConstantLeftNumberNodeGen.PrimitiveConversionData s7_ = super.insert(new JSAddConstantLeftNumberNodeGen.PrimitiveConversionData());
            s7_.toPrimitiveB_ = s7_.insertAccessor(JSToPrimitiveNode.createHintDefault());
            s7_.toNumberB_ = s7_.insertAccessor(JSToNumberNode.create());
            s7_.leftString_ = this.leftValueToString();
            s7_.createLazyString_ = s7_.insertAccessor(JSConcatStringsNode.create());
            s7_.profileB_ = ConditionProfile.createBinaryProfile();
            VarHandle.storeStoreFence();
            this.primitiveConversion_cache = s7_;
            int var57;
            this.exclude_ = var57 = exclude | 25;
            state_0 &= -51;
            int var50;
            this.state_0_ = var50 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return this.doPrimitiveConversion(operandNodeValue, s7_.toPrimitiveB_, s7_.toNumberB_, s7_.leftString_, s7_.createLazyString_, s7_.profileB_);
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
      if ((state_0 & 0xFF) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 0xFF & (state_0 & 0xFF) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[9];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doIntTruncate", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doInt", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doIntOverflow", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doNumberString", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.numberString_leftString_, this.numberString_createLazyString_));
         s[2] = cached;
      } else if ((exclude & 16) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doOverloaded", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doPrimitiveConversion", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSAddConstantLeftNumberNodeGen.PrimitiveConversionData s7_ = this.primitiveConversion_cache;
         if (s7_ != null) {
            cached.add(Arrays.asList(s7_.toPrimitiveB_, s7_.toNumberB_, s7_.leftString_, s7_.createLazyString_, s7_.profileB_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      return Introspection.Provider.create(data);
   }

   public static JSAddConstantLeftNumberNode create(Number leftValue, JavaScriptNode right, boolean truncate) {
      return new JSAddConstantLeftNumberNodeGen(leftValue, right, truncate);
   }

   @GeneratedBy(JSAddConstantLeftNumberNode.class)
   private static final class PrimitiveConversionData extends Node {
      @Node.Child
      JSToPrimitiveNode toPrimitiveB_;
      @Node.Child
      JSToNumberNode toNumberB_;
      @CompilerDirectives.CompilationFinal
      TruffleString leftString_;
      @Node.Child
      JSConcatStringsNode createLazyString_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile profileB_;

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
