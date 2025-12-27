package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(DefaultNumberOptionNode.class)
public final class DefaultNumberOptionNodeGen extends DefaultNumberOptionNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSToNumberNode getOption_toNumberNode_;
   @CompilerDirectives.CompilationFinal
   private BranchProfile getOption_errorBranch_;

   private DefaultNumberOptionNodeGen() {
   }

   @Override
   public int executeInt(Object arg0Value, int arg1Value, int arg2Value, int arg3Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && !JSGuards.isUndefined(arg0Value)) {
            return this.getOption(arg0Value, arg1Value, arg2Value, arg3Value, this.getOption_toNumberNode_, this.getOption_errorBranch_);
         }

         if ((state_0 & 2) != 0 && JSGuards.isUndefined(arg0Value)) {
            return this.getOptionFromUndefined(arg0Value, arg1Value, arg2Value, arg3Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
   }

   private int executeAndSpecialize(Object arg0Value, int arg1Value, int arg2Value, int arg3Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      int var8;
      try {
         int state_0 = this.state_0_;
         if (JSGuards.isUndefined(arg0Value)) {
            if (!JSGuards.isUndefined(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
            }

            int var13;
            this.state_0_ = var13 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.getOptionFromUndefined(arg0Value, arg1Value, arg2Value, arg3Value);
         }

         this.getOption_toNumberNode_ = super.insert(JSToNumberNode.create());
         this.getOption_errorBranch_ = BranchProfile.create();
         int var12;
         this.state_0_ = var12 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var8 = this.getOption(arg0Value, arg1Value, arg2Value, arg3Value, this.getOption_toNumberNode_, this.getOption_errorBranch_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var8;
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
      Object[] s = new Object[]{"getOption", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.getOption_toNumberNode_, this.getOption_errorBranch_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"getOptionFromUndefined", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static DefaultNumberOptionNode create() {
      return new DefaultNumberOptionNodeGen();
   }
}
