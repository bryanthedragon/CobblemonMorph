
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.data;

import com.cobblemon.mod.relocations.ibm.icu.util.EasterHoliday;
import com.cobblemon.mod.relocations.ibm.icu.util.Holiday;
import com.cobblemon.mod.relocations.ibm.icu.util.SimpleHoliday;
import java.util.ListResourceBundle;

public class HolidayBundle_en_GB
extends ListResourceBundle {
    private static final Holiday[] fHolidays = new Holiday[]{SimpleHoliday.NEW_YEARS_DAY, SimpleHoliday.MAY_DAY, new SimpleHoliday(4, 31, -2, "Spring Holiday"), new SimpleHoliday(7, 31, -2, "Summer Bank Holiday"), SimpleHoliday.CHRISTMAS, SimpleHoliday.BOXING_DAY, new SimpleHoliday(11, 31, -2, "Christmas Holiday"), EasterHoliday.GOOD_FRIDAY, EasterHoliday.EASTER_SUNDAY, EasterHoliday.EASTER_MONDAY};
    private static final Object[][] fContents = new Object[][]{{"holidays", fHolidays}, {"Labor Day", "Labour Day"}};

    @Override
    public synchronized Object[][] getContents() {
        return fContents;
    }
}

