
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.intl.SupportedLocalesOfNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSListFormat;

public final class ListFormatFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<ListFormatFunction> {
    public static final JSBuiltinsContainer BUILTINS = new ListFormatFunctionBuiltins();

    protected ListFormatFunctionBuiltins() {
        super(JSListFormat.CLASS_NAME, ListFormatFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ListFormatFunction builtinEnum) {
        switch (builtinEnum) {
            case supportedLocalesOf: {
                return SupportedLocalesOfNodeGen.create(context, builtin, ListFormatFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static enum ListFormatFunction implements BuiltinEnum<ListFormatFunction>
    {
        supportedLocalesOf(1);

        private final int length;

        private ListFormatFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

