package com.cobblemon.mod.relocations.ibm.icu.impl.number;

public interface AffixPatternProvider {
   int FLAG_POS_PREFIX = 256;
   int FLAG_POS_SUFFIX = 0;
   int FLAG_NEG_PREFIX = 768;
   int FLAG_NEG_SUFFIX = 512;

   char charAt(int var1, int var2);

   int length(int var1);

   String getString(int var1);

   boolean hasCurrencySign();

   boolean positiveHasPlusSign();

   boolean hasNegativeSubpattern();

   boolean negativeHasMinusSign();

   boolean containsSymbolType(int var1);

   boolean hasBody();

   boolean currencyAsDecimal();

   public static final class Flags {
      public static final int PLURAL_MASK = 255;
      public static final int PREFIX = 256;
      public static final int NEGATIVE_SUBPATTERN = 512;
      public static final int PADDING = 1024;
   }
}
