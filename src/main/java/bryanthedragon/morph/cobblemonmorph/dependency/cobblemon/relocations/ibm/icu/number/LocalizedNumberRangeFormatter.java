
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.cobblemon.mod.relocations.ibm.icu.number.FormattedNumberRange;
import com.cobblemon.mod.relocations.ibm.icu.number.NumberRangeFormatterImpl;
import com.cobblemon.mod.relocations.ibm.icu.number.NumberRangeFormatterSettings;

public class LocalizedNumberRangeFormatter
extends NumberRangeFormatterSettings<LocalizedNumberRangeFormatter> {
    private volatile NumberRangeFormatterImpl fImpl;

    LocalizedNumberRangeFormatter(NumberRangeFormatterSettings<?> parent, int key, Object value2) {
        super(parent, key, value2);
    }

    public FormattedNumberRange formatRange(int first, int second) {
        DecimalQuantity_DualStorageBCD dq1 = new DecimalQuantity_DualStorageBCD(first);
        DecimalQuantity_DualStorageBCD dq2 = new DecimalQuantity_DualStorageBCD(second);
        return this.formatImpl(dq1, dq2, first == second);
    }

    public FormattedNumberRange formatRange(double first, double second) {
        DecimalQuantity_DualStorageBCD dq1 = new DecimalQuantity_DualStorageBCD(first);
        DecimalQuantity_DualStorageBCD dq2 = new DecimalQuantity_DualStorageBCD(second);
        return this.formatImpl(dq1, dq2, first == second);
    }

    public FormattedNumberRange formatRange(Number first, Number second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Cannot format null values in range");
        }
        DecimalQuantity_DualStorageBCD dq1 = new DecimalQuantity_DualStorageBCD(first);
        DecimalQuantity_DualStorageBCD dq2 = new DecimalQuantity_DualStorageBCD(second);
        return this.formatImpl(dq1, dq2, first.equals(second));
    }

    FormattedNumberRange formatImpl(DecimalQuantity first, DecimalQuantity second, boolean equalBeforeRounding) {
        if (this.fImpl == null) {
            this.fImpl = new NumberRangeFormatterImpl(this.resolve());
        }
        return this.fImpl.format(first, second, equalBeforeRounding);
    }

    @Override
    LocalizedNumberRangeFormatter create(int key, Object value2) {
        return new LocalizedNumberRangeFormatter(this, key, value2);
    }
}

