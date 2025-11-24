
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.intl.SupportedLocalesOfNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormat;

public final class NumberFormatFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<NumberFormatFunction> {
    public static final JSBuiltinsContainer BUILTINS = new NumberFormatFunctionBuiltins();

    protected NumberFormatFunctionBuiltins() {
        super(JSNumberFormat.CLASS_NAME, NumberFormatFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, NumberFormatFunction builtinEnum) {
        switch (builtinEnum) {
            case supportedLocalesOf: {
                return SupportedLocalesOfNodeGen.create(context, builtin, NumberFormatFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static enum NumberFormatFunction implements BuiltinEnum<NumberFormatFunction>
    {
        supportedLocalesOf(1);

        private final int length;

        private NumberFormatFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

