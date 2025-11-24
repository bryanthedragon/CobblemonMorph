
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.util.VersionInfo;
import java.util.MissingResourceException;

public final class ICUDataVersion {
    private static final String U_ICU_VERSION_BUNDLE = "icuver";
    private static final String U_ICU_DATA_KEY = "DataVersion";

    public static VersionInfo getDataVersion() {
        UResourceBundle icudatares = null;
        try {
            icudatares = UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", U_ICU_VERSION_BUNDLE, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            icudatares = icudatares.get(U_ICU_DATA_KEY);
        }
        catch (MissingResourceException ex) {
            return null;
        }
        return VersionInfo.getInstance(icudatares.getString());
    }
}

