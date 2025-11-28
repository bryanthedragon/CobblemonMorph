package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.util.ICUException;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

public class ICUCurrencyDisplayInfoProvider implements CurrencyData.CurrencyDisplayInfoProvider {
   private volatile ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo currencyDisplayInfoCache = null;

   @Override
   public CurrencyData.CurrencyDisplayInfo getInstance(ULocale locale, boolean withFallback) {
      if (locale == null) {
         locale = ULocale.ROOT;
      }

      ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo instance = this.currencyDisplayInfoCache;
      if (instance == null || !instance.locale.equals(locale) || instance.fallback != withFallback) {
         ICUResourceBundle rb;
         if (withFallback) {
            rb = ICUResourceBundle.getBundleInstance(
               "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/curr", locale, ICUResourceBundle.OpenType.LOCALE_DEFAULT_ROOT
            );
         } else {
            try {
               rb = ICUResourceBundle.getBundleInstance(
                  "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/curr", locale, ICUResourceBundle.OpenType.LOCALE_ONLY
               );
            } catch (MissingResourceException var6) {
               return null;
            }
         }

         instance = new ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo(locale, rb, withFallback);
         this.currencyDisplayInfoCache = instance;
      }

      return instance;
   }

   @Override
   public boolean hasData() {
      return true;
   }

   static class ICUCurrencyDisplayInfo extends CurrencyData.CurrencyDisplayInfo {
      final ULocale locale;
      final boolean fallback;
      private final ICUResourceBundle rb;
      private volatile ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.FormattingData formattingDataCache = null;
      private volatile ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.VariantSymbol variantSymbolCache = null;
      private volatile String[] pluralsDataCache = null;
      private volatile SoftReference<ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.ParsingData> parsingDataCache = new SoftReference<>(null);
      private volatile Map<String, String> unitPatternsCache = null;
      private volatile CurrencyData.CurrencySpacingInfo spacingInfoCache = null;

      public ICUCurrencyDisplayInfo(ULocale locale, ICUResourceBundle rb, boolean fallback) {
         this.locale = locale;
         this.fallback = fallback;
         this.rb = rb;
      }

      @Override
      public ULocale getULocale() {
         return this.rb.getULocale();
      }

      @Override
      public String getName(String isoCode) {
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.FormattingData formattingData = this.fetchFormattingData(isoCode);
         return formattingData.displayName == null && this.fallback ? isoCode : formattingData.displayName;
      }

      @Override
      public String getSymbol(String isoCode) {
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.FormattingData formattingData = this.fetchFormattingData(isoCode);
         return formattingData.symbol == null && this.fallback ? isoCode : formattingData.symbol;
      }

      @Override
      public String getNarrowSymbol(String isoCode) {
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.VariantSymbol variantSymbol = this.fetchVariantSymbol(isoCode, "narrow");
         return variantSymbol.symbol == null && this.fallback ? this.getSymbol(isoCode) : variantSymbol.symbol;
      }

      @Override
      public String getFormalSymbol(String isoCode) {
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.VariantSymbol variantSymbol = this.fetchVariantSymbol(isoCode, "formal");
         return variantSymbol.symbol == null && this.fallback ? this.getSymbol(isoCode) : variantSymbol.symbol;
      }

      @Override
      public String getVariantSymbol(String isoCode) {
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.VariantSymbol variantSymbol = this.fetchVariantSymbol(isoCode, "variant");
         return variantSymbol.symbol == null && this.fallback ? this.getSymbol(isoCode) : variantSymbol.symbol;
      }

      @Override
      public String getPluralName(String isoCode, String pluralKey) {
         StandardPlural plural = StandardPlural.orNullFromString(pluralKey);
         String[] pluralsData = this.fetchPluralsData(isoCode);
         String result = null;
         if (plural != null) {
            result = pluralsData[1 + plural.ordinal()];
         }

         if (result == null && this.fallback) {
            result = pluralsData[1 + StandardPlural.OTHER.ordinal()];
         }

         if (result == null && this.fallback) {
            ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.FormattingData formattingData = this.fetchFormattingData(isoCode);
            result = formattingData.displayName;
         }

         if (result == null && this.fallback) {
            result = isoCode;
         }

         return result;
      }

