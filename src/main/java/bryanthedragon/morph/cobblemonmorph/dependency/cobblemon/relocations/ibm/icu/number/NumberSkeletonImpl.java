package com.cobblemon.mod.relocations.ibm.icu.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.CacheBase;
import com.cobblemon.mod.relocations.ibm.icu.impl.PatternProps;
import com.cobblemon.mod.relocations.ibm.icu.impl.SoftCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.MacroProps;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.RoundingUtils;
import com.cobblemon.mod.relocations.ibm.icu.text.DecimalFormatSymbols;
import com.cobblemon.mod.relocations.ibm.icu.text.NumberingSystem;
import com.cobblemon.mod.relocations.ibm.icu.util.BytesTrie;
import com.cobblemon.mod.relocations.ibm.icu.util.CharsTrie;
import com.cobblemon.mod.relocations.ibm.icu.util.CharsTrieBuilder;
import com.cobblemon.mod.relocations.ibm.icu.util.Currency;
import com.cobblemon.mod.relocations.ibm.icu.util.MeasureUnit;
import com.cobblemon.mod.relocations.ibm.icu.util.NoUnit;
import com.cobblemon.mod.relocations.ibm.icu.util.StringTrieBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;

class NumberSkeletonImpl {
   static final char WILDCARD_CHAR = '*';
   static final char ALT_WILDCARD_CHAR = '+';
   static final NumberSkeletonImpl.StemEnum[] STEM_ENUM_VALUES = NumberSkeletonImpl.StemEnum.values();
   static final String SERIALIZED_STEM_TRIE = buildStemTrie();
   private static final CacheBase<String, UnlocalizedNumberFormatter, Void> cache = new SoftCache<String, UnlocalizedNumberFormatter, Void>() {
      protected UnlocalizedNumberFormatter createInstance(String skeletonString, Void unused) {
         return NumberSkeletonImpl.create(skeletonString);
      }
   };

   static boolean isWildcardChar(char c) {
      return c == '*' || c == '+';
   }

   static String buildStemTrie() {
      CharsTrieBuilder b = new CharsTrieBuilder();
      b.add("compact-short", NumberSkeletonImpl.StemEnum.STEM_COMPACT_SHORT.ordinal());
      b.add("compact-long", NumberSkeletonImpl.StemEnum.STEM_COMPACT_LONG.ordinal());
      b.add("scientific", NumberSkeletonImpl.StemEnum.STEM_SCIENTIFIC.ordinal());
      b.add("engineering", NumberSkeletonImpl.StemEnum.STEM_ENGINEERING.ordinal());
      b.add("notation-simple", NumberSkeletonImpl.StemEnum.STEM_NOTATION_SIMPLE.ordinal());
      b.add("base-unit", NumberSkeletonImpl.StemEnum.STEM_BASE_UNIT.ordinal());
      b.add("percent", NumberSkeletonImpl.StemEnum.STEM_PERCENT.ordinal());
      b.add("permille", NumberSkeletonImpl.StemEnum.STEM_PERMILLE.ordinal());
      b.add("precision-integer", NumberSkeletonImpl.StemEnum.STEM_PRECISION_INTEGER.ordinal());
      b.add("precision-unlimited", NumberSkeletonImpl.StemEnum.STEM_PRECISION_UNLIMITED.ordinal());
      b.add("precision-currency-standard", NumberSkeletonImpl.StemEnum.STEM_PRECISION_CURRENCY_STANDARD.ordinal());
      b.add("precision-currency-cash", NumberSkeletonImpl.StemEnum.STEM_PRECISION_CURRENCY_CASH.ordinal());
      b.add("rounding-mode-ceiling", NumberSkeletonImpl.StemEnum.STEM_ROUNDING_MODE_CEILING.ordinal());
      b.add("rounding-mode-floor", NumberSkeletonImpl.StemEnum.STEM_ROUNDING_MODE_FLOOR.ordinal());
      b.add("rounding-mode-down", NumberSkeletonImpl.StemEnum.STEM_ROUNDING_MODE_DOWN.ordinal());
      b.add("rounding-mode-up", NumberSkeletonImpl.StemEnum.STEM_ROUNDING_MODE_UP.ordinal());
      b.add("rounding-mode-half-even", NumberSkeletonImpl.StemEnum.STEM_ROUNDING_MODE_HALF_EVEN.ordinal());
      b.add("rounding-mode-half-down", NumberSkeletonImpl.StemEnum.STEM_ROUNDING_MODE_HALF_DOWN.ordinal());
      b.add("rounding-mode-half-up", NumberSkeletonImpl.StemEnum.STEM_ROUNDING_MODE_HALF_UP.ordinal());
      b.add("rounding-mode-unnecessary", NumberSkeletonImpl.StemEnum.STEM_ROUNDING_MODE_UNNECESSARY.ordinal());
      b.add("integer-width-trunc", NumberSkeletonImpl.StemEnum.STEM_INTEGER_WIDTH_TRUNC.ordinal());
      b.add("group-off", NumberSkeletonImpl.StemEnum.STEM_GROUP_OFF.ordinal());
      b.add("group-min2", NumberSkeletonImpl.StemEnum.STEM_GROUP_MIN2.ordinal());
      b.add("group-auto", NumberSkeletonImpl.StemEnum.STEM_GROUP_AUTO.ordinal());
      b.add("group-on-aligned", NumberSkeletonImpl.StemEnum.STEM_GROUP_ON_ALIGNED.ordinal());
      b.add("group-thousands", NumberSkeletonImpl.StemEnum.STEM_GROUP_THOUSANDS.ordinal());
      b.add("latin", NumberSkeletonImpl.StemEnum.STEM_LATIN.ordinal());
      b.add("unit-width-narrow", NumberSkeletonImpl.StemEnum.STEM_UNIT_WIDTH_NARROW.ordinal());
      b.add("unit-width-short", NumberSkeletonImpl.StemEnum.STEM_UNIT_WIDTH_SHORT.ordinal());
      b.add("unit-width-full-name", NumberSkeletonImpl.StemEnum.STEM_UNIT_WIDTH_FULL_NAME.ordinal());
      b.add("unit-width-iso-code", NumberSkeletonImpl.StemEnum.STEM_UNIT_WIDTH_ISO_CODE.ordinal());
      b.add("unit-width-formal", NumberSkeletonImpl.StemEnum.STEM_UNIT_WIDTH_FORMAL.ordinal());
      b.add("unit-width-variant", NumberSkeletonImpl.StemEnum.STEM_UNIT_WIDTH_VARIANT.ordinal());
      b.add("unit-width-hidden", NumberSkeletonImpl.StemEnum.STEM_UNIT_WIDTH_HIDDEN.ordinal());
      b.add("sign-auto", NumberSkeletonImpl.StemEnum.STEM_SIGN_AUTO.ordinal());
      b.add("sign-always", NumberSkeletonImpl.StemEnum.STEM_SIGN_ALWAYS.ordinal());
      b.add("sign-never", NumberSkeletonImpl.StemEnum.STEM_SIGN_NEVER.ordinal());
      b.add("sign-accounting", NumberSkeletonImpl.StemEnum.STEM_SIGN_ACCOUNTING.ordinal());
      b.add("sign-accounting-always", NumberSkeletonImpl.StemEnum.STEM_SIGN_ACCOUNTING_ALWAYS.ordinal());
      b.add("sign-except-zero", NumberSkeletonImpl.StemEnum.STEM_SIGN_EXCEPT_ZERO.ordinal());
      b.add("sign-accounting-except-zero", NumberSkeletonImpl.StemEnum.STEM_SIGN_ACCOUNTING_EXCEPT_ZERO.ordinal());
      b.add("sign-negative", NumberSkeletonImpl.StemEnum.STEM_SIGN_NEGATIVE.ordinal());
      b.add("sign-accounting-negative", NumberSkeletonImpl.StemEnum.STEM_SIGN_ACCOUNTING_NEGATIVE.ordinal());
      b.add("decimal-auto", NumberSkeletonImpl.StemEnum.STEM_DECIMAL_AUTO.ordinal());
      b.add("decimal-always", NumberSkeletonImpl.StemEnum.STEM_DECIMAL_ALWAYS.ordinal());
      b.add("precision-increment", NumberSkeletonImpl.StemEnum.STEM_PRECISION_INCREMENT.ordinal());
      b.add("measure-unit", NumberSkeletonImpl.StemEnum.STEM_MEASURE_UNIT.ordinal());
      b.add("per-measure-unit", NumberSkeletonImpl.StemEnum.STEM_PER_MEASURE_UNIT.ordinal());
      b.add("unit", NumberSkeletonImpl.StemEnum.STEM_UNIT.ordinal());
      b.add("usage", NumberSkeletonImpl.StemEnum.STEM_UNIT_USAGE.ordinal());
      b.add("currency", NumberSkeletonImpl.StemEnum.STEM_CURRENCY.ordinal());
      b.add("integer-width", NumberSkeletonImpl.StemEnum.STEM_INTEGER_WIDTH.ordinal());
      b.add("numbering-system", NumberSkeletonImpl.StemEnum.STEM_NUMBERING_SYSTEM.ordinal());
      b.add("scale", NumberSkeletonImpl.StemEnum.STEM_SCALE.ordinal());
      b.add("K", NumberSkeletonImpl.StemEnum.STEM_COMPACT_SHORT.ordinal());
      b.add("KK", NumberSkeletonImpl.StemEnum.STEM_COMPACT_LONG.ordinal());
      b.add("%", NumberSkeletonImpl.StemEnum.STEM_PERCENT.ordinal());
      b.add("%x100", NumberSkeletonImpl.StemEnum.STEM_PERCENT_100.ordinal());
      b.add(",_", NumberSkeletonImpl.StemEnum.STEM_GROUP_OFF.ordinal());
      b.add(",?", NumberSkeletonImpl.StemEnum.STEM_GROUP_MIN2.ordinal());
      b.add(",!", NumberSkeletonImpl.StemEnum.STEM_GROUP_ON_ALIGNED.ordinal());
      b.add("+!", NumberSkeletonImpl.StemEnum.STEM_SIGN_ALWAYS.ordinal());
      b.add("+_", NumberSkeletonImpl.StemEnum.STEM_SIGN_NEVER.ordinal());
      b.add("()", NumberSkeletonImpl.StemEnum.STEM_SIGN_ACCOUNTING.ordinal());
      b.add("()!", NumberSkeletonImpl.StemEnum.STEM_SIGN_ACCOUNTING_ALWAYS.ordinal());
      b.add("+?", NumberSkeletonImpl.StemEnum.STEM_SIGN_EXCEPT_ZERO.ordinal());
      b.add("()?", NumberSkeletonImpl.StemEnum.STEM_SIGN_ACCOUNTING_EXCEPT_ZERO.ordinal());
      b.add("+-", NumberSkeletonImpl.StemEnum.STEM_SIGN_NEGATIVE.ordinal());
      b.add("()-", NumberSkeletonImpl.StemEnum.STEM_SIGN_ACCOUNTING_NEGATIVE.ordinal());
      return b.buildCharSequence(StringTrieBuilder.Option.FAST).toString();
   }

