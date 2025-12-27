package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ToTemporalInstantNode.class)
public final class ToTemporalInstantNodeGen extends ToTemporalInstantNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private ToTemporalInstantNodeGen.ToTemporalDateTimeData toTemporalDateTime_cache;

   private ToTemporalInstantNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public JSTemporalInstantObject execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         ToTemporalInstantNodeGen.ToTemporalDateTimeData s0_ = this.toTemporalDateTime_cache;
         if (s0_ != null) {
            return this.toTemporalDateTime(arg0Value, s0_.isObjectNode_, s0_.toStringNode_, s0_.isObjectProfile_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   private JSTemporalInstantObject executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      JSTemporalInstantObject var6;
      try {
         int state_0 = this.state_0_;
         ToTemporalInstantNodeGen.ToTemporalDateTimeData s0_ = super.insert(new ToTemporalInstantNodeGen.ToTemporalDateTimeData());
         s0_.isObjectNode_ = s0_.insertAccessor(IsObjectNode.create());
         s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
         s0_.isObjectProfile_ = ConditionProfile.createBinaryProfile();
         VarHandle.storeStoreFence();
         this.toTemporalDateTime_cache = s0_;
         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var6 = this.toTemporalDateTime(arg0Value, s0_.isObjectNode_, s0_.toStringNode_, s0_.isObjectProfile_);
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
      Object[] s = new Object[]{"toTemporalDateTime", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         ToTemporalInstantNodeGen.ToTemporalDateTimeData s0_ = this.toTemporalDateTime_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.isObjectNode_, s0_.toStringNode_, s0_.isObjectProfile_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static ToTemporalInstantNode create(JSContext context) {
      return new ToTemporalInstantNodeGen(context);
   }

   @GeneratedBy(ToTemporalInstantNode.class)
   private static final class ToTemporalDateTimeData extends Node {
      @Node.Child
      IsObjectNode isObjectNode_;
      @Node.Child
      JSToStringNode toStringNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isObjectProfile_;

      ToTemporalDateTimeData() {
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
