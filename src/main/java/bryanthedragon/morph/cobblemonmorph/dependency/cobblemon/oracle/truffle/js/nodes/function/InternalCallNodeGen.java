package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(InternalCallNode.class)
public final class InternalCallNodeGen extends InternalCallNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private InternalCallNodeGen.DirectCallData directCall_cache;
   @Node.Child
   private IndirectCallNode indirectCall_indirectCallNode_;

   private InternalCallNodeGen() {
   }

   @ExplodeLoop
   @Override
   public Object execute(CallTarget arg0Value, Object[] arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (InternalCallNodeGen.DirectCallData s0_ = this.directCall_cache; s0_ != null; s0_ = s0_.next_) {
               if (arg0Value == s0_.cachedCallTarget_) {
                  return InternalCallNode.directCall(arg0Value, arg1Value, s0_.cachedCallTarget_, s0_.directCallNode_);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return InternalCallNode.indirectCall(arg0Value, arg1Value, this.indirectCall_indirectCallNode_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private Object executeAndSpecialize(CallTarget arg0Value, Object[] arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var8;
      try {
         int state_0 = this.state_0_;
         int count0_ = 0;
         InternalCallNodeGen.DirectCallData s0_ = this.directCall_cache;
         if ((state_0 & 1) != 0) {
            while (s0_ != null && arg0Value != s0_.cachedCallTarget_) {
               s0_ = s0_.next_;
               count0_++;
            }
         }

         if (s0_ == null && count0_ < 3) {
            s0_ = super.insert(new InternalCallNodeGen.DirectCallData(this.directCall_cache));
            s0_.cachedCallTarget_ = arg0Value;
            s0_.directCallNode_ = s0_.insertAccessor(DirectCallNode.create(s0_.cachedCallTarget_));
            VarHandle.storeStoreFence();
            this.directCall_cache = s0_;
            this.state_0_ = state_0 |= 1;
         }

         if (s0_ == null) {
            this.indirectCall_indirectCallNode_ = super.insert(IndirectCallNode.create());
            int var12;
            this.state_0_ = var12 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return InternalCallNode.indirectCall(arg0Value, arg1Value, this.indirectCall_indirectCallNode_);
         }

         lock.unlock();
         hasLock = false;
         var8 = InternalCallNode.directCall(arg0Value, arg1Value, s0_.cachedCallTarget_, s0_.directCallNode_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var8;
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & state_0 - 1) == 0) {
            InternalCallNodeGen.DirectCallData s0_ = this.directCall_cache;
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
      Object[] s = new Object[]{"directCall", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (InternalCallNodeGen.DirectCallData s0_ = this.directCall_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.cachedCallTarget_, s0_.directCallNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"indirectCall", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.indirectCall_indirectCallNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static InternalCallNode create() {
      return new InternalCallNodeGen();
   }

   @GeneratedBy(InternalCallNode.class)
   private static final class DirectCallData extends Node {
      @Node.Child
      InternalCallNodeGen.DirectCallData next_;
      @CompilerDirectives.CompilationFinal
      CallTarget cachedCallTarget_;
      @Node.Child
      DirectCallNode directCallNode_;

      DirectCallData(InternalCallNodeGen.DirectCallData next_) {
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
