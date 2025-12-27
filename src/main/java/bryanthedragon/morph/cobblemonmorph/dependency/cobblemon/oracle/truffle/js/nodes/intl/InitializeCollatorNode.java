package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSCollator;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.MissingResourceException;

public abstract class InitializeCollatorNode extends JavaScriptBaseNode {
   private final JSContext context;
   @Node.Child
   JSToCanonicalizedLocaleListNode toCanonicalizedLocaleListNode;
   @Node.Child
   CoerceOptionsToObjectNode coerceOptionsToObjectNode;
   @Node.Child
   GetStringOptionNode getUsageOption;
   @Node.Child
   GetStringOptionNode getLocaleMatcherOption;
   @Node.Child
   GetStringOptionNode getCollationOption;
   @Node.Child
   GetBooleanOptionNode getNumericOption;
   @Node.Child
   GetStringOptionNode getCaseFirstOption;
   @Node.Child
   GetStringOptionNode getSensitivityOption;
   @Node.Child
   GetBooleanOptionNode getIgnorePunctuationOption;
   private final BranchProfile errorBranch = BranchProfile.create();

   protected InitializeCollatorNode(JSContext context) {
      this.context = context;
      this.toCanonicalizedLocaleListNode = JSToCanonicalizedLocaleListNode.create(context);
      this.coerceOptionsToObjectNode = CoerceOptionsToObjectNodeGen.create(context);
      this.getUsageOption = GetStringOptionNode.create(context, IntlUtil.KEY_USAGE, new String[]{"sort", "search"}, "sort");
      this.getLocaleMatcherOption = GetStringOptionNode.create(context, IntlUtil.KEY_LOCALE_MATCHER, new String[]{"lookup", "best fit"}, "best fit");
      this.getCollationOption = GetStringOptionNode.create(context, IntlUtil.KEY_COLLATION, null, null);
      this.getNumericOption = GetBooleanOptionNode.create(context, IntlUtil.KEY_NUMERIC, null);
      this.getCaseFirstOption = GetStringOptionNode.create(context, IntlUtil.KEY_CASE_FIRST, new String[]{"upper", "lower", "false"}, null);
      this.getSensitivityOption = GetStringOptionNode.create(context, IntlUtil.KEY_SENSITIVITY, new String[]{"base", "accent", "case", "variant"}, null);
      this.getIgnorePunctuationOption = GetBooleanOptionNode.create(context, IntlUtil.KEY_IGNORE_PUNCTUATION, false);
   }

   public abstract JSDynamicObject executeInit(JSDynamicObject collator, Object locales, Object options);

   public static InitializeCollatorNode createInitalizeCollatorNode(JSContext context) {
      return InitializeCollatorNodeGen.create(context);
   }

   @Specialization
   public JSDynamicObject initializeCollator(JSDynamicObject collatorObj, Object localesArg, Object optionsArg) {
      try {
         JSCollator.InternalState state = JSCollator.getInternalState(collatorObj);
         String[] locales = this.toCanonicalizedLocaleListNode.executeLanguageTags(localesArg);
         JSDynamicObject options = this.coerceOptionsToObjectNode.execute(optionsArg);
         String usage = this.getUsageOption.executeValue(options);
         String optLocaleMatcher = this.getLocaleMatcherOption.executeValue(options);
         String optco = this.getCollationOption.executeValue(options);
         if (optco != null) {
            IntlUtil.validateUnicodeLocaleIdentifierType(optco, this.errorBranch);
         }

         Boolean optkn = this.getNumericOption.executeValue(options);
         String optkf = this.getCaseFirstOption.executeValue(options);
         String sensitivity = this.getSensitivityOption.executeValue(options);
         Boolean ignorePunctuation = this.getIgnorePunctuationOption.executeValue(options);
         JSCollator.initializeCollator(this.context, state, locales, usage, optLocaleMatcher, optco, optkn, optkf, sensitivity, ignorePunctuation);
         return collatorObj;
      } catch (MissingResourceException var14) {
         this.errorBranch.enter();
         throw Errors.createICU4JDataError(var14);
      }
   }
}
