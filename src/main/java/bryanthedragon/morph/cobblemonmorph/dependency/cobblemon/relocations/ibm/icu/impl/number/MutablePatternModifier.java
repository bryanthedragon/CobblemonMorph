package com.cobblemon.mod.relocations.ibm.icu.impl.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedStringBuilder;
import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import com.cobblemon.mod.relocations.ibm.icu.number.NumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.text.DecimalFormatSymbols;
import com.cobblemon.mod.relocations.ibm.icu.text.NumberFormat;
import com.cobblemon.mod.relocations.ibm.icu.text.PluralRules;
import com.cobblemon.mod.relocations.ibm.icu.util.Currency;
import java.text.Format.Field;

public class MutablePatternModifier implements Modifier, AffixUtils.SymbolProvider, MicroPropsGenerator {
   final boolean isStrong;
   AffixPatternProvider patternInfo;
   NumberFormat.Field field;
   NumberFormatter.SignDisplay signDisplay;
   boolean perMilleReplacesPercent;
   boolean approximately;
   DecimalFormatSymbols symbols;
   NumberFormatter.UnitWidth unitWidth;
   Currency currency;
   PluralRules rules;
   Modifier.Signum signum;
   StandardPlural plural;
   MicroPropsGenerator parent;
   StringBuilder currentAffix;

   public MutablePatternModifier(boolean isStrong) {
      this.isStrong = isStrong;
   }

   public void setPatternInfo(AffixPatternProvider patternInfo, NumberFormat.Field field) {
      this.patternInfo = patternInfo;
      this.field = field;
   }

   public void setPatternAttributes(NumberFormatter.SignDisplay signDisplay, boolean perMille, boolean approximately) {
      this.signDisplay = signDisplay;
      this.perMilleReplacesPercent = perMille;
      this.approximately = approximately;
   }

   public void setSymbols(DecimalFormatSymbols symbols, Currency currency, NumberFormatter.UnitWidth unitWidth, PluralRules rules) {
      assert rules != null == this.needsPlurals();

      this.symbols = symbols;
      this.currency = currency;
      this.unitWidth = unitWidth;
      this.rules = rules;
   }

   public void setNumberProperties(Modifier.Signum signum, StandardPlural plural) {
      assert plural != null == this.needsPlurals();

      this.signum = signum;
      this.plural = plural;
   }

   public boolean needsPlurals() {
      return this.patternInfo.containsSymbolType(-8);
   }

   public MutablePatternModifier.ImmutablePatternModifier createImmutable() {
      FormattedStringBuilder a = new FormattedStringBuilder();
      FormattedStringBuilder b = new FormattedStringBuilder();
      if (!this.needsPlurals()) {
         this.setNumberProperties(Modifier.Signum.POS, null);
         Modifier positive = this.createConstantModifier(a, b);
         this.setNumberProperties(Modifier.Signum.POS_ZERO, null);
         Modifier posZero = this.createConstantModifier(a, b);
         this.setNumberProperties(Modifier.Signum.NEG_ZERO, null);
         Modifier negZero = this.createConstantModifier(a, b);
         this.setNumberProperties(Modifier.Signum.NEG, null);
         Modifier negative = this.createConstantModifier(a, b);
         AdoptingModifierStore pm = new AdoptingModifierStore(positive, posZero, negZero, negative);
         return new MutablePatternModifier.ImmutablePatternModifier(pm, null);
      } else {
         AdoptingModifierStore pm = new AdoptingModifierStore();

         for (StandardPlural plural : StandardPlural.VALUES) {
            this.setNumberProperties(Modifier.Signum.POS, plural);
            pm.setModifier(Modifier.Signum.POS, plural, this.createConstantModifier(a, b));
            this.setNumberProperties(Modifier.Signum.POS_ZERO, plural);
            pm.setModifier(Modifier.Signum.POS_ZERO, plural, this.createConstantModifier(a, b));
            this.setNumberProperties(Modifier.Signum.NEG_ZERO, plural);
            pm.setModifier(Modifier.Signum.NEG_ZERO, plural, this.createConstantModifier(a, b));
            this.setNumberProperties(Modifier.Signum.NEG, plural);
            pm.setModifier(Modifier.Signum.NEG, plural, this.createConstantModifier(a, b));
         }

         pm.freeze();
         return new MutablePatternModifier.ImmutablePatternModifier(pm, this.rules);
      }
   }

   private ConstantMultiFieldModifier createConstantModifier(FormattedStringBuilder a, FormattedStringBuilder b) {
      this.insertPrefix(a.clear(), 0);
      this.insertSuffix(b.clear(), 0);
      return (ConstantMultiFieldModifier)(this.patternInfo.hasCurrencySign()
         ? new CurrencySpacingEnabledModifier(a, b, !this.patternInfo.hasBody(), this.isStrong, this.symbols)
         : new ConstantMultiFieldModifier(a, b, !this.patternInfo.hasBody(), this.isStrong));
   }

   public MicroPropsGenerator addToChain(MicroPropsGenerator parent) {
      this.parent = parent;
      return this;
   }

   @Override
   public MicroProps processQuantity(DecimalQuantity fq) {
      MicroProps micros = this.parent.processQuantity(fq);
      if (micros.rounder != null) {
         micros.rounder.apply(fq);
      }

      if (micros.modMiddle != null) {
         return micros;
      } else {
         if (this.needsPlurals()) {
            StandardPlural pluralForm = RoundingUtils.getPluralSafe(micros.rounder, this.rules, fq);
            this.setNumberProperties(fq.signum(), pluralForm);
         } else {
            this.setNumberProperties(fq.signum(), null);
         }

         micros.modMiddle = this;
         return micros;
      }
   }

