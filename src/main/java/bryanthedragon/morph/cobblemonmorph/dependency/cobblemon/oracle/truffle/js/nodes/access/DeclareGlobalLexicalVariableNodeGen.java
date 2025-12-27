package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(DeclareGlobalLexicalVariableNode.class)
public final class DeclareGlobalLexicalVariableNodeGen extends DeclareGlobalLexicalVariableNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private PropertySetNode cached_cache_;

   private DeclareGlobalLexicalVariableNodeGen(TruffleString varName, boolean isConst) {
      super(varName, isConst);
   }

   @Override
   protected void executeVoid(JSDynamicObject arg0Value, JSContext arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && arg1Value.getPropertyCacheLimit() > 0) {
            this.doCached(arg0Value, arg1Value, this.cached_cache_);
            return;
         }

         if ((state_0 & 2) != 0) {
            this.doUncached(arg0Value, arg1Value);
            return;
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private void executeAndSpecialize(JSDynamicObject arg0Value, JSContext arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude != 0 || arg1Value.getPropertyCacheLimit() <= 0) {
            int var13;
            this.exclude_ = var13 = exclude | 1;
            state_0 &= -2;
            int var12;
            this.state_0_ = var12 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            this.doUncached(arg0Value, arg1Value);
            return;
         }

         this.cached_cache_ = super.insert(this.makeDefineOwnPropertyCache(arg1Value));
         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         this.doCached(arg0Value, arg1Value, this.cached_cache_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
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
      Object[] data = new Object[]{0, null, null};
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doCached", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.cached_cache_));
         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doUncached", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static DeclareGlobalLexicalVariableNode create(TruffleString varName, boolean isConst) {
      return new DeclareGlobalLexicalVariableNodeGen(varName, isConst);
   }
}
