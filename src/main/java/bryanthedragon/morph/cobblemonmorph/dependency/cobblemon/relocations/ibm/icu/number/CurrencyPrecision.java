
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.number;

import com.cobblemon.mod.relocations.ibm.icu.number.Precision;
import com.cobblemon.mod.relocations.ibm.icu.util.Currency;

public abstract class CurrencyPrecision
extends Precision {
    CurrencyPrecision() {
    }

    public Precision withCurrency(Currency currency) {
        if (currency != null) {
            Precision retval = CurrencyPrecision.constructFromCurrency(this, currency);
            retval.trailingZeroDisplay = this.trailingZeroDisplay;
            return retval;
        }
        throw new IllegalArgumentException("Currency must not be null");
    }
}

