
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.number.AffixPatternProvider;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.CustomSymbolCurrency;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalFormatProperties;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.Grouper;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.MacroProps;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.Padder;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.PatternStringParser;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.PatternStringUtils;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.PropertiesAffixPatternProvider;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.RoundingUtils;
import com.cobblemon.mod.relocations.ibm.icu.number.CompactNotation;
import com.cobblemon.mod.relocations.ibm.icu.number.CurrencyPrecision;
import com.cobblemon.mod.relocations.ibm.icu.number.FractionPrecision;
import com.cobblemon.mod.relocations.ibm.icu.number.IntegerWidth;
import com.cobblemon.mod.relocations.ibm.icu.number.Notation;
import com.cobblemon.mod.relocations.ibm.icu.number.NumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.number.Precision;
import com.cobblemon.mod.relocations.ibm.icu.number.ScientificNotation;
import com.cobblemon.mod.relocations.ibm.icu.number.UnlocalizedNumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.text.CompactDecimalFormat;
import com.cobblemon.mod.relocations.ibm.icu.text.DecimalFormatSymbols;
import com.cobblemon.mod.relocations.ibm.icu.text.PluralRules;
import com.cobblemon.mod.relocations.ibm.icu.util.Currency;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.math.BigDecimal;
import java.math.MathContext;

final class NumberPropertyMapper {
    NumberPropertyMapper() {
    }

    public static UnlocalizedNumberFormatter create(DecimalFormatProperties properties2, DecimalFormatSymbols symbols) {
        MacroProps macros = NumberPropertyMapper.oldToNew(properties2, symbols, null);
        return (UnlocalizedNumberFormatter)NumberFormatter.with().macros(macros);
    }

    public static UnlocalizedNumberFormatter create(DecimalFormatProperties properties2, DecimalFormatSymbols symbols, DecimalFormatProperties exportedProperties) {
        MacroProps macros = NumberPropertyMapper.oldToNew(properties2, symbols, exportedProperties);
        return (UnlocalizedNumberFormatter)NumberFormatter.with().macros(macros);
    }

    public static UnlocalizedNumberFormatter create(String pattern, DecimalFormatSymbols symbols) {
        DecimalFormatProperties properties2 = PatternStringParser.parseToProperties(pattern);
        return NumberPropertyMapper.create(properties2, symbols);
    }

