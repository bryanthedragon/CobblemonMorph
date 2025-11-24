
package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSAdapter;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsObject;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSDate;
import com.oracle.truffle.js.runtime.builtins.JSFinalizationRegistry;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSMap;
import com.oracle.truffle.js.runtime.builtins.JSModuleNamespace;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSObjectPrototype;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.builtins.JSSet;
import com.oracle.truffle.js.runtime.builtins.JSSharedArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.JSSymbol;
import com.oracle.truffle.js.runtime.builtins.JSWeakMap;
import com.oracle.truffle.js.runtime.builtins.JSWeakRef;
import com.oracle.truffle.js.runtime.builtins.JSWeakSet;
import com.oracle.truffle.js.runtime.builtins.intl.JSCollator;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSDisplayNames;
import com.oracle.truffle.js.runtime.builtins.intl.JSListFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSLocale;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSPluralRules;
import com.oracle.truffle.js.runtime.builtins.intl.JSRelativeTimeFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenter;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendar;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstant;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDay;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonth;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalTimeZone;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyModule;
import com.oracle.truffle.js.runtime.java.JavaPackage;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;

public final class JSGuards {
    private JSGuards() {
    }

    public static boolean isJSObject(Object value2) {
        return JSRuntime.isObject(value2);
    }

    public static boolean isJSDynamicObject(Object value2) {
        return JSDynamicObject.isJSDynamicObject(value2);
    }

    public static boolean isTruffleObject(Object value2) {
        return value2 instanceof TruffleObject;
    }

    public static boolean isTruffleString(Object value2) {
        return Strings.isTString(value2);
    }

    public static boolean isForeignObject(Object value2) {
        return JSRuntime.isForeignObject(value2);
    }

    public static boolean isUndefined(Object value2) {
        return value2 == Undefined.instance;
    }

    public static boolean isJSNull(Object value2) {
        return value2 == Null.instance;
    }

    public static boolean isJSFunction(Object value2) {
        return JSFunction.isJSFunction(value2);
    }

    public static boolean isJSFunctionShape(Shape shape) {
        return shape.getDynamicType() == JSFunction.INSTANCE;
    }

    public static boolean isBoundJSFunction(Object value2) {
        return JSGuards.isJSFunction(value2) && JSFunction.isBoundFunction((JSFunctionObject)value2);
    }

    public static boolean isCallable(Object reviver) {
        return JSRuntime.isCallable(reviver);
    }

    public static boolean isCallableProxy(JSDynamicObject proxy) {
        return JSRuntime.isCallableProxy(proxy);
    }

    public static boolean isJSString(Object value2) {
        return JSString.isJSString(value2);
    }

    public static boolean isJSNumber(Object value2) {
        return JSNumber.isJSNumber(value2);
    }

    public static boolean isJSBigInt(Object value2) {
        return JSBigInt.isJSBigInt(value2);
    }

    public static boolean isJSBoolean(Object value2) {
        return JSBoolean.isJSBoolean(value2);
    }

    public static boolean isJSDate(Object value2) {
        return JSDate.isJSDate(value2);
    }

    public static boolean isJSArray(Object value2) {
        return JSArray.isJSArray(value2);
    }

    public static boolean isJSArgumentsObject(Object value2) {
        return JSArgumentsArray.isJSArgumentsObject(value2);
    }

    public static boolean isJSRegExp(Object value2) {
        return JSRegExp.isJSRegExp(value2);
    }

    public static boolean isJSOrdinaryObject(Object value2) {
        return JSOrdinary.isJSOrdinaryObject(value2);
    }

    public static boolean isJSDateTimeFormat(Object value2) {
        return JSDateTimeFormat.isJSDateTimeFormat(value2);
    }

    public static boolean isJSCollator(Object value2) {
        return JSCollator.isJSCollator(value2);
    }

    public static boolean isJSListFormat(Object value2) {
        return JSListFormat.isJSListFormat(value2);
    }

    public static boolean isJSNumberFormat(Object value2) {
        return JSNumberFormat.isJSNumberFormat(value2);
    }

    public static boolean isJSPluralRules(Object value2) {
        return JSPluralRules.isJSPluralRules(value2);
    }

    public static boolean isJSRelativeTimeFormat(Object value2) {
        return JSRelativeTimeFormat.isJSRelativeTimeFormat(value2);
    }

