
package com.oracle.truffle.js.runtime.builtins.intl;

import com.cobblemon.mod.relocations.ibm.icu.text.DateTimePatternGenerator;
import com.cobblemon.mod.relocations.ibm.icu.text.DisplayContext;
import com.cobblemon.mod.relocations.ibm.icu.text.LocaleDisplayNames;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.DisplayNamesFunctionBuiltins;
import com.oracle.truffle.js.builtins.intl.DisplayNamesPrototypeBuiltins;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.builtins.intl.JSDisplayNamesObject;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.Locale;

public final class JSDisplayNames
extends JSNonProxy
implements JSConstructorFactory.WithFunctions,
PrototypeSupplier {
    public static final TruffleString CLASS_NAME = Strings.constant("DisplayNames");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("DisplayNames.prototype");
    public static final TruffleString TO_STRING_TAG = Strings.constant("Intl.DisplayNames");
    public static final JSDisplayNames INSTANCE = new JSDisplayNames();

    private JSDisplayNames() {
    }

    public static boolean isJSDisplayNames(Object obj) {
        return obj instanceof JSDisplayNamesObject;
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
        JSObject displayNamesPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, displayNamesPrototype, ctor);
        JSObjectUtil.putFunctionsFromContainer(realm, displayNamesPrototype, DisplayNamesPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putToStringTag(displayNamesPrototype, TO_STRING_TAG);
        return displayNamesPrototype;
    }

    @Override
    public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm, DisplayNamesFunctionBuiltins.BUILTINS);
    }

    public static JSDisplayNamesObject create(JSContext context, JSRealm realm) {
        InternalState state = new InternalState();
        JSObjectFactory factory = context.getDisplayNamesFactory();
        JSDisplayNamesObject obj = new JSDisplayNamesObject(factory.getShape(realm), state);
        factory.initProto(obj, realm);
        return obj;
    }

    @CompilerDirectives.TruffleBoundary
    public static void setupInternalState(JSContext ctx, InternalState state, String[] locales, String optStyle, String optType, String optFallback, String optLanguageDisplay) {
        Locale selectedLocale = IntlUtil.selectedLocale(ctx, locales);
        Locale strippedLocale = selectedLocale.stripExtensions();
        if (strippedLocale.toLanguageTag().equals("und")) {
            selectedLocale = ctx.getLocale();
            strippedLocale = selectedLocale.stripExtensions();
        }
        state.locale = strippedLocale.toLanguageTag();
        state.style = optStyle;
        state.type = optType;
        state.fallback = optFallback;
        String string = state.languageDisplay = "language".equals(optType) ? optLanguageDisplay : null;
        if ("dateTimeField".equals(optType)) {
            state.dateTimePatternGenerator = DateTimePatternGenerator.getInstance(strippedLocale);
            state.displayWidth = JSDisplayNames.styleDisplayWidth(optStyle);
        } else {
            DisplayContext fallbackCtx = JSDisplayNames.fallbackDisplayContext(optFallback);
            DisplayContext styleCtx = JSDisplayNames.styleDisplayContext(optStyle);
            DisplayContext languageDisplayCtx = JSDisplayNames.languageDisplayContext(optLanguageDisplay);
            state.displayNames = LocaleDisplayNames.getInstance(JSDisplayNames.convertOldISOCodes(strippedLocale), styleCtx, fallbackCtx, languageDisplayCtx);
        }
    }

    private static DisplayContext fallbackDisplayContext(String optFallback) {
        return "none".equals(optFallback) ? DisplayContext.NO_SUBSTITUTE : DisplayContext.SUBSTITUTE;
    }

    private static DisplayContext styleDisplayContext(String optStyle) {
        return "long".equals(optStyle) ? DisplayContext.LENGTH_FULL : DisplayContext.LENGTH_SHORT;
    }

    private static DisplayContext languageDisplayContext(String optLanguageDisplay) {
        return "dialect".equals(optLanguageDisplay) ? DisplayContext.DIALECT_NAMES : DisplayContext.STANDARD_NAMES;
    }

    private static DateTimePatternGenerator.DisplayWidth styleDisplayWidth(String optStyle) {
        DateTimePatternGenerator.DisplayWidth displayWidth;
        switch (optStyle) {
            case "long": {
                displayWidth = DateTimePatternGenerator.DisplayWidth.WIDE;
                break;
            }
            case "short": {
                displayWidth = DateTimePatternGenerator.DisplayWidth.ABBREVIATED;
                break;
            }
            case "narrow": {
                displayWidth = DateTimePatternGenerator.DisplayWidth.NARROW;
                break;
            }
            default: {
                throw Errors.shouldNotReachHere(optStyle);
            }
        }
        return displayWidth;
    }

    private static ULocale convertOldISOCodes(Locale locale) {
        ULocale.Builder builder = new ULocale.Builder();
        builder.setLocale(ULocale.forLocale(locale));
        switch (locale.getLanguage()) {
            case "iw": {
                builder.setLanguage("he");
                break;
            }
            case "ji": {
                builder.setLanguage("yi");
                break;
            }
            case "in": {
                builder.setLanguage("id");
            }
        }
        return builder.build();
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject resolvedOptions(JSContext context, JSRealm realm, JSDynamicObject displayNamesObject) {
        InternalState state = JSDisplayNames.getInternalState(displayNamesObject);
        return state.toResolvedOptionsObject(context, realm);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object of(JSDynamicObject displayNamesObject, String code) {
        String result;
        InternalState state = JSDisplayNames.getInternalState(displayNamesObject);
        String type = state.type;
        LocaleDisplayNames displayNames = state.displayNames;
        switch (type) {
            case "language": {
                result = displayNames.localeDisplayName(IntlUtil.validateAndCanonicalizeLanguageTag(code));
                break;
            }
            case "region": {
                IntlUtil.ensureIsStructurallyValidRegionSubtag(code);
                result = displayNames.regionDisplayName(code.toUpperCase());
                break;
            }
            case "script": {
                IntlUtil.ensureIsStructurallyValidScriptSubtag(code);
                String canonicalScript = Character.toUpperCase(code.charAt(0)) + code.substring(1).toLowerCase();
                result = displayNames.scriptDisplayName(canonicalScript);
                break;
            }
            case "calendar": {
                IntlUtil.ensureIsStructurallyValidCalendar(code);
                result = displayNames.keyValueDisplayName("calendar", JSDisplayNames.displayNamesFriendlyCalendar(code.toLowerCase()));
                break;
            }
            case "dateTimeField": {
                result = state.dateTimePatternGenerator.getFieldDisplayName(JSDisplayNames.toDateTimeFieldCode(code), state.displayWidth);
                break;
            }
            case "currency": {
                IntlUtil.ensureIsWellFormedCurrencyCode(code);
                String upperCaseCode = code.toUpperCase();
                result = displayNames.keyValueDisplayName("currency", upperCaseCode);
                if (!"none".equals(state.fallback) || !upperCaseCode.equals(result)) break;
                result = null;
                break;
            }
            default: {
                throw Errors.shouldNotReachHere(type);
            }
        }
        return result == null ? Undefined.instance : Strings.fromJavaString(result);
    }

    public static InternalState getInternalState(JSDynamicObject displayNamesObject) {
        assert (JSDisplayNames.isJSDisplayNames(displayNamesObject));
        return ((JSDisplayNamesObject)displayNamesObject).getInternalState();
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getDisplayNamesPrototype();
    }

    private static int toDateTimeFieldCode(String code) {
        int fieldCode;
        switch (code) {
            case "era": {
                fieldCode = 0;
                break;
            }
            case "year": {
                fieldCode = 1;
                break;
            }
            case "quarter": {
                fieldCode = 2;
                break;
            }
            case "month": {
                fieldCode = 3;
                break;
            }
            case "weekOfYear": {
                fieldCode = 4;
                break;
            }
            case "weekday": {
                fieldCode = 6;
                break;
            }
            case "day": {
                fieldCode = 7;
                break;
            }
            case "dayPeriod": {
                fieldCode = 10;
                break;
            }
            case "hour": {
                fieldCode = 11;
                break;
            }
            case "minute": {
                fieldCode = 12;
                break;
            }
            case "second": {
                fieldCode = 13;
                break;
            }
            case "timeZoneName": {
                fieldCode = 15;
                break;
            }
            default: {
                throw Errors.createRangeErrorInvalidDateTimeField(code);
            }
        }
        return fieldCode;
    }

    private static String displayNamesFriendlyCalendar(String calendar) {
        if ("gregory".equals(calendar)) {
            return "gregorian";
        }
        if ("ethioaa".equals(calendar)) {
            return "ethiopic-amete-alem";
        }
        return calendar;
    }

    public static class InternalState {
        String locale;
        String style;
        String type;
        String fallback;
        String languageDisplay;
        LocaleDisplayNames displayNames;
        DateTimePatternGenerator dateTimePatternGenerator;
        DateTimePatternGenerator.DisplayWidth displayWidth;

        JSObject toResolvedOptionsObject(JSContext context, JSRealm realm) {
            JSObject result = JSOrdinary.create(context, realm);
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_LOCALE, Strings.fromJavaString(this.locale), JSAttributes.getDefault());
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_STYLE, Strings.fromJavaString(this.style), JSAttributes.getDefault());
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_TYPE, Strings.fromJavaString(this.type), JSAttributes.getDefault());
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_FALLBACK, Strings.fromJavaString(this.fallback), JSAttributes.getDefault());
            if (this.languageDisplay != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_LANGUAGE_DISPLAY, Strings.fromJavaString(this.languageDisplay), JSAttributes.getDefault());
            }
            return result;
        }
    }
}

