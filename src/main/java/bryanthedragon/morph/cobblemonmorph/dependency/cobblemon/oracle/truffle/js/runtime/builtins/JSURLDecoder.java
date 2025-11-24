
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSURLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;

public class JSURLDecoder {
    private final boolean isSpecial;

    public JSURLDecoder(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
    public TruffleString decode(TruffleString string) {
        int strLen = Strings.length(string);
        TruffleStringBuilder sb = null;
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
        for (int k = 0; k < strLen; ++k) {
            char c = Strings.charAt(string, k);
            if (c != '%') {
                if (sb == null) continue;
                Strings.builderAppend(sb, c);
                continue;
            }
            if (sb == null) {
                sb = JSURLEncoder.allocStringBuilder(string, k, strLen);
            }
            k = this.decodeConvert(string, strLen, k, sb, decoder);
        }
        return sb != null ? Strings.builderToString(sb) : string;
    }

    private int decodeConvert(TruffleString string, int strLen, int start2, TruffleStringBuilder buffer, CharsetDecoder decoder) {
        int k = start2;
        if (k + 2 >= strLen) {
            throw Errors.createURIError("illegal escape sequence");
        }
        int hex1 = JSURLDecoder.getHexValue(Strings.charAt(string, k + 1));
        int hex2 = JSURLDecoder.getHexValue(Strings.charAt(string, k + 2));
        byte b = (byte)((hex1 << 4) + hex2);
        k += 2;
        if ((b & 0x80) == 0) {
            char c = (char)b;
            if (!this.isReserved(c)) {
                Strings.builderAppend(buffer, c);
            } else {
                Strings.builderAppend(buffer, string, start2, k + 1);
            }
        } else {
            k = this.decodeConvertIntl(string, strLen, k, b, buffer, decoder);
        }
        return k;
    }

    private int decodeConvertIntl(TruffleString string, int strLen, int kParam, byte b, TruffleStringBuilder buffer, CharsetDecoder decoder) {
        int k = kParam;
        int n = JSURLDecoder.findN(b);
        if (n == 1 || n > 4) {
            throw JSURLDecoder.invalidEncodingError();
        }
        byte[] octetsB = new byte[n];
        octetsB[0] = b;
        if (k + 3 * (n - 1) >= strLen) {
            throw JSURLDecoder.invalidEncodingError();
        }
        for (int j = 1; j < n; ++j) {
            int hex4;
            if (Strings.charAt(string, ++k) != '%') {
                throw JSURLDecoder.invalidEncodingError();
            }
            int hex3 = JSURLDecoder.getHexValue(Strings.charAt(string, k + 1));
            byte b2 = (byte)((hex3 << 4) + (hex4 = JSURLDecoder.getHexValue(Strings.charAt(string, k + 2))));
            if ((b2 & 0xC0) != 128) {
                throw JSURLDecoder.invalidEncodingError();
            }
            k += 2;
            octetsB[j] = b2;
        }
        ByteBuffer bb = ByteBuffer.wrap(octetsB);
        CharBuffer cb = CharBuffer.wrap(new char[2]);
        decoder.reset();
        cb.rewind();
        CoderResult coderResult = decoder.decode(bb, cb, true);
        if (coderResult.isError()) {
            throw JSURLDecoder.invalidEncodingError();
        }
        if (cb.position() == 1) {
            assert (!this.isReserved(cb.get(0)));
            Strings.builderAppend(buffer, cb.get(0));
        } else {
            Strings.builderAppend(buffer, cb.get(0));
            Strings.builderAppend(buffer, cb.get(1));
        }
        return k;
    }

    private static JSException invalidEncodingError() {
        throw Errors.createURIError("invalid encoding");
    }

    private static int getHexValue(char digit) {
        int value2 = JSRuntime.valueInHex(digit);
        if (value2 < 0) {
            throw Errors.createURIError("decode: Illegal hex characters in escape (%) pattern");
        }
        return value2;
    }

    private boolean isReserved(char c) {
        if (this.isSpecial) {
            return JSURLEncoder.reservedURISet.get(c);
        }
        return false;
    }

    private static int findN(byte b) {
        if ((b & 0x40) == 0) {
            return 1;
        }
        if ((b & 0x20) == 0) {
            return 2;
        }
        if ((b & 0x10) == 0) {
            return 3;
        }
        if ((b & 8) == 0) {
            return 4;
        }
        if ((b & 4) == 0) {
            return 5;
        }
        if ((b & 2) == 0) {
            return 6;
        }
        if ((b & 1) == 0) {
            return 7;
        }
        return 8;
    }
}

