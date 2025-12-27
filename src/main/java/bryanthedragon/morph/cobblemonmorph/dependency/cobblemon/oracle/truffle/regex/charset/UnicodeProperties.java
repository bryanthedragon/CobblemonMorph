package com.oracle.truffle.regex.charset;

import com.oracle.truffle.api.CompilerDirectives;

public class UnicodeProperties {
   public static CodePointSet getProperty(String propertySpec) {
      return getProperty(propertySpec, false);
   }

   public static CodePointSet getProperty(String propertySpec, boolean caseInsensitive) {
      return evaluatePropertySpec(normalizePropertySpec(propertySpec, caseInsensitive));
   }

   private static CodePointSet evaluatePropertySpec(String propertySpec) {
      CodePointSet generalCategory = UnicodeGeneralCategories.getGeneralCategory(propertySpec);
      return generalCategory != null ? generalCategory : UnicodePropertyData.retrieveProperty(propertySpec);
   }

   private static String normalizePropertySpec(String propertySpec, boolean caseInsensitive) {
      int equals = propertySpec.indexOf(61);
      if (equals >= 0) {
         String propertyName = normalizePropertyName(propertySpec.substring(0, equals), caseInsensitive);
         String propertyValue = propertySpec.substring(equals + 1);
         switch (propertyName) {
            case "gc":
               propertyValue = normalizeGeneralCategoryName(propertyValue, caseInsensitive);
               break;
            case "sc":
            case "scx":
               propertyValue = normalizeScriptName(propertyValue, caseInsensitive);
               break;
            default:
               CompilerDirectives.transferToInterpreterAndInvalidate();
               throw new IllegalArgumentException(
                  String.format("Binary property %s cannot appear to the left of '=' in a Unicode property escape", propertySpec.substring(0, equals))
               );
         }

         return propertyName + "=" + propertyValue;
      } else {
         return isSupportedGeneralCategory(propertySpec, caseInsensitive)
            ? "gc=" + normalizeGeneralCategoryName(propertySpec, caseInsensitive)
            : normalizePropertyName(propertySpec, caseInsensitive);
      }
   }

   private static String normalizePropertyName(String propertyName, boolean caseInsensitive) {
      String caseCorrectPropertyName = propertyName;
      if (caseInsensitive) {
         caseCorrectPropertyName = UnicodePropertyDataRuby.PROPERTY_ALIASES_LOWERCASE.get(propertyName.toLowerCase(), propertyName);
      }

      if (!UnicodePropertyData.PROPERTY_ALIASES.containsKey(caseCorrectPropertyName)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IllegalArgumentException(String.format("Unsupported Unicode character property '%s'", propertyName));
      } else {
         return UnicodePropertyData.PROPERTY_ALIASES.get(caseCorrectPropertyName);
      }
   }

   private static String normalizeGeneralCategoryName(String generalCategoryName, boolean caseInsensitive) {
      String caseCorrectGeneralCategoryName = generalCategoryName;
      if (caseInsensitive) {
         caseCorrectGeneralCategoryName = UnicodePropertyDataRuby.GENERAL_CATEGORY_ALIASES_LOWERCASE
            .get(generalCategoryName.toLowerCase(), generalCategoryName);
      }

      if (!UnicodePropertyData.GENERAL_CATEGORY_ALIASES.containsKey(caseCorrectGeneralCategoryName)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IllegalArgumentException(String.format("Unknown Unicode character general category '%s'", generalCategoryName));
      } else {
         return UnicodePropertyData.GENERAL_CATEGORY_ALIASES.get(caseCorrectGeneralCategoryName);
      }
   }

   private static String normalizeScriptName(String scriptName, boolean caseInsensitive) {
      String caseCorrectScriptName = scriptName;
      if (caseInsensitive) {
         caseCorrectScriptName = UnicodePropertyDataRuby.SCRIPT_ALIASES_LOWERCASE.get(scriptName.toLowerCase(), scriptName);
      }

      if (!UnicodePropertyData.SCRIPT_ALIASES.containsKey(caseCorrectScriptName)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IllegalArgumentException(String.format("Unkown Unicode script name '%s'", scriptName));
      } else {
         return UnicodePropertyData.SCRIPT_ALIASES.get(caseCorrectScriptName);
      }
   }

   public static boolean isSupportedProperty(String propertyName, boolean caseInsensitive) {
      return caseInsensitive
         ? UnicodePropertyDataRuby.PROPERTY_ALIASES_LOWERCASE.containsKey(propertyName.toLowerCase())
         : UnicodePropertyData.PROPERTY_ALIASES.containsKey(propertyName);
   }

   public static boolean isSupportedGeneralCategory(String generalCategoryName, boolean caseInsensitive) {
      return caseInsensitive
         ? UnicodePropertyDataRuby.GENERAL_CATEGORY_ALIASES_LOWERCASE.containsKey(generalCategoryName.toLowerCase())
         : UnicodePropertyData.GENERAL_CATEGORY_ALIASES.containsKey(generalCategoryName);
   }

   public static boolean isSupportedScript(String scriptName, boolean caseInsensitive) {
      return caseInsensitive
         ? UnicodePropertyDataRuby.SCRIPT_ALIASES_LOWERCASE.containsKey(scriptName.toLowerCase())
         : UnicodePropertyData.SCRIPT_ALIASES.containsKey(scriptName);
   }
}
