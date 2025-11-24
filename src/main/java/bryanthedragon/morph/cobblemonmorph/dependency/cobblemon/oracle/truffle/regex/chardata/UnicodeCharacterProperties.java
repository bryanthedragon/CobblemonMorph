
package com.oracle.truffle.regex.chardata;

import com.oracle.truffle.regex.chardata.CharacterSet;
import com.oracle.truffle.regex.charset.UnicodeProperties;

public final class UnicodeCharacterProperties {
    public static CharacterSet getUnicodeProperty(String propertySpec) {
        return UnicodeProperties.getProperty(propertySpec);
    }
}

