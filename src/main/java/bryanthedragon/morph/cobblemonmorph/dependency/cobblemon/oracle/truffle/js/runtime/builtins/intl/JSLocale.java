package com.oracle.truffle.js.runtime.builtins.intl;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.ULocale;
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
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.Locale;
import java.util.Locale.Builder;

public final class JSLocale extends JSNonProxy implements JSConstructorFactory.Default, PrototypeSupplier {
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
      putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_BASE_NAME);
      putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_CALENDAR);
      putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_CASE_FIRST);
      putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_COLLATION);
      putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_HOUR_CYCLE);
      putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_NUMERIC);
      putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_NUMBERING_SYSTEM);
      putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_LANGUAGE);
      putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_SCRIPT);
      putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_REGION);
      if (ctx.getEcmaScriptVersion() >= 14) {
         putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_CALENDARS);
         putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_COLLATIONS);
         putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_HOUR_CYCLES);
         putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_NUMBERING_SYSTEMS);
         putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_TIME_ZONES);
         putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_TEXT_INFO);
         putLocalePropertyAccessor(realm, localePrototype, IntlUtil.KEY_WEEK_INFO);
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
      JSLocale.InternalState state = new JSLocale.InternalState();
      JSObjectFactory factory = context.getLocaleFactory();
      JSLocaleObject obj = new JSLocaleObject(factory.getShape(realm), state);
      factory.initProto(obj, realm);
      return obj;
   }

   @CompilerDirectives.TruffleBoundary
   public static void setupInternalState(JSLocale.InternalState state, Locale locale) {
      state.locale = locale;
      state.calendar = locale.getUnicodeLocaleType("ca");
      state.caseFirst = locale.getUnicodeLocaleType("kf");
      state.collation = locale.getUnicodeLocaleType("co");
      state.hourCycle = locale.getUnicodeLocaleType("hc");
      String kn = locale.getUnicodeLocaleType("kn");
      state.numeric = "true".equals(kn) || "".equals(kn);
      state.numberingSystem = locale.getUnicodeLocaleType("nu");
   }

   public static JSLocale.InternalState getInternalState(JSDynamicObject localeObject) {
      assert isJSLocale(localeObject);

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
         ULocale max = ULocale.addLikelySubtags(ULocale.forLocale(this.locale));
         Builder builder = new Builder().setLocale(this.locale);
         builder.setLanguage(max.getLanguage());
         builder.setScript(max.getScript());
         builder.setRegion(max.getCountry());
         return builder.build().toLanguageTag();
      }

      @CompilerDirectives.TruffleBoundary
      public String minimize() {
         ULocale max = ULocale.addLikelySubtags(ULocale.forLocale(this.locale));
         ULocale min = ULocale.minimizeSubtags(max);
         Builder builder = new Builder().setLocale(this.locale);
         builder.setLanguage(min.getLanguage());
         builder.setScript(min.getScript());
         builder.setRegion(min.getCountry());
         return builder.build().toLanguageTag();
      }
   }
}
