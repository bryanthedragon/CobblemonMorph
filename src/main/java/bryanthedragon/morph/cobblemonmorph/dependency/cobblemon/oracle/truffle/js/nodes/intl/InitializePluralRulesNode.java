
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.intl.CoerceOptionsToObjectNode;
import com.oracle.truffle.js.nodes.intl.CoerceOptionsToObjectNodeGen;
import com.oracle.truffle.js.nodes.intl.GetStringOptionNode;
import com.oracle.truffle.js.nodes.intl.InitializePluralRulesNodeGen;
import com.oracle.truffle.js.nodes.intl.JSToCanonicalizedLocaleListNode;
import com.oracle.truffle.js.nodes.intl.SetNumberFormatDigitOptionsNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSPluralRules;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.MissingResourceException;

public abstract class InitializePluralRulesNode
extends JavaScriptBaseNode {
    private final JSContext context;
    @Node.Child
    JSToCanonicalizedLocaleListNode toCanonicalizedLocaleListNode;
    @Node.Child
    CoerceOptionsToObjectNode coerceOptionsToObjectNode;
    @Node.Child
    GetStringOptionNode getLocaleMatcherOption;
    @Node.Child
    SetNumberFormatDigitOptionsNode setNumberFormatDigitOptions;
    @Node.Child
    GetStringOptionNode getTypeOption;
    private final BranchProfile errorBranch = BranchProfile.create();

    protected InitializePluralRulesNode(JSContext context) {
        this.context = context;
        this.toCanonicalizedLocaleListNode = JSToCanonicalizedLocaleListNode.create(context);
        this.coerceOptionsToObjectNode = CoerceOptionsToObjectNodeGen.create(context);
        this.getLocaleMatcherOption = GetStringOptionNode.create(context, IntlUtil.KEY_LOCALE_MATCHER, new String[]{"lookup", "best fit"}, "best fit");
        this.getTypeOption = GetStringOptionNode.create(context, IntlUtil.KEY_TYPE, new String[]{"cardinal", "ordinal"}, "cardinal");
        this.setNumberFormatDigitOptions = SetNumberFormatDigitOptionsNode.create(context);
    }

    public abstract JSDynamicObject executeInit(JSDynamicObject var1, Object var2, Object var3);

    public static InitializePluralRulesNode createInitalizePluralRulesNode(JSContext context) {
        return InitializePluralRulesNodeGen.create(context);
    }

    @Specialization
    public JSDynamicObject initializePluralRules(JSDynamicObject pluralRulesObj, Object localesArg, Object optionsArg) {
        try {
            JSPluralRules.InternalState state = JSPluralRules.getInternalState(pluralRulesObj);
            String[] locales = this.toCanonicalizedLocaleListNode.executeLanguageTags(localesArg);
            JSDynamicObject options = this.coerceOptionsToObjectNode.execute(optionsArg);
            this.getLocaleMatcherOption.executeValue(options);
            String optType = this.getTypeOption.executeValue(options);
            state.setType(optType);
            state.resolveLocaleAndNumberingSystem(this.context, locales, null);
            this.setNumberFormatDigitOptions.execute(state, options, 0, 3, false);
            state.initializeNumberFormatter();
            state.initializePluralRules();
        }
        catch (MissingResourceException e) {
            this.errorBranch.enter();
            throw Errors.createICU4JDataError(e);
        }
        return pluralRulesObj;
    }
}

