package com.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICULocaleService;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUService;
import java.util.Locale;

final class CurrencyServiceShim extends Currency.ServiceShim {
   static final ICULocaleService service = new CurrencyServiceShim.CFService();

   @Override
   Locale[] getAvailableLocales() {
      return service.isDefault() ? ICUResourceBundle.getAvailableLocales() : service.getAvailableLocales();
   }

   @Override
   ULocale[] getAvailableULocales() {
      return service.isDefault() ? ICUResourceBundle.getAvailableULocales() : service.getAvailableULocales();
   }

   @Override
   Currency createInstance(ULocale loc) {
      return service.isDefault() ? Currency.createCurrency(loc) : (Currency)service.get(loc);
   }

   @Override
   Object registerInstance(Currency currency, ULocale locale) {
      return service.registerObject(currency, locale);
   }

   @Override
   boolean unregister(Object registryKey) {
      return service.unregisterFactory((ICUService.Factory)registryKey);
   }

   private static class CFService extends ICULocaleService {
      CFService() {
         super("Currency");

         class CurrencyFactory extends ICULocaleService.ICUResourceBundleFactory {
            @Override
            protected Object handleCreate(ULocale loc, int kind, ICUService srvc) {
               return Currency.createCurrency(loc);
            }
         }

         this.registerFactory(new CurrencyFactory());
         this.markDefault();
      }
   }
}
