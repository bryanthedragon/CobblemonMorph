/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.regex.result;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.regex.AbstractConstantKeysObjectGen;
import com.oracle.truffle.regex.result.RegexResult;
import com.oracle.truffle.regex.result.RegexResultFactory;
import com.oracle.truffle.regex.runtime.nodes.ToIntNode;
import com.oracle.truffle.regex.runtime.nodes.ToIntNodeGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=RegexResult.class)
final class RegexResultGen {
    private RegexResultGen() {
    }

    static {
        LibraryExport.register(RegexResult.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=RegexResult.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, RegexResult.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof RegexResult);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof RegexResult);
            return new Cached(receiver);
        }

        @GeneratedBy(value=RegexResult.class)
        @DenyReplace
        private static final class Uncached
        extends AbstractConstantKeysObjectGen.InteropLibraryExports.Uncached {
            protected Uncached(Object receiver) {
                super(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                return super.accepts(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                RegexResult arg0Value = (RegexResult)arg0Value_;
                return RegexResult.IsMemberReadable.isReadable(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readMember(Object arg0Value_, String arg1Value) throws UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                RegexResult arg0Value = (RegexResult)arg0Value_;
                if (arg1Value.equals("lastGroup")) {
                    return RegexResult.ReadMember.lastGroupEquals(arg0Value, arg1Value, arg1Value);
                }
                return RegexResult.ReadMember.readGeneric(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                RegexResult arg0Value = (RegexResult)arg0Value_;
                return RegexResult.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((RegexResult)receiver).getMembers(includeInternal);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object invokeMember(Object arg0Value_, String arg1Value, Object ... arg2Value) throws UnknownIdentifierException, ArityException, UnsupportedTypeException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                RegexResult arg0Value = (RegexResult)arg0Value_;
                return arg0Value.invokeMember(arg1Value, arg2Value, ToIntNodeGen.getUncached(), RegexResultFactory.InvokeCacheNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((RegexResult)receiver).toDisplayString(allowSideEffects);
            }
        }

        @GeneratedBy(value=RegexResult.class)
        private static final class Cached
        extends AbstractConstantKeysObjectGen.InteropLibraryExports.Cached {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @CompilerDirectives.CompilationFinal
            private IsMemberReadableCacheIdentityData isMemberReadable_cacheIdentity_cache;
            @CompilerDirectives.CompilationFinal
            private IsMemberReadableCacheEqualsData isMemberReadable_cacheEquals_cache;
            @CompilerDirectives.CompilationFinal
            private ReadMemberIsMatchIdentityData readMember_isMatchIdentity_cache;
            @CompilerDirectives.CompilationFinal
            private ReadMemberIsMatchEqualsData readMember_isMatchEquals_cache;
            @CompilerDirectives.CompilationFinal
            private ReadMemberGetStartIdentityData readMember_getStartIdentity_cache;
            @CompilerDirectives.CompilationFinal
            private ReadMemberGetStartEqualsData readMember_getStartEquals_cache;
            @CompilerDirectives.CompilationFinal
            private ReadMemberGetEndIdentityData readMember_getEndIdentity_cache;
            @CompilerDirectives.CompilationFinal
            private ReadMemberGetEndEqualsData readMember_getEndEquals_cache;
            @CompilerDirectives.CompilationFinal
            private ReadMemberLastGroupIdentityData readMember_lastGroupIdentity_cache;
            @CompilerDirectives.CompilationFinal
            private ReadMemberLastGroupEqualsData readMember_lastGroupEquals_cache;
            @CompilerDirectives.CompilationFinal
            private IsMemberInvocableCacheIdentityData isMemberInvocable_cacheIdentity_cache;
            @CompilerDirectives.CompilationFinal
            private IsMemberInvocableCacheEqualsData isMemberInvocable_cacheEquals_cache;
            @Node.Child
            private ToIntNode invokeMemberNode__invokeMember_toIntNode_;
            @Node.Child
            private RegexResult.InvokeCacheNode invokeMemberNode__invokeMember_invokeCache_;

            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            @ExplodeLoop
            public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                RegexResult arg0Value = (RegexResult)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 7) != 0) {
                    if ((state_0 & 1) != 0) {
                        IsMemberReadableCacheIdentityData s0_ = this.isMemberReadable_cacheIdentity_cache;
                        while (s0_ != null) {
                            if (arg1Value == s0_.cachedSymbol_) {
                                assert (s0_.result_);
                                return RegexResult.IsMemberReadable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.result_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        IsMemberReadableCacheEqualsData s1_ = this.isMemberReadable_cacheEquals_cache;
                        while (s1_ != null) {
                            if (arg1Value.equals(s1_.cachedSymbol_)) {
                                assert (s1_.result_);
                                return RegexResult.IsMemberReadable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.result_);
                            }
                            s1_ = s1_.next_;
                        }
                    }
                    if ((state_0 & 4) != 0) {
                        return RegexResult.IsMemberReadable.isReadable(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberReadableAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberReadableAndSpecialize(RegexResult arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if ((exclude & 1) == 0) {
                        String cachedSymbol__2;
                        boolean result__;
                        int count0_ = 0;
                        IsMemberReadableCacheIdentityData s0_ = this.isMemberReadable_cacheIdentity_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null) {
                                if (arg1Value == s0_.cachedSymbol_) {
                                    assert (s0_.result_);
                                    break;
                                }
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && (result__ = RegexResult.IsMemberReadable.isReadable(arg0Value, cachedSymbol__2 = arg1Value)) && count0_ < 4) {
                            s0_ = new IsMemberReadableCacheIdentityData(this.isMemberReadable_cacheIdentity_cache);
                            s0_.cachedSymbol_ = cachedSymbol__2;
                            s0_.result_ = result__;
                            VarHandle.storeStoreFence();
                            this.isMemberReadable_cacheIdentity_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean cachedSymbol__2 = RegexResult.IsMemberReadable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.result_);
                            return cachedSymbol__2;
                        }
                    }
                    if ((exclude & 2) == 0) {
                        String cachedSymbol__1;
                        boolean result__1;
                        int count1_ = 0;
                        IsMemberReadableCacheEqualsData s1_ = this.isMemberReadable_cacheEquals_cache;
                        if ((state_0 & 2) != 0) {
                            while (s1_ != null) {
                                if (arg1Value.equals(s1_.cachedSymbol_)) {
                                    assert (s1_.result_);
                                    break;
                                }
                                s1_ = s1_.next_;
                                ++count1_;
                            }
                        }
                        if (s1_ == null && (result__1 = RegexResult.IsMemberReadable.isReadable(arg0Value, cachedSymbol__1 = arg1Value)) && count1_ < 4) {
                            s1_ = new IsMemberReadableCacheEqualsData(this.isMemberReadable_cacheEquals_cache);
                            s1_.cachedSymbol_ = cachedSymbol__1;
                            s1_.result_ = result__1;
                            VarHandle.storeStoreFence();
                            this.isMemberReadable_cacheEquals_cache = s1_;
                            this.exclude_ = exclude |= 1;
                            this.isMemberReadable_cacheIdentity_cache = null;
                            state_0 &= 0xFFFFFFFE;
                            this.state_0_ = state_0 |= 2;
                        }
                        if (s1_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean bl = RegexResult.IsMemberReadable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.result_);
                            return bl;
                        }
                    }
                    this.exclude_ = exclude |= 3;
                    this.isMemberReadable_cacheIdentity_cache = null;
                    this.isMemberReadable_cacheEquals_cache = null;
                    state_0 &= 0xFFFFFFFC;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = RegexResult.IsMemberReadable.isReadable(arg0Value, arg1Value);
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
                if ((state_0 & 7) == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & 7 & (state_0 & 7) - 1) == 0) {
                    IsMemberReadableCacheIdentityData s0_ = this.isMemberReadable_cacheIdentity_cache;
                    IsMemberReadableCacheEqualsData s1_ = this.isMemberReadable_cacheEquals_cache;
                    if (!(s0_ != null && s0_.next_ != null || s1_ != null && s1_.next_ != null)) {
                        return NodeCost.MONOMORPHIC;
                    }
                }
                return NodeCost.POLYMORPHIC;
            }

            @Override
            @ExplodeLoop
            public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                RegexResult arg0Value = (RegexResult)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0xFF8) != 0) {
                    if ((state_0 & 8) != 0) {
                        ReadMemberIsMatchIdentityData s0_ = this.readMember_isMatchIdentity_cache;
                        while (s0_ != null) {
                            if (arg1Value == s0_.cachedSymbol_) {
                                assert (s0_.cachedSymbol_.equals("isMatch"));
                                return RegexResult.ReadMember.isMatchIdentity(arg0Value, arg1Value, s0_.cachedSymbol_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 0x10) != 0) {
                        ReadMemberIsMatchEqualsData s1_ = this.readMember_isMatchEquals_cache;
                        while (s1_ != null) {
                            if (arg1Value.equals(s1_.cachedSymbol_)) {
                                assert (s1_.cachedSymbol_.equals("isMatch"));
                                return RegexResult.ReadMember.isMatchEquals(arg0Value, arg1Value, s1_.cachedSymbol_);
                            }
                            s1_ = s1_.next_;
                        }
                    }
                    if ((state_0 & 0x20) != 0) {
                        ReadMemberGetStartIdentityData s2_ = this.readMember_getStartIdentity_cache;
                        while (s2_ != null) {
                            if (arg1Value == s2_.cachedSymbol_) {
                                assert (s2_.cachedSymbol_.equals("getStart"));
                                return RegexResult.ReadMember.getStartIdentity(arg0Value, arg1Value, s2_.cachedSymbol_);
                            }
                            s2_ = s2_.next_;
                        }
                    }
                    if ((state_0 & 0x40) != 0) {
                        ReadMemberGetStartEqualsData s3_ = this.readMember_getStartEquals_cache;
                        while (s3_ != null) {
                            if (arg1Value.equals(s3_.cachedSymbol_)) {
                                assert (s3_.cachedSymbol_.equals("getStart"));
                                return RegexResult.ReadMember.getStartEquals(arg0Value, arg1Value, s3_.cachedSymbol_);
                            }
                            s3_ = s3_.next_;
                        }
                    }
                    if ((state_0 & 0x80) != 0) {
                        ReadMemberGetEndIdentityData s4_ = this.readMember_getEndIdentity_cache;
                        while (s4_ != null) {
                            if (arg1Value == s4_.cachedSymbol_) {
                                assert (s4_.cachedSymbol_.equals("getEnd"));
                                return RegexResult.ReadMember.getEndIdentity(arg0Value, arg1Value, s4_.cachedSymbol_);
                            }
                            s4_ = s4_.next_;
                        }
                    }
                    if ((state_0 & 0x100) != 0) {
                        ReadMemberGetEndEqualsData s5_ = this.readMember_getEndEquals_cache;
                        while (s5_ != null) {
                            if (arg1Value.equals(s5_.cachedSymbol_)) {
                                assert (s5_.cachedSymbol_.equals("getEnd"));
                                return RegexResult.ReadMember.getEndEquals(arg0Value, arg1Value, s5_.cachedSymbol_);
                            }
                            s5_ = s5_.next_;
                        }
                    }
                    if ((state_0 & 0x200) != 0) {
                        ReadMemberLastGroupIdentityData s6_ = this.readMember_lastGroupIdentity_cache;
                        while (s6_ != null) {
                            if (arg1Value == s6_.cachedSymbol_) {
                                assert (s6_.cachedSymbol_.equals("lastGroup"));
                                return RegexResult.ReadMember.lastGroupIdentity(arg0Value, arg1Value, s6_.cachedSymbol_);
                            }
                            s6_ = s6_.next_;
                        }
                    }
                    if ((state_0 & 0x400) != 0) {
                        ReadMemberLastGroupEqualsData s7_ = this.readMember_lastGroupEquals_cache;
                        while (s7_ != null) {
                            if (arg1Value.equals(s7_.cachedSymbol_)) {
                                assert (s7_.cachedSymbol_.equals("lastGroup"));
                                return RegexResult.ReadMember.lastGroupEquals(arg0Value, arg1Value, s7_.cachedSymbol_);
                            }
                            s7_ = s7_.next_;
                        }
                    }
                    if ((state_0 & 0x800) != 0) {
                        return RegexResult.ReadMember.readGeneric(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readMemberAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object readMemberAndSpecialize(RegexResult arg0Value, String arg1Value) throws UnknownIdentifierException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    Object object;
                    int oldState_0;
                    block80: {
                        int state_0;
                        block79: {
                            Object cachedSymbol__6;
                            int exclude;
                            block78: {
                                Object cachedSymbol__5;
                                block77: {
                                    Object cachedSymbol__4;
                                    block76: {
                                        Object cachedSymbol__3;
                                        block75: {
                                            Object cachedSymbol__2;
                                            block74: {
                                                Object cachedSymbol__1;
                                                block73: {
                                                    Object cachedSymbol__;
                                                    state_0 = this.state_0_;
                                                    exclude = this.exclude_;
                                                    oldState_0 = state_0 & 0xFF8;
                                                    if ((exclude & 4) != 0) break block73;
                                                    int count0_ = 0;
                                                    ReadMemberIsMatchIdentityData s0_ = this.readMember_isMatchIdentity_cache;
                                                    if ((state_0 & 8) != 0) {
                                                        while (s0_ != null) {
                                                            if (arg1Value == s0_.cachedSymbol_) {
                                                                assert (s0_.cachedSymbol_.equals("isMatch"));
                                                                break;
                                                            }
                                                            s0_ = s0_.next_;
                                                            ++count0_;
                                                        }
                                                    }
                                                    if (s0_ == null && ((String)(cachedSymbol__ = arg1Value)).equals("isMatch") && count0_ < 2) {
                                                        s0_ = new ReadMemberIsMatchIdentityData(this.readMember_isMatchIdentity_cache);
                                                        s0_.cachedSymbol_ = cachedSymbol__;
                                                        VarHandle.storeStoreFence();
                                                        this.readMember_isMatchIdentity_cache = s0_;
                                                        this.state_0_ = state_0 |= 8;
                                                    }
                                                    if (s0_ == null) break block73;
                                                    lock.unlock();
                                                    hasLock = false;
                                                    cachedSymbol__ = RegexResult.ReadMember.isMatchIdentity(arg0Value, arg1Value, s0_.cachedSymbol_);
                                                    if (oldState_0 != 0) {
                                                        this.readMember_checkForPolymorphicSpecialize(oldState_0);
                                                    }
                                                    return cachedSymbol__;
                                                }
                                                if ((exclude & 8) != 0) break block74;
                                                int count1_ = 0;
                                                ReadMemberIsMatchEqualsData s1_ = this.readMember_isMatchEquals_cache;
                                                if ((state_0 & 0x10) != 0) {
                                                    while (s1_ != null) {
                                                        if (arg1Value.equals(s1_.cachedSymbol_)) {
                                                            assert (s1_.cachedSymbol_.equals("isMatch"));
                                                            break;
                                                        }
                                                        s1_ = s1_.next_;
                                                        ++count1_;
                                                    }
                                                }
                                                if (s1_ == null && ((String)(cachedSymbol__1 = arg1Value)).equals("isMatch") && count1_ < 2) {
                                                    s1_ = new ReadMemberIsMatchEqualsData(this.readMember_isMatchEquals_cache);
                                                    s1_.cachedSymbol_ = cachedSymbol__1;
                                                    VarHandle.storeStoreFence();
                                                    this.readMember_isMatchEquals_cache = s1_;
                                                    this.exclude_ = exclude |= 4;
                                                    this.readMember_isMatchIdentity_cache = null;
                                                    state_0 &= 0xFFFFFFF7;
                                                    this.state_0_ = state_0 |= 0x10;
                                                }
                                                if (s1_ == null) break block74;
                                                lock.unlock();
                                                hasLock = false;
                                                cachedSymbol__1 = RegexResult.ReadMember.isMatchEquals(arg0Value, arg1Value, s1_.cachedSymbol_);
                                                if (oldState_0 != 0) {
                                                    this.readMember_checkForPolymorphicSpecialize(oldState_0);
                                                }
                                                return cachedSymbol__1;
                                            }
                                            if ((exclude & 0x10) != 0) break block75;
                                            int count2_ = 0;
                                            ReadMemberGetStartIdentityData s2_ = this.readMember_getStartIdentity_cache;
                                            if ((state_0 & 0x20) != 0) {
                                                while (s2_ != null) {
                                                    if (arg1Value == s2_.cachedSymbol_) {
                                                        assert (s2_.cachedSymbol_.equals("getStart"));
                                                        break;
                                                    }
                                                    s2_ = s2_.next_;
                                                    ++count2_;
                                                }
                                            }
                                            if (s2_ == null && ((String)(cachedSymbol__2 = arg1Value)).equals("getStart") && count2_ < 2) {
                                                s2_ = new ReadMemberGetStartIdentityData(this.readMember_getStartIdentity_cache);
                                                s2_.cachedSymbol_ = cachedSymbol__2;
                                                VarHandle.storeStoreFence();
                                                this.readMember_getStartIdentity_cache = s2_;
                                                this.state_0_ = state_0 |= 0x20;
                                            }
                                            if (s2_ == null) break block75;
                                            lock.unlock();
                                            hasLock = false;
                                            cachedSymbol__2 = RegexResult.ReadMember.getStartIdentity(arg0Value, arg1Value, s2_.cachedSymbol_);
                                            if (oldState_0 != 0) {
                                                this.readMember_checkForPolymorphicSpecialize(oldState_0);
                                            }
                                            return cachedSymbol__2;
                                        }
                                        if ((exclude & 0x20) != 0) break block76;
                                        int count3_ = 0;
                                        ReadMemberGetStartEqualsData s3_ = this.readMember_getStartEquals_cache;
                                        if ((state_0 & 0x40) != 0) {
                                            while (s3_ != null) {
                                                if (arg1Value.equals(s3_.cachedSymbol_)) {
                                                    assert (s3_.cachedSymbol_.equals("getStart"));
                                                    break;
                                                }
                                                s3_ = s3_.next_;
                                                ++count3_;
                                            }
                                        }
                                        if (s3_ == null && ((String)(cachedSymbol__3 = arg1Value)).equals("getStart") && count3_ < 2) {
                                            s3_ = new ReadMemberGetStartEqualsData(this.readMember_getStartEquals_cache);
                                            s3_.cachedSymbol_ = cachedSymbol__3;
                                            VarHandle.storeStoreFence();
                                            this.readMember_getStartEquals_cache = s3_;
                                            this.exclude_ = exclude |= 0x10;
                                            this.readMember_getStartIdentity_cache = null;
                                            state_0 &= 0xFFFFFFDF;
                                            this.state_0_ = state_0 |= 0x40;
                                        }
                                        if (s3_ == null) break block76;
                                        lock.unlock();
                                        hasLock = false;
                                        cachedSymbol__3 = RegexResult.ReadMember.getStartEquals(arg0Value, arg1Value, s3_.cachedSymbol_);
                                        if (oldState_0 != 0) {
                                            this.readMember_checkForPolymorphicSpecialize(oldState_0);
                                        }
                                        return cachedSymbol__3;
                                    }
                                    if ((exclude & 0x40) != 0) break block77;
                                    int count4_ = 0;
                                    ReadMemberGetEndIdentityData s4_ = this.readMember_getEndIdentity_cache;
                                    if ((state_0 & 0x80) != 0) {
                                        while (s4_ != null) {
                                            if (arg1Value == s4_.cachedSymbol_) {
                                                assert (s4_.cachedSymbol_.equals("getEnd"));
                                                break;
                                            }
                                            s4_ = s4_.next_;
                                            ++count4_;
                                        }
                                    }
                                    if (s4_ == null && ((String)(cachedSymbol__4 = arg1Value)).equals("getEnd") && count4_ < 2) {
                                        s4_ = new ReadMemberGetEndIdentityData(this.readMember_getEndIdentity_cache);
                                        s4_.cachedSymbol_ = cachedSymbol__4;
                                        VarHandle.storeStoreFence();
                                        this.readMember_getEndIdentity_cache = s4_;
                                        this.state_0_ = state_0 |= 0x80;
                                    }
                                    if (s4_ == null) break block77;
                                    lock.unlock();
                                    hasLock = false;
                                    cachedSymbol__4 = RegexResult.ReadMember.getEndIdentity(arg0Value, arg1Value, s4_.cachedSymbol_);
                                    if (oldState_0 != 0) {
                                        this.readMember_checkForPolymorphicSpecialize(oldState_0);
                                    }
                                    return cachedSymbol__4;
                                }
                                if ((exclude & 0x80) != 0) break block78;
                                int count5_ = 0;
                                ReadMemberGetEndEqualsData s5_ = this.readMember_getEndEquals_cache;
                                if ((state_0 & 0x100) != 0) {
                                    while (s5_ != null) {
                                        if (arg1Value.equals(s5_.cachedSymbol_)) {
                                            assert (s5_.cachedSymbol_.equals("getEnd"));
                                            break;
                                        }
                                        s5_ = s5_.next_;
                                        ++count5_;
                                    }
                                }
                                if (s5_ == null && ((String)(cachedSymbol__5 = arg1Value)).equals("getEnd") && count5_ < 2) {
                                    s5_ = new ReadMemberGetEndEqualsData(this.readMember_getEndEquals_cache);
                                    s5_.cachedSymbol_ = cachedSymbol__5;
                                    VarHandle.storeStoreFence();
                                    this.readMember_getEndEquals_cache = s5_;
                                    this.exclude_ = exclude |= 0x40;
                                    this.readMember_getEndIdentity_cache = null;
                                    state_0 &= 0xFFFFFF7F;
                                    this.state_0_ = state_0 |= 0x100;
                                }
                                if (s5_ == null) break block78;
                                lock.unlock();
                                hasLock = false;
                                cachedSymbol__5 = RegexResult.ReadMember.getEndEquals(arg0Value, arg1Value, s5_.cachedSymbol_);
                                if (oldState_0 != 0) {
                                    this.readMember_checkForPolymorphicSpecialize(oldState_0);
                                }
                                return cachedSymbol__5;
                            }
                            if ((exclude & 0x100) != 0) break block79;
                            int count6_ = 0;
                            ReadMemberLastGroupIdentityData s6_ = this.readMember_lastGroupIdentity_cache;
                            if ((state_0 & 0x200) != 0) {
                                while (s6_ != null) {
                                    if (arg1Value == s6_.cachedSymbol_) {
                                        assert (s6_.cachedSymbol_.equals("lastGroup"));
                                        break;
                                    }
                                    s6_ = s6_.next_;
                                    ++count6_;
                                }
                            }
                            if (s6_ == null && ((String)(cachedSymbol__6 = arg1Value)).equals("lastGroup") && count6_ < 2) {
                                s6_ = new ReadMemberLastGroupIdentityData(this.readMember_lastGroupIdentity_cache);
                                s6_.cachedSymbol_ = cachedSymbol__6;
                                VarHandle.storeStoreFence();
                                this.readMember_lastGroupIdentity_cache = s6_;
                                this.state_0_ = state_0 |= 0x200;
                            }
                            if (s6_ == null) break block79;
                            lock.unlock();
                            hasLock = false;
                            cachedSymbol__6 = RegexResult.ReadMember.lastGroupIdentity(arg0Value, arg1Value, s6_.cachedSymbol_);
                            if (oldState_0 != 0) {
                                this.readMember_checkForPolymorphicSpecialize(oldState_0);
                            }
                            return cachedSymbol__6;
                        }
                        try {
                            String cachedSymbol__7;
                            int count7_ = 0;
                            ReadMemberLastGroupEqualsData s7_ = this.readMember_lastGroupEquals_cache;
                            if ((state_0 & 0x400) != 0) {
                                while (s7_ != null) {
                                    if (arg1Value.equals(s7_.cachedSymbol_)) {
                                        assert (s7_.cachedSymbol_.equals("lastGroup"));
                                        break;
                                    }
                                    s7_ = s7_.next_;
                                    ++count7_;
                                }
                            }
                            if (s7_ == null && (cachedSymbol__7 = arg1Value).equals("lastGroup") && count7_ < 2) {
                                s7_ = new ReadMemberLastGroupEqualsData(this.readMember_lastGroupEquals_cache);
                                s7_.cachedSymbol_ = cachedSymbol__7;
                                VarHandle.storeStoreFence();
                                this.readMember_lastGroupEquals_cache = s7_;
                                this.exclude_ = exclude |= 0x100;
                                this.readMember_lastGroupIdentity_cache = null;
                                state_0 &= 0xFFFFFDFF;
                                this.state_0_ = state_0 |= 0x400;
                            }
                            if (s7_ == null) break block80;
                            lock.unlock();
                            hasLock = false;
                            object = RegexResult.ReadMember.lastGroupEquals(arg0Value, arg1Value, s7_.cachedSymbol_);
                            if (oldState_0 != 0) {
                                this.readMember_checkForPolymorphicSpecialize(oldState_0);
                            }
                            return object;
                        }
                        catch (Throwable throwable) {
                            if (oldState_0 != 0) {
                                this.readMember_checkForPolymorphicSpecialize(oldState_0);
                            }
                            throw throwable;
                        }
                    }
                    this.exclude_ = exclude |= 0xFC;
                    this.readMember_isMatchIdentity_cache = null;
                    this.readMember_isMatchEquals_cache = null;
                    this.readMember_getStartIdentity_cache = null;
                    this.readMember_getStartEquals_cache = null;
                    this.readMember_getEndIdentity_cache = null;
                    this.readMember_getEndEquals_cache = null;
                    state_0 &= 0xFFFFFE07;
                    this.state_0_ = state_0 |= 0x800;
                    lock.unlock();
                    hasLock = false;
                    object = RegexResult.ReadMember.readGeneric(arg0Value, arg1Value);
                    if (oldState_0 != 0) {
                        this.readMember_checkForPolymorphicSpecialize(oldState_0);
                    }
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            private void readMember_checkForPolymorphicSpecialize(int oldState_0) {
                if ((oldState_0 & 0x800) == 0 && (this.state_0_ & 0x800) != 0) {
                    this.reportPolymorphicSpecialize();
                }
            }

            @Override
            @ExplodeLoop
            public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                RegexResult arg0Value = (RegexResult)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x7000) != 0) {
                    if ((state_0 & 0x1000) != 0) {
                        IsMemberInvocableCacheIdentityData s0_ = this.isMemberInvocable_cacheIdentity_cache;
                        while (s0_ != null) {
                            if (arg1Value == s0_.cachedSymbol_) {
                                assert (s0_.result_);
                                return RegexResult.IsMemberInvocable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.result_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 0x2000) != 0) {
                        IsMemberInvocableCacheEqualsData s1_ = this.isMemberInvocable_cacheEquals_cache;
                        while (s1_ != null) {
                            if (arg1Value.equals(s1_.cachedSymbol_)) {
                                assert (s1_.result_);
                                return RegexResult.IsMemberInvocable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.result_);
                            }
                            s1_ = s1_.next_;
                        }
                    }
                    if ((state_0 & 0x4000) != 0) {
                        return RegexResult.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberInvocableAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberInvocableAndSpecialize(RegexResult arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if ((exclude & 0x200) == 0) {
                        String cachedSymbol__2;
                        boolean result__;
                        int count0_ = 0;
                        IsMemberInvocableCacheIdentityData s0_ = this.isMemberInvocable_cacheIdentity_cache;
                        if ((state_0 & 0x1000) != 0) {
                            while (s0_ != null) {
                                if (arg1Value == s0_.cachedSymbol_) {
                                    assert (s0_.result_);
                                    break;
                                }
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && (result__ = RegexResult.IsMemberInvocable.isInvocable(arg0Value, cachedSymbol__2 = arg1Value)) && count0_ < 2) {
                            s0_ = new IsMemberInvocableCacheIdentityData(this.isMemberInvocable_cacheIdentity_cache);
                            s0_.cachedSymbol_ = cachedSymbol__2;
                            s0_.result_ = result__;
                            VarHandle.storeStoreFence();
                            this.isMemberInvocable_cacheIdentity_cache = s0_;
                            this.state_0_ = state_0 |= 0x1000;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean cachedSymbol__2 = RegexResult.IsMemberInvocable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.result_);
                            return cachedSymbol__2;
                        }
                    }
                    if ((exclude & 0x400) == 0) {
                        String cachedSymbol__1;
                        boolean result__1;
                        int count1_ = 0;
                        IsMemberInvocableCacheEqualsData s1_ = this.isMemberInvocable_cacheEquals_cache;
                        if ((state_0 & 0x2000) != 0) {
                            while (s1_ != null) {
                                if (arg1Value.equals(s1_.cachedSymbol_)) {
                                    assert (s1_.result_);
                                    break;
                                }
                                s1_ = s1_.next_;
                                ++count1_;
                            }
                        }
                        if (s1_ == null && (result__1 = RegexResult.IsMemberInvocable.isInvocable(arg0Value, cachedSymbol__1 = arg1Value)) && count1_ < 2) {
                            s1_ = new IsMemberInvocableCacheEqualsData(this.isMemberInvocable_cacheEquals_cache);
                            s1_.cachedSymbol_ = cachedSymbol__1;
                            s1_.result_ = result__1;
                            VarHandle.storeStoreFence();
                            this.isMemberInvocable_cacheEquals_cache = s1_;
                            this.exclude_ = exclude |= 0x200;
                            this.isMemberInvocable_cacheIdentity_cache = null;
                            state_0 &= 0xFFFFEFFF;
                            this.state_0_ = state_0 |= 0x2000;
                        }
                        if (s1_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean bl = RegexResult.IsMemberInvocable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.result_);
                            return bl;
                        }
                    }
                    this.exclude_ = exclude |= 0x600;
                    this.isMemberInvocable_cacheIdentity_cache = null;
                    this.isMemberInvocable_cacheEquals_cache = null;
                    state_0 &= 0xFFFFCFFF;
                    this.state_0_ = state_0 |= 0x4000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = RegexResult.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((RegexResult)receiver).getMembers(includeInternal);
            }

            @Override
            public Object invokeMember(Object arg0Value_, String arg1Value, Object ... arg2Value) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                RegexResult arg0Value = (RegexResult)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x8000) != 0) {
                    return arg0Value.invokeMember(arg1Value, arg2Value, this.invokeMemberNode__invokeMember_toIntNode_, this.invokeMemberNode__invokeMember_invokeCache_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.invokeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object invokeMemberNode_AndSpecialize(RegexResult arg0Value, String arg1Value, Object[] arg2Value) throws UnknownIdentifierException, ArityException, UnsupportedTypeException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.invokeMemberNode__invokeMember_toIntNode_ = super.insert(ToIntNode.create());
                    this.invokeMemberNode__invokeMember_invokeCache_ = super.insert(RegexResultFactory.InvokeCacheNodeGen.create());
                    this.state_0_ = state_0 |= 0x8000;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.invokeMember(arg1Value, arg2Value, this.invokeMemberNode__invokeMember_toIntNode_, this.invokeMemberNode__invokeMember_invokeCache_);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((RegexResult)receiver).toDisplayString(allowSideEffects);
            }

            @GeneratedBy(value=RegexResult.class)
            private static final class IsMemberInvocableCacheEqualsData {
                @CompilerDirectives.CompilationFinal
                IsMemberInvocableCacheEqualsData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;
                @CompilerDirectives.CompilationFinal
                boolean result_;

                IsMemberInvocableCacheEqualsData(IsMemberInvocableCacheEqualsData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=RegexResult.class)
            private static final class IsMemberInvocableCacheIdentityData {
                @CompilerDirectives.CompilationFinal
                IsMemberInvocableCacheIdentityData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;
                @CompilerDirectives.CompilationFinal
                boolean result_;

                IsMemberInvocableCacheIdentityData(IsMemberInvocableCacheIdentityData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=RegexResult.class)
            private static final class ReadMemberLastGroupEqualsData {
                @CompilerDirectives.CompilationFinal
                ReadMemberLastGroupEqualsData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;

                ReadMemberLastGroupEqualsData(ReadMemberLastGroupEqualsData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=RegexResult.class)
            private static final class ReadMemberLastGroupIdentityData {
                @CompilerDirectives.CompilationFinal
                ReadMemberLastGroupIdentityData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;

                ReadMemberLastGroupIdentityData(ReadMemberLastGroupIdentityData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=RegexResult.class)
            private static final class ReadMemberGetEndEqualsData {
                @CompilerDirectives.CompilationFinal
                ReadMemberGetEndEqualsData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;

                ReadMemberGetEndEqualsData(ReadMemberGetEndEqualsData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=RegexResult.class)
            private static final class ReadMemberGetEndIdentityData {
                @CompilerDirectives.CompilationFinal
                ReadMemberGetEndIdentityData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;

                ReadMemberGetEndIdentityData(ReadMemberGetEndIdentityData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=RegexResult.class)
            private static final class ReadMemberGetStartEqualsData {
                @CompilerDirectives.CompilationFinal
                ReadMemberGetStartEqualsData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;

                ReadMemberGetStartEqualsData(ReadMemberGetStartEqualsData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=RegexResult.class)
            private static final class ReadMemberGetStartIdentityData {
                @CompilerDirectives.CompilationFinal
                ReadMemberGetStartIdentityData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;

                ReadMemberGetStartIdentityData(ReadMemberGetStartIdentityData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=RegexResult.class)
            private static final class ReadMemberIsMatchEqualsData {
                @CompilerDirectives.CompilationFinal
                ReadMemberIsMatchEqualsData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;

                ReadMemberIsMatchEqualsData(ReadMemberIsMatchEqualsData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=RegexResult.class)
            private static final class ReadMemberIsMatchIdentityData {
                @CompilerDirectives.CompilationFinal
                ReadMemberIsMatchIdentityData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;

                ReadMemberIsMatchIdentityData(ReadMemberIsMatchIdentityData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=RegexResult.class)
            private static final class IsMemberReadableCacheEqualsData {
                @CompilerDirectives.CompilationFinal
                IsMemberReadableCacheEqualsData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;
                @CompilerDirectives.CompilationFinal
                boolean result_;

                IsMemberReadableCacheEqualsData(IsMemberReadableCacheEqualsData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=RegexResult.class)
            private static final class IsMemberReadableCacheIdentityData {
                @CompilerDirectives.CompilationFinal
                IsMemberReadableCacheIdentityData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;
                @CompilerDirectives.CompilationFinal
                boolean result_;

                IsMemberReadableCacheIdentityData(IsMemberReadableCacheIdentityData next_) {
                    this.next_ = next_;
                }
            }
        }
    }
}

