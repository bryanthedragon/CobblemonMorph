package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.regex.result.RegexResult;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(DispatchNode.class)
public final class DispatchNodeGen extends DispatchNode {
   private static final DispatchNodeGen.Uncached UNCACHED = new DispatchNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private DispatchNodeGen.ExecuteDirectData executeDirect_cache;
   @Node.Child
   private IndirectCallNode executeIndirect_callNode_;

   private DispatchNodeGen() {
   }

   @ExplodeLoop
   @Override
   public Object execute(CallTarget arg0Value, RegexResult arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (DispatchNodeGen.ExecuteDirectData s0_ = this.executeDirect_cache; s0_ != null; s0_ = s0_.next_) {
               if (arg0Value == s0_.cachedTarget_) {
                  return DispatchNode.executeDirect(arg0Value, arg1Value, s0_.cachedTarget_, s0_.callNode_);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return DispatchNode.executeIndirect(arg0Value, arg1Value, this.executeIndirect_callNode_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private Object executeAndSpecialize(CallTarget arg0Value, RegexResult arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude == 0) {
            int count0_ = 0;
            DispatchNodeGen.ExecuteDirectData s0_ = this.executeDirect_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null && arg0Value != s0_.cachedTarget_) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null && count0_ < 3) {
               s0_ = super.insert(new DispatchNodeGen.ExecuteDirectData(this.executeDirect_cache));
               s0_.cachedTarget_ = arg0Value;
               s0_.callNode_ = s0_.insertAccessor(DirectCallNode.create(s0_.cachedTarget_));
               VarHandle.storeStoreFence();
               this.executeDirect_cache = s0_;
               this.state_0_ = state_0 |= 1;
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               return DispatchNode.executeDirect(arg0Value, arg1Value, s0_.cachedTarget_, s0_.callNode_);
            }
         }

         this.executeIndirect_callNode_ = super.insert(IndirectCallNode.create());
         int var15;
         this.exclude_ = var15 = exclude | 1;
         this.executeDirect_cache = null;
         state_0 &= -2;
         int var14;
         this.state_0_ = var14 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         return DispatchNode.executeIndirect(arg0Value, arg1Value, this.executeIndirect_callNode_);
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
         if ((state_0 & state_0 - 1) == 0) {
            DispatchNodeGen.ExecuteDirectData s0_ = this.executeDirect_cache;
            if (s0_ == null || s0_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   public static DispatchNode create() {
      return new DispatchNodeGen();
   }

   public static DispatchNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(DispatchNode.class)
   private static final class ExecuteDirectData extends Node {
      @Node.Child
      DispatchNodeGen.ExecuteDirectData next_;
      @CompilerDirectives.CompilationFinal
      CallTarget cachedTarget_;
      @Node.Child
      DirectCallNode callNode_;

      ExecuteDirectData(DispatchNodeGen.ExecuteDirectData next_) {
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

   @GeneratedBy(DispatchNode.class)
   @DenyReplace
   private static final class Uncached extends DispatchNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public Object execute(CallTarget arg0Value, RegexResult arg1Value) {
         return DispatchNode.executeIndirect(arg0Value, arg1Value, IndirectCallNode.getUncached());
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @Override
      public boolean isAdoptable() {
         return false;
      }
   }
}