   @Override
   public int apply(FormattedStringBuilder output, int leftIndex, int rightIndex) {
      int prefixLen = this.insertPrefix(output, leftIndex);
      int suffixLen = this.insertSuffix(output, rightIndex + prefixLen);
      int overwriteLen = 0;
      if (!this.patternInfo.hasBody()) {
         overwriteLen = output.splice(leftIndex + prefixLen, rightIndex + prefixLen, "", 0, 0, null);
      }

      CurrencySpacingEnabledModifier.applyCurrencySpacing(output, leftIndex, prefixLen, rightIndex + prefixLen + overwriteLen, suffixLen, this.symbols);
      return prefixLen + overwriteLen + suffixLen;
   }

   @Override
   public int getPrefixLength() {
      this.prepareAffix(true);
      return AffixUtils.unescapedCount(this.currentAffix, true, this);
   }

   @Override
   public int getCodePointCount() {
      this.prepareAffix(true);
      int result = AffixUtils.unescapedCount(this.currentAffix, false, this);
      this.prepareAffix(false);
      return result + AffixUtils.unescapedCount(this.currentAffix, false, this);
   }

   @Override
   public boolean isStrong() {
      return this.isStrong;
   }

   @Override
   public boolean containsField(Field field) {
      assert false;

      return false;
   }

   @Override
   public Modifier.Parameters getParameters() {
      assert false;

      return null;
   }

   @Override
   public boolean semanticallyEquivalent(Modifier other) {
      assert false;

      return false;
   }

   private int insertPrefix(FormattedStringBuilder sb, int position) {
      this.prepareAffix(true);
      return AffixUtils.unescape(this.currentAffix, sb, position, this, this.field);
   }

   private int insertSuffix(FormattedStringBuilder sb, int position) {
      this.prepareAffix(false);
      return AffixUtils.unescape(this.currentAffix, sb, position, this, this.field);
   }

   private void prepareAffix(boolean isPrefix) {
      if (this.currentAffix == null) {
         this.currentAffix = new StringBuilder();
      }

      PatternStringUtils.patternInfoToStringBuilder(
         this.patternInfo,
         isPrefix,
         PatternStringUtils.resolveSignDisplay(this.signDisplay, this.signum),
         this.approximately,
         this.plural,
         this.perMilleReplacesPercent,
         this.currentAffix
      );
   }

   @Override
   public CharSequence getSymbol(int type) {
      switch (type) {
         case -10:
            return this.currency.getName(this.symbols.getULocale(), 3, null);
         case -9:
            return "�";
         case -8:
            assert this.plural != null;

            return this.currency.getName(this.symbols.getULocale(), 2, this.plural.getKeyword(), null);
         case -7:
            return this.currency.getCurrencyCode();
         case -6:
            return this.getCurrencySymbolForUnitWidth();
         case -5:
            return this.symbols.getPerMillString();
         case -4:
            return this.symbols.getPercentString();
         case -3:
            return this.symbols.getApproximatelySignString();
         case -2:
            return this.symbols.getPlusSignString();
         case -1:
            return this.symbols.getMinusSignString();
         default:
            throw new AssertionError();
      }
   }

   public String getCurrencySymbolForUnitWidth() {
      if (this.unitWidth == NumberFormatter.UnitWidth.ISO_CODE) {
         return this.currency.getCurrencyCode();
      } else if (this.unitWidth == NumberFormatter.UnitWidth.HIDDEN) {
         return "";
      } else {
         int selector;
         switch (this.unitWidth) {
            case SHORT:
               selector = 0;
               break;
            case NARROW:
               selector = 3;
               break;
            case FORMAL:
               selector = 4;
               break;
            case VARIANT:
               selector = 5;
               break;
            default:
               throw new AssertionError();
         }

         return this.currency.getName(this.symbols.getULocale(), selector, null);
      }
   }

   public static class ImmutablePatternModifier implements MicroPropsGenerator {
      final AdoptingModifierStore pm;
      final PluralRules rules;
      MicroPropsGenerator parent;

      ImmutablePatternModifier(AdoptingModifierStore pm, PluralRules rules) {
         this.pm = pm;
         this.rules = rules;
         this.parent = null;
      }

      public MutablePatternModifier.ImmutablePatternModifier addToChain(MicroPropsGenerator parent) {
         this.parent = parent;
         return this;
      }

      @Override
      public MicroProps processQuantity(DecimalQuantity quantity) {
         MicroProps micros = this.parent.processQuantity(quantity);
         if (micros.rounder != null) {
            micros.rounder.apply(quantity);
         }

         if (micros.modMiddle != null) {
            return micros;
         } else {
            this.applyToMicros(micros, quantity);
            return micros;
         }
      }

      public void applyToMicros(MicroProps micros, DecimalQuantity quantity) {
         if (this.rules == null) {
            micros.modMiddle = this.pm.getModifierWithoutPlural(quantity.signum());
         } else {
            StandardPlural pluralForm = RoundingUtils.getPluralSafe(micros.rounder, this.rules, quantity);
            micros.modMiddle = this.pm.getModifier(quantity.signum(), pluralForm);
         }
      }
   }
}
