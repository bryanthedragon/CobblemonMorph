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
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ToTemporalCalendarWithISODefaultNode.class)
public final class ToTemporalCalendarWithISODefaultNodeGen
extends ToTemporalCalendarWithISODefaultNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private ToTemporalCalendarWithISODefaultData toTemporalCalendarWithISODefault_cache;

    private ToTemporalCalendarWithISODefaultNodeGen(JSContext ctx) {
        super(ctx);
    }

    @Override
    public JSDynamicObject executeDynamicObject(Object arg0Value) {
        ToTemporalCalendarWithISODefaultData s0_;
        int state_0 = this.state_0_;
        if (state_0 != 0 && (s0_ = this.toTemporalCalendarWithISODefault_cache) != null) {
            return this.toTemporalCalendarWithISODefault(arg0Value, s0_.errorBranch_, s0_.toTemporalCalendarNode_, s0_.calendarAvailable_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JSDynamicObject executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            ToTemporalCalendarWithISODefaultData s0_ = super.insert(new ToTemporalCalendarWithISODefaultData());
            s0_.errorBranch_ = BranchProfile.create();
            s0_.toTemporalCalendarNode_ = s0_.insertAccessor(ToTemporalCalendarNode.create(this.ctx));
            s0_.calendarAvailable_ = ConditionProfile.createBinaryProfile();
            VarHandle.storeStoreFence();
            this.toTemporalCalendarWithISODefault_cache = s0_;
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            JSDynamicObject jSDynamicObject = this.toTemporalCalendarWithISODefault(arg0Value, s0_.errorBranch_, s0_.toTemporalCalendarNode_, s0_.calendarAvailable_);
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
        s[0] = "toTemporalCalendarWithISODefault";
        if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
            ToTemporalCalendarWithISODefaultData s0_ = this.toTemporalCalendarWithISODefault_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.errorBranch_, s0_.toTemporalCalendarNode_, s0_.calendarAvailable_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static ToTemporalCalendarWithISODefaultNode create(JSContext ctx) {
        return new ToTemporalCalendarWithISODefaultNodeGen(ctx);
    }

    @GeneratedBy(value=ToTemporalCalendarWithISODefaultNode.class)
    private static final class ToTemporalCalendarWithISODefaultData
    extends Node {
        @CompilerDirectives.CompilationFinal
        BranchProfile errorBranch_;
        @Node.Child
        ToTemporalCalendarNode toTemporalCalendarNode_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile calendarAvailable_;

        ToTemporalCalendarWithISODefaultData() {
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

