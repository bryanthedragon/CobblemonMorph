package com.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.SimpleCache;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

@Deprecated
public class GenderInfo {
   private final GenderInfo.ListGenderStyle style;
   private static GenderInfo neutral = new GenderInfo(GenderInfo.ListGenderStyle.NEUTRAL);
   private static GenderInfo.Cache genderInfoCache = new GenderInfo.Cache();

   @Deprecated
   public static GenderInfo getInstance(ULocale uLocale) {
      return genderInfoCache.get(uLocale);
   }

   @Deprecated
   public static GenderInfo getInstance(Locale locale) {
      return getInstance(ULocale.forLocale(locale));
   }

   @Deprecated
   public GenderInfo.Gender getListGender(GenderInfo.Gender... genders) {
      return this.getListGender(Arrays.asList(genders));
   }

   @Deprecated
   public GenderInfo.Gender getListGender(List<GenderInfo.Gender> genders) {
      if (genders.size() == 0) {
         return GenderInfo.Gender.OTHER;
      } else if (genders.size() == 1) {
         return genders.get(0);
      } else {
         switch (this.style) {
            case NEUTRAL:
               return GenderInfo.Gender.OTHER;
            case MIXED_NEUTRAL:
               boolean hasFemale = false;
               boolean hasMale = false;

               for (GenderInfo.Gender genderx : genders) {
                  switch (genderx) {
                     case FEMALE:
                        if (hasMale) {
                           return GenderInfo.Gender.OTHER;
                        }

                        hasFemale = true;
                        break;
                     case MALE:
                        if (hasFemale) {
                           return GenderInfo.Gender.OTHER;
                        }

                        hasMale = true;
                        break;
                     case OTHER:
                        return GenderInfo.Gender.OTHER;
                  }
               }

               return hasMale ? GenderInfo.Gender.MALE : GenderInfo.Gender.FEMALE;
            case MALE_TAINTS:
               for (GenderInfo.Gender gender : genders) {
                  if (gender != GenderInfo.Gender.FEMALE) {
                     return GenderInfo.Gender.MALE;
                  }
               }

               return GenderInfo.Gender.FEMALE;
            default:
               return GenderInfo.Gender.OTHER;
         }
      }
   }

   @Deprecated
   public GenderInfo(GenderInfo.ListGenderStyle genderStyle) {
      this.style = genderStyle;
   }

   private static class Cache {
      private final ICUCache<ULocale, GenderInfo> cache = new SimpleCache<>();

      private Cache() {
      }

      public GenderInfo get(ULocale locale) {
         GenderInfo result = this.cache.get(locale);
         if (result == null) {
            result = load(locale);
            if (result == null) {
               ULocale fallback = locale.getFallback();
               result = fallback == null ? GenderInfo.neutral : this.get(fallback);
            }

            this.cache.put(locale, result);
         }

         return result;
      }

      private static GenderInfo load(ULocale ulocale) {
         UResourceBundle rb = UResourceBundle.getBundleInstance(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "genderList", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true
         );
         UResourceBundle genderList = rb.get("genderList");

         try {
            return new GenderInfo(GenderInfo.ListGenderStyle.fromName(genderList.getString(ulocale.toString())));
         } catch (MissingResourceException var4) {
            return null;
         }
      }
   }

   @Deprecated
   public static enum Gender {
      @Deprecated
      MALE,
      @Deprecated
      FEMALE,
      @Deprecated
      OTHER;
   }

   @Deprecated
   public static enum ListGenderStyle {
      @Deprecated
      NEUTRAL,
      @Deprecated
      MIXED_NEUTRAL,
      @Deprecated
      MALE_TAINTS;

      private static Map<String, GenderInfo.ListGenderStyle> fromNameMap = new HashMap<>(3);

      @Deprecated
      public static GenderInfo.ListGenderStyle fromName(String name) {
         GenderInfo.ListGenderStyle result = fromNameMap.get(name);
         if (result == null) {
            throw new IllegalArgumentException("Unknown gender style name: " + name);
         } else {
            return result;
         }
      }

      static {
         fromNameMap.put("neutral", NEUTRAL);
         fromNameMap.put("maleTaints", MALE_TAINTS);
         fromNameMap.put("mixedNeutral", MIXED_NEUTRAL);
      }
   }
}
