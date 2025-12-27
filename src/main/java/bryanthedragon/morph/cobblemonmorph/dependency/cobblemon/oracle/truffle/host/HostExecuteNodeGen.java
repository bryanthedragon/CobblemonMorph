package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(HostExecuteNode.class)
final class HostExecuteNodeGen extends HostExecuteNode {
   private static final HostExecuteNodeGen.Uncached UNCACHED = new HostExecuteNodeGen.Uncached();
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private HostToTypeNode toHost;
   @Node.Child
   private HostContext.ToGuestValueNode toGuest;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile varArgsProfile;
   @Node.Child
   private HostExecuteNode.HostMethodProfileNode hostMethodProfile;
   @CompilerDirectives.CompilationFinal
   private BranchProfile errorBranch;
   @CompilerDirectives.CompilationFinal
   private BranchProfile seenScope;
   @CompilerDirectives.CompilationFinal
   private GuestToHostCodeCache cache;
   @Node.Child
   private HostExecuteNodeGen.FixedData fixed_cache;
   @Node.Child
   private HostExecuteNodeGen.VarArgsData varArgs_cache;
   @Node.Child
   private HostExecuteNodeGen.OverloadedCachedData overloadedCached_cache;

   private HostExecuteNodeGen() {
   }

   @ExplodeLoop
   @Override
   public Object execute(HostMethodDesc arg0Value, Object arg1Value, Object[] arg2Value, HostContext arg3Value) throws UnsupportedTypeException, ArityException {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 7) != 0 && arg0Value instanceof HostMethodDesc.SingleMethod) {
            HostMethodDesc.SingleMethod arg0Value_ = (HostMethodDesc.SingleMethod)arg0Value;
            if ((state_0 & 1) != 0 && !arg0Value_.isVarArgs()) {
               for (HostExecuteNodeGen.FixedData s0_ = this.fixed_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg0Value_ == s0_.cachedMethod_) {
                     return this.doFixed(
                        arg0Value_,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        s0_.cachedMethod_,
                        s0_.toJavaNodes_,
                        s0_.toGuest_,
                        s0_.receiverProfile_,
                        s0_.errorBranch_,
                        s0_.seenDynamicScope_,
                        s0_.cache_
                     );
                  }
               }
            }

            if ((state_0 & 2) != 0 && arg0Value_.isVarArgs()) {
               for (HostExecuteNodeGen.VarArgsData s1_ = this.varArgs_cache; s1_ != null; s1_ = s1_.next_) {
                  if (arg0Value_ == s1_.cachedMethod_) {
                     return this.doVarArgs(
                        arg0Value_,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        s1_.cachedMethod_,
                        s1_.toJavaNode_,
                        s1_.toGuest_,
                        s1_.receiverProfile_,
                        s1_.errorBranch_,
                        s1_.seenDynamicScope_,
                        s1_.asVarArgs_,
                        s1_.cache_
                     );
                  }
               }
            }

            if ((state_0 & 4) != 0) {
               return HostExecuteNode.doSingleUncached(
                  arg0Value_,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  this.toHost,
                  this.toGuest,
                  this.varArgsProfile,
                  this.hostMethodProfile,
                  this.errorBranch,
                  this.seenScope,
                  this.cache
               );
            }
         }

         if ((state_0 & 24) != 0 && arg0Value instanceof HostMethodDesc.OverloadedMethod) {
            HostMethodDesc.OverloadedMethod arg0Value_x = (HostMethodDesc.OverloadedMethod)arg0Value;
            if ((state_0 & 8) != 0) {
               for (HostExecuteNodeGen.OverloadedCachedData s3_ = this.overloadedCached_cache; s3_ != null; s3_ = s3_.next_) {
                  if (arg0Value_x == s3_.cachedMethod_
                     && HostExecuteNode.checkArgTypes(arg2Value, s3_.cachedArgTypes_, s3_.interop_, arg3Value, s3_.asVarArgs_)) {
                     return this.doOverloadedCached(
                        arg0Value_x,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        s3_.cachedMethod_,
                        s3_.toJavaNode_,
                        s3_.toGuest_,
                        s3_.interop_,
                        s3_.cachedArgTypes_,
                        s3_.overload_,
                        s3_.asVarArgs_,
                        s3_.receiverProfile_,
                        s3_.errorBranch_,
                        s3_.seenVariableScope_,
                        s3_.cache_
                     );
                  }
               }
            }

            if ((state_0 & 16) != 0) {
               return this.doOverloadedUncached(
                  arg0Value_x,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  this.toHost,
                  this.toGuest,
                  this.varArgsProfile,
                  this.hostMethodProfile,
                  this.errorBranch,
                  this.seenScope,
                  this.cache
               );
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
   }

   private Object executeAndSpecialize(HostMethodDesc arg0Value, Object arg1Value, Object[] arg2Value, HostContext arg3Value) throws ArityException, UnsupportedTypeException {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         int oldState_0 = state_0;
         int oldCacheCount = this.countCaches();

         try {
            if (arg0Value instanceof HostMethodDesc.SingleMethod) {
               HostMethodDesc.SingleMethod arg0Value_ = (HostMethodDesc.SingleMethod)arg0Value;
               if ((exclude & 1) == 0 && !arg0Value_.isVarArgs()) {
                  int count0_ = 0;
                  HostExecuteNodeGen.FixedData s0_ = this.fixed_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && arg0Value_ != s0_.cachedMethod_) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 3) {
                     s0_ = super.insert(new HostExecuteNodeGen.FixedData(this.fixed_cache));
                     s0_.cachedMethod_ = arg0Value_;
                     s0_.toJavaNodes_ = s0_.insertAccessor(HostExecuteNode.createToHost(arg0Value_.getParameterCount()));
                     s0_.toGuest_ = s0_.insertAccessor(HostContextFactory.ToGuestValueNodeGen.create());
                     s0_.receiverProfile_ = ValueProfile.createClassProfile();
                     s0_.errorBranch_ = BranchProfile.create();
                     s0_.seenDynamicScope_ = BranchProfile.create();
                     s0_.cache_ = arg3Value.getGuestToHostCache();
                     VarHandle.storeStoreFence();
                     this.fixed_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doFixed(
                        arg0Value_,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        s0_.cachedMethod_,
                        s0_.toJavaNodes_,
                        s0_.toGuest_,
                        s0_.receiverProfile_,
                        s0_.errorBranch_,
                        s0_.seenDynamicScope_,
                        s0_.cache_
                     );
                  }
               }

               if ((exclude & 2) == 0 && arg0Value_.isVarArgs()) {
                  int count1_ = 0;
                  HostExecuteNodeGen.VarArgsData s1_ = this.varArgs_cache;
                  if ((state_0 & 2) != 0) {
                     while (s1_ != null && arg0Value_ != s1_.cachedMethod_) {
                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null && count1_ < 3) {
                     s1_ = super.insert(new HostExecuteNodeGen.VarArgsData(this.varArgs_cache));
                     s1_.cachedMethod_ = arg0Value_;
                     s1_.toJavaNode_ = s1_.insertAccessor(HostToTypeNodeGen.create());
                     s1_.toGuest_ = s1_.insertAccessor(HostContextFactory.ToGuestValueNodeGen.create());
                     s1_.receiverProfile_ = ValueProfile.createClassProfile();
                     s1_.errorBranch_ = BranchProfile.create();
                     s1_.seenDynamicScope_ = BranchProfile.create();
                     s1_.asVarArgs_ = HostExecuteNode.asVarArgs(arg2Value, s1_.cachedMethod_, arg3Value);
                     s1_.cache_ = arg3Value.getGuestToHostCache();
                     VarHandle.storeStoreFence();
                     this.varArgs_cache = s1_;
                     this.state_0_ = state_0 |= 2;
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doVarArgs(
                        arg0Value_,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        s1_.cachedMethod_,
                        s1_.toJavaNode_,
                        s1_.toGuest_,
                        s1_.receiverProfile_,
                        s1_.errorBranch_,
                        s1_.seenDynamicScope_,
                        s1_.asVarArgs_,
                        s1_.cache_
                     );
                  }
               }

               this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
               this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
               this.varArgsProfile = this.varArgsProfile == null ? ConditionProfile.create() : this.varArgsProfile;
               this.hostMethodProfile = super.insert(
                  this.hostMethodProfile == null ? HostExecuteNodeGen.HostMethodProfileNodeGen.create() : this.hostMethodProfile
               );
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.seenScope = this.seenScope == null ? BranchProfile.create() : this.seenScope;
               this.cache = this.cache == null ? arg3Value.getGuestToHostCache() : this.cache;
               int var31;
               this.exclude_ = var31 = exclude | 3;
               this.fixed_cache = null;
               this.varArgs_cache = null;
               state_0 &= -4;
               int var28;
               this.state_0_ = var28 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return HostExecuteNode.doSingleUncached(
                  arg0Value_,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  this.toHost,
                  this.toGuest,
                  this.varArgsProfile,
                  this.hostMethodProfile,
                  this.errorBranch,
                  this.seenScope,
                  this.cache
               );
            } else if (!(arg0Value instanceof HostMethodDesc.OverloadedMethod)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
            } else {
               HostMethodDesc.OverloadedMethod arg0Value_x = (HostMethodDesc.OverloadedMethod)arg0Value;
               if ((exclude & 4) == 0) {
                  int count3_ = 0;
                  HostExecuteNodeGen.OverloadedCachedData s3_ = this.overloadedCached_cache;
                  if ((state_0 & 8) != 0) {
                     while (
                        s3_ != null
                           && (
                              arg0Value_x != s3_.cachedMethod_
                                 || !HostExecuteNode.checkArgTypes(arg2Value, s3_.cachedArgTypes_, s3_.interop_, arg3Value, s3_.asVarArgs_)
                           )
                     ) {
                        s3_ = s3_.next_;
                        count3_++;
                     }
                  }

                  if (s3_ == null) {
                     InteropLibrary interop__ = super.insert(INTEROP_LIBRARY_.createDispatched(3));
                     HostExecuteNode.TypeCheckNode[] cachedArgTypes__ = super.insert(HostExecuteNode.createArgTypesArray(arg2Value));
                     HostMethodDesc.SingleMethod overload__ = this.selectOverload(arg0Value_x, arg2Value, arg3Value, cachedArgTypes__);
                     boolean asVarArgs__ = HostExecuteNode.asVarArgs(arg2Value, overload__, arg3Value);
                     if (HostExecuteNode.checkArgTypes(arg2Value, cachedArgTypes__, interop__, arg3Value, asVarArgs__) && count3_ < 3) {
                        s3_ = super.insert(new HostExecuteNodeGen.OverloadedCachedData(this.overloadedCached_cache));
                        s3_.cachedMethod_ = arg0Value_x;
                        s3_.toJavaNode_ = s3_.insertAccessor(HostToTypeNodeGen.create());
                        s3_.toGuest_ = s3_.insertAccessor(HostContextFactory.ToGuestValueNodeGen.create());
                        s3_.interop_ = s3_.insertAccessor(interop__);
                        s3_.cachedArgTypes_ = s3_.insertAccessor(cachedArgTypes__);
                        s3_.overload_ = overload__;
                        s3_.asVarArgs_ = asVarArgs__;
                        s3_.receiverProfile_ = ValueProfile.createClassProfile();
                        s3_.errorBranch_ = BranchProfile.create();
                        s3_.seenVariableScope_ = BranchProfile.create();
                        s3_.cache_ = arg3Value.getGuestToHostCache();
                        VarHandle.storeStoreFence();
                        this.overloadedCached_cache = s3_;
                        this.state_0_ = state_0 |= 8;
                     }
                  }

                  if (s3_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doOverloadedCached(
                        arg0Value_x,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        s3_.cachedMethod_,
                        s3_.toJavaNode_,
                        s3_.toGuest_,
                        s3_.interop_,
                        s3_.cachedArgTypes_,
                        s3_.overload_,
                        s3_.asVarArgs_,
                        s3_.receiverProfile_,
                        s3_.errorBranch_,
                        s3_.seenVariableScope_,
                        s3_.cache_
                     );
                  }
               }

               this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
               this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
               this.varArgsProfile = this.varArgsProfile == null ? ConditionProfile.create() : this.varArgsProfile;
               this.hostMethodProfile = super.insert(
                  this.hostMethodProfile == null ? HostExecuteNodeGen.HostMethodProfileNodeGen.create() : this.hostMethodProfile
               );
               this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
               this.seenScope = this.seenScope == null ? BranchProfile.create() : this.seenScope;
               this.cache = this.cache == null ? arg3Value.getGuestToHostCache() : this.cache;
               int var32;
               this.exclude_ = var32 = exclude | 4;
               this.overloadedCached_cache = null;
               state_0 &= -9;
               int var30;
               this.state_0_ = var30 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doOverloadedUncached(
                  arg0Value_x,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  this.toHost,
                  this.toGuest,
                  this.varArgsProfile,
                  this.hostMethodProfile,
                  this.errorBranch,
                  this.seenScope,
                  this.cache
               );
            }
         } finally {
            if (oldState_0 != 0 || exclude != 0) {
               this.checkForPolymorphicSpecialize(oldState_0, exclude, oldCacheCount);
            }
         }
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }
   }

   private void checkForPolymorphicSpecialize(int oldState_0, int oldExclude, int oldCacheCount) {
      int newState_0 = this.state_0_;
      int newExclude = this.exclude_;
      if ((oldState_0 ^ newState_0) != 0 || (oldExclude ^ newExclude) != 0 || oldCacheCount < this.countCaches()) {
         this.reportPolymorphicSpecialize();
      }
   }

   private int countCaches() {
      int cacheCount = 0;

      for (HostExecuteNodeGen.FixedData s0_ = this.fixed_cache; s0_ != null; s0_ = s0_.next_) {
         cacheCount++;
      }

      for (HostExecuteNodeGen.VarArgsData s1_ = this.varArgs_cache; s1_ != null; s1_ = s1_.next_) {
         cacheCount++;
      }

      for (HostExecuteNodeGen.OverloadedCachedData s3_ = this.overloadedCached_cache; s3_ != null; s3_ = s3_.next_) {
         cacheCount++;
      }

      return cacheCount;
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & state_0 - 1) == 0) {
            HostExecuteNodeGen.FixedData s0_ = this.fixed_cache;
            HostExecuteNodeGen.VarArgsData s1_ = this.varArgs_cache;
            HostExecuteNodeGen.OverloadedCachedData s3_ = this.overloadedCached_cache;
            if ((s0_ == null || s0_.next_ == null) && (s1_ == null || s1_.next_ == null) && (s3_ == null || s3_.next_ == null)) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   public static HostExecuteNode create() {
      return new HostExecuteNodeGen();
   }

   public static HostExecuteNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(HostExecuteNode.class)
   private static final class FixedData extends Node {
      @Node.Child
      HostExecuteNodeGen.FixedData next_;
      @CompilerDirectives.CompilationFinal
      HostMethodDesc.SingleMethod cachedMethod_;
      @Node.Children
      HostToTypeNode[] toJavaNodes_;
      @Node.Child
      HostContext.ToGuestValueNode toGuest_;
      @CompilerDirectives.CompilationFinal
      ValueProfile receiverProfile_;
      @CompilerDirectives.CompilationFinal
      BranchProfile errorBranch_;
      @CompilerDirectives.CompilationFinal
      BranchProfile seenDynamicScope_;
      @CompilerDirectives.CompilationFinal
      GuestToHostCodeCache cache_;

      FixedData(HostExecuteNodeGen.FixedData next_) {
         this.next_ = next_;
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T[] insertAccessor(T[] node) {
         return (T[])super.insert(node);
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(HostExecuteNode.HostMethodProfileNode.class)
   static final class HostMethodProfileNodeGen extends HostExecuteNode.HostMethodProfileNode {
      private static final HostExecuteNodeGen.HostMethodProfileNodeGen.Uncached UNCACHED = new HostExecuteNodeGen.HostMethodProfileNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private int state_0_;
      @CompilerDirectives.CompilationFinal
      private int exclude_;

      private HostMethodProfileNodeGen() {
      }

      @Override
      public HostMethodDesc.SingleMethod execute(HostMethodDesc.SingleMethod arg0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0 && arg0Value instanceof HostMethodDesc.SingleMethod.MHBase) {
            HostMethodDesc.SingleMethod.MHBase arg0Value_ = (HostMethodDesc.SingleMethod.MHBase)arg0Value;
            return HostExecuteNode.HostMethodProfileNode.mono(arg0Value_);
         } else if ((state_0 & 2) != 0 && arg0Value instanceof HostMethodDesc.SingleMethod.ReflectBase) {
            HostMethodDesc.SingleMethod.ReflectBase arg0Value_ = (HostMethodDesc.SingleMethod.ReflectBase)arg0Value;
            return HostExecuteNode.HostMethodProfileNode.mono(arg0Value_);
         } else if ((state_0 & 4) != 0) {
            return HostExecuteNode.HostMethodProfileNode.poly(arg0Value);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }

      private HostMethodDesc.SingleMethod executeAndSpecialize(HostMethodDesc.SingleMethod arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         HostMethodDesc.SingleMethod var7;
         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if ((exclude & 1) == 0 && arg0Value instanceof HostMethodDesc.SingleMethod.MHBase) {
               HostMethodDesc.SingleMethod.MHBase arg0Value_ = (HostMethodDesc.SingleMethod.MHBase)arg0Value;
               int var14;
               this.state_0_ = var14 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return HostExecuteNode.HostMethodProfileNode.mono(arg0Value_);
            }

            if ((exclude & 2) != 0 || !(arg0Value instanceof HostMethodDesc.SingleMethod.ReflectBase)) {
               int var15;
               this.exclude_ = var15 = exclude | 3;
               state_0 &= -4;
               int var13;
               this.state_0_ = var13 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return HostExecuteNode.HostMethodProfileNode.poly(arg0Value);
            }

            HostMethodDesc.SingleMethod.ReflectBase arg0Value_ = (HostMethodDesc.SingleMethod.ReflectBase)arg0Value;
            int var11;
            this.state_0_ = var11 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            var7 = HostExecuteNode.HostMethodProfileNode.mono(arg0Value_);
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

      public static HostExecuteNode.HostMethodProfileNode create() {
         return new HostExecuteNodeGen.HostMethodProfileNodeGen();
      }

      public static HostExecuteNode.HostMethodProfileNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostExecuteNode.HostMethodProfileNode.class)
      @DenyReplace
      private static final class Uncached extends HostExecuteNode.HostMethodProfileNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public HostMethodDesc.SingleMethod execute(HostMethodDesc.SingleMethod arg0Value) {
            return HostExecuteNode.HostMethodProfileNode.poly(arg0Value);
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

   @GeneratedBy(HostExecuteNode.class)
   private static final class OverloadedCachedData extends Node {
      @Node.Child
      HostExecuteNodeGen.OverloadedCachedData next_;
      @CompilerDirectives.CompilationFinal
      HostMethodDesc.OverloadedMethod cachedMethod_;
      @Node.Child
      HostToTypeNode toJavaNode_;
      @Node.Child
      HostContext.ToGuestValueNode toGuest_;
      @Node.Child
      InteropLibrary interop_;
      @Node.Children
      HostExecuteNode.TypeCheckNode[] cachedArgTypes_;
      @CompilerDirectives.CompilationFinal
      HostMethodDesc.SingleMethod overload_;
      @CompilerDirectives.CompilationFinal
      boolean asVarArgs_;
      @CompilerDirectives.CompilationFinal
      ValueProfile receiverProfile_;
      @CompilerDirectives.CompilationFinal
      BranchProfile errorBranch_;
      @CompilerDirectives.CompilationFinal
      BranchProfile seenVariableScope_;
      @CompilerDirectives.CompilationFinal
      GuestToHostCodeCache cache_;

      OverloadedCachedData(HostExecuteNodeGen.OverloadedCachedData next_) {
         this.next_ = next_;
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T[] insertAccessor(T[] node) {
         return (T[])super.insert(node);
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(HostExecuteNode.class)
   @DenyReplace
   private static final class Uncached extends HostExecuteNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public Object execute(HostMethodDesc arg0Value, Object arg1Value, Object[] arg2Value, HostContext arg3Value) throws UnsupportedTypeException, ArityException {
         if (arg0Value instanceof HostMethodDesc.SingleMethod) {
            HostMethodDesc.SingleMethod arg0Value_ = (HostMethodDesc.SingleMethod)arg0Value;
            return HostExecuteNode.doSingleUncached(
               arg0Value_,
               arg1Value,
               arg2Value,
               arg3Value,
               HostToTypeNodeGen.getUncached(),
               HostContextFactory.ToGuestValueNodeGen.getUncached(),
               ConditionProfile.getUncached(),
               HostExecuteNodeGen.HostMethodProfileNodeGen.getUncached(),
               BranchProfile.getUncached(),
               BranchProfile.getUncached(),
               arg3Value.getGuestToHostCache()
            );
         } else if (arg0Value instanceof HostMethodDesc.OverloadedMethod) {
            HostMethodDesc.OverloadedMethod arg0Value_ = (HostMethodDesc.OverloadedMethod)arg0Value;
            return this.doOverloadedUncached(
               arg0Value_,
               arg1Value,
               arg2Value,
               arg3Value,
               HostToTypeNodeGen.getUncached(),
               HostContextFactory.ToGuestValueNodeGen.getUncached(),
               ConditionProfile.getUncached(),
               HostExecuteNodeGen.HostMethodProfileNodeGen.getUncached(),
               BranchProfile.getUncached(),
               BranchProfile.getUncached(),
               arg3Value.getGuestToHostCache()
            );
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
         }
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

   @GeneratedBy(HostExecuteNode.class)
   private static final class VarArgsData extends Node {
      @Node.Child
      HostExecuteNodeGen.VarArgsData next_;
      @CompilerDirectives.CompilationFinal
      HostMethodDesc.SingleMethod cachedMethod_;
      @Node.Child
      HostToTypeNode toJavaNode_;
      @Node.Child
      HostContext.ToGuestValueNode toGuest_;
      @CompilerDirectives.CompilationFinal
      ValueProfile receiverProfile_;
      @CompilerDirectives.CompilationFinal
      BranchProfile errorBranch_;
      @CompilerDirectives.CompilationFinal
      BranchProfile seenDynamicScope_;
      @CompilerDirectives.CompilationFinal
      boolean asVarArgs_;
      @CompilerDirectives.CompilationFinal
      GuestToHostCodeCache cache_;

      VarArgsData(HostExecuteNodeGen.VarArgsData next_) {
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
}
