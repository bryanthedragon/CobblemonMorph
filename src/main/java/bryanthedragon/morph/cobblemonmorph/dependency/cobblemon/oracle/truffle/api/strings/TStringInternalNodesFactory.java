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
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.MutableTruffleString;
import com.oracle.truffle.api.strings.MutableTruffleStringFactory;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TStringInternalNodes;
import com.oracle.truffle.api.strings.TStringOpsNodes;
import com.oracle.truffle.api.strings.TStringOpsNodesFactory;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringIterator;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TStringInternalNodes.class)
final class TStringInternalNodesFactory {
    TStringInternalNodesFactory() {
    }

    @GeneratedBy(value=TStringInternalNodes.TransCodeIntlNode.class)
    static final class TransCodeIntlNodeGen
    extends TStringInternalNodes.TransCodeIntlNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleStringIterator.NextNode iteratorNextNode;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile brokenProfile;
        @CompilerDirectives.CompilationFinal
        private BranchProfile outOfMemoryProfile;
        @Node.Child
        private UnsupportedData unsupported_cache;

        private TransCodeIntlNodeGen() {
        }

        @Override
        TruffleString execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value, TruffleString.Encoding arg5Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                UnsupportedData s10_;
                if ((state_0 & 1) != 0 && TStringGuards.isSupportedEncoding(arg4Value) && (TStringGuards.isAscii(arg5Value) || TStringGuards.isBytes(arg5Value))) {
                    return this.targetAscii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
                }
                if ((state_0 & 2) != 0 && TStringGuards.isSupportedEncoding(arg4Value) && TStringGuards.isLatin1(arg5Value)) {
                    return this.latin1Transcode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
                }
                if ((state_0 & 4) != 0 && TStringGuards.isSupportedEncoding(arg4Value) && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF8(arg5Value)) {
                    return this.utf8TranscodeRegular(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.brokenProfile, this.outOfMemoryProfile);
                }
                if ((state_0 & 8) != 0 && TStringGuards.isSupportedEncoding(arg4Value) && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF8(arg5Value)) {
                    return this.utf8TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.brokenProfile, this.outOfMemoryProfile);
                }
                if ((state_0 & 0x10) != 0 && TStringGuards.isUTF32(arg4Value) && TStringGuards.isUTF16(arg5Value)) {
                    return this.utf16Fixed32Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 0x20) != 0 && TStringGuards.isSupportedEncoding(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF16(arg5Value)) {
                    return this.utf16TranscodeRegular(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.outOfMemoryProfile);
                }
                if ((state_0 & 0x40) != 0 && TStringGuards.isSupportedEncoding(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF16(arg5Value)) {
                    return this.utf16TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.outOfMemoryProfile);
                }
                if ((state_0 & 0x80) != 0 && !TStringGuards.isUTF16(arg4Value) && TStringGuards.isSupportedEncoding(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF32(arg5Value)) {
                    return this.utf32TranscodeRegular(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
                }
                if ((state_0 & 0x100) != 0 && TStringGuards.isSupportedEncoding(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF32(arg5Value)) {
                    return TStringInternalNodes.TransCodeIntlNode.utf32TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 0x200) != 0 && TStringGuards.isUTF16(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF32(arg5Value)) {
                    return this.utf32TranscodeUTF16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
                }
                if ((state_0 & 0x400) != 0 && (s10_ = this.unsupported_cache) != null && (TStringGuards.isUnsupportedEncoding(arg4Value) || TStringGuards.isUnsupportedEncoding(arg5Value))) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s10_.outOfMemoryProfile_, s10_.nativeProfile_, s10_.fromBufferWithStringCompactionNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value, TruffleString.Encoding arg5Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isSupportedEncoding(arg4Value) && (TStringGuards.isAscii(arg5Value) || TStringGuards.isBytes(arg5Value))) {
                    this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.targetAscii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
                    return truffleString;
                }
                if (TStringGuards.isSupportedEncoding(arg4Value) && TStringGuards.isLatin1(arg5Value)) {
                    this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.latin1Transcode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
                    return truffleString;
                }
                if (TStringGuards.isSupportedEncoding(arg4Value) && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF8(arg5Value)) {
                    this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
                    this.brokenProfile = this.brokenProfile == null ? ConditionProfile.create() : this.brokenProfile;
                    this.outOfMemoryProfile = this.outOfMemoryProfile == null ? BranchProfile.create() : this.outOfMemoryProfile;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.utf8TranscodeRegular(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.brokenProfile, this.outOfMemoryProfile);
                    return truffleString;
                }
                if (TStringGuards.isSupportedEncoding(arg4Value) && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF8(arg5Value)) {
                    this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
                    this.brokenProfile = this.brokenProfile == null ? ConditionProfile.create() : this.brokenProfile;
                    this.outOfMemoryProfile = this.outOfMemoryProfile == null ? BranchProfile.create() : this.outOfMemoryProfile;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.utf8TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.brokenProfile, this.outOfMemoryProfile);
                    return truffleString;
                }
                if (TStringGuards.isUTF32(arg4Value) && TStringGuards.isUTF16(arg5Value)) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.utf16Fixed32Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return truffleString;
                }
                if (TStringGuards.isSupportedEncoding(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF16(arg5Value)) {
                    this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
                    this.outOfMemoryProfile = this.outOfMemoryProfile == null ? BranchProfile.create() : this.outOfMemoryProfile;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.utf16TranscodeRegular(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.outOfMemoryProfile);
                    return truffleString;
                }
                if (TStringGuards.isSupportedEncoding(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF16(arg5Value)) {
                    this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
                    this.outOfMemoryProfile = this.outOfMemoryProfile == null ? BranchProfile.create() : this.outOfMemoryProfile;
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.utf16TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode, this.outOfMemoryProfile);
                    return truffleString;
                }
                if (!TStringGuards.isUTF16(arg4Value) && TStringGuards.isSupportedEncoding(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF32(arg5Value)) {
                    this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.utf32TranscodeRegular(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
                    return truffleString;
                }
                if (TStringGuards.isSupportedEncoding(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF32(arg5Value)) {
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TStringInternalNodes.TransCodeIntlNode.utf32TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return truffleString;
                }
                if (TStringGuards.isUTF16(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF32(arg5Value)) {
                    this.iteratorNextNode = super.insert(this.iteratorNextNode == null ? TruffleStringIterator.NextNode.create() : this.iteratorNextNode);
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.utf32TranscodeUTF16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.iteratorNextNode);
                    return truffleString;
                }
                if (TStringGuards.isUnsupportedEncoding(arg4Value) || TStringGuards.isUnsupportedEncoding(arg5Value)) {
                    UnsupportedData s10_ = super.insert(new UnsupportedData());
                    s10_.outOfMemoryProfile_ = BranchProfile.create();
                    s10_.nativeProfile_ = ConditionProfile.create();
                    s10_.fromBufferWithStringCompactionNode_ = s10_.insertAccessor(FromBufferWithStringCompactionNodeGen.create());
                    VarHandle.storeStoreFence();
                    this.unsupported_cache = s10_;
                    this.state_0_ = state_0 |= 0x400;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s10_.outOfMemoryProfile_, s10_.nativeProfile_, s10_.fromBufferWithStringCompactionNode_);
                    return truffleString;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value});
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

        public static TStringInternalNodes.TransCodeIntlNode create() {
            return new TransCodeIntlNodeGen();
        }

        public static TStringInternalNodes.TransCodeIntlNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.TransCodeIntlNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.TransCodeIntlNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            TruffleString execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value, TruffleString.Encoding arg5Value) {
                if (TStringGuards.isSupportedEncoding(arg4Value) && (TStringGuards.isAscii(arg5Value) || TStringGuards.isBytes(arg5Value))) {
                    return this.targetAscii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleStringIterator.NextNode.getUncached());
                }
                if (TStringGuards.isSupportedEncoding(arg4Value) && TStringGuards.isLatin1(arg5Value)) {
                    return this.latin1Transcode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleStringIterator.NextNode.getUncached());
                }
                if (TStringGuards.isSupportedEncoding(arg4Value) && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF8(arg5Value)) {
                    return this.utf8TranscodeRegular(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleStringIterator.NextNode.getUncached(), ConditionProfile.getUncached(), BranchProfile.getUncached());
                }
                if (TStringGuards.isSupportedEncoding(arg4Value) && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF8(arg5Value)) {
                    return this.utf8TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleStringIterator.NextNode.getUncached(), ConditionProfile.getUncached(), BranchProfile.getUncached());
                }
                if (TStringGuards.isUTF32(arg4Value) && TStringGuards.isUTF16(arg5Value)) {
                    return this.utf16Fixed32Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isSupportedEncoding(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF16(arg5Value)) {
                    return this.utf16TranscodeRegular(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleStringIterator.NextNode.getUncached(), BranchProfile.getUncached());
                }
                if (TStringGuards.isSupportedEncoding(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF16(arg5Value)) {
                    return this.utf16TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleStringIterator.NextNode.getUncached(), BranchProfile.getUncached());
                }
                if (!TStringGuards.isUTF16(arg4Value) && TStringGuards.isSupportedEncoding(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF32(arg5Value)) {
                    return this.utf32TranscodeRegular(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleStringIterator.NextNode.getUncached());
                }
                if (TStringGuards.isSupportedEncoding(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF32(arg5Value)) {
                    return TStringInternalNodes.TransCodeIntlNode.utf32TranscodeLarge(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isUTF16(arg4Value) && !TStringGuards.isFixedWidth(arg3Value) && !TStringInternalNodes.TransCodeIntlNode.isLarge(arg2Value) && TStringGuards.isUTF32(arg5Value)) {
                    return this.utf32TranscodeUTF16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleStringIterator.NextNode.getUncached());
                }
                if (TStringGuards.isUnsupportedEncoding(arg4Value) || TStringGuards.isUnsupportedEncoding(arg5Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, BranchProfile.getUncached(), ConditionProfile.getUncached(), FromBufferWithStringCompactionNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value});
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

        @GeneratedBy(value=TStringInternalNodes.TransCodeIntlNode.class)
        private static final class UnsupportedData
        extends Node {
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

    @GeneratedBy(value=TStringInternalNodes.TransCodeNode.class)
    static final class TransCodeNodeGen
    extends TStringInternalNodes.TransCodeNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.asciiBytesInvalidProfile_ = ConditionProfile.create();
                this.transCodeIntlNode_ = super.insert(TransCodeIntlNodeGen.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.transcode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.asciiBytesInvalidProfile_, this.transCodeIntlNode_);
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

        public static TStringInternalNodes.TransCodeNode create() {
            return new TransCodeNodeGen();
        }

        public static TStringInternalNodes.TransCodeNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.TransCodeNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.TransCodeNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            TruffleString execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, TruffleString.Encoding arg4Value) {
                return this.transcode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, ConditionProfile.getUncached(), TransCodeIntlNodeGen.getUncached());
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

    @GeneratedBy(value=TStringInternalNodes.CreateJavaStringNode.class)
    static final class CreateJavaStringNodeGen
    extends TStringInternalNodes.CreateJavaStringNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private String executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.reuseProfile_ = ConditionProfile.create();
                this.getCodeRangeNode_ = super.insert(GetCodeRangeNodeGen.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                String string = this.createJavaString(arg0Value, arg1Value, this.reuseProfile_, this.getCodeRangeNode_);
                return string;
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

        public static TStringInternalNodes.CreateJavaStringNode create() {
            return new CreateJavaStringNodeGen();
        }

        public static TStringInternalNodes.CreateJavaStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.CreateJavaStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.CreateJavaStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

    @GeneratedBy(value=TStringInternalNodes.ToJavaStringNode.class)
    static final class ToJavaStringNodeGen
    extends TStringInternalNodes.ToJavaStringNode {
        private static final Uncached UNCACHED = new Uncached();
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
                    return TStringInternalNodes.ToJavaStringNode.doGeneric(arg0Value, arg1Value, this.generic_getCodePointLengthNode_, this.generic_getCodeRangeNode_, this.generic_transCodeNode_, this.createStringNode);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        private TruffleString executeAndSpecialize(TruffleString arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arg0Value.isCompatibleTo(TruffleString.Encoding.UTF_16)) {
                    this.createStringNode = super.insert(this.createStringNode == null ? CreateJavaStringNodeGen.create() : this.createStringNode);
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TStringInternalNodes.ToJavaStringNode.doUTF16(arg0Value, arg1Value, this.createStringNode);
                    return truffleString;
                }
                if (!arg0Value.isCompatibleTo(TruffleString.Encoding.UTF_16)) {
                    this.generic_getCodePointLengthNode_ = super.insert(GetCodePointLengthNodeGen.create());
                    this.generic_getCodeRangeNode_ = super.insert(GetCodeRangeNodeGen.create());
                    this.generic_transCodeNode_ = super.insert(TransCodeNodeGen.create());
                    this.createStringNode = super.insert(this.createStringNode == null ? CreateJavaStringNodeGen.create() : this.createStringNode);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TStringInternalNodes.ToJavaStringNode.doGeneric(arg0Value, arg1Value, this.generic_getCodePointLengthNode_, this.generic_getCodeRangeNode_, this.generic_transCodeNode_, this.createStringNode);
                    return truffleString;
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

        public static TStringInternalNodes.ToJavaStringNode create() {
            return new ToJavaStringNodeGen();
        }

        public static TStringInternalNodes.ToJavaStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.ToJavaStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.ToJavaStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            TruffleString execute(TruffleString arg0Value, Object arg1Value) {
                if (arg0Value.isCompatibleTo(TruffleString.Encoding.UTF_16)) {
                    return TStringInternalNodes.ToJavaStringNode.doUTF16(arg0Value, arg1Value, CreateJavaStringNodeGen.getUncached());
                }
                if (!arg0Value.isCompatibleTo(TruffleString.Encoding.UTF_16)) {
                    return TStringInternalNodes.ToJavaStringNode.doGeneric(arg0Value, arg1Value, TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), TransCodeNodeGen.getUncached(), CreateJavaStringNodeGen.getUncached());
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

    @GeneratedBy(value=TStringInternalNodes.FromJavaStringUTF16Node.class)
    static final class FromJavaStringUTF16NodeGen
    extends TStringInternalNodes.FromJavaStringUTF16Node {
        private static final Uncached UNCACHED = new Uncached();
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
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(String arg0Value, int arg1Value, int arg2Value, boolean arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.utf16CompactProfile_ = ConditionProfile.create();
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doNonEmpty(arg0Value, arg1Value, arg2Value, arg3Value, this.utf16CompactProfile_);
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

        public static TStringInternalNodes.FromJavaStringUTF16Node create() {
            return new FromJavaStringUTF16NodeGen();
        }

        public static TStringInternalNodes.FromJavaStringUTF16Node getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.FromJavaStringUTF16Node.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.FromJavaStringUTF16Node {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
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

    @GeneratedBy(value=TStringInternalNodes.ParseDoubleNode.class)
    static final class ParseDoubleNodeGen
    extends TStringInternalNodes.ParseDoubleNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private ParseData parse_cache;

        private ParseDoubleNodeGen() {
        }

        @Override
        @ExplodeLoop
        double execute(AbstractTruffleString arg0Value, Object arg1Value) throws TruffleString.NumberFormatException {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                ParseData s0_ = this.parse_cache;
                while (s0_ != null) {
                    if (s0_.cachedStride_ == arg0Value.stride()) {
                        return this.doParse(arg0Value, arg1Value, s0_.cachedStride_, s0_.errorProfile_);
                    }
                    s0_ = s0_.next_;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        private double executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value) throws TruffleString.NumberFormatException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int cachedStride__;
                int state_0 = this.state_0_;
                int count0_ = 0;
                ParseData s0_ = this.parse_cache;
                if (state_0 != 0) {
                    while (s0_ != null && s0_.cachedStride_ != arg0Value.stride()) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && (cachedStride__ = arg0Value.stride()) == arg0Value.stride() && count0_ < 3) {
                    s0_ = new ParseData(this.parse_cache);
                    s0_.cachedStride_ = cachedStride__;
                    s0_.errorProfile_ = BranchProfile.create();
                    VarHandle.storeStoreFence();
                    this.parse_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    double d = this.doParse(arg0Value, arg1Value, s0_.cachedStride_, s0_.errorProfile_);
                    return d;
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
            ParseData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.parse_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TStringInternalNodes.ParseDoubleNode create() {
            return new ParseDoubleNodeGen();
        }

        public static TStringInternalNodes.ParseDoubleNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.ParseDoubleNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.ParseDoubleNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            double execute(AbstractTruffleString arg0Value, Object arg1Value) throws TruffleString.NumberFormatException {
                if (arg0Value.stride() == arg0Value.stride()) {
                    return this.doParse(arg0Value, arg1Value, arg0Value.stride(), BranchProfile.getUncached());
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

        @GeneratedBy(value=TStringInternalNodes.ParseDoubleNode.class)
        private static final class ParseData {
            @CompilerDirectives.CompilationFinal
            ParseData next_;
            @CompilerDirectives.CompilationFinal
            int cachedStride_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorProfile_;

            ParseData(ParseData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=TStringInternalNodes.ParseLongNode.class)
    static final class ParseLongNodeGen
    extends TStringInternalNodes.ParseLongNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private Do7BitData do7Bit_cache;
        @Node.Child
        private TruffleStringIterator.NextNode parseLong_nextNode_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile parseLong_errorProfile_;

        private ParseLongNodeGen() {
        }

        @Override
        @ExplodeLoop
        long execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value) throws TruffleString.NumberFormatException {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.is7Bit(arg2Value)) {
                    Do7BitData s0_ = this.do7Bit_cache;
                    while (s0_ != null) {
                        if (s0_.cachedStride_ == arg0Value.stride()) {
                            return TStringInternalNodes.ParseLongNode.do7Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStride_, s0_.errorProfile_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0 && !TStringGuards.is7Bit(arg2Value)) {
                    return TStringInternalNodes.ParseLongNode.parseLong(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.parseLong_nextNode_, this.parseLong_errorProfile_);
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
                    int cachedStride__;
                    int count0_ = 0;
                    Do7BitData s0_ = this.do7Bit_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && s0_.cachedStride_ != arg0Value.stride()) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && (cachedStride__ = arg0Value.stride()) == arg0Value.stride() && count0_ < 3) {
                        s0_ = new Do7BitData(this.do7Bit_cache);
                        s0_.cachedStride_ = cachedStride__;
                        s0_.errorProfile_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.do7Bit_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        long l = TStringInternalNodes.ParseLongNode.do7Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStride_, s0_.errorProfile_);
                        return l;
                    }
                }
                if (!TStringGuards.is7Bit(arg2Value)) {
                    this.parseLong_nextNode_ = super.insert(TruffleStringIterator.NextNode.create());
                    this.parseLong_errorProfile_ = BranchProfile.create();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    long l = TStringInternalNodes.ParseLongNode.parseLong(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.parseLong_nextNode_, this.parseLong_errorProfile_);
                    return l;
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
            Do7BitData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.do7Bit_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TStringInternalNodes.ParseLongNode create() {
            return new ParseLongNodeGen();
        }

        public static TStringInternalNodes.ParseLongNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.ParseLongNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.ParseLongNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            long execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value) throws TruffleString.NumberFormatException {
                if (TStringGuards.is7Bit(arg2Value) && arg0Value.stride() == arg0Value.stride()) {
                    return TStringInternalNodes.ParseLongNode.do7Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg0Value.stride(), BranchProfile.getUncached());
                }
                if (!TStringGuards.is7Bit(arg2Value)) {
                    return TStringInternalNodes.ParseLongNode.parseLong(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TruffleStringIterator.NextNode.getUncached(), BranchProfile.getUncached());
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

        @GeneratedBy(value=TStringInternalNodes.ParseLongNode.class)
        private static final class Do7BitData {
            @CompilerDirectives.CompilationFinal
            Do7BitData next_;
            @CompilerDirectives.CompilationFinal
            int cachedStride_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorProfile_;

            Do7BitData(Do7BitData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=TStringInternalNodes.ParseIntNode.class)
    static final class ParseIntNodeGen
    extends TStringInternalNodes.ParseIntNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private Do7BitData do7Bit_cache;
        @Node.Child
        private TruffleStringIterator.NextNode generic_nextNode_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile generic_errorProfile_;

        private ParseIntNodeGen() {
        }

        @Override
        @ExplodeLoop
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value) throws TruffleString.NumberFormatException {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.is7Bit(arg2Value)) {
                    Do7BitData s0_ = this.do7Bit_cache;
                    while (s0_ != null) {
                        if (s0_.cachedStride_ == arg0Value.stride()) {
                            return TStringInternalNodes.ParseIntNode.do7Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStride_, s0_.errorProfile_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0 && !TStringGuards.is7Bit(arg2Value)) {
                    return TStringInternalNodes.ParseIntNode.doGeneric(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.generic_nextNode_, this.generic_errorProfile_);
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
                    int cachedStride__;
                    int count0_ = 0;
                    Do7BitData s0_ = this.do7Bit_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && s0_.cachedStride_ != arg0Value.stride()) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && (cachedStride__ = arg0Value.stride()) == arg0Value.stride() && count0_ < 3) {
                        s0_ = new Do7BitData(this.do7Bit_cache);
                        s0_.cachedStride_ = cachedStride__;
                        s0_.errorProfile_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.do7Bit_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        int n = TStringInternalNodes.ParseIntNode.do7Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStride_, s0_.errorProfile_);
                        return n;
                    }
                }
                if (!TStringGuards.is7Bit(arg2Value)) {
                    this.generic_nextNode_ = super.insert(TruffleStringIterator.NextNode.create());
                    this.generic_errorProfile_ = BranchProfile.create();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.ParseIntNode.doGeneric(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, this.generic_nextNode_, this.generic_errorProfile_);
                    return n;
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
            Do7BitData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.do7Bit_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TStringInternalNodes.ParseIntNode create() {
            return new ParseIntNodeGen();
        }

        public static TStringInternalNodes.ParseIntNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.ParseIntNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.ParseIntNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value) throws TruffleString.NumberFormatException {
                if (TStringGuards.is7Bit(arg2Value) && arg0Value.stride() == arg0Value.stride()) {
                    return TStringInternalNodes.ParseIntNode.do7Bit(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg0Value.stride(), BranchProfile.getUncached());
                }
                if (!TStringGuards.is7Bit(arg2Value)) {
                    return TStringInternalNodes.ParseIntNode.doGeneric(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TruffleStringIterator.NextNode.getUncached(), BranchProfile.getUncached());
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

        @GeneratedBy(value=TStringInternalNodes.ParseIntNode.class)
        private static final class Do7BitData {
            @CompilerDirectives.CompilationFinal
            Do7BitData next_;
            @CompilerDirectives.CompilationFinal
            int cachedStride_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorProfile_;

            Do7BitData(Do7BitData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=TStringInternalNodes.CalcStringAttributesInnerNode.class)
    static final class CalcStringAttributesInnerNodeGen
    extends TStringInternalNodes.CalcStringAttributesInnerNode {
        private static final Uncached UNCACHED = new Uncached();
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
        long execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value) {
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
                if ((state_0 & 0x10) != 0 && TStringGuards.isUTF16(arg5Value) && TStringGuards.isBrokenMultiByteOrUnknown(arg6Value)) {
                    return this.doUTF16Unknown(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if ((state_0 & 0x20) != 0 && arg4Value == 2) {
                    return this.doUTF32(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if ((state_0 & 0x40) != 0 && TStringGuards.isUnsupportedEncoding(arg5Value)) {
                    return this.doGeneric(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.generic_validCharacterProfile_, this.generic_fixedWidthProfile_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
        }

        private long executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if ((TStringGuards.is8Bit(arg6Value) || TStringGuards.isAsciiBytesOrLatin1(arg5Value)) && arg4Value == 0) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    long l = this.doLatin1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return l;
                }
                if (TStringGuards.isUpTo16Bit(arg6Value) && arg4Value == 1) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    long l = this.doBMP(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return l;
                }
                if (TStringGuards.isUTF8(arg5Value) && !TStringGuards.isFixedWidth(arg6Value)) {
                    this.uTF8_brokenProfile_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    long l = this.doUTF8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.uTF8_brokenProfile_);
                    return l;
                }
                if (TStringGuards.isUTF16(arg5Value) && TStringGuards.isValidMultiByte(arg6Value)) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    long l = this.doUTF16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return l;
                }
                if (TStringGuards.isUTF16(arg5Value) && TStringGuards.isBrokenMultiByteOrUnknown(arg6Value)) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    long l = this.doUTF16Unknown(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return l;
                }
                if (arg4Value == 2) {
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    long l = this.doUTF32(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return l;
                }
                if (TStringGuards.isUnsupportedEncoding(arg5Value)) {
                    this.generic_validCharacterProfile_ = ConditionProfile.create();
                    this.generic_fixedWidthProfile_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    long l = this.doGeneric(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.generic_validCharacterProfile_, this.generic_fixedWidthProfile_);
                    return l;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

        public static TStringInternalNodes.CalcStringAttributesInnerNode create() {
            return new CalcStringAttributesInnerNodeGen();
        }

        public static TStringInternalNodes.CalcStringAttributesInnerNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.CalcStringAttributesInnerNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.CalcStringAttributesInnerNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            long execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value) {
                if ((TStringGuards.is8Bit(arg6Value) || TStringGuards.isAsciiBytesOrLatin1(arg5Value)) && arg4Value == 0) {
                    return this.doLatin1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (TStringGuards.isUpTo16Bit(arg6Value) && arg4Value == 1) {
                    return this.doBMP(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (TStringGuards.isUTF8(arg5Value) && !TStringGuards.isFixedWidth(arg6Value)) {
                    return this.doUTF8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, ConditionProfile.getUncached());
                }
                if (TStringGuards.isUTF16(arg5Value) && TStringGuards.isValidMultiByte(arg6Value)) {
                    return this.doUTF16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (TStringGuards.isUTF16(arg5Value) && TStringGuards.isBrokenMultiByteOrUnknown(arg6Value)) {
                    return this.doUTF16Unknown(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (arg4Value == 2) {
                    return this.doUTF32(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (TStringGuards.isUnsupportedEncoding(arg5Value)) {
                    return this.doGeneric(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, ConditionProfile.getUncached(), ConditionProfile.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

    @GeneratedBy(value=TStringInternalNodes.CalcStringAttributesNode.class)
    static final class CalcStringAttributesNodeGen
    extends TStringInternalNodes.CalcStringAttributesNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TStringInternalNodes.CalcStringAttributesInnerNode notAscii_calcNode_;

        private CalcStringAttributesNodeGen() {
        }

        @Override
        long execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value) {
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

        private long executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.is7Bit(arg6Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    long l = this.ascii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return l;
                }
                if (!TStringGuards.is7Bit(arg6Value)) {
                    this.notAscii_calcNode_ = super.insert(CalcStringAttributesInnerNodeGen.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    long l = this.notAscii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.notAscii_calcNode_);
                    return l;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

        public static TStringInternalNodes.CalcStringAttributesNode create() {
            return new CalcStringAttributesNodeGen();
        }

        public static TStringInternalNodes.CalcStringAttributesNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.CalcStringAttributesNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.CalcStringAttributesNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            long execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value) {
                if (TStringGuards.is7Bit(arg6Value)) {
                    return this.ascii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (!TStringGuards.is7Bit(arg6Value)) {
                    return this.notAscii(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, CalcStringAttributesInnerNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

    @GeneratedBy(value=TStringInternalNodes.StrideFromCodeRangeNode.class)
    static final class StrideFromCodeRangeNodeGen
    extends TStringInternalNodes.StrideFromCodeRangeNode {
        private static final Uncached UNCACHED = new Uncached();
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
                this.state_0_ = state_0 |= 1;
                return this.doUTF16(arg0Value, arg1Value);
            }
            if (TStringGuards.isUTF32(arg1Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doUTF32(arg0Value, arg1Value);
            }
            if (!TStringGuards.isUTF16(arg1Value) && !TStringGuards.isUTF32(arg1Value)) {
                this.state_0_ = state_0 |= 4;
                return this.doOther(arg0Value, arg1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, new Object[]{arg0Value, arg1Value});
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

        public static TStringInternalNodes.StrideFromCodeRangeNode create() {
            return new StrideFromCodeRangeNodeGen();
        }

        public static TStringInternalNodes.StrideFromCodeRangeNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.StrideFromCodeRangeNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.StrideFromCodeRangeNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(int arg0Value, TruffleString.Encoding arg1Value) {
                if (TStringGuards.isUTF16(arg1Value)) {
                    return this.doUTF16(arg0Value, arg1Value);
                }
                if (TStringGuards.isUTF32(arg1Value)) {
                    return this.doUTF32(arg0Value, arg1Value);
                }
                if (!TStringGuards.isUTF16(arg1Value) && !TStringGuards.isUTF32(arg1Value)) {
                    return this.doOther(arg0Value, arg1Value);
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

    @GeneratedBy(value=TStringInternalNodes.LastIndexOfStringRawNode.class)
    static final class LastIndexOfStringRawNodeGen
    extends TStringInternalNodes.LastIndexOfStringRawNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TStringOpsNodes.RawLastIndexOfStringNode lios8SameEncoding_indexOfStringNode_;
        @Node.Child
        private UnsupportedData unsupported_cache;

        private LastIndexOfStringRawNodeGen() {
        }

        @Override
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, AbstractTruffleString arg3Value, Object arg4Value, int arg5Value, int arg6Value, int arg7Value, byte[] arg8Value, TruffleString.Encoding arg9Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                UnsupportedData s1_;
                if ((state_0 & 1) != 0 && (TStringGuards.isSupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value))) {
                    return TStringInternalNodes.LastIndexOfStringRawNode.lios8SameEncoding(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, this.lios8SameEncoding_indexOfStringNode_);
                }
                if ((state_0 & 2) != 0 && (s1_ = this.unsupported_cache) != null && TStringGuards.isUnsupportedEncoding(arg9Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, s1_.nextNodeA_, s1_.prevNodeA_, s1_.prevNodeB_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, AbstractTruffleString arg3Value, Object arg4Value, int arg5Value, int arg6Value, int arg7Value, byte[] arg8Value, TruffleString.Encoding arg9Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isSupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value)) {
                    this.lios8SameEncoding_indexOfStringNode_ = super.insert(TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.LastIndexOfStringRawNode.lios8SameEncoding(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, this.lios8SameEncoding_indexOfStringNode_);
                    return n;
                }
                if (TStringGuards.isUnsupportedEncoding(arg9Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    UnsupportedData s1_ = super.insert(new UnsupportedData());
                    s1_.nextNodeA_ = s1_.insertAccessor(TruffleStringIterator.NextNode.create());
                    s1_.prevNodeA_ = s1_.insertAccessor(TruffleStringIterator.PreviousNode.create());
                    s1_.prevNodeB_ = s1_.insertAccessor(TruffleStringIterator.PreviousNode.create());
                    VarHandle.storeStoreFence();
                    this.unsupported_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, s1_.nextNodeA_, s1_.prevNodeA_, s1_.prevNodeB_);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value});
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

        public static TStringInternalNodes.LastIndexOfStringRawNode create() {
            return new LastIndexOfStringRawNodeGen();
        }

        public static TStringInternalNodes.LastIndexOfStringRawNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.LastIndexOfStringRawNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.LastIndexOfStringRawNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, AbstractTruffleString arg3Value, Object arg4Value, int arg5Value, int arg6Value, int arg7Value, byte[] arg8Value, TruffleString.Encoding arg9Value) {
                if (TStringGuards.isSupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value)) {
                    return TStringInternalNodes.LastIndexOfStringRawNode.lios8SameEncoding(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.getUncached());
                }
                if (TStringGuards.isUnsupportedEncoding(arg9Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, TruffleStringIterator.NextNode.getUncached(), TruffleStringIterator.PreviousNode.getUncached(), TruffleStringIterator.PreviousNode.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value});
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

        @GeneratedBy(value=TStringInternalNodes.LastIndexOfStringRawNode.class)
        private static final class UnsupportedData
        extends Node {
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

    @GeneratedBy(value=TStringInternalNodes.LastIndexOfStringNode.class)
    static final class LastIndexOfStringNodeGen
    extends TStringInternalNodes.LastIndexOfStringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TStringOpsNodes.RawLastIndexOfStringNode direct_indexOfStringNode_;
        @Node.Child
        private DecodeData decode_cache;

        private LastIndexOfStringNodeGen() {
        }

        @Override
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, AbstractTruffleString arg3Value, Object arg4Value, int arg5Value, int arg6Value, int arg7Value, TruffleString.Encoding arg8Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                DecodeData s1_;
                if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                    return TStringInternalNodes.LastIndexOfStringNode.direct(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, this.direct_indexOfStringNode_);
                }
                if ((state_0 & 2) != 0 && (s1_ = this.decode_cache) != null && !TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                    return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, s1_.nextNodeA_, s1_.prevNodeA_, s1_.prevNodeB_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, AbstractTruffleString arg3Value, Object arg4Value, int arg5Value, int arg6Value, int arg7Value, TruffleString.Encoding arg8Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                    this.direct_indexOfStringNode_ = super.insert(TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.LastIndexOfStringNode.direct(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, this.direct_indexOfStringNode_);
                    return n;
                }
                if (!TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                    DecodeData s1_ = super.insert(new DecodeData());
                    s1_.nextNodeA_ = s1_.insertAccessor(TruffleStringIterator.NextNode.create());
                    s1_.prevNodeA_ = s1_.insertAccessor(TruffleStringIterator.PreviousNode.create());
                    s1_.prevNodeB_ = s1_.insertAccessor(TruffleStringIterator.PreviousNode.create());
                    VarHandle.storeStoreFence();
                    this.decode_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, s1_.nextNodeA_, s1_.prevNodeA_, s1_.prevNodeB_);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value});
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

        public static TStringInternalNodes.LastIndexOfStringNode create() {
            return new LastIndexOfStringNodeGen();
        }

        public static TStringInternalNodes.LastIndexOfStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.LastIndexOfStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.LastIndexOfStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, AbstractTruffleString arg3Value, Object arg4Value, int arg5Value, int arg6Value, int arg7Value, TruffleString.Encoding arg8Value) {
                if (TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                    return TStringInternalNodes.LastIndexOfStringNode.direct(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, TStringOpsNodesFactory.RawLastIndexOfStringNodeGen.getUncached());
                }
                if (!TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                    return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, TruffleStringIterator.NextNode.getUncached(), TruffleStringIterator.PreviousNode.getUncached(), TruffleStringIterator.PreviousNode.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value});
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

        @GeneratedBy(value=TStringInternalNodes.LastIndexOfStringNode.class)
        private static final class DecodeData
        extends Node {
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
    }

    @GeneratedBy(value=TStringInternalNodes.IndexOfStringRawNode.class)
    static final class IndexOfStringRawNodeGen
    extends TStringInternalNodes.IndexOfStringRawNode {
        private static final Uncached UNCACHED = new Uncached();
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
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, AbstractTruffleString arg3Value, Object arg4Value, int arg5Value, int arg6Value, int arg7Value, byte[] arg8Value, TruffleString.Encoding arg9Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && (TStringGuards.isSupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value))) {
                    return TStringInternalNodes.IndexOfStringRawNode.supported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, this.supported_indexOfStringNode_);
                }
                if ((state_0 & 2) != 0 && TStringGuards.isUnsupportedEncoding(arg9Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, this.unsupported_nextNodeA_, this.unsupported_nextNodeB_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, AbstractTruffleString arg3Value, Object arg4Value, int arg5Value, int arg6Value, int arg7Value, byte[] arg8Value, TruffleString.Encoding arg9Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isSupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value)) {
                    this.supported_indexOfStringNode_ = super.insert(TStringOpsNodesFactory.RawIndexOfStringNodeGen.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.IndexOfStringRawNode.supported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, this.supported_indexOfStringNode_);
                    return n;
                }
                if (TStringGuards.isUnsupportedEncoding(arg9Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    this.unsupported_nextNodeA_ = super.insert(TruffleStringIterator.NextNode.create());
                    this.unsupported_nextNodeB_ = super.insert(TruffleStringIterator.NextNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, this.unsupported_nextNodeA_, this.unsupported_nextNodeB_);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value});
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

        public static TStringInternalNodes.IndexOfStringRawNode create() {
            return new IndexOfStringRawNodeGen();
        }

        public static TStringInternalNodes.IndexOfStringRawNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.IndexOfStringRawNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.IndexOfStringRawNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, AbstractTruffleString arg3Value, Object arg4Value, int arg5Value, int arg6Value, int arg7Value, byte[] arg8Value, TruffleString.Encoding arg9Value) {
                if (TStringGuards.isSupportedEncoding(arg9Value) || TStringGuards.isFixedWidth(arg2Value)) {
                    return TStringInternalNodes.IndexOfStringRawNode.supported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, TStringOpsNodesFactory.RawIndexOfStringNodeGen.getUncached());
                }
                if (TStringGuards.isUnsupportedEncoding(arg9Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, TruffleStringIterator.NextNode.getUncached(), TruffleStringIterator.NextNode.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value});
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

    @GeneratedBy(value=TStringInternalNodes.IndexOfStringNode.class)
    static final class IndexOfStringNodeGen
    extends TStringInternalNodes.IndexOfStringNode {
        private static final Uncached UNCACHED = new Uncached();
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
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, AbstractTruffleString arg3Value, Object arg4Value, int arg5Value, int arg6Value, int arg7Value, TruffleString.Encoding arg8Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                    return TStringInternalNodes.IndexOfStringNode.direct(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, this.direct_indexOfStringNode_);
                }
                if ((state_0 & 2) != 0 && !TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                    return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, this.decode_nextNodeA_, this.decode_nextNodeB_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, AbstractTruffleString arg3Value, Object arg4Value, int arg5Value, int arg6Value, int arg7Value, TruffleString.Encoding arg8Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                    this.direct_indexOfStringNode_ = super.insert(TStringOpsNodesFactory.RawIndexOfStringNodeGen.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.IndexOfStringNode.direct(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, this.direct_indexOfStringNode_);
                    return n;
                }
                if (!TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                    this.decode_nextNodeA_ = super.insert(TruffleStringIterator.NextNode.create());
                    this.decode_nextNodeB_ = super.insert(TruffleStringIterator.NextNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, this.decode_nextNodeA_, this.decode_nextNodeB_);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value});
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

        public static TStringInternalNodes.IndexOfStringNode create() {
            return new IndexOfStringNodeGen();
        }

        public static TStringInternalNodes.IndexOfStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.IndexOfStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.IndexOfStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, AbstractTruffleString arg3Value, Object arg4Value, int arg5Value, int arg6Value, int arg7Value, TruffleString.Encoding arg8Value) {
                if (TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                    return TStringInternalNodes.IndexOfStringNode.direct(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, TStringOpsNodesFactory.RawIndexOfStringNodeGen.getUncached());
                }
                if (!TStringGuards.isFixedWidth(arg2Value, arg5Value)) {
                    return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, TruffleStringIterator.NextNode.getUncached(), TruffleStringIterator.NextNode.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value});
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

    @GeneratedBy(value=TStringInternalNodes.RegionEqualsNode.class)
    static final class RegionEqualsNodeGen
    extends TStringInternalNodes.RegionEqualsNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleStringIterator.NextNode decode_nextNodeA_;
        @Node.Child
        private TruffleStringIterator.NextNode decode_nextNodeB_;

        private RegionEqualsNodeGen() {
        }

        @Override
        boolean execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, AbstractTruffleString arg4Value, Object arg5Value, int arg6Value, int arg7Value, int arg8Value, TruffleString.Encoding arg9Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value, arg6Value)) {
                    return this.direct(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value);
                }
                if ((state_0 & 2) != 0 && !TStringGuards.isFixedWidth(arg2Value, arg6Value)) {
                    return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, this.decode_nextNodeA_, this.decode_nextNodeB_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value);
        }

        private boolean executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, AbstractTruffleString arg4Value, Object arg5Value, int arg6Value, int arg7Value, int arg8Value, TruffleString.Encoding arg9Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isFixedWidth(arg2Value, arg6Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.direct(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value);
                    return bl;
                }
                if (!TStringGuards.isFixedWidth(arg2Value, arg6Value)) {
                    this.decode_nextNodeA_ = super.insert(TruffleStringIterator.NextNode.create());
                    this.decode_nextNodeB_ = super.insert(TruffleStringIterator.NextNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, this.decode_nextNodeA_, this.decode_nextNodeB_);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value});
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

        public static TStringInternalNodes.RegionEqualsNode create() {
            return new RegionEqualsNodeGen();
        }

        public static TStringInternalNodes.RegionEqualsNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.RegionEqualsNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.RegionEqualsNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            boolean execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, AbstractTruffleString arg4Value, Object arg5Value, int arg6Value, int arg7Value, int arg8Value, TruffleString.Encoding arg9Value) {
                if (TStringGuards.isFixedWidth(arg2Value, arg6Value)) {
                    return this.direct(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value);
                }
                if (!TStringGuards.isFixedWidth(arg2Value, arg6Value)) {
                    return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value, TruffleStringIterator.NextNode.getUncached(), TruffleStringIterator.NextNode.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, arg7Value, arg8Value, arg9Value});
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

    @GeneratedBy(value=TStringInternalNodes.ConcatMaterializeBytesNode.class)
    static final class ConcatMaterializeBytesNodeGen
    extends TStringInternalNodes.ConcatMaterializeBytesNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private ConcatMaterializeBytesNodeGen() {
        }

        @Override
        byte[] execute(AbstractTruffleString arg0Value, Object arg1Value, AbstractTruffleString arg2Value, Object arg3Value, TruffleString.Encoding arg4Value, int arg5Value, int arg6Value) {
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

        private byte[] executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, AbstractTruffleString arg2Value, Object arg3Value, TruffleString.Encoding arg4Value, int arg5Value, int arg6Value) {
            int state_0 = this.state_0_;
            if (TStringGuards.isUTF16(arg4Value) || TStringGuards.isUTF32(arg4Value)) {
                this.state_0_ = state_0 |= 1;
                return this.doWithCompression(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }
            if (!TStringGuards.isUTF16(arg4Value) && !TStringGuards.isUTF32(arg4Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doNoCompression(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

        public static TStringInternalNodes.ConcatMaterializeBytesNode create() {
            return new ConcatMaterializeBytesNodeGen();
        }

        public static TStringInternalNodes.ConcatMaterializeBytesNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.ConcatMaterializeBytesNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.ConcatMaterializeBytesNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            byte[] execute(AbstractTruffleString arg0Value, Object arg1Value, AbstractTruffleString arg2Value, Object arg3Value, TruffleString.Encoding arg4Value, int arg5Value, int arg6Value) {
                if (TStringGuards.isUTF16(arg4Value) || TStringGuards.isUTF32(arg4Value)) {
                    return this.doWithCompression(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (!TStringGuards.isUTF16(arg4Value) && !TStringGuards.isUTF32(arg4Value)) {
                    return this.doNoCompression(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

    @GeneratedBy(value=TStringInternalNodes.ConcatEagerNode.class)
    static final class ConcatEagerNodeGen
    extends TStringInternalNodes.ConcatEagerNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ConcatData concat_cache;

        private ConcatEagerNodeGen() {
        }

        @Override
        TruffleString execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value, int arg3Value, int arg4Value, int arg5Value) {
            ConcatData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.concat_cache) != null) {
                return TStringInternalNodes.ConcatEagerNode.concat(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodePointLengthANode_, s0_.getCodePointLengthBNode_, s0_.materializeBytesNode_, s0_.calculateAttributesNode_, s0_.brokenProfile_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value, int arg3Value, int arg4Value, int arg5Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                ConcatData s0_ = super.insert(new ConcatData());
                s0_.toIndexableNodeA_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.toIndexableNodeB_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodePointLengthANode_ = s0_.insertAccessor(GetCodePointLengthNodeGen.create());
                s0_.getCodePointLengthBNode_ = s0_.insertAccessor(GetCodePointLengthNodeGen.create());
                s0_.materializeBytesNode_ = s0_.insertAccessor(ConcatMaterializeBytesNodeGen.create());
                s0_.calculateAttributesNode_ = s0_.insertAccessor(CalcStringAttributesNodeGen.create());
                s0_.brokenProfile_ = ConditionProfile.create();
                VarHandle.storeStoreFence();
                this.concat_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = TStringInternalNodes.ConcatEagerNode.concat(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.toIndexableNodeA_, s0_.toIndexableNodeB_, s0_.getCodePointLengthANode_, s0_.getCodePointLengthBNode_, s0_.materializeBytesNode_, s0_.calculateAttributesNode_, s0_.brokenProfile_);
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

        public static TStringInternalNodes.ConcatEagerNode create() {
            return new ConcatEagerNodeGen();
        }

        public static TStringInternalNodes.ConcatEagerNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.ConcatEagerNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.ConcatEagerNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            TruffleString execute(AbstractTruffleString arg0Value, AbstractTruffleString arg1Value, TruffleString.Encoding arg2Value, int arg3Value, int arg4Value, int arg5Value) {
                return TStringInternalNodes.ConcatEagerNode.concat(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, TruffleString.ToIndexableNode.getUncached(), TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), ConcatMaterializeBytesNodeGen.getUncached(), TStringInternalNodes.CalcStringAttributesNode.getUncached(), ConditionProfile.getUncached());
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

        @GeneratedBy(value=TStringInternalNodes.ConcatEagerNode.class)
        private static final class ConcatData
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
    }

    @GeneratedBy(value=TStringInternalNodes.SubstringNode.class)
    static final class SubstringNodeGen
    extends TStringInternalNodes.SubstringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TStringInternalNodes.CreateSubstringNode materializeSubstring_createSubstringNode_;
        @Node.Child
        private CreateLazySubstringData createLazySubstring_cache;

        private SubstringNodeGen() {
        }

        @Override
        TruffleString execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, boolean arg6Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                TruffleString arg0Value_;
                if ((state_0 & 1) != 0 && arg5Value == 0) {
                    return TStringInternalNodes.SubstringNode.lengthZero(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if ((state_0 & 2) != 0 && arg0Value instanceof TruffleString) {
                    arg0Value_ = (TruffleString)arg0Value;
                    if (arg4Value == 0 && arg5Value == TStringGuards.length(arg0Value_)) {
                        return TStringInternalNodes.SubstringNode.sameStr(arg0Value_, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    }
                }
                if ((state_0 & 4) != 0 && arg5Value > 0 && (arg5Value != TStringGuards.length(arg0Value) || arg0Value.isMutable()) && !arg6Value) {
                    return TStringInternalNodes.SubstringNode.materializeSubstring(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.materializeSubstring_createSubstringNode_);
                }
                if ((state_0 & 8) != 0 && arg0Value instanceof TruffleString) {
                    arg0Value_ = (TruffleString)arg0Value;
                    CreateLazySubstringData s3_ = this.createLazySubstring_cache;
                    if (s3_ != null && arg5Value > 0 && arg5Value != TStringGuards.length(arg0Value_) && arg6Value) {
                        return this.createLazySubstring(arg0Value_, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s3_.calcAttributesNode_, s3_.stride1MustMaterializeProfile_, s3_.stride2MustMaterializeProfile_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
        }

        private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, boolean arg6Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                TruffleString arg0Value_;
                int state_0 = this.state_0_;
                if (arg5Value == 0) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TStringInternalNodes.SubstringNode.lengthZero(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return truffleString;
                }
                if (arg0Value instanceof TruffleString) {
                    arg0Value_ = (TruffleString)arg0Value;
                    if (arg4Value == 0 && arg5Value == TStringGuards.length(arg0Value_)) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString = TStringInternalNodes.SubstringNode.sameStr(arg0Value_, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                        return truffleString;
                    }
                }
                if (arg5Value > 0 && (arg5Value != TStringGuards.length(arg0Value) || arg0Value.isMutable()) && !arg6Value) {
                    this.materializeSubstring_createSubstringNode_ = super.insert(CreateSubstringNodeGen.create());
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    arg0Value_ = TStringInternalNodes.SubstringNode.materializeSubstring(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.materializeSubstring_createSubstringNode_);
                    return arg0Value_;
                }
                if (arg0Value instanceof TruffleString) {
                    arg0Value_ = (TruffleString)arg0Value;
                    if (arg5Value > 0 && arg5Value != TStringGuards.length(arg0Value_) && arg6Value) {
                        CreateLazySubstringData s3_ = super.insert(new CreateLazySubstringData());
                        s3_.calcAttributesNode_ = s3_.insertAccessor(CalcStringAttributesNodeGen.create());
                        s3_.stride1MustMaterializeProfile_ = ConditionProfile.create();
                        s3_.stride2MustMaterializeProfile_ = ConditionProfile.create();
                        VarHandle.storeStoreFence();
                        this.createLazySubstring_cache = s3_;
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString = this.createLazySubstring(arg0Value_, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s3_.calcAttributesNode_, s3_.stride1MustMaterializeProfile_, s3_.stride2MustMaterializeProfile_);
                        return truffleString;
                    }
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

        public static TStringInternalNodes.SubstringNode create() {
            return new SubstringNodeGen();
        }

        public static TStringInternalNodes.SubstringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.SubstringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.SubstringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            TruffleString execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, boolean arg6Value) {
                TruffleString arg0Value_;
                if (arg5Value == 0) {
                    return TStringInternalNodes.SubstringNode.lengthZero(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (arg0Value instanceof TruffleString) {
                    arg0Value_ = (TruffleString)arg0Value;
                    if (arg4Value == 0 && arg5Value == TStringGuards.length(arg0Value_)) {
                        return TStringInternalNodes.SubstringNode.sameStr(arg0Value_, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    }
                }
                if (arg5Value > 0 && (arg5Value != TStringGuards.length(arg0Value) || arg0Value.isMutable()) && !arg6Value) {
                    return TStringInternalNodes.SubstringNode.materializeSubstring(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, CreateSubstringNodeGen.getUncached());
                }
                if (arg0Value instanceof TruffleString) {
                    arg0Value_ = (TruffleString)arg0Value;
                    if (arg5Value > 0 && arg5Value != TStringGuards.length(arg0Value_) && arg6Value) {
                        return this.createLazySubstring(arg0Value_, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringInternalNodes.CalcStringAttributesNode.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached());
                    }
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

        @GeneratedBy(value=TStringInternalNodes.SubstringNode.class)
        private static final class CreateLazySubstringData
        extends Node {
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
    }

    @GeneratedBy(value=TStringInternalNodes.LastIndexOfCodePointRawNode.class)
    static final class LastIndexOfCodePointRawNodeGen
    extends TStringInternalNodes.LastIndexOfCodePointRawNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TStringOpsNodes.RawLastIndexOfCodePointNode lastIndexOfNode;
        @Node.Child
        private TruffleStringIterator.PreviousNode unsupported_prevNode_;

        private LastIndexOfCodePointRawNodeGen() {
        }

        @Override
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value)) {
                    return TStringInternalNodes.LastIndexOfCodePointRawNode.utf8Fixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.lastIndexOfNode);
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

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isFixedWidth(arg2Value)) {
                    this.lastIndexOfNode = super.insert(this.lastIndexOfNode == null ? TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.create() : this.lastIndexOfNode);
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.LastIndexOfCodePointRawNode.utf8Fixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.lastIndexOfNode);
                    return n;
                }
                if (TStringGuards.isUTF8(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    this.lastIndexOfNode = super.insert(this.lastIndexOfNode == null ? TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.create() : this.lastIndexOfNode);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf8Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.lastIndexOfNode);
                    return n;
                }
                if (TStringGuards.isUTF16(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    this.lastIndexOfNode = super.insert(this.lastIndexOfNode == null ? TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.create() : this.lastIndexOfNode);
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf16Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.lastIndexOfNode);
                    return n;
                }
                if (TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    this.unsupported_prevNode_ = super.insert(TruffleStringIterator.PreviousNode.create());
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    int n = this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.unsupported_prevNode_);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

        public static TStringInternalNodes.LastIndexOfCodePointRawNode create() {
            return new LastIndexOfCodePointRawNodeGen();
        }

        public static TStringInternalNodes.LastIndexOfCodePointRawNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.LastIndexOfCodePointRawNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.LastIndexOfCodePointRawNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value) {
                if (TStringGuards.isFixedWidth(arg2Value)) {
                    return TStringInternalNodes.LastIndexOfCodePointRawNode.utf8Fixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.getUncached());
                }
                if (TStringGuards.isUTF8(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    return this.utf8Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.getUncached());
                }
                if (TStringGuards.isUTF16(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    return this.utf16Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.getUncached());
                }
                if (TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TruffleStringIterator.PreviousNode.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

    @GeneratedBy(value=TStringInternalNodes.LastIndexOfCodePointNode.class)
    static final class LastIndexOfCodePointNodeGen
    extends TStringInternalNodes.LastIndexOfCodePointNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TStringOpsNodes.RawLastIndexOfCodePointNode fixedWidth_lastIndexOfNode_;
        @Node.Child
        private TruffleStringIterator.NextNode decode_nextNode_;

        private LastIndexOfCodePointNodeGen() {
        }

        @Override
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value)) {
                    return TStringInternalNodes.LastIndexOfCodePointNode.doFixedWidth(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.fixedWidth_lastIndexOfNode_);
                }
                if ((state_0 & 2) != 0 && !TStringGuards.isFixedWidth(arg2Value)) {
                    return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.decode_nextNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isFixedWidth(arg2Value)) {
                    this.fixedWidth_lastIndexOfNode_ = super.insert(TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.LastIndexOfCodePointNode.doFixedWidth(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.fixedWidth_lastIndexOfNode_);
                    return n;
                }
                if (!TStringGuards.isFixedWidth(arg2Value)) {
                    this.decode_nextNode_ = super.insert(TruffleStringIterator.NextNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.decode_nextNode_);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

        public static TStringInternalNodes.LastIndexOfCodePointNode create() {
            return new LastIndexOfCodePointNodeGen();
        }

        public static TStringInternalNodes.LastIndexOfCodePointNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.LastIndexOfCodePointNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.LastIndexOfCodePointNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value) {
                if (TStringGuards.isFixedWidth(arg2Value)) {
                    return TStringInternalNodes.LastIndexOfCodePointNode.doFixedWidth(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringOpsNodesFactory.RawLastIndexOfCodePointNodeGen.getUncached());
                }
                if (!TStringGuards.isFixedWidth(arg2Value)) {
                    return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TruffleStringIterator.NextNode.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

    @GeneratedBy(value=TStringInternalNodes.IndexOfCodePointRawNode.class)
    static final class IndexOfCodePointRawNodeGen
    extends TStringInternalNodes.IndexOfCodePointRawNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TStringOpsNodes.RawIndexOfCodePointNode utf8Fixed_indexOfNode_;
        @Node.Child
        private TruffleStringIterator.NextNode unsupported_nextNode_;

        private IndexOfCodePointRawNodeGen() {
        }

        @Override
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value)) {
                    return TStringInternalNodes.IndexOfCodePointRawNode.utf8Fixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.utf8Fixed_indexOfNode_);
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

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isFixedWidth(arg2Value)) {
                    this.utf8Fixed_indexOfNode_ = super.insert(TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.IndexOfCodePointRawNode.utf8Fixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.utf8Fixed_indexOfNode_);
                    return n;
                }
                if (TStringGuards.isUTF8(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf8Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return n;
                }
                if (TStringGuards.isUTF16(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf16Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return n;
                }
                if (TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    this.unsupported_nextNode_ = super.insert(TruffleStringIterator.NextNode.create());
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    int n = this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.unsupported_nextNode_);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

        public static TStringInternalNodes.IndexOfCodePointRawNode create() {
            return new IndexOfCodePointRawNodeGen();
        }

        public static TStringInternalNodes.IndexOfCodePointRawNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.IndexOfCodePointRawNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.IndexOfCodePointRawNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value) {
                if (TStringGuards.isFixedWidth(arg2Value)) {
                    return TStringInternalNodes.IndexOfCodePointRawNode.utf8Fixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.getUncached());
                }
                if (TStringGuards.isUTF8(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    return this.utf8Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (TStringGuards.isUTF16(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    return this.utf16Variable(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.isFixedWidth(arg2Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TruffleStringIterator.NextNode.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

    @GeneratedBy(value=TStringInternalNodes.IndexOfCodePointNode.class)
    static final class IndexOfCodePointNodeGen
    extends TStringInternalNodes.IndexOfCodePointNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TStringOpsNodes.RawIndexOfCodePointNode fixedWidth_indexOfNode_;
        @Node.Child
        private TruffleStringIterator.NextNode decode_nextNode_;

        private IndexOfCodePointNodeGen() {
        }

        @Override
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value)) {
                    return TStringInternalNodes.IndexOfCodePointNode.doFixedWidth(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.fixedWidth_indexOfNode_);
                }
                if ((state_0 & 2) != 0 && !TStringGuards.isFixedWidth(arg2Value)) {
                    return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.decode_nextNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isFixedWidth(arg2Value)) {
                    this.fixedWidth_indexOfNode_ = super.insert(TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.IndexOfCodePointNode.doFixedWidth(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.fixedWidth_indexOfNode_);
                    return n;
                }
                if (!TStringGuards.isFixedWidth(arg2Value)) {
                    this.decode_nextNode_ = super.insert(TruffleStringIterator.NextNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.decode_nextNode_);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

        public static TStringInternalNodes.IndexOfCodePointNode create() {
            return new IndexOfCodePointNodeGen();
        }

        public static TStringInternalNodes.IndexOfCodePointNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.IndexOfCodePointNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.IndexOfCodePointNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, int arg6Value) {
                if (TStringGuards.isFixedWidth(arg2Value)) {
                    return TStringInternalNodes.IndexOfCodePointNode.doFixedWidth(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringOpsNodesFactory.RawIndexOfCodePointNodeGen.getUncached());
                }
                if (!TStringGuards.isFixedWidth(arg2Value)) {
                    return this.decode(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TruffleStringIterator.NextNode.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

    @GeneratedBy(value=TStringInternalNodes.CodePointAtRawNode.class)
    static final class CodePointAtRawNodeGen
    extends TStringInternalNodes.CodePointAtRawNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private Utf16Data utf16_cache;
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
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, TruffleString.ErrorHandling arg5Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                Utf16Data s0_;
                if ((state_0 & 1) != 0 && (s0_ = this.utf16_cache) != null && TStringGuards.isUTF16(arg3Value)) {
                    return TStringInternalNodes.CodePointAtRawNode.utf16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.fixedWidthProfile_, s0_.validProfile_, s0_.stride0Profile_);
                }
                if ((state_0 & 2) != 0 && TStringGuards.isUTF32(arg3Value)) {
                    return TStringInternalNodes.CodePointAtRawNode.utf32(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf32_stride0Profile_, this.utf32_stride1Profile_);
                }
                if ((state_0 & 4) != 0 && TStringGuards.isUTF8(arg3Value)) {
                    return TStringInternalNodes.CodePointAtRawNode.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8_fixedWidthProfile_, this.utf8_validProfile_);
                }
                if ((state_0 & 8) != 0 && !TStringGuards.isUTF16Or32(arg3Value) && !TStringGuards.isUTF8(arg3Value) && (TStringGuards.isBytes(arg3Value) || TStringGuards.is7Or8Bit(arg2Value))) {
                    return TStringInternalNodes.CodePointAtRawNode.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 0x10) != 0 && TStringGuards.isAscii(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                    return TStringInternalNodes.CodePointAtRawNode.doAsciiBroken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 0x20) != 0 && TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                    return TStringInternalNodes.CodePointAtRawNode.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, TruffleString.ErrorHandling arg5Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isUTF16(arg3Value)) {
                    Utf16Data s0_ = new Utf16Data();
                    s0_.fixedWidthProfile_ = ConditionProfile.create();
                    s0_.validProfile_ = ConditionProfile.create();
                    s0_.stride0Profile_ = ConditionProfile.create();
                    VarHandle.storeStoreFence();
                    this.utf16_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.CodePointAtRawNode.utf16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.fixedWidthProfile_, s0_.validProfile_, s0_.stride0Profile_);
                    return n;
                }
                if (TStringGuards.isUTF32(arg3Value)) {
                    this.utf32_stride0Profile_ = ConditionProfile.create();
                    this.utf32_stride1Profile_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.CodePointAtRawNode.utf32(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf32_stride0Profile_, this.utf32_stride1Profile_);
                    return n;
                }
                if (TStringGuards.isUTF8(arg3Value)) {
                    this.utf8_fixedWidthProfile_ = ConditionProfile.create();
                    this.utf8_validProfile_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.CodePointAtRawNode.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8_fixedWidthProfile_, this.utf8_validProfile_);
                    return n;
                }
                if (!TStringGuards.isUTF16Or32(arg3Value) && !TStringGuards.isUTF8(arg3Value) && (TStringGuards.isBytes(arg3Value) || TStringGuards.is7Or8Bit(arg2Value))) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.CodePointAtRawNode.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isAscii(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.CodePointAtRawNode.doAsciiBroken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.CodePointAtRawNode.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value});
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

        public static TStringInternalNodes.CodePointAtRawNode create() {
            return new CodePointAtRawNodeGen();
        }

        public static TStringInternalNodes.CodePointAtRawNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.CodePointAtRawNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.CodePointAtRawNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, TruffleString.ErrorHandling arg5Value) {
                if (TStringGuards.isUTF16(arg3Value)) {
                    return TStringInternalNodes.CodePointAtRawNode.utf16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached());
                }
                if (TStringGuards.isUTF32(arg3Value)) {
                    return TStringInternalNodes.CodePointAtRawNode.utf32(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached());
                }
                if (TStringGuards.isUTF8(arg3Value)) {
                    return TStringInternalNodes.CodePointAtRawNode.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached());
                }
                if (!TStringGuards.isUTF16Or32(arg3Value) && !TStringGuards.isUTF8(arg3Value) && (TStringGuards.isBytes(arg3Value) || TStringGuards.is7Or8Bit(arg2Value))) {
                    return TStringInternalNodes.CodePointAtRawNode.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isAscii(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                    return TStringInternalNodes.CodePointAtRawNode.doAsciiBroken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                    return TStringInternalNodes.CodePointAtRawNode.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value});
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

        @GeneratedBy(value=TStringInternalNodes.CodePointAtRawNode.class)
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

    @GeneratedBy(value=TStringInternalNodes.CodePointAtNode.class)
    static final class CodePointAtNodeGen
    extends TStringInternalNodes.CodePointAtNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private Utf16Data utf16_cache;
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
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, TruffleString.ErrorHandling arg5Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                Utf16Data s0_;
                if ((state_0 & 1) != 0 && (s0_ = this.utf16_cache) != null && TStringGuards.isUTF16(arg3Value)) {
                    return this.utf16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.fixedWidthProfile_, s0_.stride0Profile_, s0_.validProfile_);
                }
                if ((state_0 & 2) != 0 && TStringGuards.isUTF32(arg3Value)) {
                    return TStringInternalNodes.CodePointAtNode.utf32(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf32_stride0Profile_, this.utf32_stride1Profile_);
                }
                if ((state_0 & 4) != 0 && TStringGuards.isUTF8(arg3Value)) {
                    return this.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8_fixedWidthProfile_, this.utf8_validProfile_);
                }
                if ((state_0 & 8) != 0 && !TStringGuards.isUTF16Or32(arg3Value) && !TStringGuards.isUTF8(arg3Value) && (TStringGuards.isBytes(arg3Value) || TStringGuards.is7Or8Bit(arg2Value))) {
                    return TStringInternalNodes.CodePointAtNode.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 0x10) != 0 && TStringGuards.isAscii(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                    return TStringInternalNodes.CodePointAtNode.doAsciiBroken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 0x20) != 0 && TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, TruffleString.ErrorHandling arg5Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isUTF16(arg3Value)) {
                    Utf16Data s0_ = new Utf16Data();
                    s0_.fixedWidthProfile_ = ConditionProfile.create();
                    s0_.stride0Profile_ = ConditionProfile.create();
                    s0_.validProfile_ = ConditionProfile.create();
                    VarHandle.storeStoreFence();
                    this.utf16_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.fixedWidthProfile_, s0_.stride0Profile_, s0_.validProfile_);
                    return n;
                }
                if (TStringGuards.isUTF32(arg3Value)) {
                    this.utf32_stride0Profile_ = ConditionProfile.create();
                    this.utf32_stride1Profile_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.CodePointAtNode.utf32(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf32_stride0Profile_, this.utf32_stride1Profile_);
                    return n;
                }
                if (TStringGuards.isUTF8(arg3Value)) {
                    this.utf8_fixedWidthProfile_ = ConditionProfile.create();
                    this.utf8_validProfile_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8_fixedWidthProfile_, this.utf8_validProfile_);
                    return n;
                }
                if (!TStringGuards.isUTF16Or32(arg3Value) && !TStringGuards.isUTF8(arg3Value) && (TStringGuards.isBytes(arg3Value) || TStringGuards.is7Or8Bit(arg2Value))) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.CodePointAtNode.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isAscii(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.CodePointAtNode.doAsciiBroken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    int n = this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value});
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

        public static TStringInternalNodes.CodePointAtNode create() {
            return new CodePointAtNodeGen();
        }

        public static TStringInternalNodes.CodePointAtNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.CodePointAtNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.CodePointAtNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, TruffleString.ErrorHandling arg5Value) {
                if (TStringGuards.isUTF16(arg3Value)) {
                    return this.utf16(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached());
                }
                if (TStringGuards.isUTF32(arg3Value)) {
                    return TStringInternalNodes.CodePointAtNode.utf32(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached());
                }
                if (TStringGuards.isUTF8(arg3Value)) {
                    return this.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached());
                }
                if (!TStringGuards.isUTF16Or32(arg3Value) && !TStringGuards.isUTF8(arg3Value) && (TStringGuards.isBytes(arg3Value) || TStringGuards.is7Or8Bit(arg2Value))) {
                    return TStringInternalNodes.CodePointAtNode.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isAscii(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                    return TStringInternalNodes.CodePointAtNode.doAsciiBroken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isUnsupportedEncoding(arg3Value) && !TStringGuards.is7Or8Bit(arg2Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value});
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

        @GeneratedBy(value=TStringInternalNodes.CodePointAtNode.class)
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

    @GeneratedBy(value=TStringInternalNodes.ReadByteNode.class)
    static final class ReadByteNodeGen
    extends TStringInternalNodes.ReadByteNode {
        private static final Uncached UNCACHED = new Uncached();
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
                    return TStringInternalNodes.ReadByteNode.doUTF32(arg0Value, arg1Value, arg2Value, arg3Value, this.uTF32_stride0Profile_, this.uTF32_stride1Profile_);
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
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isUTF16(arg3Value)) {
                    this.uTF16_stride0Profile_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.ReadByteNode.doUTF16(arg0Value, arg1Value, arg2Value, arg3Value, this.uTF16_stride0Profile_);
                    return n;
                }
                if (TStringGuards.isUTF32(arg3Value)) {
                    this.uTF32_stride0Profile_ = ConditionProfile.create();
                    this.uTF32_stride1Profile_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.ReadByteNode.doUTF32(arg0Value, arg1Value, arg2Value, arg3Value, this.uTF32_stride0Profile_, this.uTF32_stride1Profile_);
                    return n;
                }
                if (!TStringGuards.isUTF16Or32(arg3Value)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    int n = TStringInternalNodes.ReadByteNode.doRest(arg0Value, arg1Value, arg2Value, arg3Value);
                    return n;
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

        public static TStringInternalNodes.ReadByteNode create() {
            return new ReadByteNodeGen();
        }

        public static TStringInternalNodes.ReadByteNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.ReadByteNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.ReadByteNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value) {
                if (TStringGuards.isUTF16(arg3Value)) {
                    return TStringInternalNodes.ReadByteNode.doUTF16(arg0Value, arg1Value, arg2Value, arg3Value, ConditionProfile.getUncached());
                }
                if (TStringGuards.isUTF32(arg3Value)) {
                    return TStringInternalNodes.ReadByteNode.doUTF32(arg0Value, arg1Value, arg2Value, arg3Value, ConditionProfile.getUncached(), ConditionProfile.getUncached());
                }
                if (!TStringGuards.isUTF16Or32(arg3Value)) {
                    return TStringInternalNodes.ReadByteNode.doRest(arg0Value, arg1Value, arg2Value, arg3Value);
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
    }

    @GeneratedBy(value=TStringInternalNodes.CodePointIndexToRawNode.class)
    static final class CodePointIndexToRawNodeGen
    extends TStringInternalNodes.CodePointIndexToRawNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private CodePointIndexToRawNodeGen() {
        }

        @Override
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, boolean arg6Value) {
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
                if ((state_0 & 0x10) != 0 && TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if ((state_0 & 0x20) != 0 && TStringGuards.isUnsupportedEncoding(arg3Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, boolean arg6Value) {
            int state_0 = this.state_0_;
            if (TStringGuards.isFixedWidth(arg2Value)) {
                this.state_0_ = state_0 |= 1;
                return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }
            if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                this.state_0_ = state_0 |= 2;
                return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }
            if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                this.state_0_ = state_0 |= 4;
                return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }
            if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                this.state_0_ = state_0 |= 8;
                return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }
            if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                this.state_0_ = state_0 |= 0x10;
                return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }
            if (TStringGuards.isUnsupportedEncoding(arg3Value)) {
                this.state_0_ = state_0 |= 0x20;
                return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

        public static TStringInternalNodes.CodePointIndexToRawNode create() {
            return new CodePointIndexToRawNodeGen();
        }

        public static TStringInternalNodes.CodePointIndexToRawNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.CodePointIndexToRawNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.CodePointIndexToRawNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value, boolean arg6Value) {
                if (TStringGuards.isFixedWidth(arg2Value)) {
                    return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                    return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                    return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                if (TStringGuards.isUnsupportedEncoding(arg3Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value});
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

    @GeneratedBy(value=TStringInternalNodes.RawIndexToCodePointIndexNode.class)
    static final class RawIndexToCodePointIndexNodeGen
    extends TStringInternalNodes.RawIndexToCodePointIndexNode {
        private static final Uncached UNCACHED = new Uncached();
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
                if ((state_0 & 0x10) != 0 && TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 0x20) != 0 && TStringGuards.isUnsupportedEncoding(arg3Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.unsupported_validProfile_, this.unsupported_fixedWidthProfile_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isFixedWidth(arg2Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                    this.utf8Valid_brokenProfile_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8Valid_brokenProfile_);
                    return n;
                }
                if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    this.utf8Broken_brokenProfile_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.utf8Broken_brokenProfile_);
                    return n;
                }
                if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isUnsupportedEncoding(arg3Value)) {
                    this.unsupported_validProfile_ = ConditionProfile.create();
                    this.unsupported_fixedWidthProfile_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    int n = this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.unsupported_validProfile_, this.unsupported_fixedWidthProfile_);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value});
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

        public static TStringInternalNodes.RawIndexToCodePointIndexNode create() {
            return new RawIndexToCodePointIndexNodeGen();
        }

        public static TStringInternalNodes.RawIndexToCodePointIndexNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.RawIndexToCodePointIndexNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.RawIndexToCodePointIndexNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value) {
                if (TStringGuards.isFixedWidth(arg2Value)) {
                    return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                    return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached());
                }
                if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached());
                }
                if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                    return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isUnsupportedEncoding(arg3Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value});
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

    @GeneratedBy(value=TStringInternalNodes.ByteLengthOfCodePointNode.class)
    static final class ByteLengthOfCodePointNodeGen
    extends TStringInternalNodes.ByteLengthOfCodePointNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TStringInternalNodes.CodePointAtRawNode uTF32BrokenReturnNegative_codePointAtRawNode_;

        private ByteLengthOfCodePointNodeGen() {
        }

        @Override
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, TruffleString.ErrorHandling arg5Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.isFixedWidth(arg2Value) && TStringGuards.isBestEffort(arg5Value)) {
                    return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 2) != 0 && TStringGuards.isUpToValidFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
                    return this.doFixedValidReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 4) != 0 && TStringGuards.isAscii(arg3Value) && TStringGuards.isBrokenFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
                    return this.doASCIIBrokenReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 8) != 0 && TStringGuards.isUTF32(arg3Value) && TStringGuards.isBrokenFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
                    return this.doUTF32BrokenReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.uTF32BrokenReturnNegative_codePointAtRawNode_);
                }
                if ((state_0 & 0x10) != 0 && TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                    return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 0x20) != 0 && TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 0x40) != 0 && TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                    return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 0x80) != 0 && TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if ((state_0 & 0x100) != 0 && TStringGuards.isUnsupportedEncoding(arg3Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, TruffleString.ErrorHandling arg5Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isFixedWidth(arg2Value) && TStringGuards.isBestEffort(arg5Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isUpToValidFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doFixedValidReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isAscii(arg3Value) && TStringGuards.isBrokenFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doASCIIBrokenReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isUTF32(arg3Value) && TStringGuards.isBrokenFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
                    this.uTF32BrokenReturnNegative_codePointAtRawNode_ = super.insert(CodePointAtRawNodeGen.create());
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doUTF32BrokenReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.uTF32BrokenReturnNegative_codePointAtRawNode_);
                    return n;
                }
                if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    int n = this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                if (TStringGuards.isUnsupportedEncoding(arg3Value)) {
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    int n = this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value});
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

        public static TStringInternalNodes.ByteLengthOfCodePointNode create() {
            return new ByteLengthOfCodePointNodeGen();
        }

        public static TStringInternalNodes.ByteLengthOfCodePointNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.ByteLengthOfCodePointNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.ByteLengthOfCodePointNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, TruffleString.ErrorHandling arg5Value) {
                if (TStringGuards.isFixedWidth(arg2Value) && TStringGuards.isBestEffort(arg5Value)) {
                    return this.doFixed(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isUpToValidFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
                    return this.doFixedValidReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isAscii(arg3Value) && TStringGuards.isBrokenFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
                    return this.doASCIIBrokenReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isUTF32(arg3Value) && TStringGuards.isBrokenFixedWidth(arg2Value) && TStringGuards.isReturnNegative(arg5Value)) {
                    return this.doUTF32BrokenReturnNegative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, CodePointAtRawNodeGen.getUncached());
                }
                if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                    return this.utf8Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isUTF8(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    return this.utf8Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isValidMultiByte(arg2Value)) {
                    return this.utf16Valid(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isUTF16(arg3Value) && TStringGuards.isBrokenMultiByte(arg2Value)) {
                    return this.utf16Broken(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                if (TStringGuards.isUnsupportedEncoding(arg3Value)) {
                    return this.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null}, new Object[]{arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value});
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

    @GeneratedBy(value=TStringInternalNodes.FromNativePointerNode.class)
    static final class FromNativePointerNodeGen
    extends TStringInternalNodes.FromNativePointerNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private FromNativePointerInternalData fromNativePointerInternal_cache;

        private FromNativePointerNodeGen() {
        }

        @Override
        TruffleString execute(AbstractTruffleString.NativePointer arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            FromNativePointerInternalData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.fromNativePointerInternal_cache) != null) {
                return this.fromNativePointerInternal(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.asciiLatinBytesProfile_, s0_.utf8Profile_, s0_.utf8BrokenProfile_, s0_.utf16Profile_, s0_.utf32Profile_, s0_.exoticValidProfile_, s0_.exoticFixedWidthProfile_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(AbstractTruffleString.NativePointer arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                FromNativePointerInternalData s0_ = new FromNativePointerInternalData();
                s0_.asciiLatinBytesProfile_ = ConditionProfile.create();
                s0_.utf8Profile_ = ConditionProfile.create();
                s0_.utf8BrokenProfile_ = ConditionProfile.create();
                s0_.utf16Profile_ = ConditionProfile.create();
                s0_.utf32Profile_ = ConditionProfile.create();
                s0_.exoticValidProfile_ = ConditionProfile.create();
                s0_.exoticFixedWidthProfile_ = ConditionProfile.create();
                VarHandle.storeStoreFence();
                this.fromNativePointerInternal_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.fromNativePointerInternal(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.asciiLatinBytesProfile_, s0_.utf8Profile_, s0_.utf8BrokenProfile_, s0_.utf16Profile_, s0_.utf32Profile_, s0_.exoticValidProfile_, s0_.exoticFixedWidthProfile_);
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

        public static TStringInternalNodes.FromNativePointerNode create() {
            return new FromNativePointerNodeGen();
        }

        public static TStringInternalNodes.FromNativePointerNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.FromNativePointerNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.FromNativePointerNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            TruffleString execute(AbstractTruffleString.NativePointer arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value) {
                return this.fromNativePointerInternal(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached());
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

        @GeneratedBy(value=TStringInternalNodes.FromNativePointerNode.class)
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
    }

    @GeneratedBy(value=TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.class)
    static final class FromBufferWithStringCompactionKnownAttributesNodeGen
    extends TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private FromBufferWithStringCompactionData fromBufferWithStringCompaction_cache;

        private FromBufferWithStringCompactionKnownAttributesNodeGen() {
        }

        @Override
        TruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value) {
            FromBufferWithStringCompactionData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.fromBufferWithStringCompaction_cache) != null) {
                return this.fromBufferWithStringCompaction(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.utf16Profile_, s0_.utf16CompactProfile_, s0_.utf32Profile_, s0_.utf32Compact0Profile_, s0_.utf32Compact1Profile_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                FromBufferWithStringCompactionData s0_ = new FromBufferWithStringCompactionData();
                s0_.utf16Profile_ = ConditionProfile.create();
                s0_.utf16CompactProfile_ = ConditionProfile.create();
                s0_.utf32Profile_ = ConditionProfile.create();
                s0_.utf32Compact0Profile_ = ConditionProfile.create();
                s0_.utf32Compact1Profile_ = ConditionProfile.create();
                VarHandle.storeStoreFence();
                this.fromBufferWithStringCompaction_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.fromBufferWithStringCompaction(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.utf16Profile_, s0_.utf16CompactProfile_, s0_.utf32Profile_, s0_.utf32Compact0Profile_, s0_.utf32Compact1Profile_);
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

        public static TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode create() {
            return new FromBufferWithStringCompactionKnownAttributesNodeGen();
        }

        public static TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            TruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, int arg4Value, int arg5Value) {
                return this.fromBufferWithStringCompaction(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached());
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

        @GeneratedBy(value=TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.class)
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
    }

    @GeneratedBy(value=TStringInternalNodes.FromBufferWithStringCompactionNode.class)
    static final class FromBufferWithStringCompactionNodeGen
    extends TStringInternalNodes.FromBufferWithStringCompactionNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private FromBufferWithStringCompactionData fromBufferWithStringCompaction_cache;

        private FromBufferWithStringCompactionNodeGen() {
        }

        @Override
        TruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value, boolean arg5Value) {
            FromBufferWithStringCompactionData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.fromBufferWithStringCompaction_cache) != null) {
                return this.fromBufferWithStringCompaction(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.asciiLatinBytesProfile_, s0_.utf8Profile_, s0_.utf8BrokenProfile_, s0_.utf16Profile_, s0_.utf16CompactProfile_, s0_.utf32Profile_, s0_.utf32Compact0Profile_, s0_.utf32Compact1Profile_, s0_.exoticValidProfile_, s0_.exoticFixedWidthProfile_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value, boolean arg5Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                FromBufferWithStringCompactionData s0_ = new FromBufferWithStringCompactionData();
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
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.fromBufferWithStringCompaction(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.asciiLatinBytesProfile_, s0_.utf8Profile_, s0_.utf8BrokenProfile_, s0_.utf16Profile_, s0_.utf16CompactProfile_, s0_.utf32Profile_, s0_.utf32Compact0Profile_, s0_.utf32Compact1Profile_, s0_.exoticValidProfile_, s0_.exoticFixedWidthProfile_);
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

        public static TStringInternalNodes.FromBufferWithStringCompactionNode create() {
            return new FromBufferWithStringCompactionNodeGen();
        }

        public static TStringInternalNodes.FromBufferWithStringCompactionNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.FromBufferWithStringCompactionNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.FromBufferWithStringCompactionNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            TruffleString execute(Object arg0Value, int arg1Value, int arg2Value, TruffleString.Encoding arg3Value, boolean arg4Value, boolean arg5Value) {
                return this.fromBufferWithStringCompaction(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached());
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

        @GeneratedBy(value=TStringInternalNodes.FromBufferWithStringCompactionNode.class)
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
    }

    @GeneratedBy(value=TStringInternalNodes.CreateSubstringNode.class)
    static final class CreateSubstringNodeGen
    extends TStringInternalNodes.CreateSubstringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private CachedData cached_cache;
        @Node.Child
        private TStringInternalNodes.CalcStringAttributesNode uncached_calcAttributesNode_;

        private CreateSubstringNodeGen() {
        }

        @Override
        @ExplodeLoop
        TruffleString execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (arg5Value == s0_.cachedEncoding_ && arg4Value == s0_.cachedStride_) {
                            return this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.cachedEncoding_, s0_.cachedStride_, s0_.calcAttributesNode_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.uncached_calcAttributesNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    int count0_ = 0;
                    CachedData s0_ = this.cached_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && (arg5Value != s0_.cachedEncoding_ || arg4Value != s0_.cachedStride_)) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && count0_ < 6) {
                        s0_ = super.insert(new CachedData(this.cached_cache));
                        s0_.cachedEncoding_ = arg5Value;
                        s0_.cachedStride_ = arg4Value;
                        s0_.calcAttributesNode_ = s0_.insertAccessor(CalcStringAttributesNodeGen.create());
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString = this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.cachedEncoding_, s0_.cachedStride_, s0_.calcAttributesNode_);
                        return truffleString;
                    }
                }
                this.uncached_calcAttributesNode_ = super.insert(CalcStringAttributesNodeGen.create());
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.uncached_calcAttributesNode_);
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
            CachedData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TStringInternalNodes.CreateSubstringNode create() {
            return new CreateSubstringNodeGen();
        }

        public static TStringInternalNodes.CreateSubstringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.CreateSubstringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.CreateSubstringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            TruffleString execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, TruffleString.Encoding arg5Value, int arg6Value) {
                return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringInternalNodes.CalcStringAttributesNode.getUncached());
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

        @GeneratedBy(value=TStringInternalNodes.CreateSubstringNode.class)
        private static final class CachedData
        extends Node {
            @Node.Child
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            TruffleString.Encoding cachedEncoding_;
            @CompilerDirectives.CompilationFinal
            int cachedStride_;
            @Node.Child
            TStringInternalNodes.CalcStringAttributesNode calcAttributesNode_;

            CachedData(CachedData next_) {
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
    }

    @GeneratedBy(value=TStringInternalNodes.GetCodePointLengthNode.class)
    static final class GetCodePointLengthNodeGen
    extends TStringInternalNodes.GetCodePointLengthNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
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

        private int executeAndSpecialize(AbstractTruffleString arg0Value) {
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
                    int n = this.immutable(arg0Value_);
                    return n;
                }
                if (arg0Value instanceof MutableTruffleString) {
                    MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
                    if (arg0Value_.codePointLength() >= 0) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        int n = this.mutableCacheHit(arg0Value_);
                        return n;
                    }
                    if (arg0Value_.codePointLength() < 0) {
                        this.mutableCacheMiss_calcLazyAttributesNode_ = super.insert(MutableTruffleStringFactory.CalcLazyAttributesNodeGen.create());
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        int n = this.mutableCacheMiss(arg0Value_, this.mutableCacheMiss_calcLazyAttributesNode_);
                        return n;
                    }
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

        public static TStringInternalNodes.GetCodePointLengthNode create() {
            return new GetCodePointLengthNodeGen();
        }

        public static TStringInternalNodes.GetCodePointLengthNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.GetCodePointLengthNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.GetCodePointLengthNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value) {
                if (arg0Value instanceof TruffleString) {
                    TruffleString arg0Value_ = (TruffleString)arg0Value;
                    return this.immutable(arg0Value_);
                }
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

    @GeneratedBy(value=TStringInternalNodes.GetCodeRangeNode.class)
    static final class GetCodeRangeNodeGen
    extends TStringInternalNodes.GetCodeRangeNode {
        private static final Uncached UNCACHED = new Uncached();
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
            }
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

        private int executeAndSpecialize(AbstractTruffleString arg0Value) {
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
                    int n = this.immutable(arg0Value_);
                    return n;
                }
                if (arg0Value instanceof MutableTruffleString) {
                    MutableTruffleString arg0Value_ = (MutableTruffleString)arg0Value;
                    if (!TStringGuards.isUnknown(arg0Value_.codeRange())) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        int n = this.mutableCacheHit(arg0Value_);
                        return n;
                    }
                    if (TStringGuards.isUnknown(arg0Value_.codeRange())) {
                        this.mutableCacheMiss_calcLazyAttributesNode_ = super.insert(MutableTruffleStringFactory.CalcLazyAttributesNodeGen.create());
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        int n = this.mutableCacheMiss(arg0Value_, this.mutableCacheMiss_calcLazyAttributesNode_);
                        return n;
                    }
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

        public static TStringInternalNodes.GetCodeRangeNode create() {
            return new GetCodeRangeNodeGen();
        }

        public static TStringInternalNodes.GetCodeRangeNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringInternalNodes.GetCodeRangeNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringInternalNodes.GetCodeRangeNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value) {
                if (arg0Value instanceof TruffleString) {
                    TruffleString arg0Value_ = (TruffleString)arg0Value;
                    return this.immutable(arg0Value_);
                }
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

