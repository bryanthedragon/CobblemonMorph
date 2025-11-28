package com.cobblemon.mod.relocations.ibm.icu.impl.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedStringBuilder;
import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import java.text.Format.Field;

public interface Modifier {
   int apply(FormattedStringBuilder var1, int var2, int var3);

   int getPrefixLength();

   int getCodePointCount();

   boolean isStrong();

   boolean containsField(Field var1);

   Modifier.Parameters getParameters();

   boolean semanticallyEquivalent(Modifier var1);

   public static class Parameters {
      public ModifierStore obj;
      public Modifier.Signum signum;
      public StandardPlural plural;
   }

   public static enum Signum {
      NEG,
      NEG_ZERO,
      POS_ZERO,
      POS;

      static final int COUNT = values().length;
   }
}
