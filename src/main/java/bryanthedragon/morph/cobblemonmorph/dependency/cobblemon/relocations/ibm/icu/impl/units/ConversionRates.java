package com.cobblemon.mod.relocations.ibm.icu.impl.units;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.UResource;
import com.cobblemon.mod.relocations.ibm.icu.util.MeasureUnit;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;

public class ConversionRates {
   private HashMap<String, ConversionRates.ConversionRateInfo> mapToConversionRate;

   public ConversionRates() {
      ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "units");
      ConversionRates.ConversionRatesSink sink = new ConversionRates.ConversionRatesSink();
      resource.getAllItemsWithFallback("convertUnits", sink);
      this.mapToConversionRate = sink.getMapToConversionRate();
   }

   private UnitsConverter.Factor getFactorToBase(SingleUnitImpl singleUnit) {
      int power = singleUnit.getDimensionality();
      MeasureUnit.MeasurePrefix unitPrefix = singleUnit.getPrefix();
      UnitsConverter.Factor result = UnitsConverter.Factor.processFactor(this.mapToConversionRate.get(singleUnit.getSimpleUnitID()).getConversionRate());
      return result.applyPrefix(unitPrefix).power(power);
   }

   public UnitsConverter.Factor getFactorToBase(MeasureUnitImpl measureUnit) {
      UnitsConverter.Factor result = new UnitsConverter.Factor();

      for (SingleUnitImpl singleUnit : measureUnit.getSingleUnits()) {
         result = result.multiply(this.getFactorToBase(singleUnit));
      }

      return result;
   }

   protected BigDecimal getOffset(
      MeasureUnitImpl source,
      MeasureUnitImpl target,
      UnitsConverter.Factor sourceToBase,
      UnitsConverter.Factor targetToBase,
      UnitsConverter.Convertibility convertibility
   ) {
      if (convertibility != UnitsConverter.Convertibility.CONVERTIBLE) {
         return BigDecimal.valueOf(0L);
      } else if (this.checkSimpleUnit(source) && this.checkSimpleUnit(target)) {
         String sourceSimpleIdentifier = source.getSingleUnits().get(0).getSimpleUnitID();
         String targetSimpleIdentifier = target.getSingleUnits().get(0).getSimpleUnitID();
         BigDecimal sourceOffset = this.mapToConversionRate.get(sourceSimpleIdentifier).getOffset();
         BigDecimal targetOffset = this.mapToConversionRate.get(targetSimpleIdentifier).getOffset();
         return sourceOffset.subtract(targetOffset).divide(targetToBase.getConversionRate(), MathContext.DECIMAL128);
      } else {
         return BigDecimal.valueOf(0L);
      }
   }

   public MeasureUnitImpl extractCompoundBaseUnit(MeasureUnitImpl measureUnit) {
      ArrayList<SingleUnitImpl> baseUnits = this.extractBaseUnits(measureUnit);
      MeasureUnitImpl result = new MeasureUnitImpl();

      for (SingleUnitImpl baseUnit : baseUnits) {
         result.appendSingleUnit(baseUnit);
      }

      return result;
   }

   public ArrayList<SingleUnitImpl> extractBaseUnits(MeasureUnitImpl measureUnitImpl) {
      ArrayList<SingleUnitImpl> result = new ArrayList<>();

      for (SingleUnitImpl singleUnit : measureUnitImpl.getSingleUnits()) {
         result.addAll(this.extractBaseUnits(singleUnit));
      }

      return result;
   }

   public ArrayList<SingleUnitImpl> extractBaseUnits(SingleUnitImpl singleUnit) {
      String target = this.mapToConversionRate.get(singleUnit.getSimpleUnitID()).getTarget();
      MeasureUnitImpl targetImpl = MeasureUnitImpl.UnitsParser.parseForIdentifier(target);
      targetImpl.applyDimensionality(singleUnit.getDimensionality());
      return targetImpl.getSingleUnits();
   }

   private boolean checkSimpleUnit(MeasureUnitImpl measureUnitImpl) {
      if (measureUnitImpl.getComplexity() != MeasureUnit.Complexity.SINGLE) {
         return false;
      } else {
         SingleUnitImpl singleUnit = measureUnitImpl.getSingleUnits().get(0);
         return singleUnit.getPrefix() != MeasureUnit.MeasurePrefix.ONE ? false : singleUnit.getDimensionality() == 1;
      }
   }

   public static class ConversionRateInfo {
      private final String simpleUnit;
      private final String target;
      private final String conversionRate;
      private final BigDecimal offset;

      public ConversionRateInfo(String simpleUnit, String target, String conversionRate, String offset) {
         this.simpleUnit = simpleUnit;
         this.target = target;
         this.conversionRate = conversionRate;
         this.offset = forNumberWithDivision(offset);
      }

      private static BigDecimal forNumberWithDivision(String numberWithDivision) {
         String[] numbers = numberWithDivision.split("/");

         assert numbers.length <= 2;

         return numbers.length == 1 ? new BigDecimal(numbers[0]) : new BigDecimal(numbers[0]).divide(new BigDecimal(numbers[1]), MathContext.DECIMAL128);
      }

      public String getTarget() {
         return this.target;
      }

      public BigDecimal getOffset() {
         return this.offset;
      }

      public String getConversionRate() {
         return this.conversionRate;
      }
   }

   public static class ConversionRatesSink extends UResource.Sink {
      private HashMap<String, ConversionRates.ConversionRateInfo> mapToConversionRate = new HashMap<>();

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         assert "convertUnits".equals(key.toString());

         UResource.Table conversionRateTable = value.getTable();

         for (int i = 0; conversionRateTable.getKeyAndValue(i, key, value); i++) {
            assert value.getType() == 2;

            String simpleUnit = key.toString();
            UResource.Table simpleUnitConversionInfo = value.getTable();
            String target = null;
            String factor = null;
            String offset = "0";

            for (int j = 0; simpleUnitConversionInfo.getKeyAndValue(j, key, value); j++) {
               assert value.getType() == 0;

               String keyString = key.toString();
               String valueString = value.toString().replaceAll(" ", "");
               if ("target".equals(keyString)) {
                  target = valueString;
               } else if ("factor".equals(keyString)) {
                  factor = valueString;
               } else if ("offset".equals(keyString)) {
                  offset = valueString;
               } else {
                  assert false : "The key must be target, factor or offset";
               }
            }

            assert target != null;

            assert factor != null;

            this.mapToConversionRate.put(simpleUnit, new ConversionRates.ConversionRateInfo(simpleUnit, target, factor, offset));
         }
      }

      public HashMap<String, ConversionRates.ConversionRateInfo> getMapToConversionRate() {
         return this.mapToConversionRate;
      }
   }
}
