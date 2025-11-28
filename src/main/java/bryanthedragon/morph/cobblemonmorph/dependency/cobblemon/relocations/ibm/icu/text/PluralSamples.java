package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.util.Output;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

@Deprecated
public class PluralSamples {
   private PluralRules pluralRules;
   private final Map<String, List<Double>> _keySamplesMap;
   @Deprecated
   public final Map<String, Boolean> _keyLimitedMap;
   private final Map<String, Set<PluralRules.FixedDecimal>> _keyFractionSamplesMap;
   private final Set<PluralRules.FixedDecimal> _fractionSamples;
   private static final int[] TENS = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000};
   private static final int LIMIT_FRACTION_SAMPLES = 3;

   @Deprecated
   public PluralSamples(PluralRules pluralRules) {
      this.pluralRules = pluralRules;
      Set<String> keywords = pluralRules.getKeywords();
      int MAX_SAMPLES = 3;
      Map<String, Boolean> temp = new HashMap<>();

      for (String k : keywords) {
         temp.put(k, pluralRules.isLimited(k));
      }

      this._keyLimitedMap = temp;
      Map<String, List<Double>> sampleMap = new HashMap<>();
      int keywordsRemaining = keywords.size();
      int limit = 128;

      for (int i = 0; keywordsRemaining > 0 && i < limit; i++) {
         keywordsRemaining = this.addSimpleSamples(pluralRules, 3, sampleMap, keywordsRemaining, i / 2.0);
      }

      keywordsRemaining = this.addSimpleSamples(pluralRules, 3, sampleMap, keywordsRemaining, 1000000.0);
      Map<String, Set<PluralRules.FixedDecimal>> sampleFractionMap = new HashMap<>();
      Set<PluralRules.FixedDecimal> mentioned = new TreeSet<>();
      Map<String, Set<PluralRules.FixedDecimal>> foundKeywords = new HashMap<>();

      for (PluralRules.FixedDecimal s : mentioned) {
         String keyword = pluralRules.select(s);
         this.addRelation(foundKeywords, keyword, s);
      }

      if (foundKeywords.size() != keywords.size()) {
         int i = 1;

         label88:
         while (true) {
            if (i >= 1000) {
               for (int ix = 10; ix < 1000; ix++) {
                  boolean done = this.addIfNotPresent(ix / 10.0, mentioned, foundKeywords);
                  if (done) {
                     break label88;
                  }
               }

               System.out.println("Failed to find sample for each keyword: " + foundKeywords + "\n\t" + pluralRules + "\n\t" + mentioned);
               break;
            }

            boolean done = this.addIfNotPresent(i, mentioned, foundKeywords);
            if (done) {
               break;
            }

            i++;
         }
      }

      mentioned.add(new PluralRules.FixedDecimal(0L));
      mentioned.add(new PluralRules.FixedDecimal(1L));
      mentioned.add(new PluralRules.FixedDecimal(2L));
      mentioned.add(new PluralRules.FixedDecimal(0.1, 1));
      mentioned.add(new PluralRules.FixedDecimal(1.99, 2));
      mentioned.addAll(this.fractions(mentioned));

      for (PluralRules.FixedDecimal s : mentioned) {
         String keyword = pluralRules.select(s);
         Set<PluralRules.FixedDecimal> list = sampleFractionMap.get(keyword);
         if (list == null) {
            list = new LinkedHashSet<>();
            sampleFractionMap.put(keyword, list);
         }

         list.add(s);
      }

      if (keywordsRemaining > 0) {
         for (String k : keywords) {
            if (!sampleMap.containsKey(k)) {
               sampleMap.put(k, Collections.emptyList());
            }

            if (!sampleFractionMap.containsKey(k)) {
               sampleFractionMap.put(k, Collections.emptySet());
            }
         }
      }

      for (Entry<String, List<Double>> entry : sampleMap.entrySet()) {
         sampleMap.put(entry.getKey(), Collections.unmodifiableList(entry.getValue()));
      }

      for (Entry<String, Set<PluralRules.FixedDecimal>> entry : sampleFractionMap.entrySet()) {
         sampleFractionMap.put(entry.getKey(), Collections.unmodifiableSet(entry.getValue()));
      }

      this._keySamplesMap = sampleMap;
      this._keyFractionSamplesMap = sampleFractionMap;
      this._fractionSamples = Collections.unmodifiableSet(mentioned);
   }

   private int addSimpleSamples(PluralRules pluralRules, int MAX_SAMPLES, Map<String, List<Double>> sampleMap, int keywordsRemaining, double val) {
      String keyword = pluralRules.select(val);
      boolean keyIsLimited = this._keyLimitedMap.get(keyword);
      List<Double> list = sampleMap.get(keyword);
      if (list == null) {
         list = new ArrayList<>(MAX_SAMPLES);
         sampleMap.put(keyword, list);
      } else if (!keyIsLimited && list.size() == MAX_SAMPLES) {
         return keywordsRemaining;
      }

      list.add(val);
      if (!keyIsLimited && list.size() == MAX_SAMPLES) {
         keywordsRemaining--;
      }

      return keywordsRemaining;
   }

   private void addRelation(Map<String, Set<PluralRules.FixedDecimal>> foundKeywords, String keyword, PluralRules.FixedDecimal s) {
      Set<PluralRules.FixedDecimal> set = foundKeywords.get(keyword);
      if (set == null) {
         foundKeywords.put(keyword, set = new HashSet<>());
      }

      set.add(s);
   }

   private boolean addIfNotPresent(double d, Set<PluralRules.FixedDecimal> mentioned, Map<String, Set<PluralRules.FixedDecimal>> foundKeywords) {
      PluralRules.FixedDecimal numberInfo = new PluralRules.FixedDecimal(d);
      String keyword = this.pluralRules.select(numberInfo);
      if (!foundKeywords.containsKey(keyword) || keyword.equals("other")) {
         this.addRelation(foundKeywords, keyword, numberInfo);
         mentioned.add(numberInfo);
         if (keyword.equals("other") && foundKeywords.get("other").size() > 1) {
            return true;
         }
      }

      return false;
   }

   private Set<PluralRules.FixedDecimal> fractions(Set<PluralRules.FixedDecimal> original) {
      Set<PluralRules.FixedDecimal> toAddTo = new HashSet<>();
      Set<Integer> result = new HashSet<>();

      for (PluralRules.FixedDecimal base1 : original) {
         result.add((int)base1.integerValue);
      }

      List<Integer> ints = new ArrayList<>(result);
      Set<String> keywords = new HashSet<>();

      for (int j = 0; j < ints.size(); j++) {
         Integer base = ints.get(j);
         String keyword = this.pluralRules.select(base.intValue());
         if (!keywords.contains(keyword)) {
            keywords.add(keyword);
            toAddTo.add(new PluralRules.FixedDecimal(base.intValue(), 1));
            toAddTo.add(new PluralRules.FixedDecimal(base.intValue(), 2));
            Integer fract = this.getDifferentCategory(ints, keyword);
            if (fract >= TENS[2]) {
               toAddTo.add(new PluralRules.FixedDecimal(base + "." + fract));
            } else {
               for (int visibleFractions = 1; visibleFractions < 3; visibleFractions++) {
                  for (int i = 1; i <= visibleFractions; i++) {
                     if (fract < TENS[i]) {
                        toAddTo.add(new PluralRules.FixedDecimal(base.intValue() + (double)fract.intValue() / TENS[i], visibleFractions));
                     }
                  }
               }
            }
         }
      }

      return toAddTo;
   }

   private Integer getDifferentCategory(List<Integer> ints, String keyword) {
      for (int i = ints.size() - 1; i >= 0; i--) {
         Integer other = ints.get(i);
         String keywordOther = this.pluralRules.select(other.intValue());
         if (!keywordOther.equals(keyword)) {
            return other;
         }
      }

      return 37;
   }

   @Deprecated
   public PluralRules.KeywordStatus getStatus(String keyword, int offset, Set<Double> explicits, Output<Double> uniqueValue) {
      if (uniqueValue != null) {
         uniqueValue.value = null;
      }

      if (!this.pluralRules.getKeywords().contains(keyword)) {
         return PluralRules.KeywordStatus.INVALID;
      } else {
         Collection<Double> values = this.pluralRules.getAllKeywordValues(keyword);
         if (values == null) {
            return PluralRules.KeywordStatus.UNBOUNDED;
         } else {
            int originalSize = values.size();
            if (explicits == null) {
               explicits = Collections.emptySet();
            }

            if (originalSize > explicits.size()) {
               if (originalSize == 1) {
                  if (uniqueValue != null) {
                     uniqueValue.value = values.iterator().next();
                  }

                  return PluralRules.KeywordStatus.UNIQUE;
               } else {
                  return PluralRules.KeywordStatus.BOUNDED;
               }
            } else {
               HashSet<Double> subtractedSet = new HashSet<>(values);

               for (Double explicit : explicits) {
                  subtractedSet.remove(explicit - offset);
               }

               if (subtractedSet.size() == 0) {
                  return PluralRules.KeywordStatus.SUPPRESSED;
               } else {
                  if (uniqueValue != null && subtractedSet.size() == 1) {
                     uniqueValue.value = subtractedSet.iterator().next();
                  }

                  return originalSize == 1 ? PluralRules.KeywordStatus.UNIQUE : PluralRules.KeywordStatus.BOUNDED;
               }
            }
         }
      }
   }

   Map<String, List<Double>> getKeySamplesMap() {
      return this._keySamplesMap;
   }

   Map<String, Set<PluralRules.FixedDecimal>> getKeyFractionSamplesMap() {
      return this._keyFractionSamplesMap;
   }

   Set<PluralRules.FixedDecimal> getFractionSamples() {
      return this._fractionSamples;
   }

   Collection<Double> getAllKeywordValues(String keyword) {
      if (!this.pluralRules.getKeywords().contains(keyword)) {
         return Collections.emptyList();
      } else {
         Collection<Double> result = this.getKeySamplesMap().get(keyword);
         return result.size() > 2 && !this._keyLimitedMap.get(keyword) ? null : result;
      }
   }
}
