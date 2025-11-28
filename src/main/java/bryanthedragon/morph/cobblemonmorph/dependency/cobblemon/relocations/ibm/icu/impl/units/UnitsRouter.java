package com.cobblemon.mod.relocations.ibm.icu.impl.units;

import com.cobblemon.mod.relocations.ibm.icu.impl.IllegalIcuArgumentException;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.MicroProps;
import com.cobblemon.mod.relocations.ibm.icu.number.Precision;
import com.cobblemon.mod.relocations.ibm.icu.util.MeasureUnit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UnitsRouter {
   private ArrayList<MeasureUnit> outputUnits_ = new ArrayList<>();
   private ArrayList<UnitsRouter.ConverterPreference> converterPreferences_ = new ArrayList<>();

   public UnitsRouter(String inputUnitIdentifier, String region, String usage) {
      this(MeasureUnitImpl.forIdentifier(inputUnitIdentifier), region, usage);
   }

   public UnitsRouter(MeasureUnitImpl inputUnit, String region, String usage) {
      UnitsData data = new UnitsData();
      String category = data.getCategory(inputUnit);
      UnitPreferences.UnitPreference[] unitPreferences = data.getPreferencesFor(category, usage, region);

      for (int i = 0; i < unitPreferences.length; i++) {
         UnitPreferences.UnitPreference preference = unitPreferences[i];
         MeasureUnitImpl complexTargetUnitImpl = MeasureUnitImpl.UnitsParser.parseForIdentifier(preference.getUnit());
         String precision = preference.getSkeleton();
         if (!precision.isEmpty() && !precision.startsWith("precision-increment")) {
            throw new AssertionError("Only `precision-increment` is allowed");
         }

         this.outputUnits_.add(complexTargetUnitImpl.build());
         this.converterPreferences_
            .add(new UnitsRouter.ConverterPreference(inputUnit, complexTargetUnitImpl, preference.getGeq(), precision, data.getConversionRates()));
      }
   }

   public UnitsRouter.RouteResult route(BigDecimal quantity, MicroProps micros) {
      Precision rounder = micros == null ? null : micros.rounder;
      UnitsRouter.ConverterPreference converterPreference = null;

      for (UnitsRouter.ConverterPreference itr : this.converterPreferences_) {
         converterPreference = itr;
         if (itr.converter.greaterThanOrEqual(quantity.abs(), itr.limit)) {
            break;
         }
      }

      assert converterPreference != null;

      assert converterPreference.precision != null;

      if (rounder != null && rounder instanceof Precision.BogusRounder) {
         Precision.BogusRounder bogus = (Precision.BogusRounder)rounder;
         if (converterPreference.precision.length() > 0) {
            rounder = bogus.into(parseSkeletonToPrecision(converterPreference.precision));
         } else {
            rounder = bogus.into(Precision.integer().withMinDigits(2));
         }
      }

      if (micros != null) {
         micros.rounder = rounder;
      }

      return new UnitsRouter.RouteResult(converterPreference.converter.convert(quantity, rounder), converterPreference.targetUnit);
   }

   private static Precision parseSkeletonToPrecision(String precisionSkeleton) {
      String kSkeletonPrefix = "precision-increment/";
      if (!precisionSkeleton.startsWith("precision-increment/")) {
         throw new IllegalIcuArgumentException("precisionSkeleton is only precision-increment");
      } else {
         String incrementValue = precisionSkeleton.substring("precision-increment/".length());
         return Precision.increment(new BigDecimal(incrementValue));
      }
   }

   public List<MeasureUnit> getOutputUnits() {
      return this.outputUnits_;
   }

   public static class ConverterPreference {
      final MeasureUnitImpl targetUnit;
      final ComplexUnitsConverter converter;
      final BigDecimal limit;
      final String precision;

      public ConverterPreference(MeasureUnitImpl source, MeasureUnitImpl targetUnit, String precision, ConversionRates conversionRates) {
         this(source, targetUnit, BigDecimal.valueOf(Double.MIN_VALUE), precision, conversionRates);
      }

      public ConverterPreference(MeasureUnitImpl source, MeasureUnitImpl targetUnit, BigDecimal limit, String precision, ConversionRates conversionRates) {
         this.converter = new ComplexUnitsConverter(source, targetUnit, conversionRates);
         this.limit = limit;
         this.precision = precision;
         this.targetUnit = targetUnit;
      }
   }

   public class RouteResult {
      public final ComplexUnitsConverter.ComplexConverterResult complexConverterResult;
      public final MeasureUnitImpl outputUnit;

      RouteResult(ComplexUnitsConverter.ComplexConverterResult complexConverterResult, MeasureUnitImpl outputUnit) {
         this.complexConverterResult = complexConverterResult;
         this.outputUnit = outputUnit;
      }
   }
}
