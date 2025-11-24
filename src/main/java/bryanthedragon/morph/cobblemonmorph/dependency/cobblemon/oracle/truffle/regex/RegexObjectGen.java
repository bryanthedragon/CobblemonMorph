/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.regex;

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
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.regex.AbstractConstantKeysObjectGen;
import com.oracle.truffle.regex.RegexObject;
import com.oracle.truffle.regex.RegexObjectFactory;
import com.oracle.truffle.regex.runtime.nodes.ToLongNode;
import com.oracle.truffle.regex.runtime.nodes.ToLongNodeGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=RegexObject.class)
final class RegexObjectGen {
    private RegexObjectGen() {
    }

    static {
        LibraryExport.register(RegexObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=RegexObject.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, RegexObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof RegexObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof RegexObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=RegexObject.class)
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
            public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                RegexObject arg0Value = (RegexObject)arg0Value_;
                return RegexObject.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object invokeMember(Object arg0Value_, String arg1Value, Object ... arg2Value) throws UnknownIdentifierException, ArityException, UnsupportedTypeException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                RegexObject arg0Value = (RegexObject)arg0Value_;
                return arg0Value.invokeMember(arg1Value, arg2Value, ToLongNodeGen.getUncached(), RegexObjectFactory.InvokeCacheNodeGen.getUncached());
            }
        }

        @GeneratedBy(value=RegexObject.class)
        private static final class Cached
        extends AbstractConstantKeysObjectGen.InteropLibraryExports.Cached {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @CompilerDirectives.CompilationFinal
            private ValueProfile classProfile;
            @CompilerDirectives.CompilationFinal
            private IsMemberInvocableCacheIdentityData isMemberInvocable_cacheIdentity_cache;
            @CompilerDirectives.CompilationFinal
            private IsMemberInvocableCacheEqualsData isMemberInvocable_cacheEquals_cache;
            @Node.Child
            private ToLongNode invokeMemberNode__invokeMember_toLongNode_;
            @Node.Child
            private RegexObject.InvokeCacheNode invokeMemberNode__invokeMember_invokeCache_;

            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            @ExplodeLoop
            public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                RegexObject arg0Value = (RegexObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 7) != 0) {
                    if ((state_0 & 1) != 0) {
                        IsMemberInvocableCacheIdentityData s0_ = this.isMemberInvocable_cacheIdentity_cache;
                        while (s0_ != null) {
                            if (arg1Value == s0_.cachedSymbol_) {
                                assert (s0_.result_);
                                return RegexObject.IsMemberInvocable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.result_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        IsMemberInvocableCacheEqualsData s1_ = this.isMemberInvocable_cacheEquals_cache;
                        while (s1_ != null) {
                            if (arg1Value.equals(s1_.cachedSymbol_)) {
                                assert (s1_.result_);
                                return RegexObject.IsMemberInvocable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.result_);
                            }
                            s1_ = s1_.next_;
                        }
                    }
                    if ((state_0 & 4) != 0) {
                        return RegexObject.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberInvocableAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberInvocableAndSpecialize(RegexObject arg0Value, String arg1Value) {
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
                        IsMemberInvocableCacheIdentityData s0_ = this.isMemberInvocable_cacheIdentity_cache;
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
                        if (s0_ == null && (result__ = RegexObject.IsMemberInvocable.isInvocable(arg0Value, cachedSymbol__2 = arg1Value)) && count0_ < 3) {
                            s0_ = new IsMemberInvocableCacheIdentityData(this.isMemberInvocable_cacheIdentity_cache);
                            s0_.cachedSymbol_ = cachedSymbol__2;
                            s0_.result_ = result__;
                            VarHandle.storeStoreFence();
                            this.isMemberInvocable_cacheIdentity_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean cachedSymbol__2 = RegexObject.IsMemberInvocable.cacheIdentity(arg0Value, arg1Value, s0_.cachedSymbol_, s0_.result_);
                            return cachedSymbol__2;
                        }
                    }
                    if ((exclude & 2) == 0) {
                        String cachedSymbol__1;
                        boolean result__1;
                        int count1_ = 0;
                        IsMemberInvocableCacheEqualsData s1_ = this.isMemberInvocable_cacheEquals_cache;
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
                        if (s1_ == null && (result__1 = RegexObject.IsMemberInvocable.isInvocable(arg0Value, cachedSymbol__1 = arg1Value)) && count1_ < 3) {
                            s1_ = new IsMemberInvocableCacheEqualsData(this.isMemberInvocable_cacheEquals_cache);
                            s1_.cachedSymbol_ = cachedSymbol__1;
                            s1_.result_ = result__1;
                            VarHandle.storeStoreFence();
                            this.isMemberInvocable_cacheEquals_cache = s1_;
                            this.exclude_ = exclude |= 1;
                            this.isMemberInvocable_cacheIdentity_cache = null;
                            state_0 &= 0xFFFFFFFE;
                            this.state_0_ = state_0 |= 2;
                        }
                        if (s1_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean bl = RegexObject.IsMemberInvocable.cacheEquals(arg0Value, arg1Value, s1_.cachedSymbol_, s1_.result_);
                            return bl;
                        }
                    }
                    this.exclude_ = exclude |= 3;
                    this.isMemberInvocable_cacheIdentity_cache = null;
                    this.isMemberInvocable_cacheEquals_cache = null;
                    state_0 &= 0xFFFFFFFC;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = RegexObject.IsMemberInvocable.isInvocable(arg0Value, arg1Value);
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
                    IsMemberInvocableCacheIdentityData s0_ = this.isMemberInvocable_cacheIdentity_cache;
                    IsMemberInvocableCacheEqualsData s1_ = this.isMemberInvocable_cacheEquals_cache;
                    if (!(s0_ != null && s0_.next_ != null || s1_ != null && s1_.next_ != null)) {
                        return NodeCost.MONOMORPHIC;
                    }
                }
                return NodeCost.POLYMORPHIC;
            }

            @Override
            public Object invokeMember(Object arg0Value_, String arg1Value, Object ... arg2Value) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                RegexObject arg0Value = (RegexObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 8) != 0) {
                    return arg0Value.invokeMember(arg1Value, arg2Value, this.invokeMemberNode__invokeMember_toLongNode_, this.invokeMemberNode__invokeMember_invokeCache_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.invokeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object invokeMemberNode_AndSpecialize(RegexObject arg0Value, String arg1Value, Object[] arg2Value) throws UnknownIdentifierException, ArityException, UnsupportedTypeException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.invokeMemberNode__invokeMember_toLongNode_ = super.insert(ToLongNode.create());
                    this.invokeMemberNode__invokeMember_invokeCache_ = super.insert(RegexObjectFactory.InvokeCacheNodeGen.create());
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.invokeMember(arg1Value, arg2Value, this.invokeMemberNode__invokeMember_toLongNode_, this.invokeMemberNode__invokeMember_invokeCache_);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @GeneratedBy(value=RegexObject.class)
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

            @GeneratedBy(value=RegexObject.class)
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
        }
    }
}

