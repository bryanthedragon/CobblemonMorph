
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.MicroProps;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.MicroPropsGenerator;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.UsagePrefsHandler;
import com.cobblemon.mod.relocations.ibm.icu.impl.units.ComplexUnitsConverter;
import com.cobblemon.mod.relocations.ibm.icu.impl.units.ConversionRates;
import com.cobblemon.mod.relocations.ibm.icu.impl.units.MeasureUnitImpl;
import com.cobblemon.mod.relocations.ibm.icu.util.MeasureUnit;

public class UnitConversionHandler
implements MicroPropsGenerator {
    private final MicroPropsGenerator fParent;
    private MeasureUnit fOutputUnit;
    private ComplexUnitsConverter fComplexUnitConverter;

    public UnitConversionHandler(MeasureUnit targetUnit, MicroPropsGenerator parent) {
        this.fOutputUnit = targetUnit;
        this.fParent = parent;
        MeasureUnitImpl targetUnitImpl = MeasureUnitImpl.forIdentifier(targetUnit.getIdentifier());
        this.fComplexUnitConverter = new ComplexUnitsConverter(targetUnitImpl, new ConversionRates());
    }

    @Override
    public MicroProps processQuantity(DecimalQuantity quantity) {
        MicroProps result = this.fParent.processQuantity(quantity);
        quantity.roundToInfinity();
        ComplexUnitsConverter.ComplexConverterResult complexConverterResult = this.fComplexUnitConverter.convert(quantity.toBigDecimal(), result.rounder);
        result.outputUnit = this.fOutputUnit;
        UsagePrefsHandler.mixedMeasuresToMicros(complexConverterResult, quantity, result);
        return result;
    }
}

