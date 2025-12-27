package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
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

@GeneratedBy(JSToUInt32Node.class)
public final class JSToUInt32NodeGen extends JSToUInt32Node implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSStringToNumberNode string_stringToNumberNode_;
   @Node.Child
   private JSOverloadedBinaryNode overloadedOperator_overloadedOperatorNode_;
   @Node.Child
   private JSToNumberNode jSObject_toNumberNode_;
   @Node.Child
   private JSToPrimitiveNode foreignObject_toPrimitiveNode_;
   @Node.Child
   private JSToUInt32Node foreignObject_toUInt32Node_;

   private JSToUInt32NodeGen(boolean unsignedRightShift, int shiftValue) {
      super(unsignedRightShift, shiftValue);
   }

   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 3) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         if ((state_0 & 1) != 0 && arg0Value_ >= 0) {
            return this.doInteger(arg0Value_);
         }

         if ((state_0 & 2) != 0 && arg0Value_ < 0) {
            return this.doIntegerNegative(arg0Value_);
         }
      }

      if ((state_0 & 4) != 0 && arg0Value instanceof SafeInteger) {
         SafeInteger arg0Value_x = (SafeInteger)arg0Value;
         return this.doSafeInteger(arg0Value_x);
      } else if ((state_0 & 8) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_x = (Boolean)arg0Value;
         return this.doBoolean(arg0Value_x);
      } else if ((state_0 & 16) != 0 && arg0Value instanceof Long) {
         long arg0Value_x = (Long)arg0Value;
         return this.doLong(arg0Value_x);
      } else {
         if ((state_0 & 224) != 0 && JSTypesGen.isImplicitDouble((state_0 & 983040) >>> 16, arg0Value)) {
            double arg0Value_x = JSTypesGen.asImplicitDouble((state_0 & 983040) >>> 16, arg0Value);
            if ((state_0 & 32) != 0 && !JSGuards.isDoubleLargerThan2e32(arg0Value_x)) {
               return this.doDoubleFitsInt32Negative(arg0Value_x);
            }

            if ((state_0 & 64) != 0 && JSGuards.isDoubleLargerThan2e32(arg0Value_x) && JSGuards.isDoubleRepresentableAsLong(arg0Value_x)) {
               return this.doDoubleRepresentableAsLong(arg0Value_x);
            }

            if ((state_0 & 128) != 0 && JSGuards.isDoubleLargerThan2e32(arg0Value_x) && !JSGuards.isDoubleRepresentableAsLong(arg0Value_x)) {
               return this.doDouble(arg0Value_x);
            }
         }

         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0 && JSGuards.isJSNull(arg0Value)) {
               return this.doNull(arg0Value);
            }

            if ((state_0 & 512) != 0 && JSGuards.isUndefined(arg0Value)) {
               return this.doUndefined(arg0Value);
            }
         }

         if ((state_0 & 1024) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_xx = (TruffleString)arg0Value;
            return this.doString(arg0Value_xx, this.string_stringToNumberNode_);
         } else if ((state_0 & 2048) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_xx = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_xx);
         } else if ((state_0 & 4096) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_xx = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_xx);
         } else if ((state_0 & 8192) != 0 && arg0Value instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject arg0Value_xx = (JSOverloadedOperatorsObject)arg0Value;

            assert this.isUnsignedRightShift();

            return this.doOverloadedOperator(arg0Value_xx, this.overloadedOperator_overloadedOperatorNode_);
         } else {
            if ((state_0 & 16384) != 0 && arg0Value instanceof JSObject) {
               JSObject arg0Value_xx = (JSObject)arg0Value;
               if (!this.isUnsignedRightShift() || !this.hasOverloadedOperators(arg0Value_xx)) {
                  return this.doJSObject(arg0Value_xx, this.jSObject_toNumberNode_);
               }
            }

            if ((state_0 & 32768) != 0 && JSGuards.isForeignObject(arg0Value)) {
               return JSToUInt32Node.doForeignObject(arg0Value, this.foreignObject_toPrimitiveNode_, this.foreignObject_toUInt32Node_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arg0Value);
            }
         }
      }
   }

   private Object executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            if (arg0Value_ >= 0) {
               int var30;
               this.state_0_ = var30 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doInteger(arg0Value_);
            }

            if (arg0Value_ < 0) {
               int var29;
               this.state_0_ = var29 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doIntegerNegative(arg0Value_);
            }
         }

         if (arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_x = (SafeInteger)arg0Value;
            int var28;
            this.state_0_ = var28 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doSafeInteger(arg0Value_x);
         } else if (arg0Value instanceof Boolean) {
            boolean arg0Value_x = (Boolean)arg0Value;
            int var27;
            this.state_0_ = var27 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.doBoolean(arg0Value_x);
         } else if (arg0Value instanceof Long) {
            long arg0Value_x = (Long)arg0Value;
            int var26;
            this.state_0_ = var26 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.doLong(arg0Value_x);
         } else {
            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
               double arg0Value_x = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
               if (!JSGuards.isDoubleLargerThan2e32(arg0Value_x)) {
                  state_0 |= doubleCast0 << 16;
                  int var25;
                  this.state_0_ = var25 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return this.doDoubleFitsInt32Negative(arg0Value_x);
               }

               if (JSGuards.isDoubleLargerThan2e32(arg0Value_x) && JSGuards.isDoubleRepresentableAsLong(arg0Value_x)) {
                  state_0 |= doubleCast0 << 16;
                  int var23;
                  this.state_0_ = var23 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return this.doDoubleRepresentableAsLong(arg0Value_x);
               }

               if (JSGuards.isDoubleLargerThan2e32(arg0Value_x) && !JSGuards.isDoubleRepresentableAsLong(arg0Value_x)) {
                  state_0 |= doubleCast0 << 16;
                  int var21;
                  this.state_0_ = var21 = state_0 | 128;
                  lock.unlock();
                  hasLock = false;
                  return this.doDouble(arg0Value_x);
               }
            }

            if (JSGuards.isJSNull(arg0Value)) {
               int var19;
               this.state_0_ = var19 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return this.doNull(arg0Value);
            } else if (JSGuards.isUndefined(arg0Value)) {
               int var18;
               this.state_0_ = var18 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return this.doUndefined(arg0Value);
            } else if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_xx = (TruffleString)arg0Value;
               this.string_stringToNumberNode_ = super.insert(JSStringToNumberNode.create());
               int var17;
               this.state_0_ = var17 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               return this.doString(arg0Value_xx, this.string_stringToNumberNode_);
            } else if (arg0Value instanceof Symbol) {
               Symbol arg0Value_xx = (Symbol)arg0Value;
               int var16;
               this.state_0_ = var16 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               return this.doSymbol(arg0Value_xx);
            } else if (arg0Value instanceof BigInt) {
               BigInt arg0Value_xx = (BigInt)arg0Value;
               int var15;
               this.state_0_ = var15 = state_0 | 4096;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(arg0Value_xx);
            } else {
               if (arg0Value instanceof JSOverloadedOperatorsObject) {
                  JSOverloadedOperatorsObject arg0Value_xx = (JSOverloadedOperatorsObject)arg0Value;
                  if (this.isUnsignedRightShift()) {
                     this.overloadedOperator_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
                     int var14;
                     this.state_0_ = var14 = state_0 | 8192;
                     lock.unlock();
                     hasLock = false;
                     return this.doOverloadedOperator(arg0Value_xx, this.overloadedOperator_overloadedOperatorNode_);
                  }
               }

               if (arg0Value instanceof JSObject) {
                  JSObject arg0Value_xx = (JSObject)arg0Value;
                  if (!this.isUnsignedRightShift() || !this.hasOverloadedOperators(arg0Value_xx)) {
                     this.jSObject_toNumberNode_ = super.insert(JSToNumberNode.create());
                     int var13;
                     this.state_0_ = var13 = state_0 | 16384;
                     lock.unlock();
                     hasLock = false;
                     return this.doJSObject(arg0Value_xx, this.jSObject_toNumberNode_);
                  }
               }

               if (!JSGuards.isForeignObject(arg0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
               } else {
                  this.foreignObject_toPrimitiveNode_ = super.insert(JSToPrimitiveNode.createHintNumber());
                  this.foreignObject_toUInt32Node_ = super.insert(JSToUInt32Node.create());
                  int var12;
                  this.state_0_ = var12 = state_0 | 32768;
                  lock.unlock();
                  hasLock = false;
                  return JSToUInt32Node.doForeignObject(arg0Value, this.foreignObject_toPrimitiveNode_, this.foreignObject_toUInt32Node_);
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
      Object[] s = new Object[]{"doInteger", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doIntegerNegative", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doLong", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doDoubleFitsInt32Negative", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
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
      s = new Object[]{"doNull", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doUndefined", null, null};
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
         cached.add(Arrays.asList(this.jSObject_toNumberNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[15] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 32768) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.foreignObject_toPrimitiveNode_, this.foreignObject_toUInt32Node_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[16] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToUInt32Node create(boolean unsignedRightShift, int shiftValue) {
      return new JSToUInt32NodeGen(unsignedRightShift, shiftValue);
   }

   @GeneratedBy(JSToUInt32Node.JSToUInt32WrapperNode.class)
   public static final class JSToUInt32WrapperNodeGen extends JSToUInt32Node.JSToUInt32WrapperNode implements Introspection.Provider {
      private JSToUInt32WrapperNodeGen(JavaScriptNode operand, boolean unsignedRightShift, int shiftValue) {
         super(operand, unsignedRightShift, shiftValue);
      }

      @Override
      public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
         return this.doDefault(operandNodeValue);
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object operandNodeValue_ = super.operandNode.execute(frameValue);
         return this.doDefault(operandNodeValue_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doDefault", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JSToUInt32Node.JSToUInt32WrapperNode create(JavaScriptNode operand, boolean unsignedRightShift, int shiftValue) {
         return new JSToUInt32NodeGen.JSToUInt32WrapperNodeGen(operand, unsignedRightShift, shiftValue);
      }
   }
}
