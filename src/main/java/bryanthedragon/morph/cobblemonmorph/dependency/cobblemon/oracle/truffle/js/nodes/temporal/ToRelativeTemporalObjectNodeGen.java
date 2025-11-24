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
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.ToRelativeTemporalObjectNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ToRelativeTemporalObjectNode.class)
public final class ToRelativeTemporalObjectNodeGen
extends ToRelativeTemporalObjectNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private ToRelativeTemporalObjectData toRelativeTemporalObject_cache;

    private ToRelativeTemporalObjectNodeGen(JSContext ctx) {
        super(ctx);
    }

    @Override
    public JSDynamicObject execute(JSDynamicObject arg0Value) {
        ToRelativeTemporalObjectData s0_;
        int state_0 = this.state_0_;
        if (state_0 != 0 && (s0_ = this.toRelativeTemporalObject_cache) != null) {
            return this.toRelativeTemporalObject(arg0Value, s0_.errorBranch_, s0_.valueIsObject_, s0_.valueIsUndefined_, s0_.valueIsPlainDate_, s0_.valueIsPlainDateTime_, s0_.timeZoneAvailable_, s0_.toStringNode_, s0_.isObjectNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_, s0_.getOptionNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JSDynamicObject executeAndSpecialize(JSDynamicObject arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            ToRelativeTemporalObjectData s0_ = super.insert(new ToRelativeTemporalObjectData());
            s0_.errorBranch_ = BranchProfile.create();
            s0_.valueIsObject_ = ConditionProfile.createBinaryProfile();
            s0_.valueIsUndefined_ = ConditionProfile.createBinaryProfile();
            s0_.valueIsPlainDate_ = ConditionProfile.createBinaryProfile();
            s0_.valueIsPlainDateTime_ = ConditionProfile.createBinaryProfile();
            s0_.timeZoneAvailable_ = ConditionProfile.createBinaryProfile();
            s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
            s0_.isObjectNode_ = s0_.insertAccessor(IsObjectNode.create());
            s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.ctx));
            s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.ctx));
            s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.ctx));
            s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
            VarHandle.storeStoreFence();
            this.toRelativeTemporalObject_cache = s0_;
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            JSDynamicObject jSDynamicObject = this.toRelativeTemporalObject(arg0Value, s0_.errorBranch_, s0_.valueIsObject_, s0_.valueIsUndefined_, s0_.valueIsPlainDate_, s0_.valueIsPlainDateTime_, s0_.timeZoneAvailable_, s0_.toStringNode_, s0_.isObjectNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_, s0_.getOptionNode_);
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
        s[0] = "toRelativeTemporalObject";
        if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
            ToRelativeTemporalObjectData s0_ = this.toRelativeTemporalObject_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.errorBranch_, s0_.valueIsObject_, s0_.valueIsUndefined_, s0_.valueIsPlainDate_, s0_.valueIsPlainDateTime_, s0_.timeZoneAvailable_, s0_.toStringNode_, s0_.isObjectNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_, s0_.getOptionNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static ToRelativeTemporalObjectNode create(JSContext ctx) {
        return new ToRelativeTemporalObjectNodeGen(ctx);
    }

    @GeneratedBy(value=ToRelativeTemporalObjectNode.class)
    private static final class ToRelativeTemporalObjectData
    extends Node {
        @CompilerDirectives.CompilationFinal
        BranchProfile errorBranch_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile valueIsObject_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile valueIsUndefined_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile valueIsPlainDate_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile valueIsPlainDateTime_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile timeZoneAvailable_;
        @Node.Child
        JSToStringNode toStringNode_;
        @Node.Child
        IsObjectNode isObjectNode_;
        @Node.Child
        ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;
        @Node.Child
        TemporalCalendarFieldsNode calendarFieldsNode_;
        @Node.Child
        TemporalCalendarDateFromFieldsNode dateFromFieldsNode_;
        @Node.Child
        TemporalGetOptionNode getOptionNode_;

        ToRelativeTemporalObjectData() {
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

