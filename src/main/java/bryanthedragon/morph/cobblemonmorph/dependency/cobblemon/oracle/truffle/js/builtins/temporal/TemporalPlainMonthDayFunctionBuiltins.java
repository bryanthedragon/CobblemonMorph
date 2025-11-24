
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainMonthDayFunctionBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.ToTemporalMonthDayNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDay;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public class TemporalPlainMonthDayFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalPlainMonthDayFunction> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalPlainMonthDayFunctionBuiltins();

    protected TemporalPlainMonthDayFunctionBuiltins() {
        super(JSTemporalPlainMonthDay.CLASS_NAME, TemporalPlainMonthDayFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalPlainMonthDayFunction builtinEnum) {
        switch (builtinEnum) {
            case from: {
                return TemporalPlainMonthDayFunctionBuiltinsFactory.JSTemporalPlainMonthDayFromNodeGen.create(context, builtin, TemporalPlainMonthDayFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalPlainMonthDayFromNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        public JSTemporalPlainMonthDayFromNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject from(Object item, Object optParam, @Cached(value="create(getContext())") ToTemporalMonthDayNode toTemporalMonthDayNode) {
            JSDynamicObject options = this.getOptionsObject(optParam);
            if (this.isObject(item) && JSTemporalPlainMonthDay.isJSTemporalPlainMonthDay(item)) {
                JSTemporalPlainMonthDayObject pmd = (JSTemporalPlainMonthDayObject)item;
                TemporalUtil.toTemporalOverflow(options, this.getOptionNode());
                return JSTemporalPlainMonthDay.create(this.getContext(), pmd.getMonth(), pmd.getDay(), pmd.getCalendar(), pmd.getYear(), this.errorBranch);
            }
            return toTemporalMonthDayNode.executeDynamicObject(item, options);
        }
    }

    public static enum TemporalPlainMonthDayFunction implements BuiltinEnum<TemporalPlainMonthDayFunction>
    {
        from(1);

        private final int length;

        private TemporalPlainMonthDayFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

