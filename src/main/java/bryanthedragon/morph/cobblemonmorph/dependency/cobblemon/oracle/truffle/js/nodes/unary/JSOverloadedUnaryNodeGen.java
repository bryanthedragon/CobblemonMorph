/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.JSOverloadedUnaryNode;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.OperatorSet;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSOverloadedUnaryNode.class)
public final class JSOverloadedUnaryNodeGen
extends JSOverloadedUnaryNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private CachedData cached_cache;
    @Node.Child
    private JSFunctionCallNode generic_callNode_;

    private JSOverloadedUnaryNodeGen(TruffleString overloadedOperatorName) {
        super(overloadedOperatorName);
    }

    @Override
    @ExplodeLoop
    public Object execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 && arg0Value instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject arg0Value_ = (JSOverloadedOperatorsObject)arg0Value;
            if ((state_0 & 1) != 0) {
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    if (arg0Value_.matchesOperatorCounter(s0_.operatorCounter_)) {
                        return this.doCached(arg0Value_, s0_.operatorCounter_, s0_.operatorImplementation_, s0_.callNode_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                return this.doGeneric(arg0Value_, this.generic_callNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Object executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            int oldState_0 = state_0;
            if (!(arg0Value instanceof JSOverloadedOperatorsObject)) throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            JSOverloadedOperatorsObject arg0Value_ = (JSOverloadedOperatorsObject)arg0Value;
            if (exclude == 0) {
                int operatorCounter__;
                int count0_ = 0;
                CachedData s0_ = this.cached_cache;
                if ((state_0 & 1) != 0) {
                    while (s0_ != null && !arg0Value_.matchesOperatorCounter(s0_.operatorCounter_)) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && arg0Value_.matchesOperatorCounter(operatorCounter__ = arg0Value_.getOperatorCounter()) && count0_ < 3) {
                    s0_ = super.insert(new CachedData(this.cached_cache));
                    s0_.operatorCounter_ = operatorCounter__;
                    s0_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value_, this.getOverloadedOperatorName());
                    s0_.callNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
                    VarHandle.storeStoreFence();
                    this.cached_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doCached(arg0Value_, s0_.operatorCounter_, s0_.operatorImplementation_, s0_.callNode_);
                    return object;
                }
            }
            this.generic_callNode_ = super.insert(JSFunctionCallNode.createCall());
            this.exclude_ = exclude |= 1;
            this.cached_cache = null;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            Object object = this.doGeneric(arg0Value_, this.generic_callNode_);
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
        if ((oldState_0 & 2) == 0 && (this.state_0_ & 2) != 0) {
            this.reportPolymorphicSpecialize();
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

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            CachedData s0_ = this.cached_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.operatorCounter_, s0_.operatorImplementation_, s0_.callNode_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.generic_callNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static JSOverloadedUnaryNode create(TruffleString overloadedOperatorName) {
        return new JSOverloadedUnaryNodeGen(overloadedOperatorName);
    }

    @GeneratedBy(value=JSOverloadedUnaryNode.class)
    private static final class CachedData
    extends Node {
        @Node.Child
        CachedData next_;
        @CompilerDirectives.CompilationFinal
        int operatorCounter_;
        @CompilerDirectives.CompilationFinal
        Object operatorImplementation_;
        @Node.Child
        JSFunctionCallNode callNode_;

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

