
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.ParsedNumber;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.SymbolMatcher;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;

public class PaddingMatcher
extends SymbolMatcher
implements NumberParseMatcher.Flexible {
    public static PaddingMatcher getInstance(String padString) {
        return new PaddingMatcher(padString);
    }

    private PaddingMatcher(String symbolString) {
        super(symbolString, UnicodeSet.EMPTY);
    }

    @Override
    protected boolean isDisabled(ParsedNumber result) {
        return false;
    }

    @Override
    protected void accept(StringSegment segment, ParsedNumber result) {
    }

    public String toString() {
        return "<PaddingMatcher>";
    }
}

