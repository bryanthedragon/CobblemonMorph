package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ToRelativeTemporalObjectNode.class)
public final class ToRelativeTemporalObjectNodeGen extends ToRelativeTemporalObjectNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private ToRelativeTemporalObjectNodeGen.ToRelativeTemporalObjectData toRelativeTemporalObject_cache;

   private ToRelativeTemporalObjectNodeGen(JSContext ctx) {
      super(ctx);
   }

   @Override
   public JSDynamicObject execute(JSDynamicObject arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         ToRelativeTemporalObjectNodeGen.ToRelativeTemporalObjectData s0_ = this.toRelativeTemporalObject_cache;
         if (s0_ != null) {
            return this.toRelativeTemporalObject(
               arg0Value,
               s0_.errorBranch_,
               s0_.valueIsObject_,
               s0_.valueIsUndefined_,
               s0_.valueIsPlainDate_,
               s0_.valueIsPlainDateTime_,
               s0_.timeZoneAvailable_,
               s0_.toStringNode_,
               s0_.isObjectNode_,
               s0_.toTemporalCalendarWithISODefaultNode_,
               s0_.calendarFieldsNode_,
               s0_.dateFromFieldsNode_,
               s0_.getOptionNode_
            );
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   private JSDynamicObject executeAndSpecialize(JSDynamicObject arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      JSDynamicObject var6;
      try {
         int state_0 = this.state_0_;
         ToRelativeTemporalObjectNodeGen.ToRelativeTemporalObjectData s0_ = super.insert(new ToRelativeTemporalObjectNodeGen.ToRelativeTemporalObjectData());
         s0_.errorBranch_ = BranchProfile.create();
         s0_.valueIsObject_ = ConditionProfile.createBinaryProfile();
         s0_.valueIsUndefined_ = ConditionProfile.createBinaryProfile();
         s0_.valueIsPlainDate_ = ConditionProfile.createBinaryProfile();
         s0_.valueIsPlainDateTime_ = ConditionProfile.createBinaryProfile();
         s0_.timeZoneAvailable_ = ConditionProfile.createBinaryProfile();
         s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
         s0_.isObjectNode_ = s0_.insertAccessor(IsObjectNode.create());
         s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.ctx));
         s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.ctx));
         s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.ctx));
         s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
         VarHandle.storeStoreFence();
         this.toRelativeTemporalObject_cache = s0_;
         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var6 = this.toRelativeTemporalObject(
            arg0Value,
            s0_.errorBranch_,
            s0_.valueIsObject_,
            s0_.valueIsUndefined_,
            s0_.valueIsPlainDate_,
            s0_.valueIsPlainDateTime_,
            s0_.timeZoneAvailable_,
            s0_.toStringNode_,
            s0_.isObjectNode_,
            s0_.toTemporalCalendarWithISODefaultNode_,
            s0_.calendarFieldsNode_,
            s0_.dateFromFieldsNode_,
            s0_.getOptionNode_
         );
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
      Object[] s = new Object[]{"toRelativeTemporalObject", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         ToRelativeTemporalObjectNodeGen.ToRelativeTemporalObjectData s0_ = this.toRelativeTemporalObject_cache;
         if (s0_ != null) {
            cached.add(
               Arrays.asList(
                  s0_.errorBranch_,
                  s0_.valueIsObject_,
                  s0_.valueIsUndefined_,
                  s0_.valueIsPlainDate_,
                  s0_.valueIsPlainDateTime_,
                  s0_.timeZoneAvailable_,
                  s0_.toStringNode_,
                  s0_.isObjectNode_,
                  s0_.toTemporalCalendarWithISODefaultNode_,
                  s0_.calendarFieldsNode_,
                  s0_.dateFromFieldsNode_,
                  s0_.getOptionNode_
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

   public static ToRelativeTemporalObjectNode create(JSContext ctx) {
      return new ToRelativeTemporalObjectNodeGen(ctx);
   }

   @GeneratedBy(ToRelativeTemporalObjectNode.class)
   private static final class ToRelativeTemporalObjectData extends Node {
      @CompilerDirectives.CompilationFinal
      BranchProfile errorBranch_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile valueIsObject_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile valueIsUndefined_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile valueIsPlainDate_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile valueIsPlainDateTime_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile timeZoneAvailable_;
      @Node.Child
      JSToStringNode toStringNode_;
      @Node.Child
      IsObjectNode isObjectNode_;
      @Node.Child
      ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;
      @Node.Child
      TemporalCalendarFieldsNode calendarFieldsNode_;
      @Node.Child
      TemporalCalendarDateFromFieldsNode dateFromFieldsNode_;
      @Node.Child
      TemporalGetOptionNode getOptionNode_;

      ToRelativeTemporalObjectData() {
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
