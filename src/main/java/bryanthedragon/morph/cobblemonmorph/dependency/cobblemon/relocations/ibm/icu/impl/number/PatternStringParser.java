package com.cobblemon.mod.relocations.ibm.icu.impl.number;

public class PatternStringParser {
   public static final int IGNORE_ROUNDING_NEVER = 0;
   public static final int IGNORE_ROUNDING_IF_CURRENCY = 1;
   public static final int IGNORE_ROUNDING_ALWAYS = 2;

   public static PatternStringParser.ParsedPatternInfo parseToPatternInfo(String patternString) {
      PatternStringParser.ParserState state = new PatternStringParser.ParserState(patternString);
      PatternStringParser.ParsedPatternInfo result = new PatternStringParser.ParsedPatternInfo(patternString);
      consumePattern(state, result);
      return result;
   }

   public static DecimalFormatProperties parseToProperties(String pattern, int ignoreRounding) {
      DecimalFormatProperties properties = new DecimalFormatProperties();
      parseToExistingPropertiesImpl(pattern, properties, ignoreRounding);
      return properties;
   }

   public static DecimalFormatProperties parseToProperties(String pattern) {
      return parseToProperties(pattern, 0);
   }

   public static void parseToExistingProperties(String pattern, DecimalFormatProperties properties, int ignoreRounding) {
      parseToExistingPropertiesImpl(pattern, properties, ignoreRounding);
   }

   public static void parseToExistingProperties(String pattern, DecimalFormatProperties properties) {
      parseToExistingProperties(pattern, properties, 0);
   }

   private static void consumePattern(PatternStringParser.ParserState state, PatternStringParser.ParsedPatternInfo result) {
      result.positive = new PatternStringParser.ParsedSubpatternInfo();
      consumeSubpattern(state, result.positive);
      if (state.peek() == 59) {
         state.next();
         if (state.peek() != -1) {
            result.negative = new PatternStringParser.ParsedSubpatternInfo();
            consumeSubpattern(state, result.negative);
         }
      }

      if (state.peek() != -1) {
         throw state.toParseException("Found unquoted special character");
      }
   }

   private static void consumeSubpattern(PatternStringParser.ParserState state, PatternStringParser.ParsedSubpatternInfo result) {
      consumePadding(state, result, Padder.PadPosition.BEFORE_PREFIX);
      result.prefixEndpoints = consumeAffix(state, result);
      consumePadding(state, result, Padder.PadPosition.AFTER_PREFIX);
      consumeFormat(state, result);
      consumeExponent(state, result);
      consumePadding(state, result, Padder.PadPosition.BEFORE_SUFFIX);
      result.suffixEndpoints = consumeAffix(state, result);
      consumePadding(state, result, Padder.PadPosition.AFTER_SUFFIX);
   }

   private static void consumePadding(
      PatternStringParser.ParserState state, PatternStringParser.ParsedSubpatternInfo result, Padder.PadPosition paddingLocation
   ) {
      if (state.peek() == 42) {
         if (result.paddingLocation != null) {
            throw state.toParseException("Cannot have multiple pad specifiers");
         } else {
            result.paddingLocation = paddingLocation;
            state.next();
            result.paddingEndpoints = result.paddingEndpoints | state.offset;
            consumeLiteral(state);
            result.paddingEndpoints = result.paddingEndpoints | (long)state.offset << 32;
         }
      }
   }

   private static long consumeAffix(PatternStringParser.ParserState state, PatternStringParser.ParsedSubpatternInfo result) {
      long endpoints = state.offset;

      while (true) {
         switch (state.peek()) {
            case -1:
            case 35:
            case 42:
            case 44:
            case 46:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 59:
            case 64:
               return endpoints | (long)state.offset << 32;
            case 37:
               result.hasPercentSign = true;
               break;
            case 43:
               result.hasPlusSign = true;
               break;
            case 45:
               result.hasMinusSign = true;
               break;
            case 164:
               result.hasCurrencySign = true;
               break;
            case 8240:
               result.hasPerMilleSign = true;
         }

         consumeLiteral(state);
      }
   }

