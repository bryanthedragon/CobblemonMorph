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
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class NumberPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<NumberPrototypeBuiltins.NumberPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new NumberPrototypeBuiltins();

   protected NumberPrototypeBuiltins() {
      super(JSNumber.PROTOTYPE_NAME, NumberPrototypeBuiltins.NumberPrototype.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, NumberPrototypeBuiltins.NumberPrototype builtinEnum) {
      switch (builtinEnum) {
         case toExponential:
            return NumberPrototypeBuiltinsFactory.JSNumberToExponentialNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case toFixed:
            return NumberPrototypeBuiltinsFactory.JSNumberToFixedNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case toLocaleString:
            if (context.isOptionIntl402()) {
               return NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringIntlNodeGen.create(
                  context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
               );
            }

            return NumberPrototypeBuiltinsFactory.JSNumberToLocaleStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case toPrecision:
            return NumberPrototypeBuiltinsFactory.JSNumberToPrecisionNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case toString:
            return NumberPrototypeBuiltinsFactory.JSNumberToStringNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case valueOf:
            return NumberPrototypeBuiltinsFactory.JSNumberValueOfNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         default:
            return null;
      }
   }

   public abstract static class JSNumberOperation extends JSBuiltinNode {
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
         assert JSRuntime.isForeignObject(obj);

         if (interop.fitsInDouble(obj)) {
            try {
               return interop.asDouble(obj);
            } catch (UnsupportedMessageException var4) {
               throw Errors.createTypeErrorUnboxException(obj, var4, this);
            }
         } else {
            throw Errors.createTypeErrorNotANumber(obj);
         }
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSNumberToExponentialNode extends NumberPrototypeBuiltins.JSNumberOperation {
      public JSNumberToExponentialNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = {"isJSNumber(thisNumber)", "isUndefined(fractionDigits)"})
      protected Object toExponentialUndefined(JSDynamicObject thisNumber, Object fractionDigits) {
         double doubleValue = this.getDoubleValue(thisNumber);
         return toExponentialStandard(doubleValue);
      }

      @Specialization(guards = {"isJSNumber(thisNumber)", "!isUndefined(fractionDigits)"})
      protected Object toExponential(
         JSDynamicObject thisNumber,
         Object fractionDigits,
         @Cached @Cached.Shared("digitsError") BranchProfile digitsErrorBranch,
         @Cached.Shared("toInt") @Cached("create()") JSToIntegerAsIntNode toIntegerNode
      ) {
         double doubleValue = this.getDoubleValue(thisNumber);
         int digits = toIntegerNode.executeInt(fractionDigits);
         return this.toExponential(doubleValue, digits, digitsErrorBranch);
      }

      @Specialization(guards = {"isJavaNumber(thisNumber)", "isUndefined(fractionDigits)"})
      protected Object toExponentialPrimitiveUndefined(Object thisNumber, Object fractionDigits) {
         double doubleValue = JSRuntime.doubleValue((Number)thisNumber);
         return toExponentialStandard(doubleValue);
      }

      @Specialization(guards = {"isJavaNumber(thisNumber)", "!isUndefined(fractionDigits)"})
      protected Object toExponentialPrimitive(
         Object thisNumber,
         Object fractionDigits,
         @Cached @Cached.Shared("digitsError") BranchProfile digitsErrorBranch,
         @Cached.Shared("toInt") @Cached("create()") JSToIntegerAsIntNode toIntegerNode
      ) {
         double doubleValue = JSRuntime.doubleValue((Number)thisNumber);
         int digits = toIntegerNode.executeInt(fractionDigits);
         return this.toExponential(doubleValue, digits, digitsErrorBranch);
      }

      @Specialization(guards = {"isForeignObject(thisNumber)", "isUndefined(fractionDigits)"}, limit = "InteropLibraryLimit")
      protected Object toExponentialForeignObjectUndefined(Object thisNumber, Object fractionDigits, @CachedLibrary("thisNumber") InteropLibrary interop) {
         double doubleValue = this.getDoubleValue(interop, thisNumber);
         return toExponentialStandard(doubleValue);
      }

      @Specialization(guards = {"isForeignObject(thisNumber)", "!isUndefined(fractionDigits)"}, limit = "InteropLibraryLimit")
      protected Object toExponentialForeignObject(
         Object thisNumber,
         Object fractionDigits,
         @Cached BranchProfile digitsErrorBranch,
         @Cached("create()") JSToIntegerAsIntNode toIntegerNode,
         @CachedLibrary("thisNumber") InteropLibrary interop
      ) {
         double doubleValue = this.getDoubleValue(interop, thisNumber);
         int digits = toIntegerNode.executeInt(fractionDigits);
         return this.toExponential(doubleValue, digits, digitsErrorBranch);
      }

      @Specialization(guards = {"!isJSNumber(thisNumber)", "!isJavaNumber(thisNumber)", "!isForeignObject(thisNumber)"})
      protected Object toExponentialOther(Object thisNumber, Object fractionDigits) {
         throw Errors.createTypeErrorNotANumber(thisNumber);
      }

      private static Object toExponentialStandard(double value) {
         if (Double.isNaN(value)) {
            return Strings.NAN;
         } else if (Double.isInfinite(value)) {
            return value < 0.0 ? Strings.NEGATIVE_INFINITY : Strings.INFINITY;
         } else {
            return JSRuntime.formatDtoAExponential(value);
         }
      }

      private Object toExponential(double value, int digits, BranchProfile digitsErrorBranch) {
         if (Double.isNaN(value)) {
            return Strings.NAN;
         } else if (Double.isInfinite(value)) {
            return value < 0.0 ? Strings.NEGATIVE_INFINITY : Strings.INFINITY;
         } else {
            this.checkDigits(digits, digitsErrorBranch);
            return JSRuntime.formatDtoAExponential(value, digits);
         }
      }

      private void checkDigits(int digits, BranchProfile digitsErrorBranch) {
         int maxDigits = this.getContext().getEcmaScriptVersion() >= 9 ? 100 : 20;
         if (0 > digits || digits > maxDigits) {
            digitsErrorBranch.enter();
            throw digitsRangeError(maxDigits);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private static JSException digitsRangeError(int maxDigits) {
         return Errors.createRangeError("toExponential() fraction digits need to be in range 0-" + maxDigits);
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSNumberToFixedNode extends NumberPrototypeBuiltins.JSNumberOperation {
      private final BranchProfile digitsErrorBranch = BranchProfile.create();
      private final BranchProfile nanBranch = BranchProfile.create();
      private final ConditionProfile dtoaOrString = ConditionProfile.createBinaryProfile();
      @Node.Child
      protected JSDoubleToStringNode doubleToStringNode;

      protected JSNumberToFixedNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSNumber(thisNumber)")
      protected Object toFixed(
         JSDynamicObject thisNumber, Object fractionDigits, @Cached.Shared("toInt") @Cached("create()") JSToIntegerAsIntNode toIntegerNode
      ) {
         int digits = toIntegerNode.executeInt(fractionDigits);
         return this.toFixedIntl(this.getDoubleValue(thisNumber), digits);
      }

      @Specialization(guards = "isJavaNumber(thisNumber)")
      protected Object toFixedJava(Object thisNumber, Object fractionDigits, @Cached.Shared("toInt") @Cached("create()") JSToIntegerAsIntNode toIntegerNode) {
         int digits = toIntegerNode.executeInt(fractionDigits);
         return this.toFixedIntl(JSRuntime.doubleValue((Number)thisNumber), digits);
      }

      @Specialization(guards = "isForeignObject(thisNumber)", limit = "InteropLibraryLimit")
      protected Object toFixedForeignObject(
         Object thisNumber, Object fractionDigits, @Cached("create()") JSToIntegerAsIntNode toIntegerNode, @CachedLibrary("thisNumber") InteropLibrary interop
      ) {
         double doubleValue = this.getDoubleValue(interop, thisNumber);
         int digits = toIntegerNode.executeInt(fractionDigits);
         return this.toFixedIntl(doubleValue, digits);
      }

      @Fallback
      protected Object toFixedGeneric(Object thisNumber, Object fractionDigits) {
         throw Errors.createTypeErrorNotANumber(thisNumber);
      }

      private Object toFixedIntl(double value, int digits) {
         if (0 > digits || digits > (this.getContext().getEcmaScriptVersion() >= 9 ? 100 : 20)) {
            this.digitsErrorBranch.enter();
            throw Errors.createRangeError("toFixed() fraction digits need to be in range 0-100");
         } else if (Double.isNaN(value)) {
            this.nanBranch.enter();
            return Strings.NAN;
         } else {
            return this.dtoaOrString.profile(value >= 1.0E21 || value <= -1.0E21) ? this.toString(value) : JSRuntime.formatDtoAFixed(value, digits);
         }
      }

      private Object toString(double value) {
         if (this.doubleToStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.doubleToStringNode = this.insert(JSDoubleToStringNode.create());
         }

         return this.doubleToStringNode.executeString(value);
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSNumberToLocaleStringIntlNode extends NumberPrototypeBuiltins.JSNumberOperation {
      @Node.Child
      InitializeNumberFormatNode initNumberFormatNode;

      public JSNumberToLocaleStringIntlNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.initNumberFormatNode = InitializeNumberFormatNode.createInitalizeNumberFormatNode(context);
      }

      @CompilerDirectives.TruffleBoundary
      private JSDynamicObject createNumberFormat(Object locales, Object options) {
         JSDynamicObject numberFormatObj = JSNumberFormat.create(this.getContext(), this.getRealm());
         this.initNumberFormatNode.executeInit(numberFormatObj, locales, options);
         return numberFormatObj;
      }

      @Specialization(guards = "isJSNumber(thisObj)")
      protected TruffleString jsNumberToLocaleString(JSDynamicObject thisObj, Object locales, Object options) {
         JSDynamicObject numberFormatObj = this.createNumberFormat(locales, options);
         return JSNumberFormat.format(numberFormatObj, this.getNumberValue(thisObj));
      }

      @Specialization(guards = "isJavaNumber(thisObj)")
      protected TruffleString javaNumberToLocaleString(Object thisObj, Object locales, Object options) {
         JSDynamicObject numberFormatObj = this.createNumberFormat(locales, options);
         return JSNumberFormat.format(numberFormatObj, JSRuntime.doubleValue((Number)thisObj));
      }

      @Specialization(guards = "isForeignObject(thisObj)", limit = "InteropLibraryLimit")
      protected TruffleString toLocaleStringForeignObject(Object thisObj, Object locales, Object options, @CachedLibrary("thisObj") InteropLibrary interop) {
         double doubleValue = this.getDoubleValue(interop, thisObj);
         JSDynamicObject numberFormatObj = this.createNumberFormat(locales, options);
         return JSNumberFormat.format(numberFormatObj, doubleValue);
      }

      @Fallback
      protected Object failForNonNumbers(Object notANumber, Object locales, Object options) {
         throw Errors.createTypeErrorNotANumber(notANumber);
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSNumberToLocaleStringNode extends NumberPrototypeBuiltins.JSNumberOperation {
      public JSNumberToLocaleStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSNumber(thisObj)")
      protected Object toLocaleString(JSDynamicObject thisObj) {
         double d = this.getDoubleValue(thisObj);
         return toLocaleStringIntl(d);
      }

      @Specialization(guards = "isJavaNumber(thisObj)")
      protected Object toLocaleStringPrimitive(Object thisObj) {
         double d = JSRuntime.doubleValue((Number)thisObj);
         return toLocaleStringIntl(d);
      }

      private static Object toLocaleStringIntl(double d) {
         return JSRuntime.doubleIsRepresentableAsInt(d) ? Strings.fromInt((int)d) : Strings.fromDouble(d);
      }

      @Specialization(guards = "isForeignObject(thisObj)", limit = "InteropLibraryLimit")
      protected Object toLocaleStringForeignObject(Object thisObj, @CachedLibrary("thisObj") InteropLibrary interop) {
         return toLocaleStringIntl(this.getDoubleValue(interop, thisObj));
      }

      @Fallback
      protected String toLocaleStringOther(Object thisNumber) {
         throw Errors.createTypeErrorNotANumber(thisNumber);
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSNumberToPrecisionNode extends NumberPrototypeBuiltins.JSNumberOperation {
      private final BranchProfile precisionErrorBranch = BranchProfile.create();

      public JSNumberToPrecisionNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = {"isJSNumber(thisNumber)", "isUndefined(precision)"})
      protected Object toPrecisionUndefined(
         JSDynamicObject thisNumber, Object precision, @Cached.Shared("toString") @Cached("create()") JSToStringNode toStringNode
      ) {
         return toStringNode.executeString(thisNumber);
      }

      @Specialization(guards = {"isJSNumber(thisNumber)", "!isUndefined(precision)"})
      protected Object toPrecision(JSDynamicObject thisNumber, Object precision, @Cached.Shared("toNumber") @Cached("create()") JSToNumberNode toNumberNode) {
         long lPrecision = JSRuntime.toInteger(toNumberNode.executeNumber(precision));
         double thisNumberVal = this.getDoubleValue(thisNumber);
         return this.toPrecisionIntl(thisNumberVal, lPrecision);
      }

      @Specialization(guards = {"isJavaNumber(thisNumber)", "isUndefined(precision)"})
      protected Object toPrecisionPrimitiveUndefined(
         Object thisNumber, Object precision, @Cached.Shared("toString") @Cached("create()") JSToStringNode toStringNode
      ) {
         return toStringNode.executeString(thisNumber);
      }

      @Specialization(guards = {"isJavaNumber(thisNumber)", "!isUndefined(precision)"})
      protected Object toPrecisionPrimitive(Object thisNumber, Object precision, @Cached.Shared("toNumber") @Cached("create()") JSToNumberNode toNumberNode) {
         long lPrecision = JSRuntime.toInteger(toNumberNode.executeNumber(precision));
         double thisNumberVal = JSRuntime.doubleValue((Number)thisNumber);
         return this.toPrecisionIntl(thisNumberVal, lPrecision);
      }

      @Specialization(guards = {"isForeignObject(thisNumber)", "isUndefined(precision)"}, limit = "InteropLibraryLimit")
      protected Object toPrecisionForeignObjectUndefined(
         Object thisNumber, Object precision, @Cached("create()") JSToStringNode toStringNode, @CachedLibrary("thisNumber") InteropLibrary interop
      ) {
         return toStringNode.executeString(this.getDoubleValue(interop, thisNumber));
      }

      @Specialization(guards = {"isForeignObject(thisNumber)", "!isUndefined(precision)"}, limit = "InteropLibraryLimit")
      protected Object toPrecisionForeignObject(
         Object thisNumber, Object precision, @Cached("create()") JSToNumberNode toNumberNode, @CachedLibrary("thisNumber") InteropLibrary interop
      ) {
         double thisNumberVal = this.getDoubleValue(interop, thisNumber);
         long lPrecision = JSRuntime.toInteger(toNumberNode.executeNumber(precision));
         return this.toPrecisionIntl(thisNumberVal, lPrecision);
      }

      @Specialization(guards = {"!isJSNumber(thisNumber)", "!isJavaNumber(thisNumber)", "!isForeignObject(thisNumber)"})
      protected Object toPrecisionOther(Object thisNumber, Object precision) {
         throw Errors.createTypeErrorNotANumber(thisNumber);
      }

      private Object toPrecisionIntl(double thisNumberVal, long lPrecision) {
         if (Double.isNaN(thisNumberVal)) {
            return Strings.NAN;
         } else if (Double.isInfinite(thisNumberVal)) {
            return thisNumberVal < 0.0 ? Strings.NEGATIVE_INFINITY : Strings.INFINITY;
         } else {
            this.checkPrecision(lPrecision);
            return JSRuntime.formatDtoAPrecision(thisNumberVal, (int)lPrecision);
         }
      }

      private void checkPrecision(long precision) {
         if (1L > precision || precision > (this.getContext().getEcmaScriptVersion() >= 9 ? 100 : 20)) {
            this.precisionErrorBranch.enter();
            throw Errors.createRangeError("precision not in range");
         }
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSNumberToStringNode extends NumberPrototypeBuiltins.JSNumberOperation {
      @Node.Child
      private JSDoubleToStringNode doubleToStringNode;
      private final BranchProfile radixOtherBranch = BranchProfile.create();
      private final BranchProfile radixErrorBranch = BranchProfile.create();

      public JSNumberToStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected Object doubleToString(double value) {
         if (this.doubleToStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.doubleToStringNode = this.insert(JSDoubleToStringNode.create());
         }

         return this.doubleToStringNode.executeString(value);
      }

      protected boolean isRadix10(Object radix) {
         return radix == Undefined.instance || radix instanceof Integer && (Integer)radix == 10;
      }

      public static boolean isJSNumberInteger(JSNumberObject thisObj) {
         return JSNumber.valueOf(thisObj) instanceof Integer;
      }

      @Specialization(guards = {"isJSNumberInteger(thisObj)", "isRadix10(radix)"})
      protected Object toStringIntRadix10(JSNumberObject thisObj, Object radix) {
         Integer i = (Integer)this.getNumberValue(thisObj);
         return Strings.fromInt(i);
      }

      @Specialization(guards = {"isJSNumber(thisObj)", "isRadix10(radix)"})
      protected Object toStringRadix10(JSDynamicObject thisObj, Object radix) {
         return this.doubleToString(this.getDoubleValue(thisObj));
      }

      @Specialization(guards = {"isJSNumber(thisObj)", "!isUndefined(radix)"})
      protected Object toString(JSDynamicObject thisObj, Object radix, @Cached.Shared("toInt") @Cached("create()") JSToIntegerAsIntNode toIntegerNode) {
         return this.toStringIntl(this.getDoubleValue(thisObj), radix, toIntegerNode);
      }

      @Specialization(guards = {"isJavaNumber(thisObj)", "isNumberInteger(thisObj)", "isRadix10(radix)"})
      protected Object toStringPrimitiveIntRadix10(Object thisObj, Object radix) {
         Integer i = (Integer)thisObj;
         return Strings.fromInt(i);
      }

      @Specialization(guards = {"isJavaNumber(thisObj)", "isRadix10(radix)"})
      protected Object toStringPrimitiveRadix10(Object thisObj, Object radix) {
         Number n = (Number)thisObj;
         return this.doubleToString(JSRuntime.doubleValue(n));
      }

      @Specialization
      protected Object toStringPrimitiveRadixInt(Number thisObj, int radix) {
         return this.toStringIntl(JSRuntime.doubleValue(thisObj), radix);
      }

      @Specialization(guards = "!isUndefined(radix)", replaces = "toStringPrimitiveRadixInt")
      protected Object toStringPrimitive(Number thisObj, Object radix, @Cached.Shared("toInt") @Cached("create()") JSToIntegerAsIntNode toIntegerNode) {
         return this.toStringIntl(JSRuntime.doubleValue(thisObj), radix, toIntegerNode);
      }

      @Specialization(guards = "isForeignObject(thisObj)", limit = "InteropLibraryLimit")
      protected Object toStringForeignObject(
         Object thisObj, Object radix, @Cached("create()") JSToIntegerAsIntNode toIntegerNode, @CachedLibrary("thisObj") InteropLibrary interop
      ) {
         return this.toStringIntl(this.getDoubleValue(interop, thisObj), radix == Undefined.instance ? 10 : radix, toIntegerNode);
      }

      @Specialization(guards = {"!isJSNumber(thisObj)", "!isJavaNumber(thisObj)", "!isForeignObject(thisObj)"})
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
         } else if (radixVal == 10) {
            return this.doubleToString(numberVal);
         } else {
            this.radixOtherBranch.enter();
            return JSRuntime.doubleToString(numberVal, radixVal);
         }
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSNumberValueOfNode extends NumberPrototypeBuiltins.JSNumberOperation {
      public JSNumberValueOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSNumber(thisNumber)")
      protected Number valueOf(JSDynamicObject thisNumber) {
         return this.getNumberValue(thisNumber);
      }

      @Specialization(guards = "isJavaNumber(thisNumber)")
      protected double valueOfPrimitive(Object thisNumber) {
         return JSRuntime.doubleValue((Number)thisNumber);
      }

      @Specialization(guards = "isForeignObject(thisNumber)", limit = "InteropLibraryLimit")
      protected double valueOfForeignObject(Object thisNumber, @CachedLibrary("thisNumber") InteropLibrary interop) {
         return this.getDoubleValue(interop, thisNumber);
      }

      @Fallback
      protected Object valueOfOther(Object thisNumber) {
         throw Errors.createTypeErrorNotANumber(thisNumber);
      }
   }

   public static enum NumberPrototype implements BuiltinEnum<NumberPrototypeBuiltins.NumberPrototype> {
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
