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
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.temporal.GetTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ToTemporalTimeNode.class)
public final class ToTemporalTimeNodeGen
extends ToTemporalTimeNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private ToTemporalTimeData toTemporalTime_cache;

    private ToTemporalTimeNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public JSDynamicObject executeDynamicObject(Object arg0Value, TemporalUtil.Overflow arg1Value) {
        ToTemporalTimeData s0_;
        int state_0 = this.state_0_;
        if (state_0 != 0 && (s0_ = this.toTemporalTime_cache) != null) {
            return this.toTemporalTime(arg0Value, arg1Value, s0_.isObjectNode_, s0_.toStringNode_, s0_.getTemporalCalendarNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JSDynamicObject executeAndSpecialize(Object arg0Value, TemporalUtil.Overflow arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            ToTemporalTimeData s0_ = super.insert(new ToTemporalTimeData());
            s0_.isObjectNode_ = s0_.insertAccessor(IsObjectNode.create());
            s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
            s0_.getTemporalCalendarNode_ = s0_.insertAccessor(GetTemporalCalendarWithISODefaultNode.create(this.ctx));
            VarHandle.storeStoreFence();
            this.toTemporalTime_cache = s0_;
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            JSDynamicObject jSDynamicObject = this.toTemporalTime(arg0Value, arg1Value, s0_.isObjectNode_, s0_.toStringNode_, s0_.getTemporalCalendarNode_);
            return jSDynamicObject;
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

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[2];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "toTemporalTime";
        if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
            ToTemporalTimeData s0_ = this.toTemporalTime_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.isObjectNode_, s0_.toStringNode_, s0_.getTemporalCalendarNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static ToTemporalTimeNode create(JSContext context) {
        return new ToTemporalTimeNodeGen(context);
    }

    @GeneratedBy(value=ToTemporalTimeNode.class)
    private static final class ToTemporalTimeData
    extends Node {
        @Node.Child
        IsObjectNode isObjectNode_;
        @Node.Child
        JSToStringNode toStringNode_;
        @Node.Child
        GetTemporalCalendarWithISODefaultNode getTemporalCalendarNode_;

        ToTemporalTimeData() {
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

