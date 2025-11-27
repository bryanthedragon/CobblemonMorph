
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.duration.impl;

import com.cobblemon.mod.relocations.ibm.icu.impl.duration.impl.PeriodFormatterData;
import java.util.Collection;

public abstract class PeriodFormatterDataService {
    public abstract PeriodFormatterData get(String var1);

    public abstract Collection<String> getAvailableLocales();
}

