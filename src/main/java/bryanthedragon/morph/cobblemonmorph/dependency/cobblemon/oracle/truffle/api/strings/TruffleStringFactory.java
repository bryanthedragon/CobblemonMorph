/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
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
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.InternalByteArray;
import com.oracle.truffle.api.strings.MutableTruffleString;
import com.oracle.truffle.api.strings.TStringAccessor;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TStringInternalNodes;
import com.oracle.truffle.api.strings.TStringInternalNodesFactory;
import com.oracle.truffle.api.strings.TStringOpsNodes;
import com.oracle.truffle.api.strings.TStringOpsNodesFactory;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringIterator;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TruffleString.class)
public final class TruffleStringFactory {

    @GeneratedBy(value=TruffleString.CreateBackwardCodePointIteratorNode.class)
    static final class CreateBackwardCodePointIteratorNodeGen
    extends TruffleString.CreateBackwardCodePointIteratorNode {
        private static final Uncached UNCACHED = new Uncached();
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
                return TruffleString.CreateBackwardCodePointIteratorNode.createIterator(arg0Value, arg1Value, arg2Value, this.toIndexableNode_, this.getCodeRangeANode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleStringIterator executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.ErrorHandling arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
                this.getCodeRangeANode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleStringIterator truffleStringIterator = TruffleString.CreateBackwardCodePointIteratorNode.createIterator(arg0Value, arg1Value, arg2Value, this.toIndexableNode_, this.getCodeRangeANode_);
                return truffleStringIterator;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.CreateBackwardCodePointIteratorNode create() {
            return new CreateBackwardCodePointIteratorNodeGen();
        }

        public static TruffleString.CreateBackwardCodePointIteratorNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.CreateBackwardCodePointIteratorNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.CreateBackwardCodePointIteratorNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleStringIterator execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.ErrorHandling arg2Value) {
                return TruffleString.CreateBackwardCodePointIteratorNode.createIterator(arg0Value, arg1Value, arg2Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached());
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

    @GeneratedBy(value=TruffleString.CreateCodePointIteratorNode.class)
    static final class CreateCodePointIteratorNodeGen
    extends TruffleString.CreateCodePointIteratorNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleStringIterator executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.ErrorHandling arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
                this.getCodeRangeANode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleStringIterator truffleStringIterator = TruffleString.CreateCodePointIteratorNode.createIterator(arg0Value, arg1Value, arg2Value, this.toIndexableNode_, this.getCodeRangeANode_);
                return truffleStringIterator;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.CreateCodePointIteratorNode create() {
            return new CreateCodePointIteratorNodeGen();
        }

        public static TruffleString.CreateCodePointIteratorNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.CreateCodePointIteratorNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.CreateCodePointIteratorNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleStringIterator execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.ErrorHandling arg2Value) {
                return TruffleString.CreateCodePointIteratorNode.createIterator(arg0Value, arg1Value, arg2Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached());
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

    @GeneratedBy(value=TruffleString.ForceEncodingNode.class)
    static final class ForceEncodingNodeGen
    extends TruffleString.ForceEncodingNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleString.AsTruffleStringNode compatibleMutable_asTruffleStringNode_;
        @Node.Child
        private ReinterpretData reinterpret_cache;

        private ForceEncodingNodeGen() {
        }

        @Override
        public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.Encoding arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                ReinterpretData s2_;
                AbstractTruffleString arg0Value_;
                if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString && TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value_ = (TruffleString)arg0Value, arg1Value, arg2Value)) {
                    return TruffleString.ForceEncodingNode.compatibleImmutable(arg0Value_, arg1Value, arg2Value);
                }
                if ((state_0 & 2) != 0 && arg0Value instanceof MutableTruffleString && TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value_ = (MutableTruffleString)arg0Value, arg1Value, arg2Value)) {
                    return TruffleString.ForceEncodingNode.compatibleMutable((MutableTruffleString)arg0Value_, arg1Value, arg2Value, this.compatibleMutable_asTruffleStringNode_);
                }
                if ((state_0 & 4) != 0 && (s2_ = this.reinterpret_cache) != null && !TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value, arg1Value, arg2Value)) {
                    return TruffleString.ForceEncodingNode.reinterpret(arg0Value, arg1Value, arg2Value, s2_.toIndexableNode_, s2_.managedProfile_, s2_.inflateProfile_, s2_.copyToByteArrayNode_, s2_.fromBufferWithStringCompactionNode_, s2_.fromNativePointerNode_);
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
                AbstractTruffleString arg0Value_;
                int state_0 = this.state_0_;
                if (arg0Value instanceof TruffleString && TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value_ = (TruffleString)arg0Value, arg1Value, arg2Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TruffleString.ForceEncodingNode.compatibleImmutable(arg0Value_, arg1Value, arg2Value);
                    return truffleString;
                }
                if (arg0Value instanceof MutableTruffleString && TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value_ = (MutableTruffleString)arg0Value, arg1Value, arg2Value)) {
                    this.compatibleMutable_asTruffleStringNode_ = super.insert(TruffleString.AsTruffleStringNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TruffleString.ForceEncodingNode.compatibleMutable((MutableTruffleString)arg0Value_, arg1Value, arg2Value, this.compatibleMutable_asTruffleStringNode_);
                    return truffleString;
                }
                if (!TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value, arg1Value, arg2Value)) {
                    ReinterpretData s2_ = super.insert(new ReinterpretData());
                    s2_.toIndexableNode_ = s2_.insertAccessor(TruffleString.ToIndexableNode.create());
                    s2_.managedProfile_ = ConditionProfile.create();
                    s2_.inflateProfile_ = ConditionProfile.create();
                    s2_.copyToByteArrayNode_ = s2_.insertAccessor(TruffleString.CopyToByteArrayNode.create());
                    s2_.fromBufferWithStringCompactionNode_ = s2_.insertAccessor(TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.create());
                    s2_.fromNativePointerNode_ = s2_.insertAccessor(TStringInternalNodesFactory.FromNativePointerNodeGen.create());
                    VarHandle.storeStoreFence();
                    this.reinterpret_cache = s2_;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TruffleString.ForceEncodingNode.reinterpret(arg0Value, arg1Value, arg2Value, s2_.toIndexableNode_, s2_.managedProfile_, s2_.inflateProfile_, s2_.copyToByteArrayNode_, s2_.fromBufferWithStringCompactionNode_, s2_.fromNativePointerNode_);
                    return truffleString;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value});
            }
            finally {
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
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleString.ForceEncodingNode create() {
            return new ForceEncodingNodeGen();
        }

        public static TruffleString.ForceEncodingNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.ForceEncodingNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.ForceEncodingNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.Encoding arg2Value) {
                AbstractTruffleString arg0Value_;
                if (arg0Value instanceof TruffleString && TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value_ = (TruffleString)arg0Value, arg1Value, arg2Value)) {
                    return TruffleString.ForceEncodingNode.compatibleImmutable(arg0Value_, arg1Value, arg2Value);
                }
                if (arg0Value instanceof MutableTruffleString && TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value_ = (MutableTruffleString)arg0Value, arg1Value, arg2Value)) {
                    return TruffleString.ForceEncodingNode.compatibleMutable((MutableTruffleString)arg0Value_, arg1Value, arg2Value, TruffleString.AsTruffleStringNode.getUncached());
                }
                if (!TruffleString.ForceEncodingNode.isCompatibleAndNotCompacted(arg0Value, arg1Value, arg2Value)) {
                    return TruffleString.ForceEncodingNode.reinterpret(arg0Value, arg1Value, arg2Value, TruffleString.ToIndexableNode.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), TruffleString.CopyToByteArrayNode.getUncached(), TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.getUncached(), TStringInternalNodesFactory.FromNativePointerNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value});
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

        @GeneratedBy(value=TruffleString.ForceEncodingNode.class)
        private static final class ReinterpretData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.SwitchEncodingNode.class)
    static final class SwitchEncodingNodeGen
    extends TruffleString.SwitchEncodingNode {
        private static final Uncached UNCACHED = new Uncached();
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
                AbstractTruffleString arg0Value_;
                if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString && (arg0Value_ = (TruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    return TruffleString.SwitchEncodingNode.compatibleImmutable((TruffleString)arg0Value_, arg1Value);
                }
                if ((state_0 & 2) != 0 && arg0Value instanceof MutableTruffleString && (arg0Value_ = (MutableTruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    return TruffleString.SwitchEncodingNode.compatibleMutable((MutableTruffleString)arg0Value_, arg1Value, this.compatibleMutable_asTruffleStringNode_);
                }
                if ((state_0 & 4) != 0 && arg0Value instanceof TruffleString && !(arg0Value_ = (TruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    return TruffleString.SwitchEncodingNode.transCode((TruffleString)arg0Value_, arg1Value, this.transCode_cacheHit_, this.transCode_toIndexableNode_, this.transCodeNode);
                }
                if ((state_0 & 8) != 0 && arg0Value instanceof MutableTruffleString && !(arg0Value_ = (MutableTruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    return this.transCodeMutable((MutableTruffleString)arg0Value_, arg1Value, this.transCodeMutable_getCodePointLengthNode_, this.transCodeMutable_getCodeRangeNode_, this.transCodeNode, this.transCodeMutable_isCompatibleProfile_);
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
                AbstractTruffleString arg0Value_;
                int state_0 = this.state_0_;
                if (arg0Value instanceof TruffleString && (arg0Value_ = (TruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TruffleString.SwitchEncodingNode.compatibleImmutable((TruffleString)arg0Value_, arg1Value);
                    return truffleString;
                }
                if (arg0Value instanceof MutableTruffleString && (arg0Value_ = (MutableTruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    this.compatibleMutable_asTruffleStringNode_ = super.insert(TruffleString.AsTruffleStringNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TruffleString.SwitchEncodingNode.compatibleMutable((MutableTruffleString)arg0Value_, arg1Value, this.compatibleMutable_asTruffleStringNode_);
                    return truffleString;
                }
                if (arg0Value instanceof TruffleString && !(arg0Value_ = (TruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    this.transCode_cacheHit_ = ConditionProfile.create();
                    this.transCode_toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
                    this.transCodeNode = super.insert(this.transCodeNode == null ? TStringInternalNodesFactory.TransCodeNodeGen.create() : this.transCodeNode);
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TruffleString.SwitchEncodingNode.transCode((TruffleString)arg0Value_, arg1Value, this.transCode_cacheHit_, this.transCode_toIndexableNode_, this.transCodeNode);
                    return truffleString;
                }
                if (arg0Value instanceof MutableTruffleString && !(arg0Value_ = (MutableTruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    this.transCodeMutable_getCodePointLengthNode_ = super.insert(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                    this.transCodeMutable_getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                    this.transCodeNode = super.insert(this.transCodeNode == null ? TStringInternalNodesFactory.TransCodeNodeGen.create() : this.transCodeNode);
                    this.transCodeMutable_isCompatibleProfile_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.transCodeMutable((MutableTruffleString)arg0Value_, arg1Value, this.transCodeMutable_getCodePointLengthNode_, this.transCodeMutable_getCodeRangeNode_, this.transCodeNode, this.transCodeMutable_isCompatibleProfile_);
                    return truffleString;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, new Object[]{arg0Value, arg1Value});
            }
            finally {
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
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleString.SwitchEncodingNode create() {
            return new SwitchEncodingNodeGen();
        }

        public static TruffleString.SwitchEncodingNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.SwitchEncodingNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.SwitchEncodingNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
                AbstractTruffleString arg0Value_;
                if (arg0Value instanceof TruffleString && (arg0Value_ = (TruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    return TruffleString.SwitchEncodingNode.compatibleImmutable((TruffleString)arg0Value_, arg1Value);
                }
                if (arg0Value instanceof MutableTruffleString && (arg0Value_ = (MutableTruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    return TruffleString.SwitchEncodingNode.compatibleMutable((MutableTruffleString)arg0Value_, arg1Value, TruffleString.AsTruffleStringNode.getUncached());
                }
                if (arg0Value instanceof TruffleString && !(arg0Value_ = (TruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    return TruffleString.SwitchEncodingNode.transCode((TruffleString)arg0Value_, arg1Value, ConditionProfile.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodesFactory.TransCodeNodeGen.getUncached());
                }
                if (arg0Value instanceof MutableTruffleString && !(arg0Value_ = (MutableTruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    return this.transCodeMutable((MutableTruffleString)arg0Value_, arg1Value, TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.TransCodeNodeGen.getUncached(), ConditionProfile.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, new Object[]{arg0Value, arg1Value});
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

    @GeneratedBy(value=TruffleString.ToJavaStringNode.class)
    static final class ToJavaStringNodeGen
    extends TruffleString.ToJavaStringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private UTF16Data uTF16_cache;
        @Node.Child
        private MutableData mutable_cache;

        private ToJavaStringNodeGen() {
        }

        @Override
        public String execute(AbstractTruffleString arg0Value) {
            AbstractTruffleString arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                UTF16Data s0_ = this.uTF16_cache;
                if (s0_ != null) {
                    return TruffleString.ToJavaStringNode.doUTF16(arg0Value_, s0_.cacheHit_, s0_.toIndexableNode_, s0_.toJavaStringNode_);
                }
            }
            if ((state_0 & 2) != 0 && arg0Value instanceof MutableTruffleString) {
                arg0Value_ = (MutableTruffleString)arg0Value;
                MutableData s1_ = this.mutable_cache;
                if (s1_ != null) {
                    return TruffleString.ToJavaStringNode.doMutable((MutableTruffleString)arg0Value_, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.transCodeNode_, s1_.createJavaStringNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        private String executeAndSpecialize(AbstractTruffleString arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arg0Value instanceof TruffleString) {
                    TruffleString arg0Value_ = (TruffleString)arg0Value;
                    UTF16Data s0_ = super.insert(new UTF16Data());
                    s0_.cacheHit_ = ConditionProfile.create();
                    s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                    s0_.toJavaStringNode_ = s0_.insertAccessor(TStringInternalNodesFactory.ToJavaStringNodeGen.create());
                    VarHandle.storeStoreFence();
                    this.uTF16_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    String string = TruffleString.ToJavaStringNode.doUTF16(arg0Value_, s0_.cacheHit_, s0_.toIndexableNode_, s0_.toJavaStringNode_);
                    return string;
                }
                if (arg0Value instanceof MutableTruffleString) {
                    MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
                    MutableData s1_ = super.insert(new MutableData());
                    s1_.getCodePointLengthNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                    s1_.getCodeRangeNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                    s1_.transCodeNode_ = s1_.insertAccessor(TStringInternalNodesFactory.TransCodeNodeGen.create());
                    s1_.createJavaStringNode_ = s1_.insertAccessor(TStringInternalNodesFactory.CreateJavaStringNodeGen.create());
                    VarHandle.storeStoreFence();
                    this.mutable_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    String string = TruffleString.ToJavaStringNode.doMutable(arg0Value_, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.transCodeNode_, s1_.createJavaStringNode_);
                    return string;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
            finally {
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
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleString.ToJavaStringNode create() {
            return new ToJavaStringNodeGen();
        }

        public static TruffleString.ToJavaStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.ToJavaStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.ToJavaStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public String execute(AbstractTruffleString arg0Value) {
                if (arg0Value instanceof TruffleString) {
                    TruffleString arg0Value_ = (TruffleString)arg0Value;
                    return TruffleString.ToJavaStringNode.doUTF16(arg0Value_, ConditionProfile.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodesFactory.ToJavaStringNodeGen.getUncached());
                }
                if (arg0Value instanceof MutableTruffleString) {
                    MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
                    return TruffleString.ToJavaStringNode.doMutable(arg0Value_, TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.TransCodeNodeGen.getUncached(), TStringInternalNodesFactory.CreateJavaStringNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

        @GeneratedBy(value=TruffleString.ToJavaStringNode.class)
        private static final class MutableData
        extends Node {
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

        @GeneratedBy(value=TruffleString.ToJavaStringNode.class)
        private static final class UTF16Data
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.CopyToNativeMemoryNode.class)
    static final class CopyToNativeMemoryNodeGen
    extends TruffleString.CopyToNativeMemoryNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private CopyData copy_cache;

        private CopyToNativeMemoryNodeGen() {
        }

        @Override
        public void execute(AbstractTruffleString arg0Value, int arg1Value, Object arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value) {
            CopyData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.copy_cache) != null) {
                this.doCopy(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.interopLibrary_, s0_.toIndexableNode_, s0_.utf16Profile_, s0_.utf16S0Profile_, s0_.utf32Profile_, s0_.utf32S0Profile_, s0_.utf32S1Profile_);
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, Object arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                CopyData s0_ = super.insert(new CopyData());
                s0_.interopLibrary_ = s0_.insertAccessor(TStringAccessor.createInteropLibrary());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.utf16Profile_ = ConditionProfile.create();
                s0_.utf16S0Profile_ = ConditionProfile.create();
                s0_.utf32Profile_ = ConditionProfile.create();
                s0_.utf32S0Profile_ = ConditionProfile.create();
                s0_.utf32S1Profile_ = ConditionProfile.create();
                VarHandle.storeStoreFence();
                this.copy_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                this.doCopy(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.interopLibrary_, s0_.toIndexableNode_, s0_.utf16Profile_, s0_.utf16S0Profile_, s0_.utf32Profile_, s0_.utf32S0Profile_, s0_.utf32S1Profile_);
                return;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.CopyToNativeMemoryNode create() {
            return new CopyToNativeMemoryNodeGen();
        }

        public static TruffleString.CopyToNativeMemoryNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.CopyToNativeMemoryNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.CopyToNativeMemoryNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void execute(AbstractTruffleString arg0Value, int arg1Value, Object arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value) {
                this.doCopy(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TStringAccessor.getUncachedInteropLibrary(), TruffleString.ToIndexableNode.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached());
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

        @GeneratedBy(value=TruffleString.CopyToNativeMemoryNode.class)
        private static final class CopyData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.CopyToByteArrayNode.class)
    static final class CopyToByteArrayNodeGen
    extends TruffleString.CopyToByteArrayNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private CopyData copy_cache;

        private CopyToByteArrayNodeGen() {
        }

        @Override
        public void execute(AbstractTruffleString arg0Value, int arg1Value, byte[] arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value) {
            CopyData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.copy_cache) != null) {
                this.doCopy(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.toIndexableNode_, s0_.utf16Profile_, s0_.utf16S0Profile_, s0_.utf32Profile_, s0_.utf32S0Profile_, s0_.utf32S1Profile_);
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, byte[] arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                CopyData s0_ = super.insert(new CopyData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.utf16Profile_ = ConditionProfile.create();
                s0_.utf16S0Profile_ = ConditionProfile.create();
                s0_.utf32Profile_ = ConditionProfile.create();
                s0_.utf32S0Profile_ = ConditionProfile.create();
                s0_.utf32S1Profile_ = ConditionProfile.create();
                VarHandle.storeStoreFence();
                this.copy_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                this.doCopy(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.toIndexableNode_, s0_.utf16Profile_, s0_.utf16S0Profile_, s0_.utf32Profile_, s0_.utf32S0Profile_, s0_.utf32S1Profile_);
                return;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.CopyToByteArrayNode create() {
            return new CopyToByteArrayNodeGen();
        }

        public static TruffleString.CopyToByteArrayNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.CopyToByteArrayNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.CopyToByteArrayNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void execute(AbstractTruffleString arg0Value, int arg1Value, byte[] arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value) {
                this.doCopy(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleString.ToIndexableNode.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached());
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

        @GeneratedBy(value=TruffleString.CopyToByteArrayNode.class)
        private static final class CopyData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.GetInternalNativePointerNode.class)
    static final class GetInternalNativePointerNodeGen
    extends TruffleString.GetInternalNativePointerNode {
        private static final Uncached UNCACHED = new Uncached();

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
            return new GetInternalNativePointerNodeGen();
        }

        public static TruffleString.GetInternalNativePointerNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.GetInternalNativePointerNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.GetInternalNativePointerNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

    @GeneratedBy(value=TruffleString.GetInternalByteArrayNode.class)
    static final class GetInternalByteArrayNodeGen
    extends TruffleString.GetInternalByteArrayNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private GetInternalByteArrayData getInternalByteArray_cache;

        private GetInternalByteArrayNodeGen() {
        }

        @Override
        public InternalByteArray execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            GetInternalByteArrayData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.getInternalByteArray_cache) != null) {
                return this.getInternalByteArray(arg0Value, arg1Value, s0_.toIndexableNode_, s0_.utf16Profile_, s0_.utf16S0Profile_, s0_.utf32Profile_, s0_.utf32S0Profile_, s0_.utf32S1Profile_, s0_.isByteArrayProfile_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private InternalByteArray executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                GetInternalByteArrayData s0_ = super.insert(new GetInternalByteArrayData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.utf16Profile_ = ConditionProfile.create();
                s0_.utf16S0Profile_ = ConditionProfile.create();
                s0_.utf32Profile_ = ConditionProfile.create();
                s0_.utf32S0Profile_ = ConditionProfile.create();
                s0_.utf32S1Profile_ = ConditionProfile.create();
                s0_.isByteArrayProfile_ = ConditionProfile.create();
                VarHandle.storeStoreFence();
                this.getInternalByteArray_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                InternalByteArray internalByteArray = this.getInternalByteArray(arg0Value, arg1Value, s0_.toIndexableNode_, s0_.utf16Profile_, s0_.utf16S0Profile_, s0_.utf32Profile_, s0_.utf32S0Profile_, s0_.utf32S1Profile_, s0_.isByteArrayProfile_);
                return internalByteArray;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.GetInternalByteArrayNode create() {
            return new GetInternalByteArrayNodeGen();
        }

        public static TruffleString.GetInternalByteArrayNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.GetInternalByteArrayNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.GetInternalByteArrayNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public InternalByteArray execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
                return this.getInternalByteArray(arg0Value, arg1Value, TruffleString.ToIndexableNode.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached());
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

        @GeneratedBy(value=TruffleString.GetInternalByteArrayNode.class)
        private static final class GetInternalByteArrayData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.ParseDoubleNode.class)
    static final class ParseDoubleNodeGen
    extends TruffleString.ParseDoubleNode {
        private static final Uncached UNCACHED = new Uncached();
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
            try {
                int state_0 = this.state_0_;
                if (TruffleString.ParseDoubleNode.isLazyLongSafeInteger(arg0Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    double d = TruffleString.ParseDoubleNode.doLazyLong(arg0Value);
                    return d;
                }
                if (!TruffleString.ParseDoubleNode.isLazyLongSafeInteger(arg0Value)) {
                    this.parseDouble_toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
                    this.parseDouble_parseDoubleNode_ = super.insert(TStringInternalNodesFactory.ParseDoubleNodeGen.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    double d = TruffleString.ParseDoubleNode.parseDouble(arg0Value, this.parseDouble_toIndexableNode_, this.parseDouble_parseDoubleNode_);
                    return d;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
            finally {
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
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleString.ParseDoubleNode create() {
            return new ParseDoubleNodeGen();
        }

        public static TruffleString.ParseDoubleNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.ParseDoubleNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.ParseDoubleNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double execute(AbstractTruffleString arg0Value) throws TruffleString.NumberFormatException {
                if (TruffleString.ParseDoubleNode.isLazyLongSafeInteger(arg0Value)) {
                    return TruffleString.ParseDoubleNode.doLazyLong(arg0Value);
                }
                if (!TruffleString.ParseDoubleNode.isLazyLongSafeInteger(arg0Value)) {
                    return TruffleString.ParseDoubleNode.parseDouble(arg0Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodesFactory.ParseDoubleNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

    @GeneratedBy(value=TruffleString.ParseLongNode.class)
    static final class ParseLongNodeGen
    extends TruffleString.ParseLongNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ParseData parse_cache;

        private ParseLongNodeGen() {
        }

        @Override
        public long execute(AbstractTruffleString arg0Value, int arg1Value) throws TruffleString.NumberFormatException {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                ParseData s1_;
                if ((state_0 & 1) != 0 && arg0Value.isLazyLong() && arg1Value == 10) {
                    return TruffleString.ParseLongNode.doLazyLong(arg0Value, arg1Value);
                }
                if (!((state_0 & 2) == 0 || (s1_ = this.parse_cache) == null || arg0Value.isLazyLong() && arg1Value == 10)) {
                    return TruffleString.ParseLongNode.doParse(arg0Value, arg1Value, s1_.toIndexableNode_, s1_.getCodeRangeANode_, s1_.parseLongNode_, s1_.radixProfile_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        private long executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value) throws TruffleString.NumberFormatException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arg0Value.isLazyLong() && arg1Value == 10) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    long l = TruffleString.ParseLongNode.doLazyLong(arg0Value, arg1Value);
                    return l;
                }
                if (!arg0Value.isLazyLong() || arg1Value != 10) {
                    ParseData s1_ = super.insert(new ParseData());
                    s1_.toIndexableNode_ = s1_.insertAccessor(TruffleString.ToIndexableNode.create());
                    s1_.getCodeRangeANode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                    s1_.parseLongNode_ = s1_.insertAccessor(TStringInternalNodesFactory.ParseLongNodeGen.create());
                    s1_.radixProfile_ = IntValueProfile.createIdentityProfile();
                    VarHandle.storeStoreFence();
                    this.parse_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    long l = TruffleString.ParseLongNode.doParse(arg0Value, arg1Value, s1_.toIndexableNode_, s1_.getCodeRangeANode_, s1_.parseLongNode_, s1_.radixProfile_);
                    return l;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
            finally {
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
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleString.ParseLongNode create() {
            return new ParseLongNodeGen();
        }

        public static TruffleString.ParseLongNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.ParseLongNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.ParseLongNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long execute(AbstractTruffleString arg0Value, int arg1Value) throws TruffleString.NumberFormatException {
                if (arg0Value.isLazyLong() && arg1Value == 10) {
                    return TruffleString.ParseLongNode.doLazyLong(arg0Value, arg1Value);
                }
                if (!arg0Value.isLazyLong() || arg1Value != 10) {
                    return TruffleString.ParseLongNode.doParse(arg0Value, arg1Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.ParseLongNodeGen.getUncached(), IntValueProfile.getUncached());
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

        @GeneratedBy(value=TruffleString.ParseLongNode.class)
        private static final class ParseData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.ParseIntNode.class)
    static final class ParseIntNodeGen
    extends TruffleString.ParseIntNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile lazyLong_errorProfile_;
        @Node.Child
        private ParseData parse_cache;

        private ParseIntNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, int arg1Value) throws TruffleString.NumberFormatException {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                ParseData s1_;
                if ((state_0 & 1) != 0 && arg0Value.isLazyLong() && arg1Value == 10) {
                    return TruffleString.ParseIntNode.doLazyLong(arg0Value, arg1Value, this.lazyLong_errorProfile_);
                }
                if (!((state_0 & 2) == 0 || (s1_ = this.parse_cache) == null || arg0Value.isLazyLong() && arg1Value == 10)) {
                    return TruffleString.ParseIntNode.doParse(arg0Value, arg1Value, s1_.toIndexableNode_, s1_.getCodeRangeANode_, s1_.parseIntNode_, s1_.radixProfile_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value) throws TruffleString.NumberFormatException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arg0Value.isLazyLong() && arg1Value == 10) {
                    this.lazyLong_errorProfile_ = BranchProfile.create();
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleString.ParseIntNode.doLazyLong(arg0Value, arg1Value, this.lazyLong_errorProfile_);
                    return n;
                }
                if (!arg0Value.isLazyLong() || arg1Value != 10) {
                    ParseData s1_ = super.insert(new ParseData());
                    s1_.toIndexableNode_ = s1_.insertAccessor(TruffleString.ToIndexableNode.create());
                    s1_.getCodeRangeANode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                    s1_.parseIntNode_ = s1_.insertAccessor(TStringInternalNodesFactory.ParseIntNodeGen.create());
                    s1_.radixProfile_ = IntValueProfile.createIdentityProfile();
                    VarHandle.storeStoreFence();
                    this.parse_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleString.ParseIntNode.doParse(arg0Value, arg1Value, s1_.toIndexableNode_, s1_.getCodeRangeANode_, s1_.parseIntNode_, s1_.radixProfile_);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
            finally {
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
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleString.ParseIntNode create() {
            return new ParseIntNodeGen();
        }

        public static TruffleString.ParseIntNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.ParseIntNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.ParseIntNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value) throws TruffleString.NumberFormatException {
                if (arg0Value.isLazyLong() && arg1Value == 10) {
                    return TruffleString.ParseIntNode.doLazyLong(arg0Value, arg1Value, BranchProfile.getUncached());
                }
                if (!arg0Value.isLazyLong() || arg1Value != 10) {
                    return TruffleString.ParseIntNode.doParse(arg0Value, arg1Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.ParseIntNodeGen.getUncached(), IntValueProfile.getUncached());
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

        @GeneratedBy(value=TruffleString.ParseIntNode.class)
        private static final class ParseData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.EqualNode.class)
    static final class EqualNodeGen
    extends TruffleString.EqualNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private CheckData check_cache;

        private EqualNodeGen() {
        }

        @Override
        public boolean execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                CheckData s1_;
                if ((state_0 & 1) != 0 && TStringGuards.identical(arg0Value, arg1Value)) {
                    return TruffleString.EqualNode.sameObject(arg0Value, arg1Value, arg2Value);
                }
                if ((state_0 & 2) != 0 && (s1_ = this.check_cache) != null && !TStringGuards.identical(arg0Value, arg1Value)) {
                    return this.check(arg0Value, arg1Value, arg2Value, s1_.toIndexableNodeA_, s1_.toIndexableNodeB_, s1_.getCodeRangeANode_, s1_.getCodeRangeBNode_, s1_.lengthAndCodeRangeCheckProfile_, s1_.compareHashProfile_, s1_.checkFirstByteProfile_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        private boolean executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.identical(arg0Value, arg1Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = TruffleString.EqualNode.sameObject(arg0Value, arg1Value, arg2Value);
                    return bl;
                }
                if (!TStringGuards.identical(arg0Value, arg1Value)) {
                    CheckData s1_ = super.insert(new CheckData());
                    s1_.toIndexableNodeA_ = s1_.insertAccessor(TruffleString.ToIndexableNode.create());
                    s1_.toIndexableNodeB_ = s1_.insertAccessor(TruffleString.ToIndexableNode.create());
                    s1_.getCodeRangeANode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                    s1_.getCodeRangeBNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                    s1_.lengthAndCodeRangeCheckProfile_ = ConditionProfile.create();
                    s1_.compareHashProfile_ = BranchProfile.create();
                    s1_.checkFirstByteProfile_ = ConditionProfile.create();
                    VarHandle.storeStoreFence();
                    this.check_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.check(arg0Value, arg1Value, arg2Value, s1_.toIndexableNodeA_, s1_.toIndexableNodeB_, s1_.getCodeRangeANode_, s1_.getCodeRangeBNode_, s1_.lengthAndCodeRangeCheckProfile_, s1_.compareHashProfile_, s1_.checkFirstByteProfile_);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value});
            }
            finally {
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
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleString.EqualNode create() {
            return new EqualNodeGen();
        }

        public static TruffleString.EqualNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.EqualNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.EqualNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
                if (TStringGuards.identical(arg0Value, arg1Value)) {
                    return TruffleString.EqualNode.sameObject(arg0Value, arg1Value, arg2Value);
                }
                if (!TStringGuards.identical(arg0Value, arg1Value)) {
                    return this.check(arg0Value, arg1Value, arg2Value, TruffleString.ToIndexableNode.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), ConditionProfile.getUncached(), BranchProfile.getUncached(), ConditionProfile.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value});
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

        @GeneratedBy(value=TruffleString.EqualNode.class)
        private static final class CheckData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.SubstringByteIndexNode.class)
    static final class SubstringByteIndexNodeGen
    extends TruffleString.SubstringByteIndexNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private SubstringRawData substringRaw_cache;

        private SubstringByteIndexNodeGen() {
        }

        @Override
        public TruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                SubstringRawData s1_;
                if ((state_0 & 1) != 0 && TruffleString.SubstringByteIndexNode.isSame(arg2Value, 0)) {
                    return TruffleString.SubstringByteIndexNode.substringEmpty(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if ((state_0 & 2) != 0 && (s1_ = this.substringRaw_cache) != null && arg2Value != 0) {
                    return TruffleString.SubstringByteIndexNode.substringRaw(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s1_.toIndexableNode_, s1_.getCodeRangeANode_, s1_.substringNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TruffleString.SubstringByteIndexNode.isSame(arg2Value, 0)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TruffleString.SubstringByteIndexNode.substringEmpty(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                    return truffleString;
                }
                if (arg2Value != 0) {
                    SubstringRawData s1_ = super.insert(new SubstringRawData());
                    s1_.toIndexableNode_ = s1_.insertAccessor(TruffleString.ToIndexableNode.create());
                    s1_.getCodeRangeANode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                    s1_.substringNode_ = s1_.insertAccessor(TStringInternalNodesFactory.SubstringNodeGen.create());
                    VarHandle.storeStoreFence();
                    this.substringRaw_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TruffleString.SubstringByteIndexNode.substringRaw(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s1_.toIndexableNode_, s1_.getCodeRangeANode_, s1_.substringNode_);
                    return truffleString;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value});
            }
            finally {
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
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleString.SubstringByteIndexNode create() {
            return new SubstringByteIndexNodeGen();
        }

        public static TruffleString.SubstringByteIndexNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.SubstringByteIndexNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.SubstringByteIndexNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
                if (TruffleString.SubstringByteIndexNode.isSame(arg2Value, 0)) {
                    return TruffleString.SubstringByteIndexNode.substringEmpty(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if (arg2Value != 0) {
                    return TruffleString.SubstringByteIndexNode.substringRaw(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.SubstringNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value});
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

        @GeneratedBy(value=TruffleString.SubstringByteIndexNode.class)
        private static final class SubstringRawData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.SubstringNode.class)
    static final class SubstringNodeGen
    extends TruffleString.SubstringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private SubstringData substring_cache;

        private SubstringNodeGen() {
        }

        @Override
        public TruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            SubstringData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.substring_cache) != null) {
                return TruffleString.SubstringNode.substring(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodeRangeANode_, s0_.getCodePointLengthNode_, s0_.translateIndexNode_, s0_.substringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                SubstringData s0_ = super.insert(new SubstringData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.translateIndexNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CodePointIndexToRawNodeGen.create());
                s0_.substringNode_ = s0_.insertAccessor(TStringInternalNodesFactory.SubstringNodeGen.create());
                VarHandle.storeStoreFence();
                this.substring_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = TruffleString.SubstringNode.substring(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodeRangeANode_, s0_.getCodePointLengthNode_, s0_.translateIndexNode_, s0_.substringNode_);
                return truffleString;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.SubstringNode create() {
            return new SubstringNodeGen();
        }

        public static TruffleString.SubstringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.SubstringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.SubstringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
                return TruffleString.SubstringNode.substring(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodesFactory.CodePointIndexToRawNodeGen.getUncached(), TStringInternalNodesFactory.SubstringNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.SubstringNode.class)
        private static final class SubstringData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.RepeatNode.class)
    static final class RepeatNodeGen
    extends TruffleString.RepeatNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private RepeatData repeat_cache;

        private RepeatNodeGen() {
        }

        @Override
        public TruffleString execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value) {
            RepeatData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.repeat_cache) != null) {
                return this.repeat(arg0Value, arg1Value, arg2Value, s0_.asTruffleStringNode_, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.getCodePointLengthNode_, s0_.calcStringAttributesNode_, s0_.brokenProfile_, s0_.outOfMemoryProfile_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                RepeatData s0_ = super.insert(new RepeatData());
                s0_.asTruffleStringNode_ = s0_.insertAccessor(TruffleString.AsTruffleStringNode.create());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.calcStringAttributesNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CalcStringAttributesNodeGen.create());
                s0_.brokenProfile_ = ConditionProfile.create();
                s0_.outOfMemoryProfile_ = BranchProfile.create();
                VarHandle.storeStoreFence();
                this.repeat_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.repeat(arg0Value, arg1Value, arg2Value, s0_.asTruffleStringNode_, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.getCodePointLengthNode_, s0_.calcStringAttributesNode_, s0_.brokenProfile_, s0_.outOfMemoryProfile_);
                return truffleString;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.RepeatNode create() {
            return new RepeatNodeGen();
        }

        public static TruffleString.RepeatNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.RepeatNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.RepeatNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value) {
                return this.repeat(arg0Value, arg1Value, arg2Value, TruffleString.AsTruffleStringNode.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.CalcStringAttributesNode.getUncached(), ConditionProfile.getUncached(), BranchProfile.getUncached());
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

        @GeneratedBy(value=TruffleString.RepeatNode.class)
        private static final class RepeatData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.ConcatNode.class)
    static final class ConcatNodeGen
    extends TruffleString.ConcatNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private AEmptyMutableData aEmptyMutable_cache;
        @Node.Child
        private BEmptyMutableData bEmptyMutable_cache;
        @Node.Child
        private ConcatData concat_cache;

        private ConcatNodeGen() {
        }

        @Override
        public TruffleString execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value, boolean arg3Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 3) != 0) {
                    AbstractTruffleString arg1Value_;
                    if ((state_0 & 1) != 0 && arg1Value instanceof TruffleString) {
                        arg1Value_ = (TruffleString)arg1Value;
                        if (TStringGuards.isEmpty(arg0Value)) {
                            return TruffleString.ConcatNode.aEmpty(arg0Value, arg1Value_, arg2Value, arg3Value);
                        }
                    }
                    if ((state_0 & 2) != 0 && arg1Value instanceof MutableTruffleString) {
                        arg1Value_ = (MutableTruffleString)arg1Value;
                        AEmptyMutableData s1_ = this.aEmptyMutable_cache;
                        if (s1_ != null && TStringGuards.isEmpty(arg0Value)) {
                            return TruffleString.ConcatNode.aEmptyMutable(arg0Value, (MutableTruffleString)arg1Value_, arg2Value, arg3Value, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.fromBufferWithStringCompactionNode_);
                        }
                    }
                }
                if ((state_0 & 0x1C) != 0) {
                    ConcatData s4_;
                    AbstractTruffleString arg0Value_;
                    if ((state_0 & 4) != 0 && arg0Value instanceof TruffleString) {
                        arg0Value_ = (TruffleString)arg0Value;
                        if (TStringGuards.isEmpty(arg1Value)) {
                            return TruffleString.ConcatNode.bEmpty(arg0Value_, arg1Value, arg2Value, arg3Value);
                        }
                    }
                    if ((state_0 & 8) != 0 && arg0Value instanceof MutableTruffleString) {
                        arg0Value_ = (MutableTruffleString)arg0Value;
                        BEmptyMutableData s3_ = this.bEmptyMutable_cache;
                        if (s3_ != null && TStringGuards.isEmpty(arg1Value)) {
                            return TruffleString.ConcatNode.bEmptyMutable((MutableTruffleString)arg0Value_, arg1Value, arg2Value, arg3Value, s3_.getCodePointLengthNode_, s3_.getCodeRangeNode_, s3_.fromBufferWithStringCompactionNode_);
                        }
                    }
                    if ((state_0 & 0x10) != 0 && (s4_ = this.concat_cache) != null && !TStringGuards.isEmpty(arg0Value) && !TStringGuards.isEmpty(arg1Value)) {
                        return TruffleString.ConcatNode.doConcat(arg0Value, arg1Value, arg2Value, arg3Value, s4_.getCodeRangeANode_, s4_.getCodeRangeBNode_, s4_.getStrideNode_, s4_.concatEagerNode_, s4_.asTruffleStringANode_, s4_.asTruffleStringBNode_, s4_.outOfMemoryProfile_, s4_.lazyProfile_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value, boolean arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                AbstractTruffleString arg0Value_;
                AbstractTruffleString arg1Value_;
                int state_0 = this.state_0_;
                if (arg1Value instanceof TruffleString) {
                    arg1Value_ = (TruffleString)arg1Value;
                    if (TStringGuards.isEmpty(arg0Value)) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString = TruffleString.ConcatNode.aEmpty(arg0Value, arg1Value_, arg2Value, arg3Value);
                        return truffleString;
                    }
                }
                if (arg1Value instanceof MutableTruffleString) {
                    arg1Value_ = (MutableTruffleString)arg1Value;
                    if (TStringGuards.isEmpty(arg0Value)) {
                        AEmptyMutableData s1_ = super.insert(new AEmptyMutableData());
                        s1_.getCodePointLengthNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                        s1_.getCodeRangeNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                        s1_.fromBufferWithStringCompactionNode_ = s1_.insertAccessor(TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.create());
                        VarHandle.storeStoreFence();
                        this.aEmptyMutable_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString = TruffleString.ConcatNode.aEmptyMutable(arg0Value, (MutableTruffleString)arg1Value_, arg2Value, arg3Value, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.fromBufferWithStringCompactionNode_);
                        return truffleString;
                    }
                }
                if (arg0Value instanceof TruffleString) {
                    arg0Value_ = (TruffleString)arg0Value;
                    if (TStringGuards.isEmpty(arg1Value)) {
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        TruffleString s1_ = TruffleString.ConcatNode.bEmpty(arg0Value_, arg1Value, arg2Value, arg3Value);
                        return s1_;
                    }
                }
                if (arg0Value instanceof MutableTruffleString) {
                    arg0Value_ = (MutableTruffleString)arg0Value;
                    if (TStringGuards.isEmpty(arg1Value)) {
                        BEmptyMutableData s3_ = super.insert(new BEmptyMutableData());
                        s3_.getCodePointLengthNode_ = s3_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                        s3_.getCodeRangeNode_ = s3_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                        s3_.fromBufferWithStringCompactionNode_ = s3_.insertAccessor(TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.create());
                        VarHandle.storeStoreFence();
                        this.bEmptyMutable_cache = s3_;
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString = TruffleString.ConcatNode.bEmptyMutable((MutableTruffleString)arg0Value_, arg1Value, arg2Value, arg3Value, s3_.getCodePointLengthNode_, s3_.getCodeRangeNode_, s3_.fromBufferWithStringCompactionNode_);
                        return truffleString;
                    }
                }
                if (!TStringGuards.isEmpty(arg0Value) && !TStringGuards.isEmpty(arg1Value)) {
                    ConcatData s4_ = super.insert(new ConcatData());
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
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TruffleString.ConcatNode.doConcat(arg0Value, arg1Value, arg2Value, arg3Value, s4_.getCodeRangeANode_, s4_.getCodeRangeBNode_, s4_.getStrideNode_, s4_.concatEagerNode_, s4_.asTruffleStringANode_, s4_.asTruffleStringBNode_, s4_.outOfMemoryProfile_, s4_.lazyProfile_);
                    return truffleString;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value});
            }
            finally {
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
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleString.ConcatNode create() {
            return new ConcatNodeGen();
        }

        public static TruffleString.ConcatNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.ConcatNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.ConcatNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value, boolean arg3Value) {
                AbstractTruffleString arg0Value_;
                AbstractTruffleString arg1Value_;
                if (arg1Value instanceof TruffleString) {
                    arg1Value_ = (TruffleString)arg1Value;
                    if (TStringGuards.isEmpty(arg0Value)) {
                        return TruffleString.ConcatNode.aEmpty(arg0Value, arg1Value_, arg2Value, arg3Value);
                    }
                }
                if (arg1Value instanceof MutableTruffleString) {
                    arg1Value_ = (MutableTruffleString)arg1Value;
                    if (TStringGuards.isEmpty(arg0Value)) {
                        return TruffleString.ConcatNode.aEmptyMutable(arg0Value, (MutableTruffleString)arg1Value_, arg2Value, arg3Value, TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.getUncached());
                    }
                }
                if (arg0Value instanceof TruffleString) {
                    arg0Value_ = (TruffleString)arg0Value;
                    if (TStringGuards.isEmpty(arg1Value)) {
                        return TruffleString.ConcatNode.bEmpty(arg0Value_, arg1Value, arg2Value, arg3Value);
                    }
                }
                if (arg0Value instanceof MutableTruffleString) {
                    arg0Value_ = (MutableTruffleString)arg0Value;
                    if (TStringGuards.isEmpty(arg1Value)) {
                        return TruffleString.ConcatNode.bEmptyMutable((MutableTruffleString)arg0Value_, arg1Value, arg2Value, arg3Value, TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.getUncached());
                    }
                }
                if (!TStringGuards.isEmpty(arg0Value) && !TStringGuards.isEmpty(arg1Value)) {
                    return TruffleString.ConcatNode.doConcat(arg0Value, arg1Value, arg2Value, arg3Value, TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.StrideFromCodeRangeNodeGen.getUncached(), TStringInternalNodesFactory.ConcatEagerNodeGen.getUncached(), TruffleString.AsTruffleStringNode.getUncached(), TruffleString.AsTruffleStringNode.getUncached(), BranchProfile.getUncached(), ConditionProfile.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value});
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

        @GeneratedBy(value=TruffleString.ConcatNode.class)
        private static final class ConcatData
        extends Node {
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

        @GeneratedBy(value=TruffleString.ConcatNode.class)
        private static final class BEmptyMutableData
        extends Node {
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

        @GeneratedBy(value=TruffleString.ConcatNode.class)
        private static final class AEmptyMutableData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.RegionEqualByteIndexNode.class)
    static final class RegionEqualByteIndexNodeGen
    extends TruffleString.RegionEqualByteIndexNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private RegionEqualsData regionEquals_cache;

        private RegionEqualByteIndexNodeGen() {
        }

        @Override
        boolean execute(AbstractTruffleString arg0Value, int arg1Value, AbstractTruffleString arg2Value, int arg3Value, int arg4Value, byte[] arg5Value, TruffleString.Encoding arg6Value) {
            RegionEqualsData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.regionEquals_cache) != null) {
                return this.regionEquals(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, AbstractTruffleString arg2Value, int arg3Value, int arg4Value, byte[] arg5Value, TruffleString.Encoding arg6Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                RegionEqualsData s0_ = super.insert(new RegionEqualsData());
                s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                VarHandle.storeStoreFence();
                this.regionEquals_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = this.regionEquals(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_);
                return bl;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.RegionEqualByteIndexNode create() {
            return new RegionEqualByteIndexNodeGen();
        }

        public static TruffleString.RegionEqualByteIndexNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.RegionEqualByteIndexNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.RegionEqualByteIndexNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            boolean execute(AbstractTruffleString arg0Value, int arg1Value, AbstractTruffleString arg2Value, int arg3Value, int arg4Value, byte[] arg5Value, TruffleString.Encoding arg6Value) {
                return this.regionEquals(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TruffleString.ToIndexableNode.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached());
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

        @GeneratedBy(value=TruffleString.RegionEqualByteIndexNode.class)
        private static final class RegionEqualsData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.RegionEqualNode.class)
    static final class RegionEqualNodeGen
    extends TruffleString.RegionEqualNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private RegionEqualsData regionEquals_cache;

        private RegionEqualNodeGen() {
        }

        @Override
        public boolean execute(AbstractTruffleString arg0Value, int arg1Value, AbstractTruffleString arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value) {
            RegionEqualsData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.regionEquals_cache) != null) {
                return TruffleString.RegionEqualNode.regionEquals(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodePointLengthANode_, s0_.getCodePointLengthBNode_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_, s0_.regionEqualsNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, AbstractTruffleString arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                RegionEqualsData s0_ = super.insert(new RegionEqualsData());
                s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodePointLengthANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.getCodePointLengthBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.regionEqualsNode_ = s0_.insertAccessor(TStringInternalNodesFactory.RegionEqualsNodeGen.create());
                VarHandle.storeStoreFence();
                this.regionEquals_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = TruffleString.RegionEqualNode.regionEquals(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodePointLengthANode_, s0_.getCodePointLengthBNode_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_, s0_.regionEqualsNode_);
                return bl;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.RegionEqualNode create() {
            return new RegionEqualNodeGen();
        }

        public static TruffleString.RegionEqualNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.RegionEqualNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.RegionEqualNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean execute(AbstractTruffleString arg0Value, int arg1Value, AbstractTruffleString arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value) {
                return TruffleString.RegionEqualNode.regionEquals(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleString.ToIndexableNode.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.RegionEqualsNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.RegionEqualNode.class)
        private static final class RegionEqualsData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.CompareIntsUTF32Node.class)
    static final class CompareIntsUTF32NodeGen
    extends TruffleString.CompareIntsUTF32Node {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private CompareData compare_cache;

        private CompareIntsUTF32NodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value) {
            CompareData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.compare_cache) != null) {
                return this.compare(arg0Value, arg1Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                CompareData s0_ = super.insert(new CompareData());
                s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                VarHandle.storeStoreFence();
                this.compare_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = this.compare(arg0Value, arg1Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.CompareIntsUTF32Node create() {
            return new CompareIntsUTF32NodeGen();
        }

        public static TruffleString.CompareIntsUTF32Node getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.CompareIntsUTF32Node.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.CompareIntsUTF32Node {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value) {
                return this.compare(arg0Value, arg1Value, TruffleString.ToIndexableNode.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached());
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

        @GeneratedBy(value=TruffleString.CompareIntsUTF32Node.class)
        private static final class CompareData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.CompareCharsUTF16Node.class)
    static final class CompareCharsUTF16NodeGen
    extends TruffleString.CompareCharsUTF16Node {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private CompareData compare_cache;

        private CompareCharsUTF16NodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value) {
            CompareData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.compare_cache) != null) {
                return this.compare(arg0Value, arg1Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                CompareData s0_ = super.insert(new CompareData());
                s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                VarHandle.storeStoreFence();
                this.compare_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = this.compare(arg0Value, arg1Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.CompareCharsUTF16Node create() {
            return new CompareCharsUTF16NodeGen();
        }

        public static TruffleString.CompareCharsUTF16Node getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.CompareCharsUTF16Node.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.CompareCharsUTF16Node {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value) {
                return this.compare(arg0Value, arg1Value, TruffleString.ToIndexableNode.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached());
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

        @GeneratedBy(value=TruffleString.CompareCharsUTF16Node.class)
        private static final class CompareData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.CompareBytesNode.class)
    static final class CompareBytesNodeGen
    extends TruffleString.CompareBytesNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private CompareData compare_cache;

        private CompareBytesNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
            CompareData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.compare_cache) != null) {
                return this.compare(arg0Value, arg1Value, arg2Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                CompareData s0_ = super.insert(new CompareData());
                s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                VarHandle.storeStoreFence();
                this.compare_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = this.compare(arg0Value, arg1Value, arg2Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.CompareBytesNode create() {
            return new CompareBytesNodeGen();
        }

        public static TruffleString.CompareBytesNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.CompareBytesNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.CompareBytesNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
                return this.compare(arg0Value, arg1Value, arg2Value, TruffleString.ToIndexableNode.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached());
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

        @GeneratedBy(value=TruffleString.CompareBytesNode.class)
        private static final class CompareData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.LastByteIndexOfStringNode.class)
    static final class LastByteIndexOfStringNodeGen
    extends TruffleString.LastByteIndexOfStringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private LastByteIndexOfStringData lastByteIndexOfString_cache;

        private LastByteIndexOfStringNodeGen() {
        }

        @Override
        int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, byte[] arg4Value, TruffleString.Encoding arg5Value) {
            LastByteIndexOfStringData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.lastByteIndexOfString_cache) != null) {
                return TruffleString.LastByteIndexOfStringNode.lastByteIndexOfString(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_, s0_.indexOfStringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, byte[] arg4Value, TruffleString.Encoding arg5Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                LastByteIndexOfStringData s0_ = super.insert(new LastByteIndexOfStringData());
                s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.indexOfStringNode_ = s0_.insertAccessor(TStringInternalNodesFactory.LastIndexOfStringRawNodeGen.create());
                VarHandle.storeStoreFence();
                this.lastByteIndexOfString_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.LastByteIndexOfStringNode.lastByteIndexOfString(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_, s0_.indexOfStringNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.LastByteIndexOfStringNode create() {
            return new LastByteIndexOfStringNodeGen();
        }

        public static TruffleString.LastByteIndexOfStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.LastByteIndexOfStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.LastByteIndexOfStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, byte[] arg4Value, TruffleString.Encoding arg5Value) {
                return TruffleString.LastByteIndexOfStringNode.lastByteIndexOfString(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleString.ToIndexableNode.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.LastIndexOfStringRawNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.LastByteIndexOfStringNode.class)
        private static final class LastByteIndexOfStringData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.LastIndexOfStringNode.class)
    static final class LastIndexOfStringNodeGen
    extends TruffleString.LastIndexOfStringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private LastIndexOfStringData lastIndexOfString_cache;

        private LastIndexOfStringNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            LastIndexOfStringData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.lastIndexOfString_cache) != null) {
                return TruffleString.LastIndexOfStringNode.lastIndexOfString(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodePointLengthANode_, s0_.getCodePointLengthBNode_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_, s0_.indexOfStringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                LastIndexOfStringData s0_ = super.insert(new LastIndexOfStringData());
                s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodePointLengthANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.getCodePointLengthBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.indexOfStringNode_ = s0_.insertAccessor(TStringInternalNodesFactory.LastIndexOfStringNodeGen.create());
                VarHandle.storeStoreFence();
                this.lastIndexOfString_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.LastIndexOfStringNode.lastIndexOfString(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodePointLengthANode_, s0_.getCodePointLengthBNode_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_, s0_.indexOfStringNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.LastIndexOfStringNode create() {
            return new LastIndexOfStringNodeGen();
        }

        public static TruffleString.LastIndexOfStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.LastIndexOfStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.LastIndexOfStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
                return TruffleString.LastIndexOfStringNode.lastIndexOfString(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TruffleString.ToIndexableNode.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.LastIndexOfStringNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.LastIndexOfStringNode.class)
        private static final class LastIndexOfStringData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.ByteIndexOfStringNode.class)
    static final class ByteIndexOfStringNodeGen
    extends TruffleString.ByteIndexOfStringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IndexOfStringData indexOfString_cache;

        private ByteIndexOfStringNodeGen() {
        }

        @Override
        int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, byte[] arg4Value, TruffleString.Encoding arg5Value) {
            IndexOfStringData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.indexOfString_cache) != null) {
                return TruffleString.ByteIndexOfStringNode.indexOfString(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_, s0_.indexOfStringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, byte[] arg4Value, TruffleString.Encoding arg5Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                IndexOfStringData s0_ = super.insert(new IndexOfStringData());
                s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.indexOfStringNode_ = s0_.insertAccessor(TStringInternalNodesFactory.IndexOfStringRawNodeGen.create());
                VarHandle.storeStoreFence();
                this.indexOfString_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.ByteIndexOfStringNode.indexOfString(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_, s0_.indexOfStringNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.ByteIndexOfStringNode create() {
            return new ByteIndexOfStringNodeGen();
        }

        public static TruffleString.ByteIndexOfStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.ByteIndexOfStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.ByteIndexOfStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, byte[] arg4Value, TruffleString.Encoding arg5Value) {
                return TruffleString.ByteIndexOfStringNode.indexOfString(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleString.ToIndexableNode.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.IndexOfStringRawNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.ByteIndexOfStringNode.class)
        private static final class IndexOfStringData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.IndexOfStringNode.class)
    static final class IndexOfStringNodeGen
    extends TruffleString.IndexOfStringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IndexOfStringData indexOfString_cache;

        private IndexOfStringNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            IndexOfStringData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.indexOfString_cache) != null) {
                return TruffleString.IndexOfStringNode.indexOfString(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodePointLengthANode_, s0_.getCodePointLengthBNode_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_, s0_.indexOfStringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                IndexOfStringData s0_ = super.insert(new IndexOfStringData());
                s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodePointLengthANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.getCodePointLengthBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.getCodeRangeANode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.getCodeRangeBNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.indexOfStringNode_ = s0_.insertAccessor(TStringInternalNodesFactory.IndexOfStringNodeGen.create());
                VarHandle.storeStoreFence();
                this.indexOfString_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.IndexOfStringNode.indexOfString(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodePointLengthANode_, s0_.getCodePointLengthBNode_, s0_.getCodeRangeANode_, s0_.getCodeRangeBNode_, s0_.indexOfStringNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.IndexOfStringNode create() {
            return new IndexOfStringNodeGen();
        }

        public static TruffleString.IndexOfStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.IndexOfStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.IndexOfStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
                return TruffleString.IndexOfStringNode.indexOfString(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TruffleString.ToIndexableNode.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.IndexOfStringNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.IndexOfStringNode.class)
        private static final class IndexOfStringData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.LastByteIndexOfCodePointNode.class)
    static final class LastByteIndexOfCodePointNodeGen
    extends TruffleString.LastByteIndexOfCodePointNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IndexOfData indexOf_cache;

        private LastByteIndexOfCodePointNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            IndexOfData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.indexOf_cache) != null) {
                return TruffleString.LastByteIndexOfCodePointNode.doIndexOf(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.lastIndexOfNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                IndexOfData s0_ = super.insert(new IndexOfData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.lastIndexOfNode_ = s0_.insertAccessor(TStringInternalNodesFactory.LastIndexOfCodePointRawNodeGen.create());
                VarHandle.storeStoreFence();
                this.indexOf_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.LastByteIndexOfCodePointNode.doIndexOf(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.lastIndexOfNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.LastByteIndexOfCodePointNode create() {
            return new LastByteIndexOfCodePointNodeGen();
        }

        public static TruffleString.LastByteIndexOfCodePointNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.LastByteIndexOfCodePointNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.LastByteIndexOfCodePointNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
                return TruffleString.LastByteIndexOfCodePointNode.doIndexOf(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.LastIndexOfCodePointRawNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.LastByteIndexOfCodePointNode.class)
        private static final class IndexOfData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.LastIndexOfCodePointNode.class)
    static final class LastIndexOfCodePointNodeGen
    extends TruffleString.LastIndexOfCodePointNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IndexOfData indexOf_cache;

        private LastIndexOfCodePointNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            IndexOfData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.indexOf_cache) != null) {
                return TruffleString.LastIndexOfCodePointNode.doIndexOf(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.lastIndexOfNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                IndexOfData s0_ = super.insert(new IndexOfData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.lastIndexOfNode_ = s0_.insertAccessor(TStringInternalNodesFactory.LastIndexOfCodePointNodeGen.create());
                VarHandle.storeStoreFence();
                this.indexOf_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.LastIndexOfCodePointNode.doIndexOf(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.lastIndexOfNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.LastIndexOfCodePointNode create() {
            return new LastIndexOfCodePointNodeGen();
        }

        public static TruffleString.LastIndexOfCodePointNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.LastIndexOfCodePointNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.LastIndexOfCodePointNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
                return TruffleString.LastIndexOfCodePointNode.doIndexOf(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.LastIndexOfCodePointNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.LastIndexOfCodePointNode.class)
        private static final class IndexOfData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.ByteIndexOfCodePointNode.class)
    static final class ByteIndexOfCodePointNodeGen
    extends TruffleString.ByteIndexOfCodePointNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IndexOfData indexOf_cache;

        private ByteIndexOfCodePointNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            IndexOfData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.indexOf_cache) != null) {
                return TruffleString.ByteIndexOfCodePointNode.doIndexOf(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                IndexOfData s0_ = super.insert(new IndexOfData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.indexOfNode_ = s0_.insertAccessor(TStringInternalNodesFactory.IndexOfCodePointRawNodeGen.create());
                VarHandle.storeStoreFence();
                this.indexOf_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.ByteIndexOfCodePointNode.doIndexOf(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.ByteIndexOfCodePointNode create() {
            return new ByteIndexOfCodePointNodeGen();
        }

        public static TruffleString.ByteIndexOfCodePointNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.ByteIndexOfCodePointNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.ByteIndexOfCodePointNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
                return TruffleString.ByteIndexOfCodePointNode.doIndexOf(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.IndexOfCodePointRawNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.ByteIndexOfCodePointNode.class)
        private static final class IndexOfData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.IndexOfCodePointNode.class)
    static final class IndexOfCodePointNodeGen
    extends TruffleString.IndexOfCodePointNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IndexOfData indexOf_cache;

        private IndexOfCodePointNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            IndexOfData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.indexOf_cache) != null) {
                return TruffleString.IndexOfCodePointNode.doIndexOf(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                IndexOfData s0_ = super.insert(new IndexOfData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.indexOfNode_ = s0_.insertAccessor(TStringInternalNodesFactory.IndexOfCodePointNodeGen.create());
                VarHandle.storeStoreFence();
                this.indexOf_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.IndexOfCodePointNode.doIndexOf(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.IndexOfCodePointNode create() {
            return new IndexOfCodePointNodeGen();
        }

        public static TruffleString.IndexOfCodePointNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.IndexOfCodePointNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.IndexOfCodePointNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
                return TruffleString.IndexOfCodePointNode.doIndexOf(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.IndexOfCodePointNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.IndexOfCodePointNode.class)
        private static final class IndexOfData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.IntIndexOfAnyIntUTF32Node.class)
    static final class IntIndexOfAnyIntUTF32NodeGen
    extends TruffleString.IntIndexOfAnyIntUTF32Node {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IndexOfRawData indexOfRaw_cache;

        private IntIndexOfAnyIntUTF32NodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int[] arg3Value) {
            IndexOfRawData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.indexOfRaw_cache) != null) {
                return this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int[] arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                IndexOfRawData s0_ = super.insert(new IndexOfRawData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.indexOfNode_ = s0_.insertAccessor(TStringOpsNodesFactory.IndexOfAnyIntNodeGen.create());
                VarHandle.storeStoreFence();
                this.indexOfRaw_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.IntIndexOfAnyIntUTF32Node create() {
            return new IntIndexOfAnyIntUTF32NodeGen();
        }

        public static TruffleString.IntIndexOfAnyIntUTF32Node getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.IntIndexOfAnyIntUTF32Node.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.IntIndexOfAnyIntUTF32Node {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, int[] arg3Value) {
                return this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringOpsNodesFactory.IndexOfAnyIntNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.IntIndexOfAnyIntUTF32Node.class)
        private static final class IndexOfRawData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.CharIndexOfAnyCharUTF16Node.class)
    static final class CharIndexOfAnyCharUTF16NodeGen
    extends TruffleString.CharIndexOfAnyCharUTF16Node {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IndexOfRawData indexOfRaw_cache;

        private CharIndexOfAnyCharUTF16NodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, char[] arg3Value) {
            IndexOfRawData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.indexOfRaw_cache) != null) {
                return this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, char[] arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                IndexOfRawData s0_ = super.insert(new IndexOfRawData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.indexOfNode_ = s0_.insertAccessor(TStringOpsNodesFactory.IndexOfAnyCharNodeGen.create());
                VarHandle.storeStoreFence();
                this.indexOfRaw_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.indexOfNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.CharIndexOfAnyCharUTF16Node create() {
            return new CharIndexOfAnyCharUTF16NodeGen();
        }

        public static TruffleString.CharIndexOfAnyCharUTF16Node getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.CharIndexOfAnyCharUTF16Node.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.CharIndexOfAnyCharUTF16Node {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, char[] arg3Value) {
                return this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringOpsNodesFactory.IndexOfAnyCharNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.CharIndexOfAnyCharUTF16Node.class)
        private static final class IndexOfRawData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.ByteIndexOfAnyByteNode.class)
    static final class ByteIndexOfAnyByteNodeGen
    extends TruffleString.ByteIndexOfAnyByteNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, byte[] arg3Value, TruffleString.Encoding arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
                this.getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.toIndexableNode_, this.getCodeRangeNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.ByteIndexOfAnyByteNode create() {
            return new ByteIndexOfAnyByteNodeGen();
        }

        public static TruffleString.ByteIndexOfAnyByteNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.ByteIndexOfAnyByteNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.ByteIndexOfAnyByteNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, byte[] arg3Value, TruffleString.Encoding arg4Value) {
                return this.indexOfRaw(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached());
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

    @GeneratedBy(value=TruffleString.CodePointAtByteIndexNode.class)
    static final class CodePointAtByteIndexNodeGen
    extends TruffleString.CodePointAtByteIndexNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ReadCodePointData readCodePoint_cache;

        private CodePointAtByteIndexNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
            ReadCodePointData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.readCodePoint_cache) != null) {
                return TruffleString.CodePointAtByteIndexNode.readCodePoint(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.readCodePointNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                ReadCodePointData s0_ = super.insert(new ReadCodePointData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.readCodePointNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CodePointAtRawNodeGen.create());
                VarHandle.storeStoreFence();
                this.readCodePoint_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.CodePointAtByteIndexNode.readCodePoint(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.readCodePointNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.CodePointAtByteIndexNode create() {
            return new CodePointAtByteIndexNodeGen();
        }

        public static TruffleString.CodePointAtByteIndexNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.CodePointAtByteIndexNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.CodePointAtByteIndexNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
                return TruffleString.CodePointAtByteIndexNode.readCodePoint(arg0Value, arg1Value, arg2Value, arg3Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.CodePointAtRawNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.CodePointAtByteIndexNode.class)
        private static final class ReadCodePointData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.CodePointAtIndexNode.class)
    static final class CodePointAtIndexNodeGen
    extends TruffleString.CodePointAtIndexNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ReadCodePointData readCodePoint_cache;

        private CodePointAtIndexNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
            ReadCodePointData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.readCodePoint_cache) != null) {
                return TruffleString.CodePointAtIndexNode.readCodePoint(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.readCodePointNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                ReadCodePointData s0_ = super.insert(new ReadCodePointData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.readCodePointNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CodePointAtNodeGen.create());
                VarHandle.storeStoreFence();
                this.readCodePoint_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.CodePointAtIndexNode.readCodePoint(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.readCodePointNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.CodePointAtIndexNode create() {
            return new CodePointAtIndexNodeGen();
        }

        public static TruffleString.CodePointAtIndexNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.CodePointAtIndexNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.CodePointAtIndexNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
                return TruffleString.CodePointAtIndexNode.readCodePoint(arg0Value, arg1Value, arg2Value, arg3Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.CodePointAtNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.CodePointAtIndexNode.class)
        private static final class ReadCodePointData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.CodePointIndexToByteIndexNode.class)
    static final class CodePointIndexToByteIndexNodeGen
    extends TruffleString.CodePointIndexToByteIndexNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TranslateData translate_cache;

        private CodePointIndexToByteIndexNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
            TranslateData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.translate_cache) != null) {
                return TruffleString.CodePointIndexToByteIndexNode.translate(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.codePointIndexToRawNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                TranslateData s0_ = super.insert(new TranslateData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.codePointIndexToRawNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CodePointIndexToRawNodeGen.create());
                VarHandle.storeStoreFence();
                this.translate_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.CodePointIndexToByteIndexNode.translate(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.codePointIndexToRawNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.CodePointIndexToByteIndexNode create() {
            return new CodePointIndexToByteIndexNodeGen();
        }

        public static TruffleString.CodePointIndexToByteIndexNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.CodePointIndexToByteIndexNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.CodePointIndexToByteIndexNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
                return TruffleString.CodePointIndexToByteIndexNode.translate(arg0Value, arg1Value, arg2Value, arg3Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.CodePointIndexToRawNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.CodePointIndexToByteIndexNode.class)
        private static final class TranslateData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.ByteIndexToCodePointIndexNode.class)
    static final class ByteIndexToCodePointIndexNodeGen
    extends TruffleString.ByteIndexToCodePointIndexNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TranslateData translate_cache;

        private ByteIndexToCodePointIndexNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
            TranslateData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.translate_cache) != null) {
                return TruffleString.ByteIndexToCodePointIndexNode.translate(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.rawIndexToCodePointIndexNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                TranslateData s0_ = super.insert(new TranslateData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.rawIndexToCodePointIndexNode_ = s0_.insertAccessor(TStringInternalNodesFactory.RawIndexToCodePointIndexNodeGen.create());
                VarHandle.storeStoreFence();
                this.translate_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.ByteIndexToCodePointIndexNode.translate(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.rawIndexToCodePointIndexNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.ByteIndexToCodePointIndexNode create() {
            return new ByteIndexToCodePointIndexNodeGen();
        }

        public static TruffleString.ByteIndexToCodePointIndexNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.ByteIndexToCodePointIndexNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.ByteIndexToCodePointIndexNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
                return TruffleString.ByteIndexToCodePointIndexNode.translate(arg0Value, arg1Value, arg2Value, arg3Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.RawIndexToCodePointIndexNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.ByteIndexToCodePointIndexNode.class)
        private static final class TranslateData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.ByteLengthOfCodePointNode.class)
    static final class ByteLengthOfCodePointNodeGen
    extends TruffleString.ByteLengthOfCodePointNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TranslateData translate_cache;

        private ByteLengthOfCodePointNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
            TranslateData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.translate_cache) != null) {
                return TruffleString.ByteLengthOfCodePointNode.translate(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.byteLengthOfCodePointNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                TranslateData s0_ = super.insert(new TranslateData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.byteLengthOfCodePointNode_ = s0_.insertAccessor(TStringInternalNodesFactory.ByteLengthOfCodePointNodeGen.create());
                VarHandle.storeStoreFence();
                this.translate_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.ByteLengthOfCodePointNode.translate(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeNode_, s0_.byteLengthOfCodePointNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.ByteLengthOfCodePointNode create() {
            return new ByteLengthOfCodePointNodeGen();
        }

        public static TruffleString.ByteLengthOfCodePointNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.ByteLengthOfCodePointNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.ByteLengthOfCodePointNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value, TruffleString.ErrorHandling arg3Value) {
                return TruffleString.ByteLengthOfCodePointNode.translate(arg0Value, arg1Value, arg2Value, arg3Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodesFactory.ByteLengthOfCodePointNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.ByteLengthOfCodePointNode.class)
        private static final class TranslateData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.ReadCharUTF16Node.class)
    static final class ReadCharUTF16NodeGen
    extends TruffleString.ReadCharUTF16Node {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private char executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
                this.utf16S0Profile_ = ConditionProfile.create();
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                char c = TruffleString.ReadCharUTF16Node.doRead(arg0Value, arg1Value, this.toIndexableNode_, this.utf16S0Profile_);
                return c;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.ReadCharUTF16Node create() {
            return new ReadCharUTF16NodeGen();
        }

        public static TruffleString.ReadCharUTF16Node getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.ReadCharUTF16Node.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.ReadCharUTF16Node {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

    @GeneratedBy(value=TruffleString.ReadByteNode.class)
    static final class ReadByteNodeGen
    extends TruffleString.ReadByteNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
                this.readByteNode_ = super.insert(TStringInternalNodesFactory.ReadByteNodeGen.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.ReadByteNode.doRead(arg0Value, arg1Value, arg2Value, this.toIndexableNode_, this.readByteNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.ReadByteNode create() {
            return new ReadByteNodeGen();
        }

        public static TruffleString.ReadByteNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.ReadByteNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.ReadByteNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, int arg1Value, TruffleString.Encoding arg2Value) {
                return TruffleString.ReadByteNode.doRead(arg0Value, arg1Value, arg2Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodesFactory.ReadByteNodeGen.getUncached());
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

    @GeneratedBy(value=TruffleString.HashCodeNode.class)
    static final class HashCodeNodeGen
    extends TruffleString.HashCodeNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private CalculateHashData calculateHash_cache;

        private HashCodeNodeGen() {
        }

        @Override
        public int execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            CalculateHashData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.calculateHash_cache) != null) {
                return TruffleString.HashCodeNode.calculateHash(arg0Value, arg1Value, s0_.cacheMiss_, s0_.toIndexableNode_, s0_.calculateHashCodeNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                CalculateHashData s0_ = super.insert(new CalculateHashData());
                s0_.cacheMiss_ = ConditionProfile.create();
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.calculateHashCodeNode_ = s0_.insertAccessor(TStringOpsNodesFactory.CalculateHashCodeNodeGen.create());
                VarHandle.storeStoreFence();
                this.calculateHash_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.HashCodeNode.calculateHash(arg0Value, arg1Value, s0_.cacheMiss_, s0_.toIndexableNode_, s0_.calculateHashCodeNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.HashCodeNode create() {
            return new HashCodeNodeGen();
        }

        public static TruffleString.HashCodeNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.HashCodeNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.HashCodeNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
                return TruffleString.HashCodeNode.calculateHash(arg0Value, arg1Value, ConditionProfile.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringOpsNodesFactory.CalculateHashCodeNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.HashCodeNode.class)
        private static final class CalculateHashData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.CodePointLengthNode.class)
    static final class CodePointLengthNodeGen
    extends TruffleString.CodePointLengthNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.getCodePointLengthNode_ = super.insert(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = TruffleString.CodePointLengthNode.get(arg0Value, arg1Value, this.getCodePointLengthNode_);
                return n;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.CodePointLengthNode create() {
            return new CodePointLengthNodeGen();
        }

        public static TruffleString.CodePointLengthNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.CodePointLengthNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.CodePointLengthNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

    @GeneratedBy(value=TruffleString.IsValidNode.class)
    static final class IsValidNodeGen
    extends TruffleString.IsValidNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = TruffleString.IsValidNode.isValid(arg0Value, arg1Value, this.getCodeRangeNode_);
                return bl;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.IsValidNode create() {
            return new IsValidNodeGen();
        }

        public static TruffleString.IsValidNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.IsValidNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.IsValidNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

    @GeneratedBy(value=TruffleString.CodeRangeEqualsNode.class)
    static final class CodeRangeEqualsNodeGen
    extends TruffleString.CodeRangeEqualsNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.CodeRange arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = TruffleString.CodeRangeEqualsNode.codeRangeEquals(arg0Value, arg1Value, this.getCodeRangeNode_);
                return bl;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.CodeRangeEqualsNode create() {
            return new CodeRangeEqualsNodeGen();
        }

        public static TruffleString.CodeRangeEqualsNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.CodeRangeEqualsNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.CodeRangeEqualsNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

    @GeneratedBy(value=TruffleString.GetByteCodeRangeNode.class)
    static final class GetByteCodeRangeNodeGen
    extends TruffleString.GetByteCodeRangeNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString.CodeRange executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString.CodeRange codeRange = TruffleString.GetByteCodeRangeNode.getCodeRange(arg0Value, arg1Value, this.getCodeRangeNode_);
                return codeRange;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.GetByteCodeRangeNode create() {
            return new GetByteCodeRangeNodeGen();
        }

        public static TruffleString.GetByteCodeRangeNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.GetByteCodeRangeNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.GetByteCodeRangeNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

    @GeneratedBy(value=TruffleString.GetCodeRangeNode.class)
    static final class GetCodeRangeNodeGen
    extends TruffleString.GetCodeRangeNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString.CodeRange executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.getCodeRangeNode_ = super.insert(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString.CodeRange codeRange = TruffleString.GetCodeRangeNode.getCodeRange(arg0Value, arg1Value, this.getCodeRangeNode_);
                return codeRange;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.GetCodeRangeNode create() {
            return new GetCodeRangeNodeGen();
        }

        public static TruffleString.GetCodeRangeNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.GetCodeRangeNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.GetCodeRangeNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

    @GeneratedBy(value=TruffleString.MaterializeNode.class)
    static final class MaterializeNodeGen
    extends TruffleString.MaterializeNode {
        private static final Uncached UNCACHED = new Uncached();
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
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toIndexableNode_ = super.insert(TruffleString.ToIndexableNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString.MaterializeNode.doMaterialize(arg0Value, arg1Value, this.toIndexableNode_);
                return;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.MaterializeNode create() {
            return new MaterializeNodeGen();
        }

        public static TruffleString.MaterializeNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.MaterializeNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.MaterializeNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

    @GeneratedBy(value=TruffleString.ToIndexableNode.class)
    static final class ToIndexableNodeFactory {
        ToIndexableNodeFactory() {
        }

        @GeneratedBy(value=TruffleString.ToIndexableNode.ToIndexableImplNode.class)
        static final class ToIndexableImplNodeGen
        extends TruffleString.ToIndexableNode.ToIndexableImplNode {
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
                    Object arg1Value_;
                    if ((state_0 & 1) != 0 && arg1Value instanceof byte[]) {
                        byte[] arg1Value_2 = (byte[])arg1Value;
                        return TruffleString.ToIndexableNode.ToIndexableImplNode.doByteArray(arg0Value, arg1Value_2);
                    }
                    if ((state_0 & 6) != 0 && arg1Value instanceof AbstractTruffleString.NativePointer) {
                        arg1Value_ = (AbstractTruffleString.NativePointer)arg1Value;
                        if ((state_0 & 2) != 0 && TStringGuards.isSupportedEncoding(arg0Value.encoding())) {
                            return TruffleString.ToIndexableNode.ToIndexableImplNode.doNativeSupported(arg0Value, (AbstractTruffleString.NativePointer)arg1Value_);
                        }
                        if ((state_0 & 4) != 0 && !TStringGuards.isSupportedEncoding(arg0Value.encoding())) {
                            return TruffleString.ToIndexableNode.ToIndexableImplNode.doNativeUnsupported(arg0Value, (AbstractTruffleString.NativePointer)arg1Value_, this.nativeUnsupported_materializeProfile_);
                        }
                    }
                    if ((state_0 & 8) != 0 && arg1Value instanceof AbstractTruffleString.LazyConcat) {
                        arg1Value_ = (AbstractTruffleString.LazyConcat)arg1Value;
                        return this.doLazyConcat(arg0Value, (AbstractTruffleString.LazyConcat)arg1Value_);
                    }
                    if ((state_0 & 0x10) != 0 && arg1Value instanceof AbstractTruffleString.LazyLong) {
                        arg1Value_ = (AbstractTruffleString.LazyLong)arg1Value;
                        return TruffleString.ToIndexableNode.ToIndexableImplNode.doLazyLong(arg0Value, (AbstractTruffleString.LazyLong)arg1Value_, this.lazyLong_materializeProfile_);
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
                    Object arg1Value_;
                    int state_0 = this.state_0_;
                    if (arg1Value instanceof byte[]) {
                        byte[] arg1Value_2 = (byte[])arg1Value;
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        byte[] byArray = TruffleString.ToIndexableNode.ToIndexableImplNode.doByteArray(arg0Value, arg1Value_2);
                        return byArray;
                    }
                    if (arg1Value instanceof AbstractTruffleString.NativePointer) {
                        arg1Value_ = (AbstractTruffleString.NativePointer)arg1Value;
                        if (TStringGuards.isSupportedEncoding(arg0Value.encoding())) {
                            this.state_0_ = state_0 |= 2;
                            lock.unlock();
                            hasLock = false;
                            AbstractTruffleString.NativePointer nativePointer = TruffleString.ToIndexableNode.ToIndexableImplNode.doNativeSupported(arg0Value, (AbstractTruffleString.NativePointer)arg1Value_);
                            return nativePointer;
                        }
                        if (!TStringGuards.isSupportedEncoding(arg0Value.encoding())) {
                            this.nativeUnsupported_materializeProfile_ = ConditionProfile.create();
                            this.state_0_ = state_0 |= 4;
                            lock.unlock();
                            hasLock = false;
                            AbstractTruffleString.NativePointer nativePointer = TruffleString.ToIndexableNode.ToIndexableImplNode.doNativeUnsupported(arg0Value, (AbstractTruffleString.NativePointer)arg1Value_, this.nativeUnsupported_materializeProfile_);
                            return nativePointer;
                        }
                    }
                    if (arg1Value instanceof AbstractTruffleString.LazyConcat) {
                        arg1Value_ = (AbstractTruffleString.LazyConcat)arg1Value;
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        byte[] byArray = this.doLazyConcat(arg0Value, (AbstractTruffleString.LazyConcat)arg1Value_);
                        return byArray;
                    }
                    if (arg1Value instanceof AbstractTruffleString.LazyLong) {
                        arg1Value_ = (AbstractTruffleString.LazyLong)arg1Value;
                        this.lazyLong_materializeProfile_ = ConditionProfile.create();
                        this.state_0_ = state_0 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        byte[] byArray = TruffleString.ToIndexableNode.ToIndexableImplNode.doLazyLong(arg0Value, (AbstractTruffleString.LazyLong)arg1Value_, this.lazyLong_materializeProfile_);
                        return byArray;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
                }
                finally {
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
                }
                if ((state_0 & state_0 - 1) == 0) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            public static TruffleString.ToIndexableNode.ToIndexableImplNode create() {
                return new ToIndexableImplNodeGen();
            }
        }
    }

    @GeneratedBy(value=TruffleString.AsManagedNode.class)
    static final class AsManagedNodeGen
    extends TruffleString.AsManagedNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private NativeOrMutableData nativeOrMutable_cache;

        private AsManagedNodeGen() {
        }

        @Override
        public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                NativeOrMutableData s1_;
                TruffleString arg0Value_;
                if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString && !(arg0Value_ = (TruffleString)arg0Value).isNative()) {
                    return TruffleString.AsManagedNode.managedImmutable(arg0Value_, arg1Value);
                }
                if ((state_0 & 2) != 0 && (s1_ = this.nativeOrMutable_cache) != null && (arg0Value.isNative() || arg0Value.isMutable())) {
                    return TruffleString.AsManagedNode.nativeOrMutable(arg0Value, arg1Value, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.fromBufferWithStringCompactionNode_);
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
                TruffleString arg0Value_;
                int state_0 = this.state_0_;
                if (arg0Value instanceof TruffleString && !(arg0Value_ = (TruffleString)arg0Value).isNative()) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TruffleString.AsManagedNode.managedImmutable(arg0Value_, arg1Value);
                    return truffleString;
                }
                if (arg0Value.isNative() || arg0Value.isMutable()) {
                    NativeOrMutableData s1_ = super.insert(new NativeOrMutableData());
                    s1_.getCodePointLengthNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                    s1_.getCodeRangeNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                    s1_.fromBufferWithStringCompactionNode_ = s1_.insertAccessor(TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.create());
                    VarHandle.storeStoreFence();
                    this.nativeOrMutable_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TruffleString.AsManagedNode.nativeOrMutable(arg0Value, arg1Value, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.fromBufferWithStringCompactionNode_);
                    return truffleString;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, new Object[]{arg0Value, arg1Value});
            }
            finally {
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
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleString.AsManagedNode create() {
            return new AsManagedNodeGen();
        }

        public static TruffleString.AsManagedNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.AsManagedNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.AsManagedNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
                TruffleString arg0Value_;
                if (arg0Value instanceof TruffleString && !(arg0Value_ = (TruffleString)arg0Value).isNative()) {
                    return TruffleString.AsManagedNode.managedImmutable(arg0Value_, arg1Value);
                }
                if (arg0Value.isNative() || arg0Value.isMutable()) {
                    return TruffleString.AsManagedNode.nativeOrMutable(arg0Value, arg1Value, TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, new Object[]{arg0Value, arg1Value});
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

        @GeneratedBy(value=TruffleString.AsManagedNode.class)
        private static final class NativeOrMutableData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.AsTruffleStringNode.class)
    static final class AsTruffleStringNodeGen
    extends TruffleString.AsTruffleStringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private FromMutableStringData fromMutableString_cache;

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
                    FromMutableStringData s1_ = this.fromMutableString_cache;
                    if (s1_ != null) {
                        return TruffleString.AsTruffleStringNode.fromMutableString(arg0Value_, arg1Value, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.fromBufferWithStringCompactionNode_);
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
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TruffleString.AsTruffleStringNode.immutable(arg0Value_, arg1Value);
                    return truffleString;
                }
                if (arg0Value instanceof MutableTruffleString) {
                    MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
                    FromMutableStringData s1_ = super.insert(new FromMutableStringData());
                    s1_.getCodePointLengthNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                    s1_.getCodeRangeNode_ = s1_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                    s1_.fromBufferWithStringCompactionNode_ = s1_.insertAccessor(TStringInternalNodesFactory.FromBufferWithStringCompactionKnownAttributesNodeGen.create());
                    VarHandle.storeStoreFence();
                    this.fromMutableString_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TruffleString.AsTruffleStringNode.fromMutableString(arg0Value_, arg1Value, s1_.getCodePointLengthNode_, s1_.getCodeRangeNode_, s1_.fromBufferWithStringCompactionNode_);
                    return truffleString;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, new Object[]{arg0Value, arg1Value});
            }
            finally {
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
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleString.AsTruffleStringNode create() {
            return new AsTruffleStringNodeGen();
        }

        public static TruffleString.AsTruffleStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.AsTruffleStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.AsTruffleStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
                if (arg0Value instanceof TruffleString) {
                    TruffleString arg0Value_ = (TruffleString)arg0Value;
                    return TruffleString.AsTruffleStringNode.immutable(arg0Value_, arg1Value);
                }
                if (arg0Value instanceof MutableTruffleString) {
                    MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
                    return TruffleString.AsTruffleStringNode.fromMutableString(arg0Value_, arg1Value, TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, new Object[]{arg0Value, arg1Value});
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

        @GeneratedBy(value=TruffleString.AsTruffleStringNode.class)
        private static final class FromMutableStringData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.FromNativePointerNode.class)
    static final class FromNativePointerNodeGen
    extends TruffleString.FromNativePointerNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private FromNativePointerData fromNativePointer_cache;

        private FromNativePointerNodeGen() {
        }

        @Override
        public TruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            FromNativePointerData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.fromNativePointer_cache) != null) {
                return this.fromNativePointer(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.interopLibrary_, s0_.fromNativePointerNode_, s0_.fromBufferWithStringCompactionNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                FromNativePointerData s0_ = super.insert(new FromNativePointerData());
                s0_.interopLibrary_ = s0_.insertAccessor(TStringAccessor.createInteropLibrary());
                s0_.fromNativePointerNode_ = s0_.insertAccessor(TStringInternalNodesFactory.FromNativePointerNodeGen.create());
                s0_.fromBufferWithStringCompactionNode_ = s0_.insertAccessor(TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.create());
                VarHandle.storeStoreFence();
                this.fromNativePointer_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.fromNativePointer(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.interopLibrary_, s0_.fromNativePointerNode_, s0_.fromBufferWithStringCompactionNode_);
                return truffleString;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.FromNativePointerNode create() {
            return new FromNativePointerNodeGen();
        }

        public static TruffleString.FromNativePointerNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.FromNativePointerNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.FromNativePointerNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
                return this.fromNativePointer(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TStringAccessor.getUncachedInteropLibrary(), TStringInternalNodesFactory.FromNativePointerNodeGen.getUncached(), TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleString.FromNativePointerNode.class)
        private static final class FromNativePointerData
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.FromIntArrayUTF32Node.class)
    static final class FromIntArrayUTF32NodeGen
    extends TruffleString.FromIntArrayUTF32Node {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private NonEmptyData nonEmpty_cache;

        private FromIntArrayUTF32NodeGen() {
        }

        @Override
        public TruffleString execute(int[] arg0Value, int arg1Value, int arg2Value) {
            NonEmptyData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.nonEmpty_cache) != null) {
                return this.doNonEmpty(arg0Value, arg1Value, arg2Value, s0_.utf32Compact0Profile_, s0_.utf32Compact1Profile_, s0_.outOfMemoryProfile_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(int[] arg0Value, int arg1Value, int arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                NonEmptyData s0_ = new NonEmptyData();
                s0_.utf32Compact0Profile_ = ConditionProfile.create();
                s0_.utf32Compact1Profile_ = ConditionProfile.create();
                s0_.outOfMemoryProfile_ = BranchProfile.create();
                VarHandle.storeStoreFence();
                this.nonEmpty_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doNonEmpty(arg0Value, arg1Value, arg2Value, s0_.utf32Compact0Profile_, s0_.utf32Compact1Profile_, s0_.outOfMemoryProfile_);
                return truffleString;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.FromIntArrayUTF32Node create() {
            return new FromIntArrayUTF32NodeGen();
        }

        public static TruffleString.FromIntArrayUTF32Node getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.FromIntArrayUTF32Node.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.FromIntArrayUTF32Node {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

        @GeneratedBy(value=TruffleString.FromIntArrayUTF32Node.class)
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
    }

    @GeneratedBy(value=TruffleString.FromJavaStringNode.class)
    static final class FromJavaStringNodeGen
    extends TruffleString.FromJavaStringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private UTF16Data uTF16_cache;

        private FromJavaStringNodeGen() {
        }

        @Override
        public TruffleString execute(String arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            UTF16Data s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.uTF16_cache) != null) {
                return TruffleString.FromJavaStringNode.doUTF16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.fromJavaStringUTF16Node_, s0_.switchEncodingNode_, s0_.utf16Profile_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(String arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                UTF16Data s0_ = super.insert(new UTF16Data());
                s0_.fromJavaStringUTF16Node_ = s0_.insertAccessor(TStringInternalNodesFactory.FromJavaStringUTF16NodeGen.create());
                s0_.switchEncodingNode_ = s0_.insertAccessor(TruffleString.SwitchEncodingNode.create());
                s0_.utf16Profile_ = ConditionProfile.create();
                VarHandle.storeStoreFence();
                this.uTF16_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = TruffleString.FromJavaStringNode.doUTF16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.fromJavaStringUTF16Node_, s0_.switchEncodingNode_, s0_.utf16Profile_);
                return truffleString;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.FromJavaStringNode create() {
            return new FromJavaStringNodeGen();
        }

        public static TruffleString.FromJavaStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.FromJavaStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.FromJavaStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(String arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
                return TruffleString.FromJavaStringNode.doUTF16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TStringInternalNodesFactory.FromJavaStringUTF16NodeGen.getUncached(), TruffleString.SwitchEncodingNode.getUncached(), ConditionProfile.getUncached());
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

        @GeneratedBy(value=TruffleString.FromJavaStringNode.class)
        private static final class UTF16Data
        extends Node {
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
    }

    @GeneratedBy(value=TruffleString.FromCharArrayUTF16Node.class)
    static final class FromCharArrayUTF16NodeGen
    extends TruffleString.FromCharArrayUTF16Node {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(char[] arg0Value, int arg1Value, int arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.utf16CompactProfile_ = ConditionProfile.create();
                this.outOfMemoryProfile_ = BranchProfile.create();
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doNonEmpty(arg0Value, arg1Value, arg2Value, this.utf16CompactProfile_, this.outOfMemoryProfile_);
                return truffleString;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.FromCharArrayUTF16Node create() {
            return new FromCharArrayUTF16NodeGen();
        }

        public static TruffleString.FromCharArrayUTF16Node getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.FromCharArrayUTF16Node.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.FromCharArrayUTF16Node {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

    @GeneratedBy(value=TruffleString.FromByteArrayNode.class)
    static final class FromByteArrayNodeGen
    extends TruffleString.FromByteArrayNode {
        private static final Uncached UNCACHED = new Uncached();
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
                return TruffleString.FromByteArrayNode.fromByteArray(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.fromBufferWithStringCompactionNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(byte[] arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.fromBufferWithStringCompactionNode_ = super.insert(TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = TruffleString.FromByteArrayNode.fromByteArray(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.fromBufferWithStringCompactionNode_);
                return truffleString;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.FromByteArrayNode create() {
            return new FromByteArrayNodeGen();
        }

        public static TruffleString.FromByteArrayNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.FromByteArrayNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.FromByteArrayNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(byte[] arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
                return TruffleString.FromByteArrayNode.fromByteArray(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TStringInternalNodesFactory.FromBufferWithStringCompactionNodeGen.getUncached());
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

    @GeneratedBy(value=TruffleString.FromLongNode.class)
    static final class FromLongNodeGen
    extends TruffleString.FromLongNode {
        private static final Uncached UNCACHED = new Uncached();
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
                this.state_0_ = state_0 |= 1;
                return TruffleString.FromLongNode.doLazy(arg0Value, arg1Value, arg2Value);
            }
            if (TStringGuards.is7BitCompatible(arg1Value) && !arg2Value) {
                this.state_0_ = state_0 |= 2;
                return TruffleString.FromLongNode.doEager(arg0Value, arg1Value, arg2Value);
            }
            if (!TStringGuards.is7BitCompatible(arg1Value)) {
                this.state_0_ = state_0 |= 4;
                return TruffleString.FromLongNode.unsupported(arg0Value, arg1Value, arg2Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value});
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleString.FromLongNode create() {
            return new FromLongNodeGen();
        }

        public static TruffleString.FromLongNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.FromLongNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.FromLongNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(long arg0Value, TruffleString.Encoding arg1Value, boolean arg2Value) {
                if (TStringGuards.is7BitCompatible(arg1Value) && arg2Value) {
                    return TruffleString.FromLongNode.doLazy(arg0Value, arg1Value, arg2Value);
                }
                if (TStringGuards.is7BitCompatible(arg1Value) && !arg2Value) {
                    return TruffleString.FromLongNode.doEager(arg0Value, arg1Value, arg2Value);
                }
                if (!TStringGuards.is7BitCompatible(arg1Value)) {
                    return TruffleString.FromLongNode.unsupported(arg0Value, arg1Value, arg2Value);
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value});
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

    @GeneratedBy(value=TruffleString.FromCodePointNode.class)
    static final class FromCodePointNodeGen
    extends TruffleString.FromCodePointNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private FromCodePointData fromCodePoint_cache;

        private FromCodePointNodeGen() {
        }

        @Override
        public TruffleString execute(int arg0Value, TruffleString.Encoding arg1Value, boolean arg2Value) {
            FromCodePointData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.fromCodePoint_cache) != null) {
                return TruffleString.FromCodePointNode.fromCodePoint(arg0Value, arg1Value, arg2Value, s0_.bytesProfile_, s0_.utf8Profile_, s0_.utf16Profile_, s0_.utf32Profile_, s0_.exoticProfile_, s0_.bmpProfile_, s0_.invalidCodePoint_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(int arg0Value, TruffleString.Encoding arg1Value, boolean arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                FromCodePointData s0_ = new FromCodePointData();
                s0_.bytesProfile_ = ConditionProfile.create();
                s0_.utf8Profile_ = ConditionProfile.create();
                s0_.utf16Profile_ = ConditionProfile.create();
                s0_.utf32Profile_ = ConditionProfile.create();
                s0_.exoticProfile_ = ConditionProfile.create();
                s0_.bmpProfile_ = ConditionProfile.create();
                s0_.invalidCodePoint_ = BranchProfile.create();
                VarHandle.storeStoreFence();
                this.fromCodePoint_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = TruffleString.FromCodePointNode.fromCodePoint(arg0Value, arg1Value, arg2Value, s0_.bytesProfile_, s0_.utf8Profile_, s0_.utf16Profile_, s0_.utf32Profile_, s0_.exoticProfile_, s0_.bmpProfile_, s0_.invalidCodePoint_);
                return truffleString;
            }
            finally {
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
            }
            return NodeCost.MONOMORPHIC;
        }

        public static TruffleString.FromCodePointNode create() {
            return new FromCodePointNodeGen();
        }

        public static TruffleString.FromCodePointNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleString.FromCodePointNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleString.FromCodePointNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(int arg0Value, TruffleString.Encoding arg1Value, boolean arg2Value) {
                return TruffleString.FromCodePointNode.fromCodePoint(arg0Value, arg1Value, arg2Value, ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), BranchProfile.getUncached());
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

        @GeneratedBy(value=TruffleString.FromCodePointNode.class)
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
    }

    @GeneratedBy(value=TruffleString.WithMask.class)
    public static final class WithMaskFactory {

        @GeneratedBy(value=TruffleString.WithMask.CreateUTF32Node.class)
        static final class CreateUTF32NodeGen
        extends TruffleString.WithMask.CreateUTF32Node {
            private static final Uncached UNCACHED = new Uncached();

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
                return new CreateUTF32NodeGen();
            }

            public static TruffleString.WithMask.CreateUTF32Node getUncached() {
                return UNCACHED;
            }

            @GeneratedBy(value=TruffleString.WithMask.CreateUTF32Node.class)
            @DenyReplace
            private static final class Uncached
            extends TruffleString.WithMask.CreateUTF32Node {
                private Uncached() {
                }

                @Override
                @CompilerDirectives.TruffleBoundary
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

        @GeneratedBy(value=TruffleString.WithMask.CreateUTF16Node.class)
        static final class CreateUTF16NodeGen
        extends TruffleString.WithMask.CreateUTF16Node {
            private static final Uncached UNCACHED = new Uncached();

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
                return new CreateUTF16NodeGen();
            }

            public static TruffleString.WithMask.CreateUTF16Node getUncached() {
                return UNCACHED;
            }

            @GeneratedBy(value=TruffleString.WithMask.CreateUTF16Node.class)
            @DenyReplace
            private static final class Uncached
            extends TruffleString.WithMask.CreateUTF16Node {
                private Uncached() {
                }

                @Override
                @CompilerDirectives.TruffleBoundary
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

        @GeneratedBy(value=TruffleString.WithMask.CreateNode.class)
        static final class CreateNodeGen
        extends TruffleString.WithMask.CreateNode {
            private static final Uncached UNCACHED = new Uncached();

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
                return new CreateNodeGen();
            }

            public static TruffleString.WithMask.CreateNode getUncached() {
                return UNCACHED;
            }

            @GeneratedBy(value=TruffleString.WithMask.CreateNode.class)
            @DenyReplace
            private static final class Uncached
            extends TruffleString.WithMask.CreateNode {
                private Uncached() {
                }

                @Override
                @CompilerDirectives.TruffleBoundary
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
    }
}

