
package com.oracle.truffle.js.runtime.builtins.intl;

import com.cobblemon.mod.relocations.ibm.icu.number.FormattedNumber;
import com.cobblemon.mod.relocations.ibm.icu.number.FormattedNumberRange;
import com.cobblemon.mod.relocations.ibm.icu.number.LocalizedNumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.number.LocalizedNumberRangeFormatter;
import com.cobblemon.mod.relocations.ibm.icu.number.NumberRangeFormatter;
import com.cobblemon.mod.relocations.ibm.icu.text.PluralRules;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.PluralRulesFunctionBuiltins;
import com.oracle.truffle.js.builtins.intl.PluralRulesPrototypeBuiltins;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSPluralRulesObject;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.LinkedList;
import java.util.List;

public final class JSPluralRules
extends JSNonProxy
implements JSConstructorFactory.WithFunctions,
PrototypeSupplier {
    public static final TruffleString CLASS_NAME = Strings.constant("PluralRules");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("PluralRules.prototype");
    public static final TruffleString TO_STRING_TAG = Strings.constant("Intl.PluralRules");
    public static final JSPluralRules INSTANCE = new JSPluralRules();

    private JSPluralRules() {
    }

    public static boolean isJSPluralRules(Object obj) {
        return obj instanceof JSPluralRulesObject;
    }

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return this.getClassName();
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSContext ctx = realm.getContext();
        JSObject pluralRulesPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, pluralRulesPrototype, ctor);
        JSObjectUtil.putFunctionsFromContainer(realm, pluralRulesPrototype, PluralRulesPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putToStringTag(pluralRulesPrototype, TO_STRING_TAG);
        return pluralRulesPrototype;
    }

    @Override
    public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
        Shape initialShape = JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
        return initialShape;
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm, PluralRulesFunctionBuiltins.BUILTINS);
    }

    public static JSPluralRulesObject create(JSContext context, JSRealm realm) {
        InternalState state = new InternalState();
        JSObjectFactory factory = context.getPluralRulesFactory();
        JSPluralRulesObject obj = new JSPluralRulesObject(factory.getShape(realm), state);
        factory.initProto(obj, realm);
        return context.trackAllocation(obj);
    }

    public static PluralRules getPluralRulesProperty(JSDynamicObject obj) {
        return JSPluralRules.getInternalState(obj).getPluralRules();
    }

    public static LocalizedNumberFormatter getNumberFormatter(JSDynamicObject obj) {
        return JSPluralRules.getInternalState(obj).getNumberFormatter();
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString select(JSDynamicObject pluralRulesObj, Object n) {
        PluralRules pluralRules = JSPluralRules.getPluralRulesProperty(pluralRulesObj);
        LocalizedNumberFormatter numberFormatter = JSPluralRules.getNumberFormatter(pluralRulesObj);
        Number number = JSRuntime.toNumber(n);
        FormattedNumber formattedNumber = numberFormatter.format(number);
        return Strings.fromJavaString(pluralRules.select(formattedNumber));
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString selectRange(JSDynamicObject pluralRulesObj, double x, double y) {
        PluralRules pluralRules = JSPluralRules.getPluralRulesProperty(pluralRulesObj);
        LocalizedNumberRangeFormatter rangeFormatter = JSPluralRules.getInternalState(pluralRulesObj).getNumberRangeFormatter();
        FormattedNumberRange formattedRange = rangeFormatter.formatRange(x, y);
        return Strings.fromJavaString(pluralRules.select(formattedRange));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject resolvedOptions(JSContext context, JSRealm realm, JSDynamicObject pluralRulesObj) {
        InternalState state = JSPluralRules.getInternalState(pluralRulesObj);
        return state.toResolvedOptionsObject(context, realm);
    }

    public static InternalState getInternalState(JSDynamicObject obj) {
        assert (JSPluralRules.isJSPluralRules(obj));
        return ((JSPluralRulesObject)obj).getInternalState();
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getPluralRulesPrototype();
    }

    public static class InternalState
    extends JSNumberFormat.BasicInternalState {
        private LocalizedNumberFormatter numberFormatter;
        private LocalizedNumberRangeFormatter numberRangeFormatter;
        private String type;
        private PluralRules pluralRules;
        private final List<TruffleString> pluralCategories = new LinkedList<TruffleString>();

        @Override
        void fillResolvedOptions(JSContext context, JSRealm realm, JSDynamicObject result) {
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_LOCALE, Strings.fromJavaString(this.getLocale()), JSAttributes.getDefault());
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_TYPE, Strings.fromJavaString(this.type), JSAttributes.getDefault());
            super.fillResolvedOptions(context, realm, result);
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_PLURAL_CATEGORIES, JSRuntime.createArrayFromList(realm.getContext(), realm, this.pluralCategories), JSAttributes.getDefault());
            String roundingType = this.getRoundingType();
            String resolvedRoundingType = "morePrecision".equals(roundingType) || "lessPrecision".equals(roundingType) ? roundingType : "auto";
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_ROUNDING_PRIORITY, Strings.fromJavaString(resolvedRoundingType), JSAttributes.getDefault());
        }

        @CompilerDirectives.TruffleBoundary
        public void initializePluralRules() {
            this.pluralRules = PluralRules.forLocale(this.getJavaLocale(), "ordinal".equals(this.type) ? PluralRules.PluralType.ORDINAL : PluralRules.PluralType.CARDINAL);
            for (String keyword : this.pluralRules.getKeywords()) {
                this.pluralCategories.add(Strings.fromJavaString(keyword));
            }
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void initializeNumberFormatter() {
            super.initializeNumberFormatter();
            this.numberFormatter = this.getUnlocalizedFormatter().locale(this.getJavaLocale());
            this.numberRangeFormatter = (LocalizedNumberRangeFormatter)NumberRangeFormatter.withLocale(this.getJavaLocale()).numberFormatterBoth(this.getUnlocalizedFormatter());
        }

        public PluralRules getPluralRules() {
            return this.pluralRules;
        }

        public LocalizedNumberFormatter getNumberFormatter() {
            return this.numberFormatter;
        }

        public LocalizedNumberRangeFormatter getNumberRangeFormatter() {
            return this.numberRangeFormatter;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

