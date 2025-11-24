
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ExactMath;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.access.IsPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.OrdinaryToPrimitiveNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.array.TypedArrayFactory;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSAdapter;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSMap;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSSet;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.JSSymbol;
import com.oracle.truffle.js.runtime.doubleconv.DoubleConversion;
import com.oracle.truffle.js.runtime.external.DToA;
import com.oracle.truffle.js.runtime.interop.InteropFunction;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Nullish;
import com.oracle.truffle.js.runtime.objects.OperatorSet;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSHashMap;
import java.util.ArrayList;
import java.util.List;

public final class JSRuntime {
    private static final long NEGATIVE_ZERO_DOUBLE_BITS = Double.doubleToRawLongBits(-0.0);
    private static final long POSITIVE_INFINITY_DOUBLE_BITS = Double.doubleToRawLongBits(Double.POSITIVE_INFINITY);
    public static final double TWO32 = 4.294967296E9;
    public static final long INVALID_ARRAY_INDEX = -1L;
    public static final long MAX_ARRAY_LENGTH = 0xFFFFFFFFL;
    public static final int MAX_UINT32_DIGITS = 10;
    public static final double MAX_SAFE_INTEGER = Math.pow(2.0, 53.0) - 1.0;
    public static final double MIN_SAFE_INTEGER = -MAX_SAFE_INTEGER;
    public static final long MAX_SAFE_INTEGER_LONG = (long)MAX_SAFE_INTEGER;
    public static final long MIN_SAFE_INTEGER_LONG = (long)MIN_SAFE_INTEGER;
    public static final long INVALID_INTEGER_INDEX = -1L;
    public static final int MAX_INTEGER_INDEX_DIGITS = 16;
    public static final int MAX_SAFE_INTEGER_DIGITS = 16;
    public static final int MAX_SAFE_INTEGER_IN_FLOAT = 0x1000000;
    public static final int MIN_SAFE_INTEGER_IN_FLOAT = -16777216;
    public static final long MAX_BIG_INT_EXPONENT = Integer.MAX_VALUE;
    public static final long INVALID_SAFE_INTEGER = Long.MIN_VALUE;
    public static final TruffleString VALUE = Strings.constant("value");
    public static final TruffleString DONE = Strings.constant("done");
    public static final TruffleString NEXT = Strings.constant("next");
    public static final HiddenKey ITERATED_OBJECT_ID = new HiddenKey("IteratedObject");
    public static final HiddenKey ITERATOR_NEXT_INDEX = new HiddenKey("IteratorNextIndex");
    public static final HiddenKey ENUMERATE_ITERATOR_ID = new HiddenKey("EnumerateIterator");
    public static final HiddenKey FOR_IN_ITERATOR_ID = new HiddenKey("ForInIterator");
    public static final HiddenKey FINALIZATION_GROUP_CLEANUP_ITERATOR_ID = new HiddenKey("CleanupIterator");
    public static final int ITERATION_KIND_KEY = 1;
    public static final int ITERATION_KIND_VALUE = 2;
    public static final int ITERATION_KIND_KEY_PLUS_VALUE = 3;

    private JSRuntime() {
    }

    public static boolean doubleIsRepresentableAsInt(double d) {
        return JSRuntime.doubleIsRepresentableAsInt(d, false);
    }

    public static boolean doubleIsRepresentableAsInt(double d, boolean ignoreNegativeZero) {
        long longValue = (long)d;
        return JSRuntime.doubleIsRepresentableAsLong(d) && JSRuntime.longIsRepresentableAsInt(longValue) && (ignoreNegativeZero || !JSRuntime.isNegativeZero(d));
    }

    public static boolean doubleIsRepresentableAsUnsignedInt(double d, boolean ignoreNegativeZero) {
        long longValue = (long)d;
        return JSRuntime.doubleIsRepresentableAsLong(d) && JSRuntime.longIsRepresentableAsInt(longValue) && (ignoreNegativeZero || !JSRuntime.isNegativeZero(d));
    }

    public static boolean isNegativeZero(double d) {
        return Double.doubleToRawLongBits(d) == NEGATIVE_ZERO_DOUBLE_BITS;
    }

    public static boolean isPositiveInfinity(double d) {
        return Double.doubleToRawLongBits(d) == POSITIVE_INFINITY_DOUBLE_BITS;
    }

    public static Number doubleToNarrowestNumber(double d) {
        if (JSRuntime.doubleIsRepresentableAsInt(d)) {
            return (int)d;
        }
        return d;
    }

    public static boolean longIsRepresentableAsInt(long value2) {
        return value2 == (long)((int)value2);
    }

    public static boolean isRepresentableAsUnsignedInt(long value2) {
        return (value2 & 0xFFFFFFFFL) == value2;
    }

    public static boolean doubleIsRepresentableAsLong(double d) {
        return d == (double)((long)d);
    }

    public static Object positiveLongToIntOrDouble(long value2) {
        if (value2 <= Integer.MAX_VALUE) {
            return (int)value2;
        }
        return (double)value2;
    }

    public static Number longToIntOrDouble(long value2) {
        if (Integer.MIN_VALUE <= value2 && value2 <= Integer.MAX_VALUE) {
            return (int)value2;
        }
        return (double)value2;
    }

