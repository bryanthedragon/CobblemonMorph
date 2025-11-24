
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StaticUnicodeSets;
import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.ParsedNumber;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.SymbolMatcher;
import com.cobblemon.mod.relocations.ibm.icu.text.DecimalFormatSymbols;

public class PermilleMatcher
extends SymbolMatcher {
    private static final PermilleMatcher DEFAULT = new PermilleMatcher();

    public static PermilleMatcher getInstance(DecimalFormatSymbols symbols) {
        String symbolString = symbols.getPerMillString();
        if (PermilleMatcher.DEFAULT.uniSet.contains(symbolString)) {
            return DEFAULT;
        }
        return new PermilleMatcher(symbolString);
    }

    private PermilleMatcher(String symbolString) {
        super(symbolString, PermilleMatcher.DEFAULT.uniSet);
    }

    private PermilleMatcher() {
        super(StaticUnicodeSets.Key.PERMILLE_SIGN);
    }

    @Override
    protected boolean isDisabled(ParsedNumber result) {
        return 0 != (result.flags & 4);
    }

    @Override
    protected void accept(StringSegment segment, ParsedNumber result) {
        result.flags |= 4;
        result.setCharsConsumed(segment);
    }

    public String toString() {
        return "<PermilleMatcher>";
    }
}

