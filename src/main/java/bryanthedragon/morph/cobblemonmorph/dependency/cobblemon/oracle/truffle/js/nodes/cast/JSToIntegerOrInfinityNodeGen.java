package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToIntegerOrInfinityNode.class)
public final class JSToIntegerOrInfinityNodeGen extends JSToIntegerOrInfinityNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSToIntegerOrInfinityNode recToIntOrInf;
   @Node.Child
   private JSStringToNumberNode string_stringToNumberNode_;
   @Node.Child
   private JSToNumberNode jSOrForeignObject_toNumberNode_;

   private JSToIntegerOrInfinityNodeGen() {
   }

   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return JSToIntegerOrInfinityNode.doInteger(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof Long) {
         long arg0Value_ = (Long)arg0Value;
         return JSToIntegerOrInfinityNode.doLong(arg0Value_);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_ = (Boolean)arg0Value;
         return JSToIntegerOrInfinityNode.doBoolean(arg0Value_);
      } else if ((state_0 & 8) != 0 && arg0Value instanceof SafeInteger) {
         SafeInteger arg0Value_ = (SafeInteger)arg0Value;
         return JSToIntegerOrInfinityNode.doSafeInteger(arg0Value_);
      } else {
         if ((state_0 & 48) != 0 && JSTypesGen.isImplicitDouble((state_0 & 61440) >>> 12, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 61440) >>> 12, arg0Value);
            if ((state_0 & 16) != 0 && JSToIntegerOrInfinityNode.shouldConvertToZero(arg0Value_)) {
               return JSToIntegerOrInfinityNode.doDoubleNegativeZero(arg0Value_);
            }

            if ((state_0 & 32) != 0 && !JSToIntegerOrInfinityNode.shouldConvertToZero(arg0Value_)) {
               return this.doDouble(arg0Value_);
            }
         }

         if ((state_0 & 192) != 0) {
            if ((state_0 & 64) != 0 && JSGuards.isJSNull(arg0Value)) {
               return JSToIntegerOrInfinityNode.doNull(arg0Value);
            }

            if ((state_0 & 128) != 0 && JSGuards.isUndefined(arg0Value)) {
               return JSToIntegerOrInfinityNode.doUndefined(arg0Value);
            }
         }

         if ((state_0 & 256) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_x = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_x);
         } else if ((state_0 & 512) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_x = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_x);
         } else if ((state_0 & 1024) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_x = (TruffleString)arg0Value;
            return this.doString(arg0Value_x, this.recToIntOrInf, this.string_stringToNumberNode_);
         } else if ((state_0 & 2048) == 0 || !JSGuards.isForeignObject(arg0Value) && !JSGuards.isJSObject(arg0Value)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         } else {
            return this.doJSOrForeignObject(arg0Value, this.recToIntOrInf, this.jSOrForeignObject_toNumberNode_);
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
            int var25;
            this.state_0_ = var25 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return JSToIntegerOrInfinityNode.doInteger(arg0Value_);
         } else if (arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            int var24;
            this.state_0_ = var24 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return JSToIntegerOrInfinityNode.doLong(arg0Value_);
         } else if (arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            int var23;
            this.state_0_ = var23 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return JSToIntegerOrInfinityNode.doBoolean(arg0Value_);
         } else if (arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            int var22;
            this.state_0_ = var22 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return JSToIntegerOrInfinityNode.doSafeInteger(arg0Value_);
         } else {
            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
               double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
               if (JSToIntegerOrInfinityNode.shouldConvertToZero(arg0Value_)) {
                  state_0 |= doubleCast0 << 12;
                  int var21;
                  this.state_0_ = var21 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return JSToIntegerOrInfinityNode.doDoubleNegativeZero(arg0Value_);
               }

               if (!JSToIntegerOrInfinityNode.shouldConvertToZero(arg0Value_)) {
                  state_0 |= doubleCast0 << 12;
                  int var19;
                  this.state_0_ = var19 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return this.doDouble(arg0Value_);
               }
            }

            if (JSGuards.isJSNull(arg0Value)) {
               int var17;
               this.state_0_ = var17 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return JSToIntegerOrInfinityNode.doNull(arg0Value);
            } else if (JSGuards.isUndefined(arg0Value)) {
               int var16;
               this.state_0_ = var16 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return JSToIntegerOrInfinityNode.doUndefined(arg0Value);
            } else if (arg0Value instanceof Symbol) {
               Symbol arg0Value_x = (Symbol)arg0Value;
               int var15;
               this.state_0_ = var15 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return this.doSymbol(arg0Value_x);
            } else if (arg0Value instanceof BigInt) {
               BigInt arg0Value_x = (BigInt)arg0Value;
               int var14;
               this.state_0_ = var14 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(arg0Value_x);
            } else if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_x = (TruffleString)arg0Value;
               this.recToIntOrInf = super.insert(this.recToIntOrInf == null ? JSToIntegerOrInfinityNode.create() : this.recToIntOrInf);
               this.string_stringToNumberNode_ = super.insert(JSStringToNumberNode.create());
               int var13;
               this.state_0_ = var13 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               return this.doString(arg0Value_x, this.recToIntOrInf, this.string_stringToNumberNode_);
            } else if (!JSGuards.isForeignObject(arg0Value) && !JSGuards.isJSObject(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            } else {
               this.recToIntOrInf = super.insert(this.recToIntOrInf == null ? JSToIntegerOrInfinityNode.create() : this.recToIntOrInf);
               this.jSOrForeignObject_toNumberNode_ = super.insert(JSToNumberNode.create());
               int var12;
               this.state_0_ = var12 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               return this.doJSOrForeignObject(arg0Value, this.recToIntOrInf, this.jSOrForeignObject_toNumberNode_);
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
      if ((state_0 & 4095) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 4095 & (state_0 & 4095) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[13];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doInteger", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doLong", null, null};
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
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doDoubleNegativeZero", null, null};
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
      s = new Object[]{"doNull", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doUndefined", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doBigInt", null, null};
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
         cached.add(Arrays.asList(this.recToIntOrInf, this.string_stringToNumberNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doJSOrForeignObject", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.recToIntOrInf, this.jSOrForeignObject_toNumberNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToIntegerOrInfinityNode create() {
      return new JSToIntegerOrInfinityNodeGen();
   }
}
