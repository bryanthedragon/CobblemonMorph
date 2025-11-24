
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.intl.SupportedLocalesOfNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSPluralRules;

public final class PluralRulesFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<PluralRulesFunction> {
    public static final JSBuiltinsContainer BUILTINS = new PluralRulesFunctionBuiltins();

    protected PluralRulesFunctionBuiltins() {
        super(JSPluralRules.CLASS_NAME, PluralRulesFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, PluralRulesFunction builtinEnum) {
        switch (builtinEnum) {
            case supportedLocalesOf: {
                return SupportedLocalesOfNodeGen.create(context, builtin, PluralRulesFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static enum PluralRulesFunction implements BuiltinEnum<PluralRulesFunction>
    {
        supportedLocalesOf(1);

        private final int length;

        private PluralRulesFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

