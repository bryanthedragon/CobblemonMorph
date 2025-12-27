package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ToTemporalCalendarWithISODefaultNode.class)
public final class ToTemporalCalendarWithISODefaultNodeGen extends ToTemporalCalendarWithISODefaultNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private ToTemporalCalendarWithISODefaultNodeGen.ToTemporalCalendarWithISODefaultData toTemporalCalendarWithISODefault_cache;

   private ToTemporalCalendarWithISODefaultNodeGen(JSContext ctx) {
      super(ctx);
   }

   @Override
   public JSDynamicObject executeDynamicObject(Object arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         ToTemporalCalendarWithISODefaultNodeGen.ToTemporalCalendarWithISODefaultData s0_ = this.toTemporalCalendarWithISODefault_cache;
         if (s0_ != null) {
            return this.toTemporalCalendarWithISODefault(arg0Value, s0_.errorBranch_, s0_.toTemporalCalendarNode_, s0_.calendarAvailable_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   private JSDynamicObject executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      JSDynamicObject var6;
      try {
         int state_0 = this.state_0_;
         ToTemporalCalendarWithISODefaultNodeGen.ToTemporalCalendarWithISODefaultData s0_ = super.insert(
            new ToTemporalCalendarWithISODefaultNodeGen.ToTemporalCalendarWithISODefaultData()
         );
         s0_.errorBranch_ = BranchProfile.create();
         s0_.toTemporalCalendarNode_ = s0_.insertAccessor(ToTemporalCalendarNode.create(this.ctx));
         s0_.calendarAvailable_ = ConditionProfile.createBinaryProfile();
         VarHandle.storeStoreFence();
         this.toTemporalCalendarWithISODefault_cache = s0_;
         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var6 = this.toTemporalCalendarWithISODefault(arg0Value, s0_.errorBranch_, s0_.toTemporalCalendarNode_, s0_.calendarAvailable_);
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
      return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"toTemporalCalendarWithISODefault", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         ToTemporalCalendarWithISODefaultNodeGen.ToTemporalCalendarWithISODefaultData s0_ = this.toTemporalCalendarWithISODefault_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.errorBranch_, s0_.toTemporalCalendarNode_, s0_.calendarAvailable_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static ToTemporalCalendarWithISODefaultNode create(JSContext ctx) {
      return new ToTemporalCalendarWithISODefaultNodeGen(ctx);
   }

   @GeneratedBy(ToTemporalCalendarWithISODefaultNode.class)
   private static final class ToTemporalCalendarWithISODefaultData extends Node {
      @CompilerDirectives.CompilationFinal
      BranchProfile errorBranch_;
      @Node.Child
      ToTemporalCalendarNode toTemporalCalendarNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile calendarAvailable_;

      ToTemporalCalendarWithISODefaultData() {
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
