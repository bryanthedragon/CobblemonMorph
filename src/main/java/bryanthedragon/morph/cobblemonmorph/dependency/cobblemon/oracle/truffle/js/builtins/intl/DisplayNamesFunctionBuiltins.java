
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.intl.SupportedLocalesOfNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSDisplayNames;

public final class DisplayNamesFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<DisplayNamesFunction> {
    public static final JSBuiltinsContainer BUILTINS = new DisplayNamesFunctionBuiltins();

    protected DisplayNamesFunctionBuiltins() {
        super(JSDisplayNames.CLASS_NAME, DisplayNamesFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, DisplayNamesFunction builtinEnum) {
        switch (builtinEnum) {
            case supportedLocalesOf: {
                return SupportedLocalesOfNodeGen.create(context, builtin, DisplayNamesFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static enum DisplayNamesFunction implements BuiltinEnum<DisplayNamesFunction>
    {
        supportedLocalesOf(1);

        private final int length;

        private DisplayNamesFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