    public static boolean isNaN(Object value2) {
        if (!(value2 instanceof Double)) {
            return false;
        }
        double d = (Double)value2;
        return Double.isNaN(d);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString typeof(Object value2) {
        if (value2 == Null.instance) {
            return Null.TYPE_NAME;
        }
        if (value2 == Undefined.instance) {
            return Undefined.TYPE_NAME;
        }
        if (Strings.isTString(value2)) {
            return JSString.TYPE_NAME;
        }
        if (JSRuntime.isNumber(value2)) {
            return JSNumber.TYPE_NAME;
        }
        if (JSRuntime.isBigInt(value2)) {
            return JSBigInt.TYPE_NAME;
        }
        if (value2 instanceof Boolean) {
            return JSBoolean.TYPE_NAME;
        }
        if (value2 instanceof Symbol) {
            return JSSymbol.TYPE_NAME;
        }
        if (JSObject.isJSObject(value2)) {
            JSObject object = (JSObject)value2;
            if (JSProxy.isJSProxy(object)) {
                Object target = JSProxy.getTargetNonProxy(object);
                return JSRuntime.typeof(target);
            }
            if (JSFunction.isJSFunction(object)) {
                return JSFunction.TYPE_NAME;
            }
            return JSOrdinary.TYPE_NAME;
        }
        if (value2 instanceof TruffleObject) {
            TruffleLanguage.Env env;
            assert (!(value2 instanceof Symbol));
            JSRealm realm = JSRealm.get(null);
            if (realm.getContext().isOptionNashornCompatibilityMode() && (env = realm.getEnv()).isHostSymbol(value2)) {
                return JSFunction.TYPE_NAME;
            }
            TruffleObject object = (TruffleObject)value2;
            InteropLibrary interop = InteropLibrary.getUncached();
            if (interop.isBoolean(object)) {
                return JSBoolean.TYPE_NAME;
            }
            if (interop.isString(object)) {
                return JSString.TYPE_NAME;
            }
            if (interop.isNumber(object)) {
                return JSNumber.TYPE_NAME;
            }
            if (interop.isExecutable(object) || interop.isInstantiable(object)) {
                return JSFunction.TYPE_NAME;
            }
            return JSOrdinary.TYPE_NAME;
        }
        throw new UnsupportedOperationException("typeof: don't know " + value2.getClass().getSimpleName());
    }

    public static boolean isObject(Object value2) {
        return value2 instanceof JSObject;
    }

    public static boolean isNullOrUndefined(Object value2) {
        return value2 instanceof Nullish;
    }

    public static boolean isNullish(Object value2) {
        return value2 == Null.instance || value2 == Undefined.instance || InteropLibrary.getUncached(value2).isNull(value2);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object toPrimitive(Object value2) {
        return JSRuntime.toPrimitive(value2, JSToPrimitiveNode.Hint.Default);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object toPrimitive(Object value2, JSToPrimitiveNode.Hint hint) {
        if (value2 == Null.instance || value2 == Undefined.instance) {
            return value2;
        }
        if (JSObject.isJSObject(value2)) {
            return JSObject.toPrimitive((JSObject)value2, hint);
        }
        if (JSRuntime.isForeignObject(value2)) {
            return JSRuntime.toPrimitiveFromForeign(value2, hint);
        }
        return value2;
    }

    @CompilerDirectives.TruffleBoundary
    public static Object toPrimitiveFromForeign(Object tObj, JSToPrimitiveNode.Hint hint) {
        Object maybeResult;
        assert (JSRuntime.isForeignObject(tObj));
        InteropLibrary interop = InteropLibrary.getFactory().getUncached(tObj);
        if (interop.isNull(tObj)) {
            return Null.instance;
        }
        if (JSInteropUtil.isBoxedPrimitive(tObj, interop)) {
            return JSInteropUtil.toPrimitiveOrDefault(tObj, Null.instance, interop, null);
        }
        if (JavaScriptLanguage.getCurrentEnv().isHostObject(tObj) && (maybeResult = JSToPrimitiveNode.tryHostObjectToPrimitive(tObj, hint, interop)) != null) {
            return maybeResult;
        }
        return JSRuntime.foreignOrdinaryToPrimitive(tObj, hint == JSToPrimitiveNode.Hint.Default ? JSToPrimitiveNode.Hint.Number : hint);
    }

    @CompilerDirectives.TruffleBoundary
    private static Object foreignOrdinaryToPrimitive(Object obj, JSToPrimitiveNode.Hint hint) {
        TruffleString[] methodNames;
        InteropLibrary interop = InteropLibrary.getFactory().getUncached(obj);
        if (hint == JSToPrimitiveNode.Hint.String) {
            methodNames = new TruffleString[]{Strings.TO_STRING, Strings.VALUE_OF};
        } else {
            assert (hint == JSToPrimitiveNode.Hint.Number);
            methodNames = new TruffleString[]{Strings.VALUE_OF, Strings.TO_STRING};
        }
        JSDynamicObject proto = ForeignObjectPrototypeNode.getUncached().execute(obj);
        for (TruffleString name : methodNames) {
            Object method;
            if (interop.hasMembers(obj) && interop.isMemberInvocable(obj, Strings.toJavaString(name)) && !OrdinaryToPrimitiveNode.isJavaArray(obj, interop)) {
                try {
                    Object result = JSRuntime.importValue(interop.invokeMember(obj, Strings.toJavaString(name), new Object[0]));
                    if (IsPrimitiveNode.getUncached().executeBoolean(result)) {
                        return result;
                    }
                }
                catch (InteropException result) {
                    // empty catch block
                }
            }
            if (!JSRuntime.isCallable(method = JSObject.getMethod(proto, name))) continue;
            Object result = JSRuntime.call(method, obj, new Object[0]);
            if (!IsPrimitiveNode.getUncached().executeBoolean(result)) continue;
            return result;
        }
        throw Errors.createTypeErrorCannotConvertToPrimitiveValue();
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean toBoolean(Object value2) {
        if (value2 == Boolean.TRUE) {
            return true;
        }
        if (value2 == Boolean.FALSE || value2 == Undefined.instance || value2 == Null.instance) {
            return false;
        }
        if (JSRuntime.isNumber(value2)) {
            return JSRuntime.toBoolean((Number)value2);
        }
        if (Strings.isTString(value2)) {
            return Strings.length((TruffleString)value2) != 0;
        }
        if (value2 instanceof BigInt) {
            return ((BigInt)value2).compareTo(BigInt.ZERO) != 0;
        }
        if (JSRuntime.isForeignObject(value2)) {
            InteropLibrary interop = InteropLibrary.getFactory().getUncached(value2);
            if (interop.isNull(value2)) {
                return false;
            }
            if (JSInteropUtil.isBoxedPrimitive(value2, interop)) {
                return JSRuntime.toBoolean(JSInteropUtil.toPrimitiveOrDefault(value2, Null.instance, interop, null));
            }
            return true;
        }
        return true;
    }

    public static boolean toBoolean(Number number) {
        double val = JSRuntime.doubleValue(number);
        if (val == 0.0 || Double.isNaN(val)) {
            return false;
        }
        return Boolean.TRUE;
    }

    @CompilerDirectives.TruffleBoundary
    public static Number toNumber(Object value2) {
        Object primitive = JSRuntime.isObject(value2) ? JSObject.toPrimitive((JSDynamicObject)value2, JSToPrimitiveNode.Hint.Number) : (JSRuntime.isForeignObject(value2) ? JSRuntime.toPrimitiveFromForeign(value2, JSToPrimitiveNode.Hint.Number) : value2);
        return JSRuntime.toNumberFromPrimitive(primitive);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object toNumeric(Object value2) {
        Object primitive;
        Object object = primitive = JSRuntime.isObject(value2) ? JSObject.toPrimitive((JSDynamicObject)value2, JSToPrimitiveNode.Hint.Number) : value2;
        if (primitive instanceof BigInt) {
            return primitive;
        }
        return JSRuntime.toNumberFromPrimitive(primitive);
    }

    @CompilerDirectives.TruffleBoundary
    public static Number toNumberFromPrimitive(Object value2) {
        if (CompilerDirectives.injectBranchProbability(0.75, JSRuntime.isNumber(value2))) {
            return (Number)value2;
        }
        if (value2 == Undefined.instance) {
            return Double.NaN;
        }
        if (value2 == Null.instance) {
            return 0;
        }
        if (value2 instanceof Boolean) {
            return JSRuntime.booleanToNumber((Boolean)value2);
        }
        if (Strings.isTString(value2)) {
            return JSRuntime.stringToNumber((TruffleString)value2);
        }
        if (value2 instanceof Symbol) {
            throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value");
        }
        if (value2 instanceof BigInt) {
            throw Errors.createTypeErrorCannotConvertToNumber("a BigInt value");
        }
        if (value2 instanceof Number) {
            assert (JSRuntime.isJavaPrimitive(value2)) : value2.getClass().getName();
            return (Number)value2;
        }
        assert (false) : "should never reach here, type " + value2.getClass().getName() + " not handled.";
        throw Errors.createTypeErrorCannotConvertToNumber(Strings.toJavaString(JSRuntime.safeToString(value2)));
    }

    public static int booleanToNumber(boolean value2) {
        return value2 ? 1 : 0;
    }

    public static boolean isNumber(Object value2) {
        return value2 instanceof Integer || value2 instanceof Double || value2 instanceof Long || value2 instanceof SafeInteger;
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInt toBigInt(Object value2) {
        Object primitive = JSRuntime.toPrimitive(value2, JSToPrimitiveNode.Hint.Number);
        if (Strings.isTString(primitive)) {
            try {
                return Strings.parseBigInt((TruffleString)primitive);
            }
            catch (NumberFormatException e) {
                throw Errors.createErrorCanNotConvertToBigInt(JSErrorType.SyntaxError, primitive);
            }
        }
        if (primitive instanceof BigInt) {
            return (BigInt)primitive;
        }
        if (primitive instanceof Boolean) {
            return (Boolean)primitive != false ? BigInt.ONE : BigInt.ZERO;
        }
        throw Errors.createErrorCanNotConvertToBigInt(JSErrorType.TypeError, primitive);
    }

    public static boolean isBigInt(Object value2) {
        return value2 instanceof BigInt;
    }

    public static boolean isJavaNumber(Object value2) {
        return value2 instanceof Number;
    }

    @CompilerDirectives.TruffleBoundary
    public static Number stringToNumber(TruffleString string) {
        TruffleString strCamel = JSRuntime.trimJSWhiteSpace(string);
        int camelLength = Strings.length(strCamel);
        if (camelLength == 0) {
            return 0;
        }
        char firstChar = Strings.charAt(strCamel, 0);
        if (camelLength >= Strings.length(Strings.INFINITY) && camelLength <= Strings.length(Strings.INFINITY) + 1 && Strings.endsWith(strCamel, Strings.INFINITY)) {
            return JSRuntime.identifyInfinity(firstChar, camelLength);
        }
        if (!JSRuntime.isAsciiDigit(firstChar) && firstChar != '-' && firstChar != '.' && firstChar != '+') {
            return Double.NaN;
        }
        return JSRuntime.stringToNumberParse(strCamel);
    }

    private static Number stringToNumberParse(TruffleString str) {
        assert (Strings.length(str) > 0);
        boolean hex = Strings.startsWith(str, Strings.LC_0X) || Strings.startsWith(str, Strings.UC_0X);
        int eIndex = JSRuntime.firstExpIndexInString(str);
        boolean sci = !hex && 0 <= eIndex && eIndex < Strings.length(str) - 1;
        try {
            if (!sci && Strings.length(str) <= 18 && Strings.indexOf(str, '.') == -1) {
                if (hex) {
                    return Strings.parseLong(Strings.lazySubstring(str, 2), 16);
                }
                return JSRuntime.stringToNumberLong(str);
            }
            return JSRuntime.parseDoubleOrNaN(str);
        }
        catch (TruffleString.NumberFormatException e) {
            return Double.NaN;
        }
    }

    private static Number stringToNumberLong(TruffleString strLower) throws TruffleString.NumberFormatException {
        assert (Strings.length(strLower) > 0);
        long num = Strings.parseLong(strLower);
        if (JSRuntime.longIsRepresentableAsInt(num)) {
            if (num == 0L && Strings.charAt(strLower, 0) == '-') {
                return -0.0;
            }
            return (int)num;
        }
        return (double)num;
    }

    @CompilerDirectives.TruffleBoundary
    public static double parseDoubleOrNaN(TruffleString input) {
        if (Strings.isEmpty(input) || Strings.charAt(input, Strings.length(input) - 1) > '9') {
            return Double.NaN;
        }
        try {
            return Strings.parseDouble(input);
        }
        catch (TruffleString.NumberFormatException e) {
            return Double.NaN;
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static int firstExpIndexInString(TruffleString str) {
        int firstIdx = Strings.indexOf(str, 101, 0);
        if (firstIdx >= 0) {
            return firstIdx;
        }
        return Strings.indexOf(str, 69, 0);
    }

    public static double identifyInfinity(char firstChar, int len) {
        int infinityLength = Strings.length(Strings.INFINITY);
        if (len == infinityLength) {
            return Double.POSITIVE_INFINITY;
        }
        if (len == infinityLength + 1) {
            if (firstChar == '+') {
                return Double.POSITIVE_INFINITY;
            }
            if (firstChar == '-') {
                return Double.NEGATIVE_INFINITY;
            }
        }
        return Double.NaN;
    }

    public static long toInteger(Object value2) {
        Number number = JSRuntime.toNumber(value2);
        return JSRuntime.toInteger(number);
    }

    public static long toInteger(Number number) {
        return JSRuntime.longValue(number);
    }

    public static long toLength(Object value2) {
        long l = JSRuntime.toInteger(value2);
        return JSRuntime.toLength(l);
    }

    public static double toLength(double d) {
        if (d <= 0.0) {
            return 0.0;
        }
        if (d > MAX_SAFE_INTEGER) {
            return MAX_SAFE_INTEGER;
        }
        return d;
    }

    public static long toLength(long l) {
        if (l <= 0L) {
            return 0L;
        }
        if (l > MAX_SAFE_INTEGER_LONG) {
            return MAX_SAFE_INTEGER_LONG;
        }
        return l;
    }

    public static int toLength(int value2) {
        if (value2 <= 0) {
            return 0;
        }
        return value2;
    }

    public static int toUInt8(Object value2) {
        Number number = JSRuntime.toNumber(value2);
        return JSRuntime.toUInt8(number);
    }

    @CompilerDirectives.TruffleBoundary
    public static int toUInt8(Number number) {
        Double d;
        if (number instanceof Double && JSRuntime.isPositiveInfinity(d = (Double)number)) {
            return 0;
        }
        return JSRuntime.toUInt8(number.longValue());
    }

    public static int toUInt8(long number) {
        return (int)(number & 0xFFL);
    }

    public static int toInt8(Object value2) {
        Number number = JSRuntime.toNumber(value2);
        return JSRuntime.toInt8(number);
    }

    @CompilerDirectives.TruffleBoundary
    public static int toInt8(Number number) {
        Double d;
        if (number instanceof Double && JSRuntime.isPositiveInfinity(d = (Double)number)) {
            return 0;
        }
        return JSRuntime.toInt8(number.longValue());
    }

    @CompilerDirectives.TruffleBoundary
    public static int toInt8(long number) {
        int res = JSRuntime.floorMod(number, 256);
        if (res >= 128) {
            res -= 256;
        }
        return res;
    }

    public static int toUInt16(Object value2) {
        Number number = JSRuntime.toNumber(value2);
        return JSRuntime.toUInt16(number);
    }

    public static int toUInt16(Number number) {
        Double d;
        if (number instanceof Double && JSRuntime.isPositiveInfinity(d = (Double)number)) {
            return 0;
        }
        return JSRuntime.toUInt16(JSRuntime.longValue(number));
    }

    public static int toUInt16(long number) {
        return (int)(number & 0xFFFFL);
    }

    public static int toInt16(Object value2) {
        Number number = JSRuntime.toNumber(value2);
        return JSRuntime.toInt16(number);
    }

    @CompilerDirectives.TruffleBoundary
    public static int toInt16(Number number) {
        Double d;
        if (number instanceof Double && JSRuntime.isPositiveInfinity(d = (Double)number)) {
            return 0;
        }
        return JSRuntime.toInt16(number.longValue());
    }

    @CompilerDirectives.TruffleBoundary
    public static int toInt16(long number) {
        int res = JSRuntime.floorMod(number, 65536);
        if (res >= 32768) {
            res -= 65536;
        }
        return res;
    }

    public static int floorMod(long x, int y) {
        long divisor = y;
        return (int)Math.floorMod(x, divisor);
    }

    public static long toUInt32(Object value2) {
        return JSRuntime.toUInt32(JSRuntime.toNumber(value2));
    }

    public static long toUInt32(Number number) {
        if (number instanceof Double) {
            return JSRuntime.toUInt32((Double)number);
        }
        return JSRuntime.toUInt32(JSRuntime.longValue(number));
    }

    public static long toUInt32(long value2) {
        return value2 & 0xFFFFFFFFL;
    }

    public static long toUInt32(double value2) {
        return JSRuntime.toUInt32NoTruncate(JSRuntime.truncateDouble(value2));
    }

    public static long toUInt32NoTruncate(double value2) {
        assert (!Double.isFinite(value2) || value2 % 1.0 == 0.0);
        double d = JSRuntime.doubleModuloTwo32(value2);
        return JSRuntime.toUInt32((long)d);
    }

    public static double truncateDouble(double value2) {
        return ExactMath.truncate(value2);
    }

    public static int toInt32(Object value2) {
        Number number = JSRuntime.toNumber(value2);
        return JSRuntime.toInt32(number);
    }

    public static int toInt32(Number number) {
        if (number instanceof Double) {
            return JSRuntime.toInt32((Double)number);
        }
        if (number instanceof Integer) {
            return (Integer)number;
        }
        if (number instanceof Long) {
            return (int)((Long)number).longValue();
        }
        return JSRuntime.toInt32Intl(number);
    }

    @CompilerDirectives.TruffleBoundary
    private static int toInt32Intl(Number number) {
        return JSRuntime.toInt32(number.doubleValue());
    }

    public static int toInt32(double value2) {
        return JSRuntime.toInt32NoTruncate(JSRuntime.truncateDouble(value2));
    }

    public static int toInt32NoTruncate(double value2) {
        assert (!Double.isFinite(value2) || value2 % 1.0 == 0.0);
        return (int)JSRuntime.doubleModuloTwo32(value2);
    }

    private static double doubleModuloTwo32(double value2) {
        return value2 - Math.floor(value2 / 4.294967296E9) * 4.294967296E9;
    }

    public static double toDouble(Object value2) {
        return JSRuntime.doubleValue(JSRuntime.toNumber(value2));
    }

    public static double toDouble(Number value2) {
        return JSRuntime.doubleValue(value2);
    }

    public static String toJavaString(Object value2) {
        return Strings.toJavaString(JSRuntime.toString(value2));
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString toString(Object value2) {
        if (CompilerDirectives.injectBranchProbability(0.75, Strings.isTString(value2))) {
            return (TruffleString)value2;
        }
        if (value2 == Undefined.instance) {
            return Undefined.NAME;
        }
        if (value2 == Null.instance) {
            return Null.NAME;
        }
        if (value2 instanceof Boolean) {
            return JSRuntime.booleanToString((Boolean)value2);
        }
        if (JSRuntime.isNumber(value2)) {
            return JSRuntime.numberToString((Number)value2);
        }
        if (value2 instanceof Symbol) {
            throw Errors.createTypeErrorCannotConvertToString("a Symbol value");
        }
        if (value2 instanceof BigInt) {
            return Strings.fromBigInt((BigInt)value2);
        }
        if (JSObject.isJSObject(value2)) {
            return JSRuntime.toString(JSObject.toPrimitive((JSObject)value2, JSToPrimitiveNode.Hint.String));
        }
        if (value2 instanceof TruffleObject) {
            assert (!JSRuntime.isJSNative(value2));
            return JSRuntime.toString(JSRuntime.toPrimitiveFromForeign(value2, JSToPrimitiveNode.Hint.String));
        }
        throw JSRuntime.toStringTypeError(value2);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString safeToString(Object value2) {
        return JSRuntime.toDisplayString(value2, false, ToDisplayStringFormat.getDefaultFormat());
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString toDisplayString(Object value2, boolean allowSideEffects) {
        return JSRuntime.toDisplayString(value2, allowSideEffects, ToDisplayStringFormat.getDefaultFormat());
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString toDisplayString(Object value2, boolean allowSideEffects, ToDisplayStringFormat format) {
        return JSRuntime.toDisplayStringImpl(value2, allowSideEffects, format, 0, null);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString toDisplayStringInner(Object value2, boolean allowSideEffects, ToDisplayStringFormat format, int currentDepth, Object parent) {
        return JSRuntime.toDisplayStringImpl(value2, allowSideEffects, format.withQuoteString(true), currentDepth + 1, parent);
    }

    public static TruffleString toDisplayStringImpl(Object value2, boolean allowSideEffects, ToDisplayStringFormat format, int depth, Object parent) {
        CompilerAsserts.neverPartOfCompilation();
        if (value2 == parent) {
            return Strings.PARENS_THIS;
        }
        if (value2 == Undefined.instance) {
            return Undefined.NAME;
        }
        if (value2 == Null.instance) {
            return Null.NAME;
        }
        if (value2 instanceof Boolean) {
            return JSRuntime.booleanToString((Boolean)value2);
        }
        if (value2 instanceof TruffleString) {
            return format.quoteString() ? JSRuntime.quote((TruffleString)value2) : (TruffleString)value2;
        }
        if (value2 instanceof String) {
            return format.quoteString() ? JSRuntime.quote(Strings.fromJavaString((String)value2)) : Strings.fromJavaString((String)value2);
        }
        if (JSObject.isJSObject(value2)) {
            return ((JSObject)value2).toDisplayStringImpl(allowSideEffects, format, depth);
        }
        if (value2 instanceof Symbol) {
            return ((Symbol)value2).toTString();
        }
        if (value2 instanceof BigInt) {
            return Strings.concat(((BigInt)value2).toTString(), Strings.N);
        }
        if (JSRuntime.isNumber(value2)) {
            Number number = (Number)value2;
            if (JSRuntime.isNegativeZero(number.doubleValue())) {
                return Strings.NEGATIVE_ZERO;
            }
            return JSRuntime.numberToString(number);
        }
        if (value2 instanceof InteropFunction) {
            return JSRuntime.toDisplayStringImpl(((InteropFunction)value2).getFunction(), allowSideEffects, format, depth, parent);
        }
        if (value2 instanceof TruffleObject) {
            assert (!JSRuntime.isJSNative(value2)) : value2;
            return JSRuntime.foreignToString(value2, allowSideEffects, format, depth);
        }
        return Strings.fromObject(value2);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString objectToDisplayString(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth, TruffleString name) {
        return JSRuntime.objectToDisplayString(obj, allowSideEffects, format, depth, name, null, null);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString objectToDisplayString(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth, TruffleString name, TruffleString[] internalKeys, Object[] internalValues) {
        assert (!JSFunction.isJSFunction(obj) && !JSProxy.isJSProxy(obj));
        boolean v8CompatMode = JSObject.getJSContext(obj).isOptionV8CompatibilityMode();
        TruffleStringBuilder sb = Strings.builderCreate();
        if (name != null) {
            Strings.builderAppend(sb, name);
        }
        boolean isArrayLike = false;
        boolean isArray = false;
        long length = -1L;
        if (JSArray.isJSArray(obj)) {
            isArrayLike = true;
            isArray = true;
            length = JSArray.arrayGetLength(obj);
        } else if (JSArrayBufferView.isJSArrayBufferView(obj)) {
            isArrayLike = true;
            length = JSArrayBufferView.typedArrayGetLength(obj);
        } else if (JSString.isJSString(obj)) {
            length = JSString.getStringLength(obj);
        }
        boolean isStringObj = JSString.isJSString(obj);
        long prevArrayIndex = -1L;
        if (isArrayLike) {
            if (length > 0L) {
                boolean topLevel;
                boolean bl = topLevel = depth == 0;
                if (depth >= format.getMaxDepth() || !topLevel && length > (long)format.getMaxElements()) {
                    if (name == null) {
                        Strings.builderAppend(sb, Strings.UC_ARRAY);
                    }
                    Strings.builderAppend(sb, Strings.PAREN_OPEN);
                    Strings.builderAppend(sb, length);
                    Strings.builderAppend(sb, Strings.PAREN_CLOSE);
                    return Strings.builderToString(sb);
                }
                if (topLevel && length >= 2L && !v8CompatMode && format.includeArrayLength()) {
                    Strings.builderAppend(sb, Strings.PAREN_OPEN);
                    Strings.builderAppend(sb, length);
                    Strings.builderAppend(sb, Strings.PAREN_CLOSE);
                }
            }
        } else if (depth >= format.getMaxDepth()) {
            Strings.builderAppend(sb, Strings.EMPTY_OBJECT_DOTS);
            return Strings.builderToString(sb);
        }
        char chr1 = isArrayLike ? (char)'[' : '{';
        Strings.builderAppend(sb, chr1);
        int propertyCount = 0;
        for (Object key : JSObject.ownPropertyKeys(obj)) {
            Property prop;
            if (!allowSideEffects && JSError.STACK_NAME.equals(key) && (prop = obj.getShape().getProperty(JSError.STACK_NAME)) != null && JSProperty.isProxy(prop)) continue;
            PropertyDescriptor desc = JSObject.getOwnProperty(obj, key);
            if ((isArrayLike || isStringObj) && key.equals(Strings.LENGTH) || isStringObj && JSRuntime.isArrayIndex(key) && JSRuntime.parseArrayIndexIsIndexRaw(key) < length) continue;
            if (propertyCount > 0) {
                Strings.builderAppend(sb, v8CompatMode ? "," : ", ");
                if (propertyCount >= format.getMaxElements()) {
                    Strings.builderAppend(sb, Strings.DOT_DOT_DOT);
                    break;
                }
            }
            if (isArray) {
                if (JSRuntime.isArrayIndex(key)) {
                    long index = JSRuntime.parseArrayIndexIsIndexRaw(key);
                    if (index < length && JSRuntime.fillEmptyArrayElements(sb, index, prevArrayIndex, false)) {
                        Strings.builderAppend(sb, ", ");
                        if (++propertyCount >= format.getMaxElements()) {
                            Strings.builderAppend(sb, "...");
                            break;
                        }
                    }
                    prevArrayIndex = index;
                } else {
                    if (JSRuntime.fillEmptyArrayElements(sb, length, prevArrayIndex, false)) {
                        Strings.builderAppend(sb, Strings.COMMA_SPC);
                        if (++propertyCount >= format.getMaxElements()) {
                            Strings.builderAppend(sb, "...");
                            break;
                        }
                    }
                    prevArrayIndex = Math.max(prevArrayIndex, length);
                }
            }
            if (!isArrayLike || !JSRuntime.isArrayIndex(key)) {
                Strings.builderAppend(sb, Strings.fromObject(key));
                Strings.builderAppend(sb, ": ");
            }
            TruffleString valueStr = null;
            if (desc.isDataDescriptor()) {
                Object value2 = desc.getValue();
                valueStr = JSRuntime.toDisplayStringInner(value2, allowSideEffects, format, depth, obj);
            } else {
                valueStr = desc.isAccessorDescriptor() ? Strings.ACCESSOR : Strings.EMPTY;
            }
            Strings.builderAppend(sb, valueStr);
            ++propertyCount;
        }
        if (isArray && propertyCount < format.getMaxElements() && JSRuntime.fillEmptyArrayElements(sb, length, prevArrayIndex, propertyCount > 0)) {
            ++propertyCount;
        }
        if (internalKeys != null) {
            assert (internalValues != null && internalKeys.length == internalValues.length);
            for (int i = 0; i < internalKeys.length; ++i) {
                if (propertyCount > 0) {
                    Strings.builderAppend(sb, Strings.COMMA_SPC);
                }
                Strings.builderAppend(sb, Strings.BRACKET_OPEN_2);
                Strings.builderAppend(sb, internalKeys[i]);
                Strings.builderAppend(sb, Strings.BRACKET_CLOSE_2_COLON);
                Strings.builderAppend(sb, JSRuntime.toDisplayStringInner(internalValues[i], allowSideEffects, format, depth, obj));
                ++propertyCount;
            }
        }
        char chr = isArrayLike ? (char)']' : '}';
        Strings.builderAppend(sb, chr);
        return Strings.builderToString(sb);
    }

    private static TruffleString foreignToString(Object value2, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        CompilerAsserts.neverPartOfCompilation();
        try {
            InteropLibrary interop = InteropLibrary.getUncached(value2);
            if (interop.isNull(value2)) {
                return Strings.NULL;
            }
            if (interop.hasArrayElements(value2)) {
                return JSRuntime.foreignArrayToString(value2, allowSideEffects, format, depth);
            }
            if (interop.isString(value2)) {
                return format.quoteString() ? Strings.fromJavaString(JSRuntime.quote(interop.asString(value2))) : interop.asTruffleString(value2);
            }
            if (interop.isBoolean(value2)) {
                return JSRuntime.booleanToString(interop.asBoolean(value2));
            }
            if (interop.isNumber(value2)) {
                Object unboxed = Strings.UC_NUMBER;
                if (interop.fitsInInt(value2)) {
                    unboxed = interop.asInt(value2);
                } else if (interop.fitsInLong(value2)) {
                    unboxed = interop.asLong(value2);
                } else if (interop.fitsInDouble(value2)) {
                    unboxed = interop.asDouble(value2);
                }
                return JSRuntime.toDisplayString(unboxed, allowSideEffects, format);
            }
            if (JavaScriptLanguage.getCurrentEnv().isHostObject(value2)) {
                return JSRuntime.hostObjectToString(value2, interop);
            }
            if (interop.isMetaObject(value2)) {
                return InteropLibrary.getUncached().asTruffleString(interop.getMetaQualifiedName(value2));
            }
            if (interop.hasMembers(value2) && !interop.isExecutable(value2) && !interop.isInstantiable(value2)) {
                return JSRuntime.foreignObjectToString(value2, allowSideEffects, format, depth);
            }
            return InteropLibrary.getUncached().asTruffleString(interop.toDisplayString(value2, allowSideEffects));
        }
        catch (InteropException e) {
            return Strings.UC_OBJECT;
        }
    }

    private static TruffleString hostObjectToString(Object value2, InteropLibrary interop) throws UnsupportedMessageException {
        if (interop.isMetaObject(value2)) {
            return Strings.concatAll(Strings.JAVA_CLASS_BRACKET, InteropLibrary.getUncached().asTruffleString(interop.getMetaQualifiedName(value2)), Strings.BRACKET_CLOSE);
        }
        Object metaObject = interop.getMetaObject(value2);
        return Strings.concatAll(Strings.JAVA_OBJECT_BRACKET, InteropLibrary.getUncached().asTruffleString(InteropLibrary.getUncached().getMetaQualifiedName(metaObject)), Strings.BRACKET_CLOSE);
    }

    private static TruffleString foreignArrayToString(Object truffleObject, boolean allowSideEffects, ToDisplayStringFormat format, int depth) throws InteropException {
        CompilerAsserts.neverPartOfCompilation();
        InteropLibrary interop = InteropLibrary.getFactory().getUncached(truffleObject);
        assert (interop.hasArrayElements(truffleObject));
        long size = interop.getArraySize(truffleObject);
        if (size == 0L) {
            return Strings.EMPTY_ARRAY;
        }
        if (depth >= format.getMaxDepth()) {
            return Strings.concatAll(Strings.ARRAY_PAREN_OPEN, Strings.fromLong(size), Strings.PAREN_CLOSE);
        }
        boolean topLevel = depth == 0;
        TruffleStringBuilder sb = Strings.builderCreate();
        if (topLevel && size >= 2L && format.includeArrayLength()) {
            Strings.builderAppend(sb, Strings.PAREN_OPEN);
            Strings.builderAppend(sb, size);
            Strings.builderAppend(sb, Strings.PAREN_CLOSE);
        }
        Strings.builderAppend(sb, '[');
        for (long i = 0L; i < size; ++i) {
            if (i > 0L) {
                Strings.builderAppend(sb, Strings.COMMA_SPC);
                if (i >= (long)format.getMaxElements()) {
                    Strings.builderAppend(sb, Strings.DOT_DOT_DOT);
                    break;
                }
            }
            Object value2 = interop.readArrayElement(truffleObject, i);
            Strings.builderAppend(sb, JSRuntime.toDisplayStringInner(value2, allowSideEffects, format, depth, truffleObject));
        }
        Strings.builderAppend(sb, ']');
        return Strings.builderToString(sb);
    }

    private static TruffleString foreignObjectToString(Object truffleObject, boolean allowSideEffects, ToDisplayStringFormat format, int depth) throws InteropException {
        CompilerAsserts.neverPartOfCompilation();
        InteropLibrary objInterop = InteropLibrary.getFactory().getUncached(truffleObject);
        assert (objInterop.hasMembers(truffleObject));
        if (allowSideEffects && objInterop.isMemberInvocable(truffleObject, Strings.TO_STRING_JLS)) {
            return JSRuntime.toString(objInterop.invokeMember(truffleObject, Strings.TO_STRING_JLS, new Object[0]));
        }
        Object keys = objInterop.getMembers(truffleObject);
        InteropLibrary keysInterop = InteropLibrary.getFactory().getUncached(keys);
        long keyCount = keysInterop.getArraySize(keys);
        if (keyCount == 0L) {
            return Strings.EMPTY_OBJECT;
        }
        if (depth >= format.getMaxDepth()) {
            return Strings.EMPTY_OBJECT_DOTS;
        }
        TruffleStringBuilder sb = Strings.builderCreate();
        Strings.builderAppend(sb, '{');
        for (long i = 0L; i < keyCount; ++i) {
            if (i > 0L) {
                Strings.builderAppend(sb, Strings.COMMA_SPC);
                if (i >= (long)format.getMaxElements()) {
                    Strings.builderAppend(sb, Strings.DOT_DOT_DOT);
                    break;
                }
            }
            Object key = keysInterop.readArrayElement(keys, i);
            assert (InteropLibrary.getUncached().isString(key));
            String stringKey = Strings.interopAsString(key);
            Object value2 = objInterop.readMember(truffleObject, stringKey);
            Strings.builderAppend(sb, stringKey);
            Strings.builderAppend(sb, Strings.COLON_SPACE);
            Strings.builderAppend(sb, JSRuntime.toDisplayStringInner(value2, allowSideEffects, format, depth, truffleObject));
        }
        Strings.builderAppend(sb, '}');
        return Strings.builderToString(sb);
    }

    private static boolean fillEmptyArrayElements(TruffleStringBuilder sb, long index, long prevArrayIndex, boolean prependComma) {
        if (prevArrayIndex < index - 1L) {
            long count;
            if (prependComma) {
                Strings.builderAppend(sb, Strings.COMMA_SPC);
            }
            if ((count = index - prevArrayIndex - 1L) == 1L) {
                Strings.builderAppend(sb, Strings.EMPTY);
            } else {
                Strings.builderAppend(sb, Strings.EMPTY_X);
                Strings.builderAppend(sb, count);
            }
            return true;
        }
        return false;
    }

    public static TruffleString collectionToConsoleString(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, TruffleString name, JSHashMap map, int depth) {
        assert (JSMap.isJSMap(obj) || JSSet.isJSSet(obj));
        assert (name != null);
        int size = map.size();
        TruffleStringBuilder sb = Strings.builderCreate();
        Strings.builderAppend(sb, name);
        Strings.builderAppend(sb, Strings.PAREN_OPEN);
        Strings.builderAppend(sb, size);
        Strings.builderAppend(sb, Strings.PAREN_CLOSE);
        if (size > 0 && depth < format.getMaxDepth()) {
            Strings.builderAppend(sb, '{');
            boolean isMap = JSMap.isJSMap(obj);
            boolean isFirst = true;
            JSHashMap.Cursor cursor = map.getEntries();
            while (cursor.advance()) {
                Object key = cursor.getKey();
                if (key == null) continue;
                if (!isFirst) {
                    Strings.builderAppend(sb, Strings.COMMA_SPC);
                }
                Strings.builderAppend(sb, JSRuntime.toDisplayStringInner(key, allowSideEffects, format, depth, obj));
                if (isMap) {
                    Strings.builderAppend(sb, Strings.BIG_ARROW_SPACES);
                    Object value2 = cursor.getValue();
                    Strings.builderAppend(sb, JSRuntime.toDisplayStringInner(value2, allowSideEffects, format, depth, obj));
                }
                isFirst = false;
            }
            Strings.builderAppend(sb, '}');
        }
        return Strings.builderToString(sb);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException toStringTypeError(Object value2) {
        String what = value2 == null ? "null" : (JSDynamicObject.isJSDynamicObject(value2) ? Strings.toJavaString(JSObject.defaultToString((JSDynamicObject)value2)) : value2.getClass().getName());
        throw Errors.createTypeErrorCannotConvertToString(what);
    }

    public static TruffleString booleanToString(boolean value2) {
        return value2 ? JSBoolean.TRUE_NAME : JSBoolean.FALSE_NAME;
    }

    public static TruffleString toString(JSDynamicObject value2) {
        if (value2 == Undefined.instance) {
            return Undefined.NAME;
        }
        if (value2 == Null.instance) {
            return Null.NAME;
        }
        return JSRuntime.toString(JSObject.toPrimitive(value2, JSToPrimitiveNode.Hint.String));
    }

    public static String numberToJavaString(Number number) {
        return Strings.toJavaString(JSRuntime.numberToString(number));
    }

    public static TruffleString numberToString(Number number) {
        CompilerAsserts.neverPartOfCompilation();
        if (number instanceof Integer) {
            return Strings.fromInt((Integer)number);
        }
        if (number instanceof SafeInteger) {
            return JSRuntime.doubleToString(((SafeInteger)number).doubleValue());
        }
        if (number instanceof Double) {
            return JSRuntime.doubleToString((Double)number);
        }
        if (number instanceof Long) {
            return Strings.fromLong(number.longValue());
        }
        throw new UnsupportedOperationException("unknown number value: " + number.toString() + " " + number.getClass().getSimpleName());
    }

    public static String javaToString(Object obj) {
        if (obj instanceof String) {
            return (String)obj;
        }
        if (Strings.isTString(obj)) {
            return Strings.toJavaString((TruffleString)obj);
        }
        return Boundaries.javaToString(obj);
    }

    public static boolean propertyKeyEquals(TruffleString.EqualNode equalsNode, Object a, Object b) {
        assert (JSRuntime.isPropertyKey(a));
        if (Strings.isTString(a)) {
            if (Strings.isTString(b)) {
                return Strings.equals(equalsNode, (TruffleString)a, (TruffleString)b);
            }
            return false;
        }
        if (a instanceof Symbol) {
            return ((Symbol)a).equals(b);
        }
        throw Errors.shouldNotReachHere();
    }

    @CompilerDirectives.TruffleBoundary
    public static Object doubleToString(double d, int radix) {
        assert (radix >= 2 && radix <= 36);
        if (Double.isNaN(d)) {
            return Strings.NAN;
        }
        if (d == Double.POSITIVE_INFINITY) {
            return Strings.INFINITY;
        }
        if (d == Double.NEGATIVE_INFINITY) {
            return Strings.NEGATIVE_INFINITY;
        }
        if (d == 0.0) {
            return Strings.ZERO;
        }
        return JSRuntime.formatDtoA(d, radix);
    }

    public static TruffleString doubleToString(double d) {
        if (Double.isNaN(d)) {
            return Strings.NAN;
        }
        if (d == Double.POSITIVE_INFINITY) {
            return Strings.INFINITY;
        }
        if (d == Double.NEGATIVE_INFINITY) {
            return Strings.NEGATIVE_INFINITY;
        }
        if (d == 0.0) {
            return Strings.ZERO;
        }
        if (JSRuntime.doubleIsRepresentableAsInt(d)) {
            return Strings.fromInt((int)d);
        }
        return Strings.fromJavaString(JSRuntime.formatDtoA(d));
    }

    @CompilerDirectives.TruffleBoundary
    public static String formatDtoA(double value2) {
        return DoubleConversion.toShortest(value2);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object formatDtoAPrecision(double value2, int precision) {
        return Strings.fromJavaString(DoubleConversion.toPrecision(value2, precision));
    }

    @CompilerDirectives.TruffleBoundary
    public static Object formatDtoAExponential(double d, int digits) {
        return Strings.fromJavaString(DoubleConversion.toExponential(d, digits));
    }

    @CompilerDirectives.TruffleBoundary
    public static Object formatDtoAExponential(double d) {
        return Strings.fromJavaString(DoubleConversion.toExponential(d, -1));
    }

    @CompilerDirectives.TruffleBoundary
    public static Object formatDtoAFixed(double value2, int digits) {
        return Strings.fromJavaString(DoubleConversion.toFixed(value2, digits));
    }

    @CompilerDirectives.TruffleBoundary
    public static Object formatDtoA(double d, int radix) {
        return Strings.fromJavaString(DToA.jsDtobasestr(radix, d));
    }

    public static TruffleObject toObject(JSContext ctx, Object value2) {
        JSRuntime.requireObjectCoercible(value2, ctx);
        if (CompilerDirectives.injectBranchProbability(0.75, JSObject.isJSObject(value2))) {
            return (JSObject)value2;
        }
        Object unboxedValue = value2;
        if (JSRuntime.isForeignObject(value2)) {
            InteropLibrary interop = InteropLibrary.getUncached(value2);
            assert (!interop.isNull(value2));
            unboxedValue = JSInteropUtil.toPrimitiveOrDefault(value2, null, interop, null);
            if (unboxedValue == null) {
                return (TruffleObject)value2;
            }
        }
        return JSRuntime.toObjectFromPrimitive(ctx, unboxedValue, true);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleObject toObjectFromPrimitive(JSContext ctx, Object value2, boolean useJavaWrapper) {
        JSRealm realm = JSRealm.get(null);
        if (value2 instanceof Boolean) {
            return JSBoolean.create(ctx, realm, (Boolean)value2);
        }
        if (Strings.isTString(value2)) {
            return JSString.create(ctx, realm, (TruffleString)value2);
        }
        if (value2 instanceof BigInt) {
            return JSBigInt.create(ctx, realm, (BigInt)value2);
        }
        if (JSRuntime.isNumber(value2)) {
            return JSNumber.create(ctx, realm, (Number)value2);
        }
        if (value2 instanceof Symbol) {
            return JSSymbol.create(ctx, realm, (Symbol)value2);
        }
        assert (!JSRuntime.isJSNative(value2) && JSRuntime.isJavaPrimitive(value2)) : value2;
        if (useJavaWrapper) {
            return (TruffleObject)realm.getEnv().asBoxedGuestValue(value2);
        }
        return null;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isSameValue(Object x, Object y) {
        if (x == Undefined.instance && y == Undefined.instance) {
            return true;
        }
        if (x == Null.instance && y == Null.instance) {
            return true;
        }
        if (x instanceof Integer && y instanceof Integer) {
            return ((Integer)x).intValue() == ((Integer)y).intValue();
        }
        if (JSRuntime.isNumber(x) && JSRuntime.isNumber(y)) {
            double yd;
            double xd = JSRuntime.doubleValue((Number)x);
            return Double.compare(xd, yd = JSRuntime.doubleValue((Number)y)) == 0;
        }
        if (Strings.isTString(x) && Strings.isTString(y)) {
            return x.toString().equals(y.toString());
        }
        if (x instanceof Boolean && y instanceof Boolean) {
            return ((Boolean)x).booleanValue() == ((Boolean)y).booleanValue();
        }
        if (JSRuntime.isBigInt(x) && JSRuntime.isBigInt(y)) {
            return ((BigInt)x).compareTo((BigInt)y) == 0;
        }
        return x == y;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean equal(Object a, Object b) {
        if (a == b) {
            return true;
        }
        if (JSRuntime.isNullOrUndefined(a)) {
            return JSRuntime.isNullish(b);
        }
        if (JSRuntime.isNullOrUndefined(b)) {
            return JSRuntime.isNullish(a);
        }
        if (a instanceof Boolean && b instanceof Boolean) {
            return a.equals(b);
        }
        if (Strings.isTString(a) && Strings.isTString(b)) {
            return a.toString().equals(b.toString());
        }
        if (JSRuntime.isJavaNumber(a) && JSRuntime.isJavaNumber(b)) {
            double db;
            double da = JSRuntime.doubleValue((Number)a);
            return da == (db = JSRuntime.doubleValue((Number)b));
        }
        if (JSRuntime.isJavaNumber(a) && Strings.isTString(b)) {
            return JSRuntime.equal(a, JSRuntime.stringToNumber((TruffleString)b));
        }
        if (Strings.isTString(a) && JSRuntime.isJavaNumber(b)) {
            return JSRuntime.equal(JSRuntime.stringToNumber((TruffleString)a), b);
        }
        if (JSRuntime.isBigInt(a) && JSRuntime.isBigInt(b)) {
            return a.equals(b);
        }
        if (JSRuntime.isBigInt(a) && Strings.isTString(b)) {
            return a.equals(JSRuntime.stringToBigInt((TruffleString)b));
        }
        if (Strings.isTString(a) && JSRuntime.isBigInt(b)) {
            return b.equals(JSRuntime.stringToBigInt((TruffleString)a));
        }
        if (JSRuntime.isJavaNumber(a) && JSRuntime.isBigInt(b)) {
            return JSRuntime.equalBigIntAndNumber((BigInt)b, (Number)a);
        }
        if (JSRuntime.isBigInt(a) && JSRuntime.isJavaNumber(b)) {
            return JSRuntime.equalBigIntAndNumber((BigInt)a, (Number)b);
        }
        if (a instanceof Boolean) {
            return JSRuntime.equal(JSRuntime.booleanToNumber((Boolean)a), b);
        }
        if (b instanceof Boolean) {
            return JSRuntime.equal(a, JSRuntime.booleanToNumber((Boolean)b));
        }
        if (JSRuntime.isObject(a)) {
            assert (b != Undefined.instance && b != Null.instance);
            if (JSOverloadedOperatorsObject.hasOverloadedOperators(a)) {
                if (JSRuntime.isObject(b) && !JSOverloadedOperatorsObject.hasOverloadedOperators(b)) {
                    return JSRuntime.equal(a, JSObject.toPrimitive((JSDynamicObject)b));
                }
                if (JSRuntime.isObject(b) || JSRuntime.isNumber(b) || JSRuntime.isBigInt(b) || Strings.isTString(b)) {
                    return JSRuntime.equalOverloaded(a, b);
                }
                return false;
            }
            if (IsPrimitiveNode.getUncached().executeBoolean(b)) {
                if (JSRuntime.isNullish(b)) {
                    return false;
                }
                return JSRuntime.equal(JSObject.toPrimitive((JSDynamicObject)a), b);
            }
        } else if (JSRuntime.isObject(b)) {
            assert (a != Undefined.instance && a != Null.instance);
            assert (!JSRuntime.isObject(a));
            if (JSOverloadedOperatorsObject.hasOverloadedOperators(b)) {
                if (JSRuntime.isNumber(a) || JSRuntime.isBigInt(a) || Strings.isTString(a)) {
                    return JSRuntime.equalOverloaded(a, b);
                }
                return false;
            }
            if (IsPrimitiveNode.getUncached().executeBoolean(a)) {
                if (JSRuntime.isNullish(a)) {
                    return false;
                }
                return JSRuntime.equal(a, JSObject.toPrimitive((JSDynamicObject)b));
            }
        }
        if (JSRuntime.isForeignObject(a) || JSRuntime.isForeignObject(b)) {
            return JSRuntime.equalInterop(a, b);
        }
        return false;
    }

    public static boolean isForeignObject(Object value2) {
        return value2 instanceof TruffleObject && JSRuntime.isForeignObject((TruffleObject)value2);
    }

    public static boolean isForeignObject(TruffleObject value2) {
        return !JSDynamicObject.isJSDynamicObject(value2) && !(value2 instanceof Symbol) && !(value2 instanceof SafeInteger) && !(value2 instanceof BigInt) && !Strings.isTString(value2);
    }

    private static boolean equalInterop(Object a, Object b) {
        Object primRight;
        assert (a != null && b != null && (JSRuntime.isForeignObject(a) || JSRuntime.isForeignObject(b)));
        boolean isAPrimitive = IsPrimitiveNode.getUncached().executeBoolean(a);
        boolean isBPrimitive = IsPrimitiveNode.getUncached().executeBoolean(b);
        if (!isAPrimitive && !isBPrimitive) {
            return InteropLibrary.getUncached(a).isIdentical(a, b, InteropLibrary.getUncached(b));
        }
        if (JSRuntime.isNullish(a)) {
            return JSRuntime.isNullish(b);
        }
        if (JSRuntime.isNullish(b)) {
            assert (!JSRuntime.isNullish(a));
            return false;
        }
        Object primLeft = !isAPrimitive || JSRuntime.isForeignObject(a) ? JSRuntime.toPrimitive(a) : a;
        Object object = primRight = !isBPrimitive || JSRuntime.isForeignObject(b) ? JSRuntime.toPrimitive(b) : b;
        assert (!JSRuntime.isForeignObject(primLeft) && !JSRuntime.isForeignObject(primRight));
        return JSRuntime.equal(primLeft, primRight);
    }

    private static boolean equalBigIntAndNumber(BigInt a, Number b) {
        if (b instanceof Double || b instanceof Float) {
            double numberVal = JSRuntime.doubleValue(b);
            return !Double.isNaN(numberVal) && a.compareValueTo(numberVal) == 0;
        }
        return a.compareValueTo(JSRuntime.longValue(b)) == 0;
    }

    private static boolean equalOverloaded(Object a, Object b) {
        Object operatorImplementation = OperatorSet.getOperatorImplementation(a, b, Strings.SYMBOL_EQUALS_EQUALS);
        if (operatorImplementation == null) {
            return false;
        }
        return JSRuntime.toBoolean(JSRuntime.call(operatorImplementation, Undefined.instance, new Object[]{a, b}));
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean identical(Object a, Object b) {
        InteropLibrary bInterop;
        if (a == b) {
            if (a instanceof Double) {
                return !Double.isNaN((Double)a);
            }
            return true;
        }
        if (a == Undefined.instance || b == Undefined.instance) {
            return false;
        }
        if (a == Null.instance) {
            assert (b != Undefined.instance);
            return InteropLibrary.getUncached(b).isNull(b);
        }
        if (b == Null.instance) {
            assert (a != Undefined.instance);
            return InteropLibrary.getUncached(a).isNull(a);
        }
        if (JSRuntime.isBigInt(a) && JSRuntime.isBigInt(b)) {
            return a.equals(b);
        }
        if (JSRuntime.isJavaNumber(a) && JSRuntime.isJavaNumber(b)) {
            if (a instanceof Integer && b instanceof Integer) {
                return ((Integer)a).intValue() == ((Integer)b).intValue();
            }
            return JSRuntime.doubleValue((Number)a) == JSRuntime.doubleValue((Number)b);
        }
        if (a instanceof Boolean && b instanceof Boolean) {
            return a.equals(b);
        }
        if (Strings.isTString(a) && Strings.isTString(b)) {
            return a.toString().equals(b.toString());
        }
        if (JSRuntime.isObject(a) || JSRuntime.isObject(b)) {
            return false;
        }
        InteropLibrary aInterop = InteropLibrary.getUncached(a);
        return aInterop.isIdentical(a, b, bInterop = InteropLibrary.getUncached(b)) || aInterop.isNull(a) && bInterop.isNull(b);
    }

    public static <T> T requireObjectCoercible(T argument, JSContext context) {
        if (argument == Undefined.instance || argument == Null.instance || JSRuntime.isForeignObject(argument) && InteropLibrary.getUncached(argument).isNull(argument)) {
            throw Errors.createTypeErrorNotObjectCoercible(argument, null, context);
        }
        return argument;
    }

    @CompilerDirectives.TruffleBoundary
    public static PropertyDescriptor toPropertyDescriptor(Object property) {
        boolean hasSet;
        boolean hasGet;
        boolean hasWritable;
        boolean hasValue;
        if (!JSRuntime.isObject(property)) {
            throw Errors.createTypeErrorNotAnObject(property);
        }
        JSDynamicObject obj = (JSDynamicObject)property;
        PropertyDescriptor desc = PropertyDescriptor.createEmpty();
        if (JSObject.hasProperty(obj, JSAttributes.ENUMERABLE)) {
            desc.setEnumerable(JSRuntime.toBoolean(JSObject.get(obj, JSAttributes.ENUMERABLE)));
        }
        if (JSObject.hasProperty(obj, JSAttributes.CONFIGURABLE)) {
            desc.setConfigurable(JSRuntime.toBoolean(JSObject.get(obj, JSAttributes.CONFIGURABLE)));
        }
        if (hasValue = JSObject.hasProperty(obj, JSAttributes.VALUE)) {
            desc.setValue(JSObject.get(obj, JSAttributes.VALUE));
        }
        if (hasWritable = JSObject.hasProperty(obj, JSAttributes.WRITABLE)) {
            desc.setWritable(JSRuntime.toBoolean(JSObject.get(obj, JSAttributes.WRITABLE)));
        }
        if (hasGet = JSObject.hasProperty(obj, JSAttributes.GET)) {
            Object getter = JSObject.get(obj, JSAttributes.GET);
            if (!JSRuntime.isCallable(getter) && getter != Undefined.instance) {
                throw Errors.createTypeError("Getter must be a function");
            }
            desc.setGet(getter);
        }
        if (hasSet = JSObject.hasProperty(obj, JSAttributes.SET)) {
            Object setter = JSObject.get(obj, JSAttributes.SET);
            if (!JSRuntime.isCallable(setter) && setter != Undefined.instance) {
                throw Errors.createTypeError("Setter must be a function");
            }
            desc.setSet(setter);
        }
        if ((hasGet || hasSet) && (hasValue || hasWritable)) {
            throw Errors.createTypeError("Invalid property. A property cannot both have accessors and be writable or have a value");
        }
        return desc;
    }

    public static int valueInRadix10(char c) {
        if (JSRuntime.isAsciiDigit(c)) {
            return c - 48;
        }
        return -1;
    }

    public static int valueInRadix(char c, int radix) {
        int val = JSRuntime.valueInRadixIntl(c);
        return val < radix ? val : -1;
    }

    private static int valueInRadixIntl(char c) {
        if (JSRuntime.isAsciiDigit(c)) {
            return c - 48;
        }
        if ('a' <= c && c <= 'z') {
            return c - 97 + 10;
        }
        if ('A' <= c && c <= 'Z') {
            return c - 65 + 10;
        }
        return -1;
    }

    public static int valueInHex(char c) {
        if (JSRuntime.isAsciiDigit(c)) {
            return c - 48;
        }
        if ('a' <= c && c <= 'f') {
            return c - 97 + 10;
        }
        if ('A' <= c && c <= 'F') {
            return c - 65 + 10;
        }
        return -1;
    }

    public static boolean isHex(char c) {
        return JSRuntime.isAsciiDigit(c) || 'a' <= c && c <= 'f' || 'A' <= c && c <= 'F';
    }

    @CompilerDirectives.TruffleBoundary
    public static long parseArrayIndexIsIndexRaw(Object o) {
        assert (JSRuntime.isArrayIndex(o));
        assert (Strings.isTString(o) || o instanceof Number);
        return JSRuntime.parseArrayIndexRaw(Strings.isTString(o) ? (TruffleString)o : Strings.fromNumber((Number)o), TruffleString.ReadCharUTF16Node.getUncached());
    }

    public static long parseArrayIndexRaw(TruffleString string, TruffleString.ReadCharUTF16Node charAtNode) {
        int pos;
        long value2 = 0L;
        int len = Strings.length(string);
        if (len > 1 && Strings.charAt(charAtNode, string, pos) == '0') {
            return -1L;
        }
        for (pos = 0; pos < len; ++pos) {
            char c = Strings.charAt(charAtNode, string, pos);
            if (!JSRuntime.isAsciiDigit(c)) {
                return -1L;
            }
            value2 *= 10L;
            value2 += (long)(c - 48);
        }
        return value2;
    }

    public static TruffleString trimJSWhiteSpace(TruffleString string) {
        return JSRuntime.trimJSWhiteSpace(string, false);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString trimJSWhiteSpace(TruffleString string, boolean useLineTerminators) {
        int firstIdx = JSRuntime.firstNonWhitespaceIndex(string, useLineTerminators, TruffleString.ReadCharUTF16Node.getUncached());
        int lastIdx = JSRuntime.lastNonWhitespaceIndex(string, useLineTerminators, TruffleString.ReadCharUTF16Node.getUncached());
        if (firstIdx == 0) {
            if (lastIdx + 1 == Strings.length(string)) {
                return string;
            }
        } else if (firstIdx > lastIdx) {
            return Strings.EMPTY_STRING;
        }
        return Strings.lazySubstring(string, firstIdx, lastIdx + 1 - firstIdx);
    }

    public static int firstNonWhitespaceIndex(TruffleString string, boolean useLineTerminators, TruffleString.ReadCharUTF16Node charAtNode) {
        int idx;
        for (idx = 0; idx < Strings.length(string) && (JSRuntime.isWhiteSpace(Strings.charAt(charAtNode, string, idx)) || useLineTerminators && JSRuntime.isLineTerminator(Strings.charAt(charAtNode, string, idx))); ++idx) {
        }
        return idx;
    }

    public static int lastNonWhitespaceIndex(TruffleString string, boolean useLineTerminators, TruffleString.ReadCharUTF16Node charAtNode) {
        int idx;
        for (idx = Strings.length(string) - 1; idx >= 0 && (JSRuntime.isWhiteSpace(Strings.charAt(charAtNode, string, idx)) || useLineTerminators && JSRuntime.isLineTerminator(Strings.charAt(charAtNode, string, idx))); --idx) {
        }
        return idx;
    }

    public static boolean isWhiteSpace(char cp) {
        if (JSRuntime.isAsciiDigit(cp)) {
            return false;
        }
        return '\t' <= cp && cp <= '\r' || '\u2000' <= cp && cp <= '\u200a' || cp == ' ' || cp == '\u00a0' || cp == '\u1680' || cp == '\u2028' || cp == '\u2029' || cp == '\u202f' || cp == '\u205f' || cp == '\u3000' || cp == '\ufeff';
    }

    private static boolean isLineTerminator(char codePoint) {
        switch (codePoint) {
            case '\n': 
            case '\r': 
            case '\u2028': 
            case '\u2029': {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidArrayLength(long longValue) {
        return 0L <= longValue && longValue <= 0xFFFFFFFFL;
    }

    public static boolean isValidArrayLength(double doubleValue) {
        long longValue = (long)doubleValue;
        return doubleValue == (double)longValue && JSRuntime.isValidArrayLength(longValue);
    }

    public static boolean isValidArrayLength(int intValue) {
        return intValue >= 0;
    }

    public static boolean isIntegerIndex(long longValue) {
        return 0L <= longValue && longValue <= MAX_SAFE_INTEGER_LONG;
    }

    public static boolean isArrayIndex(int intValue) {
        return intValue >= 0;
    }

    public static boolean isArrayIndex(long longValue) {
        return 0L <= longValue && longValue < 0xFFFFFFFFL;
    }

    public static boolean isArrayIndex(double doubleValue) {
        long longValue = (long)doubleValue;
        return (double)longValue == doubleValue && JSRuntime.isArrayIndex(longValue);
    }

    public static boolean isArrayIndexString(TruffleString property) {
        long idx = JSRuntime.propertyNameToArrayIndex(property, TruffleString.ReadCharUTF16Node.getUncached());
        return JSRuntime.isArrayIndex(idx);
    }

    public static boolean isArrayIndex(Object property) {
        if (property instanceof Integer) {
            return JSRuntime.isArrayIndex((Integer)property);
        }
        if (property instanceof Long) {
            return JSRuntime.isArrayIndex((Long)property);
        }
        if (property instanceof Double) {
            return JSRuntime.isArrayIndex((Double)property);
        }
        if (Strings.isTString(property)) {
            long idx = JSRuntime.propertyNameToArrayIndex(JSRuntime.toStringIsString(property), TruffleString.ReadCharUTF16Node.getUncached());
            return JSRuntime.isArrayIndex(idx);
        }
        return false;
    }

    public static long castArrayIndex(double doubleValue) {
        assert (JSRuntime.isArrayIndex(doubleValue));
        return (long)doubleValue & 0xFFFFFFFFL;
    }

    public static long castArrayIndex(long longValue) {
        assert (JSRuntime.isArrayIndex(longValue));
        return longValue;
    }

    public static boolean isAsciiDigit(char c) {
        return '0' <= c && c <= '9';
    }

    @CompilerDirectives.TruffleBoundary
    public static long propertyNameToArrayIndex(TruffleString propertyName, TruffleString.ReadCharUTF16Node charAtNode) {
        if (propertyName != null && JSRuntime.arrayIndexLengthInRange(propertyName) && JSRuntime.isAsciiDigit(Strings.charAt(propertyName, 0))) {
            return JSRuntime.parseArrayIndexRaw(propertyName, charAtNode);
        }
        return -1L;
    }

    public static boolean arrayIndexLengthInRange(TruffleString indexStr) {
        int len = Strings.length(indexStr);
        return 0 < len && len <= 10;
    }

    public static long propertyKeyToArrayIndex(Object propertyKey) {
        return Strings.isTString(propertyKey) ? JSRuntime.propertyNameToArrayIndex((TruffleString)propertyKey, TruffleString.ReadCharUTF16Node.getUncached()) : -1L;
    }

    @CompilerDirectives.TruffleBoundary
    public static long propertyNameToIntegerIndex(TruffleString propertyName) {
        if (propertyName != null && Strings.length(propertyName) > 0 && Strings.length(propertyName) <= 16 && JSRuntime.isAsciiDigit(Strings.charAt(propertyName, 0))) {
            return JSRuntime.parseArrayIndexRaw(propertyName, TruffleString.ReadCharUTF16Node.getUncached());
        }
        return -1L;
    }

    public static long propertyKeyToIntegerIndex(Object propertyKey) {
        return Strings.isTString(propertyKey) ? JSRuntime.propertyNameToIntegerIndex((TruffleString)propertyKey) : -1L;
    }

    public static boolean isJSNative(Object value2) {
        return JSDynamicObject.isJSDynamicObject(value2) || JSRuntime.isJSPrimitive(value2);
    }

    public static boolean isJSPrimitive(Object value2) {
        return JSRuntime.isNumber(value2) || value2 instanceof BigInt || value2 instanceof Boolean || Strings.isTString(value2) || value2 == Undefined.instance || value2 == Null.instance || value2 instanceof Symbol;
    }

    public static TruffleString toStringIsString(Object value2) {
        assert (Strings.isTString(value2));
        return (TruffleString)value2;
    }

    public static boolean isStringClass(Class<?> clazz) {
        return TruffleString.class.isAssignableFrom(clazz);
    }

    public static Object nullToUndefined(Object value2) {
        return value2 == null ? Undefined.instance : value2;
    }

    public static Object undefinedToNull(Object value2) {
        return value2 == Undefined.instance ? null : value2;
    }

    public static Object toJSNull(Object value2) {
        return value2 == null ? Null.instance : value2;
    }

    public static Object toJavaNull(Object value2) {
        return value2 == Null.instance ? null : value2;
    }

    @CompilerDirectives.TruffleBoundary
    public static Object jsObjectToJavaObject(Object obj) {
        return JSRuntime.toJavaNull(JSRuntime.undefinedToNull(obj));
    }

    public static boolean isPropertyKey(Object key) {
        return Strings.isTString(key) || key instanceof Symbol;
    }

    public static Object boxIndex(long longIndex, ConditionProfile indexInIntRangeConditionProfile) {
        if (indexInIntRangeConditionProfile.profile(longIndex <= Integer.MAX_VALUE)) {
            return (int)longIndex;
        }
        return (double)longIndex;
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInt stringToBigInt(TruffleString s) {
        try {
            return Strings.parseBigInt(s);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    public static int intValue(Number number) {
        if (number instanceof Integer) {
            return (Integer)number;
        }
        if (number instanceof Double) {
            return ((Double)number).intValue();
        }
        return JSRuntime.intValueVirtual(number);
    }

    @CompilerDirectives.TruffleBoundary
    public static int intValueVirtual(Number number) {
        return number.intValue();
    }

    public static double doubleValue(Number number) {
        if (number instanceof Double) {
            return (Double)number;
        }
        if (number instanceof Integer) {
            return ((Integer)number).doubleValue();
        }
        return JSRuntime.doubleValueVirtual(number);
    }

    public static double doubleValue(Number number, BranchProfile profile) {
        if (number instanceof Double) {
            return (Double)number;
        }
        if (number instanceof Integer) {
            return ((Integer)number).doubleValue();
        }
        profile.enter();
        return JSRuntime.doubleValueVirtual(number);
    }

    @CompilerDirectives.TruffleBoundary
    public static double doubleValueVirtual(Number number) {
        return number.doubleValue();
    }

    public static float floatValue(Number n) {
        if (n instanceof Double) {
            return ((Double)n).floatValue();
        }
        if (n instanceof Integer) {
            return ((Integer)n).floatValue();
        }
        return JSRuntime.floatValueVirtual(n);
    }

    @CompilerDirectives.TruffleBoundary
    public static float floatValueVirtual(Number n) {
        return n.floatValue();
    }

    public static long longValue(Number n) {
        if (n instanceof Integer) {
            return ((Integer)n).longValue();
        }
        if (n instanceof Double) {
            return ((Double)n).longValue();
        }
        if (n instanceof SafeInteger) {
            return ((SafeInteger)n).longValue();
        }
        return JSRuntime.longValueVirtual(n);
    }

    @CompilerDirectives.TruffleBoundary
    private static long longValueVirtual(Number n) {
        return n.longValue();
    }

    public static long toLong(Number value2) {
        return JSRuntime.longValue(value2);
    }

    @CompilerDirectives.TruffleBoundary
    public static String stringConcat(String first, String second) {
        StringBuilder stringBuilder = new StringBuilder(first.length() + second.length());
        stringBuilder.append(first).append(second);
        return stringBuilder.toString();
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject fromPropertyDescriptor(PropertyDescriptor desc, JSContext context) {
        if (desc == null) {
            return Undefined.instance;
        }
        JSObject obj = JSOrdinary.create(context, JSRealm.get(null));
        if (desc.hasValue()) {
            JSObject.set((JSDynamicObject)obj, JSAttributes.VALUE, desc.getValue());
        }
        if (desc.hasWritable()) {
            JSObject.set((JSDynamicObject)obj, JSAttributes.WRITABLE, (Object)desc.getWritable());
        }
        if (desc.hasGet()) {
            JSObject.set((JSDynamicObject)obj, JSAttributes.GET, desc.getGet());
        }
        if (desc.hasSet()) {
            JSObject.set((JSDynamicObject)obj, JSAttributes.SET, desc.getSet());
        }
        if (desc.hasEnumerable()) {
            JSObject.set((JSDynamicObject)obj, JSAttributes.ENUMERABLE, (Object)desc.getEnumerable());
        }
        if (desc.hasConfigurable()) {
            JSObject.set((JSDynamicObject)obj, JSAttributes.CONFIGURABLE, (Object)desc.getConfigurable());
        }
        return obj;
    }

    public static Object getArgOrUndefined(Object[] args, int i) {
        return args.length > i ? args[i] : Undefined.instance;
    }

    public static Object getArg(Object[] args, int i, Object defaultValue) {
        return args.length > i ? args[i] : defaultValue;
    }

    public static long getOffset(long start2, long length, ConditionProfile profile) {
        if (profile.profile(start2 < 0L)) {
            return Math.max(start2 + length, 0L);
        }
        return Math.min(start2, length);
    }

    public static int getOffset(int start2, int length, ConditionProfile profile) {
        if (profile.profile(start2 < 0)) {
            return Math.max(start2 + length, 0);
        }
        return Math.min(start2, length);
    }

    @CompilerDirectives.TruffleBoundary
    public static long parseSafeInteger(TruffleString s) {
        return JSRuntime.parseSafeInteger(s, 0, Strings.length(s), 10);
    }

    @CompilerDirectives.TruffleBoundary
    public static long parseSafeInteger(TruffleString s, int beginIndex, int endIndex, int radix) {
        return JSRuntime.parseLong(s, beginIndex, endIndex, radix, radix == 10, MAX_SAFE_INTEGER_LONG);
    }

    private static long parseLong(TruffleString s, int beginIndex, int endIndex, int radix, boolean parseSign, long limit) {
        assert (beginIndex >= 0 && beginIndex <= endIndex && endIndex <= Strings.length(s));
        assert (radix >= 2 && radix <= 36);
        assert (limit <= Long.MAX_VALUE / (long)radix - (long)radix);
        boolean negative = false;
        int i = beginIndex;
        if (i >= endIndex) {
            return Long.MIN_VALUE;
        }
        if (parseSign) {
            char firstChar = Strings.charAt(s, i);
            if (firstChar < '0') {
                if (firstChar == '-') {
                    negative = true;
                } else if (firstChar != '+') {
                    return Long.MIN_VALUE;
                }
                ++i;
            }
            if (i >= endIndex) {
                return Long.MIN_VALUE;
            }
        }
        long result = 0L;
        while (i < endIndex) {
            char c = Strings.charAt(s, i);
            int digit = JSRuntime.valueInRadix(c, radix);
            if (digit < 0) {
                return Long.MIN_VALUE;
            }
            result *= (long)radix;
            if ((result += (long)digit) > limit) {
                return Long.MIN_VALUE;
            }
            ++i;
        }
        assert (result >= 0L);
        if (negative && result == 0L) {
            return Long.MIN_VALUE;
        }
        return negative ? -result : result;
    }

    @CompilerDirectives.TruffleBoundary
    public static Number parseRawFitsLong(TruffleString string, int radix, int startPos, int endPos, boolean negate) {
        long signedValue;
        assert (startPos < endPos);
        long value2 = 0L;
        for (int pos = startPos; pos < endPos; ++pos) {
            char c = Strings.charAt(string, pos);
            int cval = JSRuntime.valueInRadix(c, radix);
            if (cval < 0) {
                if (pos != startPos) break;
                return Double.NaN;
            }
            value2 *= (long)radix;
            value2 += (long)cval;
        }
        if (value2 == 0L && negate && Strings.charAt(string, startPos) == '0') {
            return -0.0;
        }
        assert (value2 >= 0L);
        long l = signedValue = negate ? -value2 : value2;
        if (value2 <= Integer.MAX_VALUE) {
            return (int)signedValue;
        }
        return (double)signedValue;
    }

    @CompilerDirectives.TruffleBoundary
    public static double parseRawDontFitLong(TruffleString string, int radix, int startPos, int endPos, boolean negate) {
        assert (startPos < endPos);
        double value2 = 0.0;
        for (int pos = startPos; pos < endPos; ++pos) {
            char c = Strings.charAt(string, pos);
            int cval = JSRuntime.valueInRadix(c, radix);
            if (cval < 0) {
                if (pos != startPos) break;
                return Double.NaN;
            }
            value2 *= (double)radix;
            value2 += (double)cval;
        }
        assert (value2 >= 0.0);
        return negate ? -value2 : value2;
    }

    public static boolean createDataProperty(JSDynamicObject o, Object p, Object v) {
        assert (JSRuntime.isObject(o));
        assert (JSRuntime.isPropertyKey(p));
        return JSObject.defineOwnProperty(o, p, PropertyDescriptor.createDataDefault(v));
    }

    public static boolean createDataProperty(JSDynamicObject o, Object p, Object v, boolean doThrow) {
        assert (JSRuntime.isObject(o));
        assert (JSRuntime.isPropertyKey(p));
        boolean success = JSObject.defineOwnProperty(o, p, PropertyDescriptor.createDataDefault(v), doThrow);
        assert (!doThrow || success) : "should have thrown";
        return success;
    }

    public static boolean createDataPropertyOrThrow(JSDynamicObject o, Object p, Object v) {
        return JSRuntime.createDataProperty(o, p, v, true);
    }

    public static void createNonEnumerableDataPropertyOrThrow(JSDynamicObject o, Object p, Object v) {
        PropertyDescriptor newDesc = PropertyDescriptor.createData(v, JSAttributes.getDefaultNotEnumerable());
        JSRuntime.definePropertyOrThrow(o, p, newDesc);
    }

    public static void definePropertyOrThrow(JSDynamicObject o, Object key, PropertyDescriptor desc) {
        assert (JSRuntime.isObject(o));
        assert (JSRuntime.isPropertyKey(key));
        boolean success = JSObject.getJSClass(o).defineOwnProperty(o, key, desc, true);
        assert (success);
    }

    public static boolean isPrototypeOf(JSDynamicObject object, JSDynamicObject prototype) {
        JSDynamicObject prototypeChainObject = object;
        do {
            if ((prototypeChainObject = JSObject.getPrototype(prototypeChainObject)) != prototype) continue;
            return true;
        } while (prototypeChainObject != Null.instance);
        return false;
    }

    public static JSDynamicObject createArrayFromList(JSContext context, JSRealm realm, List<? extends Object> list) {
        return JSArray.createConstant(context, realm, Boundaries.listToArray(list));
    }

    public static boolean isCallable(Object value2) {
        if (JSFunction.isJSFunction(value2)) {
            return true;
        }
        if (JSProxy.isJSProxy(value2)) {
            return JSRuntime.isCallableProxy((JSDynamicObject)value2);
        }
        if (value2 instanceof TruffleObject) {
            return JSRuntime.isCallableForeign(value2);
        }
        return false;
    }

    public static boolean isCallableIsJSObject(JSDynamicObject value2) {
        assert (JSDynamicObject.isJSDynamicObject(value2));
        if (JSFunction.isJSFunction(value2)) {
            return true;
        }
        if (JSProxy.isJSProxy(value2)) {
            return JSRuntime.isCallableProxy(value2);
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isCallableForeign(Object value2) {
        if (JSRuntime.isForeignObject(value2)) {
            InteropLibrary interop = InteropLibrary.getUncached();
            return interop.isExecutable(value2) || interop.isInstantiable(value2);
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isCallableProxy(JSDynamicObject proxy) {
        assert (JSProxy.isJSProxy(proxy));
        Object target = JSProxy.getTarget(proxy);
        return JSRuntime.isCallable(target);
    }

    public static boolean isArray(Object obj) {
        if (JSArray.isJSArray(obj)) {
            return true;
        }
        if (JSProxy.isJSProxy(obj)) {
            return JSRuntime.isProxyAnArray((JSDynamicObject)obj);
        }
        if (JSRuntime.isForeignObject(obj)) {
            return InteropLibrary.getUncached().hasArrayElements(obj);
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isProxyAnArray(JSDynamicObject proxy) {
        assert (JSProxy.isJSProxy(proxy));
        if (JSProxy.isRevoked(proxy)) {
            throw Errors.createTypeErrorProxyRevoked();
        }
        return JSRuntime.isArrayProxyRecurse(proxy);
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean isArrayProxyRecurse(JSDynamicObject proxy) {
        return JSRuntime.isArray(JSProxy.getTarget(proxy));
    }

    @CompilerDirectives.TruffleBoundary
    public static Object toPropertyKey(Object arg) {
        if (Strings.isTString(arg)) {
            return arg;
        }
        if (arg instanceof Symbol) {
            return arg;
        }
        Object key = JSRuntime.toPrimitive(arg);
        if (key instanceof Symbol) {
            return key;
        }
        if (Strings.isTString(key)) {
            return key;
        }
        return JSRuntime.toString(key);
    }

    public static Object call(Object fnObj, Object holder, Object[] arguments) {
        if (JSFunction.isJSFunction(fnObj)) {
            return JSFunction.call((JSFunctionObject)fnObj, holder, arguments);
        }
        if (JSProxy.isJSProxy(fnObj)) {
            return JSProxy.call((JSDynamicObject)fnObj, holder, arguments);
        }
        if (JSRuntime.isForeignObject(fnObj)) {
            return JSInteropUtil.call(fnObj, arguments);
        }
        throw Errors.createTypeErrorNotAFunction(fnObj);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object call(Object fnObj, Object holder, Object[] arguments, Node encapsulatingNode) {
        EncapsulatingNodeReference encapsulating = null;
        Node prev = null;
        if (encapsulatingNode != null) {
            encapsulating = EncapsulatingNodeReference.getCurrent();
            prev = encapsulating.set(encapsulatingNode);
        }
        try {
            Object object = JSRuntime.call(fnObj, holder, arguments);
            return object;
        }
        finally {
            if (encapsulatingNode != null) {
                encapsulating.set(prev);
            }
        }
    }

    public static Object construct(Object fnObj, Object[] arguments) {
        if (JSFunction.isJSFunction(fnObj)) {
            return JSFunction.construct((JSFunctionObject)fnObj, arguments);
        }
        if (JSProxy.isJSProxy(fnObj)) {
            return JSProxy.construct((JSDynamicObject)fnObj, arguments);
        }
        if (JSRuntime.isForeignObject(fnObj)) {
            return JSInteropUtil.construct(fnObj, arguments);
        }
        throw Errors.createTypeErrorNotAFunction(fnObj);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object canonicalNumericIndexString(TruffleString s) {
        if (Strings.isEmpty(s) || !JSRuntime.isNumericIndexStart(Strings.charAt(s, 0))) {
            return Undefined.instance;
        }
        if (Strings.NEGATIVE_ZERO.equals(s)) {
            return -0.0;
        }
        Number n = JSRuntime.stringToNumber(s);
        if (!JSRuntime.numberToString(n).equals(s)) {
            return Undefined.instance;
        }
        return n;
    }

    private static boolean isNumericIndexStart(char c) {
        return JSRuntime.isAsciiDigit(c) || c == '-' || c == 'I' || c == 'N';
    }

    public static boolean isInteger(Object obj) {
        if (!JSRuntime.isNumber(obj)) {
            return false;
        }
        double d = JSRuntime.doubleValue((Number)obj);
        return d - JSRuntime.truncateDouble(d) == 0.0;
    }

    public static int comparePropertyKeys(Object key1, Object key2) {
        assert (JSRuntime.isPropertyKey(key1) && JSRuntime.isPropertyKey(key2));
        boolean isString1 = Strings.isTString(key1);
        boolean isString2 = Strings.isTString(key2);
        if (isString1 && isString2) {
            long index1 = JSRuntime.propertyNameToArrayIndex((TruffleString)key1, TruffleString.ReadCharUTF16Node.getUncached());
            long index2 = JSRuntime.propertyNameToArrayIndex((TruffleString)key2, TruffleString.ReadCharUTF16Node.getUncached());
            boolean isIndex1 = JSRuntime.isArrayIndex(index1);
            boolean isIndex2 = JSRuntime.isArrayIndex(index2);
            if (isIndex1 && isIndex2) {
                return Long.compare(index1, index2);
            }
            if (isIndex1) {
                return -1;
            }
            if (isIndex2) {
                return 1;
            }
            return 0;
        }
        if (isString1) {
            return -1;
        }
        if (isString2) {
            return 1;
        }
        return 0;
    }

    public static TruffleString getConstructorName(JSDynamicObject receiver) {
        Object constructor;
        JSDynamicObject prototype;
        Object toStringTag = JSRuntime.getDataProperty(receiver, Symbol.SYMBOL_TO_STRING_TAG);
        if (Strings.isTString(toStringTag)) {
            return (TruffleString)toStringTag;
        }
        if (!JSRuntime.isProxy(receiver) && (prototype = JSObject.getPrototype(receiver)) != Null.instance && JSFunction.isJSFunction(constructor = JSRuntime.getDataProperty(prototype, JSObject.CONSTRUCTOR))) {
            return JSFunction.getName((JSFunctionObject)constructor);
        }
        return JSObject.getClassName(receiver);
    }

    public static Object getDataProperty(JSDynamicObject thisObj, Object key) {
        assert (JSRuntime.isPropertyKey(key));
        JSDynamicObject current = thisObj;
        while (current != Null.instance && current != null && !JSRuntime.isProxy(current)) {
            PropertyDescriptor desc = JSObject.getOwnProperty(current, key);
            if (desc != null) {
                if (!desc.isDataDescriptor()) break;
                return desc.getValue();
            }
            current = JSObject.getPrototype(current);
        }
        return null;
    }

    private static boolean isProxy(JSDynamicObject receiver) {
        return JSProxy.isJSProxy(receiver) || JSAdapter.isJSAdapter(receiver);
    }

    public static boolean isJSRootNode(RootNode rootNode) {
        return rootNode instanceof JavaScriptRootNode;
    }

    public static boolean isJSFunctionRootNode(RootNode rootNode) {
        return rootNode instanceof JavaScriptRootNode && ((JavaScriptRootNode)rootNode).isFunction();
    }

    public static boolean isSafeInteger(double value2) {
        return value2 >= MIN_SAFE_INTEGER && value2 <= MAX_SAFE_INTEGER;
    }

    public static boolean isSafeInteger(long value2) {
        return value2 >= MIN_SAFE_INTEGER_LONG && value2 <= MAX_SAFE_INTEGER_LONG;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSRealm getFunctionRealm(Object obj, JSRealm currentRealm) {
        if (JSObject.isJSObject(obj)) {
            JSObject dynObj = (JSObject)obj;
            if (JSFunction.isJSFunction(dynObj)) {
                if (JSFunction.isBoundFunction(dynObj)) {
                    return JSRuntime.getFunctionRealm(JSFunction.getBoundTargetFunction(dynObj), currentRealm);
                }
                return JSFunction.getRealm(dynObj);
            }
            if (JSProxy.isJSProxy(dynObj)) {
                if (JSProxy.getHandler(dynObj) == Null.instance) {
                    throw Errors.createTypeErrorProxyRevoked();
                }
                return JSRuntime.getFunctionRealm(JSProxy.getTarget(dynObj), currentRealm);
            }
        }
        return currentRealm;
    }

    public static boolean isConstructor(Object constrObj) {
        if (JSFunction.isConstructor(constrObj)) {
            return true;
        }
        if (JSProxy.isJSProxy(constrObj)) {
            return JSRuntime.isConstructorProxy((JSDynamicObject)constrObj);
        }
        if (constrObj instanceof TruffleObject) {
            return JSRuntime.isConstructorForeign(constrObj);
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isConstructorForeign(Object value2) {
        if (JSRuntime.isForeignObject(value2)) {
            return InteropLibrary.getUncached().isInstantiable(value2);
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isConstructorProxy(JSDynamicObject constrObj) {
        assert (JSProxy.isJSProxy(constrObj));
        return JSRuntime.isConstructor(JSProxy.getTarget(constrObj));
    }

    public static boolean isGenerator(Object genObj) {
        if (JSFunction.isJSFunction(genObj) && JSFunction.isGenerator((JSFunctionObject)genObj)) {
            return true;
        }
        if (JSProxy.isJSProxy(genObj)) {
            return JSRuntime.isGeneratorProxy((JSDynamicObject)genObj);
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isGeneratorProxy(JSDynamicObject genObj) {
        assert (JSProxy.isJSProxy(genObj));
        return JSRuntime.isGenerator(JSProxy.getTarget(genObj));
    }

    @CompilerDirectives.TruffleBoundary
    public static List<Object> createListFromArrayLikeAllowSymbolString(Object obj) {
        if (!JSRuntime.isObject(obj)) {
            throw Errors.createTypeErrorNotAnObject(obj);
        }
        JSDynamicObject jsObj = (JSDynamicObject)obj;
        long len = JSRuntime.toLength(JSObject.get(jsObj, JSAbstractArray.LENGTH));
        if (len > Integer.MAX_VALUE) {
            throw Errors.createRangeError("range exceeded");
        }
        ArrayList<Object> list = new ArrayList<Object>();
        for (long index = 0L; index < len; ++index) {
            Object next = JSObject.get(jsObj, index);
            if (!Strings.isTString(next) && !(next instanceof Symbol)) {
                throw Errors.createTypeError("Symbol or String expected");
            }
            Boundaries.listAdd(list, next);
        }
        return list;
    }

    @CompilerDirectives.TruffleBoundary
    public static String quote(String value2) {
        char ch;
        int pos;
        for (pos = 0; pos < value2.length() && (ch = value2.charAt(pos)) >= ' ' && ch != '\\' && ch != '\"'; ++pos) {
        }
        StringBuilder builder = new StringBuilder(value2.length() + 2);
        builder.append('\"');
        builder.append(value2, 0, pos);
        for (int i = pos; i < value2.length(); ++i) {
            char ch2 = value2.charAt(i);
            if (ch2 < ' ') {
                if (ch2 == '\b') {
                    builder.append("\\b");
                    continue;
                }
                if (ch2 == '\f') {
                    builder.append("\\f");
                    continue;
                }
                if (ch2 == '\n') {
                    builder.append("\\n");
                    continue;
                }
                if (ch2 == '\r') {
                    builder.append("\\r");
                    continue;
                }
                if (ch2 == '\t') {
                    builder.append("\\t");
                    continue;
                }
                builder.append("\\u00");
                builder.append(Character.forDigit((ch2 & 0xF0) >> 4, 16));
                builder.append(Character.forDigit(ch2 & 0xF, 16));
                continue;
            }
            if (ch2 == '\\') {
                builder.append("\\\\");
                continue;
            }
            if (ch2 == '\"') {
                builder.append("\\\"");
                continue;
            }
            builder.append(ch2);
        }
        builder.append('\"');
        return builder.toString();
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString quote(TruffleString value2) {
        char ch;
        int pos;
        for (pos = 0; pos < Strings.length(value2) && (ch = Strings.charAt(value2, pos)) >= ' ' && ch != '\\' && ch != '\"'; ++pos) {
        }
        TruffleStringBuilder builder = Strings.builderCreate(Strings.length(value2) + 2);
        Strings.builderAppend(builder, '\"');
        Strings.builderAppend(builder, value2, 0, pos);
        for (int i = pos; i < Strings.length(value2); ++i) {
            char ch2 = Strings.charAt(value2, i);
            if (ch2 < ' ') {
                if (ch2 == '\b') {
                    Strings.builderAppend(builder, Strings.ESCAPE_B);
                    continue;
                }
                if (ch2 == '\f') {
                    Strings.builderAppend(builder, Strings.ESCAPE_F);
                    continue;
                }
                if (ch2 == '\n') {
                    Strings.builderAppend(builder, Strings.ESCAPE_N);
                    continue;
                }
                if (ch2 == '\r') {
                    Strings.builderAppend(builder, Strings.ESCAPE_R);
                    continue;
                }
                if (ch2 == '\t') {
                    Strings.builderAppend(builder, Strings.ESCAPE_T);
                    continue;
                }
                Strings.builderAppend(builder, Strings.ESCAPE_U_00);
                Strings.builderAppend(builder, Character.forDigit((ch2 & 0xF0) >> 4, 16));
                Strings.builderAppend(builder, Character.forDigit(ch2 & 0xF, 16));
                continue;
            }
            if (ch2 == '\\') {
                Strings.builderAppend(builder, Strings.ESCAPE_BACKSLASH);
                continue;
            }
            if (ch2 == '\"') {
                Strings.builderAppend(builder, Strings.ESCAPE_QUOTE);
                continue;
            }
            Strings.builderAppend(builder, ch2);
        }
        Strings.builderAppend(builder, '\"');
        return Strings.builderToString(builder);
    }

    public static JSDynamicObject expectJSObject(Object to, BranchProfile errorBranch) {
        if (!JSDynamicObject.isJSDynamicObject(to)) {
            errorBranch.enter();
            throw Errors.createTypeErrorJSObjectExpected();
        }
        return (JSDynamicObject)to;
    }

    @CompilerDirectives.TruffleBoundary
    public static Object exportValue(Object value2) {
        return ExportValueNode.getUncached().execute(value2);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object[] exportValueArray(Object[] arr) {
        Object[] newArr = new Object[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            newArr[i] = JSRuntime.exportValue(arr[i]);
        }
        return newArr;
    }

    @CompilerDirectives.TruffleBoundary
    public static Object importValue(Object value2) {
        assert (value2 != null);
        return ImportValueNode.getUncached().executeWithTarget(value2);
    }

    public static boolean intIsRepresentableAsFloat(int value2) {
        return -16777216 <= value2 && value2 <= 0x1000000;
    }

    public static boolean isJavaPrimitive(Object value2) {
        return value2 != null && value2 instanceof Boolean || value2 instanceof Byte || value2 instanceof Short || value2 instanceof Integer || value2 instanceof Long || value2 instanceof Float || value2 instanceof Double || value2 instanceof Character;
    }

    public static <E extends Throwable> RuntimeException rethrow(Throwable ex) throws E {
        throw ex;
    }

    public static boolean isTypedArrayBigIntFactory(TypedArrayFactory factory) {
        return factory == TypedArrayFactory.BigInt64Array || factory == TypedArrayFactory.BigUint64Array;
    }

    public static GraalJSException getException(Object errorObject) {
        if (JSError.isJSError(errorObject)) {
            return JSError.getException((JSDynamicObject)errorObject);
        }
        return UserScriptException.create(errorObject);
    }

    public static IteratorRecord getIterator(JSDynamicObject iteratedObject) {
        Object method = JSObject.get(iteratedObject, Symbol.SYMBOL_ITERATOR);
        if (!JSRuntime.isCallable(method)) {
            throw Errors.createTypeErrorNotIterable(iteratedObject, null);
        }
        Object iterator = JSRuntime.call(method, iteratedObject, new Object[0]);
        if (JSRuntime.isObject(iterator)) {
            return IteratorRecord.create((JSDynamicObject)iterator, JSObject.get((JSDynamicObject)iterator, NEXT), false);
        }
        throw Errors.createTypeErrorNotAnObject(iterator);
    }

    public static Object iteratorStep(IteratorRecord iteratorRecord) {
        JSDynamicObject iterator;
        Object nextMethod = iteratorRecord.getNextMethod();
        Object result = JSRuntime.call(nextMethod, iterator = iteratorRecord.getIterator(), new Object[0]);
        if (!JSRuntime.isObject(result)) {
            throw Errors.createTypeErrorIteratorResultNotObject(result, null);
        }
        boolean done = JSRuntime.toBoolean(JSObject.get((JSDynamicObject)result, DONE));
        if (done) {
            return false;
        }
        return result;
    }

    public static Object iteratorValue(JSDynamicObject iterator) {
        return JSObject.get(iterator, VALUE);
    }

    public static void iteratorClose(JSDynamicObject iterator) {
        Object innerResult;
        Object returnMethod = JSObject.get(iterator, Strings.RETURN);
        if (returnMethod != Undefined.instance && !JSRuntime.isObject(innerResult = JSRuntime.call(returnMethod, iterator, new Object[0]))) {
            throw Errors.createTypeErrorIterResultNotAnObject(innerResult, null);
        }
    }

    public static boolean isIntegralNumber(double arg) {
        return arg - JSRuntime.truncateDouble(arg) == 0.0;
    }
}

