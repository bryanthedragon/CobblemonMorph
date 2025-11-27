
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.ParsedNumber;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.parse.ValidationMatcher;

public class RequireAffixValidator
extends ValidationMatcher {
    @Override
    public void postProcess(ParsedNumber result) {
        if (result.prefix == null || result.suffix == null) {
            result.flags |= 0x100;
        }
    }

    public String toString() {
        return "<RequireAffix>";
    }
}

