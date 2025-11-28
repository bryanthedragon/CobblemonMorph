package com.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;
import java.util.MissingResourceException;

public final class LocaleData {
   private static final String MEASUREMENT_SYSTEM = "MeasurementSystem";
   private static final String PAPER_SIZE = "PaperSize";
   private static final String LOCALE_DISPLAY_PATTERN = "localeDisplayPattern";
   private static final String PATTERN = "pattern";
   private static final String SEPARATOR = "separator";
   private boolean noSubstitute;
   private ICUResourceBundle bundle;
   private ICUResourceBundle langBundle;
   public static final int ES_STANDARD = 0;
   public static final int ES_AUXILIARY = 1;
   public static final int ES_INDEX = 2;
   @Deprecated
   public static final int ES_CURRENCY = 3;
   public static final int ES_PUNCTUATION = 4;
   @Deprecated
   public static final int ES_COUNT = 5;
   public static final int QUOTATION_START = 0;
   public static final int QUOTATION_END = 1;
   public static final int ALT_QUOTATION_START = 2;
   public static final int ALT_QUOTATION_END = 3;
   @Deprecated
   public static final int DELIMITER_COUNT = 4;
   private static final String[] DELIMITER_TYPES = new String[]{"quotationStart", "quotationEnd", "alternateQuotationStart", "alternateQuotationEnd"};
   private static VersionInfo gCLDRVersion = null;

   private LocaleData() {
   }

   public static UnicodeSet getExemplarSet(ULocale locale, int options) {
      return getInstance(locale).getExemplarSet(options, 0);
   }

   public static UnicodeSet getExemplarSet(ULocale locale, int options, int extype) {
      return getInstance(locale).getExemplarSet(options, extype);
   }

   public UnicodeSet getExemplarSet(int options, int extype) {
      String[] exemplarSetTypes = new String[]{
         "ExemplarCharacters", "AuxExemplarCharacters", "ExemplarCharactersIndex", "ExemplarCharactersCurrency", "ExemplarCharactersPunctuation"
      };
      if (extype == 3) {
         return this.noSubstitute ? null : UnicodeSet.EMPTY;
      } else {
         try {
            String aKey = exemplarSetTypes[extype];
            ICUResourceBundle stringBundle = (ICUResourceBundle)this.bundle.get(aKey);
            if (this.noSubstitute && !this.bundle.isRoot() && stringBundle.isRoot()) {
               return null;
            } else {
               String unicodeSetPattern = stringBundle.getString();
               return new UnicodeSet(unicodeSetPattern, 1 | options);
            }
         } catch (ArrayIndexOutOfBoundsException var7) {
            throw new IllegalArgumentException(var7);
         } catch (Exception var8) {
            return this.noSubstitute ? null : UnicodeSet.EMPTY;
         }
      }
   }

   public static final LocaleData getInstance(ULocale locale) {
      LocaleData ld = new LocaleData();
      ld.bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", locale);
      ld.langBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/lang", locale);
      ld.noSubstitute = false;
      return ld;
   }

   public static final LocaleData getInstance() {
      return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public void setNoSubstitute(boolean setting) {
      this.noSubstitute = setting;
   }

   public boolean getNoSubstitute() {
      return this.noSubstitute;
   }

   public String getDelimiter(int type) {
      ICUResourceBundle delimitersBundle = (ICUResourceBundle)this.bundle.get("delimiters");
      ICUResourceBundle stringBundle = delimitersBundle.getWithFallback(DELIMITER_TYPES[type]);
      return this.noSubstitute && !this.bundle.isRoot() && stringBundle.isRoot() ? null : stringBundle.getString();
   }

   private static UResourceBundle measurementTypeBundleForLocale(ULocale locale, String measurementType) {
      UResourceBundle measTypeBundle = null;
      String region = ULocale.getRegionForSupplementalData(locale, true);

      try {
         UResourceBundle rb = UResourceBundle.getBundleInstance(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER
         );
         UResourceBundle measurementData = rb.get("measurementData");
         UResourceBundle measDataBundle = null;

         try {
            measDataBundle = measurementData.get(region);
            measTypeBundle = measDataBundle.get(measurementType);
         } catch (MissingResourceException var8) {
            measDataBundle = measurementData.get("001");
            measTypeBundle = measDataBundle.get(measurementType);
         }
      } catch (MissingResourceException var9) {
      }

      return measTypeBundle;
   }

   public static final LocaleData.MeasurementSystem getMeasurementSystem(ULocale locale) {
      UResourceBundle sysBundle = measurementTypeBundleForLocale(locale, "MeasurementSystem");
      switch (sysBundle.getInt()) {
         case 0:
            return LocaleData.MeasurementSystem.SI;
         case 1:
            return LocaleData.MeasurementSystem.US;
         case 2:
            return LocaleData.MeasurementSystem.UK;
         default:
            return null;
      }
   }

   public static final LocaleData.PaperSize getPaperSize(ULocale locale) {
      UResourceBundle obj = measurementTypeBundleForLocale(locale, "PaperSize");
      int[] size = obj.getIntVector();
      return new LocaleData.PaperSize(size[0], size[1]);
   }

   public String getLocaleDisplayPattern() {
      ICUResourceBundle locDispBundle = (ICUResourceBundle)this.langBundle.get("localeDisplayPattern");
      return locDispBundle.getStringWithFallback("pattern");
   }

   public String getLocaleSeparator() {
      String sub0 = "{0}";
      String sub1 = "{1}";
      ICUResourceBundle locDispBundle = (ICUResourceBundle)this.langBundle.get("localeDisplayPattern");
      String localeSeparator = locDispBundle.getStringWithFallback("separator");
      int index0 = localeSeparator.indexOf(sub0);
      int index1 = localeSeparator.indexOf(sub1);
      return index0 >= 0 && index1 >= 0 && index0 <= index1 ? localeSeparator.substring(index0 + sub0.length(), index1) : localeSeparator;
   }

   public static VersionInfo getCLDRVersion() {
      if (gCLDRVersion == null) {
         UResourceBundle supplementalDataBundle = UResourceBundle.getBundleInstance(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER
         );
         UResourceBundle cldrVersionBundle = supplementalDataBundle.get("cldrVersion");
         gCLDRVersion = VersionInfo.getInstance(cldrVersionBundle.getString());
      }

      return gCLDRVersion;
   }

   public static final class MeasurementSystem {
      public static final LocaleData.MeasurementSystem SI = new LocaleData.MeasurementSystem();
      public static final LocaleData.MeasurementSystem US = new LocaleData.MeasurementSystem();
      public static final LocaleData.MeasurementSystem UK = new LocaleData.MeasurementSystem();

      private MeasurementSystem() {
      }
   }

   public static final class PaperSize {
      private int height;
      private int width;

      private PaperSize(int h, int w) {
         this.height = h;
         this.width = w;
      }

      public int getHeight() {
         return this.height;
      }

      public int getWidth() {
         return this.width;
      }
   }
}
