package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TRegexUtil.class)
public final class TRegexUtilFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(TRegexUtil.InteropIsMemberReadableNode.class)
   public static final class InteropIsMemberReadableNodeGen extends TRegexUtil.InteropIsMemberReadableNode {
      private static final TRegexUtilFactory.InteropIsMemberReadableNodeGen.Uncached UNCACHED = new TRegexUtilFactory.InteropIsMemberReadableNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TRegexUtilFactory.InteropIsMemberReadableNodeGen.Read0Data read0_cache;

      private InteropIsMemberReadableNodeGen() {
      }

      @ExplodeLoop
      @Override
      public boolean execute(Object arg0Value, String arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (TRegexUtilFactory.InteropIsMemberReadableNodeGen.Read0Data s0_ = this.read0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.objs_.accepts(arg0Value)) {
                     return TRegexUtil.InteropIsMemberReadableNode.read(arg0Value, arg1Value, s0_.objs_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.read1Boundary(state_0, arg0Value, arg1Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      @CompilerDirectives.TruffleBoundary
      private boolean read1Boundary(int state_0, Object arg0Value, String arg1Value) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         boolean var7;
         try {
            InteropLibrary read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value);
            var7 = TRegexUtil.InteropIsMemberReadableNode.read(arg0Value, arg1Value, read1_objs__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      private boolean executeAndSpecialize(Object arg0Value, String arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               TRegexUtilFactory.InteropIsMemberReadableNodeGen.Read0Data s0_ = this.read0_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && !s0_.objs_.accepts(arg0Value)) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < 9) {
                  s0_ = super.insert(new TRegexUtilFactory.InteropIsMemberReadableNodeGen.Read0Data(this.read0_cache));
                  s0_.objs_ = s0_.insertAccessor(TRegexUtilFactory.INTEROP_LIBRARY_.create(arg0Value));
                  VarHandle.storeStoreFence();
                  this.read0_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return TRegexUtil.InteropIsMemberReadableNode.read(arg0Value, arg1Value, s0_.objs_);
               }
            }

            InteropLibrary read1_objs__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value);
               int var21;
               this.exclude_ = var21 = exclude | 1;
               this.read0_cache = null;
               state_0 &= -2;
               int var20;
               this.state_0_ = var20 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TRegexUtil.InteropIsMemberReadableNode.read(arg0Value, arg1Value, read1_objs__);
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
               TRegexUtilFactory.InteropIsMemberReadableNodeGen.Read0Data s0_ = this.read0_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TRegexUtil.InteropIsMemberReadableNode create() {
         return new TRegexUtilFactory.InteropIsMemberReadableNodeGen();
      }

      public static TRegexUtil.InteropIsMemberReadableNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TRegexUtil.InteropIsMemberReadableNode.class)
      private static final class Read0Data extends Node {
         @Node.Child
         TRegexUtilFactory.InteropIsMemberReadableNodeGen.Read0Data next_;
         @Node.Child
         InteropLibrary objs_;

         Read0Data(TRegexUtilFactory.InteropIsMemberReadableNodeGen.Read0Data next_) {
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

      @GeneratedBy(TRegexUtil.InteropIsMemberReadableNode.class)
      @DenyReplace
      private static final class Uncached extends TRegexUtil.InteropIsMemberReadableNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(Object arg0Value, String arg1Value) {
            return TRegexUtil.InteropIsMemberReadableNode.read(arg0Value, arg1Value, TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value));
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

   @GeneratedBy(TRegexUtil.InteropReadBooleanMemberNode.class)
   public static final class InteropReadBooleanMemberNodeGen extends TRegexUtil.InteropReadBooleanMemberNode {
      private static final TRegexUtilFactory.InteropReadBooleanMemberNodeGen.Uncached UNCACHED = new TRegexUtilFactory.InteropReadBooleanMemberNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TRegexUtilFactory.InteropReadBooleanMemberNodeGen.Read0Data read0_cache;
      @Node.Child
      private TRegexUtil.InteropToBooleanNode read1_coerceNode_;

      private InteropReadBooleanMemberNodeGen() {
      }

      @ExplodeLoop
      @Override
      public boolean execute(Object arg0Value, String arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (TRegexUtilFactory.InteropReadBooleanMemberNodeGen.Read0Data s0_ = this.read0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberReadable(arg0Value, arg1Value)) {
                     return TRegexUtil.InteropReadBooleanMemberNode.read(arg0Value, arg1Value, s0_.coerceNode_, s0_.objs_);
                  }
               }
            }

            label62:
            if ((state_0 & 2) != 0) {
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               boolean var7;
               try {
                  InteropLibrary read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
                  if (!read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                     break label62;
                  }

                  var7 = this.read1Boundary(state_0, arg0Value, arg1Value);
               } finally {
                  encapsulating_.set(prev_);
               }

               return var7;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      @CompilerDirectives.TruffleBoundary
      private boolean read1Boundary(int state_0, Object arg0Value, String arg1Value) {
         InteropLibrary read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
         return TRegexUtil.InteropReadBooleanMemberNode.read(arg0Value, arg1Value, this.read1_coerceNode_, read1_objs__);
      }

      private boolean executeAndSpecialize(Object arg0Value, String arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               TRegexUtilFactory.InteropReadBooleanMemberNodeGen.Read0Data s0_ = this.read0_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (!s0_.objs_.accepts(arg0Value) || !s0_.objs_.isMemberReadable(arg0Value, arg1Value))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  InteropLibrary objs__ = super.insert(TRegexUtilFactory.INTEROP_LIBRARY_.create(arg0Value));
                  if (objs__.isMemberReadable(arg0Value, arg1Value) && count0_ < 9) {
                     s0_ = super.insert(new TRegexUtilFactory.InteropReadBooleanMemberNodeGen.Read0Data(this.read0_cache));
                     s0_.coerceNode_ = s0_.insertAccessor(TRegexUtilFactory.InteropToBooleanNodeGen.create());
                     s0_.objs_ = s0_.insertAccessor(objs__);
                     VarHandle.storeStoreFence();
                     this.read0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return TRegexUtil.InteropReadBooleanMemberNode.read(arg0Value, arg1Value, s0_.coerceNode_, s0_.objs_);
               }
            }

            InteropLibrary read1_objs__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
               if (read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                  this.read1_coerceNode_ = super.insert(TRegexUtilFactory.InteropToBooleanNodeGen.create());
                  int var21;
                  this.exclude_ = var21 = exclude | 1;
                  this.read0_cache = null;
                  state_0 &= -2;
                  int var20;
                  this.state_0_ = var20 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return TRegexUtil.InteropReadBooleanMemberNode.read(arg0Value, arg1Value, this.read1_coerceNode_, read1_objs__);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
               TRegexUtilFactory.InteropReadBooleanMemberNodeGen.Read0Data s0_ = this.read0_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TRegexUtil.InteropReadBooleanMemberNode create() {
         return new TRegexUtilFactory.InteropReadBooleanMemberNodeGen();
      }

      public static TRegexUtil.InteropReadBooleanMemberNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TRegexUtil.InteropReadBooleanMemberNode.class)
      private static final class Read0Data extends Node {
         @Node.Child
         TRegexUtilFactory.InteropReadBooleanMemberNodeGen.Read0Data next_;
         @Node.Child
         TRegexUtil.InteropToBooleanNode coerceNode_;
         @Node.Child
         InteropLibrary objs_;

         Read0Data(TRegexUtilFactory.InteropReadBooleanMemberNodeGen.Read0Data next_) {
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

      @GeneratedBy(TRegexUtil.InteropReadBooleanMemberNode.class)
      @DenyReplace
      private static final class Uncached extends TRegexUtil.InteropReadBooleanMemberNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(Object arg0Value, String arg1Value) {
            if (TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value).isMemberReadable(arg0Value, arg1Value)) {
               return TRegexUtil.InteropReadBooleanMemberNode.read(
                  arg0Value, arg1Value, TRegexUtilFactory.InteropToBooleanNodeGen.getUncached(), TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value)
               );
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

   @GeneratedBy(TRegexUtil.InteropReadIntMemberNode.class)
   public static final class InteropReadIntMemberNodeGen extends TRegexUtil.InteropReadIntMemberNode {
      private static final TRegexUtilFactory.InteropReadIntMemberNodeGen.Uncached UNCACHED = new TRegexUtilFactory.InteropReadIntMemberNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TRegexUtilFactory.InteropReadIntMemberNodeGen.Read0Data read0_cache;
      @Node.Child
      private TRegexUtil.InteropToIntNode read1_coerceNode_;

      private InteropReadIntMemberNodeGen() {
      }

      @ExplodeLoop
      @Override
      public int execute(Object arg0Value, String arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (TRegexUtilFactory.InteropReadIntMemberNodeGen.Read0Data s0_ = this.read0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberReadable(arg0Value, arg1Value)) {
                     return TRegexUtil.InteropReadIntMemberNode.read(arg0Value, arg1Value, s0_.coerceNode_, s0_.objs_);
                  }
               }
            }

            label62:
            if ((state_0 & 2) != 0) {
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               int var7;
               try {
                  InteropLibrary read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
                  if (!read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                     break label62;
                  }

                  var7 = this.read1Boundary(state_0, arg0Value, arg1Value);
               } finally {
                  encapsulating_.set(prev_);
               }

               return var7;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      @CompilerDirectives.TruffleBoundary
      private int read1Boundary(int state_0, Object arg0Value, String arg1Value) {
         InteropLibrary read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
         return TRegexUtil.InteropReadIntMemberNode.read(arg0Value, arg1Value, this.read1_coerceNode_, read1_objs__);
      }

      private int executeAndSpecialize(Object arg0Value, String arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               TRegexUtilFactory.InteropReadIntMemberNodeGen.Read0Data s0_ = this.read0_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (!s0_.objs_.accepts(arg0Value) || !s0_.objs_.isMemberReadable(arg0Value, arg1Value))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  InteropLibrary objs__ = super.insert(TRegexUtilFactory.INTEROP_LIBRARY_.create(arg0Value));
                  if (objs__.isMemberReadable(arg0Value, arg1Value) && count0_ < 9) {
                     s0_ = super.insert(new TRegexUtilFactory.InteropReadIntMemberNodeGen.Read0Data(this.read0_cache));
                     s0_.coerceNode_ = s0_.insertAccessor(TRegexUtilFactory.InteropToIntNodeGen.create());
                     s0_.objs_ = s0_.insertAccessor(objs__);
                     VarHandle.storeStoreFence();
                     this.read0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return TRegexUtil.InteropReadIntMemberNode.read(arg0Value, arg1Value, s0_.coerceNode_, s0_.objs_);
               }
            }

            InteropLibrary read1_objs__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
               if (read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                  this.read1_coerceNode_ = super.insert(TRegexUtilFactory.InteropToIntNodeGen.create());
                  int var21;
                  this.exclude_ = var21 = exclude | 1;
                  this.read0_cache = null;
                  state_0 &= -2;
                  int var20;
                  this.state_0_ = var20 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return TRegexUtil.InteropReadIntMemberNode.read(arg0Value, arg1Value, this.read1_coerceNode_, read1_objs__);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
               TRegexUtilFactory.InteropReadIntMemberNodeGen.Read0Data s0_ = this.read0_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TRegexUtil.InteropReadIntMemberNode create() {
         return new TRegexUtilFactory.InteropReadIntMemberNodeGen();
      }

      public static TRegexUtil.InteropReadIntMemberNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TRegexUtil.InteropReadIntMemberNode.class)
      private static final class Read0Data extends Node {
         @Node.Child
         TRegexUtilFactory.InteropReadIntMemberNodeGen.Read0Data next_;
         @Node.Child
         TRegexUtil.InteropToIntNode coerceNode_;
         @Node.Child
         InteropLibrary objs_;

         Read0Data(TRegexUtilFactory.InteropReadIntMemberNodeGen.Read0Data next_) {
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

      @GeneratedBy(TRegexUtil.InteropReadIntMemberNode.class)
      @DenyReplace
      private static final class Uncached extends TRegexUtil.InteropReadIntMemberNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(Object arg0Value, String arg1Value) {
            if (TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value).isMemberReadable(arg0Value, arg1Value)) {
               return TRegexUtil.InteropReadIntMemberNode.read(
                  arg0Value, arg1Value, TRegexUtilFactory.InteropToIntNodeGen.getUncached(), TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value)
               );
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

   @GeneratedBy(TRegexUtil.InteropReadMemberNode.class)
   public static final class InteropReadMemberNodeGen extends TRegexUtil.InteropReadMemberNode {
      private static final TRegexUtilFactory.InteropReadMemberNodeGen.Uncached UNCACHED = new TRegexUtilFactory.InteropReadMemberNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TRegexUtilFactory.InteropReadMemberNodeGen.Read0Data read0_cache;

      private InteropReadMemberNodeGen() {
      }

      @ExplodeLoop
      @Override
      public Object execute(Object arg0Value, String arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (TRegexUtilFactory.InteropReadMemberNodeGen.Read0Data s0_ = this.read0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberReadable(arg0Value, arg1Value)) {
                     return TRegexUtil.InteropReadMemberNode.read(arg0Value, arg1Value, s0_.objs_);
                  }
               }
            }

            label62:
            if ((state_0 & 2) != 0) {
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               Object var7;
               try {
                  InteropLibrary read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
                  if (!read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                     break label62;
                  }

                  var7 = this.read1Boundary(state_0, arg0Value, arg1Value);
               } finally {
                  encapsulating_.set(prev_);
               }

               return var7;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      @CompilerDirectives.TruffleBoundary
      private Object read1Boundary(int state_0, Object arg0Value, String arg1Value) {
         InteropLibrary read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
         return TRegexUtil.InteropReadMemberNode.read(arg0Value, arg1Value, read1_objs__);
      }

      private Object executeAndSpecialize(Object arg0Value, String arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               TRegexUtilFactory.InteropReadMemberNodeGen.Read0Data s0_ = this.read0_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (!s0_.objs_.accepts(arg0Value) || !s0_.objs_.isMemberReadable(arg0Value, arg1Value))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  InteropLibrary objs__ = super.insert(TRegexUtilFactory.INTEROP_LIBRARY_.create(arg0Value));
                  if (objs__.isMemberReadable(arg0Value, arg1Value) && count0_ < 9) {
                     s0_ = super.insert(new TRegexUtilFactory.InteropReadMemberNodeGen.Read0Data(this.read0_cache));
                     s0_.objs_ = s0_.insertAccessor(objs__);
                     VarHandle.storeStoreFence();
                     this.read0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return TRegexUtil.InteropReadMemberNode.read(arg0Value, arg1Value, s0_.objs_);
               }
            }

            InteropLibrary read1_objs__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
               if (read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                  int var21;
                  this.exclude_ = var21 = exclude | 1;
                  this.read0_cache = null;
                  state_0 &= -2;
                  int var20;
                  this.state_0_ = var20 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return TRegexUtil.InteropReadMemberNode.read(arg0Value, arg1Value, read1_objs__);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
               TRegexUtilFactory.InteropReadMemberNodeGen.Read0Data s0_ = this.read0_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TRegexUtil.InteropReadMemberNode create() {
         return new TRegexUtilFactory.InteropReadMemberNodeGen();
      }

      public static TRegexUtil.InteropReadMemberNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TRegexUtil.InteropReadMemberNode.class)
      private static final class Read0Data extends Node {
         @Node.Child
         TRegexUtilFactory.InteropReadMemberNodeGen.Read0Data next_;
         @Node.Child
         InteropLibrary objs_;

         Read0Data(TRegexUtilFactory.InteropReadMemberNodeGen.Read0Data next_) {
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

      @GeneratedBy(TRegexUtil.InteropReadMemberNode.class)
      @DenyReplace
      private static final class Uncached extends TRegexUtil.InteropReadMemberNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public Object execute(Object arg0Value, String arg1Value) {
            if (TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value).isMemberReadable(arg0Value, arg1Value)) {
               return TRegexUtil.InteropReadMemberNode.read(arg0Value, arg1Value, TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value));
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

   @GeneratedBy(TRegexUtil.InteropReadStringMemberNode.class)
   public static final class InteropReadStringMemberNodeGen extends TRegexUtil.InteropReadStringMemberNode {
      private static final TRegexUtilFactory.InteropReadStringMemberNodeGen.Uncached UNCACHED = new TRegexUtilFactory.InteropReadStringMemberNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TRegexUtilFactory.InteropReadStringMemberNodeGen.Read0Data read0_cache;
      @Node.Child
      private TRegexUtil.InteropToStringNode read1_coerceNode_;

      private InteropReadStringMemberNodeGen() {
      }

      @ExplodeLoop
      @Override
      public TruffleString execute(Object arg0Value, String arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (TRegexUtilFactory.InteropReadStringMemberNodeGen.Read0Data s0_ = this.read0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberReadable(arg0Value, arg1Value)) {
                     return TRegexUtil.InteropReadStringMemberNode.read(arg0Value, arg1Value, s0_.coerceNode_, s0_.objs_);
                  }
               }
            }

            label62:
            if ((state_0 & 2) != 0) {
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               TruffleString var7;
               try {
                  InteropLibrary read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
                  if (!read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                     break label62;
                  }

                  var7 = this.read1Boundary(state_0, arg0Value, arg1Value);
               } finally {
                  encapsulating_.set(prev_);
               }

               return var7;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      @CompilerDirectives.TruffleBoundary
      private TruffleString read1Boundary(int state_0, Object arg0Value, String arg1Value) {
         InteropLibrary read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
         return TRegexUtil.InteropReadStringMemberNode.read(arg0Value, arg1Value, this.read1_coerceNode_, read1_objs__);
      }

      private TruffleString executeAndSpecialize(Object arg0Value, String arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               TRegexUtilFactory.InteropReadStringMemberNodeGen.Read0Data s0_ = this.read0_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (!s0_.objs_.accepts(arg0Value) || !s0_.objs_.isMemberReadable(arg0Value, arg1Value))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  InteropLibrary objs__ = super.insert(TRegexUtilFactory.INTEROP_LIBRARY_.create(arg0Value));
                  if (objs__.isMemberReadable(arg0Value, arg1Value) && count0_ < 3) {
                     s0_ = super.insert(new TRegexUtilFactory.InteropReadStringMemberNodeGen.Read0Data(this.read0_cache));
                     s0_.coerceNode_ = s0_.insertAccessor(TRegexUtilFactory.InteropToStringNodeGen.create());
                     s0_.objs_ = s0_.insertAccessor(objs__);
                     VarHandle.storeStoreFence();
                     this.read0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return TRegexUtil.InteropReadStringMemberNode.read(arg0Value, arg1Value, s0_.coerceNode_, s0_.objs_);
               }
            }

            InteropLibrary read1_objs__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               read1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
               if (read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                  this.read1_coerceNode_ = super.insert(TRegexUtilFactory.InteropToStringNodeGen.create());
                  int var21;
                  this.exclude_ = var21 = exclude | 1;
                  this.read0_cache = null;
                  state_0 &= -2;
                  int var20;
                  this.state_0_ = var20 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return TRegexUtil.InteropReadStringMemberNode.read(arg0Value, arg1Value, this.read1_coerceNode_, read1_objs__);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
               TRegexUtilFactory.InteropReadStringMemberNodeGen.Read0Data s0_ = this.read0_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TRegexUtil.InteropReadStringMemberNode create() {
         return new TRegexUtilFactory.InteropReadStringMemberNodeGen();
      }

      public static TRegexUtil.InteropReadStringMemberNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TRegexUtil.InteropReadStringMemberNode.class)
      private static final class Read0Data extends Node {
         @Node.Child
         TRegexUtilFactory.InteropReadStringMemberNodeGen.Read0Data next_;
         @Node.Child
         TRegexUtil.InteropToStringNode coerceNode_;
         @Node.Child
         InteropLibrary objs_;

         Read0Data(TRegexUtilFactory.InteropReadStringMemberNodeGen.Read0Data next_) {
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

      @GeneratedBy(TRegexUtil.InteropReadStringMemberNode.class)
      @DenyReplace
      private static final class Uncached extends TRegexUtil.InteropReadStringMemberNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(Object arg0Value, String arg1Value) {
            if (TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value).isMemberReadable(arg0Value, arg1Value)) {
               return TRegexUtil.InteropReadStringMemberNode.read(
                  arg0Value, arg1Value, TRegexUtilFactory.InteropToStringNodeGen.getUncached(), TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value)
               );
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

   @GeneratedBy(TRegexUtil.InteropToBooleanNode.class)
   public static final class InteropToBooleanNodeGen extends TRegexUtil.InteropToBooleanNode {
      private static final TRegexUtilFactory.InteropToBooleanNodeGen.Uncached UNCACHED = new TRegexUtilFactory.InteropToBooleanNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TRegexUtilFactory.InteropToBooleanNodeGen.Coerce0Data coerce0_cache;

      private InteropToBooleanNodeGen() {
      }

      @ExplodeLoop
      @Override
      public boolean execute(Object arg0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return TRegexUtil.InteropToBooleanNode.coerceDirect(arg0Value_);
         } else {
            if ((state_0 & 6) != 0) {
               if ((state_0 & 2) != 0) {
                  for (TRegexUtilFactory.InteropToBooleanNodeGen.Coerce0Data s1_ = this.coerce0_cache; s1_ != null; s1_ = s1_.next_) {
                     if (s1_.objs_.accepts(arg0Value) && s1_.objs_.isBoolean(arg0Value)) {
                        return TRegexUtil.InteropToBooleanNode.coerce(arg0Value, s1_.objs_);
                     }
                  }
               }

               label73:
               if ((state_0 & 4) != 0) {
                  EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                  Node prev_ = encapsulating_.set(this);

                  boolean var6;
                  try {
                     InteropLibrary coerce1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
                     if (!coerce1_objs__.isBoolean(arg0Value)) {
                        break label73;
                     }

                     var6 = this.coerce1Boundary(state_0, arg0Value);
                  } finally {
                     encapsulating_.set(prev_);
                  }

                  return var6;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private boolean coerce1Boundary(int state_0, Object arg0Value) {
         InteropLibrary coerce1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
         return TRegexUtil.InteropToBooleanNode.coerce(arg0Value, coerce1_objs__);
      }

      private boolean executeAndSpecialize(Object arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof Boolean) {
               boolean arg0Value_ = (Boolean)arg0Value;
               int var20;
               this.state_0_ = var20 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return TRegexUtil.InteropToBooleanNode.coerceDirect(arg0Value_);
            } else {
               if (exclude == 0) {
                  int count1_ = 0;
                  TRegexUtilFactory.InteropToBooleanNodeGen.Coerce0Data s1_ = this.coerce0_cache;
                  if ((state_0 & 2) != 0) {
                     while (s1_ != null && (!s1_.objs_.accepts(arg0Value) || !s1_.objs_.isBoolean(arg0Value))) {
                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null) {
                     InteropLibrary objs__ = super.insert(TRegexUtilFactory.INTEROP_LIBRARY_.create(arg0Value));
                     if (objs__.isBoolean(arg0Value) && count1_ < 3) {
                        s1_ = super.insert(new TRegexUtilFactory.InteropToBooleanNodeGen.Coerce0Data(this.coerce0_cache));
                        s1_.objs_ = s1_.insertAccessor(objs__);
                        VarHandle.storeStoreFence();
                        this.coerce0_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                     }
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return TRegexUtil.InteropToBooleanNode.coerce(arg0Value, s1_.objs_);
                  }
               }

               InteropLibrary coerce1_objs__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  coerce1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
                  if (coerce1_objs__.isBoolean(arg0Value)) {
                     int var21;
                     this.exclude_ = var21 = exclude | 1;
                     this.coerce0_cache = null;
                     state_0 &= -3;
                     int var19;
                     this.state_0_ = var19 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return TRegexUtil.InteropToBooleanNode.coerce(arg0Value, coerce1_objs__);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
               TRegexUtilFactory.InteropToBooleanNodeGen.Coerce0Data s1_ = this.coerce0_cache;
               if (s1_ == null || s1_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TRegexUtil.InteropToBooleanNode create() {
         return new TRegexUtilFactory.InteropToBooleanNodeGen();
      }

      public static TRegexUtil.InteropToBooleanNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TRegexUtil.InteropToBooleanNode.class)
      private static final class Coerce0Data extends Node {
         @Node.Child
         TRegexUtilFactory.InteropToBooleanNodeGen.Coerce0Data next_;
         @Node.Child
         InteropLibrary objs_;

         Coerce0Data(TRegexUtilFactory.InteropToBooleanNodeGen.Coerce0Data next_) {
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

      @GeneratedBy(TRegexUtil.InteropToBooleanNode.class)
      @DenyReplace
      private static final class Uncached extends TRegexUtil.InteropToBooleanNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(Object arg0Value) {
            if (arg0Value instanceof Boolean) {
               boolean arg0Value_ = (Boolean)arg0Value;
               return TRegexUtil.InteropToBooleanNode.coerceDirect(arg0Value_);
            } else if (TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value).isBoolean(arg0Value)) {
               return TRegexUtil.InteropToBooleanNode.coerce(arg0Value, TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value));
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

   @GeneratedBy(TRegexUtil.InteropToIntNode.class)
   public static final class InteropToIntNodeGen extends TRegexUtil.InteropToIntNode {
      private static final TRegexUtilFactory.InteropToIntNodeGen.Uncached UNCACHED = new TRegexUtilFactory.InteropToIntNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TRegexUtilFactory.InteropToIntNodeGen.Coerce0Data coerce0_cache;

      private InteropToIntNodeGen() {
      }

      @ExplodeLoop
      @Override
      public int execute(Object arg0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return TRegexUtil.InteropToIntNode.coerceDirect(arg0Value_);
         } else {
            if ((state_0 & 6) != 0) {
               if ((state_0 & 2) != 0) {
                  for (TRegexUtilFactory.InteropToIntNodeGen.Coerce0Data s1_ = this.coerce0_cache; s1_ != null; s1_ = s1_.next_) {
                     if (s1_.objs_.accepts(arg0Value) && s1_.objs_.fitsInInt(arg0Value)) {
                        return TRegexUtil.InteropToIntNode.coerce(arg0Value, s1_.objs_);
                     }
                  }
               }

               label73:
               if ((state_0 & 4) != 0) {
                  EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                  Node prev_ = encapsulating_.set(this);

                  int var6;
                  try {
                     InteropLibrary coerce1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
                     if (!coerce1_objs__.fitsInInt(arg0Value)) {
                        break label73;
                     }

                     var6 = this.coerce1Boundary(state_0, arg0Value);
                  } finally {
                     encapsulating_.set(prev_);
                  }

                  return var6;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private int coerce1Boundary(int state_0, Object arg0Value) {
         InteropLibrary coerce1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
         return TRegexUtil.InteropToIntNode.coerce(arg0Value, coerce1_objs__);
      }

      private int executeAndSpecialize(Object arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof Integer) {
               int arg0Value_ = (Integer)arg0Value;
               int var20;
               this.state_0_ = var20 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return TRegexUtil.InteropToIntNode.coerceDirect(arg0Value_);
            } else {
               if (exclude == 0) {
                  int count1_ = 0;
                  TRegexUtilFactory.InteropToIntNodeGen.Coerce0Data s1_ = this.coerce0_cache;
                  if ((state_0 & 2) != 0) {
                     while (s1_ != null && (!s1_.objs_.accepts(arg0Value) || !s1_.objs_.fitsInInt(arg0Value))) {
                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null) {
                     InteropLibrary objs__ = super.insert(TRegexUtilFactory.INTEROP_LIBRARY_.create(arg0Value));
                     if (objs__.fitsInInt(arg0Value) && count1_ < 3) {
                        s1_ = super.insert(new TRegexUtilFactory.InteropToIntNodeGen.Coerce0Data(this.coerce0_cache));
                        s1_.objs_ = s1_.insertAccessor(objs__);
                        VarHandle.storeStoreFence();
                        this.coerce0_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                     }
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return TRegexUtil.InteropToIntNode.coerce(arg0Value, s1_.objs_);
                  }
               }

               InteropLibrary coerce1_objs__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  coerce1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
                  if (coerce1_objs__.fitsInInt(arg0Value)) {
                     int var21;
                     this.exclude_ = var21 = exclude | 1;
                     this.coerce0_cache = null;
                     state_0 &= -3;
                     int var19;
                     this.state_0_ = var19 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return TRegexUtil.InteropToIntNode.coerce(arg0Value, coerce1_objs__);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
               TRegexUtilFactory.InteropToIntNodeGen.Coerce0Data s1_ = this.coerce0_cache;
               if (s1_ == null || s1_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TRegexUtil.InteropToIntNode create() {
         return new TRegexUtilFactory.InteropToIntNodeGen();
      }

      public static TRegexUtil.InteropToIntNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TRegexUtil.InteropToIntNode.class)
      private static final class Coerce0Data extends Node {
         @Node.Child
         TRegexUtilFactory.InteropToIntNodeGen.Coerce0Data next_;
         @Node.Child
         InteropLibrary objs_;

         Coerce0Data(TRegexUtilFactory.InteropToIntNodeGen.Coerce0Data next_) {
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

      @GeneratedBy(TRegexUtil.InteropToIntNode.class)
      @DenyReplace
      private static final class Uncached extends TRegexUtil.InteropToIntNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(Object arg0Value) {
            if (arg0Value instanceof Integer) {
               int arg0Value_ = (Integer)arg0Value;
               return TRegexUtil.InteropToIntNode.coerceDirect(arg0Value_);
            } else if (TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value).fitsInInt(arg0Value)) {
               return TRegexUtil.InteropToIntNode.coerce(arg0Value, TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value));
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

   @GeneratedBy(TRegexUtil.InteropToStringNode.class)
   public static final class InteropToStringNodeGen extends TRegexUtil.InteropToStringNode {
      private static final TRegexUtilFactory.InteropToStringNodeGen.Uncached UNCACHED = new TRegexUtilFactory.InteropToStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TRegexUtilFactory.InteropToStringNodeGen.Coerce0Data coerce0_cache;

      private InteropToStringNodeGen() {
      }

      @ExplodeLoop
      @Override
      public TruffleString execute(Object arg0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0 && arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            return TRegexUtil.InteropToStringNode.coerceJavaString(arg0Value_);
         } else if ((state_0 & 2) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return TRegexUtil.InteropToStringNode.coerceDirect(arg0Value_);
         } else {
            if ((state_0 & 12) != 0) {
               if ((state_0 & 4) != 0) {
                  for (TRegexUtilFactory.InteropToStringNodeGen.Coerce0Data s2_ = this.coerce0_cache; s2_ != null; s2_ = s2_.next_) {
                     if (s2_.objs_.accepts(arg0Value) && !JSGuards.isTruffleString(arg0Value) && s2_.objs_.isString(arg0Value)) {
                        return TRegexUtil.InteropToStringNode.coerce(arg0Value, s2_.objs_);
                     }
                  }
               }

               label87:
               if ((state_0 & 8) != 0) {
                  EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                  Node prev_ = encapsulating_.set(this);

                  TruffleString var6;
                  try {
                     if (JSGuards.isTruffleString(arg0Value)) {
                        break label87;
                     }

                     InteropLibrary coerce1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
                     if (!coerce1_objs__.isString(arg0Value)) {
                        break label87;
                     }

                     var6 = this.coerce1Boundary(state_0, arg0Value);
                  } finally {
                     encapsulating_.set(prev_);
                  }

                  return var6;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private TruffleString coerce1Boundary(int state_0, Object arg0Value) {
         InteropLibrary coerce1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
         return TRegexUtil.InteropToStringNode.coerce(arg0Value, coerce1_objs__);
      }

      private TruffleString executeAndSpecialize(Object arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof String) {
               String arg0Value_ = (String)arg0Value;
               int var21;
               this.state_0_ = var21 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return TRegexUtil.InteropToStringNode.coerceJavaString(arg0Value_);
            } else if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               int var20;
               this.state_0_ = var20 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TRegexUtil.InteropToStringNode.coerceDirect(arg0Value_);
            } else {
               if (exclude == 0) {
                  int count2_ = 0;
                  TRegexUtilFactory.InteropToStringNodeGen.Coerce0Data s2_ = this.coerce0_cache;
                  if ((state_0 & 4) != 0) {
                     while (s2_ != null && (!s2_.objs_.accepts(arg0Value) || JSGuards.isTruffleString(arg0Value) || !s2_.objs_.isString(arg0Value))) {
                        s2_ = s2_.next_;
                        count2_++;
                     }
                  }

                  if (s2_ == null && !JSGuards.isTruffleString(arg0Value)) {
                     InteropLibrary objs__ = super.insert(TRegexUtilFactory.INTEROP_LIBRARY_.create(arg0Value));
                     if (objs__.isString(arg0Value) && count2_ < 3) {
                        s2_ = super.insert(new TRegexUtilFactory.InteropToStringNodeGen.Coerce0Data(this.coerce0_cache));
                        s2_.objs_ = s2_.insertAccessor(objs__);
                        VarHandle.storeStoreFence();
                        this.coerce0_cache = s2_;
                        this.state_0_ = state_0 |= 4;
                     }
                  }

                  if (s2_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return TRegexUtil.InteropToStringNode.coerce(arg0Value, s2_.objs_);
                  }
               }

               InteropLibrary coerce1_objs__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (!JSGuards.isTruffleString(arg0Value)) {
                     coerce1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
                     if (coerce1_objs__.isString(arg0Value)) {
                        int var22;
                        this.exclude_ = var22 = exclude | 1;
                        this.coerce0_cache = null;
                        state_0 &= -5;
                        int var19;
                        this.state_0_ = var19 = state_0 | 8;
                        lock.unlock();
                        hasLock = false;
                        return TRegexUtil.InteropToStringNode.coerce(arg0Value, coerce1_objs__);
                     }
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
               TRegexUtilFactory.InteropToStringNodeGen.Coerce0Data s2_ = this.coerce0_cache;
               if (s2_ == null || s2_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TRegexUtil.InteropToStringNode create() {
         return new TRegexUtilFactory.InteropToStringNodeGen();
      }

      public static TRegexUtil.InteropToStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TRegexUtil.InteropToStringNode.class)
      private static final class Coerce0Data extends Node {
         @Node.Child
         TRegexUtilFactory.InteropToStringNodeGen.Coerce0Data next_;
         @Node.Child
         InteropLibrary objs_;

         Coerce0Data(TRegexUtilFactory.InteropToStringNodeGen.Coerce0Data next_) {
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

      @GeneratedBy(TRegexUtil.InteropToStringNode.class)
      @DenyReplace
      private static final class Uncached extends TRegexUtil.InteropToStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(Object arg0Value) {
            if (arg0Value instanceof String) {
               String arg0Value_ = (String)arg0Value;
               return TRegexUtil.InteropToStringNode.coerceJavaString(arg0Value_);
            } else if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               return TRegexUtil.InteropToStringNode.coerceDirect(arg0Value_);
            } else if (!JSGuards.isTruffleString(arg0Value) && TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value).isString(arg0Value)) {
               return TRegexUtil.InteropToStringNode.coerce(arg0Value, TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value));
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

   @GeneratedBy(TRegexUtil.InvokeExecMethodNode.class)
   public static final class InvokeExecMethodNodeGen extends TRegexUtil.InvokeExecMethodNode {
      private static final TRegexUtilFactory.InvokeExecMethodNodeGen.Uncached UNCACHED = new TRegexUtilFactory.InvokeExecMethodNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TRegexUtilFactory.InvokeExecMethodNodeGen.Exec0Data exec0_cache;

      private InvokeExecMethodNodeGen() {
      }

      @ExplodeLoop
      @Override
      public Object execute(Object arg0Value, Object arg1Value, long arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (TRegexUtilFactory.InvokeExecMethodNodeGen.Exec0Data s0_ = this.exec0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberInvocable(arg0Value, "exec")) {
                     return TRegexUtil.InvokeExecMethodNode.exec(arg0Value, arg1Value, arg2Value, s0_.objs_);
                  }
               }
            }

            label62:
            if ((state_0 & 2) != 0) {
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               Object var9;
               try {
                  InteropLibrary exec1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
                  if (!exec1_objs__.isMemberInvocable(arg0Value, "exec")) {
                     break label62;
                  }

                  var9 = this.exec1Boundary(state_0, arg0Value, arg1Value, arg2Value);
               } finally {
                  encapsulating_.set(prev_);
               }

               return var9;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      @CompilerDirectives.TruffleBoundary
      private Object exec1Boundary(int state_0, Object arg0Value, Object arg1Value, long arg2Value) {
         InteropLibrary exec1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
         return TRegexUtil.InvokeExecMethodNode.exec(arg0Value, arg1Value, arg2Value, exec1_objs__);
      }

      private Object executeAndSpecialize(Object arg0Value, Object arg1Value, long arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               TRegexUtilFactory.InvokeExecMethodNodeGen.Exec0Data s0_ = this.exec0_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (!s0_.objs_.accepts(arg0Value) || !s0_.objs_.isMemberInvocable(arg0Value, "exec"))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  InteropLibrary objs__ = super.insert(TRegexUtilFactory.INTEROP_LIBRARY_.create(arg0Value));
                  if (objs__.isMemberInvocable(arg0Value, "exec") && count0_ < 3) {
                     s0_ = super.insert(new TRegexUtilFactory.InvokeExecMethodNodeGen.Exec0Data(this.exec0_cache));
                     s0_.objs_ = s0_.insertAccessor(objs__);
                     VarHandle.storeStoreFence();
                     this.exec0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return TRegexUtil.InvokeExecMethodNode.exec(arg0Value, arg1Value, arg2Value, s0_.objs_);
               }
            }

            InteropLibrary exec1_objs__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               exec1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
               if (exec1_objs__.isMemberInvocable(arg0Value, "exec")) {
                  int var23;
                  this.exclude_ = var23 = exclude | 1;
                  this.exec0_cache = null;
                  state_0 &= -2;
                  int var22;
                  this.state_0_ = var22 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return TRegexUtil.InvokeExecMethodNode.exec(arg0Value, arg1Value, arg2Value, exec1_objs__);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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
               TRegexUtilFactory.InvokeExecMethodNodeGen.Exec0Data s0_ = this.exec0_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TRegexUtil.InvokeExecMethodNode create() {
         return new TRegexUtilFactory.InvokeExecMethodNodeGen();
      }

      public static TRegexUtil.InvokeExecMethodNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TRegexUtil.InvokeExecMethodNode.class)
      private static final class Exec0Data extends Node {
         @Node.Child
         TRegexUtilFactory.InvokeExecMethodNodeGen.Exec0Data next_;
         @Node.Child
         InteropLibrary objs_;

         Exec0Data(TRegexUtilFactory.InvokeExecMethodNodeGen.Exec0Data next_) {
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

      @GeneratedBy(TRegexUtil.InvokeExecMethodNode.class)
      @DenyReplace
      private static final class Uncached extends TRegexUtil.InvokeExecMethodNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public Object execute(Object arg0Value, Object arg1Value, long arg2Value) {
            if (TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value).isMemberInvocable(arg0Value, "exec")) {
               return TRegexUtil.InvokeExecMethodNode.exec(arg0Value, arg1Value, arg2Value, TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value));
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

   @GeneratedBy(TRegexUtil.InvokeGetGroupBoundariesMethodNode.class)
   public static final class InvokeGetGroupBoundariesMethodNodeGen extends TRegexUtil.InvokeGetGroupBoundariesMethodNode {
      private static final TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.Uncached UNCACHED = new TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.Exec0Data exec0_cache;
      @Node.Child
      private TRegexUtil.InteropToIntNode exec1_toIntNode_;

      private InvokeGetGroupBoundariesMethodNodeGen() {
      }

      @ExplodeLoop
      @Override
      public int execute(Object arg0Value, Object arg1Value, int arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0 && arg1Value instanceof String) {
            String arg1Value_ = (String)arg1Value;
            if ((state_0 & 1) != 0) {
               for (TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.Exec0Data s0_ = this.exec0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberInvocable(arg0Value, arg1Value_)) {
                     return TRegexUtil.InvokeGetGroupBoundariesMethodNode.exec(arg0Value, arg1Value_, arg2Value, s0_.objs_, s0_.toIntNode_);
                  }
               }
            }

            label65:
            if ((state_0 & 2) != 0) {
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               int var9;
               try {
                  InteropLibrary exec1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
                  if (!exec1_objs__.isMemberInvocable(arg0Value, arg1Value_)) {
                     break label65;
                  }

                  var9 = this.exec1Boundary(state_0, arg0Value, arg1Value_, arg2Value);
               } finally {
                  encapsulating_.set(prev_);
               }

               return var9;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      @CompilerDirectives.TruffleBoundary
      private int exec1Boundary(int state_0, Object arg0Value, String arg1Value_, int arg2Value) {
         InteropLibrary exec1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
         return TRegexUtil.InvokeGetGroupBoundariesMethodNode.exec(arg0Value, arg1Value_, arg2Value, exec1_objs__, this.exec1_toIntNode_);
      }

      private int executeAndSpecialize(Object arg0Value, Object arg1Value, int arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg1Value instanceof String) {
               String arg1Value_ = (String)arg1Value;
               if (exclude == 0) {
                  int count0_ = 0;
                  TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.Exec0Data s0_ = this.exec0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && (!s0_.objs_.accepts(arg0Value) || !s0_.objs_.isMemberInvocable(arg0Value, arg1Value_))) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null) {
                     InteropLibrary objs__ = super.insert(TRegexUtilFactory.INTEROP_LIBRARY_.create(arg0Value));
                     if (objs__.isMemberInvocable(arg0Value, arg1Value_) && count0_ < 9) {
                        s0_ = super.insert(new TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.Exec0Data(this.exec0_cache));
                        s0_.objs_ = s0_.insertAccessor(objs__);
                        s0_.toIntNode_ = s0_.insertAccessor(TRegexUtilFactory.InteropToIntNodeGen.create());
                        VarHandle.storeStoreFence();
                        this.exec0_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return TRegexUtil.InvokeGetGroupBoundariesMethodNode.exec(arg0Value, arg1Value_, arg2Value, s0_.objs_, s0_.toIntNode_);
                  }
               }

               InteropLibrary exec1_objs__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  exec1_objs__ = TRegexUtilFactory.INTEROP_LIBRARY_.getUncached();
                  if (exec1_objs__.isMemberInvocable(arg0Value, arg1Value_)) {
                     this.exec1_toIntNode_ = super.insert(TRegexUtilFactory.InteropToIntNodeGen.create());
                     int var23;
                     this.exclude_ = var23 = exclude | 1;
                     this.exec0_cache = null;
                     state_0 &= -2;
                     int var22;
                     this.state_0_ = var22 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return TRegexUtil.InvokeGetGroupBoundariesMethodNode.exec(arg0Value, arg1Value_, arg2Value, exec1_objs__, this.exec1_toIntNode_);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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
               TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.Exec0Data s0_ = this.exec0_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TRegexUtil.InvokeGetGroupBoundariesMethodNode create() {
         return new TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen();
      }

      public static TRegexUtil.InvokeGetGroupBoundariesMethodNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TRegexUtil.InvokeGetGroupBoundariesMethodNode.class)
      private static final class Exec0Data extends Node {
         @Node.Child
         TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.Exec0Data next_;
         @Node.Child
         InteropLibrary objs_;
         @Node.Child
         TRegexUtil.InteropToIntNode toIntNode_;

         Exec0Data(TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.Exec0Data next_) {
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

      @GeneratedBy(TRegexUtil.InvokeGetGroupBoundariesMethodNode.class)
      @DenyReplace
      private static final class Uncached extends TRegexUtil.InvokeGetGroupBoundariesMethodNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(Object arg0Value, Object arg1Value, int arg2Value) {
            if (arg1Value instanceof String) {
               String arg1Value_ = (String)arg1Value;
               if (TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value).isMemberInvocable(arg0Value, arg1Value_)) {
                  return TRegexUtil.InvokeGetGroupBoundariesMethodNode.exec(
                     arg0Value,
                     arg1Value_,
                     arg2Value,
                     TRegexUtilFactory.INTEROP_LIBRARY_.getUncached(arg0Value),
                     TRegexUtilFactory.InteropToIntNodeGen.getUncached()
                  );
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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
