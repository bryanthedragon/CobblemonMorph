
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
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.TemporalMonthDayFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalMonthDayNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDateTimeRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDay;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDayObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonth;
import com.oracle.truffle.js.runtime.builtins.temporal.TemporalCalendar;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.List;

public abstract class ToTemporalMonthDayNode
extends JavaScriptBaseNode {
    @Node.Child
    private PropertyGetNode getMonthNode;
    @Node.Child
    private PropertyGetNode getMonthCodeNode;
    @Node.Child
    private PropertyGetNode getYearNode;
    @Node.Child
    private PropertyGetNode getCalendarNode;
    protected final ConditionProfile isObjectProfile = ConditionProfile.create();
    protected final ConditionProfile setReferenceYear = ConditionProfile.create();
    protected final ConditionProfile returnPlainMonthDay = ConditionProfile.create();
    protected final ConditionProfile getCalendarPath = ConditionProfile.create();
    protected final JSContext ctx;

    protected ToTemporalMonthDayNode(JSContext context) {
        this.ctx = context;
    }

    public static ToTemporalMonthDayNode create(JSContext context) {
        return ToTemporalMonthDayNodeGen.create(context);
    }

    public abstract JSTemporalPlainMonthDayObject executeDynamicObject(Object var1, JSDynamicObject var2);

    @Specialization
    public JSTemporalPlainMonthDayObject toTemporalMonthDay(Object item, JSDynamicObject options, @Cached BranchProfile errorBranch, @Cached(value="create()") IsObjectNode isObjectNode, @Cached(value="create()") JSToStringNode toStringNode, @Cached(value="create(ctx)") ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode, @Cached(value="create(ctx)") TemporalMonthDayFromFieldsNode monthDayFromFieldsNode, @Cached(value="create(ctx)") TemporalCalendarFieldsNode calendarFieldsNode) {
        int referenceISOYear = 1972;
        if (this.isObjectProfile.profile(isObjectNode.executeBoolean(item))) {
            JSDynamicObject itemObj = (JSDynamicObject)item;
            if (JSTemporalPlainMonthDay.isJSTemporalPlainMonthDay(itemObj)) {
                return (JSTemporalPlainMonthDayObject)itemObj;
            }
            JSDynamicObject calendar = null;
            boolean calendarAbsent = false;
            if (this.getCalendarPath.profile(JSTemporalPlainDate.isJSTemporalPlainDate(itemObj) || JSTemporalPlainDateTime.isJSTemporalPlainDateTime(itemObj) || JSTemporalPlainTime.isJSTemporalPlainTime(itemObj) || JSTemporalPlainYearMonth.isJSTemporalPlainYearMonth(itemObj) || TemporalUtil.isTemporalZonedDateTime(itemObj))) {
                assert (itemObj instanceof TemporalCalendar);
                calendar = ((TemporalCalendar)((Object)itemObj)).getCalendar();
                calendarAbsent = false;
            } else {
                Object calendarObj = this.getCalendar(itemObj);
                calendarAbsent = calendarObj == Undefined.instance;
                calendar = toTemporalCalendarWithISODefaultNode.executeDynamicObject(calendarObj);
            }
            List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listDMMCY);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.ctx, itemObj, fieldNames, TemporalUtil.listEmpty);
            if (this.getMonthNode == null || this.getMonthCodeNode == null || this.getYearNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getMonthNode = this.insert(PropertyGetNode.create(TemporalConstants.MONTH, this.ctx));
                this.getMonthCodeNode = this.insert(PropertyGetNode.create(TemporalConstants.MONTH_CODE, this.ctx));
                this.getYearNode = this.insert(PropertyGetNode.create(TemporalConstants.YEAR, this.ctx));
            }
            Object month = this.getMonthNode.getValue(fields);
            Object monthCode = this.getMonthCodeNode.getValue(fields);
            Object year = this.getYearNode.getValue(fields);
            if (this.setReferenceYear.profile(calendarAbsent && month != Undefined.instance && monthCode == Undefined.instance && year == Undefined.instance)) {
                TemporalUtil.createDataPropertyOrThrow(this.ctx, fields, TemporalConstants.YEAR, referenceISOYear);
            }
            return monthDayFromFieldsNode.execute(calendar, fields, options);
        }
        TemporalUtil.toTemporalOverflow(options, TemporalGetOptionNode.getUncached());
        TruffleString string = toStringNode.executeString(item);
        JSTemporalDateTimeRecord result = TemporalUtil.parseTemporalMonthDayString(string);
        JSDynamicObject calendar = toTemporalCalendarWithISODefaultNode.executeDynamicObject(result.getCalendar());
        if (this.returnPlainMonthDay.profile(result.getYear() == Integer.MIN_VALUE)) {
            return JSTemporalPlainMonthDay.create(this.ctx, result.getMonth(), result.getDay(), calendar, referenceISOYear, errorBranch);
        }
        JSTemporalPlainMonthDayObject result2 = JSTemporalPlainMonthDay.create(this.ctx, result.getMonth(), result.getDay(), calendar, referenceISOYear, errorBranch);
        return monthDayFromFieldsNode.execute(calendar, result2, Undefined.instance);
    }

    private Object getCalendar(JSDynamicObject obj) {
        if (this.getCalendarNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getCalendarNode = this.insert(PropertyGetNode.create(TemporalConstants.CALENDAR, this.ctx));
        }
        return this.getCalendarNode.getValue(obj);
    }
}

