package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(MutableTruffleString.class)
public final class MutableTruffleStringFactory {
   @GeneratedBy(MutableTruffleString.AsManagedNode.class)
   static final class AsManagedNodeGen extends MutableTruffleString.AsManagedNode {
      private static final MutableTruffleStringFactory.AsManagedNodeGen.Uncached UNCACHED = new MutableTruffleStringFactory.AsManagedNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.CopyToByteArrayNode fromTruffleString_copyToByteArrayNode_;

      private AsManagedNodeGen() {
      }

      @Override
      public MutableTruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (!arg0Value_.isNative()) {
                  return MutableTruffleString.AsManagedNode.mutable(arg0Value_, arg1Value);
               }
            }

            if ((state_0 & 2) != 0 && (arg0Value.isNative() || arg0Value.isImmutable())) {
               return MutableTruffleString.AsManagedNode.fromTruffleString(arg0Value, arg1Value, this.fromTruffleString_copyToByteArrayNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private MutableTruffleString executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (!arg0Value_.isNative()) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return MutableTruffleString.AsManagedNode.mutable(arg0Value_, arg1Value);
               }
            }

            if (!arg0Value.isNative() && !arg0Value.isImmutable()) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            } else {
               this.fromTruffleString_copyToByteArrayNode_ = super.insert(TruffleString.CopyToByteArrayNode.create());
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return MutableTruffleString.AsManagedNode.fromTruffleString(arg0Value, arg1Value, this.fromTruffleString_copyToByteArrayNode_);
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

      public static MutableTruffleString.AsManagedNode create() {
         return new MutableTruffleStringFactory.AsManagedNodeGen();
      }

      public static MutableTruffleString.AsManagedNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(MutableTruffleString.AsManagedNode.class)
      @DenyReplace
      private static final class Uncached extends MutableTruffleString.AsManagedNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public MutableTruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (!arg0Value_.isNative()) {
                  return MutableTruffleString.AsManagedNode.mutable(arg0Value_, arg1Value);
               }
            }

            if (!arg0Value.isNative() && !arg0Value.isImmutable()) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            } else {
               return MutableTruffleString.AsManagedNode.fromTruffleString(arg0Value, arg1Value, TruffleString.CopyToByteArrayNode.getUncached());
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

   @GeneratedBy(MutableTruffleString.AsMutableTruffleStringNode.class)
   static final class AsMutableTruffleStringNodeGen extends MutableTruffleString.AsMutableTruffleStringNode {
      private static final MutableTruffleStringFactory.AsMutableTruffleStringNodeGen.Uncached UNCACHED = new MutableTruffleStringFactory.AsMutableTruffleStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.CopyToByteArrayNode fromTruffleString_copyToByteArrayNode_;

      private AsMutableTruffleStringNodeGen() {
      }

      @Override
      public MutableTruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               return MutableTruffleString.AsMutableTruffleStringNode.mutable(arg0Value_, arg1Value);
            }

            if ((state_0 & 2) != 0 && arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               return MutableTruffleString.AsMutableTruffleStringNode.fromTruffleString(arg0Value_, arg1Value, this.fromTruffleString_copyToByteArrayNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private MutableTruffleString executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         MutableTruffleString var7;
         try {
            int state_0 = this.state_0_;
            if (!(arg0Value instanceof MutableTruffleString)) {
               if (!(arg0Value instanceof TruffleString)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
               }

               TruffleString arg0Value_ = (TruffleString)arg0Value;
               this.fromTruffleString_copyToByteArrayNode_ = super.insert(TruffleString.CopyToByteArrayNode.create());
               int var12;
               this.state_0_ = var12 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return MutableTruffleString.AsMutableTruffleStringNode.fromTruffleString(arg0Value_, arg1Value, this.fromTruffleString_copyToByteArrayNode_);
            }

            MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = MutableTruffleString.AsMutableTruffleStringNode.mutable(arg0Value_, arg1Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var7;
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

      public static MutableTruffleString.AsMutableTruffleStringNode create() {
         return new MutableTruffleStringFactory.AsMutableTruffleStringNodeGen();
      }

      public static MutableTruffleString.AsMutableTruffleStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(MutableTruffleString.AsMutableTruffleStringNode.class)
      @DenyReplace
      private static final class Uncached extends MutableTruffleString.AsMutableTruffleStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public MutableTruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               return MutableTruffleString.AsMutableTruffleStringNode.mutable(arg0Value_, arg1Value);
            } else if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               return MutableTruffleString.AsMutableTruffleStringNode.fromTruffleString(arg0Value_, arg1Value, TruffleString.CopyToByteArrayNode.getUncached());
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

   @GeneratedBy(MutableTruffleString.CalcLazyAttributesNode.class)
   static final class CalcLazyAttributesNodeGen extends MutableTruffleString.CalcLazyAttributesNode {
      private static final MutableTruffleStringFactory.CalcLazyAttributesNodeGen.Uncached UNCACHED = new MutableTruffleStringFactory.CalcLazyAttributesNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private MutableTruffleStringFactory.CalcLazyAttributesNodeGen.CalcData calc_cache;

      private CalcLazyAttributesNodeGen() {
      }

      @Override
      void execute(MutableTruffleString arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            MutableTruffleStringFactory.CalcLazyAttributesNodeGen.CalcData s0_ = this.calc_cache;
            if (s0_ != null) {
               this.calc(
                  arg0Value,
                  s0_.dataClassProfile_,
                  s0_.asciiBytesLatinProfile_,
                  s0_.utf8Profile_,
                  s0_.utf8BrokenProfile_,
                  s0_.utf16Profile_,
                  s0_.utf16S0Profile_,
                  s0_.utf32Profile_,
                  s0_.utf32S0Profile_,
                  s0_.utf32S1Profile_,
                  s0_.exoticMaterializeNativeProfile_,
                  s0_.exoticValidProfile_,
                  s0_.exoticFixedWidthProfile_
               );
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value);
      }

      private void executeAndSpecialize(MutableTruffleString arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            MutableTruffleStringFactory.CalcLazyAttributesNodeGen.CalcData s0_ = new MutableTruffleStringFactory.CalcLazyAttributesNodeGen.CalcData();
            s0_.dataClassProfile_ = ValueProfile.createClassProfile();
            s0_.asciiBytesLatinProfile_ = ConditionProfile.create();
            s0_.utf8Profile_ = ConditionProfile.create();
            s0_.utf8BrokenProfile_ = ConditionProfile.create();
            s0_.utf16Profile_ = ConditionProfile.create();
            s0_.utf16S0Profile_ = ConditionProfile.create();
            s0_.utf32Profile_ = ConditionProfile.create();
            s0_.utf32S0Profile_ = ConditionProfile.create();
            s0_.utf32S1Profile_ = ConditionProfile.create();
            s0_.exoticMaterializeNativeProfile_ = ConditionProfile.create();
            s0_.exoticValidProfile_ = ConditionProfile.create();
            s0_.exoticFixedWidthProfile_ = ConditionProfile.create();
            VarHandle.storeStoreFence();
            this.calc_cache = s0_;
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            this.calc(
               arg0Value,
               s0_.dataClassProfile_,
               s0_.asciiBytesLatinProfile_,
               s0_.utf8Profile_,
               s0_.utf8BrokenProfile_,
               s0_.utf16Profile_,
               s0_.utf16S0Profile_,
               s0_.utf32Profile_,
               s0_.utf32S0Profile_,
               s0_.utf32S1Profile_,
               s0_.exoticMaterializeNativeProfile_,
               s0_.exoticValidProfile_,
               s0_.exoticFixedWidthProfile_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static MutableTruffleString.CalcLazyAttributesNode create() {
         return new MutableTruffleStringFactory.CalcLazyAttributesNodeGen();
      }

      public static MutableTruffleString.CalcLazyAttributesNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(MutableTruffleString.CalcLazyAttributesNode.class)
      private static final class CalcData {
         @CompilerDirectives.CompilationFinal
         ValueProfile dataClassProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile asciiBytesLatinProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf8Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf8BrokenProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf16Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf16S0Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf32Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf32S0Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf32S1Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile exoticMaterializeNativeProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile exoticValidProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile exoticFixedWidthProfile_;

         CalcData() {
         }
      }

      @GeneratedBy(MutableTruffleString.CalcLazyAttributesNode.class)
      @DenyReplace
      private static final class Uncached extends MutableTruffleString.CalcLazyAttributesNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         void execute(MutableTruffleString arg0Value) {
            this.calc(
               arg0Value,
               ValueProfile.getUncached(),
               ConditionProfile.getUncached(),
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

   @GeneratedBy(MutableTruffleString.ConcatNode.class)
   static final class ConcatNodeGen extends MutableTruffleString.ConcatNode {
      private static final MutableTruffleStringFactory.ConcatNodeGen.Uncached UNCACHED = new MutableTruffleStringFactory.ConcatNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private MutableTruffleStringFactory.ConcatNodeGen.ConcatData concat_cache;

      private ConcatNodeGen() {
      }

      @Override
      public MutableTruffleString execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            MutableTruffleStringFactory.ConcatNodeGen.ConcatData s0_ = this.concat_cache;
            if (s0_ != null) {
               return MutableTruffleString.ConcatNode.concat(
                  arg0Value, arg1Value, arg2Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.materializeBytesNode_, s0_.outOfMemoryProfile_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private MutableTruffleString executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         MutableTruffleString var8;
         try {
            int state_0 = this.state_0_;
            MutableTruffleStringFactory.ConcatNodeGen.ConcatData s0_ = super.insert(new MutableTruffleStringFactory.ConcatNodeGen.ConcatData());
            s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.materializeBytesNode_ = s0_.insertAccessor(TStringInternalNodesFactory.ConcatMaterializeBytesNodeGen.create());
            s0_.outOfMemoryProfile_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.concat_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = MutableTruffleString.ConcatNode.concat(
               arg0Value, arg1Value, arg2Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.materializeBytesNode_, s0_.outOfMemoryProfile_
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

      public static MutableTruffleString.ConcatNode create() {
         return new MutableTruffleStringFactory.ConcatNodeGen();
      }

      public static MutableTruffleString.ConcatNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(MutableTruffleString.ConcatNode.class)
      private static final class ConcatData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeA_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeB_;
         @Node.Child
         TStringInternalNodes.ConcatMaterializeBytesNode materializeBytesNode_;
         @CompilerDirectives.CompilationFinal
         BranchProfile outOfMemoryProfile_;

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

      @GeneratedBy(MutableTruffleString.ConcatNode.class)
      @DenyReplace
      private static final class Uncached extends MutableTruffleString.ConcatNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public MutableTruffleString execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
            return MutableTruffleString.ConcatNode.concat(
               arg0Value,
               arg1Value,
               arg2Value,
               TruffleString.ToIndexableNode.getUncached(),
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodesFactory.ConcatMaterializeBytesNodeGen.getUncached(),
               BranchProfile.getUncached()
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

   @GeneratedBy(MutableTruffleString.ForceEncodingNode.class)
   static final class ForceEncodingNodeGen extends MutableTruffleString.ForceEncodingNode {
      private static final MutableTruffleStringFactory.ForceEncodingNodeGen.Uncached UNCACHED = new MutableTruffleStringFactory.ForceEncodingNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.CopyToByteArrayNode reinterpret_copyToByteArrayNode_;

      private ForceEncodingNodeGen() {
      }

      @Override
      public MutableTruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.Encoding arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (arg0Value_.isCompatibleTo(arg2Value)) {
                  return MutableTruffleString.ForceEncodingNode.compatible(arg0Value_, arg1Value, arg2Value);
               }
            }

            if ((state_0 & 2) != 0 && (!arg0Value.isCompatibleTo(arg2Value) || arg0Value.isImmutable())) {
               return MutableTruffleString.ForceEncodingNode.reinterpret(arg0Value, arg1Value, arg2Value, this.reinterpret_copyToByteArrayNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private MutableTruffleString executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.Encoding arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (arg0Value_.isCompatibleTo(arg2Value)) {
                  int var13;
                  this.state_0_ = var13 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return MutableTruffleString.ForceEncodingNode.compatible(arg0Value_, arg1Value, arg2Value);
               }
            }

            if (arg0Value.isCompatibleTo(arg2Value) && !arg0Value.isImmutable()) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            } else {
               this.reinterpret_copyToByteArrayNode_ = super.insert(TruffleString.CopyToByteArrayNode.create());
               int var12;
               this.state_0_ = var12 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return MutableTruffleString.ForceEncodingNode.reinterpret(arg0Value, arg1Value, arg2Value, this.reinterpret_copyToByteArrayNode_);
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

      public static MutableTruffleString.ForceEncodingNode create() {
         return new MutableTruffleStringFactory.ForceEncodingNodeGen();
      }

      public static MutableTruffleString.ForceEncodingNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(MutableTruffleString.ForceEncodingNode.class)
      @DenyReplace
      private static final class Uncached extends MutableTruffleString.ForceEncodingNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public MutableTruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.Encoding arg2Value) {
            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (arg0Value_.isCompatibleTo(arg2Value)) {
                  return MutableTruffleString.ForceEncodingNode.compatible(arg0Value_, arg1Value, arg2Value);
               }
            }

            if (arg0Value.isCompatibleTo(arg2Value) && !arg0Value.isImmutable()) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            } else {
               return MutableTruffleString.ForceEncodingNode.reinterpret(arg0Value, arg1Value, arg2Value, TruffleString.CopyToByteArrayNode.getUncached());
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

   @GeneratedBy(MutableTruffleString.FromByteArrayNode.class)
   static final class FromByteArrayNodeGen extends MutableTruffleString.FromByteArrayNode {
      private static final MutableTruffleStringFactory.FromByteArrayNodeGen.Uncached UNCACHED = new MutableTruffleStringFactory.FromByteArrayNodeGen.Uncached();

      private FromByteArrayNodeGen() {
      }

      @Override
      public MutableTruffleString execute(byte[] arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
         return MutableTruffleString.FromByteArrayNode.fromByteArray(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      public static MutableTruffleString.FromByteArrayNode create() {
         return new MutableTruffleStringFactory.FromByteArrayNodeGen();
      }

      public static MutableTruffleString.FromByteArrayNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(MutableTruffleString.FromByteArrayNode.class)
      @DenyReplace
      private static final class Uncached extends MutableTruffleString.FromByteArrayNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public MutableTruffleString execute(byte[] arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            return MutableTruffleString.FromByteArrayNode.fromByteArray(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
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

   @GeneratedBy(MutableTruffleString.FromNativePointerNode.class)
   static final class FromNativePointerNodeGen extends MutableTruffleString.FromNativePointerNode {
      private static final MutableTruffleStringFactory.FromNativePointerNodeGen.Uncached UNCACHED = new MutableTruffleStringFactory.FromNativePointerNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private Node interopLibrary_;

      private FromNativePointerNodeGen() {
      }

      @Override
      public MutableTruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return this.fromNativePointer(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.interopLibrary_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         }
      }

      private MutableTruffleString executeAndSpecialize(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         MutableTruffleString var9;
         try {
            int state_0 = this.state_0_;
            this.interopLibrary_ = super.insert(TStringAccessor.createInteropLibrary());
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = this.fromNativePointer(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.interopLibrary_);
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

      public static MutableTruffleString.FromNativePointerNode create() {
         return new MutableTruffleStringFactory.FromNativePointerNodeGen();
      }

      public static MutableTruffleString.FromNativePointerNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(MutableTruffleString.FromNativePointerNode.class)
      @DenyReplace
      private static final class Uncached extends MutableTruffleString.FromNativePointerNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public MutableTruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            return this.fromNativePointer(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TStringAccessor.getUncachedInteropLibrary());
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

   @GeneratedBy(MutableTruffleString.SubstringByteIndexNode.class)
   static final class SubstringByteIndexNodeGen extends MutableTruffleString.SubstringByteIndexNode {
      private static final MutableTruffleStringFactory.SubstringByteIndexNodeGen.Uncached UNCACHED = new MutableTruffleStringFactory.SubstringByteIndexNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.CopyToByteArrayNode copyToByteArrayNode_;

      private SubstringByteIndexNodeGen() {
      }

      @Override
      public MutableTruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return MutableTruffleString.SubstringByteIndexNode.substringByteIndex(arg0Value, arg1Value, arg2Value, arg3Value, this.copyToByteArrayNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
         }
      }

      private MutableTruffleString executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         MutableTruffleString var8;
         try {
            int state_0 = this.state_0_;
            this.copyToByteArrayNode_ = super.insert(TruffleString.CopyToByteArrayNode.create());
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = MutableTruffleString.SubstringByteIndexNode.substringByteIndex(arg0Value, arg1Value, arg2Value, arg3Value, this.copyToByteArrayNode_);
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

      public static MutableTruffleString.SubstringByteIndexNode create() {
         return new MutableTruffleStringFactory.SubstringByteIndexNodeGen();
      }

      public static MutableTruffleString.SubstringByteIndexNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(MutableTruffleString.SubstringByteIndexNode.class)
      @DenyReplace
      private static final class Uncached extends MutableTruffleString.SubstringByteIndexNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public MutableTruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
            return MutableTruffleString.SubstringByteIndexNode.substringByteIndex(
               arg0Value, arg1Value, arg2Value, arg3Value, TruffleString.CopyToByteArrayNode.getUncached()
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

   @GeneratedBy(MutableTruffleString.SubstringNode.class)
   static final class SubstringNodeGen extends MutableTruffleString.SubstringNode {
      private static final MutableTruffleStringFactory.SubstringNodeGen.Uncached UNCACHED = new MutableTruffleStringFactory.SubstringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private MutableTruffleStringFactory.SubstringNodeGen.SubstringData substring_cache;

      private SubstringNodeGen() {
      }

      @Override
      public MutableTruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            MutableTruffleStringFactory.SubstringNodeGen.SubstringData s0_ = this.substring_cache;
            if (s0_ != null) {
               return MutableTruffleString.SubstringNode.substring(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  s0_.toIndexableNode_,
                  s0_.getCodeRangeANode_,
                  s0_.getCodePointLengthNode_,
                  s0_.translateIndexNode_,
                  s0_.copyToByteArrayNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private MutableTruffleString executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         MutableTruffleString var9;
         try {
            int state_0 = this.state_0_;
            MutableTruffleStringFactory.SubstringNodeGen.SubstringData s0_ = super.insert(new MutableTruffleStringFactory.SubstringNodeGen.SubstringData());
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.translateIndexNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CodePointIndexToRawNodeGen.create());
            s0_.copyToByteArrayNode_ = s0_.insertAccessor(TruffleString.CopyToByteArrayNode.create());
            VarHandle.storeStoreFence();
            this.substring_cache = s0_;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = MutableTruffleString.SubstringNode.substring(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               s0_.toIndexableNode_,
               s0_.getCodeRangeANode_,
               s0_.getCodePointLengthNode_,
               s0_.translateIndexNode_,
               s0_.copyToByteArrayNode_
            );
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

      public static MutableTruffleString.SubstringNode create() {
         return new MutableTruffleStringFactory.SubstringNodeGen();
      }

      public static MutableTruffleString.SubstringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(MutableTruffleString.SubstringNode.class)
      private static final class SubstringData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
         @Node.Child
         TStringInternalNodes.CodePointIndexToRawNode translateIndexNode_;
         @Node.Child
         TruffleString.CopyToByteArrayNode copyToByteArrayNode_;

         SubstringData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(MutableTruffleString.SubstringNode.class)
      @DenyReplace
      private static final class Uncached extends MutableTruffleString.SubstringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public MutableTruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
            return MutableTruffleString.SubstringNode.substring(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodesFactory.CodePointIndexToRawNodeGen.getUncached(),
               TruffleString.CopyToByteArrayNode.getUncached()
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

   @GeneratedBy(MutableTruffleString.SwitchEncodingNode.class)
   static final class SwitchEncodingNodeGen extends MutableTruffleString.SwitchEncodingNode {
      private static final MutableTruffleStringFactory.SwitchEncodingNodeGen.Uncached UNCACHED = new MutableTruffleStringFactory.SwitchEncodingNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.SwitchEncodingNode transcodeAndCopy_switchEncodingNode_;
      @Node.Child
      private MutableTruffleString.AsMutableTruffleStringNode transcodeAndCopy_asMutableTruffleStringNode_;

      private SwitchEncodingNodeGen() {
      }

      @Override
      public MutableTruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (arg0Value_.isCompatibleTo(arg1Value)) {
                  return MutableTruffleString.SwitchEncodingNode.compatibleMutable(arg0Value_, arg1Value);
               }
            }

            if ((state_0 & 2) != 0 && (!arg0Value.isCompatibleTo(arg1Value) || arg0Value.isImmutable())) {
               return MutableTruffleString.SwitchEncodingNode.transcodeAndCopy(
                  arg0Value, arg1Value, this.transcodeAndCopy_switchEncodingNode_, this.transcodeAndCopy_asMutableTruffleStringNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private MutableTruffleString executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (arg0Value_.isCompatibleTo(arg1Value)) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return MutableTruffleString.SwitchEncodingNode.compatibleMutable(arg0Value_, arg1Value);
               }
            }

            if (arg0Value.isCompatibleTo(arg1Value) && !arg0Value.isImmutable()) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            } else {
               this.transcodeAndCopy_switchEncodingNode_ = super.insert(TruffleString.SwitchEncodingNode.create());
               this.transcodeAndCopy_asMutableTruffleStringNode_ = super.insert(MutableTruffleString.AsMutableTruffleStringNode.create());
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return MutableTruffleString.SwitchEncodingNode.transcodeAndCopy(
                  arg0Value, arg1Value, this.transcodeAndCopy_switchEncodingNode_, this.transcodeAndCopy_asMutableTruffleStringNode_
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
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      public static MutableTruffleString.SwitchEncodingNode create() {
         return new MutableTruffleStringFactory.SwitchEncodingNodeGen();
      }

      public static MutableTruffleString.SwitchEncodingNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(MutableTruffleString.SwitchEncodingNode.class)
      @DenyReplace
      private static final class Uncached extends MutableTruffleString.SwitchEncodingNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public MutableTruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (arg0Value_.isCompatibleTo(arg1Value)) {
                  return MutableTruffleString.SwitchEncodingNode.compatibleMutable(arg0Value_, arg1Value);
               }
            }

            if (arg0Value.isCompatibleTo(arg1Value) && !arg0Value.isImmutable()) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            } else {
               return MutableTruffleString.SwitchEncodingNode.transcodeAndCopy(
                  arg0Value, arg1Value, TruffleString.SwitchEncodingNode.getUncached(), MutableTruffleString.AsMutableTruffleStringNode.getUncached()
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

   @GeneratedBy(MutableTruffleString.WriteByteNode.class)
   static final class WriteByteNodeGen extends MutableTruffleString.WriteByteNode {
      private static final MutableTruffleStringFactory.WriteByteNodeGen.Uncached UNCACHED = new MutableTruffleStringFactory.WriteByteNodeGen.Uncached();

      private WriteByteNodeGen() {
      }

      @Override
      public void execute(MutableTruffleString arg0Value, int arg1Value, byte arg2Value, TruffleString.Encoding arg3Value) {
         MutableTruffleString.WriteByteNode.writeByte(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      public static MutableTruffleString.WriteByteNode create() {
         return new MutableTruffleStringFactory.WriteByteNodeGen();
      }

      public static MutableTruffleString.WriteByteNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(MutableTruffleString.WriteByteNode.class)
      @DenyReplace
      private static final class Uncached extends MutableTruffleString.WriteByteNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public void execute(MutableTruffleString arg0Value, int arg1Value, byte arg2Value, TruffleString.Encoding arg3Value) {
            MutableTruffleString.WriteByteNode.writeByte(arg0Value, arg1Value, arg2Value, arg3Value);
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
