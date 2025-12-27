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
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToIntegerAsIntNode.class)
public final class JSToIntegerAsIntNodeGen extends JSToIntegerAsIntNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSToIntegerAsIntNode string_nestedToIntegerNode_;
   @Node.Child
   private JSStringToNumberNode string_stringToNumberNode_;

   private JSToIntegerAsIntNodeGen() {
   }

   @Override
   public int executeInt(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return JSToIntegerAsIntNode.doInteger(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_ = (Boolean)arg0Value;
         return JSToIntegerAsIntNode.doBoolean(arg0Value_);
      } else {
         if ((state_0 & 12) != 0 && arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            if ((state_0 & 4) != 0 && JSGuards.isLongRepresentableAsInt32(arg0Value_.longValue())) {
               return JSToIntegerAsIntNode.doSafeIntegerInt32Range(arg0Value_);
            }

            if ((state_0 & 8) != 0 && !JSGuards.isLongRepresentableAsInt32(arg0Value_.longValue())) {
               return JSToIntegerAsIntNode.doSafeIntegerOther(arg0Value_);
            }
         }

         if ((state_0 & 48) != 0 && JSTypesGen.isImplicitDouble((state_0 & 122880) >>> 13, arg0Value)) {
            double arg0Value_x = JSTypesGen.asImplicitDouble((state_0 & 122880) >>> 13, arg0Value);
            if ((state_0 & 16) != 0 && JSToIntegerAsIntNode.inInt32Range(arg0Value_x)) {
               return JSToIntegerAsIntNode.doDoubleInt32Range(arg0Value_x);
            }

            if ((state_0 & 32) != 0 && !JSToIntegerAsIntNode.inInt32Range(arg0Value_x)) {
               return JSToIntegerAsIntNode.doDoubleOther(arg0Value_x);
            }
         }

         if ((state_0 & 192) != 0) {
            if ((state_0 & 64) != 0 && JSGuards.isUndefined(arg0Value)) {
               return JSToIntegerAsIntNode.doUndefined(arg0Value);
            }

            if ((state_0 & 128) != 0 && JSGuards.isJSNull(arg0Value)) {
               return JSToIntegerAsIntNode.doNull(arg0Value);
            }
         }

         if ((state_0 & 256) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_xx = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_xx);
         } else if ((state_0 & 512) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_xx = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_xx);
         } else if ((state_0 & 1024) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_xx = (TruffleString)arg0Value;
            return this.doString(arg0Value_xx, this.string_nestedToIntegerNode_, this.string_stringToNumberNode_);
         } else if ((state_0 & 2048) != 0 && arg0Value instanceof JSObject) {
            JSObject arg0Value_xx = (JSObject)arg0Value;
            return this.doJSObject(arg0Value_xx);
         } else if ((state_0 & 4096) != 0 && JSGuards.isForeignObject(arg0Value)) {
            return this.doForeignObject(arg0Value);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }
   }

   private int executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            int var26;
            this.state_0_ = var26 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return JSToIntegerAsIntNode.doInteger(arg0Value_);
         } else if (arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            int var25;
            this.state_0_ = var25 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return JSToIntegerAsIntNode.doBoolean(arg0Value_);
         } else {
            if (arg0Value instanceof SafeInteger) {
               SafeInteger arg0Value_ = (SafeInteger)arg0Value;
               if (JSGuards.isLongRepresentableAsInt32(arg0Value_.longValue())) {
                  int var24;
                  this.state_0_ = var24 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return JSToIntegerAsIntNode.doSafeIntegerInt32Range(arg0Value_);
               }

               if (!JSGuards.isLongRepresentableAsInt32(arg0Value_.longValue())) {
                  int var23;
                  this.state_0_ = var23 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return JSToIntegerAsIntNode.doSafeIntegerOther(arg0Value_);
               }
            }

            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
               double arg0Value_x = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
               if (JSToIntegerAsIntNode.inInt32Range(arg0Value_x)) {
                  state_0 |= doubleCast0 << 13;
                  int var22;
                  this.state_0_ = var22 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return JSToIntegerAsIntNode.doDoubleInt32Range(arg0Value_x);
               }

               if (!JSToIntegerAsIntNode.inInt32Range(arg0Value_x)) {
                  state_0 |= doubleCast0 << 13;
                  int var20;
                  this.state_0_ = var20 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return JSToIntegerAsIntNode.doDoubleOther(arg0Value_x);
               }
            }

            if (JSGuards.isUndefined(arg0Value)) {
               int var18;
               this.state_0_ = var18 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return JSToIntegerAsIntNode.doUndefined(arg0Value);
            } else if (JSGuards.isJSNull(arg0Value)) {
               int var17;
               this.state_0_ = var17 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return JSToIntegerAsIntNode.doNull(arg0Value);
            } else if (arg0Value instanceof Symbol) {
               Symbol arg0Value_xx = (Symbol)arg0Value;
               int var16;
               this.state_0_ = var16 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return this.doSymbol(arg0Value_xx);
            } else if (arg0Value instanceof BigInt) {
               BigInt arg0Value_xx = (BigInt)arg0Value;
               int var15;
               this.state_0_ = var15 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(arg0Value_xx);
            } else if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_xx = (TruffleString)arg0Value;
               this.string_nestedToIntegerNode_ = super.insert(JSToIntegerAsIntNode.create());
               this.string_stringToNumberNode_ = super.insert(JSStringToNumberNode.create());
               int var14;
               this.state_0_ = var14 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               return this.doString(arg0Value_xx, this.string_nestedToIntegerNode_, this.string_stringToNumberNode_);
            } else if (arg0Value instanceof JSObject) {
               JSObject arg0Value_xx = (JSObject)arg0Value;
               int var12;
               this.state_0_ = var12 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               return this.doJSObject(arg0Value_xx);
            } else if (!JSGuards.isForeignObject(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            } else {
               int var13;
               this.state_0_ = var13 = state_0 | 4096;
               lock.unlock();
               hasLock = false;
               return this.doForeignObject(arg0Value);
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
      if ((state_0 & 8191) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 8191 & (state_0 & 8191) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[14];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doInteger", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doSafeIntegerInt32Range", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doSafeIntegerOther", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doDoubleInt32Range", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doDoubleOther", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doUndefined", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doNull", null, null};
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
         cached.add(Arrays.asList(this.string_nestedToIntegerNode_, this.string_stringToNumberNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doJSObject", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToIntegerAsIntNode create() {
      return new JSToIntegerAsIntNodeGen();
   }
}
