package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.Assert;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUBinary;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICULocaleService;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUService;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.MissingResourceException;

final class BreakIteratorFactory extends BreakIterator.BreakIteratorServiceShim {
   static final ICULocaleService service = new BreakIteratorFactory.BFService();
   private static final String[] KIND_NAMES = new String[]{"grapheme", "word", "line", "sentence", "title"};

   @Override
   public Object registerInstance(BreakIterator iter, ULocale locale, int kind) {
      iter.setText(new java.text.StringCharacterIterator(""));
      return service.registerObject(iter, locale, kind);
   }

   @Override
   public boolean unregister(Object key) {
      return service.isDefault() ? false : service.unregisterFactory((ICUService.Factory)key);
   }

   @Override
   public Locale[] getAvailableLocales() {
      return service == null ? ICUResourceBundle.getAvailableLocales() : service.getAvailableLocales();
   }

   @Override
   public ULocale[] getAvailableULocales() {
      return service == null ? ICUResourceBundle.getAvailableULocales() : service.getAvailableULocales();
   }

   @Override
   public BreakIterator createBreakIterator(ULocale locale, int kind) {
      if (service.isDefault()) {
         return createBreakInstance(locale, kind);
      } else {
         ULocale[] actualLoc = new ULocale[1];
         BreakIterator iter = (BreakIterator)service.get(locale, kind, actualLoc);
         iter.setLocale(actualLoc[0], actualLoc[0]);
         return iter;
      }
   }

   private static BreakIterator createBreakInstance(ULocale locale, int kind) {
      RuleBasedBreakIterator iter = null;
      ICUResourceBundle rb = ICUResourceBundle.getBundleInstance(
         "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/brkitr", locale, ICUResourceBundle.OpenType.LOCALE_ROOT
      );
      ByteBuffer bytes = null;
      String typeKeyExt = "";
      if (kind == 2) {
         String keyValue = locale.getKeywordValue("lb");
         if (keyValue != null && (keyValue.equals("strict") || keyValue.equals("normal") || keyValue.equals("loose"))) {
            typeKeyExt = "_" + keyValue;
         }

         String language = locale.getLanguage();
         if (language != null && language.equals("ja")) {
            keyValue = locale.getKeywordValue("lw");
            if (keyValue != null && keyValue.equals("phrase")) {
               typeKeyExt = typeKeyExt + "_" + keyValue;
            }
         }
      }

      String brkfname;
      try {
         String typeKey = typeKeyExt.isEmpty() ? KIND_NAMES[kind] : KIND_NAMES[kind] + typeKeyExt;
         brkfname = rb.getStringWithFallback("boundaries/" + typeKey);
         String rulesFileName = "brkitr/" + brkfname;
         bytes = ICUBinary.getData(rulesFileName);
      } catch (Exception var10) {
         throw new MissingResourceException(var10.toString(), "", "");
      }

      try {
         boolean isPhraseBreaking = brkfname != null && brkfname.contains("phrase");
         iter = RuleBasedBreakIterator.getInstanceFromCompiledRules(bytes, isPhraseBreaking);
      } catch (IOException var11) {
         Assert.fail(var11);
      }

      ULocale uloc = ULocale.forLocale(rb.getLocale());
      iter.setLocale(uloc, uloc);
      if (kind == 3) {
         String ssKeyword = locale.getKeywordValue("ss");
         if (ssKeyword != null && ssKeyword.equals("standard")) {
            ULocale base = new ULocale(locale.getBaseName());
            return FilteredBreakIteratorBuilder.getInstance(base).wrapIteratorWithFilter(iter);
         }
      }

      return iter;
   }

   private static class BFService extends ICULocaleService {
      BFService() {
         super("BreakIterator");

         class RBBreakIteratorFactory extends ICULocaleService.ICUResourceBundleFactory {
            @Override
            protected Object handleCreate(ULocale loc, int kind, ICUService srvc) {
               return BreakIteratorFactory.createBreakInstance(loc, kind);
            }
         }

         this.registerFactory(new RBBreakIteratorFactory());
         this.markDefault();
      }

      @Override
      public String validateFallbackLocale() {
         return "";
      }
   }
}
