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
import com.oracle.truffle.api.strings.TruffleString;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ExpectStringOrTruffleObjectNode.class)
public final class ExpectStringOrTruffleObjectNodeGen extends ExpectStringOrTruffleObjectNode {
   private static final ExpectStringOrTruffleObjectNodeGen.Uncached UNCACHED = new ExpectStringOrTruffleObjectNodeGen.Uncached();
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private ExpectStringOrTruffleObjectNodeGen.BoxedString0Data boxedString0_cache;
   @Node.Child
   private ExpectStringOrTruffleObjectNodeGen.BoxedCharArray0Data boxedCharArray0_cache;

   private ExpectStringOrTruffleObjectNodeGen() {
   }

   @ExplodeLoop
   @Override
   public Object execute(Object arg0Value) throws UnsupportedTypeException {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof String) {
         String arg0Value_ = (String)arg0Value;
         return ExpectStringOrTruffleObjectNode.doString(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         return ExpectStringOrTruffleObjectNode.doTString(arg0Value_);
      } else {
         if ((state_0 & 60) != 0) {
            if ((state_0 & 4) != 0) {
               for (ExpectStringOrTruffleObjectNodeGen.BoxedString0Data s2_ = this.boxedString0_cache; s2_ != null; s2_ = s2_.next_) {
                  if (s2_.inputs_.accepts(arg0Value) && s2_.inputs_.isString(arg0Value)) {
                     return ExpectStringOrTruffleObjectNode.doBoxedString(arg0Value, s2_.inputs_);
                  }
               }
            }

            if ((state_0 & 8) != 0) {
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  InteropLibrary boxedString1_inputs__ = INTEROP_LIBRARY_.getUncached();
                  if (boxedString1_inputs__.isString(arg0Value)) {
                     return this.boxedString1Boundary(state_0, arg0Value);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }
            }

            if ((state_0 & 16) != 0) {
               for (ExpectStringOrTruffleObjectNodeGen.BoxedCharArray0Data s4_ = this.boxedCharArray0_cache; s4_ != null; s4_ = s4_.next_) {
                  if (s4_.inputs_.accepts(arg0Value) && s4_.inputs_.hasArrayElements(arg0Value)) {
                     return ExpectStringOrTruffleObjectNode.doBoxedCharArray(arg0Value, s4_.inputs_);
                  }
               }
            }

            label185:
            if ((state_0 & 32) != 0) {
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               Object var6;
               try {
                  InteropLibrary boxedCharArray1_inputs__ = INTEROP_LIBRARY_.getUncached();
                  if (!boxedCharArray1_inputs__.hasArrayElements(arg0Value)) {
                     break label185;
                  }

                  var6 = this.boxedCharArray1Boundary(state_0, arg0Value);
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
   private Object boxedString1Boundary(int state_0, Object arg0Value) throws UnsupportedTypeException {
      InteropLibrary boxedString1_inputs__ = INTEROP_LIBRARY_.getUncached();
      return ExpectStringOrTruffleObjectNode.doBoxedString(arg0Value, boxedString1_inputs__);
   }

   @CompilerDirectives.TruffleBoundary
   private Object boxedCharArray1Boundary(int state_0, Object arg0Value) throws UnsupportedTypeException {
      InteropLibrary boxedCharArray1_inputs__ = INTEROP_LIBRARY_.getUncached();
      return ExpectStringOrTruffleObjectNode.doBoxedCharArray(arg0Value, boxedCharArray1_inputs__);
   }

   private Object executeAndSpecialize(Object arg0Value) throws UnsupportedTypeException {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            int var30;
            this.state_0_ = var30 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return ExpectStringOrTruffleObjectNode.doString(arg0Value_);
         } else if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            int var29;
            this.state_0_ = var29 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return ExpectStringOrTruffleObjectNode.doTString(arg0Value_);
         } else {
            if ((exclude & 1) == 0) {
               int count2_ = 0;
               ExpectStringOrTruffleObjectNodeGen.BoxedString0Data s2_ = this.boxedString0_cache;
               if ((state_0 & 4) != 0) {
                  while (s2_ != null && (!s2_.inputs_.accepts(arg0Value) || !s2_.inputs_.isString(arg0Value))) {
                     s2_ = s2_.next_;
                     count2_++;
                  }
               }

               if (s2_ == null) {
                  InteropLibrary inputs__ = super.insert(INTEROP_LIBRARY_.create(arg0Value));
                  if (inputs__.isString(arg0Value) && count2_ < 2) {
                     s2_ = super.insert(new ExpectStringOrTruffleObjectNodeGen.BoxedString0Data(this.boxedString0_cache));
                     s2_.inputs_ = s2_.insertAccessor(inputs__);
                     VarHandle.storeStoreFence();
                     this.boxedString0_cache = s2_;
                     this.state_0_ = state_0 |= 4;
                  }
               }

               if (s2_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return ExpectStringOrTruffleObjectNode.doBoxedString(arg0Value, s2_.inputs_);
               }
            }

            InteropLibrary boxedString1_inputs__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               boxedString1_inputs__ = INTEROP_LIBRARY_.getUncached();
               if (boxedString1_inputs__.isString(arg0Value)) {
                  int var32;
                  this.exclude_ = var32 = exclude | 1;
                  this.boxedString0_cache = null;
                  state_0 &= -5;
                  int var28;
                  this.state_0_ = var28 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return ExpectStringOrTruffleObjectNode.doBoxedString(arg0Value, boxedString1_inputs__);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            if ((exclude & 2) == 0) {
               int count4_ = 0;
               ExpectStringOrTruffleObjectNodeGen.BoxedCharArray0Data s4_ = this.boxedCharArray0_cache;
               if ((state_0 & 16) != 0) {
                  while (s4_ != null && (!s4_.inputs_.accepts(arg0Value) || !s4_.inputs_.hasArrayElements(arg0Value))) {
                     s4_ = s4_.next_;
                     count4_++;
                  }
               }

               if (s4_ == null) {
                  InteropLibrary inputs__1 = super.insert(INTEROP_LIBRARY_.create(arg0Value));
                  if (inputs__1.hasArrayElements(arg0Value) && count4_ < 2) {
                     s4_ = super.insert(new ExpectStringOrTruffleObjectNodeGen.BoxedCharArray0Data(this.boxedCharArray0_cache));
                     s4_.inputs_ = s4_.insertAccessor(inputs__1);
                     VarHandle.storeStoreFence();
                     this.boxedCharArray0_cache = s4_;
                     this.state_0_ = state_0 |= 16;
                  }
               }

               if (s4_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return ExpectStringOrTruffleObjectNode.doBoxedCharArray(arg0Value, s4_.inputs_);
               }
            }

            boxedString1_inputs__ = null;
            encapsulating_ = EncapsulatingNodeReference.getCurrent();
            prev_ = encapsulating_.set(this);

            try {
               boxedString1_inputs__ = INTEROP_LIBRARY_.getUncached();
               if (boxedString1_inputs__.hasArrayElements(arg0Value)) {
                  int var31;
                  this.exclude_ = var31 = exclude | 2;
                  this.boxedCharArray0_cache = null;
                  state_0 &= -17;
                  int var26;
                  this.state_0_ = var26 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return ExpectStringOrTruffleObjectNode.doBoxedCharArray(arg0Value, boxedString1_inputs__);
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
            ExpectStringOrTruffleObjectNodeGen.BoxedString0Data s2_ = this.boxedString0_cache;
            ExpectStringOrTruffleObjectNodeGen.BoxedCharArray0Data s4_ = this.boxedCharArray0_cache;
            if ((s2_ == null || s2_.next_ == null) && (s4_ == null || s4_.next_ == null)) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   public static ExpectStringOrTruffleObjectNode create() {
      return new ExpectStringOrTruffleObjectNodeGen();
   }

   public static ExpectStringOrTruffleObjectNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(ExpectStringOrTruffleObjectNode.class)
   private static final class BoxedCharArray0Data extends Node {
      @Node.Child
      ExpectStringOrTruffleObjectNodeGen.BoxedCharArray0Data next_;
      @Node.Child
      InteropLibrary inputs_;

      BoxedCharArray0Data(ExpectStringOrTruffleObjectNodeGen.BoxedCharArray0Data next_) {
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

   @GeneratedBy(ExpectStringOrTruffleObjectNode.class)
   private static final class BoxedString0Data extends Node {
      @Node.Child
      ExpectStringOrTruffleObjectNodeGen.BoxedString0Data next_;
      @Node.Child
      InteropLibrary inputs_;

      BoxedString0Data(ExpectStringOrTruffleObjectNodeGen.BoxedString0Data next_) {
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

   @GeneratedBy(ExpectStringOrTruffleObjectNode.class)
   @DenyReplace
   private static final class Uncached extends ExpectStringOrTruffleObjectNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public Object execute(Object arg0Value) throws UnsupportedTypeException {
         if (arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            return ExpectStringOrTruffleObjectNode.doString(arg0Value_);
         } else if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return ExpectStringOrTruffleObjectNode.doTString(arg0Value_);
         } else if (ExpectStringOrTruffleObjectNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value).isString(arg0Value)) {
            return ExpectStringOrTruffleObjectNode.doBoxedString(arg0Value, ExpectStringOrTruffleObjectNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value));
         } else if (ExpectStringOrTruffleObjectNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value).hasArrayElements(arg0Value)) {
            return ExpectStringOrTruffleObjectNode.doBoxedCharArray(arg0Value, ExpectStringOrTruffleObjectNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value));
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
