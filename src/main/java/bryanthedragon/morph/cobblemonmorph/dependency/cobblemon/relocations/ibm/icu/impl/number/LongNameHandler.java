package com.cobblemon.mod.relocations.ibm.icu.impl.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.CurrencyData;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.PatternProps;
import com.cobblemon.mod.relocations.ibm.icu.impl.SimpleFormatterImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import com.cobblemon.mod.relocations.ibm.icu.impl.UResource;
import com.cobblemon.mod.relocations.ibm.icu.impl.units.MeasureUnitImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.units.SingleUnitImpl;
import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.number.NumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.text.NumberFormat;
import com.cobblemon.mod.relocations.ibm.icu.text.PluralRules;
import com.cobblemon.mod.relocations.ibm.icu.util.Currency;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUException;
import com.cobblemon.mod.relocations.ibm.icu.util.MeasureUnit;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Map.Entry;

public class LongNameHandler implements MicroPropsGenerator, ModifierStore, LongNameMultiplexer.ParentlessMicroPropsGenerator {
   private static int i = 0;
   private static final int DNAM_INDEX = StandardPlural.COUNT + i++;
   private static final int PER_INDEX = StandardPlural.COUNT + i++;
   private static final int GENDER_INDEX = StandardPlural.COUNT + i++;
   static final int ARRAY_LENGTH = StandardPlural.COUNT + i++;
   private final Map<StandardPlural, SimpleModifier> modifiers;
   private final PluralRules rules;
   private final MicroPropsGenerator parent;
   private String gender = "";

   private static int getIndex(String pluralKeyword) {
      if (pluralKeyword.equals("dnam")) {
         return DNAM_INDEX;
      } else if (pluralKeyword.equals("per")) {
         return PER_INDEX;
      } else {
         return pluralKeyword.equals("gender") ? GENDER_INDEX : StandardPlural.fromString(pluralKeyword).ordinal();
      }
   }

   static String getWithPlural(String[] strings, StandardPlural plural) {
      String result = strings[plural.ordinal()];
      if (result == null) {
         result = strings[StandardPlural.OTHER.ordinal()];
      }

      if (result == null) {
         throw new ICUException("Could not find data in 'other' plural variant");
      } else {
         return result;
      }
   }

   private static LongNameHandler.ExtractCorePatternResult extractCorePattern(String pattern) {
      LongNameHandler.ExtractCorePatternResult result = new LongNameHandler.ExtractCorePatternResult();
      result.joinerChar = 0;
      int len = pattern.length();
      if (pattern.startsWith("{0}")) {
         result.placeholderPosition = LongNameHandler.PlaceholderPosition.BEGINNING;
         if (len > 3 && Character.isSpaceChar(pattern.charAt(3))) {
            result.joinerChar = pattern.charAt(3);
            result.coreUnit = pattern.substring(4);
         } else {
            result.coreUnit = pattern.substring(3);
         }
      } else if (pattern.endsWith("{0}")) {
         result.placeholderPosition = LongNameHandler.PlaceholderPosition.END;
         if (Character.isSpaceChar(pattern.charAt(len - 4))) {
            result.coreUnit = pattern.substring(0, len - 4);
            result.joinerChar = pattern.charAt(len - 4);
         } else {
            result.coreUnit = pattern.substring(0, len - 3);
         }
      } else if (pattern.indexOf("{0}", 1) == -1) {
         result.placeholderPosition = LongNameHandler.PlaceholderPosition.NONE;
         result.coreUnit = pattern;
      } else {
         result.placeholderPosition = LongNameHandler.PlaceholderPosition.MIDDLE;
         result.coreUnit = pattern;
      }

      return result;
   }

   private static String getGenderForBuiltin(ULocale locale, MeasureUnit builtinUnit) {
      ICUResourceBundle unitsBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(
         "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/unit", locale
      );
      StringBuilder key = new StringBuilder();
      key.append("units/");
      key.append(builtinUnit.getType());
      key.append("/");
      if (builtinUnit.getSubtype() != null && builtinUnit.getSubtype().endsWith("-person")) {
         key.append(builtinUnit.getSubtype(), 0, builtinUnit.getSubtype().length() - 7);
      } else {
         key.append(builtinUnit.getSubtype());
      }

      key.append("/gender");

      try {
         return unitsBundle.getWithFallback(key.toString()).getString();
      } catch (MissingResourceException var5) {
         return "";
      }
   }

