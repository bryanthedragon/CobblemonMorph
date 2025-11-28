package com.cobblemon.mod.relocations.ibm.icu.text;

class NullTransliterator extends Transliterator {
   static final String SHORT_ID = "Null";
   static final String _ID = "Any-Null";

   public NullTransliterator() {
      super("Any-Null", null);
   }

   @Override
   protected void handleTransliterate(Replaceable text, Transliterator.Position offsets, boolean incremental) {
      offsets.start = offsets.limit;
   }

   @Override
   public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
   }
}
