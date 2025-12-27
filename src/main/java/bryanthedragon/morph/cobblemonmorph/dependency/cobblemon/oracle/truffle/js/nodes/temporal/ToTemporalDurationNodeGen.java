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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ToTemporalDurationNode.class)
public final class ToTemporalDurationNodeGen extends ToTemporalDurationNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private IsObjectNode isObjectNode_;
   @Node.Child
   private JSToStringNode toStringNode_;

   private ToTemporalDurationNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public JSDynamicObject executeDynamicObject(Object arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         return this.toTemporalDuration(arg0Value, this.isObjectNode_, this.toStringNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private JSDynamicObject executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      JSDynamicObject var5;
      try {
         int state_0 = this.state_0_;
         this.isObjectNode_ = super.insert(IsObjectNode.create());
         this.toStringNode_ = super.insert(JSToStringNode.create());
         int var9;
         this.state_0_ = var9 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var5 = this.toTemporalDuration(arg0Value, this.isObjectNode_, this.toStringNode_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var5;
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
      Object[] s = new Object[]{"toTemporalDuration", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.isObjectNode_, this.toStringNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static ToTemporalDurationNode create(JSContext context) {
      return new ToTemporalDurationNodeGen(context);
   }
}