   static void getInflectedMeasureData(String subKey, ULocale locale, NumberFormatter.UnitWidth width, String gender, String caseVariant, String[] outArray) {
      LongNameHandler.InflectedPluralSink sink = new LongNameHandler.InflectedPluralSink(gender, caseVariant, outArray);
      ICUResourceBundle unitsBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(
         "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/unit", locale
      );
      StringBuilder key = new StringBuilder();
      key.append("units");
      if (width == NumberFormatter.UnitWidth.NARROW) {
         key.append("Narrow");
      } else if (width == NumberFormatter.UnitWidth.SHORT) {
         key.append("Short");
      }

      key.append("/");
      key.append(subKey);

      try {
         unitsBundle.getAllItemsWithFallback(key.toString(), sink);
         if (width == NumberFormatter.UnitWidth.SHORT) {
            return;
         }
      } catch (MissingResourceException var10) {
      }

      unitsBundle.getAllItemsWithFallback(key.toString(), sink);
   }

   static void getMeasureData(ULocale locale, MeasureUnit unit, NumberFormatter.UnitWidth width, String unitDisplayCase, String[] outArray) {
      LongNameHandler.PluralTableSink sink = new LongNameHandler.PluralTableSink(outArray);
      ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/unit", locale);
      StringBuilder subKey = new StringBuilder();
      subKey.append("/");
      subKey.append(unit.getType());
      subKey.append("/");
      String unitSubType = unit.getSubtype();
      ICUResourceBundle metadataResource = (ICUResourceBundle)UResourceBundle.getBundleInstance(
         "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "metadata"
      );
      LongNameHandler.AliasSink aliasSink = new LongNameHandler.AliasSink();
      metadataResource.getAllItemsWithFallbackNoFail("alias/unit/" + unitSubType, aliasSink);
      if (aliasSink.replacement != null) {
         unitSubType = aliasSink.replacement;
      }

      if (unitSubType != null && unitSubType.endsWith("-person")) {
         subKey.append(unitSubType, 0, unitSubType.length() - 7);
      } else {
         subKey.append(unitSubType);
      }

      if (width != NumberFormatter.UnitWidth.FULL_NAME) {
         StringBuilder genderKey = new StringBuilder();
         genderKey.append("units");
         genderKey.append((CharSequence)subKey);
         genderKey.append("/gender");

         try {
            outArray[GENDER_INDEX] = resource.getWithFallback(genderKey.toString()).getString();
         } catch (MissingResourceException var16) {
         }
      }

      StringBuilder key = new StringBuilder();
      key.append("units");
      if (width == NumberFormatter.UnitWidth.NARROW) {
         key.append("Narrow");
      } else if (width == NumberFormatter.UnitWidth.SHORT) {
         key.append("Short");
      }

      key.append((CharSequence)subKey);
      if (width == NumberFormatter.UnitWidth.FULL_NAME && unitDisplayCase != null && !unitDisplayCase.isEmpty()) {
         StringBuilder caseKey = new StringBuilder();
         caseKey.append((CharSequence)key);
         caseKey.append("/case/");
         caseKey.append(unitDisplayCase);

         try {
            resource.getAllItemsWithFallback(caseKey.toString(), sink);
         } catch (MissingResourceException var15) {
         }
      }

      try {
         resource.getAllItemsWithFallback(key.toString(), sink);
      } catch (MissingResourceException var14) {
         throw new IllegalArgumentException("No data for unit " + unit + ", width " + width, var14);
      }
   }

   private static void getCurrencyLongNameData(ULocale locale, Currency currency, String[] outArray) {
      Map<String, String> data = CurrencyData.provider.getInstance(locale, true).getUnitPatterns();

      for (Entry<String, String> e : data.entrySet()) {
         String pluralKeyword = e.getKey();
         int index = getIndex(pluralKeyword);
         String longName = currency.getName(locale, 2, pluralKeyword, null);
         String simpleFormat = e.getValue();
         simpleFormat = simpleFormat.replace("{1}", longName);
         outArray[index] = simpleFormat;
      }
   }

   private static String getCompoundValue(String compoundKey, ULocale locale, NumberFormatter.UnitWidth width) {
      ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/unit", locale);
      StringBuilder key = new StringBuilder();
      key.append("units");
      if (width == NumberFormatter.UnitWidth.NARROW) {
         key.append("Narrow");
      } else if (width == NumberFormatter.UnitWidth.SHORT) {
         key.append("Short");
      }

      key.append("/compound/");
      key.append(compoundKey);

      try {
         return resource.getStringWithFallback(key.toString());
      } catch (MissingResourceException var7) {
         if (width == NumberFormatter.UnitWidth.SHORT) {
            return "";
         } else {
            try {
               return resource.getStringWithFallback(key.toString());
            } catch (MissingResourceException var6) {
               return "";
            }
         }
      }
   }

