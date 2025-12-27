package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Type;
import java.util.concurrent.locks.Lock;

@GeneratedBy(HostToTypeNode.class)
final class HostToTypeNodeGen extends HostToTypeNode {
   private static final HostToTypeNodeGen.Uncached UNCACHED = new HostToTypeNodeGen.Uncached();
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private HostToTypeNodeGen.CachedData cached_cache;

   private HostToTypeNodeGen() {
   }

   @ExplodeLoop
   @Override
   public Object execute(HostContext arg0Value, Object arg1Value, Class<?> arg2Value, Type arg3Value, boolean arg4Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (HostToTypeNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.interop_.accepts(arg1Value) && arg2Value == s0_.cachedTargetType_) {
                  return this.doCached(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     s0_.interop_,
                     s0_.cachedTargetType_,
                     s0_.primitiveTarget_,
                     s0_.allowsImplementation_,
                     s0_.targetMapping_,
                     s0_.error_
                  );
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return HostToTypeNode.doGeneric(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
   }

   private Object executeAndSpecialize(HostContext arg0Value, Object arg1Value, Class<?> arg2Value, Type arg3Value, boolean arg4Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude == 0) {
            int count0_ = 0;
            HostToTypeNodeGen.CachedData s0_ = this.cached_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null && (!s0_.interop_.accepts(arg1Value) || arg2Value != s0_.cachedTargetType_)) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null && count0_ < 5) {
               s0_ = super.insert(new HostToTypeNodeGen.CachedData(this.cached_cache));
               s0_.interop_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
               s0_.cachedTargetType_ = arg2Value;
               s0_.primitiveTarget_ = HostToTypeNode.isPrimitiveTarget(s0_.cachedTargetType_);
               s0_.allowsImplementation_ = HostToTypeNode.allowsImplementation(arg0Value, arg2Value);
               s0_.targetMapping_ = s0_.insertAccessor(HostTargetMappingNode.create());
               s0_.error_ = BranchProfile.create();
               VarHandle.storeStoreFence();
               this.cached_cache = s0_;
               this.state_0_ = state_0 |= 1;
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               return this.doCached(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  s0_.interop_,
                  s0_.cachedTargetType_,
                  s0_.primitiveTarget_,
                  s0_.allowsImplementation_,
                  s0_.targetMapping_,
                  s0_.error_
               );
            }
         }

         int var18;
         this.exclude_ = var18 = exclude | 1;
         this.cached_cache = null;
         state_0 &= -2;
         int var17;
         this.state_0_ = var17 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         return HostToTypeNode.doGeneric(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & state_0 - 1) == 0) {
            HostToTypeNodeGen.CachedData s0_ = this.cached_cache;
            if (s0_ == null || s0_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   public static HostToTypeNode create() {
      return new HostToTypeNodeGen();
   }

   public static HostToTypeNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(HostToTypeNode.class)
   private static final class CachedData extends Node {
      @Node.Child
      HostToTypeNodeGen.CachedData next_;
      @Node.Child
      InteropLibrary interop_;
      @CompilerDirectives.CompilationFinal
      Class<?> cachedTargetType_;
      @CompilerDirectives.CompilationFinal
      boolean primitiveTarget_;
      @CompilerDirectives.CompilationFinal
      boolean allowsImplementation_;
      @Node.Child
      HostTargetMappingNode targetMapping_;
      @CompilerDirectives.CompilationFinal
      BranchProfile error_;

      CachedData(HostToTypeNodeGen.CachedData next_) {
         this.next_ = next_;
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(HostToTypeNode.class)
   @DenyReplace
   private static final class Uncached extends HostToTypeNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public Object execute(HostContext arg0Value, Object arg1Value, Class<?> arg2Value, Type arg3Value, boolean arg4Value) {
         return HostToTypeNode.doGeneric(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
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
