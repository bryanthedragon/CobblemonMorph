package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.builtins.temporal.TemporalDurationFunctionBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalDurationPrototypeBuiltins;
import com.oracle.truffle.js.nodes.cast.JSNumberToBigIntNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.Pair;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JSTemporalDuration extends JSNonProxy implements JSConstructorFactory.WithFunctionsAndSpecies, PrototypeSupplier {
   public static final JSTemporalDuration INSTANCE = new JSTemporalDuration();
   public static final TruffleString CLASS_NAME = Strings.constant("Duration");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("Duration.prototype");
   public static final TruffleString TO_STRING_TAG = Strings.constant("Temporal.Duration");

   private JSTemporalDuration() {
   }

   public static JSTemporalDurationObject createTemporalDuration(
      JSContext context,
      double years,
      double months,
      double weeks,
      double days,
      double hours,
      double minutes,
      double seconds,
      double milliseconds,
      double microseconds,
      double nanoseconds,
      BranchProfile errorBranch
   ) {
      if (!TemporalUtil.isValidDuration(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds)) {
         errorBranch.enter();
         throw TemporalErrors.createTypeErrorDurationOutsideRange();
      } else {
         return createIntl(context, years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
      }
   }

   public static JSTemporalDurationObject createTemporalDuration(
      JSContext context,
      double years,
      double months,
      double weeks,
      double days,
      double hours,
      double minutes,
      double seconds,
      double milliseconds,
      double microseconds,
      double nanoseconds
   ) {
      if (!TemporalUtil.isValidDuration(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds)) {
         throw TemporalErrors.createTypeErrorDurationOutsideRange();
      } else {
         return createIntl(context, years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
      }
   }

   private static JSTemporalDurationObject createIntl(
      JSContext context,
      double years,
      double months,
      double weeks,
      double days,
      double hours,
      double minutes,
      double seconds,
      double milliseconds,
      double microseconds,
      double nanoseconds
   ) {
      JSRealm realm = JSRealm.get(null);
      JSObjectFactory factory = context.getTemporalDurationFactory();
      JSTemporalDurationObject obj = factory.initProto(
         new JSTemporalDurationObject(
            factory.getShape(realm),
            nnz(years),
            nnz(months),
            nnz(weeks),
            nnz(days),
            nnz(hours),
            nnz(minutes),
            nnz(seconds),
            nnz(milliseconds),
            nnz(microseconds),
            nnz(nanoseconds)
         ),
         realm
      );
      return context.trackAllocation(obj);
   }

   private static double nnz(double d) {
      return JSRuntime.isNegativeZero(d) ? 0.0 : d;
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return TO_STRING_TAG;
   }

   @Override
   public TruffleString getClassName() {
      return CLASS_NAME;
   }

   @Override
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject constructor) {
      JSContext ctx = realm.getContext();
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(ctx, prototype, constructor);
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.YEARS, realm.lookupAccessor(TemporalDurationPrototypeBuiltins.BUILTINS, TemporalConstants.YEARS)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.MONTHS, realm.lookupAccessor(TemporalDurationPrototypeBuiltins.BUILTINS, TemporalConstants.MONTHS)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.WEEKS, realm.lookupAccessor(TemporalDurationPrototypeBuiltins.BUILTINS, TemporalConstants.WEEKS)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.DAYS, realm.lookupAccessor(TemporalDurationPrototypeBuiltins.BUILTINS, TemporalConstants.DAYS)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.HOURS, realm.lookupAccessor(TemporalDurationPrototypeBuiltins.BUILTINS, TemporalConstants.HOURS)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.MINUTES, realm.lookupAccessor(TemporalDurationPrototypeBuiltins.BUILTINS, TemporalConstants.MINUTES)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.SECONDS, realm.lookupAccessor(TemporalDurationPrototypeBuiltins.BUILTINS, TemporalConstants.SECONDS)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.MILLISECONDS, realm.lookupAccessor(TemporalDurationPrototypeBuiltins.BUILTINS, TemporalConstants.MILLISECONDS)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.MICROSECONDS, realm.lookupAccessor(TemporalDurationPrototypeBuiltins.BUILTINS, TemporalConstants.MICROSECONDS)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.NANOSECONDS, realm.lookupAccessor(TemporalDurationPrototypeBuiltins.BUILTINS, TemporalConstants.NANOSECONDS)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.SIGN, realm.lookupAccessor(TemporalDurationPrototypeBuiltins.BUILTINS, TemporalConstants.SIGN)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.BLANK, realm.lookupAccessor(TemporalDurationPrototypeBuiltins.BUILTINS, TemporalConstants.BLANK)
      );
      JSObjectUtil.putFunctionsFromContainer(realm, prototype, TemporalDurationPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(prototype, TO_STRING_TAG);
      return prototype;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getTemporalDurationPrototype();
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, TemporalDurationFunctionBuiltins.BUILTINS);
   }

   public static boolean isJSTemporalDuration(Object obj) {
      return obj instanceof JSTemporalDurationObject;
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDurationRecord parseTemporalDurationString(TruffleString string) {
      double hoursMV = 0.0;
      TruffleString minutes = Strings.EMPTY_STRING;
      TruffleString seconds = Strings.EMPTY_STRING;
      TruffleString fHours = Strings.EMPTY_STRING;
      TruffleString fMinutes = Strings.EMPTY_STRING;
      TruffleString fSeconds = Strings.EMPTY_STRING;
      Pattern regex = Pattern.compile("^([\\+−-]?)[Pp](\\d+[Yy])?(\\d+[Mm])?(\\d+[Ww])?(\\d+[Dd])?([Tt]([\\d.,]+[Hh])?([\\d.,]+[Mm])?([\\d.,]+[Ss])?)?$");
      Matcher matcher = regex.matcher(Strings.toJavaString(string));
      if (!matcher.matches()) {
         throw TemporalErrors.createRangeErrorTemporalMalformedDuration();
      } else if (matcher.start(2) < 0
         && matcher.start(3) < 0
         && matcher.start(4) < 0
         && matcher.start(5) < 0
         && matcher.start(7) < 0
         && matcher.start(8) < 0
         && matcher.start(9) < 0) {
         throw TemporalErrors.createRangeErrorTemporalMalformedDuration();
      } else {
         TruffleString sign = group(string, matcher, 1);
         long yearsMV = parseDurationIntl(string, matcher, 2);
         long monthsMV = parseDurationIntl(string, matcher, 3);
         long weeksMV = parseDurationIntl(string, matcher, 4);
         long daysMV = parseDurationIntl(string, matcher, 5);
         TruffleString timeGroup = group(string, matcher, 6);
         if (timeGroup != null && Strings.length(timeGroup) > 0) {
            Pair<TruffleString, TruffleString> hoursPair = parseDurationIntlWithFraction(string, matcher, 7);
            Pair<TruffleString, TruffleString> minutesPair = parseDurationIntlWithFraction(string, matcher, 8);
            Pair<TruffleString, TruffleString> secondsPair = parseDurationIntlWithFraction(string, matcher, 9);
            hoursMV = TemporalUtil.toIntegerOrInfinity(hoursPair.getFirst()).doubleValue();
            fHours = hoursPair.getSecond();
            minutes = minutesPair.getFirst();
            fMinutes = minutesPair.getSecond();
            seconds = secondsPair.getFirst();
            fSeconds = secondsPair.getSecond();
         }

         BigDecimal minutesMV;
         if (!empty(fHours)) {
            if (!empty(minutes) || !empty(fMinutes) || !empty(seconds) || !empty(fSeconds)) {
               throw TemporalErrors.createRangeErrorTemporalMalformedDuration();
            }

            assert !Strings.contains(fHours, '.');

            int fHoursScale = Strings.length(fHours);
            minutesMV = new BigDecimal(TemporalUtil.toIntegerOrInfinity(fHours).doubleValue()).multiply(TemporalUtil.BD_60).scaleByPowerOfTen(-fHoursScale);
         } else {
            minutesMV = new BigDecimal(TemporalUtil.toIntegerOrInfinity(minutes).doubleValue());
         }

         BigDecimal secondsMV;
         if (!empty(fMinutes)) {
            if (!empty(seconds) || !empty(fSeconds)) {
               throw TemporalErrors.createRangeErrorTemporalMalformedDuration();
            }

            assert !Strings.contains(fMinutes, '.');

            int fMinutesScale = Strings.length(fMinutes);
            secondsMV = new BigDecimal(TemporalUtil.toIntegerOrInfinity(fMinutes).doubleValue()).multiply(TemporalUtil.BD_60).scaleByPowerOfTen(-fMinutesScale);
         } else if (!empty(seconds)) {
            secondsMV = new BigDecimal(TemporalUtil.toIntegerOrInfinity(seconds).doubleValue());
         } else {
            secondsMV = minutesMV.remainder(BigDecimal.ONE, TemporalUtil.mc_20_floor).multiply(TemporalUtil.BD_60);
         }

         BigDecimal millisecondsMV;
         if (!empty(fSeconds)) {
            assert !Strings.contains(fSeconds, '.');

            int fSecondsScale = Strings.length(fSeconds);
            millisecondsMV = TemporalUtil.BD_1000
               .multiply(BigDecimal.valueOf(TemporalUtil.toIntegerOrInfinity(fSeconds).longValue()))
               .divide(TemporalUtil.BD_10.pow(fSecondsScale));
         } else {
            millisecondsMV = secondsMV.remainder(BigDecimal.ONE, TemporalUtil.mc_20_floor).multiply(TemporalUtil.BD_1000, TemporalUtil.mc_20_floor);
         }

         BigDecimal microsecondsMV = millisecondsMV.remainder(BigDecimal.ONE, TemporalUtil.mc_20_floor).multiply(TemporalUtil.BD_1000);
         BigDecimal nanosecondsMV = microsecondsMV.remainder(BigDecimal.ONE, TemporalUtil.mc_20_floor).multiply(TemporalUtil.BD_1000);
         int factor = !sign.equals(Strings.SYMBOL_MINUS) && !sign.equals(Strings.UNICODE_MINUS_SIGN) ? 1 : -1;
         return JSTemporalDurationRecord.createWeeks(
            yearsMV * factor,
            monthsMV * factor,
            weeksMV * factor,
            daysMV * factor,
            hoursMV * factor,
            minutesMV.longValue() * factor,
            secondsMV.longValue() * factor,
            millisecondsMV.longValue() * factor,
            microsecondsMV.longValue() * factor,
            nanosecondsMV.longValue() * factor
         );
      }
   }

   private static TruffleString group(TruffleString string, Matcher matcher, int groupNumber) {
      int start = matcher.start(groupNumber);
      return start < 0 ? null : Strings.lazySubstring(string, start, matcher.end(groupNumber) - start);
   }

   private static boolean empty(TruffleString s) {
      assert s != null;

      return s.isEmpty();
   }

   private static long parseDurationIntl(TruffleString string, Matcher matcher, int i) {
      int start = matcher.start(i);
      if (start >= 0) {
         TruffleString numstr = Strings.lazySubstring(string, start, matcher.end(i) - (start + 1));

         try {
            return TemporalUtil.toIntegerOrInfinity(numstr).longValue();
         } catch (NumberFormatException var6) {
            throw Errors.createRangeError("decimal numbers only allowed in time units");
         }
      } else {
         return 0L;
      }
   }

   private static Pair<TruffleString, TruffleString> parseDurationIntlWithFraction(TruffleString string, Matcher matcher, int i) {
      int start = matcher.start(i);
      if (start >= 0) {
         TruffleString numstr = Strings.lazySubstring(string, start, matcher.end(i) - (start + 1));
         int idx = findDecimalSeparator(numstr, 0);
         if (idx >= 0) {
            TruffleString wholePart = Strings.lazySubstring(numstr, 0, idx);
            TruffleString fractionalPart = Strings.lazySubstring(numstr, idx + 1);
            if (Strings.length(fractionalPart) > 9) {
               throw TemporalErrors.createRangeErrorTemporalMalformedDuration();
            } else {
               return new Pair<>(wholePart, fractionalPart);
            }
         } else {
            return new Pair<>(numstr, Strings.EMPTY_STRING);
         }
      } else {
         return new Pair<>(Strings.EMPTY_STRING, Strings.EMPTY_STRING);
      }
   }

   private static int findDecimalSeparator(TruffleString str, int startPos) {
      int idxDot = Strings.indexOf(str, 46, startPos);
      int idxComma = Strings.indexOf(str, 44, startPos);
      if (idxDot >= 0) {
         if (idxComma >= 0) {
            throw TemporalErrors.createRangeErrorTemporalMalformedDuration();
         } else if (Strings.indexOf(str, 46, idxDot + 1) >= 0) {
            throw TemporalErrors.createRangeErrorTemporalMalformedDuration();
         } else {
            return idxDot;
         }
      } else if (Strings.indexOf(str, 44, idxComma + 1) >= 0) {
         throw TemporalErrors.createRangeErrorTemporalMalformedDuration();
      } else {
         return idxComma;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDurationRecord toTemporalDurationRecord(JSDynamicObject temporalDurationLike) {
      if (isJSTemporalDuration(temporalDurationLike)) {
         JSTemporalDurationObject d = (JSTemporalDurationObject)temporalDurationLike;
         return JSTemporalDurationRecord.createWeeks(
            d.getYears(),
            d.getMonths(),
            d.getWeeks(),
            d.getDays(),
            d.getHours(),
            d.getMinutes(),
            d.getSeconds(),
            d.getMilliseconds(),
            d.getMicroseconds(),
            d.getNanoseconds()
         );
      } else {
         boolean any = false;
         double year = 0.0;
         double month = 0.0;
         double week = 0.0;
         double day = 0.0;
         double hour = 0.0;
         double minute = 0.0;
         double second = 0.0;
         double millis = 0.0;
         double micros = 0.0;
         double nanos = 0.0;

         for (TemporalUtil.UnitPlural unit : TemporalUtil.DURATION_PROPERTIES) {
            Object val = JSObject.get(temporalDurationLike, unit.toTruffleString());
            double lVal = 0.0;
            if (val == Undefined.instance) {
               lVal = 0.0;
            } else {
               any = true;
               lVal = TemporalUtil.toIntegerWithoutRounding(val);
            }

            switch (unit) {
               case YEARS:
                  year = lVal;
                  break;
               case MONTHS:
                  month = lVal;
                  break;
               case WEEKS:
                  week = lVal;
                  break;
               case DAYS:
                  day = lVal;
                  break;
               case HOURS:
                  hour = lVal;
                  break;
               case MINUTES:
                  minute = lVal;
                  break;
               case SECONDS:
                  second = lVal;
                  break;
               case MILLISECONDS:
                  millis = lVal;
                  break;
               case MICROSECONDS:
                  micros = lVal;
                  break;
               case NANOSECONDS:
                  nanos = lVal;
                  break;
               default:
                  throw Errors.unsupported("wrong type");
            }
         }

         if (!any) {
            throw Errors.createTypeError("Given duration like object has no duration properties.");
         } else if (!TemporalUtil.isValidDuration(year, month, week, day, hour, minute, second, millis, micros, nanos)) {
            throw TemporalErrors.createTypeErrorDurationOutsideRange();
         } else {
            return TemporalUtil.createDurationRecord(year, month, week, day, hour, minute, second, millis, micros, nanos);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString temporalDurationToString(
      double yearsP,
      double monthsP,
      double weeksP,
      double daysP,
      double hoursP,
      double minutesP,
      double secondsP,
      double millisecondsP,
      double microsecondsP,
      double nanosecondsP,
      Object precision,
      JSNumberToBigIntNode toBigIntNode
   ) {
      int sign = TemporalUtil.durationSign(yearsP, monthsP, weeksP, daysP, hoursP, minutesP, secondsP, millisecondsP, microsecondsP, nanosecondsP);
      BigInteger nanosecondsBI = toBigInteger(nanosecondsP, toBigIntNode);
      BigInteger microsecondsBI = toBigInteger(microsecondsP, toBigIntNode);
      BigInteger millisecondsBI = toBigInteger(millisecondsP, toBigIntNode);
      BigInteger secondsBI = toBigInteger(secondsP, toBigIntNode);
      BigInteger yearsBI = toBigIntegerOrNull(Math.abs(yearsP), toBigIntNode);
      BigInteger monthsBI = toBigIntegerOrNull(Math.abs(monthsP), toBigIntNode);
      BigInteger weeksBI = toBigIntegerOrNull(Math.abs(weeksP), toBigIntNode);
      BigInteger daysBI = toBigIntegerOrNull(Math.abs(daysP), toBigIntNode);
      BigInteger hoursBI = toBigIntegerOrNull(Math.abs(hoursP), toBigIntNode);
      BigInteger minutesBI = toBigIntegerOrNull(Math.abs(minutesP), toBigIntNode);
      boolean condition = secondsP != 0.0
         || millisecondsP != 0.0
         || microsecondsP != 0.0
         || nanosecondsP != 0.0
         || yearsP == 0.0 && monthsP == 0.0 && weeksP == 0.0 && daysP == 0.0 && hoursP == 0.0 && minutesP == 0.0;
      return temporalDurationToStringIntl(
         yearsBI, monthsBI, weeksBI, daysBI, hoursBI, minutesBI, secondsBI, millisecondsBI, microsecondsBI, nanosecondsBI, precision, sign, condition
      );
   }

   @CompilerDirectives.TruffleBoundary
   private static TruffleString temporalDurationToStringIntl(
      BigInteger yearsP,
      BigInteger monthsP,
      BigInteger weeksP,
      BigInteger daysP,
      BigInteger hoursP,
      BigInteger minutesP,
      BigInteger secondsP,
      BigInteger millisecondsP,
      BigInteger microsecondsP,
      BigInteger nanosecondsP,
      Object precision,
      int sign,
      boolean condition
   ) {
      BigInteger[] res = nanosecondsP.divideAndRemainder(TemporalUtil.BI_1000);
      BigInteger microseconds = microsecondsP.add(res[0]);
      BigInteger nanoseconds = res[1];
      res = microseconds.divideAndRemainder(TemporalUtil.BI_1000);
      BigInteger milliseconds = millisecondsP.add(res[0]);
      microseconds = res[1];
      res = milliseconds.divideAndRemainder(TemporalUtil.BI_1000);
      BigInteger seconds = secondsP.add(res[0]);
      milliseconds = res[1];
      StringBuilder datePart = new StringBuilder();
      if (yearsP != null) {
         datePart.append(yearsP.toString());
         datePart.append("Y");
      }

      if (monthsP != null) {
         datePart.append(monthsP.toString());
         datePart.append("M");
      }

      if (weeksP != null) {
         datePart.append(weeksP.toString());
         datePart.append("W");
      }

      if (daysP != null) {
         datePart.append(daysP.toString());
         datePart.append("D");
      }

      StringBuilder timePart = new StringBuilder();
      if (hoursP != null) {
         timePart.append(hoursP.toString());
         timePart.append("H");
      }

      if (minutesP != null) {
         timePart.append(minutesP.toString());
         timePart.append("M");
      }

      if (condition || !TemporalConstants.AUTO.equals(precision)) {
         long fraction = Math.abs(TemporalUtil.bitoi(milliseconds)) * 1000000L
            + Math.abs(TemporalUtil.bitoi(microseconds)) * 1000L
            + Math.abs(TemporalUtil.bitoi(nanoseconds));
         TruffleString decimalPart = Strings.format("000000000%1$09d", fraction);
         decimalPart = Strings.lazySubstring(decimalPart, Strings.length(decimalPart) - 9);
         if (!TemporalConstants.AUTO.equals(precision)) {
            if (precision instanceof Number && ((Number)precision).doubleValue() == 0.0) {
               decimalPart = Strings.EMPTY_STRING;
            } else {
               Number n = (Number)precision;
               decimalPart = Strings.lazySubstring(decimalPart, 0, Math.min(Strings.length(decimalPart), n.intValue()));
            }
         } else {
            int pos = Strings.length(decimalPart) - 1;

            while (pos >= 0 && Strings.charAt(decimalPart, pos) == '0') {
               pos--;
            }

            if (pos != Strings.length(decimalPart) - 1) {
               decimalPart = Strings.lazySubstring(decimalPart, 0, pos + 1);
            }
         }

         TruffleString secondsPart = Strings.fromJavaString(seconds.abs().toString());
         if (!decimalPart.equals(Strings.EMPTY_STRING)) {
            secondsPart = Strings.concatAll(secondsPart, Strings.DOT, decimalPart);
         }

         timePart.append(secondsPart);
         timePart.append("S");
      }

      TruffleString signPart = sign < 0 ? Strings.SYMBOL_MINUS : Strings.EMPTY_STRING;
      TruffleStringBuilder result = Strings.builderCreate();
      Strings.builderAppend(result, signPart);
      Strings.builderAppend(result, "P");
      Strings.builderAppend(result, datePart.toString());
      if (timePart.length() > 0) {
         Strings.builderAppend(result, "T");
         Strings.builderAppend(result, timePart.toString());
      }

      return Strings.builderToString(result);
   }

   private static BigInteger toBigIntegerOrNull(double value, JSNumberToBigIntNode toBigIntNode) {
      return value != 0.0 ? toBigIntNode.executeBigInt(value).bigIntegerValue() : null;
   }

   private static BigInteger toBigInteger(double value, JSNumberToBigIntNode toBigIntNode) {
      return toBigIntNode.executeBigInt(value).bigIntegerValue();
   }
}
