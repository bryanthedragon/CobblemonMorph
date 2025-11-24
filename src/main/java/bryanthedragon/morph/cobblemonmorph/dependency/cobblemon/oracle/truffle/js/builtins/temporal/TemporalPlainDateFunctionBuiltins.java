
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDateFunctionBuiltinsFactory;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public class TemporalPlainDateFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalPlainDateFunction> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalPlainDateFunctionBuiltins();

    protected TemporalPlainDateFunctionBuiltins() {
        super(JSTemporalPlainDate.CLASS_NAME, TemporalPlainDateFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalPlainDateFunction builtinEnum) {
        switch (builtinEnum) {
            case from: {
                return TemporalPlainDateFunctionBuiltinsFactory.JSTemporalPlainDateFromNodeGen.create(context, builtin, TemporalPlainDateFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case compare: {
                return TemporalPlainDateFunctionBuiltinsFactory.JSTemporalPlainDateCompareNodeGen.create(context, builtin, TemporalPlainDateFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalPlainDateCompareNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        public JSTemporalPlainDateCompareNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected int compare(Object obj1, Object obj2, @Cached(value="create(getContext())") ToTemporalDateNode toTemporalDate) {
            JSTemporalPlainDateObject one = toTemporalDate.executeDynamicObject(obj1, Undefined.instance);
            JSTemporalPlainDateObject two = toTemporalDate.executeDynamicObject(obj2, Undefined.instance);
            return TemporalUtil.compareISODate(one.getYear(), one.getMonth(), one.getDay(), two.getYear(), two.getMonth(), two.getDay());
        }
    }

    public static abstract class JSTemporalPlainDateFromNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        public JSTemporalPlainDateFromNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object from(Object item, Object optParam, @Cached(value="create(getContext())") ToTemporalDateNode toTemporalDate) {
            JSDynamicObject options = this.getOptionsObject(optParam);
            if (this.isObject(item) && JSTemporalPlainDate.isJSTemporalPlainDate(item)) {
                JSTemporalPlainDateObject dtItem = (JSTemporalPlainDateObject)item;
                TemporalUtil.toTemporalOverflow(options, this.getOptionNode());
                return JSTemporalPlainDate.create(this.getContext(), dtItem.getYear(), dtItem.getMonth(), dtItem.getDay(), dtItem.getCalendar(), this.errorBranch);
            }
            return toTemporalDate.executeDynamicObject(item, options);
        }
    }

    public static enum TemporalPlainDateFunction implements BuiltinEnum<TemporalPlainDateFunction>
    {
        from(1),
        compare(2);

        private final int length;

        private TemporalPlainDateFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