    public static boolean isJSSegmenter(Object value2) {
        return JSSegmenter.isJSSegmenter(value2);
    }

    public static boolean isJSSegments(Object value2) {
        return JSSegmenter.isJSSegments(value2);
    }

    public static boolean isJSSegmentIterator(Object value2) {
        return JSSegmenter.isJSSegmentIterator(value2);
    }

    public static boolean isJSDisplayNames(Object value2) {
        return JSDisplayNames.isJSDisplayNames(value2);
    }

    public static boolean isJSLocale(Object value2) {
        return JSLocale.isJSLocale(value2);
    }

    public static boolean isNumber(Object operand) {
        return JSRuntime.isNumber(operand);
    }

    public static boolean isJavaNumber(Object operand) {
        return JSRuntime.isJavaNumber(operand);
    }

    public static boolean isNumberInteger(Object operand) {
        return operand instanceof Integer;
    }

    public static boolean isNumberLong(Object operand) {
        return operand instanceof Long;
    }

    public static boolean isNumberDouble(Object operand) {
        return operand instanceof Double;
    }

    public static boolean isString(Object operand) {
        return operand instanceof TruffleString;
    }

    public static boolean isStringString(Object operand, Object operand2) {
        return Strings.isTString(operand) && Strings.isTString(operand2);
    }

    public static int stringLength(TruffleString operand) {
        return Strings.length(operand);
    }

    public static boolean stringEquals(TruffleString.EqualNode node, TruffleString a, TruffleString b) {
        return Strings.equals(node, a, b);
    }

    public static boolean isBoolean(Object operand) {
        return operand instanceof Boolean;
    }

    public static boolean isSymbol(Object operand) {
        return operand instanceof Symbol;
    }

    public static boolean isJSHeapArrayBuffer(Object thisObj) {
        return JSArrayBuffer.isJSHeapArrayBuffer(thisObj);
    }

    public static boolean isJSDirectArrayBuffer(Object thisObj) {
        return JSArrayBuffer.isJSDirectArrayBuffer(thisObj);
    }

    public static boolean isJSInteropArrayBuffer(Object thisObj) {
        return JSArrayBuffer.isJSInteropArrayBuffer(thisObj);
    }

    public static boolean isJSSharedArrayBuffer(Object thisObj) {
        return JSSharedArrayBuffer.isJSSharedArrayBuffer(thisObj);
    }

    public static boolean isJSArrayBufferView(Object thisObj) {
        return JSArrayBufferView.isJSArrayBufferView(thisObj);
    }

    public static boolean isJSFastArray(Object value2) {
        return JSArray.isJSFastArray(value2);
    }

    public static boolean isJSProxy(Object value2) {
        return JSProxy.isJSProxy(value2);
    }

    public static boolean isJSFastArgumentsObject(Object value2) {
        return JSArgumentsArray.isJSFastArgumentsObject(value2);
    }

    public static boolean isJSObjectPrototype(Object value2) {
        return JSObjectPrototype.isJSObjectPrototype(value2);
    }

    public static boolean isJSSymbol(Object value2) {
        return JSSymbol.isJSSymbol(value2);
    }

    public static boolean isJSTemporalTime(Object value2) {
        return JSTemporalPlainTime.isJSTemporalPlainTime(value2);
    }

    public static boolean isJSTemporalDate(Object value2) {
        return JSTemporalPlainDate.isJSTemporalPlainDate(value2);
    }

    public static boolean isJSTemporalDateTime(Object value2) {
        return JSTemporalPlainDateTime.isJSTemporalPlainDateTime(value2);
    }

    public static boolean isJSTemporalYearMonth(Object value2) {
        return JSTemporalPlainYearMonth.isJSTemporalPlainYearMonth(value2);
    }

    public static boolean isJSTemporalMonthDay(Object value2) {
        return JSTemporalPlainMonthDay.isJSTemporalPlainMonthDay(value2);
    }

    public static boolean isJSTemporalDuration(Object value2) {
        return JSTemporalDuration.isJSTemporalDuration(value2);
    }

    public static boolean isJSTemporalCalendar(Object value2) {
        return JSTemporalCalendar.isJSTemporalCalendar(value2);
    }

    public static boolean isJSTemporalInstant(Object value2) {
        return JSTemporalInstant.isJSTemporalInstant(value2);
    }

