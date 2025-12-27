package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ToLongNode.class)
public final class ToLongNodeGen extends ToLongNode {
   private static final ToLongNodeGen.Uncached UNCACHED = new ToLongNodeGen.Uncached();
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private ToLongNodeGen.Boxed0Data boxed0_cache;

   private ToLongNodeGen() {
   }

   @ExplodeLoop
   @Override
   public long execute(Object arg0Value) throws UnsupportedTypeException {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return ToLongNode.doPrimitiveInt(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof Long) {
         long arg0Value_ = (Long)arg0Value;
         return ToLongNode.doPrimitiveLong(arg0Value_);
      } else {
         if ((state_0 & 12) != 0) {
            if ((state_0 & 4) != 0) {
               for (ToLongNodeGen.Boxed0Data s2_ = this.boxed0_cache; s2_ != null; s2_ = s2_.next_) {
                  if (s2_.args_.accepts(arg0Value) && s2_.args_.fitsInLong(arg0Value)) {
                     return ToLongNode.doBoxed(arg0Value, s2_.args_);
                  }
               }
            }

            label80:
            if ((state_0 & 8) != 0) {
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               long var6;
               try {
                  InteropLibrary boxed1_args__ = INTEROP_LIBRARY_.getUncached();
                  if (!boxed1_args__.fitsInLong(arg0Value)) {
                     break label80;
                  }

                  var6 = this.boxed1Boundary(state_0, arg0Value);
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
   private long boxed1Boundary(int state_0, Object arg0Value) throws UnsupportedTypeException {
      InteropLibrary boxed1_args__ = INTEROP_LIBRARY_.getUncached();
      return ToLongNode.doBoxed(arg0Value, boxed1_args__);
   }

   private long executeAndSpecialize(Object arg0Value) throws UnsupportedTypeException {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            int var22;
            this.state_0_ = var22 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return ToLongNode.doPrimitiveInt(arg0Value_);
         } else if (arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            int var21;
            this.state_0_ = var21 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return ToLongNode.doPrimitiveLong(arg0Value_);
         } else {
            if (exclude == 0) {
               int count2_ = 0;
               ToLongNodeGen.Boxed0Data s2_ = this.boxed0_cache;
               if ((state_0 & 4) != 0) {
                  while (s2_ != null && (!s2_.args_.accepts(arg0Value) || !s2_.args_.fitsInLong(arg0Value))) {
                     s2_ = s2_.next_;
                     count2_++;
                  }
               }

               if (s2_ == null) {
                  InteropLibrary args__ = super.insert(INTEROP_LIBRARY_.create(arg0Value));
                  if (args__.fitsInLong(arg0Value) && count2_ < 2) {
                     s2_ = super.insert(new ToLongNodeGen.Boxed0Data(this.boxed0_cache));
                     s2_.args_ = s2_.insertAccessor(args__);
                     VarHandle.storeStoreFence();
                     this.boxed0_cache = s2_;
                     this.state_0_ = state_0 |= 4;
                  }
               }

               if (s2_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return ToLongNode.doBoxed(arg0Value, s2_.args_);
               }
            }

            InteropLibrary boxed1_args__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               boxed1_args__ = INTEROP_LIBRARY_.getUncached();
               if (boxed1_args__.fitsInLong(arg0Value)) {
                  int var23;
                  this.exclude_ = var23 = exclude | 1;
                  this.boxed0_cache = null;
                  state_0 &= -5;
                  int var20;
                  this.state_0_ = var20 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return ToLongNode.doBoxed(arg0Value, boxed1_args__);
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
            ToLongNodeGen.Boxed0Data s2_ = this.boxed0_cache;
            if (s2_ == null || s2_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   public static ToLongNode create() {
      return new ToLongNodeGen();
   }

   public static ToLongNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(ToLongNode.class)
   private static final class Boxed0Data extends Node {
      @Node.Child
      ToLongNodeGen.Boxed0Data next_;
      @Node.Child
      InteropLibrary args_;

      Boxed0Data(ToLongNodeGen.Boxed0Data next_) {
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

   @GeneratedBy(ToLongNode.class)
   @DenyReplace
   private static final class Uncached extends ToLongNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public long execute(Object arg0Value) throws UnsupportedTypeException {
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return ToLongNode.doPrimitiveInt(arg0Value_);
         } else if (arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            return ToLongNode.doPrimitiveLong(arg0Value_);
         } else if (ToLongNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value).fitsInLong(arg0Value)) {
            return ToLongNode.doBoxed(arg0Value, ToLongNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value));
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
