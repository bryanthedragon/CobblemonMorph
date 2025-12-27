package com.oracle.truffle.js.runtime.builtins.intl;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number.FormattedNumberRange;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number.FractionPrecision;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number.IntegerWidth;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number.LocalizedNumberFormatter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number.LocalizedNumberRangeFormatter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number.Notation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number.NumberFormatter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number.NumberRangeFormatter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number.Precision;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number.Scale;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number.UnlocalizedNumberFormatter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.ConstrainedFieldPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.FormattedValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.NumberFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.NumberingSystem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.Currency;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.MeasureUnit;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.NumberFormatFunctionBuiltins;
import com.oracle.truffle.js.builtins.intl.NumberFormatPrototypeBuiltins;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.intl.ToIntlMathematicalValue;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.Format.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Locale.Builder;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.UnmodifiableEconomicMap;

public final class JSNumberFormat extends JSNonProxy implements JSConstructorFactory.WithFunctions, PrototypeSupplier {
   public static final TruffleString CLASS_NAME = Strings.constant("NumberFormat");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("NumberFormat.prototype");
   public static final TruffleString TO_STRING_TAG = Strings.constant("Intl.NumberFormat");
   public static final TruffleString GET_FORMAT_NAME = Strings.constant("get format");
   static final HiddenKey BOUND_OBJECT_KEY = new HiddenKey(Strings.toJavaString(CLASS_NAME));
   public static final JSNumberFormat INSTANCE = new JSNumberFormat();
   private static final Set<String> historicalCurrenciesInJDK = new HashSet<>(
      Arrays.asList("ADP", "BEF", "BYB", "BYR", "ESP", "GRD", "ITL", "LUF", "MGF", "PTE", "ROL", "TPE", "TRL")
   );
   private static final LazyValue<UnmodifiableEconomicMap<NumberFormat.Field, String>> fieldToTypeMap = new LazyValue<>(
      JSNumberFormat::initializeFieldToTypeMap
   );

   private JSNumberFormat() {
   }

