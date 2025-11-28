package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.number.FormattedNumber;
import com.cobblemon.mod.relocations.ibm.icu.number.LocalizedNumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class PluralFormat extends UFormat {
   private static final long serialVersionUID = 1L;
   private ULocale ulocale = null;
   private PluralRules pluralRules = null;
   private String pattern = null;
   private transient MessagePattern msgPattern;
   private Map<String, String> parsedValues = null;
   private NumberFormat numberFormat = null;
   private transient double offset = 0.0;
   private transient PluralFormat.PluralSelectorAdapter pluralRulesWrapper = new PluralFormat.PluralSelectorAdapter();

   public PluralFormat() {
      this.init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
   }

   public PluralFormat(ULocale ulocale) {
      this.init(null, PluralRules.PluralType.CARDINAL, ulocale, null);
   }

   public PluralFormat(Locale locale) {
      this(ULocale.forLocale(locale));
   }

   public PluralFormat(PluralRules rules) {
      this.init(rules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
   }

   public PluralFormat(ULocale ulocale, PluralRules rules) {
      this.init(rules, PluralRules.PluralType.CARDINAL, ulocale, null);
   }

   public PluralFormat(Locale locale, PluralRules rules) {
      this(ULocale.forLocale(locale), rules);
   }

   public PluralFormat(ULocale ulocale, PluralRules.PluralType type) {
      this.init(null, type, ulocale, null);
   }

   public PluralFormat(Locale locale, PluralRules.PluralType type) {
      this(ULocale.forLocale(locale), type);
   }

   public PluralFormat(String pattern) {
      this.init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
      this.applyPattern(pattern);
   }

   public PluralFormat(ULocale ulocale, String pattern) {
      this.init(null, PluralRules.PluralType.CARDINAL, ulocale, null);
      this.applyPattern(pattern);
   }

   public PluralFormat(PluralRules rules, String pattern) {
      this.init(rules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
      this.applyPattern(pattern);
   }

   public PluralFormat(ULocale ulocale, PluralRules rules, String pattern) {
      this.init(rules, PluralRules.PluralType.CARDINAL, ulocale, null);
      this.applyPattern(pattern);
   }

   public PluralFormat(ULocale ulocale, PluralRules.PluralType type, String pattern) {
      this.init(null, type, ulocale, null);
      this.applyPattern(pattern);
   }

   PluralFormat(ULocale ulocale, PluralRules.PluralType type, String pattern, NumberFormat numberFormat) {
      this.init(null, type, ulocale, numberFormat);
      this.applyPattern(pattern);
   }

   private void init(PluralRules rules, PluralRules.PluralType type, ULocale locale, NumberFormat numberFormat) {
      this.ulocale = locale;
      this.pluralRules = rules == null ? PluralRules.forLocale(this.ulocale, type) : rules;
      this.resetPattern();
      this.numberFormat = numberFormat == null ? NumberFormat.getInstance(this.ulocale) : numberFormat;
   }

   private void resetPattern() {
      this.pattern = null;
      if (this.msgPattern != null) {
         this.msgPattern.clear();
      }

      this.offset = 0.0;
   }

   public void applyPattern(String pattern) {
      this.pattern = pattern;
      if (this.msgPattern == null) {
         this.msgPattern = new MessagePattern();
      }

      try {
         this.msgPattern.parsePluralStyle(pattern);
         this.offset = this.msgPattern.getPluralOffset(0);
      } catch (RuntimeException var3) {
         this.resetPattern();
         throw var3;
      }
   }

   public String toPattern() {
      return this.pattern;
   }

   static int findSubMessage(MessagePattern pattern, int partIndex, PluralFormat.PluralSelector selector, Object context, double number) {
      int count = pattern.countParts();
      MessagePattern.Part part = pattern.getPart(partIndex);
      double offset;
      if (part.getType().hasNumericValue()) {
         offset = pattern.getNumericValue(part);
         partIndex++;
      } else {
         offset = 0.0;
      }

      String keyword = null;
      boolean haveKeywordMatch = false;
      int msgStart = 0;

      do {
         part = pattern.getPart(partIndex++);
         MessagePattern.Part.Type type = part.getType();
         if (type == MessagePattern.Part.Type.ARG_LIMIT) {
            break;
         }

         assert type == MessagePattern.Part.Type.ARG_SELECTOR;

         if (pattern.getPartType(partIndex).hasNumericValue()) {
            part = pattern.getPart(partIndex++);
            if (number == pattern.getNumericValue(part)) {
               return partIndex;
            }
         } else if (!haveKeywordMatch) {
            if (pattern.partSubstringMatches(part, "other")) {
               if (msgStart == 0) {
                  msgStart = partIndex;
                  if (keyword != null && keyword.equals("other")) {
                     haveKeywordMatch = true;
                  }
               }
            } else {
               if (keyword == null) {
                  keyword = selector.select(context, number - offset);
                  if (msgStart != 0 && keyword.equals("other")) {
                     haveKeywordMatch = true;
                  }
               }

               if (!haveKeywordMatch && pattern.partSubstringMatches(part, keyword)) {
                  msgStart = partIndex;
                  haveKeywordMatch = true;
               }
            }
         }

         partIndex = pattern.getLimitPartIndex(partIndex);
      } while (++partIndex < count);

      return msgStart;
   }

   public final String format(double number) {
      return this.format(number, number);
   }

   @Override
   public StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos) {
      if (!(number instanceof Number)) {
         throw new IllegalArgumentException("'" + number + "' is not a Number");
      } else {
         Number numberObject = (Number)number;
         toAppendTo.append(this.format(numberObject, numberObject.doubleValue()));
         return toAppendTo;
      }
   }

   private String format(Number numberObject, double number) {
      if (this.msgPattern != null && this.msgPattern.countParts() != 0) {
         double numberMinusOffset = number - this.offset;
         String numberString;
         PluralRules.IFixedDecimal dec;
         if (this.numberFormat instanceof DecimalFormat) {
            LocalizedNumberFormatter f = ((DecimalFormat)this.numberFormat).toNumberFormatter();
            FormattedNumber result;
            if (this.offset == 0.0) {
               result = f.format(numberObject);
            } else {
               result = f.format(numberMinusOffset);
            }

            numberString = result.toString();
            dec = result.getFixedDecimal();
         } else {
            if (this.offset == 0.0) {
               numberString = this.numberFormat.format(numberObject);
            } else {
               numberString = this.numberFormat.format(numberMinusOffset);
            }

            dec = new PluralRules.FixedDecimal(numberMinusOffset);
         }

         int partIndex = findSubMessage(this.msgPattern, 0, this.pluralRulesWrapper, dec, number);
         StringBuilder result = null;
         int prevIndex = this.msgPattern.getPart(partIndex).getLimit();

         while (true) {
            MessagePattern.Part part = this.msgPattern.getPart(++partIndex);
            MessagePattern.Part.Type type = part.getType();
            int index = part.getIndex();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
               if (result == null) {
                  return this.pattern.substring(prevIndex, index);
               }

               return result.append(this.pattern, prevIndex, index).toString();
            }

            if (type == MessagePattern.Part.Type.REPLACE_NUMBER || type == MessagePattern.Part.Type.SKIP_SYNTAX && this.msgPattern.jdkAposMode()) {
               if (result == null) {
                  result = new StringBuilder();
               }

               result.append(this.pattern, prevIndex, index);
               if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                  result.append(numberString);
               }

               prevIndex = part.getLimit();
            } else if (type == MessagePattern.Part.Type.ARG_START) {
               if (result == null) {
                  result = new StringBuilder();
               }

               result.append(this.pattern, prevIndex, index);
               partIndex = this.msgPattern.getLimitPartIndex(partIndex);
               int var17 = this.msgPattern.getPart(partIndex).getLimit();
               MessagePattern.appendReducedApostrophes(this.pattern, index, var17, result);
               prevIndex = var17;
            }
         }
      } else {
         return this.numberFormat.format(numberObject);
      }
   }

   public Number parse(String text, ParsePosition parsePosition) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object parseObject(String source, ParsePosition pos) {
      throw new UnsupportedOperationException();
   }

   String parseType(String source, RbnfLenientScanner scanner, FieldPosition pos) {
      if (this.msgPattern != null && this.msgPattern.countParts() != 0) {
         int partIndex = 0;
         int count = this.msgPattern.countParts();
         int startingAt = pos.getBeginIndex();
         if (startingAt < 0) {
            startingAt = 0;
         }

         String keyword = null;
         String matchedWord = null;
         int matchedIndex = -1;

         while (partIndex < count) {
            MessagePattern.Part partSelector = this.msgPattern.getPart(partIndex++);
            if (partSelector.getType() == MessagePattern.Part.Type.ARG_SELECTOR) {
               MessagePattern.Part partStart = this.msgPattern.getPart(partIndex++);
               if (partStart.getType() == MessagePattern.Part.Type.MSG_START) {
                  MessagePattern.Part partLimit = this.msgPattern.getPart(partIndex++);
                  if (partLimit.getType() == MessagePattern.Part.Type.MSG_LIMIT) {
                     String currArg = this.pattern.substring(partStart.getLimit(), partLimit.getIndex());
                     int currMatchIndex;
                     if (scanner != null) {
                        int tempPos = source.indexOf(currArg, startingAt);
                        if (tempPos >= 0) {
                           currMatchIndex = tempPos;
                        } else {
                           int[] scannerMatchResult = scanner.findText(source, currArg, startingAt);
                           currMatchIndex = scannerMatchResult[0];
                        }
                     } else {
                        currMatchIndex = source.indexOf(currArg, startingAt);
                     }

                     if (currMatchIndex >= 0 && currMatchIndex >= matchedIndex && (matchedWord == null || currArg.length() > matchedWord.length())) {
                        matchedIndex = currMatchIndex;
                        matchedWord = currArg;
                        keyword = this.pattern.substring(partStart.getLimit(), partLimit.getIndex());
                     }
                  }
               }
            }
         }

         if (keyword != null) {
            pos.setBeginIndex(matchedIndex);
            pos.setEndIndex(matchedIndex + matchedWord.length());
            return keyword;
         } else {
            pos.setBeginIndex(-1);
            pos.setEndIndex(-1);
            return null;
         }
      } else {
         pos.setBeginIndex(-1);
         pos.setEndIndex(-1);
         return null;
      }
   }

   @Deprecated
   public void setLocale(ULocale ulocale) {
      if (ulocale == null) {
         ulocale = ULocale.getDefault(ULocale.Category.FORMAT);
      }

      this.init(null, PluralRules.PluralType.CARDINAL, ulocale, null);
   }

   public void setNumberFormat(NumberFormat format) {
      this.numberFormat = format;
   }

   @Override
   public boolean equals(Object rhs) {
      if (this == rhs) {
         return true;
      } else if (rhs != null && this.getClass() == rhs.getClass()) {
         PluralFormat pf = (PluralFormat)rhs;
         return Objects.equals(this.ulocale, pf.ulocale)
            && Objects.equals(this.pluralRules, pf.pluralRules)
            && Objects.equals(this.msgPattern, pf.msgPattern)
            && Objects.equals(this.numberFormat, pf.numberFormat);
      } else {
         return false;
      }
   }

   public boolean equals(PluralFormat rhs) {
      return this.equals((Object)rhs);
   }

   @Override
   public int hashCode() {
      return this.pluralRules.hashCode() ^ this.parsedValues.hashCode();
   }

   @Override
   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("locale=" + this.ulocale);
      buf.append(", rules='" + this.pluralRules + "'");
      buf.append(", pattern='" + this.pattern + "'");
      buf.append(", format='" + this.numberFormat + "'");
      return buf.toString();
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      this.pluralRulesWrapper = new PluralFormat.PluralSelectorAdapter();
      this.parsedValues = null;
      if (this.pattern != null) {
         this.applyPattern(this.pattern);
      }
   }

   interface PluralSelector {
      String select(Object var1, double var2);
   }

   private final class PluralSelectorAdapter implements PluralFormat.PluralSelector {
      private PluralSelectorAdapter() {
      }

      @Override
      public String select(Object context, double number) {
         PluralRules.IFixedDecimal dec = (PluralRules.IFixedDecimal)context;
         return PluralFormat.this.pluralRules.select(dec);
      }
   }
}
