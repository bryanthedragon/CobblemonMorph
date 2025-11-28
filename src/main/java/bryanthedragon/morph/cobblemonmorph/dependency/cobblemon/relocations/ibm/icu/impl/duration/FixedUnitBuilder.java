package com.cobblemon.mod.relocations.ibm.icu.impl.duration;

class FixedUnitBuilder extends PeriodBuilderImpl {
   private TimeUnit unit;

   public static FixedUnitBuilder get(TimeUnit unit, BasicPeriodBuilderFactory.Settings settingsToUse) {
      return settingsToUse != null && (settingsToUse.effectiveSet() & 1 << unit.ordinal) != 0 ? new FixedUnitBuilder(unit, settingsToUse) : null;
   }

   FixedUnitBuilder(TimeUnit unit, BasicPeriodBuilderFactory.Settings settings) {
      super(settings);
      this.unit = unit;
   }

   @Override
   protected PeriodBuilder withSettings(BasicPeriodBuilderFactory.Settings settingsToUse) {
      return get(this.unit, settingsToUse);
   }

   @Override
   protected Period handleCreate(long duration, long referenceDate, boolean inPast) {
      if (this.unit == null) {
         return null;
      } else {
         long unitDuration = this.approximateDurationOf(this.unit);
         return Period.at((float)((double)duration / unitDuration), this.unit).inPast(inPast);
      }
   }
}
