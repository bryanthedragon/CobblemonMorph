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

@GeneratedBy(PolyglotMap.class)
final class PolyglotMapFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(PolyglotMap.Cache.class)
   static final class CacheFactory {
      @GeneratedBy(PolyglotMap.Cache.ContainsKeyNode.class)
      static final class ContainsKeyNodeGen extends PolyglotMap.Cache.ContainsKeyNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotMapFactory.CacheFactory.ContainsKeyNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotLanguageContext.ToGuestValueNode cached1_toGuest_;

         private ContainsKeyNodeGen(PolyglotMap.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotMapFactory.CacheFactory.ContainsKeyNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.interop_.accepts(arg1Value)) {
                        return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_);
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
               InteropLibrary cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_);
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
                  PolyglotMapFactory.CacheFactory.ContainsKeyNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotMapFactory.CacheFactory.ContainsKeyNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotMapFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toGuest_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_);
                  }
               }

               InteropLibrary cached1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toGuest_ = super.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_);
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
                  PolyglotMapFactory.CacheFactory.ContainsKeyNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotMap.Cache.ContainsKeyNode create(PolyglotMap.Cache cache) {
            return new PolyglotMapFactory.CacheFactory.ContainsKeyNodeGen(cache);
         }

         @GeneratedBy(PolyglotMap.Cache.ContainsKeyNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotMapFactory.CacheFactory.ContainsKeyNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuest_;

            Cached0Data(PolyglotMapFactory.CacheFactory.ContainsKeyNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotMap.Cache.EntrySet.class)
      static final class EntrySetNodeGen extends PolyglotMap.Cache.EntrySet {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotMapFactory.CacheFactory.EntrySetNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotToHostNode cached1_toHost_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_error_;

         private EntrySetNodeGen(PolyglotMap.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotMapFactory.CacheFactory.EntrySetNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.interop_.accepts(arg1Value)) {
                        return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toHost_, s0_.error_);
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
               InteropLibrary cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toHost_, this.cached1_error_);
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
                  PolyglotMapFactory.CacheFactory.EntrySetNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotMapFactory.CacheFactory.EntrySetNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotMapFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = s0_.insertAccessor(PolyglotToHostNodeGen.create());
                     s0_.error_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toHost_, s0_.error_);
                  }
               }

               InteropLibrary cached1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toHost_ = super.insert(PolyglotToHostNodeGen.create());
                  this.cached1_error_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toHost_, this.cached1_error_);
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
                  PolyglotMapFactory.CacheFactory.EntrySetNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotMap.Cache.EntrySet create(PolyglotMap.Cache cache) {
            return new PolyglotMapFactory.CacheFactory.EntrySetNodeGen(cache);
         }

         @GeneratedBy(PolyglotMap.Cache.EntrySet.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotMapFactory.CacheFactory.EntrySetNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            PolyglotToHostNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            Cached0Data(PolyglotMapFactory.CacheFactory.EntrySetNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotMap.Cache.GetNode.class)
      static final class GetNodeGen extends PolyglotMap.Cache.GetNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotMapFactory.CacheFactory.GetNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotMapFactory.CacheFactory.GetNodeGen.Cached1Data cached1_cache;

         private GetNodeGen(PolyglotMap.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotMapFactory.CacheFactory.GetNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.interop_.accepts(arg1Value)) {
                        return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.toHost_, s0_.error_);
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotMapFactory.CacheFactory.GetNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            int state_0, PolyglotMapFactory.CacheFactory.GetNodeGen.Cached1Data s1_, PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = this.doCached(arg0Value, arg1Value, arg2Value, interop__, s1_.toGuest_, s1_.toHost_, s1_.error_);
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
                  PolyglotMapFactory.CacheFactory.GetNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotMapFactory.CacheFactory.GetNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotMapFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toGuest_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     s0_.toHost_ = s0_.insertAccessor(PolyglotToHostNodeGen.create());
                     s0_.error_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.toHost_, s0_.error_);
                  }
               }

               InteropLibrary interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotMapFactory.CacheFactory.GetNodeGen.Cached1Data s1_ = super.insert(new PolyglotMapFactory.CacheFactory.GetNodeGen.Cached1Data());
                  interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toGuest_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  s1_.toHost_ = s1_.insertAccessor(PolyglotToHostNodeGen.create());
                  s1_.error_ = BranchProfile.create();
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
                  return this.doCached(arg0Value, arg1Value, arg2Value, interop__, s1_.toGuest_, s1_.toHost_, s1_.error_);
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
                  PolyglotMapFactory.CacheFactory.GetNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotMap.Cache.GetNode create(PolyglotMap.Cache cache) {
            return new PolyglotMapFactory.CacheFactory.GetNodeGen(cache);
         }

         @GeneratedBy(PolyglotMap.Cache.GetNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotMapFactory.CacheFactory.GetNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuest_;
            @Node.Child
            PolyglotToHostNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            Cached0Data(PolyglotMapFactory.CacheFactory.GetNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotMap.Cache.GetNode.class)
         private static final class Cached1Data extends Node {
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuest_;
            @Node.Child
            PolyglotToHostNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

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

      @GeneratedBy(PolyglotMap.Cache.HashEntriesIteratorNode.class)
      static final class HashEntriesIteratorNodeGen extends PolyglotMap.Cache.HashEntriesIteratorNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotMapFactory.CacheFactory.HashEntriesIteratorNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotToHostNode cached1_toHost_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_error_;

         private HashEntriesIteratorNodeGen(PolyglotMap.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotMapFactory.CacheFactory.HashEntriesIteratorNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.interop_.accepts(arg1Value)) {
                        return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toHost_, s0_.error_);
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
               InteropLibrary cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toHost_, this.cached1_error_);
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
                  PolyglotMapFactory.CacheFactory.HashEntriesIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotMapFactory.CacheFactory.HashEntriesIteratorNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotMapFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = s0_.insertAccessor(PolyglotToHostNodeGen.create());
                     s0_.error_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toHost_, s0_.error_);
                  }
               }

               InteropLibrary cached1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toHost_ = super.insert(PolyglotToHostNodeGen.create());
                  this.cached1_error_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toHost_, this.cached1_error_);
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
                  PolyglotMapFactory.CacheFactory.HashEntriesIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotMap.Cache.HashEntriesIteratorNode create(PolyglotMap.Cache cache) {
            return new PolyglotMapFactory.CacheFactory.HashEntriesIteratorNodeGen(cache);
         }

         @GeneratedBy(PolyglotMap.Cache.HashEntriesIteratorNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotMapFactory.CacheFactory.HashEntriesIteratorNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            PolyglotToHostNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            Cached0Data(PolyglotMapFactory.CacheFactory.HashEntriesIteratorNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotMap.Cache.HashSizeNode.class)
      static final class HashSizeNodeGen extends PolyglotMap.Cache.HashSizeNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotMapFactory.CacheFactory.HashSizeNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_error_;

         private HashSizeNodeGen(PolyglotMap.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotMapFactory.CacheFactory.HashSizeNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.interop_.accepts(arg1Value)) {
                        return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.error_);
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
               InteropLibrary cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_error_);
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
                  PolyglotMapFactory.CacheFactory.HashSizeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotMapFactory.CacheFactory.HashSizeNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotMapFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.error_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.error_);
                  }
               }

               InteropLibrary cached1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_error_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_error_);
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
                  PolyglotMapFactory.CacheFactory.HashSizeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotMap.Cache.HashSizeNode create(PolyglotMap.Cache cache) {
            return new PolyglotMapFactory.CacheFactory.HashSizeNodeGen(cache);
         }

         @GeneratedBy(PolyglotMap.Cache.HashSizeNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotMapFactory.CacheFactory.HashSizeNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            Cached0Data(PolyglotMapFactory.CacheFactory.HashSizeNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotMap.Cache.Put.class)
      static final class PutNodeGen extends PolyglotMap.Cache.Put {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotMapFactory.CacheFactory.PutNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotLanguageContext.ToGuestValueNode cached1_toGuest_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_error_;

         private PutNodeGen(PolyglotMap.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotMapFactory.CacheFactory.PutNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.interop_.accepts(arg1Value)) {
                        return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.error_);
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
               InteropLibrary cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_, this.cached1_error_);
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
                  PolyglotMapFactory.CacheFactory.PutNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotMapFactory.CacheFactory.PutNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotMapFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toGuest_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     s0_.error_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.error_);
                  }
               }

               InteropLibrary cached1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toGuest_ = super.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  this.cached1_error_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_, this.cached1_error_);
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
                  PolyglotMapFactory.CacheFactory.PutNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotMap.Cache.Put create(PolyglotMap.Cache cache) {
            return new PolyglotMapFactory.CacheFactory.PutNodeGen(cache);
         }

         @GeneratedBy(PolyglotMap.Cache.Put.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotMapFactory.CacheFactory.PutNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuest_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            Cached0Data(PolyglotMapFactory.CacheFactory.PutNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotMap.Cache.RemoveBoolean.class)
      static final class RemoveBooleanNodeGen extends PolyglotMap.Cache.RemoveBoolean {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotMapFactory.CacheFactory.RemoveBooleanNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotLanguageContext.ToGuestValueNode cached1_toGuest_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_error_;

         private RemoveBooleanNodeGen(PolyglotMap.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotMapFactory.CacheFactory.RemoveBooleanNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.interop_.accepts(arg1Value)) {
                        return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.error_);
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
               InteropLibrary cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_, this.cached1_error_);
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
                  PolyglotMapFactory.CacheFactory.RemoveBooleanNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotMapFactory.CacheFactory.RemoveBooleanNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotMapFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toGuest_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     s0_.error_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.error_);
                  }
               }

               InteropLibrary cached1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toGuest_ = super.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  this.cached1_error_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_, this.cached1_error_);
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
                  PolyglotMapFactory.CacheFactory.RemoveBooleanNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotMap.Cache.RemoveBoolean create(PolyglotMap.Cache cache) {
            return new PolyglotMapFactory.CacheFactory.RemoveBooleanNodeGen(cache);
         }

         @GeneratedBy(PolyglotMap.Cache.RemoveBoolean.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotMapFactory.CacheFactory.RemoveBooleanNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuest_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            Cached0Data(PolyglotMapFactory.CacheFactory.RemoveBooleanNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotMap.Cache.RemoveNode.class)
      static final class RemoveNodeGen extends PolyglotMap.Cache.RemoveNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotMapFactory.CacheFactory.RemoveNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotLanguageContext.ToGuestValueNode cached1_toGuest_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_error_;

         private RemoveNodeGen(PolyglotMap.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotMapFactory.CacheFactory.RemoveNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.interop_.accepts(arg1Value)) {
                        return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.error_);
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
               InteropLibrary cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_, this.cached1_error_);
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
                  PolyglotMapFactory.CacheFactory.RemoveNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotMapFactory.CacheFactory.RemoveNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotMapFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toGuest_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     s0_.error_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_, s0_.toGuest_, s0_.error_);
                  }
               }

               InteropLibrary cached1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_interop__ = PolyglotMapFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toGuest_ = super.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  this.cached1_error_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__, this.cached1_toGuest_, this.cached1_error_);
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
                  PolyglotMapFactory.CacheFactory.RemoveNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotMap.Cache.RemoveNode create(PolyglotMap.Cache cache) {
            return new PolyglotMapFactory.CacheFactory.RemoveNodeGen(cache);
         }

         @GeneratedBy(PolyglotMap.Cache.RemoveNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotMapFactory.CacheFactory.RemoveNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuest_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            Cached0Data(PolyglotMapFactory.CacheFactory.RemoveNodeGen.Cached0Data next_) {
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
   }
}
