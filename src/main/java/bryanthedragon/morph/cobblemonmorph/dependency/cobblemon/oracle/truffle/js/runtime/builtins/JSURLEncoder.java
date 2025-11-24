
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.Strings;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

public final class JSURLEncoder {
    static final BitSet unreservedURISet;
    static final BitSet reservedURISet;
    private final boolean isSpecial;
    private final Charset charset;

    public JSURLEncoder(boolean isSpecial) {
        this(isSpecial, StandardCharsets.UTF_8);
    }

    public JSURLEncoder(boolean isSpecial, Charset charset) {
        this.charset = charset;
        this.isSpecial = isSpecial;
    }

    @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
    public TruffleString encode(TruffleString s) {
        int length = Strings.length(s);
        TruffleStringBuilder sb = null;
        CharsetEncoder encoder = null;
        int i = 0;
        String javaStr = Strings.toJavaString(s);
        while (i < length) {
            char c = Strings.charAt(s, i);
            if (this.needsNoEncoding(c)) {
                if (sb != null) {
                    Strings.builderAppend(sb, c);
                }
                ++i;
                continue;
            }
            if (sb == null) {
                sb = JSURLEncoder.allocStringBuilder(s, i, length + 16);
            }
            if (encoder == null) {
                encoder = this.charset.newEncoder();
            }
            i = this.encodeConvert(javaStr, i, c, sb, encoder);
        }
        return sb != null ? Strings.builderToString(sb) : s;
    }

    static TruffleStringBuilder allocStringBuilder(TruffleString s, int i, int estimatedLength) {
        TruffleStringBuilder sb = Strings.builderCreate(estimatedLength);
        if (i > 0) {
            Strings.builderAppend(sb, s, 0, i);
        }
        return sb;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int encodeConvert(String s, int iParam, int cParam, TruffleStringBuilder buffer, CharsetEncoder encoder) {
        char c;
        int i = iParam;
        int c2 = cParam;
        int startPos = i;
        do {
            if (56320 <= c2 && c2 <= 57343) {
                throw JSURLEncoder.cannotEscapeError();
            }
            if (c2 >= 55296 && c2 <= 56319) {
                if (i + 1 >= s.length()) throw JSURLEncoder.cannotEscapeError();
                char d = s.charAt(i + 1);
                if (d < '\udc00' || d > '\udfff') throw JSURLEncoder.cannotEscapeError();
                ++i;
            }
            if (++i >= s.length()) break;
            c = s.charAt(i);
            c2 = c;
        } while (!this.needsNoEncoding(c));
        ByteBuffer bb = JSURLEncoder.encodeSubstring(s, startPos, i, encoder);
        byte[] ba = bb.array();
        assert (bb.arrayOffset() + bb.position() == 0);
        int length = bb.limit();
        for (int j = 0; j < length; ++j) {
            Strings.builderAppend(buffer, '%');
            char ch = JSURLEncoder.charForDigit(ba[j] >> 4 & 0xF, 16);
            Strings.builderAppend(buffer, ch);
            ch = JSURLEncoder.charForDigit(ba[j] & 0xF, 16);
            Strings.builderAppend(buffer, ch);
        }
        return i;
    }

    public static char charForDigit(int digit, int radix) {
        assert (digit >= 0 && digit < radix && radix >= 2 && radix <= 36);
        if (digit < 10) {
            return (char)(48 + digit);
        }
        return (char)(55 + digit);
    }

    private static JSException cannotEscapeError() {
        throw Errors.createURIError("cannot escape");
    }

    private static ByteBuffer encodeSubstring(String s, int off, int len, CharsetEncoder encoder) {
        CharBuffer cb = CharBuffer.wrap(s, off, len);
        try {
            return encoder.encode(cb);
        }
        catch (CharacterCodingException ex) {
            throw JSURLEncoder.cannotEscapeError();
        }
    }

    private boolean needsNoEncoding(int c) {
        if (this.isSpecial) {
            return unreservedURISet.get(c) || reservedURISet.get(c);
        }
        return unreservedURISet.get(c);
    }

    static {
        BitSet unreserved = new BitSet(128);
        unreserved.set(97, 123);
        unreserved.set(65, 91);
        unreserved.set(48, 58);
        unreserved.set(45);
        unreserved.set(95);
        unreserved.set(46);
        unreserved.set(42);
        unreserved.set(33);
        unreserved.set(126);
        unreserved.set(39);
        unreserved.set(40);
        unreserved.set(41);
        BitSet reserved = new BitSet(128);
        reserved.set(59);
        reserved.set(47);
        reserved.set(63);
        reserved.set(58);
        reserved.set(64);
        reserved.set(38);
        reserved.set(61);
        reserved.set(43);
        reserved.set(36);
        reserved.set(44);
        reserved.set(35);
        unreservedURISet = unreserved;
        reservedURISet = reserved;
    }
}

