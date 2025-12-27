package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ToTemporalDateNode.class)
public final class ToTemporalDateNodeGen extends ToTemporalDateNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private ToTemporalDateNodeGen.ToTemporalDateData toTemporalDate_cache;

   private ToTemporalDateNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public JSTemporalPlainDateObject executeDynamicObject(Object arg0Value, JSDynamicObject arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         ToTemporalDateNodeGen.ToTemporalDateData s0_ = this.toTemporalDate_cache;
         if (s0_ != null) {
            return this.toTemporalDate(
               arg0Value,
               arg1Value,
               s0_.isObjectNode_,
               s0_.toStringNode_,
               s0_.getTemporalCalendarNode_,
               s0_.getOptionNode_,
               s0_.toTemporalCalendarWithISODefaultNode_,
               s0_.calendarFieldsNode_,
               s0_.dateFromFieldsNode_
            );
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private JSTemporalPlainDateObject executeAndSpecialize(Object arg0Value, JSDynamicObject arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      JSTemporalPlainDateObject var7;
      try {
         int state_0 = this.state_0_;
         ToTemporalDateNodeGen.ToTemporalDateData s0_ = super.insert(new ToTemporalDateNodeGen.ToTemporalDateData());
         s0_.isObjectNode_ = s0_.insertAccessor(IsObjectNode.create());
         s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
         s0_.getTemporalCalendarNode_ = s0_.insertAccessor(GetTemporalCalendarWithISODefaultNode.create(this.ctx));
         s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
         s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.ctx));
         s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.ctx));
         s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.ctx));
         VarHandle.storeStoreFence();
         this.toTemporalDate_cache = s0_;
         int var11;
         this.state_0_ = var11 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var7 = this.toTemporalDate(
            arg0Value,
            arg1Value,
            s0_.isObjectNode_,
            s0_.toStringNode_,
            s0_.getTemporalCalendarNode_,
            s0_.getOptionNode_,
            s0_.toTemporalCalendarWithISODefaultNode_,
            s0_.calendarFieldsNode_,
            s0_.dateFromFieldsNode_
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
      Object[] s = new Object[]{"toTemporalDate", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         ToTemporalDateNodeGen.ToTemporalDateData s0_ = this.toTemporalDate_cache;
         if (s0_ != null) {
            cached.add(
               Arrays.asList(
                  s0_.isObjectNode_,
                  s0_.toStringNode_,
                  s0_.getTemporalCalendarNode_,
                  s0_.getOptionNode_,
                  s0_.toTemporalCalendarWithISODefaultNode_,
                  s0_.calendarFieldsNode_,
                  s0_.dateFromFieldsNode_
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

   public static ToTemporalDateNode create(JSContext context) {
      return new ToTemporalDateNodeGen(context);
   }

   @GeneratedBy(ToTemporalDateNode.class)
   private static final class ToTemporalDateData extends Node {
      @Node.Child
      IsObjectNode isObjectNode_;
      @Node.Child
      JSToStringNode toStringNode_;
      @Node.Child
      GetTemporalCalendarWithISODefaultNode getTemporalCalendarNode_;
      @Node.Child
      TemporalGetOptionNode getOptionNode_;
      @Node.Child
      ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;
      @Node.Child
      TemporalCalendarFieldsNode calendarFieldsNode_;
      @Node.Child
      TemporalCalendarDateFromFieldsNode dateFromFieldsNode_;

      ToTemporalDateData() {
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
