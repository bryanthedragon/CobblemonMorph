package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSTypesGen;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToUInt16Node.class)
public final class JSToUInt16NodeGen extends JSToUInt16Node implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private BranchProfile double_needPositiveInfinityBranch_;
   @Node.Child
   private JSToNumberNode generic_toNumberNode_;

   private JSToUInt16NodeGen() {
   }

   @Override
   public int executeInt(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return this.doInt(arg0Value_);
      } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 120) >>> 3, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 120) >>> 3, arg0Value);
         return this.doDouble(arg0Value_, this.double_needPositiveInfinityBranch_);
      } else if ((state_0 & 4) != 0) {
         return this.doGeneric(arg0Value, this.generic_toNumberNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private int executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      int var8;
      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            int var15;
            this.state_0_ = var15 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doInt(arg0Value_);
         }

         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) == 0) {
            this.generic_toNumberNode_ = super.insert(JSToNumberNode.create());
            int var14;
            this.state_0_ = var14 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(arg0Value, this.generic_toNumberNode_);
         }

         double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
         this.double_needPositiveInfinityBranch_ = BranchProfile.create();
         state_0 |= doubleCast0 << 3;
         int var13;
         this.state_0_ = var13 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         var8 = this.doDouble(arg0Value_, this.double_needPositiveInfinityBranch_);
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
      if ((state_0 & 7) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 7 & (state_0 & 7) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[4];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doInt", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.double_needPositiveInfinityBranch_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doGeneric", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.generic_toNumberNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToUInt16Node create() {
      return new JSToUInt16NodeGen();
   }
}