   private static void consumeLiteral(PatternStringParser.ParserState state) {
      if (state.peek() == -1) {
         throw state.toParseException("Expected unquoted literal but found EOL");
      } else {
         if (state.peek() == 39) {
            state.next();

            while (state.peek() != 39) {
               if (state.peek() == -1) {
                  throw state.toParseException("Expected quoted literal but found EOL");
               }

               state.next();
            }

            state.next();
         } else {
            state.next();
         }
      }
   }

   private static void consumeFormat(PatternStringParser.ParserState state, PatternStringParser.ParsedSubpatternInfo result) {
      consumeIntegerFormat(state, result);
      if (state.peek() == 46) {
         state.next();
         result.hasDecimal = true;
         result.widthExceptAffixes++;
         consumeFractionFormat(state, result);
      } else if (state.peek() == 164) {
         switch (state.peek2()) {
            case 35:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
               result.hasCurrencySign = true;
               result.hasCurrencyDecimal = true;
               result.hasDecimal = true;
               result.widthExceptAffixes++;
               state.next();
               consumeFractionFormat(state, result);
               break;
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            default:
               return;
         }
      }
   }

   private static void consumeIntegerFormat(PatternStringParser.ParserState state, PatternStringParser.ParsedSubpatternInfo result) {
      while (true) {
         switch (state.peek()) {
            case 35:
               if (result.integerNumerals > 0) {
                  throw state.toParseException("# cannot follow 0 before decimal point");
               }

               result.widthExceptAffixes++;
               result.groupingSizes++;
               if (result.integerAtSigns > 0) {
                  result.integerTrailingHashSigns++;
               } else {
                  result.integerLeadingHashSigns++;
               }

               result.integerTotal++;
               break;
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 45:
            case 46:
            case 47:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            default:
               short grouping1 = (short)(result.groupingSizes & 65535L);
               short grouping2 = (short)(result.groupingSizes >>> 16 & 65535L);
               short grouping3 = (short)(result.groupingSizes >>> 32 & 65535L);
               if (grouping1 == 0 && grouping2 != -1) {
                  throw state.toParseException("Trailing grouping separator is invalid");
               }

               if (grouping2 == 0 && grouping3 != -1) {
                  throw state.toParseException("Grouping width of zero is invalid");
               }

               return;
            case 44:
               result.widthExceptAffixes++;
               result.groupingSizes <<= 16;
               break;
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
               if (result.integerAtSigns > 0) {
                  throw state.toParseException("Cannot mix @ and 0");
               }

               result.widthExceptAffixes++;
               result.groupingSizes++;
               result.integerNumerals++;
               result.integerTotal++;
               if (state.peek() != 48 && result.rounding == null) {
                  result.rounding = new DecimalQuantity_DualStorageBCD();
               }

               if (result.rounding != null) {
                  result.rounding.appendDigit((byte)(state.peek() - 48), 0, true);
               }
               break;
            case 64:
               if (result.integerNumerals > 0) {
                  throw state.toParseException("Cannot mix 0 and @");
               }

               if (result.integerTrailingHashSigns > 0) {
                  throw state.toParseException("Cannot nest # inside of a run of @");
               }

               result.widthExceptAffixes++;
               result.groupingSizes++;
               result.integerAtSigns++;
               result.integerTotal++;
         }

         state.next();
      }
   }

   private static void consumeFractionFormat(PatternStringParser.ParserState state, PatternStringParser.ParsedSubpatternInfo result) {
      int zeroCounter = 0;

      while (true) {
         switch (state.peek()) {
            case 35:
               result.widthExceptAffixes++;
               result.fractionHashSigns++;
               result.fractionTotal++;
               zeroCounter++;
               break;
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            default:
               return;
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
               if (result.fractionHashSigns > 0) {
                  throw state.toParseException("0 cannot follow # after decimal point");
               }

               result.widthExceptAffixes++;
               result.fractionNumerals++;
               result.fractionTotal++;
               if (state.peek() == 48) {
                  zeroCounter++;
               } else {
                  if (result.rounding == null) {
                     result.rounding = new DecimalQuantity_DualStorageBCD();
                  }

                  result.rounding.appendDigit((byte)(state.peek() - 48), zeroCounter, false);
                  zeroCounter = 0;
               }
         }

         state.next();
      }
   }

