package com.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.impl.CacheBase;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceTableAccess;
import com.cobblemon.mod.relocations.ibm.icu.impl.LocaleIDParser;
import com.cobblemon.mod.relocations.ibm.icu.impl.LocaleIDs;
import com.cobblemon.mod.relocations.ibm.icu.impl.SoftCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.Utility;
import com.cobblemon.mod.relocations.ibm.icu.impl.locale.AsciiUtil;
import com.cobblemon.mod.relocations.ibm.icu.impl.locale.BaseLocale;
import com.cobblemon.mod.relocations.ibm.icu.impl.locale.Extension;
import com.cobblemon.mod.relocations.ibm.icu.impl.locale.InternalLocaleBuilder;
import com.cobblemon.mod.relocations.ibm.icu.impl.locale.KeyTypeData;
import com.cobblemon.mod.relocations.ibm.icu.impl.locale.LanguageTag;
import com.cobblemon.mod.relocations.ibm.icu.impl.locale.LocaleExtensions;
import com.cobblemon.mod.relocations.ibm.icu.impl.locale.LocaleSyntaxException;
import com.cobblemon.mod.relocations.ibm.icu.impl.locale.ParseStatus;
import com.cobblemon.mod.relocations.ibm.icu.impl.locale.UnicodeLocaleExtension;
import com.cobblemon.mod.relocations.ibm.icu.lang.UScript;
import com.cobblemon.mod.relocations.ibm.icu.text.LocaleDisplayNames;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

public final class ULocale implements Serializable, Comparable<ULocale> {
   private static final long serialVersionUID = 3715177670352309217L;
   private static CacheBase<String, String, Void> nameCache = new SoftCache<String, String, Void>() {
      protected String createInstance(String tmpLocaleID, Void unused) {
         return new LocaleIDParser(tmpLocaleID).getName();
      }
   };
   public static final ULocale ENGLISH = new ULocale("en", Locale.ENGLISH);
   public static final ULocale FRENCH = new ULocale("fr", Locale.FRENCH);
   public static final ULocale GERMAN = new ULocale("de", Locale.GERMAN);
   public static final ULocale ITALIAN = new ULocale("it", Locale.ITALIAN);
   public static final ULocale JAPANESE = new ULocale("ja", Locale.JAPANESE);
   public static final ULocale KOREAN = new ULocale("ko", Locale.KOREAN);
   public static final ULocale CHINESE = new ULocale("zh", Locale.CHINESE);
   public static final ULocale SIMPLIFIED_CHINESE = new ULocale("zh_Hans");
   public static final ULocale TRADITIONAL_CHINESE = new ULocale("zh_Hant");
   public static final ULocale FRANCE = new ULocale("fr_FR", Locale.FRANCE);
   public static final ULocale GERMANY = new ULocale("de_DE", Locale.GERMANY);
   public static final ULocale ITALY = new ULocale("it_IT", Locale.ITALY);
   public static final ULocale JAPAN = new ULocale("ja_JP", Locale.JAPAN);
   public static final ULocale KOREA = new ULocale("ko_KR", Locale.KOREA);
   public static final ULocale CHINA = new ULocale("zh_Hans_CN");
   public static final ULocale PRC = CHINA;
   public static final ULocale TAIWAN = new ULocale("zh_Hant_TW");
   public static final ULocale UK = new ULocale("en_GB", Locale.UK);
   public static final ULocale US = new ULocale("en_US", Locale.US);
   public static final ULocale CANADA = new ULocale("en_CA", Locale.CANADA);
   public static final ULocale CANADA_FRENCH = new ULocale("fr_CA", Locale.CANADA_FRENCH);
   private static final String EMPTY_STRING = "";
   private static final char UNDERSCORE = '_';
   private static final Locale EMPTY_LOCALE = new Locale("", "");
   private static final String LOCALE_ATTRIBUTE_KEY = "attribute";
   public static final ULocale ROOT = new ULocale("", EMPTY_LOCALE);
   private static final SoftCache<Locale, ULocale, Void> CACHE = new SoftCache<Locale, ULocale, Void>() {
      protected ULocale createInstance(Locale key, Void unused) {
         return ULocale.JDKLocaleHelper.toULocale(key);
      }
   };
   private transient volatile Locale locale;
   private String localeID;
   private transient volatile BaseLocale baseLocale;
   private transient volatile LocaleExtensions extensions;
   private static String[][] CANONICALIZE_MAP = new String[][]{
      {"art__LOJBAN", "jbo"},
      {"cel__GAULISH", "cel__GAULISH"},
      {"de__1901", "de__1901"},
      {"de__1906", "de__1906"},
      {"en__BOONT", "en__BOONT"},
      {"en__SCOUSE", "en__SCOUSE"},
      {"hy__AREVELA", "hy", null, null},
      {"hy__AREVMDA", "hyw", null, null},
      {"sl__ROZAJ", "sl__ROZAJ"},
      {"zh__GUOYU", "zh"},
      {"zh__HAKKA", "hak"},
      {"zh__XIANG", "hsn"},
      {"zh_GAN", "gan"},
      {"zh_MIN", "zh__MIN"},
      {"zh_MIN_NAN", "nan"},
      {"zh_WUU", "wuu"},
      {"zh_YUE", "yue"}
   };
   private static Locale defaultLocale = Locale.getDefault();
   private static ULocale defaultULocale = forLocale(defaultLocale);
   private static Locale[] defaultCategoryLocales = new Locale[ULocale.Category.values().length];
   private static ULocale[] defaultCategoryULocales = new ULocale[ULocale.Category.values().length];
   private static Set<String> gKnownCanonicalizedCases;
   private static final String LANG_DIR_STRING = "root-en-es-pt-zh-ja-ko-de-fr-it-ar+he+fa+ru-nl-pl-th-tr-";
   public static ULocale.Type ACTUAL_LOCALE;
   public static ULocale.Type VALID_LOCALE;
   private static final String UNDEFINED_LANGUAGE = "und";
   private static final String UNDEFINED_SCRIPT = "Zzzz";
   private static final String UNDEFINED_REGION = "ZZ";
   public static final char PRIVATE_USE_EXTENSION = 'x';
   public static final char UNICODE_LOCALE_EXTENSION = 'u';

   private ULocale(String localeID, Locale locale) {
      this.localeID = localeID;
      this.locale = locale;
   }

   public static ULocale forLocale(Locale loc) {
      return loc == null ? null : CACHE.getInstance(loc, null);
   }

   public ULocale(String localeID) {
      this.localeID = getName(localeID);
   }

   public ULocale(String a, String b) {
      this(a, b, null);
   }

   public ULocale(String a, String b, String c) {
      this.localeID = getName(lscvToID(a, b, c, ""));
   }

   public static ULocale createCanonical(String nonCanonicalID) {
      return new ULocale(canonicalize(nonCanonicalID), (Locale)null);
   }

   public static ULocale createCanonical(ULocale locale) {
      return createCanonical(locale.getName());
   }

   private static String lscvToID(String lang, String script, String country, String variant) {
      StringBuilder buf = new StringBuilder();
      if (lang != null && lang.length() > 0) {
         buf.append(lang);
      }

      if (script != null && script.length() > 0) {
         buf.append('_');
         buf.append(script);
      }

      if (country != null && country.length() > 0) {
         buf.append('_');
         buf.append(country);
      }

      if (variant != null && variant.length() > 0) {
         if (country == null || country.length() == 0) {
            buf.append('_');
         }

         buf.append('_');
         buf.append(variant);
      }

      return buf.toString();
   }

   public Locale toLocale() {
      if (this.locale == null) {
         this.locale = ULocale.JDKLocaleHelper.toLocale(this);
      }

      return this.locale;
   }

   public static ULocale getDefault() {
      synchronized (ULocale.class) {
         if (defaultULocale == null) {
            return ROOT;
         } else {
            Locale currentDefault = Locale.getDefault();
            if (!defaultLocale.equals(currentDefault)) {
               defaultLocale = currentDefault;
               defaultULocale = forLocale(currentDefault);
               if (!ULocale.JDKLocaleHelper.hasLocaleCategories()) {
                  for (ULocale.Category cat : ULocale.Category.values()) {
                     int idx = cat.ordinal();
                     defaultCategoryLocales[idx] = currentDefault;
                     defaultCategoryULocales[idx] = forLocale(currentDefault);
                  }
               }
            }

            return defaultULocale;
         }
      }
   }

   public static synchronized void setDefault(ULocale newLocale) {
      defaultLocale = newLocale.toLocale();
      Locale.setDefault(defaultLocale);
      defaultULocale = newLocale;

      for (ULocale.Category cat : ULocale.Category.values()) {
         setDefault(cat, newLocale);
      }
   }

   public static ULocale getDefault(ULocale.Category category) {
      synchronized (ULocale.class) {
         int idx = category.ordinal();
         if (defaultCategoryULocales[idx] == null) {
            return ROOT;
         } else {
            if (ULocale.JDKLocaleHelper.hasLocaleCategories()) {
               Locale currentCategoryDefault = ULocale.JDKLocaleHelper.getDefault(category);
               if (!defaultCategoryLocales[idx].equals(currentCategoryDefault)) {
                  defaultCategoryLocales[idx] = currentCategoryDefault;
                  defaultCategoryULocales[idx] = forLocale(currentCategoryDefault);
               }
            } else {
               Locale currentDefault = Locale.getDefault();
               if (!defaultLocale.equals(currentDefault)) {
                  defaultLocale = currentDefault;
                  defaultULocale = forLocale(currentDefault);

                  for (ULocale.Category cat : ULocale.Category.values()) {
                     int tmpIdx = cat.ordinal();
                     defaultCategoryLocales[tmpIdx] = currentDefault;
                     defaultCategoryULocales[tmpIdx] = forLocale(currentDefault);
                  }
               }
            }

            return defaultCategoryULocales[idx];
         }
      }
   }