   private static String getDeriveCompoundRule(ULocale locale, String feature, String structure) {
      ICUResourceBundle derivationsBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(
         "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "grammaticalFeatures"
      );
      derivationsBundle = (ICUResourceBundle)derivationsBundle.get("grammaticalData");
      derivationsBundle = (ICUResourceBundle)derivationsBundle.get("derivations");

      ICUResourceBundle stackBundle;
      try {
         stackBundle = (ICUResourceBundle)derivationsBundle.get(locale.getLanguage());
      } catch (MissingResourceException var6) {
         stackBundle = (ICUResourceBundle)derivationsBundle.get("root");
      }

      stackBundle = (ICUResourceBundle)stackBundle.get("compound");
      stackBundle = (ICUResourceBundle)stackBundle.get(feature);
      return stackBundle.getString(structure);
   }

   private static String getDerivedGender(ULocale locale, String structure, String[] data0, String[] data1) {
      String val = getDeriveCompoundRule(locale, "gender", structure);
      if (val.length() == 1) {
         switch (val.charAt(0)) {
            case '0':
               return data0[GENDER_INDEX];
            case '1':
               if (data1 == null) {
                  return null;
               }

               return data1[GENDER_INDEX];
         }
      }

      return val;
   }

   private static String calculateGenderForUnit(ULocale locale, MeasureUnit unit) {
      MeasureUnitImpl mui = unit.getCopyOfMeasureUnitImpl();
      ArrayList<SingleUnitImpl> singleUnits = mui.getSingleUnits();
      int singleUnitIndex = 0;
      if (mui.getComplexity() == MeasureUnit.Complexity.COMPOUND) {
         int startSlice = 0;
         int endSlice = singleUnits.size() - 1;

         assert endSlice > 0 : "COMPOUND units have more than one single unit";

         if (singleUnits.get(endSlice).getDimensionality() < 0) {
            String perRule = getDeriveCompoundRule(locale, "gender", "per");
            if (perRule.length() != 1) {
               return perRule;
            }

            if (perRule.charAt(0) == '1') {
               while (singleUnits.get(startSlice).getDimensionality() >= 0) {
                  startSlice++;
               }
            } else {
               while (endSlice >= 0 && singleUnits.get(endSlice).getDimensionality() < 0) {
                  endSlice--;
               }

               if (endSlice < 0) {
                  return "";
               }
            }
         }

         if (endSlice > startSlice) {
            String timesRule = getDeriveCompoundRule(locale, "gender", "times");
            if (timesRule.length() != 1) {
               return timesRule;
            }

            if (timesRule.charAt(0) == '0') {
               endSlice = startSlice;
            } else {
               startSlice = endSlice;
            }
         }

         assert startSlice == endSlice;

         singleUnitIndex = startSlice;
      } else {
         if (mui.getComplexity() == MeasureUnit.Complexity.MIXED) {
            throw new ICUException("calculateGenderForUnit does not support MIXED units");
         }

         assert mui.getComplexity() == MeasureUnit.Complexity.SINGLE;

         assert singleUnits.size() == 1;
      }

      SingleUnitImpl singleUnit = singleUnits.get(singleUnitIndex);
      if (Math.abs(singleUnit.getDimensionality()) != 1) {
         String powerRule = getDeriveCompoundRule(locale, "gender", "power");
         if (powerRule.length() != 1) {
            return powerRule;
         }
      }

      if (Math.abs(singleUnit.getDimensionality()) != 1) {
         String prefixRule = getDeriveCompoundRule(locale, "gender", "prefix");
         if (prefixRule.length() != 1) {
            return prefixRule;
         }
      }

      return getGenderForBuiltin(locale, MeasureUnit.forIdentifier(singleUnit.getSimpleUnitID()));
   }

   private static void maybeCalculateGender(ULocale locale, MeasureUnit unit, String[] outArray) {
      if (outArray[GENDER_INDEX] == null) {
         String meterGender = getGenderForBuiltin(locale, MeasureUnit.METER);
         if (meterGender.isEmpty()) {
            return;
         }

         outArray[GENDER_INDEX] = calculateGenderForUnit(locale, unit);
      }
   }

   private LongNameHandler(Map<StandardPlural, SimpleModifier> modifiers, PluralRules rules, MicroPropsGenerator parent) {
      this.modifiers = modifiers;
      this.rules = rules;
      this.parent = parent;
   }

