
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.intl.NumberFormatPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.intl.ToIntlMathematicalValue;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormatObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class NumberFormatPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<NumberFormatPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new NumberFormatPrototypeBuiltins();

    protected NumberFormatPrototypeBuiltins() {
        super(JSNumberFormat.PROTOTYPE_NAME, NumberFormatPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, NumberFormatPrototype builtinEnum) {
        switch (builtinEnum) {
            case resolvedOptions: {
                return NumberFormatPrototypeBuiltinsFactory.JSNumberFormatResolvedOptionsNodeGen.create(context, builtin, NumberFormatPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case formatToParts: {
                return NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatToPartsNodeGen.create(context, builtin, NumberFormatPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case formatRange: {
                return NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeNodeGen.create(context, builtin, NumberFormatPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case formatRangeToParts: {
                return NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeToPartsNodeGen.create(context, builtin, NumberFormatPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSNumberFormatFormatRangeToPartsNode
    extends JSBuiltinNode {
        public JSNumberFormatFormatRangeToPartsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doFormatRangeToParts(JSNumberFormatObject numberFormat, Object start2, Object end2, @Cached(value="create(true)") ToIntlMathematicalValue startToIntlMVNode, @Cached(value="create(true)") ToIntlMathematicalValue endToIntlMVNode, @Cached BranchProfile errorBranch) {
            if (start2 == Undefined.instance || end2 == Undefined.instance) {
                errorBranch.enter();
                throw Errors.createTypeError("invalid range");
            }
            Number x = startToIntlMVNode.executeNumber(start2);
            Number y = endToIntlMVNode.executeNumber(end2);
            return JSNumberFormat.formatRangeToParts(this.getContext(), this.getRealm(), numberFormat, x, y);
        }

        @Fallback
        public Object throwTypeError(Object bummer, Object start2, Object end2) {
            throw Errors.createTypeErrorTypeXExpected(JSNumberFormat.CLASS_NAME);
        }
    }

    public static abstract class JSNumberFormatFormatRangeNode
    extends JSBuiltinNode {
        public JSNumberFormatFormatRangeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public TruffleString doFormatRange(JSNumberFormatObject numberFormat, Object start2, Object end2, @Cached(value="create(true)") ToIntlMathematicalValue startToIntlMVNode, @Cached(value="create(true)") ToIntlMathematicalValue endToIntlMVNode, @Cached BranchProfile errorBranch) {
            if (start2 == Undefined.instance || end2 == Undefined.instance) {
                errorBranch.enter();
                throw Errors.createTypeError("invalid range");
            }
            Number x = startToIntlMVNode.executeNumber(start2);
            Number y = endToIntlMVNode.executeNumber(end2);
            return JSNumberFormat.formatRange(numberFormat, x, y);
        }

        @Fallback
        public Object throwTypeError(Object bummer, Object start2, Object end2) {
            throw Errors.createTypeErrorTypeXExpected(JSNumberFormat.CLASS_NAME);
        }
    }

    public static abstract class JSNumberFormatFormatToPartsNode
    extends JSBuiltinNode {
        public JSNumberFormatFormatToPartsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doFormatToParts(JSNumberFormatObject numberFormat, Object value2) {
            return JSNumberFormat.formatToParts(this.getContext(), this.getRealm(), numberFormat, value2);
        }

        @Fallback
        public Object throwTypeError(Object bummer, Object value2) {
            throw Errors.createTypeErrorTypeXExpected(JSNumberFormat.CLASS_NAME);
        }
    }

    public static abstract class JSNumberFormatResolvedOptionsNode
    extends JSBuiltinNode {
        public JSNumberFormatResolvedOptionsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doResolvedOptions(JSNumberFormatObject numberFormat) {
            return JSNumberFormat.resolvedOptions(this.getContext(), this.getRealm(), numberFormat);
        }

        @Fallback
        public Object throwTypeError(Object bummer) {
            throw Errors.createTypeErrorTypeXExpected(JSNumberFormat.CLASS_NAME);
        }
    }

    public static enum NumberFormatPrototype implements BuiltinEnum<NumberFormatPrototype>
    {
        resolvedOptions(0),
        formatToParts(1),
        formatRange(2),
        formatRangeToParts(2);

        private final int length;

        private NumberFormatPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public int getECMAScriptVersion() {
            switch (this) {
                case formatToParts: {
                    return 9;
                }
                case formatRange: 
                case formatRangeToParts: {
                    return 14;
                }
            }
            return BuiltinEnum.super.getECMAScriptVersion();
        }
    }
}

