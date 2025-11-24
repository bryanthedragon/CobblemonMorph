
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.impl.data;

import com.cobblemon.mod.relocations.ibm.icu.util.EasterHoliday;
import com.cobblemon.mod.relocations.ibm.icu.util.Holiday;
import com.cobblemon.mod.relocations.ibm.icu.util.SimpleHoliday;
import java.util.ListResourceBundle;

public class HolidayBundle_it_IT
extends ListResourceBundle {
    private static final Holiday[] fHolidays = new Holiday[]{SimpleHoliday.NEW_YEARS_DAY, SimpleHoliday.EPIPHANY, new SimpleHoliday(3, 1, 0, "Liberation Day"), new SimpleHoliday(4, 1, 0, "Labor Day"), SimpleHoliday.ASSUMPTION, SimpleHoliday.ALL_SAINTS_DAY, SimpleHoliday.IMMACULATE_CONCEPTION, SimpleHoliday.CHRISTMAS, new SimpleHoliday(11, 26, 0, "St. Stephens Day"), SimpleHoliday.NEW_YEARS_EVE, EasterHoliday.EASTER_SUNDAY, EasterHoliday.EASTER_MONDAY};
    private static final Object[][] fContents = new Object[][]{{"holidays", fHolidays}};

    @Override
    public synchronized Object[][] getContents() {
        return fContents;
    }
}