   public static String getUnitDisplayName(ULocale locale, MeasureUnit unit, NumberFormatter.UnitWidth width) {
      String[] measureData = new String[ARRAY_LENGTH];
      getMeasureData(locale, unit, width, "", measureData);
      return measureData[DNAM_INDEX];
   }

   public static LongNameHandler forCurrencyLongNames(ULocale locale, Currency currency, PluralRules rules, MicroPropsGenerator parent) {
      String[] simpleFormats = new String[ARRAY_LENGTH];
      getCurrencyLongNameData(locale, currency, simpleFormats);
      Map<StandardPlural, SimpleModifier> modifiers = new EnumMap<>(StandardPlural.class);
      LongNameHandler result = new LongNameHandler(modifiers, rules, parent);
      result.simpleFormatsToModifiers(simpleFormats, NumberFormat.Field.CURRENCY);
      return result;
   }

   public static LongNameHandler forMeasureUnit(
      ULocale locale, MeasureUnit unit, NumberFormatter.UnitWidth width, String unitDisplayCase, PluralRules rules, MicroPropsGenerator parent
   ) {
      if (unit.getType() != null) {
         String[] simpleFormats = new String[ARRAY_LENGTH];
         getMeasureData(locale, unit, width, unitDisplayCase, simpleFormats);
         maybeCalculateGender(locale, unit, simpleFormats);
         Map<StandardPlural, SimpleModifier> modifiers = new EnumMap<>(StandardPlural.class);
         LongNameHandler result = new LongNameHandler(modifiers, rules, parent);
         result.simpleFormatsToModifiers(simpleFormats, NumberFormat.Field.MEASURE_UNIT);
         if (simpleFormats[GENDER_INDEX] != null) {
            result.gender = simpleFormats[GENDER_INDEX];
         }

         return result;
      } else {
         assert unit.getComplexity() != MeasureUnit.Complexity.MIXED : "Mixed units not supported by LongNameHandler: use MixedUnitLongNameHandler";

         return forArbitraryUnit(locale, unit, width, unitDisplayCase, rules, parent);
      }
   }

   private static LongNameHandler forArbitraryUnit(
      ULocale loc, MeasureUnit unit, NumberFormatter.UnitWidth width, String unitDisplayCase, PluralRules rules, MicroPropsGenerator parent
   ) {
      MeasureUnitImpl fullUnit = unit.getCopyOfMeasureUnitImpl();
      unit = null;
      MeasureUnit perUnit = null;

      for (SingleUnitImpl subUnit : fullUnit.getSingleUnits()) {
         if (subUnit.getDimensionality() > 0) {
            if (unit == null) {
               unit = subUnit.build();
            } else {
               unit = unit.product(subUnit.build());
            }
         } else {
            subUnit.setDimensionality(subUnit.getDimensionality() * -1);
            if (perUnit == null) {
               perUnit = subUnit.build();
            } else {
               perUnit = perUnit.product(subUnit.build());
            }
         }
      }

      MeasureUnitImpl unitImpl = unit == null ? null : unit.getCopyOfMeasureUnitImpl();
      MeasureUnitImpl perUnitImpl = perUnit == null ? null : perUnit.getCopyOfMeasureUnitImpl();
      LongNameHandler.DerivedComponents derivedPerCases = new LongNameHandler.DerivedComponents(loc, "case", "per");
      String[] numeratorUnitData = new String[ARRAY_LENGTH];
      processPatternTimes(unitImpl, loc, width, derivedPerCases.value0(unitDisplayCase), numeratorUnitData);
      String[] denominatorUnitData = new String[ARRAY_LENGTH];
      processPatternTimes(perUnitImpl, loc, width, derivedPerCases.value1(unitDisplayCase), denominatorUnitData);
      String perUnitPattern = null;
      if (denominatorUnitData[PER_INDEX] != null) {
         perUnitPattern = denominatorUnitData[PER_INDEX];
      } else {
         StringBuilder sb = new StringBuilder();
         String rawPerUnitFormat = getCompoundValue("per", loc, width);
         String perPatternFormatter = SimpleFormatterImpl.compileToStringMinMaxArguments(rawPerUnitFormat, sb, 2, 2);
         String rawDenominatorFormat = getWithPlural(denominatorUnitData, StandardPlural.ONE);
         String denominatorFormatter = SimpleFormatterImpl.compileToStringMinMaxArguments(rawDenominatorFormat, sb, 0, 1);
         String denominatorString = PatternProps.trimSpaceChar(SimpleFormatterImpl.getTextWithNoArguments(denominatorFormatter));
         perUnitPattern = SimpleFormatterImpl.formatCompiledPattern(perPatternFormatter, "{0}", denominatorString);
      }

      Map<StandardPlural, SimpleModifier> modifiers = new EnumMap<>(StandardPlural.class);
      LongNameHandler result = new LongNameHandler(modifiers, rules, parent);
      if (perUnitPattern.length() == 0) {
         result.simpleFormatsToModifiers(numeratorUnitData, NumberFormat.Field.MEASURE_UNIT);
      } else {
         result.multiSimpleFormatsToModifiers(numeratorUnitData, perUnitPattern, NumberFormat.Field.MEASURE_UNIT);
      }

      result.gender = getDerivedGender(loc, "per", numeratorUnitData, denominatorUnitData);
      return result;
   }

