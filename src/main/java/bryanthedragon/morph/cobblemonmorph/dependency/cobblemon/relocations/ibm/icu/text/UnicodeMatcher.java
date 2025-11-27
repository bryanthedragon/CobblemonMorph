
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.text.Replaceable;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;

public interface UnicodeMatcher {
    public static final int U_MISMATCH = 0;
    public static final int U_PARTIAL_MATCH = 1;
    public static final int U_MATCH = 2;
    public static final char ETHER = '\uffff';

    public int matches(Replaceable var1, int[] var2, int var3, boolean var4);

    public String toPattern(boolean var1);

    public boolean matchesIndexValue(int var1);

    public void addMatchSetTo(UnicodeSet var1);
}

