package com.cobblemon.mod.relocations.ibm.icu.text;

abstract class CharsetRecognizer {
   abstract String getName();

   public String getLanguage() {
      return null;
   }

   abstract CharsetMatch match(CharsetDetector var1);
}
