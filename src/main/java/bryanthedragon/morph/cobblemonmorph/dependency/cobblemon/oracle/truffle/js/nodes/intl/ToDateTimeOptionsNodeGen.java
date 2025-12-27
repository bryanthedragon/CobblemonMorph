package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ToDateTimeOptionsNode.class)
public final class ToDateTimeOptionsNodeGen extends ToDateTimeOptionsNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private CreateObjectNode.CreateObjectWithPrototypeNode fromOtherThenUndefined_createObjectNode_;

   private ToDateTimeOptionsNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public JSDynamicObject execute(Object arg0Value, String arg1Value, String arg2Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && JSGuards.isUndefined(arg0Value)) {
            return this.fromUndefined(arg0Value, arg1Value, arg2Value);
         }

         if ((state_0 & 2) != 0 && !JSGuards.isUndefined(arg0Value)) {
            return this.fromOtherThenUndefined(arg0Value, arg1Value, arg2Value, this.fromOtherThenUndefined_createObjectNode_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
   }

   private JSDynamicObject executeAndSpecialize(Object arg0Value, String arg1Value, String arg2Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      JSDynamicObject var7;
      try {
         int state_0 = this.state_0_;
         if (!JSGuards.isUndefined(arg0Value)) {
            if (JSGuards.isUndefined(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }

            this.fromOtherThenUndefined_createObjectNode_ = super.insert(CreateObjectNode.createOrdinaryWithPrototype(this.context));
            int var12;
            this.state_0_ = var12 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.fromOtherThenUndefined(arg0Value, arg1Value, arg2Value, this.fromOtherThenUndefined_createObjectNode_);
         }

         int var11;
         this.state_0_ = var11 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var7 = this.fromUndefined(arg0Value, arg1Value, arg2Value);
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
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null, null};
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"fromUndefined", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"fromOtherThenUndefined", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.fromOtherThenUndefined_createObjectNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static ToDateTimeOptionsNode create(JSContext context) {
      return new ToDateTimeOptionsNodeGen(context);
   }
}
