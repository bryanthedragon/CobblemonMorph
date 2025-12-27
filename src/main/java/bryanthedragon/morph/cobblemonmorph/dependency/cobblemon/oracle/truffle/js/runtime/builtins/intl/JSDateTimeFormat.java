package com.oracle.truffle.js.runtime.builtins.intl;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.ConstrainedFieldPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.DateFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.DateIntervalFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.DateTimePatternGenerator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.NumberingSystem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.SimpleDateFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.Calendar;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.GregorianCalendar;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.TimeZone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.ULocale;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.DateTimeFormatFunctionBuiltins;
import com.oracle.truffle.js.builtins.intl.DateTimeFormatPrototypeBuiltins;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSDate;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import com.oracle.truffle.js.runtime.util.LazyValue;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.Format.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Locale.Builder;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.UnmodifiableEconomicMap;

public final class JSDateTimeFormat extends JSNonProxy implements JSConstructorFactory.WithFunctions, PrototypeSupplier {
   public static final TruffleString CLASS_NAME = Strings.constant("DateTimeFormat");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("DateTimeFormat.prototype");
   public static final TruffleString TO_STRING_TAG = Strings.constant("Intl.DateTimeFormat");
   public static final TruffleString GET_FORMAT_NAME = Strings.constant("get format");
   static final HiddenKey BOUND_OBJECT_KEY = new HiddenKey(Strings.toJavaString(CLASS_NAME));
   public static final JSDateTimeFormat INSTANCE = new JSDateTimeFormat();
   private static final LazyValue<UnmodifiableEconomicMap<String, String>> canonicalTimeZoneIDMap = new LazyValue<>(
      JSDateTimeFormat::initCanonicalTimeZoneIDMap
   );
   private static final LazyValue<UnmodifiableEconomicMap<DateFormat.Field, String>> fieldToTypeMap = new LazyValue<>(
      JSDateTimeFormat::initializeFieldToTypeMap
   );

   private JSDateTimeFormat() {
   }