   private static void consumeExponent(PatternStringParser.ParserState state, PatternStringParser.ParsedSubpatternInfo result) {
      if (state.peek() == 69) {
         if ((result.groupingSizes & 4294901760L) != 4294901760L) {
            throw state.toParseException("Cannot have grouping separator in scientific notation");
         } else {
            state.next();
            result.widthExceptAffixes++;
            if (state.peek() == 43) {
               state.next();
               result.exponentHasPlusSign = true;
               result.widthExceptAffixes++;
            }

            while (state.peek() == 48) {
               state.next();
               result.exponentZeros++;
               result.widthExceptAffixes++;
            }
         }
      }
   }

   private static void parseToExistingPropertiesImpl(String pattern, DecimalFormatProperties properties, int ignoreRounding) {
      if (pattern != null && pattern.length() != 0) {
         PatternStringParser.ParsedPatternInfo patternInfo = parseToPatternInfo(pattern);
         patternInfoToProperties(properties, patternInfo, ignoreRounding);
      } else {
         properties.clear();
      }
   }

   private static void patternInfoToProperties(DecimalFormatProperties properties, PatternStringParser.ParsedPatternInfo patternInfo, int _ignoreRounding) {
      PatternStringParser.ParsedSubpatternInfo positive = patternInfo.positive;
      boolean ignoreRounding;
      if (_ignoreRounding == 0) {
         ignoreRounding = false;
      } else if (_ignoreRounding == 1) {
         ignoreRounding = positive.hasCurrencySign;
      } else {
         assert _ignoreRounding == 2;

         ignoreRounding = true;
      }

      short grouping1 = (short)(positive.groupingSizes & 65535L);
      short grouping2 = (short)(positive.groupingSizes >>> 16 & 65535L);
      short grouping3 = (short)(positive.groupingSizes >>> 32 & 65535L);
      if (grouping2 != -1) {
         properties.setGroupingSize(grouping1);
         properties.setGroupingUsed(true);
      } else {
         properties.setGroupingSize(-1);
         properties.setGroupingUsed(false);
      }

      if (grouping3 != -1) {
         properties.setSecondaryGroupingSize(grouping2);
      } else {
         properties.setSecondaryGroupingSize(-1);
      }

      int minInt;
      int minFrac;
      if (positive.integerTotal == 0 && positive.fractionTotal > 0) {
         minInt = 0;
         minFrac = Math.max(1, positive.fractionNumerals);
      } else if (positive.integerNumerals == 0 && positive.fractionNumerals == 0) {
         minInt = 1;
         minFrac = 0;
      } else {
         minInt = positive.integerNumerals;
         minFrac = positive.fractionNumerals;
      }

      if (positive.integerAtSigns > 0) {
         properties.setMinimumFractionDigits(-1);
         properties.setMaximumFractionDigits(-1);
         properties.setRoundingIncrement(null);
         properties.setMinimumSignificantDigits(positive.integerAtSigns);
         properties.setMaximumSignificantDigits(positive.integerAtSigns + positive.integerTrailingHashSigns);
      } else if (positive.rounding != null) {
         if (!ignoreRounding) {
            properties.setMinimumFractionDigits(minFrac);
            properties.setMaximumFractionDigits(positive.fractionTotal);
            properties.setRoundingIncrement(positive.rounding.toBigDecimal().setScale(positive.fractionNumerals));
         } else {
            properties.setMinimumFractionDigits(-1);
            properties.setMaximumFractionDigits(-1);
            properties.setRoundingIncrement(null);
         }

         properties.setMinimumSignificantDigits(-1);
         properties.setMaximumSignificantDigits(-1);
      } else {
         if (!ignoreRounding) {
            properties.setMinimumFractionDigits(minFrac);
            properties.setMaximumFractionDigits(positive.fractionTotal);
            properties.setRoundingIncrement(null);
         } else {
            properties.setMinimumFractionDigits(-1);
            properties.setMaximumFractionDigits(-1);
            properties.setRoundingIncrement(null);
         }

         properties.setMinimumSignificantDigits(-1);
         properties.setMaximumSignificantDigits(-1);
      }

      if (positive.hasDecimal && positive.fractionTotal == 0) {
         properties.setDecimalSeparatorAlwaysShown(true);
      } else {
         properties.setDecimalSeparatorAlwaysShown(false);
      }

      properties.setCurrencyAsDecimal(positive.hasCurrencyDecimal);
      if (positive.exponentZeros > 0) {
         properties.setExponentSignAlwaysShown(positive.exponentHasPlusSign);
         properties.setMinimumExponentDigits(positive.exponentZeros);
         if (positive.integerAtSigns == 0) {
            properties.setMinimumIntegerDigits(positive.integerNumerals);
            properties.setMaximumIntegerDigits(positive.integerTotal);
         } else {
            properties.setMinimumIntegerDigits(1);
            properties.setMaximumIntegerDigits(-1);
         }
      } else {
         properties.setExponentSignAlwaysShown(false);
         properties.setMinimumExponentDigits(-1);
         properties.setMinimumIntegerDigits(minInt);
         properties.setMaximumIntegerDigits(-1);
      }

      String posPrefix = patternInfo.getString(256);
      String posSuffix = patternInfo.getString(0);
      if (positive.paddingLocation != null) {
         int paddingWidth = positive.widthExceptAffixes + AffixUtils.estimateLength(posPrefix) + AffixUtils.estimateLength(posSuffix);
         properties.setFormatWidth(paddingWidth);
         String rawPaddingString = patternInfo.getString(1024);
         if (rawPaddingString.length() == 1) {
            properties.setPadString(rawPaddingString);
         } else if (rawPaddingString.length() == 2) {
            if (rawPaddingString.charAt(0) == '\'') {
               properties.setPadString("'");
            } else {
               properties.setPadString(rawPaddingString);
            }
         } else {
            properties.setPadString(rawPaddingString.substring(1, rawPaddingString.length() - 1));
         }

         assert positive.paddingLocation != null;

         properties.setPadPosition(positive.paddingLocation);
      } else {
         properties.setFormatWidth(-1);
         properties.setPadString(null);
         properties.setPadPosition(null);
      }

      properties.setPositivePrefixPattern(posPrefix);
      properties.setPositiveSuffixPattern(posSuffix);
      if (patternInfo.negative != null) {
         properties.setNegativePrefixPattern(patternInfo.getString(768));
         properties.setNegativeSuffixPattern(patternInfo.getString(512));
      } else {
         properties.setNegativePrefixPattern(null);
         properties.setNegativeSuffixPattern(null);
      }

      if (positive.hasPercentSign) {
         properties.setMagnitudeMultiplier(2);
      } else if (positive.hasPerMilleSign) {
         properties.setMagnitudeMultiplier(3);
      } else {
         properties.setMagnitudeMultiplier(0);
      }
   }

