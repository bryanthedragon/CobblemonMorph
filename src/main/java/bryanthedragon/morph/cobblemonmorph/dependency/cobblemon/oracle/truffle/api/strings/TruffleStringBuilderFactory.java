package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TruffleStringBuilder.class)
public final class TruffleStringBuilderFactory {
   @GeneratedBy(TruffleStringBuilder.AppendArrayIntlNode.class)
   static final class AppendArrayIntlNodeGen extends TruffleStringBuilder.AppendArrayIntlNode {
      private static final TruffleStringBuilderFactory.AppendArrayIntlNodeGen.Uncached UNCACHED = new TruffleStringBuilderFactory.AppendArrayIntlNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private TruffleStringBuilderFactory.AppendArrayIntlNodeGen.CachedData cached_cache;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile uncached_bufferGrowProfile_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile uncached_errorProfile_;

      private AppendArrayIntlNodeGen() {
      }

      @ExplodeLoop
      @Override
      void execute(TruffleStringBuilder arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, int arg5Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (TruffleStringBuilderFactory.AppendArrayIntlNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg0Value.stride == s0_.cachedStrideSB_ && arg4Value == s0_.cachedStrideA_ && arg5Value == s0_.cachedStrideNew_) {
                     this.doCached(
                        arg0Value,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        arg4Value,
                        arg5Value,
                        s0_.cachedStrideSB_,
                        s0_.cachedStrideA_,
                        s0_.cachedStrideNew_,
                        s0_.bufferGrowProfile_,
                        s0_.errorProfile_
                     );
                     return;
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.uncached_bufferGrowProfile_, this.uncached_errorProfile_);
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private void executeAndSpecialize(TruffleStringBuilder arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, int arg5Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               TruffleStringBuilderFactory.AppendArrayIntlNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (arg0Value.stride != s0_.cachedStrideSB_ || arg4Value != s0_.cachedStrideA_ || arg5Value != s0_.cachedStrideNew_)) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < 9) {
                  s0_ = new TruffleStringBuilderFactory.AppendArrayIntlNodeGen.CachedData(this.cached_cache);
                  s0_.cachedStrideSB_ = arg0Value.stride;
                  s0_.cachedStrideA_ = arg4Value;
                  s0_.cachedStrideNew_ = arg5Value;
                  s0_.bufferGrowProfile_ = ConditionProfile.create();
                  s0_.errorProfile_ = BranchProfile.create();
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  this.doCached(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     s0_.cachedStrideSB_,
                     s0_.cachedStrideA_,
                     s0_.cachedStrideNew_,
                     s0_.bufferGrowProfile_,
                     s0_.errorProfile_
                  );
                  return;
               }
            }

