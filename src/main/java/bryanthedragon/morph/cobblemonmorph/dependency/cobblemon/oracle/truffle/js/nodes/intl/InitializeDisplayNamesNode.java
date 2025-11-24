
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.intl.GetOptionsObjectNode;
import com.oracle.truffle.js.nodes.intl.GetOptionsObjectNodeGen;
import com.oracle.truffle.js.nodes.intl.GetStringOptionNode;
import com.oracle.truffle.js.nodes.intl.InitializeDisplayNamesNodeGen;
import com.oracle.truffle.js.nodes.intl.JSToCanonicalizedLocaleListNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSDisplayNames;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.MissingResourceException;

public abstract class InitializeDisplayNamesNode
extends JavaScriptBaseNode {
    private final JSContext context;
    @Node.Child
    JSToCanonicalizedLocaleListNode toCanonicalizedLocaleListNode;
    @Node.Child
    GetOptionsObjectNode getOptionsObjectNode;
    @Node.Child
    GetStringOptionNode getLocaleMatcherOption;
    @Node.Child
    GetStringOptionNode getStyleOption;
    @Node.Child
    GetStringOptionNode getTypeOption;
    @Node.Child
    GetStringOptionNode getFallbackOption;
    @Node.Child
    GetStringOptionNode getLanguageDisplayOption;
    private final BranchProfile errorBranch = BranchProfile.create();

    protected InitializeDisplayNamesNode(JSContext context) {
        this.context = context;
        this.toCanonicalizedLocaleListNode = JSToCanonicalizedLocaleListNode.create(context);
        this.getOptionsObjectNode = GetOptionsObjectNodeGen.create(context);
        this.getLocaleMatcherOption = GetStringOptionNode.create(context, IntlUtil.KEY_LOCALE_MATCHER, new String[]{"lookup", "best fit"}, "best fit");
        this.getStyleOption = GetStringOptionNode.create(context, IntlUtil.KEY_STYLE, new String[]{"narrow", "short", "long"}, "long");
        this.getTypeOption = GetStringOptionNode.create(context, IntlUtil.KEY_TYPE, new String[]{"language", "region", "script", "currency", "calendar", "dateTimeField"}, null);
        this.getFallbackOption = GetStringOptionNode.create(context, IntlUtil.KEY_FALLBACK, new String[]{"code", "none"}, "code");
        this.getLanguageDisplayOption = GetStringOptionNode.create(context, IntlUtil.KEY_LANGUAGE_DISPLAY, new String[]{"dialect", "standard"}, "dialect");
    }

    public abstract JSDynamicObject executeInit(JSDynamicObject var1, Object var2, Object var3);

    public static InitializeDisplayNamesNode createInitalizeDisplayNamesNode(JSContext context) {
        return InitializeDisplayNamesNodeGen.create(context);
    }

    @Specialization
    public JSDynamicObject initializeDisplayNames(JSDynamicObject displayNamesObject, Object localesArg, Object optionsArg) {
        try {
            String[] locales = this.toCanonicalizedLocaleListNode.executeLanguageTags(localesArg);
            if (optionsArg == Undefined.instance) {
                this.errorBranch.enter();
                throw Errors.createTypeError("options not specified");
            }
            Object options = this.getOptionsObjectNode.execute(optionsArg);
            this.getLocaleMatcherOption.executeValue(options);
            String optStyle = this.getStyleOption.executeValue(options);
            String optType = this.getTypeOption.executeValue(options);
            if (optType == null) {
                this.errorBranch.enter();
                throw Errors.createTypeError("'type' option not specified");
            }
            String optFallback = this.getFallbackOption.executeValue(options);
            String optLanguageDisplay = this.getLanguageDisplayOption.executeValue(options);
            JSDisplayNames.InternalState state = JSDisplayNames.getInternalState(displayNamesObject);
            JSDisplayNames.setupInternalState(this.context, state, locales, optStyle, optType, optFallback, optLanguageDisplay);
        }
        catch (MissingResourceException e) {
            this.errorBranch.enter();
            throw Errors.createICU4JDataError(e);
        }
        return displayNamesObject;
    }
}

