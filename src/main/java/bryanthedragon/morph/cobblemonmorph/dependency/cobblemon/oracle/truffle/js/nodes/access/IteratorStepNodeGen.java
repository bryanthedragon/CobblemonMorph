package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IteratorStepNode.class)
public final class IteratorStepNodeGen extends IteratorStepNode implements Introspection.Provider {
   private static final IteratorStepNodeGen.Uncached UNCACHED = new IteratorStepNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private IteratorNextNode iteratorNextNode_;
   @Node.Child
   private IteratorCompleteNode iteratorCompleteNode_;

   private IteratorStepNodeGen() {
   }

   @Override
   public Object execute(IteratorRecord arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         return IteratorStepNode.step(arg0Value, this.iteratorNextNode_, this.iteratorCompleteNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private Object executeAndSpecialize(IteratorRecord arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var5;
      try {
         int state_0 = this.state_0_;
         this.iteratorNextNode_ = super.insert(IteratorNextNode.create());
         this.iteratorCompleteNode_ = super.insert(IteratorCompleteNode.create(this.getLanguage().getJSContext()));
         int var9;
         this.state_0_ = var9 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var5 = IteratorStepNode.step(arg0Value, this.iteratorNextNode_, this.iteratorCompleteNode_);
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
      Object[] s = new Object[]{"step", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.iteratorNextNode_, this.iteratorCompleteNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static IteratorStepNode create() {
      return new IteratorStepNodeGen();
   }

   public static IteratorStepNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(IteratorStepNode.class)
   @DenyReplace
   private static final class Uncached extends IteratorStepNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public Object execute(IteratorRecord arg0Value) {
         return IteratorStepNode.step(arg0Value, IteratorNextNode.getUncached(), IteratorCompleteNode.getUncached());
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
