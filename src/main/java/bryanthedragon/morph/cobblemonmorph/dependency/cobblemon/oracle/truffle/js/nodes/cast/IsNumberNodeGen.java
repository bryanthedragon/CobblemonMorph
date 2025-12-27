package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.runtime.SafeInteger;

@GeneratedBy(IsNumberNode.class)
public final class IsNumberNodeGen extends IsNumberNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private IsNumberNodeGen() {
   }

   @Override
   public boolean execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return IsNumberNode.doInt(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof Long) {
         long arg0Value_ = (Long)arg0Value;
         return IsNumberNode.doLong(arg0Value_);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof SafeInteger) {
         SafeInteger arg0Value_ = (SafeInteger)arg0Value;
         return IsNumberNode.doSafeInteger(arg0Value_);
      } else if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, arg0Value);
         return IsNumberNode.doDouble(arg0Value_);
      } else if ((state_0 & 16) != 0 && fallbackGuard_(arg0Value)) {
         return IsNumberNode.doOther(arg0Value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private boolean executeAndSpecialize(Object arg0Value) {
      int state_0 = this.state_0_;
      if (arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         int var11;
         this.state_0_ = var11 = state_0 | 1;
         return IsNumberNode.doInt(arg0Value_);
      } else if (arg0Value instanceof Long) {
         long arg0Value_ = (Long)arg0Value;
         int var10;
         this.state_0_ = var10 = state_0 | 2;
         return IsNumberNode.doLong(arg0Value_);
      } else if (arg0Value instanceof SafeInteger) {
         SafeInteger arg0Value_ = (SafeInteger)arg0Value;
         int var9;
         this.state_0_ = var9 = state_0 | 4;
         return IsNumberNode.doSafeInteger(arg0Value_);
      } else {
         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
            double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
            state_0 |= doubleCast0 << 5;
            int var8;
            this.state_0_ = var8 = state_0 | 8;
            return IsNumberNode.doDouble(arg0Value_);
         } else {
            int var6;
            this.state_0_ = var6 = state_0 | 16;
            return IsNumberNode.doOther(arg0Value);
         }
      }
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if ((state_0 & 31) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 31 & (state_0 & 31) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[6];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doInt", null, null};
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
      s = new Object[]{"doSafeInteger", null, null};
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
      s = new Object[]{"doOther", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(Object arg0Value) {
      return !JSTypesGen.isImplicitDouble(arg0Value);
   }

   public static IsNumberNode create() {
      return new IsNumberNodeGen();
   }
}
