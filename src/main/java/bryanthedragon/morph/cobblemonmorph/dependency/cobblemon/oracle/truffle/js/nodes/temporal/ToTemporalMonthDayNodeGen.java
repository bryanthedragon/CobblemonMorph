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
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ToTemporalMonthDayNode.class)
public final class ToTemporalMonthDayNodeGen extends ToTemporalMonthDayNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private ToTemporalMonthDayNodeGen.ToTemporalMonthDayData toTemporalMonthDay_cache;

   private ToTemporalMonthDayNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public JSTemporalPlainMonthDayObject executeDynamicObject(Object arg0Value, JSDynamicObject arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         ToTemporalMonthDayNodeGen.ToTemporalMonthDayData s0_ = this.toTemporalMonthDay_cache;
         if (s0_ != null) {
            return this.toTemporalMonthDay(
               arg0Value,
               arg1Value,
               s0_.errorBranch_,
               s0_.isObjectNode_,
               s0_.toStringNode_,
               s0_.toTemporalCalendarWithISODefaultNode_,
               s0_.monthDayFromFieldsNode_,
               s0_.calendarFieldsNode_
            );
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private JSTemporalPlainMonthDayObject executeAndSpecialize(Object arg0Value, JSDynamicObject arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      JSTemporalPlainMonthDayObject var7;
      try {
         int state_0 = this.state_0_;
         ToTemporalMonthDayNodeGen.ToTemporalMonthDayData s0_ = super.insert(new ToTemporalMonthDayNodeGen.ToTemporalMonthDayData());
         s0_.errorBranch_ = BranchProfile.create();
         s0_.isObjectNode_ = s0_.insertAccessor(IsObjectNode.create());
         s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
         s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.ctx));
         s0_.monthDayFromFieldsNode_ = s0_.insertAccessor(TemporalMonthDayFromFieldsNode.create(this.ctx));
         s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.ctx));
         VarHandle.storeStoreFence();
         this.toTemporalMonthDay_cache = s0_;
         int var11;
         this.state_0_ = var11 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var7 = this.toTemporalMonthDay(
            arg0Value,
            arg1Value,
            s0_.errorBranch_,
            s0_.isObjectNode_,
            s0_.toStringNode_,
            s0_.toTemporalCalendarWithISODefaultNode_,
            s0_.monthDayFromFieldsNode_,
            s0_.calendarFieldsNode_
         );
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var7;
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
      Object[] s = new Object[]{"toTemporalMonthDay", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         ToTemporalMonthDayNodeGen.ToTemporalMonthDayData s0_ = this.toTemporalMonthDay_cache;
         if (s0_ != null) {
            cached.add(
               Arrays.asList(
                  s0_.errorBranch_,
                  s0_.isObjectNode_,
                  s0_.toStringNode_,
                  s0_.toTemporalCalendarWithISODefaultNode_,
                  s0_.monthDayFromFieldsNode_,
                  s0_.calendarFieldsNode_
               )
            );
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static ToTemporalMonthDayNode create(JSContext context) {
      return new ToTemporalMonthDayNodeGen(context);
   }

   @GeneratedBy(ToTemporalMonthDayNode.class)
   private static final class ToTemporalMonthDayData extends Node {
      @CompilerDirectives.CompilationFinal
      BranchProfile errorBranch_;
      @Node.Child
      IsObjectNode isObjectNode_;
      @Node.Child
      JSToStringNode toStringNode_;
      @Node.Child
      ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;
      @Node.Child
      TemporalMonthDayFromFieldsNode monthDayFromFieldsNode_;
      @Node.Child
      TemporalCalendarFieldsNode calendarFieldsNode_;

      ToTemporalMonthDayData() {
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
