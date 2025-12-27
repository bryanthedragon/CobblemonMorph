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

@GeneratedBy(PolyglotList.class)
final class PolyglotListFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(PolyglotList.Cache.class)
   static final class CacheFactory {
      @GeneratedBy(PolyglotList.Cache.AddAtIndexNode.class)
      static final class AddAtIndexNodeGen extends PolyglotList.Cache.AddAtIndexNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotListFactory.CacheFactory.AddAtIndexNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotLanguageContext.ToGuestValueNode cached1_toGuest_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_error_;

         private AddAtIndexNodeGen(PolyglotList.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotListFactory.CacheFactory.AddAtIndexNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
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
               InteropLibrary cached1_interop__ = PolyglotListFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
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
                  PolyglotListFactory.CacheFactory.AddAtIndexNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotListFactory.CacheFactory.AddAtIndexNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotListFactory.INTEROP_LIBRARY_.create(arg1Value));
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
                  cached1_interop__ = PolyglotListFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
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
                  PolyglotListFactory.CacheFactory.AddAtIndexNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotList.Cache.AddAtIndexNode create(PolyglotList.Cache cache) {
            return new PolyglotListFactory.CacheFactory.AddAtIndexNodeGen(cache);
         }

         @GeneratedBy(PolyglotList.Cache.AddAtIndexNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotListFactory.CacheFactory.AddAtIndexNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuest_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            Cached0Data(PolyglotListFactory.CacheFactory.AddAtIndexNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotList.Cache.AddNode.class)
      static final class AddNodeGen extends PolyglotList.Cache.AddNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotListFactory.CacheFactory.AddNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotLanguageContext.ToGuestValueNode cached1_toGuest_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_error_;

         private AddNodeGen(PolyglotList.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotListFactory.CacheFactory.AddNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
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
               InteropLibrary cached1_interop__ = PolyglotListFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
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
                  PolyglotListFactory.CacheFactory.AddNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotListFactory.CacheFactory.AddNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotListFactory.INTEROP_LIBRARY_.create(arg1Value));
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
                  cached1_interop__ = PolyglotListFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
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
                  PolyglotListFactory.CacheFactory.AddNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotList.Cache.AddNode create(PolyglotList.Cache cache) {
            return new PolyglotListFactory.CacheFactory.AddNodeGen(cache);
         }

         @GeneratedBy(PolyglotList.Cache.AddNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotListFactory.CacheFactory.AddNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuest_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            Cached0Data(PolyglotListFactory.CacheFactory.AddNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotList.Cache.GetNode.class)
      static final class GetNodeGen extends PolyglotList.Cache.GetNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotListFactory.CacheFactory.GetNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotToHostNode cached1_toHost_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_error_;

         private GetNodeGen(PolyglotList.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotListFactory.CacheFactory.GetNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
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
               InteropLibrary cached1_interop__ = PolyglotListFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
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
                  PolyglotListFactory.CacheFactory.GetNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotListFactory.CacheFactory.GetNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotListFactory.INTEROP_LIBRARY_.create(arg1Value));
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
                  cached1_interop__ = PolyglotListFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
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
                  PolyglotListFactory.CacheFactory.GetNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotList.Cache.GetNode create(PolyglotList.Cache cache) {
            return new PolyglotListFactory.CacheFactory.GetNodeGen(cache);
         }

         @GeneratedBy(PolyglotList.Cache.GetNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotListFactory.CacheFactory.GetNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            PolyglotToHostNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            Cached0Data(PolyglotListFactory.CacheFactory.GetNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotList.Cache.RemoveNode.class)
      static final class RemoveNodeGen extends PolyglotList.Cache.RemoveNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotListFactory.CacheFactory.RemoveNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_error_;

         private RemoveNodeGen(PolyglotList.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotListFactory.CacheFactory.RemoveNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
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
               InteropLibrary cached1_interop__ = PolyglotListFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
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
                  PolyglotListFactory.CacheFactory.RemoveNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotListFactory.CacheFactory.RemoveNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotListFactory.INTEROP_LIBRARY_.create(arg1Value));
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
                  cached1_interop__ = PolyglotListFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
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
                  PolyglotListFactory.CacheFactory.RemoveNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotList.Cache.RemoveNode create(PolyglotList.Cache cache) {
            return new PolyglotListFactory.CacheFactory.RemoveNodeGen(cache);
         }

         @GeneratedBy(PolyglotList.Cache.RemoveNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotListFactory.CacheFactory.RemoveNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            Cached0Data(PolyglotListFactory.CacheFactory.RemoveNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotList.Cache.SetNode.class)
      static final class SetNodeGen extends PolyglotList.Cache.SetNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotListFactory.CacheFactory.SetNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotLanguageContext.ToGuestValueNode cached1_toGuest_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_error_;

         private SetNodeGen(PolyglotList.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotListFactory.CacheFactory.SetNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
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
               InteropLibrary cached1_interop__ = PolyglotListFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
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
                  PolyglotListFactory.CacheFactory.SetNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotListFactory.CacheFactory.SetNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotListFactory.INTEROP_LIBRARY_.create(arg1Value));
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
                  cached1_interop__ = PolyglotListFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
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
                  PolyglotListFactory.CacheFactory.SetNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotList.Cache.SetNode create(PolyglotList.Cache cache) {
            return new PolyglotListFactory.CacheFactory.SetNodeGen(cache);
         }

         @GeneratedBy(PolyglotList.Cache.SetNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotListFactory.CacheFactory.SetNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuest_;
            @CompilerDirectives.CompilationFinal
            BranchProfile error_;

            Cached0Data(PolyglotListFactory.CacheFactory.SetNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotList.Cache.SizeNode.class)
      static final class SizeNodeGen extends PolyglotList.Cache.SizeNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotListFactory.CacheFactory.SizeNodeGen.Cached0Data cached0_cache;

         private SizeNodeGen(PolyglotList.Cache cache) {
            super(cache);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotListFactory.CacheFactory.SizeNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.interop_.accepts(arg1Value)) {
                        return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_);
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
               InteropLibrary cached1_interop__ = PolyglotListFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__);
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
                  PolyglotListFactory.CacheFactory.SizeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotListFactory.CacheFactory.SizeNodeGen.Cached0Data(this.cached0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotListFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(arg0Value, arg1Value, arg2Value, s0_.interop_);
                  }
               }

               InteropLibrary cached1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_interop__ = PolyglotListFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, arg2Value, cached1_interop__);
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
                  PolyglotListFactory.CacheFactory.SizeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotList.Cache.SizeNode create(PolyglotList.Cache cache) {
            return new PolyglotListFactory.CacheFactory.SizeNodeGen(cache);
         }

         @GeneratedBy(PolyglotList.Cache.SizeNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotListFactory.CacheFactory.SizeNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary interop_;

            Cached0Data(PolyglotListFactory.CacheFactory.SizeNodeGen.Cached0Data next_) {
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
