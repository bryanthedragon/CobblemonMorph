package com.oracle.truffle.js.runtime.builtins;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.Grego;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.DateFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.TimeZone;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.DateFunctionBuiltins;
import com.oracle.truffle.js.builtins.DatePrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public final class JSDate extends JSNonProxy implements JSConstructorFactory.WithFunctions, PrototypeSupplier {
   public static final TruffleString CLASS_NAME = Strings.constant("Date");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("Date.prototype");
   public static final JSDate INSTANCE = new JSDate();
   private static final int HOURS_PER_DAY = 24;
   private static final int MINUTES_PER_HOUR = 60;
   private static final int SECONDS_PER_MINUTE = 60;
   private static final int MS_PER_SECOND = 1000;
   public static final int MS_PER_MINUTE = 60000;
   private static final int MS_PER_HOUR = 3600000;
   public static final int MS_PER_DAY = 86400000;
   public static final double MAX_DATE = 8.64E15;
   public static final double MAX_YEAR_VALUE = 300000.0;
   private static final int DAYS_IN_4_YEARS = 1461;
   private static final int DAYS_IN_100_YEARS = 36524;
   private static final int DAYS_IN_400_YEARS = 146097;
   private static final int DAYS_FROM_1970_TO_2000 = 10957;
   private static final int YEAR_SHIFT = 280000;
   private static final int DAY_SHIFT = 102267900;
   public static final TruffleString INVALID_DATE_STRING = Strings.constant("Invalid Date");

   private JSDate() {
   }

   public static void setTimeMillisField(JSDateObject obj, double timeMillis) {
      assert isJSDate(obj);

      obj.setTimeMillis(timeMillis);
   }

   public static double getTimeMillisField(JSDateObject obj) {
      assert isJSDate(obj);

      return obj.getTimeMillis();
   }

   public static boolean isJSDate(Object obj) {
      return obj instanceof JSDateObject;
   }

   @Override
   public TruffleString getClassName() {
      return CLASS_NAME;
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return this.getClassName();
   }

   @Override
   public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
      return this.getClassName(object);
   }

   @Override
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
      JSContext ctx = realm.getContext();
      JSObject datePrototype;
      if (ctx.getEcmaScriptVersion() < 6) {
         Shape protoShape = JSShape.createPrototypeShape(realm.getContext(), INSTANCE, realm.getObjectPrototype());
         datePrototype = JSDateObject.create(protoShape, Double.NaN);
         JSObjectUtil.setOrVerifyPrototype(ctx, datePrototype, realm.getObjectPrototype());
      } else {
         datePrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      }

      JSObjectUtil.putConstructorProperty(ctx, datePrototype, ctor);
      JSObjectUtil.putFunctionsFromContainer(realm, datePrototype, DatePrototypeBuiltins.BUILTINS);
      if (ctx.isOptionAnnexB()) {
         Object utcStringFunction = JSDynamicObject.getOrNull(datePrototype, Strings.TO_UTC_STRING);
         JSObjectUtil.putDataProperty(ctx, datePrototype, Strings.TO_GMT_STRING, utcStringFunction, JSAttributes.getDefaultNotEnumerable());
      }

      return datePrototype;
   }

   @Override
   public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, DateFunctionBuiltins.BUILTINS);
   }

   @CompilerDirectives.TruffleBoundary
   public static double executeConstructor(double[] argsEvaluated, boolean inputIsUTC) {
      double year = argsEvaluated.length > 0 ? argsEvaluated[0] : Double.NaN;
      double month = argsEvaluated.length > 1 ? argsEvaluated[1] : 0.0;
      if (!Double.isNaN(year) && !Double.isInfinite(year) && !Double.isNaN(month) && !Double.isInfinite(month)) {
         double day = getArgOrDefault(argsEvaluated, 2, 1);
         double hour = getArgOrDefault(argsEvaluated, 3, 0);
         double minute = getArgOrDefault(argsEvaluated, 4, 0);
         double second = getArgOrDefault(argsEvaluated, 5, 0);
         double ms = getArgOrDefault(argsEvaluated, 6, 0);
         return makeDate(toFullYear(year), month, day, hour, minute, second, ms, inputIsUTC ? 0 : null);
      } else {
         return Double.NaN;
      }
   }

   private static double getArgOrDefault(double[] argsEvaluated, int index, int def) {
      return argsEvaluated.length > index ? argsEvaluated[index] : def;
   }

   private static double day(double t) {
      return floor(t / 8.64E7);
   }

   private static double timeWithinDay(double t) {
      return secureNegativeModulo(t, 8.64E7);
   }

   public static int dayFromYear(int y) {
      return 365 * (y - 1970) + Math.floorDiv(y - 1969, 4) - Math.floorDiv(y - 1901, 100) + Math.floorDiv(y - 1601, 400);
   }

   @CompilerDirectives.TruffleBoundary
   public static int yearFromTime(long t) {
      long daysAfter1970 = Math.floorDiv(t, 86400000);

      assert JSRuntime.longIsRepresentableAsInt(daysAfter1970);

      return yearFromDays((int)daysAfter1970);
   }

   public static int yearFromDays(int daysAfter1970) {
      int daysAfter2000 = daysAfter1970 - 10957;
      int days = daysAfter2000 + 102267900;

      assert days > 0;

      int year = 400 * (days / 146097);
      int remainingDays = days % 146097;
      remainingDays--;
      year += 100 * (remainingDays / 36524);
      remainingDays %= 36524;
      remainingDays++;
      year += 4 * (remainingDays / 1461);
      remainingDays %= 1461;
      remainingDays--;
      year += remainingDays / 365;
      return year - 280000 + 2000;
   }

   private static boolean isLeapYear(int year) {
      if (year % 4 != 0) {
         return false;
      } else {
         return year % 100 != 0 ? true : year % 400 == 0;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static int monthFromTime(double dt) {
      assert JSRuntime.doubleIsRepresentableAsLong(dt);

      long t = (long)dt;
      int year = yearFromTime(t);
      boolean leapYear = isLeapYear(year);
      int day = dayWithinYear(t, year);
      return monthFromTimeIntl(leapYear, day);
   }

   private static int monthFromTimeIntl(boolean leapYear, int day) {
      assert 0 <= day && day < 365 + (leapYear ? 1 : 0) : "should not reach here";

      if (day < 31) {
         return 0;
      } else if (!leapYear) {
         if (day < 59) {
            return 1;
         } else if (day < 90) {
            return 2;
         } else if (day < 120) {
            return 3;
         } else if (day < 151) {
            return 4;
         } else if (day < 181) {
            return 5;
         } else if (day < 212) {
            return 6;
         } else if (day < 243) {
            return 7;
         } else if (day < 273) {
            return 8;
         } else if (day < 304) {
            return 9;
         } else {
            return day < 334 ? 10 : 11;
         }
      } else if (day < 60) {
         return 1;
      } else if (day < 91) {
         return 2;
      } else if (day < 121) {
         return 3;
      } else if (day < 152) {
         return 4;
      } else if (day < 182) {
         return 5;
      } else if (day < 213) {
         return 6;
      } else if (day < 244) {
         return 7;
      } else if (day < 274) {
         return 8;
      } else if (day < 305) {
         return 9;
      } else {
         return day < 335 ? 10 : 11;
      }
   }

   private static int dayWithinYear(long t, int year) {
      return (int)Math.floorDiv(t, 86400000) - dayFromYear(year);
   }

   @CompilerDirectives.TruffleBoundary
   public static int dateFromTime(double dt) {
      assert JSRuntime.doubleIsRepresentableAsLong(dt);

      long t = (long)dt;
      int year = yearFromTime(t);
      int day = dayWithinYear(t, year);
      return dateFromDayInYear(year, day);
   }

   public static int dateFromDayInYear(int year, int day) {
      if (day < 31) {
         return day + 1;
      } else {
         boolean leapYear = isLeapYear(year);
         int dayMinusLeap = day - (leapYear ? 1 : 0);
         switch (monthFromTimeIntl(leapYear, day)) {
            case 1:
               return day - 30;
            case 2:
               return dayMinusLeap - 58;
            case 3:
               return dayMinusLeap - 89;
            case 4:
               return dayMinusLeap - 119;
            case 5:
               return dayMinusLeap - 150;
            case 6:
               return dayMinusLeap - 180;
            case 7:
               return dayMinusLeap - 211;
            case 8:
               return dayMinusLeap - 242;
            case 9:
               return dayMinusLeap - 272;
            case 10:
               return dayMinusLeap - 303;
            case 11:
               return dayMinusLeap - 333;
            default:
               assert false : "should not reach here";

               return -1;
         }
      }
   }

   public static double weekDay(double t) {
      int result = ((int)day(t) + 4) % 7;
      return result >= 0 ? result : result + 7;
   }

   public static double localTime(double t, Node node) {
      return t + localTZA(t, true, node);
   }

   private static double utc(double t, Node node) {
      return t - localTZA(t, false, node);
   }

   public static long localTZA(double t, boolean isUTC, Node node) {
      return localTZA(t, isUTC, JSRealm.get(node).getLocalTimeZone());
   }

   private static int getOffset(TimeZone timeZone, long date, int[] fields) {
      Grego.timeToFields(date, fields);
      return timeZone.getOffset(1, fields[0], fields[1], fields[2], fields[3], fields[5]);
   }

   private static int getOffset(TimeZone timeZone, long t, boolean isUTC) {
      int rawOffset = timeZone.getRawOffset();
      long date = isUTC ? t + rawOffset : t;
      int[] fields = new int[6];
      int offset = getOffset(timeZone, date, fields);
      if (isUTC) {
         return offset;
      } else if (offset != rawOffset) {
         int dstOffset = offset - rawOffset;
         return getOffset(timeZone, date - dstOffset, fields);
      } else {
         int dstSavings = timeZone.getDSTSavings();
         if (dstSavings == 0) {
            dstSavings = 3600000;
         }

         offset = getOffset(timeZone, date - dstSavings, fields);
         int dstOffset = offset - rawOffset;
         if (dstOffset != 0 && dstOffset != dstSavings) {
            offset = getOffset(timeZone, date - dstOffset, fields);
         }

         return offset;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static int localTZA(double t, boolean isUTC, TimeZone timeZone) {
      return getOffset(timeZone, (long)t, isUTC);
   }

   @CompilerDirectives.TruffleBoundary
   public static int hourFromTime(double t) {
      return (int)secureNegativeModulo(floor(t / 3600000.0), 24.0);
   }

   @CompilerDirectives.TruffleBoundary
   public static int minFromTime(double t) {
      return (int)secureNegativeModulo(floor(t / 60000.0), 60.0);
   }

   @CompilerDirectives.TruffleBoundary
   public static int secFromTime(double t) {
      return (int)secureNegativeModulo(floor(t / 1000.0), 60.0);
   }

   @CompilerDirectives.TruffleBoundary
   public static int msFromTime(double t) {
      return (int)secureNegativeModulo(t, 1000.0);
   }

   private static double secureNegativeModulo(double value, double modulo) {
      double result = value % modulo;
      return result >= 0.0 ? result : result + modulo;
   }

   @CompilerDirectives.TruffleBoundary
   public static double makeTime(double hour, double min, double sec, double ms) {
      if (isFinite(hour) && isFinite(min) && isFinite(sec) && isFinite(ms)) {
         double h = JSRuntime.truncateDouble(hour);
         double m = JSRuntime.truncateDouble(min);
         double s = JSRuntime.truncateDouble(sec);
         double milli = JSRuntime.truncateDouble(ms);
         return h * 3600000.0 + m * 60000.0 + s * 1000.0 + milli;
      } else {
         return Double.NaN;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static double makeDay(double year, double month, double date) {
      if (isFinite(year) && isFinite(month) && isFinite(date)) {
         double y = JSRuntime.truncateDouble(year);
         double m = JSRuntime.truncateDouble(month);
         double dt = JSRuntime.truncateDouble(date);
         double ym = y + floor(m / 12.0);
         int mn = (int)(m % 12.0);
         if (mn < 0) {
            mn += 12;
         }

         if (!(ym < -300000.0) && !(ym > 300000.0)) {
            double t = LocalDate.of((int)ym, mn + 1, 1).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
            return day(t) + dt - 1.0;
         } else {
            return Double.NaN;
         }
      } else {
         return Double.NaN;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static double makeDate(double day, double time) {
      return isFinite(day) && isFinite(time) ? day * 8.64E7 + time : Double.NaN;
   }

   @CompilerDirectives.TruffleBoundary
   public static double makeDate(double y, double m, double d, double h, double min, double sec, double ms, Integer timezone) {
      double day = makeDay(y, m, d);
      double time = makeTime(h, min, sec, ms);
      double date = makeDate(day, time);
      if (timezone == null) {
         date = utc(date, null);
      } else {
         date -= timezone * 60000;
      }

      return timeClip(date);
   }

   public static double timeClip(double time) {
      return !Double.isInfinite(time) && !Double.isNaN(time) && !(Math.abs(time) > 8.64E15) ? Double.valueOf(time).longValue() : Double.NaN;
   }

   private static boolean isFinite(double d) {
      return !Double.isNaN(d) && !Double.isInfinite(d);
   }

   private static double floor(double d) {
      return Math.floor(d);
   }

   public static JSDateObject create(JSContext context, JSRealm realm, double timeMillis) {
      JSObjectFactory factory = context.getDateFactory();
      JSDateObject obj = JSDateObject.create(factory.getShape(realm), timeMillis);
      factory.initProto(obj, realm);
      return context.trackAllocation(obj);
   }

   public static double setTime(JSDateObject thisDate, double time) {
      double v = timeClip(time);
      setTimeMillisField(thisDate, v);
      return v;
   }

   public static double setMilliseconds(JSDateObject thisDate, double ms, boolean isUTC, Node node) {
      double t = localTime(getTimeMillisField(thisDate), isUTC, node);
      double time = makeTime(hourFromTime(t), minFromTime(t), secFromTime(t), ms);
      double u = timeClip(utc(makeDate(day(t), time), isUTC, node));
      setTimeMillisField(thisDate, u);
      return u;
   }

   public static double setSeconds(JSDateObject thisDate, double s, double ms, boolean msSpecified, boolean isUTC, Node node) {
      double t = localTime(getTimeMillisField(thisDate), isUTC, node);
      double milli = msSpecified ? ms : msFromTime(t);
      double date = makeDate(day(t), makeTime(hourFromTime(t), minFromTime(t), s, milli));
      double u = timeClip(utc(date, isUTC, node));
      setTimeMillisField(thisDate, u);
      return u;
   }

   public static double setMinutes(JSDateObject thisDate, double m, double s, boolean sSpecified, double ms, boolean msSpecified, boolean isUTC, Node node) {
      double t = localTime(getTimeMillisField(thisDate), isUTC, node);
      double milli = msSpecified ? ms : msFromTime(t);
      double sec = sSpecified ? s : secFromTime(t);
      double date = makeDate(day(t), makeTime(hourFromTime(t), m, sec, milli));
      double u = timeClip(utc(date, isUTC, node));
      setTimeMillisField(thisDate, u);
      return u;
   }

   public static double setHours(
      JSDateObject thisDate, double h, double m, boolean mSpecified, double s, boolean sSpecified, double ms, boolean msSpecified, boolean isUTC, Node node
   ) {
      double t = localTime(getTimeMillisField(thisDate), isUTC, node);
      double milli = msSpecified ? ms : msFromTime(t);
      double sec = sSpecified ? s : secFromTime(t);
      double min = mSpecified ? m : minFromTime(t);
      double date = makeDate(day(t), makeTime(h, min, sec, milli));
      double u = timeClip(utc(date, isUTC, node));
      setTimeMillisField(thisDate, u);
      return u;
   }

   public static double setDate(JSDateObject thisDate, double date, boolean isUTC, Node node) {
      double t = localTime(getTimeMillisField(thisDate), isUTC, node);
      double u;
      if (Double.isNaN(t)) {
         u = Double.NaN;
      } else {
         double newDate = makeDate(makeDay(yearFromTime((long)t), monthFromTime(t), date), timeWithinDay(t));
         u = timeClip(utc(newDate, isUTC, node));
      }

      setTimeMillisField(thisDate, u);
      return u;
   }

   public static double setMonth(JSDateObject thisDate, double month, double date, boolean dateSpecified, boolean isUTC, Node node) {
      double t = localTime(getTimeMillisField(thisDate), isUTC, node);
      double newDate;
      if (Double.isNaN(t)) {
         newDate = Double.NaN;
      } else {
         double dt = dateSpecified ? date : dateFromTime(t);
         newDate = timeClip(utc(makeDate(makeDay(yearFromTime((long)t), month, dt), timeWithinDay(t)), isUTC, node));
      }

      setTimeMillisField(thisDate, newDate);
      return newDate;
   }

   public static double setFullYear(
      JSDateObject thisDate, double year, double month, boolean monthSpecified, double date, boolean dateSpecified, boolean isUTC, Node node
   ) {
      double timeFieldValue = getTimeMillisField(thisDate);
      double t = Double.isNaN(timeFieldValue) ? 0.0 : localTime(timeFieldValue, isUTC, node);
      double dt = dateSpecified ? date : dateFromTime(t);
      double m = monthSpecified ? month : monthFromTime(t);
      double newDate = makeDate(makeDay(year, m, dt), timeWithinDay(t));
      double u = timeClip(utc(newDate, isUTC, node));
      setTimeMillisField(thisDate, u);
      return u;
   }

   public static double setYear(JSDateObject thisDate, double year, Node node) {
      double t = getTimeMillisField(thisDate);
      t = Double.isNaN(t) ? 0.0 : localTime(t, node);
      if (Double.isNaN(year)) {
         setTimeMillisField(thisDate, Double.NaN);
         return Double.NaN;
      } else {
         double fullYear = toFullYear(year);
         double r5 = makeDay(fullYear, monthFromTime(t), dateFromTime(t));
         double r6 = timeClip(utc(makeDate(r5, timeWithinDay(t)), node));
         setTimeMillisField(thisDate, r6);
         return r6;
      }
   }

   private static double toFullYear(double year) {
      return -1.0 < year && year < 100.0 ? 1900 + (int)year : year;
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString format(DateFormat format, double time) {
      return Strings.fromJavaString(format.format(time));
   }

   public static TruffleString toString(double time, JSRealm realm) {
      return Double.isNaN(time) ? INVALID_DATE_STRING : format(realm.getDateToStringFormat(), time);
   }

   public static TruffleString toISOStringIntl(double time, JSRealm realm) {
      return format(realm.getJSDateISOFormat(time), time);
   }

   public static boolean isTimeValid(double time) {
      return !Double.isNaN(time) && !Double.isInfinite(time);
   }

   private static double localTime(double time, boolean isUTC, Node node) {
      return isUTC ? time : localTime(time, node);
   }

   private static double utc(double time, boolean isUTC, Node node) {
      return isUTC ? time : utc(time, node);
   }

   public static boolean isValidDate(JSDateObject date) {
      return !Double.isNaN(getTimeMillisField(date));
   }

   @CompilerDirectives.TruffleBoundary
   public static Instant asInstant(JSDateObject date) {
      assert isValidDate(date);

      return Instant.ofEpochMilli((long)getTimeMillisField(date));
   }

   @CompilerDirectives.TruffleBoundary
   public static LocalDate asLocalDate(JSDateObject date, JSRealm realm) {
      return LocalDate.from(asInstant(date).atZone(realm.getLocalTimeZoneId()));
   }

   @CompilerDirectives.TruffleBoundary
   public static LocalTime asLocalTime(JSDateObject date, JSRealm realm) {
      return LocalTime.from(asInstant(date).atZone(realm.getLocalTimeZoneId()));
   }

   public static double getDateValueFromInstant(Object receiver, InteropLibrary interop) {
      Instant instant;
      try {
         instant = interop.asInstant(receiver);
      } catch (UnsupportedMessageException var5) {
         throw Errors.createTypeErrorInteropException(receiver, var5, "asInstant", null);
      }

      try {
         return instant.toEpochMilli();
      } catch (ArithmeticException var4) {
         return Double.NaN;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      double time = getTimeMillisField((JSDateObject)obj);
      TruffleString formattedDate;
      if (isTimeValid(time)) {
         formattedDate = toISOStringIntl(time, JSRealm.get(null));
      } else {
         formattedDate = INVALID_DATE_STRING;
      }

      return JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()
         ? Strings.concatAll(Strings.BRACKET_DATE_SPC, formattedDate, Strings.BRACKET_CLOSE)
         : formattedDate;
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getDatePrototype();
   }
}
