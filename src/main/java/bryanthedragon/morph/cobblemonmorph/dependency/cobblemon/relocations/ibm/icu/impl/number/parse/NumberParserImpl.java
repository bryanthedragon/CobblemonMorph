package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.AffixPatternProvider;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.CustomSymbolCurrency;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalFormatProperties;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.Grouper;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.PatternStringParser;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.PropertiesAffixPatternProvider;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.RoundingUtils;
import com.cobblemon.mod.relocations.ibm.icu.number.NumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.number.Scale;
import com.cobblemon.mod.relocations.ibm.icu.text.DecimalFormatSymbols;
import com.cobblemon.mod.relocations.ibm.icu.util.Currency;
import com.cobblemon.mod.relocations.ibm.icu.util.CurrencyAmount;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NumberParserImpl {
   private final int parseFlags;
   private final List<NumberParseMatcher> matchers = new ArrayList<>();
   private boolean frozen;

   public static NumberParserImpl createSimpleParser(ULocale locale, String pattern, int parseFlags) {
      NumberParserImpl parser = new NumberParserImpl(parseFlags);
      Currency currency = Currency.getInstance("USD");
      DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
      IgnorablesMatcher ignorables = IgnorablesMatcher.getInstance(parseFlags);
      AffixTokenMatcherFactory factory = new AffixTokenMatcherFactory();
      factory.currency = currency;
      factory.symbols = symbols;
      factory.ignorables = ignorables;
      factory.locale = locale;
      factory.parseFlags = parseFlags;
      PatternStringParser.ParsedPatternInfo patternInfo = PatternStringParser.parseToPatternInfo(pattern);
      AffixMatcher.createMatchers(patternInfo, parser, factory, ignorables, parseFlags);
      Grouper grouper = Grouper.forStrategy(NumberFormatter.GroupingStrategy.AUTO).withLocaleData(locale, patternInfo);
      parser.addMatcher(ignorables);
      parser.addMatcher(DecimalMatcher.getInstance(symbols, grouper, parseFlags));
      parser.addMatcher(MinusSignMatcher.getInstance(symbols, false));
      parser.addMatcher(PlusSignMatcher.getInstance(symbols, false));
      parser.addMatcher(PercentMatcher.getInstance(symbols));
      parser.addMatcher(PermilleMatcher.getInstance(symbols));
      parser.addMatcher(NanMatcher.getInstance(symbols, parseFlags));
      parser.addMatcher(InfinityMatcher.getInstance(symbols));
      parser.addMatcher(PaddingMatcher.getInstance("@"));
      parser.addMatcher(ScientificMatcher.getInstance(symbols, grouper));
      parser.addMatcher(CombinedCurrencyMatcher.getInstance(currency, symbols, parseFlags));
      parser.addMatcher(new RequireNumberValidator());
      parser.freeze();
      return parser;
   }

   public static Number parseStatic(String input, ParsePosition ppos, DecimalFormatProperties properties, DecimalFormatSymbols symbols) {
      NumberParserImpl parser = createParserFromProperties(properties, symbols, false);
      ParsedNumber result = new ParsedNumber();
      parser.parse(input, true, result);
      if (result.success()) {
         ppos.setIndex(result.charEnd);
         return result.getNumber();
      } else {
         ppos.setErrorIndex(result.charEnd);
         return null;
      }
   }

   public static CurrencyAmount parseStaticCurrency(String input, ParsePosition ppos, DecimalFormatProperties properties, DecimalFormatSymbols symbols) {
      NumberParserImpl parser = createParserFromProperties(properties, symbols, true);
      ParsedNumber result = new ParsedNumber();
      parser.parse(input, true, result);
      if (result.success()) {
         ppos.setIndex(result.charEnd);

         assert result.currencyCode != null;

         return new CurrencyAmount(result.getNumber(), Currency.getInstance(result.currencyCode));
      } else {
         ppos.setErrorIndex(result.charEnd);
         return null;
      }
   }

   public static NumberParserImpl createDefaultParserForLocale(ULocale loc) {
      DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(loc);
      DecimalFormatProperties properties = PatternStringParser.parseToProperties("0");
      return createParserFromProperties(properties, symbols, false);
   }

   public static NumberParserImpl createParserFromProperties(DecimalFormatProperties properties, DecimalFormatSymbols symbols, boolean parseCurrency) {
      ULocale locale = symbols.getULocale();
      AffixPatternProvider affixProvider = PropertiesAffixPatternProvider.forProperties(properties);
      Currency currency = CustomSymbolCurrency.resolve(properties.getCurrency(), locale, symbols);
      DecimalFormatProperties.ParseMode parseMode = properties.getParseMode();
      if (parseMode == null) {
         parseMode = DecimalFormatProperties.ParseMode.LENIENT;
      }

      Grouper grouper = Grouper.forProperties(properties);
      int parseFlags = 0;
      if (!properties.getParseCaseSensitive()) {
         parseFlags |= 1;
      }

      if (properties.getParseIntegerOnly()) {
         parseFlags |= 16;
      }

      if (properties.getParseToBigDecimal()) {
         parseFlags |= 4096;
      }

      if (properties.getSignAlwaysShown()) {
         parseFlags |= 1024;
      }

      if (parseMode == DecimalFormatProperties.ParseMode.JAVA_COMPATIBILITY) {
         parseFlags |= 4;
         parseFlags |= 256;
         parseFlags |= 512;
         parseFlags |= 65536;
      } else if (parseMode == DecimalFormatProperties.ParseMode.STRICT) {
         parseFlags |= 8;
         parseFlags |= 4;
         parseFlags |= 256;
         parseFlags |= 512;
         parseFlags |= 32768;
      } else {
         parseFlags |= 128;
      }

      if (grouper.getPrimary() <= 0) {
         parseFlags |= 32;
      }

      if (parseCurrency || affixProvider.hasCurrencySign()) {
         parseFlags |= 2;
      }

      if (!parseCurrency) {
         parseFlags |= 8192;
      }

      NumberParserImpl parser = new NumberParserImpl(parseFlags);
      IgnorablesMatcher ignorables = IgnorablesMatcher.getInstance(parseFlags);
      AffixTokenMatcherFactory factory = new AffixTokenMatcherFactory();
      factory.currency = currency;
      factory.symbols = symbols;
      factory.ignorables = ignorables;
      factory.locale = locale;
      factory.parseFlags = parseFlags;
      AffixMatcher.createMatchers(affixProvider, parser, factory, ignorables, parseFlags);
      if (parseCurrency || affixProvider.hasCurrencySign()) {
         parser.addMatcher(CombinedCurrencyMatcher.getInstance(currency, symbols, parseFlags));
      }

      if (parseMode == DecimalFormatProperties.ParseMode.LENIENT && affixProvider.containsSymbolType(-4)) {
         parser.addMatcher(PercentMatcher.getInstance(symbols));
      }

      if (parseMode == DecimalFormatProperties.ParseMode.LENIENT && affixProvider.containsSymbolType(-5)) {
         parser.addMatcher(PermilleMatcher.getInstance(symbols));
      }

      if (parseMode == DecimalFormatProperties.ParseMode.LENIENT) {
         parser.addMatcher(PlusSignMatcher.getInstance(symbols, false));
         parser.addMatcher(MinusSignMatcher.getInstance(symbols, false));
      }

      parser.addMatcher(NanMatcher.getInstance(symbols, parseFlags));
      parser.addMatcher(InfinityMatcher.getInstance(symbols));
      String padString = properties.getPadString();
      if (padString != null && !ignorables.getSet().contains(padString)) {
         parser.addMatcher(PaddingMatcher.getInstance(padString));
      }

      parser.addMatcher(ignorables);
      parser.addMatcher(DecimalMatcher.getInstance(symbols, grouper, parseFlags));
      if (!properties.getParseNoExponent() || properties.getMinimumExponentDigits() > 0) {
         parser.addMatcher(ScientificMatcher.getInstance(symbols, grouper));
      }

      parser.addMatcher(new RequireNumberValidator());
      if (parseMode != DecimalFormatProperties.ParseMode.LENIENT) {
         parser.addMatcher(new RequireAffixValidator());
      }

      if (parseCurrency) {
         parser.addMatcher(new RequireCurrencyValidator());
      }

      if (properties.getDecimalPatternMatchRequired()) {
         boolean patternHasDecimalSeparator = properties.getDecimalSeparatorAlwaysShown() || properties.getMaximumFractionDigits() != 0;
         parser.addMatcher(RequireDecimalSeparatorValidator.getInstance(patternHasDecimalSeparator));
      }

      Scale multiplier = RoundingUtils.scaleFromProperties(properties);
      if (multiplier != null) {
         parser.addMatcher(new MultiplierParseHandler(multiplier));
      }

      parser.freeze();
      return parser;
   }

   public NumberParserImpl(int parseFlags) {
      this.parseFlags = parseFlags;
      this.frozen = false;
   }

   public void addMatcher(NumberParseMatcher matcher) {
      assert !this.frozen;

      this.matchers.add(matcher);
   }

   public void addMatchers(Collection<? extends NumberParseMatcher> matchers) {
      assert !this.frozen;

      this.matchers.addAll(matchers);
   }

   public void freeze() {
      this.frozen = true;
   }

   public int getParseFlags() {
      return this.parseFlags;
   }

   public void parse(String input, boolean greedy, ParsedNumber result) {
      this.parse(input, 0, greedy, result);
   }

   public void parse(String input, int start, boolean greedy, ParsedNumber result) {
      assert this.frozen;

      assert start >= 0 && start < input.length();

      StringSegment segment = new StringSegment(input, 0 != (this.parseFlags & 1));
      segment.adjustOffset(start);
      if (greedy) {
         this.parseGreedy(segment, result);
      } else if (0 != (this.parseFlags & 16384)) {
         this.parseLongestRecursive(segment, result, 1);
      } else {
         this.parseLongestRecursive(segment, result, -100);
      }

      for (NumberParseMatcher matcher : this.matchers) {
         matcher.postProcess(result);
      }

      result.postProcess();
   }

   private void parseGreedy(StringSegment segment, ParsedNumber result) {
      int i = 0;

      while (i < this.matchers.size()) {
         if (segment.length() == 0) {
            return;
         }

         NumberParseMatcher matcher = this.matchers.get(i);
         if (!matcher.smokeTest(segment)) {
            i++;
         } else {
            int initialOffset = segment.getOffset();
            matcher.match(segment, result);
            if (segment.getOffset() != initialOffset) {
               i = 0;
            } else {
               i++;
            }
         }
      }
   }

   private void parseLongestRecursive(StringSegment segment, ParsedNumber result, int recursionLevels) {
      if (segment.length() != 0) {
         if (recursionLevels != 0) {
            ParsedNumber initial = new ParsedNumber();
            initial.copyFrom(result);
            ParsedNumber candidate = new ParsedNumber();
            int initialOffset = segment.getOffset();

            for (int i = 0; i < this.matchers.size(); i++) {
               NumberParseMatcher matcher = this.matchers.get(i);
               if (matcher.smokeTest(segment)) {
                  int charsToConsume = 0;

                  while (charsToConsume < segment.length()) {
                     charsToConsume += Character.charCount(segment.codePointAt(charsToConsume));
                     candidate.copyFrom(initial);
                     segment.setLength(charsToConsume);
                     boolean maybeMore = matcher.match(segment, candidate);
                     segment.resetLength();
                     if (segment.getOffset() - initialOffset == charsToConsume) {
                        this.parseLongestRecursive(segment, candidate, recursionLevels + 1);
                        if (candidate.isBetterThan(result)) {
                           result.copyFrom(candidate);
                        }
                     }

                     segment.setOffset(initialOffset);
                     if (!maybeMore) {
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public String toString() {
      return "<NumberParserImpl matchers=" + this.matchers.toString() + ">";
   }
}
