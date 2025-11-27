
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalFormatProperties;
import com.cobblemon.mod.relocations.ibm.icu.number.LocalizedNumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.number.NumberPropertyMapper;
import com.cobblemon.mod.relocations.ibm.icu.number.NumberSkeletonImpl;
import com.cobblemon.mod.relocations.ibm.icu.number.UnlocalizedNumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.text.DecimalFormatSymbols;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.util.Locale;

public final class NumberFormatter {
    private static final UnlocalizedNumberFormatter BASE = new UnlocalizedNumberFormatter();
    static final long DEFAULT_THRESHOLD = 3L;

    private NumberFormatter() {
    }

    public static UnlocalizedNumberFormatter with() {
        return BASE;
    }

    public static LocalizedNumberFormatter withLocale(Locale locale) {
        return BASE.locale(locale);
    }

    public static LocalizedNumberFormatter withLocale(ULocale locale) {
        return BASE.locale(locale);
    }

    public static UnlocalizedNumberFormatter forSkeleton(String skeleton) {
        return NumberSkeletonImpl.getOrCreate(skeleton);
    }

    @Deprecated
    public static UnlocalizedNumberFormatter fromDecimalFormat(DecimalFormatProperties properties2, DecimalFormatSymbols symbols, DecimalFormatProperties exportedProperties) {
        return NumberPropertyMapper.create(properties2, symbols, exportedProperties);
    }

    public static enum TrailingZeroDisplay {
        AUTO,
        HIDE_IF_WHOLE;

    }

    public static enum DecimalSeparatorDisplay {
        AUTO,
        ALWAYS;

    }

    public static enum SignDisplay {
        AUTO,
        ALWAYS,
        NEVER,
        ACCOUNTING,
        ACCOUNTING_ALWAYS,
        EXCEPT_ZERO,
        ACCOUNTING_EXCEPT_ZERO,
        NEGATIVE,
        ACCOUNTING_NEGATIVE;

    }

    public static enum GroupingStrategy {
        OFF,
        MIN2,
        AUTO,
        ON_ALIGNED,
        THOUSANDS;

    }

    public static enum UnitWidth {
        NARROW,
        SHORT,
        FULL_NAME,
        ISO_CODE,
        FORMAL,
        VARIANT,
        HIDDEN;

    }

    public static enum RoundingPriority {
        RELAXED,
        STRICT;

    }
}

