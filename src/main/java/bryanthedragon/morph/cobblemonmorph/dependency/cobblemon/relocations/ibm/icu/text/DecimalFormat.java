package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedStringBuilder;
import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.Utility;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.AffixUtils;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalFormatProperties;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.Padder;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.PatternStringParser;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.PatternStringUtils;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.Properties;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.NumberParserImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.ParsedNumber;
import com.cobblemon.mod.relocations.ibm.icu.math.MathContext;
import com.cobblemon.mod.relocations.ibm.icu.number.FormattedNumber;
import com.cobblemon.mod.relocations.ibm.icu.number.LocalizedNumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.number.NumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.util.Currency;
import com.cobblemon.mod.relocations.ibm.icu.util.CurrencyAmount;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.ObjectInputStream.GetField;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.ParsePosition;

public class DecimalFormat extends NumberFormat {
   private static final long serialVersionUID = 864413376551465018L;
   private final int serialVersionOnStream = 5;
   transient DecimalFormatProperties properties;
   transient volatile DecimalFormatSymbols symbols;
   transient volatile LocalizedNumberFormatter formatter;
   transient volatile DecimalFormatProperties exportedProperties;
   transient volatile NumberParserImpl parser;
   transient volatile NumberParserImpl currencyParser;
   private transient int icuMathContextForm = 0;
   public static final int MINIMUM_GROUPING_DIGITS_AUTO = -2;
   public static final int MINIMUM_GROUPING_DIGITS_MIN2 = -3;
   public static final int PAD_BEFORE_PREFIX = 0;
   public static final int PAD_AFTER_PREFIX = 1;
   public static final int PAD_BEFORE_SUFFIX = 2;
   public static final int PAD_AFTER_SUFFIX = 3;

   public DecimalFormat() {
      ULocale def = ULocale.getDefault(ULocale.Category.FORMAT);
      String pattern = getPattern(def, 0);
      this.symbols = getDefaultSymbols();
      this.properties = new DecimalFormatProperties();
      this.exportedProperties = new DecimalFormatProperties();
      this.setPropertiesFromPattern(pattern, 1);
      this.refreshFormatter();
   }

   public DecimalFormat(String pattern) {
      this.symbols = getDefaultSymbols();
      this.properties = new DecimalFormatProperties();
      this.exportedProperties = new DecimalFormatProperties();
      this.setPropertiesFromPattern(pattern, 1);
      this.refreshFormatter();
   }

   public DecimalFormat(String pattern, DecimalFormatSymbols symbols) {
      this.symbols = (DecimalFormatSymbols)symbols.clone();
      this.properties = new DecimalFormatProperties();
      this.exportedProperties = new DecimalFormatProperties();
      this.setPropertiesFromPattern(pattern, 1);
      this.refreshFormatter();
   }

   public DecimalFormat(String pattern, DecimalFormatSymbols symbols, CurrencyPluralInfo infoInput, int style) {
      this(pattern, symbols, style);
      this.properties.setCurrencyPluralInfo(infoInput);
      this.refreshFormatter();
   }

   DecimalFormat(String pattern, DecimalFormatSymbols symbols, int choice) {
      this.symbols = (DecimalFormatSymbols)symbols.clone();
      this.properties = new DecimalFormatProperties();
      this.exportedProperties = new DecimalFormatProperties();
      if (choice != 1 && choice != 5 && choice != 7 && choice != 8 && choice != 9 && choice != 6) {
         this.setPropertiesFromPattern(pattern, 1);
      } else {
         this.setPropertiesFromPattern(pattern, 2);
      }

      this.refreshFormatter();
   }

   private static DecimalFormatSymbols getDefaultSymbols() {
      return DecimalFormatSymbols.getInstance();
   }

   public synchronized void applyPattern(String pattern) {
      this.setPropertiesFromPattern(pattern, 0);
      this.properties.setPositivePrefix(null);
      this.properties.setNegativePrefix(null);
      this.properties.setPositiveSuffix(null);
      this.properties.setNegativeSuffix(null);
      this.properties.setCurrencyPluralInfo(null);
      this.refreshFormatter();
   }

   public synchronized void applyLocalizedPattern(String localizedPattern) {
      String pattern = PatternStringUtils.convertLocalized(localizedPattern, this.symbols, false);
      this.applyPattern(pattern);
   }

   @Override
   public Object clone() {
      DecimalFormat other = (DecimalFormat)super.clone();
      other.symbols = (DecimalFormatSymbols)this.symbols.clone();
      other.properties = this.properties.clone();
      other.exportedProperties = new DecimalFormatProperties();
      other.refreshFormatter();
      return other;
   }

