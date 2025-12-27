package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;

@GeneratedBy(ToArrayLengthNode.class)
public final class ToArrayLengthNodeGen extends ToArrayLengthNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private ToArrayLengthNodeGen() {
   }

   @Override
   public long executeLong(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return ToArrayLengthNode.doInt(arg0Value_);
      } else {
         if ((state_0 & 6) != 0 && arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            if ((state_0 & 2) != 0 && JSRuntime.isValidArrayLength(arg0Value_.longValue())) {
               return ToArrayLengthNode.doSafeInteger(arg0Value_);
            }

            if ((state_0 & 4) != 0 && !JSRuntime.isValidArrayLength(arg0Value_.longValue())) {
               return ToArrayLengthNode.rangeError(arg0Value_);
            }
         }

         if ((state_0 & 24) != 0 && arg0Value instanceof Long) {
            long arg0Value_x = (Long)arg0Value;
            if ((state_0 & 8) != 0 && JSRuntime.isValidArrayLength(arg0Value_x)) {
               return ToArrayLengthNode.doLong(arg0Value_x);
            }

            if ((state_0 & 16) != 0 && !JSRuntime.isValidArrayLength(arg0Value_x)) {
               return ToArrayLengthNode.rangeError(arg0Value_x);
            }
         }

         if ((state_0 & 96) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3840) >>> 8, arg0Value)) {
            double arg0Value_xx = JSTypesGen.asImplicitDouble((state_0 & 3840) >>> 8, arg0Value);
            if ((state_0 & 32) != 0 && JSRuntime.isValidArrayLength(arg0Value_xx)) {
               return ToArrayLengthNode.doDouble(arg0Value_xx);
            }

            if ((state_0 & 64) != 0 && !JSRuntime.isValidArrayLength(arg0Value_xx)) {
               return ToArrayLengthNode.rangeError(arg0Value_xx);
            }
         }

         if ((state_0 & 128) != 0 && !JSRuntime.isNumber(arg0Value)) {
            return ToArrayLengthNode.typeNotNumber(arg0Value);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }
   }

   private long executeAndSpecialize(Object arg0Value) {
      int state_0 = this.state_0_;
      if (arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         int var15;
         this.state_0_ = var15 = state_0 | 1;
         return ToArrayLengthNode.doInt(arg0Value_);
      } else {
         if (arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            if (JSRuntime.isValidArrayLength(arg0Value_.longValue())) {
               int var14;
               this.state_0_ = var14 = state_0 | 2;
               return ToArrayLengthNode.doSafeInteger(arg0Value_);
            }

            if (!JSRuntime.isValidArrayLength(arg0Value_.longValue())) {
               int var13;
               this.state_0_ = var13 = state_0 | 4;
               return ToArrayLengthNode.rangeError(arg0Value_);
            }
         }

         if (arg0Value instanceof Long) {
            long arg0Value_x = (Long)arg0Value;
            if (JSRuntime.isValidArrayLength(arg0Value_x)) {
               int var12;
               this.state_0_ = var12 = state_0 | 8;
               return ToArrayLengthNode.doLong(arg0Value_x);
            }

            if (!JSRuntime.isValidArrayLength(arg0Value_x)) {
               int var11;
               this.state_0_ = var11 = state_0 | 16;
               return ToArrayLengthNode.rangeError(arg0Value_x);
            }
         }

         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
            double arg0Value_xx = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
            if (JSRuntime.isValidArrayLength(arg0Value_xx)) {
               state_0 |= doubleCast0 << 8;
               int var10;
               this.state_0_ = var10 = state_0 | 32;
               return ToArrayLengthNode.doDouble(arg0Value_xx);
            }

            if (!JSRuntime.isValidArrayLength(arg0Value_xx)) {
               state_0 |= doubleCast0 << 8;
               int var8;
               this.state_0_ = var8 = state_0 | 64;
               return ToArrayLengthNode.rangeError(arg0Value_xx);
            }
         }

         if (!JSRuntime.isNumber(arg0Value)) {
            int var6;
            this.state_0_ = var6 = state_0 | 128;
            return ToArrayLengthNode.typeNotNumber(arg0Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
      Object[] s = new Object[]{"doInt", null, null};
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
      s = new Object[]{"rangeError", null, null};
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
      s = new Object[]{"rangeError", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"rangeError", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"typeNotNumber", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      return Introspection.Provider.create(data);
   }

   public static ToArrayLengthNode create() {
      return new ToArrayLengthNodeGen();
   }
}