      @Override
      public Map<String, String> symbolMap() {
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.ParsingData parsingData = this.fetchParsingData();
         return parsingData.symbolToIsoCode;
      }

      @Override
      public Map<String, String> nameMap() {
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.ParsingData parsingData = this.fetchParsingData();
         return parsingData.nameToIsoCode;
      }

      @Override
      public Map<String, String> getUnitPatterns() {
         return this.fetchUnitPatterns();
      }

      @Override
      public CurrencyData.CurrencyFormatInfo getFormatInfo(String isoCode) {
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.FormattingData formattingData = this.fetchFormattingData(isoCode);
         return formattingData.formatInfo;
      }

      @Override
      public CurrencyData.CurrencySpacingInfo getSpacingInfo() {
         CurrencyData.CurrencySpacingInfo spacingInfo = this.fetchSpacingInfo();
         return (!spacingInfo.hasBeforeCurrency || !spacingInfo.hasAfterCurrency) && this.fallback ? CurrencyData.CurrencySpacingInfo.DEFAULT : spacingInfo;
      }

      ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.FormattingData fetchFormattingData(String isoCode) {
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.FormattingData result = this.formattingDataCache;
         if (result == null || !result.isoCode.equals(isoCode)) {
            result = new ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.FormattingData(isoCode);
            ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink sink = new ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink(
               !this.fallback, ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable.CURRENCIES
            );
            sink.formattingData = result;
            this.rb.getAllItemsWithFallbackNoFail("Currencies/" + isoCode, sink);
            this.formattingDataCache = result;
         }

         return result;
      }

      ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.VariantSymbol fetchVariantSymbol(String isoCode, String variant) {
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.VariantSymbol result = this.variantSymbolCache;
         if (result == null || !result.isoCode.equals(isoCode) || !result.variant.equals(variant)) {
            result = new ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.VariantSymbol(isoCode, variant);
            ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink sink = new ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink(
               !this.fallback, ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable.CURRENCY_VARIANT
            );
            sink.variantSymbol = result;
            this.rb.getAllItemsWithFallbackNoFail("Currencies%" + variant + "/" + isoCode, sink);
            this.variantSymbolCache = result;
         }

         return result;
      }

      String[] fetchPluralsData(String isoCode) {
         String[] result = this.pluralsDataCache;
         if (result == null || !result[0].equals(isoCode)) {
            result = new String[1 + StandardPlural.COUNT];
            result[0] = isoCode;
            ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink sink = new ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink(
               !this.fallback, ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable.CURRENCY_PLURALS
            );
            sink.pluralsData = result;
            this.rb.getAllItemsWithFallbackNoFail("CurrencyPlurals/" + isoCode, sink);
            this.pluralsDataCache = result;
         }

         return result;
      }

      ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.ParsingData fetchParsingData() {
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.ParsingData result = this.parsingDataCache.get();
         if (result == null) {
            result = new ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.ParsingData();
            ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink sink = new ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink(
               !this.fallback, ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable.TOP
            );
            sink.parsingData = result;
            this.rb.getAllItemsWithFallback("", sink);
            this.parsingDataCache = new SoftReference<>(result);
         }

         return result;
      }

      Map<String, String> fetchUnitPatterns() {
         Map<String, String> result = this.unitPatternsCache;
         if (result == null) {
            result = new HashMap<>();
            ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink sink = new ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink(
               !this.fallback, ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable.CURRENCY_UNIT_PATTERNS
            );
            sink.unitPatterns = result;
            this.rb.getAllItemsWithFallback("CurrencyUnitPatterns", sink);
            this.unitPatternsCache = result;
         }

         return result;
      }

      CurrencyData.CurrencySpacingInfo fetchSpacingInfo() {
         CurrencyData.CurrencySpacingInfo result = this.spacingInfoCache;
         if (result == null) {
            result = new CurrencyData.CurrencySpacingInfo();
            ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink sink = new ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink(
               !this.fallback, ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable.CURRENCY_SPACING
            );
            sink.spacingInfo = result;
            this.rb.getAllItemsWithFallback("currencySpacing", sink);
            this.spacingInfoCache = result;
         }

         return result;
      }

