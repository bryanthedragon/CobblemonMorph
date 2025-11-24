
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.intl.PluralRulesPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSPluralRules;
import com.oracle.truffle.js.runtime.builtins.intl.JSPluralRulesObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class PluralRulesPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<PluralRulesPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new PluralRulesPrototypeBuiltins();

    protected PluralRulesPrototypeBuiltins() {
        super(JSPluralRules.PROTOTYPE_NAME, PluralRulesPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, PluralRulesPrototype builtinEnum) {
        switch (builtinEnum) {
            case resolvedOptions: {
                return PluralRulesPrototypeBuiltinsFactory.JSPluralRulesResolvedOptionsNodeGen.create(context, builtin, PluralRulesPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case select: {
                return PluralRulesPrototypeBuiltinsFactory.JSPluralRulesSelectNodeGen.create(context, builtin, PluralRulesPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case selectRange: {
                return PluralRulesPrototypeBuiltinsFactory.JSPluralRulesSelectRangeNodeGen.create(context, builtin, PluralRulesPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSPluralRulesSelectRangeNode
    extends JSBuiltinNode {
        public JSPluralRulesSelectRangeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doSelectRange(JSPluralRulesObject pluralRules, Object start2, Object end2, @Cached JSToNumberNode startToNumber, @Cached JSToNumberNode endToNumber, @Cached BranchProfile errorBranch) {
            if (start2 == Undefined.instance || end2 == Undefined.instance) {
                errorBranch.enter();
                throw Errors.createTypeError("invalid range");
            }
            double x = JSRuntime.doubleValue(startToNumber.executeNumber(start2));
            double y = JSRuntime.doubleValue(endToNumber.executeNumber(end2));
            if (Double.isNaN(x) || Double.isNaN(y)) {
                errorBranch.enter();
                throw Errors.createRangeError("invalid range");
            }
            return JSPluralRules.selectRange(pluralRules, x, y);
        }

        @Fallback
        public Object throwTypeError(Object bummer, Object start2, Object end2) {
            throw Errors.createTypeErrorTypeXExpected(JSPluralRules.CLASS_NAME);
        }
    }

    public static abstract class JSPluralRulesSelectNode
    extends JSBuiltinNode {
        public JSPluralRulesSelectNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doSelect(JSPluralRulesObject pluralRules, Object value2) {
            return JSPluralRules.select(pluralRules, value2);
        }

        @Fallback
        public Object throwTypeError(Object bummer, Object value2) {
            throw Errors.createTypeErrorTypeXExpected(JSPluralRules.CLASS_NAME);
        }
    }

    public static abstract class JSPluralRulesResolvedOptionsNode
    extends JSBuiltinNode {
        public JSPluralRulesResolvedOptionsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doResolvedOptions(JSPluralRulesObject pluralRules) {
            return JSPluralRules.resolvedOptions(this.getContext(), this.getRealm(), pluralRules);
        }

        @Fallback
        public Object throwTypeError(Object bummer) {
            throw Errors.createTypeErrorTypeXExpected(JSPluralRules.CLASS_NAME);
        }
    }

    public static enum PluralRulesPrototype implements BuiltinEnum<PluralRulesPrototype>
    {
        resolvedOptions(0),
        select(1),
        selectRange(2);

        private final int length;

        private PluralRulesPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public int getECMAScriptVersion() {
            if (selectRange == this) {
                return 14;
            }
            return BuiltinEnum.super.getECMAScriptVersion();
        }
    }
}

