package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.text.CurrencyDisplayNames;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.util.Collections;
import java.util.Map;

public class CurrencyData {
   public static final CurrencyData.CurrencyDisplayInfoProvider provider;

   private CurrencyData() {
   }

   static {
      CurrencyData.CurrencyDisplayInfoProvider temp = null;

      try {
         Class<?> clzz = Class.forName("com.cobblemon.mod.relocations.ibm.icu.impl.ICUCurrencyDisplayInfoProvider");
         temp = (CurrencyData.CurrencyDisplayInfoProvider)clzz.newInstance();
      } catch (Throwable var2) {
         temp = new CurrencyData.CurrencyDisplayInfoProvider() {
            @Override
            public CurrencyData.CurrencyDisplayInfo getInstance(ULocale locale, boolean withFallback) {
               return CurrencyData.DefaultInfo.getWithFallback(withFallback);
            }

            @Override
            public boolean hasData() {
               return false;
            }
         };
      }

      provider = temp;
   }

   public abstract static class CurrencyDisplayInfo extends CurrencyDisplayNames {
      public abstract Map<String, String> getUnitPatterns();

      public abstract CurrencyData.CurrencyFormatInfo getFormatInfo(String var1);

      public abstract CurrencyData.CurrencySpacingInfo getSpacingInfo();
   }

   public interface CurrencyDisplayInfoProvider {
      CurrencyData.CurrencyDisplayInfo getInstance(ULocale var1, boolean var2);

      boolean hasData();
   }

   public static final class CurrencyFormatInfo {
      public final String isoCode;
      public final String currencyPattern;
      public final String monetaryDecimalSeparator;
      public final String monetaryGroupingSeparator;

      public CurrencyFormatInfo(String isoCode, String currencyPattern, String monetarySeparator, String monetaryGroupingSeparator) {
         this.isoCode = isoCode;
         this.currencyPattern = currencyPattern;
         this.monetaryDecimalSeparator = monetarySeparator;
         this.monetaryGroupingSeparator = monetaryGroupingSeparator;
      }
   }

   public static final class CurrencySpacingInfo {
      private final String[][] symbols = new String[CurrencyData.CurrencySpacingInfo.SpacingType.COUNT.ordinal()][CurrencyData.CurrencySpacingInfo.SpacingPattern.COUNT
         .ordinal()];
      public boolean hasBeforeCurrency = false;
      public boolean hasAfterCurrency = false;
      private static final String DEFAULT_CUR_MATCH = "[:letter:]";
      private static final String DEFAULT_CTX_MATCH = "[:digit:]";
      private static final String DEFAULT_INSERT = " ";
      public static final CurrencyData.CurrencySpacingInfo DEFAULT = new CurrencyData.CurrencySpacingInfo(
         "[:letter:]", "[:digit:]", " ", "[:letter:]", "[:digit:]", " "
      );

      public CurrencySpacingInfo() {
      }

      public CurrencySpacingInfo(String... strings) {
         assert strings.length == 6;

         int k = 0;

         for (int i = 0; i < CurrencyData.CurrencySpacingInfo.SpacingType.COUNT.ordinal(); i++) {
            for (int j = 0; j < CurrencyData.CurrencySpacingInfo.SpacingPattern.COUNT.ordinal(); j++) {
               this.symbols[i][j] = strings[k];
               k++;
            }
         }
      }

      public void setSymbolIfNull(CurrencyData.CurrencySpacingInfo.SpacingType type, CurrencyData.CurrencySpacingInfo.SpacingPattern pattern, String value) {
         int i = type.ordinal();
         int j = pattern.ordinal();
         if (this.symbols[i][j] == null) {
            this.symbols[i][j] = value;
         }
      }

      public String[] getBeforeSymbols() {
         return this.symbols[CurrencyData.CurrencySpacingInfo.SpacingType.BEFORE.ordinal()];
      }

      public String[] getAfterSymbols() {
         return this.symbols[CurrencyData.CurrencySpacingInfo.SpacingType.AFTER.ordinal()];
      }

      public static enum SpacingPattern {
         CURRENCY_MATCH(0),
         SURROUNDING_MATCH(1),
         INSERT_BETWEEN(2),
         COUNT;

         private SpacingPattern() {
         }

         private SpacingPattern(int value) {
            assert value == this.ordinal();
         }
      }

      public static enum SpacingType {
         BEFORE,
         AFTER,
         COUNT;
      }
   }

   public static class DefaultInfo extends CurrencyData.CurrencyDisplayInfo {
      private final boolean fallback;
      private static final CurrencyData.CurrencyDisplayInfo FALLBACK_INSTANCE = new CurrencyData.DefaultInfo(true);
      private static final CurrencyData.CurrencyDisplayInfo NO_FALLBACK_INSTANCE = new CurrencyData.DefaultInfo(false);

      private DefaultInfo(boolean fallback) {
         this.fallback = fallback;
      }

      public static final CurrencyData.CurrencyDisplayInfo getWithFallback(boolean fallback) {
         return fallback ? FALLBACK_INSTANCE : NO_FALLBACK_INSTANCE;
      }

      @Override
      public String getName(String isoCode) {
         return this.fallback ? isoCode : null;
      }

      @Override
      public String getPluralName(String isoCode, String pluralType) {
         return this.fallback ? isoCode : null;
      }

      @Override
      public String getSymbol(String isoCode) {
         return this.fallback ? isoCode : null;
      }

      @Override
      public String getNarrowSymbol(String isoCode) {
         return this.fallback ? isoCode : null;
      }

      @Override
      public String getFormalSymbol(String isoCode) {
         return this.fallback ? isoCode : null;
      }

      @Override
      public String getVariantSymbol(String isoCode) {
         return this.fallback ? isoCode : null;
      }

      @Override
      public Map<String, String> symbolMap() {
         return Collections.emptyMap();
      }

      @Override
      public Map<String, String> nameMap() {
         return Collections.emptyMap();
      }

      @Override
      public ULocale getULocale() {
         return ULocale.ROOT;
      }

      @Override
      public Map<String, String> getUnitPatterns() {
         return this.fallback ? Collections.emptyMap() : null;
      }

      @Override
      public CurrencyData.CurrencyFormatInfo getFormatInfo(String isoCode) {
         return null;
      }

      @Override
      public CurrencyData.CurrencySpacingInfo getSpacingInfo() {
         return this.fallback ? CurrencyData.CurrencySpacingInfo.DEFAULT : null;
      }
   }
}
