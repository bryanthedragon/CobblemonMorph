
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICULocaleService;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUService;
import com.cobblemon.mod.relocations.ibm.icu.util.Currency;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.util.Locale;

final class CurrencyServiceShim
extends Currency.ServiceShim {
    static final ICULocaleService service = new CFService();

    CurrencyServiceShim() {
    }

    @Override
    Locale[] getAvailableLocales() {
        if (service.isDefault()) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return service.getAvailableLocales();
    }

    @Override
    ULocale[] getAvailableULocales() {
        if (service.isDefault()) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return service.getAvailableULocales();
    }

    @Override
    Currency createInstance(ULocale loc) {
        if (service.isDefault()) {
            return Currency.createCurrency(loc);
        }
        Currency curr = (Currency)service.get(loc);
        return curr;
    }

    @Override
    Object registerInstance(Currency currency, ULocale locale) {
        return service.registerObject((Object)currency, locale);
    }

    @Override
    boolean unregister(Object registryKey) {
        return service.unregisterFactory((ICUService.Factory)registryKey);
    }

    private static class CFService
    extends ICULocaleService {
        CFService() {
            super("Currency");
            class CurrencyFactory
            extends ICULocaleService.ICUResourceBundleFactory {
                CurrencyFactory() {
                }

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

