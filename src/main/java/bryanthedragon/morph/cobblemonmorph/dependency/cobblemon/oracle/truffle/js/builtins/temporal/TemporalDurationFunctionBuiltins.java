
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalDurationFunctionBuiltinsFactory;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalUnbalanceDurationRelativeNode;
import com.oracle.truffle.js.nodes.temporal.ToRelativeTemporalObjectNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDurationNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public class TemporalDurationFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalDurationFunction> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalDurationFunctionBuiltins();

    protected TemporalDurationFunctionBuiltins() {
        super(JSTemporalDuration.CLASS_NAME, TemporalDurationFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalDurationFunction builtinEnum) {
        switch (builtinEnum) {
            case from: {
                return TemporalDurationFunctionBuiltinsFactory.JSTemporalDurationFromNodeGen.create(context, builtin, TemporalDurationFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case compare: {
                return TemporalDurationFunctionBuiltinsFactory.JSTemporalDurationCompareNodeGen.create(context, builtin, TemporalDurationFunctionBuiltins.args().fixedArgs(3).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalDurationCompare
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalDurationCompare(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected int compare(Object oneParam, Object twoParam, Object optionsParam, @Cached(value="create(getContext())") ToRelativeTemporalObjectNode toRelativeTemporalObjectNode, @Cached(value="create(getContext())") TemporalUnbalanceDurationRelativeNode unbalanceDurationRelativeNode, @Cached(value="create(getContext())") ToTemporalDurationNode toTemporalDurationNode) {
            double days2;
            double days1;
            JSTemporalDurationObject one = (JSTemporalDurationObject)toTemporalDurationNode.executeDynamicObject(oneParam);
            JSTemporalDurationObject two = (JSTemporalDurationObject)toTemporalDurationNode.executeDynamicObject(twoParam);
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            JSDynamicObject relativeTo = toRelativeTemporalObjectNode.execute(options);
            double shift1 = TemporalUtil.calculateOffsetShift(this.getContext(), relativeTo, one.getYears(), one.getMonths(), one.getWeeks(), one.getDays(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
            double shift2 = TemporalUtil.calculateOffsetShift(this.getContext(), relativeTo, two.getYears(), two.getMonths(), two.getWeeks(), two.getDays(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
            if (one.getYears() != 0.0 || two.getYears() != 0.0 || one.getMonths() != 0.0 || two.getMonths() != 0.0 || one.getWeeks() != 0.0 || two.getWeeks() != 0.0) {
                JSTemporalDurationRecord balanceResult1 = unbalanceDurationRelativeNode.execute(one.getYears(), one.getMonths(), one.getWeeks(), one.getDays(), TemporalUtil.Unit.DAY, relativeTo);
                JSTemporalDurationRecord balanceResult2 = unbalanceDurationRelativeNode.execute(two.getYears(), two.getMonths(), two.getWeeks(), two.getDays(), TemporalUtil.Unit.DAY, relativeTo);
                days1 = balanceResult1.getDays();
                days2 = balanceResult2.getDays();
            } else {
                days1 = one.getDays();
                days2 = two.getDays();
            }
            double ns1 = TemporalUtil.totalDurationNanoseconds(days1, one.getHours(), one.getMinutes(), one.getSeconds(), one.getMilliseconds(), one.getMicroseconds(), one.getNanoseconds(), shift1);
            double ns2 = TemporalUtil.totalDurationNanoseconds(days2, two.getHours(), two.getMinutes(), two.getSeconds(), two.getMilliseconds(), two.getMicroseconds(), two.getNanoseconds(), shift2);
            if (ns1 > ns2) {
                return 1;
            }
            if (ns1 < ns2) {
                return -1;
            }
            return 0;
        }
    }

    public static abstract class JSTemporalDurationFrom
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalDurationFrom(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject from(Object item, @Cached(value="create(getContext())") ToTemporalDurationNode toTemporalDurationNode) {
            if (this.isObject(item) && JSTemporalDuration.isJSTemporalDuration(item)) {
                JSTemporalDurationObject duration = (JSTemporalDurationObject)item;
                return JSTemporalDuration.createTemporalDuration(this.getContext(), duration.getYears(), duration.getMonths(), duration.getWeeks(), duration.getDays(), duration.getHours(), duration.getMinutes(), duration.getSeconds(), duration.getMilliseconds(), duration.getMicroseconds(), duration.getNanoseconds(), this.errorBranch);
            }
            return toTemporalDurationNode.executeDynamicObject(item);
        }
    }

    public static enum TemporalDurationFunction implements BuiltinEnum<TemporalDurationFunction>
    {
        from(1),
        compare(2);

        private final int length;

        private TemporalDurationFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

