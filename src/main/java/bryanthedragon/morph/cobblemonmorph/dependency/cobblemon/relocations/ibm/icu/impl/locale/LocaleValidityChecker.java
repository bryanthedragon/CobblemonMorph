package com.cobblemon.mod.relocations.ibm.icu.impl.locale;

import com.cobblemon.mod.relocations.ibm.icu.impl.ValidIdentifiers;
import com.cobblemon.mod.relocations.ibm.icu.util.IllformedLocaleException;
import com.cobblemon.mod.relocations.ibm.icu.util.Output;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class LocaleValidityChecker {
   private final Set<ValidIdentifiers.Datasubtype> datasubtypes;
   private final boolean allowsDeprecated;
   static Pattern SEPARATOR = Pattern.compile("[-_]");
   private static final Pattern VALID_X = Pattern.compile("[a-zA-Z0-9]{2,8}(-[a-zA-Z0-9]{2,8})*");
   static final Set<String> REORDERING_INCLUDE = new HashSet<>(Arrays.asList("space", "punct", "symbol", "currency", "digit", "others", "zzzz"));
   static final Set<String> REORDERING_EXCLUDE = new HashSet<>(Arrays.asList("zinh", "zyyy"));
   static final Set<ValidIdentifiers.Datasubtype> REGULAR_ONLY = EnumSet.of(ValidIdentifiers.Datasubtype.regular);

   public LocaleValidityChecker(Set<ValidIdentifiers.Datasubtype> datasubtypes) {
      this.datasubtypes = EnumSet.copyOf(datasubtypes);
      this.allowsDeprecated = datasubtypes.contains(ValidIdentifiers.Datasubtype.deprecated);
   }

   public LocaleValidityChecker(ValidIdentifiers.Datasubtype... datasubtypes) {
      this.datasubtypes = EnumSet.copyOf(Arrays.asList(datasubtypes));
      this.allowsDeprecated = this.datasubtypes.contains(ValidIdentifiers.Datasubtype.deprecated);
   }

   public Set<ValidIdentifiers.Datasubtype> getDatasubtypes() {
      return EnumSet.copyOf(this.datasubtypes);
   }

   public boolean isValid(ULocale locale, LocaleValidityChecker.Where where) {
      where.set(null, null);
      String language = locale.getLanguage();
      String script = locale.getScript();
      String region = locale.getCountry();
      String variantString = locale.getVariant();
      Set<Character> extensionKeys = locale.getExtensionKeys();
      if (!this.isValid(ValidIdentifiers.Datatype.language, language, where)) {
         if (language.equals("x")) {
            where.set(null, null);
            return true;
         } else {
            return false;
         }
      } else if (!this.isValid(ValidIdentifiers.Datatype.script, script, where)) {
         return false;
      } else if (!this.isValid(ValidIdentifiers.Datatype.region, region, where)) {
         return false;
      } else {
         if (!variantString.isEmpty()) {
            for (String variant : SEPARATOR.split(variantString)) {
               if (!this.isValid(ValidIdentifiers.Datatype.variant, variant, where)) {
                  return false;
               }
            }
         }

         for (Character c : extensionKeys) {
            try {
               ValidIdentifiers.Datatype datatype = ValidIdentifiers.Datatype.valueOf(c + "");
               switch (datatype) {
                  case x:
                     return true;
                  case t:
                  case u:
                     if (!this.isValidU(locale, datatype, locale.getExtension(c), where)) {
                        return false;
                     }
               }
            } catch (Exception var12) {
               return where.set(ValidIdentifiers.Datatype.illegal, c + "");
            }
         }

         return true;
      }
   }

   private boolean isValidU(ULocale locale, ValidIdentifiers.Datatype datatype, String extensionString, LocaleValidityChecker.Where where) {
      String key = "";
      int typeCount = 0;
      KeyTypeData.ValueType valueType = null;
      LocaleValidityChecker.SpecialCase specialCase = null;
      StringBuilder prefix = new StringBuilder();
      Set<String> seen = new HashSet<>();
      StringBuilder tBuffer = datatype == ValidIdentifiers.Datatype.t ? new StringBuilder() : null;

      for (String subtag : SEPARATOR.split(extensionString)) {
         if (subtag.length() != 2 || tBuffer != null && subtag.charAt(1) > '9') {
            if (tBuffer != null) {
               if (tBuffer.length() != 0) {
                  tBuffer.append('-');
               }

               tBuffer.append(subtag);
            } else {
               typeCount++;
               switch (valueType) {
                  case single:
                     if (typeCount > 1) {
                        return where.set(datatype, key + "-" + subtag);
                     }
                     break;
                  case incremental:
                     if (typeCount == 1) {
                        prefix.setLength(0);
                        prefix.append(subtag);
                     } else {
                        prefix.append('-').append(subtag);
                        subtag = prefix.toString();
                     }
                     break;
                  case multiple:
                     if (typeCount == 1) {
                        seen.clear();
                     }
               }

               switch (specialCase) {
                  case anything:
                     break;
                  case codepoints:
                     try {
                        if (Integer.parseInt(subtag, 16) > 1114111) {
                           return where.set(datatype, key + "-" + subtag);
                        }
                        break;
                     } catch (NumberFormatException var19) {
                        return where.set(datatype, key + "-" + subtag);
                     }
                  case reorder:
                     boolean newlyAdded = seen.add(subtag.equals("zzzz") ? "others" : subtag);
                     if (!newlyAdded || !this.isScriptReorder(subtag)) {
                        return where.set(datatype, key + "-" + subtag);
                     }
                     break;
                  case subdivision:
                     if (!this.isSubdivision(locale, subtag)) {
                        return where.set(datatype, key + "-" + subtag);
                     }
                     break;
                  case rgKey:
                     if (subtag.length() < 6 || !subtag.endsWith("zzzz")) {
                        return where.set(datatype, subtag);
                     }

                     if (!this.isValid(ValidIdentifiers.Datatype.region, subtag.substring(0, subtag.length() - 4), where)) {
                        return false;
                     }
                     break;
                  default:
                     Output<Boolean> isKnownKey = new Output<>();
                     Output<Boolean> isSpecialType = new Output<>();
                     String type = KeyTypeData.toBcpType(key, subtag, isKnownKey, isSpecialType);
                     if (type == null) {
                        return where.set(datatype, key + "-" + subtag);
                     }

                     if (!this.allowsDeprecated && KeyTypeData.isDeprecated(key, subtag)) {
                        return where.set(datatype, key + "-" + subtag);
                     }
               }
            }
         } else {
            if (tBuffer != null) {
               if (tBuffer.length() != 0 && !this.isValidLocale(tBuffer.toString(), where)) {
                  return false;
               }

               tBuffer = null;
            }

            key = KeyTypeData.toBcpKey(subtag);
            if (key == null) {
               return where.set(datatype, subtag);
            }

            if (!this.allowsDeprecated && KeyTypeData.isDeprecated(key)) {
               return where.set(datatype, key);
            }

            valueType = KeyTypeData.getValueType(key);
            specialCase = LocaleValidityChecker.SpecialCase.get(key);
            typeCount = 0;
         }
      }

      return tBuffer == null || tBuffer.length() == 0 || this.isValidLocale(tBuffer.toString(), where);
   }

   private boolean isSubdivision(ULocale locale, String subtag) {
      if (subtag.length() < 3) {
         return false;
      } else {
         String region = subtag.substring(0, subtag.charAt(0) <= '9' ? 3 : 2);
         String subdivision = subtag.substring(region.length());
         if (ValidIdentifiers.isValid(ValidIdentifiers.Datatype.subdivision, this.datasubtypes, region, subdivision) == null) {
            return false;
         } else {
            String localeRegion = locale.getCountry();
            if (localeRegion.isEmpty()) {
               ULocale max = ULocale.addLikelySubtags(locale);
               localeRegion = max.getCountry();
            }

            return region.equalsIgnoreCase(localeRegion);
         }
      }
   }

   private boolean isScriptReorder(String subtag) {
      subtag = AsciiUtil.toLowerString(subtag);
      if (REORDERING_INCLUDE.contains(subtag)) {
         return true;
      } else {
         return REORDERING_EXCLUDE.contains(subtag) ? false : ValidIdentifiers.isValid(ValidIdentifiers.Datatype.script, REGULAR_ONLY, subtag) != null;
      }
   }

   private boolean isValidLocale(String extensionString, LocaleValidityChecker.Where where) {
      try {
         ULocale locale = new ULocale.Builder().setLanguageTag(extensionString).build();
         return this.isValid(locale, where);
      } catch (IllformedLocaleException var6) {
         int startIndex = var6.getErrorIndex();
         String[] list = SEPARATOR.split(extensionString.substring(startIndex));
         return where.set(ValidIdentifiers.Datatype.t, list[0]);
      } catch (Exception var7) {
         return where.set(ValidIdentifiers.Datatype.t, var7.getMessage());
      }
   }

   private boolean isValid(ValidIdentifiers.Datatype datatype, String code, LocaleValidityChecker.Where where) {
      if (code.isEmpty()) {
         return true;
      } else if (datatype == ValidIdentifiers.Datatype.variant && "posix".equalsIgnoreCase(code)) {
         return true;
      } else {
         return ValidIdentifiers.isValid(datatype, this.datasubtypes, code) != null ? true : (where == null ? false : where.set(datatype, code));
      }
   }

   static enum SpecialCase {
      normal,
      anything,
      reorder,
      codepoints,
      subdivision,
      rgKey;

      static LocaleValidityChecker.SpecialCase get(String key) {
         if (key.equals("kr")) {
            return reorder;
         } else if (key.equals("vt")) {
            return codepoints;
         } else if (key.equals("sd")) {
            return subdivision;
         } else if (key.equals("rg")) {
            return rgKey;
         } else {
            return key.equals("x0") ? anything : normal;
         }
      }
   }

   public static class Where {
      public ValidIdentifiers.Datatype fieldFailure;
      public String codeFailure;

      public boolean set(ValidIdentifiers.Datatype datatype, String code) {
         this.fieldFailure = datatype;
         this.codeFailure = code;
         return false;
      }

      @Override
      public String toString() {
         return this.fieldFailure == null ? "OK" : "{" + this.fieldFailure + ", " + this.codeFailure + "}";
      }
   }
}
