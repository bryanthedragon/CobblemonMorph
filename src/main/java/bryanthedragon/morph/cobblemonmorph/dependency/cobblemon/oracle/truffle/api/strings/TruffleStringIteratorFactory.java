package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TruffleStringIterator.class)
public final class TruffleStringIteratorFactory {
   @GeneratedBy(TruffleStringIterator.NextNode.class)
   static final class NextNodeGen extends TruffleStringIterator.NextNode {
      private static final TruffleStringIteratorFactory.NextNodeGen.Uncached UNCACHED = new TruffleStringIteratorFactory.NextNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringOpsNodes.RawReadValueNode fixed_readNode_;
      @Node.Child
      private TStringOpsNodes.RawReadValueNode fixedValid_readNode_;
      @Node.Child
      private TStringOpsNodes.RawReadValueNode brokenAscii_readNode_;
      @Node.Child
      private TStringOpsNodes.RawReadValueNode brokenUTF32_readNode_;

      private NextNodeGen() {
      }

      @Override
      int executeInternal(TruffleStringIterator arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg0Value.codeRangeA) && TStringGuards.isBestEffort(arg0Value.errorHandling)) {
               return TruffleStringIterator.NextNode.fixed(arg0Value, this.fixed_readNode_);
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUpToValidFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               return TruffleStringIterator.NextNode.fixedValid(arg0Value, this.fixedValid_readNode_);
            }