   public static UnlocalizedNumberFormatter getOrCreate(String skeletonString) {
      return cache.getInstance(skeletonString, null);
   }

   public static UnlocalizedNumberFormatter create(String skeletonString) {
      MacroProps macros = parseSkeleton(skeletonString);
      return NumberFormatter.with().macros(macros);
   }

   public static String generate(MacroProps macros) {
      StringBuilder sb = new StringBuilder();
      generateSkeleton(macros, sb);
      return sb.toString();
   }

   private static MacroProps parseSkeleton(String skeletonString) {
      skeletonString = skeletonString + " ";
      MacroProps macros = new MacroProps();
      StringSegment segment = new StringSegment(skeletonString, false);
      CharsTrie stemTrie = new CharsTrie(SERIALIZED_STEM_TRIE, 0);
      NumberSkeletonImpl.ParseState stem = NumberSkeletonImpl.ParseState.STATE_NULL;
      int offset = 0;

      while (offset < segment.length()) {
         int cp = segment.codePointAt(offset);
         boolean isTokenSeparator = PatternProps.isWhiteSpace(cp);
         boolean isOptionSeparator = cp == 47;
         if (!isTokenSeparator && !isOptionSeparator) {
            offset += Character.charCount(cp);
            if (stem == NumberSkeletonImpl.ParseState.STATE_NULL) {
               stemTrie.nextForCodePoint(cp);
            }
         } else {
            if (offset != 0) {
               segment.setLength(offset);
               if (stem == NumberSkeletonImpl.ParseState.STATE_NULL) {
                  stem = parseStem(segment, stemTrie, macros);
                  stemTrie.reset();
               } else {
                  stem = parseOption(stem, segment, macros);
               }

               segment.resetLength();
               segment.adjustOffset(offset);
               offset = 0;
            } else if (stem != NumberSkeletonImpl.ParseState.STATE_NULL) {
               segment.setLength(Character.charCount(cp));
               throw new SkeletonSyntaxException("Unexpected separator character", segment);
            }

            if (isOptionSeparator && stem == NumberSkeletonImpl.ParseState.STATE_NULL) {
               segment.setLength(Character.charCount(cp));
               throw new SkeletonSyntaxException("Unexpected option separator", segment);
            }

            if (isTokenSeparator && stem != NumberSkeletonImpl.ParseState.STATE_NULL) {
               switch (stem) {
                  case STATE_INCREMENT_PRECISION:
                  case STATE_MEASURE_UNIT:
                  case STATE_PER_MEASURE_UNIT:
                  case STATE_IDENTIFIER_UNIT:
                  case STATE_UNIT_USAGE:
                  case STATE_CURRENCY_UNIT:
                  case STATE_INTEGER_WIDTH:
                  case STATE_NUMBERING_SYSTEM:
                  case STATE_SCALE:
                     segment.setLength(Character.charCount(cp));
                     throw new SkeletonSyntaxException("Stem requires an option", segment);
                  default:
                     stem = NumberSkeletonImpl.ParseState.STATE_NULL;
               }
            }

            segment.adjustOffset(Character.charCount(cp));
         }
      }

      assert stem == NumberSkeletonImpl.ParseState.STATE_NULL;

      return macros;
   }

