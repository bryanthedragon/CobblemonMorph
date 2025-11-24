
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.temporal.TemporalMonthDayFromFieldsNodeGen;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public abstract class TemporalMonthDayFromFieldsNode
extends JavaScriptBaseNode {
    protected final BranchProfile errorBranch = BranchProfile.create();
    @Node.Child
    private GetMethodNode getMethodMonthDayFromFieldsNode;
    @Node.Child
    private JSFunctionCallNode callMonthDayFromFieldsNode;

    protected TemporalMonthDayFromFieldsNode(JSContext ctx) {
        this.getMethodMonthDayFromFieldsNode = GetMethodNode.create(ctx, TemporalUtil.MONTH_DAY_FROM_FIELDS);
        this.callMonthDayFromFieldsNode = JSFunctionCallNode.createCall();
    }

    public static TemporalMonthDayFromFieldsNode create(JSContext ctx) {
        return TemporalMonthDayFromFieldsNodeGen.create(ctx);
    }

    public abstract JSTemporalPlainMonthDayObject execute(JSDynamicObject var1, JSDynamicObject var2, JSDynamicObject var3);

    @Specialization
    protected JSTemporalPlainMonthDayObject monthDayFromFields(JSDynamicObject calendar, JSDynamicObject fields, JSDynamicObject options) {
        assert (options != null);
        Object fn = this.getMethodMonthDayFromFieldsNode.executeWithTarget(calendar);
        Object monthDay = this.callMonthDayFromFieldsNode.executeCall(JSArguments.create(calendar, fn, fields, options));
        return this.requireTemporalMonthDay(monthDay);
    }

    public JSTemporalPlainMonthDayObject requireTemporalMonthDay(Object obj) {
        if (!(obj instanceof JSTemporalPlainMonthDayObject)) {
            this.errorBranch.enter();
            throw TemporalErrors.createTypeErrorTemporalPlainMonthDayExpected();
        }
        return (JSTemporalPlainMonthDayObject)obj;
    }
}