            if ((state_0 & 4) != 0
               && TStringGuards.isAscii(arg0Value.encoding)
               && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA)
               && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               return TruffleStringIterator.NextNode.brokenAscii(arg0Value, this.brokenAscii_readNode_);
            }

            if ((state_0 & 8) != 0
               && TStringGuards.isUTF32(arg0Value.encoding)
               && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA)
               && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               return TruffleStringIterator.NextNode.brokenUTF32(arg0Value, this.brokenUTF32_readNode_);
            }

            if ((state_0 & 16) != 0 && TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
               return TruffleStringIterator.NextNode.utf8Valid(arg0Value);
            }

            if ((state_0 & 32) != 0 && TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
               return TruffleStringIterator.NextNode.utf8Broken(arg0Value);
            }

            if ((state_0 & 64) != 0 && TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
               return TruffleStringIterator.NextNode.utf16Valid(arg0Value);
            }

            if ((state_0 & 128) != 0 && TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
               return TruffleStringIterator.NextNode.utf16Broken(arg0Value);
            }

            if ((state_0 & 256) != 0 && TStringGuards.isUnsupportedEncoding(arg0Value.encoding)) {
               return TruffleStringIterator.NextNode.unsupported(arg0Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private int executeAndSpecialize(TruffleStringIterator arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var5;
         try {
            int state_0 = this.state_0_;
            if (TStringGuards.isFixedWidth(arg0Value.codeRangeA) && TStringGuards.isBestEffort(arg0Value.errorHandling)) {
               this.fixed_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
               int var17;
               this.state_0_ = var17 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return TruffleStringIterator.NextNode.fixed(arg0Value, this.fixed_readNode_);
            }

            if (TStringGuards.isUpToValidFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               this.fixedValid_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
               int var16;
               this.state_0_ = var16 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TruffleStringIterator.NextNode.fixedValid(arg0Value, this.fixedValid_readNode_);
            }

            if (TStringGuards.isAscii(arg0Value.encoding)
               && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA)
               && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               this.brokenAscii_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
               int var15;
               this.state_0_ = var15 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return TruffleStringIterator.NextNode.brokenAscii(arg0Value, this.brokenAscii_readNode_);
            }

            if (!TStringGuards.isUTF32(arg0Value.encoding)
               || !TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA)
               || !TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                  int var14;
                  this.state_0_ = var14 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return TruffleStringIterator.NextNode.utf8Valid(arg0Value);
               }

               if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                  int var13;
                  this.state_0_ = var13 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return TruffleStringIterator.NextNode.utf8Broken(arg0Value);
               }

               if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return TruffleStringIterator.NextNode.utf16Valid(arg0Value);
               }

               if (!TStringGuards.isUTF16(arg0Value.encoding) || !TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                  if (!TStringGuards.isUnsupportedEncoding(arg0Value.encoding)) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                  }

                  int var11;
                  this.state_0_ = var11 = state_0 | 256;
                  lock.unlock();
                  hasLock = false;
                  return TruffleStringIterator.NextNode.unsupported(arg0Value);
               }

               int var10;
               this.state_0_ = var10 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return TruffleStringIterator.NextNode.utf16Broken(arg0Value);
            }

            this.brokenUTF32_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
            int var9;
            this.state_0_ = var9 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            var5 = TruffleStringIterator.NextNode.brokenUTF32(arg0Value, this.brokenUTF32_readNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var5;
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

      public static TruffleStringIterator.NextNode create() {
         return new TruffleStringIteratorFactory.NextNodeGen();
      }

      public static TruffleStringIterator.NextNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleStringIterator.NextNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleStringIterator.NextNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int executeInternal(TruffleStringIterator arg0Value) {
            if (TStringGuards.isFixedWidth(arg0Value.codeRangeA) && TStringGuards.isBestEffort(arg0Value.errorHandling)) {
               return TruffleStringIterator.NextNode.fixed(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
            } else if (TStringGuards.isUpToValidFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               return TruffleStringIterator.NextNode.fixedValid(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
            } else if (TStringGuards.isAscii(arg0Value.encoding)
               && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA)
               && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               return TruffleStringIterator.NextNode.brokenAscii(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
            } else if (TStringGuards.isUTF32(arg0Value.encoding)
               && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA)
               && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               return TruffleStringIterator.NextNode.brokenUTF32(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
            } else if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
               return TruffleStringIterator.NextNode.utf8Valid(arg0Value);
            } else if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
               return TruffleStringIterator.NextNode.utf8Broken(arg0Value);
            } else if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
               return TruffleStringIterator.NextNode.utf16Valid(arg0Value);
            } else if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
               return TruffleStringIterator.NextNode.utf16Broken(arg0Value);
            } else if (TStringGuards.isUnsupportedEncoding(arg0Value.encoding)) {
               return TruffleStringIterator.NextNode.unsupported(arg0Value);
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

   @GeneratedBy(TruffleStringIterator.PreviousNode.class)
   static final class PreviousNodeGen extends TruffleStringIterator.PreviousNode {
      private static final TruffleStringIteratorFactory.PreviousNodeGen.Uncached UNCACHED = new TruffleStringIteratorFactory.PreviousNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringOpsNodes.RawReadValueNode fixed_readNode_;
      @Node.Child
      private TStringOpsNodes.RawReadValueNode fixedValid_readNode_;
      @Node.Child
      private TStringOpsNodes.RawReadValueNode brokenAscii_readNode_;
      @Node.Child
      private TStringOpsNodes.RawReadValueNode brokenUTF32_readNode_;

      private PreviousNodeGen() {
      }

      @Override
      int executeInternal(TruffleStringIterator arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg0Value.codeRangeA) && TStringGuards.isBestEffort(arg0Value.errorHandling)) {
               return TruffleStringIterator.PreviousNode.fixed(arg0Value, this.fixed_readNode_);
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUpToValidFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               return TruffleStringIterator.PreviousNode.fixedValid(arg0Value, this.fixedValid_readNode_);
            }

            if ((state_0 & 4) != 0
               && TStringGuards.isAscii(arg0Value.encoding)
               && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA)
               && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               return TruffleStringIterator.PreviousNode.brokenAscii(arg0Value, this.brokenAscii_readNode_);
            }

            if ((state_0 & 8) != 0
               && TStringGuards.isUTF32(arg0Value.encoding)
               && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA)
               && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               return TruffleStringIterator.PreviousNode.brokenUTF32(arg0Value, this.brokenUTF32_readNode_);
            }

            if ((state_0 & 16) != 0 && TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
               return TruffleStringIterator.PreviousNode.utf8Valid(arg0Value);
            }

            if ((state_0 & 32) != 0 && TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
               return TruffleStringIterator.PreviousNode.utf8Broken(arg0Value);
            }

            if ((state_0 & 64) != 0 && TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
               return TruffleStringIterator.PreviousNode.utf16Valid(arg0Value);
            }

            if ((state_0 & 128) != 0 && TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
               return TruffleStringIterator.PreviousNode.utf16Broken(arg0Value);
            }

            if ((state_0 & 256) != 0 && TStringGuards.isUnsupportedEncoding(arg0Value.encoding)) {
               return TruffleStringIterator.PreviousNode.unsupported(arg0Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private int executeAndSpecialize(TruffleStringIterator arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var5;
         try {
            int state_0 = this.state_0_;
            if (TStringGuards.isFixedWidth(arg0Value.codeRangeA) && TStringGuards.isBestEffort(arg0Value.errorHandling)) {
               this.fixed_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
               int var17;
               this.state_0_ = var17 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return TruffleStringIterator.PreviousNode.fixed(arg0Value, this.fixed_readNode_);
            }

            if (TStringGuards.isUpToValidFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               this.fixedValid_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
               int var16;
               this.state_0_ = var16 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TruffleStringIterator.PreviousNode.fixedValid(arg0Value, this.fixedValid_readNode_);
            }

            if (TStringGuards.isAscii(arg0Value.encoding)
               && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA)
               && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               this.brokenAscii_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
               int var15;
               this.state_0_ = var15 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return TruffleStringIterator.PreviousNode.brokenAscii(arg0Value, this.brokenAscii_readNode_);
            }

            if (!TStringGuards.isUTF32(arg0Value.encoding)
               || !TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA)
               || !TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                  int var14;
                  this.state_0_ = var14 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return TruffleStringIterator.PreviousNode.utf8Valid(arg0Value);
               }

               if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                  int var13;
                  this.state_0_ = var13 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return TruffleStringIterator.PreviousNode.utf8Broken(arg0Value);
               }

               if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return TruffleStringIterator.PreviousNode.utf16Valid(arg0Value);
               }

               if (!TStringGuards.isUTF16(arg0Value.encoding) || !TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                  if (!TStringGuards.isUnsupportedEncoding(arg0Value.encoding)) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                  }

                  int var11;
                  this.state_0_ = var11 = state_0 | 256;
                  lock.unlock();
                  hasLock = false;
                  return TruffleStringIterator.PreviousNode.unsupported(arg0Value);
               }

               int var10;
               this.state_0_ = var10 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return TruffleStringIterator.PreviousNode.utf16Broken(arg0Value);
            }

            this.brokenUTF32_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
            int var9;
            this.state_0_ = var9 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            var5 = TruffleStringIterator.PreviousNode.brokenUTF32(arg0Value, this.brokenUTF32_readNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var5;
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

      public static TruffleStringIterator.PreviousNode create() {
         return new TruffleStringIteratorFactory.PreviousNodeGen();
      }

      public static TruffleStringIterator.PreviousNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleStringIterator.PreviousNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleStringIterator.PreviousNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int executeInternal(TruffleStringIterator arg0Value) {
            if (TStringGuards.isFixedWidth(arg0Value.codeRangeA) && TStringGuards.isBestEffort(arg0Value.errorHandling)) {
               return TruffleStringIterator.PreviousNode.fixed(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
            } else if (TStringGuards.isUpToValidFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               return TruffleStringIterator.PreviousNode.fixedValid(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
            } else if (TStringGuards.isAscii(arg0Value.encoding)
               && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA)
               && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               return TruffleStringIterator.PreviousNode.brokenAscii(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
            } else if (TStringGuards.isUTF32(arg0Value.encoding)
               && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA)
               && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
               return TruffleStringIterator.PreviousNode.brokenUTF32(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
            } else if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
               return TruffleStringIterator.PreviousNode.utf8Valid(arg0Value);
            } else if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
               return TruffleStringIterator.PreviousNode.utf8Broken(arg0Value);
            } else if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
               return TruffleStringIterator.PreviousNode.utf16Valid(arg0Value);
            } else if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
               return TruffleStringIterator.PreviousNode.utf16Broken(arg0Value);
            } else if (TStringGuards.isUnsupportedEncoding(arg0Value.encoding)) {
               return TruffleStringIterator.PreviousNode.unsupported(arg0Value);
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
}
