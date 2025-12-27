package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TemporalUnbalanceDurationRelativeNode.class)
public final class TemporalUnbalanceDurationRelativeNodeGen extends TemporalUnbalanceDurationRelativeNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private TemporalUnbalanceDurationRelativeNodeGen.UnbalanceDurationRelativeData unbalanceDurationRelative_cache;

   private TemporalUnbalanceDurationRelativeNodeGen(JSContext ctx) {
      super(ctx);
   }

   @Override
   public JSTemporalDurationRecord execute(
      double arg0Value, double arg1Value, double arg2Value, double arg3Value, TemporalUtil.Unit arg4Value, JSDynamicObject arg5Value
   ) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0) {
         TemporalUnbalanceDurationRelativeNodeGen.UnbalanceDurationRelativeData s0_ = this.unbalanceDurationRelative_cache;
         if (s0_ != null) {
            return this.unbalanceDurationRelative(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               s0_.unitIsYear_,
               s0_.unitIsWeek_,
               s0_.unitIsMonth_,
               s0_.relativeToAvailable_,
               s0_.toTemporalDateNode_
            );
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
   }

   private JSTemporalDurationRecord executeAndSpecialize(
      double arg0Value, double arg1Value, double arg2Value, double arg3Value, TemporalUtil.Unit arg4Value, JSDynamicObject arg5Value
   ) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      JSTemporalDurationRecord var15;
      try {
         int state_0 = this.state_0_;
         TemporalUnbalanceDurationRelativeNodeGen.UnbalanceDurationRelativeData s0_ = super.insert(
            new TemporalUnbalanceDurationRelativeNodeGen.UnbalanceDurationRelativeData()
         );
         s0_.unitIsYear_ = ConditionProfile.create();
         s0_.unitIsWeek_ = ConditionProfile.create();
         s0_.unitIsMonth_ = ConditionProfile.create();
         s0_.relativeToAvailable_ = ConditionProfile.create();
         s0_.toTemporalDateNode_ = s0_.insertAccessor(ToTemporalDateNode.create(this.ctx));
         VarHandle.storeStoreFence();
         this.unbalanceDurationRelative_cache = s0_;
         int var19;
         this.state_0_ = var19 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var15 = this.unbalanceDurationRelative(
            arg0Value,
            arg1Value,
            arg2Value,
            arg3Value,
            arg4Value,
            arg5Value,
            s0_.unitIsYear_,
            s0_.unitIsWeek_,
            s0_.unitIsMonth_,
            s0_.relativeToAvailable_,
            s0_.toTemporalDateNode_
         );
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var15;
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      return (state_0 & 1) == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"unbalanceDurationRelative", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         TemporalUnbalanceDurationRelativeNodeGen.UnbalanceDurationRelativeData s0_ = this.unbalanceDurationRelative_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.unitIsYear_, s0_.unitIsWeek_, s0_.unitIsMonth_, s0_.relativeToAvailable_, s0_.toTemporalDateNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static TemporalUnbalanceDurationRelativeNode create(JSContext ctx) {
      return new TemporalUnbalanceDurationRelativeNodeGen(ctx);
   }

   @GeneratedBy(TemporalUnbalanceDurationRelativeNode.class)
   private static final class UnbalanceDurationRelativeData extends Node {
      @CompilerDirectives.CompilationFinal
      ConditionProfile unitIsYear_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile unitIsWeek_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile unitIsMonth_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile relativeToAvailable_;
      @Node.Child
      ToTemporalDateNode toTemporalDateNode_;

      UnbalanceDurationRelativeData() {
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
