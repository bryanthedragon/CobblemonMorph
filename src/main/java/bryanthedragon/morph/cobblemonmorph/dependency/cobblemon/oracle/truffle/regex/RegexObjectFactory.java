/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.regex;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.regex.RegexObject;
import com.oracle.truffle.regex.runtime.nodes.ExpectByteArrayHostObjectNode;
import com.oracle.truffle.regex.runtime.nodes.ExpectByteArrayHostObjectNodeGen;
import com.oracle.truffle.regex.runtime.nodes.ExpectStringOrTruffleObjectNode;
import com.oracle.truffle.regex.runtime.nodes.ExpectStringOrTruffleObjectNodeGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=RegexObject.class)
public final class RegexObjectFactory {

    @GeneratedBy(value=RegexObject.ExecCompiledRegexNode.class)
    static final class ExecCompiledRegexNodeGen
    extends RegexObject.ExecCompiledRegexNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private ExecuteDirectCallData executeDirectCall_cache;
        @Node.Child
        private IndirectCallNode executeIndirectCall_indirectCallNode_;

        private ExecCompiledRegexNodeGen() {
        }

        @Override
        @ExplodeLoop
        Object execute(CallTarget arg0Value, Object arg1Value, int arg2Value) throws UnsupportedMessageException, ArityException, UnsupportedTypeException {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    ExecuteDirectCallData s0_ = this.executeDirectCall_cache;
                    while (s0_ != null) {
                        if (arg0Value == s0_.cachedCallTarget_) {
                            return RegexObject.ExecCompiledRegexNode.executeDirectCall(arg0Value, arg1Value, arg2Value, s0_.cachedCallTarget_, s0_.directCallNode_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return RegexObject.ExecCompiledRegexNode.executeIndirectCall(arg0Value, arg1Value, arg2Value, this.executeIndirectCall_indirectCallNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(CallTarget arg0Value, Object arg1Value, int arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int oldState_0;
                block15: {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    oldState_0 = state_0;
                    try {
                        if (exclude != 0) break block15;
                        int count0_ = 0;
                        ExecuteDirectCallData s0_ = this.executeDirectCall_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && arg0Value != s0_.cachedCallTarget_) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 4) {
                            s0_ = super.insert(new ExecuteDirectCallData(this.executeDirectCall_cache));
                            s0_.cachedCallTarget_ = arg0Value;
                            s0_.directCallNode_ = s0_.insertAccessor(DirectCallNode.create(s0_.cachedCallTarget_));
                            VarHandle.storeStoreFence();
                            this.executeDirectCall_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ == null) break block15;
                        lock.unlock();
                        hasLock = false;
                        Object object = RegexObject.ExecCompiledRegexNode.executeDirectCall(arg0Value, arg1Value, arg2Value, s0_.cachedCallTarget_, s0_.directCallNode_);
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
                this.executeIndirectCall_indirectCallNode_ = super.insert(IndirectCallNode.create());
                this.exclude_ = exclude |= 1;
                this.executeDirectCall_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = RegexObject.ExecCompiledRegexNode.executeIndirectCall(arg0Value, arg1Value, arg2Value, this.executeIndirectCall_indirectCallNode_);
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
            if ((oldState_0 & 2) == 0 && (this.state_0_ & 2) != 0) {
                this.reportPolymorphicSpecialize();
            }
        }

        @Override
        public NodeCost getCost() {
            ExecuteDirectCallData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.executeDirectCall_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static RegexObject.ExecCompiledRegexNode create() {
            return new ExecCompiledRegexNodeGen();
        }

        public static RegexObject.ExecCompiledRegexNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=RegexObject.ExecCompiledRegexNode.class)
        @DenyReplace
        private static final class Uncached
        extends RegexObject.ExecCompiledRegexNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            Object execute(CallTarget arg0Value, Object arg1Value, int arg2Value) throws UnsupportedMessageException, ArityException, UnsupportedTypeException {
                return RegexObject.ExecCompiledRegexNode.executeIndirectCall(arg0Value, arg1Value, arg2Value, IndirectCallNode.getUncached());
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

        @GeneratedBy(value=RegexObject.ExecCompiledRegexNode.class)
        private static final class ExecuteDirectCallData
        extends Node {
            @Node.Child
            ExecuteDirectCallData next_;
            @CompilerDirectives.CompilationFinal
            CallTarget cachedCallTarget_;
            @Node.Child
            DirectCallNode directCallNode_;

            ExecuteDirectCallData(ExecuteDirectCallData next_) {
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

    @GeneratedBy(value=RegexObject.InvokeCacheNode.class)
    static final class InvokeCacheNodeGen
    extends RegexObject.InvokeCacheNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private ExecIdentityData execIdentity_cache;
        @Node.Child
        private ExecEqualsData execEquals_cache;
        @Node.Child
        private ExecBooleanIdentityData execBooleanIdentity_cache;
        @Node.Child
        private ExecBooleanEqualsData execBooleanEquals_cache;
        @Node.Child
        private ExecBytesIdentityData execBytesIdentity_cache;
        @Node.Child
        private ExecBytesEqualsData execBytesEquals_cache;
        @Node.Child
        private InvokeGenericData invokeGeneric_cache;

        private InvokeCacheNodeGen() {
        }

        @Override
        @ExplodeLoop
        Object execute(String arg0Value, RegexObject arg1Value, Object arg2Value, int arg3Value) throws UnsupportedMessageException, ArityException, UnsupportedTypeException, UnknownIdentifierException {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                InvokeGenericData s6_;
                if ((state_0 & 1) != 0) {
                    ExecIdentityData s0_ = this.execIdentity_cache;
                    while (s0_ != null) {
                        if (arg0Value == s0_.cachedSymbol_) {
                            assert (s0_.cachedSymbol_.equals("exec"));
                            return this.execIdentity(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedSymbol_, s0_.expectStringOrTruffleObjectNode_, s0_.execNode_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    ExecEqualsData s1_ = this.execEquals_cache;
                    while (s1_ != null) {
                        if (arg0Value.equals(s1_.cachedSymbol_)) {
                            assert (s1_.cachedSymbol_.equals("exec"));
                            return this.execEquals(arg0Value, arg1Value, arg2Value, arg3Value, s1_.cachedSymbol_, s1_.expectStringOrTruffleObjectNode_, s1_.execNode_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 4) != 0) {
                    ExecBooleanIdentityData s2_ = this.execBooleanIdentity_cache;
                    while (s2_ != null) {
                        if (arg0Value == s2_.cachedSymbol_) {
                            assert (s2_.cachedSymbol_.equals("execBoolean"));
                            return this.execBooleanIdentity(arg0Value, arg1Value, arg2Value, arg3Value, s2_.cachedSymbol_, s2_.expectStringOrTruffleObjectNode_, s2_.execNode_);
                        }
                        s2_ = s2_.next_;
                    }
                }
                if ((state_0 & 8) != 0) {
                    ExecBooleanEqualsData s3_ = this.execBooleanEquals_cache;
                    while (s3_ != null) {
                        if (arg0Value.equals(s3_.cachedSymbol_)) {
                            assert (s3_.cachedSymbol_.equals("execBoolean"));
                            return this.execBooleanEquals(arg0Value, arg1Value, arg2Value, arg3Value, s3_.cachedSymbol_, s3_.expectStringOrTruffleObjectNode_, s3_.execNode_);
                        }
                        s3_ = s3_.next_;
                    }
                }
                if ((state_0 & 0x10) != 0) {
                    ExecBytesIdentityData s4_ = this.execBytesIdentity_cache;
                    while (s4_ != null) {
                        if (arg0Value == s4_.cachedSymbol_) {
                            assert (s4_.cachedSymbol_.equals("execBytes"));
                            return this.execBytesIdentity(arg0Value, arg1Value, arg2Value, arg3Value, s4_.cachedSymbol_, s4_.expectByteArrayHostObjectNode_, s4_.execNode_);
                        }
                        s4_ = s4_.next_;
                    }
                }
                if ((state_0 & 0x20) != 0) {
                    ExecBytesEqualsData s5_ = this.execBytesEquals_cache;
                    while (s5_ != null) {
                        if (arg0Value.equals(s5_.cachedSymbol_)) {
                            assert (s5_.cachedSymbol_.equals("execBytes"));
                            return this.execBytesEquals(arg0Value, arg1Value, arg2Value, arg3Value, s5_.cachedSymbol_, s5_.expectByteArrayHostObjectNode_, s5_.execNode_);
                        }
                        s5_ = s5_.next_;
                    }
                }
                if ((state_0 & 0x40) != 0 && (s6_ = this.invokeGeneric_cache) != null) {
                    return RegexObject.InvokeCacheNode.invokeGeneric(arg0Value, arg1Value, arg2Value, arg3Value, s6_.expectStringOrTruffleObjectNode_, s6_.expectByteArrayHostObjectNode_, s6_.execNode_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(String arg0Value, RegexObject arg1Value, Object arg2Value, int arg3Value) throws UnsupportedMessageException, ArityException, UnsupportedTypeException, UnknownIdentifierException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int oldState_0;
                block62: {
                    int exclude;
                    int state_0;
                    block61: {
                        Object cachedSymbol__4;
                        block60: {
                            Object cachedSymbol__3;
                            block59: {
                                Object cachedSymbol__2;
                                block58: {
                                    Object cachedSymbol__1;
                                    block57: {
                                        Object cachedSymbol__;
                                        state_0 = this.state_0_;
                                        exclude = this.exclude_;
                                        oldState_0 = state_0;
                                        if ((exclude & 1) != 0) break block57;
                                        int count0_ = 0;
                                        ExecIdentityData s0_ = this.execIdentity_cache;
                                        if ((state_0 & 1) != 0) {
                                            while (s0_ != null) {
                                                if (arg0Value == s0_.cachedSymbol_) {
                                                    assert (s0_.cachedSymbol_.equals("exec"));
                                                    break;
                                                }
                                                s0_ = s0_.next_;
                                                ++count0_;
                                            }
                                        }
                                        if (s0_ == null && ((String)(cachedSymbol__ = arg0Value)).equals("exec") && count0_ < 3) {
                                            s0_ = super.insert(new ExecIdentityData(this.execIdentity_cache));
                                            s0_.cachedSymbol_ = cachedSymbol__;
                                            s0_.expectStringOrTruffleObjectNode_ = s0_.insertAccessor(ExpectStringOrTruffleObjectNode.create());
                                            s0_.execNode_ = s0_.insertAccessor(ExecCompiledRegexNodeGen.create());
                                            VarHandle.storeStoreFence();
                                            this.execIdentity_cache = s0_;
                                            this.state_0_ = state_0 |= 1;
                                        }
                                        if (s0_ == null) break block57;
                                        lock.unlock();
                                        hasLock = false;
                                        cachedSymbol__ = this.execIdentity(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedSymbol_, s0_.expectStringOrTruffleObjectNode_, s0_.execNode_);
                                        if (oldState_0 != 0) {
                                            this.checkForPolymorphicSpecialize(oldState_0);
                                        }
                                        return cachedSymbol__;
                                    }
                                    if ((exclude & 2) != 0) break block58;
                                    int count1_ = 0;
                                    ExecEqualsData s1_ = this.execEquals_cache;
                                    if ((state_0 & 2) != 0) {
                                        while (s1_ != null) {
                                            if (arg0Value.equals(s1_.cachedSymbol_)) {
                                                assert (s1_.cachedSymbol_.equals("exec"));
                                                break;
                                            }
                                            s1_ = s1_.next_;
                                            ++count1_;
                                        }
                                    }
                                    if (s1_ == null && ((String)(cachedSymbol__1 = arg0Value)).equals("exec") && count1_ < 3) {
                                        s1_ = super.insert(new ExecEqualsData(this.execEquals_cache));
                                        s1_.cachedSymbol_ = cachedSymbol__1;
                                        s1_.expectStringOrTruffleObjectNode_ = s1_.insertAccessor(ExpectStringOrTruffleObjectNode.create());
                                        s1_.execNode_ = s1_.insertAccessor(ExecCompiledRegexNodeGen.create());
                                        VarHandle.storeStoreFence();
                                        this.execEquals_cache = s1_;
                                        this.exclude_ = exclude |= 1;
                                        this.execIdentity_cache = null;
                                        state_0 &= 0xFFFFFFFE;
                                        this.state_0_ = state_0 |= 2;
                                    }
                                    if (s1_ == null) break block58;
                                    lock.unlock();
                                    hasLock = false;
                                    cachedSymbol__1 = this.execEquals(arg0Value, arg1Value, arg2Value, arg3Value, s1_.cachedSymbol_, s1_.expectStringOrTruffleObjectNode_, s1_.execNode_);
                                    if (oldState_0 != 0) {
                                        this.checkForPolymorphicSpecialize(oldState_0);
                                    }
                                    return cachedSymbol__1;
                                }
                                if ((exclude & 4) != 0) break block59;
                                int count2_ = 0;
                                ExecBooleanIdentityData s2_ = this.execBooleanIdentity_cache;
                                if ((state_0 & 4) != 0) {
                                    while (s2_ != null) {
                                        if (arg0Value == s2_.cachedSymbol_) {
                                            assert (s2_.cachedSymbol_.equals("execBoolean"));
                                            break;
                                        }
                                        s2_ = s2_.next_;
                                        ++count2_;
                                    }
                                }
                                if (s2_ == null && ((String)(cachedSymbol__2 = arg0Value)).equals("execBoolean") && count2_ < 3) {
                                    s2_ = super.insert(new ExecBooleanIdentityData(this.execBooleanIdentity_cache));
                                    s2_.cachedSymbol_ = cachedSymbol__2;
                                    s2_.expectStringOrTruffleObjectNode_ = s2_.insertAccessor(ExpectStringOrTruffleObjectNode.create());
                                    s2_.execNode_ = s2_.insertAccessor(ExecCompiledRegexNodeGen.create());
                                    VarHandle.storeStoreFence();
                                    this.execBooleanIdentity_cache = s2_;
                                    this.state_0_ = state_0 |= 4;
                                }
                                if (s2_ == null) break block59;
                                lock.unlock();
                                hasLock = false;
                                cachedSymbol__2 = this.execBooleanIdentity(arg0Value, arg1Value, arg2Value, arg3Value, s2_.cachedSymbol_, s2_.expectStringOrTruffleObjectNode_, s2_.execNode_);
                                if (oldState_0 != 0) {
                                    this.checkForPolymorphicSpecialize(oldState_0);
                                }
                                return cachedSymbol__2;
                            }
                            if ((exclude & 8) != 0) break block60;
                            int count3_ = 0;
                            ExecBooleanEqualsData s3_ = this.execBooleanEquals_cache;
                            if ((state_0 & 8) != 0) {
                                while (s3_ != null) {
                                    if (arg0Value.equals(s3_.cachedSymbol_)) {
                                        assert (s3_.cachedSymbol_.equals("execBoolean"));
                                        break;
                                    }
                                    s3_ = s3_.next_;
                                    ++count3_;
                                }
                            }
                            if (s3_ == null && ((String)(cachedSymbol__3 = arg0Value)).equals("execBoolean") && count3_ < 3) {
                                s3_ = super.insert(new ExecBooleanEqualsData(this.execBooleanEquals_cache));
                                s3_.cachedSymbol_ = cachedSymbol__3;
                                s3_.expectStringOrTruffleObjectNode_ = s3_.insertAccessor(ExpectStringOrTruffleObjectNode.create());
                                s3_.execNode_ = s3_.insertAccessor(ExecCompiledRegexNodeGen.create());
                                VarHandle.storeStoreFence();
                                this.execBooleanEquals_cache = s3_;
                                this.exclude_ = exclude |= 4;
                                this.execBooleanIdentity_cache = null;
                                state_0 &= 0xFFFFFFFB;
                                this.state_0_ = state_0 |= 8;
                            }
                            if (s3_ == null) break block60;
                            lock.unlock();
                            hasLock = false;
                            cachedSymbol__3 = this.execBooleanEquals(arg0Value, arg1Value, arg2Value, arg3Value, s3_.cachedSymbol_, s3_.expectStringOrTruffleObjectNode_, s3_.execNode_);
                            if (oldState_0 != 0) {
                                this.checkForPolymorphicSpecialize(oldState_0);
                            }
                            return cachedSymbol__3;
                        }
                        if ((exclude & 0x10) != 0) break block61;
                        int count4_ = 0;
                        ExecBytesIdentityData s4_ = this.execBytesIdentity_cache;
                        if ((state_0 & 0x10) != 0) {
                            while (s4_ != null) {
                                if (arg0Value == s4_.cachedSymbol_) {
                                    assert (s4_.cachedSymbol_.equals("execBytes"));
                                    break;
                                }
                                s4_ = s4_.next_;
                                ++count4_;
                            }
                        }
                        if (s4_ == null && ((String)(cachedSymbol__4 = arg0Value)).equals("execBytes") && count4_ < 3) {
                            s4_ = super.insert(new ExecBytesIdentityData(this.execBytesIdentity_cache));
                            s4_.cachedSymbol_ = cachedSymbol__4;
                            s4_.expectByteArrayHostObjectNode_ = s4_.insertAccessor(ExpectByteArrayHostObjectNodeGen.create());
                            s4_.execNode_ = s4_.insertAccessor(ExecCompiledRegexNodeGen.create());
                            VarHandle.storeStoreFence();
                            this.execBytesIdentity_cache = s4_;
                            this.state_0_ = state_0 |= 0x10;
                        }
                        if (s4_ == null) break block61;
                        lock.unlock();
                        hasLock = false;
                        cachedSymbol__4 = this.execBytesIdentity(arg0Value, arg1Value, arg2Value, arg3Value, s4_.cachedSymbol_, s4_.expectByteArrayHostObjectNode_, s4_.execNode_);
                        if (oldState_0 != 0) {
                            this.checkForPolymorphicSpecialize(oldState_0);
                        }
                        return cachedSymbol__4;
                    }
                    try {
                        String cachedSymbol__5;
                        if ((exclude & 0x20) != 0) break block62;
                        int count5_ = 0;
                        ExecBytesEqualsData s5_ = this.execBytesEquals_cache;
                        if ((state_0 & 0x20) != 0) {
                            while (s5_ != null) {
                                if (arg0Value.equals(s5_.cachedSymbol_)) {
                                    assert (s5_.cachedSymbol_.equals("execBytes"));
                                    break;
                                }
                                s5_ = s5_.next_;
                                ++count5_;
                            }
                        }
                        if (s5_ == null && (cachedSymbol__5 = arg0Value).equals("execBytes") && count5_ < 3) {
                            s5_ = super.insert(new ExecBytesEqualsData(this.execBytesEquals_cache));
                            s5_.cachedSymbol_ = cachedSymbol__5;
                            s5_.expectByteArrayHostObjectNode_ = s5_.insertAccessor(ExpectByteArrayHostObjectNodeGen.create());
                            s5_.execNode_ = s5_.insertAccessor(ExecCompiledRegexNodeGen.create());
                            VarHandle.storeStoreFence();
                            this.execBytesEquals_cache = s5_;
                            this.exclude_ = exclude |= 0x10;
                            this.execBytesIdentity_cache = null;
                            state_0 &= 0xFFFFFFEF;
                            this.state_0_ = state_0 |= 0x20;
                        }
                        if (s5_ == null) break block62;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.execBytesEquals(arg0Value, arg1Value, arg2Value, arg3Value, s5_.cachedSymbol_, s5_.expectByteArrayHostObjectNode_, s5_.execNode_);
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
                InvokeGenericData s6_ = super.insert(new InvokeGenericData());
                s6_.expectStringOrTruffleObjectNode_ = s6_.insertAccessor(ExpectStringOrTruffleObjectNode.create());
                s6_.expectByteArrayHostObjectNode_ = s6_.insertAccessor(ExpectByteArrayHostObjectNodeGen.create());
                s6_.execNode_ = s6_.insertAccessor(ExecCompiledRegexNodeGen.create());
                VarHandle.storeStoreFence();
                this.invokeGeneric_cache = s6_;
                this.exclude_ = exclude |= 0x3F;
                this.execIdentity_cache = null;
                this.execEquals_cache = null;
                this.execBooleanIdentity_cache = null;
                this.execBooleanEquals_cache = null;
                this.execBytesIdentity_cache = null;
                this.execBytesEquals_cache = null;
                state_0 &= 0xFFFFFFC0;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                Object object = RegexObject.InvokeCacheNode.invokeGeneric(arg0Value, arg1Value, arg2Value, arg3Value, s6_.expectStringOrTruffleObjectNode_, s6_.expectByteArrayHostObjectNode_, s6_.execNode_);
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
            if ((oldState_0 & 0x40) == 0 && (this.state_0_ & 0x40) != 0) {
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
                ExecIdentityData s0_ = this.execIdentity_cache;
                ExecEqualsData s1_ = this.execEquals_cache;
                ExecBooleanIdentityData s2_ = this.execBooleanIdentity_cache;
                ExecBooleanEqualsData s3_ = this.execBooleanEquals_cache;
                ExecBytesIdentityData s4_ = this.execBytesIdentity_cache;
                ExecBytesEqualsData s5_ = this.execBytesEquals_cache;
                if (!(s0_ != null && s0_.next_ != null || s1_ != null && s1_.next_ != null || s2_ != null && s2_.next_ != null || s3_ != null && s3_.next_ != null || s4_ != null && s4_.next_ != null || s5_ != null && s5_.next_ != null)) {
                    return NodeCost.MONOMORPHIC;
                }
            }
            return NodeCost.POLYMORPHIC;
        }

        public static RegexObject.InvokeCacheNode create() {
            return new InvokeCacheNodeGen();
        }

        public static RegexObject.InvokeCacheNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=RegexObject.InvokeCacheNode.class)
        @DenyReplace
        private static final class Uncached
        extends RegexObject.InvokeCacheNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            Object execute(String arg0Value, RegexObject arg1Value, Object arg2Value, int arg3Value) throws UnsupportedMessageException, ArityException, UnsupportedTypeException, UnknownIdentifierException {
                return RegexObject.InvokeCacheNode.invokeGeneric(arg0Value, arg1Value, arg2Value, arg3Value, ExpectStringOrTruffleObjectNodeGen.getUncached(), ExpectByteArrayHostObjectNodeGen.getUncached(), ExecCompiledRegexNodeGen.getUncached());
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

        @GeneratedBy(value=RegexObject.InvokeCacheNode.class)
        private static final class InvokeGenericData
        extends Node {
            @Node.Child
            ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode_;
            @Node.Child
            ExpectByteArrayHostObjectNode expectByteArrayHostObjectNode_;
            @Node.Child
            RegexObject.ExecCompiledRegexNode execNode_;

            InvokeGenericData() {
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }

        @GeneratedBy(value=RegexObject.InvokeCacheNode.class)
        private static final class ExecBytesEqualsData
        extends Node {
            @Node.Child
            ExecBytesEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @Node.Child
            ExpectByteArrayHostObjectNode expectByteArrayHostObjectNode_;
            @Node.Child
            RegexObject.ExecCompiledRegexNode execNode_;

            ExecBytesEqualsData(ExecBytesEqualsData next_) {
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

        @GeneratedBy(value=RegexObject.InvokeCacheNode.class)
        private static final class ExecBytesIdentityData
        extends Node {
            @Node.Child
            ExecBytesIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @Node.Child
            ExpectByteArrayHostObjectNode expectByteArrayHostObjectNode_;
            @Node.Child
            RegexObject.ExecCompiledRegexNode execNode_;

            ExecBytesIdentityData(ExecBytesIdentityData next_) {
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

        @GeneratedBy(value=RegexObject.InvokeCacheNode.class)
        private static final class ExecBooleanEqualsData
        extends Node {
            @Node.Child
            ExecBooleanEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @Node.Child
            ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode_;
            @Node.Child
            RegexObject.ExecCompiledRegexNode execNode_;

            ExecBooleanEqualsData(ExecBooleanEqualsData next_) {
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

        @GeneratedBy(value=RegexObject.InvokeCacheNode.class)
        private static final class ExecBooleanIdentityData
        extends Node {
            @Node.Child
            ExecBooleanIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @Node.Child
            ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode_;
            @Node.Child
            RegexObject.ExecCompiledRegexNode execNode_;

            ExecBooleanIdentityData(ExecBooleanIdentityData next_) {
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

        @GeneratedBy(value=RegexObject.InvokeCacheNode.class)
        private static final class ExecEqualsData
        extends Node {
            @Node.Child
            ExecEqualsData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @Node.Child
            ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode_;
            @Node.Child
            RegexObject.ExecCompiledRegexNode execNode_;

            ExecEqualsData(ExecEqualsData next_) {
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

        @GeneratedBy(value=RegexObject.InvokeCacheNode.class)
        private static final class ExecIdentityData
        extends Node {
            @Node.Child
            ExecIdentityData next_;
            @CompilerDirectives.CompilationFinal
            String cachedSymbol_;
            @Node.Child
            ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode_;
            @Node.Child
            RegexObject.ExecCompiledRegexNode execNode_;

            ExecIdentityData(ExecIdentityData next_) {
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

