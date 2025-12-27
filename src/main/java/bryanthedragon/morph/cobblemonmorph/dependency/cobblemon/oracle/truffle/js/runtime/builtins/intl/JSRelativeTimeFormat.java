package com.oracle.truffle.js.runtime.builtins.intl;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.DecimalFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.DisplayContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.NumberFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.RelativeDateTimeFormatter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.ULocale;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.RelativeTimeFormatFunctionBuiltins;
import com.oracle.truffle.js.builtins.intl.RelativeTimeFormatPrototypeBuiltins;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import com.oracle.truffle.js.runtime.util.LazyValue;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.UnmodifiableEconomicMap;

public final class JSRelativeTimeFormat extends JSNonProxy implements JSConstructorFactory.WithFunctions, PrototypeSupplier {
   public static final TruffleString CLASS_NAME = Strings.constant("RelativeTimeFormat");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("RelativeTimeFormat.prototype");
   public static final TruffleString TO_STRING_TAG = Strings.constant("Intl.RelativeTimeFormat");
   public static final JSRelativeTimeFormat INSTANCE = new JSRelativeTimeFormat();
   private static final LazyValue<UnmodifiableEconomicMap<String, RelativeDateTimeFormatter.RelativeDateTimeUnit>> timeUnitMap = new LazyValue<>(
      JSRelativeTimeFormat::initTimeUnitMap
   );

   private JSRelativeTimeFormat() {
   }

