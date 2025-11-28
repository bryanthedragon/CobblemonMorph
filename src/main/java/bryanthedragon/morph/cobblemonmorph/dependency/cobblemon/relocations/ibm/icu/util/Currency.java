package com.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.impl.CacheBase;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUDebug;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.SimpleCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.SoftCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.StaticUnicodeSets;
import com.cobblemon.mod.relocations.ibm.icu.impl.TextTrieMap;
import com.cobblemon.mod.relocations.ibm.icu.text.CurrencyDisplayNames;
import com.cobblemon.mod.relocations.ibm.icu.text.CurrencyMetaInfo;
import java.io.ObjectStreamException;
import java.lang.ref.SoftReference;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.Map.Entry;

public class Currency extends MeasureUnit {
   private static final long serialVersionUID = -5839973855554750484L;
   private static final boolean DEBUG = ICUDebug.enabled("currency");
   private static ICUCache<ULocale, List<TextTrieMap<Currency.CurrencyStringInfo>>> CURRENCY_NAME_CACHE = new SimpleCache<>();
   public static final int SYMBOL_NAME = 0;
   public static final int LONG_NAME = 1;
   public static final int PLURAL_LONG_NAME = 2;
   public static final int NARROW_SYMBOL_NAME = 3;
   public static final int FORMAL_SYMBOL_NAME = 4;
   public static final int VARIANT_SYMBOL_NAME = 5;
   private static Currency.ServiceShim shim;
   private static final CacheBase<String, Currency, Void> regionCurrencyCache = new SoftCache<String, Currency, Void>() {
      protected Currency createInstance(String key, Void unused) {
         return Currency.loadCurrency(key);
      }
   };
   private static final ULocale UND = new ULocale("und");
   private static final String[] EMPTY_STRING_ARRAY = new String[0];
   private static final int[] POW10 = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
   private static SoftReference<List<String>> ALL_TENDER_CODES;
   private static SoftReference<Set<String>> ALL_CODES_AS_SET;
   private final String isoCode;

   private static Currency.ServiceShim getShim() {
      if (shim == null) {
         try {
            Class<?> cls = Class.forName("com.cobblemon.mod.relocations.ibm.icu.util.CurrencyServiceShim");
            shim = (Currency.ServiceShim)cls.newInstance();
         } catch (Exception var1) {
            if (DEBUG) {
               var1.printStackTrace();
            }

            throw new RuntimeException(var1.getMessage());
         }
      }

      return shim;
   }

   public static Currency getInstance(Locale locale) {
      return getInstance(ULocale.forLocale(locale));
   }

   public static Currency getInstance(ULocale locale) {
      String currency = locale.getKeywordValue("currency");
      if (currency != null) {
         return getInstance(currency);
      } else {
         return shim == null ? createCurrency(locale) : shim.createInstance(locale);
      }
   }

   public static String[] getAvailableCurrencyCodes(ULocale loc, Date d) {
      String region = ULocale.getRegionForSupplementalData(loc, false);
      CurrencyMetaInfo.CurrencyFilter filter = CurrencyMetaInfo.CurrencyFilter.onDate(d).withRegion(region);
      List<String> list = getTenderCurrencies(filter);
      return list.isEmpty() ? null : list.toArray(new String[list.size()]);
   }

   public static String[] getAvailableCurrencyCodes(Locale loc, Date d) {
      return getAvailableCurrencyCodes(ULocale.forLocale(loc), d);
   }

   public static Set<Currency> getAvailableCurrencies() {
      CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
      List<String> list = info.currencies(CurrencyMetaInfo.CurrencyFilter.all());
      HashSet<Currency> resultSet = new HashSet<>(list.size());

      for (String code : list) {
         resultSet.add(getInstance(code));
      }

      return resultSet;
   }

   static Currency createCurrency(ULocale loc) {
      String key = ULocale.getRegionForSupplementalData(loc, false);
      return regionCurrencyCache.getInstance(key, null);
   }

   private static Currency loadCurrency(String key) {
      CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
      List<String> list = info.currencies(CurrencyMetaInfo.CurrencyFilter.onRegion(key));
      if (!list.isEmpty()) {
         String code = list.get(0);
         return getInstance(code);
      } else {
         return null;
      }
   }

