package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToBigIntNode.class)
public final class JSToBigIntNodeGen extends JSToBigIntNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSToPrimitiveNode toPrimitiveNode_;
   @Node.Child
   private JSToBigIntNode.JSToBigIntInnerConversionNode innerConversionNode_;

   private JSToBigIntNodeGen() {
   }

   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         return this.doIt(arg0Value, this.toPrimitiveNode_, this.innerConversionNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private Object executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var5;
      try {
         int state_0 = this.state_0_;
         this.toPrimitiveNode_ = super.insert(JSToPrimitiveNode.createHintNumber());
         this.innerConversionNode_ = super.insert(JSToBigIntNode.JSToBigIntInnerConversionNode.create());
         int var9;
         this.state_0_ = var9 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var5 = this.doIt(arg0Value, this.toPrimitiveNode_, this.innerConversionNode_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var5;
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doIt", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.toPrimitiveNode_, this.innerConversionNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToBigIntNode create() {
      return new JSToBigIntNodeGen();
   }

   @GeneratedBy(JSToBigIntNode.JSToBigIntInnerConversionNode.class)
   public static final class JSToBigIntInnerConversionNodeGen extends JSToBigIntNode.JSToBigIntInnerConversionNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSToBigIntInnerConversionNodeGen() {
      }

      @Override
      public Object execute(Object arg0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return JSToBigIntNode.JSToBigIntInnerConversionNode.doBoolean(arg0Value_);
         } else if ((state_0 & 2) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return JSToBigIntNode.JSToBigIntInnerConversionNode.doBigInt(arg0Value_);
         } else if ((state_0 & 4) != 0 && JSGuards.isNumber(arg0Value)) {
            return JSToBigIntNode.JSToBigIntInnerConversionNode.doDouble(arg0Value);
         } else if ((state_0 & 8) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return JSToBigIntNode.JSToBigIntInnerConversionNode.doSymbol(arg0Value_);
         } else if ((state_0 & 16) != 0 && JSGuards.isNullOrUndefined(arg0Value)) {
            return JSToBigIntNode.JSToBigIntInnerConversionNode.doNullOrUndefined(arg0Value);
         } else if ((state_0 & 32) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return JSToBigIntNode.JSToBigIntInnerConversionNode.doString(arg0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }

      private BigInt executeAndSpecialize(Object arg0Value) {
         int state_0 = this.state_0_;
         if (arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            return JSToBigIntNode.JSToBigIntInnerConversionNode.doBoolean(arg0Value_);
         } else if (arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            int var8;
            this.state_0_ = var8 = state_0 | 2;
            return JSToBigIntNode.JSToBigIntInnerConversionNode.doBigInt(arg0Value_);
         } else if (JSGuards.isNumber(arg0Value)) {
            int var7;
            this.state_0_ = var7 = state_0 | 4;
            return JSToBigIntNode.JSToBigIntInnerConversionNode.doDouble(arg0Value);
         } else if (arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 8;
            return JSToBigIntNode.JSToBigIntInnerConversionNode.doSymbol(arg0Value_);
         } else if (JSGuards.isNullOrUndefined(arg0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 16;
            return JSToBigIntNode.JSToBigIntInnerConversionNode.doNullOrUndefined(arg0Value);
         } else if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            int var4;
            this.state_0_ = var4 = state_0 | 32;
            return JSToBigIntNode.JSToBigIntInnerConversionNode.doString(arg0Value_);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[7];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doBoolean", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doBigInt", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doDouble", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"doSymbol", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"doNullOrUndefined", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"doString", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         return Introspection.Provider.create(data);
      }

      public static JSToBigIntNode.JSToBigIntInnerConversionNode create() {
         return new JSToBigIntNodeGen.JSToBigIntInnerConversionNodeGen();
      }
   }
}
