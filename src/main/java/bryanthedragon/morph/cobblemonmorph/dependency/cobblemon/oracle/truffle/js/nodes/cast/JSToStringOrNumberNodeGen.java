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

@GeneratedBy(JSToStringOrNumberNode.class)
public final class JSToStringOrNumberNodeGen extends JSToStringOrNumberNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSToDoubleNode jSObject_toDoubleNode_;

   private JSToStringOrNumberNodeGen() {
   }

   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return this.doInteger(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof SafeInteger) {
         SafeInteger arg0Value_ = (SafeInteger)arg0Value;
         return this.doSafeInteger(arg0Value_);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_ = (Boolean)arg0Value;
         return this.doBoolean(arg0Value_);
      } else if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, arg0Value);
         return this.doDouble(arg0Value_);
      } else if ((state_0 & 16) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         return this.doString(arg0Value_);
      } else if ((state_0 & 32) != 0 && arg0Value instanceof JSObject) {
         JSObject arg0Value_ = (JSObject)arg0Value;
         return this.doJSObject(arg0Value_, this.jSObject_toDoubleNode_);
      } else if ((state_0 & 64) != 0 && JSGuards.isJSNull(arg0Value)) {
         return this.doNull(arg0Value);
      } else if ((state_0 & 128) != 0 && arg0Value instanceof Symbol) {
         Symbol arg0Value_ = (Symbol)arg0Value;
         return this.doSymbol(arg0Value_);
      } else if ((state_0 & 256) != 0 && JSGuards.isUndefined(arg0Value)) {
         return this.doUndefined(arg0Value);
      } else if ((state_0 & 512) != 0 && arg0Value instanceof BigInt) {
         BigInt arg0Value_ = (BigInt)arg0Value;
         return JSToStringOrNumberNode.doBigInt(arg0Value_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private Object executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Double var23;
      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            int var22;
            this.state_0_ = var22 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doInteger(arg0Value_);
         }

         if (arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            int var21;
            this.state_0_ = var21 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doSafeInteger(arg0Value_);
         }

         if (arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            int var20;
            this.state_0_ = var20 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doBoolean(arg0Value_);
         }

         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
            double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
            state_0 |= doubleCast0 << 10;
            int var19;
            this.state_0_ = var19 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.doDouble(arg0Value_);
         }

         if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            int var17;
            this.state_0_ = var17 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.doString(arg0Value_);
         }

         if (arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            this.jSObject_toDoubleNode_ = super.insert(JSToDoubleNode.create());
            int var16;
            this.state_0_ = var16 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doJSObject(arg0Value_, this.jSObject_toDoubleNode_);
         }

         if (JSGuards.isJSNull(arg0Value)) {
            int var15;
            this.state_0_ = var15 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.doNull(arg0Value);
         }

         if (arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            int var14;
            this.state_0_ = var14 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return this.doSymbol(arg0Value_);
         }

         if (!JSGuards.isUndefined(arg0Value)) {
            if (!(arg0Value instanceof BigInt)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            BigInt arg0Value_ = (BigInt)arg0Value;
            int var13;
            this.state_0_ = var13 = state_0 | 512;
            lock.unlock();
            hasLock = false;
            return JSToStringOrNumberNode.doBigInt(arg0Value_);
         }

         int var12;
         this.state_0_ = var12 = state_0 | 256;
         lock.unlock();
         hasLock = false;
         var23 = this.doUndefined(arg0Value);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var23;
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
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doJSObject", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.jSObject_toDoubleNode_));
         s[2] = cached;
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
      s = new Object[]{"doSymbol", null, null};
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
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToStringOrNumberNode create() {
      return new JSToStringOrNumberNodeGen();
   }
}
