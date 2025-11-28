package com.cobblemon.mod.relocations.ibm.icu.text;

import java.util.ArrayList;
import java.util.List;

class TransliterationRuleSet {
   private List<TransliterationRule> ruleVector = new ArrayList<>();
   private int maxContextLength = 0;
   private TransliterationRule[] rules;
   private int[] index;

   public TransliterationRuleSet() {
   }

   public int getMaximumContextLength() {
      return this.maxContextLength;
   }

   public void addRule(TransliterationRule rule) {
      this.ruleVector.add(rule);
      int len;
      if ((len = rule.getAnteContextLength()) > this.maxContextLength) {
         this.maxContextLength = len;
      }

      this.rules = null;
   }

   public void freeze() {
      int n = this.ruleVector.size();
      this.index = new int[257];
      List<TransliterationRule> v = new ArrayList<>(2 * n);
      int[] indexValue = new int[n];

      for (int j = 0; j < n; j++) {
         TransliterationRule r = this.ruleVector.get(j);
         indexValue[j] = r.getIndexValue();
      }

      for (int x = 0; x < 256; x++) {
         this.index[x] = v.size();

         for (int j = 0; j < n; j++) {
            if (indexValue[j] >= 0) {
               if (indexValue[j] == x) {
                  v.add(this.ruleVector.get(j));
               }
            } else {
               TransliterationRule r = this.ruleVector.get(j);
               if (r.matchesIndexValue(x)) {
                  v.add(r);
               }
            }
         }
      }

      this.index[256] = v.size();
      this.rules = new TransliterationRule[v.size()];
      v.toArray(this.rules);
      StringBuilder errors = null;

      for (int x = 0; x < 256; x++) {
         for (int jx = this.index[x]; jx < this.index[x + 1] - 1; jx++) {
            TransliterationRule r1 = this.rules[jx];

            for (int k = jx + 1; k < this.index[x + 1]; k++) {
               TransliterationRule r2 = this.rules[k];
               if (r1.masks(r2)) {
                  if (errors == null) {
                     errors = new StringBuilder();
                  } else {
                     errors.append("\n");
                  }

                  errors.append("Rule " + r1 + " masks " + r2);
               }
            }
         }
      }

      if (errors != null) {
         throw new IllegalArgumentException(errors.toString());
      }
   }

   public boolean transliterate(Replaceable text, Transliterator.Position pos, boolean incremental) {
      int indexByte = text.char32At(pos.start) & 0xFF;

      for (int i = this.index[indexByte]; i < this.index[indexByte + 1]; i++) {
         int m = this.rules[i].matchAndReplace(text, pos, incremental);
         switch (m) {
            case 1:
               return false;
            case 2:
               return true;
         }
      }

      pos.start = pos.start + UTF16.getCharCount(text.char32At(pos.start));
      return true;
   }

   String toRules(boolean escapeUnprintable) {
      int count = this.ruleVector.size();
      StringBuilder ruleSource = new StringBuilder();

      for (int i = 0; i < count; i++) {
         if (i != 0) {
            ruleSource.append('\n');
         }

         TransliterationRule r = this.ruleVector.get(i);
         ruleSource.append(r.toRule(escapeUnprintable));
      }

      return ruleSource.toString();
   }

   void addSourceTargetSet(UnicodeSet filter, UnicodeSet sourceSet, UnicodeSet targetSet) {
      UnicodeSet currentFilter = new UnicodeSet(filter);
      UnicodeSet revisiting = new UnicodeSet();
      int count = this.ruleVector.size();

      for (int i = 0; i < count; i++) {
         TransliterationRule r = this.ruleVector.get(i);
         r.addSourceTargetSet(currentFilter, sourceSet, targetSet, revisiting.clear());
         currentFilter.addAll(revisiting);
      }
   }
}