   public static class ParsedPatternInfo implements AffixPatternProvider {
      public String pattern;
      public PatternStringParser.ParsedSubpatternInfo positive;
      public PatternStringParser.ParsedSubpatternInfo negative;

      private ParsedPatternInfo(String pattern) {
         this.pattern = pattern;
      }

      @Override
      public char charAt(int flags, int index) {
         long endpoints = this.getEndpoints(flags);
         int left = (int)(endpoints & -1L);
         int right = (int)(endpoints >>> 32);
         if (index >= 0 && index < right - left) {
            return this.pattern.charAt(left + index);
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      @Override
      public int length(int flags) {
         return getLengthFromEndpoints(this.getEndpoints(flags));
      }

      public static int getLengthFromEndpoints(long endpoints) {
         int left = (int)(endpoints & -1L);
         int right = (int)(endpoints >>> 32);
         return right - left;
      }

      @Override
      public String getString(int flags) {
         long endpoints = this.getEndpoints(flags);
         int left = (int)(endpoints & -1L);
         int right = (int)(endpoints >>> 32);
         return left == right ? "" : this.pattern.substring(left, right);
      }

      private long getEndpoints(int flags) {
         boolean prefix = (flags & 256) != 0;
         boolean isNegative = (flags & 512) != 0;
         boolean padding = (flags & 1024) != 0;
         if (isNegative && padding) {
            return this.negative.paddingEndpoints;
         } else if (padding) {
            return this.positive.paddingEndpoints;
         } else if (prefix && isNegative) {
            return this.negative.prefixEndpoints;
         } else if (prefix) {
            return this.positive.prefixEndpoints;
         } else {
            return isNegative ? this.negative.suffixEndpoints : this.positive.suffixEndpoints;
         }
      }

      @Override
      public boolean positiveHasPlusSign() {
         return this.positive.hasPlusSign;
      }

      @Override
      public boolean hasNegativeSubpattern() {
         return this.negative != null;
      }

      @Override
      public boolean negativeHasMinusSign() {
         return this.negative.hasMinusSign;
      }

      @Override
      public boolean hasCurrencySign() {
         return this.positive.hasCurrencySign || this.negative != null && this.negative.hasCurrencySign;
      }

      @Override
      public boolean containsSymbolType(int type) {
         return AffixUtils.containsType(this.pattern, type);
      }

      @Override
      public boolean hasBody() {
         return this.positive.integerTotal > 0;
      }

      @Override
      public boolean currencyAsDecimal() {
         return this.positive.hasCurrencyDecimal;
      }
   }

   public static class ParsedSubpatternInfo {
      public long groupingSizes = 281474976645120L;
      public int integerLeadingHashSigns = 0;
      public int integerTrailingHashSigns = 0;
      public int integerNumerals = 0;
      public int integerAtSigns = 0;
      public int integerTotal = 0;
      public int fractionNumerals = 0;
      public int fractionHashSigns = 0;
      public int fractionTotal = 0;
      public boolean hasDecimal = false;
      public int widthExceptAffixes = 0;
      public Padder.PadPosition paddingLocation = null;
      public DecimalQuantity_DualStorageBCD rounding = null;
      public boolean exponentHasPlusSign = false;
      public int exponentZeros = 0;
      public boolean hasPercentSign = false;
      public boolean hasPerMilleSign = false;
      public boolean hasCurrencySign = false;
      public boolean hasCurrencyDecimal = false;
      public boolean hasMinusSign = false;
      public boolean hasPlusSign = false;
      public long prefixEndpoints = 0L;
      public long suffixEndpoints = 0L;
      public long paddingEndpoints = 0L;
   }

   private static class ParserState {
      final String pattern;
      int offset;

      ParserState(String pattern) {
         this.pattern = pattern;
         this.offset = 0;
      }

      int peek() {
         return this.offset == this.pattern.length() ? -1 : this.pattern.codePointAt(this.offset);
      }

      int peek2() {
         if (this.offset == this.pattern.length()) {
            return -1;
         } else {
            int cp1 = this.pattern.codePointAt(this.offset);
            int offset2 = this.offset + Character.charCount(cp1);
            return offset2 == this.pattern.length() ? -1 : this.pattern.codePointAt(offset2);
         }
      }

      int next() {
         int codePoint = this.peek();
         this.offset = this.offset + Character.charCount(codePoint);
         return codePoint;
      }

      IllegalArgumentException toParseException(String message) {
         StringBuilder sb = new StringBuilder();
         sb.append("Malformed pattern for ICU DecimalFormat: \"");
         sb.append(this.pattern);
         sb.append("\": ");
         sb.append(message);
         sb.append(" at position ");
         sb.append(this.offset);
         return new IllegalArgumentException(sb.toString());
      }
   }
}