   private static NumberSkeletonImpl.ParseState parseStem(StringSegment segment, CharsTrie stemTrie, MacroProps macros) {
      switch (segment.charAt(0)) {
         case '.':
            checkNull(macros.precision, segment);
            NumberSkeletonImpl.BlueprintHelpers.parseFractionStem(segment, macros);
            return NumberSkeletonImpl.ParseState.STATE_FRACTION_PRECISION;
         case '0':
            checkNull(macros.integerWidth, segment);
            NumberSkeletonImpl.BlueprintHelpers.parseIntegerStem(segment, macros);
            return NumberSkeletonImpl.ParseState.STATE_NULL;
         case '@':
            checkNull(macros.precision, segment);
            NumberSkeletonImpl.BlueprintHelpers.parseDigitsStem(segment, macros);
            return NumberSkeletonImpl.ParseState.STATE_PRECISION;
         case 'E':
            checkNull(macros.notation, segment);
            NumberSkeletonImpl.BlueprintHelpers.parseScientificStem(segment, macros);
            return NumberSkeletonImpl.ParseState.STATE_NULL;
         default:
            BytesTrie.Result stemResult = stemTrie.current();
            if (stemResult != BytesTrie.Result.INTERMEDIATE_VALUE && stemResult != BytesTrie.Result.FINAL_VALUE) {
               throw new SkeletonSyntaxException("Unknown stem", segment);
            } else {
               NumberSkeletonImpl.StemEnum stem = STEM_ENUM_VALUES[stemTrie.getValue()];
               switch (stem) {
                  case STEM_COMPACT_SHORT:
                  case STEM_COMPACT_LONG:
                  case STEM_SCIENTIFIC:
                  case STEM_ENGINEERING:
                  case STEM_NOTATION_SIMPLE:
                     checkNull(macros.notation, segment);
                     macros.notation = NumberSkeletonImpl.StemToObject.notation(stem);
                     switch (stem) {
                        case STEM_SCIENTIFIC:
                        case STEM_ENGINEERING:
                           return NumberSkeletonImpl.ParseState.STATE_SCIENTIFIC;
                        default:
                           return NumberSkeletonImpl.ParseState.STATE_NULL;
                     }
                  case STEM_BASE_UNIT:
                  case STEM_PERCENT:
                  case STEM_PERMILLE:
                     checkNull(macros.unit, segment);
                     macros.unit = NumberSkeletonImpl.StemToObject.unit(stem);
                     return NumberSkeletonImpl.ParseState.STATE_NULL;
                  case STEM_PRECISION_INTEGER:
                  case STEM_PRECISION_UNLIMITED:
                  case STEM_PRECISION_CURRENCY_STANDARD:
                  case STEM_PRECISION_CURRENCY_CASH:
                     checkNull(macros.precision, segment);
                     macros.precision = NumberSkeletonImpl.StemToObject.precision(stem);
                     switch (stem) {
                        case STEM_PRECISION_INTEGER:
                           return NumberSkeletonImpl.ParseState.STATE_FRACTION_PRECISION;
                        default:
                           return NumberSkeletonImpl.ParseState.STATE_PRECISION;
                     }
                  case STEM_ROUNDING_MODE_CEILING:
                  case STEM_ROUNDING_MODE_FLOOR:
                  case STEM_ROUNDING_MODE_DOWN:
                  case STEM_ROUNDING_MODE_UP:
                  case STEM_ROUNDING_MODE_HALF_EVEN:
                  case STEM_ROUNDING_MODE_HALF_DOWN:
                  case STEM_ROUNDING_MODE_HALF_UP:
                  case STEM_ROUNDING_MODE_UNNECESSARY:
                     checkNull(macros.roundingMode, segment);
                     macros.roundingMode = NumberSkeletonImpl.StemToObject.roundingMode(stem);
                     return NumberSkeletonImpl.ParseState.STATE_NULL;
                  case STEM_GROUP_OFF:
                  case STEM_GROUP_MIN2:
                  case STEM_GROUP_AUTO:
                  case STEM_GROUP_ON_ALIGNED:
                  case STEM_GROUP_THOUSANDS:
                     checkNull(macros.grouping, segment);
                     macros.grouping = NumberSkeletonImpl.StemToObject.groupingStrategy(stem);
                     return NumberSkeletonImpl.ParseState.STATE_NULL;
                  case STEM_UNIT_WIDTH_NARROW:
                  case STEM_UNIT_WIDTH_SHORT:
                  case STEM_UNIT_WIDTH_FULL_NAME:
                  case STEM_UNIT_WIDTH_ISO_CODE:
                  case STEM_UNIT_WIDTH_FORMAL:
                  case STEM_UNIT_WIDTH_VARIANT:
                  case STEM_UNIT_WIDTH_HIDDEN:
                     checkNull(macros.unitWidth, segment);
                     macros.unitWidth = NumberSkeletonImpl.StemToObject.unitWidth(stem);
                     return NumberSkeletonImpl.ParseState.STATE_NULL;
                  case STEM_SIGN_AUTO:
                  case STEM_SIGN_ALWAYS:
                  case STEM_SIGN_NEVER:
                  case STEM_SIGN_ACCOUNTING:
                  case STEM_SIGN_ACCOUNTING_ALWAYS:
                  case STEM_SIGN_EXCEPT_ZERO:
                  case STEM_SIGN_ACCOUNTING_EXCEPT_ZERO:
                  case STEM_SIGN_NEGATIVE:
                  case STEM_SIGN_ACCOUNTING_NEGATIVE:
                     checkNull(macros.sign, segment);
                     macros.sign = NumberSkeletonImpl.StemToObject.signDisplay(stem);
                     return NumberSkeletonImpl.ParseState.STATE_NULL;
                  case STEM_DECIMAL_AUTO:
                  case STEM_DECIMAL_ALWAYS:
                     checkNull(macros.decimal, segment);
                     macros.decimal = NumberSkeletonImpl.StemToObject.decimalSeparatorDisplay(stem);
                     return NumberSkeletonImpl.ParseState.STATE_NULL;
                  case STEM_PERCENT_100:
                     checkNull(macros.scale, segment);
                     checkNull(macros.unit, segment);
                     macros.scale = Scale.powerOfTen(2);
                     macros.unit = NoUnit.PERCENT;
                     return NumberSkeletonImpl.ParseState.STATE_NULL;
                  case STEM_INTEGER_WIDTH_TRUNC:
                     checkNull(macros.integerWidth, segment);
                     macros.integerWidth = IntegerWidth.zeroFillTo(0).truncateAt(0);
                     return NumberSkeletonImpl.ParseState.STATE_NULL;
                  case STEM_LATIN:
                     checkNull(macros.symbols, segment);
                     macros.symbols = NumberingSystem.LATIN;
                     return NumberSkeletonImpl.ParseState.STATE_NULL;
                  case STEM_PRECISION_INCREMENT:
                     checkNull(macros.precision, segment);
                     return NumberSkeletonImpl.ParseState.STATE_INCREMENT_PRECISION;
                  case STEM_MEASURE_UNIT:
                     checkNull(macros.unit, segment);
                     return NumberSkeletonImpl.ParseState.STATE_MEASURE_UNIT;
                  case STEM_PER_MEASURE_UNIT:
                     if (macros.unit instanceof Currency) {
                        throw new SkeletonSyntaxException("Duplicated setting", segment);
                     }

                     checkNull(macros.perUnit, segment);
                     return NumberSkeletonImpl.ParseState.STATE_PER_MEASURE_UNIT;
                  case STEM_UNIT:
                     checkNull(macros.unit, segment);
                     checkNull(macros.perUnit, segment);
                     return NumberSkeletonImpl.ParseState.STATE_IDENTIFIER_UNIT;
                  case STEM_UNIT_USAGE:
                     checkNull(macros.usage, segment);
                     return NumberSkeletonImpl.ParseState.STATE_UNIT_USAGE;
                  case STEM_CURRENCY:
                     checkNull(macros.unit, segment);
                     checkNull(macros.perUnit, segment);
                     return NumberSkeletonImpl.ParseState.STATE_CURRENCY_UNIT;
                  case STEM_INTEGER_WIDTH:
                     checkNull(macros.integerWidth, segment);
                     return NumberSkeletonImpl.ParseState.STATE_INTEGER_WIDTH;
                  case STEM_NUMBERING_SYSTEM:
                     checkNull(macros.symbols, segment);
                     return NumberSkeletonImpl.ParseState.STATE_NUMBERING_SYSTEM;
                  case STEM_SCALE:
                     checkNull(macros.scale, segment);
                     return NumberSkeletonImpl.ParseState.STATE_SCALE;
                  default:
                     throw new AssertionError();
               }
            }
      }
   }