   private static void processPatternTimes(MeasureUnitImpl productUnit, ULocale loc, NumberFormatter.UnitWidth width, String caseVariant, String[] outArray) {
      assert outArray[StandardPlural.OTHER.ordinal()] == null : "outArray must have only null values!";

      assert outArray[PER_INDEX] == null : "outArray must have only null values!";

      if (productUnit == null) {
         outArray[StandardPlural.OTHER.ordinal()] = "";
         outArray[PER_INDEX] = "";
      } else if (productUnit.getComplexity() == MeasureUnit.Complexity.MIXED) {
         throw new UnsupportedOperationException("Mixed units not supported by LongNameHandler");
      } else {
         if (productUnit.getIdentifier() == null) {
            productUnit.serialize();
         }

         if (productUnit.getIdentifier().length() != 0) {
            MeasureUnit simpleUnit = MeasureUnit.findBySubType(productUnit.getIdentifier());
            if (simpleUnit != null) {
               getMeasureData(loc, simpleUnit, width, caseVariant, outArray);
               maybeCalculateGender(loc, simpleUnit, outArray);
            } else {
               String timesPattern = getCompoundValue("times", loc, width);
               StringBuilder sb = new StringBuilder();
               String timesPatternFormatter = SimpleFormatterImpl.compileToStringMinMaxArguments(timesPattern, sb, 2, 2);
               LongNameHandler.PlaceholderPosition[] globalPlaceholder = new LongNameHandler.PlaceholderPosition[ARRAY_LENGTH];
               char globalJoinerChar = 0;

               for (StandardPlural plural : StandardPlural.values()) {
                  int pluralIndex = plural.ordinal();
                  if (plural == StandardPlural.OTHER) {
                     outArray[pluralIndex] = "";
                  } else {
                     outArray[pluralIndex] = null;
                  }

                  globalPlaceholder[pluralIndex] = null;
               }

               String pluralCategory = null;
               LongNameHandler.DerivedComponents derivedTimesPlurals = new LongNameHandler.DerivedComponents(loc, "plural", "times");
               LongNameHandler.DerivedComponents derivedTimesCases = new LongNameHandler.DerivedComponents(loc, "case", "times");
               LongNameHandler.DerivedComponents derivedPowerCases = new LongNameHandler.DerivedComponents(loc, "case", "power");
               ArrayList<SingleUnitImpl> singleUnits = productUnit.getSingleUnits();

               for (int singleUnitIndex = 0; singleUnitIndex < singleUnits.size(); singleUnitIndex++) {
                  SingleUnitImpl singleUnit = singleUnits.get(singleUnitIndex);
                  String singlePluralCategory;
                  String singleCaseVariant;
                  if (singleUnitIndex < singleUnits.size() - 1) {
                     singlePluralCategory = derivedTimesPlurals.value0(pluralCategory);
                     singleCaseVariant = derivedTimesCases.value0(caseVariant);
                     pluralCategory = derivedTimesPlurals.value1(pluralCategory);
                     caseVariant = derivedTimesCases.value1(caseVariant);
                  } else {
                     singlePluralCategory = derivedTimesPlurals.value1(pluralCategory);
                     singleCaseVariant = derivedTimesCases.value1(caseVariant);
                  }

                  simpleUnit = MeasureUnit.findBySubType(singleUnit.getSimpleUnitID());
                  if (simpleUnit == null) {
                     throw new UnsupportedOperationException("Unsupported sinlgeUnit: " + singleUnit.getSimpleUnitID());
                  }

                  String gender = getGenderForBuiltin(loc, simpleUnit);

                  assert singleUnit.getDimensionality() > 0;

                  int dimensionality = singleUnit.getDimensionality();
                  String[] dimensionalityPrefixPatterns = new String[ARRAY_LENGTH];
                  if (dimensionality != 1) {
                     StringBuilder dimensionalityKey = new StringBuilder("compound/power");
                     dimensionalityKey.append(dimensionality);

                     try {
                        getInflectedMeasureData(dimensionalityKey.toString(), loc, width, gender, singleCaseVariant, dimensionalityPrefixPatterns);
                     } catch (MissingResourceException var34) {
                        if (dimensionality > 3) {
                           throw new UnsupportedOperationException("powerN not supported for N > 3: " + productUnit.getIdentifier());
                        }

                        throw var34;
                     }

                     singleCaseVariant = derivedPowerCases.value0(singleCaseVariant);
                     singleUnit.setDimensionality(1);
                  }

                  MeasureUnit.MeasurePrefix prefix = singleUnit.getPrefix();
                  String prefixPattern = "";
                  if (prefix != MeasureUnit.MeasurePrefix.ONE) {
                     StringBuilder prefixKey = new StringBuilder();
                     prefixKey.append(prefix.getBase());
                     prefixKey.append('p');
                     prefixKey.append(prefix.getPower());
                     prefixPattern = getCompoundValue(prefixKey.toString(), loc, width);
                     singleUnit.setPrefix(MeasureUnit.MeasurePrefix.ONE);
                  }

                  String[] singleUnitArray = new String[ARRAY_LENGTH];

                  assert singleUnit.build().getIdentifier().equals(singleUnit.getSimpleUnitID()) : "Should be equal: singleUnit.build().getIdentifier() produced "
                     + singleUnit.build().getIdentifier()
                     + ", singleUnit.getSimpleUnitID() produced "
                     + singleUnit.getSimpleUnitID();

                  getMeasureData(loc, singleUnit.build(), width, singleCaseVariant, singleUnitArray);
                  if (singleUnitArray[GENDER_INDEX] != null) {
                     assert !singleUnitArray[GENDER_INDEX].isEmpty();

                     if (prefix != MeasureUnit.MeasurePrefix.ONE) {
                        singleUnitArray[GENDER_INDEX] = getDerivedGender(loc, "prefix", singleUnitArray, null);
                     }

                     if (dimensionality != 1) {
                        singleUnitArray[GENDER_INDEX] = getDerivedGender(loc, "power", singleUnitArray, null);
                     }

                     String timesGenderRule = getDeriveCompoundRule(loc, "gender", "times");
                     if (timesGenderRule.length() == 1) {
                        switch (timesGenderRule.charAt(0)) {
                           case '0':
                              if (singleUnitIndex == 0) {
                                 assert outArray[GENDER_INDEX] == null;

                                 outArray[GENDER_INDEX] = singleUnitArray[GENDER_INDEX];
                              }
                              break;
                           case '1':
                              if (singleUnitIndex == singleUnits.size() - 1) {
                                 assert outArray[GENDER_INDEX] == null;

                                 outArray[GENDER_INDEX] = singleUnitArray[GENDER_INDEX];
                              }
                        }
                     } else if (outArray[GENDER_INDEX] == null) {
                        outArray[GENDER_INDEX] = timesGenderRule;
                     }
                  }

                  for (StandardPlural plural_ : StandardPlural.values()) {
                     StandardPlural plural = plural_;
                     int pluralIndex = plural_.ordinal();
                     if (outArray[pluralIndex] == null) {
                        if (singleUnitArray[pluralIndex] == null) {
                           continue;
                        }

                        outArray[pluralIndex] = getWithPlural(outArray, plural_);
                     }

                     if (singlePluralCategory != null) {
                        plural = StandardPlural.fromString(singlePluralCategory);
                     }

                     LongNameHandler.ExtractCorePatternResult r = extractCorePattern(getWithPlural(singleUnitArray, plural));
                     if (r.placeholderPosition == LongNameHandler.PlaceholderPosition.MIDDLE) {
                        throw new UnsupportedOperationException();
                     }

                     if (globalPlaceholder[pluralIndex] == null) {
                        globalPlaceholder[pluralIndex] = r.placeholderPosition;
                        globalJoinerChar = r.joinerChar;
                     } else {
                        assert globalPlaceholder[pluralIndex] == r.placeholderPosition;
                     }

                     if (prefix != MeasureUnit.MeasurePrefix.ONE) {
                        String prefixCompiled = SimpleFormatterImpl.compileToStringMinMaxArguments(prefixPattern, sb, 1, 1);
                        if (width == NumberFormatter.UnitWidth.FULL_NAME) {
                           r.coreUnit = UCharacter.toLowerCase(loc, r.coreUnit);
                        }

                        r.coreUnit = SimpleFormatterImpl.formatCompiledPattern(prefixCompiled, r.coreUnit);
                     }

                     if (dimensionality != 1) {
                        String dimensionalityCompiled = SimpleFormatterImpl.compileToStringMinMaxArguments(
                           getWithPlural(dimensionalityPrefixPatterns, plural), sb, 1, 1
                        );
                        if (width == NumberFormatter.UnitWidth.FULL_NAME) {
                           r.coreUnit = UCharacter.toLowerCase(loc, r.coreUnit);
                        }

                        r.coreUnit = SimpleFormatterImpl.formatCompiledPattern(dimensionalityCompiled, r.coreUnit);
                     }

                     if (outArray[pluralIndex].length() == 0) {
                        outArray[pluralIndex] = r.coreUnit;
                     } else {
                        outArray[pluralIndex] = SimpleFormatterImpl.formatCompiledPattern(timesPatternFormatter, outArray[pluralIndex], r.coreUnit);
                     }
                  }
               }

               for (StandardPlural pluralx : StandardPlural.values()) {
                  int pluralIndexx = pluralx.ordinal();
                  if (globalPlaceholder[pluralIndexx] == LongNameHandler.PlaceholderPosition.BEGINNING) {
                     StringBuilder tmp = new StringBuilder();
                     tmp.append("{0}");
                     if (globalJoinerChar != 0) {
                        tmp.append(globalJoinerChar);
                     }

                     tmp.append(outArray[pluralIndexx]);
                     outArray[pluralIndexx] = tmp.toString();
                  } else if (globalPlaceholder[pluralIndexx] == LongNameHandler.PlaceholderPosition.END) {
                     if (globalJoinerChar != 0) {
                        outArray[pluralIndexx] = outArray[pluralIndexx] + globalJoinerChar;
                     }

                     outArray[pluralIndexx] = outArray[pluralIndexx] + "{0}";
                  }
               }
            }
         }
      }
   }

