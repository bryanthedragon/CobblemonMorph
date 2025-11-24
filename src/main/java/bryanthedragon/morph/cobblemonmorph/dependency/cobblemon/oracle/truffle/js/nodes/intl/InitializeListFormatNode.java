
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.intl.GetOptionsObjectNode;
import com.oracle.truffle.js.nodes.intl.GetOptionsObjectNodeGen;
import com.oracle.truffle.js.nodes.intl.GetStringOptionNode;
import com.oracle.truffle.js.nodes.intl.InitializeListFormatNodeGen;
import com.oracle.truffle.js.nodes.intl.JSToCanonicalizedLocaleListNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSListFormat;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.MissingResourceException;

public abstract class InitializeListFormatNode
extends JavaScriptBaseNode {
    private final JSContext context;
    @Node.Child
    JSToCanonicalizedLocaleListNode toCanonicalizedLocaleListNode;
    @Node.Child
    GetOptionsObjectNode getOptionsObjectNode;
    @Node.Child
    GetStringOptionNode getLocaleMatcherOption;
    @Node.Child
    GetStringOptionNode getTypeOption;
    @Node.Child
    GetStringOptionNode getStyleOption;
    private final BranchProfile errorBranch = BranchProfile.create();

    protected InitializeListFormatNode(JSContext context) {
        this.context = context;
        this.toCanonicalizedLocaleListNode = JSToCanonicalizedLocaleListNode.create(context);
        this.getOptionsObjectNode = GetOptionsObjectNodeGen.create(context);
        this.getTypeOption = GetStringOptionNode.create(context, IntlUtil.KEY_TYPE, new String[]{"conjunction", "disjunction", "unit"}, "conjunction");
        this.getStyleOption = GetStringOptionNode.create(context, IntlUtil.KEY_STYLE, new String[]{"long", "short", "narrow"}, "long");
        this.getLocaleMatcherOption = GetStringOptionNode.create(context, IntlUtil.KEY_LOCALE_MATCHER, new String[]{"lookup", "best fit"}, "best fit");
    }

    public abstract JSDynamicObject executeInit(JSDynamicObject var1, Object var2, Object var3);

    public static InitializeListFormatNode createInitalizeListFormatNode(JSContext context) {
        return InitializeListFormatNodeGen.create(context);
    }

    @Specialization
    public JSDynamicObject initializeListFormat(JSDynamicObject listFormatObj, Object localesArg, Object optionsArg) {
        try {
            JSListFormat.InternalState state = JSListFormat.getInternalState(listFormatObj);
            String[] locales = this.toCanonicalizedLocaleListNode.executeLanguageTags(localesArg);
            Object options = this.getOptionsObjectNode.execute(optionsArg);
            this.getLocaleMatcherOption.executeValue(options);
            String optType = this.getTypeOption.executeValue(options);
            String optStyle = this.getStyleOption.executeValue(options);
            state.setType(optType);
            state.setStyle(optStyle);
            JSListFormat.setLocale(this.context, state, locales);
            JSListFormat.setupInternalListFormatter(state);
        }
        catch (MissingResourceException e) {
            this.errorBranch.enter();
            throw Errors.createICU4JDataError(e);
        }
        return listFormatObj;
    }
}

