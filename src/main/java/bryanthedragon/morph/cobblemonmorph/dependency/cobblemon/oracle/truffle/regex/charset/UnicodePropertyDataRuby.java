
package com.oracle.truffle.regex.charset;

import com.oracle.truffle.regex.charset.UnicodePropertyData;
import org.graalvm.collections.EconomicMap;

class UnicodePropertyDataRuby {
    static final EconomicMap<String, String> PROPERTY_ALIASES_LOWERCASE = EconomicMap.create(UnicodePropertyData.PROPERTY_ALIASES.size());
    static final EconomicMap<String, String> GENERAL_CATEGORY_ALIASES_LOWERCASE = EconomicMap.create(UnicodePropertyData.GENERAL_CATEGORY_ALIASES.size());
    static final EconomicMap<String, String> SCRIPT_ALIASES_LOWERCASE = EconomicMap.create(UnicodePropertyData.SCRIPT_ALIASES.size());

    UnicodePropertyDataRuby() {
    }

    static {
        for (String propertyAlias : UnicodePropertyData.PROPERTY_ALIASES.getKeys()) {
            PROPERTY_ALIASES_LOWERCASE.put(propertyAlias.toLowerCase(), propertyAlias);
        }
        for (String generalCategoryAlias : UnicodePropertyData.GENERAL_CATEGORY_ALIASES.getKeys()) {
            GENERAL_CATEGORY_ALIASES_LOWERCASE.put(generalCategoryAlias.toLowerCase(), generalCategoryAlias);
        }
        for (String scriptAlias : UnicodePropertyData.SCRIPT_ALIASES.getKeys()) {
            SCRIPT_ALIASES_LOWERCASE.put(scriptAlias.toLowerCase(), scriptAlias);
        }
    }
}

