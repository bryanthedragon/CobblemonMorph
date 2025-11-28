package com.cobblemon.mod.relocations.ibm.icu.impl.number.range;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import com.cobblemon.mod.relocations.ibm.icu.impl.UResource;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceTypeMismatchException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StandardPluralRanges {
   StandardPlural[] flatTriples;
   int numTriples = 0;
   private static volatile Map<String, String> languageToSet;
   public static final StandardPluralRanges DEFAULT = new StandardPluralRanges();

   private static Map<String, String> getLanguageToSet() {
      Map<String, String> candidate = languageToSet;
      if (candidate == null) {
         Map<String, String> map = new HashMap<>();
         StandardPluralRanges.PluralRangeSetsDataSink sink = new StandardPluralRanges.PluralRangeSetsDataSink(map);
         ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "pluralRanges"
         );
         resource.getAllItemsWithFallback("locales", sink);
         candidate = Collections.unmodifiableMap(map);
      }

      if (languageToSet == null) {
         languageToSet = candidate;
      }

      return languageToSet;
   }

   private static void getPluralRangesData(String set, StandardPluralRanges out) {
      StringBuilder sb = new StringBuilder();
      ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance(
         "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "pluralRanges"
      );
      sb.setLength(0);
      sb.append("rules/");
      sb.append(set);
      String key = sb.toString();
      StandardPluralRanges.PluralRangesDataSink sink = new StandardPluralRanges.PluralRangesDataSink(out);
      resource.getAllItemsWithFallback(key, sink);
   }

   public static StandardPluralRanges forLocale(ULocale locale) {
      return forSet(getSetForLocale(locale));
   }

   public static StandardPluralRanges forSet(String set) {
      StandardPluralRanges result = new StandardPluralRanges();
      if (set == null) {
         return DEFAULT;
      } else {
         getPluralRangesData(set, result);
         return result;
      }
   }

   public static String getSetForLocale(ULocale locale) {
      return getLanguageToSet().get(locale.getLanguage());
   }

   private StandardPluralRanges() {
   }

   private void addPluralRange(StandardPlural first, StandardPlural second, StandardPlural result) {
      this.flatTriples[3 * this.numTriples] = first;
      this.flatTriples[3 * this.numTriples + 1] = second;
      this.flatTriples[3 * this.numTriples + 2] = result;
      this.numTriples++;
   }

   private void setCapacity(int length) {
      this.flatTriples = new StandardPlural[length * 3];
   }

   public StandardPlural resolve(StandardPlural first, StandardPlural second) {
      for (int i = 0; i < this.numTriples; i++) {
         if (first == this.flatTriples[3 * i] && second == this.flatTriples[3 * i + 1]) {
            return this.flatTriples[3 * i + 2];
         }
      }

      return StandardPlural.OTHER;
   }

   private static final class PluralRangeSetsDataSink extends UResource.Sink {
      Map<String, String> output;

      PluralRangeSetsDataSink(Map<String, String> output) {
         this.output = output;
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table table = value.getTable();

         for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
            assert key.toString().equals(new ULocale(key.toString()).getLanguage());

            this.output.put(key.toString(), value.toString());
         }
      }
   }

   private static final class PluralRangesDataSink extends UResource.Sink {
      StandardPluralRanges output;

      PluralRangesDataSink(StandardPluralRanges output) {
         this.output = output;
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Array entriesArray = value.getArray();
         this.output.setCapacity(entriesArray.getSize());

         for (int i = 0; entriesArray.getValue(i, value); i++) {
            UResource.Array pluralFormsArray = value.getArray();
            if (pluralFormsArray.getSize() != 3) {
               throw new UResourceTypeMismatchException("Expected 3 elements in pluralRanges.txt array");
            }

            pluralFormsArray.getValue(0, value);
            StandardPlural first = StandardPlural.fromString(value.getString());
            pluralFormsArray.getValue(1, value);
            StandardPlural second = StandardPlural.fromString(value.getString());
            pluralFormsArray.getValue(2, value);
            StandardPlural result = StandardPlural.fromString(value.getString());
            this.output.addPluralRange(first, second, result);
         }
      }
   }
}
