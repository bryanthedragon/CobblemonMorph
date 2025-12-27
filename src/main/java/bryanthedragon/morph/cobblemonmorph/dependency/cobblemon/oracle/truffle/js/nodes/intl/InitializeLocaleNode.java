package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.intl.JSLocale;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Locale.Builder;

public abstract class InitializeLocaleNode extends JavaScriptBaseNode {
   @Node.Child
   CoerceOptionsToObjectNode coerceOptionsToObjectNode;
   @Node.Child
   GetStringOptionNode getLanguageOption;
   @Node.Child
   GetStringOptionNode getScriptOption;
   @Node.Child
   GetStringOptionNode getRegionOption;
   @Node.Child
   GetStringOptionNode getCalendarOption;
   @Node.Child
   GetStringOptionNode getCollationOption;
   @Node.Child
   GetStringOptionNode getHourCycleOption;
   @Node.Child
   GetStringOptionNode getCaseFirstOption;
   @Node.Child
   GetBooleanOptionNode getNumericOption;
   @Node.Child
   GetStringOptionNode getNumberingSystemOption;
   private final BranchProfile errorBranch = BranchProfile.create();

   protected InitializeLocaleNode(JSContext context) {
      this.coerceOptionsToObjectNode = CoerceOptionsToObjectNodeGen.create(context);
      this.getLanguageOption = GetStringOptionNode.create(context, IntlUtil.KEY_LANGUAGE, null, null);
      this.getScriptOption = GetStringOptionNode.create(context, IntlUtil.KEY_SCRIPT, null, null);
      this.getRegionOption = GetStringOptionNode.create(context, IntlUtil.KEY_REGION, null, null);
      this.getCalendarOption = GetStringOptionNode.create(context, IntlUtil.KEY_CALENDAR, null, null);
      this.getCollationOption = GetStringOptionNode.create(context, IntlUtil.KEY_COLLATION, null, null);
      this.getHourCycleOption = GetStringOptionNode.create(context, IntlUtil.KEY_HOUR_CYCLE, new String[]{"h11", "h12", "h23", "h24"}, null);
      this.getCaseFirstOption = GetStringOptionNode.create(context, IntlUtil.KEY_CASE_FIRST, new String[]{"upper", "lower", "false"}, null);
      this.getNumericOption = GetBooleanOptionNode.create(context, IntlUtil.KEY_NUMERIC, null);
      this.getNumberingSystemOption = GetStringOptionNode.create(context, IntlUtil.KEY_NUMBERING_SYSTEM, null, null);
   }

   public abstract JSDynamicObject executeInit(JSDynamicObject locale, Object tag, Object options);

   public static InitializeLocaleNode createInitalizeLocaleNode(JSContext context) {
      return InitializeLocaleNodeGen.create(context);
   }

   @Specialization
   public JSDynamicObject initializeLocaleUsingString(JSDynamicObject localeObject, TruffleString tagArg, Object optionsArg) {
      return this.initializeLocaleUsingJString(localeObject, Strings.toJavaString(tagArg), optionsArg);
   }

   private JSDynamicObject initializeLocaleUsingJString(JSDynamicObject localeObject, String tagArg, Object optionsArg) {
      try {
         JSDynamicObject options = this.coerceOptionsToObjectNode.execute(optionsArg);
         String tag = this.applyOptionsToTag(tagArg, options);
         String optCalendar = this.getCalendarOption.executeValue(options);
         if (optCalendar != null) {
            IntlUtil.validateUnicodeLocaleIdentifierType(optCalendar, this.errorBranch);
         }

         String optCollation = this.getCollationOption.executeValue(options);
         if (optCollation != null) {
            IntlUtil.validateUnicodeLocaleIdentifierType(optCollation, this.errorBranch);
         }

         String optHourCycle = this.getHourCycleOption.executeValue(options);
         String optCaseFirst = this.getCaseFirstOption.executeValue(options);
         Boolean optNumeric = this.getNumericOption.executeValue(options);
         String optNumberingSystem = this.getNumberingSystemOption.executeValue(options);
         if (optNumberingSystem != null) {
            IntlUtil.validateUnicodeLocaleIdentifierType(optNumberingSystem, this.errorBranch);
         }

         Locale locale = applyUnicodeExtensionToTag(tag, optCalendar, optCollation, optHourCycle, optCaseFirst, optNumeric, optNumberingSystem);
         JSLocale.InternalState state = JSLocale.getInternalState(localeObject);
         JSLocale.setupInternalState(state, locale);
         return localeObject;
      } catch (MissingResourceException var14) {
         this.errorBranch.enter();
         throw Errors.createICU4JDataError(var14);
      }
   }

