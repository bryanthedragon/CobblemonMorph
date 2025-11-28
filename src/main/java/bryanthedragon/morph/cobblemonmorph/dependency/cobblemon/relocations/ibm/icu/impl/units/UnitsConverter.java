package com.cobblemon.mod.relocations.ibm.icu.impl.units;

import com.cobblemon.mod.relocations.ibm.icu.impl.IllegalIcuArgumentException;
import com.cobblemon.mod.relocations.ibm.icu.util.MeasureUnit;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class UnitsConverter {
   private BigDecimal conversionRate;
   private boolean reciprocal;
   private BigDecimal offset;

   public UnitsConverter(String sourceIdentifier, String targetIdentifier) {
      this(MeasureUnitImpl.forIdentifier(sourceIdentifier), MeasureUnitImpl.forIdentifier(targetIdentifier), new ConversionRates());
   }

   public UnitsConverter(MeasureUnitImpl source, MeasureUnitImpl target, ConversionRates conversionRates) {
      UnitsConverter.Convertibility convertibility = extractConvertibility(source, target, conversionRates);
      if (convertibility != UnitsConverter.Convertibility.CONVERTIBLE && convertibility != UnitsConverter.Convertibility.RECIPROCAL) {
         throw new IllegalIcuArgumentException("input units must be convertible or reciprocal");
      } else {
         UnitsConverter.Factor sourceToBase = conversionRates.getFactorToBase(source);
         UnitsConverter.Factor targetToBase = conversionRates.getFactorToBase(target);
         if (convertibility == UnitsConverter.Convertibility.CONVERTIBLE) {
            this.conversionRate = sourceToBase.divide(targetToBase).getConversionRate();
         } else {
            assert convertibility == UnitsConverter.Convertibility.RECIPROCAL;

            this.conversionRate = sourceToBase.multiply(targetToBase).getConversionRate();
         }

         this.reciprocal = convertibility == UnitsConverter.Convertibility.RECIPROCAL;
         this.offset = conversionRates.getOffset(source, target, sourceToBase, targetToBase, convertibility);

         assert convertibility != UnitsConverter.Convertibility.RECIPROCAL || this.offset == BigDecimal.ZERO;
      }
   }

   public static UnitsConverter.Convertibility extractConvertibility(MeasureUnitImpl source, MeasureUnitImpl target, ConversionRates conversionRates) {
      ArrayList<SingleUnitImpl> sourceSingleUnits = conversionRates.extractBaseUnits(source);
      ArrayList<SingleUnitImpl> targetSingleUnits = conversionRates.extractBaseUnits(target);
      HashMap<String, Integer> dimensionMap = new HashMap<>();
      insertInMap(dimensionMap, sourceSingleUnits, 1);
      insertInMap(dimensionMap, targetSingleUnits, -1);
      if (areDimensionsZeroes(dimensionMap)) {
         return UnitsConverter.Convertibility.CONVERTIBLE;
      } else {
         insertInMap(dimensionMap, targetSingleUnits, 2);
         return areDimensionsZeroes(dimensionMap) ? UnitsConverter.Convertibility.RECIPROCAL : UnitsConverter.Convertibility.UNCONVERTIBLE;
      }
   }

   private static void insertInMap(HashMap<String, Integer> dimensionMap, ArrayList<SingleUnitImpl> singleUnits, int multiplier) {
      for (SingleUnitImpl singleUnit : singleUnits) {
         if (dimensionMap.containsKey(singleUnit.getSimpleUnitID())) {
            dimensionMap.put(singleUnit.getSimpleUnitID(), dimensionMap.get(singleUnit.getSimpleUnitID()) + singleUnit.getDimensionality() * multiplier);
         } else {
            dimensionMap.put(singleUnit.getSimpleUnitID(), singleUnit.getDimensionality() * multiplier);
         }
      }
   }

   private static boolean areDimensionsZeroes(HashMap<String, Integer> dimensionMap) {
      for (Integer value : dimensionMap.values()) {
         if (!value.equals(0)) {
            return false;
         }
      }

      return true;
   }

   public BigDecimal convert(BigDecimal inputValue) {
      BigDecimal result = inputValue.multiply(this.conversionRate).add(this.offset);
      if (this.reciprocal) {
         assert this.offset == BigDecimal.ZERO;

         if (result.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
         }

         result = BigDecimal.ONE.divide(result, MathContext.DECIMAL128);
      }

      return result;
   }

   public BigDecimal convertInverse(BigDecimal inputValue) {
      BigDecimal result = inputValue;
      if (this.reciprocal) {
         assert this.offset == BigDecimal.ZERO;

         if (inputValue.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
         }

         result = BigDecimal.ONE.divide(inputValue, MathContext.DECIMAL128);
      }

      return result.subtract(this.offset).divide(this.conversionRate, MathContext.DECIMAL128);
   }

   public UnitsConverter.ConversionInfo getConversionInfo() {
      UnitsConverter.ConversionInfo result = new UnitsConverter.ConversionInfo();
      result.conversionRate = this.conversionRate;
      result.offset = this.offset;
      result.reciprocal = this.reciprocal;
      return result;
   }

   @Override
   public String toString() {
      return "UnitsConverter [conversionRate=" + this.conversionRate + ", offset=" + this.offset + "]";
   }

   public static class ConversionInfo {
      public BigDecimal conversionRate;
      public BigDecimal offset;
      public boolean reciprocal;
   }

   public static enum Convertibility {
      CONVERTIBLE,
      RECIPROCAL,
      UNCONVERTIBLE;
   }

   static class Factor {
      private BigDecimal factorNum;
      private BigDecimal factorDen;
      private int exponentFtToM = 0;
      private int exponentPi = 0;
      private int exponentGravity = 0;
      private int exponentG = 0;
      private int exponentGalImpToM3 = 0;
      private int exponentLbToKg = 0;
      private int exponentGlucoseMolarMass = 0;
      private int exponentItemPerMole = 0;

      public Factor() {
         this.factorNum = BigDecimal.valueOf(1L);
         this.factorDen = BigDecimal.valueOf(1L);
      }

      public static UnitsConverter.Factor processFactor(String factor) {
         assert !factor.isEmpty();

         factor = factor.replaceAll("\\s+", "");
         String[] fractions = factor.split("/");

         assert fractions.length == 1 || fractions.length == 2;

         if (fractions.length == 1) {
            return processFactorWithoutDivision(fractions[0]);
         } else {
            UnitsConverter.Factor num = processFactorWithoutDivision(fractions[0]);
            UnitsConverter.Factor den = processFactorWithoutDivision(fractions[1]);
            return num.divide(den);
         }
      }

      private static UnitsConverter.Factor processFactorWithoutDivision(String factorWithoutDivision) {
         UnitsConverter.Factor result = new UnitsConverter.Factor();

         for (String poweredEntity : factorWithoutDivision.split(Pattern.quote("*"))) {
            result.addPoweredEntity(poweredEntity);
         }

         return result;
      }

      protected UnitsConverter.Factor copy() {
         UnitsConverter.Factor result = new UnitsConverter.Factor();
         result.factorNum = this.factorNum;
         result.factorDen = this.factorDen;
         result.exponentFtToM = this.exponentFtToM;
         result.exponentPi = this.exponentPi;
         result.exponentGravity = this.exponentGravity;
         result.exponentG = this.exponentG;
         result.exponentGalImpToM3 = this.exponentGalImpToM3;
         result.exponentLbToKg = this.exponentLbToKg;
         result.exponentGlucoseMolarMass = this.exponentGlucoseMolarMass;
         result.exponentItemPerMole = this.exponentItemPerMole;
         return result;
      }

      public BigDecimal getConversionRate() {
         UnitsConverter.Factor resultCollector = this.copy();
         resultCollector.multiply(new BigDecimal("0.3048"), this.exponentFtToM);
         resultCollector.multiply(new BigDecimal("411557987.0").divide(new BigDecimal("131002976.0"), MathContext.DECIMAL128), this.exponentPi);
         resultCollector.multiply(new BigDecimal("9.80665"), this.exponentGravity);
         resultCollector.multiply(new BigDecimal("6.67408E-11"), this.exponentG);
         resultCollector.multiply(new BigDecimal("0.00454609"), this.exponentGalImpToM3);
         resultCollector.multiply(new BigDecimal("0.45359237"), this.exponentLbToKg);
         resultCollector.multiply(new BigDecimal("180.1557"), this.exponentGlucoseMolarMass);
         resultCollector.multiply(new BigDecimal("6.02214076E+23"), this.exponentItemPerMole);
         return resultCollector.factorNum.divide(resultCollector.factorDen, MathContext.DECIMAL128);
      }

      private void multiply(BigDecimal value, int power) {
         if (power != 0) {
            BigDecimal absPoweredValue = value.pow(Math.abs(power), MathContext.DECIMAL128);
            if (power > 0) {
               this.factorNum = this.factorNum.multiply(absPoweredValue);
            } else {
               this.factorDen = this.factorDen.multiply(absPoweredValue);
            }
         }
      }

      public UnitsConverter.Factor applyPrefix(MeasureUnit.MeasurePrefix unitPrefix) {
         UnitsConverter.Factor result = this.copy();
         if (unitPrefix == MeasureUnit.MeasurePrefix.ONE) {
            return result;
         } else {
            int base = unitPrefix.getBase();
            int power = unitPrefix.getPower();
            BigDecimal absFactor = BigDecimal.valueOf((long)base).pow(Math.abs(power), MathContext.DECIMAL128);
            if (power < 0) {
               result.factorDen = this.factorDen.multiply(absFactor);
               return result;
            } else {
               result.factorNum = this.factorNum.multiply(absFactor);
               return result;
            }
         }
      }

      public UnitsConverter.Factor power(int power) {
         UnitsConverter.Factor result = new UnitsConverter.Factor();
         if (power == 0) {
            return result;
         } else {
            if (power > 0) {
               result.factorNum = this.factorNum.pow(power);
               result.factorDen = this.factorDen.pow(power);
            } else {
               result.factorNum = this.factorDen.pow(power * -1);
               result.factorDen = this.factorNum.pow(power * -1);
            }

            result.exponentFtToM = this.exponentFtToM * power;
            result.exponentPi = this.exponentPi * power;
            result.exponentGravity = this.exponentGravity * power;
            result.exponentG = this.exponentG * power;
            result.exponentGalImpToM3 = this.exponentGalImpToM3 * power;
            result.exponentLbToKg = this.exponentLbToKg * power;
            result.exponentGlucoseMolarMass = this.exponentGlucoseMolarMass * power;
            result.exponentItemPerMole = this.exponentItemPerMole * power;
            return result;
         }
      }

      public UnitsConverter.Factor divide(UnitsConverter.Factor other) {
         UnitsConverter.Factor result = new UnitsConverter.Factor();
         result.factorNum = this.factorNum.multiply(other.factorDen);
         result.factorDen = this.factorDen.multiply(other.factorNum);
         result.exponentFtToM = this.exponentFtToM - other.exponentFtToM;
         result.exponentPi = this.exponentPi - other.exponentPi;
         result.exponentGravity = this.exponentGravity - other.exponentGravity;
         result.exponentG = this.exponentG - other.exponentG;
         result.exponentGalImpToM3 = this.exponentGalImpToM3 - other.exponentGalImpToM3;
         result.exponentLbToKg = this.exponentLbToKg - other.exponentLbToKg;
         result.exponentGlucoseMolarMass = this.exponentGlucoseMolarMass - other.exponentGlucoseMolarMass;
         result.exponentItemPerMole = this.exponentItemPerMole - other.exponentItemPerMole;
         return result;
      }

      public UnitsConverter.Factor multiply(UnitsConverter.Factor other) {
         UnitsConverter.Factor result = new UnitsConverter.Factor();
         result.factorNum = this.factorNum.multiply(other.factorNum);
         result.factorDen = this.factorDen.multiply(other.factorDen);
         result.exponentFtToM = this.exponentFtToM + other.exponentFtToM;
         result.exponentPi = this.exponentPi + other.exponentPi;
         result.exponentGravity = this.exponentGravity + other.exponentGravity;
         result.exponentG = this.exponentG + other.exponentG;
         result.exponentGalImpToM3 = this.exponentGalImpToM3 + other.exponentGalImpToM3;
         result.exponentLbToKg = this.exponentLbToKg + other.exponentLbToKg;
         result.exponentGlucoseMolarMass = this.exponentGlucoseMolarMass + other.exponentGlucoseMolarMass;
         result.exponentItemPerMole = this.exponentItemPerMole + other.exponentItemPerMole;
         return result;
      }

      private void addPoweredEntity(String poweredEntity) {
         String[] entities = poweredEntity.split(Pattern.quote("^"));

         assert entities.length == 1 || entities.length == 2;

         int power = entities.length == 2 ? Integer.parseInt(entities[1]) : 1;
         this.addEntity(entities[0], power);
      }

      private void addEntity(String entity, int power) {
         if ("ft_to_m".equals(entity)) {
            this.exponentFtToM += power;
         } else if ("ft2_to_m2".equals(entity)) {
            this.exponentFtToM += 2 * power;
         } else if ("ft3_to_m3".equals(entity)) {
            this.exponentFtToM += 3 * power;
         } else if ("in3_to_m3".equals(entity)) {
            this.exponentFtToM += 3 * power;
            this.factorDen = this.factorDen.multiply(BigDecimal.valueOf(Math.pow(12.0, 3.0)));
         } else if ("gal_to_m3".equals(entity)) {
            this.factorNum = this.factorNum.multiply(BigDecimal.valueOf(231L));
            this.exponentFtToM += 3 * power;
            this.factorDen = this.factorDen.multiply(BigDecimal.valueOf(1728L));
         } else if ("gal_imp_to_m3".equals(entity)) {
            this.exponentGalImpToM3 += power;
         } else if ("G".equals(entity)) {
            this.exponentG += power;
         } else if ("gravity".equals(entity)) {
            this.exponentGravity += power;
         } else if ("lb_to_kg".equals(entity)) {
            this.exponentLbToKg += power;
         } else if ("glucose_molar_mass".equals(entity)) {
            this.exponentGlucoseMolarMass += power;
         } else if ("item_per_mole".equals(entity)) {
            this.exponentItemPerMole += power;
         } else if ("PI".equals(entity)) {
            this.exponentPi += power;
         } else {
            BigDecimal decimalEntity = new BigDecimal(entity).pow(power, MathContext.DECIMAL128);
            this.factorNum = this.factorNum.multiply(decimalEntity);
         }
      }
   }
}
