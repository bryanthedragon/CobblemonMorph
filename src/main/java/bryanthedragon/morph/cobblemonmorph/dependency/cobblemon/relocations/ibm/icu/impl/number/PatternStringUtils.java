package com.cobblemon.mod.relocations.ibm.icu.impl.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import com.cobblemon.mod.relocations.ibm.icu.number.NumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.text.DecimalFormatSymbols;
import java.math.BigDecimal;

public class PatternStringUtils {
   public static boolean ignoreRoundingIncrement(BigDecimal roundIncrDec, int maxFrac) {
      double roundIncr = roundIncrDec.doubleValue();
      if (roundIncr == 0.0) {
         return true;
      } else if (maxFrac < 0) {
         return false;
      } else {
         int frac = 0;
         roundIncr *= 2.0;

         for (frac = 0; frac <= maxFrac && roundIncr <= 1.0; roundIncr *= 10.0) {
            frac++;
         }

         return frac > maxFrac;
      }
   }

   public static String propertiesToPatternString(DecimalFormatProperties properties) {
      StringBuilder sb = new StringBuilder();
      int dosMax = 100;
      int grouping1 = Math.max(0, Math.min(properties.getGroupingSize(), dosMax));
      int grouping2 = Math.max(0, Math.min(properties.getSecondaryGroupingSize(), dosMax));
      boolean useGrouping = properties.getGroupingUsed();
      int paddingWidth = Math.min(properties.getFormatWidth(), dosMax);
      Padder.PadPosition paddingLocation = properties.getPadPosition();
      String paddingString = properties.getPadString();
      int minInt = Math.max(0, Math.min(properties.getMinimumIntegerDigits(), dosMax));
      int maxInt = Math.min(properties.getMaximumIntegerDigits(), dosMax);
      int minFrac = Math.max(0, Math.min(properties.getMinimumFractionDigits(), dosMax));
      int maxFrac = Math.min(properties.getMaximumFractionDigits(), dosMax);
      int minSig = Math.min(properties.getMinimumSignificantDigits(), dosMax);
      int maxSig = Math.min(properties.getMaximumSignificantDigits(), dosMax);
      boolean alwaysShowDecimal = properties.getDecimalSeparatorAlwaysShown();
      boolean currencyAsDecimal = properties.getCurrencyAsDecimal();
      int exponentDigits = Math.min(properties.getMinimumExponentDigits(), dosMax);
      boolean exponentShowPlusSign = properties.getExponentSignAlwaysShown();
      AffixPatternProvider affixes = PropertiesAffixPatternProvider.forProperties(properties);
      sb.append(affixes.getString(256));
      int afterPrefixPos = sb.length();
      if (!useGrouping) {
         grouping1 = 0;
         grouping2 = 0;
      } else if (grouping1 == grouping2) {
         grouping1 = 0;
      }

      int groupingLength = grouping1 + grouping2 + 1;
      BigDecimal roundingInterval = properties.getRoundingIncrement();
      StringBuilder digitsString = new StringBuilder();
      int digitsStringScale = 0;
      if (maxSig == Math.min(dosMax, -1)) {
         if (roundingInterval != null && !ignoreRoundingIncrement(roundingInterval, maxFrac)) {
            digitsStringScale = -roundingInterval.scale();
            String str = roundingInterval.scaleByPowerOfTen(roundingInterval.scale()).toPlainString();
            if (str.charAt(0) == '-') {
               digitsString.append(str, 1, str.length());
            } else {
               digitsString.append(str);
            }
         }
      } else {
         while (digitsString.length() < minSig) {
            digitsString.append('@');
         }

         while (digitsString.length() < maxSig) {
            digitsString.append('#');
         }
      }

      while (digitsString.length() + digitsStringScale < minInt) {
         digitsString.insert(0, '0');
      }

      while (-digitsStringScale < minFrac) {
         digitsString.append('0');
         digitsStringScale--;
      }

      int m0 = Math.max(groupingLength, digitsString.length() + digitsStringScale);
      m0 = maxInt != dosMax ? Math.max(maxInt, m0) - 1 : m0 - 1;
      int mN = maxFrac != dosMax ? Math.min(-maxFrac, digitsStringScale) : digitsStringScale;

      for (int magnitude = m0; magnitude >= mN; magnitude--) {
         int di = digitsString.length() + digitsStringScale - magnitude - 1;
         if (di >= 0 && di < digitsString.length()) {
            sb.append(digitsString.charAt(di));
         } else {
            sb.append('#');
         }

         if (magnitude == 0 && (alwaysShowDecimal || mN < 0)) {
            if (currencyAsDecimal) {
               sb.append('¤');
            } else {
               sb.append('.');
            }
         }

         if (useGrouping) {
            if (magnitude > 0 && magnitude == grouping1) {
               sb.append(',');
            }

            if (magnitude > grouping1 && grouping2 > 0 && (magnitude - grouping1) % grouping2 == 0) {
               sb.append(',');
            }
         }
      }

      if (exponentDigits != Math.min(dosMax, -1)) {
         sb.append('E');
         if (exponentShowPlusSign) {
            sb.append('+');
         }

         for (int i = 0; i < exponentDigits; i++) {
            sb.append('0');
         }
      }

      int beforeSuffixPos = sb.length();
      sb.append(affixes.getString(0));
      if (paddingWidth > 0) {
         while (paddingWidth - sb.length() > 0) {
            sb.insert(afterPrefixPos, '#');
            beforeSuffixPos++;
         }

         switch (paddingLocation) {
            case BEFORE_PREFIX: {
               int addedLength = escapePaddingString(paddingString, sb, 0);
               sb.insert(0, '*');
               afterPrefixPos += addedLength + 1;
               beforeSuffixPos += addedLength + 1;
               break;
            }
            case AFTER_PREFIX: {
               int addedLength = escapePaddingString(paddingString, sb, afterPrefixPos);
               sb.insert(afterPrefixPos, '*');
               afterPrefixPos += addedLength + 1;
               beforeSuffixPos += addedLength + 1;
               break;
            }
            case BEFORE_SUFFIX:
               escapePaddingString(paddingString, sb, beforeSuffixPos);
               sb.insert(beforeSuffixPos, '*');
               break;
            case AFTER_SUFFIX:
               sb.append('*');
               escapePaddingString(paddingString, sb, sb.length());
         }
      }

      if (affixes.hasNegativeSubpattern()) {
         sb.append(';');
         sb.append(affixes.getString(768));
         sb.append(sb, afterPrefixPos, beforeSuffixPos);
         sb.append(affixes.getString(512));
      }

      return sb.toString();
   }

