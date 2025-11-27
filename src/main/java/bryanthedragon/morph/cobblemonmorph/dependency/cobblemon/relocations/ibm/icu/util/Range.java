
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.util.DateRule;
import java.util.Date;

class Range {
    public Date start;
    public DateRule rule;

    public Range(Date start2, DateRule rule) {
        this.start = start2;
        this.rule = rule;
    }
}

