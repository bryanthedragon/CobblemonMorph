
package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import java.util.BitSet;

public final class StringEscape {
    private static final BitSet dontEscapeSet;

    private StringEscape() {
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString escape(TruffleString s) {
        int len = Strings.length(s);
        TruffleStringBuilder out = null;
        for (int i = 0; i < len; ++i) {
            char ch;
            char c = Strings.charAt(s, i);
            if (dontEscapeSet.get(c)) {
                if (out == null) continue;
                Strings.builderAppend(out, c);
                continue;
            }
            if (out == null) {
                out = StringEscape.allocStringBuilder(s, i, len + 16);
            }
            Strings.builderAppend(out, '%');
            if (c < '\u0100') {
                ch = StringEscape.hexChar(c >> 4 & 0xF);
                Strings.builderAppend(out, ch);
                ch = StringEscape.hexChar(c & 0xF);
                Strings.builderAppend(out, ch);
                continue;
            }
            Strings.builderAppend(out, 'u');
            ch = StringEscape.hexChar(c >> 12 & 0xF);
            Strings.builderAppend(out, ch);
            ch = StringEscape.hexChar(c >> 8 & 0xF);
            Strings.builderAppend(out, ch);
            ch = StringEscape.hexChar(c >> 4 & 0xF);
            Strings.builderAppend(out, ch);
            ch = StringEscape.hexChar(c & 0xF);
            Strings.builderAppend(out, ch);
        }
        return out != null ? Strings.builderToString(out) : s;
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString unescape(TruffleString string) {
        int len = Strings.length(string);
        TruffleStringBuilder sb = null;
        int k = 0;
        while (k < len) {
            char c = Strings.charAt(string, k);
            if (c == '%') {
                if (sb == null) {
                    sb = StringEscape.allocStringBuilder(string, k, len);
                }
                if (k <= len - 6 && StringEscape.unescapeU0000(string, sb, k)) {
                    k += 6;
                    continue;
                }
                if (k <= len - 3 && StringEscape.unescape00(string, sb, k)) {
                    k += 3;
                    continue;
                }
            }
            if (sb != null) {
                Strings.builderAppend(sb, c);
            }
            ++k;
        }
        return sb != null ? Strings.builderToString(sb) : string;
    }

    private static TruffleStringBuilder allocStringBuilder(TruffleString s, int i, int estimatedLength) {
        TruffleStringBuilder sb = Strings.builderCreate(estimatedLength);
        if (i > 0) {
            Strings.builderAppend(sb, s, 0, i);
        }
        return sb;
    }

    private static boolean unescapeU0000(TruffleString string, TruffleStringBuilder builder, int k) {
        char c1 = Strings.charAt(string, k + 1);
        if (c1 == 'u') {
            char c2 = Strings.charAt(string, k + 2);
            char c3 = Strings.charAt(string, k + 3);
            char c4 = Strings.charAt(string, k + 4);
            char c5 = Strings.charAt(string, k + 5);
            if (JSRuntime.isHex(c2) && JSRuntime.isHex(c3) && JSRuntime.isHex(c4) && JSRuntime.isHex(c5)) {
                char newC = (char)(StringEscape.hexVal(c2) * 16 * 16 * 16 + StringEscape.hexVal(c3) * 16 * 16 + StringEscape.hexVal(c4) * 16 + StringEscape.hexVal(c5));
                Strings.builderAppend(builder, newC);
                return true;
            }
        }
        return false;
    }

    private static boolean unescape00(TruffleString string, TruffleStringBuilder builder, int k) {
        char c1 = Strings.charAt(string, k + 1);
        char c2 = Strings.charAt(string, k + 2);
        if (JSRuntime.isHex(c1) && JSRuntime.isHex(c2)) {
            char newC = (char)(StringEscape.hexVal(c1) * 16 + StringEscape.hexVal(c2));
            Strings.builderAppend(builder, newC);
            return true;
        }
        return false;
    }

    private static char hexChar(int value2) {
        if (value2 < 10) {
            return (char)(48 + value2);
        }
        return (char)(65 + value2 - 10);
    }

    private static int hexVal(char c) {
        int value2 = JSRuntime.valueInHex(c);
        if (value2 < 0) {
            assert (false) : "not a hex character";
            return 0;
        }
        return value2;
    }

    static {
        BitSet unescaped = new BitSet(128);
        unescaped.set(97, 123);
        unescaped.set(65, 91);
        unescaped.set(48, 58);
        unescaped.set(64);
        unescaped.set(42);
        unescaped.set(95);
        unescaped.set(43);
        unescaped.set(45);
        unescaped.set(46);
        unescaped.set(47);
        dontEscapeSet = unescaped;
    }
}

