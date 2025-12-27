package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TemporalGetOptionNode.class)
public final class TemporalGetOptionNodeGen extends TemporalGetOptionNode implements Introspection.Provider {
   private static final TemporalGetOptionNodeGen.Uncached UNCACHED = new TemporalGetOptionNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private TemporalGetOptionNodeGen.GetOptionData getOption_cache;

   private TemporalGetOptionNodeGen() {
   }

   @Override
   public Object execute(JSDynamicObject arg0Value, TruffleString arg1Value, TemporalUtil.OptionType arg2Value, List<?> arg3Value, Object arg4Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         TemporalGetOptionNodeGen.GetOptionData s0_ = this.getOption_cache;
         if (s0_ != null) {
            return this.getOption(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               s0_.errorBranch_,
               s0_.isFallbackProfile_,
               s0_.toBooleanNode_,
               s0_.toStringNode_,
               s0_.toNumberNode_
            );
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
   }

   private Object executeAndSpecialize(
      JSDynamicObject arg0Value, TruffleString arg1Value, TemporalUtil.OptionType arg2Value, List<?> arg3Value, Object arg4Value
   ) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var10;
      try {
         int state_0 = this.state_0_;
         TemporalGetOptionNodeGen.GetOptionData s0_ = super.insert(new TemporalGetOptionNodeGen.GetOptionData());
         s0_.errorBranch_ = BranchProfile.create();
         s0_.isFallbackProfile_ = ConditionProfile.create();
         s0_.toBooleanNode_ = s0_.insertAccessor(JSToBooleanNode.create());
         s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
         s0_.toNumberNode_ = s0_.insertAccessor(JSToNumberNode.create());
         VarHandle.storeStoreFence();
         this.getOption_cache = s0_;
         int var14;
         this.state_0_ = var14 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var10 = this.getOption(
            arg0Value,
            arg1Value,
            arg2Value,
            arg3Value,
            arg4Value,
            s0_.errorBranch_,
            s0_.isFallbackProfile_,
            s0_.toBooleanNode_,
            s0_.toStringNode_,
            s0_.toNumberNode_
         );
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var10;
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
      Object[] s = new Object[]{"getOption", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         TemporalGetOptionNodeGen.GetOptionData s0_ = this.getOption_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.errorBranch_, s0_.isFallbackProfile_, s0_.toBooleanNode_, s0_.toStringNode_, s0_.toNumberNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static TemporalGetOptionNode create() {
      return new TemporalGetOptionNodeGen();
   }

   public static TemporalGetOptionNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(TemporalGetOptionNode.class)
   private static final class GetOptionData extends Node {
      @CompilerDirectives.CompilationFinal
      BranchProfile errorBranch_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isFallbackProfile_;
      @Node.Child
      JSToBooleanNode toBooleanNode_;
      @Node.Child
      JSToStringNode toStringNode_;
      @Node.Child
      JSToNumberNode toNumberNode_;

      GetOptionData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(TemporalGetOptionNode.class)
   @DenyReplace
   private static final class Uncached extends TemporalGetOptionNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public Object execute(JSDynamicObject arg0Value, TruffleString arg1Value, TemporalUtil.OptionType arg2Value, List<?> arg3Value, Object arg4Value) {
         return this.getOption(
            arg0Value,
            arg1Value,
            arg2Value,
            arg3Value,
            arg4Value,
            BranchProfile.getUncached(),
            ConditionProfile.getUncached(),
            JSToBooleanNodeGen.getUncached(),
            this.createEmptyToString(),
            this.createEmptyToNumber()
         );
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
