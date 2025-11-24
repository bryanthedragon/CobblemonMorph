
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
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDateTimeRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstant;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.List;

public abstract class ToTemporalDateNode
extends JavaScriptBaseNode {
    private final ConditionProfile isObjectProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isPlainDateTimeProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isZonedDateTimeProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isPlainDateProfile = ConditionProfile.createBinaryProfile();
    private final BranchProfile errorBranch = BranchProfile.create();
    protected final JSContext ctx;

    protected ToTemporalDateNode(JSContext context) {
        this.ctx = context;
    }

    public static ToTemporalDateNode create(JSContext context) {
        return ToTemporalDateNodeGen.create(context);
    }

    public abstract JSTemporalPlainDateObject executeDynamicObject(Object var1, JSDynamicObject var2);

    @Specialization
    public JSTemporalPlainDateObject toTemporalDate(Object itemParam, JSDynamicObject options, @Cached(value="create()") IsObjectNode isObjectNode, @Cached(value="create()") JSToStringNode toStringNode, @Cached(value="create(ctx)") GetTemporalCalendarWithISODefaultNode getTemporalCalendarNode, @Cached TemporalGetOptionNode getOptionNode, @Cached(value="create(ctx)") ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode, @Cached(value="create(ctx)") TemporalCalendarFieldsNode calendarFieldsNode, @Cached(value="create(ctx)") TemporalCalendarDateFromFieldsNode dateFromFieldsNode) {
        assert (options != null);
        if (this.isObjectProfile.profile(isObjectNode.executeBoolean(itemParam))) {
            JSDynamicObject item = (JSDynamicObject)itemParam;
            if (this.isPlainDateProfile.profile(JSTemporalPlainDate.isJSTemporalPlainDate(item))) {
                return (JSTemporalPlainDateObject)item;
            }
            if (this.isZonedDateTimeProfile.profile(JSTemporalZonedDateTime.isJSTemporalZonedDateTime(item))) {
                TemporalUtil.toTemporalOverflow(options, getOptionNode);
                JSTemporalZonedDateTimeObject zdt = (JSTemporalZonedDateTimeObject)item;
                JSTemporalInstantObject instant = JSTemporalInstant.create(this.ctx, this.getRealm(), zdt.getNanoseconds());
                JSTemporalPlainDateTimeObject plainDateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.ctx, zdt.getTimeZone(), instant, zdt.getCalendar());
                return JSTemporalPlainDate.create(this.ctx, plainDateTime.getYear(), plainDateTime.getMonth(), plainDateTime.getDay(), plainDateTime.getCalendar(), this.errorBranch);
            }
            if (this.isPlainDateTimeProfile.profile(JSTemporalPlainDateTime.isJSTemporalPlainDateTime(item))) {
                TemporalUtil.toTemporalOverflow(options, getOptionNode);
                JSTemporalPlainDateTimeObject dt = (JSTemporalPlainDateTimeObject)item;
                return JSTemporalPlainDate.create(this.ctx, dt.getYear(), dt.getMonth(), dt.getDay(), dt.getCalendar(), this.errorBranch);
            }
            JSDynamicObject calendar = getTemporalCalendarNode.executeDynamicObject(item);
            List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listDMMCY);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.ctx, item, fieldNames, TemporalUtil.listEmpty);
            return dateFromFieldsNode.execute(calendar, fields, options);
        }
        TemporalUtil.toTemporalOverflow(options, getOptionNode);
        JSTemporalDateTimeRecord result = TemporalUtil.parseTemporalDateString(toStringNode.executeString(itemParam));
        assert (TemporalUtil.isValidISODate(result.getYear(), result.getMonth(), result.getDay()));
        JSDynamicObject calendar = toTemporalCalendarWithISODefaultNode.executeDynamicObject(result.getCalendar());
        return JSTemporalPlainDate.create(this.ctx, result.getYear(), result.getMonth(), result.getDay(), calendar, this.errorBranch);
    }
}

