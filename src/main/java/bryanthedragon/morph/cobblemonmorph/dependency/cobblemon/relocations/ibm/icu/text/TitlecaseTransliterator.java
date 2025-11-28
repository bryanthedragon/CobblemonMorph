package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.UCaseProps;
import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;

class TitlecaseTransliterator extends Transliterator {
   static final String _ID = "Any-Title";
   private final ULocale locale;
   private final UCaseProps csp;
   private ReplaceableContextIterator iter;
   private StringBuilder result;
   private int caseLocale;
   SourceTargetUtility sourceTargetUtility = null;

   static void register() {
      Transliterator.registerFactory("Any-Title", new Transliterator.Factory() {
         @Override
         public Transliterator getInstance(String ID) {
            return new TitlecaseTransliterator(ULocale.US);
         }
      });
      registerSpecialInverse("Title", "Lower", false);
   }

   public TitlecaseTransliterator(ULocale loc) {
      super("Any-Title", null);
      this.locale = loc;
      this.setMaximumContextLength(2);
      this.csp = UCaseProps.INSTANCE;
      this.iter = new ReplaceableContextIterator();
      this.result = new StringBuilder();
      this.caseLocale = UCaseProps.getCaseLocale(this.locale);
   }

   @Override
   protected synchronized void handleTransliterate(Replaceable text, Transliterator.Position offsets, boolean isIncremental) {
      if (offsets.start < offsets.limit) {
         boolean doTitle = true;
         int start = offsets.start - 1;

         while (start >= offsets.contextStart) {
            int c = text.char32At(start);
            int type = this.csp.getTypeOrIgnorable(c);
            if (type > 0) {
               doTitle = false;
               break;
            }

            if (type == 0) {
               break;
            }

            start -= UTF16.getCharCount(c);
         }

         this.iter.setText(text);
         this.iter.setIndex(offsets.start);
         this.iter.setLimit(offsets.limit);
         this.iter.setContextLimits(offsets.contextStart, offsets.contextLimit);
         this.result.setLength(0);

         int cx;
         while ((cx = this.iter.nextCaseMapCP()) >= 0) {
            int typex = this.csp.getTypeOrIgnorable(cx);
            if (typex >= 0) {
               if (doTitle) {
                  cx = this.csp.toFullTitle(cx, this.iter, this.result, this.caseLocale);
               } else {
                  cx = this.csp.toFullLower(cx, this.iter, this.result, this.caseLocale);
               }

               doTitle = typex == 0;
               if (this.iter.didReachLimit() && isIncremental) {
                  offsets.start = this.iter.getCaseMapCPStart();
                  return;
               }

               if (cx >= 0) {
                  int delta;
                  if (cx <= 31) {
                     delta = this.iter.replace(this.result.toString());
                     this.result.setLength(0);
                  } else {
                     delta = this.iter.replace(UTF16.valueOf(cx));
                  }

                  if (delta != 0) {
                     offsets.limit += delta;
                     offsets.contextLimit += delta;
                  }
               }
            }
         }

         offsets.start = offsets.limit;
      }
   }

   @Override
   public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
      synchronized (this) {
         if (this.sourceTargetUtility == null) {
            this.sourceTargetUtility = new SourceTargetUtility(new Transform<String, String>() {
               public String transform(String source) {
                  return UCharacter.toTitleCase(TitlecaseTransliterator.this.locale, source, null);
               }
            });
         }
      }

      this.sourceTargetUtility.addSourceTargetSet(this, inputFilter, sourceSet, targetSet);
   }
}
