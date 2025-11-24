
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendar;
import com.oracle.truffle.js.runtime.builtins.temporal.TemporalCalendar;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public abstract class ToTemporalCalendarNode
extends JavaScriptBaseNode {
    private final ConditionProfile isObjectProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isCalendarProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile hasCalendarProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile hasCalendar2Profile = ConditionProfile.createBinaryProfile();
    private final BranchProfile parseBranch = BranchProfile.create();
    private final JSContext context;
    @Node.Child
    private PropertyGetNode getCalendarPropertyNode;

    protected ToTemporalCalendarNode(JSContext context) {
        this.context = context;
    }

    public static ToTemporalCalendarNode create(JSContext context) {
        return ToTemporalCalendarNodeGen.create(context);
    }

    public abstract JSDynamicObject executeDynamicObject(Object var1);

    @Specialization
    public JSDynamicObject toTemporalCalendar(Object itemParam, @Cached BranchProfile errorBranch, @Cached(value="create()") IsObjectNode isObjectNode, @Cached(value="create()") JSToStringNode toStringNode) {
        TruffleString identifier;
        Object item = itemParam;
        if (this.isObjectProfile.profile(isObjectNode.executeBoolean(item))) {
            JSDynamicObject itemObj = TemporalUtil.toJSDynamicObject(item, errorBranch);
            if (this.isCalendarProfile.profile(item instanceof TemporalCalendar)) {
                return ((TemporalCalendar)item).getCalendar();
            }
            if (this.hasCalendarProfile.profile(!JSObject.hasProperty(itemObj, TemporalConstants.CALENDAR))) {
                return itemObj;
            }
            item = this.getCalendarProperty(itemObj);
            if (this.hasCalendar2Profile.profile(isObjectNode.executeBoolean(item) && !JSObject.hasProperty((JSDynamicObject)item, TemporalConstants.CALENDAR))) {
                return (JSDynamicObject)item;
            }
        }
        if (!TemporalUtil.isBuiltinCalendar(identifier = toStringNode.executeString(item))) {
            this.parseBranch.enter();
            identifier = TemporalUtil.parseTemporalCalendarString(identifier);
            if (!TemporalUtil.isBuiltinCalendar(identifier)) {
                throw TemporalErrors.createRangeErrorCalendarUnknown();
            }
        }
        return JSTemporalCalendar.create(this.context, this.getRealm(), identifier, errorBranch);
    }

    private Object getCalendarProperty(JSDynamicObject obj) {
        if (this.getCalendarPropertyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getCalendarPropertyNode = this.insert(PropertyGetNode.create(TemporalConstants.CALENDAR, false, this.context));
        }
        return this.getCalendarPropertyNode.getValue(obj);
    }
}