   @Specialization(guards = "isJSLocale(tagArg)")
   public JSDynamicObject initializeLocaleUsingLocale(JSDynamicObject localeObject, JSDynamicObject tagArg, Object optionsArg) {
      JSLocale.InternalState state = JSLocale.getInternalState(tagArg);
      return this.initializeLocaleUsingJString(localeObject, state.getLocale(), optionsArg);
   }

   @Specialization(guards = {"isJSObject(tagArg)", "!isJSLocale(tagArg)"})
   public JSDynamicObject initializeLocaleUsingObject(
      JSDynamicObject localeObject, JSDynamicObject tagArg, Object optionsArg, @Cached("create()") JSToStringNode toStringNode
   ) {
      return this.initializeLocaleUsingString(localeObject, toStringNode.executeString(tagArg), optionsArg);
   }

   @Specialization(guards = {"!isJSObject(tagArg)", "!isString(tagArg)"})
   public JSDynamicObject initializeLocaleOther(JSDynamicObject localeObject, Object tagArg, Object optionsArg) {
      throw Errors.createTypeError("Tag should be a string or an object.");
   }

   private String applyOptionsToTag(String tag, JSDynamicObject options) {
      String canonicalizedTag = IntlUtil.validateAndCanonicalizeLanguageTag(tag);
      String optLanguage = this.getLanguageOption.executeValue(options);
      if (optLanguage != null) {
         IntlUtil.ensureIsStructurallyValidLanguageSubtag(optLanguage);
      }

      String optScript = this.getScriptOption.executeValue(options);
      if (optScript != null) {
         IntlUtil.ensureIsStructurallyValidScriptSubtag(optScript);
      }

      String optRegion = this.getRegionOption.executeValue(options);
      if (optRegion != null) {
         IntlUtil.ensureIsStructurallyValidRegionSubtag(optRegion);
      }

      return IntlUtil.validateAndCanonicalizeLanguageTag(applyOptionsToTag(canonicalizedTag, optLanguage, optScript, optRegion));
   }

   @CompilerDirectives.TruffleBoundary
   private static String applyOptionsToTag(String tag, String optLanguage, String optScript, String optRegion) {
      Builder builder = new Builder().setLanguageTag(tag);
      if (optLanguage != null) {
         builder.setLanguage(optLanguage);
      }

      if (optScript != null) {
         builder.setScript(optScript);
      }

      if (optRegion != null) {
         builder.setRegion(optRegion);
      }

      return IntlUtil.maybeAppendMissingLanguageSubTag(builder.build().toLanguageTag());
   }

   @CompilerDirectives.TruffleBoundary
   private static Locale applyUnicodeExtensionToTag(
      String tag, String optCalendar, String optCollation, String optHourCycle, String optCaseFirst, Boolean optNumeric, String optNumberingSystem
   ) {
      Builder builder = new Builder().setLanguageTag(tag);
      if (optCalendar != null) {
         setUnicodeLocaleKeywordHelper(builder, "ca", IntlUtil.normalizeCAType(optCalendar));
      }

      if (optCollation != null) {
         setUnicodeLocaleKeywordHelper(builder, "co", optCollation);
      }

      if (optHourCycle != null) {
         setUnicodeLocaleKeywordHelper(builder, "hc", optHourCycle);
      }

      if (optCaseFirst != null) {
         setUnicodeLocaleKeywordHelper(builder, "kf", optCaseFirst);
      }

      if (optNumeric != null) {
         setUnicodeLocaleKeywordHelper(builder, "kn", optNumeric.toString());
      }

      if (optNumberingSystem != null) {
         setUnicodeLocaleKeywordHelper(builder, "nu", optNumberingSystem);
      }

      return builder.build();
   }

   private static void setUnicodeLocaleKeywordHelper(Builder builder, String key, String type) {
      builder.setUnicodeLocaleKeyword(key, "true".equals(type) ? "" : type);
   }
}