   public static synchronized void setDefault(ULocale.Category category, ULocale newLocale) {
      Locale newJavaDefault = newLocale.toLocale();
      int idx = category.ordinal();
      defaultCategoryULocales[idx] = newLocale;
      defaultCategoryLocales[idx] = newJavaDefault;
      ULocale.JDKLocaleHelper.setDefault(category, newJavaDefault);
   }

   @Override
   public Object clone() {
      return this;
   }

   @Override
   public int hashCode() {
      return this.localeID.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj instanceof ULocale ? this.localeID.equals(((ULocale)obj).localeID) : false;
      }
   }

   public int compareTo(ULocale other) {
      if (this == other) {
         return 0;
      } else {
         int cmp = 0;
         cmp = this.getLanguage().compareTo(other.getLanguage());
         if (cmp == 0) {
            cmp = this.getScript().compareTo(other.getScript());
            if (cmp == 0) {
               cmp = this.getCountry().compareTo(other.getCountry());
               if (cmp == 0) {
                  cmp = this.getVariant().compareTo(other.getVariant());
                  if (cmp == 0) {
                     Iterator<String> thisKwdItr = this.getKeywords();
                     Iterator<String> otherKwdItr = other.getKeywords();
                     if (thisKwdItr == null) {
                        cmp = otherKwdItr == null ? 0 : -1;
                     } else if (otherKwdItr == null) {
                        cmp = 1;
                     } else {
                        while (cmp == 0 && thisKwdItr.hasNext()) {
                           if (!otherKwdItr.hasNext()) {
                              cmp = 1;
                              break;
                           }

                           String thisKey = thisKwdItr.next();
                           String otherKey = otherKwdItr.next();
                           cmp = thisKey.compareTo(otherKey);
                           if (cmp == 0) {
                              String thisVal = this.getKeywordValue(thisKey);
                              String otherVal = other.getKeywordValue(otherKey);
                              if (thisVal == null) {
                                 cmp = otherVal == null ? 0 : -1;
                              } else if (otherVal == null) {
                                 cmp = 1;
                              } else {
                                 cmp = thisVal.compareTo(otherVal);
                              }
                           }
                        }

                        if (cmp == 0 && otherKwdItr.hasNext()) {
                           cmp = -1;
                        }
                     }
                  }
               }
            }
         }

         return cmp < 0 ? -1 : (cmp > 0 ? 1 : 0);
      }
   }

   public static ULocale[] getAvailableLocales() {
      return (ULocale[])ICUResourceBundle.getAvailableULocales().clone();
   }

   public static Collection<ULocale> getAvailableLocalesByType(ULocale.AvailableType type) {
      if (type == null) {
         throw new IllegalArgumentException();
      } else {
         List<ULocale> result;
         if (type == ULocale.AvailableType.WITH_LEGACY_ALIASES) {
            result = new ArrayList<>();
            Collections.addAll(result, ICUResourceBundle.getAvailableULocales(ULocale.AvailableType.DEFAULT));
            Collections.addAll(result, ICUResourceBundle.getAvailableULocales(ULocale.AvailableType.ONLY_LEGACY_ALIASES));
         } else {
            result = Arrays.asList(ICUResourceBundle.getAvailableULocales(type));
         }

         return Collections.unmodifiableList(result);
      }
   }

   public static String[] getISOCountries() {
      return LocaleIDs.getISOCountries();
   }

   public static String[] getISOLanguages() {
      return LocaleIDs.getISOLanguages();
   }

   public String getLanguage() {
      return this.base().getLanguage();
   }

   public static String getLanguage(String localeID) {
      return new LocaleIDParser(localeID).getLanguage();
   }

   public String getScript() {
      return this.base().getScript();
   }

   public static String getScript(String localeID) {
      return new LocaleIDParser(localeID).getScript();
   }

   public String getCountry() {
      return this.base().getRegion();
   }

   public static String getCountry(String localeID) {
      return new LocaleIDParser(localeID).getCountry();
   }

   @Deprecated
   public static String getRegionForSupplementalData(ULocale locale, boolean inferRegion) {
      String region = locale.getKeywordValue("rg");
      if (region != null && region.length() == 6) {
         String regionUpper = AsciiUtil.toUpperString(region);
         if (regionUpper.endsWith("ZZZZ")) {
            return regionUpper.substring(0, 2);
         }
      }

      region = locale.getCountry();
      if (region.length() == 0 && inferRegion) {
         ULocale maximized = addLikelySubtags(locale);
         region = maximized.getCountry();
      }

      return region;
   }

   public String getVariant() {
      return this.base().getVariant();
   }

   public static String getVariant(String localeID) {
      return new LocaleIDParser(localeID).getVariant();
   }

   public static String getFallback(String localeID) {
      return getFallbackString(getName(localeID));
   }

   public ULocale getFallback() {
      return this.localeID.length() != 0 && this.localeID.charAt(0) != '@' ? new ULocale(getFallbackString(this.localeID), (Locale)null) : null;
   }

   private static String getFallbackString(String fallback) {
      int extStart = fallback.indexOf(64);
      if (extStart == -1) {
         extStart = fallback.length();
      }

      int last = fallback.lastIndexOf(95, extStart);
      if (last == -1) {
         last = 0;
      } else {
         while (last > 0 && fallback.charAt(last - 1) == '_') {
            last--;
         }
      }

      return fallback.substring(0, last) + fallback.substring(extStart);
   }

   public String getBaseName() {
      return getBaseName(this.localeID);
   }

   public static String getBaseName(String localeID) {
      return localeID.indexOf(64) == -1 ? localeID : new LocaleIDParser(localeID).getBaseName();
   }

   public String getName() {
      return this.localeID;
   }

   private static int getShortestSubtagLength(String localeID) {
      int localeIDLength = localeID.length();
      int length = localeIDLength;
      boolean reset = true;
      int tmpLength = 0;

      for (int i = 0; i < localeIDLength; i++) {
         if (localeID.charAt(i) != '_' && localeID.charAt(i) != '-') {
            if (reset) {
               reset = false;
               tmpLength = 0;
            }

            tmpLength++;
         } else {
            if (tmpLength != 0 && tmpLength < length) {
               length = tmpLength;
            }

            reset = true;
         }
      }

      return length;
   }

   public static String getName(String localeID) {
      String tmpLocaleID = localeID;
      if (localeID != null && !localeID.contains("@") && getShortestSubtagLength(localeID) == 1) {
         if (localeID.indexOf(95) >= 0 && localeID.charAt(1) != '_' && localeID.charAt(1) != '-') {
            tmpLocaleID = localeID.replace('_', '-');
         }

         tmpLocaleID = forLanguageTag(tmpLocaleID).getName();
         if (tmpLocaleID.length() == 0) {
            tmpLocaleID = localeID;
         }
      } else if ("root".equalsIgnoreCase(localeID)) {
         tmpLocaleID = "";
      } else {
         tmpLocaleID = stripLeadingUnd(localeID);
      }

      return nameCache.getInstance(tmpLocaleID, null);
   }

   private static String stripLeadingUnd(String localeID) {
      int length = localeID.length();
      if (length < 3) {
         return localeID;
      } else if (!localeID.regionMatches(true, 0, "und", 0, 3)) {
         return localeID;
      } else if (length == 3) {
         return "";
      } else {
         char separator = localeID.charAt(3);
         return separator != '-' && separator != '_' ? localeID : localeID.substring(3);
      }
   }

   @Override
   public String toString() {
      return this.localeID;
   }

   public Iterator<String> getKeywords() {
      return getKeywords(this.localeID);
   }

   public static Iterator<String> getKeywords(String localeID) {
      return new LocaleIDParser(localeID).getKeywords();
   }

   public String getKeywordValue(String keywordName) {
      return getKeywordValue(this.localeID, keywordName);
   }

   public static String getKeywordValue(String localeID, String keywordName) {
      return new LocaleIDParser(localeID).getKeywordValue(keywordName);
   }

   public static String canonicalize(String localeID) {
      LocaleIDParser parser = new LocaleIDParser(localeID, true);
      String baseName = parser.getBaseName();
      boolean foundVariant = false;
      if (localeID.equals("")) {
         return "";
      } else {
         for (int i = 0; i < CANONICALIZE_MAP.length; i++) {
            String[] vals = CANONICALIZE_MAP[i];
            if (vals[0].equals(baseName)) {
               foundVariant = true;
               parser.setBaseName(vals[1]);
               break;
            }
         }

         if (!foundVariant && parser.getLanguage().equals("nb") && parser.getVariant().equals("NY")) {
            parser.setBaseName(lscvToID("nn", parser.getScript(), parser.getCountry(), null));
         }

         String name = parser.getName();
         if (!isKnownCanonicalizedLocale(name)) {
            ULocale.AliasReplacer replacer = new ULocale.AliasReplacer(
               parser.getLanguage(),
               parser.getScript(),
               parser.getCountry(),
               AsciiUtil.toLowerString(parser.getVariant()),
               parser.getName().substring(parser.getBaseName().length())
            );
            String replaced = replacer.replace();
            if (replaced != null) {
               parser = new LocaleIDParser(replaced);
            }
         }

         return parser.getName();
      }
   }

   private static synchronized boolean isKnownCanonicalizedLocale(String name) {
      if (!name.equals("c") && !name.equals("en") && !name.equals("en_US")) {
         if (gKnownCanonicalizedCases == null) {
            List<String> items = Arrays.asList(
               "af",
               "af_ZA",
               "am",
               "am_ET",
               "ar",
               "ar_001",
               "as",
               "as_IN",
               "az",
               "az_AZ",
               "be",
               "be_BY",
               "bg",
               "bg_BG",
               "bn",
               "bn_IN",
               "bs",
               "bs_BA",
               "ca",
               "ca_ES",
               "cs",
               "cs_CZ",
               "cy",
               "cy_GB",
               "da",
               "da_DK",
               "de",
               "de_DE",
               "el",
               "el_GR",
               "en",
               "en_GB",
               "en_US",
               "es",
               "es_419",
               "es_ES",
               "et",
               "et_EE",
               "eu",
               "eu_ES",
               "fa",
               "fa_IR",
               "fi",
               "fi_FI",
               "fil",
               "fil_PH",
               "fr",
               "fr_FR",
               "ga",
               "ga_IE",
               "gl",
               "gl_ES",
               "gu",
               "gu_IN",
               "he",
               "he_IL",
               "hi",
               "hi_IN",
               "hr",
               "hr_HR",
               "hu",
               "hu_HU",
               "hy",
               "hy_AM",
               "id",
               "id_ID",
               "is",
               "is_IS",
               "it",
               "it_IT",
               "ja",
               "ja_JP",
               "jv",
               "jv_ID",
               "ka",
               "ka_GE",
               "kk",
               "kk_KZ",
               "km",
               "km_KH",
               "kn",
               "kn_IN",
               "ko",
               "ko_KR",
               "ky",
               "ky_KG",
               "lo",
               "lo_LA",
               "lt",
               "lt_LT",
               "lv",
               "lv_LV",
               "mk",
               "mk_MK",
               "ml",
               "ml_IN",
               "mn",
               "mn_MN",
               "mr",
               "mr_IN",
               "ms",
               "ms_MY",
               "my",
               "my_MM",
               "nb",
               "nb_NO",
               "ne",
               "ne_NP",
               "nl",
               "nl_NL",
               "no",
               "or",
               "or_IN",
               "pa",
               "pa_IN",
               "pl",
               "pl_PL",
               "ps",
               "ps_AF",
               "pt",
               "pt_BR",
               "pt_PT",
               "ro",
               "ro_RO",
               "ru",
               "ru_RU",
               "sd",
               "sd_IN",
               "si",
               "si_LK",
               "sk",
               "sk_SK",
               "sl",
               "sl_SI",
               "so",
               "so_SO",
               "sq",
               "sq_AL",
               "sr",
               "sr_Cyrl_RS",
               "sr_Latn",
               "sr_RS",
               "sv",
               "sv_SE",
               "sw",
               "sw_TZ",
               "ta",
               "ta_IN",
               "te",
               "te_IN",
               "th",
               "th_TH",
               "tk",
               "tk_TM",
               "tr",
               "tr_TR",
               "uk",
               "uk_UA",
               "ur",
               "ur_PK",
               "uz",
               "uz_UZ",
               "vi",
               "vi_VN",
               "yue",
               "yue_Hant",
               "yue_Hant_HK",
               "yue_HK",
               "zh",
               "zh_CN",
               "zh_Hans",
               "zh_Hans_CN",
               "zh_Hant",
               "zh_Hant_TW",
               "zh_TW",
               "zu",
               "zu_ZA"
            );
            gKnownCanonicalizedCases = new HashSet<>(items);
         }

         return gKnownCanonicalizedCases.contains(name);
      } else {
         return true;
      }
   }

   public ULocale setKeywordValue(String keyword, String value) {
      return new ULocale(setKeywordValue(this.localeID, keyword, value), (Locale)null);
   }

   public static String setKeywordValue(String localeID, String keyword, String value) {
      LocaleIDParser parser = new LocaleIDParser(localeID);
      parser.setKeywordValue(keyword, value);
      return parser.getName();
   }

   public String getISO3Language() {
      return getISO3Language(this.localeID);
   }

   public static String getISO3Language(String localeID) {
      return LocaleIDs.getISO3Language(getLanguage(localeID));
   }

   public String getISO3Country() {
      return getISO3Country(this.localeID);
   }

   public static String getISO3Country(String localeID) {
      return LocaleIDs.getISO3Country(getCountry(localeID));
   }

   public boolean isRightToLeft() {
      String script = this.getScript();
      if (script.length() == 0) {
         String lang = this.getLanguage();
         if (!lang.isEmpty()) {
            int langIndex = "root-en-es-pt-zh-ja-ko-de-fr-it-ar+he+fa+ru-nl-pl-th-tr-".indexOf(lang);
            if (langIndex >= 0) {
               switch ("root-en-es-pt-zh-ja-ko-de-fr-it-ar+he+fa+ru-nl-pl-th-tr-".charAt(langIndex + lang.length())) {
                  case '+':
                     return true;
                  case '-':
                     return false;
               }
            }
         }

         ULocale likely = addLikelySubtags(this);
         script = likely.getScript();
         if (script.length() == 0) {
            return false;
         }
      }

      int scriptCode = UScript.getCodeFromName(script);
      return UScript.isRightToLeft(scriptCode);
   }

   public String getDisplayLanguage() {
      return getDisplayLanguageInternal(this, getDefault(ULocale.Category.DISPLAY), false);
   }

   public String getDisplayLanguage(ULocale displayLocale) {
      return getDisplayLanguageInternal(this, displayLocale, false);
   }

   public static String getDisplayLanguage(String localeID, String displayLocaleID) {
      return getDisplayLanguageInternal(new ULocale(localeID), new ULocale(displayLocaleID), false);
   }

   public static String getDisplayLanguage(String localeID, ULocale displayLocale) {
      return getDisplayLanguageInternal(new ULocale(localeID), displayLocale, false);
   }

   public String getDisplayLanguageWithDialect() {
      return getDisplayLanguageInternal(this, getDefault(ULocale.Category.DISPLAY), true);
   }

   public String getDisplayLanguageWithDialect(ULocale displayLocale) {
      return getDisplayLanguageInternal(this, displayLocale, true);
   }

   public static String getDisplayLanguageWithDialect(String localeID, String displayLocaleID) {
      return getDisplayLanguageInternal(new ULocale(localeID), new ULocale(displayLocaleID), true);
   }

   public static String getDisplayLanguageWithDialect(String localeID, ULocale displayLocale) {
      return getDisplayLanguageInternal(new ULocale(localeID), displayLocale, true);
   }

   private static String getDisplayLanguageInternal(ULocale locale, ULocale displayLocale, boolean useDialect) {
      String lang = useDialect ? locale.getBaseName() : locale.getLanguage();
      return LocaleDisplayNames.getInstance(displayLocale).languageDisplayName(lang);
   }

   public String getDisplayScript() {
      return getDisplayScriptInternal(this, getDefault(ULocale.Category.DISPLAY));
   }

   @Deprecated
   public String getDisplayScriptInContext() {
      return getDisplayScriptInContextInternal(this, getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayScript(ULocale displayLocale) {
      return getDisplayScriptInternal(this, displayLocale);
   }

   @Deprecated
   public String getDisplayScriptInContext(ULocale displayLocale) {
      return getDisplayScriptInContextInternal(this, displayLocale);
   }

   public static String getDisplayScript(String localeID, String displayLocaleID) {
      return getDisplayScriptInternal(new ULocale(localeID), new ULocale(displayLocaleID));
   }

   @Deprecated
   public static String getDisplayScriptInContext(String localeID, String displayLocaleID) {
      return getDisplayScriptInContextInternal(new ULocale(localeID), new ULocale(displayLocaleID));
   }

   public static String getDisplayScript(String localeID, ULocale displayLocale) {
      return getDisplayScriptInternal(new ULocale(localeID), displayLocale);
   }

   @Deprecated
   public static String getDisplayScriptInContext(String localeID, ULocale displayLocale) {
      return getDisplayScriptInContextInternal(new ULocale(localeID), displayLocale);
   }

   private static String getDisplayScriptInternal(ULocale locale, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale).scriptDisplayName(locale.getScript());
   }

   private static String getDisplayScriptInContextInternal(ULocale locale, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale).scriptDisplayNameInContext(locale.getScript());
   }

   public String getDisplayCountry() {
      return getDisplayCountryInternal(this, getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayCountry(ULocale displayLocale) {
      return getDisplayCountryInternal(this, displayLocale);
   }

   public static String getDisplayCountry(String localeID, String displayLocaleID) {
      return getDisplayCountryInternal(new ULocale(localeID), new ULocale(displayLocaleID));
   }

   public static String getDisplayCountry(String localeID, ULocale displayLocale) {
      return getDisplayCountryInternal(new ULocale(localeID), displayLocale);
   }

   private static String getDisplayCountryInternal(ULocale locale, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale).regionDisplayName(locale.getCountry());
   }

   public String getDisplayVariant() {
      return getDisplayVariantInternal(this, getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayVariant(ULocale displayLocale) {
      return getDisplayVariantInternal(this, displayLocale);
   }

   public static String getDisplayVariant(String localeID, String displayLocaleID) {
      return getDisplayVariantInternal(new ULocale(localeID), new ULocale(displayLocaleID));
   }

   public static String getDisplayVariant(String localeID, ULocale displayLocale) {
      return getDisplayVariantInternal(new ULocale(localeID), displayLocale);
   }

   private static String getDisplayVariantInternal(ULocale locale, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale).variantDisplayName(locale.getVariant());
   }

   public static String getDisplayKeyword(String keyword) {
      return getDisplayKeywordInternal(keyword, getDefault(ULocale.Category.DISPLAY));
   }

   public static String getDisplayKeyword(String keyword, String displayLocaleID) {
      return getDisplayKeywordInternal(keyword, new ULocale(displayLocaleID));
   }

   public static String getDisplayKeyword(String keyword, ULocale displayLocale) {
      return getDisplayKeywordInternal(keyword, displayLocale);
   }

   private static String getDisplayKeywordInternal(String keyword, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale).keyDisplayName(keyword);
   }

   public String getDisplayKeywordValue(String keyword) {
      return getDisplayKeywordValueInternal(this, keyword, getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayKeywordValue(String keyword, ULocale displayLocale) {
      return getDisplayKeywordValueInternal(this, keyword, displayLocale);
   }

   public static String getDisplayKeywordValue(String localeID, String keyword, String displayLocaleID) {
      return getDisplayKeywordValueInternal(new ULocale(localeID), keyword, new ULocale(displayLocaleID));
   }

   public static String getDisplayKeywordValue(String localeID, String keyword, ULocale displayLocale) {
      return getDisplayKeywordValueInternal(new ULocale(localeID), keyword, displayLocale);
   }

   private static String getDisplayKeywordValueInternal(ULocale locale, String keyword, ULocale displayLocale) {
      keyword = AsciiUtil.toLowerString(keyword.trim());
      String value = locale.getKeywordValue(keyword);
      return LocaleDisplayNames.getInstance(displayLocale).keyValueDisplayName(keyword, value);
   }

   public String getDisplayName() {
      return getDisplayNameInternal(this, getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayName(ULocale displayLocale) {
      return getDisplayNameInternal(this, displayLocale);
   }

   public static String getDisplayName(String localeID, String displayLocaleID) {
      return getDisplayNameInternal(new ULocale(localeID), new ULocale(displayLocaleID));
   }

   public static String getDisplayName(String localeID, ULocale displayLocale) {
      return getDisplayNameInternal(new ULocale(localeID), displayLocale);
   }

   private static String getDisplayNameInternal(ULocale locale, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale).localeDisplayName(locale);
   }

   public String getDisplayNameWithDialect() {
      return getDisplayNameWithDialectInternal(this, getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayNameWithDialect(ULocale displayLocale) {
      return getDisplayNameWithDialectInternal(this, displayLocale);
   }

   public static String getDisplayNameWithDialect(String localeID, String displayLocaleID) {
      return getDisplayNameWithDialectInternal(new ULocale(localeID), new ULocale(displayLocaleID));
   }

   public static String getDisplayNameWithDialect(String localeID, ULocale displayLocale) {
      return getDisplayNameWithDialectInternal(new ULocale(localeID), displayLocale);
   }

   private static String getDisplayNameWithDialectInternal(ULocale locale, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale, LocaleDisplayNames.DialectHandling.DIALECT_NAMES).localeDisplayName(locale);
   }

   public String getCharacterOrientation() {
      return ICUResourceTableAccess.getTableString("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", this, "layout", "characters", "characters");
   }

   public String getLineOrientation() {
      return ICUResourceTableAccess.getTableString("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", this, "layout", "lines", "lines");
   }

   public static ULocale acceptLanguage(String acceptLanguageList, ULocale[] availableLocales, boolean[] fallback) {
      if (fallback != null) {
         fallback[0] = true;
      }

      LocalePriorityList desired;
      try {
         desired = LocalePriorityList.add(acceptLanguageList).build();
      } catch (IllegalArgumentException var9) {
         return null;
      }

      LocaleMatcher.Builder builder = LocaleMatcher.builder();

      for (ULocale locale : availableLocales) {
         builder.addSupportedULocale(locale);
      }

      LocaleMatcher matcher = builder.build();
      LocaleMatcher.Result result = matcher.getBestMatchResult(desired);
      if (result.getDesiredIndex() >= 0) {
         if (fallback != null && result.getDesiredULocale().equals(result.getSupportedULocale())) {
            fallback[0] = false;
         }

         return result.getSupportedULocale();
      } else {
         return null;
      }
   }

   public static ULocale acceptLanguage(ULocale[] acceptLanguageList, ULocale[] availableLocales, boolean[] fallback) {
      if (fallback != null) {
         fallback[0] = true;
      }

      LocaleMatcher.Builder builder = LocaleMatcher.builder();

      for (ULocale locale : availableLocales) {
         builder.addSupportedULocale(locale);
      }

      LocaleMatcher matcher = builder.build();
      LocaleMatcher.Result result;
      if (acceptLanguageList.length == 1) {
         result = matcher.getBestMatchResult(acceptLanguageList[0]);
      } else {
         result = matcher.getBestMatchResult(Arrays.asList(acceptLanguageList));
      }

      if (result.getDesiredIndex() >= 0) {
         if (fallback != null && result.getDesiredULocale().equals(result.getSupportedULocale())) {
            fallback[0] = false;
         }

         return result.getSupportedULocale();
      } else {
         return null;
      }
   }

   public static ULocale acceptLanguage(String acceptLanguageList, boolean[] fallback) {
      return acceptLanguage(acceptLanguageList, getAvailableLocales(), fallback);
   }

   public static ULocale acceptLanguage(ULocale[] acceptLanguageList, boolean[] fallback) {
      return acceptLanguage(acceptLanguageList, getAvailableLocales(), fallback);
   }

   public static ULocale addLikelySubtags(ULocale loc) {
      String[] tags = new String[3];
      String trailing = null;
      int trailingIndex = parseTagString(loc.localeID, tags);
      if (trailingIndex < loc.localeID.length()) {
         trailing = loc.localeID.substring(trailingIndex);
      }

      String newLocaleID = createLikelySubtagsString(tags[0], tags[1], tags[2], trailing);
      return newLocaleID == null ? loc : new ULocale(newLocaleID);
   }

   public static ULocale minimizeSubtags(ULocale loc) {
      return minimizeSubtags(loc, ULocale.Minimize.FAVOR_REGION);
   }

   @Deprecated
   public static ULocale minimizeSubtags(ULocale loc, ULocale.Minimize fieldToFavor) {
      String[] tags = new String[3];
      int trailingIndex = parseTagString(loc.localeID, tags);
      String originalLang = tags[0];
      String originalScript = tags[1];
      String originalRegion = tags[2];
      String originalTrailing = null;
      if (trailingIndex < loc.localeID.length()) {
         originalTrailing = loc.localeID.substring(trailingIndex);
      }

      String maximizedLocaleID = createLikelySubtagsString(originalLang, originalScript, originalRegion, null);
      if (isEmptyString(maximizedLocaleID)) {
         return loc;
      } else {
         String tag = createLikelySubtagsString(originalLang, null, null, null);
         if (tag.equals(maximizedLocaleID)) {
            String newLocaleID = createTagString(originalLang, null, null, originalTrailing);
            return new ULocale(newLocaleID);
         } else {
            if (fieldToFavor == ULocale.Minimize.FAVOR_REGION) {
               if (originalRegion.length() != 0) {
                  tag = createLikelySubtagsString(originalLang, null, originalRegion, null);
                  if (tag.equals(maximizedLocaleID)) {
                     String newLocaleID = createTagString(originalLang, null, originalRegion, originalTrailing);
                     return new ULocale(newLocaleID);
                  }
               }

               if (originalScript.length() != 0) {
                  tag = createLikelySubtagsString(originalLang, originalScript, null, null);
                  if (tag.equals(maximizedLocaleID)) {
                     String newLocaleID = createTagString(originalLang, originalScript, null, originalTrailing);
                     return new ULocale(newLocaleID);
                  }
               }
            } else {
               if (originalScript.length() != 0) {
                  tag = createLikelySubtagsString(originalLang, originalScript, null, null);
                  if (tag.equals(maximizedLocaleID)) {
                     String newLocaleID = createTagString(originalLang, originalScript, null, originalTrailing);
                     return new ULocale(newLocaleID);
                  }
               }

               if (originalRegion.length() != 0) {
                  tag = createLikelySubtagsString(originalLang, null, originalRegion, null);
                  if (tag.equals(maximizedLocaleID)) {
                     String newLocaleID = createTagString(originalLang, null, originalRegion, originalTrailing);
                     return new ULocale(newLocaleID);
                  }
               }
            }

            return loc;
         }
      }
   }

   private static boolean isEmptyString(String string) {
      return string == null || string.length() == 0;
   }

   private static void appendTag(String tag, StringBuilder buffer) {
      if (buffer.length() != 0) {
         buffer.append('_');
      }

      buffer.append(tag);
   }

   private static String createTagString(String lang, String script, String region, String trailing, String alternateTags) {
      LocaleIDParser parser = null;
      boolean regionAppended = false;
      StringBuilder tag = new StringBuilder();
      if (!isEmptyString(lang)) {
         appendTag(lang, tag);
      } else if (isEmptyString(alternateTags)) {
         appendTag("und", tag);
      } else {
         parser = new LocaleIDParser(alternateTags);
         String alternateLang = parser.getLanguage();
         appendTag(!isEmptyString(alternateLang) ? alternateLang : "und", tag);
      }

      if (!isEmptyString(script)) {
         appendTag(script, tag);
      } else if (!isEmptyString(alternateTags)) {
         if (parser == null) {
            parser = new LocaleIDParser(alternateTags);
         }

         String alternateScript = parser.getScript();
         if (!isEmptyString(alternateScript)) {
            appendTag(alternateScript, tag);
         }
      }

      if (!isEmptyString(region)) {
         appendTag(region, tag);
         regionAppended = true;
      } else if (!isEmptyString(alternateTags)) {
         if (parser == null) {
            parser = new LocaleIDParser(alternateTags);
         }

         String alternateRegion = parser.getCountry();
         if (!isEmptyString(alternateRegion)) {
            appendTag(alternateRegion, tag);
            regionAppended = true;
         }
      }

      if (trailing != null && trailing.length() > 1) {
         int separators = 0;
         if (trailing.charAt(0) == '_') {
            if (trailing.charAt(1) == '_') {
               separators = 2;
            }
         } else {
            separators = 1;
         }

         if (regionAppended) {
            if (separators == 2) {
               tag.append(trailing.substring(1));
            } else {
               tag.append(trailing);
            }
         } else {
            if (separators == 1) {
               tag.append('_');
            }

            tag.append(trailing);
         }
      }

      return tag.toString();
   }

   static String createTagString(String lang, String script, String region, String trailing) {
      return createTagString(lang, script, region, trailing, null);
   }

   private static int parseTagString(String localeID, String[] tags) {
      LocaleIDParser parser = new LocaleIDParser(localeID);
      String lang = parser.getLanguage();
      String script = parser.getScript();
      String region = parser.getCountry();
      if (isEmptyString(lang)) {
         tags[0] = "und";
      } else {
         tags[0] = lang;
      }

      if (script.equals("Zzzz")) {
         tags[1] = "";
      } else {
         tags[1] = script;
      }

      if (region.equals("ZZ")) {
         tags[2] = "";
      } else {
         tags[2] = region;
      }

      String variant = parser.getVariant();
      if (!isEmptyString(variant)) {
         int index = localeID.indexOf(variant);
         return index > 0 ? index - 1 : index;
      } else {
         int index = localeID.indexOf(64);
         return index == -1 ? localeID.length() : index;
      }
   }

   private static String lookupLikelySubtags(String localeId) {
      UResourceBundle bundle = UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "likelySubtags");

      try {
         return bundle.getString(localeId);
      } catch (MissingResourceException var3) {
         return null;
      }
   }

   private static String createLikelySubtagsString(String lang, String script, String region, String variants) {
      if (!isEmptyString(script) && !isEmptyString(region)) {
         String searchTag = createTagString(lang, script, region, null);
         String likelySubtags = lookupLikelySubtags(searchTag);
         if (likelySubtags != null) {
            return createTagString(null, null, null, variants, likelySubtags);
         }
      }

      if (!isEmptyString(script)) {
         String searchTag = createTagString(lang, script, null, null);
         String likelySubtags = lookupLikelySubtags(searchTag);
         if (likelySubtags != null) {
            return createTagString(null, null, region, variants, likelySubtags);
         }
      }

      if (!isEmptyString(region)) {
         String searchTag = createTagString(lang, null, region, null);
         String likelySubtags = lookupLikelySubtags(searchTag);
         if (likelySubtags != null) {
            return createTagString(null, script, null, variants, likelySubtags);
         }
      }

      String searchTag = createTagString(lang, null, null, null);
      String likelySubtags = lookupLikelySubtags(searchTag);
      return likelySubtags != null ? createTagString(null, script, region, variants, likelySubtags) : null;
   }

   public String getExtension(char key) {
      if (!LocaleExtensions.isValidKey(key)) {
         throw new IllegalArgumentException("Invalid extension key: " + key);
      } else {
         return this.extensions().getExtensionValue(key);
      }
   }

   public Set<Character> getExtensionKeys() {
      return this.extensions().getKeys();
   }

   public Set<String> getUnicodeLocaleAttributes() {
      return this.extensions().getUnicodeLocaleAttributes();
   }

   public String getUnicodeLocaleType(String key) {
      if (!LocaleExtensions.isValidUnicodeLocaleKey(key)) {
         throw new IllegalArgumentException("Invalid Unicode locale key: " + key);
      } else {
         return this.extensions().getUnicodeLocaleType(key);
      }
   }

   public Set<String> getUnicodeLocaleKeys() {
      return this.extensions().getUnicodeLocaleKeys();
   }

   public String toLanguageTag() {
      BaseLocale base = this.base();
      LocaleExtensions exts = this.extensions();
      if (base.getVariant().equalsIgnoreCase("POSIX")) {
         base = BaseLocale.getInstance(base.getLanguage(), base.getScript(), base.getRegion(), "");
         if (exts.getUnicodeLocaleType("va") == null) {
            InternalLocaleBuilder ilocbld = new InternalLocaleBuilder();

            try {
               ilocbld.setLocale(BaseLocale.ROOT, exts);
               ilocbld.setUnicodeLocaleKeyword("va", "posix");
               exts = ilocbld.getLocaleExtensions();
            } catch (LocaleSyntaxException var10) {
               throw new RuntimeException(var10);
            }
         }
      }

      LanguageTag tag = LanguageTag.parseLocale(base, exts);
      StringBuilder buf = new StringBuilder();
      String subtag = tag.getLanguage();
      if (subtag.length() > 0) {
         buf.append(LanguageTag.canonicalizeLanguage(subtag));
      }

      subtag = tag.getScript();
      if (subtag.length() > 0) {
         buf.append("-");
         buf.append(LanguageTag.canonicalizeScript(subtag));
      }

      subtag = tag.getRegion();
      if (subtag.length() > 0) {
         buf.append("-");
         buf.append(LanguageTag.canonicalizeRegion(subtag));
      }

      List<String> subtags = tag.getVariants();
      ArrayList<String> variants = new ArrayList<>(subtags);
      Collections.sort(variants);

      for (String s : variants) {
         buf.append("-");
         buf.append(LanguageTag.canonicalizeVariant(s));
      }

      for (String s : tag.getExtensions()) {
         buf.append("-");
         buf.append(LanguageTag.canonicalizeExtension(s));
      }

      subtag = tag.getPrivateuse();
      if (subtag.length() > 0) {
         if (buf.length() == 0) {
            buf.append("und");
         }

         buf.append("-");
         buf.append("x").append("-");
         buf.append(LanguageTag.canonicalizePrivateuse(subtag));
      }

      return buf.toString();
   }

   public static ULocale forLanguageTag(String languageTag) {
      LanguageTag tag = LanguageTag.parse(languageTag, null);
      InternalLocaleBuilder bldr = new InternalLocaleBuilder();
      bldr.setLanguageTag(tag);
      return getInstance(bldr.getBaseLocale(), bldr.getLocaleExtensions());
   }

   public static String toUnicodeLocaleKey(String keyword) {
      String bcpKey = KeyTypeData.toBcpKey(keyword);
      if (bcpKey == null && UnicodeLocaleExtension.isKey(keyword)) {
         bcpKey = AsciiUtil.toLowerString(keyword);
      }

      return bcpKey;
   }

   public static String toUnicodeLocaleType(String keyword, String value) {
      String bcpType = KeyTypeData.toBcpType(keyword, value, null, null);
      if (bcpType == null && UnicodeLocaleExtension.isType(value)) {
         bcpType = AsciiUtil.toLowerString(value);
      }

      return bcpType;
   }

   public static String toLegacyKey(String keyword) {
      String legacyKey = KeyTypeData.toLegacyKey(keyword);
      if (legacyKey == null && keyword.matches("[0-9a-zA-Z]+")) {
         legacyKey = AsciiUtil.toLowerString(keyword);
      }

      return legacyKey;
   }

   public static String toLegacyType(String keyword, String value) {
      String legacyType = KeyTypeData.toLegacyType(keyword, value, null, null);
      if (legacyType == null && value.matches("[0-9a-zA-Z]+([_/\\-][0-9a-zA-Z]+)*")) {
         legacyType = AsciiUtil.toLowerString(value);
      }

      return legacyType;
   }

   private static ULocale getInstance(BaseLocale base, LocaleExtensions exts) {
      String id = lscvToID(base.getLanguage(), base.getScript(), base.getRegion(), base.getVariant());
      Set<Character> extKeys = exts.getKeys();
      if (!extKeys.isEmpty()) {
         TreeMap<String, String> kwds = new TreeMap<>();

         for (Character key : extKeys) {
            Extension ext = exts.getExtension(key);
            if (ext instanceof UnicodeLocaleExtension) {
               UnicodeLocaleExtension uext = (UnicodeLocaleExtension)ext;

               for (String bcpKey : uext.getUnicodeLocaleKeys()) {
                  String bcpType = uext.getUnicodeLocaleType(bcpKey);
                  String lkey = toLegacyKey(bcpKey);
                  String ltype = toLegacyType(bcpKey, bcpType.length() == 0 ? "yes" : bcpType);
                  if (lkey.equals("va") && ltype.equals("posix") && base.getVariant().length() == 0) {
                     id = id + "_POSIX";
                  } else {
                     kwds.put(lkey, ltype);
                  }
               }

               Set<String> uattributes = uext.getUnicodeLocaleAttributes();
               if (uattributes.size() > 0) {
                  StringBuilder attrbuf = new StringBuilder();

                  for (String attr : uattributes) {
                     if (attrbuf.length() > 0) {
                        attrbuf.append('-');
                     }

                     attrbuf.append(attr);
                  }

                  kwds.put("attribute", attrbuf.toString());
               }
            } else {
               kwds.put(String.valueOf(key), ext.getValue());
            }
         }

         if (!kwds.isEmpty()) {
            StringBuilder buf = new StringBuilder(id);
            buf.append("@");
            Set<Entry<String, String>> kset = kwds.entrySet();
            boolean insertSep = false;

            for (Entry<String, String> kwd : kset) {
               if (insertSep) {
                  buf.append(";");
               } else {
                  insertSep = true;
               }

               buf.append(kwd.getKey());
               buf.append("=");
               buf.append(kwd.getValue());
            }

            id = buf.toString();
         }
      }

      return new ULocale(id);
   }

   private BaseLocale base() {
      if (this.baseLocale == null) {
         String variant = "";
         String region = "";
         String script = "";
         String language = "";
         if (!this.equals(ROOT)) {
            LocaleIDParser lp = new LocaleIDParser(this.localeID);
            language = lp.getLanguage();
            script = lp.getScript();
            region = lp.getCountry();
            variant = lp.getVariant();
         }

         this.baseLocale = BaseLocale.getInstance(language, script, region, variant);
      }

      return this.baseLocale;
   }

   private LocaleExtensions extensions() {
      if (this.extensions == null) {
         Iterator<String> kwitr = this.getKeywords();
         if (kwitr == null) {
            this.extensions = LocaleExtensions.EMPTY_EXTENSIONS;
         } else {
            InternalLocaleBuilder intbld = new InternalLocaleBuilder();

            while (kwitr.hasNext()) {
               String key = kwitr.next();
               if (key.equals("attribute")) {
                  String[] uattributes = this.getKeywordValue(key).split("[-_]");

                  for (String uattr : uattributes) {
                     try {
                        intbld.addUnicodeLocaleAttribute(uattr);
                     } catch (LocaleSyntaxException var12) {
                     }
                  }
               } else if (key.length() >= 2) {
                  String bcpKey = toUnicodeLocaleKey(key);
                  String bcpType = toUnicodeLocaleType(key, this.getKeywordValue(key));
                  if (bcpKey != null && bcpType != null) {
                     try {
                        intbld.setUnicodeLocaleKeyword(bcpKey, bcpType);
                     } catch (LocaleSyntaxException var11) {
                     }
                  }
               } else if (key.length() == 1 && key.charAt(0) != 'u') {
                  try {
                     intbld.setExtension(key.charAt(0), this.getKeywordValue(key).replace("_", "-"));
                  } catch (LocaleSyntaxException var10) {
                  }
               }
            }

            this.extensions = intbld.getLocaleExtensions();
         }
      }

      return this.extensions;
   }

   static {
      if (ULocale.JDKLocaleHelper.hasLocaleCategories()) {
         for (ULocale.Category cat : ULocale.Category.values()) {
            int idx = cat.ordinal();
            defaultCategoryLocales[idx] = ULocale.JDKLocaleHelper.getDefault(cat);
            defaultCategoryULocales[idx] = forLocale(defaultCategoryLocales[idx]);
         }
      } else {
         for (ULocale.Category cat : ULocale.Category.values()) {
            int idx = cat.ordinal();
            defaultCategoryLocales[idx] = defaultLocale;
            defaultCategoryULocales[idx] = defaultULocale;
         }
      }

      gKnownCanonicalizedCases = null;
      ACTUAL_LOCALE = new ULocale.Type();
      VALID_LOCALE = new ULocale.Type();
   }

   private static class AliasReplacer {
      private String language;
      private String script;
      private String region;
      private List<String> variants;
      private String extensions;
      private static boolean aliasDataIsLoaded = false;
      private static Map<String, String> languageAliasMap = null;
      private static Map<String, String> scriptAliasMap = null;
      private static Map<String, List<String>> territoryAliasMap = null;
      private static Map<String, String> variantAliasMap = null;
      private static Map<String, String> subdivisionAliasMap = null;

      public AliasReplacer(String language, String script, String region, String variants, String extensions) {
         assert language != null;

         assert script != null;

         assert region != null;

         assert variants != null;

         assert extensions != null;

         this.language = language;
         this.script = script;
         this.region = region;
         if (!variants.isEmpty()) {
            this.variants = new ArrayList<>(Arrays.asList(variants.split("_")));
         }

         this.extensions = extensions;
      }

      public String replace() {
         boolean changed = false;
         loadAliasData();

         for (int count = 0; count++ <= 10; changed = true) {
            if (!this.replaceLanguage(true, true, true)
               && !this.replaceLanguage(true, true, false)
               && !this.replaceLanguage(true, false, true)
               && !this.replaceLanguage(true, false, false)
               && !this.replaceLanguage(false, false, true)
               && !this.replaceRegion()
               && !this.replaceScript()
               && !this.replaceVariant()) {
               if (this.extensions == null && !changed) {
                  return null;
               }

               String result = ULocale.lscvToID(this.language, this.script, this.region, this.variants == null ? "" : Utility.joinStrings("_", this.variants));
               if (this.extensions != null) {
                  boolean keywordChanged = false;
                  ULocale temp = new ULocale(result + this.extensions);
                  Iterator<String> keywords = temp.getKeywords();

                  while (keywords != null && keywords.hasNext()) {
                     String key = keywords.next();
                     if (key.equals("rg") || key.equals("sd") || key.equals("t")) {
                        String value = temp.getKeywordValue(key);
                        String replacement = key.equals("t") ? this.replaceTransformedExtensions(value) : this.replaceSubdivision(value);
                        if (replacement != null) {
                           temp = temp.setKeywordValue(key, replacement);
                           keywordChanged = true;
                        }
                     }
                  }

                  if (keywordChanged) {
                     this.extensions = temp.getName().substring(temp.getBaseName().length());
                     changed = true;
                  }

                  result = result + this.extensions;
               }

               if (changed) {
                  return result;
               }

               return null;
            }
         }

         throw new IllegalArgumentException(
            "Have problem to resolve locale alias of "
               + ULocale.lscvToID(this.language, this.script, this.region, this.variants == null ? "" : Utility.joinStrings("_", this.variants))
               + this.extensions
         );
      }

      private static synchronized void loadAliasData() {
         if (!aliasDataIsLoaded) {
            languageAliasMap = new HashMap<>();
            scriptAliasMap = new HashMap<>();
            territoryAliasMap = new HashMap<>();
            variantAliasMap = new HashMap<>();
            subdivisionAliasMap = new HashMap<>();
            UResourceBundle metadata = UResourceBundle.getBundleInstance(
               "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "metadata", ICUResourceBundle.ICU_DATA_CLASS_LOADER
            );
            UResourceBundle metadataAlias = metadata.get("alias");
            UResourceBundle languageAlias = metadataAlias.get("language");
            UResourceBundle scriptAlias = metadataAlias.get("script");
            UResourceBundle territoryAlias = metadataAlias.get("territory");
            UResourceBundle variantAlias = metadataAlias.get("variant");
            UResourceBundle subdivisionAlias = metadataAlias.get("subdivision");

            for (int i = 0; i < languageAlias.getSize(); i++) {
               UResourceBundle res = languageAlias.get(i);
               String aliasFrom = res.getKey();
               String aliasTo = res.get("replacement").getString();
               Locale testLocale = new Locale(aliasFrom);
               if (!testLocale.getScript().isEmpty() || aliasFrom.startsWith("und") && !testLocale.getCountry().isEmpty()) {
                  throw new IllegalArgumentException("key [" + aliasFrom + "] in alias:language contains unsupported fields combination.");
               }

               languageAliasMap.put(aliasFrom, aliasTo);
            }

            for (int i = 0; i < scriptAlias.getSize(); i++) {
               UResourceBundle res = scriptAlias.get(i);
               String aliasFrom = res.getKey();
               String aliasTo = res.get("replacement").getString();
               if (aliasFrom.length() != 4) {
                  throw new IllegalArgumentException("Incorrect key [" + aliasFrom + "] in alias:script.");
               }

               scriptAliasMap.put(aliasFrom, aliasTo);
            }

            for (int i = 0; i < territoryAlias.getSize(); i++) {
               UResourceBundle res = territoryAlias.get(i);
               String aliasFrom = res.getKey();
               String aliasTo = res.get("replacement").getString();
               if (aliasFrom.length() < 2 || aliasFrom.length() > 3) {
                  throw new IllegalArgumentException("Incorrect key [" + aliasFrom + "] in alias:territory.");
               }

               territoryAliasMap.put(aliasFrom, new ArrayList<>(Arrays.asList(aliasTo.split(" "))));
            }

            int i = 0;

            while (i < variantAlias.getSize()) {
               UResourceBundle res = variantAlias.get(i);
               String aliasFrom = res.getKey();
               String aliasTo = res.get("replacement").getString();
               if (aliasFrom.length() >= 4 && aliasFrom.length() <= 8 && (aliasFrom.length() != 4 || aliasFrom.charAt(0) >= '0' && aliasFrom.charAt(0) <= '9')) {
                  if (aliasTo.length() >= 4 && aliasTo.length() <= 8 && (aliasTo.length() != 4 || aliasTo.charAt(0) >= '0' && aliasTo.charAt(0) <= '9')) {
                     variantAliasMap.put(aliasFrom, aliasTo);
                     i++;
                     continue;
                  }

                  throw new IllegalArgumentException("Incorrect variant [" + aliasTo + "] for the key [" + aliasFrom + "] in alias:variant.");
               }

               throw new IllegalArgumentException("Incorrect key [" + aliasFrom + "] in alias:variant.");
            }

            for (int ix = 0; ix < subdivisionAlias.getSize(); ix++) {
               UResourceBundle res = subdivisionAlias.get(ix);
               String aliasFrom = res.getKey();
               String aliasTo = res.get("replacement").getString().split(" ")[0];
               if (aliasFrom.length() < 3 || aliasFrom.length() > 8) {
                  throw new IllegalArgumentException("Incorrect key [" + aliasFrom + "] in alias:territory.");
               }

               if (aliasTo.length() == 2) {
                  aliasTo = aliasTo + "zzzz";
               } else if (aliasTo.length() < 2 || aliasTo.length() > 8) {
                  throw new IllegalArgumentException("Incorrect value [" + aliasTo + "] in alias:territory.");
               }

               subdivisionAliasMap.put(aliasFrom, aliasTo);
            }

            aliasDataIsLoaded = true;
         }
      }

      private static String generateKey(String language, String region, String variant) {
         assert variant == null || variant.length() >= 4;

         StringBuilder buf = new StringBuilder();
         buf.append(language);
         if (region != null && !region.isEmpty()) {
            buf.append('_');
            buf.append(region);
         }

         if (variant != null && !variant.isEmpty()) {
            buf.append('_');
            buf.append(variant);
         }

         return buf.toString();
      }

      private static String deleteOrReplace(String input, String type, String replacement) {
         return replacement != null && !replacement.isEmpty()
            ? (input != null && !input.isEmpty() ? input : replacement)
            : (type != null && !type.isEmpty() ? null : input);
      }

      private boolean replaceLanguage(boolean checkLanguage, boolean checkRegion, boolean checkVariants) {
         if ((!checkRegion || this.region != null && !this.region.isEmpty()) && (!checkVariants || this.variants != null)) {
            int variantSize = checkVariants ? this.variants.size() : 1;
            String searchLanguage = checkLanguage ? this.language : "und";
            String searchRegion = checkRegion ? this.region : null;
            String searchVariant = null;

            for (int variantIndex = 0; variantIndex < variantSize; variantIndex++) {
               if (checkVariants) {
                  searchVariant = this.variants.get(variantIndex);
               }

               if (searchVariant != null && searchVariant.length() < 4) {
                  searchVariant = null;
               }

               String typeKey = generateKey(searchLanguage, searchRegion, searchVariant);
               String replacement = languageAliasMap.get(typeKey);
               if (replacement != null) {
                  String replacedScript = null;
                  String replacedRegion = null;
                  String replacedVariant = null;
                  String replacedExtensions = null;
                  String replacedLanguage = null;
                  if (replacement.indexOf(95) < 0) {
                     replacedLanguage = replacement.equals("und") ? this.language : replacement;
                  } else {
                     String[] replacementFields = replacement.split("_");
                     replacedLanguage = replacementFields[0];
                     int index = 1;
                     if (replacedLanguage.equals("und")) {
                        replacedLanguage = this.language;
                     }

                     int consumed = replacementFields[0].length() + 1;

                     while (replacementFields.length > index) {
                        String field = replacementFields[index];
                        int len = field.length();
                        if (1 == len) {
                           replacedExtensions = replacement.substring(consumed);
                           break;
                        }

                        if (len >= 2 && len <= 3) {
                           assert replacedRegion == null;

                           replacedRegion = field;
                        } else if (len >= 5 && len <= 8) {
                           assert replacedVariant == null;

                           replacedVariant = field;
                        } else if (len == 4) {
                           if (field.charAt(0) >= '0' && field.charAt(0) <= '9') {
                              assert replacedVariant == null;

                              replacedVariant = field;
                           } else {
                              assert replacedScript == null;

                              replacedScript = field;
                           }
                        }

                        index++;
                        consumed += len + 1;
                     }
                  }

                  replacedScript = deleteOrReplace(this.script, null, replacedScript);
                  replacedRegion = deleteOrReplace(this.region, searchRegion, replacedRegion);
                  replacedVariant = deleteOrReplace(searchVariant, searchVariant, replacedVariant);
                  if (!this.language.equals(replacedLanguage)
                     || !this.script.equals(replacedScript)
                     || !this.region.equals(replacedRegion)
                     || !Objects.equals(searchVariant, replacedVariant)
                     || replacedExtensions != null) {
                     this.language = replacedLanguage;
                     this.script = replacedScript;
                     this.region = replacedRegion;
                     if (searchVariant != null && !searchVariant.isEmpty()) {
                        if (replacedVariant != null && !replacedVariant.isEmpty()) {
                           this.variants.set(variantIndex, replacedVariant);
                        } else {
                           this.variants.remove(variantIndex);
                           if (this.variants.isEmpty()) {
                              this.variants = null;
                           }
                        }
                     }

                     if (replacedExtensions != null && !replacedExtensions.isEmpty()) {
                     }

                     return true;
                  }
               }
            }

            return false;
         } else {
            return false;
         }
      }

      private boolean replaceRegion() {
         if (this.region != null && !this.region.isEmpty()) {
            List<String> replacement = territoryAliasMap.get(this.region);
            if (replacement == null) {
               return false;
            } else {
               String replacedRegion;
               if (replacement.size() > 1) {
                  String regionOfLanguageAndScript = ULocale.addLikelySubtags(new ULocale(this.language, this.script, null)).getCountry();
                  replacedRegion = replacement.contains(regionOfLanguageAndScript) ? regionOfLanguageAndScript : replacement.get(0);
               } else {
                  replacedRegion = replacement.get(0);
               }

               assert !this.region.equals(replacedRegion);

               this.region = replacedRegion;
               return true;
            }
         } else {
            return false;
         }
      }

      private boolean replaceScript() {
         if (this.script != null && !this.script.isEmpty()) {
            String replacement = scriptAliasMap.get(this.script);
            if (replacement == null) {
               return false;
            } else {
               assert !this.script.equals(replacement);

               this.script = replacement;
               return true;
            }
         } else {
            return false;
         }
      }

      private boolean replaceVariant() {
         if (this.variants == null) {
            return false;
         } else {
            for (int i = 0; i < this.variants.size(); i++) {
               String variant = this.variants.get(i);
               String replacement = variantAliasMap.get(variant);
               if (replacement != null) {
                  assert replacement.length() >= 4;

                  assert replacement.length() <= 8;

                  assert replacement.length() != 4 || replacement.charAt(0) >= '0' && replacement.charAt(0) <= '9';

                  if (!variant.equals(replacement)) {
                     this.variants.set(i, replacement);
                     if (variant.equals("heploc")) {
                        this.variants.remove("hepburn");
                        if (this.variants.isEmpty()) {
                           this.variants = null;
                        }
                     }

                     return true;
                  }
               }
            }

            return false;
         }
      }

      private String replaceSubdivision(String subdivision) {
         return subdivisionAliasMap.get(subdivision);
      }

      private String replaceTransformedExtensions(String extensions) {
         StringBuilder builder = new StringBuilder();
         List<String> subtags = new ArrayList<>(Arrays.asList(extensions.split("-")));
         List<String> tfields = new ArrayList<>();
         int processedLength = 0;
         int tlangLength = 0;
         String tkey = "";

         for (String subtag : subtags) {
            if (LanguageTag.isTKey(subtag)) {
               if (tlangLength == 0) {
                  tlangLength = processedLength - 1;
               }

               if (builder.length() > 0) {
                  tfields.add(builder.toString());
                  builder.setLength(0);
               }

               tkey = subtag;
               builder.append(subtag);
            } else if (tlangLength != 0) {
               builder.append("-").append(ULocale.toUnicodeLocaleType(tkey, subtag));
            }

            processedLength += subtag.length() + 1;
         }

         if (builder.length() > 0) {
            tfields.add(builder.toString());
            builder.setLength(0);
         }

         String tlang = tlangLength > 0 ? extensions.substring(0, tlangLength) : (tfields.size() == 0 ? extensions : "");
         if (tlang.length() > 0) {
            String canonicalized = ULocale.createCanonical(ULocale.forLanguageTag(extensions)).toLanguageTag();
            builder.append(AsciiUtil.toLowerString(canonicalized));
         }

         if (tfields.size() > 0) {
            if (builder.length() > 0) {
               builder.append("-");
            }

            Collections.sort(tfields);
            builder.append(Utility.joinStrings("-", tfields));
         }

         return builder.toString();
      }
   }

   public static enum AvailableType {
      DEFAULT,
      ONLY_LEGACY_ALIASES,
      WITH_LEGACY_ALIASES;
   }

   public static final class Builder {
      private final InternalLocaleBuilder _locbld = new InternalLocaleBuilder();

      public ULocale.Builder setLocale(ULocale locale) {
         try {
            this._locbld.setLocale(locale.base(), locale.extensions());
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public ULocale.Builder setLanguageTag(String languageTag) {
         ParseStatus sts = new ParseStatus();
         LanguageTag tag = LanguageTag.parse(languageTag, sts);
         if (sts.isError()) {
            throw new IllformedLocaleException(sts.getErrorMessage(), sts.getErrorIndex());
         } else {
            this._locbld.setLanguageTag(tag);
            return this;
         }
      }

      public ULocale.Builder setLanguage(String language) {
         try {
            this._locbld.setLanguage(language);
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public ULocale.Builder setScript(String script) {
         try {
            this._locbld.setScript(script);
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public ULocale.Builder setRegion(String region) {
         try {
            this._locbld.setRegion(region);
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public ULocale.Builder setVariant(String variant) {
         try {
            this._locbld.setVariant(variant);
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public ULocale.Builder setExtension(char key, String value) {
         try {
            this._locbld.setExtension(key, value);
            return this;
         } catch (LocaleSyntaxException var4) {
            throw new IllformedLocaleException(var4.getMessage(), var4.getErrorIndex());
         }
      }

      public ULocale.Builder setUnicodeLocaleKeyword(String key, String type) {
         try {
            this._locbld.setUnicodeLocaleKeyword(key, type);
            return this;
         } catch (LocaleSyntaxException var4) {
            throw new IllformedLocaleException(var4.getMessage(), var4.getErrorIndex());
         }
      }

      public ULocale.Builder addUnicodeLocaleAttribute(String attribute) {
         try {
            this._locbld.addUnicodeLocaleAttribute(attribute);
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public ULocale.Builder removeUnicodeLocaleAttribute(String attribute) {
         try {
            this._locbld.removeUnicodeLocaleAttribute(attribute);
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public ULocale.Builder clear() {
         this._locbld.clear();
         return this;
      }

      public ULocale.Builder clearExtensions() {
         this._locbld.clearExtensions();
         return this;
      }

      public ULocale build() {
         return ULocale.getInstance(this._locbld.getBaseLocale(), this._locbld.getLocaleExtensions());
      }
   }

   public static enum Category {
      DISPLAY,
      FORMAT;
   }

   private static final class JDKLocaleHelper {
      private static boolean hasLocaleCategories = false;
      private static Method mGetDefault;
      private static Method mSetDefault;
      private static Object eDISPLAY;
      private static Object eFORMAT;

      public static boolean hasLocaleCategories() {
         return hasLocaleCategories;
      }

      public static ULocale toULocale(Locale loc) {
         String language = loc.getLanguage();
         String script = "";
         String country = loc.getCountry();
         String variant = loc.getVariant();
         Set<String> attributes = null;
         Map<String, String> keywords = null;
         script = loc.getScript();
         Set<Character> extKeys = loc.getExtensionKeys();
         if (!extKeys.isEmpty()) {
            for (Character extKey : extKeys) {
               if (extKey == 'u') {
                  Set<String> uAttributes = loc.getUnicodeLocaleAttributes();
                  if (!uAttributes.isEmpty()) {
                     attributes = new TreeSet<>();

                     for (String attr : uAttributes) {
                        attributes.add(attr);
                     }
                  }

                  for (String kwKey : loc.getUnicodeLocaleKeys()) {
                     String kwVal = loc.getUnicodeLocaleType(kwKey);
                     if (kwVal != null) {
                        if (kwKey.equals("va")) {
                           variant = variant.length() == 0 ? kwVal : kwVal + "_" + variant;
                        } else {
                           if (keywords == null) {
                              keywords = new TreeMap<>();
                           }

                           keywords.put(kwKey, kwVal);
                        }
                     }
                  }
               } else {
                  String extVal = loc.getExtension(extKey);
                  if (extVal != null) {
                     if (keywords == null) {
                        keywords = new TreeMap<>();
                     }

                     keywords.put(String.valueOf(extKey), extVal);
                  }
               }
            }
         }

         if (language.equals("no") && country.equals("NO") && variant.equals("NY")) {
            language = "nn";
            variant = "";
         }

         StringBuilder buf = new StringBuilder(language);
         if (script.length() > 0) {
            buf.append('_');
            buf.append(script);
         }

         if (country.length() > 0) {
            buf.append('_');
            buf.append(country);
         }

         if (variant.length() > 0) {
            if (country.length() == 0) {
               buf.append('_');
            }

            buf.append('_');
            buf.append(variant);
         }

         if (attributes != null) {
            StringBuilder attrBuf = new StringBuilder();

            for (String attr : attributes) {
               if (attrBuf.length() != 0) {
                  attrBuf.append('-');
               }

               attrBuf.append(attr);
            }

            if (keywords == null) {
               keywords = new TreeMap<>();
            }

            keywords.put("attribute", attrBuf.toString());
         }

         if (keywords != null) {
            buf.append('@');
            boolean addSep = false;

            for (Entry<String, String> kwEntry : keywords.entrySet()) {
               String kwKeyx = kwEntry.getKey();
               String kwVal = kwEntry.getValue();
               if (kwKeyx.length() != 1) {
                  kwKeyx = ULocale.toLegacyKey(kwKeyx);
                  kwVal = ULocale.toLegacyType(kwKeyx, kwVal.length() == 0 ? "yes" : kwVal);
               }

               if (addSep) {
                  buf.append(';');
               } else {
                  addSep = true;
               }

               buf.append(kwKeyx);
               buf.append('=');
               buf.append(kwVal);
            }
         }

         return new ULocale(ULocale.getName(buf.toString()), loc);
      }

      public static Locale toLocale(ULocale uloc) {
         Locale loc = null;
         String ulocStr = uloc.getName();
         if (uloc.getScript().length() > 0 || ulocStr.contains("@")) {
            String tag = uloc.toLanguageTag();
            tag = AsciiUtil.toUpperString(tag);
            loc = Locale.forLanguageTag(tag);
         }

         if (loc == null) {
            loc = new Locale(uloc.getLanguage(), uloc.getCountry(), uloc.getVariant());
         }

         return loc;
      }

      public static Locale getDefault(ULocale.Category category) {
         if (hasLocaleCategories) {
            Object cat = null;
            switch (category) {
               case DISPLAY:
                  cat = eDISPLAY;
                  break;
               case FORMAT:
                  cat = eFORMAT;
            }

            if (cat != null) {
               try {
                  return (Locale)mGetDefault.invoke(null, cat);
               } catch (InvocationTargetException var3) {
               } catch (IllegalArgumentException var4) {
               } catch (IllegalAccessException var5) {
               }
            }
         }

         return Locale.getDefault();
      }

      public static void setDefault(ULocale.Category category, Locale newLocale) {
         if (hasLocaleCategories) {
            Object cat = null;
            switch (category) {
               case DISPLAY:
                  cat = eDISPLAY;
                  break;
               case FORMAT:
                  cat = eFORMAT;
            }

            if (cat != null) {
               try {
                  mSetDefault.invoke(null, cat, newLocale);
               } catch (InvocationTargetException var4) {
               } catch (IllegalArgumentException var5) {
               } catch (IllegalAccessException var6) {
               }
            }
         }
      }

      static {
         try {
            Class<?> cCategory = null;
            Class<?>[] classes = Locale.class.getDeclaredClasses();

            for (Class<?> c : classes) {
               if (c.getName().equals("java.util.Locale$Category")) {
                  cCategory = c;
                  break;
               }
            }

            if (cCategory != null) {
               mGetDefault = Locale.class.getDeclaredMethod("getDefault", cCategory);
               mSetDefault = Locale.class.getDeclaredMethod("setDefault", cCategory, Locale.class);
               Method mName = cCategory.getMethod("name", (Class<?>[])null);
               Object[] enumConstants = cCategory.getEnumConstants();

               for (Object e : enumConstants) {
                  String catVal = (String)mName.invoke(e, (Object[])null);
                  if (catVal.equals("DISPLAY")) {
                     eDISPLAY = e;
                  } else if (catVal.equals("FORMAT")) {
                     eFORMAT = e;
                  }
               }

               if (eDISPLAY != null && eFORMAT != null) {
                  hasLocaleCategories = true;
               }
            }
         } catch (NoSuchMethodException var9) {
         } catch (IllegalArgumentException var10) {
         } catch (IllegalAccessException var11) {
         } catch (InvocationTargetException var12) {
         } catch (SecurityException var13) {
         }
      }
   }

   @Deprecated
   public static enum Minimize {
      @Deprecated
      FAVOR_SCRIPT,
      @Deprecated
      FAVOR_REGION;
   }

   public static final class Type {
      private Type() {
      }
   }
}