   public static boolean isJSRelativeTimeFormat(Object obj) {
      return obj instanceof JSRelativeTimeFormatObject;
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
      JSObject relativeTimeFormatPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(ctx, relativeTimeFormatPrototype, ctor);
      JSObjectUtil.putFunctionsFromContainer(realm, relativeTimeFormatPrototype, RelativeTimeFormatPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(relativeTimeFormatPrototype, TO_STRING_TAG);
      return relativeTimeFormatPrototype;
   }

   @Override
   public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, RelativeTimeFormatFunctionBuiltins.BUILTINS);
   }

   public static JSRelativeTimeFormatObject create(JSContext context, JSRealm realm) {
      JSRelativeTimeFormat.InternalState state = new JSRelativeTimeFormat.InternalState();
      JSObjectFactory factory = context.getRelativeTimeFormatFactory();
      JSRelativeTimeFormatObject obj = new JSRelativeTimeFormatObject(factory.getShape(realm), state);
      factory.initProto(obj, realm);
      return context.trackAllocation(obj);
   }

   public static RelativeDateTimeFormatter getRelativeDateTimeFormatterProperty(JSDynamicObject obj) {
      return getInternalState(obj).getRelativeDateTimeFormatter();
   }

   private static void ensureFiniteNumber(double d) {
      if (!Double.isFinite(d)) {
         throw Errors.createRangeError("Value need to be finite number for Intl.RelativeTimeFormat operation");
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString format(JSDynamicObject relativeTimeFormatObj, double amount, String unit) {
      ensureFiniteNumber(amount);
      JSRelativeTimeFormat.InternalState state = getInternalState(relativeTimeFormatObj);
      RelativeDateTimeFormatter.RelativeDateTimeUnit icuUnit = singularRelativeTimeUnit("format", unit);
      return Strings.fromJavaString(innerFormat(amount, state, state.getRelativeDateTimeFormatter(), icuUnit));
   }

   private static String innerFormat(
      double amount,
      JSRelativeTimeFormat.InternalState state,
      RelativeDateTimeFormatter relativeDateTimeFormatter,
      RelativeDateTimeFormatter.RelativeDateTimeUnit icuUnit
   ) {
      return state.getNumeric().equals("always") ? relativeDateTimeFormatter.formatNumeric(amount, icuUnit) : relativeDateTimeFormatter.format(amount, icuUnit);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject formatToParts(JSContext context, JSRealm realm, JSDynamicObject relativeTimeFormatObj, double amount, String unit) {
      ensureFiniteNumber(amount);
      JSRelativeTimeFormat.InternalState state = getInternalState(relativeTimeFormatObj);
      RelativeDateTimeFormatter relativeDateTimeFormatter = state.getRelativeDateTimeFormatter();
      NumberFormat numberFormat = relativeDateTimeFormatter.getNumberFormat();
      RelativeDateTimeFormatter.RelativeDateTimeUnit icuUnit = singularRelativeTimeUnit("formatToParts", unit);
      String formattedText = innerFormat(amount, state, relativeDateTimeFormatter, icuUnit);
      double positiveAmount = Math.abs(amount);
      String formattedNumber = numberFormat.format(positiveAmount);
      int numberIndex = formattedText.indexOf(formattedNumber);
      boolean numberPresentInFormattedText = numberIndex > -1;
      List<Object> resultParts = new ArrayList<>();
      if (numberPresentInFormattedText) {
         if (numberIndex > 0) {
            resultParts.add(IntlUtil.makePart(context, realm, "literal", formattedText.substring(0, numberIndex)));
         }

         String esUnit = icuUnit.toString().toLowerCase();
         AttributedCharacterIterator iterator = numberFormat.formatToCharacterIterator(positiveAmount);
         String formatted = numberFormat.format(positiveAmount);
         resultParts.addAll(JSNumberFormat.innerFormatToParts(context, realm, iterator, positiveAmount, formatted, esUnit, false));
         if (numberIndex + formattedNumber.length() < formattedText.length()) {
            resultParts.add(
               IntlUtil.makePart(context, realm, "literal", formattedText.substring(numberIndex + formattedNumber.length(), formattedText.length()))
            );
         }
      } else {
         resultParts.add(IntlUtil.makePart(context, realm, "literal", formattedText));
      }

      return JSArray.createConstant(context, realm, resultParts.toArray());
   }

   private static RelativeDateTimeFormatter createFormatter(Locale locale, String style) {
      ULocale ulocale = ULocale.forLocale(locale);
      NumberFormat numberFormat = NumberFormat.getNumberInstance(ulocale);
      if (numberFormat instanceof DecimalFormat) {
         ((DecimalFormat)numberFormat).setMinimumGroupingDigits(-2);
      }

      return RelativeDateTimeFormatter.getInstance(
         ulocale, numberFormat, RelativeDateTimeFormatter.Style.valueOf(style.toUpperCase()), DisplayContext.CAPITALIZATION_NONE
      );
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject resolvedOptions(JSContext context, JSRealm realm, JSDynamicObject relativeTimeFormatObj) {
      JSRelativeTimeFormat.InternalState state = getInternalState(relativeTimeFormatObj);
      return state.toResolvedOptionsObject(context, realm);
   }

   public static JSRelativeTimeFormat.InternalState getInternalState(JSDynamicObject obj) {
      assert isJSRelativeTimeFormat(obj);

      return ((JSRelativeTimeFormatObject)obj).getInternalState();
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getRelativeTimeFormatPrototype();
   }

   private static RelativeDateTimeFormatter.RelativeDateTimeUnit toRelTimeUnit(String unit) {
      return timeUnitMap.get().get(unit);
   }

   private static UnmodifiableEconomicMap<String, RelativeDateTimeFormatter.RelativeDateTimeUnit> initTimeUnitMap() {
      CompilerAsserts.neverPartOfCompilation();
      EconomicMap<String, RelativeDateTimeFormatter.RelativeDateTimeUnit> map = EconomicMap.create(16);
      map.put("second", RelativeDateTimeFormatter.RelativeDateTimeUnit.SECOND);
      map.put("seconds", RelativeDateTimeFormatter.RelativeDateTimeUnit.SECOND);
      map.put("minute", RelativeDateTimeFormatter.RelativeDateTimeUnit.MINUTE);
      map.put("minutes", RelativeDateTimeFormatter.RelativeDateTimeUnit.MINUTE);
      map.put("hour", RelativeDateTimeFormatter.RelativeDateTimeUnit.HOUR);
      map.put("hours", RelativeDateTimeFormatter.RelativeDateTimeUnit.HOUR);
      map.put("day", RelativeDateTimeFormatter.RelativeDateTimeUnit.DAY);
      map.put("days", RelativeDateTimeFormatter.RelativeDateTimeUnit.DAY);
      map.put("week", RelativeDateTimeFormatter.RelativeDateTimeUnit.WEEK);
      map.put("weeks", RelativeDateTimeFormatter.RelativeDateTimeUnit.WEEK);
      map.put("month", RelativeDateTimeFormatter.RelativeDateTimeUnit.MONTH);
      map.put("months", RelativeDateTimeFormatter.RelativeDateTimeUnit.MONTH);
      map.put("quarter", RelativeDateTimeFormatter.RelativeDateTimeUnit.QUARTER);
      map.put("quarters", RelativeDateTimeFormatter.RelativeDateTimeUnit.QUARTER);
      map.put("year", RelativeDateTimeFormatter.RelativeDateTimeUnit.YEAR);
      map.put("years", RelativeDateTimeFormatter.RelativeDateTimeUnit.YEAR);
      return map;
   }

   private static RelativeDateTimeFormatter.RelativeDateTimeUnit singularRelativeTimeUnit(String functionName, String unit) {
      RelativeDateTimeFormatter.RelativeDateTimeUnit result = toRelTimeUnit(unit);
      if (result != null) {
         return result;
      } else {
         throw Errors.createRangeErrorInvalidUnitArgument(functionName, unit);
      }
   }

   public static class InternalState extends JSNumberFormat.BasicInternalState {
      private RelativeDateTimeFormatter relativeDateTimeFormatter;
      private String style;
      private String numeric;

      @Override
      JSDynamicObject toResolvedOptionsObject(JSContext context, JSRealm realm) {
         JSDynamicObject result = JSOrdinary.create(context, realm);
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_LOCALE, Strings.fromJavaString(this.getLocale()), JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_STYLE, Strings.fromJavaString(this.style), JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_NUMERIC, Strings.fromJavaString(this.numeric), JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(
            context, result, IntlUtil.KEY_NUMBERING_SYSTEM, Strings.fromJavaString(this.getNumberingSystem()), JSAttributes.getDefault()
         );
         return result;
      }

      @CompilerDirectives.TruffleBoundary
      public void initializeRelativeTimeFormatter() {
         this.relativeDateTimeFormatter = JSRelativeTimeFormat.createFormatter(this.getJavaLocale(), this.style);
      }

      public RelativeDateTimeFormatter getRelativeDateTimeFormatter() {
         return this.relativeDateTimeFormatter;
      }

      public void setStyle(String style) {
         this.style = style;
      }

      public void setNumeric(String numeric) {
         this.numeric = numeric;
      }

      public String getNumeric() {
         return this.numeric;
      }
   }
}
