
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.ParsedNumber;

public abstract class ValidationMatcher
implements NumberParseMatcher {
    @Override
    public boolean match(StringSegment segment, ParsedNumber result) {
        return false;
    }

    @Override
    public boolean smokeTest(StringSegment segment) {
        return false;
    }
}