   private static NumberSkeletonImpl.ParseState parseOption(NumberSkeletonImpl.ParseState stem, StringSegment segment, MacroProps macros) {
      switch (stem) {
         case STATE_INCREMENT_PRECISION:
            NumberSkeletonImpl.BlueprintHelpers.parseIncrementOption(segment, macros);
            return NumberSkeletonImpl.ParseState.STATE_PRECISION;
         case STATE_MEASURE_UNIT:
            NumberSkeletonImpl.BlueprintHelpers.parseMeasureUnitOption(segment, macros);
            return NumberSkeletonImpl.ParseState.STATE_NULL;
         case STATE_PER_MEASURE_UNIT:
            NumberSkeletonImpl.BlueprintHelpers.parseMeasurePerUnitOption(segment, macros);
            return NumberSkeletonImpl.ParseState.STATE_NULL;
         case STATE_IDENTIFIER_UNIT:
            NumberSkeletonImpl.BlueprintHelpers.parseIdentifierUnitOption(segment, macros);
            return NumberSkeletonImpl.ParseState.STATE_NULL;
         case STATE_UNIT_USAGE:
            NumberSkeletonImpl.BlueprintHelpers.parseUnitUsageOption(segment, macros);
            return NumberSkeletonImpl.ParseState.STATE_NULL;
         case STATE_CURRENCY_UNIT:
            NumberSkeletonImpl.BlueprintHelpers.parseCurrencyOption(segment, macros);
            return NumberSkeletonImpl.ParseState.STATE_NULL;
         case STATE_INTEGER_WIDTH:
            NumberSkeletonImpl.BlueprintHelpers.parseIntegerWidthOption(segment, macros);
            return NumberSkeletonImpl.ParseState.STATE_NULL;
         case STATE_NUMBERING_SYSTEM:
            NumberSkeletonImpl.BlueprintHelpers.parseNumberingSystemOption(segment, macros);
            return NumberSkeletonImpl.ParseState.STATE_NULL;
         case STATE_SCALE:
            NumberSkeletonImpl.BlueprintHelpers.parseScaleOption(segment, macros);
            return NumberSkeletonImpl.ParseState.STATE_NULL;
         default:
            switch (stem) {
               case STATE_SCIENTIFIC:
                  if (NumberSkeletonImpl.BlueprintHelpers.parseExponentWidthOption(segment, macros)) {
                     return NumberSkeletonImpl.ParseState.STATE_SCIENTIFIC;
                  } else if (NumberSkeletonImpl.BlueprintHelpers.parseExponentSignOption(segment, macros)) {
                     return NumberSkeletonImpl.ParseState.STATE_SCIENTIFIC;
                  }
               default:
                  switch (stem) {
                     case STATE_FRACTION_PRECISION:
                        if (NumberSkeletonImpl.BlueprintHelpers.parseFracSigOption(segment, macros)) {
                           return NumberSkeletonImpl.ParseState.STATE_PRECISION;
                        } else {
                           stem = NumberSkeletonImpl.ParseState.STATE_PRECISION;
                        }
                     default:
                        switch (stem) {
                           case STATE_PRECISION:
                              if (NumberSkeletonImpl.BlueprintHelpers.parseTrailingZeroOption(segment, macros)) {
                                 return NumberSkeletonImpl.ParseState.STATE_NULL;
                              }
                           default:
                              throw new SkeletonSyntaxException("Invalid option", segment);
                        }
                  }
            }
      }
   }

