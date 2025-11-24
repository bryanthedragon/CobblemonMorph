
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
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDateTimeRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstant;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public abstract class ToTemporalTimeNode
extends JavaScriptBaseNode {
    private final ConditionProfile isObjectProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isPlainDateTimeProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isZonedDateTimeProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isPlainTimeProfile = ConditionProfile.createBinaryProfile();
    private final BranchProfile errorBranch = BranchProfile.create();
    protected final JSContext ctx;

    protected ToTemporalTimeNode(JSContext context) {
        this.ctx = context;
    }

    public static ToTemporalTimeNode create(JSContext context) {
        return ToTemporalTimeNodeGen.create(context);
    }

    public abstract JSDynamicObject executeDynamicObject(Object var1, TemporalUtil.Overflow var2);

    @Specialization
    protected JSDynamicObject toTemporalTime(Object item, TemporalUtil.Overflow overflowParam, @Cached(value="create()") IsObjectNode isObjectNode, @Cached(value="create()") JSToStringNode toStringNode, @Cached(value="create(ctx)") GetTemporalCalendarWithISODefaultNode getTemporalCalendarNode) {
        TemporalUtil.Overflow overflow;
        TemporalUtil.Overflow overflow2 = overflow = overflowParam == null ? TemporalUtil.Overflow.CONSTRAIN : overflowParam;
        assert (overflow == TemporalUtil.Overflow.CONSTRAIN || overflow == TemporalUtil.Overflow.REJECT);
        JSTemporalDurationRecord result2 = null;
        if (this.isObjectProfile.profile(isObjectNode.executeBoolean(item))) {
            JSDynamicObject itemObj = (JSDynamicObject)item;
            if (this.isPlainTimeProfile.profile(JSTemporalPlainTime.isJSTemporalPlainTime(itemObj))) {
                return itemObj;
            }
            if (this.isZonedDateTimeProfile.profile(TemporalUtil.isTemporalZonedDateTime(itemObj))) {
                JSTemporalZonedDateTimeObject zdt = (JSTemporalZonedDateTimeObject)itemObj;
                JSTemporalInstantObject instant = JSTemporalInstant.create(this.ctx, this.getRealm(), zdt.getNanoseconds());
                JSTemporalPlainDateTimeObject plainDateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.ctx, zdt.getTimeZone(), instant, zdt.getCalendar());
                return JSTemporalPlainTime.create(this.ctx, plainDateTime.getHour(), plainDateTime.getMinute(), plainDateTime.getSecond(), plainDateTime.getMillisecond(), plainDateTime.getMicrosecond(), plainDateTime.getNanosecond(), this.errorBranch);
            }
            if (this.isPlainDateTimeProfile.profile(JSTemporalPlainDateTime.isJSTemporalPlainDateTime(itemObj))) {
                JSTemporalPlainDateTimeObject dt = (JSTemporalPlainDateTimeObject)itemObj;
                return JSTemporalPlainTime.create(this.ctx, dt.getHour(), dt.getMinute(), dt.getSecond(), dt.getMillisecond(), dt.getMicrosecond(), dt.getNanosecond(), this.errorBranch);
            }
            JSDynamicObject calendar = getTemporalCalendarNode.executeDynamicObject(itemObj);
            if (!toStringNode.executeString(calendar).equals(TemporalConstants.ISO8601)) {
                this.errorBranch.enter();
                throw TemporalErrors.createRangeErrorTemporalISO8601Expected();
            }
            JSTemporalDateTimeRecord result = TemporalUtil.toTemporalTimeRecord(itemObj);
            result2 = TemporalUtil.regulateTime(result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond(), overflow);
        } else {
            TruffleString string = toStringNode.executeString(item);
            JSTemporalDateTimeRecord result = TemporalUtil.parseTemporalTimeString(string);
            assert (TemporalUtil.isValidTime(result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond()));
            if (result.hasCalendar() && !toStringNode.executeString(result.getCalendar()).equals(TemporalConstants.ISO8601)) {
                throw TemporalErrors.createRangeErrorTemporalISO8601Expected();
            }
            result2 = JSTemporalDurationRecord.create(result);
        }
        return JSTemporalPlainTime.create(this.ctx, TemporalUtil.dtoi(result2.getHours()), TemporalUtil.dtoi(result2.getMinutes()), TemporalUtil.dtoi(result2.getSeconds()), TemporalUtil.dtoi(result2.getMilliseconds()), TemporalUtil.dtoi(result2.getMicroseconds()), TemporalUtil.dtoi(result2.getNanoseconds()), this.errorBranch);
    }
}

