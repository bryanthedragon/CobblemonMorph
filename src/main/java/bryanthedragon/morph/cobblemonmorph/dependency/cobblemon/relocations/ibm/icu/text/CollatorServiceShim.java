package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICULocaleService;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUService;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.CollationLoader;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.CollationTailoring;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUCloneNotSupportedException;
import com.cobblemon.mod.relocations.ibm.icu.util.Output;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

final class CollatorServiceShim extends Collator.ServiceShim {
   private static ICULocaleService service = new CollatorServiceShim.CService();

   @Override
   Collator getInstance(ULocale locale) {
      try {
         ULocale[] actualLoc = new ULocale[1];
         Collator coll = (Collator)service.get(locale, actualLoc);
         if (coll == null) {
            throw new MissingResourceException("Could not locate Collator data", "", "");
         } else {
            return (Collator)coll.clone();
         }
      } catch (CloneNotSupportedException var4) {
         throw new ICUCloneNotSupportedException(var4);
      }
   }

   @Override
   Object registerInstance(Collator collator, ULocale locale) {
      collator.setLocale(locale, locale);
      return service.registerObject(collator, locale);
   }

   @Override
   Object registerFactory(Collator.CollatorFactory f) {
      class CFactory extends ICULocaleService.LocaleKeyFactory {
         Collator.CollatorFactory delegate;

         CFactory(Collator.CollatorFactory fctry) {
            super(fctry.visible());
            this.delegate = fctry;
         }

         @Override
         public Object handleCreate(ULocale loc, int kind, ICUService srvc) {
            return this.delegate.createCollator(loc);
         }

         @Override
         public String getDisplayName(String id, ULocale displayLocale) {
            ULocale objectLocale = new ULocale(id);
            return this.delegate.getDisplayName(objectLocale, displayLocale);
         }

         @Override
         public Set<String> getSupportedIDs() {
            return this.delegate.getSupportedLocaleIDs();
         }
      }

      return service.registerFactory(new CFactory(f));
   }

   @Override
   boolean unregister(Object registryKey) {
      return service.unregisterFactory((ICUService.Factory)registryKey);
   }

   @Override
   Locale[] getAvailableLocales() {
      Locale[] result;
      if (service.isDefault()) {
         result = ICUResourceBundle.getAvailableLocales(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER
         );
      } else {
         result = service.getAvailableLocales();
      }

      return result;
   }

   @Override
   ULocale[] getAvailableULocales() {
      ULocale[] result;
      if (service.isDefault()) {
         result = ICUResourceBundle.getAvailableULocales(
            "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER
         );
      } else {
         result = service.getAvailableULocales();
      }

      return result;
   }

   @Override
   String getDisplayName(ULocale objectLocale, ULocale displayLocale) {
      String id = objectLocale.getName();
      return service.getDisplayName(id, displayLocale);
   }

   private static final Collator makeInstance(ULocale desiredLocale) {
      Output<ULocale> validLocale = new Output<>(ULocale.ROOT);
      CollationTailoring t = CollationLoader.loadTailoring(desiredLocale, validLocale);
      return new RuleBasedCollator(t, validLocale.value);
   }

   private static class CService extends ICULocaleService {
      CService() {
         super("Collator");

         class CollatorFactory extends ICULocaleService.ICUResourceBundleFactory {
            CollatorFactory() {
               super("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/coll");
            }

            @Override
            protected Object handleCreate(ULocale uloc, int kind, ICUService srvc) {
               return CollatorServiceShim.makeInstance(uloc);
            }
         }

         this.registerFactory(new CollatorFactory());
         this.markDefault();
      }

      @Override
      public String validateFallbackLocale() {
         return "";
      }

      @Override
      protected Object handleDefault(ICUService.Key key, String[] actualIDReturn) {
         if (actualIDReturn != null) {
            actualIDReturn[0] = "root";
         }

         try {
            return CollatorServiceShim.makeInstance(ULocale.ROOT);
         } catch (MissingResourceException var4) {
            return null;
         }
      }
   }
}
