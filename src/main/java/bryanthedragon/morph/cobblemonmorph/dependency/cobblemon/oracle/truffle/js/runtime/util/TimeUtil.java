package com.oracle.truffle.js.runtime.util;

import java.util.Locale;

public final class TimeUtil {
   private TimeUtil() {
   }

   public static String format(long time) {
      if (time < 1000000L) {
         return String.format(Locale.ROOT, "%.2fµs", time / 1000.0);
      } else {
         return time < 1000000000L ? String.format(Locale.ROOT, "%.2fms", time / 1000000.0) : String.format(Locale.ROOT, "%.2fs", time / 1.0E9);
      }
   }
}
