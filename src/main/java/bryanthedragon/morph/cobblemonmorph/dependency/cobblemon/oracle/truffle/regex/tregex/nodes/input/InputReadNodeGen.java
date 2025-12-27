package com.oracle.truffle.regex.tregex.nodes.input;

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
import com.oracle.truffle.regex.runtime.nodes.ToCharNode;
import com.oracle.truffle.regex.runtime.nodes.ToCharNodeGen;
import com.oracle.truffle.regex.tregex.string.Encodings;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(InputReadNode.class)
public final class InputReadNodeGen extends InputReadNode {
   private static final InputReadNodeGen.Uncached UNCACHED = new InputReadNodeGen.Uncached();
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private TruffleString.ReadByteNode tStringUTF8_readRawNode_;
   @Node.Child
   private TruffleString.ReadCharUTF16Node tStringUTF16_readRawNode_;
   @Node.Child
   private TruffleString.CodePointAtIndexNode tStringUTF32_readRawNode_;
   @Node.Child
   private InputReadNodeGen.BoxedCharArray0Data boxedCharArray0_cache;
   @Node.Child
   private ToCharNode boxedCharArray1_toCharNode_;

   private InputReadNodeGen() {
   }

   @ExplodeLoop
   @Override
   public int execute(Object arg0Value, int arg1Value, Encodings.Encoding arg2Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            return InputReadNode.doBytes(arg0Value_, arg1Value, arg2Value);
         }

         if ((state_0 & 2) != 0 && arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            return InputReadNode.doString(arg0Value_, arg1Value, arg2Value);
         }

         if ((state_0 & 28) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            if ((state_0 & 4) != 0 && arg2Value != Encodings.UTF_16 && arg2Value != Encodings.UTF_32 && arg2Value != Encodings.UTF_16_RAW) {
               return InputReadNode.doTStringUTF8(arg0Value_, arg1Value, arg2Value, this.tStringUTF8_readRawNode_);
            }

            if ((state_0 & 8) != 0 && (arg2Value == Encodings.UTF_16 || arg2Value == Encodings.UTF_16_RAW)) {
               return InputReadNode.doTStringUTF16(arg0Value_, arg1Value, arg2Value, this.tStringUTF16_readRawNode_);
            }

            if ((state_0 & 16) != 0 && arg2Value == Encodings.UTF_32) {
               return InputReadNode.doTStringUTF32(arg0Value_, arg1Value, arg2Value, this.tStringUTF32_readRawNode_);
            }
         }

