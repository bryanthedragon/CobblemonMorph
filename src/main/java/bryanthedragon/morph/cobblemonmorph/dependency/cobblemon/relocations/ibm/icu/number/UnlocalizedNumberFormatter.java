
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number;

import com.cobblemon.mod.relocations.ibm.icu.number.LocalizedNumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.number.NumberFormatterSettings;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.util.Locale;

public class UnlocalizedNumberFormatter
extends NumberFormatterSettings<UnlocalizedNumberFormatter> {
    UnlocalizedNumberFormatter() {
        super(null, 14, new Long(3L));
    }

    UnlocalizedNumberFormatter(NumberFormatterSettings<?> parent, int key, Object value2) {
        super(parent, key, value2);
    }

    public LocalizedNumberFormatter locale(Locale locale) {
        return new LocalizedNumberFormatter(this, 1, ULocale.forLocale(locale));
    }

    public LocalizedNumberFormatter locale(ULocale locale) {
        return new LocalizedNumberFormatter(this, 1, locale);
    }

    @Override
    UnlocalizedNumberFormatter create(int key, Object value2) {
        return new UnlocalizedNumberFormatter(this, key, value2);
    }
}

