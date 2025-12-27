package com.oracle.js.parser;

final class Options {
   private static final String OPTION_NAME_PREFIX = "truffle.js.";

   private Options() {
   }

   public static boolean getBooleanProperty(final String name, final Boolean defValue) {
      try {
         String property = System.getProperty("truffle.js." + name);
         return property == null && defValue != null ? defValue : property != null && !"false".equalsIgnoreCase(property);
      } catch (SecurityException var3) {
         return false;
      }
   }

   public static boolean getBooleanProperty(final String name) {
      return getBooleanProperty(name, null);
   }
}
