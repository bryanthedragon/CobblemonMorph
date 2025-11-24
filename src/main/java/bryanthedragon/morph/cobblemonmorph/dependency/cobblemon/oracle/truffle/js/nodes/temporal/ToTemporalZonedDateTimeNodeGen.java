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
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.temporal.GetTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalZonedDateTimeNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ToTemporalZonedDateTimeNode.class)
public final class ToTemporalZonedDateTimeNodeGen
extends ToTemporalZonedDateTimeNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private ToTemporalZonedDateTimeData toTemporalZonedDateTime_cache;

    private ToTemporalZonedDateTimeNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public JSDynamicObject executeDynamicObject(Object arg0Value, JSDynamicObject arg1Value) {
        ToTemporalZonedDateTimeData s0_;
        int state_0 = this.state_0_;
        if (state_0 != 0 && (s0_ = this.toTemporalZonedDateTime_cache) != null) {
            return this.toTemporalZonedDateTime(arg0Value, arg1Value, s0_.isObjectNode_, s0_.toStringNode_, s0_.getOptionNode_, s0_.toTemporalTimeZone_, s0_.getTemporalCalendarNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.calendarFieldsNode_, s0_.equalNode_, s0_.dateFromFieldsNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JSDynamicObject executeAndSpecialize(Object arg0Value, JSDynamicObject arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            ToTemporalZonedDateTimeData s0_ = super.insert(new ToTemporalZonedDateTimeData());
            s0_.isObjectNode_ = s0_.insertAccessor(IsObjectNode.create());
            s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
            s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
            s0_.toTemporalTimeZone_ = s0_.insertAccessor(ToTemporalTimeZoneNode.create(this.ctx));
            s0_.getTemporalCalendarNode_ = s0_.insertAccessor(GetTemporalCalendarWithISODefaultNode.create(this.ctx));
            s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.ctx));
            s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.ctx));
            s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
            s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.ctx));
            VarHandle.storeStoreFence();
            this.toTemporalZonedDateTime_cache = s0_;
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            JSDynamicObject jSDynamicObject = this.toTemporalZonedDateTime(arg0Value, arg1Value, s0_.isObjectNode_, s0_.toStringNode_, s0_.getOptionNode_, s0_.toTemporalTimeZone_, s0_.getTemporalCalendarNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.calendarFieldsNode_, s0_.equalNode_, s0_.dateFromFieldsNode_);
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
        s[0] = "toTemporalZonedDateTime";
        if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
            ToTemporalZonedDateTimeData s0_ = this.toTemporalZonedDateTime_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.isObjectNode_, s0_.toStringNode_, s0_.getOptionNode_, s0_.toTemporalTimeZone_, s0_.getTemporalCalendarNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.calendarFieldsNode_, s0_.equalNode_, s0_.dateFromFieldsNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static ToTemporalZonedDateTimeNode create(JSContext context) {
        return new ToTemporalZonedDateTimeNodeGen(context);
    }

    @GeneratedBy(value=ToTemporalZonedDateTimeNode.class)
    private static final class ToTemporalZonedDateTimeData
    extends Node {
        @Node.Child
        IsObjectNode isObjectNode_;
        @Node.Child
        JSToStringNode toStringNode_;
        @Node.Child
        TemporalGetOptionNode getOptionNode_;
        @Node.Child
        ToTemporalTimeZoneNode toTemporalTimeZone_;
        @Node.Child
        GetTemporalCalendarWithISODefaultNode getTemporalCalendarNode_;
        @Node.Child
        ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;
        @Node.Child
        TemporalCalendarFieldsNode calendarFieldsNode_;
        @Node.Child
        TruffleString.EqualNode equalNode_;
        @Node.Child
        TemporalCalendarDateFromFieldsNode dateFromFieldsNode_;

        ToTemporalZonedDateTimeData() {
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