    public static boolean isJSTemporalTimeZone(Object value2) {
        return JSTemporalTimeZone.isJSTemporalTimeZone(value2);
    }

    public static boolean isJSTemporalZonedDateTime(Object value2) {
        return JSTemporalZonedDateTime.isJSTemporalZonedDateTime(value2);
    }

    public static boolean isJSMap(Object value2) {
        return JSMap.isJSMap(value2);
    }

    public static boolean isJSSet(Object value2) {
        return JSSet.isJSSet(value2);
    }

    public static boolean isJSWeakRef(Object value2) {
        return JSWeakRef.isJSWeakRef(value2);
    }

    public static boolean isJSFinalizationRegistry(Object value2) {
        return JSFinalizationRegistry.isJSFinalizationRegistry(value2);
    }

    public static boolean isJSWeakMap(Object value2) {
        return JSWeakMap.isJSWeakMap(value2);
    }

    public static boolean isJSWeakSet(Object value2) {
        return JSWeakSet.isJSWeakSet(value2);
    }

    public static boolean isJSModuleNamespace(Object value2) {
        return JSModuleNamespace.isJSModuleNamespace(value2);
    }

    public static boolean isJSAdapter(Object object) {
        return JSAdapter.isJSAdapter(object);
    }

    public static boolean isJSWebAssemblyModule(Object object) {
        return JSWebAssemblyModule.isJSWebAssemblyModule(object);
    }

    public static boolean isValidPrototype(Object prototype) {
        return JSGuards.isJSObject(prototype) || JSGuards.isJSNull(prototype);
    }

    public static boolean isList(Object value2) {
        return value2 instanceof List;
    }

    public static boolean isJavaPackage(Object target) {
        return JavaPackage.isJavaPackage(target);
    }

    public static boolean isJavaArray(Object value2) {
        return value2 != null && value2.getClass().isArray();
    }

    public static boolean isBigInt(Object target) {
        return target instanceof BigInt;
    }

    public static boolean isBigIntZero(BigInt a) {
        return BigInt.ZERO.equals(a);
    }

    public static boolean isBigIntNegativeVal(BigInt a) {
        return a.signum() == -1;
    }

    public static boolean isDoubleInInt32Range(double value2) {
        return -2.147483648E9 <= value2 && value2 <= 2.147483647E9;
    }

    public static boolean isDoubleLargerThan2e32(double d) {
        return Math.abs(d) >= 4.294967296E9;
    }

    public static boolean isLongRepresentableAsInt32(long value2) {
        return JSRuntime.longIsRepresentableAsInt(value2);
    }

    public static boolean isDoubleRepresentableAsLong(double d) {
        return JSRuntime.doubleIsRepresentableAsLong(d);
    }

    public static boolean isDoubleSafeInteger(double d) {
        return JSRuntime.isSafeInteger(d);
    }

    public static boolean isIntArrayIndex(int i) {
        return JSRuntime.isArrayIndex(i);
    }

    public static boolean isLongArrayIndex(long i) {
        return JSRuntime.isArrayIndex(i);
    }

    public static boolean isBigIntArrayIndex(BigInt i) {
        return i.fitsInLong() && JSRuntime.isArrayIndex(i.longValue());
    }

    public static boolean isArgumentsDisconnected(JSArgumentsObject argumentsArray) {
        return JSAbstractArgumentsArray.hasDisconnectedIndices(argumentsArray);
    }

    public static Class<?> getClassIfJSObject(Object object) {
        if (JSGuards.isJSObject(object)) {
            return object.getClass();
        }
        return null;
    }

    public static Class<?> getClassIfJSDynamicObject(Object object) {
        if (JSGuards.isJSDynamicObject(object)) {
            return object.getClass();
        }
        return null;
    }

    public static boolean isReferenceEquals(Object a, Object b) {
        return a == b;
    }

    public static boolean isJavaPrimitive(Object value2) {
        return JSRuntime.isJavaPrimitive(value2);
    }

    public static boolean isJavaPrimitiveNumber(Object value2) {
        return value2 instanceof Number && JSRuntime.isJavaPrimitive(value2);
    }

    public static boolean isNullOrUndefined(Object value2) {
        return JSRuntime.isNullOrUndefined(value2);
    }

    public static boolean hasOverloadedOperators(Object value2) {
        return JSOverloadedOperatorsObject.hasOverloadedOperators(value2);
    }
}

