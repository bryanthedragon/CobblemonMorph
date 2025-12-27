package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(FlattenNode.class)
public final class FlattenNodeGen extends FlattenNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private TruffleString.MaterializeNode lazyString_materializeNode_;

   private FlattenNodeGen() {
   }

   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         return FlattenNode.doLazyString(arg0Value_, this.lazyString_materializeNode_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof SafeInteger) {
         SafeInteger arg0Value_ = (SafeInteger)arg0Value;
         return FlattenNode.doSafeInteger(arg0Value_);
      } else if ((state_0 & 4) != 0 && fallbackGuard_(state_0, arg0Value)) {
         return FlattenNode.doOther(arg0Value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private Object executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Double var6;
      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            this.lazyString_materializeNode_ = super.insert(TruffleString.MaterializeNode.create());
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return FlattenNode.doLazyString(arg0Value_, this.lazyString_materializeNode_);
         }

         if (!(arg0Value instanceof SafeInteger)) {
            int var11;
            this.state_0_ = var11 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return FlattenNode.doOther(arg0Value);
         }

         SafeInteger arg0Value_ = (SafeInteger)arg0Value;
         int var10;
         this.state_0_ = var10 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         var6 = FlattenNode.doSafeInteger(arg0Value_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var6;
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
      Object[] data = new Object[4];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doLazyString", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.lazyString_materializeNode_));
         s[2] = cached;
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
      s = new Object[]{"doOther", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(int state_0, Object arg0Value) {
      return (state_0 & 1) == 0 && arg0Value instanceof TruffleString ? false : (state_0 & 2) != 0 || !(arg0Value instanceof SafeInteger);
   }

   public static FlattenNode create() {
      return new FlattenNodeGen();
   }
}