         if ((state_0 & 96) != 0) {
            if ((state_0 & 32) != 0) {
               for (InputReadNodeGen.BoxedCharArray0Data s5_ = this.boxedCharArray0_cache; s5_ != null; s5_ = s5_.next_) {
                  if (s5_.inputs_.accepts(arg0Value) && s5_.inputs_.hasArrayElements(arg0Value)) {
                     return InputReadNode.doBoxedCharArray(arg0Value, arg1Value, arg2Value, s5_.inputs_, s5_.toCharNode_);
                  }
               }
            }

            label127:
            if ((state_0 & 64) != 0) {
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               int var8;
               try {
                  InteropLibrary boxedCharArray1_inputs__ = INTEROP_LIBRARY_.getUncached();
                  if (!boxedCharArray1_inputs__.hasArrayElements(arg0Value)) {
                     break label127;
                  }

                  var8 = this.boxedCharArray1Boundary(state_0, arg0Value, arg1Value, arg2Value);
               } finally {
                  encapsulating_.set(prev_);
               }

               return var8;
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
   }

   @CompilerDirectives.TruffleBoundary
   private int boxedCharArray1Boundary(int state_0, Object arg0Value, int arg1Value, Encodings.Encoding arg2Value) {
      InteropLibrary boxedCharArray1_inputs__ = INTEROP_LIBRARY_.getUncached();
      return InputReadNode.doBoxedCharArray(arg0Value, arg1Value, arg2Value, boxedCharArray1_inputs__, this.boxedCharArray1_toCharNode_);
   }

   private int executeAndSpecialize(Object arg0Value, int arg1Value, Encodings.Encoding arg2Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            int var26;
            this.state_0_ = var26 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return InputReadNode.doBytes(arg0Value_, arg1Value, arg2Value);
         } else if (arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            int var25;
            this.state_0_ = var25 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return InputReadNode.doString(arg0Value_, arg1Value, arg2Value);
         } else {
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (arg2Value != Encodings.UTF_16 && arg2Value != Encodings.UTF_32 && arg2Value != Encodings.UTF_16_RAW) {
                  this.tStringUTF8_readRawNode_ = super.insert(TruffleString.ReadByteNode.create());
                  int var24;
                  this.state_0_ = var24 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return InputReadNode.doTStringUTF8(arg0Value_, arg1Value, arg2Value, this.tStringUTF8_readRawNode_);
               }

               if (arg2Value == Encodings.UTF_16 || arg2Value == Encodings.UTF_16_RAW) {
                  this.tStringUTF16_readRawNode_ = super.insert(TruffleString.ReadCharUTF16Node.create());
                  int var23;
                  this.state_0_ = var23 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return InputReadNode.doTStringUTF16(arg0Value_, arg1Value, arg2Value, this.tStringUTF16_readRawNode_);
               }

               if (arg2Value == Encodings.UTF_32) {
                  this.tStringUTF32_readRawNode_ = super.insert(TruffleString.CodePointAtIndexNode.create());
                  int var22;
                  this.state_0_ = var22 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return InputReadNode.doTStringUTF32(arg0Value_, arg1Value, arg2Value, this.tStringUTF32_readRawNode_);
               }
            }

            if (exclude == 0) {
               int count5_ = 0;
               InputReadNodeGen.BoxedCharArray0Data s5_ = this.boxedCharArray0_cache;
               if ((state_0 & 32) != 0) {
                  while (s5_ != null && (!s5_.inputs_.accepts(arg0Value) || !s5_.inputs_.hasArrayElements(arg0Value))) {
                     s5_ = s5_.next_;
                     count5_++;
                  }
               }

               if (s5_ == null) {
                  InteropLibrary inputs__ = super.insert(INTEROP_LIBRARY_.create(arg0Value));
                  if (inputs__.hasArrayElements(arg0Value) && count5_ < 2) {
                     s5_ = super.insert(new InputReadNodeGen.BoxedCharArray0Data(this.boxedCharArray0_cache));
                     s5_.inputs_ = s5_.insertAccessor(inputs__);
                     s5_.toCharNode_ = s5_.insertAccessor(ToCharNode.create());
                     VarHandle.storeStoreFence();
                     this.boxedCharArray0_cache = s5_;
                     this.state_0_ = state_0 |= 32;
                  }
               }

               if (s5_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return InputReadNode.doBoxedCharArray(arg0Value, arg1Value, arg2Value, s5_.inputs_, s5_.toCharNode_);
               }
            }

            InteropLibrary boxedCharArray1_inputs__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               boxedCharArray1_inputs__ = INTEROP_LIBRARY_.getUncached();
               if (boxedCharArray1_inputs__.hasArrayElements(arg0Value)) {
                  this.boxedCharArray1_toCharNode_ = super.insert(ToCharNode.create());
                  int var27;
                  this.exclude_ = var27 = exclude | 1;
                  this.boxedCharArray0_cache = null;
                  state_0 &= -33;
                  int var21;
                  this.state_0_ = var21 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return InputReadNode.doBoxedCharArray(arg0Value, arg1Value, arg2Value, boxedCharArray1_inputs__, this.boxedCharArray1_toCharNode_);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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
            InputReadNodeGen.BoxedCharArray0Data s5_ = this.boxedCharArray0_cache;
            if (s5_ == null || s5_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   public static InputReadNode create() {
      return new InputReadNodeGen();
   }

   public static InputReadNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(InputReadNode.class)
   private static final class BoxedCharArray0Data extends Node {
      @Node.Child
      InputReadNodeGen.BoxedCharArray0Data next_;
      @Node.Child
      InteropLibrary inputs_;
      @Node.Child
      ToCharNode toCharNode_;

      BoxedCharArray0Data(InputReadNodeGen.BoxedCharArray0Data next_) {
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

   @GeneratedBy(InputReadNode.class)
   @DenyReplace
   private static final class Uncached extends InputReadNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public int execute(Object arg0Value, int arg1Value, Encodings.Encoding arg2Value) {
         if (arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            return InputReadNode.doBytes(arg0Value_, arg1Value, arg2Value);
         } else if (arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            return InputReadNode.doString(arg0Value_, arg1Value, arg2Value);
         } else {
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (arg2Value != Encodings.UTF_16 && arg2Value != Encodings.UTF_32 && arg2Value != Encodings.UTF_16_RAW) {
                  return InputReadNode.doTStringUTF8(arg0Value_, arg1Value, arg2Value, TruffleString.ReadByteNode.getUncached());
               }

               if (arg2Value == Encodings.UTF_16 || arg2Value == Encodings.UTF_16_RAW) {
                  return InputReadNode.doTStringUTF16(arg0Value_, arg1Value, arg2Value, TruffleString.ReadCharUTF16Node.getUncached());
               }

               if (arg2Value == Encodings.UTF_32) {
                  return InputReadNode.doTStringUTF32(arg0Value_, arg1Value, arg2Value, TruffleString.CodePointAtIndexNode.getUncached());
               }
            }

            if (InputReadNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value).hasArrayElements(arg0Value)) {
               return InputReadNode.doBoxedCharArray(
                  arg0Value, arg1Value, arg2Value, InputReadNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value), ToCharNodeGen.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }
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