   private static int escapePaddingString(CharSequence input, StringBuilder output, int startIndex) {
      if (input == null || input.length() == 0) {
         input = " ";
      }

      int startLength = output.length();
      if (input.length() == 1) {
         if (input.equals("'")) {
            output.insert(startIndex, "''");
         } else {
            output.insert(startIndex, input);
         }
      } else {
         output.insert(startIndex, '\'');
         int offset = 1;

         for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch == '\'') {
               output.insert(startIndex + offset, "''");
               offset += 2;
            } else {
               output.insert(startIndex + offset, ch);
               offset++;
            }
         }

         output.insert(startIndex + offset, '\'');
      }

      return output.length() - startLength;
   }

   public static String convertLocalized(String input, DecimalFormatSymbols symbols, boolean toLocalized) {
      if (input == null) {
         return null;
      } else {
         String[][] table = new String[21][2];
         int standIdx = toLocalized ? 0 : 1;
         int localIdx = toLocalized ? 1 : 0;
         table[0][standIdx] = "%";
         table[0][localIdx] = symbols.getPercentString();
         table[1][standIdx] = "‰";
         table[1][localIdx] = symbols.getPerMillString();
         table[2][standIdx] = ".";
         table[2][localIdx] = symbols.getDecimalSeparatorString();
         table[3][standIdx] = ",";
         table[3][localIdx] = symbols.getGroupingSeparatorString();
         table[4][standIdx] = "-";
         table[4][localIdx] = symbols.getMinusSignString();
         table[5][standIdx] = "+";
         table[5][localIdx] = symbols.getPlusSignString();
         table[6][standIdx] = ";";
         table[6][localIdx] = Character.toString(symbols.getPatternSeparator());
         table[7][standIdx] = "@";
         table[7][localIdx] = Character.toString(symbols.getSignificantDigit());
         table[8][standIdx] = "E";
         table[8][localIdx] = symbols.getExponentSeparator();
         table[9][standIdx] = "*";
         table[9][localIdx] = Character.toString(symbols.getPadEscape());
         table[10][standIdx] = "#";
         table[10][localIdx] = Character.toString(symbols.getDigit());

         for (int i = 0; i < 10; i++) {
            table[11 + i][standIdx] = Character.toString((char)(48 + i));
            table[11 + i][localIdx] = symbols.getDigitStringsLocal()[i];
         }

         for (int i = 0; i < table.length; i++) {
            table[i][localIdx] = table[i][localIdx].replace('\'', '’');
         }

         StringBuilder result = new StringBuilder();
         int state = 0;

         label134:
         for (int offset = 0; offset < input.length(); offset++) {
            char ch = input.charAt(offset);
            if (ch == '\'') {
               if (state == 0) {
                  result.append('\'');
                  state = 1;
               } else if (state == 1) {
                  result.append('\'');
                  state = 0;
               } else if (state == 2) {
                  state = 3;
               } else if (state == 3) {
                  result.append('\'');
                  result.append('\'');
                  state = 1;
               } else if (state == 4) {
                  state = 5;
               } else {
                  assert state == 5;

                  result.append('\'');
                  result.append('\'');
                  state = 4;
               }
            } else if (state != 0 && state != 3 && state != 4) {
               assert state == 1 || state == 2 || state == 5;

               result.append(ch);
               state = 2;
            } else {
               for (String[] pair : table) {
                  if (input.regionMatches(offset, pair[0], 0, pair[0].length())) {
                     offset += pair[0].length() - 1;
                     if (state == 3 || state == 4) {
                        result.append('\'');
                        state = 0;
                     }

                     result.append(pair[1]);
                     continue label134;
                  }
               }

               for (String[] pairx : table) {
                  if (input.regionMatches(offset, pairx[1], 0, pairx[1].length())) {
                     if (state == 0) {
                        result.append('\'');
                        state = 4;
                     }

                     result.append(ch);
                     continue label134;
                  }
               }

               if (state == 3 || state == 4) {
                  result.append('\'');
                  state = 0;
               }

               result.append(ch);
            }
         }

         if (state == 3 || state == 4) {
            result.append('\'');
            state = 0;
         }

         if (state != 0) {
            throw new IllegalArgumentException("Malformed localized pattern: unterminated quote");
         } else {
            return result.toString();
         }
      }
   }

   public static void patternInfoToStringBuilder(
      AffixPatternProvider patternInfo,
      boolean isPrefix,
      PatternStringUtils.PatternSignType patternSignType,
      boolean approximately,
      StandardPlural plural,
      boolean perMilleReplacesPercent,
      StringBuilder output
   ) {
      boolean plusReplacesMinusSign = patternSignType == PatternStringUtils.PatternSignType.POS_SIGN && !patternInfo.positiveHasPlusSign();
      boolean useNegativeAffixPattern = patternInfo.hasNegativeSubpattern()
         && (patternSignType == PatternStringUtils.PatternSignType.NEG || patternInfo.negativeHasMinusSign() && (plusReplacesMinusSign || approximately));
      int flags = 0;
      if (useNegativeAffixPattern) {
         flags |= 512;
      }

      if (isPrefix) {
         flags |= 256;
      }

      if (plural != null) {
         assert plural.ordinal() == (0xFF & plural.ordinal());

         flags |= plural.ordinal();
      }

      boolean prependSign;
      if (!isPrefix || useNegativeAffixPattern) {
         prependSign = false;
      } else if (patternSignType == PatternStringUtils.PatternSignType.NEG) {
         prependSign = true;
      } else {
         prependSign = plusReplacesMinusSign || approximately;
      }

      String signSymbols = "-";
      if (approximately) {
         if (plusReplacesMinusSign) {
            signSymbols = "~+";
         } else if (patternSignType == PatternStringUtils.PatternSignType.NEG) {
            signSymbols = "~-";
         } else {
            signSymbols = "~";
         }
      } else if (plusReplacesMinusSign) {
         signSymbols = "+";
      }

      int length = patternInfo.length(flags) + (prependSign ? 1 : 0);
      output.setLength(0);

      for (int index = 0; index < length; index++) {
         char candidate;
         if (prependSign && index == 0) {
            candidate = '-';
         } else if (prependSign) {
            candidate = patternInfo.charAt(flags, index - 1);
         } else {
            candidate = patternInfo.charAt(flags, index);
         }

         if (candidate == '-') {
            if (signSymbols.length() == 1) {
               candidate = signSymbols.charAt(0);
            } else {
               output.append(signSymbols.charAt(0));
               candidate = signSymbols.charAt(1);
            }
         }

         if (perMilleReplacesPercent && candidate == '%') {
            candidate = 8240;
         }

         output.append(candidate);
      }
   }

   public static PatternStringUtils.PatternSignType resolveSignDisplay(NumberFormatter.SignDisplay signDisplay, Modifier.Signum signum) {
      switch (signDisplay) {
         case AUTO:
         case ACCOUNTING:
            switch (signum) {
               case NEG:
               case NEG_ZERO:
                  return PatternStringUtils.PatternSignType.NEG;
               case POS_ZERO:
               case POS:
                  return PatternStringUtils.PatternSignType.POS;
               default:
                  throw new AssertionError("Unreachable");
            }
         case ALWAYS:
         case ACCOUNTING_ALWAYS:
            switch (signum) {
               case NEG:
               case NEG_ZERO:
                  return PatternStringUtils.PatternSignType.NEG;
               case POS_ZERO:
               case POS:
                  return PatternStringUtils.PatternSignType.POS_SIGN;
               default:
                  throw new AssertionError("Unreachable");
            }
         case EXCEPT_ZERO:
         case ACCOUNTING_EXCEPT_ZERO:
            switch (signum) {
               case NEG:
                  return PatternStringUtils.PatternSignType.NEG;
               case NEG_ZERO:
               case POS_ZERO:
                  return PatternStringUtils.PatternSignType.POS;
               case POS:
                  return PatternStringUtils.PatternSignType.POS_SIGN;
               default:
                  throw new AssertionError("Unreachable");
            }
         case NEGATIVE:
         case ACCOUNTING_NEGATIVE:
            switch (signum) {
               case NEG:
                  return PatternStringUtils.PatternSignType.NEG;
               case NEG_ZERO:
               case POS_ZERO:
               case POS:
                  return PatternStringUtils.PatternSignType.POS;
               default:
                  throw new AssertionError("Unreachable");
            }
         case NEVER:
            return PatternStringUtils.PatternSignType.POS;
         default:
            throw new AssertionError("Unreachable");
      }
   }

   public static enum PatternSignType {
      POS,
      POS_SIGN,
      NEG;

      public static final PatternStringUtils.PatternSignType[] VALUES = values();
   }
}
