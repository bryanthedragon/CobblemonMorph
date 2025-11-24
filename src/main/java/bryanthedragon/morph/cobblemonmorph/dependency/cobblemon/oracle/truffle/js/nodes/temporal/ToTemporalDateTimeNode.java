
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.temporal.GetTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateTimeNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDateTimeRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstant;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.List;

public abstract class ToTemporalDateTimeNode
extends JavaScriptBaseNode {
    private final ConditionProfile isObjectProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isPlainDateTimeProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isZonedDateTimeProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isPlainDateProfile = ConditionProfile.createBinaryProfile();
    protected final JSContext ctx;

    protected ToTemporalDateTimeNode(JSContext context) {
        this.ctx = context;
    }

    public static ToTemporalDateTimeNode create(JSContext context) {
        return ToTemporalDateTimeNodeGen.create(context);
    }

    public abstract JSDynamicObject executeDynamicObject(Object var1, JSDynamicObject var2);

    @Specialization
    public JSDynamicObject toTemporalDateTime(Object item, JSDynamicObject options, @Cached BranchProfile errorBranch, @Cached(value="create()") IsObjectNode isObjectNode, @Cached(value="create()") JSToStringNode toStringNode, @Cached(value="create(ctx)") GetTemporalCalendarWithISODefaultNode getTemporalCalendarNode, @Cached(value="create(ctx)") ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode, @Cached(value="create(ctx)") TemporalCalendarFieldsNode calendarFieldsNode, @Cached TemporalGetOptionNode getOptionNode, @Cached(value="create(ctx)") TemporalCalendarDateFromFieldsNode dateFromFieldsNode) {
        assert (options != null);
        JSTemporalDateTimeRecord result = null;
        JSDynamicObject calendar = null;
        if (this.isObjectProfile.profile(isObjectNode.executeBoolean(item))) {
            JSDynamicObject itemObj = (JSDynamicObject)item;
            if (this.isPlainDateTimeProfile.profile(itemObj instanceof JSTemporalPlainDateTimeObject)) {
                return itemObj;
            }
            if (this.isZonedDateTimeProfile.profile(TemporalUtil.isTemporalZonedDateTime(itemObj))) {
                TemporalUtil.toTemporalOverflow(options, getOptionNode);
                JSTemporalZonedDateTimeObject zdt = (JSTemporalZonedDateTimeObject)itemObj;
                JSTemporalInstantObject instant = JSTemporalInstant.create(this.ctx, this.getRealm(), zdt.getNanoseconds());
                return TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.ctx, zdt.getTimeZone(), instant, zdt.getCalendar());
            }
            if (this.isPlainDateProfile.profile(itemObj instanceof JSTemporalPlainDateObject)) {
                TemporalUtil.toTemporalOverflow(options, getOptionNode);
                JSTemporalPlainDateObject date = (JSTemporalPlainDateObject)itemObj;
                return JSTemporalPlainDateTime.create(this.ctx, date.getYear(), date.getMonth(), date.getDay(), 0, 0, 0, 0, 0, 0, date.getCalendar(), errorBranch);
            }
            calendar = getTemporalCalendarNode.executeDynamicObject(itemObj);
            List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listDHMMMMMNSY);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.ctx, itemObj, fieldNames, TemporalUtil.listEmpty);
            result = TemporalUtil.interpretTemporalDateTimeFields(calendar, fields, options, getOptionNode, dateFromFieldsNode);
        } else {
            TemporalUtil.toTemporalOverflow(options, getOptionNode);
            TruffleString string = toStringNode.executeString(item);
            result = TemporalUtil.parseTemporalDateTimeString(string);
            assert (TemporalUtil.isValidISODate(result.getYear(), result.getMonth(), result.getDay()));
            assert (TemporalUtil.isValidTime(result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond()));
            calendar = toTemporalCalendarWithISODefaultNode.executeDynamicObject(result.getCalendar());
        }
        return JSTemporalPlainDateTime.create(this.ctx, result.getYear(), result.getMonth(), result.getDay(), result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond(), calendar, errorBranch);
    }
}