      private static final class CurrencySink extends UResource.Sink {
         final boolean noRoot;
         final ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable entrypointTable;
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.FormattingData formattingData = null;
         String[] pluralsData = null;
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.ParsingData parsingData = null;
         Map<String, String> unitPatterns = null;
         CurrencyData.CurrencySpacingInfo spacingInfo = null;
         ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.VariantSymbol variantSymbol = null;

         CurrencySink(boolean noRoot, ICUCurrencyDisplayInfoProvider.ICUCurrencyDisplayInfo.CurrencySink.EntrypointTable entrypointTable) {
            this.noRoot = noRoot;
            this.entrypointTable = entrypointTable;
         }

         @Override
         public void put(UResource.Key key, UResource.Value value, boolean isRoot) {
            if (!this.noRoot || !isRoot) {
               switch (this.entrypointTable) {
                  case TOP:
                     this.consumeTopTable(key, value);
                     break;
                  case CURRENCIES:
                     this.consumeCurrenciesEntry(key, value);
                     break;
                  case CURRENCY_PLURALS:
                     this.consumeCurrencyPluralsEntry(key, value);
                     break;
                  case CURRENCY_VARIANT:
                     this.consumeCurrenciesVariantEntry(key, value);
                     break;
                  case CURRENCY_SPACING:
                     this.consumeCurrencySpacingTable(key, value);
                     break;
                  case CURRENCY_UNIT_PATTERNS:
                     this.consumeCurrencyUnitPatternsTable(key, value);
               }
            }
         }

         private void consumeTopTable(UResource.Key key, UResource.Value value) {
            UResource.Table table = value.getTable();

            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
               if (key.contentEquals("Currencies")) {
                  this.consumeCurrenciesTable(key, value);
               } else if (key.contentEquals("Currencies%variant")) {
                  this.consumeCurrenciesVariantTable(key, value);
               } else if (key.contentEquals("CurrencyPlurals")) {
                  this.consumeCurrencyPluralsTable(key, value);
               }
            }
         }

         void consumeCurrenciesTable(UResource.Key key, UResource.Value value) {
            assert this.parsingData != null;

            UResource.Table table = value.getTable();

            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
               String isoCode = key.toString();
               if (value.getType() != 8) {
                  throw new ICUException("Unexpected data type in Currencies table for " + isoCode);
               }

               UResource.Array array = value.getArray();
               this.parsingData.symbolToIsoCode.put(isoCode, isoCode);
               array.getValue(0, value);
               this.parsingData.symbolToIsoCode.put(value.getString(), isoCode);
               array.getValue(1, value);
               this.parsingData.nameToIsoCode.put(value.getString(), isoCode);
            }
         }

         void consumeCurrenciesEntry(UResource.Key key, UResource.Value value) {
            assert this.formattingData != null;

            String isoCode = key.toString();
            if (value.getType() != 8) {
               throw new ICUException("Unexpected data type in Currencies table for " + isoCode);
            } else {
               UResource.Array array = value.getArray();
               if (this.formattingData.symbol == null) {
                  array.getValue(0, value);
                  this.formattingData.symbol = value.getString();
               }

               if (this.formattingData.displayName == null) {
                  array.getValue(1, value);
                  this.formattingData.displayName = value.getString();
               }

               if (array.getSize() > 2 && this.formattingData.formatInfo == null) {
                  array.getValue(2, value);
                  UResource.Array formatArray = value.getArray();
                  formatArray.getValue(0, value);
                  String formatPattern = value.getString();
                  formatArray.getValue(1, value);
                  String decimalSeparator = value.getString();
                  formatArray.getValue(2, value);
                  String groupingSeparator = value.getString();
                  this.formattingData.formatInfo = new CurrencyData.CurrencyFormatInfo(isoCode, formatPattern, decimalSeparator, groupingSeparator);
               }
            }
         }

         void consumeCurrenciesVariantEntry(UResource.Key key, UResource.Value value) {
            assert this.variantSymbol != null;

            if (this.variantSymbol.symbol == null) {
               this.variantSymbol.symbol = value.getString();
            }
         }

