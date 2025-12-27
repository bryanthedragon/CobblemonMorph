package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(PolyglotIterator.class)
final class PolyglotIteratorFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(PolyglotIterator.Cache.class)
   static final class CacheFactory {
      @GeneratedBy(PolyglotIterator.Cache.HasNextNode.class)
      static final class HasNextNodeGen extends PolyglotIterator.Cache.HasNextNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotIteratorFactory.CacheFactory.HasNextNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_error_;

         private HasNextNodeGen(PolyglotIterator.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotIteratorFactory.CacheFactory.HasNextNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.iterators_.accepts(arg1Value)) {
                        return this.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.error_);
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  return this.cached1Boundary(state_0, arg0Value, arg1Value, arg2Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
         }

         @CompilerDirectives.TruffleBoundary
         private Object cached1Boundary(int state_0, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var8;
            try {
               InteropLibrary cached1_iterators__ = PolyglotIteratorFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = this.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__, this.cached1_error_);
            } finally {
               encapsulating_.set(prev_);
            }

            return var8;
         }

         private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if (exclude == 0) {
                  int count0_ = 0;
                  PolyglotIteratorFactory.CacheFactory.HasNextNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.iterators_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotIteratorFactory.CacheFactory.HasNextNodeGen.Cached0Data(this.cached0_cache));
                     s0_.iterators_ = s0_.insertAccessor(PolyglotIteratorFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.error_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.error_);
                  }
               }

               InteropLibrary cached1_iterators__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_iterators__ = PolyglotIteratorFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_error_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__, this.cached1_error_);
               } finally {
                  encapsulating_.set(prev_);
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
                  PolyglotIteratorFactory.CacheFactory.HasNextNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotIterator.Cache.HasNextNode create(PolyglotIterator.Cache cache) {
            return new PolyglotIteratorFactory.CacheFactory.HasNextNodeGen(cache);
         }

         @GeneratedBy(PolyglotIterator.Cache.HasNextNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotIteratorFactory.CacheFactory.HasNextNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary iterators_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            Cached0Data(PolyglotIteratorFactory.CacheFactory.HasNextNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotIterator.Cache.NextNode.class)
      static final class NextNodeGen extends PolyglotIterator.Cache.NextNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotIteratorFactory.CacheFactory.NextNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotIteratorFactory.CacheFactory.NextNodeGen.Cached1Data cached1_cache;

         private NextNodeGen(PolyglotIterator.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotIteratorFactory.CacheFactory.NextNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.iterators_.accepts(arg1Value)) {
                        return this.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.toHost_, s0_.error_, s0_.stop_);
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotIteratorFactory.CacheFactory.NextNodeGen.Cached1Data s1_ = this.cached1_cache;
                  if (s1_ != null) {
                     return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value);
                  }
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
         }

         @CompilerDirectives.TruffleBoundary
         private Object cached1Boundary(
            int state_0,
            PolyglotIteratorFactory.CacheFactory.NextNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary iterators__ = PolyglotIteratorFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = this.doCached(arg0Value, arg1Value, arg2Value, iterators__, s1_.toHost_, s1_.error_, s1_.stop_);
            } finally {
               encapsulating_.set(prev_);
            }

            return var9;
         }

         private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if (exclude == 0) {
                  int count0_ = 0;
                  PolyglotIteratorFactory.CacheFactory.NextNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.iterators_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotIteratorFactory.CacheFactory.NextNodeGen.Cached0Data(this.cached0_cache));
                     s0_.iterators_ = s0_.insertAccessor(PolyglotIteratorFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = s0_.insertAccessor(PolyglotToHostNodeGen.create());
                     s0_.error_ = BranchProfile.create();
                     s0_.stop_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.toHost_, s0_.error_, s0_.stop_);
                  }
               }

               InteropLibrary iterators__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotIteratorFactory.CacheFactory.NextNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotIteratorFactory.CacheFactory.NextNodeGen.Cached1Data()
                  );
                  iterators__ = PolyglotIteratorFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toHost_ = s1_.insertAccessor(PolyglotToHostNodeGen.create());
                  s1_.error_ = BranchProfile.create();
                  s1_.stop_ = BranchProfile.create();
                  VarHandle.storeStoreFence();
                  this.cached1_cache = s1_;
                  int var23;
                  this.exclude_ = var23 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var22;
                  this.state_0_ = var22 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, arg2Value, iterators__, s1_.toHost_, s1_.error_, s1_.stop_);
               } finally {
                  encapsulating_.set(prev_);
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
                  PolyglotIteratorFactory.CacheFactory.NextNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotIterator.Cache.NextNode create(PolyglotIterator.Cache cache) {
            return new PolyglotIteratorFactory.CacheFactory.NextNodeGen(cache);
         }

         @GeneratedBy(PolyglotIterator.Cache.NextNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotIteratorFactory.CacheFactory.NextNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary iterators_;
            @Node.Child
            PolyglotToHostNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;
            @CompilerDirectives.CompilationFinal
            BranchProfile stop_;

            Cached0Data(PolyglotIteratorFactory.CacheFactory.NextNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotIterator.Cache.NextNode.class)
         private static final class Cached1Data extends Node {
            @Node.Child
            PolyglotToHostNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;
            @CompilerDirectives.CompilationFinal
            BranchProfile stop_;

            Cached1Data() {
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
   }
}
