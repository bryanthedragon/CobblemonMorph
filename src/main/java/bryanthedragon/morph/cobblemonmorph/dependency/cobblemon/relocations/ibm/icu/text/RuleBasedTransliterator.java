package com.cobblemon.mod.relocations.ibm.icu.text;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class RuleBasedTransliterator extends Transliterator {
   private final RuleBasedTransliterator.Data data;

   RuleBasedTransliterator(String ID, RuleBasedTransliterator.Data data, UnicodeFilter filter) {
      super(ID, filter);
      this.data = data;
      this.setMaximumContextLength(data.ruleSet.getMaximumContextLength());
   }

   @Deprecated
   @Override
   protected void handleTransliterate(Replaceable text, Transliterator.Position index, boolean incremental) {
      synchronized (this.data) {
         int loopCount = 0;
         int loopLimit = index.limit - index.start << 4;
         if (loopLimit < 0) {
            loopLimit = Integer.MAX_VALUE;
         }

         while (index.start < index.limit && loopCount <= loopLimit && this.data.ruleSet.transliterate(text, index, incremental)) {
            loopCount++;
         }
      }
   }

   @Deprecated
   @Override
   public String toRules(boolean escapeUnprintable) {
      return this.data.ruleSet.toRules(escapeUnprintable);
   }

   @Deprecated
   @Override
   public void addSourceTargetSet(UnicodeSet filter, UnicodeSet sourceSet, UnicodeSet targetSet) {
      this.data.ruleSet.addSourceTargetSet(filter, sourceSet, targetSet);
   }

   @Deprecated
   public Transliterator safeClone() {
      UnicodeFilter filter = this.getFilter();
      if (filter != null && filter instanceof UnicodeSet) {
         filter = new UnicodeSet((UnicodeSet)filter);
      }

      return new RuleBasedTransliterator(this.getID(), this.data, filter);
   }

   static class Data {
      public TransliterationRuleSet ruleSet;
      Map<String, char[]> variableNames = new HashMap<>();
      Object[] variables;
      char variablesBase;

      public Data() {
         this.ruleSet = new TransliterationRuleSet();
      }

      public UnicodeMatcher lookupMatcher(int standIn) {
         int i = standIn - this.variablesBase;
         return i >= 0 && i < this.variables.length ? (UnicodeMatcher)this.variables[i] : null;
      }

      public UnicodeReplacer lookupReplacer(int standIn) {
         int i = standIn - this.variablesBase;
         return i >= 0 && i < this.variables.length ? (UnicodeReplacer)this.variables[i] : null;
      }
   }
}
