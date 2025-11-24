
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StaticUnicodeSets;
import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.ParsedNumber;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.SymbolMatcher;
import com.cobblemon.mod.relocations.ibm.icu.text.DecimalFormatSymbols;

public class PercentMatcher
extends SymbolMatcher {
    private static final PercentMatcher DEFAULT = new PercentMatcher();

    public static PercentMatcher getInstance(DecimalFormatSymbols symbols) {
        String symbolString = symbols.getPercentString();
        if (PercentMatcher.DEFAULT.uniSet.contains(symbolString)) {
            return DEFAULT;
        }
        return new PercentMatcher(symbolString);
    }

    private PercentMatcher(String symbolString) {
        super(symbolString, PercentMatcher.DEFAULT.uniSet);
    }

    private PercentMatcher() {
        super(StaticUnicodeSets.Key.PERCENT_SIGN);
    }

    @Override
    protected boolean isDisabled(ParsedNumber result) {
        return 0 != (result.flags & 2);
    }

    @Override
    protected void accept(StringSegment segment, ParsedNumber result) {
        result.flags |= 2;
        result.setCharsConsumed(segment);
    }

    public String toString() {
        return "<PercentMatcher>";
    }
}

