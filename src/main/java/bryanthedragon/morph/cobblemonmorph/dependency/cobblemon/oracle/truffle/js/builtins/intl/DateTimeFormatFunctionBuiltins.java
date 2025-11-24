
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.intl.SupportedLocalesOfNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormat;

public final class DateTimeFormatFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<DateTimeFormatFunction> {
    public static final JSBuiltinsContainer BUILTINS = new DateTimeFormatFunctionBuiltins();

    protected DateTimeFormatFunctionBuiltins() {
        super(JSDateTimeFormat.CLASS_NAME, DateTimeFormatFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, DateTimeFormatFunction builtinEnum) {
        switch (builtinEnum) {
            case supportedLocalesOf: {
                return SupportedLocalesOfNodeGen.create(context, builtin, DateTimeFormatFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static enum DateTimeFormatFunction implements BuiltinEnum<DateTimeFormatFunction>
    {
        supportedLocalesOf(1);

        private final int length;

        private DateTimeFormatFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