   public static Currency getInstance(String theISOCode) {
      if (theISOCode == null) {
         throw new NullPointerException("The input currency code is null.");
      } else if (!isAlpha3Code(theISOCode)) {
         throw new IllegalArgumentException("The input currency code is not 3-letter alphabetic code.");
      } else {
         return (Currency)MeasureUnit.internalGetInstance("currency", theISOCode.toUpperCase(Locale.ENGLISH));
      }
   }

   private static boolean isAlpha3Code(String code) {
      if (code.length() != 3) {
         return false;
      } else {
         for (int i = 0; i < 3; i++) {
            char ch = code.charAt(i);
            if (ch < 'A' || ch > 'Z' && ch < 'a' || ch > 'z') {
               return false;
            }
         }

         return true;
      }
   }

   public static Currency fromJavaCurrency(java.util.Currency currency) {
      return getInstance(currency.getCurrencyCode());
   }

   public java.util.Currency toJavaCurrency() {
      return java.util.Currency.getInstance(this.getCurrencyCode());
   }

   public static Object registerInstance(Currency currency, ULocale locale) {
      return getShim().registerInstance(currency, locale);
   }

   public static boolean unregister(Object registryKey) {
      if (registryKey == null) {
         throw new IllegalArgumentException("registryKey must not be null");
      } else {
         return shim == null ? false : shim.unregister(registryKey);
      }
   }

   public static Locale[] getAvailableLocales() {
      return shim == null ? ICUResourceBundle.getAvailableLocales() : shim.getAvailableLocales();
   }

   public static ULocale[] getAvailableULocales() {
      return shim == null ? ICUResourceBundle.getAvailableULocales() : shim.getAvailableULocales();
   }

   public static final String[] getKeywordValuesForLocale(String key, ULocale locale, boolean commonlyUsed) {
      if (!"currency".equals(key)) {
         return EMPTY_STRING_ARRAY;
      } else if (!commonlyUsed) {
         return getAllTenderCurrencies().toArray(new String[0]);
      } else if (UND.equals(locale)) {
         return EMPTY_STRING_ARRAY;
      } else {
         String prefRegion = ULocale.getRegionForSupplementalData(locale, true);
         CurrencyMetaInfo.CurrencyFilter filter = CurrencyMetaInfo.CurrencyFilter.now().withRegion(prefRegion);
         List<String> result = getTenderCurrencies(filter);
         return result.size() == 0 ? EMPTY_STRING_ARRAY : result.toArray(new String[result.size()]);
      }
   }

   public String getCurrencyCode() {
      return this.subType;
   }

   public int getNumericCode() {
      int result = 0;

      try {
         UResourceBundle bundle = UResourceBundle.getBundleInstance(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "currencyNumericCodes", ICUResourceBundle.ICU_DATA_CLASS_LOADER
         );
         UResourceBundle codeMap = bundle.get("codeMap");
         UResourceBundle numCode = codeMap.get(this.subType);
         result = numCode.getInt();
      } catch (MissingResourceException var5) {
      }

      return result;
   }

   public String getSymbol() {
      return this.getSymbol(ULocale.getDefault(ULocale.Category.DISPLAY));
   }

   public String getSymbol(Locale loc) {
      return this.getSymbol(ULocale.forLocale(loc));
   }

   public String getSymbol(ULocale uloc) {
      return this.getName(uloc, 0, null);
   }

   public String getName(Locale locale, int nameStyle, boolean[] isChoiceFormat) {
      return this.getName(ULocale.forLocale(locale), nameStyle, isChoiceFormat);
   }

   public String getName(ULocale locale, int nameStyle, boolean[] isChoiceFormat) {
      if (isChoiceFormat != null) {
         isChoiceFormat[0] = false;
      }

      CurrencyDisplayNames names = CurrencyDisplayNames.getInstance(locale);
      switch (nameStyle) {
         case 0:
            return names.getSymbol(this.subType);
         case 1:
            return names.getName(this.subType);
         case 2:
         default:
            throw new IllegalArgumentException("bad name style: " + nameStyle);
         case 3:
            return names.getNarrowSymbol(this.subType);
         case 4:
            return names.getFormalSymbol(this.subType);
         case 5:
            return names.getVariantSymbol(this.subType);
      }
   }

   public String getName(Locale locale, int nameStyle, String pluralCount, boolean[] isChoiceFormat) {
      return this.getName(ULocale.forLocale(locale), nameStyle, pluralCount, isChoiceFormat);
   }

