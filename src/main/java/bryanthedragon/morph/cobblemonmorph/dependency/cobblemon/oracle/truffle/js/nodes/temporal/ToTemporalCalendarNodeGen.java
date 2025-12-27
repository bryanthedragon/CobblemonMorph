package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ToTemporalCalendarNode.class)
public final class ToTemporalCalendarNodeGen extends ToTemporalCalendarNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private ToTemporalCalendarNodeGen.ToTemporalCalendarData toTemporalCalendar_cache;

   private ToTemporalCalendarNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public JSDynamicObject executeDynamicObject(Object arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         ToTemporalCalendarNodeGen.ToTemporalCalendarData s0_ = this.toTemporalCalendar_cache;
         if (s0_ != null) {
            return this.toTemporalCalendar(arg0Value, s0_.errorBranch_, s0_.isObjectNode_, s0_.toStringNode_);
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
         ToTemporalCalendarNodeGen.ToTemporalCalendarData s0_ = super.insert(new ToTemporalCalendarNodeGen.ToTemporalCalendarData());
         s0_.errorBranch_ = BranchProfile.create();
         s0_.isObjectNode_ = s0_.insertAccessor(IsObjectNode.create());
         s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
         VarHandle.storeStoreFence();
         this.toTemporalCalendar_cache = s0_;
         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var6 = this.toTemporalCalendar(arg0Value, s0_.errorBranch_, s0_.isObjectNode_, s0_.toStringNode_);
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
      Object[] s = new Object[]{"toTemporalCalendar", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         ToTemporalCalendarNodeGen.ToTemporalCalendarData s0_ = this.toTemporalCalendar_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.errorBranch_, s0_.isObjectNode_, s0_.toStringNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static ToTemporalCalendarNode create(JSContext context) {
      return new ToTemporalCalendarNodeGen(context);
   }

   @GeneratedBy(ToTemporalCalendarNode.class)
   private static final class ToTemporalCalendarData extends Node {
      @CompilerDirectives.CompilationFinal
      BranchProfile errorBranch_;
      @Node.Child
      IsObjectNode isObjectNode_;
      @Node.Child
      JSToStringNode toStringNode_;

      ToTemporalCalendarData() {
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
