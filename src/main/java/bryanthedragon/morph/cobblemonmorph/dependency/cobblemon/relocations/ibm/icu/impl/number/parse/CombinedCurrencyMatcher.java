package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;
import com.cobblemon.mod.relocations.ibm.icu.impl.TextTrieMap;
import com.cobblemon.mod.relocations.ibm.icu.text.DecimalFormatSymbols;
import com.cobblemon.mod.relocations.ibm.icu.util.Currency;
import java.util.Iterator;

public class CombinedCurrencyMatcher implements NumberParseMatcher {
   private final String isoCode;
   private final String currency1;
   private final String currency2;
   private final String[] localLongNames;
   private final String afterPrefixInsert;
   private final String beforeSuffixInsert;
   private final TextTrieMap<Currency.CurrencyStringInfo> longNameTrie;
   private final TextTrieMap<Currency.CurrencyStringInfo> symbolTrie;

   public static CombinedCurrencyMatcher getInstance(Currency currency, DecimalFormatSymbols dfs, int parseFlags) {
      return new CombinedCurrencyMatcher(currency, dfs, parseFlags);
   }

   private CombinedCurrencyMatcher(Currency currency, DecimalFormatSymbols dfs, int parseFlags) {
      this.isoCode = currency.getSubtype();
      this.currency1 = currency.getSymbol(dfs.getULocale());
      this.currency2 = currency.getCurrencyCode();
      this.afterPrefixInsert = dfs.getPatternForCurrencySpacing(2, false);
      this.beforeSuffixInsert = dfs.getPatternForCurrencySpacing(2, true);
      if (0 == (parseFlags & 8192)) {
         this.longNameTrie = Currency.getParsingTrie(dfs.getULocale(), 1);
         this.symbolTrie = Currency.getParsingTrie(dfs.getULocale(), 0);
         this.localLongNames = null;
      } else {
         this.longNameTrie = null;
         this.symbolTrie = null;
         this.localLongNames = new String[StandardPlural.COUNT];

         for (int i = 0; i < StandardPlural.COUNT; i++) {
            String pluralKeyword = StandardPlural.VALUES.get(i).getKeyword();
            this.localLongNames[i] = currency.getName(dfs.getLocale(), 2, pluralKeyword, null);
         }
      }
   }

   @Override
   public boolean match(StringSegment segment, ParsedNumber result) {
      if (result.currencyCode != null) {
         return false;
      } else {
         int initialOffset = segment.getOffset();
         boolean maybeMore = false;
         if (result.seenNumber() && !this.beforeSuffixInsert.isEmpty()) {
            int overlap = segment.getCommonPrefixLength(this.beforeSuffixInsert);
            if (overlap == this.beforeSuffixInsert.length()) {
               segment.adjustOffset(overlap);
            }

            maybeMore = maybeMore || overlap == segment.length();
         }

         maybeMore = maybeMore || this.matchCurrency(segment, result);
         if (result.currencyCode == null) {
            segment.setOffset(initialOffset);
            return maybeMore;
         } else {
            if (!result.seenNumber() && !this.afterPrefixInsert.isEmpty()) {
               int overlap = segment.getCommonPrefixLength(this.afterPrefixInsert);
               if (overlap == this.afterPrefixInsert.length()) {
                  segment.adjustOffset(overlap);
               }

               maybeMore = maybeMore || overlap == segment.length();
            }

            return maybeMore;
         }
      }
   }

   private boolean matchCurrency(StringSegment segment, ParsedNumber result) {
      boolean maybeMore = false;
      int overlap1;
      if (!this.currency1.isEmpty()) {
         overlap1 = segment.getCaseSensitivePrefixLength(this.currency1);
      } else {
         overlap1 = -1;
      }

      maybeMore = maybeMore || overlap1 == segment.length();
      if (overlap1 == this.currency1.length()) {
         result.currencyCode = this.isoCode;
         segment.adjustOffset(overlap1);
         result.setCharsConsumed(segment);
         return maybeMore;
      } else {
         int overlap2;
         if (!this.currency2.isEmpty()) {
            overlap2 = segment.getCommonPrefixLength(this.currency2);
         } else {
            overlap2 = -1;
         }

         maybeMore = maybeMore || overlap2 == segment.length();
         if (overlap2 == this.currency2.length()) {
            result.currencyCode = this.isoCode;
            segment.adjustOffset(overlap2);
            result.setCharsConsumed(segment);
            return maybeMore;
         } else {
            if (this.longNameTrie != null) {
               TextTrieMap.Output trieOutput = new TextTrieMap.Output();
               Iterator<Currency.CurrencyStringInfo> values = this.longNameTrie.get(segment, 0, trieOutput);
               maybeMore = maybeMore || trieOutput.partialMatch;
               if (values == null) {
                  values = this.symbolTrie.get(segment, 0, trieOutput);
                  maybeMore = maybeMore || trieOutput.partialMatch;
               }

               if (values != null) {
                  result.currencyCode = values.next().getISOCode();
                  segment.adjustOffset(trieOutput.matchLength);
                  result.setCharsConsumed(segment);
                  return maybeMore;
               }
            } else {
               int longestFullMatch = 0;

               for (int i = 0; i < StandardPlural.COUNT; i++) {
                  String name = this.localLongNames[i];
                  if (!name.isEmpty()) {
                     int overlap = segment.getCommonPrefixLength(name);
                     if (overlap == name.length() && name.length() > longestFullMatch) {
                        longestFullMatch = name.length();
                     }

                     maybeMore = maybeMore || overlap > 0;
                  }
               }

               if (longestFullMatch > 0) {
                  result.currencyCode = this.isoCode;
                  segment.adjustOffset(longestFullMatch);
                  result.setCharsConsumed(segment);
                  return maybeMore;
               }
            }

            return maybeMore;
         }
      }
   }

   @Override
   public boolean smokeTest(StringSegment segment) {
      return true;
   }

   @Override
   public void postProcess(ParsedNumber result) {
   }

   @Override
   public String toString() {
      return "<CombinedCurrencyMatcher " + this.isoCode + ">";
   }
}