   public String getName(ULocale locale, int nameStyle, String pluralCount, boolean[] isChoiceFormat) {
      if (nameStyle != 2) {
         return this.getName(locale, nameStyle, isChoiceFormat);
      } else {
         if (isChoiceFormat != null) {
            isChoiceFormat[0] = false;
         }

         CurrencyDisplayNames names = CurrencyDisplayNames.getInstance(locale);
         return names.getPluralName(this.subType, pluralCount);
      }
   }

   public String getDisplayName() {
      return this.getName(Locale.getDefault(), 1, null);
   }

   public String getDisplayName(Locale locale) {
      return this.getName(locale, 1, null);
   }

   @Deprecated
   public static String parse(ULocale locale, String text, int type, ParsePosition pos) {
      List<TextTrieMap<Currency.CurrencyStringInfo>> currencyTrieVec = getCurrencyTrieVec(locale);
      int maxLength = 0;
      String isoResult = null;
      TextTrieMap<Currency.CurrencyStringInfo> currencyNameTrie = currencyTrieVec.get(1);
      Currency.CurrencyNameResultHandler handler = new Currency.CurrencyNameResultHandler();
      currencyNameTrie.find(text, pos.getIndex(), handler);
      isoResult = handler.getBestCurrencyISOCode();
      maxLength = handler.getBestMatchLength();
      if (type != 1) {
         TextTrieMap<Currency.CurrencyStringInfo> currencySymbolTrie = currencyTrieVec.get(0);
         handler = new Currency.CurrencyNameResultHandler();
         currencySymbolTrie.find(text, pos.getIndex(), handler);
         if (handler.getBestMatchLength() > maxLength) {
            isoResult = handler.getBestCurrencyISOCode();
            maxLength = handler.getBestMatchLength();
         }
      }

      int start = pos.getIndex();
      pos.setIndex(start + maxLength);
      return isoResult;
   }

   @Deprecated
   public static TextTrieMap<Currency.CurrencyStringInfo> getParsingTrie(ULocale locale, int type) {
      List<TextTrieMap<Currency.CurrencyStringInfo>> currencyTrieVec = getCurrencyTrieVec(locale);
      return type == 1 ? currencyTrieVec.get(1) : currencyTrieVec.get(0);
   }

   private static List<TextTrieMap<Currency.CurrencyStringInfo>> getCurrencyTrieVec(ULocale locale) {
      List<TextTrieMap<Currency.CurrencyStringInfo>> currencyTrieVec = CURRENCY_NAME_CACHE.get(locale);
      if (currencyTrieVec == null) {
         TextTrieMap<Currency.CurrencyStringInfo> currencyNameTrie = new TextTrieMap<>(true);
         TextTrieMap<Currency.CurrencyStringInfo> currencySymbolTrie = new TextTrieMap<>(false);
         currencyTrieVec = new ArrayList<>();
         currencyTrieVec.add(currencySymbolTrie);
         currencyTrieVec.add(currencyNameTrie);
         setupCurrencyTrieVec(locale, currencyTrieVec);
         CURRENCY_NAME_CACHE.put(locale, currencyTrieVec);
      }

      return currencyTrieVec;
   }

   private static void setupCurrencyTrieVec(ULocale locale, List<TextTrieMap<Currency.CurrencyStringInfo>> trieVec) {
      TextTrieMap<Currency.CurrencyStringInfo> symTrie = trieVec.get(0);
      TextTrieMap<Currency.CurrencyStringInfo> trie = trieVec.get(1);
      CurrencyDisplayNames names = CurrencyDisplayNames.getInstance(locale);

      for (Entry<String, String> e : names.symbolMap().entrySet()) {
         String symbol = e.getKey();
         String isoCode = e.getValue();
         StaticUnicodeSets.Key key = StaticUnicodeSets.chooseCurrency(symbol);
         Currency.CurrencyStringInfo value = new Currency.CurrencyStringInfo(isoCode, symbol);
         if (key != null) {
            for (String equivalentSymbol : StaticUnicodeSets.get(key)) {
               symTrie.put(equivalentSymbol, value);
            }
         } else {
            symTrie.put(symbol, value);
         }
      }

      for (Entry<String, String> ex : names.nameMap().entrySet()) {
         String name = ex.getKey();
         String isoCode = ex.getValue();
         trie.put(name, new Currency.CurrencyStringInfo(isoCode, name));
      }
   }

   public int getDefaultFractionDigits() {
      return this.getDefaultFractionDigits(Currency.CurrencyUsage.STANDARD);
   }

