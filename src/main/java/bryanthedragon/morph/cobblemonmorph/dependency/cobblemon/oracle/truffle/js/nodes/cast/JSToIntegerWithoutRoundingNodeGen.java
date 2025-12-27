package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToIntegerWithoutRoundingNode.class)
public final class JSToIntegerWithoutRoundingNodeGen extends JSToIntegerWithoutRoundingNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSToIntegerWithoutRoundingNode recToIntOrInf;
   @CompilerDirectives.CompilationFinal
   private BranchProfile doubleInfinite_errorBranch_;
   @Node.Child
   private JSStringToNumberNode string_stringToNumberNode_;
   @Node.Child
   private JSToNumberNode jSOrForeignObject_toNumberNode_;

   private JSToIntegerWithoutRoundingNodeGen() {
   }

   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return JSToIntegerWithoutRoundingNode.doInteger(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof Long) {
         long arg0Value_ = (Long)arg0Value;
         return JSToIntegerWithoutRoundingNode.doLong(arg0Value_);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_ = (Boolean)arg0Value;
         return JSToIntegerWithoutRoundingNode.doBoolean(arg0Value_);
      } else if ((state_0 & 8) != 0 && arg0Value instanceof SafeInteger) {
         SafeInteger arg0Value_ = (SafeInteger)arg0Value;
         return JSToIntegerWithoutRoundingNode.doSafeInteger(arg0Value_);
      } else if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 30720) >>> 11, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 30720) >>> 11, arg0Value);
         return JSToIntegerWithoutRoundingNode.doDoubleInfinite(arg0Value_, this.doubleInfinite_errorBranch_);
      } else {
         if ((state_0 & 96) != 0) {
            if ((state_0 & 32) != 0 && JSGuards.isJSNull(arg0Value)) {
               return JSToIntegerWithoutRoundingNode.doNull(arg0Value);
            }

            if ((state_0 & 64) != 0 && JSGuards.isUndefined(arg0Value)) {
               return JSToIntegerWithoutRoundingNode.doUndefined(arg0Value);
            }
         }

         if ((state_0 & 128) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
         } else if ((state_0 & 256) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_);
         } else if ((state_0 & 512) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.doString(arg0Value_, this.recToIntOrInf, this.string_stringToNumberNode_);
         } else if ((state_0 & 1024) == 0 || !JSGuards.isForeignObject(arg0Value) && !JSGuards.isJSObject(arg0Value)) {
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

      Double arg0Value_;
      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof Integer) {
            int arg0Value_x = (Integer)arg0Value;
            int var23;
            this.state_0_ = var23 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return JSToIntegerWithoutRoundingNode.doInteger(arg0Value_x);
         }

         if (arg0Value instanceof Long) {
            long arg0Value_x = (Long)arg0Value;
            int var22;
            this.state_0_ = var22 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return JSToIntegerWithoutRoundingNode.doLong(arg0Value_x);
         }

         if (arg0Value instanceof Boolean) {
            boolean arg0Value_x = (Boolean)arg0Value;
            int var21;
            this.state_0_ = var21 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return JSToIntegerWithoutRoundingNode.doBoolean(arg0Value_x);
         }

         if (arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_x = (SafeInteger)arg0Value;
            int var20;
            this.state_0_ = var20 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return JSToIntegerWithoutRoundingNode.doSafeInteger(arg0Value_x);
         }

         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
            double arg0Value_x = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
            this.doubleInfinite_errorBranch_ = BranchProfile.create();
            state_0 |= doubleCast0 << 11;
            int var19;
            this.state_0_ = var19 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return JSToIntegerWithoutRoundingNode.doDoubleInfinite(arg0Value_x, this.doubleInfinite_errorBranch_);
         }

         if (JSGuards.isJSNull(arg0Value)) {
            int var17;
            this.state_0_ = var17 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return JSToIntegerWithoutRoundingNode.doNull(arg0Value);
         }

         if (JSGuards.isUndefined(arg0Value)) {
            int var16;
            this.state_0_ = var16 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return JSToIntegerWithoutRoundingNode.doUndefined(arg0Value);
         }

         if (arg0Value instanceof Symbol) {
            Symbol arg0Value_x = (Symbol)arg0Value;
            int var15;
            this.state_0_ = var15 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return this.doSymbol(arg0Value_x);
         }

         if (arg0Value instanceof BigInt) {
            BigInt arg0Value_x = (BigInt)arg0Value;
            int var14;
            this.state_0_ = var14 = state_0 | 256;
            lock.unlock();
            hasLock = false;
            return this.doBigInt(arg0Value_x);
         }

         if (!(arg0Value instanceof TruffleString)) {
            if (!JSGuards.isForeignObject(arg0Value) && !JSGuards.isJSObject(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            this.recToIntOrInf = super.insert(this.recToIntOrInf == null ? JSToIntegerWithoutRoundingNode.create() : this.recToIntOrInf);
            this.jSOrForeignObject_toNumberNode_ = super.insert(JSToNumberNode.create());
            int var13;
            this.state_0_ = var13 = state_0 | 1024;
            lock.unlock();
            hasLock = false;
            return this.doJSOrForeignObject(arg0Value, this.recToIntOrInf, this.jSOrForeignObject_toNumberNode_);
         }

         TruffleString arg0Value_x = (TruffleString)arg0Value;
         this.recToIntOrInf = super.insert(this.recToIntOrInf == null ? JSToIntegerWithoutRoundingNode.create() : this.recToIntOrInf);
         this.string_stringToNumberNode_ = super.insert(JSStringToNumberNode.create());
         int var12;
         this.state_0_ = var12 = state_0 | 512;
         lock.unlock();
         hasLock = false;
         arg0Value_ = this.doString(arg0Value_x, this.recToIntOrInf, this.string_stringToNumberNode_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return arg0Value_;
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if ((state_0 & 2047) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 2047 & (state_0 & 2047) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[12];
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
      s = new Object[]{"doDoubleInfinite", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.doubleInfinite_errorBranch_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doNull", null, null};
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
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.recToIntOrInf, this.string_stringToNumberNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doJSOrForeignObject", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.recToIntOrInf, this.jSOrForeignObject_toNumberNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToIntegerWithoutRoundingNode create() {
      return new JSToIntegerWithoutRoundingNodeGen();
   }
}
