package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.PluralRulesLoader;
import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.range.StandardPluralRanges;
import com.cobblemon.mod.relocations.ibm.icu.number.FormattedNumber;
import com.cobblemon.mod.relocations.ibm.icu.number.FormattedNumberRange;
import com.cobblemon.mod.relocations.ibm.icu.util.Output;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class PluralRules implements Serializable {
   static final UnicodeSet ALLOWED_ID = new UnicodeSet("[a-z]").freeze();
   private static final String CATEGORY_SEPARATOR = ";  ";
   private static final long serialVersionUID = 1L;
   private final PluralRules.RuleList rules;
   private final transient Set<String> keywords;
   private final transient StandardPluralRanges standardPluralRanges;
   public static final String KEYWORD_ZERO = "zero";
   public static final String KEYWORD_ONE = "one";
   public static final String KEYWORD_TWO = "two";
   public static final String KEYWORD_FEW = "few";
   public static final String KEYWORD_MANY = "many";
   public static final String KEYWORD_OTHER = "other";
   public static final double NO_UNIQUE_VALUE = -0.00123456777;
   private static final PluralRules.Constraint NO_CONSTRAINT = new PluralRules.Constraint() {
      private static final long serialVersionUID = 9163464945387899416L;

      @Override
      public boolean isFulfilled(PluralRules.IFixedDecimal n) {
         return true;
      }

      @Override
      public boolean isLimited(PluralRules.SampleType sampleType) {
         return false;
      }

      @Override
      public String toString() {
         return "";
      }
   };
   private static final PluralRules.Rule DEFAULT_RULE = new PluralRules.Rule("other", NO_CONSTRAINT, null, null);
   public static final PluralRules DEFAULT = new PluralRules(new PluralRules.RuleList().addRule(DEFAULT_RULE), StandardPluralRanges.DEFAULT);
   static final Pattern AT_SEPARATED = Pattern.compile("\\s*\\Q\\E@\\s*");
   static final Pattern OR_SEPARATED = Pattern.compile("\\s*or\\s*");
   static final Pattern AND_SEPARATED = Pattern.compile("\\s*and\\s*");
   static final Pattern COMMA_SEPARATED = Pattern.compile("\\s*,\\s*");
   static final Pattern DOTDOT_SEPARATED = Pattern.compile("\\s*\\Q..\\E\\s*");
   static final Pattern TILDE_SEPARATED = Pattern.compile("\\s*~\\s*");
   static final Pattern SEMI_SEPARATED = Pattern.compile("\\s*;\\s*");

   public static PluralRules parseDescription(String description) throws ParseException {
      return newInternal(description, null);
   }

   public static PluralRules createRules(String description) {
      try {
         return parseDescription(description);
      } catch (Exception var2) {
         return null;
      }
   }

   @Deprecated
   public static PluralRules newInternal(String description, StandardPluralRanges ranges) throws ParseException {
      description = description.trim();
      return description.length() == 0 ? DEFAULT : new PluralRules(parseRuleChain(description), ranges);
   }

   private static PluralRules.Constraint parseConstraint(String description) throws ParseException {
      PluralRules.Constraint result = null;
      String[] or_together = OR_SEPARATED.split(description);

      for (int i = 0; i < or_together.length; i++) {
         PluralRules.Constraint andConstraint = null;
         String[] and_together = AND_SEPARATED.split(or_together[i]);

         for (int j = 0; j < and_together.length; j++) {
            PluralRules.Constraint newConstraint = NO_CONSTRAINT;
            String condition = and_together[j].trim();
            String[] tokens = PluralRules.SimpleTokenizer.split(condition);
            int mod = 0;
            boolean inRange = true;
            boolean integersOnly = true;
            double lowBound = 9.223372E18F;
            double highBound = -9.223372E18F;
            long[] vals = null;
            int x = 0;
            String t = tokens[x++];
            boolean hackForCompatibility = false;

            PluralRules.Operand operand;
            try {
               operand = PluralRules.FixedDecimal.getOperand(t);
            } catch (Exception var27) {
               throw unexpected(t, condition);
            }

            if (x < tokens.length) {
               t = tokens[x++];
               if ("mod".equals(t) || "%".equals(t)) {
                  mod = Integer.parseInt(tokens[x++]);
                  t = nextToken(tokens, x++, condition);
               }

               if ("not".equals(t)) {
                  inRange = !inRange;
                  t = nextToken(tokens, x++, condition);
                  if ("=".equals(t)) {
                     throw unexpected(t, condition);
                  }
               } else if ("!".equals(t)) {
                  inRange = !inRange;
                  t = nextToken(tokens, x++, condition);
                  if (!"=".equals(t)) {
                     throw unexpected(t, condition);
                  }
               }

               if (!"is".equals(t) && !"in".equals(t) && !"=".equals(t)) {
                  if (!"within".equals(t)) {
                     throw unexpected(t, condition);
                  }

                  integersOnly = false;
                  t = nextToken(tokens, x++, condition);
               } else {
                  hackForCompatibility = "is".equals(t);
                  if (hackForCompatibility && !inRange) {
                     throw unexpected(t, condition);
                  }

                  t = nextToken(tokens, x++, condition);
               }

               if ("not".equals(t)) {
                  if (!hackForCompatibility && !inRange) {
                     throw unexpected(t, condition);
                  }

                  inRange = !inRange;
                  t = nextToken(tokens, x++, condition);
               }

               List<Long> valueList = new ArrayList<>();

               while (true) {
                  long low = Long.parseLong(t);
                  long high = low;
                  if (x < tokens.length) {
                     t = nextToken(tokens, x++, condition);
                     if (t.equals(".")) {
                        t = nextToken(tokens, x++, condition);
                        if (!t.equals(".")) {
                           throw unexpected(t, condition);
                        }

                        t = nextToken(tokens, x++, condition);
                        high = Long.parseLong(t);
                        if (x < tokens.length) {
                           t = nextToken(tokens, x++, condition);
                           if (!t.equals(",")) {
                              throw unexpected(t, condition);
                           }
                        }
                     } else if (!t.equals(",")) {
                        throw unexpected(t, condition);
                     }
                  }

                  if (low > high) {
                     throw unexpected(low + "~" + high, condition);
                  }

                  if (mod != 0 && high >= mod) {
                     throw unexpected(high + ">mod=" + mod, condition);
                  }

                  valueList.add(low);
                  valueList.add(high);
                  lowBound = Math.min(lowBound, (double)low);
                  highBound = Math.max(highBound, (double)high);
                  if (x >= tokens.length) {
                     if (t.equals(",")) {
                        throw unexpected(t, condition);
                     }

                     if (valueList.size() == 2) {
                        vals = null;
                     } else {
                        vals = new long[valueList.size()];

                        for (int k = 0; k < vals.length; k++) {
                           vals[k] = valueList.get(k);
                        }
                     }

                     if (lowBound != highBound && hackForCompatibility && !inRange) {
                        throw unexpected("is not <range>", condition);
                     }

                     newConstraint = new PluralRules.RangeConstraint(mod, inRange, operand, integersOnly, lowBound, highBound, vals);
                     break;
                  }

                  t = nextToken(tokens, x++, condition);
               }
            }

            if (andConstraint == null) {
               andConstraint = newConstraint;
            } else {
               andConstraint = new PluralRules.AndConstraint(andConstraint, newConstraint);
            }
         }

         if (result == null) {
            result = andConstraint;
         } else {
            result = new PluralRules.OrConstraint(result, andConstraint);
         }
      }

      return result;
   }

   private static ParseException unexpected(String token, String context) {
      return new ParseException("unexpected token '" + token + "' in '" + context + "'", -1);
   }

   private static String nextToken(String[] tokens, int x, String context) throws ParseException {
      if (x < tokens.length) {
         return tokens[x];
      } else {
         throw new ParseException("missing token at end of '" + context + "'", -1);
      }
   }

   private static PluralRules.Rule parseRule(String description) throws ParseException {
      if (description.length() == 0) {
         return DEFAULT_RULE;
      } else {
         description = description.toLowerCase(Locale.ENGLISH);
         int x = description.indexOf(58);
         if (x == -1) {
            throw new ParseException("missing ':' in rule description '" + description + "'", 0);
         } else {
            String keyword = description.substring(0, x).trim();
            if (!isValidKeyword(keyword)) {
               throw new ParseException("keyword '" + keyword + " is not valid", 0);
            } else {
               description = description.substring(x + 1).trim();
               String[] constraintOrSamples = AT_SEPARATED.split(description);
               boolean sampleFailure = false;
               PluralRules.FixedDecimalSamples integerSamples = null;
               PluralRules.FixedDecimalSamples decimalSamples = null;
               switch (constraintOrSamples.length) {
                  case 1:
                     break;
                  case 2:
                     integerSamples = PluralRules.FixedDecimalSamples.parse(constraintOrSamples[1]);
                     if (integerSamples.sampleType == PluralRules.SampleType.DECIMAL) {
                        decimalSamples = integerSamples;
                        integerSamples = null;
                     }
                     break;
                  case 3:
                     integerSamples = PluralRules.FixedDecimalSamples.parse(constraintOrSamples[1]);
                     decimalSamples = PluralRules.FixedDecimalSamples.parse(constraintOrSamples[2]);
                     if (integerSamples.sampleType != PluralRules.SampleType.INTEGER || decimalSamples.sampleType != PluralRules.SampleType.DECIMAL) {
                        throw new IllegalArgumentException("Must have @integer then @decimal in " + description);
                     }
                     break;
                  default:
                     throw new IllegalArgumentException("Too many samples in " + description);
               }

               if (sampleFailure) {
                  throw new IllegalArgumentException("Ill-formed samples—'@' characters.");
               } else {
                  boolean isOther = keyword.equals("other");
                  if (isOther != (constraintOrSamples[0].length() == 0)) {
                     throw new IllegalArgumentException("The keyword 'other' must have no constraints, just samples.");
                  } else {
                     PluralRules.Constraint constraint;
                     if (isOther) {
                        constraint = NO_CONSTRAINT;
                     } else {
                        constraint = parseConstraint(constraintOrSamples[0]);
                     }

                     return new PluralRules.Rule(keyword, constraint, integerSamples, decimalSamples);
                  }
               }
            }
         }
      }
   }

   private static PluralRules.RuleList parseRuleChain(String description) throws ParseException {
      PluralRules.RuleList result = new PluralRules.RuleList();
      if (description.endsWith(";")) {
         description = description.substring(0, description.length() - 1);
      }

      String[] rules = SEMI_SEPARATED.split(description);

      for (int i = 0; i < rules.length; i++) {
         PluralRules.Rule rule = parseRule(rules[i].trim());
         result.hasExplicitBoundingInfo = result.hasExplicitBoundingInfo | (rule.integerSamples != null || rule.decimalSamples != null);
         result.addRule(rule);
      }

      return result.finish();
   }

   private static void addRange(StringBuilder result, double lb, double ub, boolean addSeparator) {
      if (addSeparator) {
         result.append(",");
      }

      if (lb == ub) {
         result.append(format(lb));
      } else {
         result.append(format(lb) + ".." + format(ub));
      }
   }

   private static String format(double lb) {
      long lbi = (long)lb;
      return lb == lbi ? String.valueOf(lbi) : String.valueOf(lb);
   }

   private boolean addConditional(Set<PluralRules.IFixedDecimal> toAddTo, Set<PluralRules.IFixedDecimal> others, double trial) {
      PluralRules.IFixedDecimal toAdd = new PluralRules.FixedDecimal(trial);
      boolean added;
      if (!toAddTo.contains(toAdd) && !others.contains(toAdd)) {
         others.add(toAdd);
         added = true;
      } else {
         added = false;
      }

      return added;
   }

   public static PluralRules forLocale(ULocale locale) {
      return PluralRules.Factory.getDefaultFactory().forLocale(locale, PluralRules.PluralType.CARDINAL);
   }

   public static PluralRules forLocale(Locale locale) {
      return forLocale(ULocale.forLocale(locale));
   }

   public static PluralRules forLocale(ULocale locale, PluralRules.PluralType type) {
      return PluralRules.Factory.getDefaultFactory().forLocale(locale, type);
   }

   public static PluralRules forLocale(Locale locale, PluralRules.PluralType type) {
      return forLocale(ULocale.forLocale(locale), type);
   }

   private static boolean isValidKeyword(String token) {
      return ALLOWED_ID.containsAll(token);
   }

   private PluralRules(PluralRules.RuleList rules, StandardPluralRanges standardPluralRanges) {
      this.rules = rules;
      this.keywords = Collections.unmodifiableSet(rules.getKeywords());
      this.standardPluralRanges = standardPluralRanges;
   }

   @Override
   public int hashCode() {
      return this.rules.hashCode();
   }

   public String select(double number) {
      return this.rules.select(new PluralRules.FixedDecimal(number));
   }

   public String select(FormattedNumber number) {
      return this.rules.select(number.getFixedDecimal());
   }

   public String select(FormattedNumberRange range) {
      if (this.standardPluralRanges == null) {
         throw new UnsupportedOperationException("Plural ranges are unavailable on this instance");
      } else {
         StandardPlural form1 = StandardPlural.fromString(this.select(range.getFirstFixedDecimal()));
         StandardPlural form2 = StandardPlural.fromString(this.select(range.getSecondFixedDecimal()));
         StandardPlural result = this.standardPluralRanges.resolve(form1, form2);
         return result.getKeyword();
      }
   }

   @Deprecated
   public String select(double number, int countVisibleFractionDigits, long fractionaldigits) {
      return this.rules.select(new PluralRules.FixedDecimal(number, countVisibleFractionDigits, fractionaldigits));
   }

   @Deprecated
   public String select(PluralRules.IFixedDecimal number) {
      return this.rules.select(number);
   }

   @Deprecated
   public boolean matches(PluralRules.FixedDecimal sample, String keyword) {
      return this.rules.select(sample, keyword);
   }

   public Set<String> getKeywords() {
      return this.keywords;
   }

   public double getUniqueKeywordValue(String keyword) {
      Collection<Double> values = this.getAllKeywordValues(keyword);
      return values != null && values.size() == 1 ? values.iterator().next() : -0.00123456777;
   }

   public Collection<Double> getAllKeywordValues(String keyword) {
      return this.getAllKeywordValues(keyword, PluralRules.SampleType.INTEGER);
   }

   @Deprecated
   public Collection<Double> getAllKeywordValues(String keyword, PluralRules.SampleType type) {
      if (!this.isLimited(keyword, type)) {
         return null;
      } else {
         Collection<Double> samples = this.getSamples(keyword, type);
         return samples == null ? null : Collections.unmodifiableCollection(samples);
      }
   }

   public Collection<Double> getSamples(String keyword) {
      return this.getSamples(keyword, PluralRules.SampleType.INTEGER);
   }

   @Deprecated
   public Collection<Double> getSamples(String keyword, PluralRules.SampleType sampleType) {
      if (!this.keywords.contains(keyword)) {
         return null;
      } else {
         Set<Double> result = new TreeSet<>();
         if (this.rules.hasExplicitBoundingInfo) {
            PluralRules.FixedDecimalSamples samples = this.rules.getDecimalSamples(keyword, sampleType);
            return samples == null ? Collections.unmodifiableSet(result) : Collections.unmodifiableSet(samples.addSamples(result));
         } else {
            int maxCount = this.isLimited(keyword, sampleType) ? Integer.MAX_VALUE : 20;
            switch (sampleType) {
               case INTEGER:
                  int i = 0;

                  while (i < 200 && this.addSample(keyword, i, maxCount, result)) {
                     i++;
                  }

                  this.addSample(keyword, 1000000, maxCount, result);
                  break;
               case DECIMAL:
                  int i = 0;

                  while (i < 2000 && this.addSample(keyword, new PluralRules.FixedDecimal(i / 10.0, 1), maxCount, result)) {
                     i++;
                  }

                  this.addSample(keyword, new PluralRules.FixedDecimal(1000000.0, 1), maxCount, result);
            }

            return result.size() == 0 ? null : Collections.unmodifiableSet(result);
         }
      }
   }

   private boolean addSample(String keyword, Number sample, int maxCount, Set<Double> result) {
      String selectedKeyword = sample instanceof PluralRules.FixedDecimal ? this.select((PluralRules.FixedDecimal)sample) : this.select(sample.doubleValue());
      if (selectedKeyword.equals(keyword)) {
         result.add(sample.doubleValue());
         if (--maxCount < 0) {
            return false;
         }
      }

      return true;
   }

   @Deprecated
   public PluralRules.FixedDecimalSamples getDecimalSamples(String keyword, PluralRules.SampleType sampleType) {
      return this.rules.getDecimalSamples(keyword, sampleType);
   }

   public static ULocale[] getAvailableULocales() {
      return PluralRules.Factory.getDefaultFactory().getAvailableULocales();
   }

   public static ULocale getFunctionalEquivalent(ULocale locale, boolean[] isAvailable) {
      return PluralRules.Factory.getDefaultFactory().getFunctionalEquivalent(locale, isAvailable);
   }

   @Override
   public String toString() {
      return this.rules.toString();
   }

   @Override
   public boolean equals(Object rhs) {
      return rhs instanceof PluralRules && this.equals((PluralRules)rhs);
   }

   public boolean equals(PluralRules rhs) {
      return rhs != null && this.toString().equals(rhs.toString());
   }

   public PluralRules.KeywordStatus getKeywordStatus(String keyword, int offset, Set<Double> explicits, Output<Double> uniqueValue) {
      return this.getKeywordStatus(keyword, offset, explicits, uniqueValue, PluralRules.SampleType.INTEGER);
   }

   @Deprecated
   public PluralRules.KeywordStatus getKeywordStatus(
      String keyword, int offset, Set<Double> explicits, Output<Double> uniqueValue, PluralRules.SampleType sampleType
   ) {
      if (uniqueValue != null) {
         uniqueValue.value = null;
      }

      if (!this.keywords.contains(keyword)) {
         return PluralRules.KeywordStatus.INVALID;
      } else if (!this.isLimited(keyword, sampleType)) {
         return PluralRules.KeywordStatus.UNBOUNDED;
      } else {
         Collection<Double> values = this.getSamples(keyword, sampleType);
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

   @Deprecated
   public String getRules(String keyword) {
      return this.rules.getRules(keyword);
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      throw new NotSerializableException();
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      throw new NotSerializableException();
   }

   private Object writeReplace() throws ObjectStreamException {
      return new PluralRulesSerialProxy(this.toString());
   }

   @Deprecated
   public int compareTo(PluralRules other) {
      return this.toString().compareTo(other.toString());
   }

   Boolean isLimited(String keyword) {
      return this.rules.isLimited(keyword, PluralRules.SampleType.INTEGER);
   }

   @Deprecated
   public boolean isLimited(String keyword, PluralRules.SampleType sampleType) {
      return this.rules.isLimited(keyword, sampleType);
   }

   @Deprecated
   public boolean computeLimited(String keyword, PluralRules.SampleType sampleType) {
      return this.rules.computeLimited(keyword, sampleType);
   }

   private static class AndConstraint extends PluralRules.BinaryConstraint {
      private static final long serialVersionUID = 7766999779862263523L;

      AndConstraint(PluralRules.Constraint a, PluralRules.Constraint b) {
         super(a, b);
      }

      @Override
      public boolean isFulfilled(PluralRules.IFixedDecimal n) {
         return this.a.isFulfilled(n) && this.b.isFulfilled(n);
      }

      @Override
      public boolean isLimited(PluralRules.SampleType sampleType) {
         return this.a.isLimited(sampleType) || this.b.isLimited(sampleType);
      }

      @Override
      public String toString() {
         return this.a.toString() + " and " + this.b.toString();
      }
   }

   private abstract static class BinaryConstraint implements PluralRules.Constraint, Serializable {
      private static final long serialVersionUID = 1L;
      protected final PluralRules.Constraint a;
      protected final PluralRules.Constraint b;

      protected BinaryConstraint(PluralRules.Constraint a, PluralRules.Constraint b) {
         this.a = a;
         this.b = b;
      }
   }

   private interface Constraint extends Serializable {
      boolean isFulfilled(PluralRules.IFixedDecimal var1);

      boolean isLimited(PluralRules.SampleType var1);
   }

   @Deprecated
   public abstract static class Factory {
      @Deprecated
      protected Factory() {
      }

      @Deprecated
      public abstract PluralRules forLocale(ULocale var1, PluralRules.PluralType var2);

      @Deprecated
      public final PluralRules forLocale(ULocale locale) {
         return this.forLocale(locale, PluralRules.PluralType.CARDINAL);
      }

      @Deprecated
      public abstract ULocale[] getAvailableULocales();

      @Deprecated
      public abstract ULocale getFunctionalEquivalent(ULocale var1, boolean[] var2);

      @Deprecated
      public static PluralRulesLoader getDefaultFactory() {
         return PluralRulesLoader.loader;
      }

      @Deprecated
      public abstract boolean hasOverride(ULocale var1);
   }

   @Deprecated
   public static class FixedDecimal extends Number implements Comparable<PluralRules.FixedDecimal>, PluralRules.IFixedDecimal {
      private static final long serialVersionUID = -4756200506571685661L;
      final double source;
      final int visibleDecimalDigitCount;
      final int visibleDecimalDigitCountWithoutTrailingZeros;
      final long decimalDigits;
      final long decimalDigitsWithoutTrailingZeros;
      final long integerValue;
      final boolean hasIntegerValue;
      final boolean isNegative;
      final int exponent;
      private final int baseFactor;
      static final long MAX = 1000000000000000000L;
      private static final long MAX_INTEGER_PART = 1000000000L;

      @Deprecated
      public double getSource() {
         return this.source;
      }

      @Deprecated
      public int getVisibleDecimalDigitCount() {
         return this.visibleDecimalDigitCount;
      }

      @Deprecated
      public int getVisibleDecimalDigitCountWithoutTrailingZeros() {
         return this.visibleDecimalDigitCountWithoutTrailingZeros;
      }

      @Deprecated
      public long getDecimalDigits() {
         return this.decimalDigits;
      }

      @Deprecated
      public long getDecimalDigitsWithoutTrailingZeros() {
         return this.decimalDigitsWithoutTrailingZeros;
      }

      @Deprecated
      public long getIntegerValue() {
         return this.integerValue;
      }

      @Deprecated
      @Override
      public boolean isHasIntegerValue() {
         return this.hasIntegerValue;
      }

      @Deprecated
      public boolean isNegative() {
         return this.isNegative;
      }

      @Deprecated
      public int getBaseFactor() {
         return this.baseFactor;
      }

      @Deprecated
      public FixedDecimal(double n, int v, long f, int e, int c) {
         this.isNegative = n < 0.0;
         this.source = this.isNegative ? -n : n;
         this.visibleDecimalDigitCount = v;
         this.decimalDigits = f;
         this.integerValue = n > 1.0E18 ? 1000000000000000000L : (long)this.source;
         int initExpVal = e;
         if (e == 0) {
            initExpVal = c;
         }

         this.exponent = initExpVal;
         this.hasIntegerValue = this.source == this.integerValue;
         if (f == 0L) {
            this.decimalDigitsWithoutTrailingZeros = 0L;
            this.visibleDecimalDigitCountWithoutTrailingZeros = 0;
         } else {
            long fdwtz = f;

            int trimmedCount;
            for (trimmedCount = v; fdwtz % 10L == 0L; trimmedCount--) {
               fdwtz /= 10L;
            }

            this.decimalDigitsWithoutTrailingZeros = fdwtz;
            this.visibleDecimalDigitCountWithoutTrailingZeros = trimmedCount;
         }

         this.baseFactor = (int)Math.pow(10.0, v);
      }

      @Deprecated
      public FixedDecimal(double n, int v, long f, int e) {
         this(n, v, f, e, e);
      }

      @Deprecated
      public FixedDecimal(double n, int v, long f) {
         this(n, v, f, 0);
      }

      @Deprecated
      public static PluralRules.FixedDecimal createWithExponent(double n, int v, int e) {
         return new PluralRules.FixedDecimal(n, v, getFractionalDigits(n, v), e);
      }

      @Deprecated
      public FixedDecimal(double n, int v) {
         this(n, v, getFractionalDigits(n, v));
      }

      private static int getFractionalDigits(double n, int v) {
         if (v == 0) {
            return 0;
         } else {
            if (n < 0.0) {
               n = -n;
            }

            int baseFactor = (int)Math.pow(10.0, v);
            long scaled = Math.round(n * baseFactor);
            return (int)(scaled % baseFactor);
         }
      }

      @Deprecated
      public FixedDecimal(double n) {
         this(n, decimals(n));
      }

      @Deprecated
      public FixedDecimal(long n) {
         this(n, 0);
      }

      @Deprecated
      public static int decimals(double n) {
         if (!Double.isInfinite(n) && !Double.isNaN(n)) {
            if (n < 0.0) {
               n = -n;
            }

            if (n == Math.floor(n)) {
               return 0;
            } else if (n < 1.0E9) {
               long temp = (long)(n * 1000000.0) % 1000000L;
               int mask = 10;

               for (int digits = 6; digits > 0; digits--) {
                  if (temp % mask != 0L) {
                     return digits;
                  }

                  mask *= 10;
               }

               return 0;
            } else {
               String buf = String.format(Locale.ENGLISH, "%1.15e", n);
               int ePos = buf.lastIndexOf(101);
               int expNumPos = ePos + 1;
               if (buf.charAt(expNumPos) == '+') {
                  expNumPos++;
               }

               String exponentStr = buf.substring(expNumPos);
               int exponent = Integer.parseInt(exponentStr);
               int numFractionDigits = ePos - 2 - exponent;
               if (numFractionDigits < 0) {
                  return 0;
               } else {
                  for (int i = ePos - 1; numFractionDigits > 0 && buf.charAt(i) == '0'; i--) {
                     numFractionDigits--;
                  }

                  return numFractionDigits;
               }
            }
         } else {
            return 0;
         }
      }

      @Deprecated
      private FixedDecimal(PluralRules.FixedDecimal other) {
         this.source = other.source;
         this.visibleDecimalDigitCount = other.visibleDecimalDigitCount;
         this.visibleDecimalDigitCountWithoutTrailingZeros = other.visibleDecimalDigitCountWithoutTrailingZeros;
         this.decimalDigits = other.decimalDigits;
         this.decimalDigitsWithoutTrailingZeros = other.decimalDigitsWithoutTrailingZeros;
         this.integerValue = other.integerValue;
         this.hasIntegerValue = other.hasIntegerValue;
         this.isNegative = other.isNegative;
         this.exponent = other.exponent;
         this.baseFactor = other.baseFactor;
      }

      @Deprecated
      public FixedDecimal(String n) {
         this(parseDecimalSampleRangeNumString(n));
      }

      @Deprecated
      private static PluralRules.FixedDecimal parseDecimalSampleRangeNumString(String num) {
         if (!num.contains("e") && !num.contains("c")) {
            return new PluralRules.FixedDecimal(Double.parseDouble(num), getVisibleFractionCount(num));
         } else {
            int ePos = num.lastIndexOf(101);
            if (ePos < 0) {
               ePos = num.lastIndexOf(99);
            }

            int expNumPos = ePos + 1;
            String exponentStr = num.substring(expNumPos);
            int exponent = Integer.parseInt(exponentStr);
            String fractionStr = num.substring(0, ePos);
            return createWithExponent(Double.parseDouble(fractionStr), getVisibleFractionCount(fractionStr), exponent);
         }
      }

      private static int getVisibleFractionCount(String value) {
         value = value.trim();
         int decimalPos = value.indexOf(46) + 1;
         return decimalPos == 0 ? 0 : value.length() - decimalPos;
      }

      @Deprecated
      @Override
      public double getPluralOperand(PluralRules.Operand operand) {
         switch (operand) {
            case n:
               return this.exponent == 0 ? this.source : this.source * Math.pow(10.0, this.exponent);
            case i:
               return this.intValue();
            case f:
               return this.decimalDigits;
            case t:
               return this.decimalDigitsWithoutTrailingZeros;
            case v:
               return this.visibleDecimalDigitCount;
            case w:
               return this.visibleDecimalDigitCountWithoutTrailingZeros;
            case e:
               return this.exponent;
            case c:
               return this.exponent;
            default:
               return this.doubleValue();
         }
      }

      @Deprecated
      public static PluralRules.Operand getOperand(String t) {
         return PluralRules.Operand.valueOf(t);
      }

      @Deprecated
      public int compareTo(PluralRules.FixedDecimal other) {
         if (this.exponent != other.exponent) {
            return this.doubleValue() < other.doubleValue() ? -1 : 1;
         } else if (this.integerValue != other.integerValue) {
            return this.integerValue < other.integerValue ? -1 : 1;
         } else if (this.source != other.source) {
            return this.source < other.source ? -1 : 1;
         } else if (this.visibleDecimalDigitCount != other.visibleDecimalDigitCount) {
            return this.visibleDecimalDigitCount < other.visibleDecimalDigitCount ? -1 : 1;
         } else {
            long diff = this.decimalDigits - other.decimalDigits;
            if (diff != 0L) {
               return diff < 0L ? -1 : 1;
            } else {
               return 0;
            }
         }
      }

      @Deprecated
      @Override
      public boolean equals(Object arg0) {
         if (arg0 == null) {
            return false;
         } else if (arg0 == this) {
            return true;
         } else if (!(arg0 instanceof PluralRules.FixedDecimal)) {
            return false;
         } else {
            PluralRules.FixedDecimal other = (PluralRules.FixedDecimal)arg0;
            return this.source == other.source
               && this.visibleDecimalDigitCount == other.visibleDecimalDigitCount
               && this.decimalDigits == other.decimalDigits
               && this.exponent == other.exponent;
         }
      }

      @Deprecated
      @Override
      public int hashCode() {
         return (int)(this.decimalDigits + 37 * (this.visibleDecimalDigitCount + (int)(37.0 * this.source)));
      }

      @Deprecated
      @Override
      public String toString() {
         String baseString = String.format(Locale.ROOT, "%." + this.visibleDecimalDigitCount + "f", this.source);
         return this.exponent != 0 ? baseString + "e" + this.exponent : baseString;
      }

      @Deprecated
      public boolean hasIntegerValue() {
         return this.hasIntegerValue;
      }

      @Deprecated
      @Override
      public int intValue() {
         return (int)this.longValue();
      }

      @Deprecated
      @Override
      public long longValue() {
         return this.exponent == 0 ? this.integerValue : (long)(Math.pow(10.0, this.exponent) * this.integerValue);
      }

      @Deprecated
      @Override
      public float floatValue() {
         return (float)(this.source * Math.pow(10.0, this.exponent));
      }

      @Deprecated
      @Override
      public double doubleValue() {
         return (this.isNegative ? -this.source : this.source) * Math.pow(10.0, this.exponent);
      }

      @Deprecated
      public long getShiftedValue() {
         return this.exponent != 0 && this.visibleDecimalDigitCount == 0 && this.decimalDigits == 0L
            ? (long)(this.source * Math.pow(10.0, this.exponent))
            : this.integerValue * this.baseFactor + this.decimalDigits;
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         throw new NotSerializableException();
      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         throw new NotSerializableException();
      }

      @Deprecated
      @Override
      public boolean isNaN() {
         return Double.isNaN(this.source);
      }

      @Deprecated
      @Override
      public boolean isInfinite() {
         return Double.isInfinite(this.source);
      }
   }

   @Deprecated
   public static class FixedDecimalRange {
      @Deprecated
      public final PluralRules.FixedDecimal start;
      @Deprecated
      public final PluralRules.FixedDecimal end;

      @Deprecated
      public FixedDecimalRange(PluralRules.FixedDecimal start, PluralRules.FixedDecimal end) {
         if (start.visibleDecimalDigitCount != end.visibleDecimalDigitCount) {
            throw new IllegalArgumentException("Ranges must have the same number of visible decimals: " + start + "~" + end);
         } else {
            this.start = start;
            this.end = end;
         }
      }

      @Deprecated
      @Override
      public String toString() {
         return this.start + (this.end == this.start ? "" : "~" + this.end);
      }
   }

   @Deprecated
   public static class FixedDecimalSamples {
      @Deprecated
      public final PluralRules.SampleType sampleType;
      @Deprecated
      public final Set<PluralRules.FixedDecimalRange> samples;
      @Deprecated
      public final boolean bounded;

      private FixedDecimalSamples(PluralRules.SampleType sampleType, Set<PluralRules.FixedDecimalRange> samples, boolean bounded) {
         this.sampleType = sampleType;
         this.samples = samples;
         this.bounded = bounded;
      }

      static PluralRules.FixedDecimalSamples parse(String source) {
         boolean bounded2 = true;
         boolean haveBound = false;
         Set<PluralRules.FixedDecimalRange> samples2 = new LinkedHashSet<>();
         PluralRules.SampleType sampleType2;
         if (source.startsWith("integer")) {
            sampleType2 = PluralRules.SampleType.INTEGER;
         } else {
            if (!source.startsWith("decimal")) {
               throw new IllegalArgumentException("Samples must start with 'integer' or 'decimal'");
            }

            sampleType2 = PluralRules.SampleType.DECIMAL;
         }

         source = source.substring(7).trim();

         for (String range : PluralRules.COMMA_SEPARATED.split(source)) {
            if (!range.equals("…") && !range.equals("...")) {
               if (haveBound) {
                  throw new IllegalArgumentException("Can only have … at the end of samples: " + range);
               }

               String[] rangeParts = PluralRules.TILDE_SEPARATED.split(range);
               switch (rangeParts.length) {
                  case 1:
                     PluralRules.FixedDecimal sample = new PluralRules.FixedDecimal(rangeParts[0]);
                     checkDecimal(sampleType2, sample);
                     samples2.add(new PluralRules.FixedDecimalRange(sample, sample));
                     break;
                  case 2:
                     PluralRules.FixedDecimal start = new PluralRules.FixedDecimal(rangeParts[0]);
                     PluralRules.FixedDecimal end = new PluralRules.FixedDecimal(rangeParts[1]);
                     checkDecimal(sampleType2, start);
                     checkDecimal(sampleType2, end);
                     samples2.add(new PluralRules.FixedDecimalRange(start, end));
                     break;
                  default:
                     throw new IllegalArgumentException("Ill-formed number range: " + range);
               }
            } else {
               bounded2 = false;
               haveBound = true;
            }
         }

         return new PluralRules.FixedDecimalSamples(sampleType2, Collections.unmodifiableSet(samples2), bounded2);
      }

      private static void checkDecimal(PluralRules.SampleType sampleType2, PluralRules.FixedDecimal sample) {
         if (sampleType2 == PluralRules.SampleType.INTEGER != (sample.getVisibleDecimalDigitCount() == 0)) {
            throw new IllegalArgumentException("Ill-formed number range: " + sample);
         }
      }

      @Deprecated
      public Set<Double> addSamples(Set<Double> result) {
         for (PluralRules.FixedDecimalRange item : this.samples) {
            long startDouble = item.start.getShiftedValue();
            long endDouble = item.end.getShiftedValue();

            for (long d = startDouble; d <= endDouble; d++) {
               result.add((double)d / item.start.baseFactor);
            }
         }

         return result;
      }

      @Deprecated
      @Override
      public String toString() {
         StringBuilder b = new StringBuilder("@").append(this.sampleType.toString().toLowerCase(Locale.ENGLISH));
         boolean first = true;

         for (PluralRules.FixedDecimalRange item : this.samples) {
            if (first) {
               first = false;
            } else {
               b.append(",");
            }

            b.append(' ').append(item);
         }

         if (!this.bounded) {
            b.append(", …");
         }

         return b.toString();
      }

      @Deprecated
      public Set<PluralRules.FixedDecimalRange> getSamples() {
         return this.samples;
      }

      @Deprecated
      public void getStartEndSamples(Set<PluralRules.FixedDecimal> target) {
         for (PluralRules.FixedDecimalRange item : this.samples) {
            target.add(item.start);
            target.add(item.end);
         }
      }
   }

   @Deprecated
   public interface IFixedDecimal {
      @Deprecated
      double getPluralOperand(PluralRules.Operand var1);

      @Deprecated
      boolean isNaN();

      @Deprecated
      boolean isInfinite();

      @Deprecated
      boolean isHasIntegerValue();
   }

   public static enum KeywordStatus {
      INVALID,
      SUPPRESSED,
      UNIQUE,
      BOUNDED,
      UNBOUNDED;
   }

   @Deprecated
   public static enum Operand {
      @Deprecated
      n,
      @Deprecated
      i,
      @Deprecated
      f,
      @Deprecated
      t,
      @Deprecated
      v,
      @Deprecated
      w,
      @Deprecated
      e,
      @Deprecated
      c,
      @Deprecated
      j;
   }

   private static class OrConstraint extends PluralRules.BinaryConstraint {
      private static final long serialVersionUID = 1405488568664762222L;

      OrConstraint(PluralRules.Constraint a, PluralRules.Constraint b) {
         super(a, b);
      }

      @Override
      public boolean isFulfilled(PluralRules.IFixedDecimal n) {
         return this.a.isFulfilled(n) || this.b.isFulfilled(n);
      }

      @Override
      public boolean isLimited(PluralRules.SampleType sampleType) {
         return this.a.isLimited(sampleType) && this.b.isLimited(sampleType);
      }

      @Override
      public String toString() {
         return this.a.toString() + " or " + this.b.toString();
      }
   }

   public static enum PluralType {
      CARDINAL,
      ORDINAL;
   }

   private static class RangeConstraint implements PluralRules.Constraint, Serializable {
      private static final long serialVersionUID = 1L;
      private final int mod;
      private final boolean inRange;
      private final boolean integersOnly;
      private final double lowerBound;
      private final double upperBound;
      private final long[] range_list;
      private final PluralRules.Operand operand;

      RangeConstraint(int mod, boolean inRange, PluralRules.Operand operand, boolean integersOnly, double lowBound, double highBound, long[] vals) {
         this.mod = mod;
         this.inRange = inRange;
         this.integersOnly = integersOnly;
         this.lowerBound = lowBound;
         this.upperBound = highBound;
         this.range_list = vals;
         this.operand = operand;
      }

      @Override
      public boolean isFulfilled(PluralRules.IFixedDecimal number) {
         double n = number.getPluralOperand(this.operand);
         if ((!this.integersOnly || n - (long)n == 0.0) && (this.operand != PluralRules.Operand.j || number.getPluralOperand(PluralRules.Operand.v) == 0.0)) {
            if (this.mod != 0) {
               n %= this.mod;
            }

            boolean test = n >= this.lowerBound && n <= this.upperBound;
            if (test && this.range_list != null) {
               test = false;

               for (int i = 0; !test && i < this.range_list.length; i += 2) {
                  test = n >= this.range_list[i] && n <= this.range_list[i + 1];
               }
            }

            return this.inRange == test;
         } else {
            return !this.inRange;
         }
      }

      @Override
      public boolean isLimited(PluralRules.SampleType sampleType) {
         boolean valueIsZero = this.lowerBound == this.upperBound && this.lowerBound == 0.0;
         boolean hasDecimals = (
               this.operand == PluralRules.Operand.v
                  || this.operand == PluralRules.Operand.w
                  || this.operand == PluralRules.Operand.f
                  || this.operand == PluralRules.Operand.t
            )
            && this.inRange != valueIsZero;
         switch (sampleType) {
            case INTEGER:
               return hasDecimals
                  || (this.operand == PluralRules.Operand.n || this.operand == PluralRules.Operand.i || this.operand == PluralRules.Operand.j)
                     && this.mod == 0
                     && this.inRange;
            case DECIMAL:
               return (!hasDecimals || this.operand == PluralRules.Operand.n || this.operand == PluralRules.Operand.j)
                  && (this.integersOnly || this.lowerBound == this.upperBound)
                  && this.mod == 0
                  && this.inRange;
            default:
               return false;
         }
      }

      @Override
      public String toString() {
         StringBuilder result = new StringBuilder();
         result.append(this.operand);
         if (this.mod != 0) {
            result.append(" % ").append(this.mod);
         }

         boolean isList = this.lowerBound != this.upperBound;
         result.append(
            !isList ? (this.inRange ? " = " : " != ") : (this.integersOnly ? (this.inRange ? " = " : " != ") : (this.inRange ? " within " : " not within "))
         );
         if (this.range_list != null) {
            for (int i = 0; i < this.range_list.length; i += 2) {
               PluralRules.addRange(result, (double)this.range_list[i], (double)this.range_list[i + 1], i != 0);
            }
         } else {
            PluralRules.addRange(result, this.lowerBound, this.upperBound, false);
         }

         return result.toString();
      }
   }

   private static class Rule implements Serializable {
      private static final long serialVersionUID = 1L;
      private final String keyword;
      private final PluralRules.Constraint constraint;
      private final PluralRules.FixedDecimalSamples integerSamples;
      private final PluralRules.FixedDecimalSamples decimalSamples;

      public Rule(
         String keyword, PluralRules.Constraint constraint, PluralRules.FixedDecimalSamples integerSamples, PluralRules.FixedDecimalSamples decimalSamples
      ) {
         this.keyword = keyword;
         this.constraint = constraint;
         this.integerSamples = integerSamples;
         this.decimalSamples = decimalSamples;
      }

      public PluralRules.Rule and(PluralRules.Constraint c) {
         return new PluralRules.Rule(this.keyword, new PluralRules.AndConstraint(this.constraint, c), this.integerSamples, this.decimalSamples);
      }

      public PluralRules.Rule or(PluralRules.Constraint c) {
         return new PluralRules.Rule(this.keyword, new PluralRules.OrConstraint(this.constraint, c), this.integerSamples, this.decimalSamples);
      }

      public String getKeyword() {
         return this.keyword;
      }

      public boolean appliesTo(PluralRules.IFixedDecimal n) {
         return this.constraint.isFulfilled(n);
      }

      public boolean isLimited(PluralRules.SampleType sampleType) {
         return this.constraint.isLimited(sampleType);
      }

      @Override
      public String toString() {
         return this.keyword
            + ": "
            + this.constraint.toString()
            + (this.integerSamples == null ? "" : " " + this.integerSamples.toString())
            + (this.decimalSamples == null ? "" : " " + this.decimalSamples.toString());
      }

      @Override
      public int hashCode() {
         return this.keyword.hashCode() ^ this.constraint.hashCode();
      }

      public String getConstraint() {
         return this.constraint.toString();
      }
   }

   private static class RuleList implements Serializable {
      private boolean hasExplicitBoundingInfo = false;
      private static final long serialVersionUID = 1L;
      private final List<PluralRules.Rule> rules = new ArrayList<>();

      private RuleList() {
      }

      public PluralRules.RuleList addRule(PluralRules.Rule nextRule) {
         String keyword = nextRule.getKeyword();

         for (PluralRules.Rule rule : this.rules) {
            if (keyword.equals(rule.getKeyword())) {
               throw new IllegalArgumentException("Duplicate keyword: " + keyword);
            }
         }

         this.rules.add(nextRule);
         return this;
      }

      public PluralRules.RuleList finish() throws ParseException {
         PluralRules.Rule otherRule = null;
         Iterator<PluralRules.Rule> it = this.rules.iterator();

         while (it.hasNext()) {
            PluralRules.Rule rule = it.next();
            if ("other".equals(rule.getKeyword())) {
               otherRule = rule;
               it.remove();
            }
         }

         if (otherRule == null) {
            otherRule = PluralRules.parseRule("other:");
         }

         this.rules.add(otherRule);
         return this;
      }

      private PluralRules.Rule selectRule(PluralRules.IFixedDecimal n) {
         for (PluralRules.Rule rule : this.rules) {
            if (rule.appliesTo(n)) {
               return rule;
            }
         }

         return null;
      }

      public String select(PluralRules.IFixedDecimal n) {
         if (!n.isInfinite() && !n.isNaN()) {
            PluralRules.Rule r = this.selectRule(n);
            return r.getKeyword();
         } else {
            return "other";
         }
      }

      public Set<String> getKeywords() {
         Set<String> result = new LinkedHashSet<>();

         for (PluralRules.Rule rule : this.rules) {
            result.add(rule.getKeyword());
         }

         return result;
      }

      public boolean isLimited(String keyword, PluralRules.SampleType sampleType) {
         if (this.hasExplicitBoundingInfo) {
            PluralRules.FixedDecimalSamples mySamples = this.getDecimalSamples(keyword, sampleType);
            return mySamples == null ? true : mySamples.bounded;
         } else {
            return this.computeLimited(keyword, sampleType);
         }
      }

      public boolean computeLimited(String keyword, PluralRules.SampleType sampleType) {
         boolean result = false;

         for (PluralRules.Rule rule : this.rules) {
            if (keyword.equals(rule.getKeyword())) {
               if (!rule.isLimited(sampleType)) {
                  return false;
               }

               result = true;
            }
         }

         return result;
      }

      @Override
      public String toString() {
         StringBuilder builder = new StringBuilder();

         for (PluralRules.Rule rule : this.rules) {
            if (builder.length() != 0) {
               builder.append(";  ");
            }

            builder.append(rule);
         }

         return builder.toString();
      }

      public String getRules(String keyword) {
         for (PluralRules.Rule rule : this.rules) {
            if (rule.getKeyword().equals(keyword)) {
               return rule.getConstraint();
            }
         }

         return null;
      }

      public boolean select(PluralRules.IFixedDecimal sample, String keyword) {
         for (PluralRules.Rule rule : this.rules) {
            if (rule.getKeyword().equals(keyword) && rule.appliesTo(sample)) {
               return true;
            }
         }

         return false;
      }

      public PluralRules.FixedDecimalSamples getDecimalSamples(String keyword, PluralRules.SampleType sampleType) {
         for (PluralRules.Rule rule : this.rules) {
            if (rule.getKeyword().equals(keyword)) {
               return sampleType == PluralRules.SampleType.INTEGER ? rule.integerSamples : rule.decimalSamples;
            }
         }

         return null;
      }
   }

   @Deprecated
   public static enum SampleType {
      @Deprecated
      INTEGER,
      @Deprecated
      DECIMAL;
   }

   static class SimpleTokenizer {
      static final UnicodeSet BREAK_AND_IGNORE = new UnicodeSet(9, 10, 12, 13, 32, 32).freeze();
      static final UnicodeSet BREAK_AND_KEEP = new UnicodeSet(33, 33, 37, 37, 44, 44, 46, 46, 61, 61).freeze();

      static String[] split(String source) {
         int last = -1;
         List<String> result = new ArrayList<>();

         for (int i = 0; i < source.length(); i++) {
            char ch = source.charAt(i);
            if (BREAK_AND_IGNORE.contains(ch)) {
               if (last >= 0) {
                  result.add(source.substring(last, i));
                  last = -1;
               }
            } else if (BREAK_AND_KEEP.contains(ch)) {
               if (last >= 0) {
                  result.add(source.substring(last, i));
               }

               result.add(source.substring(i, i + 1));
               last = -1;
            } else if (last < 0) {
               last = i;
            }
         }

         if (last >= 0) {
            result.add(source.substring(last));
         }

         return result.toArray(new String[result.size()]);
      }
   }
}
