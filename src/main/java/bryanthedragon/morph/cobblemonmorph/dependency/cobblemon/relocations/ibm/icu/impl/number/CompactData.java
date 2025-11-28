package com.cobblemon.mod.relocations.ibm.icu.impl.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import com.cobblemon.mod.relocations.ibm.icu.impl.UResource;
import com.cobblemon.mod.relocations.ibm.icu.text.CompactDecimalFormat;
import com.cobblemon.mod.relocations.ibm.icu.text.PluralRules;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUException;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class CompactData implements MultiplierProducer {
   private static final String USE_FALLBACK = "<USE FALLBACK>";
   private final String[] patterns = new String[16 * StandardPlural.COUNT];
   private final byte[] multipliers = new byte[16];
   private byte largestMagnitude = 0;
   private boolean isEmpty = true;
   private static final int COMPACT_MAX_DIGITS = 15;

   public void populate(ULocale locale, String nsName, CompactDecimalFormat.CompactStyle compactStyle, CompactData.CompactType compactType) {
      assert this.isEmpty;

      CompactData.CompactDataSink sink = new CompactData.CompactDataSink(this);
      ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", locale);
      boolean nsIsLatn = nsName.equals("latn");
      boolean compactIsShort = compactStyle == CompactDecimalFormat.CompactStyle.SHORT;
      StringBuilder resourceKey = new StringBuilder();
      getResourceBundleKey(nsName, compactStyle, compactType, resourceKey);
      rb.getAllItemsWithFallbackNoFail(resourceKey.toString(), sink);
      if (this.isEmpty && !nsIsLatn) {
         getResourceBundleKey("latn", compactStyle, compactType, resourceKey);
         rb.getAllItemsWithFallbackNoFail(resourceKey.toString(), sink);
      }

      if (this.isEmpty && !compactIsShort) {
         getResourceBundleKey(nsName, CompactDecimalFormat.CompactStyle.SHORT, compactType, resourceKey);
         rb.getAllItemsWithFallbackNoFail(resourceKey.toString(), sink);
      }

      if (this.isEmpty && !nsIsLatn && !compactIsShort) {
         getResourceBundleKey("latn", CompactDecimalFormat.CompactStyle.SHORT, compactType, resourceKey);
         rb.getAllItemsWithFallbackNoFail(resourceKey.toString(), sink);
      }

      if (this.isEmpty) {
         throw new ICUException("Could not load compact decimal data for locale " + locale);
      }
   }

   private static void getResourceBundleKey(
      String nsName, CompactDecimalFormat.CompactStyle compactStyle, CompactData.CompactType compactType, StringBuilder sb
   ) {
      sb.setLength(0);
      sb.append("NumberElements/");
      sb.append(nsName);
      sb.append(compactStyle == CompactDecimalFormat.CompactStyle.SHORT ? "/patternsShort" : "/patternsLong");
      sb.append(compactType == CompactData.CompactType.DECIMAL ? "/decimalFormat" : "/currencyFormat");
   }

   public void populate(Map<String, Map<String, String>> powersToPluralsToPatterns) {
      assert this.isEmpty;

      for (Entry<String, Map<String, String>> magnitudeEntry : powersToPluralsToPatterns.entrySet()) {
         byte magnitude = (byte)(magnitudeEntry.getKey().length() - 1);

         for (Entry<String, String> pluralEntry : magnitudeEntry.getValue().entrySet()) {
            String pluralString = pluralEntry.getKey().toString();
            StandardPlural plural = StandardPlural.fromString(pluralString);
            String patternString = pluralEntry.getValue().toString();
            this.patterns[getIndex(magnitude, plural)] = patternString;
            int numZeros = countZeros(patternString);
            if (numZeros > 0) {
               this.multipliers[magnitude] = (byte)(numZeros - magnitude - 1);
               if (magnitude > this.largestMagnitude) {
                  this.largestMagnitude = magnitude;
               }

               this.isEmpty = false;
            }
         }
      }
   }

   @Override
   public int getMultiplier(int magnitude) {
      if (magnitude < 0) {
         return 0;
      } else {
         if (magnitude > this.largestMagnitude) {
            magnitude = this.largestMagnitude;
         }

         return this.multipliers[magnitude];
      }
   }

   public String getPattern(int magnitude, PluralRules rules, DecimalQuantity dq) {
      if (magnitude < 0) {
         return null;
      } else {
         if (magnitude > this.largestMagnitude) {
            magnitude = this.largestMagnitude;
         }

         String patternString = null;
         if (dq.isHasIntegerValue()) {
            long i = dq.toLong(true);
            if (i == 0L) {
               patternString = this.patterns[getIndex(magnitude, StandardPlural.EQ_0)];
            } else if (i == 1L) {
               patternString = this.patterns[getIndex(magnitude, StandardPlural.EQ_1)];
            }

            if (patternString != null) {
               return patternString;
            }
         }

         StandardPlural plural = dq.getStandardPlural(rules);
         patternString = this.patterns[getIndex(magnitude, plural)];
         if (patternString == null && plural != StandardPlural.OTHER) {
            patternString = this.patterns[getIndex(magnitude, StandardPlural.OTHER)];
         }

         if (patternString == "<USE FALLBACK>") {
            patternString = null;
         }

         return patternString;
      }
   }

   public void getUniquePatterns(Set<String> output) {
      assert output.isEmpty();

      output.addAll(Arrays.asList(this.patterns));
      output.remove("<USE FALLBACK>");
      output.remove(null);
   }

   private static final int getIndex(int magnitude, StandardPlural plural) {
      return magnitude * StandardPlural.COUNT + plural.ordinal();
   }

   private static final int countZeros(String patternString) {
      int numZeros = 0;

      for (int i = 0; i < patternString.length(); i++) {
         if (patternString.charAt(i) == '0') {
            numZeros++;
         } else if (numZeros > 0) {
            break;
         }
      }

      return numZeros;
   }

   private static final class CompactDataSink extends UResource.Sink {
      CompactData data;

      public CompactDataSink(CompactData data) {
         this.data = data;
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean isRoot) {
         UResource.Table powersOfTenTable = value.getTable();

         for (int i3 = 0; powersOfTenTable.getKeyAndValue(i3, key, value); i3++) {
            byte magnitude = (byte)(key.length() - 1);
            byte multiplier = this.data.multipliers[magnitude];

            assert magnitude < 15;

            UResource.Table pluralVariantsTable = value.getTable();

            for (int i4 = 0; pluralVariantsTable.getKeyAndValue(i4, key, value); i4++) {
               StandardPlural plural = StandardPlural.fromString(key.toString());
               if (this.data.patterns[CompactData.getIndex(magnitude, plural)] == null) {
                  String patternString = value.toString();
                  if (patternString.equals("0")) {
                     patternString = "<USE FALLBACK>";
                  }

                  this.data.patterns[CompactData.getIndex(magnitude, plural)] = patternString;
                  if (multiplier == 0) {
                     int numZeros = CompactData.countZeros(patternString);
                     if (numZeros > 0) {
                        multiplier = (byte)(numZeros - magnitude - 1);
                     }
                  }
               }
            }

            if (this.data.multipliers[magnitude] == 0) {
               this.data.multipliers[magnitude] = multiplier;
               if (magnitude > this.data.largestMagnitude) {
                  this.data.largestMagnitude = magnitude;
               }

               this.data.isEmpty = false;
            } else {
               assert this.data.multipliers[magnitude] == multiplier;
            }
         }
      }
   }

   public static enum CompactType {
      DECIMAL,
      CURRENCY;
   }
}