   private void simpleFormatsToModifiers(String[] simpleFormats, NumberFormat.Field field) {
      StringBuilder sb = new StringBuilder();

      for (StandardPlural plural : StandardPlural.VALUES) {
         String simpleFormat = getWithPlural(simpleFormats, plural);
         String compiled = SimpleFormatterImpl.compileToStringMinMaxArguments(simpleFormat, sb, 0, 1);
         Modifier.Parameters parameters = new Modifier.Parameters();
         parameters.obj = this;
         parameters.signum = null;
         parameters.plural = plural;
         this.modifiers.put(plural, new SimpleModifier(compiled, field, false, parameters));
      }
   }

   private void multiSimpleFormatsToModifiers(String[] leadFormats, String trailFormat, NumberFormat.Field field) {
      StringBuilder sb = new StringBuilder();
      String trailCompiled = SimpleFormatterImpl.compileToStringMinMaxArguments(trailFormat, sb, 1, 1);

      for (StandardPlural plural : StandardPlural.VALUES) {
         String leadFormat = getWithPlural(leadFormats, plural);
         String compoundFormat;
         if (leadFormat.length() == 0) {
            compoundFormat = trailFormat;
         } else {
            compoundFormat = SimpleFormatterImpl.formatCompiledPattern(trailCompiled, leadFormat);
         }

         String compoundCompiled = SimpleFormatterImpl.compileToStringMinMaxArguments(compoundFormat, sb, 0, 1);
         Modifier.Parameters parameters = new Modifier.Parameters();
         parameters.obj = this;
         parameters.signum = null;
         parameters.plural = plural;
         this.modifiers.put(plural, new SimpleModifier(compoundCompiled, field, false, parameters));
      }
   }

