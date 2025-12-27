package com.oracle.truffle.js.runtime.builtins.intl;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.ICUResourceBundle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.ListFormatter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.SimpleFormatter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.ULocale;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.UResourceBundle;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.ListFormatFunctionBuiltins;
import com.oracle.truffle.js.builtins.intl.ListFormatPrototypeBuiltins;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class JSListFormat extends JSNonProxy implements JSConstructorFactory.WithFunctions, PrototypeSupplier {
   public static final TruffleString CLASS_NAME = Strings.constant("ListFormat");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("ListFormat.prototype");
   public static final TruffleString TO_STRING_TAG = Strings.constant("Intl.ListFormat");
   public static final JSListFormat INSTANCE = new JSListFormat();

   private JSListFormat() {
   }

   public static boolean isJSListFormat(Object obj) {
      return obj instanceof JSListFormatObject;
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
      JSObject listFormatPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(ctx, listFormatPrototype, ctor);
      JSObjectUtil.putFunctionsFromContainer(realm, listFormatPrototype, ListFormatPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(listFormatPrototype, TO_STRING_TAG);
      return listFormatPrototype;
   }

   @Override
   public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, ListFormatFunctionBuiltins.BUILTINS);
   }

   public static JSListFormatObject create(JSContext context, JSRealm realm) {
      JSListFormat.InternalState state = new JSListFormat.InternalState();
      JSObjectFactory factory = context.getListFormatFactory();
      JSListFormatObject obj = new JSListFormatObject(factory.getShape(realm), state);
      factory.initProto(obj, realm);
      return context.trackAllocation(obj);
   }

   @CompilerDirectives.TruffleBoundary
   public static void setLocale(JSContext ctx, JSListFormat.InternalState state, String[] locales) {
      Locale selectedLocale = IntlUtil.selectedLocale(ctx, locales);
      Locale strippedLocale = selectedLocale.stripExtensions();
      if (strippedLocale.toLanguageTag().equals("und")) {
         selectedLocale = ctx.getLocale();
         strippedLocale = selectedLocale.stripExtensions();
      }

      state.locale = strippedLocale.toLanguageTag();
      state.javaLocale = strippedLocale;
   }

   @CompilerDirectives.TruffleBoundary
   public static void setupInternalListFormatter(JSListFormat.InternalState state) {
      state.javaLocale = Locale.forLanguageTag(state.locale);
      state.listFormatter = createFormatter(state.javaLocale, getICUListFormatterStyle(state.type, state.style));
   }

   private static String getICUListFormatterStyle(String type, String style) {
      switch (type) {
         case "conjunction":
            switch (style) {
               case "long":
                  return "standard";
               case "narrow":
                  return "standard-narrow";
               case "short":
                  return "standard-short";
               default:
                  throw Errors.shouldNotReachHere(style);
            }
         case "disjunction":
            switch (style) {
               case "long":
                  return "or";
               case "narrow":
                  return "or-narrow";
               case "short":
                  return "or-short";
               default:
                  throw Errors.shouldNotReachHere(style);
            }
         case "unit":
            switch (style) {
               case "long":
                  return "unit";
               case "narrow":
                  return "unit-narrow";
               case "short":
                  return "unit-short";
               default:
                  throw Errors.shouldNotReachHere(style);
            }
         default:
            throw Errors.shouldNotReachHere(type);
      }
   }

   public static ListFormatter getListFormatterProperty(JSDynamicObject obj) {
      return getInternalState(obj).listFormatter;
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString format(JSDynamicObject listFormatObj, List<String> list) {
      ListFormatter listFormatter = getListFormatterProperty(listFormatObj);
      return Strings.fromJavaString(listFormatter.format(list));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject formatToParts(JSContext context, JSRealm realm, JSDynamicObject listFormatObj, List<String> list) {
      if (list.size() == 0) {
         return JSArray.createConstantEmptyArray(context, realm);
      } else {
         ListFormatter listFormatter = getListFormatterProperty(listFormatObj);
         String pattern = listFormatter.getPatternForNumItems(list.size());
         int[] offsets = new int[list.size()];
         SimpleFormatter simpleFormatter = SimpleFormatter.compile(pattern);
         StringBuilder formatted = new StringBuilder();
         simpleFormatter.formatAndAppend(formatted, offsets, list.toArray(new String[0]));
         int i = 0;
         int idx = 0;
         List<Object> resultParts = new ArrayList<>();

         for (String element : list) {
            int nextOffset = offsets[idx++];
            if (i < nextOffset) {
               resultParts.add(IntlUtil.makePart(context, realm, "literal", formatted.substring(i, nextOffset)));
               i = nextOffset;
            }

            if (i == nextOffset) {
               int elemLength = element.length();
               resultParts.add(IntlUtil.makePart(context, realm, "element", formatted.substring(i, i + elemLength)));
               i += elemLength;
            }
         }

         if (i < formatted.length()) {
            resultParts.add(IntlUtil.makePart(context, realm, "literal", formatted.substring(i, formatted.length())));
         }

         return JSArray.createConstant(context, realm, resultParts.toArray());
      }
   }

   private static ListFormatter createFormatter(Locale locale, String style) {
      ULocale ulocale = ULocale.forLocale(locale);
      ICUResourceBundle r = (ICUResourceBundle)UResourceBundle.getBundleInstance(null, ulocale);
      String end = r.getWithFallback("listPattern/" + style + "/end").getString();
      String middle = r.getWithFallback("listPattern/" + style + "/middle").getString();
      String two = r.getWithFallback("listPattern/" + style + "/2").getString();
      String start = r.getWithFallback("listPattern/" + style + "/start").getString();
      return new ListFormatter(two, start, middle, end);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject resolvedOptions(JSContext context, JSRealm realm, JSDynamicObject listFormatObj) {
      JSListFormat.InternalState state = getInternalState(listFormatObj);
      return state.toResolvedOptionsObject(context, realm);
   }

   public static JSListFormat.InternalState getInternalState(JSDynamicObject obj) {
      assert isJSListFormat(obj);

      return ((JSListFormatObject)obj).getInternalState();
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getListFormatPrototype();
   }

   public static class InternalState {
      private ListFormatter listFormatter;
      private String locale;
      private Locale javaLocale;
      private String type = "conjunction";
      private String style = "long";

      JSDynamicObject toResolvedOptionsObject(JSContext context, JSRealm realm) {
         JSDynamicObject result = JSOrdinary.create(context, realm);
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_LOCALE, Strings.fromJavaString(this.locale), JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_TYPE, Strings.fromJavaString(this.type), JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_STYLE, Strings.fromJavaString(this.style), JSAttributes.getDefault());
         return result;
      }

      public void setType(String type) {
         this.type = type;
      }

      public void setStyle(String style) {
         this.style = style;
      }
   }
}
