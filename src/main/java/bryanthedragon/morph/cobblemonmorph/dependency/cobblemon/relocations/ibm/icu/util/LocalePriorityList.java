package com.cobblemon.mod.relocations.ibm.icu.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalePriorityList implements Iterable<ULocale> {
   private static final Double D1 = 1.0;
   private static final Pattern languageSplitter = Pattern.compile("\\s*,\\s*");
   private static final Pattern weightSplitter = Pattern.compile("\\s*(\\S*)\\s*;\\s*q\\s*=\\s*(\\S*)");
   private final Map<ULocale, Double> languagesAndWeights;
   private static Comparator<Double> myDescendingDouble = new Comparator<Double>() {
      public int compare(Double o1, Double o2) {
         int result = o1.compareTo(o2);
         return result > 0 ? -1 : (result < 0 ? 1 : 0);
      }
   };

   public static LocalePriorityList.Builder add(ULocale... locales) {
      return new LocalePriorityList.Builder().add(locales);
   }

   public static LocalePriorityList.Builder add(ULocale locale, double weight) {
      return new LocalePriorityList.Builder().add(locale, weight);
   }

   public static LocalePriorityList.Builder add(LocalePriorityList list) {
      return new LocalePriorityList.Builder(list);
   }

   public static LocalePriorityList.Builder add(String acceptLanguageString) {
      return new LocalePriorityList.Builder().add(acceptLanguageString);
   }

   public Double getWeight(ULocale locale) {
      return this.languagesAndWeights.get(locale);
   }

   public Set<ULocale> getULocales() {
      return this.languagesAndWeights.keySet();
   }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder();

      for (Entry<ULocale, Double> entry : this.languagesAndWeights.entrySet()) {
         ULocale language = entry.getKey();
         double weight = entry.getValue();
         if (result.length() != 0) {
            result.append(", ");
         }

         result.append(language);
         if (weight != 1.0) {
            result.append(";q=").append(weight);
         }
      }

      return result.toString();
   }

   @Override
   public Iterator<ULocale> iterator() {
      return this.languagesAndWeights.keySet().iterator();
   }

   @Override
   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (this == o) {
         return true;
      } else {
         try {
            LocalePriorityList that = (LocalePriorityList)o;
            return this.languagesAndWeights.equals(that.languagesAndWeights);
         } catch (RuntimeException var3) {
            return false;
         }
      }
   }

   @Override
   public int hashCode() {
      return this.languagesAndWeights.hashCode();
   }

   private LocalePriorityList(Map<ULocale, Double> languageToWeight) {
      this.languagesAndWeights = languageToWeight;
   }

   public static class Builder {
      private Map<ULocale, Double> languageToWeight;
      private LocalePriorityList built;
      private boolean hasWeights = false;

      private Builder() {
         this.languageToWeight = new LinkedHashMap<>();
      }

      private Builder(LocalePriorityList list) {
         this.built = list;

         for (Double value : list.languagesAndWeights.values()) {
            double weight = value;

            assert 0.0 < weight && weight <= 1.0;

            if (weight != 1.0) {
               this.hasWeights = true;
               break;
            }
         }
      }

      public LocalePriorityList build() {
         return this.build(false);
      }

      public LocalePriorityList build(boolean preserveWeights) {
         if (this.built != null) {
            return this.built;
         } else {
            Map<ULocale, Double> temp;
            if (this.hasWeights) {
               TreeMap<Double, List<ULocale>> weightToLanguages = new TreeMap<>(LocalePriorityList.myDescendingDouble);

               for (Entry<ULocale, Double> entry : this.languageToWeight.entrySet()) {
                  ULocale lang = entry.getKey();
                  Double weight = entry.getValue();
                  List<ULocale> s = weightToLanguages.get(weight);
                  if (s == null) {
                     weightToLanguages.put(weight, s = new LinkedList<>());
                  }

                  s.add(lang);
               }

               if (weightToLanguages.size() <= 1) {
                  temp = this.languageToWeight;
                  if (weightToLanguages.isEmpty() || weightToLanguages.firstKey() == 1.0) {
                     this.hasWeights = false;
                  }
               } else {
                  temp = new LinkedHashMap<>();

                  for (Entry<Double, List<ULocale>> langEntry : weightToLanguages.entrySet()) {
                     Double weight = preserveWeights ? langEntry.getKey() : LocalePriorityList.D1;

                     for (ULocale lang : langEntry.getValue()) {
                        temp.put(lang, weight);
                     }
                  }
               }
            } else {
               temp = this.languageToWeight;
            }

            this.languageToWeight = null;
            return this.built = new LocalePriorityList(Collections.unmodifiableMap(temp));
         }
      }

      public LocalePriorityList.Builder add(LocalePriorityList list) {
         for (Entry<ULocale, Double> entry : list.languagesAndWeights.entrySet()) {
            this.add(entry.getKey(), entry.getValue());
         }

         return this;
      }

      public LocalePriorityList.Builder add(ULocale locale) {
         return this.add(locale, 1.0);
      }

      public LocalePriorityList.Builder add(ULocale... locales) {
         for (ULocale languageCode : locales) {
            this.add(languageCode, 1.0);
         }

         return this;
      }

      public LocalePriorityList.Builder add(ULocale locale, double weight) {
         if (this.languageToWeight == null) {
            this.languageToWeight = new LinkedHashMap<>(this.built.languagesAndWeights);
            this.built = null;
         }

         if (this.languageToWeight.containsKey(locale)) {
            this.languageToWeight.remove(locale);
         }

         if (weight <= 0.0) {
            return this;
         } else {
            Double value;
            if (weight >= 1.0) {
               value = LocalePriorityList.D1;
            } else {
               value = weight;
               this.hasWeights = true;
            }

            this.languageToWeight.put(locale, value);
            return this;
         }
      }

      public LocalePriorityList.Builder add(String acceptLanguageList) {
         String[] items = LocalePriorityList.languageSplitter.split(acceptLanguageList.trim());
         Matcher itemMatcher = LocalePriorityList.weightSplitter.matcher("");

         for (String item : items) {
            if (itemMatcher.reset(item).matches()) {
               ULocale language = new ULocale(itemMatcher.group(1));
               double weight = Double.parseDouble(itemMatcher.group(2));
               if (!(0.0 <= weight) || !(weight <= 1.0)) {
                  throw new IllegalArgumentException("Illegal weight, must be 0..1: " + weight);
               }

               this.add(language, weight);
            } else if (item.length() != 0) {
               this.add(new ULocale(item));
            }
         }

         return this;
      }
   }
}