   public int getDefaultFractionDigits(Currency.CurrencyUsage Usage) {
      CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
      CurrencyMetaInfo.CurrencyDigits digits = info.currencyDigits(this.subType, Usage);
      return digits.fractionDigits;
   }

   public double getRoundingIncrement() {
      return this.getRoundingIncrement(Currency.CurrencyUsage.STANDARD);
   }

   public double getRoundingIncrement(Currency.CurrencyUsage Usage) {
      CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
      CurrencyMetaInfo.CurrencyDigits digits = info.currencyDigits(this.subType, Usage);
      int data1 = digits.roundingIncrement;
      if (data1 == 0) {
         return 0.0;
      } else {
         int data0 = digits.fractionDigits;
         return data0 >= 0 && data0 < POW10.length ? (double)data1 / POW10[data0] : 0.0;
      }
   }

   @Override
   public String toString() {
      return this.subType;
   }

   protected Currency(String theISOCode) {
      super("currency", theISOCode);
      this.isoCode = theISOCode;
   }

   private static synchronized List<String> getAllTenderCurrencies() {
      List<String> all = ALL_TENDER_CODES == null ? null : ALL_TENDER_CODES.get();
      if (all == null) {
         CurrencyMetaInfo.CurrencyFilter filter = CurrencyMetaInfo.CurrencyFilter.all();
         all = Collections.unmodifiableList(getTenderCurrencies(filter));
         ALL_TENDER_CODES = new SoftReference<>(all);
      }

      return all;
   }

   private static synchronized Set<String> getAllCurrenciesAsSet() {
      Set<String> all = ALL_CODES_AS_SET == null ? null : ALL_CODES_AS_SET.get();
      if (all == null) {
         CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
         all = Collections.unmodifiableSet(new HashSet<>(info.currencies(CurrencyMetaInfo.CurrencyFilter.all())));
         ALL_CODES_AS_SET = new SoftReference<>(all);
      }

      return all;
   }

   public static boolean isAvailable(String code, Date from, Date to) {
      if (!isAlpha3Code(code)) {
         return false;
      } else if (from != null && to != null && from.after(to)) {
         throw new IllegalArgumentException("To is before from");
      } else {
         code = code.toUpperCase(Locale.ENGLISH);
         boolean isKnown = getAllCurrenciesAsSet().contains(code);
         if (!isKnown) {
            return false;
         } else if (from == null && to == null) {
            return true;
         } else {
            CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
            List<String> allActive = info.currencies(CurrencyMetaInfo.CurrencyFilter.onDateRange(from, to).withCurrency(code));
            return allActive.contains(code);
         }
      }
   }

   private static List<String> getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter filter) {
      CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
      return info.currencies(filter.withTender());
   }

   private Object writeReplace() throws ObjectStreamException {
      return new MeasureUnit.MeasureUnitProxy(this.type, this.subType);
   }

   private Object readResolve() throws ObjectStreamException {
      return getInstance(this.isoCode);
   }

   private static class CurrencyNameResultHandler implements TextTrieMap.ResultHandler<Currency.CurrencyStringInfo> {
      private int bestMatchLength;
      private String bestCurrencyISOCode;

      private CurrencyNameResultHandler() {
      }

      @Override
      public boolean handlePrefixMatch(int matchLength, Iterator<Currency.CurrencyStringInfo> values) {
         if (values.hasNext()) {
            this.bestCurrencyISOCode = values.next().getISOCode();
            this.bestMatchLength = matchLength;
         }

         return true;
      }

      public String getBestCurrencyISOCode() {
         return this.bestCurrencyISOCode;
      }

      public int getBestMatchLength() {
         return this.bestMatchLength;
      }
   }

   @Deprecated
   public static final class CurrencyStringInfo {
      private String isoCode;
      private String currencyString;

      @Deprecated
      public CurrencyStringInfo(String isoCode, String currencyString) {
         this.isoCode = isoCode;
         this.currencyString = currencyString;
      }

      @Deprecated
      public String getISOCode() {
         return this.isoCode;
      }

      @Deprecated
      public String getCurrencyString() {
         return this.currencyString;
      }
   }

   public static enum CurrencyUsage {
      STANDARD,
      CASH;
   }

   abstract static class ServiceShim {
      abstract ULocale[] getAvailableULocales();

      abstract Locale[] getAvailableLocales();

      abstract Currency createInstance(ULocale var1);

      abstract Object registerInstance(Currency var1, ULocale var2);

      abstract boolean unregister(Object var1);
   }
}
