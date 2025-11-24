/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.regex.result;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.regex.result.RegexResult;
import com.oracle.truffle.regex.runtime.nodes.DispatchNode;
import com.oracle.truffle.regex.runtime.nodes.DispatchNodeGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=RegexResult.class)
public final class RegexResultFactory {

    @GeneratedBy(value=RegexResult.RegexResultGetStartNode.class)
    public static final class RegexResultGetStartNodeGen
    extends RegexResult.RegexResultGetStartNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile lazyProfile_;
        @Node.Child
        private DispatchNode getIndicesCall_;

        private RegexResultGetStartNodeGen() {
        }

        @Override
        public int execute(Object arg0Value, int arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0 && arg0Value instanceof RegexResult) {
                RegexResult arg0Value_ = (RegexResult)arg0Value;
                return RegexResult.RegexResultGetStartNode.doResult(arg0Value_, arg1Value, this.lazyProfile_, this.getIndicesCall_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        private int executeAndSpecialize(Object arg0Value, int arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arg0Value instanceof RegexResult) {
                    RegexResult arg0Value_ = (RegexResult)arg0Value;
                    this.lazyProfile_ = BranchProfile.create();
                    this.getIndicesCall_ = super.insert(DispatchNodeGen.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = RegexResult.RegexResultGetStartNode.doResult(arg0Value_, arg1Value, this.lazyProfile_, this.getIndicesCall_);
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
            return NodeCost.MONOMORPHIC;
        }

        public static RegexResult.RegexResultGetStartNode create() {
            return new RegexResultGetStartNodeGen();
        }

        public static RegexResult.RegexResultGetStartNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=RegexResult.RegexResultGetStartNode.class)
        @DenyReplace
        private static final class Uncached
        extends RegexResult.RegexResultGetStartNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(Object arg0Value, int arg1Value) {
                if (arg0Value instanceof RegexResult) {
                    RegexResult arg0Value_ = (RegexResult)arg0Value;
                    return RegexResult.RegexResultGetStartNode.doResult(arg0Value_, arg1Value, BranchProfile.getUncached(), DispatchNodeGen.getUncached());
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

    @GeneratedBy(value=RegexResult.RegexResultGetEndNode.class)
    static final class RegexResultGetEndNodeGen
    extends RegexResult.RegexResultGetEndNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile lazyProfile_;
        @Node.Child
        private DispatchNode getIndicesCall_;

        private RegexResultGetEndNodeGen() {
        }

        @Override
        int execute(Object arg0Value, int arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0 && arg0Value instanceof RegexResult) {
                RegexResult arg0Value_ = (RegexResult)arg0Value;
                return RegexResult.RegexResultGetEndNode.doResult(arg0Value_, arg1Value, this.lazyProfile_, this.getIndicesCall_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        private int executeAndSpecialize(Object arg0Value, int arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arg0Value instanceof RegexResult) {
                    RegexResult arg0Value_ = (RegexResult)arg0Value;
                    this.lazyProfile_ = BranchProfile.create();
                    this.getIndicesCall_ = super.insert(DispatchNodeGen.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = RegexResult.RegexResultGetEndNode.doResult(arg0Value_, arg1Value, this.lazyProfile_, this.getIndicesCall_);
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
            return NodeCost.MONOMORPHIC;
        }

        public static RegexResult.RegexResultGetEndNode create() {
            return new RegexResultGetEndNodeGen();
        }

        public static RegexResult.RegexResultGetEndNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=RegexResult.RegexResultGetEndNode.class)
        @DenyReplace
        private static final class Uncached
        extends RegexResult.RegexResultGetEndNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            int execute(Object arg0Value, int arg1Value) {
                if (arg0Value instanceof RegexResult) {
                    RegexResult arg0Value_ = (RegexResult)arg0Value;
                    return RegexResult.RegexResultGetEndNode.doResult(arg0Value_, arg1Value, BranchProfile.getUncached(), DispatchNodeGen.getUncached());
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

    @GeneratedBy(value=RegexResult.InvokeCacheNode.class)
    static final class InvokeCacheNodeGen
    extends RegexResult.InvokeCacheNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private GetStartIdentityData getStartIdentity_cache;
        @Node.Child
        private GetStartEqualsData getStartEquals_cache;
        @Node.Child
        private GetEndIdentityData getEndIdentity_cache;
        @Node.Child
        private GetEndEqualsData getEndEquals_cache;
        @Node.Child
        private RegexResult.RegexResultGetStartNode invokeGeneric_getStartNode_;
        @Node.Child
        private RegexResult.RegexResultGetEndNode invokeGeneric_getEndNode_;

        private InvokeCacheNodeGen() {
        }

        @Override
        @ExplodeLoop
        Object execute(RegexResult arg0Value, String arg1Value, int arg2Value) throws UnknownIdentifierException {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    GetStartIdentityData s0_ = this.getStartIdentity_cache;
                    while (s0_ != null) {
                        if (arg1Value == s0_.cachedSymbol_) {
                            assert (s0_.cachedSymbol_.equals("getStart"));
                            return this.getStartIdentity(arg0Value, arg1Value, arg2Value, s0_.cachedSymbol_, s0_.getStartNode_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    GetStartEqualsData s1_ = this.getStartEquals_cache;
                    while (s1_ != null) {
                        if (arg1Value.equals(s1_.cachedSymbol_)) {
                            assert (s1_.cachedSymbol_.equals("getStart"));
                            return this.getStartEquals(arg0Value, arg1Value, arg2Value, s1_.cachedSymbol_, s1_.getStartNode_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 4) != 0) {
                    GetEndIdentityData s2_ = this.getEndIdentity_cache;
                    while (s2_ != null) {
                        if (arg1Value == s2_.cachedSymbol_) {
                            assert (s2_.cachedSymbol_.equals("getEnd"));
                            return this.getEndIdentity(arg0Value, arg1Value, arg2Value, s2_.cachedSymbol_, s2_.getEndNode_);
                        }
                        s2_ = s2_.next_;
                    }
                }
                if ((state_0 & 8) != 0) {
                    GetEndEqualsData s3_ = this.getEndEquals_cache;
                    while (s3_ != null) {
                        if (arg1Value.equals(s3_.cachedSymbol_)) {
                            assert (s3_.cachedSymbol_.equals("getEnd"));
                            return this.getEndEquals(arg0Value, arg1Value, arg2Value, s3_.cachedSymbol_, s3_.getEndNode_);
                        }
                        s3_ = s3_.next_;
                    }
                }
                if ((state_0 & 0x10) != 0) {
                    return RegexResult.InvokeCacheNode.invokeGeneric(arg0Value, arg1Value, arg2Value, this.invokeGeneric_getStartNode_, this.invokeGeneric_getEndNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(RegexResult arg0Value, String arg1Value, int arg2Value) throws UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int oldState_0;
                block44: {
                    int exclude;
                    int state_0;
                    block43: {
                        Object cachedSymbol__2;
                        block42: {
                            Object cachedSymbol__1;
                            block41: {
                                Object cachedSymbol__;
                                state_0 = this.state_0_;
                                exclude = this.exclude_;
                                oldState_0 = state_0;
                                if ((exclude & 1) != 0) break block41;
                                int count0_ = 0;
                                GetStartIdentityData s0_ = this.getStartIdentity_cache;
                                if ((state_0 & 1) != 0) {
                                    while (s0_ != null) {
                                        if (arg1Value == s0_.cachedSymbol_) {
                                            assert (s0_.cachedSymbol_.equals("getStart"));
                                            break;
                                        }
                                        s0_ = s0_.next_;
                                        ++count0_;
                                    }
                                }
                                if (s0_ == null && ((String)(cachedSymbol__ = arg1Value)).equals("getStart") && count0_ < 2) {
                                    s0_ = super.insert(new GetStartIdentityData(this.getStartIdentity_cache));
                                    s0_.cachedSymbol_ = cachedSymbol__;
                                    s0_.getStartNode_ = s0_.insertAccessor(RegexResult.RegexResultGetStartNode.create());
                                    VarHandle.storeStoreFence();
                                    this.getStartIdentity_cache = s0_;
                                    this.state_0_ = state_0 |= 1;
                                }
                                if (s0_ == null) break block41;
                                lock.unlock();
                                hasLock = false;
                                cachedSymbol__ = this.getStartIdentity(arg0Value, arg1Value, arg2Value, s0_.cachedSymbol_, s0_.getStartNode_);
                                if (oldState_0 != 0) {
                                    this.checkForPolymorphicSpecialize(oldState_0);
                                }
                                return cachedSymbol__;
                            }
                            if ((exclude & 2) != 0) break block42;
                            int count1_ = 0;
                            GetStartEqualsData s1_ = this.getStartEquals_cache;
                            if ((state_0 & 2) != 0) {
                                while (s1_ != null) {
                                    if (arg1Value.equals(s1_.cachedSymbol_)) {
                                        assert (s1_.cachedSymbol_.equals("getStart"));
                                        break;
                                    }
                                    s1_ = s1_.next_;
                                    ++count1_;
                                }
                            }
                            if (s1_ == null && ((String)(cachedSymbol__1 = arg1Value)).equals("getStart") && count1_ < 2) {
                                s1_ = super.insert(new GetStartEqualsData(this.getStartEquals_cache));
                                s1_.cachedSymbol_ = cachedSymbol__1;
                                s1_.getStartNode_ = s1_.insertAccessor(RegexResult.RegexResultGetStartNode.create());
                                VarHandle.storeStoreFence();
                                this.getStartEquals_cache = s1_;
                                this.exclude_ = exclude |= 1;
                                this.getStartIdentity_cache = null;
                                state_0 &= 0xFFFFFFFE;
                                this.state_0_ = state_0 |= 2;
                            }
                            if (s1_ == null) break block42;
                            lock.unlock();
                            hasLock = false;
                            cachedSymbol__1 = this.getStartEquals(arg0Value, arg1Value, arg2Value, s1_.cachedSymbol_, s1_.getStartNode_);
                            if (oldState_0 != 0) {
                                this.checkForPolymorphicSpecialize(oldState_0);
                            }
                            return cachedSymbol__1;
                        }
                        if ((exclude & 4) != 0) break block43;
                        int count2_ = 0;
                        GetEndIdentityData s2_ = this.getEndIdentity_cache;
                        if ((state_0 & 4) != 0) {
                            while (s2_ != null) {
                                if (arg1Value == s2_.cachedSymbol_) {
                                    assert (s2_.cachedSymbol_.equals("getEnd"));
                                    break;
                                }
                                s2_ = s2_.next_;
                                ++count2_;
                            }
                        }
                        if (s2_ == null && ((String)(cachedSymbol__2 = arg1Value)).equals("getEnd") && count2_ < 2) {
                            s2_ = super.insert(new GetEndIdentityData(this.getEndIdentity_cache));
                            s2_.cachedSymbol_ = cachedSymbol__2;
                            s2_.getEndNode_ = s2_.insertAccessor(RegexResultGetEndNodeGen.create());
                            VarHandle.storeStoreFence();
                            this.getEndIdentity_cache = s2_;
                            this.state_0_ = state_0 |= 4;
                        }
                        if (s2_ == null) break block43;
                        lock.unlock();
                        hasLock = false;
                        cachedSymbol__2 = this.getEndIdentity(arg0Value, arg1Value, arg2Value, s2_.cachedSymbol_, s2_.getEndNode_);
                        if (oldState_0 != 0) {
                            this.checkForPolymorphicSpecialize(oldState_0);
                        }
                        return cachedSymbol__2;
                    }
                    try {
                        String cachedSymbol__3;
                        if ((exclude & 8) != 0) break block44;
                        int count3_ = 0;
                        GetEndEqualsData s3_ = this.getEndEquals_cache;
                        if ((state_0 & 8) != 0) {
                            while (s3_ != null) {
                                if (arg1Value.equals(s3_.cachedSymbol_)) {
                                    assert (s3_.cachedSymbol_.equals("getEnd"));
                                    break;
                                }
                                s3_ = s3_.next_;
                                ++count3_;
                            }
                        }
                        if (s3_ == null && (cachedSymbol__3 = arg1Value).equals("getEnd") && count3_ < 2) {
                            s3_ = super.insert(new GetEndEqualsData(this.getEndEquals_cache));
                            s3_.cachedSymbol_ = cachedSymbol__3;
                            s3_.getEndNode_ = s3_.insertAccessor(RegexResultGetEndNodeGen.create());
                            VarHandle.storeStoreFence();
                            this.getEndEquals_cache = s3_;
                            this.exclude_ = exclude |= 4;
                            this.getEndIdentity_cache = null;
                            state_0 &= 0xFFFFFFFB;
                            this.state_0_ = state_0 |= 8;
                        }
                        if (s3_ == null) break block44;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.getEndEquals(arg0Value, arg1Value, arg2Value, s3_.cachedSymbol_, s3_.getEndNode_);
                        if (oldState_0 != 0) {
                            this.checkForPolymorphicSpecialize(oldState_0);
                        }
                        return object;
                    }
                    catch (Throwable throwable) {
                        if (oldState_0 != 0) {
                            this.checkForPolymorphicSpecialize(oldState_0);
                        }
                        throw throwable;
                    }
                }
                this.invokeGeneric_getStartNode_ = super.insert(RegexResult.RegexResultGetStartNode.create());
                this.invokeGeneric_getEndNode_ = super.insert(RegexResultGetEndNodeGen.create());
                this.exclude_ = exclude |= 0xF;
                this.getStartIdentity_cache = null;
                this.getStartEquals_cache = null;
                this.getEndIdentity_cache = null;
                this.getEndEquals_cache = null;
                state_0 &= 0xFFFFFFF0;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Object object = RegexResult.InvokeCacheNode.invokeGeneric(arg0Value, arg1Value, arg2Value, this.invokeGeneric_getStartNode_, this.invokeGeneric_getEndNode_);
                if (oldState_0 != 0) {
                    this.checkForPolymorphicSpecialize(oldState_0);
                }
                return object;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        private void checkForPolymorphicSpecialize(int oldState_0) {
            if ((oldState_0 & 0x10) == 0 && (this.state_0_ & 0x10) != 0) {
                this.reportPolymorphicSpecialize();
            }
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0) {
                GetStartIdentityData s0_ = this.getStartIdentity_cache;
                GetStartEqualsData s1_ = this.getStartEquals_cache;
                GetEndIdentityData s2_ = this.getEndIdentity_cache;
                GetEndEqualsData s3_ = this.getEndEquals_cache;
                if (!(s0_ != null && s0_.next_ != null || s1_ != null && s1_.next_ != null || s2_ != null && s2_.next_ != null || s3_ != null && s3_.next_ != null)) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        public static RegexResult.InvokeCacheNode create() {
            return new InvokeCacheNodeGen();
        }

        public static RegexResult.InvokeCacheNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=RegexResult.InvokeCacheNode.class)
        @DenyReplace
        private static final class Uncached
        extends RegexResult.InvokeCacheNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            Object execute(RegexResult arg0Value, String arg1Value, int arg2Value) throws UnknownIdentifierException {
                return RegexResult.InvokeCacheNode.invokeGeneric(arg0Value, arg1Value, arg2Value, RegexResult.RegexResultGetStartNode.getUncached(), RegexResultGetEndNodeGen.getUncached());
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

        @GeneratedBy(value=RegexResult.InvokeCacheNode.class)
        private static final class GetEndEqualsData
        extends Node {
            @Node.Child
            GetEndEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @Node.Child
            RegexResult.RegexResultGetEndNode getEndNode_;

            GetEndEqualsData(GetEndEqualsData next_) {
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

        @GeneratedBy(value=RegexResult.InvokeCacheNode.class)
        private static final class GetEndIdentityData
        extends Node {
            @Node.Child
            GetEndIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @Node.Child
            RegexResult.RegexResultGetEndNode getEndNode_;

            GetEndIdentityData(GetEndIdentityData next_) {
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

        @GeneratedBy(value=RegexResult.InvokeCacheNode.class)
        private static final class GetStartEqualsData
        extends Node {
            @Node.Child
            GetStartEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @Node.Child
            RegexResult.RegexResultGetStartNode getStartNode_;

            GetStartEqualsData(GetStartEqualsData next_) {
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

        @GeneratedBy(value=RegexResult.InvokeCacheNode.class)
        private static final class GetStartIdentityData
        extends Node {
            @Node.Child
            GetStartIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @Node.Child
            RegexResult.RegexResultGetStartNode getStartNode_;

            GetStartIdentityData(GetStartIdentityData next_) {
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
}

