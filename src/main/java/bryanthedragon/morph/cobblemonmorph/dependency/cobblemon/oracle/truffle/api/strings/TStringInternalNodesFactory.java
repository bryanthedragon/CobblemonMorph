package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TStringInternalNodes.class)
final class TStringInternalNodesFactory {
   @GeneratedBy(TStringInternalNodes.ByteLengthOfCodePointNode.class)
   static final class ByteLengthOfCodePointNodeGen extends TStringInternalNodes.ByteLengthOfCodePointNode {
      private static final TStringInternalNodesFactory.ByteLengthOfCodePointNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.ByteLengthOfCodePointNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringInternalNodes.CodePointAtRawNode uTF32BrokenReturnNegative_codePointAtRawNode_;

      private ByteLengthOfCodePointNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         TruffleString.Encoding arg3Value,
         int arg4Value,
         TruffleString.ErrorHandling arg5Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value) && TStringGuards.isBestEffort(arg5Value)) {
               return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUpToValidFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
               return this.doFixedValidReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 4) != 0
               && TStringGuards.isAscii(arg3Value)
               && TStringGuards.isBrokenFixedWidth(arg2Value)
               && TStringGuards.isReturnNegative(arg5Value)) {
               return this.doASCIIBrokenReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 8) != 0
               && TStringGuards.isUTF32(arg3Value)
               && TStringGuards.isBrokenFixedWidth(arg2Value)
               && TStringGuards.isReturnNegative(arg5Value)) {
               return this.doUTF32BrokenReturnNegative(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.uTF32BrokenReturnNegative_codePointAtRawNode_
               );
            }

            if ((state_0 & 16) != 0 && TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 32) != 0 && TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
               return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 64) != 0 && TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 128) != 0 && TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
               return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 256) != 0 && TStringGuards.isUnsupportedEncoding(arg3Value)) {
               return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         TruffleString.Encoding arg3Value,
         int arg4Value,
         TruffleString.ErrorHandling arg5Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var10;
         try {
            int state_0 = this.state_0_;
            if (TStringGuards.isFixedWidth(arg2Value) && TStringGuards.isBestEffort(arg5Value)) {
               int var22;
               this.state_0_ = var22 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if (TStringGuards.isUpToValidFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
               int var21;
               this.state_0_ = var21 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doFixedValidReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if (TStringGuards.isAscii(arg3Value) && TStringGuards.isBrokenFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
               int var20;
               this.state_0_ = var20 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doASCIIBrokenReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if (!TStringGuards.isUTF32(arg3Value) || !TStringGuards.isBrokenFixedWidth(arg2Value) || !TStringGuards.isReturnNegative(arg5Value)) {
               if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                  int var19;
                  this.state_0_ = var19 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
               }

               if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                  int var18;
                  this.state_0_ = var18 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
               }

               if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                  int var17;
                  this.state_0_ = var17 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
               }

               if (!TStringGuards.isUTF16(arg3Value) || !TStringGuards.isBrokenMultiByte(arg2Value)) {
                  if (!TStringGuards.isUnsupportedEncoding(arg3Value)) {
                     throw new UnsupportedSpecializationException(
                        this, new Node[]{null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value
                     );
                  }

                  int var16;
                  this.state_0_ = var16 = state_0 | 256;
                  lock.unlock();
                  hasLock = false;
                  return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
               }

               int var15;
               this.state_0_ = var15 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            this.uTF32BrokenReturnNegative_codePointAtRawNode_ = super.insert(TStringInternalNodesFactory.CodePointAtRawNodeGen.create());
            int var14;
            this.state_0_ = var14 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            var10 = this.doUTF32BrokenReturnNegative(
               arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.uTF32BrokenReturnNegative_codePointAtRawNode_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var10;
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

      public static TStringInternalNodes.ByteLengthOfCodePointNode create() {
         return new TStringInternalNodesFactory.ByteLengthOfCodePointNodeGen();
      }

      public static TStringInternalNodes.ByteLengthOfCodePointNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.ByteLengthOfCodePointNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.ByteLengthOfCodePointNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value,
            Object arg1Value,
            int arg2Value,
            TruffleString.Encoding arg3Value,
            int arg4Value,
            TruffleString.ErrorHandling arg5Value
         ) {
            if (TStringGuards.isFixedWidth(arg2Value) && TStringGuards.isBestEffort(arg5Value)) {
               return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            } else if (TStringGuards.isUpToValidFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
               return this.doFixedValidReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            } else if (TStringGuards.isAscii(arg3Value) && TStringGuards.isBrokenFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
               return this.doASCIIBrokenReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            } else if (TStringGuards.isUTF32(arg3Value) && TStringGuards.isBrokenFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
               return this.doUTF32BrokenReturnNegative(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TStringInternalNodesFactory.CodePointAtRawNodeGen.getUncached()
               );
            } else if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            } else if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
               return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            } else if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            } else if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
               return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            } else if (TStringGuards.isUnsupportedEncoding(arg3Value)) {
               return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value
               );
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

   @GeneratedBy(TStringInternalNodes.CalcStringAttributesInnerNode.class)
   static final class CalcStringAttributesInnerNodeGen extends TStringInternalNodes.CalcStringAttributesInnerNode {
      private static final TStringInternalNodesFactory.CalcStringAttributesInnerNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.CalcStringAttributesInnerNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile uTF8_brokenProfile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile generic_validCharacterProfile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile generic_fixedWidthProfile_;

      private CalcStringAttributesInnerNodeGen() {
      }

      @Override
      long execute(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && (TStringGuards.is8Bit(arg6Value) || TStringGuards.isAsciiBytesOrLatin1(arg5Value)) && arg4Value == 0) {
               return this.doLatin1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUpTo16Bit(arg6Value) && arg4Value == 1) {
               return this.doBMP(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 4) != 0 && TStringGuards.isUTF8(arg5Value) && !TStringGuards.isFixedWidth(arg6Value)) {
               return this.doUTF8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.uTF8_brokenProfile_);
            }

            if ((state_0 & 8) != 0 && TStringGuards.isUTF16(arg5Value) && TStringGuards.isValidMultiByte(arg6Value)) {
               return this.doUTF16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 16) != 0 && TStringGuards.isUTF16(arg5Value) && TStringGuards.isBrokenMultiByteOrUnknown(arg6Value)) {
               return this.doUTF16Unknown(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 32) != 0 && arg4Value == 2) {
               return this.doUTF32(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 64) != 0 && TStringGuards.isUnsupportedEncoding(arg5Value)) {
               return this.doGeneric(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  this.generic_validCharacterProfile_,
                  this.generic_fixedWidthProfile_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private long executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         long var11;
         try {
            int state_0 = this.state_0_;
            if ((TStringGuards.is8Bit(arg6Value) || TStringGuards.isAsciiBytesOrLatin1(arg5Value)) && arg4Value == 0) {
               int var22;
               this.state_0_ = var22 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doLatin1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if (TStringGuards.isUpTo16Bit(arg6Value) && arg4Value == 1) {
               int var21;
               this.state_0_ = var21 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doBMP(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if (TStringGuards.isUTF8(arg5Value) && !TStringGuards.isFixedWidth(arg6Value)) {
               this.uTF8_brokenProfile_ = ConditionProfile.create();
               int var20;
               this.state_0_ = var20 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doUTF8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.uTF8_brokenProfile_);
            }

            if (TStringGuards.isUTF16(arg5Value) && TStringGuards.isValidMultiByte(arg6Value)) {
               int var19;
               this.state_0_ = var19 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doUTF16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if (!TStringGuards.isUTF16(arg5Value) || !TStringGuards.isBrokenMultiByteOrUnknown(arg6Value)) {
               if (arg4Value != 2) {
                  if (!TStringGuards.isUnsupportedEncoding(arg5Value)) {
                     throw new UnsupportedSpecializationException(
                        this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
                     );
                  }

                  this.generic_validCharacterProfile_ = ConditionProfile.create();
                  this.generic_fixedWidthProfile_ = ConditionProfile.create();
                  int var18;
                  this.state_0_ = var18 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return this.doGeneric(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     arg6Value,
                     this.generic_validCharacterProfile_,
                     this.generic_fixedWidthProfile_
                  );
               }

               int var17;
               this.state_0_ = var17 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doUTF32(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            int var16;
            this.state_0_ = var16 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            var11 = this.doUTF16Unknown(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var11;
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

      public static TStringInternalNodes.CalcStringAttributesInnerNode create() {
         return new TStringInternalNodesFactory.CalcStringAttributesInnerNodeGen();
      }

      public static TStringInternalNodes.CalcStringAttributesInnerNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.CalcStringAttributesInnerNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.CalcStringAttributesInnerNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         long execute(
            AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value
         ) {
            if ((TStringGuards.is8Bit(arg6Value) || TStringGuards.isAsciiBytesOrLatin1(arg5Value)) && arg4Value == 0) {
               return this.doLatin1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUpTo16Bit(arg6Value) && arg4Value == 1) {
               return this.doBMP(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUTF8(arg5Value) && !TStringGuards.isFixedWidth(arg6Value)) {
               return this.doUTF8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, ConditionProfile.getUncached());
            } else if (TStringGuards.isUTF16(arg5Value) && TStringGuards.isValidMultiByte(arg6Value)) {
               return this.doUTF16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUTF16(arg5Value) && TStringGuards.isBrokenMultiByteOrUnknown(arg6Value)) {
               return this.doUTF16Unknown(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (arg4Value == 2) {
               return this.doUTF32(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUnsupportedEncoding(arg5Value)) {
               return this.doGeneric(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, ConditionProfile.getUncached(), ConditionProfile.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
               );
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

   @GeneratedBy(TStringInternalNodes.CalcStringAttributesNode.class)
   static final class CalcStringAttributesNodeGen extends TStringInternalNodes.CalcStringAttributesNode {
      private static final TStringInternalNodesFactory.CalcStringAttributesNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.CalcStringAttributesNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringInternalNodes.CalcStringAttributesInnerNode notAscii_calcNode_;

      private CalcStringAttributesNodeGen() {
      }

      @Override
      long execute(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.is7Bit(arg6Value)) {
               return this.ascii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 2) != 0 && !TStringGuards.is7Bit(arg6Value)) {
               return this.notAscii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.notAscii_calcNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private long executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         long var11;
         try {
            int state_0 = this.state_0_;
            if (!TStringGuards.is7Bit(arg6Value)) {
               if (TStringGuards.is7Bit(arg6Value)) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
                  );
               }

               this.notAscii_calcNode_ = super.insert(TStringInternalNodesFactory.CalcStringAttributesInnerNodeGen.create());
               int var17;
               this.state_0_ = var17 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.notAscii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.notAscii_calcNode_);
            }

            int var16;
            this.state_0_ = var16 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var11 = this.ascii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var11;
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

      public static TStringInternalNodes.CalcStringAttributesNode create() {
         return new TStringInternalNodesFactory.CalcStringAttributesNodeGen();
      }

      public static TStringInternalNodes.CalcStringAttributesNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.CalcStringAttributesNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.CalcStringAttributesNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         long execute(
            AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value
         ) {
            if (TStringGuards.is7Bit(arg6Value)) {
               return this.ascii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (!TStringGuards.is7Bit(arg6Value)) {
               return this.notAscii(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  TStringInternalNodesFactory.CalcStringAttributesInnerNodeGen.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
               );
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

   @GeneratedBy(TStringInternalNodes.CodePointAtNode.class)
   static final class CodePointAtNodeGen extends TStringInternalNodes.CodePointAtNode {
      private static final TStringInternalNodesFactory.CodePointAtNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.CodePointAtNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringInternalNodesFactory.CodePointAtNodeGen.Utf16Data utf16_cache;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf32_stride0Profile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf32_stride1Profile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf8_fixedWidthProfile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf8_validProfile_;

      private CodePointAtNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         TruffleString.Encoding arg3Value,
         int arg4Value,
         TruffleString.ErrorHandling arg5Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               TStringInternalNodesFactory.CodePointAtNodeGen.Utf16Data s0_ = this.utf16_cache;
               if (s0_ != null && TStringGuards.isUTF16(arg3Value)) {
                  return this.utf16(
                     arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.fixedWidthProfile_, s0_.stride0Profile_, s0_.validProfile_
                  );
               }
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUTF32(arg3Value)) {
               return TStringInternalNodes.CodePointAtNode.utf32(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf32_stride0Profile_, this.utf32_stride1Profile_
               );
            }

            if ((state_0 & 4) != 0 && TStringGuards.isUTF8(arg3Value)) {
               return this.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8_fixedWidthProfile_, this.utf8_validProfile_);
            }

            if ((state_0 & 8) != 0
               && !TStringGuards.isUTF16Or32(arg3Value)
               && !TStringGuards.isUTF8(arg3Value)
               && (TStringGuards.isBytes(arg3Value) || TStringGuards.is7Or8Bit(arg2Value))) {
               return TStringInternalNodes.CodePointAtNode.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 16) != 0 && TStringGuards.isAscii(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
               return TStringInternalNodes.CodePointAtNode.doAsciiBroken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 32) != 0 && TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
               return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         TruffleString.Encoding arg3Value,
         int arg4Value,
         TruffleString.ErrorHandling arg5Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int s0_;
         try {
            int state_0 = this.state_0_;
            if (TStringGuards.isUTF16(arg3Value)) {
               TStringInternalNodesFactory.CodePointAtNodeGen.Utf16Data s0_x = new TStringInternalNodesFactory.CodePointAtNodeGen.Utf16Data();
               s0_x.fixedWidthProfile_ = ConditionProfile.create();
               s0_x.stride0Profile_ = ConditionProfile.create();
               s0_x.validProfile_ = ConditionProfile.create();
               VarHandle.storeStoreFence();
               this.utf16_cache = s0_x;
               int var20;
               this.state_0_ = var20 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.utf16(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_x.fixedWidthProfile_, s0_x.stride0Profile_, s0_x.validProfile_
               );
            }

            if (TStringGuards.isUTF32(arg3Value)) {
               this.utf32_stride0Profile_ = ConditionProfile.create();
               this.utf32_stride1Profile_ = ConditionProfile.create();
               int var19;
               this.state_0_ = var19 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.CodePointAtNode.utf32(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf32_stride0Profile_, this.utf32_stride1Profile_
               );
            }

            if (TStringGuards.isUTF8(arg3Value)) {
               this.utf8_fixedWidthProfile_ = ConditionProfile.create();
               this.utf8_validProfile_ = ConditionProfile.create();
               int var18;
               this.state_0_ = var18 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8_fixedWidthProfile_, this.utf8_validProfile_);
            }

            if (!TStringGuards.isUTF16Or32(arg3Value)
               && !TStringGuards.isUTF8(arg3Value)
               && (TStringGuards.isBytes(arg3Value) || TStringGuards.is7Or8Bit(arg2Value))) {
               int var17;
               this.state_0_ = var17 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.CodePointAtNode.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if (!TStringGuards.isAscii(arg3Value) || TStringGuards.is7Or8Bit(arg2Value)) {
               if (!TStringGuards.isUnsupportedEncoding(arg3Value) || TStringGuards.is7Or8Bit(arg2Value)) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value
                  );
               }

               int var16;
               this.state_0_ = var16 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            int var15;
            this.state_0_ = var15 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            s0_ = TStringInternalNodes.CodePointAtNode.doAsciiBroken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return s0_;
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

      public static TStringInternalNodes.CodePointAtNode create() {
         return new TStringInternalNodesFactory.CodePointAtNodeGen();
      }

      public static TStringInternalNodes.CodePointAtNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.CodePointAtNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.CodePointAtNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value,
            Object arg1Value,
            int arg2Value,
            TruffleString.Encoding arg3Value,
            int arg4Value,
            TruffleString.ErrorHandling arg5Value
         ) {
            if (TStringGuards.isUTF16(arg3Value)) {
               return this.utf16(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  ConditionProfile.getUncached(),
                  ConditionProfile.getUncached(),
                  ConditionProfile.getUncached()
               );
            } else if (TStringGuards.isUTF32(arg3Value)) {
               return TStringInternalNodes.CodePointAtNode.utf32(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached()
               );
            } else if (TStringGuards.isUTF8(arg3Value)) {
               return this.utf8(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached()
               );
            } else if (TStringGuards.isUTF16Or32(arg3Value)
               || TStringGuards.isUTF8(arg3Value)
               || !TStringGuards.isBytes(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
               if (TStringGuards.isAscii(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                  return TStringInternalNodes.CodePointAtNode.doAsciiBroken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
               } else if (TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                  return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
               } else {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value
                  );
               }
            } else {
               return TStringInternalNodes.CodePointAtNode.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
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

      @GeneratedBy(TStringInternalNodes.CodePointAtNode.class)
      private static final class Utf16Data {
         @CompilerDirectives.CompilationFinal
         ConditionProfile fixedWidthProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile stride0Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile validProfile_;

         Utf16Data() {
         }
      }
   }

   @GeneratedBy(TStringInternalNodes.CodePointAtRawNode.class)
   static final class CodePointAtRawNodeGen extends TStringInternalNodes.CodePointAtRawNode {
      private static final TStringInternalNodesFactory.CodePointAtRawNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.CodePointAtRawNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringInternalNodesFactory.CodePointAtRawNodeGen.Utf16Data utf16_cache;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf32_stride0Profile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf32_stride1Profile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf8_fixedWidthProfile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf8_validProfile_;

      private CodePointAtRawNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         TruffleString.Encoding arg3Value,
         int arg4Value,
         TruffleString.ErrorHandling arg5Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               TStringInternalNodesFactory.CodePointAtRawNodeGen.Utf16Data s0_ = this.utf16_cache;
               if (s0_ != null && TStringGuards.isUTF16(arg3Value)) {
                  return TStringInternalNodes.CodePointAtRawNode.utf16(
                     arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.fixedWidthProfile_, s0_.validProfile_, s0_.stride0Profile_
                  );
               }
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUTF32(arg3Value)) {
               return TStringInternalNodes.CodePointAtRawNode.utf32(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf32_stride0Profile_, this.utf32_stride1Profile_
               );
            }

            if ((state_0 & 4) != 0 && TStringGuards.isUTF8(arg3Value)) {
               return TStringInternalNodes.CodePointAtRawNode.utf8(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8_fixedWidthProfile_, this.utf8_validProfile_
               );
            }

            if ((state_0 & 8) != 0
               && !TStringGuards.isUTF16Or32(arg3Value)
               && !TStringGuards.isUTF8(arg3Value)
               && (TStringGuards.isBytes(arg3Value) || TStringGuards.is7Or8Bit(arg2Value))) {
               return TStringInternalNodes.CodePointAtRawNode.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 16) != 0 && TStringGuards.isAscii(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
               return TStringInternalNodes.CodePointAtRawNode.doAsciiBroken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 32) != 0 && TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
               return TStringInternalNodes.CodePointAtRawNode.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         TruffleString.Encoding arg3Value,
         int arg4Value,
         TruffleString.ErrorHandling arg5Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int s0_;
         try {
            int state_0 = this.state_0_;
            if (TStringGuards.isUTF16(arg3Value)) {
               TStringInternalNodesFactory.CodePointAtRawNodeGen.Utf16Data s0_x = new TStringInternalNodesFactory.CodePointAtRawNodeGen.Utf16Data();
               s0_x.fixedWidthProfile_ = ConditionProfile.create();
               s0_x.validProfile_ = ConditionProfile.create();
               s0_x.stride0Profile_ = ConditionProfile.create();
               VarHandle.storeStoreFence();
               this.utf16_cache = s0_x;
               int var20;
               this.state_0_ = var20 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.CodePointAtRawNode.utf16(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_x.fixedWidthProfile_, s0_x.validProfile_, s0_x.stride0Profile_
               );
            }

            if (TStringGuards.isUTF32(arg3Value)) {
               this.utf32_stride0Profile_ = ConditionProfile.create();
               this.utf32_stride1Profile_ = ConditionProfile.create();
               int var19;
               this.state_0_ = var19 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.CodePointAtRawNode.utf32(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf32_stride0Profile_, this.utf32_stride1Profile_
               );
            }

            if (TStringGuards.isUTF8(arg3Value)) {
               this.utf8_fixedWidthProfile_ = ConditionProfile.create();
               this.utf8_validProfile_ = ConditionProfile.create();
               int var18;
               this.state_0_ = var18 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.CodePointAtRawNode.utf8(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8_fixedWidthProfile_, this.utf8_validProfile_
               );
            }

            if (!TStringGuards.isUTF16Or32(arg3Value)
               && !TStringGuards.isUTF8(arg3Value)
               && (TStringGuards.isBytes(arg3Value) || TStringGuards.is7Or8Bit(arg2Value))) {
               int var17;
               this.state_0_ = var17 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.CodePointAtRawNode.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if (!TStringGuards.isAscii(arg3Value) || TStringGuards.is7Or8Bit(arg2Value)) {
               if (!TStringGuards.isUnsupportedEncoding(arg3Value) || TStringGuards.is7Or8Bit(arg2Value)) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value
                  );
               }

               int var16;
               this.state_0_ = var16 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.CodePointAtRawNode.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            int var15;
            this.state_0_ = var15 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            s0_ = TStringInternalNodes.CodePointAtRawNode.doAsciiBroken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return s0_;
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

      public static TStringInternalNodes.CodePointAtRawNode create() {
         return new TStringInternalNodesFactory.CodePointAtRawNodeGen();
      }

      public static TStringInternalNodes.CodePointAtRawNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.CodePointAtRawNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.CodePointAtRawNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value,
            Object arg1Value,
            int arg2Value,
            TruffleString.Encoding arg3Value,
            int arg4Value,
            TruffleString.ErrorHandling arg5Value
         ) {
            if (TStringGuards.isUTF16(arg3Value)) {
               return TStringInternalNodes.CodePointAtRawNode.utf16(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  ConditionProfile.getUncached(),
                  ConditionProfile.getUncached(),
                  ConditionProfile.getUncached()
               );
            } else if (TStringGuards.isUTF32(arg3Value)) {
               return TStringInternalNodes.CodePointAtRawNode.utf32(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached()
               );
            } else if (TStringGuards.isUTF8(arg3Value)) {
               return TStringInternalNodes.CodePointAtRawNode.utf8(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached()
               );
            } else if (TStringGuards.isUTF16Or32(arg3Value)
               || TStringGuards.isUTF8(arg3Value)
               || !TStringGuards.isBytes(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
               if (TStringGuards.isAscii(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                  return TStringInternalNodes.CodePointAtRawNode.doAsciiBroken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
               } else if (TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                  return TStringInternalNodes.CodePointAtRawNode.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
               } else {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value
                  );
               }
            } else {
               return TStringInternalNodes.CodePointAtRawNode.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
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

      @GeneratedBy(TStringInternalNodes.CodePointAtRawNode.class)
      private static final class Utf16Data {
         @CompilerDirectives.CompilationFinal
         ConditionProfile fixedWidthProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile validProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile stride0Profile_;

         Utf16Data() {
         }
      }
   }

   @GeneratedBy(TStringInternalNodes.CodePointIndexToRawNode.class)
   static final class CodePointIndexToRawNodeGen extends TStringInternalNodes.CodePointIndexToRawNode {
      private static final TStringInternalNodesFactory.CodePointIndexToRawNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.CodePointIndexToRawNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private CodePointIndexToRawNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, boolean arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value)) {
               return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 4) != 0 && TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
               return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 8) != 0 && TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 16) != 0 && TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
               return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 32) != 0 && TStringGuards.isUnsupportedEncoding(arg3Value)) {
               return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, boolean arg6Value
      ) {
         int state_0 = this.state_0_;
         if (TStringGuards.isFixedWidth(arg2Value)) {
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
         } else if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
            int var13;
            this.state_0_ = var13 = state_0 | 2;
            return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
         } else if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
            int var12;
            this.state_0_ = var12 = state_0 | 4;
            return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
         } else if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
            int var11;
            this.state_0_ = var11 = state_0 | 8;
            return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
         } else if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
            int var10;
            this.state_0_ = var10 = state_0 | 16;
            return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
         } else if (TStringGuards.isUnsupportedEncoding(arg3Value)) {
            int var9;
            this.state_0_ = var9 = state_0 | 32;
            return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
            );
         }
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

      public static TStringInternalNodes.CodePointIndexToRawNode create() {
         return new TStringInternalNodesFactory.CodePointIndexToRawNodeGen();
      }

      public static TStringInternalNodes.CodePointIndexToRawNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.CodePointIndexToRawNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.CodePointIndexToRawNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, boolean arg6Value
         ) {
            if (TStringGuards.isFixedWidth(arg2Value)) {
               return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
               return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
               return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUnsupportedEncoding(arg3Value)) {
               return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
               );
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

   @GeneratedBy(TStringInternalNodes.ConcatEagerNode.class)
   static final class ConcatEagerNodeGen extends TStringInternalNodes.ConcatEagerNode {
      private static final TStringInternalNodesFactory.ConcatEagerNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.ConcatEagerNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringInternalNodesFactory.ConcatEagerNodeGen.ConcatData concat_cache;

      private ConcatEagerNodeGen() {
      }

      @Override
      TruffleString execute(
         AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value, int arg3Value, int arg4Value, int arg5Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TStringInternalNodesFactory.ConcatEagerNodeGen.ConcatData s0_ = this.concat_cache;
            if (s0_ != null) {
               return TStringInternalNodes.ConcatEagerNode.concat(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  s0_.toIndexableNodeA_,
                  s0_.toIndexableNodeB_,
                  s0_.getCodePointLengthANode_,
                  s0_.getCodePointLengthBNode_,
                  s0_.materializeBytesNode_,
                  s0_.calculateAttributesNode_,
                  s0_.brokenProfile_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private TruffleString executeAndSpecialize(
         AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value, int arg3Value, int arg4Value, int arg5Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var11;
         try {
            int state_0 = this.state_0_;
            TStringInternalNodesFactory.ConcatEagerNodeGen.ConcatData s0_ = super.insert(new TStringInternalNodesFactory.ConcatEagerNodeGen.ConcatData());
            s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodePointLengthANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.getCodePointLengthBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.materializeBytesNode_ = s0_.insertAccessor(TStringInternalNodesFactory.ConcatMaterializeBytesNodeGen.create());
            s0_.calculateAttributesNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CalcStringAttributesNodeGen.create());
            s0_.brokenProfile_ = ConditionProfile.create();
            VarHandle.storeStoreFence();
            this.concat_cache = s0_;
            int var15;
            this.state_0_ = var15 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var11 = TStringInternalNodes.ConcatEagerNode.concat(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               s0_.toIndexableNodeA_,
               s0_.toIndexableNodeB_,
               s0_.getCodePointLengthANode_,
               s0_.getCodePointLengthBNode_,
               s0_.materializeBytesNode_,
               s0_.calculateAttributesNode_,
               s0_.brokenProfile_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var11;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TStringInternalNodes.ConcatEagerNode create() {
         return new TStringInternalNodesFactory.ConcatEagerNodeGen();
      }

      public static TStringInternalNodes.ConcatEagerNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.ConcatEagerNode.class)
      private static final class ConcatData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeA_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeB_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthANode_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthBNode_;
         @Node.Child
         TStringInternalNodes.ConcatMaterializeBytesNode materializeBytesNode_;
         @Node.Child
         TStringInternalNodes.CalcStringAttributesNode calculateAttributesNode_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile brokenProfile_;

         ConcatData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TStringInternalNodes.ConcatEagerNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.ConcatEagerNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         TruffleString execute(
            AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value, int arg3Value, int arg4Value, int arg5Value
         ) {
            return TStringInternalNodes.ConcatEagerNode.concat(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               TruffleString.ToIndexableNode.getUncached(),
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodesFactory.ConcatMaterializeBytesNodeGen.getUncached(),
               TStringInternalNodes.CalcStringAttributesNode.getUncached(),
               ConditionProfile.getUncached()
            );
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

   @GeneratedBy(TStringInternalNodes.ConcatMaterializeBytesNode.class)
   static final class ConcatMaterializeBytesNodeGen extends TStringInternalNodes.ConcatMaterializeBytesNode {
      private static final TStringInternalNodesFactory.ConcatMaterializeBytesNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.ConcatMaterializeBytesNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConcatMaterializeBytesNodeGen() {
      }

      @Override
      byte[] execute(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         AbstractTruffleString arg2Value,
         Object arg3Value,
         TruffleString.Encoding arg4Value,
         int arg5Value,
         int arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && (TStringGuards.isUTF16(arg4Value) || TStringGuards.isUTF32(arg4Value))) {
               return this.doWithCompression(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 2) != 0 && !TStringGuards.isUTF16(arg4Value) && !TStringGuards.isUTF32(arg4Value)) {
               return this.doNoCompression(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private byte[] executeAndSpecialize(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         AbstractTruffleString arg2Value,
         Object arg3Value,
         TruffleString.Encoding arg4Value,
         int arg5Value,
         int arg6Value
      ) {
         int state_0 = this.state_0_;
         if (TStringGuards.isUTF16(arg4Value) || TStringGuards.isUTF32(arg4Value)) {
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            return this.doWithCompression(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
         } else if (!TStringGuards.isUTF16(arg4Value) && !TStringGuards.isUTF32(arg4Value)) {
            int var9;
            this.state_0_ = var9 = state_0 | 2;
            return this.doNoCompression(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
            );
         }
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

      public static TStringInternalNodes.ConcatMaterializeBytesNode create() {
         return new TStringInternalNodesFactory.ConcatMaterializeBytesNodeGen();
      }

      public static TStringInternalNodes.ConcatMaterializeBytesNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.ConcatMaterializeBytesNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.ConcatMaterializeBytesNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         byte[] execute(
            AbstractTruffleString arg0Value,
            Object arg1Value,
            AbstractTruffleString arg2Value,
            Object arg3Value,
            TruffleString.Encoding arg4Value,
            int arg5Value,
            int arg6Value
         ) {
            if (TStringGuards.isUTF16(arg4Value) || TStringGuards.isUTF32(arg4Value)) {
               return this.doWithCompression(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (!TStringGuards.isUTF16(arg4Value) && !TStringGuards.isUTF32(arg4Value)) {
               return this.doNoCompression(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
               );
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

   @GeneratedBy(TStringInternalNodes.CreateJavaStringNode.class)
   static final class CreateJavaStringNodeGen extends TStringInternalNodes.CreateJavaStringNode {
      private static final TStringInternalNodesFactory.CreateJavaStringNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.CreateJavaStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile reuseProfile_;
      @Node.Child
      private TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;

      private CreateJavaStringNodeGen() {
      }

      @Override
      String execute(AbstractTruffleString arg0Value, Object arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return this.createJavaString(arg0Value, arg1Value, this.reuseProfile_, this.getCodeRangeNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private String executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         String var6;
         try {
            int state_0 = this.state_0_;
            this.reuseProfile_ = ConditionProfile.create();
            this.getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.createJavaString(arg0Value, arg1Value, this.reuseProfile_, this.getCodeRangeNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TStringInternalNodes.CreateJavaStringNode create() {
         return new TStringInternalNodesFactory.CreateJavaStringNodeGen();
      }

      public static TStringInternalNodes.CreateJavaStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.CreateJavaStringNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.CreateJavaStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         String execute(AbstractTruffleString arg0Value, Object arg1Value) {
            return this.createJavaString(arg0Value, arg1Value, ConditionProfile.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached());
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

   @GeneratedBy(TStringInternalNodes.CreateSubstringNode.class)
   static final class CreateSubstringNodeGen extends TStringInternalNodes.CreateSubstringNode {
      private static final TStringInternalNodesFactory.CreateSubstringNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.CreateSubstringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TStringInternalNodesFactory.CreateSubstringNodeGen.CachedData cached_cache;
      @Node.Child
      private TStringInternalNodes.CalcStringAttributesNode uncached_calcAttributesNode_;

      private CreateSubstringNodeGen() {
      }

      @ExplodeLoop
      @Override
      TruffleString execute(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (TStringInternalNodesFactory.CreateSubstringNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg5Value == s0_.cachedEncoding_ && arg4Value == s0_.cachedStride_) {
                     return this.doCached(
                        arg0Value,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        arg4Value,
                        arg5Value,
                        arg6Value,
                        s0_.cachedEncoding_,
                        s0_.cachedStride_,
                        s0_.calcAttributesNode_
                     );
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.uncached_calcAttributesNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private TruffleString executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               TStringInternalNodesFactory.CreateSubstringNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (arg5Value != s0_.cachedEncoding_ || arg4Value != s0_.cachedStride_)) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < 6) {
                  s0_ = super.insert(new TStringInternalNodesFactory.CreateSubstringNodeGen.CachedData(this.cached_cache));
                  s0_.cachedEncoding_ = arg5Value;
                  s0_.cachedStride_ = arg4Value;
                  s0_.calcAttributesNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CalcStringAttributesNodeGen.create());
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     arg6Value,
                     s0_.cachedEncoding_,
                     s0_.cachedStride_,
                     s0_.calcAttributesNode_
                  );
               }
            }

            this.uncached_calcAttributesNode_ = super.insert(TStringInternalNodesFactory.CalcStringAttributesNodeGen.create());
            int var20;
            this.exclude_ = var20 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var19;
            this.state_0_ = var19 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.uncached_calcAttributesNode_);
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
               TStringInternalNodesFactory.CreateSubstringNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TStringInternalNodes.CreateSubstringNode create() {
         return new TStringInternalNodesFactory.CreateSubstringNodeGen();
      }

      public static TStringInternalNodes.CreateSubstringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.CreateSubstringNode.class)
      private static final class CachedData extends Node {
         @Node.Child
         TStringInternalNodesFactory.CreateSubstringNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         TruffleString.Encoding cachedEncoding_;
         @CompilerDirectives.CompilationFinal
         int cachedStride_;
         @Node.Child
         TStringInternalNodes.CalcStringAttributesNode calcAttributesNode_;

         CachedData(TStringInternalNodesFactory.CreateSubstringNodeGen.CachedData next_) {
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

      @GeneratedBy(TStringInternalNodes.CreateSubstringNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.CreateSubstringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         TruffleString execute(
            AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value
         ) {
            return this.doUncached(
               arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringInternalNodes.CalcStringAttributesNode.getUncached()
            );
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

   @GeneratedBy(TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.class)
   static final class FromBufferWithStringCompactionKnownAttributesNodeGen extends TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode {
      private static final TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.FromBufferWithStringCompactionData fromBufferWithStringCompaction_cache;

      private FromBufferWithStringCompactionKnownAttributesNodeGen() {
      }

      @Override
      TruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.FromBufferWithStringCompactionData s0_ = this.fromBufferWithStringCompaction_cache;
            if (s0_ != null) {
               return this.fromBufferWithStringCompaction(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  s0_.utf16Profile_,
                  s0_.utf16CompactProfile_,
                  s0_.utf32Profile_,
                  s0_.utf32Compact0Profile_,
                  s0_.utf32Compact1Profile_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private TruffleString executeAndSpecialize(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var11;
         try {
            int state_0 = this.state_0_;
            TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.FromBufferWithStringCompactionData s0_ = new TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.FromBufferWithStringCompactionData();
            s0_.utf16Profile_ = ConditionProfile.create();
            s0_.utf16CompactProfile_ = ConditionProfile.create();
            s0_.utf32Profile_ = ConditionProfile.create();
            s0_.utf32Compact0Profile_ = ConditionProfile.create();
            s0_.utf32Compact1Profile_ = ConditionProfile.create();
            VarHandle.storeStoreFence();
            this.fromBufferWithStringCompaction_cache = s0_;
            int var15;
            this.state_0_ = var15 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var11 = this.fromBufferWithStringCompaction(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               s0_.utf16Profile_,
               s0_.utf16CompactProfile_,
               s0_.utf32Profile_,
               s0_.utf32Compact0Profile_,
               s0_.utf32Compact1Profile_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var11;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode create() {
         return new TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen();
      }

      public static TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.class)
      private static final class FromBufferWithStringCompactionData {
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf16Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf16CompactProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf32Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf32Compact0Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf32Compact1Profile_;

         FromBufferWithStringCompactionData() {
         }
      }

      @GeneratedBy(TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         TruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value) {
            return this.fromBufferWithStringCompaction(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached()
            );
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

   @GeneratedBy(TStringInternalNodes.FromBufferWithStringCompactionNode.class)
   static final class FromBufferWithStringCompactionNodeGen extends TStringInternalNodes.FromBufferWithStringCompactionNode {
      private static final TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.FromBufferWithStringCompactionData fromBufferWithStringCompaction_cache;

      private FromBufferWithStringCompactionNodeGen() {
      }

      @Override
      TruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value, boolean arg5Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.FromBufferWithStringCompactionData s0_ = this.fromBufferWithStringCompaction_cache;
            if (s0_ != null) {
               return this.fromBufferWithStringCompaction(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  s0_.asciiLatinBytesProfile_,
                  s0_.utf8Profile_,
                  s0_.utf8BrokenProfile_,
                  s0_.utf16Profile_,
                  s0_.utf16CompactProfile_,
                  s0_.utf32Profile_,
                  s0_.utf32Compact0Profile_,
                  s0_.utf32Compact1Profile_,
                  s0_.exoticValidProfile_,
                  s0_.exoticFixedWidthProfile_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private TruffleString executeAndSpecialize(
         Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value, boolean arg5Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var11;
         try {
            int state_0 = this.state_0_;
            TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.FromBufferWithStringCompactionData s0_ = new TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.FromBufferWithStringCompactionData();
            s0_.asciiLatinBytesProfile_ = ConditionProfile.create();
            s0_.utf8Profile_ = ConditionProfile.create();
            s0_.utf8BrokenProfile_ = ConditionProfile.create();
            s0_.utf16Profile_ = ConditionProfile.create();
            s0_.utf16CompactProfile_ = ConditionProfile.create();
            s0_.utf32Profile_ = ConditionProfile.create();
            s0_.utf32Compact0Profile_ = ConditionProfile.create();
            s0_.utf32Compact1Profile_ = ConditionProfile.create();
            s0_.exoticValidProfile_ = ConditionProfile.create();
            s0_.exoticFixedWidthProfile_ = ConditionProfile.create();
            VarHandle.storeStoreFence();
            this.fromBufferWithStringCompaction_cache = s0_;
            int var15;
            this.state_0_ = var15 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var11 = this.fromBufferWithStringCompaction(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               s0_.asciiLatinBytesProfile_,
               s0_.utf8Profile_,
               s0_.utf8BrokenProfile_,
               s0_.utf16Profile_,
               s0_.utf16CompactProfile_,
               s0_.utf32Profile_,
               s0_.utf32Compact0Profile_,
               s0_.utf32Compact1Profile_,
               s0_.exoticValidProfile_,
               s0_.exoticFixedWidthProfile_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var11;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TStringInternalNodes.FromBufferWithStringCompactionNode create() {
         return new TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen();
      }

      public static TStringInternalNodes.FromBufferWithStringCompactionNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.FromBufferWithStringCompactionNode.class)
      private static final class FromBufferWithStringCompactionData {
         @CompilerDirectives.CompilationFinal
         ConditionProfile asciiLatinBytesProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf8Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf8BrokenProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf16Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf16CompactProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf32Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf32Compact0Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf32Compact1Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile exoticValidProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile exoticFixedWidthProfile_;

         FromBufferWithStringCompactionData() {
         }
      }

      @GeneratedBy(TStringInternalNodes.FromBufferWithStringCompactionNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.FromBufferWithStringCompactionNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         TruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value, boolean arg5Value) {
            return this.fromBufferWithStringCompaction(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached()
            );
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

   @GeneratedBy(TStringInternalNodes.FromJavaStringUTF16Node.class)
   static final class FromJavaStringUTF16NodeGen extends TStringInternalNodes.FromJavaStringUTF16Node {
      private static final TStringInternalNodesFactory.FromJavaStringUTF16NodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.FromJavaStringUTF16NodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf16CompactProfile_;

      private FromJavaStringUTF16NodeGen() {
      }

      @Override
      TruffleString execute(String arg0Value, int arg1Value, int arg2Value, boolean arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return this.doNonEmpty(arg0Value, arg1Value, arg2Value, arg3Value, this.utf16CompactProfile_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
         }
      }

      private TruffleString executeAndSpecialize(String arg0Value, int arg1Value, int arg2Value, boolean arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var8;
         try {
            int state_0 = this.state_0_;
            this.utf16CompactProfile_ = ConditionProfile.create();
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.doNonEmpty(arg0Value, arg1Value, arg2Value, arg3Value, this.utf16CompactProfile_);
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

      public static TStringInternalNodes.FromJavaStringUTF16Node create() {
         return new TStringInternalNodesFactory.FromJavaStringUTF16NodeGen();
      }

      public static TStringInternalNodes.FromJavaStringUTF16Node getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.FromJavaStringUTF16Node.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.FromJavaStringUTF16Node {
         @CompilerDirectives.TruffleBoundary
         @Override
         TruffleString execute(String arg0Value, int arg1Value, int arg2Value, boolean arg3Value) {
            return this.doNonEmpty(arg0Value, arg1Value, arg2Value, arg3Value, ConditionProfile.getUncached());
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

   @GeneratedBy(TStringInternalNodes.FromNativePointerNode.class)
   static final class FromNativePointerNodeGen extends TStringInternalNodes.FromNativePointerNode {
      private static final TStringInternalNodesFactory.FromNativePointerNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.FromNativePointerNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringInternalNodesFactory.FromNativePointerNodeGen.FromNativePointerInternalData fromNativePointerInternal_cache;

      private FromNativePointerNodeGen() {
      }

      @Override
      TruffleString execute(AbstractTruffleString.NativePointer arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TStringInternalNodesFactory.FromNativePointerNodeGen.FromNativePointerInternalData s0_ = this.fromNativePointerInternal_cache;
            if (s0_ != null) {
               return this.fromNativePointerInternal(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  s0_.asciiLatinBytesProfile_,
                  s0_.utf8Profile_,
                  s0_.utf8BrokenProfile_,
                  s0_.utf16Profile_,
                  s0_.utf32Profile_,
                  s0_.exoticValidProfile_,
                  s0_.exoticFixedWidthProfile_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private TruffleString executeAndSpecialize(
         AbstractTruffleString.NativePointer arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var10;
         try {
            int state_0 = this.state_0_;
            TStringInternalNodesFactory.FromNativePointerNodeGen.FromNativePointerInternalData s0_ = new TStringInternalNodesFactory.FromNativePointerNodeGen.FromNativePointerInternalData();
            s0_.asciiLatinBytesProfile_ = ConditionProfile.create();
            s0_.utf8Profile_ = ConditionProfile.create();
            s0_.utf8BrokenProfile_ = ConditionProfile.create();
            s0_.utf16Profile_ = ConditionProfile.create();
            s0_.utf32Profile_ = ConditionProfile.create();
            s0_.exoticValidProfile_ = ConditionProfile.create();
            s0_.exoticFixedWidthProfile_ = ConditionProfile.create();
            VarHandle.storeStoreFence();
            this.fromNativePointerInternal_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var10 = this.fromNativePointerInternal(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               s0_.asciiLatinBytesProfile_,
               s0_.utf8Profile_,
               s0_.utf8BrokenProfile_,
               s0_.utf16Profile_,
               s0_.utf32Profile_,
               s0_.exoticValidProfile_,
               s0_.exoticFixedWidthProfile_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var10;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TStringInternalNodes.FromNativePointerNode create() {
         return new TStringInternalNodesFactory.FromNativePointerNodeGen();
      }

      public static TStringInternalNodes.FromNativePointerNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.FromNativePointerNode.class)
      private static final class FromNativePointerInternalData {
         @CompilerDirectives.CompilationFinal
         ConditionProfile asciiLatinBytesProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf8Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf8BrokenProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf16Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf32Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile exoticValidProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile exoticFixedWidthProfile_;

         FromNativePointerInternalData() {
         }
      }

      @GeneratedBy(TStringInternalNodes.FromNativePointerNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.FromNativePointerNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         TruffleString execute(AbstractTruffleString.NativePointer arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            return this.fromNativePointerInternal(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached()
            );
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

   @GeneratedBy(TStringInternalNodes.GetCodePointLengthNode.class)
   static final class GetCodePointLengthNodeGen extends TStringInternalNodes.GetCodePointLengthNode {
      private static final TStringInternalNodesFactory.GetCodePointLengthNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.GetCodePointLengthNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private MutableTruffleString.CalcLazyAttributesNode mutableCacheMiss_calcLazyAttributesNode_;

      private GetCodePointLengthNodeGen() {
      }

      @Override
      int execute(AbstractTruffleString arg0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.immutable(arg0Value_);
         } else {
            if ((state_0 & 6) != 0 && arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if ((state_0 & 2) != 0 && arg0Value_.codePointLength() >= 0) {
                  return this.mutableCacheHit(arg0Value_);
               }

               if ((state_0 & 4) != 0 && arg0Value_.codePointLength() < 0) {
                  return this.mutableCacheMiss(arg0Value_, this.mutableCacheMiss_calcLazyAttributesNode_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               int var12;
               this.state_0_ = var12 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.immutable(arg0Value_);
            } else {
               if (arg0Value instanceof MutableTruffleString) {
                  MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
                  if (arg0Value_.codePointLength() >= 0) {
                     int var11;
                     this.state_0_ = var11 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.mutableCacheHit(arg0Value_);
                  }

                  if (arg0Value_.codePointLength() < 0) {
                     this.mutableCacheMiss_calcLazyAttributesNode_ = super.insert(MutableTruffleStringFactory.CalcLazyAttributesNodeGen.create());
                     int var10;
                     this.state_0_ = var10 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.mutableCacheMiss(arg0Value_, this.mutableCacheMiss_calcLazyAttributesNode_);
                  }
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
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      public static TStringInternalNodes.GetCodePointLengthNode create() {
         return new TStringInternalNodesFactory.GetCodePointLengthNodeGen();
      }

      public static TStringInternalNodes.GetCodePointLengthNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.GetCodePointLengthNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.GetCodePointLengthNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(AbstractTruffleString arg0Value) {
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               return this.immutable(arg0Value_);
            } else {
               if (arg0Value instanceof MutableTruffleString) {
                  MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
                  if (arg0Value_.codePointLength() >= 0) {
                     return this.mutableCacheHit(arg0Value_);
                  }

                  if (arg0Value_.codePointLength() < 0) {
                     return this.mutableCacheMiss(arg0Value_, MutableTruffleStringFactory.CalcLazyAttributesNodeGen.getUncached());
                  }
               }

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

   @GeneratedBy(TStringInternalNodes.GetCodeRangeNode.class)
   static final class GetCodeRangeNodeGen extends TStringInternalNodes.GetCodeRangeNode {
      private static final TStringInternalNodesFactory.GetCodeRangeNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.GetCodeRangeNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private MutableTruffleString.CalcLazyAttributesNode mutableCacheMiss_calcLazyAttributesNode_;

      private GetCodeRangeNodeGen() {
      }

      @Override
      int execute(AbstractTruffleString arg0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.immutable(arg0Value_);
         } else {
            if ((state_0 & 6) != 0 && arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if ((state_0 & 2) != 0 && !TStringGuards.isUnknown(arg0Value_.codeRange())) {
                  return this.mutableCacheHit(arg0Value_);
               }

               if ((state_0 & 4) != 0 && TStringGuards.isUnknown(arg0Value_.codeRange())) {
                  return this.mutableCacheMiss(arg0Value_, this.mutableCacheMiss_calcLazyAttributesNode_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               int var12;
               this.state_0_ = var12 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.immutable(arg0Value_);
            } else {
               if (arg0Value instanceof MutableTruffleString) {
                  MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
                  if (!TStringGuards.isUnknown(arg0Value_.codeRange())) {
                     int var11;
                     this.state_0_ = var11 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.mutableCacheHit(arg0Value_);
                  }

                  if (TStringGuards.isUnknown(arg0Value_.codeRange())) {
                     this.mutableCacheMiss_calcLazyAttributesNode_ = super.insert(MutableTruffleStringFactory.CalcLazyAttributesNodeGen.create());
                     int var10;
                     this.state_0_ = var10 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.mutableCacheMiss(arg0Value_, this.mutableCacheMiss_calcLazyAttributesNode_);
                  }
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
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      public static TStringInternalNodes.GetCodeRangeNode create() {
         return new TStringInternalNodesFactory.GetCodeRangeNodeGen();
      }

      public static TStringInternalNodes.GetCodeRangeNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.GetCodeRangeNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.GetCodeRangeNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(AbstractTruffleString arg0Value) {
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               return this.immutable(arg0Value_);
            } else {
               if (arg0Value instanceof MutableTruffleString) {
                  MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
                  if (!TStringGuards.isUnknown(arg0Value_.codeRange())) {
                     return this.mutableCacheHit(arg0Value_);
                  }

                  if (TStringGuards.isUnknown(arg0Value_.codeRange())) {
                     return this.mutableCacheMiss(arg0Value_, MutableTruffleStringFactory.CalcLazyAttributesNodeGen.getUncached());
                  }
               }

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

   @GeneratedBy(TStringInternalNodes.IndexOfCodePointNode.class)
   static final class IndexOfCodePointNodeGen extends TStringInternalNodes.IndexOfCodePointNode {
      private static final TStringInternalNodesFactory.IndexOfCodePointNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.IndexOfCodePointNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringOpsNodes.RawIndexOfCodePointNode fixedWidth_indexOfNode_;
      @Node.Child
      private TruffleStringIterator.NextNode decode_nextNode_;

      private IndexOfCodePointNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value)) {
               return TStringInternalNodes.IndexOfCodePointNode.doFixedWidth(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.fixedWidth_indexOfNode_
               );
            }

            if ((state_0 & 2) != 0 && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.decode_nextNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var11;
         try {
            int state_0 = this.state_0_;
            if (!TStringGuards.isFixedWidth(arg2Value)) {
               if (TStringGuards.isFixedWidth(arg2Value)) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
                  );
               }

               this.decode_nextNode_ = super.insert(TruffleStringIterator.NextNode.create());
               int var16;
               this.state_0_ = var16 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.decode_nextNode_);
            }

            this.fixedWidth_indexOfNode_ = super.insert(TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.create());
            int var15;
            this.state_0_ = var15 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var11 = TStringInternalNodes.IndexOfCodePointNode.doFixedWidth(
               arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.fixedWidth_indexOfNode_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var11;
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

      public static TStringInternalNodes.IndexOfCodePointNode create() {
         return new TStringInternalNodesFactory.IndexOfCodePointNodeGen();
      }

      public static TStringInternalNodes.IndexOfCodePointNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.IndexOfCodePointNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.IndexOfCodePointNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value
         ) {
            if (TStringGuards.isFixedWidth(arg2Value)) {
               return TStringInternalNodes.IndexOfCodePointNode.doFixedWidth(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.getUncached()
               );
            } else if (!TStringGuards.isFixedWidth(arg2Value)) {
               return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TruffleStringIterator.NextNode.getUncached());
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
               );
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

   @GeneratedBy(TStringInternalNodes.IndexOfCodePointRawNode.class)
   static final class IndexOfCodePointRawNodeGen extends TStringInternalNodes.IndexOfCodePointRawNode {
      private static final TStringInternalNodesFactory.IndexOfCodePointRawNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.IndexOfCodePointRawNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringOpsNodes.RawIndexOfCodePointNode utf8Fixed_indexOfNode_;
      @Node.Child
      private TruffleStringIterator.NextNode unsupported_nextNode_;

      private IndexOfCodePointRawNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value)) {
               return TStringInternalNodes.IndexOfCodePointRawNode.utf8Fixed(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.utf8Fixed_indexOfNode_
               );
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUTF8(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.utf8Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 4) != 0 && TStringGuards.isUTF16(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.utf16Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 8) != 0 && TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.unsupported_nextNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var11;
         try {
            int state_0 = this.state_0_;
            if (TStringGuards.isFixedWidth(arg2Value)) {
               this.utf8Fixed_indexOfNode_ = super.insert(TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.create());
               int var18;
               this.state_0_ = var18 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.IndexOfCodePointRawNode.utf8Fixed(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.utf8Fixed_indexOfNode_
               );
            }

            if (TStringGuards.isUTF8(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               int var17;
               this.state_0_ = var17 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.utf8Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if (!TStringGuards.isUTF16(arg3Value) || TStringGuards.isFixedWidth(arg2Value)) {
               if (!TStringGuards.isUnsupportedEncoding(arg3Value) || TStringGuards.isFixedWidth(arg2Value)) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
                  );
               }

               this.unsupported_nextNode_ = super.insert(TruffleStringIterator.NextNode.create());
               int var16;
               this.state_0_ = var16 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.unsupported_nextNode_);
            }

            int var15;
            this.state_0_ = var15 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            var11 = this.utf16Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var11;
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

      public static TStringInternalNodes.IndexOfCodePointRawNode create() {
         return new TStringInternalNodesFactory.IndexOfCodePointRawNodeGen();
      }

      public static TStringInternalNodes.IndexOfCodePointRawNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.IndexOfCodePointRawNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.IndexOfCodePointRawNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value
         ) {
            if (TStringGuards.isFixedWidth(arg2Value)) {
               return TStringInternalNodes.IndexOfCodePointRawNode.utf8Fixed(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.getUncached()
               );
            } else if (TStringGuards.isUTF8(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.utf8Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUTF16(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.utf16Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else if (TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.unsupported(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TruffleStringIterator.NextNode.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
               );
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

   @GeneratedBy(TStringInternalNodes.IndexOfStringNode.class)
   static final class IndexOfStringNodeGen extends TStringInternalNodes.IndexOfStringNode {
      private static final TStringInternalNodesFactory.IndexOfStringNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.IndexOfStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringOpsNodes.RawIndexOfStringNode direct_indexOfStringNode_;
      @Node.Child
      private TruffleStringIterator.NextNode decode_nextNodeA_;
      @Node.Child
      private TruffleStringIterator.NextNode decode_nextNodeB_;

      private IndexOfStringNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         AbstractTruffleString arg3Value,
         Object arg4Value,
         int arg5Value,
         int arg6Value,
         int arg7Value,
         TruffleString.Encoding arg8Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
               return TStringInternalNodes.IndexOfStringNode.direct(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, this.direct_indexOfStringNode_
               );
            }

            if ((state_0 & 2) != 0 && !TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
               return this.decode(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  this.decode_nextNodeA_,
                  this.decode_nextNodeB_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         AbstractTruffleString arg3Value,
         Object arg4Value,
         int arg5Value,
         int arg6Value,
         int arg7Value,
         TruffleString.Encoding arg8Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var13;
         try {
            int state_0 = this.state_0_;
            if (!TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
               if (TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                  throw new UnsupportedSpecializationException(
                     this,
                     new Node[]{null, null, null, null, null, null, null, null, null},
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     arg6Value,
                     arg7Value,
                     arg8Value
                  );
               }

               this.decode_nextNodeA_ = super.insert(TruffleStringIterator.NextNode.create());
               this.decode_nextNodeB_ = super.insert(TruffleStringIterator.NextNode.create());
               int var18;
               this.state_0_ = var18 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.decode(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  this.decode_nextNodeA_,
                  this.decode_nextNodeB_
               );
            }

            this.direct_indexOfStringNode_ = super.insert(TStringOpsNodesFactory.RawIndexOfStringNodeGen.create());
            int var17;
            this.state_0_ = var17 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var13 = TStringInternalNodes.IndexOfStringNode.direct(
               arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, this.direct_indexOfStringNode_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var13;
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

      public static TStringInternalNodes.IndexOfStringNode create() {
         return new TStringInternalNodesFactory.IndexOfStringNodeGen();
      }

      public static TStringInternalNodes.IndexOfStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.IndexOfStringNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.IndexOfStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value,
            Object arg1Value,
            int arg2Value,
            AbstractTruffleString arg3Value,
            Object arg4Value,
            int arg5Value,
            int arg6Value,
            int arg7Value,
            TruffleString.Encoding arg8Value
         ) {
            if (TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
               return TStringInternalNodes.IndexOfStringNode.direct(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  TStringOpsNodesFactory.RawIndexOfStringNodeGen.getUncached()
               );
            } else if (!TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
               return this.decode(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  TruffleStringIterator.NextNode.getUncached(),
                  TruffleStringIterator.NextNode.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{null, null, null, null, null, null, null, null, null},
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value
               );
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

   @GeneratedBy(TStringInternalNodes.IndexOfStringRawNode.class)
   static final class IndexOfStringRawNodeGen extends TStringInternalNodes.IndexOfStringRawNode {
      private static final TStringInternalNodesFactory.IndexOfStringRawNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.IndexOfStringRawNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringOpsNodes.RawIndexOfStringNode supported_indexOfStringNode_;
      @Node.Child
      private TruffleStringIterator.NextNode unsupported_nextNodeA_;
      @Node.Child
      private TruffleStringIterator.NextNode unsupported_nextNodeB_;

      private IndexOfStringRawNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         AbstractTruffleString arg3Value,
         Object arg4Value,
         int arg5Value,
         int arg6Value,
         int arg7Value,
         byte[] arg8Value,
         TruffleString.Encoding arg9Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && (TStringGuards.isSupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value))) {
               return TStringInternalNodes.IndexOfStringRawNode.supported(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value,
                  this.supported_indexOfStringNode_
               );
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUnsupportedEncoding(arg9Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.unsupported(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value,
                  this.unsupported_nextNodeA_,
                  this.unsupported_nextNodeB_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         AbstractTruffleString arg3Value,
         Object arg4Value,
         int arg5Value,
         int arg6Value,
         int arg7Value,
         byte[] arg8Value,
         TruffleString.Encoding arg9Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var14;
         try {
            int state_0 = this.state_0_;
            if (TStringGuards.isSupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value)) {
               this.supported_indexOfStringNode_ = super.insert(TStringOpsNodesFactory.RawIndexOfStringNodeGen.create());
               int var19;
               this.state_0_ = var19 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.IndexOfStringRawNode.supported(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value,
                  this.supported_indexOfStringNode_
               );
            }

            if (!TStringGuards.isUnsupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value)) {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{null, null, null, null, null, null, null, null, null, null},
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value
               );
            }

            this.unsupported_nextNodeA_ = super.insert(TruffleStringIterator.NextNode.create());
            this.unsupported_nextNodeB_ = super.insert(TruffleStringIterator.NextNode.create());
            int var18;
            this.state_0_ = var18 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            var14 = this.unsupported(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               arg6Value,
               arg7Value,
               arg8Value,
               arg9Value,
               this.unsupported_nextNodeA_,
               this.unsupported_nextNodeB_
            );
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
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      public static TStringInternalNodes.IndexOfStringRawNode create() {
         return new TStringInternalNodesFactory.IndexOfStringRawNodeGen();
      }

      public static TStringInternalNodes.IndexOfStringRawNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.IndexOfStringRawNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.IndexOfStringRawNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value,
            Object arg1Value,
            int arg2Value,
            AbstractTruffleString arg3Value,
            Object arg4Value,
            int arg5Value,
            int arg6Value,
            int arg7Value,
            byte[] arg8Value,
            TruffleString.Encoding arg9Value
         ) {
            if (TStringGuards.isSupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value)) {
               return TStringInternalNodes.IndexOfStringRawNode.supported(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value,
                  TStringOpsNodesFactory.RawIndexOfStringNodeGen.getUncached()
               );
            } else if (TStringGuards.isUnsupportedEncoding(arg9Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.unsupported(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value,
                  TruffleStringIterator.NextNode.getUncached(),
                  TruffleStringIterator.NextNode.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{null, null, null, null, null, null, null, null, null, null},
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value
               );
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

   @GeneratedBy(TStringInternalNodes.LastIndexOfCodePointNode.class)
   static final class LastIndexOfCodePointNodeGen extends TStringInternalNodes.LastIndexOfCodePointNode {
      private static final TStringInternalNodesFactory.LastIndexOfCodePointNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.LastIndexOfCodePointNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringOpsNodes.RawLastIndexOfCodePointNode fixedWidth_lastIndexOfNode_;
      @Node.Child
      private TruffleStringIterator.NextNode decode_nextNode_;

      private LastIndexOfCodePointNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value)) {
               return TStringInternalNodes.LastIndexOfCodePointNode.doFixedWidth(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.fixedWidth_lastIndexOfNode_
               );
            }

            if ((state_0 & 2) != 0 && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.decode_nextNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var11;
         try {
            int state_0 = this.state_0_;
            if (!TStringGuards.isFixedWidth(arg2Value)) {
               if (TStringGuards.isFixedWidth(arg2Value)) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
                  );
               }

               this.decode_nextNode_ = super.insert(TruffleStringIterator.NextNode.create());
               int var16;
               this.state_0_ = var16 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.decode_nextNode_);
            }

            this.fixedWidth_lastIndexOfNode_ = super.insert(TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.create());
            int var15;
            this.state_0_ = var15 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var11 = TStringInternalNodes.LastIndexOfCodePointNode.doFixedWidth(
               arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.fixedWidth_lastIndexOfNode_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var11;
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

      public static TStringInternalNodes.LastIndexOfCodePointNode create() {
         return new TStringInternalNodesFactory.LastIndexOfCodePointNodeGen();
      }

      public static TStringInternalNodes.LastIndexOfCodePointNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.LastIndexOfCodePointNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.LastIndexOfCodePointNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value
         ) {
            if (TStringGuards.isFixedWidth(arg2Value)) {
               return TStringInternalNodes.LastIndexOfCodePointNode.doFixedWidth(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.getUncached()
               );
            } else if (!TStringGuards.isFixedWidth(arg2Value)) {
               return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TruffleStringIterator.NextNode.getUncached());
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
               );
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

   @GeneratedBy(TStringInternalNodes.LastIndexOfCodePointRawNode.class)
   static final class LastIndexOfCodePointRawNodeGen extends TStringInternalNodes.LastIndexOfCodePointRawNode {
      private static final TStringInternalNodesFactory.LastIndexOfCodePointRawNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.LastIndexOfCodePointRawNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringOpsNodes.RawLastIndexOfCodePointNode lastIndexOfNode;
      @Node.Child
      private TruffleStringIterator.PreviousNode unsupported_prevNode_;

      private LastIndexOfCodePointRawNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value)) {
               return TStringInternalNodes.LastIndexOfCodePointRawNode.utf8Fixed(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.lastIndexOfNode
               );
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUTF8(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.utf8Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.lastIndexOfNode);
            }

            if ((state_0 & 4) != 0 && TStringGuards.isUTF16(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.utf16Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.lastIndexOfNode);
            }

            if ((state_0 & 8) != 0 && TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.unsupported_prevNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var11;
         try {
            int state_0 = this.state_0_;
            if (TStringGuards.isFixedWidth(arg2Value)) {
               this.lastIndexOfNode = super.insert(
                  this.lastIndexOfNode == null ? TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.create() : this.lastIndexOfNode
               );
               int var18;
               this.state_0_ = var18 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.LastIndexOfCodePointRawNode.utf8Fixed(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.lastIndexOfNode
               );
            }

            if (TStringGuards.isUTF8(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               this.lastIndexOfNode = super.insert(
                  this.lastIndexOfNode == null ? TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.create() : this.lastIndexOfNode
               );
               int var17;
               this.state_0_ = var17 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.utf8Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.lastIndexOfNode);
            }

            if (!TStringGuards.isUTF16(arg3Value) || TStringGuards.isFixedWidth(arg2Value)) {
               if (!TStringGuards.isUnsupportedEncoding(arg3Value) || TStringGuards.isFixedWidth(arg2Value)) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
                  );
               }

               this.unsupported_prevNode_ = super.insert(TruffleStringIterator.PreviousNode.create());
               int var16;
               this.state_0_ = var16 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.unsupported_prevNode_);
            }

            this.lastIndexOfNode = super.insert(
               this.lastIndexOfNode == null ? TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.create() : this.lastIndexOfNode
            );
            int var15;
            this.state_0_ = var15 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            var11 = this.utf16Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.lastIndexOfNode);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var11;
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

      public static TStringInternalNodes.LastIndexOfCodePointRawNode create() {
         return new TStringInternalNodesFactory.LastIndexOfCodePointRawNodeGen();
      }

      public static TStringInternalNodes.LastIndexOfCodePointRawNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.LastIndexOfCodePointRawNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.LastIndexOfCodePointRawNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value
         ) {
            if (TStringGuards.isFixedWidth(arg2Value)) {
               return TStringInternalNodes.LastIndexOfCodePointRawNode.utf8Fixed(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.getUncached()
               );
            } else if (TStringGuards.isUTF8(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.utf8Variable(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.getUncached()
               );
            } else if (TStringGuards.isUTF16(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.utf16Variable(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.getUncached()
               );
            } else if (TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.unsupported(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TruffleStringIterator.PreviousNode.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
               );
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

   @GeneratedBy(TStringInternalNodes.LastIndexOfStringNode.class)
   static final class LastIndexOfStringNodeGen extends TStringInternalNodes.LastIndexOfStringNode {
      private static final TStringInternalNodesFactory.LastIndexOfStringNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.LastIndexOfStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringOpsNodes.RawLastIndexOfStringNode direct_indexOfStringNode_;
      @Node.Child
      private TStringInternalNodesFactory.LastIndexOfStringNodeGen.DecodeData decode_cache;

      private LastIndexOfStringNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         AbstractTruffleString arg3Value,
         Object arg4Value,
         int arg5Value,
         int arg6Value,
         int arg7Value,
         TruffleString.Encoding arg8Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
               return TStringInternalNodes.LastIndexOfStringNode.direct(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, this.direct_indexOfStringNode_
               );
            }

            if ((state_0 & 2) != 0) {
               TStringInternalNodesFactory.LastIndexOfStringNodeGen.DecodeData s1_ = this.decode_cache;
               if (s1_ != null && !TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                  return this.decode(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     arg6Value,
                     arg7Value,
                     arg8Value,
                     s1_.nextNodeA_,
                     s1_.prevNodeA_,
                     s1_.prevNodeB_
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         AbstractTruffleString arg3Value,
         Object arg4Value,
         int arg5Value,
         int arg6Value,
         int arg7Value,
         TruffleString.Encoding arg8Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int s1_;
         try {
            int state_0 = this.state_0_;
            if (!TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
               if (TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                  throw new UnsupportedSpecializationException(
                     this,
                     new Node[]{null, null, null, null, null, null, null, null, null},
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     arg6Value,
                     arg7Value,
                     arg8Value
                  );
               }

               TStringInternalNodesFactory.LastIndexOfStringNodeGen.DecodeData s1_x = super.insert(
                  new TStringInternalNodesFactory.LastIndexOfStringNodeGen.DecodeData()
               );
               s1_x.nextNodeA_ = s1_x.insertAccessor(TruffleStringIterator.NextNode.create());
               s1_x.prevNodeA_ = s1_x.insertAccessor(TruffleStringIterator.PreviousNode.create());
               s1_x.prevNodeB_ = s1_x.insertAccessor(TruffleStringIterator.PreviousNode.create());
               VarHandle.storeStoreFence();
               this.decode_cache = s1_x;
               int var19;
               this.state_0_ = var19 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.decode(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  s1_x.nextNodeA_,
                  s1_x.prevNodeA_,
                  s1_x.prevNodeB_
               );
            }

            this.direct_indexOfStringNode_ = super.insert(TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.create());
            int var18;
            this.state_0_ = var18 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            s1_ = TStringInternalNodes.LastIndexOfStringNode.direct(
               arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, this.direct_indexOfStringNode_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return s1_;
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

      public static TStringInternalNodes.LastIndexOfStringNode create() {
         return new TStringInternalNodesFactory.LastIndexOfStringNodeGen();
      }

      public static TStringInternalNodes.LastIndexOfStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.LastIndexOfStringNode.class)
      private static final class DecodeData extends Node {
         @Node.Child
         TruffleStringIterator.NextNode nextNodeA_;
         @Node.Child
         TruffleStringIterator.PreviousNode prevNodeA_;
         @Node.Child
         TruffleStringIterator.PreviousNode prevNodeB_;

         DecodeData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TStringInternalNodes.LastIndexOfStringNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.LastIndexOfStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value,
            Object arg1Value,
            int arg2Value,
            AbstractTruffleString arg3Value,
            Object arg4Value,
            int arg5Value,
            int arg6Value,
            int arg7Value,
            TruffleString.Encoding arg8Value
         ) {
            if (TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
               return TStringInternalNodes.LastIndexOfStringNode.direct(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.getUncached()
               );
            } else if (!TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
               return this.decode(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  TruffleStringIterator.NextNode.getUncached(),
                  TruffleStringIterator.PreviousNode.getUncached(),
                  TruffleStringIterator.PreviousNode.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{null, null, null, null, null, null, null, null, null},
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value
               );
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

   @GeneratedBy(TStringInternalNodes.LastIndexOfStringRawNode.class)
   static final class LastIndexOfStringRawNodeGen extends TStringInternalNodes.LastIndexOfStringRawNode {
      private static final TStringInternalNodesFactory.LastIndexOfStringRawNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.LastIndexOfStringRawNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringOpsNodes.RawLastIndexOfStringNode lios8SameEncoding_indexOfStringNode_;
      @Node.Child
      private TStringInternalNodesFactory.LastIndexOfStringRawNodeGen.UnsupportedData unsupported_cache;

      private LastIndexOfStringRawNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         AbstractTruffleString arg3Value,
         Object arg4Value,
         int arg5Value,
         int arg6Value,
         int arg7Value,
         byte[] arg8Value,
         TruffleString.Encoding arg9Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && (TStringGuards.isSupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value))) {
               return TStringInternalNodes.LastIndexOfStringRawNode.lios8SameEncoding(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value,
                  this.lios8SameEncoding_indexOfStringNode_
               );
            }

            if ((state_0 & 2) != 0) {
               TStringInternalNodesFactory.LastIndexOfStringRawNodeGen.UnsupportedData s1_ = this.unsupported_cache;
               if (s1_ != null && TStringGuards.isUnsupportedEncoding(arg9Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                  return this.unsupported(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     arg6Value,
                     arg7Value,
                     arg8Value,
                     arg9Value,
                     s1_.nextNodeA_,
                     s1_.prevNodeA_,
                     s1_.prevNodeB_
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         AbstractTruffleString arg3Value,
         Object arg4Value,
         int arg5Value,
         int arg6Value,
         int arg7Value,
         byte[] arg8Value,
         TruffleString.Encoding arg9Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var15;
         try {
            int state_0 = this.state_0_;
            if (TStringGuards.isSupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value)) {
               this.lios8SameEncoding_indexOfStringNode_ = super.insert(TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.create());
               int var20;
               this.state_0_ = var20 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.LastIndexOfStringRawNode.lios8SameEncoding(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value,
                  this.lios8SameEncoding_indexOfStringNode_
               );
            }

            if (!TStringGuards.isUnsupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value)) {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{null, null, null, null, null, null, null, null, null, null},
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value
               );
            }

            TStringInternalNodesFactory.LastIndexOfStringRawNodeGen.UnsupportedData s1_ = super.insert(
               new TStringInternalNodesFactory.LastIndexOfStringRawNodeGen.UnsupportedData()
            );
            s1_.nextNodeA_ = s1_.insertAccessor(TruffleStringIterator.NextNode.create());
            s1_.prevNodeA_ = s1_.insertAccessor(TruffleStringIterator.PreviousNode.create());
            s1_.prevNodeB_ = s1_.insertAccessor(TruffleStringIterator.PreviousNode.create());
            VarHandle.storeStoreFence();
            this.unsupported_cache = s1_;
            int var19;
            this.state_0_ = var19 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            var15 = this.unsupported(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               arg6Value,
               arg7Value,
               arg8Value,
               arg9Value,
               s1_.nextNodeA_,
               s1_.prevNodeA_,
               s1_.prevNodeB_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var15;
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

      public static TStringInternalNodes.LastIndexOfStringRawNode create() {
         return new TStringInternalNodesFactory.LastIndexOfStringRawNodeGen();
      }

      public static TStringInternalNodes.LastIndexOfStringRawNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.LastIndexOfStringRawNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.LastIndexOfStringRawNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value,
            Object arg1Value,
            int arg2Value,
            AbstractTruffleString arg3Value,
            Object arg4Value,
            int arg5Value,
            int arg6Value,
            int arg7Value,
            byte[] arg8Value,
            TruffleString.Encoding arg9Value
         ) {
            if (TStringGuards.isSupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value)) {
               return TStringInternalNodes.LastIndexOfStringRawNode.lios8SameEncoding(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value,
                  TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.getUncached()
               );
            } else if (TStringGuards.isUnsupportedEncoding(arg9Value) && !TStringGuards.isFixedWidth(arg2Value)) {
               return this.unsupported(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value,
                  TruffleStringIterator.NextNode.getUncached(),
                  TruffleStringIterator.PreviousNode.getUncached(),
                  TruffleStringIterator.PreviousNode.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{null, null, null, null, null, null, null, null, null, null},
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value
               );
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

      @GeneratedBy(TStringInternalNodes.LastIndexOfStringRawNode.class)
      private static final class UnsupportedData extends Node {
         @Node.Child
         TruffleStringIterator.NextNode nextNodeA_;
         @Node.Child
         TruffleStringIterator.PreviousNode prevNodeA_;
         @Node.Child
         TruffleStringIterator.PreviousNode prevNodeB_;

         UnsupportedData() {
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

   @GeneratedBy(TStringInternalNodes.ParseDoubleNode.class)
   static final class ParseDoubleNodeGen extends TStringInternalNodes.ParseDoubleNode {
      private static final TStringInternalNodesFactory.ParseDoubleNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.ParseDoubleNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringInternalNodesFactory.ParseDoubleNodeGen.ParseData parse_cache;

      private ParseDoubleNodeGen() {
      }

      @ExplodeLoop
      @Override
      double execute(AbstractTruffleString arg0Value, Object arg1Value) throws TruffleString.NumberFormatException {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            for (TStringInternalNodesFactory.ParseDoubleNodeGen.ParseData s0_ = this.parse_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.cachedStride_ == arg0Value.stride()) {
                  return this.doParse(arg0Value, arg1Value, s0_.cachedStride_, s0_.errorProfile_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private double executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value) throws TruffleString.NumberFormatException {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         double var14;
         try {
            int state_0 = this.state_0_;
            int count0_ = 0;
            TStringInternalNodesFactory.ParseDoubleNodeGen.ParseData s0_ = this.parse_cache;
            if (state_0 != 0) {
               while (s0_ != null && s0_.cachedStride_ != arg0Value.stride()) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null) {
               int cachedStride__ = arg0Value.stride();
               if (cachedStride__ == arg0Value.stride() && count0_ < 3) {
                  s0_ = new TStringInternalNodesFactory.ParseDoubleNodeGen.ParseData(this.parse_cache);
                  s0_.cachedStride_ = cachedStride__;
                  s0_.errorProfile_ = BranchProfile.create();
                  VarHandle.storeStoreFence();
                  this.parse_cache = s0_;
                  int var13;
                  this.state_0_ = var13 = state_0 | 1;
               }
            }

            if (s0_ == null) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            lock.unlock();
            hasLock = false;
            var14 = this.doParse(arg0Value, arg1Value, s0_.cachedStride_, s0_.errorProfile_);
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
               TStringInternalNodesFactory.ParseDoubleNodeGen.ParseData s0_ = this.parse_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TStringInternalNodes.ParseDoubleNode create() {
         return new TStringInternalNodesFactory.ParseDoubleNodeGen();
      }

      public static TStringInternalNodes.ParseDoubleNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.ParseDoubleNode.class)
      private static final class ParseData {
         @CompilerDirectives.CompilationFinal
         TStringInternalNodesFactory.ParseDoubleNodeGen.ParseData next_;
         @CompilerDirectives.CompilationFinal
         int cachedStride_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorProfile_;

         ParseData(TStringInternalNodesFactory.ParseDoubleNodeGen.ParseData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TStringInternalNodes.ParseDoubleNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.ParseDoubleNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         double execute(AbstractTruffleString arg0Value, Object arg1Value) throws TruffleString.NumberFormatException {
            if (arg0Value.stride() == arg0Value.stride()) {
               return this.doParse(arg0Value, arg1Value, arg0Value.stride(), BranchProfile.getUncached());
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

   @GeneratedBy(TStringInternalNodes.ParseIntNode.class)
   static final class ParseIntNodeGen extends TStringInternalNodes.ParseIntNode {
      private static final TStringInternalNodesFactory.ParseIntNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.ParseIntNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringInternalNodesFactory.ParseIntNodeGen.Do7BitData do7Bit_cache;
      @Node.Child
      private TruffleStringIterator.NextNode generic_nextNode_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile generic_errorProfile_;

      private ParseIntNodeGen() {
      }

      @ExplodeLoop
      @Override
      int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value) throws TruffleString.NumberFormatException {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.is7Bit(arg2Value)) {
               for (TStringInternalNodesFactory.ParseIntNodeGen.Do7BitData s0_ = this.do7Bit_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.cachedStride_ == arg0Value.stride()) {
                     return TStringInternalNodes.ParseIntNode.do7Bit(
                        arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStride_, s0_.errorProfile_
                     );
                  }
               }
            }

            if ((state_0 & 2) != 0 && !TStringGuards.is7Bit(arg2Value)) {
               return TStringInternalNodes.ParseIntNode.doGeneric(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.generic_nextNode_, this.generic_errorProfile_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value) throws TruffleString.NumberFormatException {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (TStringGuards.is7Bit(arg2Value)) {
               int count0_ = 0;
               TStringInternalNodesFactory.ParseIntNodeGen.Do7BitData s0_ = this.do7Bit_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && s0_.cachedStride_ != arg0Value.stride()) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  int cachedStride__ = arg0Value.stride();
                  if (cachedStride__ == arg0Value.stride() && count0_ < 3) {
                     s0_ = new TStringInternalNodesFactory.ParseIntNodeGen.Do7BitData(this.do7Bit_cache);
                     s0_.cachedStride_ = cachedStride__;
                     s0_.errorProfile_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.do7Bit_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return TStringInternalNodes.ParseIntNode.do7Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStride_, s0_.errorProfile_);
               }
            }

            if (TStringGuards.is7Bit(arg2Value)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value
               );
            } else {
               this.generic_nextNode_ = super.insert(TruffleStringIterator.NextNode.create());
               this.generic_errorProfile_ = BranchProfile.create();
               int var15;
               this.state_0_ = var15 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.ParseIntNode.doGeneric(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.generic_nextNode_, this.generic_errorProfile_
               );
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
               TStringInternalNodesFactory.ParseIntNodeGen.Do7BitData s0_ = this.do7Bit_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TStringInternalNodes.ParseIntNode create() {
         return new TStringInternalNodesFactory.ParseIntNodeGen();
      }

      public static TStringInternalNodes.ParseIntNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.ParseIntNode.class)
      private static final class Do7BitData {
         @CompilerDirectives.CompilationFinal
         TStringInternalNodesFactory.ParseIntNodeGen.Do7BitData next_;
         @CompilerDirectives.CompilationFinal
         int cachedStride_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorProfile_;

         Do7BitData(TStringInternalNodesFactory.ParseIntNodeGen.Do7BitData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TStringInternalNodes.ParseIntNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.ParseIntNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value) throws TruffleString.NumberFormatException {
            if (TStringGuards.is7Bit(arg2Value) && arg0Value.stride() == arg0Value.stride()) {
               return TStringInternalNodes.ParseIntNode.do7Bit(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg0Value.stride(), BranchProfile.getUncached()
               );
            } else if (!TStringGuards.is7Bit(arg2Value)) {
               return TStringInternalNodes.ParseIntNode.doGeneric(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TruffleStringIterator.NextNode.getUncached(), BranchProfile.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value
               );
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

   @GeneratedBy(TStringInternalNodes.ParseLongNode.class)
   static final class ParseLongNodeGen extends TStringInternalNodes.ParseLongNode {
      private static final TStringInternalNodesFactory.ParseLongNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.ParseLongNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TStringInternalNodesFactory.ParseLongNodeGen.Do7BitData do7Bit_cache;
      @Node.Child
      private TruffleStringIterator.NextNode parseLong_nextNode_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile parseLong_errorProfile_;

      private ParseLongNodeGen() {
      }

      @ExplodeLoop
      @Override
      long execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value) throws TruffleString.NumberFormatException {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.is7Bit(arg2Value)) {
               for (TStringInternalNodesFactory.ParseLongNodeGen.Do7BitData s0_ = this.do7Bit_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.cachedStride_ == arg0Value.stride()) {
                     return TStringInternalNodes.ParseLongNode.do7Bit(
                        arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStride_, s0_.errorProfile_
                     );
                  }
               }
            }

            if ((state_0 & 2) != 0 && !TStringGuards.is7Bit(arg2Value)) {
               return TStringInternalNodes.ParseLongNode.parseLong(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.parseLong_nextNode_, this.parseLong_errorProfile_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private long executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value) throws TruffleString.NumberFormatException {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (TStringGuards.is7Bit(arg2Value)) {
               int count0_ = 0;
               TStringInternalNodesFactory.ParseLongNodeGen.Do7BitData s0_ = this.do7Bit_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && s0_.cachedStride_ != arg0Value.stride()) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  int cachedStride__ = arg0Value.stride();
                  if (cachedStride__ == arg0Value.stride() && count0_ < 3) {
                     s0_ = new TStringInternalNodesFactory.ParseLongNodeGen.Do7BitData(this.do7Bit_cache);
                     s0_.cachedStride_ = cachedStride__;
                     s0_.errorProfile_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.do7Bit_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return TStringInternalNodes.ParseLongNode.do7Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStride_, s0_.errorProfile_);
               }
            }

            if (TStringGuards.is7Bit(arg2Value)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value
               );
            } else {
               this.parseLong_nextNode_ = super.insert(TruffleStringIterator.NextNode.create());
               this.parseLong_errorProfile_ = BranchProfile.create();
               int var16;
               this.state_0_ = var16 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.ParseLongNode.parseLong(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.parseLong_nextNode_, this.parseLong_errorProfile_
               );
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
               TStringInternalNodesFactory.ParseLongNodeGen.Do7BitData s0_ = this.do7Bit_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static TStringInternalNodes.ParseLongNode create() {
         return new TStringInternalNodesFactory.ParseLongNodeGen();
      }

      public static TStringInternalNodes.ParseLongNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.ParseLongNode.class)
      private static final class Do7BitData {
         @CompilerDirectives.CompilationFinal
         TStringInternalNodesFactory.ParseLongNodeGen.Do7BitData next_;
         @CompilerDirectives.CompilationFinal
         int cachedStride_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorProfile_;

         Do7BitData(TStringInternalNodesFactory.ParseLongNodeGen.Do7BitData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(TStringInternalNodes.ParseLongNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.ParseLongNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         long execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value) throws TruffleString.NumberFormatException {
            if (TStringGuards.is7Bit(arg2Value) && arg0Value.stride() == arg0Value.stride()) {
               return TStringInternalNodes.ParseLongNode.do7Bit(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg0Value.stride(), BranchProfile.getUncached()
               );
            } else if (!TStringGuards.is7Bit(arg2Value)) {
               return TStringInternalNodes.ParseLongNode.parseLong(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TruffleStringIterator.NextNode.getUncached(), BranchProfile.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value
               );
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

   @GeneratedBy(TStringInternalNodes.RawIndexToCodePointIndexNode.class)
   static final class RawIndexToCodePointIndexNodeGen extends TStringInternalNodes.RawIndexToCodePointIndexNode {
      private static final TStringInternalNodesFactory.RawIndexToCodePointIndexNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.RawIndexToCodePointIndexNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf8Valid_brokenProfile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf8Broken_brokenProfile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile unsupported_validProfile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile unsupported_fixedWidthProfile_;

      private RawIndexToCodePointIndexNodeGen() {
      }

      @Override
      int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value)) {
               return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8Valid_brokenProfile_);
            }

            if ((state_0 & 4) != 0 && TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
               return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8Broken_brokenProfile_);
            }

            if ((state_0 & 8) != 0 && TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 16) != 0 && TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
               return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 32) != 0 && TStringGuards.isUnsupportedEncoding(arg3Value)) {
               return this.unsupported(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.unsupported_validProfile_, this.unsupported_fixedWidthProfile_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var10;
         try {
            int state_0 = this.state_0_;
            if (TStringGuards.isFixedWidth(arg2Value)) {
               int var19;
               this.state_0_ = var19 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               this.utf8Valid_brokenProfile_ = ConditionProfile.create();
               int var18;
               this.state_0_ = var18 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8Valid_brokenProfile_);
            }

            if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
               this.utf8Broken_brokenProfile_ = ConditionProfile.create();
               int var17;
               this.state_0_ = var17 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8Broken_brokenProfile_);
            }

            if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               int var16;
               this.state_0_ = var16 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if (!TStringGuards.isUTF16(arg3Value) || !TStringGuards.isBrokenMultiByte(arg2Value)) {
               if (!TStringGuards.isUnsupportedEncoding(arg3Value)) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value
                  );
               }

               this.unsupported_validProfile_ = ConditionProfile.create();
               this.unsupported_fixedWidthProfile_ = ConditionProfile.create();
               int var15;
               this.state_0_ = var15 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.unsupported(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.unsupported_validProfile_, this.unsupported_fixedWidthProfile_
               );
            }

            int var14;
            this.state_0_ = var14 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            var10 = this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var10;
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

      public static TStringInternalNodes.RawIndexToCodePointIndexNode create() {
         return new TStringInternalNodesFactory.RawIndexToCodePointIndexNodeGen();
      }

      public static TStringInternalNodes.RawIndexToCodePointIndexNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.RawIndexToCodePointIndexNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.RawIndexToCodePointIndexNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value) {
            if (TStringGuards.isFixedWidth(arg2Value)) {
               return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            } else if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached());
            } else if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
               return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached());
            } else if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
               return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            } else if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
               return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            } else if (TStringGuards.isUnsupportedEncoding(arg3Value)) {
               return this.unsupported(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value
               );
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

   @GeneratedBy(TStringInternalNodes.ReadByteNode.class)
   static final class ReadByteNodeGen extends TStringInternalNodes.ReadByteNode {
      private static final TStringInternalNodesFactory.ReadByteNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.ReadByteNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile uTF16_stride0Profile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile uTF32_stride0Profile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile uTF32_stride1Profile_;

      private ReadByteNodeGen() {
      }

      @Override
      int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isUTF16(arg3Value)) {
               return TStringInternalNodes.ReadByteNode.doUTF16(arg0Value, arg1Value, arg2Value, arg3Value, this.uTF16_stride0Profile_);
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUTF32(arg3Value)) {
               return TStringInternalNodes.ReadByteNode.doUTF32(
                  arg0Value, arg1Value, arg2Value, arg3Value, this.uTF32_stride0Profile_, this.uTF32_stride1Profile_
               );
            }

            if ((state_0 & 4) != 0 && !TStringGuards.isUTF16Or32(arg3Value)) {
               return TStringInternalNodes.ReadByteNode.doRest(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var8;
         try {
            int state_0 = this.state_0_;
            if (TStringGuards.isUTF16(arg3Value)) {
               this.uTF16_stride0Profile_ = ConditionProfile.create();
               int var14;
               this.state_0_ = var14 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.ReadByteNode.doUTF16(arg0Value, arg1Value, arg2Value, arg3Value, this.uTF16_stride0Profile_);
            }

            if (!TStringGuards.isUTF32(arg3Value)) {
               if (TStringGuards.isUTF16Or32(arg3Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
               }

               int var13;
               this.state_0_ = var13 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.ReadByteNode.doRest(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            this.uTF32_stride0Profile_ = ConditionProfile.create();
            this.uTF32_stride1Profile_ = ConditionProfile.create();
            int var12;
            this.state_0_ = var12 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            var8 = TStringInternalNodes.ReadByteNode.doUTF32(arg0Value, arg1Value, arg2Value, arg3Value, this.uTF32_stride0Profile_, this.uTF32_stride1Profile_);
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
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      public static TStringInternalNodes.ReadByteNode create() {
         return new TStringInternalNodesFactory.ReadByteNodeGen();
      }

      public static TStringInternalNodes.ReadByteNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.ReadByteNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.ReadByteNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
            if (TStringGuards.isUTF16(arg3Value)) {
               return TStringInternalNodes.ReadByteNode.doUTF16(arg0Value, arg1Value, arg2Value, arg3Value, ConditionProfile.getUncached());
            } else if (TStringGuards.isUTF32(arg3Value)) {
               return TStringInternalNodes.ReadByteNode.doUTF32(
                  arg0Value, arg1Value, arg2Value, arg3Value, ConditionProfile.getUncached(), ConditionProfile.getUncached()
               );
            } else if (!TStringGuards.isUTF16Or32(arg3Value)) {
               return TStringInternalNodes.ReadByteNode.doRest(arg0Value, arg1Value, arg2Value, arg3Value);
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
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

   @GeneratedBy(TStringInternalNodes.RegionEqualsNode.class)
   static final class RegionEqualsNodeGen extends TStringInternalNodes.RegionEqualsNode {
      private static final TStringInternalNodesFactory.RegionEqualsNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.RegionEqualsNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringIterator.NextNode decode_nextNodeA_;
      @Node.Child
      private TruffleStringIterator.NextNode decode_nextNodeB_;

      private RegionEqualsNodeGen() {
      }

      @Override
      boolean execute(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         int arg3Value,
         AbstractTruffleString arg4Value,
         Object arg5Value,
         int arg6Value,
         int arg7Value,
         int arg8Value,
         TruffleString.Encoding arg9Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value, arg6Value)) {
               return this.direct(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value);
            }

            if ((state_0 & 2) != 0 && !TStringGuards.isFixedWidth(arg2Value, arg6Value)) {
               return this.decode(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value,
                  this.decode_nextNodeA_,
                  this.decode_nextNodeB_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value);
      }

      private boolean executeAndSpecialize(
         AbstractTruffleString arg0Value,
         Object arg1Value,
         int arg2Value,
         int arg3Value,
         AbstractTruffleString arg4Value,
         Object arg5Value,
         int arg6Value,
         int arg7Value,
         int arg8Value,
         TruffleString.Encoding arg9Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var14;
         try {
            int state_0 = this.state_0_;
            if (!TStringGuards.isFixedWidth(arg2Value, arg6Value)) {
               if (TStringGuards.isFixedWidth(arg2Value, arg6Value)) {
                  throw new UnsupportedSpecializationException(
                     this,
                     new Node[]{null, null, null, null, null, null, null, null, null, null},
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     arg6Value,
                     arg7Value,
                     arg8Value,
                     arg9Value
                  );
               }

               this.decode_nextNodeA_ = super.insert(TruffleStringIterator.NextNode.create());
               this.decode_nextNodeB_ = super.insert(TruffleStringIterator.NextNode.create());
               int var19;
               this.state_0_ = var19 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.decode(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value,
                  this.decode_nextNodeA_,
                  this.decode_nextNodeB_
               );
            }

            int var18;
            this.state_0_ = var18 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var14 = this.direct(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value);
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
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      public static TStringInternalNodes.RegionEqualsNode create() {
         return new TStringInternalNodesFactory.RegionEqualsNodeGen();
      }

      public static TStringInternalNodes.RegionEqualsNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.RegionEqualsNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.RegionEqualsNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         boolean execute(
            AbstractTruffleString arg0Value,
            Object arg1Value,
            int arg2Value,
            int arg3Value,
            AbstractTruffleString arg4Value,
            Object arg5Value,
            int arg6Value,
            int arg7Value,
            int arg8Value,
            TruffleString.Encoding arg9Value
         ) {
            if (TStringGuards.isFixedWidth(arg2Value, arg6Value)) {
               return this.direct(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value);
            } else if (!TStringGuards.isFixedWidth(arg2Value, arg6Value)) {
               return this.decode(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value,
                  TruffleStringIterator.NextNode.getUncached(),
                  TruffleStringIterator.NextNode.getUncached()
               );
            } else {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{null, null, null, null, null, null, null, null, null, null},
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  arg7Value,
                  arg8Value,
                  arg9Value
               );
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

   @GeneratedBy(TStringInternalNodes.StrideFromCodeRangeNode.class)
   static final class StrideFromCodeRangeNodeGen extends TStringInternalNodes.StrideFromCodeRangeNode {
      private static final TStringInternalNodesFactory.StrideFromCodeRangeNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.StrideFromCodeRangeNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private StrideFromCodeRangeNodeGen() {
      }

      @Override
      int execute(int arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isUTF16(arg1Value)) {
               return this.doUTF16(arg0Value, arg1Value);
            }

            if ((state_0 & 2) != 0 && TStringGuards.isUTF32(arg1Value)) {
               return this.doUTF32(arg0Value, arg1Value);
            }

            if ((state_0 & 4) != 0 && !TStringGuards.isUTF16(arg1Value) && !TStringGuards.isUTF32(arg1Value)) {
               return this.doOther(arg0Value, arg1Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private int executeAndSpecialize(int arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (TStringGuards.isUTF16(arg1Value)) {
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.doUTF16(arg0Value, arg1Value);
         } else if (TStringGuards.isUTF32(arg1Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.doUTF32(arg0Value, arg1Value);
         } else if (!TStringGuards.isUTF16(arg1Value) && !TStringGuards.isUTF32(arg1Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 4;
            return this.doOther(arg0Value, arg1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
         }
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

      public static TStringInternalNodes.StrideFromCodeRangeNode create() {
         return new TStringInternalNodesFactory.StrideFromCodeRangeNodeGen();
      }

      public static TStringInternalNodes.StrideFromCodeRangeNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.StrideFromCodeRangeNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.StrideFromCodeRangeNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(int arg0Value, TruffleString.Encoding arg1Value) {
            if (TStringGuards.isUTF16(arg1Value)) {
               return this.doUTF16(arg0Value, arg1Value);
            } else if (TStringGuards.isUTF32(arg1Value)) {
               return this.doUTF32(arg0Value, arg1Value);
            } else if (!TStringGuards.isUTF16(arg1Value) && !TStringGuards.isUTF32(arg1Value)) {
               return this.doOther(arg0Value, arg1Value);
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

   @GeneratedBy(TStringInternalNodes.SubstringNode.class)
   static final class SubstringNodeGen extends TStringInternalNodes.SubstringNode {
      private static final TStringInternalNodesFactory.SubstringNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.SubstringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringInternalNodes.CreateSubstringNode materializeSubstring_createSubstringNode_;
      @Node.Child
      private TStringInternalNodesFactory.SubstringNodeGen.CreateLazySubstringData createLazySubstring_cache;

      private SubstringNodeGen() {
      }

      @Override
      TruffleString execute(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, boolean arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg5Value == 0) {
               return TStringInternalNodes.SubstringNode.lengthZero(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }

            if ((state_0 & 2) != 0 && arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (arg4Value == 0 && arg5Value == TStringGuards.length(arg0Value_)) {
                  return TStringInternalNodes.SubstringNode.sameStr(arg0Value_, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
               }
            }

            if ((state_0 & 4) != 0 && arg5Value > 0 && (arg5Value != TStringGuards.length(arg0Value) || arg0Value.isMutable()) && !arg6Value) {
               return TStringInternalNodes.SubstringNode.materializeSubstring(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.materializeSubstring_createSubstringNode_
               );
            }

            if ((state_0 & 8) != 0 && arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               TStringInternalNodesFactory.SubstringNodeGen.CreateLazySubstringData s3_ = this.createLazySubstring_cache;
               if (s3_ != null && arg5Value > 0 && arg5Value != TStringGuards.length(arg0Value_) && arg6Value) {
                  return this.createLazySubstring(
                     arg0Value_,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     arg6Value,
                     s3_.calcAttributesNode_,
                     s3_.stride1MustMaterializeProfile_,
                     s3_.stride2MustMaterializeProfile_
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private TruffleString executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, boolean arg6Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arg5Value == 0) {
               int var20;
               this.state_0_ = var20 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.SubstringNode.lengthZero(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else {
               if (arg0Value instanceof TruffleString) {
                  TruffleString arg0Value_ = (TruffleString)arg0Value;
                  if (arg4Value == 0 && arg5Value == TStringGuards.length(arg0Value_)) {
                     int var19;
                     this.state_0_ = var19 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return TStringInternalNodes.SubstringNode.sameStr(arg0Value_, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                  }
               }

               if (arg5Value <= 0 || arg5Value == TStringGuards.length(arg0Value) && !arg0Value.isMutable() || arg6Value) {
                  if (arg0Value instanceof TruffleString) {
                     TruffleString arg0Value_ = (TruffleString)arg0Value;
                     if (arg5Value > 0 && arg5Value != TStringGuards.length(arg0Value_) && arg6Value) {
                        TStringInternalNodesFactory.SubstringNodeGen.CreateLazySubstringData s3_ = super.insert(
                           new TStringInternalNodesFactory.SubstringNodeGen.CreateLazySubstringData()
                        );
                        s3_.calcAttributesNode_ = s3_.insertAccessor(TStringInternalNodesFactory.CalcStringAttributesNodeGen.create());
                        s3_.stride1MustMaterializeProfile_ = ConditionProfile.create();
                        s3_.stride2MustMaterializeProfile_ = ConditionProfile.create();
                        VarHandle.storeStoreFence();
                        this.createLazySubstring_cache = s3_;
                        int var18;
                        this.state_0_ = var18 = state_0 | 8;
                        lock.unlock();
                        hasLock = false;
                        return this.createLazySubstring(
                           arg0Value_,
                           arg1Value,
                           arg2Value,
                           arg3Value,
                           arg4Value,
                           arg5Value,
                           arg6Value,
                           s3_.calcAttributesNode_,
                           s3_.stride1MustMaterializeProfile_,
                           s3_.stride2MustMaterializeProfile_
                        );
                     }
                  }

                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
                  );
               } else {
                  this.materializeSubstring_createSubstringNode_ = super.insert(TStringInternalNodesFactory.CreateSubstringNodeGen.create());
                  int var17;
                  this.state_0_ = var17 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return TStringInternalNodes.SubstringNode.materializeSubstring(
                     arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.materializeSubstring_createSubstringNode_
                  );
               }
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
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      public static TStringInternalNodes.SubstringNode create() {
         return new TStringInternalNodesFactory.SubstringNodeGen();
      }

      public static TStringInternalNodes.SubstringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.SubstringNode.class)
      private static final class CreateLazySubstringData extends Node {
         @Node.Child
         TStringInternalNodes.CalcStringAttributesNode calcAttributesNode_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile stride1MustMaterializeProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile stride2MustMaterializeProfile_;

         CreateLazySubstringData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TStringInternalNodes.SubstringNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.SubstringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         TruffleString execute(
            AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, boolean arg6Value
         ) {
            if (arg5Value == 0) {
               return TStringInternalNodes.SubstringNode.lengthZero(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            } else {
               if (arg0Value instanceof TruffleString) {
                  TruffleString arg0Value_ = (TruffleString)arg0Value;
                  if (arg4Value == 0 && arg5Value == TStringGuards.length(arg0Value_)) {
                     return TStringInternalNodes.SubstringNode.sameStr(arg0Value_, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                  }
               }

               if (arg5Value > 0 && (arg5Value != TStringGuards.length(arg0Value) || arg0Value.isMutable()) && !arg6Value) {
                  return TStringInternalNodes.SubstringNode.materializeSubstring(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     arg6Value,
                     TStringInternalNodesFactory.CreateSubstringNodeGen.getUncached()
                  );
               } else {
                  if (arg0Value instanceof TruffleString) {
                     TruffleString arg0Value_ = (TruffleString)arg0Value;
                     if (arg5Value > 0 && arg5Value != TStringGuards.length(arg0Value_) && arg6Value) {
                        return this.createLazySubstring(
                           arg0Value_,
                           arg1Value,
                           arg2Value,
                           arg3Value,
                           arg4Value,
                           arg5Value,
                           arg6Value,
                           TStringInternalNodes.CalcStringAttributesNode.getUncached(),
                           ConditionProfile.getUncached(),
                           ConditionProfile.getUncached()
                        );
                     }
                  }

                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value
                  );
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

   @GeneratedBy(TStringInternalNodes.ToJavaStringNode.class)
   static final class ToJavaStringNodeGen extends TStringInternalNodes.ToJavaStringNode {
      private static final TStringInternalNodesFactory.ToJavaStringNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.ToJavaStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringInternalNodes.CreateJavaStringNode createStringNode;
      @Node.Child
      private TStringInternalNodes.GetCodePointLengthNode generic_getCodePointLengthNode_;
      @Node.Child
      private TStringInternalNodes.GetCodeRangeNode generic_getCodeRangeNode_;
      @Node.Child
      private TStringInternalNodes.TransCodeNode generic_transCodeNode_;

      private ToJavaStringNodeGen() {
      }

      @Override
      TruffleString execute(TruffleString arg0Value, Object arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value.isCompatibleTo(TruffleString.Encoding.UTF_16)) {
               return TStringInternalNodes.ToJavaStringNode.doUTF16(arg0Value, arg1Value, this.createStringNode);
            }

            if ((state_0 & 2) != 0 && !arg0Value.isCompatibleTo(TruffleString.Encoding.UTF_16)) {
               return TStringInternalNodes.ToJavaStringNode.doGeneric(
                  arg0Value,
                  arg1Value,
                  this.generic_getCodePointLengthNode_,
                  this.generic_getCodeRangeNode_,
                  this.generic_transCodeNode_,
                  this.createStringNode
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private TruffleString executeAndSpecialize(TruffleString arg0Value, Object arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var6;
         try {
            int state_0 = this.state_0_;
            if (arg0Value.isCompatibleTo(TruffleString.Encoding.UTF_16)) {
               this.createStringNode = super.insert(
                  this.createStringNode == null ? TStringInternalNodesFactory.CreateJavaStringNodeGen.create() : this.createStringNode
               );
               int var11;
               this.state_0_ = var11 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return TStringInternalNodes.ToJavaStringNode.doUTF16(arg0Value, arg1Value, this.createStringNode);
            }

            if (arg0Value.isCompatibleTo(TruffleString.Encoding.UTF_16)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            this.generic_getCodePointLengthNode_ = super.insert(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            this.generic_getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            this.generic_transCodeNode_ = super.insert(TStringInternalNodesFactory.TransCodeNodeGen.create());
            this.createStringNode = super.insert(
               this.createStringNode == null ? TStringInternalNodesFactory.CreateJavaStringNodeGen.create() : this.createStringNode
            );
            int var10;
            this.state_0_ = var10 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            var6 = TStringInternalNodes.ToJavaStringNode.doGeneric(
               arg0Value, arg1Value, this.generic_getCodePointLengthNode_, this.generic_getCodeRangeNode_, this.generic_transCodeNode_, this.createStringNode
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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

      public static TStringInternalNodes.ToJavaStringNode create() {
         return new TStringInternalNodesFactory.ToJavaStringNodeGen();
      }

      public static TStringInternalNodes.ToJavaStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.ToJavaStringNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.ToJavaStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         TruffleString execute(TruffleString arg0Value, Object arg1Value) {
            if (arg0Value.isCompatibleTo(TruffleString.Encoding.UTF_16)) {
               return TStringInternalNodes.ToJavaStringNode.doUTF16(arg0Value, arg1Value, TStringInternalNodesFactory.CreateJavaStringNodeGen.getUncached());
            } else if (!arg0Value.isCompatibleTo(TruffleString.Encoding.UTF_16)) {
               return TStringInternalNodes.ToJavaStringNode.doGeneric(
                  arg0Value,
                  arg1Value,
                  TStringInternalNodes.GetCodePointLengthNode.getUncached(),
                  TStringInternalNodes.GetCodeRangeNode.getUncached(),
                  TStringInternalNodesFactory.TransCodeNodeGen.getUncached(),
                  TStringInternalNodesFactory.CreateJavaStringNodeGen.getUncached()
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

   @GeneratedBy(TStringInternalNodes.TransCodeIntlNode.class)
   static final class TransCodeIntlNodeGen extends TStringInternalNodes.TransCodeIntlNode {
      private static final TStringInternalNodesFactory.TransCodeIntlNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.TransCodeIntlNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringIterator.NextNode iteratorNextNode;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile brokenProfile;
      @CompilerDirectives.CompilationFinal
      private BranchProfile outOfMemoryProfile;
      @Node.Child
      private TStringInternalNodesFactory.TransCodeIntlNodeGen.UnsupportedData unsupported_cache;

      private TransCodeIntlNodeGen() {
      }

      @Override
      TruffleString execute(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value, TruffleString.Encoding arg5Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.isSupportedEncoding(arg4Value) && (TStringGuards.isAscii(arg5Value) || TStringGuards.isBytes(arg5Value))) {
               return this.targetAscii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
            }

            if ((state_0 & 2) != 0 && TStringGuards.isSupportedEncoding(arg4Value) && TStringGuards.isLatin1(arg5Value)) {
               return this.latin1Transcode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
            }

            if ((state_0 & 4) != 0
               && TStringGuards.isSupportedEncoding(arg4Value)
               && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
               && TStringGuards.isUTF8(arg5Value)) {
               return this.utf8TranscodeRegular(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.brokenProfile, this.outOfMemoryProfile
               );
            }

            if ((state_0 & 8) != 0
               && TStringGuards.isSupportedEncoding(arg4Value)
               && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
               && TStringGuards.isUTF8(arg5Value)) {
               return this.utf8TranscodeLarge(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.brokenProfile, this.outOfMemoryProfile
               );
            }

            if ((state_0 & 16) != 0 && TStringGuards.isUTF32(arg4Value) && TStringGuards.isUTF16(arg5Value)) {
               return this.utf16Fixed32Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 32) != 0
               && TStringGuards.isSupportedEncoding(arg4Value)
               && !TStringGuards.isFixedWidth(arg3Value)
               && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
               && TStringGuards.isUTF16(arg5Value)) {
               return this.utf16TranscodeRegular(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.outOfMemoryProfile
               );
            }

            if ((state_0 & 64) != 0
               && TStringGuards.isSupportedEncoding(arg4Value)
               && !TStringGuards.isFixedWidth(arg3Value)
               && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
               && TStringGuards.isUTF16(arg5Value)) {
               return this.utf16TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.outOfMemoryProfile);
            }

            if ((state_0 & 128) != 0
               && !TStringGuards.isUTF16(arg4Value)
               && TStringGuards.isSupportedEncoding(arg4Value)
               && !TStringGuards.isFixedWidth(arg3Value)
               && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
               && TStringGuards.isUTF32(arg5Value)) {
               return this.utf32TranscodeRegular(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
            }

            if ((state_0 & 256) != 0
               && TStringGuards.isSupportedEncoding(arg4Value)
               && !TStringGuards.isFixedWidth(arg3Value)
               && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
               && TStringGuards.isUTF32(arg5Value)) {
               return TStringInternalNodes.TransCodeIntlNode.utf32TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if ((state_0 & 512) != 0
               && TStringGuards.isUTF16(arg4Value)
               && !TStringGuards.isFixedWidth(arg3Value)
               && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
               && TStringGuards.isUTF32(arg5Value)) {
               return this.utf32TranscodeUTF16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
            }

            if ((state_0 & 1024) != 0) {
               TStringInternalNodesFactory.TransCodeIntlNodeGen.UnsupportedData s10_ = this.unsupported_cache;
               if (s10_ != null && (TStringGuards.isUnsupportedEncoding(arg4Value) || TStringGuards.isUnsupportedEncoding(arg5Value))) {
                  return this.unsupported(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     s10_.outOfMemoryProfile_,
                     s10_.nativeProfile_,
                     s10_.fromBufferWithStringCompactionNode_
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private TruffleString executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value, TruffleString.Encoding arg5Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString s10_;
         try {
            int state_0 = this.state_0_;
            if (TStringGuards.isSupportedEncoding(arg4Value) && (TStringGuards.isAscii(arg5Value) || TStringGuards.isBytes(arg5Value))) {
               this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
               int var25;
               this.state_0_ = var25 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.targetAscii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
            }

            if (TStringGuards.isSupportedEncoding(arg4Value) && TStringGuards.isLatin1(arg5Value)) {
               this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
               int var24;
               this.state_0_ = var24 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.latin1Transcode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
            }

            if (TStringGuards.isSupportedEncoding(arg4Value) && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF8(arg5Value)) {
               this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
               this.brokenProfile = this.brokenProfile == null ? ConditionProfile.create() : this.brokenProfile;
               this.outOfMemoryProfile = this.outOfMemoryProfile == null ? BranchProfile.create() : this.outOfMemoryProfile;
               int var23;
               this.state_0_ = var23 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.utf8TranscodeRegular(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.brokenProfile, this.outOfMemoryProfile
               );
            }

            if (TStringGuards.isSupportedEncoding(arg4Value) && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF8(arg5Value)) {
               this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
               this.brokenProfile = this.brokenProfile == null ? ConditionProfile.create() : this.brokenProfile;
               this.outOfMemoryProfile = this.outOfMemoryProfile == null ? BranchProfile.create() : this.outOfMemoryProfile;
               int var22;
               this.state_0_ = var22 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.utf8TranscodeLarge(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.brokenProfile, this.outOfMemoryProfile
               );
            }

            if (TStringGuards.isUTF32(arg4Value) && TStringGuards.isUTF16(arg5Value)) {
               int var21;
               this.state_0_ = var21 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.utf16Fixed32Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
            }

            if (TStringGuards.isSupportedEncoding(arg4Value)
               && !TStringGuards.isFixedWidth(arg3Value)
               && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
               && TStringGuards.isUTF16(arg5Value)) {
               this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
               this.outOfMemoryProfile = this.outOfMemoryProfile == null ? BranchProfile.create() : this.outOfMemoryProfile;
               int var20;
               this.state_0_ = var20 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.utf16TranscodeRegular(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.outOfMemoryProfile
               );
            }

            if (TStringGuards.isSupportedEncoding(arg4Value)
               && !TStringGuards.isFixedWidth(arg3Value)
               && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
               && TStringGuards.isUTF16(arg5Value)) {
               this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
               this.outOfMemoryProfile = this.outOfMemoryProfile == null ? BranchProfile.create() : this.outOfMemoryProfile;
               int var19;
               this.state_0_ = var19 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.utf16TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.outOfMemoryProfile);
            }

            if (TStringGuards.isUTF16(arg4Value)
               || !TStringGuards.isSupportedEncoding(arg4Value)
               || TStringGuards.isFixedWidth(arg3Value)
               || TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
               || !TStringGuards.isUTF32(arg5Value)) {
               if (TStringGuards.isSupportedEncoding(arg4Value)
                  && !TStringGuards.isFixedWidth(arg3Value)
                  && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
                  && TStringGuards.isUTF32(arg5Value)) {
                  int var18;
                  this.state_0_ = var18 = state_0 | 256;
                  lock.unlock();
                  hasLock = false;
                  return TStringInternalNodes.TransCodeIntlNode.utf32TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
               }

               if (TStringGuards.isUTF16(arg4Value)
                  && !TStringGuards.isFixedWidth(arg3Value)
                  && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
                  && TStringGuards.isUTF32(arg5Value)) {
                  this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
                  int var17;
                  this.state_0_ = var17 = state_0 | 512;
                  lock.unlock();
                  hasLock = false;
                  return this.utf32TranscodeUTF16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
               }

               if (!TStringGuards.isUnsupportedEncoding(arg4Value) && !TStringGuards.isUnsupportedEncoding(arg5Value)) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value
                  );
               }

               TStringInternalNodesFactory.TransCodeIntlNodeGen.UnsupportedData s10_x = super.insert(
                  new TStringInternalNodesFactory.TransCodeIntlNodeGen.UnsupportedData()
               );
               s10_x.outOfMemoryProfile_ = BranchProfile.create();
               s10_x.nativeProfile_ = ConditionProfile.create();
               s10_x.fromBufferWithStringCompactionNode_ = s10_x.insertAccessor(TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.create());
               VarHandle.storeStoreFence();
               this.unsupported_cache = s10_x;
               int var16;
               this.state_0_ = var16 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               return this.unsupported(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  s10_x.outOfMemoryProfile_,
                  s10_x.nativeProfile_,
                  s10_x.fromBufferWithStringCompactionNode_
               );
            }

            this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
            int var15;
            this.state_0_ = var15 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            s10_ = this.utf32TranscodeRegular(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return s10_;
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

      public static TStringInternalNodes.TransCodeIntlNode create() {
         return new TStringInternalNodesFactory.TransCodeIntlNodeGen();
      }

      public static TStringInternalNodes.TransCodeIntlNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.TransCodeIntlNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.TransCodeIntlNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         TruffleString execute(
            AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value, TruffleString.Encoding arg5Value
         ) {
            if (!TStringGuards.isSupportedEncoding(arg4Value) || !TStringGuards.isAscii(arg5Value) && !TStringGuards.isBytes(arg5Value)) {
               if (TStringGuards.isSupportedEncoding(arg4Value) && TStringGuards.isLatin1(arg5Value)) {
                  return this.latin1Transcode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleStringIterator.NextNode.getUncached());
               } else if (TStringGuards.isSupportedEncoding(arg4Value)
                  && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
                  && TStringGuards.isUTF8(arg5Value)) {
                  return this.utf8TranscodeRegular(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     TruffleStringIterator.NextNode.getUncached(),
                     ConditionProfile.getUncached(),
                     BranchProfile.getUncached()
                  );
               } else if (TStringGuards.isSupportedEncoding(arg4Value)
                  && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
                  && TStringGuards.isUTF8(arg5Value)) {
                  return this.utf8TranscodeLarge(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     TruffleStringIterator.NextNode.getUncached(),
                     ConditionProfile.getUncached(),
                     BranchProfile.getUncached()
                  );
               } else if (TStringGuards.isUTF32(arg4Value) && TStringGuards.isUTF16(arg5Value)) {
                  return this.utf16Fixed32Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
               } else if (TStringGuards.isSupportedEncoding(arg4Value)
                  && !TStringGuards.isFixedWidth(arg3Value)
                  && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
                  && TStringGuards.isUTF16(arg5Value)) {
                  return this.utf16TranscodeRegular(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     TruffleStringIterator.NextNode.getUncached(),
                     BranchProfile.getUncached()
                  );
               } else if (TStringGuards.isSupportedEncoding(arg4Value)
                  && !TStringGuards.isFixedWidth(arg3Value)
                  && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
                  && TStringGuards.isUTF16(arg5Value)) {
                  return this.utf16TranscodeLarge(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     TruffleStringIterator.NextNode.getUncached(),
                     BranchProfile.getUncached()
                  );
               } else if (!TStringGuards.isUTF16(arg4Value)
                  && TStringGuards.isSupportedEncoding(arg4Value)
                  && !TStringGuards.isFixedWidth(arg3Value)
                  && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
                  && TStringGuards.isUTF32(arg5Value)) {
                  return this.utf32TranscodeRegular(
                     arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleStringIterator.NextNode.getUncached()
                  );
               } else if (TStringGuards.isSupportedEncoding(arg4Value)
                  && !TStringGuards.isFixedWidth(arg3Value)
                  && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
                  && TStringGuards.isUTF32(arg5Value)) {
                  return TStringInternalNodes.TransCodeIntlNode.utf32TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
               } else if (TStringGuards.isUTF16(arg4Value)
                  && !TStringGuards.isFixedWidth(arg3Value)
                  && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value)
                  && TStringGuards.isUTF32(arg5Value)) {
                  return this.utf32TranscodeUTF16(
                     arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleStringIterator.NextNode.getUncached()
                  );
               } else if (!TStringGuards.isUnsupportedEncoding(arg4Value) && !TStringGuards.isUnsupportedEncoding(arg5Value)) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value
                  );
               } else {
                  return this.unsupported(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     arg5Value,
                     BranchProfile.getUncached(),
                     ConditionProfile.getUncached(),
                     TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.getUncached()
                  );
               }
            } else {
               return this.targetAscii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleStringIterator.NextNode.getUncached());
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

      @GeneratedBy(TStringInternalNodes.TransCodeIntlNode.class)
      private static final class UnsupportedData extends Node {
         @CompilerDirectives.CompilationFinal
         BranchProfile outOfMemoryProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile nativeProfile_;
         @Node.Child
         TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode_;

         UnsupportedData() {
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

   @GeneratedBy(TStringInternalNodes.TransCodeNode.class)
   static final class TransCodeNodeGen extends TStringInternalNodes.TransCodeNode {
      private static final TStringInternalNodesFactory.TransCodeNodeGen.Uncached UNCACHED = new TStringInternalNodesFactory.TransCodeNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile asciiBytesInvalidProfile_;
      @Node.Child
      private TStringInternalNodes.TransCodeIntlNode transCodeIntlNode_;

      private TransCodeNodeGen() {
      }

      @Override
      TruffleString execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return this.transcode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.asciiBytesInvalidProfile_, this.transCodeIntlNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         }
      }

      private TruffleString executeAndSpecialize(
         AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var9;
         try {
            int state_0 = this.state_0_;
            this.asciiBytesInvalidProfile_ = ConditionProfile.create();
            this.transCodeIntlNode_ = super.insert(TStringInternalNodesFactory.TransCodeIntlNodeGen.create());
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = this.transcode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.asciiBytesInvalidProfile_, this.transCodeIntlNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var9;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TStringInternalNodes.TransCodeNode create() {
         return new TStringInternalNodesFactory.TransCodeNodeGen();
      }

      public static TStringInternalNodes.TransCodeNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TStringInternalNodes.TransCodeNode.class)
      @DenyReplace
      private static final class Uncached extends TStringInternalNodes.TransCodeNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         TruffleString execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            return this.transcode(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               ConditionProfile.getUncached(),
               TStringInternalNodesFactory.TransCodeIntlNodeGen.getUncached()
            );
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
