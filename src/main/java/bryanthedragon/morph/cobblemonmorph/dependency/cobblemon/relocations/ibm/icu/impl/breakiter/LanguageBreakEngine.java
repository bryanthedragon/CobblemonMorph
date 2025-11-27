
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.breakiter;

import com.cobblemon.mod.relocations.ibm.icu.impl.breakiter.DictionaryBreakEngine;
import java.text.CharacterIterator;

public interface LanguageBreakEngine {
    public boolean handles(int var1);

    public int findBreaks(CharacterIterator var1, int var2, int var3, DictionaryBreakEngine.DequeI var4, boolean var5);
}

