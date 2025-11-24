
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.util.Currency;
import com.cobblemon.mod.relocations.ibm.icu.util.Measure;

public class CurrencyAmount
extends Measure {
    public CurrencyAmount(Number number, Currency currency) {
        super(number, currency);
    }

    public CurrencyAmount(double number, Currency currency) {
        super(new Double(number), currency);
    }

    public CurrencyAmount(Number number, java.util.Currency currency) {
        this(number, Currency.fromJavaCurrency(currency));
    }

    public CurrencyAmount(double number, java.util.Currency currency) {
        this(number, Currency.fromJavaCurrency(currency));
    }

    public Currency getCurrency() {
        return (Currency)this.getUnit();
    }
}

