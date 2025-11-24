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
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.MutableTruffleString;
import com.oracle.truffle.api.strings.TStringAccessor;
import com.oracle.truffle.api.strings.TStringInternalNodes;
import com.oracle.truffle.api.strings.TStringInternalNodesFactory;
import com.oracle.truffle.api.strings.TruffleString;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=MutableTruffleString.class)
public final class MutableTruffleStringFactory {

    @GeneratedBy(value=MutableTruffleString.CalcLazyAttributesNode.class)
    static final class CalcLazyAttributesNodeGen
    extends MutableTruffleString.CalcLazyAttributesNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private CalcData calc_cache;

        private CalcLazyAttributesNodeGen() {
        }

        @Override
        void execute(MutableTruffleString arg0Value) {
            CalcData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.calc_cache) != null) {
                this.calc(arg0Value, s0_.dataClassProfile_, s0_.asciiBytesLatinProfile_, s0_.utf8Profile_, s0_.utf8BrokenProfile_, s0_.utf16Profile_, s0_.utf16S0Profile_, s0_.utf32Profile_, s0_.utf32S0Profile_, s0_.utf32S1Profile_, s0_.exoticMaterializeNativeProfile_, s0_.exoticValidProfile_, s0_.exoticFixedWidthProfile_);
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(MutableTruffleString arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                CalcData s0_ = new CalcData();
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
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                this.calc(arg0Value, s0_.dataClassProfile_, s0_.asciiBytesLatinProfile_, s0_.utf8Profile_, s0_.utf8BrokenProfile_, s0_.utf16Profile_, s0_.utf16S0Profile_, s0_.utf32Profile_, s0_.utf32S0Profile_, s0_.utf32S1Profile_, s0_.exoticMaterializeNativeProfile_, s0_.exoticValidProfile_, s0_.exoticFixedWidthProfile_);
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

        public static MutableTruffleString.CalcLazyAttributesNode create() {
            return new CalcLazyAttributesNodeGen();
        }

        public static MutableTruffleString.CalcLazyAttributesNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=MutableTruffleString.CalcLazyAttributesNode.class)
        @DenyReplace
        private static final class Uncached
        extends MutableTruffleString.CalcLazyAttributesNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            void execute(MutableTruffleString arg0Value) {
                this.calc(arg0Value, ValueProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached());
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

        @GeneratedBy(value=MutableTruffleString.CalcLazyAttributesNode.class)
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
    }

    @GeneratedBy(value=MutableTruffleString.ForceEncodingNode.class)
    static final class ForceEncodingNodeGen
    extends MutableTruffleString.ForceEncodingNode {
        private static final Uncached UNCACHED = new Uncached();
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
                MutableTruffleString arg0Value_;
                if ((state_0 & 1) != 0 && arg0Value instanceof MutableTruffleString && (arg0Value_ = (MutableTruffleString)arg0Value).isCompatibleTo(arg2Value)) {
                    return MutableTruffleString.ForceEncodingNode.compatible(arg0Value_, arg1Value, arg2Value);
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
                MutableTruffleString arg0Value_;
                int state_0 = this.state_0_;
                if (arg0Value instanceof MutableTruffleString && (arg0Value_ = (MutableTruffleString)arg0Value).isCompatibleTo(arg2Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    MutableTruffleString mutableTruffleString = MutableTruffleString.ForceEncodingNode.compatible(arg0Value_, arg1Value, arg2Value);
                    return mutableTruffleString;
                }
                if (!arg0Value.isCompatibleTo(arg2Value) || arg0Value.isImmutable()) {
                    this.reinterpret_copyToByteArrayNode_ = super.insert(TruffleString.CopyToByteArrayNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    MutableTruffleString mutableTruffleString = MutableTruffleString.ForceEncodingNode.reinterpret(arg0Value, arg1Value, arg2Value, this.reinterpret_copyToByteArrayNode_);
                    return mutableTruffleString;
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

        public static MutableTruffleString.ForceEncodingNode create() {
            return new ForceEncodingNodeGen();
        }

        public static MutableTruffleString.ForceEncodingNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=MutableTruffleString.ForceEncodingNode.class)
        @DenyReplace
        private static final class Uncached
        extends MutableTruffleString.ForceEncodingNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public MutableTruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value, TruffleString.Encoding arg2Value) {
                MutableTruffleString arg0Value_;
                if (arg0Value instanceof MutableTruffleString && (arg0Value_ = (MutableTruffleString)arg0Value).isCompatibleTo(arg2Value)) {
                    return MutableTruffleString.ForceEncodingNode.compatible(arg0Value_, arg1Value, arg2Value);
                }
                if (!arg0Value.isCompatibleTo(arg2Value) || arg0Value.isImmutable()) {
                    return MutableTruffleString.ForceEncodingNode.reinterpret(arg0Value, arg1Value, arg2Value, TruffleString.CopyToByteArrayNode.getUncached());
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

    @GeneratedBy(value=MutableTruffleString.SwitchEncodingNode.class)
    static final class SwitchEncodingNodeGen
    extends MutableTruffleString.SwitchEncodingNode {
        private static final Uncached UNCACHED = new Uncached();
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
                MutableTruffleString arg0Value_;
                if ((state_0 & 1) != 0 && arg0Value instanceof MutableTruffleString && (arg0Value_ = (MutableTruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    return MutableTruffleString.SwitchEncodingNode.compatibleMutable(arg0Value_, arg1Value);
                }
                if ((state_0 & 2) != 0 && (!arg0Value.isCompatibleTo(arg1Value) || arg0Value.isImmutable())) {
                    return MutableTruffleString.SwitchEncodingNode.transcodeAndCopy(arg0Value, arg1Value, this.transcodeAndCopy_switchEncodingNode_, this.transcodeAndCopy_asMutableTruffleStringNode_);
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
                MutableTruffleString arg0Value_;
                int state_0 = this.state_0_;
                if (arg0Value instanceof MutableTruffleString && (arg0Value_ = (MutableTruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    MutableTruffleString mutableTruffleString = MutableTruffleString.SwitchEncodingNode.compatibleMutable(arg0Value_, arg1Value);
                    return mutableTruffleString;
                }
                if (!arg0Value.isCompatibleTo(arg1Value) || arg0Value.isImmutable()) {
                    this.transcodeAndCopy_switchEncodingNode_ = super.insert(TruffleString.SwitchEncodingNode.create());
                    this.transcodeAndCopy_asMutableTruffleStringNode_ = super.insert(MutableTruffleString.AsMutableTruffleStringNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    MutableTruffleString mutableTruffleString = MutableTruffleString.SwitchEncodingNode.transcodeAndCopy(arg0Value, arg1Value, this.transcodeAndCopy_switchEncodingNode_, this.transcodeAndCopy_asMutableTruffleStringNode_);
                    return mutableTruffleString;
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

        public static MutableTruffleString.SwitchEncodingNode create() {
            return new SwitchEncodingNodeGen();
        }

        public static MutableTruffleString.SwitchEncodingNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=MutableTruffleString.SwitchEncodingNode.class)
        @DenyReplace
        private static final class Uncached
        extends MutableTruffleString.SwitchEncodingNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public MutableTruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
                MutableTruffleString arg0Value_;
                if (arg0Value instanceof MutableTruffleString && (arg0Value_ = (MutableTruffleString)arg0Value).isCompatibleTo(arg1Value)) {
                    return MutableTruffleString.SwitchEncodingNode.compatibleMutable(arg0Value_, arg1Value);
                }
                if (!arg0Value.isCompatibleTo(arg1Value) || arg0Value.isImmutable()) {
                    return MutableTruffleString.SwitchEncodingNode.transcodeAndCopy(arg0Value, arg1Value, TruffleString.SwitchEncodingNode.getUncached(), MutableTruffleString.AsMutableTruffleStringNode.getUncached());
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

    @GeneratedBy(value=MutableTruffleString.SubstringByteIndexNode.class)
    static final class SubstringByteIndexNodeGen
    extends MutableTruffleString.SubstringByteIndexNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private MutableTruffleString executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.copyToByteArrayNode_ = super.insert(TruffleString.CopyToByteArrayNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                MutableTruffleString mutableTruffleString = MutableTruffleString.SubstringByteIndexNode.substringByteIndex(arg0Value, arg1Value, arg2Value, arg3Value, this.copyToByteArrayNode_);
                return mutableTruffleString;
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

        public static MutableTruffleString.SubstringByteIndexNode create() {
            return new SubstringByteIndexNodeGen();
        }

        public static MutableTruffleString.SubstringByteIndexNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=MutableTruffleString.SubstringByteIndexNode.class)
        @DenyReplace
        private static final class Uncached
        extends MutableTruffleString.SubstringByteIndexNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public MutableTruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
                return MutableTruffleString.SubstringByteIndexNode.substringByteIndex(arg0Value, arg1Value, arg2Value, arg3Value, TruffleString.CopyToByteArrayNode.getUncached());
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

    @GeneratedBy(value=MutableTruffleString.SubstringNode.class)
    static final class SubstringNodeGen
    extends MutableTruffleString.SubstringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private SubstringData substring_cache;

        private SubstringNodeGen() {
        }

        @Override
        public MutableTruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
            SubstringData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.substring_cache) != null) {
                return MutableTruffleString.SubstringNode.substring(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeANode_, s0_.getCodePointLengthNode_, s0_.translateIndexNode_, s0_.copyToByteArrayNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private MutableTruffleString executeAndSpecialize(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
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
                s0_.copyToByteArrayNode_ = s0_.insertAccessor(TruffleString.CopyToByteArrayNode.create());
                VarHandle.storeStoreFence();
                this.substring_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                MutableTruffleString mutableTruffleString = MutableTruffleString.SubstringNode.substring(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodeRangeANode_, s0_.getCodePointLengthNode_, s0_.translateIndexNode_, s0_.copyToByteArrayNode_);
                return mutableTruffleString;
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

        public static MutableTruffleString.SubstringNode create() {
            return new SubstringNodeGen();
        }

        public static MutableTruffleString.SubstringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=MutableTruffleString.SubstringNode.class)
        @DenyReplace
        private static final class Uncached
        extends MutableTruffleString.SubstringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public MutableTruffleString execute(AbstractTruffleString arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
                return MutableTruffleString.SubstringNode.substring(arg0Value, arg1Value, arg2Value, arg3Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodesFactory.CodePointIndexToRawNodeGen.getUncached(), TruffleString.CopyToByteArrayNode.getUncached());
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

        @GeneratedBy(value=MutableTruffleString.SubstringNode.class)
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
    }

    @GeneratedBy(value=MutableTruffleString.ConcatNode.class)
    static final class ConcatNodeGen
    extends MutableTruffleString.ConcatNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ConcatData concat_cache;

        private ConcatNodeGen() {
        }

        @Override
        public MutableTruffleString execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
            ConcatData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.concat_cache) != null) {
                return MutableTruffleString.ConcatNode.concat(arg0Value, arg1Value, arg2Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.materializeBytesNode_, s0_.outOfMemoryProfile_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private MutableTruffleString executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                ConcatData s0_ = super.insert(new ConcatData());
                s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.materializeBytesNode_ = s0_.insertAccessor(TStringInternalNodesFactory.ConcatMaterializeBytesNodeGen.create());
                s0_.outOfMemoryProfile_ = BranchProfile.create();
                VarHandle.storeStoreFence();
                this.concat_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                MutableTruffleString mutableTruffleString = MutableTruffleString.ConcatNode.concat(arg0Value, arg1Value, arg2Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.materializeBytesNode_, s0_.outOfMemoryProfile_);
                return mutableTruffleString;
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

        public static MutableTruffleString.ConcatNode create() {
            return new ConcatNodeGen();
        }

        public static MutableTruffleString.ConcatNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=MutableTruffleString.ConcatNode.class)
        @DenyReplace
        private static final class Uncached
        extends MutableTruffleString.ConcatNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public MutableTruffleString execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value) {
                return MutableTruffleString.ConcatNode.concat(arg0Value, arg1Value, arg2Value, TruffleString.ToIndexableNode.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodesFactory.ConcatMaterializeBytesNodeGen.getUncached(), BranchProfile.getUncached());
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

        @GeneratedBy(value=MutableTruffleString.ConcatNode.class)
        private static final class ConcatData
        extends Node {
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
    }

    @GeneratedBy(value=MutableTruffleString.WriteByteNode.class)
    static final class WriteByteNodeGen
    extends MutableTruffleString.WriteByteNode {
        private static final Uncached UNCACHED = new Uncached();

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
            return new WriteByteNodeGen();
        }

        public static MutableTruffleString.WriteByteNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=MutableTruffleString.WriteByteNode.class)
        @DenyReplace
        private static final class Uncached
        extends MutableTruffleString.WriteByteNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

    @GeneratedBy(value=MutableTruffleString.AsManagedNode.class)
    static final class AsManagedNodeGen
    extends MutableTruffleString.AsManagedNode {
        private static final Uncached UNCACHED = new Uncached();
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
                MutableTruffleString arg0Value_;
                if ((state_0 & 1) != 0 && arg0Value instanceof MutableTruffleString && !(arg0Value_ = (MutableTruffleString)arg0Value).isNative()) {
                    return MutableTruffleString.AsManagedNode.mutable(arg0Value_, arg1Value);
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
                MutableTruffleString arg0Value_;
                int state_0 = this.state_0_;
                if (arg0Value instanceof MutableTruffleString && !(arg0Value_ = (MutableTruffleString)arg0Value).isNative()) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    MutableTruffleString mutableTruffleString = MutableTruffleString.AsManagedNode.mutable(arg0Value_, arg1Value);
                    return mutableTruffleString;
                }
                if (arg0Value.isNative() || arg0Value.isImmutable()) {
                    this.fromTruffleString_copyToByteArrayNode_ = super.insert(TruffleString.CopyToByteArrayNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    MutableTruffleString mutableTruffleString = MutableTruffleString.AsManagedNode.fromTruffleString(arg0Value, arg1Value, this.fromTruffleString_copyToByteArrayNode_);
                    return mutableTruffleString;
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

        public static MutableTruffleString.AsManagedNode create() {
            return new AsManagedNodeGen();
        }

        public static MutableTruffleString.AsManagedNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=MutableTruffleString.AsManagedNode.class)
        @DenyReplace
        private static final class Uncached
        extends MutableTruffleString.AsManagedNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public MutableTruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
                MutableTruffleString arg0Value_;
                if (arg0Value instanceof MutableTruffleString && !(arg0Value_ = (MutableTruffleString)arg0Value).isNative()) {
                    return MutableTruffleString.AsManagedNode.mutable(arg0Value_, arg1Value);
                }
                if (arg0Value.isNative() || arg0Value.isImmutable()) {
                    return MutableTruffleString.AsManagedNode.fromTruffleString(arg0Value, arg1Value, TruffleString.CopyToByteArrayNode.getUncached());
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

    @GeneratedBy(value=MutableTruffleString.AsMutableTruffleStringNode.class)
    static final class AsMutableTruffleStringNodeGen
    extends MutableTruffleString.AsMutableTruffleStringNode {
        private static final Uncached UNCACHED = new Uncached();
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
            try {
                int state_0 = this.state_0_;
                if (arg0Value instanceof MutableTruffleString) {
                    MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    MutableTruffleString mutableTruffleString = MutableTruffleString.AsMutableTruffleStringNode.mutable(arg0Value_, arg1Value);
                    return mutableTruffleString;
                }
                if (arg0Value instanceof TruffleString) {
                    TruffleString arg0Value_ = (TruffleString)arg0Value;
                    this.fromTruffleString_copyToByteArrayNode_ = super.insert(TruffleString.CopyToByteArrayNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    MutableTruffleString mutableTruffleString = MutableTruffleString.AsMutableTruffleStringNode.fromTruffleString(arg0Value_, arg1Value, this.fromTruffleString_copyToByteArrayNode_);
                    return mutableTruffleString;
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

        public static MutableTruffleString.AsMutableTruffleStringNode create() {
            return new AsMutableTruffleStringNodeGen();
        }

        public static MutableTruffleString.AsMutableTruffleStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=MutableTruffleString.AsMutableTruffleStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends MutableTruffleString.AsMutableTruffleStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public MutableTruffleString execute(AbstractTruffleString arg0Value, TruffleString.Encoding arg1Value) {
                if (arg0Value instanceof MutableTruffleString) {
                    MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
                    return MutableTruffleString.AsMutableTruffleStringNode.mutable(arg0Value_, arg1Value);
                }
                if (arg0Value instanceof TruffleString) {
                    TruffleString arg0Value_ = (TruffleString)arg0Value;
                    return MutableTruffleString.AsMutableTruffleStringNode.fromTruffleString(arg0Value_, arg1Value, TruffleString.CopyToByteArrayNode.getUncached());
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

    @GeneratedBy(value=MutableTruffleString.FromNativePointerNode.class)
    static final class FromNativePointerNodeGen
    extends MutableTruffleString.FromNativePointerNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private MutableTruffleString executeAndSpecialize(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.interopLibrary_ = super.insert(TStringAccessor.createInteropLibrary());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                MutableTruffleString mutableTruffleString = this.fromNativePointer(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.interopLibrary_);
                return mutableTruffleString;
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

        public static MutableTruffleString.FromNativePointerNode create() {
            return new FromNativePointerNodeGen();
        }

        public static MutableTruffleString.FromNativePointerNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=MutableTruffleString.FromNativePointerNode.class)
        @DenyReplace
        private static final class Uncached
        extends MutableTruffleString.FromNativePointerNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

    @GeneratedBy(value=MutableTruffleString.FromByteArrayNode.class)
    static final class FromByteArrayNodeGen
    extends MutableTruffleString.FromByteArrayNode {
        private static final Uncached UNCACHED = new Uncached();

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
            return new FromByteArrayNodeGen();
        }

        public static MutableTruffleString.FromByteArrayNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=MutableTruffleString.FromByteArrayNode.class)
        @DenyReplace
        private static final class Uncached
        extends MutableTruffleString.FromByteArrayNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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
}

