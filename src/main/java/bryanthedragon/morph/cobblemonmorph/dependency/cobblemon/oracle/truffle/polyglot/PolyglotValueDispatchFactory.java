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

@GeneratedBy(PolyglotValueDispatch.class)
final class PolyglotValueDispatchFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(PolyglotValueDispatch.InteropValue.class)
   static final class InteropValueFactory {
      @GeneratedBy(PolyglotValueDispatch.InteropValue.AsDateNode.class)
      static final class AsDateNodeGen extends PolyglotValueDispatch.InteropValue.AsDateNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.AsDateNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private AsDateNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.AsDateNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.AsDateNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.AsDateNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.AsDateNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.AsDateNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.AsDateNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.AsDateNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.AsDateNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.AsDateNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.AsDateNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.AsDateNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.AsDateNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.AsDateNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.AsDurationNode.class)
      static final class AsDurationNodeGen extends PolyglotValueDispatch.InteropValue.AsDurationNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.AsDurationNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private AsDurationNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.AsDurationNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.AsDurationNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.AsDurationNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.AsDurationNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.AsDurationNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.AsDurationNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.AsDurationNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.AsDurationNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.AsDurationNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.AsDurationNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.AsDurationNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.AsDurationNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.AsDurationNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.AsInstantNode.class)
      static final class AsInstantNodeGen extends PolyglotValueDispatch.InteropValue.AsInstantNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.AsInstantNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private AsInstantNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.AsInstantNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.AsInstantNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.AsInstantNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.AsInstantNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.AsInstantNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.AsInstantNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.AsInstantNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.AsInstantNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.AsInstantNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.AsInstantNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.AsInstantNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.AsInstantNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.AsInstantNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.AsNativePointerNode.class)
      static final class AsNativePointerNodeGen extends PolyglotValueDispatch.InteropValue.AsNativePointerNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.AsNativePointerNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private AsNativePointerNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.AsNativePointerNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.natives_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.AsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, s0_.natives_, s0_.unsupported_);
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
               InteropLibrary cached1_natives__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.AsNativePointerNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_natives__, this.cached1_unsupported_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.AsNativePointerNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.natives_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.AsNativePointerNodeGen.Cached0Data(this.cached0_cache));
                     s0_.natives_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.AsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, s0_.natives_, s0_.unsupported_);
                  }
               }

               InteropLibrary cached1_natives__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_natives__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.AsNativePointerNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_natives__, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.AsNativePointerNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.AsNativePointerNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.AsNativePointerNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.AsNativePointerNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.AsNativePointerNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary natives_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.AsNativePointerNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.AsTimeNode.class)
      static final class AsTimeNodeGen extends PolyglotValueDispatch.InteropValue.AsTimeNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.AsTimeNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private AsTimeNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.AsTimeNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.AsTimeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.AsTimeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.AsTimeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.AsTimeNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.AsTimeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.AsTimeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.AsTimeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.AsTimeNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.AsTimeNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.AsTimeNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.AsTimeNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.AsTimeNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.AsTimeZoneNode.class)
      static final class AsTimeZoneNodeGen extends PolyglotValueDispatch.InteropValue.AsTimeZoneNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.AsTimeZoneNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private AsTimeZoneNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.AsTimeZoneNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.AsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.AsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.AsTimeZoneNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.AsTimeZoneNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.AsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.AsTimeZoneNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.AsTimeZoneNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.AsTimeZoneNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.AsTimeZoneNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.AsTimeZoneNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.AsTimeZoneNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.AsTimeZoneNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.CanExecuteNode.class)
      static final class CanExecuteNodeGen extends PolyglotValueDispatch.InteropValue.CanExecuteNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.CanExecuteNodeGen.Cached0Data cached0_cache;

         private CanExecuteNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.CanExecuteNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.executables_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.CanExecuteNode.doCached(arg0Value, arg1Value, arg2Value, s0_.executables_);
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
               InteropLibrary cached1_executables__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.CanExecuteNode.doCached(arg0Value, arg1Value, arg2Value, cached1_executables__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.CanExecuteNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.executables_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.CanExecuteNodeGen.Cached0Data(this.cached0_cache));
                     s0_.executables_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.CanExecuteNode.doCached(arg0Value, arg1Value, arg2Value, s0_.executables_);
                  }
               }

               InteropLibrary cached1_executables__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_executables__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.CanExecuteNode.doCached(arg0Value, arg1Value, arg2Value, cached1_executables__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.CanExecuteNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.CanExecuteNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.CanExecuteNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.CanExecuteNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.CanExecuteNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary executables_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.CanExecuteNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.CanInstantiateNode.class)
      static final class CanInstantiateNodeGen extends PolyglotValueDispatch.InteropValue.CanInstantiateNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.CanInstantiateNodeGen.Cached0Data cached0_cache;

         private CanInstantiateNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.CanInstantiateNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.instantiables_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.CanInstantiateNode.doCached(arg0Value, arg1Value, arg2Value, s0_.instantiables_);
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
               InteropLibrary cached1_instantiables__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.CanInstantiateNode.doCached(arg0Value, arg1Value, arg2Value, cached1_instantiables__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.CanInstantiateNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.instantiables_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.CanInstantiateNodeGen.Cached0Data(this.cached0_cache));
                     s0_.instantiables_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.CanInstantiateNode.doCached(arg0Value, arg1Value, arg2Value, s0_.instantiables_);
                  }
               }

               InteropLibrary cached1_instantiables__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_instantiables__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.CanInstantiateNode.doCached(arg0Value, arg1Value, arg2Value, cached1_instantiables__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.CanInstantiateNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.CanInstantiateNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.CanInstantiateNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.CanInstantiateNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.CanInstantiateNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary instantiables_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.CanInstantiateNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.CanInvokeNode.class)
      static final class CanInvokeNodeGen extends PolyglotValueDispatch.InteropValue.CanInvokeNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.CanInvokeNodeGen.Cached0Data cached0_cache;

         private CanInvokeNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.CanInvokeNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.CanInvokeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.CanInvokeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.CanInvokeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.CanInvokeNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.CanInvokeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.CanInvokeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.CanInvokeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.CanInvokeNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.CanInvokeNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.CanInvokeNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.CanInvokeNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.CanInvokeNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetArrayElementNode.class)
      static final class GetArrayElementNodeGen extends PolyglotValueDispatch.InteropValue.GetArrayElementNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.Cached1Data cached1_cache;

         private GetArrayElementNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.arrays_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetArrayElementNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary arrays__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.GetArrayElementNode.doCached(
                  arg0Value, arg1Value, arg2Value, arrays__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.arrays_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.Cached0Data(this.cached0_cache));
                     s0_.arrays_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.unknown_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetArrayElementNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                     );
                  }
               }

               InteropLibrary arrays__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.Cached1Data()
                  );
                  arrays__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toHost_ = this.createToHost();
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.unknown_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.GetArrayElementNode.doCached(
                     arg0Value, arg1Value, arg2Value, arrays__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetArrayElementNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetArrayElementNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary arrays_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetArrayElementNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetArrayElementNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetArraySizeNode.class)
      static final class GetArraySizeNodeGen extends PolyglotValueDispatch.InteropValue.GetArraySizeNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetArraySizeNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private GetArraySizeNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetArraySizeNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.arrays_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetArraySizeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.unsupported_);
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
               InteropLibrary cached1_arrays__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.GetArraySizeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_arrays__, this.cached1_unsupported_);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetArraySizeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.arrays_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetArraySizeNodeGen.Cached0Data(this.cached0_cache));
                     s0_.arrays_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetArraySizeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.unsupported_);
                  }
               }

               InteropLibrary cached1_arrays__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_arrays__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.GetArraySizeNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_arrays__, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetArraySizeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetArraySizeNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetArraySizeNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetArraySizeNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetArraySizeNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary arrays_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetArraySizeNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetBufferSizeNode.class)
      static final class GetBufferSizeNodeGen extends PolyglotValueDispatch.InteropValue.GetBufferSizeNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetBufferSizeNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private GetBufferSizeNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetBufferSizeNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetBufferSizeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_);
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
               InteropLibrary cached1_buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.GetBufferSizeNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_buffers__, this.cached1_unsupported_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetBufferSizeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetBufferSizeNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetBufferSizeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_);
                  }
               }

               InteropLibrary cached1_buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.GetBufferSizeNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_buffers__, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetBufferSizeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetBufferSizeNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetBufferSizeNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetBufferSizeNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetBufferSizeNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetBufferSizeNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode.class)
      static final class GetHashEntriesIteratorNodeGen extends PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetHashEntriesIteratorNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private PolyglotLanguageContext.ToHostValueNode cached1_toHost_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private GetHashEntriesIteratorNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetHashEntriesIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.hashes_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toHost_, s0_.unsupported_
                        );
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
               InteropLibrary cached1_hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toHost_, this.cached1_unsupported_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashEntriesIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetHashEntriesIteratorNodeGen.Cached0Data(this.cached0_cache));
                     s0_.hashes_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toHost_, s0_.unsupported_
                     );
                  }
               }

               InteropLibrary cached1_hashes__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toHost_ = this.createToHost();
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toHost_, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashEntriesIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetHashEntriesIteratorNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashEntriesIteratorNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetHashEntriesIteratorNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary hashes_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetHashEntriesIteratorNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode.class)
      static final class GetHashKeysIteratorNodeGen extends PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetHashKeysIteratorNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private PolyglotLanguageContext.ToHostValueNode cached1_toHost_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private GetHashKeysIteratorNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetHashKeysIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.hashes_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toHost_, s0_.unsupported_
                        );
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
               InteropLibrary cached1_hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toHost_, this.cached1_unsupported_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashKeysIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetHashKeysIteratorNodeGen.Cached0Data(this.cached0_cache));
                     s0_.hashes_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toHost_, s0_.unsupported_
                     );
                  }
               }

               InteropLibrary cached1_hashes__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toHost_ = this.createToHost();
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toHost_, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashKeysIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetHashKeysIteratorNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashKeysIteratorNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetHashKeysIteratorNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary hashes_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetHashKeysIteratorNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashSizeNode.class)
      static final class GetHashSizeNodeGen extends PolyglotValueDispatch.InteropValue.GetHashSizeNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetHashSizeNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private GetHashSizeNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetHashSizeNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.hashes_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetHashSizeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.unsupported_);
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
               InteropLibrary cached1_hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.GetHashSizeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_unsupported_);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashSizeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetHashSizeNodeGen.Cached0Data(this.cached0_cache));
                     s0_.hashes_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetHashSizeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.unsupported_);
                  }
               }

               InteropLibrary cached1_hashes__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.GetHashSizeNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashSizeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetHashSizeNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetHashSizeNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashSizeNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetHashSizeNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary hashes_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetHashSizeNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashValueNode.class)
      static final class GetHashValueNodeGen extends PolyglotValueDispatch.InteropValue.GetHashValueNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.Cached1Data cached1_cache;

         private GetHashValueNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.hashes_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetHashValueNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.toHost_, s0_.unsupported_, s0_.invalidKey_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.GetHashValueNode.doCached(
                  arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.toHost_, s1_.unsupported_, s1_.invalidKey_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.Cached0Data(this.cached0_cache));
                     s0_.hashes_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toGuestKey_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.invalidKey_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetHashValueNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.toHost_, s0_.unsupported_, s0_.invalidKey_
                     );
                  }
               }

               InteropLibrary hashes__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.Cached1Data()
                  );
                  hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toGuestKey_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  s1_.toHost_ = this.createToHost();
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.invalidKey_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.GetHashValueNode.doCached(
                     arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.toHost_, s1_.unsupported_, s1_.invalidKey_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetHashValueNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashValueNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary hashes_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidKey_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashValueNode.class)
         private static final class Cached1Data extends Node {
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidKey_;

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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.class)
      static final class GetHashValueOrDefaultNodeGen extends PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.Cached1Data cached1_cache;

         private GetHashValueOrDefaultNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.hashes_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.doCached(
                           arg0Value,
                           arg1Value,
                           arg2Value,
                           s0_.hashes_,
                           s0_.toGuestKey_,
                           s0_.toGuestDefaultValue_,
                           s0_.toHost_,
                           s0_.unsupported_,
                           s0_.invalidKey_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.doCached(
                  arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.toGuestDefaultValue_, s1_.toHost_, s1_.unsupported_, s1_.invalidKey_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.Cached0Data(this.cached0_cache));
                     s0_.hashes_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toGuestKey_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     s0_.toGuestDefaultValue_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.invalidKey_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.toGuestDefaultValue_, s0_.toHost_, s0_.unsupported_, s0_.invalidKey_
                     );
                  }
               }

               InteropLibrary hashes__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.Cached1Data()
                  );
                  hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toGuestKey_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  s1_.toGuestDefaultValue_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  s1_.toHost_ = this.createToHost();
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.invalidKey_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.doCached(
                     arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.toGuestDefaultValue_, s1_.toHost_, s1_.unsupported_, s1_.invalidKey_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary hashes_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestDefaultValue_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidKey_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetHashValueOrDefaultNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashValueOrDefaultNode.class)
         private static final class Cached1Data extends Node {
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestDefaultValue_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidKey_;

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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode.class)
      static final class GetHashValuesIteratorNodeGen extends PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetHashValuesIteratorNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private PolyglotLanguageContext.ToHostValueNode cached1_toHost_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private GetHashValuesIteratorNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetHashValuesIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.hashes_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toHost_, s0_.unsupported_
                        );
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
               InteropLibrary cached1_hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toHost_, this.cached1_unsupported_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashValuesIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetHashValuesIteratorNodeGen.Cached0Data(this.cached0_cache));
                     s0_.hashes_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toHost_, s0_.unsupported_
                     );
                  }
               }

               InteropLibrary cached1_hashes__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toHost_ = this.createToHost();
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toHost_, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetHashValuesIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetHashValuesIteratorNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetHashValuesIteratorNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetHashValuesIteratorNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary hashes_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetHashValuesIteratorNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.class)
      static final class GetIteratorNextElementNodeGen extends PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.Cached1Data cached1_cache;

         private GetIteratorNextElementNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.iterators_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.toHost_, s0_.unsupported_, s0_.stop_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary iterators__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.doCached(
                  arg0Value, arg1Value, arg2Value, iterators__, s1_.toHost_, s1_.unsupported_, s1_.stop_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.iterators_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.Cached0Data(this.cached0_cache));
                     s0_.iterators_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.stop_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.toHost_, s0_.unsupported_, s0_.stop_
                     );
                  }
               }

               InteropLibrary iterators__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.Cached1Data()
                  );
                  iterators__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toHost_ = this.createToHost();
                  s1_.unsupported_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.doCached(
                     arg0Value, arg1Value, arg2Value, iterators__, s1_.toHost_, s1_.unsupported_, s1_.stop_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary iterators_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile stop_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNextElementNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetIteratorNextElementNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile stop_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetIteratorNode.class)
      static final class GetIteratorNodeGen extends PolyglotValueDispatch.InteropValue.GetIteratorNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private PolyglotLanguageContext.ToHostValueNode cached1_toHost_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private GetIteratorNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.iterators_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetIteratorNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.toHost_, s0_.unsupported_
                        );
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
               InteropLibrary cached1_iterators__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.GetIteratorNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_iterators__, this.cached1_toHost_, this.cached1_unsupported_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.iterators_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNodeGen.Cached0Data(this.cached0_cache));
                     s0_.iterators_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetIteratorNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.toHost_, s0_.unsupported_
                     );
                  }
               }

               InteropLibrary cached1_iterators__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_iterators__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toHost_ = this.createToHost();
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.GetIteratorNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_iterators__, this.cached1_toHost_, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetIteratorNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetIteratorNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary iterators_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetIteratorNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetMemberKeysNode.class)
      static final class GetMemberKeysNodeGen extends PolyglotValueDispatch.InteropValue.GetMemberKeysNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetMemberKeysNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private PolyglotLanguageContext.ToHostValueNode cached1_toHost_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private GetMemberKeysNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetMemberKeysNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetMemberKeysNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toHost_, s0_.unsupported_
                        );
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.GetMemberKeysNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toHost_, this.cached1_unsupported_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetMemberKeysNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetMemberKeysNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetMemberKeysNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toHost_, s0_.unsupported_
                     );
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toHost_ = this.createToHost();
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.GetMemberKeysNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toHost_, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetMemberKeysNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetMemberKeysNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetMemberKeysNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetMemberKeysNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetMemberKeysNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetMemberKeysNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetMemberNode.class)
      static final class GetMemberNodeGen extends PolyglotValueDispatch.InteropValue.GetMemberNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.Cached1Data cached1_cache;

         private GetMemberNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetMemberNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.GetMemberNode.doCached(
                  arg0Value, arg1Value, arg2Value, objects__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.unknown_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetMemberNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                     );
                  }
               }

               InteropLibrary objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.Cached1Data()
                  );
                  objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toHost_ = this.createToHost();
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.unknown_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.GetMemberNode.doCached(
                     arg0Value, arg1Value, arg2Value, objects__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetMemberNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetMemberNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetMemberNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetMemberNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetMetaParentsNode.class)
      static final class GetMetaParentsNodeGen extends PolyglotValueDispatch.InteropValue.GetMetaParentsNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetMetaParentsNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private PolyglotLanguageContext.ToHostValueNode cached1_toHost_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private GetMetaParentsNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetMetaParentsNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetMetaParentsNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toHost_, s0_.unsupported_
                        );
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.GetMetaParentsNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toHost_, this.cached1_unsupported_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetMetaParentsNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetMetaParentsNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetMetaParentsNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toHost_, s0_.unsupported_
                     );
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toHost_ = this.createToHost();
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.GetMetaParentsNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toHost_, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetMetaParentsNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetMetaParentsNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetMetaParentsNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetMetaParentsNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetMetaParentsNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetMetaParentsNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode.class)
      static final class GetMetaQualifiedNameNodeGen extends PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetMetaQualifiedNameNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private InteropLibrary cached1_toString_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private GetMetaQualifiedNameNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetMetaQualifiedNameNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toString_, s0_.unsupported_
                        );
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

            String var8;
            try {
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toString_, this.cached1_unsupported_
               );
            } finally {
               encapsulating_.set(prev_);
            }

            return var8;
         }

         private String executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if (exclude == 0) {
                  int count0_ = 0;
                  PolyglotValueDispatchFactory.InteropValueFactory.GetMetaQualifiedNameNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetMetaQualifiedNameNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toString_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.createDispatched(1));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toString_, s0_.unsupported_
                     );
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toString_ = super.insert(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.createDispatched(1));
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toString_, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetMetaQualifiedNameNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetMetaQualifiedNameNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetMetaQualifiedNameNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetMetaQualifiedNameNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @Node.Child
            InteropLibrary toString_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetMetaQualifiedNameNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode.class)
      static final class GetMetaSimpleNameNodeGen extends PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.GetMetaSimpleNameNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private InteropLibrary cached1_toString_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private GetMetaSimpleNameNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.GetMetaSimpleNameNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toString_, s0_.unsupported_
                        );
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

            String var8;
            try {
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toString_, this.cached1_unsupported_
               );
            } finally {
               encapsulating_.set(prev_);
            }

            return var8;
         }

         private String executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if (exclude == 0) {
                  int count0_ = 0;
                  PolyglotValueDispatchFactory.InteropValueFactory.GetMetaSimpleNameNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.GetMetaSimpleNameNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toString_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.createDispatched(1));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toString_, s0_.unsupported_
                     );
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toString_ = super.insert(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.createDispatched(1));
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toString_, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.GetMetaSimpleNameNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.GetMetaSimpleNameNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.GetMetaSimpleNameNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.GetMetaSimpleNameNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @Node.Child
            InteropLibrary toString_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.GetMetaSimpleNameNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.HasArrayElementsNode.class)
      static final class HasArrayElementsNodeGen extends PolyglotValueDispatch.InteropValue.HasArrayElementsNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.HasArrayElementsNodeGen.Cached0Data cached0_cache;

         private HasArrayElementsNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.HasArrayElementsNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.arrays_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.HasArrayElementsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_);
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
               InteropLibrary cached1_arrays__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.HasArrayElementsNode.doCached(arg0Value, arg1Value, arg2Value, cached1_arrays__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasArrayElementsNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.arrays_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.HasArrayElementsNodeGen.Cached0Data(this.cached0_cache));
                     s0_.arrays_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.HasArrayElementsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrays_);
                  }
               }

               InteropLibrary cached1_arrays__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_arrays__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.HasArrayElementsNode.doCached(arg0Value, arg1Value, arg2Value, cached1_arrays__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasArrayElementsNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.HasArrayElementsNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.HasArrayElementsNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.HasArrayElementsNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.HasArrayElementsNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary arrays_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.HasArrayElementsNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.HasBufferElementsNode.class)
      static final class HasBufferElementsNodeGen extends PolyglotValueDispatch.InteropValue.HasBufferElementsNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.HasBufferElementsNodeGen.Cached0Data cached0_cache;

         private HasBufferElementsNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.HasBufferElementsNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.HasBufferElementsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_);
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
               InteropLibrary cached1_buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.HasBufferElementsNode.doCached(arg0Value, arg1Value, arg2Value, cached1_buffers__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasBufferElementsNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.HasBufferElementsNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.HasBufferElementsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_);
                  }
               }

               InteropLibrary cached1_buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.HasBufferElementsNode.doCached(arg0Value, arg1Value, arg2Value, cached1_buffers__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasBufferElementsNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.HasBufferElementsNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.HasBufferElementsNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.HasBufferElementsNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.HasBufferElementsNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.HasBufferElementsNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.HasHashEntriesNode.class)
      static final class HasHashEntriesNodeGen extends PolyglotValueDispatch.InteropValue.HasHashEntriesNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntriesNodeGen.Cached0Data cached0_cache;

         private HasHashEntriesNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntriesNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.hashes_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.HasHashEntriesNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_);
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
               InteropLibrary cached1_hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.HasHashEntriesNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntriesNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntriesNodeGen.Cached0Data(this.cached0_cache));
                     s0_.hashes_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.HasHashEntriesNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_);
                  }
               }

               InteropLibrary cached1_hashes__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.HasHashEntriesNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntriesNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.HasHashEntriesNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntriesNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.HasHashEntriesNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntriesNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary hashes_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntriesNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.HasHashEntryNode.class)
      static final class HasHashEntryNodeGen extends PolyglotValueDispatch.InteropValue.HasHashEntryNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntryNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotLanguageContext.ToGuestValueNode cached1_toGuestKey_;

         private HasHashEntryNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntryNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.hashes_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.HasHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_);
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
               InteropLibrary cached1_hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.HasHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toGuestKey_);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntryNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntryNodeGen.Cached0Data(this.cached0_cache));
                     s0_.hashes_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toGuestKey_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.HasHashEntryNode.doCached(arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_);
                  }
               }

               InteropLibrary cached1_hashes__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toGuestKey_ = super.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.HasHashEntryNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_hashes__, this.cached1_toGuestKey_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntryNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.HasHashEntryNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntryNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.HasHashEntryNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntryNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary hashes_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestKey_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.HasHashEntryNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode.class)
      static final class HasIteratorNextElementNodeGen extends PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNextElementNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private HasIteratorNextElementNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNextElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.iterators_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.unsupported_
                        );
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
               InteropLibrary cached1_iterators__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_iterators__, this.cached1_unsupported_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNextElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.iterators_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNextElementNodeGen.Cached0Data(this.cached0_cache));
                     s0_.iterators_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.iterators_, s0_.unsupported_
                     );
                  }
               }

               InteropLibrary cached1_iterators__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_iterators__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_iterators__, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNextElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNextElementNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.HasIteratorNextElementNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNextElementNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary iterators_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNextElementNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.HasIteratorNode.class)
      static final class HasIteratorNodeGen extends PolyglotValueDispatch.InteropValue.HasIteratorNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNodeGen.Cached0Data cached0_cache;

         private HasIteratorNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.iterators_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.HasIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_);
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
               InteropLibrary cached1_iterators__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.HasIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.iterators_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNodeGen.Cached0Data(this.cached0_cache));
                     s0_.iterators_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.HasIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_);
                  }
               }

               InteropLibrary cached1_iterators__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_iterators__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.HasIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.HasIteratorNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.HasIteratorNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary iterators_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.HasIteratorNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.HasMemberNode.class)
      static final class HasMemberNodeGen extends PolyglotValueDispatch.InteropValue.HasMemberNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.HasMemberNodeGen.Cached0Data cached0_cache;

         private HasMemberNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.HasMemberNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.HasMemberNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.HasMemberNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasMemberNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.HasMemberNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.HasMemberNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.HasMemberNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasMemberNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.HasMemberNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.HasMemberNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.HasMemberNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.HasMemberNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.HasMemberNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.HasMembersNode.class)
      static final class HasMembersNodeGen extends PolyglotValueDispatch.InteropValue.HasMembersNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.HasMembersNodeGen.Cached0Data cached0_cache;

         private HasMembersNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.HasMembersNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.HasMembersNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.HasMembersNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasMembersNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.HasMembersNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.HasMembersNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.HasMembersNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasMembersNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.HasMembersNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.HasMembersNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.HasMembersNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.HasMembersNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.HasMembersNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.HasMetaParentsNode.class)
      static final class HasMetaParentsNodeGen extends PolyglotValueDispatch.InteropValue.HasMetaParentsNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.HasMetaParentsNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private HasMetaParentsNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.HasMetaParentsNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.HasMetaParentsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
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

            Boolean var8;
            try {
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.HasMetaParentsNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_
               );
            } finally {
               encapsulating_.set(prev_);
            }

            return var8;
         }

         private boolean executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if (exclude == 0) {
                  int count0_ = 0;
                  PolyglotValueDispatchFactory.InteropValueFactory.HasMetaParentsNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.HasMetaParentsNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.HasMetaParentsNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.HasMetaParentsNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.HasMetaParentsNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.HasMetaParentsNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.HasMetaParentsNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.HasMetaParentsNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.HasMetaParentsNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.HasMetaParentsNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.IsBufferWritableNode.class)
      static final class IsBufferWritableNodeGen extends PolyglotValueDispatch.InteropValue.IsBufferWritableNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.IsBufferWritableNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private IsBufferWritableNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.IsBufferWritableNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.IsBufferWritableNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_);
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
               InteropLibrary cached1_buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.IsBufferWritableNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_buffers__, this.cached1_unsupported_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsBufferWritableNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.IsBufferWritableNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.IsBufferWritableNode.doCached(arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_);
                  }
               }

               InteropLibrary cached1_buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.IsBufferWritableNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_buffers__, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsBufferWritableNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.IsBufferWritableNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.IsBufferWritableNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.IsBufferWritableNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.IsBufferWritableNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.IsBufferWritableNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.IsDateNode.class)
      static final class IsDateNodeGen extends PolyglotValueDispatch.InteropValue.IsDateNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.IsDateNodeGen.Cached0Data cached0_cache;

         private IsDateNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.IsDateNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.IsDateNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.IsDateNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsDateNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.IsDateNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.IsDateNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.IsDateNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsDateNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.IsDateNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.IsDateNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.IsDateNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.IsDateNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.IsDateNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.IsDurationNode.class)
      static final class IsDurationNodeGen extends PolyglotValueDispatch.InteropValue.IsDurationNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.IsDurationNodeGen.Cached0Data cached0_cache;

         private IsDurationNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.IsDurationNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.IsDurationNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.IsDurationNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsDurationNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.IsDurationNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.IsDurationNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.IsDurationNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsDurationNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.IsDurationNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.IsDurationNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.IsDurationNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.IsDurationNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.IsDurationNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.IsExceptionNode.class)
      static final class IsExceptionNodeGen extends PolyglotValueDispatch.InteropValue.IsExceptionNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.IsExceptionNodeGen.Cached0Data cached0_cache;

         private IsExceptionNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.IsExceptionNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.IsExceptionNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.IsExceptionNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsExceptionNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.IsExceptionNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.IsExceptionNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.IsExceptionNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsExceptionNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.IsExceptionNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.IsExceptionNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.IsExceptionNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.IsExceptionNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.IsExceptionNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.IsIteratorNode.class)
      static final class IsIteratorNodeGen extends PolyglotValueDispatch.InteropValue.IsIteratorNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.IsIteratorNodeGen.Cached0Data cached0_cache;

         private IsIteratorNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.IsIteratorNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.iterators_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.IsIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_);
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
               InteropLibrary cached1_iterators__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.IsIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.iterators_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.IsIteratorNodeGen.Cached0Data(this.cached0_cache));
                     s0_.iterators_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.IsIteratorNode.doCached(arg0Value, arg1Value, arg2Value, s0_.iterators_);
                  }
               }

               InteropLibrary cached1_iterators__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_iterators__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.IsIteratorNode.doCached(arg0Value, arg1Value, arg2Value, cached1_iterators__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsIteratorNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.IsIteratorNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.IsIteratorNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.IsIteratorNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.IsIteratorNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary iterators_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.IsIteratorNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.IsMetaInstanceNode.class)
      static final class IsMetaInstanceNodeGen extends PolyglotValueDispatch.InteropValue.IsMetaInstanceNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.IsMetaInstanceNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotLanguageContext.ToGuestValueNode cached1_toGuest_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private IsMetaInstanceNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.IsMetaInstanceNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.IsMetaInstanceNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toGuest_, s0_.unsupported_
                        );
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

            Boolean var8;
            try {
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.IsMetaInstanceNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toGuest_, this.cached1_unsupported_
               );
            } finally {
               encapsulating_.set(prev_);
            }

            return var8;
         }

         private boolean executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if (exclude == 0) {
                  int count0_ = 0;
                  PolyglotValueDispatchFactory.InteropValueFactory.IsMetaInstanceNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.IsMetaInstanceNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toGuest_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.IsMetaInstanceNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toGuest_, s0_.unsupported_
                     );
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_toGuest_ = super.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.IsMetaInstanceNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_toGuest_, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsMetaInstanceNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.IsMetaInstanceNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.IsMetaInstanceNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.IsMetaInstanceNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.IsMetaInstanceNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuest_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.IsMetaInstanceNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.IsMetaObjectNode.class)
      static final class IsMetaObjectNodeGen extends PolyglotValueDispatch.InteropValue.IsMetaObjectNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.IsMetaObjectNodeGen.Cached0Data cached0_cache;

         private IsMetaObjectNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.IsMetaObjectNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.IsMetaObjectNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
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

            Boolean var8;
            try {
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.IsMetaObjectNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
            } finally {
               encapsulating_.set(prev_);
            }

            return var8;
         }

         private boolean executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if (exclude == 0) {
                  int count0_ = 0;
                  PolyglotValueDispatchFactory.InteropValueFactory.IsMetaObjectNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.IsMetaObjectNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.IsMetaObjectNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.IsMetaObjectNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsMetaObjectNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.IsMetaObjectNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.IsMetaObjectNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.IsMetaObjectNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.IsMetaObjectNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.IsMetaObjectNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.IsNativePointerNode.class)
      static final class IsNativePointerNodeGen extends PolyglotValueDispatch.InteropValue.IsNativePointerNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.IsNativePointerNodeGen.Cached0Data cached0_cache;

         private IsNativePointerNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.IsNativePointerNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.natives_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.IsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, s0_.natives_);
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
               InteropLibrary cached1_natives__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.IsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, cached1_natives__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsNativePointerNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.natives_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.IsNativePointerNodeGen.Cached0Data(this.cached0_cache));
                     s0_.natives_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.IsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, s0_.natives_);
                  }
               }

               InteropLibrary cached1_natives__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_natives__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.IsNativePointerNode.doCached(arg0Value, arg1Value, arg2Value, cached1_natives__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsNativePointerNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.IsNativePointerNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.IsNativePointerNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.IsNativePointerNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.IsNativePointerNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary natives_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.IsNativePointerNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.IsNullNode.class)
      static final class IsNullNodeGen extends PolyglotValueDispatch.InteropValue.IsNullNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.IsNullNodeGen.Cached0Data cached0_cache;

         private IsNullNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.IsNullNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.values_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.IsNullNode.doCached(arg0Value, arg1Value, arg2Value, s0_.values_);
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
               InteropLibrary cached1_values__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.IsNullNode.doCached(arg0Value, arg1Value, arg2Value, cached1_values__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsNullNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.values_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.IsNullNodeGen.Cached0Data(this.cached0_cache));
                     s0_.values_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.IsNullNode.doCached(arg0Value, arg1Value, arg2Value, s0_.values_);
                  }
               }

               InteropLibrary cached1_values__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_values__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.IsNullNode.doCached(arg0Value, arg1Value, arg2Value, cached1_values__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsNullNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.IsNullNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.IsNullNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.IsNullNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.IsNullNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary values_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.IsNullNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.IsTimeNode.class)
      static final class IsTimeNodeGen extends PolyglotValueDispatch.InteropValue.IsTimeNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.IsTimeNodeGen.Cached0Data cached0_cache;

         private IsTimeNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.IsTimeNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.IsTimeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.IsTimeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsTimeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.IsTimeNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.IsTimeNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.IsTimeNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsTimeNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.IsTimeNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.IsTimeNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.IsTimeNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.IsTimeNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.IsTimeNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.IsTimeZoneNode.class)
      static final class IsTimeZoneNodeGen extends PolyglotValueDispatch.InteropValue.IsTimeZoneNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.IsTimeZoneNodeGen.Cached0Data cached0_cache;

         private IsTimeZoneNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.IsTimeZoneNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.IsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.IsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsTimeZoneNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.IsTimeZoneNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.IsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.IsTimeZoneNode.doCached(arg0Value, arg1Value, arg2Value, cached1_objects__);
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
                  PolyglotValueDispatchFactory.InteropValueFactory.IsTimeZoneNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.IsTimeZoneNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.IsTimeZoneNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.IsTimeZoneNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.IsTimeZoneNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.IsTimeZoneNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.NewInstanceNode.class)
      static final class NewInstanceNodeGen extends PolyglotValueDispatch.InteropValue.NewInstanceNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.Cached1Data cached1_cache;

         private NewInstanceNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.instantiables_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.NewInstanceNode.doCached(
                           arg0Value,
                           arg1Value,
                           arg2Value,
                           s0_.instantiables_,
                           s0_.toGuestValues_,
                           s0_.toHostValue_,
                           s0_.arity_,
                           s0_.invalidArgument_,
                           s0_.unsupported_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary instantiables__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.NewInstanceNode.doCached(
                  arg0Value, arg1Value, arg2Value, instantiables__, s1_.toGuestValues_, s1_.toHostValue_, s1_.arity_, s1_.invalidArgument_, s1_.unsupported_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.instantiables_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.Cached0Data(this.cached0_cache));
                     s0_.instantiables_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toGuestValues_ = s0_.insertAccessor(PolyglotLanguageContext.ToGuestValuesNode.create());
                     s0_.toHostValue_ = this.createToHost();
                     s0_.arity_ = BranchProfile.create();
                     s0_.invalidArgument_ = BranchProfile.create();
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.NewInstanceNode.doCached(
                        arg0Value,
                        arg1Value,
                        arg2Value,
                        s0_.instantiables_,
                        s0_.toGuestValues_,
                        s0_.toHostValue_,
                        s0_.arity_,
                        s0_.invalidArgument_,
                        s0_.unsupported_
                     );
                  }
               }

               InteropLibrary instantiables__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.Cached1Data()
                  );
                  instantiables__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toGuestValues_ = s1_.insertAccessor(PolyglotLanguageContext.ToGuestValuesNode.create());
                  s1_.toHostValue_ = this.createToHost();
                  s1_.arity_ = BranchProfile.create();
                  s1_.invalidArgument_ = BranchProfile.create();
                  s1_.unsupported_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.NewInstanceNode.doCached(
                     arg0Value, arg1Value, arg2Value, instantiables__, s1_.toGuestValues_, s1_.toHostValue_, s1_.arity_, s1_.invalidArgument_, s1_.unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.NewInstanceNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.NewInstanceNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary instantiables_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValuesNode toGuestValues_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHostValue_;
            @CompilerDirectives.CompilationFinal
            BranchProfile arity_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidArgument_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.NewInstanceNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.NewInstanceNode.class)
         private static final class Cached1Data extends Node {
            @Node.Child
            PolyglotLanguageContext.ToGuestValuesNode toGuestValues_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHostValue_;
            @CompilerDirectives.CompilationFinal
            BranchProfile arity_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidArgument_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.PutHashEntryNode.class)
      static final class PutHashEntryNodeGen extends PolyglotValueDispatch.InteropValue.PutHashEntryNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.Cached1Data cached1_cache;

         private PutHashEntryNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.hashes_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.PutHashEntryNode.doCached(
                           arg0Value,
                           arg1Value,
                           arg2Value,
                           s0_.hashes_,
                           s0_.toGuestKey_,
                           s0_.toGuestValue_,
                           s0_.unsupported_,
                           s0_.invalidKey_,
                           s0_.invalidValue_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.PutHashEntryNode.doCached(
                  arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.toGuestValue_, s1_.unsupported_, s1_.invalidKey_, s1_.invalidValue_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.Cached0Data(this.cached0_cache));
                     s0_.hashes_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toGuestKey_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     s0_.toGuestValue_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.invalidKey_ = BranchProfile.create();
                     s0_.invalidValue_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.PutHashEntryNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.toGuestValue_, s0_.unsupported_, s0_.invalidKey_, s0_.invalidValue_
                     );
                  }
               }

               InteropLibrary hashes__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.Cached1Data()
                  );
                  hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toGuestKey_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  s1_.toGuestValue_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.invalidKey_ = BranchProfile.create();
                  s1_.invalidValue_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.PutHashEntryNode.doCached(
                     arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.toGuestValue_, s1_.unsupported_, s1_.invalidKey_, s1_.invalidValue_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.PutHashEntryNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.PutHashEntryNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary hashes_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestValue_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidKey_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.PutHashEntryNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.PutHashEntryNode.class)
         private static final class Cached1Data extends Node {
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestValue_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidKey_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.PutMemberNode.class)
      static final class PutMemberNodeGen extends PolyglotValueDispatch.InteropValue.PutMemberNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.PutMemberNodeGen.CachedData cached_cache;

         private PutMemberNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               PolyglotValueDispatchFactory.InteropValueFactory.PutMemberNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ != null) {
                  return PolyglotValueDispatch.InteropValue.PutMemberNode.doCached(
                     arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toGuestValue_, s0_.unsupported_, s0_.invalidValue_, s0_.unknown_
                  );
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
         }

         private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var8;
            try {
               int state_0 = this.state_0_;
               PolyglotValueDispatchFactory.InteropValueFactory.PutMemberNodeGen.CachedData s0_ = super.insert(
                  new PolyglotValueDispatchFactory.InteropValueFactory.PutMemberNodeGen.CachedData()
               );
               s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.createDispatched(5));
               s0_.toGuestValue_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
               s0_.unsupported_ = BranchProfile.create();
               s0_.invalidValue_ = BranchProfile.create();
               s0_.unknown_ = BranchProfile.create();
               VarHandle.storeStoreFence();
               this.cached_cache = s0_;
               int var12;
               this.state_0_ = var12 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               var8 = PolyglotValueDispatch.InteropValue.PutMemberNode.doCached(
                  arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.toGuestValue_, s0_.unsupported_, s0_.invalidValue_, s0_.unknown_
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
         }

         public static PolyglotValueDispatch.InteropValue.PutMemberNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.PutMemberNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.PutMemberNode.class)
         private static final class CachedData extends Node {
            @Node.Child
            InteropLibrary objects_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestValue_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            CachedData() {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferByteNode.class)
      static final class ReadBufferByteNodeGen extends PolyglotValueDispatch.InteropValue.ReadBufferByteNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.Cached1Data cached1_cache;

         private ReadBufferByteNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.ReadBufferByteNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.ReadBufferByteNode.doCached(
                  arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.unknown_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.ReadBufferByteNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                     );
                  }
               }

               InteropLibrary buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.Cached1Data()
                  );
                  buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toHost_ = this.createToHost();
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.unknown_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.ReadBufferByteNode.doCached(
                     arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.ReadBufferByteNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferByteNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferByteNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferByteNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.class)
      static final class ReadBufferDoubleNodeGen extends PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.Cached1Data cached1_cache;

         private ReadBufferDoubleNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.doCached(
                  arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.unknown_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                     );
                  }
               }

               InteropLibrary buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.Cached1Data()
                  );
                  buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toHost_ = this.createToHost();
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.unknown_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.doCached(
                     arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferDoubleNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferDoubleNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.class)
      static final class ReadBufferFloatNodeGen extends PolyglotValueDispatch.InteropValue.ReadBufferFloatNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.Cached1Data cached1_cache;

         private ReadBufferFloatNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.doCached(
                  arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.unknown_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                     );
                  }
               }

               InteropLibrary buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.Cached1Data()
                  );
                  buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toHost_ = this.createToHost();
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.unknown_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.doCached(
                     arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.ReadBufferFloatNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferFloatNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferFloatNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferIntNode.class)
      static final class ReadBufferIntNodeGen extends PolyglotValueDispatch.InteropValue.ReadBufferIntNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.Cached1Data cached1_cache;

         private ReadBufferIntNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.ReadBufferIntNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.ReadBufferIntNode.doCached(
                  arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.unknown_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.ReadBufferIntNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                     );
                  }
               }

               InteropLibrary buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.Cached1Data()
                  );
                  buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toHost_ = this.createToHost();
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.unknown_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.ReadBufferIntNode.doCached(
                     arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.ReadBufferIntNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferIntNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferIntNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferIntNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferLongNode.class)
      static final class ReadBufferLongNodeGen extends PolyglotValueDispatch.InteropValue.ReadBufferLongNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.Cached1Data cached1_cache;

         private ReadBufferLongNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.ReadBufferLongNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.ReadBufferLongNode.doCached(
                  arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.unknown_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.ReadBufferLongNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                     );
                  }
               }

               InteropLibrary buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.Cached1Data()
                  );
                  buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toHost_ = this.createToHost();
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.unknown_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.ReadBufferLongNode.doCached(
                     arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.ReadBufferLongNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferLongNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferLongNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferLongNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferShortNode.class)
      static final class ReadBufferShortNodeGen extends PolyglotValueDispatch.InteropValue.ReadBufferShortNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.Cached1Data cached1_cache;

         private ReadBufferShortNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.ReadBufferShortNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.ReadBufferShortNode.doCached(
                  arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toHost_ = this.createToHost();
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.unknown_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.ReadBufferShortNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.toHost_, s0_.unsupported_, s0_.unknown_
                     );
                  }
               }

               InteropLibrary buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.Cached1Data()
                  );
                  buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toHost_ = this.createToHost();
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.unknown_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.ReadBufferShortNode.doCached(
                     arg0Value, arg1Value, arg2Value, buffers__, s1_.toHost_, s1_.unsupported_, s1_.unknown_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.ReadBufferShortNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferShortNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.ReadBufferShortNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.ReadBufferShortNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            PolyglotLanguageContext.ToHostValueNode toHost_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.RemoveArrayElementNode.class)
      static final class RemoveArrayElementNodeGen extends PolyglotValueDispatch.InteropValue.RemoveArrayElementNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.RemoveArrayElementNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_invalidIndex_;

         private RemoveArrayElementNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.RemoveArrayElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.arrays_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.RemoveArrayElementNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.unsupported_, s0_.invalidIndex_
                        );
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
               InteropLibrary cached1_arrays__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.RemoveArrayElementNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_arrays__, this.cached1_unsupported_, this.cached1_invalidIndex_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.RemoveArrayElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.arrays_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.RemoveArrayElementNodeGen.Cached0Data(this.cached0_cache));
                     s0_.arrays_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.invalidIndex_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.RemoveArrayElementNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.unsupported_, s0_.invalidIndex_
                     );
                  }
               }

               InteropLibrary cached1_arrays__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_arrays__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  this.cached1_invalidIndex_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.RemoveArrayElementNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_arrays__, this.cached1_unsupported_, this.cached1_invalidIndex_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.RemoveArrayElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.RemoveArrayElementNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.RemoveArrayElementNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.RemoveArrayElementNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.RemoveArrayElementNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary arrays_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.RemoveArrayElementNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.class)
      static final class RemoveHashEntryNodeGen extends PolyglotValueDispatch.InteropValue.RemoveHashEntryNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.Cached1Data cached1_cache;

         private RemoveHashEntryNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.hashes_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.unsupported_, s0_.invalidKey_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.doCached(
                  arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.unsupported_, s1_.invalidKey_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.hashes_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.Cached0Data(this.cached0_cache));
                     s0_.hashes_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toGuestKey_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.invalidKey_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.hashes_, s0_.toGuestKey_, s0_.unsupported_, s0_.invalidKey_
                     );
                  }
               }

               InteropLibrary hashes__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.Cached1Data()
                  );
                  hashes__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toGuestKey_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.invalidKey_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.doCached(
                     arg0Value, arg1Value, arg2Value, hashes__, s1_.toGuestKey_, s1_.unsupported_, s1_.invalidKey_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.RemoveHashEntryNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary hashes_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidKey_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.RemoveHashEntryNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.RemoveHashEntryNode.class)
         private static final class Cached1Data extends Node {
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestKey_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidKey_;

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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.RemoveMemberNode.class)
      static final class RemoveMemberNodeGen extends PolyglotValueDispatch.InteropValue.RemoveMemberNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.RemoveMemberNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unknown_;

         private RemoveMemberNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.RemoveMemberNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.RemoveMemberNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_, s0_.unknown_
                        );
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.RemoveMemberNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_, this.cached1_unknown_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.RemoveMemberNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.RemoveMemberNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.unknown_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.RemoveMemberNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_, s0_.unknown_
                     );
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  this.cached1_unknown_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.RemoveMemberNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_, this.cached1_unknown_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.RemoveMemberNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.RemoveMemberNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.RemoveMemberNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.RemoveMemberNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.RemoveMemberNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unknown_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.RemoveMemberNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.SetArrayElementNode.class)
      static final class SetArrayElementNodeGen extends PolyglotValueDispatch.InteropValue.SetArrayElementNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.Cached1Data cached1_cache;

         private SetArrayElementNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.arrays_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.SetArrayElementNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.toGuestValue_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary arrays__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.SetArrayElementNode.doCached(
                  arg0Value, arg1Value, arg2Value, arrays__, s1_.toGuestValue_, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.arrays_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.Cached0Data(this.cached0_cache));
                     s0_.arrays_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.toGuestValue_ = s0_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.invalidIndex_ = BranchProfile.create();
                     s0_.invalidValue_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.SetArrayElementNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.arrays_, s0_.toGuestValue_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                     );
                  }
               }

               InteropLibrary arrays__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.Cached1Data()
                  );
                  arrays__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.toGuestValue_ = s1_.insertAccessor(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.invalidIndex_ = BranchProfile.create();
                  s1_.invalidValue_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.SetArrayElementNode.doCached(
                     arg0Value, arg1Value, arg2Value, arrays__, s1_.toGuestValue_, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.SetArrayElementNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.SetArrayElementNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary arrays_;
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestValue_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.SetArrayElementNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.SetArrayElementNode.class)
         private static final class Cached1Data extends Node {
            @Node.Child
            PolyglotLanguageContext.ToGuestValueNode toGuestValue_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.ThrowExceptionNode.class)
      static final class ThrowExceptionNodeGen extends PolyglotValueDispatch.InteropValue.ThrowExceptionNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.ThrowExceptionNodeGen.Cached0Data cached0_cache;
         @CompilerDirectives.CompilationFinal
         private BranchProfile cached1_unsupported_;

         private ThrowExceptionNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.ThrowExceptionNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.objects_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.ThrowExceptionNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
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
               InteropLibrary cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var8 = PolyglotValueDispatch.InteropValue.ThrowExceptionNode.doCached(
                  arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ThrowExceptionNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.objects_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.ThrowExceptionNodeGen.Cached0Data(this.cached0_cache));
                     s0_.objects_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.ThrowExceptionNode.doCached(arg0Value, arg1Value, arg2Value, s0_.objects_, s0_.unsupported_);
                  }
               }

               InteropLibrary cached1_objects__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  cached1_objects__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.cached1_unsupported_ = BranchProfile.create();
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -2;
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotValueDispatch.InteropValue.ThrowExceptionNode.doCached(
                     arg0Value, arg1Value, arg2Value, cached1_objects__, this.cached1_unsupported_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.ThrowExceptionNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.ThrowExceptionNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.ThrowExceptionNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.ThrowExceptionNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.ThrowExceptionNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary objects_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.ThrowExceptionNodeGen.Cached0Data next_) {
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

      @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferByteNode.class)
      static final class WriteBufferByteNodeGen extends PolyglotValueDispatch.InteropValue.WriteBufferByteNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.Cached1Data cached1_cache;

         private WriteBufferByteNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.WriteBufferByteNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.WriteBufferByteNode.doCached(
                  arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.invalidIndex_ = BranchProfile.create();
                     s0_.invalidValue_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.WriteBufferByteNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                     );
                  }
               }

               InteropLibrary buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.Cached1Data()
                  );
                  buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.invalidIndex_ = BranchProfile.create();
                  s1_.invalidValue_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.WriteBufferByteNode.doCached(
                     arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.WriteBufferByteNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferByteNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferByteNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferByteNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.class)
      static final class WriteBufferDoubleNodeGen extends PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.Cached1Data cached1_cache;

         private WriteBufferDoubleNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.doCached(
                  arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.invalidIndex_ = BranchProfile.create();
                     s0_.invalidValue_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                     );
                  }
               }

               InteropLibrary buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.Cached1Data()
                  );
                  buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.invalidIndex_ = BranchProfile.create();
                  s1_.invalidValue_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.doCached(
                     arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferDoubleNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferDoubleNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.class)
      static final class WriteBufferFloatNodeGen extends PolyglotValueDispatch.InteropValue.WriteBufferFloatNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.Cached1Data cached1_cache;

         private WriteBufferFloatNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.doCached(
                  arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.invalidIndex_ = BranchProfile.create();
                     s0_.invalidValue_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                     );
                  }
               }

               InteropLibrary buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.Cached1Data()
                  );
                  buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.invalidIndex_ = BranchProfile.create();
                  s1_.invalidValue_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.doCached(
                     arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.WriteBufferFloatNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferFloatNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferFloatNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferIntNode.class)
      static final class WriteBufferIntNodeGen extends PolyglotValueDispatch.InteropValue.WriteBufferIntNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.Cached1Data cached1_cache;

         private WriteBufferIntNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.WriteBufferIntNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.WriteBufferIntNode.doCached(
                  arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.invalidIndex_ = BranchProfile.create();
                     s0_.invalidValue_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.WriteBufferIntNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                     );
                  }
               }

               InteropLibrary buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.Cached1Data()
                  );
                  buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.invalidIndex_ = BranchProfile.create();
                  s1_.invalidValue_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.WriteBufferIntNode.doCached(
                     arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.WriteBufferIntNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferIntNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferIntNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferIntNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferLongNode.class)
      static final class WriteBufferLongNodeGen extends PolyglotValueDispatch.InteropValue.WriteBufferLongNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.Cached1Data cached1_cache;

         private WriteBufferLongNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.WriteBufferLongNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.WriteBufferLongNode.doCached(
                  arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.invalidIndex_ = BranchProfile.create();
                     s0_.invalidValue_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.WriteBufferLongNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                     );
                  }
               }

               InteropLibrary buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.Cached1Data()
                  );
                  buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.invalidIndex_ = BranchProfile.create();
                  s1_.invalidValue_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.WriteBufferLongNode.doCached(
                     arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.WriteBufferLongNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferLongNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferLongNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferLongNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }

      @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferShortNode.class)
      static final class WriteBufferShortNodeGen extends PolyglotValueDispatch.InteropValue.WriteBufferShortNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.Cached0Data cached0_cache;
         @Node.Child
         private PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.Cached1Data cached1_cache;

         private WriteBufferShortNodeGen(PolyglotValueDispatch.InteropValue interop) {
            super(interop);
         }

         @ExplodeLoop
         @Override
         protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.Cached0Data s0_ = this.cached0_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (s0_.buffers_.accepts(arg1Value)) {
                        return PolyglotValueDispatch.InteropValue.WriteBufferShortNode.doCached(
                           arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.Cached1Data s1_ = this.cached1_cache;
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
            PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.Cached1Data s1_,
            PolyglotLanguageContext arg0Value,
            Object arg1Value,
            Object[] arg2Value
         ) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            Object var9;
            try {
               InteropLibrary buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
               var9 = PolyglotValueDispatch.InteropValue.WriteBufferShortNode.doCached(
                  arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
               );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.buffers_.accepts(arg1Value)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.Cached0Data(this.cached0_cache));
                     s0_.buffers_ = s0_.insertAccessor(PolyglotValueDispatchFactory.INTEROP_LIBRARY_.create(arg1Value));
                     s0_.unsupported_ = BranchProfile.create();
                     s0_.invalidIndex_ = BranchProfile.create();
                     s0_.invalidValue_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.cached0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotValueDispatch.InteropValue.WriteBufferShortNode.doCached(
                        arg0Value, arg1Value, arg2Value, s0_.buffers_, s0_.unsupported_, s0_.invalidIndex_, s0_.invalidValue_
                     );
                  }
               }

               InteropLibrary buffers__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.Cached1Data s1_ = super.insert(
                     new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.Cached1Data()
                  );
                  buffers__ = PolyglotValueDispatchFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.unsupported_ = BranchProfile.create();
                  s1_.invalidIndex_ = BranchProfile.create();
                  s1_.invalidValue_ = BranchProfile.create();
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
                  return PolyglotValueDispatch.InteropValue.WriteBufferShortNode.doCached(
                     arg0Value, arg1Value, arg2Value, buffers__, s1_.unsupported_, s1_.invalidIndex_, s1_.invalidValue_
                  );
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
                  PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.Cached0Data s0_ = this.cached0_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         public static PolyglotValueDispatch.InteropValue.WriteBufferShortNode create(PolyglotValueDispatch.InteropValue interop) {
            return new PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen(interop);
         }

         @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferShortNode.class)
         private static final class Cached0Data extends Node {
            @Node.Child
            PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.Cached0Data next_;
            @Node.Child
            InteropLibrary buffers_;
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached0Data(PolyglotValueDispatchFactory.InteropValueFactory.WriteBufferShortNodeGen.Cached0Data next_) {
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

         @GeneratedBy(PolyglotValueDispatch.InteropValue.WriteBufferShortNode.class)
         private static final class Cached1Data extends Node {
            @CompilerDirectives.CompilationFinal
            BranchProfile unsupported_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidIndex_;
            @CompilerDirectives.CompilationFinal
            BranchProfile invalidValue_;

            Cached1Data() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }
         }
      }
   }
}
