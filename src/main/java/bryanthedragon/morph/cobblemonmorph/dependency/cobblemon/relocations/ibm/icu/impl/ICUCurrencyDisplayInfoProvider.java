
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.impl.CurrencyData;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import com.cobblemon.mod.relocations.ibm.icu.impl.UResource;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUException;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

public class ICUCurrencyDisplayInfoProvider
implements CurrencyData.CurrencyDisplayInfoProvider {
    private volatile ICUCurrencyDisplayInfo currencyDisplayInfoCache = null;

    @Override
    public CurrencyData.CurrencyDisplayInfo getInstance(ULocale locale, boolean withFallback) {
        ICUCurrencyDisplayInfo instance;
        if (locale == null) {
            locale = ULocale.ROOT;
        }
        if ((instance = this.currencyDisplayInfoCache) == null || !instance.locale.equals(locale) || instance.fallback != withFallback) {
            ICUResourceBundle rb;
            if (withFallback) {
                rb = ICUResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/curr", locale, ICUResourceBundle.OpenType.LOCALE_DEFAULT_ROOT);
            } else {
                try {
                    rb = ICUResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/curr", locale, ICUResourceBundle.OpenType.LOCALE_ONLY);
                }
                catch (MissingResourceException e) {
                    return null;
                }
            }
            this.currencyDisplayInfoCache = instance = new ICUCurrencyDisplayInfo(locale, rb, withFallback);
        }
        return instance;
    }

    @Override
    public boolean hasData() {
        return true;
    }

    static class ICUCurrencyDisplayInfo
    extends CurrencyData.CurrencyDisplayInfo {
        final ULocale locale;
        final boolean fallback;
        private final ICUResourceBundle rb;
        private volatile FormattingData formattingDataCache = null;
        private volatile VariantSymbol variantSymbolCache = null;
        private volatile String[] pluralsDataCache = null;
        private volatile SoftReference<ParsingData> parsingDataCache = new SoftReference<Object>(null);
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
            FormattingData formattingData = this.fetchFormattingData(isoCode);
            if (formattingData.displayName == null && this.fallback) {
                return isoCode;
            }
            return formattingData.displayName;
        }

        @Override
        public String getSymbol(String isoCode) {
            FormattingData formattingData = this.fetchFormattingData(isoCode);
            if (formattingData.symbol == null && this.fallback) {
                return isoCode;
            }
            return formattingData.symbol;
        }

        @Override
        public String getNarrowSymbol(String isoCode) {
            VariantSymbol variantSymbol = this.fetchVariantSymbol(isoCode, "narrow");
            if (variantSymbol.symbol == null && this.fallback) {
                return this.getSymbol(isoCode);
            }
            return variantSymbol.symbol;
        }

        @Override
        public String getFormalSymbol(String isoCode) {
            VariantSymbol variantSymbol = this.fetchVariantSymbol(isoCode, "formal");
            if (variantSymbol.symbol == null && this.fallback) {
                return this.getSymbol(isoCode);
            }
            return variantSymbol.symbol;
        }

        @Override
        public String getVariantSymbol(String isoCode) {
            VariantSymbol variantSymbol = this.fetchVariantSymbol(isoCode, "variant");
            if (variantSymbol.symbol == null && this.fallback) {
                return this.getSymbol(isoCode);
            }
            return variantSymbol.symbol;
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
                FormattingData formattingData = this.fetchFormattingData(isoCode);
                result = formattingData.displayName;
            }
            if (result == null && this.fallback) {
                result = isoCode;
            }
            return result;
        }

        @Override
        public Map<String, String> symbolMap() {
            ParsingData parsingData = this.fetchParsingData();
            return parsingData.symbolToIsoCode;
        }

        @Override
        public Map<String, String> nameMap() {
            ParsingData parsingData = this.fetchParsingData();
            return parsingData.nameToIsoCode;
        }

        @Override
        public Map<String, String> getUnitPatterns() {
            Map<String, String> unitPatterns = this.fetchUnitPatterns();
            return unitPatterns;
        }

        @Override
        public CurrencyData.CurrencyFormatInfo getFormatInfo(String isoCode) {
            FormattingData formattingData = this.fetchFormattingData(isoCode);
            return formattingData.formatInfo;
        }

        @Override
        public CurrencyData.CurrencySpacingInfo getSpacingInfo() {
            CurrencyData.CurrencySpacingInfo spacingInfo = this.fetchSpacingInfo();
            if (!(spacingInfo.hasBeforeCurrency && spacingInfo.hasAfterCurrency || !this.fallback)) {
                return CurrencyData.CurrencySpacingInfo.DEFAULT;
            }
            return spacingInfo;
        }

        FormattingData fetchFormattingData(String isoCode) {
            FormattingData result = this.formattingDataCache;
            if (result == null || !result.isoCode.equals(isoCode)) {
                result = new FormattingData(isoCode);
                CurrencySink sink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCIES);
                sink.formattingData = result;
                this.rb.getAllItemsWithFallbackNoFail("Currencies/" + isoCode, sink);
                this.formattingDataCache = result;
            }
            return result;
        }

        VariantSymbol fetchVariantSymbol(String isoCode, String variant) {
            VariantSymbol result = this.variantSymbolCache;
            if (result == null || !result.isoCode.equals(isoCode) || !result.variant.equals(variant)) {
                result = new VariantSymbol(isoCode, variant);
                CurrencySink sink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_VARIANT);
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
                CurrencySink sink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_PLURALS);
                sink.pluralsData = result;
                this.rb.getAllItemsWithFallbackNoFail("CurrencyPlurals/" + isoCode, sink);
                this.pluralsDataCache = result;
            }
            return result;
        }

        ParsingData fetchParsingData() {
            ParsingData result = this.parsingDataCache.get();
            if (result == null) {
                result = new ParsingData();
                CurrencySink sink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.TOP);
                sink.parsingData = result;
                this.rb.getAllItemsWithFallback("", sink);
                this.parsingDataCache = new SoftReference<ParsingData>(result);
            }
            return result;
        }

        Map<String, String> fetchUnitPatterns() {
            Map<String, String> result = this.unitPatternsCache;
            if (result == null) {
                result = new HashMap<String, String>();
                CurrencySink sink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_UNIT_PATTERNS);
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
                CurrencySink sink = new CurrencySink(!this.fallback, CurrencySink.EntrypointTable.CURRENCY_SPACING);
                sink.spacingInfo = result;
                this.rb.getAllItemsWithFallback("currencySpacing", sink);
                this.spacingInfoCache = result;
            }
            return result;
        }

        private static final class CurrencySink
        extends UResource.Sink {
            final boolean noRoot;
            final EntrypointTable entrypointTable;
            FormattingData formattingData = null;
            String[] pluralsData = null;
            ParsingData parsingData = null;
            Map<String, String> unitPatterns = null;
            CurrencyData.CurrencySpacingInfo spacingInfo = null;
            VariantSymbol variantSymbol = null;

            CurrencySink(boolean noRoot, EntrypointTable entrypointTable) {
                this.noRoot = noRoot;
                this.entrypointTable = entrypointTable;
            }

            @Override
            public void put(UResource.Key key, UResource.Value value2, boolean isRoot) {
                if (this.noRoot && isRoot) {
                    return;
                }
                switch (this.entrypointTable) {
                    case TOP: {
                        this.consumeTopTable(key, value2);
                        break;
                    }
                    case CURRENCIES: {
                        this.consumeCurrenciesEntry(key, value2);
                        break;
                    }
                    case CURRENCY_PLURALS: {
                        this.consumeCurrencyPluralsEntry(key, value2);
                        break;
                    }
                    case CURRENCY_VARIANT: {
                        this.consumeCurrenciesVariantEntry(key, value2);
                        break;
                    }
                    case CURRENCY_SPACING: {
                        this.consumeCurrencySpacingTable(key, value2);
                        break;
                    }
                    case CURRENCY_UNIT_PATTERNS: {
                        this.consumeCurrencyUnitPatternsTable(key, value2);
                    }
                }
            }

            private void consumeTopTable(UResource.Key key, UResource.Value value2) {
                UResource.Table table = value2.getTable();
                int i = 0;
                while (table.getKeyAndValue(i, key, value2)) {
                    if (key.contentEquals("Currencies")) {
                        this.consumeCurrenciesTable(key, value2);
                    } else if (key.contentEquals("Currencies%variant")) {
                        this.consumeCurrenciesVariantTable(key, value2);
                    } else if (key.contentEquals("CurrencyPlurals")) {
                        this.consumeCurrencyPluralsTable(key, value2);
                    }
                    ++i;
                }
            }

            void consumeCurrenciesTable(UResource.Key key, UResource.Value value2) {
                assert (this.parsingData != null);
                UResource.Table table = value2.getTable();
                int i = 0;
                while (table.getKeyAndValue(i, key, value2)) {
                    String isoCode = key.toString();
                    if (value2.getType() != 8) {
                        throw new ICUException("Unexpected data type in Currencies table for " + isoCode);
                    }
                    UResource.Array array = value2.getArray();
                    this.parsingData.symbolToIsoCode.put(isoCode, isoCode);
                    array.getValue(0, value2);
                    this.parsingData.symbolToIsoCode.put(value2.getString(), isoCode);
                    array.getValue(1, value2);
                    this.parsingData.nameToIsoCode.put(value2.getString(), isoCode);
                    ++i;
                }
            }

            void consumeCurrenciesEntry(UResource.Key key, UResource.Value value2) {
                assert (this.formattingData != null);
                String isoCode = key.toString();
                if (value2.getType() != 8) {
                    throw new ICUException("Unexpected data type in Currencies table for " + isoCode);
                }
                UResource.Array array = value2.getArray();
                if (this.formattingData.symbol == null) {
                    array.getValue(0, value2);
                    this.formattingData.symbol = value2.getString();
                }
                if (this.formattingData.displayName == null) {
                    array.getValue(1, value2);
                    this.formattingData.displayName = value2.getString();
                }
                if (array.getSize() > 2 && this.formattingData.formatInfo == null) {
                    array.getValue(2, value2);
                    UResource.Array formatArray = value2.getArray();
                    formatArray.getValue(0, value2);
                    String formatPattern = value2.getString();
                    formatArray.getValue(1, value2);
                    String decimalSeparator = value2.getString();
                    formatArray.getValue(2, value2);
                    String groupingSeparator = value2.getString();
                    this.formattingData.formatInfo = new CurrencyData.CurrencyFormatInfo(isoCode, formatPattern, decimalSeparator, groupingSeparator);
                }
            }

            void consumeCurrenciesVariantEntry(UResource.Key key, UResource.Value value2) {
                assert (this.variantSymbol != null);
                if (this.variantSymbol.symbol == null) {
                    this.variantSymbol.symbol = value2.getString();
                }
            }

            void consumeCurrenciesVariantTable(UResource.Key key, UResource.Value value2) {
                assert (this.parsingData != null);
                UResource.Table table = value2.getTable();
                int i = 0;
                while (table.getKeyAndValue(i, key, value2)) {
                    String isoCode = key.toString();
                    this.parsingData.symbolToIsoCode.put(value2.getString(), isoCode);
                    ++i;
                }
            }

            void consumeCurrencyPluralsTable(UResource.Key key, UResource.Value value2) {
                assert (this.parsingData != null);
                UResource.Table table = value2.getTable();
                int i = 0;
                while (table.getKeyAndValue(i, key, value2)) {
                    String isoCode = key.toString();
                    UResource.Table pluralsTable = value2.getTable();
                    int j = 0;
                    while (pluralsTable.getKeyAndValue(j, key, value2)) {
                        StandardPlural plural = StandardPlural.orNullFromString(key.toString());
                        if (plural == null) {
                            throw new ICUException("Could not make StandardPlural from keyword " + key);
                        }
                        this.parsingData.nameToIsoCode.put(value2.getString(), isoCode);
                        ++j;
                    }
                    ++i;
                }
            }

            void consumeCurrencyPluralsEntry(UResource.Key key, UResource.Value value2) {
                assert (this.pluralsData != null);
                UResource.Table pluralsTable = value2.getTable();
                int j = 0;
                while (pluralsTable.getKeyAndValue(j, key, value2)) {
                    StandardPlural plural = StandardPlural.orNullFromString(key.toString());
                    if (plural == null) {
                        throw new ICUException("Could not make StandardPlural from keyword " + key);
                    }
                    if (this.pluralsData[1 + plural.ordinal()] == null) {
                        this.pluralsData[1 + plural.ordinal()] = value2.getString();
                    }
                    ++j;
                }
            }

            void consumeCurrencySpacingTable(UResource.Key key, UResource.Value value2) {
                assert (this.spacingInfo != null);
                UResource.Table spacingTypesTable = value2.getTable();
                int i = 0;
                while (spacingTypesTable.getKeyAndValue(i, key, value2)) {
                    block8: {
                        CurrencyData.CurrencySpacingInfo.SpacingType type;
                        block7: {
                            block6: {
                                if (!key.contentEquals("beforeCurrency")) break block6;
                                type = CurrencyData.CurrencySpacingInfo.SpacingType.BEFORE;
                                this.spacingInfo.hasBeforeCurrency = true;
                                break block7;
                            }
                            if (!key.contentEquals("afterCurrency")) break block8;
                            type = CurrencyData.CurrencySpacingInfo.SpacingType.AFTER;
                            this.spacingInfo.hasAfterCurrency = true;
                        }
                        UResource.Table patternsTable = value2.getTable();
                        int j = 0;
                        while (patternsTable.getKeyAndValue(j, key, value2)) {
                            block12: {
                                CurrencyData.CurrencySpacingInfo.SpacingPattern pattern;
                                block10: {
                                    block11: {
                                        block9: {
                                            if (!key.contentEquals("currencyMatch")) break block9;
                                            pattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.CURRENCY_MATCH;
                                            break block10;
                                        }
                                        if (!key.contentEquals("surroundingMatch")) break block11;
                                        pattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.SURROUNDING_MATCH;
                                        break block10;
                                    }
                                    if (!key.contentEquals("insertBetween")) break block12;
                                    pattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.INSERT_BETWEEN;
                                }
                                this.spacingInfo.setSymbolIfNull(type, pattern, value2.getString());
                            }
                            ++j;
                        }
                    }
                    ++i;
                }
            }

            void consumeCurrencyUnitPatternsTable(UResource.Key key, UResource.Value value2) {
                assert (this.unitPatterns != null);
                UResource.Table table = value2.getTable();
                int i = 0;
                while (table.getKeyAndValue(i, key, value2)) {
                    String pluralKeyword = key.toString();
                    if (this.unitPatterns.get(pluralKeyword) == null) {
                        this.unitPatterns.put(pluralKeyword, value2.getString());
                    }
                    ++i;
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

        static class ParsingData {
            Map<String, String> symbolToIsoCode = new HashMap<String, String>();
            Map<String, String> nameToIsoCode = new HashMap<String, String>();

            ParsingData() {
            }
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

        static class FormattingData {
            final String isoCode;
            String displayName = null;
            String symbol = null;
            CurrencyData.CurrencyFormatInfo formatInfo = null;

            FormattingData(String isoCode) {
                this.isoCode = isoCode;
            }
        }
    }
}