            this.uncached_bufferGrowProfile_ = ConditionProfile.create();
            this.uncached_errorProfile_ = BranchProfile.create();
            int var18;
            this.exclude_ = var18 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var17;
            this.state_0_ = var17 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.uncached_bufferGrowProfile_, this.uncached_errorProfile_);
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
               TruffleStringBuilderFactory.AppendArrayIntlNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TruffleStringBuilder.AppendArrayIntlNode create() {
         return new TruffleStringBuilderFactory.AppendArrayIntlNodeGen();
      }

      public static TruffleStringBuilder.AppendArrayIntlNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleStringBuilder.AppendArrayIntlNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         TruffleStringBuilderFactory.AppendArrayIntlNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideSB_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideA_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideNew_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile bufferGrowProfile_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorProfile_;

         CachedData(TruffleStringBuilderFactory.AppendArrayIntlNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TruffleStringBuilder.AppendArrayIntlNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleStringBuilder.AppendArrayIntlNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         void execute(TruffleStringBuilder arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, int arg5Value) {
            this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), BranchProfile.getUncached());
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

   @GeneratedBy(TruffleStringBuilder.AppendByteNode.class)
   static final class AppendByteNodeGen extends TruffleStringBuilder.AppendByteNode {
      private static final TruffleStringBuilderFactory.AppendByteNodeGen.Uncached UNCACHED = new TruffleStringBuilderFactory.AppendByteNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile bufferGrowProfile_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile errorProfile_;

      private AppendByteNodeGen() {
      }

      @Override
      public void execute(TruffleStringBuilder arg0Value, byte arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringBuilder.AppendByteNode.append(arg0Value, arg1Value, this.bufferGrowProfile_, this.errorProfile_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private void executeAndSpecialize(TruffleStringBuilder arg0Value, byte arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            this.bufferGrowProfile_ = ConditionProfile.create();
            this.errorProfile_ = BranchProfile.create();
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            TruffleStringBuilder.AppendByteNode.append(arg0Value, arg1Value, this.bufferGrowProfile_, this.errorProfile_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleStringBuilder.AppendByteNode create() {
         return new TruffleStringBuilderFactory.AppendByteNodeGen();
      }

      public static TruffleStringBuilder.AppendByteNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleStringBuilder.AppendByteNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleStringBuilder.AppendByteNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public void execute(TruffleStringBuilder arg0Value, byte arg1Value) {
            TruffleStringBuilder.AppendByteNode.append(arg0Value, arg1Value, ConditionProfile.getUncached(), BranchProfile.getUncached());
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

   @GeneratedBy(TruffleStringBuilder.AppendCharUTF16Node.class)
   static final class AppendCharUTF16NodeGen extends TruffleStringBuilder.AppendCharUTF16Node {
      private static final TruffleStringBuilderFactory.AppendCharUTF16NodeGen.Uncached UNCACHED = new TruffleStringBuilderFactory.AppendCharUTF16NodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private TruffleStringBuilderFactory.AppendCharUTF16NodeGen.CachedData cached_cache;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile uncached_bufferGrowProfile_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile uncached_errorProfile_;

      private AppendCharUTF16NodeGen() {
      }

      @ExplodeLoop
      @Override
      public void execute(TruffleStringBuilder arg0Value, char arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (TruffleStringBuilderFactory.AppendCharUTF16NodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.cachedCurStride_ == arg0Value.stride && s0_.cachedNewStride_ == TruffleStringBuilder.utf16Stride(arg0Value, arg1Value)) {
                     this.doCached(arg0Value, arg1Value, s0_.cachedCurStride_, s0_.cachedNewStride_, s0_.bufferGrowProfile_, s0_.errorProfile_);
                     return;
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               this.doUncached(arg0Value, arg1Value, this.uncached_bufferGrowProfile_, this.uncached_errorProfile_);
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private void executeAndSpecialize(TruffleStringBuilder arg0Value, char arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               TruffleStringBuilderFactory.AppendCharUTF16NodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (
                     s0_ != null
                        && (s0_.cachedCurStride_ != arg0Value.stride || s0_.cachedNewStride_ != TruffleStringBuilder.utf16Stride(arg0Value, arg1Value))
                  ) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  int cachedNewStride__ = TruffleStringBuilder.utf16Stride(arg0Value, arg1Value);
                  if (cachedNewStride__ == TruffleStringBuilder.utf16Stride(arg0Value, arg1Value) && count0_ < 9) {
                     s0_ = new TruffleStringBuilderFactory.AppendCharUTF16NodeGen.CachedData(this.cached_cache);
                     s0_.cachedCurStride_ = arg0Value.stride;
                     s0_.cachedNewStride_ = cachedNewStride__;
                     s0_.bufferGrowProfile_ = ConditionProfile.create();
                     s0_.errorProfile_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  this.doCached(arg0Value, arg1Value, s0_.cachedCurStride_, s0_.cachedNewStride_, s0_.bufferGrowProfile_, s0_.errorProfile_);
                  return;
               }
            }

            this.uncached_bufferGrowProfile_ = ConditionProfile.create();
            this.uncached_errorProfile_ = BranchProfile.create();
            int var15;
            this.exclude_ = var15 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var14;
            this.state_0_ = var14 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            this.doUncached(arg0Value, arg1Value, this.uncached_bufferGrowProfile_, this.uncached_errorProfile_);
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
               TruffleStringBuilderFactory.AppendCharUTF16NodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TruffleStringBuilder.AppendCharUTF16Node create() {
         return new TruffleStringBuilderFactory.AppendCharUTF16NodeGen();
      }

      public static TruffleStringBuilder.AppendCharUTF16Node getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleStringBuilder.AppendCharUTF16Node.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         TruffleStringBuilderFactory.AppendCharUTF16NodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         int cachedCurStride_;
         @CompilerDirectives.CompilationFinal
         int cachedNewStride_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile bufferGrowProfile_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorProfile_;

         CachedData(TruffleStringBuilderFactory.AppendCharUTF16NodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TruffleStringBuilder.AppendCharUTF16Node.class)
      @DenyReplace
      private static final class Uncached extends TruffleStringBuilder.AppendCharUTF16Node {
         @CompilerDirectives.TruffleBoundary
         @Override
         public void execute(TruffleStringBuilder arg0Value, char arg1Value) {
            this.doUncached(arg0Value, arg1Value, ConditionProfile.getUncached(), BranchProfile.getUncached());
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

   @GeneratedBy(TruffleStringBuilder.AppendCodePointIntlNode.class)
   static final class AppendCodePointIntlNodeGen extends TruffleStringBuilder.AppendCodePointIntlNode {
      private static final TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Uncached UNCACHED = new TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf16CachedData utf16Cached_cache;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf16Uncached_bmpProfile_;
      @CompilerDirectives.CompilationFinal
      private TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf32CachedData utf32Cached_cache;

      private AppendCodePointIntlNodeGen() {
      }

      @ExplodeLoop
      @Override
      void execute(
         TruffleStringBuilder arg0Value,
         int arg1Value,
         TruffleString.Encoding arg2Value,
         int arg3Value,
         boolean arg4Value,
         ConditionProfile arg5Value,
         BranchProfile arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isAsciiBytesOrLatin1(arg2Value)) {
               TruffleStringBuilder.AppendCodePointIntlNode.bytes(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
               return;
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUTF8(arg2Value)) {
               TruffleStringBuilder.AppendCodePointIntlNode.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
               return;
            }

            if ((state_0 & 4) != 0 && TStringGuards.isUTF16(arg2Value)) {
               for (TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf16CachedData s2_ = this.utf16Cached_cache; s2_ != null; s2_ = s2_.next_) {
                  if (s2_.cachedCurStride_ == arg0Value.stride && s2_.cachedNewStride_ == TruffleStringBuilder.utf16Stride(arg0Value, arg1Value)) {
                     this.utf16Cached(
                        arg0Value,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        arg4Value,
                        arg5Value,
                        arg6Value,
                        s2_.cachedCurStride_,
                        s2_.cachedNewStride_,
                        s2_.bmpProfile_
                     );
                     return;
                  }
               }
            }

            if ((state_0 & 8) != 0 && TStringGuards.isUTF16(arg2Value)) {
               this.utf16Uncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.utf16Uncached_bmpProfile_);
               return;
            }

            if ((state_0 & 16) != 0 && TStringGuards.isUTF32(arg2Value)) {
               for (TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf32CachedData s4_ = this.utf32Cached_cache; s4_ != null; s4_ = s4_.next_) {
                  if (s4_.cachedCurStride_ == arg0Value.stride && s4_.cachedNewStride_ == TruffleStringBuilder.utf32Stride(arg0Value, arg1Value)) {
                     this.utf32Cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s4_.cachedCurStride_, s4_.cachedNewStride_);
                     return;
                  }
               }
            }

            if ((state_0 & 32) != 0 && TStringGuards.isUTF32(arg2Value)) {
               this.utf32Uncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
               return;
            }

            if ((state_0 & 64) != 0 && TStringGuards.isUnsupportedEncoding(arg2Value)) {
               TruffleStringBuilder.AppendCodePointIntlNode.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private void executeAndSpecialize(
         TruffleStringBuilder arg0Value,
         int arg1Value,
         TruffleString.Encoding arg2Value,
         int arg3Value,
         boolean arg4Value,
         ConditionProfile arg5Value,
         BranchProfile arg6Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (TStringGuards.isAsciiBytesOrLatin1(arg2Value)) {
               int var24;
               this.state_0_ = var24 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               TruffleStringBuilder.AppendCodePointIntlNode.bytes(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUTF8(arg2Value)) {
               int var23;
               this.state_0_ = var23 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               TruffleStringBuilder.AppendCodePointIntlNode.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else {
               if ((exclude & 1) == 0 && TStringGuards.isUTF16(arg2Value)) {
                  int count2_ = 0;
                  TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf16CachedData s2_ = this.utf16Cached_cache;
                  if ((state_0 & 4) != 0) {
                     while (
                        s2_ != null
                           && (s2_.cachedCurStride_ != arg0Value.stride || s2_.cachedNewStride_ != TruffleStringBuilder.utf16Stride(arg0Value, arg1Value))
                     ) {
                        s2_ = s2_.next_;
                        count2_++;
                     }
                  }

                  if (s2_ == null) {
                     int cachedNewStride__ = TruffleStringBuilder.utf16Stride(arg0Value, arg1Value);
                     if (cachedNewStride__ == TruffleStringBuilder.utf16Stride(arg0Value, arg1Value) && count2_ < 9) {
                        s2_ = new TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf16CachedData(this.utf16Cached_cache);
                        s2_.cachedCurStride_ = arg0Value.stride;
                        s2_.cachedNewStride_ = cachedNewStride__;
                        s2_.bmpProfile_ = ConditionProfile.create();
                        VarHandle.storeStoreFence();
                        this.utf16Cached_cache = s2_;
                        this.state_0_ = state_0 |= 4;
                     }
                  }

                  if (s2_ != null) {
                     lock.unlock();
                     hasLock = false;
                     this.utf16Cached(
                        arg0Value,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        arg4Value,
                        arg5Value,
                        arg6Value,
                        s2_.cachedCurStride_,
                        s2_.cachedNewStride_,
                        s2_.bmpProfile_
                     );
                     return;
                  }
               }

               if (TStringGuards.isUTF16(arg2Value)) {
                  this.utf16Uncached_bmpProfile_ = ConditionProfile.create();
                  int var26;
                  this.exclude_ = var26 = exclude | 1;
                  this.utf16Cached_cache = null;
                  state_0 &= -5;
                  int var22;
                  this.state_0_ = var22 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  this.utf16Uncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.utf16Uncached_bmpProfile_);
               } else {
                  if ((exclude & 2) == 0 && TStringGuards.isUTF32(arg2Value)) {
                     int count4_ = 0;
                     TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf32CachedData s4_ = this.utf32Cached_cache;
                     if ((state_0 & 16) != 0) {
                        while (
                           s4_ != null
                              && (s4_.cachedCurStride_ != arg0Value.stride || s4_.cachedNewStride_ != TruffleStringBuilder.utf32Stride(arg0Value, arg1Value))
                        ) {
                           s4_ = s4_.next_;
                           count4_++;
                        }
                     }

                     if (s4_ == null) {
                        int cachedNewStride__1 = TruffleStringBuilder.utf32Stride(arg0Value, arg1Value);
                        if (cachedNewStride__1 == TruffleStringBuilder.utf32Stride(arg0Value, arg1Value) && count4_ < 9) {
                           s4_ = new TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf32CachedData(this.utf32Cached_cache);
                           s4_.cachedCurStride_ = arg0Value.stride;
                           s4_.cachedNewStride_ = cachedNewStride__1;
                           VarHandle.storeStoreFence();
                           this.utf32Cached_cache = s4_;
                           this.state_0_ = state_0 |= 16;
                        }
                     }

                     if (s4_ != null) {
                        lock.unlock();
                        hasLock = false;
                        this.utf32Cached(
                           arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s4_.cachedCurStride_, s4_.cachedNewStride_
                        );
                        return;
                     }
                  }

                  if (TStringGuards.isUTF32(arg2Value)) {
                     int var25;
                     this.exclude_ = var25 = exclude | 2;
                     this.utf32Cached_cache = null;
                     state_0 &= -17;
                     int var19;
                     this.state_0_ = var19 = state_0 | 32;
                     lock.unlock();
                     hasLock = false;
                     this.utf32Uncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                  } else if (!TStringGuards.isUnsupportedEncoding(arg2Value)) {
                     throw new UnsupportedSpecializationException(
                        this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
                     );
                  } else {
                     int var20;
                     this.state_0_ = var20 = state_0 | 64;
                     lock.unlock();
                     hasLock = false;
                     TruffleStringBuilder.AppendCodePointIntlNode.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                  }
               }
            }
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
               TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf16CachedData s2_ = this.utf16Cached_cache;
               TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf32CachedData s4_ = this.utf32Cached_cache;
               if ((s2_ == null || s2_.next_ == null) && (s4_ == null || s4_.next_ == null)) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TruffleStringBuilder.AppendCodePointIntlNode create() {
         return new TruffleStringBuilderFactory.AppendCodePointIntlNodeGen();
      }

      public static TruffleStringBuilder.AppendCodePointIntlNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleStringBuilder.AppendCodePointIntlNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleStringBuilder.AppendCodePointIntlNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         void execute(
            TruffleStringBuilder arg0Value,
            int arg1Value,
            TruffleString.Encoding arg2Value,
            int arg3Value,
            boolean arg4Value,
            ConditionProfile arg5Value,
            BranchProfile arg6Value
         ) {
            if (TStringGuards.isAsciiBytesOrLatin1(arg2Value)) {
               TruffleStringBuilder.AppendCodePointIntlNode.bytes(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUTF8(arg2Value)) {
               TruffleStringBuilder.AppendCodePointIntlNode.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUTF16(arg2Value)) {
               this.utf16Uncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, ConditionProfile.getUncached());
            } else if (TStringGuards.isUTF32(arg2Value)) {
               this.utf32Uncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUnsupportedEncoding(arg2Value)) {
               TruffleStringBuilder.AppendCodePointIntlNode.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
               );
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

      @GeneratedBy(TruffleStringBuilder.AppendCodePointIntlNode.class)
      private static final class Utf16CachedData {
         @CompilerDirectives.CompilationFinal
         TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf16CachedData next_;
         @CompilerDirectives.CompilationFinal
         int cachedCurStride_;
         @CompilerDirectives.CompilationFinal
         int cachedNewStride_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile bmpProfile_;

         Utf16CachedData(TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf16CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TruffleStringBuilder.AppendCodePointIntlNode.class)
      private static final class Utf32CachedData {
         @CompilerDirectives.CompilationFinal
         TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf32CachedData next_;
         @CompilerDirectives.CompilationFinal
         int cachedCurStride_;
         @CompilerDirectives.CompilationFinal
         int cachedNewStride_;

         Utf32CachedData(TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.Utf32CachedData next_) {
            this.next_ = next_;
         }
      }
   }

   @GeneratedBy(TruffleStringBuilder.AppendCodePointNode.class)
   static final class AppendCodePointNodeGen extends TruffleStringBuilder.AppendCodePointNode {
      private static final TruffleStringBuilderFactory.AppendCodePointNodeGen.Uncached UNCACHED = new TruffleStringBuilderFactory.AppendCodePointNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringBuilderFactory.AppendCodePointNodeGen.AppendData append_cache;

      private AppendCodePointNodeGen() {
      }

      @Override
      public void execute(TruffleStringBuilder arg0Value, int arg1Value, int arg2Value, boolean arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringBuilderFactory.AppendCodePointNodeGen.AppendData s0_ = this.append_cache;
            if (s0_ != null) {
               TruffleStringBuilder.AppendCodePointNode.append(
                  arg0Value, arg1Value, arg2Value, arg3Value, s0_.appendCodePointIntlNode_, s0_.bufferGrowProfile_, s0_.errorProfile_
               );
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private void executeAndSpecialize(TruffleStringBuilder arg0Value, int arg1Value, int arg2Value, boolean arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            TruffleStringBuilderFactory.AppendCodePointNodeGen.AppendData s0_ = super.insert(
               new TruffleStringBuilderFactory.AppendCodePointNodeGen.AppendData()
            );
            s0_.appendCodePointIntlNode_ = s0_.insertAccessor(TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.create());
            s0_.bufferGrowProfile_ = ConditionProfile.create();
            s0_.errorProfile_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.append_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            TruffleStringBuilder.AppendCodePointNode.append(
               arg0Value, arg1Value, arg2Value, arg3Value, s0_.appendCodePointIntlNode_, s0_.bufferGrowProfile_, s0_.errorProfile_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleStringBuilder.AppendCodePointNode create() {
         return new TruffleStringBuilderFactory.AppendCodePointNodeGen();
      }

      public static TruffleStringBuilder.AppendCodePointNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleStringBuilder.AppendCodePointNode.class)
      private static final class AppendData extends Node {
         @Node.Child
         TruffleStringBuilder.AppendCodePointIntlNode appendCodePointIntlNode_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile bufferGrowProfile_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorProfile_;

         AppendData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleStringBuilder.AppendCodePointNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleStringBuilder.AppendCodePointNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public void execute(TruffleStringBuilder arg0Value, int arg1Value, int arg2Value, boolean arg3Value) {
            TruffleStringBuilder.AppendCodePointNode.append(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               TruffleStringBuilderFactory.AppendCodePointIntlNodeGen.getUncached(),
               ConditionProfile.getUncached(),
               BranchProfile.getUncached()
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

   @GeneratedBy(TruffleStringBuilder.AppendIntNumberNode.class)
   static final class AppendIntNumberNodeGen extends TruffleStringBuilder.AppendIntNumberNode {
      private static final TruffleStringBuilderFactory.AppendIntNumberNodeGen.Uncached UNCACHED = new TruffleStringBuilderFactory.AppendIntNumberNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TruffleStringBuilderFactory.AppendIntNumberNodeGen.AppendData append_cache;

      private AppendIntNumberNodeGen() {
      }

      @ExplodeLoop
      @Override
      public void execute(TruffleStringBuilder arg0Value, int arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            for (TruffleStringBuilderFactory.AppendIntNumberNodeGen.AppendData s0_ = this.append_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.cachedStride_ == arg0Value.stride) {
                  this.doAppend(arg0Value, arg1Value, s0_.cachedStride_, s0_.bufferGrowProfile_, s0_.errorProfile_);
                  return;
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private void executeAndSpecialize(TruffleStringBuilder arg0Value, int arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int count0_ = 0;
            TruffleStringBuilderFactory.AppendIntNumberNodeGen.AppendData s0_ = this.append_cache;
            if (state_0 != 0) {
               while (s0_ != null && s0_.cachedStride_ != arg0Value.stride) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null && count0_ < 3) {
               s0_ = new TruffleStringBuilderFactory.AppendIntNumberNodeGen.AppendData(this.append_cache);
               s0_.cachedStride_ = arg0Value.stride;
               s0_.bufferGrowProfile_ = ConditionProfile.create();
               s0_.errorProfile_ = BranchProfile.create();
               VarHandle.storeStoreFence();
               this.append_cache = s0_;
               int var11;
               this.state_0_ = var11 = state_0 | 1;
            }

            if (s0_ == null) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            lock.unlock();
            hasLock = false;
            this.doAppend(arg0Value, arg1Value, s0_.cachedStride_, s0_.bufferGrowProfile_, s0_.errorProfile_);
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
               TruffleStringBuilderFactory.AppendIntNumberNodeGen.AppendData s0_ = this.append_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TruffleStringBuilder.AppendIntNumberNode create() {
         return new TruffleStringBuilderFactory.AppendIntNumberNodeGen();
      }

      public static TruffleStringBuilder.AppendIntNumberNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleStringBuilder.AppendIntNumberNode.class)
      private static final class AppendData {
         @CompilerDirectives.CompilationFinal
         TruffleStringBuilderFactory.AppendIntNumberNodeGen.AppendData next_;
         @CompilerDirectives.CompilationFinal
         int cachedStride_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile bufferGrowProfile_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorProfile_;

         AppendData(TruffleStringBuilderFactory.AppendIntNumberNodeGen.AppendData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TruffleStringBuilder.AppendIntNumberNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleStringBuilder.AppendIntNumberNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public void execute(TruffleStringBuilder arg0Value, int arg1Value) {
            this.doAppend(arg0Value, arg1Value, arg0Value.stride, ConditionProfile.getUncached(), BranchProfile.getUncached());
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

   @GeneratedBy(TruffleStringBuilder.AppendJavaStringUTF16Node.class)
   static final class AppendJavaStringUTF16NodeGen extends TruffleStringBuilder.AppendJavaStringUTF16Node {
      private static final TruffleStringBuilderFactory.AppendJavaStringUTF16NodeGen.Uncached UNCACHED = new TruffleStringBuilderFactory.AppendJavaStringUTF16NodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringBuilder.AppendArrayIntlNode appendArrayIntlNode_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile stride0Profile_;

      private AppendJavaStringUTF16NodeGen() {
      }

      @Override
      public void execute(TruffleStringBuilder arg0Value, String arg1Value, int arg2Value, int arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            this.append(arg0Value, arg1Value, arg2Value, arg3Value, this.appendArrayIntlNode_, this.stride0Profile_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
         }
      }

      private void executeAndSpecialize(TruffleStringBuilder arg0Value, String arg1Value, int arg2Value, int arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            this.appendArrayIntlNode_ = super.insert(TruffleStringBuilderFactory.AppendArrayIntlNodeGen.create());
            this.stride0Profile_ = ConditionProfile.create();
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            this.append(arg0Value, arg1Value, arg2Value, arg3Value, this.appendArrayIntlNode_, this.stride0Profile_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleStringBuilder.AppendJavaStringUTF16Node create() {
         return new TruffleStringBuilderFactory.AppendJavaStringUTF16NodeGen();
      }

      public static TruffleStringBuilder.AppendJavaStringUTF16Node getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleStringBuilder.AppendJavaStringUTF16Node.class)
      @DenyReplace
      private static final class Uncached extends TruffleStringBuilder.AppendJavaStringUTF16Node {
         @CompilerDirectives.TruffleBoundary
         @Override
         public void execute(TruffleStringBuilder arg0Value, String arg1Value, int arg2Value, int arg3Value) {
            this.append(
               arg0Value, arg1Value, arg2Value, arg3Value, TruffleStringBuilderFactory.AppendArrayIntlNodeGen.getUncached(), ConditionProfile.getUncached()
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

   @GeneratedBy(TruffleStringBuilder.AppendLongNumberNode.class)
   static final class AppendLongNumberNodeGen extends TruffleStringBuilder.AppendLongNumberNode {
      private static final TruffleStringBuilderFactory.AppendLongNumberNodeGen.Uncached UNCACHED = new TruffleStringBuilderFactory.AppendLongNumberNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TruffleStringBuilderFactory.AppendLongNumberNodeGen.AppendData append_cache;

      private AppendLongNumberNodeGen() {
      }

      @ExplodeLoop
      @Override
      public void execute(TruffleStringBuilder arg0Value, long arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            for (TruffleStringBuilderFactory.AppendLongNumberNodeGen.AppendData s0_ = this.append_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.cachedStride_ == arg0Value.stride) {
                  this.doAppend(arg0Value, arg1Value, s0_.cachedStride_, s0_.bufferGrowProfile_, s0_.errorProfile_);
                  return;
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private void executeAndSpecialize(TruffleStringBuilder arg0Value, long arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int count0_ = 0;
            TruffleStringBuilderFactory.AppendLongNumberNodeGen.AppendData s0_ = this.append_cache;
            if (state_0 != 0) {
               while (s0_ != null && s0_.cachedStride_ != arg0Value.stride) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null && count0_ < 3) {
               s0_ = new TruffleStringBuilderFactory.AppendLongNumberNodeGen.AppendData(this.append_cache);
               s0_.cachedStride_ = arg0Value.stride;
               s0_.bufferGrowProfile_ = ConditionProfile.create();
               s0_.errorProfile_ = BranchProfile.create();
               VarHandle.storeStoreFence();
               this.append_cache = s0_;
               int var12;
               this.state_0_ = var12 = state_0 | 1;
            }

            if (s0_ == null) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            lock.unlock();
            hasLock = false;
            this.doAppend(arg0Value, arg1Value, s0_.cachedStride_, s0_.bufferGrowProfile_, s0_.errorProfile_);
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
               TruffleStringBuilderFactory.AppendLongNumberNodeGen.AppendData s0_ = this.append_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TruffleStringBuilder.AppendLongNumberNode create() {
         return new TruffleStringBuilderFactory.AppendLongNumberNodeGen();
      }

      public static TruffleStringBuilder.AppendLongNumberNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleStringBuilder.AppendLongNumberNode.class)
      private static final class AppendData {
         @CompilerDirectives.CompilationFinal
         TruffleStringBuilderFactory.AppendLongNumberNodeGen.AppendData next_;
         @CompilerDirectives.CompilationFinal
         int cachedStride_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile bufferGrowProfile_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorProfile_;

         AppendData(TruffleStringBuilderFactory.AppendLongNumberNodeGen.AppendData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TruffleStringBuilder.AppendLongNumberNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleStringBuilder.AppendLongNumberNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public void execute(TruffleStringBuilder arg0Value, long arg1Value) {
            this.doAppend(arg0Value, arg1Value, arg0Value.stride, ConditionProfile.getUncached(), BranchProfile.getUncached());
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

   @GeneratedBy(TruffleStringBuilder.AppendStringNode.class)
   static final class AppendStringNodeGen extends TruffleStringBuilder.AppendStringNode {
      private static final TruffleStringBuilderFactory.AppendStringNodeGen.Uncached UNCACHED = new TruffleStringBuilderFactory.AppendStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringBuilderFactory.AppendStringNodeGen.AppendData append_cache;

      private AppendStringNodeGen() {
      }

      @Override
      public void execute(TruffleStringBuilder arg0Value, AbstractTruffleString arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringBuilderFactory.AppendStringNodeGen.AppendData s0_ = this.append_cache;
            if (s0_ != null) {
               TruffleStringBuilder.AppendStringNode.append(
                  arg0Value, arg1Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.appendArrayIntlNode_
               );
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private void executeAndSpecialize(TruffleStringBuilder arg0Value, AbstractTruffleString arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            TruffleStringBuilderFactory.AppendStringNodeGen.AppendData s0_ = super.insert(new TruffleStringBuilderFactory.AppendStringNodeGen.AppendData());
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.appendArrayIntlNode_ = s0_.insertAccessor(TruffleStringBuilderFactory.AppendArrayIntlNodeGen.create());
            VarHandle.storeStoreFence();
            this.append_cache = s0_;
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            TruffleStringBuilder.AppendStringNode.append(
               arg0Value, arg1Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.appendArrayIntlNode_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleStringBuilder.AppendStringNode create() {
         return new TruffleStringBuilderFactory.AppendStringNodeGen();
      }

      public static TruffleStringBuilder.AppendStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleStringBuilder.AppendStringNode.class)
      private static final class AppendData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TruffleStringBuilder.AppendArrayIntlNode appendArrayIntlNode_;

         AppendData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleStringBuilder.AppendStringNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleStringBuilder.AppendStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public void execute(TruffleStringBuilder arg0Value, AbstractTruffleString arg1Value) {
            TruffleStringBuilder.AppendStringNode.append(
               arg0Value,
               arg1Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TruffleStringBuilderFactory.AppendArrayIntlNodeGen.getUncached()
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

   @GeneratedBy(TruffleStringBuilder.AppendSubstringByteIndexNode.class)
   static final class AppendSubstringByteIndexNodeGen extends TruffleStringBuilder.AppendSubstringByteIndexNode {
      private static final TruffleStringBuilderFactory.AppendSubstringByteIndexNodeGen.Uncached UNCACHED = new TruffleStringBuilderFactory.AppendSubstringByteIndexNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringBuilderFactory.AppendSubstringByteIndexNodeGen.AppendData append_cache;

      private AppendSubstringByteIndexNodeGen() {
      }

      @Override
      public void execute(TruffleStringBuilder arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringBuilderFactory.AppendSubstringByteIndexNodeGen.AppendData s0_ = this.append_cache;
            if (s0_ != null) {
               TruffleStringBuilder.AppendSubstringByteIndexNode.append(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  s0_.toIndexableNode_,
                  s0_.getCodePointLengthNode_,
                  s0_.getCodeRangeNode_,
                  s0_.appendArrayIntlNode_,
                  s0_.calcAttributesNode_,
                  s0_.calcAttrsProfile_
               );
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private void executeAndSpecialize(TruffleStringBuilder arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            TruffleStringBuilderFactory.AppendSubstringByteIndexNodeGen.AppendData s0_ = super.insert(
               new TruffleStringBuilderFactory.AppendSubstringByteIndexNodeGen.AppendData()
            );
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.appendArrayIntlNode_ = s0_.insertAccessor(TruffleStringBuilderFactory.AppendArrayIntlNodeGen.create());
            s0_.calcAttributesNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CalcStringAttributesNodeGen.create());
            s0_.calcAttrsProfile_ = ConditionProfile.create();
            VarHandle.storeStoreFence();
            this.append_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            TruffleStringBuilder.AppendSubstringByteIndexNode.append(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               s0_.toIndexableNode_,
               s0_.getCodePointLengthNode_,
               s0_.getCodeRangeNode_,
               s0_.appendArrayIntlNode_,
               s0_.calcAttributesNode_,
               s0_.calcAttrsProfile_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleStringBuilder.AppendSubstringByteIndexNode create() {
         return new TruffleStringBuilderFactory.AppendSubstringByteIndexNodeGen();
      }

      public static TruffleStringBuilder.AppendSubstringByteIndexNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleStringBuilder.AppendSubstringByteIndexNode.class)
      private static final class AppendData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TruffleStringBuilder.AppendArrayIntlNode appendArrayIntlNode_;
         @Node.Child
         TStringInternalNodes.CalcStringAttributesNode calcAttributesNode_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile calcAttrsProfile_;

         AppendData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleStringBuilder.AppendSubstringByteIndexNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleStringBuilder.AppendSubstringByteIndexNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public void execute(TruffleStringBuilder arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value) {
            TruffleStringBuilder.AppendSubstringByteIndexNode.append(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TruffleStringBuilderFactory.AppendArrayIntlNodeGen.getUncached(),
               TStringInternalNodes.CalcStringAttributesNode.getUncached(),
               ConditionProfile.getUncached()
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

   @GeneratedBy(TruffleStringBuilder.ToStringNode.class)
   static final class ToStringNodeGen extends TruffleStringBuilder.ToStringNode {
      private static final TruffleStringBuilderFactory.ToStringNodeGen.Uncached UNCACHED = new TruffleStringBuilderFactory.ToStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile calcAttributesProfile_;
      @Node.Child
      private TStringInternalNodes.CalcStringAttributesNode calcAttributesNode_;

      private ToStringNodeGen() {
      }

      @Override
      public TruffleString execute(TruffleStringBuilder arg0Value, boolean arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return TruffleStringBuilder.ToStringNode.createString(arg0Value, arg1Value, this.calcAttributesProfile_, this.calcAttributesNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private TruffleString executeAndSpecialize(TruffleStringBuilder arg0Value, boolean arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var6;
         try {
            int state_0 = this.state_0_;
            this.calcAttributesProfile_ = ConditionProfile.create();
            this.calcAttributesNode_ = super.insert(TStringInternalNodesFactory.CalcStringAttributesNodeGen.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = TruffleStringBuilder.ToStringNode.createString(arg0Value, arg1Value, this.calcAttributesProfile_, this.calcAttributesNode_);
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

      public static TruffleStringBuilder.ToStringNode create() {
         return new TruffleStringBuilderFactory.ToStringNodeGen();
      }

      public static TruffleStringBuilder.ToStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleStringBuilder.ToStringNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleStringBuilder.ToStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(TruffleStringBuilder arg0Value, boolean arg1Value) {
            return TruffleStringBuilder.ToStringNode.createString(
               arg0Value, arg1Value, ConditionProfile.getUncached(), TStringInternalNodes.CalcStringAttributesNode.getUncached()
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
}
