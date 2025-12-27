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
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonthObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ToTemporalYearMonthNode.class)
public final class ToTemporalYearMonthNodeGen extends ToTemporalYearMonthNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private ToTemporalYearMonthNodeGen.ToTemporalYearMonthData toTemporalYearMonth_cache;

   private ToTemporalYearMonthNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public JSTemporalPlainYearMonthObject executeDynamicObject(Object arg0Value, JSDynamicObject arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         ToTemporalYearMonthNodeGen.ToTemporalYearMonthData s0_ = this.toTemporalYearMonth_cache;
         if (s0_ != null) {
            return this.toTemporalYearMonth(
               arg0Value,
               arg1Value,
               s0_.errorBranch_,
               s0_.isObjectNode_,
               s0_.toStringNode_,
               s0_.getTemporalCalendarWithISODefaultNode_,
               s0_.toTemporalCalendarWithISODefaultNode_,
               s0_.getOptionNode_,
               s0_.yearMonthFromFieldsNode_,
               s0_.calendarFieldsNode_
            );
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private JSTemporalPlainYearMonthObject executeAndSpecialize(Object arg0Value, JSDynamicObject arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      JSTemporalPlainYearMonthObject var7;
      try {
         int state_0 = this.state_0_;
         ToTemporalYearMonthNodeGen.ToTemporalYearMonthData s0_ = super.insert(new ToTemporalYearMonthNodeGen.ToTemporalYearMonthData());
         s0_.errorBranch_ = BranchProfile.create();
         s0_.isObjectNode_ = s0_.insertAccessor(IsObjectNode.create());
         s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
         s0_.getTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(GetTemporalCalendarWithISODefaultNode.create(this.ctx));
         s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.ctx));
         s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
         s0_.yearMonthFromFieldsNode_ = s0_.insertAccessor(TemporalYearMonthFromFieldsNode.create(this.ctx));
         s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.ctx));
         VarHandle.storeStoreFence();
         this.toTemporalYearMonth_cache = s0_;
         int var11;
         this.state_0_ = var11 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var7 = this.toTemporalYearMonth(
            arg0Value,
            arg1Value,
            s0_.errorBranch_,
            s0_.isObjectNode_,
            s0_.toStringNode_,
            s0_.getTemporalCalendarWithISODefaultNode_,
            s0_.toTemporalCalendarWithISODefaultNode_,
            s0_.getOptionNode_,
            s0_.yearMonthFromFieldsNode_,
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
      Object[] s = new Object[]{"toTemporalYearMonth", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         ToTemporalYearMonthNodeGen.ToTemporalYearMonthData s0_ = this.toTemporalYearMonth_cache;
         if (s0_ != null) {
            cached.add(
               Arrays.asList(
                  s0_.errorBranch_,
                  s0_.isObjectNode_,
                  s0_.toStringNode_,
                  s0_.getTemporalCalendarWithISODefaultNode_,
                  s0_.toTemporalCalendarWithISODefaultNode_,
                  s0_.getOptionNode_,
                  s0_.yearMonthFromFieldsNode_,
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

   public static ToTemporalYearMonthNode create(JSContext context) {
      return new ToTemporalYearMonthNodeGen(context);
   }

   @GeneratedBy(ToTemporalYearMonthNode.class)
   private static final class ToTemporalYearMonthData extends Node {
      @CompilerDirectives.CompilationFinal
      BranchProfile errorBranch_;
      @Node.Child
      IsObjectNode isObjectNode_;
      @Node.Child
      JSToStringNode toStringNode_;
      @Node.Child
      GetTemporalCalendarWithISODefaultNode getTemporalCalendarWithISODefaultNode_;
      @Node.Child
      ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;
      @Node.Child
      TemporalGetOptionNode getOptionNode_;
      @Node.Child
      TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode_;
      @Node.Child
      TemporalCalendarFieldsNode calendarFieldsNode_;

      ToTemporalYearMonthData() {
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
