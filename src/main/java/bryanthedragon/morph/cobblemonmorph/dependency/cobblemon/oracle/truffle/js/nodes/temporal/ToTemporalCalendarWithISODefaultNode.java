
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarWithISODefaultNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendar;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;

public abstract class ToTemporalCalendarWithISODefaultNode
extends JavaScriptBaseNode {
    protected final JSContext ctx;

    protected ToTemporalCalendarWithISODefaultNode(JSContext ctx) {
        this.ctx = ctx;
    }

    public static ToTemporalCalendarWithISODefaultNode create(JSContext context) {
        return ToTemporalCalendarWithISODefaultNodeGen.create(context);
    }

    public abstract JSDynamicObject executeDynamicObject(Object var1);

    @Specialization
    public JSDynamicObject toTemporalCalendarWithISODefault(Object calendar, @Cached BranchProfile errorBranch, @Cached(value="create(ctx)") ToTemporalCalendarNode toTemporalCalendarNode, @Cached(value="createBinaryProfile()") ConditionProfile calendarAvailable) {
        if (calendarAvailable.profile(calendar == null || calendar == Undefined.instance)) {
            return JSTemporalCalendar.create(this.ctx, this.getRealm(), TemporalConstants.ISO8601, errorBranch);
        }
        return toTemporalCalendarNode.executeDynamicObject(calendar);
    }
}

