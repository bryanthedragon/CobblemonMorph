/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.temporal.TemporalBalanceDurationRelativeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TemporalBalanceDurationRelativeNode.class)
public final class TemporalBalanceDurationRelativeNodeGen
extends TemporalBalanceDurationRelativeNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private BalanceDurationRelativeData balanceDurationRelative_cache;

    private TemporalBalanceDurationRelativeNodeGen(JSContext ctx) {
        super(ctx);
    }

    @Override
    public JSTemporalDurationRecord execute(double arg0Value, double arg1Value, double arg2Value, double arg3Value, TemporalUtil.Unit arg4Value, JSDynamicObject arg5Value) {
        BalanceDurationRelativeData s0_;
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && (s0_ = this.balanceDurationRelative_cache) != null) {
            return this.balanceDurationRelative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.unitIsYear_, s0_.unitIsMonth_, s0_.unitIsDay_, s0_.toTemporalDateNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JSTemporalDurationRecord executeAndSpecialize(double arg0Value, double arg1Value, double arg2Value, double arg3Value, TemporalUtil.Unit arg4Value, JSDynamicObject arg5Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            BalanceDurationRelativeData s0_ = super.insert(new BalanceDurationRelativeData());
            s0_.unitIsYear_ = ConditionProfile.createBinaryProfile();
            s0_.unitIsMonth_ = ConditionProfile.createBinaryProfile();
            s0_.unitIsDay_ = ConditionProfile.createBinaryProfile();
            s0_.toTemporalDateNode_ = s0_.insertAccessor(ToTemporalDateNode.create(this.ctx));
            VarHandle.storeStoreFence();
            this.balanceDurationRelative_cache = s0_;
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            JSTemporalDurationRecord jSTemporalDurationRecord = this.balanceDurationRelative(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, s0_.unitIsYear_, s0_.unitIsMonth_, s0_.unitIsDay_, s0_.toTemporalDateNode_);
            return jSTemporalDurationRecord;
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
        if ((state_0 & 1) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        return NodeCost.MONOMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[2];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "balanceDurationRelative";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
            BalanceDurationRelativeData s0_ = this.balanceDurationRelative_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.unitIsYear_, s0_.unitIsMonth_, s0_.unitIsDay_, s0_.toTemporalDateNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static TemporalBalanceDurationRelativeNode create(JSContext ctx) {
        return new TemporalBalanceDurationRelativeNodeGen(ctx);
    }

    @GeneratedBy(value=TemporalBalanceDurationRelativeNode.class)
    private static final class BalanceDurationRelativeData
    extends Node {
        @CompilerDirectives.CompilationFinal
        ConditionProfile unitIsYear_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile unitIsMonth_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile unitIsDay_;
        @Node.Child
        ToTemporalDateNode toTemporalDateNode_;

        BalanceDurationRelativeData() {
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

