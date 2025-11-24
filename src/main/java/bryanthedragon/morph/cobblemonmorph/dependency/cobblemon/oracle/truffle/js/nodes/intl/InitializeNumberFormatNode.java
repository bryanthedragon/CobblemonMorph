
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.intl.CoerceOptionsToObjectNode;
import com.oracle.truffle.js.nodes.intl.CoerceOptionsToObjectNodeGen;
import com.oracle.truffle.js.nodes.intl.GetNumberOptionNode;
import com.oracle.truffle.js.nodes.intl.GetStringOptionNode;
import com.oracle.truffle.js.nodes.intl.GetStringOrBooleanOptionNode;
import com.oracle.truffle.js.nodes.intl.InitializeNumberFormatNodeGen;
import com.oracle.truffle.js.nodes.intl.JSToCanonicalizedLocaleListNode;
import com.oracle.truffle.js.nodes.intl.SetNumberFormatDigitOptionsNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormat;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.MissingResourceException;

public abstract class InitializeNumberFormatNode
extends JavaScriptBaseNode {
    private final JSContext context;
    @Node.Child
    JSToCanonicalizedLocaleListNode toCanonicalizedLocaleListNode;
    @Node.Child
    CoerceOptionsToObjectNode coerceOptionsToObjectNode;
    @Node.Child
    GetStringOptionNode getLocaleMatcherOption;
    @Node.Child
    GetStringOptionNode getNumberingSystemOption;
    @Node.Child
    SetNumberFormatDigitOptionsNode setNumberFormatDigitOptions;
    @Node.Child
    GetStringOptionNode getStyleOption;
    @Node.Child
    GetStringOptionNode getCurrencyOption;
    @Node.Child
    GetStringOptionNode getCurrencyDisplayOption;
    @Node.Child
    GetStringOptionNode getCurrencySignOption;
    @Node.Child
    GetStringOptionNode getUnitOption;
    @Node.Child
    GetStringOptionNode getUnitDisplayOption;
    @Node.Child
    GetStringOptionNode getNotationOption;
    @Node.Child
    GetStringOptionNode getCompactDisplayOption;
    @Node.Child
    GetStringOrBooleanOptionNode getUseGroupingOption;
    @Node.Child
    GetStringOptionNode getSignDisplayOption;
    @Node.Child
    GetNumberOptionNode getRoundingIncrementOption;
    @Node.Child
    GetStringOptionNode getTrailingZeroDisplayOption;
    @Node.Child
    GetStringOptionNode getRoundingModeOption;
    private final BranchProfile errorBranch = BranchProfile.create();

    protected InitializeNumberFormatNode(JSContext context) {
        this.context = context;
        this.toCanonicalizedLocaleListNode = JSToCanonicalizedLocaleListNode.create(context);
        this.coerceOptionsToObjectNode = CoerceOptionsToObjectNodeGen.create(context);
        this.getLocaleMatcherOption = GetStringOptionNode.create(context, IntlUtil.KEY_LOCALE_MATCHER, new String[]{"lookup", "best fit"}, "best fit");
        this.getNumberingSystemOption = GetStringOptionNode.create(context, IntlUtil.KEY_NUMBERING_SYSTEM, null, null);
        this.getStyleOption = GetStringOptionNode.create(context, IntlUtil.KEY_STYLE, new String[]{"decimal", "percent", "currency", "unit"}, "decimal");
        this.getCurrencyOption = GetStringOptionNode.create(context, IntlUtil.KEY_CURRENCY, null, null);
        this.getCurrencyDisplayOption = GetStringOptionNode.create(context, IntlUtil.KEY_CURRENCY_DISPLAY, new String[]{"code", "symbol", "narrowSymbol", "name"}, "symbol");
        this.getCurrencySignOption = GetStringOptionNode.create(context, IntlUtil.KEY_CURRENCY_SIGN, new String[]{"standard", "accounting"}, "standard");
        this.getUnitOption = GetStringOptionNode.create(context, IntlUtil.KEY_UNIT, null, null);
        this.getUnitDisplayOption = GetStringOptionNode.create(context, IntlUtil.KEY_UNIT_DISPLAY, new String[]{"short", "narrow", "long"}, "short");
        this.getNotationOption = GetStringOptionNode.create(context, IntlUtil.KEY_NOTATION, new String[]{"standard", "scientific", "engineering", "compact"}, "standard");
        this.getCompactDisplayOption = GetStringOptionNode.create(context, IntlUtil.KEY_COMPACT_DISPLAY, new String[]{"short", "long"}, "short");
        this.getUseGroupingOption = GetStringOrBooleanOptionNode.create(context, IntlUtil.KEY_USE_GROUPING, new String[]{"min2", "auto", "always"}, "always", false, null);
        this.getSignDisplayOption = GetStringOptionNode.create(context, IntlUtil.KEY_SIGN_DISPLAY, new String[]{"auto", "never", "always", "exceptZero", "negative"}, "auto");
        this.getRoundingIncrementOption = GetNumberOptionNode.create(context, IntlUtil.KEY_ROUNDING_INCREMENT);
        this.getTrailingZeroDisplayOption = GetStringOptionNode.create(context, IntlUtil.KEY_TRAILING_ZERO_DISPLAY, new String[]{"auto", "stripIfInteger"}, "auto");
        this.getRoundingModeOption = GetStringOptionNode.create(context, IntlUtil.KEY_ROUNDING_MODE, new String[]{"ceil", "floor", "expand", "trunc", "halfCeil", "halfFloor", "halfExpand", "halfTrunc", "halfEven"}, "halfExpand");
        this.setNumberFormatDigitOptions = SetNumberFormatDigitOptionsNode.create(context);
    }

    public abstract JSDynamicObject executeInit(JSDynamicObject var1, Object var2, Object var3);

    public static InitializeNumberFormatNode createInitalizeNumberFormatNode(JSContext context) {
        return InitializeNumberFormatNodeGen.create(context);
    }

    @Specialization
    public JSDynamicObject initializeNumberFormat(JSDynamicObject numberFormatObj, Object localesArg, Object optionsArg) {
        try {
            Object useGrouping;
            int mxfdDefault;
            int mnfdDefault;
            JSNumberFormat.InternalState state = JSNumberFormat.getInternalState(numberFormatObj);
            String[] locales = this.toCanonicalizedLocaleListNode.executeLanguageTags(localesArg);
            JSDynamicObject options = this.coerceOptionsToObjectNode.execute(optionsArg);
            this.getLocaleMatcherOption.executeValue(options);
            String numberingSystem = this.getNumberingSystemOption.executeValue(options);
            if (numberingSystem != null) {
                IntlUtil.validateUnicodeLocaleIdentifierType(numberingSystem, this.errorBranch);
                numberingSystem = IntlUtil.normalizeUnicodeLocaleIdentifierType(numberingSystem);
            }
            state.resolveLocaleAndNumberingSystem(this.context, locales, numberingSystem);
            this.setNumberFormatUnitOptions(state, options);
            String style = state.getStyle();
            if ("currency".equals(style)) {
                int cDigits;
                mnfdDefault = cDigits = JSNumberFormat.currencyDigits(this.context, state.getCurrency());
                mxfdDefault = cDigits;
            } else {
                mnfdDefault = 0;
                mxfdDefault = "percent".equals(style) ? 0 : 3;
            }
            String notation = this.getNotationOption.executeValue(options);
            state.setNotation(notation);
            boolean compactNotation = "compact".equals(notation);
            this.setNumberFormatDigitOptions.execute(state, options, mnfdDefault, mxfdDefault, compactNotation);
            int roundingIncrement = this.getRoundingIncrementOption.executeInt(options, 1, 5000, 1);
            if (!InitializeNumberFormatNode.isValidRoundingIncrement(roundingIncrement)) {
                this.errorBranch.enter();
                throw Errors.createRangeError("roundingIncrement value is out of range.");
            }
            if (roundingIncrement != 1) {
                if (!"fractionDigits".equals(state.getRoundingType())) {
                    this.errorBranch.enter();
                    throw Errors.createTypeError("roundingIncrement can be used with fractionDigits rounding type only");
                }
                if (state.getMinimumFractionDigits().intValue() != state.getMaximumFractionDigits().intValue()) {
                    this.errorBranch.enter();
                    throw Errors.createRangeError("roundingIncrement can be used when minimumFractionDigits and maximumFractionDigits are equal only");
                }
            }
            state.setRoundingIncrement(roundingIncrement);
            String trailingZeroDisplay = this.getTrailingZeroDisplayOption.executeValue(options);
            state.setTrailingZeroDisplay(trailingZeroDisplay);
            String compactDisplay = this.getCompactDisplayOption.executeValue(options);
            String defaultUseGrouping = "auto";
            if (compactNotation) {
                state.setCompactDisplay(compactDisplay);
                defaultUseGrouping = "min2";
            }
            if ((useGrouping = this.getUseGroupingOption.executeValue(options)) == null) {
                useGrouping = defaultUseGrouping;
            }
            state.setGroupingUsed(useGrouping);
            String signDisplay = this.getSignDisplayOption.executeValue(options);
            state.setSignDisplay(signDisplay);
            String roundingMode = this.getRoundingModeOption.executeValue(options);
            state.setRoundingMode(roundingMode);
            state.initializeNumberFormatter();
        }
        catch (MissingResourceException e) {
            this.errorBranch.enter();
            throw Errors.createICU4JDataError(e);
        }
        return numberFormatObj;
    }

    private void setNumberFormatUnitOptions(JSNumberFormat.InternalState state, JSDynamicObject options) {
        String style = this.getStyleOption.executeValue(options);
        state.setStyle(style);
        boolean styleIsCurrency = "currency".equals(style);
        boolean styleIsUnit = "unit".equals(style);
        String currency = this.getCurrencyOption.executeValue(options);
        if (currency == null) {
            if (styleIsCurrency) {
                this.errorBranch.enter();
                throw Errors.createTypeError("Currency can not be undefined when style is \"currency\".");
            }
        } else {
            IntlUtil.ensureIsWellFormedCurrencyCode(currency);
        }
        String currencyDisplay = this.getCurrencyDisplayOption.executeValue(options);
        String currencySign = this.getCurrencySignOption.executeValue(options);
        String unit = this.getUnitOption.executeValue(options);
        if (unit == null) {
            if (styleIsUnit) {
                this.errorBranch.enter();
                throw Errors.createTypeError("Unit can not be undefined when style is \"unit\".");
            }
        } else {
            IntlUtil.ensureIsWellFormedUnitIdentifier(unit);
        }
        String unitDisplay = this.getUnitDisplayOption.executeValue(options);
        if (styleIsCurrency) {
            currency = IntlUtil.toUpperCase(currency);
            state.setCurrency(currency);
            state.setCurrencyDisplay(currencyDisplay);
            state.setCurrencySign(currencySign);
        } else if (styleIsUnit) {
            state.setUnit(unit);
            state.setUnitDisplay(unitDisplay);
        }
    }

    private static boolean isValidRoundingIncrement(int roundingIncrement) {
        switch (roundingIncrement) {
            case 1: 
            case 2: 
            case 5: 
            case 10: 
            case 20: 
            case 25: 
            case 50: 
            case 100: 
            case 200: 
            case 250: 
            case 500: 
            case 1000: 
            case 2000: 
            case 2500: 
            case 5000: {
                return true;
            }
        }
        return false;
    }
}

