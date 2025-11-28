package com.cobblemon.mod.relocations.ibm.icu.impl;

public final class ICUDebug {
   private static String params;
   private static boolean debug;
   private static boolean help;

   public static boolean enabled() {
      return debug;
   }

   public static boolean enabled(String arg) {
      if (debug) {
         boolean result = params.indexOf(arg) != -1;
         if (help) {
            System.out.println("\nICUDebug.enabled(" + arg + ") = " + result);
         }

         return result;
      } else {
         return false;
      }
   }

   public static String value(String arg) {
      String result = "false";
      if (debug) {
         int index = params.indexOf(arg);
         if (index != -1) {
            index += arg.length();
            if (params.length() > index && params.charAt(index) == '=') {
               int limit = params.indexOf(",", ++index);
               result = params.substring(index, limit == -1 ? params.length() : limit);
            } else {
               result = "true";
            }
         }

         if (help) {
            System.out.println("\nICUDebug.value(" + arg + ") = " + result);
         }
      }

      return result;
   }

   static {
      try {
         params = System.getProperty("ICUDebug");
      } catch (SecurityException var1) {
      }

      debug = params != null;
      help = debug && (params.equals("") || params.indexOf("help") != -1);
      if (debug) {
         System.out.println("\nICUDebug=" + params);
      }
   }
}
