
package com.oracle.truffle.js.parser.json;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.runtime.Strings;

public class JSONParserUtil {
    public static TruffleString quote(TruffleString value2) {
        TruffleStringBuilder product = Strings.builderCreate();
        Strings.builderAppend(product, Strings.DOUBLE_QUOTE);
        int len = Strings.length(value2);
        for (int i = 0; i < len; ++i) {
            char ch = Strings.charAt(value2, i);
            if (ch < ' ') {
                if (ch == '\b') {
                    Strings.builderAppend(product, Strings.BACKSLASH_B);
                    continue;
                }
                if (ch == '\f') {
                    Strings.builderAppend(product, Strings.BACKSLASH_F);
                    continue;
                }
                if (ch == '\n') {
                    Strings.builderAppend(product, Strings.BACKSLASH_N);
                    continue;
                }
                if (ch == '\r') {
                    Strings.builderAppend(product, Strings.BACKSLASH_R);
                    continue;
                }
                if (ch == '\t') {
                    Strings.builderAppend(product, Strings.BACKSLASH_T);
                    continue;
                }
                Strings.builderAppend(product, Strings.BACKSLASH_U);
                TruffleString hex = Strings.intToHexString(ch);
                for (int j = Strings.length(hex); j < 4; ++j) {
                    Strings.builderAppend(product, '0');
                }
                Strings.builderAppend(product, hex);
                continue;
            }
            if (ch == '\\') {
                Strings.builderAppend(product, Strings.BACKSLASH_BACKSLASH);
                continue;
            }
            if (ch == '\"') {
                Strings.builderAppend(product, Strings.BACKSLASH_DOUBLE_QUOTE);
                continue;
            }
            Strings.builderAppend(product, ch);
        }
        Strings.builderAppend(product, Strings.DOUBLE_QUOTE);
        return Strings.builderToString(product);
    }
}

