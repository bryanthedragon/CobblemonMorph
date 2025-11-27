
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.duration;

import com.cobblemon.mod.relocations.ibm.icu.impl.duration.DurationFormatterFactory;
import com.cobblemon.mod.relocations.ibm.icu.impl.duration.PeriodBuilderFactory;
import com.cobblemon.mod.relocations.ibm.icu.impl.duration.PeriodFormatterFactory;
import java.util.Collection;

public interface PeriodFormatterService {
    public DurationFormatterFactory newDurationFormatterFactory();

    public PeriodFormatterFactory newPeriodFormatterFactory();

    public PeriodBuilderFactory newPeriodBuilderFactory();

    public Collection<String> getAvailableLocaleNames();
}

