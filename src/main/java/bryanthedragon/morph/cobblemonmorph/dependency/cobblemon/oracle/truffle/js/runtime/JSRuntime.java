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
   public static final double TWO32 = 4.2949673E9F;
   public static final long INVALID_ARRAY_INDEX = -1L;
   public static final long MAX_ARRAY_LENGTH = 4294967295L;
   public static final int MAX_UINT32_DIGITS = 10;
   public static final double MAX_SAFE_INTEGER = Math.pow(2.0, 53.0) - 1.0;
   public static final double MIN_SAFE_INTEGER = -MAX_SAFE_INTEGER;
   public static final long MAX_SAFE_INTEGER_LONG = (long)MAX_SAFE_INTEGER;
   public static final long MIN_SAFE_INTEGER_LONG = (long)MIN_SAFE_INTEGER;
   public static final long INVALID_INTEGER_INDEX = -1L;
   public static final int MAX_INTEGER_INDEX_DIGITS = 16;
   public static final int MAX_SAFE_INTEGER_DIGITS = 16;
   public static final int MAX_SAFE_INTEGER_IN_FLOAT = 16777216;
   public static final int MIN_SAFE_INTEGER_IN_FLOAT = -16777216;
   public static final long MAX_BIG_INT_EXPONENT = 2147483647L;
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
      return doubleIsRepresentableAsInt(d, false);
   }

   public static boolean doubleIsRepresentableAsInt(double d, boolean ignoreNegativeZero) {
      long longValue = (long)d;
      return doubleIsRepresentableAsLong(d) && longIsRepresentableAsInt(longValue) && (ignoreNegativeZero || !isNegativeZero(d));
   }

   public static boolean doubleIsRepresentableAsUnsignedInt(double d, boolean ignoreNegativeZero) {
      long longValue = (long)d;
      return doubleIsRepresentableAsLong(d) && longIsRepresentableAsInt(longValue) && (ignoreNegativeZero || !isNegativeZero(d));
   }

   public static boolean isNegativeZero(double d) {
      return Double.doubleToRawLongBits(d) == NEGATIVE_ZERO_DOUBLE_BITS;
   }

   public static boolean isPositiveInfinity(double d) {
      return Double.doubleToRawLongBits(d) == POSITIVE_INFINITY_DOUBLE_BITS;
   }

   public static Number doubleToNarrowestNumber(double d) {
      return (Number)(doubleIsRepresentableAsInt(d) ? (int)d : d);
   }

   public static boolean longIsRepresentableAsInt(long value) {
      return value == (int)value;
   }

   public static boolean isRepresentableAsUnsignedInt(long value) {
      return (value & 4294967295L) == value;
   }

   public static boolean doubleIsRepresentableAsLong(double d) {
      return d == (long)d;
   }

   public static Object positiveLongToIntOrDouble(long value) {
      return value <= 2147483647L ? (int)value : (double)value;
   }

   public static Number longToIntOrDouble(long value) {
      return (Number)(-2147483648L <= value && value <= 2147483647L ? (int)value : (double)value);
   }

   public static boolean isNaN(Object value) {
      if (!(value instanceof Double)) {
         return false;
      } else {
         double d = (Double)value;
         return Double.isNaN(d);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString typeof(Object value) {
      if (value == Null.instance) {
         return Null.TYPE_NAME;
      } else if (value == Undefined.instance) {
         return Undefined.TYPE_NAME;
      } else if (Strings.isTString(value)) {
         return JSString.TYPE_NAME;
      } else if (isNumber(value)) {
         return JSNumber.TYPE_NAME;
      } else if (isBigInt(value)) {
         return JSBigInt.TYPE_NAME;
      } else if (value instanceof Boolean) {
         return JSBoolean.TYPE_NAME;
      } else if (value instanceof Symbol) {
         return JSSymbol.TYPE_NAME;
      } else if (JSObject.isJSObject(value)) {
         JSObject object = (JSObject)value;
         if (JSProxy.isJSProxy(object)) {
            Object target = JSProxy.getTargetNonProxy(object);
            return typeof(target);
         } else {
            return JSFunction.isJSFunction(object) ? JSFunction.TYPE_NAME : JSOrdinary.TYPE_NAME;
         }
      } else if (value instanceof TruffleObject) {
         assert !(value instanceof Symbol);

         JSRealm realm = JSRealm.get(null);
         if (realm.getContext().isOptionNashornCompatibilityMode()) {
            TruffleLanguage.Env env = realm.getEnv();
            if (env.isHostSymbol(value)) {
               return JSFunction.TYPE_NAME;
            }
         }

         TruffleObject object = (TruffleObject)value;
         InteropLibrary interop = InteropLibrary.getUncached();
         if (interop.isBoolean(object)) {
            return JSBoolean.TYPE_NAME;
         } else if (interop.isString(object)) {
            return JSString.TYPE_NAME;
         } else if (interop.isNumber(object)) {
            return JSNumber.TYPE_NAME;
         } else {
            return !interop.isExecutable(object) && !interop.isInstantiable(object) ? JSOrdinary.TYPE_NAME : JSFunction.TYPE_NAME;
         }
      } else {
         throw new UnsupportedOperationException("typeof: don't know " + value.getClass().getSimpleName());
      }
   }

   public static boolean isObject(Object value) {
      return value instanceof JSObject;
   }

   public static boolean isNullOrUndefined(Object value) {
      return value instanceof Nullish;
   }

   public static boolean isNullish(Object value) {
      return value == Null.instance || value == Undefined.instance || InteropLibrary.getUncached(value).isNull(value);
   }

   @CompilerDirectives.TruffleBoundary
   public static Object toPrimitive(Object value) {
      return toPrimitive(value, JSToPrimitiveNode.Hint.Default);
   }

   @CompilerDirectives.TruffleBoundary
   public static Object toPrimitive(Object value, JSToPrimitiveNode.Hint hint) {
      if (value == Null.instance || value == Undefined.instance) {
         return value;
      } else if (JSObject.isJSObject(value)) {
         return JSObject.toPrimitive((JSObject)value, hint);
      } else {
         return isForeignObject(value) ? toPrimitiveFromForeign(value, hint) : value;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Object toPrimitiveFromForeign(Object tObj, JSToPrimitiveNode.Hint hint) {
      assert isForeignObject(tObj);

      InteropLibrary interop = InteropLibrary.getFactory().getUncached(tObj);
      if (interop.isNull(tObj)) {
         return Null.instance;
      } else if (JSInteropUtil.isBoxedPrimitive(tObj, interop)) {
         return JSInteropUtil.toPrimitiveOrDefault(tObj, Null.instance, interop, null);
      } else {
         if (JavaScriptLanguage.getCurrentEnv().isHostObject(tObj)) {
            Object maybeResult = JSToPrimitiveNode.tryHostObjectToPrimitive(tObj, hint, interop);
            if (maybeResult != null) {
               return maybeResult;
            }
         }

         return foreignOrdinaryToPrimitive(tObj, hint == JSToPrimitiveNode.Hint.Default ? JSToPrimitiveNode.Hint.Number : hint);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static Object foreignOrdinaryToPrimitive(Object obj, JSToPrimitiveNode.Hint hint) {
      InteropLibrary interop = InteropLibrary.getFactory().getUncached(obj);
      TruffleString[] methodNames;
      if (hint == JSToPrimitiveNode.Hint.String) {
         methodNames = new TruffleString[]{Strings.TO_STRING, Strings.VALUE_OF};
      } else {
         assert hint == JSToPrimitiveNode.Hint.Number;

         methodNames = new TruffleString[]{Strings.VALUE_OF, Strings.TO_STRING};
      }

      JSDynamicObject proto = ForeignObjectPrototypeNode.getUncached().execute(obj);

      for (TruffleString name : methodNames) {
         if (interop.hasMembers(obj) && interop.isMemberInvocable(obj, Strings.toJavaString(name)) && !OrdinaryToPrimitiveNode.isJavaArray(obj, interop)) {
            try {
               Object result = importValue(interop.invokeMember(obj, Strings.toJavaString(name)));
               if (IsPrimitiveNode.getUncached().executeBoolean(result)) {
                  return result;
               }
            } catch (InteropException var11) {
            }
         }

         Object method = JSObject.getMethod(proto, name);
         if (isCallable(method)) {
            Object result = call(method, obj, new Object[0]);
            if (IsPrimitiveNode.getUncached().executeBoolean(result)) {
               return result;
            }
         }
      }

      throw Errors.createTypeErrorCannotConvertToPrimitiveValue();
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean toBoolean(Object value) {
      if (value == Boolean.TRUE) {
         return true;
      } else if (value == Boolean.FALSE || value == Undefined.instance || value == Null.instance) {
         return false;
      } else if (isNumber(value)) {
         return toBoolean((Number)value);
      } else if (Strings.isTString(value)) {
         return Strings.length((TruffleString)value) != 0;
      } else if (value instanceof BigInt) {
         return ((BigInt)value).compareTo(BigInt.ZERO) != 0;
      } else if (isForeignObject(value)) {
         InteropLibrary interop = InteropLibrary.getFactory().getUncached(value);
         if (interop.isNull(value)) {
            return false;
         } else {
            return JSInteropUtil.isBoxedPrimitive(value, interop) ? toBoolean(JSInteropUtil.toPrimitiveOrDefault(value, Null.instance, interop, null)) : true;
         }
      } else {
         return true;
      }
   }

   public static boolean toBoolean(Number number) {
      double val = doubleValue(number);
      return val != 0.0 && !Double.isNaN(val) ? Boolean.TRUE : false;
   }

   @CompilerDirectives.TruffleBoundary
   public static Number toNumber(Object value) {
      Object primitive;
      if (isObject(value)) {
         primitive = JSObject.toPrimitive((JSDynamicObject)value, JSToPrimitiveNode.Hint.Number);
      } else if (isForeignObject(value)) {
         primitive = toPrimitiveFromForeign(value, JSToPrimitiveNode.Hint.Number);
      } else {
         primitive = value;
      }

      return toNumberFromPrimitive(primitive);
   }

   @CompilerDirectives.TruffleBoundary
   public static Object toNumeric(Object value) {
      Object primitive = isObject(value) ? JSObject.toPrimitive((JSDynamicObject)value, JSToPrimitiveNode.Hint.Number) : value;
      return primitive instanceof BigInt ? primitive : toNumberFromPrimitive(primitive);
   }

   @CompilerDirectives.TruffleBoundary
   public static Number toNumberFromPrimitive(Object value) {
      if (CompilerDirectives.injectBranchProbability(0.75, isNumber(value))) {
         return (Number)value;
      } else if (value == Undefined.instance) {
         return Double.NaN;
      } else if (value == Null.instance) {
         return 0;
      } else if (value instanceof Boolean) {
         return booleanToNumber((Boolean)value);
      } else if (Strings.isTString(value)) {
         return stringToNumber((TruffleString)value);
      } else if (value instanceof Symbol) {
         throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value");
      } else if (value instanceof BigInt) {
         throw Errors.createTypeErrorCannotConvertToNumber("a BigInt value");
      } else if (value instanceof Number) {
         assert isJavaPrimitive(value) : value.getClass().getName();

         return (Number)value;
      } else {
         assert false : "should never reach here, type " + value.getClass().getName() + " not handled.";

         throw Errors.createTypeErrorCannotConvertToNumber(Strings.toJavaString(safeToString(value)));
      }
   }

   public static int booleanToNumber(boolean value) {
      return value ? 1 : 0;
   }

   public static boolean isNumber(Object value) {
      return value instanceof Integer || value instanceof Double || value instanceof Long || value instanceof SafeInteger;
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInt toBigInt(Object value) {
      Object primitive = toPrimitive(value, JSToPrimitiveNode.Hint.Number);
      if (Strings.isTString(primitive)) {
         try {
            return Strings.parseBigInt((TruffleString)primitive);
         } catch (NumberFormatException var3) {
            throw Errors.createErrorCanNotConvertToBigInt(JSErrorType.SyntaxError, primitive);
         }
      } else if (primitive instanceof BigInt) {
         return (BigInt)primitive;
      } else if (primitive instanceof Boolean) {
         return (Boolean)primitive ? BigInt.ONE : BigInt.ZERO;
      } else {
         throw Errors.createErrorCanNotConvertToBigInt(JSErrorType.TypeError, primitive);
      }
   }

   public static boolean isBigInt(Object value) {
      return value instanceof BigInt;
   }

   public static boolean isJavaNumber(Object value) {
      return value instanceof Number;
   }

   @CompilerDirectives.TruffleBoundary
   public static Number stringToNumber(TruffleString string) {
      TruffleString strCamel = trimJSWhiteSpace(string);
      int camelLength = Strings.length(strCamel);
      if (camelLength == 0) {
         return 0;
      } else {
         char firstChar = Strings.charAt(strCamel, 0);
         if (camelLength >= Strings.length(Strings.INFINITY)
            && camelLength <= Strings.length(Strings.INFINITY) + 1
            && Strings.endsWith(strCamel, Strings.INFINITY)) {
            return identifyInfinity(firstChar, camelLength);
         } else {
            return (Number)(!isAsciiDigit(firstChar) && firstChar != '-' && firstChar != '.' && firstChar != '+' ? Double.NaN : stringToNumberParse(strCamel));
         }
      }
   }

   private static Number stringToNumberParse(TruffleString str) {
      assert Strings.length(str) > 0;

      boolean hex = Strings.startsWith(str, Strings.LC_0X) || Strings.startsWith(str, Strings.UC_0X);
      int eIndex = firstExpIndexInString(str);
      boolean sci = !hex && 0 <= eIndex && eIndex < Strings.length(str) - 1;

      try {
         if (sci || Strings.length(str) > 18 || Strings.indexOf(str, '.') != -1) {
            return parseDoubleOrNaN(str);
         } else {
            return (Number)(hex ? Strings.parseLong(Strings.lazySubstring(str, 2), 16) : stringToNumberLong(str));
         }
      } catch (TruffleString.NumberFormatException var5) {
         return Double.NaN;
      }
   }

   private static Number stringToNumberLong(TruffleString strLower) throws TruffleString.NumberFormatException {
      assert Strings.length(strLower) > 0;

      long num = Strings.parseLong(strLower);
      if (longIsRepresentableAsInt(num)) {
         return (Number)(num == 0L && Strings.charAt(strLower, 0) == '-' ? -0.0 : (int)num);
      } else {
         return (double)num;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static double parseDoubleOrNaN(TruffleString input) {
      if (!Strings.isEmpty(input) && Strings.charAt(input, Strings.length(input) - 1) <= '9') {
         try {
            return Strings.parseDouble(input);
         } catch (TruffleString.NumberFormatException var2) {
            return Double.NaN;
         }
      } else {
         return Double.NaN;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static int firstExpIndexInString(TruffleString str) {
      int firstIdx = Strings.indexOf(str, 101, 0);
      return firstIdx >= 0 ? firstIdx : Strings.indexOf(str, 69, 0);
   }

   public static double identifyInfinity(char firstChar, int len) {
      int infinityLength = Strings.length(Strings.INFINITY);
      if (len == infinityLength) {
         return Double.POSITIVE_INFINITY;
      } else {
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
   }

   public static long toInteger(Object value) {
      Number number = toNumber(value);
      return toInteger(number);
   }

   public static long toInteger(Number number) {
      return longValue(number);
   }

   public static long toLength(Object value) {
      long l = toInteger(value);
      return toLength(l);
   }

   public static double toLength(double d) {
      if (d <= 0.0) {
         return 0.0;
      } else {
         return d > MAX_SAFE_INTEGER ? MAX_SAFE_INTEGER : d;
      }
   }

   public static long toLength(long l) {
      if (l <= 0L) {
         return 0L;
      } else {
         return l > MAX_SAFE_INTEGER_LONG ? MAX_SAFE_INTEGER_LONG : l;
      }
   }

   public static int toLength(int value) {
      return value <= 0 ? 0 : value;
   }

   public static int toUInt8(Object value) {
      Number number = toNumber(value);
      return toUInt8(number);
   }

   @CompilerDirectives.TruffleBoundary
   public static int toUInt8(Number number) {
      if (number instanceof Double) {
         Double d = (Double)number;
         if (isPositiveInfinity(d)) {
            return 0;
         }
      }

      return toUInt8(number.longValue());
   }

   public static int toUInt8(long number) {
      return (int)(number & 255L);
   }

   public static int toInt8(Object value) {
      Number number = toNumber(value);
      return toInt8(number);
   }

   @CompilerDirectives.TruffleBoundary
   public static int toInt8(Number number) {
      if (number instanceof Double) {
         Double d = (Double)number;
         if (isPositiveInfinity(d)) {
            return 0;
         }
      }

      return toInt8(number.longValue());
   }

   @CompilerDirectives.TruffleBoundary
   public static int toInt8(long number) {
      int res = floorMod(number, 256);
      if (res >= 128) {
         res -= 256;
      }

      return res;
   }

   public static int toUInt16(Object value) {
      Number number = toNumber(value);
      return toUInt16(number);
   }

   public static int toUInt16(Number number) {
      if (number instanceof Double) {
         Double d = (Double)number;
         if (isPositiveInfinity(d)) {
            return 0;
         }
      }

      return toUInt16(longValue(number));
   }

   public static int toUInt16(long number) {
      return (int)(number & 65535L);
   }

   public static int toInt16(Object value) {
      Number number = toNumber(value);
      return toInt16(number);
   }

   @CompilerDirectives.TruffleBoundary
   public static int toInt16(Number number) {
      if (number instanceof Double) {
         Double d = (Double)number;
         if (isPositiveInfinity(d)) {
            return 0;
         }
      }

      return toInt16(number.longValue());
   }

   @CompilerDirectives.TruffleBoundary
   public static int toInt16(long number) {
      int res = floorMod(number, 65536);
      if (res >= 32768) {
         res -= 65536;
      }

      return res;
   }

   public static int floorMod(long x, int y) {
      long divisor = y;
      return (int)Math.floorMod(x, divisor);
   }

   public static long toUInt32(Object value) {
      return toUInt32(toNumber(value));
   }

   public static long toUInt32(Number number) {
      return number instanceof Double ? toUInt32(((Double)number).doubleValue()) : toUInt32(longValue(number));
   }

   public static long toUInt32(long value) {
      return value & 4294967295L;
   }

   public static long toUInt32(double value) {
      return toUInt32NoTruncate(truncateDouble(value));
   }

   public static long toUInt32NoTruncate(double value) {
      assert !Double.isFinite(value) || value % 1.0 == 0.0;

      double d = doubleModuloTwo32(value);
      return toUInt32((long)d);
   }

   public static double truncateDouble(double value) {
      return ExactMath.truncate(value);
   }

   public static int toInt32(Object value) {
      Number number = toNumber(value);
      return toInt32(number);
   }

   public static int toInt32(Number number) {
      if (number instanceof Double) {
         return toInt32(((Double)number).doubleValue());
      } else if (number instanceof Integer) {
         return (Integer)number;
      } else {
         return number instanceof Long ? (int)((Long)number).longValue() : toInt32Intl(number);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static int toInt32Intl(Number number) {
      return toInt32(number.doubleValue());
   }

   public static int toInt32(double value) {
      return toInt32NoTruncate(truncateDouble(value));
   }

   public static int toInt32NoTruncate(double value) {
      assert !Double.isFinite(value) || value % 1.0 == 0.0;

      return (int)((long)doubleModuloTwo32(value));
   }

   private static double doubleModuloTwo32(double value) {
      return value - Math.floor(value / 4.2949673E9F) * 4.2949673E9F;
   }

   public static double toDouble(Object value) {
      return doubleValue(toNumber(value));
   }

   public static double toDouble(Number value) {
      return doubleValue(value);
   }

   public static String toJavaString(Object value) {
      return Strings.toJavaString(toString(value));
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString toString(Object value) {
      if (CompilerDirectives.injectBranchProbability(0.75, Strings.isTString(value))) {
         return (TruffleString)value;
      } else if (value == Undefined.instance) {
         return Undefined.NAME;
      } else if (value == Null.instance) {
         return Null.NAME;
      } else if (value instanceof Boolean) {
         return booleanToString((Boolean)value);
      } else if (isNumber(value)) {
         return numberToString((Number)value);
      } else if (value instanceof Symbol) {
         throw Errors.createTypeErrorCannotConvertToString("a Symbol value");
      } else if (value instanceof BigInt) {
         return Strings.fromBigInt((BigInt)value);
      } else if (JSObject.isJSObject(value)) {
         return toString(JSObject.toPrimitive((JSObject)value, JSToPrimitiveNode.Hint.String));
      } else if (value instanceof TruffleObject) {
         assert !isJSNative(value);

         return toString(toPrimitiveFromForeign(value, JSToPrimitiveNode.Hint.String));
      } else {
         throw toStringTypeError(value);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString safeToString(Object value) {
      return toDisplayString(value, false, ToDisplayStringFormat.getDefaultFormat());
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString toDisplayString(Object value, boolean allowSideEffects) {
      return toDisplayString(value, allowSideEffects, ToDisplayStringFormat.getDefaultFormat());
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString toDisplayString(Object value, boolean allowSideEffects, ToDisplayStringFormat format) {
      return toDisplayStringImpl(value, allowSideEffects, format, 0, null);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString toDisplayStringInner(Object value, boolean allowSideEffects, ToDisplayStringFormat format, int currentDepth, Object parent) {
      return toDisplayStringImpl(value, allowSideEffects, format.withQuoteString(true), currentDepth + 1, parent);
   }

   public static TruffleString toDisplayStringImpl(Object value, boolean allowSideEffects, ToDisplayStringFormat format, int depth, Object parent) {
      CompilerAsserts.neverPartOfCompilation();
      if (value == parent) {
         return Strings.PARENS_THIS;
      } else if (value == Undefined.instance) {
         return Undefined.NAME;
      } else if (value == Null.instance) {
         return Null.NAME;
      } else if (value instanceof Boolean) {
         return booleanToString((Boolean)value);
      } else if (value instanceof TruffleString) {
         return format.quoteString() ? quote((TruffleString)value) : (TruffleString)value;
      } else if (value instanceof String) {
         return format.quoteString() ? quote(Strings.fromJavaString((String)value)) : Strings.fromJavaString((String)value);
      } else if (JSObject.isJSObject(value)) {
         return ((JSObject)value).toDisplayStringImpl(allowSideEffects, format, depth);
      } else if (value instanceof Symbol) {
         return ((Symbol)value).toTString();
      } else if (value instanceof BigInt) {
         return Strings.concat(((BigInt)value).toTString(), Strings.N);
      } else if (isNumber(value)) {
         Number number = (Number)value;
         return isNegativeZero(number.doubleValue()) ? Strings.NEGATIVE_ZERO : numberToString(number);
      } else if (value instanceof InteropFunction) {
         return toDisplayStringImpl(((InteropFunction)value).getFunction(), allowSideEffects, format, depth, parent);
      } else if (value instanceof TruffleObject) {
         assert !isJSNative(value) : value;

         return foreignToString(value, allowSideEffects, format, depth);
      } else {
         return Strings.fromObject(value);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString objectToDisplayString(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth, TruffleString name) {
      return objectToDisplayString(obj, allowSideEffects, format, depth, name, null, null);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString objectToDisplayString(
      JSDynamicObject obj,
      boolean allowSideEffects,
      ToDisplayStringFormat format,
      int depth,
      TruffleString name,
      TruffleString[] internalKeys,
      Object[] internalValues
   ) {
      assert !JSFunction.isJSFunction(obj) && !JSProxy.isJSProxy(obj);

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
            boolean topLevel = depth == 0;
            if (depth >= format.getMaxDepth() || !topLevel && length > format.getMaxElements()) {
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

      char chr1 = (char)(isArrayLike ? 91 : 123);
      Strings.builderAppend(sb, chr1);
      int propertyCount = 0;

      for (Object key : JSObject.ownPropertyKeys(obj)) {
         if (!allowSideEffects && JSError.STACK_NAME.equals(key)) {
            Property prop = obj.getShape().getProperty(JSError.STACK_NAME);
            if (prop != null && JSProperty.isProxy(prop)) {
               continue;
            }
         }

         PropertyDescriptor desc = JSObject.getOwnProperty(obj, key);
         if ((!isArrayLike && !isStringObj || !key.equals(Strings.LENGTH)) && (!isStringObj || !isArrayIndex(key) || parseArrayIndexIsIndexRaw(key) >= length)) {
            if (propertyCount > 0) {
               Strings.builderAppend(sb, v8CompatMode ? "," : ", ");
               if (propertyCount >= format.getMaxElements()) {
                  Strings.builderAppend(sb, Strings.DOT_DOT_DOT);
                  break;
               }
            }

            if (isArray) {
               if (isArrayIndex(key)) {
                  long index = parseArrayIndexIsIndexRaw(key);
                  if (index < length && fillEmptyArrayElements(sb, index, prevArrayIndex, false)) {
                     Strings.builderAppend(sb, ", ");
                     if (++propertyCount >= format.getMaxElements()) {
                        Strings.builderAppend(sb, "...");
                        break;
                     }
                  }

                  prevArrayIndex = index;
               } else {
                  if (fillEmptyArrayElements(sb, length, prevArrayIndex, false)) {
                     Strings.builderAppend(sb, Strings.COMMA_SPC);
                     if (++propertyCount >= format.getMaxElements()) {
                        Strings.builderAppend(sb, "...");
                        break;
                     }
                  }

                  prevArrayIndex = Math.max(prevArrayIndex, length);
               }
            }

            if (!isArrayLike || !isArrayIndex(key)) {
               Strings.builderAppend(sb, Strings.fromObject(key));
               Strings.builderAppend(sb, ": ");
            }

            TruffleString valueStr = null;
            if (desc.isDataDescriptor()) {
               Object value = desc.getValue();
               valueStr = toDisplayStringInner(value, allowSideEffects, format, depth, obj);
            } else if (desc.isAccessorDescriptor()) {
               valueStr = Strings.ACCESSOR;
            } else {
               valueStr = Strings.EMPTY;
            }

            Strings.builderAppend(sb, valueStr);
            propertyCount++;
         }
      }

      if (isArray && propertyCount < format.getMaxElements() && fillEmptyArrayElements(sb, length, prevArrayIndex, propertyCount > 0)) {
         propertyCount++;
      }

      if (internalKeys != null) {
         assert internalValues != null && internalKeys.length == internalValues.length;

         for (int i = 0; i < internalKeys.length; i++) {
            if (propertyCount > 0) {
               Strings.builderAppend(sb, Strings.COMMA_SPC);
            }

            Strings.builderAppend(sb, Strings.BRACKET_OPEN_2);
            Strings.builderAppend(sb, internalKeys[i]);
            Strings.builderAppend(sb, Strings.BRACKET_CLOSE_2_COLON);
            Strings.builderAppend(sb, toDisplayStringInner(internalValues[i], allowSideEffects, format, depth, obj));
            propertyCount++;
         }
      }

      char chr = (char)(isArrayLike ? 93 : 125);
      Strings.builderAppend(sb, chr);
      return Strings.builderToString(sb);
   }

   private static TruffleString foreignToString(Object value, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      CompilerAsserts.neverPartOfCompilation();

      try {
         InteropLibrary interop = InteropLibrary.getUncached(value);
         if (interop.isNull(value)) {
            return Strings.NULL;
         } else if (interop.hasArrayElements(value)) {
            return foreignArrayToString(value, allowSideEffects, format, depth);
         } else if (interop.isString(value)) {
            return format.quoteString() ? Strings.fromJavaString(quote(interop.asString(value))) : interop.asTruffleString(value);
         } else if (interop.isBoolean(value)) {
            return booleanToString(interop.asBoolean(value));
         } else if (interop.isNumber(value)) {
            Object unboxed = Strings.UC_NUMBER;
            if (interop.fitsInInt(value)) {
               unboxed = interop.asInt(value);
            } else if (interop.fitsInLong(value)) {
               unboxed = interop.asLong(value);
            } else if (interop.fitsInDouble(value)) {
               unboxed = interop.asDouble(value);
            }

            return toDisplayString(unboxed, allowSideEffects, format);
         } else if (JavaScriptLanguage.getCurrentEnv().isHostObject(value)) {
            return hostObjectToString(value, interop);
         } else if (interop.isMetaObject(value)) {
            return InteropLibrary.getUncached().asTruffleString(interop.getMetaQualifiedName(value));
         } else {
            return interop.hasMembers(value) && !interop.isExecutable(value) && !interop.isInstantiable(value)
               ? foreignObjectToString(value, allowSideEffects, format, depth)
               : InteropLibrary.getUncached().asTruffleString(interop.toDisplayString(value, allowSideEffects));
         }
      } catch (InteropException var6) {
         return Strings.UC_OBJECT;
      }
   }

   private static TruffleString hostObjectToString(Object value, InteropLibrary interop) throws UnsupportedMessageException {
      if (interop.isMetaObject(value)) {
         return Strings.concatAll(
            Strings.JAVA_CLASS_BRACKET, InteropLibrary.getUncached().asTruffleString(interop.getMetaQualifiedName(value)), Strings.BRACKET_CLOSE
         );
      } else {
         Object metaObject = interop.getMetaObject(value);
         return Strings.concatAll(
            Strings.JAVA_OBJECT_BRACKET,
            InteropLibrary.getUncached().asTruffleString(InteropLibrary.getUncached().getMetaQualifiedName(metaObject)),
            Strings.BRACKET_CLOSE
         );
      }
   }

   private static TruffleString foreignArrayToString(Object truffleObject, boolean allowSideEffects, ToDisplayStringFormat format, int depth) throws InteropException {
      CompilerAsserts.neverPartOfCompilation();
      InteropLibrary interop = InteropLibrary.getFactory().getUncached(truffleObject);

      assert interop.hasArrayElements(truffleObject);

      long size = interop.getArraySize(truffleObject);
      if (size == 0L) {
         return Strings.EMPTY_ARRAY;
      } else if (depth >= format.getMaxDepth()) {
         return Strings.concatAll(Strings.ARRAY_PAREN_OPEN, Strings.fromLong(size), Strings.PAREN_CLOSE);
      } else {
         boolean topLevel = depth == 0;
         TruffleStringBuilder sb = Strings.builderCreate();
         if (topLevel && size >= 2L && format.includeArrayLength()) {
            Strings.builderAppend(sb, Strings.PAREN_OPEN);
            Strings.builderAppend(sb, size);
            Strings.builderAppend(sb, Strings.PAREN_CLOSE);
         }

         Strings.builderAppend(sb, '[');

         for (long i = 0L; i < size; i++) {
            if (i > 0L) {
               Strings.builderAppend(sb, Strings.COMMA_SPC);
               if (i >= format.getMaxElements()) {
                  Strings.builderAppend(sb, Strings.DOT_DOT_DOT);
                  break;
               }
            }

            Object value = interop.readArrayElement(truffleObject, i);
            Strings.builderAppend(sb, toDisplayStringInner(value, allowSideEffects, format, depth, truffleObject));
         }

         Strings.builderAppend(sb, ']');
         return Strings.builderToString(sb);
      }
   }

   private static TruffleString foreignObjectToString(Object truffleObject, boolean allowSideEffects, ToDisplayStringFormat format, int depth) throws InteropException {
      CompilerAsserts.neverPartOfCompilation();
      InteropLibrary objInterop = InteropLibrary.getFactory().getUncached(truffleObject);

      assert objInterop.hasMembers(truffleObject);

      if (allowSideEffects && objInterop.isMemberInvocable(truffleObject, Strings.TO_STRING_JLS)) {
         return toString(objInterop.invokeMember(truffleObject, Strings.TO_STRING_JLS));
      } else {
         Object keys = objInterop.getMembers(truffleObject);
         InteropLibrary keysInterop = InteropLibrary.getFactory().getUncached(keys);
         long keyCount = keysInterop.getArraySize(keys);
         if (keyCount == 0L) {
            return Strings.EMPTY_OBJECT;
         } else if (depth >= format.getMaxDepth()) {
            return Strings.EMPTY_OBJECT_DOTS;
         } else {
            TruffleStringBuilder sb = Strings.builderCreate();
            Strings.builderAppend(sb, '{');

            for (long i = 0L; i < keyCount; i++) {
               if (i > 0L) {
                  Strings.builderAppend(sb, Strings.COMMA_SPC);
                  if (i >= format.getMaxElements()) {
                     Strings.builderAppend(sb, Strings.DOT_DOT_DOT);
                     break;
                  }
               }

               Object key = keysInterop.readArrayElement(keys, i);

               assert InteropLibrary.getUncached().isString(key);

               String stringKey = Strings.interopAsString(key);
               Object value = objInterop.readMember(truffleObject, stringKey);
               Strings.builderAppend(sb, stringKey);
               Strings.builderAppend(sb, Strings.COLON_SPACE);
               Strings.builderAppend(sb, toDisplayStringInner(value, allowSideEffects, format, depth, truffleObject));
            }

            Strings.builderAppend(sb, '}');
            return Strings.builderToString(sb);
         }
      }
   }

   private static boolean fillEmptyArrayElements(TruffleStringBuilder sb, long index, long prevArrayIndex, boolean prependComma) {
      if (prevArrayIndex < index - 1L) {
         if (prependComma) {
            Strings.builderAppend(sb, Strings.COMMA_SPC);
         }

         long count = index - prevArrayIndex - 1L;
         if (count == 1L) {
            Strings.builderAppend(sb, Strings.EMPTY);
         } else {
            Strings.builderAppend(sb, Strings.EMPTY_X);
            Strings.builderAppend(sb, count);
         }

         return true;
      } else {
         return false;
      }
   }

   public static TruffleString collectionToConsoleString(
      JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, TruffleString name, JSHashMap map, int depth
   ) {
      assert JSMap.isJSMap(obj) || JSSet.isJSSet(obj);

      assert name != null;

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
            if (key != null) {
               if (!isFirst) {
                  Strings.builderAppend(sb, Strings.COMMA_SPC);
               }

               Strings.builderAppend(sb, toDisplayStringInner(key, allowSideEffects, format, depth, obj));
               if (isMap) {
                  Strings.builderAppend(sb, Strings.BIG_ARROW_SPACES);
                  Object value = cursor.getValue();
                  Strings.builderAppend(sb, toDisplayStringInner(value, allowSideEffects, format, depth, obj));
               }

               isFirst = false;
            }
         }

         Strings.builderAppend(sb, '}');
      }

      return Strings.builderToString(sb);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException toStringTypeError(Object value) {
      String what = value == null
         ? "null"
         : (JSDynamicObject.isJSDynamicObject(value) ? Strings.toJavaString(JSObject.defaultToString((JSDynamicObject)value)) : value.getClass().getName());
      throw Errors.createTypeErrorCannotConvertToString(what);
   }

   public static TruffleString booleanToString(boolean value) {
      return value ? JSBoolean.TRUE_NAME : JSBoolean.FALSE_NAME;
   }

   public static TruffleString toString(JSDynamicObject value) {
      if (value == Undefined.instance) {
         return Undefined.NAME;
      } else {
         return value == Null.instance ? Null.NAME : toString(JSObject.toPrimitive(value, JSToPrimitiveNode.Hint.String));
      }
   }

   public static String numberToJavaString(Number number) {
      return Strings.toJavaString(numberToString(number));
   }

   public static TruffleString numberToString(Number number) {
      CompilerAsserts.neverPartOfCompilation();
      if (number instanceof Integer) {
         return Strings.fromInt((Integer)number);
      } else if (number instanceof SafeInteger) {
         return doubleToString(((SafeInteger)number).doubleValue());
      } else if (number instanceof Double) {
         return doubleToString((Double)number);
      } else if (number instanceof Long) {
         return Strings.fromLong(number.longValue());
      } else {
         throw new UnsupportedOperationException("unknown number value: " + number.toString() + " " + number.getClass().getSimpleName());
      }
   }

   public static String javaToString(Object obj) {
      if (obj instanceof String) {
         return (String)obj;
      } else {
         return Strings.isTString(obj) ? Strings.toJavaString((TruffleString)obj) : Boundaries.javaToString(obj);
      }
   }

   public static boolean propertyKeyEquals(TruffleString.EqualNode equalsNode, Object a, Object b) {
      assert isPropertyKey(a);

      if (Strings.isTString(a)) {
         return Strings.isTString(b) ? Strings.equals(equalsNode, (TruffleString)a, (TruffleString)b) : false;
      } else if (a instanceof Symbol) {
         return ((Symbol)a).equals(b);
      } else {
         throw Errors.shouldNotReachHere();
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Object doubleToString(double d, int radix) {
      assert radix >= 2 && radix <= 36;

      if (Double.isNaN(d)) {
         return Strings.NAN;
      } else if (d == Double.POSITIVE_INFINITY) {
         return Strings.INFINITY;
      } else if (d == Double.NEGATIVE_INFINITY) {
         return Strings.NEGATIVE_INFINITY;
      } else {
         return d == 0.0 ? Strings.ZERO : formatDtoA(d, radix);
      }
   }

   public static TruffleString doubleToString(double d) {
      if (Double.isNaN(d)) {
         return Strings.NAN;
      } else if (d == Double.POSITIVE_INFINITY) {
         return Strings.INFINITY;
      } else if (d == Double.NEGATIVE_INFINITY) {
         return Strings.NEGATIVE_INFINITY;
      } else if (d == 0.0) {
         return Strings.ZERO;
      } else {
         return doubleIsRepresentableAsInt(d) ? Strings.fromInt((int)d) : Strings.fromJavaString(formatDtoA(d));
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static String formatDtoA(double value) {
      return DoubleConversion.toShortest(value);
   }

   @CompilerDirectives.TruffleBoundary
   public static Object formatDtoAPrecision(double value, int precision) {
      return Strings.fromJavaString(DoubleConversion.toPrecision(value, precision));
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
   public static Object formatDtoAFixed(double value, int digits) {
      return Strings.fromJavaString(DoubleConversion.toFixed(value, digits));
   }

   @CompilerDirectives.TruffleBoundary
   public static Object formatDtoA(double d, int radix) {
      return Strings.fromJavaString(DToA.jsDtobasestr(radix, d));
   }

   public static TruffleObject toObject(JSContext ctx, Object value) {
      requireObjectCoercible(value, ctx);
      if (CompilerDirectives.injectBranchProbability(0.75, JSObject.isJSObject(value))) {
         return (JSObject)value;
      } else {
         Object unboxedValue = value;
         if (isForeignObject(value)) {
            InteropLibrary interop = InteropLibrary.getUncached(value);

            assert !interop.isNull(value);

            unboxedValue = JSInteropUtil.toPrimitiveOrDefault(value, null, interop, null);
            if (unboxedValue == null) {
               return (TruffleObject)value;
            }
         }

         return toObjectFromPrimitive(ctx, unboxedValue, true);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleObject toObjectFromPrimitive(JSContext ctx, Object value, boolean useJavaWrapper) {
      JSRealm realm = JSRealm.get(null);
      if (value instanceof Boolean) {
         return JSBoolean.create(ctx, realm, (Boolean)value);
      } else if (Strings.isTString(value)) {
         return JSString.create(ctx, realm, (TruffleString)value);
      } else if (value instanceof BigInt) {
         return JSBigInt.create(ctx, realm, (BigInt)value);
      } else if (isNumber(value)) {
         return JSNumber.create(ctx, realm, (Number)value);
      } else if (value instanceof Symbol) {
         return JSSymbol.create(ctx, realm, (Symbol)value);
      } else {
         assert !isJSNative(value) && isJavaPrimitive(value) : value;

         return useJavaWrapper ? (TruffleObject)realm.getEnv().asBoxedGuestValue(value) : null;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean isSameValue(Object x, Object y) {
      if (x == Undefined.instance && y == Undefined.instance) {
         return true;
      } else if (x == Null.instance && y == Null.instance) {
         return true;
      } else if (x instanceof Integer && y instanceof Integer) {
         return (Integer)x == (Integer)y;
      } else if (isNumber(x) && isNumber(y)) {
         double xd = doubleValue((Number)x);
         double yd = doubleValue((Number)y);
         return Double.compare(xd, yd) == 0;
      } else if (Strings.isTString(x) && Strings.isTString(y)) {
         return x.toString().equals(y.toString());
      } else if (x instanceof Boolean && y instanceof Boolean) {
         return (Boolean)x == (Boolean)y;
      } else {
         return isBigInt(x) && isBigInt(y) ? ((BigInt)x).compareTo((BigInt)y) == 0 : x == y;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean equal(Object a, Object b) {
      if (a == b) {
         return true;
      } else if (isNullOrUndefined(a)) {
         return isNullish(b);
      } else if (isNullOrUndefined(b)) {
         return isNullish(a);
      } else if (a instanceof Boolean && b instanceof Boolean) {
         return a.equals(b);
      } else if (Strings.isTString(a) && Strings.isTString(b)) {
         return a.toString().equals(b.toString());
      } else if (isJavaNumber(a) && isJavaNumber(b)) {
         double da = doubleValue((Number)a);
         double db = doubleValue((Number)b);
         return da == db;
      } else if (isJavaNumber(a) && Strings.isTString(b)) {
         return equal(a, stringToNumber((TruffleString)b));
      } else if (Strings.isTString(a) && isJavaNumber(b)) {
         return equal(stringToNumber((TruffleString)a), b);
      } else if (isBigInt(a) && isBigInt(b)) {
         return a.equals(b);
      } else if (isBigInt(a) && Strings.isTString(b)) {
         return a.equals(stringToBigInt((TruffleString)b));
      } else if (Strings.isTString(a) && isBigInt(b)) {
         return b.equals(stringToBigInt((TruffleString)a));
      } else if (isJavaNumber(a) && isBigInt(b)) {
         return equalBigIntAndNumber((BigInt)b, (Number)a);
      } else if (isBigInt(a) && isJavaNumber(b)) {
         return equalBigIntAndNumber((BigInt)a, (Number)b);
      } else if (a instanceof Boolean) {
         return equal(booleanToNumber((Boolean)a), b);
      } else if (b instanceof Boolean) {
         return equal(a, booleanToNumber((Boolean)b));
      } else {
         if (isObject(a)) {
            assert b != Undefined.instance && b != Null.instance;

            if (JSOverloadedOperatorsObject.hasOverloadedOperators(a)) {
               if (isObject(b) && !JSOverloadedOperatorsObject.hasOverloadedOperators(b)) {
                  return equal(a, JSObject.toPrimitive((JSDynamicObject)b));
               }

               if (!isObject(b) && !isNumber(b) && !isBigInt(b) && !Strings.isTString(b)) {
                  return false;
               }

               return equalOverloaded(a, b);
            }

            if (IsPrimitiveNode.getUncached().executeBoolean(b)) {
               if (isNullish(b)) {
                  return false;
               }

               return equal(JSObject.toPrimitive((JSDynamicObject)a), b);
            }
         } else if (isObject(b)) {
            assert a != Undefined.instance && a != Null.instance;

            assert !isObject(a);

            if (JSOverloadedOperatorsObject.hasOverloadedOperators(b)) {
               if (!isNumber(a) && !isBigInt(a) && !Strings.isTString(a)) {
                  return false;
               }

               return equalOverloaded(a, b);
            }

            if (IsPrimitiveNode.getUncached().executeBoolean(a)) {
               if (isNullish(a)) {
                  return false;
               }

               return equal(a, JSObject.toPrimitive((JSDynamicObject)b));
            }
         }

         return !isForeignObject(a) && !isForeignObject(b) ? false : equalInterop(a, b);
      }
   }

   public static boolean isForeignObject(Object value) {
      return value instanceof TruffleObject && isForeignObject((TruffleObject)value);
   }

   public static boolean isForeignObject(TruffleObject value) {
      return !JSDynamicObject.isJSDynamicObject(value)
         && !(value instanceof Symbol)
         && !(value instanceof SafeInteger)
         && !(value instanceof BigInt)
         && !Strings.isTString(value);
   }

   private static boolean equalInterop(Object a, Object b) {
      assert a != null && b != null && (isForeignObject(a) || isForeignObject(b));

      boolean isAPrimitive = IsPrimitiveNode.getUncached().executeBoolean(a);
      boolean isBPrimitive = IsPrimitiveNode.getUncached().executeBoolean(b);
      if (!isAPrimitive && !isBPrimitive) {
         return InteropLibrary.getUncached(a).isIdentical(a, b, InteropLibrary.getUncached(b));
      } else if (isNullish(a)) {
         return isNullish(b);
      } else if (!isNullish(b)) {
         Object primLeft = isAPrimitive && !isForeignObject(a) ? a : toPrimitive(a);
         Object primRight = isBPrimitive && !isForeignObject(b) ? b : toPrimitive(b);

         assert !isForeignObject(primLeft) && !isForeignObject(primRight);

         return equal(primLeft, primRight);
      } else {
         assert !isNullish(a);

         return false;
      }
   }

   private static boolean equalBigIntAndNumber(BigInt a, Number b) {
      if (!(b instanceof Double) && !(b instanceof Float)) {
         return a.compareValueTo(longValue(b)) == 0;
      } else {
         double numberVal = doubleValue(b);
         return !Double.isNaN(numberVal) && a.compareValueTo(numberVal) == 0;
      }
   }

   private static boolean equalOverloaded(Object a, Object b) {
      Object operatorImplementation = OperatorSet.getOperatorImplementation(a, b, Strings.SYMBOL_EQUALS_EQUALS);
      return operatorImplementation == null ? false : toBoolean(call(operatorImplementation, Undefined.instance, new Object[]{a, b}));
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean identical(Object a, Object b) {
      if (a == b) {
         return a instanceof Double ? !Double.isNaN((Double)a) : true;
      } else if (a == Undefined.instance || b == Undefined.instance) {
         return false;
      } else if (a == Null.instance) {
         assert b != Undefined.instance;

         return InteropLibrary.getUncached(b).isNull(b);
      } else if (b == Null.instance) {
         assert a != Undefined.instance;

         return InteropLibrary.getUncached(a).isNull(a);
      } else if (isBigInt(a) && isBigInt(b)) {
         return a.equals(b);
      } else if (isJavaNumber(a) && isJavaNumber(b)) {
         return a instanceof Integer && b instanceof Integer ? (Integer)a == (Integer)b : doubleValue((Number)a) == doubleValue((Number)b);
      } else if (a instanceof Boolean && b instanceof Boolean) {
         return a.equals(b);
      } else if (Strings.isTString(a) && Strings.isTString(b)) {
         return a.toString().equals(b.toString());
      } else if (!isObject(a) && !isObject(b)) {
         InteropLibrary aInterop = InteropLibrary.getUncached(a);
         InteropLibrary bInterop = InteropLibrary.getUncached(b);
         return aInterop.isIdentical(a, b, bInterop) || aInterop.isNull(a) && bInterop.isNull(b);
      } else {
         return false;
      }
   }

   public static <T> T requireObjectCoercible(T argument, JSContext context) {
      if (argument != Undefined.instance && argument != Null.instance && (!isForeignObject(argument) || !InteropLibrary.getUncached(argument).isNull(argument))
         )
       {
         return argument;
      } else {
         throw Errors.createTypeErrorNotObjectCoercible(argument, null, context);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static PropertyDescriptor toPropertyDescriptor(Object property) {
      if (!isObject(property)) {
         throw Errors.createTypeErrorNotAnObject(property);
      } else {
         JSDynamicObject obj = (JSDynamicObject)property;
         PropertyDescriptor desc = PropertyDescriptor.createEmpty();
         if (JSObject.hasProperty(obj, JSAttributes.ENUMERABLE)) {
            desc.setEnumerable(toBoolean(JSObject.get(obj, JSAttributes.ENUMERABLE)));
         }

         if (JSObject.hasProperty(obj, JSAttributes.CONFIGURABLE)) {
            desc.setConfigurable(toBoolean(JSObject.get(obj, JSAttributes.CONFIGURABLE)));
         }

         boolean hasValue = JSObject.hasProperty(obj, JSAttributes.VALUE);
         if (hasValue) {
            desc.setValue(JSObject.get(obj, JSAttributes.VALUE));
         }

         boolean hasWritable = JSObject.hasProperty(obj, JSAttributes.WRITABLE);
         if (hasWritable) {
            desc.setWritable(toBoolean(JSObject.get(obj, JSAttributes.WRITABLE)));
         }

         boolean hasGet = JSObject.hasProperty(obj, JSAttributes.GET);
         if (hasGet) {
            Object getter = JSObject.get(obj, JSAttributes.GET);
            if (!isCallable(getter) && getter != Undefined.instance) {
               throw Errors.createTypeError("Getter must be a function");
            }

            desc.setGet(getter);
         }

         boolean hasSet = JSObject.hasProperty(obj, JSAttributes.SET);
         if (hasSet) {
            Object setter = JSObject.get(obj, JSAttributes.SET);
            if (!isCallable(setter) && setter != Undefined.instance) {
               throw Errors.createTypeError("Setter must be a function");
            }

            desc.setSet(setter);
         }

         if (!hasGet && !hasSet || !hasValue && !hasWritable) {
            return desc;
         } else {
            throw Errors.createTypeError("Invalid property. A property cannot both have accessors and be writable or have a value");
         }
      }
   }

   public static int valueInRadix10(char c) {
      return isAsciiDigit(c) ? c - 48 : -1;
   }

   public static int valueInRadix(char c, int radix) {
      int val = valueInRadixIntl(c);
      return val < radix ? val : -1;
   }

   private static int valueInRadixIntl(char c) {
      if (isAsciiDigit(c)) {
         return c - 48;
      } else if ('a' <= c && c <= 'z') {
         return c - 97 + 10;
      } else {
         return 65 <= c && c <= 90 ? c - 65 + 10 : -1;
      }
   }

   public static int valueInHex(char c) {
      if (isAsciiDigit(c)) {
         return c - 48;
      } else if ('a' <= c && c <= 'f') {
         return c - 97 + 10;
      } else {
         return 65 <= c && c <= 70 ? c - 65 + 10 : -1;
      }
   }

   public static boolean isHex(char c) {
      return isAsciiDigit(c) || 'a' <= c && c <= 'f' || 'A' <= c && c <= 'F';
   }

   @CompilerDirectives.TruffleBoundary
   public static long parseArrayIndexIsIndexRaw(Object o) {
      assert isArrayIndex(o);

      assert Strings.isTString(o) || o instanceof Number;

      return parseArrayIndexRaw(Strings.isTString(o) ? (TruffleString)o : Strings.fromNumber((Number)o), TruffleString.ReadCharUTF16Node.getUncached());
   }

   public static long parseArrayIndexRaw(TruffleString string, TruffleString.ReadCharUTF16Node charAtNode) {
      long value = 0L;
      int pos = 0;
      int len = Strings.length(string);
      if (len > 1 && Strings.charAt(charAtNode, string, pos) == '0') {
         return -1L;
      } else {
         while (pos < len) {
            char c = Strings.charAt(charAtNode, string, pos);
            if (!isAsciiDigit(c)) {
               return -1L;
            }

            value *= 10L;
            value += c - '0';
            pos++;
         }

         return value;
      }
   }

   public static TruffleString trimJSWhiteSpace(TruffleString string) {
      return trimJSWhiteSpace(string, false);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString trimJSWhiteSpace(TruffleString string, boolean useLineTerminators) {
      int firstIdx = firstNonWhitespaceIndex(string, useLineTerminators, TruffleString.ReadCharUTF16Node.getUncached());
      int lastIdx = lastNonWhitespaceIndex(string, useLineTerminators, TruffleString.ReadCharUTF16Node.getUncached());
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
      int idx = 0;

      while (
         idx < Strings.length(string)
            && (isWhiteSpace(Strings.charAt(charAtNode, string, idx)) || useLineTerminators && isLineTerminator(Strings.charAt(charAtNode, string, idx)))
      ) {
         idx++;
      }

      return idx;
   }

   public static int lastNonWhitespaceIndex(TruffleString string, boolean useLineTerminators, TruffleString.ReadCharUTF16Node charAtNode) {
      int idx = Strings.length(string) - 1;

      while (
         idx >= 0 && (isWhiteSpace(Strings.charAt(charAtNode, string, idx)) || useLineTerminators && isLineTerminator(Strings.charAt(charAtNode, string, idx)))
      ) {
         idx--;
      }

      return idx;
   }

   public static boolean isWhiteSpace(char cp) {
      return isAsciiDigit(cp)
         ? false
         : '\t' <= cp && cp <= '\r'
            || 8192 <= cp && cp <= 8202
            || cp == ' '
            || cp == 160
            || cp == 5760
            || cp == 8232
            || cp == 8233
            || cp == 8239
            || cp == 8287
            || cp == 12288
            || cp == '\ufeff';
   }

   private static boolean isLineTerminator(char codePoint) {
      switch (codePoint) {
         case '\n':
         case '\r':
         case '\u2028':
         case '\u2029':
            return true;
         default:
            return false;
      }
   }

   public static boolean isValidArrayLength(long longValue) {
      return 0L <= longValue && longValue <= 4294967295L;
   }

   public static boolean isValidArrayLength(double doubleValue) {
      long longValue = (long)doubleValue;
      return doubleValue == longValue && isValidArrayLength(longValue);
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
      return 0L <= longValue && longValue < 4294967295L;
   }

   public static boolean isArrayIndex(double doubleValue) {
      long longValue = (long)doubleValue;
      return longValue == doubleValue && isArrayIndex(longValue);
   }

   public static boolean isArrayIndexString(TruffleString property) {
      long idx = propertyNameToArrayIndex(property, TruffleString.ReadCharUTF16Node.getUncached());
      return isArrayIndex(idx);
   }

   public static boolean isArrayIndex(Object property) {
      if (property instanceof Integer) {
         return isArrayIndex(((Integer)property).intValue());
      } else if (property instanceof Long) {
         return isArrayIndex(((Long)property).longValue());
      } else if (property instanceof Double) {
         return isArrayIndex(((Double)property).doubleValue());
      } else if (Strings.isTString(property)) {
         long idx = propertyNameToArrayIndex(toStringIsString(property), TruffleString.ReadCharUTF16Node.getUncached());
         return isArrayIndex(idx);
      } else {
         return false;
      }
   }

   public static long castArrayIndex(double doubleValue) {
      assert isArrayIndex(doubleValue);

      return (long)doubleValue & 4294967295L;
   }

   public static long castArrayIndex(long longValue) {
      assert isArrayIndex(longValue);

      return longValue;
   }

   public static boolean isAsciiDigit(char c) {
      return '0' <= c && c <= '9';
   }

   @CompilerDirectives.TruffleBoundary
   public static long propertyNameToArrayIndex(TruffleString propertyName, TruffleString.ReadCharUTF16Node charAtNode) {
      return propertyName != null && arrayIndexLengthInRange(propertyName) && isAsciiDigit(Strings.charAt(propertyName, 0))
         ? parseArrayIndexRaw(propertyName, charAtNode)
         : -1L;
   }

   public static boolean arrayIndexLengthInRange(TruffleString indexStr) {
      int len = Strings.length(indexStr);
      return 0 < len && len <= 10;
   }

   public static long propertyKeyToArrayIndex(Object propertyKey) {
      return Strings.isTString(propertyKey) ? propertyNameToArrayIndex((TruffleString)propertyKey, TruffleString.ReadCharUTF16Node.getUncached()) : -1L;
   }

   @CompilerDirectives.TruffleBoundary
   public static long propertyNameToIntegerIndex(TruffleString propertyName) {
      return propertyName != null && Strings.length(propertyName) > 0 && Strings.length(propertyName) <= 16 && isAsciiDigit(Strings.charAt(propertyName, 0))
         ? parseArrayIndexRaw(propertyName, TruffleString.ReadCharUTF16Node.getUncached())
         : -1L;
   }

   public static long propertyKeyToIntegerIndex(Object propertyKey) {
      return Strings.isTString(propertyKey) ? propertyNameToIntegerIndex((TruffleString)propertyKey) : -1L;
   }

   public static boolean isJSNative(Object value) {
      return JSDynamicObject.isJSDynamicObject(value) || isJSPrimitive(value);
   }

   public static boolean isJSPrimitive(Object value) {
      return isNumber(value)
         || value instanceof BigInt
         || value instanceof Boolean
         || Strings.isTString(value)
         || value == Undefined.instance
         || value == Null.instance
         || value instanceof Symbol;
   }

   public static TruffleString toStringIsString(Object value) {
      assert Strings.isTString(value);

      return (TruffleString)value;
   }

   public static boolean isStringClass(Class<?> clazz) {
      return TruffleString.class.isAssignableFrom(clazz);
   }

   public static Object nullToUndefined(Object value) {
      return value == null ? Undefined.instance : value;
   }

   public static Object undefinedToNull(Object value) {
      return value == Undefined.instance ? null : value;
   }

   public static Object toJSNull(Object value) {
      return value == null ? Null.instance : value;
   }

   public static Object toJavaNull(Object value) {
      return value == Null.instance ? null : value;
   }

   @CompilerDirectives.TruffleBoundary
   public static Object jsObjectToJavaObject(Object obj) {
      return toJavaNull(undefinedToNull(obj));
   }

   public static boolean isPropertyKey(Object key) {
      return Strings.isTString(key) || key instanceof Symbol;
   }

   public static Object boxIndex(long longIndex, ConditionProfile indexInIntRangeConditionProfile) {
      return indexInIntRangeConditionProfile.profile(longIndex <= 2147483647L) ? (int)longIndex : (double)longIndex;
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInt stringToBigInt(TruffleString s) {
      try {
         return Strings.parseBigInt(s);
      } catch (NumberFormatException var2) {
         return null;
      }
   }

   public static int intValue(Number number) {
      if (number instanceof Integer) {
         return (Integer)number;
      } else {
         return number instanceof Double ? ((Double)number).intValue() : intValueVirtual(number);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static int intValueVirtual(Number number) {
      return number.intValue();
   }

   public static double doubleValue(Number number) {
      if (number instanceof Double) {
         return (Double)number;
      } else {
         return number instanceof Integer ? ((Integer)number).doubleValue() : doubleValueVirtual(number);
      }
   }

   public static double doubleValue(Number number, BranchProfile profile) {
      if (number instanceof Double) {
         return (Double)number;
      } else if (number instanceof Integer) {
         return ((Integer)number).doubleValue();
      } else {
         profile.enter();
         return doubleValueVirtual(number);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static double doubleValueVirtual(Number number) {
      return number.doubleValue();
   }

   public static float floatValue(Number n) {
      if (n instanceof Double) {
         return ((Double)n).floatValue();
      } else {
         return n instanceof Integer ? ((Integer)n).floatValue() : floatValueVirtual(n);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static float floatValueVirtual(Number n) {
      return n.floatValue();
   }

   public static long longValue(Number n) {
      if (n instanceof Integer) {
         return ((Integer)n).longValue();
      } else if (n instanceof Double) {
         return ((Double)n).longValue();
      } else {
         return n instanceof SafeInteger ? ((SafeInteger)n).longValue() : longValueVirtual(n);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static long longValueVirtual(Number n) {
      return n.longValue();
   }

   public static long toLong(Number value) {
      return longValue(value);
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
      } else {
         JSObject obj = JSOrdinary.create(context, JSRealm.get(null));
         if (desc.hasValue()) {
            JSObject.set(obj, JSAttributes.VALUE, desc.getValue());
         }

         if (desc.hasWritable()) {
            JSObject.set(obj, JSAttributes.WRITABLE, desc.getWritable());
         }

         if (desc.hasGet()) {
            JSObject.set(obj, JSAttributes.GET, desc.getGet());
         }

         if (desc.hasSet()) {
            JSObject.set(obj, JSAttributes.SET, desc.getSet());
         }

         if (desc.hasEnumerable()) {
            JSObject.set(obj, JSAttributes.ENUMERABLE, desc.getEnumerable());
         }

         if (desc.hasConfigurable()) {
            JSObject.set(obj, JSAttributes.CONFIGURABLE, desc.getConfigurable());
         }

         return obj;
      }
   }

   public static Object getArgOrUndefined(Object[] args, int i) {
      return args.length > i ? args[i] : Undefined.instance;
   }

   public static Object getArg(Object[] args, int i, Object defaultValue) {
      return args.length > i ? args[i] : defaultValue;
   }

   public static long getOffset(long start, long length, ConditionProfile profile) {
      return profile.profile(start < 0L) ? Math.max(start + length, 0L) : Math.min(start, length);
   }

   public static int getOffset(int start, int length, ConditionProfile profile) {
      return profile.profile(start < 0) ? Math.max(start + length, 0) : Math.min(start, length);
   }

   @CompilerDirectives.TruffleBoundary
   public static long parseSafeInteger(TruffleString s) {
      return parseSafeInteger(s, 0, Strings.length(s), 10);
   }

   @CompilerDirectives.TruffleBoundary
   public static long parseSafeInteger(TruffleString s, int beginIndex, int endIndex, int radix) {
      return parseLong(s, beginIndex, endIndex, radix, radix == 10, MAX_SAFE_INTEGER_LONG);
   }

   private static long parseLong(TruffleString s, int beginIndex, int endIndex, int radix, boolean parseSign, long limit) {
      assert beginIndex >= 0 && beginIndex <= endIndex && endIndex <= Strings.length(s);

      assert radix >= 2 && radix <= 36;

      assert limit <= Long.MAX_VALUE / (long)radix - (long)radix;

      boolean negative = false;
      int i = beginIndex;
      if (beginIndex >= endIndex) {
         return Long.MIN_VALUE;
      } else {
         if (parseSign) {
            char firstChar = Strings.charAt(s, beginIndex);
            if (firstChar < '0') {
               if (firstChar == '-') {
                  negative = true;
               } else if (firstChar != '+') {
                  return Long.MIN_VALUE;
               }

               i = beginIndex + 1;
            }

            if (i >= endIndex) {
               return Long.MIN_VALUE;
            }
         }

         long result;
         for (result = 0L; i < endIndex; i++) {
            char c = Strings.charAt(s, i);
            int digit = valueInRadix(c, radix);
            if (digit < 0) {
               return Long.MIN_VALUE;
            }

            result *= radix;
            result += digit;
            if (result > limit) {
               return Long.MIN_VALUE;
            }
         }

         assert result >= 0L;

         if (negative && result == 0L) {
            return Long.MIN_VALUE;
         } else {
            return negative ? -result : result;
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Number parseRawFitsLong(TruffleString string, int radix, int startPos, int endPos, boolean negate) {
      assert startPos < endPos;

      int pos = startPos;

      long value;
      for (value = 0L; pos < endPos; pos++) {
         char c = Strings.charAt(string, pos);
         int cval = valueInRadix(c, radix);
         if (cval < 0) {
            if (pos == startPos) {
               return Double.NaN;
            }
            break;
         }

         value *= radix;
         value += cval;
      }

      if (value == 0L && negate && Strings.charAt(string, startPos) == '0') {
         return -0.0;
      } else {
         assert value >= 0L;

         long signedValue = negate ? -value : value;
         return (Number)(value <= 2147483647L ? (int)signedValue : (double)signedValue);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static double parseRawDontFitLong(TruffleString string, int radix, int startPos, int endPos, boolean negate) {
      assert startPos < endPos;

      int pos = startPos;

      double value;
      for (value = 0.0; pos < endPos; pos++) {
         char c = Strings.charAt(string, pos);
         int cval = valueInRadix(c, radix);
         if (cval < 0) {
            if (pos == startPos) {
               return Double.NaN;
            }
            break;
         }

         value *= radix;
         value += cval;
      }

      assert value >= 0.0;

      return negate ? -value : value;
   }

   public static boolean createDataProperty(JSDynamicObject o, Object p, Object v) {
      assert isObject(o);

      assert isPropertyKey(p);

      return JSObject.defineOwnProperty(o, p, PropertyDescriptor.createDataDefault(v));
   }

   public static boolean createDataProperty(JSDynamicObject o, Object p, Object v, boolean doThrow) {
      assert isObject(o);

      assert isPropertyKey(p);

      boolean success = JSObject.defineOwnProperty(o, p, PropertyDescriptor.createDataDefault(v), doThrow);

      assert !doThrow || success : "should have thrown";

      return success;
   }

   public static boolean createDataPropertyOrThrow(JSDynamicObject o, Object p, Object v) {
      return createDataProperty(o, p, v, true);
   }

   public static void createNonEnumerableDataPropertyOrThrow(JSDynamicObject o, Object p, Object v) {
      PropertyDescriptor newDesc = PropertyDescriptor.createData(v, JSAttributes.getDefaultNotEnumerable());
      definePropertyOrThrow(o, p, newDesc);
   }

   public static void definePropertyOrThrow(JSDynamicObject o, Object key, PropertyDescriptor desc) {
      assert isObject(o);

      assert isPropertyKey(key);

      boolean success = JSObject.getJSClass(o).defineOwnProperty(o, key, desc, true);

      assert success;
   }

   public static boolean isPrototypeOf(JSDynamicObject object, JSDynamicObject prototype) {
      JSDynamicObject prototypeChainObject = object;

      do {
         prototypeChainObject = JSObject.getPrototype(prototypeChainObject);
         if (prototypeChainObject == prototype) {
            return true;
         }
      } while (prototypeChainObject != Null.instance);

      return false;
   }

   public static JSDynamicObject createArrayFromList(JSContext context, JSRealm realm, List<? extends Object> list) {
      return JSArray.createConstant(context, realm, Boundaries.listToArray(list));
   }

   public static boolean isCallable(Object value) {
      if (JSFunction.isJSFunction(value)) {
         return true;
      } else if (JSProxy.isJSProxy(value)) {
         return isCallableProxy((JSDynamicObject)value);
      } else {
         return value instanceof TruffleObject ? isCallableForeign(value) : false;
      }
   }

   public static boolean isCallableIsJSObject(JSDynamicObject value) {
      assert JSDynamicObject.isJSDynamicObject(value);

      if (JSFunction.isJSFunction(value)) {
         return true;
      } else {
         return JSProxy.isJSProxy(value) ? isCallableProxy(value) : false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean isCallableForeign(Object value) {
      if (!isForeignObject(value)) {
         return false;
      } else {
         InteropLibrary interop = InteropLibrary.getUncached();
         return interop.isExecutable(value) || interop.isInstantiable(value);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean isCallableProxy(JSDynamicObject proxy) {
      assert JSProxy.isJSProxy(proxy);

      Object target = JSProxy.getTarget(proxy);
      return isCallable(target);
   }

   public static boolean isArray(Object obj) {
      if (JSArray.isJSArray(obj)) {
         return true;
      } else if (JSProxy.isJSProxy(obj)) {
         return isProxyAnArray((JSDynamicObject)obj);
      } else {
         return isForeignObject(obj) ? InteropLibrary.getUncached().hasArrayElements(obj) : false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean isProxyAnArray(JSDynamicObject proxy) {
      assert JSProxy.isJSProxy(proxy);

      if (JSProxy.isRevoked(proxy)) {
         throw Errors.createTypeErrorProxyRevoked();
      } else {
         return isArrayProxyRecurse(proxy);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static boolean isArrayProxyRecurse(JSDynamicObject proxy) {
      return isArray(JSProxy.getTarget(proxy));
   }

   @CompilerDirectives.TruffleBoundary
   public static Object toPropertyKey(Object arg) {
      if (Strings.isTString(arg)) {
         return arg;
      } else if (arg instanceof Symbol) {
         return arg;
      } else {
         Object key = toPrimitive(arg);
         if (key instanceof Symbol) {
            return key;
         } else {
            return Strings.isTString(key) ? key : toString(key);
         }
      }
   }

   public static Object call(Object fnObj, Object holder, Object[] arguments) {
      if (JSFunction.isJSFunction(fnObj)) {
         return JSFunction.call((JSFunctionObject)fnObj, holder, arguments);
      } else if (JSProxy.isJSProxy(fnObj)) {
         return JSProxy.call((JSDynamicObject)fnObj, holder, arguments);
      } else if (isForeignObject(fnObj)) {
         return JSInteropUtil.call(fnObj, arguments);
      } else {
         throw Errors.createTypeErrorNotAFunction(fnObj);
      }
   }

   public static Object call(Object fnObj, Object holder, Object[] arguments, Node encapsulatingNode) {
      EncapsulatingNodeReference encapsulating = null;
      Node prev = null;
      if (encapsulatingNode != null) {
         encapsulating = EncapsulatingNodeReference.getCurrent();
         prev = encapsulating.set(encapsulatingNode);
      }

      Object var6;
      try {
         var6 = call(fnObj, holder, arguments);
      } finally {
         if (encapsulatingNode != null) {
            encapsulating.set(prev);
         }
      }

      return var6;
   }

   public static Object construct(Object fnObj, Object[] arguments) {
      if (JSFunction.isJSFunction(fnObj)) {
         return JSFunction.construct((JSFunctionObject)fnObj, arguments);
      } else if (JSProxy.isJSProxy(fnObj)) {
         return JSProxy.construct((JSDynamicObject)fnObj, arguments);
      } else if (isForeignObject(fnObj)) {
         return JSInteropUtil.construct(fnObj, arguments);
      } else {
         throw Errors.createTypeErrorNotAFunction(fnObj);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Object canonicalNumericIndexString(TruffleString s) {
      if (Strings.isEmpty(s) || !isNumericIndexStart(Strings.charAt(s, 0))) {
         return Undefined.instance;
      } else if (Strings.NEGATIVE_ZERO.equals(s)) {
         return -0.0;
      } else {
         Number n = stringToNumber(s);
         return !numberToString(n).equals(s) ? Undefined.instance : n;
      }
   }

   private static boolean isNumericIndexStart(char c) {
      return isAsciiDigit(c) || c == '-' || c == 'I' || c == 'N';
   }

   public static boolean isInteger(Object obj) {
      if (!isNumber(obj)) {
         return false;
      } else {
         double d = doubleValue((Number)obj);
         return d - truncateDouble(d) == 0.0;
      }
   }

   public static int comparePropertyKeys(Object key1, Object key2) {
      assert isPropertyKey(key1) && isPropertyKey(key2);

      boolean isString1 = Strings.isTString(key1);
      boolean isString2 = Strings.isTString(key2);
      if (isString1 && isString2) {
         long index1 = propertyNameToArrayIndex((TruffleString)key1, TruffleString.ReadCharUTF16Node.getUncached());
         long index2 = propertyNameToArrayIndex((TruffleString)key2, TruffleString.ReadCharUTF16Node.getUncached());
         boolean isIndex1 = isArrayIndex(index1);
         boolean isIndex2 = isArrayIndex(index2);
         if (isIndex1 && isIndex2) {
            return Long.compare(index1, index2);
         } else if (isIndex1) {
            return -1;
         } else {
            return isIndex2 ? 1 : 0;
         }
      } else if (isString1) {
         return -1;
      } else {
         return isString2 ? 1 : 0;
      }
   }

   public static TruffleString getConstructorName(JSDynamicObject receiver) {
      Object toStringTag = getDataProperty(receiver, Symbol.SYMBOL_TO_STRING_TAG);
      if (Strings.isTString(toStringTag)) {
         return (TruffleString)toStringTag;
      } else {
         if (!isProxy(receiver)) {
            JSDynamicObject prototype = JSObject.getPrototype(receiver);
            if (prototype != Null.instance) {
               Object constructor = getDataProperty(prototype, JSObject.CONSTRUCTOR);
               if (JSFunction.isJSFunction(constructor)) {
                  return JSFunction.getName((JSFunctionObject)constructor);
               }
            }
         }

         return JSObject.getClassName(receiver);
      }
   }

   public static Object getDataProperty(JSDynamicObject thisObj, Object key) {
      assert isPropertyKey(key);

      for (JSDynamicObject current = thisObj; current != Null.instance && current != null && !isProxy(current); current = JSObject.getPrototype(current)) {
         PropertyDescriptor desc = JSObject.getOwnProperty(current, key);
         if (desc != null) {
            if (desc.isDataDescriptor()) {
               return desc.getValue();
            }
            break;
         }
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

   public static boolean isSafeInteger(double value) {
      return value >= MIN_SAFE_INTEGER && value <= MAX_SAFE_INTEGER;
   }

   public static boolean isSafeInteger(long value) {
      return value >= MIN_SAFE_INTEGER_LONG && value <= MAX_SAFE_INTEGER_LONG;
   }

   @CompilerDirectives.TruffleBoundary
   public static JSRealm getFunctionRealm(Object obj, JSRealm currentRealm) {
      if (JSObject.isJSObject(obj)) {
         JSObject dynObj = (JSObject)obj;
         if (JSFunction.isJSFunction(dynObj)) {
            if (JSFunction.isBoundFunction(dynObj)) {
               return getFunctionRealm(JSFunction.getBoundTargetFunction(dynObj), currentRealm);
            }

            return JSFunction.getRealm(dynObj);
         }

         if (JSProxy.isJSProxy(dynObj)) {
            if (JSProxy.getHandler(dynObj) == Null.instance) {
               throw Errors.createTypeErrorProxyRevoked();
            }

            return getFunctionRealm(JSProxy.getTarget(dynObj), currentRealm);
         }
      }

      return currentRealm;
   }

   public static boolean isConstructor(Object constrObj) {
      if (JSFunction.isConstructor(constrObj)) {
         return true;
      } else if (JSProxy.isJSProxy(constrObj)) {
         return isConstructorProxy((JSDynamicObject)constrObj);
      } else {
         return constrObj instanceof TruffleObject ? isConstructorForeign(constrObj) : false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean isConstructorForeign(Object value) {
      return isForeignObject(value) ? InteropLibrary.getUncached().isInstantiable(value) : false;
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean isConstructorProxy(JSDynamicObject constrObj) {
      assert JSProxy.isJSProxy(constrObj);

      return isConstructor(JSProxy.getTarget(constrObj));
   }

   public static boolean isGenerator(Object genObj) {
      if (JSFunction.isJSFunction(genObj) && JSFunction.isGenerator((JSFunctionObject)genObj)) {
         return true;
      } else {
         return JSProxy.isJSProxy(genObj) ? isGeneratorProxy((JSDynamicObject)genObj) : false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean isGeneratorProxy(JSDynamicObject genObj) {
      assert JSProxy.isJSProxy(genObj);

      return isGenerator(JSProxy.getTarget(genObj));
   }

   @CompilerDirectives.TruffleBoundary
   public static List<Object> createListFromArrayLikeAllowSymbolString(Object obj) {
      if (!isObject(obj)) {
         throw Errors.createTypeErrorNotAnObject(obj);
      } else {
         JSDynamicObject jsObj = (JSDynamicObject)obj;
         long len = toLength(JSObject.get(jsObj, JSAbstractArray.LENGTH));
         if (len > 2147483647L) {
            throw Errors.createRangeError("range exceeded");
         } else {
            List<Object> list = new ArrayList<>();

            for (long index = 0L; index < len; index++) {
               Object next = JSObject.get(jsObj, index);
               if (!Strings.isTString(next) && !(next instanceof Symbol)) {
                  throw Errors.createTypeError("Symbol or String expected");
               }

               Boundaries.listAdd(list, next);
            }

            return list;
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static String quote(String value) {
      int pos;
      for (pos = 0; pos < value.length(); pos++) {
         char ch = value.charAt(pos);
         if (ch < ' ' || ch == '\\' || ch == '"') {
            break;
         }
      }

      StringBuilder builder = new StringBuilder(value.length() + 2);
      builder.append('"');
      builder.append(value, 0, pos);

      for (int i = pos; i < value.length(); i++) {
         char ch = value.charAt(i);
         if (ch < ' ') {
            if (ch == '\b') {
               builder.append("\\b");
            } else if (ch == '\f') {
               builder.append("\\f");
            } else if (ch == '\n') {
               builder.append("\\n");
            } else if (ch == '\r') {
               builder.append("\\r");
            } else if (ch == '\t') {
               builder.append("\\t");
            } else {
               builder.append("\\u00");
               builder.append(Character.forDigit((ch & 240) >> 4, 16));
               builder.append(Character.forDigit(ch & 15, 16));
            }
         } else if (ch == '\\') {
            builder.append("\\\\");
         } else if (ch == '"') {
            builder.append("\\\"");
         } else {
            builder.append(ch);
         }
      }

      builder.append('"');
      return builder.toString();
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString quote(TruffleString value) {
      int pos;
      for (pos = 0; pos < Strings.length(value); pos++) {
         char ch = Strings.charAt(value, pos);
         if (ch < ' ' || ch == '\\' || ch == '"') {
            break;
         }
      }

      TruffleStringBuilder builder = Strings.builderCreate(Strings.length(value) + 2);
      Strings.builderAppend(builder, '"');
      Strings.builderAppend(builder, value, 0, pos);

      for (int i = pos; i < Strings.length(value); i++) {
         char ch = Strings.charAt(value, i);
         if (ch < ' ') {
            if (ch == '\b') {
               Strings.builderAppend(builder, Strings.ESCAPE_B);
            } else if (ch == '\f') {
               Strings.builderAppend(builder, Strings.ESCAPE_F);
            } else if (ch == '\n') {
               Strings.builderAppend(builder, Strings.ESCAPE_N);
            } else if (ch == '\r') {
               Strings.builderAppend(builder, Strings.ESCAPE_R);
            } else if (ch == '\t') {
               Strings.builderAppend(builder, Strings.ESCAPE_T);
            } else {
               Strings.builderAppend(builder, Strings.ESCAPE_U_00);
               Strings.builderAppend(builder, Character.forDigit((ch & 240) >> 4, 16));
               Strings.builderAppend(builder, Character.forDigit(ch & 15, 16));
            }
         } else if (ch == '\\') {
            Strings.builderAppend(builder, Strings.ESCAPE_BACKSLASH);
         } else if (ch == '"') {
            Strings.builderAppend(builder, Strings.ESCAPE_QUOTE);
         } else {
            Strings.builderAppend(builder, ch);
         }
      }

      Strings.builderAppend(builder, '"');
      return Strings.builderToString(builder);
   }

   public static JSDynamicObject expectJSObject(Object to, BranchProfile errorBranch) {
      if (!JSDynamicObject.isJSDynamicObject(to)) {
         errorBranch.enter();
         throw Errors.createTypeErrorJSObjectExpected();
      } else {
         return (JSDynamicObject)to;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Object exportValue(Object value) {
      return ExportValueNode.getUncached().execute(value);
   }

   @CompilerDirectives.TruffleBoundary
   public static Object[] exportValueArray(Object[] arr) {
      Object[] newArr = new Object[arr.length];

      for (int i = 0; i < arr.length; i++) {
         newArr[i] = exportValue(arr[i]);
      }

      return newArr;
   }

   @CompilerDirectives.TruffleBoundary
   public static Object importValue(Object value) {
      assert value != null;

      return ImportValueNode.getUncached().executeWithTarget(value);
   }

   public static boolean intIsRepresentableAsFloat(int value) {
      return -16777216 <= value && value <= 16777216;
   }

   public static boolean isJavaPrimitive(Object value) {
      return value != null && value instanceof Boolean
         || value instanceof Byte
         || value instanceof Short
         || value instanceof Integer
         || value instanceof Long
         || value instanceof Float
         || value instanceof Double
         || value instanceof Character;
   }

   public static <E extends Throwable> RuntimeException rethrow(Throwable ex) throws E {
      throw ex;
   }

   public static boolean isTypedArrayBigIntFactory(TypedArrayFactory factory) {
      return factory == TypedArrayFactory.BigInt64Array || factory == TypedArrayFactory.BigUint64Array;
   }

   public static GraalJSException getException(Object errorObject) {
      return (GraalJSException)(JSError.isJSError(errorObject) ? JSError.getException((JSDynamicObject)errorObject) : UserScriptException.create(errorObject));
   }

   public static IteratorRecord getIterator(JSDynamicObject iteratedObject) {
      Object method = JSObject.get(iteratedObject, Symbol.SYMBOL_ITERATOR);
      if (!isCallable(method)) {
         throw Errors.createTypeErrorNotIterable(iteratedObject, null);
      } else {
         Object iterator = call(method, iteratedObject, new Object[0]);
         if (isObject(iterator)) {
            return IteratorRecord.create((JSDynamicObject)iterator, JSObject.get((JSDynamicObject)iterator, NEXT), false);
         } else {
            throw Errors.createTypeErrorNotAnObject(iterator);
         }
      }
   }

   public static Object iteratorStep(IteratorRecord iteratorRecord) {
      Object nextMethod = iteratorRecord.getNextMethod();
      JSDynamicObject iterator = iteratorRecord.getIterator();
      Object result = call(nextMethod, iterator, new Object[0]);
      if (!isObject(result)) {
         throw Errors.createTypeErrorIteratorResultNotObject(result, null);
      } else {
         boolean done = toBoolean(JSObject.get((JSDynamicObject)result, DONE));
         return done ? false : result;
      }
   }

   public static Object iteratorValue(JSDynamicObject iterator) {
      return JSObject.get(iterator, VALUE);
   }

   public static void iteratorClose(JSDynamicObject iterator) {
      Object returnMethod = JSObject.get(iterator, Strings.RETURN);
      if (returnMethod != Undefined.instance) {
         Object innerResult = call(returnMethod, iterator, new Object[0]);
         if (!isObject(innerResult)) {
            throw Errors.createTypeErrorIterResultNotAnObject(innerResult, null);
         }
      }
   }

   public static boolean isIntegralNumber(double arg) {
      return arg - truncateDouble(arg) == 0.0;
   }
}
