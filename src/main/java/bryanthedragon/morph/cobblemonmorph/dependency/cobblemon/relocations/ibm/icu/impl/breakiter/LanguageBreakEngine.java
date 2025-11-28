package com.cobblemon.mod.relocations.ibm.icu.impl.breakiter;

import java.text.CharacterIterator;

public interface LanguageBreakEngine {
   boolean handles(int var1);

   int findBreaks(CharacterIterator var1, int var2, int var3, DictionaryBreakEngine.DequeI var4, boolean var5);
}
