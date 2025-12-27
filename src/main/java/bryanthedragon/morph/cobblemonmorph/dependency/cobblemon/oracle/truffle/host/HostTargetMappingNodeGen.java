package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(HostTargetMappingNode.class)
final class HostTargetMappingNodeGen extends HostTargetMappingNode {
   private static final HostTargetMappingNodeGen.Uncached UNCACHED = new HostTargetMappingNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private HostTargetMappingNodeGen.CachedData cached_cache;

   private HostTargetMappingNodeGen() {
   }

   @Override
   Object execute(Object arg0Value, Class<?> arg1Value, HostContext arg2Value, InteropLibrary arg3Value, boolean arg4Value, int arg5Value, int arg6Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            HostTargetMappingNodeGen.CachedData s0_ = this.cached_cache;
            if (s0_ != null && arg1Value != null) {
               return this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.mappings_, s0_.mappingNodes_);
            }
         }

         if ((state_0 & 2) != 0) {
            return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
   }

   private Object executeAndSpecialize(
      Object arg0Value, Class<?> arg1Value, HostContext arg2Value, InteropLibrary arg3Value, boolean arg4Value, int arg5Value, int arg6Value
   ) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var13;
      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude != 0 || arg1Value == null) {
            int var20;
            this.exclude_ = var20 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var19;
            this.state_0_ = var19 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
         }

         HostTargetMappingNodeGen.CachedData s0_ = super.insert(new HostTargetMappingNodeGen.CachedData());
         s0_.mappings_ = HostTargetMappingNode.getMappings(arg2Value, arg1Value);
         s0_.mappingNodes_ = s0_.insertAccessor(HostTargetMappingNode.createMappingNodes(s0_.mappings_));
         VarHandle.storeStoreFence();
         this.cached_cache = s0_;
         int var17;
         this.state_0_ = var17 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var13 = this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.mappings_, s0_.mappingNodes_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var13;
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

   public static HostTargetMappingNode create() {
      return new HostTargetMappingNodeGen();
   }

   public static HostTargetMappingNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(HostTargetMappingNode.class)
   private static final class CachedData extends Node {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      HostTargetMapping[] mappings_;
      @Node.Children
      HostTargetMappingNode.SingleMappingNode[] mappingNodes_;

      CachedData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T[] insertAccessor(T[] node) {
         return (T[])super.insert(node);
      }
   }

   @GeneratedBy(HostTargetMappingNode.SingleMappingNode.class)
   static final class SingleMappingNodeGen extends HostTargetMappingNode.SingleMappingNode {
      private static final HostTargetMappingNodeGen.SingleMappingNodeGen.Uncached UNCACHED = new HostTargetMappingNodeGen.SingleMappingNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private HostTargetMappingNodeGen.SingleMappingNodeGen.DefaultData default_cache;

      private SingleMappingNodeGen() {
      }

      @Override
      Object execute(Object arg0Value, HostTargetMapping arg1Value, HostContext arg2Value, InteropLibrary arg3Value, boolean arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            HostTargetMappingNodeGen.SingleMappingNodeGen.DefaultData s0_ = this.default_cache;
            if (s0_ != null) {
               return this.doDefault(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.acceptsProfile_, s0_.allowsImplementation_, s0_.toHostRecursive_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private Object executeAndSpecialize(Object arg0Value, HostTargetMapping arg1Value, HostContext arg2Value, InteropLibrary arg3Value, boolean arg4Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var10;
         try {
            int state_0 = this.state_0_;
            HostTargetMappingNodeGen.SingleMappingNodeGen.DefaultData s0_ = super.insert(new HostTargetMappingNodeGen.SingleMappingNodeGen.DefaultData());
            s0_.acceptsProfile_ = ConditionProfile.create();
            s0_.allowsImplementation_ = HostTargetMappingNode.SingleMappingNode.allowsImplementation(arg2Value, arg1Value.sourceType);
            s0_.toHostRecursive_ = s0_.insertAccessor(HostToTypeNodeGen.create());
            VarHandle.storeStoreFence();
            this.default_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var10 = this.doDefault(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.acceptsProfile_, s0_.allowsImplementation_, s0_.toHostRecursive_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var10;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static HostTargetMappingNode.SingleMappingNode create() {
         return new HostTargetMappingNodeGen.SingleMappingNodeGen();
      }

      public static HostTargetMappingNode.SingleMappingNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostTargetMappingNode.SingleMappingNode.class)
      private static final class DefaultData extends Node {
         @CompilerDirectives.CompilationFinal
         ConditionProfile acceptsProfile_;
         @CompilerDirectives.CompilationFinal
         boolean allowsImplementation_;
         @Node.Child
         HostToTypeNode toHostRecursive_;

         DefaultData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(HostTargetMappingNode.SingleMappingNode.class)
      @DenyReplace
      private static final class Uncached extends HostTargetMappingNode.SingleMappingNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         Object execute(Object arg0Value, HostTargetMapping arg1Value, HostContext arg2Value, InteropLibrary arg3Value, boolean arg4Value) {
            return this.doDefault(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               ConditionProfile.getUncached(),
               HostTargetMappingNode.SingleMappingNode.allowsImplementation(arg2Value, arg1Value.sourceType),
               HostToTypeNodeGen.getUncached()
            );
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

   @GeneratedBy(HostTargetMappingNode.class)
   @DenyReplace
   private static final class Uncached extends HostTargetMappingNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      Object execute(Object arg0Value, Class<?> arg1Value, HostContext arg2Value, InteropLibrary arg3Value, boolean arg4Value, int arg5Value, int arg6Value) {
         return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
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