   @Override
   public MicroProps processQuantity(DecimalQuantity quantity) {
      MicroProps micros = this.parent.processQuantity(quantity);
      StandardPlural pluralForm = RoundingUtils.getPluralSafe(micros.rounder, this.rules, quantity);
      micros.modOuter = this.modifiers.get(pluralForm);
      micros.gender = this.gender;
      return micros;
   }

   @Override
   public MicroProps processQuantityWithMicros(DecimalQuantity quantity, MicroProps micros) {
      StandardPlural pluralForm = RoundingUtils.getPluralSafe(micros.rounder, this.rules, quantity);
      micros.modOuter = this.modifiers.get(pluralForm);
      return micros;
   }

   @Override
   public Modifier getModifier(Modifier.Signum signum, StandardPlural plural) {
      return this.modifiers.get(plural);
   }

   private static final class AliasSink extends UResource.Sink {
      String replacement;

      private AliasSink() {
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table aliasTable = value.getTable();

         for (int i = 0; aliasTable.getKeyAndValue(i, key, value); i++) {
            String keyString = key.toString();
            if (keyString.equals("replacement")) {
               this.replacement = value.toString();
            }
         }
      }
   }

   private static class DerivedComponents {
      private String value0 = "";
      private String value1 = "";

      DerivedComponents(ULocale locale, String feature, String structure) {
         try {
            ICUResourceBundle derivationsBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(
               "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "grammaticalFeatures"
            );
            derivationsBundle = (ICUResourceBundle)derivationsBundle.get("grammaticalData");
            derivationsBundle = (ICUResourceBundle)derivationsBundle.get("derivations");

            ICUResourceBundle stackBundle;
            try {
               stackBundle = (ICUResourceBundle)derivationsBundle.get(locale.getLanguage());
            } catch (MissingResourceException var7) {
               stackBundle = (ICUResourceBundle)derivationsBundle.get("root");
            }

            stackBundle = (ICUResourceBundle)stackBundle.get("component");
            stackBundle = (ICUResourceBundle)stackBundle.get(feature);
            stackBundle = (ICUResourceBundle)stackBundle.get(structure);
            String value = stackBundle.getString(0);
            if (value.compareTo("compound") == 0) {
               this.value0 = null;
            } else {
               this.value0 = value;
            }

            value = stackBundle.getString(1);
            if (value.compareTo("compound") == 0) {
               this.value1 = null;
            } else {
               this.value1 = value;
            }
         } catch (MissingResourceException var8) {
         }
      }

