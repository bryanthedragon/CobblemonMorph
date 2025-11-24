
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.BigIntPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.intl.InitializeNumberFormatNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormatObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class BigIntPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<BigIntPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new BigIntPrototypeBuiltins();

    protected BigIntPrototypeBuiltins() {
        super(JSBigInt.PROTOTYPE_NAME, BigIntPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, BigIntPrototype builtinEnum) {
        switch (builtinEnum) {
            case toLocaleString: {
                if (context.isOptionIntl402()) {
                    return BigIntPrototypeBuiltinsFactory.JSBigIntToLocaleStringIntlNodeGen.create(context, builtin, BigIntPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
                }
                return BigIntPrototypeBuiltinsFactory.JSBigIntToLocaleStringNodeGen.create(context, builtin, BigIntPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toString: {
                return BigIntPrototypeBuiltinsFactory.JSBigIntToStringNodeGen.create(context, builtin, BigIntPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case valueOf: {
                return BigIntPrototypeBuiltinsFactory.JSBigIntValueOfNodeGen.create(context, builtin, BigIntPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSBigIntValueOfNode
    extends JSBigIntOperation {
        public JSBigIntValueOfNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected BigInt valueOfBigInt(BigInt thisObj) {
            return thisObj;
        }

        @Specialization(guards={"isJSBigInt(thisObj)"})
        protected BigInt valueOf(JSDynamicObject thisObj) {
            return JSBigInt.valueOf(thisObj);
        }

        @Fallback
        protected void valueOf(Object thisObj) {
            throw Errors.createTypeError("BigInt.prototype.valueOf requires that 'this' be a BigInt");
        }
    }

    public static abstract class JSBigIntToLocaleStringNode
    extends JSBigIntOperation {
        public JSBigIntToLocaleStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected TruffleString toLocaleStringBigInt(BigInt thisObj) {
            return JSBigIntToLocaleStringNode.toLocaleStringImpl(thisObj);
        }

        @Specialization(guards={"isJSBigInt(thisObj)"})
        protected TruffleString toLocaleStringJSBigInt(JSDynamicObject thisObj) {
            return JSBigIntToLocaleStringNode.toLocaleStringImpl(this.getBigIntValue(thisObj));
        }

        private static TruffleString toLocaleStringImpl(BigInt bi) {
            return Strings.fromBigInt(bi);
        }

        @Fallback
        protected Object failForNonBigInts(Object thisObject) {
            throw this.noBigIntFailure(thisObject);
        }
    }

    public static abstract class JSBigIntToLocaleStringIntlNode
    extends JSBigIntOperation {
        @Node.Child
        InitializeNumberFormatNode initNumberFormatNode;

        public JSBigIntToLocaleStringIntlNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.initNumberFormatNode = InitializeNumberFormatNode.createInitalizeNumberFormatNode(context);
        }

        @CompilerDirectives.TruffleBoundary
        private JSNumberFormatObject createNumberFormat(Object locales, Object options) {
            JSNumberFormatObject numberFormatObj = JSNumberFormat.create(this.getContext(), this.getRealm());
            this.initNumberFormatNode.executeInit(numberFormatObj, locales, options);
            return numberFormatObj;
        }

        @Specialization
        protected TruffleString bigIntToLocaleString(BigInt thisObj, Object locales, Object options) {
            JSNumberFormatObject numberFormatObj = this.createNumberFormat(locales, options);
            return JSNumberFormat.format(numberFormatObj, thisObj);
        }

        @Specialization(guards={"isJSBigInt(thisObj)"})
        protected TruffleString jsBigIntToLocaleString(JSDynamicObject thisObj, Object locales, Object options) {
            JSNumberFormatObject numberFormatObj = this.createNumberFormat(locales, options);
            return JSNumberFormat.format(numberFormatObj, this.getBigIntValue(thisObj));
        }

        @Fallback
        protected Object failForNonBigInts(Object notANumber, Object locales, Object options) {
            throw this.noBigIntFailure(notANumber);
        }
    }

    public static abstract class JSBigIntToStringNode
    extends JSBigIntOperation {
        private final BranchProfile radixErrorBranch = BranchProfile.create();

        public JSBigIntToStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isUndefined(radix)"})
        protected TruffleString toStringBigIntRadix10(BigInt thisObj, Object radix) {
            return this.toStringImpl(thisObj, 10);
        }

        @Specialization(guards={"!isUndefined(radix)"})
        protected TruffleString toStringBigInt(BigInt thisObj, Object radix) {
            return this.toStringImpl(thisObj, radix);
        }

        @Specialization(guards={"isJSBigInt(thisObj)", "isUndefined(radix)"})
        protected TruffleString toStringRadix10(JSDynamicObject thisObj, Object radix) {
            return this.toStringImpl(JSBigInt.valueOf(thisObj), 10);
        }

        @Specialization(guards={"isJSBigInt(thisObj)", "!isUndefined(radix)"})
        protected TruffleString toString(JSDynamicObject thisObj, Object radix) {
            return this.toStringImpl(JSBigInt.valueOf(thisObj), radix);
        }

        @Fallback
        protected void toStringNoBigInt(Object thisObj, Object radix) {
            throw Errors.createTypeError("BigInt.prototype.toString requires that 'this' be a BigInt");
        }

        private TruffleString toStringImpl(BigInt numberVal, Object radix) {
            int radixVal = this.toIntegerAsInt(radix);
            if (radixVal < 2 || radixVal > 36) {
                this.radixErrorBranch.enter();
                throw Errors.createRangeError("toString() expects radix in range 2-36");
            }
            return Strings.fromBigInt(numberVal, radixVal);
        }
    }

    public static abstract class JSBigIntOperation
    extends JSBuiltinNode {
        @Node.Child
        private JSToIntegerAsIntNode toIntegerNode;

        public JSBigIntOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        protected JSException noBigIntFailure(Object value2) {
            throw Errors.createTypeError(JSRuntime.safeToString(value2) + " is not a BigInt");
        }

        protected BigInt getBigIntValue(JSDynamicObject obj) {
            return JSBigInt.valueOf(obj);
        }

        protected int toIntegerAsInt(Object target) {
            if (this.toIntegerNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toIntegerNode = this.insert(JSToIntegerAsIntNode.create());
            }
            return this.toIntegerNode.executeInt(target);
        }
    }

    public static enum BigIntPrototype implements BuiltinEnum<BigIntPrototype>
    {
        toLocaleString(0),
        toString(0),
        valueOf(0);

        private final int length;

        private BigIntPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

