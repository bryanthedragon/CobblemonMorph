package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(GetOptionsObjectNode.class)
public final class GetOptionsObjectNodeGen extends GetOptionsObjectNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private IsObjectNode fromOther_isObjectNode_;
   @CompilerDirectives.CompilationFinal
   private BranchProfile fromOther_errorBranch_;

   private GetOptionsObjectNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && JSGuards.isUndefined(arg0Value)) {
         return this.fromUndefined(arg0Value);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof JSObject) {
         JSObject arg0Value_ = (JSObject)arg0Value;
         return this.fromJSObject(arg0Value_);
      } else if ((state_0 & 4) != 0 && !JSGuards.isUndefined(arg0Value)) {
         return this.fromOther(arg0Value, this.fromOther_isObjectNode_, this.fromOther_errorBranch_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private Object executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var7;
      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (JSGuards.isUndefined(arg0Value)) {
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.fromUndefined(arg0Value);
         }

         if (exclude != 0 || !(arg0Value instanceof JSObject)) {
            if (JSGuards.isUndefined(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            this.fromOther_isObjectNode_ = super.insert(IsObjectNode.create());
            this.fromOther_errorBranch_ = BranchProfile.create();
            int var15;
            this.exclude_ = var15 = exclude | 1;
            state_0 &= -3;
            int var13;
            this.state_0_ = var13 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.fromOther(arg0Value, this.fromOther_isObjectNode_, this.fromOther_errorBranch_);
         }

         JSObject arg0Value_ = (JSObject)arg0Value;
         int var11;
         this.state_0_ = var11 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         var7 = this.fromJSObject(arg0Value_);
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
      Object[] data = new Object[4];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"fromUndefined", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"fromJSObject", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"fromOther", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.fromOther_isObjectNode_, this.fromOther_errorBranch_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static GetOptionsObjectNode create(JSContext context) {
      return new GetOptionsObjectNodeGen(context);
   }
}