   private synchronized void writeObject(ObjectOutputStream oos) throws IOException {
      oos.defaultWriteObject();
      oos.writeInt(0);
      oos.writeObject(this.properties);
      oos.writeObject(this.symbols);
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      GetField fieldGetter = ois.readFields();
      ObjectStreamField[] serializedFields = fieldGetter.getObjectStreamClass().getFields();
      int serialVersion = fieldGetter.get("serialVersionOnStream", -1);
      if (serialVersion > 5) {
         throw new IOException("Cannot deserialize newer com.ibm.icu.text.DecimalFormat (v" + serialVersion + ")");
      } else {
         if (serialVersion == 5) {
            if (serializedFields.length > 1) {
               throw new IOException("Too many fields when reading serial version 5");
            }

            ois.readInt();
            Object serializedProperties = ois.readObject();
            if (serializedProperties instanceof DecimalFormatProperties) {
               this.properties = (DecimalFormatProperties)serializedProperties;
            } else {
               this.properties = ((Properties)serializedProperties).getInstance();
            }

            this.symbols = (DecimalFormatSymbols)ois.readObject();
            this.exportedProperties = new DecimalFormatProperties();
            this.refreshFormatter();
         } else {
            this.properties = new DecimalFormatProperties();
            String pp = null;
            String ppp = null;
            String ps = null;
            String psp = null;
            String np = null;
            String npp = null;
            String ns = null;
            String nsp = null;

            for (ObjectStreamField field : serializedFields) {
               String name = field.getName();
               if (name.equals("decimalSeparatorAlwaysShown")) {
                  this.setDecimalSeparatorAlwaysShown(fieldGetter.get("decimalSeparatorAlwaysShown", false));
               } else if (name.equals("exponentSignAlwaysShown")) {
                  this.setExponentSignAlwaysShown(fieldGetter.get("exponentSignAlwaysShown", false));
               } else if (name.equals("formatWidth")) {
                  this.setFormatWidth(fieldGetter.get("formatWidth", 0));
               } else if (name.equals("groupingSize")) {
                  this.setGroupingSize(fieldGetter.get("groupingSize", (byte)3));
               } else if (name.equals("groupingSize2")) {
                  this.setSecondaryGroupingSize(fieldGetter.get("groupingSize2", (byte)0));
               } else if (name.equals("maxSignificantDigits")) {
                  this.setMaximumSignificantDigits(fieldGetter.get("maxSignificantDigits", 6));
               } else if (name.equals("minExponentDigits")) {
                  this.setMinimumExponentDigits(fieldGetter.get("minExponentDigits", (byte)0));
               } else if (name.equals("minSignificantDigits")) {
                  this.setMinimumSignificantDigits(fieldGetter.get("minSignificantDigits", 1));
               } else if (name.equals("multiplier")) {
                  this.setMultiplier(fieldGetter.get("multiplier", 1));
               } else if (name.equals("pad")) {
                  this.setPadCharacter(fieldGetter.get("pad", ' '));
               } else if (name.equals("padPosition")) {
                  this.setPadPosition(fieldGetter.get("padPosition", 0));
               } else if (name.equals("parseBigDecimal")) {
                  this.setParseBigDecimal(fieldGetter.get("parseBigDecimal", false));
               } else if (name.equals("parseRequireDecimalPoint")) {
                  this.setDecimalPatternMatchRequired(fieldGetter.get("parseRequireDecimalPoint", false));
               } else if (name.equals("roundingMode")) {
                  this.setRoundingMode(fieldGetter.get("roundingMode", 0));
               } else if (name.equals("useExponentialNotation")) {
                  this.setScientificNotation(fieldGetter.get("useExponentialNotation", false));
               } else if (name.equals("useSignificantDigits")) {
                  this.setSignificantDigitsUsed(fieldGetter.get("useSignificantDigits", false));
               } else if (name.equals("currencyPluralInfo")) {
                  this.setCurrencyPluralInfo((CurrencyPluralInfo)fieldGetter.get("currencyPluralInfo", null));
               } else if (name.equals("mathContext")) {
                  this.setMathContextICU((MathContext)fieldGetter.get("mathContext", null));
               } else if (name.equals("negPrefixPattern")) {
                  npp = (String)fieldGetter.get("negPrefixPattern", null);
               } else if (name.equals("negSuffixPattern")) {
                  nsp = (String)fieldGetter.get("negSuffixPattern", null);
               } else if (name.equals("negativePrefix")) {
                  np = (String)fieldGetter.get("negativePrefix", null);
               } else if (name.equals("negativeSuffix")) {
                  ns = (String)fieldGetter.get("negativeSuffix", null);
               } else if (name.equals("posPrefixPattern")) {
                  ppp = (String)fieldGetter.get("posPrefixPattern", null);
               } else if (name.equals("posSuffixPattern")) {
                  psp = (String)fieldGetter.get("posSuffixPattern", null);
               } else if (name.equals("positivePrefix")) {
                  pp = (String)fieldGetter.get("positivePrefix", null);
               } else if (name.equals("positiveSuffix")) {
                  ps = (String)fieldGetter.get("positiveSuffix", null);
               } else if (name.equals("roundingIncrement")) {
                  this.setRoundingIncrement((BigDecimal)fieldGetter.get("roundingIncrement", null));
               } else if (name.equals("symbols")) {
                  this.setDecimalFormatSymbols((DecimalFormatSymbols)fieldGetter.get("symbols", null));
               }
            }

            if (npp == null) {
               this.properties.setNegativePrefix(np);
            } else {
               this.properties.setNegativePrefixPattern(npp);
            }

            if (nsp == null) {
               this.properties.setNegativeSuffix(ns);
            } else {
               this.properties.setNegativeSuffixPattern(nsp);
            }

            if (ppp == null) {
               this.properties.setPositivePrefix(pp);
            } else {
               this.properties.setPositivePrefixPattern(ppp);
            }

            if (psp == null) {
               this.properties.setPositiveSuffix(ps);
            } else {
               this.properties.setPositiveSuffixPattern(psp);
            }

            try {
               java.lang.reflect.Field getter = NumberFormat.class.getDeclaredField("groupingUsed");
               getter.setAccessible(true);
               this.setGroupingUsed((Boolean)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("parseIntegerOnly");
               getter.setAccessible(true);
               this.setParseIntegerOnly((Boolean)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("maximumIntegerDigits");
               getter.setAccessible(true);
               this.setMaximumIntegerDigits((Integer)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("minimumIntegerDigits");
               getter.setAccessible(true);
               this.setMinimumIntegerDigits((Integer)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("maximumFractionDigits");
               getter.setAccessible(true);
               this.setMaximumFractionDigits((Integer)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("minimumFractionDigits");
               getter.setAccessible(true);
               this.setMinimumFractionDigits((Integer)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("currency");
               getter.setAccessible(true);
               this.setCurrency((Currency)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("parseStrict");
               getter.setAccessible(true);
               this.setParseStrict((Boolean)getter.get(this));
            } catch (IllegalArgumentException var18) {
               throw new IOException(var18);
            } catch (IllegalAccessException var19) {
               throw new IOException(var19);
            } catch (NoSuchFieldException var20) {
               throw new IOException(var20);
            } catch (SecurityException var21) {
               throw new IOException(var21);
            }

            if (this.symbols == null) {
               this.symbols = getDefaultSymbols();
            }

            this.exportedProperties = new DecimalFormatProperties();
            this.refreshFormatter();
         }
      }
   }

   @Override
   public StringBuffer format(double number, StringBuffer result, FieldPosition fieldPosition) {
      DecimalQuantity dq = new DecimalQuantity_DualStorageBCD(number);
      FormattedStringBuilder string = new FormattedStringBuilder();
      this.formatter.formatImpl(dq, string);
      fieldPositionHelper(dq, string, fieldPosition, result.length());
      Utility.appendTo(string, result);
      return result;
   }

   @Override
   public StringBuffer format(long number, StringBuffer result, FieldPosition fieldPosition) {
      DecimalQuantity dq = new DecimalQuantity_DualStorageBCD(number);
      FormattedStringBuilder string = new FormattedStringBuilder();
      this.formatter.formatImpl(dq, string);
      fieldPositionHelper(dq, string, fieldPosition, result.length());
      Utility.appendTo(string, result);
      return result;
   }

   @Override
   public StringBuffer format(BigInteger number, StringBuffer result, FieldPosition fieldPosition) {
      DecimalQuantity dq = new DecimalQuantity_DualStorageBCD(number);
      FormattedStringBuilder string = new FormattedStringBuilder();
      this.formatter.formatImpl(dq, string);
      fieldPositionHelper(dq, string, fieldPosition, result.length());
      Utility.appendTo(string, result);
      return result;
   }

   @Override
   public StringBuffer format(BigDecimal number, StringBuffer result, FieldPosition fieldPosition) {
      DecimalQuantity dq = new DecimalQuantity_DualStorageBCD(number);
      FormattedStringBuilder string = new FormattedStringBuilder();
      this.formatter.formatImpl(dq, string);
      fieldPositionHelper(dq, string, fieldPosition, result.length());
      Utility.appendTo(string, result);
      return result;
   }

   @Override
   public StringBuffer format(com.cobblemon.mod.relocations.ibm.icu.math.BigDecimal number, StringBuffer result, FieldPosition fieldPosition) {
      DecimalQuantity dq = new DecimalQuantity_DualStorageBCD(number);
      FormattedStringBuilder string = new FormattedStringBuilder();
      this.formatter.formatImpl(dq, string);
      fieldPositionHelper(dq, string, fieldPosition, result.length());
      Utility.appendTo(string, result);
      return result;
   }

   @Override
   public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
      if (!(obj instanceof Number)) {
         throw new IllegalArgumentException();
      } else {
         Number number = (Number)obj;
         FormattedNumber output = this.formatter.format(number);
         return output.toCharacterIterator();
      }
   }

   @Override
   public StringBuffer format(CurrencyAmount currAmt, StringBuffer result, FieldPosition fieldPosition) {
      DecimalFormatSymbols localSymbols = (DecimalFormatSymbols)this.symbols.clone();
      localSymbols.setCurrency(currAmt.getCurrency());
      DecimalQuantity dq = new DecimalQuantity_DualStorageBCD(currAmt.getNumber());
      FormattedStringBuilder string = new FormattedStringBuilder();
      this.formatter.symbols(localSymbols).unit(currAmt.getCurrency()).formatImpl(dq, string);
      fieldPositionHelper(dq, string, fieldPosition, result.length());
      Utility.appendTo(string, result);
      return result;
   }

   @Override
   public Number parse(String text, ParsePosition parsePosition) {
      if (text == null) {
         throw new IllegalArgumentException("Text cannot be null");
      } else {
         if (parsePosition == null) {
            parsePosition = new ParsePosition(0);
         }

         if (parsePosition.getIndex() < 0) {
            throw new IllegalArgumentException("Cannot start parsing at a negative offset");
         } else if (parsePosition.getIndex() >= text.length()) {
            return null;
         } else {
            ParsedNumber result = new ParsedNumber();
            int startIndex = parsePosition.getIndex();
            NumberParserImpl parser = this.getParser();
            parser.parse(text, startIndex, true, result);
            if (result.success()) {
               parsePosition.setIndex(result.charEnd);
               Number number = result.getNumber(parser.getParseFlags());
               if (number instanceof BigDecimal) {
                  number = this.safeConvertBigDecimal((BigDecimal)number);
               }

               return number;
            } else {
               parsePosition.setErrorIndex(startIndex + result.charEnd);
               return null;
            }
         }
      }
   }

   @Override
   public CurrencyAmount parseCurrency(CharSequence text, ParsePosition parsePosition) {
      if (text == null) {
         throw new IllegalArgumentException("Text cannot be null");
      } else {
         if (parsePosition == null) {
            parsePosition = new ParsePosition(0);
         }

         if (parsePosition.getIndex() < 0) {
            throw new IllegalArgumentException("Cannot start parsing at a negative offset");
         } else if (parsePosition.getIndex() >= text.length()) {
            return null;
         } else {
            ParsedNumber result = new ParsedNumber();
            int startIndex = parsePosition.getIndex();
            NumberParserImpl parser = this.getCurrencyParser();
            parser.parse(text.toString(), startIndex, true, result);
            if (result.success()) {
               parsePosition.setIndex(result.charEnd);
               Number number = result.getNumber(parser.getParseFlags());
               if (number instanceof BigDecimal) {
                  number = this.safeConvertBigDecimal((BigDecimal)number);
               }

               Currency currency = Currency.getInstance(result.currencyCode);
               return new CurrencyAmount(number, currency);
            } else {
               parsePosition.setErrorIndex(startIndex + result.charEnd);
               return null;
            }
         }
      }
   }

   public synchronized DecimalFormatSymbols getDecimalFormatSymbols() {
      return (DecimalFormatSymbols)this.symbols.clone();
   }

   public synchronized void setDecimalFormatSymbols(DecimalFormatSymbols newSymbols) {
      this.symbols = (DecimalFormatSymbols)newSymbols.clone();
      this.refreshFormatter();
   }

   public synchronized String getPositivePrefix() {
      return this.formatter.getAffixImpl(true, false);
   }

   public synchronized void setPositivePrefix(String prefix) {
      if (prefix == null) {
         throw new NullPointerException();
      } else {
         this.properties.setPositivePrefix(prefix);
         this.refreshFormatter();
      }
   }

   public synchronized String getNegativePrefix() {
      return this.formatter.getAffixImpl(true, true);
   }

   public synchronized void setNegativePrefix(String prefix) {
      if (prefix == null) {
         throw new NullPointerException();
      } else {
         this.properties.setNegativePrefix(prefix);
         this.refreshFormatter();
      }
   }

   public synchronized String getPositiveSuffix() {
      return this.formatter.getAffixImpl(false, false);
   }

   public synchronized void setPositiveSuffix(String suffix) {
      if (suffix == null) {
         throw new NullPointerException();
      } else {
         this.properties.setPositiveSuffix(suffix);
         this.refreshFormatter();
      }
   }

   public synchronized String getNegativeSuffix() {
      return this.formatter.getAffixImpl(false, true);
   }

   public synchronized void setNegativeSuffix(String suffix) {
      if (suffix == null) {
         throw new NullPointerException();
      } else {
         this.properties.setNegativeSuffix(suffix);
         this.refreshFormatter();
      }
   }

   public synchronized boolean isSignAlwaysShown() {
      return this.properties.getSignAlwaysShown();
   }

   public synchronized void setSignAlwaysShown(boolean value) {
      this.properties.setSignAlwaysShown(value);
      this.refreshFormatter();
   }

   public synchronized int getMultiplier() {
      return this.properties.getMultiplier() != null
         ? this.properties.getMultiplier().intValue()
         : (int)Math.pow(10.0, this.properties.getMagnitudeMultiplier());
   }

   public synchronized void setMultiplier(int multiplier) {
      if (multiplier == 0) {
         throw new IllegalArgumentException("Multiplier must be nonzero.");
      } else {
         int delta = 0;
         int value = multiplier;

         while (value != 1) {
            delta++;
            int temp = value / 10;
            if (temp * 10 != value) {
               delta = -1;
               break;
            }

            value = temp;
         }

         if (delta != -1) {
            this.properties.setMagnitudeMultiplier(delta);
            this.properties.setMultiplier(null);
         } else {
            this.properties.setMagnitudeMultiplier(0);
            this.properties.setMultiplier(BigDecimal.valueOf((long)multiplier));
         }

         this.refreshFormatter();
      }
   }

   public synchronized BigDecimal getRoundingIncrement() {
      return this.exportedProperties.getRoundingIncrement();
   }

   public synchronized void setRoundingIncrement(BigDecimal increment) {
      if (increment != null && increment.compareTo(BigDecimal.ZERO) == 0) {
         this.properties.setMaximumFractionDigits(Integer.MAX_VALUE);
      } else {
         this.properties.setRoundingIncrement(increment);
         this.refreshFormatter();
      }
   }

   public synchronized void setRoundingIncrement(com.cobblemon.mod.relocations.ibm.icu.math.BigDecimal increment) {
      BigDecimal javaBigDecimal = increment == null ? null : increment.toBigDecimal();
      this.setRoundingIncrement(javaBigDecimal);
   }

   public synchronized void setRoundingIncrement(double increment) {
      if (increment == 0.0) {
         this.setRoundingIncrement((BigDecimal)null);
      } else {
         BigDecimal javaBigDecimal = BigDecimal.valueOf(increment).stripTrailingZeros();
         this.setRoundingIncrement(javaBigDecimal);
      }
   }

   @Override
   public synchronized int getRoundingMode() {
      RoundingMode mode = this.exportedProperties.getRoundingMode();
      return mode == null ? 0 : mode.ordinal();
   }

   @Override
   public synchronized void setRoundingMode(int roundingMode) {
      this.properties.setRoundingMode(RoundingMode.valueOf(roundingMode));
      this.refreshFormatter();
   }

   public synchronized java.math.MathContext getMathContext() {
      java.math.MathContext mathContext = this.exportedProperties.getMathContext();

      assert mathContext != null;

      return mathContext;
   }

   public synchronized void setMathContext(java.math.MathContext mathContext) {
      this.properties.setMathContext(mathContext);
      this.refreshFormatter();
   }

   public synchronized MathContext getMathContextICU() {
      java.math.MathContext mathContext = this.getMathContext();
      return new MathContext(mathContext.getPrecision(), this.icuMathContextForm, false, mathContext.getRoundingMode().ordinal());
   }

   public synchronized void setMathContextICU(MathContext mathContextICU) {
      this.icuMathContextForm = mathContextICU.getForm();
      java.math.MathContext mathContext;
      if (mathContextICU.getLostDigits()) {
         mathContext = new java.math.MathContext(mathContextICU.getDigits(), RoundingMode.UNNECESSARY);
      } else {
         mathContext = new java.math.MathContext(mathContextICU.getDigits(), RoundingMode.valueOf(mathContextICU.getRoundingMode()));
      }

      this.setMathContext(mathContext);
   }

   @Override
   public synchronized int getMinimumIntegerDigits() {
      return this.exportedProperties.getMinimumIntegerDigits();
   }

   @Override
   public synchronized void setMinimumIntegerDigits(int value) {
      int max = this.properties.getMaximumIntegerDigits();
      if (max >= 0 && max < value) {
         this.properties.setMaximumIntegerDigits(value);
      }

      this.properties.setMinimumIntegerDigits(value);
      this.refreshFormatter();
   }

   @Override
   public synchronized int getMaximumIntegerDigits() {
      return this.exportedProperties.getMaximumIntegerDigits();
   }

   @Override
   public synchronized void setMaximumIntegerDigits(int value) {
      int min = this.properties.getMinimumIntegerDigits();
      if (min >= 0 && min > value) {
         this.properties.setMinimumIntegerDigits(value);
      }

      this.properties.setMaximumIntegerDigits(value);
      this.refreshFormatter();
   }

   @Override
   public synchronized int getMinimumFractionDigits() {
      return this.exportedProperties.getMinimumFractionDigits();
   }

   @Override
   public synchronized void setMinimumFractionDigits(int value) {
      int max = this.properties.getMaximumFractionDigits();
      if (max >= 0 && max < value) {
         this.properties.setMaximumFractionDigits(value);
      }

      this.properties.setMinimumFractionDigits(value);
      this.refreshFormatter();
   }

   @Override
   public synchronized int getMaximumFractionDigits() {
      return this.exportedProperties.getMaximumFractionDigits();
   }

   @Override
   public synchronized void setMaximumFractionDigits(int value) {
      int min = this.properties.getMinimumFractionDigits();
      if (min >= 0 && min > value) {
         this.properties.setMinimumFractionDigits(value);
      }

      this.properties.setMaximumFractionDigits(value);
      this.refreshFormatter();
   }

   public synchronized boolean areSignificantDigitsUsed() {
      return this.properties.getMinimumSignificantDigits() != -1 || this.properties.getMaximumSignificantDigits() != -1;
   }

   public synchronized void setSignificantDigitsUsed(boolean useSignificantDigits) {
      int oldMinSig = this.properties.getMinimumSignificantDigits();
      int oldMaxSig = this.properties.getMaximumSignificantDigits();
      if (useSignificantDigits) {
         if (oldMinSig != -1 || oldMaxSig != -1) {
            return;
         }
      } else if (oldMinSig == -1 && oldMaxSig == -1) {
         return;
      }

      int minSig = useSignificantDigits ? 1 : -1;
      int maxSig = useSignificantDigits ? 6 : -1;
      this.properties.setMinimumSignificantDigits(minSig);
      this.properties.setMaximumSignificantDigits(maxSig);
      this.refreshFormatter();
   }

   public synchronized int getMinimumSignificantDigits() {
      return this.exportedProperties.getMinimumSignificantDigits();
   }

   public synchronized void setMinimumSignificantDigits(int value) {
      int max = this.properties.getMaximumSignificantDigits();
      if (max >= 0 && max < value) {
         this.properties.setMaximumSignificantDigits(value);
      }

      this.properties.setMinimumSignificantDigits(value);
      this.refreshFormatter();
   }

   public synchronized int getMaximumSignificantDigits() {
      return this.exportedProperties.getMaximumSignificantDigits();
   }

   public synchronized void setMaximumSignificantDigits(int value) {
      int min = this.properties.getMinimumSignificantDigits();
      if (min >= 0 && min > value) {
         this.properties.setMinimumSignificantDigits(value);
      }

      this.properties.setMaximumSignificantDigits(value);
      this.refreshFormatter();
   }

   public synchronized int getFormatWidth() {
      return this.properties.getFormatWidth();
   }

   public synchronized void setFormatWidth(int width) {
      this.properties.setFormatWidth(width);
      this.refreshFormatter();
   }

   public synchronized char getPadCharacter() {
      CharSequence paddingString = this.properties.getPadString();
      return paddingString == null ? " ".charAt(0) : paddingString.charAt(0);
   }

   public synchronized void setPadCharacter(char padChar) {
      this.properties.setPadString(Character.toString(padChar));
      this.refreshFormatter();
   }

   public synchronized int getPadPosition() {
      Padder.PadPosition loc = this.properties.getPadPosition();
      return loc == null ? 0 : loc.toOld();
   }

   public synchronized void setPadPosition(int padPos) {
      this.properties.setPadPosition(Padder.PadPosition.fromOld(padPos));
      this.refreshFormatter();
   }

   public synchronized boolean isScientificNotation() {
      return this.properties.getMinimumExponentDigits() != -1;
   }

   public synchronized void setScientificNotation(boolean useScientific) {
      if (useScientific) {
         this.properties.setMinimumExponentDigits(1);
      } else {
         this.properties.setMinimumExponentDigits(-1);
      }

      this.refreshFormatter();
   }

   public synchronized byte getMinimumExponentDigits() {
      return (byte)this.properties.getMinimumExponentDigits();
   }

   public synchronized void setMinimumExponentDigits(byte minExpDig) {
      this.properties.setMinimumExponentDigits(minExpDig);
      this.refreshFormatter();
   }

   public synchronized boolean isExponentSignAlwaysShown() {
      return this.properties.getExponentSignAlwaysShown();
   }

   public synchronized void setExponentSignAlwaysShown(boolean expSignAlways) {
      this.properties.setExponentSignAlwaysShown(expSignAlways);
      this.refreshFormatter();
   }

   @Override
   public synchronized boolean isGroupingUsed() {
      return this.properties.getGroupingUsed();
   }

   @Override
   public synchronized void setGroupingUsed(boolean enabled) {
      this.properties.setGroupingUsed(enabled);
      this.refreshFormatter();
   }

   public synchronized int getGroupingSize() {
      return this.properties.getGroupingSize() < 0 ? 0 : this.properties.getGroupingSize();
   }

   public synchronized void setGroupingSize(int width) {
      this.properties.setGroupingSize(width);
      this.refreshFormatter();
   }

   public synchronized int getSecondaryGroupingSize() {
      int grouping2 = this.properties.getSecondaryGroupingSize();
      return grouping2 < 0 ? 0 : grouping2;
   }

   public synchronized void setSecondaryGroupingSize(int width) {
      this.properties.setSecondaryGroupingSize(width);
      this.refreshFormatter();
   }

   public synchronized int getMinimumGroupingDigits() {
      return this.properties.getMinimumGroupingDigits() > 0 ? this.properties.getMinimumGroupingDigits() : 1;
   }

   public synchronized void setMinimumGroupingDigits(int number) {
      this.properties.setMinimumGroupingDigits(number);
      this.refreshFormatter();
   }

   public synchronized boolean isDecimalSeparatorAlwaysShown() {
      return this.properties.getDecimalSeparatorAlwaysShown();
   }

   public synchronized void setDecimalSeparatorAlwaysShown(boolean value) {
      this.properties.setDecimalSeparatorAlwaysShown(value);
      this.refreshFormatter();
   }

   @Override
   public synchronized Currency getCurrency() {
      return this.exportedProperties.getCurrency();
   }

   @Override
   public synchronized void setCurrency(Currency currency) {
      this.properties.setCurrency(currency);
      if (currency != null) {
         this.symbols.setCurrency(currency);
      }

      this.refreshFormatter();
   }

   public synchronized Currency.CurrencyUsage getCurrencyUsage() {
      Currency.CurrencyUsage usage = this.properties.getCurrencyUsage();
      if (usage == null) {
         usage = Currency.CurrencyUsage.STANDARD;
      }

      return usage;
   }

   public synchronized void setCurrencyUsage(Currency.CurrencyUsage usage) {
      this.properties.setCurrencyUsage(usage);
      this.refreshFormatter();
   }

   public synchronized CurrencyPluralInfo getCurrencyPluralInfo() {
      return this.properties.getCurrencyPluralInfo();
   }

   public synchronized void setCurrencyPluralInfo(CurrencyPluralInfo newInfo) {
      this.properties.setCurrencyPluralInfo(newInfo);
      this.refreshFormatter();
   }

   public synchronized boolean isParseBigDecimal() {
      return this.properties.getParseToBigDecimal();
   }

   public synchronized void setParseBigDecimal(boolean value) {
      this.properties.setParseToBigDecimal(value);
      this.refreshFormatter();
   }

   @Deprecated
   public int getParseMaxDigits() {
      return 1000;
   }

   @Deprecated
   public void setParseMaxDigits(int maxDigits) {
   }

   @Override
   public synchronized boolean isParseStrict() {
      return this.properties.getParseMode() == DecimalFormatProperties.ParseMode.STRICT;
   }

   @Override
   public synchronized void setParseStrict(boolean parseStrict) {
      DecimalFormatProperties.ParseMode mode = parseStrict ? DecimalFormatProperties.ParseMode.STRICT : DecimalFormatProperties.ParseMode.LENIENT;
      this.properties.setParseMode(mode);
      this.refreshFormatter();
   }

   @Deprecated
   public synchronized void setParseStrictMode(DecimalFormatProperties.ParseMode parseMode) {
      this.properties.setParseMode(parseMode);
      this.refreshFormatter();
   }

   @Override
   public synchronized boolean isParseIntegerOnly() {
      return this.properties.getParseIntegerOnly();
   }

   @Override
   public synchronized void setParseIntegerOnly(boolean parseIntegerOnly) {
      this.properties.setParseIntegerOnly(parseIntegerOnly);
      this.refreshFormatter();
   }

   public synchronized boolean isDecimalPatternMatchRequired() {
      return this.properties.getDecimalPatternMatchRequired();
   }

   public synchronized void setDecimalPatternMatchRequired(boolean value) {
      this.properties.setDecimalPatternMatchRequired(value);
      this.refreshFormatter();
   }

   public synchronized boolean isParseNoExponent() {
      return this.properties.getParseNoExponent();
   }

   public synchronized void setParseNoExponent(boolean value) {
      this.properties.setParseNoExponent(value);
      this.refreshFormatter();
   }

   public synchronized boolean isParseCaseSensitive() {
      return this.properties.getParseCaseSensitive();
   }

   public synchronized void setParseCaseSensitive(boolean value) {
      this.properties.setParseCaseSensitive(value);
      this.refreshFormatter();
   }

   @Override
   public synchronized boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (obj == this) {
         return true;
      } else if (!(obj instanceof DecimalFormat)) {
         return false;
      } else {
         DecimalFormat other = (DecimalFormat)obj;
         return this.properties.equals(other.properties) && this.symbols.equals(other.symbols);
      }
   }

   @Override
   public synchronized int hashCode() {
      return this.properties.hashCode() ^ this.symbols.hashCode();
   }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder();
      result.append(this.getClass().getName());
      result.append("@");
      result.append(Integer.toHexString(this.hashCode()));
      result.append(" { symbols@");
      result.append(Integer.toHexString(this.symbols.hashCode()));
      synchronized (this) {
         this.properties.toStringBare(result);
      }

      result.append(" }");
      return result.toString();
   }

   public synchronized String toPattern() {
      DecimalFormatProperties tprops = new DecimalFormatProperties().copyFrom(this.properties);
      boolean useCurrency = tprops.getCurrency() != null
         || tprops.getCurrencyPluralInfo() != null
         || tprops.getCurrencyUsage() != null
         || tprops.getCurrencyAsDecimal()
         || AffixUtils.hasCurrencySymbols(tprops.getPositivePrefixPattern())
         || AffixUtils.hasCurrencySymbols(tprops.getPositiveSuffixPattern())
         || AffixUtils.hasCurrencySymbols(tprops.getNegativePrefixPattern())
         || AffixUtils.hasCurrencySymbols(tprops.getNegativeSuffixPattern());
      if (useCurrency) {
         tprops.setMinimumFractionDigits(this.exportedProperties.getMinimumFractionDigits());
         tprops.setMaximumFractionDigits(this.exportedProperties.getMaximumFractionDigits());
         tprops.setRoundingIncrement(this.exportedProperties.getRoundingIncrement());
      }

      return PatternStringUtils.propertiesToPatternString(tprops);
   }

   public synchronized String toLocalizedPattern() {
      String pattern = this.toPattern();
      return PatternStringUtils.convertLocalized(pattern, this.symbols, true);
   }

   public LocalizedNumberFormatter toNumberFormatter() {
      return this.formatter;
   }

   @Deprecated
   public PluralRules.IFixedDecimal getFixedDecimal(double number) {
      return this.formatter.format(number).getFixedDecimal();
   }

   void refreshFormatter() {
      if (this.exportedProperties != null) {
         ULocale locale = this.getLocale(ULocale.ACTUAL_LOCALE);
         if (locale == null) {
            locale = this.symbols.getLocale(ULocale.ACTUAL_LOCALE);
         }

         if (locale == null) {
            locale = this.symbols.getULocale();
         }

         assert locale != null;

         this.formatter = NumberFormatter.fromDecimalFormat(this.properties, this.symbols, this.exportedProperties).locale(locale);
         this.parser = null;
         this.currencyParser = null;
      }
   }

   NumberParserImpl getParser() {
      if (this.parser == null) {
         this.parser = NumberParserImpl.createParserFromProperties(this.properties, this.symbols, false);
      }

      return this.parser;
   }

   NumberParserImpl getCurrencyParser() {
      if (this.currencyParser == null) {
         this.currencyParser = NumberParserImpl.createParserFromProperties(this.properties, this.symbols, true);
      }

      return this.currencyParser;
   }

   private Number safeConvertBigDecimal(BigDecimal number) {
      try {
         return new com.cobblemon.mod.relocations.ibm.icu.math.BigDecimal(number);
      } catch (NumberFormatException var3) {
         if (number.signum() > 0 && number.scale() < 0) {
            return Double.POSITIVE_INFINITY;
         } else if (number.scale() < 0) {
            return Double.NEGATIVE_INFINITY;
         } else {
            return number.signum() < 0 ? -0.0 : 0.0;
         }
      }
   }

   void setPropertiesFromPattern(String pattern, int ignoreRounding) {
      if (pattern == null) {
         throw new NullPointerException();
      } else {
         PatternStringParser.parseToExistingProperties(pattern, this.properties, ignoreRounding);
      }
   }

   static void fieldPositionHelper(DecimalQuantity dq, FormattedStringBuilder string, FieldPosition fieldPosition, int offset) {
      fieldPosition.setBeginIndex(0);
      fieldPosition.setEndIndex(0);
      dq.populateUFieldPosition(fieldPosition);
      boolean found = FormattedValueStringBuilderImpl.nextFieldPosition(string, fieldPosition);
      if (found && offset != 0) {
         fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + offset);
         fieldPosition.setEndIndex(fieldPosition.getEndIndex() + offset);
      }
   }

   @Deprecated
   public synchronized void setProperties(DecimalFormat.PropertySetter func) {
      func.set(this.properties);
      this.refreshFormatter();
   }

   @Deprecated
   public interface PropertySetter {
      @Deprecated
      void set(DecimalFormatProperties var1);
   }
}
