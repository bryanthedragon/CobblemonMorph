
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.MicroProps;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.MicroPropsGenerator;
import com.cobblemon.mod.relocations.ibm.icu.number.Scale;

public class MultiplierFormatHandler
implements MicroPropsGenerator {
    final Scale multiplier;
    final MicroPropsGenerator parent;

    public MultiplierFormatHandler(Scale multiplier, MicroPropsGenerator parent) {
        this.multiplier = multiplier;
        this.parent = parent;
    }

    @Override
    public MicroProps processQuantity(DecimalQuantity quantity) {
        MicroProps micros = this.parent.processQuantity(quantity);
        this.multiplier.applyTo(quantity);
        return micros;
    }
}