   private static void generateSkeleton(MacroProps macros, StringBuilder sb) {
      if (macros.notation != null && NumberSkeletonImpl.GeneratorHelpers.notation(macros, sb)) {
         sb.append(' ');
      }

      if (macros.unit != null && NumberSkeletonImpl.GeneratorHelpers.unit(macros, sb)) {
         sb.append(' ');
      }

      if (macros.usage != null && NumberSkeletonImpl.GeneratorHelpers.usage(macros, sb)) {
         sb.append(' ');
      }

      if (macros.precision != null && NumberSkeletonImpl.GeneratorHelpers.precision(macros, sb)) {
         sb.append(' ');
      }

      if (macros.roundingMode != null && NumberSkeletonImpl.GeneratorHelpers.roundingMode(macros, sb)) {
         sb.append(' ');
      }

      if (macros.grouping != null && NumberSkeletonImpl.GeneratorHelpers.grouping(macros, sb)) {
         sb.append(' ');
      }

      if (macros.integerWidth != null && NumberSkeletonImpl.GeneratorHelpers.integerWidth(macros, sb)) {
         sb.append(' ');
      }

      if (macros.symbols != null && NumberSkeletonImpl.GeneratorHelpers.symbols(macros, sb)) {
         sb.append(' ');
      }

      if (macros.unitWidth != null && NumberSkeletonImpl.GeneratorHelpers.unitWidth(macros, sb)) {
         sb.append(' ');
      }

      if (macros.sign != null && NumberSkeletonImpl.GeneratorHelpers.sign(macros, sb)) {
         sb.append(' ');
      }

      if (macros.decimal != null && NumberSkeletonImpl.GeneratorHelpers.decimal(macros, sb)) {
         sb.append(' ');
      }

      if (macros.scale != null && NumberSkeletonImpl.GeneratorHelpers.scale(macros, sb)) {
         sb.append(' ');
      }

      if (macros.padder != null) {
         throw new UnsupportedOperationException("Cannot generate number skeleton with custom padder");
      } else if (macros.unitDisplayCase != null && !macros.unitDisplayCase.isEmpty()) {
         throw new UnsupportedOperationException("Cannot generate number skeleton with custom unit display case");
      } else if (macros.affixProvider != null) {
         throw new UnsupportedOperationException("Cannot generate number skeleton with custom affix provider");
      } else if (macros.rules != null) {
         throw new UnsupportedOperationException("Cannot generate number skeleton with custom plural rules");
      } else {
         if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
         }
      }
   }

   private static void checkNull(Object value, CharSequence content) {
      if (value != null) {
         throw new SkeletonSyntaxException("Duplicated setting", content);
      }
   }

   private static void appendMultiple(StringBuilder sb, int cp, int count) {
      for (int i = 0; i < count; i++) {
         sb.appendCodePoint(cp);
      }
   }

   static final class BlueprintHelpers {
      private static boolean parseExponentWidthOption(StringSegment segment, MacroProps macros) {
         if (!NumberSkeletonImpl.isWildcardChar(segment.charAt(0))) {
            return false;
         } else {
            int offset = 1;

            int minExp;
            for (minExp = 0; offset < segment.length() && segment.charAt(offset) == 'e'; offset++) {
               minExp++;
            }

            if (offset < segment.length()) {
               return false;
            } else {
               macros.notation = ((ScientificNotation)macros.notation).withMinExponentDigits(minExp);
               return true;
            }
         }
      }

      private static void generateExponentWidthOption(int minExponentDigits, StringBuilder sb) {
         sb.append('*');
         NumberSkeletonImpl.appendMultiple(sb, 101, minExponentDigits);
      }

      private static boolean parseExponentSignOption(StringSegment segment, MacroProps macros) {
         CharsTrie tempStemTrie = new CharsTrie(NumberSkeletonImpl.SERIALIZED_STEM_TRIE, 0);
         BytesTrie.Result result = tempStemTrie.next(segment, 0, segment.length());
         if (result != BytesTrie.Result.INTERMEDIATE_VALUE && result != BytesTrie.Result.FINAL_VALUE) {
            return false;
         } else {
            NumberFormatter.SignDisplay sign = NumberSkeletonImpl.StemToObject.signDisplay(NumberSkeletonImpl.STEM_ENUM_VALUES[tempStemTrie.getValue()]);
            if (sign == null) {
               return false;
            } else {
               macros.notation = ((ScientificNotation)macros.notation).withExponentSignDisplay(sign);
               return true;
            }
         }
      }

      private static void parseCurrencyOption(StringSegment segment, MacroProps macros) {
         String currencyCode = segment.subSequence(0, segment.length()).toString();

         Currency currency;
         try {
            currency = Currency.getInstance(currencyCode);
         } catch (IllegalArgumentException var5) {
            throw new SkeletonSyntaxException("Invalid currency", segment, var5);
         }

         macros.unit = currency;
      }

      private static void generateCurrencyOption(Currency currency, StringBuilder sb) {
         sb.append(currency.getCurrencyCode());
      }

      private static void parseMeasureUnitOption(StringSegment segment, MacroProps macros) {
         int firstHyphen = 0;

         while (firstHyphen < segment.length() && segment.charAt(firstHyphen) != '-') {
            firstHyphen++;
         }

         if (firstHyphen == segment.length()) {
            throw new SkeletonSyntaxException("Invalid measure unit option", segment);
         } else {
            String type = segment.subSequence(0, firstHyphen).toString();
            String subType = segment.subSequence(firstHyphen + 1, segment.length()).toString();

            for (MeasureUnit unit : MeasureUnit.getAvailable(type)) {
               if (subType.equals(unit.getSubtype())) {
                  macros.unit = unit;
                  return;
               }
            }

            throw new SkeletonSyntaxException("Unknown measure unit", segment);
         }
      }

      private static void parseMeasurePerUnitOption(StringSegment segment, MacroProps macros) {
         MeasureUnit numerator = macros.unit;
         parseMeasureUnitOption(segment, macros);
         macros.perUnit = macros.unit;
         macros.unit = numerator;
      }

      private static void parseIdentifierUnitOption(StringSegment segment, MacroProps macros) {
         try {
            macros.unit = MeasureUnit.forIdentifier(segment.asString());
         } catch (IllegalArgumentException var3) {
            throw new SkeletonSyntaxException("Invalid unit stem", segment);
         }
      }

      private static void parseUnitUsageOption(StringSegment segment, MacroProps macros) {
         macros.usage = segment.asString();
      }

      private static void parseFractionStem(StringSegment segment, MacroProps macros) {
         assert segment.charAt(0) == '.';

         int offset = 1;

         int minFrac;
         for (minFrac = 0; offset < segment.length() && segment.charAt(offset) == '0'; offset++) {
            minFrac++;
         }

         int maxFrac;
         if (offset < segment.length()) {
            if (NumberSkeletonImpl.isWildcardChar(segment.charAt(offset))) {
               maxFrac = -1;
               offset++;
            } else {
               for (maxFrac = minFrac; offset < segment.length() && segment.charAt(offset) == '#'; offset++) {
                  maxFrac++;
               }
            }
         } else {
            maxFrac = minFrac;
         }

         if (offset < segment.length()) {
            throw new SkeletonSyntaxException("Invalid fraction stem", segment);
         } else {
            if (maxFrac == -1) {
               if (minFrac == 0) {
                  macros.precision = Precision.unlimited();
               } else {
                  macros.precision = Precision.minFraction(minFrac);
               }
            } else {
               macros.precision = Precision.minMaxFraction(minFrac, maxFrac);
            }
         }
      }

      private static void generateFractionStem(int minFrac, int maxFrac, StringBuilder sb) {
         if (minFrac == 0 && maxFrac == 0) {
            sb.append("precision-integer");
         } else {
            sb.append('.');
            NumberSkeletonImpl.appendMultiple(sb, 48, minFrac);
            if (maxFrac == -1) {
               sb.append('*');
            } else {
               NumberSkeletonImpl.appendMultiple(sb, 35, maxFrac - minFrac);
            }
         }
      }

      private static void parseDigitsStem(StringSegment segment, MacroProps macros) {
         assert segment.charAt(0) == '@';

         int offset = 0;

         int minSig;
         for (minSig = 0; offset < segment.length() && segment.charAt(offset) == '@'; offset++) {
            minSig++;
         }

         int maxSig;
         if (offset < segment.length()) {
            if (NumberSkeletonImpl.isWildcardChar(segment.charAt(offset))) {
               maxSig = -1;
               offset++;
            } else {
               for (maxSig = minSig; offset < segment.length() && segment.charAt(offset) == '#'; offset++) {
                  maxSig++;
               }
            }
         } else {
            maxSig = minSig;
         }

         if (offset < segment.length()) {
            throw new SkeletonSyntaxException("Invalid significant digits stem", segment);
         } else {
            if (maxSig == -1) {
               macros.precision = Precision.minSignificantDigits(minSig);
            } else {
               macros.precision = Precision.minMaxSignificantDigits(minSig, maxSig);
            }
         }
      }

      private static void generateDigitsStem(int minSig, int maxSig, StringBuilder sb) {
         NumberSkeletonImpl.appendMultiple(sb, 64, minSig);
         if (maxSig == -1) {
            sb.append('*');
         } else {
            NumberSkeletonImpl.appendMultiple(sb, 35, maxSig - minSig);
         }
      }

      private static void parseScientificStem(StringSegment segment, MacroProps macros) {
         assert segment.charAt(0) == 'E';

         int offset = 1;
         if (segment.length() != offset) {
            boolean isEngineering = false;
            if (segment.charAt(offset) == 'E') {
               isEngineering = true;
               if (segment.length() == ++offset) {
                  throw new SkeletonSyntaxException("Invalid scientific stem", segment);
               }
            }

            NumberFormatter.SignDisplay signDisplay = NumberFormatter.SignDisplay.AUTO;
            if (segment.charAt(offset) == '+') {
               if (segment.length() == ++offset) {
                  throw new SkeletonSyntaxException("Invalid scientific stem", segment);
               }

               if (segment.charAt(offset) == '!') {
                  signDisplay = NumberFormatter.SignDisplay.ALWAYS;
               } else {
                  if (segment.charAt(offset) != '?') {
                     throw new SkeletonSyntaxException("Invalid scientific stem", segment);
                  }

                  signDisplay = NumberFormatter.SignDisplay.EXCEPT_ZERO;
               }

               if (segment.length() == ++offset) {
                  throw new SkeletonSyntaxException("Invalid scientific stem", segment);
               }
            }

            int minDigits = 0;

            while (true) {
               if (offset >= segment.length()) {
                  macros.notation = (isEngineering ? Notation.engineering() : Notation.scientific())
                     .withExponentSignDisplay(signDisplay)
                     .withMinExponentDigits(minDigits);
                  return;
               }

               if (segment.charAt(offset) != '0') {
                  break;
               }

               minDigits++;
               offset++;
            }
         }

         throw new SkeletonSyntaxException("Invalid scientific stem", segment);
      }

      private static void parseIntegerStem(StringSegment segment, MacroProps macros) {
         assert segment.charAt(0) == '0';

         int offset;
         for (offset = 1; offset < segment.length(); offset++) {
            if (segment.charAt(offset) != '0') {
               offset--;
               break;
            }
         }

         if (offset < segment.length()) {
            throw new SkeletonSyntaxException("Invalid integer stem", segment);
         } else {
            macros.integerWidth = IntegerWidth.zeroFillTo(offset);
         }
      }

      private static boolean parseFracSigOption(StringSegment segment, MacroProps macros) {
         if (segment.charAt(0) != '@') {
            return false;
         } else {
            int offset = 0;

            int minSig;
            for (minSig = 0; offset < segment.length() && segment.charAt(offset) == '@'; offset++) {
               minSig++;
            }

            int maxSig;
            if (offset < segment.length()) {
               if (NumberSkeletonImpl.isWildcardChar(segment.charAt(offset))) {
                  maxSig = -1;
                  offset++;
               } else {
                  for (maxSig = minSig; offset < segment.length() && segment.charAt(offset) == '#'; offset++) {
                     maxSig++;
                  }
               }
            } else {
               maxSig = minSig;
            }

            FractionPrecision oldRounder = (FractionPrecision)macros.precision;
            if (offset < segment.length()) {
               if (maxSig == -1) {
                  throw new SkeletonSyntaxException("Invalid digits option: Wildcard character not allowed with the priority annotation", segment);
               }

               NumberFormatter.RoundingPriority priority;
               if (segment.codePointAt(offset) == 114) {
                  priority = NumberFormatter.RoundingPriority.RELAXED;
                  offset++;
               } else if (segment.codePointAt(offset) == 115) {
                  priority = NumberFormatter.RoundingPriority.STRICT;
                  offset++;
               } else {
                  assert offset < segment.length();

                  priority = NumberFormatter.RoundingPriority.RELAXED;
               }

               if (offset < segment.length()) {
                  throw new SkeletonSyntaxException("Invalid digits option for fraction rounder", segment);
               }

               macros.precision = oldRounder.withSignificantDigits(minSig, maxSig, priority);
            } else if (maxSig == -1) {
               macros.precision = oldRounder.withMinDigits(minSig);
            } else {
               if (minSig != 1) {
                  throw new SkeletonSyntaxException("Invalid digits option: Priority annotation required", segment);
               }

               macros.precision = oldRounder.withMaxDigits(maxSig);
            }

            return true;
         }
      }

      private static boolean parseTrailingZeroOption(StringSegment segment, MacroProps macros) {
         if (segment.contentEquals("w")) {
            macros.precision = macros.precision.trailingZeroDisplay(NumberFormatter.TrailingZeroDisplay.HIDE_IF_WHOLE);
            return true;
         } else {
            return false;
         }
      }

      private static void parseIncrementOption(StringSegment segment, MacroProps macros) {
         String str = segment.subSequence(0, segment.length()).toString();

         BigDecimal increment;
         try {
            increment = new BigDecimal(str);
         } catch (NumberFormatException var5) {
            throw new SkeletonSyntaxException("Invalid rounding increment", segment, var5);
         }

         macros.precision = Precision.increment(increment);
      }

      private static void generateIncrementOption(BigDecimal increment, StringBuilder sb) {
         sb.append(increment.toPlainString());
      }

      private static void parseIntegerWidthOption(StringSegment segment, MacroProps macros) {
         int offset = 0;
         int minInt = 0;
         int maxInt;
         if (NumberSkeletonImpl.isWildcardChar(segment.charAt(0))) {
            maxInt = -1;
            offset++;
         } else {
            maxInt = 0;
         }

         while (offset < segment.length() && maxInt != -1 && segment.charAt(offset) == '#') {
            maxInt++;
            offset++;
         }

         if (offset < segment.length()) {
            while (offset < segment.length() && segment.charAt(offset) == '0') {
               minInt++;
               offset++;
            }
         }

         if (maxInt != -1) {
            maxInt += minInt;
         }

         if (offset < segment.length()) {
            throw new SkeletonSyntaxException("Invalid integer width stem", segment);
         } else {
            if (maxInt == -1) {
               macros.integerWidth = IntegerWidth.zeroFillTo(minInt);
            } else {
               macros.integerWidth = IntegerWidth.zeroFillTo(minInt).truncateAt(maxInt);
            }
         }
      }

      private static void generateIntegerWidthOption(int minInt, int maxInt, StringBuilder sb) {
         if (maxInt == -1) {
            sb.append('*');
         } else {
            NumberSkeletonImpl.appendMultiple(sb, 35, maxInt - minInt);
         }

         NumberSkeletonImpl.appendMultiple(sb, 48, minInt);
      }

      private static void parseNumberingSystemOption(StringSegment segment, MacroProps macros) {
         String nsName = segment.subSequence(0, segment.length()).toString();
         NumberingSystem ns = NumberingSystem.getInstanceByName(nsName);
         if (ns == null) {
            throw new SkeletonSyntaxException("Unknown numbering system", segment);
         } else {
            macros.symbols = ns;
         }
      }

      private static void generateNumberingSystemOption(NumberingSystem ns, StringBuilder sb) {
         sb.append(ns.getName());
      }

      private static void parseScaleOption(StringSegment segment, MacroProps macros) {
         String str = segment.subSequence(0, segment.length()).toString();

         BigDecimal bd;
         try {
            bd = new BigDecimal(str);
         } catch (NumberFormatException var5) {
            throw new SkeletonSyntaxException("Invalid scale", segment, var5);
         }

         macros.scale = Scale.byBigDecimal(bd);
      }

      private static void generateScaleOption(Scale scale, StringBuilder sb) {
         BigDecimal bd = scale.arbitrary;
         if (bd == null) {
            bd = BigDecimal.ONE;
         }

         bd = bd.scaleByPowerOfTen(scale.magnitude);
         sb.append(bd.toPlainString());
      }
   }

   static final class EnumToStemString {
      private static void roundingMode(RoundingMode value, StringBuilder sb) {
         switch (value) {
            case CEILING:
               sb.append("rounding-mode-ceiling");
               break;
            case FLOOR:
               sb.append("rounding-mode-floor");
               break;
            case DOWN:
               sb.append("rounding-mode-down");
               break;
            case UP:
               sb.append("rounding-mode-up");
               break;
            case HALF_EVEN:
               sb.append("rounding-mode-half-even");
               break;
            case HALF_DOWN:
               sb.append("rounding-mode-half-down");
               break;
            case HALF_UP:
               sb.append("rounding-mode-half-up");
               break;
            case UNNECESSARY:
               sb.append("rounding-mode-unnecessary");
               break;
            default:
               throw new AssertionError();
         }
      }

      private static void groupingStrategy(NumberFormatter.GroupingStrategy value, StringBuilder sb) {
         switch (value) {
            case OFF:
               sb.append("group-off");
               break;
            case MIN2:
               sb.append("group-min2");
               break;
            case AUTO:
               sb.append("group-auto");
               break;
            case ON_ALIGNED:
               sb.append("group-on-aligned");
               break;
            case THOUSANDS:
               sb.append("group-thousands");
               break;
            default:
               throw new AssertionError();
         }
      }

      private static void unitWidth(NumberFormatter.UnitWidth value, StringBuilder sb) {
         switch (value) {
            case NARROW:
               sb.append("unit-width-narrow");
               break;
            case SHORT:
               sb.append("unit-width-short");
               break;
            case FULL_NAME:
               sb.append("unit-width-full-name");
               break;
            case ISO_CODE:
               sb.append("unit-width-iso-code");
               break;
            case FORMAL:
               sb.append("unit-width-formal");
               break;
            case VARIANT:
               sb.append("unit-width-variant");
               break;
            case HIDDEN:
               sb.append("unit-width-hidden");
               break;
            default:
               throw new AssertionError();
         }
      }

      private static void signDisplay(NumberFormatter.SignDisplay value, StringBuilder sb) {
         switch (value) {
            case AUTO:
               sb.append("sign-auto");
               break;
            case ALWAYS:
               sb.append("sign-always");
               break;
            case NEVER:
               sb.append("sign-never");
               break;
            case ACCOUNTING:
               sb.append("sign-accounting");
               break;
            case ACCOUNTING_ALWAYS:
               sb.append("sign-accounting-always");
               break;
            case EXCEPT_ZERO:
               sb.append("sign-except-zero");
               break;
            case ACCOUNTING_EXCEPT_ZERO:
               sb.append("sign-accounting-except-zero");
               break;
            case NEGATIVE:
               sb.append("sign-negative");
               break;
            case ACCOUNTING_NEGATIVE:
               sb.append("sign-accounting-negative");
               break;
            default:
               throw new AssertionError();
         }
      }

      private static void decimalSeparatorDisplay(NumberFormatter.DecimalSeparatorDisplay value, StringBuilder sb) {
         switch (value) {
            case AUTO:
               sb.append("decimal-auto");
               break;
            case ALWAYS:
               sb.append("decimal-always");
               break;
            default:
               throw new AssertionError();
         }
      }
   }

   static final class GeneratorHelpers {
      private static boolean notation(MacroProps macros, StringBuilder sb) {
         if (macros.notation instanceof CompactNotation) {
            if (macros.notation == Notation.compactLong()) {
               sb.append("compact-long");
               return true;
            } else if (macros.notation == Notation.compactShort()) {
               sb.append("compact-short");
               return true;
            } else {
               throw new UnsupportedOperationException("Cannot generate number skeleton with custom compact data");
            }
         } else if (macros.notation instanceof ScientificNotation) {
            ScientificNotation impl = (ScientificNotation)macros.notation;
            if (impl.engineeringInterval == 3) {
               sb.append("engineering");
            } else {
               sb.append("scientific");
            }

            if (impl.minExponentDigits > 1) {
               sb.append('/');
               NumberSkeletonImpl.BlueprintHelpers.generateExponentWidthOption(impl.minExponentDigits, sb);
            }

            if (impl.exponentSignDisplay != NumberFormatter.SignDisplay.AUTO) {
               sb.append('/');
               NumberSkeletonImpl.EnumToStemString.signDisplay(impl.exponentSignDisplay, sb);
            }

            return true;
         } else {
            assert macros.notation instanceof SimpleNotation;

            return false;
         }
      }

      private static boolean unit(MacroProps macros, StringBuilder sb) {
         MeasureUnit unit = macros.unit;
         if (macros.perUnit != null) {
            if (macros.unit instanceof Currency || macros.perUnit instanceof Currency) {
               throw new UnsupportedOperationException("Cannot generate number skeleton with currency unit and per-unit");
            }

            unit = unit.product(macros.perUnit.reciprocal());
         }

         if (unit instanceof Currency) {
            sb.append("currency/");
            NumberSkeletonImpl.BlueprintHelpers.generateCurrencyOption((Currency)unit, sb);
            return true;
         } else if (unit.equals(MeasureUnit.PERCENT)) {
            sb.append("percent");
            return true;
         } else if (unit.equals(MeasureUnit.PERMILLE)) {
            sb.append("permille");
            return true;
         } else {
            sb.append("unit/");
            sb.append(unit.getIdentifier());
            return true;
         }
      }

      private static boolean usage(MacroProps macros, StringBuilder sb) {
         if (macros.usage != null && macros.usage.length() > 0) {
            sb.append("usage/");
            sb.append(macros.usage);
            return true;
         } else {
            return false;
         }
      }

      private static boolean precision(MacroProps macros, StringBuilder sb) {
         if (macros.precision instanceof Precision.InfiniteRounderImpl) {
            sb.append("precision-unlimited");
         } else if (macros.precision instanceof Precision.FractionRounderImpl) {
            Precision.FractionRounderImpl impl = (Precision.FractionRounderImpl)macros.precision;
            NumberSkeletonImpl.BlueprintHelpers.generateFractionStem(impl.minFrac, impl.maxFrac, sb);
         } else if (macros.precision instanceof Precision.SignificantRounderImpl) {
            Precision.SignificantRounderImpl impl = (Precision.SignificantRounderImpl)macros.precision;
            NumberSkeletonImpl.BlueprintHelpers.generateDigitsStem(impl.minSig, impl.maxSig, sb);
         } else if (macros.precision instanceof Precision.FracSigRounderImpl) {
            Precision.FracSigRounderImpl impl = (Precision.FracSigRounderImpl)macros.precision;
            NumberSkeletonImpl.BlueprintHelpers.generateFractionStem(impl.minFrac, impl.maxFrac, sb);
            sb.append('/');
            if (impl.retain) {
               if (impl.priority == NumberFormatter.RoundingPriority.RELAXED) {
                  NumberSkeletonImpl.BlueprintHelpers.generateDigitsStem(impl.maxSig, -1, sb);
               } else {
                  NumberSkeletonImpl.BlueprintHelpers.generateDigitsStem(1, impl.maxSig, sb);
               }
            } else {
               NumberSkeletonImpl.BlueprintHelpers.generateDigitsStem(impl.minSig, impl.maxSig, sb);
               if (impl.priority == NumberFormatter.RoundingPriority.RELAXED) {
                  sb.append('r');
               } else {
                  sb.append('s');
               }
            }
         } else if (macros.precision instanceof Precision.IncrementRounderImpl) {
            Precision.IncrementRounderImpl impl = (Precision.IncrementRounderImpl)macros.precision;
            sb.append("precision-increment/");
            NumberSkeletonImpl.BlueprintHelpers.generateIncrementOption(impl.increment, sb);
         } else {
            assert macros.precision instanceof Precision.CurrencyRounderImpl;

            Precision.CurrencyRounderImpl impl = (Precision.CurrencyRounderImpl)macros.precision;
            if (impl.usage == Currency.CurrencyUsage.STANDARD) {
               sb.append("precision-currency-standard");
            } else {
               sb.append("precision-currency-cash");
            }
         }

         if (macros.precision.trailingZeroDisplay == NumberFormatter.TrailingZeroDisplay.HIDE_IF_WHOLE) {
            sb.append("/w");
         }

         return true;
      }

      private static boolean roundingMode(MacroProps macros, StringBuilder sb) {
         if (macros.roundingMode == RoundingUtils.DEFAULT_ROUNDING_MODE) {
            return false;
         } else {
            NumberSkeletonImpl.EnumToStemString.roundingMode(macros.roundingMode, sb);
            return true;
         }
      }

      private static boolean grouping(MacroProps macros, StringBuilder sb) {
         if (macros.grouping instanceof NumberFormatter.GroupingStrategy) {
            if (macros.grouping == NumberFormatter.GroupingStrategy.AUTO) {
               return false;
            } else {
               NumberSkeletonImpl.EnumToStemString.groupingStrategy((NumberFormatter.GroupingStrategy)macros.grouping, sb);
               return true;
            }
         } else {
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom Grouper");
         }
      }

      private static boolean integerWidth(MacroProps macros, StringBuilder sb) {
         if (macros.integerWidth.equals(IntegerWidth.DEFAULT)) {
            return false;
         } else if (macros.integerWidth.minInt == 0 && macros.integerWidth.maxInt == 0) {
            sb.append("integer-width-trunc");
            return true;
         } else {
            sb.append("integer-width/");
            NumberSkeletonImpl.BlueprintHelpers.generateIntegerWidthOption(macros.integerWidth.minInt, macros.integerWidth.maxInt, sb);
            return true;
         }
      }

      private static boolean symbols(MacroProps macros, StringBuilder sb) {
         if (macros.symbols instanceof NumberingSystem) {
            NumberingSystem ns = (NumberingSystem)macros.symbols;
            if (ns.getName().equals("latn")) {
               sb.append("latin");
            } else {
               sb.append("numbering-system/");
               NumberSkeletonImpl.BlueprintHelpers.generateNumberingSystemOption(ns, sb);
            }

            return true;
         } else {
            assert macros.symbols instanceof DecimalFormatSymbols;

            throw new UnsupportedOperationException("Cannot generate number skeleton with custom DecimalFormatSymbols");
         }
      }

      private static boolean unitWidth(MacroProps macros, StringBuilder sb) {
         if (macros.unitWidth == NumberFormatter.UnitWidth.SHORT) {
            return false;
         } else {
            NumberSkeletonImpl.EnumToStemString.unitWidth(macros.unitWidth, sb);
            return true;
         }
      }

      private static boolean sign(MacroProps macros, StringBuilder sb) {
         if (macros.sign == NumberFormatter.SignDisplay.AUTO) {
            return false;
         } else {
            NumberSkeletonImpl.EnumToStemString.signDisplay(macros.sign, sb);
            return true;
         }
      }

      private static boolean decimal(MacroProps macros, StringBuilder sb) {
         if (macros.decimal == NumberFormatter.DecimalSeparatorDisplay.AUTO) {
            return false;
         } else {
            NumberSkeletonImpl.EnumToStemString.decimalSeparatorDisplay(macros.decimal, sb);
            return true;
         }
      }

      private static boolean scale(MacroProps macros, StringBuilder sb) {
         if (!macros.scale.isValid()) {
            return false;
         } else {
            sb.append("scale/");
            NumberSkeletonImpl.BlueprintHelpers.generateScaleOption(macros.scale, sb);
            return true;
         }
      }
   }

   static enum ParseState {
      STATE_NULL,
      STATE_SCIENTIFIC,
      STATE_FRACTION_PRECISION,
      STATE_PRECISION,
      STATE_INCREMENT_PRECISION,
      STATE_MEASURE_UNIT,
      STATE_PER_MEASURE_UNIT,
      STATE_IDENTIFIER_UNIT,
      STATE_UNIT_USAGE,
      STATE_CURRENCY_UNIT,
      STATE_INTEGER_WIDTH,
      STATE_NUMBERING_SYSTEM,
      STATE_SCALE;
   }

   static enum StemEnum {
      STEM_COMPACT_SHORT,
      STEM_COMPACT_LONG,
      STEM_SCIENTIFIC,
      STEM_ENGINEERING,
      STEM_NOTATION_SIMPLE,
      STEM_BASE_UNIT,
      STEM_PERCENT,
      STEM_PERMILLE,
      STEM_PERCENT_100,
      STEM_PRECISION_INTEGER,
      STEM_PRECISION_UNLIMITED,
      STEM_PRECISION_CURRENCY_STANDARD,
      STEM_PRECISION_CURRENCY_CASH,
      STEM_ROUNDING_MODE_CEILING,
      STEM_ROUNDING_MODE_FLOOR,
      STEM_ROUNDING_MODE_DOWN,
      STEM_ROUNDING_MODE_UP,
      STEM_ROUNDING_MODE_HALF_EVEN,
      STEM_ROUNDING_MODE_HALF_DOWN,
      STEM_ROUNDING_MODE_HALF_UP,
      STEM_ROUNDING_MODE_UNNECESSARY,
      STEM_INTEGER_WIDTH_TRUNC,
      STEM_GROUP_OFF,
      STEM_GROUP_MIN2,
      STEM_GROUP_AUTO,
      STEM_GROUP_ON_ALIGNED,
      STEM_GROUP_THOUSANDS,
      STEM_LATIN,
      STEM_UNIT_WIDTH_NARROW,
      STEM_UNIT_WIDTH_SHORT,
      STEM_UNIT_WIDTH_FULL_NAME,
      STEM_UNIT_WIDTH_ISO_CODE,
      STEM_UNIT_WIDTH_FORMAL,
      STEM_UNIT_WIDTH_VARIANT,
      STEM_UNIT_WIDTH_HIDDEN,
      STEM_SIGN_AUTO,
      STEM_SIGN_ALWAYS,
      STEM_SIGN_NEVER,
      STEM_SIGN_ACCOUNTING,
      STEM_SIGN_ACCOUNTING_ALWAYS,
      STEM_SIGN_EXCEPT_ZERO,
      STEM_SIGN_ACCOUNTING_EXCEPT_ZERO,
      STEM_SIGN_NEGATIVE,
      STEM_SIGN_ACCOUNTING_NEGATIVE,
      STEM_DECIMAL_AUTO,
      STEM_DECIMAL_ALWAYS,
      STEM_PRECISION_INCREMENT,
      STEM_MEASURE_UNIT,
      STEM_PER_MEASURE_UNIT,
      STEM_UNIT,
      STEM_UNIT_USAGE,
      STEM_CURRENCY,
      STEM_INTEGER_WIDTH,
      STEM_NUMBERING_SYSTEM,
      STEM_SCALE;
   }

   static final class StemToObject {
      private static Notation notation(NumberSkeletonImpl.StemEnum stem) {
         switch (stem) {
            case STEM_COMPACT_SHORT:
               return Notation.compactShort();
            case STEM_COMPACT_LONG:
               return Notation.compactLong();
            case STEM_SCIENTIFIC:
               return Notation.scientific();
            case STEM_ENGINEERING:
               return Notation.engineering();
            case STEM_NOTATION_SIMPLE:
               return Notation.simple();
            default:
               throw new AssertionError();
         }
      }

      private static MeasureUnit unit(NumberSkeletonImpl.StemEnum stem) {
         switch (stem) {
            case STEM_BASE_UNIT:
               return NoUnit.BASE;
            case STEM_PERCENT:
               return NoUnit.PERCENT;
            case STEM_PERMILLE:
               return NoUnit.PERMILLE;
            default:
               throw new AssertionError();
         }
      }

      private static Precision precision(NumberSkeletonImpl.StemEnum stem) {
         switch (stem) {
            case STEM_PRECISION_INTEGER:
               return Precision.integer();
            case STEM_PRECISION_UNLIMITED:
               return Precision.unlimited();
            case STEM_PRECISION_CURRENCY_STANDARD:
               return Precision.currency(Currency.CurrencyUsage.STANDARD);
            case STEM_PRECISION_CURRENCY_CASH:
               return Precision.currency(Currency.CurrencyUsage.CASH);
            default:
               throw new AssertionError();
         }
      }

      private static RoundingMode roundingMode(NumberSkeletonImpl.StemEnum stem) {
         switch (stem) {
            case STEM_ROUNDING_MODE_CEILING:
               return RoundingMode.CEILING;
            case STEM_ROUNDING_MODE_FLOOR:
               return RoundingMode.FLOOR;
            case STEM_ROUNDING_MODE_DOWN:
               return RoundingMode.DOWN;
            case STEM_ROUNDING_MODE_UP:
               return RoundingMode.UP;
            case STEM_ROUNDING_MODE_HALF_EVEN:
               return RoundingMode.HALF_EVEN;
            case STEM_ROUNDING_MODE_HALF_DOWN:
               return RoundingMode.HALF_DOWN;
            case STEM_ROUNDING_MODE_HALF_UP:
               return RoundingMode.HALF_UP;
            case STEM_ROUNDING_MODE_UNNECESSARY:
               return RoundingMode.UNNECESSARY;
            default:
               throw new AssertionError();
         }
      }

      private static NumberFormatter.GroupingStrategy groupingStrategy(NumberSkeletonImpl.StemEnum stem) {
         switch (stem) {
            case STEM_GROUP_OFF:
               return NumberFormatter.GroupingStrategy.OFF;
            case STEM_GROUP_MIN2:
               return NumberFormatter.GroupingStrategy.MIN2;
            case STEM_GROUP_AUTO:
               return NumberFormatter.GroupingStrategy.AUTO;
            case STEM_GROUP_ON_ALIGNED:
               return NumberFormatter.GroupingStrategy.ON_ALIGNED;
            case STEM_GROUP_THOUSANDS:
               return NumberFormatter.GroupingStrategy.THOUSANDS;
            default:
               return null;
         }
      }

      private static NumberFormatter.UnitWidth unitWidth(NumberSkeletonImpl.StemEnum stem) {
         switch (stem) {
            case STEM_UNIT_WIDTH_NARROW:
               return NumberFormatter.UnitWidth.NARROW;
            case STEM_UNIT_WIDTH_SHORT:
               return NumberFormatter.UnitWidth.SHORT;
            case STEM_UNIT_WIDTH_FULL_NAME:
               return NumberFormatter.UnitWidth.FULL_NAME;
            case STEM_UNIT_WIDTH_ISO_CODE:
               return NumberFormatter.UnitWidth.ISO_CODE;
            case STEM_UNIT_WIDTH_FORMAL:
               return NumberFormatter.UnitWidth.FORMAL;
            case STEM_UNIT_WIDTH_VARIANT:
               return NumberFormatter.UnitWidth.VARIANT;
            case STEM_UNIT_WIDTH_HIDDEN:
               return NumberFormatter.UnitWidth.HIDDEN;
            default:
               return null;
         }
      }

      private static NumberFormatter.SignDisplay signDisplay(NumberSkeletonImpl.StemEnum stem) {
         switch (stem) {
            case STEM_SIGN_AUTO:
               return NumberFormatter.SignDisplay.AUTO;
            case STEM_SIGN_ALWAYS:
               return NumberFormatter.SignDisplay.ALWAYS;
            case STEM_SIGN_NEVER:
               return NumberFormatter.SignDisplay.NEVER;
            case STEM_SIGN_ACCOUNTING:
               return NumberFormatter.SignDisplay.ACCOUNTING;
            case STEM_SIGN_ACCOUNTING_ALWAYS:
               return NumberFormatter.SignDisplay.ACCOUNTING_ALWAYS;
            case STEM_SIGN_EXCEPT_ZERO:
               return NumberFormatter.SignDisplay.EXCEPT_ZERO;
            case STEM_SIGN_ACCOUNTING_EXCEPT_ZERO:
               return NumberFormatter.SignDisplay.ACCOUNTING_EXCEPT_ZERO;
            case STEM_SIGN_NEGATIVE:
               return NumberFormatter.SignDisplay.NEGATIVE;
            case STEM_SIGN_ACCOUNTING_NEGATIVE:
               return NumberFormatter.SignDisplay.ACCOUNTING_NEGATIVE;
            default:
               return null;
         }
      }

      private static NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay(NumberSkeletonImpl.StemEnum stem) {
         switch (stem) {
            case STEM_DECIMAL_AUTO:
               return NumberFormatter.DecimalSeparatorDisplay.AUTO;
            case STEM_DECIMAL_ALWAYS:
               return NumberFormatter.DecimalSeparatorDisplay.ALWAYS;
            default:
               return null;
         }
      }
   }
}
