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
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TStringInternalNodes;
import com.oracle.truffle.api.strings.TStringInternalNodesFactory;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TruffleStringBuilder.class)
public final class TruffleStringBuilderFactory {

    @GeneratedBy(value=TruffleStringBuilder.AppendArrayIntlNode.class)
    static final class AppendArrayIntlNodeGen
    extends TruffleStringBuilder.AppendArrayIntlNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile uncached_bufferGrowProfile_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile uncached_errorProfile_;

        private AppendArrayIntlNodeGen() {
        }

        @Override
        @ExplodeLoop
        void execute(TruffleStringBuilder arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, int arg5Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (arg0Value.stride == s0_.cachedStrideSB_ && arg4Value == s0_.cachedStrideA_ && arg5Value == s0_.cachedStrideNew_) {
                            this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.cachedStrideSB_, s0_.cachedStrideA_, s0_.cachedStrideNew_, s0_.bufferGrowProfile_, s0_.errorProfile_);
                            return;
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.uncached_bufferGrowProfile_, this.uncached_errorProfile_);
                    return;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(TruffleStringBuilder arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, int arg5Value) {
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
                        while (s0_ != null && (arg0Value.stride != s0_.cachedStrideSB_ || arg4Value != s0_.cachedStrideA_ || arg5Value != s0_.cachedStrideNew_)) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && count0_ < 9) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedStrideSB_ = arg0Value.stride;
                        s0_.cachedStrideA_ = arg4Value;
                        s0_.cachedStrideNew_ = arg5Value;
                        s0_.bufferGrowProfile_ = ConditionProfile.create();
                        s0_.errorProfile_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.cachedStrideSB_, s0_.cachedStrideA_, s0_.cachedStrideNew_, s0_.bufferGrowProfile_, s0_.errorProfile_);
                        return;
                    }
                }
                this.uncached_bufferGrowProfile_ = ConditionProfile.create();
                this.uncached_errorProfile_ = BranchProfile.create();
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, this.uncached_bufferGrowProfile_, this.uncached_errorProfile_);
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

        public static TruffleStringBuilder.AppendArrayIntlNode create() {
            return new AppendArrayIntlNodeGen();
        }

        public static TruffleStringBuilder.AppendArrayIntlNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleStringBuilder.AppendArrayIntlNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleStringBuilder.AppendArrayIntlNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            void execute(TruffleStringBuilder arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value, int arg5Value) {
                this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, ConditionProfile.getUncached(), BranchProfile.getUncached());
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

        @GeneratedBy(value=TruffleStringBuilder.AppendArrayIntlNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideSB_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideA_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideNew_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile bufferGrowProfile_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorProfile_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=TruffleStringBuilder.ToStringNode.class)
    static final class ToStringNodeGen
    extends TruffleStringBuilder.ToStringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile calcAttributesProfile_;
        @Node.Child
        private TStringInternalNodes.CalcStringAttributesNode calcAttributesNode_;

        private ToStringNodeGen() {
        }

        @Override
        public TruffleString execute(TruffleStringBuilder arg0Value, boolean arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                return TruffleStringBuilder.ToStringNode.createString(arg0Value, arg1Value, this.calcAttributesProfile_, this.calcAttributesNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(TruffleStringBuilder arg0Value, boolean arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.calcAttributesProfile_ = ConditionProfile.create();
                this.calcAttributesNode_ = super.insert(TStringInternalNodesFactory.CalcStringAttributesNodeGen.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = TruffleStringBuilder.ToStringNode.createString(arg0Value, arg1Value, this.calcAttributesProfile_, this.calcAttributesNode_);
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

        public static TruffleStringBuilder.ToStringNode create() {
            return new ToStringNodeGen();
        }

        public static TruffleStringBuilder.ToStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleStringBuilder.ToStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleStringBuilder.ToStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(TruffleStringBuilder arg0Value, boolean arg1Value) {
                return TruffleStringBuilder.ToStringNode.createString(arg0Value, arg1Value, ConditionProfile.getUncached(), TStringInternalNodes.CalcStringAttributesNode.getUncached());
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

    @GeneratedBy(value=TruffleStringBuilder.AppendJavaStringUTF16Node.class)
    static final class AppendJavaStringUTF16NodeGen
    extends TruffleStringBuilder.AppendJavaStringUTF16Node {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleStringBuilder.AppendArrayIntlNode appendArrayIntlNode_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile stride0Profile_;

        private AppendJavaStringUTF16NodeGen() {
        }

        @Override
        public void execute(TruffleStringBuilder arg0Value, String arg1Value, int arg2Value, int arg3Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                this.append(arg0Value, arg1Value, arg2Value, arg3Value, this.appendArrayIntlNode_, this.stride0Profile_);
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(TruffleStringBuilder arg0Value, String arg1Value, int arg2Value, int arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.appendArrayIntlNode_ = super.insert(AppendArrayIntlNodeGen.create());
                this.stride0Profile_ = ConditionProfile.create();
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                this.append(arg0Value, arg1Value, arg2Value, arg3Value, this.appendArrayIntlNode_, this.stride0Profile_);
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

        public static TruffleStringBuilder.AppendJavaStringUTF16Node create() {
            return new AppendJavaStringUTF16NodeGen();
        }

        public static TruffleStringBuilder.AppendJavaStringUTF16Node getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleStringBuilder.AppendJavaStringUTF16Node.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleStringBuilder.AppendJavaStringUTF16Node {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void execute(TruffleStringBuilder arg0Value, String arg1Value, int arg2Value, int arg3Value) {
                this.append(arg0Value, arg1Value, arg2Value, arg3Value, AppendArrayIntlNodeGen.getUncached(), ConditionProfile.getUncached());
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

    @GeneratedBy(value=TruffleStringBuilder.AppendSubstringByteIndexNode.class)
    static final class AppendSubstringByteIndexNodeGen
    extends TruffleStringBuilder.AppendSubstringByteIndexNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private AppendData append_cache;

        private AppendSubstringByteIndexNodeGen() {
        }

        @Override
        public void execute(TruffleStringBuilder arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value) {
            AppendData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.append_cache) != null) {
                TruffleStringBuilder.AppendSubstringByteIndexNode.append(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.appendArrayIntlNode_, s0_.calcAttributesNode_, s0_.calcAttrsProfile_);
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(TruffleStringBuilder arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                AppendData s0_ = super.insert(new AppendData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.appendArrayIntlNode_ = s0_.insertAccessor(AppendArrayIntlNodeGen.create());
                s0_.calcAttributesNode_ = s0_.insertAccessor(TStringInternalNodesFactory.CalcStringAttributesNodeGen.create());
                s0_.calcAttrsProfile_ = ConditionProfile.create();
                VarHandle.storeStoreFence();
                this.append_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleStringBuilder.AppendSubstringByteIndexNode.append(arg0Value, arg1Value, arg2Value, arg3Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.appendArrayIntlNode_, s0_.calcAttributesNode_, s0_.calcAttrsProfile_);
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

        public static TruffleStringBuilder.AppendSubstringByteIndexNode create() {
            return new AppendSubstringByteIndexNodeGen();
        }

        public static TruffleStringBuilder.AppendSubstringByteIndexNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleStringBuilder.AppendSubstringByteIndexNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleStringBuilder.AppendSubstringByteIndexNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void execute(TruffleStringBuilder arg0Value, AbstractTruffleString arg1Value, int arg2Value, int arg3Value) {
                TruffleStringBuilder.AppendSubstringByteIndexNode.append(arg0Value, arg1Value, arg2Value, arg3Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), AppendArrayIntlNodeGen.getUncached(), TStringInternalNodes.CalcStringAttributesNode.getUncached(), ConditionProfile.getUncached());
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

        @GeneratedBy(value=TruffleStringBuilder.AppendSubstringByteIndexNode.class)
        private static final class AppendData
        extends Node {
            @Node.Child
            TruffleString.ToIndexableNode toIndexableNode_;
            @Node.Child
            TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
            @Node.Child
            TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
            @Node.Child
            TruffleStringBuilder.AppendArrayIntlNode appendArrayIntlNode_;
            @Node.Child
            TStringInternalNodes.CalcStringAttributesNode calcAttributesNode_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile calcAttrsProfile_;

            AppendData() {
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

    @GeneratedBy(value=TruffleStringBuilder.AppendStringNode.class)
    static final class AppendStringNodeGen
    extends TruffleStringBuilder.AppendStringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private AppendData append_cache;

        private AppendStringNodeGen() {
        }

        @Override
        public void execute(TruffleStringBuilder arg0Value, AbstractTruffleString arg1Value) {
            AppendData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.append_cache) != null) {
                TruffleStringBuilder.AppendStringNode.append(arg0Value, arg1Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.appendArrayIntlNode_);
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(TruffleStringBuilder arg0Value, AbstractTruffleString arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                AppendData s0_ = super.insert(new AppendData());
                s0_.toIndexableNode_ = s0_.insertAccessor(TruffleString.ToIndexableNode.create());
                s0_.getCodePointLengthNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodePointLengthNodeGen.create());
                s0_.getCodeRangeNode_ = s0_.insertAccessor(TStringInternalNodesFactory.GetCodeRangeNodeGen.create());
                s0_.appendArrayIntlNode_ = s0_.insertAccessor(AppendArrayIntlNodeGen.create());
                VarHandle.storeStoreFence();
                this.append_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleStringBuilder.AppendStringNode.append(arg0Value, arg1Value, s0_.toIndexableNode_, s0_.getCodePointLengthNode_, s0_.getCodeRangeNode_, s0_.appendArrayIntlNode_);
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

        public static TruffleStringBuilder.AppendStringNode create() {
            return new AppendStringNodeGen();
        }

        public static TruffleStringBuilder.AppendStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleStringBuilder.AppendStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleStringBuilder.AppendStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void execute(TruffleStringBuilder arg0Value, AbstractTruffleString arg1Value) {
                TruffleStringBuilder.AppendStringNode.append(arg0Value, arg1Value, TruffleString.ToIndexableNode.getUncached(), TStringInternalNodes.GetCodePointLengthNode.getUncached(), TStringInternalNodes.GetCodeRangeNode.getUncached(), AppendArrayIntlNodeGen.getUncached());
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

        @GeneratedBy(value=TruffleStringBuilder.AppendStringNode.class)
        private static final class AppendData
        extends Node {
            @Node.Child
            TruffleString.ToIndexableNode toIndexableNode_;
            @Node.Child
            TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode_;
            @Node.Child
            TStringInternalNodes.GetCodeRangeNode getCodeRangeNode_;
            @Node.Child
            TruffleStringBuilder.AppendArrayIntlNode appendArrayIntlNode_;

            AppendData() {
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

    @GeneratedBy(value=TruffleStringBuilder.AppendLongNumberNode.class)
    static final class AppendLongNumberNodeGen
    extends TruffleStringBuilder.AppendLongNumberNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private AppendData append_cache;

        private AppendLongNumberNodeGen() {
        }

        @Override
        @ExplodeLoop
        public void execute(TruffleStringBuilder arg0Value, long arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                AppendData s0_ = this.append_cache;
                while (s0_ != null) {
                    if (s0_.cachedStride_ == arg0Value.stride) {
                        this.doAppend(arg0Value, arg1Value, s0_.cachedStride_, s0_.bufferGrowProfile_, s0_.errorProfile_);
                        return;
                    }
                    s0_ = s0_.next_;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value);
        }

        private void executeAndSpecialize(TruffleStringBuilder arg0Value, long arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int count0_ = 0;
                AppendData s0_ = this.append_cache;
                if (state_0 != 0) {
                    while (s0_ != null && s0_.cachedStride_ != arg0Value.stride) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && count0_ < 3) {
                    s0_ = new AppendData(this.append_cache);
                    s0_.cachedStride_ = arg0Value.stride;
                    s0_.bufferGrowProfile_ = ConditionProfile.create();
                    s0_.errorProfile_ = BranchProfile.create();
                    VarHandle.storeStoreFence();
                    this.append_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    this.doAppend(arg0Value, arg1Value, s0_.cachedStride_, s0_.bufferGrowProfile_, s0_.errorProfile_);
                    return;
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
            AppendData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.append_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleStringBuilder.AppendLongNumberNode create() {
            return new AppendLongNumberNodeGen();
        }

        public static TruffleStringBuilder.AppendLongNumberNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleStringBuilder.AppendLongNumberNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleStringBuilder.AppendLongNumberNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void execute(TruffleStringBuilder arg0Value, long arg1Value) {
                this.doAppend(arg0Value, arg1Value, arg0Value.stride, ConditionProfile.getUncached(), BranchProfile.getUncached());
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

        @GeneratedBy(value=TruffleStringBuilder.AppendLongNumberNode.class)
        private static final class AppendData {
            @CompilerDirectives.CompilationFinal
            AppendData next_;
            @CompilerDirectives.CompilationFinal
            int cachedStride_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile bufferGrowProfile_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorProfile_;

            AppendData(AppendData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=TruffleStringBuilder.AppendIntNumberNode.class)
    static final class AppendIntNumberNodeGen
    extends TruffleStringBuilder.AppendIntNumberNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private AppendData append_cache;

        private AppendIntNumberNodeGen() {
        }

        @Override
        @ExplodeLoop
        public void execute(TruffleStringBuilder arg0Value, int arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                AppendData s0_ = this.append_cache;
                while (s0_ != null) {
                    if (s0_.cachedStride_ == arg0Value.stride) {
                        this.doAppend(arg0Value, arg1Value, s0_.cachedStride_, s0_.bufferGrowProfile_, s0_.errorProfile_);
                        return;
                    }
                    s0_ = s0_.next_;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value);
        }

        private void executeAndSpecialize(TruffleStringBuilder arg0Value, int arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int count0_ = 0;
                AppendData s0_ = this.append_cache;
                if (state_0 != 0) {
                    while (s0_ != null && s0_.cachedStride_ != arg0Value.stride) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && count0_ < 3) {
                    s0_ = new AppendData(this.append_cache);
                    s0_.cachedStride_ = arg0Value.stride;
                    s0_.bufferGrowProfile_ = ConditionProfile.create();
                    s0_.errorProfile_ = BranchProfile.create();
                    VarHandle.storeStoreFence();
                    this.append_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    this.doAppend(arg0Value, arg1Value, s0_.cachedStride_, s0_.bufferGrowProfile_, s0_.errorProfile_);
                    return;
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
            AppendData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.append_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleStringBuilder.AppendIntNumberNode create() {
            return new AppendIntNumberNodeGen();
        }

        public static TruffleStringBuilder.AppendIntNumberNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleStringBuilder.AppendIntNumberNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleStringBuilder.AppendIntNumberNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void execute(TruffleStringBuilder arg0Value, int arg1Value) {
                this.doAppend(arg0Value, arg1Value, arg0Value.stride, ConditionProfile.getUncached(), BranchProfile.getUncached());
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

        @GeneratedBy(value=TruffleStringBuilder.AppendIntNumberNode.class)
        private static final class AppendData {
            @CompilerDirectives.CompilationFinal
            AppendData next_;
            @CompilerDirectives.CompilationFinal
            int cachedStride_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile bufferGrowProfile_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorProfile_;

            AppendData(AppendData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=TruffleStringBuilder.AppendCodePointIntlNode.class)
    static final class AppendCodePointIntlNodeGen
    extends TruffleStringBuilder.AppendCodePointIntlNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private Utf16CachedData utf16Cached_cache;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile utf16Uncached_bmpProfile_;
        @CompilerDirectives.CompilationFinal
        private Utf32CachedData utf32Cached_cache;

        private AppendCodePointIntlNodeGen() {
        }

        @Override
        @ExplodeLoop
        void execute(TruffleStringBuilder arg0Value, int arg1Value, TruffleString.Encoding arg2Value, int arg3Value, boolean arg4Value, ConditionProfile arg5Value, BranchProfile arg6Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.isAsciiBytesOrLatin1(arg2Value)) {
                    TruffleStringBuilder.AppendCodePointIntlNode.bytes(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return;
                }
                if ((state_0 & 2) != 0 && TStringGuards.isUTF8(arg2Value)) {
                    TruffleStringBuilder.AppendCodePointIntlNode.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return;
                }
                if ((state_0 & 4) != 0 && TStringGuards.isUTF16(arg2Value)) {
                    Utf16CachedData s2_ = this.utf16Cached_cache;
                    while (s2_ != null) {
                        if (s2_.cachedCurStride_ == arg0Value.stride && s2_.cachedNewStride_ == TruffleStringBuilder.utf16Stride(arg0Value, arg1Value)) {
                            this.utf16Cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s2_.cachedCurStride_, s2_.cachedNewStride_, s2_.bmpProfile_);
                            return;
                        }
                        s2_ = s2_.next_;
                    }
                }
                if ((state_0 & 8) != 0 && TStringGuards.isUTF16(arg2Value)) {
                    this.utf16Uncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.utf16Uncached_bmpProfile_);
                    return;
                }
                if ((state_0 & 0x10) != 0 && TStringGuards.isUTF32(arg2Value)) {
                    Utf32CachedData s4_ = this.utf32Cached_cache;
                    while (s4_ != null) {
                        if (s4_.cachedCurStride_ == arg0Value.stride && s4_.cachedNewStride_ == TruffleStringBuilder.utf32Stride(arg0Value, arg1Value)) {
                            this.utf32Cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s4_.cachedCurStride_, s4_.cachedNewStride_);
                            return;
                        }
                        s4_ = s4_.next_;
                    }
                }
                if ((state_0 & 0x20) != 0 && TStringGuards.isUTF32(arg2Value)) {
                    this.utf32Uncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return;
                }
                if ((state_0 & 0x40) != 0 && TStringGuards.isUnsupportedEncoding(arg2Value)) {
                    TruffleStringBuilder.AppendCodePointIntlNode.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
        }

        private void executeAndSpecialize(TruffleStringBuilder arg0Value, int arg1Value, TruffleString.Encoding arg2Value, int arg3Value, boolean arg4Value, ConditionProfile arg5Value, BranchProfile arg6Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (TStringGuards.isAsciiBytesOrLatin1(arg2Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    TruffleStringBuilder.AppendCodePointIntlNode.bytes(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return;
                }
                if (TStringGuards.isUTF8(arg2Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    TruffleStringBuilder.AppendCodePointIntlNode.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return;
                }
                if ((exclude & 1) == 0 && TStringGuards.isUTF16(arg2Value)) {
                    int cachedNewStride__;
                    int count2_ = 0;
                    Utf16CachedData s2_ = this.utf16Cached_cache;
                    if ((state_0 & 4) != 0) {
                        while (s2_ != null && (s2_.cachedCurStride_ != arg0Value.stride || s2_.cachedNewStride_ != TruffleStringBuilder.utf16Stride(arg0Value, arg1Value))) {
                            s2_ = s2_.next_;
                            ++count2_;
                        }
                    }
                    if (s2_ == null && (cachedNewStride__ = TruffleStringBuilder.utf16Stride(arg0Value, arg1Value)) == TruffleStringBuilder.utf16Stride(arg0Value, arg1Value) && count2_ < 9) {
                        s2_ = new Utf16CachedData(this.utf16Cached_cache);
                        s2_.cachedCurStride_ = arg0Value.stride;
                        s2_.cachedNewStride_ = cachedNewStride__;
                        s2_.bmpProfile_ = ConditionProfile.create();
                        VarHandle.storeStoreFence();
                        this.utf16Cached_cache = s2_;
                        this.state_0_ = state_0 |= 4;
                    }
                    if (s2_ != null) {
                        lock.unlock();
                        hasLock = false;
                        this.utf16Cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s2_.cachedCurStride_, s2_.cachedNewStride_, s2_.bmpProfile_);
                        return;
                    }
                }
                if (TStringGuards.isUTF16(arg2Value)) {
                    this.utf16Uncached_bmpProfile_ = ConditionProfile.create();
                    this.exclude_ = exclude |= 1;
                    this.utf16Cached_cache = null;
                    state_0 &= 0xFFFFFFFB;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    this.utf16Uncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, this.utf16Uncached_bmpProfile_);
                    return;
                }
                if ((exclude & 2) == 0 && TStringGuards.isUTF32(arg2Value)) {
                    int cachedNewStride__1;
                    int count4_ = 0;
                    Utf32CachedData s4_ = this.utf32Cached_cache;
                    if ((state_0 & 0x10) != 0) {
                        while (s4_ != null && (s4_.cachedCurStride_ != arg0Value.stride || s4_.cachedNewStride_ != TruffleStringBuilder.utf32Stride(arg0Value, arg1Value))) {
                            s4_ = s4_.next_;
                            ++count4_;
                        }
                    }
                    if (s4_ == null && (cachedNewStride__1 = TruffleStringBuilder.utf32Stride(arg0Value, arg1Value)) == TruffleStringBuilder.utf32Stride(arg0Value, arg1Value) && count4_ < 9) {
                        s4_ = new Utf32CachedData(this.utf32Cached_cache);
                        s4_.cachedCurStride_ = arg0Value.stride;
                        s4_.cachedNewStride_ = cachedNewStride__1;
                        VarHandle.storeStoreFence();
                        this.utf32Cached_cache = s4_;
                        this.state_0_ = state_0 |= 0x10;
                    }
                    if (s4_ != null) {
                        lock.unlock();
                        hasLock = false;
                        this.utf32Cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s4_.cachedCurStride_, s4_.cachedNewStride_);
                        return;
                    }
                }
                if (TStringGuards.isUTF32(arg2Value)) {
                    this.exclude_ = exclude |= 2;
                    this.utf32Cached_cache = null;
                    state_0 &= 0xFFFFFFEF;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    this.utf32Uncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return;
                }
                if (TStringGuards.isUnsupportedEncoding(arg2Value)) {
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    TruffleStringBuilder.AppendCodePointIntlNode.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return;
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
                Utf16CachedData s2_ = this.utf16Cached_cache;
                Utf32CachedData s4_ = this.utf32Cached_cache;
                if (!(s2_ != null && s2_.next_ != null || s4_ != null && s4_.next_ != null)) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TruffleStringBuilder.AppendCodePointIntlNode create() {
            return new AppendCodePointIntlNodeGen();
        }

        public static TruffleStringBuilder.AppendCodePointIntlNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleStringBuilder.AppendCodePointIntlNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleStringBuilder.AppendCodePointIntlNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            void execute(TruffleStringBuilder arg0Value, int arg1Value, TruffleString.Encoding arg2Value, int arg3Value, boolean arg4Value, ConditionProfile arg5Value, BranchProfile arg6Value) {
                if (TStringGuards.isAsciiBytesOrLatin1(arg2Value)) {
                    TruffleStringBuilder.AppendCodePointIntlNode.bytes(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return;
                }
                if (TStringGuards.isUTF8(arg2Value)) {
                    TruffleStringBuilder.AppendCodePointIntlNode.utf8(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return;
                }
                if (TStringGuards.isUTF16(arg2Value)) {
                    this.utf16Uncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, ConditionProfile.getUncached());
                    return;
                }
                if (TStringGuards.isUTF32(arg2Value)) {
                    this.utf32Uncached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return;
                }
                if (TStringGuards.isUnsupportedEncoding(arg2Value)) {
                    TruffleStringBuilder.AppendCodePointIntlNode.unsupported(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
                    return;
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

        @GeneratedBy(value=TruffleStringBuilder.AppendCodePointIntlNode.class)
        private static final class Utf32CachedData {
            @CompilerDirectives.CompilationFinal
            Utf32CachedData next_;
            @CompilerDirectives.CompilationFinal
            int cachedCurStride_;
            @CompilerDirectives.CompilationFinal
            int cachedNewStride_;

            Utf32CachedData(Utf32CachedData next_) {
                this.next_ = next_;
            }
        }

        @GeneratedBy(value=TruffleStringBuilder.AppendCodePointIntlNode.class)
        private static final class Utf16CachedData {
            @CompilerDirectives.CompilationFinal
            Utf16CachedData next_;
            @CompilerDirectives.CompilationFinal
            int cachedCurStride_;
            @CompilerDirectives.CompilationFinal
            int cachedNewStride_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile bmpProfile_;

            Utf16CachedData(Utf16CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=TruffleStringBuilder.AppendCodePointNode.class)
    static final class AppendCodePointNodeGen
    extends TruffleStringBuilder.AppendCodePointNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private AppendData append_cache;

        private AppendCodePointNodeGen() {
        }

        @Override
        public void execute(TruffleStringBuilder arg0Value, int arg1Value, int arg2Value, boolean arg3Value) {
            AppendData s0_;
            int state_0 = this.state_0_;
            if (state_0 != 0 && (s0_ = this.append_cache) != null) {
                TruffleStringBuilder.AppendCodePointNode.append(arg0Value, arg1Value, arg2Value, arg3Value, s0_.appendCodePointIntlNode_, s0_.bufferGrowProfile_, s0_.errorProfile_);
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(TruffleStringBuilder arg0Value, int arg1Value, int arg2Value, boolean arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                AppendData s0_ = super.insert(new AppendData());
                s0_.appendCodePointIntlNode_ = s0_.insertAccessor(AppendCodePointIntlNodeGen.create());
                s0_.bufferGrowProfile_ = ConditionProfile.create();
                s0_.errorProfile_ = BranchProfile.create();
                VarHandle.storeStoreFence();
                this.append_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleStringBuilder.AppendCodePointNode.append(arg0Value, arg1Value, arg2Value, arg3Value, s0_.appendCodePointIntlNode_, s0_.bufferGrowProfile_, s0_.errorProfile_);
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

        public static TruffleStringBuilder.AppendCodePointNode create() {
            return new AppendCodePointNodeGen();
        }

        public static TruffleStringBuilder.AppendCodePointNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleStringBuilder.AppendCodePointNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleStringBuilder.AppendCodePointNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void execute(TruffleStringBuilder arg0Value, int arg1Value, int arg2Value, boolean arg3Value) {
                TruffleStringBuilder.AppendCodePointNode.append(arg0Value, arg1Value, arg2Value, arg3Value, AppendCodePointIntlNodeGen.getUncached(), ConditionProfile.getUncached(), BranchProfile.getUncached());
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

        @GeneratedBy(value=TruffleStringBuilder.AppendCodePointNode.class)
        private static final class AppendData
        extends Node {
            @Node.Child
            TruffleStringBuilder.AppendCodePointIntlNode appendCodePointIntlNode_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile bufferGrowProfile_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorProfile_;

            AppendData() {
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

    @GeneratedBy(value=TruffleStringBuilder.AppendCharUTF16Node.class)
    static final class AppendCharUTF16NodeGen
    extends TruffleStringBuilder.AppendCharUTF16Node {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile uncached_bufferGrowProfile_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile uncached_errorProfile_;

        private AppendCharUTF16NodeGen() {
        }

        @Override
        @ExplodeLoop
        public void execute(TruffleStringBuilder arg0Value, char arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (s0_.cachedCurStride_ == arg0Value.stride && s0_.cachedNewStride_ == TruffleStringBuilder.utf16Stride(arg0Value, arg1Value)) {
                            this.doCached(arg0Value, arg1Value, s0_.cachedCurStride_, s0_.cachedNewStride_, s0_.bufferGrowProfile_, s0_.errorProfile_);
                            return;
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    this.doUncached(arg0Value, arg1Value, this.uncached_bufferGrowProfile_, this.uncached_errorProfile_);
                    return;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(TruffleStringBuilder arg0Value, char arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    int cachedNewStride__;
                    int count0_ = 0;
                    CachedData s0_ = this.cached_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && (s0_.cachedCurStride_ != arg0Value.stride || s0_.cachedNewStride_ != TruffleStringBuilder.utf16Stride(arg0Value, arg1Value))) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && (cachedNewStride__ = TruffleStringBuilder.utf16Stride(arg0Value, arg1Value)) == TruffleStringBuilder.utf16Stride(arg0Value, arg1Value) && count0_ < 9) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedCurStride_ = arg0Value.stride;
                        s0_.cachedNewStride_ = cachedNewStride__;
                        s0_.bufferGrowProfile_ = ConditionProfile.create();
                        s0_.errorProfile_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        this.doCached(arg0Value, arg1Value, s0_.cachedCurStride_, s0_.cachedNewStride_, s0_.bufferGrowProfile_, s0_.errorProfile_);
                        return;
                    }
                }
                this.uncached_bufferGrowProfile_ = ConditionProfile.create();
                this.uncached_errorProfile_ = BranchProfile.create();
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                this.doUncached(arg0Value, arg1Value, this.uncached_bufferGrowProfile_, this.uncached_errorProfile_);
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

        public static TruffleStringBuilder.AppendCharUTF16Node create() {
            return new AppendCharUTF16NodeGen();
        }

        public static TruffleStringBuilder.AppendCharUTF16Node getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleStringBuilder.AppendCharUTF16Node.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleStringBuilder.AppendCharUTF16Node {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void execute(TruffleStringBuilder arg0Value, char arg1Value) {
                this.doUncached(arg0Value, arg1Value, ConditionProfile.getUncached(), BranchProfile.getUncached());
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

        @GeneratedBy(value=TruffleStringBuilder.AppendCharUTF16Node.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            int cachedCurStride_;
            @CompilerDirectives.CompilationFinal
            int cachedNewStride_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile bufferGrowProfile_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorProfile_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=TruffleStringBuilder.AppendByteNode.class)
    static final class AppendByteNodeGen
    extends TruffleStringBuilder.AppendByteNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile bufferGrowProfile_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile errorProfile_;

        private AppendByteNodeGen() {
        }

        @Override
        public void execute(TruffleStringBuilder arg0Value, byte arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                TruffleStringBuilder.AppendByteNode.append(arg0Value, arg1Value, this.bufferGrowProfile_, this.errorProfile_);
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(TruffleStringBuilder arg0Value, byte arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.bufferGrowProfile_ = ConditionProfile.create();
                this.errorProfile_ = BranchProfile.create();
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleStringBuilder.AppendByteNode.append(arg0Value, arg1Value, this.bufferGrowProfile_, this.errorProfile_);
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

        public static TruffleStringBuilder.AppendByteNode create() {
            return new AppendByteNodeGen();
        }

        public static TruffleStringBuilder.AppendByteNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TruffleStringBuilder.AppendByteNode.class)
        @DenyReplace
        private static final class Uncached
        extends TruffleStringBuilder.AppendByteNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void execute(TruffleStringBuilder arg0Value, byte arg1Value) {
                TruffleStringBuilder.AppendByteNode.append(arg0Value, arg1Value, ConditionProfile.getUncached(), BranchProfile.getUncached());
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

