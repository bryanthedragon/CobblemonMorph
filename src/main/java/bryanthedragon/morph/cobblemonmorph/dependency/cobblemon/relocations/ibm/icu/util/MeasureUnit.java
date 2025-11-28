package com.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.impl.CollectionSet;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.UResource;
import com.cobblemon.mod.relocations.ibm.icu.impl.units.MeasureUnitImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.units.SingleUnitImpl;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MeasureUnit implements Serializable {
   private static final long serialVersionUID = -1839973855554750484L;
   private static final Map<String, Map<String, MeasureUnit>> cache = new HashMap<>();
   private static boolean cacheIsPopulated = false;
   @Deprecated
   protected final String type;
   @Deprecated
   protected final String subType;
   private MeasureUnitImpl measureUnitImpl;
   static final UnicodeSet ASCII = new UnicodeSet(97, 122).freeze();
   static final UnicodeSet ASCII_HYPHEN_DIGITS = new UnicodeSet(45, 45, 48, 57, 97, 122).freeze();
   private static MeasureUnit.Factory UNIT_FACTORY = new MeasureUnit.Factory() {
      @Override
      public MeasureUnit create(String type, String subType) {
         return new MeasureUnit(type, subType);
      }
   };
   static MeasureUnit.Factory CURRENCY_FACTORY = new MeasureUnit.Factory() {
      @Override
      public MeasureUnit create(String unusedType, String subType) {
         return new Currency(subType);
      }
   };
   static MeasureUnit.Factory TIMEUNIT_FACTORY = new MeasureUnit.Factory() {
      @Override
      public MeasureUnit create(String type, String subType) {
         return new TimeUnit(type, subType);
      }
   };
   public static final MeasureUnit G_FORCE = internalGetInstance("acceleration", "g-force");
   public static final MeasureUnit METER_PER_SECOND_SQUARED = internalGetInstance("acceleration", "meter-per-square-second");
   public static final MeasureUnit ARC_MINUTE = internalGetInstance("angle", "arc-minute");
   public static final MeasureUnit ARC_SECOND = internalGetInstance("angle", "arc-second");
   public static final MeasureUnit DEGREE = internalGetInstance("angle", "degree");
   public static final MeasureUnit RADIAN = internalGetInstance("angle", "radian");
   public static final MeasureUnit REVOLUTION_ANGLE = internalGetInstance("angle", "revolution");
   public static final MeasureUnit ACRE = internalGetInstance("area", "acre");
   public static final MeasureUnit DUNAM = internalGetInstance("area", "dunam");
   public static final MeasureUnit HECTARE = internalGetInstance("area", "hectare");
   public static final MeasureUnit SQUARE_CENTIMETER = internalGetInstance("area", "square-centimeter");
   public static final MeasureUnit SQUARE_FOOT = internalGetInstance("area", "square-foot");
   public static final MeasureUnit SQUARE_INCH = internalGetInstance("area", "square-inch");
   public static final MeasureUnit SQUARE_KILOMETER = internalGetInstance("area", "square-kilometer");
   public static final MeasureUnit SQUARE_METER = internalGetInstance("area", "square-meter");
   public static final MeasureUnit SQUARE_MILE = internalGetInstance("area", "square-mile");
   public static final MeasureUnit SQUARE_YARD = internalGetInstance("area", "square-yard");
   public static final MeasureUnit ITEM = internalGetInstance("concentr", "item");
   public static final MeasureUnit KARAT = internalGetInstance("concentr", "karat");
   public static final MeasureUnit MILLIGRAM_OFGLUCOSE_PER_DECILITER = internalGetInstance("concentr", "milligram-ofglucose-per-deciliter");
   public static final MeasureUnit MILLIGRAM_PER_DECILITER = internalGetInstance("concentr", "milligram-per-deciliter");
   public static final MeasureUnit MILLIMOLE_PER_LITER = internalGetInstance("concentr", "millimole-per-liter");
   public static final MeasureUnit MOLE = internalGetInstance("concentr", "mole");
   public static final MeasureUnit PERCENT = internalGetInstance("concentr", "percent");
   public static final MeasureUnit PERMILLE = internalGetInstance("concentr", "permille");
   public static final MeasureUnit PART_PER_MILLION = internalGetInstance("concentr", "permillion");
   public static final MeasureUnit PERMYRIAD = internalGetInstance("concentr", "permyriad");
   public static final MeasureUnit LITER_PER_100KILOMETERS = internalGetInstance("consumption", "liter-per-100-kilometer");
   public static final MeasureUnit LITER_PER_KILOMETER = internalGetInstance("consumption", "liter-per-kilometer");
   public static final MeasureUnit MILE_PER_GALLON = internalGetInstance("consumption", "mile-per-gallon");
   public static final MeasureUnit MILE_PER_GALLON_IMPERIAL = internalGetInstance("consumption", "mile-per-gallon-imperial");
   public static final MeasureUnit BIT = internalGetInstance("digital", "bit");
   public static final MeasureUnit BYTE = internalGetInstance("digital", "byte");
   public static final MeasureUnit GIGABIT = internalGetInstance("digital", "gigabit");
   public static final MeasureUnit GIGABYTE = internalGetInstance("digital", "gigabyte");
   public static final MeasureUnit KILOBIT = internalGetInstance("digital", "kilobit");
   public static final MeasureUnit KILOBYTE = internalGetInstance("digital", "kilobyte");
   public static final MeasureUnit MEGABIT = internalGetInstance("digital", "megabit");
   public static final MeasureUnit MEGABYTE = internalGetInstance("digital", "megabyte");
   public static final MeasureUnit PETABYTE = internalGetInstance("digital", "petabyte");
   public static final MeasureUnit TERABIT = internalGetInstance("digital", "terabit");
   public static final MeasureUnit TERABYTE = internalGetInstance("digital", "terabyte");
   public static final MeasureUnit CENTURY = internalGetInstance("duration", "century");
   public static final TimeUnit DAY = (TimeUnit)internalGetInstance("duration", "day");
   public static final MeasureUnit DAY_PERSON = internalGetInstance("duration", "day-person");
   public static final MeasureUnit DECADE = internalGetInstance("duration", "decade");
   public static final TimeUnit HOUR = (TimeUnit)internalGetInstance("duration", "hour");
   public static final MeasureUnit MICROSECOND = internalGetInstance("duration", "microsecond");
   public static final MeasureUnit MILLISECOND = internalGetInstance("duration", "millisecond");
   public static final TimeUnit MINUTE = (TimeUnit)internalGetInstance("duration", "minute");
   public static final TimeUnit MONTH = (TimeUnit)internalGetInstance("duration", "month");
   public static final MeasureUnit MONTH_PERSON = internalGetInstance("duration", "month-person");
   public static final MeasureUnit NANOSECOND = internalGetInstance("duration", "nanosecond");
   public static final TimeUnit SECOND = (TimeUnit)internalGetInstance("duration", "second");
   public static final TimeUnit WEEK = (TimeUnit)internalGetInstance("duration", "week");
   public static final MeasureUnit WEEK_PERSON = internalGetInstance("duration", "week-person");
   public static final TimeUnit YEAR = (TimeUnit)internalGetInstance("duration", "year");
   public static final MeasureUnit YEAR_PERSON = internalGetInstance("duration", "year-person");
   public static final MeasureUnit AMPERE = internalGetInstance("electric", "ampere");
   public static final MeasureUnit MILLIAMPERE = internalGetInstance("electric", "milliampere");
   public static final MeasureUnit OHM = internalGetInstance("electric", "ohm");
   public static final MeasureUnit VOLT = internalGetInstance("electric", "volt");
   public static final MeasureUnit BRITISH_THERMAL_UNIT = internalGetInstance("energy", "british-thermal-unit");
   public static final MeasureUnit CALORIE = internalGetInstance("energy", "calorie");
   public static final MeasureUnit ELECTRONVOLT = internalGetInstance("energy", "electronvolt");
   public static final MeasureUnit FOODCALORIE = internalGetInstance("energy", "foodcalorie");
   public static final MeasureUnit JOULE = internalGetInstance("energy", "joule");
   public static final MeasureUnit KILOCALORIE = internalGetInstance("energy", "kilocalorie");
   public static final MeasureUnit KILOJOULE = internalGetInstance("energy", "kilojoule");
   public static final MeasureUnit KILOWATT_HOUR = internalGetInstance("energy", "kilowatt-hour");
   public static final MeasureUnit THERM_US = internalGetInstance("energy", "therm-us");
   public static final MeasureUnit KILOWATT_HOUR_PER_100_KILOMETER = internalGetInstance("force", "kilowatt-hour-per-100-kilometer");
   public static final MeasureUnit NEWTON = internalGetInstance("force", "newton");
   public static final MeasureUnit POUND_FORCE = internalGetInstance("force", "pound-force");
   public static final MeasureUnit GIGAHERTZ = internalGetInstance("frequency", "gigahertz");
   public static final MeasureUnit HERTZ = internalGetInstance("frequency", "hertz");
   public static final MeasureUnit KILOHERTZ = internalGetInstance("frequency", "kilohertz");
   public static final MeasureUnit MEGAHERTZ = internalGetInstance("frequency", "megahertz");
   public static final MeasureUnit DOT = internalGetInstance("graphics", "dot");
   public static final MeasureUnit DOT_PER_CENTIMETER = internalGetInstance("graphics", "dot-per-centimeter");
   public static final MeasureUnit DOT_PER_INCH = internalGetInstance("graphics", "dot-per-inch");
   public static final MeasureUnit EM = internalGetInstance("graphics", "em");
   public static final MeasureUnit MEGAPIXEL = internalGetInstance("graphics", "megapixel");
   public static final MeasureUnit PIXEL = internalGetInstance("graphics", "pixel");
   public static final MeasureUnit PIXEL_PER_CENTIMETER = internalGetInstance("graphics", "pixel-per-centimeter");
   public static final MeasureUnit PIXEL_PER_INCH = internalGetInstance("graphics", "pixel-per-inch");
   public static final MeasureUnit ASTRONOMICAL_UNIT = internalGetInstance("length", "astronomical-unit");
   public static final MeasureUnit CENTIMETER = internalGetInstance("length", "centimeter");
   public static final MeasureUnit DECIMETER = internalGetInstance("length", "decimeter");
   public static final MeasureUnit EARTH_RADIUS = internalGetInstance("length", "earth-radius");
   public static final MeasureUnit FATHOM = internalGetInstance("length", "fathom");
   public static final MeasureUnit FOOT = internalGetInstance("length", "foot");
   public static final MeasureUnit FURLONG = internalGetInstance("length", "furlong");
   public static final MeasureUnit INCH = internalGetInstance("length", "inch");
   public static final MeasureUnit KILOMETER = internalGetInstance("length", "kilometer");
   public static final MeasureUnit LIGHT_YEAR = internalGetInstance("length", "light-year");
   public static final MeasureUnit METER = internalGetInstance("length", "meter");
   public static final MeasureUnit MICROMETER = internalGetInstance("length", "micrometer");
   public static final MeasureUnit MILE = internalGetInstance("length", "mile");
   public static final MeasureUnit MILE_SCANDINAVIAN = internalGetInstance("length", "mile-scandinavian");
   public static final MeasureUnit MILLIMETER = internalGetInstance("length", "millimeter");
   public static final MeasureUnit NANOMETER = internalGetInstance("length", "nanometer");
   public static final MeasureUnit NAUTICAL_MILE = internalGetInstance("length", "nautical-mile");
   public static final MeasureUnit PARSEC = internalGetInstance("length", "parsec");
   public static final MeasureUnit PICOMETER = internalGetInstance("length", "picometer");
   public static final MeasureUnit POINT = internalGetInstance("length", "point");
   public static final MeasureUnit SOLAR_RADIUS = internalGetInstance("length", "solar-radius");
   public static final MeasureUnit YARD = internalGetInstance("length", "yard");
   public static final MeasureUnit CANDELA = internalGetInstance("light", "candela");
   public static final MeasureUnit LUMEN = internalGetInstance("light", "lumen");
   public static final MeasureUnit LUX = internalGetInstance("light", "lux");
   public static final MeasureUnit SOLAR_LUMINOSITY = internalGetInstance("light", "solar-luminosity");
   public static final MeasureUnit CARAT = internalGetInstance("mass", "carat");
   public static final MeasureUnit DALTON = internalGetInstance("mass", "dalton");
   public static final MeasureUnit EARTH_MASS = internalGetInstance("mass", "earth-mass");
   public static final MeasureUnit GRAIN = internalGetInstance("mass", "grain");
   public static final MeasureUnit GRAM = internalGetInstance("mass", "gram");
   public static final MeasureUnit KILOGRAM = internalGetInstance("mass", "kilogram");
   public static final MeasureUnit METRIC_TON = internalGetInstance("mass", "metric-ton");
   public static final MeasureUnit MICROGRAM = internalGetInstance("mass", "microgram");
   public static final MeasureUnit MILLIGRAM = internalGetInstance("mass", "milligram");
   public static final MeasureUnit OUNCE = internalGetInstance("mass", "ounce");
   public static final MeasureUnit OUNCE_TROY = internalGetInstance("mass", "ounce-troy");
   public static final MeasureUnit POUND = internalGetInstance("mass", "pound");
   public static final MeasureUnit SOLAR_MASS = internalGetInstance("mass", "solar-mass");
   public static final MeasureUnit STONE = internalGetInstance("mass", "stone");
   public static final MeasureUnit TON = internalGetInstance("mass", "ton");
   public static final MeasureUnit GIGAWATT = internalGetInstance("power", "gigawatt");
   public static final MeasureUnit HORSEPOWER = internalGetInstance("power", "horsepower");
   public static final MeasureUnit KILOWATT = internalGetInstance("power", "kilowatt");
   public static final MeasureUnit MEGAWATT = internalGetInstance("power", "megawatt");
   public static final MeasureUnit MILLIWATT = internalGetInstance("power", "milliwatt");
   public static final MeasureUnit WATT = internalGetInstance("power", "watt");
   public static final MeasureUnit ATMOSPHERE = internalGetInstance("pressure", "atmosphere");
   public static final MeasureUnit BAR = internalGetInstance("pressure", "bar");
   public static final MeasureUnit HECTOPASCAL = internalGetInstance("pressure", "hectopascal");
   public static final MeasureUnit INCH_HG = internalGetInstance("pressure", "inch-ofhg");
   public static final MeasureUnit KILOPASCAL = internalGetInstance("pressure", "kilopascal");
   public static final MeasureUnit MEGAPASCAL = internalGetInstance("pressure", "megapascal");
   public static final MeasureUnit MILLIBAR = internalGetInstance("pressure", "millibar");
   public static final MeasureUnit MILLIMETER_OF_MERCURY = internalGetInstance("pressure", "millimeter-ofhg");
   public static final MeasureUnit PASCAL = internalGetInstance("pressure", "pascal");
   public static final MeasureUnit POUND_PER_SQUARE_INCH = internalGetInstance("pressure", "pound-force-per-square-inch");
   public static final MeasureUnit KILOMETER_PER_HOUR = internalGetInstance("speed", "kilometer-per-hour");
   public static final MeasureUnit KNOT = internalGetInstance("speed", "knot");
   public static final MeasureUnit METER_PER_SECOND = internalGetInstance("speed", "meter-per-second");
   public static final MeasureUnit MILE_PER_HOUR = internalGetInstance("speed", "mile-per-hour");
   public static final MeasureUnit CELSIUS = internalGetInstance("temperature", "celsius");
   public static final MeasureUnit FAHRENHEIT = internalGetInstance("temperature", "fahrenheit");
   public static final MeasureUnit GENERIC_TEMPERATURE = internalGetInstance("temperature", "generic");
   public static final MeasureUnit KELVIN = internalGetInstance("temperature", "kelvin");
   public static final MeasureUnit NEWTON_METER = internalGetInstance("torque", "newton-meter");
   public static final MeasureUnit POUND_FOOT = internalGetInstance("torque", "pound-force-foot");
   public static final MeasureUnit ACRE_FOOT = internalGetInstance("volume", "acre-foot");
   public static final MeasureUnit BARREL = internalGetInstance("volume", "barrel");
   public static final MeasureUnit BUSHEL = internalGetInstance("volume", "bushel");
   public static final MeasureUnit CENTILITER = internalGetInstance("volume", "centiliter");
   public static final MeasureUnit CUBIC_CENTIMETER = internalGetInstance("volume", "cubic-centimeter");
   public static final MeasureUnit CUBIC_FOOT = internalGetInstance("volume", "cubic-foot");
   public static final MeasureUnit CUBIC_INCH = internalGetInstance("volume", "cubic-inch");
   public static final MeasureUnit CUBIC_KILOMETER = internalGetInstance("volume", "cubic-kilometer");
   public static final MeasureUnit CUBIC_METER = internalGetInstance("volume", "cubic-meter");
   public static final MeasureUnit CUBIC_MILE = internalGetInstance("volume", "cubic-mile");
   public static final MeasureUnit CUBIC_YARD = internalGetInstance("volume", "cubic-yard");
   public static final MeasureUnit CUP = internalGetInstance("volume", "cup");
   public static final MeasureUnit CUP_METRIC = internalGetInstance("volume", "cup-metric");
   public static final MeasureUnit DECILITER = internalGetInstance("volume", "deciliter");
   public static final MeasureUnit DESSERT_SPOON = internalGetInstance("volume", "dessert-spoon");
   public static final MeasureUnit DESSERT_SPOON_IMPERIAL = internalGetInstance("volume", "dessert-spoon-imperial");
   public static final MeasureUnit DRAM = internalGetInstance("volume", "dram");
   public static final MeasureUnit DROP = internalGetInstance("volume", "drop");
   public static final MeasureUnit FLUID_OUNCE = internalGetInstance("volume", "fluid-ounce");
   public static final MeasureUnit FLUID_OUNCE_IMPERIAL = internalGetInstance("volume", "fluid-ounce-imperial");
   public static final MeasureUnit GALLON = internalGetInstance("volume", "gallon");
   public static final MeasureUnit GALLON_IMPERIAL = internalGetInstance("volume", "gallon-imperial");
   public static final MeasureUnit HECTOLITER = internalGetInstance("volume", "hectoliter");
   public static final MeasureUnit JIGGER = internalGetInstance("volume", "jigger");
   public static final MeasureUnit LITER = internalGetInstance("volume", "liter");
   public static final MeasureUnit MEGALITER = internalGetInstance("volume", "megaliter");
   public static final MeasureUnit MILLILITER = internalGetInstance("volume", "milliliter");
   public static final MeasureUnit PINCH = internalGetInstance("volume", "pinch");
   public static final MeasureUnit PINT = internalGetInstance("volume", "pint");
   public static final MeasureUnit PINT_METRIC = internalGetInstance("volume", "pint-metric");
   public static final MeasureUnit QUART = internalGetInstance("volume", "quart");
   public static final MeasureUnit QUART_IMPERIAL = internalGetInstance("volume", "quart-imperial");
   public static final MeasureUnit TABLESPOON = internalGetInstance("volume", "tablespoon");
   public static final MeasureUnit TEASPOON = internalGetInstance("volume", "teaspoon");

   @Deprecated
   protected MeasureUnit(String type, String subType) {
      this.type = type;
      this.subType = subType;
   }

   public static MeasureUnit forIdentifier(String identifier) {
      return identifier != null && !identifier.isEmpty() ? MeasureUnitImpl.forIdentifier(identifier).build() : NoUnit.BASE;
   }

   @Deprecated
   public static MeasureUnit fromMeasureUnitImpl(MeasureUnitImpl measureUnitImpl) {
      measureUnitImpl.serialize();
      String identifier = measureUnitImpl.getIdentifier();
      MeasureUnit result = findBySubType(identifier);
      return result != null ? result : new MeasureUnit(measureUnitImpl);
   }

   private MeasureUnit(MeasureUnitImpl measureUnitImpl) {
      this.type = null;
      this.subType = null;
      this.measureUnitImpl = measureUnitImpl.copy();
   }

   public String getType() {
      return this.type;
   }

   public String getSubtype() {
      return this.subType;
   }

   public String getIdentifier() {
      String result = this.measureUnitImpl == null ? this.getSubtype() : this.measureUnitImpl.getIdentifier();
      return result == null ? "" : result;
   }

   public MeasureUnit.Complexity getComplexity() {
      return this.measureUnitImpl == null ? MeasureUnitImpl.forIdentifier(this.getIdentifier()).getComplexity() : this.measureUnitImpl.getComplexity();
   }

   public MeasureUnit withPrefix(MeasureUnit.MeasurePrefix prefix) {
      SingleUnitImpl singleUnit = this.getSingleUnitImpl();
      singleUnit.setPrefix(prefix);
      return singleUnit.build();
   }

   public MeasureUnit.MeasurePrefix getPrefix() {
      return this.getSingleUnitImpl().getPrefix();
   }

   public int getDimensionality() {
      return this.getSingleUnitImpl().getDimensionality();
   }

   public MeasureUnit withDimensionality(int dimensionality) {
      SingleUnitImpl singleUnit = this.getSingleUnitImpl();
      singleUnit.setDimensionality(dimensionality);
      return singleUnit.build();
   }

   public MeasureUnit reciprocal() {
      MeasureUnitImpl measureUnit = this.getCopyOfMeasureUnitImpl();
      measureUnit.takeReciprocal();
      return measureUnit.build();
   }

   public MeasureUnit product(MeasureUnit other) {
      MeasureUnitImpl implCopy = this.getCopyOfMeasureUnitImpl();
      if (other == null) {
         return implCopy.build();
      } else {
         MeasureUnitImpl otherImplRef = other.getMaybeReferenceOfMeasureUnitImpl();
         if (implCopy.getComplexity() != MeasureUnit.Complexity.MIXED && otherImplRef.getComplexity() != MeasureUnit.Complexity.MIXED) {
            for (SingleUnitImpl singleUnit : otherImplRef.getSingleUnits()) {
               implCopy.appendSingleUnit(singleUnit);
            }

            return implCopy.build();
         } else {
            throw new UnsupportedOperationException();
         }
      }
   }

   public List<MeasureUnit> splitToSingleUnits() {
      ArrayList<SingleUnitImpl> singleUnits = this.getMaybeReferenceOfMeasureUnitImpl().getSingleUnits();
      List<MeasureUnit> result = new ArrayList<>(singleUnits.size());

      for (SingleUnitImpl singleUnit : singleUnits) {
         result.add(singleUnit.build());
      }

      return result;
   }

   @Override
   public int hashCode() {
      return 31 * this.type.hashCode() + this.subType.hashCode();
   }

   @Override
   public boolean equals(Object rhs) {
      if (rhs == this) {
         return true;
      } else {
         return !(rhs instanceof MeasureUnit) ? false : this.getIdentifier().equals(((MeasureUnit)rhs).getIdentifier());
      }
   }

   @Override
   public String toString() {
      String result = this.measureUnitImpl == null ? this.type + "-" + this.subType : this.measureUnitImpl.getIdentifier();
      return result == null ? "" : result;
   }

   public static Set<String> getAvailableTypes() {
      populateCache();
      return Collections.unmodifiableSet(cache.keySet());
   }

   public static Set<MeasureUnit> getAvailable(String type) {
      populateCache();
      Map<String, MeasureUnit> units = cache.get(type);
      return units == null ? Collections.emptySet() : Collections.unmodifiableSet(new CollectionSet<>(units.values()));
   }

   public static synchronized Set<MeasureUnit> getAvailable() {
      Set<MeasureUnit> result = new HashSet<>();

      for (String type : new HashSet<>(getAvailableTypes())) {
         for (MeasureUnit unit : getAvailable(type)) {
            result.add(unit);
         }
      }

      return Collections.unmodifiableSet(result);
   }

   @Deprecated
   public static MeasureUnit internalGetInstance(String type, String subType) {
      if (type != null && subType != null) {
         if ("currency".equals(type) || ASCII.containsAll(type) && ASCII_HYPHEN_DIGITS.containsAll(subType)) {
            MeasureUnit.Factory factory;
            if ("currency".equals(type)) {
               factory = CURRENCY_FACTORY;
            } else if ("duration".equals(type)) {
               factory = TIMEUNIT_FACTORY;
            } else {
               factory = UNIT_FACTORY;
            }

            return addUnit(type, subType, factory);
         } else {
            throw new IllegalArgumentException("The type or subType are invalid.");
         }
      } else {
         throw new NullPointerException("Type and subType must be non-null");
      }
   }

   @Deprecated
   public static MeasureUnit findBySubType(String subType) {
      populateCache();

      for (Map<String, MeasureUnit> unitsForType : cache.values()) {
         if (unitsForType.containsKey(subType)) {
            return unitsForType.get(subType);
         }
      }

      return null;
   }

   private static synchronized void populateCache() {
      if (!cacheIsPopulated) {
         cacheIsPopulated = true;
         ICUResourceBundle rb1 = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/unit", "en");
         rb1.getAllItemsWithFallback("units", new MeasureUnit.MeasureUnitSink());
         ICUResourceBundle rb2 = (ICUResourceBundle)UResourceBundle.getBundleInstance(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "currencyNumericCodes", ICUResourceBundle.ICU_DATA_CLASS_LOADER
         );
         rb2.getAllItemsWithFallback("codeMap", new MeasureUnit.CurrencyNumericCodeSink());
      }
   }

   @Deprecated
   protected static synchronized MeasureUnit addUnit(String type, String unitName, MeasureUnit.Factory factory) {
      Map<String, MeasureUnit> tmp = cache.get(type);
      if (tmp == null) {
         cache.put(type, tmp = new HashMap<>());
      } else {
         type = tmp.entrySet().iterator().next().getValue().type;
      }

      MeasureUnit unit = tmp.get(unitName);
      if (unit == null) {
         tmp.put(unitName, unit = factory.create(type, unitName));
      }

      return unit;
   }

   private Object writeReplace() throws ObjectStreamException {
      return new MeasureUnit.MeasureUnitProxy(this.type, this.subType);
   }

   private SingleUnitImpl getSingleUnitImpl() {
      return this.measureUnitImpl == null ? MeasureUnitImpl.forIdentifier(this.getIdentifier()).getSingleUnitImpl() : this.measureUnitImpl.getSingleUnitImpl();
   }

   @Deprecated
   public MeasureUnitImpl getCopyOfMeasureUnitImpl() {
      return this.measureUnitImpl == null ? MeasureUnitImpl.forIdentifier(this.getIdentifier()) : this.measureUnitImpl.copy();
   }

   private MeasureUnitImpl getMaybeReferenceOfMeasureUnitImpl() {
      return this.measureUnitImpl == null ? MeasureUnitImpl.forIdentifier(this.getIdentifier()) : this.measureUnitImpl;
   }

   public static enum Complexity {
      SINGLE,
      COMPOUND,
      MIXED;
   }

   private static final class CurrencyNumericCodeSink extends UResource.Sink {
      private CurrencyNumericCodeSink() {
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table codesTable = value.getTable();

         for (int i1 = 0; codesTable.getKeyAndValue(i1, key, value); i1++) {
            MeasureUnit.internalGetInstance("currency", key.toString());
         }
      }
   }

   @Deprecated
   protected interface Factory {
      @Deprecated
      MeasureUnit create(String var1, String var2);
   }

   public static enum MeasurePrefix {
      YOTTA(24, "yotta", 10),
      ZETTA(21, "zetta", 10),
      EXA(18, "exa", 10),
      PETA(15, "peta", 10),
      TERA(12, "tera", 10),
      GIGA(9, "giga", 10),
      MEGA(6, "mega", 10),
      KILO(3, "kilo", 10),
      HECTO(2, "hecto", 10),
      DEKA(1, "deka", 10),
      ONE(0, "", 10),
      DECI(-1, "deci", 10),
      CENTI(-2, "centi", 10),
      MILLI(-3, "milli", 10),
      MICRO(-6, "micro", 10),
      NANO(-9, "nano", 10),
      PICO(-12, "pico", 10),
      FEMTO(-15, "femto", 10),
      ATTO(-18, "atto", 10),
      ZEPTO(-21, "zepto", 10),
      YOCTO(-24, "yocto", 10),
      KIBI(1, "kibi", 1024),
      MEBI(2, "mebi", 1024),
      GIBI(3, "gibi", 1024),
      TEBI(4, "tebi", 1024),
      PEBI(5, "pebi", 1024),
      EXBI(6, "exbi", 1024),
      ZEBI(7, "zebi", 1024),
      YOBI(8, "yobi", 1024);

      private final int base;
      private final int power;
      private final String identifier;

      private MeasurePrefix(int power, String identifier, int base) {
         this.base = base;
         this.power = power;
         this.identifier = identifier;
      }

      @Deprecated
      public String getIdentifier() {
         return this.identifier;
      }

      public int getBase() {
         return this.base;
      }

      public int getPower() {
         return this.power;
      }
   }

   static final class MeasureUnitProxy implements Externalizable {
      private static final long serialVersionUID = -3910681415330989598L;
      private String type;
      private String subType;

      public MeasureUnitProxy(String type, String subType) {
         this.type = type;
         this.subType = subType;
      }

      public MeasureUnitProxy() {
      }

      @Override
      public void writeExternal(ObjectOutput out) throws IOException {
         out.writeByte(0);
         out.writeUTF(this.type);
         out.writeUTF(this.subType);
         out.writeShort(0);
      }

      @Override
      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         in.readByte();
         this.type = in.readUTF();
         this.subType = in.readUTF();
         int extra = in.readShort();
         if (extra > 0) {
            byte[] extraBytes = new byte[extra];
            in.read(extraBytes, 0, extra);
         }
      }

      private Object readResolve() throws ObjectStreamException {
         return MeasureUnit.internalGetInstance(this.type, this.subType);
      }
   }

   private static final class MeasureUnitSink extends UResource.Sink {
      private MeasureUnitSink() {
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table unitTypesTable = value.getTable();

         for (int i2 = 0; unitTypesTable.getKeyAndValue(i2, key, value); i2++) {
            if (!key.contentEquals("compound") && !key.contentEquals("coordinate")) {
               String unitType = key.toString();
               UResource.Table unitNamesTable = value.getTable();

               for (int i3 = 0; unitNamesTable.getKeyAndValue(i3, key, value); i3++) {
                  String unitName = key.toString();
                  MeasureUnit.internalGetInstance(unitType, unitName);
               }
            }
         }
      }
   }
}