   public static boolean isJSDateTimeFormat(Object obj) {
      return obj instanceof JSDateTimeFormatObject;
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
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
      JSContext ctx = realm.getContext();
      JSObject numberFormatPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(ctx, numberFormatPrototype, ctor);
      JSObjectUtil.putFunctionsFromContainer(realm, numberFormatPrototype, DateTimeFormatPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putBuiltinAccessorProperty(numberFormatPrototype, Strings.FORMAT, createFormatFunctionGetter(realm, ctx), Undefined.instance);
      JSObjectUtil.putToStringTag(numberFormatPrototype, TO_STRING_TAG);
      return numberFormatPrototype;
   }

   @Override
   public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, DateTimeFormatFunctionBuiltins.BUILTINS);
   }

   public static JSDateTimeFormatObject create(JSContext context, JSRealm realm) {
      JSDateTimeFormat.InternalState state = new JSDateTimeFormat.InternalState();
      JSObjectFactory factory = context.getDateTimeFormatFactory();
      JSDateTimeFormatObject obj = new JSDateTimeFormatObject(factory.getShape(realm), state);
      factory.initProto(obj, realm);
      return context.trackAllocation(obj);
   }

   @CompilerDirectives.TruffleBoundary
   public static void setupInternalDateTimeFormat(
      JSContext ctx,
      JSDateTimeFormat.InternalState state,
      String[] locales,
      String weekdayOpt,
      String eraOpt,
      String yearOpt,
      String monthOpt,
      String dayOpt,
      String dayPeriodOpt,
      String hourOpt,
      String hcOpt,
      Boolean hour12Opt,
      String minuteOpt,
      String secondOpt,
      int fractionalSecondDigitsOpt,
      String tzNameOpt,
      TimeZone timeZone,
      String calendarOpt,
      String numberingSystemOpt,
      String dateStyleOpt,
      String timeStyleOpt
   ) {
      Locale selectedLocale = IntlUtil.selectedLocale(ctx, locales);
      Locale strippedLocale = selectedLocale.stripExtensions();
      Builder builder = new Builder();
      builder.setLocale(strippedLocale);
      String hc;
      if (hour12Opt == null) {
         String hcType = selectedLocale.getUnicodeLocaleType("hc");
         if (hcType != null && (hcOpt == null || hcOpt.equals(hcType)) && isValidHCType(hcType)) {
            hc = hcType;
            builder.setUnicodeLocaleKeyword("hc", hcType);
         } else {
            hc = hcOpt;
         }
      } else {
         hc = null;
      }

      String caType = IntlUtil.normalizeCAType(selectedLocale.getUnicodeLocaleType("ca"));
      String normCalendarOpt = IntlUtil.normalizeCAType(calendarOpt);
      if (caType != null && (normCalendarOpt == null || normCalendarOpt.equals(caType)) && isValidCAType(strippedLocale, caType)) {
         state.calendar = caType;
         builder.setUnicodeLocaleKeyword("ca", caType);
      }

      String nuType = selectedLocale.getUnicodeLocaleType("nu");
      if (nuType != null && IntlUtil.isValidNumberingSystem(nuType) && (numberingSystemOpt == null || numberingSystemOpt.equals(nuType))) {
         state.numberingSystem = nuType;
         builder.setUnicodeLocaleKeyword("nu", nuType);
      }

      state.locale = builder.build().toLanguageTag();
      if (normCalendarOpt != null && isValidCAType(strippedLocale, normCalendarOpt)) {
         state.calendar = normCalendarOpt;
         builder.setUnicodeLocaleKeyword("ca", normCalendarOpt);
      }

      if (numberingSystemOpt != null && IntlUtil.isValidNumberingSystem(numberingSystemOpt)) {
         state.numberingSystem = numberingSystemOpt;
         builder.setUnicodeLocaleKeyword("nu", numberingSystemOpt);
      }

      Locale javaLocale = builder.build();
      if (state.numberingSystem == null) {
         state.numberingSystem = IntlUtil.defaultNumberingSystemName(ctx, javaLocale);
      }

      state.dateStyle = dateStyleOpt;
      state.timeStyle = timeStyleOpt;
      DateTimePatternGenerator patternGenerator = DateTimePatternGenerator.getInstance(javaLocale);
      String hcDefault = IntlUtil.toJSHourCycle(patternGenerator.getDefaultHourCycle());
      if (hc == null) {
         hc = hcDefault;
      }

      if (hour12Opt != null) {
         boolean h11or23 = "h11".equals(hcDefault) || "h23".equals(hcDefault);
         if (hour12Opt) {
            hc = h11or23 ? "h11" : "h12";
         } else {
            hc = h11or23 ? "h23" : "h24";
         }
      }

      DateFormat dateFormat;
      if (timeStyleOpt == null) {
         if (dateStyleOpt == null) {
            String skeleton = makeSkeleton(
               weekdayOpt, eraOpt, yearOpt, monthOpt, dayOpt, dayPeriodOpt, hourOpt, hc, minuteOpt, secondOpt, fractionalSecondDigitsOpt, tzNameOpt
            );
            String bestPattern = patternGenerator.getBestPattern(skeleton, 2048);
            if (containsOneOf(bestPattern, "eEc")) {
               state.weekday = weekdayOpt;
            }

            if (bestPattern.contains("G")) {
               state.era = eraOpt;
            }

            if (containsOneOf(bestPattern, "YyUu")) {
               state.year = yearOpt;
            }

            if (containsOneOf(bestPattern, "ML")) {
               state.month = monthOpt;
            }

            if (containsOneOf(bestPattern, "dDFg")) {
               state.day = dayOpt;
            }

            if (containsOneOf(bestPattern, "Bb")) {
               state.dayPeriod = dayPeriodOpt;
            }

            if (containsOneOf(bestPattern, "hHKk")) {
               if (!bestPattern.contains("hh") && !bestPattern.contains("HH") && !bestPattern.contains("KK") && !bestPattern.contains("kk")) {
                  state.hour = "numeric";
               } else {
                  state.hour = "2-digit";
               }

               state.hourCycle = hc;
            }

            if (bestPattern.contains("m")) {
               state.minute = bestPattern.contains("mm") ? "2-digit" : "numeric";
            }

            if (bestPattern.contains("s")) {
               state.second = bestPattern.contains("ss") ? "2-digit" : "numeric";
            }

            if (containsOneOf(bestPattern, "SA")) {
               state.fractionalSecondDigits = fractionalSecondDigitsOpt;
            }

            dateFormat = new SimpleDateFormat(bestPattern, javaLocale);
         } else {
            dateFormat = DateFormat.getDateInstance(dateFormatStyle(dateStyleOpt), javaLocale);
         }
      } else {
         if (dateStyleOpt == null) {
            dateFormat = DateFormat.getTimeInstance(dateFormatStyle(timeStyleOpt), javaLocale);
         } else {
            dateFormat = DateFormat.getDateTimeInstance(dateFormatStyle(dateStyleOpt), dateFormatStyle(timeStyleOpt), javaLocale);
         }

         state.hourCycle = hc;
      }

      String pattern = ((SimpleDateFormat)dateFormat).toPattern();
      String skeletonx = patternGenerator.getSkeleton(pattern);
      if (!Objects.equals(state.hourCycle, hourCycleFromPattern(pattern))) {
         skeletonx = replaceHourCycle(skeletonx, hc);
         String bestPatternx = patternGenerator.getBestPattern(skeletonx, 2048);
         dateFormat = new SimpleDateFormat(replaceHourCycle(bestPatternx, hc), javaLocale);
      }

      state.dateFormat = dateFormat;
      Locale intervalFormatLocale = new Builder().setLocale(javaLocale).setUnicodeLocaleKeyword("hc", hc).build();

      try {
         state.dateIntervalFormat = DateIntervalFormat.getInstance(skeletonx, intervalFormatLocale);
      } catch (IllegalArgumentException var36) {
         state.dateIntervalFormat = DateIntervalFormat.getInstance(patchSkeletonToAvoidICU21939(skeletonx), intervalFormatLocale);
      }

      if (state.calendar == null) {
         state.calendar = IntlUtil.normalizeCAType(Calendar.getInstance(javaLocale).getType());
      }

      if ("gregory".equals(state.calendar)) {
         Calendar calendar = dateFormat.getCalendar();
         if (!(calendar instanceof GregorianCalendar)) {
            calendar = new GregorianCalendar(javaLocale);
            dateFormat.setCalendar(calendar);
         }

         ((GregorianCalendar)calendar).setGregorianChange(new Date(Long.MIN_VALUE));
      }

      if (tzNameOpt != null && !tzNameOpt.isEmpty()) {
         state.timeZoneName = tzNameOpt;
      }

      state.dateFormat.setTimeZone(timeZone);
      state.timeZone = timeZone.getID();
      state.initialized = true;
   }

   private static String patchSkeletonToAvoidICU21939(String skeleton) {
      StringBuilder sb = new StringBuilder();

      for (char c : skeleton.toCharArray()) {
         switch (c) {
            case 'B':
            case 'a':
            case 'b':
               break;
            case 'U':
            case 'Y':
            case 'r':
            case 'u':
               sb.append('y');
               break;
            default:
               sb.append(c);
         }
      }

      return sb.toString();
   }

   private static int dateFormatStyle(String style) {
      if ("full".equals(style)) {
         return 0;
      } else if ("long".equals(style)) {
         return 1;
      } else if ("medium".equals(style)) {
         return 2;
      } else {
         assert "short".equals(style);

         return 3;
      }
   }

   private static String hourCycleFromPattern(String pattern) {
      boolean quoted = false;

      for (char c : pattern.toCharArray()) {
         if (c == '\'') {
            quoted = !quoted;
         } else if (!quoted) {
            switch (c) {
               case 'H':
                  return "h23";
               case 'K':
                  return "h11";
               case 'h':
                  return "h12";
               case 'k':
                  return "h24";
            }
         }
      }

      return null;
   }

   private static String replaceHourCycle(String pattern, String hourCycle) {
      StringBuilder sb = new StringBuilder();
      char replacement;
      if ("h11".equals(hourCycle)) {
         replacement = 'K';
      } else if ("h12".equals(hourCycle)) {
         replacement = 'h';
      } else if ("h23".equals(hourCycle)) {
         replacement = 'H';
      } else {
         assert "h24".equals(hourCycle);

         replacement = 'k';
      }

      boolean quoted = false;

      for (char c : pattern.toCharArray()) {
         if (c == '\'') {
            quoted = !quoted;
         } else if (!quoted) {
            switch (c) {
               case 'H':
               case 'K':
               case 'h':
               case 'k':
                  sb.append(replacement);
                  continue;
            }
         }

         sb.append(c);
      }

      return sb.toString();
   }

   private static boolean isValidHCType(String hcType) {
      return "h11".equals(hcType) || "h12".equals(hcType) || "h23".equals(hcType) || "h24".equals(hcType);
   }

   private static boolean isValidCAType(Locale locale, String calendar) {
      assert Objects.equals(calendar, IntlUtil.normalizeCAType(calendar));

      String[] validValues = Calendar.getKeywordValuesForLocale("ca", ULocale.forLocale(locale), false);

      for (String validValue : validValues) {
         if (IntlUtil.normalizeCAType(validValue).equals(calendar)) {
            return true;
         }
      }

      return false;
   }

   private static String weekdayOptToSkeleton(String weekdayOpt) {
      if (weekdayOpt == null) {
         return "";
      } else {
         switch (weekdayOpt) {
            case "narrow":
               return "eeeee";
            case "short":
               return "eee";
            case "long":
               return "eeee";
            default:
               return "";
         }
      }
   }

   private static String eraOptToSkeleton(String eraOpt) {
      if (eraOpt == null) {
         return "";
      } else {
         switch (eraOpt) {
            case "narrow":
               return "GGGGG";
            case "short":
               return "GGG";
            case "long":
               return "GGGG";
            default:
               return "";
         }
      }
   }

   private static String yearOptToSkeleton(String yearOpt) {
      if (yearOpt == null) {
         return "";
      } else {
         switch (yearOpt) {
            case "2-digit":
               return "yy";
            case "numeric":
               return "y";
            default:
               return "";
         }
      }
   }

   private static String monthOptToSkeleton(String monthOpt) {
      if (monthOpt == null) {
         return "";
      } else {
         switch (monthOpt) {
            case "2-digit":
               return "MM";
            case "numeric":
               return "M";
            case "narrow":
               return "MMMMM";
            case "short":
               return "MMM";
            case "long":
               return "MMMM";
            default:
               return "";
         }
      }
   }

   private static String dayOptToSkeleton(String dayOpt) {
      if (dayOpt == null) {
         return "";
      } else {
         switch (dayOpt) {
            case "2-digit":
               return "dd";
            case "numeric":
               return "d";
            default:
               return "";
         }
      }
   }

   private static String dayPeriodOptToSkeleton(String dayPeriodOpt) {
      if (dayPeriodOpt == null) {
         return "";
      } else {
         switch (dayPeriodOpt) {
            case "narrow":
               return "BBBBB";
            case "short":
               return "B";
            case "long":
               return "BBBB";
            default:
               return "";
         }
      }
   }

   private static String hourOptToSkeleton(String hourOpt, String hcOpt) {
      if (hourOpt == null) {
         return "";
      } else {
         switch (hourOpt) {
            case "2-digit":
               switch (hcOpt) {
                  case "h11":
                     return "KK";
                  case "h12":
                     return "hh";
                  case "h23":
                     return "HH";
                  case "h24":
                     return "kk";
                  default:
                     return "";
               }
            case "numeric":
               switch (hcOpt) {
                  case "h11":
                     return "K";
                  case "h12":
                     return "h";
                  case "h23":
                     return "H";
                  case "h24":
                     return "k";
               }
         }

         return "";
      }
   }

   private static String minuteOptToSkeleton(String minuteOpt) {
      if (minuteOpt == null) {
         return "";
      } else {
         switch (minuteOpt) {
            case "2-digit":
               return "mm";
            case "numeric":
               return "m";
            default:
               return "";
         }
      }
   }

   private static String secondOptToSkeleton(String secondOpt, int fractionalSecondDigitsOpt) {
      StringBuilder skeleton = new StringBuilder();
      if (secondOpt != null) {
         if ("numeric".equals(secondOpt)) {
            skeleton.append("s");
         } else {
            assert "2-digit".equals(secondOpt);

            skeleton.append("ss");
         }
      }

      for (int i = 0; i < fractionalSecondDigitsOpt; i++) {
         skeleton.append('S');
      }

      return skeleton.toString();
   }

   private static String timeZoneNameOptToSkeleton(String timeZoneNameOpt) {
      if (timeZoneNameOpt == null) {
         return "";
      } else {
         switch (timeZoneNameOpt) {
            case "short":
               return "z";
            case "long":
               return "zzzz";
            case "shortOffset":
               return "O";
            case "longOffset":
               return "OOOO";
            case "shortGeneric":
               return "v";
            case "longGeneric":
               return "vvvv";
            default:
               return "";
         }
      }
   }

   private static String makeSkeleton(
      String weekdayOpt,
      String eraOpt,
      String yearOpt,
      String monthOpt,
      String dayOpt,
      String dayPeriodOpt,
      String hourOpt,
      String hcOpt,
      String minuteOpt,
      String secondOpt,
      int fractionalSecondDigitsOpt,
      String timeZoneNameOpt
   ) {
      return weekdayOptToSkeleton(weekdayOpt)
         + eraOptToSkeleton(eraOpt)
         + yearOptToSkeleton(yearOpt)
         + monthOptToSkeleton(monthOpt)
         + dayOptToSkeleton(dayOpt)
         + dayPeriodOptToSkeleton(dayPeriodOpt)
         + hourOptToSkeleton(hourOpt, hcOpt)
         + minuteOptToSkeleton(minuteOpt)
         + secondOptToSkeleton(secondOpt, fractionalSecondDigitsOpt)
         + timeZoneNameOptToSkeleton(timeZoneNameOpt);
   }

   private static UnmodifiableEconomicMap<String, String> initCanonicalTimeZoneIDMap() {
      CompilerAsserts.neverPartOfCompilation();
      EconomicMap<String, String> map = EconomicMap.create();

      for (String available : TimeZone.getAvailableIDs()) {
         map.put(IntlUtil.toUpperCase(available), TimeZone.getCanonicalID(available));
      }

      return map;
   }

   @CompilerDirectives.TruffleBoundary
   public static String canonicalizeTimeZoneName(TruffleString tzId) {
      String ucTzId = IntlUtil.toUpperCase(Strings.toJavaString(tzId));
      String canTzId = canonicalTimeZoneIDMap.get().get(ucTzId);
      if (canTzId == null) {
         return null;
      } else {
         return !canTzId.equals("Etc/UTC") && !canTzId.equals("Etc/GMT") ? canTzId : "UTC";
      }
   }

   private static boolean containsOneOf(String suspect, String containees) {
      for (int c : containees.getBytes()) {
         if (suspect.indexOf(c) > -1) {
            return true;
         }
      }

      return false;
   }

   public static DateFormat getDateFormatProperty(JSDynamicObject obj) {
      return getInternalState(obj).dateFormat;
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString format(JSDynamicObject numberFormatObj, Object n) {
      DateFormat dateFormat = getDateFormatProperty(numberFormatObj);
      return Strings.fromJavaString(dateFormat.format(timeClip(n)));
   }

   private static double timeClip(Object n) {
      double x;
      if (n == Undefined.instance) {
         x = JSRealm.get(null).currentTimeMillis();
      } else {
         x = JSDate.timeClip(JSRuntime.toDouble(n));
         if (Double.isNaN(x)) {
            throwDateOutOfRange();
         }
      }

      return x;
   }

   private static void throwDateOutOfRange() throws JSException {
      throw Errors.createRangeError("Provided date is not in valid range.");
   }

   private static UnmodifiableEconomicMap<DateFormat.Field, String> initializeFieldToTypeMap() {
      CompilerAsserts.neverPartOfCompilation();
      EconomicMap<DateFormat.Field, String> map = EconomicMap.create(14);
      map.put(DateFormat.Field.AM_PM, "dayPeriod");
      map.put(DateFormat.Field.AM_PM_MIDNIGHT_NOON, "dayPeriod");
      map.put(DateFormat.Field.FLEXIBLE_DAY_PERIOD, "dayPeriod");
      map.put(DateFormat.Field.ERA, "era");
      map.put(DateFormat.Field.YEAR, "year");
      map.put(DateFormat.Field.RELATED_YEAR, "relatedYear");
      map.put(DateFormat.Field.MONTH, "month");
      map.put(DateFormat.Field.DOW_LOCAL, "weekday");
      map.put(DateFormat.Field.DAY_OF_WEEK, "weekday");
      map.put(DateFormat.Field.DAY_OF_MONTH, "day");
      map.put(DateFormat.Field.HOUR0, "hour");
      map.put(DateFormat.Field.HOUR1, "hour");
      map.put(DateFormat.Field.HOUR_OF_DAY0, "hour");
      map.put(DateFormat.Field.HOUR_OF_DAY1, "hour");
      map.put(DateFormat.Field.MINUTE, "minute");
      map.put(DateFormat.Field.SECOND, "second");
      map.put(DateFormat.Field.MILLISECOND, "fractionalSecond");
      map.put(DateFormat.Field.MILLISECONDS_IN_DAY, "fractionalSecond");
      map.put(DateFormat.Field.TIME_ZONE, "timeZoneName");
      return map;
   }

   private static String fieldToType(DateFormat.Field field) {
      return fieldToTypeMap.get().get(field);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject formatToParts(JSContext context, JSRealm realm, JSDynamicObject numberFormatObj, Object n, String source) {
      DateFormat dateFormat = getDateFormatProperty(numberFormatObj);
      String yearPattern = yearRelatedSubpattern(dateFormat);
      int yearPatternIndex = 0;
      double x = timeClip(n);
      List<Object> resultParts = new ArrayList<>();
      AttributedCharacterIterator fit = dateFormat.formatToCharacterIterator(x);
      String formatted = dateFormat.format(x);
      int i = fit.getBeginIndex();

      while (i < fit.getEndIndex()) {
         fit.setIndex(i);
         Map<Attribute, Object> attributes = fit.getAttributes();
         Set<Attribute> attKeySet = attributes.keySet();
         if (!attKeySet.isEmpty()) {
            Iterator value = attKeySet.iterator();
            if (value.hasNext()) {
               Attribute a = (Attribute)value.next();
               if (!(a instanceof DateFormat.Field)) {
                  throw Errors.shouldNotReachHere();
               }

               String valuex = formatted.substring(fit.getRunStart(), fit.getRunLimit());
               String type;
               if (a == DateFormat.Field.YEAR) {
                  if (yearPatternIndex < yearPattern.length() && yearPattern.charAt(yearPatternIndex) == 'U') {
                     type = "yearName";
                  } else {
                     type = "year";
                  }

                  yearPatternIndex++;
               } else {
                  type = fieldToType((DateFormat.Field)a);

                  assert type != null : a;
               }

               resultParts.add(makePart(context, realm, type, valuex, source));
               i = fit.getRunLimit();
            }
         } else {
            String value = formatted.substring(fit.getRunStart(), fit.getRunLimit());
            resultParts.add(makePart(context, realm, "literal", value, source));
            i = fit.getRunLimit();
         }
      }

      return JSArray.createConstant(context, realm, resultParts.toArray());
   }

   private static DateIntervalFormat.FormattedDateInterval formatRangeImpl(JSDynamicObject dateTimeFormat, double startDate, double endDate) {
      JSDateTimeFormat.InternalState state = getInternalState(dateTimeFormat);
      DateFormat dateFormat = state.dateFormat;
      Calendar calendar = dateFormat.getCalendar();
      Calendar fromCalendar = (Calendar)calendar.clone();
      Calendar toCalendar = (Calendar)calendar.clone();
      fromCalendar.setTimeInMillis((long)startDate);
      toCalendar.setTimeInMillis((long)endDate);
      return state.dateIntervalFormat.formatToValue(fromCalendar, toCalendar);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString formatRange(JSDynamicObject dateTimeFormat, double startDate, double endDate) {
      DateIntervalFormat.FormattedDateInterval formattedRange = formatRangeImpl(dateTimeFormat, startDate, endDate);
      return dateFieldsPracticallyEqual(formattedRange) ? format(dateTimeFormat, startDate) : Strings.fromJavaString(formattedRange.toString());
   }

   private static boolean dateFieldsPracticallyEqual(DateIntervalFormat.FormattedDateInterval formattedRange) {
      ConstrainedFieldPosition cfPos = new ConstrainedFieldPosition();

      while (formattedRange.nextPosition(cfPos)) {
         if (cfPos.getField() instanceof DateIntervalFormat.SpanField) {
            return false;
         }
      }

      return true;
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject formatRangeToParts(JSContext context, JSRealm realm, JSDynamicObject dateTimeFormat, double startDate, double endDate) {
      DateIntervalFormat.FormattedDateInterval formattedRange = formatRangeImpl(dateTimeFormat, startDate, endDate);
      if (dateFieldsPracticallyEqual(formattedRange)) {
         return formatToParts(context, realm, dateTimeFormat, startDate, "shared");
      } else {
         String formattedString = formattedRange.toString();
         String digits = null;
         List<Object> parts = new ArrayList<>();
         int startRangeStart = 0;
         int startRangeLimit = 0;
         int endRangeStart = 0;
         int endRangeLimit = 0;
         int lastLimit = 0;
         ConstrainedFieldPosition cfPos = new ConstrainedFieldPosition();

         while (formattedRange.nextPosition(cfPos)) {
            int start = cfPos.getStart();
            int limit = cfPos.getLimit();
            if (lastLimit < start) {
               String literal = formattedString.substring(lastLimit, start);
               String source = IntlUtil.sourceString(lastLimit, start, startRangeStart, startRangeLimit, endRangeStart, endRangeLimit);
               parts.add(makePart(context, realm, "literal", literal, source));
               lastLimit = start;
            }

            Field field = cfPos.getField();
            if (field instanceof DateIntervalFormat.SpanField) {
               Object fieldValue = cfPos.getFieldValue();
               if (fieldValue.equals(0)) {
                  startRangeStart = start;
                  startRangeLimit = limit;
               } else {
                  if (!fieldValue.equals(1)) {
                     throw Errors.shouldNotReachHere(fieldValue.toString());
                  }

                  endRangeStart = start;
                  endRangeLimit = limit;
               }
            } else {
               if (!(field instanceof DateFormat.Field)) {
                  throw Errors.shouldNotReachHere(field.toString());
               }

               String value = formattedString.substring(start, limit);
               String type;
               if (field == DateFormat.Field.YEAR) {
                  if (digits == null) {
                     String numberingSystem = getInternalState(dateTimeFormat).numberingSystem;
                     digits = NumberingSystem.getInstanceByName(numberingSystem).getDescription();
                  }

                  boolean year = value.length() > 0 && digits.indexOf(value.charAt(0)) != -1;
                  type = year ? "year" : "yearName";
               } else {
                  type = fieldToType((DateFormat.Field)field);
               }

               String source = IntlUtil.sourceString(start, limit, startRangeStart, startRangeLimit, endRangeStart, endRangeLimit);
               parts.add(makePart(context, realm, type, value, source));
               lastLimit = limit;
            }
         }

         int length = formattedString.length();
         if (lastLimit < length) {
            String literal = formattedString.substring(lastLimit, length);
            String source = IntlUtil.sourceString(lastLimit, length, startRangeStart, startRangeLimit, endRangeStart, endRangeLimit);
            parts.add(makePart(context, realm, "literal", literal, source));
         }

         return JSArray.createConstant(context, realm, parts.toArray());
      }
   }

   private static String yearRelatedSubpattern(DateFormat dateFormat) {
      if (!(dateFormat instanceof SimpleDateFormat)) {
         return "";
      } else {
         String pattern = ((SimpleDateFormat)dateFormat).toPattern();
         StringBuilder sb = new StringBuilder();
         boolean quoted = false;

         for (char c : pattern.toCharArray()) {
            if (c == '\'') {
               quoted = !quoted;
            } else if (!quoted && (c == 'y' || c == 'Y' || c == 'u' || c == 'U')) {
               sb.append(c);
            }
         }

         return sb.toString();
      }
   }

   private static Object makePart(JSContext context, JSRealm realm, String type, String value, String source) {
      JSObject p = JSOrdinary.create(context, realm);
      JSObject.set(p, IntlUtil.KEY_TYPE, Strings.fromJavaString(type));
      JSObject.set(p, IntlUtil.KEY_VALUE, Strings.fromJavaString(value));
      if (source != null) {
         JSObject.set(p, IntlUtil.KEY_SOURCE, Strings.fromJavaString(source));
      }

      return p;
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject resolvedOptions(JSContext context, JSRealm realm, JSDynamicObject numberFormatObj) {
      JSDateTimeFormat.InternalState state = getInternalState(numberFormatObj);
      return state.toResolvedOptionsObject(context, realm);
   }

   public static JSDateTimeFormat.InternalState getInternalState(JSDynamicObject obj) {
      assert isJSDateTimeFormat(obj);

      return ((JSDateTimeFormatObject)obj).getInternalState();
   }

   private static CallTarget createGetFormatCallTarget(JSContext context) {
      return (new JavaScriptRootNode(context.getLanguage(), null, null) {
            private final BranchProfile errorBranch = BranchProfile.create();
            @Node.Child
            private PropertySetNode setBoundObjectNode = PropertySetNode.createSetHidden(JSDateTimeFormat.BOUND_OBJECT_KEY, context);

            @Override
            public Object execute(VirtualFrame frame) {
               Object[] frameArgs = frame.getArguments();
               Object dateTimeFormatObj = JSArguments.getThisObject(frameArgs);
               if (JSDateTimeFormat.isJSDateTimeFormat(dateTimeFormatObj)) {
                  JSDateTimeFormat.InternalState state = JSDateTimeFormat.getInternalState((JSDynamicObject)dateTimeFormatObj);
                  if (state != null && state.initialized) {
                     if (state.boundFormatFunction == null) {
                        JSFunctionData formatFunctionData = context.getOrCreateBuiltinFunctionData(
                           JSContext.BuiltinFunctionKey.DateTimeFormatFormat, c -> JSDateTimeFormat.createFormatFunctionData(c)
                        );
                        JSDynamicObject formatFn = JSFunction.create(this.getRealm(), formatFunctionData);
                        this.setBoundObjectNode.setValue(formatFn, dateTimeFormatObj);
                        state.boundFormatFunction = formatFn;
                     }

                     return state.boundFormatFunction;
                  } else {
                     this.errorBranch.enter();
                     throw Errors.createTypeErrorMethodCalledOnNonObjectOrWrongType("format");
                  }
               } else {
                  this.errorBranch.enter();
                  throw Errors.createTypeErrorTypeXExpected(JSDateTimeFormat.CLASS_NAME);
               }
            }
         })
         .getCallTarget();
   }

   private static JSFunctionData createFormatFunctionData(JSContext context) {
      return JSFunctionData.createCallOnly(context, (new JavaScriptRootNode(context.getLanguage(), null, null) {
         @Node.Child
         private PropertyGetNode getBoundObjectNode = PropertyGetNode.createGetHidden(JSDateTimeFormat.BOUND_OBJECT_KEY, context);

         @Override
         public Object execute(VirtualFrame frame) {
            Object[] arguments = frame.getArguments();
            JSDynamicObject thisObj = (JSDynamicObject)this.getBoundObjectNode.getValue(JSArguments.getFunctionObject(arguments));

            assert JSDateTimeFormat.isJSDateTimeFormat(thisObj);

            Object n = JSArguments.getUserArgumentCount(arguments) > 0 ? JSArguments.getUserArgument(arguments, 0) : Undefined.instance;
            return JSDateTimeFormat.format(thisObj, n);
         }
      }).getCallTarget(), 1, Strings.EMPTY_STRING);
   }

   private static JSDynamicObject createFormatFunctionGetter(JSRealm realm, JSContext context) {
      JSFunctionData fd = realm.getContext().getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.DateTimeFormatGetFormat, c -> {
         CallTarget ct = createGetFormatCallTarget(context);
         return JSFunctionData.create(context, ct, ct, 0, GET_FORMAT_NAME, false, false, false, true);
      });
      return JSFunction.create(realm, fd);
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getDateTimeFormatPrototype();
   }

   public static class InternalState {
      private boolean initialized = false;
      private DateFormat dateFormat;
      private DateIntervalFormat dateIntervalFormat;
      private JSDynamicObject boundFormatFunction = null;
      private String locale;
      private String calendar;
      private String numberingSystem;
      private String weekday;
      private String era;
      private String year;
      private String month;
      private String day;
      private String dayPeriod;
      private String hour;
      private String minute;
      private String second;
      private int fractionalSecondDigits;
      private String hourCycle;
      private String timeZoneName;
      private String timeZone;
      private String dateStyle;
      private String timeStyle;

      JSObject toResolvedOptionsObject(JSContext context, JSRealm realm) {
         JSObject result = JSOrdinary.create(context, realm);
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_LOCALE, Strings.fromJavaString(this.locale), JSAttributes.getDefault());
         if (this.calendar != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_CALENDAR, Strings.fromJavaString(this.calendar), JSAttributes.getDefault());
         }

         JSObjectUtil.defineDataProperty(
            context, result, IntlUtil.KEY_NUMBERING_SYSTEM, Strings.fromJavaString(this.numberingSystem), JSAttributes.getDefault()
         );
         if (this.timeZone != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_TIME_ZONE, Strings.fromJavaString(this.timeZone), JSAttributes.getDefault());
         }

         if (this.hourCycle != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_HOUR_CYCLE, Strings.fromJavaString(this.hourCycle), JSAttributes.getDefault());
            boolean hour12 = "h11".equals(this.hourCycle) || "h12".equals(this.hourCycle);
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_HOUR12, hour12, JSAttributes.getDefault());
         }

         if (this.weekday != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_WEEKDAY, Strings.fromJavaString(this.weekday), JSAttributes.getDefault());
         }

         if (this.era != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_ERA, Strings.fromJavaString(this.era), JSAttributes.getDefault());
         }

         if (this.year != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_YEAR, Strings.fromJavaString(this.year), JSAttributes.getDefault());
         }

         if (this.month != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_MONTH, Strings.fromJavaString(this.month), JSAttributes.getDefault());
         }

         if (this.day != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_DAY, Strings.fromJavaString(this.day), JSAttributes.getDefault());
         }

         if (this.dayPeriod != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_DAY_PERIOD, Strings.fromJavaString(this.dayPeriod), JSAttributes.getDefault());
         }

         if (this.hour != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_HOUR, Strings.fromJavaString(this.hour), JSAttributes.getDefault());
         }

         if (this.minute != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_MINUTE, Strings.fromJavaString(this.minute), JSAttributes.getDefault());
         }

         if (this.second != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_SECOND, Strings.fromJavaString(this.second), JSAttributes.getDefault());
         }

         if (this.fractionalSecondDigits != 0) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_FRACTIONAL_SECOND_DIGITS, this.fractionalSecondDigits, JSAttributes.getDefault());
         }

         if (this.timeZoneName != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_TIME_ZONE_NAME, Strings.fromJavaString(this.timeZoneName), JSAttributes.getDefault());
         }

         if (this.dateStyle != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_DATE_STYLE, Strings.fromJavaString(this.dateStyle), JSAttributes.getDefault());
         }

         if (this.timeStyle != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_TIME_STYLE, Strings.fromJavaString(this.timeStyle), JSAttributes.getDefault());
         }

         return result;
      }
   }
}
