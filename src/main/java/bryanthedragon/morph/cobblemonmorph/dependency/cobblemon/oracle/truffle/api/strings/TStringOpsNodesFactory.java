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
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TStringOpsNodes;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TStringOpsNodes.class)
final class TStringOpsNodesFactory {
    TStringOpsNodesFactory() {
    }

    @GeneratedBy(value=TStringOpsNodes.CalculateHashCodeNode.class)
    static final class CalculateHashCodeNodeGen
    extends TStringOpsNodes.CalculateHashCodeNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private CalculateHashCodeNodeGen() {
        }

        @Override
        @ExplodeLoop
        int execute(AbstractTruffleString arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    if (TStringGuards.stride(arg0Value) == s0_.cachedStrideA_) {
                        return this.cached(arg0Value, arg1Value, s0_.cachedStrideA_);
                    }
                    s0_ = s0_.next_;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int count0_ = 0;
                CachedData s0_ = this.cached_cache;
                if (state_0 != 0) {
                    while (s0_ != null && TStringGuards.stride(arg0Value) != s0_.cachedStrideA_) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null) {
                    int cachedStrideA__ = TStringGuards.stride(arg0Value);
                    if (TStringGuards.stride(arg0Value) == cachedStrideA__ && count0_ < 9) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedStrideA_ = cachedStrideA__;
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    int n = this.cached(arg0Value, arg1Value, s0_.cachedStrideA_);
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

        public static TStringOpsNodes.CalculateHashCodeNode create() {
            return new CalculateHashCodeNodeGen();
        }

        public static TStringOpsNodes.CalculateHashCodeNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringOpsNodes.CalculateHashCodeNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringOpsNodes.CalculateHashCodeNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value) {
                if (TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value)) {
                    return this.cached(arg0Value, arg1Value, TStringGuards.stride(arg0Value));
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

        @GeneratedBy(value=TStringOpsNodes.CalculateHashCodeNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideA_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=TStringOpsNodes.RawLastIndexOfStringNode.class)
    static final class RawLastIndexOfStringNodeGen
    extends TStringOpsNodes.RawLastIndexOfStringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private CachedLen1Data cachedLen1_cache;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private RawLastIndexOfStringNodeGen() {
        }

        @Override
        @ExplodeLoop
        int execute(AbstractTruffleString arg0Value, Object arg1Value, AbstractTruffleString arg2Value, Object arg3Value, int arg4Value, int arg5Value, byte[] arg6Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.length(arg2Value) == 1) {
                    CachedLen1Data s0_ = this.cachedLen1_cache;
                    while (s0_ != null) {
                        if (TStringGuards.stride(arg0Value) == s0_.cachedStrideA_ && TStringGuards.stride(arg2Value) == s0_.cachedStrideB_) {
                            return this.cachedLen1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.cachedStrideA_, s0_.cachedStrideB_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0 && TStringGuards.length(arg2Value) > 1) {
                    CachedData s1_ = this.cached_cache;
                    while (s1_ != null) {
                        if (TStringGuards.stride(arg0Value) == s1_.cachedStrideA_ && TStringGuards.stride(arg2Value) == s1_.cachedStrideB_) {
                            return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s1_.cachedStrideA_, s1_.cachedStrideB_);
                        }
                        s1_ = s1_.next_;
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, AbstractTruffleString arg2Value, Object arg3Value, int arg4Value, int arg5Value, byte[] arg6Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.length(arg2Value) == 1) {
                    int cachedStrideA__;
                    int count0_ = 0;
                    CachedLen1Data s0_ = this.cachedLen1_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && (TStringGuards.stride(arg0Value) != s0_.cachedStrideA_ || TStringGuards.stride(arg2Value) != s0_.cachedStrideB_)) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null) {
                        cachedStrideA__ = TStringGuards.stride(arg0Value);
                        if (TStringGuards.stride(arg0Value) == cachedStrideA__) {
                            int cachedStrideB__ = TStringGuards.stride(arg2Value);
                            if (TStringGuards.stride(arg2Value) == cachedStrideB__ && count0_ < 9) {
                                s0_ = new CachedLen1Data(this.cachedLen1_cache);
                                s0_.cachedStrideA_ = cachedStrideA__;
                                s0_.cachedStrideB_ = cachedStrideB__;
                                VarHandle.storeStoreFence();
                                this.cachedLen1_cache = s0_;
                                this.state_0_ = state_0 |= 1;
                            }
                        }
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        cachedStrideA__ = this.cachedLen1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.cachedStrideA_, s0_.cachedStrideB_);
                        return cachedStrideA__;
                    }
                }
                if (TStringGuards.length(arg2Value) > 1) {
                    int count1_ = 0;
                    CachedData s1_ = this.cached_cache;
                    if ((state_0 & 2) != 0) {
                        while (s1_ != null && (TStringGuards.stride(arg0Value) != s1_.cachedStrideA_ || TStringGuards.stride(arg2Value) != s1_.cachedStrideB_)) {
                            s1_ = s1_.next_;
                            ++count1_;
                        }
                    }
                    if (s1_ == null) {
                        int cachedStrideA__1 = TStringGuards.stride(arg0Value);
                        if (TStringGuards.stride(arg0Value) == cachedStrideA__1) {
                            int cachedStrideB__1 = TStringGuards.stride(arg2Value);
                            if (TStringGuards.stride(arg2Value) == cachedStrideB__1 && count1_ < 9) {
                                s1_ = new CachedData(this.cached_cache);
                                s1_.cachedStrideA_ = cachedStrideA__1;
                                s1_.cachedStrideB_ = cachedStrideB__1;
                                VarHandle.storeStoreFence();
                                this.cached_cache = s1_;
                                this.state_0_ = state_0 |= 2;
                            }
                        }
                    }
                    if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        int n = this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s1_.cachedStrideA_, s1_.cachedStrideB_);
                        return n;
                    }
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
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
                CachedLen1Data s0_ = this.cachedLen1_cache;
                CachedData s1_ = this.cached_cache;
                if (!(s0_ != null && s0_.next_ != null || s1_ != null && s1_.next_ != null)) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TStringOpsNodes.RawLastIndexOfStringNode create() {
            return new RawLastIndexOfStringNodeGen();
        }

