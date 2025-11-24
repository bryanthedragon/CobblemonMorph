
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.NumberPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.cast.JSDoubleToStringNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.intl.InitializeNumberFormatNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSNumberObject;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormatObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class NumberPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<NumberPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new NumberPrototypeBuiltins();

    protected NumberPrototypeBuiltins() {
        super(JSNumber.PROTOTYPE_NAME, NumberPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, NumberPrototype builtinEnum) {
        switch (builtinEnum) {
            case toExponential: {
                return NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.create(context, builtin, NumberPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toFixed: {
                return NumberPrototypeBuiltinsFactory.JSNumberToFixedNodeGen.create(context, builtin, NumberPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toLocaleString: {
                if (context.isOptionIntl402()) {
                    return NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringIntlNodeGen.create(context, builtin, NumberPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
                }
                return NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringNodeGen.create(context, builtin, NumberPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toPrecision: {
                return NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.create(context, builtin, NumberPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toString: {
                return NumberPrototypeBuiltinsFactory.JSNumberToStringNodeGen.create(context, builtin, NumberPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case valueOf: {
                return NumberPrototypeBuiltinsFactory.JSNumberValueOfNodeGen.create(context, builtin, NumberPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class JSNumberToPrecisionNode
    extends JSNumberOperation {
        private final BranchProfile precisionErrorBranch = BranchProfile.create();

        public JSNumberToPrecisionNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSNumber(thisNumber)", "isUndefined(precision)"})
        protected Object toPrecisionUndefined(JSDynamicObject thisNumber, Object precision, @Cached.Shared(value="toString") @Cached(value="create()") JSToStringNode toStringNode) {
            return toStringNode.executeString(thisNumber);
        }

        @Specialization(guards={"isJSNumber(thisNumber)", "!isUndefined(precision)"})
        protected Object toPrecision(JSDynamicObject thisNumber, Object precision, @Cached.Shared(value="toNumber") @Cached(value="create()") JSToNumberNode toNumberNode) {
            long lPrecision = JSRuntime.toInteger(toNumberNode.executeNumber(precision));
            double thisNumberVal = this.getDoubleValue(thisNumber);
            return this.toPrecisionIntl(thisNumberVal, lPrecision);
        }

        @Specialization(guards={"isJavaNumber(thisNumber)", "isUndefined(precision)"})
        protected Object toPrecisionPrimitiveUndefined(Object thisNumber, Object precision, @Cached.Shared(value="toString") @Cached(value="create()") JSToStringNode toStringNode) {
            return toStringNode.executeString(thisNumber);
        }

        @Specialization(guards={"isJavaNumber(thisNumber)", "!isUndefined(precision)"})
        protected Object toPrecisionPrimitive(Object thisNumber, Object precision, @Cached.Shared(value="toNumber") @Cached(value="create()") JSToNumberNode toNumberNode) {
            long lPrecision = JSRuntime.toInteger(toNumberNode.executeNumber(precision));
            double thisNumberVal = JSRuntime.doubleValue((Number)thisNumber);
            return this.toPrecisionIntl(thisNumberVal, lPrecision);
        }

        @Specialization(guards={"isForeignObject(thisNumber)", "isUndefined(precision)"}, limit="InteropLibraryLimit")
        protected Object toPrecisionForeignObjectUndefined(Object thisNumber, Object precision, @Cached(value="create()") JSToStringNode toStringNode, @CachedLibrary(value="thisNumber") InteropLibrary interop) {
            return toStringNode.executeString(this.getDoubleValue(interop, thisNumber));
        }

        @Specialization(guards={"isForeignObject(thisNumber)", "!isUndefined(precision)"}, limit="InteropLibraryLimit")
        protected Object toPrecisionForeignObject(Object thisNumber, Object precision, @Cached(value="create()") JSToNumberNode toNumberNode, @CachedLibrary(value="thisNumber") InteropLibrary interop) {
            double thisNumberVal = this.getDoubleValue(interop, thisNumber);
            long lPrecision = JSRuntime.toInteger(toNumberNode.executeNumber(precision));
            return this.toPrecisionIntl(thisNumberVal, lPrecision);
        }

        @Specialization(guards={"!isJSNumber(thisNumber)", "!isJavaNumber(thisNumber)", "!isForeignObject(thisNumber)"})
        protected Object toPrecisionOther(Object thisNumber, Object precision) {
            throw Errors.createTypeErrorNotANumber(thisNumber);
        }

        private Object toPrecisionIntl(double thisNumberVal, long lPrecision) {
            if (Double.isNaN(thisNumberVal)) {
                return Strings.NAN;
            }
            if (Double.isInfinite(thisNumberVal)) {
                return thisNumberVal < 0.0 ? Strings.NEGATIVE_INFINITY : Strings.INFINITY;
            }
            this.checkPrecision(lPrecision);
            return JSRuntime.formatDtoAPrecision(thisNumberVal, (int)lPrecision);
        }

        private void checkPrecision(long precision) {
            if (1L > precision || precision > (long)(this.getContext().getEcmaScriptVersion() >= 9 ? 100 : 20)) {
                this.precisionErrorBranch.enter();
                throw Errors.createRangeError("precision not in range");
            }
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class JSNumberToExponentialNode
    extends JSNumberOperation {
        public JSNumberToExponentialNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSNumber(thisNumber)", "isUndefined(fractionDigits)"})
        protected Object toExponentialUndefined(JSDynamicObject thisNumber, Object fractionDigits) {
            double doubleValue = this.getDoubleValue(thisNumber);
            return JSNumberToExponentialNode.toExponentialStandard(doubleValue);
        }

        @Specialization(guards={"isJSNumber(thisNumber)", "!isUndefined(fractionDigits)"})
        protected Object toExponential(JSDynamicObject thisNumber, Object fractionDigits, @Cached @Cached.Shared(value="digitsError") BranchProfile digitsErrorBranch, @Cached.Shared(value="toInt") @Cached(value="create()") JSToIntegerAsIntNode toIntegerNode) {
            double doubleValue = this.getDoubleValue(thisNumber);
            int digits = toIntegerNode.executeInt(fractionDigits);
            return this.toExponential(doubleValue, digits, digitsErrorBranch);
        }

        @Specialization(guards={"isJavaNumber(thisNumber)", "isUndefined(fractionDigits)"})
        protected Object toExponentialPrimitiveUndefined(Object thisNumber, Object fractionDigits) {
            double doubleValue = JSRuntime.doubleValue((Number)thisNumber);
            return JSNumberToExponentialNode.toExponentialStandard(doubleValue);
        }

        @Specialization(guards={"isJavaNumber(thisNumber)", "!isUndefined(fractionDigits)"})
        protected Object toExponentialPrimitive(Object thisNumber, Object fractionDigits, @Cached @Cached.Shared(value="digitsError") BranchProfile digitsErrorBranch, @Cached.Shared(value="toInt") @Cached(value="create()") JSToIntegerAsIntNode toIntegerNode) {
            double doubleValue = JSRuntime.doubleValue((Number)thisNumber);
            int digits = toIntegerNode.executeInt(fractionDigits);
            return this.toExponential(doubleValue, digits, digitsErrorBranch);
        }

        @Specialization(guards={"isForeignObject(thisNumber)", "isUndefined(fractionDigits)"}, limit="InteropLibraryLimit")
        protected Object toExponentialForeignObjectUndefined(Object thisNumber, Object fractionDigits, @CachedLibrary(value="thisNumber") InteropLibrary interop) {
            double doubleValue = this.getDoubleValue(interop, thisNumber);
            return JSNumberToExponentialNode.toExponentialStandard(doubleValue);
        }

        @Specialization(guards={"isForeignObject(thisNumber)", "!isUndefined(fractionDigits)"}, limit="InteropLibraryLimit")
        protected Object toExponentialForeignObject(Object thisNumber, Object fractionDigits, @Cached BranchProfile digitsErrorBranch, @Cached(value="create()") JSToIntegerAsIntNode toIntegerNode, @CachedLibrary(value="thisNumber") InteropLibrary interop) {
            double doubleValue = this.getDoubleValue(interop, thisNumber);
            int digits = toIntegerNode.executeInt(fractionDigits);
            return this.toExponential(doubleValue, digits, digitsErrorBranch);
        }

        @Specialization(guards={"!isJSNumber(thisNumber)", "!isJavaNumber(thisNumber)", "!isForeignObject(thisNumber)"})
        protected Object toExponentialOther(Object thisNumber, Object fractionDigits) {
            throw Errors.createTypeErrorNotANumber(thisNumber);
        }

        private static Object toExponentialStandard(double value2) {
            if (Double.isNaN(value2)) {
                return Strings.NAN;
            }
            if (Double.isInfinite(value2)) {
                return value2 < 0.0 ? Strings.NEGATIVE_INFINITY : Strings.INFINITY;
            }
            return JSRuntime.formatDtoAExponential(value2);
        }

        private Object toExponential(double value2, int digits, BranchProfile digitsErrorBranch) {
            if (Double.isNaN(value2)) {
                return Strings.NAN;
            }
            if (Double.isInfinite(value2)) {
                return value2 < 0.0 ? Strings.NEGATIVE_INFINITY : Strings.INFINITY;
            }
            this.checkDigits(digits, digitsErrorBranch);
            return JSRuntime.formatDtoAExponential(value2, digits);
        }

        private void checkDigits(int digits, BranchProfile digitsErrorBranch) {
            int maxDigits;
            int n = maxDigits = this.getContext().getEcmaScriptVersion() >= 9 ? 100 : 20;
            if (0 > digits || digits > maxDigits) {
                digitsErrorBranch.enter();
                throw JSNumberToExponentialNode.digitsRangeError(maxDigits);
            }
        }

        @CompilerDirectives.TruffleBoundary
        private static JSException digitsRangeError(int maxDigits) {
            return Errors.createRangeError("toExponential() fraction digits need to be in range 0-" + maxDigits);
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class JSNumberToFixedNode
    extends JSNumberOperation {
        private final BranchProfile digitsErrorBranch = BranchProfile.create();
        private final BranchProfile nanBranch = BranchProfile.create();
        private final ConditionProfile dtoaOrString = ConditionProfile.createBinaryProfile();
        @Node.Child
        protected JSDoubleToStringNode doubleToStringNode;

        protected JSNumberToFixedNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSNumber(thisNumber)"})
        protected Object toFixed(JSDynamicObject thisNumber, Object fractionDigits, @Cached.Shared(value="toInt") @Cached(value="create()") JSToIntegerAsIntNode toIntegerNode) {
            int digits = toIntegerNode.executeInt(fractionDigits);
            return this.toFixedIntl(this.getDoubleValue(thisNumber), digits);
        }

        @Specialization(guards={"isJavaNumber(thisNumber)"})
        protected Object toFixedJava(Object thisNumber, Object fractionDigits, @Cached.Shared(value="toInt") @Cached(value="create()") JSToIntegerAsIntNode toIntegerNode) {
            int digits = toIntegerNode.executeInt(fractionDigits);
            return this.toFixedIntl(JSRuntime.doubleValue((Number)thisNumber), digits);
        }

        @Specialization(guards={"isForeignObject(thisNumber)"}, limit="InteropLibraryLimit")
        protected Object toFixedForeignObject(Object thisNumber, Object fractionDigits, @Cached(value="create()") JSToIntegerAsIntNode toIntegerNode, @CachedLibrary(value="thisNumber") InteropLibrary interop) {
            double doubleValue = this.getDoubleValue(interop, thisNumber);
            int digits = toIntegerNode.executeInt(fractionDigits);
            return this.toFixedIntl(doubleValue, digits);
        }

        @Fallback
        protected Object toFixedGeneric(Object thisNumber, Object fractionDigits) {
            throw Errors.createTypeErrorNotANumber(thisNumber);
        }

        private Object toFixedIntl(double value2, int digits) {
            if (0 > digits || digits > (this.getContext().getEcmaScriptVersion() >= 9 ? 100 : 20)) {
                this.digitsErrorBranch.enter();
                throw Errors.createRangeError("toFixed() fraction digits need to be in range 0-100");
            }
            if (Double.isNaN(value2)) {
                this.nanBranch.enter();
                return Strings.NAN;
            }
            if (this.dtoaOrString.profile(value2 >= 1.0E21 || value2 <= -1.0E21)) {
                return this.toString(value2);
            }
            return JSRuntime.formatDtoAFixed(value2, digits);
        }

        private Object toString(double value2) {
            if (this.doubleToStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.doubleToStringNode = this.insert(JSDoubleToStringNode.create());
            }
            return this.doubleToStringNode.executeString(value2);
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class JSNumberValueOfNode
    extends JSNumberOperation {
        public JSNumberValueOfNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSNumber(thisNumber)"})
        protected Number valueOf(JSDynamicObject thisNumber) {
            return this.getNumberValue(thisNumber);
        }

        @Specialization(guards={"isJavaNumber(thisNumber)"})
        protected double valueOfPrimitive(Object thisNumber) {
            return JSRuntime.doubleValue((Number)thisNumber);
        }

        @Specialization(guards={"isForeignObject(thisNumber)"}, limit="InteropLibraryLimit")
        protected double valueOfForeignObject(Object thisNumber, @CachedLibrary(value="thisNumber") InteropLibrary interop) {
            return this.getDoubleValue(interop, thisNumber);
        }

        @Fallback
        protected Object valueOfOther(Object thisNumber) {
            throw Errors.createTypeErrorNotANumber(thisNumber);
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class JSNumberToLocaleStringIntlNode
    extends JSNumberOperation {
        @Node.Child
        InitializeNumberFormatNode initNumberFormatNode;

        public JSNumberToLocaleStringIntlNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.initNumberFormatNode = InitializeNumberFormatNode.createInitalizeNumberFormatNode(context);
        }

        @CompilerDirectives.TruffleBoundary
        private JSDynamicObject createNumberFormat(Object locales, Object options) {
            JSNumberFormatObject numberFormatObj = JSNumberFormat.create(this.getContext(), this.getRealm());
            this.initNumberFormatNode.executeInit(numberFormatObj, locales, options);
            return numberFormatObj;
        }

        @Specialization(guards={"isJSNumber(thisObj)"})
        protected TruffleString jsNumberToLocaleString(JSDynamicObject thisObj, Object locales, Object options) {
            JSDynamicObject numberFormatObj = this.createNumberFormat(locales, options);
            return JSNumberFormat.format(numberFormatObj, this.getNumberValue(thisObj));
        }

        @Specialization(guards={"isJavaNumber(thisObj)"})
        protected TruffleString javaNumberToLocaleString(Object thisObj, Object locales, Object options) {
            JSDynamicObject numberFormatObj = this.createNumberFormat(locales, options);
            return JSNumberFormat.format(numberFormatObj, JSRuntime.doubleValue((Number)thisObj));
        }

        @Specialization(guards={"isForeignObject(thisObj)"}, limit="InteropLibraryLimit")
        protected TruffleString toLocaleStringForeignObject(Object thisObj, Object locales, Object options, @CachedLibrary(value="thisObj") InteropLibrary interop) {
            double doubleValue = this.getDoubleValue(interop, thisObj);
            JSDynamicObject numberFormatObj = this.createNumberFormat(locales, options);
            return JSNumberFormat.format(numberFormatObj, doubleValue);
        }

        @Fallback
        protected Object failForNonNumbers(Object notANumber, Object locales, Object options) {
            throw Errors.createTypeErrorNotANumber(notANumber);
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class JSNumberToLocaleStringNode
    extends JSNumberOperation {
        public JSNumberToLocaleStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSNumber(thisObj)"})
        protected Object toLocaleString(JSDynamicObject thisObj) {
            double d = this.getDoubleValue(thisObj);
            return JSNumberToLocaleStringNode.toLocaleStringIntl(d);
        }

        @Specialization(guards={"isJavaNumber(thisObj)"})
        protected Object toLocaleStringPrimitive(Object thisObj) {
            double d = JSRuntime.doubleValue((Number)thisObj);
            return JSNumberToLocaleStringNode.toLocaleStringIntl(d);
        }

        private static Object toLocaleStringIntl(double d) {
            if (JSRuntime.doubleIsRepresentableAsInt(d)) {
                return Strings.fromInt((int)d);
            }
            return Strings.fromDouble(d);
        }

        @Specialization(guards={"isForeignObject(thisObj)"}, limit="InteropLibraryLimit")
        protected Object toLocaleStringForeignObject(Object thisObj, @CachedLibrary(value="thisObj") InteropLibrary interop) {
            return JSNumberToLocaleStringNode.toLocaleStringIntl(this.getDoubleValue(interop, thisObj));
        }

        @Fallback
        protected String toLocaleStringOther(Object thisNumber) {
            throw Errors.createTypeErrorNotANumber(thisNumber);
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class JSNumberToStringNode
    extends JSNumberOperation {
        @Node.Child
        private JSDoubleToStringNode doubleToStringNode;
        private final BranchProfile radixOtherBranch = BranchProfile.create();
        private final BranchProfile radixErrorBranch = BranchProfile.create();

        public JSNumberToStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected Object doubleToString(double value2) {
            if (this.doubleToStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.doubleToStringNode = this.insert(JSDoubleToStringNode.create());
            }
            return this.doubleToStringNode.executeString(value2);
        }

        protected boolean isRadix10(Object radix) {
            return radix == Undefined.instance || radix instanceof Integer && (Integer)radix == 10;
        }

        public static boolean isJSNumberInteger(JSNumberObject thisObj) {
            return JSNumber.valueOf(thisObj) instanceof Integer;
        }

        @Specialization(guards={"isJSNumberInteger(thisObj)", "isRadix10(radix)"})
        protected Object toStringIntRadix10(JSNumberObject thisObj, Object radix) {
            Integer i = (Integer)this.getNumberValue(thisObj);
            return Strings.fromInt(i);
        }

        @Specialization(guards={"isJSNumber(thisObj)", "isRadix10(radix)"})
        protected Object toStringRadix10(JSDynamicObject thisObj, Object radix) {
            return this.doubleToString(this.getDoubleValue(thisObj));
        }

        @Specialization(guards={"isJSNumber(thisObj)", "!isUndefined(radix)"})
        protected Object toString(JSDynamicObject thisObj, Object radix, @Cached.Shared(value="toInt") @Cached(value="create()") JSToIntegerAsIntNode toIntegerNode) {
            return this.toStringIntl(this.getDoubleValue(thisObj), radix, toIntegerNode);
        }

        @Specialization(guards={"isJavaNumber(thisObj)", "isNumberInteger(thisObj)", "isRadix10(radix)"})
        protected Object toStringPrimitiveIntRadix10(Object thisObj, Object radix) {
            Integer i = (Integer)thisObj;
            return Strings.fromInt(i);
        }

        @Specialization(guards={"isJavaNumber(thisObj)", "isRadix10(radix)"})
        protected Object toStringPrimitiveRadix10(Object thisObj, Object radix) {
            Number n = (Number)thisObj;
            return this.doubleToString(JSRuntime.doubleValue(n));
        }

        @Specialization
        protected Object toStringPrimitiveRadixInt(Number thisObj, int radix) {
            return this.toStringIntl(JSRuntime.doubleValue(thisObj), radix);
        }

        @Specialization(guards={"!isUndefined(radix)"}, replaces={"toStringPrimitiveRadixInt"})
        protected Object toStringPrimitive(Number thisObj, Object radix, @Cached.Shared(value="toInt") @Cached(value="create()") JSToIntegerAsIntNode toIntegerNode) {
            return this.toStringIntl(JSRuntime.doubleValue(thisObj), radix, toIntegerNode);
        }

        @Specialization(guards={"isForeignObject(thisObj)"}, limit="InteropLibraryLimit")
        protected Object toStringForeignObject(Object thisObj, Object radix, @Cached(value="create()") JSToIntegerAsIntNode toIntegerNode, @CachedLibrary(value="thisObj") InteropLibrary interop) {
            return this.toStringIntl(this.getDoubleValue(interop, thisObj), radix == Undefined.instance ? Integer.valueOf(10) : radix, toIntegerNode);
        }

        @Specialization(guards={"!isJSNumber(thisObj)", "!isJavaNumber(thisObj)", "!isForeignObject(thisObj)"})
        protected String toStringNoNumber(Object thisObj, Object radix) {
            throw Errors.createTypeErrorNotANumber(thisObj);
        }

        private Object toStringIntl(double numberVal, Object radix, JSToIntegerAsIntNode toIntegerNode) {
            int radixVal = toIntegerNode.executeInt(radix);
            return this.toStringIntl(numberVal, radixVal);
        }

        private Object toStringIntl(double numberVal, int radixVal) {
            if (radixVal < 2 || radixVal > 36) {
                this.radixErrorBranch.enter();
                throw Errors.createRangeError("toString() expects radix in range 2-36");
            }
            if (radixVal == 10) {
                return this.doubleToString(numberVal);
            }
            this.radixOtherBranch.enter();
            return JSRuntime.doubleToString(numberVal, radixVal);
        }
    }

    public static abstract class JSNumberOperation
    extends JSBuiltinNode {
        public JSNumberOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected Number getNumberValue(JSDynamicObject obj) {
            return JSNumber.valueOf(obj);
        }

        protected double getDoubleValue(JSDynamicObject obj) {
            return JSRuntime.doubleValue(JSNumber.valueOf(obj));
        }

        protected double getDoubleValue(InteropLibrary interop, Object obj) {
            assert (JSRuntime.isForeignObject(obj));
            if (interop.fitsInDouble(obj)) {
                try {
                    return interop.asDouble(obj);
                }
                catch (UnsupportedMessageException ex) {
                    throw Errors.createTypeErrorUnboxException(obj, ex, this);
                }
            }
            throw Errors.createTypeErrorNotANumber(obj);
        }
    }

    public static enum NumberPrototype implements BuiltinEnum<NumberPrototype>
    {
        toExponential(1),
        toFixed(1),
        toLocaleString(0),
        toPrecision(1),
        toString(1),
        valueOf(0);

        private final int length;

        private NumberPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

