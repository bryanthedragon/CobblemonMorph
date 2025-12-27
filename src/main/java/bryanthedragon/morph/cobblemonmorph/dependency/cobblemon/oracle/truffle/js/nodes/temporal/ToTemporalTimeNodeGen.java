package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ToTemporalTimeNode.class)
public final class ToTemporalTimeNodeGen extends ToTemporalTimeNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private ToTemporalTimeNodeGen.ToTemporalTimeData toTemporalTime_cache;

   private ToTemporalTimeNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public JSDynamicObject executeDynamicObject(Object arg0Value, TemporalUtil.Overflow arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         ToTemporalTimeNodeGen.ToTemporalTimeData s0_ = this.toTemporalTime_cache;
         if (s0_ != null) {
            return this.toTemporalTime(arg0Value, arg1Value, s0_.isObjectNode_, s0_.toStringNode_, s0_.getTemporalCalendarNode_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private JSDynamicObject executeAndSpecialize(Object arg0Value, TemporalUtil.Overflow arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      JSDynamicObject var7;
      try {
         int state_0 = this.state_0_;
         ToTemporalTimeNodeGen.ToTemporalTimeData s0_ = super.insert(new ToTemporalTimeNodeGen.ToTemporalTimeData());
         s0_.isObjectNode_ = s0_.insertAccessor(IsObjectNode.create());
         s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
         s0_.getTemporalCalendarNode_ = s0_.insertAccessor(GetTemporalCalendarWithISODefaultNode.create(this.ctx));
         VarHandle.storeStoreFence();
         this.toTemporalTime_cache = s0_;
         int var11;
         this.state_0_ = var11 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var7 = this.toTemporalTime(arg0Value, arg1Value, s0_.isObjectNode_, s0_.toStringNode_, s0_.getTemporalCalendarNode_);
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
      Object[] s = new Object[]{"toTemporalTime", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         ToTemporalTimeNodeGen.ToTemporalTimeData s0_ = this.toTemporalTime_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.isObjectNode_, s0_.toStringNode_, s0_.getTemporalCalendarNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static ToTemporalTimeNode create(JSContext context) {
      return new ToTemporalTimeNodeGen(context);
   }

   @GeneratedBy(ToTemporalTimeNode.class)
   private static final class ToTemporalTimeData extends Node {
      @Node.Child
      IsObjectNode isObjectNode_;
      @Node.Child
      JSToStringNode toStringNode_;
      @Node.Child
      GetTemporalCalendarWithISODefaultNode getTemporalCalendarNode_;

      ToTemporalTimeData() {
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
