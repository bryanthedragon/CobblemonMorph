
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNodeGen;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.List;

public abstract class TemporalCalendarFieldsNode
extends JavaScriptBaseNode {
    private final JSContext ctx;
    @Node.Child
    private GetMethodNode getMethodFieldsNode;
    @Node.Child
    private JSFunctionCallNode callFieldsNode;
    private final ConditionProfile fieldsUndefined = ConditionProfile.createBinaryProfile();

    protected TemporalCalendarFieldsNode(JSContext ctx) {
        this.ctx = ctx;
        this.getMethodFieldsNode = GetMethodNode.create(ctx, TemporalConstants.FIELDS);
    }

    public static TemporalCalendarFieldsNode create(JSContext ctx) {
        return TemporalCalendarFieldsNodeGen.create(ctx);
    }

    public abstract List<TruffleString> execute(JSDynamicObject var1, List<TruffleString> var2);

    @Specialization
    protected List<TruffleString> calendarFields(JSDynamicObject calendar, List<TruffleString> strings) {
        Object fields = this.getMethodFieldsNode.executeWithTarget(calendar);
        if (this.fieldsUndefined.profile(fields == Undefined.instance)) {
            return strings;
        }
        JSDynamicObject fieldsArray = JSArray.createConstant(this.ctx, this.getRealm(), Boundaries.listToArray(strings));
        fieldsArray = this.callFields(fields, calendar, new Object[]{fieldsArray});
        return TemporalUtil.iterableToListOfTypeString(fieldsArray);
    }

    private JSDynamicObject callFields(Object fieldsFn, JSDynamicObject calendar, Object[] args) {
        if (this.callFieldsNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callFieldsNode = this.insert(JSFunctionCallNode.createCall());
        }
        return TemporalUtil.toDynamicObject(this.callFieldsNode.executeCall(JSArguments.create(calendar, fieldsFn, args)));
    }
}

