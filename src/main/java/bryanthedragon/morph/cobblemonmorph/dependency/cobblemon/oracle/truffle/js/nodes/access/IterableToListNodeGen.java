package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IterableToListNode.class)
public final class IterableToListNodeGen extends IterableToListNode implements Introspection.Provider {
   private static final IterableToListNodeGen.Uncached UNCACHED = new IterableToListNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private IterableToListNodeGen.IterableToListData iterableToList_cache;

   private IterableToListNodeGen() {
   }

   @Override
   public SimpleArrayList<Object> execute(IteratorRecord arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         IterableToListNodeGen.IterableToListData s0_ = this.iterableToList_cache;
         if (s0_ != null) {
            return IterableToListNode.iterableToList(arg0Value, s0_.iteratorStepNode_, s0_.getIteratorValueNode_, s0_.growProfile_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   private SimpleArrayList<Object> executeAndSpecialize(IteratorRecord arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      SimpleArrayList var6;
      try {
         int state_0 = this.state_0_;
         IterableToListNodeGen.IterableToListData s0_ = super.insert(new IterableToListNodeGen.IterableToListData());
         s0_.iteratorStepNode_ = s0_.insertAccessor(IteratorStepNode.create());
         s0_.getIteratorValueNode_ = s0_.insertAccessor(IteratorValueNode.create());
         s0_.growProfile_ = BranchProfile.create();
         VarHandle.storeStoreFence();
         this.iterableToList_cache = s0_;
         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var6 = IterableToListNode.iterableToList(arg0Value, s0_.iteratorStepNode_, s0_.getIteratorValueNode_, s0_.growProfile_);
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
      Object[] s = new Object[]{"iterableToList", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         IterableToListNodeGen.IterableToListData s0_ = this.iterableToList_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.iteratorStepNode_, s0_.getIteratorValueNode_, s0_.growProfile_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static IterableToListNode create() {
      return new IterableToListNodeGen();
   }

   public static IterableToListNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(IterableToListNode.class)
   private static final class IterableToListData extends Node {
      @Node.Child
      IteratorStepNode iteratorStepNode_;
      @Node.Child
      IteratorValueNode getIteratorValueNode_;
      @CompilerDirectives.CompilationFinal
      BranchProfile growProfile_;

      IterableToListData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(IterableToListNode.class)
   @DenyReplace
   private static final class Uncached extends IterableToListNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public SimpleArrayList<Object> execute(IteratorRecord arg0Value) {
         return IterableToListNode.iterableToList(arg0Value, IteratorStepNode.getUncached(), IteratorValueNode.getUncached(), BranchProfile.getUncached());
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
