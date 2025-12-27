package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.OperatorSet;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSOverloadedUnaryNode.class)
public final class JSOverloadedUnaryNodeGen extends JSOverloadedUnaryNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSOverloadedUnaryNodeGen.CachedData cached_cache;
   @Node.Child
   private JSFunctionCallNode generic_callNode_;

   private JSOverloadedUnaryNodeGen(TruffleString overloadedOperatorName) {
      super(overloadedOperatorName);
   }

   @ExplodeLoop
   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0 && arg0Value instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject arg0Value_ = (JSOverloadedOperatorsObject)arg0Value;
         if ((state_0 & 1) != 0) {
            for (JSOverloadedUnaryNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               if (arg0Value_.matchesOperatorCounter(s0_.operatorCounter_)) {
                  return this.doCached(arg0Value_, s0_.operatorCounter_, s0_.operatorImplementation_, s0_.callNode_);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return this.doGeneric(arg0Value_, this.generic_callNode_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   private Object executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         int oldState_0 = state_0;

         try {
            if (!(arg0Value instanceof JSOverloadedOperatorsObject)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            } else {
               JSOverloadedOperatorsObject arg0Value_ = (JSOverloadedOperatorsObject)arg0Value;
               if (exclude == 0) {
                  int count0_ = 0;
                  JSOverloadedUnaryNodeGen.CachedData s0_ = this.cached_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !arg0Value_.matchesOperatorCounter(s0_.operatorCounter_)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null) {
                     int operatorCounter__ = arg0Value_.getOperatorCounter();
                     if (arg0Value_.matchesOperatorCounter(operatorCounter__) && count0_ < 3) {
                        s0_ = super.insert(new JSOverloadedUnaryNodeGen.CachedData(this.cached_cache));
                        s0_.operatorCounter_ = operatorCounter__;
                        s0_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value_, this.getOverloadedOperatorName());
                        s0_.callNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(arg0Value_, s0_.operatorCounter_, s0_.operatorImplementation_, s0_.callNode_);
                  }
               }

               this.generic_callNode_ = super.insert(JSFunctionCallNode.createCall());
               int var21;
               this.exclude_ = var21 = exclude | 1;
               this.cached_cache = null;
               state_0 &= -2;
               int var20;
               this.state_0_ = var20 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doGeneric(arg0Value_, this.generic_callNode_);
            }
         } finally {
            if (oldState_0 != 0) {
               this.checkForPolymorphicSpecialize(oldState_0);
            }
         }
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }
   }

   private void checkForPolymorphicSpecialize(int oldState_0) {
      if ((oldState_0 & 2) == 0 && (this.state_0_ & 2) != 0) {
         this.reportPolymorphicSpecialize();
      }
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & state_0 - 1) == 0) {
            JSOverloadedUnaryNodeGen.CachedData s0_ = this.cached_cache;
            if (s0_ == null || s0_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
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

         for (JSOverloadedUnaryNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.operatorCounter_, s0_.operatorImplementation_, s0_.callNode_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doGeneric", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.generic_callNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static JSOverloadedUnaryNode create(TruffleString overloadedOperatorName) {
      return new JSOverloadedUnaryNodeGen(overloadedOperatorName);
   }

   @GeneratedBy(JSOverloadedUnaryNode.class)
   private static final class CachedData extends Node {
      @Node.Child
      JSOverloadedUnaryNodeGen.CachedData next_;
      @CompilerDirectives.CompilationFinal
      int operatorCounter_;
      @CompilerDirectives.CompilationFinal
      Object operatorImplementation_;
      @Node.Child
      JSFunctionCallNode callNode_;

      CachedData(JSOverloadedUnaryNodeGen.CachedData next_) {
         this.next_ = next_;
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }
}
