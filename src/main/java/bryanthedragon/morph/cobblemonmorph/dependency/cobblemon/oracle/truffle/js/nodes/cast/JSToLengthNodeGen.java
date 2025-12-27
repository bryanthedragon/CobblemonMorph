package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToLengthNode.class)
public final class JSToLengthNodeGen extends JSToLengthNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private BranchProfile negativeBranch;
   @CompilerDirectives.CompilationFinal
   private BranchProfile tooLargeBranch;
   @Node.Child
   private JSToNumberNode object_toNumberNode_;

   private JSToLengthNodeGen() {
   }

   @Override
   public long executeLong(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return JSToLengthNode.doInt(arg0Value_, this.negativeBranch);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof SafeInteger) {
         SafeInteger arg0Value_ = (SafeInteger)arg0Value;
         return JSToLengthNode.doSafeInteger(arg0Value_, this.negativeBranch);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, arg0Value);
         return JSToLengthNode.doDouble(arg0Value_, this.negativeBranch, this.tooLargeBranch);
      } else {
         if ((state_0 & 24) != 0) {
            if ((state_0 & 8) != 0 && JSGuards.isUndefined(arg0Value)) {
               return JSToLengthNode.doUndefined(arg0Value);
            }

            if ((state_0 & 16) != 0) {
               return JSToLengthNode.doObject(arg0Value, this.object_toNumberNode_, this.negativeBranch, this.tooLargeBranch);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private long executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      long var8;
      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            this.negativeBranch = this.negativeBranch == null ? BranchProfile.create() : this.negativeBranch;
            int var18;
            this.state_0_ = var18 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return JSToLengthNode.doInt(arg0Value_, this.negativeBranch);
         }

         if (arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            this.negativeBranch = this.negativeBranch == null ? BranchProfile.create() : this.negativeBranch;
            int var17;
            this.state_0_ = var17 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return JSToLengthNode.doSafeInteger(arg0Value_, this.negativeBranch);
         }

         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) == 0) {
            if (!JSGuards.isUndefined(arg0Value)) {
               this.object_toNumberNode_ = super.insert(JSToNumberNode.create());
               this.negativeBranch = this.negativeBranch == null ? BranchProfile.create() : this.negativeBranch;
               this.tooLargeBranch = this.tooLargeBranch == null ? BranchProfile.create() : this.tooLargeBranch;
               int var16;
               this.state_0_ = var16 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return JSToLengthNode.doObject(arg0Value, this.object_toNumberNode_, this.negativeBranch, this.tooLargeBranch);
            }

            int var15;
            this.state_0_ = var15 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return JSToLengthNode.doUndefined(arg0Value);
         }

         double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
         this.negativeBranch = this.negativeBranch == null ? BranchProfile.create() : this.negativeBranch;
         this.tooLargeBranch = this.tooLargeBranch == null ? BranchProfile.create() : this.tooLargeBranch;
         state_0 |= doubleCast0 << 5;
         int var14;
         this.state_0_ = var14 = state_0 | 4;
         lock.unlock();
         hasLock = false;
         var8 = JSToLengthNode.doDouble(arg0Value_, this.negativeBranch, this.tooLargeBranch);
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
      if ((state_0 & 31) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 31 & (state_0 & 31) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[6];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doInt", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.negativeBranch));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.negativeBranch));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.negativeBranch, this.tooLargeBranch));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doUndefined", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doObject", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.object_toNumberNode_, this.negativeBranch, this.tooLargeBranch));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToLengthNode create() {
      return new JSToLengthNodeGen();
   }
}