      String value0(String compoundValue) {
         return this.value0 != null ? this.value0 : compoundValue;
      }

      String value1(String compoundValue) {
         return this.value1 != null ? this.value1 : compoundValue;
      }
   }

   private static class ExtractCorePatternResult {
      String coreUnit;
      LongNameHandler.PlaceholderPosition placeholderPosition;
      char joinerChar;

      private ExtractCorePatternResult() {
      }
   }

   private static final class InflectedPluralSink extends UResource.Sink {
      String gender;
      String caseVariant;
      String[] outArray;

      public InflectedPluralSink(String gender, String caseVariant, String[] outArray) {
         this.gender = gender;
         this.caseVariant = caseVariant;
         this.outArray = outArray;

         for (int i = 0; i < LongNameHandler.ARRAY_LENGTH; i++) {
            outArray[i] = null;
         }
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table pluralsTable = value.getTable();

         for (int i = 0; pluralsTable.getKeyAndValue(i, key, value); i++) {
            String keyString = key.toString();
            int pluralIndex = LongNameHandler.getIndex(keyString);
            if (this.outArray[pluralIndex] == null) {
               UResource.Table genderTable = value.getTable();
               if (this.loadForPluralForm(genderTable, value)) {
                  this.outArray[pluralIndex] = value.getString();
               }
            }
         }
      }

      private boolean loadForPluralForm(UResource.Table genderTable, UResource.Value value) {
         if (this.gender != null && !this.gender.isEmpty()) {
            if (this.loadForGender(genderTable, this.gender, value)) {
               return true;
            }

            if (this.gender != "neuter" && this.loadForGender(genderTable, "neuter", value)) {
               return true;
            }
         }

         return this.loadForGender(genderTable, "_", value);
      }

      private boolean loadForGender(UResource.Table genderTable, String genderVal, UResource.Value value) {
         if (!genderTable.findValue(genderVal, value)) {
            return false;
         } else {
            UResource.Table caseTable = value.getTable();
            if (this.caseVariant != null && !this.caseVariant.isEmpty()) {
               if (this.loadForCase(caseTable, this.caseVariant, value)) {
                  return true;
               }

               if (this.caseVariant != "nominative" && this.loadForCase(caseTable, "nominative", value)) {
                  return true;
               }
            }

            return this.loadForCase(caseTable, "_", value);
         }
      }

      private boolean loadForCase(UResource.Table caseTable, String caseValue, UResource.Value value) {
         return caseTable.findValue(caseValue, value);
      }
   }

   private static enum PlaceholderPosition {
      NONE,
      BEGINNING,
      MIDDLE,
      END;
   }

   private static final class PluralTableSink extends UResource.Sink {
      String[] outArray;

      public PluralTableSink(String[] outArray) {
         this.outArray = outArray;
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table pluralsTable = value.getTable();

         for (int i = 0; pluralsTable.getKeyAndValue(i, key, value); i++) {
            String keyString = key.toString();
            if (!keyString.equals("case")) {
               int index = LongNameHandler.getIndex(keyString);
               if (this.outArray[index] == null) {
                  String formatString = value.getString();
                  this.outArray[index] = formatString;
               }
            }
         }
      }
   }
}
