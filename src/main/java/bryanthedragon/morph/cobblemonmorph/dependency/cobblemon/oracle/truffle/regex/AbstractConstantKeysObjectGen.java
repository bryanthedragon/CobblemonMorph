/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.regex.AbstractConstantKeysObject;
import com.oracle.truffle.regex.AbstractRegexObjectGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=AbstractConstantKeysObject.class)
public final class AbstractConstantKeysObjectGen {
    private AbstractConstantKeysObjectGen() {
    }

    static {
        LibraryExport.register(AbstractConstantKeysObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=AbstractConstantKeysObject.class)
    public static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, AbstractConstantKeysObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof AbstractConstantKeysObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof AbstractConstantKeysObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=AbstractConstantKeysObject.class)
        public static class Uncached
        extends AbstractRegexObjectGen.InteropLibraryExports.Uncached {
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
                AbstractConstantKeysObject arg0Value = (AbstractConstantKeysObject)arg0Value_;
                return AbstractConstantKeysObject.IsMemberReadable.isReadable(arg0Value, arg1Value, ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readMember(Object arg0Value_, String arg1Value) throws UnknownIdentifierException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                AbstractConstantKeysObject arg0Value = (AbstractConstantKeysObject)arg0Value_;
                return AbstractConstantKeysObject.ReadMember.read(arg0Value, arg1Value, ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((AbstractConstantKeysObject)receiver).hasMembers();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((AbstractConstantKeysObject)receiver).getMembers(includeInternal);
            }
        }

        @GeneratedBy(value=AbstractConstantKeysObject.class)
        public static class Cached
        extends AbstractRegexObjectGen.InteropLibraryExports.Cached {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @CompilerDirectives.CompilationFinal
            private ValueProfile classProfile;
            @CompilerDirectives.CompilationFinal
            private IsMemberReadableCacheIdentityData isMemberReadable_cacheIdentity_cache;
            @CompilerDirectives.CompilationFinal
            private IsMemberReadableCacheEqualsData isMemberReadable_cacheEquals_cache;
            @CompilerDirectives.CompilationFinal
            private ReadMemberReadIdentityData readMember_readIdentity_cache;
            @CompilerDirectives.CompilationFinal
            private ReadMemberReadEqualsData readMember_readEquals_cache;

            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            @ExplodeLoop
            public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                AbstractConstantKeysObject arg0Value = (AbstractConstantKeysObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 7) != 0) {
                    if ((state_0 & 1) != 0) {
                        IsMemberReadableCacheIdentityData s0_ = this.isMemberReadable_cacheIdentity_cache;
                        while (s0_ != null) {
                            if (arg1Value == s0_.cachedSymbol_ && CompilerDirectives.isExact(arg0Value, s0_.cachedClass_)) {
                                assert (s0_.result_);
                                return AbstractConstantKeysObject.IsMemberReadable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.cachedClass_, s0_.result_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        IsMemberReadableCacheEqualsData s1_ = this.isMemberReadable_cacheEquals_cache;
                        while (s1_ != null) {
                            if (arg1Value.equals(s1_.cachedSymbol_) && CompilerDirectives.isExact(arg0Value, s1_.cachedClass_)) {
                                assert (s1_.result_);
                                return AbstractConstantKeysObject.IsMemberReadable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.cachedClass_, s1_.result_);
                            }
                            s1_ = s1_.next_;
                        }
                    }
                    if ((state_0 & 4) != 0) {
                        return AbstractConstantKeysObject.IsMemberReadable.isReadable(arg0Value, arg1Value, this.classProfile);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberReadableAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberReadableAndSpecialize(AbstractConstantKeysObject arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if ((exclude & 1) == 0) {
                        String cachedSymbol__;
                        boolean result__;
                        Class<?> cachedClass__2;
                        int count0_ = 0;
                        IsMemberReadableCacheIdentityData s0_ = this.isMemberReadable_cacheIdentity_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null) {
                                if (arg1Value == s0_.cachedSymbol_ && CompilerDirectives.isExact(arg0Value, s0_.cachedClass_)) {
                                    assert (s0_.result_);
                                    break;
                                }
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && CompilerDirectives.isExact(arg0Value, cachedClass__2 = arg0Value.getClass()) && (result__ = arg0Value.isMemberReadableImpl(cachedSymbol__ = arg1Value)) && count0_ < 8) {
                            s0_ = new IsMemberReadableCacheIdentityData(this.isMemberReadable_cacheIdentity_cache);
                            s0_.cachedSymbol_ = cachedSymbol__;
                            s0_.cachedClass_ = cachedClass__2;
                            s0_.result_ = result__;
                            VarHandle.storeStoreFence();
                            this.isMemberReadable_cacheIdentity_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean cachedClass__2 = AbstractConstantKeysObject.IsMemberReadable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.cachedClass_, s0_.result_);
                            return cachedClass__2;
                        }
                    }
                    if ((exclude & 2) == 0) {
                        String cachedSymbol__1;
                        boolean result__1;
                        Class<?> cachedClass__1;
                        int count1_ = 0;
                        IsMemberReadableCacheEqualsData s1_ = this.isMemberReadable_cacheEquals_cache;
                        if ((state_0 & 2) != 0) {
                            while (s1_ != null) {
                                if (arg1Value.equals(s1_.cachedSymbol_) && CompilerDirectives.isExact(arg0Value, s1_.cachedClass_)) {
                                    assert (s1_.result_);
                                    break;
                                }
                                s1_ = s1_.next_;
                                ++count1_;
                            }
                        }
                        if (s1_ == null && CompilerDirectives.isExact(arg0Value, cachedClass__1 = arg0Value.getClass()) && (result__1 = arg0Value.isMemberReadableImpl(cachedSymbol__1 = arg1Value)) && count1_ < 8) {
                            s1_ = new IsMemberReadableCacheEqualsData(this.isMemberReadable_cacheEquals_cache);
                            s1_.cachedSymbol_ = cachedSymbol__1;
                            s1_.cachedClass_ = cachedClass__1;
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
                            boolean bl = AbstractConstantKeysObject.IsMemberReadable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.cachedClass_, s1_.result_);
                            return bl;
                        }
                    }
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.exclude_ = exclude |= 3;
                    this.isMemberReadable_cacheIdentity_cache = null;
                    this.isMemberReadable_cacheEquals_cache = null;
                    state_0 &= 0xFFFFFFFC;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = AbstractConstantKeysObject.IsMemberReadable.isReadable(arg0Value, arg1Value, this.classProfile);
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
                AbstractConstantKeysObject arg0Value = (AbstractConstantKeysObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x38) != 0) {
                    if ((state_0 & 8) != 0) {
                        ReadMemberReadIdentityData s0_ = this.readMember_readIdentity_cache;
                        while (s0_ != null) {
                            if (arg1Value == s0_.cachedSymbol_) {
                                return AbstractConstantKeysObject.ReadMember.readIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.classProfile_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 0x10) != 0) {
                        ReadMemberReadEqualsData s1_ = this.readMember_readEquals_cache;
                        while (s1_ != null) {
                            if (arg1Value.equals(s1_.cachedSymbol_)) {
                                return AbstractConstantKeysObject.ReadMember.readEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.classProfile_);
                            }
                            s1_ = s1_.next_;
                        }
                    }
                    if ((state_0 & 0x20) != 0) {
                        return AbstractConstantKeysObject.ReadMember.read(arg0Value, arg1Value, this.classProfile);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readMemberAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object readMemberAndSpecialize(AbstractConstantKeysObject arg0Value, String arg1Value) throws UnknownIdentifierException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if ((exclude & 4) == 0) {
                        int count0_ = 0;
                        ReadMemberReadIdentityData s0_ = this.readMember_readIdentity_cache;
                        if ((state_0 & 8) != 0) {
                            while (s0_ != null && arg1Value != s0_.cachedSymbol_) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && count0_ < 8) {
                            s0_ = new ReadMemberReadIdentityData(this.readMember_readIdentity_cache);
                            s0_.cachedSymbol_ = arg1Value;
                            s0_.classProfile_ = ValueProfile.createClassProfile();
                            VarHandle.storeStoreFence();
                            this.readMember_readIdentity_cache = s0_;
                            this.state_0_ = state_0 |= 8;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object = AbstractConstantKeysObject.ReadMember.readIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.classProfile_);
                            return object;
                        }
                    }
                    if ((exclude & 8) == 0) {
                        int count1_ = 0;
                        ReadMemberReadEqualsData s1_ = this.readMember_readEquals_cache;
                        if ((state_0 & 0x10) != 0) {
                            while (s1_ != null && !arg1Value.equals(s1_.cachedSymbol_)) {
                                s1_ = s1_.next_;
                                ++count1_;
                            }
                        }
                        if (s1_ == null && count1_ < 8) {
                            s1_ = new ReadMemberReadEqualsData(this.readMember_readEquals_cache);
                            s1_.cachedSymbol_ = arg1Value;
                            s1_.classProfile_ = ValueProfile.createClassProfile();
                            VarHandle.storeStoreFence();
                            this.readMember_readEquals_cache = s1_;
                            this.exclude_ = exclude |= 4;
                            this.readMember_readIdentity_cache = null;
                            state_0 &= 0xFFFFFFF7;
                            this.state_0_ = state_0 |= 0x10;
                        }
                        if (s1_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object = AbstractConstantKeysObject.ReadMember.readEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.classProfile_);
                            return object;
                        }
                    }
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.exclude_ = exclude |= 0xC;
                    this.readMember_readIdentity_cache = null;
                    this.readMember_readEquals_cache = null;
                    state_0 &= 0xFFFFFFE7;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    Object object = AbstractConstantKeysObject.ReadMember.read(arg0Value, arg1Value, this.classProfile);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((AbstractConstantKeysObject)receiver).hasMembers();
            }

