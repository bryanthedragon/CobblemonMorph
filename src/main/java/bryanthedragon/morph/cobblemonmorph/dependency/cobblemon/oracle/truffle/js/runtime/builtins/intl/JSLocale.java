
package com.oracle.truffle.js.runtime.builtins.intl;

import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.LocalePrototypeBuiltins;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.builtins.intl.JSLocaleObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.Locale;

public final class JSLocale
extends JSNonProxy
implements JSConstructorFactory.Default,
PrototypeSupplier {
    public static final TruffleString CLASS_NAME = Strings.constant("Locale");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("Locale.prototype");
    public static final TruffleString TO_STRING_TAG = Strings.constant("Intl.Locale");
    public static final JSLocale INSTANCE = new JSLocale();

    private JSLocale() {
    }

    public static boolean isJSLocale(Object obj) {
        return obj instanceof JSLocaleObject;
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
        JSObject localePrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, localePrototype, ctor);
        JSObjectUtil.putToStringTag(localePrototype, TO_STRING_TAG);
        JSObjectUtil.putFunctionsFromContainer(realm, localePrototype, LocalePrototypeBuiltins.BUILTINS);
        JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_BASE_NAME);
        JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_CALENDAR);
        JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_CASE_FIRST);
        JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_COLLATION);
        JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_HOUR_CYCLE);
        JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_NUMERIC);
        JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_NUMBERING_SYSTEM);
        JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_LANGUAGE);
        JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_SCRIPT);
        JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_REGION);
        if (ctx.getEcmaScriptVersion() >= 14) {
            JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_CALENDARS);
            JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_COLLATIONS);
            JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_HOUR_CYCLES);
            JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_NUMBERING_SYSTEMS);
            JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_TIME_ZONES);
            JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_TEXT_INFO);
            JSLocale.putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_WEEK_INFO);
        }
        return localePrototype;
    }

    private static void putLocalePropertyAccessor(JSRealm realm, JSDynamicObject prototype, TruffleString name) {
        JSObjectUtil.putBuiltinAccessorProperty(prototype, name, realm.lookupAccessor(LocalePrototypeBuiltins.BUILTINS, name));
    }

    @Override
    public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm);
    }

    public static JSLocaleObject create(JSContext context, JSRealm realm) {
        InternalState state = new InternalState();
        JSObjectFactory factory = context.getLocaleFactory();
        JSLocaleObject obj = new JSLocaleObject(factory.getShape(realm), state);
        factory.initProto(obj, realm);
        return obj;
    }

    @CompilerDirectives.TruffleBoundary
    public static void setupInternalState(InternalState state, Locale locale) {
        state.locale = locale;
        state.calendar = locale.getUnicodeLocaleType("ca");
        state.caseFirst = locale.getUnicodeLocaleType("kf");
        state.collation = locale.getUnicodeLocaleType("co");
        state.hourCycle = locale.getUnicodeLocaleType("hc");
        String kn = locale.getUnicodeLocaleType("kn");
        state.numeric = "true".equals(kn) || "".equals(kn);
        state.numberingSystem = locale.getUnicodeLocaleType("nu");
    }

    public static InternalState getInternalState(JSDynamicObject localeObject) {
        assert (JSLocale.isJSLocale(localeObject));
        return ((JSLocaleObject)localeObject).getInternalState();
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getLocalePrototype();
    }

    public static class InternalState {
        private Locale locale;
        String calendar;
        String caseFirst;
        String collation;
        String hourCycle;
        boolean numeric;
        String numberingSystem;

        @CompilerDirectives.TruffleBoundary
        public ULocale getULocale() {
            return ULocale.forLocale(this.locale);
        }

        @CompilerDirectives.TruffleBoundary
        public String getLocale() {
            return IntlUtil.maybeAppendMissingLanguageSubTag(this.locale.toLanguageTag());
        }

        @CompilerDirectives.TruffleBoundary
        public String getBaseName() {
            return this.locale.stripExtensions().toLanguageTag();
        }

        public String getCalendar() {
            return this.calendar;
        }

        public String getCaseFirst() {
            return this.caseFirst;
        }

        public String getCollation() {
            return this.collation;
        }

        public String getHourCycle() {
            return this.hourCycle;
        }

        public boolean getNumeric() {
            return this.numeric;
        }

        public String getNumberingSystem() {
            return this.numberingSystem;
        }

        @CompilerDirectives.TruffleBoundary
        public String getLanguage() {
            return this.locale.getLanguage();
        }

        @CompilerDirectives.TruffleBoundary
        public String getScript() {
            return this.locale.getScript();
        }

        @CompilerDirectives.TruffleBoundary
        public String getRegion() {
            return this.locale.getCountry();
        }

        @CompilerDirectives.TruffleBoundary
        public String maximize() {
            ULocale max2 = ULocale.addLikelySubtags(ULocale.forLocale(this.locale));
            Locale.Builder builder = new Locale.Builder().setLocale(this.locale);
            builder.setLanguage(max2.getLanguage());
            builder.setScript(max2.getScript());
            builder.setRegion(max2.getCountry());
            return builder.build().toLanguageTag();
        }

        @CompilerDirectives.TruffleBoundary
        public String minimize() {
            ULocale max2 = ULocale.addLikelySubtags(ULocale.forLocale(this.locale));
            ULocale min2 = ULocale.minimizeSubtags(max2);
            Locale.Builder builder = new Locale.Builder().setLocale(this.locale);
            builder.setLanguage(min2.getLanguage());
            builder.setScript(min2.getScript());
            builder.setRegion(min2.getCountry());
            return builder.build().toLanguageTag();
        }
    }
}

