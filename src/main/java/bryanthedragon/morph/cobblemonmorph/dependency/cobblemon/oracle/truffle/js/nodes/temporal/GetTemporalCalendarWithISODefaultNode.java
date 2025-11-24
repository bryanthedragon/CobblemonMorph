
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.temporal.GetTemporalCalendarWithISODefaultNodeGen;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.TemporalCalendar;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public abstract class GetTemporalCalendarWithISODefaultNode
extends JavaScriptBaseNode {
    private final ConditionProfile isCalendarProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isNullishProfile = ConditionProfile.createBinaryProfile();
    private final JSContext ctx;
    @Node.Child
    protected PropertyGetNode getCalendarNode;
    @Node.Child
    protected ToTemporalCalendarNode toTemporalCalendarNode;

    protected GetTemporalCalendarWithISODefaultNode(JSContext context) {
        this.ctx = context;
    }

    public static GetTemporalCalendarWithISODefaultNode create(JSContext context) {
        return GetTemporalCalendarWithISODefaultNodeGen.create(context);
    }

    public abstract JSDynamicObject executeDynamicObject(Object var1);

    @Specialization
    protected JSDynamicObject getTemporalCalendarWithISODefault(Object item, @Cached BranchProfile errorBranch) {
        if (this.isCalendarProfile.profile(item instanceof TemporalCalendar)) {
            return ((TemporalCalendar)item).getCalendar();
        }
        Object calendar = this.getCalendar((JSDynamicObject)item);
        assert (calendar != null);
        if (this.isNullishProfile.profile(calendar == Undefined.instance)) {
            return TemporalUtil.getISO8601Calendar(this.ctx, this.getRealm(), errorBranch);
        }
        return this.toTemporalCalendar(calendar);
    }

    private JSDynamicObject toTemporalCalendar(Object obj) {
        if (this.toTemporalCalendarNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toTemporalCalendarNode = this.insert(ToTemporalCalendarNode.create(this.ctx));
        }
        return this.toTemporalCalendarNode.executeDynamicObject(obj);
    }

    private Object getCalendar(JSDynamicObject obj) {
        if (this.getCalendarNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getCalendarNode = this.insert(PropertyGetNode.create(TemporalConstants.CALENDAR, false, this.ctx));
        }
        return this.getCalendarNode.getValue(obj);
    }
}

