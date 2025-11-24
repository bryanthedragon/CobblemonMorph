
package com.oracle.js.parser;

import com.oracle.truffle.api.strings.TruffleString;

public final class ParserStrings {
    public static TruffleString constant(String s) {
        TruffleString ret = ParserStrings.fromJavaString(s);
        ret.hashCodeUncached(TruffleString.Encoding.UTF_16);
        return ret;
    }

    public static TruffleString fromJavaString(String s) {
        return TruffleString.fromJavaStringUncached(s, TruffleString.Encoding.UTF_16);
    }
}

