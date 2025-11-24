
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalCalendarFunctionBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendar;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public class TemporalCalendarFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalCalendarFunction> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalCalendarFunctionBuiltins();

    protected TemporalCalendarFunctionBuiltins() {
        super(JSTemporalCalendar.CLASS_NAME, TemporalCalendarFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalCalendarFunction builtinEnum) {
        switch (builtinEnum) {
            case from: {
                return TemporalCalendarFunctionBuiltinsFactory.JSTemporalCalendarFromNodeGen.create(context, builtin, TemporalCalendarFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalCalendarFromNode
    extends JSBuiltinNode {
        public JSTemporalCalendarFromNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject from(Object item, @Cached(value="create(getContext())") ToTemporalCalendarNode toTemporalCalendar) {
            return toTemporalCalendar.executeDynamicObject(item);
        }
    }

    public static enum TemporalCalendarFunction implements BuiltinEnum<TemporalCalendarFunction>
    {
        from(1);

        private final int length;

        private TemporalCalendarFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

