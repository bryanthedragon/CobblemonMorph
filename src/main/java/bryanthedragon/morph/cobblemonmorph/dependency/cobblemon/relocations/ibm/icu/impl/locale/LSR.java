package com.cobblemon.mod.relocations.ibm.icu.impl.locale;

import java.util.Objects;

public final class LSR {
   public static final int REGION_INDEX_LIMIT = 1677;
   public static final int EXPLICIT_LSR = 7;
   public static final int EXPLICIT_LANGUAGE = 4;
   public static final int EXPLICIT_SCRIPT = 2;
   public static final int EXPLICIT_REGION = 1;
   public static final int IMPLICIT_LSR = 0;
   public static final int DONT_CARE_FLAGS = 0;
   public static final boolean DEBUG_OUTPUT = false;
   public final String language;
   public final String script;
   public final String region;
   final int regionIndex;
   public final int flags;

   public LSR(String language, String script, String region, int flags) {
      this.language = language;
      this.script = script;
      this.region = region;
      this.regionIndex = indexForRegion(region);
      this.flags = flags;
   }

   public static final int indexForRegion(String region) {
      if (region.length() == 2) {
         int a = region.charAt(0) - 'A';
         if (a >= 0 && 25 >= a) {
            int b = region.charAt(1) - 'A';
            return b >= 0 && 25 >= b ? 26 * a + b + 1001 : 0;
         } else {
            return 0;
         }
      } else if (region.length() == 3) {
         int a = region.charAt(0) - '0';
         if (a >= 0 && 9 >= a) {
            int b = region.charAt(1) - '0';
            if (b >= 0 && 9 >= b) {
               int c = region.charAt(2) - '0';
               return c >= 0 && 9 >= c ? (10 * a + b) * 10 + c + 1 : 0;
            } else {
               return 0;
            }
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder(this.language);
      if (!this.script.isEmpty()) {
         result.append('-').append(this.script);
      }

      if (!this.region.isEmpty()) {
         result.append('-').append(this.region);
      }

      return result.toString();
   }

   public boolean isEquivalentTo(LSR other) {
      return this.language.equals(other.language) && this.script.equals(other.script) && this.region.equals(other.region);
   }

   @Override
   public boolean equals(Object obj) {
      LSR other;
      return this == obj
         || obj != null
            && obj.getClass() == this.getClass()
            && this.language.equals((other = (LSR)obj).language)
            && this.script.equals(other.script)
            && this.region.equals(other.region)
            && this.flags == other.flags;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.language, this.script, this.region, this.flags);
   }
}
