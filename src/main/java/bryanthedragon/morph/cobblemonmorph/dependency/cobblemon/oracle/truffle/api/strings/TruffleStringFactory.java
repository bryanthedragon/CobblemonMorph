package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.IntValueProfile;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TruffleString.class)
public final class TruffleStringFactory {
   @GeneratedBy(TruffleString.AsManagedNode.class)
   static final class AsManagedNodeGen extends TruffleString.AsManagedNode {
      private static final TruffleStringFactory.AsManagedNodeGen.Uncached UNCACHED = new TruffleStringFactory.AsManagedNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.AsManagedNodeGen.NativeOrMutableData nativeOrMutable_cache;

      private AsManagedNodeGen() {
      }

      @Override
      public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (!arg0Value_.isNative()) {
                  return TruffleString.AsManagedNode.managedImmutable(arg0Value_, arg1Value);
               }
            }

            if ((state_0 & 2) != 0) {
               TruffleStringFactory.AsManagedNodeGen.NativeOrMutableData s1_ = this.nativeOrMutable_cache;
               if (s1_ != null && (arg0Value.isNative() || arg0Value.isMutable())) {
                  return TruffleString.AsManagedNode.nativeOrMutable(
                     arg0Value, arg1Value, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.fromBufferWithStringCompactionNode_
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (!arg0Value_.isNative()) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return TruffleString.AsManagedNode.managedImmutable(arg0Value_, arg1Value);
               }
            }

            if (!arg0Value.isNative() && !arg0Value.isMutable()) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            } else {
               TruffleStringFactory.AsManagedNodeGen.NativeOrMutableData s1_ = super.insert(new TruffleStringFactory.AsManagedNodeGen.NativeOrMutableData());
               s1_.getCodePointLengthNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
               s1_.getCodeRangeNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
               s1_.fromBufferWithStringCompactionNode_ = s1_.insertAccessor(
                  TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.create()
               );
               VarHandle.storeStoreFence();
               this.nativeOrMutable_cache = s1_;
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TruffleString.AsManagedNode.nativeOrMutable(
                  arg0Value, arg1Value, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.fromBufferWithStringCompactionNode_
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

      public static TruffleString.AsManagedNode create() {
         return new TruffleStringFactory.AsManagedNodeGen();
      }

      public static TruffleString.AsManagedNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.AsManagedNode.class)
      private static final class NativeOrMutableData extends Node {
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode fromBufferWithStringCompactionNode_;

         NativeOrMutableData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.AsManagedNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.AsManagedNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (!arg0Value_.isNative()) {
                  return TruffleString.AsManagedNode.managedImmutable(arg0Value_, arg1Value);
               }
            }

            if (!arg0Value.isNative() && !arg0Value.isMutable()) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            } else {
               return TruffleString.AsManagedNode.nativeOrMutable(
                  arg0Value,
                  arg1Value,
                  TStringInternalNodes.GetCodePointLengthNode.getUncached(),
                  TStringInternalNodes.GetCodeRangeNode.getUncached(),
                  TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.getUncached()
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

   @GeneratedBy(TruffleString.AsTruffleStringNode.class)
   static final class AsTruffleStringNodeGen extends TruffleString.AsTruffleStringNode {
      private static final TruffleStringFactory.AsTruffleStringNodeGen.Uncached UNCACHED = new TruffleStringFactory.AsTruffleStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.AsTruffleStringNodeGen.FromMutableStringData fromMutableString_cache;

      private AsTruffleStringNodeGen() {
      }

      @Override
      public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               return TruffleString.AsTruffleStringNode.immutable(arg0Value_, arg1Value);
            }

            if ((state_0 & 2) != 0 && arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               TruffleStringFactory.AsTruffleStringNodeGen.FromMutableStringData s1_ = this.fromMutableString_cache;
               if (s1_ != null) {
                  return TruffleString.AsTruffleStringNode.fromMutableString(
                     arg0Value_, arg1Value, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.fromBufferWithStringCompactionNode_
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString s1_;
         try {
            int state_0 = this.state_0_;
            if (!(arg0Value instanceof TruffleString)) {
               if (!(arg0Value instanceof MutableTruffleString)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
               }

               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               TruffleStringFactory.AsTruffleStringNodeGen.FromMutableStringData s1_x = super.insert(
                  new TruffleStringFactory.AsTruffleStringNodeGen.FromMutableStringData()
               );
               s1_x.getCodePointLengthNode_ = s1_x.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
               s1_x.getCodeRangeNode_ = s1_x.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
               s1_x.fromBufferWithStringCompactionNode_ = s1_x.insertAccessor(
                  TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.create()
               );
               VarHandle.storeStoreFence();
               this.fromMutableString_cache = s1_x;
               int var13;
               this.state_0_ = var13 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TruffleString.AsTruffleStringNode.fromMutableString(
                  arg0Value_, arg1Value, s1_x.getCodePointLengthNode_, s1_x.getCodeRangeNode_, s1_x.fromBufferWithStringCompactionNode_
               );
            }

            TruffleString arg0Value_ = (TruffleString)arg0Value;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            s1_ = TruffleString.AsTruffleStringNode.immutable(arg0Value_, arg1Value);
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

      public static TruffleString.AsTruffleStringNode create() {
         return new TruffleStringFactory.AsTruffleStringNodeGen();
      }

      public static TruffleString.AsTruffleStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.AsTruffleStringNode.class)
      private static final class FromMutableStringData extends Node {
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode fromBufferWithStringCompactionNode_;

         FromMutableStringData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.AsTruffleStringNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.AsTruffleStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               return TruffleString.AsTruffleStringNode.immutable(arg0Value_, arg1Value);
            } else if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               return TruffleString.AsTruffleStringNode.fromMutableString(
                  arg0Value_,
                  arg1Value,
                  TStringInternalNodes.GetCodePointLengthNode.getUncached(),
                  TStringInternalNodes.GetCodeRangeNode.getUncached(),
                  TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.getUncached()
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

   @GeneratedBy(TruffleString.ByteIndexOfAnyByteNode.class)
   static final class ByteIndexOfAnyByteNodeGen extends TruffleString.ByteIndexOfAnyByteNode {
      private static final TruffleStringFactory.ByteIndexOfAnyByteNodeGen.Uncached UNCACHED = new TruffleStringFactory.ByteIndexOfAnyByteNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.ToIndexableNode toIndexableNode_;
      @Node.Child
      private TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;

      private ByteIndexOfAnyByteNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, byte[] arg3Value, TruffleString.Encoding arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.toIndexableNode_, this.getCodeRangeNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         }
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, byte[] arg3Value, TruffleString.Encoding arg4Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var9;
         try {
            int state_0 = this.state_0_;
            this.toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
            this.getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.toIndexableNode_, this.getCodeRangeNode_);
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

      public static TruffleString.ByteIndexOfAnyByteNode create() {
         return new TruffleStringFactory.ByteIndexOfAnyByteNodeGen();
      }

      public static TruffleString.ByteIndexOfAnyByteNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.ByteIndexOfAnyByteNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.ByteIndexOfAnyByteNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, byte[] arg3Value, TruffleString.Encoding arg4Value) {
            return this.indexOfRaw(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached()
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

   @GeneratedBy(TruffleString.ByteIndexOfCodePointNode.class)
   static final class ByteIndexOfCodePointNodeGen extends TruffleString.ByteIndexOfCodePointNode {
      private static final TruffleStringFactory.ByteIndexOfCodePointNodeGen.Uncached UNCACHED = new TruffleStringFactory.ByteIndexOfCodePointNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.ByteIndexOfCodePointNodeGen.IndexOfData indexOf_cache;

      private ByteIndexOfCodePointNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.ByteIndexOfCodePointNodeGen.IndexOfData s0_ = this.indexOf_cache;
            if (s0_ != null) {
               return TruffleString.ByteIndexOfCodePointNode.doIndexOf(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var10;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.ByteIndexOfCodePointNodeGen.IndexOfData s0_ = super.insert(new TruffleStringFactory.ByteIndexOfCodePointNodeGen.IndexOfData());
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.indexOfNode_ = s0_.insertAccessor(TStringInternalNodesFactory.IndexOfCodePointRawNodeGen.create());
            VarHandle.storeStoreFence();
            this.indexOf_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var10 = TruffleString.ByteIndexOfCodePointNode.doIndexOf(
               arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_
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

      public static TruffleString.ByteIndexOfCodePointNode create() {
         return new TruffleStringFactory.ByteIndexOfCodePointNodeGen();
      }

      public static TruffleString.ByteIndexOfCodePointNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.ByteIndexOfCodePointNode.class)
      private static final class IndexOfData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.IndexOfCodePointRawNode indexOfNode_;

         IndexOfData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.ByteIndexOfCodePointNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.ByteIndexOfCodePointNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            return TruffleString.ByteIndexOfCodePointNode.doIndexOf(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.IndexOfCodePointRawNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.ByteIndexOfStringNode.class)
   static final class ByteIndexOfStringNodeGen extends TruffleString.ByteIndexOfStringNode {
      private static final TruffleStringFactory.ByteIndexOfStringNodeGen.Uncached UNCACHED = new TruffleStringFactory.ByteIndexOfStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.ByteIndexOfStringNodeGen.IndexOfStringData indexOfString_cache;

      private ByteIndexOfStringNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, byte[] arg4Value, TruffleString.Encoding arg5Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.ByteIndexOfStringNodeGen.IndexOfStringData s0_ = this.indexOfString_cache;
            if (s0_ != null) {
               return TruffleString.ByteIndexOfStringNode.indexOfString(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  s0_.toIndexableNodeA_,
                  s0_.toIndexableNodeB_,
                  s0_.getCodeRangeANode_,
                  s0_.getCodeRangeBNode_,
                  s0_.indexOfStringNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, byte[] arg4Value, TruffleString.Encoding arg5Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var11;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.ByteIndexOfStringNodeGen.IndexOfStringData s0_ = super.insert(
               new TruffleStringFactory.ByteIndexOfStringNodeGen.IndexOfStringData()
            );
            s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.indexOfStringNode_ = s0_.insertAccessor(TStringInternalNodesFactory.IndexOfStringRawNodeGen.create());
            VarHandle.storeStoreFence();
            this.indexOfString_cache = s0_;
            int var15;
            this.state_0_ = var15 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var11 = TruffleString.ByteIndexOfStringNode.indexOfString(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               s0_.toIndexableNodeA_,
               s0_.toIndexableNodeB_,
               s0_.getCodeRangeANode_,
               s0_.getCodeRangeBNode_,
               s0_.indexOfStringNode_
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

      public static TruffleString.ByteIndexOfStringNode create() {
         return new TruffleStringFactory.ByteIndexOfStringNodeGen();
      }

      public static TruffleString.ByteIndexOfStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.ByteIndexOfStringNode.class)
      private static final class IndexOfStringData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeA_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeB_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode_;
         @Node.Child
         TStringInternalNodes.IndexOfStringRawNode indexOfStringNode_;

         IndexOfStringData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.ByteIndexOfStringNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.ByteIndexOfStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, byte[] arg4Value, TruffleString.Encoding arg5Value
         ) {
            return TruffleString.ByteIndexOfStringNode.indexOfString(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               TruffleString.ToIndexableNode.getUncached(),
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.IndexOfStringRawNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.ByteIndexToCodePointIndexNode.class)
   static final class ByteIndexToCodePointIndexNodeGen extends TruffleString.ByteIndexToCodePointIndexNode {
      private static final TruffleStringFactory.ByteIndexToCodePointIndexNodeGen.Uncached UNCACHED = new TruffleStringFactory.ByteIndexToCodePointIndexNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.ByteIndexToCodePointIndexNodeGen.TranslateData translate_cache;

      private ByteIndexToCodePointIndexNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.ByteIndexToCodePointIndexNodeGen.TranslateData s0_ = this.translate_cache;
            if (s0_ != null) {
               return TruffleString.ByteIndexToCodePointIndexNode.translate(
                  arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.rawIndexToCodePointIndexNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var9;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.ByteIndexToCodePointIndexNodeGen.TranslateData s0_ = super.insert(
               new TruffleStringFactory.ByteIndexToCodePointIndexNodeGen.TranslateData()
            );
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.rawIndexToCodePointIndexNode_ = s0_.insertAccessor(TStringInternalNodesFactory.RawIndexToCodePointIndexNodeGen.create());
            VarHandle.storeStoreFence();
            this.translate_cache = s0_;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = TruffleString.ByteIndexToCodePointIndexNode.translate(
               arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.rawIndexToCodePointIndexNode_
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

      public static TruffleString.ByteIndexToCodePointIndexNode create() {
         return new TruffleStringFactory.ByteIndexToCodePointIndexNodeGen();
      }

      public static TruffleString.ByteIndexToCodePointIndexNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.ByteIndexToCodePointIndexNode.class)
      private static final class TranslateData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.RawIndexToCodePointIndexNode rawIndexToCodePointIndexNode_;

         TranslateData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.ByteIndexToCodePointIndexNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.ByteIndexToCodePointIndexNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
            return TruffleString.ByteIndexToCodePointIndexNode.translate(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.RawIndexToCodePointIndexNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.ByteLengthOfCodePointNode.class)
   static final class ByteLengthOfCodePointNodeGen extends TruffleString.ByteLengthOfCodePointNode {
      private static final TruffleStringFactory.ByteLengthOfCodePointNodeGen.Uncached UNCACHED = new TruffleStringFactory.ByteLengthOfCodePointNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.ByteLengthOfCodePointNodeGen.TranslateData translate_cache;

      private ByteLengthOfCodePointNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.ByteLengthOfCodePointNodeGen.TranslateData s0_ = this.translate_cache;
            if (s0_ != null) {
               return TruffleString.ByteLengthOfCodePointNode.translate(
                  arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.byteLengthOfCodePointNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var9;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.ByteLengthOfCodePointNodeGen.TranslateData s0_ = super.insert(
               new TruffleStringFactory.ByteLengthOfCodePointNodeGen.TranslateData()
            );
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.byteLengthOfCodePointNode_ = s0_.insertAccessor(TStringInternalNodesFactory.ByteLengthOfCodePointNodeGen.create());
            VarHandle.storeStoreFence();
            this.translate_cache = s0_;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = TruffleString.ByteLengthOfCodePointNode.translate(
               arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.byteLengthOfCodePointNode_
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

      public static TruffleString.ByteLengthOfCodePointNode create() {
         return new TruffleStringFactory.ByteLengthOfCodePointNodeGen();
      }

      public static TruffleString.ByteLengthOfCodePointNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.ByteLengthOfCodePointNode.class)
      private static final class TranslateData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.ByteLengthOfCodePointNode byteLengthOfCodePointNode_;

         TranslateData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.ByteLengthOfCodePointNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.ByteLengthOfCodePointNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
            return TruffleString.ByteLengthOfCodePointNode.translate(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.ByteLengthOfCodePointNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.CharIndexOfAnyCharUTF16Node.class)
   static final class CharIndexOfAnyCharUTF16NodeGen extends TruffleString.CharIndexOfAnyCharUTF16Node {
      private static final TruffleStringFactory.CharIndexOfAnyCharUTF16NodeGen.Uncached UNCACHED = new TruffleStringFactory.CharIndexOfAnyCharUTF16NodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.CharIndexOfAnyCharUTF16NodeGen.IndexOfRawData indexOfRaw_cache;

      private CharIndexOfAnyCharUTF16NodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, char[] arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.CharIndexOfAnyCharUTF16NodeGen.IndexOfRawData s0_ = this.indexOfRaw_cache;
            if (s0_ != null) {
               return this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, char[] arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var9;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.CharIndexOfAnyCharUTF16NodeGen.IndexOfRawData s0_ = super.insert(
               new TruffleStringFactory.CharIndexOfAnyCharUTF16NodeGen.IndexOfRawData()
            );
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.indexOfNode_ = s0_.insertAccessor(TStringOpsNodesFactory.IndexOfAnyCharNodeGen.create());
            VarHandle.storeStoreFence();
            this.indexOfRaw_cache = s0_;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_);
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

      public static TruffleString.CharIndexOfAnyCharUTF16Node create() {
         return new TruffleStringFactory.CharIndexOfAnyCharUTF16NodeGen();
      }

      public static TruffleString.CharIndexOfAnyCharUTF16Node getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.CharIndexOfAnyCharUTF16Node.class)
      private static final class IndexOfRawData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringOpsNodes.IndexOfAnyCharNode indexOfNode_;

         IndexOfRawData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.CharIndexOfAnyCharUTF16Node.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.CharIndexOfAnyCharUTF16Node {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, char[] arg3Value) {
            return this.indexOfRaw(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringOpsNodesFactory.IndexOfAnyCharNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.CodePointAtByteIndexNode.class)
   static final class CodePointAtByteIndexNodeGen extends TruffleString.CodePointAtByteIndexNode {
      private static final TruffleStringFactory.CodePointAtByteIndexNodeGen.Uncached UNCACHED = new TruffleStringFactory.CodePointAtByteIndexNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.CodePointAtByteIndexNodeGen.ReadCodePointData readCodePoint_cache;

      private CodePointAtByteIndexNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.CodePointAtByteIndexNodeGen.ReadCodePointData s0_ = this.readCodePoint_cache;
            if (s0_ != null) {
               return TruffleString.CodePointAtByteIndexNode.readCodePoint(
                  arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.readCodePointNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var9;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.CodePointAtByteIndexNodeGen.ReadCodePointData s0_ = super.insert(
               new TruffleStringFactory.CodePointAtByteIndexNodeGen.ReadCodePointData()
            );
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.readCodePointNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CodePointAtRawNodeGen.create());
            VarHandle.storeStoreFence();
            this.readCodePoint_cache = s0_;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = TruffleString.CodePointAtByteIndexNode.readCodePoint(
               arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.readCodePointNode_
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

      public static TruffleString.CodePointAtByteIndexNode create() {
         return new TruffleStringFactory.CodePointAtByteIndexNodeGen();
      }

      public static TruffleString.CodePointAtByteIndexNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.CodePointAtByteIndexNode.class)
      private static final class ReadCodePointData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.CodePointAtRawNode readCodePointNode_;

         ReadCodePointData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.CodePointAtByteIndexNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.CodePointAtByteIndexNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
            return TruffleString.CodePointAtByteIndexNode.readCodePoint(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.CodePointAtRawNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.CodePointAtIndexNode.class)
   static final class CodePointAtIndexNodeGen extends TruffleString.CodePointAtIndexNode {
      private static final TruffleStringFactory.CodePointAtIndexNodeGen.Uncached UNCACHED = new TruffleStringFactory.CodePointAtIndexNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.CodePointAtIndexNodeGen.ReadCodePointData readCodePoint_cache;

      private CodePointAtIndexNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.CodePointAtIndexNodeGen.ReadCodePointData s0_ = this.readCodePoint_cache;
            if (s0_ != null) {
               return TruffleString.CodePointAtIndexNode.readCodePoint(
                  arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.readCodePointNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var9;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.CodePointAtIndexNodeGen.ReadCodePointData s0_ = super.insert(
               new TruffleStringFactory.CodePointAtIndexNodeGen.ReadCodePointData()
            );
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.readCodePointNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CodePointAtNodeGen.create());
            VarHandle.storeStoreFence();
            this.readCodePoint_cache = s0_;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = TruffleString.CodePointAtIndexNode.readCodePoint(
               arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.readCodePointNode_
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

      public static TruffleString.CodePointAtIndexNode create() {
         return new TruffleStringFactory.CodePointAtIndexNodeGen();
      }

      public static TruffleString.CodePointAtIndexNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.CodePointAtIndexNode.class)
      private static final class ReadCodePointData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.CodePointAtNode readCodePointNode_;

         ReadCodePointData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.CodePointAtIndexNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.CodePointAtIndexNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
            return TruffleString.CodePointAtIndexNode.readCodePoint(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.CodePointAtNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.CodePointIndexToByteIndexNode.class)
   static final class CodePointIndexToByteIndexNodeGen extends TruffleString.CodePointIndexToByteIndexNode {
      private static final TruffleStringFactory.CodePointIndexToByteIndexNodeGen.Uncached UNCACHED = new TruffleStringFactory.CodePointIndexToByteIndexNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.CodePointIndexToByteIndexNodeGen.TranslateData translate_cache;

      private CodePointIndexToByteIndexNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.CodePointIndexToByteIndexNodeGen.TranslateData s0_ = this.translate_cache;
            if (s0_ != null) {
               return TruffleString.CodePointIndexToByteIndexNode.translate(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  s0_.toIndexableNode_,
                  s0_.getCodePointLengthNode_,
                  s0_.getCodeRangeNode_,
                  s0_.codePointIndexToRawNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var9;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.CodePointIndexToByteIndexNodeGen.TranslateData s0_ = super.insert(
               new TruffleStringFactory.CodePointIndexToByteIndexNodeGen.TranslateData()
            );
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.codePointIndexToRawNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CodePointIndexToRawNodeGen.create());
            VarHandle.storeStoreFence();
            this.translate_cache = s0_;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = TruffleString.CodePointIndexToByteIndexNode.translate(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               s0_.toIndexableNode_,
               s0_.getCodePointLengthNode_,
               s0_.getCodeRangeNode_,
               s0_.codePointIndexToRawNode_
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

      public static TruffleString.CodePointIndexToByteIndexNode create() {
         return new TruffleStringFactory.CodePointIndexToByteIndexNodeGen();
      }

      public static TruffleString.CodePointIndexToByteIndexNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.CodePointIndexToByteIndexNode.class)
      private static final class TranslateData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.CodePointIndexToRawNode codePointIndexToRawNode_;

         TranslateData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.CodePointIndexToByteIndexNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.CodePointIndexToByteIndexNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
            return TruffleString.CodePointIndexToByteIndexNode.translate(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.CodePointIndexToRawNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.CodePointLengthNode.class)
   static final class CodePointLengthNodeGen extends TruffleString.CodePointLengthNode {
      private static final TruffleStringFactory.CodePointLengthNodeGen.Uncached UNCACHED = new TruffleStringFactory.CodePointLengthNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;

      private CodePointLengthNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return TruffleString.CodePointLengthNode.get(arg0Value, arg1Value, this.getCodePointLengthNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var6;
         try {
            int state_0 = this.state_0_;
            this.getCodePointLengthNode_ = super.insert(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = TruffleString.CodePointLengthNode.get(arg0Value, arg1Value, this.getCodePointLengthNode_);
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

      public static TruffleString.CodePointLengthNode create() {
         return new TruffleStringFactory.CodePointLengthNodeGen();
      }

      public static TruffleString.CodePointLengthNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.CodePointLengthNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.CodePointLengthNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            return TruffleString.CodePointLengthNode.get(arg0Value, arg1Value, TStringInternalNodes.GetCodePointLengthNode.getUncached());
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

   @GeneratedBy(TruffleString.CodeRangeEqualsNode.class)
   static final class CodeRangeEqualsNodeGen extends TruffleString.CodeRangeEqualsNode {
      private static final TruffleStringFactory.CodeRangeEqualsNodeGen.Uncached UNCACHED = new TruffleStringFactory.CodeRangeEqualsNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;

      private CodeRangeEqualsNodeGen() {
      }

      @Override
      public boolean execute(AbstractTruffleString arg0Value, TruffleString.CodeRange arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return TruffleString.CodeRangeEqualsNode.codeRangeEquals(arg0Value, arg1Value, this.getCodeRangeNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private boolean executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.CodeRange arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var6;
         try {
            int state_0 = this.state_0_;
            this.getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = TruffleString.CodeRangeEqualsNode.codeRangeEquals(arg0Value, arg1Value, this.getCodeRangeNode_);
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

      public static TruffleString.CodeRangeEqualsNode create() {
         return new TruffleStringFactory.CodeRangeEqualsNodeGen();
      }

      public static TruffleString.CodeRangeEqualsNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.CodeRangeEqualsNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.CodeRangeEqualsNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(AbstractTruffleString arg0Value, TruffleString.CodeRange arg1Value) {
            return TruffleString.CodeRangeEqualsNode.codeRangeEquals(arg0Value, arg1Value, TStringInternalNodes.GetCodeRangeNode.getUncached());
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

   @GeneratedBy(TruffleString.CompareBytesNode.class)
   static final class CompareBytesNodeGen extends TruffleString.CompareBytesNode {
      private static final TruffleStringFactory.CompareBytesNodeGen.Uncached UNCACHED = new TruffleStringFactory.CompareBytesNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.CompareBytesNodeGen.CompareData compare_cache;

      private CompareBytesNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.CompareBytesNodeGen.CompareData s0_ = this.compare_cache;
            if (s0_ != null) {
               return this.compare(
                  arg0Value, arg1Value, arg2Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var8;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.CompareBytesNodeGen.CompareData s0_ = super.insert(new TruffleStringFactory.CompareBytesNodeGen.CompareData());
            s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            VarHandle.storeStoreFence();
            this.compare_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.compare(arg0Value, arg1Value, arg2Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_);
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

      public static TruffleString.CompareBytesNode create() {
         return new TruffleStringFactory.CompareBytesNodeGen();
      }

      public static TruffleString.CompareBytesNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.CompareBytesNode.class)
      private static final class CompareData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeA_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeB_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode_;

         CompareData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.CompareBytesNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.CompareBytesNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
            return this.compare(
               arg0Value,
               arg1Value,
               arg2Value,
               TruffleString.ToIndexableNode.getUncached(),
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached()
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

   @GeneratedBy(TruffleString.CompareCharsUTF16Node.class)
   static final class CompareCharsUTF16NodeGen extends TruffleString.CompareCharsUTF16Node {
      private static final TruffleStringFactory.CompareCharsUTF16NodeGen.Uncached UNCACHED = new TruffleStringFactory.CompareCharsUTF16NodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.CompareCharsUTF16NodeGen.CompareData compare_cache;

      private CompareCharsUTF16NodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.CompareCharsUTF16NodeGen.CompareData s0_ = this.compare_cache;
            if (s0_ != null) {
               return this.compare(arg0Value, arg1Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var7;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.CompareCharsUTF16NodeGen.CompareData s0_ = super.insert(new TruffleStringFactory.CompareCharsUTF16NodeGen.CompareData());
            s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            VarHandle.storeStoreFence();
            this.compare_cache = s0_;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.compare(arg0Value, arg1Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_);
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleString.CompareCharsUTF16Node create() {
         return new TruffleStringFactory.CompareCharsUTF16NodeGen();
      }

      public static TruffleString.CompareCharsUTF16Node getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.CompareCharsUTF16Node.class)
      private static final class CompareData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeA_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeB_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode_;

         CompareData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.CompareCharsUTF16Node.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.CompareCharsUTF16Node {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value) {
            return this.compare(
               arg0Value,
               arg1Value,
               TruffleString.ToIndexableNode.getUncached(),
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached()
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

   @GeneratedBy(TruffleString.CompareIntsUTF32Node.class)
   static final class CompareIntsUTF32NodeGen extends TruffleString.CompareIntsUTF32Node {
      private static final TruffleStringFactory.CompareIntsUTF32NodeGen.Uncached UNCACHED = new TruffleStringFactory.CompareIntsUTF32NodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.CompareIntsUTF32NodeGen.CompareData compare_cache;

      private CompareIntsUTF32NodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.CompareIntsUTF32NodeGen.CompareData s0_ = this.compare_cache;
            if (s0_ != null) {
               return this.compare(arg0Value, arg1Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var7;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.CompareIntsUTF32NodeGen.CompareData s0_ = super.insert(new TruffleStringFactory.CompareIntsUTF32NodeGen.CompareData());
            s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            VarHandle.storeStoreFence();
            this.compare_cache = s0_;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.compare(arg0Value, arg1Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_);
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleString.CompareIntsUTF32Node create() {
         return new TruffleStringFactory.CompareIntsUTF32NodeGen();
      }

      public static TruffleString.CompareIntsUTF32Node getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.CompareIntsUTF32Node.class)
      private static final class CompareData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeA_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeB_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode_;

         CompareData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.CompareIntsUTF32Node.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.CompareIntsUTF32Node {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value) {
            return this.compare(
               arg0Value,
               arg1Value,
               TruffleString.ToIndexableNode.getUncached(),
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached()
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

   @GeneratedBy(TruffleString.ConcatNode.class)
   static final class ConcatNodeGen extends TruffleString.ConcatNode {
      private static final TruffleStringFactory.ConcatNodeGen.Uncached UNCACHED = new TruffleStringFactory.ConcatNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.ConcatNodeGen.AEmptyMutableData aEmptyMutable_cache;
      @Node.Child
      private TruffleStringFactory.ConcatNodeGen.BEmptyMutableData bEmptyMutable_cache;
      @Node.Child
      private TruffleStringFactory.ConcatNodeGen.ConcatData concat_cache;

      private ConcatNodeGen() {
      }

      @Override
      public TruffleString execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value, boolean arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 3) != 0) {
               if ((state_0 & 1) != 0 && arg1Value instanceof TruffleString) {
                  TruffleString arg1Value_ = (TruffleString)arg1Value;
                  if (TStringGuards.isEmpty(arg0Value)) {
                     return TruffleString.ConcatNode.aEmpty(arg0Value, arg1Value_, arg2Value, arg3Value);
                  }
               }

               if ((state_0 & 2) != 0 && arg1Value instanceof MutableTruffleString) {
                  MutableTruffleString arg1Value_ = (MutableTruffleString)arg1Value;
                  TruffleStringFactory.ConcatNodeGen.AEmptyMutableData s1_ = this.aEmptyMutable_cache;
                  if (s1_ != null && TStringGuards.isEmpty(arg0Value)) {
                     return TruffleString.ConcatNode.aEmptyMutable(
                        arg0Value,
                        arg1Value_,
                        arg2Value,
                        arg3Value,
                        s1_.getCodePointLengthNode_,
                        s1_.getCodeRangeNode_,
                        s1_.fromBufferWithStringCompactionNode_
                     );
                  }
               }
            }

            if ((state_0 & 28) != 0) {
               if ((state_0 & 4) != 0 && arg0Value instanceof TruffleString) {
                  TruffleString arg0Value_ = (TruffleString)arg0Value;
                  if (TStringGuards.isEmpty(arg1Value)) {
                     return TruffleString.ConcatNode.bEmpty(arg0Value_, arg1Value, arg2Value, arg3Value);
                  }
               }

               if ((state_0 & 8) != 0 && arg0Value instanceof MutableTruffleString) {
                  MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
                  TruffleStringFactory.ConcatNodeGen.BEmptyMutableData s3_ = this.bEmptyMutable_cache;
                  if (s3_ != null && TStringGuards.isEmpty(arg1Value)) {
                     return TruffleString.ConcatNode.bEmptyMutable(
                        arg0Value_,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        s3_.getCodePointLengthNode_,
                        s3_.getCodeRangeNode_,
                        s3_.fromBufferWithStringCompactionNode_
                     );
                  }
               }

               if ((state_0 & 16) != 0) {
                  TruffleStringFactory.ConcatNodeGen.ConcatData s4_ = this.concat_cache;
                  if (s4_ != null && !TStringGuards.isEmpty(arg0Value) && !TStringGuards.isEmpty(arg1Value)) {
                     return TruffleString.ConcatNode.doConcat(
                        arg0Value,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        s4_.getCodeRangeANode_,
                        s4_.getCodeRangeBNode_,
                        s4_.getStrideNode_,
                        s4_.concatEagerNode_,
                        s4_.asTruffleStringANode_,
                        s4_.asTruffleStringBNode_,
                        s4_.outOfMemoryProfile_,
                        s4_.lazyProfile_
                     );
                  }
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private TruffleString executeAndSpecialize(
         AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value, boolean arg3Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arg1Value instanceof TruffleString) {
               TruffleString arg1Value_ = (TruffleString)arg1Value;
               if (TStringGuards.isEmpty(arg0Value)) {
                  int var18;
                  this.state_0_ = var18 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return TruffleString.ConcatNode.aEmpty(arg0Value, arg1Value_, arg2Value, arg3Value);
               }
            }

            if (arg1Value instanceof MutableTruffleString) {
               MutableTruffleString arg1Value_ = (MutableTruffleString)arg1Value;
               if (TStringGuards.isEmpty(arg0Value)) {
                  TruffleStringFactory.ConcatNodeGen.AEmptyMutableData s1_ = super.insert(new TruffleStringFactory.ConcatNodeGen.AEmptyMutableData());
                  s1_.getCodePointLengthNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                  s1_.getCodeRangeNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                  s1_.fromBufferWithStringCompactionNode_ = s1_.insertAccessor(
                     TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.create()
                  );
                  VarHandle.storeStoreFence();
                  this.aEmptyMutable_cache = s1_;
                  int var17;
                  this.state_0_ = var17 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return TruffleString.ConcatNode.aEmptyMutable(
                     arg0Value, arg1Value_, arg2Value, arg3Value, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.fromBufferWithStringCompactionNode_
                  );
               }
            }

            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (TStringGuards.isEmpty(arg1Value)) {
                  int var16;
                  this.state_0_ = var16 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return TruffleString.ConcatNode.bEmpty(arg0Value_, arg1Value, arg2Value, arg3Value);
               }
            }

            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (TStringGuards.isEmpty(arg1Value)) {
                  TruffleStringFactory.ConcatNodeGen.BEmptyMutableData s3_ = super.insert(new TruffleStringFactory.ConcatNodeGen.BEmptyMutableData());
                  s3_.getCodePointLengthNode_ = s3_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                  s3_.getCodeRangeNode_ = s3_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                  s3_.fromBufferWithStringCompactionNode_ = s3_.insertAccessor(
                     TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.create()
                  );
                  VarHandle.storeStoreFence();
                  this.bEmptyMutable_cache = s3_;
                  int var15;
                  this.state_0_ = var15 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return TruffleString.ConcatNode.bEmptyMutable(
                     arg0Value_, arg1Value, arg2Value, arg3Value, s3_.getCodePointLengthNode_, s3_.getCodeRangeNode_, s3_.fromBufferWithStringCompactionNode_
                  );
               }
            }

            if (TStringGuards.isEmpty(arg0Value) || TStringGuards.isEmpty(arg1Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
            } else {
               TruffleStringFactory.ConcatNodeGen.ConcatData s4_ = super.insert(new TruffleStringFactory.ConcatNodeGen.ConcatData());
               s4_.getCodeRangeANode_ = s4_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
               s4_.getCodeRangeBNode_ = s4_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
               s4_.getStrideNode_ = s4_.insertAccessor(TStringInternalNodesFactory.StrideFromCodeRangeNodeGen.create());
               s4_.concatEagerNode_ = s4_.insertAccessor(TStringInternalNodesFactory.ConcatEagerNodeGen.create());
               s4_.asTruffleStringANode_ = s4_.insertAccessor(TruffleString.AsTruffleStringNode.create());
               s4_.asTruffleStringBNode_ = s4_.insertAccessor(TruffleString.AsTruffleStringNode.create());
               s4_.outOfMemoryProfile_ = BranchProfile.create();
               s4_.lazyProfile_ = ConditionProfile.create();
               VarHandle.storeStoreFence();
               this.concat_cache = s4_;
               int var14;
               this.state_0_ = var14 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return TruffleString.ConcatNode.doConcat(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  s4_.getCodeRangeANode_,
                  s4_.getCodeRangeBNode_,
                  s4_.getStrideNode_,
                  s4_.concatEagerNode_,
                  s4_.asTruffleStringANode_,
                  s4_.asTruffleStringBNode_,
                  s4_.outOfMemoryProfile_,
                  s4_.lazyProfile_
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

      public static TruffleString.ConcatNode create() {
         return new TruffleStringFactory.ConcatNodeGen();
      }

      public static TruffleString.ConcatNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.ConcatNode.class)
      private static final class AEmptyMutableData extends Node {
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode fromBufferWithStringCompactionNode_;

         AEmptyMutableData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.ConcatNode.class)
      private static final class BEmptyMutableData extends Node {
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode fromBufferWithStringCompactionNode_;

         BEmptyMutableData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.ConcatNode.class)
      private static final class ConcatData extends Node {
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode_;
         @Node.Child
         TStringInternalNodes.StrideFromCodeRangeNode getStrideNode_;
         @Node.Child
         TStringInternalNodes.ConcatEagerNode concatEagerNode_;
         @Node.Child
         TruffleString.AsTruffleStringNode asTruffleStringANode_;
         @Node.Child
         TruffleString.AsTruffleStringNode asTruffleStringBNode_;
         @CompilerDirectives.CompilationFinal
         BranchProfile outOfMemoryProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile lazyProfile_;

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

      @GeneratedBy(TruffleString.ConcatNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.ConcatNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value, boolean arg3Value) {
            if (arg1Value instanceof TruffleString) {
               TruffleString arg1Value_ = (TruffleString)arg1Value;
               if (TStringGuards.isEmpty(arg0Value)) {
                  return TruffleString.ConcatNode.aEmpty(arg0Value, arg1Value_, arg2Value, arg3Value);
               }
            }

            if (arg1Value instanceof MutableTruffleString) {
               MutableTruffleString arg1Value_ = (MutableTruffleString)arg1Value;
               if (TStringGuards.isEmpty(arg0Value)) {
                  return TruffleString.ConcatNode.aEmptyMutable(
                     arg0Value,
                     arg1Value_,
                     arg2Value,
                     arg3Value,
                     TStringInternalNodes.GetCodePointLengthNode.getUncached(),
                     TStringInternalNodes.GetCodeRangeNode.getUncached(),
                     TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.getUncached()
                  );
               }
            }

            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (TStringGuards.isEmpty(arg1Value)) {
                  return TruffleString.ConcatNode.bEmpty(arg0Value_, arg1Value, arg2Value, arg3Value);
               }
            }

            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (TStringGuards.isEmpty(arg1Value)) {
                  return TruffleString.ConcatNode.bEmptyMutable(
                     arg0Value_,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     TStringInternalNodes.GetCodePointLengthNode.getUncached(),
                     TStringInternalNodes.GetCodeRangeNode.getUncached(),
                     TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.getUncached()
                  );
               }
            }

            if (!TStringGuards.isEmpty(arg0Value) && !TStringGuards.isEmpty(arg1Value)) {
               return TruffleString.ConcatNode.doConcat(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  TStringInternalNodes.GetCodeRangeNode.getUncached(),
                  TStringInternalNodes.GetCodeRangeNode.getUncached(),
                  TStringInternalNodesFactory.StrideFromCodeRangeNodeGen.getUncached(),
                  TStringInternalNodesFactory.ConcatEagerNodeGen.getUncached(),
                  TruffleString.AsTruffleStringNode.getUncached(),
                  TruffleString.AsTruffleStringNode.getUncached(),
                  BranchProfile.getUncached(),
                  ConditionProfile.getUncached()
               );
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

   @GeneratedBy(TruffleString.CopyToByteArrayNode.class)
   static final class CopyToByteArrayNodeGen extends TruffleString.CopyToByteArrayNode {
      private static final TruffleStringFactory.CopyToByteArrayNodeGen.Uncached UNCACHED = new TruffleStringFactory.CopyToByteArrayNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.CopyToByteArrayNodeGen.CopyData copy_cache;

      private CopyToByteArrayNodeGen() {
      }

      @Override
      public void execute(AbstractTruffleString arg0Value, int arg1Value, byte[] arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.CopyToByteArrayNodeGen.CopyData s0_ = this.copy_cache;
            if (s0_ != null) {
               this.doCopy(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  s0_.toIndexableNode_,
                  s0_.utf16Profile_,
                  s0_.utf16S0Profile_,
                  s0_.utf32Profile_,
                  s0_.utf32S0Profile_,
                  s0_.utf32S1Profile_
               );
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private void executeAndSpecialize(
         AbstractTruffleString arg0Value, int arg1Value, byte[] arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.CopyToByteArrayNodeGen.CopyData s0_ = super.insert(new TruffleStringFactory.CopyToByteArrayNodeGen.CopyData());
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.utf16Profile_ = ConditionProfile.create();
            s0_.utf16S0Profile_ = ConditionProfile.create();
            s0_.utf32Profile_ = ConditionProfile.create();
            s0_.utf32S0Profile_ = ConditionProfile.create();
            s0_.utf32S1Profile_ = ConditionProfile.create();
            VarHandle.storeStoreFence();
            this.copy_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            this.doCopy(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               s0_.toIndexableNode_,
               s0_.utf16Profile_,
               s0_.utf16S0Profile_,
               s0_.utf32Profile_,
               s0_.utf32S0Profile_,
               s0_.utf32S1Profile_
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

      public static TruffleString.CopyToByteArrayNode create() {
         return new TruffleStringFactory.CopyToByteArrayNodeGen();
      }

      public static TruffleString.CopyToByteArrayNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.CopyToByteArrayNode.class)
      private static final class CopyData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
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

         CopyData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.CopyToByteArrayNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.CopyToByteArrayNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public void execute(AbstractTruffleString arg0Value, int arg1Value, byte[] arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value) {
            this.doCopy(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               TruffleString.ToIndexableNode.getUncached(),
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

   @GeneratedBy(TruffleString.CopyToNativeMemoryNode.class)
   static final class CopyToNativeMemoryNodeGen extends TruffleString.CopyToNativeMemoryNode {
      private static final TruffleStringFactory.CopyToNativeMemoryNodeGen.Uncached UNCACHED = new TruffleStringFactory.CopyToNativeMemoryNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.CopyToNativeMemoryNodeGen.CopyData copy_cache;

      private CopyToNativeMemoryNodeGen() {
      }

      @Override
      public void execute(AbstractTruffleString arg0Value, int arg1Value, Object arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.CopyToNativeMemoryNodeGen.CopyData s0_ = this.copy_cache;
            if (s0_ != null) {
               this.doCopy(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  s0_.interopLibrary_,
                  s0_.toIndexableNode_,
                  s0_.utf16Profile_,
                  s0_.utf16S0Profile_,
                  s0_.utf32Profile_,
                  s0_.utf32S0Profile_,
                  s0_.utf32S1Profile_
               );
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private void executeAndSpecialize(
         AbstractTruffleString arg0Value, int arg1Value, Object arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.CopyToNativeMemoryNodeGen.CopyData s0_ = super.insert(new TruffleStringFactory.CopyToNativeMemoryNodeGen.CopyData());
            s0_.interopLibrary_ = s0_.insertAccessor(TStringAccessor.createInteropLibrary());
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.utf16Profile_ = ConditionProfile.create();
            s0_.utf16S0Profile_ = ConditionProfile.create();
            s0_.utf32Profile_ = ConditionProfile.create();
            s0_.utf32S0Profile_ = ConditionProfile.create();
            s0_.utf32S1Profile_ = ConditionProfile.create();
            VarHandle.storeStoreFence();
            this.copy_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            this.doCopy(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               s0_.interopLibrary_,
               s0_.toIndexableNode_,
               s0_.utf16Profile_,
               s0_.utf16S0Profile_,
               s0_.utf32Profile_,
               s0_.utf32S0Profile_,
               s0_.utf32S1Profile_
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

      public static TruffleString.CopyToNativeMemoryNode create() {
         return new TruffleStringFactory.CopyToNativeMemoryNodeGen();
      }

      public static TruffleString.CopyToNativeMemoryNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.CopyToNativeMemoryNode.class)
      private static final class CopyData extends Node {
         @Node.Child
         Node interopLibrary_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
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

         CopyData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.CopyToNativeMemoryNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.CopyToNativeMemoryNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public void execute(AbstractTruffleString arg0Value, int arg1Value, Object arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value) {
            this.doCopy(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               TStringAccessor.getUncachedInteropLibrary(),
               TruffleString.ToIndexableNode.getUncached(),
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

   @GeneratedBy(TruffleString.CreateBackwardCodePointIteratorNode.class)
   static final class CreateBackwardCodePointIteratorNodeGen extends TruffleString.CreateBackwardCodePointIteratorNode {
      private static final TruffleStringFactory.CreateBackwardCodePointIteratorNodeGen.Uncached UNCACHED = new TruffleStringFactory.CreateBackwardCodePointIteratorNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.ToIndexableNode toIndexableNode_;
      @Node.Child
      private TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;

      private CreateBackwardCodePointIteratorNodeGen() {
      }

      @Override
      public TruffleStringIterator execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.ErrorHandling arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return TruffleString.CreateBackwardCodePointIteratorNode.createIterator(
               arg0Value, arg1Value, arg2Value, this.toIndexableNode_, this.getCodeRangeANode_
            );
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
         }
      }

      private TruffleStringIterator executeAndSpecialize(
         AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.ErrorHandling arg2Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleStringIterator var7;
         try {
            int state_0 = this.state_0_;
            this.toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
            this.getCodeRangeANode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = TruffleString.CreateBackwardCodePointIteratorNode.createIterator(
               arg0Value, arg1Value, arg2Value, this.toIndexableNode_, this.getCodeRangeANode_
            );
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleString.CreateBackwardCodePointIteratorNode create() {
         return new TruffleStringFactory.CreateBackwardCodePointIteratorNodeGen();
      }

      public static TruffleString.CreateBackwardCodePointIteratorNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.CreateBackwardCodePointIteratorNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.CreateBackwardCodePointIteratorNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleStringIterator execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.ErrorHandling arg2Value) {
            return TruffleString.CreateBackwardCodePointIteratorNode.createIterator(
               arg0Value, arg1Value, arg2Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached()
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

   @GeneratedBy(TruffleString.CreateCodePointIteratorNode.class)
   static final class CreateCodePointIteratorNodeGen extends TruffleString.CreateCodePointIteratorNode {
      private static final TruffleStringFactory.CreateCodePointIteratorNodeGen.Uncached UNCACHED = new TruffleStringFactory.CreateCodePointIteratorNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.ToIndexableNode toIndexableNode_;
      @Node.Child
      private TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;

      private CreateCodePointIteratorNodeGen() {
      }

      @Override
      public TruffleStringIterator execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.ErrorHandling arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return TruffleString.CreateCodePointIteratorNode.createIterator(arg0Value, arg1Value, arg2Value, this.toIndexableNode_, this.getCodeRangeANode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
         }
      }

      private TruffleStringIterator executeAndSpecialize(
         AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.ErrorHandling arg2Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleStringIterator var7;
         try {
            int state_0 = this.state_0_;
            this.toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
            this.getCodeRangeANode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = TruffleString.CreateCodePointIteratorNode.createIterator(arg0Value, arg1Value, arg2Value, this.toIndexableNode_, this.getCodeRangeANode_);
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleString.CreateCodePointIteratorNode create() {
         return new TruffleStringFactory.CreateCodePointIteratorNodeGen();
      }

      public static TruffleString.CreateCodePointIteratorNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.CreateCodePointIteratorNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.CreateCodePointIteratorNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleStringIterator execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.ErrorHandling arg2Value) {
            return TruffleString.CreateCodePointIteratorNode.createIterator(
               arg0Value, arg1Value, arg2Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached()
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

   @GeneratedBy(TruffleString.EqualNode.class)
   static final class EqualNodeGen extends TruffleString.EqualNode {
      private static final TruffleStringFactory.EqualNodeGen.Uncached UNCACHED = new TruffleStringFactory.EqualNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.EqualNodeGen.CheckData check_cache;

      private EqualNodeGen() {
      }

      @Override
      public boolean execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.identical(arg0Value, arg1Value)) {
               return TruffleString.EqualNode.sameObject(arg0Value, arg1Value, arg2Value);
            }

            if ((state_0 & 2) != 0) {
               TruffleStringFactory.EqualNodeGen.CheckData s1_ = this.check_cache;
               if (s1_ != null && !TStringGuards.identical(arg0Value, arg1Value)) {
                  return this.check(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     s1_.toIndexableNodeA_,
                     s1_.toIndexableNodeB_,
                     s1_.getCodeRangeANode_,
                     s1_.getCodeRangeBNode_,
                     s1_.lengthAndCodeRangeCheckProfile_,
                     s1_.compareHashProfile_,
                     s1_.checkFirstByteProfile_
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private boolean executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean s1_;
         try {
            int state_0 = this.state_0_;
            if (!TStringGuards.identical(arg0Value, arg1Value)) {
               if (TStringGuards.identical(arg0Value, arg1Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
               }

               TruffleStringFactory.EqualNodeGen.CheckData s1_x = super.insert(new TruffleStringFactory.EqualNodeGen.CheckData());
               s1_x.toIndexableNodeA_ = s1_x.insertAccessor(TruffleString.ToIndexableNode.create());
               s1_x.toIndexableNodeB_ = s1_x.insertAccessor(TruffleString.ToIndexableNode.create());
               s1_x.getCodeRangeANode_ = s1_x.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
               s1_x.getCodeRangeBNode_ = s1_x.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
               s1_x.lengthAndCodeRangeCheckProfile_ = ConditionProfile.create();
               s1_x.compareHashProfile_ = BranchProfile.create();
               s1_x.checkFirstByteProfile_ = ConditionProfile.create();
               VarHandle.storeStoreFence();
               this.check_cache = s1_x;
               int var13;
               this.state_0_ = var13 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.check(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  s1_x.toIndexableNodeA_,
                  s1_x.toIndexableNodeB_,
                  s1_x.getCodeRangeANode_,
                  s1_x.getCodeRangeBNode_,
                  s1_x.lengthAndCodeRangeCheckProfile_,
                  s1_x.compareHashProfile_,
                  s1_x.checkFirstByteProfile_
               );
            }

            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            s1_ = TruffleString.EqualNode.sameObject(arg0Value, arg1Value, arg2Value);
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

      public static TruffleString.EqualNode create() {
         return new TruffleStringFactory.EqualNodeGen();
      }

      public static TruffleString.EqualNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.EqualNode.class)
      private static final class CheckData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeA_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeB_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile lengthAndCodeRangeCheckProfile_;
         @CompilerDirectives.CompilationFinal
         BranchProfile compareHashProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile checkFirstByteProfile_;

         CheckData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.EqualNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.EqualNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
            if (TStringGuards.identical(arg0Value, arg1Value)) {
               return TruffleString.EqualNode.sameObject(arg0Value, arg1Value, arg2Value);
            } else if (!TStringGuards.identical(arg0Value, arg1Value)) {
               return this.check(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  TruffleString.ToIndexableNode.getUncached(),
                  TruffleString.ToIndexableNode.getUncached(),
                  TStringInternalNodes.GetCodeRangeNode.getUncached(),
                  TStringInternalNodes.GetCodeRangeNode.getUncached(),
                  ConditionProfile.getUncached(),
                  BranchProfile.getUncached(),
                  ConditionProfile.getUncached()
               );
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

   @GeneratedBy(TruffleString.ForceEncodingNode.class)
   static final class ForceEncodingNodeGen extends TruffleString.ForceEncodingNode {
      private static final TruffleStringFactory.ForceEncodingNodeGen.Uncached UNCACHED = new TruffleStringFactory.ForceEncodingNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.AsTruffleStringNode compatibleMutable_asTruffleStringNode_;
      @Node.Child
      private TruffleStringFactory.ForceEncodingNodeGen.ReinterpretData reinterpret_cache;

      private ForceEncodingNodeGen() {
      }

      @Override
      public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.Encoding arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value_, arg1Value, arg2Value)) {
                  return TruffleString.ForceEncodingNode.compatibleImmutable(arg0Value_, arg1Value, arg2Value);
               }
            }

            if ((state_0 & 2) != 0 && arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value_, arg1Value, arg2Value)) {
                  return TruffleString.ForceEncodingNode.compatibleMutable(arg0Value_, arg1Value, arg2Value, this.compatibleMutable_asTruffleStringNode_);
               }
            }

            if ((state_0 & 4) != 0) {
               TruffleStringFactory.ForceEncodingNodeGen.ReinterpretData s2_ = this.reinterpret_cache;
               if (s2_ != null && !TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value, arg1Value, arg2Value)) {
                  return TruffleString.ForceEncodingNode.reinterpret(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     s2_.toIndexableNode_,
                     s2_.managedProfile_,
                     s2_.inflateProfile_,
                     s2_.copyToByteArrayNode_,
                     s2_.fromBufferWithStringCompactionNode_,
                     s2_.fromNativePointerNode_
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.Encoding arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value_, arg1Value, arg2Value)) {
                  int var14;
                  this.state_0_ = var14 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return TruffleString.ForceEncodingNode.compatibleImmutable(arg0Value_, arg1Value, arg2Value);
               }
            }

            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value_, arg1Value, arg2Value)) {
                  this.compatibleMutable_asTruffleStringNode_ = super.insert(TruffleString.AsTruffleStringNode.create());
                  int var13;
                  this.state_0_ = var13 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return TruffleString.ForceEncodingNode.compatibleMutable(arg0Value_, arg1Value, arg2Value, this.compatibleMutable_asTruffleStringNode_);
               }
            }

            if (TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value, arg1Value, arg2Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            } else {
               TruffleStringFactory.ForceEncodingNodeGen.ReinterpretData s2_ = super.insert(new TruffleStringFactory.ForceEncodingNodeGen.ReinterpretData());
               s2_.toIndexableNode_ = s2_.insertAccessor(TruffleString.ToIndexableNode.create());
               s2_.managedProfile_ = ConditionProfile.create();
               s2_.inflateProfile_ = ConditionProfile.create();
               s2_.copyToByteArrayNode_ = s2_.insertAccessor(TruffleString.CopyToByteArrayNode.create());
               s2_.fromBufferWithStringCompactionNode_ = s2_.insertAccessor(TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.create());
               s2_.fromNativePointerNode_ = s2_.insertAccessor(TStringInternalNodesFactory.FromNativePointerNodeGen.create());
               VarHandle.storeStoreFence();
               this.reinterpret_cache = s2_;
               int var12;
               this.state_0_ = var12 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return TruffleString.ForceEncodingNode.reinterpret(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  s2_.toIndexableNode_,
                  s2_.managedProfile_,
                  s2_.inflateProfile_,
                  s2_.copyToByteArrayNode_,
                  s2_.fromBufferWithStringCompactionNode_,
                  s2_.fromNativePointerNode_
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

      public static TruffleString.ForceEncodingNode create() {
         return new TruffleStringFactory.ForceEncodingNodeGen();
      }

      public static TruffleString.ForceEncodingNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.ForceEncodingNode.class)
      private static final class ReinterpretData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile managedProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile inflateProfile_;
         @Node.Child
         TruffleString.CopyToByteArrayNode copyToByteArrayNode_;
         @Node.Child
         TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode_;
         @Node.Child
         TStringInternalNodes.FromNativePointerNode fromNativePointerNode_;

         ReinterpretData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.ForceEncodingNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.ForceEncodingNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.Encoding arg2Value) {
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value_, arg1Value, arg2Value)) {
                  return TruffleString.ForceEncodingNode.compatibleImmutable(arg0Value_, arg1Value, arg2Value);
               }
            }

            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value_, arg1Value, arg2Value)) {
                  return TruffleString.ForceEncodingNode.compatibleMutable(arg0Value_, arg1Value, arg2Value, TruffleString.AsTruffleStringNode.getUncached());
               }
            }

            if (!TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value, arg1Value, arg2Value)) {
               return TruffleString.ForceEncodingNode.reinterpret(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  TruffleString.ToIndexableNode.getUncached(),
                  ConditionProfile.getUncached(),
                  ConditionProfile.getUncached(),
                  TruffleString.CopyToByteArrayNode.getUncached(),
                  TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.getUncached(),
                  TStringInternalNodesFactory.FromNativePointerNodeGen.getUncached()
               );
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

   @GeneratedBy(TruffleString.FromByteArrayNode.class)
   static final class FromByteArrayNodeGen extends TruffleString.FromByteArrayNode {
      private static final TruffleStringFactory.FromByteArrayNodeGen.Uncached UNCACHED = new TruffleStringFactory.FromByteArrayNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode_;

      private FromByteArrayNodeGen() {
      }

      @Override
      public TruffleString execute(byte[] arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return TruffleString.FromByteArrayNode.fromByteArray(
               arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.fromBufferWithStringCompactionNode_
            );
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
         }
      }

      private TruffleString executeAndSpecialize(byte[] arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var9;
         try {
            int state_0 = this.state_0_;
            this.fromBufferWithStringCompactionNode_ = super.insert(TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.create());
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = TruffleString.FromByteArrayNode.fromByteArray(
               arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.fromBufferWithStringCompactionNode_
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

      public static TruffleString.FromByteArrayNode create() {
         return new TruffleStringFactory.FromByteArrayNodeGen();
      }

      public static TruffleString.FromByteArrayNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.FromByteArrayNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.FromByteArrayNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(byte[] arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            return TruffleString.FromByteArrayNode.fromByteArray(
               arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.FromCharArrayUTF16Node.class)
   static final class FromCharArrayUTF16NodeGen extends TruffleString.FromCharArrayUTF16Node {
      private static final TruffleStringFactory.FromCharArrayUTF16NodeGen.Uncached UNCACHED = new TruffleStringFactory.FromCharArrayUTF16NodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf16CompactProfile_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile outOfMemoryProfile_;

      private FromCharArrayUTF16NodeGen() {
      }

      @Override
      public TruffleString execute(char[] arg0Value, int arg1Value, int arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return this.doNonEmpty(arg0Value, arg1Value, arg2Value, this.utf16CompactProfile_, this.outOfMemoryProfile_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
         }
      }

      private TruffleString executeAndSpecialize(char[] arg0Value, int arg1Value, int arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var7;
         try {
            int state_0 = this.state_0_;
            this.utf16CompactProfile_ = ConditionProfile.create();
            this.outOfMemoryProfile_ = BranchProfile.create();
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.doNonEmpty(arg0Value, arg1Value, arg2Value, this.utf16CompactProfile_, this.outOfMemoryProfile_);
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleString.FromCharArrayUTF16Node create() {
         return new TruffleStringFactory.FromCharArrayUTF16NodeGen();
      }

      public static TruffleString.FromCharArrayUTF16Node getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.FromCharArrayUTF16Node.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.FromCharArrayUTF16Node {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(char[] arg0Value, int arg1Value, int arg2Value) {
            return this.doNonEmpty(arg0Value, arg1Value, arg2Value, ConditionProfile.getUncached(), BranchProfile.getUncached());
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

   @GeneratedBy(TruffleString.FromCodePointNode.class)
   static final class FromCodePointNodeGen extends TruffleString.FromCodePointNode {
      private static final TruffleStringFactory.FromCodePointNodeGen.Uncached UNCACHED = new TruffleStringFactory.FromCodePointNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TruffleStringFactory.FromCodePointNodeGen.FromCodePointData fromCodePoint_cache;

      private FromCodePointNodeGen() {
      }

      @Override
      public TruffleString execute(int arg0Value, TruffleString.Encoding arg1Value, boolean arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.FromCodePointNodeGen.FromCodePointData s0_ = this.fromCodePoint_cache;
            if (s0_ != null) {
               return TruffleString.FromCodePointNode.fromCodePoint(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  s0_.bytesProfile_,
                  s0_.utf8Profile_,
                  s0_.utf16Profile_,
                  s0_.utf32Profile_,
                  s0_.exoticProfile_,
                  s0_.bmpProfile_,
                  s0_.invalidCodePoint_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private TruffleString executeAndSpecialize(int arg0Value, TruffleString.Encoding arg1Value, boolean arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var8;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.FromCodePointNodeGen.FromCodePointData s0_ = new TruffleStringFactory.FromCodePointNodeGen.FromCodePointData();
            s0_.bytesProfile_ = ConditionProfile.create();
            s0_.utf8Profile_ = ConditionProfile.create();
            s0_.utf16Profile_ = ConditionProfile.create();
            s0_.utf32Profile_ = ConditionProfile.create();
            s0_.exoticProfile_ = ConditionProfile.create();
            s0_.bmpProfile_ = ConditionProfile.create();
            s0_.invalidCodePoint_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.fromCodePoint_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = TruffleString.FromCodePointNode.fromCodePoint(
               arg0Value,
               arg1Value,
               arg2Value,
               s0_.bytesProfile_,
               s0_.utf8Profile_,
               s0_.utf16Profile_,
               s0_.utf32Profile_,
               s0_.exoticProfile_,
               s0_.bmpProfile_,
               s0_.invalidCodePoint_
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

      public static TruffleString.FromCodePointNode create() {
         return new TruffleStringFactory.FromCodePointNodeGen();
      }

      public static TruffleString.FromCodePointNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.FromCodePointNode.class)
      private static final class FromCodePointData {
         @CompilerDirectives.CompilationFinal
         ConditionProfile bytesProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf8Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf16Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf32Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile exoticProfile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile bmpProfile_;
         @CompilerDirectives.CompilationFinal
         BranchProfile invalidCodePoint_;

         FromCodePointData() {
         }
      }

      @GeneratedBy(TruffleString.FromCodePointNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.FromCodePointNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(int arg0Value, TruffleString.Encoding arg1Value, boolean arg2Value) {
            return TruffleString.FromCodePointNode.fromCodePoint(
               arg0Value,
               arg1Value,
               arg2Value,
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
               ConditionProfile.getUncached(),
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

   @GeneratedBy(TruffleString.FromIntArrayUTF32Node.class)
   static final class FromIntArrayUTF32NodeGen extends TruffleString.FromIntArrayUTF32Node {
      private static final TruffleStringFactory.FromIntArrayUTF32NodeGen.Uncached UNCACHED = new TruffleStringFactory.FromIntArrayUTF32NodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private TruffleStringFactory.FromIntArrayUTF32NodeGen.NonEmptyData nonEmpty_cache;

      private FromIntArrayUTF32NodeGen() {
      }

      @Override
      public TruffleString execute(int[] arg0Value, int arg1Value, int arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.FromIntArrayUTF32NodeGen.NonEmptyData s0_ = this.nonEmpty_cache;
            if (s0_ != null) {
               return this.doNonEmpty(arg0Value, arg1Value, arg2Value, s0_.utf32Compact0Profile_, s0_.utf32Compact1Profile_, s0_.outOfMemoryProfile_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private TruffleString executeAndSpecialize(int[] arg0Value, int arg1Value, int arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var8;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.FromIntArrayUTF32NodeGen.NonEmptyData s0_ = new TruffleStringFactory.FromIntArrayUTF32NodeGen.NonEmptyData();
            s0_.utf32Compact0Profile_ = ConditionProfile.create();
            s0_.utf32Compact1Profile_ = ConditionProfile.create();
            s0_.outOfMemoryProfile_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.nonEmpty_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.doNonEmpty(arg0Value, arg1Value, arg2Value, s0_.utf32Compact0Profile_, s0_.utf32Compact1Profile_, s0_.outOfMemoryProfile_);
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

      public static TruffleString.FromIntArrayUTF32Node create() {
         return new TruffleStringFactory.FromIntArrayUTF32NodeGen();
      }

      public static TruffleString.FromIntArrayUTF32Node getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.FromIntArrayUTF32Node.class)
      private static final class NonEmptyData {
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf32Compact0Profile_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf32Compact1Profile_;
         @CompilerDirectives.CompilationFinal
         BranchProfile outOfMemoryProfile_;

         NonEmptyData() {
         }
      }

      @GeneratedBy(TruffleString.FromIntArrayUTF32Node.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.FromIntArrayUTF32Node {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(int[] arg0Value, int arg1Value, int arg2Value) {
            return this.doNonEmpty(arg0Value, arg1Value, arg2Value, ConditionProfile.getUncached(), ConditionProfile.getUncached(), BranchProfile.getUncached());
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

   @GeneratedBy(TruffleString.FromJavaStringNode.class)
   static final class FromJavaStringNodeGen extends TruffleString.FromJavaStringNode {
      private static final TruffleStringFactory.FromJavaStringNodeGen.Uncached UNCACHED = new TruffleStringFactory.FromJavaStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.FromJavaStringNodeGen.UTF16Data uTF16_cache;

      private FromJavaStringNodeGen() {
      }

      @Override
      public TruffleString execute(String arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.FromJavaStringNodeGen.UTF16Data s0_ = this.uTF16_cache;
            if (s0_ != null) {
               return TruffleString.FromJavaStringNode.doUTF16(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.fromJavaStringUTF16Node_, s0_.switchEncodingNode_, s0_.utf16Profile_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private TruffleString executeAndSpecialize(String arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var10;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.FromJavaStringNodeGen.UTF16Data s0_ = super.insert(new TruffleStringFactory.FromJavaStringNodeGen.UTF16Data());
            s0_.fromJavaStringUTF16Node_ = s0_.insertAccessor(TStringInternalNodesFactory.FromJavaStringUTF16NodeGen.create());
            s0_.switchEncodingNode_ = s0_.insertAccessor(TruffleString.SwitchEncodingNode.create());
            s0_.utf16Profile_ = ConditionProfile.create();
            VarHandle.storeStoreFence();
            this.uTF16_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var10 = TruffleString.FromJavaStringNode.doUTF16(
               arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.fromJavaStringUTF16Node_, s0_.switchEncodingNode_, s0_.utf16Profile_
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

      public static TruffleString.FromJavaStringNode create() {
         return new TruffleStringFactory.FromJavaStringNodeGen();
      }

      public static TruffleString.FromJavaStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.FromJavaStringNode.class)
      private static final class UTF16Data extends Node {
         @Node.Child
         TStringInternalNodes.FromJavaStringUTF16Node fromJavaStringUTF16Node_;
         @Node.Child
         TruffleString.SwitchEncodingNode switchEncodingNode_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile utf16Profile_;

         UTF16Data() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.FromJavaStringNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.FromJavaStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(String arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            return TruffleString.FromJavaStringNode.doUTF16(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               TStringInternalNodesFactory.FromJavaStringUTF16NodeGen.getUncached(),
               TruffleString.SwitchEncodingNode.getUncached(),
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

   @GeneratedBy(TruffleString.FromLongNode.class)
   static final class FromLongNodeGen extends TruffleString.FromLongNode {
      private static final TruffleStringFactory.FromLongNodeGen.Uncached UNCACHED = new TruffleStringFactory.FromLongNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private FromLongNodeGen() {
      }

      @Override
      public TruffleString execute(long arg0Value, TruffleString.Encoding arg1Value, boolean arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TStringGuards.is7BitCompatible(arg1Value) && arg2Value) {
               return TruffleString.FromLongNode.doLazy(arg0Value, arg1Value, arg2Value);
            }

            if ((state_0 & 2) != 0 && TStringGuards.is7BitCompatible(arg1Value) && !arg2Value) {
               return TruffleString.FromLongNode.doEager(arg0Value, arg1Value, arg2Value);
            }

            if ((state_0 & 4) != 0 && !TStringGuards.is7BitCompatible(arg1Value)) {
               return TruffleString.FromLongNode.unsupported(arg0Value, arg1Value, arg2Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private TruffleString executeAndSpecialize(long arg0Value, TruffleString.Encoding arg1Value, boolean arg2Value) {
         int state_0 = this.state_0_;
         if (TStringGuards.is7BitCompatible(arg1Value) && arg2Value) {
            int var8;
            this.state_0_ = var8 = state_0 | 1;
            return TruffleString.FromLongNode.doLazy(arg0Value, arg1Value, arg2Value);
         } else if (TStringGuards.is7BitCompatible(arg1Value) && !arg2Value) {
            int var7;
            this.state_0_ = var7 = state_0 | 2;
            return TruffleString.FromLongNode.doEager(arg0Value, arg1Value, arg2Value);
         } else if (!TStringGuards.is7BitCompatible(arg1Value)) {
            int var6;
            this.state_0_ = var6 = state_0 | 4;
            return TruffleString.FromLongNode.unsupported(arg0Value, arg1Value, arg2Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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

      public static TruffleString.FromLongNode create() {
         return new TruffleStringFactory.FromLongNodeGen();
      }

      public static TruffleString.FromLongNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.FromLongNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.FromLongNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(long arg0Value, TruffleString.Encoding arg1Value, boolean arg2Value) {
            if (TStringGuards.is7BitCompatible(arg1Value) && arg2Value) {
               return TruffleString.FromLongNode.doLazy(arg0Value, arg1Value, arg2Value);
            } else if (TStringGuards.is7BitCompatible(arg1Value) && !arg2Value) {
               return TruffleString.FromLongNode.doEager(arg0Value, arg1Value, arg2Value);
            } else if (!TStringGuards.is7BitCompatible(arg1Value)) {
               return TruffleString.FromLongNode.unsupported(arg0Value, arg1Value, arg2Value);
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

   @GeneratedBy(TruffleString.FromNativePointerNode.class)
   static final class FromNativePointerNodeGen extends TruffleString.FromNativePointerNode {
      private static final TruffleStringFactory.FromNativePointerNodeGen.Uncached UNCACHED = new TruffleStringFactory.FromNativePointerNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.FromNativePointerNodeGen.FromNativePointerData fromNativePointer_cache;

      private FromNativePointerNodeGen() {
      }

      @Override
      public TruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.FromNativePointerNodeGen.FromNativePointerData s0_ = this.fromNativePointer_cache;
            if (s0_ != null) {
               return this.fromNativePointer(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  s0_.interopLibrary_,
                  s0_.fromNativePointerNode_,
                  s0_.fromBufferWithStringCompactionNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private TruffleString executeAndSpecialize(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var10;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.FromNativePointerNodeGen.FromNativePointerData s0_ = super.insert(
               new TruffleStringFactory.FromNativePointerNodeGen.FromNativePointerData()
            );
            s0_.interopLibrary_ = s0_.insertAccessor(TStringAccessor.createInteropLibrary());
            s0_.fromNativePointerNode_ = s0_.insertAccessor(TStringInternalNodesFactory.FromNativePointerNodeGen.create());
            s0_.fromBufferWithStringCompactionNode_ = s0_.insertAccessor(TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.create());
            VarHandle.storeStoreFence();
            this.fromNativePointer_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var10 = this.fromNativePointer(
               arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.interopLibrary_, s0_.fromNativePointerNode_, s0_.fromBufferWithStringCompactionNode_
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

      public static TruffleString.FromNativePointerNode create() {
         return new TruffleStringFactory.FromNativePointerNodeGen();
      }

      public static TruffleString.FromNativePointerNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.FromNativePointerNode.class)
      private static final class FromNativePointerData extends Node {
         @Node.Child
         Node interopLibrary_;
         @Node.Child
         TStringInternalNodes.FromNativePointerNode fromNativePointerNode_;
         @Node.Child
         TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode_;

         FromNativePointerData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.FromNativePointerNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.FromNativePointerNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            return this.fromNativePointer(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               TStringAccessor.getUncachedInteropLibrary(),
               TStringInternalNodesFactory.FromNativePointerNodeGen.getUncached(),
               TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.GetByteCodeRangeNode.class)
   static final class GetByteCodeRangeNodeGen extends TruffleString.GetByteCodeRangeNode {
      private static final TruffleStringFactory.GetByteCodeRangeNodeGen.Uncached UNCACHED = new TruffleStringFactory.GetByteCodeRangeNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;

      private GetByteCodeRangeNodeGen() {
      }

      @Override
      public TruffleString.CodeRange execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return TruffleString.GetByteCodeRangeNode.getCodeRange(arg0Value, arg1Value, this.getCodeRangeNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private TruffleString.CodeRange executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString.CodeRange var6;
         try {
            int state_0 = this.state_0_;
            this.getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = TruffleString.GetByteCodeRangeNode.getCodeRange(arg0Value, arg1Value, this.getCodeRangeNode_);
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

      public static TruffleString.GetByteCodeRangeNode create() {
         return new TruffleStringFactory.GetByteCodeRangeNodeGen();
      }

      public static TruffleString.GetByteCodeRangeNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.GetByteCodeRangeNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.GetByteCodeRangeNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString.CodeRange execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            return TruffleString.GetByteCodeRangeNode.getCodeRange(arg0Value, arg1Value, TStringInternalNodes.GetCodeRangeNode.getUncached());
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

   @GeneratedBy(TruffleString.GetCodeRangeNode.class)
   static final class GetCodeRangeNodeGen extends TruffleString.GetCodeRangeNode {
      private static final TruffleStringFactory.GetCodeRangeNodeGen.Uncached UNCACHED = new TruffleStringFactory.GetCodeRangeNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;

      private GetCodeRangeNodeGen() {
      }

      @Override
      public TruffleString.CodeRange execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return TruffleString.GetCodeRangeNode.getCodeRange(arg0Value, arg1Value, this.getCodeRangeNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private TruffleString.CodeRange executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString.CodeRange var6;
         try {
            int state_0 = this.state_0_;
            this.getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = TruffleString.GetCodeRangeNode.getCodeRange(arg0Value, arg1Value, this.getCodeRangeNode_);
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

      public static TruffleString.GetCodeRangeNode create() {
         return new TruffleStringFactory.GetCodeRangeNodeGen();
      }

      public static TruffleString.GetCodeRangeNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.GetCodeRangeNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.GetCodeRangeNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString.CodeRange execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            return TruffleString.GetCodeRangeNode.getCodeRange(arg0Value, arg1Value, TStringInternalNodes.GetCodeRangeNode.getUncached());
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

   @GeneratedBy(TruffleString.GetInternalByteArrayNode.class)
   static final class GetInternalByteArrayNodeGen extends TruffleString.GetInternalByteArrayNode {
      private static final TruffleStringFactory.GetInternalByteArrayNodeGen.Uncached UNCACHED = new TruffleStringFactory.GetInternalByteArrayNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.GetInternalByteArrayNodeGen.GetInternalByteArrayData getInternalByteArray_cache;

      private GetInternalByteArrayNodeGen() {
      }

      @Override
      public InternalByteArray execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.GetInternalByteArrayNodeGen.GetInternalByteArrayData s0_ = this.getInternalByteArray_cache;
            if (s0_ != null) {
               return this.getInternalByteArray(
                  arg0Value,
                  arg1Value,
                  s0_.toIndexableNode_,
                  s0_.utf16Profile_,
                  s0_.utf16S0Profile_,
                  s0_.utf32Profile_,
                  s0_.utf32S0Profile_,
                  s0_.utf32S1Profile_,
                  s0_.isByteArrayProfile_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private InternalByteArray executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         InternalByteArray var7;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.GetInternalByteArrayNodeGen.GetInternalByteArrayData s0_ = super.insert(
               new TruffleStringFactory.GetInternalByteArrayNodeGen.GetInternalByteArrayData()
            );
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.utf16Profile_ = ConditionProfile.create();
            s0_.utf16S0Profile_ = ConditionProfile.create();
            s0_.utf32Profile_ = ConditionProfile.create();
            s0_.utf32S0Profile_ = ConditionProfile.create();
            s0_.utf32S1Profile_ = ConditionProfile.create();
            s0_.isByteArrayProfile_ = ConditionProfile.create();
            VarHandle.storeStoreFence();
            this.getInternalByteArray_cache = s0_;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.getInternalByteArray(
               arg0Value,
               arg1Value,
               s0_.toIndexableNode_,
               s0_.utf16Profile_,
               s0_.utf16S0Profile_,
               s0_.utf32Profile_,
               s0_.utf32S0Profile_,
               s0_.utf32S1Profile_,
               s0_.isByteArrayProfile_
            );
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleString.GetInternalByteArrayNode create() {
         return new TruffleStringFactory.GetInternalByteArrayNodeGen();
      }

      public static TruffleString.GetInternalByteArrayNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.GetInternalByteArrayNode.class)
      private static final class GetInternalByteArrayData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
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
         ConditionProfile isByteArrayProfile_;

         GetInternalByteArrayData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.GetInternalByteArrayNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.GetInternalByteArrayNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public InternalByteArray execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            return this.getInternalByteArray(
               arg0Value,
               arg1Value,
               TruffleString.ToIndexableNode.getUncached(),
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

   @GeneratedBy(TruffleString.GetInternalNativePointerNode.class)
   static final class GetInternalNativePointerNodeGen extends TruffleString.GetInternalNativePointerNode {
      private static final TruffleStringFactory.GetInternalNativePointerNodeGen.Uncached UNCACHED = new TruffleStringFactory.GetInternalNativePointerNodeGen.Uncached();

      private GetInternalNativePointerNodeGen() {
      }

      @Override
      public Object execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         return TruffleString.GetInternalNativePointerNode.getNativePointer(arg0Value, arg1Value);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      public static TruffleString.GetInternalNativePointerNode create() {
         return new TruffleStringFactory.GetInternalNativePointerNodeGen();
      }

      public static TruffleString.GetInternalNativePointerNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.GetInternalNativePointerNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.GetInternalNativePointerNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public Object execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            return TruffleString.GetInternalNativePointerNode.getNativePointer(arg0Value, arg1Value);
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

   @GeneratedBy(TruffleString.HashCodeNode.class)
   static final class HashCodeNodeGen extends TruffleString.HashCodeNode {
      private static final TruffleStringFactory.HashCodeNodeGen.Uncached UNCACHED = new TruffleStringFactory.HashCodeNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.HashCodeNodeGen.CalculateHashData calculateHash_cache;

      private HashCodeNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.HashCodeNodeGen.CalculateHashData s0_ = this.calculateHash_cache;
            if (s0_ != null) {
               return TruffleString.HashCodeNode.calculateHash(arg0Value, arg1Value, s0_.cacheMiss_, s0_.toIndexableNode_, s0_.calculateHashCodeNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var7;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.HashCodeNodeGen.CalculateHashData s0_ = super.insert(new TruffleStringFactory.HashCodeNodeGen.CalculateHashData());
            s0_.cacheMiss_ = ConditionProfile.create();
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.calculateHashCodeNode_ = s0_.insertAccessor(TStringOpsNodesFactory.CalculateHashCodeNodeGen.create());
            VarHandle.storeStoreFence();
            this.calculateHash_cache = s0_;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = TruffleString.HashCodeNode.calculateHash(arg0Value, arg1Value, s0_.cacheMiss_, s0_.toIndexableNode_, s0_.calculateHashCodeNode_);
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleString.HashCodeNode create() {
         return new TruffleStringFactory.HashCodeNodeGen();
      }

      public static TruffleString.HashCodeNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.HashCodeNode.class)
      private static final class CalculateHashData extends Node {
         @CompilerDirectives.CompilationFinal
         ConditionProfile cacheMiss_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringOpsNodes.CalculateHashCodeNode calculateHashCodeNode_;

         CalculateHashData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.HashCodeNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.HashCodeNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            return TruffleString.HashCodeNode.calculateHash(
               arg0Value,
               arg1Value,
               ConditionProfile.getUncached(),
               TruffleString.ToIndexableNode.getUncached(),
               TStringOpsNodesFactory.CalculateHashCodeNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.IndexOfCodePointNode.class)
   static final class IndexOfCodePointNodeGen extends TruffleString.IndexOfCodePointNode {
      private static final TruffleStringFactory.IndexOfCodePointNodeGen.Uncached UNCACHED = new TruffleStringFactory.IndexOfCodePointNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.IndexOfCodePointNodeGen.IndexOfData indexOf_cache;

      private IndexOfCodePointNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.IndexOfCodePointNodeGen.IndexOfData s0_ = this.indexOf_cache;
            if (s0_ != null) {
               return TruffleString.IndexOfCodePointNode.doIndexOf(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  s0_.toIndexableNode_,
                  s0_.getCodePointLengthNode_,
                  s0_.getCodeRangeNode_,
                  s0_.indexOfNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var10;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.IndexOfCodePointNodeGen.IndexOfData s0_ = super.insert(new TruffleStringFactory.IndexOfCodePointNodeGen.IndexOfData());
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.indexOfNode_ = s0_.insertAccessor(TStringInternalNodesFactory.IndexOfCodePointNodeGen.create());
            VarHandle.storeStoreFence();
            this.indexOf_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var10 = TruffleString.IndexOfCodePointNode.doIndexOf(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               s0_.toIndexableNode_,
               s0_.getCodePointLengthNode_,
               s0_.getCodeRangeNode_,
               s0_.indexOfNode_
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

      public static TruffleString.IndexOfCodePointNode create() {
         return new TruffleStringFactory.IndexOfCodePointNodeGen();
      }

      public static TruffleString.IndexOfCodePointNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.IndexOfCodePointNode.class)
      private static final class IndexOfData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.IndexOfCodePointNode indexOfNode_;

         IndexOfData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.IndexOfCodePointNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.IndexOfCodePointNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            return TruffleString.IndexOfCodePointNode.doIndexOf(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.IndexOfCodePointNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.IndexOfStringNode.class)
   static final class IndexOfStringNodeGen extends TruffleString.IndexOfStringNode {
      private static final TruffleStringFactory.IndexOfStringNodeGen.Uncached UNCACHED = new TruffleStringFactory.IndexOfStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.IndexOfStringNodeGen.IndexOfStringData indexOfString_cache;

      private IndexOfStringNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.IndexOfStringNodeGen.IndexOfStringData s0_ = this.indexOfString_cache;
            if (s0_ != null) {
               return TruffleString.IndexOfStringNode.indexOfString(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  s0_.toIndexableNodeA_,
                  s0_.toIndexableNodeB_,
                  s0_.getCodePointLengthANode_,
                  s0_.getCodePointLengthBNode_,
                  s0_.getCodeRangeANode_,
                  s0_.getCodeRangeBNode_,
                  s0_.indexOfStringNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var10;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.IndexOfStringNodeGen.IndexOfStringData s0_ = super.insert(new TruffleStringFactory.IndexOfStringNodeGen.IndexOfStringData());
            s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodePointLengthANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.getCodePointLengthBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.indexOfStringNode_ = s0_.insertAccessor(TStringInternalNodesFactory.IndexOfStringNodeGen.create());
            VarHandle.storeStoreFence();
            this.indexOfString_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var10 = TruffleString.IndexOfStringNode.indexOfString(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               s0_.toIndexableNodeA_,
               s0_.toIndexableNodeB_,
               s0_.getCodePointLengthANode_,
               s0_.getCodePointLengthBNode_,
               s0_.getCodeRangeANode_,
               s0_.getCodeRangeBNode_,
               s0_.indexOfStringNode_
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

      public static TruffleString.IndexOfStringNode create() {
         return new TruffleStringFactory.IndexOfStringNodeGen();
      }

      public static TruffleString.IndexOfStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.IndexOfStringNode.class)
      private static final class IndexOfStringData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeA_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeB_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthANode_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthBNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode_;
         @Node.Child
         TStringInternalNodes.IndexOfStringNode indexOfStringNode_;

         IndexOfStringData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.IndexOfStringNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.IndexOfStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            return TruffleString.IndexOfStringNode.indexOfString(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               TruffleString.ToIndexableNode.getUncached(),
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.IndexOfStringNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.IntIndexOfAnyIntUTF32Node.class)
   static final class IntIndexOfAnyIntUTF32NodeGen extends TruffleString.IntIndexOfAnyIntUTF32Node {
      private static final TruffleStringFactory.IntIndexOfAnyIntUTF32NodeGen.Uncached UNCACHED = new TruffleStringFactory.IntIndexOfAnyIntUTF32NodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.IntIndexOfAnyIntUTF32NodeGen.IndexOfRawData indexOfRaw_cache;

      private IntIndexOfAnyIntUTF32NodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int[] arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.IntIndexOfAnyIntUTF32NodeGen.IndexOfRawData s0_ = this.indexOfRaw_cache;
            if (s0_ != null) {
               return this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int[] arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var9;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.IntIndexOfAnyIntUTF32NodeGen.IndexOfRawData s0_ = super.insert(
               new TruffleStringFactory.IntIndexOfAnyIntUTF32NodeGen.IndexOfRawData()
            );
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.indexOfNode_ = s0_.insertAccessor(TStringOpsNodesFactory.IndexOfAnyIntNodeGen.create());
            VarHandle.storeStoreFence();
            this.indexOfRaw_cache = s0_;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_);
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

      public static TruffleString.IntIndexOfAnyIntUTF32Node create() {
         return new TruffleStringFactory.IntIndexOfAnyIntUTF32NodeGen();
      }

      public static TruffleString.IntIndexOfAnyIntUTF32Node getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.IntIndexOfAnyIntUTF32Node.class)
      private static final class IndexOfRawData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringOpsNodes.IndexOfAnyIntNode indexOfNode_;

         IndexOfRawData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.IntIndexOfAnyIntUTF32Node.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.IntIndexOfAnyIntUTF32Node {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int[] arg3Value) {
            return this.indexOfRaw(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringOpsNodesFactory.IndexOfAnyIntNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.IsValidNode.class)
   static final class IsValidNodeGen extends TruffleString.IsValidNode {
      private static final TruffleStringFactory.IsValidNodeGen.Uncached UNCACHED = new TruffleStringFactory.IsValidNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;

      private IsValidNodeGen() {
      }

      @Override
      public boolean execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return TruffleString.IsValidNode.isValid(arg0Value, arg1Value, this.getCodeRangeNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private boolean executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var6;
         try {
            int state_0 = this.state_0_;
            this.getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = TruffleString.IsValidNode.isValid(arg0Value, arg1Value, this.getCodeRangeNode_);
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

      public static TruffleString.IsValidNode create() {
         return new TruffleStringFactory.IsValidNodeGen();
      }

      public static TruffleString.IsValidNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.IsValidNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.IsValidNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            return TruffleString.IsValidNode.isValid(arg0Value, arg1Value, TStringInternalNodes.GetCodeRangeNode.getUncached());
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

   @GeneratedBy(TruffleString.LastByteIndexOfCodePointNode.class)
   static final class LastByteIndexOfCodePointNodeGen extends TruffleString.LastByteIndexOfCodePointNode {
      private static final TruffleStringFactory.LastByteIndexOfCodePointNodeGen.Uncached UNCACHED = new TruffleStringFactory.LastByteIndexOfCodePointNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.LastByteIndexOfCodePointNodeGen.IndexOfData indexOf_cache;

      private LastByteIndexOfCodePointNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.LastByteIndexOfCodePointNodeGen.IndexOfData s0_ = this.indexOf_cache;
            if (s0_ != null) {
               return TruffleString.LastByteIndexOfCodePointNode.doIndexOf(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.lastIndexOfNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var10;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.LastByteIndexOfCodePointNodeGen.IndexOfData s0_ = super.insert(
               new TruffleStringFactory.LastByteIndexOfCodePointNodeGen.IndexOfData()
            );
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.lastIndexOfNode_ = s0_.insertAccessor(TStringInternalNodesFactory.LastIndexOfCodePointRawNodeGen.create());
            VarHandle.storeStoreFence();
            this.indexOf_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var10 = TruffleString.LastByteIndexOfCodePointNode.doIndexOf(
               arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.lastIndexOfNode_
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

      public static TruffleString.LastByteIndexOfCodePointNode create() {
         return new TruffleStringFactory.LastByteIndexOfCodePointNodeGen();
      }

      public static TruffleString.LastByteIndexOfCodePointNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.LastByteIndexOfCodePointNode.class)
      private static final class IndexOfData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.LastIndexOfCodePointRawNode lastIndexOfNode_;

         IndexOfData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.LastByteIndexOfCodePointNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.LastByteIndexOfCodePointNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            return TruffleString.LastByteIndexOfCodePointNode.doIndexOf(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.LastIndexOfCodePointRawNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.LastByteIndexOfStringNode.class)
   static final class LastByteIndexOfStringNodeGen extends TruffleString.LastByteIndexOfStringNode {
      private static final TruffleStringFactory.LastByteIndexOfStringNodeGen.Uncached UNCACHED = new TruffleStringFactory.LastByteIndexOfStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.LastByteIndexOfStringNodeGen.LastByteIndexOfStringData lastByteIndexOfString_cache;

      private LastByteIndexOfStringNodeGen() {
      }

      @Override
      int execute(
         AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, byte[] arg4Value, TruffleString.Encoding arg5Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.LastByteIndexOfStringNodeGen.LastByteIndexOfStringData s0_ = this.lastByteIndexOfString_cache;
            if (s0_ != null) {
               return TruffleString.LastByteIndexOfStringNode.lastByteIndexOfString(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  s0_.toIndexableNodeA_,
                  s0_.toIndexableNodeB_,
                  s0_.getCodeRangeANode_,
                  s0_.getCodeRangeBNode_,
                  s0_.indexOfStringNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, byte[] arg4Value, TruffleString.Encoding arg5Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var11;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.LastByteIndexOfStringNodeGen.LastByteIndexOfStringData s0_ = super.insert(
               new TruffleStringFactory.LastByteIndexOfStringNodeGen.LastByteIndexOfStringData()
            );
            s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.indexOfStringNode_ = s0_.insertAccessor(TStringInternalNodesFactory.LastIndexOfStringRawNodeGen.create());
            VarHandle.storeStoreFence();
            this.lastByteIndexOfString_cache = s0_;
            int var15;
            this.state_0_ = var15 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var11 = TruffleString.LastByteIndexOfStringNode.lastByteIndexOfString(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               s0_.toIndexableNodeA_,
               s0_.toIndexableNodeB_,
               s0_.getCodeRangeANode_,
               s0_.getCodeRangeBNode_,
               s0_.indexOfStringNode_
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

      public static TruffleString.LastByteIndexOfStringNode create() {
         return new TruffleStringFactory.LastByteIndexOfStringNodeGen();
      }

      public static TruffleString.LastByteIndexOfStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.LastByteIndexOfStringNode.class)
      private static final class LastByteIndexOfStringData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeA_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeB_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode_;
         @Node.Child
         TStringInternalNodes.LastIndexOfStringRawNode indexOfStringNode_;

         LastByteIndexOfStringData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.LastByteIndexOfStringNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.LastByteIndexOfStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         int execute(
            AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, byte[] arg4Value, TruffleString.Encoding arg5Value
         ) {
            return TruffleString.LastByteIndexOfStringNode.lastByteIndexOfString(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               TruffleString.ToIndexableNode.getUncached(),
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.LastIndexOfStringRawNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.LastIndexOfCodePointNode.class)
   static final class LastIndexOfCodePointNodeGen extends TruffleString.LastIndexOfCodePointNode {
      private static final TruffleStringFactory.LastIndexOfCodePointNodeGen.Uncached UNCACHED = new TruffleStringFactory.LastIndexOfCodePointNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.LastIndexOfCodePointNodeGen.IndexOfData indexOf_cache;

      private LastIndexOfCodePointNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.LastIndexOfCodePointNodeGen.IndexOfData s0_ = this.indexOf_cache;
            if (s0_ != null) {
               return TruffleString.LastIndexOfCodePointNode.doIndexOf(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  s0_.toIndexableNode_,
                  s0_.getCodePointLengthNode_,
                  s0_.getCodeRangeNode_,
                  s0_.lastIndexOfNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var10;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.LastIndexOfCodePointNodeGen.IndexOfData s0_ = super.insert(new TruffleStringFactory.LastIndexOfCodePointNodeGen.IndexOfData());
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.lastIndexOfNode_ = s0_.insertAccessor(TStringInternalNodesFactory.LastIndexOfCodePointNodeGen.create());
            VarHandle.storeStoreFence();
            this.indexOf_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var10 = TruffleString.LastIndexOfCodePointNode.doIndexOf(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               s0_.toIndexableNode_,
               s0_.getCodePointLengthNode_,
               s0_.getCodeRangeNode_,
               s0_.lastIndexOfNode_
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

      public static TruffleString.LastIndexOfCodePointNode create() {
         return new TruffleStringFactory.LastIndexOfCodePointNodeGen();
      }

      public static TruffleString.LastIndexOfCodePointNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.LastIndexOfCodePointNode.class)
      private static final class IndexOfData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.LastIndexOfCodePointNode lastIndexOfNode_;

         IndexOfData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.LastIndexOfCodePointNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.LastIndexOfCodePointNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            return TruffleString.LastIndexOfCodePointNode.doIndexOf(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.LastIndexOfCodePointNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.LastIndexOfStringNode.class)
   static final class LastIndexOfStringNodeGen extends TruffleString.LastIndexOfStringNode {
      private static final TruffleStringFactory.LastIndexOfStringNodeGen.Uncached UNCACHED = new TruffleStringFactory.LastIndexOfStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.LastIndexOfStringNodeGen.LastIndexOfStringData lastIndexOfString_cache;

      private LastIndexOfStringNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.LastIndexOfStringNodeGen.LastIndexOfStringData s0_ = this.lastIndexOfString_cache;
            if (s0_ != null) {
               return TruffleString.LastIndexOfStringNode.lastIndexOfString(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  s0_.toIndexableNodeA_,
                  s0_.toIndexableNodeB_,
                  s0_.getCodePointLengthANode_,
                  s0_.getCodePointLengthBNode_,
                  s0_.getCodeRangeANode_,
                  s0_.getCodeRangeBNode_,
                  s0_.indexOfStringNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private int executeAndSpecialize(
         AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var10;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.LastIndexOfStringNodeGen.LastIndexOfStringData s0_ = super.insert(
               new TruffleStringFactory.LastIndexOfStringNodeGen.LastIndexOfStringData()
            );
            s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodePointLengthANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.getCodePointLengthBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.indexOfStringNode_ = s0_.insertAccessor(TStringInternalNodesFactory.LastIndexOfStringNodeGen.create());
            VarHandle.storeStoreFence();
            this.lastIndexOfString_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var10 = TruffleString.LastIndexOfStringNode.lastIndexOfString(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               s0_.toIndexableNodeA_,
               s0_.toIndexableNodeB_,
               s0_.getCodePointLengthANode_,
               s0_.getCodePointLengthBNode_,
               s0_.getCodeRangeANode_,
               s0_.getCodeRangeBNode_,
               s0_.indexOfStringNode_
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

      public static TruffleString.LastIndexOfStringNode create() {
         return new TruffleStringFactory.LastIndexOfStringNodeGen();
      }

      public static TruffleString.LastIndexOfStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.LastIndexOfStringNode.class)
      private static final class LastIndexOfStringData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeA_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeB_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthANode_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthBNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode_;
         @Node.Child
         TStringInternalNodes.LastIndexOfStringNode indexOfStringNode_;

         LastIndexOfStringData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.LastIndexOfStringNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.LastIndexOfStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            return TruffleString.LastIndexOfStringNode.lastIndexOfString(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               TruffleString.ToIndexableNode.getUncached(),
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.LastIndexOfStringNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.MaterializeNode.class)
   static final class MaterializeNodeGen extends TruffleString.MaterializeNode {
      private static final TruffleStringFactory.MaterializeNodeGen.Uncached UNCACHED = new TruffleStringFactory.MaterializeNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.ToIndexableNode toIndexableNode_;

      private MaterializeNodeGen() {
      }

      @Override
      public void execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleString.MaterializeNode.doMaterialize(arg0Value, arg1Value, this.toIndexableNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private void executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            this.toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            TruffleString.MaterializeNode.doMaterialize(arg0Value, arg1Value, this.toIndexableNode_);
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

      public static TruffleString.MaterializeNode create() {
         return new TruffleStringFactory.MaterializeNodeGen();
      }

      public static TruffleString.MaterializeNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.MaterializeNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.MaterializeNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public void execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            TruffleString.MaterializeNode.doMaterialize(arg0Value, arg1Value, TruffleString.ToIndexableNode.getUncached());
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

   @GeneratedBy(TruffleString.ParseDoubleNode.class)
   static final class ParseDoubleNodeGen extends TruffleString.ParseDoubleNode {
      private static final TruffleStringFactory.ParseDoubleNodeGen.Uncached UNCACHED = new TruffleStringFactory.ParseDoubleNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.ToIndexableNode parseDouble_toIndexableNode_;
      @Node.Child
      private TStringInternalNodes.ParseDoubleNode parseDouble_parseDoubleNode_;

      private ParseDoubleNodeGen() {
      }

      @Override
      public double execute(AbstractTruffleString arg0Value) throws TruffleString.NumberFormatException {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TruffleString.ParseDoubleNode.isLazyLongSafeInteger(arg0Value)) {
               return TruffleString.ParseDoubleNode.doLazyLong(arg0Value);
            }

            if ((state_0 & 2) != 0 && !TruffleString.ParseDoubleNode.isLazyLongSafeInteger(arg0Value)) {
               return TruffleString.ParseDoubleNode.parseDouble(arg0Value, this.parseDouble_toIndexableNode_, this.parseDouble_parseDoubleNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private double executeAndSpecialize(AbstractTruffleString arg0Value) throws TruffleString.NumberFormatException {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         double var5;
         try {
            int state_0 = this.state_0_;
            if (!TruffleString.ParseDoubleNode.isLazyLongSafeInteger(arg0Value)) {
               if (TruffleString.ParseDoubleNode.isLazyLongSafeInteger(arg0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
               }

               this.parseDouble_toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
               this.parseDouble_parseDoubleNode_ = super.insert(TStringInternalNodesFactory.ParseDoubleNodeGen.create());
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TruffleString.ParseDoubleNode.parseDouble(arg0Value, this.parseDouble_toIndexableNode_, this.parseDouble_parseDoubleNode_);
            }

            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = TruffleString.ParseDoubleNode.doLazyLong(arg0Value);
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

      public static TruffleString.ParseDoubleNode create() {
         return new TruffleStringFactory.ParseDoubleNodeGen();
      }

      public static TruffleString.ParseDoubleNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.ParseDoubleNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.ParseDoubleNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public double execute(AbstractTruffleString arg0Value) throws TruffleString.NumberFormatException {
            if (TruffleString.ParseDoubleNode.isLazyLongSafeInteger(arg0Value)) {
               return TruffleString.ParseDoubleNode.doLazyLong(arg0Value);
            } else if (!TruffleString.ParseDoubleNode.isLazyLongSafeInteger(arg0Value)) {
               return TruffleString.ParseDoubleNode.parseDouble(
                  arg0Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodesFactory.ParseDoubleNodeGen.getUncached()
               );
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

   @GeneratedBy(TruffleString.ParseIntNode.class)
   static final class ParseIntNodeGen extends TruffleString.ParseIntNode {
      private static final TruffleStringFactory.ParseIntNodeGen.Uncached UNCACHED = new TruffleStringFactory.ParseIntNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile lazyLong_errorProfile_;
      @Node.Child
      private TruffleStringFactory.ParseIntNodeGen.ParseData parse_cache;

      private ParseIntNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value) throws TruffleString.NumberFormatException {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value.isLazyLong() && arg1Value == 10) {
               return TruffleString.ParseIntNode.doLazyLong(arg0Value, arg1Value, this.lazyLong_errorProfile_);
            }

            if ((state_0 & 2) != 0) {
               TruffleStringFactory.ParseIntNodeGen.ParseData s1_ = this.parse_cache;
               if (s1_ != null && (!arg0Value.isLazyLong() || arg1Value != 10)) {
                  return TruffleString.ParseIntNode.doParse(
                     arg0Value, arg1Value, s1_.toIndexableNode_, s1_.getCodeRangeANode_, s1_.parseIntNode_, s1_.radixProfile_
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value) throws TruffleString.NumberFormatException {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int s1_;
         try {
            int state_0 = this.state_0_;
            if (!arg0Value.isLazyLong() || arg1Value != 10) {
               if (arg0Value.isLazyLong() && arg1Value == 10) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
               }

               TruffleStringFactory.ParseIntNodeGen.ParseData s1_x = super.insert(new TruffleStringFactory.ParseIntNodeGen.ParseData());
               s1_x.toIndexableNode_ = s1_x.insertAccessor(TruffleString.ToIndexableNode.create());
               s1_x.getCodeRangeANode_ = s1_x.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
               s1_x.parseIntNode_ = s1_x.insertAccessor(TStringInternalNodesFactory.ParseIntNodeGen.create());
               s1_x.radixProfile_ = IntValueProfile.createIdentityProfile();
               VarHandle.storeStoreFence();
               this.parse_cache = s1_x;
               int var12;
               this.state_0_ = var12 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TruffleString.ParseIntNode.doParse(
                  arg0Value, arg1Value, s1_x.toIndexableNode_, s1_x.getCodeRangeANode_, s1_x.parseIntNode_, s1_x.radixProfile_
               );
            }

            this.lazyLong_errorProfile_ = BranchProfile.create();
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            s1_ = TruffleString.ParseIntNode.doLazyLong(arg0Value, arg1Value, this.lazyLong_errorProfile_);
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

      public static TruffleString.ParseIntNode create() {
         return new TruffleStringFactory.ParseIntNodeGen();
      }

      public static TruffleString.ParseIntNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.ParseIntNode.class)
      private static final class ParseData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.ParseIntNode parseIntNode_;
         @CompilerDirectives.CompilationFinal
         IntValueProfile radixProfile_;

         ParseData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.ParseIntNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.ParseIntNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value) throws TruffleString.NumberFormatException {
            if (arg0Value.isLazyLong() && arg1Value == 10) {
               return TruffleString.ParseIntNode.doLazyLong(arg0Value, arg1Value, BranchProfile.getUncached());
            } else if (arg0Value.isLazyLong() && arg1Value == 10) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            } else {
               return TruffleString.ParseIntNode.doParse(
                  arg0Value,
                  arg1Value,
                  TruffleString.ToIndexableNode.getUncached(),
                  TStringInternalNodes.GetCodeRangeNode.getUncached(),
                  TStringInternalNodesFactory.ParseIntNodeGen.getUncached(),
                  IntValueProfile.getUncached()
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

   @GeneratedBy(TruffleString.ParseLongNode.class)
   static final class ParseLongNodeGen extends TruffleString.ParseLongNode {
      private static final TruffleStringFactory.ParseLongNodeGen.Uncached UNCACHED = new TruffleStringFactory.ParseLongNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.ParseLongNodeGen.ParseData parse_cache;

      private ParseLongNodeGen() {
      }

      @Override
      public long execute(AbstractTruffleString arg0Value, int arg1Value) throws TruffleString.NumberFormatException {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value.isLazyLong() && arg1Value == 10) {
               return TruffleString.ParseLongNode.doLazyLong(arg0Value, arg1Value);
            }

            if ((state_0 & 2) != 0) {
               TruffleStringFactory.ParseLongNodeGen.ParseData s1_ = this.parse_cache;
               if (s1_ != null && (!arg0Value.isLazyLong() || arg1Value != 10)) {
                  return TruffleString.ParseLongNode.doParse(
                     arg0Value, arg1Value, s1_.toIndexableNode_, s1_.getCodeRangeANode_, s1_.parseLongNode_, s1_.radixProfile_
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private long executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value) throws TruffleString.NumberFormatException {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         long s1_;
         try {
            int state_0 = this.state_0_;
            if (!arg0Value.isLazyLong() || arg1Value != 10) {
               if (arg0Value.isLazyLong() && arg1Value == 10) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
               }

               TruffleStringFactory.ParseLongNodeGen.ParseData s1_x = super.insert(new TruffleStringFactory.ParseLongNodeGen.ParseData());
               s1_x.toIndexableNode_ = s1_x.insertAccessor(TruffleString.ToIndexableNode.create());
               s1_x.getCodeRangeANode_ = s1_x.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
               s1_x.parseLongNode_ = s1_x.insertAccessor(TStringInternalNodesFactory.ParseLongNodeGen.create());
               s1_x.radixProfile_ = IntValueProfile.createIdentityProfile();
               VarHandle.storeStoreFence();
               this.parse_cache = s1_x;
               int var13;
               this.state_0_ = var13 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TruffleString.ParseLongNode.doParse(
                  arg0Value, arg1Value, s1_x.toIndexableNode_, s1_x.getCodeRangeANode_, s1_x.parseLongNode_, s1_x.radixProfile_
               );
            }

            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            s1_ = TruffleString.ParseLongNode.doLazyLong(arg0Value, arg1Value);
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

      public static TruffleString.ParseLongNode create() {
         return new TruffleStringFactory.ParseLongNodeGen();
      }

      public static TruffleString.ParseLongNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.ParseLongNode.class)
      private static final class ParseData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.ParseLongNode parseLongNode_;
         @CompilerDirectives.CompilationFinal
         IntValueProfile radixProfile_;

         ParseData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.ParseLongNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.ParseLongNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public long execute(AbstractTruffleString arg0Value, int arg1Value) throws TruffleString.NumberFormatException {
            if (arg0Value.isLazyLong() && arg1Value == 10) {
               return TruffleString.ParseLongNode.doLazyLong(arg0Value, arg1Value);
            } else if (arg0Value.isLazyLong() && arg1Value == 10) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            } else {
               return TruffleString.ParseLongNode.doParse(
                  arg0Value,
                  arg1Value,
                  TruffleString.ToIndexableNode.getUncached(),
                  TStringInternalNodes.GetCodeRangeNode.getUncached(),
                  TStringInternalNodesFactory.ParseLongNodeGen.getUncached(),
                  IntValueProfile.getUncached()
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

   @GeneratedBy(TruffleString.ReadByteNode.class)
   static final class ReadByteNodeGen extends TruffleString.ReadByteNode {
      private static final TruffleStringFactory.ReadByteNodeGen.Uncached UNCACHED = new TruffleStringFactory.ReadByteNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.ToIndexableNode toIndexableNode_;
      @Node.Child
      private TStringInternalNodes.ReadByteNode readByteNode_;

      private ReadByteNodeGen() {
      }

      @Override
      public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return TruffleString.ReadByteNode.doRead(arg0Value, arg1Value, arg2Value, this.toIndexableNode_, this.readByteNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
         }
      }

      private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var7;
         try {
            int state_0 = this.state_0_;
            this.toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
            this.readByteNode_ = super.insert(TStringInternalNodesFactory.ReadByteNodeGen.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = TruffleString.ReadByteNode.doRead(arg0Value, arg1Value, arg2Value, this.toIndexableNode_, this.readByteNode_);
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleString.ReadByteNode create() {
         return new TruffleStringFactory.ReadByteNodeGen();
      }

      public static TruffleString.ReadByteNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.ReadByteNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.ReadByteNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value) {
            return TruffleString.ReadByteNode.doRead(
               arg0Value, arg1Value, arg2Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodesFactory.ReadByteNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.ReadCharUTF16Node.class)
   static final class ReadCharUTF16NodeGen extends TruffleString.ReadCharUTF16Node {
      private static final TruffleStringFactory.ReadCharUTF16NodeGen.Uncached UNCACHED = new TruffleStringFactory.ReadCharUTF16NodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.ToIndexableNode toIndexableNode_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile utf16S0Profile_;

      private ReadCharUTF16NodeGen() {
      }

      @Override
      public char execute(AbstractTruffleString arg0Value, int arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return TruffleString.ReadCharUTF16Node.doRead(arg0Value, arg1Value, this.toIndexableNode_, this.utf16S0Profile_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private char executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         char var6;
         try {
            int state_0 = this.state_0_;
            this.toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
            this.utf16S0Profile_ = ConditionProfile.create();
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = TruffleString.ReadCharUTF16Node.doRead(arg0Value, arg1Value, this.toIndexableNode_, this.utf16S0Profile_);
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

      public static TruffleString.ReadCharUTF16Node create() {
         return new TruffleStringFactory.ReadCharUTF16NodeGen();
      }

      public static TruffleString.ReadCharUTF16Node getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.ReadCharUTF16Node.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.ReadCharUTF16Node {
         @CompilerDirectives.TruffleBoundary
         @Override
         public char execute(AbstractTruffleString arg0Value, int arg1Value) {
            return TruffleString.ReadCharUTF16Node.doRead(arg0Value, arg1Value, TruffleString.ToIndexableNode.getUncached(), ConditionProfile.getUncached());
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

   @GeneratedBy(TruffleString.RegionEqualByteIndexNode.class)
   static final class RegionEqualByteIndexNodeGen extends TruffleString.RegionEqualByteIndexNode {
      private static final TruffleStringFactory.RegionEqualByteIndexNodeGen.Uncached UNCACHED = new TruffleStringFactory.RegionEqualByteIndexNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.RegionEqualByteIndexNodeGen.RegionEqualsData regionEquals_cache;

      private RegionEqualByteIndexNodeGen() {
      }

      @Override
      boolean execute(
         AbstractTruffleString arg0Value,
         int arg1Value,
         AbstractTruffleString arg2Value,
         int arg3Value,
         int arg4Value,
         byte[] arg5Value,
         TruffleString.Encoding arg6Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.RegionEqualByteIndexNodeGen.RegionEqualsData s0_ = this.regionEquals_cache;
            if (s0_ != null) {
               return this.regionEquals(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  arg5Value,
                  arg6Value,
                  s0_.toIndexableNodeA_,
                  s0_.toIndexableNodeB_,
                  s0_.getCodeRangeANode_,
                  s0_.getCodeRangeBNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
      }

      private boolean executeAndSpecialize(
         AbstractTruffleString arg0Value,
         int arg1Value,
         AbstractTruffleString arg2Value,
         int arg3Value,
         int arg4Value,
         byte[] arg5Value,
         TruffleString.Encoding arg6Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var12;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.RegionEqualByteIndexNodeGen.RegionEqualsData s0_ = super.insert(
               new TruffleStringFactory.RegionEqualByteIndexNodeGen.RegionEqualsData()
            );
            s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            VarHandle.storeStoreFence();
            this.regionEquals_cache = s0_;
            int var16;
            this.state_0_ = var16 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var12 = this.regionEquals(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               arg6Value,
               s0_.toIndexableNodeA_,
               s0_.toIndexableNodeB_,
               s0_.getCodeRangeANode_,
               s0_.getCodeRangeBNode_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var12;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static TruffleString.RegionEqualByteIndexNode create() {
         return new TruffleStringFactory.RegionEqualByteIndexNodeGen();
      }

      public static TruffleString.RegionEqualByteIndexNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.RegionEqualByteIndexNode.class)
      private static final class RegionEqualsData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeA_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeB_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode_;

         RegionEqualsData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.RegionEqualByteIndexNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.RegionEqualByteIndexNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         boolean execute(
            AbstractTruffleString arg0Value,
            int arg1Value,
            AbstractTruffleString arg2Value,
            int arg3Value,
            int arg4Value,
            byte[] arg5Value,
            TruffleString.Encoding arg6Value
         ) {
            return this.regionEquals(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               arg5Value,
               arg6Value,
               TruffleString.ToIndexableNode.getUncached(),
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached()
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

   @GeneratedBy(TruffleString.RegionEqualNode.class)
   static final class RegionEqualNodeGen extends TruffleString.RegionEqualNode {
      private static final TruffleStringFactory.RegionEqualNodeGen.Uncached UNCACHED = new TruffleStringFactory.RegionEqualNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.RegionEqualNodeGen.RegionEqualsData regionEquals_cache;

      private RegionEqualNodeGen() {
      }

      @Override
      public boolean execute(
         AbstractTruffleString arg0Value, int arg1Value, AbstractTruffleString arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value
      ) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.RegionEqualNodeGen.RegionEqualsData s0_ = this.regionEquals_cache;
            if (s0_ != null) {
               return TruffleString.RegionEqualNode.regionEquals(
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
                  s0_.getCodeRangeANode_,
                  s0_.getCodeRangeBNode_,
                  s0_.regionEqualsNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
      }

      private boolean executeAndSpecialize(
         AbstractTruffleString arg0Value, int arg1Value, AbstractTruffleString arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var11;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.RegionEqualNodeGen.RegionEqualsData s0_ = super.insert(new TruffleStringFactory.RegionEqualNodeGen.RegionEqualsData());
            s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodePointLengthANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.getCodePointLengthBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.regionEqualsNode_ = s0_.insertAccessor(TStringInternalNodesFactory.RegionEqualsNodeGen.create());
            VarHandle.storeStoreFence();
            this.regionEquals_cache = s0_;
            int var15;
            this.state_0_ = var15 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var11 = TruffleString.RegionEqualNode.regionEquals(
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
               s0_.getCodeRangeANode_,
               s0_.getCodeRangeBNode_,
               s0_.regionEqualsNode_
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

      public static TruffleString.RegionEqualNode create() {
         return new TruffleStringFactory.RegionEqualNodeGen();
      }

      public static TruffleString.RegionEqualNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.RegionEqualNode.class)
      private static final class RegionEqualsData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeA_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNodeB_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthANode_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthBNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode_;
         @Node.Child
         TStringInternalNodes.RegionEqualsNode regionEqualsNode_;

         RegionEqualsData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.RegionEqualNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.RegionEqualNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(
            AbstractTruffleString arg0Value, int arg1Value, AbstractTruffleString arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value
         ) {
            return TruffleString.RegionEqualNode.regionEquals(
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
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodesFactory.RegionEqualsNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.RepeatNode.class)
   static final class RepeatNodeGen extends TruffleString.RepeatNode {
      private static final TruffleStringFactory.RepeatNodeGen.Uncached UNCACHED = new TruffleStringFactory.RepeatNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.RepeatNodeGen.RepeatData repeat_cache;

      private RepeatNodeGen() {
      }

      @Override
      public TruffleString execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.RepeatNodeGen.RepeatData s0_ = this.repeat_cache;
            if (s0_ != null) {
               return this.repeat(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  s0_.asTruffleStringNode_,
                  s0_.toIndexableNode_,
                  s0_.getCodeRangeNode_,
                  s0_.getCodePointLengthNode_,
                  s0_.calcStringAttributesNode_,
                  s0_.brokenProfile_,
                  s0_.outOfMemoryProfile_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var8;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.RepeatNodeGen.RepeatData s0_ = super.insert(new TruffleStringFactory.RepeatNodeGen.RepeatData());
            s0_.asTruffleStringNode_ = s0_.insertAccessor(TruffleString.AsTruffleStringNode.create());
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.calcStringAttributesNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CalcStringAttributesNodeGen.create());
            s0_.brokenProfile_ = ConditionProfile.create();
            s0_.outOfMemoryProfile_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.repeat_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.repeat(
               arg0Value,
               arg1Value,
               arg2Value,
               s0_.asTruffleStringNode_,
               s0_.toIndexableNode_,
               s0_.getCodeRangeNode_,
               s0_.getCodePointLengthNode_,
               s0_.calcStringAttributesNode_,
               s0_.brokenProfile_,
               s0_.outOfMemoryProfile_
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

      public static TruffleString.RepeatNode create() {
         return new TruffleStringFactory.RepeatNodeGen();
      }

      public static TruffleString.RepeatNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.RepeatNode.class)
      private static final class RepeatData extends Node {
         @Node.Child
         TruffleString.AsTruffleStringNode asTruffleStringNode_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
         @Node.Child
         TStringInternalNodes.CalcStringAttributesNode calcStringAttributesNode_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile brokenProfile_;
         @CompilerDirectives.CompilationFinal
         BranchProfile outOfMemoryProfile_;

         RepeatData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.RepeatNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.RepeatNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value) {
            return this.repeat(
               arg0Value,
               arg1Value,
               arg2Value,
               TruffleString.AsTruffleStringNode.getUncached(),
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodes.CalcStringAttributesNode.getUncached(),
               ConditionProfile.getUncached(),
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

   @GeneratedBy(TruffleString.SubstringByteIndexNode.class)
   static final class SubstringByteIndexNodeGen extends TruffleString.SubstringByteIndexNode {
      private static final TruffleStringFactory.SubstringByteIndexNodeGen.Uncached UNCACHED = new TruffleStringFactory.SubstringByteIndexNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.SubstringByteIndexNodeGen.SubstringRawData substringRaw_cache;

      private SubstringByteIndexNodeGen() {
      }

      @Override
      public TruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && TruffleString.SubstringByteIndexNode.isSame(arg2Value, 0)) {
               return TruffleString.SubstringByteIndexNode.substringEmpty(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }

            if ((state_0 & 2) != 0) {
               TruffleStringFactory.SubstringByteIndexNodeGen.SubstringRawData s1_ = this.substringRaw_cache;
               if (s1_ != null && arg2Value != 0) {
                  return TruffleString.SubstringByteIndexNode.substringRaw(
                     arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s1_.toIndexableNode_, s1_.getCodeRangeANode_, s1_.substringNode_
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private TruffleString executeAndSpecialize(
         AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString s1_;
         try {
            int state_0 = this.state_0_;
            if (!TruffleString.SubstringByteIndexNode.isSame(arg2Value, 0)) {
               if (arg2Value == 0) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value
                  );
               }

               TruffleStringFactory.SubstringByteIndexNodeGen.SubstringRawData s1_x = super.insert(
                  new TruffleStringFactory.SubstringByteIndexNodeGen.SubstringRawData()
               );
               s1_x.toIndexableNode_ = s1_x.insertAccessor(TruffleString.ToIndexableNode.create());
               s1_x.getCodeRangeANode_ = s1_x.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
               s1_x.substringNode_ = s1_x.insertAccessor(TStringInternalNodesFactory.SubstringNodeGen.create());
               VarHandle.storeStoreFence();
               this.substringRaw_cache = s1_x;
               int var15;
               this.state_0_ = var15 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TruffleString.SubstringByteIndexNode.substringRaw(
                  arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s1_x.toIndexableNode_, s1_x.getCodeRangeANode_, s1_x.substringNode_
               );
            }

            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            s1_ = TruffleString.SubstringByteIndexNode.substringEmpty(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
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

      public static TruffleString.SubstringByteIndexNode create() {
         return new TruffleStringFactory.SubstringByteIndexNodeGen();
      }

      public static TruffleString.SubstringByteIndexNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.SubstringByteIndexNode.class)
      private static final class SubstringRawData extends Node {
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeANode_;
         @Node.Child
         TStringInternalNodes.SubstringNode substringNode_;

         SubstringRawData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.SubstringByteIndexNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.SubstringByteIndexNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            if (TruffleString.SubstringByteIndexNode.isSame(arg2Value, 0)) {
               return TruffleString.SubstringByteIndexNode.substringEmpty(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            } else if (arg2Value != 0) {
               return TruffleString.SubstringByteIndexNode.substringRaw(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  TruffleString.ToIndexableNode.getUncached(),
                  TStringInternalNodes.GetCodeRangeNode.getUncached(),
                  TStringInternalNodesFactory.SubstringNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.SubstringNode.class)
   static final class SubstringNodeGen extends TruffleString.SubstringNode {
      private static final TruffleStringFactory.SubstringNodeGen.Uncached UNCACHED = new TruffleStringFactory.SubstringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.SubstringNodeGen.SubstringData substring_cache;

      private SubstringNodeGen() {
      }

      @Override
      public TruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            TruffleStringFactory.SubstringNodeGen.SubstringData s0_ = this.substring_cache;
            if (s0_ != null) {
               return TruffleString.SubstringNode.substring(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  s0_.toIndexableNode_,
                  s0_.getCodeRangeANode_,
                  s0_.getCodePointLengthNode_,
                  s0_.translateIndexNode_,
                  s0_.substringNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private TruffleString executeAndSpecialize(
         AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var10;
         try {
            int state_0 = this.state_0_;
            TruffleStringFactory.SubstringNodeGen.SubstringData s0_ = super.insert(new TruffleStringFactory.SubstringNodeGen.SubstringData());
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
            s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
            s0_.translateIndexNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CodePointIndexToRawNodeGen.create());
            s0_.substringNode_ = s0_.insertAccessor(TStringInternalNodesFactory.SubstringNodeGen.create());
            VarHandle.storeStoreFence();
            this.substring_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var10 = TruffleString.SubstringNode.substring(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               s0_.toIndexableNode_,
               s0_.getCodeRangeANode_,
               s0_.getCodePointLengthNode_,
               s0_.translateIndexNode_,
               s0_.substringNode_
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

      public static TruffleString.SubstringNode create() {
         return new TruffleStringFactory.SubstringNodeGen();
      }

      public static TruffleString.SubstringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.SubstringNode.class)
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
         TStringInternalNodes.SubstringNode substringNode_;

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

      @GeneratedBy(TruffleString.SubstringNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.SubstringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            return TruffleString.SubstringNode.substring(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               TruffleString.ToIndexableNode.getUncached(),
               TStringInternalNodes.GetCodeRangeNode.getUncached(),
               TStringInternalNodes.GetCodePointLengthNode.getUncached(),
               TStringInternalNodesFactory.CodePointIndexToRawNodeGen.getUncached(),
               TStringInternalNodesFactory.SubstringNodeGen.getUncached()
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

   @GeneratedBy(TruffleString.SwitchEncodingNode.class)
   static final class SwitchEncodingNodeGen extends TruffleString.SwitchEncodingNode {
      private static final TruffleStringFactory.SwitchEncodingNodeGen.Uncached UNCACHED = new TruffleStringFactory.SwitchEncodingNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TStringInternalNodes.TransCodeNode transCodeNode;
      @Node.Child
      private TruffleString.AsTruffleStringNode compatibleMutable_asTruffleStringNode_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile transCode_cacheHit_;
      @Node.Child
      private TruffleString.ToIndexableNode transCode_toIndexableNode_;
      @Node.Child
      private TStringInternalNodes.GetCodePointLengthNode transCodeMutable_getCodePointLengthNode_;
      @Node.Child
      private TStringInternalNodes.GetCodeRangeNode transCodeMutable_getCodeRangeNode_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile transCodeMutable_isCompatibleProfile_;

      private SwitchEncodingNodeGen() {
      }

      @Override
      public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (arg0Value_.isCompatibleTo(arg1Value)) {
                  return TruffleString.SwitchEncodingNode.compatibleImmutable(arg0Value_, arg1Value);
               }
            }

            if ((state_0 & 2) != 0 && arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (arg0Value_.isCompatibleTo(arg1Value)) {
                  return TruffleString.SwitchEncodingNode.compatibleMutable(arg0Value_, arg1Value, this.compatibleMutable_asTruffleStringNode_);
               }
            }

            if ((state_0 & 4) != 0 && arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (!arg0Value_.isCompatibleTo(arg1Value)) {
                  return TruffleString.SwitchEncodingNode.transCode(
                     arg0Value_, arg1Value, this.transCode_cacheHit_, this.transCode_toIndexableNode_, this.transCodeNode
                  );
               }
            }

            if ((state_0 & 8) != 0 && arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (!arg0Value_.isCompatibleTo(arg1Value)) {
                  return this.transCodeMutable(
                     arg0Value_,
                     arg1Value,
                     this.transCodeMutable_getCodePointLengthNode_,
                     this.transCodeMutable_getCodeRangeNode_,
                     this.transCodeNode,
                     this.transCodeMutable_isCompatibleProfile_
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (arg0Value_.isCompatibleTo(arg1Value)) {
                  int var14;
                  this.state_0_ = var14 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return TruffleString.SwitchEncodingNode.compatibleImmutable(arg0Value_, arg1Value);
               }
            }

            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (arg0Value_.isCompatibleTo(arg1Value)) {
                  this.compatibleMutable_asTruffleStringNode_ = super.insert(TruffleString.AsTruffleStringNode.create());
                  int var13;
                  this.state_0_ = var13 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return TruffleString.SwitchEncodingNode.compatibleMutable(arg0Value_, arg1Value, this.compatibleMutable_asTruffleStringNode_);
               }
            }

            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (!arg0Value_.isCompatibleTo(arg1Value)) {
                  this.transCode_cacheHit_ = ConditionProfile.create();
                  this.transCode_toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
                  this.transCodeNode = super.insert(this.transCodeNode == null ? TStringInternalNodesFactory.TransCodeNodeGen.create() : this.transCodeNode);
                  int var12;
                  this.state_0_ = var12 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return TruffleString.SwitchEncodingNode.transCode(
                     arg0Value_, arg1Value, this.transCode_cacheHit_, this.transCode_toIndexableNode_, this.transCodeNode
                  );
               }
            }

            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (!arg0Value_.isCompatibleTo(arg1Value)) {
                  this.transCodeMutable_getCodePointLengthNode_ = super.insert(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                  this.transCodeMutable_getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                  this.transCodeNode = super.insert(this.transCodeNode == null ? TStringInternalNodesFactory.TransCodeNodeGen.create() : this.transCodeNode);
                  this.transCodeMutable_isCompatibleProfile_ = ConditionProfile.create();
                  int var11;
                  this.state_0_ = var11 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.transCodeMutable(
                     arg0Value_,
                     arg1Value,
                     this.transCodeMutable_getCodePointLengthNode_,
                     this.transCodeMutable_getCodeRangeNode_,
                     this.transCodeNode,
                     this.transCodeMutable_isCompatibleProfile_
                  );
               }
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
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      public static TruffleString.SwitchEncodingNode create() {
         return new TruffleStringFactory.SwitchEncodingNodeGen();
      }

      public static TruffleString.SwitchEncodingNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.SwitchEncodingNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.SwitchEncodingNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (arg0Value_.isCompatibleTo(arg1Value)) {
                  return TruffleString.SwitchEncodingNode.compatibleImmutable(arg0Value_, arg1Value);
               }
            }

            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (arg0Value_.isCompatibleTo(arg1Value)) {
                  return TruffleString.SwitchEncodingNode.compatibleMutable(arg0Value_, arg1Value, TruffleString.AsTruffleStringNode.getUncached());
               }
            }

            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               if (!arg0Value_.isCompatibleTo(arg1Value)) {
                  return TruffleString.SwitchEncodingNode.transCode(
                     arg0Value_,
                     arg1Value,
                     ConditionProfile.getUncached(),
                     TruffleString.ToIndexableNode.getUncached(),
                     TStringInternalNodesFactory.TransCodeNodeGen.getUncached()
                  );
               }
            }

            if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               if (!arg0Value_.isCompatibleTo(arg1Value)) {
                  return this.transCodeMutable(
                     arg0Value_,
                     arg1Value,
                     TStringInternalNodes.GetCodePointLengthNode.getUncached(),
                     TStringInternalNodes.GetCodeRangeNode.getUncached(),
                     TStringInternalNodesFactory.TransCodeNodeGen.getUncached(),
                     ConditionProfile.getUncached()
                  );
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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

   @GeneratedBy(TruffleString.ToIndexableNode.class)
   static final class ToIndexableNodeFactory {
      @GeneratedBy(TruffleString.ToIndexableNode.ToIndexableImplNode.class)
      static final class ToIndexableImplNodeGen extends TruffleString.ToIndexableNode.ToIndexableImplNode {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private ConditionProfile nativeUnsupported_materializeProfile_;
         @CompilerDirectives.CompilationFinal
         private ConditionProfile lazyLong_materializeProfile_;

         private ToIndexableImplNodeGen() {
         }

         @Override
         Object execute(AbstractTruffleString arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0 && arg1Value instanceof byte[]) {
                  byte[] arg1Value_ = (byte[])arg1Value;
                  return TruffleString.ToIndexableNode.ToIndexableImplNode.doByteArray(arg0Value, arg1Value_);
               }

               if ((state_0 & 6) != 0 && arg1Value instanceof AbstractTruffleString.NativePointer) {
                  AbstractTruffleString.NativePointer arg1Value_ = (AbstractTruffleString.NativePointer)arg1Value;
                  if ((state_0 & 2) != 0 && TStringGuards.isSupportedEncoding(arg0Value.encoding())) {
                     return TruffleString.ToIndexableNode.ToIndexableImplNode.doNativeSupported(arg0Value, arg1Value_);
                  }

                  if ((state_0 & 4) != 0 && !TStringGuards.isSupportedEncoding(arg0Value.encoding())) {
                     return TruffleString.ToIndexableNode.ToIndexableImplNode.doNativeUnsupported(
                        arg0Value, arg1Value_, this.nativeUnsupported_materializeProfile_
                     );
                  }
               }

               if ((state_0 & 8) != 0 && arg1Value instanceof AbstractTruffleString.LazyConcat) {
                  AbstractTruffleString.LazyConcat arg1Value_x = (AbstractTruffleString.LazyConcat)arg1Value;
                  return this.doLazyConcat(arg0Value, arg1Value_x);
               }

               if ((state_0 & 16) != 0 && arg1Value instanceof AbstractTruffleString.LazyLong) {
                  AbstractTruffleString.LazyLong arg1Value_x = (AbstractTruffleString.LazyLong)arg1Value;
                  return TruffleString.ToIndexableNode.ToIndexableImplNode.doLazyLong(arg0Value, arg1Value_x, this.lazyLong_materializeProfile_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }

         private Object executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               if (arg1Value instanceof byte[]) {
                  byte[] arg1Value_ = (byte[])arg1Value;
                  int var15;
                  this.state_0_ = var15 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return TruffleString.ToIndexableNode.ToIndexableImplNode.doByteArray(arg0Value, arg1Value_);
               } else {
                  if (arg1Value instanceof AbstractTruffleString.NativePointer) {
                     AbstractTruffleString.NativePointer arg1Value_ = (AbstractTruffleString.NativePointer)arg1Value;
                     if (TStringGuards.isSupportedEncoding(arg0Value.encoding())) {
                        int var14;
                        this.state_0_ = var14 = state_0 | 2;
                        lock.unlock();
                        hasLock = false;
                        return TruffleString.ToIndexableNode.ToIndexableImplNode.doNativeSupported(arg0Value, arg1Value_);
                     }

                     if (!TStringGuards.isSupportedEncoding(arg0Value.encoding())) {
                        this.nativeUnsupported_materializeProfile_ = ConditionProfile.create();
                        int var13;
                        this.state_0_ = var13 = state_0 | 4;
                        lock.unlock();
                        hasLock = false;
                        return TruffleString.ToIndexableNode.ToIndexableImplNode.doNativeUnsupported(
                           arg0Value, arg1Value_, this.nativeUnsupported_materializeProfile_
                        );
                     }
                  }

                  if (arg1Value instanceof AbstractTruffleString.LazyConcat) {
                     AbstractTruffleString.LazyConcat arg1Value_x = (AbstractTruffleString.LazyConcat)arg1Value;
                     int var11;
                     this.state_0_ = var11 = state_0 | 8;
                     lock.unlock();
                     hasLock = false;
                     return this.doLazyConcat(arg0Value, arg1Value_x);
                  } else if (!(arg1Value instanceof AbstractTruffleString.LazyLong)) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
                  } else {
                     AbstractTruffleString.LazyLong arg1Value_x = (AbstractTruffleString.LazyLong)arg1Value;
                     this.lazyLong_materializeProfile_ = ConditionProfile.create();
                     int var12;
                     this.state_0_ = var12 = state_0 | 16;
                     lock.unlock();
                     hasLock = false;
                     return TruffleString.ToIndexableNode.ToIndexableImplNode.doLazyLong(arg0Value, arg1Value_x, this.lazyLong_materializeProfile_);
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

         public static TruffleString.ToIndexableNode.ToIndexableImplNode create() {
            return new TruffleStringFactory.ToIndexableNodeFactory.ToIndexableImplNodeGen();
         }
      }
   }

   @GeneratedBy(TruffleString.ToJavaStringNode.class)
   static final class ToJavaStringNodeGen extends TruffleString.ToJavaStringNode {
      private static final TruffleStringFactory.ToJavaStringNodeGen.Uncached UNCACHED = new TruffleStringFactory.ToJavaStringNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleStringFactory.ToJavaStringNodeGen.UTF16Data uTF16_cache;
      @Node.Child
      private TruffleStringFactory.ToJavaStringNodeGen.MutableData mutable_cache;

      private ToJavaStringNodeGen() {
      }

      @Override
      public String execute(AbstractTruffleString arg0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            TruffleStringFactory.ToJavaStringNodeGen.UTF16Data s0_ = this.uTF16_cache;
            if (s0_ != null) {
               return TruffleString.ToJavaStringNode.doUTF16(arg0Value_, s0_.cacheHit_, s0_.toIndexableNode_, s0_.toJavaStringNode_);
            }
         }

         if ((state_0 & 2) != 0 && arg0Value instanceof MutableTruffleString) {
            MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
            TruffleStringFactory.ToJavaStringNodeGen.MutableData s1_ = this.mutable_cache;
            if (s1_ != null) {
               return TruffleString.ToJavaStringNode.doMutable(
                  arg0Value_, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.transCodeNode_, s1_.createJavaStringNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private String executeAndSpecialize(AbstractTruffleString arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         String var7;
         try {
            int state_0 = this.state_0_;
            if (!(arg0Value instanceof TruffleString)) {
               if (!(arg0Value instanceof MutableTruffleString)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
               }

               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               TruffleStringFactory.ToJavaStringNodeGen.MutableData s1_ = super.insert(new TruffleStringFactory.ToJavaStringNodeGen.MutableData());
               s1_.getCodePointLengthNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
               s1_.getCodeRangeNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
               s1_.transCodeNode_ = s1_.insertAccessor(TStringInternalNodesFactory.TransCodeNodeGen.create());
               s1_.createJavaStringNode_ = s1_.insertAccessor(TStringInternalNodesFactory.CreateJavaStringNodeGen.create());
               VarHandle.storeStoreFence();
               this.mutable_cache = s1_;
               int var12;
               this.state_0_ = var12 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return TruffleString.ToJavaStringNode.doMutable(
                  arg0Value_, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.transCodeNode_, s1_.createJavaStringNode_
               );
            }

            TruffleString arg0Value_ = (TruffleString)arg0Value;
            TruffleStringFactory.ToJavaStringNodeGen.UTF16Data s0_ = super.insert(new TruffleStringFactory.ToJavaStringNodeGen.UTF16Data());
            s0_.cacheHit_ = ConditionProfile.create();
            s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
            s0_.toJavaStringNode_ = s0_.insertAccessor(TStringInternalNodesFactory.ToJavaStringNodeGen.create());
            VarHandle.storeStoreFence();
            this.uTF16_cache = s0_;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = TruffleString.ToJavaStringNode.doUTF16(arg0Value_, s0_.cacheHit_, s0_.toIndexableNode_, s0_.toJavaStringNode_);
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

      public static TruffleString.ToJavaStringNode create() {
         return new TruffleStringFactory.ToJavaStringNodeGen();
      }

      public static TruffleString.ToJavaStringNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(TruffleString.ToJavaStringNode.class)
      private static final class MutableData extends Node {
         @Node.Child
         TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
         @Node.Child
         TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
         @Node.Child
         TStringInternalNodes.TransCodeNode transCodeNode_;
         @Node.Child
         TStringInternalNodes.CreateJavaStringNode createJavaStringNode_;

         MutableData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.ToJavaStringNode.class)
      private static final class UTF16Data extends Node {
         @CompilerDirectives.CompilationFinal
         ConditionProfile cacheHit_;
         @Node.Child
         TruffleString.ToIndexableNode toIndexableNode_;
         @Node.Child
         TStringInternalNodes.ToJavaStringNode toJavaStringNode_;

         UTF16Data() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(TruffleString.ToJavaStringNode.class)
      @DenyReplace
      private static final class Uncached extends TruffleString.ToJavaStringNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public String execute(AbstractTruffleString arg0Value) {
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               return TruffleString.ToJavaStringNode.doUTF16(
                  arg0Value_,
                  ConditionProfile.getUncached(),
                  TruffleString.ToIndexableNode.getUncached(),
                  TStringInternalNodesFactory.ToJavaStringNodeGen.getUncached()
               );
            } else if (arg0Value instanceof MutableTruffleString) {
               MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
               return TruffleString.ToJavaStringNode.doMutable(
                  arg0Value_,
                  TStringInternalNodes.GetCodePointLengthNode.getUncached(),
                  TStringInternalNodes.GetCodeRangeNode.getUncached(),
                  TStringInternalNodesFactory.TransCodeNodeGen.getUncached(),
                  TStringInternalNodesFactory.CreateJavaStringNodeGen.getUncached()
               );
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

   @GeneratedBy(TruffleString.WithMask.class)
   public static final class WithMaskFactory {
      @GeneratedBy(TruffleString.WithMask.CreateNode.class)
      static final class CreateNodeGen extends TruffleString.WithMask.CreateNode {
         private static final TruffleStringFactory.WithMaskFactory.CreateNodeGen.Uncached UNCACHED = new TruffleStringFactory.WithMaskFactory.CreateNodeGen.Uncached();

         private CreateNodeGen() {
         }

         @Override
         public TruffleString.WithMask execute(AbstractTruffleString arg0Value, byte[] arg1Value, TruffleString.Encoding arg2Value) {
            return this.doCreate(arg0Value, arg1Value, arg2Value);
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
         }

         public static TruffleString.WithMask.CreateNode create() {
            return new TruffleStringFactory.WithMaskFactory.CreateNodeGen();
         }

         public static TruffleString.WithMask.CreateNode getUncached() {
            return UNCACHED;
         }

         @GeneratedBy(TruffleString.WithMask.CreateNode.class)
         @DenyReplace
         private static final class Uncached extends TruffleString.WithMask.CreateNode {
            @CompilerDirectives.TruffleBoundary
            @Override
            public TruffleString.WithMask execute(AbstractTruffleString arg0Value, byte[] arg1Value, TruffleString.Encoding arg2Value) {
               return this.doCreate(arg0Value, arg1Value, arg2Value);
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

      @GeneratedBy(TruffleString.WithMask.CreateUTF16Node.class)
      static final class CreateUTF16NodeGen extends TruffleString.WithMask.CreateUTF16Node {
         private static final TruffleStringFactory.WithMaskFactory.CreateUTF16NodeGen.Uncached UNCACHED = new TruffleStringFactory.WithMaskFactory.CreateUTF16NodeGen.Uncached();

         private CreateUTF16NodeGen() {
         }

         @Override
         public TruffleString.WithMask execute(AbstractTruffleString arg0Value, char[] arg1Value) {
            return this.doCreate(arg0Value, arg1Value);
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
         }

         public static TruffleString.WithMask.CreateUTF16Node create() {
            return new TruffleStringFactory.WithMaskFactory.CreateUTF16NodeGen();
         }

         public static TruffleString.WithMask.CreateUTF16Node getUncached() {
            return UNCACHED;
         }

         @GeneratedBy(TruffleString.WithMask.CreateUTF16Node.class)
         @DenyReplace
         private static final class Uncached extends TruffleString.WithMask.CreateUTF16Node {
            @CompilerDirectives.TruffleBoundary
            @Override
            public TruffleString.WithMask execute(AbstractTruffleString arg0Value, char[] arg1Value) {
               return this.doCreate(arg0Value, arg1Value);
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

      @GeneratedBy(TruffleString.WithMask.CreateUTF32Node.class)
      static final class CreateUTF32NodeGen extends TruffleString.WithMask.CreateUTF32Node {
         private static final TruffleStringFactory.WithMaskFactory.CreateUTF32NodeGen.Uncached UNCACHED = new TruffleStringFactory.WithMaskFactory.CreateUTF32NodeGen.Uncached();

         private CreateUTF32NodeGen() {
         }

         @Override
         public TruffleString.WithMask execute(AbstractTruffleString arg0Value, int[] arg1Value) {
            return this.doCreate(arg0Value, arg1Value);
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
         }

         public static TruffleString.WithMask.CreateUTF32Node create() {
            return new TruffleStringFactory.WithMaskFactory.CreateUTF32NodeGen();
         }

         public static TruffleString.WithMask.CreateUTF32Node getUncached() {
            return UNCACHED;
         }

         @GeneratedBy(TruffleString.WithMask.CreateUTF32Node.class)
         @DenyReplace
         private static final class Uncached extends TruffleString.WithMask.CreateUTF32Node {
            @CompilerDirectives.TruffleBoundary
            @Override
            public TruffleString.WithMask execute(AbstractTruffleString arg0Value, int[] arg1Value) {
               return this.doCreate(arg0Value, arg1Value);
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
}
