
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StaticUnicodeSets;
import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.ParsedNumber;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.SymbolMatcher;
import com.cobblemon.mod.relocations.ibm.icu.text.DecimalFormatSymbols;

public class InfinityMatcher
extends SymbolMatcher {
    private static final InfinityMatcher DEFAULT = new InfinityMatcher();

    public static InfinityMatcher getInstance(DecimalFormatSymbols symbols) {
        String symbolString = symbols.getInfinity();
        if (InfinityMatcher.DEFAULT.uniSet.contains(symbolString)) {
            return DEFAULT;
        }
        return new InfinityMatcher(symbolString);
    }

    private InfinityMatcher(String symbolString) {
        super(symbolString, InfinityMatcher.DEFAULT.uniSet);
    }

    private InfinityMatcher() {
        super(StaticUnicodeSets.Key.INFINITY_SIGN);
    }

    @Override
    protected boolean isDisabled(ParsedNumber result) {
        return 0 != (result.flags & 0x80);
    }

    @Override
    protected void accept(StringSegment segment, ParsedNumber result) {
        result.flags |= 0x80;
        result.setCharsConsumed(segment);
    }

    public String toString() {
        return "<InfinityMatcher>";
    }
}

