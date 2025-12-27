package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TStringOpsNodes.class)
final class TStringOpsNodesFactory {
   @GeneratedBy(TStringOpsNodes.CalculateHashCodeNode.class)
   static final class CalculateHashCodeNodeGen extends TStringOpsNodes.CalculateHashCodeNode {
      private static final TStringOpsNodesFactory.CalculateHashCodeNodeGen.Uncached UNCACHED = new TStringOpsNodesFactory.CalculateHashCodeNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringOpsNodesFactory.CalculateHashCodeNodeGen.CachedData cached_cache;

      private CalculateHashCodeNodeGen() {
      }

      @ExplodeLoop
      @Override
      int execute(AbstractTruffleString arg0Value, Object arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            for (TStringOpsNodesFactory.CalculateHashCodeNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               if (TStringGuards.stride(arg0Value) == s0_.cachedStrideA_) {
                  return this.cached(arg0Value, arg1Value, s0_.cachedStrideA_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int cachedStrideA__;
         try {
            int state_0 = this.state_0_;
            int count0_ = 0;
            TStringOpsNodesFactory.CalculateHashCodeNodeGen.CachedData s0_ = this.cached_cache;
            if (state_0 != 0) {
               while (s0_ != null && TStringGuards.stride(arg0Value) != s0_.cachedStrideA_) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null) {
               cachedStrideA__ = TStringGuards.stride(arg0Value);
               if (TStringGuards.stride(arg0Value) == cachedStrideA__ && count0_ < 9) {
                  s0_ = new TStringOpsNodesFactory.CalculateHashCodeNodeGen.CachedData(this.cached_cache);
                  s0_.cachedStrideA_ = cachedStrideA__;
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  int var12;
                  this.state_0_ = var12 = state_0 | 1;
               }
            }

            if (s0_ == null) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            lock.unlock();
            hasLock = false;
            cachedStrideA__ = this.cached(arg0Value, arg1Value, s0_.cachedStrideA_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return cachedStrideA__;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               TStringOpsNodesFactory.CalculateHashCodeNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TStringOpsNodes.CalculateHashCodeNode create() {
         return new TStringOpsNodesFactory.CalculateHashCodeNodeGen();
      }

      public static TStringOpsNodes.CalculateHashCodeNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringOpsNodes.CalculateHashCodeNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         TStringOpsNodesFactory.CalculateHashCodeNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideA_;

         CachedData(TStringOpsNodesFactory.CalculateHashCodeNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TStringOpsNodes.CalculateHashCodeNode.class)
      @DenyReplace
      private static final class Uncached extends TStringOpsNodes.CalculateHashCodeNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(AbstractTruffleString arg0Value, Object arg1Value) {
            if (TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value)) {
               return this.cached(arg0Value, arg1Value, TStringGuards.stride(arg0Value));
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
   }

   @GeneratedBy(TStringOpsNodes.IndexOfAnyCharNode.class)
   static final class IndexOfAnyCharNodeGen extends TStringOpsNodes.IndexOfAnyCharNode {
      private static final TStringOpsNodesFactory.IndexOfAnyCharNodeGen.Uncached UNCACHED = new TStringOpsNodesFactory.IndexOfAnyCharNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private IndexOfAnyCharNodeGen() {
      }

      @Override
      int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, char[] arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isStride0(arg0Value) && arg4Value.length == 1) {
               return this.stride0(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }

            if ((state_0 & 2) != 0 && TStringGuards.isStride0(arg0Value) && arg4Value.length > 1) {
               return this.stride0MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }

            if ((state_0 & 4) != 0 && TStringGuards.isStride1(arg0Value)) {
               return this.stride1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, char[] arg4Value) {
         int state_0 = this.state_0_;
         if (TStringGuards.isStride0(arg0Value) && arg4Value.length == 1) {
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            return this.stride0(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         } else if (TStringGuards.isStride0(arg0Value) && arg4Value.length > 1) {
            int var8;
            this.state_0_ = var8 = state_0 | 2;
            return this.stride0MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         } else if (TStringGuards.isStride1(arg0Value)) {
            int var7;
            this.state_0_ = var7 = state_0 | 4;
            return this.stride1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         }
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

      public static TStringOpsNodes.IndexOfAnyCharNode create() {
         return new TStringOpsNodesFactory.IndexOfAnyCharNodeGen();
      }

      public static TStringOpsNodes.IndexOfAnyCharNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringOpsNodes.IndexOfAnyCharNode.class)
      @DenyReplace
      private static final class Uncached extends TStringOpsNodes.IndexOfAnyCharNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, char[] arg4Value) {
            if (TStringGuards.isStride0(arg0Value) && arg4Value.length == 1) {
               return this.stride0(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            } else if (TStringGuards.isStride0(arg0Value) && arg4Value.length > 1) {
               return this.stride0MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            } else if (TStringGuards.isStride1(arg0Value)) {
               return this.stride1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value
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
   }

   @GeneratedBy(TStringOpsNodes.IndexOfAnyIntNode.class)
   static final class IndexOfAnyIntNodeGen extends TStringOpsNodes.IndexOfAnyIntNode {
      private static final TStringOpsNodesFactory.IndexOfAnyIntNodeGen.Uncached UNCACHED = new TStringOpsNodesFactory.IndexOfAnyIntNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private IndexOfAnyIntNodeGen() {
      }

      @Override
      int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int[] arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isStride0(arg0Value) && arg4Value.length == 1) {
               return this.stride0(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }

            if ((state_0 & 2) != 0 && TStringGuards.isStride0(arg0Value) && arg4Value.length > 1) {
               return this.stride0MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }

            if ((state_0 & 4) != 0 && TStringGuards.isStride1(arg0Value) && arg4Value.length == 1) {
               return this.stride1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }

            if ((state_0 & 8) != 0 && TStringGuards.isStride1(arg0Value) && arg4Value.length > 1) {
               return this.stride1MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }

            if ((state_0 & 16) != 0 && TStringGuards.isStride2(arg0Value)) {
               return this.stride2(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int[] arg4Value) {
         int state_0 = this.state_0_;
         if (TStringGuards.isStride0(arg0Value) && arg4Value.length == 1) {
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            return this.stride0(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         } else if (TStringGuards.isStride0(arg0Value) && arg4Value.length > 1) {
            int var10;
            this.state_0_ = var10 = state_0 | 2;
            return this.stride0MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         } else if (TStringGuards.isStride1(arg0Value) && arg4Value.length == 1) {
            int var9;
            this.state_0_ = var9 = state_0 | 4;
            return this.stride1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         } else if (TStringGuards.isStride1(arg0Value) && arg4Value.length > 1) {
            int var8;
            this.state_0_ = var8 = state_0 | 8;
            return this.stride1MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         } else if (TStringGuards.isStride2(arg0Value)) {
            int var7;
            this.state_0_ = var7 = state_0 | 16;
            return this.stride2(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         }
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

      public static TStringOpsNodes.IndexOfAnyIntNode create() {
         return new TStringOpsNodesFactory.IndexOfAnyIntNodeGen();
      }

      public static TStringOpsNodes.IndexOfAnyIntNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringOpsNodes.IndexOfAnyIntNode.class)
      @DenyReplace
      private static final class Uncached extends TStringOpsNodes.IndexOfAnyIntNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int[] arg4Value) {
            if (TStringGuards.isStride0(arg0Value) && arg4Value.length == 1) {
               return this.stride0(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            } else if (TStringGuards.isStride0(arg0Value) && arg4Value.length > 1) {
               return this.stride0MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            } else if (TStringGuards.isStride1(arg0Value) && arg4Value.length == 1) {
               return this.stride1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            } else if (TStringGuards.isStride1(arg0Value) && arg4Value.length > 1) {
               return this.stride1MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            } else if (TStringGuards.isStride2(arg0Value)) {
               return this.stride2(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value
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
   }

   @GeneratedBy(TStringOpsNodes.RawIndexOfCodePointNode.class)
   static final class RawIndexOfCodePointNodeGen extends TStringOpsNodes.RawIndexOfCodePointNode {
      private static final TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.Uncached UNCACHED = new TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.CachedData cached_cache;

      private RawIndexOfCodePointNodeGen() {
      }

      @ExplodeLoop
      @Override
      int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            for (TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               if (TStringGuards.stride(arg0Value) == s0_.cachedStrideA_) {
                  return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStrideA_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int cachedStrideA__;
         try {
            int state_0 = this.state_0_;
            int count0_ = 0;
            TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.CachedData s0_ = this.cached_cache;
            if (state_0 != 0) {
               while (s0_ != null && TStringGuards.stride(arg0Value) != s0_.cachedStrideA_) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null) {
               cachedStrideA__ = TStringGuards.stride(arg0Value);
               if (TStringGuards.stride(arg0Value) == cachedStrideA__ && count0_ < 9) {
                  s0_ = new TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.CachedData(this.cached_cache);
                  s0_.cachedStrideA_ = cachedStrideA__;
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  int var15;
                  this.state_0_ = var15 = state_0 | 1;
               }
            }

            if (s0_ == null) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value
               );
            }

            lock.unlock();
            hasLock = false;
            cachedStrideA__ = this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStrideA_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return cachedStrideA__;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TStringOpsNodes.RawIndexOfCodePointNode create() {
         return new TStringOpsNodesFactory.RawIndexOfCodePointNodeGen();
      }

      public static TStringOpsNodes.RawIndexOfCodePointNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringOpsNodes.RawIndexOfCodePointNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideA_;

         CachedData(TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TStringOpsNodes.RawIndexOfCodePointNode.class)
      @DenyReplace
      private static final class Uncached extends TStringOpsNodes.RawIndexOfCodePointNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value) {
            if (TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value)) {
               return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TStringGuards.stride(arg0Value));
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value
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
   }

   @GeneratedBy(TStringOpsNodes.RawIndexOfStringNode.class)
   static final class RawIndexOfStringNodeGen extends TStringOpsNodes.RawIndexOfStringNode {
      private static final TStringOpsNodesFactory.RawIndexOfStringNodeGen.Uncached UNCACHED = new TStringOpsNodesFactory.RawIndexOfStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedLen1Data cachedLen1_cache;
      @CompilerDirectives.CompilationFinal
      private TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedData cached_cache;

      private RawIndexOfStringNodeGen() {
      }

      @ExplodeLoop
      @Override
      int execute(
         AbstractTruffleString arg0Value, Object arg1Value, AbstractTruffleString arg2Value, Object arg3Value, int arg4Value, int arg5Value, byte[] arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.length(arg2Value) == 1) {
               for (TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedLen1Data s0_ = this.cachedLen1_cache; s0_ != null; s0_ = s0_.next_) {
                  if (TStringGuards.stride(arg0Value) == s0_.cachedStrideA_ && TStringGuards.stride(arg2Value) == s0_.cachedStrideB_) {
                     return this.cachedLen1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.cachedStrideA_, s0_.cachedStrideB_);
                  }
               }
            }

            if ((state_0 & 2) != 0 && TStringGuards.length(arg2Value) > 1) {
               for (TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedData s1_ = this.cached_cache; s1_ != null; s1_ = s1_.next_) {
                  if (TStringGuards.stride(arg0Value) == s1_.cachedStrideA_ && TStringGuards.stride(arg2Value) == s1_.cachedStrideB_) {
                     return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s1_.cachedStrideA_, s1_.cachedStrideB_);
                  }
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, AbstractTruffleString arg2Value, Object arg3Value, int arg4Value, int arg5Value, byte[] arg6Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (TStringGuards.length(arg2Value) == 1) {
               int count0_ = 0;
               TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedLen1Data s0_ = this.cachedLen1_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (TStringGuards.stride(arg0Value) != s0_.cachedStrideA_ || TStringGuards.stride(arg2Value) != s0_.cachedStrideB_)) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  int cachedStrideA__ = TStringGuards.stride(arg0Value);
                  if (TStringGuards.stride(arg0Value) == cachedStrideA__) {
                     int cachedStrideB__ = TStringGuards.stride(arg2Value);
                     if (TStringGuards.stride(arg2Value) == cachedStrideB__ && count0_ < 9) {
                        s0_ = new TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedLen1Data(this.cachedLen1_cache);
                        s0_.cachedStrideA_ = cachedStrideA__;
                        s0_.cachedStrideB_ = cachedStrideB__;
                        VarHandle.storeStoreFence();
                        this.cachedLen1_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                     }
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.cachedLen1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.cachedStrideA_, s0_.cachedStrideB_);
               }
            }

            if (TStringGuards.length(arg2Value) > 1) {
               int count1_ = 0;
               TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedData s1_ = this.cached_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && (TStringGuards.stride(arg0Value) != s1_.cachedStrideA_ || TStringGuards.stride(arg2Value) != s1_.cachedStrideB_)) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null) {
                  int cachedStrideA__1 = TStringGuards.stride(arg0Value);
                  if (TStringGuards.stride(arg0Value) == cachedStrideA__1) {
                     int cachedStrideB__1 = TStringGuards.stride(arg2Value);
                     if (TStringGuards.stride(arg2Value) == cachedStrideB__1 && count1_ < 9) {
                        s1_ = new TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedData(this.cached_cache);
                        s1_.cachedStrideA_ = cachedStrideA__1;
                        s1_.cachedStrideB_ = cachedStrideB__1;
                        VarHandle.storeStoreFence();
                        this.cached_cache = s1_;
                        int var18;
                        this.state_0_ = var18 = state_0 | 2;
                     }
                  }
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s1_.cachedStrideA_, s1_.cachedStrideB_);
               }
            }

            throw new UnsupportedSpecializationException(
               this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
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
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedLen1Data s0_ = this.cachedLen1_cache;
               TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedData s1_ = this.cached_cache;
               if ((s0_ == null || s0_.next_ == null) && (s1_ == null || s1_.next_ == null)) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TStringOpsNodes.RawIndexOfStringNode create() {
         return new TStringOpsNodesFactory.RawIndexOfStringNodeGen();
      }

      public static TStringOpsNodes.RawIndexOfStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringOpsNodes.RawIndexOfStringNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideA_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideB_;

         CachedData(TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TStringOpsNodes.RawIndexOfStringNode.class)
      private static final class CachedLen1Data {
         @CompilerDirectives.CompilationFinal
         TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedLen1Data next_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideA_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideB_;

         CachedLen1Data(TStringOpsNodesFactory.RawIndexOfStringNodeGen.CachedLen1Data next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TStringOpsNodes.RawIndexOfStringNode.class)
      @DenyReplace
      private static final class Uncached extends TStringOpsNodes.RawIndexOfStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value,
            Object arg1Value,
            AbstractTruffleString arg2Value,
            Object arg3Value,
            int arg4Value,
            int arg5Value,
            byte[] arg6Value
         ) {
            if (TStringGuards.length(arg2Value) == 1
               && TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value)
               && TStringGuards.stride(arg2Value) == TStringGuards.stride(arg2Value)) {
               return this.cachedLen1(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringGuards.stride(arg0Value), TStringGuards.stride(arg2Value)
               );
            } else if (TStringGuards.length(arg2Value) > 1
               && TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value)
               && TStringGuards.stride(arg2Value) == TStringGuards.stride(arg2Value)) {
               return this.cached(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringGuards.stride(arg0Value), TStringGuards.stride(arg2Value)
               );
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
   }

   @GeneratedBy(TStringOpsNodes.RawLastIndexOfCodePointNode.class)
   static final class RawLastIndexOfCodePointNodeGen extends TStringOpsNodes.RawLastIndexOfCodePointNode {
      private static final TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.Uncached UNCACHED = new TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.CachedData cached_cache;

      private RawLastIndexOfCodePointNodeGen() {
      }

      @ExplodeLoop
      @Override
      int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            for (TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               if (TStringGuards.stride(arg0Value) == s0_.cachedStrideA_) {
                  return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStrideA_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int cachedStrideA__;
         try {
            int state_0 = this.state_0_;
            int count0_ = 0;
            TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.CachedData s0_ = this.cached_cache;
            if (state_0 != 0) {
               while (s0_ != null && TStringGuards.stride(arg0Value) != s0_.cachedStrideA_) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null) {
               cachedStrideA__ = TStringGuards.stride(arg0Value);
               if (TStringGuards.stride(arg0Value) == cachedStrideA__ && count0_ < 9) {
                  s0_ = new TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.CachedData(this.cached_cache);
                  s0_.cachedStrideA_ = cachedStrideA__;
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  int var15;
                  this.state_0_ = var15 = state_0 | 1;
               }
            }

            if (s0_ == null) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value
               );
            }

            lock.unlock();
            hasLock = false;
            cachedStrideA__ = this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStrideA_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return cachedStrideA__;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TStringOpsNodes.RawLastIndexOfCodePointNode create() {
         return new TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen();
      }

      public static TStringOpsNodes.RawLastIndexOfCodePointNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringOpsNodes.RawLastIndexOfCodePointNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideA_;

         CachedData(TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TStringOpsNodes.RawLastIndexOfCodePointNode.class)
      @DenyReplace
      private static final class Uncached extends TStringOpsNodes.RawLastIndexOfCodePointNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value) {
            if (TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value)) {
               return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TStringGuards.stride(arg0Value));
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value
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
   }

   @GeneratedBy(TStringOpsNodes.RawLastIndexOfStringNode.class)
   static final class RawLastIndexOfStringNodeGen extends TStringOpsNodes.RawLastIndexOfStringNode {
      private static final TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.Uncached UNCACHED = new TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedLen1Data cachedLen1_cache;
      @CompilerDirectives.CompilationFinal
      private TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedData cached_cache;

      private RawLastIndexOfStringNodeGen() {
      }

      @ExplodeLoop
      @Override
      int execute(
         AbstractTruffleString arg0Value, Object arg1Value, AbstractTruffleString arg2Value, Object arg3Value, int arg4Value, int arg5Value, byte[] arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.length(arg2Value) == 1) {
               for (TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedLen1Data s0_ = this.cachedLen1_cache; s0_ != null; s0_ = s0_.next_) {
                  if (TStringGuards.stride(arg0Value) == s0_.cachedStrideA_ && TStringGuards.stride(arg2Value) == s0_.cachedStrideB_) {
                     return this.cachedLen1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.cachedStrideA_, s0_.cachedStrideB_);
                  }
               }
            }

            if ((state_0 & 2) != 0 && TStringGuards.length(arg2Value) > 1) {
               for (TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedData s1_ = this.cached_cache; s1_ != null; s1_ = s1_.next_) {
                  if (TStringGuards.stride(arg0Value) == s1_.cachedStrideA_ && TStringGuards.stride(arg2Value) == s1_.cachedStrideB_) {
                     return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s1_.cachedStrideA_, s1_.cachedStrideB_);
                  }
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, AbstractTruffleString arg2Value, Object arg3Value, int arg4Value, int arg5Value, byte[] arg6Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (TStringGuards.length(arg2Value) == 1) {
               int count0_ = 0;
               TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedLen1Data s0_ = this.cachedLen1_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (TStringGuards.stride(arg0Value) != s0_.cachedStrideA_ || TStringGuards.stride(arg2Value) != s0_.cachedStrideB_)) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  int cachedStrideA__ = TStringGuards.stride(arg0Value);
                  if (TStringGuards.stride(arg0Value) == cachedStrideA__) {
                     int cachedStrideB__ = TStringGuards.stride(arg2Value);
                     if (TStringGuards.stride(arg2Value) == cachedStrideB__ && count0_ < 9) {
                        s0_ = new TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedLen1Data(this.cachedLen1_cache);
                        s0_.cachedStrideA_ = cachedStrideA__;
                        s0_.cachedStrideB_ = cachedStrideB__;
                        VarHandle.storeStoreFence();
                        this.cachedLen1_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                     }
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.cachedLen1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.cachedStrideA_, s0_.cachedStrideB_);
               }
            }

            if (TStringGuards.length(arg2Value) > 1) {
               int count1_ = 0;
               TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedData s1_ = this.cached_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && (TStringGuards.stride(arg0Value) != s1_.cachedStrideA_ || TStringGuards.stride(arg2Value) != s1_.cachedStrideB_)) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null) {
                  int cachedStrideA__1 = TStringGuards.stride(arg0Value);
                  if (TStringGuards.stride(arg0Value) == cachedStrideA__1) {
                     int cachedStrideB__1 = TStringGuards.stride(arg2Value);
                     if (TStringGuards.stride(arg2Value) == cachedStrideB__1 && count1_ < 9) {
                        s1_ = new TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedData(this.cached_cache);
                        s1_.cachedStrideA_ = cachedStrideA__1;
                        s1_.cachedStrideB_ = cachedStrideB__1;
                        VarHandle.storeStoreFence();
                        this.cached_cache = s1_;
                        int var18;
                        this.state_0_ = var18 = state_0 | 2;
                     }
                  }
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s1_.cachedStrideA_, s1_.cachedStrideB_);
               }
            }

            throw new UnsupportedSpecializationException(
               this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
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
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedLen1Data s0_ = this.cachedLen1_cache;
               TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedData s1_ = this.cached_cache;
               if ((s0_ == null || s0_.next_ == null) && (s1_ == null || s1_.next_ == null)) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TStringOpsNodes.RawLastIndexOfStringNode create() {
         return new TStringOpsNodesFactory.RawLastIndexOfStringNodeGen();
      }

      public static TStringOpsNodes.RawLastIndexOfStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringOpsNodes.RawLastIndexOfStringNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideA_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideB_;

         CachedData(TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TStringOpsNodes.RawLastIndexOfStringNode.class)
      private static final class CachedLen1Data {
         @CompilerDirectives.CompilationFinal
         TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedLen1Data next_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideA_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideB_;

         CachedLen1Data(TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.CachedLen1Data next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TStringOpsNodes.RawLastIndexOfStringNode.class)
      @DenyReplace
      private static final class Uncached extends TStringOpsNodes.RawLastIndexOfStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value,
            Object arg1Value,
            AbstractTruffleString arg2Value,
            Object arg3Value,
            int arg4Value,
            int arg5Value,
            byte[] arg6Value
         ) {
            if (TStringGuards.length(arg2Value) == 1
               && TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value)
               && TStringGuards.stride(arg2Value) == TStringGuards.stride(arg2Value)) {
               return this.cachedLen1(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringGuards.stride(arg0Value), TStringGuards.stride(arg2Value)
               );
            } else if (TStringGuards.length(arg2Value) > 1
               && TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value)
               && TStringGuards.stride(arg2Value) == TStringGuards.stride(arg2Value)) {
               return this.cached(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringGuards.stride(arg0Value), TStringGuards.stride(arg2Value)
               );
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
   }

   @GeneratedBy(TStringOpsNodes.RawReadValueNode.class)
   static final class RawReadValueNodeGen extends TStringOpsNodes.RawReadValueNode {
      private static final TStringOpsNodesFactory.RawReadValueNodeGen.Uncached UNCACHED = new TStringOpsNodesFactory.RawReadValueNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringOpsNodesFactory.RawReadValueNodeGen.CachedData cached_cache;

      private RawReadValueNodeGen() {
      }

      @ExplodeLoop
      @Override
      int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            for (TStringOpsNodesFactory.RawReadValueNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               if (TStringGuards.stride(arg0Value) == s0_.cachedStrideA_) {
                  return TStringOpsNodes.RawReadValueNode.cached(arg0Value, arg1Value, arg2Value, s0_.cachedStrideA_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int cachedStrideA__;
         try {
            int state_0 = this.state_0_;
            int count0_ = 0;
            TStringOpsNodesFactory.RawReadValueNodeGen.CachedData s0_ = this.cached_cache;
            if (state_0 != 0) {
               while (s0_ != null && TStringGuards.stride(arg0Value) != s0_.cachedStrideA_) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null) {
               cachedStrideA__ = TStringGuards.stride(arg0Value);
               if (TStringGuards.stride(arg0Value) == cachedStrideA__ && count0_ < 9) {
                  s0_ = new TStringOpsNodesFactory.RawReadValueNodeGen.CachedData(this.cached_cache);
                  s0_.cachedStrideA_ = cachedStrideA__;
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  int var13;
                  this.state_0_ = var13 = state_0 | 1;
               }
            }

            if (s0_ == null) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }

            lock.unlock();
            hasLock = false;
            cachedStrideA__ = TStringOpsNodes.RawReadValueNode.cached(arg0Value, arg1Value, arg2Value, s0_.cachedStrideA_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return cachedStrideA__;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               TStringOpsNodesFactory.RawReadValueNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TStringOpsNodes.RawReadValueNode create() {
         return new TStringOpsNodesFactory.RawReadValueNodeGen();
      }

      public static TStringOpsNodes.RawReadValueNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringOpsNodes.RawReadValueNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         TStringOpsNodesFactory.RawReadValueNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         int cachedStrideA_;

         CachedData(TStringOpsNodesFactory.RawReadValueNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TStringOpsNodes.RawReadValueNode.class)
      @DenyReplace
      private static final class Uncached extends TStringOpsNodes.RawReadValueNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value) {
            if (TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value)) {
               return TStringOpsNodes.RawReadValueNode.cached(arg0Value, arg1Value, arg2Value, TStringGuards.stride(arg0Value));
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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
   }
}
