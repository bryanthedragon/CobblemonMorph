
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.intl.CoerceOptionsToObjectNode;
import com.oracle.truffle.js.nodes.intl.CoerceOptionsToObjectNodeGen;
import com.oracle.truffle.js.nodes.intl.GetStringOptionNode;
import com.oracle.truffle.js.nodes.intl.InitializeRelativeTimeFormatNodeGen;
import com.oracle.truffle.js.nodes.intl.JSToCanonicalizedLocaleListNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSRelativeTimeFormat;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.MissingResourceException;

public abstract class InitializeRelativeTimeFormatNode
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
    GetStringOptionNode getStyleOption;
    @Node.Child
    GetStringOptionNode getNumericOption;
    private final BranchProfile errorBranch = BranchProfile.create();

    protected InitializeRelativeTimeFormatNode(JSContext context) {
        this.context = context;
        this.toCanonicalizedLocaleListNode = JSToCanonicalizedLocaleListNode.create(context);
        this.coerceOptionsToObjectNode = CoerceOptionsToObjectNodeGen.create(context);
        this.getStyleOption = GetStringOptionNode.create(context, IntlUtil.KEY_STYLE, new String[]{"long", "short", "narrow"}, "long");
        this.getNumericOption = GetStringOptionNode.create(context, IntlUtil.KEY_NUMERIC, new String[]{"always", "auto"}, "always");
        this.getLocaleMatcherOption = GetStringOptionNode.create(context, IntlUtil.KEY_LOCALE_MATCHER, new String[]{"lookup", "best fit"}, "best fit");
        this.getNumberingSystemOption = GetStringOptionNode.create(context, IntlUtil.KEY_NUMBERING_SYSTEM, null, null);
    }

    public abstract JSDynamicObject executeInit(JSDynamicObject var1, Object var2, Object var3);

    public static InitializeRelativeTimeFormatNode createInitalizeRelativeTimeFormatNode(JSContext context) {
        return InitializeRelativeTimeFormatNodeGen.create(context);
    }

    @Specialization
    public JSDynamicObject initializeRelativeTimeFormat(JSDynamicObject relativeTimeFormatObj, Object localesArg, Object optionsArg) {
        try {
            JSRelativeTimeFormat.InternalState state = JSRelativeTimeFormat.getInternalState(relativeTimeFormatObj);
            String[] locales = this.toCanonicalizedLocaleListNode.executeLanguageTags(localesArg);
            JSDynamicObject options = this.coerceOptionsToObjectNode.execute(optionsArg);
            this.getLocaleMatcherOption.executeValue(options);
            String numberingSystem = this.getNumberingSystemOption.executeValue(options);
            if (numberingSystem != null) {
                IntlUtil.validateUnicodeLocaleIdentifierType(numberingSystem, this.errorBranch);
                numberingSystem = IntlUtil.normalizeUnicodeLocaleIdentifierType(numberingSystem);
            }
            String style = this.getStyleOption.executeValue(options);
            String numeric = this.getNumericOption.executeValue(options);
            state.setStyle(style);
            state.setNumeric(numeric);
            state.resolveLocaleAndNumberingSystem(this.context, locales, numberingSystem);
            state.initializeRelativeTimeFormatter();
        }
        catch (MissingResourceException e) {
            this.errorBranch.enter();
            throw Errors.createICU4JDataError(e);
        }
        return relativeTimeFormatObj;
    }
}

