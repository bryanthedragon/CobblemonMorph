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
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSBitwiseXorConstantNode.class)
public final class JSBitwiseXorConstantNodeGen extends JSBitwiseXorConstantNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSToInt32Node double_leftInt32_;
   @Node.Child
   private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSBitwiseXorConstantNodeGen.GenericData generic_cache;
   @Node.Child
   private JSToNumericNode genericBigIntCase_toNumeric_;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile genericBigIntCase_profileIsBigInt_;

   private JSBitwiseXorConstantNodeGen(JavaScriptNode left, Object rightValue) {
      super(left, rightValue);
   }

   @Override
   public Object executeObject(Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;

         assert this.isInt;

         return this.doInteger(operandNodeValue_);
      } else if ((state_0 & 2) != 0 && operandNodeValue instanceof SafeInteger) {
         SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;

         assert this.isInt;

         return this.doSafeInteger(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, operandNodeValue)) {
         double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, operandNodeValue);

         assert this.isInt;

         return this.doDouble(operandNodeValue_, this.double_leftInt32_);
      } else if ((state_0 & 64) != 0 && operandNodeValue instanceof BigInt) {
         BigInt operandNodeValue_ = (BigInt)operandNodeValue;

         assert !this.isInt;

         return this.doBigInt(operandNodeValue_);
      } else if ((state_0 & 128) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
         return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
      } else {
         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0) {
               JSBitwiseXorConstantNodeGen.GenericData s8_ = this.generic_cache;
               if (s8_ != null && !this.hasOverloadedOperators(operandNodeValue)) {
                  assert this.isInt;

                  return this.doGeneric(operandNodeValue, s8_.toNumeric_, s8_.profileIsBigInt_, s8_.innerXorNode_);
               }
            }

            if ((state_0 & 512) != 0 && !this.hasOverloadedOperators(operandNodeValue)) {
               assert !this.isInt();

               return this.doGenericBigIntCase(operandNodeValue, this.genericBigIntCase_toNumeric_, this.genericBigIntCase_profileIsBigInt_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;

         assert this.isInt;

         return this.doInteger(operandNodeValue_);
      } else if ((state_0 & 2) != 0 && operandNodeValue instanceof SafeInteger) {
         SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;

         assert this.isInt;

         return this.doSafeInteger(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, operandNodeValue)) {
         double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, operandNodeValue);

         assert this.isInt;

         return this.doDouble(operandNodeValue_, this.double_leftInt32_);
      } else if ((state_0 & 64) != 0 && operandNodeValue instanceof BigInt) {
         BigInt operandNodeValue_ = (BigInt)operandNodeValue;

         assert !this.isInt;

         return this.doBigInt(operandNodeValue_);
      } else if ((state_0 & 128) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
         return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
      } else {
         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0) {
               JSBitwiseXorConstantNodeGen.GenericData s8_ = this.generic_cache;
               if (s8_ != null && !this.hasOverloadedOperators(operandNodeValue)) {
                  assert this.isInt;

                  return this.doGeneric(operandNodeValue, s8_.toNumeric_, s8_.profileIsBigInt_, s8_.innerXorNode_);
               }
            }

            if ((state_0 & 512) != 0 && !this.hasOverloadedOperators(operandNodeValue)) {
               assert !this.isInt();

               return this.doGenericBigIntCase(operandNodeValue, this.genericBigIntCase_toNumeric_, this.genericBigIntCase_profileIsBigInt_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 966) == 0 && (state_0 & 967) != 0) {
         return this.execute_int0(state_0, frameValue);
      } else {
         return (state_0 & 963) == 0 && (state_0 & 967) != 0 ? this.execute_double1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
      }
   }

   private Object execute_int0(int state_0, VirtualFrame frameValue) {
      int operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeInt(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 1) != 0;

      assert this.isInt;

      return this.doInteger(operandNodeValue_);
   }

   private Object execute_double1(int state_0, VirtualFrame frameValue) {
      long operandNodeValue_long = 0L;
      int operandNodeValue_int = 0;

      double operandNodeValue_;
      try {
         if ((state_0 & 14336) == 0 && (state_0 & 1023) != 0) {
            operandNodeValue_ = super.operandNode.executeDouble(frameValue);
         } else if ((state_0 & 13312) == 0 && (state_0 & 1023) != 0) {
            operandNodeValue_int = super.operandNode.executeInt(frameValue);
            operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
         } else if ((state_0 & 7168) == 0 && (state_0 & 1023) != 0) {
            operandNodeValue_long = super.operandNode.executeLong(frameValue);
            operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
         } else {
            Object operandNodeValue__ = super.operandNode.execute(frameValue);
            operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 15360) >>> 10, operandNodeValue__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var9.getResult());
      }

      assert (state_0 & 4) != 0;

      assert this.isInt;

      return this.doDouble(operandNodeValue_, this.double_leftInt32_);
   }

   private Object execute_generic2(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
         int operandNodeValue__ = (Integer)operandNodeValue_;

         assert this.isInt;

         return this.doInteger(operandNodeValue__);
      } else if ((state_0 & 2) != 0 && operandNodeValue_ instanceof SafeInteger) {
         SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;

         assert this.isInt;

         return this.doSafeInteger(operandNodeValue__);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, operandNodeValue_)) {
         double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, operandNodeValue_);

         assert this.isInt;

         return this.doDouble(operandNodeValue__, this.double_leftInt32_);
      } else if ((state_0 & 64) != 0 && operandNodeValue_ instanceof BigInt) {
         BigInt operandNodeValue__ = (BigInt)operandNodeValue_;

         assert !this.isInt;

         return this.doBigInt(operandNodeValue__);
      } else if ((state_0 & 128) != 0 && operandNodeValue_ instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue__ = (JSOverloadedOperatorsObject)operandNodeValue_;
         return this.doOverloaded(operandNodeValue__, this.overloaded_overloadedOperatorNode_);
      } else {
         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0) {
               JSBitwiseXorConstantNodeGen.GenericData s8_ = this.generic_cache;
               if (s8_ != null && !this.hasOverloadedOperators(operandNodeValue_)) {
                  assert this.isInt;

                  return this.doGeneric(operandNodeValue_, s8_.toNumeric_, s8_.profileIsBigInt_, s8_.innerXorNode_);
               }
            }

            if ((state_0 & 512) != 0 && !this.hasOverloadedOperators(operandNodeValue_)) {
               assert !this.isInt();

               return this.doGenericBigIntCase(operandNodeValue_, this.genericBigIntCase_toNumeric_, this.genericBigIntCase_profileIsBigInt_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 384) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else if ((state_0 & 6) == 0 && (state_0 & 7) != 0) {
         return this.executeInt_int3(state_0, frameValue);
      } else {
         return (state_0 & 3) == 0 && (state_0 & 7) != 0 ? this.executeInt_double4(state_0, frameValue) : this.executeInt_generic5(state_0, frameValue);
      }
   }

   private int executeInt_int3(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      int operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeInt(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(var5.getResult()));
      }

      assert (state_0 & 1) != 0;

      assert this.isInt;

      return this.doInteger(operandNodeValue_);
   }

   private int executeInt_double4(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      long operandNodeValue_long = 0L;
      int operandNodeValue_int = 0;

      double operandNodeValue_;
      try {
         if ((state_0 & 14336) == 0 && (state_0 & 1023) != 0) {
            operandNodeValue_ = super.operandNode.executeDouble(frameValue);
         } else if ((state_0 & 13312) == 0 && (state_0 & 1023) != 0) {
            operandNodeValue_int = super.operandNode.executeInt(frameValue);
            operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
         } else if ((state_0 & 7168) == 0 && (state_0 & 1023) != 0) {
            operandNodeValue_long = super.operandNode.executeLong(frameValue);
            operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
         } else {
            Object operandNodeValue__ = super.operandNode.execute(frameValue);
            operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 15360) >>> 10, operandNodeValue__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(var9.getResult()));
      }

      assert (state_0 & 4) != 0;

      assert this.isInt;

      return this.doDouble(operandNodeValue_, this.double_leftInt32_);
   }

   private int executeInt_generic5(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
         int operandNodeValue__ = (Integer)operandNodeValue_;

         assert this.isInt;

         return this.doInteger(operandNodeValue__);
      } else if ((state_0 & 2) != 0 && operandNodeValue_ instanceof SafeInteger) {
         SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;

         assert this.isInt;

         return this.doSafeInteger(operandNodeValue__);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, operandNodeValue_)) {
         double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, operandNodeValue_);

         assert this.isInt;

         return this.doDouble(operandNodeValue__, this.double_leftInt32_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(operandNodeValue_));
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 1016) == 0 && (state_0 & 1023) != 0) {
            this.executeInt(frameValue);
            return;
         }

         if ((state_0 & 967) != 0) {
            this.execute(frameValue);
            return;
         }
      } catch (UnexpectedResultException var4) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return;
      }

      if ((state_0 & 48) == 0 && (state_0 & 56) != 0) {
         this.executeVoid_int6(state_0, frameValue);
      } else if ((state_0 & 40) == 0 && (state_0 & 56) != 0) {
         this.executeVoid_double7(state_0, frameValue);
      } else {
         this.executeVoid_generic8(state_0, frameValue);
      }
   }

   private void executeVoid_int6(int state_0, VirtualFrame frameValue) {
      int operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeInt(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(var5.getResult());
         return;
      }

      assert (state_0 & 8) != 0;

      assert !this.isInt;

      this.doIntegerThrows(operandNodeValue_);
   }

   private void executeVoid_double7(int state_0, VirtualFrame frameValue) {
      long operandNodeValue_long = 0L;
      int operandNodeValue_int = 0;

      double operandNodeValue_;
      try {
         if ((state_0 & 14336) == 0 && (state_0 & 1023) != 0) {
            operandNodeValue_ = super.operandNode.executeDouble(frameValue);
         } else if ((state_0 & 13312) == 0 && (state_0 & 1023) != 0) {
            operandNodeValue_int = super.operandNode.executeInt(frameValue);
            operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
         } else if ((state_0 & 7168) == 0 && (state_0 & 1023) != 0) {
            operandNodeValue_long = super.operandNode.executeLong(frameValue);
            operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
         } else {
            Object operandNodeValue__ = super.operandNode.execute(frameValue);
            operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 15360) >>> 10, operandNodeValue__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(var9.getResult());
         return;
      }

      assert (state_0 & 16) != 0;

      assert !this.isInt;

      this.doDoubleThrows(operandNodeValue_);
   }

   private void executeVoid_generic8(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 8) != 0 && operandNodeValue_ instanceof Integer) {
         int operandNodeValue__ = (Integer)operandNodeValue_;

         assert !this.isInt;

         this.doIntegerThrows(operandNodeValue__);
      } else if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, operandNodeValue_)) {
         double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, operandNodeValue_);

         assert !this.isInt;

         this.doDoubleThrows(operandNodeValue__);
      } else if ((state_0 & 32) != 0 && operandNodeValue_ instanceof BigInt) {
         BigInt operandNodeValue__ = (BigInt)operandNodeValue_;

         assert this.isInt;

         this.doBigIntThrows(operandNodeValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(operandNodeValue_);
      }
   }

   private Object executeAndSpecialize(Object operandNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if ((exclude & 1) == 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            if (this.isInt) {
               int var26;
               this.state_0_ = var26 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doInteger(operandNodeValue_);
            }
         }

         if ((exclude & 2) == 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
            if (this.isInt) {
               int var25;
               this.state_0_ = var25 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doSafeInteger(operandNodeValue_);
            }
         }

         int doubleCast0;
         if ((exclude & 4) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
            if (this.isInt) {
               this.double_leftInt32_ = super.insert(JSToInt32Node.create());
               state_0 |= doubleCast0 << 10;
               int var24;
               this.state_0_ = var24 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doDouble(operandNodeValue_, this.double_leftInt32_);
            }
         }

         if ((exclude & 8) == 0 && operandNodeValue instanceof Integer) {
            doubleCast0 = (Integer)operandNodeValue;
            if (!this.isInt) {
               int var22;
               this.state_0_ = var22 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               this.doIntegerThrows(doubleCast0);
               return null;
            }
         }

         if ((exclude & 16) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
            if (!this.isInt) {
               state_0 |= doubleCast0 << 10;
               int var21;
               this.state_0_ = var21 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               this.doDoubleThrows(operandNodeValue_);
               return null;
            }
         }

         if (operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            if ((exclude & 32) == 0 && this.isInt) {
               int var19;
               this.state_0_ = var19 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               this.doBigIntThrows(operandNodeValue_);
               return null;
            }

            if ((exclude & 64) == 0 && !this.isInt) {
               int var18;
               this.state_0_ = var18 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(operandNodeValue_);
            }
         }

         if (operandNodeValue instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue_x = (JSOverloadedOperatorsObject)operandNodeValue;
            this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
            int var17;
            this.state_0_ = var17 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return this.doOverloaded(operandNodeValue_x, this.overloaded_overloadedOperatorNode_);
         } else if (!this.hasOverloadedOperators(operandNodeValue) && this.isInt) {
            JSBitwiseXorConstantNodeGen.GenericData s8_ = super.insert(new JSBitwiseXorConstantNodeGen.GenericData());
            s8_.toNumeric_ = s8_.insertAccessor(JSToNumericNode.create());
            s8_.profileIsBigInt_ = ConditionProfile.createBinaryProfile();
            s8_.innerXorNode_ = s8_.insertAccessor(this.makeCopy());
            VarHandle.storeStoreFence();
            this.generic_cache = s8_;
            int var27;
            this.exclude_ = var27 = exclude | 39;
            state_0 &= -40;
            int var14;
            this.state_0_ = var14 = state_0 | 256;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(operandNodeValue, s8_.toNumeric_, s8_.profileIsBigInt_, s8_.innerXorNode_);
         } else if (this.hasOverloadedOperators(operandNodeValue) || this.isInt()) {
            throw new UnsupportedSpecializationException(this, new Node[]{super.operandNode}, operandNodeValue);
         } else {
            this.genericBigIntCase_toNumeric_ = super.insert(JSToNumericNode.create());
            this.genericBigIntCase_profileIsBigInt_ = ConditionProfile.createBinaryProfile();
            int var28;
            this.exclude_ = var28 = exclude | 88;
            state_0 &= -89;
            int var16;
            this.state_0_ = var16 = state_0 | 512;
            lock.unlock();
            hasLock = false;
            return this.doGenericBigIntCase(operandNodeValue, this.genericBigIntCase_toNumeric_, this.genericBigIntCase_profileIsBigInt_);
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
      if ((state_0 & 1023) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 1023 & (state_0 & 1023) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[11];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doInteger", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.double_leftInt32_));
         s[2] = cached;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doIntegerThrows", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doDoubleThrows", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 16) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doBigIntThrows", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 32) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 64) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doOverloaded", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doGeneric", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSBitwiseXorConstantNodeGen.GenericData s8_ = this.generic_cache;
         if (s8_ != null) {
            cached.add(Arrays.asList(s8_.toNumeric_, s8_.profileIsBigInt_, s8_.innerXorNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doGenericBigIntCase", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.genericBigIntCase_toNumeric_, this.genericBigIntCase_profileIsBigInt_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      return Introspection.Provider.create(data);
   }

   public static JSBitwiseXorConstantNode create(JavaScriptNode left, Object rightValue) {
      return new JSBitwiseXorConstantNodeGen(left, rightValue);
   }

   @GeneratedBy(JSBitwiseXorConstantNode.class)
   private static final class GenericData extends Node {
      @Node.Child
      JSToNumericNode toNumeric_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile profileIsBigInt_;
      @Node.Child
      JavaScriptNode innerXorNode_;

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
