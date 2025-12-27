package com.oracle.truffle.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Shape;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(DynamicObjectLibraryImpl.class)
final class DynamicObjectLibraryImplFactory {
   @GeneratedBy(DynamicObjectLibraryImpl.MakeSharedNode.class)
   static final class MakeSharedNodeGen extends DynamicObjectLibraryImpl.MakeSharedNode {
      private static final DynamicObjectLibraryImplFactory.MakeSharedNodeGen.Uncached UNCACHED = new DynamicObjectLibraryImplFactory.MakeSharedNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private Shape newShape_;

      private MakeSharedNodeGen() {
      }

      @Override
      void execute(DynamicObject arg0Value, Shape arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            DynamicObjectLibraryImpl.MakeSharedNode.doCached(arg0Value, arg1Value, this.newShape_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private void executeAndSpecialize(DynamicObject arg0Value, Shape arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            this.newShape_ = DynamicObjectLibraryImpl.MakeSharedNode.makeSharedShape(arg1Value);
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            DynamicObjectLibraryImpl.MakeSharedNode.doCached(arg0Value, arg1Value, this.newShape_);
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

      public static DynamicObjectLibraryImpl.MakeSharedNode create() {
         return new DynamicObjectLibraryImplFactory.MakeSharedNodeGen();
      }

      public static DynamicObjectLibraryImpl.MakeSharedNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(DynamicObjectLibraryImpl.MakeSharedNode.class)
      @DenyReplace
      private static final class Uncached extends DynamicObjectLibraryImpl.MakeSharedNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         void execute(DynamicObject arg0Value, Shape arg1Value) {
            DynamicObjectLibraryImpl.MakeSharedNode.doCached(arg0Value, arg1Value, DynamicObjectLibraryImpl.MakeSharedNode.makeSharedShape(arg1Value));
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

   @GeneratedBy(DynamicObjectLibraryImpl.ResetShapeNode.class)
   static final class ResetShapeNodeGen extends DynamicObjectLibraryImpl.ResetShapeNode {
      private static final DynamicObjectLibraryImplFactory.ResetShapeNodeGen.Uncached UNCACHED = new DynamicObjectLibraryImplFactory.ResetShapeNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private DynamicObjectLibraryImplFactory.ResetShapeNodeGen.CachedData cached_cache;

      private ResetShapeNodeGen() {
      }

      @ExplodeLoop
      @Override
      boolean execute(DynamicObject arg0Value, Shape arg1Value, Shape arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            for (DynamicObjectLibraryImplFactory.ResetShapeNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               if (arg2Value == s0_.cachedOtherShape_) {
                  return DynamicObjectLibraryImpl.ResetShapeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedOtherShape_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private boolean executeAndSpecialize(DynamicObject arg0Value, Shape arg1Value, Shape arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var14;
         try {
            int state_0 = this.state_0_;
            int count0_ = 0;
            DynamicObjectLibraryImplFactory.ResetShapeNodeGen.CachedData s0_ = this.cached_cache;
            if (state_0 != 0) {
               while (s0_ != null && arg2Value != s0_.cachedOtherShape_) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null) {
               Shape cachedOtherShape__ = DynamicObjectLibraryImpl.ResetShapeNode.verifyResetShape(arg1Value, arg2Value);
               if (arg2Value == cachedOtherShape__ && count0_ < 3) {
                  s0_ = new DynamicObjectLibraryImplFactory.ResetShapeNodeGen.CachedData(this.cached_cache);
                  s0_.cachedOtherShape_ = cachedOtherShape__;
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
            var14 = DynamicObjectLibraryImpl.ResetShapeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedOtherShape_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var14;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               DynamicObjectLibraryImplFactory.ResetShapeNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static DynamicObjectLibraryImpl.ResetShapeNode create() {
         return new DynamicObjectLibraryImplFactory.ResetShapeNodeGen();
      }

      public static DynamicObjectLibraryImpl.ResetShapeNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(DynamicObjectLibraryImpl.ResetShapeNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         DynamicObjectLibraryImplFactory.ResetShapeNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         Shape cachedOtherShape_;

         CachedData(DynamicObjectLibraryImplFactory.ResetShapeNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(DynamicObjectLibraryImpl.ResetShapeNode.class)
      @DenyReplace
      private static final class Uncached extends DynamicObjectLibraryImpl.ResetShapeNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         boolean execute(DynamicObject arg0Value, Shape arg1Value, Shape arg2Value) {
            if (arg2Value == DynamicObjectLibraryImpl.ResetShapeNode.verifyResetShape(arg1Value, arg2Value)) {
               return DynamicObjectLibraryImpl.ResetShapeNode.doCached(
                  arg0Value, arg1Value, arg2Value, DynamicObjectLibraryImpl.ResetShapeNode.verifyResetShape(arg1Value, arg2Value)
               );
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

   @GeneratedBy(DynamicObjectLibraryImpl.SetDynamicTypeNode.class)
   static final class SetDynamicTypeNodeGen extends DynamicObjectLibraryImpl.SetDynamicTypeNode {
      private static final DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen.Uncached UNCACHED = new DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen.CachedData cached_cache;

      private SetDynamicTypeNodeGen() {
      }

      @ExplodeLoop
      @Override
      boolean execute(DynamicObject arg0Value, Shape arg1Value, Object arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg2Value == s0_.newObjectType_) {
                     return DynamicObjectLibraryImpl.SetDynamicTypeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.newObjectType_, s0_.newShape_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return DynamicObjectLibraryImpl.SetDynamicTypeNode.doUncached(arg0Value, arg1Value, arg2Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private boolean executeAndSpecialize(DynamicObject arg0Value, Shape arg1Value, Object arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && arg2Value != s0_.newObjectType_) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < 3) {
                  s0_ = new DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen.CachedData(this.cached_cache);
                  s0_.newObjectType_ = arg2Value;
                  s0_.newShape_ = DynamicObjectLibraryImpl.SetDynamicTypeNode.shapeSetDynamicType(arg1Value, s0_.newObjectType_);
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return DynamicObjectLibraryImpl.SetDynamicTypeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.newObjectType_, s0_.newShape_);
               }
            }

            int var16;
            this.exclude_ = var16 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var15;
            this.state_0_ = var15 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return DynamicObjectLibraryImpl.SetDynamicTypeNode.doUncached(arg0Value, arg1Value, arg2Value);
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
               DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static DynamicObjectLibraryImpl.SetDynamicTypeNode create() {
         return new DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen();
      }

      public static DynamicObjectLibraryImpl.SetDynamicTypeNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(DynamicObjectLibraryImpl.SetDynamicTypeNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         Object newObjectType_;
         @CompilerDirectives.CompilationFinal
         Shape newShape_;

         CachedData(DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(DynamicObjectLibraryImpl.SetDynamicTypeNode.class)
      @DenyReplace
      private static final class Uncached extends DynamicObjectLibraryImpl.SetDynamicTypeNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         boolean execute(DynamicObject arg0Value, Shape arg1Value, Object arg2Value) {
            return DynamicObjectLibraryImpl.SetDynamicTypeNode.doUncached(arg0Value, arg1Value, arg2Value);
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

   @GeneratedBy(DynamicObjectLibraryImpl.SetFlagsNode.class)
   static final class SetFlagsNodeGen extends DynamicObjectLibraryImpl.SetFlagsNode {
      private static final DynamicObjectLibraryImplFactory.SetFlagsNodeGen.Uncached UNCACHED = new DynamicObjectLibraryImplFactory.SetFlagsNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private DynamicObjectLibraryImplFactory.SetFlagsNodeGen.CachedData cached_cache;

      private SetFlagsNodeGen() {
      }

      @ExplodeLoop
      @Override
      boolean execute(DynamicObject arg0Value, Shape arg1Value, int arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (DynamicObjectLibraryImplFactory.SetFlagsNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg2Value == s0_.newFlags_) {
                     return DynamicObjectLibraryImpl.SetFlagsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.newFlags_, s0_.newShape_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return DynamicObjectLibraryImpl.SetFlagsNode.doUncached(arg0Value, arg1Value, arg2Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private boolean executeAndSpecialize(DynamicObject arg0Value, Shape arg1Value, int arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               DynamicObjectLibraryImplFactory.SetFlagsNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && arg2Value != s0_.newFlags_) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < 3) {
                  s0_ = new DynamicObjectLibraryImplFactory.SetFlagsNodeGen.CachedData(this.cached_cache);
                  s0_.newFlags_ = arg2Value;
                  s0_.newShape_ = DynamicObjectLibraryImpl.SetFlagsNode.shapeSetFlags(arg1Value, s0_.newFlags_);
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return DynamicObjectLibraryImpl.SetFlagsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.newFlags_, s0_.newShape_);
               }
            }

            int var16;
            this.exclude_ = var16 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var15;
            this.state_0_ = var15 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return DynamicObjectLibraryImpl.SetFlagsNode.doUncached(arg0Value, arg1Value, arg2Value);
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
               DynamicObjectLibraryImplFactory.SetFlagsNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static DynamicObjectLibraryImpl.SetFlagsNode create() {
         return new DynamicObjectLibraryImplFactory.SetFlagsNodeGen();
      }

      public static DynamicObjectLibraryImpl.SetFlagsNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(DynamicObjectLibraryImpl.SetFlagsNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         DynamicObjectLibraryImplFactory.SetFlagsNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         int newFlags_;
         @CompilerDirectives.CompilationFinal
         Shape newShape_;

         CachedData(DynamicObjectLibraryImplFactory.SetFlagsNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(DynamicObjectLibraryImpl.SetFlagsNode.class)
      @DenyReplace
      private static final class Uncached extends DynamicObjectLibraryImpl.SetFlagsNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         boolean execute(DynamicObject arg0Value, Shape arg1Value, int arg2Value) {
            return DynamicObjectLibraryImpl.SetFlagsNode.doUncached(arg0Value, arg1Value, arg2Value);
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