        public static TStringOpsNodes.RawLastIndexOfStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringOpsNodes.RawLastIndexOfStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringOpsNodes.RawLastIndexOfStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, AbstractTruffleString arg2Value, Object arg3Value, int arg4Value, int arg5Value, byte[] arg6Value) {
                if (TStringGuards.length(arg2Value) == 1 && TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value) && TStringGuards.stride(arg2Value) == TStringGuards.stride(arg2Value)) {
                    return this.cachedLen1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringGuards.stride(arg0Value), TStringGuards.stride(arg2Value));
                }
                if (TStringGuards.length(arg2Value) > 1 && TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value) && TStringGuards.stride(arg2Value) == TStringGuards.stride(arg2Value)) {
                    return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringGuards.stride(arg0Value), TStringGuards.stride(arg2Value));
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
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

        @GeneratedBy(value=TStringOpsNodes.RawLastIndexOfStringNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideA_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideB_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }

        @GeneratedBy(value=TStringOpsNodes.RawLastIndexOfStringNode.class)
        private static final class CachedLen1Data {
            @CompilerDirectives.CompilationFinal
            CachedLen1Data next_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideA_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideB_;

            CachedLen1Data(CachedLen1Data next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=TStringOpsNodes.RawIndexOfStringNode.class)
    static final class RawIndexOfStringNodeGen
    extends TStringOpsNodes.RawIndexOfStringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private CachedLen1Data cachedLen1_cache;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private RawIndexOfStringNodeGen() {
        }

        @Override
        @ExplodeLoop
        int execute(AbstractTruffleString arg0Value, Object arg1Value, AbstractTruffleString arg2Value, Object arg3Value, int arg4Value, int arg5Value, byte[] arg6Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.length(arg2Value) == 1) {
                    CachedLen1Data s0_ = this.cachedLen1_cache;
                    while (s0_ != null) {
                        if (TStringGuards.stride(arg0Value) == s0_.cachedStrideA_ && TStringGuards.stride(arg2Value) == s0_.cachedStrideB_) {
                            return this.cachedLen1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.cachedStrideA_, s0_.cachedStrideB_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0 && TStringGuards.length(arg2Value) > 1) {
                    CachedData s1_ = this.cached_cache;
                    while (s1_ != null) {
                        if (TStringGuards.stride(arg0Value) == s1_.cachedStrideA_ && TStringGuards.stride(arg2Value) == s1_.cachedStrideB_) {
                            return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s1_.cachedStrideA_, s1_.cachedStrideB_);
                        }
                        s1_ = s1_.next_;
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, AbstractTruffleString arg2Value, Object arg3Value, int arg4Value, int arg5Value, byte[] arg6Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (TStringGuards.length(arg2Value) == 1) {
                    int cachedStrideA__;
                    int count0_ = 0;
                    CachedLen1Data s0_ = this.cachedLen1_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && (TStringGuards.stride(arg0Value) != s0_.cachedStrideA_ || TStringGuards.stride(arg2Value) != s0_.cachedStrideB_)) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null) {
                        cachedStrideA__ = TStringGuards.stride(arg0Value);
                        if (TStringGuards.stride(arg0Value) == cachedStrideA__) {
                            int cachedStrideB__ = TStringGuards.stride(arg2Value);
                            if (TStringGuards.stride(arg2Value) == cachedStrideB__ && count0_ < 9) {
                                s0_ = new CachedLen1Data(this.cachedLen1_cache);
                                s0_.cachedStrideA_ = cachedStrideA__;
                                s0_.cachedStrideB_ = cachedStrideB__;
                                VarHandle.storeStoreFence();
                                this.cachedLen1_cache = s0_;
                                this.state_0_ = state_0 |= 1;
                            }
                        }
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        cachedStrideA__ = this.cachedLen1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s0_.cachedStrideA_, s0_.cachedStrideB_);
                        return cachedStrideA__;
                    }
                }
                if (TStringGuards.length(arg2Value) > 1) {
                    int count1_ = 0;
                    CachedData s1_ = this.cached_cache;
                    if ((state_0 & 2) != 0) {
                        while (s1_ != null && (TStringGuards.stride(arg0Value) != s1_.cachedStrideA_ || TStringGuards.stride(arg2Value) != s1_.cachedStrideB_)) {
                            s1_ = s1_.next_;
                            ++count1_;
                        }
                    }
                    if (s1_ == null) {
                        int cachedStrideA__1 = TStringGuards.stride(arg0Value);
                        if (TStringGuards.stride(arg0Value) == cachedStrideA__1) {
                            int cachedStrideB__1 = TStringGuards.stride(arg2Value);
                            if (TStringGuards.stride(arg2Value) == cachedStrideB__1 && count1_ < 9) {
                                s1_ = new CachedData(this.cached_cache);
                                s1_.cachedStrideA_ = cachedStrideA__1;
                                s1_.cachedStrideB_ = cachedStrideB__1;
                                VarHandle.storeStoreFence();
                                this.cached_cache = s1_;
                                this.state_0_ = state_0 |= 2;
                            }
                        }
                    }
                    if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        int n = this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s1_.cachedStrideA_, s1_.cachedStrideB_);
                        return n;
                    }
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
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
                CachedLen1Data s0_ = this.cachedLen1_cache;
                CachedData s1_ = this.cached_cache;
                if (!(s0_ != null && s0_.next_ != null || s1_ != null && s1_.next_ != null)) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TStringOpsNodes.RawIndexOfStringNode create() {
            return new RawIndexOfStringNodeGen();
        }

        public static TStringOpsNodes.RawIndexOfStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringOpsNodes.RawIndexOfStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringOpsNodes.RawIndexOfStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, AbstractTruffleString arg2Value, Object arg3Value, int arg4Value, int arg5Value, byte[] arg6Value) {
                if (TStringGuards.length(arg2Value) == 1 && TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value) && TStringGuards.stride(arg2Value) == TStringGuards.stride(arg2Value)) {
                    return this.cachedLen1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringGuards.stride(arg0Value), TStringGuards.stride(arg2Value));
                }
                if (TStringGuards.length(arg2Value) > 1 && TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value) && TStringGuards.stride(arg2Value) == TStringGuards.stride(arg2Value)) {
                    return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, TStringGuards.stride(arg0Value), TStringGuards.stride(arg2Value));
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
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

        @GeneratedBy(value=TStringOpsNodes.RawIndexOfStringNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideA_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideB_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }

        @GeneratedBy(value=TStringOpsNodes.RawIndexOfStringNode.class)
        private static final class CachedLen1Data {
            @CompilerDirectives.CompilationFinal
            CachedLen1Data next_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideA_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideB_;

            CachedLen1Data(CachedLen1Data next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=TStringOpsNodes.RawLastIndexOfCodePointNode.class)
    static final class RawLastIndexOfCodePointNodeGen
    extends TStringOpsNodes.RawLastIndexOfCodePointNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private RawLastIndexOfCodePointNodeGen() {
        }

        @Override
        @ExplodeLoop
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    if (TStringGuards.stride(arg0Value) == s0_.cachedStrideA_) {
                        return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStrideA_);
                    }
                    s0_ = s0_.next_;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int count0_ = 0;
                CachedData s0_ = this.cached_cache;
                if (state_0 != 0) {
                    while (s0_ != null && TStringGuards.stride(arg0Value) != s0_.cachedStrideA_) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null) {
                    int cachedStrideA__ = TStringGuards.stride(arg0Value);
                    if (TStringGuards.stride(arg0Value) == cachedStrideA__ && count0_ < 9) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedStrideA_ = cachedStrideA__;
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    int n = this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStrideA_);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
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

        public static TStringOpsNodes.RawLastIndexOfCodePointNode create() {
            return new RawLastIndexOfCodePointNodeGen();
        }

        public static TStringOpsNodes.RawLastIndexOfCodePointNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringOpsNodes.RawLastIndexOfCodePointNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringOpsNodes.RawLastIndexOfCodePointNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value) {
                if (TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value)) {
                    return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TStringGuards.stride(arg0Value));
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
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

        @GeneratedBy(value=TStringOpsNodes.RawLastIndexOfCodePointNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideA_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=TStringOpsNodes.RawIndexOfCodePointNode.class)
    static final class RawIndexOfCodePointNodeGen
    extends TStringOpsNodes.RawIndexOfCodePointNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private RawIndexOfCodePointNodeGen() {
        }

        @Override
        @ExplodeLoop
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    if (TStringGuards.stride(arg0Value) == s0_.cachedStrideA_) {
                        return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStrideA_);
                    }
                    s0_ = s0_.next_;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int count0_ = 0;
                CachedData s0_ = this.cached_cache;
                if (state_0 != 0) {
                    while (s0_ != null && TStringGuards.stride(arg0Value) != s0_.cachedStrideA_) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null) {
                    int cachedStrideA__ = TStringGuards.stride(arg0Value);
                    if (TStringGuards.stride(arg0Value) == cachedStrideA__ && count0_ < 9) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedStrideA_ = cachedStrideA__;
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    int n = this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, s0_.cachedStrideA_);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
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

        public static TStringOpsNodes.RawIndexOfCodePointNode create() {
            return new RawIndexOfCodePointNodeGen();
        }

        public static TStringOpsNodes.RawIndexOfCodePointNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringOpsNodes.RawIndexOfCodePointNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringOpsNodes.RawIndexOfCodePointNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int arg4Value) {
                if (TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value)) {
                    return this.cached(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, TStringGuards.stride(arg0Value));
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
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

        @GeneratedBy(value=TStringOpsNodes.RawIndexOfCodePointNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideA_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=TStringOpsNodes.IndexOfAnyIntNode.class)
    static final class IndexOfAnyIntNodeGen
    extends TStringOpsNodes.IndexOfAnyIntNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private IndexOfAnyIntNodeGen() {
        }

        @Override
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int[] arg4Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.isStride0(arg0Value) && arg4Value.length == 1) {
                    return this.stride0(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if ((state_0 & 2) != 0 && TStringGuards.isStride0(arg0Value) && arg4Value.length > 1) {
                    return this.stride0MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if ((state_0 & 4) != 0 && TStringGuards.isStride1(arg0Value) && arg4Value.length == 1) {
                    return this.stride1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if ((state_0 & 8) != 0 && TStringGuards.isStride1(arg0Value) && arg4Value.length > 1) {
                    return this.stride1MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if ((state_0 & 0x10) != 0 && TStringGuards.isStride2(arg0Value)) {
                    return this.stride2(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int[] arg4Value) {
            int state_0 = this.state_0_;
            if (TStringGuards.isStride0(arg0Value) && arg4Value.length == 1) {
                this.state_0_ = state_0 |= 1;
                return this.stride0(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }
            if (TStringGuards.isStride0(arg0Value) && arg4Value.length > 1) {
                this.state_0_ = state_0 |= 2;
                return this.stride0MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }
            if (TStringGuards.isStride1(arg0Value) && arg4Value.length == 1) {
                this.state_0_ = state_0 |= 4;
                return this.stride1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }
            if (TStringGuards.isStride1(arg0Value) && arg4Value.length > 1) {
                this.state_0_ = state_0 |= 8;
                return this.stride1MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }
            if (TStringGuards.isStride2(arg0Value)) {
                this.state_0_ = state_0 |= 0x10;
                return this.stride2(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
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

        public static TStringOpsNodes.IndexOfAnyIntNode create() {
            return new IndexOfAnyIntNodeGen();
        }

        public static TStringOpsNodes.IndexOfAnyIntNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringOpsNodes.IndexOfAnyIntNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringOpsNodes.IndexOfAnyIntNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, int[] arg4Value) {
                if (TStringGuards.isStride0(arg0Value) && arg4Value.length == 1) {
                    return this.stride0(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if (TStringGuards.isStride0(arg0Value) && arg4Value.length > 1) {
                    return this.stride0MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if (TStringGuards.isStride1(arg0Value) && arg4Value.length == 1) {
                    return this.stride1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if (TStringGuards.isStride1(arg0Value) && arg4Value.length > 1) {
                    return this.stride1MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if (TStringGuards.isStride2(arg0Value)) {
                    return this.stride2(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
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

    @GeneratedBy(value=TStringOpsNodes.IndexOfAnyCharNode.class)
    static final class IndexOfAnyCharNodeGen
    extends TStringOpsNodes.IndexOfAnyCharNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private IndexOfAnyCharNodeGen() {
        }

        @Override
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, char[] arg4Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && TStringGuards.isStride0(arg0Value) && arg4Value.length == 1) {
                    return this.stride0(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if ((state_0 & 2) != 0 && TStringGuards.isStride0(arg0Value) && arg4Value.length > 1) {
                    return this.stride0MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if ((state_0 & 4) != 0 && TStringGuards.isStride1(arg0Value)) {
                    return this.stride1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, char[] arg4Value) {
            int state_0 = this.state_0_;
            if (TStringGuards.isStride0(arg0Value) && arg4Value.length == 1) {
                this.state_0_ = state_0 |= 1;
                return this.stride0(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }
            if (TStringGuards.isStride0(arg0Value) && arg4Value.length > 1) {
                this.state_0_ = state_0 |= 2;
                return this.stride0MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }
            if (TStringGuards.isStride1(arg0Value)) {
                this.state_0_ = state_0 |= 4;
                return this.stride1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
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

        public static TStringOpsNodes.IndexOfAnyCharNode create() {
            return new IndexOfAnyCharNodeGen();
        }

        public static TStringOpsNodes.IndexOfAnyCharNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringOpsNodes.IndexOfAnyCharNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringOpsNodes.IndexOfAnyCharNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value, int arg3Value, char[] arg4Value) {
                if (TStringGuards.isStride0(arg0Value) && arg4Value.length == 1) {
                    return this.stride0(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if (TStringGuards.isStride0(arg0Value) && arg4Value.length > 1) {
                    return this.stride0MultiValue(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                if (TStringGuards.isStride1(arg0Value)) {
                    return this.stride1(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
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

    @GeneratedBy(value=TStringOpsNodes.RawReadValueNode.class)
    static final class RawReadValueNodeGen
    extends TStringOpsNodes.RawReadValueNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private RawReadValueNodeGen() {
        }

        @Override
        @ExplodeLoop
        int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    if (TStringGuards.stride(arg0Value) == s0_.cachedStrideA_) {
                        return TStringOpsNodes.RawReadValueNode.cached(arg0Value, arg1Value, arg2Value, s0_.cachedStrideA_);
                    }
                    s0_ = s0_.next_;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        private int executeAndSpecialize(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int count0_ = 0;
                CachedData s0_ = this.cached_cache;
                if (state_0 != 0) {
                    while (s0_ != null && TStringGuards.stride(arg0Value) != s0_.cachedStrideA_) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null) {
                    int cachedStrideA__ = TStringGuards.stride(arg0Value);
                    if (TStringGuards.stride(arg0Value) == cachedStrideA__ && count0_ < 9) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedStrideA_ = cachedStrideA__;
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    int n = TStringOpsNodes.RawReadValueNode.cached(arg0Value, arg1Value, arg2Value, s0_.cachedStrideA_);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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

        public static TStringOpsNodes.RawReadValueNode create() {
            return new RawReadValueNodeGen();
        }

        public static TStringOpsNodes.RawReadValueNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TStringOpsNodes.RawReadValueNode.class)
        @DenyReplace
        private static final class Uncached
        extends TStringOpsNodes.RawReadValueNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(AbstractTruffleString arg0Value, Object arg1Value, int arg2Value) {
                if (TStringGuards.stride(arg0Value) == TStringGuards.stride(arg0Value)) {
                    return TStringOpsNodes.RawReadValueNode.cached(arg0Value, arg1Value, arg2Value, TStringGuards.stride(arg0Value));
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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

        @GeneratedBy(value=TStringOpsNodes.RawReadValueNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            int cachedStrideA_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }
}