   public static boolean isJSNumberFormat(Object obj) {
      return obj instanceof JSNumberFormatObject;
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
      JSObjectUtil.putFunctionsFromContainer(realm, numberFormatPrototype, NumberFormatPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putBuiltinAccessorProperty(numberFormatPrototype, Strings.FORMAT, createFormatFunctionGetter(realm, ctx), Undefined.instance);
      JSObjectUtil.putToStringTag(numberFormatPrototype, TO_STRING_TAG);
      return numberFormatPrototype;
   }

   @CompilerDirectives.TruffleBoundary
   public static int currencyDigits(JSContext context, String currencyCode) {
      if (context.isOptionV8CompatibilityMode()) {
         return Currency.getInstance(currencyCode).getDefaultFractionDigits();
      } else if (historicalCurrenciesInJDK.contains(currencyCode)) {
         return 2;
      } else {
         try {
            int digits = java.util.Currency.getInstance(currencyCode).getDefaultFractionDigits();
            return digits == -1 ? 2 : digits;
         } catch (IllegalArgumentException var3) {
            return 2;
         }
      }
   }

   @Override
   public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, NumberFormatFunctionBuiltins.BUILTINS);
   }

   public static JSNumberFormatObject create(JSContext context, JSRealm realm) {
      JSNumberFormat.InternalState state = new JSNumberFormat.InternalState();
      JSObjectFactory factory = context.getNumberFormatFactory();
      JSNumberFormatObject obj = new JSNumberFormatObject(factory.getShape(realm), state);
      factory.initProto(obj, realm);
      return context.trackAllocation(obj);
   }

   private static Notation notationToICUNotation(String notation, String compactDisplay) {
      Notation icuNotation;
      switch (notation) {
         case "standard":
            icuNotation = Notation.simple();
            break;
         case "scientific":
            icuNotation = Notation.scientific();
            break;
         case "engineering":
            icuNotation = Notation.engineering();
            break;
         case "compact":
            icuNotation = "long".equals(compactDisplay) ? Notation.compactLong() : Notation.compactShort();
            break;
         default:
            throw Errors.shouldNotReachHere(notation);
      }

      return icuNotation;
   }

   private static NumberFormatter.UnitWidth currencyDisplayToUnitWidth(String currencyDisplay) {
      NumberFormatter.UnitWidth unitWidth;
      switch (currencyDisplay) {
         case "code":
            unitWidth = NumberFormatter.UnitWidth.ISO_CODE;
            break;
         case "symbol":
            unitWidth = NumberFormatter.UnitWidth.SHORT;
            break;
         case "narrowSymbol":
            unitWidth = NumberFormatter.UnitWidth.NARROW;
            break;
         case "name":
            unitWidth = NumberFormatter.UnitWidth.FULL_NAME;
            break;
         default:
            throw Errors.shouldNotReachHere(currencyDisplay);
      }

      return unitWidth;
   }

   private static NumberFormatter.UnitWidth unitDisplayToUnitWidth(String unitDisplay) {
      NumberFormatter.UnitWidth unitWidth;
      switch (unitDisplay) {
         case "short":
            unitWidth = NumberFormatter.UnitWidth.SHORT;
            break;
         case "narrow":
            unitWidth = NumberFormatter.UnitWidth.NARROW;
            break;
         case "long":
            unitWidth = NumberFormatter.UnitWidth.FULL_NAME;
            break;
         default:
            throw Errors.shouldNotReachHere(unitDisplay);
      }

      return unitWidth;
   }

   private static NumberFormatter.GroupingStrategy useGroupingToGroupingStrategy(Object useGrouping) {
      NumberFormatter.GroupingStrategy strategy;
      if (Boolean.FALSE.equals(useGrouping)) {
         strategy = NumberFormatter.GroupingStrategy.OFF;
      } else if ("min2".equals(useGrouping)) {
         strategy = NumberFormatter.GroupingStrategy.MIN2;
      } else if ("auto".equals(useGrouping)) {
         strategy = NumberFormatter.GroupingStrategy.AUTO;
      } else {
         assert "always".equals(useGrouping);

         strategy = NumberFormatter.GroupingStrategy.ON_ALIGNED;
      }

      return strategy;
   }

   private static RoundingMode roundingModeToICURoundingMode(String roundingMode) {
      RoundingMode mode;
      switch (roundingMode) {
         case "ceil":
            mode = RoundingMode.CEILING;
            break;
         case "floor":
            mode = RoundingMode.FLOOR;
            break;
         case "expand":
            mode = RoundingMode.UP;
            break;
         case "trunc":
            mode = RoundingMode.DOWN;
            break;
         case "halfCeil":
            mode = RoundingMode.HALF_UP;
            break;
         case "halfFloor":
            mode = RoundingMode.HALF_DOWN;
            break;
         case "halfExpand":
            mode = RoundingMode.HALF_UP;
            break;
         case "halfTrunc":
            mode = RoundingMode.HALF_DOWN;
            break;
         case "halfEven":
            mode = RoundingMode.HALF_EVEN;
            break;
         default:
            throw Errors.shouldNotReachHere(roundingMode);
      }

      return mode;
   }

   private static MeasureUnit unitToMeasureUnit(String unit) {
      MeasureUnit measureUnit;
      switch (unit) {
         case "acre":
            measureUnit = MeasureUnit.ACRE;
            break;
         case "bit":
            measureUnit = MeasureUnit.BIT;
            break;
         case "byte":
            measureUnit = MeasureUnit.BYTE;
            break;
         case "celsius":
            measureUnit = MeasureUnit.CELSIUS;
            break;
         case "centimeter":
            measureUnit = MeasureUnit.CENTIMETER;
            break;
         case "day":
            measureUnit = MeasureUnit.DAY;
            break;
         case "degree":
            measureUnit = MeasureUnit.DEGREE;
            break;
         case "fahrenheit":
            measureUnit = MeasureUnit.FAHRENHEIT;
            break;
         case "fluid-ounce":
            measureUnit = MeasureUnit.FLUID_OUNCE;
            break;
         case "foot":
            measureUnit = MeasureUnit.FOOT;
            break;
         case "gallon":
            measureUnit = MeasureUnit.GALLON;
            break;
         case "gigabit":
            measureUnit = MeasureUnit.GIGABIT;
            break;
         case "gigabyte":
            measureUnit = MeasureUnit.GIGABYTE;
            break;
         case "gram":
            measureUnit = MeasureUnit.GRAM;
            break;
         case "hectare":
            measureUnit = MeasureUnit.HECTARE;
            break;
         case "hour":
            measureUnit = MeasureUnit.HOUR;
            break;
         case "inch":
            measureUnit = MeasureUnit.INCH;
            break;
         case "kilobit":
            measureUnit = MeasureUnit.KILOBIT;
            break;
         case "kilobyte":
            measureUnit = MeasureUnit.KILOBYTE;
            break;
         case "kilogram":
            measureUnit = MeasureUnit.KILOGRAM;
            break;
         case "kilometer":
            measureUnit = MeasureUnit.KILOMETER;
            break;
         case "liter":
            measureUnit = MeasureUnit.LITER;
            break;
         case "megabit":
            measureUnit = MeasureUnit.MEGABIT;
            break;
         case "megabyte":
            measureUnit = MeasureUnit.MEGABYTE;
            break;
         case "meter":
            measureUnit = MeasureUnit.METER;
            break;
         case "mile":
            measureUnit = MeasureUnit.MILE;
            break;
         case "mile-scandinavian":
            measureUnit = MeasureUnit.MILE_SCANDINAVIAN;
            break;
         case "milliliter":
            measureUnit = MeasureUnit.MILLILITER;
            break;
         case "millimeter":
            measureUnit = MeasureUnit.MILLIMETER;
            break;
         case "millisecond":
            measureUnit = MeasureUnit.MILLISECOND;
            break;
         case "minute":
            measureUnit = MeasureUnit.MINUTE;
            break;
         case "month":
            measureUnit = MeasureUnit.MONTH;
            break;
         case "ounce":
            measureUnit = MeasureUnit.OUNCE;
            break;
         case "percent":
            measureUnit = MeasureUnit.PERCENT;
            break;
         case "petabyte":
            measureUnit = MeasureUnit.PETABYTE;
            break;
         case "pound":
            measureUnit = MeasureUnit.POUND;
            break;
         case "second":
            measureUnit = MeasureUnit.SECOND;
            break;
         case "stone":
            measureUnit = MeasureUnit.STONE;
            break;
         case "terabit":
            measureUnit = MeasureUnit.TERABIT;
            break;
         case "terabyte":
            measureUnit = MeasureUnit.TERABYTE;
            break;
         case "week":
            measureUnit = MeasureUnit.WEEK;
            break;
         case "yard":
            measureUnit = MeasureUnit.YARD;
            break;
         case "year":
            measureUnit = MeasureUnit.YEAR;
            break;
         default:
            throw Errors.shouldNotReachHere(unit);
      }

      return measureUnit;
   }

   private static NumberFormatter.SignDisplay signDisplay(String signDisplay, boolean accounting) {
      switch (signDisplay) {
         case "auto":
            return accounting ? NumberFormatter.SignDisplay.ACCOUNTING : NumberFormatter.SignDisplay.AUTO;
         case "never":
            return NumberFormatter.SignDisplay.NEVER;
         case "always":
            return accounting ? NumberFormatter.SignDisplay.ACCOUNTING_ALWAYS : NumberFormatter.SignDisplay.ALWAYS;
         case "exceptZero":
            return accounting ? NumberFormatter.SignDisplay.ACCOUNTING_EXCEPT_ZERO : NumberFormatter.SignDisplay.EXCEPT_ZERO;
         case "negative":
            return accounting ? NumberFormatter.SignDisplay.ACCOUNTING_NEGATIVE : NumberFormatter.SignDisplay.NEGATIVE;
         default:
            throw Errors.shouldNotReachHere(signDisplay);
      }
   }

   private static FormattedValue formattedValue(JSNumberFormat.InternalState state, Number x) {
      LocalizedNumberFormatter numberFormatter = state.getNumberFormatter(x.doubleValue() < 0.0);
      return numberFormatter.format(x);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString format(JSDynamicObject numberFormatObj, Object n) {
      JSNumberFormat.InternalState state = getInternalState(numberFormatObj);
      Number x = toInternalNumberRepresentation(JSRuntime.toNumeric(n));
      return Strings.fromJavaString(formattedValue(state, x).toString());
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString formatMV(JSDynamicObject numberFormatObj, Number mv) {
      JSNumberFormat.InternalState state = getInternalState(numberFormatObj);
      return Strings.fromJavaString(formattedValue(state, mv).toString());
   }

   private static FormattedNumberRange formatRangeImpl(JSDynamicObject numberFormatObj, Number x, Number y) {
      if (!JSRuntime.isNaN(x) && !JSRuntime.isNaN(y)) {
         JSNumberFormat.InternalState state = getInternalState(numberFormatObj);
         boolean xNegative = x instanceof BigDecimal ? ((BigDecimal)x).signum() == -1 : (Double)x < 0.0;
         boolean yNegative = y instanceof BigDecimal ? ((BigDecimal)y).signum() == -1 : (Double)y < 0.0;
         LocalizedNumberRangeFormatter formatter = state.getNumberRangeFormatter(xNegative, yNegative);
         return formatter.formatRange(x, y);
      } else {
         throw Errors.createRangeError("invalid range");
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString formatRange(JSDynamicObject numberFormatObj, Number x, Number y) {
      return Strings.fromJavaString(formatRangeImpl(numberFormatObj, x, y).toString());
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject formatRangeToParts(JSContext context, JSRealm realm, JSDynamicObject numberFormatObj, Number x, Number y) {
      FormattedNumberRange formattedRange = formatRangeImpl(numberFormatObj, x, y);
      String formattedString = formattedRange.toString();
      List<Object> parts = new ArrayList<>();
      int startRangeStart = 0;
      int startRangeLimit = 0;
      int endRangeStart = 0;
      int endRangeLimit = 0;
      int lastLimit = 0;
      boolean seenGroupingSeparator = false;
      ConstrainedFieldPosition cfPos = new ConstrainedFieldPosition();

      while (formattedRange.nextPosition(cfPos)) {
         int start = cfPos.getStart();
         int limit = cfPos.getLimit();
         Field field = cfPos.getField();
         if (lastLimit < start) {
            String literal = formattedString.substring(lastLimit, start);
            String source = IntlUtil.sourceString(lastLimit, start, startRangeStart, startRangeLimit, endRangeStart, endRangeLimit);
            String type;
            if (field == NumberFormat.Field.GROUPING_SEPARATOR) {
               type = "integer";
               seenGroupingSeparator = true;
            } else {
               type = "literal";
            }

            parts.add(IntlUtil.makePart(context, realm, type, literal, null, source));
            lastLimit = start;
         }

         if (field instanceof NumberRangeFormatter.SpanField) {
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
            if (!(field instanceof NumberFormat.Field)) {
               throw Errors.shouldNotReachHere(field.toString());
            }

            int effectiveStart;
            if (field == NumberFormat.Field.INTEGER && seenGroupingSeparator) {
               effectiveStart = lastLimit;
               seenGroupingSeparator = false;
            } else {
               assert !seenGroupingSeparator || field == NumberFormat.Field.GROUPING_SEPARATOR;

               effectiveStart = start;
            }

            String value = formattedString.substring(effectiveStart, limit);
            String source = IntlUtil.sourceString(start, limit, startRangeStart, startRangeLimit, endRangeStart, endRangeLimit);
            String type;
            if (field == NumberFormat.Field.SIGN) {
               type = isPlusSign(value) ? "plusSign" : "minusSign";
            } else if (field == NumberFormat.Field.INTEGER) {
               Object val = "endRange".equals(source) ? y : y;
               if (JSRuntime.isNaN(val)) {
                  type = "nan";
               } else if (val instanceof Double && Double.isInfinite((Double)val)) {
                  type = "infinity";
               } else {
                  type = "integer";
               }
            } else {
               type = fieldToType((NumberFormat.Field)field);

               assert type != null : field;
            }

            parts.add(IntlUtil.makePart(context, realm, type, value, null, source));
            lastLimit = limit;
         }
      }

      int length = formattedString.length();
      if (lastLimit < length) {
         String literal = formattedString.substring(lastLimit, length);
         String source = IntlUtil.sourceString(lastLimit, length, startRangeStart, startRangeLimit, endRangeStart, endRangeLimit);
         parts.add(IntlUtil.makePart(context, realm, "literal", literal, null, source));
      }

      return JSArray.createConstant(context, realm, parts.toArray());
   }

   private static UnmodifiableEconomicMap<NumberFormat.Field, String> initializeFieldToTypeMap() {
      CompilerAsserts.neverPartOfCompilation();
      EconomicMap<NumberFormat.Field, String> map = EconomicMap.create(6);
      map.put(NumberFormat.Field.DECIMAL_SEPARATOR, "decimal");
      map.put(NumberFormat.Field.FRACTION, "fraction");
      map.put(NumberFormat.Field.GROUPING_SEPARATOR, "group");
      map.put(NumberFormat.Field.CURRENCY, "currency");
      map.put(NumberFormat.Field.MEASURE_UNIT, "unit");
      map.put(NumberFormat.Field.EXPONENT_SYMBOL, "exponentSeparator");
      map.put(NumberFormat.Field.EXPONENT_SIGN, "exponentMinusSign");
      map.put(NumberFormat.Field.EXPONENT, "exponentInteger");
      map.put(NumberFormat.Field.COMPACT, "compact");
      map.put(NumberFormat.Field.APPROXIMATELY_SIGN, "approximatelySign");
      return map;
   }

   private static String fieldToType(NumberFormat.Field field) {
      return fieldToTypeMap.get().get(field);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject formatToParts(JSContext context, JSRealm realm, JSDynamicObject numberFormatObj, Object n) {
      JSNumberFormat.InternalState state = getInternalState(numberFormatObj);
      Number x = toInternalNumberRepresentation(JSRuntime.toNumeric(n));
      FormattedValue formattedValue = formattedValue(state, x);
      AttributedCharacterIterator fit = formattedValue.toCharacterIterator();
      String formatted = formattedValue.toString();
      List<JSDynamicObject> resultParts = innerFormatToParts(context, realm, fit, x.doubleValue(), formatted, null, "percent".equals(state.getStyle()));
      return JSArray.createConstant(context, realm, resultParts.toArray());
   }

   static List<JSDynamicObject> innerFormatToParts(
      JSContext context, JSRealm realm, AttributedCharacterIterator iterator, double value, String formattedValue, String unit, boolean stylePercent
   ) {
      List<JSDynamicObject> resultParts = new ArrayList<>();
      int i = iterator.getBeginIndex();

      while (i < iterator.getEndIndex()) {
         iterator.setIndex(i);
         Map<Attribute, Object> attributes = iterator.getAttributes();
         Set<Attribute> attKeySet = attributes.keySet();
         if (!attKeySet.isEmpty()) {
            Iterator run = attKeySet.iterator();
            if (run.hasNext()) {
               Attribute a = (Attribute)run.next();
               if (!(a instanceof NumberFormat.Field)) {
                  throw Errors.shouldNotReachHere();
               }

               String runx = formattedValue.substring(iterator.getRunStart(), iterator.getRunLimit());
               String type;
               if (a == NumberFormat.Field.INTEGER) {
                  if (Double.isNaN(value)) {
                     type = "nan";
                  } else if (Double.isInfinite(value)) {
                     type = "infinity";
                  } else {
                     type = "integer";
                  }
               } else if (a == NumberFormat.Field.SIGN) {
                  type = isPlusSign(runx) ? "plusSign" : "minusSign";
               } else if (a == NumberFormat.Field.PERCENT) {
                  type = stylePercent ? "percentSign" : "unit";
               } else {
                  type = fieldToType((NumberFormat.Field)a);

                  assert type != null : a;
               }

               resultParts.add(IntlUtil.makePart(context, realm, type, runx, unit));
               i = iterator.getRunLimit();
            }
         } else {
            String run = formattedValue.substring(iterator.getRunStart(), iterator.getRunLimit());
            resultParts.add(IntlUtil.makePart(context, realm, "literal", run, unit));
            i = iterator.getRunLimit();
         }
      }

      return resultParts;
   }

   private static boolean isPlusSign(String str) {
      return str.length() == 1 && str.charAt(0) == '+';
   }

   private static Number toInternalNumberRepresentation(Object o) {
      if (o instanceof SafeInteger) {
         return ((SafeInteger)o).doubleValue();
      } else if (o instanceof Double) {
         return Double.isNaN((Double)o) ? Double.NaN : (Double)o;
      } else if (o instanceof Number) {
         return (Number)o;
      } else if (o instanceof BigInt) {
         return ((BigInt)o).bigIntegerValue();
      } else {
         throw Errors.shouldNotReachHere();
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject resolvedOptions(JSContext context, JSRealm realm, JSDynamicObject numberFormatObj) {
      JSNumberFormat.InternalState state = getInternalState(numberFormatObj);
      return state.toResolvedOptionsObject(context, realm);
   }

   public static JSNumberFormat.InternalState getInternalState(JSDynamicObject obj) {
      assert isJSNumberFormat(obj);

      return ((JSNumberFormatObject)obj).getInternalState();
   }

   private static CallTarget createGetFormatCallTarget(JSContext context) {
      return (new JavaScriptRootNode(context.getLanguage(), null, null) {
            private final BranchProfile errorBranch = BranchProfile.create();
            @Node.Child
            private PropertySetNode setBoundObjectNode = PropertySetNode.createSetHidden(JSNumberFormat.BOUND_OBJECT_KEY, context);

            @Override
            public Object execute(VirtualFrame frame) {
               Object[] frameArgs = frame.getArguments();
               Object numberFormatObj = JSArguments.getThisObject(frameArgs);
               if (JSNumberFormat.isJSNumberFormat(numberFormatObj)) {
                  JSNumberFormat.InternalState state = JSNumberFormat.getInternalState((JSDynamicObject)numberFormatObj);
                  if (state == null) {
                     this.errorBranch.enter();
                     throw Errors.createTypeErrorMethodCalledOnNonObjectOrWrongType("format");
                  } else {
                     if (state.boundFormatFunction == null) {
                        JSFunctionData formatFunctionData = context.getOrCreateBuiltinFunctionData(
                           JSContext.BuiltinFunctionKey.NumberFormatFormat, c -> JSNumberFormat.createFormatFunctionData(c)
                        );
                        JSDynamicObject formatFn = JSFunction.create(this.getRealm(), formatFunctionData);
                        this.setBoundObjectNode.setValue(formatFn, numberFormatObj);
                        state.boundFormatFunction = formatFn;
                     }

                     return state.boundFormatFunction;
                  }
               } else {
                  this.errorBranch.enter();
                  throw Errors.createTypeErrorTypeXExpected(JSNumberFormat.CLASS_NAME);
               }
            }
         })
         .getCallTarget();
   }

   private static JSFunctionData createFormatFunctionData(JSContext context) {
      return JSFunctionData.createCallOnly(context, (new JavaScriptRootNode(context.getLanguage(), null, null) {
         @Node.Child
         private PropertyGetNode getBoundObjectNode = PropertyGetNode.createGetHidden(JSNumberFormat.BOUND_OBJECT_KEY, context);
         @Node.Child
         private ToIntlMathematicalValue toIntlMVValueNode = ToIntlMathematicalValue.create(false);

         @Override
         public Object execute(VirtualFrame frame) {
            Object[] arguments = frame.getArguments();
            JSDynamicObject thisObj = (JSDynamicObject)this.getBoundObjectNode.getValue(JSArguments.getFunctionObject(arguments));

            assert JSNumberFormat.isJSNumberFormat(thisObj);

            Object n = JSArguments.getUserArgumentCount(arguments) > 0 ? JSArguments.getUserArgument(arguments, 0) : Undefined.instance;
            return JSNumberFormat.formatMV(thisObj, this.toIntlMVValueNode.executeNumber(n));
         }
      }).getCallTarget(), 1, Strings.EMPTY_STRING);
   }

   private static JSDynamicObject createFormatFunctionGetter(JSRealm realm, JSContext context) {
      JSFunctionData fd = realm.getContext().getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.NumberFormatGetFormat, c -> {
         CallTarget ct = createGetFormatCallTarget(context);
         return JSFunctionData.create(context, ct, ct, 0, GET_FORMAT_NAME, false, false, false, true);
      });
      return JSFunction.create(realm, fd);
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getNumberFormatPrototype();
   }

   public static class BasicInternalState {
      private UnlocalizedNumberFormatter unlocalizedFormatter;
      private Precision precision;
      private Locale javaLocale;
      private String locale;
      private String numberingSystem;
      private int minimumIntegerDigits;
      private Integer minimumFractionDigits;
      private Integer maximumFractionDigits;
      private Integer minimumSignificantDigits;
      private Integer maximumSignificantDigits;
      private String roundingType;

      JSDynamicObject toResolvedOptionsObject(JSContext context, JSRealm realm) {
         JSDynamicObject resolvedOptions = JSOrdinary.create(context, realm);
         this.fillResolvedOptions(context, realm, resolvedOptions);
         return resolvedOptions;
      }

      void fillResolvedOptions(JSContext context, JSRealm realm, JSDynamicObject result) {
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_MINIMUM_INTEGER_DIGITS, this.minimumIntegerDigits, JSAttributes.getDefault());
         if (this.minimumFractionDigits != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_MINIMUM_FRACTION_DIGITS, this.minimumFractionDigits, JSAttributes.getDefault());
         }

         if (this.maximumFractionDigits != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_MAXIMUM_FRACTION_DIGITS, this.maximumFractionDigits, JSAttributes.getDefault());
         }

         if (this.minimumSignificantDigits != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_MINIMUM_SIGNIFICANT_DIGITS, this.minimumSignificantDigits, JSAttributes.getDefault());
         }

         if (this.maximumSignificantDigits != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_MAXIMUM_SIGNIFICANT_DIGITS, this.maximumSignificantDigits, JSAttributes.getDefault());
         }
      }

      @CompilerDirectives.TruffleBoundary
      public void resolveLocaleAndNumberingSystem(JSContext ctx, String[] locales, String numberingSystemOpt) {
         Locale selectedLocale = IntlUtil.selectedLocale(ctx, locales);
         Locale strippedLocale = selectedLocale.stripExtensions();
         if (strippedLocale.toLanguageTag().equals("und")) {
            selectedLocale = ctx.getLocale();
            strippedLocale = selectedLocale.stripExtensions();
         }

         Builder builder = new Builder();
         builder.setLocale(strippedLocale);
         String nuType = selectedLocale.getUnicodeLocaleType("nu");
         if (nuType != null && IntlUtil.isValidNumberingSystem(nuType) && (numberingSystemOpt == null || numberingSystemOpt.equals(nuType))) {
            this.numberingSystem = nuType;
            builder.setUnicodeLocaleKeyword("nu", nuType);
         }

         this.locale = builder.build().toLanguageTag();
         if (numberingSystemOpt != null && IntlUtil.isValidNumberingSystem(numberingSystemOpt)) {
            this.numberingSystem = numberingSystemOpt;
            builder.setUnicodeLocaleKeyword("nu", numberingSystemOpt);
         }

         this.javaLocale = builder.build();
         if (this.numberingSystem == null) {
            this.numberingSystem = IntlUtil.defaultNumberingSystemName(ctx, this.javaLocale);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public void initializeNumberFormatter() {
         UnlocalizedNumberFormatter formatter = NumberFormatter.with();
         formatter = formatter.roundingMode(RoundingMode.HALF_UP);
         formatter = formatter.symbols(NumberingSystem.getInstanceByName(this.numberingSystem));
         formatter = formatter.integerWidth(IntegerWidth.zeroFillTo(this.minimumIntegerDigits));
         if ("significantDigits".equals(this.roundingType)) {
            this.precision = Precision.minMaxSignificantDigits(this.minimumSignificantDigits, this.maximumSignificantDigits);
         } else {
            FractionPrecision fractionPrecision = Precision.minMaxFraction(this.minimumFractionDigits, this.maximumFractionDigits);
            if ("fractionDigits".equals(this.roundingType)) {
               this.precision = fractionPrecision;
            } else {
               boolean morePrecision = "morePrecision".equals(this.roundingType);

               assert morePrecision || "lessPrecision".equals(this.roundingType);

               this.precision = fractionPrecision.withSignificantDigits(
                  this.minimumSignificantDigits,
                  this.maximumSignificantDigits,
                  morePrecision ? NumberFormatter.RoundingPriority.RELAXED : NumberFormatter.RoundingPriority.STRICT
               );
            }
         }

         formatter = formatter.precision(this.precision);
         this.unlocalizedFormatter = formatter;
      }

      public UnlocalizedNumberFormatter getUnlocalizedFormatter() {
         return this.unlocalizedFormatter;
      }

      public Precision getPrecision() {
         return this.precision;
      }

      public Locale getJavaLocale() {
         return this.javaLocale;
      }

      public String getLocale() {
         return this.locale;
      }

      public String getNumberingSystem() {
         return this.numberingSystem;
      }

      public void setMinimumIntegerDigits(int minimumIntegerDigits) {
         this.minimumIntegerDigits = minimumIntegerDigits;
      }

      public int getMinimumIntegerDigits() {
         return this.minimumIntegerDigits;
      }

      public void setMinimumFractionDigits(int minimumFractionDigits) {
         this.minimumFractionDigits = minimumFractionDigits;
      }

      public Integer getMinimumFractionDigits() {
         return this.minimumFractionDigits;
      }

      public void setMaximumFractionDigits(int maximumFractionDigits) {
         this.maximumFractionDigits = maximumFractionDigits;
      }

      public Integer getMaximumFractionDigits() {
         return this.maximumFractionDigits;
      }

      public void setMinimumSignificantDigits(int minimumSignificantDigits) {
         this.minimumSignificantDigits = minimumSignificantDigits;
      }

      public Integer getMinimumSignificantDigits() {
         return this.minimumSignificantDigits;
      }

      public void setMaximumSignificantDigits(int maximumSignificantDigits) {
         this.maximumSignificantDigits = maximumSignificantDigits;
      }

      public Integer getMaximumSignificantDigits() {
         return this.maximumSignificantDigits;
      }

      public void setRoundingType(String roundingType) {
         this.roundingType = roundingType;
      }

      public String getRoundingType() {
         return this.roundingType;
      }
   }

   public static class InternalState extends JSNumberFormat.BasicInternalState {
      private LocalizedNumberFormatter positiveNumberFormatter;
      private LocalizedNumberFormatter negativeNumberFormatter;
      private LocalizedNumberRangeFormatter[] numberRangeFormatter;
      private String style;
      private String currency;
      private String currencyDisplay;
      private String currencySign;
      private String unit;
      private String unitDisplay;
      private Object useGrouping;
      private String notation;
      private String compactDisplay;
      private String signDisplay;
      private String roundingMode;
      private int roundingIncrement;
      private String trailingZeroDisplay;
      JSDynamicObject boundFormatFunction;

      @Override
      void fillResolvedOptions(JSContext context, JSRealm realm, JSDynamicObject result) {
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_LOCALE, Strings.fromJavaString(this.getLocale()), JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(
            context, result, IntlUtil.KEY_NUMBERING_SYSTEM, Strings.fromJavaString(this.getNumberingSystem()), JSAttributes.getDefault()
         );
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_STYLE, Strings.fromJavaString(this.style), JSAttributes.getDefault());
         if (this.currency != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_CURRENCY, Strings.fromJavaString(this.currency), JSAttributes.getDefault());
         }

         if (this.currencyDisplay != null) {
            JSObjectUtil.defineDataProperty(
               context, result, IntlUtil.KEY_CURRENCY_DISPLAY, Strings.fromJavaString(this.currencyDisplay), JSAttributes.getDefault()
            );
         }

         if (this.currencySign != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_CURRENCY_SIGN, Strings.fromJavaString(this.currencySign), JSAttributes.getDefault());
         }

         if (this.unit != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_UNIT, Strings.fromJavaString(this.unit), JSAttributes.getDefault());
         }

         if (this.unitDisplay != null) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_UNIT_DISPLAY, Strings.fromJavaString(this.unitDisplay), JSAttributes.getDefault());
         }

         super.fillResolvedOptions(context, realm, result);
         Object resolvedUseGrouping = this.useGrouping;
         if (this.useGrouping instanceof String && context.getEcmaScriptVersion() < 14) {
            resolvedUseGrouping = true;
         }

         JSObjectUtil.defineDataProperty(
            context,
            result,
            IntlUtil.KEY_USE_GROUPING,
            resolvedUseGrouping instanceof String ? Strings.fromJavaString((String)resolvedUseGrouping) : resolvedUseGrouping,
            JSAttributes.getDefault()
         );
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_NOTATION, Strings.fromJavaString(this.notation), JSAttributes.getDefault());
         if (this.compactDisplay != null) {
            JSObjectUtil.defineDataProperty(
               context, result, IntlUtil.KEY_COMPACT_DISPLAY, Strings.fromJavaString(this.compactDisplay), JSAttributes.getDefault()
            );
         }

         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_SIGN_DISPLAY, Strings.fromJavaString(this.signDisplay), JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_ROUNDING_MODE, Strings.fromJavaString(this.roundingMode), JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_ROUNDING_INCREMENT, this.roundingIncrement, JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(
            context, result, IntlUtil.KEY_TRAILING_ZERO_DISPLAY, Strings.fromJavaString(this.trailingZeroDisplay), JSAttributes.getDefault()
         );
         String roundingType = this.getRoundingType();
         String resolvedRoundingType = !"morePrecision".equals(roundingType) && !"lessPrecision".equals(roundingType) ? "auto" : roundingType;
         JSObjectUtil.defineDataProperty(
            context, result, IntlUtil.KEY_ROUNDING_PRIORITY, Strings.fromJavaString(resolvedRoundingType), JSAttributes.getDefault()
         );
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void initializeNumberFormatter() {
         super.initializeNumberFormatter();
         UnlocalizedNumberFormatter formatter = this.getUnlocalizedFormatter();
         formatter = formatter.notation(JSNumberFormat.notationToICUNotation(this.notation, this.compactDisplay));
         formatter = formatter.grouping(JSNumberFormat.useGroupingToGroupingStrategy(this.useGrouping));
         if ("currency".equals(this.style)) {
            formatter = formatter.unit(Currency.getInstance(this.currency));
            formatter = formatter.unitWidth(JSNumberFormat.currencyDisplayToUnitWidth(this.currencyDisplay));
         } else if ("percent".equals(this.style)) {
            formatter = formatter.unit(MeasureUnit.PERCENT);
            formatter = formatter.scale(Scale.powerOfTen(2));
         } else if ("unit".equals(this.style)) {
            String per = "-per-";
            int index = this.unit.indexOf(per);
            if (index == -1) {
               formatter = formatter.unit(JSNumberFormat.unitToMeasureUnit(this.unit));
            } else {
               String numerator = this.unit.substring(0, index);
               String denominator = this.unit.substring(index + per.length());
               formatter = formatter.unit(JSNumberFormat.unitToMeasureUnit(numerator));
               formatter = formatter.perUnit(JSNumberFormat.unitToMeasureUnit(denominator));
            }

            formatter = formatter.unitWidth(JSNumberFormat.unitDisplayToUnitWidth(this.unitDisplay));
         }

         formatter = formatter.sign(JSNumberFormat.signDisplay(this.signDisplay, "accounting".equals(this.currencySign)));
         formatter = formatter.roundingMode(JSNumberFormat.roundingModeToICURoundingMode(this.roundingMode));
         Precision precision = this.getPrecision();
         if (this.roundingIncrement != 1) {
            BigDecimal increment = BigDecimal.ONE.movePointLeft(this.getMaximumFractionDigits()).multiply(BigDecimal.valueOf((long)this.roundingIncrement));
            precision = Precision.increment(increment);
         }

         if ("stripIfInteger".equals(this.trailingZeroDisplay)) {
            precision = precision.trailingZeroDisplay(NumberFormatter.TrailingZeroDisplay.HIDE_IF_WHOLE);
         }

         formatter = formatter.precision(precision);
         LocalizedNumberRangeFormatter rangeFormatter = NumberRangeFormatter.withLocale(this.getJavaLocale());
         this.numberRangeFormatter = new LocalizedNumberRangeFormatter[3];
         this.positiveNumberFormatter = formatter.locale(this.getJavaLocale());
         if ("halfCeil".equals(this.roundingMode)) {
            UnlocalizedNumberFormatter negativeFormatter = formatter.roundingMode(RoundingMode.HALF_DOWN);
            this.numberRangeFormatter[0] = rangeFormatter.numberFormatterBoth(negativeFormatter);
            this.numberRangeFormatter[1] = rangeFormatter.numberFormatterFirst(negativeFormatter).numberFormatterSecond(formatter);
            this.numberRangeFormatter[2] = rangeFormatter.numberFormatterBoth(formatter);
            this.negativeNumberFormatter = this.positiveNumberFormatter.roundingMode(RoundingMode.HALF_DOWN);
         } else if ("halfFloor".equals(this.roundingMode)) {
            UnlocalizedNumberFormatter negativeFormatter = formatter.roundingMode(RoundingMode.HALF_UP);
            this.numberRangeFormatter[0] = rangeFormatter.numberFormatterBoth(negativeFormatter);
            this.numberRangeFormatter[1] = rangeFormatter.numberFormatterFirst(negativeFormatter).numberFormatterSecond(formatter);
            this.numberRangeFormatter[2] = rangeFormatter.numberFormatterBoth(formatter);
            this.negativeNumberFormatter = this.positiveNumberFormatter.roundingMode(RoundingMode.HALF_UP);
         } else {
            this.negativeNumberFormatter = this.positiveNumberFormatter;
            this.numberRangeFormatter[0] = this.numberRangeFormatter[1] = this.numberRangeFormatter[2] = rangeFormatter.numberFormatterBoth(formatter);
         }
      }

      public String getStyle() {
         return this.style;
      }

      public void setStyle(String style) {
         this.style = style;
      }

      public String getCurrency() {
         return this.currency;
      }

      public void setCurrency(String currency) {
         this.currency = currency;
      }

      public void setCurrencyDisplay(String currencyDisplay) {
         this.currencyDisplay = currencyDisplay;
      }

      public void setCurrencySign(String currencySign) {
         this.currencySign = currencySign;
      }

      public void setUnit(String unit) {
         this.unit = unit;
      }

      public void setUnitDisplay(String unitDisplay) {
         this.unitDisplay = unitDisplay;
      }

      public void setGroupingUsed(Object useGrouping) {
         this.useGrouping = useGrouping;
      }

      public void setNotation(String notation) {
         this.notation = notation;
      }

      public void setCompactDisplay(String compactDisplay) {
         this.compactDisplay = compactDisplay;
      }

      public void setSignDisplay(String signDisplay) {
         this.signDisplay = signDisplay;
      }

      public void setRoundingMode(String roundingMode) {
         this.roundingMode = roundingMode;
      }

      public void setRoundingIncrement(int roundingIncrement) {
         this.roundingIncrement = roundingIncrement;
      }

      public void setTrailingZeroDisplay(String trailingZeroDisplay) {
         this.trailingZeroDisplay = trailingZeroDisplay;
      }

      public LocalizedNumberFormatter getNumberFormatter(boolean forNegativeNumbers) {
         return forNegativeNumbers ? this.negativeNumberFormatter : this.positiveNumberFormatter;
      }

      public LocalizedNumberRangeFormatter getNumberRangeFormatter(boolean firstNegative, boolean secondNegative) {
         return this.numberRangeFormatter[secondNegative ? 0 : (firstNegative ? 1 : 2)];
      }
   }
}
