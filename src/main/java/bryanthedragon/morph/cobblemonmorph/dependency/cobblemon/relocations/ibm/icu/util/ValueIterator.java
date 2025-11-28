package com.cobblemon.mod.relocations.ibm.icu.util;

public interface ValueIterator {
   boolean next(ValueIterator.Element var1);

   void reset();

   void setRange(int var1, int var2);

   public static final class Element {
      public int integer;
      public Object value;
   }
}
