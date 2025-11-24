
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TStringOpsNodes;
import com.oracle.truffle.api.strings.TStringOpsNodesFactory;
import com.oracle.truffle.api.strings.TruffleStringIterator;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TruffleStringIterator.class)
public final class TruffleStringIteratorFactory {

    @GeneratedBy(value=TruffleStringIterator.PreviousNode.class)
    static final class PreviousNodeGen
    extends TruffleStringIterator.PreviousNode {
        private static final Uncached UNCACHED = new Uncached();
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
                if ((state_0 & 4) != 0 && TStringGuards.isAscii(arg0Value.encoding) && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    return TruffleStringIterator.PreviousNode.brokenAscii(arg0Value, this.brokenAscii_readNode_);
                }
                if ((state_0 & 8) != 0 && TStringGuards.isUTF32(arg0Value.encoding) && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    return TruffleStringIterator.PreviousNode.brokenUTF32(arg0Value, this.brokenUTF32_readNode_);
                }
                if ((state_0 & 0x10) != 0 && TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.PreviousNode.utf8Valid(arg0Value);
                }
                if ((state_0 & 0x20) != 0 && TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.PreviousNode.utf8Broken(arg0Value);
                }
                if ((state_0 & 0x40) != 0 && TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.PreviousNode.utf16Valid(arg0Value);
                }
                if ((state_0 & 0x80) != 0 && TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.PreviousNode.utf16Broken(arg0Value);
                }
                if ((state_0 & 0x100) != 0 && TStringGuards.isUnsupportedEncoding(arg0Value.encoding)) {
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
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isFixedWidth(arg0Value.codeRangeA) && TStringGuards.isBestEffort(arg0Value.errorHandling)) {
                    this.fixed_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.PreviousNode.fixed(arg0Value, this.fixed_readNode_);
                    return n;
                }
                if (TStringGuards.isUpToValidFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    this.fixedValid_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.PreviousNode.fixedValid(arg0Value, this.fixedValid_readNode_);
                    return n;
                }
                if (TStringGuards.isAscii(arg0Value.encoding) && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    this.brokenAscii_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.PreviousNode.brokenAscii(arg0Value, this.brokenAscii_readNode_);
                    return n;
                }
                if (TStringGuards.isUTF32(arg0Value.encoding) && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    this.brokenUTF32_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.PreviousNode.brokenUTF32(arg0Value, this.brokenUTF32_readNode_);
                    return n;
                }
                if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.PreviousNode.utf8Valid(arg0Value);
                    return n;
                }
                if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.PreviousNode.utf8Broken(arg0Value);
                    return n;
                }
                if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.PreviousNode.utf16Valid(arg0Value);
                    return n;
                }
                if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.PreviousNode.utf16Broken(arg0Value);
                    return n;
                }
                if (TStringGuards.isUnsupportedEncoding(arg0Value.encoding)) {
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.PreviousNode.unsupported(arg0Value);
                    return n;
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

        public static TruffleStringIterator.PreviousNode create() {
            return new PreviousNodeGen();
        }

        public static TruffleStringIterator.PreviousNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleStringIterator.PreviousNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleStringIterator.PreviousNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int executeInternal(TruffleStringIterator arg0Value) {
                if (TStringGuards.isFixedWidth(arg0Value.codeRangeA) && TStringGuards.isBestEffort(arg0Value.errorHandling)) {
                    return TruffleStringIterator.PreviousNode.fixed(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
                }
                if (TStringGuards.isUpToValidFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    return TruffleStringIterator.PreviousNode.fixedValid(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
                }
                if (TStringGuards.isAscii(arg0Value.encoding) && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    return TruffleStringIterator.PreviousNode.brokenAscii(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
                }
                if (TStringGuards.isUTF32(arg0Value.encoding) && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    return TruffleStringIterator.PreviousNode.brokenUTF32(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
                }
                if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.PreviousNode.utf8Valid(arg0Value);
                }
                if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.PreviousNode.utf8Broken(arg0Value);
                }
                if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.PreviousNode.utf16Valid(arg0Value);
                }
                if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.PreviousNode.utf16Broken(arg0Value);
                }
                if (TStringGuards.isUnsupportedEncoding(arg0Value.encoding)) {
                    return TruffleStringIterator.PreviousNode.unsupported(arg0Value);
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

    @GeneratedBy(value=TruffleStringIterator.NextNode.class)
    static final class NextNodeGen
    extends TruffleStringIterator.NextNode {
        private static final Uncached UNCACHED = new Uncached();
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
                if ((state_0 & 4) != 0 && TStringGuards.isAscii(arg0Value.encoding) && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    return TruffleStringIterator.NextNode.brokenAscii(arg0Value, this.brokenAscii_readNode_);
                }
                if ((state_0 & 8) != 0 && TStringGuards.isUTF32(arg0Value.encoding) && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    return TruffleStringIterator.NextNode.brokenUTF32(arg0Value, this.brokenUTF32_readNode_);
                }
                if ((state_0 & 0x10) != 0 && TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.NextNode.utf8Valid(arg0Value);
                }
                if ((state_0 & 0x20) != 0 && TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.NextNode.utf8Broken(arg0Value);
                }
                if ((state_0 & 0x40) != 0 && TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.NextNode.utf16Valid(arg0Value);
                }
                if ((state_0 & 0x80) != 0 && TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.NextNode.utf16Broken(arg0Value);
                }
                if ((state_0 & 0x100) != 0 && TStringGuards.isUnsupportedEncoding(arg0Value.encoding)) {
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
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.isFixedWidth(arg0Value.codeRangeA) && TStringGuards.isBestEffort(arg0Value.errorHandling)) {
                    this.fixed_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.NextNode.fixed(arg0Value, this.fixed_readNode_);
                    return n;
                }
                if (TStringGuards.isUpToValidFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    this.fixedValid_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.NextNode.fixedValid(arg0Value, this.fixedValid_readNode_);
                    return n;
                }
                if (TStringGuards.isAscii(arg0Value.encoding) && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    this.brokenAscii_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.NextNode.brokenAscii(arg0Value, this.brokenAscii_readNode_);
                    return n;
                }
                if (TStringGuards.isUTF32(arg0Value.encoding) && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    this.brokenUTF32_readNode_ = super.insert(TStringOpsNodesFactory.RawReadValueNodeGen.create());
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.NextNode.brokenUTF32(arg0Value, this.brokenUTF32_readNode_);
                    return n;
                }
                if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.NextNode.utf8Valid(arg0Value);
                    return n;
                }
                if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.NextNode.utf8Broken(arg0Value);
                    return n;
                }
                if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.NextNode.utf16Valid(arg0Value);
                    return n;
                }
                if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.NextNode.utf16Broken(arg0Value);
                    return n;
                }
                if (TStringGuards.isUnsupportedEncoding(arg0Value.encoding)) {
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    int n = TruffleStringIterator.NextNode.unsupported(arg0Value);
                    return n;
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

        public static TruffleStringIterator.NextNode create() {
            return new NextNodeGen();
        }

        public static TruffleStringIterator.NextNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleStringIterator.NextNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleStringIterator.NextNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int executeInternal(TruffleStringIterator arg0Value) {
                if (TStringGuards.isFixedWidth(arg0Value.codeRangeA) && TStringGuards.isBestEffort(arg0Value.errorHandling)) {
                    return TruffleStringIterator.NextNode.fixed(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
                }
                if (TStringGuards.isUpToValidFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    return TruffleStringIterator.NextNode.fixedValid(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
                }
                if (TStringGuards.isAscii(arg0Value.encoding) && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    return TruffleStringIterator.NextNode.brokenAscii(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
                }
                if (TStringGuards.isUTF32(arg0Value.encoding) && TStringGuards.isBrokenFixedWidth(arg0Value.codeRangeA) && TStringGuards.isReturnNegative(arg0Value.errorHandling)) {
                    return TruffleStringIterator.NextNode.brokenUTF32(arg0Value, TStringOpsNodesFactory.RawReadValueNodeGen.getUncached());
                }
                if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.NextNode.utf8Valid(arg0Value);
                }
                if (TStringGuards.isUTF8(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.NextNode.utf8Broken(arg0Value);
                }
                if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isValidMultiByte(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.NextNode.utf16Valid(arg0Value);
                }
                if (TStringGuards.isUTF16(arg0Value.encoding) && TStringGuards.isBrokenMultiByteOrUnknown(arg0Value.codeRangeA)) {
                    return TruffleStringIterator.NextNode.utf16Broken(arg0Value);
                }
                if (TStringGuards.isUnsupportedEncoding(arg0Value.encoding)) {
                    return TruffleStringIterator.NextNode.unsupported(arg0Value);
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