            @Override
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((AbstractConstantKeysObject)receiver).getMembers(includeInternal);
            }

            @GeneratedBy(value=AbstractConstantKeysObject.class)
            private static final class ReadMemberReadEqualsData {
                @CompilerDirectives.CompilationFinal
                ReadMemberReadEqualsData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;
                @CompilerDirectives.CompilationFinal
                ValueProfile classProfile_;

                ReadMemberReadEqualsData(ReadMemberReadEqualsData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=AbstractConstantKeysObject.class)
            private static final class ReadMemberReadIdentityData {
                @CompilerDirectives.CompilationFinal
                ReadMemberReadIdentityData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;
                @CompilerDirectives.CompilationFinal
                ValueProfile classProfile_;

                ReadMemberReadIdentityData(ReadMemberReadIdentityData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=AbstractConstantKeysObject.class)
            private static final class IsMemberReadableCacheEqualsData {
                @CompilerDirectives.CompilationFinal
                IsMemberReadableCacheEqualsData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;
                @CompilerDirectives.CompilationFinal
                Class<?> cachedClass_;
                @CompilerDirectives.CompilationFinal
                boolean result_;

                IsMemberReadableCacheEqualsData(IsMemberReadableCacheEqualsData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=AbstractConstantKeysObject.class)
            private static final class IsMemberReadableCacheIdentityData {
                @CompilerDirectives.CompilationFinal
                IsMemberReadableCacheIdentityData next_;
                @CompilerDirectives.CompilationFinal
                String cachedSymbol_;
                @CompilerDirectives.CompilationFinal
                Class<?> cachedClass_;
                @CompilerDirectives.CompilationFinal
                boolean result_;

                IsMemberReadableCacheIdentityData(IsMemberReadableCacheIdentityData next_) {
                    this.next_ = next_;
                }
            }
        }
    }
}

