
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.text.Replaceable;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;

interface UnicodeReplacer {
    public int replace(Replaceable var1, int var2, int var3, int[] var4);

    public String toReplacerPattern(boolean var1);

    public void addReplacementSetTo(UnicodeSet var1);
}