    public static MacroProps oldToNew(DecimalFormatProperties properties2, DecimalFormatSymbols symbols, DecimalFormatProperties exportedProperties) {
        boolean explicitMinMaxSig;
        boolean explicitCurrencyUsage;
        AffixPatternProvider affixProvider;
        MacroProps macros = new MacroProps();
        ULocale locale = symbols.getULocale();
        macros.symbols = symbols;
        PluralRules rules = properties2.getPluralRules();
        if (rules == null && properties2.getCurrencyPluralInfo() != null) {
            rules = properties2.getCurrencyPluralInfo().getPluralRules();
        }
        macros.rules = rules;
        macros.affixProvider = affixProvider = PropertiesAffixPatternProvider.forProperties(properties2);
        boolean useCurrency = properties2.getCurrency() != null || properties2.getCurrencyPluralInfo() != null || properties2.getCurrencyUsage() != null || affixProvider.hasCurrencySign();
        Currency currency = CustomSymbolCurrency.resolve(properties2.getCurrency(), locale, symbols);
        Currency.CurrencyUsage currencyUsage = properties2.getCurrencyUsage();
        boolean bl = explicitCurrencyUsage = currencyUsage != null;
        if (!explicitCurrencyUsage) {
            currencyUsage = Currency.CurrencyUsage.STANDARD;
        }
        if (useCurrency) {
            macros.unit = currency;
        }
        int maxInt = properties2.getMaximumIntegerDigits();
        int minInt = properties2.getMinimumIntegerDigits();
        int maxFrac = properties2.getMaximumFractionDigits();
        int minFrac = properties2.getMinimumFractionDigits();
        int minSig = properties2.getMinimumSignificantDigits();
        int maxSig = properties2.getMaximumSignificantDigits();
        BigDecimal roundingIncrement = properties2.getRoundingIncrement();
        MathContext mathContext = RoundingUtils.getMathContextOrUnlimited(properties2);
        boolean explicitMinMaxFrac = minFrac != -1 || maxFrac != -1;
        boolean bl2 = explicitMinMaxSig = minSig != -1 || maxSig != -1;
        if (useCurrency) {
            if (minFrac == -1 && maxFrac == -1) {
                minFrac = currency.getDefaultFractionDigits(currencyUsage);
                maxFrac = currency.getDefaultFractionDigits(currencyUsage);
            } else if (minFrac == -1) {
                minFrac = Math.min(maxFrac, currency.getDefaultFractionDigits(currencyUsage));
            } else if (maxFrac == -1) {
                maxFrac = Math.max(minFrac, currency.getDefaultFractionDigits(currencyUsage));
            }
        }
        if (minInt == 0 && maxFrac != 0) {
            int n = minFrac = minFrac < 0 || minFrac == 0 && maxInt == 0 ? 1 : minFrac;
            maxFrac = maxFrac < 0 ? -1 : (maxFrac < minFrac ? minFrac : maxFrac);
            minInt = 0;
            maxInt = maxInt < 0 ? -1 : (maxInt > 999 ? -1 : maxInt);
        } else {
            int n = minFrac = minFrac < 0 ? 0 : minFrac;
            int n2 = maxFrac < 0 ? -1 : (maxFrac = maxFrac < minFrac ? minFrac : maxFrac);
            int n3 = minInt <= 0 ? 1 : (minInt = minInt > 999 ? 1 : minInt);
            maxInt = maxInt < 0 ? -1 : (maxInt < minInt ? minInt : (maxInt > 999 ? -1 : maxInt));
        }
        Precision rounding = null;
        if (explicitCurrencyUsage) {
            rounding = Precision.constructCurrency(currencyUsage).withCurrency(currency);
        } else if (roundingIncrement != null) {
            if (PatternStringUtils.ignoreRoundingIncrement(roundingIncrement, maxFrac)) {
                rounding = Precision.constructFraction(minFrac, maxFrac);
            } else {
                if (minFrac > roundingIncrement.scale()) {
                    roundingIncrement = roundingIncrement.setScale(minFrac);
                }
                rounding = Precision.constructIncrement(roundingIncrement);
            }
        } else if (explicitMinMaxSig) {
            int n = minSig < 1 ? 1 : (minSig = minSig > 999 ? 999 : minSig);
            maxSig = maxSig < 0 ? 999 : (maxSig < minSig ? minSig : (maxSig > 999 ? 999 : maxSig));
            rounding = Precision.constructSignificant(minSig, maxSig);
        } else if (explicitMinMaxFrac) {
            rounding = Precision.constructFraction(minFrac, maxFrac);
        } else if (useCurrency) {
            rounding = Precision.constructCurrency(currencyUsage);
        }
        if (rounding != null) {
            macros.precision = rounding = rounding.withMode(mathContext);
        }
        macros.integerWidth = IntegerWidth.zeroFillTo(minInt).truncateAt(maxInt);
        macros.grouping = Grouper.forProperties(properties2);
        if (properties2.getFormatWidth() > 0) {
            macros.padder = Padder.forProperties(properties2);
        }
        macros.decimal = properties2.getDecimalSeparatorAlwaysShown() ? NumberFormatter.DecimalSeparatorDisplay.ALWAYS : NumberFormatter.DecimalSeparatorDisplay.AUTO;
        NumberFormatter.SignDisplay signDisplay = macros.sign = properties2.getSignAlwaysShown() ? NumberFormatter.SignDisplay.ALWAYS : NumberFormatter.SignDisplay.AUTO;
        if (properties2.getMinimumExponentDigits() != -1) {
            if (maxInt > 8) {
                maxInt = minInt;
                macros.integerWidth = IntegerWidth.zeroFillTo(minInt).truncateAt(maxInt);
            } else if (maxInt > minInt && minInt > 1) {
                minInt = 1;
                macros.integerWidth = IntegerWidth.zeroFillTo(minInt).truncateAt(maxInt);
            }
            int engineering = maxInt < 0 ? -1 : maxInt;
            macros.notation = new ScientificNotation(engineering, engineering == minInt, properties2.getMinimumExponentDigits(), properties2.getExponentSignAlwaysShown() ? NumberFormatter.SignDisplay.ALWAYS : NumberFormatter.SignDisplay.AUTO);
            if (macros.precision instanceof FractionPrecision) {
                int maxInt_ = properties2.getMaximumIntegerDigits();
                int minInt_ = properties2.getMinimumIntegerDigits();
                int minFrac_ = properties2.getMinimumFractionDigits();
                int maxFrac_ = properties2.getMaximumFractionDigits();
                if (minInt_ == 0 && maxFrac_ == 0) {
                    macros.precision = Precision.constructInfinite().withMode(mathContext);
                } else if (minInt_ == 0 && minFrac_ == 0) {
                    macros.precision = Precision.constructSignificant(1, maxFrac_ + 1).withMode(mathContext);
                } else {
                    int maxSig_ = minInt_ + maxFrac_;
                    if (maxInt_ > minInt_ && minInt_ > 1) {
                        minInt_ = 1;
                    }
                    int minSig_ = minInt_ + minFrac_;
                    macros.precision = Precision.constructSignificant(minSig_, maxSig_).withMode(mathContext);
                }
            }
        }
        if (properties2.getCompactStyle() != null) {
            macros.notation = properties2.getCompactCustomData() != null ? new CompactNotation(properties2.getCompactCustomData()) : (properties2.getCompactStyle() == CompactDecimalFormat.CompactStyle.LONG ? Notation.compactLong() : Notation.compactShort());
            macros.affixProvider = null;
        }
        macros.scale = RoundingUtils.scaleFromProperties(properties2);
        if (exportedProperties != null) {
            exportedProperties.setCurrency(currency);
            exportedProperties.setMathContext(mathContext);
            exportedProperties.setRoundingMode(mathContext.getRoundingMode());
            exportedProperties.setMinimumIntegerDigits(minInt);
            exportedProperties.setMaximumIntegerDigits(maxInt == -1 ? Integer.MAX_VALUE : maxInt);
            Precision rounding_ = rounding instanceof CurrencyPrecision ? ((CurrencyPrecision)rounding).withCurrency(currency) : rounding;
            int minFrac_ = minFrac;
            int maxFrac_ = maxFrac;
            int minSig_ = minSig;
            int maxSig_ = maxSig;
            BigDecimal increment_ = null;
            if (rounding_ instanceof Precision.FractionRounderImpl) {
                minFrac_ = ((Precision.FractionRounderImpl)rounding_).minFrac;
                maxFrac_ = ((Precision.FractionRounderImpl)rounding_).maxFrac;
            } else if (rounding_ instanceof Precision.IncrementRounderImpl) {
                increment_ = ((Precision.IncrementRounderImpl)rounding_).increment;
                minFrac_ = increment_.scale();
                maxFrac_ = increment_.scale();
            } else if (rounding_ instanceof Precision.SignificantRounderImpl) {
                minSig_ = ((Precision.SignificantRounderImpl)rounding_).minSig;
                maxSig_ = ((Precision.SignificantRounderImpl)rounding_).maxSig;
            }
            exportedProperties.setMinimumFractionDigits(minFrac_);
            exportedProperties.setMaximumFractionDigits(maxFrac_);
            exportedProperties.setMinimumSignificantDigits(minSig_);
            exportedProperties.setMaximumSignificantDigits(maxSig_);
            exportedProperties.setRoundingIncrement(increment_);
        }
        return macros;
    }
}

