
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.impl.TimeZoneNamesImpl;
import com.cobblemon.mod.relocations.ibm.icu.text.TimeZoneNames;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;

public class TimeZoneNamesFactoryImpl
extends TimeZoneNames.Factory {
    @Override
    public TimeZoneNames getTimeZoneNames(ULocale locale) {
        return new TimeZoneNamesImpl(locale);
    }
}

