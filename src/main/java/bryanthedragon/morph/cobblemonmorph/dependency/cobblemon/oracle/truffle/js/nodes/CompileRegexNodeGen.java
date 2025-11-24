/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.utilities.AssumedValue;
import com.oracle.truffle.js.nodes.CompileRegexNode;
import com.oracle.truffle.js.runtime.JSContext;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=CompileRegexNode.class)
public final class CompileRegexNodeGen
extends CompileRegexNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private GetCachedData getCached_cache;

    private CompileRegexNodeGen(JSContext context) {
        super(context);
    }

    @Override
    @ExplodeLoop
    protected Object executeCompile(Object arg0Value, Object arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            if (arg1Value instanceof TruffleString) {
                TruffleString arg1Value_ = (TruffleString)arg1Value;
                if ((state_0 & 1) != 0) {
                    GetCachedData s0_ = this.getCached_cache;
                    while (s0_ != null) {
                        if (CompileRegexNode.stringEquals(s0_.equalsNode_, arg0Value_, s0_.cachedPattern_) && CompileRegexNode.stringEquals(s0_.equalsNode2_, arg1Value_, s0_.cachedFlags_)) {
                            return this.getCached(arg0Value_, arg1Value_, s0_.cachedPattern_, s0_.cachedFlags_, s0_.cachedCompiledRegex_, s0_.equalsNode_, s0_.equalsNode2_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    assert (false);
                    return this.doCompileNoTrimCache(arg0Value_, arg1Value_);
                }
                if ((state_0 & 4) != 0) {
                    return this.doCompile(arg0Value_, arg1Value_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Object executeAndSpecialize(Object arg0Value, Object arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            int oldState_0 = state_0;
            if (!(arg0Value instanceof TruffleString)) throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            if (!(arg1Value instanceof TruffleString)) throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            TruffleString arg1Value_ = (TruffleString)arg1Value;
            if (exclude == 0) {
                int count0_ = 0;
                GetCachedData s0_ = this.getCached_cache;
                if ((state_0 & 1) != 0) {
                    while (!(s0_ == null || CompileRegexNode.stringEquals(s0_.equalsNode_, arg0Value_, s0_.cachedPattern_) && CompileRegexNode.stringEquals(s0_.equalsNode2_, arg1Value_, s0_.cachedFlags_))) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null) {
                    TruffleString cachedPattern__ = arg0Value_;
                    TruffleString.EqualNode equalsNode__ = super.insert(TruffleString.EqualNode.create());
                    if (CompileRegexNode.stringEquals(equalsNode__, arg0Value_, cachedPattern__)) {
                        TruffleString cachedFlags__ = arg1Value_;
                        TruffleString.EqualNode equalsNode2__ = super.insert(TruffleString.EqualNode.create());
                        if (CompileRegexNode.stringEquals(equalsNode2__, arg1Value_, cachedFlags__) && count0_ < 4) {
                            s0_ = super.insert(new GetCachedData(this.getCached_cache));
                            s0_.cachedPattern_ = cachedPattern__;
                            s0_.cachedFlags_ = cachedFlags__;
                            s0_.cachedCompiledRegex_ = this.createAssumedValue();
                            s0_.equalsNode_ = s0_.insertAccessor(equalsNode__);
                            s0_.equalsNode2_ = s0_.insertAccessor(equalsNode2__);
                            VarHandle.storeStoreFence();
                            this.getCached_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                    }
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    Object object = this.getCached(arg0Value_, arg1Value_, s0_.cachedPattern_, s0_.cachedFlags_, s0_.cachedCompiledRegex_, s0_.equalsNode_, s0_.equalsNode2_);
                    return object;
                }
            }
            this.exclude_ = exclude |= 1;
            this.getCached_cache = null;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 4;
            lock.unlock();
            hasLock = false;
            Object object = this.doCompile(arg0Value_, arg1Value_);
            return object;
            finally {
                if (oldState_0 != 0) {
                    this.checkForPolymorphicSpecialize(oldState_0);
                }
            }
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    private void checkForPolymorphicSpecialize(int oldState_0) {
        if ((oldState_0 & 4) == 0 && (this.state_0_ & 4) != 0) {
            this.reportPolymorphicSpecialize();
        }
    }

    @Override
    public NodeCost getCost() {
        GetCachedData s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.getCached_cache) == null || s0_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "getCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<List<Object>> cached = new ArrayList<List<Object>>();
            GetCachedData s0_ = this.getCached_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.cachedPattern_, s0_.cachedFlags_, s0_.cachedCompiledRegex_, s0_.equalsNode_, s0_.equalsNode2_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doCompileNoTrimCache";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doCompile";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static CompileRegexNode create(JSContext context) {
        return new CompileRegexNodeGen(context);
    }

    @GeneratedBy(value=CompileRegexNode.class)
    private static final class GetCachedData
    extends Node {
        @Node.Child
        GetCachedData next_;
        @CompilerDirectives.CompilationFinal
        TruffleString cachedPattern_;
        @CompilerDirectives.CompilationFinal
        TruffleString cachedFlags_;
        @CompilerDirectives.CompilationFinal
        AssumedValue<Object> cachedCompiledRegex_;
        @Node.Child
        TruffleString.EqualNode equalsNode_;
        @Node.Child
        TruffleString.EqualNode equalsNode2_;

        GetCachedData(GetCachedData next_) {
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

