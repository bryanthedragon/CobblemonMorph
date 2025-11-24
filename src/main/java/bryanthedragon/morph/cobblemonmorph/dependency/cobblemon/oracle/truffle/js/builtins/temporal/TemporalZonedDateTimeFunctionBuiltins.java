
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalZonedDateTimeFunctionBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalZonedDateTimeNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public class TemporalZonedDateTimeFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalZonedDateTimeFunction> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalZonedDateTimeFunctionBuiltins();

    protected TemporalZonedDateTimeFunctionBuiltins() {
        super(JSTemporalZonedDateTime.CLASS_NAME, TemporalZonedDateTimeFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalZonedDateTimeFunction builtinEnum) {
        switch (builtinEnum) {
            case from: {
                return TemporalZonedDateTimeFunctionBuiltinsFactory.JSTemporalZonedDateTimeFromNodeGen.create(context, builtin, TemporalZonedDateTimeFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case compare: {
                return TemporalZonedDateTimeFunctionBuiltinsFactory.JSTemporalZonedDateTimeCompareNodeGen.create(context, builtin, TemporalZonedDateTimeFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalZonedDateTimeCompareNode
    extends JSBuiltinNode {
        public JSTemporalZonedDateTimeCompareNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected int compare(Object obj1, Object obj2, @Cached(value="create(getContext())") ToTemporalZonedDateTimeNode toTemporalZonedDateTime) {
            JSTemporalZonedDateTimeObject one = (JSTemporalZonedDateTimeObject)toTemporalZonedDateTime.executeDynamicObject(obj1, Undefined.instance);
            JSTemporalZonedDateTimeObject two = (JSTemporalZonedDateTimeObject)toTemporalZonedDateTime.executeDynamicObject(obj2, Undefined.instance);
            return TemporalUtil.compareEpochNanoseconds(one.getNanoseconds(), two.getNanoseconds());
        }
    }

    public static abstract class JSTemporalZonedDateTimeFromNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        public JSTemporalZonedDateTimeFromNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject from(Object item, Object optionsParam, @Cached(value="create(getContext())") ToTemporalZonedDateTimeNode toTemporalZonedDateTime, @Cached TruffleString.EqualNode equalNode) {
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            if (JSTemporalZonedDateTime.isJSTemporalZonedDateTime(item)) {
                JSTemporalZonedDateTimeObject zdt = (JSTemporalZonedDateTimeObject)item;
                TemporalUtil.toTemporalOverflow(options, this.getOptionNode());
                TemporalUtil.toTemporalDisambiguation(options, this.getOptionNode(), equalNode);
                TemporalUtil.toTemporalOffset(options, TemporalConstants.REJECT, this.getOptionNode(), equalNode);
                return JSTemporalZonedDateTime.create(this.getContext(), this.getRealm(), zdt.getNanoseconds(), zdt.getTimeZone(), zdt.getCalendar());
            }
            return toTemporalZonedDateTime.executeDynamicObject(item, options);
        }
    }

    public static enum TemporalZonedDateTimeFunction implements BuiltinEnum<TemporalZonedDateTimeFunction>
    {
        from(1),
        compare(2);

        private final int length;

        private TemporalZonedDateTimeFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

