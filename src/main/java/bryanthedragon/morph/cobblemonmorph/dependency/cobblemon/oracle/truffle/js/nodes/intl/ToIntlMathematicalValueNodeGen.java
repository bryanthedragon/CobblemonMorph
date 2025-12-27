package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ToIntlMathematicalValue.class)
public final class ToIntlMathematicalValueNodeGen extends ToIntlMathematicalValue implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSToPrimitiveNode generic_toPrimitiveNode_;
   @Node.Child
   private ToIntlMathematicalValue generic_nestedToIntlMVNode_;

   private ToIntlMathematicalValueNodeGen(boolean partOfRange) {
      super(partOfRange);
   }

   @Override
   public Number executeNumber(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3840) >>> 8, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 3840) >>> 8, arg0Value);
         return this.doDouble(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof BigInt) {
         BigInt arg0Value_ = (BigInt)arg0Value;
         return this.doBigInt(arg0Value_);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         return this.doString(arg0Value_);
      } else if ((state_0 & 8) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_ = (Boolean)arg0Value;
         return this.doBoolean(arg0Value_);
      } else {
         if ((state_0 & 48) != 0) {
            if ((state_0 & 16) != 0 && JSGuards.isUndefined(arg0Value)) {
               return this.doUndefined(arg0Value);
            }

            if ((state_0 & 32) != 0 && JSGuards.isJSNull(arg0Value)) {
               return this.doNull(arg0Value);
            }
         }

         if ((state_0 & 64) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
         } else if ((state_0 & 128) != 0) {
            return this.doGeneric(arg0Value, this.generic_toPrimitiveNode_, this.generic_nestedToIntlMVNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }
   }

   private Number executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Number arg0Value_;
      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         int doubleCast0;
         if ((exclude & 1) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
            double arg0Value_x = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
            state_0 |= doubleCast0 << 8;
            int var22;
            this.state_0_ = var22 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doDouble(arg0Value_x);
         }

         if ((exclude & 2) == 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_x = (BigInt)arg0Value;
            int var20;
            this.state_0_ = var20 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doBigInt(arg0Value_x);
         }

         if ((exclude & 4) == 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_x = (TruffleString)arg0Value;
            int var19;
            this.state_0_ = var19 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doString(arg0Value_x);
         }

         if ((exclude & 8) == 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_x = (Boolean)arg0Value;
            int var18;
            this.state_0_ = var18 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.doBoolean(arg0Value_x);
         }

         if ((exclude & 16) == 0 && JSGuards.isUndefined(arg0Value)) {
            int var17;
            this.state_0_ = var17 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.doUndefined(arg0Value);
         }

         if ((exclude & 32) == 0 && JSGuards.isJSNull(arg0Value)) {
            int var16;
            this.state_0_ = var16 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doNull(arg0Value);
         }

         if ((exclude & 64) != 0 || !(arg0Value instanceof Symbol)) {
            this.generic_toPrimitiveNode_ = super.insert(JSToPrimitiveNode.createHintNumber());
            this.generic_nestedToIntlMVNode_ = super.insert(ToIntlMathematicalValue.create(this.partOfRange));
            int var23;
            this.exclude_ = var23 = exclude | 127;
            state_0 &= -128;
            int var15;
            this.state_0_ = var15 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(arg0Value, this.generic_toPrimitiveNode_, this.generic_nestedToIntlMVNode_);
         }

         Symbol arg0Value_x = (Symbol)arg0Value;
         int var13;
         this.state_0_ = var13 = state_0 | 64;
         lock.unlock();
         hasLock = false;
         arg0Value_ = this.doSymbol(arg0Value_x);
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
      Object[] s = new Object[]{"doDouble", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doUndefined", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 16) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doNull", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 32) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 64) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doGeneric", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.generic_toPrimitiveNode_, this.generic_nestedToIntlMVNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      return Introspection.Provider.create(data);
   }

   public static ToIntlMathematicalValue create(boolean partOfRange) {
      return new ToIntlMathematicalValueNodeGen(partOfRange);
   }
}
