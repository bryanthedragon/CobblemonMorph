
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNodeGen;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.List;

@GenerateUncached
public abstract class TemporalGetOptionNode
extends JavaScriptBaseNode {
    protected TemporalGetOptionNode() {
    }

    public static TemporalGetOptionNode create() {
        return TemporalGetOptionNodeGen.create();
    }

    public static TemporalGetOptionNode getUncached() {
        return TemporalGetOptionNodeGen.getUncached();
    }

    public abstract Object execute(JSDynamicObject var1, TruffleString var2, TemporalUtil.OptionType var3, List<?> var4, Object var5);

    @Specialization
    protected Object getOption(JSDynamicObject options, TruffleString property, TemporalUtil.OptionType types, List<?> values, Object fallback, @Cached BranchProfile errorBranch, @Cached ConditionProfile isFallbackProfile, @Cached JSToBooleanNode toBooleanNode, @Cached(uncached="createEmptyToString()") JSToStringNode toStringNode, @Cached(uncached="createEmptyToNumber()") JSToNumberNode toNumberNode) {
        assert (JSRuntime.isObject(options));
        Object value2 = JSObject.get(options, property);
        if (isFallbackProfile.profile(value2 == Undefined.instance)) {
            return fallback;
        }
        TemporalUtil.OptionType type = value2 instanceof Boolean && types.allowsBoolean() ? TemporalUtil.OptionType.BOOLEAN : (Strings.isTString(value2) && types.allowsString() ? TemporalUtil.OptionType.STRING : (JSRuntime.isNumber(value2) && types.allowsNumber() ? TemporalUtil.OptionType.NUMBER : types.getLast()));
        if (type.allowsBoolean()) {
            value2 = toBooleanNode.executeBoolean(value2);
        } else if (type.allowsNumber()) {
            Object object = value2 = toNumberNode == null ? (Number)JSRuntime.toNumber(value2) : (Number)toNumberNode.executeNumber(value2);
            if (JSRuntime.isNaN(value2)) {
                errorBranch.enter();
                throw TemporalErrors.createRangeErrorNumberIsNaN();
            }
        } else if (type.allowsString()) {
            Object object = value2 = toStringNode == null ? JSRuntime.toString(value2) : toStringNode.executeString(value2);
        }
        if (value2 != Undefined.instance && values != null && !Boundaries.listContainsUnchecked(values, value2)) {
            errorBranch.enter();
            throw TemporalErrors.createRangeErrorOptionsNotContained(values, value2);
        }
        return value2;
    }

    protected JSToStringNode createEmptyToString() {
        return null;
    }

    protected JSToNumberNode createEmptyToNumber() {
        return null;
    }
}

