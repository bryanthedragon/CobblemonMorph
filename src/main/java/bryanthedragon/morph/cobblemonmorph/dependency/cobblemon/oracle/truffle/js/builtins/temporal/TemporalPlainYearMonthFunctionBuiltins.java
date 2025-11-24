
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainYearMonthFunctionBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.ToTemporalYearMonthNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonth;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonthObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public class TemporalPlainYearMonthFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalPlainYearMonthFunction> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalPlainYearMonthFunctionBuiltins();

    protected TemporalPlainYearMonthFunctionBuiltins() {
        super(JSTemporalPlainYearMonth.CLASS_NAME, TemporalPlainYearMonthFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalPlainYearMonthFunction builtinEnum) {
        switch (builtinEnum) {
            case from: {
                return TemporalPlainYearMonthFunctionBuiltinsFactory.JSTemporalPlainYearMonthFromNodeGen.create(context, builtin, TemporalPlainYearMonthFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case compare: {
                return TemporalPlainYearMonthFunctionBuiltinsFactory.JSTemporalPlainYearMonthCompareNodeGen.create(context, builtin, TemporalPlainYearMonthFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalPlainYearMonthCompareNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        public JSTemporalPlainYearMonthCompareNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected int compare(Object one, Object two, @Cached(value="create(getContext())") ToTemporalYearMonthNode toTemporalYearMonthNode) {
            JSTemporalPlainYearMonthObject oneYM = toTemporalYearMonthNode.executeDynamicObject(one, Undefined.instance);
            JSTemporalPlainYearMonthObject twoYM = toTemporalYearMonthNode.executeDynamicObject(two, Undefined.instance);
            return TemporalUtil.compareISODate(oneYM.getYear(), oneYM.getMonth(), oneYM.getDay(), twoYM.getYear(), twoYM.getMonth(), twoYM.getDay());
        }
    }

    public static abstract class JSTemporalPlainYearMonthFromNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        public JSTemporalPlainYearMonthFromNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject from(Object item, Object optParam, @Cached(value="create(getContext())") ToTemporalYearMonthNode toTemporalYearMonthNode) {
            JSDynamicObject options = this.getOptionsObject(optParam);
            if (this.isObject(item) && JSTemporalPlainYearMonth.isJSTemporalPlainYearMonth(item)) {
                JSTemporalPlainYearMonthObject pmd = (JSTemporalPlainYearMonthObject)item;
                TemporalUtil.toTemporalOverflow(options, this.getOptionNode());
                return JSTemporalPlainYearMonth.create(this.getContext(), pmd.getYear(), pmd.getMonth(), pmd.getCalendar(), pmd.getDay(), this.errorBranch);
            }
            return toTemporalYearMonthNode.executeDynamicObject(item, options);
        }
    }

    public static enum TemporalPlainYearMonthFunction implements BuiltinEnum<TemporalPlainYearMonthFunction>
    {
        from(1),
        compare(2);

        private final int length;

        private TemporalPlainYearMonthFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

