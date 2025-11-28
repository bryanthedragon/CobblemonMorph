package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.number.AffixUtils;

public class AffixPatternMatcher extends SeriesMatcher implements AffixUtils.TokenConsumer {
   private final String affixPattern;
   private AffixTokenMatcherFactory factory;
   private IgnorablesMatcher ignorables;
   private int lastTypeOrCp;

   private AffixPatternMatcher(String affixPattern) {
      this.affixPattern = affixPattern;
   }

   public static AffixPatternMatcher fromAffixPattern(String affixPattern, AffixTokenMatcherFactory factory, int parseFlags) {
      if (affixPattern.isEmpty()) {
         return null;
      } else {
         AffixPatternMatcher series = new AffixPatternMatcher(affixPattern);
         series.factory = factory;
         series.ignorables = 0 != (parseFlags & 512) ? null : factory.ignorables();
         series.lastTypeOrCp = 0;
         AffixUtils.iterateWithConsumer(affixPattern, series);
         series.factory = null;
         series.ignorables = null;
         series.lastTypeOrCp = 0;
         series.freeze();
         return series;
      }
   }

   @Override
   public void consumeToken(int typeOrCp) {
      if (this.ignorables != null && this.length() > 0 && (this.lastTypeOrCp < 0 || !this.ignorables.getSet().contains(this.lastTypeOrCp))) {
         this.addMatcher(this.ignorables);
      }

      if (typeOrCp < 0) {
         switch (typeOrCp) {
            case -10:
            case -9:
            case -8:
            case -7:
            case -6:
               this.addMatcher(this.factory.currency());
               break;
            case -5:
               this.addMatcher(this.factory.permille());
               break;
            case -4:
               this.addMatcher(this.factory.percent());
               break;
            case -3:
            default:
               throw new AssertionError();
            case -2:
               this.addMatcher(this.factory.plusSign());
               break;
            case -1:
               this.addMatcher(this.factory.minusSign());
         }
      } else if (this.ignorables == null || !this.ignorables.getSet().contains(typeOrCp)) {
         this.addMatcher(CodePointMatcher.getInstance(typeOrCp));
      }

      this.lastTypeOrCp = typeOrCp;
   }

   public String getPattern() {
      return this.affixPattern;
   }

   @Override
   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof AffixPatternMatcher) ? false : this.affixPattern.equals(((AffixPatternMatcher)other).affixPattern);
      }
   }

   @Override
   public int hashCode() {
      return this.affixPattern.hashCode();
   }

   @Override
   public String toString() {
      return this.affixPattern;
   }
}
