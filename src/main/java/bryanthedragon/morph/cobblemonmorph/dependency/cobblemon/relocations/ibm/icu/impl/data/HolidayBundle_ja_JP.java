
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.impl.data;

import com.cobblemon.mod.relocations.ibm.icu.util.Holiday;
import com.cobblemon.mod.relocations.ibm.icu.util.SimpleHoliday;
import java.util.ListResourceBundle;

public class HolidayBundle_ja_JP
extends ListResourceBundle {
    private static final Holiday[] fHolidays = new Holiday[]{new SimpleHoliday(1, 11, 0, "National Foundation Day")};
    private static final Object[][] fContents = new Object[][]{{"holidays", fHolidays}};

    @Override
    public synchronized Object[][] getContents() {
        return fContents;
    }
}

