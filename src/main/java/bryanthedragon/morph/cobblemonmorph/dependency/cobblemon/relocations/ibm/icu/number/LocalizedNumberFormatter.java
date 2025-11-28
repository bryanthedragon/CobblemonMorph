package com.cobblemon.mod.relocations.ibm.icu.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedStringBuilder;
import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.LocalizedNumberFormatterAsFormat;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.MacroProps;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.MicroProps;
import com.cobblemon.mod.relocations.ibm.icu.util.Measure;
import com.cobblemon.mod.relocations.ibm.icu.util.MeasureUnit;
import java.text.Format;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class LocalizedNumberFormatter extends NumberFormatterSettings<LocalizedNumberFormatter> {
   static final AtomicLongFieldUpdater<LocalizedNumberFormatter> callCount = AtomicLongFieldUpdater.newUpdater(
      LocalizedNumberFormatter.class, "callCountInternal"
   );
   volatile long callCountInternal;
   volatile LocalizedNumberFormatter savedWithUnit;
   volatile NumberFormatterImpl compiled;

   LocalizedNumberFormatter(NumberFormatterSettings<?> parent, int key, Object value) {
      super(parent, key, value);
   }

   public FormattedNumber format(long input) {
      return this.format(new DecimalQuantity_DualStorageBCD(input));
   }

   public FormattedNumber format(double input) {
      return this.format(new DecimalQuantity_DualStorageBCD(input));
   }

   public FormattedNumber format(Number input) {
      return this.format(new DecimalQuantity_DualStorageBCD(input));
   }

   public FormattedNumber format(Measure input) {
      DecimalQuantity fq = new DecimalQuantity_DualStorageBCD(input.getNumber());
      MeasureUnit unit = input.getUnit();
      FormattedStringBuilder string = new FormattedStringBuilder();
      MicroProps micros = this.formatImpl(fq, unit, string);
      return new FormattedNumber(string, fq, micros.outputUnit, micros.gender);
   }

   public Format toFormat() {
      return new LocalizedNumberFormatterAsFormat(this, this.resolve().loc);
   }

   private FormattedNumber format(DecimalQuantity fq) {
      FormattedStringBuilder string = new FormattedStringBuilder();
      MicroProps micros = this.formatImpl(fq, string);
      return new FormattedNumber(string, fq, micros.outputUnit, micros.gender);
   }

   @Deprecated
   public MicroProps formatImpl(DecimalQuantity fq, FormattedStringBuilder string) {
      return this.computeCompiled() ? this.compiled.format(fq, string) : NumberFormatterImpl.formatStatic(this.resolve(), fq, string);
   }

   @Deprecated
   public MicroProps formatImpl(DecimalQuantity fq, MeasureUnit unit, FormattedStringBuilder string) {
      if (Objects.equals(this.resolve().unit, unit)) {
         return this.formatImpl(fq, string);
      } else {
         LocalizedNumberFormatter withUnit = this.savedWithUnit;
         if (withUnit == null || !Objects.equals(withUnit.resolve().unit, unit)) {
            withUnit = new LocalizedNumberFormatter(this, 3, unit);
            this.savedWithUnit = withUnit;
         }

         return withUnit.formatImpl(fq, string);
      }
   }

   @Deprecated
   public String getAffixImpl(boolean isPrefix, boolean isNegative) {
      FormattedStringBuilder string = new FormattedStringBuilder();
      byte signum = (byte)(isNegative ? -1 : 1);
      StandardPlural plural = StandardPlural.OTHER;
      int prefixLength;
      if (this.computeCompiled()) {
         prefixLength = this.compiled.getPrefixSuffix(signum, plural, string);
      } else {
         prefixLength = NumberFormatterImpl.getPrefixSuffixStatic(this.resolve(), signum, plural, string);
      }

      return isPrefix ? string.subSequence(0, prefixLength).toString() : string.subSequence(prefixLength, string.length()).toString();
   }

   private boolean computeCompiled() {
      MacroProps macros = this.resolve();
      long currentCount = callCount.incrementAndGet(this);
      if (currentCount == macros.threshold) {
         this.compiled = new NumberFormatterImpl(macros);
         return true;
      } else {
         return this.compiled != null;
      }
   }

   LocalizedNumberFormatter create(int key, Object value) {
      return new LocalizedNumberFormatter(this, key, value);
   }
}
