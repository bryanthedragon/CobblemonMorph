package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenter;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.MissingResourceException;

public abstract class InitializeSegmenterNode extends JavaScriptBaseNode {
   private final JSContext context;
   @Node.Child
   JSToCanonicalizedLocaleListNode toCanonicalizedLocaleListNode;
   @Node.Child
   GetOptionsObjectNode getOptionsObjectNode;
   @Node.Child
   GetStringOptionNode getLocaleMatcherOption;
   @Node.Child
   GetStringOptionNode getGranularityOption;
   private final BranchProfile errorBranch = BranchProfile.create();

   protected InitializeSegmenterNode(JSContext context) {
      this.context = context;
      this.toCanonicalizedLocaleListNode = JSToCanonicalizedLocaleListNode.create(context);
      this.getOptionsObjectNode = GetOptionsObjectNodeGen.create(context);
      this.getGranularityOption = GetStringOptionNode.create(context, IntlUtil.KEY_GRANULARITY, new String[]{"grapheme", "word", "sentence"}, "grapheme");
      this.getLocaleMatcherOption = GetStringOptionNode.create(context, IntlUtil.KEY_LOCALE_MATCHER, new String[]{"lookup", "best fit"}, "best fit");
   }

   public abstract JSDynamicObject executeInit(JSDynamicObject collator, Object locales, Object options);

   public static InitializeSegmenterNode createInitalizeSegmenterNode(JSContext context) {
      return InitializeSegmenterNodeGen.create(context);
   }

   @Specialization
   public JSDynamicObject initializeSegmenter(JSDynamicObject segmenterObj, Object localesArg, Object optionsArg) {
      try {
         JSSegmenter.InternalState state = JSSegmenter.getInternalState(segmenterObj);
         String[] locales = this.toCanonicalizedLocaleListNode.executeLanguageTags(localesArg);
         Object options = this.getOptionsObjectNode.execute(optionsArg);
         this.getLocaleMatcherOption.executeValue(options);
         String optGranularity = this.getGranularityOption.executeValue(options);
         JSSegmenter.setLocale(this.context, state, locales);
         JSSegmenter.setupInternalBreakIterator(state, optGranularity);
         return segmenterObj;
      } catch (MissingResourceException var8) {
         this.errorBranch.enter();
         throw Errors.createICU4JDataError(var8);
      }
   }
}
