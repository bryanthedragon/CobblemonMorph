package com.cobblemon.mod.relocations.ibm.icu.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum StandardPlural {
   ZERO("zero"),
   ONE("one"),
   TWO("two"),
   FEW("few"),
   MANY("many"),
   OTHER("other"),
   EQ_0("=0"),
   EQ_1("=1");

   public static final int OTHER_INDEX = OTHER.ordinal();
   public static final List<StandardPlural> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
   public static final int COUNT = VALUES.size();
   private final String keyword;

   private StandardPlural(String kw) {
      this.keyword = kw;
   }

   public final String getKeyword() {
      return this.keyword;
   }

   public static final StandardPlural orNullFromString(CharSequence keyword) {
      switch (keyword.length()) {
         case 1:
            if (keyword.charAt(0) == '0') {
               return EQ_0;
            }

            if (keyword.charAt(0) == '1') {
               return EQ_1;
            }
            break;
         case 2:
            if ("=0".contentEquals(keyword)) {
               return EQ_0;
            }

            if ("=1".contentEquals(keyword)) {
               return EQ_1;
            }
            break;
         case 3:
            if ("one".contentEquals(keyword)) {
               return ONE;
            }

            if ("two".contentEquals(keyword)) {
               return TWO;
            }

            if ("few".contentEquals(keyword)) {
               return FEW;
            }
            break;
         case 4:
            if ("many".contentEquals(keyword)) {
               return MANY;
            }

            if ("zero".contentEquals(keyword)) {
               return ZERO;
            }
            break;
         case 5:
            if ("other".contentEquals(keyword)) {
               return OTHER;
            }
      }

      return null;
   }

   public static final StandardPlural orOtherFromString(CharSequence keyword) {
      StandardPlural p = orNullFromString(keyword);
      return p != null ? p : OTHER;
   }

   public static final StandardPlural fromString(CharSequence keyword) {
      StandardPlural p = orNullFromString(keyword);
      if (p != null) {
         return p;
      } else {
         throw new IllegalArgumentException(keyword.toString());
      }
   }

   public static final int indexOrNegativeFromString(CharSequence keyword) {
      StandardPlural p = orNullFromString(keyword);
      return p != null ? p.ordinal() : -1;
   }

   public static final int indexOrOtherIndexFromString(CharSequence keyword) {
      StandardPlural p = orNullFromString(keyword);
      return p != null ? p.ordinal() : OTHER.ordinal();
   }

   public static final int indexFromString(CharSequence keyword) {
      StandardPlural p = orNullFromString(keyword);
      if (p != null) {
         return p.ordinal();
      } else {
         throw new IllegalArgumentException(keyword.toString());
      }
   }
}
