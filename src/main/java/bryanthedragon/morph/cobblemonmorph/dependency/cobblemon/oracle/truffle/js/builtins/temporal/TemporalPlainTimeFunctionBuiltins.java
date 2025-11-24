
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainTimeFunctionBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTimeObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public class TemporalPlainTimeFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalPlainTimeFunction> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalPlainTimeFunctionBuiltins();

    protected TemporalPlainTimeFunctionBuiltins() {
        super(JSTemporalPlainTime.CLASS_NAME, TemporalPlainTimeFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalPlainTimeFunction builtinEnum) {
        switch (builtinEnum) {
            case from: {
                return TemporalPlainTimeFunctionBuiltinsFactory.JSTemporalPlainTimeFromNodeGen.create(context, builtin, TemporalPlainTimeFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case compare: {
                return TemporalPlainTimeFunctionBuiltinsFactory.JSTemporalPlainTimeCompareNodeGen.create(context, builtin, TemporalPlainTimeFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalPlainTimeCompareNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        public JSTemporalPlainTimeCompareNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected int compare(Object obj1, Object obj2, @Cached(value="create(getContext())") ToTemporalTimeNode toTemporalTime) {
            JSTemporalPlainTimeObject time1 = (JSTemporalPlainTimeObject)toTemporalTime.executeDynamicObject(obj1, null);
            JSTemporalPlainTimeObject time2 = (JSTemporalPlainTimeObject)toTemporalTime.executeDynamicObject(obj2, null);
            return TemporalUtil.compareTemporalTime(time1.getHour(), time1.getMinute(), time1.getSecond(), time1.getMillisecond(), time1.getMicrosecond(), time1.getNanosecond(), time2.getHour(), time2.getMinute(), time2.getSecond(), time2.getMillisecond(), time2.getMicrosecond(), time2.getNanosecond());
        }
    }

    public static abstract class JSTemporalPlainTimeFromNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        public JSTemporalPlainTimeFromNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object from(Object item, Object options, @Cached(value="create(getContext())") ToTemporalTimeNode toTemporalTime) {
            JSDynamicObject normalizedOptions = this.getOptionsObject(options);
            TemporalUtil.Overflow overflow = TemporalUtil.toTemporalOverflow(normalizedOptions, this.getOptionNode());
            if (this.isObject(item) && JSTemporalPlainTime.isJSTemporalPlainTime(item)) {
                JSTemporalPlainTimeObject timeItem = (JSTemporalPlainTimeObject)item;
                return JSTemporalPlainTime.create(this.getContext(), timeItem.getHour(), timeItem.getMinute(), timeItem.getSecond(), timeItem.getMillisecond(), timeItem.getMicrosecond(), timeItem.getNanosecond(), this.errorBranch);
            }
            return toTemporalTime.executeDynamicObject(item, overflow);
        }
    }

    public static enum TemporalPlainTimeFunction implements BuiltinEnum<TemporalPlainTimeFunction>
    {
        from(1),
        compare(2);

        private final int length;

        private TemporalPlainTimeFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

