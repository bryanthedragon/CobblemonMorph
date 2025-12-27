package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToInt32Node.class)
public final class JSToInt32NodeGen extends JSToInt32Node implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSStringToNumberNode string_stringToNumberNode_;
   @Node.Child
   private JSOverloadedBinaryNode overloadedOperator_overloadedOperatorNode_;
   @Node.Child
   private JSToDoubleNode jSObject_toDoubleNode_;
   @Node.Child
   private JSToPrimitiveNode foreignObject_toPrimitiveNode_;
   @Node.Child
   private JSToInt32Node foreignObject_toInt32Node_;

   private JSToInt32NodeGen(JavaScriptNode operand, boolean bitwiseOr) {
      super(operand, bitwiseOr);
   }

   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;
         return this.doInteger(operandNodeValue_);
      } else if ((state_0 & 2) != 0 && operandNodeValue instanceof SafeInteger) {
         SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
         return this.doSafeInteger(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && operandNodeValue instanceof Boolean) {
         boolean operandNodeValue_ = (Boolean)operandNodeValue;
         return this.doBoolean(operandNodeValue_);
      } else {
         if ((state_0 & 8) != 0 && operandNodeValue instanceof Long) {
            long operandNodeValue_ = (Long)operandNodeValue;
            if (JSGuards.isLongRepresentableAsInt32(operandNodeValue_)) {
               return this.doLong(operandNodeValue_);
            }
         }

         if ((state_0 & 240) != 0 && JSTypesGen.isImplicitDouble((state_0 & 983040) >>> 16, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 983040) >>> 16, operandNodeValue);
            if ((state_0 & 16) != 0 && !JSGuards.isDoubleLargerThan2e32(operandNodeValue_)) {
               return this.doDoubleFitsInt(operandNodeValue_);
            }

            if ((state_0 & 32) != 0
               && JSGuards.isDoubleLargerThan2e32(operandNodeValue_)
               && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)
               && JSGuards.isDoubleSafeInteger(operandNodeValue_)) {
               return this.doDoubleRepresentableAsSafeInteger(operandNodeValue_);
            }

            if ((state_0 & 64) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)) {
               return this.doDoubleRepresentableAsLong(operandNodeValue_);
            }

            if ((state_0 & 128) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_) && !JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)) {
               return this.doDouble(operandNodeValue_);
            }
         }

         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0 && JSGuards.isUndefined(operandNodeValue)) {
               return this.doUndefined(operandNodeValue);
            }

            if ((state_0 & 512) != 0 && JSGuards.isJSNull(operandNodeValue)) {
               return this.doNull(operandNodeValue);
            }
         }

         if ((state_0 & 1024) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_x = (TruffleString)operandNodeValue;
            return this.doString(operandNodeValue_x, this.string_stringToNumberNode_);
         } else if ((state_0 & 2048) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_x = (Symbol)operandNodeValue;
            return this.doSymbol(operandNodeValue_x);
         } else if ((state_0 & 4096) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_x = (BigInt)operandNodeValue;
            return this.doBigInt(operandNodeValue_x);
         } else if ((state_0 & 8192) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue_x = (JSOverloadedOperatorsObject)operandNodeValue;

            assert this.isBitwiseOr();

            return this.doOverloadedOperator(operandNodeValue_x, this.overloadedOperator_overloadedOperatorNode_);
         } else {
            if ((state_0 & 16384) != 0 && operandNodeValue instanceof JSObject) {
               JSObject operandNodeValue_x = (JSObject)operandNodeValue;
               if (!this.isBitwiseOr() || !this.hasOverloadedOperators(operandNodeValue_x)) {
                  return this.doJSObject(operandNodeValue_x, this.jSObject_toDoubleNode_);
               }
            }

            if ((state_0 & 32768) != 0 && JSGuards.isForeignObject(operandNodeValue)) {
               return JSToInt32Node.doForeignObject(operandNodeValue, this.foreignObject_toPrimitiveNode_, this.foreignObject_toInt32Node_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(operandNodeValue);
            }
         }
      }
   }

   @Override
   public int executeInt(Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 8192) != 0) {
         return (Integer)this.execute(null, operandNodeValue);
      } else if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;
         return this.doInteger(operandNodeValue_);
      } else if ((state_0 & 2) != 0 && operandNodeValue instanceof SafeInteger) {
         SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
         return this.doSafeInteger(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && operandNodeValue instanceof Boolean) {
         boolean operandNodeValue_ = (Boolean)operandNodeValue;
         return this.doBoolean(operandNodeValue_);
      } else {
         if ((state_0 & 8) != 0 && operandNodeValue instanceof Long) {
            long operandNodeValue_ = (Long)operandNodeValue;
            if (JSGuards.isLongRepresentableAsInt32(operandNodeValue_)) {
               return this.doLong(operandNodeValue_);
            }
         }

         if ((state_0 & 240) != 0 && JSTypesGen.isImplicitDouble((state_0 & 983040) >>> 16, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 983040) >>> 16, operandNodeValue);
            if ((state_0 & 16) != 0 && !JSGuards.isDoubleLargerThan2e32(operandNodeValue_)) {
               return this.doDoubleFitsInt(operandNodeValue_);
            }

            if ((state_0 & 32) != 0
               && JSGuards.isDoubleLargerThan2e32(operandNodeValue_)
               && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)
               && JSGuards.isDoubleSafeInteger(operandNodeValue_)) {
               return this.doDoubleRepresentableAsSafeInteger(operandNodeValue_);
            }

            if ((state_0 & 64) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)) {
               return this.doDoubleRepresentableAsLong(operandNodeValue_);
            }

            if ((state_0 & 128) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_) && !JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)) {
               return this.doDouble(operandNodeValue_);
            }
         }

         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0 && JSGuards.isUndefined(operandNodeValue)) {
               return this.doUndefined(operandNodeValue);
            }

            if ((state_0 & 512) != 0 && JSGuards.isJSNull(operandNodeValue)) {
               return this.doNull(operandNodeValue);
            }
         }

         if ((state_0 & 1024) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_x = (TruffleString)operandNodeValue;
            return this.doString(operandNodeValue_x, this.string_stringToNumberNode_);
         } else if ((state_0 & 2048) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_x = (Symbol)operandNodeValue;
            return this.doSymbol(operandNodeValue_x);
         } else if ((state_0 & 4096) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_x = (BigInt)operandNodeValue;
            return this.doBigInt(operandNodeValue_x);
         } else {
            if ((state_0 & 16384) != 0 && operandNodeValue instanceof JSObject) {
               JSObject operandNodeValue_x = (JSObject)operandNodeValue;
               if (!this.isBitwiseOr() || !this.hasOverloadedOperators(operandNodeValue_x)) {
                  return this.doJSObject(operandNodeValue_x, this.jSObject_toDoubleNode_);
               }
            }

            if ((state_0 & 32768) != 0 && JSGuards.isForeignObject(operandNodeValue)) {
               return JSToInt32Node.doForeignObject(operandNodeValue, this.foreignObject_toPrimitiveNode_, this.foreignObject_toInt32Node_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return (Integer)this.executeAndSpecialize(operandNodeValue);
            }
         }
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 57342) == 0 && (state_0 & 57343) != 0) {
         return this.executeInt_int0(state_0, frameValue);
      } else if ((state_0 & 57339) == 0 && (state_0 & 57343) != 0) {
         return this.executeInt_boolean1(state_0, frameValue);
      } else if ((state_0 & 57335) == 0 && (state_0 & 57343) != 0) {
         return this.executeInt_long2(state_0, frameValue);
      } else {
         return (state_0 & 57103) == 0 && (state_0 & 57343) != 0 ? this.executeInt_double3(state_0, frameValue) : this.executeInt_generic4(state_0, frameValue);
      }
   }

   private int executeInt_int0(int state_0, VirtualFrame frameValue) {
      int operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeInt(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return (Integer)this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 1) != 0;

      return this.doInteger(operandNodeValue_);
   }

   private int executeInt_boolean1(int state_0, VirtualFrame frameValue) {
      boolean operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return (Integer)this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 4) != 0;

      return this.doBoolean(operandNodeValue_);
   }

   private int executeInt_long2(int state_0, VirtualFrame frameValue) {
      long operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeLong(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return (Integer)this.executeAndSpecialize(var6.getResult());
      }

      assert (state_0 & 8) != 0;

      if (JSGuards.isLongRepresentableAsInt32(operandNodeValue_)) {
         return this.doLong(operandNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return (Integer)this.executeAndSpecialize(operandNodeValue_);
      }
   }

   private int executeInt_double3(int state_0, VirtualFrame frameValue) {
      long operandNodeValue_long = 0L;
      int operandNodeValue_int = 0;

      double operandNodeValue_;
      try {
         if ((state_0 & 917504) == 0 && (state_0 & 65535) != 0) {
            operandNodeValue_ = super.operandNode.executeDouble(frameValue);
         } else if ((state_0 & 851968) == 0 && (state_0 & 65535) != 0) {
            operandNodeValue_int = super.operandNode.executeInt(frameValue);
            operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
         } else if ((state_0 & 458752) == 0 && (state_0 & 65535) != 0) {
            operandNodeValue_long = super.operandNode.executeLong(frameValue);
            operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
         } else {
            Object operandNodeValue__ = super.operandNode.execute(frameValue);
            operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 983040) >>> 16, operandNodeValue__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return (Integer)this.executeAndSpecialize(var9.getResult());
      }

      if ((state_0 & 16) != 0 && !JSGuards.isDoubleLargerThan2e32(operandNodeValue_)) {
         return this.doDoubleFitsInt(operandNodeValue_);
      } else if ((state_0 & 32) != 0
         && JSGuards.isDoubleLargerThan2e32(operandNodeValue_)
         && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)
         && JSGuards.isDoubleSafeInteger(operandNodeValue_)) {
         return this.doDoubleRepresentableAsSafeInteger(operandNodeValue_);
      } else if ((state_0 & 64) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)) {
         return this.doDoubleRepresentableAsLong(operandNodeValue_);
      } else if ((state_0 & 128) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue_) && !JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)) {
         return this.doDouble(operandNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return (Integer)this.executeAndSpecialize(
            (state_0 & 851968) == 0 && (state_0 & 65535) != 0
               ? operandNodeValue_int
               : ((state_0 & 458752) == 0 && (state_0 & 65535) != 0 ? operandNodeValue_long : operandNodeValue_)
         );
      }
   }

   private int executeInt_generic4(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
         int operandNodeValue__ = (Integer)operandNodeValue_;
         return this.doInteger(operandNodeValue__);
      } else if ((state_0 & 2) != 0 && operandNodeValue_ instanceof SafeInteger) {
         SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
         return this.doSafeInteger(operandNodeValue__);
      } else if ((state_0 & 4) != 0 && operandNodeValue_ instanceof Boolean) {
         boolean operandNodeValue__ = (Boolean)operandNodeValue_;
         return this.doBoolean(operandNodeValue__);
      } else {
         if ((state_0 & 8) != 0 && operandNodeValue_ instanceof Long) {
            long operandNodeValue__ = (Long)operandNodeValue_;
            if (JSGuards.isLongRepresentableAsInt32(operandNodeValue__)) {
               return this.doLong(operandNodeValue__);
            }
         }

         if ((state_0 & 240) != 0 && JSTypesGen.isImplicitDouble((state_0 & 983040) >>> 16, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 983040) >>> 16, operandNodeValue_);
            if ((state_0 & 16) != 0 && !JSGuards.isDoubleLargerThan2e32(operandNodeValue__)) {
               return this.doDoubleFitsInt(operandNodeValue__);
            }

            if ((state_0 & 32) != 0
               && JSGuards.isDoubleLargerThan2e32(operandNodeValue__)
               && JSGuards.isDoubleRepresentableAsLong(operandNodeValue__)
               && JSGuards.isDoubleSafeInteger(operandNodeValue__)) {
               return this.doDoubleRepresentableAsSafeInteger(operandNodeValue__);
            }

            if ((state_0 & 64) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue__) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue__)) {
               return this.doDoubleRepresentableAsLong(operandNodeValue__);
            }

            if ((state_0 & 128) != 0 && JSGuards.isDoubleLargerThan2e32(operandNodeValue__) && !JSGuards.isDoubleRepresentableAsLong(operandNodeValue__)) {
               return this.doDouble(operandNodeValue__);
            }
         }

         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
               return this.doUndefined(operandNodeValue_);
            }

            if ((state_0 & 512) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
               return this.doNull(operandNodeValue_);
            }
         }

         if ((state_0 & 1024) != 0 && operandNodeValue_ instanceof TruffleString) {
            TruffleString operandNodeValue__x = (TruffleString)operandNodeValue_;
            return this.doString(operandNodeValue__x, this.string_stringToNumberNode_);
         } else if ((state_0 & 2048) != 0 && operandNodeValue_ instanceof Symbol) {
            Symbol operandNodeValue__x = (Symbol)operandNodeValue_;
            return this.doSymbol(operandNodeValue__x);
         } else if ((state_0 & 4096) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__x = (BigInt)operandNodeValue_;
            return this.doBigInt(operandNodeValue__x);
         } else {
            if ((state_0 & 16384) != 0 && operandNodeValue_ instanceof JSObject) {
               JSObject operandNodeValue__x = (JSObject)operandNodeValue_;
               if (!this.isBitwiseOr() || !this.hasOverloadedOperators(operandNodeValue__x)) {
                  return this.doJSObject(operandNodeValue__x, this.jSObject_toDoubleNode_);
               }
            }

            if ((state_0 & 32768) != 0 && JSGuards.isForeignObject(operandNodeValue_)) {
               return JSToInt32Node.doForeignObject(operandNodeValue_, this.foreignObject_toPrimitiveNode_, this.foreignObject_toInt32Node_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return (Integer)this.executeAndSpecialize(operandNodeValue_);
            }
         }
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 8192) == 0 && (state_0 & 65535) != 0) {
         this.executeInt(frameValue);
      } else {
         this.executeInt(frameValue);
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
            int var33;
            this.state_0_ = var33 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doInteger(operandNodeValue_);
         } else if (operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
            int var32;
            this.state_0_ = var32 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doSafeInteger(operandNodeValue_);
         } else if (operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_ = (Boolean)operandNodeValue;
            int var31;
            this.state_0_ = var31 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doBoolean(operandNodeValue_);
         } else {
            if (operandNodeValue instanceof Long) {
               long operandNodeValue_ = (Long)operandNodeValue;
               if (JSGuards.isLongRepresentableAsInt32(operandNodeValue_)) {
                  int var30;
                  this.state_0_ = var30 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.doLong(operandNodeValue_);
               }
            }

            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
               double operandNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
               if (!JSGuards.isDoubleLargerThan2e32(operandNodeValue_)) {
                  state_0 |= doubleCast0 << 16;
                  int var29;
                  this.state_0_ = var29 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.doDoubleFitsInt(operandNodeValue_);
               }

               if (exclude == 0
                  && JSGuards.isDoubleLargerThan2e32(operandNodeValue_)
                  && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)
                  && JSGuards.isDoubleSafeInteger(operandNodeValue_)) {
                  state_0 |= doubleCast0 << 16;
                  int var27;
                  this.state_0_ = var27 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return this.doDoubleRepresentableAsSafeInteger(operandNodeValue_);
               }

               if (JSGuards.isDoubleLargerThan2e32(operandNodeValue_) && JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)) {
                  int var34;
                  this.exclude_ = var34 = exclude | 1;
                  state_0 &= -33;
                  state_0 |= doubleCast0 << 16;
                  int var25;
                  this.state_0_ = var25 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return this.doDoubleRepresentableAsLong(operandNodeValue_);
               }

               if (JSGuards.isDoubleLargerThan2e32(operandNodeValue_) && !JSGuards.isDoubleRepresentableAsLong(operandNodeValue_)) {
                  state_0 |= doubleCast0 << 16;
                  int var22;
                  this.state_0_ = var22 = state_0 | 128;
                  lock.unlock();
                  hasLock = false;
                  return this.doDouble(operandNodeValue_);
               }
            }

            if (JSGuards.isUndefined(operandNodeValue)) {
               int var20;
               this.state_0_ = var20 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return this.doUndefined(operandNodeValue);
            } else if (JSGuards.isJSNull(operandNodeValue)) {
               int var19;
               this.state_0_ = var19 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return this.doNull(operandNodeValue);
            } else if (operandNodeValue instanceof TruffleString) {
               TruffleString operandNodeValue_x = (TruffleString)operandNodeValue;
               this.string_stringToNumberNode_ = super.insert(JSStringToNumberNode.create());
               int var18;
               this.state_0_ = var18 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               return this.doString(operandNodeValue_x, this.string_stringToNumberNode_);
            } else if (operandNodeValue instanceof Symbol) {
               Symbol operandNodeValue_x = (Symbol)operandNodeValue;
               int var17;
               this.state_0_ = var17 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               return this.doSymbol(operandNodeValue_x);
            } else if (operandNodeValue instanceof BigInt) {
               BigInt operandNodeValue_x = (BigInt)operandNodeValue;
               int var16;
               this.state_0_ = var16 = state_0 | 4096;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(operandNodeValue_x);
            } else {
               if (operandNodeValue instanceof JSOverloadedOperatorsObject) {
                  JSOverloadedOperatorsObject operandNodeValue_x = (JSOverloadedOperatorsObject)operandNodeValue;
                  if (this.isBitwiseOr()) {
                     this.overloadedOperator_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
                     int var15;
                     this.state_0_ = var15 = state_0 | 8192;
                     lock.unlock();
                     hasLock = false;
                     return this.doOverloadedOperator(operandNodeValue_x, this.overloadedOperator_overloadedOperatorNode_);
                  }
               }

               if (operandNodeValue instanceof JSObject) {
                  JSObject operandNodeValue_x = (JSObject)operandNodeValue;
                  if (!this.isBitwiseOr() || !this.hasOverloadedOperators(operandNodeValue_x)) {
                     this.jSObject_toDoubleNode_ = super.insert(JSToDoubleNode.create());
                     int var14;
                     this.state_0_ = var14 = state_0 | 16384;
                     lock.unlock();
                     hasLock = false;
                     return this.doJSObject(operandNodeValue_x, this.jSObject_toDoubleNode_);
                  }
               }

               if (!JSGuards.isForeignObject(operandNodeValue)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{super.operandNode}, operandNodeValue);
               } else {
                  this.foreignObject_toPrimitiveNode_ = super.insert(JSToPrimitiveNode.createHintNumber());
                  this.foreignObject_toInt32Node_ = super.insert(JSToInt32Node.create());
                  int var13;
                  this.state_0_ = var13 = state_0 | 32768;
                  lock.unlock();
                  hasLock = false;
                  return JSToInt32Node.doForeignObject(operandNodeValue, this.foreignObject_toPrimitiveNode_, this.foreignObject_toInt32Node_);
               }
            }
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
      if ((state_0 & 65535) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 65535 & (state_0 & 65535) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[17];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doInteger", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doLong", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doDoubleFitsInt", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doDoubleRepresentableAsSafeInteger", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doDoubleRepresentableAsLong", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doUndefined", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doNull", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.string_stringToNumberNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      s = new Object[]{"doOverloadedOperator", null, null};
      if ((state_0 & 8192) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.overloadedOperator_overloadedOperatorNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[14] = s;
      s = new Object[]{"doJSObject", null, null};
      if ((state_0 & 16384) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.jSObject_toDoubleNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[15] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 32768) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.foreignObject_toPrimitiveNode_, this.foreignObject_toInt32Node_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[16] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToInt32Node create(JavaScriptNode operand, boolean bitwiseOr) {
      return new JSToInt32NodeGen(operand, bitwiseOr);
   }
}
