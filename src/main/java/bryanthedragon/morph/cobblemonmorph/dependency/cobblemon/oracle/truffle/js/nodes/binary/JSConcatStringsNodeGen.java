package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSConcatStringsNode.class)
public final class JSConcatStringsNodeGen extends JSConcatStringsNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private BranchProfile errorBranch_;
   @Node.Child
   private TruffleString.ConcatNode concatNode_;

   private JSConcatStringsNodeGen(int stringLengthLimit) {
      super(stringLengthLimit);
   }

   @Override
   public TruffleString executeTString(TruffleString arg0Value, TruffleString arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         return this.doConcat(arg0Value, arg1Value, this.errorBranch_, this.concatNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }
   }

   private TruffleString executeAndSpecialize(TruffleString arg0Value, TruffleString arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      TruffleString var6;
      try {
         int state_0 = this.state_0_;
         this.errorBranch_ = BranchProfile.create();
         this.concatNode_ = super.insert(TruffleString.ConcatNode.create());
         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var6 = this.doConcat(arg0Value, arg1Value, this.errorBranch_, this.concatNode_);
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
      Object[] s = new Object[]{"doConcat", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.errorBranch_, this.concatNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static JSConcatStringsNode create(int stringLengthLimit) {
      return new JSConcatStringsNodeGen(stringLengthLimit);
   }
}
