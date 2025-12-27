package com.oracle.truffle.js.runtime.util;

import java.util.regex.Pattern;

public class UTS35Validator {
   private static final Pattern LOCALE_ID_PATTERN = Pattern.compile(unicodeLocaleID());

   public static boolean isWellFormedUnicodeBCP47LocaleIdentifier(String languageTag) {
      return LOCALE_ID_PATTERN.matcher(languageTag).matches();
   }

   public static boolean isDigit(char c) {
      return '0' <= c && c <= '9';
   }

   public static boolean isAlpha(char c) {
      return 'A' <= c && c <= 'Z' || 'a' <= c && c <= 'z';
   }

   public static boolean isAlphanum(char c) {
      return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || '0' <= c && c <= '9';
   }

   public static boolean isStructurallyValidLanguageSubtag(String language) {
      int length = language.length();
      if (length >= 2 && length != 4 && length <= 8) {
         for (int i = 0; i < length; i++) {
            if (!isAlpha(language.charAt(i))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean isStructurallyValidRegionSubtag(String region) {
      int length = region.length();
      return length == 2 && isAlpha(region.charAt(0)) && isAlpha(region.charAt(1))
         || length == 3 && isDigit(region.charAt(0)) && isDigit(region.charAt(1)) && isDigit(region.charAt(2));
   }

   public static boolean isStructurallyValidScriptSubtag(String script) {
      return script.length() == 4 && isAlpha(script.charAt(0)) && isAlpha(script.charAt(1)) && isAlpha(script.charAt(2)) && isAlpha(script.charAt(3));
   }

   public static boolean isStructurallyValidType(String type) {
      int alphanumStart = 0;

      for (int i = 0; i < type.length(); i++) {
         char c = type.charAt(i);
         if (!isAlphanum(c)) {
            if (c != '-') {
               return false;
            }

            int alphanumLength = i - alphanumStart;
            if (3 > alphanumLength || alphanumLength > 8) {
               return false;
            }

            alphanumStart = i + 1;
         }
      }

      int alphanumLength = type.length() - alphanumStart;
      return 3 <= alphanumLength && alphanumLength <= 8;
   }

   private static String unicodeLanguageID() {
      return group(
         unicodeLanguageSubtag()
            + group(sep() + unicodeScriptSubtag())
            + "?"
            + group(sep() + unicodeRegionSubtag())
            + "?"
            + group(sep() + unicodeVariantSubtag())
            + "*"
      );
   }

   private static String unicodeLanguageSubtag() {
      return group(alpha() + "{2,3}|" + alpha() + "{5,8}");
   }

   private static String unicodeScriptSubtag() {
      return alpha() + "{4}";
   }

   private static String unicodeRegionSubtag() {
      return group(alpha() + "{2}|" + digit() + "{3}");
   }

   private static String unicodeVariantSubtag() {
      return group(alphanum() + "{5,8}|" + digit() + alphanum() + "{3}");
   }

   private static String sep() {
      return "-";
   }

   private static String digit() {
      return "[0-9]";
   }

   private static String alpha() {
      return "[A-Za-z]";
   }

   private static String alphanum() {
      return "[0-9A-Za-z]";
   }

   private static String unicodeLocaleID() {
      return group(unicodeLanguageID() + extensions() + "*" + puExtensions() + "?");
   }

   private static String extensions() {
      return group(unicodeLocaleExtensions() + "|" + transformedExtentensions() + "|" + otherExtensions());
   }

   private static String unicodeLocaleExtensions() {
      return group(sep() + "[uU]" + group(group(sep() + keyword()) + "+|" + group(sep() + attribute()) + "+" + group(sep() + keyword()) + "*"));
   }

   private static String transformedExtentensions() {
      return group(sep() + "[tT]" + group(group(sep() + tLang() + group(sep() + tField()) + "*") + "|" + group(sep() + tField()) + "+"));
   }

   private static String puExtensions() {
      return group(sep() + "[xX]" + group(sep() + alphanum() + "{1,8}") + "+");
   }

   private static String otherExtensions() {
      return group(sep() + "[0-9a-svwyzA-SVWYZ]" + group(sep() + alphanum() + "{2,8}") + "+");
   }

   private static String keyword() {
      return group(key() + group(sep() + type()) + "?");
   }

   private static String key() {
      return group(alphanum() + alpha());
   }

   private static String type() {
      return group(alphanum() + "{3,8}" + group(sep() + alphanum() + "{3,8}") + "*");
   }

   private static String attribute() {
      return alphanum() + "{3,8}";
   }

   private static String tLang() {
      return group(
         unicodeLanguageSubtag()
            + group(sep() + unicodeScriptSubtag())
            + "?"
            + group(sep() + unicodeRegionSubtag())
            + "?"
            + group(sep() + unicodeVariantSubtag())
            + "*"
      );
   }

   private static String tField() {
      return group(tKey() + tValue());
   }

   private static String tKey() {
      return group(alpha() + digit());
   }

   private static String tValue() {
      return group(sep() + alphanum() + "{3,8}") + "+";
   }

   private static String group(String expression) {
      return "(?:" + expression + ")";
   }
}
