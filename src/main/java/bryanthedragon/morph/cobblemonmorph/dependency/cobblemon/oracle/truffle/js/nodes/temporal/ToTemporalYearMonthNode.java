
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
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.TemporalYearMonthFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalYearMonthNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDateTimeRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonth;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonthObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.List;

public abstract class ToTemporalYearMonthNode
extends JavaScriptBaseNode {
    private final ConditionProfile isObjectProfile = ConditionProfile.createBinaryProfile();
    protected final JSContext ctx;

    protected ToTemporalYearMonthNode(JSContext context) {
        this.ctx = context;
    }

    public static ToTemporalYearMonthNode create(JSContext context) {
        return ToTemporalYearMonthNodeGen.create(context);
    }

    public abstract JSTemporalPlainYearMonthObject executeDynamicObject(Object var1, JSDynamicObject var2);

    @Specialization
    public JSTemporalPlainYearMonthObject toTemporalYearMonth(Object item, JSDynamicObject options, @Cached BranchProfile errorBranch, @Cached(value="create()") IsObjectNode isObjectNode, @Cached(value="create()") JSToStringNode toStringNode, @Cached(value="create(ctx)") GetTemporalCalendarWithISODefaultNode getTemporalCalendarWithISODefaultNode, @Cached(value="create(ctx)") ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode, @Cached TemporalGetOptionNode getOptionNode, @Cached(value="create(ctx)") TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode, @Cached(value="create(ctx)") TemporalCalendarFieldsNode calendarFieldsNode) {
        assert (options != null);
        if (this.isObjectProfile.profile(isObjectNode.executeBoolean(item))) {
            JSDynamicObject itemObj = (JSDynamicObject)item;
            if (JSTemporalPlainYearMonth.isJSTemporalPlainYearMonth(itemObj)) {
                return (JSTemporalPlainYearMonthObject)itemObj;
            }
            JSDynamicObject calendar = getTemporalCalendarWithISODefaultNode.executeDynamicObject(itemObj);
            List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listMMCY);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.ctx, itemObj, fieldNames, TemporalUtil.listEmpty);
            return yearMonthFromFieldsNode.execute(calendar, fields, options);
        }
        TemporalUtil.toTemporalOverflow(options, getOptionNode);
        TruffleString string = toStringNode.executeString(item);
        JSTemporalDateTimeRecord result = TemporalUtil.parseTemporalYearMonthString(string);
        JSDynamicObject calendar = toTemporalCalendarWithISODefaultNode.executeDynamicObject(result.getCalendar());
        JSTemporalPlainYearMonthObject result2 = JSTemporalPlainYearMonth.create(this.ctx, result.getYear(), result.getMonth(), calendar, result.getDay(), errorBranch);
        return yearMonthFromFieldsNode.execute(calendar, result2, Undefined.instance);
    }
}