         void consumeCurrenciesVariantTable(UResource.Key key, UResource.Value value) {
            assert this.parsingData != null;

            UResource.Table table = value.getTable();

            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
               String isoCode = key.toString();
               this.parsingData.symbolToIsoCode.put(value.getString(), isoCode);
            }
         }

         void consumeCurrencyPluralsTable(UResource.Key key, UResource.Value value) {
            assert this.parsingData != null;

            UResource.Table table = value.getTable();

            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
               String isoCode = key.toString();
               UResource.Table pluralsTable = value.getTable();

               for (int j = 0; pluralsTable.getKeyAndValue(j, key, value); j++) {
                  StandardPlural plural = StandardPlural.orNullFromString(key.toString());
                  if (plural == null) {
                     throw new ICUException("Could not make StandardPlural from keyword " + key);
                  }

                  this.parsingData.nameToIsoCode.put(value.getString(), isoCode);
               }
            }
         }

         void consumeCurrencyPluralsEntry(UResource.Key key, UResource.Value value) {
            assert this.pluralsData != null;

            UResource.Table pluralsTable = value.getTable();

            for (int j = 0; pluralsTable.getKeyAndValue(j, key, value); j++) {
               StandardPlural plural = StandardPlural.orNullFromString(key.toString());
               if (plural == null) {
                  throw new ICUException("Could not make StandardPlural from keyword " + key);
               }

               if (this.pluralsData[1 + plural.ordinal()] == null) {
                  this.pluralsData[1 + plural.ordinal()] = value.getString();
               }
            }
         }

         void consumeCurrencySpacingTable(UResource.Key key, UResource.Value value) {
            assert this.spacingInfo != null;

            UResource.Table spacingTypesTable = value.getTable();

            for (int i = 0; spacingTypesTable.getKeyAndValue(i, key, value); i++) {
               CurrencyData.CurrencySpacingInfo.SpacingType type;
               if (key.contentEquals("beforeCurrency")) {
                  type = CurrencyData.CurrencySpacingInfo.SpacingType.BEFORE;
                  this.spacingInfo.hasBeforeCurrency = true;
               } else {
                  if (!key.contentEquals("afterCurrency")) {
                     continue;
                  }

                  type = CurrencyData.CurrencySpacingInfo.SpacingType.AFTER;
                  this.spacingInfo.hasAfterCurrency = true;
               }

               UResource.Table patternsTable = value.getTable();

               for (int j = 0; patternsTable.getKeyAndValue(j, key, value); j++) {
                  CurrencyData.CurrencySpacingInfo.SpacingPattern pattern;
                  if (key.contentEquals("currencyMatch")) {
                     pattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.CURRENCY_MATCH;
                  } else if (key.contentEquals("surroundingMatch")) {
                     pattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.SURROUNDING_MATCH;
                  } else {
                     if (!key.contentEquals("insertBetween")) {
                        continue;
                     }

                     pattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.INSERT_BETWEEN;
                  }

                  this.spacingInfo.setSymbolIfNull(type, pattern, value.getString());
               }
            }
         }

         void consumeCurrencyUnitPatternsTable(UResource.Key key, UResource.Value value) {
            assert this.unitPatterns != null;

            UResource.Table table = value.getTable();

            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
               String pluralKeyword = key.toString();
               if (this.unitPatterns.get(pluralKeyword) == null) {
                  this.unitPatterns.put(pluralKeyword, value.getString());
               }
            }
         }

         static enum EntrypointTable {
            TOP,
            CURRENCIES,
            CURRENCY_PLURALS,
            CURRENCY_VARIANT,
            CURRENCY_SPACING,
            CURRENCY_UNIT_PATTERNS;
         }
      }

      static class FormattingData {
         final String isoCode;
         String displayName = null;
         String symbol = null;
         CurrencyData.CurrencyFormatInfo formatInfo = null;

         FormattingData(String isoCode) {
            this.isoCode = isoCode;
         }
      }

      static class ParsingData {
         Map<String, String> symbolToIsoCode = new HashMap<>();
         Map<String, String> nameToIsoCode = new HashMap<>();
      }

      static class VariantSymbol {
         final String isoCode;
         final String variant;
         String symbol = null;

         VariantSymbol(String isoCode, String variant) {
            this.isoCode = isoCode;
            this.variant = variant;
         }
      }
   }
}
