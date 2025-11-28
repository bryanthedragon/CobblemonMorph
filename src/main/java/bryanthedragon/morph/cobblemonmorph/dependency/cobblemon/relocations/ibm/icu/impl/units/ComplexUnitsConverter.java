package com.cobblemon.mod.relocations.ibm.icu.impl.units;

import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.cobblemon.mod.relocations.ibm.icu.number.Precision;
import com.cobblemon.mod.relocations.ibm.icu.util.Measure;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComplexUnitsConverter {
   public static final BigDecimal EPSILON = BigDecimal.valueOf(Math.ulp(1.0));
   public static final BigDecimal EPSILON_MULTIPLIER = BigDecimal.valueOf(1L).add(EPSILON);
   public ArrayList<UnitsConverter> unitsConverters_;
   public List<MeasureUnitImpl.MeasureUnitImplWithIndex> units_;
   private MeasureUnitImpl inputUnit_;

   public ComplexUnitsConverter(MeasureUnitImpl targetUnit, ConversionRates conversionRates) {
      this.units_ = targetUnit.extractIndividualUnitsWithIndices();

      assert !this.units_.isEmpty();

      this.inputUnit_ = this.units_.get(0).unitImpl;
      MeasureUnitImpl.MeasureUnitImplComparator comparator = new MeasureUnitImpl.MeasureUnitImplComparator(conversionRates);

      for (MeasureUnitImpl.MeasureUnitImplWithIndex unitWithIndex : this.units_) {
         if (comparator.compare(unitWithIndex.unitImpl, this.inputUnit_) > 0) {
            this.inputUnit_ = unitWithIndex.unitImpl;
         }
      }

      this.init(conversionRates);
   }

   public ComplexUnitsConverter(String inputUnitIdentifier, String outputUnitsIdentifier) {
      this(MeasureUnitImpl.forIdentifier(inputUnitIdentifier), MeasureUnitImpl.forIdentifier(outputUnitsIdentifier), new ConversionRates());
   }

   public ComplexUnitsConverter(MeasureUnitImpl inputUnit, MeasureUnitImpl outputUnits, ConversionRates conversionRates) {
      this.inputUnit_ = inputUnit;
      this.units_ = outputUnits.extractIndividualUnitsWithIndices();

      assert !this.units_.isEmpty();

      this.init(conversionRates);
   }

   private void init(ConversionRates conversionRates) {
      Collections.sort(this.units_, Collections.reverseOrder(new MeasureUnitImpl.MeasureUnitImplWithIndexComparator(conversionRates)));
      this.unitsConverters_ = new ArrayList<>();
      int i = 0;

      for (int n = this.units_.size(); i < n; i++) {
         if (i == 0) {
            this.unitsConverters_.add(new UnitsConverter(this.inputUnit_, this.units_.get(i).unitImpl, conversionRates));
         } else {
            this.unitsConverters_.add(new UnitsConverter(this.units_.get(i - 1).unitImpl, this.units_.get(i).unitImpl, conversionRates));
         }
      }
   }

   public boolean greaterThanOrEqual(BigDecimal quantity, BigDecimal limit) {
      assert !this.units_.isEmpty();

      return this.unitsConverters_.get(0).convert(quantity).multiply(EPSILON_MULTIPLIER).compareTo(limit) >= 0;
   }

   public ComplexUnitsConverter.ComplexConverterResult convert(BigDecimal quantity, Precision rounder) {
      BigInteger sign = BigInteger.ONE;
      if (quantity.compareTo(BigDecimal.ZERO) < 0) {
         quantity = quantity.abs();
         sign = sign.negate();
      }

      List<BigInteger> intValues = new ArrayList<>(this.unitsConverters_.size() - 1);
      int i = 0;

      for (int n = this.unitsConverters_.size(); i < n; i++) {
         quantity = this.unitsConverters_.get(i).convert(quantity);
         if (i < n - 1) {
            BigInteger flooredQuantity = quantity.multiply(EPSILON_MULTIPLIER).setScale(0, RoundingMode.FLOOR).toBigInteger();
            intValues.add(flooredQuantity);
            BigDecimal remainder = quantity.subtract(BigDecimal.valueOf(flooredQuantity.longValue()));
            if (remainder.compareTo(BigDecimal.ZERO) == -1) {
               quantity = BigDecimal.ZERO;
            } else {
               quantity = remainder;
            }
         }
      }

      quantity = this.applyRounder(intValues, quantity, rounder);
      List<Measure> measures = new ArrayList<>(this.unitsConverters_.size());

      for (int ix = 0; ix < this.unitsConverters_.size(); ix++) {
         measures.add(null);
      }

      int indexOfQuantity = -1;
      int ix = 0;

      for (int nx = this.unitsConverters_.size(); ix < nx; ix++) {
         if (ix < nx - 1) {
            Measure measure = new Measure(intValues.get(ix).multiply(sign), this.units_.get(ix).unitImpl.build());
            measures.set(this.units_.get(ix).index, measure);
         } else {
            indexOfQuantity = this.units_.get(ix).index;
            Measure measure = new Measure(quantity.multiply(BigDecimal.valueOf(sign.longValue())), this.units_.get(ix).unitImpl.build());
            measures.set(indexOfQuantity, measure);
         }
      }

      return new ComplexUnitsConverter.ComplexConverterResult(indexOfQuantity, measures);
   }

   private BigDecimal applyRounder(List<BigInteger> intValues, BigDecimal quantity, Precision rounder) {
      if (rounder == null) {
         return quantity;
      } else {
         DecimalQuantity quantityBCD = new DecimalQuantity_DualStorageBCD(quantity);
         rounder.apply(quantityBCD);
         quantity = quantityBCD.toBigDecimal();
         if (intValues.size() == 0) {
            return quantity;
         } else {
            int lastIndex = this.unitsConverters_.size() - 1;
            BigDecimal carry = this.unitsConverters_.get(lastIndex).convertInverse(quantity).multiply(EPSILON_MULTIPLIER).setScale(0, RoundingMode.FLOOR);
            if (carry.compareTo(BigDecimal.ZERO) <= 0) {
               return quantity;
            } else {
               quantity = quantity.subtract(this.unitsConverters_.get(lastIndex).convert(carry));
               intValues.set(lastIndex - 1, intValues.get(lastIndex - 1).add(carry.toBigInteger()));

               for (int j = lastIndex - 1; j > 0; j--) {
                  carry = this.unitsConverters_
                     .get(j)
                     .convertInverse(BigDecimal.valueOf(intValues.get(j).longValue()))
                     .multiply(EPSILON_MULTIPLIER)
                     .setScale(0, RoundingMode.FLOOR);
                  if (carry.compareTo(BigDecimal.ZERO) <= 0) {
                     break;
                  }

                  intValues.set(j, intValues.get(j).subtract(this.unitsConverters_.get(j).convert(carry).toBigInteger()));
                  intValues.set(j - 1, intValues.get(j - 1).add(carry.toBigInteger()));
               }

               return quantity;
            }
         }
      }
   }

   @Override
   public String toString() {
      return "ComplexUnitsConverter [unitsConverters_=" + this.unitsConverters_ + ", units_=" + this.units_ + "]";
   }

   public static class ComplexConverterResult {
      public final int indexOfQuantity;
      public final List<Measure> measures;

      ComplexConverterResult(int indexOfQuantity, List<Measure> measures) {
         this.indexOfQuantity = indexOfQuantity;
         this.measures = measures;
      }
   }
}
